# Image to run build files

FROM eclipse-temurin:11-jdk-jammy

# Running applications with user privileges helps to mitigate some risks
# So, an important improvement to the Dockerfile is to run the application as a non-root user:
RUN adduser spring
USER spring:spring

# Copy build project files
ARG JAR_FILE=build/libs/\*.jar
COPY ${JAR_FILE} app.jar

# Run java build file
ENTRYPOINT ["java","-jar","/app.jar"]