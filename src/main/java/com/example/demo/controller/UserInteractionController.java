/*
 * 文件名：UserInteractionController.java
 * 描述：必要控制器
 * 修改人：刘可
 * 修改时间：2021-02-20
 */

package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

import javax.validation.constraints.*;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.service.*;
import com.example.demo.constant.*;

/**
 * 用户互动控制器。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @see initModel
 * @see addBlackList
 * @see deleteBlackList
 * @see sendMsg
 * @see deleteMsg
 * @see hideMsg
 * @see addFocus
 * @see deleteFocus
 * @since 2021-02-20
 */
@Controller
public class UserInteractionController extends CommonController
{
    @Autowired
    private SignInService userService;
    @Autowired
    private UserInteractionService interaction;

    /**
     * 初始化Model。
     * 
     * @param account 请求者账号
     * @param pwd 请求者密码
     * @param focusAccount 目标用户账号
     * @param model 主要用于向Model添加属性
     */
    @ModelAttribute
    protected void initModel(
            @NotNull @NotEmpty @RequestParam(
                    value = Constants.KEY_USER_ACCOUNT
            ) String account,
            @NotNull @NotEmpty @RequestParam(
                    value = Constants.KEY_USER_PASSWORD
            ) String pwd,
            @NotNull @NotEmpty @RequestParam(
                    value = Constants.KEY_FOCUS_USER
            ) String focusAccount, Model model
    )
    {
        model.addAttribute(Constants.KEY_USER_ACCOUNT, account);
        model.addAttribute(Constants.KEY_USER_PASSWORD, pwd);
        model.addAttribute(Constants.KEY_FOCUS_USER, focusAccount);
    }

    /**
     * 拉黑用户。
     * 
     * @param account 请求者账号
     * @param pwd 请求者密码
     * @param focusAccount 目标用户账号
     * @param model 主要用于向Model添加属性
     * @return JSON字符串，用户账号为键对应布尔值描述操作成功与否。
     */
    @RequestMapping("user/black")
    @ResponseBody
    protected String addBlackList(
            @ModelAttribute(value = Constants.KEY_USER_ACCOUNT) String account,
            @ModelAttribute(value = Constants.KEY_USER_PASSWORD) String pwd,
            @ModelAttribute(
                    value = Constants.KEY_FOCUS_USER
            ) String focusAccount, Model model
    )
    {
        String ret = "";

        try
        {
            BigInteger id = userService.verify(account, pwd);
            BigInteger blackId = userService.account2Id(focusAccount);

            if (tool.isPositive(id, blackId))
            {
                boolean res = interaction.addBlackList(id, blackId);
                JSONObject result = new JSONObject();
                result.put(account, res);
                System.out.println(result);// debug
                ret = result.toJSONString();
            } // 结束：if (tool.isPositive(id, blackId))
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 取消黑名单用户。
     * 
     * @param account 请求者账号
     * @param pwd 请求者密码
     * @param focusAccount 目标用户账号
     * @param model 主要用于向Model添加属性
     * @return JSON字符串，用户账号为键对应布尔值描述操作成功与否。
     */
    @RequestMapping("user/deleteBlack")
    @ResponseBody
    protected String deleteBlackList(
            @ModelAttribute(value = Constants.KEY_USER_ACCOUNT) String account,
            @ModelAttribute(value = Constants.KEY_USER_PASSWORD) String pwd,
            @ModelAttribute(
                    value = Constants.KEY_FOCUS_USER
            ) String focusAccount, Model model
    )
    {
        String ret = "";

        try
        {
            BigInteger id = userService.verify(account, pwd);
            BigInteger blackId = userService.account2Id(focusAccount);

            if (tool.isPositive(id, blackId))
            {
                boolean res = interaction.deleteBlackList(id, blackId);
                JSONObject result = new JSONObject();
                result.put(account, res);
                System.out.println(result);// debug
                ret = result.toJSONString();
            } // 结束：if (tool.isPositive(id, blackId))
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 发送私信。
     * 
     * @param account 请求者账号
     * @param pwd 请求者密码
     * @param focusAccount 目标用户账号
     * @param text 私信文本
     * @param model 主要用于向Model添加属性
     * @return JSON字符串，用户账号为键对应布尔值描述操作成功与否。
     */
    @RequestMapping("user/msg")
    @ResponseBody
    protected String sendMsg(
            @ModelAttribute(value = Constants.KEY_USER_ACCOUNT) String account,
            @ModelAttribute(value = Constants.KEY_USER_PASSWORD) String pwd,
            @ModelAttribute(
                    value = Constants.KEY_FOCUS_USER
            ) String focusAccount,
            @NotNull @NotEmpty @NotBlank @RequestParam(
                    value = Constants.KEY_MSG_TEXT
            ) String text, Model model
    )
    {
        String ret = "";

        try
        {
            BigInteger fromId = userService.verify(account, pwd);
            BigInteger toId = userService.account2Id(focusAccount);

            if (tool.isPositive(fromId, toId))
            {
                long res = interaction.sendMsg(fromId, toId, text);
                JSONObject result = new JSONObject();
                result.put(account, res);
                System.out.println(result);// debug
                ret = result.toJSONString();
            } // 结束：if (tool.isPositive(fromId, toId))
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 删除私信。
     * 
     * @param account 请求者账号
     * @param pwd 请求者密码
     * @param focusAccount 目标用户账号
     * @param time 私信发出时间
     * @param model 主要用于向Model添加属性
     * @return JSON字符串，用户账号为键对应布尔值描述操作成功与否。
     */
    @RequestMapping("user/deleteMsg")
    @ResponseBody
    protected String deleteMsg(
            @ModelAttribute(value = Constants.KEY_USER_ACCOUNT) String account,
            @ModelAttribute(value = Constants.KEY_USER_PASSWORD) String pwd,
            @ModelAttribute(
                    value = Constants.KEY_FOCUS_USER
            ) String focusAccount,
            @NotNull @RequestParam(
                    value = Constants.KEY_MSG_DATETIME
            ) Long time, Model model
    )
    {
        String ret = "";

        try
        {
            BigInteger fromId = userService.verify(account, pwd);
            BigInteger toId = userService.account2Id(focusAccount);

            if (tool.isPositive(fromId, toId))
            {
                int res = interaction.deleteMsg(fromId, toId, time);
                JSONObject result = new JSONObject();
                result.put(account, res);
                System.out.println(result);// debug
                ret = result.toJSONString();
            } // 结束：if (tool.isPositive(fromId, toId))
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 隐藏一个私信。
     * <p>
     * 让一条私信单方面不可见。
     * 
     * @param account 请求者账号
     * @param pwd 请求者密码
     * @param focusAccount 目标用户账号
     * @param time 私信发出时间
     * @param model 主要用于向Model添加属性
     * @return JSON字符串，用户账号为键对应布尔值描述操作成功与否。
     */
    @RequestMapping("user/hideMsg")
    @ResponseBody
    protected String hideMsg(
            @ModelAttribute(value = Constants.KEY_USER_ACCOUNT) String account,
            @ModelAttribute(value = Constants.KEY_USER_PASSWORD) String pwd,
            @ModelAttribute(
                    value = Constants.KEY_FOCUS_USER
            ) String focusAccount,
            @NotNull @RequestParam(
                    value = Constants.KEY_MSG_DATETIME
            ) Long time, Model model
    )
    {
        String ret = "";

        try
        {
            BigInteger toId = userService.verify(account, pwd);
            BigInteger fromId = userService.account2Id(focusAccount);

            if (tool.isPositive(fromId, toId))
            {
                int res = interaction.hideMsg(fromId, toId, time);
                JSONObject result = new JSONObject();
                result.put(account, res);
                System.out.println(result);// debug
                ret = result.toJSONString();
            } // 结束：if (tool.isPositive(fromId, toId))
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 关注用户。
     * 
     * @param account 请求者账号
     * @param pwd 请求者密码
     * @param focusAccount 目标用户账号
     * @param model 主要用于向Model添加属性
     * @return JSON字符串，用户账号为键对应布尔值描述操作成功与否。
     */
    @RequestMapping("user/focus")
    @ResponseBody
    protected String addFocus(
            @ModelAttribute(value = Constants.KEY_USER_ACCOUNT) String account,
            @ModelAttribute(value = Constants.KEY_USER_PASSWORD) String pwd,
            @ModelAttribute(
                    value = Constants.KEY_FOCUS_USER
            ) String focusAccount, Model model
    )
    {
        String ret = "";

        try
        {
            BigInteger fanId = userService.verify(account, pwd);
            BigInteger userId = userService.account2Id(focusAccount);

            if (tool.isPositive(fanId, userId))
            {
                boolean res = interaction.addFocus(fanId, userId);
                JSONObject result = new JSONObject();
                result.put(account, res);
                System.out.println(result);// debug
                ret = result.toJSONString();
            } // 结束：if (tool.isPositive(fanId, userId))
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 取关用户。
     * 
     * @param account 请求者账号
     * @param pwd 请求者密码
     * @param focusAccount 目标用户账号
     * @param model 主要用于向Model添加属性
     * @return JSON字符串，用户账号为键对应布尔值描述操作成功与否。
     */

    @RequestMapping("user/deleteFocus")
    @ResponseBody
    protected String deleteFocus(
            @ModelAttribute(value = Constants.KEY_USER_ACCOUNT) String account,
            @ModelAttribute(value = Constants.KEY_USER_PASSWORD) String pwd,
            @ModelAttribute(
                    value = Constants.KEY_FOCUS_USER
            ) String focusAccount, Model model
    )
    {
        String ret = "";

        try
        {
            BigInteger fanId = userService.verify(account, pwd);
            BigInteger userId = userService.account2Id(focusAccount);

            if (tool.isPositive(fanId, userId))
            {
                boolean res = interaction.deleteFocus(fanId, userId);
                JSONObject result = new JSONObject();
                result.put(account, res);
                System.out.println(result);// debug
                ret = result.toJSONString();
            } // 结束：if (tool.isPositive(fanId, userId))
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ret;
    }
}
