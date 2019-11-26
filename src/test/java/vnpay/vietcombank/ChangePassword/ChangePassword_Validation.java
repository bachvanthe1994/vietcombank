package vnpay.vietcombank.ChangePassword;

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

public class ChangePassword_Validation extends Base {
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
		
		login.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");
		

	}

	@Test
	public void TC_01_KiemTraManHinhDoiMatKhau(){
		System.out.println("Start");
		
		log.info("TC_01_1_Text Chao mung quy khach den voi ung dung VCB-Mobile B@nking cua Vietcombank");
//		login.verifyIconChangeLanguage();
		verifyTrue(login.isDynamicMessageAndLabelTextDisplayed(driver, LogIn_Data.Message.WELCOME_MESSAGE));
		log.info("TC_01_2_Textbox Số điện thoại");
		verifyTrue(login.isDynamicTextInInputBoxDisPlayed(driver, LogIn_Data.UI.PHONE_NUMBER));
		verifyTrue(login.isDynamicButtonDisplayed(driver, LogIn_Data.UI.CONTINUE_BUTTON));
		verifyTrue(login.isDynamicMessageAndLabelTextDisplayed(driver, LogIn_Data.UI.ONLINE_REGISTER));
		verifyTrue(login.isDynamicMessageAndLabelTextDisplayed(driver, LogIn_Data.UI.REGISTER));
		verifyTrue(login.isDynamicMessageAndLabelTextDisplayed(driver, LogIn_Data.UI.FIND_ATM));
		
		
	}
	

	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
