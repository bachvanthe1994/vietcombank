package pageObjects;


import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;

import commons.AbstractPage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import vietcombankUI.DynamicPageUIs;
import vietcombankUI.LockCardPageUIs;

public class LockCardPageObject extends AbstractPage{
	
	public LockCardPageObject(AndroidDriver<AndroidElement> mappingDriver) {
		driver = mappingDriver;
	}

	private AndroidDriver<AndroidElement> driver;
	
	public void openCardTypeDropdownList() {
		waitForElementVisible(driver, LockCardPageUIs.CARD_TYPES_DROPDOWN);
		clickToElement(driver, LockCardPageUIs.CARD_TYPES_DROPDOWN);
	}
	
	public void openCardNumberDropdownList(String text,String id) {
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_DROPDOWN_BY_HEADER,text,id);
		clickToElement(driver, DynamicPageUIs.DYNAMIC_DROPDOWN_BY_HEADER,text,id);
	}
	
	public String getFirstCardNumberInDropdownList() {
		waitForElementVisible(driver, LockCardPageUIs.CARD_NUMBER_DROPDOWN_VIEW);
		return getTextInFirstElement(driver, 1, LockCardPageUIs.CARD_NUMBER_DROPDOWN_LIST);
	}
	
	public boolean isCardNumberDisplayedInDropdownList(String expTextVal) {
		waitForElementVisible(driver, LockCardPageUIs.CARD_NUMBER_DROPDOWN_LIST);
		boolean result = false;
		List<AndroidElement> elements = driver.findElementsByXPath(LockCardPageUIs.CARD_NUMBER_DROPDOWN_LIST);
		ArrayList<String> allTextElement = new ArrayList<String>();
		for (AndroidElement element : elements) {
			allTextElement.add(element.getText());
		}
		for(String textElement : allTextElement) {
		if(textElement.contains(expTextVal)) {
			result = true;
		}}
		return result;
	}
	
	public void clickBackToHomePage() {
		waitForElementVisible(driver, LockCardPageUIs.BACK_BUTTON);
		clickToElement(driver, LockCardPageUIs.BACK_BUTTON);
	}
	
}
