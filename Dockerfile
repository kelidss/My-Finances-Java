FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/finance-control-app.jar finance-control-app.jar
ENTRYPOINT ["java", "-jar", "finance-control-app.jar"]
