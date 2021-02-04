/*
 * 文件名：Article.java
 * 描述：数据表article的实体。
 * 修改人：刘可
 * 修改时间：2021-02-04
 */
package com.example.demo.entity;

import java.math.BigInteger;
import java.sql.Timestamp;

import javax.persistence.*;

import lombok.*;
/**
 * 文章的实体。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @since 2021-02-04
 */
@EqualsAndHashCode
@ToString
@Getter
@Setter
@Entity
@Table(name = "article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(
            name = "id",
            insertable = false,
            updatable = false
    )
    private BigInteger id;
    @Column(
            name = "authorid",
            nullable = false,
            insertable = true,
            updatable = false
    )
    private BigInteger authorId;
    @Column(
            name = "createtime",
            insertable = false,
            updatable = false
    )
    private Timestamp createTime;
    @Column(
            name = "title",
            nullable = false,
            insertable = true,
            updatable = true,
            length = 63
    )
    private String title;
    @Column(
            name = "del",
            nullable = true,
            insertable = false,
            updatable = false
    )
    private Boolean del;
    @Column(
            name = "draft",
            nullable = true,
            insertable = true,
            updatable = true
    )
    private Boolean draft;
    @Column(
            name = "type",
            nullable = true,
            insertable = true,
            updatable = true
    )
    private Integer type;
}
