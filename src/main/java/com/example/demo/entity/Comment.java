/*
 * 文件名：Comment.java
 * 描述：数据表comment的实体。
 * 修改人：刘可
 * 修改时间：2021-02-05
 */
package com.example.demo.entity;

import java.math.BigInteger;
import java.sql.Timestamp;

import javax.persistence.*;

import lombok.*;

/**
 * 评论。
 * <p>
 * 用户对文章的评论。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @since 2021-02-05
 */
@EqualsAndHashCode
@ToString
@Getter
@Setter
@Entity
@Table(name = "comment")
public class Comment
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(
            name = "id",
            insertable = false,
            updatable = false
    )
    private BigInteger id;
    @Column(
            name = "articleid",
            nullable = false,
            insertable = true,
            updatable = false
    )
    private BigInteger articleId;
    @Column(
            name = "userid",
            nullable = false,
            insertable = true,
            updatable = false
    )
    private BigInteger userId;
    @Column(
            name = "createtime",
            insertable = false,
            updatable = false
    )
    private Timestamp createTime;
    @Column(
            name = "text",
            nullable = false,
            insertable = true,
            updatable = false,
            length = 255
    )
    private String text;
}
