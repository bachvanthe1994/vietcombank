package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.AbstractPage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class LogInPageObject extends AbstractPage{

	public LogInPageObject(AndroidDriver<AndroidElement> mappingDriver) {
		driver = mappingDriver;
	}
	private AndroidDriver<AndroidElement> driver;
}
