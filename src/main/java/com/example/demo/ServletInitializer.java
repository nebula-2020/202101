/*
 * 文件名：ServletInitializer.java
 * 描述：Servlet初始化
 * 修改人：刘可
 * 修改时间：2021-02-88
 */
package com.example.demo;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer
{

    @Override
    protected SpringApplicationBuilder
            configure(SpringApplicationBuilder application)
    {
        return application.sources(DemoApplication.class);
    }

}
