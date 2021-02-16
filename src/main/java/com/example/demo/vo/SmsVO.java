/*
 * 文件名：SmsVO.java
 * 描述：搭建项目数据传输
 * 修改人：刘可
 * 修改时间：2021-02-16
 */
package com.example.demo.vo;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.*;

/**
 * 描述短信验证码校验用信息。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @since 2021-02-16
 */
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class SmsVO implements Serializable, VO
{
    private static final long serialVersionUID = 1L;
    @NotNull
    @NotEmpty
    private String phone;
    @NotNull
    @NotEmpty
    private String code;
    @NotNull
    @NotEmpty
    private String key;
    @NotNull
    @NotEmpty
    private String secret;
    @NotNull
    private long createTime;
}
