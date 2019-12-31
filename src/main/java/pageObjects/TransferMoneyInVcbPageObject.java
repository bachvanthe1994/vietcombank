package pageObjects;

import commons.AbstractPage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import vietcombankUI.TransferMoneyInVCBPageUIs;

public class TransferMoneyInVcbPageObject extends AbstractPage {

	public TransferMoneyInVcbPageObject(AppiumDriver<MobileElement> mappingDriver) {
		driver = mappingDriver;
	}

	private AppiumDriver<MobileElement> driver;

	public void inputFrequencyNumber(String inputValue) {
		clearText(driver, TransferMoneyInVCBPageUIs.FREQUENCY_NUMBER_INPUT);
		waitForElementVisible(driver, TransferMoneyInVCBPageUIs.FREQUENCY_NUMBER_INPUT);
		sendKeyToElement(driver, TransferMoneyInVCBPageUIs.FREQUENCY_NUMBER_INPUT, inputValue);

	}
}
