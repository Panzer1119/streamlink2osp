#!/bin/bash

# Check if the required commands are installed
## Streamlink
if ! command -v streamlink &>/dev/null; then
  echo "streamlink could not be found"
  exit
fi
## FFmpeg
if ! command -v ffmpeg &>/dev/null; then
  echo "ffmpeg could not be found"
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
## Open Streaming Platform
OSP_RTMP_FQDN=${OSP_RTMP_FQDN:-""}
OSP_RTMP_PORT=${OSP_RTMP_PORT:-"1935"}
OSP_STREAM_KEY=${OSP_STREAM_KEY:-""}

# Combine some variables
## Open Streaming Platform
OSP_STREAM_URL="rtmp://${OSP_RTMP_FQDN}:${OSP_RTMP_PORT}/stream/${OSP_STREAM_KEY}"

# Check if environment variables are set
## Streamlink
if [ -z "${LIVESTREAM_URL}" ]; then
  echo "LIVESTREAM_URL is not set"
  exit 1
fi
if [ -z "${STREAMLINK_QUALITY}" ]; then
  echo "STREAMLINK_QUALITY is not set"
  exit 1
fi
## Open Streaming Platform
if [ -z "${OSP_RTMP_FQDN}" ]; then
  echo "OSP_RTMP_FQDN is not set"
  exit 1
fi
if [ -z "${OSP_RTMP_PORT}" ]; then
  echo "OSP_RTMP_PORT is not set"
  exit 1
fi
if [ -z "${OSP_STREAM_KEY}" ]; then
  echo "OSP_STREAM_KEY is not set"
  exit 1
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
FFMPEG_COMMAND="ffmpeg -i - -c copy -f flv ${OSP_STREAM_URL}"

# Execute the commands and print them to the console
echo "Executing the following commands:"
# Replace the stream key in the command with a placeholder
echo "${STREAMLINK_COMMAND} | ${FFMPEG_COMMAND/${OSP_STREAM_KEY}/<STREAM_KEY>}"
eval "${STREAMLINK_COMMAND} | ${FFMPEG_COMMAND}"
