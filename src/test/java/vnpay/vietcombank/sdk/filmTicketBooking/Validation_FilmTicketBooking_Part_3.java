package vnpay.vietcombank.sdk.filmTicketBooking;

import java.io.IOException;
import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import model.FilmInfo;
import model.SeatType;
import model.SeatType.TypeButton;
import pageObjects.LogInPageObject;
import pageObjects.sdk.filmTicketBooking.FilmTicketBookingPageObject;

public class Validation_FilmTicketBooking_Part_3 extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private FilmTicketBookingPageObject filmTicketBooking;
	String cinemaName = "";
	FilmInfo filmInfo = null;
	
	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);

		filmTicketBooking = PageFactoryManager.getFilmTicketBookingPageObject(driver);
		log.info("TC_00_01_Click Dat ve xem phim");
		filmTicketBooking.clickToDynamicTextOrButtonLink("Đặt vé xem phim");

		log.info("TC_00_02_Click nut Dong y");
		filmTicketBooking.clickToDynamicButton("Đồng ý");

		log.info("TC_00_03_Click chon Tinh thanh");
		filmTicketBooking.clickToDynamicTextViewByID("com.VCB:id/tvLocationName");

		log.info("TC_00_04_Tim kiem thanh pho");
		filmTicketBooking.inputIntoEditTextByID("Hồ Chí Minh", "com.VCB:id/edtSearch");;

		log.info("TC_00_05_Click chon thanh pho");
		filmTicketBooking.clickToDynamicTextView("Hồ Chí Minh");
		
		log.info("TC_00_06_Click chon cum rap Mega GS");
		filmTicketBooking.clickToDynamicTextView("Mega GS");
		
		log.info("TC_00_07_Click chon rap phim");
		List<String> listCinema = filmTicketBooking.getListOfSuggestedMoneyOrListText("com.VCB:id/tvNameCinema");
		cinemaName = listCinema.get(0);
		filmTicketBooking.clickToDynamicTextView(cinemaName);
		
		log.info("TC_00_08_Click xem chi tiet phim");
		filmInfo = filmTicketBooking.getInfoOfTheFirstFilm();
		filmTicketBooking.clickToDynamicTextView(filmInfo.filmName);
		
		log.info("TC_00_09_Nhan nut Dat ve");
		filmTicketBooking.clickToDynamicTextView("Đặt vé");
		
		log.info("TC_00_10_Nhan chon gio chieu");
		filmTicketBooking.clickToDynamicTextViewByViewGroupID("com.VCB:id/tagShowtimes2D", "0");
		
	}

	@Test
	public void TC_01_ChonTheoRap_ChonSoLuongVe_KiemTraManHinh_ThongTinChiTiet() {
		log.info("TC_01_01_Kiem tra man hinh chon so luong ve");
		verifyTrue(filmTicketBooking.isDynamicTextViewDisplayed("Chọn số lượng vé"));
		
		log.info("TC_01_02_Kiem tra icon back");
		verifyTrue(filmTicketBooking.isDynamicImageViewDisplayed("com.VCB:id/ivBack"));
		
		log.info("TC_01_03_Kiem tra thong tin phim");
		log.info("TC_01_03_01_Kiem tra anh phim");
		verifyTrue(filmTicketBooking.isDynamicImageViewDisplayed("com.VCB:id/ivFilmThumbnail"));
		
		log.info("TC_01_03_02_Kiem tra ten phim");
		verifyEquals(filmTicketBooking.getTextViewByID("com.VCB:id/tvFilmName"), filmInfo.filmName);
		
		log.info("TC_01_03_03_Kiem tra ten rap");
		verifyEquals(filmTicketBooking.getTextViewByID("com.VCB:id/tvCinemaName"), cinemaName);
		
		log.info("TC_01_03_04_Kiem tra thoi gian chieu");
//		verifyEquals(filmTicketBooking.getTextViewByID("com.VCB:id/tvTime"), cinemaName);
//		verifyEquals(filmTicketBooking.getTextViewByID("com.VCB:id/tvDate"), cinemaName);
		
		List<SeatType> seats = filmTicketBooking.getListSeatType();
		log.info("TC_01_04_01_Kiem tra danh sach loai ghe");
		
		
		log.info("TC_01_04_06_Kiem gia tien");
		
		log.info("TC_01_04_07_Kiem tra so luong");
		
		log.info("TC_01_04_08_icon tang giam so luong");
		
		log.info("TC_01_04_09_Tong tien");
		verifyTrue(filmTicketBooking.isDynamicTextViewDisplayed("Tổng tiền"));
		
		log.info("TC_01_04_10_Nut chon cho ngoi disable");
		filmTicketBooking.clickToDynamicTextView("Chọn chỗ ngồi");
		verifyTrue(filmTicketBooking.isDynamicTextViewDisplayed("Chọn số lượng vé"));
		
	}
	
	@Test
	public void TC_02_ChonTheoRap_ChonSoLuongVe_KiemTraNutBack() {
		log.info("TC_02_01_Nhan nut Back");
		filmTicketBooking.clickToDynamicImageViewByID("com.VCB:id/ivBack");
		
		log.info("TC_02_02_Kiem tra tro ve man hinh lich chieu");
		verifyTrue(filmTicketBooking.isDynamicTextViewDisplayed("Lịch chiếu"));
		
	}
	
	@Test
	public void TC_03_ChonTheoRap_ChonSoLuongVe_ChonSoVeBang0() {
		log.info("TC_03_01_Nhan chon gio chieu");
		filmTicketBooking.clickToDynamicTextViewByViewGroupID("com.VCB:id/tagShowtimes2D", "0");
		
		List<SeatType> seats = filmTicketBooking.getListSeatType();
		log.info("TC_03_02_Kiem tra so luong ve mac dinh bang 0");
		verifyTrue(filmTicketBooking.checkSeatTypeDefault(seats));
		
		log.info("TC_03_03_Kiem tra nut Chon cho ngoi disable");
		filmTicketBooking.clickToDynamicTextView("Chọn chỗ ngồi");
		verifyTrue(filmTicketBooking.isDynamicTextViewDisplayed("Chọn số lượng vé"));
		
		log.info("TC_03_04_Bam chon 1 ghe");
		filmTicketBooking.clickToDynamicTextViewByID("com.VCB:id/tvPlus");
		
		log.info("TC_03_05_Kiem tra nut Chon cho ngoi enable");
		filmTicketBooking.clickToDynamicTextView("Chọn chỗ ngồi");
		verifyTrue(filmTicketBooking.isDynamicTextViewDisplayed("Thanh toán"));
		
	}
	
	@Test
	public void TC_04_ChonTheoRap_ChonSoLuongVe_ChoPhepThayDoiSoLuongVe() {
		log.info("TC_04_01_Nhan nut Back");
		filmTicketBooking.clickToDynamicImageViewByID("com.VCB:id/ivBack");
		
		log.info("TC_04_02_Thay doi so luong ve");
		filmTicketBooking.clickToChangeNumberSeat(TypeButton.INCREASE, 2);
		
		List<SeatType> seats = filmTicketBooking.getListSeatType();
		log.info("TC_04_03_Kiem tra tong tien");
		verifyEquals(filmTicketBooking.getTextViewByID("com.VCB:id/tvTotalAmount"), filmTicketBooking.canculateAmountFilmBooking(seats));
		
	}
	
	@Test
	public void TC_05_ChonTheoRap_ChonSoLuongVe_ChonSoVeToiDa() {
		log.info("TC_05_01_Nhan nut Back");
		filmTicketBooking.clickToDynamicImageViewByID("com.VCB:id/ivBack");
		
		log.info("TC_05_02_Nhan chon gio chieu");
		filmTicketBooking.clickToDynamicTextViewByViewGroupID("com.VCB:id/tagShowtimes2D", "0");
		
		log.info("TC_05_03_Thay doi so luong ve");
		filmTicketBooking.clickToChangeNumberSeat(TypeButton.INCREASE, 11);
		
		List<SeatType> seats = filmTicketBooking.getListSeatType();
		log.info("TC_05_04_Kiem tra tong tien");
		verifyEquals(filmTicketBooking.getTextViewByID("com.VCB:id/tvTotalAmount"), filmTicketBooking.canculateAmountFilmBooking(seats));
		
	}
	
	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
