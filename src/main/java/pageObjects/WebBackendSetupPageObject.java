package pageObjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebDriver;

import commons.WebAbstractPage;
import model.ServiceLimitInfo;
import model.ServiceTypeLimitInfo;
import vietcombankUI.DynamicWebPageUIs;

public class WebBackendSetupPageObject extends WebAbstractPage {

	private WebDriver driver;
	List<String> listExpect;
	List<String> listActual;

	WebAbstractPage test = new WebAbstractPage();
	int longTime = 40;

	public WebBackendSetupPageObject(WebDriver mappingDriver) {
		driver = mappingDriver;
	}

	public static ServiceLimitInfo getInfo = new ServiceLimitInfo(null, null, null, null);
	public static ServiceTypeLimitInfo getInfoType = new ServiceTypeLimitInfo(null, null, null);

	public void Setup_Assign_Services_Limit(WebDriver driver, String selectValue, ServiceLimitInfo inputInfo) {
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

	public void reset_Setup_Assign_Services_Limit(WebDriver driver, String selectValue, ServiceLimitInfo inputInfo) {
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

	public void Login_Web_Backend(WebDriver driver, String username, String passWeb) {
		inputIntoInputByID(driver, username, "login-username");
		inputIntoInputByID(driver, passWeb, "login-password");
		clickToDynamicButtonByID(driver, "btn-login");
	}

	public void Setup_Assign_Services_Type_Limit(WebDriver driver, String servicesName, ServiceTypeLimitInfo inputInfoType) {
		clickToDynamicMenuByLink(driver, "/Package/Index?f=2&c=191");
		selectItemInDropdown(driver, "ng-pristine", "100");
		clickToDynamicIconByText(driver, "PKG1", "Assign Service Type Limit");
		clickToDynamicSelectModel(driver, "PerPageItems");

		// Lay list phuong thuc xac thuc
		List<String> listActualMethod = new ArrayList<String>();
		listActualMethod = getTextInListElements(driver, DynamicWebPageUIs.DYNAMIC_TD_FOLLOWING_INDEX, "ng-scope", servicesName + "\n", "1");
		listActualMethod.remove("Vân tay");
		listActualMethod.remove("Smart OTP");

		// Setup hạn mức cho nhóm dịch vụ cho những nhóm đã có sẵn
		for (int i = 0; i <= listActualMethod.size(); i++) {
			clickToDynamicIconByTwoTexts(driver, servicesName + "\n", listActualMethod.get(i), "Edit Service Type Limit");
			getInfoType.methodOTP = getDataSelectText(driver, "edit-method-otp");
			getInfoType.currentcy = getDataSelectText(driver, "edit-ccy");
			getInfoType.totalLimit = getDataInInputByID(driver, "edit-limit-day");
			inputIntoInputByID(driver, inputInfoType.totalLimit, "edit-limit-day");
			clickToDynamicButtonATagByID(driver, "update-servicetype");
			acceptAlert(driver);
			break;
		}
		listExpect = new ArrayList<String>();
		listExpect.add("Soft OTP");
		listExpect.add("All");
		listExpect.add("SMS OTP");
		listExpect.add("PIN");
		listExpect.removeAll(listActualMethod);

		// Tạo mới nhóm dịch vụ với PTXT chưa có trong list
		for (int i = 0; i <= listExpect.size(); i++) {
			clickToDynamicNgClick(driver, "addServiceType()");
			clickToDynamicSelectID(driver, "service-type");
			clickToDynamicOptionText(driver, servicesName);
			clickToDynamicSelectID(driver, "method-otp");
			clickToDynamicOptionText(driver, listExpect.get(i));
			inputIntoInputByID(driver, inputInfoType.totalLimit, "limit-day");
			clickToDynamicButtonATagByID(driver, "create-servicetypelimit");
			acceptAlert(driver);
			listExpect.remove(listExpect.get(i));
			break;
		}

	}

	public void reset_Setup_Assign_Services_Type_Limit(WebDriver driver, String selectValue, ServiceLimitInfo inputInfo) {

	}

	public void Setup_Package_Total_Limit() {

	}

}