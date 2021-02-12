/*
 * 文件名：InterceptorConfig.java
 * 描述：拦截器配置。
 * 修改人：刘可
 * 修改时间：2021-02-12
 */

package com.example.demo.config;

import com.example.demo.interceptor.CommonInterceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 定义回调方法。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @see addInterceptors
 * @see securityInterceptor
 * @since 2021-02-12
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer
{
    /**
     * 用于创建主要拦截器。
     * 
     * @return 拦截器。
     */
    @Bean
    public CommonInterceptor securityInterceptor()
    {
        return new CommonInterceptor();
    }

    /**
     * 添加Spring MVC生命周期拦截器，
     * 用于控制器方法调用和资源处理程序请求的预处理和后处理。
     * 可以注册拦截器以应用于所有请求，
     * 也可以限制为URL模式的一个子集。
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(securityInterceptor())
                .excludePathPatterns("/static/*").addPathPatterns("/**");
    }
}