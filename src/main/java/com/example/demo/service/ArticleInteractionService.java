/*
 * 文件名：ArticleInteractionService.java
 * 描述：项目主要服务。
 * 修改人： 刘可
 * 修改时间：2021-02-19
 */
package com.example.demo.service;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Optional;

import com.example.demo.entity.*;
import com.example.demo.entity.pk.LikeKey;
import com.example.demo.entity.pk.UserArticleKey;
import com.example.demo.repository.*;
import com.example.demo.util.BigIntUtils;
import com.example.demo.util.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 文章提交修改和删除服务。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @see comment
 * @see deleteComment
 * @see like
 * @see addFavorite
 * @see deleteFavorite
 * @since 2021-02-19
 */
@Service("articleInteractionService")
public class ArticleInteractionService extends ComService
{
    @Autowired
    ArticleRepository articleRepo;

    @Autowired
    CommentRepository commentRepo;

    @Autowired
    FavoriteRepository favoriteRepo;

    @Autowired
    LikeRepository likeRepo;

    /**
     * 用户对指定文章评论。
     * 
     * @param userId 用户ID
     * @param articleId 文章ID
     * @param text 评论文本
     * @return 操作成功与否。
     */
    public boolean comment(BigInteger userId, BigInteger articleId, String text)
    {

        if (userId == null || articleId == null
                || userId.compareTo(BigInteger.ZERO) <= 0
                || articleId.compareTo(BigInteger.ZERO) <= 0
                || !StringUtils.hasText(text))
        {
            return false;
        } // 结束：if (userId == null || articleId == null...
        Article article = articleRepo.getOne(articleId);
        boolean ret = false;

        if (article != null && !(article.getDraft() || article.getDel()))
        {
            Comment entity = new Comment();
            entity.setUserId(userId);
            entity.setArticleId(articleId);
            entity.setText(text);
            entity = commentRepo.save(entity);
            ret = entity.getId() != null;
        } // 结束：if(article!=null&&!(article.getDraft()||article.getDel()))
        return ret;
    }

    /**
     * 删除评论。
     * <p>
     * 为评论增加删除标记。
     * 
     * @param userId 用户
     * @param commentId 评论
     * @return 操作成功与否。
     */
    public boolean deleteComment(BigInteger userId, BigInteger commentId)
    {

        if (userId == null || commentId == null
                || userId.compareTo(BigInteger.ZERO) <= 0
                || commentId.compareTo(BigInteger.ZERO) <= 0)
        {
            return false;
        } // 结束：if (userId == null || commentId == null...
        Optional<Comment> optional = commentRepo.findById(commentId);
        boolean ret = false;

        if (optional != null && optional.isPresent())
        {
            Comment entity = optional.get();

            if (BigIntUtils.isSame(entity.getId(), userId))
            {
                int res = commentRepo.updateOne(commentId, true);
                ret = res > 0;
            } // 结束：if (BigIntUtils.isSame(entity.getId(), userId))
        } // 结束：if (optional != null && optional.isPresent())
        return ret;
    }

    /**
     * 点赞。
     * 
     * @param userId 用户ID
     * @param articleId 文章ID
     * @return 操作成功与否。
     */
    public boolean like(BigInteger userId, BigInteger articleId)
    {
        boolean ret = false;

        try
        {
            LikeKey primaryKey = new LikeKey();
            primaryKey.setArticleId(articleId);
            primaryKey.setUserId(userId);
            primaryKey.setDate(LocalDate.now());
            Like like = new Like();
            like.setKey(primaryKey);
            likeRepo.save(like);
            ret = true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
        }
        return ret;
    }

    /**
     * 收藏。
     * <p>
     * 两个参数共同确定一条收藏。
     * 
     * @param userId 用户ID
     * @param articleId 文章ID
     * @return 操作成功与否。
     */
    public boolean addFavorite(BigInteger userId, BigInteger articleId)
    {
        boolean ret = false;

        try
        {
            Favorite favorite = new Favorite();
            UserArticleKey primaryKey = new UserArticleKey();
            primaryKey.setArticleId(articleId);
            primaryKey.setUserId(userId);
            favorite.setKey(primaryKey);
            favorite.setCreatetime(new Timestamp(System.currentTimeMillis()));
            favoriteRepo.save(favorite);
            ret = true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
        }
        return ret;
    }

    /**
     * 取消收藏。
     * <p>
     * 两个参数共同确定一条收藏。
     * 
     * @param userId 用户ID
     * @param articleId 文章ID
     * @return 操作成功与否。
     */
    public boolean deleteFavorite(BigInteger userId, BigInteger articleId)
    {
        boolean ret = false;

        try
        {
            Favorite favorite = new Favorite();
            UserArticleKey primaryKey = new UserArticleKey();
            primaryKey.setArticleId(articleId);
            primaryKey.setUserId(userId);
            favorite.setKey(primaryKey);
            favorite.setCreatetime(new Timestamp(System.currentTimeMillis()));
            favoriteRepo.delete(favorite);
            ret = true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
        }
        return ret;
    }
}
