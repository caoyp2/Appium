package page;

import common.Location;
import io.appium.java_client.AppiumDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import driver.APPDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;

public class BasePage {

    public AppiumDriver appiumDriver;

    BasePage(){
        this.appiumDriver = APPDriver.appiumDriver;
    }

    public WebElement find(String type,String value){
        return appiumDriver.findElement(type,value);
    }
    public WebElement find(By by){
        return appiumDriver.findElement(by);
    }

    public WebElement findByText(String text){
        return find(Location.XPATH,String.format("//*[@text='%s']",text));
    }


    //智能等待
    public WebElement WebElementWait (WebDriver driver, int timeOut, int everytime, final String type, final String value) {
        WebElement webElement = null;
        WebDriverWait driverWait =
                new WebDriverWait(driver,timeOut,everytime);
        try {
            webElement = driverWait.until(new ExpectedCondition<WebElement>() {
                @Override
                public WebElement apply(WebDriver driver) {
                    if(type.equals(Location.XPATH)){
                        return driver.findElement(By.xpath(value));
                    }if(type.equals(Location.ID)){
                        return driver.findElement(By.id(value));
                    }
                    return null;
                }
            });

        } catch (Exception e) {
            System.out.println("未找到元素：" + type + "--" + value);
        }
        return webElement;
    }

    //截图
    public String ScreenShot(String picname){
        File screen = appiumDriver.getScreenshotAs(OutputType.FILE);
        //这里路径需要判断系统是windows还是linux
        String dir = System.getProperty("user.dir") + "\\pic";
        String picdir = dir + "\\" + picname + ".jpg";
        try {
            FileUtils.copyFile(screen,new File(picdir));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return picdir;
    }
}
