package driver;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

public class APPDriver {

    public static AppiumDriver appiumDriver;

    public static AppiumDriver getDriver() {
        if(appiumDriver == null){
            //设置自动化相关参数
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("platformName", "android");
            capabilities.setCapability("deviceName", "test");
            capabilities.setCapability("appPackage", "com.wyapp");
            capabilities.setCapability("appActivity", "com.wyapp.MainActivity");
            capabilities.setCapability("autoGrantPermissions", true);
//            capabilities.setCapability("noReset", true);  //每次都重新打开app,不保留缓存数据
            capabilities.setCapability("unicodeKeyboard",true);
            capabilities.setCapability("resetKeyboard",true);
            try {
                appiumDriver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return appiumDriver;
    }

}
