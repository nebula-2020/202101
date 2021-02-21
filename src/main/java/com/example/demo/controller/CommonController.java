/*
 * 文件名：CommonController.java
 * 描述：必要控制器
 * 修改人：刘可
 * 修改时间：2021-02-21
 */

package com.example.demo.controller;

import com.example.demo.tool.*;

/**
 * 常见控制器。
 * <p>
 * 包括一个工具类实体。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @since 2021-02-21
 */
public abstract class CommonController
{
    protected final CommonTool tool = new CommonTool();
}
