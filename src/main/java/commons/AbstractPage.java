
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
import org.testng.Assert;
import org.testng.Reporter;

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
import vietcombankUI.TransferMoneyOutSideVCBPageUIs;
import vietcombankUI.sdk.trainTicket.TrainTicketPageUIs;

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

	public boolean isControlForcus(AndroidDriver<AndroidElement> driver, String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		WebElement element = driver.findElement(By.xpath(locator));
		if (element.getAttribute("selected").equalsIgnoreCase("true")) {
			return true;
		} else {
			return false;
		}
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
		System.out.println(contexts);
		driver.context(webViewName);
	}

	public void scrollToText(AndroidDriver<AndroidElement> driver, String dynamicTextValue) {
		try {
			driver.findElementByAndroidUIAutomator("new UiScrollable(" + "new UiSelector().scrollable(true)).scrollIntoView(" + "new UiSelector().textContains(\"" + dynamicTextValue + "\"));");
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}

	public void scrollToElementByID(AndroidDriver<AndroidElement> driver, String dynamicID) {
		try {
			driver.findElementByAndroidUIAutomator("new UiScrollable(" + "new UiSelector().scrollable(true)).scrollIntoView(" + "new UiSelector().resourceId(\"" + dynamicID + "\"));");
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}

	public void scrollIDown(AndroidDriver<AndroidElement> driver, String locator) {
		Dimension size = driver.manage().window().getSize();
		int x = size.getWidth() / 2;
		int startY = (int) (size.getHeight() * 0.80);
		int endY = (int) (size.getHeight() * 0.30);
		TouchAction touch = new TouchAction(driver);

		for (int i = 0; i < 20; i++) {
			overRideTimeOut(driver, 2);
			List<AndroidElement> elementsOne = driver.findElements(By.xpath(locator));
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

	public void scrollUp(AndroidDriver<AndroidElement> driver, String locator) {
		Dimension size = driver.manage().window().getSize();
		int x = size.getWidth() / 2;
		int startY = (int) (size.getHeight() * 0.30);
		int endY = (int) (size.getHeight() * 0.80);
		TouchAction touch = new TouchAction(driver);

		for (int i = 0; i < 20; i++) {
			overRideTimeOut(driver, 2);
			List<AndroidElement> elementsOne = driver.findElements(By.xpath(locator));
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

	public void scrollIDown(AndroidDriver<AndroidElement> driver, String locator, String... dynamicValue) {
		Dimension size = driver.manage().window().getSize();
		int x = size.getWidth() / 2;
		int startY = (int) (size.getHeight() * 0.80);
		int endY = (int) (size.getHeight() * 0.30);
		TouchAction touch = new TouchAction(driver);
		locator = String.format(locator, (Object[]) dynamicValue);
		for (int i = 0; i < 20; i++) {
			locator = String.format(locator, (Object[]) dynamicValue);
			overRideTimeOut(driver, 2);
			List<AndroidElement> elementsOne = driver.findElements(By.xpath(locator));
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

	public void scrollUp(AndroidDriver<AndroidElement> driver, String locator, String... dynamicValue) {
		Dimension size = driver.manage().window().getSize();
		int x = size.getWidth() / 2;
		int startY = (int) (size.getHeight() * 0.30);
		int endY = (int) (size.getHeight() * 0.80);
		TouchAction touch = new TouchAction(driver);
		locator = String.format(locator, (Object[]) dynamicValue);
		for (int i = 0; i < 20; i++) {
			locator = String.format(locator, (Object[]) dynamicValue);
			overRideTimeOut(driver, 2);
			List<AndroidElement> elementsOne = driver.findElements(By.xpath(locator));
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

	public void clickToElement(AndroidDriver<AndroidElement> driver, String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		element.click();

	}

	public void clickToElement(AndroidDriver<AndroidElement> driver, String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		WebElement element = driver.findElement(By.xpath(locator));
		element.click();

	}

	public void clickToOneOfElement(AndroidDriver<AndroidElement> driver, int elementIndex, String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		List<AndroidElement> element = driver.findElements(By.xpath(locator));
		element.get(elementIndex).click();

	}

	public void clickToOneOfElement(AndroidDriver<AndroidElement> driver, int elementIndex, String locator) {
		List<AndroidElement> element = driver.findElements(By.xpath(locator));
		element.get(elementIndex).click();

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

	public void setValueToElement(AndroidDriver<AndroidElement> driver, String locator, String value, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		MobileElement element1 = driver.findElement(By.xpath(locator));
		element1.clear();
		driver.getKeyboard().sendKeys(value);
	}

	public String getTextInOneOFElement(AndroidDriver<AndroidElement> driver, int index, String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		List<AndroidElement> element = driver.findElements(By.xpath(locator));
		return element.get(index).getText();
	}

	public String getAttributeValue(AndroidDriver<AndroidElement> driver, String locator, String attribute) {
		WebElement element = driver.findElement(By.xpath(locator));
		return element.getAttribute(attribute);
	}

	public String getTextElement(AndroidDriver<AndroidElement> driver, String locator, String... dynamicValue) {

		locator = String.format(locator, (Object[]) dynamicValue);
		WebElement element = driver.findElement(By.xpath(locator));
		return element.getText();
	}

	public String getTextInFirstElement(AndroidDriver<AndroidElement> driver, int index, String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		List<AndroidElement> element = driver.findElements(By.xpath(locator));
		return element.get(index).getText();
	}

	public List<String> getTextInListElements(AndroidDriver<AndroidElement> driver, String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		List<AndroidElement> listElements = driver.findElements(By.xpath(locator));
		List<String> listTextView = new ArrayList<String>();
		for (AndroidElement element : listElements) {
			listTextView.add(element.getText());
		}
		return listTextView;
	}

	public List<String> getContentDescInListElements(AndroidDriver<AndroidElement> driver, String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		List<AndroidElement> listElements = driver.findElements(By.xpath(locator));
		List<String> listTextView = new ArrayList<String>();
		for (AndroidElement element : listElements) {
			listTextView.add(element.getAttribute("content-desc"));
		}
		return listTextView;
	}

	public List<String> getEnableInListElements(AndroidDriver<AndroidElement> driver, String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		List<AndroidElement> listElements = driver.findElements(By.xpath(locator));
		List<String> listTextView = new ArrayList<String>();
		for (AndroidElement element : listElements) {
			listTextView.add(element.getAttribute("enabled"));
		}
		return listTextView;
	}

	public List<String> getSelectedInListElements(AndroidDriver<AndroidElement> driver, String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		List<AndroidElement> listElements = driver.findElements(By.xpath(locator));
		List<String> listTextView = new ArrayList<String>();
		for (AndroidElement element : listElements) {
			listTextView.add(element.getAttribute("selected"));
		}
		return listTextView;
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

	public void overRideTimeOut(AndroidDriver<AndroidElement> driver, long time) {
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

	public void backToDefault(AndroidDriver<AndroidElement> driver) {
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

	public boolean waitForElementVisible(AndroidDriver<AndroidElement> driver, String locator) {

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
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		locator = String.format(locator, (Object[]) dynamicValue);
		List<AndroidElement> elements = driver.findElements(By.xpath(locator));
		if (elements.size() > 0 && elements.get(0).isDisplayed()) {
			return true;
		} else {
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

	public String removeUnicode(AndroidDriver<AndroidElement> driver, String locator) {

		String temp = Normalizer.normalize(locator, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(temp).replaceAll("");
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

	public boolean waitForElementVisible(AndroidDriver<AndroidElement> driver, String locator, String... dynamicValue) {
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

	public boolean waitForElementInvisible(AndroidDriver<AndroidElement> driver, String locator, String... dynamicValue) {
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

	/* CLICK METHOD */

//Click vào Cho phép lúc khởi tạo app, hoặc check chức chăng có permission
	public void clickToDynamicAcceptButton(AndroidDriver<AndroidElement> driver, String dynamicIDValue) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_ACCEPT_BUTTON_OR_BUTTON, dynamicIDValue);
		if (status == true) {
			clickToElement(driver, DynamicPageUIs.DYNAMIC_ACCEPT_BUTTON_OR_BUTTON, dynamicIDValue);
		}

	}

//Click vao 1 button sử dụng  tham số là text
	public void clickToDynamicButton(AndroidDriver<AndroidElement> driver, String dynamicTextValue) {
		boolean status = false;
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
		if (status == true) {
			clickToElement(driver, DynamicPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
		}

	}

//Click vào dropdown list tham số truyển vào là label của ô dropdown list đó
	public void clickToDynamicDropDown(AndroidDriver<AndroidElement> driver, String dymanicText) {
		boolean status = false;
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_DROPDOWN_BY_LABEL, dymanicText);
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_DROPDOWN_BY_LABEL, dymanicText);
		if (status == true) {
			clickToElement(driver, DynamicPageUIs.DYNAMIC_DROPDOWN_BY_LABEL, dymanicText);
		}

	}

//Click vào button, text có class là textview, tham số truyền vào là text
	public void clickToDynamicButtonLinkOrLinkText(AndroidDriver<AndroidElement> driver, String dynamicTextValue) {
		boolean status = false;
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
		if (status == true) {
			clickToElement(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);

		}
	}

//Click vào ô textbox lấy theo header, có 2 tham số truyền vào là text của label và vị trí index của ô input đó
	public void clickToDynamicInputBoxByHeader(AndroidDriver<AndroidElement> driver, String... dynamicTextValue) {
		boolean status = false;
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX_BY_HEADER, dynamicTextValue);
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX_BY_HEADER, dynamicTextValue);
		if (status == true) {
			clickToElement(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX_BY_HEADER, dynamicTextValue);
		}

	}

//Click vào ô dropdown lấy theo header, có 2 tham số truyền vào là text của label và vị trí index của ô input đó
	public void clickToDynamicDropdownByHeader(AndroidDriver<AndroidElement> driver, String... dynamicTextValue) {
		boolean status = false;
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_DROPDOWN_BY_HEADER, dynamicTextValue);
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_DROPDOWN_BY_HEADER, dynamicTextValue);
		if (status == true) {
			clickToElement(driver, DynamicPageUIs.DYNAMIC_DROPDOWN_BY_HEADER, dynamicTextValue);
		}

	}

// Click vào ô dropdown, và ô date time , tham số truyền vào là resource id
	public void clickToDynamicDropdownAndDateTimePicker(AndroidDriver<AndroidElement> driver, String dynamicID) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY, dynamicID);
		if (status == true) {
			clickToElement(driver, DynamicPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY, dynamicID);
		}

	}

// Click vào ngày trong date time picker , tham số truyền vào là text
	public void clickToDynamicDateInDateTimePicker(AndroidDriver<AndroidElement> driver, String dynamicText) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_DATE_IN_DATE_TIME_PICKER_AND_TEXT, dynamicText);
		if (status == true) {
			clickToElement(driver, DynamicPageUIs.DYNAMIC_DATE_IN_DATE_TIME_PICKER_AND_TEXT, dynamicText);
		}

	}

// Click vào menu tại bottom hoặc icon đóng k chứa text, tham số truyền vào là resource id
	public void clickToDynamicImageViewByID(AndroidDriver<AndroidElement> driver, String dynamicID) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BOTTOM_MENU_CLOSE_ICON, dynamicID);
		if (status == true) {
			clickToElement(driver, DynamicPageUIs.DYNAMIC_BOTTOM_MENU_CLOSE_ICON, dynamicID);
		}
	}

//Click vào close icon có tham số truyền vào là text cạnh nó
	public void clickToDynamicCloseIcon(AndroidDriver<AndroidElement> driver, String dynamicTextValue) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_CLOSE_ICON, dynamicTextValue);
		if (status == true) {
			clickToElement(driver, DynamicPageUIs.DYNAMIC_CLOSE_ICON, dynamicTextValue);
		}
	}

// Click nút back bằng label cạnh nó
	public void clickToDynamicBackIcon(AndroidDriver<AndroidElement> driver, String dynamicTextValue) {
		boolean status = false;
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_BACK_ICON, dynamicTextValue);
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BACK_ICON, dynamicTextValue);
		if (status == true) {
			clickToElement(driver, DynamicPageUIs.DYNAMIC_BACK_ICON, dynamicTextValue);
		}

	}

// click vào icon ứng dụng trên màn hình theo tên của icon đó
	public void clickToDynamicIcon(AndroidDriver<AndroidElement> driver, String dynamicTextValue) {
		boolean status = false;
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_ICON, dynamicTextValue);
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_ICON, dynamicTextValue);
		if (status == true) {
			clickToElement(driver, DynamicPageUIs.DYNAMIC_ICON, dynamicTextValue);
		}
	}

// Click vào ô input box 
	public void clickToDynamicInput(AndroidDriver<AndroidElement> driver, String dynamicTextValue) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, dynamicTextValue);
		if (status == true) {
			clickToElement(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, dynamicTextValue);
		}

	}

//Click vào giao dịch trong báo cáo giao dịch tham số truyền vào là index và resource-id
	public void clickToDynamicTransactionInReport(AndroidDriver<AndroidElement> driver, String... dynamicIndexAndID) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_TRANSACTION_INFO_IN_REPORT, dynamicIndexAndID);
		if (status == true) {
			clickToElement(driver, DynamicPageUIs.DYNAMIC_TRANSACTION_INFO_IN_REPORT, dynamicIndexAndID);
		}

	}

//Click vào 1 giao dịch trong trạng thái lệnh chuyển tiền, tham số truyền vào là index và resource id
	public void clickToDynamicTransactionInTransactionOrderStatus(AndroidDriver<AndroidElement> driver, String... dynamicIndex1ID2) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_TRANSACTION_INFO_IN_TRANSFER_ORDER_STATUS, dynamicIndex1ID2);
		if (status == true) {
			clickToElement(driver, DynamicPageUIs.DYNAMIC_TRANSACTION_INFO_IN_TRANSFER_ORDER_STATUS, dynamicIndex1ID2);
		}
	}

	public void clickDynamicPointStartAndEnd(AndroidDriver<AndroidElement> driver, String... dynamicTextValue) {
		boolean status = false;
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_START_AND_END_TEXT, dynamicTextValue);
		if (status == true) {
			clickToElement(driver, TrainTicketPageUIs.DYNAMIC_START_AND_END_TEXT, dynamicTextValue);
		}
	}

	// Click button cancel
	public void clickDynamicCancelIcon(AndroidDriver<AndroidElement> driver, String dynamicTextValue) {
		boolean status = false;
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_CANCEL_ICON, dynamicTextValue);
		if (status == true) {
			clickToElement(driver, TrainTicketPageUIs.DYNAMIC_CANCEL_ICON, dynamicTextValue);

		}
	}

	// So sánh giá trị trong list combobox, không cần sắp xếp theo thứ tự
	public boolean checkListContain(List<String> actualList, List<String> expectList) {
		return expectList.containsAll(actualList);
	}

	public void clickToDynamicBottomMenu(AndroidDriver<AndroidElement> driver, String dynamicID) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BOTTOM_MENU, dynamicID);
		if (status == true) {
			clickToElement(driver, DynamicPageUIs.DYNAMIC_BOTTOM_MENU, dynamicID);

		}
	}

	public void clickToDynamicImageButtonByID(AndroidDriver<AndroidElement> driver, String dynamicID) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_IMAGE_BUTTON, dynamicID);
		if (status == true) {
			clickToElement(driver, DynamicPageUIs.DYNAMIC_IMAGE_BUTTON, dynamicID);
		}
	}

	public void clickToDynamicLink(AndroidDriver<AndroidElement> driver, String... dynamicTextValue) {
		boolean status = false;
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_HISTORY_ICON, dynamicTextValue);
		if (status == true) {
			clickToElement(driver, TrainTicketPageUIs.DYNAMIC_HISTORY_ICON, dynamicTextValue);
		}
	}

	public void clickToDynamicSuggestedMoney(AndroidDriver<AndroidElement> driver, int index, String dynamicID) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY, dynamicID);
		if (status == true) {
			clickToOneOfElement(driver, index, DynamicPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY, dynamicID);
		}
	}

	// Click icon change
	public void clickToDynamicIconChange(AndroidDriver<AndroidElement> driver, String dynamicText) {
		boolean status = false;
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_CHANGE_ICON, dynamicText);
		if (status == true) {
			clickToElement(driver, TrainTicketPageUIs.DYNAMIC_CHANGE_ICON, dynamicText);
		}
	}

	// Click select date
	public void clickToDynamicSelectDate(AndroidDriver<AndroidElement> driver, String dynamicID) {
		boolean status = false;
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_DATE, dynamicID);
		if (status == true) {
			clickToElement(driver, TrainTicketPageUIs.DYNAMIC_DATE, dynamicID);
		}
	}

	// Click chọn ngày trong lịch calendar
	public void clickDynamicDateStartAndEnd(AndroidDriver<AndroidElement> driver, String... dynamicTextValue) {
		boolean status = false;
		scrollIDown(driver, TrainTicketPageUIs.DYNAMIC_DATE_SELECTED, dynamicTextValue);
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_DATE_SELECTED, dynamicTextValue);
		if (status == true) {
			clickToElement(driver, TrainTicketPageUIs.DYNAMIC_DATE_SELECTED, dynamicTextValue);
		}
	}

	// Click radio button
	public void clickDynamicRadioSelectType(AndroidDriver<AndroidElement> driver, String dynamicTextValue) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_SUCCESS_ICON, dynamicTextValue);
		if (status == true) {
			clickToElement(driver, DynamicPageUIs.DYNAMIC_SUCCESS_ICON, dynamicTextValue);
		}
	}

	public void clickToTextViewByLinearLayoutID(AndroidDriver<AndroidElement> driver, String... dynamicID) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_TEXTVIEW_BY_LINEARLAYOUT_ID, dynamicID);
		if (status == true) {
			clickToElement(driver, DynamicPageUIs.DYNAMIC_TEXTVIEW_BY_LINEARLAYOUT_ID, dynamicID);
		}

	}

	/* INPUT BOX METHOD */

	public void inputOTPInvalidBy_N_Times(AndroidDriver<AndroidElement> driver, int time) {
		for (int i = 0; i < time; i++) {
			inputToDynamicOtpOrPIN(driver, "213456", "Tiếp tục");
			clickToDynamicButton(driver, "Tiếp tục");
			if (i < time - 1) {
				clickToDynamicButton(driver, "Đóng");
			}
		}
	}

	public void inputPasswordInvalidBy_N_Times(AndroidDriver<AndroidElement> driver, int time) {
		for (int i = 0; i < time; i++) {
			inputToDynamicPopupPasswordInput(driver, "12345678", "Tiếp tục");
			clickToDynamicButton(driver, "Tiếp tục");
			if (i < time - 1) {
				clickToDynamicButton(driver, "Đóng");
			}
		}
	}

	public void inputIntoEditTextByID(AndroidDriver<AndroidElement> driver, String inputValue, String... dynamicID) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_TEXT_BOX_WITH_ID, dynamicID);
		if (status == true) {
			clearText(driver, DynamicPageUIs.DYNAMIC_TEXT_BOX_WITH_ID, dynamicID);
			sendKeyToElement(driver, DynamicPageUIs.DYNAMIC_TEXT_BOX_WITH_ID, inputValue, dynamicID);
		}

	}

//Dien text vao input box
// input vào ô input với tham số truyền vào là inputbox
	public void inputToDynamicInputBox(AndroidDriver<AndroidElement> driver, String inputValue, String dynamicTextValue) {
		boolean status = false;
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, dynamicTextValue);
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, dynamicTextValue);
		if (status == true) {
			clearText(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, dynamicTextValue);
			sendKeyToElement(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, inputValue, dynamicTextValue);
		}

	}

//input vào 1 ô text box bằng label của nó, tham số truyền vào là text của label và vị trí của ô input
	public void inputToDynamicInputBoxByHeader(AndroidDriver<AndroidElement> driver, String inputValue, String... dynamicTextValueAndID) {
		boolean status = false;
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX_BY_HEADER, dynamicTextValueAndID);
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX_BY_HEADER, dynamicTextValueAndID);
		if (status == true) {
			clearText(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX_BY_HEADER, dynamicTextValueAndID);
			sendKeyToElement(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX_BY_HEADER, inputValue, dynamicTextValueAndID);
		}

	}

	public void pressKeyCodeIntoDynamicInputBoxByHeader(AndroidDriver<AndroidElement> driver, List<Keys> dynamicKey, String... dynamicTextValueAndID) {
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

	public void pressKeyCodeIntoAmountInput(AndroidDriver<AndroidElement> driver, List<Keys> dynamicKey) {
		boolean status = false;
		status = waitForElementVisible(driver, TransferMoneyInVCBPageUIs.FREQUENCY_NUMBER_INPUT);
		if (status == true) {
			clearText(driver, TransferMoneyInVCBPageUIs.FREQUENCY_NUMBER_INPUT);
			for (Keys key : dynamicKey) {
				driver.getKeyboard().sendKeys(key);
			}
		}
	}

	public void inputToDynamicLogInTextBox(AndroidDriver<AndroidElement> driver, String inputValue, String dynamicTextValue) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_INPUT_IN_LOGIN, dynamicTextValue);
		;
		if (status == true) {
			clearText(driver, DynamicPageUIs.DYNAMIC_INPUT_IN_LOGIN, dynamicTextValue);
			sendKeyToElement(driver, DynamicPageUIs.DYNAMIC_INPUT_IN_LOGIN, inputValue, dynamicTextValue);
		}
	}

//Input vào ô nhập otp , tham số truyền vào là text của button tiếp tục
	public void inputToDynamicOtpOrPIN(AndroidDriver<AndroidElement> driver, String inputValue, String dynamicTextValue) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_OTP_INPUT, dynamicTextValue);
		if (status == true) {
			clearText(driver, DynamicPageUIs.DYNAMIC_OTP_INPUT, dynamicTextValue);
			setValueToElement(driver, DynamicPageUIs.DYNAMIC_OTP_INPUT, inputValue, dynamicTextValue);
		}
	}

//input vào pop-up nhập mật khẩu xác thực, tham số truyền vào là text của button tiếp tục

	// Nhập địa điểm tìm kiếm
	public void inputToDynamicTextPoint(AndroidDriver<AndroidElement> driver, String inputValue, String dynamicIndexValue) {
		boolean status = false;
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_INPUT_POINT, dynamicIndexValue);
		if (status == true) {
			clearText(driver, TrainTicketPageUIs.DYNAMIC_INPUT_POINT, dynamicIndexValue);
			sendKeyToElement(driver, TrainTicketPageUIs.DYNAMIC_INPUT_POINT, inputValue, dynamicIndexValue);
		}
	}

	public void inputToDynamicPopupPasswordInput(AndroidDriver<AndroidElement> driver, String inputValue, String dynamicTextValue) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_PASSWORD_INPUT, dynamicTextValue);
		if (status == true) {
			clearText(driver, DynamicPageUIs.DYNAMIC_PASSWORD_INPUT, dynamicTextValue);
			sendKeyToElement(driver, DynamicPageUIs.DYNAMIC_PASSWORD_INPUT, inputValue, dynamicTextValue);
		}
	}

	public void inputToPasswordConfirm(AndroidDriver<AndroidElement> driver, String inputValue) {
		boolean status = false;
		status = waitForElementVisible(driver, TransferMoneyOutSideVCBPageUIs.PASSWORD_CONFIRM_INPUT);
		if (status == true) {
			clearText(driver, TransferMoneyOutSideVCBPageUIs.PASSWORD_CONFIRM_INPUT);
			setValueToElement(driver, TransferMoneyOutSideVCBPageUIs.PASSWORD_CONFIRM_INPUT, inputValue);
		}
	}

	public void inputToDynamicInputBoxSearch(AndroidDriver<AndroidElement> driver, String inputValue, String dynamicTextValue) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_LABEL_LIST_ACCEPT, dynamicTextValue);
		if (status == true) {
			clearText(driver, DynamicPageUIs.DYNAMIC_LABEL_LIST_ACCEPT, dynamicTextValue);
			sendKeyToElement(driver, DynamicPageUIs.DYNAMIC_LABEL_LIST_ACCEPT, inputValue, dynamicTextValue);
		}
	}

	public void inputToDynamicInputBoxSearchBank(AndroidDriver<AndroidElement> driver, String inputValue, String dynamicTextValue) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_LABEL_SEARCH_BANK, dynamicTextValue);
		if (status == true) {
			clearText(driver, DynamicPageUIs.DYNAMIC_LABEL_SEARCH_BANK, dynamicTextValue);
			sendKeyToElement(driver, DynamicPageUIs.DYNAMIC_LABEL_SEARCH_BANK, inputValue, dynamicTextValue);
		}
	}

	/* BOLEAN METHOD */
// check button có hiển thị hay không, tham số truyền vào là text của button
	public boolean isDynamicButtonDisplayed(AndroidDriver<AndroidElement> driver, String dynamicTextValue) {
		boolean isDisplayed = false;
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
		boolean status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, DynamicPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
		}
		return isDisplayed;

	}

//Kiểm tra text có hiển thị hay không, tham số truyền vào là text 
	public boolean isDynamicMessageAndLabelTextDisplayed(AndroidDriver<AndroidElement> driver, String dynamicTextValue) {
		boolean isDisplayed = false;
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
		boolean status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
		}
		return isDisplayed;

	}

	public boolean isDynamicImageViewByTextViewdDisplayed(AndroidDriver<AndroidElement> driver, String... dynamicTextValue) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_CLOSE_ICON, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, DynamicPageUIs.DYNAMIC_CLOSE_ICON, dynamicTextValue);
		}
		return isDisplayed;
	}

	public boolean isKeyBoardDisplayed(AndroidDriver<AndroidElement> driver) {
		return driver.isKeyboardShown();
	}

//Kiểm tra text không hiển thị trên màn hình, tham số truyền vào là text
	public boolean isDynamicMessageAndLabelTextUndisplayed(AndroidDriver<AndroidElement> driver, String dynamicTextValue) {
		boolean isDisplayed = true;
		boolean status = waitForElementInvisible(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlUnDisplayed(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
		}
		return isDisplayed;
	}

//Kiểm  tra text trong ô input có hiển thị hay không, tham số truyền vào là text
	public boolean isDynamicTextInInputBoxDisPlayed(AndroidDriver<AndroidElement> driver, String... dynamicTextValue) {

		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, dynamicTextValue);
		}
		return isDisplayed;
	}

//Kiểm tra gợi ý số tiền không hiển thị trên màn hình,tham số truyền vào là resource-id 
	public boolean isDynamicSuggestedMoneyUndisplayed(AndroidDriver<AndroidElement> driver, String... dynamicTextValue) {
		boolean isDisplayed = false;
		boolean status = waitForElementInvisible(driver, DynamicPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlUnDisplayed(driver, DynamicPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY, dynamicTextValue);
		}
		return isDisplayed;
	}

	public boolean isDynamicBackIconDisplayed(AndroidDriver<AndroidElement> driver, String dynamicTextValue) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BACK_ICON, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, DynamicPageUIs.DYNAMIC_BACK_ICON, dynamicTextValue);
		}
		return isDisplayed;
	}

	public boolean isDynamicHistoryIconDisplayed(AndroidDriver<AndroidElement> driver, String dynamicTextValue) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_HISTORY_ICON, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, TrainTicketPageUIs.DYNAMIC_HISTORY_ICON, dynamicTextValue);
		}
		return isDisplayed;
	}

	// Check hiển thị button chuyển đổi
	public boolean isDynamicChangeIconDisplayed(AndroidDriver<AndroidElement> driver, String dynamicTextValue) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_CHANGE_ICON, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, TrainTicketPageUIs.DYNAMIC_CHANGE_ICON, dynamicTextValue);
		}
		return isDisplayed;
	}

	// Check hiển thị icon combobox
	public boolean isDynamicComboboxDisplayed(AndroidDriver<AndroidElement> driver, String dynamicTextValue) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_COMBOBOX, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, TrainTicketPageUIs.DYNAMIC_COMBOBOX, dynamicTextValue);
		}
		return isDisplayed;
	}

	public boolean isDynamicVerifyTextOnButton(AndroidDriver<AndroidElement> driver, String dynamicTextValue) {
		boolean isDisplayed = false;
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
		boolean status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, DynamicPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
		}
		return isDisplayed;

	}

	public boolean isDynamicTextDetailByID(AndroidDriver<AndroidElement> driver, String dynamicID) {
		boolean isDisplayed = false;
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_TEXT_BY_ID, dynamicID);
		boolean status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_TEXT_BY_ID, dynamicID);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, DynamicPageUIs.DYNAMIC_TEXT_BY_ID, dynamicID);
		}
		return isDisplayed;

	}

	// Kiểm tra gợi ý số tiền có hiển thị, tham số truyền vào là resource-id
	public boolean isDynamicSuggestedMoneyDisplayed(AndroidDriver<AndroidElement> driver, String... dynamicTextValue) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, DynamicPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY, dynamicTextValue);
		}
		return isDisplayed;
	}

	// Kiểm tra text trong nội dung link thông báo
	public boolean isDynamicTextInfoDisplayed(AndroidDriver<AndroidElement> driver, String... dynamicTextValue) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_DATE_IN_DATE_TIME_PICKER_AND_TEXT, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, DynamicPageUIs.DYNAMIC_DATE_IN_DATE_TIME_PICKER_AND_TEXT, dynamicTextValue);
		}
		return isDisplayed;
	}

	// Kiểm tra hiển thị image, check đã chọn có định dạng ImageView
	public boolean isDynamicImageSelect(AndroidDriver<AndroidElement> driver, String dynamicTextValue) {
		boolean isDisplayed = false;
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_CLOSE_ICON, dynamicTextValue);
		boolean status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_CLOSE_ICON, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, DynamicPageUIs.DYNAMIC_CLOSE_ICON, dynamicTextValue);
		}
		return isDisplayed;
	}

	// Kiểm tra hiển thị image, check chuyển khoản thành công
	public boolean isDynamicImageSuccess(AndroidDriver<AndroidElement> driver, String dynamicTextValue) {
		boolean isDisplayed = false;
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_CLOSE_ICON, dynamicTextValue);
		boolean status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_SUCCESS_ICON, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, DynamicPageUIs.DYNAMIC_SUCCESS_ICON, dynamicTextValue);
		}
		return isDisplayed;
	}

	// Kiểm tra hiển thị icon home man hinh chuyen tien thanh cong
	public boolean isDynamicImageHomeDisplay(AndroidDriver<AndroidElement> driver, String dynamicID) {
		boolean isDisplayed = false;
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_BOTTOM_MENU, dynamicID);
		boolean status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BOTTOM_MENU, dynamicID);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, DynamicPageUIs.DYNAMIC_BOTTOM_MENU, dynamicID);
		}
		return isDisplayed;
	}

	// Kiểm tra hiển thị time màn hình chuyển khoản thành công
	public boolean isDynamicTimeAndMoneyDisplay(AndroidDriver<AndroidElement> driver, String... dynamicTextValue) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_TRANSFER_TIME, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, DynamicPageUIs.DYNAMIC_TRANSFER_TIME, dynamicTextValue);
		}
		return isDisplayed;

	}

	public boolean isDynamicTextNumberCustomerDisplayed(AndroidDriver<AndroidElement> driver, String dynamicTextValue) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_LABEL_AMOUNT, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, DynamicPageUIs.DYNAMIC_LABEL_AMOUNT, dynamicTextValue);
		}
		return isDisplayed;
	}

	// Không hiển thị text trường số lượng hành khách
	public boolean isDynamicTextNumberCustomerUnDisplayed(AndroidDriver<AndroidElement> driver, String dynamicTextValue) {
		boolean isDisplayed = false;
		boolean status = waitForElementInvisible(driver, DynamicPageUIs.DYNAMIC_LABEL_AMOUNT, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlUnDisplayed(driver, DynamicPageUIs.DYNAMIC_LABEL_AMOUNT, dynamicTextValue);
		}
		return isDisplayed;
	}

	// hiển thị icon trường số lượng hành khách
	public boolean isDynamicIconChangeNumber(AndroidDriver<AndroidElement> driver, String... dynamicTextValue) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_ICON_CHANGE_NUMBER, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, TrainTicketPageUIs.DYNAMIC_ICON_CHANGE_NUMBER, dynamicTextValue);
		}
		return isDisplayed;

	}

	// hiển thị text trường số lượng hành khách
	public boolean isDynamicTextChangeNumber(AndroidDriver<AndroidElement> driver, String... dynamicTextValue) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_TEXT_CHANGE_NUMBER, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, TrainTicketPageUIs.DYNAMIC_TEXT_CHANGE_NUMBER, dynamicTextValue);
		}
		return isDisplayed;

	}

	public boolean isDynamicImageButtonDisplayed(AndroidDriver<AndroidElement> driver, String dynamicTextValue) {
		boolean isDisplayed = false;
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_IMAGE_BUTTON, dynamicTextValue);
		boolean status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_IMAGE_BUTTON, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, DynamicPageUIs.DYNAMIC_IMAGE_BUTTON, dynamicTextValue);
		}
		return isDisplayed;
	}

	/* GET TEXT METHOD */

// lay text trong ô dropdown bằng index và header của nó
	public String getDynamicTextInDropDownByHeader(AndroidDriver<AndroidElement> driver, String... dynamicTextValueAndID) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_DROPDOWN_BY_HEADER, dynamicTextValueAndID);
		if (status == true) {
			text = getTextElement(driver, DynamicPageUIs.DYNAMIC_DROPDOWN_BY_HEADER, dynamicTextValueAndID);

		}
		return text;

	}

// lay loại tiền tệ trong ô text box số tiền bằng text
	public String getDynamicCurrencyInMoneyTextbox(AndroidDriver<AndroidElement> driver, String... dynamicText) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_CURRENTCY_IN_MONEY_TEXTBOX, dynamicText);
		if (status == true) {
			text = getTextElement(driver, DynamicPageUIs.DYNAMIC_CURRENTCY_IN_MONEY_TEXTBOX, dynamicText);

		}
		return text;

	}

//lấy text trong ô input, tham số truyền vào là text
	public String getDynamicTextInInputBox(AndroidDriver<AndroidElement> driver, String dynamicTextValue) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, dynamicTextValue);
		if (status == true) {
			text = getTextElement(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, dynamicTextValue);

		}
		return text;
	}

//Lấy text trong ô input bằng bằng label của nó, tham số truyền vào là  text của label và vị trí index của ô input
	public String getDynamicTextInInputBoxByHeader(AndroidDriver<AndroidElement> driver, String... dynamicTextAndIndex) {
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
	public String getTextDynamicDefaultSourceAccount(AndroidDriver<AndroidElement> driver, String... dynamicTextValueAndIndex) {
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
	public String getDynamicTransferTimeAndMoney(AndroidDriver<AndroidElement> driver, String... dynamicTextValue) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_TRANSFER_TIME, dynamicTextValue);
		if (status == true) {
			text = getTextElement(driver, DynamicPageUIs.DYNAMIC_TRANSFER_TIME, dynamicTextValue);

		}
		return text;

	}

	public String getTextDynamicInSelectBox(AndroidDriver<AndroidElement> driver, String dynamicTextValue) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
		if (status == true) {
			text = getTextElement(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);

		}
		return text;

	}

	public String getTextInDynamicPopup(AndroidDriver<AndroidElement> driver, String dynamicResourceID) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_TEXT_IN_POPUP, dynamicResourceID);
		if (status == true) {
			text = getTextElement(driver, DynamicPageUIs.DYNAMIC_TEXT_IN_POPUP, dynamicResourceID);

		}
		return text;
	}

	public String getTextInDynamicNote(AndroidDriver<AndroidElement> driver, String dynamicIndex) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_TEXT_INDEX, dynamicIndex);
		if (status == true) {
			text = getTextElement(driver, TrainTicketPageUIs.DYNAMIC_TEXT_INDEX, dynamicIndex);

		}
		return text;
	}

//Get thông tin được tạo trong chi tiết giao dich , tham số truyền vào là text phía bên tay trái
	public String getDynamicTextInTransactionDetail(AndroidDriver<AndroidElement> driver, String dynamicTextValue) {
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
	public String getDynamicTextDetailByID(AndroidDriver<AndroidElement> driver, String dynamicID) {
		boolean status = false;
		String text = null;
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_TEXT_BY_ID, dynamicID);
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_TEXT_BY_ID, dynamicID);
		if (status == true) {
			text = getTextElement(driver, DynamicPageUIs.DYNAMIC_TEXT_BY_ID, dynamicID);

		}
		return text;
	}

//lấy text ở dòng thứ 2 của phí chuyển tromg xác thực giao dich
	public String getDynamicTextInTextViewLine2(AndroidDriver<AndroidElement> driver, String dynamicTextValue) {
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
	public String getTextInDynamicTransactionInReport(AndroidDriver<AndroidElement> driver, String... dynamicIndex1Index2) {
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
	public String getTextInDynamicTransactionInTransferOrderStatus(AndroidDriver<AndroidElement> driver, String... dynamicIndex1ResouceID) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_TRANSACTION_INFO_IN_TRANSFER_ORDER_STATUS, dynamicIndex1ResouceID);
		if (status == true) {
			text = getTextElement(driver, DynamicPageUIs.DYNAMIC_TRANSACTION_INFO_IN_TRANSFER_ORDER_STATUS, dynamicIndex1ResouceID);

		}
		return text;
	}

	public String getDynamicAmountLabel(AndroidDriver<AndroidElement> driver, String dynamicTextValue) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_LABEL_AMOUNT, dynamicTextValue);
		if (status == true) {
			text = getTextElement(driver, DynamicPageUIs.DYNAMIC_LABEL_AMOUNT, dynamicTextValue);
		}
		return text;

	}

	public String getDynamicAmountLabelList(AndroidDriver<AndroidElement> driver, String... dynamicTextValue) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_INPUT_LABEL_BY_HEADER, dynamicTextValue);
		if (status == true) {
			text = getTextElement(driver, DynamicPageUIs.DYNAMIC_INPUT_LABEL_BY_HEADER, dynamicTextValue);
		}
		return text;
	}

	public String getDynamicAmountCostLabel(AndroidDriver<AndroidElement> driver, String dynamicTextValue) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_LABEL_COST, dynamicTextValue);
		if (status == true) {
			text = getTextElement(driver, DynamicPageUIs.DYNAMIC_LABEL_COST, dynamicTextValue);
		}
		return text;

	}

//Lấy text ở dropdown hoặc datetime picker bằng id
	public String getTextInDynamicDropdownOrDateTimePicker(AndroidDriver<AndroidElement> driver, String dynamicID) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY, dynamicID);
		if (status == true) {
			text = getTextElement(driver, DynamicPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY, dynamicID);
		}
		return text;

	}

	// Lấy toàn bộ số tiền được suggest ở ô số tiền và lưu vào array list

	public String getDynamicTextInPopUp(AndroidDriver<AndroidElement> driver, String... dynamicTextValue) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
		if (status == true) {
			text = getTextElement(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
		}
		return text;
	}

	// Lấy text tìm hiếm điểm khởi hành và điểm đến
	public String getDynamicPointStartAndEnd(AndroidDriver<AndroidElement> driver, String... dynamicTextValue) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_START_AND_END_TEXT, dynamicTextValue);
		if (status == true) {
			text = getTextElement(driver, TrainTicketPageUIs.DYNAMIC_START_AND_END_TEXT, dynamicTextValue);
		}
		return text;

	}

	// Lấy giá trị tìm kiếm trong danh sách
	public String getDynamicInputPoint(AndroidDriver<AndroidElement> driver, String dynamicIndexValue) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_INPUT_POINT, dynamicIndexValue);
		if (status == true) {
			text = getTextElement(driver, TrainTicketPageUIs.DYNAMIC_INPUT_POINT, dynamicIndexValue);
		}
		return text;

	}

	public String getDynamicTextPointStart(AndroidDriver<AndroidElement> driver, String dynamicID) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_VIEW_TEXT_START, dynamicID);
		if (status == true) {
			text = getTextElement(driver, TrainTicketPageUIs.DYNAMIC_VIEW_TEXT_START, dynamicID);
		}
		return text;
	}

	public List<String> getListOfSuggestedMoneyOrListText(AndroidDriver<AndroidElement> driver, String dynamicID) {
		boolean status = false;
		List<String> text = null;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY, dynamicID);
		if (status == true) {
			text = getTextInListElements(driver, DynamicPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY, dynamicID);
		}
		return text;

	}

// Kiểm tra keyboard có hiển thị

	public String getMoneyByAccount(AndroidDriver<AndroidElement> driver, String dynamicTextValue) {
		boolean status = false;
		String text = null;
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_LABEL_MONEY_BY_ACCOUNT, dynamicTextValue);
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_LABEL_MONEY_BY_ACCOUNT, dynamicTextValue);
		if (status == true) {
			text = getTextElement(driver, DynamicPageUIs.DYNAMIC_LABEL_MONEY_BY_ACCOUNT, dynamicTextValue);
		}
		return text;

	}

	// Lấy text phí giao dịch
	public String getTextInDynamicFee(AndroidDriver<AndroidElement> driver, String... dynamicTextValue) {
		boolean status = false;
		String text = null;
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_LABEL_MONEY_BY_ACCOUNT, dynamicTextValue);
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_DROPDOWN_BY_HEADER, dynamicTextValue);
		if (status == true) {
			text = getTextElement(driver, DynamicPageUIs.DYNAMIC_DROPDOWN_BY_HEADER, dynamicTextValue);
		}
		return text;
	}

	// Lấy text trên ô điền OTP
	public String getTextInDynamicOtp(AndroidDriver<AndroidElement> driver, String dynamicTextValue) {
		boolean status = false;
		String text = null;
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_LABEL_MONEY_BY_ACCOUNT, dynamicTextValue);
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_OTP_INPUT, dynamicTextValue);
		if (status == true) {
			text = getTextElement(driver, DynamicPageUIs.DYNAMIC_OTP_INPUT, dynamicTextValue);
		}
		return text;

	}

	// Lấy text trên ô điền Mat khau xac thuc
	public String getTextInDynamicPasswordInput(AndroidDriver<AndroidElement> driver, String dynamicID) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_TEXT_BOX_WITH_ID, dynamicID);
		if (status == true) {
			text = getTextElement(driver, DynamicPageUIs.DYNAMIC_TEXT_BOX_WITH_ID, dynamicID);
		}
		return text;

	}

	// Lấy text ngày đặt vé
	public String getTextInDynamicDateTicket(AndroidDriver<AndroidElement> driver, String... dynamicTextValue) {
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
	public String getDynamicTextInDropDownByLable(AndroidDriver<AndroidElement> driver, String dynamicTextValue) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_DROPDOWN_BY_LABEL, dynamicTextValue);
		if (status == true) {
			text = getTextElement(driver, DynamicPageUIs.DYNAMIC_DROPDOWN_BY_LABEL, dynamicTextValue);
		}
		return text;

	}

	// Lấy text trường số lượng hành khách

	public String getTextInEditTextFieldByID(AndroidDriver<AndroidElement> driver, String... dynamicID) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_TEXT_BOX_WITH_ID, dynamicID);
		if (status == true) {
			text = getTextElement(driver, DynamicPageUIs.DYNAMIC_TEXT_BOX_WITH_ID, dynamicID);
		}
		return text;

	}

	public String getTextTextViewByLinearLayoutID(AndroidDriver<AndroidElement> driver, String... dynamicID) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_TEXTVIEW_BY_LINEARLAYOUT_ID, dynamicID);
		if (status == true) {
			text = getTextElement(driver, DynamicPageUIs.DYNAMIC_TEXTVIEW_BY_LINEARLAYOUT_ID, dynamicID);
		}
		return text;

	}

	public List<String> getListTextViewByLinearLayoutID(AndroidDriver<AndroidElement> driver, String... dynamicID) {
		boolean status = false;
		List<String> text = null;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_TEXTVIEW_BY_LINEARLAYOUT_ID, dynamicID);
		if (status == true) {
			text = getTextInListElements(driver, DynamicPageUIs.DYNAMIC_TEXTVIEW_BY_LINEARLAYOUT_ID, dynamicID);
		}
		return text;

	}

	public List<String> getListTextViewByRelativeLayoutID(AndroidDriver<AndroidElement> driver, String... dynamicID) {

		boolean status = false;
		List<String> text = null;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_TEXTVIEW_BY_RELATIVELAYOUT_ID, dynamicID);
		if (status == true) {
			text = getTextInListElements(driver, DynamicPageUIs.DYNAMIC_TEXTVIEW_BY_RELATIVELAYOUT_ID, dynamicID);
		}
		return text;

	}

	public List<String> getListImageViewByLinearLayoutID(AndroidDriver<AndroidElement> driver, String... dynamicID) {
		boolean status = false;
		List<String> text = null;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_IMAGEVIEW_BY_LINEARLAYOUT_ID, dynamicID);
		if (status == true) {
			text = getTextInListElements(driver, DynamicPageUIs.DYNAMIC_IMAGEVIEW_BY_LINEARLAYOUT_ID, dynamicID);
		}
		return text;
	}

	// Hiển thị time ngay book
	public String getDynamicDateTime(AndroidDriver<AndroidElement> driver, String dynamicID) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_DATE, dynamicID);
		if (status == true) {
			text = getTextElement(driver, TrainTicketPageUIs.DYNAMIC_DATE, dynamicID);
		}
		return text;

	}

	public String getDynamicTitleSelectDate(AndroidDriver<AndroidElement> driver, String dynamicText) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_TITLE_SELECT_DATE, dynamicText);
		if (status == true) {
			text = getTextElement(driver, TrainTicketPageUIs.DYNAMIC_TITLE_SELECT_DATE, dynamicText);
		}
		return text;

	}

//Lay thu trong tuan
	public String getDynamicTitleWeek(AndroidDriver<AndroidElement> driver, String... dynamicText) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_TITLE_SELECT_WEEK, dynamicText);
		if (status == true) {
			text = getTextElement(driver, TrainTicketPageUIs.DYNAMIC_TITLE_SELECT_WEEK, dynamicText);
		}
		return text;
	}

	// Lấy message thông báo
	public String getDynamicMessageInvalid(AndroidDriver<AndroidElement> driver, String... dynamicText) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_LABEL_AMOUNT, dynamicText);
		if (status == true) {
			text = getTextElement(driver, DynamicPageUIs.DYNAMIC_LABEL_AMOUNT, dynamicText);
		}
		return text;

	}

	public String getDynamicTextOld(AndroidDriver<AndroidElement> driver, String... dynamicText) {

		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_NUMBER_CUSTOMER, dynamicText);
		if (status == true) {
			text = getTextElement(driver, TrainTicketPageUIs.DYNAMIC_NUMBER_CUSTOMER, dynamicText);
		}
		return text;
	}

}
