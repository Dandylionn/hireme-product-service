# For Java 8, try this
# FROM openjdk:8-jdk-alpine

# For Java 11, try this
FROM amazoncorretto:17-alpine-jdk

# Refer to Maven build -> finalName
ARG JAR_FILE=staging/app.jar

# cd /opt/app
WORKDIR /opt/app

# cp staging/app.jar /opt/app/app.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8081
# java -jar /opt/app/app.jar
ENTRYPOINT ["java","-jar","app.jar"]
