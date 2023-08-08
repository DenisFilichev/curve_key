FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/curve_key-0.0.1-SNAPSHOT.jar curve_key.jar

CMD ["java", "-jar", "ertelapiaction.jar"]