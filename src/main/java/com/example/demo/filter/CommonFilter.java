/*
 * 文件名：CommonFilter.java
 * 描述：项目必需过滤器。
 * 修改人：刘可
 * 修改时间：2021-03-08
 */

package com.example.demo.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.demo.constant.Constants;
import com.example.demo.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 常见过滤器。
 * <p>
 * 设置request格式为UTF-8。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @see doFilterInternal
 * @since 2021-03-08
 */
@Order(10)
@WebFilter(urlPatterns =
{
        "/*"
},
        filterName = "commonFilter"
)
public class CommonFilter extends OncePerRequestFilter
{
    @Autowired
    protected RedisService redis;

    /**
     * 过滤器逻辑。
     * 
     * @param request 为servlet传参
     * @param response 为servlet传参
     * @param chain 提供了一个查看经过过滤的资源请求的调用链的视图
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response,
            FilterChain chain
    ) throws ServletException, IOException
    {
        response.setCharacterEncoding(Constants.CHARASET_UTF8);
        response.setContentType(Constants.CHARASET_UTF8CONTENTYPE);
        chain.doFilter(request, response);
    }

}
