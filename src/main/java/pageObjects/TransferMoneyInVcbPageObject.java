package pageObjects;

import java.util.List;

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
	
	public void chooseDateNextYearInDatePicker(String dynamicIDValue, String year) {
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_VIEW_BY_ID, dynamicIDValue);
		clickToDynamicButtonLinkOrLinkText(driver, String.valueOf(Integer.parseInt(year) - 1));
		clickToDynamicButtonLinkOrLinkText(driver, year);
		
	}
	
	public boolean checkDateNextYearEnable(String dynamicIDValue, int index) {
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_VIEW_BY_ID, dynamicIDValue);
		
		List<String> listDate = getEnableInListElements(driver, DynamicPageUIs.DYNAMIC_VIEW_BY_ID, dynamicIDValue);
		
		if (index < 0) {
			return false;
		}
		
		if (index >= listDate.size()) {
			return false;
		}
		
		boolean firstDate = Boolean.parseBoolean(listDate.get(index));

		return firstDate;
		
	}
}
