# Disruptor高并发框架

## 什么是Disruptor

- Martin Fowler在自己网站上写了一篇LMAX架构的文章，在文章中他介绍了LMAX是一种新型零售金融交易平台，它能够以很低的延迟产生大量交易。这个系统是建立在JVM平台上，其核心是一个业务逻辑处理器，它能够在一个线程里每秒处理6百万订单。业务逻辑处理器完全是运行在内存中，使用事件源驱动方式。业务逻辑处理器的核心是Disruptor。
- Disruptor它是一个开源的并发框架，并获得2011 Duke's程序框架创新奖，能够在无锁的情况下实现网络的Queue并发操作。

## Disruptor有什么特点

- Disruptor是一个Java的并发编程框架，大大的简化了并发程序开发的难度，在性能上也比Java本身提供的一些并发包要好。
- Disruptor是一个高性能异步处理框架，它实现了观察者模式。
- Disruptor它是无锁的、CPU友好；它不会清除缓存中的数据，只会覆盖，降低了垃圾回收机制启动的频率。

## RingBuffer

- RingBuffer是整个模式（Disruptor）的核心
- 它是一个首尾相接的环，用于在不同线程间传递数据的Buffer。
- RingBuffer拥有一个序号，这个序号指向数组中下一个可用的元素。
- RingBuffer采用数组实现，没有首尾指针。
- 随着不停填充这个buffer，这个序号会一直增长，知道绕过这个环。
- 前序号指向的元素，可以通过mode计算：序号 % 长度 = 索引，例如 10 % 8 = 2。
- 长度设置为2的N次方，有利于二进制计算机计算。使用：序号 & (长度 - 1) = 索引号，替代mode计算，效率更高。

## Disruptor开发

- 开发模型

  - 1、定义Event，代表Disruptor所处理的数据单元。
  - 2、定义Event工厂，实现EventFactory<?>接口，用来填充RingBuffer容器。
  - 3、定义Event处理器（消费者），实现EventHandler<?>接口，用来从RingBuffer中取出数据并处理。
  - 4、组合（1~3步）

- 发布事件固定模板

  ```java
  long sequece = ringBuffer.next();
  try {
      Object object = ringBuffer.get(sequece);
      // Do some work with the event.
  } finally {
      ringBuffer.publish(sequence);
  }
  ```

- EventTranslator: Disruptor提供了以下接口，简化Event的发布操作。

  - EventTranslator
  - EventTranslatorOneArg
  - EventTranslatorTwoArg
  - EventTranslatorThreeArg
  - EventTranslatorVararg

- ProducerType

  - 可以制定ProducerType.MULTI和ProducerType.SINGLE参数，来控制序列器的生成模式。默认使用MULTI模式。
  - 如果确认是在单线程环境下产生Event，应该调整为SINGLE模式，可以显著提高性能，因为不用处理并发下sequence的产生。
  - 如果在多线程情况下使用SINGLE模式，将会导致混乱，出现sequence丢失问题。

- ExceptionHandler(异常处理器)

  - 为EventHandler设置异常处理器，需要实现ExceptionHandler接口。
  - disruptor.setDefaultExceptionHandler方法用于设置默认异常处理器。
  - disruptor.handleExceptionsFor(eventHandler).with(exceptionHandler)代表为eventHandler指定具体的ExceptionHandler，会覆盖DefaultExceptionHandlerd的设置。

- WaitStrategy(等待策略)

  - Disruptor提供了多个WaitStrategy的实现，每种策略都具有不同性能和优缺点，根据实际运行环境CPU的硬件特点选择恰当的策略，并配合特定的JVM的配置参数，能够实现不同的性能提升。
  - BlockingWaitStrategy(阻塞等待)是最低效的策略，但其对CPU的消耗最小并且在各种不同部署环境中能提供更加一致的性能表现。
  - SleepingWaitStrategy()的性能表现跟BlockingWaitStrategy差不多，对CPU的消耗也类似，但其对生产者线程的影响最小，适合用于异步日志类似的场景。
  - YieldingWaitStrategy(放弃等待)的性能是最好的，适合用于低延迟的系统。在要求极高性能且事件处理线数小于CPU逻辑核心数的场景中，推荐使用此策略；列入，CPU开启超线程的特性。

## Disruptor核心解析

- 为什么这么快

  - Disruptor不是使用锁，通过内存屏障和原子性的CAS操作替换锁。 
  - 锁机制
    - 悲观锁：读写的时候都严格加锁，高并发下锁竞争问题严重，性能底下。
    - 乐观锁：读的时候不加锁、写的时候才加锁；性能有所提高，但是会引起读写不一致问题。
  - 缓存基于数组而不是链表，用位运算替代求模。缓存的长度总是2的n次方，这样可以用位运算`i & (length - 1)`替代 `i % length`。
  - 去除伪共享，CPU的缓存一般是以缓存行为最小单位的，对应主存的一块相应大小的单元；当前的缓存行大小一般是64字节，每个缓存行一次只能被一个CPU核访问，如果一个缓存行被多个CPU核访问，就会造成竞争，导致某个核必须等其他核处理完了才能继续处理，影响性能。去除伪共享就是确保CPU核访问某个缓存行时不会出现争用。
  - 预分配缓存对象，通过更新缓存里对象的属性而不是删除对象来减少垃圾回收。

- 缓存行

  - 数据在缓存中不是以独立的项来存储的，不是单独的变量或指针。缓存是由缓存行组成的，同程是64字节（如果是32位计算机则为32字节），它有效地引用主内存的一块地址。一个Java的long类型是8字节，因此在一个缓存行中可以存8个long类型的变量。
    ![1552274178131](C:\Users\kuhn\AppData\Roaming\Typora\typora-user-images\1552274178131.png)

- 伪共享问题

  - 免费缓存存在弊端，可能会将完全不相关的数据同时缓存，导致缓存命中失效，拖慢性能。
    ![1552274709960](C:\Users\kuhn\AppData\Roaming\Typora\typora-user-images\1552274709960.png)

  - Disruptor解决伪共享是通过增加补全来确保RingBuffer的序列号不会和其他东西同时存在于一个缓存行中

    ```java
    public long p1, p2, p3, p4, p5, p6, p7; // cache line padding
    private volatile long cursor = INITIAL_CURSOR_VALUE;
    public long p8, p9, p10, p11, p12, p13, p14; // cache line padding
    // 通过以上数据去填充其余7个位置的数据，保证RingBuffer的学历噩耗不会和其他的同时存在于一个缓存行中
    ```

## Disruptor与MQ

- MQ等中间件产品，AactiveMQ、RabbitMQ、Kafka等都是需要安装部署的，而Disruptor与应用绑定，随应用启动而启动。
- MQ解决的是服务之间的解耦，Disruptor解决的是内部线程间的解耦。
- 可以利用Disruptor开发自己的MQ中间件。
- MQ被服务公用，Disruptor为应用私有。MQ可以集群化，Disruptor无法水平部署，只能内部负载化。

## Disruptor误区

- 不要把Disruptor编程一个高性能的多线程工具类；而要充分利用它的流程化处理、依赖处理、多Handler处理相同对象的特点。
- 让一个Event经过一条设计好的流水线，输出最终结果。
- 掌握Disruptor的使用和架构方法后，重点应该关注的是流水线的设计、每一个工序的设计。
- 升级维护都是针对每一个工序、流程进行优化。
- 不是Disruptor不够好，而是我们的能力还不够！