/*
 * 文件名：Like.java
 * 描述：数据表like的实体。
 * 修改人：刘可
 * 修改时间：2021-02-05
 */
package com.example.demo.entity;
import com.example.demo.entity.pk.*;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * 赞。
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
@Table(name = "like")
public class Like
{
    @EmbeddedId
    LikeKey key;
}
