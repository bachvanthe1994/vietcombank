package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.WebAbstractPage;

public class WebBackendSetupPageObject extends WebAbstractPage {
	private WebDriver driver1;
	WebAbstractPage test = new WebAbstractPage();
	int longTime = 40;

	public WebBackendSetupPageObject(WebDriver mappingDriver) {
		driver1 = mappingDriver;
	}

	public void Login_Web_Backend(String username, String passWeb) {
		inputIntoInputByID(driver1, username, "login-username");
		inputIntoInputByID(driver1, passWeb, "login-password");
		clickToDynamicButtonByID(driver1, "btn-login");
	}

	public void Setup_Assign_Services_Limit() {

	}

	public void Setup_Assign_Services_Type_Limit() {

	}

	public void Setup_Package_Total_Limit() {

	}

}