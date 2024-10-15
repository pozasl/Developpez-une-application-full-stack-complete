# API Gateway
Route the API calls to the other microservices.

## Configuration
The routes are configured in *src/main/resources/application.yml*

```yaml
spring:
  application:
    name:api-gateway
  cloud:
    gateway:
      routes:
      - id: posts_route
        uri: http://localhost:8081
        predicates:
        - Path=/api/posts/**
      - id: topics_route
        uri: http://localhost:8081
        predicates:
        - Path=/api/topics/**
      - id: user_route
        uri: http://localhost:8082
        predicates:
        - Path=/api/user/**
      - id: subscribtions_route
        uri: http://localhost:8084
        predicates:
        - Path=/api/subscribtions/**
      - id: feeds_route
        uri: http://localhost:8085
        predicates:
        - Path=/api/feeds/**
      - id: auth_route
        uri: http://localhost:8088
        predicates:
        - Path=/api/auth/**
```

## running localy

Just run

```bash
./mvnw spring-boot:run
```
The server will be listening on http://localhost:8080

/!\ The other microservices must be launched before the api-gateway

## Building

To build
```bash
./mvnw clean packages
```

The artifact will be build in the *target* folder