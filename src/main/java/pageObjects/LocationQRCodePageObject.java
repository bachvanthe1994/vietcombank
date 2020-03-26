package pageObjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;

import commons.AbstractPage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import vietcombankUI.DynamicPageUIs;
import vietcombankUI.sdk.trainTicket.TrainTicketPageUIs;
import vietcombankUI.shopping_online_UI.ShoppingOnlinePageUIs;



public class LocationQRCodePageObject extends AbstractPage {


	public LocationQRCodePageObject(AppiumDriver<MobileElement> mappingDriver) {
		driver = mappingDriver;
	}
	private AppiumDriver<MobileElement> driver;
	
	public List<String> clickChooseLocator(int numberLocator) {
		List<String> listLocator = new ArrayList<>();

		String locator = String.format(DynamicPageUIs.DYNAMIC_VIEWGROUP_TEXT,"Nổi bật" );
		boolean status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_VIEWGROUP_TEXT,"Nổi bật");
		if (status) {
			List<MobileElement> elements = driver.findElements(By.xpath(locator));
			for (MobileElement element : elements) {
				try {
					
						element.click();

					
						numberLocator--;
					

					if (numberLocator <= 0) {
						break;
					}

				} catch (Exception e) {

				}
			
			}
		}
		return listLocator;

		}
	
	public List<String> getListOfSuggestedMoneyOrListText1(AppiumDriver<MobileElement> driver, String dynamictext) {
		boolean status = false;
		List<String> text = null;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_VIEWGROUP_TEXT, dynamictext);
		if (status == true) {
			text = getTextInListElements(driver, DynamicPageUIs.DYNAMIC_VIEWGROUP_TEXT, dynamictext);
		}
		return text;

	}
	
}