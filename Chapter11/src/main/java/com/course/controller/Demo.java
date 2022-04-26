package com.course.controller;

import com.course.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Log4j
@RestController
//这是RequestMapping的说明
@Api(value = "/v1")
@RequestMapping("/v1/")
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
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    @ApiOperation(value = "增加user数据",httpMethod = "POST")
    public int addUser(@RequestBody User user){
        int result = template.insert("addUser",user);
        return result;
    }
    //修改user表中用户信息,需要传入一个对象
    @RequestMapping(value = "/upDateUserInfo",method = RequestMethod.POST)
    @ApiOperation(value = "修改user表中用户信息",httpMethod = "POST")
    public int upDateUserInfo(@RequestBody User user){
        return template.update("upDateUserInfo",user);
    }
    //删除user表中用户信息，传入一个id，根据这个id进行删除
    @RequestMapping(value = "/deleteUserInfo",method = RequestMethod.POST)
    @ApiOperation(value = "删除user表中用户信息",httpMethod = "POST")
    public int deleteUserInfo(@RequestParam int id){
        return template.delete("deleteUserInfo",id);
    }


}
