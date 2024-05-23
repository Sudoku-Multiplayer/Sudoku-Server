FROM maven:3.8.3-openjdk-17 as build
LABEL author="https://github.com/HimanshuSajwan911"

COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-alpine
COPY --from=build /target/sudoku-server-0.0.1-SNAPSHOT.jar sudoku-server.jar

ARG _SERVER_PORT
ARG _SPRING_PROFILE
ARG _DB_HOST
ARG _DB_PORT
ARG _DB_USERNAME
ARG _DB_PASS
ARG _DB_NAME

EXPOSE ${SERVER_PORT}
ENTRYPOINT ["java", "-DSERVER_PORT=${_SERVER_PORT}", "-DDB_HOST=${_DB_HOST}", "-DDB_PORT=${_DB_PORT}", "-DDB_USERNAME=${_DB_USERNAME}", "-DDB_PASSWORD=${_DB_PASS}", "-DDB_NAME=${_DB_NAME}", "-jar", "sudoku-server.jar", "--spring.profiles.active=${_SPRING_PROFILE}"]