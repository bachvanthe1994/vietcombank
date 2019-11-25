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
import pageObjects.LogInPageObject;
import vietcombank_test_data.HomePage_Data;
import vietcombank_test_data.LogIn_Data;

public class Login_Validation extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;


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
		verifyTrue(login.isDynamicMessageAndLabelTextDisplayed(driver, LogIn_Data.Message.WELCOME_MESSAGE));
		
		verifyTrue(login.isDynamicTextInInputBoxDisPlayed(driver, LogIn_Data.UI.PHONE_NUMBER));
		verifyTrue(login.isDynamicMessageAndLabelTextDisplayed(driver, LogIn_Data.UI.ONLINE_REGISTER));
		verifyTrue(login.isDynamicMessageAndLabelTextDisplayed(driver, LogIn_Data.UI.REGISTER));
		login.inputToDynamicInputBox(driver, LogIn_Data.Login_Account.PHONE, LogIn_Data.UI.PHONE_NUMBER);
		login.clickToDynamicButton(driver, "Tiếp tục");
		verifyTrue(login.isDynamicMessageAndLabelTextDisplayed(driver, LogIn_Data.Message.INPUT_PASSWORD_MESSAGE));
		verifyTrue(login.isDynamicTextInInputBoxDisPlayed(driver, LogIn_Data.UI.PASSWORD_LABEL));
		verifyTrue(login.isDynamicMessageAndLabelTextDisplayed(driver, LogIn_Data.UI.FORGOT_PASSWORD));
		
	
		
	}

	@Test
	public void TC_02_KiemTraKhiNhapKyTuTrong(){
		login.inputToDynamicInputBox(driver, LogIn_Data.Login_Account.NULL, LogIn_Data.UI.PASSWORD_LABEL);
		login.clickToDynamicButton(driver, "Tiếp tục");
		verifyTrue(login.isDynamicMessageAndLabelTextDisplayed(driver, LogIn_Data.Message.EMPTY_MESSAGE));
		login.clickToDynamicButton(driver, "Đóng");
	}
	@Test
	public void TC_03_KiemTraLoaiKyTuNhap(){
		login.inputToDynamicInputBox(driver, LogIn_Data.Login_Account.INVALID_PASSWORD, LogIn_Data.UI.PASSWORD_LABEL);
		verifyTrue(login.getTextDynamicTextInInputBox(driver,LogIn_Data.UI.PASSWORD_LABEL).equals(LogIn_Data.UI.PASSWORD_LABEL));
		
	}

	@Test
	public void TC_04_KiemTraSoLuongKyTuToiDa(){
		login.clickToDynamicButionLinkOrLinkText(driver, "Quay lại");
		login.clickToDynamicButton(driver, "Tiếp tục");
		login.inputToDynamicInputBox(driver, LogIn_Data.Login_Account.PASSWORD_GREATER_THAN_20, LogIn_Data.UI.PASSWORD_LABEL);
		verifyTrue(!login.getTextDynamicTextInInputBox(driver,LogIn_Data.UI.PASSWORD_LABEL).equals(LogIn_Data.Login_Account.PASSWORD_GREATER_THAN_20));
		
	}
	@Test
	public void TC_05_NhapSaiMatKhau(){
		login.clickToDynamicButionLinkOrLinkText(driver, "Quay lại");
		login.clickToDynamicButton(driver, "Tiếp tục");
		login.inputToDynamicInputBox(driver, LogIn_Data.Login_Account.WRONG_PASSWORD, LogIn_Data.UI.PASSWORD_LABEL);
		login.clickToDynamicButton(driver, "Tiếp tục");
		verifyTrue(login.isDynamicMessageAndLabelTextDisplayed(driver, LogIn_Data.Message.WRONG_PASSWORD_MESSAGE));
		login.clickToDynamicButton(driver, "Đóng");
		
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
