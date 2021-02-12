/*
 * 文件名：CommonTag.java
 * 描述：常见工具。
 * 修改人：刘可
 * 修改时间：2021-02-12
 */
package com.example.demo.tool;

/**
 * 常见常值。
 * <p>
 * 代替一些经常使用的重复名称，便于维护。
 * @author 刘可
 * @version 1.0.0.0
 * @since 2021-02-12
 */
public abstract class CommonTag {
    public static final String ARTICLE_ID = "aid";
    /**
     * 手机号。
     */
    public static final String KEY_PHONE = "phone";
    /**
     * 用户密码。
     */
    public static final String KEY_PASSWORD = "pwd";
    /**
     * ID。
     */
    public static final String KEY_ID = "id";
    /**
     * IPv4。
     */
    public static final String KEY_IPV4 = "ipv4[]";
    /**
     * IPv6。
     */
    public static final String KEY_IPV6 = "ipv6[]";
    /**
     * 物理地址。
     */
    public static final String KEY_MAC = "mac[]";
}
