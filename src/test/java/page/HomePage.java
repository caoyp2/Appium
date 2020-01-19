package page;

import common.Location;
import org.openqa.selenium.WebElement;

public class HomePage extends BasePage{
    private String[] myData = {Location.XPATH, "//*[@text='我的']"};
    private String[] homeData = {Location.XPATH, "//*[@text='主页']"};
    private String[] newsData = {Location.XPATH, "//*[@text='消息']"};

    public void goMyPage(){
        WebElement myEle = find(myData[0],myData[1]);
        myEle.click();
    }


}
