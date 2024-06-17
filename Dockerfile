FROM maven:3.8.4-openjdk-17 AS build

ADD ./pom.xml pom.xml
ADD ./src src/

RUN mvn clean package -Dmaven.test.skip=true

FROM openjdk:17-jdk-slim

COPY --from=builder target/v1-0.0.1-SNAPSHOT.jar v1-0.0.1-SNAPSHOT.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/v1-0.0.1-SNAPSHOT.jar"]
