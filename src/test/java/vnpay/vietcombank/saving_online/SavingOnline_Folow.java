package vnpay.vietcombank.saving_online;

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
import vietcombank_test_data.Account_Data;

public class SavingOnline_Folow extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private SavingOnlinePageObject savingOnline;
	
	long fee = 0;
	double transferFeeCurrentcy = 0;
	
	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
	}

	private long surplus, availableBalance, actualAvailableBalance;
	private double surplusCurrentcy, availableBalanceCurrentcy, actualAvailableBalanceCurrentcy;

	@Test
	public void TC_01_MoTaiKhoanTietKiem_VND_1Thang_LaiNhapGoc() {
		homePage = PageFactoryManager.getHomePageObject(driver);
		savingOnline = PageFactoryManager.getSavingOnlinePageObject(driver);
		
		log.info("TC_01_1_Click Mo tai khoan tiet kiem");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Mở tài khoản tiết kiệm");

		log.info("TC_01_2_Chon so tai khoan");
		savingOnline.clickToDynamicDropDown(driver, "Số tài khoản");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);
		
		log.info("TC_01_3_Chon ky han gui");
		savingOnline.clickToDynamicDropDownListTextViewByHeader(driver, "Thông tin giao dịch", "1");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, "1 tháng");
		
		log.info("TC_01_4_Nhap so tien gui");
		savingOnline.inputToDynamicInputBoxByHeader(driver, "2000000", "Thông tin giao dịch", "2");
		
		log.info("TC_01_5_Hinh ");
		savingOnline.clickToDynamicDropDownListTextViewByHeader(driver, "Thông tin giao dịch", "1");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, "1 tháng");
		
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
