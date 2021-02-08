/*
 * 文件名：VisitInfo.java
 * 描述：数据表visit的实体。
 * 修改人：刘可
 * 修改时间：2021-02-08
 */
package com.example.demo.entity;

import javax.persistence.*;

import com.example.demo.entity.pk.ArticleTimeKey;

import lombok.*;

/**
 * 包括地址的用户访问记录。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @since 2021-02-08
 */
@EqualsAndHashCode
@ToString
@Getter
@Setter
@Entity
@Table(name = "visitinfo")
public class VisitInfo
{
    @EmbeddedId
    private ArticleTimeKey primaryKey;
    @Column(
            name = "ipv6",
            nullable = true,
            insertable = true,
            updatable = false
    )
    private byte[] ipv6;
    @Column(
            name = "mac",
            nullable = true,
            insertable = true,
            updatable = false
    )
    private byte[] mac;
    @Column(
            name = "ipv4",
            nullable = true,
            insertable = true,
            updatable = false
    )
    private Long ipv4;
}
