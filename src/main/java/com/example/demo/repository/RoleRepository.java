/*
 * 文件名：RoleRepository.java
 * 描述：单表存储库
 * 修改人： 刘可
 * 修改时间：2021-03-02
 */
package com.example.demo.repository;

import org.springframework.data.jpa.repository.*;

import com.example.demo.entity.*;

/**
 * 角色存储库。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @since 2021-03-02
 */
public interface RoleRepository
        extends JpaRepository<Role, Short>, JpaSpecificationExecutor<Role>
{

}
