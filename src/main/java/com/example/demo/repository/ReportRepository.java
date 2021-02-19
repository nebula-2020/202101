/*
 * 文件名：Report.java
 * 描述：单表存储库
 * 修改人：刘可
 * 修改时间：2021-02-19
 */
package com.example.demo.repository;

import java.math.BigInteger;
import com.example.demo.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * 举报信息的存储库。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @see finishOne
 * @since 2021-02-19
 */
public interface ReportRepository extends JpaRepository<Report, BigInteger>,
        JpaSpecificationExecutor<Report>
{
    /**
     * 设置一个举报信的有效性。
     * 
     * @param id 举报信ID
     * @param del 标注一个举报是否处理完毕
     * @return 变更行数。
     */
    @Transactional
    @Modifying
    @Query(
            value = "UPDATE `report` SET `del`=:del WHERE id=:id",
            nativeQuery = true
    )
    int finishOne(@Param("id") BigInteger id, @Param("del") boolean del);
}
