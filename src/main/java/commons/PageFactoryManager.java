package commons;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import pageObjects.TransferMoneyInVcbPageObject;
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

	public static TransferMoneyInVcbPageObject getTransferMoneyInVcbPageObject(AndroidDriver<AndroidElement> driver) {
		return new TransferMoneyInVcbPageObject(driver);
	}


}