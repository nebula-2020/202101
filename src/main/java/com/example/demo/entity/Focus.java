/*
 * 文件名：Focus.java
 * 描述：数据表 focus 的实体。
 * 修改人：刘可
 * 修改时间：2021-02-11
 */

package com.example.demo.entity;

import java.sql.Timestamp;

import javax.persistence.*;

import lombok.*;

/**
 * 关注的实体。
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
@Table(name = "focus")
public class Focus
{
    @EmbeddedId
    private ForeignKey key;
    @Column(
            name = "createtime",
            insertable = false,
            updatable = false
    )
    private Timestamp createtime;
}
