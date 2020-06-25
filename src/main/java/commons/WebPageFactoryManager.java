package commons;

import org.openqa.selenium.WebDriver;

import pageObjects.WebBackendSetupPageObject;

public class WebPageFactoryManager {
	
	public static WebBackendSetupPageObject getWebBackendSetupPageObject(WebDriver driver) {
		return new WebBackendSetupPageObject(driver);
	}

}