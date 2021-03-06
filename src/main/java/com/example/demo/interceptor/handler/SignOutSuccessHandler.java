/*
 * 文件名：SignOutSuccessHandler.java
 * 描述：访问处理器。
 * 修改人： 刘可
 * 修改时间：2021-03-06
 */

package com.example.demo.interceptor.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * 用户登出成功。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @see onLogoutSuccess
 * @since 2021-03-06
 */
@Component
public class SignOutSuccessHandler implements LogoutSuccessHandler
{

    /**
     * 描述用户登出。
     * 
     * @param req 为servlet传参
     * @param res 为servlet传参
     * @param auth 身份验证
     */
    @Override
    public void onLogoutSuccess(
            HttpServletRequest req, HttpServletResponse res, Authentication auth
    ) throws IOException, ServletException
    {
        System.out.println("注销成功!");
    }

}
