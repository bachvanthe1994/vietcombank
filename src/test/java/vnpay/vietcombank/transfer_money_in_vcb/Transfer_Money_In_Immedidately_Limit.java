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
import model.ServiceTypeLimitInfo;
import model.SourceAccountModel;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.TransferMoneyInVcbPageObject;
import pageObjects.WebBackendSetupPageObject;
import vietcombank_test_data.HomePage_Data.Home_Text_Elements;
import vietcombank_test_data.TransferMoneyInVCB_Data;
import vietcombank_test_data.TransferMoneyInVCB_Data.TittleData;

public class Transfer_Money_In_Immedidately_Limit extends Base {
	AppiumDriver<MobileElement> driver;
	WebDriver driverWeb;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferMoneyInVcbPageObject transferInVCB;
	private WebBackendSetupPageObject loginWeb;
	SourceAccountModel sourceAccount = new SourceAccountModel();
	SourceAccountModel distanceAccount = new SourceAccountModel();

	String[] exchangeRateUSD;
	String today = getCurrentDay() + "/" + getCurrenMonth() + "/" + getCurrentYear();
	String tommorrowDate = getForwardDate(1);
	ServiceLimitInfo inputInfo = new ServiceLimitInfo("1000", "10000", "1000000", "1500000");
	ServiceTypeLimitInfo inputInfoType = new ServiceTypeLimitInfo("PIN", "Việt Nam Đồng", "40000000");

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp", "username", "passWeb" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt, String username, String passWeb) throws IOException, InterruptedException {
		log.info("Before class: Mo backend ");
		startServer();
		driverWeb = openMultiBrowser(Constants.BE_BROWSER_CHROME, Constants.BE_BROWSER_VERSION, Constants.BE_URL);
		loginWeb = WebPageFactoryManager.getWebBackendSetupPageObject(driverWeb);
		loginWeb.Login_Web_Backend(driverWeb, username, passWeb);

		loginWeb.addMethodOtpLimit(driverWeb, "Chuyển khoản nội bộ cùng chủ tài khoản");
		loginWeb.setupAssignServicesLimit(driverWeb, "Chuyển khoản nội bộ cùng chủ tài khoản", inputInfo);
		

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

	}

	@Test
	public void TC_01_ChuyenTienTuongLaiThapHonHanMucToiThieu() throws InterruptedException {

		log.info("TC_01_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, Home_Text_Elements.HOME_TRANSFER_IN_VCB);

		log.info("TC_01_Step_02:Click tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);

		log.info("TC_01_Step_04:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);
		List<String> listAccount = transferInVCB.getListSourceAccount(driver, Constants.VND_CURRENCY);
		log.info("TC_01_Step_05: Chon tai khoan dich");
		sourceAccount = transferInVCB.chooseSourceAccount(driver, Constants.AMOUNT_VND, Constants.VND_CURRENCY);

		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);
		distanceAccount.account = transferInVCB.getDistanceAccount(driver, sourceAccount.account, listAccount);
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_01_Step_08: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, distanceAccount.account, TittleData.INPUT_ACCOUNT_BENEFICI);

		log.info("TC_01_Step_09: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, Integer.parseInt(inputInfo.minTran) - 1 + "", TittleData.AMOUNT);

		log.info("TC_01_Step_10: Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, TittleData.TRANSFER_INFO, "3");

		log.info("TC_01_Step_11: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		verifyEquals(transferInVCB.getDynamicTextView(driver, "com.VCB:id/tvContent"), "Chuyển tiền không thành công. Số tiền giao dịch nhỏ hơn hạn mức 10,000 VND/1 lần, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp");

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
		transferInVCB.inputToDynamicInputBox(driver, distanceAccount.account, TittleData.INPUT_ACCOUNT_BENEFICI);

		log.info("TC_01_Step_09: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, Integer.parseInt(inputInfo.maxTran) + 1 + "", TittleData.AMOUNT);

		log.info("TC_01_Step_10: Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, TittleData.TRANSFER_INFO, "3");

		log.info("TC_01_Step_11: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		verifyEquals(transferInVCB.getDynamicTextView(driver, "com.VCB:id/tvContent"), "Chuyển tiền không thành công. Số tiền giao dịch lớn hơn hạn mức 1,000,000 VND/1 lần, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp.");

		transferInVCB.clickToDynamicContinue(driver, "com.VCB:id/btOK");

		transferInVCB.clickToDynamicBackIcon(driver, TittleData.TRANSFER_IN_VCBANK);

		homePage.clickToDynamicIcon(driver, Home_Text_Elements.HOME_TRANSFER_IN_VCB);

	}

	@Parameters({ "otp" })
	@Test
	public void TC_03_ChuyenTienTuongLaiVuotQuaHanMucTrongNgay(String otp) throws InterruptedException {

//		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
//
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);
//
//		log.info("TC_04_Step_08: Nhap tai khoan nhan");
//		transferInVCB.inputToDynamicInputBox(driver, distanceAccount.account, TittleData.INPUT_ACCOUNT_BENEFICI);
//
//		log.info("TC_01_Step_09: Nhap so tien chuyen");
//		transferInVCB.inputToDynamicInputBox(driver, inputInfo.maxTran, TittleData.AMOUNT);
//
//		log.info("TC_01_Step_10: Nhap noi dung");
//		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, TittleData.TRANSFER_INFO, "3");
//
//		log.info("TC_01_Step_11: Click tiep tuc");
//		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);
//
//		log.info("TC_01_Step_20: Chon Phuong thuc nhap");
//		transferInVCB.clickToDynamicDropDown(driver, TittleData.METHOD_VALIDATE);
//
//		log.info("TC_01_Step_21: Chon SMS OTP");
//		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TittleData.SMS_OTP);
//
//		log.info("TC_01_Step_22: Click Tiep tuc");
//		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);
//
//		log.info("TC_01_Step_23: Nhap OTP");
//		transferInVCB.inputToDynamicOtp(driver, otp, TittleData.CONTINUE_BTN);
//
//		log.info("TC_01_Step_24: Click tiep tuc");
//		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);
//
//		log.info("TC_01_Step_25: Kiem  tra giao dich thanh cong");
//		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE));
//		log.info("TC_01_Step_31: Click thuc hien giao dich moi");
//		transferInVCB.clickToDynamicButton(driver, TittleData.NEW_TRANSFER);
//
//		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);
//		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_01_Step_08: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, distanceAccount.account, TittleData.INPUT_ACCOUNT_BENEFICI);

		log.info("TC_01_Step_09: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, inputInfo.maxTran, TittleData.AMOUNT);

		log.info("TC_01_Step_10: Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, TittleData.TRANSFER_INFO, "3");

		log.info("TC_01_Step_11: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		verifyEquals(transferInVCB.getDynamicTextView(driver, "com.VCB:id/tvContent"), "Chuyển tiền không thành công. Số tiền giao dịch lớn hơn hạn mức 1,500,000 VND/1 ngày, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp.");

		transferInVCB.clickToDynamicContinue(driver, "com.VCB:id/btOK");

		transferInVCB.clickToDynamicBackIcon(driver, TittleData.TRANSFER_IN_VCBANK);

		homePage.clickToDynamicIcon(driver, Home_Text_Elements.HOME_TRANSFER_IN_VCB);

		loginWeb.resetAssignServicesLimit(driverWeb, "Chuyển khoản nội bộ cùng chủ tài khoản");

	}

	// Truoc khi chay case nay can set Up Nhom dich vu vs han muc la 99.999.999 VND
	public void TC_04_ChuyenTienTuongLaiVuotQuaNhomDichVu() throws InterruptedException {
		loginWeb.Setup_Assign_Services_Type_Limit(driverWeb, "Chuyển khoản cùng chủ", "40000000");

		transferInVCB.clickToDynamicBackIcon(driver, TittleData.TRANSFER_IN_VCBANK);

		homePage.clickToDynamicIcon(driver, Home_Text_Elements.HOME_TRANSFER_IN_VCB);

		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);

		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_04_Step_08: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, distanceAccount.account, TittleData.INPUT_ACCOUNT_BENEFICI);

		log.info("TC_04_Step_09: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY, TittleData.AMOUNT);

		log.info("TC_04_Step_10: Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, TittleData.TRANSFER_INFO, "3");

		log.info("TC_04_Step_11: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);
		
		verifyEquals(transferInVCB.getDynamicTextView(driver, "com.VCB:id/tvContent"), "Chuyển tiền không thành công. Số tiền giao dịch lớn hơn hạn mức 1,500,000 VND/1 ngày, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp.");

		transferInVCB.clickToDynamicContinue(driver, "com.VCB:id/btOK");

		transferInVCB.clickToDynamicBackIcon(driver, TittleData.TRANSFER_IN_VCBANK);

		homePage.clickToDynamicIcon(driver, Home_Text_Elements.HOME_TRANSFER_IN_VCB);
		
		loginWeb.Setup_Assign_Services_Type_Limit(driverWeb, "Chuyển khoản cùng chủ", "40000000");


	}

	// Truoc khi chay case nay can set Up Goi dich vu vs han muc la 99.999.999 VND
	@Test
	public void TC_05_ChuyenTienTuongLaiVuotQuaGoiDichVu() throws InterruptedException {
		loginWeb.Setup_Add_Method_Package_Total_Limit(driverWeb, "PKG1", "Method Otp");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);

		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_04_Step_08: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, distanceAccount.account, TittleData.INPUT_ACCOUNT_BENEFICI);

		log.info("TC_05_Step_09: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, Integer.parseInt(Constants.AMOUNT_DEFAULT_MIN_PACKAGE) + 1 + "", TittleData.AMOUNT);

		log.info("TC_05_Step_10: Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, TittleData.TRANSFER_INFO, "3");

		log.info("TC_05_Step_11: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		verifyTrue(transferInVCB.getDynamicTextView(driver, "com.VCB:id/tvContent").contains("Chuyển tiền không thành công. Số tiền giao dịch lớn hơn hạn mức"));

		transferInVCB.clickToDynamicContinue(driver, "com.VCB:id/btOK");

		loginWeb.Reset_Package_Total_Limit(driverWeb, "PKG1", "Method Otp");


	}

	@AfterClass(alwaysRun = true)

	public void afterClass() {
		closeApp();
		service.stop();
	}

}
