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
import model.SavingOnlineInfo;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.SavingOnlinePageObject;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.SavingOnline_Data;
import vietcombank_test_data.TransferMoneyQuick_Data;

public class Limit_Settlement_Saving_Online extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private SavingOnlinePageObject savingOnline;
	long transferFee = 0;
	double transferFeeCurrentcy = 0;

	SavingOnlineInfo info = new SavingOnlineInfo(Account_Data.Valid_Account.ACCOUNT2, "1 tháng", "200", "Lãi nhập gốc");
	SavingOnlineInfo info1 = new SavingOnlineInfo(Account_Data.Valid_Account.ACCOUNT2, "3 tháng", "20000000", "Lãi nhập gốc");
	SavingOnlineInfo info2 = new SavingOnlineInfo(Account_Data.Valid_Account.ACCOUNT2, "3 tháng", "10000000", "Lãi nhập gốc");
	SavingOnlineInfo info3 = new SavingOnlineInfo(Account_Data.Valid_Account.ACCOUNT2, "3 tháng", "50000000", "Lãi nhập gốc");
	
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
		homePage = PageFactoryManager.getHomePageObject(driver);
		savingOnline = PageFactoryManager.getSavingOnlinePageObject(driver);

	}

//	@Test
	public void TC_01_SoTienGiaoDichNhoHonHanMucToiThieuMotLanGiaoDich() {
		log.info("TC_01_1_Click Tat toan tai khoan tiet kiem");
		homePage.scrollDownToText(driver, "Tín dụng");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Tất toán tài khoản tiết kiệm");
		
		log.info("TC_01_2_Chon so tai khoan tiet kiem");
		savingOnline.clickToDynamicDropDownInSavingOnline("Tài khoản tiết kiệm");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_01_3_Chon tai khoan dich");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, "Chọn tài khoản đích");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_01_4_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_01_Step_verify message khi so tien chuyen nho hon han muc toi thieu ");
		verifyTrue(savingOnline.isDynamicMessageAndLabelTextDisplayed(driver, SavingOnline_Data.CONFIRM_TRANSECTION_LIMIT_MIN_SETTLEMENT));
	}
	
//	@Test
	public void TC_02_SoTienGiaoDichLonHonHanMucToiThieuMotLanGiaoDich() {
		log.info("TC_02_1_Click Tat toan tai khoan tiet kiem");
		homePage.scrollDownToText(driver, "Tín dụng");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Tất toán tài khoản tiết kiệm");
		
		log.info("TC_02_2_Chon so tai khoan tiet kiem");
		savingOnline.clickToDynamicDropDownInSavingOnline("Tài khoản tiết kiệm");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_02_3_Chon tai khoan dich");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, "Chọn tài khoản đích");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_02_4_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_02_Step_verify message khi so tien chuyen nho hon han muc toi thieu ");
		verifyTrue(savingOnline.isDynamicMessageAndLabelTextDisplayed(driver, SavingOnline_Data.CONFIRM_TRANSECTION_LIMIT_MAX_SETTLEMENT));
	}
	
	@Test
	public void TC_03_SoTienGiaoDichLonHonHanMucToiThieuCuaNhomGiaoDich() {
		log.info("TC_03_1_Click Tat toan tai khoan tiet kiem");
		homePage.scrollDownToText(driver, "Tín dụng");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Tất toán tài khoản tiết kiệm");
		
		log.info("TC_03_2_Chon so tai khoan tiet kiem");
		savingOnline.clickToDynamicDropDownInSavingOnline("Tài khoản tiết kiệm");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_03_3_Chon tai khoan dich");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, "Chọn tài khoản đích");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_03_4_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_03_Step_verify message khi so tien chuyen nho hon han muc toi thieu ");
		verifyTrue(savingOnline.isDynamicMessageAndLabelTextDisplayed(driver, SavingOnline_Data.CONFIRM_TRANSECTION_LIMIT_MAX_SETTLEMENT));
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
//		service.stop();
	}

}
