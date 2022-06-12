package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.GetUserInfoCase;
import com.course.model.User;
import com.course.utils.DatabaseUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetUserInfoTest {
    //依赖login
    @Test(dependsOnGroups = "loginTrue",description = "获取userID为1的用户信息")
    public void getUserInfo() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession();
        GetUserInfoCase getUserInfoCase = session.selectOne("getUserInfoCase",1);
        System.out.println(getUserInfoCase.toString());
        System.out.println(TestConfig.getUserInfoUrl);

        JSONArray resultJson = getJsonResult(getUserInfoCase);
        //取数据库信息
        User user = session.selectOne(getUserInfoCase.getExpected(),getUserInfoCase);
        //把查询出来的user放到list中,只有放进去了，才能转换jsonArray
        List userList = new ArrayList();
        userList.add(user);
        //转换成jsonArray
        JSONArray jsonArray = new JSONArray(userList);
        JSONArray jsonArray1 = new JSONArray(resultJson.getString(0));
        System.out.println(jsonArray1);
        //验证/断言
        Assert.assertEquals(jsonArray.toString(),jsonArray1.toString());

    }

    private JSONArray getJsonResult(GetUserInfoCase getUserInfoCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.getUserInfoUrl);
        JSONObject param = new JSONObject();
        param.put("id",getUserInfoCase.getUserId());
        post.setHeader("content-type","application/json");
        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);
        //设置cookie
        TestConfig.closeableHttpClient = HttpClients.custom().setDefaultCookieStore(TestConfig.cookieStore).build();
        String result;
        //发请求
        CloseableHttpResponse closeableHttpResponse = TestConfig.closeableHttpClient.execute(post);
        result = EntityUtils.toString(closeableHttpResponse.getEntity(),"utf-8");
        //把result转换成list,不转换变不了jsonArray
        List resultList = Arrays.asList(result);

        JSONArray jsonArray = new JSONArray(resultList);
        return jsonArray;
    }
}
