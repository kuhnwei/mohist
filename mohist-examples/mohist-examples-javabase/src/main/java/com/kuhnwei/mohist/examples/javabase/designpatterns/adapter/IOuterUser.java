package com.kuhnwei.mohist.examples.javabase.designpatterns.adapter;

import java.util.Map;

/**
 * @author Kuhn Wei ,email@kuhnwei.com
 * @version 2018/4/21 14:30
 */
public interface IOuterUser {
    Map<String, String> getUserBaseInfo();
    Map<String, String> gettUserOfficeInfo();
    Map<String, String> getUserHomeInfo();
}
