/*
 * 文件名：Topic.java
 * 描述：数据表topic的实体。
 * 修改人：刘可
 * 修改时间：2021-02-11
 */
package com.example.demo.entity;

import java.math.BigInteger;
import java.sql.Timestamp;

import javax.persistence.*;

import lombok.*;

/**
 * 话题的实体。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @since 2021-02-11
 */
@EqualsAndHashCode
@ToString
@Getter
@Setter
@Entity
@Table(name = "topic")
public class Topic
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
            name = "masterid",
            nullable = false,
            insertable = true,
            updatable = false
    )
    private BigInteger masterId;
    @Column(
            name = "createtime",
            insertable = false,
            updatable = false
    )
    private Timestamp createTime;
    @Column(
            name = "name",
            nullable = false,
            insertable = true,
            updatable = false,
            length = 20
    )
    private String name;
    @Column(
            name = "del",
            nullable = false,
            insertable = false,
            updatable = true
    )
    private Boolean del;
    @Column(
            name = "ban",
            nullable = false,
            insertable = false,
            updatable = true
    )
    private Boolean ban;
    @Column(
            name = "maxsize",
            nullable = false,
            insertable = true,
            updatable = true
    )
    private Short maxSize;
}
