package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.UpdateUserInfoCase;
import com.course.model.User;
import com.course.utils.DatabaseUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class UpDateUserInfoTest {
    //依赖login
    @Test(dependsOnGroups = "loginTrue",description = "更改用户信息")
    public void upDateUserInfo() throws IOException, InterruptedException {
        SqlSession session = DatabaseUtil.getSqlSession();
        UpdateUserInfoCase updateUserInfoCase = session.selectOne("upDateUserInfoCase",1);
        System.out.println(updateUserInfoCase.toString());
        System.out.println(TestConfig.upDateUserInfo);
        int result = getResult(updateUserInfoCase);
        Thread.sleep(3000);
        //查询数据库信息
        User user = session.selectOne(updateUserInfoCase.getExpected(),updateUserInfoCase);
        //验证/断言
        Assert.assertNotNull(result);
        Assert.assertNotNull(user);
    }

    //删除
    @Test(dependsOnGroups = "loginTrue",description = "删除用户信息")
    public void deleteUser() throws IOException, InterruptedException {
        SqlSession session = DatabaseUtil.getSqlSession();
        UpdateUserInfoCase updateUserInfoCase = session.selectOne("upDateUserInfoCase",2);
        System.out.println(updateUserInfoCase.toString());
        System.out.println(TestConfig.upDateUserInfo);
        int result = getResult(updateUserInfoCase);
        //查询数据库信息
        Thread.sleep(3000);

        User user = session.selectOne(updateUserInfoCase.getExpected(),updateUserInfoCase);
        //验证/断言
        Assert.assertNotNull(result);
        Assert.assertNotNull(user);
    }

    private int getResult(UpdateUserInfoCase updateUserInfoCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.upDateUserInfo);
        //设置请求参数
        JSONObject param = new JSONObject();
        param.put("id",updateUserInfoCase.getUserId());
        param.put("userName",updateUserInfoCase.getUserName());
        param.put("sex",updateUserInfoCase.getSex());
        param.put("age",updateUserInfoCase.getAge());
        param.put("permission",updateUserInfoCase.getPermission());
        param.put("isDelete",updateUserInfoCase.getIsDelete());
        post.setHeader("content-type","application/json");
        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);
        //设置cookie
        TestConfig.closeableHttpClient = HttpClients.custom().setDefaultCookieStore(TestConfig.cookieStore).build();
        //发送请求
        String result;
        CloseableHttpResponse closeableHttpResponse = TestConfig.closeableHttpClient.execute(post);
        result = EntityUtils.toString(closeableHttpResponse.getEntity(),"utf-8");
        return Integer.parseInt(result);
    }
}
