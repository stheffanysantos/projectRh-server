FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app
COPY . .
RUN ./gradlew build -x test
VOLUME /tmp
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
