### Image to run build files
FROM eclipse-temurin:11-jdk-jammy as build
# Running applications with user privileges helps to mitigate some risks
# So, an important improvement to the Dockerfile is to run the application as a non-root user:
RUN adduser spring
USER spring:spring
# Copy build project files
ARG JAR_FILE=build/libs/\*.jar
COPY ${JAR_FILE} app.jar
# Run java build file
ENTRYPOINT ["java","-jar","/app.jar"]

### Image to build and run source files
FROM eclipse-temurin:11-jdk-jammy as standalone
WORKDIR /app
# Copy gradle and source project files
COPY gradle/ gradle
COPY gradlew build.gradle settings.gradle ./
COPY src ./src
# Install all the dependencies
# Not required since dependencies will be downloaded twice
RUN ./gradlew
# Run gradle SpringBoot:run
CMD ["./gradlew", "bootRun"]