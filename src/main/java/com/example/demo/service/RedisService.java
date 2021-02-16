/*
 * 文件名：RedisService.java
 * 描述：项目主要服务。
 * 修改人：刘可
 * 修改时间：2021-02-16
 */

package com.example.demo.service;

import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSON;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.*;
import org.springframework.data.util.CastUtils;

/**
 * Redis服务。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @see set
 * @see deleteObj
 * @see isKeyExist
 * @see get
 * @since 2021-02-16
 */
@Service("redisService")
public class RedisService extends SessionService
{
    @Autowired
    protected StringRedisTemplate redis;

    @Override
    public void set(String key, Object obj, long liveTime)
    {

        if (!tool.containsNull(obj, key))
        {
            redis.opsForValue().set(
                    key, JSON.toJSONString(obj), liveTime, TimeUnit.MILLISECONDS
            );
        } // 结束：if (!tool.containsNull(obj, key))
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

        if (isKeyExist(key))
        {

            try
            {
                ValueOperations<String, String> operations =
                        redis.opsForValue();
                ret = (T)JSON.parseObject(operations.get(key), clazz);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        } // 结束：if (isKeyExist(key))
        return ret;
    }

    /**
     * 判断键是否存在。
     * 
     * @param key 键名
     * @param key Hash键名，非空
     * @return 描述键是否存在。
     */
    public boolean isHashKeyExist(String tag, Object key)
    {

        if (!tool.containsNull(tag, key))
        {
            return redis.opsForHash().hasKey(tag, key);
        } // 结束：if (!tool.containsNull(tag, key))
        return false;
    }

    /**
     * 将一个值插入Session。
     * 
     * @param tag 键名，非空
     * @param key Hash键名，非空
     * @param val Hash值，非空
     */
    public void setHash(String tag, Object key, Object val)
    {

        if (!tool.containsNull(tag, key, val))
        {
            redis.opsForHash().put(tag, key, val);
        } // 结束： if(!tool.containsNull(tag,key,val))
    }

    /**
     * 获取值。
     * 
     * @param <T> 返回值类型
     * @param tag 键名，非空
     * @param key Hash键名，非空
     * @return 键对应值，出现任何异常返回<code>null</code>。
     */
    public <T> T getHash(String tag, Object key)
    {
        T ret = null;

        if (!tool.containsNull(tag, key))
        {

            try
            {
                Object res = redis.opsForHash().get(tag, key);
                ret = CastUtils.cast(res);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        } // 结束： if(!tool.containsNull(tag,key))
        return ret;
    }

    /**
     * 删除哈希值。
     * 
     * @param tag 键名，非空
     * @param keys Hash键名，非空
     * @return 在管道/事务中使用时为<code>null</code>。
     */
    public Long deleteHash(String tag, Object... keys)
    {
        Long ret = 0L;

        if (!(tool.containsNull(tag, keys) || tool.containsNull(keys)))
        {
            ret = redis.opsForHash().delete(tag, keys);
        }
        return ret;
    }
}
