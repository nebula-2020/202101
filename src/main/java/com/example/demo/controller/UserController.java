/*
 * 文件名：SignUpController.java
 * 描述：控制器负责用户注册业务
 * 修改人：刘可
 * 修改时间：2021-03-09
 */
package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.constant.*;
import com.example.demo.tool.*;
import com.example.demo.util.StringUtils;
import com.example.demo.vo.*;

import java.util.*;

import javax.validation.constraints.*;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.service.*;
import com.example.demo.enumation.*;

/**
 * 用户控制器。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @see initModel
 * @see signUp
 * @see codeReguest
 * @see passwordSignIn
 * @see codeSignIn
 * @since 2021-03-09
 */
@Controller
public class UserController extends CommonController
{
    @Autowired
    protected SignUpService signUpService;
    @Autowired
    protected SignInService signInService;
    @Autowired
    protected SmsService sms;
    @Autowired
    protected RedisService redis;

    /**
     * 初始化Model。
     * 
     * @param principal 可以用来表示任何实体，比如个人、公司和登录id
     * @param phone 手机号
     * @param key 客户端随机代码
     * @param code 短信验证码
     * @param sec 服务器随机代码
     * @param pwd 密码
     * @param ipv4 IPv4地址，大端模式
     * @param ipv6 IPv6地址，大端模式
     * @param mac 物理地址，大端模式
     * @param gps 地理位置
     * @param model 主要用于向Model添加属性
     */
    @ModelAttribute
    protected void initModel(
            @NotEmpty @Pattern(regexp = Constants.REGEXP_PHONE) @RequestParam(
                    value = Constants.KEY_USER_PHONE,
                    required = false
            ) String phone,
            @NotEmpty @RequestParam(
                    value = Constants.KEY_USER_PASSWORD,
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
            ) String gps,
            @RequestParam(
                    value = Constants.KEY_SMS_JS,
                    required = false
            ) String key,
            @RequestParam(
                    value = Constants.KEY_SMS_CODE,
                    required = false
            ) String code,
            @RequestParam(
                    value = Constants.KEY_SMS_SECRET,
                    required = false
            ) String sec, Model model
    )
    {
        model.addAttribute(Constants.KEY_USER_PHONE, phone);
        model.addAttribute(Constants.KEY_USER_PASSWORD, pwd);
        VisitVO visit = new VisitVO(ipv4, ipv6, mac, gps, SignInMethod.UNKNOWN);
        model.addAttribute(Constants.SESSION_LOCATION, visit);

        if (StringUtils.hasText(code, phone, key, sec))
        {
            SmsVO smsVo = new SmsVO(
                    phone, code, key, sec, System.currentTimeMillis()
            );
            model.addAttribute(Constants.SESSION_SMS, smsVo);
        } // 结束：if (!tool.isNullOrEmpty(code))
    }

    /**
     * 用户注册。
     * 
     * @param phone 手机号
     * @param pwd 密码
     * @param requestMap 描述短信验证码校验用信息
     * @param name 昵称
     * @param model 主要用于向Model添加属性
     * @return JSON字符串，用户手机号为键对应布尔值描述注册成功与否。
     */
    @RequestMapping("/codeSignUp")
    @ResponseBody
    protected String signUp(
            @NotNull @ModelAttribute(
                    value = Constants.KEY_USER_PHONE
            ) String phone,
            @NotNull @ModelAttribute(
                    value = Constants.KEY_USER_PASSWORD
            ) String pwd,
            @ModelAttribute(value = Constants.SESSION_SMS) SmsVO requestMap,
            @NotNull @RequestParam(value = Constants.KEY_USER_NAME) String name,
            Model model
    )
    {
        String ret = "";

        try
        {
            SmsVO sessionMap = redis.getSmsSession(phone);

            if (sms.verify(requestMap, Constants.TIME_1MIN, sessionMap))// 密钥匹配
            {
                // 验证成功
                boolean res = signUpService.signUp(phone, pwd, name);// 注册账号
                JSONObject result = new JSONObject();
                result.put(phone, res);// true表示注册成功
                System.out.println(result);// debug
                ret = result.toJSONString();
            } // 结束： if (sms.verify(requestMap, MSEC_60000, sessionMap))
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
        }
        return ret;

    }

    /**
     * 请求短信。
     * 
     * @param phone 手机号
     * @param key 客户端随机代码
     * @param model 主要用于向Model添加属性
     * @return JSON字符串，用户手机号为键对应值表示服务器随机代码。
     */
    @RequestMapping("/codeReg")
    @ResponseBody
    protected String codeReguest(
            @NotNull @ModelAttribute(
                    value = Constants.KEY_USER_PHONE
            ) String phone,
            @NotEmpty @NotNull @RequestParam(Constants.KEY_SMS_JS) String key,
            Model model
    )
    {
        JSONObject ret = new JSONObject();

        try
        {
            String sec = Long.toHexString(Rand.getRandom().nextLong());// 密钥：随机长整型转16进制
            SmsVO res = sms.send(phone, key, sec, Constants.TIME_1MIN);
            boolean succeed = redis.setSmsSession(res, Constants.TIME_1MIN);

            if (succeed)
            {
                ret.put(phone, sec);
            } // 结束：if(succeed)
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ret.toJSONString();
    }

    /**
     * 密码登录。
     * <p>
     * 账号和手机号有一个非空即可。
     * 
     * @param phone 手机号
     * @param pwd 密码
     * @param account 账号
     * @param pwd 密码
     * @param info 描述访问信息
     * @param model 主要用于向Model添加属性
     * @return JSON字符串，用户账号为键对应值表示登录成功与否。
     */
    @RequestMapping("/passwordSignIn")
    @ResponseBody
    protected String passwordSignIn(
            @ModelAttribute(value = Constants.KEY_USER_PHONE) String phone,
            @NotNull @ModelAttribute(
                    value = Constants.KEY_USER_PASSWORD
            ) String pwd,
            @RequestParam(
                    value = Constants.KEY_USER_ACCOUNT,
                    required = false
            ) String account,
            @ModelAttribute(value = Constants.SESSION_LOCATION) VisitVO info,
            Model model
    )
    {
        String ret = "";

        if (StringUtils.hasText(phone) || StringUtils.hasText(account))
        {

            try
            {
                info.setMethod(SignInMethod.PASSWORD);

                // 布尔值描述数据库操作成功与否
                String res =
                        signInService.signIn(phone, account, pwd, false, info);
                redis.deleteObj(phone);

                if (StringUtils.hasText(res))
                {
                    redis.setSignInSession(account, Constants.TIME_1HOUR);
                    JSONObject json = new JSONObject();
                    json.put(phone, res);
                    json.put(Constants.TOKEN, res);// ★暂时先存明文
                    System.out.print(json);
                    ret = json.toJSONString();
                } // 结束：if (StringUtils.hasText(res))
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
            }
        } // 结束：if (StringUtils.hasText(phone)||StringUtils.hasText (account))

        return ret;

    }

    /**
     * 短信验证登录。
     * 
     * @param smsInfo 描述短信验证码校验用信息
     * @param info 描述访问信息
     * @param model 主要用于向Model添加属性
     * @return JSON字符串，用户账号为键对应值表示登录成功与否。
     */
    @RequestMapping("/codeSignIn")
    @ResponseBody
    protected String codeSignIn(
            @NotNull @ModelAttribute(
                    value = Constants.SESSION_SMS
            ) SmsVO smsInfo,
            @ModelAttribute(value = Constants.SESSION_LOCATION) VisitVO info,
            Model model
    )
    {
        String ret = "";

        try
        {
            SmsVO sessionMap = redis.getSmsSession(smsInfo.getPhone());

            // 密钥匹配
            if (sessionMap != null
                    && sms.verify(smsInfo, Constants.TIME_1MIN, sessionMap))
            {
                info.setMethod(SignInMethod.SMS);
                String phone = smsInfo.getPhone();
                String res = signInService.signIn(phone, info);
                redis.deleteObj(phone);

                if (StringUtils.hasText(res))
                {
                    redis.setSignInSession(res, Constants.TIME_1HOUR);
                    JSONObject json = new JSONObject();
                    json.put(phone, res);
                    json.put(Constants.TOKEN, res);// ★暂时先存明文
                    System.out.print(json);
                    ret = json.toJSONString();
                } // 结束：if (StringUtils.hasText(res))
            } // 结束：if (sessionMap != null && sms.verify(smsInfo,...
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ret;
    }
}