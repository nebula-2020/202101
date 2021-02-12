/*
 * 文件名：Focus.java
 * 描述：数据表 focus 的实体。
 * 修改人：刘可
 * 修改时间：2021-02-12
 */

package com.example.demo.entity;

import java.sql.Timestamp;

import javax.persistence.*;

import com.example.demo.entity.pk.FocusKey;

import lombok.*;

/**
 * 关注的实体。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @since 2021-02-12
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
    private FocusKey key;
    @Column(
            name = "createtime",
            insertable = false,
            updatable = false
    )
    private Timestamp createtime;
}
