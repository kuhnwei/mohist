## RedisCache缓存控制工具

对于项目之中的缓存，如果现在只是进行了单机的配置，那么现在可以使用的方案就是EHCache解决方案，可是如果放到集群之中，EHCache会有明显的性能问题，那么就需要单独创建Redis缓存。

既然要进行Redis的缓存处理操作，那么首先就需要提供有Redis的相关处理类，而这个处理类需要有一个重要的说明：要利用SpringData提供的相关的JedisConnectionPool的处理操作来完成，但是不建议使用RedisTemplate来处理，因为这个类如果在进行一些系统类序列化的时候有可能会出现无法反序列化的情况。那么最好的做法就是进行自定义的redis工具类的编写。

1. 在项目之中配置好需要使用到的Redis、Spring-Data相关依赖支持包：

   ```xml
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-data-redis</artifactId>
   </dependency>
   <dependency>
       <groupId>redis.clients</groupId>
       <artifactId>jedis</artifactId>
   </dependency>
   ```

2. 设置redis连接相关配置：

   ```yaml
   spring:
     redis:
       database: 0
       host: 127.0.0.1
       port: 6378
       password:
       pool:
         max-active: 8
         max-wait: -1
         max-idle: 8
         min-idle: 0
       timeout: 0
   ```

3. 

## Redis缓存认证与授权信息

在整个的Shiro里面核心的问题就是认证与授权的管理操作，而对于认证和授权管理操作为了避免重复性的去执行数据库或者是远程RPC端的服务调用，所以需要利用缓存来完成。

