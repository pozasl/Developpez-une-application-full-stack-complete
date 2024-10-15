# Authentification API

Microservices managing the users authentication at the */api/auth* endpoint

## running localy

Just run

```bash
./mvnw spring-boot:run
```
The server will be listening on http://localhost:8088

## Building

This application needs the common library to be installed before building.
The integration tests need a PostgtreSQL Database.

To build
```bash
./mvnw clean packages
```

The artifact will be build in the *target* folder