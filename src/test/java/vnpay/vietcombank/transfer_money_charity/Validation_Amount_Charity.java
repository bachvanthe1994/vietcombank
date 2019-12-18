package vnpay.vietcombank.transfer_money_charity;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import model.TransferCharity;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.TransferMoneyCharityPageObject;
import vietcombank_test_data.LogIn_Data;

public class Validation_Amount_Charity extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferMoneyCharityPageObject transferMoneyCharity;

	TransferCharity info = new TransferCharity("0010000000322", "Test order", "1000000", "Do Minh Duc", "So 18 ngo 3 Thai Ha", "Ho ngheo", "Mật khẩu đăng nhập");
	TransferCharity info1 = new TransferCharity("0011140000647", "Test order", "10", "Do Minh Duc", "So 18 ngo 3 Thai Ha", "Ho ngheo", "Mật khẩu đăng nhập");
	TransferCharity info2 = new TransferCharity("0010000000322", "Test order", "1000000", "Do Minh Duc", "So 18 ngo 3 Thai Ha", "Ho ngheo", "SMS OTP");
	TransferCharity info3 = new TransferCharity("0011140000647", "Test order", "10", "Do Minh Duc", "So 18 ngo 3 Thai Ha", "Ho ngheo", "SMS OTP");

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName" })

	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);

		login = PageFactoryManager.getLoginPageObject(driver);

		login.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");

		login.inputToDynamicLogInTextBox(driver, LogIn_Data.Login_Account.PHONE, "Tiếp tục");

		login.clickToDynamicButton(driver, "Tiếp tục");

		login.inputToDynamicInputBox(driver, LogIn_Data.Login_Account.NEW_PASSWORD, LogIn_Data.UI.PASSWORD_LABEL);

		login.clickToDynamicButton(driver, "Tiếp tục");

		login.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		login.clickToDynamicButton(driver, "Tiếp tục");

		login.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");
	}

	@Test
	public void TC_01_KiemTraHienThiMacDinh() {
		homePage = PageFactoryManager.getHomePageObject(driver);
		transferMoneyCharity = PageFactoryManager.getTransferMoneyCharityPageObject(driver);

		log.info("TC_01_1_Click Chuyen tien tu thien");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền từ thiện");

		String actualAmountMoney = transferMoneyCharity.getTextDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1");
		log.info("TC_01_2_Kiem tra gia tri trong o So tien ung ho");
		verifyEquals(actualAmountMoney, "Số tiền ủng hộ");
	}
	
	@Test
	public void TC_02_KiemTraNhapSoTienBang0() {
//		log.info("TC_02_1_Nhap so 0 vao o So tien ung ho");
//		transferMoneyCharity.inputToDynamicInputBoxByHeader(driver, "000", "Thông tin giao dịch", "1");
//
//		String actualAmountMoney = transferMoneyCharity.getTextDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1");
//		log.info("TC_02_2_Kiem tra gia tri trong o So tien ung ho");
//		verifyEquals(actualAmountMoney, "Số tiền ủng hộ");
	}

	@Test
	public void TC_03_KiemTraLoaiKyTuNhapSoTien() {
		String actualAmountMoney;
		log.info("TC_03_1_Chon tai khoan nguon VND");
		transferMoneyCharity.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);
		
		log.info("TC_03_2_Nhap ki tu so, chu, ki tu dac biet, dau cham vao o So tien ung ho");
		
		log.info("TC_03_2_1_Nhap ki tu so");
		transferMoneyCharity.inputToDynamicInputBoxByHeader(driver, "123", "Thông tin giao dịch", "1");
		
		actualAmountMoney = transferMoneyCharity.getTextDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1");
		log.info("TC_03_2_2_Kiem tra gia tri trong o So tien ung ho");
		verifyEquals(actualAmountMoney, "123");
		
//		log.info("TC_03_2_3_Nhap dau cham");
//		transferMoneyCharity.inputToDynamicInputBoxByHeader(driver, ".", "Thông tin giao dịch", "1");
//
//		actualAmountMoney = transferMoneyCharity.getTextDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1");
//		log.info("TC_03_2_4_Kiem tra gia tri trong o So tien ung ho");
//		verifyEquals(actualAmountMoney, "Số tiền ủng hộ");
		
		log.info("TC_03_4_Chon tai khoan nguon Ngoai te");
		transferMoneyCharity.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info1.sourceAccount);
		
		log.info("TC_03_5_Nhap ki tu so, dau cham");
		transferMoneyCharity.inputToDynamicInputBoxByHeader(driver, "5.5", "Thông tin giao dịch", "1");
		
		actualAmountMoney = transferMoneyCharity.getTextDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1");
		log.info("TC_03_5_1_Kiem tra gia tri trong o So tien ung ho");
		verifyEquals(actualAmountMoney, "5.5");
	}
	
	@Test
	public void TC_04_KiemTraKiemTraGioiHanNhapSoTien() {
		String actualAmountMoney;
		log.info("TC_04_1_Chon tai khoan nguon VND");
		transferMoneyCharity.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);
		
		log.info("TC_04_2_Nhap ki tu so, chu, ki tu dac biet, dau cham vao o So tien ung ho");
		transferMoneyCharity.inputToDynamicInputBoxByHeader(driver, "1234567899", "Thông tin giao dịch", "1");
		
		actualAmountMoney = transferMoneyCharity.getTextDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1");
		verifyEquals(actualAmountMoney.replaceAll("\\D+","").length(), 10);
		
		log.info("TC_04_3_Chon tai khoan nguon Ngoai te");
		transferMoneyCharity.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info1.sourceAccount);
		
		log.info("TC_04_4_Nhap ki tu so, chu, ki tu dac biet, dau cham vao o So tien ung ho");
		transferMoneyCharity.inputToDynamicInputBoxByHeader(driver, "11111111111.11", "Thông tin giao dịch", "1");
	
		actualAmountMoney = transferMoneyCharity.getTextDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1");
		verifyEquals(actualAmountMoney.replaceAll("\\D+","").length(), 13);
	}
	
	@Test
	public void TC_05_KiemTraDinhDangSoTienNhap() {
		String actualAmountMoney;
		log.info("TC_05_1_Chon tai khoan nguon VND");
		transferMoneyCharity.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);
		
		log.info("TC_05_2_Nhap ki tu so, chu, ki tu dac biet, dau cham vao o So tien ung ho");
		transferMoneyCharity.inputToDynamicInputBoxByHeader(driver, "5000000", "Thông tin giao dịch", "1");
		
		actualAmountMoney = transferMoneyCharity.getTextDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1");
		verifyTrue(actualAmountMoney.contains(","));
	}
	
	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
