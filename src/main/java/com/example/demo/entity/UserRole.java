/*
 * 文件名：UserRole.java
 * 描述：数据表role_user的实体。
 * 修改人：刘可
 * 修改时间：2021-02-05
 */
package com.example.demo.entity;

import java.math.BigInteger;

import javax.persistence.*;
import lombok.*;

/**
 * 用户和角色对应表。
 * <p>
 * 此表中不存在的用户可以认为是用普通用户权限。
 * @author 刘可
 * @version 1.0.0.0
 * @since 2021-02-01
 */
@EqualsAndHashCode
@ToString
@Getter
@Setter
@Entity
@Table(name = "role_user")
public class UserRole {
    @Id
    @Column(
            name = "user_id",
            insertable = true,
            nullable = false,
            updatable = false
    )
    private BigInteger userId;
    @Column(
            name = "role_id",
            insertable = true,
            nullable = false,
            updatable = false
    )
    private Short roleId;
}
