# Stage 1: Build with Maven + JDK 21
FROM maven:3.9.11-eclipse-temurin-21-alpine AS build
WORKDIR /app
# Copy shared models first and install it into local repo
COPY lib/skyblock-shared-models ./skyblock-shared-models
RUN mvn -f ./skyblock-shared-models/pom.xml clean install -DskipTests

# Copy and build the microservice
COPY services/skyblock-flipper/pom.xml .
COPY services/skyblock-flipper/src ./src
RUN mvn clean package -DskipTests

# Stage 2: Runtime with JDK 21
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/SkyBlockFlipper-1.0-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]