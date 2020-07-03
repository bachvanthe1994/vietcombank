package vnpay.vietcombank.mobile_topup;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.Constants;
import commons.PageFactoryManager;
import commons.WebPageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import model.ServiceLimitInfo;
import model.SourceAccountModel;
import pageObjects.LogInPageObject;
import pageObjects.MobileTopupPageObject;
import pageObjects.WebBackendSetupPageObject;
import vietcombank_test_data.HomePage_Data.Home_Text_Elements;
import vietcombank_test_data.MobileTopupPage_Data;
import vietcombank_test_data.MobileTopupPage_Data.UIs;

public class Limit_Mobile_Topup extends Base {
	AppiumDriver<MobileElement> driver;
	private WebDriver driverWeb;
	private LogInPageObject login;
	private MobileTopupPageObject mobileTopup;
	private WebBackendSetupPageObject setupBE;
	SourceAccountModel sourceAcoount = new SourceAccountModel();

	ServiceLimitInfo inputInfo = new ServiceLimitInfo("1000", "10000", "1000000", "1500000");

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp", "username", "passWeb" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt, String username, String passWeb) throws IOException, InterruptedException {
		startServer();

		log.info("Before class: Mo backend ");
		driverWeb = openMultiBrowser(Constants.BE_BROWSER_CHROME, Constants.BE_BROWSER_VERSION, Constants.BE_URL);

		setupBE = WebPageFactoryManager.getWebBackendSetupPageObject(driverWeb);

		setupBE.Login_Web_Backend(driverWeb, username, passWeb);

		log.info("Setup Add method package total limit");
		setupBE.Setup_Add_Method_Package_Total_Limit(driverWeb, Constants.BE_CODE_PACKAGE, "Method Otp", inputInfo.totalLimit);
		
		log.info("Setup assign services limit");
		setupBE.Setup_Assign_Services_Type_Limit(driverWeb, Constants.BE_CODE_PACKAGE, "Nạp tiền", "1000000000");

		log.info("Setup assign services limit");
		setupBE.Setup_Assign_Services_Limit(driverWeb, Constants.BE_CODE_PACKAGE, "Topup", "31000", "499000", "1000000000");

		setupBE.clearCacheBE(driverWeb);

		log.info("Before class: Mo app ");
		if (deviceType.contains("android")) {
			driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		} else if (deviceType.contains("ios")) {
			driver = openIOSApp(deviceName, udid, url);
		}
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
		mobileTopup = PageFactoryManager.getMobileTopupPageObject(driver);
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, "Nạp tiền điện thoại");
	}

	@Test
	public void TC_01_NapTienNhoHonHanMucToiThieuGiaoDich(String pass, String phone) {
		log.info("TC_01_Step_01: Keo xuong va click vao phan 'Nap tien dien thoai'");
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, Home_Text_Elements.MOBILE_TOPUP);
		mobileTopup = PageFactoryManager.getMobileTopupPageObject(driver);

		log.info("TC_01_Step_02: Click vào DrodownList 'Tai khoan nguon' ");
		mobileTopup.clickToDynamicAcceptButtonContainOR(driver, "com.android.packageinstaller:id/permission_allow_button", "com.android.permissioncontroller:id/permission_allow_button");
		mobileTopup.clickToTextID(driver, "com.VCB:id/number_account");

		log.info("TC_01_Step_03: Chon tai khoan nguon");

		sourceAcoount = mobileTopup.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		log.info("TC_01_Step_04: Click vao menh gia 30,000");
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, UIs.LIST_UNIT_VALUE[0]);

		log.info("TC_01_Step_05: An nut 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btn_submit");

		log.info("TC_01_Step_15: Verify hien thi man hinh thong bao loi");
		mobileTopup.isDynamicMessageAndLabelTextDisplayed(driver, MobileTopupPage_Data.MESSEGE_ERROR_Limit.MESSEGE_LOWER_LIMIT_A_TRAN);

		log.info("TC_01_Step_16: Click btn Dong");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

	}

	@Test
	public void TC_02_NapTienCaoHonHanMucToiDaGiaoDich(String pass, String phone) {
		log.info("TC_02_Step_01: Keo xuong va click vao phan 'Nap tien dien thoai'");
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, Home_Text_Elements.MOBILE_TOPUP);

		log.info("TC_02_Step_02: Click vào DrodownList 'Tai khoan nguon' ");
		mobileTopup.clickToTextID(driver, "com.VCB:id/number_account");

		log.info("TC_02_Step_03: Chon tai khoan nguon");

		sourceAcoount = mobileTopup.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);
		sourceAcoount.account = sourceAcoount.account;

		log.info("TC_02_Step_04: Click vao menh gia 500,000,00 VND");
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, UIs.LIST_UNIT_VALUE[5]);

		log.info("TC_02_Step_05: An nut 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btn_submit");

		log.info("TC_02_Step_06: Verify hien thi man hinh thong bao loi");
		mobileTopup.isDynamicMessageAndLabelTextDisplayed(driver, MobileTopupPage_Data.MESSEGE_ERROR_Limit.MESSEGE_HIGHER_LIMIT_A_TRAN);

		log.info("TC_02_Step_07: Click btn Dong");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

	}

	@Test
	// Han muc nap tien dien thoai toi da tronf 1 ngay laf 400k
	public void TC_03_NapTienCaoHonHanMucToiDaGiaoDichTrongNgay() {
		log.info("TC_03_Step_01: Keo xuong va click vao phan 'Nap tien dien thoai'");
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, Home_Text_Elements.MOBILE_TOPUP);

		log.info("TC_03_Step_02: Click vào DrodownList 'Tai khoan nguon' ");
		mobileTopup.clickToTextID(driver, "com.VCB:id/number_account");

		log.info("TC_03_Step_03: Chon tai khoan nguon");

		sourceAcoount = mobileTopup.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);
		sourceAcoount.account = sourceAcoount.account;

		log.info("TC_03_Step_04: Click vao menh gia 500,000,00 VND");
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, UIs.LIST_UNIT_VALUE[5]);

		log.info("TC_03_Step_05: An nut 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btn_submit");

		log.info("TC_03_Step_06: Verify hien thi man hinh thong bao loi");
		mobileTopup.isDynamicMessageAndLabelTextDisplayed(driver, MobileTopupPage_Data.MESSEGE_ERROR_Limit.MESSEGE_HIGHER_LIMIT_A_DAY);

		log.info("TC_03_Step_07: Click btn Dong");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
	}

	// Truoc khi chay can setup BE
	// Han muc nap tien dien thoai toi da Nhom Giao Dich 200k
	@Test
	public void TC_04_NapTienVuotQuaHanMucToiDaNhomGiaoDich() {
		log.info("TC_04_Step_01: Keo xuong va click vao phan 'Nap tien dien thoai'");
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, Home_Text_Elements.MOBILE_TOPUP);

		log.info("TC_04_Step_02: Click vào DrodownList 'Tai khoan nguon' ");
		mobileTopup.clickToTextID(driver, "com.VCB:id/number_account");

		log.info("TC_04_Step_03: Chon tai khoan nguon");

		sourceAcoount = mobileTopup.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);
		sourceAcoount.account = sourceAcoount.account;

		log.info("TC_04_Step_04: Click vao menh gia 500,000,00 VND");
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, UIs.LIST_UNIT_VALUE[5]);

		log.info("TC_04_Step_05: An nut 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btn_submit");

		log.info("TC_04_Step_06: Verify hien thi man hinh thong bao loi");
		mobileTopup.isDynamicMessageAndLabelTextDisplayed(driver, MobileTopupPage_Data.MESSEGE_ERROR_Limit.MESSEGE_HIGHER_LIMIT_GROUP);

		log.info("TC_04_Step_07: Click btn Dong");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
	}

	// Truoc khi chay can setup BE
	// Han muc nap tien dien thoai toi da Goi Giao Dich 200k
	@Test
	public void TC_05_NapTienVuotQuaHanMucToiDaGoiGiaoDich() {
		log.info("TC_05_Step_01: Keo xuong va click vao phan 'Nap tien dien thoai'");
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, Home_Text_Elements.MOBILE_TOPUP);

		log.info("TC_05_Step_02: Click vào DrodownList 'Tai khoan nguon' ");
		mobileTopup.clickToTextID(driver, "com.VCB:id/number_account");

		log.info("TC_05_Step_03: Chon tai khoan nguon");

		sourceAcoount = mobileTopup.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);
		sourceAcoount.account = sourceAcoount.account;

		log.info("TC_05_Step_04: Click vao menh gia 500,000,00 VND");
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, UIs.LIST_UNIT_VALUE[5]);

		log.info("TC_05_Step_05: An nut 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btn_submit");

		log.info("TC_05_Step_06: Verify hien thi man hinh thong bao loi");
		mobileTopup.isDynamicMessageAndLabelTextDisplayed(driver, MobileTopupPage_Data.MESSEGE_ERROR_Limit.MESSEGE_HIGHER_LIMIT_PACKAGE);

		log.info("TC_05_Step_07: Click btn Dong");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		setupBE.Reset_Package_Total_Limit(driverWeb, Constants.BE_CODE_PACKAGE, "Method Otp");
		setupBE.Reset_Setup_Assign_Services_Type_Limit(driverWeb, Constants.BE_CODE_PACKAGE, "Nạp tiền");
		setupBE.Reset_Setup_Assign_Services_Limit(driverWeb, Constants.BE_CODE_PACKAGE, "Topup");
		service.stop();
		
	}

}
