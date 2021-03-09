/*
 * 文件名：ArticleController.java
 * 描述：必要控制器
 * 修改人：刘可
 * 修改时间：2021-03-09
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
import com.example.demo.vo.ArticleVO;
import com.example.demo.constant.*;

/**
 * 文章控制器。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @see initModel
 * @see updateArticle
 * @see deleteArticle
 * @since 2021-03-09
 */
@Controller
@RequestMapping("/article")
public class ArticleController extends CommonController
{
    @Autowired
    private ArticleEditService articleService;

    /**
     * 初始化Model。
     * 
     * @param account 请求者用户账号，原本位于请求头，由过滤器设置
     * @param id 请求者用户账号对应用户ID，由过滤器设置
     * @param pwd 密码
     * @param articleId 文章ID
     * @param source 转载源
     * @param title 文章标题
     * @param text 文章内容
     * @param isDraft 是否为草稿
     * @param model 主要用于向Model添加属性
     */
    @ModelAttribute
    protected void initModel(
            @NotNull @NotEmpty @RequestParam(
                Constants.KEY_USER_ACCOUNT
            ) String account,
            @NotNull @NotEmpty @RequestParam(
                    value = Constants.KEY_USER_ID
            ) BigInteger id,
            @NotNull @NotEmpty @RequestParam(
                    value = Constants.KEY_USER_PASSWORD
            ) String pwd,
            @Min(1) @RequestParam(
                    value = Constants.KEY_ARTICLE_ID,
                    required = false
            ) BigInteger articleId,
            @RequestParam(
                    value = Constants.KEY_ARTICLE_SOURCE,
                    required = false
            ) String source,
            @NotEmpty @RequestParam(
                    value = Constants.KEY_ARTICLE_TITLE,
                    required = false
            ) String title,
            @NotEmpty @RequestParam(
                    value = Constants.KEY_ARTICLE_TEXT,
                    required = false
            ) String text,
            @RequestParam(
                    value = Constants.KEY_ARTICLE_DRAFT,
                    required = false
            ) Boolean isDraft, Model model
    )
    {
        model.addAttribute(Constants.KEY_USER_ACCOUNT, account);
        model.addAttribute(Constants.KEY_USER_ID, id);
        model.addAttribute(Constants.KEY_USER_PASSWORD, pwd);
        model.addAttribute(Constants.KEY_ARTICLE_ID, articleId);

        if (!tool.containsNullOrEmpty(title, text))
        {
            boolean draft = isDraft == null ? true : isDraft;
            ArticleVO articleVO = new ArticleVO(title, text, source, draft);
            model.addAttribute(Constants.SESSION_ARTICLE, articleVO);
        } // 结束：if (!tool.containsNullOrEmpty(title, text))
    }

    /**
     * 提交文章。
     * 
     * @param userId 作者ID
     * @param account 作者账号
     * @param articleId 文章ID
     * @param article 描述用户提交的文章数据
     * @param model 主要用于向Model添加属性
     * @return JSON字符串，用户账号为键对应值表示删除成功与否。
     */
    @RequestMapping("/updateArticle")
    @ResponseBody
    protected String updateArticle(
            @ModelAttribute(value = Constants.KEY_USER_ID) BigInteger userId,
            @ModelAttribute(value = Constants.KEY_USER_ACCOUNT) String account,
            @ModelAttribute(
                    value = Constants.KEY_ARTICLE_ID
            ) BigInteger articleId,
            @NotNull @ModelAttribute(
                    value = Constants.SESSION_ARTICLE
            ) ArticleVO article, Model model
    )
    {
        String ret = "";

        try
        {
            boolean res =
                    articleService.updateArticle(userId, articleId, article);//
            JSONObject json = new JSONObject();
            json.put(account, res);
            System.out.println(json);// debug
            ret = json.toJSONString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 删除文章。
     * 
     * @param userId 作者ID
     * @param account 作者账号
     * @param articleId 文章ID
     * @param model 主要用于向Model添加属性
     * @return JSON字符串，用户账号为键对应值表示删除成功与否。
     */

    @RequestMapping("/deleteArticle")
    @ResponseBody
    protected String deleteArticle(
            @ModelAttribute(value = Constants.KEY_USER_ID) BigInteger userId,
            @ModelAttribute(value = Constants.KEY_USER_ACCOUNT) String account,
            @NotNull @ModelAttribute(
                    value = Constants.KEY_ARTICLE_ID
            ) BigInteger articleId, Model model
    )
    {
        String ret = "";

        try
        {
            boolean res = articleService.deleteArticle(userId, articleId);
            JSONObject json = new JSONObject();
            json.put(account, res);
            System.out.println(json);// debug
            ret = json.toJSONString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ret;//
    }
}
