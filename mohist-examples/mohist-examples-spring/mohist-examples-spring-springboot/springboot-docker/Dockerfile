# 指定基础镜像，在其上进行定制
FROM java:8

# 维护者信息
MAINTAINER kuhnwei <email@kuhnwei.com>

VOLUME /app

ADD /target/mohist.jar app.jar

RUN bash -c "touch /app.jar"

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
