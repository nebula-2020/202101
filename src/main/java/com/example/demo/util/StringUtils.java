/*
 * 文件名：StringUtils.java
 * 描述：常见工具。
 * 修改人：刘可
 * 修改时间：2021-03-10
 */
package com.example.demo.util;

import javax.validation.constraints.NotNull;

import org.springframework.lang.Nullable;

/**
 * {@link String}实用方法。
 * <p>
 * 主要用于框架内内部使用;考虑使用
 * <a href="https://commons.apache.org/proper/commons-lang/">
 * Apache's Commons Lang
 * </a>
 * 来获得一套更全面的{@code String}实用程序。
 * <p>
 * 这个类提供了一些简单的功能，这些功能实际上应该由核心{@link String}和{@link StringBuilder}类提供。
 * 它还提供了一些易于使用的方法，用于在分隔字符串(如CSV字符串)与集合和数组之间进行转换。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @see hasText
 * @since 2021-03-10
 */
public abstract class StringUtils extends org.springframework.util.StringUtils
{
    /**
     * 检查给定的字符串是否<b>全部</b>包含实际的文本。
     * 
     * @param strs 候选对象(可为{@code null})
     * @return 如果字符串不存在{@code null}，长度全部大于0，并且全部包含至少一个非空白字符，则该方法返回{@code true}。
     */
    public static boolean hasText(@Nullable String... strs)
    {

        if (strs != null)
        {

            for (String str: strs)
            {

                if (!hasText(str))
                {
                    return false;
                } // 结束：if (!hasText(str))
            } // 结束：for(String str:strs)
        } // 结束：if (strs != null)
        return true;
    }

    public static int compareTo(@NotNull String str, @NotNull String anotherStr)
    {
        StringBuilder strBuilder = new StringBuilder(str);
        StringBuilder anotherStrBuilder = new StringBuilder(anotherStr);
        return strBuilder.compareTo(anotherStrBuilder);
    }
}
