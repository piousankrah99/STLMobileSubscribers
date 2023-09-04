# Use an official OpenJDK runtime as a parent image
FROM openjdk:11-jre-slim

# Set the working directory to /app
WORKDIR /app

# Copy the Maven project file and POM dependencies file to the container
COPY pom.xml .
COPY .mvn .mvn

# Copy the source code to the container
COPY src src

# Add execute permissions to the mvnw script
RUN chmod +x .mvn/wrapper/mvnw

# Build the application with Maven (downloads dependencies and compiles code)
RUN ./mvnw package -DskipTests

# Expose the port your Spring Boot application will run on (default: 8080)
EXPOSE 8080

# Define environment variables for database connection
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/mobilesubscribers
ENV SPRING_DATASOURCE_USERNAME=SoftIntern
ENV SPRING_DATASOURCE_PASSWORD=SoftIntern123
ENV SPRING_JPA_HIBERNATE_DDL_AUTO=update
ENV SPRING_JPA_SHOW_SQL=true
ENV SPRING_JPA_PROPERTIES_HIBERNATE_DIALECT=org.hibernate.dialect.PostgreSQLDialect
ENV SPRING_JPA_PROPERTIES_HIBERNATE_FORMAT_SQL=true
ENV SPRING_DATASOURCE_DRIVER_CLASS_NAME=org.postgresql.Driver

# Define Thymeleaf configuration
ENV SPRING_THYMELEAF_CACHE=false
ENV SPRING_THYMELEAF_ENABLED=true
ENV SPRING_THYMELEAF_ENCODING=UTF-8
ENV SPRING_THYMELEAF_SERVLET_CONTENT_TYPE=text/html
ENV SPRING_THYMELEAF_MODE=HTML
ENV SPRING_THYMELEAF_PREFIX=classpath:/templates/
ENV SPRING_THYMELEAF_SUFFIX=.html

# Define server error message configuration
ENV SERVER_ERROR_INCLUDE_MESSAGE=always

# Specify the command to run your Spring Boot application
CMD ["java", "-jar", "target/MobileSubscribers-0.0.1-SNAPSHOT.jar"]
