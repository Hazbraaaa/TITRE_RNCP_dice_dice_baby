# BACKEND - DEV ONLY
FROM eclipse-temurin:21-jdk AS dev

# Installer Maven
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

WORKDIR /app

# Copier pom.xml pour installer les deps
COPY pom.xml .
RUN mvn dependency:go-offline

# Les sources seront montées par volumes, on ne les copie pas
CMD ["mvn", "spring-boot:run"]
