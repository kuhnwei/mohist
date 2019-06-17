package com.kuhnwei.mohist.examples.javabase.designpatterns.adapter;

import java.util.Map;

/**
 * @author Kuhn Wei ,email@kuhnwei.com
 * @version 2018/4/21 14:37
 */
public class OuterUserInfo extends OuterUser implements IUserInfo {

    private Map<String, String> baseInfo = super.getUserBaseInfo();
    private Map<String, String> homeInfo = super.getUserHomeInfo();
    private Map<String, String> officeInfo = super.gettUserOfficeInfo();

    @Override
    public String getUsername() {
        String username = baseInfo.get("userName");
        System.out.println(username);
        return username;
    }

    @Override
    public String getHomeAddress() {
        String homeAddress = homeInfo.get("homeAddress");
        System.out.println(homeAddress);
        return homeAddress;
    }

    @Override
    public String getMobileNumber() {
        String mobileNumber = baseInfo.get("mobileNumber");
        System.out.println(mobileNumber);
        return mobileNumber;
    }

    @Override
    public String getOfficeTelNumber() {
        String officeTelNumber = officeInfo.get("officeTelNumber");
        System.out.println(officeTelNumber);
        return officeTelNumber;
    }

    @Override
    public String getJobPosition() {
        String jobPosition = officeInfo.get("jobPosition");
        System.out.println(jobPosition);
        return jobPosition;
    }

    @Override
    public String getHomeTelNumber() {
        String homeTelNumber = homeInfo.get("homeTelNumber");
        System.out.println(homeTelNumber);
        return homeTelNumber;
    }
}
