package testng;

import org.testng.ITestResult;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import static org.testng.Assert.assertTrue;

@Listeners({TestngReport.class})
public class RetryTestDemo {

    @Test(retryAnalyzer = TestngRetry.class)
    public void test1() {
        assertTrue(false);
    }

    @Test()
    public void test2() {
        assertTrue(true);
    }

    @Test()
    public void test3() {
        assertTrue(true);
    }

    @Test()
    public void test4() {
        assertTrue(false);
    }

    @Test()
    public void test5() {
        assertTrue(true);
    }
}
