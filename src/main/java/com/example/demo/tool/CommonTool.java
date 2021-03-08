/*
 * 文件名：CommonTool.java
 * 描述：常见工具。
 * 修改人：刘可
 * 修改时间：2021-03-08
 */
package com.example.demo.tool;

import java.io.*;
import java.math.BigInteger;
import java.util.*;

import org.springframework.util.StringUtils;

/**
 * 常见工具。
 *
 * @author 刘可
 * @version 1.0.0.0
 * @see supportTo
 * @see isNullOrEmpty
 * @see containsNullOrEmpty
 * @see containsNull
 * @see isNull
 * @see toByteArray
 * @see isStrSame
 * @see isBigIntSame
 * @see bytes2Ipv4
 * @see closeAll
 * @see bytes2Obj
 * @see obj2Bytes
 * @see isPositive
 * @see containsPositive
 * @since 2021-02-20
 */
public final class CommonTool
{
    private final byte IPV4_LEN = 4;
    private final short UBYTE_MAX = 0xff;

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
     * @deprecated
     */
    public boolean isNullOrEmpty(String str)
    {
        return !StringUtils.hasText(str);
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

            if (!StringUtils.hasText(s))
            {
                return true;
            } // 结束：if (!StringUtils.hasText(s))

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
        int size = list == null ? 0 : list.size();

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

        if (integer == null || anotherInteger == null)
        {
            return false;
        } // 结束：if (integer == null && anotherInteger == null)

        boolean ret = integer.compareTo(anotherInteger) == 0;
        return ret;
    }

    /**
     * 字节数组与IPv4地址转换方法，数组长度不足则返回<code>null</code>，过长则超出部分不参与运算。
     * 
     * @param ipv4Array 表示IP地址的数组
     * @return 长整型IP地址。
     */
    public Long bytes2Ipv4(byte[] ipv4Array)
    {

        if (ipv4Array == null || ipv4Array.length <= IPV4_LEN)
        {
            return null;
        } // 结束：if (bytes == null || bytes.length <= 0)
        Long ret = null;

        if (ipv4Array != null && ipv4Array.length >= IPV4_LEN)
        {
            ret = 0L;

            for (int i = 0; i < IPV4_LEN; i++)
            {
                ret <<= Byte.SIZE;
                ret |= (ipv4Array[i] & UBYTE_MAX);
            } // 结束：for (int i = 0; i <ipv4Array.length; i++)
        } // 结束：if(ipv4Array.length>=ipv4Len){
        return ret;
    }

    /**
     * 关闭所有对象。
     * 
     * @param closeables 可以关闭的数据源或目标数据
     */
    public void closeAll(Closeable... closeables)
    {

        if (closeables != null)
        {

            for (Closeable ele: closeables)
            {

                if (ele != null)
                {

                    try
                    {
                        ele.close();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    finally
                    {
                    }
                } // 结束：if (ele != null)
            } // 结束：for (Closeable ele: closeables)
        } // 结束： if (closeables != null)
    }

    /**
     * 数组转对象。
     * 
     * @param bytes 比特数组
     * @return 对象，转换失败返回<code>null</code>。
     */
    public Object bytes2Obj(byte[] bytes)
    {

        if (bytes == null || bytes.length <= 0)
        {
            return null;
        } // 结束：if (bytes == null || bytes.length <= 0)
        Object ret = null;
        ByteArrayInputStream arrSteam = null;
        ObjectInputStream objSteam = null;

        try
        {
            arrSteam = new ByteArrayInputStream(bytes);
            objSteam = new ObjectInputStream(arrSteam);
            ret = objSteam.readObject();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeAll(objSteam, arrSteam);
        }
        return ret;
    }

    /**
     * 对象转数组。
     * 
     * @param obj 对象
     * @return 数组，转换失败返回<code>null</code>。
     */
    public byte[] obj2Bytes(Object obj)
    {

        if (obj == null)
        {
            return null;
        } // 结束：if (bytes == null || bytes.length <= 0)
        byte[] bytes = null;
        ByteArrayOutputStream arrSteam = null;
        ObjectOutputStream objSteam = null;

        try
        {
            arrSteam = new ByteArrayOutputStream();
            objSteam = new ObjectOutputStream(arrSteam);
            objSteam.writeObject(obj);
            objSteam.flush();
            bytes = arrSteam.toByteArray();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeAll(objSteam, arrSteam);
        }
        return bytes;
    }

    /**
     * 判断所给数值是否全为正。
     * 
     * @param bigInts 被检测值
     * @return 满足以下条件之一则返回<code>true</code>，否则返回<code>false</code>：
     * <ul>
     * <li>被检测值中不存在非正数；</li>
     * <li>被检测值中不存在<code>null</code>；</li>
     * <li>被检测值非<code>null</code>。</li>
     * </ul>
     */
    public boolean isPositive(BigInteger... bigInts)
    {
        boolean ret = true;

        if (bigInts != null)
        {

            for (BigInteger i: bigInts)
            {

                if (i == null || BigInteger.ZERO.compareTo(i) >= 0)
                {
                    ret = false;
                    break;
                } // 结束：if (i==null||BigInteger.ZERO.compareTo(i)>=0)
            } // 结束：for (BigInteger i : bigInts)
        } // 结束：if (bigInts != null)
        return ret;
    }

    /**
     * 判断所给数值是存在正数。
     * 
     * @param bigInts 被检测值
     * @return 满足以下条件则返回<code>true</code>，否则返回<code>false</code>：
     * <ul>
     * <li>被检测值中存在正数；</li>
     * <li>被检测值非<code>null</code>。</li>
     * </ul>
     */
    public boolean containsPositive(BigInteger... bigInts)
    {
        boolean ret = false;

        if (bigInts != null)
        {

            for (BigInteger i: bigInts)
            {

                if (i != null && BigInteger.ZERO.compareTo(i) < 0)
                {
                    ret = true;
                    break;
                } // 结束：if (i != null && BigInteger.ZERO.compareTo(i) < 0)
            } // 结束：for (BigInteger i : bigInts)
        } // 结束：if (bigInts != null)
        return ret;
    }
}
