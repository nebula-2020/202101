/*
 * 文件名：Message.java
 * 描述：单表存储库
 * 修改人：刘可
 * 修改时间：2021-02-20
 */
package com.example.demo.repository;

import com.example.demo.entity.*;
import com.example.demo.entity.pk.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * 私信存储库。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @see deleteOne
 * @see hideOne
 * @since 2021-02-20
 */
public  interface  MessageRepository extends JpaRepository<Message, MsgKey>,
JpaSpecificationExecutor<Message>
{
    /**
     * 设置私信是否删除。
     * 
     * @param id 实体联合主键
     * @param del 描述私信可见性
     * @return 变更行数。
     */
    @Transactional
    @Modifying
    @Query(
            value = "UPDATE `msg` SET `del`=:del WHERE `id`=:id",
            nativeQuery = true
    )
    int deleteOne(@Param("id") MsgKey id, @Param("del") Boolean del);
    /**
     * 设置私信是否隐藏。
     * 
     * @param id 实体联合主键
     * @param del 描述私信对于收件人的可见性
     * @return 变更行数。
     */
    @Transactional
    @Modifying
    @Query(
            value = "UPDATE `msg` SET `hide`=:hide WHERE `id`=:id",
            nativeQuery = true
    )
    int hideOne(@Param("id") MsgKey id, @Param("hide") Boolean del);
}
