package pageObjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import commons.AbstractPage;
import commons.Constants;
import commons.VerificationFailures;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import vietcombankUI.DynamicPageUIs;
import vietcombankUI.HomePageUIs;

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
		touch.longPress(PointOption.point(startX, y)).moveTo(PointOption.point(endX, y)).release().perform();

	}

	/* SCROLL LeftToTexT */
	public void scrollLeftToText(String dynamicText) {
		scrollRightLeft(HomePageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicText);

	}

	// Click dynamic text View
	public void clickToDynamicTextViewByText(String dynamicText) {
		boolean status = false;
		status = waitForElementVisible(driver, HomePageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicText);
		if (status == true) {
			clickToElement(driver, HomePageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicText);
		}

	}

	// wait
	public boolean waitForElementVisible(String locator) {
		WebDriverWait wait = new WebDriverWait(driver, longTime);
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
		} catch (Exception e) {
			VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
			Reporter.getCurrentTestResult().setThrowable(e);
			System.out.println(e.getMessage());
			String nameofCurrMethod = new Throwable().getStackTrace()[2].getMethodName();
			System.out.println(nameofCurrMethod);
			if (nameofCurrMethod.equalsIgnoreCase("beforeClass")) {
				Assert.assertTrue(false);
			}

			if (!Constants.RUN_CONTINUE_AFTER_STEP_FAIL) {
				Assert.assertTrue(false);
			}

			return false;

		}
		return true;

	}

// get array list
	public List<String> getListElements(String locator, String... dynamicValue) {

		List<String> listIcon = new ArrayList<>();
		locator = String.format(locator, (Object[]) dynamicValue);
		boolean status = waitForElementVisible(locator);
		if (status) {
			List<MobileElement> elements = driver.findElements(By.xpath(locator));
			for (MobileElement element : elements) {
				listIcon.add(element.getText());
			}
		}
		return listIcon;
	}

	// lấy text dynamic text By Text
	public String getDynamicTextView(String dynamicTextValue) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, HomePageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
		if (status == true) {
			text = getTextElement(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);

		}
		return text;
	}

	// Click dynamic button by resource-id
	public void clickToDynamicButtonById(String dynamicID) {
		boolean status = false;
		status = waitForElementVisible(driver, HomePageUIs.DYNAMIC_BUTTON, dynamicID);
		if (status == true) {
			clickToElement(driver, HomePageUIs.DYNAMIC_BUTTON, dynamicID);
		}
	}

	// Click dynamic textView dynamic text
	public void clickToDynamicByText(String dynamicText) {
		boolean status = false;
		status = waitForElementVisible(driver, HomePageUIs.DYNAMIC_TEXT_BY_TEXTVIEW, dynamicText);
		if (status == true) {
			clickToElement(driver, HomePageUIs.DYNAMIC_TEXT_BY_TEXTVIEW, dynamicText);
		}
	}

	// Click dynamic button by resource-id
	public void clickToDynamicButtonOK(String dynamicID) {
		boolean status = false;
		status = waitForElementVisible(driver, HomePageUIs.DYNAMIC_BUTTON_ID, dynamicID);
		if (status == true) {
			clickToElement(driver, HomePageUIs.DYNAMIC_BUTTON_ID, dynamicID);
		}
	}

	// Click dynamic button by resource-id
	public void clickToDynamicButtonImageBack(String dynamicID) {
		boolean status = false;
		status = waitForElementVisible(driver, HomePageUIs.DYNAMIC_BUTTON_IMAGE_BACK, dynamicID);
		if (status == true) {
			clickToElement(driver, HomePageUIs.DYNAMIC_BUTTON_IMAGE_BACK, dynamicID);
		}
	}

	/* SCROLL UP */
	public void scrollUpToText(AppiumDriver<MobileElement> driver, String dynamicText) {
		scrollUp(driver, HomePageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicText);

	}

	// get text theo text
	public String getTextDynamicByID(String... dynamicID) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, HomePageUIs.DYNAMIC_LIST_ICON, dynamicID);
		if (status == true) {
			text = getTextElement(driver, HomePageUIs.DYNAMIC_LIST_ICON, dynamicID);

		}
		return text;

	}

}