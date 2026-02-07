# Build stage: use JDK 21 to compile
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Pre-copy only wrapper and pom for dependency resolution caching
COPY .mvn/ .mvn/
COPY mvnw mvnw
COPY pom.xml pom.xml
RUN chmod +x mvnw

# Copy source and build
COPY src/ src/
RUN ./mvnw -B -DskipTests clean package

# Runtime stage: use JRE 21
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the built jar from the builder stage
COPY --from=build /app/target/ecommerceapp-0.0.1-SNAPSHOT.jar /app/app.jar

# Railway provides PORT; Spring Boot reads it via application-prod.properties
ENV SPRING_PROFILES_ACTIVE=prod
EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/app.jar"]
