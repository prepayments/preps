[![Build Status](https://travis-ci.org/prepayments/preps.svg?branch=master)](https://travis-ci.org/prepayments/preps)

# PREPS

##### Prepayments Management System

This is an application for managing prepayment accounts and their related expenses for enterprises.

### Development

This system has been build on java's spring framework and angular for the front end client.
It also leans heavily on kafka streams and netflix oss for configuration and service discovery
though it is designed primarily as a monolith.

Work is still on going to fully containerize deployments but in the meantime with the exception
of a working postgresql database the dependencies are expected to run on docker.
The main setup of the application as well as registration is based on jhipster.

The main app is otherwise deployed as a jar in java 8 environment

#### Installation

This procedure is manual and so if non-container deployment makes you break out in hives
kindly skip this part...

It is expected that you environment contains the following dependencies running

- Postgresql Database on port 5432
- Zookeeper
- Kafka
- JHipster registry

###### Environment Varables

The following variabes will be required at a minimum :

- PG_DATABASE_DEV_USER
- PG_DATABASE_DEV_PASSWORD

The above indicate that the app is still in development phase if anything as most of the fields
and tables are subject to change subject to the delopers assessment of the application user's
requirement.

Assuming the above is up and running all you have to do is run the jar :

    java -jar preps-${VERSION}.jar

The application runs on port 11001

### Acknowledgement

This app is designed primarily with open source libraries and software. Among others I would
not forget to mention the following :

- Jetbrains team; Intellij idea is such a great IDE
- JHipster team; thanks guys
- Netflix OSS developers, been of such great help
- Pivotal Inc, spring framework is an amazing tool
- Angular developers, may the framework live on...
