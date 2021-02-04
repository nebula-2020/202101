/*
 * 文件名：ArticleInfoRepository.java
 * 描述：单表存储库
 * 修改人：刘可
 * 修改时间：2021-02-04
 */
package com.example.demo.repository;

import java.math.BigInteger;
import java.sql.Timestamp;

import com.example.demo.entity.*;

import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

import io.lettuce.core.dynamic.annotation.Param;

/**
 * 文章信息存储库。
 * 
 * @author 刘可
 * @see updateOne
 * @version 1.0.0.0
 * @since 2021-02-04
 */
public interface ArticleInfoRepository
        extends JpaRepository<ArticleInfo, BigInteger>,
        JpaSpecificationExecutor<ArticleInfo>
{

    /**
     * 修改文章信息。
     * 
     * @param id 文章ID
     * @param source 转载源
     * @param text 内容
     * @param now 修改时间
     * @return 变更行数。
     */
    @Transactional
    @Modifying
    @Query(
            value = "UPDATE `articleinf` SET "
                    + "`modifytime`=:now,`source`=:source,`text`=:text"
                    + " WHERE id=:id",
            nativeQuery = true
    )
    int updateOne(
            @Param("id") BigInteger id, @Param("source") String source,
            @Param("text") String text, @Param("now") Timestamp now
    );
}