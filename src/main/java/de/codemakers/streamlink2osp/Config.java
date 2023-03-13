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
    // // Twitch
    private static List<String> TWITCH_USER_IDS = null;
    private static List<String> TWITCH_USER_LOGINS = null;

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

    public static String getOSPApiPort() {
        return checkValue(VALUE_OSP_API_PORT, KEY_OSP_API_PORT);
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

    public static String getOSPRtmpPort() {
        return checkValue(VALUE_OSP_RTMP_PORT, KEY_OSP_RTMP_PORT);
    }

    public static String getOSPStreamKey() {
        return checkValue(VALUE_OSP_STREAM_KEY, KEY_OSP_STREAM_KEY);
    }

    private Config() {
    }

}
