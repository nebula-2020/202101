/*
 * 文件名：SignInMethod.java
 * 描述：数据相关枚举。
 * 修改人： 刘可
 * 修改时间：2021-02-03
 */
package com.example.demo.entity;

/**
 * 描述用户的登录方式。
 * <p>
 * 数据库中有登录信息表，用1字节存储用户登陆方式。登陆方式不限于密码、短信验证码。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @see getValue
 * @see getName
 * @since 2021-02-02
 */
public enum SignInMethod
{
    PASSWORD((byte)10, "密码登录"), CODE((byte)20, "扫码登录"), SMS((byte)30, "验证码登录"),
    UNKNOWN((byte)0, "其它");

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
    private SignInMethod(byte value, String name)
    {
        this.value = value;
        this.name = name;
    }
}
