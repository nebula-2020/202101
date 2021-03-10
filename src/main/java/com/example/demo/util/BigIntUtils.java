/*
 * 文件名：BigIntUtils.java
 * 描述：常见工具。
 * 修改人：刘可
 * 修改时间：2021-03-10
 */

package com.example.demo.util;

import java.math.BigInteger;

import javax.validation.constraints.NotNull;

/**
 * 大数工具。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @since 2021-03-10
 * @see isSame
 * @see isPositive
 * @see containsPositive
 */
public abstract class BigIntUtils
{
    /**
     * 判断两个不可变的任意精度的整数是否非{@code null}且相等。
     * 
     * @param bigInt1 不可变的任意精度的整数
     * @param bigInt2 不可变的任意精度的整数
     * @return 判断两个整数是否非{@code null}且相等。
     */
    public static boolean
            isSame(@NotNull BigInteger bigInt1, @NotNull BigInteger bigInt2)
    {

        if (bigInt1 != null && bigInt2 != null)
        {
            return bigInt1.compareTo(bigInt2) == 0;
        } // 结束：if (bigInt1 != null && bigInt2 != null)
        return false;
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
    public static boolean isPositive(BigInteger... bigInts)
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
    public static boolean containsPositive(BigInteger... bigInts)
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
