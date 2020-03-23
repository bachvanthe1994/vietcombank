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
import vietcombankUI.sdk.filmTicketBooking.FilmTicketBookingPageUIs;
import vietcombank_test_data.Account_Data;
import vnpay.vietcombank.sdk.filmTicketBooking.data.FilmTicketBooking_Data;

public class Flow_FilmTicketBooking_Part_1 extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private FilmTicketBookingPageObject filmTicketBooking;
	private String transferTime, transactionNumber, ticketCode;
	private long surplus, availableBalance, actualAvailableBalance, fee;
	String password = "";
	
	FilmTicketInfo info = new FilmTicketInfo();
	
	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
		password = pass;
	}

	@Test
	public void TC_01_DatVeXemPhim_Rap_MegaGS_BHDCineplex() {
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
		verifyEquals(filmTicketBooking.getDynamicTextInFilmTicketInfoDetail("Phim"), info.filmName);
		
		log.info("TC_01_15_02: Kiem tra suat chieu");
		verifyTrue(filmTicketBooking.getDynamicTextInFilmTicketInfoDetail("Suất chiếu").contains(info.time));
		
		log.info("TC_01_15_03: Kiem tra thời lượng");
		verifyEquals(filmTicketBooking.getDynamicTextInFilmTicketInfoDetail("Thời lượng"), info.filmDuration);
		
		log.info("TC_01_15_04: Kiem tra ten rap");
		info.cinemaAddress = filmTicketBooking.getTextViewByID("com.VCB:id/tvCinemaAddress");
		verifyEquals(filmTicketBooking.getDynamicTextInFilmTicketInfoDetail("Rạp"), info.cinemaName);
		
		log.info("TC_01_15_05: Kiem tra so tien");
		verifyEquals(filmTicketBooking.getDynamicTextInFilmTicketInfoDetail("Số tiền"), info.price);
		
		log.info("TC_01_16_Nhap thong tin nguoi nhan ve");
		log.info("TC_01_16_01_Nhap ten khach hang");
		filmTicketBooking.inputToDynamicInputBoxByID("Duc Do", "com.VCB:id/etCustomerName");
		
		log.info("TC_01_16_02_Nhap sdt");
		filmTicketBooking.inputToDynamicInputBoxByID(FilmTicketBooking_Data.PHONE_BOOKING, "com.VCB:id/etPhoneNumber");
		
		log.info("TC_01_16_03_Nhap email");
		filmTicketBooking.scrollDownToText("Thanh toán");
		filmTicketBooking.inputToDynamicInputBoxByID(FilmTicketBooking_Data.EMAIL_BOOKING, "com.VCB:id/etEmail");
		
		log.info("TC_01_17_Click Thanh toan");
		filmTicketBooking.clickToTextViewByText("Thanh toán");

		log.info("TC_01_18_Chon tai khoan nguon");
		filmTicketBooking.clickToDynamicDropDown("Tài khoản nguồn");
		filmTicketBooking.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);
		surplus = convertAvailableBalanceCurrentcyOrFeeToLong(filmTicketBooking.getDynamicTextInTransactionDetail("Số dư khả dụng"));
		
		log.info("TC_01_19_Kiem tra man hinh Thong tin mua ve");
		log.info("TC_01_19_01: Kiem tra ten phim");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail("Phim"), info.filmName);
		
		log.info("TC_01_19_02: Kiem tra suat chieu");
		verifyTrue(filmTicketBooking.getDynamicTextInTransactionDetail("Suất chiếu").contains(info.time));
		
		log.info("TC_01_19_03: Kiem tra thời lượng");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail("Thời lượng"), info.filmDuration);
		
		log.info("TC_01_19_04: Kiem tra ten rap");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail("Rạp"), info.cinemaName);
		
		log.info("TC_01_19_05: Kiem tra dia chi rap");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail("Địa chỉ"), info.cinemaAddress);
		
		log.info("TC_01_19_06: Kiem tra so tien");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail("Số tiền"), info.price);
		
		log.info("TC_01_19_07: Kiem tra ten khach hang");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail("Tên khách hàng"), "Duc Do");
		
		log.info("TC_01_19_08: Kiem tra sdt");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail("Số điện thoại nhận vé"), FilmTicketBooking_Data.PHONE_BOOKING);
		
		log.info("TC_01_19_09: Kiem tra email");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail("Email nhận vé"), FilmTicketBooking_Data.EMAIL_BOOKING);
		
		log.info("TC_01_20: Click Tiep tuc");
		filmTicketBooking.clickToDynamicButton("Tiếp tục");
		
		log.info("TC_01_21: Kiem tra man hinh xac nhan thong tin");
		log.info("TC_01_21_01: Kiem tra tai khoan nguon");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail("Tài khoản nguồn"), Account_Data.Valid_Account.ACCOUNT2);
		
		log.info("TC_01_21_02: Kiem tra so tien");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail("Số tiền"), info.price);
		
		log.info("TC_01_22_Chon phuong thuc xac thuc");
		filmTicketBooking.clickToDynamicTextView("Mật khẩu đăng nhập");
		fee = convertAvailableBalanceCurrentcyOrFeeToLong(filmTicketBooking.getDynamicTextInTransactionDetail("Mật khẩu đăng nhập"));
		filmTicketBooking.clickToDynamicTextView("Mật khẩu đăng nhập");
		
		log.info("TC_01_22_01: Kiem tra so tien phi");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail("Số tiền phí"), addCommasToLong(fee + "") + " VND");
		
		log.info("TC_01_23: Click Tiep tuc");
		filmTicketBooking.clickToDynamicButton("Tiếp tục");
		
		filmTicketBooking.inputToDynamicPopupPasswordInput(password, "Tiếp tục");

		filmTicketBooking.clickToDynamicButton("Tiếp tục");
		
		log.info("TC_01_24: Kiem tra man hinh thanh toan thanh cong");
		verifyTrue(filmTicketBooking.isDynamicMessageAndLabelTextDisplayed("THANH TOÁN THÀNH CÔNG"));
		transferTime = filmTicketBooking.getTransferTimeSuccess("THANH TOÁN THÀNH CÔNG");
		transactionNumber = filmTicketBooking.getDynamicTextInTransactionDetail("Mã giao dịch");
		ticketCode = filmTicketBooking.getDynamicTextInTransactionDetail("Mã vé");
		
		log.info("TC_01_25_Kiem tra nut Thuc hien giao dich moi");
		verifyTrue(filmTicketBooking.isDynamicButtonDisplayed("Thực hiện giao dịch mới"));
		
		log.info("TC_01_26_Click Thuc hien giao dich moi");
		filmTicketBooking.clickToDynamicButton("Thực hiện giao dịch mới");
		
		log.info("TC_01_27_Tinh so du kha dung cua tai khoan sau khi thanh toan thanh cong");
		availableBalance = canculateAvailableBalances(surplus, convertAvailableBalanceCurrentcyOrFeeToLong(info.price), fee);
		
	}

	@Test
	public void TC_02_DatVeXemPhim_Rap_MegaGS_CineStar_BaoCaoGiaoDich() {
		log.info("TC_02_1: Click  nut Back");
		filmTicketBooking.clickToDynamicBackIcon("Mua vé xem phim");

		log.info("TC_02_2: Click vao More Icon");
		filmTicketBooking.clickToDynamicImageViewByID("com.VCB:id/menu_5");

		log.info("TC_02_3: Click Bao Cao giao Dich");
		filmTicketBooking.clickToDynamicTextView("Báo cáo giao dịch");

		log.info("TC_02_4: Click Tat Ca Cac Loai Giao Dich");
		filmTicketBooking.clickToDynamicTextView("Tất cả các loại giao dịch");

		log.info("TC_02_5: Chon Chuyen Tien Trong VCB");
		filmTicketBooking.clickToDynamicTextView("Thanh toán vé xem phim");

		log.info("TC_02_6: Click Chon Tai Khoan");
		filmTicketBooking.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvSelectAcc");
		
		log.info("TC_02_7: Lay so du kha dung luc sau");
		actualAvailableBalance = convertAvailableBalanceCurrentcyOrFeeToLong(filmTicketBooking.getDynamicTextInTransactionDetail(Account_Data.Valid_Account.ACCOUNT2));
		
		log.info("TC_02_8: Kiem tra so du kha dung sau khi thanh toan thanh cong");
		verifyEquals(actualAvailableBalance, availableBalance);
		
		log.info("TC_02_9: Chon tai Khoan chuyen");
		filmTicketBooking.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_02_10: Click Tim Kiem");
		filmTicketBooking.clickToDynamicButton("Tìm kiếm");

		log.info("TC_02_11: Kiem tra ngay tao giao dich hien thi");
		String reportTime1 = filmTicketBooking.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
		verifyEquals(convertDateTimeIgnoreHHmmss(reportTime1), convertTransferTimeToReportDateTime(transferTime));
		
		log.info("TC_02_12: Kiem tra so tien chuyen hien thi");
		verifyEquals(filmTicketBooking.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + info.price));
		
		log.info("TC_02_13: Click vao giao dich");
		filmTicketBooking.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
		
		log.info("TC_02_14: Kiem tra thoi gian tao giao dich hien thi");
		String reportTime2 = filmTicketBooking.getDynamicTextInTransactionDetail("Thời gian giao dịch");
		verifyEquals(reportTime2, reportTime1);

		log.info("TC_02_15: Kiem tra ma giao dich");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail("Số lệnh giao dịch"), transactionNumber);
		
		log.info("TC_02_16: Kiem tra so tai khoan trich no");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail("Tài khoản/thẻ trích nợ"), Account_Data.Valid_Account.ACCOUNT2);
		
		log.info("TC_02_17: Kiem tra ma ve");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail("Mã vé"), ticketCode);
		
		log.info("TC_02_18: Kiem tra so tien giao dich hien thi");
		verifyTrue(filmTicketBooking.getDynamicTextInTransactionDetail("Số tiền giao dịch").contains(info.price));
		
		log.info("TC_02_19: Kiem tra so tien phi");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail("Số tiền phí"), addCommasToLong(fee + "") + " VND");
		
		log.info("TC_02_20: Kiem tra loai giao dich");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail("Loại giao dịch"), "Thanh toán vé xem phim");
		
		log.info("TC_02_21: Kiem tra noi dung giao dich");
		String note = "MBVCB" + transactionNumber + ". thanh toan ve xem phim";
		verifyTrue(filmTicketBooking.getDynamicTextInTransactionDetail("Nội dung giao dịch").contains(note));
		
		log.info("TC_02_22: Click  nut Back");
		filmTicketBooking.clickToDynamicBackIcon("Chi tiết giao dịch");

		log.info("TC_02_23: Click  nut Back");
		filmTicketBooking.clickToDynamicBackIcon("Báo cáo giao dịch");

		log.info("TC_02_24: Click  nut Home");
		filmTicketBooking.clickToDynamicImageViewByID("com.VCB:id/menu_1");
		
	}
	
//	@Test
	public void TC_03_DatVeXemPhim_Rap_CineStar() {
		filmTicketBooking = PageFactoryManager.getFilmTicketBookingPageObject(driver);

		log.info("TC_03_01_Click Dat ve xem phim");
		filmTicketBooking.clickToDynamicTextOrButtonLink("Đặt vé xem phim");

		log.info("TC_03_02_Click nut Dong y");
		filmTicketBooking.clickToDynamicButton("Đồng ý");
		
		log.info("TC_03_03_Click chon Tinh thanh");
		filmTicketBooking.clickToDynamicTextViewByID("com.VCB:id/tvLocationName");

		log.info("TC_03_04_Tim kiem thanh pho");
		filmTicketBooking.inputIntoEditTextByID("Hồ Chí Minh", "com.VCB:id/edtSearch");

		log.info("TC_03_05_Click chon thanh pho");
		filmTicketBooking.clickToDynamicTextView("Hồ Chí Minh");
		
		log.info("TC_03_06_Click chon cum rap Cinestar");
		filmTicketBooking.clickToDynamicTextView("Cinestar");
		
		log.info("TC_03_07_Click chon rap phim");
		List<String> listCinema = filmTicketBooking.getListOfSuggestedMoneyOrListText("com.VCB:id/tvNameCinema");
		String cinemaName = listCinema.get(0);
		filmTicketBooking.clickToDynamicTextView(cinemaName);

		log.info("TC_03_08_Click xem chi tiet phim");
		FilmInfo filmInfo = filmTicketBooking.getInfoOfTheFirstFilm();
		filmTicketBooking.clickToDynamicTextView(filmInfo.filmName);
		info.filmName = filmInfo.filmName;
		info.filmDuration = filmTicketBooking.canculateDurationOfFilm(filmInfo.filmDuration);

		log.info("TC_03_09_Nhan nut Dat ve");
		filmTicketBooking.clickToTextViewByText("Đặt vé");

		log.info("TC_03_10_Nhan chon gio chieu");
		info.time = filmTicketBooking.getDynamicTextViewByViewGroupID("com.VCB:id/tagShowtimes2D", "0");
		filmTicketBooking.clickToDynamicTextViewByViewGroupID("com.VCB:id/tagShowtimes2D", "0");

		log.info("TC_03_11_Lay mau cua loai ghe Thuong");
		String colorOfSeat = filmTicketBooking.getColorOfElement(FilmTicketBookingPageUIs.VIEW_BY_TEXT, "Standard");

		log.info("TC_03_12_Click chon cho ngoi loai ghe Thuong");
		filmTicketBooking.chooseSeats(1, colorOfSeat);
		
		log.info("TC_03_13_Lay ten rap va gia ve");
		info.cinemaName = filmTicketBooking.getTextViewByID("com.VCB:id/tvTitle");
		info.price = filmTicketBooking.getTextViewByID("com.VCB:id/tvPrince");
		
		log.info("TC_03_14_Click Thanh toan");
		filmTicketBooking.clickToTextViewByText("Thanh toán");
		
		log.info("TC_03_15_Kiem tra Thong tin Thanh toan ve xem phim");
		log.info("TC_03_15_01: Kiem tra ten phim");
		verifyEquals(filmTicketBooking.getDynamicTextInFilmTicketInfoDetail("Phim"), info.filmName);
		
		log.info("TC_03_15_02: Kiem tra suat chieu");
		verifyTrue(filmTicketBooking.getDynamicTextInFilmTicketInfoDetail("Suất chiếu").contains(info.time));
		
		log.info("TC_03_15_03: Kiem tra thời lượng");
		verifyEquals(filmTicketBooking.getDynamicTextInFilmTicketInfoDetail("Thời lượng"), info.filmDuration);
		
		log.info("TC_03_15_04: Kiem tra ten rap");
		info.cinemaAddress = filmTicketBooking.getTextViewByID("com.VCB:id/tvCinemaAddress");
		verifyEquals(filmTicketBooking.getDynamicTextInFilmTicketInfoDetail("Rạp"), info.cinemaName);
		
		log.info("TC_03_15_05: Kiem tra so tien");
		verifyEquals(filmTicketBooking.getDynamicTextInFilmTicketInfoDetail("Số tiền"), info.price);
		
		log.info("TC_03_16_Nhap thong tin nguoi nhan ve");
		log.info("TC_03_16_01_Nhap ten khach hang");
		filmTicketBooking.inputToDynamicInputBoxByID("Duc Do", "com.VCB:id/etCustomerName");
		
		log.info("TC_03_16_02_Nhap sdt");
		filmTicketBooking.inputToDynamicInputBoxByID(FilmTicketBooking_Data.PHONE_BOOKING, "com.VCB:id/etPhoneNumber");
		
		log.info("TC_03_16_03_Nhap email");
		filmTicketBooking.scrollDownToText("Thanh toán");
		filmTicketBooking.inputToDynamicInputBoxByID(FilmTicketBooking_Data.EMAIL_BOOKING, "com.VCB:id/etEmail");
		
		log.info("TC_03_17_Click Thanh toan");
		filmTicketBooking.clickToTextViewByText("Thanh toán");

		log.info("TC_03_18_Chon tai khoan nguon");
		filmTicketBooking.clickToDynamicDropDown("Tài khoản nguồn");
		filmTicketBooking.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);
		surplus = convertAvailableBalanceCurrentcyOrFeeToLong(filmTicketBooking.getDynamicTextInTransactionDetail("Số dư khả dụng"));
		
		log.info("TC_03_19_Kiem tra man hinh Thong tin mua ve");
		log.info("TC_03_19_01: Kiem tra ten phim");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail("Phim"), info.filmName);
		
		log.info("TC_03_19_02: Kiem tra suat chieu");
		verifyTrue(filmTicketBooking.getDynamicTextInTransactionDetail("Suất chiếu").contains(info.time));
		
		log.info("TC_03_19_03: Kiem tra thời lượng");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail("Thời lượng"), info.filmDuration);
		
		log.info("TC_03_19_04: Kiem tra ten rap");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail("Rạp"), info.cinemaName);
		
		log.info("TC_03_19_05: Kiem tra dia chi rap");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail("Địa chỉ"), info.cinemaAddress);
		
		log.info("TC_03_19_06: Kiem tra so tien");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail("Số tiền"), info.price);
		
		log.info("TC_03_19_07: Kiem tra ten khach hang");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail("Tên khách hàng"), "Duc Do");
		
		log.info("TC_03_19_08: Kiem tra sdt");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail("Số điện thoại nhận vé"), FilmTicketBooking_Data.PHONE_BOOKING);
		
		log.info("TC_03_19_09: Kiem tra email");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail("Email nhận vé"), FilmTicketBooking_Data.EMAIL_BOOKING);
		
		log.info("TC_03_20: Click Tiep tuc");
		filmTicketBooking.clickToDynamicButton("Tiếp tục");
		
		log.info("TC_03_21: Kiem tra man hinh xac nhan thong tin");
		log.info("TC_03_21_01: Kiem tra tai khoan nguon");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail("Tài khoản nguồn"), Account_Data.Valid_Account.ACCOUNT2);
		
		log.info("TC_03_21_02: Kiem tra so tien");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail("Số tiền"), info.price);
		
		log.info("TC_03_22_Chon phuong thuc xac thuc");
		filmTicketBooking.clickToDynamicTextView("Mật khẩu đăng nhập");
		fee = convertAvailableBalanceCurrentcyOrFeeToLong(filmTicketBooking.getDynamicTextInTransactionDetail("Mật khẩu đăng nhập"));
		filmTicketBooking.clickToDynamicTextView("Mật khẩu đăng nhập");
		
		log.info("TC_03_22_01: Kiem tra so tien phi");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail("Số tiền phí"), addCommasToLong(fee + "") + " VND");
		
		log.info("TC_03_23: Click Tiep tuc");
		filmTicketBooking.clickToDynamicButton("Tiếp tục");
		
		filmTicketBooking.inputToDynamicPopupPasswordInput(password, "Tiếp tục");

		filmTicketBooking.clickToDynamicButton("Tiếp tục");
		
		log.info("TC_03_24: Kiem tra man hinh thanh toan thanh cong");
		verifyTrue(filmTicketBooking.isDynamicMessageAndLabelTextDisplayed("THANH TOÁN THÀNH CÔNG"));
		transferTime = filmTicketBooking.getTransferTimeSuccess("THANH TOÁN THÀNH CÔNG");
		transactionNumber = filmTicketBooking.getDynamicTextInTransactionDetail("Mã giao dịch");
		ticketCode = filmTicketBooking.getDynamicTextInTransactionDetail("Mã vé");
		
		log.info("TC_03_25_Kiem tra nut Thuc hien giao dich moi");
		verifyTrue(filmTicketBooking.isDynamicButtonDisplayed("Thực hiện giao dịch mới"));
		
		log.info("TC_03_26_Click Thuc hien giao dich moi");
		filmTicketBooking.clickToDynamicButton("Thực hiện giao dịch mới");
		
		log.info("TC_03_27_Tinh so du kha dung cua tai khoan sau khi thanh toan thanh cong");
		availableBalance = canculateAvailableBalances(surplus, convertAvailableBalanceCurrentcyOrFeeToLong(info.price), fee);
		
	}

//	@Test
	public void TC_04_DatVeXemPhim_Rap_CineStar_BaoCaoGiaoDich() {
		log.info("TC_04_1: Click  nut Back");
		filmTicketBooking.clickToDynamicBackIcon("Mua vé xem phim");

		log.info("TC_04_2: Click vao More Icon");
		filmTicketBooking.clickToDynamicImageViewByID("com.VCB:id/menu_5");

		log.info("TC_04_3: Click Bao Cao giao Dich");
		filmTicketBooking.clickToDynamicTextView("Báo cáo giao dịch");

		log.info("TC_04_4: Click Tat Ca Cac Loai Giao Dich");
		filmTicketBooking.clickToDynamicTextView("Tất cả các loại giao dịch");

		log.info("TC_04_5: Chon Chuyen Tien Trong VCB");
		filmTicketBooking.clickToDynamicTextView("Thanh toán vé xem phim");

		log.info("TC_04_6: Click Chon Tai Khoan");
		filmTicketBooking.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvSelectAcc");
		
		log.info("TC_04_7: Lay so du kha dung luc sau");
		actualAvailableBalance = convertAvailableBalanceCurrentcyOrFeeToLong(filmTicketBooking.getDynamicTextInTransactionDetail(Account_Data.Valid_Account.ACCOUNT2));
		
		log.info("TC_04_8: Kiem tra so du kha dung sau khi thanh toan thanh cong");
		verifyEquals(actualAvailableBalance, availableBalance);
		
		log.info("TC_04_9: Chon tai Khoan chuyen");
		filmTicketBooking.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_04_10: Click Tim Kiem");
		filmTicketBooking.clickToDynamicButton("Tìm kiếm");

		log.info("TC_04_11: Kiem tra ngay tao giao dich hien thi");
		String reportTime1 = filmTicketBooking.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
		verifyEquals(convertDateTimeIgnoreHHmmss(reportTime1), convertTransferTimeToReportDateTime(transferTime));
		
		log.info("TC_04_12: Kiem tra so tien chuyen hien thi");
		verifyEquals(filmTicketBooking.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + info.price));
		
		log.info("TC_04_13: Click vao giao dich");
		filmTicketBooking.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
		
		log.info("TC_04_14: Kiem tra thoi gian tao giao dich hien thi");
		String reportTime2 = filmTicketBooking.getDynamicTextInTransactionDetail("Thời gian giao dịch");
		verifyEquals(reportTime2, reportTime1);

		log.info("TC_04_15: Kiem tra ma giao dich");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail("Số lệnh giao dịch"), transactionNumber);
		
		log.info("TC_04_16: Kiem tra so tai khoan trich no");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail("Tài khoản/thẻ trích nợ"), Account_Data.Valid_Account.ACCOUNT2);
		
		log.info("TC_04_17: Kiem tra ma ve");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail("Mã vé"), ticketCode);
		
		log.info("TC_04_18: Kiem tra so tien giao dich hien thi");
		verifyTrue(filmTicketBooking.getDynamicTextInTransactionDetail("Số tiền giao dịch").contains(info.price));
		
		log.info("TC_04_19: Kiem tra so tien phi");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail("Số tiền phí"), addCommasToLong(fee + "") + " VND");
		
		log.info("TC_04_20: Kiem tra loai giao dich");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail("Loại giao dịch"), "Thanh toán vé xem phim");
		
		log.info("TC_04_21: Kiem tra noi dung giao dich");
		String note = "MBVCB" + transactionNumber + ". thanh toan ve xem phim";
		verifyTrue(filmTicketBooking.getDynamicTextInTransactionDetail("Nội dung giao dịch").contains(note));
		
	}
	
	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
