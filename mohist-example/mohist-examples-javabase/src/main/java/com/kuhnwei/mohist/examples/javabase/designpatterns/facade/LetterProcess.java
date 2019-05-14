package com.kuhnwei.mohist.examples.javabase.designpatterns.facade;

/**
 * @author Kuhn Wei ,email@kuhnwei.com
 * @version 2018/4/21 14:00
 */
public interface LetterProcess {
    // 写信
    void writeContext(String context);
    // 写信封
    void fillEnvelope(String address);
    // 把信放到信封里
    void letterIntoEnvelope();
    // 邮递
    void sendLetter();
}
