package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.AbstractPage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class AccountPageObject extends AbstractPage{

	public AccountPageObject(AndroidDriver<AndroidElement> mappingDriver) {
		driver = mappingDriver;
	}
	private AndroidDriver<AndroidElement> driver;
}
