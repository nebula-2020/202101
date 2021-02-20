/*
 * 文件名：BlackList.java
 * 描述：单表存储库
 * 修改人：刘可
 * 修改时间：2021-02-20
 */
package com.example.demo.repository;

import java.math.BigInteger;

import com.example.demo.entity.*;
import com.example.demo.entity.pk.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * 黑名单信息存储库。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @see deleteOne
 * @since 2021-02-20
 */

public interface BlackListRepository
        extends JpaRepository<BlackList, BlackListKey>,
        JpaSpecificationExecutor<BlackList>
{
    @Transactional
    @Modifying
    @Query(
            value = "UPDATE `blacklist` SET "
                    + "`personalid`=:account WHERE `id`=:id",
            nativeQuery = true
    )
    int deleteOne(@Param("id") BigInteger id, @Param("account") String account);
}
