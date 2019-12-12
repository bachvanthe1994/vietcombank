package pageObjects;

import commons.AbstractPage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import vietcombankUI.TransferMoneyInVCBPageUIs;

public class TransferMoneyInVcbPageObject extends AbstractPage{

	public TransferMoneyInVcbPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	
	public void inputFrequencyNumber(String inputValue) {
		clearText(TransferMoneyInVCBPageUIs.FREQUENCY_NUMBER_INPUT);
		waitForElementVisible(TransferMoneyInVCBPageUIs.FREQUENCY_NUMBER_INPUT);
		sendKeyToElement(TransferMoneyInVCBPageUIs.FREQUENCY_NUMBER_INPUT, inputValue);

	}
}
