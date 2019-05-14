# Compose模板文件

模板文件是使用`Compose`的核心，涉及到的指令关键字也比较多。但大家不用担心，这里面大部分指令跟`docker run`相关参数的含义都是类似的。

默认的模板文件名称为`docker-compose.yml`，格式为YAML格式。

```yaml
version: "3"

services:
  webapp:
    image: examples/web
	  ports:
        - "80:80"
      volumes:
        - "/data"
```

注意每个服务都必须通过`image`指令指定镜像或`build`指令（需要Dockerfile）等来自动构建生成镜像。

如果使用`build`指令，在`Dockerfile`中设置的选项（例如：`CDM`,`EXPOSE`,`VOLUME`,`ENV`等）将会自动被获取，无需在`docker-compose.yml`中再次设置。

下面分别介绍各个指令的用法。

## build

指定





```
# 使用管理员权限打开终端
docker-compose up

docker-compose down
```

