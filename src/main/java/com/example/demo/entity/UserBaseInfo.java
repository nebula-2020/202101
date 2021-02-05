/*
 * 文件名：UserBaseInfo.java
 * 描述：数据表user的实体。
 * 修改人：刘可
 * 修改时间：2021-02-05
 */
package com.example.demo.entity;

import java.math.BigInteger;

import javax.persistence.*;

import lombok.*;

/**
 * 账号注册信息的实体。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @since 2021-02-01
 */
@EqualsAndHashCode
@ToString
@Getter
@Setter
@Entity
@Table(name = "user")
public class UserBaseInfo
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "id",
            insertable = false,
            updatable = false
    )
    private BigInteger id;
    @Column(
            name = "personalid",
            unique = true,
            nullable = false,
            insertable = true,
            updatable = true,
            length = 20
    )
    private String account;
    @Column(
            name = "phone",
            nullable = false,
            insertable = true,
            updatable = true,
            length = 15
    )
    private String phone;
    @Column(
            name = "password",
            nullable = false,
            insertable = true,
            updatable = true,
            length = 20
    )
    private String password;
}
