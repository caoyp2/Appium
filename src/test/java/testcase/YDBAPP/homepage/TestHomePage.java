package testcase.YDBAPP.homepage;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import page.HomePage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Feature("主页模块")
@Story("主页模块")
public class TestHomePage {

    private HomePage homePage = null;

    @BeforeClass
    public void initPage(){
        homePage = new HomePage();
    }


    @Test
    @Description("点击'我的'按钮，跳转到我的页面")
    public void testmyBtn(){
        Allure.step("点击'我的'按钮");
        homePage.goMyPage();

        WebElement coompanyEle = homePage.findByText("公司信息");
        try {
            Allure.addAttachment("'我的'页面",new FileInputStream(new File(homePage.ScreenShot("my"))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Assert.assertNotEquals(coompanyEle,null);
    }
}
