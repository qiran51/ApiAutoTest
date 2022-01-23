package cn.liam.httpclient.cookies;

import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class MyCookiesForGet {
    private String url;
    private ResourceBundle bundle;
    private CookieStore cookieStore;
    @BeforeTest
    public void beforeTest(){
        bundle = ResourceBundle.getBundle("application", Locale.CHINA);
        url = bundle.getString("test.url");
    }
    @Test
    public void testGetCookies() throws IOException {
        String result;
        //从配置文件中，拼接测试的url
        HttpGet get = new HttpGet(this.url+bundle.getString("getCookies.uri"));
        //测试get请求
        //CloseableHttpClient client = HttpClients.createDefault();
        //创建CookieStore对象
        CookieStore cookieStore = new BasicCookieStore();
        //创建CloseableHttpClient对象，同时设置CookieStore
        CloseableHttpClient client = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        //创建Response对象执行get方法
        CloseableHttpResponse response = client.execute(get);
        //获得响应数据指定UTF-8
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);
        //获取CookieStore,为全局变量cookieStore赋值
        this.cookieStore = cookieStore;
        //循环输出ListCookies对象
        List<Cookie> cookieList = cookieStore.getCookies();
        for(Cookie cookie : cookieList){
            String name = cookie.getName();
            String value = cookie.getValue();
            //逐步迭代
            System.out.println("Cookie name="+name+";Cookie value="+value);
        }
    }
    //依赖testGetCookies类
    @Test(dependsOnMethods = {"testGetCookies"})
    public void testGetWithCookies() throws IOException {
        //创建get请求对象
        HttpGet get = new HttpGet(this.url+bundle.getString("test.get.with.cookies"));
        //创建CloseableHttpClient对象，设置Cookie + 创建response对象
        CloseableHttpClient client = HttpClients.custom().setDefaultCookieStore(this.cookieStore).build();
        CloseableHttpResponse response;
        //设置Cookie信息
        response = client.execute(get);
        //获取响应状态码
        int statusCode = response.getStatusLine().getStatusCode();
        //打印状态码
        System.out.println("Code = "+ statusCode);
        //判断状态码是否正确
        if (statusCode == 200){
            String result = EntityUtils.toString(response.getEntity(),"utf-8");
            System.out.println(result);
        }else{
            System.out.println("状态ww码是否正确：false");
        }

    }

}
