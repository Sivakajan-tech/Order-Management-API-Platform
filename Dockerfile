# Use the official OpenJDK 17 image with Alpine Linux as the base image
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container to /app
WORKDIR /app

# Set an environment variable for the application port
ENV PORT=8080

# Expose the application port to be accessible from outside the container
EXPOSE 8080

# Copy the JAR file from the target directory on the host to the container's /app directory
COPY target/*.jar app.jar

# Specify the command to run the application using the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
