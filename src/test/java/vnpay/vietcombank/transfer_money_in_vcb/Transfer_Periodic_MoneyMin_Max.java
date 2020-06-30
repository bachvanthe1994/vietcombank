package vnpay.vietcombank.transfer_money_in_vcb;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.Constants;
import commons.PageFactoryManager;
import commons.WebPageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import model.ServiceLimitInfo;
import model.SourceAccountModel;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.TransferMoneyInVcbPageObject;
import pageObjects.WebBackendSetupPageObject;
import vietcombank_test_data.HomePage_Data.Home_Text_Elements;
import vietcombank_test_data.TransferMoneyInVCB_Data;
import vietcombank_test_data.TransferMoneyInVCB_Data.InputDataInVCB;
import vietcombank_test_data.TransferMoneyInVCB_Data.InputData_MoneyRecurrent;
import vietcombank_test_data.TransferMoneyInVCB_Data.InputText_MoneyRecurrent;
import vietcombank_test_data.TransferMoneyInVCB_Data.TittleData;

public class Transfer_Periodic_MoneyMin_Max extends Base {
	AppiumDriver<MobileElement> driver;
	private WebDriver driverWeb;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferMoneyInVcbPageObject transferInVCB;
	private WebBackendSetupPageObject setupBE;

	String[] exchangeRateUSD;
	String today = getCurrentDay() + "/" + getCurrenMonth() + "/" + getCurrentYear();
	String tommorrowDate = getForwardDate(2);
	int timesLimitOfDay = 10;
	private String lowerMin, higherMax, higherGroup,higherPackage, otpNo,passNo;

	SourceAccountModel sourceAccount = new SourceAccountModel();
	SourceAccountModel receiverAccount = new SourceAccountModel();
	ServiceLimitInfo inputInfo = new ServiceLimitInfo("1000", "10000", "1000000", "1500000");

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp", "username", "passWeb" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt, String username, String passWeb) throws IOException, InterruptedException {
		startServer();

		log.info("Before class: Mo backend ");
		driverWeb = openMultiBrowser(Constants.BE_BROWSER_CHROME, Constants.BE_BROWSER_VERSION, Constants.BE_URL);

		setupBE = WebPageFactoryManager.getWebBackendSetupPageObject(driver);

		setupBE.Login_Web_Backend(driverWeb, username, passWeb);
		
		setupBE.Setup_Add_Method_Package_Total_Limit(driverWeb, "TESTBUG", "Method Otp");
		setupBE.Setup_Assign_Services_Type_Limit(driverWeb,Constants.BE_CODE_PACKAGE, InputText_MoneyRecurrent.BE_TRANSFER_TEXT, inputInfo.totalLimit);
		
		log.info("Before class: Mo app ");
		if (deviceType.contains("android")) {
			driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		} else if (deviceType.contains("ios")) {
			driver = openIOSApp(deviceName, udid, url);
		}
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
		passNo = pass;
		otpNo = opt;
		log.info("Before class_Step_10: Scroll den trang thai lenh chuyen tien");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.scrollDownToText(driver, InputText_MoneyRecurrent.TRANSFER_MONEY_STATUS_TEXT);
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		lowerMin = (Integer.parseInt(inputInfo.minTran) - 1) + "";
		higherMax = (Integer.parseInt(inputInfo.maxTran) + 1) + "";
		higherGroup = (Integer.parseInt(inputInfo.totalLimit) + 1) + "";
		higherPackage = (Integer.parseInt(Constants.AMOUNT_DEFAULT_MIN_PACKAGE) + 1) + "";

	}

	@Test
	public void TC_01_ChuyenTienDinhKyVuotQuaNhomDichVu() {

		log.info("TC_01_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, Home_Text_Elements.HOME_TRANSFER_IN_VCB);

		log.info("TC_01_Step_02: Chon chuyen tien ngay gia tri hien tai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.OPTION_TRANSFER[0]);

		log.info("TC_01_Step_03: Chon chuyen tien ngay gia tri hien tai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.OPTION_TRANSFER[1]);

		log.info("TC_01_Step_04:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		log.info("TC_01_Step_05: Chon tai khoan dich");
		List<String> listDistanceAccount = transferInVCB.getListSourceAccount(driver, Constants.VND_CURRENCY);
		sourceAccount = transferInVCB.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);
		
		log.info("TC_01_Step_10: Nhap tai khoan nhan");
		receiverAccount.account = transferInVCB.getDistanceAccount(driver, sourceAccount.account, listDistanceAccount);
		transferInVCB.inputToDynamicInputBox(driver, receiverAccount.account, TittleData.INPUT_ACCOUNT_BENEFICI);

		log.info("TC_01_05_Chon tan suat");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, InputData_MoneyRecurrent.DAY_TEXT);
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, InputData_MoneyRecurrent.DAY_TEXT);
		transferInVCB.inputFrequencyNumber("1");

		log.info("TC_01_05_Chon tan suat");
		transferInVCB.inputToDynamicInputBox(driver, higherGroup, TittleData.AMOUNT);

		log.info("TC_01_Step_10: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_01_Step_11: Verify hien thi man hinh thong bao loi");
		verifyEquals(transferInVCB.getTextDynamicFollowImage(driver, "com.VCB:id/ivTitle"), TransferMoneyInVCB_Data.Output.MESSEGE_ERROR_HIGHER_MAX_GROUP_LIMIT + addCommasToLong(inputInfo.totalLimit) + TransferMoneyInVCB_Data.Output.DETAIL_A_GROUP_MESSAGE);

		log.info("TC_01_Step_12: Click btn Dong");
		transferInVCB.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
		transferInVCB.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
		
		setupBE.Reset_Setup_Assign_Services_Type_Limit(driverWeb,Constants.BE_CODE_PACKAGE, InputText_MoneyRecurrent.BE_TRANSFER_TEXT);

	}

	@Test
	public void TC_02_ChuyenTienTuongLaiVuotQuaGoiDichVu() {

		log.info("TC_02_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, Home_Text_Elements.HOME_TRANSFER_IN_VCB);

		log.info("TC_02_Step_02: Chon chuyen tien ngay gia tri hien tai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.OPTION_TRANSFER[0]);

		log.info("TC_02_Step_03: Chon chuyen tien ngay gia tri hien tai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.OPTION_TRANSFER[1]);

		log.info("TC_02_Step_04:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		log.info("TC_02_Step_05: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_02_Step_10: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, receiverAccount.account, TittleData.INPUT_ACCOUNT_BENEFICI);

		log.info("TC_02_05_Chon tan suat");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, InputData_MoneyRecurrent.DAY_TEXT);
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, InputData_MoneyRecurrent.DAY_TEXT);
		transferInVCB.inputFrequencyNumber("1");

		log.info("TC_02_05_Chon tan suat");
		transferInVCB.inputToDynamicInputBox(driver, higherPackage, TittleData.AMOUNT);

		log.info("TC_02_Step_10: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_02_Step_11: Verify hien thi man hinh thong bao loi");
		verifyEquals(transferInVCB.getTextDynamicFollowImage(driver, "com.VCB:id/ivTitle"), TransferMoneyInVCB_Data.Output.MESSEGE_ERROR_HIGHER_MAX_LIMIT + addCommasToLong(Constants.AMOUNT_DEFAULT_MIN_PACKAGE) + TransferMoneyInVCB_Data.Output.DETAIL_A_PACKAGE_MESSAGE);

		log.info("TC_02_Step_12: Click btn Dong");
		transferInVCB.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
		transferInVCB.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
	}
	
	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp", "username", "passWeb" })
	@Test
	public void TC_03_ResetHanMucMinMax_Va_SuaHanMucNhom_GoiDichVu(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt, String username, String passWeb) throws MalformedURLException {
		
		closeApp();
		setupBE.Reset_Package_Total_Limit(driverWeb, Constants.BE_CODE_PACKAGE, "Method Otp");
		setupBE.setupAssignServicesLimit_All(driverWeb, InputText_MoneyRecurrent.BE_TRANSFER_RECURRENT_TEXT, inputInfo,Constants.BE_CODE_PACKAGE);
		
		log.info("Before class: Mo app ");
		if (deviceType.contains("android")) {
			driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		} else if (deviceType.contains("ios")) {
			driver = openIOSApp(deviceName, udid, url);
		}
		login.Global_login(phone, pass, opt);
		
	}
	
	@Test
	public void TC_04_ChuyenTienDinhKyThapHonHanMucToiThieu() {

		log.info("TC_04_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, Home_Text_Elements.HOME_TRANSFER_IN_VCB);

		log.info("TC_04_02_Chon phuong thuc chuyen tien");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.OPTION_TRANSFER[0]);
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.OPTION_TRANSFER[1]);

		log.info("TC_04_Step_04:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);
		List<String> listDistanceAccount = transferInVCB.getListSourceAccount(driver, Constants.VND_CURRENCY);
		sourceAccount = transferInVCB.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		log.info("TC_04_Step_10: Nhap tai khoan nhan");
		receiverAccount.account = transferInVCB.getDistanceAccount(driver, sourceAccount.account, listDistanceAccount);
		transferInVCB.inputToDynamicInputBox(driver, receiverAccount.account, TittleData.INPUT_ACCOUNT_BENEFICI);

		log.info("TC_04_05_Chon tan suat");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, InputData_MoneyRecurrent.DAY_TEXT);
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, InputData_MoneyRecurrent.DAY_TEXT);
		transferInVCB.inputFrequencyNumber("1");

		log.info("TC_04_05_Chon tan suat");
		transferInVCB.inputToDynamicInputBox(driver, lowerMin, TittleData.AMOUNT);

		log.info("TC_04_Step_10: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_04_Step_15: Verify hien thi man hinh thong bao loi");
		verifyEquals(transferInVCB.getTextDynamicFollowImage(driver, "com.VCB:id/ivTitle"), TransferMoneyInVCB_Data.Output.MESSEGE_ERROR_lOWER_MIN_LIMIT + addCommasToLong(inputInfo.minTran) + TransferMoneyInVCB_Data.Output.LIMIT_TRANSACTION_MESSAGE);

		log.info("TC_04_Step_16: Click btn Dong");
		transferInVCB.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
		transferInVCB.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

	}
	
	@Test
	public void TC_05_ChuyenTienDinhKyCaoHonHanMucToiDa() {

		log.info("TC_05_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, Home_Text_Elements.HOME_TRANSFER_IN_VCB);

		log.info("TC_05_Step_02: Chon chuyen tien ngay gia tri hien tai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.OPTION_TRANSFER[0]);
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.OPTION_TRANSFER[1]);

		log.info("TC_05_Step_04:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		log.info("TC_05_Step_05: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_05_Step_10: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, receiverAccount.account, TittleData.INPUT_ACCOUNT_BENEFICI);

		log.info("TC_05_05_Chon tan suat");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, InputData_MoneyRecurrent.DAY_TEXT);
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, InputData_MoneyRecurrent.DAY_TEXT);
		transferInVCB.inputFrequencyNumber("1");

		log.info("TC_05_05_Chon tan suat");
		transferInVCB.inputToDynamicInputBox(driver, higherMax, TittleData.AMOUNT);

		log.info("TC_05_Step_10: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_05_Step_15: Verify hien thi man hinh thong bao loi");
		verifyEquals(transferInVCB.getTextDynamicFollowImage(driver, "com.VCB:id/ivTitle"), TransferMoneyInVCB_Data.Output.MESSEGE_ERROR_HIGHER_MAX_LIMIT + addCommasToLong(inputInfo.maxTran) + TransferMoneyInVCB_Data.Output.LIMIT_TRANSACTION_MESSAGE);

		log.info("TC_05_Step_16: Click btn Dong");
		transferInVCB.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
		transferInVCB.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
	}
	
	@Test
	public void TC_06_ChuyenTienTuongLaiVuotQuaHanMucTrongNgay() {

		log.info("TC_06_Step_01: Chon chuyen tien ngay gia tri hien tai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.OPTION_TRANSFER[0]);
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.OPTION_TRANSFER[1]);

		log.info("TC_06_Step_02:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		log.info("TC_06_Step_03: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_06_Step_04: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, receiverAccount.account, TittleData.INPUT_ACCOUNT_BENEFICI);

		log.info("TC_06_Step_05: Chon tan suat");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, InputData_MoneyRecurrent.DAY_TEXT);
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, InputData_MoneyRecurrent.DAY_TEXT);
		transferInVCB.inputFrequencyNumber("1");

		log.info("TC_06_Step_06: Nhap so tien");
		transferInVCB.inputToDynamicInputBox(driver, inputInfo.maxTran, TittleData.AMOUNT);

		log.info("TC_06_Step_07: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_06_Step_08: verify xac thuc thong tin");
		transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.INFO_VALIDATION);

		log.info("TC_06_Step_09: Chon Phuong thuc nhap");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.METHOD_VALIDATE);

		log.info("TC_06_Step_10: Chon SMS OTP");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TittleData.SMS_OTP);

		log.info("TC_06_Step_11: Click Tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_06_Step_12: Nhap OTP");
		transferInVCB.inputToDynamicOtp(driver, otpNo, TittleData.CONTINUE_BTN);

		log.info("TC_06_Step_13: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_06_Step_14: Click thuc hien giao dich moi");
		transferInVCB.clickToDynamicButton(driver, TittleData.NEW_TRANSFER);
		
		log.info("TC_06_Step_15:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		log.info("TC_06_Step_16: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_06_Step_17: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, receiverAccount.account, TittleData.INPUT_ACCOUNT_BENEFICI);

		log.info("TC_06_Step_18: Chon tan suat");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, InputData_MoneyRecurrent.DAY_TEXT);
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, InputData_MoneyRecurrent.DAY_TEXT);
		transferInVCB.inputFrequencyNumber("1");

		log.info("TC_06_Step_19: Nhap so tien");
		transferInVCB.inputToDynamicInputBox(driver, inputInfo.maxTran, TittleData.AMOUNT);

		log.info("TC_06_Step_20: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_06_Step_21: verify xac thuc thong tin");
		transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.INFO_VALIDATION);

		log.info("TC_06_Step_22: Chon Phuong thuc nhap");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.METHOD_VALIDATE);

		log.info("TC_06_Step_23: Chon MK");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TittleData.PASSWORD);

		log.info("TC_06_Step_24: Click Tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_06_Step_25: Nhap MK");
		transferInVCB.inputToDynamicPopupPasswordInput(driver, passNo, TittleData.CONTINUE_BTN);

		log.info("TC_06_Step_14: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_06_Step_15: Click thuc hien giao dich moi");
		transferInVCB.clickToDynamicButton(driver, TittleData.NEW_TRANSFER);

		log.info("TC_06_Step_16: Chon chuyen tien ngay gia tri hien tai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.OPTION_TRANSFER[0]);
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.OPTION_TRANSFER[1]);

		log.info("TC_06_Step_17: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		log.info("TC_06_Step_18: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_06_Step_19: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, receiverAccount.account, TittleData.INPUT_ACCOUNT_BENEFICI);

		log.info("TC_06_Step_20: Chon tan suat");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, InputData_MoneyRecurrent.DAY_TEXT);
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, InputData_MoneyRecurrent.DAY_TEXT);
		transferInVCB.inputFrequencyNumber("1");

		log.info("TC_06_Step_21: Chon tan suat");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInFutureForOTP.HIGHER_TRANSFER_AMOUNT, TittleData.AMOUNT);

		log.info("TC_06_Step_22: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_06_Step_23: Verify hien thi man hinh thong bao loi");
		verifyEquals(transferInVCB.getTextDynamicFollowImage(driver, "com.VCB:id/ivTitle"), TransferMoneyInVCB_Data.Output.MESSEGE_ERROR_HIGHER_MAX_LIMIT + addCommasToLong(inputInfo.totalLimit) + TransferMoneyInVCB_Data.Output.LIMIT_DAY_MESSAGE);

		log.info("TC_06_Step_24: Click btn Dong");
		transferInVCB.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
		transferInVCB.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		setupBE.resetAssignServicesLimit_All(driverWeb, InputText_MoneyRecurrent.BE_TRANSFER_RECURRENT_TEXT,Constants.BE_CODE_PACKAGE);
		driverWeb.quit();
		service.stop();
	}

}
