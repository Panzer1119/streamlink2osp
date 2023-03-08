# Changelog

All notable changes to this project will be documented in this file. See [standard-version](https://github.com/conventional-changelog/standard-version) for commit guidelines.

### [1.2.2](https://github.com/Panzer1119/streamlink2osp/compare/v1.2.1...v1.2.2) (2023-03-08)


### Bug Fixes

* add a check if the commands curl and jq are installed when enabling the Twitch API ([5285abd](https://github.com/Panzer1119/streamlink2osp/commit/5285abd72a6e62131f435ca19b1643da1c47476d))
* install curl and jq in docker image ([902fb56](https://github.com/Panzer1119/streamlink2osp/commit/902fb5602a9a90a3dfc25ef9d0da6de8547adf95))
* skip on_stream_start if Twitch API is not enabled ([2a5f107](https://github.com/Panzer1119/streamlink2osp/commit/2a5f107d9d928dcd6ce6d4e98ea9c165b6814adb))

### [1.2.1](https://github.com/Panzer1119/streamlink2osp/compare/v1.2.0...v1.2.1) (2023-03-08)

## [1.2.0](https://github.com/Panzer1119/streamlink2osp/compare/v1.1.0...v1.2.0) (2023-03-08)


### Features

* add a check to validate the twitch api credentials and made the client id optional by retrieving it with the bearer token ([d232c1a](https://github.com/Panzer1119/streamlink2osp/commit/d232c1aed75cc41c1fcfa6c6f8d321ab017eb2c9))
* implement deletion of old hls files on a new stream ([3d2ee01](https://github.com/Panzer1119/streamlink2osp/commit/3d2ee01f759b195a13ae60c3e125093dc679bed8))
* use the Twitch API to retrieve data about the livestream and add an option to remove old HLS files ([e487ea7](https://github.com/Panzer1119/streamlink2osp/commit/e487ea7fb6404dd6c36da99f12abb843070aa2ec))

## [1.1.0](https://github.com/Panzer1119/streamlink2osp/compare/v1.0.0...v1.1.0) (2023-03-07)


### Features

* add environment variable FFMPEG_OPTIONS to customize the FFmpeg options ([440bde2](https://github.com/Panzer1119/streamlink2osp/commit/440bde2a150adba90bd4038ae94baa815295d19f))
* implement environment variable STREAMLINK_ADDITIONAL_OPTIONS ([9a878bc](https://github.com/Panzer1119/streamlink2osp/commit/9a878bc0c1fd62d35e31c6ed9ace478b09245c24))

## 1.0.0
 (2023-03-07)


### Features

* add options to disable twitch ads and reruns and enable them by default ([eaa0e13](https://github.com/Panzer1119/streamlink2osp/commit/eaa0e138c0f75ce57f040d11b9824dddc1f812ce))
* create Dockerfile ([9300589](https://github.com/Panzer1119/streamlink2osp/commit/93005896bc135188e48879f2df11ca8501fe5370))
* create streamlink2osp.sh ([c107eee](https://github.com/Panzer1119/streamlink2osp/commit/c107eee5406611f375d0e493c696958fd64e9038))
* make streamlink cli options retry streams, max and open configurable via environment variables ([3f5cfb0](https://github.com/Panzer1119/streamlink2osp/commit/3f5cfb0157c9508519e6776a7c74d5f67609dc23))
* switch from debian base image and manual ffmpeg installation to linuxserver/ffmpeg base image ([6305059](https://github.com/Panzer1119/streamlink2osp/commit/6305059e5e9e98b6dfee653824237712e9d7e9ef))
* switch from linuxserver/ffmpeg base image and manual streamlink installation to rayou/streamlink base image ([1e01e73](https://github.com/Panzer1119/streamlink2osp/commit/1e01e7345372190ad897f2fd9e833bf34e208dd3))


### Bug Fixes

* clean up after installing streamlink in Dockerfile ([102d99f](https://github.com/Panzer1119/streamlink2osp/commit/102d99fc9dc7f85098dfb983985835aab299c218))
* execute the main script with sh ([49e06a1](https://github.com/Panzer1119/streamlink2osp/commit/49e06a1de7133060fa1e0899b7b52210576c1c1d))
* remove the entrypoint from the base image ([747d7f8](https://github.com/Panzer1119/streamlink2osp/commit/747d7f85dcc691e1f4189b85991f6d64b07178fa))
* remove the stream key from logging the command that gets executed ([ada2d91](https://github.com/Panzer1119/streamlink2osp/commit/ada2d9130ea5d9c9f297fbee7a28716012035471))
