FROM openjdk:17-jdk-slim
RUN apt-get update && apt-get install -y ghostscript libgs-dev libjna-java
ARG JAR_FILE=target/worker-service-1.0-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
