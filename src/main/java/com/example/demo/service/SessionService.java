/*
 * 文件名：SessionService.java
 * 描述：项目主要服务。
 * 修改人：刘可
 * 修改时间：2021-02-16
 */
package com.example.demo.service;

/**
 * @author 刘可
 * @version 1.0.0.0
 * @see set
 * @see deleteObj
 * @see isKeyExist
 * @see get
 * @since 2021-02-16
 */
public abstract class SessionService extends ComService
{
    protected static final long MSEC_60000 = 60000;

    /**
     * 将一个值插入Session。
     * <p>
     * 有效期默认为1分钟。
     * 
     * @param key 键名
     * @param obj 对应值
     * @param liveTime Session有效期，单位为毫秒
     */
    public abstract void set(String key, Object obj, long liveTime);

    /**
     * 将一个值插入Session。
     * <p>
     * 有效期为1分钟。
     * 
     * @param key 键名
     * @param obj 对应值
     */
    public void set(String key, Object obj)
    {
        set(key, obj, MSEC_60000);
    }

    /**
     * 删单个键。
     * 
     * @param key 键名
     * @return 描述删除成功与否。
     */
    public abstract boolean deleteObj(String key);

    /**
     * 判断键是否存在。
     * 
     * @param key 键名
     * @return 描述键是否存在。
     */
    public abstract boolean isKeyExist(String key);

    /**
     * 获取值。
     * 
     * @param <T> 返回值类型
     * @param key 键名
     * @param clazz 描述返回值类型
     * @return 键对应值。
     */
    public abstract <T> T get(String key, Class<T> clazz);
}
