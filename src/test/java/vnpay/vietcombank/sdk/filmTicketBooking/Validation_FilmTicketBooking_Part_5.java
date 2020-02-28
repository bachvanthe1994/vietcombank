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
import model.FilmInfo.TypeShow;
import pageObjects.LogInPageObject;
import pageObjects.sdk.filmTicketBooking.FilmTicketBookingPageObject;
import vnpay.vietcombank.sdk.filmTicketBooking.data.FilmTicketBooking_Data;

public class Validation_FilmTicketBooking_Part_5 extends Base {
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
		
	}
	
	@Test
	public void TC_01_ChonTheoPhim_KiemTraManHinhTabPhim() {
		log.info("TC_01_01_Kiem tra hien thi 2 tab Dang chieu va Sap chieu");
		verifyTrue(filmTicketBooking.isDynamicTextViewDisplayed("Đang chiếu"));
		verifyTrue(filmTicketBooking.isDynamicTextViewDisplayed("Sắp chiếu"));
		
		log.info("TC_01_02_Kiem tra hien thi thong tin phim");
		verifyTrue(filmTicketBooking.isDynamicTextViewDisplayedByID("com.VCB:id/tvFilmName"));
		verifyTrue(filmTicketBooking.isDynamicTextViewDisplayedByID("com.VCB:id/tvCategory"));
		verifyTrue(filmTicketBooking.isDynamicTextViewDisplayedByID("com.VCB:id/tvFilmMinute"));
		verifyTrue(filmTicketBooking.isDynamicImageViewDisplayed("com.VCB:id/ivFilmThumbnail"));
		verifyTrue(filmTicketBooking.isDynamicTextViewDisplayed("Đặt vé")); 
		
		log.info("TC_01_03_Kiem tra nut Back va nut Search");
		verifyTrue(filmTicketBooking.isDynamicImageViewDisplayed("com.VCB:id/ivBack"));
		verifyTrue(filmTicketBooking.isDynamicImageViewDisplayed("com.VCB:id/ivSearch"));
		
		log.info("TC_01_04_Kiem tra nut Dat ve");
		verifyTrue(filmTicketBooking.isDynamicTextViewDisplayed("Đặt vé"));
		
		log.info("TC_01_05_Kiem tra thanh tab cuoi man hinh");
		verifyTrue(filmTicketBooking.isDynamicTextViewDisplayed("Rạp chiếu"));
		verifyTrue(filmTicketBooking.isDynamicTextViewDisplayed("Phim"));
		verifyTrue(filmTicketBooking.isDynamicTextViewDisplayed("Vé của tôi"));
		
	}
	
	@Test
	public void TC_02_ChonTheoPhim_KiemTraNutBack() {
		log.info("TC_02_01_Nhan nut Back");
		filmTicketBooking.clickToDynamicImageViewByID("com.VCB:id/ivBack");
		
		log.info("TC_02_02_Kiem tra tro ve man hinh danh sach phim dang chieu");
		verifyTrue(filmTicketBooking.isDynamicTextViewDisplayed("Đặt vé xem phim"));
		
	}

	@Test
	public void TC_03_ChonTheoPhim_KiemTraDoiDinhDangHienThi() {
		log.info("TC_03_01_Click Dat ve xem phim");
		filmTicketBooking.clickToDynamicTextOrButtonLink("Đặt vé xem phim");

		log.info("TC_03_02_Click nut Dong y");
		filmTicketBooking.clickToDynamicButton("Đồng ý");

		log.info("TC_03_03_Click chon Tab Phim");
		filmTicketBooking.clickToTextViewByText("Phim");
		
		log.info("TC_03_04_Kiem tra dinh dang sap xep phim theo chieu ngang");
		verifyTrue(filmTicketBooking.checkTypeShowFilmList(TypeShow.HORIZONTAL));
		
		log.info("TC_03_05_Click chuyen dinh dang");
		filmTicketBooking.clickToDynamicImageViewByID("com.VCB:id/ivTypeShow");
		
		log.info("TC_03_04_Kiem tra dinh dang sap xep phim theo chieu doc");
		verifyTrue(filmTicketBooking.checkTypeShowFilmList(TypeShow.VERTICAL));
		
	}
	
	@Test
	public void TC_04_ChonTheoPhim_KiemTraIconSearch_KiemTraManHinhTimKiemPhim() {
		log.info("TC_04_01_Click icon search");
		filmTicketBooking.clickToDynamicImageViewByID("com.VCB:id/ivSearch");
		
		log.info("TC_04_02_Kiem tra man hinh Tim kiem phim");
		log.info("TC_04_02_01_Kiem tra edit box Tim kiem phim");
		verifyEquals(filmTicketBooking.getTextInEditTextFieldByID("com.VCB:id/etSearch"), "Tìm kiếm phim");
		
		log.info("TC_04_02_02_Kiem tra nut Back va nut Search");
		verifyTrue(filmTicketBooking.isDynamicImageViewDisplayed("com.VCB:id/ivBack"));
		verifyTrue(filmTicketBooking.isDynamicImageViewDisplayed("com.VCB:id/ivSearch"));
		
		log.info("TC_04_02_03_Kiem tra label PHIM HOT DANG CHIEU");
		verifyTrue(filmTicketBooking.isDynamicTextViewDisplayed("PHIM HOT ĐANG CHIẾU"));
		
		log.info("TC_04_02_04_Kiem tra hien thi danh sach phim hot dang chieu");
		verifyTrue(filmTicketBooking.isDynamicTextViewDisplayedByID("com.VCB:id/tvFilmName"));
		verifyTrue(filmTicketBooking.isDynamicTextViewDisplayedByID("com.VCB:id/tvCategories"));
		
	}
	
	List<String> listSearchFilm = new ArrayList<String>();
	@Test
	public void TC_05_ChonTheoPhim_KiemTraIconSearch_KiemTraTimKiemPhim() {
		log.info("TC_05_01_Lay ten phim");
		listSearchFilm = filmTicketBooking.getListOfSuggestedMoneyOrListText("com.VCB:id/tvFilmName");
		String name = listSearchFilm.get(0).split(" ")[0];
		
		log.info("TC_05_02_Nhap ten phim vao o tim kiem");
		filmTicketBooking.inputIntoEditTextByID(name, "com.VCB:id/etSearch");
		
		log.info("TC_05_03_Kiem tra label Ket qua");
		verifyTrue(filmTicketBooking.isDynamicTextViewDisplayed("KẾT QUẢ"));
		
		log.info("TC_05_04_Kiem tra danh sach phim tim kiem");
		listSearchFilm = filmTicketBooking.getListOfSuggestedMoneyOrListText("com.VCB:id/tvFilmName");
		verifyTrue(filmTicketBooking.checkSeachFilm(listSearchFilm, name));
		
	}
	
	@Test
	public void TC_06_ChonTheoPhim_KiemTraIconSearch_KiemTraChiTietPhimTimKiem() {
		log.info("TC_06_01_Click chon phim");
		filmTicketBooking.clickToTextViewByText(listSearchFilm.get(0));
		
		log.info("TC_06_02_Kiem tra man hinh thong tin phim");
		log.info("TC_06_02_01_Kiem tra text Thong tin, Lich chieu");
		verifyTrue(filmTicketBooking.isDynamicTextViewDisplayed("Thông tin"));
		verifyTrue(filmTicketBooking.isDynamicTextViewDisplayed("Lịch chiếu"));
		
		log.info("TC_06_02_02_Kiem tra thong tin phim");
		verifyEquals(filmTicketBooking.getTextViewByID("com.VCB:id/tvFilmName"), listSearchFilm.get(0));
		
		log.info("TC_06_02_03_Kiem tra hien thi ngay chieu");
		verifyTrue(filmTicketBooking.isDynamicTextViewDisplayedByID("com.VCB:id/tvDate"));
		
		log.info("TC_06_02_04_Kiem tra hien thi phien ban");
		verifyTrue(filmTicketBooking.isDynamicTextViewDisplayedByID("com.VCB:id/tvVersion"));
		
		log.info("TC_06_02_05_Kiem tra hien thi noi dung phim");
		filmTicketBooking.swipeElementToElementByText("Nội dung", listSearchFilm.get(0));
		verifyTrue(filmTicketBooking.isDynamicTextViewDisplayed("Nội dung"));
		
		log.info("TC_06_02_06_Kiem tra hien thi dien vien");
		verifyTrue(filmTicketBooking.isDynamicTextViewDisplayed("Diễn viên"));
		
		log.info("TC_06_02_07_Kiem tra hien thi dao dien");
		verifyTrue(filmTicketBooking.isDynamicTextViewDisplayed("Đạo diễn"));
		
		log.info("TC_06_02_08_Kiem tra hien thi nut Dat ve");
		verifyTrue(filmTicketBooking.isDynamicTextViewDisplayed("Đặt vé"));
		
	}
	
	@Test
	public void TC_07_ChonTheoPhim_KiemTraNutBack() {
		log.info("TC_07_01_Nhan nut Back");
		filmTicketBooking.clickToDynamicImageViewByID("com.VCB:id/ivBack");
		
		log.info("TC_07_02_Kiem tra tro ve man hinh tim kiem phim, giu nguyen Ket qua tim kiem");
		verifyTrue(filmTicketBooking.isDynamicTextViewDisplayed(listSearchFilm.get(0)));
		
	}
	
	List<String> actualListCinema = new ArrayList<String>();
	@Test
	public void TC_08_ChonTheoPhim_ChonSuatChieu_ManHinhChonSuatChieu() {
		log.info("TC_08_01_Nhan chon phim tim kiem");
		filmTicketBooking.clickToDynamicTextView(listSearchFilm.get(0));
		
		log.info("TC_08_02_Nhan nut Dat ve");
		filmTicketBooking.clickToDynamicTextView("Đặt vé");
		
		log.info("TC_08_03_Kiem tra text Thong tin, Lich chieu");
		verifyTrue(filmTicketBooking.isDynamicTextViewDisplayed("Thông tin"));
		verifyTrue(filmTicketBooking.isDynamicTextViewDisplayed("Lịch chiếu"));
		
		log.info("TC_08_04_Kiem tra thong tin ngay chieu");
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
		
		log.info("TC_08_05_Kiem tra highlight ngay chieu Hom nay");
		verifyTrue(filmTicketBooking.isDynamicTextViewSelected("Hôm nay"));
		
		log.info("TC_08_06_Kiem tra danh sach cum rap");
		log.info("TC_08_06_01_Click chon Tinh thanh");
		filmTicketBooking.clickToDynamicTextViewByID("com.VCB:id/tvLocationName");
		
		log.info("TC_08_06_02_Tim kiem thanh pho");
		filmTicketBooking.inputIntoEditTextByID("Hồ Chí Minh", "com.VCB:id/edtSearch");;

		log.info("TC_08_06_03_Click chon thanh pho");
		filmTicketBooking.clickToDynamicTextView("Hồ Chí Minh");
		
		log.info("TC_08_06_04_Kiem tra danh sach rap theo thanh pho");
		actualListCinema = filmTicketBooking.getListOfSuggestedMoneyOrListText("com.VCB:id/tvNameGroup");
		verifyTrue(filmTicketBooking.checkListContain(actualListCinema, FilmTicketBooking_Data.LIST_CINEMA_HCM));
		
	}
	
	@Test
	public void TC_09_ChonTheoPhim_ChonSuatChieu_KiemTraDanhSachIconVaTenRap() {
		log.info("TC_09_01_Click chon Tinh thanh");
		verifyTrue(filmTicketBooking.checkIconAndCinemaNameAndInfo(actualListCinema));
		
	}
	

	@Test
	public void TC_10_ChonTheoPhim_ChonSuatChieu_KiemTraChonNgayChieu() {
		log.info("TC_10_01_Click chon ngay chieu khac");
		String todayPlus1 = getForwardDate(1);
		String filmDate = todayPlus1.split("/")[0] + "/" + todayPlus1.split("/")[1];
		filmTicketBooking.clickToDynamicTextView(filmDate);
		
		log.info("TC_10_02_Kiem tra highlight ngay chieu da chon");
		verifyTrue(filmTicketBooking.isDynamicTextViewSelected(filmDate));
		
	}
	
	@Test
	public void TC_11_ChonTheoPhim_ChonSuatChieu_KiemTraThoiGianChonToiDa() {
		String todayPlus1 = getForwardDate(10);
		String filmDate = todayPlus1.split("/")[0] + "/" + todayPlus1.split("/")[1];
		
		log.info("TC_11_01_Swipe den ngay chieu cuoi cung");
		filmTicketBooking.swipeToTheEndDate(3);
		
		log.info("TC_11_02_Click chon ngay chieu khac");
		filmTicketBooking.clickToDynamicTextView(filmDate);
		
		log.info("TC_11_03_Kiem tra highlight ngay chieu da chon");
		verifyTrue(filmTicketBooking.isDynamicTextViewSelected(filmDate));
		
	}
	
	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}