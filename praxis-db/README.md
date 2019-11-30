# praxis-db
Sets up the PostgreSQL database required by Praxis.

The database migration can be run as a standalone process for development purposes. However, when using the [praxis-service](../praxis-service), it
will be automatically run on application startup.

## Standalone Configuration
The database migrator supports multiple methods of configuration when running as a standalone migration application:

1. Environment Variables
2. System Properties
3. Command Line Arguments

Each successive configuration method overrides the ones before it (ie. command line arguments override everything)

### Environment Variables
The following environment variables can be used to configure the database migrator:

| Variable | Description |
|----------|-------------|
| DB_JDBC_URL | Database JDBC connection URL |
| DB_USERNAME | Database username |
| DB_PASSWORD | Database password |
| DB_ENV      | Database environment name (controls which initial data is loaded)

### System Properties
The following system properties can be used to configure the database migrator:

| Variable | Description |
|----------|-------------|
| db.jdbcUrl | Database JDBC connection URL |
| db.username | Database username |
| db.password | Database password |
| db.env      | Database environment name (controls which initial data is loaded)

### Command Line Arguments
The following command line arguments can be used to configure the database migrator:
        
| Variable | Description |
|----------|-------------|
| --jdbcUrl | Database JDBC connection URL |
| --username | Database username |
| --password | Database password |
| --env      | Database environment name (controls which initial data is loaded)

## Embedded Configuration
This is the database migration method that is configured for the [praxis-service]. On startup the service will automatically
run this database migration, if needed, based on the configuration of the service.

## Development

### Migrate Database to Latest Version
Run the following command to migrate the database to the latest version during development:

    ./gradlew flywayMigrate
    
### Drop Database
Run the following command to drop the database during development:

    ./gradlew flywayClean
