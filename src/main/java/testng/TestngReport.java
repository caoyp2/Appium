package testng;

import org.testng.*;
import org.testng.xml.XmlSuite;

import java.util.List;
import java.util.Map;

public class TestngReport implements IReporter {
    @Override
    public void generateReport(List<XmlSuite> list, List<ISuite> list1, String s) {
        int successCount=0;
        int failCount=0;
        int skipCount=0;

        for(ISuite iSuite : list1){
            Map<String, ISuiteResult> results = iSuite.getResults();
            for (ISuiteResult r : results.values()) {
                ITestContext context = r.getTestContext();
                IResultMap failedTests = context.getFailedTests();
                IResultMap passedTests = context.getPassedTests();
                failCount = failedTests.size();
                successCount=passedTests.size();
                System.out.println("failCount:" + failCount);
                System.out.println("successCount:" + successCount);

            }
        }
    }
}
