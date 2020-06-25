package commons;

import org.openqa.selenium.WebDriver;

import pageObjects.WebBackendSetupPageObject;
import pageObjects.WebLogInPageObject;

public class WebPageFactoryManager {

	public static WebLogInPageObject getWebLogInPageObject(WebDriver driver) {
		return new WebLogInPageObject(driver);
	}
	
	public static WebBackendSetupPageObject getWebBackendSetupPageObject(WebDriver driver) {
		return new WebBackendSetupPageObject(driver);
	}

}