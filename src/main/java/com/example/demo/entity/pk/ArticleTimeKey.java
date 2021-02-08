/*
 * 文件名：ArticleTimeKey.java
 * 描述：数据表联合主键
 * 修改人：刘可
 * 修改时间：2021-02-08
 */
package com.example.demo.entity.pk;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

import javax.persistence.*;
import lombok.*;

/**
 * 主键包含`articleid`和`time`两列。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @since 2021-02-08
 */
@EqualsAndHashCode
@ToString
@Getter
@Setter
@Embeddable
public class ArticleTimeKey implements Serializable
{

    private static final long serialVersionUID = 1L;

    @Column(
            name = "articleid",
            insertable = true,
            updatable = false
    )
    private BigInteger articleId;

    @Column(
            name = "time",
            insertable = false,
            updatable = false
    )
    private Timestamp date;
}
