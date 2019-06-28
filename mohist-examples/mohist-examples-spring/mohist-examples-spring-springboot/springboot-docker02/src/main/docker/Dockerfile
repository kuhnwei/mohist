FROM openjdk:8-jdk-alpine

VOLUME /temp

ADD springboot-docker02.jar app.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
