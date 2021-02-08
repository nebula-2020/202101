/*
 * 文件名：ArticleController.java
 * 描述：必要控制器
 * 修改人：刘可
 * 修改时间：2021-02-06
 */
package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.demo.tool.*;

import java.math.BigInteger;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.ArticleInfo;
import com.example.demo.service.*;

/**
 * 文章控制器。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @see updateArticle
 * @see deleteArticle
 * @since 2021-02-06
 */
@Controller
public class ArticleController
{
    @Autowired
    private ArticleService articleService;
    @Autowired
    private SignInService signInService;
    private final CommonTool tool = new CommonTool();
    /**
     * 用户手机号。
     */
    public final String KEY_USER = "phone";
    /**
     * 用户密码。
     */
    public final String KEY_PASSWORD = "pwd";
    /**
     * 文章ID。
     */
    public final String KEY_ARTICLE = "id";
    /**
     * 文章标题。
     */
    public final String KEY_TITLE = "title";
    /**
     * 内容。
     */
    public final String KEY_TEXT = "md";
    /**
     * 转载源。
     */
    public final String KEY_SOURCE = "source";
    /**
     * 是否为草稿。
     */
    public final String KEY_DRAFT = "draft";
    /**
     * IPv4。
     */
    public final String KEY_IPV4 = "ipv4[]";
    /**
     * IPv6。
     */
    public final String KEY_IPV6 = "ipv6[]";
    /**
     * 物理地址。
     */
    public final String KEY_MAC = "mac[]";

    /**
     * 提交文章。
     * 
     * @param idStr 文章ID
     * @param phone 作者手机号
     * @param pwd 作者密码
     * @param isDraft <code>true</code>表示提交草稿
     * @param source 转载源
     * @param title 文章标题
     * @param text 文章内容
     * @return JSON字符串，用户手机号为键对应值表示提交成功与否。
     */
    @RequestMapping("updateArticle")
    @ResponseBody
    protected String updateArticle(
            @RequestParam(
                    value = KEY_ARTICLE,
                    required = false
            ) String idStr, @RequestParam(value = KEY_USER) String phone,
            @RequestParam(value = KEY_PASSWORD) String pwd,
            @RequestParam(value = KEY_DRAFT) Boolean isDraft,
            @RequestParam(
                    value = KEY_SOURCE,
                    required = false
            ) String source, @RequestParam(value = KEY_TITLE) String title,
            @RequestParam(value = KEY_TEXT) String text
    )
    {
        String ret = "";

        if (!tool.containsNullOrEmpty(text, title))
        {
            BigInteger userId = signInService.verify(phone, pwd);

            if (userId != null && userId.compareTo(BigInteger.ZERO) > 0)
            {
                // 用户存在且匹配
                BigInteger articleId = null;

                if (!tool.isNullOrEmpty(idStr))
                {
                    articleId = new BigInteger(idStr);
                } // 结束：if (!tool.isNullOrEmpty(idStr))

                try
                {
                    boolean draft = isDraft == null ? true : isDraft;
                    boolean res = articleService.updateArticle(
                            userId, articleId, title, source, text, draft
                    );//
                    JSONObject json = new JSONObject();
                    json.put(phone, res);
                    System.out.println(json);// debug
                    ret = json.toJSONString();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                finally
                {
                }
            } // 结束：if (userId!=null&&userId.compareTo(BigInteger.ZERO)>0)
        } // 结束：if(!tool.containsNullOrEmpty(text, title))
        return ret;
    }

    /**
     * 删除文章。
     * 
     * @param idStr 文章ID
     * @param phone 作者手机号
     * @param pwd 作者密码
     * @return JSON字符串，用户手机号为键对应值表示删除成功与否。
     */
    @RequestMapping("deleteArticle")
    @ResponseBody
    protected String deleteArticle(
            @RequestParam(value = KEY_ARTICLE) String idStr,
            @RequestParam(value = KEY_USER) String phone,
            @RequestParam(value = KEY_PASSWORD) String pwd
    )
    {
        String ret = "";

        if (!tool.isNullOrEmpty(idStr))
        {
            BigInteger userId = signInService.verify(phone, pwd);

            if (userId != null && userId.compareTo(BigInteger.ZERO) > 0)
            {
                // 用户存在且匹配
                BigInteger articleId = null;

                if (!tool.isNullOrEmpty(idStr))
                {
                    articleId = new BigInteger(idStr);
                } // 结束：if (!tool.isNullOrEmpty(idStr))

                try
                {
                    boolean res =
                            articleService.deleteArticle(userId, articleId);
                    JSONObject json = new JSONObject();
                    json.put(phone, res);
                    System.out.println(json);// debug
                    ret = json.toJSONString();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                finally
                {
                }
            } // 结束：if(userId!=null&&userId.compareTo(BigInteger.ZERO)>0)
        } // 结束：if(!tool.isNullOrEmpty(idStr))
        return ret;//
    }

    /**
     * 删除文章。
     * 
     * @param idStr 文章ID
     * @param phone 作者手机号
     * @param pwd 作者密码
     * @return JSON字符串，用户手机号为键对应值表示删除成功与否。
     */
    @RequestMapping("article")
    @ResponseBody
    protected String visitArticle(
            @RequestParam(value = KEY_ARTICLE) String idStr,
            @RequestParam(value = KEY_USER) String phone,
            @RequestParam(value = KEY_IPV4) List<Byte> ipv4,
            @RequestParam(value = KEY_IPV6) List<Byte> ipv6,
            @RequestParam(value = KEY_MAC) List<Byte> mac
    )
    {
        String ret = "";

        if (!tool.isNullOrEmpty(idStr))
        {
            BigInteger userId = signInService.phone2Id(phone);

            // 用户存在且匹配
            BigInteger articleId = null;

            if (!tool.isNullOrEmpty(idStr))
            {
                articleId = new BigInteger(idStr);
            } // 结束：if (!tool.isNullOrEmpty(idStr))

            try
            {
                byte[] ipv6Array = tool.toByteArray(ipv6);
                byte[] macArray = tool.toByteArray(mac);
                byte[] ipv4Array = tool.toByteArray(ipv4);
                ArticleInfo res = articleService.visitArticle(
                        articleId, userId, tool.bytes2Ipv4(ipv4Array),
                        ipv6Array, macArray
                );

                if (res != null)
                {
                    JSONObject json = new JSONObject();
                    json.put(KEY_TEXT, res.getText());
                    json.put(KEY_SOURCE, res.getSource());
                    json.put(KEY_ARTICLE, res.getId());
                    ret = json.toJSONString();
                }//结束： if (res != null)
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
            }
        } // 结束：if(!tool.isNullOrEmpty(idStr))
        return ret;//
    }
}
