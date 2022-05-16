package com.sourse.cases;

import com.sourse.config.TestConfig;
import com.sourse.model.GetUserListCase;
import com.sourse.utils.DatabaseUtil;
import org.apache.ibatis.session.SqlSession;
import org.testng.annotations.Test;

import java.io.IOException;

public class GetUserInfoListTest {
    //依赖login接口
    @Test(dependsOnGroups = "loginTrue",description = "获取性别为男的用户信息")
    public void getUserListInfo() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession();
        GetUserListCase getUserListCase = session.selectOne("getUserListCase",1);
        System.out.println(getUserListCase.toString());
        System.out.println(TestConfig.getUserListUrl);
    }

}
