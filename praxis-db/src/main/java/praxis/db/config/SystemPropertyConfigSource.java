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

import com.sun.tools.javac.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemPropertyConfigSource implements ConfigSource {
    private static final Logger LOG = LoggerFactory.getLogger(SystemPropertyConfigSource.class);

    /**
     * Enumeration of configuration environment variables.
     */
    public enum SystemProps {
        DB_JDBC_URL("db.jdbcUrl"),
        DB_USERNAME("db.username"),
        DB_PASSWORD("db.password"),
        DB_ENV("db.env");

        private final String value;

        SystemProps(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    @Override
    public void resolve(DatabaseMigratorConfig config) {
        Assert.checkNonNull(config);

        LOG.debug("Resolving configuration properties via system properties");

        if (System.getenv(SystemProps.DB_JDBC_URL.getValue()) != null) {
            config.setJdbcUrl(System.getenv(EnvironmentConfigSource.EnvironmentVars.DB_JDBC_URL.getValue()));
        }

        if (System.getenv(SystemProps.DB_USERNAME.getValue()) != null) {
            config.setJdbcUrl(System.getenv(EnvironmentConfigSource.EnvironmentVars.DB_USERNAME.getValue()));
        }

        if (System.getenv(SystemProps.DB_PASSWORD.getValue()) != null) {
            config.setJdbcUrl(System.getenv(EnvironmentConfigSource.EnvironmentVars.DB_PASSWORD.getValue()));
        }

        if (System.getenv(SystemProps.DB_ENV.getValue()) != null) {
            config.setJdbcUrl(System.getenv(EnvironmentConfigSource.EnvironmentVars.DB_ENV.getValue()));
        }
    }
}
