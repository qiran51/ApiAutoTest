package com.course.controller;

import com.course.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

@Log4j
@RestController
@Api(value = "v1")
//路径访问
@RequestMapping("v1")
public class UserManager {
    // 首先要有访问数据库的对象,Autowired可以直接把对象new出来
    @Autowired
    private SqlSessionTemplate template;
    //登录
    @ApiOperation(value="登录接口",httpMethod = "POST")
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    //response返回cookie用的
    //user入参
    public Boolean login(HttpServletResponse response, @RequestBody User user){
        int i = template.selectOne("login",user);
        Cookie cookie = new Cookie("login","true");
        response.addCookie(cookie);
        log.info("查询到的结果是"+i);
        if(i==1){
            log.info("查询到的用户是"+user.getUserName());
            return true;
        }
        return false;
    }
    //添加用户
    @ApiOperation(value = "添加用户接口",httpMethod = "POST")
    @RequestMapping(value = "/addUser",method = RequestMethod.POST)
    //添加用户需要登录通过才能添加，需要带Cookie请求，验证Cookie
    public boolean addUser(HttpServletRequest request,@RequestBody User user){
        Boolean x = verifyCookies(request);
        //记录
        int result = 0;
        if (x != null){
            result = template.insert("addUser",user);
        }
        if (result > 0){
            log.info("添加用户数量是"+result);
            return true;
            }
        return false;
    }

    @ApiOperation(value = "获取用户（列表）信息接口",httpMethod = "POST")
    @RequestMapping(value = "/getUserInfo",method = RequestMethod.POST)
    //返回List可以是单个也可以是多个
    public List<User> getUserInfo(HttpServletRequest request,@RequestBody User user){
        Boolean x = verifyCookies(request);
        if(x==true){
            List<User> users = template.selectList("getUserInfo",user);
            log.info("获取到的数量是"+users.size());
            return users;
        }else{
            return null;
        }
    }

    @ApiOperation(value = "更新/删除用户信息",httpMethod = "POST")
    @RequestMapping(value = "/upDateUserInfo",method = RequestMethod.POST)
    //更新用户信息
    public int upDateUser(HttpServletRequest request,@RequestBody User user){
        Boolean x = verifyCookies(request);
        int i = 0;
        if(x == true){
            i = template.update("upDateUserInfo",user);
        }
        log.info("更新数据的条目数为："+i);
        return i;
    }

    private Boolean verifyCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(Objects.isNull(cookies)){
            log.info("Cookies为空");
            return false;
        }
        //遍历cookie
        for(Cookie cookie:cookies){
            if(cookie.getName().equals("login") && cookie.getValue().equals("true")){
                log.info("cookie验证通过");
                return true;
            }
        }
        return false;
    }
}
