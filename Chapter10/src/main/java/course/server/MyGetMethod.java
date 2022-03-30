package course.server;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jdk.internal.org.objectweb.asm.tree.analysis.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@Api(value = "/")
public class MyGetMethod {

    @RequestMapping(value="/getCookies",method = RequestMethod.GET)
    @ApiOperation(value = "通过这个方法获取cookies",httpMethod = "GET")
    public String getCookies(HttpServletResponse response){
        Cookie cookie = new Cookie("login","true");
        response.addCookie(cookie);
        return "已经获得Cookies信息";
    }

    /**
     * 携带cookie访问get请求
     *
     * */
    @RequestMapping(value = "/get/with/cookies",method = RequestMethod.GET)
    @ApiOperation(value = "携带cookie访问get请求",httpMethod = "GET")
    public String getWithCookies(HttpServletRequest request){
        Cookie [] cookies = request.getCookies();
        //判断数组是否为空
        if(Objects.isNull(cookies)){
            return "No cookie information";
        }
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("login") && cookie.getValue().equals("true")){
                return "succeed";
            }
        }
        return "No cookie information";
    }
    /**
     * 开发一个需要携带参数才能访问的get请求
     * 第一种实现方式，url：key=value&key=value
     * 模拟获取商品列表
     * */
    @RequestMapping(value = "/get/with/param",method = RequestMethod.GET)
    @ApiOperation(value = "需要携带参数才能访问的get请求1",httpMethod = "GET")
    public Map<String,Integer> getList(@RequestParam Integer start,@RequestParam Integer end){
        Map<String,Integer> myList = new HashMap<>();
        myList.put("足力建",300);
        myList.put("手机",6000);
        myList.put("电脑",9000);

        return myList;
    }
    /**
     * 第二种需要携带参数才能访问的get请求
     * 第二种实现方式，url：ip:port/get/with/param/10/20
     * 模拟获取商品列表
     * */
    @RequestMapping(value = "/get/with/param/{start}/{end}")
    @ApiOperation(value = "需要携带参数才能访问的get请求2",httpMethod = "GET")
    public Map myGetList(@PathVariable Integer start,@PathVariable Integer end){
        Map<String,Integer> myList = new HashMap<>();
        myList.put("足力建",300);
        myList.put("手机",2000);
        myList.put("电脑",5000);

        return myList;
    }

}
