/*
 * 文件名：ReportController.java
 * 描述：必要控制器
 * 修改人：刘可
 * 修改时间：2021-02-19
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
import com.example.demo.entity.ReportCause;

/**
 * 用户举报控制器。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @see initModel
 * @see report
 * @since 2021-02-19
 */
@Controller
public class ReportController extends CommonController
{
    @Autowired
    private ReportService reportService;
    @Autowired
    private SignInService signInService;

    /**
     * 初始化Model。
     * 
     * @param account 账号
     * @param pwd 密码
     * @param model 主要用于向Model添加属性
     */
    @ModelAttribute
    protected void initModel(
            @NotNull @NotEmpty @RequestParam(
                    value = Constants.KEY_USER_ACCOUNT
            ) String account,
            @NotNull @NotEmpty @RequestParam(
                    value = Constants.KEY_USER_PASSWORD
            ) String pwd, Model model
    )
    {
        model.addAttribute(Constants.KEY_USER_ACCOUNT, account);
        model.addAttribute(Constants.KEY_USER_PASSWORD, pwd);
    }

    /**
     * 用户举报一篇文章。
     * 
     * @param account 账号
     * @param pwd 密码
     * @param cause 举报原因
     * @param articleId 被举报文章ID
     * @param model 主要用于向Model添加属性
     * @return JSON字符串，用户账号为键对应值表示删除成功与否。
     */
    @RequestMapping("report")
    @ResponseBody
    protected String report(
            @ModelAttribute(value = Constants.KEY_USER_ACCOUNT) String account,
            @ModelAttribute(value = Constants.KEY_USER_PASSWORD) String pwd,
            @RequestAttribute(value = Constants.KEY_REPORT_CAUSE) Byte cause,
            @RequestAttribute(
                    value = Constants.KEY_ARTICLE_ID
            ) BigInteger articleId, Model model
    )
    {
        String ret = "";

        try
        {
            BigInteger accuserId = signInService.account2Id(account);

            if (accuserId != null && accuserId.compareTo(BigInteger.ZERO) > 0)
            {
                BigInteger id = reportService.report(
                        accuserId, articleId,
                        ReportCause.praseReportCause(cause)
                );
                JSONObject json = new JSONObject();
                json.put(account, id);
                System.out.print(json);
                ret = json.toJSONString();
            } // 结束：if (accuserId != null && accuserId.compareTo(BigInteger...
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ret;
    }
}
