# Bank-Api

## Description

The project is an example of a banking API that contains all the functions related to storing data and providing an API
to work with it.

README documentation consists of the following sections:
- **[Useful links](#UsefulLinks)**
- **[Installation](#Installation)**
- **[Startup](#Startup)**
- **[Docker](#Docker)**
- **[Databases](#Databases)**
- **[Environment](#Environment)**
- **[Kafka](#Kafka)**
- **[Available Requests flow](#AvailableRequestsFlow)**
- **[Task Flow](#TaskFlow)**
- **[Git Flow](#GitFlow)**
- **[Naming](#Naming)**
- **[Api flows](#ApiFlows)**

## <a id="UsefulLinks"></a> Useful links

- [Notion](https://www.notion.so/BankApi-4cd52d55c7204482a6f364f1b7687c5e)
- [Github](https://github.com/VadzimMatsiushonak/Bank-Api)
- [Gitlab](https://gitlab.com/vadzim.matsiushonak/bank-api)
- [ClickUp](https://app.clickup.com/43295014/v/b/6-386791473-2)
- [Toggl timer](https://track.toggl.com/timer)
- [BankApi model](https://app.sqldbm.com/MySQL/Edit/p237658/)

## <a id="Installation"></a> Installation

1. Download the source project from [GitHub](https://github.com/VadzimMatsiushonak/Bank-Api.git)
2. Run command to build the project ```./gradlew clean build```

## <a id="Startup"></a> Startup

### Note

Before running the application, you need to make sure that the services used by the application are running on the
machine:

- Database: **[Startup->PostgreSQL](#StartupPostgreSQL)** or **[Startup->MySQL](#StartupMySQL)**
- Message broker: **[Startup->Kafka](#StartupKafka)**

### Locally

1. Start the project by running BankApiApplication main method or using ```./gradlew bootRun```
2. Open [Swagger](http://localhost:8080/swagger-ui/) to access Api GUI

### <a id="StartupDocker"></a> Docker

**Image to run build files**

1. Run ```./gradlew clean build``` to build jar file
2. Run ```docker build --target build -t asimx/bank-api -f Dockerfile .``` to build docker image
3. Run ```docker run -p 8080:8080 asimx/bank-api``` to launch docker container

**Image to build and run source files**

1. Run ```docker build --target standalone -t asimx/bank-api -f Dockerfile .``` to build docker image
2. Run ```docker run -p 8080:8080 asimx/bank-api-latest``` to launch docker container

## <a id="Docker"></a> Docker

### Dockerfile's

**Dockerfile** - Image to build and application
**docker-compose.yml** - Compose file to start standalone application in docker environment 
**docker/kafka-compose.yml** -  Compose file to start kafka as standalone service in docker environment

## <a id="Databases"></a>  Databases

### <a id="StartupMySQL"></a> MySQL

#### Locally

1. Install MySQL on your OS
2. Set 'local-mysql' as active profile
    - in IDE change Run/Debug configurations, set active profiles to local-mysql
    - in Gradle ./gradlew bootRun -Dspring.profiles.active=local-mysql

#### Docker

##### Docker compose

1. Open docker-compose.yaml file and use MySql values

```
  environment:
    - SPRING_PROFILES_ACTIVE=docker-mysql

  depends_on:
    - mysqlserver
      
  mysql:
    container_name: mysqlserver
    environment:
      - MYSQL_USER=admin
      - MYSQL_PASSWORD=pass
      - MYSQL_ROOT_PASSWORD=pass
      - MYSQL_DATABASE=bank-api-liquibase
    ports:
      - '3306:3306'
    image: 'mysql:8.0'
```

2. Run ```docker-compose -f docker-compose.yml up -d --build```

##### Docker run

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

- Run your application locally **[Startup](#Startup)** with '_local-mysql_' as active profile
- Or Build your Docker image **[Startup->Docker](#StartupDocker)** and run your container with '_docker-mysql_' as
  active profile
    ```
    docker run --network mysqlnet -d -p 8080:8080 -e "SPRING_PROFILES_ACTIVE=docker-mysql" {bank-api-image}
    ```

### <a id="StartupPostgreSQL"></a> PostgreSQL

#### Locally

1. Install PostgreSQL on your OS
2. Set 'local-psql' as active profile
    - in IDE change Run/Debug configurations, set active profiles to local-psql
    - in Gradle ./gradlew bootRun -Dspring.profiles.active=local-psql

#### Docker

##### Docker compose

1. Open docker-compose.yaml file and use PostgreSQL values

```
  environment:
    - SPRING_PROFILES_ACTIVE=docker-psql

  depends_on:
    - psqlserver
      
  psqlserver:
    container_name: psqlserver
    environment:
      - POSTGRES_USER=admin
      - POSTGRES_PASSWORD=pass
      - POSTGRES_DB=bank-api-liquibase
    ports:
      - '5432:5432'
    image: 'postgres:13.1-alpine'
```

2. Run ```docker-compose -f docker-compose.yml up -d --build```

``` 
--build     Build images before starting containers
-f          To specify name and path of one or more Compose files
```

##### Docker run

1. Create docker network ```docker network create psqlnet```
2. Run your psql instance
    ```
    docker run -it --rm -d \
    --network psqlnet \
    --name psqlserver \
    -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=pass  \
    -e POSTGRES_DB=bank-api-liquibase \
    -p 5432:5432 postgres:13.1-alpine
    ```

- Run your application locally **[Startup](#Startup)** with '_local-psql_' as active profile
- Or Build your Docker image **[Startup->Docker](#StartupDocker)** and run your container with '_docker-psql_' as active
  profile
    ```
    docker run --network psqlnet -d -p 8080:8080 -e "SPRING_PROFILES_ACTIVE=docker-psql" {bank-api-image}
    ```

## <a id="Environment"></a> Environment

### Profiles

##### default - application.yaml

- Runs default environment

##### local-psql - application-local-psql.yaml

- Runs environment with PostgreSQL installed on your OS

##### local-mysql - application-local-mysql.yaml

- Runs environment with MySql installed on your OS

##### docker-psql - application-docker-psql.yaml

- Runs environment with PostgreSQL running in Docker

##### docker-mysql - application-docker-mysql.yaml

- Runs environment with MySql running in Docker

##### docker-kafka - application-docker-kafka.yaml

- Runs environment with Kafka running in Docker

### Properties

#### OAuth2 security properties

##### bank-api.security:

**client-id** - client id for grant_type=client_credentials flow \
**client-secret** - client secret for grant_type=client_credentials flow \
**redirect-uris** - allowed redirect URLs for grant_type=authorization_code flow

## <a id="Kafka"></a> Kafka

Before working with kafka it's required to check **[Quickstart documentation](https://kafka.apache.org/quickstart)**

### <a id="StartupKafka"></a> Startup

Kafka is already configured in the docker compose file, but you can also run it as a standalone service with the
examples below.

#### Locally

- **[Quickstart documentation](https://kafka.apache.org/quickstart)** can be used to run Kafka locally

#### Docker

- ./docker/kafka-compose.yml file can be used to run Kafka in Docker environment using below command

```
docker-compose -f docker/kafka-compose.yml up -d
```

### Available topics

- **logging** - used for test purposes to log messages

### Produce messages to kafka topic

```
bin/kafka-console-producer.sh --topic logging --bootstrap-server localhost:9092
```

### Consume messages from kafka topic

```
bin/kafka-console-consumer.sh --topic logging --bootstrap-server localhost:9092
```

### Testing

1. You need to make a POST request to the StatusRestController

```
curl -X POST "http://localhost:8080/api/v1/kafka/log/qwe" -H "accept: */*" -d ""
```

**OR**

```
curl -X POST "http://localhost:8080/api/v1/kafka/log" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"message\": \"string\", \"type\": \"ERROR\"}"
```

2. You will then receive messages in the LoggingListener in the format below

```
[{1}L|{2}p] : {3} : {4}

# Where:
# 1 - Listener number
# 2 - Partition number
# 3 - Log type (INFO|DEBUG|etc...)
# 4 - Message
```

## <a id="Sonar"></a> Sonar

### <a id="SonarStartup"></a> Startup steps

1. Start a SonarQube container by running the command:

```
docker run -d --name sonarqube -e SONAR_ES_BOOTSTRAP_CHECKS_DISABLE=true -p 9000:9000 sonarqube:latest
```

2. Access the SonarQube UI at `http://localhost:9000` and log in using the default
   credentials `admin` and `admin`. Change the password to something more secure.
3. Click on `Projects` and then `Manually` to create a new project.
4. Enter the project data: display name `Bank-Api` , project key `Bank-Api`, and branch name `main`.
5. Click `Use the global settings` and then `Create project`.
6. Go to your project and click on `Locally` to set up local analysis.
7. Enter `Bank-Api` as the token name and choose `No expiration`. Click `Generate` to create the
   token.
8. Copy the provided token and paste it into your `build.gradle` file under the `sonar.token`
   property, using the format `property "sonar.token", "sqp_..."`. For example:

```  
   property "sonar.token", "sqp_98fc7d274354574277612b666f49e9e76879bf6b"
```

9. Run the command `./gradlew test jacocoTestReport sonar` to run the tests, generate the test
   report, and analyze the code with SonarQube.
10. Once the tests have passed and the report has been generated, you can view the code analysis
    statistics on the project page in SonarQube.

## <a id="AvailableRequestsFlow"></a> Available Requests flow

1. Initiate payment

## <a id="TaskFlow"></a> Task Flow

1. Create/Get task in ClickUp
2. Assign it to yourself
3. Change the status to In Progress
4. Track time with ClickUp
5. Connect GitHub PR to ClickUp task
6. Merge the PR and change status to 'In Test' or 'Solved' (if tests are provided in the task)

## <a id="GitFlow"></a> Git Flow

1. PR should have at least 1 reviewer
2. Use only squash and merge

## <a id="Naming"></a> Naming

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

## <a id="ApiFlows"></a> Api flows

### CRUD

- CRUD request available on [Swagger](http://localhost:8080/swagger-ui/) with Admin role privileges

### Auth

### Payment

- Payment initiation _**/api/v1/payments/initiatePayment**_

### Account