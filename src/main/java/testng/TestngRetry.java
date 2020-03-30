package testng;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * 实现失败重试的接口
 */
public class TestngRetry implements IRetryAnalyzer {

    private int count = 1;
    private int maxRetryCount = 3;

    public boolean retry(ITestResult result) {
        if(count <= maxRetryCount){
            result.setAttribute("RETRY", new Integer(count));
            count++;
            return true;
        }
        return false;
    }
}
