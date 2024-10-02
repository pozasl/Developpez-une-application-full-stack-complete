#!/bin/sh
docker build -f resources/docker/api-gateway/Dockerfile -t mdd-api-gateway:0.0.1-SNAPSHOT .
docker build -f resources/docker/auth-api/Dockerfile -t mdd-auth-api:0.0.1-SNAPSHOT .
docker build -f resources/docker/users-api/Dockerfile -t mdd-users-api:0.0.1-SNAPSHOT .
docker build -f resources/docker/topics-api/Dockerfile -t mdd-topics-api:0.0.1-SNAPSHOT .
docker build -f resources/docker/posts-api/Dockerfile -t mdd-posts-api:0.0.1-SNAPSHOT .
docker build -f resources/docker/subs-api/Dockerfile -t mdd-subs-api:0.0.1-SNAPSHOT .
docker build -f resources/docker/feeds-api/Dockerfile -t mdd-feeds-api:0.0.1-SNAPSHOT .