/*
 * 文件名：ComService.java
 * 描述：常见服务。
 * 修改人：刘可
 * 修改时间：2021-02-01
 */
package com.example.demo.service;

import com.example.demo.tool.CommonTool;

/**
 * 常见服务。
 * <p>
 * 为子类提供一个工具类实例。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @since 2021-02-01
 */
public abstract class ComService
{
    protected final CommonTool tool = new CommonTool();
}
