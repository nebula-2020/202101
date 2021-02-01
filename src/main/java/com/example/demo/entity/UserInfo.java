/*
 * 文件名：UserInfo.java
 * 描述：数据表userinfo的实体。
 * 修改人：刘可
 * 修改时间：2021-02-01
 */
package com.example.demo.entity;

import java.math.BigInteger;
import java.sql.Timestamp;

import javax.persistence.*;
import lombok.*;

/**
 * 表明用户账号信息的实体。
 * 
 * 包括用户创建时间、昵称、头像的src、积分、个性签名。
 * 根据相关字段为所有对象继承的<code>toString</code>、<code>equals</code>和<code>hashCode</code>方法生成实现。
 * @author 刘可
 * @version 1.0.0.0
 * @since 2021-02-01
 */
@EqualsAndHashCode
@ToString
@Getter
@Setter
@Entity
@Table(name = "userinfo")
public class UserInfo
{

    @Id
    @Column(
            name = "id",
            insertable = true,
            updatable = false
    )
    private BigInteger id;
    @Column(
            name = "name",
            nullable = false,
            insertable = true,
            updatable = true,
            length = 20
    )
    private String name;

    @Column(
            name = "createtime",
            insertable = false,
            updatable = false
    )
    private Timestamp createtime;
    @Column(
            name = "point",
            insertable = false,
            updatable = false
    )
    private Integer point;

    @Column(
            name = "signature",
            insertable = false,
            nullable = false,
            updatable = true,
            length = 30
    )
    private String signature;

    @Column(
            name = "icon",
            insertable = false,
            nullable = true,
            updatable = true,
            length = 24
    )
    private String icon;
}
