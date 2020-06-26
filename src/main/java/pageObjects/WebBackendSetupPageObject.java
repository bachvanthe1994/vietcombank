package pageObjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;

import commons.WebAbstractPage;
import model.ServiceLimitInfo;
import model.ServiceLimitInfo02;

public class WebBackendSetupPageObject extends WebAbstractPage {


	private WebDriver driver;

	public WebBackendSetupPageObject(WebDriver driverWeb) {
	}

	public static ServiceLimitInfo getInfo = new ServiceLimitInfo("", "", "", "");
	public List<ServiceLimitInfo02> getInfoList = new ArrayList<ServiceLimitInfo02>();

	public static String oldValue = "";

	public void Login_Web_Backend(WebDriver driver, String username, String passWeb) {
		inputIntoInputByID(driver, username, "login-username");
		inputIntoInputByID(driver, passWeb, "login-password");
		clickToDynamicButtonByID(driver, "btn-login");
	}

//	setup limit cho lần giao dịch
//	selectValue: service name
//	inputInfo: giá trị config truyền vào
	public void setup_Assign_Services_Limit(WebDriver driver, String selectValue, ServiceLimitInfo inputInfo) {

		clickToDynamicMenuByLink(driver, "/Package/Index?f=2&c=191");
		selectItemInDropdown(driver, "ng-pristine", "100");
		clickToDynamicIconByText(driver, "PKG1", "Assign Service Limit");
		selectItemInDropdown(driver, "ng-pristine", "100");
		clickToDynamicIconByText(driver, selectValue, "Edit Service Limit");
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

//	setup limit min max cho lần giao dịch
//	selectValue: service name
//	inputInfo: giá trị config truyền vào
	public void setupAssignServicesLimit(WebDriver driver, String dynamicText, ServiceLimitInfo info) {

		clickToDynamicMenuByLink(driver, "/Package/Index?f=2&c=191");
		selectItemInDropdown(driver, "ng-pristine", "100");
		clickToDynamicIconByText(driver, "PKG1", "Assign Service Limit");
		selectItemInDropdown(driver, "ng-pristine", "100");
		getInfoList = getAndInputDataByListIcon(driver, dynamicText, info);
	}

	public void resetAssignServicesLimit(WebDriver driver, String dynamicText) {
		inputDynamicDataByListIcon(driver, dynamicText);
		driver.quit();
	}

	public void resetServiceLimitBackend(WebDriver driver, String selectValue) {

		clickToDynamicIconByTwoTexts(driver, selectValue, "All", "Edit Service Limit");
		inputIntoInputByID(driver, getInfo.timesDay, "edit-times-day");
		inputIntoInputByID(driver, getInfo.minTran, "edit-min-tran");
		inputIntoInputByID(driver, getInfo.maxTran, "edit-max-tran");
		inputIntoInputByID(driver, getInfo.totalLimit, "edit-total-limit");
		clickToDynamicButtonATagByID(driver, "edit-limit");
		acceptAlert(driver);

	}

//	selectValue: service name
//	inputInfo: giá trị config truyền vào
	public void resetServiceLimitBackend(WebDriver driver, String selectValue, ServiceLimitInfo inputInfo) {

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

	// Setting han muc goi giao dich

	public void Setup_Package_Total_Limit(WebDriver driver, String packageCode, String methodAccuracy, ServiceLimitInfo inputInf) {

		clickToDynamicMenuByLink(driver, "/Package/Index?f=2&c=191");
		selectItemInDropdown(driver, "ng-pristine", "100");
		clickToDynamicIconPackage(driver, packageCode, "Assign Package Total Limit");
		clickToDynamicIconPencil(driver, methodAccuracy, "blue");
		oldValue = getDataInInputByID(driver, "edit-limit-day").replace(",", "");
		inputIntoInputByID(driver, inputInf.minTran, "edit-limit-day");
		clickToDynamicLinkAByID(driver, "update-servicetype");
		acceptAlert(driver);
	}

	// Reset goi han muc goi giao dic
	public void Reset_Package_Total_Limit(WebDriver driver, String packageCode, String methodAccuracy) {
		clickToDynamicMenuByLink(driver, "/Package/Index?f=2&c=191");
		selectItemInDropdown(driver, "ng-pristine", "100");
		clickToDynamicIconPackage(driver, packageCode, "Assign Package Total Limit");
		clickToDynamicIconPencil(driver, methodAccuracy, "blue");
		inputIntoInputByID(driver, oldValue, "edit-limit-day");
		clickToDynamicLinkAByID(driver, "update-servicetype");
		acceptAlert(driver);

	}

	public void inputDynamicDataByListIcon(WebDriver driver, String dynamicText) {
		for (ServiceLimitInfo02 inputInfo : getInfoList) {
			clickToDynamicIconByTwoTexts(driver, dynamicText, inputInfo.method, "Edit Service Limit");
			inputIntoInputByID(driver, inputInfo.timesDay, "edit-times-day");
			inputIntoInputByID(driver, inputInfo.minTran, "edit-min-tran");
			inputIntoInputByID(driver, inputInfo.maxTran, "edit-max-tran");
			inputIntoInputByID(driver, inputInfo.totalLimit, "edit-total-limit");
			clickToDynamicButtonATagByID(driver, "edit-limit");
			acceptAlert(driver);
		}
	}

	public List<ServiceLimitInfo02> getAndInputDataByListIcon(WebDriver driver, String dynamicText, ServiceLimitInfo info) {

		List<String> methodList = getDynamicDataByListIcon(driver, dynamicText,"1");
		for (String text : methodList) {
			ServiceLimitInfo02 getInfo = new ServiceLimitInfo02("", "", "", "", "");
			getInfo.method = text.trim();
			clickToDynamicIconByTwoTexts(driver, dynamicText, getInfo.method, "Edit Service Limit");
			getInfo.timesDay = getDataInInputByID(driver, "edit-times-day");
			getInfo.minTran = getDataInInputByID(driver, "edit-min-tran");
			getInfo.maxTran = getDataInInputByID(driver, "edit-max-tran");
			getInfo.totalLimit = getDataInInputByID(driver, "edit-total-limit");
			getInfoList.add(getInfo);
			inputIntoInputByID(driver, info.timesDay, "edit-times-day");
			inputIntoInputByID(driver, info.minTran, "edit-min-tran");
			inputIntoInputByID(driver, info.maxTran, "edit-max-tran");
			inputIntoInputByID(driver, info.totalLimit, "edit-total-limit");
			clickToDynamicButtonATagByID(driver, "edit-limit");
			acceptAlert(driver);
		}
		
		return getInfoList;
	}
	
	public void addMethodOtpLimit(WebDriver driver,String dynamicText) {
		
		clickToDynamicMenuByLink(driver, "MethodOtpLimit/Index?f=2&c=239");
		selectItemInDropdownByID(driver, "service",dynamicText);
		clickToDynamicButtonATagByClass(driver, "btn btn-primary");
		List<String> actual = getDynamicDataByListIcon(driver, dynamicText,"2");
		clickToDynamicButtonATagByID(driver, "btn-create-billprovider");
		
	}

}