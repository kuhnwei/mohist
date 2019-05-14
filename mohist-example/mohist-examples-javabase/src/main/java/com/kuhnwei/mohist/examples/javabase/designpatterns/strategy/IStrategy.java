package com.kuhnwei.mohist.examples.javabase.designpatterns.strategy;

/**
 * 首先定一个策略接口，诸葛亮给赵云的三个锦囊妙计接口
 * @author Kuhn Wei ,email@kuhnwei.com
 * @version 2018/4/20 15:56
 */
public interface IStrategy {
    /**
     * 每个锦囊妙计都是一个可执行的算法
     */
    void operate();
}
