package reportConfig;

import org.openqa.selenium.OutputType;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.relevantcodes.extentreports.LogStatus;

import commons.Base;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class ExtentTestListener extends Base implements ITestListener {

	@Override
	public void onStart(ITestContext context) {
		System.out.println("---------- " + context.getName() + " STARTED test ----------");
		context.setAttribute("WebDriver", this.getDriver());
	}

	@Override
	public void onFinish(ITestContext context) {
		System.out.println("---------- " + context.getName() + " FINISHED test ----------");
		ExtentTestManager.endTest();
		ExtentManager.getReporter().flush();
	}

	@Override
	public void onTestStart(ITestResult result) {
		System.out.println("---------- " + result.getName() + " STARTED test ----------");
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		System.out.println("---------- " + result.getName() + " SUCCESS test ----------");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Test passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		System.out.println("---------- " + result.getName() + " FAILED test ----------");

		Object testClass = result.getInstance();
		AppiumDriver<MobileElement> androidDriver = ((Base) testClass).getDriver();
		String base64Screenshot = "data:image/png;base64," + androidDriver.getScreenshotAs(OutputType.BASE64);
		ExtentTestManager.getTest().log(LogStatus.FAIL, "Test Failed", ExtentTestManager.getTest().addBase64ScreenShot(base64Screenshot));
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		System.out.println("---------- " + result.getName() + " SKIPPED test ----------");
		ExtentTestManager.getTest().log(LogStatus.SKIP, "Test Skipped");
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		System.out.println("---------- " + result.getName() + " FAILED WITH SUCCESS PERCENTAGE test ----------");
	}

}