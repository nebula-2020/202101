/*
 * 文件名：ArticleInfo.java
 * 描述：数据表article的实体。
 * 修改人：刘可
 * 修改时间：2021-02-03
 */
package com.example.demo.entity;

import java.math.BigInteger;
import java.sql.Timestamp;

import javax.persistence.*;

import lombok.*;
/**
 * 文章信息。
 * <p>
 * 用户阅读文章时将请求的内容。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @since 2021-02-03
 */
@EqualsAndHashCode
@ToString
@Getter
@Setter
@Entity
@Table(name = "articleinf")
public class ArticleInfo {
    @Id
    @Column(
            name = "id",
            insertable = false,
            updatable = false
    )
    private BigInteger id;
    @Column(
            name = "modifytime",
            insertable = false,
            updatable = true
    )
    private Timestamp modifyTime;
    
    @Column(
            name = "text",
            insertable = true,
            updatable = true
    )
    private String text;
    @Column(
            name = "source",
            insertable = true,
            updatable = true,
            length = 255
    )
    private String source;
}
