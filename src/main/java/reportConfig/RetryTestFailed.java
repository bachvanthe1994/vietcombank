package reportConfig;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryTestFailed implements IRetryAnalyzer {
	private int retryCount = 0;
	private int maxReryCount = 1;
	
	
	public boolean retry(ITestResult result)
	{
		if(retryCount<maxReryCount) {
			System.out.println("Retry Test Name "+ result.getName()+ "with" + (retryCount+1) +"time(s).");
			retryCount++;
			return true;
		}
		return false;
	}	
	

}
