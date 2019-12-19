package pageObjects;

import commons.AbstractPage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import vietcombankUI.DynamicPageUIs;
import vietcombankUI.TransferMoneyCharityPageUIs;

public class TransferMoneyCharityPageObject extends AbstractPage {
	public TransferMoneyCharityPageObject(AndroidDriver<AndroidElement> mappingDriver) {
		driver = mappingDriver;
	}
	private AndroidDriver<AndroidElement> driver;
	
	public long canculateAvailableBalances(long surPlus, long money) {
		return surPlus - money;
	}
	
	public String getTextCurrencyCharity() {
		waitForElementVisible(driver, TransferMoneyCharityPageUIs.TEXT_VIEW_CURRENCY_CHARITY);
		return getTextElement(driver, TransferMoneyCharityPageUIs.TEXT_VIEW_CURRENCY_CHARITY);
	}
}
