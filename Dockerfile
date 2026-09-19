# Stage 1: Build with Maven + JDK 21
FROM maven:3.9.11-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY services/skyblock-flipper/pom.xml .
COPY services/skyblock-flipper/src ./src
RUN mvn clean package -DskipTests

# Stage 2: Runtime with JDK 21
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/skyblock-flipper-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]