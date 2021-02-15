/*
 * 文件名：SignUpController.java
 * 描述：控制器负责用户注册业务
 * 修改人：刘可
 * 修改时间：2021-02-16
 */
package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.constant.*;
import com.example.demo.tool.*;
import com.example.demo.vo.SmsVO;
import com.example.demo.vo.VisitVO;

import java.util.*;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.*;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.service.*;
import com.example.demo.entity.*;

/**
 * 用户控制器。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @see signUp
 * @see codeReguest
 * @see passwordSignIn
 * @see codeSignIn
 * @since 2021-02-16
 */
@Controller
public class UserController extends CommonController
{
    public final long MSEC_60000 = 60000;
    public final long MSEC_1000 = 1000;
    /**
     * 描述一分钟等于多少秒。
     */
    public final long MIN2SEC = 60;
    public final String UNIT_OF_TIME = "分钟";
    public final String PROJECT_NAME = "星云社区";
    private final String KEY_SMS_INFO = Constants.SESSION_SMS;
    private final String KEY_PHONE = Constants.KEY_USER_PHONE;
    private final String KEY_PASSWORD = Constants.KEY_USER_PASSWORD;
    private final String KEY_LOCATION = "_location";
    @Autowired
    private SignUpService signUpService;
    @Autowired
    private SignInService signInService;
    @Autowired
    private SmsService sms;
    @Autowired
    private RedisService redis;

    @ModelAttribute
    public void getPhone(
            @NotEmpty @NotNull @Pattern(
                    regexp = Constants.REGEXP_PHONE
            ) @RequestParam(value = Constants.KEY_USER_PHONE) String phone,
            @RequestParam(
                    value = KEY_PASSWORD,
                    required = false
            ) String pwd,
            @RequestParam(
                    value = Constants.KEY_SIGNIN_IPV4,
                    required = false
            ) Long ipv4,
            @RequestParam(
                    value = Constants.KEY_SIGNIN_IPV6,
                    required = false
            ) List<Byte> ipv6,
            @RequestParam(
                    value = Constants.KEY_SIGNIN_MAC,
                    required = false
            ) List<Byte> mac,
            @RequestParam(
                    value = Constants.KEY_SIGNIN_GPS,
                    required = false
            ) String gps, Model model
    )
    {
        model.addAttribute(KEY_PHONE, phone);
        model.addAttribute(KEY_PASSWORD, pwd);
        VisitVO visit = new VisitVO(ipv4, ipv6, mac, gps, SignInMethod.UNKNOWN);
        model.addAttribute(KEY_LOCATION, visit);
    }

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
    @RequestMapping("user/codeSignUp")
    @ResponseBody
    protected String signUp(
            @ModelAttribute(value = KEY_PHONE) String phone,
            @ModelAttribute(value = KEY_PASSWORD) String pwd,
            @RequestParam(value = Constants.KEY_USER_NAME) String name,
            @RequestParam(value = Constants.KEY_SMS_JS) String key,
            @RequestParam(value = Constants.KEY_SMS_CODE) String code,
            @RequestParam(value = Constants.KEY_SMS_SECRET) String sec,
            HttpSession session, Model model
    )
    {
        String ret = "";

        if (!tool.containsNullOrEmpty(phone, key, code, pwd, name, sec))
        {

            try
            {
                SmsVO sessionMap = redis.get(KEY_SMS_INFO, SmsVO.class);
                Long now = System.currentTimeMillis();
                SmsVO requestMap = new SmsVO(phone, code, key, sec, now);

                if (sms.verify(requestMap, MSEC_60000, sessionMap))// 密钥匹配
                {
                    // 验证成功
                    boolean res = signUpService.signUp(phone, pwd, name);// 注册账号
                    session.invalidate();// 销毁session
                    JSONObject result = new JSONObject();
                    result.put(phone, res);// true表示注册成功
                    System.out.println(result);// debug
                    ret = result.toJSONString();
                }// 结束： if (sms.verify(requestMap, MSEC_60000, sessionMap))
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
            }
        }// 结束：if (!tool.containsNullOrEmpty(phone, key, code, pwd, name, sec))
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
    @RequestMapping("user/codeReg")
    @ResponseBody
    protected String codeReguest(
            @ModelAttribute(value = KEY_PHONE) String phone,
            @NotEmpty @NotNull @RequestParam(Constants.KEY_SMS_JS) String key,
            HttpSession session, Model model
    )
    {
        JSONObject ret = new JSONObject();

        // 60秒内不能重发，得等上一个session失效
        if (!redis.isKeyExist(KEY_SMS_INFO))
        {

            if (!tool.containsNullOrEmpty(phone, key))
            {

                try
                {
                    String sec = Long.toHexString(Rand.getRandom().nextLong());// 密钥：随机长整型转16进制
                    SmsVO res = sms.send(phone, key, sec, MSEC_60000);
                    Calendar time = Calendar.getInstance();
                    time.setTimeInMillis(MSEC_60000);
                    redis.set(KEY_SMS_INFO, res, MSEC_60000);
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
        } // 结束：if (!redis.isKeyExist(KEY_SMS_INFO))

        return ret.toJSONString();
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
    @RequestMapping("user/passwordSignIn")
    @ResponseBody
    protected String passwordSignIn(
            @ModelAttribute(value = KEY_PHONE) String phone,
            @ModelAttribute(value = KEY_PASSWORD) String pwd,
            @RequestParam(
                    value = Constants.KEY_USER_ACCOUNT,
                    required = false
            ) String account,
            @ModelAttribute(value = KEY_LOCATION) VisitVO info,
            HttpSession session, Model model
    )
    {
        String ret = "";

        if (!tool.isNullOrEmpty(phone, account))
        {

            try
            {
                info.setMethod(SignInMethod.PASSWORD);

                // 布尔值描述数据库操作成功与否
                boolean res =
                        signInService.signIn(phone, account, pwd, false, info);
                redis.deleteObj(phone);

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
    @RequestMapping("user/codeSignIn")
    @ResponseBody
    protected String codeSignIn(
            @ModelAttribute(value = KEY_PHONE) String phone,
            @RequestParam(value = Constants.KEY_SMS_JS) String key,
            @RequestParam(value = Constants.KEY_SMS_CODE) String code,
            @RequestParam(value = Constants.KEY_SMS_SECRET) String sec,
            @ModelAttribute(value = KEY_LOCATION) VisitVO info,
            HttpSession session, Model model
    )
    {
        String ret = "";

        if (!tool.isNullOrEmpty(phone))
        {

            try
            {
                SmsVO sessionMap = redis.get(KEY_SMS_INFO, SmsVO.class);
                Long now = System.currentTimeMillis();
                SmsVO requestMap = new SmsVO(phone, code, key, sec, now);

                if (sms.verify(requestMap, MSEC_60000, sessionMap))// 密钥匹配
                {
                    info.setMethod(SignInMethod.SMS);

                    // 布尔值描述数据库操作成功与否
                    boolean res =
                            signInService.signIn(phone, null, null, true, info);
                    redis.deleteObj(phone);

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