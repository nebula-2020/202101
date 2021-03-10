/*
 * 文件名：SignUpService.java
 * 描述：用于用户注册账号时验证和修改数据库。
 * 修改人：刘可
 * 修改时间：2021-02-04
 */
package com.example.demo.service;

import java.math.BigInteger;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import com.example.demo.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户注册服务。
 * <p>
 * 用户通过短信验证注册时用此类的方法验证和完成注册。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @see signUp
 * @since 2021-02-04
 */
@Service("signUpService")
public class SignUpService extends ComService
{
    @Autowired
    UserDetailsRepository detailsRepo;
    @Autowired
    UserBaseInfoRepository baseRepo;
    @Autowired
    UserInfoRepository infoRepo;
    @Autowired
    UserStatusRepository statusRepo;

    /**
     * 用户注册服务。
     * 提供手机、密码、昵称三个参数缺一不可。
     * 
     * @exception NullPointerException 参数存在空值或运行中调用其他方法抛出此异常。
     * @param phone 手机号
     * @param pwd 密码
     * @param name 昵称
     * @return 描述注册成功与否。
     */
    public boolean signUp(String phone, String pwd, String name)
    {

        if (!StringUtils.hasText(phone, pwd, name))
        {
            throw new NullPointerException();
        } // 结束：if (tool.containsNullOrEmpty(phone, pwd, name))

        boolean ret = false;

        try
        {
            // 检查手机号注册过多少账号
            UserBaseInfo user = baseRepo.findByPhone(phone);

            if (user == null || user.getId() == null)
            {
                // 没注册过
                user = new UserBaseInfo();
                // 插入部分数据
                user.setPhone(phone);
                user.setAccount(phone);
                user.setPassword(pwd);
                user = baseRepo.save(user);
                BigInteger id = user.getId();// 获取第一个用此手机号注册的用户之id

                if (id != null && id.compareTo(BigInteger.valueOf(0)) > 0)// 判断id是否大于0
                {
                    // 插入另一部分数据
                    UserDetails detials = new UserDetails();
                    UserStatus status = new UserStatus();
                    UserInfo info = new UserInfo();
                    detials.setId(id);
                    status.setId(id);
                    info.setId(id);
                    info.setName(name);
                    info = infoRepo.save(info);
                    detials = detailsRepo.save(detials);
                    status = statusRepo.save(status);
                    ret = true;
                } // 结束：if (id!=null&&id.compareTo(BigInteger.valueOf(0)) > 0)

            } // 结束：if (existUser == null||existUser.getId()==null)

        }
        catch (IndexOutOfBoundsException e)
        {
            e.printStackTrace();
        }
        catch (NullPointerException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return ret;
    }
}
