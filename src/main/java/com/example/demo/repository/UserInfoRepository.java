/*
 * 文件名：UserInfoRepository.java 
 * 描述：单表存储库
 * 修改人：刘可
 * 修改时间：2021-02-20
 */
package com.example.demo.repository;

import java.math.BigInteger;

import com.example.demo.entity.UserInfo;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.*;

/**
 * 用户信息存储库。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @see updateInfo
 * @see updatePoint
 * @since 2021-02-20
 */
@Component
public interface UserInfoRepository extends JpaRepository<UserInfo, BigInteger>,
        JpaSpecificationExecutor<UserInfo>
{
    /**
     * 修改以用户基本信息。
     * 
     * @param id 用户ID
     * @param name 昵称
     * @param signature 个性签名
     * @param icon 头像src
     * @return 变更行数。
     */
    @Transactional
    @Modifying
    @Query(
            value = "UPDATE `userinfo` SET "
                    + "`name`=:name,`signature`=:signature,`icon`=:icon"
                    + " WHERE `id`=:id",
            nativeQuery = true
    )
    int updateInfo(
            @Param("id") BigInteger id, @Param("name") String name,
            @Param("signature") String signature, @Param("icon") String icon
    );

    /**
     * 修改用户积分。
     * 
     * @param id 用户ID
     * @param point 用户当前积分
     * @return 变更行数。
     */
    @Transactional
    @Modifying
    @Query(
            value = "UPDATE `userinfo` SET `point`=:point WHERE `id`=:id",
            nativeQuery = true
    )
    int updatePoint(@Param("id") BigInteger id, @Param("point") Integer point);
}