package com.kuhnwei.mohist.examples.redis.pool;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * redis线程池测试类
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/1/26 21:46
 */
public class TestRedisConnectionPool {

    private RedisConnectionPool redisConnectionPool;
    private Jedis jedis;

    @Before
    public void before() {
        this.redisConnectionPool = new RedisConnectionPool();
        this.jedis = this.redisConnectionPool.getJedis();
    }

    @Test
    public void test() {
        System.out.println(this.jedis.keys("*"));
    }

    @After
    public void after() {
        this.jedis.close();
        this.redisConnectionPool.close();
    }

}
