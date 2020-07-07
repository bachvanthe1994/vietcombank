package vnpay.vietcombank.saving_online;

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
import model.SavingOnlineInfo;
import model.ServiceLimitInfo;
import model.SourceAccountModel;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.SavingOnlinePageObject;
import pageObjects.WebBackendSetupPageObject;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.SavingOnline_Data;

public class Limit_Settlement_Saving_Online extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private SavingOnlinePageObject savingOnline;
	long transferFee = 0;
	double transferFeeCurrentcy = 0;
	SourceAccountModel sourceAccount = new SourceAccountModel();
	SourceAccountModel distanceAccount = new SourceAccountModel();
	String savingAccount;

	WebDriver driverWeb;
	private WebBackendSetupPageObject loginWeb;

	SavingOnlineInfo info = new SavingOnlineInfo(Account_Data.Valid_Account.ACCOUNT2, "1 tháng", "100000", "Lãi nhập gốc");
	ServiceLimitInfo inputInfoMin = new ServiceLimitInfo("1000", Integer.parseInt(info.money) + 1 + "", Integer.parseInt(info.money) + 2 + "", "100000000000");
	ServiceLimitInfo inputInfoMax = new ServiceLimitInfo("1000", Integer.parseInt(info.money) - 2 + "", Integer.parseInt(info.money) - 1 + "", "100000000000");
	ServiceLimitInfo inputInfo = new ServiceLimitInfo("1000", "100", "1000000000", "100000000000");

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp", "username", "passWeb" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt, String username, String passWeb) throws IOException, InterruptedException, GeneralSecurityException {
		log.info("Before class: Mo backend ");
		startServer();
		driverWeb = openMultiBrowser(Constants.BE_BROWSER_CHROME, Constants.BE_BROWSER_VERSION, Constants.BE_URL);
		loginWeb = WebPageFactoryManager.getWebBackendSetupPageObject(driverWeb);
		loginWeb.Login_Web_Backend(driverWeb, username, passWeb);

		loginWeb.setupAssignServicesLimit(driverWeb, SavingOnline_Data.EXPIRE_SAVING_ACCOUNT_ONLINE, inputInfo, Constants.BE_CODE_PACKAGE);

		log.info("Before class: Mo app ");
		if (deviceType.contains("android")) {
			driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		} else if (deviceType.contains("ios")) {
			driver = openIOSApp(deviceName, udid, url);
		}
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
		homePage = PageFactoryManager.getHomePageObject(driver);
		savingOnline = PageFactoryManager.getSavingOnlinePageObject(driver);

	}

	@Test
	public void TC_00_Mo_Tai_Khoan_Tiet_Kiem() {
		log.info("TC_01_1_Click Mo tai khoan tiet kiem");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, SavingOnline_Data.OPEN_SAVING_ACCOUNT);

		log.info("TC_01_2_Chon so tai khoan");
		savingOnline.clickToDynamicDropDown(driver, SavingOnline_Data.ACCOUNT_NUMBER);
		sourceAccount = savingOnline.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		log.info("TC_01_3_Chon ky han gui");
		savingOnline.clickToDynamicDropDownListTextViewByHeader(driver, SavingOnline_Data.TRANSACTION_INFO, "1");
		savingOnline.clickToDynamicTextContains(driver, info.term);

		log.info("TC_01_4_Nhap so tien gui");
		savingOnline.inputToDynamicInputBoxByHeader(driver, info.money, SavingOnline_Data.TRANSACTION_INFO, "2");

		log.info("TC_01_5_Chon hinh thuc chuyen tien");
		savingOnline.clickToDynamicDropDownListTextViewByHeader(driver, SavingOnline_Data.TRANSACTION_INFO, "3");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, info.formOfPayment);

		log.info("TC_01_6_Chon dong y tuan thu cam ket");
		savingOnline.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");

		log.info("TC_01_7_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, SavingOnline_Data.CONTINUE_BUTTON);

		log.info("TC_01_09_Chon phuong thuc xac thuc");
		savingOnline.scrollDownToText(driver, SavingOnline_Data.ACCURACY_METHOD);
		savingOnline.clickToDynamicDropDown(driver, SavingOnline_Data.ACCURACY_METHOD);
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, SavingOnline_Data.SMS_OTP);

		log.info("TC_01_10_Kiem tra so tien phi");
		transferFee = 0;

		log.info("TC_01_11_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, SavingOnline_Data.CONTINUE_BUTTON);

		savingOnline.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, SavingOnline_Data.CONTINUE_BUTTON);

		savingOnline.clickToDynamicButton(driver, SavingOnline_Data.CONTINUE_BUTTON);

		log.info("TC_01_12_Kiem tra man hinh Giao dich thanh cong");
		log.info("TC_01_12_1_Kiem tra Giao dich thanh cong");
		verifyTrue(savingOnline.isDynamicMessageAndLabelTextDisplayed(driver, SavingOnline_Data.SUCCESS_TRANSACTION));

		log.info("TC_01_12_6_Lay ma giao dich");
		savingAccount = savingOnline.getDynamicTextInTransactionDetail(driver, SavingOnline_Data.SAVING_NUMBER);

		log.info("TC_01_13_Click Thuc hien giao dich moi");
		savingOnline.clickToDynamicButton(driver, SavingOnline_Data.NEW_TRANSACTION_PERFORM);
		savingOnline.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

	}

	@Test
	public void TC_01_SoTienGiaoDichNhoHonHanMucToiThieuMotLanGiaoDich() throws InterruptedException {
		log.info("TC_01_1_Click Mo tai khoan tiet kiem");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, SavingOnline_Data.EXPIRE_SAVING_ACCOUNT);
		savingOnline.clickToDynamicDropDown(driver, SavingOnline_Data.SAVING_NUMBER_ACCOUNT);
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, savingAccount);
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, SavingOnline_Data.CHOICE_ACCOUNT_DISTANCE);

		loginWeb.setupAssignServicesLimit(driverWeb, SavingOnline_Data.EXPIRE_SAVING_ACCOUNT_ONLINE, inputInfoMin, Constants.BE_CODE_PACKAGE);

		distanceAccount = savingOnline.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		savingOnline.clickToDynamicButton(driver, SavingOnline_Data.CONTINUE_BUTTON);

		verifyEquals(savingOnline.getDynamicTextView(driver, "com.VCB:id/tvContent"), "Tất toán không thành công. Số tiền giao dịch nhỏ hơn hạn mức " + addCommasToLong(Integer.parseInt(info.money) + 1 + "") + " VND/1 lần, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline của Vietcombank để được trợ giúp.");
		savingOnline.clickToDynamicContinue(driver, "com.VCB:id/btOK");

	}

	@Test
	public void TC_02_SoTienGiaoDichLonHonHanMucToiThieuCuaNhomGiaoDich() throws InterruptedException {
		loginWeb.setupAssignServicesLimit(driverWeb, SavingOnline_Data.EXPIRE_SAVING_ACCOUNT_ONLINE, inputInfoMax, Constants.BE_CODE_PACKAGE);
		savingOnline.clickToDynamicButton(driver, SavingOnline_Data.CONTINUE_BUTTON);

		verifyEquals(savingOnline.getDynamicTextView(driver, "com.VCB:id/tvContent"), "Tất toán không thành công. Số tiền giao dịch lớn hơn hạn mức " + addCommasToLong(Integer.parseInt(info.money) - 1 + "") + " VND/1 lần, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp.");
		savingOnline.clickToDynamicContinue(driver, "com.VCB:id/btOK");

		loginWeb.setupAssignServicesLimit(driverWeb, SavingOnline_Data.EXPIRE_SAVING_ACCOUNT_ONLINE, inputInfo, Constants.BE_CODE_PACKAGE);

	}

	@Test
	public void TC_03_SoTienGiaoDichLonHonHanMucToiThieuCuaNhomGiaoDich() throws InterruptedException {

		loginWeb.Setup_Assign_Services_Type_Limit(driverWeb, Constants.BE_CODE_PACKAGE, SavingOnline_Data.SAVING_ONLINE, Integer.parseInt(info.money) - 1000 + "");
		savingOnline.clickToDynamicButton(driver, SavingOnline_Data.CONTINUE_BUTTON);

		verifyEquals(savingOnline.getDynamicTextView(driver, "com.VCB:id/tvContent"), "Tất toán không thành công. Số tiền giao dịch lớn hơn hạn mức " + addCommasToLong(Integer.parseInt(info.money) - 1000 + "") + " VND/1 ngày của nhóm dịch vụ, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp.");

		loginWeb.Reset_Setup_Assign_Services_Type_Limit(driverWeb, Constants.BE_CODE_PACKAGE, SavingOnline_Data.SAVING_ONLINE);

		savingOnline.clickToDynamicContinue(driver, "com.VCB:id/btOK");

	}

	@Test
	public void TC_04_SoTienGiaoDichLonHonHanMucToiThieuCuaGoiGiaoDich() throws InterruptedException {
		loginWeb.Setup_Add_Method_Package_Total_Limit(driverWeb, Constants.BE_CODE_PACKAGE, Constants.METHOD_OTP, Integer.parseInt(info.money) - 1000 + "");
		savingOnline.clickToDynamicButton(driver, SavingOnline_Data.CONTINUE_BUTTON);

		verifyEquals(savingOnline.getDynamicTextView(driver, "com.VCB:id/tvContent"), "Tất toán không thành công. Số tiền giao dịch lớn hơn hạn mức " + addCommasToLong(Integer.parseInt(info.money) - 1000 + "") + " VND/1 ngày của gói dịch vụ, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp.");
		savingOnline.clickToDynamicContinue(driver, "com.VCB:id/btOK");

		loginWeb.setupAssignServicesLimit(driverWeb, SavingOnline_Data.EXPIRE_SAVING_ACCOUNT_ONLINE, inputInfo, Constants.BE_CODE_PACKAGE);

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
//		service.stop();
	}

}
