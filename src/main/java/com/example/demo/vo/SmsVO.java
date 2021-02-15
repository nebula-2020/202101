/*
 * 文件名：SmsVO.java
 * 描述：搭建项目数据传输
 * 修改人：刘可
 * 修改时间：2021-02-15
 */
package com.example.demo.vo;

import java.io.Serializable;

import lombok.*;
/**
 * 描述短信验证码校验用信息。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @since 2021-02-15
 */
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class SmsVO implements Serializable, VO
{
    private static final long serialVersionUID = 1L;
    private String phone;
    private String code;
    private String key;
    private String secret;
    private long createTime;
}
