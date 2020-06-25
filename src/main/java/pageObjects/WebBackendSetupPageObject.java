package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.WebAbstractPage;
import model.ServiceLimitInfo;

public class WebBackendSetupPageObject extends WebAbstractPage {
	private WebDriver driver;
	
	public WebBackendSetupPageObject(WebDriver mappingDriver) {
		driver = mappingDriver;
	}
	
	public static ServiceLimitInfo getInfo = new ServiceLimitInfo(null, null, null, null);

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
		
	}
	
	public void resetServiceLimitBackend(WebDriver driver,String selectValue,ServiceLimitInfo inputInfo) {
		
		clickToDynamicMenuByLink(driver, "/Package/Index?f=2&c=191");
		selectItemInDropdown(driver, "ng-pristine", "100");
		clickToDynamicIconByText(driver, "PKG1","Assign Service Limit");
		selectItemInDropdown(driver, "ng-pristine", "100");
		clickToDynamicIconByTwoTexts(driver, selectValue,"All","Edit Service Limit");
		inputIntoInputByID(driver, inputInfo.timesDay, "edit-times-day");
		inputIntoInputByID(driver, inputInfo.minTran, "edit-min-tran");
		inputIntoInputByID(driver, inputInfo.maxTran, "edit-max-tran");
		inputIntoInputByID(driver, inputInfo.totalLimit, "edit-total-limit");
		clickToDynamicButtonATagByID(driver, "edit-limit");
		
	}
	
}