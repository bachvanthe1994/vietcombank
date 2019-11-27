package commons;

import static io.appium.java_client.touch.LongPressOptions.longPressOptions;
import static io.appium.java_client.touch.TapOptions.tapOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;
import static java.time.Duration.ofSeconds;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import vietcombankUI.DynamicPageUIs;

public class AbstractPage {
	int longTime = 30;
	int shortTime = 5;
	long longTime1 = 30;
	long shortTime1 = 5;

	public void TabtoElement(AndroidDriver<AndroidElement> driver, String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		TouchAction touch = new TouchAction(driver);
		touch.tap(tapOptions().withElement(element(element))).perform();

	}

	public void navigateBack(AndroidDriver<AndroidElement> driver) {
		driver.navigate().back();
	}

	public void navigatForward(AndroidDriver<AndroidElement> driver) {
		driver.navigate().forward();
		;
	}

	public void LongPressToElement(AndroidDriver<AndroidElement> driver, String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		TouchAction touch = new TouchAction(driver);
		touch.longPress(longPressOptions().withElement(element(element)).withDuration(ofSeconds(2))).release().perform();

	}

	public void SwipingFromOneElementToOtherElement(AndroidDriver<AndroidElement> driver, String locator1, String locator2) {
		WebElement element1 = driver.findElement(By.xpath(locator1));
		WebElement element2 = driver.findElement(By.xpath(locator2));
		TouchAction touch = new TouchAction(driver);
		touch.longPress(longPressOptions().withElement(element(element1)).withDuration(ofSeconds(2))).moveTo(element(element2)).release().perform();

	}

	public void DragAndDrop(AndroidDriver<AndroidElement> driver, String locator1, String locator2) {
		WebElement element1 = driver.findElement(By.xpath(locator1));
		WebElement element2 = driver.findElement(By.xpath(locator2));
		TouchAction touch = new TouchAction(driver);
		touch.longPress(longPressOptions().withElement(element(element1))).moveTo(element(element2)).release().perform();
	}

	public void BackKeyCode(AndroidDriver<AndroidElement> driver) {
		driver.pressKey(new KeyEvent(AndroidKey.BACK));

	}

	public void hideKeyBoard(AndroidDriver<AndroidElement> driver) {
		driver.hideKeyboard();

	}

	public void HomeKeyCode(AndroidDriver<AndroidElement> driver) {
		driver.pressKey(new KeyEvent(AndroidKey.HOME));

	}

	public void EnterKeyCode(AndroidDriver<AndroidElement> driver) {
		driver.pressKey(new KeyEvent(AndroidKey.ENTER));

	}

	public String getToastMessage(AndroidDriver<AndroidElement> driver) {
		String toastMessage = driver.findElement(By.xpath("//android.widget.Toast[1]")).getAttribute("name");
		return toastMessage;
	}

	public double convertToDouble(String value) {
		double value1 = Double.parseDouble(value.substring(1));
		return value1;
	}

	public void SwitchToContext(AndroidDriver<AndroidElement> driver, String webViewName) {
		Set<String> contexts = driver.getContextHandles();
		driver.context(webViewName);
	}

	public void ScrollToText(AndroidDriver<AndroidElement> driver, String dynamicTextValue) {
		try {
			driver.findElementByAndroidUIAutomator("new UiScrollable(" + "new UiSelector().scrollable(true)).scrollIntoView(" + "new UiSelector().textContains(\"" + dynamicTextValue + "\"));");
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}

	public void clickToElementByJava(AndroidDriver<AndroidElement> driver, String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].click();", element);

	}

	public void clickToElement(AndroidDriver<AndroidElement> driver, String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		if (element.isDisplayed()) {
			element.click();
		} else {
			clickToElementByJava(driver, locator);
		}
	}

	public void clickToElement(AndroidDriver<AndroidElement> driver, String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		WebElement element = driver.findElement(By.xpath(locator));
		if (element.isDisplayed()) {
			element.click();
		} else {
			clickToElementByJava(driver, locator);
		}
	}

	public void clearText(AndroidDriver<AndroidElement> driver, String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		WebElement element = driver.findElement(By.xpath(locator));

		element.clear();
	}

	public void checkToElement(AndroidDriver<AndroidElement> driver, String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		if (!element.isSelected()) {
			element.click();
		}
	}

	public void uncheckToElement(AndroidDriver<AndroidElement> driver, String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		if (element.getAttribute("checked").equalsIgnoreCase("true")) {
			element.click();
		}
	}

	public void uncheckToElement(AndroidDriver<AndroidElement> driver, String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		WebElement element = driver.findElement(By.xpath(locator));
		if (element.getAttribute("checked").equalsIgnoreCase("true")) {
			element.click();
		}
	}

	public void sendKeyToElement(AndroidDriver<AndroidElement> driver, String locator, String value) {
		WebElement element = driver.findElement(By.xpath(locator));
		element.clear();
		element.sendKeys(value);
	}

	public void sendKeyToElement(AndroidDriver<AndroidElement> driver, String locator, String value, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		WebElement element = driver.findElement(By.xpath(locator));
		element.clear();
		element.sendKeys(value);
	}

	public String getAttributeValue(AndroidDriver<AndroidElement> driver, String locator, String attribute) {
		WebElement element = driver.findElement(By.xpath(locator));
		return element.getAttribute(attribute);
	}

	public String getTextElement(AndroidDriver<AndroidElement> driver, String locator, String... dynamicValue) {
		waitForElementVisible(driver, locator, dynamicValue);
		locator = String.format(locator, (Object[]) dynamicValue);
		WebElement element = driver.findElement(By.xpath(locator));
		return element.getText();
	}

	public String getTextElement(AndroidDriver<AndroidElement> driver, String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		return element.getText();
	}

	public int countElementNumber(AndroidDriver<AndroidElement> driver, String locator) {
		List<AndroidElement> elements = driver.findElements(By.xpath(locator));
		return elements.size();
	}

	public int countElementNumber(AndroidDriver<AndroidElement> driver, String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		List<AndroidElement> elements = driver.findElements(By.xpath(locator));
		return elements.size();
	}

	public boolean isControlSelected(AndroidDriver<AndroidElement> driver, String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		if (element.getAttribute("checked").equalsIgnoreCase("true")) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isControlSelected(AndroidDriver<AndroidElement> driver, String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		WebElement element = driver.findElement(By.xpath(locator));
		if (element.getAttribute("checked").equalsIgnoreCase("true")) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isControlDisplayed(AndroidDriver<AndroidElement> driver, String locator) {
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

	public boolean isControlUnDisplayed(AndroidDriver<AndroidElement> driver, String locator) {
		overRideTimeOut(driver, shortTime);
		List<AndroidElement> elements = driver.findElements(By.xpath(locator));
		if (elements.size() > 0 && elements.get(0).isDisplayed()) {
			overRideTimeOut(driver, longTime);
			return false;
		} else {
			overRideTimeOut(driver, longTime);
			return true;

		}
	}

	public void overRideTimeOut(AndroidDriver<AndroidElement> driver, int time) {
		driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
	}

	public boolean isControlEnabled(AndroidDriver<AndroidElement> driver, String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		if (element.getAttribute("enabled").equalsIgnoreCase("true")) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isControlEnabled(AndroidDriver<AndroidElement> driver, String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		WebElement element = driver.findElement(By.xpath(locator));
		if (element.getAttribute("enabled").equalsIgnoreCase("true")) {
			return true;
		} else {
			return false;
		}
	}

	public void switchToIframe(AndroidDriver<AndroidElement> driver, String locator) {
		WebElement frame = driver.findElement(By.xpath(locator));
		driver.switchTo().frame(frame);
	}

	public void backToDefault(AndroidDriver<AndroidElement> driver, String locator) {
		driver.switchTo().defaultContent();
	}

//upload
	public void uploadSingleFile(AndroidDriver<AndroidElement> driver, String locator, String fileName) {
		WebElement addFileButton = driver.findElement(By.xpath(locator));
		String rootFolder = System.getProperty("user.dir");
		String filePath = rootFolder + "\\Upload_file\\" + fileName;
		addFileButton.sendKeys(filePath);

	}

	public void uploadMultipleFilesInQueue(AndroidDriver<AndroidElement> driver, String locator, String fileName01, String fileName02, String fileName03) {
		String rootFolder = System.getProperty("user.dir");
		String filePath01 = rootFolder + "\\Upload_file\\" + fileName01;
		String filePath02 = rootFolder + "\\Upload_file\\" + fileName02;
		String filePath03 = rootFolder + "\\Upload_file\\" + fileName03;
		String[] files = { filePath01, filePath02, filePath03 };
		for (String file : files) {
			WebElement addFileButton = driver.findElement(By.xpath(locator));

			addFileButton.sendKeys(file);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void uploadMultipleFilesAtOnce(AndroidDriver<AndroidElement> driver, String locator, String fileName01, String fileName02, String fileName03) {
		WebElement addFileButton = driver.findElement(By.xpath(locator));
		String rootFolder = System.getProperty("user.dir");
		String filePath01 = rootFolder + "\\Upload_file\\" + fileName01;
		String filePath02 = rootFolder + "\\Upload_file\\" + fileName02;
		String filePath03 = rootFolder + "\\Upload_file\\" + fileName03;
		String filePath = filePath01 + "\n" + filePath02 + "\n" + filePath03;

		addFileButton.sendKeys(filePath);
	}

	public void uploadByAutoIT(AndroidDriver<AndroidElement> driver, String uploadButtonlocator, String fileName01, String fileName02, String fileName03) throws Exception {
		String rootFolder = System.getProperty("user.dir");
		String filePath01 = rootFolder + "\\Upload_file\\" + fileName01;
		String filePath02 = rootFolder + "\\Upload_file\\" + fileName02;
		String filePath03 = rootFolder + "\\Upload_file\\" + fileName03;
		String[] files = { filePath01, filePath02, filePath03 };
		String chromePath = rootFolder + "\\Upload_file\\chrome.exe";
		String firefoxPath = rootFolder + "\\Upload_file\\firefox.exe";
		String iePath = rootFolder + "\\Upload_file\\ie.exe";
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		for (String file : files) {

			WebElement uploadFileButton = driver.findElement(By.xpath(uploadButtonlocator));
			jsExecutor.executeScript("arguments[0].click();", uploadFileButton);

			uploadFileButton.click();

			if (driver.toString().contains("chrome")) {
				Runtime.getRuntime().exec(new String[] { chromePath, file });
			} else if (driver.toString().contains("firefox")) {
				Runtime.getRuntime().exec(new String[] { firefoxPath, file });
			} else if (driver.toString().contains("ie")) {
				Runtime.getRuntime().exec(new String[] { iePath, file });
			}
			Thread.sleep(2000);
		}

	}

//waits
	public void implicitWaitLongTime(AndroidDriver<AndroidElement> driver) {

		driver.manage().timeouts().implicitlyWait(longTime1, TimeUnit.SECONDS);

	}

	public void implicitWaitShortTime(AndroidDriver<AndroidElement> driver) {

		driver.manage().timeouts().implicitlyWait(shortTime1, TimeUnit.SECONDS);
	}

	public void waitForElementPresent(AndroidDriver<AndroidElement> driver, String locator) {
		WebDriverWait wait = new WebDriverWait(driver, longTime1);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));
	}

	public void waitForAllElementPresent(AndroidDriver<AndroidElement> driver, String locator) {
		WebDriverWait wait = new WebDriverWait(driver, longTime1);
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(locator)));
	}

	public void waitForAllElementPresent(AndroidDriver<AndroidElement> driver, String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		WebDriverWait wait = new WebDriverWait(driver, longTime1);
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(locator)));
	}

	public void waitForElementVisible(AndroidDriver<AndroidElement> driver, String locator) {

		WebDriverWait wait = new WebDriverWait(driver, longTime1);
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
		} catch (Exception e) {
			System.out.println(e.getMessage());

		}
	}

	public void waitForElementClickable(AndroidDriver<AndroidElement> driver, String locator) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator)));
	}

	public void waitForAllElementsVisible(AndroidDriver<AndroidElement> driver, String locator) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(locator)));
	}

	public void waitForElementInvisible(AndroidDriver<AndroidElement> driver, String locator) {
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

	public String getAttributeValue(AndroidDriver<AndroidElement> driver, String locator, String attribute, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		WebElement element = driver.findElement(By.xpath(locator));
		return element.getAttribute(attribute);
	}

	public void clickToElementByJava(AndroidDriver<AndroidElement> driver, String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		WebElement element = driver.findElement(By.xpath(locator));
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].click();", element);
	}

	public boolean isControlDisplayed(AndroidDriver<AndroidElement> driver, String locator, String... dynamicValue) {
		try {
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			locator = String.format(locator, (Object[]) dynamicValue);
			WebElement element = driver.findElement(By.xpath(locator));
			boolean status = element.isDisplayed();
			System.out.println("element" + status);
			return status;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	public boolean isControlUnDisplayed(AndroidDriver<AndroidElement> driver, String locator, String... dynamicValue) {
		overRideTimeOut(driver, shortTime);
		locator = String.format(locator, (Object[]) dynamicValue);
		List<AndroidElement> elements = driver.findElements(By.xpath(locator));
		if (elements.size() > 0 && elements.get(0).isDisplayed()) {
			overRideTimeOut(driver, longTime);
			return false;
		} else {
			overRideTimeOut(driver, longTime);
			return true;

		}
	}

	public void sleep(AndroidDriver<AndroidElement> driver, long milisecond) {
		try {
			Thread.sleep(milisecond);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void removeAttributeInDOM(AndroidDriver<AndroidElement> driver, String locator, String attribute, String... value) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		locator = String.format(locator, (Object[]) value);
		WebElement element = driver.findElement(By.xpath(locator));
		js.executeScript("arguments[0].removeAttribute('" + attribute + "');", element);
		sleep(driver, 1000);
	}

	public void waitForElementVisible(AndroidDriver<AndroidElement> driver, String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
	}

	public void waitForElementInvisible(AndroidDriver<AndroidElement> driver, String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(locator)));
	}

	public String getOTPText(AndroidDriver<AndroidElement> driver, String bankName, String allMesssage) {

		clickToElement(driver, "//android.widget.TextView[contains(@text,\"" + bankName + "\")]");
		waitForAllElementsVisible(driver, allMesssage);
		int no = countElementNumber(driver, allMesssage);
		String message = driver.findElements(By.xpath(allMesssage)).get(no - 1).getText();
		int index = message.indexOf("So OTP");
		String otp = message.substring(index + 8, index + 14);
		System.out.println(otp);
		return otp;
	}

	public String[] getAllValueInList(AndroidDriver<AndroidElement> driver, String AllChidren) {
		WebDriverWait waitExplicit = new WebDriverWait(driver, 60);
		waitExplicit.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(AllChidren)));
		List<AndroidElement> elements = driver.findElements(By.xpath(AllChidren));
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
			dayInWeek = "Thứ hai";
			System.out.println(dayInWeek);
			break;
		case 2:
			dayInWeek = "Thứ ba";
			System.out.println(dayInWeek);
			break;
		case 3:
			dayInWeek = "Thứ tư";
			System.out.println(dayInWeek);
			break;
		case 4:
			dayInWeek = "Thứ năm";
			System.out.println(dayInWeek);
			break;
		case 5:
			dayInWeek = "Thứ sáu";
			System.out.println(dayInWeek);
			;
			break;
		case 6:
			dayInWeek = "Thứ bảy";
			System.out.println(dayInWeek);
			break;
		case 7:
			dayInWeek = "Chủ nhật";
			System.out.println(dayInWeek);
			break;
		}

		return dayInWeek;

	}

	public void clickToDynamicAcceptButton(AndroidDriver<AndroidElement> driver, String dynamicIDValue) {
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_ACCEPT_BUTTON_OR_BUTTON, dynamicIDValue);
		clickToElement(driver, DynamicPageUIs.DYNAMIC_ACCEPT_BUTTON_OR_BUTTON, dynamicIDValue);

	}

	public void clickToDynamicButton(AndroidDriver<AndroidElement> driver, String dynamicTextValue) {
		ScrollToText(driver, dynamicTextValue);
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
		clickToElement(driver, DynamicPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
		
	}
	
	public void clickToDynamicButionLinkOrLinkText(AndroidDriver<AndroidElement> driver, String dynamicTextValue) {
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
		clickToElement(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);

	}


	public void inputToDynamicInputBox(AndroidDriver<AndroidElement> driver, String inputValue, String dynamicTextValue) {
		clearText(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, dynamicTextValue);
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, dynamicTextValue);
		sendKeyToElement(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, inputValue, dynamicTextValue);

	}

	public void inputToDynamicOtpOrPIN(AndroidDriver<AndroidElement> driver, String inputValue, String dynamicTextValue) {
		clearText(driver, DynamicPageUIs.DYNAMIC_OTP_INPUT, dynamicTextValue);
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_OTP_INPUT, dynamicTextValue);
		sendKeyToElement(driver, DynamicPageUIs.DYNAMIC_OTP_INPUT, inputValue, dynamicTextValue);
	}
	
	public boolean isDynamicButtonDisplayed(AndroidDriver<AndroidElement> driver, String dynamicTextValue) {
		ScrollToText(driver, dynamicTextValue);
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
		return isControlDisplayed(driver, DynamicPageUIs.DYNAMIC_BUTTON, dynamicTextValue);

	}

	public boolean isDynamicMessageAndLabelTextDisplayed(AndroidDriver<AndroidElement> driver, String dynamicTextValue) {
		ScrollToText(driver, dynamicTextValue);
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
		return isControlDisplayed(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);

	}

	public boolean isDynamicMessageAndLabelTextUndisplayed(AndroidDriver<AndroidElement> driver, String dynamicTextValue) {
		waitForElementInvisible(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
		return isControlUnDisplayed(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);

	}


	public boolean isDynamicTextInInputBoxDisPlayed(AndroidDriver<AndroidElement> driver, String... dynamicTextValue) {
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, dynamicTextValue);
		return isControlDisplayed(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, dynamicTextValue);
	}
	
	public String getTextDynamicTextInInputBox(AndroidDriver<AndroidElement> driver, String... dynamicTextValue) {
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, dynamicTextValue);
		return getTextElement(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, dynamicTextValue);
	}
	
	public String getTextDynamicPopup(AndroidDriver<AndroidElement> driver, String dynamicTextValue) {
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
		return getTextElement(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
		
	}
	
	public boolean isDynamicVerifyTextOnButton(AndroidDriver<AndroidElement> driver, String dynamicTextValue) {
		ScrollToText(driver, dynamicTextValue);
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
		return isControlDisplayed(driver, DynamicPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
	}
  
}
