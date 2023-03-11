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
import de.codemakers.streamlink2osp.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TwitchAPI {

    private static final Logger logger = LogManager.getLogger();

    private static final CredentialManager credentialManager;
    private static final TwitchClient twitchClient;
    private static final TwitchHelix twitchHelix;

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
    }

    private static void checkValues() {
        // Check if Twitch user ids and logins together are more than 100
        if (Config.getTwitchUserIds().size() + Config.getTwitchUserLogins().size() > 100) {
            throw new IllegalArgumentException("Twitch user ids and logins together are more than 100. For more information see https://dev.twitch.tv/docs/api/reference#get-users");
        }
    }

    private static void close() {
        try {
            if (twitchClient == null) {
                logger.warn("TwitchClient is null, so it can't be closed");
                return;
            }
            logger.info("Closing TwitchClient");
            twitchClient.close();
            logger.info("Closed TwitchClient");
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

    private TwitchAPI() {
    }

}
