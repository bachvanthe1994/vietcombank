package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.WebAbstractPage;
import model.ServiceLimitInfo;

public class WebBackendSetupPageObject extends WebAbstractPage {

	private WebDriver driver;
	

	WebAbstractPage test = new WebAbstractPage();
	int longTime = 40;

	public WebBackendSetupPageObject(WebDriver mappingDriver) {
		driver = mappingDriver;
	}
	
	public ServiceLimitInfo getInfo = new ServiceLimitInfo(null, null, null, null);

	public void setupServiceLimitBackend(WebDriver driver,String selectValue,ServiceLimitInfo inputInfo) {
		
		clickToDynamicMenuByLink(driver, "/Package/Index?f=2&c=191");
		selectItemInDropdown(driver, "ng-pristine", "100");
		clickToDynamicIconByText(driver, "PKG1","Assign Service Limit");
		selectItemInDropdown(driver, "ng-pristine", "100");
		clickToDynamicIconByTwoTexts(driver, selectValue,"All","Edit Service Limit");
		getInfo.timesDay = getDataInInputByID(driver, "edit-times-day");
		getInfo.minTran = getDataInInputByID(driver, "edit-min-tran");
		getInfo.maxTran = getDataInInputByID(driver, "edit-max-tran");
		getInfo.totalLimit = getDataInInputByID(driver, "edit-total-limit");
		inputIntoInputByID(driver, inputInfo.timesDay, "edit-times-day");
		inputIntoInputByID(driver, inputInfo.minTran, "edit-min-tran");
		inputIntoInputByID(driver, inputInfo.maxTran, "edit-max-tran");
		inputIntoInputByID(driver, inputInfo.totalLimit, "edit-total-limit");
		clickToDynamicButtonATagByID(driver, "edit-limit");
		acceptAlert(driver);
		
	}
	
	public void resetServiceLimitBackend(WebDriver driver,String selectValue) {
		

		clickToDynamicIconByTwoTexts(driver, selectValue,"All","Edit Service Limit");
		inputIntoInputByID(driver, getInfo.timesDay, "edit-times-day");
		inputIntoInputByID(driver, getInfo.minTran, "edit-min-tran");
		inputIntoInputByID(driver, getInfo.maxTran, "edit-max-tran");
		inputIntoInputByID(driver, getInfo.totalLimit, "edit-total-limit");
		clickToDynamicButtonATagByID(driver, "edit-limit");
		acceptAlert(driver);
		
	}
	
	public void Login_Web_Backend(WebDriver driver,String username, String passWeb) {
		inputIntoInputByID(driver, username, "login-username");
		inputIntoInputByID(driver, passWeb, "login-password");
		clickToDynamicButtonByID(driver, "btn-login");
	}

	public void Setup_Assign_Services_Limit() {

	}

	public void Setup_Assign_Services_Type_Limit() {

	}

	public void Setup_Package_Total_Limit() {

	}

}