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

import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ConfigTest {

    // Generate random values for the environment variables with RandomStringUtils
    private static final String TWITCH_CLIENT_ID = RandomStringUtils.randomAlphanumeric(32);
    private static final String TWITCH_CLIENT_SECRET = RandomStringUtils.randomAlphanumeric(32);
    private static final String TWITCH_USER_IDS = IntStream.range(0, 10).mapToObj(i -> RandomStringUtils.randomNumeric(8)).collect(Collectors.joining(","));
    private static final String TWITCH_USER_LOGINS = IntStream.range(0, 10).mapToObj(i -> RandomStringUtils.randomAlphanumeric(12)).collect(Collectors.joining(","));

    @BeforeAll
    static void setUp() {
        // Set the environment variables
        System.setProperty("TWITCH_CLIENT_ID", TWITCH_CLIENT_ID);
        System.setProperty("TWITCH_CLIENT_SECRET", TWITCH_CLIENT_SECRET);
        System.setProperty("TWITCH_USER_IDS", TWITCH_USER_IDS);
        System.setProperty("TWITCH_USER_LOGINS", TWITCH_USER_LOGINS);
    }

    @Test
    void getTwitchClientId() {
        // Check if the environment variable is set correctly
        assertEquals(TWITCH_CLIENT_ID, Config.getTwitchClientId());
    }

    @Test
    void getTwitchClientSecret() {
        // Check if the environment variable is set correctly
        assertEquals(TWITCH_CLIENT_SECRET, Config.getTwitchClientSecret());
    }

    @Test
    void getTwitchUserIds() {
        // Check if the environment variable is set correctly
        assertArrayEquals(TWITCH_USER_IDS.trim().split("\\s*,\\s*"), Config.getTwitchUserIds());
    }

    @Test
    void getTwitchUserLogins() {
        // Check if the environment variable is set correctly
        assertArrayEquals(TWITCH_USER_LOGINS.trim().split("\\s*,\\s*"), Config.getTwitchUserLogins());
    }

}
