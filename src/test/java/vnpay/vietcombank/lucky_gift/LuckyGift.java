package vnpay.vietcombank.lucky_gift;

import java.io.IOException;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import pageObjects.LogInPageObject;
import pageObjects.LuckyGiftPageObject;
import vietcombank_test_data.LogIn_Data;

public class LuckyGift extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private LuckyGiftPageObject luckyGift;

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName" })

	@BeforeMethod
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities,
			String appPackage, String appName) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);

		login = PageFactoryManager.getLoginPageObject(driver);
		luckyGift = PageFactoryManager.getLuckyGiftPageObject(driver);

		login.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");

		login.inputToDynamicLogInTextBox(driver, LogIn_Data.Login_Account.PHONE, "Tiếp tục");

		login.clickToDynamicButton(driver, "Tiếp tục");

		login.inputToDynamicInputBox(driver, LogIn_Data.Login_Account.NEW_PASSWORD, LogIn_Data.UI.PASSWORD_LABEL);

		login.clickToDynamicButton(driver, "Tiếp tục");

		login.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		login.clickToDynamicButton(driver, "Tiếp tục");

		login.clickToDynamicButton(driver, "Hủy");

		login.clickToDynamicCloseIcon(driver, "Kích hoạt tính năng mới");
	}


	@Test
	public void TC_01_HuyChuyenTienDinhKy() {
		log.info("TC_01_1_Click trang thai lenh chuyen tien");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, "Quà tặng may mắn");

		log.info("TC_01_Step_Select tai khoan nguon");

		
		
	
	}

	@AfterMethod(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
