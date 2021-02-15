/*
 * 文件名：RedisConfig.java
 * 描述：项目配置。
 * 修改人：刘可
 * 修改时间：2021-02-15
 */
package com.example.demo.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.data.redis.connection.jedis.*;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.*;

import redis.clients.jedis.JedisPoolConfig;

/**
 * 便于使用RedisTemplate。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @see initTemplate
 * @see getJedisPoolConfig
 * @see getJedisConnectioFactory
 * @since 2021-02-15
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport
{
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.timeout}")
    private int timeout;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.jedis.pool.max-active}")
    private int maxActive;
    @Value("${spring.redis.jedis.pool.max-wait}")
    private int maxWait;
    @Value("${spring.redis.jedis.pool.max-idle}")
    private int maxIdle;
    @Value("${spring.redis.jedis.pool.min-idle}")
    private int minIdle;

    /**
     * 创建RedisTemplate。
     * 
     * @param factory 基于Jedis的连接创建连接工厂
     * @return RedisTemplate的弦串目标扩展。
     */
    @Bean
    public StringRedisTemplate initTemplate(JedisConnectionFactory factory)
    {
        StringRedisTemplate ret = new StringRedisTemplate();
        ret.setConnectionFactory(factory);
        FastJsonRedisSerializer<Object> serializer =
                new FastJsonRedisSerializer<>(Object.class);
        StringRedisSerializer stringSerializer = new StringRedisSerializer();
        ret.setKeySerializer(serializer);
        ret.setHashKeySerializer(serializer);
        ret.setValueSerializer(stringSerializer);
        ret.setHashValueSerializer(stringSerializer);
        ret.afterPropertiesSet();
        return ret;
    }

    /**
     * 创建配置。
     * 
     * @return 配置。
     */
    @Bean
    public JedisPoolConfig getJedisPoolConfig()
    {
        JedisPoolConfig ret = new JedisPoolConfig();
        ret.setMaxIdle(maxIdle);
        ret.setMaxWaitMillis(maxWait);
        ret.setMinIdle(minIdle);
        return ret;
    }

    /**
     * 创建工厂类。
     * 
     * @param config 配置
     * @return 基于Jedis的连接创建连接工厂。
     */
    @Bean
    public JedisConnectionFactory
            getJedisConnectioFactory(JedisPoolConfig config)
    {
        return new JedisConnectionFactory(config);
    }
}
