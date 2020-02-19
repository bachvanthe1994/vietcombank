package vnpay.vietcombank.sdk.filmTicketBooking;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import vnpay.vietcombank.sdk.filmTicketBooking.data.FilmTicketBooking_Data;

public class Validation_FilmTicketBooking_Part_2 extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private FilmTicketBookingPageObject filmTicketBooking;
	
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
	public void TC_01_ChonTheoRap_KiemTraHienThiDanhSachTinhThanh() {
		filmTicketBooking = PageFactoryManager.getFilmTicketBookingPageObject(driver);

		log.info("TC_01_01_Click Dat ve xem phim");
		filmTicketBooking.clickToDynamicTextOrButtonLink("Đặt vé xem phim");

		log.info("TC_01_02_Click nut Dong y");
		filmTicketBooking.clickToDynamicButton("Đồng ý");

		log.info("TC_01_03_Click chon Tinh thanh");
		filmTicketBooking.clickToDynamicTextViewByID("com.VCB:id/tvLocationName");

		log.info("TC_01_04_Kiem tra man hinh tinh thanh, danh sach tinh thanh");
		List<String> actualListCity = filmTicketBooking.getListCity();
		verifyEquals(actualListCity, FilmTicketBooking_Data.LIST_CITY);
		
	}

	List<String> actualListCinema = new ArrayList<String>();
	@Test
	public void TC_02_ChonTheoRap_ChonMotTinhThanh() {
		log.info("TC_02_01_Tim kiem thanh pho");
		filmTicketBooking.inputIntoEditTextByID("Hà Nội", "com.VCB:id/edtSearch");;

		log.info("TC_02_02_Click chon thanh pho");
		filmTicketBooking.clickToDynamicTextView("Hà Nội");
		
		log.info("TC_02_03_Kiem tra hien thi tinh thanh duoc chon");
		verifyEquals(filmTicketBooking.getTextViewByID("com.VCB:id/tvLocationName"), "Hà Nội");
		
		log.info("TC_02_04_Kiem tra danh sach rap theo thanh pho");
		actualListCinema = filmTicketBooking.getListOfSuggestedMoneyOrListText("com.VCB:id/tvNameGroup");
		verifyEquals(actualListCinema, FilmTicketBooking_Data.LIST_CINEMA_HANOI);
		
	}
	
	@Test
	public void TC_03_ChonTheoRap_ChonMotTinhThanhKhac() {
		log.info("TC_03_01_Click chon Tinh thanh");
		filmTicketBooking.clickToDynamicTextViewByID("com.VCB:id/tvLocationName");
		
		log.info("TC_03_02_Tim kiem thanh pho");
		filmTicketBooking.inputIntoEditTextByID("Hồ Chí Minh", "com.VCB:id/edtSearch");;

		log.info("TC_03_03_Click chon thanh pho");
		filmTicketBooking.clickToDynamicTextView("Hồ Chí Minh");
		
		log.info("TC_03_04_Kiem tra hien thi tinh thanh duoc chon");
		verifyEquals(filmTicketBooking.getTextViewByID("com.VCB:id/tvLocationName"), "Hồ Chí Minh");
		
		log.info("TC_03_05_Kiem tra danh sach rap theo thanh pho");
		actualListCinema = filmTicketBooking.getListOfSuggestedMoneyOrListText("com.VCB:id/tvNameGroup");
		verifyEquals(actualListCinema, FilmTicketBooking_Data.LIST_CINEMA_HCM);
		
	}
	
	@Test
	public void TC_04_ChonTheoRap_KiemTraDanhSachIconVaTenRap() {
		log.info("TC_04_01_Click chon Tinh thanh");
		verifyTrue(filmTicketBooking.checkIconAndCinemaName(actualListCinema));
		
	}
	
	@Test
	public void TC_05_ChonTheoRap_ChonRap_KiemTraDanhSachPhimDangChieu() {
		log.info("TC_05_01_Lay danh sach rap phim");
		List<String> listCinema = filmTicketBooking.getListOfSuggestedMoneyOrListText("com.VCB:id/tvNameCinema");
		
		String cinemaName = listCinema.get(0);
		log.info("TC_05_02_Click chon 1 rap phim");
		filmTicketBooking.clickToDynamicTextViewByID(cinemaName);
		
		log.info("TC_05_03_Kiem tra man hinh danh sach phim");
		verifyTrue(filmTicketBooking.isDynamicTextViewDisplayed(cinemaName));
		
	}
	
	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
