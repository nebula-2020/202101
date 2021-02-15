/*
 * 文件名：SignInInfo.java
 * 描述：数据表signindata的实体
 * 修改人：刘可
 * 修改时间：2021-02-16
 */
package com.example.demo.entity;

import com.example.demo.entity.pk.*;
import javax.persistence.*;
import lombok.*;

@EqualsAndHashCode
@ToString
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "signindata")
/**
 * 账号登录信息的实体。
 * <p>
 * 记录用户登录时间、地址和位置。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @since 2021-02-16
 */
public class SignInInfo
{
    @EmbeddedId
    private SignInInfoKey primaryKey;
    @Column(
            name = "method",
            nullable = false,
            insertable = true,
            updatable = false
    )
    private Byte method;
    @Column(
            name = "ipv6",
            nullable = true,
            insertable = true,
            updatable = false
    )
    private byte[] ipv6;
    @Column(
            name = "mac",
            nullable = true,
            insertable = true,
            updatable = false
    )
    private byte[] mac;
    @Column(
            name = "ipv4",
            nullable = true,
            insertable = true,
            updatable = false
    )
    private Long ipv4;
    @Column(
            name = "location",
            nullable = true,
            insertable = true,
            updatable = false
    )
    private String gps;
}
