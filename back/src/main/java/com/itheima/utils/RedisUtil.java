package com.itheima.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ResourceBundle;

/**
 *
 * 这个工具类不是最终版本
 * rc  release candidate 稳定版候选版本
 *
 *
 *
 */
public class RedisUtil {
    private static JedisPool pool;
    static{
        JedisPoolConfig config = new JedisPoolConfig();

        //这个工具 填名字不用填后缀properties
        ResourceBundle redis = ResourceBundle.getBundle("redis");
        String host = redis.getString("host");
        Integer port = Integer.parseInt(redis.getString("port"));
        Integer minIdle = Integer.parseInt(redis.getString("minIdle"));
        Integer maxIdle = Integer.parseInt(redis.getString("maxIdle"));
        Integer maxTotal = Integer.parseInt(redis.getString("maxTotal"));

        config.setMinIdle(minIdle);
        config.setMaxIdle(maxIdle);
        config.setMaxTotal(maxTotal);


        //创建一个连接池
        pool = new JedisPool(config, host, port);
    }
    public static Jedis getConnection(){
        //需要连接 借一个出来
        Jedis jedis = pool.getResource();
        return jedis;
    }
}
