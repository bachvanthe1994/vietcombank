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

public class Validation_Name_Charity extends Base {
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

		String actualName = transferMoneyCharity.getTextDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "2");
		log.info("TC_01_2_Kiem tra gia tri trong o Ten nguoi ung ho");
		verifyEquals(actualName, "Tên người ủng hộ");
	}
	
	@Test
	public void TC_02_NhapTenNguoiUngHo() {
		log.info("TC_02_1_Nhap ten nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBoxByHeader(driver, "Do Minh Duc", "Thông tin giao dịch", "2");
		
		
		String actualName = transferMoneyCharity.getTextDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "2");
		log.info("TC_02_2_Kiem tra gia tri trong o Ten nguoi ung ho");
		verifyEquals(actualName, "Do Minh Duc");
	}
	
	@Test
	public void TC_03_KiemTraLoaiKyTuNhap() {
		log.info("TC_03_1_Nhap ten nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBoxByHeader(driver, "Do Minh Duc", "Thông tin giao dịch", "2");
		
		
		String actualName = transferMoneyCharity.getTextDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "2");
		log.info("TC_03_2_Kiem tra gia tri trong o Ten nguoi ung ho");
		verifyEquals(actualName, "Do Minh Duc");
	}
	
	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
