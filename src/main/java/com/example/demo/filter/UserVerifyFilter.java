/*
 * 文件名： UserVerifyFilter.java
 * 描述：项目必需过滤器。
 * 修改人：刘可
 * 修改时间：2021-03-08
 */

package com.example.demo.filter;

import java.io.IOException;
import java.util.*;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.demo.constant.Constants;
import com.example.demo.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 常见过滤器。
 * <p>
 * 验证token用。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @see doFilterInternal
 * @since 2021-03-08
 */
@Order(50)
@WebFilter(urlPatterns =
{
        "/article/*", "/user/*"
},
        filterName = "commonFilter"
)
public class UserVerifyFilter extends OncePerRequestFilter
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

        // 获取请求头所有信息
        while (headerNames.hasMoreElements())
        {
            String key = (String)headerNames.nextElement();
            String val = request.getHeader(key);
            headers.put(key, val);
        } // 结束：while (headerNames.hasMoreElements())

        String token = request.getHeader(Constants.TOKEN);// 找到token

        if (StringUtils.hasText(token) && redis.isKeyExist(token))
        {

            // 这里需要一个token解密
            try
            {
                boolean status = redis.get(token, boolean.class);

                if (status)
                {
                    chain.doFilter(request, response);
                    System.out.println("登录状态验证成功。");
                    return;
                } // 结束：if (Objects.isNull(loginStatus))
                System.out.println("TOKEN错误： " + token);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return;
        } // 结束：if (StringUtils.hasText(token)&&redis.isKeyExist(token))
        System.out.println("没token");
    }

}
