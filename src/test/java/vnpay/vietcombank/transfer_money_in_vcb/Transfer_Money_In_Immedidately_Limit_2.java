package vnpay.vietcombank.transfer_money_in_vcb;

import java.io.IOException;
import java.security.GeneralSecurityException;

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
import vietcombank_test_data.TransferMoneyInVCB_Data.TittleData;

public class Transfer_Money_In_Immedidately_Limit_2 extends Base {
	AppiumDriver<MobileElement> driver;
	WebDriver driverWeb;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferMoneyInVcbPageObject transferInVCB;
	private WebBackendSetupPageObject loginWeb;
	SourceAccountModel sourceAccount = new SourceAccountModel();
	String other_account = "";
	String[] exchangeRateUSD;
	String today = getCurrentDay() + "/" + getCurrenMonth() + "/" + getCurrentYear();
	String tommorrowDate = getForwardDate(1);
	ServiceLimitInfo inputInfo = new ServiceLimitInfo("1000", "1000", "1000000", "1000001");

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp", "username", "passWeb" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt, String username, String passWeb) throws IOException, InterruptedException, GeneralSecurityException {
		log.info("Before class: Mo backend ");
		startServer();
		driverWeb = openMultiBrowser(Constants.BE_BROWSER_CHROME, Constants.BE_BROWSER_VERSION, Constants.BE_URL);
		loginWeb = WebPageFactoryManager.getWebBackendSetupPageObject(driverWeb);
		loginWeb.Login_Web_Backend(driverWeb, username, passWeb);

		loginWeb.setupAssignServicesLimit(driverWeb, TittleData.TRANSFER_IN_BANK_OTHER_OWNER, inputInfo, TittleData.PACKAGE_NAME);

		log.info("Before class: Mo app ");
		if (deviceType.contains("android")) {
			driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		} else if (deviceType.contains("ios")) {
			driver = openIOSApp(deviceName, udid, url);
		}
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);

		log.info("Before class_Step_10: Scroll den trang thai lenh chuyen tien");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.scrollDownToText(driver, Home_Text_Elements.TRANS_STATUS);
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		other_account = getDataInCell(0);
	}

// Hạn mức nội bộ khác chủ tài khoản
	@Test
	public void TC_01_ChuyenTienTuongLaiThapHonHanMucToiThieu() throws InterruptedException {

		log.info("TC_01_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, Home_Text_Elements.HOME_TRANSFER_IN_VCB);

		log.info("TC_01_Step_02:Click tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);

		log.info("TC_01_Step_04:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		log.info("TC_01_Step_05: Chon tai khoan dich");
		sourceAccount = transferInVCB.chooseSourceAccount(driver, Constants.AMOUNT_VND, Constants.VND_CURRENCY);

		log.info("TC_01_Step_08: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, other_account, TittleData.INPUT_ACCOUNT_BENEFICI);

		log.info("TC_01_Step_09: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, Integer.parseInt(inputInfo.minTran) - 1 + "", TittleData.AMOUNT);

		log.info("TC_01_Step_10: Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, TittleData.TRANSFER_INFO, "3");

		log.info("TC_01_Step_11: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		verifyEquals(transferInVCB.getDynamicTextView(driver, "com.VCB:id/tvContent"), vietcombank_test_data.TransferMoneyInVCB_Data.Output.ERROR_MESSAGE_WITH_MIN_LIMIT_TRANSFER_MONEY);

		transferInVCB.clickToDynamicContinue(driver, "com.VCB:id/btOK");

		transferInVCB.clickToDynamicBackIcon(driver, TittleData.TRANSFER_IN_VCBANK);

		homePage.clickToDynamicIcon(driver, Home_Text_Elements.HOME_TRANSFER_IN_VCB);

	}

	@Test
	public void TC_02_ChuyenTienTuongLaiCaoHonHanMucToiDa() throws InterruptedException {

		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);

		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_04_Step_08: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, other_account, TittleData.INPUT_ACCOUNT_BENEFICI);

		log.info("TC_01_Step_09: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, Integer.parseInt(inputInfo.maxTran) + 1 + "", TittleData.AMOUNT);

		log.info("TC_01_Step_10: Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, TittleData.TRANSFER_INFO, "3");

		log.info("TC_01_Step_11: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		verifyEquals(transferInVCB.getDynamicTextView(driver, "com.VCB:id/tvContent"), vietcombank_test_data.TransferMoneyInVCB_Data.Output.ERROR_MESSAGE_WITH_MAX_LIMIT_TRANSFER_MONEY);

		transferInVCB.clickToDynamicContinue(driver, "com.VCB:id/btOK");

		transferInVCB.clickToDynamicBackIcon(driver, TittleData.TRANSFER_IN_VCBANK);

		homePage.clickToDynamicIcon(driver, Home_Text_Elements.HOME_TRANSFER_IN_VCB);

	}

	@Parameters({ "otp" })
	@Test
	public void TC_03_ChuyenTienTuongLaiVuotQuaHanMucTrongNgay(String otp) throws InterruptedException {

		loginWeb.setupAssignServicesLimit_Total_Day(driverWeb, TittleData.TRANSFER_IN_BANK_SAME_OWNER, inputInfo, TittleData.PACKAGE_NAME);

		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_03_Step_08: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, other_account, TittleData.INPUT_ACCOUNT_BENEFICI);

		log.info("TC_03_Step_09: Nhap so tien chuyen");

		transferInVCB.inputToDynamicInputBox(driver, Integer.parseInt(inputInfo.minTran)+ "", TittleData.AMOUNT);

		log.info("TC_03_Step_10: Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, TittleData.TRANSFER_INFO, "3");

		log.info("TC_03_Step_11: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);
		
		
		log.info("TC_01_Step_20: Chon Phuong thuc nhap");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.METHOD_VALIDATE);

		log.info("TC_01_Step_21: Chon SMS OTP");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TittleData.SMS_OTP);


		log.info("TC_01_Step_22: Click Tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_01_Step_23: Nhap OTP");
		transferInVCB.inputToDynamicOtp(driver, otp, TittleData.CONTINUE_BTN);

		log.info("TC_01_Step_24: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_01_Step_25: Kiem  tra giao dich thanh cong");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE));


		log.info("TC_01_Step_31: Click thuc hien giao dich moi");
		transferInVCB.clickToDynamicButton(driver, TittleData.NEW_TRANSFER);
		
		
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_03_Step_08: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, other_account, TittleData.INPUT_ACCOUNT_BENEFICI);

		log.info("TC_03_Step_09: Nhap so tien chuyen");

		transferInVCB.inputToDynamicInputBox(driver, Integer.parseInt(inputInfo.maxTran)+"", TittleData.AMOUNT);

		
		log.info("TC_03_Step_10: Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, TittleData.TRANSFER_INFO, "3");

		log.info("TC_03_Step_11: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);
		

		verifyTrue(transferInVCB.getDynamicTextView(driver, "com.VCB:id/tvContent").contains("Chuyển tiền không thành công. Số tiền giao dịch lớn hơn hạn mức"));

		transferInVCB.clickToDynamicContinue(driver, "com.VCB:id/btOK");
		transferInVCB.clickToDynamicBackIcon(driver, TittleData.TRANSFER_IN_VCBANK);

		loginWeb.resetAssignServicesLimit(driverWeb, TittleData.TRANSFER_IN_BANK_SAME_OWNER, TittleData.PACKAGE_NAME);
	}

	@Test
	public void TC_04_ChuyenTienTuongLaiVuotQuaNhomDichVu() throws InterruptedException {

		transferInVCB.clickToDynamicBackIcon(driver, TittleData.TRANSFER_IN_VCBANK);
		homePage.clickToDynamicIcon(driver, Home_Text_Elements.HOME_TRANSFER_IN_VCB);

		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);

		loginWeb.Setup_Assign_Services_Type_Limit(driverWeb, TittleData.PACKAGE_NAME, TittleData.TRANSFER_IN_BANK_OTHER_OWNER, Constants.AMOUNT_DEFAULT_MIN_PACKAGE);

		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_04_Step_08: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, other_account, TittleData.INPUT_ACCOUNT_BENEFICI);

		log.info("TC_04_Step_09: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, Integer.parseInt(Constants.AMOUNT_DEFAULT_MIN_PACKAGE) + 1 + "", TittleData.AMOUNT);

		log.info("TC_04_Step_10: Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, TittleData.TRANSFER_INFO, "3");

		log.info("TC_04_Step_11: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		verifyTrue(transferInVCB.getDynamicTextView(driver, "com.VCB:id/tvContent").contains("Chuyển tiền không thành công. Số tiền giao dịch"));

		transferInVCB.clickToDynamicContinue(driver, "com.VCB:id/btOK");

		transferInVCB.clickToDynamicBackIcon(driver, TittleData.TRANSFER_IN_VCBANK);

		homePage.clickToDynamicIcon(driver, Home_Text_Elements.HOME_TRANSFER_IN_VCB);

		loginWeb.Reset_Setup_Assign_Services_Type_Limit(driverWeb, TittleData.PACKAGE_NAME, TittleData.TRANSFER_IN_BANK_OTHER_OWNER);

	}

	@Test
	public void TC_05_ChuyenTienTuongLaiVuotQuaGoiDichVu() throws InterruptedException {
		loginWeb.Setup_Add_Method_Package_Total_Limit(driverWeb, TittleData.PACKAGE_NAME, TittleData.TITTLE_METHOD,Constants.AMOUNT_DEFAULT_MIN_PACKAGE);

		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);

		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_04_Step_08: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, other_account, TittleData.INPUT_ACCOUNT_BENEFICI);

		log.info("TC_05_Step_09: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, Integer.parseInt(Constants.AMOUNT_DEFAULT_MIN_PACKAGE) + 1 + "", TittleData.AMOUNT);

		log.info("TC_05_Step_10: Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, TittleData.TRANSFER_INFO, "3");

		log.info("TC_05_Step_11: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		verifyTrue(transferInVCB.getDynamicTextView(driver, "com.VCB:id/tvContent").contains("Chuyển tiền không thành công"));

		transferInVCB.clickToDynamicContinue(driver, "com.VCB:id/btOK");

		loginWeb.Reset_Package_Total_Limit(driverWeb, "PKG1", TittleData.TITTLE_METHOD);

	}

	@AfterClass(alwaysRun = true)

	public void afterClass() {
//		closeApp();
//		service.stop();
	}

}
