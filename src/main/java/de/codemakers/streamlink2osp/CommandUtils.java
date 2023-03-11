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

import org.apache.commons.exec.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class CommandUtils {

    private static final Logger logger = LogManager.getLogger();

    public static final String COMMAND_STREAMLINK = "streamlink";
    public static final String COMMAND_FFMPEG = "ffmpeg";

    static {
        checkCommandAvailable(COMMAND_STREAMLINK);
        checkCommandAvailable(COMMAND_FFMPEG);
    }

    public static boolean checkCommandAvailable(String command) {
        if (command == null || command.isEmpty()) {
            throw new IllegalArgumentException("Command is null or empty!");
        }
        // Determine the command syntax based on the operating system
        // Create a command line object to represent the "where" or "which" command
        final CommandLine commandLine = new CommandLine((OS.isFamilyWindows()) ? "where" : "which");
        // Add the command to be checked as an argument to the "where" or "which" command
        commandLine.addArgument(command);
        // Create a new DefaultExecutor
        final Executor executor = new DefaultExecutor();
        try {
            // Set a StreamHandler that suppresses the output of the command
            executor.setStreamHandler(new PumpStreamHandler(null, null, null));
            // Execute the "where" or "which" command to check if the command is available on the system
            final int exitValue = executor.execute(commandLine);
            if (exitValue == 0) {
                return true;
            }
        } catch (ExecuteException ex) {
            //logger.error("ExecuteException occurred", ex);
        } catch (IOException ex) {
            logger.error("IOException occurred", ex);
        }
        throw new RuntimeException("Command \"" + command + "\" is not available on this system!");
    }

    private CommandUtils() {
    }

}
