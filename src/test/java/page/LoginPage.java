package page;

import common.Location;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {

        private String[] usernameData = {Location.XPATH, "//android.widget.EditText[@text='用户名']"};
        private String[] passwordData = {Location.XPATH,"//android.widget.EditText[@text='密码']"};
        private String[] loginBtn = {Location.XPATH,"//android.widget.TextView[@text='登 录']"};
        private WebElement usernameEle = null;
        private WebElement passwordEle = null;

        public void loginPassword(String username, String password){
            usernameEle = find(usernameData[0], usernameData[1]);
            passwordEle = find(passwordData[0], passwordData[1]);
            WebElement loginEle = find(loginBtn[0],loginBtn[1]);
            usernameEle.sendKeys(username);
            passwordEle.sendKeys(password);
            loginEle.click();
        }

        public Boolean getTip(String type,String msg){
            Boolean flag = false;
            WebElement tip = WebElementWait(appiumDriver,5,1,type,msg);
            if (tip != null){
                flag = true;
            }
            return flag;
        }

        //在同一页面中反复操作时，需清理页面数据
        public void pageInit(){
            usernameEle.clear();
            passwordEle.clear();
        }
}
