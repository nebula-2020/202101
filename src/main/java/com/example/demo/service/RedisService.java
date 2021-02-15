/*
 * 文件名：RedisService.java
 * 描述：项目主要服务。
 * 修改人：刘可
 * 修改时间：2021-02-15
 */

package com.example.demo.service;

import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSON;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.*;

/**
 * Redis服务。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @see set
 * @see deleteObj
 * @see isKeyExist
 * @see get
 * 
 * @since 2021-02-15
 */
@Service("redisService")
public class RedisService extends SessionService
{
    @Autowired
    protected StringRedisTemplate redis;

    @Override
    public void set(String key, Object obj, long liveTime)
    {
        redis.opsForValue().set(
                key, JSON.toJSONString(obj), liveTime, TimeUnit.MILLISECONDS
        );
    }

    @Override
    public boolean deleteObj(String key)
    {
        return redis.delete(key);
    }

    @Override
    public boolean isKeyExist(String key)
    {
        return redis.hasKey(key);
    }

    /**
     * 获取值。
     * 
     * @param <T> 返回值类型
     * @param key 键名
     * @param clazz 描述返回值类型
     * @return 键对应值，出现任何异常返回<code>null</code>。
     */
    @Override
    public <T> T get(String key, Class<T> clazz)
    {
        T ret = null;

        try
        {
            ValueOperations<String, String> operations = redis.opsForValue();
            System.out.println(operations.get(key));
            ret = (T)JSON.parseObject(operations.get(key), clazz);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ret;
    }
}
