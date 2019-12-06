package commons;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import pageObjects.DomesticAirTicketPageObject;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.RegisterPageObject;
import pageObjects.TransferMoneyCharityPageObject;
import pageObjects.TransferMoneyInVcbPageObject;
import pageObjects.TransferMoneyObject;
import pageObjects.TransferMoneyOutSideVCBPageObject;


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
	
	public static TransferMoneyObject getTransferMoneyObject(AndroidDriver<AndroidElement> driver) {
		return new TransferMoneyObject(driver);
	}

	public static DomesticAirTicketPageObject getDomesticAirTicketPageObject(AndroidDriver<AndroidElement> driver) {
		return new DomesticAirTicketPageObject(driver);
	}
	

	public static TransferIdentiryPageObject getTransferPageObject(AndroidDriver<AndroidElement> driver) {
		return new TransferIdentiryPageObject(driver);
	}
	
	public static TransferMoneyOutSideVCBPageObject getTransferMoneyOutSideVCBPageObject(AndroidDriver<AndroidElement> driver) {
		return new TransferMoneyOutSideVCBPageObject(driver);
	}
	
	public static TransferMoneyCharityPageObject getTransferMoneyCharityPageObject(AndroidDriver<AndroidElement> driver) {
		return new TransferMoneyCharityPageObject(driver);
	}
}