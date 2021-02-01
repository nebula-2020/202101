/*
 * 文件名：UserStatusRepository.java
 * 描述：单表存储库
 * 修改人：刘可
 * 修改时间：2021-02-02
 */
package com.example.demo.repository;

import java.math.BigInteger;

import com.example.demo.entity.UserStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.data.jdbc.repository.query.Modifying;

/**
 * 用户信息存储库。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @see updateBan
 * @see updateLock
 * @since 2021-02-01
 */
@Component
public interface UserStatusRepository
        extends JpaRepository<UserStatus, BigInteger>,
        JpaSpecificationExecutor<UserStatus>
{
    @Modifying
    @Query(
            value = "update `userstatus` set `ban` = :ban where `id` = :id",
            nativeQuery = true
    )
    int updateBan(@Param("id") BigInteger id, @Param("ban") Byte ban);

    @Modifying
    @Query(
            value = "update `userstatus` set `lock` = :lock where `id` = :id",
            nativeQuery = true
    )
    int updateLock(@Param("id") BigInteger id, @Param("lock") Byte lock);
}
