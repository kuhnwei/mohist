package com.kuhnwei.mohist.examples.redis.simple;

import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * Redis 连接 测试类
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/1/26 21:12
 */
public class TestRedisConnection {

    @Test
    public void getJedis() {
        RedisConnection rc = new RedisConnection();
        Jedis jedis = rc.getJedis();
        jedis.set("test_redis_key", "test_redis_value");
        System.out.println(jedis.keys("test_*"));
        System.out.println(jedis.get("test_redis_key"));
        rc.close();
    }
}
