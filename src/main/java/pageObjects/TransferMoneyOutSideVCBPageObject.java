package pageObjects;

import commons.AbstractPage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class TransferMoneyOutSideVCBPageObject extends AbstractPage {
	public TransferMoneyOutSideVCBPageObject(AppiumDriver<MobileElement> mappingDriver) {
		driver = mappingDriver;
	}

	private AppiumDriver<MobileElement> driver;

	public long canculateAvailableBalances(long surPlus, long money, long transactionFree) {
		return surPlus - money - transactionFree;
	}
}
