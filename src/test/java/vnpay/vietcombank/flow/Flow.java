package vnpay.vietcombank.flow;

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
import vietcombank_test_data.LogIn_Data;

public class Flow extends Base {
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
	public void TC_01_ChangePassWord() throws InterruptedException{
		login.inputToDynamicInputBox(driver, LogIn_Data.Login_Account.PHONE, "Số điện thoại");
		login.clickToDynamicButton(driver, "Tiếp tục");
		verifyTrue(login.isDynamicMessageAndLabelTextDisplayed(driver, LogIn_Data.Message.CHANGE_PASSWORD_INSTRUCTION));
		login.inputToDynamicInputBox(driver, LogIn_Data.Login_Account.PASSWORD, "Nhập mật khẩu mặc định");
		login.inputToDynamicInputBox(driver, LogIn_Data.Login_Account.NEW_PASSWORD, "Nhập lại mật khẩu");
		login.inputToDynamicInputBox(driver, LogIn_Data.Login_Account.NEW_PASSWORD, "Nhập lại mật khẩu mới");
		login.clickToDynamicButton(driver, "Tiếp tục");
		
	}
	

	

	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
