package commons;

import org.openqa.selenium.WebDriver;

import pageObjects.WebLogInPageObject;

public class WebPageFactoryManager {

	public static WebLogInPageObject getWebLogInPageObject(WebDriver driver) {
		return new WebLogInPageObject(driver);
	}

}