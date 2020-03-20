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
import model.SeatType;
import model.SeatType.TypeButton;
import pageObjects.LogInPageObject;
import pageObjects.sdk.filmTicketBooking.FilmTicketBookingPageObject;
import vietcombankUI.sdk.filmTicketBooking.FilmTicketBookingPageUIs;
import vnpay.vietcombank.sdk.filmTicketBooking.data.FilmTicketBooking_Data;

public class Validation_FilmTicketBooking_Part_6 extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private FilmTicketBookingPageObject filmTicketBooking;
	String cinemaName = "";
	FilmInfo filmInfo = null;

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
	public void TC_01_ChonTheoPhim_ChonSuatChieu_ChonSuatChieuThanhCongVoiRap_MegaGS() {
		log.info("TC_01_01_Click chon Tinh thanh");
		filmTicketBooking.clickToDynamicTextView("Đặt vé");
		filmTicketBooking.clickToDynamicTextViewByID("com.VCB:id/tvLocationName");

		log.info("TC_01_02_Tim kiem thanh pho");
		filmTicketBooking.inputIntoEditTextByID("Hồ Chí Minh", "com.VCB:id/edtSearch");
		;

		log.info("TC_01_03_Click chon thanh pho");
		filmTicketBooking.clickToDynamicTextView("Hồ Chí Minh");

		log.info("TC_01_04_Click chon cum rap Mega GS");
		filmTicketBooking.clickToDynamicTextView("Mega GS");

		log.info("TC_01_05_Click chon rap phim");
		List<String> listCinema = filmTicketBooking.getListOfSuggestedMoneyOrListText("com.VCB:id/tvNameCinema");
		cinemaName = listCinema.get(0);
		filmTicketBooking.clickToDynamicTextView(cinemaName);

		log.info("TC_01_06_Click xem chi tiet phim");
		filmInfo = filmTicketBooking.getInfoOfTheFirstFilm();
		filmTicketBooking.clickToDynamicTextView(filmInfo.filmName);

		log.info("TC_01_07_Nhan nut Dat ve");
		filmTicketBooking.clickToDynamicTextView("Đặt vé");

		log.info("TC_01_08_Nhan chon gio chieu");
		filmTicketBooking.clickToDynamicTextViewByViewGroupID("com.VCB:id/tagShowtimes2D", "0");

		log.info("TC_01_09_Kiem tra man hinh chon so luong ve");
		verifyTrue(filmTicketBooking.isDynamicTextViewDisplayed("Chọn số lượng vé"));

	}

	@Test
	public void TC_02_ChonTheoPhim_ChonSoLuongVe_KiemTraManHinh_ThongTinChiTiet() {
		log.info("TC_02_01_Kiem tra man hinh chon so luong ve");
		verifyTrue(filmTicketBooking.isDynamicTextViewDisplayed("Chọn số lượng vé"));

		log.info("TC_02_02_Kiem tra icon back");
		verifyTrue(filmTicketBooking.isDynamicImageViewDisplayed("com.VCB:id/ivBack"));

		log.info("TC_02_03_Kiem tra thong tin phim");
		log.info("TC_02_03_01_Kiem tra anh phim");
		verifyTrue(filmTicketBooking.isDynamicImageViewDisplayed("com.VCB:id/ivFilmThumbnail"));

		log.info("TC_02_03_02_Kiem tra ten phim");
		verifyEquals(filmTicketBooking.getTextViewByID("com.VCB:id/tvFilmName"), filmInfo.filmName);

		log.info("TC_02_03_03_Kiem tra ten rap");
		verifyEquals(filmTicketBooking.getTextViewByID("com.VCB:id/tvCinemaName"), cinemaName);

		log.info("TC_02_03_04_Kiem tra thoi gian chieu");
//		verifyEquals(filmTicketBooking.getTextViewByID("com.VCB:id/tvTime"), cinemaName);
//		verifyEquals(filmTicketBooking.getTextViewByID("com.VCB:id/tvDate"), cinemaName);

		log.info("TC_02_04_01_Kiem tra danh sach loai ghe");

		log.info("TC_02_04_06_Kiem gia tien");

		log.info("TC_02_04_07_Kiem tra so luong");

		log.info("TC_02_04_08_icon tang giam so luong");

		log.info("TC_02_04_09_Tong tien");
		verifyTrue(filmTicketBooking.isDynamicTextViewDisplayed("Tổng tiền"));

		log.info("TC_02_04_10_Nut chon cho ngoi disable");
		filmTicketBooking.clickToDynamicTextView("Chọn chỗ ngồi");
		verifyTrue(filmTicketBooking.isDynamicTextViewDisplayed("Chọn số lượng vé"));

	}

	@Test
	public void TC_03_ChonTheoPhim_ChonSoLuongVe_KiemTraNutBack() {
		log.info("TC_03_01_Nhan nut Back");
		filmTicketBooking.clickToDynamicImageViewByID("com.VCB:id/ivBack");

		log.info("TC_03_02_Kiem tra tro ve man hinh lich chieu");
		verifyTrue(filmTicketBooking.isDynamicTextViewDisplayed("Lịch chiếu"));

	}

	@Test
	public void TC_04_ChonTheoPhim_ChonSoLuongVe_ChonSoVeBang0() {
		log.info("TC_04_01_Nhan chon gio chieu");
		filmTicketBooking.clickToDynamicTextViewByViewGroupID("com.VCB:id/tagShowtimes2D", "0");

		List<SeatType> seats = filmTicketBooking.getListSeatType();
		log.info("TC_04_02_Kiem tra so luong ve mac dinh bang 0");
		verifyTrue(filmTicketBooking.checkSeatTypeDefault(seats));

		log.info("TC_04_03_Kiem tra nut Chon cho ngoi disable");
		filmTicketBooking.clickToDynamicTextView("Chọn chỗ ngồi");
		verifyTrue(filmTicketBooking.isDynamicTextViewDisplayed("Chọn số lượng vé"));

		log.info("TC_04_04_Bam chon 1 ghe");
		filmTicketBooking.clickToDynamicTextViewByID("com.VCB:id/tvPlus");

		log.info("TC_04_05_Kiem tra nut Chon cho ngoi enable");
		filmTicketBooking.clickToDynamicTextView("Chọn chỗ ngồi");
		verifyTrue(filmTicketBooking.isDynamicTextViewDisplayed("Thanh toán"));

	}

	@Test
	public void TC_05_ChonTheoPhim_ChonSoLuongVe_ChoPhepThayDoiSoLuongVe() {
		log.info("TC_05_01_Nhan nut Back");
		filmTicketBooking.clickToDynamicImageViewByID("com.VCB:id/ivBack");

		log.info("TC_05_02_Thay doi so luong ve");
		filmTicketBooking.clickToChangeNumberSeat(TypeButton.INCREASE, 1);

		List<SeatType> seats = filmTicketBooking.getListSeatType();
		log.info("TC_05_03_Kiem tra tong tien");
		verifyEquals(filmTicketBooking.getTextViewByID("com.VCB:id/tvTotalAmount"), filmTicketBooking.canculateAmountFilmBooking(seats));

	}

	String roomName = "";

	@Test
	public void TC_06_ChonTheoPhim_ChonSoLuongVe_ChonSoVeToiDa() {
		log.info("TC_06_01_Nhan nut Back");
		filmTicketBooking.clickToDynamicImageViewByID("com.VCB:id/ivBack");

		log.info("TC_06_02_Nhan chon gio chieu");
		filmTicketBooking.clickToDynamicTextViewByViewGroupID("com.VCB:id/tagShowtimes2D", "0");

		log.info("TC_06_03_Thay doi so luong ve tong ve bang 10");
		filmTicketBooking.clickToChangeNumberSeatSum10Tickets();

		List<SeatType> seats = filmTicketBooking.getListSeatType();
		log.info("TC_06_04_Kiem tra tong tien");
		verifyEquals(filmTicketBooking.getTextViewByID("com.VCB:id/tvTotalAmount"), filmTicketBooking.canculateAmountFilmBooking(seats));

		log.info("TC_06_05_Kiem tra chon so luong ghe thu 11");
		filmTicketBooking.clickToDynamicTextViewByID("com.VCB:id/tvPlus");

		log.info("TC_06_06_Kiem tra message thong bao chi cho chon toi da 10 ve 1 lan");
		verifyEquals(filmTicketBooking.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), FilmTicketBooking_Data.MAX_10_SEATS_PER_1_MESSAGE);

		log.info("TC_05_07_Click Dong y");
		filmTicketBooking.clickToDynamicTextView("Đồng ý");

		log.info("TC_05_08_Nhan nut Back");
		filmTicketBooking.clickToDynamicImageViewByID("com.VCB:id/ivBack");

		log.info("TC_05_09_Nhan chon gio chieu");
		filmTicketBooking.clickToDynamicTextViewByViewGroupID("com.VCB:id/tagShowtimes2D", "0");

		log.info("TC_05_10_Lay ten phong");
		roomName = filmTicketBooking.getTextViewByID("com.VCB:id/tvRoomName");

		log.info("TC_05_11_Click chon 2 ghe");
		filmTicketBooking.clickToDynamicTextViewByID("com.VCB:id/tvPlus");
		filmTicketBooking.clickToDynamicTextViewByID("com.VCB:id/tvPlus");

	}

	@Test
	public void TC_07_ChonTheoRap_ChonChoNgoi_KiemTraManHinhChonChoNgoi_ThongTinChiTiet() {
		log.info("TC_07_01_Click chon cho ngoi");
		filmTicketBooking.clickToTextViewByText("Chọn chỗ ngồi");

		log.info("TC_07_02_Kiem tra ten rap");
		verifyEquals(filmTicketBooking.getTextViewByID("com.VCB:id/tvTitle"), cinemaName);

		log.info("TC_07_03_Kiem tra nut back");
		verifyTrue(filmTicketBooking.isDynamicImageViewDisplayed("com.VCB:id/ivBack"));

		log.info("TC_07_04_Kiem tra ten phong");
		verifyEquals(filmTicketBooking.getTextViewByID("com.VCB:id/tvRoomName"), roomName);
	}

	@Test
	public void TC_08_ChonTheoRap_ChonChoNgoi_KiemTraChonGhe_KhongChonGheNao() {
		log.info("TC_08_01_Click Thanh toan");
		filmTicketBooking.clickToTextViewByText("Thanh toán");

		log.info("TC_08_02_Kiem tra Thong bao");
		verifyEquals(filmTicketBooking.getTextInDynamicPopup("com.VCB:id/tvContent"), "Quý khách vui lòng chọn ghế");

		log.info("TC_08_03_Dong Thong bao");
		filmTicketBooking.clickToTextViewByText("Đồng ý");

	}

	@Test
	public void TC_08_ChonTheoRap_ChonChoNgoi_KiemTraChonGhe_BoTrongGheOGiua() {
		log.info("TC_09_01_Chon ghe bo trong ghe o giua");
		filmTicketBooking.chooseSeatsByLineEmptyBetweenSeat();

		log.info("TC_09_02_Click Thanh toan");
		filmTicketBooking.clickToTextViewByText("Thanh toán");

		log.info("TC_09_03_Kiem tra Thong bao");
		verifyEquals(filmTicketBooking.getTextInDynamicPopup("com.VCB:id/tvContent"), "Quý khách không thể bỏ trống ghế ở giữa. Vui lòng chọn lại.");

		log.info("TC_09_04_Dong Thong bao");
		filmTicketBooking.clickToTextViewByText("Đồng ý");

		log.info("TC_09_05_Nhan nut Back");
		filmTicketBooking.clickToDynamicImageViewByID("com.VCB:id/ivBack");
		filmTicketBooking.clickToDynamicImageViewByID("com.VCB:id/ivBack");

		log.info("TC_09_06_Nhan chon gio chieu");
		filmTicketBooking.clickToDynamicTextViewByViewGroupID("com.VCB:id/tagShowtimes2D", "0");

		log.info("TC_09_07_Click chon 1 ghe");
		filmTicketBooking.clickToDynamicTextViewByID("com.VCB:id/tvPlus");

		log.info("TC_09_01_Click chon cho ngoi");
		filmTicketBooking.clickToTextViewByText("Chọn chỗ ngồi");

	}

	@Test
	public void TC_10_ChonTheoRap_ChonChoNgoi_KiemTraChonGhe_BoTrongGheNgoaiCung() {
		log.info("TC_10_01_Chon ghe bo trong ghe o giua");
		filmTicketBooking.chooseSeatsByLineEmptyLastSeat();

		log.info("TC_10_02_Click Thanh toan");
		filmTicketBooking.clickToTextViewByText("Thanh toán");

		log.info("TC_10_03_Kiem tra Thong bao");
		verifyEquals(filmTicketBooking.getTextInDynamicPopup("com.VCB:id/tvContent"), "Quý khách không thể bỏ trống ghế ngoài cùng bên phải hoặc bên trái, vui lòng chọn lại.");

		log.info("TC_10_04_Dong Thong bao");
		filmTicketBooking.clickToTextViewByText("Đồng ý");

		log.info("TC_10_05_Nhan nut Back");
		filmTicketBooking.clickToDynamicImageViewByID("com.VCB:id/ivBack");
		filmTicketBooking.clickToDynamicImageViewByID("com.VCB:id/ivBack");

		log.info("TC_10_06_Nhan chon gio chieu");
		filmTicketBooking.clickToDynamicTextViewByViewGroupID("com.VCB:id/tagShowtimes2D", "0");

		log.info("TC_10_07_Click chon toi da so ghe");
		filmTicketBooking.clickToChangeNumberSeatSum10Tickets_DeluxeVipStandardCouble_Only();

	}

	@Test
	public void TC_11_ChonTheoRap_ChonChoNgoi_KiemTraChonGhe_ChonToiDaSoGhe() {
		List<SeatType> listSeatType = filmTicketBooking.getListSeatType();

		log.info("TC_11_01_Click Chon cho ngoi");
		filmTicketBooking.clickToTextViewByText("Chọn chỗ ngồi");

		log.info("TC_11_02_Lay tong tien luc dau");
		String beforePrice = filmTicketBooking.getTextViewByID("com.VCB:id/tvPrince");

		log.info("TC_11_03_Click chon cho ngoi loai ghe Thuong");
		filmTicketBooking.chooseMaxSeats(listSeatType);

		log.info("TC_11_04_Lay tong tien luc sau");
		String afterPrice = filmTicketBooking.getTextViewByID("com.VCB:id/tvPrince");

		log.info("TC_11_05_Kiem tra tong tien");
		verifyTrue(!beforePrice.equals(afterPrice));

	}

	@Test
	public void TC_12_ChonTheoRap_ChonChoNgoi_KiemTraChonGhe_DaDat() {
		log.info("TC_12_01_Nhan nut Back");
		filmTicketBooking.clickToDynamicImageViewByID("com.VCB:id/ivBack");

		log.info("TC_12_02_Click chon cho ngoi");
		filmTicketBooking.clickToTextViewByText("Chọn chỗ ngồi");

		log.info("TC_12_03_Lay mau cua loai ghe Da Dat");
		String colorOfSeat = filmTicketBooking.getColorOfElement(FilmTicketBookingPageUIs.VIEW_BY_TEXT, "Đã đặt");

		log.info("TC_12_04_Lay tong tien luc dau");
		String beforePrice = filmTicketBooking.getTextViewByID("com.VCB:id/tvPrince");

		log.info("TC_12_05_Click chon cho ngoi loai ghe Da dat");
		verifyTrue(filmTicketBooking.chooseSeatsAndCheckColorAfterChoose(2, colorOfSeat, colorOfSeat));

		log.info("TC_12_06_Lay tong tien luc sau");
		String afterPrice = filmTicketBooking.getTextViewByID("com.VCB:id/tvPrince");

		log.info("TC_12_07_Kiem tra tong tien");
		verifyTrue(beforePrice.equals(afterPrice));
	}

	@Test
	public void TC_13_ChonTheoRap_ChonChoNgoi_KiemTraChonGhe_Thuong() {
		log.info("TC_13_01_Lay mau cua loai ghe Thuong");
		String colorOfSeat = filmTicketBooking.getColorOfElement(FilmTicketBookingPageUIs.VIEW_BY_TEXT, "Standard");
		String checkColor = filmTicketBooking.getColorOfElement(FilmTicketBookingPageUIs.VIEW_BY_TEXT, "Đang chọn");

		log.info("TC_13_02_Lay tong tien luc dau");
		String beforePrice = filmTicketBooking.getTextViewByID("com.VCB:id/tvPrince");

		log.info("TC_13_03_Click chon cho ngoi loai ghe Thuong");
		verifyTrue(filmTicketBooking.chooseSeatsAndCheckColorAfterChoose(2, colorOfSeat, checkColor));

		log.info("TC_13_04_Lay tong tien luc sau");
		String afterPrice = filmTicketBooking.getTextViewByID("com.VCB:id/tvPrince");

		log.info("TC_13_05_Kiem tra tong tien");
		verifyTrue(!beforePrice.equals(afterPrice));

	}

	@Test
	public void TC_14_ChonTheoRap_ChonChoNgoi_KiemTraChonGhe_DaChon() {
		log.info("TC_14_01_Lay mau cua loai ghe Da chon");
		String colorOfSeat = filmTicketBooking.getColorOfElement(FilmTicketBookingPageUIs.VIEW_BY_TEXT, "Đang chọn");
		String checkColor = filmTicketBooking.getColorOfElement(FilmTicketBookingPageUIs.VIEW_BY_TEXT, "Standard");

		log.info("TC_14_02_Lay tong tien luc dau");
		String beforePrice = filmTicketBooking.getTextViewByID("com.VCB:id/tvPrince");

		log.info("TC_14_03_Click chon cho ngoi loai ghe Da chon");
		verifyTrue(filmTicketBooking.chooseSeatsAndCheckColorAfterChoose(2, colorOfSeat, checkColor));

		log.info("TC_14_04_Lay tong tien luc sau");
		String afterPrice = filmTicketBooking.getTextViewByID("com.VCB:id/tvPrince");

		log.info("TC_14_05_Kiem tra tong tien");
		verifyTrue(!beforePrice.equals(afterPrice));

	}

	@Test
	public void TC_15_ChonTheoRap_ChonChoNgoi_KiemTraChonGhe_Vip() {
		log.info("TC_15_01_Lay mau cua loai ghe Vip");
		String colorOfSeat = filmTicketBooking.getColorOfElement(FilmTicketBookingPageUIs.VIEW_BY_TEXT, "Vip");
		String checkColor = filmTicketBooking.getColorOfElement(FilmTicketBookingPageUIs.VIEW_BY_TEXT, "Đang chọn");

		log.info("TC_15_02_Click chon cho ngoi loai ghe Vip");
		verifyTrue(filmTicketBooking.chooseSeatsAndCheckColorAfterChoose(2, colorOfSeat, checkColor));

	}

	@Test
	public void TC_16_ChonTheoRap_ChonChoNgoi_KiemTraChonGhe_Deluxe() {
		log.info("TC_16_01_Lay mau cua loai ghe Deluxe");
		String colorOfSeat = filmTicketBooking.getColorOfElement(FilmTicketBookingPageUIs.VIEW_BY_TEXT, "Deluxe");
		String checkColor = filmTicketBooking.getColorOfElement(FilmTicketBookingPageUIs.VIEW_BY_TEXT, "Đang chọn");

		log.info("TC_16_02_Click chon cho ngoi loai ghe Deluxe");
		verifyTrue(filmTicketBooking.chooseSeatsAndCheckColorAfterChoose(2, colorOfSeat, checkColor));

	}

	@Test
	public void TC_17_ChonTheoRap_ChonChoNgoi_KiemTraChonGhe_Couple() {
		log.info("TC_17_01_Lay mau cua loai ghe Couple");
		String colorOfSeat = filmTicketBooking.getColorOfElement(FilmTicketBookingPageUIs.VIEW_BY_TEXT, "Couple");
		String checkColor = filmTicketBooking.getColorOfElement(FilmTicketBookingPageUIs.VIEW_BY_TEXT, "Đang chọn");

		log.info("TC_17_02_Click chon cho ngoi loai ghe Couple");
		verifyTrue(filmTicketBooking.chooseSeatsAndCheckColorAfterChoose(2, colorOfSeat, checkColor));

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
