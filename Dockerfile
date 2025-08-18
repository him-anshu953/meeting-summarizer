FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copy Maven wrapper and pom.xml
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Ensure mvnw is executable
RUN chmod +x mvnw

# Preload dependencies
RUN ./mvnw dependency:go-offline

# Copy source code
COPY src ./src

# Build the application
RUN ./mvnw clean package -DskipTests

# Run the Spring Boot JAR
CMD ["java", "-jar", "target/sumarizer-0.0.1-SNAPSHOT.jar"]
