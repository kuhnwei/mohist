package com.kuhnwei.mohist.examples.redis.simple;

import redis.clients.jedis.Jedis;

/**
 * Redis 连接
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/1/26 20:46
 */
public class RedisConnection {

    /** redis 服务地址 */
    private static final String HOST = "118.24.19.196";

    /** redis 服务端口号 */
    private static final int PORT = 6379;

    /** redis 服务密码 */
    private static final String PASSWORD = "650901";

    /** jedis 对象 */
    private Jedis jedis;

    /**
     * 构造方法 初始化jedis 连接redis服务
     */
    public RedisConnection() {
        try {
            this.jedis = new Jedis(HOST, PORT);
            jedis.auth(PASSWORD);
            String pong = "PONG";
            if (pong.equals(this.jedis.ping())) {
                System.out.println("------------------ Redis( "+ HOST + ":" + PORT + "): 服务连接成功 --------------------");
            }
        } catch (Exception e) {
            System.err.println("------------------ Redis( " + HOST + ":" + PORT + "): 服务连接异常 --------------------");
        }
    }

    /**
     * 获取jedis实例化对象
     * @return Jedis
     */
    public Jedis getJedis() {
        return this.jedis;
    }

    /**
     * 断开redis的服务连接
     */
    public void close() {
        this.jedis.close();
    }
}
