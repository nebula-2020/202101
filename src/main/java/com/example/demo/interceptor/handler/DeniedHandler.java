/*
 * 文件名：DeniedHandler.java
 * 描述：访问处理器。
 * 修改人： 刘可
 * 修改时间：2021-03-06
 */
package com.example.demo.interceptor.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

/**
 * 用户无法访问。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @see handle
 * @since 2021-03-06
 */
@Component
public class DeniedHandler implements AccessDeniedHandler
{
    /**
     * 用户访问被拦截而未能进入控制器。
     * 
     * @param req 为servlet传参
     * @param res 为servlet传参
     * @param ex 拒绝访问异常
     */
    @Override
    public void handle(
            HttpServletRequest req, HttpServletResponse res,
            AccessDeniedException ex
    ) throws IOException, ServletException
    {
        res.setStatus(HttpServletResponse.SC_FORBIDDEN);
        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();
        out.write("{\"key\":\"权限不足...\"}");
        out.flush();
        out.close();
    }
}
