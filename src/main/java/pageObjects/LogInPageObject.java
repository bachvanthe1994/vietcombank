package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import commons.AbstractPage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import vietcombankUI.LoginPageUIs;

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
	
	public void verifyIconChangeLanguage() {
		waitForElementVisible(driver, LoginPageUIs.TEXT_LANGUAGE);
//		Assert.assertTrue(isControlDisplayed(driver, LoginPageUIs.ICON_CHANGE_LANGUAGE));
		String actualTextChangeLanguage = driver.findElement(By.xpath(LoginPageUIs.TEXT_LANGUAGE)).getText();
		Assert.assertEquals(actualTextChangeLanguage, "Vietnamese");
	}
}
