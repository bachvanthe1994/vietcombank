package pageObjects;

import commons.AbstractPage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class TransferMoneyOutSideVCBPageObject extends AbstractPage{
	
	public TransferMoneyOutSideVCBPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	
	public long canculateAvailableBalances(long surPlus, long money, long transactionFree) {
		return surPlus - money - transactionFree;
	}
}
