package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.WebAbstractPage;

public class WebBackendSetupPageObject extends WebAbstractPage {
	private WebDriver driver;
	WebAbstractPage test = new WebAbstractPage();
	int longTime = 40;

	public WebBackendSetupPageObject(WebDriver mappingDriver) {
		driver = mappingDriver;
	}

	public void Login_Web_Backend(String username, String passWeb) {
		inputIntoInputByID(driver, username, "login-username");
		inputIntoInputByID(driver, passWeb, "login-password");
		clickToDynamicButtonByID(driver, "btn-login");
	}

	public void Setup_Assign_Services_Limit() {
		clickToDynamicLinkLiByID(driver, "191");
		clickToDynamicSelectModel(driver, "PerPageItems");
	}

	public void Setup_Assign_Services_Type_Limit() {
		
		
	}

	public void Setup_Package_Total_Limit() {

	}

}