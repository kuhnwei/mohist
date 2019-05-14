package com.kuhnwei.mohist.examples.javabase.designpatterns.adapter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kuhn Wei ,email@kuhnwei.com
 * @version 2018/4/21 14:31
 */
public class OuterUser implements IOuterUser {
    @Override
    public Map<String, String> getUserBaseInfo() {
        Map<String, String> baseInfoMap = new HashMap<>();
        baseInfoMap.put("userName", "这个员工叫...");
        baseInfoMap.put("mobileNumber", "这个员工的电话是...");
        return baseInfoMap;
    }

    @Override
    public Map<String, String> gettUserOfficeInfo() {
        Map<String, String> map = new HashMap<>();
        map.put("jobPosition", "这个人的职位是Boss...");
        map.put("officeTelNumber", "员工的办公电话是...");
        return map;
    }

    @Override
    public Map<String, String> getUserHomeInfo() {
        Map<String, String> homeInfo = new HashMap<>();
        homeInfo.put("homeTelNumber", "员工的家庭电话是...");
        homeInfo.put("homeAddress", "员工的家庭地址是...");
        return homeInfo;
    }
}
