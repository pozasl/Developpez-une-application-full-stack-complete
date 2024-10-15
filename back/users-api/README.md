# Users API

Microservices managing the user's data

## running localy

Just run

```bash
./mvnw spring-boot:run
```
The server will be listening on http://localhost:8082

## Building

This application needs the common library to be installed before building.
The integration tests need a PostgtreSQL Database.

To build
```bash
./mvnw clean packages
```

The artifact will be build in the *target* folder