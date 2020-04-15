package vnpay.vietcombank.sdk.filmTicketBooking;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.Soundbank;

import org.openqa.selenium.By;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import model.FilmTicketInfo;
import model.SeatType;
import pageObjects.LogInPageObject;
import pageObjects.sdk.filmTicketBooking.FilmTicketBookingPageObject;
import vietcombankUI.sdk.filmTicketBooking.FilmTicketBookingPageUIs;
import vnpay.vietcombank.sdk.filmTicketBooking.data.FilmTicketBooking_Data;

public class Limit_FilmTicketBooking extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private FilmTicketBookingPageObject filmTicketBooking;

	String password = "";

	FilmTicketInfo filmInfo = new FilmTicketInfo();

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
		password = pass;

		filmTicketBooking = PageFactoryManager.getFilmTicketBookingPageObject(driver);
		log.info("--------------------------Click Dat ve xem phim-------------------------------");
		filmTicketBooking.clickToDynamicTextOrButtonLink("Đặt vé xem phim");

		log.info("--------------------------Click nut Dong y--------------------------");
		filmTicketBooking.clickToDynamicButton("Đồng ý");

		log.info("--------------------------Click chon Tinh thanh--------------------------");
		filmTicketBooking.clickToDynamicTextViewByID("com.VCB:id/tvLocationName");

		log.info("--------------------------Tim kiem thanh pho--------------------------");
		filmTicketBooking.inputIntoEditTextByID("Hồ Chí Minh", "com.VCB:id/edtSearch");

		log.info("--------------------------Click chon thanh pho--------------------------");
		filmTicketBooking.clickToDynamicTextView("Hồ Chí Minh");

	}

	@Test
	public void TC_01_ThanhToanVeXeNhoHonHanMucToiThieu() {

		log.info("--------------------------Click chon cum rap Mega GS--------------------------");
		filmTicketBooking.clickToDynamicTextView("BHD Star Cineplex");

		log.info("--------------------------TC_01_Click chon rap phim--------------------------");
		List<String> listCinema = filmTicketBooking.getListOfSuggestedMoneyOrListText("com.VCB:id/tvNameCinema");
		String cinemaName = listCinema.get(0);
		filmTicketBooking.clickToDynamicTextView(cinemaName);

		log.info("--------------------------TC_01_Nhan nut Dat ve--------------------------");
		filmTicketBooking.clickToTextViewByText("Đặt vé");
		filmTicketBooking.clickFilmScheduleNotToday(1);

		log.info("--------------------------TC_01-Nhan chon gio chieu--------------------------");
		filmInfo.time = filmTicketBooking.getDynamicTextViewByViewGroupID("com.VCB:id/tagShowtimes2D", "0");
		filmTicketBooking.clickToDynamicTextViewByViewGroupID("com.VCB:id/tagShowtimes2D", "0");

		log.info("--------------------------TC_01_11_Chon 1 ghe--------------------------");
		filmTicketBooking.clickToDynamicTextViewByID("com.VCB:id/tvPlus");

		List<SeatType> listSeatType = filmTicketBooking.getListSeatType();
		String type = filmTicketBooking.getTypeOfSeat(listSeatType);

		log.info("--------------------------TC_01_12_Click chon cho ngoi--------------------------");
		filmTicketBooking.clickToTextViewByText("Chọn chỗ ngồi");

		log.info("--------------------------TC_01_13_Chon cho ngoi nhu da dang ky--------------------------");
		String colorOfSeat = filmTicketBooking.getColorOfElement(FilmTicketBookingPageUIs.VIEW_BY_TEXT, type);
		filmTicketBooking.chooseSeats(1, colorOfSeat);

		filmInfo.cinemaName = filmTicketBooking.getTextViewByID("com.VCB:id/tvTitle");
		filmInfo.price = filmTicketBooking.getTextViewByID("com.VCB:id/tvPrince");

		log.info("--------------------------TC_01_14_Click Thanh toan--------------------------");
		filmTicketBooking.clickToTextViewByText("Thanh toán");

		log.info("--------------------------TC_01_16_03_Nhap email--------------------------");
		filmTicketBooking.scrollDownToText("Thanh toán");
		filmTicketBooking.inputToDynamicInputBoxByID(FilmTicketBooking_Data.EMAIL_BOOKING, "com.VCB:id/etEmail");

		log.info("--------------------------TC_01_14_Click Thanh toan--------------------------");
		filmTicketBooking.clickToTextViewByText("Thanh toán");

		log.info("--------------------------TC_01_14_Click Thanh toan--------------------------");
		filmTicketBooking.scrollIDownOneTime(driver);
		filmTicketBooking.clickToDynamicButton("Tiếp tục");

		log.info("--------------------------TC_01_14_Verify thong bao--------------------------");
		filmTicketBooking.isDynamicMessageAndLabelTextDisplayed(FilmTicketBooking_Data.MESSEGE_ERROR_LOWER_MIN_LIMIT_A_TRAN);

		log.info("--------------------------TC_01_14_ Click btn OK--------------------------");
		filmTicketBooking.clickToDynamicButton("Đóng");

	}

	@Test
	public void TC_02_ThanhToanVeXeLonHonHanMucToiDa() {
		
		log.info("--------------------------TC_02_01_Nhan nut Quay lai--------------------------");
		filmTicketBooking.clickToDynamicBackIcon("Thông tin mua vé");
		filmTicketBooking.clickToDynamicButton("Quay lại");

		log.info("--------------------------TC_02_Nhan nut Dat ve--------------------------");
		filmTicketBooking.clickToTextViewByText("Đặt vé");
		filmTicketBooking.clickFilmScheduleNotToday(1);

		log.info("--------------------------TC_02_Nhan chon gio chieu--------------------------");
		filmInfo.time = filmTicketBooking.getDynamicTextViewByViewGroupID("com.VCB:id/tagShowtimes2D", "3");
		filmTicketBooking.clickToDynamicTextViewByViewGroupID("com.VCB:id/tagShowtimes2D", "3");

		log.info("--------------------------TC_02_Chon 1 ghe--------------------------");
		filmTicketBooking.clickToDynamicTextViewByID("com.VCB:id/tvPlus");
		filmTicketBooking.clickToDynamicTextViewByID("com.VCB:id/tvPlus");
		filmTicketBooking.clickToDynamicTextViewByID("com.VCB:id/tvPlus");

		List<SeatType> listSeatType = filmTicketBooking.getListSeatType();
		String type = filmTicketBooking.getTypeOfSeat(listSeatType);

		log.info("--------------------------TC_02_Click chon cho ngoi--------------------------");
		filmTicketBooking.clickToTextViewByText("Chọn chỗ ngồi");

		log.info("--------------------------TC_02_Chon cho ngoi nhu da dang ky--------------------------");
		String colorOfSeat = filmTicketBooking.getColorOfElement(FilmTicketBookingPageUIs.VIEW_BY_TEXT, type);
		filmTicketBooking.chooseSeats(3, colorOfSeat);

		filmInfo.cinemaName = filmTicketBooking.getTextViewByID("com.VCB:id/tvTitle");
		filmInfo.price = filmTicketBooking.getTextViewByID("com.VCB:id/tvPrince");

		log.info("--------------------------TC_02_Click Thanh toan--------------------------");
		filmTicketBooking.clickToTextViewByText("Thanh toán");

		log.info("--------------------------TC_02_Nhap email--------------------------");
		filmTicketBooking.scrollDownToText("Thanh toán");
		filmTicketBooking.inputToDynamicInputBoxByID(FilmTicketBooking_Data.EMAIL_BOOKING, "com.VCB:id/etEmail");

		log.info("--------------------------TC_02_Click Thanh toan--------------------------");
		filmTicketBooking.clickToTextViewByText("Thanh toán");

		log.info("--------------------------TC_02_Click Thanh toan--------------------------");
		filmTicketBooking.scrollIDownOneTime(driver);
		filmTicketBooking.clickToDynamicButton("Tiếp tục");

		log.info("--------------------------TC_02_Verify thong bao--------------------------");
		filmTicketBooking.isDynamicMessageAndLabelTextDisplayed(FilmTicketBooking_Data.MESSEGE_ERROR_HIGHER_MAX_LIMIT_A_TRAN);

		log.info("--------------------------TC_02_ Click btn OK--------------------------");
		filmTicketBooking.clickToDynamicButton("Đóng");
	}

	@Test
	// hạn mức ngày set 260 000VND
	public void TC_03_ThanhToanVeXeLonHonHanMucToiDaTrongNgay() {
		
		log.info("--------------------------TC_03_01_Nhan nut Quay lai--------------------------");
		filmTicketBooking.clickToDynamicBackIcon("Thông tin mua vé");
		filmTicketBooking.clickToDynamicButton("Quay lại");

		log.info("--------------------------TC_03_Nhan nut Dat ve--------------------------");
		filmTicketBooking.clickToTextViewByText("Đặt vé");
		filmTicketBooking.clickFilmScheduleNotToday(1);

		log.info("--------------------------TC_03_Nhan chon gio chieu--------------------------");
		filmInfo.time = filmTicketBooking.getDynamicTextViewByViewGroupID("com.VCB:id/tagShowtimes2D", "3");
		filmTicketBooking.clickToDynamicTextViewByViewGroupID("com.VCB:id/tagShowtimes2D", "3");

		log.info("--------------------------TC_03_Chon 1 ghe--------------------------");
		filmTicketBooking.clickToDynamicTextViewByID("com.VCB:id/tvPlus");
		filmTicketBooking.clickToDynamicTextViewByID("com.VCB:id/tvPlus");

		List<SeatType> listSeatType = filmTicketBooking.getListSeatType();
		String type = filmTicketBooking.getTypeOfSeat(listSeatType);

		log.info("--------------------------TC_03_Click chon cho ngoi--------------------------");
		filmTicketBooking.clickToTextViewByText("Chọn chỗ ngồi");

		log.info("--------------------------TC_03_Chon cho ngoi nhu da dang ky--------------------------");
		String colorOfSeat = filmTicketBooking.getColorOfElement(FilmTicketBookingPageUIs.VIEW_BY_TEXT, type);
		filmTicketBooking.chooseSeats(2, colorOfSeat);

		filmInfo.cinemaName = filmTicketBooking.getTextViewByID("com.VCB:id/tvTitle");
		filmInfo.price = filmTicketBooking.getTextViewByID("com.VCB:id/tvPrince");

		log.info("--------------------------TC_03_Click Thanh toan--------------------------");
		filmTicketBooking.clickToTextViewByText("Thanh toán");

		log.info("--------------------------TC_03_Nhap email--------------------------");
		filmTicketBooking.scrollDownToText("Thanh toán");
		filmTicketBooking.inputToDynamicInputBoxByID(FilmTicketBooking_Data.EMAIL_BOOKING, "com.VCB:id/etEmail");

		log.info("--------------------------TC_03_Click Thanh toan--------------------------");
		filmTicketBooking.clickToTextViewByText("Thanh toán");

		log.info("--------------------------TC_03_Click Thanh toan--------------------------");
		filmTicketBooking.scrollIDownOneTime(driver);
		filmTicketBooking.clickToDynamicButton("Tiếp tục");

		filmTicketBooking.clickToDynamicTextView("Mật khẩu đăng nhập");
		filmTicketBooking.clickToDynamicTextView("Mật khẩu đăng nhập");

		log.info("--------------------------TC_03: Click Tiep tuc--------------------------");
		filmTicketBooking.clickToDynamicButton("Tiếp tục");

		filmTicketBooking.inputToDynamicPopupPasswordInput(password, "Tiếp tục");

		filmTicketBooking.clickToDynamicButton("Tiếp tục");

		log.info("--------------------------TC_03: Kiem tra man hinh thanh toan thanh cong--------------------------");
		verifyTrue(filmTicketBooking.isDynamicMessageAndLabelTextDisplayed("THANH TOÁN THÀNH CÔNG"));

		log.info("--------------------------TC_03_Click Thuc hien giao dich moi--------------------------");
		filmTicketBooking.clickToDynamicButton("Thực hiện giao dịch mới");

		log.info("--------------------------TC_03_Click chon Tinh thanh--------------------------");
		filmTicketBooking.clickToDynamicTextViewByID("com.VCB:id/tvLocationName");

		log.info("--------------------------TC_03_Tim kiem thanh pho--------------------------");
		filmTicketBooking.inputIntoEditTextByID("Hồ Chí Minh", "com.VCB:id/edtSearch");

		log.info("--------------------------TC_03_Click chon thanh pho--------------------------");
		filmTicketBooking.clickToDynamicTextView("Hồ Chí Minh");

		log.info("--------------------------TC_03_Click chon cum rap Mega GS--------------------------");
		filmTicketBooking.clickToDynamicTextView("BHD Star Cineplex");

		log.info("--------------------------TC_03_Click chon rap phim--------------------------");
		List<String> listCinema = filmTicketBooking.getListOfSuggestedMoneyOrListText("com.VCB:id/tvNameCinema");
		String cinemaName = listCinema.get(0);
		filmTicketBooking.clickToDynamicTextView(cinemaName);

		log.info("--------------------------TC_03_Nhan nut Dat ve--------------------------");
		filmTicketBooking.clickToTextViewByText("Đặt vé");
		filmTicketBooking.clickFilmScheduleNotToday(1);

		log.info("--------------------------TC_03_Nhan chon gio chieu--------------------------");
		filmInfo.time = filmTicketBooking.getDynamicTextViewByViewGroupID("com.VCB:id/tagShowtimes2D", "3");
		filmTicketBooking.clickToDynamicTextViewByViewGroupID("com.VCB:id/tagShowtimes2D", "3");

		log.info("--------------------------TC_03_Chon 1 ghe--------------------------");
		filmTicketBooking.clickToDynamicTextViewByID("com.VCB:id/tvPlus");
		type = filmTicketBooking.getTypeOfSeat(listSeatType);

		log.info("--------------------------TC_03_Click chon cho ngoi--------------------------");
		filmTicketBooking.clickToTextViewByText("Chọn chỗ ngồi");

		log.info("--------------------------TC_03_Chon cho ngoi nhu da dang ky--------------------------");
		colorOfSeat = filmTicketBooking.getColorOfElement(FilmTicketBookingPageUIs.VIEW_BY_TEXT, type);
		filmTicketBooking.chooseSeats(1, colorOfSeat);

		filmInfo.cinemaName = filmTicketBooking.getTextViewByID("com.VCB:id/tvTitle");
		filmInfo.price = filmTicketBooking.getTextViewByID("com.VCB:id/tvPrince");

		log.info("--------------------------TC_03_Click Thanh toan--------------------------");
		filmTicketBooking.clickToTextViewByText("Thanh toán");

		log.info("--------------------------TC_03_Nhap email--------------------------");
		filmTicketBooking.scrollDownToText("Thanh toán");
		filmTicketBooking.inputToDynamicInputBoxByID(FilmTicketBooking_Data.EMAIL_BOOKING, "com.VCB:id/etEmail");

		log.info("--------------------------TC_03_Click Thanh toan--------------------------");
		filmTicketBooking.clickToTextViewByText("Thanh toán");

		log.info("--------------------------TC_03_Click Thanh toan--------------------------");
		filmTicketBooking.scrollIDownOneTime(driver);
		filmTicketBooking.clickToDynamicButton("Tiếp tục");

		log.info("--------------------------TC_03_Verify thong bao--------------------------");
		filmTicketBooking.isDynamicMessageAndLabelTextDisplayed(FilmTicketBooking_Data.MESSEGE_ERROR_HIGHER_MAX_LIMIT_A_DAY);

		log.info("--------------------------TC_03_ Click btn OK");
		filmTicketBooking.clickToDynamicButton("Đóng");

	}

	// Set han muc nhom la 50 000 VND
	public void TC_04_ThanhToanVeXeNhoHonHanMucDaNhom() {

		log.info("--------------------------TC_04_Click chon cum rap Mega GS--------------------------");
		filmTicketBooking.clickToDynamicTextView("BHD Star Cineplex");

		log.info("--------------------------TC_04_Click chon rap phim--------------------------");
		List<String> listCinema = filmTicketBooking.getListOfSuggestedMoneyOrListText("com.VCB:id/tvFilmName");
		String cinemaName = listCinema.get(0);
		filmTicketBooking.clickToDynamicTextView(cinemaName);

		log.info("--------------------------TC_04_Nhan nut Dat ve--------------------------");
		filmTicketBooking.clickToTextViewByText("Đặt vé");
		List<String> listSchedule = filmTicketBooking.getListFilmSchedule();
		filmTicketBooking.clickToElement(driver, listSchedule.get(1));

		log.info("--------------------------TC_04_Nhan chon gio chieu--------------------------");
		filmInfo.time = filmTicketBooking.getDynamicTextViewByViewGroupID("com.VCB:id/tagShowtimes2D", "3");
		filmTicketBooking.clickToDynamicTextViewByViewGroupID("com.VCB:id/tagShowtimes2D", "3");

		log.info("--------------------------TC_04_Chon 1 ghe--------------------------");
		filmTicketBooking.clickToDynamicTextViewByID("com.VCB:id/tvPlus");

		List<SeatType> listSeatType = filmTicketBooking.getListSeatType();
		String type = filmTicketBooking.getTypeOfSeat(listSeatType);

		log.info("--------------------------TC_04_Click chon cho ngoi--------------------------");
		filmTicketBooking.clickToTextViewByText("Chọn chỗ ngồi");

		log.info("--------------------------TC_04_Chon cho ngoi nhu da dang ky--------------------------");
		String colorOfSeat = filmTicketBooking.getColorOfElement(FilmTicketBookingPageUIs.VIEW_BY_TEXT, type);
		filmTicketBooking.chooseSeats(1, colorOfSeat);

		filmInfo.cinemaName = filmTicketBooking.getTextViewByID("com.VCB:id/tvTitle");
		filmInfo.price = filmTicketBooking.getTextViewByID("com.VCB:id/tvPrince");

		log.info("--------------------------TC_04_Click Thanh toan--------------------------");
		filmTicketBooking.clickToTextViewByText("Thanh toán");

		log.info("--------------------------TC_04_Verify thong bao--------------------------");
		filmTicketBooking.isDynamicMessageAndLabelTextDisplayed(FilmTicketBooking_Data.MESSEGE_ERROR_HIGHER_MAX_LIMIT_GROUP);

		log.info("--------------------------TC_04_ Click btn OK--------------------------");
		filmTicketBooking.clickToTextViewByText("Đóng");

	}

	// Set han muc goi la 50 000 VND
	public void TC_05_ThanhToanVeXeNhoHonHanMucToiDaGoi() {

		log.info("--------------------------TC_05_Click chon cum rap Mega GS--------------------------");
		filmTicketBooking.clickToDynamicTextView("BHD Star Cineplex");

		log.info("--------------------------TC_05_Click chon rap phim--------------------------");
		List<String> listCinema = filmTicketBooking.getListOfSuggestedMoneyOrListText("com.VCB:id/tvFilmName");
		String cinemaName = listCinema.get(0);
		filmTicketBooking.clickToDynamicTextView(cinemaName);

		log.info("--------------------------TC_05_Nhan nut Dat ve--------------------------");
		filmTicketBooking.clickToTextViewByText("Đặt vé");
		List<String> listSchedule = filmTicketBooking.getListFilmSchedule();
		filmTicketBooking.clickToElement(driver, listSchedule.get(1));

		log.info("--------------------------TC_05_Nhan chon gio chieu--------------------------");
		filmInfo.time = filmTicketBooking.getDynamicTextViewByViewGroupID("com.VCB:id/tagShowtimes2D", "3");
		filmTicketBooking.clickToDynamicTextViewByViewGroupID("com.VCB:id/tagShowtimes2D", "3");

		log.info("--------------------------TC_05_Chon 1 ghe--------------------------");
		filmTicketBooking.clickToDynamicTextViewByID("com.VCB:id/tvPlus");

		List<SeatType> listSeatType = filmTicketBooking.getListSeatType();
		String type = filmTicketBooking.getTypeOfSeat(listSeatType);

		log.info("--------------------------TC_05_Click chon cho ngoi--------------------------");
		filmTicketBooking.clickToTextViewByText("Chọn chỗ ngồi");

		log.info("--------------------------TC_05_Chon cho ngoi nhu da dang ky--------------------------");
		String colorOfSeat = filmTicketBooking.getColorOfElement(FilmTicketBookingPageUIs.VIEW_BY_TEXT, type);
		filmTicketBooking.chooseSeats(1, colorOfSeat);

		filmInfo.cinemaName = filmTicketBooking.getTextViewByID("com.VCB:id/tvTitle");
		filmInfo.price = filmTicketBooking.getTextViewByID("com.VCB:id/tvPrince");

		log.info("--------------------------TC_05_Click Thanh toan--------------------------");
		filmTicketBooking.clickToTextViewByText("Thanh toán");

		log.info("--------------------------TC_05_Verify thong bao--------------------------");
		filmTicketBooking.isDynamicMessageAndLabelTextDisplayed(FilmTicketBooking_Data.MESSEGE_ERROR_HIGHER_MAX_LIMIT_PACKAGE);

		log.info("--------------------------TC_05_ Click btn OK--------------------------");
		filmTicketBooking.clickToTextViewByText("Đóng");

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
