FROM openjdk:17
ADD target/airplane-docker.jar airplane-docker.jar
ENTRYPOINT ["java", "-jar", "airplane-docker.jar"]
EXPOSE 8080