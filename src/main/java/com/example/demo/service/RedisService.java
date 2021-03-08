/*
 * 文件名：RedisService.java
 * 描述：项目主要服务。
 * 修改人：刘可
 * 修改时间：2021-03-08
 */

package com.example.demo.service;

import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSON;
import com.example.demo.vo.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.*;
import org.springframework.util.StringUtils;
import org.springframework.data.util.CastUtils;

/**
 * Redis服务。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @see signOut
 * @see setSignInSession
 * @see verifySignIn
 * @see setSmsSession
 * @see getSmsSession
 * @see set
 * @see deleteObj
 * @see isKeyExist
 * @see get
 * @since 2021-03-08
 */
@Service("redisService")
public class RedisService extends SessionService
{
    @Autowired
    protected StringRedisTemplate redis;

    /**
     * 在一段时间内登出一个用户。
     * 
     * @param account 用户账号
     * @param time Session保存时间，单位为毫秒，应长于Token保存时间
     * @return 操作成功与否。
     */
    public boolean signOut(String account, long time)
    {
        boolean ret = false;

        try
        {
            set(account, false, time);
            ret = true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 在Session中存储用户登陆时间。
     * 
     * @param account 用户账号
     * @param time Session保存时间，单位为毫秒
     * @return 操作成功与否。
     */
    public boolean setSignInSession(String account, long time)
    {
        boolean ret = false;

        try
        {
            boolean canSignIn = !isKeyExist(account);

            if (!canSignIn)
            {
                canSignIn = get(account, boolean.class);// 可能设置登出了
                set(account, true, time);
                ret = true;
            } // 结束：if (!canSignIn)

            if (canSignIn)
            {
                set(account, true, time);
                ret = true;
            } // 结束：if (canSignIn)
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * Session验证用户登录。
     * 
     * @param account 用户账号
     * @return 描述用户登录值是否在Session中。
     */
    public boolean verifySignIn(String account)
    {
        boolean ret = false;

        try
        {

            if (isKeyExist(account))
            {
                ret = true;
            } // 结束：if (isKeyExist(account))
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ret;
    }

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
                set(vo.getPhone(), vo, time);
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

        if (!StringUtils.hasText(phone))
        {
            throw new IllegalArgumentException();
        } // 结束：if (!StringUtils.hasText(phone))
        SmsVO ret = null;

        try
        {

            if (isKeyExist(phone))
            {
                ret = (SmsVO)get(phone, SmsVO.class);
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
