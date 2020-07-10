package vnpay.vietcombank.transfer_money_in_vcb;

import java.io.IOException;
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
import vietcombank_test_data.TransferMoneyInVCB_Data.InputText_MoneyRecurrent;
import vietcombank_test_data.TransferMoneyInVCB_Data.TittleData;

public class Transfer_Money_Recurrent_Limit extends Base {
	AppiumDriver<MobileElement> driver;
	private WebDriver driverWeb;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferMoneyInVcbPageObject transferInVCB;
	private WebBackendSetupPageObject setupBE;

	private String higherGroup, higherPackage, lowerMin, higherMax;

	SourceAccountModel sourceAccount = new SourceAccountModel();
	SourceAccountModel receiverAccount = new SourceAccountModel();
	ServiceLimitInfo inputPackageGroupInfo = new ServiceLimitInfo("1000", "10000", "100000000", "150000000");
	ServiceLimitInfo inputInfo = new ServiceLimitInfo("1000", "10000", "200000100", "200000200");
	ServiceLimitInfo info = new ServiceLimitInfo("100000", "10000", "10000000000", "15000000000");

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp", "username", "passWeb" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt, String username, String passWeb) throws IOException, InterruptedException {
		startServer();

		log.info("Before class: Mo backend ");
		driverWeb = openMultiBrowser(Constants.BE_BROWSER_CHROME, Constants.BE_BROWSER_VERSION, Constants.BE_URL);

		setupBE = WebPageFactoryManager.getWebBackendSetupPageObject(driverWeb);

		setupBE.Login_Web_Backend(driverWeb, username, passWeb);

		setupBE.Setup_Add_Method_Package_Total_Limit(driverWeb, Constants.BE_CODE_PACKAGE, "Method Otp", inputPackageGroupInfo.totalLimit);
		setupBE.Setup_Assign_Services_Type_Limit(driverWeb, Constants.BE_CODE_PACKAGE, InputText_MoneyRecurrent.BE_TRANSFER_TEXT, inputPackageGroupInfo.maxTran);
		setupBE.addMethodOtpLimit(driverWeb, InputText_MoneyRecurrent.BE_TRANSFER_RECURRENT_TEXT);
		setupBE.addMethodServicesLimit(driverWeb, InputText_MoneyRecurrent.BE_TRANSFER_RECURRENT_TEXT, info, Constants.BE_CODE_PACKAGE);
		setupBE.setupAssignServicesLimit_All(driverWeb, InputText_MoneyRecurrent.BE_TRANSFER_RECURRENT_TEXT, inputInfo, Constants.BE_CODE_PACKAGE);
		setupBE.clearCacheBE(driverWeb);
		driverWeb.quit();

		log.info("Before class: Mo app ");
		if (deviceType.contains("android")) {
			driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		} else if (deviceType.contains("ios")) {
			driver = openIOSApp(deviceName, udid, url);
		}
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);

		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.scrollDownToText(driver, InputText_MoneyRecurrent.TRANSFER_MONEY_STATUS_TEXT);
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);

		higherGroup = (Integer.parseInt(inputPackageGroupInfo.maxTran) + 1) + "";
		higherPackage = (Integer.parseInt(inputPackageGroupInfo.totalLimit) + 1) + "";
		lowerMin = (Integer.parseInt(inputInfo.minTran) - 1) + "";
		higherMax = (Integer.parseInt(inputInfo.maxTran) + 1) + "";

	}

	@Parameters({"username","passWeb"})
	@Test
	public void TC_01_ChuyenTienDinhKy_VuotQuaNhomDichVu(String username, String passWeb) {

		log.info("TC_01_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, Home_Text_Elements.HOME_TRANSFER_IN_VCB);

		log.info("TC_01_Step_02: Chon chuyen tien ngay gia tri hien tai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.OPTION_TRANSFER[0]);

		log.info("TC_01_Step_03: Chon chuyen tien ngay gia tri hien tai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.OPTION_TRANSFER[1]);

		log.info("TC_01_Step_04: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		log.info("TC_01_Step_05: Chon tai khoan dich");
		List<String> listDistanceAccount = transferInVCB.getListSourceAccount(driver, Constants.VND_CURRENCY);
		sourceAccount = transferInVCB.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		log.info("TC_01_Step_06: Nhap tai khoan nhan");
		receiverAccount.account = transferInVCB.getDistanceAccount(driver, sourceAccount.account, listDistanceAccount);
		transferInVCB.inputToDynamicInputBox(driver, receiverAccount.account, TittleData.INPUT_ACCOUNT_BENEFICI);

		driverWeb = openMultiBrowser(Constants.BE_BROWSER_CHROME, Constants.BE_BROWSER_VERSION, Constants.BE_URL);
		setupBE.Login_Web_Backend(driverWeb, username, passWeb);

		log.info("TC_01_Step_07: Nhap so tien");
		transferInVCB.inputToDynamicInputBox(driver, higherGroup, TittleData.AMOUNT);

		log.info("TC_01_Step_08: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_01_Step_09: Verify hien thi man hinh thong bao loi");
		verifyEquals(transferInVCB.getTextDynamicFollowImage(driver, "com.VCB:id/ivTitle"), TransferMoneyInVCB_Data.Output.MESSEGE_ERROR_HIGHER_MAX_GROUP_LIMIT + addCommasToLong(inputPackageGroupInfo.maxTran) + TransferMoneyInVCB_Data.Output.DETAIL_A_GROUP_MESSAGE);

		setupBE.Reset_Setup_Assign_Services_Type_Limit(driverWeb, Constants.BE_CODE_PACKAGE, InputText_MoneyRecurrent.BE_TRANSFER_TEXT);
		setupBE.clearCacheBE(driverWeb);

		log.info("TC_01_Step_10: Click btn Dong");
		transferInVCB.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

	}

	@Test
	public void TC_02_ChuyenTienDinhKy_VuotQuaGoiDichVu() {

		log.info("TC_02_Step_01: Nhap So tien chuyen");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, higherPackage, TittleData.TRANSFER_INFO, "1");

		log.info("TC_02_Step_02: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_02_Step_03: Verify hien thi man hinh thong bao loi");
		verifyEquals(transferInVCB.getTextDynamicFollowImage(driver, "com.VCB:id/ivTitle"), TransferMoneyInVCB_Data.Output.MESSEGE_ERROR_HIGHER_MAX_LIMIT + addCommasToLong(inputPackageGroupInfo.totalLimit) + TransferMoneyInVCB_Data.Output.DETAIL_A_PACKAGE_MESSAGE);

		log.info("TC_02_Step_04: Click btn Dong");
		transferInVCB.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
	}

	@Test
	public void TC_03_ChuyenTienDinhKy_ThapHonHanMucToiThieu() {

		if(transferInVCB.isTextDisplayed(driver, TransferMoneyInVCB_Data.Output.INFO_VALIDATION) == true) {
			transferInVCB.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
		}
		
		log.info("TC_03_Step_01: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, lowerMin, TittleData.TRANSFER_INFO, "1");

		setupBE.Reset_Package_Total_Limit(driverWeb, Constants.BE_CODE_PACKAGE, "Method Otp");
		driverWeb.quit();

		log.info("TC_03_Step_02: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_03_Step_03: Verify hien thi man hinh thong bao loi");
		verifyEquals(transferInVCB.getTextDynamicFollowImage(driver, "com.VCB:id/ivTitle"), TransferMoneyInVCB_Data.Output.RECURRENT_LOWER_MIN_LIMIT + addCommasToLong(inputInfo.minTran) + TransferMoneyInVCB_Data.Output.LIMIT_TRANSACTION_MESSAGE);

		log.info("TC_03_Step_04: Click btn Dong");
		transferInVCB.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

	}

	@Test
	public void TC_04_ChuyenTienDinhKy_CaoHonHanMucToiDa() {

		log.info("TC_04_Step_01: Nhap so tien");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, higherMax, TittleData.TRANSFER_INFO, "1");

		log.info("TC_04_Step_02: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_04_Step_03: Verify hien thi man hinh thong bao loi");
		verifyEquals(transferInVCB.getTextDynamicFollowImage(driver, "com.VCB:id/ivTitle"), TransferMoneyInVCB_Data.Output.RECURRENT_HIGHER_MAX_LIMT + addCommasToLong(inputInfo.maxTran) + TransferMoneyInVCB_Data.Output.LIMIT_TRANSACTION_MESSAGE);

		log.info("TC_04_Step_04: Click btn Dong");
		transferInVCB.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

	}

	// Chi run duoc TC nay trong thoi diem hach toan
	@Parameters({"otp","pass"})
	@Test
	public void TC_05_ChuyenTienDinhKy_VuotQuaHanMuc_TrongNgay(String otpNo, String passNo) {

		log.info("TC_05_Step_06: Nhap so tien");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, inputInfo.minTran, TittleData.TRANSFER_INFO, "1");

		log.info("TC_05_Step_07: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_05_Step_09: Chon Phuong thuc nhap");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.METHOD_VALIDATE);

		log.info("TC_05_Step_10: Chon SMS OTP");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TittleData.SMS_OTP);

		log.info("TC_05_Step_11: Click Tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_05_Step_12: Nhap OTP");
		transferInVCB.inputToDynamicOtp(driver, otpNo, TittleData.CONTINUE_BTN);

		log.info("TC_05_Step_13: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_05_Step_14: Click thuc hien giao dich moi");
		transferInVCB.clickToDynamicButton(driver, TittleData.NEW_TRANSFER);

		log.info("TC_05_Step_01: Chon chuyen tien dinh ky");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.OPTION_TRANSFER[0]);
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.OPTION_TRANSFER[1]);

		log.info("TC_05_Step_02:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		log.info("TC_05_Step_03: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_05_Step_04: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, receiverAccount.account, TittleData.INPUT_ACCOUNT_BENEFICI);

		log.info("TC_05_Step_06: Nhap so tien");
		transferInVCB.inputToDynamicInputBox(driver, inputInfo.minTran, TittleData.AMOUNT);

		log.info("TC_05_Step_07: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_05_Step_09: Chon Phuong thuc nhap");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.METHOD_VALIDATE);

		log.info("TC_05_Step_10: Chon MK");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TittleData.PASSWORD);

		log.info("TC_05_Step_11: Click Tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_05_Step_12: Nhap MK");
		transferInVCB.inputToDynamicPopupPasswordInput(driver, passNo, TittleData.CONTINUE_BTN);
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_05_Step_14: Click thuc hien giao dich moi");
		transferInVCB.clickToDynamicButton(driver, TittleData.NEW_TRANSFER);

		log.info("TC_05_Step_15:Click tai khoan nguon");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.OPTION_TRANSFER[0]);
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.OPTION_TRANSFER[1]);

		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		log.info("TC_05_Step_16: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_05_Step_17: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, receiverAccount.account, TittleData.INPUT_ACCOUNT_BENEFICI);

		log.info("TC_05_Step_19: Nhap so tien");
		transferInVCB.inputToDynamicInputBox(driver, inputInfo.maxTran, TittleData.AMOUNT);

		log.info("TC_05_Step_20: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_05_Step_23: Verify hien thi man hinh thong bao loi");
		verifyEquals(transferInVCB.getTextDynamicFollowImage(driver, "com.VCB:id/ivTitle"), TransferMoneyInVCB_Data.Output.MESSEGE_ERROR_HIGHER_MAX_LIMIT + addCommasToLong(inputInfo.totalLimit) + TransferMoneyInVCB_Data.Output.LIMIT_DAY_MESSAGE);

		log.info("TC_05_Step_24: Click btn Dong");
		transferInVCB.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
		transferInVCB.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

	}

	@Parameters ({"username","passWeb"})
	@AfterClass(alwaysRun = true)
	public void afterClass(String username,String passWeb) {
		
		driverWeb = openMultiBrowser(Constants.BE_BROWSER_CHROME, Constants.BE_BROWSER_VERSION, Constants.BE_URL);
		setupBE.Login_Web_Backend(driverWeb, username, passWeb);
		
		setupBE.resetAssignServicesLimit_All(driverWeb, InputText_MoneyRecurrent.BE_TRANSFER_RECURRENT_TEXT,Constants.BE_CODE_PACKAGE);
		driverWeb.quit();
		
		service.stop();
	}

}
