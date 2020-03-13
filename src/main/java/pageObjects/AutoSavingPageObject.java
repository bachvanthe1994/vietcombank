package pageObjects;

import java.util.List;

import commons.AbstractPage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import vietcombankUI.DynamicPageUIs;



public class AutoSavingPageObject extends AbstractPage {


	public AutoSavingPageObject(AppiumDriver<MobileElement> mappingDriver) {
		driver = mappingDriver;
	}
	private AppiumDriver<MobileElement> driver;

	public String getFirstOptionInList(String dynamicValue) {
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_TEXT_BY_ID, dynamicValue);
		return getTextInFirstElement(driver, 0, DynamicPageUIs.DYNAMIC_TEXT_BY_ID, dynamicValue);
	}
	
	public String getFirstElementContainsText(String expText,String... dynamicValue) {
		String locator = String.format(DynamicPageUIs.DYNAMIC_TEXT_IN_POPUP, (Object[]) dynamicValue);
		String text="";
		waitForElementVisible(driver, locator);
		List<MobileElement> elements = driver.findElementsByXPath(locator);
		for (MobileElement element : elements) {
		   if(element.getText().contains(expText) == true) {
			   text = element.getText();
		   }
		}
		return text;
	}

}
