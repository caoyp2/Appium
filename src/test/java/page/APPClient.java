package page;

import driver.APPDriver;

public class APPClient {

    public static LoginPage startApp(){
        APPDriver.getDriver();
        return new LoginPage();
    }

    public static void stopAPP(){
        APPDriver.getDriver().quit();
        APPDriver.appiumDriver = null;
    }
}
