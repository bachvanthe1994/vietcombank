package pageObjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.WebDriver;

import com.google.common.collect.Lists;

import commons.Constants;
import commons.WebAbstractPage;
import model.ServiceLimitInfo;
import model.ServiceLimitInfo02;
import model.ServiceTypeLimitInfo;
import vietcombankUI.DynamicWebPageUIs;


public class WebBackendSetupPageObject extends WebAbstractPage {


	List<String> listExpect = new ArrayList<String>();
	List<String> listActual;
	public static ServiceLimitInfo getInfo = new ServiceLimitInfo("", "", "", "");
	public List<ServiceLimitInfo02> getInfoList = new ArrayList<ServiceLimitInfo02>();
	public static ServiceTypeLimitInfo getInfoType = new ServiceTypeLimitInfo(null, null, null);
	public static String oldValue = "";

	public void Login_Web_Backend(WebDriver driver, String username, String passWeb) {
		inputIntoInputByID(driver, username, "login-username");
		inputIntoInputByID(driver, passWeb, "login-password");
		clickToDynamicButtonByID(driver, "btn-login");
	}
	

//	setup limit min max cho lần giao dịch
//	selectValue: service name
//	inputInfo: giá trị config truyền vào
	public void setupAssignServicesLimit(WebDriver driver, String dynamicText, ServiceLimitInfo info) {
		addMethodOtpLimit(driver, dynamicText);
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


	public void Setup_Assign_Services_Type_Limit(WebDriver driver, String servicesName, ServiceTypeLimitInfo inputInfoType) {
		clickToDynamicMenuByLink(driver, "/Package/Index?f=2&c=191");
		selectItemInDropdown(driver, "ng-pristine", "100");
		clickToDynamicIconByText(driver, "PKG1", "Assign Service Type Limit");
		clickToDynamicSelectModel(driver, "PerPageItems");
		clickToDynamicOptionText(driver, "100");

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

		List<String> listExpect = Lists.newArrayList("All", "Soft OTP", "PIN", "SMS OTP");
		listExpect.removeAll(listActualMethod);
	
		// Tạo mới nhóm dịch vụ với PTXT chưa có trong list
		for (int i = 0; i < listExpect.size(); i++) {
			clickToDynamicNgClick(driver, "addServiceType()");
			clickToDynamicSelectID(driver, "service-type");
			clickToDynamicOptionText(driver, servicesName);
			clickToDynamicSelectID(driver, "method-otp");
			clickToDynamicOptionText(driver, listExpect.get(0));
			inputIntoInputByID(driver, inputInfoType.totalLimit, "limit-day");
			clickToDynamicButtonATagByID(driver, "create-servicetypelimit");
			acceptAlert(driver);
			listExpect.remove(listExpect.get(0));
			break;
		}
	}

	public void Setup_Add_Method_Package_Total_Limit(WebDriver driver, String packageCode, String tittleTableValue) {
		clickToDynamicMenuByLink(driver, "/Package/Index?f=2&c=191");
		selectItemInDropdown(driver, "ng-pristine", "100");
		clickToDynamicIconPackage(driver, packageCode, "Assign Package Total Limit");
		List<String> listActualMethod = getListMetodOtp(driver, tittleTableValue);
		for (String valueMethods : listActualMethod) {
			clickToDynamicIconPencil(driver, valueMethods, "blue");
			inputIntoInputByID(driver, Constants.AMOUNT_DEFAULT_MIN_PACKAGE, "edit-limit-day");
			clickToDynamicLinkAByID(driver, "update-servicetype");
			acceptAlert(driver);
		}
		List<String> listMethodExpert = Lists.newArrayList("All", "Soft OTP", "PIN", "SMS OTP");
		listMethodExpert.remove("Smart OTP");
		listMethodExpert.remove("Vân tay");
		listMethodExpert.removeAll(listActualMethod);

		for (String methods : listMethodExpert) {
			clickToDynamicButtonATagByClass(driver, "btn btn-primary");
			selectItemInDropdown(driver, "form-control", methods);
			inputIntoInputByID(driver, Constants.AMOUNT_DEFAULT_MIN_PACKAGE, "limit-day");
			clickToDynamicButtonATagByID(driver, "create-servicetypelimit");
			acceptAlert(driver);
		}

	}

	// Reset goi han muc goi giao dic
		public void Reset_Package_Total_Limit(WebDriver driver, String packageCode, String tittleTableValue) {
			List<String> listActualMethod = getListMetodOtp(driver, tittleTableValue);
			for (String valueMethods : listActualMethod) {
				clickToDynamicIconPencil(driver, valueMethods, "blue");
				inputIntoInputByID(driver, Constants.AMOUNT_DEFAULT_MAX_PACKAGE, "edit-limit-day");
				clickToDynamicLinkAByID(driver, "update-servicetype");
				acceptAlert(driver);
			}
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

		List<String> methodList = getDynamicDataByListIcon(driver, dynamicText, "1");
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

	public void addMethodOtpLimit(WebDriver driver, String dynamicText) {

		clickToDynamicMenuByLink(driver, "MethodOtpLimit/Index?f=2&c=239");
		selectItemInDropdownByID(driver, "service", dynamicText);
		clickToDynamicButtonATagByClass(driver, "btn btn-primary");

		List<String> actual = getDynamicDataByListIcon(driver, dynamicText,"2");
		actual.remove("Vân tay");
		actual.remove("Smart OTP");
		if(actual.size() > 0) {
			for(String a : actual) {
				clickToDynamicIconByThreeTexts(driver, dynamicText,"Việt Nam Đồng",a,"Edit");
				inputIntoInputByID(driver, Constants.THREE_BILLION_VND, "limit");
				clickToDynamicButtonATagByID(driver, "create");
				acceptAlert(driver);
				clickToDynamicMenuByLink(driver, "MethodOtpLimit/Index?f=2&c=239");
				selectItemInDropdownByID(driver, "service",dynamicText);
				clickToDynamicButtonATagByClass(driver, "btn btn-primary");
			}
		}
		List<String> listMethodExpect = Lists.newArrayList("All", "Soft OTP", "PIN", "SMS OTP");
		listMethodExpect.removeAll(actual);
		if(listExpect.size() > 0) {
			clickToDynamicButtonATagByID(driver, "btn-create-billprovider");
			for(String a :listExpect) {
				inputIntoInputByID(driver, randomNumber()+"", "code");
				selectItemInDropdownByID(driver, "service", dynamicText);
				selectItemInDropdownByID(driver, "ccy", Constants.VND_CURRENCY);
				selectItemInDropdownByID(driver, "method-otp", a);
				inputIntoInputByID(driver, Constants.THREE_BILLION_VND, "limit");
				clickToDynamicButtonATagByID(driver, "create");
				acceptAlert(driver);
			}
		}
		

	}

	public static int randomNumber() {

		Random random = new Random();
		int randomNumber = random.nextInt(999);
		return randomNumber;

	}
}