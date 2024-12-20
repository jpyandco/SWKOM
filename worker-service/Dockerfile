FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/worker-service-1.0-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
