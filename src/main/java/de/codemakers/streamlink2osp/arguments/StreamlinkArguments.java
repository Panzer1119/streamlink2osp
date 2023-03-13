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

public record StreamlinkArguments(String path, String quality, Integer retryStreams, Integer retryMax,
                                  Integer retryOpen, Boolean twitchDisableAds, Boolean twitchDisableReruns,
                                  String additionalOptions) {

    private static final StreamlinkArguments DEFAULT = new StreamlinkArguments("streamlink", "best", null, null, null, true, true, "");

    public static StreamlinkArguments getDefault() {
        return DEFAULT;
    }

    public static StreamlinkArguments createFromConfig() {
        // Get the necessary config values
        final String path = Config.getStreamlinkPath();
        final String quality = Config.getStreamlinkQuality();
        final Integer retryStreams = Config.getStreamlinkRetryStreams();
        final Integer retryMax = Config.getStreamlinkRetryMax();
        final Integer retryOpen = Config.getStreamlinkRetryOpen();
        final Boolean twitchDisableAds = Config.getStreamlinkTwitchDisableAds();
        final Boolean twitchDisableReruns = Config.getStreamlinkTwitchDisableReruns();
        final String additionalOptions = Config.getStreamlinkAdditionalOptions();
        // Create the StreamlinkArguments
        return new StreamlinkArguments(path, quality, retryStreams, retryMax, retryOpen, twitchDisableAds, twitchDisableReruns, additionalOptions);
    }

}
