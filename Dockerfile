# Use Maven image for building the application
FROM maven:3.8.1-openjdk-17-slim AS builder

# Set working directory
WORKDIR /app

# Copy only the pom.xml first to leverage Docker layer caching
COPY pom.xml .

# Copy the rest of the source code
COPY src ./src

# Build the application
RUN mvn clean package -Dmaven.test.skip=true

# Use AdoptOpenJDK 17 JRE image for the runtime environment
FROM adoptopenjdk:17-jre-hotspot AS runtime

# Set working directory
WORKDIR /app

# Copy the JAR file from the builder stage
COPY --from=builder /app/target/mod-c-admin-latest.jar mod-c-admin-latest.jar

# Expose the port
EXPOSE 8081

# Specify the command to run on container start
ENTRYPOINT ["java", "-jar", "mod-c-admin-latest.jar"]


# FROM maven:3.6.3-jdk-11-slim AS builder
#
# ADD ./pom.xml pom.xml
# ADD ./src src/
#
# RUN mvn clean package -Dmaven.test.skip=true
#
# FROM adoptopenjdk/openjdk11:jre-11.0.6_10-alpine
#
# COPY --from=builder target/mod-c-admin-latest.jar mod-c-admin-latest.jar
# EXPOSE 8081
# ENTRYPOINT ["java","-jar","/mod-c-admin-latest.jar"]
