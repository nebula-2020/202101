/*
 * 文件名：VisitService.java
 * 描述：项目主要服务。
 * 修改人：刘可
 * 修改时间：2021-02-17
 */
package com.example.demo.service;

import java.math.BigInteger;
import java.sql.Timestamp;

import com.example.demo.entity.*;
import com.example.demo.entity.pk.ArticleTimeKey;
import com.example.demo.repository.*;
import com.example.demo.vo.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 文章访问服务。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @see tryGet
 * @see getArticle
 * @see addVisitInfo
 * @see visitArticle
 * @see getArticleInfo
 * @since 2021-02-17
 */
@Service("visitService")
public class VisitService extends ComService
{
    @Autowired
    ArticleInfoRepository infoRepo;
    @Autowired
    ArticleRepository articleRepo;
    @Autowired
    UserBaseInfoRepository userRepo;
    @Autowired
    UserStatusRepository userStatusRepo;
    @Autowired
    VisitRepository visitRepo;
    @Autowired
    VisitInfoRepository visitInfoRepo;

    /**
     * 判断文章是否允许获取。
     * <p>
     * 作者还可以获取到自己的草稿。
     * 
     * @param authorId 作者ID
     * @param articleId 文章ID，不得为<code>null</code>
     * @return 若文章不能获取则返回<code>null</code>。
     */
    public Article tryGet(BigInteger authorId, BigInteger articleId)
    {
        Article article = articleRepo.getOne(articleId);

        if (article.getDel())
        {
            return null;
        } // 结束：if(article.getDel())

        if (article.getDraft()
                && !tool.isBigIntSame(authorId, article.getAuthorId()))
        {
            return null;
        } // 结束：if(article.getDraft()&&!tool.isBigIntSame(authorId,...
        UserStatus status = userStatusRepo.getOne(article.getAuthorId());

        if (status.getBan())
        {
            return null;
        } // 结束：if (status.getBan())
        return article;
    }

    /**
     * 获取文章。
     * 
     * @param authorId 作者ID
     * @param articleId 文章ID，不得为<code>null</code>
     * @return 文章速览实体，查询失败则返回<code>null</code>。
     */
    public ArticlePeekVO getArticle(BigInteger articleId, BigInteger authorId)
    {
        Article article = tryGet(authorId, articleId);

        if (article == null)
        {
            return null;
        } // 结束：if (article == null)
        UserBaseInfo authorInfo = userRepo.getOne(article.getAuthorId());
        String account = tool.supportTo(
                authorInfo.getAccount(), authorInfo.getId().toString()
        );

        ArticlePeekVO ret = new ArticlePeekVO(
                article.getId(), //
                article.getTitle(), //
                article.getAuthorId(), //
                account, //
                article.getCreateTime(), //
                article.getDraft()
        );
        return ret;
    }

    /**
     * 添加文章访问信息。
     * @param articleId 文章ID，不得为<code>null</code>
     * @param userId 访客ID
     * @param vo 访问数据
     */
    protected void
            addVisitInfo(BigInteger articleId, BigInteger userId, VisitVO vo)
    {

        try
        {
            ArticleTimeKey primaryKey = new ArticleTimeKey();
            primaryKey.setArticleId(articleId);
            primaryKey.setDate(new Timestamp(System.currentTimeMillis()));

            // 设置访问记录
            Visit visit = new Visit();
            visit.setPrimaryKey(primaryKey);
            visit.setVisitorId(userId);
            visitRepo.save(visit);

            if (vo != null)
            {
                // 设置访问信息
                VisitInfo info = new VisitInfo();
                info.setIpv4(vo.getIpv4());
                info.setIpv6(tool.toByteArray(vo.getIpv6()));
                info.setMac(tool.toByteArray(vo.getMac()));
                info.setPrimaryKey(primaryKey);
                visitInfoRepo.save(info);
            } // 结束：if (vo != null)

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 获取文章内容并记录访问信息。
     * 
     * @param authorId 作者ID
     * @param articleId 文章ID，不得为<code>null</code>
     * @return 查询失败则返回<code>null</code>。
     */
    public ArticleInfoVO visitArticle(
            BigInteger articleId, BigInteger authorId, VisitVO visit
    )
    {
        ArticleInfoVO ret = getArticleInfo(articleId, authorId, visit);

        if (ret != null)
        {
            addVisitInfo(articleId, authorId, visit);
        } // 结束：if (ret != null)
        return ret;
    }

    /**
     * 获取文章内容。
     * 
     * @param authorId 作者ID
     * @param articleId 文章ID，不得为<code>null</code>
     * @return 查询失败则返回<code>null</code>。
     */
    public ArticleInfoVO getArticleInfo(
            BigInteger articleId, BigInteger authorId, VisitVO visit
    )
    {
        Article article = tryGet(authorId, articleId);

        if (article == null)
        {
            return null;
        } // 结束：if (article == null)
        ArticleInfo info = infoRepo.getOne(article.getId());

        if (info.getText() == null)
        {
            return null;
        } // 结束：if (info.getText() == null)
        UserBaseInfo authorInfo = userRepo.getOne(article.getAuthorId());
        String account = tool.supportTo(
                authorInfo.getAccount(), authorInfo.getId().toString()
        );

        ArticleInfoVO ret = new ArticleInfoVO(
                article.getId(), article.getTitle(), info.getText(),
                info.getSource(), account, article.getCreateTime(),
                info.getModifyTime(), article.getDraft()
        );
        return ret;
    }
}
