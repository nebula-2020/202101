/*
 * 文件名：Visit.java
 * 描述：数据表visit的实体。
 * 修改人：刘可
 * 修改时间：2021-02-08
 */
package com.example.demo.entity;

import java.math.BigInteger;

import javax.persistence.*;

import com.example.demo.entity.pk.ArticleTimeKey;

import lombok.*;

/**
 * 描述访问记录的实体。
 * <p>
 * 根据相关字段为所有对象继承的<code>toString</code>、<code>equals</code>和<code>hashCode</code>方法生成实现。
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
@Table(name = "visit")
public class Visit
{
    @EmbeddedId
    private ArticleTimeKey primaryKey;

    @Column(
            name = "userid",
            nullable = true,
            insertable = true,
            updatable = false
    )
    private BigInteger visitorId;
}
