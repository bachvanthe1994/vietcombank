package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.WebAbstractPage;

public class WebLogInPageObject extends WebAbstractPage {

	public WebLogInPageObject(WebDriver driver1) {
		driver = driver1;
	}
	
	private WebDriver driver;

	public void Login_backend(String username, String passWeb) {
		inputIntoInputByID(driver, username, "login-username");
		inputIntoInputByID(driver, passWeb, "login-password");
		clickToDynamicButtonByID(driver, "btn-login");
	}
	
	public void Click_service_package() {
		clickToDynamicLinkTextByID(driver, "191");
		clickToDynamicLinkTextByText(driver, "3");
	}

	public void Config_limit() {
		
	}
}
