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
    private CloseableHttpClient client;

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
        CookieStore cookieStore = new BasicCookieStore();
        //获取Cookies信息
        client = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        CloseableHttpResponse response = client.execute(get);
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);

        //获取Cookies信息
        //获取CookiesStore对象
        List<Cookie> cookieList = cookieStore.getCookies();
        //循环输出ListCookies对象
        for(Cookie cookie : cookieList){
            String name = cookie.getName();
            String value = cookie.getValue();
            //逐步迭代
            System.out.println("Cookie name="+name+";Cookie value="+value);
        }
    }
    //依赖testGetCookies类
    @Test(dependsOnMethods = {"testGetCookies"})
    public void testGetWithCookies(){
        //拼接测试的url
        HttpGet get = new HttpGet(this.url+bundle.getString("test.get.with.cookies"));
        CloseableHttpClient client = HttpClients.createDefault();

        //设置Cookie信息

    }

}
