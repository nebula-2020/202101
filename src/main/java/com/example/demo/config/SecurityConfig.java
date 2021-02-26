/*
 * 文件名：SecurityConfig.java
 * 描述：项目配置。
 * 修改人：刘可
 * 修改时间：2021-02-26
 */

package com.example.demo.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Spring Security配置类。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @since 2021-02-26
 */
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception
    {
        super.configure(builder);
    }

    @Override
    public void configure(WebSecurity web) throws Exception
    {
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.authorizeRequests().anyRequest().authenticated()//
                .and().formLogin().loginProcessingUrl("user/codeSignIn")//
                .and().formLogin().loginProcessingUrl("user/passwordSignIn")//
                .and().csrf().disable();
    }
}
