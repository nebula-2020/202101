/*
 * 文件名：ReportCause.java
 * 描述：数据相关枚举。
 * 修改人： 刘可
 * 修改时间：2021-02-27
 */
package com.example.demo.enumation;

/**
 * 描述用户举报原因。
 * <p>
 * 用户可以举报网站上的某些内容，原因限制为枚举类中的值。用于数据库用户举报信息表。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @see getValue
 * @see getName
 * @see praseReportCause
 * @since 2021-02-19
 */
public enum ReportCause
{
    ETC((byte)0, "其它"), TORT((byte)10, "侵权"), AD((byte)20, "广告"),
    POLITIC((byte)30, "政治"), LIBEL((byte)40, "诽谤"), VULGAR((byte)50, "三俗"),
    RUMOR((byte)60, "造谣"), LQ((byte)70, "质量差");

    private byte value;
    private String name;

    /**
     * 枚举值对应数字。
     * 
     * @return 枚举值对应数字。
     */
    public byte getValue()
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
    private ReportCause(byte value, String name)
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
    public static ReportCause praseReportCause(byte b)
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
