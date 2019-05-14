package com.kuhnwei.mohist.examples.redis.pool;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis 连接池
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/1/26 21:32
 */
public class RedisConnectionPool {

    /** redis 服务地址 */
    private static final String HOST = "118.24.19.196";

    /** redis 服务端口号 */
    private static final int PORT = 6379;

    /** redis 服务权限密码 */
    private static final String PASSWORD = "650901";

    /** */
    private static final int TIMEOUT = 2000;

    /** redis线程池最大连接数 */
    private static final int MAX_TOTAL = 1000;

    /** redis线程池的空闲连接数 */
    private static final int MAX_IDLE = 200;

    /** redis连接的最大等待时间 */
    private static final int MAX_WAIT_MILLIS = 1000;

    /** redis是否要进行连接测试 以保证返回的连接为可用连接 */
    private static final boolean TEST_ON_BORROW = true;

    private JedisPool pool;

    /**
     * 构造方法 初始化redis线程池
     */
    public RedisConnectionPool() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(MAX_TOTAL);
        poolConfig.setMaxIdle(MAX_IDLE);
        poolConfig.setMaxWaitMillis(MAX_WAIT_MILLIS);
        poolConfig.setTestOnBorrow(TEST_ON_BORROW);
        this.pool = new JedisPool(poolConfig, HOST, PORT, TIMEOUT, PASSWORD);
    }

    /**
     * 从redis线程池获取jedis对象
     * @return Jedis
     */
    public Jedis getJedis() {
        return this.pool.getResource();
    }

    /**
     * 关闭redis线程池
     */
    public void close() {
        this.pool.close();
    }

}
