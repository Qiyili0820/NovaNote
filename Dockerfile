# Use Eclipse Temurin as Java base image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy Maven wrapper + .mvn
COPY demo/.mvn /app/demo/.mvn
COPY demo/mvnw /app/demo/mvnw
COPY demo/pom.xml /app/demo/pom.xml

# Prepare Maven cache layer first
WORKDIR /app/demo
RUN chmod +x mvnw && ./mvnw dependency:go-offline

# Copy the rest of the project
COPY demo/src /app/demo/src

# Build the project
RUN ./mvnw clean package

# Run the Spring Boot app
CMD ["java", "-jar", "target/demo-0.0.1-SNAPSHOT.jar"]
