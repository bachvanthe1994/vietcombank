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
import model.FilmInfo;
import pageObjects.LogInPageObject;
import pageObjects.sdk.filmTicketBooking.FilmTicketBookingPageObject;
import vietcombankUI.sdk.filmTicketBooking.FilmTicketBookingPageUIs;

public class Validation_FilmTicketBooking_Part_6 extends Base {
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

		log.info("TC_00_03_Click chon Tab Phim");
		filmTicketBooking.clickToTextViewByText("Phim");
		
		log.info("TC_00_04_Nhan nut Dat ve");
		filmTicketBooking.clickToDynamicTextView("Đặt vé");
	}
	
	@Test	
	public void TC_01_ChonTheoPhim_ChonSuatChieu_ChonSuatChieuThanhCongVoiRap_Cinestar() {
		log.info("TC_01_01_Click chon Tinh thanh");
		filmTicketBooking.clickToDynamicTextViewByID("com.VCB:id/tvLocationName");
		
		log.info("TC_01_02_Tim kiem thanh pho");
		filmTicketBooking.inputIntoEditTextByID("Hồ Chí Minh", "com.VCB:id/edtSearch");;

		log.info("TC_01_03_Click chon thanh pho");
		filmTicketBooking.clickToDynamicTextView("Hồ Chí Minh");
		
		log.info("TC_01_04_Click chon cum rap Cinestar");
		filmTicketBooking.clickToDynamicTextView("Cinestar");
		
		log.info("TC_01_05_Lay ten rap");
		cinemaName = filmTicketBooking.getTextViewByID("com.VCB:id/tvCinemaName");
		
		log.info("TC_01_06_Nhan chon gio chieu");
		filmTicketBooking.clickToDynamicTextViewByViewGroupID("com.VCB:id/tagShowtimes2D", "0");
		
	}
	
	@Test
	public void TC_02_ChonTheoPhim_ChonChoNgoi_KiemTraManHinhChonChoNgoi_ThongTinChiTiet() {
		log.info("TC_02_01_Kiem tra ten rap");
		verifyEquals(filmTicketBooking.getTextViewByID("com.VCB:id/tvTitle"), cinemaName);
		
		log.info("TC_02_02_Kiem tra nut back");
		verifyTrue(filmTicketBooking.isDynamicImageViewDisplayed("com.VCB:id/ivBack"));

	}
	
	@Test
	public void TC_03_ChonTheoPhim_ChonChoNgoi_KiemTraChonGhe_KhongChonGheNao() {
		log.info("TC_03_01_Click Thanh toan");
		filmTicketBooking.clickToTextViewByText("Thanh toán");
		
		log.info("TC_03_02_Kiem tra Thong bao");
		verifyEquals(filmTicketBooking.getTextInDynamicPopup("com.VCB:id/tvContent"), "Quý khách vui lòng chọn ghế");
		
		log.info("TC_03_03_Dong Thong bao");
		filmTicketBooking.clickToTextViewByText("Đồng ý");
		
	}
	
	@Test
	public void TC_04_ChonTheoPhim_ChonChoNgoi_KiemTraChonGhe_BoTrongGheOGiua() {
		log.info("TC_04_01_Chon ghe bo trong ghe o giua");
		filmTicketBooking.chooseSeatsByLineEmptyBetweenSeat("A");
		
		log.info("TC_04_02_Click Thanh toan");
		filmTicketBooking.clickToTextViewByText("Thanh toán");
		
		log.info("TC_04_03_Kiem tra Thong bao");
		verifyEquals(filmTicketBooking.getTextInDynamicPopup("com.VCB:id/tvContent"), "Quý khách không thể bỏ trống ghế ở giữa. Vui lòng chọn lại.");
		
		log.info("TC_04_04_Dong Thong bao");
		filmTicketBooking.clickToTextViewByText("Đồng ý");
		
		log.info("TC_04_05_Bo ghe da chon");
		filmTicketBooking.chooseSeatsByLineEmptyBetweenSeat("A");
		
	}
	
	@Test
	public void TC_05_ChonTheoPhim_ChonChoNgoi_KiemTraChonGhe_BoTrongGheNgoaiCung() {
		log.info("TC_05_01_Chon ghe bo trong ghe o giua");
		filmTicketBooking.chooseSeatsByLineEmptyLastSeat("A");
		
		log.info("TC_05_02_Click Thanh toan");
		filmTicketBooking.clickToTextViewByText("Thanh toán");
		
		log.info("TC_05_03_Kiem tra Thong bao");
		verifyEquals(filmTicketBooking.getTextInDynamicPopup("com.VCB:id/tvContent"), "Quý khách không thể bỏ trống ghế ngoài cùng bên phải hoặc bên trái, vui lòng chọn lại.");
		
		log.info("TC_05_04_Dong Thong bao");
		filmTicketBooking.clickToTextViewByText("Đồng ý");
		
		log.info("TC_05_05_Nhan nut Back");
		filmTicketBooking.clickToDynamicImageViewByID("com.VCB:id/ivBack");
		
		log.info("TC_05_06_Nhan chon gio chieu");
		filmTicketBooking.clickToDynamicTextViewByViewGroupID("com.VCB:id/tagShowtimes2D", "0");
		
	}
	
	@Test
	public void TC_06_ChonTheoPhim_ChonChoNgoi_KiemTraChonGhe_ChonToiDaSoGhe() {
		log.info("TC_06_01_Lay tong tien luc dau");
		String beforePrice = filmTicketBooking.getTextViewByID("com.VCB:id/tvPrince");
		
		log.info("TC_06_02_Click chon cho ngoi loai ghe Thuong");
		String colorOfSeat = filmTicketBooking.getColorOfElement(FilmTicketBookingPageUIs.VIEW_BY_TEXT, "Standard");
		String checkColor = filmTicketBooking.getColorOfElement(FilmTicketBookingPageUIs.VIEW_BY_TEXT, "Đang chọn");
		verifyTrue(filmTicketBooking.chooseSeatsAndCheckColorAfterChoose(10, colorOfSeat, checkColor));
		
		log.info("TC_06_03_Lay tong tien luc sau");
		String afterPrice = filmTicketBooking.getTextViewByID("com.VCB:id/tvPrince");
		
		log.info("TC_06_04_Kiem tra tong tien");
		verifyTrue(!beforePrice.equals(afterPrice));
		
		log.info("TC_06_05_Nhan nut Back");
		filmTicketBooking.clickToDynamicImageViewByID("com.VCB:id/ivBack");
		
		log.info("TC_06_06_Nhan chon gio chieu");
		filmTicketBooking.clickToDynamicTextViewByViewGroupID("com.VCB:id/tagShowtimes2D", "0");
		
	}
	
	@Test
	public void TC_07_ChonTheoPhim_ChonChoNgoi_KiemTraChonGhe_DaDat() {
		log.info("TC_07_01_Lay mau cua loai ghe Da Dat");
		String colorOfSeat = filmTicketBooking.getColorOfElement(FilmTicketBookingPageUIs.VIEW_BY_TEXT, "Đã đặt");
		
		log.info("TC_07_03_Click chon cho ngoi loai ghe Da dat");
		verifyTrue(filmTicketBooking.chooseSeatsAndCheckColorAfterChoose(2, colorOfSeat, colorOfSeat));
		
	}
	
	@Test
	public void TC_08_ChonTheoPhim_ChonChoNgoi_KiemTraChonGhe_Thuong() {
		log.info("TC_08_01_Lay mau cua loai ghe Thuong");
		String colorOfSeat = filmTicketBooking.getColorOfElement(FilmTicketBookingPageUIs.VIEW_BY_TEXT, "Standard");
		String checkColor = filmTicketBooking.getColorOfElement(FilmTicketBookingPageUIs.VIEW_BY_TEXT, "Đang chọn");
		
		log.info("TC_08_02_Click chon cho ngoi loai ghe Thuong");
		verifyTrue(filmTicketBooking.chooseSeatsAndCheckColorAfterChoose(2, colorOfSeat, checkColor));
		
	}
	
	@Test
	public void TC_09_ChonTheoPhim_ChonChoNgoi_KiemTraChonGhe_DaChon() {
		log.info("TC_09_01_Lay mau cua loai ghe Da chon");
		String colorOfSeat = filmTicketBooking.getColorOfElement(FilmTicketBookingPageUIs.VIEW_BY_TEXT, "Đang chọn");
		String checkColor = filmTicketBooking.getColorOfElement(FilmTicketBookingPageUIs.VIEW_BY_TEXT, "Standard");
		
		log.info("TC_09_02_Click chon cho ngoi loai ghe Da chon");
		verifyTrue(filmTicketBooking.chooseSeatsAndCheckColorAfterChoose(2, colorOfSeat, checkColor));
		
	}
	
	@Test
	public void TC_10_ChonTheoPhim_ChonChoNgoi_KiemTraChonGhe_Vip() {
		log.info("TC_10_01_Lay mau cua loai ghe Vip");
		String colorOfSeat = filmTicketBooking.getColorOfElement(FilmTicketBookingPageUIs.VIEW_BY_TEXT, "Vip");
		String checkColor = filmTicketBooking.getColorOfElement(FilmTicketBookingPageUIs.VIEW_BY_TEXT, "Đang chọn");

		log.info("TC_10_02_Click chon cho ngoi loai ghe Vip");
		verifyTrue(filmTicketBooking.chooseSeatsAndCheckColorAfterChoose(2, colorOfSeat, checkColor));

	}
	
	@Test
	public void TC_11_ChonTheoPhim_ChonChoNgoi_KiemTraChonGhe_Deluxe() {
		log.info("TC_11_01_Lay mau cua loai ghe Deluxe");
		String colorOfSeat = filmTicketBooking.getColorOfElement(FilmTicketBookingPageUIs.VIEW_BY_TEXT, "Deluxe");
		String checkColor = filmTicketBooking.getColorOfElement(FilmTicketBookingPageUIs.VIEW_BY_TEXT, "Đang chọn");

		log.info("TC_11_02_Click chon cho ngoi loai ghe Deluxe");
		verifyTrue(filmTicketBooking.chooseSeatsAndCheckColorAfterChoose(2, colorOfSeat, checkColor));
		
	}
	
	@Test
	public void TC_12_ChonTheoPhim_ChonChoNgoi_KiemTraChonGhe_Couple() {
		log.info("TC_12_01_Lay mau cua loai ghe Couple");
		String colorOfSeat = filmTicketBooking.getColorOfElement(FilmTicketBookingPageUIs.VIEW_BY_TEXT, "Couple");
		String checkColor = filmTicketBooking.getColorOfElement(FilmTicketBookingPageUIs.VIEW_BY_TEXT, "Đang chọn");

		log.info("TC_12_02_Click chon cho ngoi loai ghe Couple");
		verifyTrue(filmTicketBooking.chooseSeatsAndCheckColorAfterChoose(2, colorOfSeat, checkColor));
		
	}
	
	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
