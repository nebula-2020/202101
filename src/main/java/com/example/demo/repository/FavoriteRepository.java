/*
 * 文件名：FavoriteRepository.java
 * 描述：单表存储库
 * 修改人：刘可
 * 修改时间：2021-02-09
 */
package com.example.demo.repository;

import com.example.demo.entity.*;
import com.example.demo.entity.pk.UserArticleKey;

import org.springframework.data.jpa.repository.*;

/**
 * 收藏夹存储库。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @since 2021-02-09
 */
public interface FavoriteRepository
        extends JpaRepository<Favorite, UserArticleKey>,
        JpaSpecificationExecutor<Favorite>
{

}
