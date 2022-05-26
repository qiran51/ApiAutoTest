package com.course.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

//获取能执行sql的对象SqlSession
public class DatabaseUtil {
    public static SqlSession getSqlSession() throws IOException {
        //获取配置的资源文件
        Reader reader = Resources.getResourceAsReader("databaseConfig.xml");
        //使用类加载器加载这个文件
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
        //返回SqlSession
        SqlSession sqlSession = factory.openSession();

        return sqlSession;
    }
}
