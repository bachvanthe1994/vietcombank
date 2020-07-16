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
	public List<ServiceLimitInfo02> getInfoList1 = new ArrayList<ServiceLimitInfo02>();
	public List<ServiceLimitInfo02> getInfoList_All = new ArrayList<ServiceLimitInfo02>();

	public List<ServiceLimitInfo02> getInfoList_totalDay = new ArrayList<ServiceLimitInfo02>();

	public static List<ServiceTypeLimitInfo> getInfoType = new ArrayList<ServiceTypeLimitInfo>();
	public static List<ServiceLimitInfo02> serviceLimitInfoList = new ArrayList<ServiceLimitInfo02>();

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

	public void setupAssignServicesLimit_Total_Day(WebDriver driver, String dynamicText, ServiceLimitInfo info, String codePackage) {
		clickToDynamicMenuByLink(driver, "/Package/Index?f=2&c=191");
		selectItemInDropdown(driver, "ng-pristine", "100");
		clickToDynamicIconByText(driver, codePackage, "Assign Service Limit");
		selectItemInDropdown(driver, "ng-pristine", "100");
		getInfoList = getAndInputDataByListIcon_Total_LimitDay(driver, dynamicText, info);
	}


	public void setupAssignServicesLimit(WebDriver driverWeb, String serviceName, ServiceLimitInfo inputInfo,String codePackage) {
		//addMethodOtpLimit(driverWeb, serviceName);
		addMethodServicesLimit(driverWeb, serviceName, inputInfo, codePackage);
		openAssignServiceLimit(driverWeb, codePackage);
		getInfoList = getAndInputDataByListIcon(driverWeb, serviceName, inputInfo);
	}

	public void resetAssignServicesLimit(WebDriver driver, String serviceName, String codePackage) {
		openAssignServiceLimit(driver,codePackage);
		inputDynamicDataByListIcon(driver, serviceName);
	}

	public void Setup_Assign_Services_Type_Limit(WebDriver driver,String codePackage, String servicesName, String valueLimit) {
		clickToDynamicMenuByLink(driver, "/Package/Index?f=2&c=191");
		selectItemInDropdown(driver, "ng-pristine", "100");
		clickToDynamicIconByText(driver, codePackage, "Assign Service Type Limit");
		clickToDynamicSelectModel(driver, "PerPageItems");
		clickToDynamicOptionText(driver, "100");
		// Lay list phuong thuc xac thuc
		List<String> listActualMethod = new ArrayList<String>();
		listActualMethod = getTextInListElements(driver, DynamicWebPageUIs.DYNAMIC_TD_FOLLOWING_INDEX, "ng-scope", servicesName + "\n", "1");
		listActualMethod.remove("Vân tay");
		listActualMethod.remove("Smart OTP");

		// Setup hạn mức cho nhóm dịch vụ cho những nhóm đã có sẵn
		for (String list : listActualMethod) {
			ServiceTypeLimitInfo serviceType = new ServiceTypeLimitInfo("", "", "");
			serviceType.methodOTP = list;
			clickToDynamicIconByTwoTexts(driver, servicesName + "\n", list, "Edit Service Type Limit");
			serviceType.currentcy = getDataSelectText(driver, "edit-ccy");
			serviceType.totalLimit = getDataInInputByID(driver, "edit-limit-day");
			getInfoType.add(serviceType);
			inputIntoInputByID(driver, valueLimit, "edit-limit-day");
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
			inputIntoInputByID(driver, valueLimit, "limit-day");
			clickToDynamicButtonATagByID(driver, "create-servicetypelimit");
			acceptAlert(driver);

		}
	}

	
	public void Reset_Setup_Assign_Services_Type_Limit(WebDriver driver, String codePackage, String servicesName) {
		clickToDynamicMenuByLink(driver, "/Package/Index?f=2&c=191");
		selectItemInDropdown(driver, "ng-pristine", "100");
		clickToDynamicIconByText(driver, codePackage, "Assign Service Type Limit");
		clickToDynamicSelectModel(driver, "PerPageItems");
		clickToDynamicOptionText(driver, "100");
		for (ServiceTypeLimitInfo serviceType : getInfoType) {
			clickToDynamicIconByTwoTexts(driver, servicesName + "\n", serviceType.methodOTP, "Edit Service Type Limit");
			inputIntoInputByID(driver, Constants.THREE_BILLION_VND, "edit-limit-day");
			clickToDynamicLinkAByID(driver, "update-servicetype");
			acceptAlert(driver);
		}
	}
	

	public void Setup_Assign_Services_Limit(WebDriver driver,String codePackage, String servicesName, String minLimitTrans, String maxLimitTrans, String totalLimit) {
		clickToDynamicMenuByLink(driver, "/Package/Index?f=2&c=191");
		selectItemInDropdown(driver, "ng-pristine", "100");
		clickToDynamicIconByText(driver, codePackage, "Assign Service Limit");
		clickToDynamicSelectModel(driver, "PerPageItems");
		clickToDynamicOptionText(driver, "100");
		// Lay list phuong thuc xac thuc
		List<String> listActualMethod = new ArrayList<String>();
		listActualMethod = getTextInListElements(driver, DynamicWebPageUIs.DYNAMIC_TD_FOLLOWING_INDEX, "ng-scope", servicesName + "\n", "1");
		listActualMethod.remove("Vân tay");
		listActualMethod.remove("Smart OTP");

		// Setup hạn mức cho nhóm dịch vụ cho những nhóm đã có sẵn
		for (String list : listActualMethod) {
			ServiceLimitInfo02 service = new ServiceLimitInfo02("", "", "", "", "");
			clickToDynamicIconByTwoTexts(driver, servicesName + "\n", list, "Edit Service Limit");
			service.method = list;
			service.timesDay = getDataInInputByID(driver, "edit-times-day");
			service.minTran = getDataInInputByID(driver, "edit-min-tran");
			service.maxTran = getDataInInputByID(driver, "edit-max-tran");
			service.totalLimit = getDataInInputByID(driver, "edit-total-limit");
			
			serviceLimitInfoList.add(service);
			inputIntoInputByID(driver, "1000", "edit-times-day");
			inputIntoInputByID(driver, minLimitTrans, "edit-min-tran");
			inputIntoInputByID(driver, maxLimitTrans, "edit-max-tran");
			inputIntoInputByID(driver, totalLimit, "edit-total-limit");
			clickToDynamicButtonATagByID(driver, "edit-limit");
			acceptAlert(driver);
		}
		List<String> listExpect = Lists.newArrayList("All", "Soft OTP", "PIN", "SMS OTP");
		listExpect.removeAll(listActualMethod);

		// Tạo mới nhóm dịch vụ với PTXT chưa có trong list
		for (String service : listExpect) {
			clickToDynamicNgClick(driver, "addServiceLimit()");
			clickToDynamicSelectID(driver, "service");
			clickToDynamicOptionText(driver, servicesName);
			clickToDynamicSelectID(driver, "method-otp");
			clickToDynamicOptionText(driver, service);
			inputIntoInputByID(driver, "1000", "times-day");
			inputIntoInputByID(driver, minLimitTrans, "min-tran");
			inputIntoInputByID(driver, maxLimitTrans, "max-tran");
			inputIntoInputByID(driver, totalLimit, "total-limit");
			clickToDynamicButtonATagByID(driver, "create-limit");
			acceptAlert(driver);

		}
	}

	
	public void Reset_Setup_Assign_Services_Limit(WebDriver driver, String codePackage, String servicesName) {
		clickToDynamicMenuByLink(driver, "/Package/Index?f=2&c=191");
		selectItemInDropdown(driver, "ng-pristine", "100");
		clickToDynamicIconByText(driver, codePackage, "Assign Service Limit");
		clickToDynamicSelectModel(driver, "PerPageItems");
		clickToDynamicOptionText(driver, "100");
		for (ServiceLimitInfo02 service : serviceLimitInfoList) {
			clickToDynamicIconByTwoTexts(driver, servicesName + "\n", service.method, "Edit Service Limit");
			inputIntoInputByID(driver, "1000", "edit-times-day");
			inputIntoInputByID(driver, Constants.MIN_TRANSFER, "edit-min-tran");
			inputIntoInputByID(driver, "1000000000", "edit-max-tran");
			inputIntoInputByID(driver, "1000000000", "edit-total-limit");
			clickToDynamicButtonATagByID(driver, "edit-limit");
			acceptAlert(driver);
		}
	}

	//Set limit value of Package 
	public void Setup_Add_Method_Package_Total_Limit(WebDriver driverWeb, String packageCode, String tittleTableValue,String amountLimit) {
		
		clickToDynamicMenuByLink(driverWeb, "/Package/Index?f=2&c=191");
		selectItemInDropdown(driverWeb, "ng-pristine", "100");
		clickToDynamicIconPackage(driverWeb, packageCode, "Assign Package Total Limit");
		List<String> listActualMethods = getListMetodOtp(driverWeb, tittleTableValue);

		for (String valueMethods : listActualMethods) {
			Assign_Package_Total_Limit assign = new Assign_Package_Total_Limit("", "");
			assign.total_limit = getDynamicDataByMethod(driverWeb, valueMethods, "2");
			assign.method_Otp = valueMethods;
			listAssign.add(assign);
			clickToDynamicIconPencil(driverWeb, valueMethods, "blue");
			inputIntoInputByID(driverWeb, amountLimit, "edit-limit-day");
			clickToDynamicLinkAByID(driverWeb, "update-servicetype");
			acceptAlert(driverWeb);
		}
		List<String> listMethodExpert = Lists.newArrayList("All", "Soft OTP", "PIN", "SMS OTP");
		listMethodExpert.remove("Smart OTP");
		listMethodExpert.remove("Vân tay");
		listMethodExpert.removeAll(listActualMethods);

		for (String methods : listMethodExpert) {
			clickToDynamicButtonATagByClass(driverWeb, "btn btn-primary");
			selectItemInDropdown(driverWeb, "form-control", methods);
			inputIntoInputByID(driverWeb, amountLimit, "limit-day");
			clickToDynamicButtonATagByID(driverWeb, "create-servicetypelimit");
			acceptAlert(driverWeb);
		}

	}

	// Reset goi han muc goi giao dich
	public void Reset_Package_Total_Limit(WebDriver driver, String packageCode, String tittleTableValue) {
		
		clickToDynamicMenuByLink(driver, "/Package/Index?f=2&c=191");
		selectItemInDropdown(driver, "ng-pristine", "100");
		clickToDynamicIconPackage(driver, packageCode, "Assign Package Total Limit");
		for (Assign_Package_Total_Limit assign : listAssign) {
			clickToDynamicIconPencil(driver, assign.method_Otp, "blue");
			inputIntoInputByID(driver, Constants.THREE_BILLION_VND, "edit-limit-day");
			clickToDynamicLinkAByID(driver, "update-servicetype");
			acceptAlert(driver);
		}
	}

	// Nhap lai data cu vao phan Assign Service Limit
	public void inputDynamicDataByListIcon(WebDriver driver, String serviceName) {
		for (ServiceLimitInfo02 inputInfo : getInfoList) {
			clickToDynamicIconByTwoTexts(driver, serviceName, inputInfo.method, "Edit Service Limit");
			inputIntoInputByID(driver, Constants.MIN_TRANSFER, "edit-min-tran");
			inputIntoInputByID(driver, Constants.THREE_BILLION_VND, "edit-max-tran");
			inputIntoInputByID(driver, Constants.THREE_BILLION_VND, "edit-total-limit");
			clickToDynamicButtonATagByID(driver, "edit-limit");
			acceptAlert(driver);
		}
	}


	// Lay data cu va thay doi thanh data moi phan Assign Service Limit
	public List<ServiceLimitInfo02> getAndInputDataByListIcon(WebDriver driver, String serviceName, ServiceLimitInfo inputInfo) {

		List<String> methodList = getDynamicDataByListIcon(driver, serviceName, "1");
		for (String text : methodList) {
			ServiceLimitInfo02 getInfo = new ServiceLimitInfo02("", "", "", "", "");
			getInfo.method = text.trim();
			clickToDynamicIconByTwoTexts(driver, serviceName, getInfo.method, "Edit Service Limit");
			getInfo.minTran = getDataInInputByID(driver, "edit-min-tran");
			getInfo.maxTran = getDataInInputByID(driver, "edit-max-tran");
			getInfo.totalLimit = getDataInInputByID(driver, "edit-total-limit");
			getInfoList.add(getInfo);

			inputIntoInputByID(driver, inputInfo.minTran, "edit-min-tran");
			inputIntoInputByID(driver, inputInfo.maxTran, "edit-max-tran");
			clickToDynamicButtonATagByID(driver, "edit-limit");
			acceptAlert(driver);

		}

		return getInfoList;
	}
	public List<ServiceLimitInfo02> getAndInputDataByListIcon1(WebDriver driver, String serviceName, ServiceLimitInfo inputInfo) {
		
		List<String> methodList = getDynamicDataByListIcon(driver, serviceName, "1");
		for (String text : methodList) {
			ServiceLimitInfo02 getInfo = new ServiceLimitInfo02("", "", "", "", "");
			getInfo.method = text.trim();
			clickToDynamicIconByTwoTexts(driver, serviceName, getInfo.method, "Edit Service Limit");
			getInfo.minTran = getDataInInputByID(driver, "edit-min-tran");
			getInfo.maxTran = getDataInInputByID(driver, "edit-max-tran");
			getInfo.totalLimit = getDataInInputByID(driver, "edit-total-limit");
			getInfoList1.add(getInfo);
			
			inputIntoInputByID(driver, inputInfo.minTran, "edit-min-tran");
			inputIntoInputByID(driver, inputInfo.maxTran, "edit-max-tran");
			clickToDynamicButtonATagByID(driver, "edit-limit");
			acceptAlert(driver);
			
		}
		
		return getInfoList1;
	}

	public List<ServiceLimitInfo02> getAndInputDataByListIcon_Total_LimitDay(WebDriver driver, String dynamicText, ServiceLimitInfo info) {
		List<String> methodList = getDynamicDataByListIcon(driver, dynamicText, "1");
		for (String text : methodList) {
			ServiceLimitInfo02 getInfo1 = new ServiceLimitInfo02("", "", "", "", "");
			getInfo1.method = text.trim();
			clickToDynamicIconByTwoTexts(driver, dynamicText, getInfo1.method, "Edit Service Limit");
			getInfo1.totalLimit = getDataInInputByID(driver, "edit-total-limit");
			getInfoList_totalDay.add(getInfo1);
			inputIntoInputByID(driver, info.totalLimit, "edit-total-limit");
			clickToDynamicButtonATagByID(driver, "edit-limit");
			acceptAlert(driver);
		}

		return getInfoList;
	}

	// Them method OTP vap phan Method Otp Limit
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

	// Them Method OTP vao phan Service Limit
	public void addMethodServicesLimit(WebDriver driver, String serviceName, ServiceLimitInfo inputInfo,String codePackage) {

		openAssignServiceLimit(driver, codePackage);
		List<String> actualMethodList = getDynamicDataByListIcon(driver, serviceName, "1");
		actualMethodList.remove("Vân tay");
		actualMethodList.remove("Smart OTP");
		List<String> listMethodExpect = Lists.newArrayList("All", "Soft OTP", "PIN", "SMS OTP");
		listMethodExpect.removeAll(actualMethodList);
		if (listMethodExpect.size() > 0) {
			for (String methodOtp : listMethodExpect) {
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

	// Mo phan Assign Service Limit
	public void openAssignServiceLimit(WebDriver driver, String codePackage) {
		clickToDynamicMenuByLink(driver, "/Package/Index?f=2&c=191");
		selectItemInDropdown(driver, "ng-pristine", "100");
		clickToDynamicIconByText(driver, codePackage, "Assign Service Limit");
		clickToDynamicOptionText(driver, "100");
	}

	// Nhap data vao truong Edit Assign Service Limit
	public void inputDataToServiceLimit(WebDriver driver, ServiceLimitInfo inputInfo) {
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

	public void setupAssignServicesLimit_All(WebDriver driver, String serviceName, ServiceLimitInfo inputInfo, String codePackage) {
		openAssignServiceLimit(driver,codePackage);
		getInfoList_All = getAndInputDataByListIcon_All(driver, serviceName, inputInfo);
	}
	
	public void resetAssignServicesLimit_All(WebDriver driver, String serviceName, String codePackage) {
		openAssignServiceLimit(driver,codePackage);
		inputDynamicDataByListIcon_All(driver, serviceName);
	}

	// Lay data cu va thay doi thanh data moi phan Assign Service Limit
	public List<ServiceLimitInfo02> getAndInputDataByListIcon_All(WebDriver driver, String serviceName, ServiceLimitInfo inputInfo) {

		List<String> methodList = getDynamicDataByListIcon(driver, serviceName, "1");
		for (String text : methodList) {
			ServiceLimitInfo02 getInfo = new ServiceLimitInfo02("", "", "", "", "");
			getInfo.method = text.trim();
			clickToDynamicIconByTwoTexts(driver, serviceName, getInfo.method, "Edit Service Limit");
			getInfo.timesDay = getDataInInputByID(driver, "edit-times-day");
			getInfo.minTran = getDataInInputByID(driver, "edit-min-tran");
			getInfo.maxTran = getDataInInputByID(driver, "edit-max-tran");
			getInfo.totalLimit = getDataInInputByID(driver, "edit-total-limit");
			getInfoList_All.add(getInfo);
			inputDataToServiceLimit(driver, inputInfo);

		}

		return getInfoList_All;
	}

	// Nhap lai data cu vao phan Assign Service Limit
	public void inputDynamicDataByListIcon_All(WebDriver driver, String serviceName) {
		for (ServiceLimitInfo02 inputInfo : getInfoList_All) {
			clickToDynamicIconByTwoTexts(driver, serviceName, inputInfo.method, "Edit Service Limit");
			inputIntoInputByID(driver, Constants.MIN_TRANSFER, "edit-min-tran");
			inputIntoInputByID(driver, Constants.THREE_BILLION_VND, "edit-max-tran");
			inputIntoInputByID(driver, Constants.THREE_BILLION_VND, "edit-total-limit");

			clickToDynamicButtonATagByID(driver, "edit-limit");
			acceptAlert(driver);
		}
	}
	public void inputDynamicDataByListIcon_Reset(WebDriver driver, String serviceName,ServiceLimitInfo02 inputInfo ) {
			clickToDynamicIconByTwoTexts(driver, serviceName, inputInfo.method, "Edit Service Limit");
			inputIntoInputByID(driver, inputInfo.minTran, "edit-min-tran");
			inputIntoInputByID(driver, inputInfo.maxTran, "edit-max-tran");
			clickToDynamicButtonATagByID(driver, "edit-limit");
			acceptAlert(driver);
		
	}
	
	public void clearCacheBE(WebDriver driver) {
		
		clickToDynamicMenuByLink(driver, "/Configuration/Index");
		clickToDynamicButtonATagByClass(driver, "btn btn-info");
		acceptAlert(driver);
		waitForAlerVisibleAndClickAccept(driver);
	}
	
	public void addMethod(WebDriver driverWeb, String serviceName, ServiceLimitInfo inputInfo,String codePackage) {
		addMethodOtpLimit(driverWeb, serviceName);
		addMethodServicesLimit(driverWeb, serviceName, inputInfo, codePackage);
	}
	
	public void getInfoServiceLimit(WebDriver driverWeb, String serviceName, ServiceLimitInfo inputInfo,String codePackage) {
		openAssignServiceLimit(driverWeb, codePackage);
		getInfoList = getAndInputDataByListIcon(driverWeb, serviceName, inputInfo);
	}
	public void getInfoServiceLimit1(WebDriver driverWeb, String serviceName, ServiceLimitInfo inputInfo,String codePackage) {
		openAssignServiceLimit(driverWeb, codePackage);
		getInfoList1 = getAndInputDataByListIcon1(driverWeb, serviceName, inputInfo);
	}
}