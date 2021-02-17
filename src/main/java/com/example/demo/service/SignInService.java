/*
 * 文件名：SignInService.java
 * 描述：项目主要服务。
 * 修改人： 刘可
 * 修改时间：2021-02-17
 */
package com.example.demo.service;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.regex.*;

import com.example.demo.constant.Constants;
import com.example.demo.entity.*;
import com.example.demo.entity.pk.SignInInfoKey;
import com.example.demo.repository.*;
import com.example.demo.vo.VisitVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户登录服务。
 * <p>
 * 验证登录和记录登录信息。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @see verify
 * @see phone2Id
 * @see account2Id
 * @see id2Account
 * @see signIn
 * @since 2021-02-17
 */
@Service("signInService")
public class SignInService extends ComService
{

    @Autowired
    UserBaseInfoRepository baseRepo;
    @Autowired
    SignInInfoRepository signInRepo;

    /**
     * 手机号密码验证。
     * <p>
     * 此方法不变更数据表。
     * 
     * @param phone 手机号
     * @param pwd 密码
     * @return 验证成功则返回用户ID，否则返回<code>null</code>。
     */
    public BigInteger verify(String phone, String pwd)
    {

        if (tool.containsNullOrEmpty(phone, pwd))
        {
            throw new NullPointerException();
        } // 结束：if (tool.containsNullOrEmpty(phone, pwd))
        UserBaseInfo user = baseRepo.findByPhone(phone);
        BigInteger ret = null;

        if (user != null && tool.isStrSame(pwd, user.getPassword()))
        {
            ret = user.getId();
        } // 结束：if(user != null && tool.isStrSame(pwd, user.getPassword()))
        return ret;
    }

    /**
     * 用户手机号转ID。
     * 
     * @param phone 手机号
     * @return ID
     */
    public BigInteger phone2Id(String phone)
    {
        BigInteger ret = null;

        if (!tool.isNullOrEmpty(phone))
        {
            UserBaseInfo user = baseRepo.findByPhone(phone);

            if (user != null)
            {
                ret = user.getId();
            } // 结束：if(user != null)
        } // 结束：if (tool.isNullOrEmpty(phone))
        return ret;
    }

    /**
     * 用户账号转ID或ID转ID。
     * <p>
     * 参数全为数字则认为其为ID。
     * 账号或ID存在则返回存在的ID。
     * 
     * @param account 个性账号或ID
     * @return ID。
     */
    public BigInteger account2Id(String account)
    {
        BigInteger ret = null;

        if (!tool.isNullOrEmpty(account))
        {
            Pattern p = Pattern.compile(Constants.REGEXP_ID);
            Matcher m = p.matcher(account);
            UserBaseInfo user = null;

            if (m.matches())
            {
                user = baseRepo.getOne(new BigInteger(account));
            }
            else
            {
                user = baseRepo.findByAccount(account);
            } // 结束：if(m.matches())

            if (user != null)
            {
                ret = user.getId();
            } // 结束：if(user != null)
        } // 结束：if (!tool.isNullOrEmpty(account))
        return ret;
    }

    /**
     * ID转账号。
     * 如果用户未定义个性账号则直接调用<code>toString</code>返回ID。
     * 
     * @param id ID
     * @return 用户账号。
     */
    public String id2Account(BigInteger id)
    {
        String ret = null;

        if (id != null)
        {
            UserBaseInfo user = baseRepo.getOne(id);

            if (user != null)
            {
                ret = user.getAccount();
            } // 结束：if(user != null)

            if (user != null)
            {
                ret = user.getId().toString();
            } // 结束：if(user != null)
        } // 结束：if (id!=null)
        return ret;
    }

    /**
     * 用户登录服务。
     * <p>
     * 手机号和账号仅一个参数非空即可，若都非空则用手机号确定用户。
     * 
     * @param phone 手机号
     * @param account 账号
     * @param pwd 密码
     * @param noPwd 取<code>true</code>表示免密登录
     * @param info 登录信息
     * @return 登陆成功返回用户ID，否则返回<code>null</code>。
     */
    public BigInteger signIn(
            String phone, String account, String pwd, boolean noPwd,
            VisitVO info
    ) throws NullPointerException
    {
        BigInteger ret = null;

        // 账号和手机号全为空或密码为空且非免密登录
        if (tool.isNullOrEmpty(phone, account)
                || (tool.isNullOrEmpty(pwd) && !noPwd))
        {
            throw new NullPointerException();
        } // 结束：if(tool.isNullOrEmpty(phone,account)||(tool.isNullOrEmpty(pwd)&&!noPwd))

        try
        {
            // 用户登录成功与否
            UserBaseInfo user = null;

            // 验证用户信息
            if (!tool.isNullOrEmpty(phone))
            {
                user = baseRepo.findByPhone(phone);
            }
            else// 前面已经验证过了，如果手机号为空账号必然非空
            {
                user = baseRepo.findByAccount(account);
            } // 结束：if (!tool.isNullOrEmpty(phone))

            if (user != null
                    && (noPwd || tool.isStrSame(pwd, user.getPassword())))
            {
                // 用户存在且免密登录或密码正确
                SignInInfoKey primaryKey = new SignInInfoKey();
                primaryKey.setId(user.getId());
                primaryKey.setTime(new Timestamp(System.currentTimeMillis()));

                SignInInfo signIninfo = new SignInInfo(
                        primaryKey, info.getMethod().getValue(),
                        tool.toByteArray(info.getIpv6()),
                        tool.toByteArray(info.getMac()), info.getIpv4(),
                        info.getGps()
                );

                signInRepo.save(signIninfo);
                ret = user.getId();
            } // 结束：if(user!=null&&(noPwd||tool.isStrSame(pwd,user.getPassword())))
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 用户免密登录服务。
     * 
     * @param phone 手机号
     * @param info 登录信息
     * @return 登陆成功返回用户ID，否则返回<code>null</code>。
     */
    public BigInteger signIn(String phone, VisitVO info)
            throws NullPointerException
    {
        return signIn(phone, null, null, true, info);
    }
}
