
package commons;

import static io.appium.java_client.touch.LongPressOptions.longPressOptions;
import static io.appium.java_client.touch.TapOptions.tapOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;
import static java.time.Duration.ofSeconds;

import java.text.Normalizer;
import java.util.ArrayList;
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

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.offset.PointOption;
import vietcombankUI.DynamicPageUIs;
import vietcombankUI.TransferMoneyOutSideVCBPageUIs;

public class AbstractPage {
	int longTime = 30;
	int shortTime = 5;
	long longTime1 = 30;
	long shortTime1 = 5;

	public void TabtoElement(AppiumDriver<MobileElement> driver, String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		TouchAction touch = new TouchAction(driver);
		touch.tap(tapOptions().withElement(element(element))).perform();

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

	public void BackKeyCode(AndroidDriver<MobileElement> driver) {
		driver.pressKey(new KeyEvent(AndroidKey.BACK));

	}

	public void hideKeyBoard(AppiumDriver<MobileElement> driver) {
		driver.hideKeyboard();

	}

	public void HomeKeyCode(AndroidDriver<MobileElement> driver) {
		driver.pressKey(new KeyEvent(AndroidKey.HOME));

	}

	public void EnterKeyCode(AndroidDriver<MobileElement> driver) {
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

	public void scrollToText(MobileDriver<MobileElement> driver, String text) {
		Dimension size = driver.manage().window().getSize();
		int x = size.getWidth() / 2;
		int startY = (int) (size.getHeight() * 0.70);
		int endY = (int) (size.getHeight() * 0.30);
		TouchAction touch = new TouchAction(driver);
		for (int i = 0; i < 20; i++) {
			String sourceCode = driver.getPageSource();
			if (sourceCode.contains(text)) {
				break;
			} else {
				touch.press(PointOption.point(x, startY)).moveTo(PointOption.point(x, endY)).release().perform();
				sleep(driver, 1000);
			}
		}
	}

	public void clickToElement(AppiumDriver<MobileElement> driver, String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		if (element.isDisplayed()) {
			element.click();
		} else {
			clickToElementByJava(driver, locator);
		}
	}

	public void clickToElement(AppiumDriver<MobileElement> driver, String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		WebElement element = driver.findElement(By.xpath(locator));
		if (element.isDisplayed()) {
			element.click();
		} else {
			clickToElementByJava(driver, locator);
		}
	}

	public void clickToOneOfElement(AppiumDriver<MobileElement> driver, String locator, int elementIndex, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		List<MobileElement> element = driver.findElements(By.xpath(locator));
		element.get(elementIndex).click();

	}

	public void clearText(AppiumDriver<MobileElement> driver, String locator, String... dynamicTextValue) {
		locator = String.format(locator, (Object[]) dynamicTextValue);
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

	public String getAttributeValue(AppiumDriver<MobileElement> driver, String locator, String attribute) {
		WebElement element = driver.findElement(By.xpath(locator));
		return element.getAttribute(attribute);
	}

	public String getTextElement(AppiumDriver<MobileElement> driver, String locator, String... dynamicValue) {

		locator = String.format(locator, (Object[]) dynamicValue);
		WebElement element = driver.findElement(By.xpath(locator));
		return element.getText();
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

	public String getTextElement(AppiumDriver<MobileElement> driver, String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		return element.getText();
	}

	public int countElementNumber(AppiumDriver<MobileElement> driver, String locator) {
		List<MobileElement> elements = driver.findElements(By.xpath(locator));
		return elements.size();
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

	public boolean isControlSelected(AppiumDriver<MobileElement> driver, String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		WebElement element = driver.findElement(By.xpath(locator));
		if (element.getAttribute("checked").equalsIgnoreCase("true")) {
			return true;
		} else {
			return false;
		}
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

	public boolean isControlUnDisplayed(AppiumDriver<MobileElement> driver, String locator) {
		overRideTimeOut(driver, shortTime);
		List<MobileElement> elements = driver.findElements(By.xpath(locator));
		if (elements.size() > 0 && elements.get(0).isDisplayed()) {
			overRideTimeOut(driver, longTime);
			return false;
		} else {
			overRideTimeOut(driver, longTime);
			return true;

		}
	}

	public void overRideTimeOut(AppiumDriver<MobileElement> driver, int time) {
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

	public void switchToIframe(AppiumDriver<MobileElement> driver, String locator) {
		WebElement frame = driver.findElement(By.xpath(locator));
		driver.switchTo().frame(frame);
	}

	public void backToDefault(AppiumDriver<MobileElement> driver) {
		driver.switchTo().defaultContent();
	}

//upload
	public void uploadSingleFile(AppiumDriver<MobileElement> driver, String locator, String fileName) {
		WebElement addFileButton = driver.findElement(By.xpath(locator));
		String rootFolder = System.getProperty("user.dir");
		String filePath = rootFolder + "\\Upload_file\\" + fileName;
		addFileButton.sendKeys(filePath);

	}

	public void uploadMultipleFilesInQueue(AppiumDriver<MobileElement> driver, String locator, String fileName01, String fileName02, String fileName03) {
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

	public void uploadMultipleFilesAtOnce(AppiumDriver<MobileElement> driver, String locator, String fileName01, String fileName02, String fileName03) {
		WebElement addFileButton = driver.findElement(By.xpath(locator));
		String rootFolder = System.getProperty("user.dir");
		String filePath01 = rootFolder + "\\Upload_file\\" + fileName01;
		String filePath02 = rootFolder + "\\Upload_file\\" + fileName02;
		String filePath03 = rootFolder + "\\Upload_file\\" + fileName03;
		String filePath = filePath01 + "\n" + filePath02 + "\n" + filePath03;

		addFileButton.sendKeys(filePath);
	}

	public void uploadByAutoIT(AppiumDriver<MobileElement> driver, String uploadButtonlocator, String fileName01, String fileName02, String fileName03) throws Exception {
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

	public void waitForElementVisible(AppiumDriver<MobileElement> driver, String locator) {

		WebDriverWait wait = new WebDriverWait(driver, longTime1);
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
		} catch (Exception e) {
			System.out.println(e.getMessage());

		}
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

	public String getAttributeValue(AppiumDriver<MobileElement> driver, String locator, String attribute, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		WebElement element = driver.findElement(By.xpath(locator));
		return element.getAttribute(attribute);
	}

	public void clickToElementByJava(AppiumDriver<MobileElement> driver, String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		WebElement element = driver.findElement(By.xpath(locator));
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].click();", element);
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

	public String removeUnicode(AppiumDriver<MobileElement> driver, String locator) {

		String temp = Normalizer.normalize(locator, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(temp).replaceAll("");
	}

	public void sleep(MobileDriver<MobileElement> driver, long milisecond) {
		try {
			Thread.sleep(milisecond);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void removeAttributeInDOM(AppiumDriver<MobileElement> driver, String locator, String attribute, String... value) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		locator = String.format(locator, (Object[]) value);
		WebElement element = driver.findElement(By.xpath(locator));
		js.executeScript("arguments[0].removeAttribute('" + attribute + "');", element);
		sleep(driver, 1000);
	}

	public void waitForElementVisible(AppiumDriver<MobileElement> driver, String locator, String... dynamicIndex1Index2) {
		locator = String.format(locator, (Object[]) dynamicIndex1Index2);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
	}

	public void waitForElementInvisible(AppiumDriver<MobileElement> driver, String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		try {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(locator)));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
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

	public void clickToDynamicAcceptButton(AppiumDriver<MobileElement> driver, String dynamicIDValue) {
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_ACCEPT_BUTTON_OR_BUTTON, dynamicIDValue);
		clickToElement(driver, DynamicPageUIs.DYNAMIC_ACCEPT_BUTTON_OR_BUTTON, dynamicIDValue);

	}

	public void clickToDynamicButton(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
		scrollToText(driver, dynamicTextValue);
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
		clickToElement(driver, DynamicPageUIs.DYNAMIC_BUTTON, dynamicTextValue);

	}

	public void clickToDynamicDropDown(AppiumDriver<MobileElement> driver, String dymanicText) {
		scrollToText(driver, dymanicText);
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_DROPDOWN_BY_LABEL, dymanicText);
		clickToElement(driver, DynamicPageUIs.DYNAMIC_DROPDOWN_BY_LABEL, dymanicText);
	}

	public void clickToDynamicButtonLinkOrLinkText(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
		scrollToText(driver, dynamicTextValue);
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
		clickToElement(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);

	}

	public void clickToDynamicInputBoxByHeader(AppiumDriver<MobileElement> driver, String... dynamicTextValue) {
		scrollToText(driver, dynamicTextValue[0]);
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX_BY_HEADER, dynamicTextValue);
		clickToElement(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX_BY_HEADER, dynamicTextValue);

	}

	public void clickToDynamicDropdownAndDateTimePicker(AppiumDriver<MobileElement> driver, String dynamicID) {
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY, dynamicID);
		clickToElement(driver, DynamicPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY, dynamicID);

	}

	public void clickToDynamicBottomMenuOrCloseIcon(AppiumDriver<MobileElement> driver, String dynamicID) {
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BOTTOM_MENU_CLOSE_ICON, dynamicID);
		clickToElement(driver, DynamicPageUIs.DYNAMIC_BOTTOM_MENU_CLOSE_ICON, dynamicID);

	}

	public void clickToDynamicCloseIcon(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_CLOSE_ICON, dynamicTextValue);
		clickToElement(driver, DynamicPageUIs.DYNAMIC_CLOSE_ICON, dynamicTextValue);

	}

	public void clickToDynamicBackIcon(AppiumDriver<MobileElement> driver, String dynamicTextValue) {

		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BACK_ICON, dynamicTextValue);
		clickToElement(driver, DynamicPageUIs.DYNAMIC_BACK_ICON, dynamicTextValue);

	}

	public void clickToDynamicIcon(AppiumDriver<MobileElement> driver, String dynamicTextValue) {

		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_ICON, dynamicTextValue);
		clickToElement(driver, DynamicPageUIs.DYNAMIC_ICON, dynamicTextValue);

	}

	public void clickToDynamicInput(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, dynamicTextValue);
		clickToElement(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, dynamicTextValue);

	}

	public void clickToDynamicTransactionInReport(AppiumDriver<MobileElement> driver, String... dynamicIndexAndID) {
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_TRANSACTION_INFO_IN_REPORT, dynamicIndexAndID);
		clickToElement(driver, DynamicPageUIs.DYNAMIC_TRANSACTION_INFO_IN_REPORT, dynamicIndexAndID);
	}

	public void clickToDynamicTransactionInTransactionOrderStatus(AppiumDriver<MobileElement> driver, String... dynamicIndex1ID2) {
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_TRANSACTION_INFO_IN_TRANSFER_ORDER_STATUS, dynamicIndex1ID2);
		clickToElement(driver, DynamicPageUIs.DYNAMIC_TRANSACTION_INFO_IN_TRANSFER_ORDER_STATUS, dynamicIndex1ID2);
	}

	public void clickToDynamicSuggestedMoney(AppiumDriver<MobileElement> driver, int index, String dynamicID) {
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY, dynamicID);
		clickToOneOfElement(driver, DynamicPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY, index, dynamicID);

	}

	public void inputToDynamicInputBox(AppiumDriver<MobileElement> driver, String inputValue, String dynamicTextValue) {
		scrollToText(driver, dynamicTextValue);
		clearText(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, dynamicTextValue);
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, dynamicTextValue);
		sendKeyToElement(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, inputValue, dynamicTextValue);

	}

	public void inputToDynamicInputBoxByHeader(AppiumDriver<MobileElement> driver, String inputValue, String... dynamicTextValue) {
		scrollToText(driver, dynamicTextValue[0]);
		clearText(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX_BY_HEADER, dynamicTextValue);
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX_BY_HEADER, dynamicTextValue);
		sendKeyToElement(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX_BY_HEADER, inputValue, dynamicTextValue);

	}

	public void pressKeyCodeIntoDynamicInputBoxByHeader(AppiumDriver<MobileElement> driver, List<Keys> dynamicKey, String... dynamicTextValue) {
		scrollToText(driver, dynamicTextValue[0]);
		clearText(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX_BY_HEADER, dynamicTextValue);
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX_BY_HEADER, dynamicTextValue);
		for (Keys key : dynamicKey) {
			driver.getKeyboard().sendKeys(key);
		}
	}

	public void inputToDynamicPasswordInput(AppiumDriver<MobileElement> driver, String inputValue, String dynamicTextValue) {
		clearText(driver, DynamicPageUIs.DYNAMIC_PASSWORD_INPUT, dynamicTextValue);
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_PASSWORD_INPUT, dynamicTextValue);
		sendKeyToElement(driver, DynamicPageUIs.DYNAMIC_PASSWORD_INPUT, inputValue, dynamicTextValue);
	}

	public void inputToDynamicLogInTextBox(AppiumDriver<MobileElement> driver, String inputValue, String dynamicTextValue) {
		clearText(driver, DynamicPageUIs.DYNAMIC_INPUT_IN_LOGIN, dynamicTextValue);
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_INPUT_IN_LOGIN, dynamicTextValue);
		sendKeyToElement(driver, DynamicPageUIs.DYNAMIC_INPUT_IN_LOGIN, inputValue, dynamicTextValue);

	}

	public void inputToDynamicOtpOrPIN(AppiumDriver<MobileElement> driver, String inputValue, String dynamicTextValue) {
		clearText(driver, DynamicPageUIs.DYNAMIC_OTP_INPUT, dynamicTextValue);
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_OTP_INPUT, dynamicTextValue);
		setValueToElement(driver, DynamicPageUIs.DYNAMIC_OTP_INPUT, inputValue, dynamicTextValue);
	}

	public void inputToDynamicPopupPasswordInput(AppiumDriver<MobileElement> driver, String inputValue, String dynamicTextValue) {
		clearText(driver, DynamicPageUIs.DYNAMIC_PASSWORD_INPUT, dynamicTextValue);
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_PASSWORD_INPUT, dynamicTextValue);
		sendKeyToElement(driver, DynamicPageUIs.DYNAMIC_PASSWORD_INPUT, inputValue, dynamicTextValue);
	}

	public void inputToPasswordConfirm(AppiumDriver<MobileElement> driver, String inputValue) {
		clearText(driver, TransferMoneyOutSideVCBPageUIs.PASSWORD_CONFIRM_INPUT);
		waitForElementVisible(driver, TransferMoneyOutSideVCBPageUIs.PASSWORD_CONFIRM_INPUT);
		setValueToElement(driver, TransferMoneyOutSideVCBPageUIs.PASSWORD_CONFIRM_INPUT, inputValue);
	}

	public boolean isDynamicButtonDisplayed(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
		scrollToText(driver, dynamicTextValue);
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
		return isControlDisplayed(driver, DynamicPageUIs.DYNAMIC_BUTTON, dynamicTextValue);

	}

	public boolean isDynamicMessageAndLabelTextDisplayed(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
		scrollToText(driver, dynamicTextValue);
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
		return isControlDisplayed(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);

	}

	public boolean isDynamicMessageAndLabelTextUndisplayed(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
		waitForElementInvisible(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
		return isControlUnDisplayed(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);

	}

	public boolean isDynamicTextInInputBoxDisPlayed(AppiumDriver<MobileElement> driver, String... dynamicTextValue) {
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, dynamicTextValue);
		return isControlDisplayed(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, dynamicTextValue);
	}

	public boolean isDynamicSuggestedMoneyUndisplayed(AppiumDriver<MobileElement> driver, String... dynamicTextValue) {
		waitForElementInvisible(driver, DynamicPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY, dynamicTextValue);
		return isControlUnDisplayed(driver, DynamicPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY, dynamicTextValue);
	}

	public boolean isDynamicSuggestedMoneyDisplayed(AppiumDriver<MobileElement> driver, String... dynamicTextValue) {
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY, dynamicTextValue);
		return isControlDisplayed(driver, DynamicPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY, dynamicTextValue);
	}

	public boolean isDynamicVerifyTextOnButton(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
		scrollToText(driver, dynamicTextValue);
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
		return isControlDisplayed(driver, DynamicPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
	}

	public String getDynamicTextInInputBox(AppiumDriver<MobileElement> driver, String... dynamicTextValue) {
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, dynamicTextValue);
		return getTextElement(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, dynamicTextValue);
	}

	public String getDynamicTextInInputBoxByHeader(AppiumDriver<MobileElement> driver, String... dynamicTextAndIndex) {
		scrollToText(driver, dynamicTextAndIndex[0]);
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX_BY_HEADER, dynamicTextAndIndex);
		return getTextElement(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX_BY_HEADER, dynamicTextAndIndex);
	}

	public String getTextDynamicDefaultSourceAccount(AppiumDriver<MobileElement> driver, String... dynamicTextValue) {
		scrollToText(driver, dynamicTextValue[0]);
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_DEFAULT_SOURCE_ACCOUNT, dynamicTextValue);
		return getTextElement(driver, DynamicPageUIs.DYNAMIC_DEFAULT_SOURCE_ACCOUNT, dynamicTextValue);
	}

	public String getDynamicTransferTimeAndMoney(AppiumDriver<MobileElement> driver, String... dynamicTextValue) {
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_TRANSFER_TIME, dynamicTextValue);
		return getTextElement(driver, DynamicPageUIs.DYNAMIC_TRANSFER_TIME, dynamicTextValue);
	}

	public String getTextDynamicPopup(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
		return getTextElement(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);

	}

	public String getTextInDynamicPopup(AppiumDriver<MobileElement> driver, String dynamicResourceID) {
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_TEXT_IN_POPUP, dynamicResourceID);
		return getTextElement(driver, DynamicPageUIs.DYNAMIC_TEXT_IN_POPUP, dynamicResourceID);

	}

	public String getDynamicTextInTransactionDetail(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
		scrollToText(driver, dynamicTextValue);
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, dynamicTextValue);
		return getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, dynamicTextValue);

	}

	public String getDynamicTextDetailByID(AppiumDriver<MobileElement> driver, String dynamicID) {

		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_TEXT_BY_ID, dynamicID);
		return getTextElement(driver, DynamicPageUIs.DYNAMIC_TEXT_BY_ID, dynamicID);

	}

	public String getDynamicTextInTextViewLine2(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
		scrollToText(driver, dynamicTextValue);
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_CONFIRM_SECOND_LINE_INFO, dynamicTextValue);
		return getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_SECOND_LINE_INFO, dynamicTextValue);

	}

	public String getTextInDynamicTransactionInReport(AppiumDriver<MobileElement> driver, String... dynamicIndex1Index2) {
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_TRANSACTION_INFO_IN_REPORT, dynamicIndex1Index2);
		return getTextElement(driver, DynamicPageUIs.DYNAMIC_TRANSACTION_INFO_IN_REPORT, dynamicIndex1Index2);

	}

	public String getTextInDynamicTransactionInTransferOrderStatus(AppiumDriver<MobileElement> driver, String... dynamicIndex1ResouceID) {
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_TRANSACTION_INFO_IN_TRANSFER_ORDER_STATUS, dynamicIndex1ResouceID);
		return getTextElement(driver, DynamicPageUIs.DYNAMIC_TRANSACTION_INFO_IN_TRANSFER_ORDER_STATUS, dynamicIndex1ResouceID);

	}

	public String getDynamicAmountLabel(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_LABEL_AMOUNT, dynamicTextValue);
		return getTextElement(driver, DynamicPageUIs.DYNAMIC_LABEL_AMOUNT, dynamicTextValue);

	}

	public String getDynamicAmountCostLabel(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_LABEL_COST, dynamicTextValue);
		return getTextElement(driver, DynamicPageUIs.DYNAMIC_LABEL_COST, dynamicTextValue);

	}

	public String getTextInDynamicDropdownOrDateTimePicker(AppiumDriver<MobileElement> driver, String dynamicID) {
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY, dynamicID);
		return getTextElement(driver, DynamicPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY, dynamicID);

	}

	public List<String> getListOfSuggestedMoney(AppiumDriver<MobileElement> driver, String dynamicID) {
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY, dynamicID);
		return getTextInListElements(driver, DynamicPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY, dynamicID);

	}

	public boolean isKeyBoardDisplayed(AndroidDriver<MobileElement> driver) {
		return driver.isKeyboardShown();
	}

	public String getMoneyByAccount(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
		scrollToText(driver, dynamicTextValue);
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_LABEL_MONEY_BY_ACCOUNT, dynamicTextValue);
		return getTextElement(driver, DynamicPageUIs.DYNAMIC_LABEL_MONEY_BY_ACCOUNT, dynamicTextValue);
	}

}
