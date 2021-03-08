/*
 * 文件名：SecurityConfig.java
 * 描述：项目配置。
 * 修改人：刘可
 * 修改时间：2021-03-08
 */

package com.example.demo.config;

import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Spring Security配置类。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @since 2021-03-08
 */
@Configuration
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter
{

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.rememberMe();
    }
}
