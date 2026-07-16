# BACKEND - DEV ONLY
FROM eclipse-temurin:21-jdk AS dev

# Install Maven
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Sources will be mounted via volumes
CMD ["sh", "-c", "./mvnw spotless:apply && ./mvnw spring-boot:run"]