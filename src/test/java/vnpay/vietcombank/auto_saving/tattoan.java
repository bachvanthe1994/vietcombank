package vnpay.vietcombank.auto_saving;

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
import pageObjects.LogInPageObject;
import pageObjects.saving_online.SavingOnlinePageObject;
import vietcombank_test_data.Auto_Saving_Data;
import vietcombank_test_data.LogIn_Data;

public class tattoan extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject home;

	private SavingOnlinePageObject savingOnline;

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
		home = PageFactoryManager.getHomePageObject(driver);
		savingOnline = PageFactoryManager.getSavingOnlinePageObject(driver);
	}

	@Parameters({ "otp" })
	@Test(invocationCount = 100)
	public void TC_00_MoTaiKhoanTietKiem_TaiKhoanNguon_VND(String otp) {

		log.info("TC_12_1_Click Tat toan tai khoan tiet kiem");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Tất toán tài khoản tiết kiệm");

		log.info("TC_12_3_Chon tai khoan dich");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, "Chọn tài khoản đích");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_VND);

		log.info("TC_12_4_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_12_5_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_12_7_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, "Tiếp tục");

		savingOnline.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		savingOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_12_7_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, "Thực hiện giao dịch mới");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		service.stop();
	}

}
