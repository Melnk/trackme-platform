# ===== Stage 1: Build =====
FROM gradle:8-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle clean build --no-daemon -x test

# ===== Stage 2: Runtime =====
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]