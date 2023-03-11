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

public class Config {

    private static final Logger logger = LogManager.getLogger();

    // Keys
    public static final String KEY_TWITCH_CLIENT_ID = "TWITCH_CLIENT_ID";
    public static final String KEY_TWITCH_CLIENT_SECRET = "TWITCH_CLIENT_SECRET";
    public static final String KEY_TWITCH_USER_IDS = "TWITCH_USER_IDS";
    public static final String KEY_TWITCH_USER_LOGINS = "TWITCH_USER_LOGINS";

    // Default Values
    private static final String DEFAULT_TWITCH_CLIENT_ID = null;
    private static final String DEFAULT_TWITCH_CLIENT_SECRET = null;
    private static final String DEFAULT_TWITCH_USER_IDS = "";
    private static final String DEFAULT_TWITCH_USER_LOGINS = "";

    // Environment Values
    private static final String ENV_TWITCH_CLIENT_ID = System.getenv(KEY_TWITCH_CLIENT_ID);
    private static final String ENV_TWITCH_CLIENT_SECRET = System.getenv(KEY_TWITCH_CLIENT_SECRET);
    private static final String ENV_TWITCH_USER_IDS = System.getenv(KEY_TWITCH_USER_IDS);
    private static final String ENV_TWITCH_USER_LOGINS = System.getenv(KEY_TWITCH_USER_LOGINS);

    // Values
    private static final String VALUE_TWITCH_CLIENT_ID = ENV_TWITCH_CLIENT_ID != null ? ENV_TWITCH_CLIENT_ID : DEFAULT_TWITCH_CLIENT_ID;
    private static final String VALUE_TWITCH_CLIENT_SECRET = ENV_TWITCH_CLIENT_SECRET != null ? ENV_TWITCH_CLIENT_SECRET : DEFAULT_TWITCH_CLIENT_SECRET;
    private static final String VALUE_TWITCH_USER_IDS = ENV_TWITCH_USER_IDS != null ? ENV_TWITCH_USER_IDS : DEFAULT_TWITCH_USER_IDS;
    private static final String VALUE_TWITCH_USER_LOGINS = ENV_TWITCH_USER_LOGINS != null ? ENV_TWITCH_USER_LOGINS : DEFAULT_TWITCH_USER_LOGINS;

    // Runtime Values
    private static String[] TWITCH_USER_IDS = null;
    private static String[] TWITCH_USER_LOGINS = null;

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

    public static String getTwitchClientId() {
        return checkValue(VALUE_TWITCH_CLIENT_ID, KEY_TWITCH_CLIENT_ID);
    }

    public static String getTwitchClientSecret() {
        return checkValue(VALUE_TWITCH_CLIENT_SECRET, KEY_TWITCH_CLIENT_SECRET);
    }

    public static String[] getTwitchUserIds() {
        if (TWITCH_USER_IDS == null) {
            TWITCH_USER_IDS = checkArrayValue(VALUE_TWITCH_USER_IDS, KEY_TWITCH_USER_IDS).trim().split("\\s*,\\s*");
        }
        return TWITCH_USER_IDS;
    }

    public static String[] getTwitchUserLogins() {
        if (TWITCH_USER_LOGINS == null) {
            TWITCH_USER_LOGINS = checkArrayValue(VALUE_TWITCH_USER_LOGINS, KEY_TWITCH_USER_LOGINS).trim().split("\\s*,\\s*");
        }
        return TWITCH_USER_LOGINS;
    }

    private Config() {
    }

}
