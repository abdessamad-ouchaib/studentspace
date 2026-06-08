# ── Étape 1 : Build Maven ──────────────────────────────────────
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn package -DskipTests
# Renommer le JAR pour éviter tout problème de nom
RUN mv target/studentspace-backend-1.0.0.jar target/app.jar

# ── Étape 2 : Image finale légère ─────────────────────────────
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/app.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

