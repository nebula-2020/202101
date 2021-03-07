/*
 * 文件名：Permission.java
 * 描述：数据相关枚举。
 * 修改人： 刘可
 * 修改时间：2021-03-07
 */
package com.example.demo.enumation;

/**
 * 描述权限。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @see getValue
 * @see getName
 * @see praseReportCause
 * @since 2021-03-07
 */
public enum Permission
{
    BASE(0b1L, "基础权限"), //
    READ_USER(0b10L, "读任意用户数据"), //
    READ_ARTICLE(0b10000L, "读任意文章信息"), //
    READ_TOPIC(0b10000000L, "读任意话题数据"), //
    READ_REPORT(0b10000000000L, "读任意举报数据"), //

    WRITE_USER(0b100, "写任意用户数据"), //
    WRITE_ARTICLE(0b100000L, "写任意文章信息"), //
    WRITE_TOPIC(0b100000000L, "写任意话题数据"), //
    WRITE_REPORT(0b100000000000L, "写任意举报数据"), //

    DELETE_USER(0b1000L, "删任意用户数据"), //
    DELETE_ARTICLE(0b1000000L, "删任意文章信息"), //
    DELETE_TOPIC(0b1000000000L, "删任意话题数据"), //
    DELETE_REPORT(0b1000000000000L, "删任意举报数据"), //

    ADD_DATA(0b10000000000000L, "增加任意数据"), //
    MODIFY_TABLE(0b100000000000000L, "修改数据表结构"), //
    ADD_TABLE(0b1000000000000000L, "增加数据表"), //
    DELETE_TABLE(0b10000000000000000L, "删除数据表"), //

    ANY_SCRIPT(0b100000000000000000L, "执行任意脚本");//

    private long value;
    private String name;

    /**
     * 枚举值对应数字。
     * 
     * @return 枚举值对应数字。
     */
    public long getValue()
    {
        return value;
    }

    /**
     * 描述枚举值。
     * <p>
     * 字符串表示此枚举值的具体含义。
     * 
     * @return 枚举值描述。
     */
    public String getName()
    {
        return name;
    }

    /**
     * 构造新枚举值。
     * 
     * @param value 枚举值对应数字。
     * @param name 枚举值描述。
     */
    private Permission(long value, String name)
    {
        this.value = value;
        this.name = name;
    }

    /**
     * 数值转换为枚举值。
     * 
     * @param b 值
     * @return 枚举值。
     * @exception IllegalArgumentException 参数错误导致无法转换为枚举值。
     */
    public static ReportCause praseReportCause(long b)
    {

        for (ReportCause ele: ReportCause.values())
        {

            if (b == ele.getValue())
            {
                return ele;
            } // 结束：if (b == ele.getValue())
        } // 结束：for (ReportCause ele: ReportCause.values())
        throw new IllegalArgumentException();
    }
}
