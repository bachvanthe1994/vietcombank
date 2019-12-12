package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import commons.AbstractPage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import vietcombankUI.DynamicPageUIs;
import vietcombankUI.LoginPageUIs;

public class LogInPageObject extends AbstractPage{

	public LogInPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}

	public void inputPhoneNumber(String inputValue) {
		waitForElementVisible(LoginPageUIs.PHONE_NUMBER_TEXT_FIELD);
		clearText(LoginPageUIs.PHONE_NUMBER_TEXT_FIELD);
		sendKeyToElement(LoginPageUIs.PHONE_NUMBER_TEXT_FIELD, inputValue);
	}
	
	public void clearPhoneNumber() {
		clearText(LoginPageUIs.PHONE_NUMBER_TEXT_FIELD);
	}
	
	public void verifyIconChangeLanguage() {
		waitForElementVisible(LoginPageUIs.TEXT_LANGUAGE);
		Assert.assertTrue(isControlDisplayed(LoginPageUIs.ICON_CHANGE_LANGUAGE));
		String actualTextChangeLanguage = driver.findElement(By.xpath(LoginPageUIs.TEXT_LANGUAGE)).getText();
		Assert.assertEquals(actualTextChangeLanguage, "Vietnamese");
	}
	
	public void verifyPhoneNumberTextBox(String inputValue) {
		String actualTextChangeLanguage = driver.findElement(By.xpath(LoginPageUIs.PHONE_NUMBER_TEXT_FIELD)).getText();
		int countNumberCharacterOfInputValue = actualTextChangeLanguage.length();
		Assert.assertEquals(countNumberCharacterOfInputValue, 10);
	}
	
	public void inputPassword(String inputValue, String placeHolder, String defaultvalue) {
		clearText(DynamicPageUIs.DYNAMIC_INPUT_BOX, placeHolder);
		waitForElementVisible(DynamicPageUIs.DYNAMIC_INPUT_BOX, defaultvalue);
		sendKeyToElement(DynamicPageUIs.DYNAMIC_INPUT_BOX, inputValue, defaultvalue);
	}
	
	public void verifyDefaultPasswordTextboxAllowInputValue(String inputValue) {
		String locator = String.format(DynamicPageUIs.DYNAMIC_INPUT_BOX, inputValue);
		WebElement element = driver.findElement(By.xpath(locator));
		String actualText = element.getText();
		Assert.assertEquals(actualText, inputValue);
	}
}
