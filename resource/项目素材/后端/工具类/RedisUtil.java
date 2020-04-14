package com.itheima.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ResourceBundle;

public class RedisUtil {

    private static JedisPool pool=null;
    static{
        //解析配置文件
        ResourceBundle redis = ResourceBundle.getBundle("redis");

        //获取值
        String host = redis.getString("host");
        //获取端口号
        int port =Integer.parseInt(redis.getString("port"));
        //获取最大等待时间
        int MaxWaitMillis =Integer.parseInt(redis.getString("MaxWaitMillis"));
        int MaxTotal =Integer.parseInt(redis.getString("MaxTotal"));
        int MaxIdle =Integer.parseInt(redis.getString("MaxIdle"));
        int MinIdle =Integer.parseInt(redis.getString("MinIdle"));




        //创建 连接池配置对象
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxWaitMillis(MaxWaitMillis);
        jedisPoolConfig.setMaxTotal(MaxTotal);
        jedisPoolConfig.setMaxIdle(MaxIdle);
        jedisPoolConfig.setMinIdle(MinIdle);
        //先创建连接池
        pool = new JedisPool(jedisPoolConfig, host, port);
    }

    public static Jedis getConnection(){
        //找连接池对象借出一个连接

        return pool.getResource();
    }

    public static void main(String[] args) {
        Jedis connection = getConnection();
        String name = connection.get("name");
        System.out.println(name);
        connection.close();
    }

}
