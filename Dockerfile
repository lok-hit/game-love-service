# Stade 1 : building app.
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# Copying app files
COPY pom.xml .
COPY src ./src

# Building game-love-service
RUN mvn clean package -DskipTests

# Etap 2: starting app
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copying JAR
COPY --from=build /app/target/game-love-service-1.0-SNAPSHOT.jar app.jar

# Copying configuration
COPY src/main/resources/application.yml ./config/application.yml

# Env for Spring profile
ENV SPRING_CONFIG_LOCATION=classpath:/config/application.yml
ENV SPRING_PROFILES_ACTIVE=prod

HEALTHCHECK --interval=30s --timeout=5s --start-period=10s \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

LABEL maintainer="mateusz@lokhit.org"
LABEL version="1.0"
LABEL description="Game Love Service - Spring Boot + Kafka + Keycloak"

# port exposing
EXPOSE 8080

# entrypoint
ENTRYPOINT ["java", "-jar", "app.jar"]