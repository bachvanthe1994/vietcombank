package vnpay.vietcombank.investigation_online;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import pageObjects.HomePageObject;
import pageObjects.InvestigationOnlinePageObject;
import pageObjects.LogInPageObject;
import pageObjects.InvestigationOnlinePageObject;
import vietcombank_test_data.Register_Online_data;

public class Flow_Investigation_Online extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject homePageOnline;
	private InvestigationOnlinePageObject investigationOnline;
	String phoneNumber = "";
	String nameCustomer = "";

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
		phoneNumber = phone;
		homePageOnline = PageFactoryManager.getHomePageObject(driver);

		homePageOnline.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");
		homePageOnline.clickToDynamicButtonLinkOrLinkText(driver, "Tra soát trực tuyến");

	}

	@Test
	public void TC_01_GiaoDichNopTienThanhCong() {

		investigationOnline = PageFactoryManager.getInvestigationOnline(driver);
		investigationOnline.isDynamicMessageAndLabelTextDisplayed(driver, "Loại yêu cầu tra soát");
		
		log.info("TC_01_Step: Click giao dich tien mat/chuyen tien");
		investigationOnline.clickToDynamicButtonLinkOrLinkText(driver, "Lập yêu cầu tra soát");

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
