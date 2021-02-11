/*
 * 文件名：TopicUser.java
 * 描述：数据表 topicuser 的实体。
 * 修改人：刘可
 * 修改时间：2021-02-11
 */

package com.example.demo.entity;

import java.sql.Timestamp;

import javax.persistence.*;

import com.example.demo.entity.pk.TopicUserKey;

import lombok.*;

/**
 * 话题人员实体。
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
@Table(name = "topicuser")
public class TopicUser
{
    @EmbeddedId
    private TopicUserKey primaryKey;
    @Column(
            name = "jointime",
            insertable = false,
            updatable = false
    )
    private Timestamp joinTime;
    @Column(
            name = "del",
            nullable = true,
            insertable = false,
            updatable = false
    )
    private Boolean del;
}
