package vnpay.vietcombank.electric_bills;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import pageObjects.ElectricBillPageObject;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.Electric_Bills_Data;

public class Electric_Bills_Flow extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject home;
	private ElectricBillPageObject electricBill;

	private String sourceAccountMoney;

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone",
			"pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities,
			String appPackage, String appName, String phone, String pass, String opt)
			throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login("0918679292", "aaaa1111", opt);

	}

	@Test
	public void TC_01_ThanhToanTienDien_MK() {

		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_07_Step_01: Hoa don tien dien");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Hoá đơn tiền điện");
		electricBill = PageFactoryManager.getElectricBillPageObject(driver);

		log.info("TC_07_Step_02: Chon tai khoan nguon");
		electricBill.clickToTextID(driver, "com.VCB:id/number_account");
		electricBill.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);
		sourceAccountMoney = electricBill.getDynamicTextByLabel(driver, "Số dư khả dụng");

		log.info("TC_07_Step_03: Chon Nha cung cap EVN Mien Trung");
		electricBill.clickToTextID(driver, "com.VCB:id/wrap_tv");
		electricBill.clickToDynamicButtonLinkOrLinkText(driver, Electric_Bills_Data.TEXT.EVN_MIEN_TRUNG);

		log.info("TC_07_Step_04: Nhap ma khach hang");
		electricBill.inputIntoEditTextByID(driver, Electric_Bills_Data.TEXT.CUSTOMER_ID_01, "com.VCB:id/code");

		log.info("TC_07_Step_15: An nut Tiep Tuc");
		verifyEquals(electricBill.getDynamicTextButtonById(driver, "com.VCB:id/btContinue"), "Tiếp tục");
		electricBill.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		service.stop();
	}

}
