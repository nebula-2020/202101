/*
 * 文件名：VisitorController.java
 * 描述：必要控制器
 * 修改人：刘可
 * 修改时间：2021-02-27
 */
package com.example.demo.controller;

import java.math.BigInteger;
import java.util.List;

import javax.validation.constraints.*;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.constant.Constants;
import com.example.demo.enumation.*;
import com.example.demo.service.*;
import com.example.demo.vo.ArticleInfoVO;
import com.example.demo.vo.VisitVO;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 文章访问控制器。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @see initModel
 * @see visitArticle
 * @since 2021-02-27
 */
@Controller
public class VisitorController extends CommonController
{
    @Autowired
    private VisitService articleService;
    @Autowired
    private SignInService signInService;

    /**
     * 初始化Model。
     * 
     * @param articleId
     * @param phone 手机号
     * @param pwd 密码
     * @param ipv4 IPv4地址，大端模式
     * @param ipv6 IPv6地址，大端模式
     * @param mac 物理地址，大端模式
     * @param model 主要用于向Model添加属性
     */
    @ModelAttribute
    protected void initModel(
            @NotNull @Min(1) @RequestParam(
                    value = Constants.KEY_ARTICLE_ID
            ) BigInteger articleId,
            @NotNull @NotEmpty @RequestParam(
                    value = Constants.KEY_USER_ACCOUNT,
                    required = false
            ) String account,
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
            ) List<Byte> mac, Model model
    )
    {
        model.addAttribute(Constants.KEY_USER_ACCOUNT, account);
        model.addAttribute(Constants.KEY_USER_PASSWORD, pwd);
        model.addAttribute(Constants.KEY_ARTICLE_ID, articleId);
        VisitVO visit =
                new VisitVO(ipv4, ipv6, mac, null, SignInMethod.UNKNOWN);
        model.addAttribute(Constants.SESSION_LOCATION, visit);
    }

    /**
     * 访问文章。
     * 
     * @param articleId 文章ID
     * @param phone 访客手机号
     * @param visit 访问信息
     * @return JSON字符串，用户手机号为键对应值表示操作成功与否。
     */
    @RequestMapping("article")
    @ResponseBody
    protected String visitArticle(
            @ModelAttribute(
                    value = Constants.KEY_ARTICLE_ID
            ) BigInteger articleId,
            @ModelAttribute(value = Constants.KEY_USER_PHONE) String phone,
            @ModelAttribute(value = Constants.SESSION_LOCATION) VisitVO visit,
            Model model
    )
    {
        String ret = "";
        BigInteger userId = signInService.phone2Id(phone);

        try
        {
            ArticleInfoVO res =
                    articleService.visitArticle(articleId, userId, visit);

            if (res != null)
            {
                JSONObject json = new JSONObject();
                json.put(Constants.KEY_ARTICLE_TEXT, res.getText());
                json.put(Constants.KEY_ARTICLE_SOURCE, res.getSource());
                json.put(Constants.KEY_ARTICLE_ID, res.getId());
                json.put(Constants.KEY_ARTICLE_DRAFT, res.isDraft());
                json.put(Constants.KEY_ARTICLE_TITLE, res.getTitle());
                json.put(Constants.KEY_ARTICLE_CREATETIME, res.getCreateTime());
                json.put(Constants.KEY_ARTICLE_MODIFYTIME, res.getModifyTime());
                json.put(Constants.KEY_USER_ACCOUNT, res.getAuthorAccount());
                ret = json.toJSONString();
            } // 结束： if (res != null)
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ret;//
    }
}
