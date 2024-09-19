FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/my_finances-0.0.1-SNAPSHOT.jar /app/my_finances.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "my_finances.jar"]