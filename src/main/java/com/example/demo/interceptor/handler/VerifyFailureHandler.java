/*
 * 文件名：VerifyFailureHandler.java
 * 描述：访问处理器。
 * 修改人： 刘可
 * 修改时间：2021-03-06
 */
package com.example.demo.interceptor.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

/**
 * 用户访问未成功。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @see onAuthenticationFailure
 * @since 2021-03-06
 */
@Component
public class VerifyFailureHandler implements AuthenticationFailureHandler
{
    /**
     * 描述用户访问失败。
     * 
     * @param req 为servlet传参
     * @param res 为servlet传参
     * @param ex 拒绝访问异常
     */
    @Override
    public void onAuthenticationFailure(
            HttpServletRequest req, HttpServletResponse res,
            AuthenticationException ex
    ) throws IOException, ServletException
    {
        System.out.println("登录失败!");
        res.setStatus(401);
    }
}
