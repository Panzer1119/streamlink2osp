streamlink2osp
==============

[![Docker Image Version (tag latest semver)](https://img.shields.io/docker/v/panzer1119/streamlink2osp/latest)](https://hub.docker.com/r/panzer1119/streamlink2osp/tags)
[![Docker Image Size (tag)](https://img.shields.io/docker/image-size/panzer1119/streamlink2osp/latest)](https://hub.docker.com/r/panzer1119/streamlink2osp/tags?name=latest)
[![Docker Pulls](https://img.shields.io/docker/pulls/panzer1119/streamlink2osp)](https://hub.docker.com/r/panzer1119/streamlink2osp)

Streamlink2osp is a tool that enables users to stream video content from various sources
using [Streamlink](https://streamlink.github.io/) directly to
an [Open Streaming Platform](https://openstreamingplatform.com/) (OSP) instance.
It is distributed as a Docker image and can be easily run on any machine that has Docker installed.

Docker Image
------------

The Docker image for streamlink2osp can be found on both DockerHub and GitHub Container Registry:

* [DockerHub](https://hub.docker.com/r/panzer1119/streamlink2osp): `docker pull panzer1119/streamlink2osp:latest`
* [GitHub Container Registry](https://github.com/Panzer1119/streamlink2osp/pkgs/container/streamlink2osp): `docker pull ghcr.io/panzer1119/streamlink2osp:latest`

Usage
-----

To use streamlink2osp, simply run the following command:

```bash
docker run --rm -d -e OSP_RTMP_FQDN=<FQDN> -e OSP_STREAM_KEY=<KEY> -e LIVESTREAM_URL=<URL> panzer1119/streamlink2osp:latest
```

Environment Variables
---------------------

### Required

| Environment Variable | Description                                                                                                                                                                            | Example Value                     |
|----------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------|
| `LIVESTREAM_URL`     | The URL of the video stream to be downloaded using Streamlink.                                                                                                                         | `https://www.twitch.tv/mychannel` |
| `OSP_RTMP_FQDN`      | The FQDN of the OSP RTMP instance to which the video stream should be uploaded.                                                                                                        | `osp.example.com`                 |
| `OSP_STREAM_KEY`     | The secret stream key to authenticate with the OSP instance. [How to find your Stream Key.](https://open-streaming-platform.readthedocs.io/en/latest/usage/streaming.html#stream-keys) | <STREAM-KEY>                      |

### Optional

| Environment Variable               | Description                                                                                                                                                                    | Default Value                                                               | Example Value          |
|------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------|------------------------|
| `STREAMLINK_QUALITY`               | The desired video quality to download the stream in.                                                                                                                           | `best`                                                                      | `720p`                 |
| `STREAMLINK_TWITCH_DISABLE_ADS`    | A flag to disable ads when downloading Twitch streams. If set to true, ads will be skipped.                                                                                    | `true`                                                                      | `false`                |
| `STREAMLINK_TWITCH_DISABLE_RERUNS` | A flag to disable reruns when downloading Twitch streams. If set to true, reruns will be skipped.                                                                              | `true`                                                                      | `false`                |
| `STREAMLINK_RETRY_STREAMS`         | The time (in seconds) to wait before attempting to fetch again.                                                                                                                | [Single try](https://streamlink.github.io/cli.html#cmdoption-retry-streams) | `120`                  |
| `STREAMLINK_RETRY_MAX`             | The maximum number of retries to attempt to fetch a stream. Fetch will retry infinitely if this is zero or unset.                                                              | [None](https://streamlink.github.io/cli.html#cmdoption-retry-max)           | `0`                    |
| `STREAMLINK_RETRY_OPEN`            | The maximum number of retries to attempt to open a stream after a successful fetch.                                                                                            | [Single try](https://streamlink.github.io/cli.html#cmdoption-retry-open)    | `2`                    |
| `STREAMLINK_ADDITIONAL_OPTIONS`    | Additional options to be passed to Streamlink. **Not implemented yet!**                                                                                                        | `""`                                                                        | `--twitch-low-latency` |
| `OSP_RTMP_PORT`                    | The Port of the OSP RTMP instance. [Currently can't be proxied to another Port.](https://open-streaming-platform.readthedocs.io/en/latest/install/install.html#docker-install) | `1935`                                                                      | `1935`                 |
