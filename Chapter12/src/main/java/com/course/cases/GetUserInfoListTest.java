package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.GetUserListCase;
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
import java.util.List;

public class GetUserInfoListTest {
    //依赖login接口
    @Test(dependsOnGroups = "loginTrue",description = "获取性别为男的用户信息")
    public void getUserListInfo() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession();
        GetUserListCase getUserListCase = session.selectOne("getUserListCase",1);
        System.out.println(getUserListCase.toString());
        System.out.println(TestConfig.getUserListUrl);

        //发送请求获取结果
        JSONArray resultJson = getJsonArray(getUserListCase);

        //验证
        List<User> userList = session.selectList(getUserListCase.getExpected(),getUserListCase);

        for(User u:userList){
            System.out.println("获取的user"+u.toString());
        }
        JSONArray userListJson = new JSONArray(userList);
        //判断长度
        Assert.assertEquals(userListJson.length(),resultJson.length());
        //判断用户是否一样
        for(int i = 0;i<resultJson.length();i++){
            JSONObject expect = (JSONObject) resultJson.get(i);
            JSONObject actual = (JSONObject) userListJson.get(i);
            Assert.assertEquals(expect.toString(),actual.toString());
        }
    }

    private JSONArray getJsonArray(GetUserListCase getUserListCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.getUserListUrl);
        JSONObject param = new JSONObject();
        param.put("userName",getUserListCase.getUserName());
        param.put("age",getUserListCase.getAge());
        param.put("sex",getUserListCase.getSex());
        post.setHeader("content-type","application/json");
        //将参数添加到方法中
        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);
        //设置cookie
        TestConfig.closeableHttpClient = HttpClients.custom().setDefaultCookieStore(TestConfig.cookieStore).build();
        String result;
        CloseableHttpResponse closeableHttpResponse = TestConfig.closeableHttpClient.execute(post);
        result = EntityUtils.toString(closeableHttpResponse.getEntity(),"utf-8");
        System.out.println(result);

        //将result信息转换为JSONArray,json对象做json转换
        JSONArray jsonArray = new JSONArray(result);

        return jsonArray;
    }

}
