/*
 * 文件名：Message.java
 * 描述：数据表msg的实体。
 * 修改人：刘可
 * 修改时间：2021-02-08
 */
package com.example.demo.entity;

import com.example.demo.entity.pk.*;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 私信。
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
@Table(name = "msg")
public class Message
{
    @EmbeddedId
    private MsgKey msgKey;
    @Column(
            name = "text",
            nullable = true,
            insertable = true,
            updatable = false,
            length = 255
    )
    private String text;
    @Column(
            name = "del",
            nullable = true,
            insertable = false,
            updatable = true
    )
    private Boolean del;
}
