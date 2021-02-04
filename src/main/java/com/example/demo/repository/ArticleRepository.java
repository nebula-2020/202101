/*
 * 文件名：ArticleRepository.java
 * 描述：单表存储库
 * 修改人：刘可
 * 修改时间：2021-02-04
 */
package com.example.demo.repository;

import java.math.BigInteger;

import com.example.demo.entity.*;

import org.springframework.data.jpa.repository.*;

import io.lettuce.core.dynamic.annotation.Param;

/**
 * 文章信息存储库。
 * 
 * @author 刘可
 * @see updateOne
 * @version 1.0.0.0
 * @since 2021-02-04
 */
public interface ArticleRepository extends JpaRepository<Article, BigInteger>,
        JpaSpecificationExecutor<Article>
{
    /**
     * 修改文章基本信息。
     * 
     * @param id 文章ID
     * @param title 新标题
     * @param type 新标签
     * @param draft 是否为草稿
     * @return 变更行数。
     */
    @Query(
            value = "UPDATE `article` SET "
                    + "`title`=:title,`draft`=:draft,`type`=:type WHERE id=:id",
            nativeQuery = true
    )
    int updateOne(
            @Param("id") BigInteger id, @Param("title") String title,
            @Param("type") Integer type, @Param("draft") boolean draft
    );

    /**
     * 修改文章基本信息。
     * 
     * @param id 文章ID
     * @param del 是否为草稿
     * @return 变更行数。
     */
    @Query(
            value = "UPDATE `article` SET `del`=:del WHERE id=:id",
            nativeQuery = true
    )
    int deleteOne(@Param("id") BigInteger id, @Param("del") boolean del);
}