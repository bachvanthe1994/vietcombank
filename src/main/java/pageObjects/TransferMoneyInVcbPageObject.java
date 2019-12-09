package pageObjects;

import commons.AbstractPage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import vietcombankUI.DynamicPageUIs;
import vietcombankUI.TransferMoneyInVCBPageUIs;

public class TransferMoneyInVcbPageObject extends AbstractPage{

	public TransferMoneyInVcbPageObject(AndroidDriver<AndroidElement> mappingDriver) {
		driver = mappingDriver;
	}
	private AndroidDriver<AndroidElement> driver;
	
	public void inputFrequencyNumber(String inputValue) {
		clearText(driver, TransferMoneyInVCBPageUIs.FREQUENCY_NUMBER_INPUT);
		waitForElementVisible(driver, TransferMoneyInVCBPageUIs.FREQUENCY_NUMBER_INPUT);
		sendKeyToElement(driver, TransferMoneyInVCBPageUIs.FREQUENCY_NUMBER_INPUT, inputValue);

	}
}
