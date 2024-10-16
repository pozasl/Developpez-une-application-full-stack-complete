# MDD

MDD is a social network for developpers

## Tech stack

Frontend
* Node 22
* Angular 18
* Material-angular 18

Backend
* Java 17
* Spring-boot 3.3.4
* Spring-Cloud Gateway
* Spring Webflux
* PostgreSQL 17.0
* MongoDB 8.0
* Kafka 3.8

## Building

### Generate OpenAPI Spring API source code

From **back** folder

```bash
./mvnw generate-sources
```

### Build and install microservices'common configuration library

From **back** folder run

```bash
./mvnw clean install
```

### Build all packages

You should have MongoDB & PostgreSQL configured and running to run the integration tests.

From **back** folder:

```bash
./mvnw clean package
```

### Build all docker images (Back & Front)

From the project root folder run the uild_back_images.sh script:

```bash
sh build_fullstack_images.sh
```

### Generate OpenAPI Angular client source code

From the **front** directory:

```bash
npx openapi-generator-cli generate -i ../resources/openapi/mdd.yml -g typescript-angular -o src/app/core/modules/openapi --additional-properties fileNaming=kebab-case,withInterfaces=true --generate-alias-as-model
```

## Running

### Running the stack localy in dev environment

**If you don't use the provided devcontainer** you'll need to setup Kafka, MongoDB and PostgreSQL localy and set those environment variables:

```
MONGO_INITDB_ROOT_USERNAME=AN_ADMIN_NAME
MONGO_INITDB_ROOT_PASSWORD=AN_ADMIN_PASS
MONGO_INITDB_DATABASE=mdd
MONGO_HOST=localhost
MONGO_PORT=27017
MONGO_USERNAME=A_MONGO_USER
MONGO_PASSWORD=A_MONGO_PASS
POSTGRES_HOST=localhost
POSTGRES_PORT=5432
POSTGRES_PASSWORD=A_POSTGRES_PASS
POSTGRES_USER=A_POSTGRES_USER
POSTGRES_DB=mdd
KAFKA_HOST=localhost
```

Then init the database with the provided scripts in resources/compose/mdd/init-db
- setup.js for mongodb
- 01-schema.sql for postgresql

You'll need to open several terminals in each microservices folder and in this order

```bash
cd back/auth-api
./mvnw spring-boot:run
```
swagger-ui available at http://localhost:8088/webjars/swagger-ui/index.html


```bash
cd back/feeds-api
./mvnw spring-boot:run
```
swagger-ui available at http://localhost:8085/webjars/swagger-ui/index.html

```bash
cd back/posts-api
./mvnw spring-boot:run
```
swagger-ui available at http://localhost:8081/webjars/swagger-ui/index.html

```bash
cd back/subscribtions-api
./mvnw spring-boot:run
```
swagger-ui available at http://localhost:8084/webjars/swagger-ui/index.html

```bash
cd back/users-api
./mvnw spring-boot:run
```
swagger-ui available at http://localhost:8082/webjars/swagger-ui/index.html

```bash
cd back/api-gateway
./mvnw spring-boot:run
```

```bash
cd front
ng serve
```
The application will be accessible from http://127.0.0.1:4200

### Run the complete compose stack localy

**if you're not using a devcontainer** setup the LOCAL_WORKSPACE_FOLDER environment var

```bash
export LOCAL_WORKSPACE_FOLDER=/where/this/project/is
```

Open a terminal in the **resources/compose/mdd** folder and run:

```bash
docker-compose up -d
```
The application will be accessible from http://localhost