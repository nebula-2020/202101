/*
 * 文件名：SecurityConfig.java
 * 描述：项目配置。
 * 修改人：刘可
 * 修改时间：2021-02-28
 */

package com.example.demo.config;

import com.example.demo.constant.Constants;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Spring Security配置类。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @since 2021-02-28
 */
@Configuration
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    @Override
    protected void configure(AuthenticationManagerBuilder builder)
            throws Exception
    {

        builder.inMemoryAuthentication()
                .passwordEncoder(new BCryptPasswordEncoder())
                .withUser(Constants.SECURITY_USER)
                .password(new BCryptPasswordEncoder().encode("password"))
                .roles("USER");

        builder.inMemoryAuthentication()
                .passwordEncoder(new BCryptPasswordEncoder())
                .withUser(Constants.SECURITY_ADMIN)
                .password(new BCryptPasswordEncoder().encode("password"))
                .roles("USER", "ADMIN");
    }

    @Override
    public void configure(WebSecurity web) throws Exception
    {
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.httpBasic().and().authorizeRequests().antMatchers("/**")
                .permitAll().anyRequest().authenticated() //一律认证
                .and().csrf().disable(); // 禁用CSRF
        http.logout().logoutSuccessUrl("/");
        http.rememberMe().rememberMeParameter("remember");
    }
}
