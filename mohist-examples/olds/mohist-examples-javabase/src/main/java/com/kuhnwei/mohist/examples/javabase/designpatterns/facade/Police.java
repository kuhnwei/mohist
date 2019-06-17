package com.kuhnwei.mohist.examples.javabase.designpatterns.facade;

/**
 * @author Kuhn Wei ,email@kuhnwei.com
 * @version 2018/4/21 14:12
 */
public class Police {
    public void checkLetter(LetterProcess letterProcess) {
        System.out.println("检查信件: " + letterProcess);
    }
}
