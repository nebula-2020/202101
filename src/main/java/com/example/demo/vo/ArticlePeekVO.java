/*
 * 文件名：ArticlePeekVO.java
 * 描述：搭建项目数据传输
 * 修改人：刘可
 * 修改时间：2021-02-17
 */

package com.example.demo.vo;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

import javax.validation.constraints.*;

import lombok.*;
/**
 * 描述用户请求文章列表时单一文章展示的信息。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @since 2021-02-17
 */
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ArticlePeekVO implements VO,Serializable
{
    private static final long serialVersionUID = 1L;
    @NotNull
    @Min(1)
    private BigInteger id;
    @NotNull
    @NotEmpty
    @NotBlank
    private String title;
    @NotNull
    @Min(1)
    private BigInteger authorId;
    @NotEmpty
    @NotBlank
    private String authorAccount;
    @Past
    @NotNull
    private Timestamp createTime;
    private boolean draft;
}
