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

package de.codemakers.streamlink2osp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Config {

    private static final Logger logger = LogManager.getLogger();

    // Keys
    // // Streamlink
    public static final String KEY_STREAMLINK_PATH = "STREAMLINK_PATH";
    public static final String KEY_STREAMLINK_QUALITY = "STREAMLINK_QUALITY";
    public static final String KEY_STREAMLINK_RETRY_STREAMS = "STREAMLINK_RETRY_STREAMS";
    public static final String KEY_STREAMLINK_RETRY_MAX = "STREAMLINK_RETRY_MAX";
    public static final String KEY_STREAMLINK_RETRY_OPEN = "STREAMLINK_RETRY_OPEN";
    public static final String KEY_STREAMLINK_TWITCH_DISABLE_ADS = "STREAMLINK_TWITCH_DISABLE_ADS";
    public static final String KEY_STREAMLINK_TWITCH_DISABLE_RERUNS = "STREAMLINK_TWITCH_DISABLE_RERUNS";
    public static final String KEY_STREAMLINK_ADDITIONAL_OPTIONS = "STREAMLINK_ADDITIONAL_OPTIONS";
    // // FFmpeg
    public static final String KEY_FFMPEG_PATH = "FFMPEG_PATH";
    public static final String KEY_FFMPEG_OPTIONS = "FFMPEG_OPTIONS";
    // // Twitch
    public static final String KEY_TWITCH_CLIENT_ID = "TWITCH_CLIENT_ID";
    public static final String KEY_TWITCH_CLIENT_SECRET = "TWITCH_CLIENT_SECRET";
    public static final String KEY_TWITCH_USER_IDS = "TWITCH_USER_IDS";
    public static final String KEY_TWITCH_USER_LOGINS = "TWITCH_USER_LOGINS";
    // // Open Streaming Platform
    public static final String KEY_OSP_API_PROTOCOL = "OSP_API_PROTOCOL";
    public static final String KEY_OSP_API_HOST = "OSP_API_HOST";
    public static final String KEY_OSP_API_PORT = "OSP_API_PORT";
    public static final String KEY_OSP_API_PATH = "OSP_API_PATH";
    public static final String KEY_OSP_API_VERSION = "OSP_API_VERSION";
    public static final String KEY_OSP_API_KEY = "OSP_API_KEY";
    public static final String KEY_OSP_RTMP_HOST = "OSP_RTMP_HOST";
    public static final String KEY_OSP_RTMP_PORT = "OSP_RTMP_PORT";
    public static final String KEY_OSP_STREAM_KEY = "OSP_STREAM_KEY";

    // Default Values
    // // Streamlink
    private static final String DEFAULT_STREAMLINK_PATH = "streamlink";
    private static final String DEFAULT_STREAMLINK_QUALITY = "best";
    private static final String DEFAULT_STREAMLINK_RETRY_STREAMS = "120";
    private static final String DEFAULT_STREAMLINK_RETRY_MAX = "0";
    private static final String DEFAULT_STREAMLINK_RETRY_OPEN = "2";
    private static final String DEFAULT_STREAMLINK_TWITCH_DISABLE_ADS = "true";
    private static final String DEFAULT_STREAMLINK_TWITCH_DISABLE_RERUNS = "true";
    private static final String DEFAULT_STREAMLINK_ADDITIONAL_OPTIONS = "";
    // // FFmpeg
    private static final String DEFAULT_FFMPEG_PATH = "ffmpeg";
    private static final String DEFAULT_FFMPEG_OPTIONS = "-c copy -f flv";
    // // Twitch
    private static final String DEFAULT_TWITCH_CLIENT_ID = null;
    private static final String DEFAULT_TWITCH_CLIENT_SECRET = null;
    private static final String DEFAULT_TWITCH_USER_IDS = "";
    private static final String DEFAULT_TWITCH_USER_LOGINS = "";
    // // Open Streaming Platform
    private static final String DEFAULT_OSP_API_PROTOCOL = "http";
    private static final String DEFAULT_OSP_API_HOST = "localhost";
    private static final String DEFAULT_OSP_API_PORT = "80";
    private static final String DEFAULT_OSP_API_PATH = "api";
    private static final String DEFAULT_OSP_API_VERSION = "v1";
    private static final String DEFAULT_OSP_API_KEY = null;
    private static final String DEFAULT_OSP_RTMP_HOST = "localhost";
    private static final String DEFAULT_OSP_RTMP_PORT = "1935";
    private static final String DEFAULT_OSP_STREAM_KEY = null;

    // Values
    // // Streamlink
    private static final String VALUE_STREAMLINK_PATH = getConfig(KEY_STREAMLINK_PATH, DEFAULT_STREAMLINK_PATH);
    private static final String VALUE_STREAMLINK_QUALITY = getConfig(KEY_STREAMLINK_QUALITY, DEFAULT_STREAMLINK_QUALITY);
    private static final String VALUE_STREAMLINK_RETRY_STREAMS = getConfig(KEY_STREAMLINK_RETRY_STREAMS, DEFAULT_STREAMLINK_RETRY_STREAMS);
    private static final String VALUE_STREAMLINK_RETRY_MAX = getConfig(KEY_STREAMLINK_RETRY_MAX, DEFAULT_STREAMLINK_RETRY_MAX);
    private static final String VALUE_STREAMLINK_RETRY_OPEN = getConfig(KEY_STREAMLINK_RETRY_OPEN, DEFAULT_STREAMLINK_RETRY_OPEN);
    private static final String VALUE_STREAMLINK_TWITCH_DISABLE_ADS = getConfig(KEY_STREAMLINK_TWITCH_DISABLE_ADS, DEFAULT_STREAMLINK_TWITCH_DISABLE_ADS);
    private static final String VALUE_STREAMLINK_TWITCH_DISABLE_RERUNS = getConfig(KEY_STREAMLINK_TWITCH_DISABLE_RERUNS, DEFAULT_STREAMLINK_TWITCH_DISABLE_RERUNS);
    private static final String VALUE_STREAMLINK_ADDITIONAL_OPTIONS = getConfig(KEY_STREAMLINK_ADDITIONAL_OPTIONS, DEFAULT_STREAMLINK_ADDITIONAL_OPTIONS);
    // // FFmpeg
    private static final String VALUE_FFMPEG_PATH = getConfig(KEY_FFMPEG_PATH, DEFAULT_FFMPEG_PATH);
    private static final String VALUE_FFMPEG_OPTIONS = getConfig(KEY_FFMPEG_OPTIONS, DEFAULT_FFMPEG_OPTIONS);
    // // Twitch
    private static final String VALUE_TWITCH_CLIENT_ID = getConfig(KEY_TWITCH_CLIENT_ID, DEFAULT_TWITCH_CLIENT_ID);
    private static final String VALUE_TWITCH_CLIENT_SECRET = getConfig(KEY_TWITCH_CLIENT_SECRET, DEFAULT_TWITCH_CLIENT_SECRET);
    private static final String VALUE_TWITCH_USER_IDS = getConfig(KEY_TWITCH_USER_IDS, DEFAULT_TWITCH_USER_IDS);
    private static final String VALUE_TWITCH_USER_LOGINS = getConfig(KEY_TWITCH_USER_LOGINS, DEFAULT_TWITCH_USER_LOGINS);
    // // Open Streaming Platform
    private static final String VALUE_OSP_API_PROTOCOL = getConfig(KEY_OSP_API_PROTOCOL, DEFAULT_OSP_API_PROTOCOL);
    private static final String VALUE_OSP_API_HOST = getConfig(KEY_OSP_API_HOST, DEFAULT_OSP_API_HOST);
    private static final String VALUE_OSP_API_PORT = getConfig(KEY_OSP_API_PORT, DEFAULT_OSP_API_PORT);
    private static final String VALUE_OSP_API_PATH = getConfig(KEY_OSP_API_PATH, DEFAULT_OSP_API_PATH);
    private static final String VALUE_OSP_API_VERSION = getConfig(KEY_OSP_API_VERSION, DEFAULT_OSP_API_VERSION);
    private static final String VALUE_OSP_API_KEY = getConfig(KEY_OSP_API_KEY, DEFAULT_OSP_API_KEY);
    private static final String VALUE_OSP_RTMP_HOST = getConfig(KEY_OSP_RTMP_HOST, DEFAULT_OSP_RTMP_HOST);
    private static final String VALUE_OSP_RTMP_PORT = getConfig(KEY_OSP_RTMP_PORT, DEFAULT_OSP_RTMP_PORT);
    private static final String VALUE_OSP_STREAM_KEY = getConfig(KEY_OSP_STREAM_KEY, DEFAULT_OSP_STREAM_KEY);

    // Runtime Values
    // Streamlink
    private static Integer STREAMLINK_RETRY_STREAMS = null;
    private static Integer STREAMLINK_RETRY_MAX = null;
    private static Integer STREAMLINK_RETRY_OPEN = null;
    private static Boolean STREAMLINK_TWITCH_DISABLE_ADS = null;
    private static Boolean STREAMLINK_TWITCH_DISABLE_RERUNS = null;
    // // Twitch
    private static List<String> TWITCH_USER_IDS = null;
    private static List<String> TWITCH_USER_LOGINS = null;
    // // Open Streaming Platform
    private static Integer OSP_API_PORT = null;
    private static Integer OSP_RTMP_PORT = null;

    private static String getConfig(String key, String defaultValue) {
        String value = System.getProperty(key);
        if (value == null) {
            value = System.getenv(key);
        }
        if (value == null) {
            value = defaultValue;
        }
        return value;
    }

    private static String checkValue(String configValue, String configKey) {
        if (configValue == null || configValue.isEmpty()) {
            throw new RuntimeException(configKey + " is null or empty!");
        }
        return configValue;
    }

    private static String checkArrayValue(String configValue, String configKey) {
        if (configValue == null) {
            throw new RuntimeException(configKey + " is null!");
        }
        // Log a warning if the value is very long, and it seems like the user forgot to add a comma
        if (configValue.length() > 25 && !configValue.contains(",")) {
            logger.warn("The value of {} is very long and it seems like you forgot to add a comma!", configKey);
        }
        return configValue;
    }

    // Getters

    // // Streamlink

    public static String getStreamlinkPath() {
        return checkValue(VALUE_STREAMLINK_PATH, KEY_STREAMLINK_PATH);
    }

    public static String getStreamlinkQuality() {
        return checkValue(VALUE_STREAMLINK_QUALITY, KEY_STREAMLINK_QUALITY);
    }

    public static Integer getStreamlinkRetryStreams() {
        if (STREAMLINK_RETRY_STREAMS == null && VALUE_STREAMLINK_RETRY_STREAMS != null) {
            STREAMLINK_RETRY_STREAMS = Integer.parseInt(checkValue(VALUE_STREAMLINK_RETRY_STREAMS, KEY_STREAMLINK_RETRY_STREAMS));
        }
        return STREAMLINK_RETRY_STREAMS;
    }

    public static Integer getStreamlinkRetryMax() {
        if (STREAMLINK_RETRY_MAX == null && VALUE_STREAMLINK_RETRY_MAX != null) {
            STREAMLINK_RETRY_MAX = Integer.parseInt(checkValue(VALUE_STREAMLINK_RETRY_MAX, KEY_STREAMLINK_RETRY_MAX));
        }
        return STREAMLINK_RETRY_MAX;
    }

    public static Integer getStreamlinkRetryOpen() {
        if (STREAMLINK_RETRY_OPEN == null && VALUE_STREAMLINK_RETRY_OPEN != null) {
            STREAMLINK_RETRY_OPEN = Integer.parseInt(checkValue(VALUE_STREAMLINK_RETRY_OPEN, KEY_STREAMLINK_RETRY_OPEN));
        }
        return STREAMLINK_RETRY_OPEN;
    }

    public static Boolean getStreamlinkTwitchDisableAds() {
        if (STREAMLINK_TWITCH_DISABLE_ADS == null && VALUE_STREAMLINK_TWITCH_DISABLE_ADS != null) {
            STREAMLINK_TWITCH_DISABLE_ADS = Boolean.parseBoolean(checkValue(VALUE_STREAMLINK_TWITCH_DISABLE_ADS, KEY_STREAMLINK_TWITCH_DISABLE_ADS));
        }
        return STREAMLINK_TWITCH_DISABLE_ADS;
    }

    public static Boolean getStreamlinkTwitchDisableReruns() {
        if (STREAMLINK_TWITCH_DISABLE_RERUNS == null && VALUE_STREAMLINK_TWITCH_DISABLE_RERUNS != null) {
            STREAMLINK_TWITCH_DISABLE_RERUNS = Boolean.parseBoolean(checkValue(VALUE_STREAMLINK_TWITCH_DISABLE_RERUNS, KEY_STREAMLINK_TWITCH_DISABLE_RERUNS));
        }
        return STREAMLINK_TWITCH_DISABLE_RERUNS;
    }

    public static String getStreamlinkAdditionalOptions() {
        return checkValue(VALUE_STREAMLINK_ADDITIONAL_OPTIONS, KEY_STREAMLINK_ADDITIONAL_OPTIONS);
    }

    // // FFmpeg

    public static String getFfmpegPath() {
        return checkValue(VALUE_FFMPEG_PATH, KEY_FFMPEG_PATH);
    }

    public static String getFfmpegOptions() {
        return checkValue(VALUE_FFMPEG_OPTIONS, KEY_FFMPEG_OPTIONS);
    }

    // // Twitch

    public static String getTwitchClientId() {
        return checkValue(VALUE_TWITCH_CLIENT_ID, KEY_TWITCH_CLIENT_ID);
    }

    public static String getTwitchClientSecret() {
        return checkValue(VALUE_TWITCH_CLIENT_SECRET, KEY_TWITCH_CLIENT_SECRET);
    }

    public static List<String> getTwitchUserIds() {
        if (TWITCH_USER_IDS == null) {
            TWITCH_USER_IDS = List.of(checkArrayValue(VALUE_TWITCH_USER_IDS, KEY_TWITCH_USER_IDS).trim().split("\\s*,\\s*"));
        }
        return TWITCH_USER_IDS;
    }

    public static List<String> getTwitchUserLogins() {
        if (TWITCH_USER_LOGINS == null) {
            TWITCH_USER_LOGINS = List.of(checkArrayValue(VALUE_TWITCH_USER_LOGINS, KEY_TWITCH_USER_LOGINS).trim().split("\\s*,\\s*"));
        }
        return TWITCH_USER_LOGINS;
    }

    // // Open Streaming Platform

    public static String getOSPApiProtocol() {
        return checkValue(VALUE_OSP_API_PROTOCOL, KEY_OSP_API_PROTOCOL);
    }

    public static String getOSPApiHost() {
        return checkValue(VALUE_OSP_API_HOST, KEY_OSP_API_HOST);
    }

    public static Integer getOSPApiPort() {
        if (OSP_API_PORT == null && VALUE_OSP_API_PORT != null) {
            OSP_API_PORT = Integer.parseInt(checkValue(VALUE_OSP_API_PORT, KEY_OSP_API_PORT));
        }
        return OSP_API_PORT;
    }

    public static String getOSPApiPath() {
        return checkValue(VALUE_OSP_API_PATH, KEY_OSP_API_PATH);
    }

    public static String getOSPApiVersion() {
        return checkValue(VALUE_OSP_API_VERSION, KEY_OSP_API_VERSION);
    }

    public static String getOSPApiKey() {
        return checkValue(VALUE_OSP_API_KEY, KEY_OSP_API_KEY);
    }

    public static String getOSPRtmpHost() {
        return checkValue(VALUE_OSP_RTMP_HOST, KEY_OSP_RTMP_HOST);
    }

    public static Integer getOSPRtmpPort() {
        if (OSP_RTMP_PORT == null && VALUE_OSP_RTMP_PORT != null) {
            OSP_RTMP_PORT = Integer.parseInt(checkValue(VALUE_OSP_RTMP_PORT, KEY_OSP_RTMP_PORT));
        }
        return OSP_RTMP_PORT;
    }

    public static String getOSPStreamKey() {
        return checkValue(VALUE_OSP_STREAM_KEY, KEY_OSP_STREAM_KEY);
    }

    private Config() {
    }

}
