/*
 * 文件名：ArticleEditService.java
 * 描述：项目主要服务。
 * 修改人： 刘可
 * 修改时间：2021-02-17
 */
package com.example.demo.service;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import com.example.demo.vo.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户文章创作服务。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @see verify
 * @see createArticle
 * @see modifyArticle
 * @see updateArticle
 * @see deleteArticle
 * @see banArticle
 * @see calcArticleTypeVal
 * @since 2021-02-17
 */
@Service("articleService")
public class ArticleEditService extends ComService
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
     * @param authorId 作者ID，不得为<code>null</code>
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
                } // 结束：if (found != null && found.isPresent())
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
     * 新建文章。
     * 
     * @param authorId 作者ID，不得为<code>null</code>
     * @param vo 描述新的文章属性，不得为<code>null</code>
     * @return 描述操作成功与否。
     */
    protected boolean createArticle(BigInteger authorId, ArticleVO vo)
    {
        boolean ret = false;

        try
        {
            Article article = new Article();
            article.setAuthorId(authorId);
            article.setDraft(vo.isDraft());
            article.setTitle(vo.getTitle());
            article.setType(calcArticleTypeVal(vo.getTitle(), vo.getText()));
            article = articleRepo.save(article);// 插入并获取ID

            ArticleInfo info = new ArticleInfo();
            info.setId(article.getId());
            info.setSource(vo.getSource());
            info.setText(vo.getText());
            info.setModifyTime(new Timestamp(System.currentTimeMillis()));
            infoRepo.save(info);
            ret = true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 更新文章。
     * <p>
     * 验证用户是文章作者后更新文章。
     * 
     * @param authorId 作者ID，不得为<code>null</code>
     * @param id 文章ID
     * @param vo 描述新的文章属性，不得为<code>null</code>
     * @return 描述操作成功与否。
     */
    protected boolean
            modifyArticle(BigInteger authorId, BigInteger id, ArticleVO vo)
    {
        boolean ret = false;

        try
        {
            Article article = verify(authorId, id);

            if (article != null && article.getId() != null)
            {
                boolean draft = vo.isDraft();

                if (!article.getDraft().booleanValue())
                {
                    // 如果文章已经设置了草稿标志，则只能从草稿转为公开而不能从公开转为草稿
                    draft = false;
                } // 结束：if(!article.getDraft().booleanValue())

                Timestamp now = new Timestamp(System.currentTimeMillis());
                int type = calcArticleTypeVal(vo.getTitle(), vo.getText());

                articleRepo.updateOne(id, vo.getTitle(), type, draft);
                infoRepo.updateOne(id, vo.getSource(), vo.getText(), now);
                ret = true;
            } // 结束：if (article!=null&&article.getId() != null)
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 提交文章修改。
     * <p>
     * 若文章ID不为<code>null</code>则修改内容，否则新建文章。
     * 
     * @param authorId 作者ID，不得为<code>null</code>
     * @param id 文章ID
     * @param vo 描述新的文章属性，不得为<code>null</code>
     * @return 描述操作成功与否。
     */
    public boolean
            updateArticle(BigInteger authorId, BigInteger id, ArticleVO vo)

    {
        boolean ret = false;

        if (id == null)
        {
            ret = createArticle(authorId, vo);
        }
        else
        {
            ret = modifyArticle(authorId, id, vo);
        } // 结束：if (id == null)
        return ret;
    }

    /**
     * 删除文章。
     * <p>
     * 验证用户是文章作者后删除文章。
     * 
     * @param authorId 作者ID，不得为<code>null</code>
     * @param id 文章ID，不得为<code>null</code>
     * @return 描述操作成功与否。
     */
    public boolean deleteArticle(BigInteger authorId, BigInteger id)
    {
        boolean ret = false;

        try
        {
            Article article = verify(authorId, id);

            if (article.getId() != null)
            {
                ret = banArticle(authorId, id);
            } // 结束：if (article.getId() != null)
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 删除文章。
     * <p>
     * 直接删除文章。
     * 
     * @param authorId 作者ID，不得为<code>null</code>
     * @param id 文章ID，不得为<code>null</code>
     * @return 描述操作成功与否。
     */
    public boolean banArticle(BigInteger authorId, BigInteger id)
    {
        boolean ret = false;

        try
        {
            articleRepo.deleteOne(id, true);
            ret = true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 计算文章特征标签。
     * 
     * @param title 标题
     * @param text 文章内容
     * @return 文章标签值。
     */
    public int calcArticleTypeVal(String title, String text)
    {
        return 0;
    }
}
