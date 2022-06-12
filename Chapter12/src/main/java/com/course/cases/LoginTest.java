package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.InterfaceName;
import com.course.model.LoginCase;
import com.course.utils.ConfigFile;
import com.course.utils.DatabaseUtil;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class LoginTest {

    @BeforeTest(groups = "loginTrue",description = "测试准备工作，获取httpClient对象")
    public void beforeTest(){
        TestConfig.getUserInfoUrl = ConfigFile.getUrl(InterfaceName.GETUSERINFO);
        TestConfig.getUserListUrl = ConfigFile.getUrl(InterfaceName.GETUSERLIST);
        TestConfig.addUserUrl = ConfigFile.getUrl(InterfaceName.ADDUSERINFO);
        TestConfig.loginUrl = ConfigFile.getUrl(InterfaceName.LOGIN);
        TestConfig.upDateUserInfo = ConfigFile.getUrl(InterfaceName.UPDATEUSERINFO);

        //newHttpClient
        TestConfig.closeableHttpClient = HttpClients.createDefault();
    }

    //登录
    @Test(groups = "loginTrue",description = "用户登录成功接口测试")
    public void loginTrue() throws IOException {
        //从数据库中取测试数据
        SqlSession session = DatabaseUtil.getSqlSession();
        LoginCase loginCase = session.selectOne("loginTrue",1);
        System.out.println(loginCase.toString());
        System.out.println(TestConfig.loginUrl);
        //第一步发送请求
        String result = getResult(loginCase);

        //第二步验证结果
        Assert.assertEquals(loginCase.getExpected(),result);
    }


    @Test(groups = "loginFalse",description = "用户登录失败接口测试")
    public void loginFalse() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession();
        LoginCase loginCase = session.selectOne("loginTrue",2);
        System.out.println(loginCase.toString());
        System.out.println(TestConfig.loginUrl);
        //第一步发送请求
        String result = getResult(loginCase);

        //第二步验证结果
        Assert.assertEquals(loginCase.getExpected(),result);
    }

    private String getResult(LoginCase loginCase) throws IOException {

        //创建请求
        HttpPost post = new HttpPost(TestConfig.loginUrl);
        //设置参数
        JSONObject param = new JSONObject();
        param.put("userName",loginCase.getUserName());
        param.put("password",loginCase.getPassword());
        post.setHeader("content-type","application/json");
        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);
        TestConfig.cookieStore = new BasicCookieStore();
        TestConfig.closeableHttpClient = HttpClients.custom().setDefaultCookieStore(TestConfig.cookieStore).build();
        String result;
        //发起请求
        CloseableHttpResponse closeableHttpResponse = TestConfig.closeableHttpClient.execute(post);
        result = EntityUtils.toString(closeableHttpResponse.getEntity(),"utf-8");
        //System.out.println(result);
        //给cookies变量赋值，不然后面所有依赖登录接口的用例全部报错
        //System.out.println("-----");
        //TestConfig.cookieStore = (CookieStore) TestConfig.cookieStore.getCookies();
//        List<Cookie> cookies = TestConfig.cookieStore.getCookies();
//        for (Cookie cookie : cookies){
//            System.out.println("cookie name="+cookie.getName()+";cookie value="+cookie.getValue());
//        }
        //test
        return result;
    }
}
