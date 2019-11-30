# praxis-db
Sets up the PostgreSQL database required by Praxis.

The database migration can be run as a standalone process for development purposes. However, when using the [praxis-service](../praxis-service), it
will be automatically run on application startup.

## Migrate Database to Latest Version
Run the following command to migrate the database to the latest version during development:

    ./gradlew flywayMigrate
    
## Drop Database
Run the following command to drop the database during development:

    ./gradlew flywayClean
