/*
 * 文件名：LikeKey.java
 * 描述：数据表联合主键
 * 修改人：刘可
 * 修改时间：2021-02-05
 */
package com.example.demo.entity.pk;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDate;

import javax.persistence.*;
import lombok.*;

/**
 * 赞的联合主键。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @since 2021-02-05
 */
@EqualsAndHashCode
@ToString
@Getter
@Setter
@Embeddable
public class LikeKey implements Serializable
{

    private static final long serialVersionUID = 1L;

    @Column(
            name = "userid",
            insertable = true,
            updatable = false
    )
    private BigInteger userId;
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
    private LocalDate date;
}
