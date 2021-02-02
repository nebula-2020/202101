/*
 * 文件名：SignUpController.java
 * 描述：控制器负责用户注册业务
 * 修改人：刘可
 * 修改时间：2021-02-02
 */
package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.demo.tool.*;

import java.util.*;

import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.service.*;

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
    private SignUpService service;
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
                    boolean res = service.signUp(phone, pwd, name);// 注册账号
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

        if (!tool.containsNullOrEmpty(phone, key))
        {

            // 60秒内不能重发，得等上一个session失效
            if (tool.isNullOrEmpty((String)session.getAttribute(KEY_PHONE)))
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
            } // 结束：if(tool.isNullOrEmpty((String)session.getAttribute(KEY_PHONE)))
        } // 结束：if (!tool.containsNullOrEmpty(phone, key))

        return ret.toJSONString();
    }

}
