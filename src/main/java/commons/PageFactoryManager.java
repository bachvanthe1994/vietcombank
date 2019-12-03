package commons;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import pageObjects.AccountPageObject;
import pageObjects.DomesticAirTicketPageObject;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.RegisterPageObject;

public class PageFactoryManager {

	public static LogInPageObject getLoginPageObject(AndroidDriver<AndroidElement> driver) {
		return new LogInPageObject(driver);
	}

	public static HomePageObject getHomePageObject(AndroidDriver<AndroidElement> driver) {
		return new HomePageObject(driver);
	}

	public static RegisterPageObject getRegisterPageObject(AndroidDriver<AndroidElement> driver) {
		return new RegisterPageObject(driver);
	}

	public static AccountPageObject getAccountPageObject(AndroidDriver<AndroidElement> driver) {
		return new AccountPageObject(driver);
	}

	public static DomesticAirTicketPageObject getDomesticAirTicketPageObject(AndroidDriver<AndroidElement> driver) {
		return new DomesticAirTicketPageObject(driver);
	}
	
}