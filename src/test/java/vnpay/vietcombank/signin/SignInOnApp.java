package vnpay.vietcombank.signin;

import java.io.IOException;
import java.sql.Driver;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.model.Log;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;

public class SignInOnApp extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities,
			String appPackage, String appName) throws IOException, InterruptedException {
			startServer();
	log.info("Before class: Mo app ");
	driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);

	login = PageFactoryManager.getLoginPageObject(driver);

	log.info("Before class: Click Allow Button");
	login.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");


	}

	@Test
	public void TC_01_ManHinhDangNhap() {
		log.info("TC_01_1_Text Chao mung quy khach den voi ung dung VCB-Mobile B@nking cua Vietcombank");
		verifyTrue(login.isDynamicMessageAndLabelTextDisplayed(driver, "	Chào mừng Quý khách đến với ứng dụng VCB-Mobile B@nking của Vietcombank"));
		                                                        

		log.info("TC_01_2_So dien thoai");
		verifyTrue(login.isDynamicMessageAndLabelTextDisplayed(driver, "Số điện thoại"));
		
		log.info("TC_01_2_button Tiep tuc");
		verifyTrue(login.isDynamicMessageAndLabelTextDisplayed(driver, "Tiếp tụcyyyyyyyyyyyyyyyyyyyyy"));

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
