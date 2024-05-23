FROM maven:3.8.3-openjdk-17 as build
LABEL author="https://github.com/HimanshuSajwan911"

COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-alpine
COPY --from=build /target/sudoku-server-0.0.1-SNAPSHOT.jar sudoku-server.jar

ARG SERVER_PORT
ARG SPRING_PROFILE
ARG DB_HOST
ARG DB_PORT
ARG DB_USERNAME
ARG DB_PASSWORD
ARG DB_NAME

EXPOSE ${SERVER_PORT}
ENTRYPOINT ["java", "-DSERVER_PORT=${SERVER_PORT} -DDB_HOST=${DB_HOST} -DDB_PORT=${DB_PORT} -DDB_USERNAME=${DB_USERNAME} -DDB_PASSWORD=${DB_PASSWORD} -DDB_NAME=${DB_NAME}", "-jar","sudoku-server.jar", "--spring.profiles.active=${SPRING_PROFILE}}}"]
