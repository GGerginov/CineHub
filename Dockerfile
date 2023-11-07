# Use a base image with Java installed
FROM eclipse-temurin:17-jdk as build

# Set the working directory inside the container
WORKDIR /app/

# Copy the Maven wrapper and pom.xml first (for caching)
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Only download dependencies
# This layer is cached if the pom.xml file doesn't change
RUN ./mvnw dependency:go-offline

# Copy the project source
COPY src src

# Build the project and package the jar
# The --no-transfer-progress option is used to reduce log clutter
RUN ./mvnw clean package -DskipTests --no-transfer-progress

# Use a smaller JRE base image to create a smaller final image
FROM eclipse-temurin:17-jre as runtime

# Set the working directory in the JRE image
WORKDIR /app

# Copy the jar from the build stage to the JRE stage
COPY --from=build /app/target/CineHub-0.0.1-SNAPSHOT.jar .

# Expose the port the app runs on
EXPOSE 8080

# Set the command to run the application
CMD ["java", "-jar", "CineHub-0.0.1-SNAPSHOT.jar"]
