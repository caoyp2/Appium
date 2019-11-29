package testcase;

import common.Location;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import driver.APPDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class TestAppium {

    public static void main(String[] args) throws MalformedURLException {
        //设置自动化相关参数
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
        capabilities.setCapability("platformName","android");
        capabilities.setCapability("deviceName","test");
        capabilities.setCapability("appPackage","com.xueqiu.android");
        capabilities.setCapability("appActivity",".view.WelcomeActivityAlias");
        capabilities.setCapability("autoGrantPermissions","true");

        AndroidDriver driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"),capabilities);
        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        driver.findElementByXPath("//*[@text='私募']").click();

        driver.quit();
    }

    @Test
    public void testH5AndNative() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
        capabilities.setCapability("noReset", true);
        capabilities.setCapability("platformName","android");
        capabilities.setCapability("deviceName","test");
        capabilities.setCapability("appPackage","com.android.chrome");
        capabilities.setCapability("appActivity","org.chromium.chrome.browser.ChromeTabbedActivity");
//        capabilities.setCapability("appPackage", "com.wyapp");
//        capabilities.setCapability("appActivity", "com.wyapp.MainActivity");
        capabilities.setCapability("autoGrantPermissions",true);

        AndroidDriver driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"),capabilities);
        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
//        testcase.driver.findElement(By.xpath("//*[contains(@name,'search')]")).click();
//        Set<String>  contexts = testcase.driver.getContextHandles();
//        for(String s: contexts){
//            System.out.println(s);
//        }
        driver.quit();
    }

    @Test
    public void testH5() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "Chrome");
        capabilities.setCapability("noReset", true);
        capabilities.setCapability("platformName","android");
        capabilities.setCapability("deviceName","test");
        capabilities.setCapability("autoGrantPermissions",true);

        AndroidDriver driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"),capabilities);
        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        driver.get("http://www.baidu.com");
        driver.findElement(By.id("index-kw")).sendKeys("testfan");
        driver.findElement(By.id("index-bn")).click();
        driver.quit();
    }

    @Test
    public void testDriver() throws InterruptedException {
        AndroidDriver driver = (AndroidDriver) APPDriver.getDriver();
        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
//        WebElement username = driver.findElement("xpath","//*[@text='用户名']");
        WebElement webElement = null;
        WebDriverWait driverWait =
                new WebDriverWait(driver,10,2);
        try {
            webElement = driverWait.until(new ExpectedCondition<WebElement>() {
                @Override
                public WebElement apply(WebDriver driver) {
                    System.out.println("xxxxxxxxxxxxx");
                    return driver.findElement(By.xpath("//*[@text='用户名']"));
                }
            });

        } catch (Exception e) {
            System.out.println("未找到瓷元素");
        }
        if(webElement != null) {
            webElement.sendKeys("cyp");
        }
        driver.quit();
    }
}