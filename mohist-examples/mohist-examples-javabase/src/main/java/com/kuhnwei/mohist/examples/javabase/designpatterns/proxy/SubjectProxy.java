package com.kuhnwei.mohist.examples.javabase.designpatterns.proxy;

/**
 * 主题代理
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/1/26 19:57
 */
public class SubjectProxy implements Subject {

    private Subject subject;

    /**
     * 构造方法
     * @param subject 主题对象
     */
    public SubjectProxy(Subject subject) {
        this.subject = subject;
    }

    /**
     * 主题方法执行前
     */
    public void prepare() {
        System.out.println("[操作前]--->>> 1... 2... 3...");
    }

    public void destroy() {
        System.out.println("[操作后]--->>> 1... 2... 3...");
    }

    @Override
    public void get() {
        this.prepare();
        this.subject.get();
        this.destroy();
    }
}
