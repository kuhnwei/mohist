package com.kuhnwei.mohist.examples.javabase.designpatterns.facade;

/**
 * @author Kuhn Wei ,email@kuhnwei.com
 * @version 2018/4/21 14:05
 */
public class Client {
    public static void main(String[] args) {
        String context = "Hello, It's me, do you know who I am? I'm your old lover. I'd like to ...";
        String address = "Happy Road No. 666, God Province, Heaven";
        ModenPostOffice hellRoadPostOffice = new ModenPostOffice();
        hellRoadPostOffice.sendLetter(context, address);
    }
}
