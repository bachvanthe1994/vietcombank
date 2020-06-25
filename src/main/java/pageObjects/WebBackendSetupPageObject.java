package pageObjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import commons.Constants;
import commons.PageFactoryManager;
import commons.WebAbstractPage;
import model.ServiceLimitInfo;

public class WebBackendSetupPageObject extends WebAbstractPage {

	private WebDriver driver;

	WebAbstractPage test = new WebAbstractPage();
	WebBackendSetupPageObject webBESetup = new WebBackendSetupPageObject(driver);
	int longTime = 40;

	public WebBackendSetupPageObject(WebDriver driverWeb) {
	}

	public static ServiceLimitInfo getInfo = new ServiceLimitInfo(null, null, null, null);

//	username: user đăng nhập backend
//	passWeb: pass đăng nhập backend
	public void Login_Web_Backend(WebDriver driver, String username, String passWeb) {
		inputIntoInputByID(driver, username, "login-username");
		inputIntoInputByID(driver, passWeb, "login-password");
		clickToDynamicButtonByID(driver, "btn-login");
	}


//	setup limit cho lần giao dịch
//	selectValue: service name
//	inputInfo: giá trị config truyền vào
	public void setup_Assign_Services_Limit(WebDriver driver,String selectValue,ServiceLimitInfo inputInfo) {
		
		clickToDynamicMenuByLink(driver, "/Package/Index?f=2&c=191");
		selectItemInDropdown(driver, "ng-pristine", "100");
		clickToDynamicIconByText(driver, "PKG1", "Assign Service Limit");
		selectItemInDropdown(driver, "ng-pristine", "100");
		clickToDynamicIconByTwoTexts(driver, selectValue, "All", "Edit Service Limit");
		getInfo.timesDay = getDataInInputByID(driver, "edit-times-day");
		getInfo.minTran = getDataInInputByID(driver, "edit-min-tran");
		getInfo.maxTran = getDataInInputByID(driver, "edit-max-tran");
		getInfo.totalLimit = getDataInInputByID(driver, "edit-total-limit");
		inputIntoInputByID(driver, inputInfo.timesDay, "edit-times-day");
		inputIntoInputByID(driver, inputInfo.minTran, "edit-min-tran");
		inputIntoInputByID(driver, inputInfo.maxTran, "edit-max-tran");
		inputIntoInputByID(driver, inputInfo.totalLimit, "edit-total-limit");
		clickToDynamicButtonATagByID(driver, "edit-limit");

	}



	
//	selectValue: service name
//	inputInfo: giá trị config truyền vào
	public void resetServiceLimitBackend(WebDriver driver,String selectValue,ServiceLimitInfo inputInfo) {
		

		clickToDynamicMenuByLink(driver, "/Package/Index?f=2&c=191");
		selectItemInDropdown(driver, "ng-pristine", "100");
		clickToDynamicIconByText(driver, "PKG1", "Assign Service Limit");
		selectItemInDropdown(driver, "ng-pristine", "100");
		clickToDynamicIconByTwoTexts(driver, selectValue, "All", "Edit Service Limit");
		inputIntoInputByID(driver, inputInfo.timesDay, "edit-times-day");
		inputIntoInputByID(driver, inputInfo.minTran, "edit-min-tran");
		inputIntoInputByID(driver, inputInfo.maxTran, "edit-max-tran");
		inputIntoInputByID(driver, inputInfo.totalLimit, "edit-total-limit");
		clickToDynamicButtonATagByID(driver, "edit-limit");


		
	}
	



	public void Setup_Package_Total_Limit(WebDriver driver, String selectValue, ServiceLimitInfo inputInf) {

		clickToDynamicMenuByLink(driver, "/Package/Index?f=2&c=191");
		selectItemInDropdown(driver, "ng-pristine", "100");
		clickToDynamicIconByText(driver, "PKG1", "Assign Service Limit");
		selectItemInDropdown(driver, "ng-pristine", "100");
		webBESetup.clickToDynamicIconPackage(driver, "PKG1", "Assign Package Total Limit");
		webBESetup.clickToDynamicIconPencil(driver, "ALL", "blue");
		webBESetup.inputIntoInputByID(driver, inputInf.maxTran, "edit-limit-day");
		webBESetup.clickToDynamicLinkAByID(driver, "update-servicetype");
		driver.switchTo().alert().accept();

	}
	


}