/*
 * 文件名：DemoApplication.java
 * 描述：项目主文件
 * 修改人：刘可
 * 修改时间：2021-02-08
 */
package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 主类。
 */
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class
},
        scanBasePackages =
        {
                "com.example.demo"
        }
)
@ComponentScan(

    "com.example.demo"
)
@EntityScan("com.example.demo")
@EnableJpaRepositories("com.example.demo")
public class DemoApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(DemoApplication.class, args);
    }

}
