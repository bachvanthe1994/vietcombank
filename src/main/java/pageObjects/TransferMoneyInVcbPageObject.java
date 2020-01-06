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
	
	public void chooseDateNextYearInDatePickerByID(String dynamicIDValue, String yearNow) {
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_VIEW_BY_ID, dynamicIDValue);
		
		List<String> listDate = getContentDescInListElements(driver, DynamicPageUIs.DYNAMIC_VIEW_BY_ID, dynamicIDValue);
		String firstDate = listDate.get(0);
		int count = 12;
		while (!firstDate.contains(String.valueOf(Integer.parseInt(yearNow + 1)))){
			clickToDynamicImageButtonByID(driver, "android:id/next");
			count --;
			listDate = getContentDescInListElements(driver, DynamicPageUIs.DYNAMIC_VIEW_BY_ID, dynamicIDValue);
			firstDate = listDate.get(0);
			if (count <= 0) {
				break;
			}
		}
		
	}
	
	public boolean checkDateNextYearEnable(String dynamicIDValue, int index) {
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_VIEW_BY_ID, dynamicIDValue);
		
		List<String> listDate = getEnableInListElements(driver, DynamicPageUIs.DYNAMIC_VIEW_BY_ID, dynamicIDValue);
		boolean firstDate = Boolean.parseBoolean(listDate.get(index));

		return firstDate;
		
	}
}
