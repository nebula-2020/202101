/*
 * 文件名：TopicInfo.java
 * 描述：单表存储库
 * 修改人：刘可
 * 修改时间：2021-02-20
 */
package com.example.demo.repository;


import java.math.BigInteger;
import com.example.demo.entity.*;
import org.springframework.data.jpa.repository.*;

/**
 * 话题信息的存储库。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @since 2021-02-20
 */
public  interface  TopicInfoRepository extends JpaRepository<TopicInfo, BigInteger>,
JpaSpecificationExecutor<TopicInfo>
{
}