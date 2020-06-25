package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.AbstractPage;
import commons.WebAbstractPage;

public class WebBackendSetupPageObject extends AbstractPage {
	private WebDriver driver1;
	WebAbstractPage test = new WebAbstractPage();
	int longTime = 40;

	public WebBackendSetupPageObject(WebDriver mappingDriver) {
		driver1 = mappingDriver;
	}


	
}