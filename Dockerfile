# Use the official Maven image as a parent image
FROM maven:3.8.4-openjdk-11-slim AS build

# Set the working directory in the container
WORKDIR /app

# Copy the project's POM file and download dependencies
COPY STLMobileSubscribers/pom.xml .
RUN mvn dependency:go-offline

# Copy the source code into the container
COPY STLMobileSubscribers/src ./src

# Build the application
RUN mvn package

# Use a lightweight Alpine-based JRE image to run the application
FROM openjdk:11-jre-slim

# Set the working directory in the container
WORKDIR /STLMobileSubscribers


# Copy the JAR file from the build stage to the final image
COPY --from=build /app/STLMobileSubscribers/target/MobileSubscribers-0.0.1-SNAPSHOT.jar MobileSubscribers.jar

# Expose the port that your application listens on (change as needed)
EXPOSE 8080

# Environment variables for database connection settings
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/mobilesubscribers
ENV SPRING_DATASOURCE_USERNAME=SoftIntern
ENV SPRING_DATASOURCE_PASSWORD=SoftIntern123
ENV SPRING_JPA_HIBERNATE_DDL_AUTO=update
ENV SPRING_JPA_SHOW_SQL=true
ENV SPRING_JPA_PROPERTIES_HIBERNATE_DIALECT=org.hibernate.dialect.PostgreSQLDialect
ENV SPRING_JPA_PROPERTIES_HIBERNATE_FORMAT_SQL=true
ENV SPRING_DATASOURCE_DRIVER-CLASS-NAME=org.postgresql.Driver

# Thymeleaf configuration
ENV SPRING_THYMELEAF_CACHE=false
ENV SPRING_THYMELEAF_ENABLED=true
ENV SPRING_THYMELEAF_ENCODING=UTF-8
ENV SPRING_THYMELEAF_SERVLET_CONTENT-TYPE=text/html
ENV SPRING_THYMELEAF_MODE=HTML
ENV SPRING_THYMELEAF_PREFIX=classpath:/templates/
ENV SPRING_THYMELEAF_SUFFIX=.html

# Server error settings
ENV SERVER_ERROR_INCLUDE_MESSAGE=always

# Command to run the application
CMD ["java", "-jar", "MobileSubscribers.jar"]
