/*
 * 文件名：Report.java
 * 描述：数据表 report 的实体。
 * 修改人：刘可
 * 修改时间：2021-02-11
 */

package com.example.demo.entity;

import java.math.BigInteger;
import java.sql.Timestamp;

import javax.persistence.*;

import lombok.*;

/**
 * 举报信息的实体。
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
@Table(name = "report")
public class Report
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(
            name = "id",
            insertable = false,
            updatable = false
    )
    private BigInteger id;
    @Column(
            name = "userid",
            insertable = true,
            updatable = false
    )
    private BigInteger userId;
    @Column(
            name = "senderid",
            insertable = true,
            updatable = false
    )
    private BigInteger senderId;
    @Column(
            name = "datetime",
            insertable = false,
            updatable = false
    )
    private Timestamp createTime;
    @Column(
            name = "cause",
            nullable = false,
            insertable = true,
            updatable = false
    )
    private Byte cause;
    @Column(
            name = "del",
            nullable = false,
            insertable = false,
            updatable = true
    )
    private Boolean del;
}
