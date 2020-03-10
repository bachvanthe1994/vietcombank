package pageObjects;

import java.util.List;

import org.openqa.selenium.By;

import commons.AbstractPage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import vietcombankUI.DynamicPageUIs;



public class AutoSavingPageObject extends AbstractPage {


	public AutoSavingPageObject(AppiumDriver<MobileElement> mappingDriver) {
		driver = mappingDriver;
	}
	private AppiumDriver<MobileElement> driver;
	
	public void clickToFirstOptionInDropdown(String... dynamicID) {
		String locator = String.format(DynamicPageUIs.DYNAMIC_TEXT_IN_POPUP, (Object[]) dynamicID);
		List<MobileElement> elements = driver.findElements(By.xpath(locator));
		waitForElementVisible(driver,locator);
		elements.get(0).click();
	}
	
	public String getFirstOptionDataInDropdown(String... dynamicID) {
		String locator = String.format(DynamicPageUIs.DYNAMIC_TEXT_IN_POPUP, (Object[]) dynamicID);
		List<MobileElement> element = driver.findElements(By.xpath(locator));
		return element.get(0).getText();
	}

}
