FROM maven:3.6.3-jdk-11-slim AS builder

ADD ./pom.xml pom.xml
ADD ./src src/

RUN mvn clean package -Dmaven.test.skip=true

FROM adoptopenjdk/openjdk11:jre-11.0.6_10-alpine

COPY --from=builder target/mod-c-admin-latest.jar mod-c-admin-latest.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/mod-c-admin-latest.jar"]
