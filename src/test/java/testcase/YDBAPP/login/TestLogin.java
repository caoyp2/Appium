package testcase.YDBAPP.login;

import common.Location;
import io.qameta.allure.*;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import page.APPClient;
import page.LoginPage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;

@Feature("登录模块")
@Story("登录模块")
public class TestLogin {
    private LoginPage  loginPage = null;

    @Epic("益电宝APP UI自动化")
    @BeforeSuite
    public void startApp(){
        loginPage = APPClient.startApp();
        loginPage.appiumDriver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
    }

   @DataProvider(name = "loginInfoFail")
   public Object[][] usernameAndPwdFail(){
        return new Object[][]{
                {"cyp","123456","账号密码不匹配"}
        };
   }

    @Test(dataProvider = "loginInfoFail")
    @Description("用户名或密码错误时点击登录")
    public void loginByUsernameAndPassword(String username,String password,String msg){
        Allure.attachment("用户名",username);
        Allure.attachment("密码",password);

        Allure.step("点击登录");
        loginPage.loginPassword(username,password);
        boolean result = loginPage.getTip(Location.XPATH,String.format("//android.widget.TextView[@text='%s']",msg));

        Allure.step("清理登录页面填写的数据");
        loginPage.pageInit();

        Assert.assertEquals(result,true);
    }

    @DataProvider(name = "loginInfoSuccess")
    public Object[][] usernameAndPwdSuccess(){
        return new Object[][]{
                {"cyp","c123456","主页"}
        };
    }

    @Test(dataProvider = "loginInfoSuccess")
    @Description("用户名和密码正确时，点击登录")
    public void loginSuccess(String username,String password,String msg){
        Allure.attachment("用户名",username);
        Allure.attachment("密码",password);
        Allure.step("点击登录");

        loginPage.loginPassword(username,password);

        Allure.step("进入首页");
        try {
            Allure.addAttachment("登录成功进入首页",new FileInputStream(new File(loginPage.ScreenShot("loginSuccess"))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        WebElement homepage = loginPage.findByText(msg);
        Assert.assertNotEquals(homepage,null);
    }



    @AfterSuite
    public void shutdown(){
        loginPage.appiumDriver.quit();
    }
}
