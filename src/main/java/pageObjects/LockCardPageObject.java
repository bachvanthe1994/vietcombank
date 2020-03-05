package pageObjects;


import java.util.ArrayList;
import java.util.List;

import commons.AbstractPage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import vietcombankUI.LockCardPageUIs;

public class LockCardPageObject extends AbstractPage{
	
	public LockCardPageObject(AppiumDriver<MobileElement> mappingDriver) {
		driver = mappingDriver;
	}

	private AppiumDriver<MobileElement> driver;
	
	public boolean isCardNumberDisplayedInDropdownList(String expTextVal) {
		waitForElementVisible(driver, LockCardPageUIs.CARD_NUMBER_DROPDOWN_LIST);
		boolean result = false;
		List<MobileElement> elements = driver.findElementsByXPath(LockCardPageUIs.CARD_NUMBER_DROPDOWN_LIST);
		ArrayList<String> allTextElement = new ArrayList<String>();
		for (MobileElement element : elements) {
			allTextElement.add(element.getText());
		}
		for(String textElement : allTextElement) {
		if(textElement.contains(expTextVal)) {
			result = true;
		}}
		return result;
	}
	public void clickToConfirmCheckBox() {
		waitForElementVisible(driver, LockCardPageUIs.CONFIRM_CHECKBOX);
		clickToElement(driver, LockCardPageUIs.CONFIRM_CHECKBOX);
	}
	
	public void clickBackToHomePage() {
		waitForElementVisible(driver, LockCardPageUIs.BACK_BUTTON);
		clickToElement(driver, LockCardPageUIs.BACK_BUTTON);
	}
	
}
