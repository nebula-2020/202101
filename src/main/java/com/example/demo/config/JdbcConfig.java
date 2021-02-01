package com.example.demo.config;

import javax.sql.DataSource;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.PlatformTransactionManager;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * spring的配置类，相当于bean.xml
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @see createDataSource
 * @see createJdbcTemplate
 * @see createTransactionManager
 * @since 2021-02-01
 */
@Configuration // <beans>
@ComponentScan(basePackages =
{ "pers.nebula.nebulahome" }) // 需要扫描的包
@PropertySource("application.properties")
@EnableJpaRepositories
@EnableTransactionManagement // 开启spring对事务注解的支持
public class JdbcConfig
{

    @Value("${spring.datasource.driver-class-name}")
    private String driver;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    /**
     * 新建数据源。
     * 
     * @return 数据源。
     */
    @Bean(name = "dataSource")
    public DataSource createDataSource()
    {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(driver);
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        return ds;
    }

    /**
     * 用指定数据源新建JdbcTemplate模板。
     * 
     * @param dataSource 数据源
     * @return 模板。
     */
    @Bean(name = "jdbcTemplate")
    public JdbcTemplate createJdbcTemplate(DataSource dataSource)
    {
        return new JdbcTemplate(dataSource);
    }

    /**
     * 新建事务管理器。
     * 
     * @param dataSource 数据源
     * @return 事务管理器。
     */
    @Bean(name = "transactionManager")
    public PlatformTransactionManager
            createTransactionManager(DataSource dataSource)
    {
        return new DataSourceTransactionManager(dataSource);
    }
}
