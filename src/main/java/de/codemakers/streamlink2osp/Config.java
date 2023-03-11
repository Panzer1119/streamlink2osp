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

public class Config {

    // Keys
    public static final String KEY_TWITCH_CLIENT_ID = "TWITCH_CLIENT_ID";
    public static final String KEY_TWITCH_CLIENT_SECRET = "TWITCH_CLIENT_SECRET";

    // Default Values
    private static final String DEFAULT_TWITCH_CLIENT_ID = null;
    private static final String DEFAULT_TWITCH_CLIENT_SECRET = null;

    // Environment Values
    private static final String ENV_TWITCH_CLIENT_ID = System.getenv(KEY_TWITCH_CLIENT_ID);
    private static final String ENV_TWITCH_CLIENT_SECRET = System.getenv(KEY_TWITCH_CLIENT_SECRET);

    // Values
    private static final String VALUE_TWITCH_CLIENT_ID = ENV_TWITCH_CLIENT_ID != null ? ENV_TWITCH_CLIENT_ID : DEFAULT_TWITCH_CLIENT_ID;
    private static final String VALUE_TWITCH_CLIENT_SECRET = ENV_TWITCH_CLIENT_SECRET != null ? ENV_TWITCH_CLIENT_SECRET : DEFAULT_TWITCH_CLIENT_SECRET;

    private static String checkValue(String configValue, String configKey) {
        if (configValue == null || configValue.isEmpty()) {
            throw new RuntimeException(configKey + " is null or empty!");
        }
        return configValue;
    }

    public static String getTwitchClientId() {
        return checkValue(VALUE_TWITCH_CLIENT_ID, KEY_TWITCH_CLIENT_ID);
    }

    public static String getTwitchClientSecret() {
        return checkValue(VALUE_TWITCH_CLIENT_SECRET, KEY_TWITCH_CLIENT_SECRET);
    }

    private Config() {
    }

}
