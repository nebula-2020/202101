/*
 * 文件名：ArticleController.java
 * 描述：必要控制器
 * 修改人：刘可
 * 修改时间：2021-02-21
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
 * @since 2021-02-19
 */
@Controller
public class ArticleController extends CommonController
{
    @Autowired
    private ArticleEditService articleService;
    @Autowired
    private SignInService signInService;

    /**
     * 初始化Model。
     * 
     * @param phone 手机号
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
     * @param phone 作者手机号
     * @param pwd 作者密码
     * @param articleId 文章ID
     * @param article 文章
     * @param model 主要用于向Model添加属性
     * @return JSON字符串，用户账号为键对应值表示删除成功与否。
     */
    @RequestMapping("article/updateArticle")
    @ResponseBody
    protected String updateArticle(
            @ModelAttribute(value = Constants.KEY_USER_ACCOUNT) String account,
            @ModelAttribute(value = Constants.KEY_USER_PASSWORD) String pwd,
            @ModelAttribute(
                    value = Constants.KEY_ARTICLE_ID
            ) BigInteger articleId,
            @NotNull @ModelAttribute(
                    value = Constants.SESSION_ARTICLE
            ) ArticleVO article, Model model
    )
    {
        String ret = "";

        BigInteger userId = signInService.verify(account, pwd);

        if (userId != null && userId.compareTo(BigInteger.ZERO) > 0)
        {

            try
            {
                boolean res = articleService
                        .updateArticle(userId, articleId, article);//
                JSONObject json = new JSONObject();
                json.put(account, res);
                System.out.println(json);// debug
                ret = json.toJSONString();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        } // 结束：if (userId!=null&&userId.compareTo(BigInteger.ZERO)>0)
        return ret;
    }

    /**
     * 删除文章。
     *
     * @param articleId 文章ID
     * @param phone 作者手机号
     * @param pwd 作者密码
     * @param model 主要用于向Model添加属性
     * @return JSON字符串，用户账号为键对应值表示删除成功与否。
     */
    @RequestMapping("article/deleteArticle")
    @ResponseBody
    protected String deleteArticle(
            @ModelAttribute(value = Constants.KEY_USER_ACCOUNT) String account,
            @ModelAttribute(value = Constants.KEY_USER_PASSWORD) String pwd,
            @NotNull @ModelAttribute(
                    value = Constants.KEY_ARTICLE_ID
            ) BigInteger articleId, Model model
    )
    {
        String ret = "";

        BigInteger userId = signInService.verify(account, pwd);

        if (userId != null && userId.compareTo(BigInteger.ZERO) > 0)
        {

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
        } // 结束：if(userId!=null&&userId.compareTo(BigInteger.ZERO)>0)
        return ret;//
    }
}
