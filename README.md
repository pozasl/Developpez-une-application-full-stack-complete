# Mmd
## Tech stack

Frontend
* Node 22
* Angular 18
* Material-angular

Backend
* Java 17
* Spring-boot 3.3.4
* Spring-Cloud Gateway
* Webflux
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

From **back/common**

```bash
./mvnw clean install
```

### Build all packages

From **back** folder:

```bash
./mvnw clean package
```

### Build all docker images

From the project root folder run the uild_back_images.sh script:

```bash
sh build_back_images.sh
```

### Generate OpenAPI Angular client source code

From the **front** directory:

```bash
npx openapi-generator-cli generate -i ../resources/openapi/mdd.yml -g typescript-angular -o src/app/core/modules/openapi --additional-properties fileNaming=kebab-case,withInterfaces=true --generate-alias-as-model
```

## Running

### Run the complete stack localy

Open a terminal in the **resources/compose/mdd** folder and run:

```bash
docker-compose up -d
```

Then go in the **front** folder and run:

```bash
npm run start
```

## Deploying

TODO