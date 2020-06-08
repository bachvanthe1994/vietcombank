package vnpay.vietcombank.change_password;

import java.io.IOException;
import java.security.GeneralSecurityException;

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
	String passLogin,newPassword = "";

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException, GeneralSecurityException {
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
		newPassword = getDataInCell(13);
	}

	@Test
	public void TC_01_DoiMatKhauThanhCong() {
		
		log.info("TC_01_Step: Click menu header");
		changePassword.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_01_Step: Click cai dat");
		changePassword.clickToDynamicButtonLinkOrLinkText(driver, ChangePassword_Data.UI.SETTING);

		log.info("TC_01_Step: Scroll xuong phan doi mat khau");
		changePassword.scrollDownToText(driver, ChangePassword_Data.UI.SEARCHING);

		log.info("TC_01_Step: Click doi mat khau");
		changePassword.clickToDynamicButtonLinkOrLinkText(driver, ChangePassword_Data.UI.CHANGE_PASSWORD);

		log.info("TC_01_Step: Nhap mat khau cu");
		changePassword.inputIntoEditTextByID(driver, passLogin, "com.VCB:id/oldPass");

		log.info("TC_01_Step: Nhap mat khau moi");
		changePassword.inputIntoEditTextByID(driver, newPassword, "com.VCB:id/newPass");

		log.info("TC_01_Step: Nhap mat lại khau moi");
		changePassword.inputIntoEditTextByID(driver, newPassword, "com.VCB:id/renewPass");

		log.info("TC_01_Step: Click button Xac nhan");
		changePassword.clickToDynamicButton(driver, ChangePassword_Data.UI.CONFIRM);

		log.info("TC_01_Step: verrify message");

		verifyEquals(changePassword.getDynamicTextMessage(driver, ChangePassword_Data.UI.LOGIN_TEXT), ChangePassword_Data.Message.CHANGE_PASSWORD_SUCCESS);

		log.info("TC_01_Step: Click button dong message");
		changePassword.clickToDynamicButton(driver, ChangePassword_Data.UI.LOGIN_TEXT);

		changePassword.inputIntoEditTextByID(driver, newPassword, "com.VCB:id/edtInput");

		changePassword.clickToDynamicButton(driver, ChangePassword_Data.UI.LOGIN_TEXT);


		log.info("TC_01_Step: verrify dang nhap thanh cong");
		verifyEquals(changePassword.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvLabel"), ChangePassword_Data.UI.ACCOUNT_PAYMENT);
	}

	@Test
	public void TC_02_DoiLaiMatKhauThanhCong() {
		log.info("TC_02_Step: Click menu header");
		changePassword.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_02_Step: Click cai dat");
		changePassword.clickToDynamicButtonLinkOrLinkText(driver, ChangePassword_Data.UI.SETTING);

		log.info("TC_02_Step: Scroll xuong phan doi mat khau");
		changePassword.scrollDownToText(driver, ChangePassword_Data.UI.SEARCHING);

		log.info("TC_02_Step: Click doi mat khau");
		changePassword.clickToDynamicButtonLinkOrLinkText(driver, ChangePassword_Data.UI.CHANGE_PASSWORD);

		log.info("TC_02_Step: Nhap mat khau cu");
		changePassword.inputIntoEditTextByID(driver, newPassword, "com.VCB:id/oldPass");

		log.info("TC_02_Step: Nhap mat khau moi");
		changePassword.inputIntoEditTextByID(driver, passLogin, "com.VCB:id/newPass");

		log.info("TC_02_Step: Nhap mat lại khau moi");
		changePassword.inputIntoEditTextByID(driver, passLogin, "com.VCB:id/renewPass");

		log.info("TC_02_Step: Click button Xac nhan");
		changePassword.clickToDynamicButton(driver, ChangePassword_Data.UI.CONFIRM);

		log.info("TC_02_Step: Kiem tra pop-up thanh cong hien thi");
		verifyEquals(changePassword.getDynamicTextMessage(driver, ChangePassword_Data.UI.LOGIN_TEXT), ChangePassword_Data.Message.CHANGE_PASSWORD_SUCCESS);

		log.info("TC_02_Step: Click button Dang nhap");
		changePassword.clickToDynamicButton(driver, ChangePassword_Data.UI.LOGIN_TEXT);

		log.info("TC_02_Step: Click button dong message");
		changePassword.inputIntoEditTextByID(driver, passLogin, "com.VCB:id/edtInput");

		changePassword.clickToDynamicButton(driver, ChangePassword_Data.UI.LOGIN_TEXT);

		log.info("TC_01_Step: verrify dang nhap thanh cong");
		verifyEquals(changePassword.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvLabel"), ChangePassword_Data.UI.ACCOUNT_PAYMENT);

	}
}
