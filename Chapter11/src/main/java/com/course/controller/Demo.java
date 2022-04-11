package com.course.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import org.apache.catalina.User;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Log4j
@RestController
//这是RequestMapping的说明
@Api(value = "/v1")
@RequestMapping("/v1")
public class Demo {
    //首先获取sql语句的对象

    //@Autowired，预加载，启动即加载
    @Autowired
    private SqlSessionTemplate template;

    @RequestMapping(value = "/getUserCount", method = RequestMethod.GET)
    @ApiOperation(value = "获取到用户数量",httpMethod = "GET")
    public int getUserCount(){
       return template.selectOne("getUserCount");
    }
    //向user表中添加数据
    @RequestMapping(value = "/addUser",method = RequestMethod.POST)
    public int addUser(@RequestBody User user){
        int result = template.insert("addUser",user);
        return result;
    }

}
