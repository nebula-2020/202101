/*
 * 文件名：FocusKey.java
 * 描述：数据表联合主键
 * 修改人：刘可
 * 修改时间：2021-02-11
 */

package com.example.demo.entity.pk;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.*;
import lombok.*;

/**
 * 关注消息的联合主键。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @since 2021-02-11
 */
@EqualsAndHashCode
@ToString
@Getter
@Setter
@Embeddable
public class FocusKey implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Column(
            name = "userid",
            insertable = true,
            updatable = false
    )
    private BigInteger userId;
    @Column(
            name = "fanid",
            insertable = true,
            updatable = false
    )
    private BigInteger fanId;
}