/*
 * 文件名：ArticleInteractionController.java
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

/**
 * 文章互动控制器。
 * <p>
 * 处理文章点赞评论和收藏请求。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @see initModel
 * @see like
 * @see comment
 * @see deleteComment
 * @see favorite
 * @see deleteFavorite
 * @since 2021-02-19
 */
@Controller
public class ArticleInteractionController extends CommonController
{

    @Autowired
    private ArticleInteractionService articleService;
    @Autowired
    private SignInService signInService;

    /**
     * 初始化Model。
     * 
     * @param account 账号
     * @param pwd 密码
     * @param articleId 文章ID
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
            @NotNull @Min(1) @RequestParam(
                    value = Constants.KEY_ARTICLE_ID
            ) BigInteger articleId, Model model
    )
    {
        model.addAttribute(Constants.KEY_USER_ACCOUNT, account);
        model.addAttribute(Constants.KEY_USER_PASSWORD, pwd);
        model.addAttribute(Constants.KEY_ARTICLE_ID, articleId);
    }

    /**
     * 在文章点赞。
     * 
     * @param account 账号
     * @param pwd 密码
     * @param id 文章ID
     * @param model 主要用于向Model添加属性
     * @return JSON字符串，用户账号为键对应值表示操作成功与否。
     */
    @RequestMapping("article/like")
    @ResponseBody
    protected String like(
            @ModelAttribute(Constants.KEY_USER_ACCOUNT) String account,
            @ModelAttribute(Constants.KEY_USER_PASSWORD) String pwd,
            @ModelAttribute(Constants.KEY_ARTICLE_ID) BigInteger id, Model model
    )
    {
        String ret = "";

        try
        {

            BigInteger userId = signInService.verify(account, pwd);

            if (userId != null)
            {
                boolean res = articleService.like(userId, id);

                JSONObject json = new JSONObject();
                json.put(account, res);
                System.out.print(json);
                ret = json.toJSONString();
            } // 结束：if(userId!=null)
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 评论文章。
     * 
     * @param account 账号
     * @param pwd 密码
     * @param id 文章ID
     * @param text 评论内容
     * @param model 主要用于向Model添加属性
     * @return JSON字符串，用户账号为键对应值表示操作成功与否。
     */
    @RequestMapping("article/comment")
    @ResponseBody
    protected String comment(
            @ModelAttribute(Constants.KEY_USER_ACCOUNT) String account,
            @ModelAttribute(Constants.KEY_USER_PASSWORD) String pwd,
            @ModelAttribute(Constants.KEY_ARTICLE_ID) BigInteger id,
            @NotNull @NotEmpty @RequestParam(
                    value = Constants.KEY_COMMENT_TEXT,
                    required = false
            ) String text, Model model
    )
    {
        String ret = "";

        try
        {

            BigInteger userId = signInService.verify(account, pwd);

            if (userId != null)
            {
                boolean res = articleService.comment(userId, id, text);

                JSONObject json = new JSONObject();
                json.put(account, res);
                System.out.print(json);
                ret = json.toJSONString();
            } // 结束：if(userId!=null)
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 在文章点删除评论。
     * 
     * @param account 账号
     * @param pwd 密码
     * @param id 文章ID
     * @param model 主要用于向Model添加属性
     * @return JSON字符串，用户账号为键对应值表示操作成功与否。
     */
    @RequestMapping("article/deleteComment")
    @ResponseBody
    protected String deleteComment(
            @ModelAttribute(Constants.KEY_USER_ACCOUNT) String account,
            @ModelAttribute(Constants.KEY_USER_PASSWORD) String pwd,
            @ModelAttribute(Constants.KEY_ARTICLE_ID) BigInteger id, Model model
    )
    {
        String ret = "";

        try
        {

            BigInteger userId = signInService.verify(account, pwd);

            if (userId != null)
            {
                boolean res = articleService.deleteComment(userId, id);

                JSONObject json = new JSONObject();
                json.put(account, res);
                System.out.print(json);
                ret = json.toJSONString();
            } // 结束：if(userId!=null)
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ret;
    }


    /**
     * 收藏文章。
     * 
     * @param account 账号
     * @param pwd 密码
     * @param id 文章ID
     * @param model 主要用于向Model添加属性
     * @return JSON字符串，用户账号为键对应值表示操作成功与否。
     */
    @RequestMapping("user/favorite")
    @ResponseBody
    protected String favorite(
            @ModelAttribute(Constants.KEY_USER_ACCOUNT) String account,
            @ModelAttribute(Constants.KEY_USER_PASSWORD) String pwd,
            @ModelAttribute(Constants.KEY_ARTICLE_ID) BigInteger id, Model model
    )
    {
        String ret = "";

        try
        {

            BigInteger userId = signInService.verify(account, pwd);

            if (userId != null)
            {
                boolean res = articleService.addFavorite(userId, id);

                JSONObject json = new JSONObject();
                json.put(account, res);
                System.out.print(json);
                ret = json.toJSONString();
            } // 结束：if(userId!=null)
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ret;
    }


    /**
     * 取消收藏文章。
     * 
     * @param account 账号
     * @param pwd 密码
     * @param id 文章ID
     * @param model 主要用于向Model添加属性
     * @return JSON字符串，用户账号为键对应值表示操作成功与否。
     */
    @RequestMapping("user/deleteFavorite")
    @ResponseBody
    protected String deleteFavorite(
            @ModelAttribute(Constants.KEY_USER_ACCOUNT) String account,
            @ModelAttribute(Constants.KEY_USER_PASSWORD) String pwd,
            @ModelAttribute(Constants.KEY_ARTICLE_ID) BigInteger id, Model model
    )
    {
        String ret = "";

        try
        {

            BigInteger userId = signInService.verify(account, pwd);

            if (userId != null)
            {
                boolean res = articleService.deleteFavorite(userId, id);

                JSONObject json = new JSONObject();
                json.put(account, res);
                System.out.print(json);
                ret = json.toJSONString();
            } // 结束：if(userId!=null)
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ret;
    }
}
