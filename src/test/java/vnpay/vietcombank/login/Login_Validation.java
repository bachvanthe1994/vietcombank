package vnpay.vietcombank.login;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import vietcombank_test_data.HomePage_Data;
import vietcombank_test_data.LogIn_Data;

public class Login_Validation extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;


	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities,
			String appPackage, String appName) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver= openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);

		login = PageFactoryManager.getLoginPageObject(driver);
		log.info("Before class: Click Allow Button");
		login.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");
		

	}

	@Test
	public void TC_01_KiemTraMacDinhTextBoxNhapMatKhau(){
		log.info("TC_01_Step_01: Kiem tra chao mung quy khach hien thi");
		verifyTrue(login.isDynamicMessageAndLabelTextDisplayed(driver, LogIn_Data.Message.WELCOME_MESSAGE));
		
		log.info("TC_01_Step_02: Kiem tra textbox dien so dien thoai hien thi");
		verifyTrue(login.isDynamicTextInInputBoxDisPlayed(driver, LogIn_Data.UI.PHONE_NUMBER));
		
		log.info("TC_01_Step_03: Kiem tra Dang ky truc tuyen hien thi");
		verifyTrue(login.isDynamicMessageAndLabelTextDisplayed(driver, LogIn_Data.UI.ONLINE_REGISTER));
		
		log.info("TC_01_Step_04: Kiem tra Đăng ký hiển thị");
		verifyTrue(login.isDynamicMessageAndLabelTextDisplayed(driver, LogIn_Data.UI.REGISTER));
		
		log.info("TC_01_Step_05: Dien so dien thoai");
		login.inputToDynamicInputBox(driver, LogIn_Data.Login_Account.PHONE, LogIn_Data.UI.PHONE_NUMBER);
		
		log.info("TC_01_Step_06: Click tiep tuc");
		login.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_01_Step_07: Kiểm tra message yeu cau nhap mat khau");
		verifyTrue(login.isDynamicMessageAndLabelTextDisplayed(driver, LogIn_Data.Message.INPUT_PASSWORD_MESSAGE));
		
		log.info("TC_01_Step_08: Kiểm tra title nhap mat khau hien thi");
		verifyTrue(login.isDynamicTextInInputBoxDisPlayed(driver, LogIn_Data.UI.PASSWORD_LABEL));
		
		log.info("TC_01_Step_09: Kiem tra quen mat khau hien thi");
		verifyTrue(login.isDynamicMessageAndLabelTextDisplayed(driver, LogIn_Data.UI.FORGOT_PASSWORD));
		
	
		
	}

	@Test
	public void TC_02_KiemTraKhiNhapKyTuTrong(){
		
		log.info("TC_02_Step_01: Bo trong Mat khau");
		login.inputToDynamicInputBox(driver, LogIn_Data.Login_Account.NULL, LogIn_Data.UI.PASSWORD_LABEL);
		
		log.info("TC_02_Step_02: Click tiep tuc");
		login.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_02_Step_03: Kiem tra pop-up yeu cau nhap mat khau hien thi");
		verifyTrue(login.isDynamicMessageAndLabelTextDisplayed(driver, LogIn_Data.Message.EMPTY_MESSAGE));
		
		log.info("TC_02_Step_04: Click Dong");
		login.clickToDynamicButton(driver, "Đóng");
	}
	@Test
	public void TC_03_KiemTraLoaiKyTuNhap(){
		
		log.info("TC_03_Step_01: Dien mat khau khong dung dinh dang");
		login.inputToDynamicInputBox(driver, LogIn_Data.Login_Account.INVALID_PASSWORD, LogIn_Data.UI.PASSWORD_LABEL);
		
		log.info("TC_03_Step_02: Kiem tra mat khau do khong duoc nhap");
		verifyTrue(login.getDynamicTextInInputBox(driver,LogIn_Data.UI.PASSWORD_LABEL).equals(LogIn_Data.UI.PASSWORD_LABEL));
		
	}

	@Test
	public void TC_04_KiemTraSoLuongKyTuToiDa(){
		
		log.info("TC_04_Step_01: Click Quay Lai");
		login.clickToDynamicButtonLinkOrLinkText(driver, "Quay lại");
		
		log.info("TC_04_Step_02: Click Tiep Tuc");
		login.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_04_Step_03: Dien mat khau dai hon 20 ky tu  ");
		login.inputToDynamicInputBox(driver, LogIn_Data.Login_Account.PASSWORD_GREATER_THAN_20, LogIn_Data.UI.PASSWORD_LABEL);
		
		log.info("TC_04_Step_04: Kiem tra mat khau do khong duoc nhap");
		verifyTrue(!login.getDynamicTextInInputBox(driver,LogIn_Data.UI.PASSWORD_LABEL).equals(LogIn_Data.Login_Account.PASSWORD_GREATER_THAN_20));
		
	}
	@Test
	public void TC_05_NhapSaiMatKhau(){
		
		log.info("TC_05_Step_01: Click Quay lai");
		login.clickToDynamicButtonLinkOrLinkText(driver, "Quay lại");
		
		log.info("TC_05_Step_02: Click Tiep tuc");
		login.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_05_Step_03: Dien Mat Khau sai");
		login.inputToDynamicInputBox(driver, LogIn_Data.Login_Account.WRONG_PASSWORD, LogIn_Data.UI.PASSWORD_LABEL);
		
		log.info("TC_05_Step_04: Click Tiep Tuc");
		login.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_05_Step_05:Kiem tra pop up sai mat khau hien thi");
		verifyTrue(login.isDynamicMessageAndLabelTextDisplayed(driver, LogIn_Data.Message.WRONG_PASSWORD_MESSAGE));
		
		log.info("TC_05_Step_06: Click Dong");
		login.clickToDynamicButton(driver, "Đóng");
		
	}
	@Test
	public void TC_06_KiemTraTaiKhoanBiKhoa(){
		
		log.info("TC_06_Step_01: Click quay lai");
		login.clickToDynamicButtonLinkOrLinkText(driver, "Quay lại");
		
		log.info("TC_06_Step_02: Dien so dien thoai dung");
		login.inputToDynamicLogInTextBox(driver, LogIn_Data.Login_Account.LOCKED_PHONE, "Tiếp tục");
		
		log.info("TC_06_Step_03: Click Tiep tuc");
		login.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_06_Step_04: Kiem tra pop-up tai khoan da bi khoa hien thi");
		verifyTrue(login.isDynamicMessageAndLabelTextDisplayed(driver, LogIn_Data.Message.LOCKED_ACCOUNT_MESSAGE));
		
		log.info("TC_06_Step_05: Click Dong ");
		login.clickToDynamicButton(driver, "Đóng");
		
	}

	@Test
	public void TC_07_DangNhapTaiKhoanThanhCong(){
		
		log.info("TC_07_Step_01: Dien so dien thoai dung");
		login.inputToDynamicLogInTextBox(driver, LogIn_Data.Login_Account.PHONE, "Tiếp tục");
		
		log.info("TC_07_Step_02: Click Tiep tuc");
		login.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_05_Step_03: Dien password dung");
		login.inputToDynamicInputBox(driver, LogIn_Data.Login_Account.NEW_PASSWORD, LogIn_Data.UI.PASSWORD_LABEL);
		
		log.info("TC_07_Step_04: Click tiep tuc");
		login.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_07_Step_05: Dien OTP");
		login.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");
		
		log.info("TC_07_Step_06: Click tiep tuc");
		login.clickToDynamicButton(driver, "Tiếp tục");
		//URD update remove 2 popups 'phuong thuc xac thuc OTP' va 'Kich hoat tinh nang moi'
		
	}
	
	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
