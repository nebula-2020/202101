/*
 * 文件名：UserDetails.java
 * 描述：数据表userdetails的实体。
 * 修改人：刘可
 * 修改时间：2021-02-05
 */
package com.example.demo.entity;

import java.math.BigInteger;
import java.sql.Timestamp;

import javax.persistence.*;

import lombok.*;

/**
 * 表明账号详细信息的实体。
 * 包括用户性别、公司、学校、住址、生日、简介。
 * 根据相关字段为所有对象继承的<code>toString</code>、<code>equals</code>和<code>hashCode</code>方法生成实现。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @since 2021-02-01
 */
@EqualsAndHashCode
@ToString
@Getter
@Setter
@Entity // 标明数据表映射的类
@Table(name = "userdetails") // @Table来指定和哪个数据表对应;如果省略默认表名就是类名；
public class UserDetails
{

    @Id
    @Column(
            name = "id",
            nullable = false,
            insertable = true,
            updatable = false
    )
    private BigInteger id;

    @Column(
            name = "sex",
            nullable = true,
            insertable = false,
            updatable = true,
            length = 1
    )
    private Byte sex;

    @Column(
            name = "com",
            nullable = true,
            insertable = false,
            updatable = true,
            length = 50
    )
    private String com;

    @Column(
            name = "address",
            nullable = true,
            insertable = false,
            updatable = true,
            length = 50
    )
    private String address;

    @Column(
            name = "college",
            nullable = true,
            insertable = false,
            updatable = true,
            length = 50
    )
    private String college;

    @Column(
            name = "birthday",
            nullable = true,
            insertable = false,
            updatable = true
    )
    private Timestamp birthday;

    @Column(
            name = "intro",
            nullable = true,
            insertable = false,
            updatable = true,
            length = 255
    )
    private String intro;
}
