/*
 * 文件名： UserVerifyFilter.java
 * 描述：项目必需过滤器。
 * 修改人：刘可
 * 修改时间：2021-03-09
 */

package com.example.demo.filter;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.example.demo.constant.Constants;
import com.example.demo.service.*;

import org.apache.catalina.util.ParameterMap;
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
 * @since 2021-03-09
 */
@Order(50)
@WebFilter(urlPatterns =
{
        "/article/*", "/user/*"
},
        filterName = "userVerifyFilter"
)
public class UserVerifyFilter extends OncePerRequestFilter
{
    @Autowired
    protected RedisService redis;
    @Autowired
    private SignInService signInService;

    /**
     * 过滤器逻辑。
     * <p>
     * token中存储了用户的账号(目前未加密)。方法会将账号和用户ID一同存储进请求。
     * 
     * @param request 为servlet传参
     * @param response 为servlet传参
     * @param chain 提供了一个查看经过过滤的资源请求的调用链的视图
     * @since 2021-03-09
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

        if (StringUtils.hasText(token) && //
                redis.isKeyExist(token))
        {

            // 这里需要一个token解密
            try
            {
                boolean status = redis.get(token, boolean.class);

                if (status)
                {
                    BigInteger id = signInService.account2Id(token);

                    if (id != null && BigInteger.ZERO.compareTo(id) < 0)
                    {
                        ParameterMap<String, String[]> map =
                                (ParameterMap<String, String[]>)request
                                        .getParameterMap();
                        map.setLocked(false);// 设置对象锁定状态
                        map.put(Constants.KEY_USER_ID, new String[] {
                                id.toString()
                        });
                        map.put(Constants.KEY_USER_ACCOUNT, new String[] {
                                token
                        });// 插入(覆盖)值
                        map.setLocked(true);// 重新锁定
                        System.out.println(JSON.toJSONString(map));
                        chain.doFilter(request, response);
                        System.out.println("登录状态验证成功。");
                        return;
                    }
                    else
                    {
                        System.out.println("账号不存在或已经注销。");
                    } // 结束：if(id!=null&&BigInteger.ZERO.compareTo(id)<0)
                } // 结束：if (status)
                System.out.println("TOKEN错误： " + token);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return;
        } // 结束：if (StringUtils.hasText(token)&&redis.isKeyExist(token))
        System.out.println("没token");
        System.out.println(JSON.toJSONString(headers));
    }

}
