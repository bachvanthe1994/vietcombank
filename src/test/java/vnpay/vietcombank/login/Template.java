package vnpay.vietcombank.login;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import commons.WebAbstractPage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import pageObjects.LogInPageObject;

public class Template extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		WebAbstractPage test = new WebAbstractPage();
		driver1 = openMultiBrowser("chrome", "83.0.4103.14", "http://10.22.7.91:2021/HistorySMS/Index?f=5&c=107");
		test.inputIntoInputByID(driver1, "anhvt", "login-username");
		test.inputIntoInputByID(driver1, "123456a@", "login-password");
		test.clickToDynamicButtonByID(driver1, "btn-login");
		
		
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
	
	}

	@Test
	public void TC_01_KiemTraChonDiemDenKhiChuaChonDiemDi() {

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
