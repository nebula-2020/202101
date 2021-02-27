/*
 * 文件名：FilterConfig.java
 * 描述：项目配置。
 * 修改人：刘可
 * 修改时间：2021-02-27
 */
package com.example.demo.config;

import javax.servlet.Filter;

import com.example.demo.filter.*;

import org.springframework.boot.web.servlet.*;
import org.springframework.context.annotation.*;

@Configuration
public class FilterConfig
{

    @Bean
    public FilterRegistrationBean<Filter> registFilter()
    {
        FilterRegistrationBean<Filter> registration =
                new FilterRegistrationBean<>();
        registration.setFilter(new CommonFilter());
        registration.addUrlPatterns("/*");
        registration.setName("CommonFilter");
        registration.setOrder(1);
        return registration;
    }

}