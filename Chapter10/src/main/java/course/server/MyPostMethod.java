package course.server;

import course.bean.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.val;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Api(value = "/")
//在这class中的所有请求路径都会带上v1
@RequestMapping("/v1")
public class MyPostMethod {
    //这个变量是用来装cookies的
    private static Cookie cookie;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(HttpServletResponse response,
                        @RequestParam(value = "userName",required = true) String userName,
                        @RequestParam(value = "password",required = true) String password){

        //判断信息是否一致
        if(userName.equals("Liam") && password.equals("123456")){
            cookie = new Cookie("login","true");
//            val s = cookie.toString();
            response.addCookie(cookie);
//            return s;
            return "登录成功";
        }
            return "账户或者密码错误";
    }

    @RequestMapping(value = "/getUserList",method = RequestMethod.POST)
    @ApiOperation(value = "获取用户列表",httpMethod = "POST")
    //HttpServletRequest不传拿不到cookie
    public String getUserList(HttpServletRequest request,@RequestBody User u){
        User user;
        //获得cookie
        Cookie[] cookies = request.getCookies();
        //校验cookie
        for(Cookie c : cookies){
            if(c.getName().equals("login")
                    && c.getValue().equals("true")
                    && u.getUserName().equals("Liam")
                    && u.getPassWord().equals("123456")){
                user = new User();
                user.setName("Liam1");
                user.setAge("19");
                user.setSex("man");
                return user.toString();
            }
        }
        return "参数错误";
    }
}
