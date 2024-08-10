# Use an official Eclipse Temurin OpenJDK 17 runtime as a parent image
FROM eclipse-temurin:17-jre

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Expose port 8080 to the outside world
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
