/*
 * 文件名：UserInfoRepository.java 
 * 描述：单表存储库
 * 修改人：刘可
 * 修改时间：2021-02-02
 */
package com.example.demo.repository;

import java.math.BigInteger;

import com.example.demo.entity.UserInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

/**
 * 用户信息存储库。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @since 2021-02-01
 */
@Component
public interface UserInfoRepository extends JpaRepository<UserInfo, BigInteger>,
        JpaSpecificationExecutor<UserInfo>
{
}