/*
 * 文件名：Favorite.java
 * 描述：数据表favorite的实体。
 * 修改人：刘可
 * 修改时间：2021-02-05
 */
package com.example.demo.entity;

import java.sql.Timestamp;

import javax.persistence.*;

import com.example.demo.entity.pk.*;

import lombok.*;

/**
 * 用户收藏夹。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @since 2021-02-05
 */
@EqualsAndHashCode
@ToString
@Getter
@Setter
@Entity
@Table(name = "favorite")
public class Favorite
{

    @EmbeddedId
   private UserArticleKey key;
    @Column(
            name = "createtime",
            insertable = false,
            updatable = false
    )
   private Timestamp createtime;
}
