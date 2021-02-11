/*
 * 文件名：TopicInfo.java
 * 描述：数据表topicinf的实体。
 * 修改人：刘可
 * 修改时间：2021-02-11
 */
package com.example.demo.entity;

import java.math.BigInteger;

import javax.persistence.*;

import lombok.*;

/**
 * 话题信息的实体。
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
@Table(name = "topicinf")
public class TopicInfo
{
    @Id
    @Column(
            name = "id",
            insertable = false,
            updatable = false
    )
    private BigInteger id;
    @Column(
            name = "intro",
            nullable = true,
            insertable = true,
            updatable = false,
            length = 255
    )
    private String introduction;
}