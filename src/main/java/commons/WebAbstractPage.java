package commons;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import vietcombankUI.DynamicWebPageUIs;

public class WebAbstractPage {

// Web browser
	public void openURL(WebDriver driver, String url) {
		driver.get(url);
		if (driver.toString().toLowerCase().contains("internet explorer")) {
			sleep(driver, 5000);
		}
	}

	public String getPageTitle(WebDriver driver) {
		return driver.getTitle();
	}

	public String getCurrentURL(WebDriver driver) {
		return driver.getCurrentUrl();
	}

	public String getCurrentPageSource(WebDriver driver) {
		return driver.getPageSource();
	}

	public void backToPreviousPage(WebDriver driver) {
		driver.navigate().back();
	}

	public void forwardToPreviousPage(WebDriver driver) {
		driver.navigate().forward();
		;
	}

	public void refeshPage(WebDriver driver) {
		driver.navigate().refresh();
		;
	}

	public void acceptAlert(WebDriver driver) {
		sleep(driver, 1000);
		Alert alert = driver.switchTo().alert();
		alert.accept();
		if (driver.toString().toLowerCase().contains("internet explorer")) {
			sleep(driver, 5000);
		}
	}

	public void cancelAlert(WebDriver driver) {
		sleep(driver, 1000);
		Alert alert = driver.switchTo().alert();
		alert.dismiss();
		if (driver.toString().toLowerCase().contains("internet explorer")) {
			sleep(driver, 5000);
		}
	}

	public String getTextInAlert(WebDriver driver) {
		sleep(driver, 1000);
		Alert alert = driver.switchTo().alert();
		return alert.getText();

	}

	public void sendKeyToAlert(WebDriver driver, String value) {
		sleep(driver, 1000);
		Alert alert = driver.switchTo().alert();
		alert.sendKeys(value);
		if (driver.toString().toLowerCase().contains("internet explorer")) {
			sleep(driver, 5000);
		}
	}
	
	public void waitForAlerVisibleAndClickAccept(WebDriver driver) {
		
	     WebDriverWait wait = new WebDriverWait(driver, 10);
         wait.until(ExpectedConditions.alertIsPresent());

         Alert alert = driver.switchTo().alert();
         alert.accept();

	}

//WebElement

	public boolean isMultipleSelected(WebDriver driver, String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		WebElement element = driver.findElement(By.xpath(locator));
		Select select = new Select(element);
		if (select.isMultiple()) {
			return true;
		} else {
			return false;
		}
	}

	public void clickToElement(WebDriver driver, String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		if (driver.toString().toLowerCase().contains("internet explorer")) {
			clickToElementByJava(driver, locator);
			sleep(driver, 5000);
		}

		else {
			element.click();
		}

	}

	public void clickToElementByJava(WebDriver driver, String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].click();", element);

	}

	public void checkToElement(WebDriver driver, String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		if (!element.isSelected()) {
			element.click();
		}
	}

	public void uncheckToElement(WebDriver driver, String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		if (element.isSelected()) {
			element.click();
		}
	}

	public void sendKeyToElement(WebDriver driver, String locator, String value) {
		WebElement element = driver.findElement(By.xpath(locator));
		element.clear();
		element.sendKeys(value);
	}

	public void sendKeyToElement(WebDriver driver, String locator, String value, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		WebElement element = driver.findElement(By.xpath(locator));
		element.clear();
		element.sendKeys(value);
	}

	public void checkToCustomCheckBox(WebDriver driver, String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		if (!element.isSelected()) {
			JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
			jsExecutor.executeScript("arguments[0].click();", element);
		}
	}

	public void uncheckToCustomCheckBox(WebDriver driver, String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		if (element.isSelected()) {
			JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
			jsExecutor.executeScript("arguments[0].click();", element);
		}
	}

	public void selectItemInHtmlDropdown(WebDriver driver, String locator, String selectValue) {
		WebElement element = driver.findElement(By.xpath(locator));
		Select select = new Select(element);
		select.selectByVisibleText(selectValue);
	}

	public void selectItemInHtmlDropdown(WebDriver driver, String locator, String selectValue, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		WebElement element = driver.findElement(By.xpath(locator));
		Select select = new Select(element);
		select.selectByVisibleText(selectValue);
	}

	public String getSelectedItemInHtmlDropdown(WebDriver driver, String locator, String expectValue) {
		WebElement element = driver.findElement(By.xpath(locator));
		Select select = new Select(element);
		select.selectByVisibleText(expectValue);
		return select.getFirstSelectedOption().getText();
	}

	public String getSelectedItemInHtmlDropdown(WebDriver driver, String locator, String expectedValue, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		WebElement element = driver.findElement(By.xpath(locator));
		Select select = new Select(element);
		select.selectByVisibleText(expectedValue);

		return select.getFirstSelectedOption().getText();
	}

	public void selectItemInCustomDropdown(WebDriver driver, String parentXpath, String allItemXpath, String expectedValueItem) throws InterruptedException {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		WebDriverWait waitExplicit = new WebDriverWait(driver, 60);

		WebElement parentDropdown = driver.findElement(By.xpath(parentXpath));

		if (parentDropdown.isDisplayed()) {
			parentDropdown.click();
		} else {
			jsExecutor.executeScript("arguments[0].click();", parentDropdown);
		}

		waitExplicit.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(allItemXpath)));

		List<WebElement> allItems = driver.findElements(By.xpath(allItemXpath));

		for (int i = 0; i < allItems.size(); i++) {
			if (allItems.get(i).getText().equals(expectedValueItem)) {
				jsExecutor.executeScript("arguments[0].scrollIntoView(true);", allItems.get(i));
				Thread.sleep(1500);
				if (allItems.get(i).isDisplayed()) {
					allItems.get(i).click();
				} else {
					jsExecutor.executeScript("arguments[0].click();", allItems.get(i));
				}

				break;
			}
		}
	}

	public void selectMutipleItems(WebDriver driver, String parentXpath, String allItemsXpath, String[] expectedValues, By selectedLocators) throws Exception {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		WebDriverWait waitExplicit = new WebDriverWait(driver, 60);
		WebElement parentDropdown = driver.findElement(By.xpath(parentXpath));
		if (parentDropdown.isDisplayed()) {
			parentDropdown.click();
		} else {
			jsExecutor.executeScript("arguments[0].click();", parentDropdown);
		}

		waitExplicit.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(allItemsXpath)));
		List<WebElement> allItems = driver.findElements(By.xpath(allItemsXpath));
		for (WebElement childELement : allItems) {
			for (String item : expectedValues) {
				if (childELement.getText().equals(item)) {
					jsExecutor.executeScript("arguments[0].scrollIntoView(true);", childELement);
					Thread.sleep(1000);
					if (childELement.isDisplayed()) {
						childELement.click();
					} else {
						jsExecutor.executeScript("arguments[0].click();", childELement);
					}
					Thread.sleep(1000);
					List<WebElement> selectedItems = driver.findElements(selectedLocators);

					if (expectedValues.length == selectedItems.size()) {
						break;

					}

				}

			}
		}
	}

	public String[] getListOfValueInDropDown(WebDriver driver, String ParentXpath, String AllChidren) {
		clickToElement(driver, ParentXpath);
		;
		WebDriverWait waitExplicit = new WebDriverWait(driver, 60);
		waitExplicit.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(AllChidren)));
		List<WebElement> elements = driver.findElements(By.xpath(AllChidren));
		int noOfElement = elements.size() - 1;
		System.out.println("Tong so gia Tri " + noOfElement);
		String[] Array1 = new String[noOfElement];
		for (int i = 0; i < noOfElement; i++) {
			Array1[i] = driver.findElements(By.xpath(AllChidren)).get(i + 1).getText();
			System.out.println("Gia Tri Trong Mang " + i + "  " + Array1[i]);
		}
		Arrays.sort(Array1);
		return Array1;
	}

	public String[] getListOfValueInDropDownExceptTheFirstOne(WebDriver driver, String AllChidren, String... dynamicValue) {
		AllChidren = String.format(AllChidren, (Object[]) dynamicValue);
		List<WebElement> elements = driver.findElements(By.xpath(AllChidren));
		int noOfElement = elements.size() - 1;
		System.out.println("Tong so gia Tri " + noOfElement);
		String[] Array1 = new String[noOfElement];
		for (int i = 0; i < noOfElement; i++) {
			Array1[i] = driver.findElements(By.xpath(AllChidren)).get(i + 1).getText().trim();
			System.out.println("Gia Tri Trong Mang " + i + "  " + Array1[i]);
		}
		return Array1;
	}

	public String[] getListOfValueInDropDown(WebDriver driver, String AllChidren, String... dynamicValue) {
		AllChidren = String.format(AllChidren, (Object[]) dynamicValue);
		List<WebElement> elements = driver.findElements(By.xpath(AllChidren));
		int noOfElement = elements.size();
		System.out.println("Tong so gia Tri " + noOfElement);
		String[] Array1 = new String[noOfElement];
		for (int i = 0; i < noOfElement; i++) {
			Array1[i] = driver.findElements(By.xpath(AllChidren)).get(i).getText().trim();
			System.out.println("Gia Tri Trong Mang " + i + "  " + Array1[i]);
		}
		return Array1;
	}

	public String getAttributeValue(WebDriver driver, String locator, String attribute) {
		WebElement element = driver.findElement(By.xpath(locator));
		return element.getAttribute(attribute);
	}

	public String getTextElement(WebDriver driver, String locator, String... dynamicValue) {
		waitForElementVisible(driver, locator, dynamicValue);
		locator = String.format(locator, (Object[]) dynamicValue);
		WebElement element = driver.findElement(By.xpath(locator));
		return element.getText();
	}

	public String getTextElement(WebDriver driver, String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		return element.getText();
	}

	public int countElementNumber(WebDriver driver, String locator) {
		List<WebElement> elements = driver.findElements(By.xpath(locator));
		return elements.size();
	}

	public int countElementNumber(WebDriver driver, String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		List<WebElement> elements = driver.findElements(By.xpath(locator));
		return elements.size();
	}

	public boolean isControlSelected(WebDriver driver, String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		if (element.isSelected()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isControlDisplayed(WebDriver driver, String locator) {
		try {
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			WebElement element = driver.findElement(By.xpath(locator));
			boolean status = element.isDisplayed();
			System.out.println("element" + status);
			return status;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	int longTime = 30;
	int shortTime = 5;

	public boolean isControlUnDisplayed(WebDriver driver, String locator) {
		overRideTimeOut(driver, shortTime);
		List<WebElement> elements = driver.findElements(By.xpath(locator));
		if (elements.size() > 0 && elements.get(0).isDisplayed()) {
			overRideTimeOut(driver, longTime);
			return false;
		} else {
			overRideTimeOut(driver, longTime);
			return true;

		}
	}

	public void overRideTimeOut(WebDriver driver, int time) {
		driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
	}

	public boolean isControlEnabled(WebDriver driver, String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		if (element.isEnabled()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isControlEnabled(WebDriver driver, String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		WebElement element = driver.findElement(By.xpath(locator));
		if (element.isEnabled()) {
			return true;
		} else {
			return false;
		}
	}

//Windows
	public void switchToChildWindowByID(WebDriver driver, String parentID) {
		Set<String> allWindowID = driver.getWindowHandles();
		for (String childID : allWindowID) {
			if (!childID.equals(parentID)) {
				driver.switchTo().window(childID);
				break;
			}
		}
	}

	public void switchToWindowByTitle(WebDriver driver, String childTitle) {
		Set<String> allWindowID = driver.getWindowHandles();
		for (String childrenID : allWindowID) {
			driver.switchTo().window(childrenID);
			String currentWin = driver.getTitle();
			if (currentWin.equals(childTitle)) {
				break;
			}

		}
	}

	public boolean closeAllWithoutparentWindow(WebDriver driver, String parentID) {
		Set<String> allWindowID = driver.getWindowHandles();
		for (String childID : allWindowID) {

			if (!childID.equals(parentID)) {
				driver.switchTo().window(childID);
				driver.close();
			}
		}
		driver.switchTo().window(parentID);
		if (driver.getWindowHandles().size() == 1) {
			return true;
		} else
			return false;
	}

	public void switchToIframe(WebDriver driver, String locator) {
		WebElement frame = driver.findElement(By.xpath(locator));
		driver.switchTo().frame(frame);
	}

	public void backToDefault(WebDriver driver, String locator) {
		driver.switchTo().defaultContent();
	}

//User actions
	public void doubleClickToElement(WebDriver driver, String locator) {
		Actions action = new Actions(driver);
		WebElement element = driver.findElement(By.xpath(locator));
		action.doubleClick(element).perform();
	}

	public void hoverMouseToElement(WebDriver driver, String locator) {
		Actions action = new Actions(driver);
		WebElement element = driver.findElement(By.xpath(locator));
		action.moveToElement(element).perform();
	}

	public void rightClickToElement(WebDriver driver, String locator) {
		Actions action = new Actions(driver);
		WebElement element = driver.findElement(By.xpath(locator));
		action.contextClick(element).perform();
	}

	public void dragAndDropToElement(WebDriver driver, By parentlocator, By tagretLocator) {
		Actions action = new Actions(driver);
		WebElement parentelement = driver.findElement(parentlocator);
		WebElement targetElement = driver.findElement(tagretLocator);
		action.dragAndDrop(parentelement, targetElement).perform();
	}

	public void sendkeyboardToElement(WebDriver driver, String locator, Keys key) {
		WebElement element = driver.findElement(By.xpath(locator));
		Actions action = new Actions(driver);
		action.sendKeys(element, key).perform();

	}

//upload
	public void uploadSingleFile(WebDriver driver, String locator, String fileName) {
		WebElement addFileButton = driver.findElement(By.xpath(locator));
		String rootFolder = System.getProperty("user.dir");
		String filePath = rootFolder + "\\Upload_file\\" + fileName;
		addFileButton.sendKeys(filePath);

	}

	public void uploadMultipleFilesInQueue(WebDriver driver, String locator, String fileName01, String fileName02, String fileName03) {
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

	public void uploadMultipleFilesAtOnce(WebDriver driver, String locator, String fileName01, String fileName02, String fileName03) {
		WebElement addFileButton = driver.findElement(By.xpath(locator));
		String rootFolder = System.getProperty("user.dir");
		String filePath01 = rootFolder + "\\Upload_file\\" + fileName01;
		String filePath02 = rootFolder + "\\Upload_file\\" + fileName02;
		String filePath03 = rootFolder + "\\Upload_file\\" + fileName03;
		String filePath = filePath01 + "\n" + filePath02 + "\n" + filePath03;

		addFileButton.sendKeys(filePath);
	}

	public void uploadByAutoIT(WebDriver driver, String uploadButtonlocator, String fileName01, String fileName02, String fileName03) throws Exception {
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

	public void uploadByRobot(WebDriver driver, String uploadButtonlocator, String fileName01, String fileName02, String fileName03) throws Exception {
		String rootFolder = System.getProperty("user.dir");
		String filePath01 = rootFolder + "\\Upload_file\\" + fileName01;
		String filePath02 = rootFolder + "\\Upload_file\\" + fileName02;
		String filePath03 = rootFolder + "\\Upload_file\\" + fileName03;
		String[] files = { filePath01, filePath02, filePath03 };
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		for (String file : files) {
			StringSelection select = new StringSelection(file);

			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(select, null);
			WebElement uploadFileButton = driver.findElement(By.xpath(uploadButtonlocator));
			if (driver.toString().contains("ie")) {
				jsExecutor.executeScript("arguments[0].click();", uploadFileButton);
				Thread.sleep(1000);
			}

			else {

				uploadFileButton.click();
				Thread.sleep(1000);
			}

			Robot robot = new Robot();
			Thread.sleep(1000);

			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);

			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);

			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_V);
			Thread.sleep(1000);

			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			Thread.sleep(2000);
		}
	}

//Javascript
	public Object refreshBrowserByJS(WebDriver driver) {

		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		return jsExecutor.executeScript("history.go(0)");
	}

	public Object getTitleOfPageByJS(WebDriver driver) {

		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		return jsExecutor.executeScript("return document.title;").toString();
	}

	public Object getURLByJS(WebDriver driver) {

		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		return (String) jsExecutor.executeScript("return document.URL");
	}

	public Object getDomainByJS(WebDriver driver) {

		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		return (String) jsExecutor.executeScript("return document.domain");
	}

	public Object navigateToOtherPageByJS(WebDriver driver, String URL) {

		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		return jsExecutor.executeScript(URL);
	}

	public void highlightElement(WebDriver driver, WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].style.border='6px groove red'", element);
	}

	public Object executeForBrowser(WebDriver driver, String javaSript) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js.executeScript(javaSript);
	}

	public Object clickToElementByJS(WebDriver driver, WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js.executeScript("arguments[0].click();", element);
	}

	public Object sendkeyToElementByJS(WebDriver driver, WebElement element, String value) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js.executeScript("arguments[0].setAttribute('value', '" + value + "')", element);
	}

	public Object removeAttributeInDOM(WebDriver driver, WebElement element, String attribute) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js.executeScript("arguments[0].removeAttribute('" + attribute + "');", element);
	}

	public Object setAttributeInDOM(WebDriver driver, WebElement element, String attribute) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js.executeScript("arguments[0].setAttribute('" + attribute + "');", element);
	}

	public Object scrollToBottomPage(WebDriver driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
	}

	public Object scrollToElement(WebDriver driver, String locator) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement element = driver.findElement(By.xpath(locator));
		return js.executeScript("arguments[0].click();", element);
	}

	public Object navigateToUrlByJS(WebDriver driver, String url) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js.executeScript("window.location = '" + url + "'");
	}

//waits
	public void implicitWaitLongTime(WebDriver driver) {
		long longTime = 30;
		driver.manage().timeouts().implicitlyWait(longTime, TimeUnit.SECONDS);

	}

	public void implicitWaitShortTime(WebDriver driver) {
		long shortTime = 5;
		driver.manage().timeouts().implicitlyWait(shortTime, TimeUnit.SECONDS);
	}

	public void waitForElementPresent(WebDriver driver, String locator) {
		WebDriverWait wait = new WebDriverWait(driver, longTime);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));
	}

	public void waitForAllElementsPresent(WebDriver driver, String locator) {
		WebDriverWait wait = new WebDriverWait(driver, longTime);
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(locator)));

	}

	public void waitForAllElementsPresent(WebDriver driver, String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		WebDriverWait wait = new WebDriverWait(driver, longTime);
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(locator)));

	}

	public void waitForElementVisible(WebDriver driver, String locator) {

		WebDriverWait wait = new WebDriverWait(driver, longTime);
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
		} catch (Exception e) {
			System.out.println(e.getMessage());

		}
	}

	public void waitForElementClickable(WebDriver driver, String locator) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator)));
	}

	public void waitForElementInvisible(WebDriver driver, String locator) {
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

	public String getAttributeValue(WebDriver driver, String locator, String attribute, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		WebElement element = driver.findElement(By.xpath(locator));
		return element.getAttribute(attribute);
	}

	public void clickToElementByJava(WebDriver driver, String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		WebElement element = driver.findElement(By.xpath(locator));
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].click();", element);
	}

	public boolean isControlDisplayed(WebDriver driver, String locator, String... dynamicValue) {
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

	public boolean isControlUnDisplayed(WebDriver driver, String locator, String... dynamicValue) {
		overRideTimeOut(driver, shortTime);
		locator = String.format(locator, (Object[]) dynamicValue);
		List<WebElement> elements = driver.findElements(By.xpath(locator));
		if (elements.size() > 0 && elements.get(0).isDisplayed()) {
			overRideTimeOut(driver, longTime);
			return false;
		} else {
			overRideTimeOut(driver, longTime);
			return true;

		}
	}

	public void sleep(WebDriver driver, long milisecond) {
		try {
			Thread.sleep(milisecond);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void removeAttributeInDOM(WebDriver driver, String locator, String attribute, String... value) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		locator = String.format(locator, (Object[]) value);
		WebElement element = driver.findElement(By.xpath(locator));
		js.executeScript("arguments[0].removeAttribute('" + attribute + "');", element);
		sleep(driver, 1000);
	}

//	public void waitForElementVisible(WebDriver driver, String locator, String... dynamicValue) {
//		locator = String.format(locator, (Object[]) dynamicValue);
//		WebDriverWait wait = new WebDriverWait(driver, 30);
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
//	}

	public boolean waitForElementVisible(WebDriver driver, String locator, String... dynamicValue) {
		sleep(driver, 1000);
		locator = String.format(locator, (Object[]) dynamicValue);
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

	public void clickToElement(WebDriver driver, String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		WebElement element = driver.findElement(By.xpath(locator));
		if (driver.toString().toLowerCase().contains("internet explorer")) {
			clickToElementByJava(driver, locator, dynamicValue);
			sleep(driver, 5000);
		}

		else {
			element.click();
		}

	}

	public void sendkeyboardToElement(WebDriver driver, String locator, Keys key, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		WebElement element = driver.findElement(By.xpath(locator));
		Actions action = new Actions(driver);
		action.sendKeys(element, key).perform();
	}

	public void clearInputText(WebDriver driver, String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		WebElement element = driver.findElement(By.xpath(locator));
		element.clear();

	}

	public String[] getListOfValueInTable(WebDriver driver, String rowXpath, String nextXpath, String currentStatusXpath, String attribute, String disableStatus) {

		List<String> arrayList = new ArrayList<>();

		WebDriverWait waitExplicit = new WebDriverWait(driver, 60);
		waitExplicit.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(rowXpath)));
		List<WebElement> element1 = driver.findElements(By.xpath(rowXpath));
		for (int i = 0; i < element1.size(); i++) {
			arrayList.add(driver.findElements(By.xpath(rowXpath)).get(i).getText().trim());
		}
		for (int x = 0; x < 2000; x++) {
			waitExplicit.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(currentStatusXpath)));
			String currentStatus = driver.findElement(By.xpath(currentStatusXpath)).getAttribute(attribute);
			if (!currentStatus.equalsIgnoreCase(disableStatus)) {
				driver.findElement(By.xpath(nextXpath)).click();
				;
				waitExplicit.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(rowXpath)));
				List<WebElement> element2 = driver.findElements(By.xpath(rowXpath));
				for (int n = 0; n < element2.size(); n++) {
					arrayList.add(driver.findElements(By.xpath(rowXpath)).get(n).getText().trim());
				}
			} else if (currentStatus.equalsIgnoreCase(disableStatus)) {
				break;
			}
		}

		return arrayList.toArray(new String[arrayList.size()]);
	}

	public String[] getListOfValueInTheFirstPage(WebDriver driver, String rowXpath) {

		List<String> arrayList = new ArrayList<>();

		WebDriverWait waitExplicit = new WebDriverWait(driver, 60);
		waitExplicit.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(rowXpath)));
		List<WebElement> element1 = driver.findElements(By.xpath(rowXpath));
		for (int i = 0; i < element1.size(); i++) {
			arrayList.add(driver.findElements(By.xpath(rowXpath)).get(i).getText().trim());
		}

		return arrayList.toArray(new String[arrayList.size()]);

	}

	public String getPublicIPAddress() {
		InetAddress ip = null;
		String ip2 = "";
		try {
			ip = InetAddress.getLocalHost();
			ip2 = ip.getHostAddress();
			System.out.println("Current IP Address" + ip2);
		} catch (UnknownHostException e) {

			e.printStackTrace();
		}
		return ip2;
	}

	public boolean ConvertDateToInt(WebDriver driver, String dateXpath) throws ParseException {
		// get list date
		boolean status = false;
		WebDriverWait waitExplicit = new WebDriverWait(driver, 60);
		waitExplicit.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(dateXpath)));
		List<WebElement> element1 = driver.findElements(By.xpath(dateXpath));
		List<Integer> arrayDate = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			String dateTime = element1.get(i).getText().trim();

			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a");
			Date date = formatter.parse(dateTime);
			System.out.println(formatter.format(date));

			int DateToInt = (int) (date.getTime() / 1000);
			arrayDate.add(DateToInt);
			System.out.println(arrayDate);
		}

		for (int n = 0; n < arrayDate.size() - 1; n++) {
			if (arrayDate.get(n) > arrayDate.get(n + 1)) {
				status = true;
			}

		}

		return status;
	}

	public void clearText(WebDriver driver, String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		WebElement element = driver.findElement(By.xpath(locator));

		element.clear();
	}

	public List<String> getTextInListElements(WebDriver driver, String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		List<WebElement> listElements = driver.findElements(By.xpath(locator));
		List<String> listTextView = new ArrayList<String>();
		for (WebElement element : listElements) {
			listTextView.add(element.getText());
		}
		return listTextView;
	}

//	INPUT METHODS
	public void inputIntoInputByID(WebDriver driver, String inputValue, String dynamicID) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicWebPageUIs.DYNAMIC_INPUT_BY_ID, dynamicID);
		if (status == true) {
			clearText(driver, DynamicWebPageUIs.DYNAMIC_INPUT_BY_ID, dynamicID);
			sendKeyToElement(driver, DynamicWebPageUIs.DYNAMIC_INPUT_BY_ID, inputValue, dynamicID);
		}

	}

//	CLICK METHODS	
	public void clickToDynamicButtonByID(WebDriver driver, String dynamicID) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicWebPageUIs.DYNAMIC_BUTTON_BY_ID, dynamicID);
		if (status == true) {
			clickToElement(driver, DynamicWebPageUIs.DYNAMIC_BUTTON_BY_ID, dynamicID);
		}
	}

	public void clickToDynamicLinkLiByID(WebDriver driver1, String dynamicID) {
		boolean status = false;
		status = waitForElementVisible(driver1, DynamicWebPageUIs.DYNAMIC_LINK_LI_BY_ID, dynamicID);
		if (status == true) {
			clickToElement(driver1, DynamicWebPageUIs.DYNAMIC_LINK_LI_BY_ID, dynamicID);
		}
	}

	public void clickToDynamicLinkAByID(WebDriver driver1, String dynamicID) {
		boolean status = false;
		status = waitForElementVisible(driver1, DynamicWebPageUIs.DYNAMIC_LINK_A_BY_ID, dynamicID);
		if (status == true) {
			clickToElement(driver1, DynamicWebPageUIs.DYNAMIC_LINK_A_BY_ID, dynamicID);
		}
	}

	public void clickToDynamicSelectByClass(WebDriver driver1, String dynamicID) {
		boolean status = false;
		status = waitForElementVisible(driver1, DynamicWebPageUIs.DYNAMIC_SELECT_BY_CLASS, dynamicID);
		if (status == true) {
			clickToElement(driver1, DynamicWebPageUIs.DYNAMIC_SELECT_BY_CLASS, dynamicID);
		}
	}

	public void clickToDynamicIconPackage(WebDriver driver1, String dynamicValue, String dynamicValue2) {
		boolean status = false;
		status = waitForElementVisible(driver1, DynamicWebPageUIs.DYNAMIC_ICON_TITLE_BY_FOLLOW_TEXT, dynamicValue, dynamicValue2);
		if (status == true) {
			clickToElement(driver1, DynamicWebPageUIs.DYNAMIC_ICON_TITLE_BY_FOLLOW_TEXT, dynamicValue, dynamicValue2);
		}
	}

	public void clickToDynamicIconPencil(WebDriver driver1, String dynamicValue, String dynamicValue2) {
		boolean status = false;
		status = waitForElementVisible(driver1, DynamicWebPageUIs.DYNAMIC_ICON_PENCIL, dynamicValue, dynamicValue2);
		if (status == true) {
			clickToElement(driver1, DynamicWebPageUIs.DYNAMIC_ICON_PENCIL, dynamicValue, dynamicValue2);
		}
	}

	public void clickToDynamicSelectDropdownList(WebDriver driver1, String dynamicClassName, String value) {
		Select oSelect = new Select(driver1.findElement(By.className(dynamicClassName)));
		oSelect.selectByVisibleText(value);
	}

	public void clickToDynamicLinkTextByID(WebDriver driver, String dynamicID) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicWebPageUIs.DYNAMIC_LINK_TEXT_BY_ID, dynamicID);
		if (status == true) {
			clickToElement(driver, DynamicWebPageUIs.DYNAMIC_LINK_TEXT_BY_ID, dynamicID);
		}
	}

	public void clickToDynamicLinkTextByText(WebDriver driver, String dynamicText) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicWebPageUIs.DYNAMIC_LINK_TEXT_BY_TEXT, dynamicText);
		if (status == true) {
			clickToElement(driver, DynamicWebPageUIs.DYNAMIC_LINK_TEXT_BY_TEXT, dynamicText);
		}
	}

	public void clickToDynamicButtonATagByID(WebDriver driver, String dynamicID) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicWebPageUIs.DYNAMIC_BUTTON_A_BY_ID, dynamicID);
		if (status == true) {
			clickToElement(driver, DynamicWebPageUIs.DYNAMIC_BUTTON_A_BY_ID, dynamicID);
		}
	}

	public void clickToDynamicButtonATagByClass(WebDriver driver, String dynamicClass) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicWebPageUIs.DYNAMIC_BUTTON_A_BY_CLASS, dynamicClass);
		if (status == true) {
			clickToElement(driver, DynamicWebPageUIs.DYNAMIC_BUTTON_A_BY_CLASS, dynamicClass);
		}
	}

	public void clickToDynamicMenuByLink(WebDriver driver, String dynamicLink) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicWebPageUIs.DYNAMIC_MENU_BY_LINK, dynamicLink);
		if (status == true) {
			clickToElement(driver, DynamicWebPageUIs.DYNAMIC_MENU_BY_LINK, dynamicLink);
		}
	}

	public void clickToDynamicIconByText(WebDriver driver, String... dynamicText) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicWebPageUIs.DYNAMIC_ICON_TITLE_BY_FOLLOW_TEXT, dynamicText);
		if (status == true) {
			clickToElement(driver, DynamicWebPageUIs.DYNAMIC_ICON_TITLE_BY_FOLLOW_TEXT, dynamicText);
		}
	}

	public void clickToDynamicIconByTwoTexts(WebDriver driver, String... dynamicText) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicWebPageUIs.DYNAMIC_ICON_TITLE_BY_FOLLOW_TWO_TEXTS, dynamicText);
		if (status == true) {
			clickToElement(driver, DynamicWebPageUIs.DYNAMIC_ICON_TITLE_BY_FOLLOW_TWO_TEXTS, dynamicText);
		}
	}

	public void clickToDynamicIconByThreeTexts(WebDriver driver, String... dynamicText) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicWebPageUIs.DYNAMIC_ICON_TITLE_BY_FOLLOW_THREE_TEXTS, dynamicText);
		if (status == true) {
			clickToElement(driver, DynamicWebPageUIs.DYNAMIC_ICON_TITLE_BY_FOLLOW_THREE_TEXTS, dynamicText);
		}
	}

	public void clickToDynamicNgClick(WebDriver driver, String dynamicText) {
		sleep(driver, 2000);
		boolean status = false;
		status = waitForElementVisible(driver, DynamicWebPageUIs.DYNAMIC_MENU_BY_NG_CLICK, dynamicText);
		if (status == true) {
			clickToElement(driver, DynamicWebPageUIs.DYNAMIC_MENU_BY_NG_CLICK, dynamicText);
		}
	}

	public void clickToDynamicSelectModel(WebDriver driver, String dynamictext) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicWebPageUIs.DYNAMIC_SELECT_MODEL, dynamictext);
		if (status == true) {
			clickToElement(driver, DynamicWebPageUIs.DYNAMIC_SELECT_MODEL, dynamictext);
		}
	}

	public void clickToDynamicOption(WebDriver driver, String dynamicvalue) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicWebPageUIs.DYNAMIC_OPTION_VALUE, dynamicvalue);
		if (status == true) {
			clickToElement(driver, DynamicWebPageUIs.DYNAMIC_OPTION_VALUE, dynamicvalue);
		}
	}

	public void clickToDynamicOptionText(WebDriver driver, String dynamicvalue) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicWebPageUIs.DYNAMIC_OPTION_TEXT, dynamicvalue);
		if (status == true) {
			clickToElement(driver, DynamicWebPageUIs.DYNAMIC_OPTION_TEXT, dynamicvalue);
		}
	}

	public void clickToDynamicSelectID(WebDriver driver, String dynamicID) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicWebPageUIs.DYNAMIC_SELECT_ID, dynamicID);
		if (status == true) {
			clickToElement(driver, DynamicWebPageUIs.DYNAMIC_SELECT_ID, dynamicID);
		}
	}

//  SELECT METHODS
	public void selectItemInDropdown(WebDriver driver, String dynamicClassID, String selectValue) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicWebPageUIs.DYNAMIC_DROPDOWN_BY_CLASS, dynamicClassID);
		if (status == true) {
			selectItemInHtmlDropdown(driver, DynamicWebPageUIs.DYNAMIC_DROPDOWN_BY_CLASS, selectValue, dynamicClassID);
		}
	}

	public void selectItemInDropdownByID(WebDriver driver, String dynamicID, String selectValue) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicWebPageUIs.DYNAMIC_DROPDOWN_BY_ID, dynamicID);
		if (status == true) {
			selectItemInHtmlDropdown(driver, DynamicWebPageUIs.DYNAMIC_DROPDOWN_BY_ID, selectValue, dynamicID);
		}
	}

//	GET METHODS
	public String getDataInInputByID(WebDriver driver, String dynamicClassID) {
		String data = "";
		boolean status = false;
		status = waitForElementVisible(driver, DynamicWebPageUIs.DYNAMIC_INPUT_BY_ID, dynamicClassID);
		if (status == true) {
			data = getAttributeValue(driver, DynamicWebPageUIs.DYNAMIC_INPUT_BY_ID, "value", dynamicClassID);
		}
		return data;
	}

	public String getDataFollowing(WebDriver driver, String dynamicClassID, String index) {
		String data = "";
		boolean status = false;
		status = waitForElementVisible(driver, DynamicWebPageUIs.DYNAMIC_TEXT_BY_FOLLOW_TEXT_INDEX, dynamicClassID, index);
		if (status == true) {
			data = getAttributeValue(driver, DynamicWebPageUIs.DYNAMIC_TEXT_BY_FOLLOW_TEXT_INDEX, "value", dynamicClassID, index);
		}
		return data;
	}

	public String getDataSelectText(WebDriver driver, String dynamicClassID) {
		sleep(driver, 2000);
		String data = "";
		boolean status = false;
		status = waitForElementVisible(driver, DynamicWebPageUIs.DYNAMIC_SELECT_ID, dynamicClassID);
		if (status == true) {
			data = getAttributeValue(driver, DynamicWebPageUIs.DYNAMIC_SELECT_ID, "value", dynamicClassID);
		}
		return data;
	}

	public String getDataTdFollowing(WebDriver driver, String... dynamicValue) {
		String data = "";
		boolean status = false;
		status = waitForElementVisible(driver, DynamicWebPageUIs.DYNAMIC_TD_FOLLOWING_INDEX, dynamicValue);
		if (status == true) {
			data = getAttributeValue(driver, DynamicWebPageUIs.DYNAMIC_TD_FOLLOWING_INDEX, "value", dynamicValue);
		}
		return data;
	}

	public boolean checkListContain(List<String> actualList, List<String> expectList) {
		return expectList.containsAll(actualList);
	}

	public List<String> getListMetodOtp(WebDriver driver, String dynamictext) {
		boolean status = false;
		List<String> listText = null;
		status = waitForElementVisible(driver, DynamicWebPageUIs.DYNAMIC_GET_LIST_METHOD, dynamictext);
		if (status == true) {
			listText = getTextInListElements(driver, DynamicWebPageUIs.DYNAMIC_GET_LIST_METHOD, dynamictext);
		}
		return listText;
	}

	public List<String> getDynamicDataByListIcon(WebDriver driver, String dynamicText, String index) {
		boolean status = false;
		boolean isDisplayed = false;
		String locator = "";
		List<String> getMethodList = new ArrayList<String>();
		isDisplayed = isControlDisplayed(driver, DynamicWebPageUIs.DYNAMIC_TEXT_BY_FOLLOW_TEXT_INDEX, dynamicText, index);
		if (isDisplayed == true) {
			locator = String.format(DynamicWebPageUIs.DYNAMIC_TEXT_BY_FOLLOW_TEXT_INDEX, dynamicText, index);
			List<WebElement> elements = driver.findElements(By.xpath(locator));
			status = elements.size() > 0;
			if (status == true) {
				for (WebElement element : elements) {
					getMethodList.add(element.getText());
				}
			}
		}
		return getMethodList;
	}
	
	public String getDynamicDataByIcon(WebDriver driver, String dynamicText, String index) {
		boolean status = false;
		String text = "";
		status = isControlDisplayed(driver, DynamicWebPageUIs.DYNAMIC_TEXT_BY_FOLLOW_TEXT_INDEX, dynamicText, index);
		if(status == true) {
			text = getTextElement(driver,DynamicWebPageUIs.DYNAMIC_TEXT_BY_FOLLOW_TEXT_INDEX, dynamicText, index);
		}
		return text ;
	}

	public String getDynamicDataByMethod(WebDriver driver, String dynamicText, String index) {
		String data = "";
		boolean status = false;
		status = waitForElementVisible(driver, DynamicWebPageUIs.DYNAMIC_TEXT_BY_FOLLOW_TEXT_INDEX, dynamicText, index);
		if (status == true) {
			data = getTextElement(driver, DynamicWebPageUIs.DYNAMIC_TEXT_BY_FOLLOW_TEXT_INDEX, dynamicText, index);
		}
		return data;
	}

}
