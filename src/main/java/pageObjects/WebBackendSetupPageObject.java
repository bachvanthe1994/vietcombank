package pageObjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.WebDriver;

import com.google.common.collect.Lists;

import commons.Constants;
import commons.WebAbstractPage;
import model.Assign_Package_Total_Limit;
import model.ServiceLimitInfo;
import model.ServiceLimitInfo02;
import model.ServiceTypeLimitInfo;
import vietcombankUI.DynamicWebPageUIs;

public class WebBackendSetupPageObject extends WebAbstractPage {
	List<String> listExpect = new ArrayList<String>();
	List<String> listActual;
	
	List<String> listExpectFull = Lists.newArrayList("All", "Soft OTP", "PIN", "SMS OTP");
	public static ServiceLimitInfo getInfo = new ServiceLimitInfo("", "", "", "");
	public List<ServiceLimitInfo02> getInfoList = new ArrayList<ServiceLimitInfo02>();
	public static ServiceTypeLimitInfo getInfoType = new ServiceTypeLimitInfo(null, null, null);
	public static String oldValue = "";
	public List<Assign_Package_Total_Limit> listAssign = new ArrayList<Assign_Package_Total_Limit>();


	public void Login_Web_Backend(WebDriver driver, String username, String passWeb) {
		inputIntoInputByID(driver, username, "login-username");
		inputIntoInputByID(driver, passWeb, "login-password");
		clickToDynamicButtonByID(driver, "btn-login");
	}

//	setup limit min max cho lần giao dịch
//	selectValue: service name
//	inputInfo: giá trị config truyền vào
	public void setupAssignServicesLimit(WebDriver driver, String serviceName, ServiceLimitInfo inputInfo) {
//		addMethodOtpLimit(driver, serviceName);
		addMethodServicesLimit(driver, serviceName, inputInfo);
		openAssignServiceLimit(driver);
		getInfoList = getAndInputDataByListIcon(driver, serviceName, inputInfo);
		driver.quit();
	}

	public void resetAssignServicesLimit(WebDriver driver, String serviceName) {
		openAssignServiceLimit(driver);
		inputDynamicDataByListIcon(driver, serviceName);
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
		for (String list : listActualMethod) {
			clickToDynamicIconByTwoTexts(driver, servicesName + "\n", list, "Edit Service Type Limit");
			getInfoType.methodOTP = getDataSelectText(driver, "edit-method-otp");
			getInfoType.currentcy = getDataSelectText(driver, "edit-ccy");
			getInfoType.totalLimit = getDataInInputByID(driver, "edit-limit-day");
			inputIntoInputByID(driver, inputInfoType.totalLimit, "edit-limit-day");
			clickToDynamicButtonATagByID(driver, "update-servicetype");
			acceptAlert(driver);

		}

		List<String> listExpect = Lists.newArrayList("All", "Soft OTP", "PIN", "SMS OTP");
		listExpect.removeAll(listActualMethod);

		// Tạo mới nhóm dịch vụ với PTXT chưa có trong list
		for (String service : listExpect) {

			clickToDynamicNgClick(driver, "addServiceType()");
			clickToDynamicSelectID(driver, "service-type");
			clickToDynamicOptionText(driver, servicesName);
			clickToDynamicSelectID(driver, "method-otp");
			clickToDynamicOptionText(driver, service);
			inputIntoInputByID(driver, inputInfoType.totalLimit, "limit-day");
			clickToDynamicButtonATagByID(driver, "create-servicetypelimit");
			acceptAlert(driver);

		}
	}

	//
	public void Setup_Add_Method_Package_Total_Limit(WebDriver driver, String packageCode, String tittleTableValue) {
		clickToDynamicMenuByLink(driver, "/Package/Index?f=2&c=191");
		selectItemInDropdown(driver, "ng-pristine", "100");
		clickToDynamicIconPackage(driver, packageCode, "Assign Package Total Limit");
		List<String> listActualMethod = getListMetodOtp(driver, tittleTableValue);		

		for (String valueMethods : listActualMethod) {
			Assign_Package_Total_Limit assign = new Assign_Package_Total_Limit("", "");
			assign.total_limit= getDynamicDataByListIcon(driver, valueMethods,"2").get(0);
			assign.method_Otp = valueMethods;
			listAssign.add(assign);
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

	// Reset goi han muc goi giao dich
	public void Reset_Package_Total_Limit(WebDriver driver, String packageCode, String tittleTableValue) {
		List<String> listActualMethod = getListMetodOtp(driver, tittleTableValue);
		for (String valueMethods : listActualMethod) {
			clickToDynamicIconPencil(driver, valueMethods, "blue");
			inputIntoInputByID(driver, Constants.AMOUNT_DEFAULT_MAX_PACKAGE, "edit-limit-day");
			clickToDynamicLinkAByID(driver, "update-servicetype");
			acceptAlert(driver);
			}
	}

	// Nhap lai data cu vao phan Assign Service Limit
	public void inputDynamicDataByListIcon(WebDriver driver, String serviceName) {
		for (ServiceLimitInfo02 inputInfo : getInfoList) {
			clickToDynamicIconByTwoTexts(driver, serviceName, inputInfo.method, "Edit Service Limit");
			inputIntoInputByID(driver, inputInfo.timesDay, "edit-times-day");
			inputIntoInputByID(driver, inputInfo.minTran, "edit-min-tran");
			inputIntoInputByID(driver, inputInfo.maxTran, "edit-max-tran");
			inputIntoInputByID(driver, inputInfo.totalLimit, "edit-total-limit");
			clickToDynamicButtonATagByID(driver, "edit-limit");
			acceptAlert(driver);
		}
	}

	//Lay data cu va thay doi thanh data moi phan Assign Service Limit
	public List<ServiceLimitInfo02> getAndInputDataByListIcon(WebDriver driver, String serviceName, ServiceLimitInfo inputInfo) {

		List<String> methodList = getDynamicDataByListIcon(driver, serviceName, "1");
		for (String text : methodList) {
			ServiceLimitInfo02 getInfo = new ServiceLimitInfo02("", "", "", "", "");
			getInfo.method = text.trim();
			clickToDynamicIconByTwoTexts(driver, serviceName, getInfo.method, "Edit Service Limit");
			getInfo.timesDay = getDataInInputByID(driver, "edit-times-day");
			getInfo.minTran = getDataInInputByID(driver, "edit-min-tran");
			getInfo.maxTran = getDataInInputByID(driver, "edit-max-tran");
			getInfo.totalLimit = getDataInInputByID(driver, "edit-total-limit");
			getInfoList.add(getInfo);
			inputDataToServiceLimit(driver, inputInfo);
		}

		return getInfoList;
	}

	//Them method OTP vap phan Method Otp Limit
	public void addMethodOtpLimit(WebDriver driver, String serviceName) {

		clickToDynamicMenuByLink(driver, "MethodOtpLimit/Index?f=2&c=239");
		selectItemInDropdownByID(driver, "service", serviceName);
		clickToDynamicButtonATagByClass(driver, "btn btn-primary");

		List<String> actualMethodList = getDynamicDataByListIcon(driver, serviceName, "2");
		actualMethodList.remove("Vân tay");
		actualMethodList.remove("Smart OTP");
		if (actualMethodList.size() > 0) {
			for (String methodOtp : actualMethodList) {
				clickToDynamicIconByThreeTexts(driver, serviceName, "Việt Nam Đồng", methodOtp, "Edit");
				inputIntoInputByID(driver, Constants.THREE_BILLION_VND, "limit");
				clickToDynamicButtonATagByID(driver, "create");
				acceptAlert(driver);
				clickToDynamicMenuByLink(driver, "MethodOtpLimit/Index?f=2&c=239");
				selectItemInDropdownByID(driver, "service", serviceName);
				clickToDynamicButtonATagByClass(driver, "btn btn-primary");
			}
		}
		List<String> listMethodExpect = Lists.newArrayList("All", "Soft OTP", "PIN", "SMS OTP");
		listMethodExpect.removeAll(actualMethodList);
		if (listMethodExpect.size() > 0) {
			clickToDynamicButtonATagByID(driver, "btn-create-billprovider");
			for (String methodOtp : listMethodExpect) {
				inputIntoInputByID(driver, randomNumber() + "", "code");
				selectItemInDropdownByID(driver, "service", serviceName);
				selectItemInDropdownByID(driver, "ccy", Constants.VND_CURRENCY);
				selectItemInDropdownByID(driver, "method-otp", methodOtp);
				inputIntoInputByID(driver, Constants.THREE_BILLION_VND, "limit");
				clickToDynamicButtonATagByID(driver, "create");
				acceptAlert(driver);
			}
		}

	}
	
	//Them Method OTP vao phan Service Limit
	public void addMethodServicesLimit(WebDriver driver,String serviceName, ServiceLimitInfo inputInfo) {
		
		openAssignServiceLimit(driver);
		List<String> actualMethodList = getDynamicDataByListIcon(driver, serviceName, "1");
		actualMethodList.remove("Vân tay");
		actualMethodList.remove("Smart OTP");
		List<String> listMethodExpect = Lists.newArrayList("All", "Soft OTP", "PIN", "SMS OTP");
		listMethodExpect.removeAll(actualMethodList);
		if(listMethodExpect.size() > 0) {
			for(String methodOtp:listMethodExpect) {
				clickToDynamicButtonATagByClass(driver, "btn btn-primary");
				selectItemInDropdownByID(driver, "service", serviceName);
				selectItemInDropdownByID(driver, "method-otp", methodOtp);
				selectItemInDropdownByID(driver, "ccy", "Việt Nam Đồng");
				inputIntoInputByID(driver, inputInfo.timesDay, "times-day");
				inputIntoInputByID(driver, inputInfo.minTran, "min-tran");
				inputIntoInputByID(driver, inputInfo.maxTran, "max-tran");
				inputIntoInputByID(driver, inputInfo.totalLimit, "total-limit");
				clickToDynamicButtonATagByID(driver, "create-limit");
				acceptAlert(driver);
				
			}
		}
		
	}

	//Mo phan Assign Service Limit
	public void openAssignServiceLimit(WebDriver driver) {
		clickToDynamicMenuByLink(driver, "/Package/Index?f=2&c=191");
		selectItemInDropdown(driver, "ng-pristine", "100");
		clickToDynamicIconByText(driver, "PKG1", "Assign Service Limit");
		selectItemInDropdown(driver, "ng-pristine", "100");
	}
	
	// Nhap data vao truong Edit Assign Service Limit
	public void inputDataToServiceLimit(WebDriver driver, ServiceLimitInfo inputInfo) {
		
		inputIntoInputByID(driver, inputInfo.timesDay, "edit-times-day");
		inputIntoInputByID(driver, inputInfo.minTran, "edit-min-tran");
		inputIntoInputByID(driver, inputInfo.maxTran, "edit-max-tran");
		inputIntoInputByID(driver, inputInfo.totalLimit, "edit-total-limit");
		clickToDynamicButtonATagByID(driver, "edit-limit");
		acceptAlert(driver);
	}
	
	public static int randomNumber() {

		Random random = new Random();
		int randomNumber = random.nextInt(999);
		return randomNumber;

	}
}