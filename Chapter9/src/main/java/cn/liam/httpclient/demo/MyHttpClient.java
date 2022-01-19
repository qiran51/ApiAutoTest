package cn.liam.httpclient.demo;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;

import java.io.IOException;

public class MyHttpClient {

    @Test
    public void test1() throws IOException {
        //存放结果
        String result;
        HttpGet get = new HttpGet("http://www.baidu.com");
        //执行get方法
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = client.execute(get);
        //getEntity()  注：接收整个响应内容，不能转换字符串
        //EntityUtils.toString()  注：将内容转换成字符串
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);
    }
}
