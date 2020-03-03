package pageObjects;

import java.util.List;

import commons.AbstractPage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import vietcombankUI.DynamicPageUIs;
import vietcombankUI.TransferMoneyInVCBPageUIs;

public class TransferMoneyInVcbPageObject extends AbstractPage {

	public TransferMoneyInVcbPageObject(AppiumDriver<MobileElement> mappingDriver) {
		driver = mappingDriver;
	}

	private AppiumDriver<MobileElement> driver;

	public void inputFrequencyNumber(String inputValue) {

		boolean status = false;
		status = waitForElementVisible(driver, TransferMoneyInVCBPageUIs.FREQUENCY_NUMBER_INPUT);
		if (status == true) {
			clearText(driver, TransferMoneyInVCBPageUIs.FREQUENCY_NUMBER_INPUT);
			sendKeyToElement(driver, TransferMoneyInVCBPageUIs.FREQUENCY_NUMBER_INPUT, inputValue);
		}
	}

	public void chooseDateNextYearInDatePicker(String dynamicIDValue, String year) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_VIEW_BY_ID, dynamicIDValue);
		if (status == true) {
			clickToDynamicButtonLinkOrLinkText(driver, String.valueOf(Integer.parseInt(year) - 1));
			clickToDynamicButtonLinkOrLinkText(driver, year);

		}

	}

	public boolean checkDateNextYearEnable(String dynamicIDValue, int index) {
		boolean status = false;
		boolean firstDate = false;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_VIEW_BY_ID, dynamicIDValue);
		if (status == true) {
			List<String> listDate = getEnableInListElements(driver, DynamicPageUIs.DYNAMIC_VIEW_BY_ID, dynamicIDValue);

			if (index < 0) {
				return false;
			}

			if (index >= listDate.size()) {
				return false;
			}

			firstDate = Boolean.parseBoolean(listDate.get(index));
		}
		return firstDate;

	}
}
