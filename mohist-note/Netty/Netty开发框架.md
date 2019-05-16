# Netty开发框架

## ![1557888126055](img/1557888126055.png)

网络程序的核心在于：数据通讯交互，即便有了`NIO`也已经实现了新的处理模型，但是整体的程序开发难度是非常高的，也就是说`NIO(AIO)`如果直接编写，则对于开发者的要求是非常高的，在整个编程开发过程之中需要考虑：长连接、数据的粘包与拆包、各种处理协议问题（`HTTP`、`WebSocket`），在现实的开发环境里面，对于`JDK`的原生支持只能够说是一个程序实现依赖，但是如果要想更好的去实现所需要的开发，那么就需要对原生的技术进行进一步的包裹，在这样的背景下就需要有高性能的`IO`通讯框架出现，而对于这样的通讯框架有`MINA`、`Netty`。

## Netty简介

Netty是一个最为成熟的、流行的异步通讯的高性能的开发框架啊，当你开始去研究`Netty`的时候证明你现在的开发重点已经不仅仅是在做一个简单的`CRUD`的规范化项目开发，而是要开始更多的考虑高性能通信的问题。

`Netty`是基于`NIO`的一种包装，同时也是`Reactor`模型的一种实现（`NIO`本身就是`Reactor`模型的实现），在`Reactor`模型里面所有的连接的通道需要进行注册，而后采用统一的方式进行操作，下面以`NIO`中的程序类例：`Selector`、`Channel`、`Buffer`。

![1557891993864](img/1557891993864.png)

而在整个的`Netty`开发框架里面也完全支持有这样的处理模型，所以可以将`Netty`理解为`NIO`的完美实现，帮助开发者解决了所有的通讯之中所能够遇见的难题，包括像华为也有许多的中间件是基于`Netty`开发的。

http://netty.io

`Netty`可以实现`HTTP`服务器的开发，但是这种开发和传统的`Tomcat`还是有所区别的，`Tomcat`是一个所谓的公版的`HTTP`服务器，并且`Tomcat`是基于`NIO`实现的（`Tomcat`性能并不会太差），使用`Netty`可以更方便的处理大数据量的信息交互。

同时也需要清楚在整个`Netty`里面它之所以可以方便的处理各种网络通讯问题，是因为它为了解决这一系列的问题采用了责任链的设计模式（过滤器其实就是一种责任链的实现），在过滤链之中会有提供一系列的数据处理操作，以帮助开发者简化操作流程，同时也为开发者开发自定义的过滤程序提供支持（一般出现在编码与解码操作上、对象序列化传输）。

`Netty`开发框架的主要功能是解决网络通讯问题（提升网络通讯的性能支持），所以在这样的背景下，如果要想进行`Netty`的开发就必须考虑到服务端与客户端两个组成部分。

但是需要清楚的另外一个实际的问题在于，`Netty`在整个系统开发之中时一个什么杨的地位呢？

在当今的社会已经处于一个大数据的时代了，那么大数据时代的一个明显标志在于：数据采集上，那么当有成千上万台的设备要进行数据采集的时候，我们需要考虑的时什么？

		- 数据的发送格式
		- 如何可以稳定的进行数据的全部接收。

那么对于数据的接收由于并发访问的数据量会非常的庞大，所以在这样的处理情况下肯定会想到使用消息中间件来解决此时数据访问量过大的问题。

![1557892604383](img/1557892604383.png)

## Netty编程起步

任何的网络开发里面最为重要的一个开发模型就是ECHO程序模型，利用ECHO程序结构就可以对其进行扩展，而幸运的是，在整个的`Netty`开发框架之中已经帮助开发者提供各种线程池的支持了，所以开发者只需要关注核心即可。

### Netty常用类

`Netty`在整体的开发处理之中依然沿用了`NIO`的相关概念，包括一些类的功能都与`NIO`的定义时相同的，下面首先来观察几个核心的类定义。

- `io.netty.channel.EventLoopGroup`：创建要给线程池的循环处理器；
  - 子类：`io.netty.channel.nio.NioEventLoopGroup`；
- `io.netty.channel.Channel`：进行所有通道的定义；
- `io.netty.channel.ChannelPipeline`：`Netty`对于数据的处理采用了责任链的模式，而此模式实现的关键在于此接口的使用，在此接口可以定义所有操作的程序类（顺序模式）；
  - 在最后添加处理器：`public ChannelPipeline addLast(ChannelHandler... handlers)`；
  - 在之前添加处理器：`public ChannelPipeline addFirst(ChannelHandler... handlers)`。

在整个`Netty`里面它在进行数据处理的时候考虑到了输入（`in`、`ChannelInboundInvoker`）与输出（`out`、`ChannelOutboundInvoker`）的问题。

![1557901869740](img/1557901869740.png)

- `io.netty.bootstrap.ServerBootstrap`：配置服务端的启动；
- `io.netty.channel.socket.nio.NioServerSocketChannel`：进行服务端`Channel`定义；

![1557901956231](img/1557901956231.png)

### ChannelHandle生命周期控制方法

在整个的`Netty`框架开发里面核心的本质都是在于`ChannelHandler`接口的处理里面，而这个接口里面所定义的方法全部都是生命周期的控制方法。

1. 【Client】`clientBootstrap.connect()`客户端尝试与服务端建立连接
2. 【Server】`channelActive()`服务端激活与此客户端的连接
3. 【Server】`channelRead()`服务端响应连接的建立
4. 【Client】`channelRead()`客户端可以开始向服务端发送消息

在`Netty`里面所有与连接与数据处理有关的生命周期控制方法都在`ChannelHandler`接口中定义了（一般使用的都是`ChannelHandlerAdapter`适配器处理类），那么在这样的情况下就需要去关注这个接口中的方法：

| No.  | 方法名称                                                     | 类型 | 描述                                                         |
| ---- | ------------------------------------------------------------ | ---- | ------------------------------------------------------------ |
| 01   | `public void channelActive(ChannelHandlerContext ctx) throws Exception` | 普通 | 通道连接时发送的内容，当通道连接之后可以进行一些先期数据的发送，例如：登陆验证、SessionID创建 |
| 02   | `public void channelInactive(ChannelHandlerContext ctx) throws Exception` | 普通 | 通道关闭时的回调处理操作                                     |
| 03   | `public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception` | 普通 | 信息接收                                                     |
| 04   | `public void channelReadComplete(ChannelHandlerContext ctx) throws Exception` | 普通 | 信息读取完毕                                                 |
| 05   | `public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception` | 普通 | 异常处理                                                     |
| 06   | `public void channelRegistered(ChannelHandlerContext ctx) throws Exception` | 普通 | 通道注册时调用                                               |
| 07   | `public void channelUnregistered(ChannelHandlerContext ctx) throws Exception` | 普通 | 通道取消注册时调用                                           |

以上的操作流程：通道先进行注册，而后再进行激活，最后再开始进行数据读写操作。

每一个生命周期的处理方法都可以编写相应的处理逻辑，常用的处理操作就是激活、读取、读取完毕、异常处理。

### 异步回调监听

现在已经实现了完善的`Echo`处理模型，但是在整体的处理之中对于数据的回应操作：`ctx.writeAndFlush(echoBuf)`，这个操作方法时存在有返回类型的。

在`Netty`之中提供有一个异步处理的监听程序接口：`io.netty.util.concurrent.GenericFutureListener`。

利用这一机制可以实现发送内容的回调处理操作进行各种消息发送的处理。

### 字符串解码器

现在已经可以使用`Netty`搭建正常的`TCP`程序了，并且也实现一个正常的`Echo`程序，而在整个的程序开发之中可以发现，在`Netty`里面默认情况下不管时客户端还是服务端进行的数据处理的时候采用的都是`ByteBuf`完成的。

但是通过源代码可以发现一个问题：

- 数据的发送处理：`public ChannelFuture writeAndFlush(Object msg)`；
- 数据的接收回调：`public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception`；

因为`Netty`最大的操作特点来自于其设置的责任链设计操作，在整个`ChannelPipeline`进行定义的时候可以追加一系列的处理过滤，也就意味着可以追加一些程序将传入的`ByteBuf`内容变为字符串类型（二进制的序列化数据、Json结构）。

1. 修改`EchoServer`类，追加一个新的处理程序逻辑

   ```java
   serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
       @Override
       protected void initChannel(SocketChannel socketChannel) throws Exception {
           socketChannel.pipeline().addLast(new StringEncoder(CharsetUtil.UTF_8));
           socketChannel.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8));
           socketChannel.pipeline().addLast(new EchoServerHandler());
       }
   });
   ```

2. 修改`EchoServerHandler`程序类，使用字符串进行数据的接收

   ```java
   @Override
   public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
       String inputStr = (String) msg;
       String echoContent = "【ECHO】" + inputStr;
       if ("exit".equalsIgnoreCase(inputStr)) {
           echoContent = "quit";
       } else if (inputStr.startsWith("userid")) {
           echoContent = "【服务器端】 欢迎“" + inputStr.split(":")[1] + "”登录访问，连接通道已经建立成功，可以开始进行服务器通信处理";
       }
       ChannelFuture future = ctx.writeAndFlush(echoContent);
       future.addListener(new ChannelFutureListener() {
           @Override
           public void operationComplete(ChannelFuture future) throws Exception {
               if (future.isSuccess()) {
                   System.out.println("******* 服务器端回应客户端请求成功。");
               }
           }
       });
   }
   ```

3. 修改`EchoClient`客户端程序类，追加由同样的字符串解码器

   ```java
   clientBootstrap.handler(new ChannelInitializer<SocketChannel>() {
       @Override
       protected void initChannel(SocketChannel ch) throws Exception {
           ch.pipeline().addLast(new StringEncoder(CharsetUtil.UTF_8));
           ch.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8));
           ch.pipeline().addLast(new EchoClientHandler());
       }
   });
   ```

4. 修改`EchoClientHandler`类之中也应该可以直接使用`String`处理数据

   ```java
   @Override
   public void channelActive(ChannelHandlerContext ctx) throws Exception {
       String data = "userid:kuhnwei";
       ctx.writeAndFlush(data);
   }
   
   @Override
   public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
       String content = (String) msg;
       if ("quit".equalsIgnoreCase(content)) {
           System.out.println("#### 本次操作接收，已退出 ####");
           ctx.close();
       } else {
           System.out.println(" {客户端} " + content);
           String inputStr = InputUtil.getString("请输入要发送的信息：");
           ChannelFuture future = ctx.writeAndFlush(inputStr);
           future.addListener(new ChannelFutureListener() {
               @Override
               public void operationComplete(ChannelFuture future) throws Exception {
                   if (future.isSuccess()) {
                       System.out.println("******* 客户端信息发送成功");
                   }
               }
           });
       }
   }
   ```

在整个`Netty`进行项目处理的时候所有的`Handler`类只复制有最终的具体数据的执行，而中间对于数据的传输与处理部分完全都由`Netty`来实现。

### UDP程序实现

在网络通讯志宏除了使用`TCP`(可靠连接)之外也会使用`UDP`(不可靠的连接)实现数据报信息传输，本次的讲解还是分为两个项目模块：广播消息发送端、广播消息的接收端。

1. 创建一个`UDPClientHandler`程序类

   ```java
   public class UDPClientHandler extends ChannelHandlerAdapter {
       @Override
       public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
           DatagramPacket packet = (DatagramPacket) msg;
           System.out.println("【客户端】（监听者）数据：" + packet.content().toString(CharsetUtil.UTF_8));
           System.out.println("【客户端】（监听者）来源：" + packet.sender());
       }
   
       @Override
       public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
           ctx.close();
       }
   }
   ```

2. 创建`UDPClient`程序类进行数据的接收处理。

   ```java
   public class UDPClient {
       public void run() throws Exception {
           EventLoopGroup group = new NioEventLoopGroup();
           try {
               Bootstrap clientBootstrap = new Bootstrap();
               clientBootstrap.group(group);
               clientBootstrap.channel(NioDatagramChannel.class);
               clientBootstrap.handler(new UDPClientHandler());
               clientBootstrap.option(ChannelOption.SO_BROADCAST, true);
               clientBootstrap.bind(ServerInfo.PORT).sync().channel().closeFuture().await();
           } catch (Exception e) {
               group.shutdownGracefully();
           }
       }
   
       public static void main(String[] args) throws Exception {
           new UDPClient().run();
       }
   }
   ```

3. 开发服务端的程序进行消息的发送处理`UDPServerHandler`

   ```java
   public class UDPServerHandler extends ChannelHandlerAdapter {
       @Override
       public void channelActive(ChannelHandlerContext ctx) throws Exception {
           String str = "【MOHIST】www.mohist.site";
           ByteBuf data = Unpooled.copiedBuffer(str, CharsetUtil.UTF_8);
           InetSocketAddress address = new InetSocketAddress("255.255.255.255", ServerInfo.PORT);
           DatagramPacket packet = new DatagramPacket(data, address);
           ctx.writeAndFlush(packet).sync();
       }
   
       @Override
       public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
           System.out.println("服务端出现异常!");
       }
   }
   ```

4. 创建`UDPServer`程序类，进行数据的发送配置

   ```java
   public class UDPServer {
       public void run() throws Exception {
           EventLoopGroup group = new NioEventLoopGroup();
           try {
               Bootstrap serverBootstrap = new Bootstrap();
               serverBootstrap.group(group);
               serverBootstrap.channel(NioDatagramChannel.class);
               serverBootstrap.handler(new UDPServerHandler());
               serverBootstrap.option(ChannelOption.SO_BROADCAST, true);
               ChannelFuture future = serverBootstrap.bind(0).sync();
               future.channel().closeFuture().sync();
           } catch (Exception e) {
               group.shutdownGracefully();
           }
       }
   
       public static void main(String[] args) throws Exception {
           new UDPServer().run();
       }
   }
   ```

5. `UDP`程序的最大特点就是需要进行广播的控制处理操作，但是对于广播的控制也需要注意一个问题，在一些网络处理里面由可能会出现一条数重复接收的情况，那么此时可以修改缓冲区的大小配置，修改`UDPClient`程序类

   ```java
   clientBootstrap.option(ChannelOption.SO_RCVBUF, 2048);
   ```

   如果出现由重复的接收处理（重复的接收也可以通过一些其它的逻辑判断），那么就可以追加以上的配置实现。

## 粘包与拆包处理

在进行网络通讯的时候本身就是一场数据的传送与接收的游戏，在进行数据传输的时候如果数据量很大的情况下未必可以一次性的将数据发送服务端，可能需要拆分几次才可以完成，这样的操作就称为拆包，反之，对于这些拆包的整合就称为粘包。

### 粘包与拆包操作问题分析

在`TCP`协议通讯过程之中，当客户端与服务器端建立连接之后则需要进行一系列的数据回应处理，而在进行数据回应处理的时候对于数据量过大或者数据量过小都有可能造成传输的数据不准确问题。

![1557974694111](img/1557974694111.png)

这个时候就需要去思考了如果在我们自己所编写的程序代码里面（现在使用的是原生的完了过开发技术），面对于此时的操作那么该如何解决呢？那么面对于此时的操作需要通过如下的几点进行思考：

- 设置消息的边界，现在可能将换行设置为消息的边界内容，相当于在进行读取的时候如果发现没有换行了，则将读取的数据缓冲起来继续通过网络读取，一直读取到换行，则认为改消息读取完毕了；
- 在每一个消息的内容之前追加有一个头部的信息，假设传递的消息："www.mohist.site"，则在整个的消息前面追加10个字节长度的内容进行消息长度的传输；
- 设置一个定长的消息，现在假设每个数据包的长度为50，如果现在不够50则补充空字符串，这样在读取的时候就可以根据50个字节为一组进行数据的读取操作。

但是如果要在开发之中采用如此的方式来进行处理的时候，那么难度是非常高的，而且缓存中的数据操作的逻辑也异常复杂，而幸运的是`Netty`帮助我们解决了这样的问题，它提供由拆包与粘包的数据处理能力。

## 搭建HTTP服务器

`HTTP`服务器主要操作时进行`HTTP`请求处理的，如果要真使用`HTTP`请求处理最方便的肯定使用的时`Tomcat`、`Jetty`这样的精巧的`WEB`容器，但是为什么又需要通过`Netty`来实现？

`Tomcat`本身是基于`NIO`的一种应用，同时也是使用最为广泛的`WEB`容器，但是很多时候可能需要做一些小巧的定制型的`WEB`容器开发，那么在这样的情况下使用`Netty`（异步非阻塞的处理框架）最方便。

### HTTP简介

`HTTP`属于超文本传输协议，是在整个`WEB`开发过程之中使用最广泛的处理协议，同时也是所有的服务器必用的开发协议。但是需要清楚的是`HTTP`协议实际上是构建在`TCP`上的一种应用，在使用`HTTP`协议的时候本质上还是进行数据的传输与响应的。

![1557993129783](img/1557993129783.png)

`HTTP`请求处理之中依然遵循着`C/S`处理原则，只不过在整个的请求处理之中`C`变成了`B`（浏览器）。

### HTTP请求（request）

请求信息是由客户端发送给浏览器的，但是需要注意的是只要遵从`HTTP`协议标准的请求都可以发送到服务器端，也就是说发送请求的不一定只有浏览器，也可以通过客户端进行发送（例如：`HttpClient`、`Netty`）。

那么在进行请求的时候往往需要考虑请求模式，而对于请求模式也分为如下的两种情况：

- `HTTP 1.0`：`GET`、`POST`、`HEAD`；
- `HTTP 1.1`：`OPTIONS`、`PUT`、`DELETE`、`TRACE`、`CONNECT`；

| No.  | 方法    | 描述                                                         |
| ---- | ------- | ------------------------------------------------------------ |
| 1    | GET     | 请求制定的页面信息，并返回实体主体。                         |
| 2    | HEAD    | 类似于GET请求，只不过返回的响应中没有具体的内容，用于获取报头。 |
| 3    | POST    | 像制定资源提交数据进行处理请求（例如提交表单或者上传文件），数据被包含在请求体中，POST请求可能会导致新的资源的建立或和已有资源的修改。 |
| 4    | PUT     | 从客户端向服务器传送的数据取代指定文档的内容。               |
| 5    | DELETE  | 请求服务器删除指定的页面。                                   |
| 6    | CONNECT | HTTP/1.1协议中预留给能够将连接改为管道方式的代理服务器。     |
| 7    | OPTIONS | 允许客户端查看服务器的性能。                                 |
| 8    | TRACE   | 回显服务器收到的请求，主要用于测试或诊断。                   |

随着技术的不断发展，很多开发者认为使用这么多的复杂模式来进行请求处理实在是过于麻烦了，所以又一部分的开发者坚持只使用`GET`/`POST`来完成，而有另外一部分的开发者开始考虑使用不同的请求模式来进行处理，例如：以`restful`处理架构为例；

在进行请求的时候除了数据本身之外，还会包含有头部的信息内容，而在请求处理之中常见的头部信息如下：

| No.  | 头信息              | 描述                                                         | 示例                                                   |
| ---- | ------------------- | ------------------------------------------------------------ | ------------------------------------------------------ |
| 01   | Accept              | 指定客户端能够接收的内容类型                                 | Accept: text/plain, text/html                          |
| 02   | Accept-Charset      | 浏览器可以接收的字符编码集                                   | Accept-Charset:ios-8859-5                              |
| 03   | Accept-Encoding     | 指定浏览器可以支持的web服务器返回内容压缩编码类型            | Accept-Encoding:compress, gzip                         |
| 04   | Accept-Language     | 浏览器可以接收的语言                                         | Accept-Language:en,zh                                  |
| 05   | Accept-Ranges       | 可以请求网页实体的一个或者多个子范围字段                     | Accept-Ranges:bytes                                    |
| 06   | Authorization       | HTTP授权的授权证书                                           | Authorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==      |
| 07   | Cache-Control       | 指定请求和响应遵循的缓存机制                                 | Cache-Control: no-cache                                |
| 08   | Connection          | 表示是否需要持久连接。（HTTP 1.1默认进行持久连接）           | Connection: close                                      |
| 09   | Cookie              | HTTP请求发送时，会把保存在该请求域名下的所有cookie值一起发送给web服务器。 | Cookie: $Version=1; Skin=new;                          |
| 10   | Content-Length      | 请求的内容长度                                               | Content-Length: 348                                    |
| 11   | Content-Type        | 请求的与实体对应的MIME信息                                   | Content-Type: application/x-www-form-urlencoded        |
| 12   | Date                | 请求发送的日期和时间                                         | Date: Tue, 15 Nov 1987 08:12:32 GMT                    |
| 13   | Expect              | 请求特定的服务器行为                                         | Expect: 100-continue                                   |
| 14   | From                | 发出请求的用户额Email                                        | From: email@kuhnwei.com                                |
| 15   | Host                | 指定请求的服务器的域名和端口号                               | Host: www.mohist.site                                  |
| 16   | If-Match            | 只有请求内容与实体相匹配才有效                               | If-Match:"737060cd8c284d8af7ad3082f209582d"            |
| 17   | If-Modified-Since   | 如果请求的部分在指定时间之后被修改则请求成功，未被修改则返回304代码 | If-Modified-Since: Sat, 29 Oct 1987 19:43:31 GMT       |
| 18   | If-None-Match       | 如果内容未改变返回304代码，参数为服务器先前发送的Etag，与服务器回应的Etag比较判断是否改变 | If-None-Match:"737060cd8c284d8af7ad3082f209582d"       |
| 19   | If-Range            | 如果实体未改变，服务器发送客户端丢失的部分，否则发送整个实体。参数也为Etag | If-Range:"737060cd8c284d8af7ad3082f209582d"            |
| 20   | If-Unmodified-Since | 只在实体指定时间之后未被修改才请求成功                       | If-Unmodified-Since:Sat, 29 Oct 1987 19:43:31 GMT      |
| 21   | Max-Forwards        | 限制信息通过代理和网关传送的时间                             | Max-Forwards: 10                                       |
| 22   | Pragma              | 用来包含实现特定的指令                                       | Pragma: no-cache                                       |
| 23   | Proxy-Authorization | 连接到代理的授权证书                                         | Proxy-Authorization: Basic QWxhGRpbjpvcGVuIHNlc2FtZQ== |
| 24   | Range               | 只请求实体的一部分，指定范围                                 | Range:bytes=500-999                                    |
| 25   | Referer             | 先前网页的地址，当前请求网页紧随其后，即来路                 | Referer: http://www.mohist.site/xxx/12.html            |
| 26   | TE                  | 客户端愿意接收的传输编码，并通知服务器接收首尾加头inxi       | TE：trailers,deflate;q=0.5                             |
| 27   | Upgrade             | 向服务器指定某种传输协议以便服务器进行转换（如果支持）       | Upgrade:HTTP/2.0,SHTTP/1.3,IRC/6.9,RFA/x11             |
| 28   | User-Agent          | User-Agent的内容包含发出请求的用户信息                       | User-Agent: Mozilla/5.0(Linux; X11)                    |
| 29   | Via                 | 通知中间网管或代理服务器地址，通信协议                       | Via: 1.0 fred, 1.1 nowhere.com(Apache/1.1)             |
| 30   | Warning             | 关于消息实体的警告信息                                       | Warn: 199 Miscellaneous warning                        |

### HTTP响应（response）

在进行`HTTP`响应过程之中首先需要注意的就是要给请求编码，如果请求编码为200则表示请求成功可以开始进行数据的返回，而对于常见的`HTTP`状态码如下：

| No.  | 分类 | 描述                                           |
| ---- | ---- | ---------------------------------------------- |
| 1    | 1**  | 信息，服务器收到请求，需要请求者继续执行操作   |
| 2    | 2**  | 成功，操作被成功接收并处理                     |
| 3    | 3**  | 重定向，需要进一步的操作以完成请求             |
| 4    | 4**  | 客户端错误，请求包含语法错误或无法完成请求     |
| 5    | 5**  | 服务器错误，服务器在处理请求的过程中发生了错误 |

以上表示的只是整个`HTTP`状态码的分类，而常见的状态码如下：

| No.  | 状态码 | 英文名称                        | 描述                                                         |
| ---- | ------ | ------------------------------- | ------------------------------------------------------------ |
| 01   | 100    | Continue                        | 继续。客户端应继续其请求                                     |
| 02   | 101    | Switching Protocols             | 切换协议。服务器根据客户端的请求切换协议。只能切换到更高级的协议，例如，切换到HTTP的新版本协议 |
| 03   | 200    | OK                              | 请求成功。一般用于GET与POST请求                              |
| 04   | 201    | Created                         | 已创建。成功请求并创建了新的资源                             |
| 05   | 202    | Accepted                        | 以接受。已经接受请求，但未处理完成                           |
| 06   | 203    | Non-Authoritative Infomation    | 非授权信息。请求成功。但返回的meta信息不再原始的服务器，而是一个副本 |
| 07   | 204    | No Content                      | 无内容。服务器成功处理，但未返回内容。在未更新网页的情况下，可确保浏览器继续显示当前文档 |
| 08   | 205    | Reset Content                   | 重置内容。服务器处理成功，用户终端（例如：浏览器）应充值文档视图。可通过此返回码清楚浏览器的表单域 |
| 09   | 206    | Partial Content                 | 部分内容。服务器成功处理部分GET请求                          |
| 10   | 300    | Multiple Choices                | 多种选择。请求的资源可包括多个位置，响应可返回一个资源特征与地址的列表用于用户终端选择 |
| 11   | 301    | Moved Permanently               | 永久移动。请求的资源已被永久的移动到新URI，返回信息会包括新URI，浏览器会自动定向到新URI。今后任何新的请求都应该使用新的URI代替 |
| 12   | 302    | Found                           | 临时移动。与301类似。但资源只是临时被移动。客户端应继续使用原有URI |
| 13   | 303    | See Other                       | 查看其它地址。与301类似。使用GET和POST请求查看               |
| 14   | 304    | Not Modified                    | 未修改。所请求的资源未修改，服务器返回此状态码时，不会返回任何资源。客户端通常会缓存访问过的资源，通过提供一个头信息指出客户端希望只返回在指定日期之后修改的资源 |
| 15   | 305    | Use Proxy                       | 使用代理。所请求的资源必须通过代理访问                       |
| 16   | 306    | Unused                          | 已经被废弃的HTTP状态码                                       |
| 17   | 307    | Temporary Redirect              | 临时重定向。与302类似。使用GET请求重定向                     |
| 18   | 400    | Bad Request                     | 客户端请求的语法错误，服务器无法理解                         |
| 19   | 401    | Unauthorized                    | 请求要求用户的身份认证                                       |
| 20   | 402    | Payment Required                | 保留，将来使用                                               |
| 21   | 403    | Forbidden                       | 服务器理解请求客户端的请求，但是拒绝执行此请求               |
| 22   | 404    | Not Found                       | 服务器无法根据客户端的请求找到资源。通过此代码，网站涉及人员可以设置“您所请求的资源无法找到”的个性页面 |
| 23   | 405    | Method Not Allowed              | 客户端请求中的方法被禁止                                     |
| 24   | 406    | Not Acceptable                  | 服务器无法根据客户端请求的内容特性完成请求                   |
| 25   | 407    | Proxy Authentication Required   | 请求要求代理的身份认证，与401类似，但请求者应当使用代理进行授权 |
| 26   | 408    | Request Time-out                | 服务器等待客户端发送的请求时间过长，超时                     |
| 27   | 409    | Conflict                        | 服务器完成客户端的PUT请求时可能返回此代码，服务器处理请求时发生了冲突 |
| 28   | 410    | Gone                            | 永久删除了可使用410代码，网站涉及人员可通过301代码指定资源的新位置 |
| 29   | 411    | Length Required                 | 服务器无法处理客户端发送的不带Content-Length的请求信息       |
| 30   | 412    | Precondition Failed             | 客户端请求信息的先决条件错误                                 |
| 31   | 413    | Request Entity Too Large        | 由于请求的实体过大，服务器无法处理，因此拒绝请求。为防止客户端的连续请求，服务器可能会关闭连接。如果只是服务器暂时无法处理，则会包含一个Retry-After的响应信息 |
| 32   | 414    | Request-URI Too Large           | 请求的URI过长，服务器无法处理                                |
| 33   | 415    | Unsupported Media Type          | 服务器无法处理请求附带的媒体格式                             |
| 34   | 416    | Requested range not satisfiable | 客户端请求的范围无效                                         |
| 35   | 417    | Expectation Failed              | 服务器无法满足Expect的请求头信息                             |
| 36   | 500    | Internal Server Error           | 服务器内部错误，无法完成请求                                 |
| 37   | 501    | Not Implemented                 | 服务器不支持请求的功能 ，无法完成请求                        |
| 38   | 502    | Bad Gateway                     | 充当网关或代理的服务器，从远端服务器接收到了一个无效的请求   |
| 39   | 503    | Service Unavailable             | 由于超载或系统维护，服务器暂时无法处理客户端的请求。延时的长度可包含在服务器的Retry-After头信息中 |
| 40   | 504    | Gateway Time-out                | 充当网管或代理的服务器，未及时从远端服务器获取请求           |
| 41   | 505    | HTTP Version not supported      | 服务器不支持请求                                             |

对于请求而言，也需要考虑响应返回的`HTTP`头部信息问题，以下列出了一些常见的`HTTP`请求的头信息内容。

| No.  | 头信息             | 描述                                                      |
| ---- | ------------------ | --------------------------------------------------------- |
| 01   | Accept-Ranges      | 表明服务器是否支持指定范围请求及那种类型的分段请求        |
| 02   | Age                | 从原始服务器到代理缓存形成的估算时间（以秒计，非负）      |
| 03   | Allow              | 对某网络资源的有效请求行为，不允许则返回405               |
| 04   | Cache-Control      | 告诉所有的缓存机制是否可以缓存及那种类型                  |
| 05   | Content-Encoding   | web服务器支持的返回内容压缩编码类型                       |
| 06   | Content-Language   | 响应体的语言                                              |
| 07   | Content-Length     | 响应体的长度                                              |
| 08   | Content-Location   | 请求资源可替代的备用的另一地址                            |
| 09   | Content-MD5        | 返回资源的MD5校验值                                       |
| 10   | Content-Range      | 在整个返回体中本部分的字节位置                            |
| 11   | Content-Type       | 返回内容的MIME类型                                        |
| 12   | Date               | 原始服务器消息发出的时间                                  |
| 13   | ETag               | 请求变量的实体标签的当前值                                |
| 14   | Expires            | 响应过期的日期和时间                                      |
| 15   | Last-Modified      | 请求资源的最后修改时间                                    |
| 16   | Location           | 用来重定向接收方到非请求URL的位置来完成请求或标识新的资源 |
| 17   | Pragma             | 包括实现特定的指令，它可应用到响应链上的                  |
| 18   | Proxy-Authenticate | 它指出认证方案和可应用到代理的该URL上的参数               |
| 19   | refresh            | 应用于重定向或要给新的资源被创造，在5秒之后重定向         |
| 20   | Retry-After        | 如果实体暂时不可取，通知客户端在指定时间之后再次尝试      |
| 21   | Server             | web服务器软件名称                                         |
| 22   | Set-Cookie         | 设置Http Cookie                                           |
| 23   | Trailer            | 指出头域在分块传输编码的尾部存在                          |
| 24   | Transfer-Encoding  | 文件传输编码                                              |
| 25   | Vary               | 告诉下游代理是使用缓存响应还是从原始服务器请求            |
| 26   | Via                | 告知代理客户端响应是通过哪里发送的                        |
| 27   | Warning            | 警告实体可能存在的问题                                    |
| 28   | WWW-Authenticate   | 表明客户端请求实体应该使用的授权方案                      |

如果要自定义`HTTP`服务器，则就必须手工处理信息和响应的状态问题，只有在自定义的情况下才可以编写出更加高效且安全的`HTTP`服务器应用。

如果现在要基于`Netty`实现`HTTP`服务器，则需要注意要给编解码问题。

![1557999005671](img/1557999005671.png)

### 编写HTTP服务器

在`Netty`之中针对于`HTTP`服务器的开发分两种处理情况：

- 处理请求：`io.netty.handler.codec.http.HttpRequest`;
- 处理数据：`io.netty.handler.codec.http.HttpContent`。

也就是说在使用`Netty`开发的时候对于`ChannelHandler`子类需要考虑两种不同的处理形式在同一次请求之中的状态。

```java
public class HttpServerHandler extends ChannelHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) msg;
            System.out.println("【HTTP接收请求】 uri = " + request.uri() + "; method = " + request.method());
            String content = "<html><head><title>MOHIST</title></head><body><h1>www.mohist.site</h1></body></html>";
            this.responseWrite(ctx, content);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

    private void responseWrite(ChannelHandlerContext ctx, String content) {
        ByteBuf buf = Unpooled.copiedBuffer(content, CharsetUtil.UTF_8);
        HttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html;charset=UTF-8");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, String.valueOf(buf.readableBytes()));
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }
}
```

```java
public class HttpServer {
    public void  run() throws Exception {
        EventLoopGroup boosGrop = new NioEventLoopGroup();
        EventLoopGroup workGrop = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boosGrop, workGrop);
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new HttpResponseEncoder());
                    ch.pipeline().addLast(new HttpRequestDecoder());
                    ch.pipeline().addLast(new HttpServerHandler());
                }
            });
            serverBootstrap.option(ChannelOption.SO_BACKLOG, 64);
            ChannelFuture future = serverBootstrap.bind(ServerInfo.PORT).sync();
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            boosGrop.shutdownGracefully();
            workGrop.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("****************** 服务器正常启动 *********************");
        new HttpServer().run();
    }
}
```

```
****************** 服务器正常启动 *********************
【HTTP接收请求】 uri = /; method = GET
【HTTP接收请求】 uri = /favicon.ico; method = GET
```

### SESSION管理

在`HTTP`服务创建的过程里面最为重要的部分就是针对于`session`的管理，`HTTP`协议本身是属于无状态的，所以每一次请求的时候服务器都无法知道其上一次请求的状态是什么，所以为了清楚的知道客户的状态，引入了`session`的概念，而`session`就是在服务器端保持的一组数据，这组数据实际上就是保存在了一个`Map`集合里面，现在如果要使用`Netty`开发自定义的`HTTP`服务器就必须考虑到`session`的管理问题，而一旦有`session`的管理问题就牵扯到了`Cookie`的处理机制。

1. 创建一个`HttpSession`接口

   ```java
   public interface HttpSession {
       static final String SESSION = "MOHIST_SESSION";
       Object getAttribute(String name);
       void setAttribute(String name, Object value);
       void removeAttribute(String name);
       String getId();
       void invalidate();
   }
   ```

2. 定义`HttpSession`接口的实现子类

   ```java
   public class DefaultHttpSession implements HttpSession {
   
       private String sessionId;
       private Map<String, Object> attributes = new HashMap<>();
   
       public DefaultHttpSession() {
           this.sessionId = UUID.randomUUID().toString();
       }
   
       @Override
       public Object getAttribute(String name) {
           return this.attributes.get(name);
       }
   
       @Override
       public void setAttribute(String name, Object value) {
           this.attributes.put(name, value);
       }
   
       @Override
       public void removeAttribute(String name) {
           this.attributes.remove(name);
       }
   
       @Override
       public String getId() {
           return this.sessionId;
       }
   
       @Override
       public void invalidate() {
           this.sessionId = null;
       }
   }
   ```

3. 定义`Session`管理类`HttpSessionManager`

   ```java
   public class HttpSessionManager {
       private static final Map<String, HttpSession> SESSION_MAP = new ConcurrentHashMap<>();
   
       public static String createSession() {
           HttpSession session = new DefaultHttpSession();
           String sessionId = session.getId();
           SESSION_MAP.put(sessionId, session);
           return sessionId;
       }
   
       public static boolean isExists(String sessionId) {
           if (SESSION_MAP.containsKey(sessionId)) {
               HttpSession session = SESSION_MAP.get(sessionId);
               if (session.getId() == null) {
                   SESSION_MAP.remove(sessionId);
                   return false;
               }
               return true;
           }
           return false;
       }
   
       public static void invalidate(String sessionId) {
           SESSION_MAP.remove(sessionId);
       }
   
       public static HttpSession getSession(String sessionId) {
           return SESSION_MAP.get(sessionId);
       }
   }
   ```

   对于这种`Session`管理最合适的做法应该使用`JUC`中的延迟队列形式进行定时弹出，随后设置一个后台的守护线程进行无用`Session`检测。

4. 修改`HttpServerHandler`程序类，进行`session`控制的整合

   ```java
   public class HttpServerHandler extends ChannelHandlerAdapter {
   
       private HttpRequest request;
       private HttpResponse response;
       private HttpSession session;
   
       @Override
       public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
           if (msg instanceof HttpRequest) {
               this.request = (HttpRequest) msg;
               System.out.println("【HTTP接收请求】 uri = " + this.request.uri() + "; method = " + this.request.method());
               String content = "<html><head><title>MOHIST</title></head><body><h1>www.mohist.site</h1></body></html>";
               this.responseWrite(ctx, content);
           }
       }
   
       @Override
       public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
           cause.printStackTrace();
       }
   
       private void responseWrite(ChannelHandlerContext ctx, String content) {
           ByteBuf buf = Unpooled.copiedBuffer(content, CharsetUtil.UTF_8);
           this.response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);
           if (!this.hasCookie()) {
               this.createCookie();
           }
           this.response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html;charset=UTF-8");
           this.response.headers().set(HttpHeaderNames.CONTENT_LENGTH, String.valueOf(buf.readableBytes()));
           ctx.writeAndFlush(this.response).addListener(ChannelFutureListener.CLOSE);
       }
   
       private void createCookie() {
           String encode = ServerCookieEncoder.encode(HttpSession.SESSION, HttpSessionManager.createSession());
           this.response.headers().set(HttpHeaderNames.SET_COOKIE, encode);
       }
   
       private boolean hasCookie() {
           String cookieStr = (String) this.request.headers().get("Cookie");
           if (cookieStr == null || "".equals(cookieStr)) {
               return false;
           }
           Set<Cookie> cookieSet = ServerCookieDecoder.decode(cookieStr);
           for (Cookie cookie : cookieSet) {
               if (HttpSession.SESSION.equals(cookie.name())
                       && HttpSessionManager.isExists(cookie.value())) {
                   this.session = HttpSessionManager.getSession(cookie.value());
                   return true;
               }
           }
           return false;
       }
   }
   ```

   此时按照了`Tomcat`的实现形式实现了`Cookie`的整体处理流程，现在可以特别清楚的发现，`Cookie`工作在客户端，而`Session`工作在服务器端，同时`Session`中是需要通过集合来进行`Session`数据保存的。