/*
 * 文件名：CommentRepository.java
 * 描述：单表存储库
 * 修改人：刘可
 * 修改时间：2021-02-09
 */
package com.example.demo.repository;

import java.math.BigInteger;

import com.example.demo.entity.*;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * 文章评论存储库。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @see updateOne
 * @since 2021-02-09
 */
public interface CommentRepository extends JpaRepository<Comment, BigInteger>,
        JpaSpecificationExecutor<Comment>
{
    /**
    * 设置一条评论的可见性。
    * 
    * @param id 文章ID
    * @param title 新标题
    * @param type 新标签
    * @param draft 是否为草稿
    * @return 变更行数。
    */
   @Transactional
   @Modifying
   @Query(
           value = "UPDATE `comment` SET `del`=:del WHERE id=:id",
           nativeQuery = true
   )
   int updateOne(
           @Param("id") BigInteger id, @Param("del") boolean delete
   );
}
