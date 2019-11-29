package page;

import driver.APPDriver;

public class APPClient {

    public static LoginPage startApp(){
        APPDriver.getDriver();
        return new LoginPage();
    }
}
