/*
 * 文件名：ArticleService.java
 * 描述：项目主要服务。
 * 修改人： 刘可
 * 修改时间：2021-02-06
 */

package com.example.demo.service;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.example.demo.entity.Article;
import com.example.demo.entity.ArticleInfo;
import com.example.demo.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 文章提交修改和删除服务。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @see verify
 * @see updateArticle
 * @see deleteArticle
 * @since 2021-02-06
 */

@Service("articleService")
public class ArticleService extends ComService
{
    @Autowired
    ArticleInfoRepository infoRepo;
    @Autowired
    ArticleRepository articleRepo;

    /**
     * 文章验证。
     * <p>
     * 判断文章的作者是否等于当前作者，如果等于则返回文章对象避免二次查找。
     * 如果作者不相等则返回<code>null</code>。
     * 如果文章不存在返回空对象。
     * 
     * @param authorId 作者ID
     * @param articleId 文章ID
     * @return 文章。
     */
    protected Article verify(BigInteger authorId, BigInteger articleId)
    {
        Article ret = null;

        try
        {

            if (articleId != null && articleId.compareTo(BigInteger.ZERO) > 0)
            {
                Optional<Article> found = articleRepo.findById(articleId);

                if (found != null && found.isPresent())
                {
                    ret = found.get();

                    if (!tool.isBigIntSame(ret.getAuthorId(), authorId))
                    {
                        // 不是此作者所写
                        ret = null;
                    } // 结束：if(!tool.isBigIntSame(ret.getAuthorId(),authorId))
                }
                else
                {
                    ret = new Article();
                }
            }
            else
            {
                ret = new Article();
            } // 结束：if(articleId!=null&&articleId.compareTo(BigInteger.ZERO)>0)
        }
        catch (NoSuchElementException e)
        {
            e.printStackTrace();
            ret = new Article();
        }
        return ret;
    }

    /**
     * 上传文章。
     * <p>
     * 若文章ID为空值或<code>null</code>则将在数据库中插入行，否则若用户ID与已存在文章的作者ID一致则修改行。
     * 转载源为空值或<code>null</code>代表此文章为原创。
     * 已经发布的文章不能存为草稿。
     * 
     * @param authorId 作者ID
     * @param articleId 文章ID
     * @param title 文章标题
     * @param source 文章转载源
     * @param text 内容
     * @param isDraft 是否存为草稿
     * @return 文章创建成功与否。
     */
    public boolean updateArticle(
            BigInteger authorId, BigInteger articleId, String title,
            String source, String text, boolean isDraft
    )
    {

        if (tool.containsNull(authorId, title, text))
        {
            return false;
        } // 结束：if (tool.containsNull(authorId, title, text))
        boolean ret = false;

        try
        {
            Article article = verify(authorId, articleId);

            if (article == null || article.getDel())
            {
                // 文章作者与提交更改的用户不同或已经删除，更改失败
                ret = false;
            }
            else if (article.getId() == null)
            {
                // 文章不存在，新增文章
                boolean draft = isDraft;

                if (article.getDraft() != null)
                {
                    // 如果文章已经设置了草稿标志，则只能从草稿转为公开而不能从公开转为草稿
                    draft = draft || article.getDraft();
                } // 结束：if (article.getDraft() != null)

                article.setAuthorId(authorId);
                article.setDraft(draft);
                article.setTitle(title);
                article.setType(0);

                ArticleInfo info = new ArticleInfo();
                info.setSource(source);
                info.setText(text);
                info.setModifyTime(new Timestamp(System.currentTimeMillis()));

                article = articleRepo.save(article);// 插入并获取ID
                info.setId(article.getId());
                infoRepo.save(info);
                ret = true;
            }
            else
            {
                // 文章存在，将更改
                int type = 0;
                Timestamp now = new Timestamp(System.currentTimeMillis());

                articleRepo.updateOne(articleId, title, type, isDraft);
                infoRepo.updateOne(articleId, source, text, now);
                ret = true;
            } // 结束： if (article == null||article.getDel())
        }
        catch (Exception e)
        {
            e.printStackTrace();
            ret = false;
        }
        return ret;
    }

    /**
     * 删除文章。
     * <p>
     * 给文章加上删除标记。
     * 
     * @param authorId 作者ID
     * @param articleId 文章ID
     * @return 删除成功与否。
     */
    public boolean deleteArticle(BigInteger authorId, BigInteger articleId)
    {

        if (tool.containsNull(authorId, articleId))
        {
            return false;
        } // 结束：if (tool.containsNull(authorId, title, text))
        boolean ret = false;

        try
        {
            Article article = verify(authorId, articleId);

            if (article != null && article.getId() != null)
            {
                articleRepo.deleteOne(articleId, true);
                ret = true;
            } // 结束：if (article != null && article.getId() != null)
        }
        catch (Exception e)
        {
            e.printStackTrace();
            ret = false;
        }
        return ret;
    }
}
