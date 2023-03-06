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

# Run command with the variables
streamlink --stdout "${LIVESTREAM_URL}" "${STREAMLINK_QUALITY}" | ffmpeg -i - -c copy -f flv "${OSP_STREAM_URL}"
