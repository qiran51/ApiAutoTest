package course.server;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
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
            response.addCookie(cookie);
            return "登录成功";
        }
            return "账户或者密码错误";
    }

}
