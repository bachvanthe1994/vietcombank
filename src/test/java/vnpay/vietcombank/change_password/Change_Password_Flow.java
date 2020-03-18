package vnpay.vietcombank.change_password;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import pageObjects.ChangePasswordPageObject;
import pageObjects.LogInPageObject;
import vietcombank_test_data.ChangePassword_Data;


public class Change_Password_Flow extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private ChangePasswordPageObject changePassword;
	String passLogin = "";
	

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		if (deviceType.contains("android")) {
			driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		} else if (deviceType.contains("ios")) {
			driver = openIOSApp(deviceName, udid, url);
		}
		changePassword = PageFactoryManager.getChangePasswordPageObject(driver);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
	passLogin = pass;
	}

	@Test
	public void TC_01_DoiMatKhauThanhCong() {
		log.info("TC_01_Step: Click menu header");
		changePassword.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");
		
		log.info("TC_01_Step: Click cai dat");
		changePassword.clickToDynamicButtonLinkOrLinkText(driver, "Cài đặt");
		
		log.info("TC_01_Step: Click doi mat khau");
		changePassword.clickToDynamicButtonLinkOrLinkText(driver, "Đổi mật khẩu");
		
		log.info("TC_01_Step: Nhap mat khau cu");
		changePassword.inputIntoEditTextByID(driver,passLogin, "com.VCB:id/oldPass");
		
		log.info("TC_01_Step: Nhap mat khau moi");
		changePassword.inputIntoEditTextByID(driver,ChangePassword_Data.Password.PASS_NEW, "com.VCB:id/newPass");
		
		log.info("TC_01_Step: Nhap mat lại khau moi");
		changePassword.inputIntoEditTextByID(driver,ChangePassword_Data.Password.PASS_NEW, "com.VCB:id/renewPass");
		
		log.info("TC_01_Step: Click button Xac nhan");
		changePassword.clickToDynamicButton(driver, "Xác nhận");
		
		log.info("TC_01_Step: verrify message");
		verifyEquals(ChangePassword_Data.Message.CHANGE_PASSWORD_SUCCESS, "Quý khách đổi mật khẩu thành công. Vui lòng đăng nhập lại để sử dụng dịch vụ");
		
		log.info("TC_01_Step: Click button dong message");
		changePassword.clickToDynamicButton(driver, "Đăng nhập");
		
		changePassword.inputIntoEditTextByID(driver, ChangePassword_Data.Password.PASS_NEW, "com.VCB:id/edtInput");

		changePassword.clickToDynamicButton(driver, "Đăng nhập");

		log.info("TC_01_Step: verrify dang nhap thanh cong");
		verifyEquals(changePassword.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvLabel"), "TÀI KHOẢN THANH TOÁN");	
	}
	
	@Test
	public void TC_02_DoiLaiMatKhauThanhCong() {
		log.info("TC_02_Step: Click menu header");
		changePassword.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");
		
		log.info("TC_02_Step: Click cai dat");
		changePassword.clickToDynamicButtonLinkOrLinkText(driver, "Cài đặt");
		
		log.info("TC_02_Step: Click doi mat khau");
		changePassword.clickToDynamicButtonLinkOrLinkText(driver, "Đổi mật khẩu");
		
		log.info("TC_02_Step: Nhap mat khau cu");
		changePassword.inputIntoEditTextByID(driver,ChangePassword_Data.Password.PASS_NEW, "com.VCB:id/oldPass");
		
		log.info("TC_02_Step: Nhap mat khau moi");
		changePassword.inputIntoEditTextByID(driver,passLogin, "com.VCB:id/newPass");
		
		log.info("TC_02_Step: Nhap mat lại khau moi");
		changePassword.inputIntoEditTextByID(driver,passLogin, "com.VCB:id/renewPass");
		
		log.info("TC_02_Step: Click button Xac nhan");
		changePassword.clickToDynamicButton(driver, "Xác nhận");
		
		log.info("TC_02_Step: verrify message");
		verifyEquals(ChangePassword_Data.Message.CHANGE_PASSWORD_SUCCESS, "Quý khách đổi mật khẩu thành công. Vui lòng đăng nhập lại để sử dụng dịch vụ");
		
		log.info("TC_02_Step: Click button dong message");
		changePassword.clickToDynamicButton(driver, "Đăng nhập");
		
		changePassword.inputIntoEditTextByID(driver, passLogin, "com.VCB:id/edtInput");

		changePassword.clickToDynamicButton(driver, "Đăng nhập");

		log.info("TC_01_Step: verrify dang nhap thanh cong");
		verifyEquals(changePassword.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvLabel"), "TÀI KHOẢN THANH TOÁN");
		
	}
	}
