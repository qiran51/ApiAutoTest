package com.sourse.cases;

import com.sourse.config.TestConfig;
import com.sourse.model.InterfaceName;
import com.sourse.model.LoginCase;
import com.sourse.utils.ConfigFile;
import com.sourse.utils.DatabaseUtil;
import org.apache.http.impl.client.HttpClients;
import org.apache.ibatis.session.SqlSession;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginTest {

    @BeforeTest(groups = "loginTrue",description = "测试准备工作，获取httpClient对象")
    public void beforeTest(){
        TestConfig.getUserInfoUrl = ConfigFile.getUrl(InterfaceName.GETUSERINFO);
        TestConfig.getUserListUrl = ConfigFile.getUrl(InterfaceName.GETUSERLIST);
        TestConfig.addUserUrl = ConfigFile.getUrl(InterfaceName.ADDUSERINFO);
        TestConfig.loginUrl = ConfigFile.getUrl(InterfaceName.LOGIN);
        TestConfig.updateUserInfoUrl = ConfigFile.getUrl(InterfaceName.UPDATEUSERINFO);

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
    }
    @Test(groups = "loginFalse",description = "用户登录失败接口测试")
    public void loginFalse() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession();
        LoginCase loginCase = session.selectOne("loginTrue",2);
        System.out.println(loginCase.toString());
        System.out.println(TestConfig.loginUrl);
    }

}
