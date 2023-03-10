#!/bin/bash

#
#    Copyright 2023 Paul Hagedorn
#
#    Licensed under the Apache License, Version 2.0 (the "License");
#    you may not use this file except in compliance with the License.
#    You may obtain a copy of the License at
#
#        http://www.apache.org/licenses/LICENSE-2.0
#
#    Unless required by applicable law or agreed to in writing, software
#    distributed under the License is distributed on an "AS IS" BASIS,
#    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#    See the License for the specific language governing permissions and
#    limitations under the License.
#

# Check if the required commands are installed
## Streamlink
if ! command -v streamlink &>/dev/null; then
  echo "streamlink could not be found" >&2
  exit
fi
## FFmpeg
if ! command -v ffmpeg &>/dev/null; then
  echo "FFmpeg could not be found" >&2
  exit
fi

# Read environment variables and use defaults if not set
## Streamlink
LIVESTREAM_URL=${LIVESTREAM_URL:-""}
STREAMLINK_QUALITY=${STREAMLINK_QUALITY:-"best"}
STREAMLINK_TWITCH_DISABLE_ADS=${STREAMLINK_TWITCH_DISABLE_ADS:-"true"}
STREAMLINK_TWITCH_DISABLE_RERUNS=${STREAMLINK_TWITCH_DISABLE_RERUNS:-"true"}
STREAMLINK_RETRY_STREAMS=${STREAMLINK_RETRY_STREAMS:-""}
STREAMLINK_RETRY_MAX=${STREAMLINK_RETRY_MAX:-""}
STREAMLINK_RETRY_OPEN=${STREAMLINK_RETRY_OPEN:-""}
STREAMLINK_ADDITIONAL_OPTIONS=${STREAMLINK_ADDITIONAL_OPTIONS:-""}
## FFmpeg
FFMPEG_OPTIONS=${FFMPEG_OPTIONS:-""}
## Open Streaming Platform
OSP_RTMP_FQDN=${OSP_RTMP_FQDN:-""}
OSP_RTMP_PORT=${OSP_RTMP_PORT:-"1935"}
OSP_STREAM_KEY=${OSP_STREAM_KEY:-""}
OSP_DELETE_HLS_FILES_ON_NEW_STREAM=${OSP_DELETE_HLS_FILES_ON_NEW_STREAM:-"false"}
OSP_DELETE_HLS_FILES_AFTER_STREAM_END=${OSP_DELETE_HLS_FILES_AFTER_STREAM_END:-"false"}
OSP_LIVE_HLS_DIRECTORY=${OSP_LIVE_HLS_DIRECTORY:-"/tmpfs/live"}
## Twitch
TWITCH_CLIENT_ID=${TWITCH_CLIENT_ID:-""}
TWITCH_BEARER_TOKEN=${TWITCH_BEARER_TOKEN:-""}
TWITCH_USER_NAME=${TWITCH_USER_NAME:-""}
## Misc
DEBUG=${DEBUG:-"false"}

# Internal variables
## Open Streaming Platform
OSP_STREAM_URL="rtmp://${OSP_RTMP_FQDN}:${OSP_RTMP_PORT}/stream/${OSP_STREAM_KEY}"
OSP_STREAM_LIVE_HLS_DIRECTORY="${OSP_LIVE_HLS_DIRECTORY}/${OSP_STREAM_KEY}"
## Twitch
TWITCH_ENABLE_API=${TWITCH_ENABLE_API:-"false"}
TWITCH_STREAM_STARTED_AT=${TWITCH_STREAM_STARTED_AT:-""}
TWITCH_STREAM_STARTED_AT_FILE="${OSP_LIVE_HLS_DIRECTORY}/${OSP_STREAM_KEY}.started_at"

# If debug is enabled print that we are in debug mode
if [ "${DEBUG}" = "true" ]; then
  echo "Debug mode is enabled"
fi

check_twitch_api_credentials() {
  # Validate bearer token and retrieve client id
  local response
  response=$(curl -s -H "Authorization: Bearer ${TWITCH_BEARER_TOKEN}" -X GET "https://id.twitch.tv/oauth2/validate")
  local status
  status=$(echo "${response}" | jq -r '.status')

  # If the status is 401 the bearer token is invalid or expired
  if [ "${status}" = "401" ]; then
    echo "Bearer token is invalid or expired" >&2
    exit 1
  fi

  local client_id
  client_id=$(echo "${response}" | jq -r '.client_id')

  # If the client id is empty the bearer token is invalid
  if [ -z "${client_id}" ]; then
    echo "Bearer token may be invalid, because no client id was returned" >&2
    exit 1
  fi

  # If the TWITCH_CLIENT_ID is not set, set it to the client id retrieved from the bearer token
  if [ -z "${TWITCH_CLIENT_ID}" ]; then
    echo "TWITCH_CLIENT_ID is not set, using retrieved Twitch client id: ${client_id}"
    TWITCH_CLIENT_ID="${client_id}"
  fi

  # If the TWITCH_CLIENT_ID is not equal to the client id retrieved from the bearer token the bearer token is not valid for the client id
  if [ "${TWITCH_CLIENT_ID}" != "${client_id}" ]; then
    echo "Bearer token is not valid for the client id, it is valid for ${client_id}" >&2
    exit 1
  fi
}

# Check if environment variables are set
## Streamlink
if [ -z "${LIVESTREAM_URL}" ]; then
  echo "LIVESTREAM_URL is not set" >&2
  exit 1
fi
if [ -z "${STREAMLINK_QUALITY}" ]; then
  echo "STREAMLINK_QUALITY is not set" >&2
  exit 1
fi
## Open Streaming Platform
if [ -z "${OSP_RTMP_FQDN}" ]; then
  echo "OSP_RTMP_FQDN is not set" >&2
  exit 1
fi
if [ -z "${OSP_RTMP_PORT}" ]; then
  echo "OSP_RTMP_PORT is not set" >&2
  exit 1
fi
if [ -z "${OSP_STREAM_KEY}" ]; then
  echo "OSP_STREAM_KEY is not set" >&2
  exit 1
fi
## Twitch
### Set TWITCH_ENABLE_API to true if OSP_DELETE_HLS_FILES_ON_NEW_STREAM or OSP_DELETE_HLS_FILES_AFTER_STREAM_END is true
if [ "${OSP_DELETE_HLS_FILES_ON_NEW_STREAM}" = "true" ]; then
  TWITCH_ENABLE_API="true"
fi
if [ "${OSP_DELETE_HLS_FILES_AFTER_STREAM_END}" = "true" ]; then
  TWITCH_ENABLE_API="true"
fi
### Check if the environment variables for the Twitch API are set if TWITCH_ENABLE_API is true
if [ "${TWITCH_ENABLE_API}" = "true" ]; then
  # Check if the required commands are installed
  ## curl
  if ! command -v curl &>/dev/null; then
    echo "curl could not be found" >&2
    exit
  fi
  ## jq
  if ! command -v jq &>/dev/null; then
    echo "jq could not be found" >&2
    exit
  fi
  # Check if the LIVESTREAM_URL is a Twitch livestream url
  if ! echo "${LIVESTREAM_URL}" | grep -q "twitch.tv"; then
    echo "LIVESTREAM_URL is not a Twitch livestream url" >&2
    exit 1
  fi
  if [ -z "${TWITCH_BEARER_TOKEN}" ]; then
    echo "TWITCH_BEARER_TOKEN is not set" >&2
    exit 1
  fi
  check_twitch_api_credentials
  # If OSP_DELETE_HLS_FILES_ON_NEW_STREAM or OSP_DELETE_HLS_FILES_AFTER_STREAM_END is true check if OSP_LIVE_HLS_DIRECTORY exists
  if [ "${OSP_DELETE_HLS_FILES_ON_NEW_STREAM}" = "true" ] || [ "${OSP_DELETE_HLS_FILES_AFTER_STREAM_END}" = "true" ]; then
    if [ ! -d "${OSP_LIVE_HLS_DIRECTORY}" ]; then
      echo "Directory '${OSP_LIVE_HLS_DIRECTORY}' does not exist" >&2
      exit 1
    fi
  fi
  # If TWITCH_USER_NAME is not set try to extract the Twitch username from the livestream url
  if [ -z "${TWITCH_USER_NAME}" ]; then
    TWITCH_USER_NAME="${LIVESTREAM_URL#https://twitch.tv/}"
    TWITCH_USER_NAME="${TWITCH_USER_NAME%/}"
    echo "TWITCH_USER_NAME is not set, using extracted Twitch username: '${TWITCH_USER_NAME}'"
  fi
  # Check if TWITCH_USER_NAME is set
  if [ -z "${TWITCH_USER_NAME}" ]; then
    echo "TWITCH_USER_NAME is not set" >&2
    exit 1
  fi
fi

# Build a command with flags to disable twitch ads and reruns if the corresponding environment variables are set
STREAMLINK_COMMAND="streamlink --stdout"
if [ "${STREAMLINK_TWITCH_DISABLE_ADS}" = "true" ]; then
  STREAMLINK_COMMAND="${STREAMLINK_COMMAND} --twitch-disable-ads"
fi
if [ "${STREAMLINK_TWITCH_DISABLE_RERUNS}" = "true" ]; then
  STREAMLINK_COMMAND="${STREAMLINK_COMMAND} --twitch-disable-reruns"
fi
# Add retry streams, max and open flags if the corresponding environment variables are set
if [ -n "${STREAMLINK_RETRY_STREAMS}" ]; then
  STREAMLINK_COMMAND="${STREAMLINK_COMMAND} --retry-streams ${STREAMLINK_RETRY_STREAMS}"
fi
if [ -n "${STREAMLINK_RETRY_MAX}" ]; then
  STREAMLINK_COMMAND="${STREAMLINK_COMMAND} --retry-max ${STREAMLINK_RETRY_MAX}"
fi
if [ -n "${STREAMLINK_RETRY_OPEN}" ]; then
  STREAMLINK_COMMAND="${STREAMLINK_COMMAND} --retry-open ${STREAMLINK_RETRY_OPEN}"
fi
# Add additional options if the corresponding environment variable is set
if [ -n "${STREAMLINK_ADDITIONAL_OPTIONS}" ]; then
  STREAMLINK_COMMAND="${STREAMLINK_COMMAND} ${STREAMLINK_ADDITIONAL_OPTIONS}"
fi
# Add the livestream url and quality
STREAMLINK_COMMAND="${STREAMLINK_COMMAND} ${LIVESTREAM_URL} ${STREAMLINK_QUALITY}"

# Build a command to stream the livestream to the Open Streaming Platform
FFMPEG_COMMAND="ffmpeg -i -"
# Set the options to default value if the corresponding environment variable is not set
if [ -z "${FFMPEG_OPTIONS}" ]; then
  FFMPEG_OPTIONS="-c copy -f flv"
fi
# Add the options
FFMPEG_COMMAND="${FFMPEG_COMMAND} ${FFMPEG_OPTIONS}"
# Add the Open Streaming Platform stream url
FFMPEG_COMMAND="${FFMPEG_COMMAND} ${OSP_STREAM_URL}"

get_streams() {
  # Make HTTP request to Twitch API to get the active streams of the streamer
  local response
  response=$(curl -s -H "Authorization: Bearer ${TWITCH_BEARER_TOKEN}" \
    -H "Client-ID: ${TWITCH_CLIENT_ID}" \
    -X GET "https://api.twitch.tv/helix/streams?user_login=${TWITCH_USER_NAME}")
  echo "${response}"
}

get_stream_started_at() {
  # Get the streams of the streamer
  local response
  response=$(get_streams)

  # Parse JSON response with jq
  local is_live
  is_live=$(echo "${response}" | jq -r '.data | length')
  local stream_type
  stream_type=$(echo "${response}" | jq -r '.data[0]?.type')
  local started_at
  started_at=$(echo "${response}" | jq -r '.data[0]?.started_at')

  # Return if the stream is not live
  if [ "${is_live}" = "0" ]; then
    echo ""
    return
  fi

  # Return if the stream type is not live
  if [ "${stream_type}" != "live" ]; then
    echo ""
    return
  fi

  echo "${started_at}"
}

save_stream_started_at() {
  if [ ! -d "${OSP_LIVE_HLS_DIRECTORY}" ]; then
    echo "Directory '${OSP_LIVE_HLS_DIRECTORY}' does not exist" >&2
    return
  fi

  # Get the started_at timestamp
  local started_at
  started_at=$(get_stream_started_at)

  # If the started_at timestamp is empty return
  if [ -z "${started_at}" ]; then
    echo "No running Twitch stream found"
    # Delete TWITCH_STREAM_STARTED_AT_FILE if it exists
    if [ -f "${TWITCH_STREAM_STARTED_AT_FILE}" ]; then
      rm "${TWITCH_STREAM_STARTED_AT_FILE}"
      if [ "${DEBUG}" = "true" ]; then
        echo "Deleted file '${TWITCH_STREAM_STARTED_AT_FILE}'"
      fi
    fi
    return
  fi

  echo "Current Twitch stream started at: ${started_at}"

  # Save the started_at timestamp in an environment variable
  export TWITCH_STREAM_STARTED_AT="${started_at}"

  # Save the started_at timestamp in a file
  echo "${started_at}" >"${TWITCH_STREAM_STARTED_AT_FILE}"
  if [ "${DEBUG}" = "true" ]; then
    echo "Saved Twitch stream started at '${started_at}' to file '${TWITCH_STREAM_STARTED_AT_FILE}'"
  fi
}

load_stream_started_at() {
  # If the file does not exist return
  if [ ! -f "${TWITCH_STREAM_STARTED_AT_FILE}" ]; then
    # Clear the started_at timestamp in an environment variable
    export TWITCH_STREAM_STARTED_AT=""
    return
  fi

  # Load the started_at timestamp from a file
  local started_at
  started_at=$(cat "${TWITCH_STREAM_STARTED_AT_FILE}")
  if [ "${DEBUG}" = "true" ]; then
    echo "Loaded Twitch stream started at '${started_at}' from file '${TWITCH_STREAM_STARTED_AT_FILE}'"
  fi

  # Save the started_at timestamp in an environment variable
  export TWITCH_STREAM_STARTED_AT="${started_at}"
}

on_stream_start() {
  # Return if TWITCH_ENABLE_API is not true
  if [ "${TWITCH_ENABLE_API}" != "true" ]; then
    return
  fi

  # Load the started_at timestamp
  load_stream_started_at

  local is_new_stream
  is_new_stream="false"

  # If the started_at timestamp is not set save it
  if [ -z "${TWITCH_STREAM_STARTED_AT}" ]; then
    save_stream_started_at
    if [ -z "${TWITCH_STREAM_STARTED_AT}" ]; then
      return
    fi
    is_new_stream="true"
  fi

  # Get the started_at timestamp
  local started_at
  started_at=$(get_stream_started_at)

  # If the started_at timestamp is empty return
  if [ -z "${started_at}" ]; then
    echo "No running Twitch stream found"
    return
  fi

  # If the started_at timestamp is different set is_new_stream to true
  if [ "${started_at}" != "${TWITCH_STREAM_STARTED_AT}" ]; then
    is_new_stream="true"
  fi

  # If the stream is a new stream delete the HLS files
  if [ "${is_new_stream}" = "true" ]; then
    echo "New Twitch stream started"
    # If the environment variable OSP_DELETE_HLS_FILES_ON_NEW_STREAM is set to true delete the HLS files
    if [ "${OSP_DELETE_HLS_FILES_ON_NEW_STREAM}" == "true" ]; then
      delete_hls_files
    fi
  fi
}

on_stream_end() {
  # Return if TWITCH_ENABLE_API is not true
  if [ "${TWITCH_ENABLE_API}" != "true" ]; then
    return
  fi

  # Get the streams of the streamer
  local response
  response=$(get_streams)
  if [ "${DEBUG}" = "true" ]; then
    echo "${response}" | jq '.'
  fi

  # Parse JSON response with jq
  local is_live
  is_live=$(echo "${response}" | jq -r '.data | length')
  local stream_type
  stream_type=$(echo "${response}" | jq -r '.data[0]?.type')
  local user_name
  user_name=$(echo "${response}" | jq -r '.data[0]?.user_name')
  local started_at
  started_at=$(echo "${response}" | jq -r '.data[0]?.started_at')

  # Check if the stream is the same stream
  if [ "${is_live}" = "1" ] && [ "${stream_type}" = "live" ] && [ "${started_at}" = "${TWITCH_STREAM_STARTED_AT}" ]; then
    echo "User '${user_name}' is still live"
  else
    echo "User '${user_name}' is no longer live"
    # If the stream is not the same stream, delete all hls segments from the Open Streaming Platform
    if [ "${OSP_DELETE_HLS_FILES_AFTER_STREAM_END}" == "true" ]; then
      delete_hls_files
    fi
  fi
}

delete_hls_files() {
  echo "Deleting old HLS files in '${OSP_STREAM_LIVE_HLS_DIRECTORY}'"

  # Delete all files in OSP_STREAM_LIVE_HLS_DIRECTORY using find
  find "${OSP_STREAM_LIVE_HLS_DIRECTORY}" -type f -delete
}

on_stream_start
# Execute the commands and print them to the console
echo "Executing the following commands:"
# Replace the stream key in the command with a placeholder
echo "${STREAMLINK_COMMAND} | ${FFMPEG_COMMAND/${OSP_STREAM_KEY}/<STREAM_KEY>}"
eval "${STREAMLINK_COMMAND} | ${FFMPEG_COMMAND}"
# Execute the on_stream_end function when the script is about to exit
on_stream_end
