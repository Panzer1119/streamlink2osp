/*
 *    Copyright 2023 Paul Hagedorn
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package de.codemakers.streamlink2osp.stream;

import de.codemakers.streamlink2osp.Config;
import de.codemakers.streamlink2osp.arguments.FFmpegArguments;
import de.codemakers.streamlink2osp.arguments.StreamlinkArguments;
import org.apache.commons.exec.CommandLine;

public record TwitchReStreamArguments(StreamlinkArguments streamlinkArguments, String twitchUserLogin,
                                      FFmpegArguments ffmpegArguments, String ospRtmpHost, Integer ospRtmpPort,
                                      String ospStreamKey) {

    public static TwitchReStreamArguments createFromConfig(String twitchUserLogin) {
        return new TwitchReStreamArguments(StreamlinkArguments.createFromConfig(), twitchUserLogin, FFmpegArguments.createFromConfig(), Config.getOSPRtmpHost(), Config.getOSPRtmpPort(), Config.getOSPStreamKey());
    }

    public String formatTwitchStreamUrl() {
        return String.format("twitch.tv/%s", twitchUserLogin);
    }

    public String formatOSPStreamUrl() {
        return String.format("rtmp://%s:%s/stream/%s", ospRtmpHost, ospRtmpPort, ospStreamKey);
    }

    public CommandLine createStreamlinkCommandLine() {
        return streamlinkArguments.createCommandLine(this);
    }

    public CommandLine createFFmpegCommandLine() {
        return ffmpegArguments.createCommandLine(this);
    }

}
