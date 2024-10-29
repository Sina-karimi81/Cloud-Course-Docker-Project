FROM openjdk:17-jdk-slim-buster as builder
WORKDIR /build
COPY . .
RUN ./mvnw clean package -Dskiptests

FROM openjdk:17-jdk-slim-buster
WORKDIR /app
COPY --from=builder ./build/target/Dictionary-0.0.1-SNAPSHOT.jar ./app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]