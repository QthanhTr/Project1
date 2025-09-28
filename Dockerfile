# Stage 1: Build the application
FROM eclipse-temurin:17-jdk-focal as build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN ./mvnw clean package -DskipTests

# Stage 2: Create the final production image
FROM eclipse-temurin:17-jre-focal
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Define runtime configuration
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
