/*
 * 文件名：SignInInfoRepository.java
 * 描述：单表存储库
 * 修改人：刘可
 * 修改时间：2021-02-05
 */
package com.example.demo.repository;

import com.example.demo.entity.*;
import com.example.demo.entity.pk.SignInInfoKey;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Component;

/**
 * 登陆记录存储库。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @since 2021-02-02
 */
@Component
public interface SignInInfoRepository
        extends JpaRepository<SignInInfo, SignInInfoKey>,
        JpaSpecificationExecutor<SignInInfo>
{
}
