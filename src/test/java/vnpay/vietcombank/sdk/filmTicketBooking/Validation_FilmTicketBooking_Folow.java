package vnpay.vietcombank.sdk.filmTicketBooking;

import java.io.IOException;
import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import model.FilmInfo;
import pageObjects.LogInPageObject;
import pageObjects.sdk.filmTicketBooking.FilmTicketBookingPageObject;

public class Validation_FilmTicketBooking_Folow extends Base {
	AppiumDriver<MobileElement> driver;
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
	public void TC_01_DatVeXemPhim() {
		filmTicketBooking = PageFactoryManager.getFilmTicketBookingPageObject(driver);

		log.info("TC_01_01_Click Dat ve xem phim");
		filmTicketBooking.clickToDynamicTextOrButtonLink("Đặt vé xem phim");

		log.info("TC_01_02_Click nut Dong y");
		filmTicketBooking.clickToDynamicButton("Đồng ý");
		
		log.info("TC_01_03_Click chon Tinh thanh");
		filmTicketBooking.clickToDynamicTextViewByID("com.VCB:id/tvLocationName");

		log.info("TC_03_02_Tim kiem thanh pho");
		filmTicketBooking.inputIntoEditTextByID("Hồ Chí Minh", "com.VCB:id/edtSearch");

		log.info("TC_03_03_Click chon thanh pho");
		filmTicketBooking.clickToDynamicTextView("Hồ Chí Minh");
		
		log.info("TC_01_02_Click chon cum rap Mega GS");
		filmTicketBooking.clickToDynamicTextView("Mega GS");
		
		log.info("TC_01_02_Click chon rap phim");
		List<String> listCinema = filmTicketBooking.getListOfSuggestedMoneyOrListText("com.VCB:id/tvNameCinema");
		String cinemaName = listCinema.get(0);
		filmTicketBooking.clickToDynamicTextView(cinemaName);

		log.info("TC_01_03_Click xem chi tiet phim");
		FilmInfo filmInfo = filmTicketBooking.getInfoOfTheFirstFilm();
		filmTicketBooking.clickToDynamicTextView(filmInfo.filmName);

		log.info("TC_01_04_Nhan nut Dat ve");
		filmTicketBooking.clickToTextViewByText("Đặt vé");

		log.info("TC_01_05_Nhan chon gio chieu");
		filmTicketBooking.clickToDynamicTextViewByViewGroupID("com.VCB:id/tagShowtimes2D", "0");
		
		log.info("TC_06_03_Thay doi so luong ve tong ve bang 10");
		filmTicketBooking.clickToChangeNumberSeatSum10Tickets();
		
		log.info("TC_07_01_Click chon cho ngoi");
		filmTicketBooking.clickToTextViewByText("Chọn chỗ ngồi");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();
	}

}
