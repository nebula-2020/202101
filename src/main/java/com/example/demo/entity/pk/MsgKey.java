/*
 * 文件名：MsgKey.java
 * 描述：数据表联合主键
 * 修改人：刘可
 * 修改时间：2021-02-12
 */
package com.example.demo.entity.pk;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

import javax.persistence.*;
import lombok.*;

/**
 * 私信联合主键。
 * <p>
 * 根据相关字段为所有对象继承的<code>toString</code>、<code>equals</code>和<code>hashCode</code>方法生成实现。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @since 2021-02-11
 */
@EqualsAndHashCode
@ToString
@Getter
@Setter
@Embeddable
public class MsgKey implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Column(
            name = "userid",
            insertable = true,
            updatable = false
    )
    private BigInteger userId;

    @Column(
            name = "senderid",
            insertable = true,
            updatable = false
    )
    private BigInteger senderId;

    @Column(
            name = "datetime",
            insertable = false,
            updatable = false
    )
    private Timestamp time;
}
