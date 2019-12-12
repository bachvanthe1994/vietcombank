package vnpay.vietcombank.signin;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import pageObjects.LogInPageObject;
import vietcombank_test_data.LogIn_Data;

public class CheckPopup extends Base {
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
		
		

	}

	@Test
	public void TC_01_KiemTraPopUp(){
		login.inputToDynamicInputBox("0973217428", "Số điện thoại");
		login.clickToDynamicButton(SignIn_Data.Message_popup.BUTTON_NEXT);
		String text = login.getTextDynamicPopup(SignIn_Data.Message_popup.MESSAGE_POPUP);
		verifyTrue(text.equals(SignIn_Data.Message_popup.MESSAGE_POPUP));
	}
	
	@Test
	public void TC_02_ChonDangKiNgay(){
		login.clickToDynamicButton("Đăng ký ngay");
		String messageIb = login.getTextDynamicPopup("VCB-iB@nking");
		verifyTrue(messageIb.equals(SignIn_Data.Message_popup.MESSAGE_SIGNIN_IB));
	}
	
	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
