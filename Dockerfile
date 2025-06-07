# 🧱 Stage 1: Build the Spring Boot app using Maven
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Create a working directory inside the container
WORKDIR /app

# Copy your pom.xml (so Docker can download dependencies first)
COPY pom.xml .

# Download dependencies to speed up future builds
RUN mvn dependency:go-offline

# Copy the rest of your project files into the container
COPY . .

# Build the Spring Boot JAR file (skip tests to make it faster)
RUN mvn clean package -DskipTests

# 🧱 Stage 2: Run the app in a lightweight Java environment
FROM eclipse-temurin:17-jdk

# Set working directory again
WORKDIR /app

# Copy the JAR file built in stage 1
COPY --from=build /app/target/*.jar app.jar

# Open port 8080 inside the container
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
