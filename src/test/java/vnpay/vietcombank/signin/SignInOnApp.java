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
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.LogIn_Data.Login_Account;

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

	//@Test
	public void TC_01_ManHinhDangNhap() {
		log.info("TC_01_1_Text Chao mung quy khach den voi ung dung VCB-Mobile B@nking cua Vietcombank");
		verifyTrue(login.isDynamicMessageAndLabelTextDisplayed(driver,
				"Chào mừng Quý khách đến với ứng dụng" + "\n" + "VCB-Mobile B@nking của Vietcombank"));
	
		log.info("TC_01_2_So dien thoai");
		verifyTrue(login.isDynamicTextInInputBoxDisPlayed(driver, "Số điện thoại"));

		log.info("TC_01_3_button Tiep tuc");
		verifyTrue(login.isDynamicVerifyTextOnButton(driver, "Tiếp tục"));

		log.info("TC_01_4_verify Dang ky vcb-MB");
		verifyTrue(login.isDynamicMessageAndLabelTextDisplayed(driver, "Đăng ký VCB-Mobile B@nking"));

		log.info("TC_01_5_verify Dang ky truc tuyen");
		verifyTrue(login.isDynamicMessageAndLabelTextDisplayed(driver, "Đăng ký trực tuyến"));

		log.info("TC_01_6_verify Tìm kiếm ATM/chi nhánh gần đây");
		verifyTrue(login.isDynamicMessageAndLabelTextDisplayed(driver, "Tìm kiếm ATM/chi nhánh gần đây"));
	}
	
	//@Test
	public void TC_02_NhapToiDaSoDienThoai() {
		log.info("TC_02_1_Nhap so dien thoai 11 ky tu");
		login.inputToDynamicInputBox(driver,Login_Account.PHONE_InV, "Số điện thoại");
		log.info("TC_02_2_Verify so dien thoai, chi cho phep nhap 10 so");	
		verifyTrue(login.isDynamicTextInInputBoxDisPlayed(driver, Login_Account.PHONE));
		
	}
	
	@Test
	public void TC_03_NhapThongTinIB() {
		log.info("TC_03_1_Click dang ky vcb-mobile banking");
		login.clickToDynamicButionLinkOrLinkText(driver, "Đăng ký VCB-Mobile B@nking");
		
		log.info("TC_03_2_Click VCB - IB");
		login.clickToDynamicButionLinkOrLinkText(driver, "VCB-iB@nking");
		
		log.info("TC_03_3_Check button quay lai");
		login.clickToDynamicButionLinkOrLinkText(driver, "Quay lại");
		
		log.info("TC_03_4_verify text check man hinh sau khi nhan quay lai");
		verifyTrue(login.isDynamicMessageAndLabelTextDisplayed(driver, "Tìm kiếm ATM/chi nhánh gần đây"));
		
		log.info("TC_03_5_Click dang ky vcb-mobile banking");
		login.clickToDynamicButionLinkOrLinkText(driver, "Đăng ký VCB-Mobile B@nking");
		
		log.info("TC_03_6_Click VCB - IB");
		login.clickToDynamicButionLinkOrLinkText(driver, "VCB-iB@nking");
		
		log.info("TC_01_1_Quý khách vui lòng nhập tên đăng nhập và mật khẩu VCB-iB@nking");
		verifyTrue(login.isDynamicMessageAndLabelTextDisplayed(driver,
				"Quý khách vui lòng nhập tên đăng" + "\n" + "nhập và mật khẩu VCB-iB@nking"));
	}


	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
