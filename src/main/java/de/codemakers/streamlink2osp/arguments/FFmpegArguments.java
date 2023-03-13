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

package de.codemakers.streamlink2osp.arguments;

import de.codemakers.streamlink2osp.Config;
import de.codemakers.streamlink2osp.stream.TwitchReStreamArguments;
import org.apache.commons.exec.CommandLine;

public record FFmpegArguments(String path, String options) {

    private static final FFmpegArguments DEFAULT = new FFmpegArguments("ffmpeg", "-c copy -f flv");

    public static FFmpegArguments getDefault() {
        return DEFAULT;
    }

    public static FFmpegArguments createFromConfig() {
        // Get the necessary config values
        final String path = Config.getFfmpegPath();
        final String options = Config.getFfmpegOptions();
        // Create the FFmpegArguments
        return new FFmpegArguments(path, options);
    }

    public CommandLine createCommandLine(TwitchReStreamArguments arguments) {
        final CommandLine commandLine = new CommandLine(path);
        commandLine.addArgument("-i");
        commandLine.addArgument("-");
        commandLine.addArgument(options);
        commandLine.addArgument(arguments.formatOSPStreamUrl());
        return commandLine;
    }

}
