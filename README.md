# Mmd
## Tech stack

Frontend
* Angular 18
* Material-angular

Backend
* Spring-boot3
* Spring-Cloud Gateway
* Webflux
* PostgreSQL
* MongoDB
* Kafka

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