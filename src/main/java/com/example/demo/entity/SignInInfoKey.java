/*
 * 文件名：SignInInfoKey.java
 * 描述：数据表联合主键
 * 修改人：刘可
 * 修改时间：2021-02-02
 */
 package com.example.demo.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

import javax.persistence.*;
import lombok.*;

@EqualsAndHashCode
@ToString
@Getter
@Setter
@Embeddable
/**
 * 登录信息联合主键。
 * <p>
 * 根据相关字段为所有对象继承的<code>toString</code>、<code>equals</code>和<code>hashCode</code>方法生成实现。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @since 2021-02-02
 */
public class SignInInfoKey implements Serializable
{

    private static final long serialVersionUID = 1L;

    @Column(
            name = "userid",
            insertable = true,
            updatable = false
    )
    private BigInteger id;

    @Column(
            name = "time",
            insertable = false,
            updatable = false
    )
    private Timestamp time;
}