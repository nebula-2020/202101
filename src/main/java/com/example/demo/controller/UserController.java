/*
 * 文件名：SignUpController.java
 * 描述：控制器负责用户注册业务
 * 修改人：刘可
 * 修改时间：2021-02-03
 */
package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.demo.tool.*;

import java.util.*;

import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.SignInInfo;
import com.example.demo.service.*;
import com.example.demo.entity.*;

/**
 * 用户控制器。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @see signUp
 * @see codeReguest
 * @since 2021-02-02
 */
@Controller
public class UserController
{
    public final long MSEC_60000 = 60000;
    public final long MSEC_1000 = 1000;
    private final short UBYTE_MAX = 0xff;
    /**
     * 描述一分钟等于多少秒。
     */
    public final long MIN2SEC = 60;
    public final String UNIT_OF_TIME = "分钟";
    public final String PROJECT_NAME = "星云社区";
    /**
     * 手机号。
     */
    public final String KEY_PHONE = "phone";
    /**
     * 服务器随机代码。
     */
    public final String KEY_SECRET = "server";
    /**
     * 短信验证码。
     */
    public final String KEY_CODE = "code";
    /**
     * 客户端随机代码。
     */
    public final String KEY_KEY = "js";
    /**
     * 时间。
     */
    public final String KEY_TIME = "time";
    /**
     * 昵称。
     */
    public final String KEY_NAME = "name";
    /**
     * 密码。
     */
    public final String KEY_PASSWORD = "pwd";

    /**
     * 账号。
     */
    public final String KEY_ACCOUNT = "account";
    /**
     * IPv4。
     */
    public final String KEY_IPV4 = "ipv4";
    /**
     * IPv6。
     */
    public final String KEY_IPV6 = "ipv6[]";
    /**
     * 物理地址。
     */
    public final String KEY_MAC = "mac[]";
    /**
     * 地理位置。
     */
    public final String KEY_GPS = "gps";
    @Autowired
    private SignUpService signUpService;
    @Autowired
    private SignInService signInService;
    @Autowired
    private SmsService sms;
    private final CommonTool tool = new CommonTool();

    /**
     * 用户注册。
     * 
     * @param phone 手机号
     * @param key 客户端随机代码
     * @param code 短信验证码
     * @param pwd 密码
     * @param name 昵称
     * @param sec 服务器随机代码
     * @param session 提供一种方法，以跨对网站的多个页面请求或访问识别用户，并存储有关该用户的信息
     * @return JSON字符串，用户手机号为键对应布尔值描述注册成功与否。
     */
    @RequestMapping("codeSignUp")
    @ResponseBody
    protected String signUp(
            @RequestParam(value = KEY_PHONE) String phone,
            @RequestParam(value = KEY_KEY) String key,
            @RequestParam(value = KEY_CODE) String code,
            @RequestParam(value = KEY_PASSWORD) String pwd,
            @RequestParam(value = KEY_NAME) String name,
            @RequestParam(value = KEY_SECRET) String sec, HttpSession session
    )
    {
        String ret = "";

        if (!tool.containsNullOrEmpty(phone, key, code, pwd, name, sec))
        {

            try
            {
                Map<String, Object> sessionMap = new HashMap<>();
                Iterator<String> iterator =
                        session.getAttributeNames().asIterator();

                while (iterator.hasNext())
                {
                    String attrName = iterator.next();
                    Object sessionObject = session.getAttribute(attrName);
                    sessionMap.put(attrName, sessionObject);
                } // 结束：while (iterator.hasNext())

                Long now = System.currentTimeMillis();
                Map<String, Object> requestMap =
                        sms.getMap(phone, code, key, sec, now);

                if (sms.verify(requestMap, sessionMap))// 密钥匹配
                {
                    // 验证成功
                    boolean res = signUpService.signUp(phone, pwd, name);// 注册账号
                    session.invalidate();// 销毁session
                    JSONObject json = new JSONObject();
                    json.put(phone, res);// true表示注册成功
                    System.out.println(json);// debug
                    ret = json.toJSONString();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
            }
        }
        return ret;

    }

    /**
     * 请求短信。
     * 
     * @param phone 手机号
     * @param key 客户端随机代码
     * @param session 提供一种方法，以跨对网站的多个页面请求或访问识别用户，并存储有关该用户的信息
     * @return JSON字符串，用户手机号为键对应值表示服务器随机代码。
     */
    @RequestMapping("codeReg")
    @ResponseBody
    protected String codeReguest(
            @RequestParam(value = KEY_PHONE) String phone,
            @RequestParam(value = KEY_KEY) String key, HttpSession session
    )
    {
        JSONObject ret = new JSONObject();

        // 60秒内不能重发，得等上一个session失效
        if (true)
        {

            if (!tool.containsNullOrEmpty(phone, key))
            {

                try
                {
                    String sec = Long.toHexString(Rand.getRandom().nextLong());// 密钥：随机长整型转16进制
                    Map<String, Object> res =
                            sms.send(phone, key, sec, MSEC_60000);

                    for (Map.Entry<String, Object> entry: res.entrySet())
                    {
                        session.setAttribute(entry.getKey(), entry.getValue());
                    } // 结束：for(Map.Entry<String,Object>entry:res.entrySet())

                    Calendar time = Calendar.getInstance();
                    time.setTimeInMillis(MSEC_60000);
                    session.setMaxInactiveInterval(time.get(Calendar.SECOND));// 一分钟有效

                    ret.put(phone, sec);
                }
                catch (NullPointerException e)
                {
                    e.printStackTrace();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            } // 结束：if (!tool.containsNullOrEmpty(phone, key))
        } // 结束：if(true)

        return ret.toJSONString();
    }

    /**
     * 构造账号登录信息的实体，登录用。
     * 
     * @param ipv4 IPv4地址，大端模式
     * @param ipv6 IPv6地址，大端模式
     * @param mac 物理地址，大端模式
     * @param gps 地理位置
     * @param method 登陆方式
     * @return 账号登录信息的实体。
     */
    protected SignInInfo valsToInfo(
            List<Byte> ipv4, List<Byte> ipv6, List<Byte> mac, String gps,
            SignInMethod method
    )
    {
        byte[] ipv6Array =tool.toByteArray(ipv6);
        byte[] macArray = tool.toByteArray(mac);
        byte[] ipv4Array = tool.toByteArray(ipv4);
        long ipv4Value = 0;

        for (int i = 0; i < ipv4Array.length; i++)
        {
            ipv4Value <<= Byte.SIZE;
            ipv4Value |= (ipv4Array[i] & UBYTE_MAX);
        } // 结束：for (int i = 0; i <ipv4Array.length; i++)
        SignInInfo info = new SignInInfo();
        info.setIpv4(ipv4Value);
        info.setIpv6(ipv6Array);
        info.setMac(macArray);
        info.setGps(gps);

        if (method == null)
        {
            info.setMethod(SignInMethod.UNKNOWN.getValue());
        }
        else
        {
            info.setMethod(method.getValue());
        } // 结束：if (method == null)
        return info;
    }

    /**
     * 密码登录。
     * <p>
     * 账号和手机号有一个非空即可。
     * 
     * @param phone 手机号
     * @param account 账号
     * @param pwd 密码
     * @param ipv4 IPv4地址，大端模式
     * @param ipv6 IPv6地址，大端模式
     * @param mac 物理地址，大端模式
     * @param gps 地理位置
     * @param session 提供一种方法，以跨对网站的多个页面请求或访问识别用户，并存储有关该用户的信息
     * @return JSON字符串，用户手机号为键对应值表示登录成功与否。
     */
    @RequestMapping("passwordSignIn")
    @ResponseBody
    protected String passwordSignIn(
            @RequestParam(
                    value = KEY_PHONE,
                    required = false
            ) String phone,
            @RequestParam(
                    value = KEY_ACCOUNT,
                    required = false
            ) String account, @RequestParam(value = KEY_PASSWORD) String pwd,
            @RequestParam(value = KEY_IPV4) List<Byte> ipv4,
            @RequestParam(value = KEY_IPV6) List<Byte> ipv6,
            @RequestParam(value = KEY_MAC) List<Byte> mac,
            @RequestParam(value = KEY_GPS) String gps, HttpSession session
    )
    {
        String ret = "";

        if (!tool.isNullOrEmpty(phone, account))
        {

            try
            {
                SignInInfo info =
                        valsToInfo(ipv4, ipv6, mac, gps, SignInMethod.PASSWORD);

                // 布尔值描述数据库操作成功与否
                boolean res =
                        signInService.signIn(phone, account, pwd, false, info);

                if (res)
                {
                    JSONObject json = new JSONObject();
                    json.put(phone, res);
                    System.out.print(json);
                    ret = json.toJSONString();
                } // 结束：if(res)
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
            }
        } // 结束：if (!tool.isNullOrEmpty(phone, account))

        return ret;

    }

    /**
     * 短信验证登录。
     * 
     * @param phone 手机号
     * @param key 客户端随机代码
     * @param code 短信验证码
     * @param sec 服务器随机代码
     * @param ipv4 IPv4地址，大端模式
     * @param ipv6 IPv6地址，大端模式
     * @param mac 物理地址，大端模式
     * @param gps 地理位置
     * @param session 提供一种方法，以跨对网站的多个页面请求或访问识别用户，并存储有关该用户的信息
     * @return JSON字符串，用户手机号为键对应值表示登录成功与否。
     */
    @RequestMapping("codeSignIn")
    @ResponseBody
    protected String codeSignIn(
            @RequestParam(value = KEY_PHONE) String phone,
            @RequestParam(value = KEY_KEY) String key,
            @RequestParam(value = KEY_CODE) String code,
            @RequestParam(value = KEY_SECRET) String sec,
            @RequestParam(value = KEY_IPV4) List<Byte> ipv4,
            @RequestParam(value = KEY_IPV6) List<Byte> ipv6,
            @RequestParam(value = KEY_MAC) List<Byte> mac,
            @RequestParam(value = KEY_GPS) String gps, HttpSession session
    )
    {
        String ret = "";

        if (!tool.isNullOrEmpty(phone))
        {

            try
            {

                Map<String, Object> sessionMap = new HashMap<>();
                Iterator<String> iterator =
                        session.getAttributeNames().asIterator();

                while (iterator.hasNext())
                {
                    String attrName = iterator.next();
                    Object sessionObject = session.getAttribute(attrName);
                    sessionMap.put(attrName, sessionObject);
                } // 结束：while (iterator.hasNext())

                Long now = System.currentTimeMillis();
                Map<String, Object> requestMap =
                        sms.getMap(phone, code, key, sec, now);

                if (sms.verify(requestMap, sessionMap))// 密钥匹配
                {
                    SignInInfo info =
                            valsToInfo(ipv4, ipv6, mac, gps, SignInMethod.SMS);

                    // 布尔值描述数据库操作成功与否
                    boolean res =
                            signInService.signIn(phone, null, null, true, info);

                    if (res)
                    {
                        JSONObject json = new JSONObject();
                        json.put(phone, res);
                        System.out.print(json);
                        ret = json.toJSONString();
                    } // 结束：if(res)
                } // 结束：if (sms.verify(requestMap, sessionMap))
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        } // 结束：if (!tool.isNullOrEmpty(phone))
        return ret;
    }
}