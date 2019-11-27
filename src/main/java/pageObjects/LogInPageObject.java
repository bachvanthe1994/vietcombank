package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.AbstractPage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class LogInPageObject extends AbstractPage{

	public LogInPageObject(AndroidDriver<AndroidElement> mappingDriver) {
		driver = mappingDriver;
	}
	private AndroidDriver<AndroidElement> driver;
	
	public void inputPhoneNumber(String inputValue) {
		waitForElementVisible(driver, LoginPageUIs.PHONE_NUMBER_TEXT_FIELD);
		clearText(driver, LoginPageUIs.PHONE_NUMBER_TEXT_FIELD);
		sendKeyToElement(driver, LoginPageUIs.PHONE_NUMBER_TEXT_FIELD, inputValue);
	}
	
	public void clearPhoneNumber() {
		clearText(driver, LoginPageUIs.PHONE_NUMBER_TEXT_FIELD);
	}
	
	public void verifyIconChangeLanguage() {
		waitForElementVisible(driver, LoginPageUIs.TEXT_LANGUAGE);
		Assert.assertTrue(isControlDisplayed(driver, LoginPageUIs.ICON_CHANGE_LANGUAGE));
		String actualTextChangeLanguage = driver.findElement(By.xpath(LoginPageUIs.TEXT_LANGUAGE)).getText();
		Assert.assertEquals(actualTextChangeLanguage, "Vietnamese");
	}
	
	public void verifyPhoneNumberTextBox(String inputValue) {
		String actualTextChangeLanguage = driver.findElement(By.xpath(LoginPageUIs.PHONE_NUMBER_TEXT_FIELD)).getText();
		int countNumberCharacterOfInputValue = actualTextChangeLanguage.length();
		Assert.assertEquals(countNumberCharacterOfInputValue, 10);
	}
	
	public void inputPassword(String inputValue, String placeHolder, String defaultvalue) {
		clearText(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, placeHolder);
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, defaultvalue);
		sendKeyToElement(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, inputValue, defaultvalue);
	}
	
	public void verifyDefaultPasswordTextboxAllowInputValue(String inputValue) {
		String locator = String.format(DynamicPageUIs.DYNAMIC_INPUT_BOX, inputValue);
		WebElement element = driver.findElement(By.xpath(locator));
		String actualText = element.getText();
		Assert.assertEquals(actualText, inputValue);
	}
}
