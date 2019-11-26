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
package praxis.service.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import praxis.db.DatabaseMigrator;

import javax.sql.DataSource;

/**
 * Application listener responsible for running a database migration on app startup.
 */
@Component
public class DatabaseMigratorListener implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger LOG = LoggerFactory.getLogger(DatabaseMigratorListener.class);

    private final DatabaseMigrator databaseMigrator;

    @Value("${praxis.database.migrateonstartup:false}")
    private boolean migrateOnStartup;

    private Environment env;

    @Autowired
    public DatabaseMigratorListener(Environment env, DataSource dataSource) {
        this.env = env;
        this.databaseMigrator = new DatabaseMigrator(dataSource);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (migrateOnStartup) {
            LOG.info("Running database migration...");

            if (env.getActiveProfiles().length > 0) {
                // We are only expecting a single profile to be active at any given time
                databaseMigrator.run(env.getActiveProfiles()[0]);
            } else {
                databaseMigrator.run(null);
            }
        } else {
            LOG.debug("Not running database migration because property 'praxis.database.migrateonstartup' is 'false'");
        }
    }
}
