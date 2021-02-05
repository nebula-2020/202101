/*
 * 文件名：UserDetailsRepository.java
 * 描述：单表存储库
 * 修改人：刘可
 * 修改时间：2021-02-05
 */
package com.example.demo.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Component;

import com.example.demo.entity.UserDetails;

/**
 * 用户信息存储库。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @since 2021-02-01
 */
@Component
@RepositoryDefinition(
        domainClass = UserDetails.class,
        idClass = BigInteger.class
)
public interface UserDetailsRepository
        extends JpaRepository<UserDetails, BigInteger>,
        JpaSpecificationExecutor<UserDetails>
{
}
