/*
 * 文件名：UserBaseInfoRepository.java
 * 描述：单表存储库
 * 修改人：刘可
 * 修改时间：2021-02-05
 */
package com.example.demo.repository;

import java.math.BigInteger;

import com.example.demo.entity.UserBaseInfo;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户信息存储库。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @see findByPhone
 * @see findByAccount
 * @see updateAccountById
 * @see updatePhoneAndPasswordById
 * @since 2021-02-01
 */
@Component
public interface UserBaseInfoRepository
        extends JpaRepository<UserBaseInfo, BigInteger>,
        JpaSpecificationExecutor<UserBaseInfo>
{

    @Query(
            value = "select * from `user` where phone=:phone",
            nativeQuery = true
    )
    UserBaseInfo findByPhone(@Param("phone") String phone);

    @Query(
            value = "select * from `user` where personalid=:account",
            nativeQuery = true
    )
    UserBaseInfo findByAccount(@Param("account") String account);

    @Transactional
    @Modifying
    @Query(
            value = "UPDATE `user` SET `personalid`=:account WHERE `id`=:id",
            nativeQuery = true
    )
    int updateAccountById(
            @Param("id") BigInteger id, @Param("account") String account
    );

    @Transactional
    @Modifying
    @Query(
            value = "UPDATE `user` SET `phone`=:phone,`password`=:pwd WHERE `id`=:id",
            nativeQuery = true
    )
    int updatePhoneAndPasswordById(
            @Param("id") BigInteger id, @Param("phone") String phone,
            @Param("pwd") String password
    );

}
