package commons;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.LuckyGiftPageObject;
import pageObjects.RegisterPageObject;
import pageObjects.SetupContactPageObject;
import pageObjects.TransactionReportPageObject;
import pageObjects.TransferIdentiryPageObject;
import pageObjects.TransferMoneyCharityPageObject;
import pageObjects.TransferMoneyInVcbPageObject;
import pageObjects.TransferMoneyObject;
import pageObjects.TransferMoneyOutSideVCBPageObject;
import pageObjects.TransferMoneyStatusPageObject;

public class PageFactoryManager {

	public static LogInPageObject getLoginPageObject(AppiumDriver<MobileElement> driver) {
		return new LogInPageObject(driver);
	}

	public static HomePageObject getHomePageObject(AppiumDriver<MobileElement> driver) {
		return new HomePageObject(driver);
	}

	public static RegisterPageObject getRegisterPageObject(AppiumDriver<MobileElement> driver) {
		return new RegisterPageObject(driver);
	}

	public static TransferMoneyInVcbPageObject getTransferMoneyInVcbPageObject(AppiumDriver<MobileElement> driver) {
		return new TransferMoneyInVcbPageObject(driver);
	}

	public static TransferMoneyObject getTransferMoneyObject(AppiumDriver<MobileElement> driver) {
		return new TransferMoneyObject(driver);
	}

	public static TransferIdentiryPageObject getTransferIdentiryPageObject(AppiumDriver<MobileElement> driver) {
		return new TransferIdentiryPageObject(driver);
	}

	public static TransferMoneyOutSideVCBPageObject getTransferMoneyOutSideVCBPageObject(AppiumDriver<MobileElement> driver) {
		return new TransferMoneyOutSideVCBPageObject(driver);
	}

	public static TransferMoneyCharityPageObject getTransferMoneyCharityPageObject(AppiumDriver<MobileElement> driver) {
		return new TransferMoneyCharityPageObject(driver);
	}

	public static TransactionReportPageObject getTransactionReportPageObject(AppiumDriver<MobileElement> driver) {
		return new TransactionReportPageObject(driver);
	}

	public static TransferMoneyStatusPageObject getTransferMoneyStatusPageObject(AppiumDriver<MobileElement> driver) {
		return new TransferMoneyStatusPageObject(driver);
	}

	public static LuckyGiftPageObject getLuckyGiftPageObject(AppiumDriver<MobileElement> driver) {
		return new LuckyGiftPageObject(driver);
	}

	public static SetupContactPageObject getSetupContactPageObject(AppiumDriver<MobileElement> driver) {
		return new SetupContactPageObject(driver);

	}

}