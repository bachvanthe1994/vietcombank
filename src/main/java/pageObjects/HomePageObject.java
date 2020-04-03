package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;

import commons.AbstractPage;
import commons.Constants;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import vietcombankUI.DynamicPageUIs;

public class HomePageObject extends AbstractPage {
	private AppiumDriver<MobileElement> driver;

	int longTime = 40;

	public HomePageObject(AppiumDriver<MobileElement> mappingDriver) {
		driver = mappingDriver;
	}

	// Scroll from rigt to left
	public void scrollRightLeft(String locator, String... dynamicValue) {
		Dimension size = driver.manage().window().getSize();
		MobileElement elementFind = driver.findElementByXPath(DynamicPageUIs.DYNAMIC_QUICK_MENU);

		int y = elementFind.getLocation().y;
		int startX = (int) (size.getWidth() * 0.80);
		int endX = (int) (size.getWidth() * 0.30);

		TouchAction touch = new TouchAction(driver);
		List<MobileElement> elementsOne = null;
		try {
			locator = String.format(locator, (Object[]) dynamicValue);
		} catch (Exception e) {
		}
		for (int i = 0; i < 20; i++) {
			boolean checkElementDisplayed = false;
			overRideTimeOut(driver, 2);
			try {
				driver.getPageSource();
				elementsOne = driver.findElements(By.xpath(locator));
				checkElementDisplayed = elementsOne.get(0).isDisplayed();
			} catch (Exception e) {
				checkElementDisplayed = true;
			}

			overRideTimeOut(driver, Constants.LONG_TIME);
			if (elementsOne.size() > 0 && checkElementDisplayed) {
				break;
			} else {
				try {
					touch.longPress(PointOption.point(startX, y)).moveTo(PointOption.point(endX, y)).release().perform();
				} catch (Exception e) {
					System.out.print(e.getMessage());
				}
			}
		}
	}

	// Scroll from LEFT to RIGHT
	public void scrollLeftToRight(String menuScrollDynamic) {
		Dimension size = driver.manage().window().getSize();
		MobileElement elementFind = driver.findElementByXPath(menuScrollDynamic);
		int y = elementFind.getLocation().y;
		int startX = (int) (size.getWidth() * 0.30);
		int endX = (int) (size.getWidth() * 0.80);
		TouchAction touch = new TouchAction(driver);
		try {
			touch.longPress(PointOption.point(startX, y)).moveTo(PointOption.point(endX, y)).release().perform();
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}

	}

	/* SCROLL LeftToTexT */
	public void scrollLeftToText(String dynamicText) {
		scrollRightLeft(DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicText);

	}

	
}