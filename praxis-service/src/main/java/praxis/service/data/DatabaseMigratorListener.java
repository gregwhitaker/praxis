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
