package pageObjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import commons.Constants;
import commons.PageFactoryManager;
import commons.WebAbstractPage;

public class WebBackendSetupPageObject extends WebAbstractPage {
	private WebDriver driver1;
	WebAbstractPage test = new WebAbstractPage();
	WebBackendSetupPageObject webBESetup = new WebBackendSetupPageObject(driver1);
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

	public void Setup_Package_Total_Limit(String idTypeSystem) {
		webBESetup = PageFactoryManager.getWebBackendSetupPageObject(driver1);
		webBESetup.clickToDynamicLinkLiByID(driver1, idTypeSystem);
		webBESetup.clickToDynamicSelectByClass(driver1, "ng-pristine ng-valid ng-touched");
		webBESetup.clickToDynamicSelectDropdownList(driver1, "ng-valid ng-touched ng-dirty ng-valid-parse", "100");
		JavascriptExecutor jse = (JavascriptExecutor) driver1;
		jse.executeScript("window.scrollBy(0,250)");
		webBESetup.clickToDynamicIconPackage(driver1, "PKG1", "Assign Package Total Limit");
		webBESetup.clickToDynamicIconPencil(driver1, "ALL", "blue");
		webBESetup.inputIntoInputByID(driver1, String.valueOf(Constants.MONEY_CHECK_VND), "edit-limit-day");
		webBESetup.clickToDynamicLinkAByID(driver1, "update-servicetype");
		driver1.switchTo().alert().accept();
		
	}

}