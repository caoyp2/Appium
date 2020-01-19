package testcase.allure;


import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Epic("模块1-epic")
@Feature("模块1-Feature")
@Story("模块1-story")
public class TestAllure {

    @Test
    @Description("错误的断言")
    public void test1(){
        Allure.step("初始化变量");
        int a = 10;
        Allure.attachment("a",a +"");

        int b = 20;
        Allure.attachment("b",b +"");
        Assert.assertEquals(a,b);
    }

    @Story("测试2")
    @Test
    @Description("正确的断言")
    public void test2(){
        Assert.assertEquals(20,20);
        try {
            Allure.addAttachment("截图",new FileInputStream(new File("./pic/test.jpg")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
