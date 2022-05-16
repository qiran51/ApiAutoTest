package com.sourse.cases;

import com.sourse.config.TestConfig;
import com.sourse.model.GetUserInfoCase;
import com.sourse.utils.DatabaseUtil;
import org.apache.ibatis.session.SqlSession;
import org.testng.annotations.Test;

import java.io.IOException;

public class GetUserInfoTest {
    //依赖login
    @Test(dependsOnGroups = "loginTrue",description = "获取userID为1的用户信息")
    public void getUserInfo() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession();
        GetUserInfoCase getUserInfoTest = session.selectOne("getUserInfo",1);
        System.out.println(getUserInfoTest.toString());
        System.out.println(TestConfig.getUserInfoUrl);
    }
}
