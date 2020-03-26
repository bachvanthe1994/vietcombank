package pageObjects.shopping_online;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;

import commons.AbstractPage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import vietcombankUI.shopping_online_UI.ShoppingOnlinePageUIs;

public class ShoppingOnlinePageObject extends AbstractPage {

	public ShoppingOnlinePageObject(AppiumDriver<MobileElement> mappingDriver) {
		driver = mappingDriver;
	}

	private AppiumDriver<MobileElement> driver;

	/*
	 * // Click vao san pham public void clickToDynamicView(String dynamicTextValue)
	 * { boolean status = false; scrollIDown(driver,
	 * ShoppingOnlinePageUIs.DYNAMIC_VIEW_LIST, dynamicTextValue); status =
	 * waitForElementVisible(driver, ShoppingOnlinePageUIs.DYNAMIC_VIEW_LIST,
	 * dynamicTextValue); if (status == true) { clickToElement(driver,
	 * ShoppingOnlinePageUIs.DYNAMIC_VIEW_LIST, dynamicTextValue); } }
	 */

	public List<String> clickChooseOrrder(int numberOrder, String textOrder) {
			List<String> listOrder = new ArrayList<>();

			String locator = String.format(ShoppingOnlinePageUIs.DYNAMIC_VIEW_LIST,textOrder );
			boolean status = waitForElementVisible(driver, ShoppingOnlinePageUIs.DYNAMIC_VIEW_LIST,textOrder);
			if (status) {
				List<MobileElement> elements = driver.findElements(By.xpath(locator));
				for (MobileElement element : elements) {
					//File imageFile = ((TakesScreenshot) element).getScreenshotAs(OutputType.FILE);
					try {
						
							element.click();

						
							numberOrder--;
						

						if (numberOrder <= 0) {
							break;
						}

					} catch (Exception e) {

					}
				
				}
			}
			return listOrder;

			}
}
