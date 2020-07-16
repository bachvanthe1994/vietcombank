package vnpay.vietcombank.auto_saving;

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
import model.SourceAccountModel;
import pageObjects.AutoSavingPageObject;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.WebBackendSetupPageObject;
import vietcombank_test_data.Auto_Saving_Data.Auto_Saving_Text;

public class Limit_Auto_Saving extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject home;
	WebDriver driverWeb;
	private WebBackendSetupPageObject webBackend;

	private AutoSavingPageObject autoSaving;
	SourceAccountModel sourceAccount = new SourceAccountModel();
	SourceAccountModel accountPayment = new SourceAccountModel();

	ServiceLimitInfo inputInfo = new ServiceLimitInfo("1000", "1000", "1000000", "1000001");

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp", "username", "passWeb" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt, String username, String passWeb) throws IOException, InterruptedException, GeneralSecurityException {
		log.info("Before class: Mo backend ");
		startServer();
		driverWeb = openMultiBrowser(Constants.BE_BROWSER_CHROME, Constants.BE_BROWSER_VERSION, Constants.BE_URL);
		webBackend = WebPageFactoryManager.getWebBackendSetupPageObject(driverWeb);
		webBackend.Login_Web_Backend(driverWeb, username, passWeb);

		webBackend.addMethod(driverWeb, "Đăng ký tiết kiệm tự động", inputInfo, Constants.BE_CODE_PACKAGE);

		log.info("Before class: Mo app ");
		if (deviceType.contains("android")) {
			driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		} else if (deviceType.contains("ios")) {
			driver = openIOSApp(deviceName, udid, url);
		}
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
		home = PageFactoryManager.getHomePageObject(driver);
		autoSaving = PageFactoryManager.getAutoSavingPageObject(driver);

	}

	@Test
	public void TC_01_Auto_Saving_Nho_Hon_Han_Muc_Toi_THieu_Min_Tran() throws InterruptedException {

		log.info("TC_02_Step_01: Keo xuong va click vao phan 'Tiet kiem tu dong'");
		home.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Text.AUTO_SAVING_TEXT);

		log.info("TC_02_Step_02: Chon tai khoan nguon VND");
		autoSaving.clickToTextID(driver, "com.VCB:id/tvContent");
		sourceAccount = autoSaving.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		log.info("TC_02_Step_03: Chon tai khoan tiet kiem");
		autoSaving.clickToDynamicLinerLayoutID(driver, "com.VCB:id/TaiKhoanTietKiem");
		accountPayment = autoSaving.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		log.info("TC_02_Step_05: Chon ngay ket thuc");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Text.END_DATE_TEXT);
		autoSaving.clickToDynamicDateInDateTimePicker(driver, getForWardDay(3));
		autoSaving.clickToDynamicButton(driver, Auto_Saving_Text.BUTTON_OK_TEXT);

		log.info("TC_02_Step_06: Nhap so tien chuyen");
		autoSaving.inputToDynamicEditviewByLinearlayoutId(driver, Integer.parseInt(inputInfo.minTran) - 1 + "", "com.VCB:id/SoTien");
		webBackend.getInfoServiceLimit(driverWeb, "Đăng ký tiết kiệm tự động", inputInfo, Constants.BE_CODE_PACKAGE);

		log.info("TC_02_Step_07: An Tiep tuc");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		verifyEquals(autoSaving.getDynamicTextView(driver, "com.VCB:id/tvContent"), "Giao dịch không thành công. Số tiền giao dịch nhỏ hơn hạn mức " + addCommasToLong((inputInfo.minTran) + "") + " VND/1 lần, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline của Vietcombank để được trợ giúp.");
		autoSaving.clickToDynamicContinue(driver, "com.VCB:id/btOK");

	}

	@Test
	public void TC_02_Auto_Saving_Lon_Hon_Han_Muc_Toi_Da_Max_Tran() throws InterruptedException {

		autoSaving.inputToDynamicEditviewByLinearlayoutId(driver, Integer.parseInt(inputInfo.maxTran) + 1 + "", "com.VCB:id/SoTien");
		log.info("TC_02_Step_07: An Tiep tuc");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		verifyEquals(autoSaving.getDynamicTextView(driver, "com.VCB:id/tvContent"), "TGiao dịch không thành công. Số tiền giao dịch lớn hơn hạn mức " + addCommasToLong((inputInfo.maxTran) + "") + " VND/1 lần, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline của Vietcombank để được trợ giúp.");
		autoSaving.clickToDynamicContinue(driver, "com.VCB:id/btOK");

		webBackend.resetAssignServicesLimit_All(driverWeb, "Đăng ký tiết kiệm tự động", Constants.BE_CODE_PACKAGE);

	}

	@Test
	public void TC_03_Auto_Saving_Lon_Hon_Han_Muc_Toi_Da_Max_Nhom_Giao_Dich() throws InterruptedException {

		autoSaving.inputToDynamicEditviewByLinearlayoutId(driver, Integer.parseInt(inputInfo.totalLimit) + 1 + "", "com.VCB:id/SoTien");
		log.info("TC_02_Step_07: An Tiep tuc");

		webBackend.Setup_Assign_Services_Type_Limit(driverWeb, Constants.BE_CODE_PACKAGE, "Tiết kiệm tự động", inputInfo.totalLimit);
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		verifyEquals(autoSaving.getDynamicTextView(driver, "com.VCB:id/tvContent"), "Giao dịch không thành công. Số tiền giao dịch lớn hơn hạn mức " + addCommasToLong((inputInfo.totalLimit) + "") + " VND/1 VND/1 ngày của nhóm dịch vụ, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline của Vietcombank để được trợ giúp.");
		autoSaving.clickToDynamicContinue(driver, "com.VCB:id/btOK");

		webBackend.Reset_Setup_Assign_Services_Type_Limit(driverWeb, Constants.BE_CODE_PACKAGE, "Tiết kiệm tự động");

	}

	@Test
	public void TC_04_Auto_Saving_Lon_Hon_Han_Muc_Toi_Da_Max_Goi_Giao_Dich() throws InterruptedException {

		autoSaving.inputToDynamicEditviewByLinearlayoutId(driver, Integer.parseInt(inputInfo.totalLimit) + 1 + "", "com.VCB:id/SoTien");
		log.info("TC_02_Step_07: An Tiep tuc");

		webBackend.Setup_Add_Method_Package_Total_Limit(driverWeb, Constants.BE_CODE_PACKAGE, Constants.METHOD_OTP, inputInfo.totalLimit);
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		verifyEquals(autoSaving.getDynamicTextView(driver, "com.VCB:id/tvContent"), "Giao dịch không thành công. Số tiền giao dịch lớn hơn hạn mức " + addCommasToLong((inputInfo.totalLimit) + "") + " VND/1 ngày của gói dịch vụ, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline của Vietcombank để được trợ giúp.");
		autoSaving.clickToDynamicContinue(driver, "com.VCB:id/btOK");

		webBackend.Reset_Setup_Assign_Services_Type_Limit(driverWeb, Constants.BE_CODE_PACKAGE, Constants.METHOD_OTP);

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		service.stop();
	}

}
