/*
 * 文件名：VisitRepository.java
 * 描述：单表存储库
 * 修改人：刘可
 * 修改时间：2021-02-08
 */
package com.example.demo.repository;

import org.springframework.stereotype.Component;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.demo.entity.Visit;
import com.example.demo.entity.pk.ArticleTimeKey;

/**
 * 文章访问记录存储库。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @since 2021-02-08
 */
@Component
public interface VisitRepository extends JpaRepository<Visit, ArticleTimeKey>,
        JpaSpecificationExecutor<Visit>
{
}
