/*
 * 文件名：SecurityService.java
 * 描述：项目主要服务。
 * 修改人：刘可
 * 修改时间：2021-03-02
 */

package com.example.demo.service;

import java.math.BigInteger;

import com.example.demo.entity.*;
import com.example.demo.repository.*;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 权限相关服务。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @see SecurityService
 * @since 2021-03-02
 */
public class SecurityService
{
    @Autowired
    private RoleRepository rRepo;
    @Autowired
    private UserRoleRepository urRepo;

    /**
     * 获取用户权限。
     * 
     * @param userId 用户ID
     * @return 用户权限码，每位代表一种权限。
     * @exception NullPointerException 参数为空。
     */
    public long getPremission(BigInteger userId)
    {
        long ret = 0;

        if (userId == null || BigInteger.ZERO.compareTo(userId) >= 0)
        {
            throw new NullPointerException();
        } // 结束：if (userId == null || BigInteger.ZERO.compareTo(userId) >= 0)

        try
        {

            if (urRepo.existsById(userId))
            {
                UserRole uRole = urRepo.getOne(userId);
                Role role = rRepo.getOne(uRole.getRoleId());
                ret = role.getPermissions();
            } // 结束：if (urRepo.existsById(userId))
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ret;
    }
}
