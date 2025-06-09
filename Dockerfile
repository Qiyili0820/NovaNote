# Stage 1 - Build
FROM eclipse-temurin:17-jdk-alpine as builder

WORKDIR /app
COPY demo/.mvn /app/.mvn
COPY demo/mvnw /app/mvnw
COPY demo/pom.xml /app/pom.xml
RUN chmod +x mvnw && ./mvnw dependency:go-offline

COPY demo/src /app/src
RUN ./mvnw clean package

# Stage 2 - Run
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar


CMD ["java", "-jar", "app.jar"]
