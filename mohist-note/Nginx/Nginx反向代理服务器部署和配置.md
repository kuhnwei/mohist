Nginx反向代理服务器部署和配置

## Nginx简介

Nginx是继Apache之后在国内的负载均衡软件中已经被广泛的应用，其可以使用它的反向代理实现WEB集群的配置，而且也已经成为了所有从业者的必备技能。

- Nginx("engine x")是一个高性能的HTTP和反向代理服务器，也是一个IMAP/POP3/SMTP服务器。
- Nginx是由Igor Sysoev为俄罗斯访问量第二的Rambler.ru站点开发的，第一个公开版本0.1.0发布于2004年10月4日。其将源代码以类BSD许可证的形式发布，因为它的稳定性、丰富的功能集、示例配置文件和低系统资源的消耗而闻名。2011年6月1日，Nginx1.0.4发布。
- 其特点是占有内存少，并发能力强，事实上Nginx的并发能力确实在同类型的网页服务器中表现较好。

Nginx最大的特点是可以进行多模块的整合，它可以整合其它的开发模块实现更加复杂的功能。

## Nginx安装

```shell
apt-get update
apt-get install nginx
```

安装后与nginx相关的目录

```
/usr/share/doc/nginx
/usr/share/nginx
/usr/sbin/nginx
/etc/init.d/nginx
/etc/nginx
/etc/logrotate.d/nginx
/etc/default/nginx
/etc/ufw/applications.d/nginx
/var/lib/nginx
/var/log/nginx
```

启动

```shell
# 通过上面的方式安装就可以直接输入 nginx 启动
nginx
```

nginx启动之后默认的占用80端口。可以直接通过浏览器访问：```http://服务器ip地址```

```shell
nginx -s reload # 重新载入配置文件
nginx -s reopen # 重启
nginx -s stop   # 停止
```

## Nginx配置文件nginx.conf详解

> \conf\ - 配置文件目录
> nginx.conf - 主配置文件
> \html\ - 默认网站文件位置
> \log\ - 默认日志文件位置
>
> HTTP默认端口：TCP 80
> HTTPS默认端口：* TCP 443

### nginx.conf

```
# 定义nginx运行的用户和用户组
user www-data;
# nginx进程数，建议设置为等于CPU总核心数
worker_processes 4;
# 进程文件 
pid /var/run/nginx.pid;

# 全局错误日志定义类型，[ debug | info | notice | warn | error | crit ]
# error_log logs/error.log;
# error_log logs/error.log notice;
# error_log logs/error.log info;

# 工作模式与连接数上限
events {
    # 单个进程最大连接数（最大连接数 = 连接数 * 进程数）
    worker_connections 1024;
}

http {
    
    ##
    # Basic Settings
    ## 
    
    # 提高 Nginx 静态资源托管效率
    sendfile on;
    
    # 防止网络堵塞
    tcp_nopush on;
    
    # 
    tcp_nodelay on;
    
    # 长连接超时时间，单位是秒
    keepalive_timeout 65;
    
    
    types_hash_max_size 2048;
    # server_tokens off;

	# server_names_hash_bucket_size 64;
	# server_name_in_redirect off;
	
	# 文件扩展名与文件类型映射表
	include /etc/nginx/mime.types;
	
	# 默认文件类型
	default_type application/octet-stream;
	
	##
	# Logging Settings
	##

	access_log /var/log/nginx/access.log;
	error_log /var/log/nginx/error.log;
	
	##
	# Gzip Settings
	##
	
	# 开启器gzip压缩输出
	gzip on;
	gzip_disable "msie6";

	# gzip_vary on;
	# gzip_proxied any;
	# gzip_comp_level 6;
	# gzip_buffers 16 8k;
	# gzip_http_version 1.1;
	# gzip_types text/plain text/css application/json application/x-javascript text/xml application/xml application/xml+rss text/javascript;
	
	# 虚拟主机的配置
	server {
        # 监听端口
        listen 80;
        
        # 域名可以有多个，用空格隔开
        server_name localhost;
        
        # 默认编码
        #charset utf-8;
        
	}
}

```



#### 基础设置

- ```sendfile``` 配置可提高Nginx静态资源托管效率。sendfile是一个系统调用，直接在内核空间完成文件发送，不需要先read再write，没有上下文切换开销。

- ```tcp_nopush``` 是FreeBSD的一个socket选项，对应Linux的tcp_cork，Nginx里统一用```tcp_nopush```来控制它，并且只有在启用了sendifile之后才生效。启用它之后，数据包会累计到一定大小之后才会发送，减小了额外开销，提高网络效率。

- ```tcp_nodelay``` 也是一个socket选项，启用后会禁用Nagle算法，尽快发送数据，某些情况下可以节约200ms（Nagle算法原理是：在发出去的数据还未被确认之前，新生成的小数据先存起来，凑满一个MSS或者等到收到确认后再发送）。Nginx只会针对处于kepp-alive状态的TCP连接才会启用```tcp_nodelay```。

  > 可以看到```tcp_nopush```是要等数据包累积到一定大小才发送，```tcp_nodelay```是要尽快发送，二者相互矛盾。实际上，它们确实可以一起用，最终的效果是县填满包，再尽快发送。

- ```keepalive_timeout``` 指定服务端为每个TCP连接最多可以保持多长时间。Nginx的默认值是75秒，有些浏览器最多保持60秒。

