/*
 * 文件名：CommonFilter.java
 * 描述：项目必需过滤器。
 * 修改人：刘可
 * 修改时间：2021-03-07
 */

package com.example.demo.filter;

import java.io.IOException;
import java.util.*;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.example.demo.constant.Constants;
import com.example.demo.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 常见过滤器。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @see doFilterInternal
 * @since 2021-03-07
 */
@Order(10)
@WebFilter(urlPatterns =
{
        "/article/*", "/user/*"
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
        Map<String, String> headers = new HashMap<String, String>();
        Enumeration<String> headerNames = request.getHeaderNames();

        while (headerNames.hasMoreElements())
        {
            String key = (String)headerNames.nextElement();
            String val = request.getHeader(key);
            headers.put(key, val);
        } // 结束：while (headerNames.hasMoreElements())
        response.setCharacterEncoding(Constants.CHARASET_UTF8);
        response.setContentType(Constants.CHARASET_UTF8CONTENTYPE);
        String token = request.getHeader(Constants.TOKEN);
        System.out.println(JSON.toJSONString(headers));

        if (!StringUtils.hasText(token))
        {
            System.out.println("没token");
            return;
        } // 结束：if (!StringUtils.hasText(token))
        Object loginStatus = redis.get(token, Object.class);

        if (Objects.isNull(loginStatus))
        {
            System.out.println("TOKEN错误： " + token);
            return;
        } // 结束：if (Objects.isNull(loginStatus))
        System.out.println("验证成功。");
        chain.doFilter(request, response);
    }

}
