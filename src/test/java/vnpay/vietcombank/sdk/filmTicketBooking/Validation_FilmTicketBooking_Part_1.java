package vnpay.vietcombank.sdk.filmTicketBooking;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import pageObjects.LogInPageObject;
import pageObjects.sdk.filmTicketBooking.FilmTicketBookingPageObject;

public class Validation_FilmTicketBooking_Part_1 extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private FilmTicketBookingPageObject filmTicketBooking;
	
	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openGlobalSetting(deviceName, udid, url);
		filmTicketBooking = PageFactoryManager.getFilmTicketBookingPageObject(driver);
		filmTicketBooking.turnOnSwitchInGlobalSetting("Vị trí");
		
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);

	}

	@Test
	public void TC_01_ChonTheoRap_KiemTraManHinhMuaVeXemPhim() {
		filmTicketBooking = PageFactoryManager.getFilmTicketBookingPageObject(driver);

		log.info("TC_01_01_Click Dat ve xem phim");
		filmTicketBooking.clickToDynamicTextOrButtonLink("Đặt vé xem phim");

		log.info("TC_01_02_Click nut Dong ý");
		filmTicketBooking.clickToDynamicButton("Đồng ý");

		log.info("TC_01_03_Kiem tra man hinh mua ve xem phim");

	}

	@Test
	public void TC_02_ChonTheoRap_KiemTraNutBack() {
		log.info("TC_02_01_Click nut Back");
		filmTicketBooking.clickToDynamicBackIcon("Mua vé xem phim");

		log.info("TC_02_02_Kiem tra tro ve man hinh Home");
		verifyTrue(filmTicketBooking.isDynamicTextViewDisplayed("Đặt vé xem phim"));
		
		log.info("TC_02_03_Click Dat ve xem phim");
		filmTicketBooking.clickToDynamicTextOrButtonLink("Đặt vé xem phim");
		
		log.info("TC_02_04_Click nut Dong ý");
		filmTicketBooking.clickToDynamicButton("Đồng ý");
		
	}
	
	@Test
	public void TC_03_ChonTheoRap_DanhSachTinhThanhVaTenRap_KiemTraHienThiMacDinh_ThietBiDaBatDinhVi(String deviceName, String udid, String url) throws IOException, InterruptedException {
		log.info("TC_03_01_Kiem tra tinh thanh hien thi mac dinh");
		verifyEquals(filmTicketBooking.getTextViewByID("com.VCB:id/tvLocationName"), filmTicketBooking.getLocationByCheckSwitchLocationInGlobalSetting(true));
		
		log.info("TC_03_02_Tat dinh vi trong setting");
		driver = openGlobalSetting(deviceName, udid, url);
		filmTicketBooking = PageFactoryManager.getFilmTicketBookingPageObject(driver);
		filmTicketBooking.turnOffSwitchInGlobalSetting("Vị trí");
		
		
	}
	
	@Test
	public void TC_04_ChonTheoRap_DanhSachTinhThanhVaTenRap_KiemTraHienThiMacDinh_ThietBiDaTatDinhVi(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		log.info("TC_04_01_Tat dinh vi trong setting");
		driver = openGlobalSetting(deviceName, udid, url);
		filmTicketBooking = PageFactoryManager.getFilmTicketBookingPageObject(driver);
		filmTicketBooking.turnOffSwitchInGlobalSetting("Vị trí");
		
		log.info("TC_04_01_Bat lai app");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
		
		log.info("TC_04_02_Truy cap den chuc nang dat ve xem phim");
		filmTicketBooking = PageFactoryManager.getFilmTicketBookingPageObject(driver);
		filmTicketBooking.clickToDynamicTextOrButtonLink("Đặt vé xem phim");
		filmTicketBooking.clickToDynamicButton("Đồng ý");
		
		log.info("TC_04_03_Kiem tra tinh thanh hien thi mac dinh");
		verifyEquals(filmTicketBooking.getTextViewByID("com.VCB:id/tvLocationName"), filmTicketBooking.getLocationByCheckSwitchLocationInGlobalSetting(false));
		
	}
	
	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();
	}

}
