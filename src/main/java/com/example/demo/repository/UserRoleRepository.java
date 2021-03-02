
/*
 * 文件名： UserRoleRepository.java
 * 描述：单表存储库
 * 修改人： 刘可
 * 修改时间：2021-03-02
 */
package com.example.demo.repository;

import java.math.BigInteger;
import org.springframework.data.jpa.repository.*;
import com.example.demo.entity.*;

/**
 * 用户和角色对应表存储库。
 * <p>
 * 表中不存在的用户可以认为是用普通用户权限。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @since 2021-03-02
 */
public interface UserRoleRepository extends JpaRepository<UserRole, BigInteger>,
        JpaSpecificationExecutor<UserRole>
{

}