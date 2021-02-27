/*
 * 文件名：CommonFilter.java
 * 描述：项目必需过滤器。
 * 修改人：刘可
 * 修改时间：2021-02-27
 */

package com.example.demo.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.*;
import org.springframework.web.filter.OncePerRequestFilter;
/**
 * 常见过滤器。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @see doFilterInternal
 * @since 2021-02-27
 */
@Component
@Order(10)
public class CommonFilter extends OncePerRequestFilter
{
    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response,
            FilterChain chain
    ) throws ServletException, IOException
    {
        System.out.println("Filter Running");
        chain.doFilter(request, response);
    }

}
