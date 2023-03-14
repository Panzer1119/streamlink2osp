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

package de.codemakers.streamlink2osp.apis;

import com.github.philippheuer.credentialmanager.CredentialManager;
import com.github.philippheuer.credentialmanager.CredentialManagerBuilder;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.github.twitch4j.auth.providers.TwitchIdentityProvider;
import com.github.twitch4j.helix.TwitchHelix;
import com.github.twitch4j.helix.domain.User;
import com.github.twitch4j.helix.domain.UserList;
import de.codemakers.streamlink2osp.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TwitchAPI {

    private static final Logger logger = LogManager.getLogger(TwitchAPI.class);

    private static final CredentialManager credentialManager;
    private static final TwitchClient twitchClient;
    private static final TwitchHelix twitchHelix;

    private static final List<User> twitchUsers = new ArrayList<>();

    static {
        checkValues();
        logger.info("Initializing TwitchClient");
        final TwitchIdentityProvider twitchIdentityProvider = new TwitchIdentityProvider(Config.getTwitchClientId(), Config.getTwitchClientSecret(), "");
        logger.debug("Setting up CredentialManager with TwitchIdentityProvider: {}", twitchIdentityProvider);
        credentialManager = CredentialManagerBuilder.builder().build();
        credentialManager.registerIdentityProvider(twitchIdentityProvider);
        logger.debug("Set up CredentialManager: {}", credentialManager);
        logger.debug("Setting up TwitchClient with CredentialManager: {}", credentialManager);
        twitchClient = TwitchClientBuilder.builder()
                .withCredentialManager(credentialManager)
                .withClientId(Config.getTwitchClientId())
                .withClientSecret(Config.getTwitchClientSecret())
                .withEnableHelix(true)
                .build();
        Runtime.getRuntime().addShutdownHook(new Thread(TwitchAPI::close));
        logger.debug("Set up TwitchClient: {}", twitchClient);
        twitchHelix = twitchClient.getHelix();
        logger.info("Initialized TwitchClient");
        init();
    }

    private static void checkValues() {
        // Check if no Twitch user was specified
        if (Config.getTwitchUserIds().isEmpty() && Config.getTwitchUserLogins().isEmpty()) {
            throw new IllegalArgumentException("No Twitch user was specified.");
        }
        // Print a warning if more than one Twitch user was specified, because adding or removing one would need a restart, which kills all running streams
        if (Config.getTwitchUserIds().size() + Config.getTwitchUserLogins().size() > 1) {
            logger.warn("More than one Twitch user was specified. Adding or removing one would need a restart, which kills all running streams.");
        }
        // Check if Twitch user ids and logins together are more than 100
        if (Config.getTwitchUserIds().size() + Config.getTwitchUserLogins().size() > 100) {
            throw new IllegalArgumentException("Twitch user ids and logins together are more than 100. For more information see https://dev.twitch.tv/docs/api/reference#get-users");
        }
    }

    private static void init() {
        logger.info("Initializing TwitchAPI");
        // Get Twitch users
        logger.debug("Getting Twitch users");
        final UserList userList = twitchHelix.getUsers(null, Config.getTwitchUserIds(), Config.getTwitchUserLogins()).execute();
        twitchUsers.addAll(userList.getUsers());
        // Log Twitch user display names
        logger.debug("Got Twitch users: {}", twitchUsers.stream()
                .map(User::getDisplayName)
                .sorted()
                .map(name -> "\"" + name + "\"")
                .collect(Collectors.joining(", ")));
        checkUsers();
        logger.info("Initialized TwitchAPI");
    }

    private static void checkUsers() {
        // Check if Twitch users are empty
        if (twitchUsers.isEmpty()) {
            throw new IllegalArgumentException("No Twitch users found.");
        }
        // Check if a Twitch user login is null and log the user
        twitchUsers.stream().filter(user -> user.getLogin() == null).forEach(user -> logger.warn("Twitch user login is null: {}", user));
        // Map Twitch user logins to multiple Twitch users
        final Map<String, List<User>> loginUsersMap = twitchUsers.stream().collect(Collectors.groupingBy(User::getLogin));
        // Remove all Twitch user logins that are not mapped to multiple Twitch users
        loginUsersMap.entrySet().removeIf(entry -> entry.getValue().size() <= 1);
        // Log all Twitch user logins that are mapped to multiple Twitch users
        loginUsersMap.forEach(TwitchAPI::logDuplicateUsers);
    }

    private static void logDuplicateUsers(String login, List<User> users) {
        final List<String> userStrings = users.stream().map(TwitchAPI::formatDuplicateUser).toList();
        logger.warn("Twitch user login \"{}\" is mapped to multiple Twitch users:\n{}", login, String.join("\n", userStrings));
    }

    private static String formatDuplicateUser(User user) {
        final String id = user.getId();
        final String displayName = user.getDisplayName();
        final Instant createdAt = user.getCreatedAt();
        final String broadcasterType = user.getBroadcasterType();
        final String description = user.getDescription();
        final int viewCount = user.getViewCount() == null ? -1 : user.getViewCount();
        return String.format("ID: \"%s\", Display Name: \"%s\", Created at: \"%s\", Broadcaster Type: \"%s\", View Count: %d, Description: \"%s\"", id, displayName, createdAt, broadcasterType, viewCount, description);
    }

    private static void close() {
        try {
            if (twitchClient == null) {
                logger.warn("TwitchClient is null, so it can't be closed");
                return;
            }
            logger.debug("Closing TwitchClient");
            twitchClient.close();
            logger.debug("Closed TwitchClient");
        } catch (Exception ex) {
            logger.error("Failed to close TwitchClient", ex);
        }
    }

    public static CredentialManager getCredentialManager() {
        return credentialManager;
    }

    public static TwitchClient getTwitchClient() {
        return twitchClient;
    }

    public static TwitchHelix getTwitchHelix() {
        return twitchHelix;
    }

    public static List<User> getTwitchUsers() {
        return twitchUsers;
    }

    private TwitchAPI() {
    }

}
