# Use Eclipse Temurin as Java base image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy and build project
COPY demo /app/demo
WORKDIR /app/demo
RUN chmod +x mvnw && ./mvnw clean package


# Run the Spring Boot app
CMD ["java", "-jar", "target/demo-0.0.1-SNAPSHOT.jar"]
