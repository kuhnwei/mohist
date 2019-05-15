package com.kuhnwei.mohist.examples.javabase.designpatterns.template;

/**
 * 动作抽象模板类
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/1/26 20:11
 */
public abstract class AbstractAction {

    /** 吃 */
    private static final int EAT = 1;

    /** 睡 */
    private static final int SLEEP = 2;

    /** 工作 */
    private static final int WORK = 5;

    /**
     * 动作命令
     * @param code 动作代码
     */
    private void command(int code) {
        switch (code) {
            case EAT : {
                this.eat();
                break;
            }
            case SLEEP : {
                this.sleep();
                break;
            }
            case WORK : {
                this.work();
                break;
            }
            default: {
                break;
            }
        }
    }

    /**
     * 执行
     */
    void execute() {
        this.command(EAT);
        this.command(SLEEP);
        this.command(WORK);
    }

    /**
     * 吃
     */
    abstract void eat();

    /**
     * 睡觉
     */
    abstract void sleep();

    /**
     * 工作
     */
    abstract void work();

}
