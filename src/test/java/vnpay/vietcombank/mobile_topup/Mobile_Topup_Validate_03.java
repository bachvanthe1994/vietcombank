package vnpay.vietcombank.mobile_topup;

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
import pageObjects.MobileTopupPageObject;
import vietcombank_test_data.MobileTopupPage_Data.UIs;

public class Mobile_Topup_Validate_03 extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject home;
	private MobileTopupPageObject mobileTopup;
	
	private String originAccountNumber = "";
	private String originPhoneNumber = "";

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
	}

	@Test
	public void TC_01_ManHinhXacNhanThongTin_KiemTraManHinhHienThi() {

		
	}

	@Test
	public void TC_02_KiemTraBoTrongSoDienThoaiMacDinh() {
		
	
		
	}
	
	@Test
	public void TC_03_KiemTraGioiHanKiTu() {
		
		
	
	}
	
	@Test
	public void TC_04_NhanIconX() {
		
	}
	
	@Test
	public void TC_05_NhapSoDienThoaiNhoHon10KiTu() {
		
		
	}
	
	@Test
	public void TC_06_NhapSoDienThoaiBatDauBangKiTuKhac0() {
		
		
	}
	
	@Test
	public void TC_07_NhapSoDienThoaiKhongDungQuyDinhCuaMangVienThong() {
		
		
	
	}
	
	@Test
	public void TC_08_SoTienGiaoDichLonHonSoDuTKNguon() {
		
		
		
	}
	
	@Test
	public void TC_09_KhoiTaoGiaoDichHopLe() {
		
		
	}
	
	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
