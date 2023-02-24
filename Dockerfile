FROM openjdk:17-jdk-slim
COPY 2022-08-otus-spring-shigortsovmv-9plus/target/BooksLib-0.0.1-SNAPSHOT.jar /app/BooksLib.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/BooksLib.jar"]
