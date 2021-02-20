/*
 * 文件名：BlackList.java
 * 描述：数据表 blacklist 的实体。
 * 修改人：刘可
 * 修改时间：2021-02-20
 */

package com.example.demo.entity;

import javax.persistence.*;

import com.example.demo.entity.pk.BlackListKey;

import lombok.*;

/**
 * 黑名单信息的实体。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @since 2021-02-20
 */
@EqualsAndHashCode
@ToString
@Getter
@Setter
@Entity
@Table(name = "blacklist")
public class BlackList
{
    @EmbeddedId
    private BlackListKey key;
    @Column(
            name = "datetime",
            insertable = false,
            updatable = false
    )
    private Boolean dateTime;
}
