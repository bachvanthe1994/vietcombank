package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.AbstractPage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class DomesticAirTicketPageObject extends AbstractPage{

	public DomesticAirTicketPageObject(AndroidDriver<AndroidElement> mappingDriver) {
		driver = mappingDriver;
	}
	private AndroidDriver<AndroidElement> driver;
}
