/*
 * 文件名：UserStatus.java
 * 描述：数据表userstatus的实体。
 * 修改人：刘可
 * 修改时间：2021-02-03
 */
package com.example.demo.entity;

import java.math.BigInteger;

import javax.persistence.*;
import lombok.*;

/**
 * 表明用户账号状态的实体。
 * 
 * 根据相关字段为所有对象继承的<code>toString</code>、<code>equals</code>和<code>hashCode</code>方法生成实现。
 * @author 刘可
 * @version 1.0.0.0
 * @since 2021-02-03
 */
@EqualsAndHashCode
@ToString
@Getter
@Setter
@Entity
@Table(name = "userstatus")
public class UserStatus
{
    @Id
    @Column(
            name = "id",
            insertable = true,
            updatable = false
    )
    private BigInteger id;
    @Column(
            name = "lock",
            nullable = false,
            insertable = false,
            updatable = false,
            length = 1
    )
    private Boolean lock;
    @Column(
            name = "ban",
            nullable = false,
            insertable = false,
            updatable = false,
            length = 1
    )
    private Boolean ban;

}
