package cn.liam.httpclient.cookies;

import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class MyCookieForPost {
    private String url;
    private ResourceBundle bundle;
    private CookieStore cookieStore;

    @BeforeTest
    public void readConfiguration(){
        bundle = ResourceBundle.getBundle("application", Locale.CHINA);
        url = bundle.getString("test.url");
    }
    @Test
    public void getCookie() throws IOException {
        //从配置文件中拼接测试的URL
        //HttpGet httpGet = new HttpGet(this.url+bundle.getString("getCookies.uri"));
        String uri = bundle.getString("getCookies.uri");
        String testUri = this.url+uri;

        //创建CookieStore获取cookies信息,并设置cookies信息
        this.cookieStore = new BasicCookieStore();
        CloseableHttpClient client = HttpClients.custom().setDefaultCookieStore(cookieStore).build();

        //创建httpGet请求，传入拼接好的URL，测试逻辑
        HttpGet httpGet = new HttpGet(testUri);
        CloseableHttpResponse response = client.execute(httpGet);

        //打印返回值
        String result = EntityUtils.toString(response.getEntity());
        System.out.println(result);

        //读取cookie信息
        List<Cookie> cookieList = cookieStore.getCookies();
        for (Cookie cookie : cookieList){
            System.out.println("cookie name="+cookie.getName()+";cookie value="+cookie.getValue());
        }
    }
    //创建测试类，依赖于getCookie类
    @Test(dependsOnMethods = {"getCookie"})
    public void requestPost() throws IOException {
        //读取配置
        String uri = bundle.getString("test.post.with.cookies");
        //拼接最终测试地址
        String testUrl = this.url+uri;
        //声明post方法
        HttpPost httpPost = new HttpPost(testUrl);
        //添加参数
        JSONObject param = new JSONObject();
        param.put("name","zhangjialing");
        param.put("age","19");
        //添加请求头
        httpPost.setHeader("conent-type","application/json");
        //将参数信息添加到方法中
        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        httpPost.setEntity(entity);
        //声明一个client对象，用于进行方法的执行，并设置cookies信息
        CloseableHttpClient client = HttpClients.custom().setDefaultCookieStore(this.cookieStore).build();
        //执行post的方法并得到响应结果
        CloseableHttpResponse response = client.execute(httpPost);
        //判断结果是否符合预期
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("StatusCode="+statusCode);
        //获取响应结果
        String result = EntityUtils.toString(response.getEntity(),"utf-8");
        if (statusCode == 200){
            System.out.println(result);
        }else{
            System.out.println("fail");
        }
        //将返回的响应结果字符串转换json对象
        JSONObject resultjson = new JSONObject(result);
        //获取到结果值
        String success = (String) resultjson.get("zhangjialing");
        System.out.println(success);
        Assert.assertEquals("success",success);
    }





}
