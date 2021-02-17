/*
 * 文件名：ArticleVO.java
 * 描述：搭建项目数据传输
 * 修改人：刘可
 * 修改时间：2021-02-17
 */

package com.example.demo.vo;

import java.io.Serializable;

import javax.validation.constraints.*;

import lombok.*;
/**
 * 描述用户提交的文章数据。
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
public class ArticleVO implements Serializable, VO
{
    private static final long serialVersionUID = 1L;
    @NotNull
    @NotEmpty
    @NotBlank
    private String title;
    @NotNull
    @NotEmpty
    @NotBlank
    private String text;
    @NotEmpty
    private String source;
    private boolean draft;
}
