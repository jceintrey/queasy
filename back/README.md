# Queasy App back

The Backend is based on Java21 using the SpringBoot 3.4.x Framework. It is a standard structured Rest Api application.
It uses

* Spring Security Framework manage authentication and authorizations to api endpoints
* OAuth2 Resssource Server Library to handle jwt operations
* Lombok to manage boilerplate code
* modelMapper library to handle conversions between DTOs and Entities
* jpa/hibernate as ORM for data persistence

# Setup

## Prerequisites

* Maven
* Java 21
* Mysql Server

## Install the application

### Clone the repository

```bash
git clone git@github.com:jceintrey/queasy.git
```

### Install dependencies

Go to the project folder

```bash
cd queasy/back
```

Install the project

```bash
mvn clean install
```

If there is a database connection error, the build will fail. You can skip the database access tests and come back to them later.

## Install and prepare the database

Once Mysql Server is installed, you have to configure a RW user for the application and adapt application.properties in back/src/main/resources/ to fit the databasename, username and password.

```bash
- QUEASY_DBURL
- QUEASY_DBUSER
- QUEASY_DBKEY
```

```bash
create database queasy;
CREATE USER 'queasy'@'%' IDENTIFIED BY 'heresetastrongpassword';
GRANT ALL PRIVILEGES ON queasy.* TO 'queasy'@'%';
flush privileges;
```

## Running the application

To run the backend application,

```bash
mvn spring-boot:run
```

## Documentation

API is well documented at http://localhost:8080/swagger-ui/index.html
