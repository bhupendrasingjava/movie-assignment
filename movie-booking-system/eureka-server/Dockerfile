# Stage 1: Build the application
FROM maven:3.8.8-eclipse-temurin-17-alpine AS build
WORKDIR /app

# Copy the pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of the source code and build the application
COPY src ./src
RUN mvn clean install

# Stage 2: Run the application
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/eureka-server-0.0.1-SNAPSHOT.jar /app/eureka-server.jar

# Expose the Eureka server port
EXPOSE 8761

# Command to run the Eureka server
ENTRYPOINT ["java", "-jar", "/app/eureka-server.jar"]
