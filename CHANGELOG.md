# Changelog

All notable changes to this project will be documented in this file. See [standard-version](https://github.com/conventional-changelog/standard-version) for commit guidelines.

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
