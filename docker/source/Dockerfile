# Image to build and run source files

FROM eclipse-temurin:11-jdk-jammy

WORKDIR /app

# Copy gradle and source project files
COPY gradle/ gradle
COPY gradlew build.gradle settings.gradle ./
COPY src ./src

# Install all the dependencies
# Not required since dependencies will be downloaded twice
RUN ./gradlew

CMD ["./gradlew", "bootRun"]