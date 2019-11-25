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

/**
 * Configuration source that retrieves values from environment variables.
 */
public class EnvironmentConfigSource implements ConfigSource {
    private static final Logger LOG = LoggerFactory.getLogger(EnvironmentConfigSource.class);

    /**
     * Enumeration of configuration environment variables.
     */
    public enum EnvironmentVars {
        DB_JDBC_URL("DB_JDBC_URL"),
        DB_USERNAME("DB_USERNAME"),
        DB_PASSWORD("DB_PASSWORD"),
        DB_ENV("DB_ENV");

        private final String value;

        EnvironmentVars(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    @Override
    public void resolve(final DatabaseMigratorConfig config) {
        LOG.debug("Resolving configuration properties via environment variables");

        if (System.getenv(EnvironmentVars.DB_JDBC_URL.getValue()) != null) {
            config.setJdbcUrl(System.getenv(EnvironmentVars.DB_JDBC_URL.getValue()));
        }

        if (System.getenv(EnvironmentVars.DB_USERNAME.getValue()) != null) {
            config.setJdbcUrl(System.getenv(EnvironmentVars.DB_USERNAME.getValue()));
        }

        if (System.getenv(EnvironmentVars.DB_PASSWORD.getValue()) != null) {
            config.setJdbcUrl(System.getenv(EnvironmentVars.DB_PASSWORD.getValue()));
        }

        if (System.getenv(EnvironmentVars.DB_ENV.getValue()) != null) {
            config.setJdbcUrl(System.getenv(EnvironmentVars.DB_ENV.getValue()));
        }
    }
}
