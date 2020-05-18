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
import pageObjects.SavingOnlinePageObject;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.Auto_Saving_Data;


public class Limit_Auto_Saving extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject home;

	private SavingOnlinePageObject savingOnline;

	

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		if (deviceType.contains("android")) {
			driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		} else if (deviceType.contains("ios")) {
			driver = openIOSApp(deviceName, udid, url);
		}
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);

	}

	@Parameters({ "otp" })
	@Test
	public void TC_01_MoTaiKhoanTietKiem_TaiKhoanNguon_VND(String otp) {

		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_01_1_Click Mo tai khoan tiet kiem");
		home.scrollDownToText(driver, "Tín dụng");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Mở tài khoản tiết kiệm");
		savingOnline = PageFactoryManager.getSavingOnlinePageObject(driver);

		log.info("TC_01_2_Chon so tai khoan");
		savingOnline.clickToDynamicDropDown(driver, "Số tài khoản");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_01_3_Chon ky han gui");
		savingOnline.clickToDynamicDropDownListTextViewByHeader(driver, "Thông tin giao dịch", "1");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Data.TEXT.TERM);

		log.info("TC_01_4_Nhap so tien gui");
		savingOnline.inputToDynamicInputBoxByHeader(driver, Auto_Saving_Data.TEXT.INPUT_VND, "Thông tin giao dịch", "2");

		log.info("TC_01_5_Chon hinh thuc chuyen tien");
		savingOnline.clickToDynamicDropDownListTextViewByHeader(driver, "Thông tin giao dịch", "3");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Data.TEXT.FORM_OF_PAYMENT);

		log.info("TC_01_6_Chon dong y tuan thu cam ket");
		savingOnline.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");

		log.info("TC_01_7_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, "Tiếp tục");

	}
	@AfterClass(alwaysRun = true)
	public void afterClass() {
		service.stop();
	}

}
