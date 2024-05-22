FROM maven:3.8.3-openjdk-17 as build
LABEL author="https://github.com/HimanshuSajwan911"

COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-alpine
COPY --from=build /target/sudoku-server-0.0.1-SNAPSHOT.jar sudoku-server.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar","sudoku-server.jar", "--spring.profiles.active=prod"]
