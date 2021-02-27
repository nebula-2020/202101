/*
 * 文件名：ReportService.java
 * 描述：项目主要服务。
 * 修改人：刘可
 * 修改时间：2021-02-27
 */
package com.example.demo.service;

import java.math.BigInteger;
import com.example.demo.entity.*;
import com.example.demo.repository.*;
import com.example.demo.enumation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户举报服务。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @see report
 * @see finishReport
 * @since 2021-02-27
 */
@Service("reportService")
public class ReportService extends ComService
{
    @Autowired
    ArticleRepository articleRepo;
    @Autowired
    private ReportRepository reportRepo;

    /**
     * 某账号的用户举报某篇文章。
     * 
     * @param accuserId 举报者ID
     * @param articleId 文章ID
     * @param cause 举报原因
     * @return 发送举报信的ID。
     */
    public BigInteger report(
            BigInteger accuserId, BigInteger articleId, ReportCause cause
    )
    {
        BigInteger ret = null;

        try
        {
            Article article = articleRepo.getOne(articleId);

            // 判断账号和文章的存在和可以操作
            if ((!tool.containsNull(
                    accuserId, article, article.getDel(), article.getDraft()
            )) && (!article.getDel().booleanValue())
                    && (!article.getDraft().booleanValue()))
            {
                Report report = new Report();
                report.setArticle(articleId);
                report.setCause(cause.getValue());
                report.setSenderId(accuserId);
                report = reportRepo.save(report);
                ret = report.getId();
            } // 结束：if (sender != null && sender.getId() != null...

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 完结一个举报信。
     * 
     * @param reportId 举报信的ID
     * @param val 描述是否完结
     * @return 变更行数。
     */
    public int finishReport(BigInteger reportId, boolean val)
    {
        int ret = 0;

        try
        {
            ret = reportRepo.finishOne(reportId, val);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ret;
    }
}
