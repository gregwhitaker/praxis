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
package praxis.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import praxis.db.config.DatabaseMigratorConfig;

import javax.sql.DataSource;

/**
 * Handles versioning and setup of the praxis database.
 */
public final class DatabaseMigrator {
    private static final Logger LOG = LoggerFactory.getLogger(DatabaseMigrator.class);

    private final DataSource dataSource;

    /**
     * Run a database migration from the command line.
     *
     * @param args command-line arguments
     */
    public static void main(String... args) {
        final DatabaseMigratorConfig config = DatabaseMigratorConfig.get(args);

        // Configure Datasource
        final HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(config.getJdbcUrl());
        hikariConfig.setUsername(config.getUsername());

        if (config.getPassword() != null) {
            hikariConfig.setPassword(config.getPassword());
        }

        LOG.info("Running command-line database migration for: {}", config.getJdbcUrl());

        // Start Migration
        DatabaseMigrator migrator = new DatabaseMigrator(new HikariDataSource(hikariConfig));
        migrator.run(config.getEnvironment());
    }

    public DatabaseMigrator(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Run database migration.
     *
     * @param env migration environment name or <code>null</code> if no environment is desired
     */
    public void run(String env) {
        String[] locations;
        if (env == null || env.isEmpty()) {
            // No environment specified so just run the standard migration
            locations = new String[]{"classpath:/db/migration"};
        } else {
            locations = new String[]{"classpath:/db/migration", "classpath:/db/data/" + env.toLowerCase()};
        }

        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .baselineOnMigrate(false)
                .locations(locations)
                .load();

        flyway.migrate();
    }
}
