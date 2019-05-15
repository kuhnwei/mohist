package com.kuhnwei.mohist.examples.javabase.designpatterns.strategy;

/**
 * 计谋有了，还要有个锦囊
 * @author Kuhn Wei ,email@kuhnwei.com
 * @version 2018/4/20 16:00
 */
public class Context {
    private IStrategy strategy;
    public Context(IStrategy strategy) {
        this.strategy = strategy;
    }

    public void operate() {
        // 使用计谋
        this.strategy.operate();
    }
}
