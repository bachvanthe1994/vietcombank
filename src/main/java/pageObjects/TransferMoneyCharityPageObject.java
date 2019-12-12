package pageObjects;

import commons.AbstractPage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class TransferMoneyCharityPageObject extends AbstractPage {
	public TransferMoneyCharityPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	
	public long canculateAvailableBalances(long surPlus, long money) {
		return surPlus - money;
	}
}
