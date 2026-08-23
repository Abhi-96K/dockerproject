# Stage 1: Build the JAR file using Maven
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code and package the JAR
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Create lightweight runtime Docker image
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy the built JAR from build stage
COPY --from=build /app/target/*.jar app.jar

# Expose default Spring Boot port
EXPOSE 8080

# Execute the Spring Boot JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
#why i'm using java
#let's try it in different way i mean using python

