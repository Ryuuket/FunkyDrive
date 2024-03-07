FROM gradle:8.6.0-jdk21 AS build
COPY  --chown=gradle:gradle . /usr/src/app
WORKDIR /usr/src/app
COPY . .
RUN gradle build

FROM openjdk:21-slim
WORKDIR /app
COPY --from=build /usr/src/app/build/libs/*.jar .
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "./com.example.funkydrive-all.jar"]