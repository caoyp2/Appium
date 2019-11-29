package page;

import common.Location;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import driver.APPDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

    public AppiumDriver appiumDriver;

    BasePage(){
        this.appiumDriver = APPDriver.appiumDriver;
    }

    public WebElement find(String type,String value){
        return appiumDriver.findElement(type,value);
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

}
