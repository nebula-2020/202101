/*
 * 文件名：TokenService.java
 * 描述：项目主要服务。
 * 修改人：刘可
 * 修改时间：2021-03-08
 */

package com.example.demo.service;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.*;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import com.example.demo.constant.Constants;
import org.springframework.util.*;
import org.springframework.stereotype.Service;

/**
 * token加密、解密(创建、读取)以及其它操作。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @see createToken
 * @see getToken
 * @since 2021-03-08
 */
@Service("tokenService")
public class TokenService extends ComService
{
    /**
     * 将账号加密。
     * 
     * @param account 用户账号
     * @return token中的值信息。
     */
    public String createToken(String account)
    {

        MessageDigest messageDigest;
        String ret = "";

        try
        {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(account.getBytes(Constants.CHARASET_UTF8));
            BigInteger bigInt = new BigInteger(messageDigest.digest());
            ret = bigInt.toString(Constants.NUM_36);
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 从请求头读取token。
     * 
     * @param request 为servlet传参
     * @return 从请求读取的token值。
     */
    public String getToken(HttpServletRequest request)
    {
        String ret = "";

        try
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

            if (StringUtils.hasText(token)
                    && token.startsWith(Constants.TOKEN_PREFIX))
            {
                StringBuilder builder = new StringBuilder(token);
                ret = builder.substring(Constants.NUM_TOKENPREFIXLENGTH);
            } // 结束：if (StringUtils.hasText(token)&&token.startsWith...
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ret;
    }
}
