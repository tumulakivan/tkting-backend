# Use official OpenJDK image
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy your project files
COPY . .

# Make Maven wrapper executable (if you're using mvnw)
RUN chmod +x mvnw

# Build the Spring Boot app
RUN ./mvnw clean package -DskipTests

# Run the generated JAR
CMD ["java", "-jar", "target/appdev-0.0.1-SNAPSHOT.jar"]
