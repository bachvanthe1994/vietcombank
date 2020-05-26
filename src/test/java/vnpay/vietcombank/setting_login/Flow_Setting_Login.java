package vnpay.vietcombank.setting_login;

import java.io.IOException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import pageObjects.LogInPageObject;
import pageObjects.SettingLoginPageObject;
import vietcombank_test_data.Setting_Login_Data;

public class Flow_Setting_Login extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private SettingLoginPageObject settingLogin;
	String passLogin = "";

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
		settingLogin = PageFactoryManager.getSettingLoginPageObject(driver);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
		passLogin = pass;
	}

	@Test
	public void TC_01_CaiDatDangNhapDichVuVCB() {

		log.info("-------------------------------TC_01_Step: Click menu header-----------------------------");
		settingLogin.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("-------------------------------TC_01_Step: Click cai dat-------------------------------");
		settingLogin.clickToDynamicButtonLinkOrLinkText(driver, "Cài đặt");

		log.info("-------------------------------TC_01_Step: Scroll xuong phan doi mat khau-------------------------------");
		settingLogin.scrollDownToText(driver, "Tra cứu");

		log.info("-------------------------------TC_01_Step: Click Cai dat dang nhap-------------------------------");
		settingLogin.clickToDynamicButtonLinkOrLinkText(driver, "Cài đặt đăng nhập");

		log.info("-------------------------------TC_01_Step: Verify man hinh cai dat dang nhap-------------------------------");
		verifyEquals(settingLogin.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Cài đặt đăng nhập");
		
		log.info("-------------------------------TC_01_Step: Click button cai dat dang nhap-------------------------------");
		settingLogin.clickToDynamicImageViewByID(driver, "com.VCB:id/settingLogin");
		
		log.info("-------------------------------TC_01_Step: Verify noi dung thong bao-------------------------------");
		verifyEquals(settingLogin.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), Setting_Login_Data.MESSEGE_KHOA);

		log.info("-------------------------------TC_01_Step: Click dong y-------------------------------");
		settingLogin.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

		log.info("-------------------------------TC_01_Step: Doi cho den khi tat thong bao-------------------------------");
		settingLogin.sleep(driver, 5000);
	}

	@Parameters({ "pass" })
	@Test
	public void TC_02_HuyCaiDatDangNhapDichVuVCB(String pass) {

		log.info("-------------------------------TC_02_Step: Click button cai dat dang nhap-------------------------------");
		settingLogin.clickToDynamicImageViewByID(driver, "com.VCB:id/settingLogin");

		log.info("-------------------------------TC_02_Step: Verify noi dung thong bao-------------------------------");
		verifyEquals(settingLogin.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), Setting_Login_Data.MESSEGE_MO_KHOA);

		log.info("-------------------------------TC_02_Step: Click dong y-------------------------------");
		settingLogin.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

		log.info("-------------------------------TC_02_Step: Verify man hinh thong bao-------------------------------");
		verifyEquals(settingLogin.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), "Thông báo");

		log.info("-------------------------------TC_02_Step: Nhap mat khau-------------------------------");
		settingLogin.inputIntoEditTextByID(driver, pass, "com.VCB:id/edtPassWord");

		log.info("-------------------------------TC_02_Step: Click ok-------------------------------");
		settingLogin.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("-------------------------------TC_02_Step: Doi cho den khi tat thong bao-------------------------------");
		settingLogin.sleep(driver, 5000);

	}

	@Test
	public void TC_03_CaiDatXacThucDangNhapDichVuVCB() {

		log.info("-------------------------------TC_03_Step: Click button cai dat dang nhap-------------------------------");
		settingLogin.clickToDynamicImageViewByID(driver, "com.VCB:id/settingAuthen");

		log.info("-------------------------------TC_03_Step:Verify man hinh noi dung thong bao-------------------------------");
		verifyEquals(settingLogin.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), Setting_Login_Data.MESSEGE_KICH_HOAT);

		log.info("-------------------------------TC_03_Step: Click btn dong y-------------------------------");
		settingLogin.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

		log.info("-------------------------------TC_03_Step: Doi cho den khi tat thong bao-------------------------------");
		settingLogin.sleep(driver, 5000);

	}

	@Parameters({ "pass" })
	@Test
	public void TC_04_HuyCaiDatXacThucDangNhapDichVuVCB(String pass) {
		log.info("-------------------------------TC_04_Step: Click button cai dat dang nhap-------------------------------");
		settingLogin.clickToDynamicImageViewByID(driver, "com.VCB:id/settingAuthen");
		
		log.info("-------------------------------TC_04_Step: Verify noi dung thong bao-------------------------------");
		verifyEquals(settingLogin.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), Setting_Login_Data.MESSEGE_HUY_KICH_HOAT);
		
		log.info("-------------------------------TC_04_Step: Click dong y-------------------------------");
		settingLogin.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
		
		log.info("-------------------------------TC_04_Step: Verify man hinh thong bao-------------------------------");
		verifyEquals(settingLogin.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), "Thông báo");
		
		log.info("-------------------------------TC_04_Step: Nhap mat khau-------------------------------");
		settingLogin.inputIntoEditTextByID(driver, pass, "com.VCB:id/edtPassWord");
		
		log.info("-------------------------------TC_04_Step: Click ok-------------------------------");
		settingLogin.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");
		
		log.info("-------------------------------TC_04_Step: Doi cho den khi tat thong bao-------------------------------");
		settingLogin.sleep(driver, 5000);

	}
}
