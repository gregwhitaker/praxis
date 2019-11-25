/**
 * Copyright 2019 Greg Whitaker
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package praxis.db.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;

import static picocli.CommandLine.Option;

/**
 * Configuration source that retrieves configuration from command line parameters.
 */
public class CommandLineConfigSource implements ConfigSource {
    private static final Logger LOG = LoggerFactory.getLogger(CommandLineConfigSource.class);

    private final String[] args;

    public CommandLineConfigSource(String... args) {
        this.args = args;
    }

    @Override
    public void resolve(DatabaseMigratorConfig config) {
        if (args != null) {
            LOG.debug("Resolving configuration properties via command line arguments");

            // Parse Command-Line Arguments
            CommandLineArgs parsedConfig = CommandLine.populateCommand(new CommandLineArgs(), args);

            if (parsedConfig.jdbcUrl != null && !parsedConfig.jdbcUrl.isEmpty()) {
                config.setJdbcUrl(parsedConfig.jdbcUrl);
            }

            if (parsedConfig.username != null && !parsedConfig.username.isEmpty()) {
                config.setUsername(parsedConfig.username);
            }

            if (parsedConfig.password != null && !parsedConfig.password.isEmpty()) {
                config.setPassword(parsedConfig.password);
            }

            if (parsedConfig.env != null && !parsedConfig.env.isEmpty()) {
                config.setEnvironment(parsedConfig.env);
            }
        }
    }

    /**
     * DatabaseMigrator command line arguments.
     */
    public static class CommandLineArgs {

        @Option(names = { "--jdbcUrl" }, description = "Database jdbc connection url")
        public String jdbcUrl;

        @Option(names = { "--username" }, description = "Database username")
        public String username;

        @Option(names = { "--password" }, description = "Database password")
        public String password;

        @Option(names = { "--env" }, description = "Migration environment name")
        public String env;
    }
}
