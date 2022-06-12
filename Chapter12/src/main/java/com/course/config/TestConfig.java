package com.course.config;

import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.CloseableHttpClient;

//获取用户基本信息配置
public class TestConfig {
    //跟配置文件里面的名称对应
    public static String loginUrl;
    public static String upDateUserInfo;
    public static String getUserListUrl;
    public static String getUserInfoUrl;
    public static String addUserUrl;

    public static CloseableHttpClient closeableHttpClient;
    public static CookieStore cookieStore;

}
