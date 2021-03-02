/*
 * 文件名：Role.java
 * 描述：数据表 role 的实体。
 * 修改人：刘可
 * 修改时间：2021-03-02
 */

package com.example.demo.entity;

import javax.persistence.*;

import lombok.*;

/**
 * 角色表中的数据描述角色能如何访问数据库。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @since 2021-03-02
 */
@EqualsAndHashCode
@ToString
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "role")
public class Role
{

    @Id
    @Column(
            name = "id",
            insertable = true,
            nullable = false,
            updatable = false
    )
    private Short id;
    @Column(
            name = "name",
            insertable = true,
            nullable = false,
            updatable = false
    )
    private String name;
    @Column(
            name = "permissions",
            insertable = true,
            nullable = false,
            updatable = false
    )
    private Long permissions;
}
