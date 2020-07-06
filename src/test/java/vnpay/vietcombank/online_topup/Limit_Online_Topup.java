package vnpay.vietcombank.online_topup;

import java.io.IOException;
import java.security.GeneralSecurityException;

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
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.OnlineTopupPageObject;
import pageObjects.WebBackendSetupPageObject;
import vietcombank_test_data.Online_Topup_Data;
import vietcombank_test_data.Online_Topup_Data.Online_Topup_Text;

public class Limit_Online_Topup extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject home;
	private OnlineTopupPageObject onlineTopup;
	WebDriver driverWeb;
	private WebBackendSetupPageObject webBackend;
	ServiceLimitInfo inputInfo = new ServiceLimitInfo("1000", "1000", "1000000", "1000001");
	String code_VECT;

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp", "username", "passWeb" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt, String username, String passWeb) throws IOException, InterruptedException, GeneralSecurityException {
		log.info("Before class: Mo backend ");
		startServer();
		driverWeb = openMultiBrowser(Constants.BE_BROWSER_CHROME, Constants.BE_BROWSER_VERSION, Constants.BE_URL);
		webBackend = WebPageFactoryManager.getWebBackendSetupPageObject(driverWeb);
		webBackend.Login_Web_Backend(driverWeb, username, passWeb);

//		webBackend.addMethod(driverWeb, "Nạp tiền VETC", inputInfo, Constants.BE_CODE_PACKAGE);
		log.info("Before class: Mo app ");
		if (deviceType.contains("android")) {
			driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		} else if (deviceType.contains("ios")) {
			driver = openIOSApp(deviceName, udid, url);
		}
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(Online_Topup_Data.MOMO.MOMO_DATA_01, "qqqq1111", opt);
		login.scrollDownToText(driver, "Tiết kiệm");
		home = PageFactoryManager.getHomePageObject(driver);
		home.clickToDynamicButtonLinkOrLinkText(driver, "Nạp tiền điện tử");
		onlineTopup = PageFactoryManager.getOnlineTopupPageObject(driver);
		code_VECT = getDataInCell(26);

		// Nạp tiền VETC

	}

	@Test
	public void TC_01_NapTienDienTuVaoVETCThapHonHanMucToiThieu() {

		log.info("TC_01_Step_01: An mo dropdownlist Ten Dich Vu");
		onlineTopup.clickToDynamicButtonLinkOrLinkText(driver, Online_Topup_Text.E_WALLET_TOPUP_TEXT);

		webBackend.getInfoServiceLimit(driverWeb, "Nạp tiền VETC", inputInfo, Constants.BE_CODE_PACKAGE);

		log.info("TC_01_Step_03: Chon 'Nap tien tai khoan VETC");
		onlineTopup.clickToDynamicButtonLinkOrLinkText(driver, Online_Topup_Data.VETC.VETC_SERVICE);

		log.info("TC_01_Step_04: An mo dropdownlist Nha cung cap");
		onlineTopup.clickToDynamicButtonLinkOrLinkText(driver, "VETC");

		log.info("TC_01_Step_05: Chon 'VETC'");
		onlineTopup.clickToDynamicButtonLinkOrLinkText(driver, "VETC");

		log.info("TC_01_Step_06: Nhap Bien so xe/Ma khach hang");
		onlineTopup.inputToDynamicInputBox(driver, code_VECT, Online_Topup_Data.VETC.LICENSE_PLATES_CODE_CUSTOMER);

		log.info("TC_01_Step_07: Nhap so tien");
		onlineTopup.scrollDownToButton(driver, "Tiếp tục");
		onlineTopup.inputToDynamicEditviewByLinearlayoutId(driver, Integer.parseInt(inputInfo.minTran) - 1 + "", "com.VCB:id/layout5");

		log.info("TC_01_Step_08: An vao check box");
		onlineTopup.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");

		log.info("TC_01_Step_09: CLick tiep tuc");
		onlineTopup.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_10: An nut 'Tiep tuc'");
		verifyEquals(onlineTopup.getDynamicTextView(driver, "com.VCB:id/tvContent"), "Thanh toán hóa đơn không thành công. Số tiền giao dịch nhỏ hơn hạn mức " + addCommasToLong((inputInfo.minTran) + "") + " VND/1 lần, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline của Vietcombank để được trợ giúp.");

		log.info("TC_01_Step_11: Click btn Dong");
		onlineTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

	}

	@Test
	public void TC_02_NapTienDienTuVaoVETCVuotQuaHanMucToiDa() {
		onlineTopup.inputToDynamicEditviewByLinearlayoutId(driver, Integer.parseInt(inputInfo.maxTran) + 1 + "", "com.VCB:id/layout5");

		log.info("TC_01_Step_08: An vao check box");
		onlineTopup.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");

		log.info("TC_01_Step_09: CLick tiep tuc");
		onlineTopup.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_10: An nut 'Tiep tuc'");
		verifyEquals(onlineTopup.getDynamicTextView(driver, "com.VCB:id/tvContent"), "Thanh toán hóa đơn không thành công. Số tiền giao dịch lớn hơn hơn hạn mức " + addCommasToLong((inputInfo.maxTran) + "") + " VND/1 lần, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline của Vietcombank để được trợ giúp.");
		webBackend.resetAssignServicesLimit_All(driverWeb, "Nạp tiền VETC", Constants.BE_CODE_PACKAGE);

	}

	@Test
	public void TC_03_NapTienDienTuVaoVETCVuotQuaHanMucToiDaTrongNgay() {

		onlineTopup.inputToDynamicEditviewByLinearlayoutId(driver, Integer.parseInt(inputInfo.totalLimit) + 1 + "", "com.VCB:id/layout5");

		webBackend.Setup_Assign_Services_Type_Limit(driverWeb, Constants.BE_CODE_PACKAGE, "Nạp tiền điện tử", inputInfo.totalLimit);

		log.info("TC_01_Step_08: An vao check box");
		onlineTopup.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");

		log.info("TC_01_Step_09: CLick tiep tuc");
		onlineTopup.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_10: An nut 'Tiep tuc'");
		verifyEquals(onlineTopup.getDynamicTextView(driver, "com.VCB:id/tvContent"), "Thanh toán hóa đơn không thành công. Số tiền giao dịch lớn hơn hơn hạn mức " + addCommasToLong((inputInfo.totalLimit) + "") + " VND/1 lần, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline của Vietcombank để được trợ giúp.");
		webBackend.Reset_Setup_Assign_Services_Type_Limit(driverWeb, Constants.BE_CODE_PACKAGE, "Nạp tiền điện tử");

	}

	@Test
	public void TC_04_NapTienDienTuVaoVETCCaoHonHanMucToiDaGoiDichVu() {
		onlineTopup.inputToDynamicEditviewByLinearlayoutId(driver, Integer.parseInt(inputInfo.maxTran) + 1 + "", "com.VCB:id/layout5");
		webBackend.Setup_Add_Method_Package_Total_Limit(driverWeb, Constants.BE_CODE_PACKAGE, Constants.METHOD_OTP, inputInfo.totalLimit);

		log.info("TC_01_Step_08: An vao check box");
		onlineTopup.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");

		log.info("TC_01_Step_09: CLick tiep tuc");
		onlineTopup.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_10: An nut 'Tiep tuc'");
		verifyEquals(onlineTopup.getDynamicTextView(driver, "com.VCB:id/tvContent"), "Thanh toán hóa đơn không thành công. Số tiền giao dịch lớn hơn hơn hạn mức " + addCommasToLong((inputInfo.maxTran) + "") + " VND/1 lần, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline của Vietcombank để được trợ giúp.");
		webBackend.Reset_Package_Total_Limit(driverWeb, Constants.BE_CODE_PACKAGE, Constants.METHOD_OTP);

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		service.stop();
	}

}
