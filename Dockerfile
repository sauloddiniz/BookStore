FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/com.bookstore-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Xms512m", "-Xmx1g", "-jar", "app.jar"]

