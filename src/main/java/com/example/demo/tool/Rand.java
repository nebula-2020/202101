/*
 * 文件名：Rand.java
 * 描述：常见工具。
 * 修改人：刘可
 * 修改时间：2021-01-31
 */
package com.example.demo.tool;

import java.util.Random;

/**
 * 随机数生成器。
 */
public abstract class Rand
{
    private final static Random ROOT_RANDOM = new Random();

    public final static Random getRandom()
    {
        return new Random(ROOT_RANDOM.nextLong());
    }
}
