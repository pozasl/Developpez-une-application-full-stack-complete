FROM eclipse-temurin:17-jre-alpine
ENV APP_NAME=users-api
ENV APP_VERSION=0.0.1
ENV JAR_NAME=${APP_NAME}-${APP_VERSION}.jar
RUN mkdir /opt/app
COPY back/${APP_NAME}/target/${JAR_NAME} /opt/app/
CMD java -jar /opt/app/${JAR_NAME}