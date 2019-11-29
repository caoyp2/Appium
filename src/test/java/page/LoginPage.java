package page;

import common.Location;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {

        String[] usernameData = {Location.XPATH, "//*[@text='用户名']"};
        String[] passwordData = {Location.XPATH,"//*[@text='密码']"};
        String[] loginBtn = {Location.XPATH,"//android.widget.TextView[@text='登 录']"};

        public void loginPassword(String username, String password){
            WebElement usernameEle = appiumDriver.findElement(usernameData[0], usernameData[1]);
            WebElement passwordEle = appiumDriver.findElement(passwordData[0], passwordData[1]);
            WebElement loginEle = appiumDriver.findElement(loginBtn[0],loginBtn[1]);
            usernameEle.sendKeys(username);
            passwordEle.sendKeys(password);
            loginEle.click();
        }
}
