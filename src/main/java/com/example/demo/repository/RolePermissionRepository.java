/*
 * 文件名：RolePermissionRepository.java
 * 描述：单表存储库
 * 修改人： 刘可
 * 修改时间：2021-03-02
 */

package com.example.demo.repository;

import org.springframework.data.jpa.repository.*;
import com.example.demo.entity.*;

/**
 * 权限存储库。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @since 2021-03-02
 */
public interface RolePermissionRepository
        extends JpaRepository<RolePermission, Long>,
        JpaSpecificationExecutor<RolePermission>
{

}
