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
import pageObjects.AutoSavingPageObject;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.TransactionReportPageObject;
import pageObjects.saving_online.SavingOnlinePageObject;
import vietcombank_test_data.Auto_Saving_Data;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.SavingOnline_Data;

public class Auto_Saving_Validate_01 extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject home;
	private AutoSavingPageObject autoSaving;
	private TransactionReportPageObject transactionReport;
	private SavingOnlinePageObject savingOnline;
	
	private String transactionID,savingAccount,tranferMoney,transactionDate,startDate,endDate, fee;
	private long transferFee;

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login("0335059860", "aaaa1111", opt);

	}

	@Parameters({ "otp" })
	@Test
	public void TC_01_TietKiemTuDong_(String otp) {

	}

	@Parameters({ "pass" })
	@Test
	public void TC_02_TietKiemTuDong_(String pass) {

	}

	@Test
	public void TC_03_TietKiemTuDong_() {

	}

	@Test
	public void TC_04_TietKiemTuDong_() {

	}

	@Test
	public void TC_05_TietKiemTuDong_() {

	}

	@Parameters({ "otp" })
	@Test
	public void TC_06_TietKiemTuDong_(String otp) {

	}

	@Parameters({ "pass" })
	@Test
	public void TC_07_TietKiemTuDong_(String pass) {

	}

	@Test
	public void TC_08_TietKiemTuDong_() {

	}

	@Test
	public void TC_09_TietKiemTuDong_() {

	}

	@Test
	public void TC_10_TietKiemTuDong_() {

	}

	@Parameters({ "otp" })
	@Test
	public void TC_11_TietKiemTuDong_(String otp) {

	}

	@Parameters({ "pass" })
	@Test
	public void TC_12_TietKiemTuDong_(String pass) {

	}

	@Test
	public void TC_13_TietKiemTuDong_() {
	}

	@Test
	public void TC_14_TietKiemTuDong_() {

	}

	@Test
	public void TC_15_TietKiemTuDong_() {

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		service.stop();
	}

}
