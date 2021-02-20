/*
 * 文件名：UserInteractionService.java
 * 描述：项目主要服务。
 * 修改人： 刘可
 * 修改时间：2021-02-20
 */
package com.example.demo.service;

import java.math.BigInteger;
import java.sql.Timestamp;

import com.example.demo.entity.*;
import com.example.demo.entity.pk.BlackListKey;
import com.example.demo.entity.pk.FocusKey;
import com.example.demo.entity.pk.MsgKey;
import com.example.demo.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户互动服务。
 * <p>
 * 包括私信、关注、拉黑等。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @see addBlackList
 * @see deleteBlackList
 * @see sendMsg
 * @see deleteMsg
 * @see hideMsg
 * @see addFocus
 * @see deleteFocus
 * @since 2021-02-20
 */
@Service("userInteractionService")
public class UserInteractionService extends ComService
{
    @Autowired
    private FocusRepository focusRepo;
    @Autowired
    private BlackListRepository blackRepo;
    @Autowired
    private MessageRepository msgRepo;

    /**
     * 将一个用户添加至指定用户黑名单。
     * 
     * @param id 用户ID
     * @param blackId 被黑用户ID
     * @return 描述操作成功与否。
     */
    public boolean addBlackList(BigInteger id, BigInteger blackId)
    {
        boolean ret = false;

        try
        {
            BlackListKey primaryKey = new BlackListKey();
            primaryKey.setUserId(id);
            primaryKey.setBlackUserId(blackId);

            if (!blackRepo.existsById(primaryKey))
            {
                // 用户未拉黑
                BlackList black = new BlackList();
                black.setKey(primaryKey);
                blackRepo.save(black);
                ret = true;
            } // 结束：if(!blackRepo.existsById(primaryKey))
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 将一个用户移出指定用户黑名单。
     * 
     * @param id 用户ID
     * @param blackId 被黑用户ID
     * @return 描述操作成功与否。
     */
    public boolean deleteBlackList(BigInteger id, BigInteger blackId)
    {
        boolean ret = false;

        try
        {
            BlackListKey primaryKey = new BlackListKey();
            primaryKey.setUserId(id);
            primaryKey.setBlackUserId(blackId);

            if (blackRepo.existsById(primaryKey))
            {
                blackRepo.deleteById(primaryKey);
                ret = true;
            } // 结束：if(blackRepo.existsById(primaryKey))
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 发出私信。
     * 
     * @param fromId 发信用户ID
     * @param toId 收信用户ID
     * @param text 私信内容
     * @return 描述发信时间，操作失败返回负数。
     */
    public long sendMsg(BigInteger fromId, BigInteger toId, String text)
    {
        long ret = -1;

        try
        {
            MsgKey primaryKey = new MsgKey();
            primaryKey.setSenderId(fromId);
            primaryKey.setUserId(toId);
            Timestamp now = new Timestamp(System.currentTimeMillis());
            primaryKey.setTime(now);
            Message msg = new Message();
            msg.setMsgKey(primaryKey);
            msg.setText(text);
            msg = msgRepo.save(msg);
            ret = msg.getMsgKey().getTime().getTime();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 发信者删除私信。
     * 
     * @param fromId 发信用户ID
     * @param toId 收信用户ID
     * @param time 发信时间，构成联合主键
     * @return 变更行数。
     */
    public int deleteMsg(BigInteger fromId, BigInteger toId, long time)
    {
        int ret = 0;

        try
        {
            MsgKey primaryKey = new MsgKey();
            primaryKey.setSenderId(fromId);
            primaryKey.setUserId(toId);
            Timestamp now = new Timestamp(time);
            primaryKey.setTime(now);

            if (msgRepo.existsById(primaryKey))
            {
                ret = msgRepo.deleteOne(primaryKey, true);
            } // 结束：if(msgRepo.existsById(primaryKey))
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 收信者屏蔽私信。
     * 
     * @param fromId 发信用户ID
     * @param toId 收信用户ID
     * @param time 发信时间，构成联合主键
     * @return 变更行数。
     */
    public int hideMsg(BigInteger fromId, BigInteger toId, long time)
    {
        int ret = 0;

        try
        {
            MsgKey primaryKey = new MsgKey();
            primaryKey.setSenderId(fromId);
            primaryKey.setUserId(toId);
            Timestamp now = new Timestamp(time);
            primaryKey.setTime(now);

            if (msgRepo.existsById(primaryKey))
            {
                ret = msgRepo.hideOne(primaryKey, true);
            } // 结束：if(msgRepo.existsById(primaryKey))
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 关注一个用户。
     * 
     * @param fanId 粉丝ID
     * @param userId 被粉用户ID
     * @return 描述操作成功与否。
     */
    public boolean addFocus(BigInteger fanId, BigInteger userId)
    {
        boolean ret = false;

        try
        {
            FocusKey primaryKey = new FocusKey();
            primaryKey.setFanId(fanId);
            primaryKey.setUserId(userId);

            if (!focusRepo.existsById(primaryKey))
            {
                Focus focus = new Focus();
                focus.setKey(primaryKey);
                focusRepo.save(focus);
                ret = true;
            } // 结束：if(!focusRepo.existsById(primaryKey))
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 取关一个用户。
     * 
     * @param fanId 原粉丝ID
     * @param userId 被粉用户ID
     * @return 描述操作成功与否。
     */
    public boolean deleteFocus(BigInteger fanId, BigInteger userId)
    {
        boolean ret = false;

        try
        {
            FocusKey primaryKey = new FocusKey();
            primaryKey.setFanId(fanId);
            primaryKey.setUserId(userId);

            if (focusRepo.existsById(primaryKey))
            {
                focusRepo.deleteById(primaryKey);
                ret = true;
            } // 结束：if(focusRepo.existsById(primaryKey))
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ret;
    }
}
