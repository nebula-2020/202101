/*
 * 文件名：ArticleInfoVO.java
 * 描述：搭建项目数据传输
 * 修改人：刘可
 * 修改时间：2021-02-17
 */

package com.example.demo.vo;

import java.math.BigInteger;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.validation.constraints.*;

import lombok.*;

/**
 * 描述用户进入文章界面浏览或编辑时请求的文章数据。
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
public class ArticleInfoVO implements Serializable, VO
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
    @NotEmpty
    @NotBlank
    private String text;
    @NotEmpty
    @NotBlank
    private String source;
    @NotNull
    @NotEmpty
    @NotBlank
    private String authorAccount;
    @Past
    @NotNull
    private Timestamp createTime;
    @Past
    @NotNull
    private Timestamp modifyTime;
    private boolean draft;
}
