package vnpay.vietcombank.sdk.filmTicketBooking;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
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
		filmTicketBooking.clickToDynamicTextView(cinemaName);
		
		log.info("TC_05_03_Kiem tra ten rap phim");
		verifyTrue(filmTicketBooking.isDynamicTextViewDisplayed(cinemaName));
		
		log.info("TC_05_04_Kiem tra hien thi 2 tab Dang chieu va Sap chieu");
		verifyTrue(filmTicketBooking.isDynamicTextViewDisplayed("Đang chiếu"));
		verifyTrue(filmTicketBooking.isDynamicTextViewDisplayed("Sắp chiếu"));
		
		log.info("TC_05_05_Kiem tra hien thi thong tin phim");
		verifyTrue(filmTicketBooking.isDynamicTextViewDisplayedByID("com.VCB:id/tvFilmName"));
		verifyTrue(filmTicketBooking.isDynamicTextViewDisplayedByID("com.VCB:id/tvCategory"));
		verifyTrue(filmTicketBooking.isDynamicTextViewDisplayedByID("com.VCB:id/tvDuration"));
		verifyTrue(filmTicketBooking.isDynamicTextViewDisplayedByID("com.VCB:id/tvIMDb"));
		verifyTrue(filmTicketBooking.isDynamicTextViewDisplayed("Đặt vé")); 
		verifyTrue(filmTicketBooking.isDynamicImageViewDisplayed("com.VCB:id/ivFilmThumbnail"));
		
	}
	
	@Test
	public void TC_06_ChonTheoRap_ChonRap_KiemTraThongTinChiTietManHinh() {

	}
	
	@Test
	public void TC_07_ChonTheoRap_ChonRap_KiemTraNutBack() {
		log.info("TC_07_01_Nhan nut Back");
		filmTicketBooking.clickToDynamicImageViewByID("com.VCB:id/ivBack");
		
		log.info("TC_07_01_Kiem tra tro ve man hinh Mua ve xem phim");
		verifyTrue(filmTicketBooking.isDynamicTextViewDisplayed("Mua vé xem phim"));
		
	}
	
	String cinemaName = "";
	FilmInfo filmInfo = null;
	@Test
	public void TC_08_ChonTheoRap_ChonRap_XemChiTietPhimDangChieuVaThongTinChiTiet() {
		log.info("TC_08_01_Click chon rap phim");
		List<String> listCinema = filmTicketBooking.getListOfSuggestedMoneyOrListText("com.VCB:id/tvNameCinema");
		cinemaName = listCinema.get(0);
		filmTicketBooking.clickToDynamicTextView(cinemaName);
		
		filmInfo = filmTicketBooking.getInfoOfTheFirstFilm();
		log.info("TC_08_02_Click xem chi tiet phim");
		filmTicketBooking.clickToDynamicTextView(filmInfo.filmName);
		
		log.info("TC_08_03_Kiem tra chi tiet phim");
		log.info("TC_08_03_01_Kiem tra ten phim");
		verifyEquals(filmTicketBooking.getTextViewByID("com.VCB:id/tvFilmName"), filmInfo.filmName);
		
		log.info("TC_08_03_02_Kiem tra the loai phim");
		verifyEquals(filmTicketBooking.getTextViewByID("com.VCB:id/tvCategories"), filmInfo.filmCategory);
		
		log.info("TC_08_03_03_Kiem tra danh gia phim");
		verifyEquals(filmTicketBooking.getTextViewByID("com.VCB:id/tvIMDb"), filmInfo.filmRate);
		
		log.info("TC_08_03_04_Kiem tra thoi luong phim");
		verifyEquals(filmTicketBooking.getTextViewByID("com.VCB:id/tvTime"), filmTicketBooking.canculateDurationOfFilm(filmInfo.filmDuration));
		
		log.info("TC_08_03_05_Kiem tra hien thi ngay chieu");
		verifyTrue(filmTicketBooking.isDynamicTextViewDisplayedByID("com.VCB:id/tvDate"));
		
		log.info("TC_08_03_06_Kiem tra hien thi phien ban");
		verifyTrue(filmTicketBooking.isDynamicTextViewDisplayedByID("com.VCB:id/tvVersion"));
		
		log.info("TC_08_03_07_Kiem tra hien thi noi dung phim");
		verifyTrue(filmTicketBooking.isDynamicTextViewDisplayed("Nội dung"));
		
		log.info("TC_08_03_08_Kiem tra hien thi dien vien");
		verifyTrue(filmTicketBooking.isDynamicTextViewDisplayed("Diễn viên"));
		
		log.info("TC_08_03_09_Kiem tra hien thi dao dien");
		verifyTrue(filmTicketBooking.isDynamicTextViewDisplayed("Đạo diễn"));
		
		log.info("TC_08_03_10_Kiem tra hien thi nut Dat ve");
		verifyTrue(filmTicketBooking.isDynamicTextViewDisplayed("Đặt vé"));
		
	}
	
	@Test
	public void TC_09_ChonTheoRap_ChonRap_KiemTraNutBack() {
		log.info("TC_09_01_Nhan nut Back");
		filmTicketBooking.clickToDynamicImageViewByID("com.VCB:id/ivBack");
		
		log.info("TC_09_02_Kiem tra tro ve man hinh danh sach phim dang chieu");
		verifyTrue(filmTicketBooking.isDynamicTextViewDisplayed("Đang chiếu"));
		
	}
	
	@Test
	public void TC_10_ChonTheoRap_ChonSuatChieu_KiemTraManHinhChonSuatChieu_VaThongTinChiTiet() {
		log.info("TC_10_01_Nhan nut Dat ve");
		filmTicketBooking.clickToDynamicTextView("Đặt vé");

		log.info("TC_10_02_Kiem tra man hinh chon suat chieu");
		log.info("TC_10_02_01_Kiem tra thong tin suat chieu");
		List<String> actualListDay = filmTicketBooking.getListFilmSchedule();
		
		LocalDate now = LocalDate.now();
		LocalDate datePlus1 = now.plusDays(1);
		LocalDate datePlus2 = now.plusDays(2);
		LocalDate datePlus3 = now.plusDays(3);
		String today = getForwardDate(0);
		String todayPlus1 = getForwardDate(1);
		String todayPlus2 = getForwardDate(2);
		String todayPlus3 = getForwardDate(3);
		List<String> expectListDay = new ArrayList<String>();
		expectListDay.add(today.split("/")[0] + "/" + today.split("/")[1] + "Hôm nay");
		expectListDay.add(todayPlus1.split("/")[0] + "/" + todayPlus1.split("/")[1] + convertDayOfWeekVietNamese(getCurrentDayOfWeek(datePlus1)));
		expectListDay.add(todayPlus2.split("/")[0] + "/" + todayPlus2.split("/")[1] + convertDayOfWeekVietNamese(getCurrentDayOfWeek(datePlus2)));
		expectListDay.add(todayPlus3.split("/")[0] + "/" + todayPlus3.split("/")[1] + convertDayOfWeekVietNamese(getCurrentDayOfWeek(datePlus3)));
		
		verifyEquals(actualListDay, expectListDay);
		
		log.info("TC_10_02_02_Kiem tra thong tin phim");
		verifyEquals(filmTicketBooking.getTextViewByID("com.VCB:id/tvFilmName"), filmInfo.filmName);
		verifyEquals(filmTicketBooking.getTextViewByID("com.VCB:id/tvCinemaName"), cinemaName);
		verifyTrue(filmTicketBooking.isDynamicTextViewDisplayed("2D") || filmTicketBooking.isDynamicTextViewDisplayed("3D"));
		
		log.info("TC_10_02_03_Kiem tra icon back");
		verifyTrue(filmTicketBooking.isDynamicImageViewDisplayed("com.VCB:id/ivBack"));
		
	}
	
	@Test
	public void TC_11_ChonTheoRap_ChonSuatChieu_KiemTraNutBack() {
		log.info("TC_11_01_Nhan nut Back");
		filmTicketBooking.clickToDynamicImageViewByID("com.VCB:id/ivBack");
		
		log.info("TC_11_01_Kiem tra tro ve man hinh danh sach phim dang chieu");
		verifyTrue(filmTicketBooking.isDynamicTextViewDisplayed("Đang chiếu"));
		
	}
	
	@Test
	public void TC_12_ChonTheoRap_ChonSuatChieu_KiemTraNgayChieuMacDinh() {
		log.info("TC_12_01_Click xem chi tiet phim");
		filmTicketBooking.clickToDynamicTextView(filmInfo.filmName);
	
		log.info("TC_12_02_Nhan nut Dat ve");
		filmTicketBooking.clickToTextViewByText("Đặt vé");
		
		log.info("TC_12_03_Kiem tra highlight ngay chieu Hom nay");
		verifyTrue(filmTicketBooking.isDynamicTextViewSelected("Hôm nay"));
		
	}
	
	@Test
	public void TC_13_ChonTheoRap_ChonSuatChieu_KiemTraChonNgayChieu() {
		log.info("TC_13_01_Click chon ngay chieu khac");
		String todayPlus1 = getForwardDate(1);
		String filmDate = todayPlus1.split("/")[0] + "/" + todayPlus1.split("/")[1];
		filmTicketBooking.clickToDynamicTextView(filmDate);
		
		log.info("TC_13_02_Kiem tra highlight ngay chieu da chon");
		verifyTrue(filmTicketBooking.isDynamicTextViewSelected(filmDate));
		
	}
	
	@Test
	public void TC_14_ChonTheoRap_ChonSuatChieu_KiemTraThoiGianChonToiDa() {
		String todayPlus1 = getForwardDate(10);
		String filmDate = todayPlus1.split("/")[0] + "/" + todayPlus1.split("/")[1];
		
		log.info("TC_14_01_Swipe den ngay chieu cuoi cung");
		filmTicketBooking.swipeToTheEndDate(3);
		
		log.info("TC_14_02_Click chon ngay chieu khac");
		filmTicketBooking.clickToDynamicTextView(filmDate);
		
		log.info("TC_14_03_Kiem tra highlight ngay chieu da chon");
		verifyTrue(filmTicketBooking.isDynamicTextViewSelected(filmDate));
		
	}
	
	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
