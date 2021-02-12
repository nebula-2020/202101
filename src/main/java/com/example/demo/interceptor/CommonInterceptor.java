/*
 * 文件名：CommonInterceptor.java
 * 描述：项目主要拦截器。
 * 修改人：刘可
 * 修改时间：2021-02-12
 */

package com.example.demo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 拦截器用于非法请求参数。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @see afterCompletion
 * @see postHandle
 * @see preHandle
 * @since 2021-02-12
 */
@Component
public class CommonInterceptor implements HandlerInterceptor
{
    /**
     * 定义请求完成操作。
     */
    @Override
    public void afterCompletion(
            HttpServletRequest request, HttpServletResponse response,
            Object handler, Exception ex
    ) throws Exception
    {
        HandlerInterceptor.super.afterCompletion(
                request, response, handler, ex
        );
    }

    /**
     * 定义请求后的操作。
     */
    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response,
            Object handler, ModelAndView modelAndView
    ) throws Exception
    {
        HandlerInterceptor.super.postHandle(
                request, response, handler, modelAndView
        );
    }

    /**
     * 定义请求前的操作。
     */
    @Override
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response,
            Object handler
    ) throws Exception
    {
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}