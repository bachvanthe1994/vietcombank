
package commons;

import static io.appium.java_client.touch.LongPressOptions.longPressOptions;
import static io.appium.java_client.touch.TapOptions.tapOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;
import static java.time.Duration.ofSeconds;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.HasOnScreenKeyboard;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.offset.PointOption;
import model.TransferInVCBRecurrent;
import vietcombankUI.DynamicPageUIs;
import vietcombankUI.TransferMoneyInVCBPageUIs;
import vietcombankUI.sdk.trainTicket.TrainTicketPageUIs;

public class AbstractPage {
	int longTime = 30;
	int shortTime = 5;
	long longTime1 = 30;
	long shortTime1 = 5;

	public String getPageSource(AppiumDriver<MobileElement> driver) {
		sleep(driver, 3000);
		String text = driver.getPageSource();
		return text;

	}

	public void TabtoElement(AppiumDriver<MobileElement> driver, String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		TouchAction touch = new TouchAction(driver);
		touch.tap(tapOptions().withElement(element(element))).perform();

	}

	public boolean isControlForcus(AppiumDriver<MobileElement> driver, String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		WebElement element = driver.findElement(By.xpath(locator));
		if (element.getAttribute("selected").equalsIgnoreCase("true")) {
			return true;
		} else {
			return false;
		}
	}

	public void navigateBack(AppiumDriver<MobileElement> driver) {
		driver.navigate().back();
	}

	public void navigatForward(AppiumDriver<MobileElement> driver) {
		driver.navigate().forward();
		;
	}

	public void LongPressToElement(AppiumDriver<MobileElement> driver, String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		TouchAction touch = new TouchAction(driver);
		touch.longPress(longPressOptions().withElement(element(element)).withDuration(ofSeconds(2))).release().perform();

	}

	public void SwipingFromOneElementToOtherElement(AppiumDriver<MobileElement> driver, String locator1, String locator2) {
		WebElement element1 = driver.findElement(By.xpath(locator1));
		WebElement element2 = driver.findElement(By.xpath(locator2));
		TouchAction touch = new TouchAction(driver);
		touch.longPress(longPressOptions().withElement(element(element1)).withDuration(ofSeconds(2))).moveTo(element(element2)).release().perform();

	}

	public void DragAndDrop(AppiumDriver<MobileElement> driver, String locator1, String locator2) {
		WebElement element1 = driver.findElement(By.xpath(locator1));
		WebElement element2 = driver.findElement(By.xpath(locator2));
		TouchAction touch = new TouchAction(driver);
		touch.longPress(longPressOptions().withElement(element(element1))).moveTo(element(element2)).release().perform();
	}

	public void BackKeyCode(AndroidDriver<AndroidElement> driver) {
		driver.pressKey(new KeyEvent(AndroidKey.BACK));

	}

	public void hideKeyBoard(AppiumDriver<MobileElement> driver) {
		driver.hideKeyboard();

	}

	public void HomeKeyCode(AndroidDriver<AndroidElement> driver) {
		driver.pressKey(new KeyEvent(AndroidKey.HOME));

	}

	public void EnterKeyCode(AndroidDriver<AndroidElement> driver) {
		driver.pressKey(new KeyEvent(AndroidKey.ENTER));

	}

	public String getToastMessage(AppiumDriver<MobileElement> driver) {
		String toastMessage = driver.findElement(By.xpath("//android.widget.Toast[1]")).getAttribute("name");
		return toastMessage;
	}

	public double convertToDouble(String value) {
		double value1 = Double.parseDouble(value.substring(1));
		return value1;
	}

	public void SwitchToContext(AppiumDriver<MobileElement> driver, String webViewName) {
		Set<String> contexts = driver.getContextHandles();
		System.out.println(contexts);
		driver.context(webViewName);
	}

//	public void scrollToText(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
//		try {
//			driver.findElementByAndroidUIAutomator("new UiScrollable(" + "new UiSelector().scrollable(true)).scrollIntoView(" + "new UiSelector().textContains(\"" + dynamicTextValue + "\"));");
//		} catch (Exception e) {
//			System.out.print(e.getMessage());
//		}
//	}
//
//	public void scrollToElementByID(AppiumDriver<MobileElement> driver, String dynamicID) {
//		try {
//			driver.findElementByAndroidUIAutomator("new UiScrollable(" + "new UiSelector().scrollable(true)).scrollIntoView(" + "new UiSelector().resourceId(\"" + dynamicID + "\"));");
//		} catch (Exception e) {
//			System.out.print(e.getMessage());
//		}
//	}

	public void scrollIDown(AppiumDriver<MobileElement> driver, String locator) {
		Dimension size = driver.manage().window().getSize();
		int x = size.getWidth() / 2;
		int startY = (int) (size.getHeight() * 0.80);
		int endY = (int) (size.getHeight() * 0.30);
		TouchAction touch = new TouchAction(driver);

		for (int i = 0; i < 20; i++) {
			overRideTimeOut(driver, 2);
			List<MobileElement> elementsOne = driver.findElements(By.xpath(locator));
			overRideTimeOut(driver, Constants.LONG_TIME);
			if (elementsOne.size() > 0 && elementsOne.get(0).isDisplayed()) {
				break;
			} else {
				try {
					touch.longPress(PointOption.point(x, startY)).moveTo(PointOption.point(x, endY)).release().perform();
				} catch (Exception e) {
					System.out.print(e.getMessage());
				}
			}
		}
	}

	public void scrollUp(AppiumDriver<MobileElement> driver, String locator) {
		Dimension size = driver.manage().window().getSize();
		int x = size.getWidth() / 2;
		int startY = (int) (size.getHeight() * 0.30);
		int endY = (int) (size.getHeight() * 0.80);
		TouchAction touch = new TouchAction(driver);

		for (int i = 0; i < 20; i++) {
			overRideTimeOut(driver, 2);
			List<MobileElement> elementsOne = driver.findElements(By.xpath(locator));
			overRideTimeOut(driver, Constants.LONG_TIME);
			if (elementsOne.size() > 0 && elementsOne.get(0).isDisplayed()) {
				break;
			} else {
				try {
					touch.longPress(PointOption.point(x, startY)).moveTo(PointOption.point(x, endY)).release().perform();
				} catch (Exception e) {
					System.out.print(e.getMessage());
				}
			}
		}
	}

	public void scrollIDown(AppiumDriver<MobileElement> driver, String locator, String... dynamicValue) {
		Dimension size = driver.manage().window().getSize();
		int x = size.getWidth() / 2;
		int startY = (int) (size.getHeight() * 0.80);
		int endY = (int) (size.getHeight() * 0.30);
		TouchAction touch = new TouchAction(driver);
		locator = String.format(locator, (Object[]) dynamicValue);
		for (int i = 0; i < 30; i++) {
			locator = String.format(locator, (Object[]) dynamicValue);
			overRideTimeOut(driver, 2);
			List<MobileElement> elementsOne = driver.findElements(By.xpath(locator));
			overRideTimeOut(driver, Constants.LONG_TIME);
			if (elementsOne.size() > 0 && elementsOne.get(0).isDisplayed()) {
				break;
			} else {
				try {
					touch.longPress(PointOption.point(x, startY)).moveTo(PointOption.point(x, endY)).release().perform();
				} catch (Exception e) {

					System.out.println(e.getMessage());
					overRideTimeOut(driver, longTime);
				}
			}
		}
	}

	public void sendKeyToElement(AppiumDriver<MobileElement> driver, String locator, String value, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		WebElement element = driver.findElement(By.xpath(locator));
		element.clear();
		element.sendKeys(value);

	}

	public void setValueToElement(AppiumDriver<MobileElement> driver, String locator, String value, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		MobileElement element1 = driver.findElement(By.xpath(locator));
		element1.clear();
		driver.getKeyboard().sendKeys(value);
	}

	public String getTextInOneOFElement(AppiumDriver<MobileElement> driver, int index, String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		List<MobileElement> element = driver.findElements(By.xpath(locator));
		return element.get(index).getText();
	}

	public String getAttributeValue(AppiumDriver<MobileElement> driver, String locator, String attribute, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		WebElement element = driver.findElement(By.xpath(locator));
		return element.getAttribute(attribute);

	}

	public String getTextElement(AppiumDriver<MobileElement> driver, String locator, String... dynamicValue) {

		locator = String.format(locator, (Object[]) dynamicValue);
		WebElement element = driver.findElement(By.xpath(locator));
		return element.getText();
	}

	public String getTextInFirstElement(AppiumDriver<MobileElement> driver, int index, String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		List<MobileElement> element = driver.findElements(By.xpath(locator));
		return element.get(index).getText();
	}

	public List<String> getTextInListElements(AppiumDriver<MobileElement> driver, String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		List<MobileElement> listElements = driver.findElements(By.xpath(locator));
		List<String> listTextView = new ArrayList<String>();
		for (MobileElement element : listElements) {
			listTextView.add(element.getText());
		}
		return listTextView;
	}

	public List<String> getContentDescInListElements(AppiumDriver<MobileElement> driver, String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		List<MobileElement> listElements = driver.findElements(By.xpath(locator));
		List<String> listTextView = new ArrayList<String>();
		for (MobileElement element : listElements) {
			listTextView.add(element.getAttribute("content-desc"));
		}
		return listTextView;
	}

	public List<String> getEnableInListElements(AppiumDriver<MobileElement> driver, String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		List<MobileElement> listElements = driver.findElements(By.xpath(locator));
		List<String> listTextView = new ArrayList<String>();
		for (MobileElement element : listElements) {
			listTextView.add(element.getAttribute("enabled"));
		}
		return listTextView;
	}

	public List<String> getSelectedInListElements(AppiumDriver<MobileElement> driver, String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		List<MobileElement> listElements = driver.findElements(By.xpath(locator));
		List<String> listTextView = new ArrayList<String>();
		for (MobileElement element : listElements) {
			listTextView.add(element.getAttribute("selected"));
		}
		return listTextView;
	}

	public boolean isControlDisplayed(AppiumDriver<MobileElement> driver, String locator) {
		try {
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			WebElement element = driver.findElement(By.xpath(locator));
			boolean status = element.isDisplayed();
			return status;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	public boolean isKeyBoardDisplayed(AppiumDriver<MobileElement> driver) {
		return ((HasOnScreenKeyboard) driver).isKeyboardShown();
	}

	public boolean isControlUnDisplayed(AppiumDriver<MobileElement> driver, String locator, String... dynamicValue) {
		overRideTimeOut(driver, shortTime);
		locator = String.format(locator, (Object[]) dynamicValue);
		List<MobileElement> elements = driver.findElements(By.xpath(locator));
		if (elements.size() > 0 && elements.get(0).isDisplayed()) {
			overRideTimeOut(driver, longTime);
			return false;
		} else {
			overRideTimeOut(driver, longTime);
			return true;

		}
	}

	public void overRideTimeOut(AppiumDriver<MobileElement> driver, long time) {
		driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
	}

	public boolean isControlEnabled(AppiumDriver<MobileElement> driver, String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		if (element.getAttribute("enabled").equalsIgnoreCase("true")) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isControlEnabled(AppiumDriver<MobileElement> driver, String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		WebElement element = driver.findElement(By.xpath(locator));
		if (element.getAttribute("enabled").equalsIgnoreCase("true")) {
			return true;
		} else {
			return false;
		}
	}

	public void sleep(AppiumDriver<MobileElement> driver, long milisecond) {
		try {
			Thread.sleep(milisecond);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void clickToElement(AppiumDriver<MobileElement> driver, String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		element.click();

	}

	public void clickToElement(AppiumDriver<MobileElement> driver, String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		WebElement element = driver.findElement(By.xpath(locator));
		element.click();

	}

	public void clickToOneOfElement(AppiumDriver<MobileElement> driver, int elementIndex, String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		List<MobileElement> element = driver.findElements(By.xpath(locator));
		element.get(elementIndex).click();

	}

	public void clickToOneOfElement(AppiumDriver<MobileElement> driver, int elementIndex, String locator) {
		List<MobileElement> element = driver.findElements(By.xpath(locator));
		element.get(elementIndex).click();

	}

	public void clearText(AppiumDriver<MobileElement> driver, String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		WebElement element = driver.findElement(By.xpath(locator));

		element.clear();
	}

	public void checkToElement(AppiumDriver<MobileElement> driver, String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		if (!element.isSelected()) {
			element.click();
		}
	}

	public void uncheckToElement(AppiumDriver<MobileElement> driver, String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		if (element.getAttribute("checked").equalsIgnoreCase("true")) {
			element.click();
		}
	}

	public void uncheckToElement(AppiumDriver<MobileElement> driver, String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		WebElement element = driver.findElement(By.xpath(locator));
		if (element.getAttribute("checked").equalsIgnoreCase("true")) {
			element.click();
		}
	}

	public void sendKeyToElement(AppiumDriver<MobileElement> driver, String locator, String value) {
		WebElement element = driver.findElement(By.xpath(locator));
		element.clear();
		element.sendKeys(value);
	}

	/* CLICK METHOD */
	// Click dau ... dua theo ten
	public void clickToDynamicIconByText(AppiumDriver<MobileElement> driver, String dynamicKey) {
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_CONTACT_KEY_MENU, dynamicKey);
		clickToElement(driver, DynamicPageUIs.DYNAMIC_CONTACT_KEY_MENU, dynamicKey);
	}

//Click Icon by linerLayout
	public void clickToDynamicLinerLayoutID(AppiumDriver<MobileElement> driver, String dynamicValue) {
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_LINEAERLAYOUT_BY_ID, dynamicValue);
		clickToElement(driver, DynamicPageUIs.DYNAMIC_LINEAERLAYOUT_BY_ID, dynamicValue);
	}

	// Click vào icon clear trong ô input
	public void clickToDynamicIconByLinerLayout(AppiumDriver<MobileElement> driver, String dynamicValue) {
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_IMAGEVIEW_BY_LINEARLAYOUT_ID, dynamicValue);
		clickToElement(driver, DynamicPageUIs.DYNAMIC_IMAGEVIEW_BY_LINEARLAYOUT_ID, dynamicValue);
	}

//waits
	public void implicitWaitLongTime(AppiumDriver<MobileElement> driver) {

		driver.manage().timeouts().implicitlyWait(longTime1, TimeUnit.SECONDS);

	}

	public void implicitWaitShortTime(AppiumDriver<MobileElement> driver) {

		driver.manage().timeouts().implicitlyWait(shortTime1, TimeUnit.SECONDS);
	}

	public void waitForElementPresent(AppiumDriver<MobileElement> driver, String locator) {
		WebDriverWait wait = new WebDriverWait(driver, longTime1);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));
	}

	public void waitForAllElementPresent(AppiumDriver<MobileElement> driver, String locator) {
		WebDriverWait wait = new WebDriverWait(driver, longTime1);
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(locator)));
	}

	public void waitForAllElementPresent(AppiumDriver<MobileElement> driver, String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		WebDriverWait wait = new WebDriverWait(driver, longTime1);
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(locator)));
	}

	public boolean waitForElementVisible(AppiumDriver<MobileElement> driver, String locator) {

		WebDriverWait wait = new WebDriverWait(driver, 30);
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
			return false;

		}
		return true;

	}

	public void waitForElementClickable(AppiumDriver<MobileElement> driver, String locator) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator)));
	}

	public void waitForAllElementsVisible(AppiumDriver<MobileElement> driver, String locator) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(locator)));
	}

	public void waitForElementInvisible(AppiumDriver<MobileElement> driver, String locator) {
		overRideTimeOut(driver, shortTime);
		WebDriverWait wait = new WebDriverWait(driver, longTime);
		try {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(locator)));
			overRideTimeOut(driver, longTime);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			overRideTimeOut(driver, longTime);
		}
	}

	public void clickToElementByJava(AppiumDriver<MobileElement> driver, String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		WebElement element = driver.findElement(By.xpath(locator));
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].click();", element);
	}

	public int countElementNumber(AppiumDriver<MobileElement> driver, String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		List<MobileElement> elements = driver.findElements(By.xpath(locator));
		return elements.size();
	}

	public boolean isControlSelected(AppiumDriver<MobileElement> driver, String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		if (element.getAttribute("checked").equalsIgnoreCase("true")) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isControlDisplayed(AppiumDriver<MobileElement> driver, String locator, String... dynamicValue) {
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		locator = String.format(locator, (Object[]) dynamicValue);
		List<MobileElement> elements = driver.findElements(By.xpath(locator));
		if (elements.size() > 0 && elements.get(0).isDisplayed()) {
			return true;
		} else {
			return false;

		}
	}

	public boolean isControlSelected(AppiumDriver<MobileElement> driver, String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		WebElement element = driver.findElement(By.xpath(locator));
		if (element.getAttribute("checked").equalsIgnoreCase("true")) {
			return true;
		} else {
			return false;
		}
	}

// check button có hiển thị hay không, tham số truyền vào là text của button
	public boolean isDynamicButtonByTextDisplayed(AppiumDriver<MobileElement> driver, String dynamicTextValue) {

		boolean isDisplayed = false;
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
		boolean status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, DynamicPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
		}
		return isDisplayed;
	}

	public String removeUnicode(AppiumDriver<MobileElement> driver, String locator) {

		String temp = Normalizer.normalize(locator, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(temp).replaceAll("");
	}

	// Xac nhan hien thi text box qua editText ID
	public boolean isDynamicEditTexByIdDisplayed(AppiumDriver<MobileElement> driver, String dynamicValue) {
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_TEXT_BOX_WITH_ID, dynamicValue);
		return isControlDisplayed(driver, DynamicPageUIs.DYNAMIC_TEXT_BOX_WITH_ID, dynamicValue);
	}

	// Xac nhan hien thi button qua Button ID
	public boolean isDynamicButtonByIdDisplayed(AppiumDriver<MobileElement> driver, String dynamicValue) {
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_ACCEPT_BUTTON_OR_BUTTON, dynamicValue);
		return isControlDisplayed(driver, DynamicPageUIs.DYNAMIC_ACCEPT_BUTTON_OR_BUTTON, dynamicValue);
	}

	// Kiem tra text co trong List Element Text hay khong
	public boolean isTextDisplayedInListTextElements(AppiumDriver<MobileElement> driver, String expTextVal, String... dynamicValue) {

		String locator = "";
		locator = String.format(DynamicPageUIs.DYNAMIC_TEXT_IN_POPUP, (Object[]) dynamicValue);

		waitForElementVisible(driver, locator);
		boolean result = false;
		List<MobileElement> elements = driver.findElementsByXPath(locator);
		ArrayList<String> allTextElement = new ArrayList<String>();
		for (MobileElement element : elements) {
			allTextElement.add(element.getText());
		}
		for (String textElement : allTextElement) {
			if (textElement.contains(expTextVal)) {
				result = true;
			}
		}
		return result;
	}

	public boolean waitForElementVisible(AppiumDriver<MobileElement> driver, String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		WebDriverWait wait = new WebDriverWait(driver, 30);
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

	public boolean waitForElementInvisible(AppiumDriver<MobileElement> driver, String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		try {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(locator)));

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

	public String getOTPText(AppiumDriver<MobileElement> driver, String bankName, String allMesssage) {

		clickToElement(driver, "//android.widget.TextView[contains(@text,\"" + bankName + "\")]");
		waitForAllElementsVisible(driver, allMesssage);
		int no = countElementNumber(driver, allMesssage);
		String message = driver.findElements(By.xpath(allMesssage)).get(no - 1).getText();
		int index = message.indexOf("So OTP");
		String otp = message.substring(index + 8, index + 14);
		System.out.println(otp);
		return otp;
	}

	public String[] getAllValueInList(AppiumDriver<MobileElement> driver, String AllChidren) {
		WebDriverWait waitExplicit = new WebDriverWait(driver, 60);
		waitExplicit.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(AllChidren)));
		List<MobileElement> elements = driver.findElements(By.xpath(AllChidren));
		int noOfElement = elements.size();
		System.out.println("Tong so gia Tri " + noOfElement);
		String[] Array1 = new String[noOfElement];
		for (int i = 0; i < noOfElement; i++) {
			Array1[i] = driver.findElements(By.xpath(AllChidren)).get(i).getText();
			System.out.println("Gia Tri Trong Mang " + i + "  " + Array1[i]);
		}
		return Array1;
	}

	public String getDayInWeek(String dayMonthYear) {
		DateTimeFormatter formatter = DateTimeFormat.forPattern("d/M/yyyy");
		LocalDate localDate = formatter.parseLocalDate(dayMonthYear);
		int dayOfWeek = localDate.getDayOfWeek();
		String dayInWeek = "";
		switch (dayOfWeek) {
		case 1:
			dayInWeek = "Thứ Hai";
			System.out.println(dayInWeek);
			break;
		case 2:
			dayInWeek = "Thứ Ba";
			System.out.println(dayInWeek);
			break;
		case 3:
			dayInWeek = "Thứ Tư";
			System.out.println(dayInWeek);
			break;
		case 4:
			dayInWeek = "Thứ Năm";
			System.out.println(dayInWeek);
			break;
		case 5:
			dayInWeek = "Thứ Sáu";
			System.out.println(dayInWeek);
			;
			break;
		case 6:
			dayInWeek = "Thứ Bảy";
			System.out.println(dayInWeek);
			break;
		case 7:
			dayInWeek = "Chủ Nhật";
			System.out.println(dayInWeek);
			break;
		}

		return dayInWeek;

	}

	/* SCROLL DOWN */
	public void scrollDownToText(AppiumDriver<MobileElement> driver, String dynamicText) {
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicText);

	}

	/* SCROLL UP */
	public void scrollUpToText(AppiumDriver<MobileElement> driver, String dynamicText) {
		scrollUp(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT);

	}

	public void scrollDownToButton(AppiumDriver<MobileElement> driver, String dynamicText) {
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_BUTTON, dynamicText);

	}

	/* CLICK METHOD */

//Click vào Cho phép lúc khởi tạo app, hoặc check chức chăng có permission
	public void clickToDynamicAcceptButton(AppiumDriver<MobileElement> driver, String dynamicIDValue) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_ACCEPT_BUTTON_OR_BUTTON, dynamicIDValue);
		if (status == true) {
			clickToElement(driver, DynamicPageUIs.DYNAMIC_ACCEPT_BUTTON_OR_BUTTON, dynamicIDValue);
		}

	}

//Click vao 1 button sử dụng  tham số là text
	public void clickToDynamicButton(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
		boolean status = false;
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
		if (status == true) {
			clickToElement(driver, DynamicPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
		}
	}

	// Click vao 1 button sử dụng tham số là text
	public void clickToDynamicRadioIndex(AppiumDriver<MobileElement> driver, String ... dynamicTextAndIndex) {
		boolean status = false;
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_VIEW_VIEW_BY_INDEX, dynamicTextAndIndex);
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_VIEW_VIEW_BY_INDEX, dynamicTextAndIndex);
		if (status == true) {
			clickToElement(driver, DynamicPageUIs.DYNAMIC_VIEW_VIEW_BY_INDEX, dynamicTextAndIndex);
		}

	}

//Click vào dropdown list tham số truyển vào là label của ô dropdown list đó
	public void clickToDynamicDropDown(AppiumDriver<MobileElement> driver, String dymanicText) {
		boolean status = false;
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_DROPDOWN_BY_LABEL, dymanicText);
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_DROPDOWN_BY_LABEL, dymanicText);
		if (status == true) {
			clickToElement(driver, DynamicPageUIs.DYNAMIC_DROPDOWN_BY_LABEL, dymanicText);
		}

	}

//Click vào button, text có class là textview, tham số truyền vào là text
	public void clickToDynamicButtonLinkOrLinkText(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
		boolean status = false;
//		scrollIDown(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
		if (status == true) {
			clickToElement(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);

		}
	}

//Click vào ô textbox lấy theo header, có 2 tham số truyền vào là text của label và vị trí index của ô input đó
	public void clickToDynamicInputBoxByHeader(AppiumDriver<MobileElement> driver, String... dynamicTextValue) {
		boolean status = false;
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX_BY_HEADER, dynamicTextValue);
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX_BY_HEADER, dynamicTextValue);
		if (status == true) {
			clickToElement(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX_BY_HEADER, dynamicTextValue);
		}
	}

	// Click vào ô textbox lấy theo header, có 2 tham số truyền vào là text của
	// label và vị trí index của ô input đó
	public void clickToDynamicComboboxText(AppiumDriver<MobileElement> driver, String... dynamicTextValue) {
		boolean status = false;
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_COMBOBOX_TEXT, dynamicTextValue);
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_COMBOBOX_TEXT, dynamicTextValue);
		if (status == true) {
			clickToElement(driver, DynamicPageUIs.DYNAMIC_COMBOBOX_TEXT, dynamicTextValue);
		}

	}

//Click vào ô dropdown lấy theo header, có 2 tham số truyền vào là text của label và vị trí index của ô input đó
	public void clickToDynamicDropdownByHeader(AppiumDriver<MobileElement> driver, String... dynamicTextValue) {
		boolean status = false;
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_DROPDOWN_BY_HEADER, dynamicTextValue);
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_DROPDOWN_BY_HEADER, dynamicTextValue);
		if (status == true) {
			clickToElement(driver, DynamicPageUIs.DYNAMIC_DROPDOWN_BY_HEADER, dynamicTextValue);
		}

	}

// Click vào ô dropdown, và ô date time , tham số truyền vào là resource id
	public void clickToDynamicDropdownAndDateTimePicker(AppiumDriver<MobileElement> driver, String dynamicID) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY, dynamicID);
		if (status == true) {
			clickToElement(driver, DynamicPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY, dynamicID);
		}

	}

// Click vào ngày trong date time picker , tham số truyền vào là text
	public void clickToDynamicDateInDateTimePicker(AppiumDriver<MobileElement> driver, String dynamicText) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_DATE_IN_DATE_TIME_PICKER_AND_TEXT, dynamicText);
		if (status == true) {
			clickToElement(driver, DynamicPageUIs.DYNAMIC_DATE_IN_DATE_TIME_PICKER_AND_TEXT, dynamicText);
		}

	}

// Click vào menu tại bottom hoặc icon đóng k chứa text, tham số truyền vào là resource id
	public void clickToDynamicImageViewByID(AppiumDriver<MobileElement> driver, String dynamicID) {
		boolean status = false;
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_BOTTOM_MENU_CLOSE_ICON, dynamicID);
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BOTTOM_MENU_CLOSE_ICON, dynamicID);
		if (status == true) {
			clickToElement(driver, DynamicPageUIs.DYNAMIC_BOTTOM_MENU_CLOSE_ICON, dynamicID);
		}
	}

//Click vào close icon có tham số truyền vào là text cạnh nó
	public void clickToDynamicCloseIcon(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_CLOSE_ICON, dynamicTextValue);
		if (status == true) {
			clickToElement(driver, DynamicPageUIs.DYNAMIC_CLOSE_ICON, dynamicTextValue);
		}
	}

// Click nút back bằng label cạnh nó
	public void clickToDynamicBackIcon(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
		boolean status = false;
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_BACK_ICON, dynamicTextValue);
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BACK_ICON, dynamicTextValue);
		if (status == true) {
			clickToElement(driver, DynamicPageUIs.DYNAMIC_BACK_ICON, dynamicTextValue);
		}

	}

// click vào icon ứng dụng trên màn hình theo tên của icon đó
	public void clickToDynamicIcon(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
		boolean status = false;
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_ICON, dynamicTextValue);
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_ICON, dynamicTextValue);
		if (status == true) {
			clickToElement(driver, DynamicPageUIs.DYNAMIC_ICON, dynamicTextValue);
		}
	}

// Click vào ô input box 
	public void clickToDynamicInput(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, dynamicTextValue);
		if (status == true) {
			clickToElement(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, dynamicTextValue);
		}

	}

//Click vào giao dịch trong báo cáo giao dịch tham số truyền vào là index và resource-id
	public void clickToDynamicTransactionInReport(AppiumDriver<MobileElement> driver, String... dynamicIndexAndID) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_TRANSACTION_INFO_IN_REPORT, dynamicIndexAndID);
		if (status == true) {
			clickToElement(driver, DynamicPageUIs.DYNAMIC_TRANSACTION_INFO_IN_REPORT, dynamicIndexAndID);
		}

	}

//Click vào 1 giao dịch trong trạng thái lệnh chuyển tiền, tham số truyền vào là index và resource id
	public void clickToDynamicTransactionInTransactionOrderStatus(AppiumDriver<MobileElement> driver, String... dynamicIndex1ID2) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_TRANSACTION_INFO_IN_TRANSFER_ORDER_STATUS, dynamicIndex1ID2);
		if (status == true) {
			clickToElement(driver, DynamicPageUIs.DYNAMIC_TRANSACTION_INFO_IN_TRANSFER_ORDER_STATUS, dynamicIndex1ID2);
		}
	}

	public void clickDynamicPointStartAndEnd(AppiumDriver<MobileElement> driver, String... dynamicTextValue) {
		boolean status = false;
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_START_AND_END_TEXT, dynamicTextValue);
		if (status == true) {
			clickToElement(driver, TrainTicketPageUIs.DYNAMIC_START_AND_END_TEXT, dynamicTextValue);
		}
	}

	public void clickToDynamicDropDownListTextViewByHeader(AppiumDriver<MobileElement> driver, String... dynamicTextValueAndID) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_DROPDOWN_BY_HEADER, dynamicTextValueAndID);
		if (status == true) {
			clickToElement(driver, DynamicPageUIs.DYNAMIC_DROPDOWN_BY_HEADER, dynamicTextValueAndID);
		}

	}

	// Click button cancel
	public void clickDynamicCancelIcon(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
		boolean status = false;
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_CANCEL_ICON, dynamicTextValue);
		if (status == true) {
			clickToElement(driver, TrainTicketPageUIs.DYNAMIC_CANCEL_ICON, dynamicTextValue);
		}
	}

	// Click button cancel
	public void clickDynamicCheckBox(AppiumDriver<MobileElement> driver, String dynamicID) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_CHECK_BOX, dynamicID);
		if (status == true) {
			clickToElement(driver, DynamicPageUIs.DYNAMIC_CHECK_BOX, dynamicID);

		}
	}

	// Click radio theo text
	public void clickToImageRadio(AppiumDriver<MobileElement> driver, String dynamicText) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_IMAGE_TEXT, dynamicText);
		if (status == true) {
			clickToElement(driver, DynamicPageUIs.DYNAMIC_IMAGE_TEXT, dynamicText);
		}
	}

	// So sánh giá trị trong list combobox, không cần sắp xếp theo thứ tự
	public boolean checkListContain(List<String> actualList, List<String> expectList) {
		return expectList.containsAll(actualList);
	}

	public void clickToDynamicBottomMenu(AppiumDriver<MobileElement> driver, String dynamicID) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BOTTOM_MENU, dynamicID);
		if (status == true) {
			clickToElement(driver, DynamicPageUIs.DYNAMIC_BOTTOM_MENU, dynamicID);

		}
	}

	public void clickToDynamicImageButtonByID(AppiumDriver<MobileElement> driver, String dynamicID) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_IMAGE_BUTTON, dynamicID);
		if (status == true) {
			clickToElement(driver, DynamicPageUIs.DYNAMIC_IMAGE_BUTTON, dynamicID);
		}
	}

	public void clickToDynamicLink(AppiumDriver<MobileElement> driver, String... dynamicTextValue) {
		boolean status = false;
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_HISTORY_ICON, dynamicTextValue);
		if (status == true) {
			clickToElement(driver, TrainTicketPageUIs.DYNAMIC_HISTORY_ICON, dynamicTextValue);
		}
	}

	public void clickToDynamicSuggestedMoney(AppiumDriver<MobileElement> driver, int index, String dynamicID) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY, dynamicID);
		if (status == true) {
			clickToOneOfElement(driver, index, DynamicPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY, dynamicID);
		}
	}

	// Click icon change
	public void clickToDynamicIconChange(AppiumDriver<MobileElement> driver, String dynamicText) {
		boolean status = false;
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_CHANGE_ICON, dynamicText);
		if (status == true) {
			clickToElement(driver, TrainTicketPageUIs.DYNAMIC_CHANGE_ICON, dynamicText);
		}
	}

	// Click select date
	public void clickToDynamicSelectDate(AppiumDriver<MobileElement> driver, String dynamicID) {
		boolean status = false;
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_DATE, dynamicID);
		if (status == true) {
			clickToElement(driver, TrainTicketPageUIs.DYNAMIC_DATE, dynamicID);
		}
	}

	// Click chọn ngày trong lịch calendar
	public void clickDynamicDateStartAndEnd(AppiumDriver<MobileElement> driver, String... dynamicTextValue) {
		boolean status = false;
		scrollIDown(driver, TrainTicketPageUIs.DYNAMIC_DATE_SELECTED, dynamicTextValue);
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_DATE_SELECTED, dynamicTextValue);
		if (status == true) {
			clickToElement(driver, TrainTicketPageUIs.DYNAMIC_DATE_SELECTED, dynamicTextValue);
		}
	}

	// Click radio button
	public void clickDynamicRadioSelectType(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_SUCCESS_ICON, dynamicTextValue);
		if (status == true) {
			clickToElement(driver, DynamicPageUIs.DYNAMIC_SUCCESS_ICON, dynamicTextValue);
		}
	}

	//
	public void clickToDynamicImageCombobox(AppiumDriver<MobileElement> driver, String... dynamicTextValue) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_IMAGE_COMBOBOX, dynamicTextValue);
		if (status == true) {
			clickToElement(driver, DynamicPageUIs.DYNAMIC_IMAGE_COMBOBOX, dynamicTextValue);

		}
	}

	public void clickToTextViewByLinearLayoutID(AppiumDriver<MobileElement> driver, String... dynamicID) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_TEXTVIEW_BY_LINEARLAYOUT_ID, dynamicID);
		if (status == true) {
			clickToElement(driver, DynamicPageUIs.DYNAMIC_TEXTVIEW_BY_LINEARLAYOUT_ID, dynamicID);
		}
	}

	public void clickToTextID(AppiumDriver<MobileElement> driver, String dynamicID) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_TEXT_BY_ID, dynamicID);
		if (status == true) {
			clickToElement(driver, DynamicPageUIs.DYNAMIC_TEXT_BY_ID, dynamicID);
		}

	}

	// Click vào giao dịch trong báo cáo giao dịch tham số truyền vào là index và
	// resource-id
	public void clickToDynamicTextIndex(AppiumDriver<MobileElement> driver, String... dynamicIndexAndText) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_TEXT_INDEX, dynamicIndexAndText);
		if (status == true) {
			clickToElement(driver, DynamicPageUIs.DYNAMIC_TEXT_INDEX, dynamicIndexAndText);
		}
	}
	
	public void clickToDynamicWishes(AppiumDriver<MobileElement> driver, String dynamicText) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, dynamicText);
		if (status == true) {
			clickToElement(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, dynamicText);
		}

	}

	/* INPUT BOX METHOD */

	public void inputOTPInvalidBy_N_Times(AppiumDriver<MobileElement> driver, int time) {
		for (int i = 0; i < time; i++) {
			inputToDynamicOtp(driver, "213456", "Tiếp tục");
			clickToDynamicButton(driver, "Tiếp tục");
			if (i < time - 1) {
				clickToDynamicButton(driver, "Đóng");
			}
		}
	}

	// Click combobox
	public void clickToTextViewCombobox(AppiumDriver<MobileElement> driver, String... dynamicID) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_TEXT_IN_POPUP, dynamicID);
		if (status == true) {
			clickToElement(driver, DynamicPageUIs.DYNAMIC_TEXT_IN_POPUP, dynamicID);
		}

	}

	// Click combobox
	public void clickToTextViewDate(AppiumDriver<MobileElement> driver, String... dynamicText) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_EDIT_INDEX, dynamicText);
		if (status == true) {
			clickToElement(driver, DynamicPageUIs.DYNAMIC_EDIT_INDEX, dynamicText);
		}
	}

	// Click select năm, sử dụng scroll up
	public void clickToTextListview(AppiumDriver<MobileElement> driver, String... dynamicText) {
		boolean status = false;
		scrollUp(driver, DynamicPageUIs.DYNAMIC_TEXTVIEW_BY_LISTVIEW);
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_TEXTVIEW_BY_LISTVIEW, dynamicText);
		if (status == true) {
			clickToElement(driver, DynamicPageUIs.DYNAMIC_TEXTVIEW_BY_LISTVIEW, dynamicText);
		}

	}

	public void inputPasswordInvalidBy_N_Times(AppiumDriver<MobileElement> driver, int time) {
		for (int i = 0; i < time; i++) {
			inputToDynamicPopupPasswordInput(driver, "12345678", "Tiếp tục");
			clickToDynamicButton(driver, "Tiếp tục");
			if (i < time - 1) {
				clickToDynamicButton(driver, "Đóng");
			}
		}
	}

	public void inputIntoEditTextByID(AppiumDriver<MobileElement> driver, String inputValue, String... dynamicID) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_TEXT_BOX_WITH_ID, dynamicID);
		if (status == true) {
			clearText(driver, DynamicPageUIs.DYNAMIC_TEXT_BOX_WITH_ID, dynamicID);
			sendKeyToElement(driver, DynamicPageUIs.DYNAMIC_TEXT_BOX_WITH_ID, inputValue, dynamicID);
		}

	}

//Dien text vao input box
// input vào ô input với tham số truyền vào là inputbox
	public void inputToDynamicInputBox(AppiumDriver<MobileElement> driver, String inputValue, String dynamicTextValue) {
		boolean status = false;
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, dynamicTextValue);
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, dynamicTextValue);
		if (status == true) {
			clearText(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, dynamicTextValue);
			sendKeyToElement(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, inputValue, dynamicTextValue);
		}

	}

//input vào 1 ô text box bằng label của nó, tham số truyền vào là text của label và vị trí của ô input
	public void inputToDynamicInputBoxByHeader(AppiumDriver<MobileElement> driver, String inputValue, String... dynamicTextValueAndID) {
		boolean status = false;
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX_BY_HEADER, dynamicTextValueAndID);
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX_BY_HEADER, dynamicTextValueAndID);
		if (status == true) {
			clearText(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX_BY_HEADER, dynamicTextValueAndID);
			sendKeyToElement(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX_BY_HEADER, inputValue, dynamicTextValueAndID);
		}

	}

	public void pressKeyCodeIntoDynamicInputBoxByHeader(AppiumDriver<MobileElement> driver, List<Keys> dynamicKey, String... dynamicTextValueAndID) {
		boolean status = false;
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX_BY_HEADER, dynamicTextValueAndID);
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX_BY_HEADER, dynamicTextValueAndID);
		if (status == true) {
			clearText(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX_BY_HEADER, dynamicTextValueAndID);
			for (Keys key : dynamicKey) {
				driver.getKeyboard().sendKeys(key);
			}
		}
	}

	public void pressKeyCodeIntoAmountInput(AppiumDriver<MobileElement> driver, List<Keys> dynamicKey) {
		boolean status = false;
		status = waitForElementVisible(driver, TransferMoneyInVCBPageUIs.FREQUENCY_NUMBER_INPUT);
		if (status == true) {
			clearText(driver, TransferMoneyInVCBPageUIs.FREQUENCY_NUMBER_INPUT);
			for (Keys key : dynamicKey) {
				driver.getKeyboard().sendKeys(key);
			}
		}
	}

	public void inputToDynamicLogInTextBox(AppiumDriver<MobileElement> driver, String inputValue, String dynamicTextValue) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_INPUT_IN_LOGIN, dynamicTextValue);
		;
		if (status == true) {
			clearText(driver, DynamicPageUIs.DYNAMIC_INPUT_IN_LOGIN, dynamicTextValue);
			sendKeyToElement(driver, DynamicPageUIs.DYNAMIC_INPUT_IN_LOGIN, inputValue, dynamicTextValue);
		}
	}

//Input vào ô nhập otp , tham số truyền vào là text của button tiếp tục
	public void inputToDynamicOtp(AppiumDriver<MobileElement> driver, String inputValue, String dynamicTextValue) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_OTP_INPUT, dynamicTextValue);
		if (status == true) {
			clearText(driver, DynamicPageUIs.DYNAMIC_OTP_INPUT, dynamicTextValue);
			sendKeyToElement(driver, DynamicPageUIs.DYNAMIC_OTP_INPUT, inputValue, dynamicTextValue);
		}
	}

//input vào pop-up nhập mật khẩu xác thực, tham số truyền vào là text của button tiếp tục

	// Nhập địa điểm tìm kiếm
	public void inputToDynamicTextPoint(AppiumDriver<MobileElement> driver, String inputValue, String dynamicIndexValue) {
		boolean status = false;
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_INPUT_POINT, dynamicIndexValue);
		if (status == true) {
			clearText(driver, TrainTicketPageUIs.DYNAMIC_INPUT_POINT, dynamicIndexValue);
			sendKeyToElement(driver, TrainTicketPageUIs.DYNAMIC_INPUT_POINT, inputValue, dynamicIndexValue);
		}
	}

	public void inputToDynamicPopupPasswordInput(AppiumDriver<MobileElement> driver, String inputValue, String dynamicTextValue) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_PASSWORD_INPUT, dynamicTextValue);
		if (status == true) {
			clearText(driver, DynamicPageUIs.DYNAMIC_PASSWORD_INPUT, dynamicTextValue);
			sendKeyToElement(driver, DynamicPageUIs.DYNAMIC_PASSWORD_INPUT, inputValue, dynamicTextValue);
		}
	}

	public void inputToDynamicInputBoxSearch(AppiumDriver<MobileElement> driver, String inputValue, String dynamicTextValue) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_LABEL_LIST_ACCEPT, dynamicTextValue);
		if (status == true) {
			clearText(driver, DynamicPageUIs.DYNAMIC_LABEL_LIST_ACCEPT, dynamicTextValue);
			sendKeyToElement(driver, DynamicPageUIs.DYNAMIC_LABEL_LIST_ACCEPT, inputValue, dynamicTextValue);
		}
	}

	public void inputToDynamicInputBoxSearchBank(AppiumDriver<MobileElement> driver, String inputValue, String dynamicTextValue) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_LABEL_SEARCH_BANK, dynamicTextValue);
		if (status == true) {
			clearText(driver, DynamicPageUIs.DYNAMIC_LABEL_SEARCH_BANK, dynamicTextValue);
			sendKeyToElement(driver, DynamicPageUIs.DYNAMIC_LABEL_SEARCH_BANK, inputValue, dynamicTextValue);
		}
	}

	// Input theo textview
	public void inputToDynamicInputText(AppiumDriver<MobileElement> driver, String inputValue, String... dynamicTextValue) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_EDIT_FOLLOW_TEXT, dynamicTextValue);
		if (status == true) {
			clearText(driver, DynamicPageUIs.DYNAMIC_EDIT_FOLLOW_TEXT, dynamicTextValue);
			sendKeyToElement(driver, DynamicPageUIs.DYNAMIC_EDIT_FOLLOW_TEXT, inputValue, dynamicTextValue);
		}
	}

	/* BOLEAN METHOD */

	// Xac nhan Button Enable qua Button ID
	public boolean isDynamicButtonByIdEnable(AppiumDriver<MobileElement> driver, String dynamicValue) {
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_ACCEPT_BUTTON_OR_BUTTON, dynamicValue);
		return isControlEnabled(driver, DynamicPageUIs.DYNAMIC_ACCEPT_BUTTON_OR_BUTTON, dynamicValue);
	}

	// Xac dinh text co duoc Focus hay khong
	public boolean isDynamicValuesFocus(AppiumDriver<MobileElement> driver, String dynamicValue) {
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicValue);
		return isControlForcus(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicValue);
	}

	// Xac nhan hien thi text qua Text ID
	public boolean isDynamicTextByIdDisplayed(AppiumDriver<MobileElement> driver, String dynamicValue) {
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_TEXT_IN_POPUP, dynamicValue);
		return isControlDisplayed(driver, DynamicPageUIs.DYNAMIC_TEXT_IN_POPUP, dynamicValue);
	}

// check button có hiển thị hay không, tham số truyền vào là text của button
	public boolean isDynamicButtonDisplayed(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
		boolean isDisplayed = false;
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
		boolean status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, DynamicPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
		}
		return isDisplayed;

	}

//Kiểm tra text có hiển thị hay không, tham số truyền vào là text 
	public boolean isDynamicMessageAndLabelTextDisplayed(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
		boolean isDisplayed = false;
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
		boolean status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
		}
		return isDisplayed;

	}

	public boolean isDynamicImageViewByTextViewdDisplayed(AppiumDriver<MobileElement> driver, String... dynamicTextValue) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_CLOSE_ICON, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, DynamicPageUIs.DYNAMIC_CLOSE_ICON, dynamicTextValue);
		}
		return isDisplayed;
	}

//Kiểm tra text không hiển thị trên màn hình, tham số truyền vào là text
	public boolean isDynamicMessageAndLabelTextUndisplayed(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
		boolean isDisplayed = true;
		boolean status = waitForElementInvisible(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlUnDisplayed(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
		}
		return isDisplayed;
	}

//Kiểm  tra text trong ô input có hiển thị hay không, tham số truyền vào là text
	public boolean isDynamicTextInInputBoxDisPlayed(AppiumDriver<MobileElement> driver, String... dynamicTextValue) {

		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, dynamicTextValue);
		}
		return isDisplayed;
	}

//Kiểm tra gợi ý số tiền không hiển thị trên màn hình,tham số truyền vào là resource-id 	
	public boolean isDynamicImageTextDisplayed(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
		boolean isDisplayed = false;
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_IMAGE_TEXT, dynamicTextValue);
		boolean status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_IMAGE_TEXT, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, DynamicPageUIs.DYNAMIC_IMAGE_TEXT, dynamicTextValue);
		}
		return isDisplayed;
	}

	// Kiem tra element Linearlayout ID co hien thi hay khong
	public boolean isDynamicLinearlayoutByIdDisplayed(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
		boolean isDisplayed = false;
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_LINEAERLAYOUT_BY_ID, dynamicTextValue);
		boolean status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_LINEAERLAYOUT_BY_ID, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, DynamicPageUIs.DYNAMIC_LINEAERLAYOUT_BY_ID, dynamicTextValue);
		}
		return isDisplayed;
	}

	public boolean isDynamicSuggestedMoneyUndisplayed(AppiumDriver<MobileElement> driver, String... dynamicTextValue) {
		boolean isDisplayed = false;
		boolean status = waitForElementInvisible(driver, DynamicPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlUnDisplayed(driver, DynamicPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY, dynamicTextValue);
		}
		return isDisplayed;
	}

	public boolean isDynamicBackIconDisplayed(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BACK_ICON, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, DynamicPageUIs.DYNAMIC_BACK_ICON, dynamicTextValue);
		}
		return isDisplayed;
	}

	public boolean isDynamicHistoryIconDisplayed(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_HISTORY_ICON, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, TrainTicketPageUIs.DYNAMIC_HISTORY_ICON, dynamicTextValue);
		}
		return isDisplayed;
	}

	// Check hiển thị button chuyển đổi
	public boolean isDynamicChangeIconDisplayed(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_CHANGE_ICON, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, TrainTicketPageUIs.DYNAMIC_CHANGE_ICON, dynamicTextValue);
		}
		return isDisplayed;
	}

	// Check hiển thị icon combobox
	public boolean isDynamicComboboxDisplayed(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_COMBOBOX, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, TrainTicketPageUIs.DYNAMIC_COMBOBOX, dynamicTextValue);
		}
		return isDisplayed;
	}

	public boolean isDynamicVerifyTextOnButton(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
		boolean isDisplayed = false;
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
		boolean status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, DynamicPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
		}
		return isDisplayed;

	}

	public boolean isDynamicTextDetailByID(AppiumDriver<MobileElement> driver, String dynamicID) {
		boolean isDisplayed = false;
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_TEXT_BY_ID, dynamicID);
		boolean status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_TEXT_BY_ID, dynamicID);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, DynamicPageUIs.DYNAMIC_TEXT_BY_ID, dynamicID);
		}
		return isDisplayed;

	}

	// Kiểm tra gợi ý số tiền có hiển thị, tham số truyền vào là resource-id
	public boolean isDynamicSuggestedMoneyDisplayed(AppiumDriver<MobileElement> driver, String... dynamicTextValue) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, DynamicPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY, dynamicTextValue);
		}
		return isDisplayed;
	}

	// Kiểm tra text trong nội dung link thông báo
	public boolean isDynamicTextInfoDisplayed(AppiumDriver<MobileElement> driver, String... dynamicTextValue) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_DATE_IN_DATE_TIME_PICKER_AND_TEXT, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, DynamicPageUIs.DYNAMIC_DATE_IN_DATE_TIME_PICKER_AND_TEXT, dynamicTextValue);
		}
		return isDisplayed;
	}

	// Kiểm tra hiển thị image, check đã chọn có định dạng ImageView
	public boolean isDynamicImageSelect(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
		boolean isDisplayed = false;
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_CLOSE_ICON, dynamicTextValue);
		boolean status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_CLOSE_ICON, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, DynamicPageUIs.DYNAMIC_CLOSE_ICON, dynamicTextValue);
		}
		return isDisplayed;
	}

	// Kiểm tra hiển thị image, check chuyển khoản thành công
	public boolean isDynamicImageSuccess(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
		boolean isDisplayed = false;
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_CLOSE_ICON, dynamicTextValue);
		boolean status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_SUCCESS_ICON, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, DynamicPageUIs.DYNAMIC_SUCCESS_ICON, dynamicTextValue);
		}
		return isDisplayed;
	}

	// Kiểm tra hiển thị icon home man hinh chuyen tien thanh cong
	public boolean isDynamicImageHomeDisplay(AppiumDriver<MobileElement> driver, String dynamicID) {
		boolean isDisplayed = false;
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_BOTTOM_MENU, dynamicID);
		boolean status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BOTTOM_MENU, dynamicID);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, DynamicPageUIs.DYNAMIC_BOTTOM_MENU, dynamicID);
		}
		return isDisplayed;
	}

	// Kiểm tra hiển thị time màn hình chuyển khoản thành công
	public boolean isDynamicTimeAndMoneyDisplay(AppiumDriver<MobileElement> driver, String... dynamicTextValue) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_TRANSFER_TIME, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, DynamicPageUIs.DYNAMIC_TRANSFER_TIME, dynamicTextValue);
		}
		return isDisplayed;

	}

	public boolean isDynamicTextNumberCustomerDisplayed(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_LABEL_AMOUNT, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, DynamicPageUIs.DYNAMIC_LABEL_AMOUNT, dynamicTextValue);
		}
		return isDisplayed;
	}

	// Không hiển thị text trường số lượng hành khách
	public boolean isDynamicTextNumberCustomerUnDisplayed(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
		boolean isDisplayed = false;
		boolean status = waitForElementInvisible(driver, DynamicPageUIs.DYNAMIC_LABEL_AMOUNT, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlUnDisplayed(driver, DynamicPageUIs.DYNAMIC_LABEL_AMOUNT, dynamicTextValue);
		}
		return isDisplayed;
	}

	// hiển thị icon trường số lượng hành khách
	public boolean isDynamicIconChangeNumber(AppiumDriver<MobileElement> driver, String... dynamicTextValue) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_ICON_CHANGE_NUMBER, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, TrainTicketPageUIs.DYNAMIC_ICON_CHANGE_NUMBER, dynamicTextValue);
		}
		return isDisplayed;

	}

	// hiển thị text trường số lượng hành khách
	public boolean isDynamicTextChangeNumber(AppiumDriver<MobileElement> driver, String... dynamicTextValue) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_TEXT_CHANGE_NUMBER, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, TrainTicketPageUIs.DYNAMIC_TEXT_CHANGE_NUMBER, dynamicTextValue);
		}
		return isDisplayed;

	}

	public boolean isDynamicImageButtonDisplayed(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
		boolean isDisplayed = false;
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_IMAGE_BUTTON, dynamicTextValue);
		boolean status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_IMAGE_BUTTON, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, DynamicPageUIs.DYNAMIC_IMAGE_BUTTON, dynamicTextValue);
		}
		return isDisplayed;
	}

	public boolean isDynamicImageByFollowingImageIdDisplayed(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
		boolean isDisplayed = false;
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_IMAGE_BY_FOLLOWING_IMAGE_ID, dynamicTextValue);
		boolean status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_IMAGE_BY_FOLLOWING_IMAGE_ID, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, DynamicPageUIs.DYNAMIC_IMAGE_BY_FOLLOWING_IMAGE_ID, dynamicTextValue);
		}
		return isDisplayed;
	}

	public boolean isDynamicImageButtonBackDisplayed(AppiumDriver<MobileElement> driver, String dynamicID) {
		boolean isDisplayed = false;
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_BOTTOM_MENU_CLOSE_ICON, dynamicID);
		boolean status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BOTTOM_MENU_CLOSE_ICON, dynamicID);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, DynamicPageUIs.DYNAMIC_BOTTOM_MENU_CLOSE_ICON, dynamicID);
		}
		return isDisplayed;
	}

	/* GET TEXT METHOD */

// lay text trong ô dropdown bằng index và header của nó
	public String getDynamicTextInDropDownByHeader(AppiumDriver<MobileElement> driver, String... dynamicTextValueAndID) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_DROPDOWN_BY_HEADER, dynamicTextValueAndID);
		if (status == true) {
			text = getTextElement(driver, DynamicPageUIs.DYNAMIC_DROPDOWN_BY_HEADER, dynamicTextValueAndID);

		}
		return text;

	}

// lay loại tiền tệ trong ô text box số tiền bằng text
	public String getDynamicCurrencyInMoneyTextbox(AppiumDriver<MobileElement> driver, String... dynamicText) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_CURRENTCY_IN_MONEY_TEXTBOX, dynamicText);
		if (status == true) {
			text = getTextElement(driver, DynamicPageUIs.DYNAMIC_CURRENTCY_IN_MONEY_TEXTBOX, dynamicText);

		}
		return text;

	}

//lấy text trong ô input, tham số truyền vào là text
	public String getDynamicTextInInputBox(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, dynamicTextValue);
		if (status == true) {
			text = getTextElement(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, dynamicTextValue);

		}
		return text;
	}

//Lấy text trong ô input bằng bằng label của nó, tham số truyền vào là  text của label và vị trí index của ô input
	public String getDynamicTextInInputBoxByHeader(AppiumDriver<MobileElement> driver, String... dynamicTextAndIndex) {
		boolean status = false;
		String text = null;
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX_BY_HEADER, dynamicTextAndIndex);
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX_BY_HEADER, dynamicTextAndIndex);
		if (status == true) {
			text = getTextElement(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX_BY_HEADER, dynamicTextAndIndex);

		}
		return text;

	}

//lấy tài khoản nguồn mặc định bằng label của nó, tham số truyền vào là text và index 
	public String getTextDynamicDefaultSourceAccount(AppiumDriver<MobileElement> driver, String... dynamicTextValueAndIndex) {
		boolean status = false;
		String text = null;
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_DEFAULT_SOURCE_ACCOUNT, dynamicTextValueAndIndex);
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_DEFAULT_SOURCE_ACCOUNT, dynamicTextValueAndIndex);
		if (status == true) {
			text = getTextElement(driver, DynamicPageUIs.DYNAMIC_DEFAULT_SOURCE_ACCOUNT, dynamicTextValueAndIndex);

		}
		return text;
	}

//lấy thời gian tạo giao dịch ở màn hình xác thực giao dịch, tham số truyền vào là text và vị trí index của nó
	public String getDynamicTransferTimeAndMoney(AppiumDriver<MobileElement> driver, String... dynamicTextValue) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_TRANSFER_TIME, dynamicTextValue);
		if (status == true) {
			text = getTextElement(driver, DynamicPageUIs.DYNAMIC_TRANSFER_TIME, dynamicTextValue);

		}
		return text;

	}

	public String getTextDynamicInSelectBox(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
		if (status == true) {
			text = getTextElement(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);

		}
		return text;

	}

	// Get text theo image
	public String getTextDynamicFollowImage(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_TEXT_IMAGE, dynamicTextValue);
		if (status == true) {
			text = getTextElement(driver, DynamicPageUIs.DYNAMIC_TEXT_IMAGE, dynamicTextValue);

		}
		return text;

	}

	// get text theo button
	public String getTextDynamicFollowImageIndex(AppiumDriver<MobileElement> driver, String... dynamicIndex) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_TEXTVIEW_FOLLOW_IMAGE, dynamicIndex);
		if (status == true) {
			text = getTextElement(driver, DynamicPageUIs.DYNAMIC_TEXTVIEW_FOLLOW_IMAGE, dynamicIndex);

		}
		return text;

	}

	// get text theolayout
	public String getTextDynamicFollowLayout(AppiumDriver<MobileElement> driver, String... dynamicIndex) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_TEXT_FOLLOW_LAYOUT, dynamicIndex);
		if (status == true) {
			text = getTextElement(driver, DynamicPageUIs.DYNAMIC_TEXT_FOLLOW_LAYOUT, dynamicIndex);

		}
		return text;

	}

	// Xac dinh lay so luong element dang duoc Focus
	public int getNumberOfDynamicElementsFocus(AppiumDriver<MobileElement> driver, String... dynamicValue) {

		String locator = "";
		int occurrences = 0;
		locator = String.format(DynamicPageUIs.DYNAMIC_TEXT_IN_POPUP, (Object[]) dynamicValue);

		waitForElementVisible(driver, locator);
		List<MobileElement> elements = driver.findElementsByXPath(locator);
		ArrayList<String> allStatusElement = new ArrayList<String>();
		for (MobileElement element : elements) {
			allStatusElement.add(element.getAttribute("selected"));
		}
		occurrences = Collections.frequency(allStatusElement, "true");
		return occurrences;
	}

	public String getTextInDynamicNote(AppiumDriver<MobileElement> driver, String dynamicIndex) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_TEXT_INDEX, dynamicIndex);
		if (status == true) {
			text = getTextElement(driver, TrainTicketPageUIs.DYNAMIC_TEXT_INDEX, dynamicIndex);

		}
		return text;
	}

//Get thông tin được tạo trong chi tiết giao dich , tham số truyền vào là text phía bên tay trái
	public String getDynamicTextInTransactionDetail(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
		boolean status = false;
		String text = null;
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, dynamicTextValue);
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, dynamicTextValue);
		if (status == true) {
			text = getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, dynamicTextValue);

		}
		return text;

	}

//Lấy text bằng id

	public String getDynamicTextDetailByIDOrPopup(AppiumDriver<MobileElement> driver, String dynamicID) {
		boolean status = false;
		String text = null;
//		scrollIDown(driver, DynamicPageUIs.DYNAMIC_TEXT_BY_ID, dynamicID);
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_TEXT_BY_ID, dynamicID);
		if (status == true) {
			text = getTextElement(driver, DynamicPageUIs.DYNAMIC_TEXT_BY_ID, dynamicID);

		}
		return text;
	}

//lấy text ở dòng thứ 2 của phí chuyển tromg xác thực giao dich
	public String getDynamicTextInTextViewLine2(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
		boolean status = false;
		String text = null;
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_CONFIRM_SECOND_LINE_INFO, dynamicTextValue);
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_CONFIRM_SECOND_LINE_INFO, dynamicTextValue);
		if (status == true) {
			text = getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_SECOND_LINE_INFO, dynamicTextValue);

		}
		return text;

	}

//lấy thông tin giao dịch được tạo ở chị tiết giao dich của báo cáo giao dịch
	public String getTextInDynamicTransactionInReport(AppiumDriver<MobileElement> driver, String... dynamicIndex1Index2) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_TRANSACTION_INFO_IN_REPORT, dynamicIndex1Index2);
		if (status == true) {
			text = getTextElement(driver, DynamicPageUIs.DYNAMIC_TRANSACTION_INFO_IN_REPORT, dynamicIndex1Index2);

		}
		return text;
	}

	// lấy thông tin giao dịch được tạo ở chị tiết giao dich của trạng thái lệnh
	// chuyển tiền
	public String getTextInDynamicTransactionInTransferOrderStatus(AppiumDriver<MobileElement> driver, String... dynamicIndex1ResouceID) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_TRANSACTION_INFO_IN_TRANSFER_ORDER_STATUS, dynamicIndex1ResouceID);
		if (status == true) {
			text = getTextElement(driver, DynamicPageUIs.DYNAMIC_TRANSACTION_INFO_IN_TRANSFER_ORDER_STATUS, dynamicIndex1ResouceID);

		}
		return text;
	}

	public String getDynamicTextByLabel(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_LABEL_AMOUNT, dynamicTextValue);
		if (status == true) {
			text = getTextElement(driver, DynamicPageUIs.DYNAMIC_LABEL_AMOUNT, dynamicTextValue);
		}
		return text;

	}

	public String getDynamicAmountLabelList(AppiumDriver<MobileElement> driver, String... dynamicTextValue) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_INPUT_LABEL_BY_HEADER, dynamicTextValue);
		if (status == true) {
			text = getTextElement(driver, DynamicPageUIs.DYNAMIC_INPUT_LABEL_BY_HEADER, dynamicTextValue);
		}
		return text;
	}

	public String getDynamicAmountCostLabel(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_LABEL_COST, dynamicTextValue);
		if (status == true) {
			text = getTextElement(driver, DynamicPageUIs.DYNAMIC_LABEL_COST, dynamicTextValue);
		}
		return text;

	}

//Lấy text ở dropdown hoặc datetime picker bằng id
	public String getTextInDynamicDropdownOrDateTimePicker(AppiumDriver<MobileElement> driver, String dynamicID) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY, dynamicID);
		if (status == true) {
			text = getTextElement(driver, DynamicPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY, dynamicID);
		}
		return text;

	}

	// Lấy toàn bộ số tiền được suggest ở ô số tiền và lưu vào array list

	public String getDynamicTextInPopUp(AppiumDriver<MobileElement> driver, String... dynamicTextValue) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
		if (status == true) {
			text = getTextElement(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
		}
		return text;
	}

	// Lấy text tìm hiếm điểm khởi hành và điểm đến
	public String getDynamicPointStartAndEnd(AppiumDriver<MobileElement> driver, String... dynamicTextValue) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_START_AND_END_TEXT, dynamicTextValue);
		if (status == true) {
			text = getTextElement(driver, TrainTicketPageUIs.DYNAMIC_START_AND_END_TEXT, dynamicTextValue);
		}
		return text;

	}

	// Lấy giá trị tìm kiếm trong danh sách
	public String getDynamicInputPoint(AppiumDriver<MobileElement> driver, String dynamicIndexValue) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_INPUT_POINT, dynamicIndexValue);
		if (status == true) {
			text = getTextElement(driver, TrainTicketPageUIs.DYNAMIC_INPUT_POINT, dynamicIndexValue);
		}
		return text;

	}

	public String getDynamicTextPointStart(AppiumDriver<MobileElement> driver, String dynamicID) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_VIEW_TEXT_START, dynamicID);
		if (status == true) {
			text = getTextElement(driver, TrainTicketPageUIs.DYNAMIC_VIEW_TEXT_START, dynamicID);
		}
		return text;
	}

	public List<String> getListOfSuggestedMoneyOrListText(AppiumDriver<MobileElement> driver, String dynamicID) {
		boolean status = false;
		List<String> text = null;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY, dynamicID);
		if (status == true) {
			text = getTextInListElements(driver, DynamicPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY, dynamicID);
		}
		return text;

	}

	// lấy text trong ô edit tham số truyền vào là text và index
	public String getTextDynamicFollowIndex(AppiumDriver<MobileElement> driver, String... dynamicTextValueAndIndex) {
		boolean status = false;
		String text = null;
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_EDIT_FOLLOW_TEXT, dynamicTextValueAndIndex);
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_EDIT_FOLLOW_TEXT, dynamicTextValueAndIndex);
		if (status == true) {
			text = getTextElement(driver, DynamicPageUIs.DYNAMIC_EDIT_FOLLOW_TEXT, dynamicTextValueAndIndex);

		}
		return text;
	}

// Kiểm tra keyboard có hiển thị

	public String getMoneyByAccount(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
		boolean status = false;
		String text = null;
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_LABEL_MONEY_BY_ACCOUNT, dynamicTextValue);
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_LABEL_MONEY_BY_ACCOUNT, dynamicTextValue);
		if (status == true) {
			text = getTextElement(driver, DynamicPageUIs.DYNAMIC_LABEL_MONEY_BY_ACCOUNT, dynamicTextValue);

		}
		return text;
	}

	// Lấy text trên ô điền OTP
	public String getTextInDynamicOtp(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
		boolean status = false;
		String text = null;
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_OTP_INPUT, dynamicTextValue);
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_OTP_INPUT, dynamicTextValue);
		if (status == true) {
			text = getTextElement(driver, DynamicPageUIs.DYNAMIC_OTP_INPUT, dynamicTextValue);
		}

		return text;
	}

	// Lấy text phí giao dịch
	public String getTextInDynamicFee(AppiumDriver<MobileElement> driver, String... dynamicTextValue) {
		boolean status = false;
		String text = null;
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_LABEL_MONEY_BY_ACCOUNT, dynamicTextValue);
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_DROPDOWN_BY_HEADER, dynamicTextValue);
		if (status == true) {
			text = getTextElement(driver, DynamicPageUIs.DYNAMIC_DROPDOWN_BY_HEADER, dynamicTextValue);
		}
		return text;
	}

	// Lấy text trên ô điền Mat khau xac thuc
	public String getTextInDynamicPasswordInput(AppiumDriver<MobileElement> driver, String dynamicID) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_TEXT_BOX_WITH_ID, dynamicID);
		if (status == true) {
			text = getTextElement(driver, DynamicPageUIs.DYNAMIC_TEXT_BOX_WITH_ID, dynamicID);
		}
		return text;

	}

	// Chuyen tu Array sang List
	public List<String> arrayToArrayList(String[] arr) {

		ArrayList<String> list = new ArrayList<String>();
		for (String item : arr) {
			list.add(item);
		}
		return list;
	}

	// Lấy text ngày đặt vé
	public String getTextInDynamicDateTicket(AppiumDriver<MobileElement> driver, String... dynamicTextValue) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_DATE_SELECTED, dynamicTextValue);
		if (status == true) {
			text = getTextElement(driver, TrainTicketPageUIs.DYNAMIC_DATE_SELECTED, dynamicTextValue);
		}
		return text;
	}

	public boolean checkFormatMoney(String moneyInput, TransferInVCBRecurrent.Currency currency) {
		long longNumber;
		double doubleNumber;
		String money = moneyInput.split(" ")[0];
		if (!money.contains(",")) {
			return false;
		}
		money = money.replace(",", "");

		boolean result = true;
		if (currency == TransferInVCBRecurrent.Currency.VND) {

			try {
				longNumber = Long.parseLong(money);
				result = true;
			} catch (NumberFormatException e) {
				result = false;
			}

		} else if (currency == TransferInVCBRecurrent.Currency.CURRENCY) {
			try {
				doubleNumber = Double.parseDouble(money);
				result = true;
			} catch (NumberFormatException e) {
				result = false;
			}
		}

		return result;
	}

	// lay tai khoan mac dinh băng label
	public String getDynamicTextInDropDownByLable(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_DROPDOWN_BY_LABEL, dynamicTextValue);
		if (status == true) {
			text = getTextElement(driver, DynamicPageUIs.DYNAMIC_DROPDOWN_BY_LABEL, dynamicTextValue);
		}
		return text;

	}

	// Lấy text trường số lượng hành khách

	public String getTextInEditTextFieldByID(AppiumDriver<MobileElement> driver, String... dynamicID) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_TEXT_BOX_WITH_ID, dynamicID);
		if (status == true) {
			text = getTextElement(driver, DynamicPageUIs.DYNAMIC_TEXT_BOX_WITH_ID, dynamicID);
		}
		return text;

	}

	public String getTextTextViewByLinearLayoutID(AppiumDriver<MobileElement> driver, String... dynamicID) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_TEXTVIEW_BY_LINEARLAYOUT_ID, dynamicID);
		if (status == true) {
			text = getTextElement(driver, DynamicPageUIs.DYNAMIC_TEXTVIEW_BY_LINEARLAYOUT_ID, dynamicID);
		}
		return text;

	}

	public List<String> getListTextViewByLinearLayoutID(AppiumDriver<MobileElement> driver, String... dynamicID) {
		boolean status = false;
		List<String> text = null;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_TEXTVIEW_BY_LINEARLAYOUT_ID, dynamicID);
		if (status == true) {
			text = getTextInListElements(driver, DynamicPageUIs.DYNAMIC_TEXTVIEW_BY_LINEARLAYOUT_ID, dynamicID);
		}
		return text;

	}

	public List<String> getListTextViewByRelativeLayoutID(AppiumDriver<MobileElement> driver, String... dynamicID) {

		boolean status = false;
		List<String> text = null;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_TEXTVIEW_BY_RELATIVELAYOUT_ID, dynamicID);
		if (status == true) {
			text = getTextInListElements(driver, DynamicPageUIs.DYNAMIC_TEXTVIEW_BY_RELATIVELAYOUT_ID, dynamicID);
		}
		return text;

	}

	public List<String> getListImageViewByLinearLayoutID(AppiumDriver<MobileElement> driver, String... dynamicID) {
		boolean status = false;
		List<String> text = null;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_IMAGEVIEW_BY_LINEARLAYOUT_ID, dynamicID);
		if (status == true) {
			text = getTextInListElements(driver, DynamicPageUIs.DYNAMIC_IMAGEVIEW_BY_LINEARLAYOUT_ID, dynamicID);
		}
		return text;
	}

	// Hiển thị time ngay book
	public String getDynamicDateTime(AppiumDriver<MobileElement> driver, String dynamicID) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_DATE, dynamicID);
		if (status == true) {
			text = getTextElement(driver, TrainTicketPageUIs.DYNAMIC_DATE, dynamicID);
		}
		return text;

	}

	public String getDynamicTitleSelectDate(AppiumDriver<MobileElement> driver, String dynamicText) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_TITLE_SELECT_DATE, dynamicText);
		if (status == true) {
			text = getTextElement(driver, TrainTicketPageUIs.DYNAMIC_TITLE_SELECT_DATE, dynamicText);
		}
		return text;

	}

//Lay thu trong tuan
	public String getDynamicTitleWeek(AppiumDriver<MobileElement> driver, String... dynamicText) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_TITLE_SELECT_WEEK, dynamicText);
		if (status == true) {
			text = getTextElement(driver, TrainTicketPageUIs.DYNAMIC_TITLE_SELECT_WEEK, dynamicText);
		}
		return text;
	}

	public String getDynamicTextOld(AppiumDriver<MobileElement> driver, String... dynamicText) {

		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_NUMBER_CUSTOMER, dynamicText);
		if (status == true) {
			text = getTextElement(driver, TrainTicketPageUIs.DYNAMIC_NUMBER_CUSTOMER, dynamicText);
		}
		return text;
	}

}
