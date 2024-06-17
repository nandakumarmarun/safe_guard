FROM maven:3.8.4-eclipse-temurin-17 AS builder

ADD ./pom.xml pom.xml
ADD ./src src/

RUN mvn clean package -Dmaven.test.skip=true

FROM eclipse-temurin:17-jre

COPY --from=builder target/v1-0.0.1-SNAPSHOT.jar v1-0.0.1-SNAPSHOT.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/v1-0.0.1-SNAPSHOT.jar"]
