FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Copy the Gradle wrapper and build file
COPY gradle/wrapper ./gradle/wrapper
COPY gradlew build.gradle ./

# Run Gradle build to download dependencies
RUN ./gradlew build

# Copy the source code
COPY src ./src

# Set the default command to run the application
CMD ["./gradlew", "bootRun"]

EXPOSE 8080