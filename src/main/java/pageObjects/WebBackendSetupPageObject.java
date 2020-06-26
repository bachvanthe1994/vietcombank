package pageObjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;

import com.google.common.collect.Lists;

import commons.WebAbstractPage;
import model.ServiceLimitInfo;

public class WebBackendSetupPageObject extends WebAbstractPage {

	public WebBackendSetupPageObject(WebDriver driverWeb) {
	}

	public static ServiceLimitInfo getInfo = new ServiceLimitInfo(null, null, null, null);
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

	public void Setup_Add_Method_Package_Total_Limit(WebDriver driver, String packageCode, String tittleTableValue) {
		clickToDynamicMenuByLink(driver, "/Package/Index?f=2&c=191");
		selectItemInDropdown(driver, "ng-pristine", "100");
		clickToDynamicIconPackage(driver, packageCode, "Assign Package Total Limit");
		List<String> listMethodExpert = Lists.newArrayList("All", "Soft OTP", "PIN", "SMS OTP");
		List<String> listActualMethod = getListMetodOtp(driver, tittleTableValue);
		listMethodExpert.remove("Smart OTP");
		listMethodExpert.remove("Vân tay");
		listMethodExpert.removeAll(listActualMethod);
	}

	// Setting han muc goi giao dich
	public void Setup_Package_Total_Limit(WebDriver driver, String packageCode, String methodAccuracy, ServiceLimitInfo inputInf) {
		clickToDynamicMenuByLink(driver, "/Package/Index?f=2&c=191");
		selectItemInDropdown(driver, "ng-pristine", "100");
		clickToDynamicIconPackage(driver, packageCode, "Assign Package Total Limit");
		clickToDynamicIconPencil(driver, methodAccuracy, "blue");
		oldValue = getDataInInputByID(driver, "edit-limit-day");
		inputIntoInputByID(driver, inputInf.minTran, "edit-limit-day");
		clickToDynamicLinkAByID(driver, "update-servicetype");
		acceptAlert(driver);
	}

	// Reset goi han muc goi giao dic
	public void Reset_Package_Total_Limit(WebDriver driver, String packageCode, String methodAccuracy) {
		clickToDynamicIconPencil(driver, methodAccuracy, "blue");
		inputIntoInputByID(driver, oldValue, "edit-limit-day");
		clickToDynamicLinkAByID(driver, "update-servicetype");
		acceptAlert(driver);

	}

}