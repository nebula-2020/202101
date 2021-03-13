/*
 * 文件名：SmsVO.java
 * 描述：搭建项目数据传输
 * 修改人：刘可
 * 修改时间：2021-03-13
 */
package com.example.demo.vo;

import com.example.demo.constant.*;
import com.example.demo.util.StringUtils;

import java.io.Serializable;

import javax.validation.constraints.*;

import lombok.*;

/**
 * 描述短信验证码校验用信息。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @since 2021-03-13
 */
@Getter
@Setter
@EqualsAndHashCode
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

    /**
     * 初始化数据传输对象。
     * <p>
     * 描述一次短信验证的数据。
     * 
     * @param phone 手机号
     * @param code 验证码
     * @param key 客户端随机代码
     * @param secret 服务器随机代码
     * @exception IllegalArgumentException 参数为空或空白。
     */
    public SmsVO(
            @Pattern(regexp = Constants.REGEXP_PHONE) String phone, String code,
            String key, String secret
    )
    {

        if (!StringUtils.hasText(phone, code, key, secret))
        {
            throw new IllegalArgumentException();
        } // 结束：if (!StringUtils.hasText(phone, code, key, secret))
        this.phone = phone;
        this.code = code;
        this.key = key;
        this.secret = secret;
    }
}
