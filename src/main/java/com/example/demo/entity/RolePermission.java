/*
 * 文件名：RolePermission.java
 * 描述：数据表 permission 的实体。
 * 修改人：刘可
 * 修改时间：2021-02-16
 */
package com.example.demo.entity;

import javax.persistence.*;

import lombok.*;

/**
 * 权限实体。
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
@Table(name = "permission")
public class RolePermission
{
    @Id
    @Column(
            name = "permission",
            insertable = true,
            nullable = false,
            updatable = false
    )
    private Long permission;
    @Column(
            name = "name",
            insertable = true,
            nullable = false,
            updatable = false
    )
    private String name;
}
