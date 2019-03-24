package com.tg.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.function.Function;

/**
 * Created by linzc on 2018/11/26.
 */
public class RedisUtils {
    private RedisUtils() {
        /*nothing to do*/
    }

    /**
     * redis eval
     *
     * @param jedisPool jedis pool
     * @param function  callback
     * @param <R>       Type
     * @return Result
     */
    public static <R> R eval(JedisPool jedisPool, Function<Jedis, R> function) {
        Jedis jedis = null;
        try {
            if (jedisPool == null) {
                return null;
            }
            jedis = jedisPool.getResource();
            if (jedis == null) {
                return null;
            }
            return function.apply(jedis);
        } finally {
            if (jedis != null) {
                //返还到连接池
                jedis.close();
            }
        }
    }
}
