/*
 * 文件名：CommonTool.java
 * 描述：常见工具。
 * 修改人：刘可
 * 修改时间：2021-01-31
 */
package com.example.demo.tool;

import java.math.BigInteger;
import java.util.*;

/**
 * 常见工具。
 *
 * @author 刘可
 * @version 1.0.0.0
 */
public final class CommonTool
{
    /**
     * 补给方法。
     * 
     * @param <T> 返回值类型
     * @param obj 一个对象，当此参数不为<code>null</code>时返回此对象
     * @param support 补给对象，当参数<code>obj</code>为<code>null</code>时返回此对象
     * @return 指定类型对象
     */
    public <T> T supportTo(T obj, T support)
    {
        return obj != null ? obj : support;
    }

    /**
     * 检验字符串是否为空值。
     * 
     * @param str 被检测值
     * @return 满足以下条件之一则返回<code>true</code>，否则返回<code>false</code>：
     * <ul>
     * <li>被检测值为<code>""</code>；</li>
     * <li>被检测值为<code>null</code>。</li>
     * </ul>
     */
    public boolean isNullOrEmpty(String str)
    {
        return str == null || str.length() <= 0;
    }

    /**
     * 检验一组字符串是否存在空值。
     * 
     * @param strs 被检测值
     * @return 满足以下条件之一则返回<code>true</code>，否则返回<code>false</code>：
     * <ul>
     * <li>被检测值中有一个为<code>""</code>；</li>
     * <li>被检测值中有一个为<code>null</code>；</li>
     * <li>被检测值为<code>null</code>。</li>
     * </ul>
     */
    public boolean containsNullOrEmpty(String... strs)
    {

        if (strs == null)
        {
            return true;
        } // 结束： if (str == null)

        for (String s: strs)
        {

            if (isNullOrEmpty(s))
            {
                return true;
            } // 结束：if (isNullOrEmpty(s))

        } // 结束：for (String s: str)

        return false;
    }

    /**
     * 检验对象是否为<code>null</code>。
     * 
     * @param obj 被检测值
     * @return 满足以下条件之一则返回<code>true</code>，否则返回<code>false</code>：
     * <ul>
     * <li>被检测值中有一个为<code>null</code>；</li>
     * <li>被检测值为<code>null</code>。</li>
     * </ul>
     * @deprecated 直接使用<code>obj == null</code>。
     */
    @Deprecated
    public boolean containsNull(Object obj)
    {
        return obj == null;
    }

    /**
     * 检验对象是否为<code>null</code>。
     * 
     * @param obj 被检测值
     * @return 满足以下条件之一则返回<code>true</code>，否则返回<code>false</code>：
     * <ul>
     * <li>被检测值中没有一个不为<code>null</code>；</li>
     * <li>被检测值为<code>null</code>。</li>
     * </ul>
     * @deprecated 直接使用<code>obj == null</code>。
     */
    @Deprecated
    public boolean isNull(Object obj)
    {
        return obj == null;
    }

    /**
     * 检验一组对象是否存在<code>null</code>。
     * 
     * @param objs 被检测值
     * @return 满足以下条件之一则返回<code>true</code>，否则返回<code>false</code>：
     * <ul>
     * <li>被检测值中有一个为<code>null</code>；</li>
     * <li>被检测值为<code>null</code>。</li>
     * </ul>
     */
    public boolean containsNull(Object... objs)
    {

        if (objs == null)
        {
            return true;
        } // 结束：if (objs == null)

        for (Object o: objs)
        {

            if (o == null)
            {
                return true;
            } // 结束：if (o == null)

        } // 结束：for (Object o: objs)

        return false;
    }

    /**
     * 检验一组对象是否全为<code>null</code>。
     * 
     * @param objs 被检测值
     * @return 满足以下条件之一则返回<code>true</code>，否则返回<code>false</code>：
     * <ul>
     * <li>被检测值中没有一个不为<code>null</code>；</li>
     * <li>被检测值为<code>null</code>。</li>
     * </ul>
     */
    public boolean isNull(Object... objs)
    {

        if (objs == null)
        {
            return true;
        } // 结束：if (objs == null)

        for (Object o: objs)
        {

            if (o != null)
            {
                return false;
            } // 结束：if (o == null)

        } // 结束：for (Object o: objs)

        return true;
    }

    /**
     * 检验字符串是否为空值。
     * 
     * @param str 被检测值
     * @return 满足以下条件之一则返回<code>true</code>，否则返回<code>false</code>：
     * <ul>
     * <li>被检测值为<code>""</code>；</li>
     * <li>被检测值为<code>null</code>。</li>
     * </ul>
     * @deprecated <code>containsNullOrEmpty(String... str)</code>。
     */
    @Deprecated
    public boolean containsNullOrEmpty(String str)
    {
        return isNullOrEmpty(str);
    }

    /**
     * 检验一组字符串是否全为空值。
     * 
     * @param strs 被检测值
     * @return 满足以下条件之一则返回<code>true</code>，否则返回<code>false</code>：
     * <ul>
     * <li>被检测值中没有一个既不为<code>""</code>，也不为<code>null</code>；</li>
     * <li>被检测值为<code>null</code>。</li>
     * </ul>
     */
    public boolean isNullOrEmpty(String... strs)
    {

        if (strs == null)
        {
            return true;
        } // 结束：if (str == null)

        for (String s: strs)
        {

            if (!isNullOrEmpty(s))
            {
                return false;
            } // 结束：if (!isNullOrEmpty(s))

        } // 结束：for (String s: str)

        return true;
    }

    /**
     * 将列表转换为数组，列表中的<code>null</code>转换为<code>0</code>；
     * 列表为<code>null</code>时返回值也为<code>null</code>。
     * 
     * @param list 待转换列表
     * @return 转换后的列表。
     */
    public byte[] toByteArray(List<Byte> list)
    {
        byte[] ret = null;
        int size = list.size();

        if (size > 0)
        {
            ret = new byte[size];

            for (int i = 0; i < size; i++)
            {

                try
                {
                    ret[i] = list.get(i);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    ret[i] = (byte)0;
                }
                finally
                {
                }

            } // 结束：for (int i = 0; i < size; i++)

        } // 结束： if (size > 0)

        return ret;
    }

    /**
     * 比较两个字符串是否相等。
     * 
     * @param str 字符串
     * @param anotherStr 字符串
     * @return 满足以下条件之一返回<code>false</code>，否则返回<code>true</code>：
     * <ul>
     * <li>字符串不相等；</li>
     * <li>有一个字符串为<code>null</code>。</li>
     * </ul>
     */
    public boolean isStrSame(String str, String anotherStr)
    {

        if (str == null || anotherStr == null || str.length() <= 0
                || anotherStr.length() <= 0)
        {
            return false;
        } // 结束：if(str==null||anotherStr==null||str.length()<=0||anotherStr.length()<=0)

        StringBuilder strBuilder = new StringBuilder(str);
        StringBuilder anotherStrBuilder = new StringBuilder(anotherStr);
        boolean ret = strBuilder.compareTo(anotherStrBuilder) == 0;
        return ret;
    }

    /**
     * 两个整数是否相等。
     * 
     * @param integer 不可变的任意精度的整数
     * @param anotherInteger 不可变的任意精度的整数
     * @return 描述相等与否。
     */
    public boolean isBigIntSame(BigInteger integer, BigInteger anotherInteger)
    {

        if (integer != null && anotherInteger != null)
        {
            return false;
        } // 结束： if (containsNullOrEmpty(str, anotherStr))

        boolean ret = integer.compareTo(anotherInteger) == 0;
        return ret;
    }

}
