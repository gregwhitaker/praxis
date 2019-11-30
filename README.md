# praxis
Service and clients for generating and ingesting application usage data.

Provides a framework for ingesting and processing application heartbeats and monitoring how long a customer is using your
application that is deployed in their infrastructure (ie. ingest phone home events).

## Project Structure
This repository contains the following projects:

- [praxis-db](praxis-db) - Sets up the PostgreSQL database required by Praxis.
- [praxis-service](praxis-service) - Service that ingests and processes events.
- [praxis-java](praxis-java) - Praxis client for Java applications.
- [demo](demo) - Simple demo application that sends events to Praxis.

## Building Praxis
Run the following command to build Praxis as a set of Docker images:

    ./gradlew clean buildImage

## Bugs and Feedback
For bugs, questions, and discussions please use the [Github Issues](https://github.com/gregwhitaker/praxis/issues).

## License
Copyright 2019 Greg Whitaker

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.