
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

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import vietcombankUI.DynamicPageUIs;
import vietcombankUI.TransferMoneyOutSideVCBPageUIs;

public class AbstractPage {
	protected AndroidDriver<AndroidElement> driver;
	int longTime = 30;
	int shortTime = 5;
	long longTime1 = 30;
	long shortTime1 = 5;
	public AbstractPage(AndroidDriver<AndroidElement> mappingDriver) {
		driver = mappingDriver;
	}
	
	public void TabtoElement(String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		TouchAction touch = new TouchAction(driver);
		touch.tap(tapOptions().withElement(element(element))).perform();

	}

	public void navigateBack() {
		driver.navigate().back();
	}

	public void navigatForward(AndroidDriver<AndroidElement> driver) {
		driver.navigate().forward();
		;
	}

	public void LongPressToElement(String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		TouchAction touch = new TouchAction(driver);
		touch.longPress(longPressOptions().withElement(element(element)).withDuration(ofSeconds(2))).release().perform();

	}

	public void SwipingFromOneElementToOtherElement(String locator1, String locator2) {
		WebElement element1 = driver.findElement(By.xpath(locator1));
		WebElement element2 = driver.findElement(By.xpath(locator2));
		TouchAction touch = new TouchAction(driver);
		touch.longPress(longPressOptions().withElement(element(element1)).withDuration(ofSeconds(2))).moveTo(element(element2)).release().perform();

	}

	public void DragAndDrop(String locator1, String locator2) {
		WebElement element1 = driver.findElement(By.xpath(locator1));
		WebElement element2 = driver.findElement(By.xpath(locator2));
		TouchAction touch = new TouchAction(driver);
		touch.longPress(longPressOptions().withElement(element(element1))).moveTo(element(element2)).release().perform();
	}

	public void BackKeyCode() {
		driver.pressKey(new KeyEvent(AndroidKey.BACK));

	}

	public void hideKeyBoard() {
		driver.hideKeyboard();

	}

	public void HomeKeyCode() {
		driver.pressKey(new KeyEvent(AndroidKey.HOME));

	}

	public void EnterKeyCode() {
		driver.pressKey(new KeyEvent(AndroidKey.ENTER));

	}

	public String getToastMessage() {
		String toastMessage = driver.findElement(By.xpath("//android.widget.Toast[1]")).getAttribute("name");
		return toastMessage;
	}

	public double convertToDouble(String value) {
		double value1 = Double.parseDouble(value.substring(1));
		return value1;
	}

	public void SwitchToContext(String webViewName) {
		Set<String> contexts = driver.getContextHandles();
		driver.context(webViewName);
	}

	public void scrollToText(String dynamicTextValue) {
		try {
			driver.findElementByAndroidUIAutomator("new UiScrollable(" + "new UiSelector().scrollable(true)).scrollIntoView(" + "new UiSelector().textContains(\"" + dynamicTextValue + "\"));");
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}

	public void clickToElementByJava(String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].click();", element);

	}

	public void clickToElement(String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		if (element.isDisplayed()) {
			element.click();
		} else {
			clickToElementByJava(locator);
		}
	}

	public void clickToElement(String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		WebElement element = driver.findElement(By.xpath(locator));
		if (element.isDisplayed()) {
			element.click();
		} else {
			clickToElementByJava(locator);
		}
	}

	public void clearText(String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		WebElement element = driver.findElement(By.xpath(locator));

		element.clear();
	}

	public void checkToElement(String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		if (!element.isSelected()) {
			element.click();
		}
	}

	public void uncheckToElement(String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		if (element.getAttribute("checked").equalsIgnoreCase("true")) {
			element.click();
		}
	}

	public void uncheckToElement(String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		WebElement element = driver.findElement(By.xpath(locator));
		if (element.getAttribute("checked").equalsIgnoreCase("true")) {
			element.click();
		}
	}

	public void sendKeyToElement(String locator, String value) {
		WebElement element = driver.findElement(By.xpath(locator));
		element.clear();
		element.sendKeys(value);
	}

	public void sendKeyToElement(String locator, String value, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		WebElement element = driver.findElement(By.xpath(locator));
		element.clear();
		element.sendKeys(value);
	}

	public void setValueToElement(String locator, String value, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		MobileElement element1 = driver.findElement(By.xpath(locator));
		element1.clear();
		driver.getKeyboard().sendKeys(value);
	}

	public String getAttributeValue(String locator, String attribute) {
		WebElement element = driver.findElement(By.xpath(locator));
		return element.getAttribute(attribute);
	}

	public String getTextElement(String locator, String... dynamicValue) {

		locator = String.format(locator, (Object[]) dynamicValue);
		WebElement element = driver.findElement(By.xpath(locator));
		return element.getText();
	}

	public String getTextElement(String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		return element.getText();
	}

	public int countElementNumber(String locator) {
		List<AndroidElement> elements = driver.findElements(By.xpath(locator));
		return elements.size();
	}

	public int countElementNumber(String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		List<AndroidElement> elements = driver.findElements(By.xpath(locator));
		return elements.size();
	}

	public boolean isControlSelected(String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		if (element.getAttribute("checked").equalsIgnoreCase("true")) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isControlSelected(String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		WebElement element = driver.findElement(By.xpath(locator));
		if (element.getAttribute("checked").equalsIgnoreCase("true")) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isControlDisplayed(String locator) {
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

	public boolean isControlUnDisplayed(String locator) {
		overRideTimeOut(shortTime);
		List<AndroidElement> elements = driver.findElements(By.xpath(locator));
		if (elements.size() > 0 && elements.get(0).isDisplayed()) {
			overRideTimeOut(longTime);
			return false;
		} else {
			overRideTimeOut(longTime);
			return true;

		}
	}

	public void overRideTimeOut(int time) {
		driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
	}

	public boolean isControlEnabled(String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		if (element.getAttribute("enabled").equalsIgnoreCase("true")) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isControlEnabled(String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		WebElement element = driver.findElement(By.xpath(locator));
		if (element.getAttribute("enabled").equalsIgnoreCase("true")) {
			return true;
		} else {
			return false;
		}
	}

	public void switchToIframe(String locator) {
		WebElement frame = driver.findElement(By.xpath(locator));
		driver.switchTo().frame(frame);
	}

	public void backToDefault(String locator) {
		driver.switchTo().defaultContent();
	}

//upload
	public void uploadSingleFile(String locator, String fileName) {
		WebElement addFileButton = driver.findElement(By.xpath(locator));
		String rootFolder = System.getProperty("user.dir");
		String filePath = rootFolder + "\\Upload_file\\" + fileName;
		addFileButton.sendKeys(filePath);

	}

	public void uploadMultipleFilesInQueue(String locator, String fileName01, String fileName02, String fileName03) {
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

	public void uploadMultipleFilesAtOnce(String locator, String fileName01, String fileName02, String fileName03) {
		WebElement addFileButton = driver.findElement(By.xpath(locator));
		String rootFolder = System.getProperty("user.dir");
		String filePath01 = rootFolder + "\\Upload_file\\" + fileName01;
		String filePath02 = rootFolder + "\\Upload_file\\" + fileName02;
		String filePath03 = rootFolder + "\\Upload_file\\" + fileName03;
		String filePath = filePath01 + "\n" + filePath02 + "\n" + filePath03;

		addFileButton.sendKeys(filePath);
	}

	public void uploadByAutoIT(String uploadButtonlocator, String fileName01, String fileName02, String fileName03) throws Exception {
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
	public void implicitWaitLongTime() {

		driver.manage().timeouts().implicitlyWait(longTime1, TimeUnit.SECONDS);

	}

	public void implicitWaitShortTime() {

		driver.manage().timeouts().implicitlyWait(shortTime1, TimeUnit.SECONDS);
	}

	public void waitForElementPresent(String locator) {
		WebDriverWait wait = new WebDriverWait(driver, longTime1);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));
	}

	public void waitForAllElementPresent(String locator) {
		WebDriverWait wait = new WebDriverWait(driver, longTime1);
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(locator)));
	}

	public void waitForAllElementPresent(String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		WebDriverWait wait = new WebDriverWait(driver, longTime1);
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(locator)));
	}

	public void waitForElementVisible(String locator) {

		WebDriverWait wait = new WebDriverWait(driver, longTime1);
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
		} catch (Exception e) {
			System.out.println(e.getMessage());

		}
	}

	public void waitForElementClickable(String locator) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator)));
	}

	public void waitForAllElementsVisible(String locator) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(locator)));
	}

	public void waitForElementInvisible(String locator) {
		overRideTimeOut(shortTime);
		WebDriverWait wait = new WebDriverWait(driver, longTime);
		try {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(locator)));
			overRideTimeOut(longTime);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			overRideTimeOut(longTime);
		}
	}

	public String getAttributeValue(String locator, String attribute, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		WebElement element = driver.findElement(By.xpath(locator));
		return element.getAttribute(attribute);
	}

	public void clickToElementByJava(String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		WebElement element = driver.findElement(By.xpath(locator));
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].click();", element);
	}

	public boolean isControlDisplayed(String locator, String... dynamicValue) {
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

	public boolean isControlUnDisplayed(String locator, String... dynamicValue) {
		overRideTimeOut(shortTime);
		locator = String.format(locator, (Object[]) dynamicValue);
		List<AndroidElement> elements = driver.findElements(By.xpath(locator));
		if (elements.size() > 0 && elements.get(0).isDisplayed()) {
			overRideTimeOut(longTime);
			return false;
		} else {
			overRideTimeOut(longTime);
			return true;

		}
	}

	public void sleep(long milisecond) {
		try {
			Thread.sleep(milisecond);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void removeAttributeInDOM(String locator, String attribute, String... value) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		locator = String.format(locator, (Object[]) value);
		WebElement element = driver.findElement(By.xpath(locator));
		js.executeScript("arguments[0].removeAttribute('" + attribute + "');", element);
		sleep(1000);
	}

	public void waitForElementVisible(String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
	}

	public void waitForElementInvisible(String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(locator)));
	}

	public String getOTPText(String bankName, String allMesssage) {

		clickToElement("//android.widget.TextView[contains(@text,\"" + bankName + "\")]");
		waitForAllElementsVisible(allMesssage);
		int no = countElementNumber(allMesssage);
		String message = driver.findElements(By.xpath(allMesssage)).get(no - 1).getText();
		int index = message.indexOf("So OTP");
		String otp = message.substring(index + 8, index + 14);
		System.out.println(otp);
		return otp;
	}

	public String[] getAllValueInList(String AllChidren) {
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

	public void clickToDynamicAcceptButton(String dynamicIDValue) {
		waitForElementVisible(DynamicPageUIs.DYNAMIC_ACCEPT_BUTTON_OR_BUTTON, dynamicIDValue);
		clickToElement(DynamicPageUIs.DYNAMIC_ACCEPT_BUTTON_OR_BUTTON, dynamicIDValue);

	}
	
	public void clickToDynamicButton(String dynamicTextValue) {
		scrollToText(dynamicTextValue);
		waitForElementVisible(DynamicPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
		clickToElement(DynamicPageUIs.DYNAMIC_BUTTON, dynamicTextValue);

	}

	public void clickToDynamicDropDown(String dymanicText) {
		waitForElementVisible(DynamicPageUIs.DYNAMIC_DROPDOWN_BY_LABEL, dymanicText);
		clickToElement(DynamicPageUIs.DYNAMIC_DROPDOWN_BY_LABEL, dymanicText);
	}

	public void clickToDynamicButtonLinkOrLinkText(String dynamicTextValue) {
		scrollToText(dynamicTextValue);
		waitForElementVisible(DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
		clickToElement(DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);

	}

	public void clickToDynamicDropdownAndDateTimePicker(String dynamicID) {
		waitForElementVisible(DynamicPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID, dynamicID);
		clickToElement(DynamicPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID, dynamicID);

	}

	public void clickToDynamicBottomMenu(String dynamicID) {
		waitForElementVisible(DynamicPageUIs.DYNAMIC_BOTTOM_MENU, dynamicID);
		clickToElement(DynamicPageUIs.DYNAMIC_BOTTOM_MENU, dynamicID);

	}

	public void clickToDynamicCloseIcon(String dynamicTextValue) {
		waitForElementVisible(DynamicPageUIs.DYNAMIC_CLOSE_ICON, dynamicTextValue);
		clickToElement(DynamicPageUIs.DYNAMIC_CLOSE_ICON, dynamicTextValue);

	}

	public void clickToDynamicBackIcon(String dynamicTextValue) {

		waitForElementVisible(DynamicPageUIs.DYNAMIC_BACK_ICON, dynamicTextValue);
		clickToElement(DynamicPageUIs.DYNAMIC_BACK_ICON, dynamicTextValue);

	}

	public void clickToDynamicIcon(String dynamicTextValue) {

		waitForElementVisible(DynamicPageUIs.DYNAMIC_ICON, dynamicTextValue);
		clickToElement(DynamicPageUIs.DYNAMIC_ICON, dynamicTextValue);

	}

	public void clickToDynamicInput(String dynamicTextValue) {
		waitForElementVisible(DynamicPageUIs.DYNAMIC_INPUT_BOX, dynamicTextValue);
		clickToElement(DynamicPageUIs.DYNAMIC_INPUT_BOX, dynamicTextValue);

	}

	public void clickToDynamicTransaction(String... dynamicIndex1Index2) {
		waitForElementVisible(DynamicPageUIs.DYNAMIC_TRANSACTION_INFO, dynamicIndex1Index2);
		clickToElement(DynamicPageUIs.DYNAMIC_TRANSACTION_INFO, dynamicIndex1Index2);
	}

	public void inputToDynamicInputBox(String inputValue, String dynamicTextValue) {
		scrollToText(dynamicTextValue);
		clearText(DynamicPageUIs.DYNAMIC_INPUT_BOX, dynamicTextValue);
		waitForElementVisible(DynamicPageUIs.DYNAMIC_INPUT_BOX, dynamicTextValue);
		sendKeyToElement(DynamicPageUIs.DYNAMIC_INPUT_BOX, inputValue, dynamicTextValue);

	}

	public void inputToDynamicLogInTextBox(String inputValue, String dynamicTextValue) {
		clearText(DynamicPageUIs.DYNAMIC_INPUT_IN_LOGIN, dynamicTextValue);
		waitForElementVisible(DynamicPageUIs.DYNAMIC_INPUT_IN_LOGIN, dynamicTextValue);
		sendKeyToElement(DynamicPageUIs.DYNAMIC_INPUT_IN_LOGIN, inputValue, dynamicTextValue);

	}
	
	public void inputToDynamicOtpOrPIN(String inputValue, String dynamicTextValue) {
		clearText(DynamicPageUIs.DYNAMIC_OTP_INPUT, dynamicTextValue);
		waitForElementVisible(DynamicPageUIs.DYNAMIC_OTP_INPUT, dynamicTextValue);
		setValueToElement(DynamicPageUIs.DYNAMIC_OTP_INPUT, inputValue, dynamicTextValue);
	}

	public void inputToDynamicPopupPasswordInput(String inputValue, String dynamicTextValue) {
		clearText(DynamicPageUIs.DYNAMIC_PASSWORD_INPUT, dynamicTextValue);
		waitForElementVisible(DynamicPageUIs.DYNAMIC_PASSWORD_INPUT, dynamicTextValue);
		sendKeyToElement(DynamicPageUIs.DYNAMIC_PASSWORD_INPUT, inputValue, dynamicTextValue);
	}

	public void inputToPasswordConfirm(String inputValue) {
		clearText(TransferMoneyOutSideVCBPageUIs.PASSWORD_CONFIRM_INPUT);
		waitForElementVisible(TransferMoneyOutSideVCBPageUIs.PASSWORD_CONFIRM_INPUT);
		setValueToElement(TransferMoneyOutSideVCBPageUIs.PASSWORD_CONFIRM_INPUT, inputValue);
	}

	public boolean isDynamicButtonDisplayed(String dynamicTextValue) {
		scrollToText(dynamicTextValue);
		waitForElementVisible(DynamicPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
		return isControlDisplayed(DynamicPageUIs.DYNAMIC_BUTTON, dynamicTextValue);

	}

	public boolean isDynamicMessageAndLabelTextDisplayed(String dynamicTextValue) {
		scrollToText(dynamicTextValue);
		waitForElementVisible(DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
		return isControlDisplayed(DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);

	}

	public boolean isDynamicMessageAndLabelTextUndisplayed(String dynamicTextValue) {
		waitForElementInvisible(DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
		return isControlUnDisplayed(DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);

	}

	public boolean isDynamicTextInInputBoxDisPlayed(String... dynamicTextValue) {
		waitForElementVisible(DynamicPageUIs.DYNAMIC_INPUT_BOX, dynamicTextValue);
		return isControlDisplayed(DynamicPageUIs.DYNAMIC_INPUT_BOX, dynamicTextValue);
	}

	public boolean isDynamicVerifyTextOnButton(String dynamicTextValue) {
		scrollToText(dynamicTextValue);
		waitForElementVisible(DynamicPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
		return isControlDisplayed(DynamicPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
	}

	public String getTextDynamicTextInInputBox(String... dynamicTextValue) {
		waitForElementVisible(DynamicPageUIs.DYNAMIC_INPUT_BOX, dynamicTextValue);
		return getTextElement(DynamicPageUIs.DYNAMIC_INPUT_BOX, dynamicTextValue);
	}

	public String getDynamicTransferTimeAndMoney(String... dynamicTextValue) {
		waitForElementVisible(DynamicPageUIs.DYNAMIC_TRANSFER_TIME, dynamicTextValue);
		return getTextElement(DynamicPageUIs.DYNAMIC_TRANSFER_TIME, dynamicTextValue);
	}

	public String getTextDynamicPopup(String dynamicTextValue) {
		waitForElementVisible(DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
		return getTextElement(DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);

	}

	public String getDynamicTextInTextView(String dynamicTextValue) {
		scrollToText(dynamicTextValue);
		waitForElementVisible(DynamicPageUIs.DYNAMIC_CONFIRM_INFO, dynamicTextValue);
		return getTextElement(DynamicPageUIs.DYNAMIC_CONFIRM_INFO, dynamicTextValue);

	}

	public String getDynamicTextInTextViewLine2(String dynamicTextValue) {
		scrollToText(dynamicTextValue);
		waitForElementVisible(DynamicPageUIs.DYNAMIC_CONFIRM_SECOND_LINE_INFO, dynamicTextValue);
		return getTextElement(DynamicPageUIs.DYNAMIC_CONFIRM_SECOND_LINE_INFO, dynamicTextValue);

	}

	public String getTextInDynamicTransaction(String... dynamicIndex1Index2) {
		waitForElementVisible(DynamicPageUIs.DYNAMIC_TRANSACTION_INFO, dynamicIndex1Index2);
		return getTextElement(DynamicPageUIs.DYNAMIC_TRANSACTION_INFO, dynamicIndex1Index2);

	}

	public String getDynamicAmountLabel(String dynamicTextValue) {
		waitForElementVisible(DynamicPageUIs.DYNAMIC_LABEL_AMOUNT, dynamicTextValue);
		return getTextElement(DynamicPageUIs.DYNAMIC_LABEL_AMOUNT, dynamicTextValue);

	}

	public String getTextInDynamicDropdownOrDateTimePicker(String dynamicID) {
		waitForElementVisible(DynamicPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID, dynamicID);
		return getTextElement(DynamicPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID, dynamicID);

	}
}
