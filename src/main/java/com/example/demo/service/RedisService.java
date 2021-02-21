/*
 * 文件名：RedisService.java
 * 描述：项目主要服务。
 * 修改人：刘可
 * 修改时间：2021-02-22
 */

package com.example.demo.service;

import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.constant.Constants;
import com.example.demo.vo.*;

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
 * @see setSmsSession
 * @see getSmsSession
 * @see set
 * @see deleteObj
 * @see isKeyExist
 * @see get
 * @since 2021-02-22
 */
@Service("redisService")
public class RedisService extends SessionService
{
    @Autowired
    protected StringRedisTemplate redis;

    /**
     * 将一条短信验证用Session存入数据库。
     * <p>
     * 将以<b>用户手机号</b>为键存储数据。
     * 
     * @param vo 待储存对象
     * @param time Session保存时间，单位为毫秒
     * @return 描述操作成功与否。
     * @throws NullPointerException 参数为<code>null</code>。
     */
    public boolean setSmsSession(SmsVO vo, long time)
    {

        if (vo == null)
        {
            throw new NullPointerException();
        } // 结束：if (vo == null)
        boolean ret = false;

        // 对应键session必须不存在
        if (!isKeyExist(vo.getPhone()))
        {

            try
            {
                JSONObject val = new JSONObject();
                val.put(Constants.KEY_SMS_CODE, vo.getCode());
                val.put(Constants.KEY_SMS_JS, vo.getKey());
                val.put(Constants.KEY_SMS_SECRET, vo.getSecret());
                val.put(
                        Constants.KEY_SMS_TIME, Long.valueOf(vo.getCreateTime())
                );
                set(vo.getPhone(), val.toJSONString(), time);
                ret = true;
            }
            catch (NullPointerException e)
            {
                e.printStackTrace();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        } // 结束：if (!isKeyExist(vo.getPhone()))
        return ret;
    }

    /**
     * 在Session中获取指定手机号的对应短信验证用信息。
     * 
     * @param phone 手机号
     * @return 短信验证码校验用信息，返回<code>null</code>系出现异常或未找到值。
     * @throws IllegalArgumentException 参数为<code>null</code>或空值。
     */
    public SmsVO getSmsSession(String phone)
    {

        if (tool.isNullOrEmpty(phone))
        {
            throw new IllegalArgumentException();
        } // 结束：if (tool.isNullOrEmpty(phone))
        SmsVO ret = null;

        try
        {

            if (isKeyExist(phone))
            {
                String val = get(phone, String.class);
                JSONObject res = JSONObject.parseObject(val);
                ret = new SmsVO(
                        phone, res.getString(Constants.KEY_SMS_CODE),
                        res.getString(Constants.KEY_SMS_JS),
                        res.getString(Constants.KEY_SMS_SECRET),
                        res.getLong(Constants.KEY_SMS_TIME)
                );
            } // 结束：if (isKeyExist(phone))
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ret;
    }

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
