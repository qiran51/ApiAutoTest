package cn.liam.extend.demo;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class TestMethodsDemo {
    @Test
    public void test1(){
        Assert.assertEquals(1,1);
    }
    @Test
    public void test2(){
        Assert.assertEquals(1,2);
    }
    @Test
    public void test3(){
        Assert.assertEquals("liam","liam");
    }
    @Test
    public void logPrint(){
        Reporter.log("日志打印@liam");
        throw new RuntimeException("logPrint方法抛出的异常");
    }
}
