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
import model.FilmTicketInfo;
import model.SeatType;
import pageObjects.LogInPageObject;
import pageObjects.sdk.filmTicketBooking.FilmTicketBookingPageObject;
import vietcombank_test_data.Account_Data;

public class Folow_FilmTicketBooking_Part_1 extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private FilmTicketBookingPageObject filmTicketBooking;

	FilmTicketInfo info = new FilmTicketInfo();
	
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

		log.info("TC_01_04_Tim kiem thanh pho");
		filmTicketBooking.inputIntoEditTextByID("Hồ Chí Minh", "com.VCB:id/edtSearch");

		log.info("TC_01_05_Click chon thanh pho");
		filmTicketBooking.clickToDynamicTextView("Hồ Chí Minh");
		
		log.info("TC_01_06_Click chon cum rap Mega GS");
		filmTicketBooking.clickToDynamicTextView("BHD Star Cineplex");
		
		log.info("TC_01_07_Click chon rap phim");
		List<String> listCinema = filmTicketBooking.getListOfSuggestedMoneyOrListText("com.VCB:id/tvNameCinema");
		String cinemaName = listCinema.get(0);
		filmTicketBooking.clickToDynamicTextView(cinemaName);

		log.info("TC_01_08_Click xem chi tiet phim");
		FilmInfo filmInfo = filmTicketBooking.getInfoOfTheFirstFilm();
		filmTicketBooking.clickToDynamicTextView(filmInfo.filmName);
		info.filmName = filmInfo.filmName;
		info.filmDuration = filmTicketBooking.canculateDurationOfFilm(filmInfo.filmDuration);

		log.info("TC_01_09_Nhan nut Dat ve");
		filmTicketBooking.clickToTextViewByText("Đặt vé");

		log.info("TC_01_10_Nhan chon gio chieu");
		info.time = filmTicketBooking.getDynamicTextViewByViewGroupID("com.VCB:id/tagShowtimes2D", "0");
		filmTicketBooking.clickToDynamicTextViewByViewGroupID("com.VCB:id/tagShowtimes2D", "0");
		
		log.info("TC_01_11_Chon moi loai 1 ghe");
		filmTicketBooking.clickToChooseEachTypeASeate();
		
		List<SeatType> listSeatType = filmTicketBooking.getListSeatType();
		
		log.info("TC_01_12_Click chon cho ngoi");
		filmTicketBooking.clickToTextViewByText("Chọn chỗ ngồi");

		log.info("TC_01_13_Chon cho ngoi nhu da dang ky");
		filmTicketBooking.chooseMaxSeats(listSeatType);
		
		info.cinemaName = filmTicketBooking.getTextViewByID("com.VCB:id/tvTitle");
		info.price = filmTicketBooking.getTextViewByID("com.VCB:id/tvPrince");
		
		log.info("TC_01_14_Click Thanh toan");
		filmTicketBooking.clickToTextViewByText("Thanh toán");
		
		log.info("TC_01_15_Kiem tra Thong tin Thanh toan ve xem phim");
		log.info("TC_01_15_01: Kiem tra ten phim");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(driver, "Phim"), info.filmName);
		
		log.info("TC_01_15_02: Kiem tra suat chieu");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(driver, "Suất chiếu"), info.time);
		
		log.info("TC_01_15_03: Kiem tra thời lượng");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(driver, "Thời lượng"), info.filmDuration);
		
		log.info("TC_01_15_04: Kiem tra ten rap");
		info.cinemaAddress = filmTicketBooking.getTextViewByID("com.VCB:id/tvCinemaAddress");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(driver, "Rạp"), info.cinemaName);
		
		log.info("TC_01_15_05: Kiem tra so tien");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(driver, "Số tiền"), info.price);
		
		log.info("TC_01_16_Nhap thong tin nguoi nhan ve");
		log.info("TC_01_16_01_Nhap ten khach hang");
		filmTicketBooking.inputToDynamicInputBoxByID("Duc Do", "com.VCB:id/etCustomerName");
		
		log.info("TC_01_16_02_Nhap sdt");
		filmTicketBooking.inputToDynamicInputBoxByID("0363056625", "com.VCB:id/etPhoneNumber");
		
		log.info("TC_01_16_03_Nhap email");
		filmTicketBooking.inputToDynamicInputBoxByID("minhducdo2603@gmail.com", "com.VCB:id/etEmail");
		
		log.info("TC_01_17_Click Thanh toan");
		filmTicketBooking.clickToTextViewByText("Thanh toán");

		log.info("TC_01_18_Chon tai khoan nguon");
		filmTicketBooking.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		filmTicketBooking.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);
		
		log.info("TC_01_19_Kiem tra man hinh Thong tin mua ve");
		log.info("TC_01_19_01: Kiem tra ten phim");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(driver, "Phim"), info.filmName);
		
		log.info("TC_01_19_02: Kiem tra suat chieu");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(driver, "Suất chiếu"), info.time);
		
		log.info("TC_01_19_03: Kiem tra thời lượng");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(driver, "Thời lượng"), info.filmDuration);
		
		log.info("TC_01_19_04: Kiem tra ten rap");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(driver, "Rạp"), info.cinemaName);
		
		log.info("TC_01_19_05: Kiem tra dia chi rap");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(driver, "Rạp"), info.cinemaAddress);
		
		log.info("TC_01_19_06: Kiem tra so tien");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(driver, "Số tiền"), info.price);
		
		log.info("TC_01_19_07: Kiem tra ten khach hang");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(driver, "Tên khách hàng"), "Duc Do");
		
		log.info("TC_01_19_08: Kiem tra sdt");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(driver, "Số điện thoại"), "0363056625");
		
		log.info("TC_01_19_09: Kiem tra email");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(driver, "Email"), "minhducdo2603@gmail.com");
		
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
