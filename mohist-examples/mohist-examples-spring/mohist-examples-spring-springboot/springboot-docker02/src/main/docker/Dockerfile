FROM openjdk:8-jdk-alpine

VOLUME /temp

ADD springboot-docker02.jar mohist/app.jar

CMD ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/mohist/app.jar","--spring.config.location=/mohist/config/application.yml"]
