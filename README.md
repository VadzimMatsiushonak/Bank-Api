# Bank-Api

## Description

The project is an example of a banking API that contains all the functions related to storing data and providing an API
to work with it.

## Useful links

- [Notion](https://www.notion.so/BankApi-4cd52d55c7204482a6f364f1b7687c5e)
- [Github](https://github.com/VadzimMatsiushonak/Bank-Api)
- [Gitlab](https://gitlab.com/vadzim.matsiushonak/bank-api)
- [ClickUp](https://app.clickup.com/43295014/v/b/6-386791473-2)
- [Toggl timer](https://track.toggl.com/timer)
- [BankApi model](https://app.sqldbm.com/MySQL/Edit/p237658/)

## Installation

1. Download the source project from [GitHub](https://github.com/VadzimMatsiushonak/Bank-Api.git)
2. Run command to build the project ```./gradlew clean build```

## Startup

### Locally

1. Start the project by running BankApiApplication main method or using ```./gradlew bootRun```
2. Open [Swagger](http://localhost:8080/swagger-ui/) to access Api GUI

### <a id="StartupDocker"></a> Docker

**Image to run build files**

1. Run ```./gradlew clean build``` to build jar file
2. Run ```docker build -t asimx/bank-api -f docker/Dockerfile .``` to build docker image
3. Run ```docker run -p 8080:8080 asimx/bank-api``` to launch docker container

**Image to build and run source files**

1. Run ```docker build -t asimx/bank-api -f docker/source/Dockerfile .``` to build docker image
2. Run ```docker run -p 8080:8080 asimx/bank-api-latest``` to launch docker container

## Usage of different Databases

### MySQL

#### Locally

1. Install MySQL on your OS
2. Set 'local-mysql' as active profile
    - in IDE change Run/Debug configurations, set active profiles to local-mysql
    - in Gradle ./gradlew bootRun -Dspring.profiles.active=local-mysql

#### Docker

1. Create docker network ```docker network create mysqlnet```
2. Run your mysql instance
    ```
    docker run -it --rm -d \
    --network mysqlnet \
    --name mysqlserver \
    -e MYSQL_USER=admin -e MYSQL_PASSWORD=pass \
    -e MYSQL_ROOT_PASSWORD=pass -e MYSQL_DATABASE=bank-api-liquibase \
    -p 3306:3306 mysql:8.0
    ```
3. Build your Docker image **[Startup->Docker](#StartupDocker)**
4. Run your container and set 'docker-mysql' as active profile
    ```
    docker run --network mysqlnet -d -p 8080:8080 -e "SPRING_PROFILES_ACTIVE=docker-mysql" {bank-api-image}
    ```

### PostgreSQL

#### Locally

1. Install PostgreSQL on your OS
2. Set 'local-psql' as active profile
    - in IDE change Run/Debug configurations, set active profiles to local-psql
    - in Gradle ./gradlew bootRun -Dspring.profiles.active=local-psql

#### Docker

1. Create docker network ```docker network create psqlnet```
2. Run your mysql instance
    ```
    docker run -it --rm -d \
    --network psqlnet \
    --name psqlserver \
    -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=pass  \
    -e POSTGRES_DB=bank-api-liquibase \
    -p 5432:5432 postgres:13.1-alpine
    ```
3. Build your Docker image **[Startup->Docker](#StartupDocker)**
4. Run your container and set 'docker-psql' as active profile
    ```
    docker run --network psqlnet -d -p 8080:8080 -e "SPRING_PROFILES_ACTIVE=docker-psql" {bank-api-image}
    ```

## Environment

### Profiles

##### default - application.yaml

Runs default environment

##### local-mysql - application-local-mysql.yaml

Runs environment with MySql installed on your OS

##### docker-mysql - application-docker-mysql.yaml

Runs environment with MySql running in Docker

### Properties

#### OAuth2 security properties

##### bank-api.security:

**client-id** - client id for grant_type=client_credentials flow \
**client-secret** - client secret for grant_type=client_credentials flow \
**redirect-uris** - allowed redirect URLs for grant_type=authorization_code flow

## Docker

### Dockerfile's

**docker/Dockerfile** - Image to run build files
**docker/source/Dockerfile** - Image to build and run source files

## Available Requests flow

1. Initiate payment

## Task Flow

1. Create/Get task in ClickUp
2. Assign it to yourself
3. Change the status to In Progress
4. Track time with ClickUp
5. Connect GitHub PR to ClickUp task
6. Merge the PR and change status to 'In Test' or 'Solved' (if tests are provided in the task)

## Git Flow

1. PR should have at least 1 reviewer
2. Use only squash and merge

## Naming

- PR
    - Title should have (tag) [task ID] task title
    - Example: (feat) [BA-1] Setup project structure
- Commits
    - The same rules as PR
- Task IDs
    - [taskType taskID]
    - current task types: BA - Backend, FE - Frontend, AD - Android
- Tags
    - feature, bug, tests, docs

## Api flows

### CRUD

- CRUD request available on [Swagger](http://localhost:8080/swagger-ui/) with Admin role privileges

### Auth

### Payment

- Payment initiation _**/api/v1/payments/initiatePayment**_

### Account