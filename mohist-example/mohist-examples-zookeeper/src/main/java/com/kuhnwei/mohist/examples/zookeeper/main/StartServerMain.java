package com.kuhnwei.mohist.examples.zookeeper.main;

import com.kuhnwei.mohist.examples.zookeeper.listener.ServerListener;

/**
 * @author Kuhn Wei, email@kuhnwei.com
 * @version 2018/4/1 18:40
 */
public class StartServerMain {
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            // 程序执行时没有传递参数，也就是服务主机名称
            System.err.println("错误的执行程序，应该在执行的时候输入服务器名称。");
            // 系统退出
            System.exit(1);
        }
        new ServerListener(args[0]);
    }
}
