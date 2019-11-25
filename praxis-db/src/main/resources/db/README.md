# Database Migration
Database migration scripts for the Praxis database.

The scripts are managed via [Flyway](https://www.flywaydb.org).

## Folder Structure

- [data](data) - Per environment configurations of data that needs to be added to the database on initial creation.
- [migration](migration) - Versioned DDL scripts to create and migrate the database to the latest version.
