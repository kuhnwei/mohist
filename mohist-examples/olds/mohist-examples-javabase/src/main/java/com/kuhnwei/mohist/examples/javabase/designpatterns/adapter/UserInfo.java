package com.kuhnwei.mohist.examples.javabase.designpatterns.adapter;

/**
 * @author Kuhn Wei ,email@kuhnwei.com
 * @version 2018/4/21 14:25
 */
public class UserInfo implements IUserInfo {
    @Override
    public String getUsername() {
        System.out.println("姓名叫...");
        return null;
    }

    @Override
    public String getHomeAddress() {
        System.out.println("这是员工的家庭地址...");
        return null;
    }

    @Override
    public String getMobileNumber() {
        System.out.println("这个人的手机号码是.......");
        return null;
    }

    @Override
    public String getOfficeTelNumber() {
        System.out.println("办公室电话是.....");
        return null;
    }

    @Override
    public String getJobPosition() {
        System.out.println("这个人的职位是.....");
        return null;
    }

    @Override
    public String getHomeTelNumber() {
        System.out.println("家庭电话....");
        return null;
    }
}
