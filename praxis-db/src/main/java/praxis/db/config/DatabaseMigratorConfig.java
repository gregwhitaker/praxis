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

import java.util.List;

/**
 * Configuration for the database migrator.
 *
 * This configuration object first attempts to resolve configuration items from command line parameters, if not found
 * there it checks for environment variables.
 */
public final class DatabaseMigratorConfig {
    private static final Logger LOG = LoggerFactory.getLogger(DatabaseMigratorConfig.class);

    /**
     * Gets the database migrator configuration.
     *
     * @return database migrator configuration
     */
    public static DatabaseMigratorConfig get() {
        getConfigSourceChain();
        return null;
    }

    /**
     * Gets the chain of config sources.
     *
     * @return a list containing the config sources to check in order
     */
    private static List<ConfigSource> getConfigSourceChain() {
        return null;
    }

    private String jdbcUrl;
    private String username;
    private String password;
    private String environment;

    private DatabaseMigratorConfig() {
        // Prevent direct instantiation
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    // Package scoped so config source implementations in chain have access, but
    // object is immutable once returned from builder
    void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getUsername() {
        return username;
    }

    // Package scoped so config source implementations in chain have access, but
    // object is immutable once returned from builder
    void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    // Package scoped so config source implementations in chain have access, but
    // object is immutable once returned from builder
    void setPassword(String password) {
        this.password = password;
    }

    public String getEnvironment() {
        return environment;
    }

    // Package scoped so config source implementations in chain have access, but
    // object is immutable once returned from builder
    void setEnvironment(String environment) {
        this.environment = environment;
    }
}
