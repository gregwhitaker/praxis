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

import static picocli.CommandLine.Option;

/**
 * Configuration source that retrieves configuration from command line parameters.
 */
public class CommandLineConfigSource extends BaseConfigSource {
    private static final Logger LOG = LoggerFactory.getLogger(CommandLineConfigSource.class);

    @Override
    public void resolve(DatabaseMigratorConfig config) {

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
