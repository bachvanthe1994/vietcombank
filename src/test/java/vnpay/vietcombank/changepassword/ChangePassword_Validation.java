package vnpay.vietcombank.changepassword;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import pageObjects.LogInPageObject;
import vietcombank_test_data.ChangePassword_Data;
import vietcombank_test_data.LogIn_Data;

public class ChangePassword_Validation extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);

		login = PageFactoryManager.getLoginPageObject(driver);

		login.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");

		login.inputPhoneNumber(ChangePassword_Data.Password.PHONE);

		login.clickToDynamicButton(driver, LogIn_Data.UI.CONTINUE_BUTTON);
	}

	@Test
	public void TC_01_KiemTraManHinhDoiMatKhau() {
		System.out.println("Start");

		log.info("TC_01_1_Text Kiem tra hien thi man hinh doi mat khau");
		verifyTrue(login.isDynamicMessageAndLabelTextDisplayed(driver, ChangePassword_Data.UI.RETURN_BUTTON));
		verifyTrue(login.isDynamicMessageAndLabelTextDisplayed(driver, ChangePassword_Data.UI.CHANGE_PASSWORD_NOFICATION));
		verifyTrue(login.isDynamicTextInInputBoxDisPlayed(driver, ChangePassword_Data.UI.DEFAULT_PASSWORD_TEXT_VIEW));
		verifyTrue(login.isDynamicTextInInputBoxDisPlayed(driver, ChangePassword_Data.UI.NEW_PASSWORD_TEXT_VIEW));
		verifyTrue(login.isDynamicTextInInputBoxDisPlayed(driver, ChangePassword_Data.UI.CONFIRM_PASSWORD_TEXT_VIEW));
		verifyTrue(login.isDynamicButtonDisplayed(driver, LogIn_Data.UI.CONTINUE_BUTTON));
		verifyTrue(login.isDynamicMessageAndLabelTextDisplayed(driver, ChangePassword_Data.UI.FORGOT_PASSWORD));

	}

	@Test
	public void TC_02_KiemTraTextboxMatKhauMacDinh() {
		System.out.println("Start");

		log.info("TC_02_1_Text Kiem tra Mac dinh trong, hien thi Nhap mat khau mac dinh");
		verifyTrue(login.isDynamicTextInInputBoxDisPlayed(driver, ChangePassword_Data.UI.DEFAULT_PASSWORD_TEXT_VIEW));

		log.info("TC_02_2_Text Kiem tra cho phep nhap ky tu 0-9, a-z");
		login.inputPassword("0123456789", ChangePassword_Data.UI.DEFAULT_PASSWORD_TEXT_VIEW, ChangePassword_Data.UI.DEFAULT_PASSWORD_TEXT_VIEW);
		login.verifyDefaultPasswordTextboxAllowInputValue("0123456789");
		login.inputPassword("abc", "0123456789", ChangePassword_Data.UI.DEFAULT_PASSWORD_TEXT_VIEW);
		login.verifyDefaultPasswordTextboxAllowInputValue("abc");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();
	}

}
