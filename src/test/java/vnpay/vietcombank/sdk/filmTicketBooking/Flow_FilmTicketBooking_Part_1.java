package vnpay.vietcombank.sdk.filmTicketBooking;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.Constants;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import model.FilmInfo;
import model.FilmTicketInfo;
import model.SeatType;
import model.SourceAccountModel;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.SettingVCBSmartOTPPageObject;
import pageObjects.sdk.filmTicketBooking.FilmTicketBookingPageObject;
import vietcombankUI.sdk.filmTicketBooking.FilmTicketBookingPageUIs;
import vietcombank_test_data.Notify_Management_Data.Notify_Text;
import vietcombank_test_data.TransactionReport_Data.ReportTitle;
import vnpay.vietcombank.sdk.filmTicketBooking.data.FilmTicketBooking_Data;

public class Flow_FilmTicketBooking_Part_1 extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private FilmTicketBookingPageObject filmTicketBooking;
	private String transferTime, transactionNumber, ticketCode;
	private long surplus, availableBalance, actualAvailableBalance, fee;
	private SettingVCBSmartOTPPageObject smartOTP;
	String password = "";
	String account;
	String passSmartOTP  = "111222";
	SourceAccountModel sourceAccount = new SourceAccountModel();
	FilmTicketInfo info = new FilmTicketInfo();

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException, GeneralSecurityException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
		password = pass;
		
		homePage = PageFactoryManager.getHomePageObject(driver);
		filmTicketBooking = PageFactoryManager.getFilmTicketBookingPageObject(driver);
		smartOTP = PageFactoryManager.getSettingVCBSmartOTPPageObject(driver);
		smartOTP.setupSmartOTP(passSmartOTP, getDataInCell(6));
	}

	@Test
	public void TC_01_DatVeXemPhim_Rap_MegaGS_BHDCineplex() {
		
		log.info("TC_01_01_Click Dat ve xem phim");
		filmTicketBooking.clickToDynamicTextOrButtonLink(FilmTicketBooking_Data.FILM_TITLE);

		log.info("TC_01_02_Click nut Dong y");
		filmTicketBooking.clickToDynamicButton(FilmTicketBooking_Data.AGREE);

		log.info("TC_01_03_Click chon Tinh thanh");
		filmTicketBooking.clickToDynamicTextViewByID("com.VCB:id/tvLocationName");

		log.info("TC_01_04_Tim kiem thanh pho");
		filmTicketBooking.inputIntoEditTextByID(FilmTicketBooking_Data.CITY, "com.VCB:id/edtSearch");

		log.info("TC_01_05_Click chon thanh pho");
		filmTicketBooking.clickToDynamicTextView(FilmTicketBooking_Data.CITY);

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
		filmTicketBooking.clickToTextViewByText(FilmTicketBooking_Data.BOOKING_TICKET);

		log.info("TC_01_10_Nhan chon gio chieu");
		info.time = filmTicketBooking.getDynamicTextViewByViewGroupID("com.VCB:id/tagShowtimes2D", "0");
		filmTicketBooking.clickToDynamicTextViewByViewGroupID("com.VCB:id/tagShowtimes2D", "0");

		log.info("TC_01_11_Chon 1 ghe");
		filmTicketBooking.clickToDynamicTextViewByID("com.VCB:id/tvPlus");

		List<SeatType> listSeatType = filmTicketBooking.getListSeatType();
		String type = filmTicketBooking.getTypeOfSeat(listSeatType);

		log.info("TC_01_12_Click chon cho ngoi");
		filmTicketBooking.clickToTextViewByText("Chọn chỗ ngồi");

		log.info("TC_01_13_Chon cho ngoi nhu da dang ky");
		String colorOfSeat = filmTicketBooking.getColorOfElement(FilmTicketBookingPageUIs.VIEW_BY_TEXT, type);
		filmTicketBooking.chooseSeats(1, colorOfSeat);

		info.cinemaName = filmTicketBooking.getTextViewByID("com.VCB:id/tvTitle");
		info.price = filmTicketBooking.getTextViewByID("com.VCB:id/tvPrince");

		log.info("TC_01_14_Click Thanh toan");
		filmTicketBooking.clickToTextViewByText(FilmTicketBooking_Data.PAY);

		log.info("TC_01_15_Kiem tra Thong tin Thanh toan ve xem phim");
		log.info("TC_01_15_01: Kiem tra ten phim");
		filmTicketBooking.scrollUpToText(FilmTicketBooking_Data.FILM);
		verifyEquals(filmTicketBooking.getDynamicTextInFilmTicketInfoDetail(FilmTicketBooking_Data.FILM), info.filmName);

		log.info("TC_01_15_02: Kiem tra suat chieu");
		verifyTrue(filmTicketBooking.getDynamicTextInFilmTicketInfoDetail(FilmTicketBooking_Data.TIME_SLOT).contains(info.time));

		log.info("TC_01_15_03: Kiem tra thời lượng");
		verifyEquals(filmTicketBooking.getDynamicTextInFilmTicketInfoDetail(FilmTicketBooking_Data.TIME), info.filmDuration);

		log.info("TC_01_15_04: Kiem tra ten rap");
		info.cinemaAddress = filmTicketBooking.getTextViewByID("com.VCB:id/tvCinemaAddress");
		verifyEquals(filmTicketBooking.getDynamicTextInFilmTicketInfoDetail(FilmTicketBooking_Data.MOVIE_THEATER), info.cinemaName);

		log.info("TC_01_15_05: Kiem tra so tien");
		verifyEquals(filmTicketBooking.getDynamicTextInFilmTicketInfoDetail(FilmTicketBooking_Data.MONEY), info.price);

		log.info("TC_01_16_Nhap thong tin nguoi nhan ve");
		log.info("TC_01_16_01_Nhap ten khach hang");
		filmTicketBooking.inputToDynamicInputBoxByID("Duc Do", "com.VCB:id/etCustomerName");

		log.info("TC_01_16_02_Nhap sdt");
		filmTicketBooking.inputToDynamicInputBoxByID(FilmTicketBooking_Data.PHONE_BOOKING, "com.VCB:id/etPhoneNumber");

		log.info("TC_01_16_03_Nhap email");
		filmTicketBooking.scrollDownToText(FilmTicketBooking_Data.PAY);
		filmTicketBooking.inputToDynamicInputBoxByID(FilmTicketBooking_Data.EMAIL_BOOKING, "com.VCB:id/etEmail");

		log.info("TC_01_17_Click Thanh toan");
		filmTicketBooking.clickToTextViewByText(FilmTicketBooking_Data.PAY);

		log.info("TC_01_18_Chon tai khoan nguon");
		filmTicketBooking.clickToDynamicDropDown(FilmTicketBooking_Data.ACCOUNT_FROM_LABEL);
		sourceAccount = filmTicketBooking.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, "VND");
		account = sourceAccount.account;
		surplus = convertAvailableBalanceCurrentcyOrFeeToLong(filmTicketBooking.getDynamicTextInTransactionDetail("Số dư khả dụng"));

		log.info("TC_01_19_Kiem tra man hinh Thong tin mua ve");
		log.info("TC_01_19_01: Kiem tra ten phim");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(FilmTicketBooking_Data.FILM), info.filmName);

		log.info("TC_01_19_02: Kiem tra suat chieu");
		verifyTrue(filmTicketBooking.getDynamicTextInTransactionDetail(FilmTicketBooking_Data.TIME_SLOT).contains(info.time));

		log.info("TC_01_19_03: Kiem tra thời lượng");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(FilmTicketBooking_Data.TIME), info.filmDuration);

		log.info("TC_01_19_04: Kiem tra ten rap");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(FilmTicketBooking_Data.MOVIE_THEATER), info.cinemaName);

		log.info("TC_01_19_05: Kiem tra dia chi rap");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(FilmTicketBooking_Data.ADDRESS), info.cinemaAddress);

		log.info("TC_01_19_06: Kiem tra so tien");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(FilmTicketBooking_Data.MONEY), info.price);

		log.info("TC_01_19_07: Kiem tra ten khach hang");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(FilmTicketBooking_Data.NAME_CUSTOMER), "Duc Do");

		log.info("TC_01_19_08: Kiem tra sdt");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(FilmTicketBooking_Data.PHONE_TAKE_TICKET), FilmTicketBooking_Data.PHONE_BOOKING);

		log.info("TC_01_19_09: Kiem tra email");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(FilmTicketBooking_Data.EMAIL_TAKE_TICKET), FilmTicketBooking_Data.EMAIL_BOOKING);

		log.info("TC_01_20: Click Tiep tuc");
		filmTicketBooking.clickToDynamicButton(FilmTicketBooking_Data.NEXT);

		log.info("TC_01_21: Kiem tra man hinh xac nhan thong tin");
		
		log.info("TC_01_21_01: Kiem tra tai khoan nguon");
		filmTicketBooking.scrollUpToText(FilmTicketBooking_Data.ACCOUNT_FROM_LABEL);
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(FilmTicketBooking_Data.ACCOUNT_FROM_LABEL), account);

		log.info("TC_01_21_02: Kiem tra so tien");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(FilmTicketBooking_Data.MONEY), info.price);

		log.info("TC_01_22_Chon phuong thuc xac thuc");
		filmTicketBooking.clickToDynamicTextViewByID("com.VCB:id/tvptxt");
		filmTicketBooking.clickToDynamicTextView(FilmTicketBooking_Data.PASSWORD);

		log.info("TC_01_22_01: Kiem tra so tien phi");
		fee = convertAvailableBalanceCurrentcyOrFeeToLong(filmTicketBooking.getDynamicTextInTransactionDetail(FilmTicketBooking_Data.FEE));
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(FilmTicketBooking_Data.FEE), addCommasToLong(fee + "") + " VND");

		log.info("TC_01_23: Click Tiep tuc");
		filmTicketBooking.clickToDynamicButton(FilmTicketBooking_Data.NEXT);

		filmTicketBooking.inputToDynamicPopupPasswordInput(password, FilmTicketBooking_Data.NEXT);

		filmTicketBooking.clickToDynamicButton(FilmTicketBooking_Data.NEXT);

		log.info("TC_01_24: Kiem tra man hinh thanh toan thanh cong");
		verifyTrue(filmTicketBooking.isDynamicMessageAndLabelTextDisplayed(FilmTicketBooking_Data.MESSEGE_SUCCESS));
		transferTime = filmTicketBooking.getTransferTimeSuccess(FilmTicketBooking_Data.MESSEGE_SUCCESS);
		transactionNumber = filmTicketBooking.getDynamicTextInTransactionDetail(FilmTicketBooking_Data.CODE_TRANSFER);
		ticketCode = filmTicketBooking.getDynamicTextInTransactionDetail(ReportTitle.CODE_TICKET);

		log.info("TC_01_25_Kiem tra nut Thuc hien giao dich moi");
		verifyTrue(filmTicketBooking.isDynamicButtonDisplayed(FilmTicketBooking_Data.NEW_TRANSFER));

		log.info("TC_01_26_Click Thuc hien giao dich moi");
		filmTicketBooking.clickToDynamicButton(FilmTicketBooking_Data.NEW_TRANSFER);

		log.info("TC_01_27_Tinh so du kha dung cua tai khoan sau khi thanh toan thanh cong");
		availableBalance = canculateAvailableBalances(surplus, convertAvailableBalanceCurrentcyOrFeeToLong(info.price), fee);

	}

	@Test
	public void TC_02_DatVeXemPhim_Rap_MegaGS_CineStar_BaoCaoGiaoDich() {
		log.info("TC_02_1: Click  nut Back");
		filmTicketBooking.clickToDynamicBackIcon(FilmTicketBooking_Data.BUY_TICKET_FILM);

		log.info("TC_02_2: Click vao More Icon");
		filmTicketBooking.clickToDynamicImageViewByID("com.VCB:id/menu_5");

		log.info("TC_02_3: Click Bao Cao giao Dich");
		filmTicketBooking.clickToDynamicTextView(ReportTitle.TRANSACTION_REPORT);

		log.info("TC_02_4: Click Tat Ca Cac Loai Giao Dich");
		filmTicketBooking.clickToDynamicTextView(ReportTitle.ALL_TYPE_TRANSACTION);

		log.info("TC_02_5: Chon Chuyen Tien Trong VCB");
		filmTicketBooking.clickToDynamicTextView(ReportTitle.PAY_FILM_TICKET);

		log.info("TC_02_6: Click Chon Tai Khoan");
		filmTicketBooking.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvSelectAcc");

		log.info("TC_02_7: Lay so du kha dung luc sau");
		actualAvailableBalance = convertAvailableBalanceCurrentcyOrFeeToLong(filmTicketBooking.getDynamicTextInTransactionDetail(account));

		log.info("TC_02_8: Kiem tra so du kha dung sau khi thanh toan thanh cong");
		verifyEquals(actualAvailableBalance, availableBalance);

		log.info("TC_02_9: Chon tai Khoan chuyen");
		filmTicketBooking.clickToDynamicButtonLinkOrLinkText(driver, account);

		log.info("TC_02_10: Click Tim Kiem");
		filmTicketBooking.clickToDynamicButton(ReportTitle.SEARCH_BUTTON);

		log.info("TC_02_11: Kiem tra ngay tao giao dich hien thi");
		String reportTime1 = filmTicketBooking.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
		verifyEquals(convertDateTimeIgnoreHHmmss(reportTime1), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_02_12: Kiem tra so tien chuyen hien thi");
		verifyEquals(filmTicketBooking.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + info.price));

		log.info("TC_02_13: Click vao giao dich");
		filmTicketBooking.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_02_14: Kiem tra thoi gian tao giao dich hien thi");
		String reportTime2 = filmTicketBooking.getDynamicTextInTransactionDetail(ReportTitle.TIME_TRANSFER);
		verifyEquals(reportTime2, reportTime1);

		log.info("TC_02_15: Kiem tra ma giao dich");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(ReportTitle.TRANSACTION_NUMBER), transactionNumber);

		log.info("TC_02_16: Kiem tra so tai khoan trich no");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(ReportTitle.ACCOUNT_CARD), account);

		log.info("TC_02_17: Kiem tra ma ve");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(ReportTitle.CODE_TICKET), ticketCode);

		log.info("TC_02_18: Kiem tra so tien giao dich hien thi");
		verifyTrue(filmTicketBooking.getDynamicTextInTransactionDetail(ReportTitle.TRANSACTION_MONEY).contains(info.price));

		log.info("TC_02_19: Kiem tra so tien phi");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(FilmTicketBooking_Data.FEE), addCommasToLong(fee + "") + " VND");

		log.info("TC_02_20: Kiem tra loai giao dich");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(ReportTitle.TRANSACTION_TYPE), ReportTitle.PAY_FILM_TICKET);

		log.info("------------------------------------TC_02_21: Kiem tra noi dung giao dich------------------------------------");
		String note = "MBVCB." + transactionNumber + ". thanh toan ve xem phim";
		verifyTrue(filmTicketBooking.getDynamicTextInTransactionDetail(ReportTitle.TRANSACTION_CONTENT).contains(note));

		log.info("TC_02_22: Click  nut Back");
		filmTicketBooking.clickToDynamicBackIcon("Chi tiết giao dịch");

		log.info("TC_02_23: Click  nut Back");
		filmTicketBooking.clickToDynamicBackIcon(ReportTitle.TRANSACTION_REPORT);

		log.info("TC_02_24: Click  nut Home");
		filmTicketBooking.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");

	}
	
	@Test
	public void TC_03_KiemTra_HienThiThongBao_DaDangNhap() {
		
		log.info("TC_03_Step_01: Click vao Inbox");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_3");
		
		log.info("TC_03_Step_02: Click vao tab Tat ca");
		homePage.clickToTextID(driver, "com.VCB:id/radioAll");
		
		log.info("TC_03_Step_03: Lay du lieu hien thi");
		String inboxContent = homePage.getDynamicTextByContentID(driver, "com.VCB:id/recycleview","com.VCB:id/content",ticketCode);
		
		log.info("TC_03_Step_04: So sanh thong tin thanh toan");
		verifyTrue(inboxContent.contains(ticketCode));
		verifyTrue(inboxContent.contains(info.filmName));
		
		log.info("TC_03_Step_05: Click vao tab Khac");
		homePage.clickToTextID(driver, "com.VCB:id/radioOther");
		
		log.info("TC_03_Step_06: Lay du lieu hien thi");
		inboxContent = homePage.getDynamicTextByContentID(driver, "com.VCB:id/recycleview","com.VCB:id/content",ticketCode);
		
		log.info("TC_03_Step_07: So sanh thong tin thanh toan");
		verifyTrue(inboxContent.contains(ticketCode));
		verifyTrue(inboxContent.contains(info.filmName));
		
		log.info("TC_03_Step_08: Mo tab Home");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@Test
	public void TC_04_DatVeXemPhim_Rap_CineStar() {
		
		log.info("TC_04_01_Click Dat ve xem phim");
		filmTicketBooking.clickToDynamicTextOrButtonLink(FilmTicketBooking_Data.FILM_TITLE);

		log.info("TC_04_02_Click nut Dong y");
		filmTicketBooking.clickToDynamicButton(FilmTicketBooking_Data.AGREE);

		log.info("TC_04_03_Click chon Tinh thanh");
		filmTicketBooking.clickToDynamicTextViewByID("com.VCB:id/tvLocationName");

		log.info("TC_04_04_Tim kiem thanh pho");
		filmTicketBooking.inputIntoEditTextByID(FilmTicketBooking_Data.CITY, "com.VCB:id/edtSearch");

		log.info("TC_04_05_Click chon thanh pho");
		filmTicketBooking.clickToDynamicTextView(FilmTicketBooking_Data.CITY);

		log.info("TC_04_06_Click chon cum rap Cinestar");
		filmTicketBooking.clickToDynamicTextView("Cinestar");

		log.info("TC_04_07_Click chon rap phim");
		List<String> listCinema = filmTicketBooking.getListOfSuggestedMoneyOrListText("com.VCB:id/tvNameCinema");
		String cinemaName = listCinema.get(0);
		filmTicketBooking.clickToDynamicTextView(cinemaName);

		log.info("TC_04_08_Click xem chi tiet phim");
		FilmInfo filmInfo = filmTicketBooking.getInfoOfTheFirstFilm();
		filmTicketBooking.clickToDynamicTextView(filmInfo.filmName);
		info.filmName = filmInfo.filmName;
		info.filmDuration = filmTicketBooking.canculateDurationOfFilm(filmInfo.filmDuration);

		log.info("TC_04_09_Nhan nut Dat ve");
		filmTicketBooking.clickToTextViewByText(FilmTicketBooking_Data.BOOKING_TICKET);

		log.info("TC_04_10_Nhan chon gio chieu");
		info.time = filmTicketBooking.getDynamicTextViewByViewGroupID("com.VCB:id/tagShowtimes2D", "0");
		filmTicketBooking.clickToDynamicTextViewByViewGroupID("com.VCB:id/tagShowtimes2D", "0");

		log.info("TC_04_11_Lay mau cua loai ghe Thuong");
		String colorOfSeat = filmTicketBooking.getColorOfElement(FilmTicketBookingPageUIs.VIEW_BY_TEXT, "Standard");

		log.info("TC_04_12_Click chon cho ngoi loai ghe Thuong");
		filmTicketBooking.chooseSeats(1, colorOfSeat);

		log.info("TC_04_13_Lay ten rap va gia ve");
		info.cinemaName = filmTicketBooking.getTextViewByID("com.VCB:id/tvTitle");
		info.price = filmTicketBooking.getTextViewByID("com.VCB:id/tvPrince");

		log.info("TC_04_14_Click Thanh toan");
		filmTicketBooking.clickToTextViewByText(FilmTicketBooking_Data.PAY);

		log.info("TC_04_15_Kiem tra Thong tin Thanh toan ve xem phim");
		log.info("TC_04_15_01: Kiem tra ten phim");
		verifyEquals(filmTicketBooking.getDynamicTextInFilmTicketInfoDetail(FilmTicketBooking_Data.FILM), info.filmName);

		log.info("TC_04_15_02: Kiem tra suat chieu");
		verifyTrue(filmTicketBooking.getDynamicTextInFilmTicketInfoDetail(FilmTicketBooking_Data.TIME_SLOT).contains(info.time));

		log.info("TC_04_15_03: Kiem tra thời lượng");
		verifyEquals(filmTicketBooking.getDynamicTextInFilmTicketInfoDetail(FilmTicketBooking_Data.TIME), info.filmDuration);

		log.info("TC_04_15_04: Kiem tra ten rap");
		info.cinemaAddress = filmTicketBooking.getTextViewByID("com.VCB:id/tvCinemaAddress");
		verifyEquals(filmTicketBooking.getDynamicTextInFilmTicketInfoDetail(FilmTicketBooking_Data.MOVIE_THEATER), info.cinemaName);

		log.info("TC_04_15_05: Kiem tra so tien");
		verifyEquals(filmTicketBooking.getDynamicTextInFilmTicketInfoDetail(FilmTicketBooking_Data.MONEY), info.price);

		log.info("TC_04_16_Nhap thong tin nguoi nhan ve");
		log.info("TC_04_16_01_Nhap ten khach hang");
		filmTicketBooking.inputToDynamicInputBoxByID("Duc Do", "com.VCB:id/etCustomerName");

		log.info("TC_04_16_02_Nhap sdt");
		filmTicketBooking.inputToDynamicInputBoxByID(FilmTicketBooking_Data.PHONE_BOOKING, "com.VCB:id/etPhoneNumber");

		log.info("TC_04_16_03_Nhap email");
		filmTicketBooking.scrollDownToText(FilmTicketBooking_Data.PAY);
		filmTicketBooking.inputToDynamicInputBoxByID(FilmTicketBooking_Data.EMAIL_BOOKING, "com.VCB:id/etEmail");

		log.info("TC_04_17_Click Thanh toan");
		filmTicketBooking.clickToTextViewByText(FilmTicketBooking_Data.PAY);

		log.info("TC_04_18_Chon tai khoan nguon");
		filmTicketBooking.clickToDynamicDropDown(FilmTicketBooking_Data.ACCOUNT_FROM_LABEL);
		sourceAccount = filmTicketBooking.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, "VND");
		account = sourceAccount.account;
		surplus = convertAvailableBalanceCurrentcyOrFeeToLong(filmTicketBooking.getDynamicTextInTransactionDetail("Số dư khả dụng"));

		log.info("TC_04_19_Kiem tra man hinh Thong tin mua ve");
		log.info("TC_04_19_01: Kiem tra ten phim");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(FilmTicketBooking_Data.FILM), info.filmName);

		log.info("TC_04_19_02: Kiem tra suat chieu");
		verifyTrue(filmTicketBooking.getDynamicTextInTransactionDetail(FilmTicketBooking_Data.TIME_SLOT).contains(info.time));

		log.info("TC_04_19_03: Kiem tra thời lượng");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(FilmTicketBooking_Data.TIME), info.filmDuration);

		log.info("TC_04_19_04: Kiem tra ten rap");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(FilmTicketBooking_Data.MOVIE_THEATER), info.cinemaName);

		log.info("TC_04_19_05: Kiem tra dia chi rap");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(FilmTicketBooking_Data.ADDRESS), info.cinemaAddress);

		log.info("TC_04_19_06: Kiem tra so tien");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(FilmTicketBooking_Data.MONEY), info.price);

		log.info("TC_04_19_07: Kiem tra ten khach hang");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(FilmTicketBooking_Data.NAME_CUSTOMER), "Duc Do");

		log.info("TC_04_19_08: Kiem tra sdt");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(FilmTicketBooking_Data.PHONE_TAKE_TICKET), FilmTicketBooking_Data.PHONE_BOOKING);

		log.info("TC_04_19_09: Kiem tra email");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(FilmTicketBooking_Data.EMAIL_TAKE_TICKET), FilmTicketBooking_Data.EMAIL_BOOKING);

		log.info("TC_04_20: Click Tiep tuc");
		filmTicketBooking.clickToDynamicButton(FilmTicketBooking_Data.NEXT);

		log.info("TC_04_21: Kiem tra man hinh xac nhan thong tin");
		log.info("TC_04_21_01: Kiem tra tai khoan nguon");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(FilmTicketBooking_Data.ACCOUNT_FROM_LABEL), account);

		log.info("TC_04_21_02: Kiem tra so tien");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(FilmTicketBooking_Data.MONEY), info.price);

		log.info("TC_04_22_Chon phuong thuc xac thuc");
		filmTicketBooking.clickToDynamicTextViewByID("com.VCB:id/tvptxt");
		filmTicketBooking.clickToDynamicTextView(FilmTicketBooking_Data.PASSWORD);

		log.info("TC_04_22_01: Kiem tra so tien phi");
		fee = convertAvailableBalanceCurrentcyOrFeeToLong(filmTicketBooking.getDynamicTextInTransactionDetail(FilmTicketBooking_Data.FEE));
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(FilmTicketBooking_Data.FEE), addCommasToLong(fee + "") + " VND");

		log.info("TC_04_23: Click Tiep tuc");
		filmTicketBooking.clickToDynamicButton(FilmTicketBooking_Data.NEXT);

		filmTicketBooking.inputToDynamicPopupPasswordInput(password, FilmTicketBooking_Data.NEXT);

		filmTicketBooking.clickToDynamicButton(FilmTicketBooking_Data.NEXT);

		log.info("TC_04_24: Kiem tra man hinh thanh toan thanh cong");
		verifyTrue(filmTicketBooking.isDynamicMessageAndLabelTextDisplayed(FilmTicketBooking_Data.MESSEGE_SUCCESS));
		transferTime = filmTicketBooking.getTransferTimeSuccess(FilmTicketBooking_Data.MESSEGE_SUCCESS);
		transactionNumber = filmTicketBooking.getDynamicTextInTransactionDetail(FilmTicketBooking_Data.CODE_TRANSFER);
		ticketCode = filmTicketBooking.getDynamicTextInTransactionDetail(ReportTitle.CODE_TICKET);

		log.info("TC_04_25_Kiem tra nut Thuc hien giao dich moi");
		verifyTrue(filmTicketBooking.isDynamicButtonDisplayed(FilmTicketBooking_Data.NEW_TRANSFER));

		log.info("TC_04_26_Click Thuc hien giao dich moi");
		filmTicketBooking.clickToDynamicButton(FilmTicketBooking_Data.NEW_TRANSFER);

		log.info("TC_04_27_Tinh so du kha dung cua tai khoan sau khi thanh toan thanh cong");
		availableBalance = canculateAvailableBalances(surplus, convertAvailableBalanceCurrentcyOrFeeToLong(info.price), fee);

	}

	@Test
	public void TC_05_DatVeXemPhim_Rap_CineStar_BaoCaoGiaoDich() {
		
		log.info("TC_05_1: Click  nut Back");
		filmTicketBooking.clickToDynamicBackIcon(FilmTicketBooking_Data.BUY_TICKET_FILM);

		log.info("TC_05_2: Click vao More Icon");
		filmTicketBooking.clickToDynamicImageViewByID("com.VCB:id/menu_5");

		log.info("TC_05_3: Click Bao Cao giao Dich");
		filmTicketBooking.clickToDynamicTextView(ReportTitle.TRANSACTION_REPORT);

		log.info("TC_05_4: Click Tat Ca Cac Loai Giao Dich");
		filmTicketBooking.clickToDynamicTextView(ReportTitle.ALL_TYPE_TRANSACTION);

		log.info("TC_05_5: Chon Chuyen Tien Trong VCB");
		filmTicketBooking.clickToDynamicTextView(ReportTitle.PAY_FILM_TICKET);

		log.info("TC_05_6: Click Chon Tai Khoan");
		filmTicketBooking.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvSelectAcc");

		log.info("TC_05_7: Lay so du kha dung luc sau");
		actualAvailableBalance = convertAvailableBalanceCurrentcyOrFeeToLong(filmTicketBooking.getDynamicTextInTransactionDetail(account));

		log.info("TC_05_8: Kiem tra so du kha dung sau khi thanh toan thanh cong");
		verifyEquals(actualAvailableBalance, availableBalance);

		log.info("TC_05_9: Chon tai Khoan chuyen");
		filmTicketBooking.clickToDynamicButtonLinkOrLinkText(driver, account);

		log.info("TC_05_10: Click Tim Kiem");
		filmTicketBooking.clickToDynamicButton(ReportTitle.SEARCH_BUTTON);

		log.info("TC_05_11: Kiem tra ngay tao giao dich hien thi");
		String reportTime1 = filmTicketBooking.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
		verifyEquals(convertDateTimeIgnoreHHmmss(reportTime1), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_05_12: Kiem tra so tien chuyen hien thi");
		verifyEquals(filmTicketBooking.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + info.price));

		log.info("TC_05_13: Click vao giao dich");
		filmTicketBooking.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_05_14: Kiem tra thoi gian tao giao dich hien thi");
		String reportTime2 = filmTicketBooking.getDynamicTextInTransactionDetail(ReportTitle.TIME_TRANSFER);
		verifyEquals(reportTime2, reportTime1);

		log.info("TC_05_15: Kiem tra ma giao dich");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(ReportTitle.TRANSACTION_NUMBER), transactionNumber);

		log.info("TC_05_16: Kiem tra so tai khoan trich no");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(ReportTitle.ACCOUNT_CARD), account);

		log.info("TC_05_17: Kiem tra ma ve");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(ReportTitle.CODE_TICKET), ticketCode);

		log.info("TC_05_18: Kiem tra so tien giao dich hien thi");
		verifyTrue(filmTicketBooking.getDynamicTextInTransactionDetail(ReportTitle.TRANSACTION_MONEY).contains(info.price));

		log.info("TC_05_19: Kiem tra so tien phi");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(FilmTicketBooking_Data.FEE), addCommasToLong(fee + "") + " VND");

		log.info("TC_05_20: Kiem tra loai giao dich");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(ReportTitle.TRANSACTION_TYPE), ReportTitle.PAY_FILM_TICKET);

		log.info("TC_05_21: Kiem tra noi dung giao dich");
		String note = "MBVCB." + transactionNumber + ". thanh toan ve xem phim";
		verifyTrue(filmTicketBooking.getDynamicTextInTransactionDetail(ReportTitle.TRANSACTION_CONTENT).contains(note));

	}
	
	@Parameters ({"pass"})
	@Test
	public void TC_06_KiemTra_HienThiThongBao_ChuaDangNhap(String password) {
		
		log.info("TC_06_Step_01: Click vao Menu");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");
		
		log.info("TC_06_Step_02: Click vao Thoat Ung Dung");
		homePage.scrollUpToText(driver, Notify_Text.LOG_OUT_TEXT);
		homePage.clickToDynamicButtonLinkOrLinkTextNotScroll(driver, Notify_Text.LOG_OUT_TEXT);
		
		log.info("TC_06_Step_03: Click vao Dong y");
		homePage.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
		
		log.info("TC_06_Step_04: Click vao Inbox");
		homePage.clickToDynamicImageView(driver, "com.VCB:id/ivOTT");
		
		log.info("TC_06_Step_05: Click vao tab Tat ca");
		homePage.clickToTextID(driver, "com.VCB:id/radioAll");
		
		log.info("TC_06_Step_06: Lay du lieu hien thi");
		String inboxContent = homePage.getDynamicTextByContentID(driver, "com.VCB:id/recycleview","com.VCB:id/content",ticketCode);
		
		log.info("TC_06_Step_07: So sanh thong tin thanh toan");
		verifyTrue(inboxContent.contains(ticketCode));
		verifyTrue(inboxContent.contains(info.filmName));
		
		log.info("TC_06_Step_08: Click vao tab Khac");
		homePage.clickToTextID(driver, "com.VCB:id/radioOther");
		
		log.info("TC_06_Step_09: Lay du lieu hien thi");
		inboxContent = homePage.getDynamicTextByContentID(driver, "com.VCB:id/recycleview","com.VCB:id/content",ticketCode);

		log.info("TC_06_Step_10: So sanh thong tin thanh toan");
		verifyTrue(inboxContent.contains(ticketCode));
		verifyTrue(inboxContent.contains(info.filmName));
		
		log.info("TC_06_Step_11: Back ve man Log In");
		homePage.clickToDynamicImageView(driver, "com.VCB:id/back");
		
		log.info("TC_06_Step_12: Nhap mat khau va dang nhap");
		homePage.inputIntoEditTextByID(driver, password, "com.VCB:id/edtInput");
		homePage.clickToDynamicAcceptButton(driver, "com.VCB:id/btnNext");
		
	}
	
	@Test
	public void TC_07_DatVeXemPhim_Rap_MegaGS_BHDCineplex_SmartOTP() {
		log.info("TC_07_01_Click Dat ve xem phim");
		filmTicketBooking.clickToDynamicTextOrButtonLink(FilmTicketBooking_Data.FILM_TITLE);

		log.info("TC_07_02_Click nut Dong y");
		filmTicketBooking.clickToDynamicButton(FilmTicketBooking_Data.AGREE);

		log.info("TC_07_03_Click chon Tinh thanh");
		filmTicketBooking.clickToDynamicTextViewByID("com.VCB:id/tvLocationName");

		log.info("TC_07_04_Tim kiem thanh pho");
		filmTicketBooking.inputIntoEditTextByID(FilmTicketBooking_Data.CITY, "com.VCB:id/edtSearch");

		log.info("TC_07_05_Click chon thanh pho");
		filmTicketBooking.clickToDynamicTextView(FilmTicketBooking_Data.CITY);

		log.info("TC_07_06_Click chon cum rap Mega GS");
		filmTicketBooking.clickToDynamicTextView(FilmTicketBooking_Data.BHD_MOVIE_THEATER );

		log.info("TC_07_07_Click chon rap phim");
		List<String> listCinema = filmTicketBooking.getListOfSuggestedMoneyOrListText("com.VCB:id/tvNameCinema");
		String cinemaName = listCinema.get(0);
		filmTicketBooking.clickToDynamicTextView(cinemaName);

		log.info("TC_07_08_Click xem chi tiet phim");
		FilmInfo filmInfo = filmTicketBooking.getInfoOfTheFirstFilm();
		filmTicketBooking.clickToDynamicTextView(filmInfo.filmName);
		info.filmName = filmInfo.filmName;
		info.filmDuration = filmTicketBooking.canculateDurationOfFilm(filmInfo.filmDuration);

		log.info("TC_07_09_Nhan nut Dat ve");
		filmTicketBooking.clickToTextViewByText(FilmTicketBooking_Data.BOOKING_TICKET);

		log.info("TC_07_10_Nhan chon gio chieu");
		info.time = filmTicketBooking.getDynamicTextViewByViewGroupID("com.VCB:id/tagShowtimes2D", "0");
		filmTicketBooking.clickToDynamicTextViewByViewGroupID("com.VCB:id/tagShowtimes2D", "0");

		log.info("TC_07_11_Chon 1 ghe");
		filmTicketBooking.clickToDynamicTextViewByID("com.VCB:id/tvPlus");

		List<SeatType> listSeatType = filmTicketBooking.getListSeatType();
		String type = filmTicketBooking.getTypeOfSeat(listSeatType);

		log.info("TC_07_12_Click chon cho ngoi");
		filmTicketBooking.clickToTextViewByText(FilmTicketBooking_Data.CHOOSE_SEAT);

		log.info("TC_07_13_Chon cho ngoi nhu da dang ky");
		String colorOfSeat = filmTicketBooking.getColorOfElement(FilmTicketBookingPageUIs.VIEW_BY_TEXT, type);
		filmTicketBooking.chooseSeats(1, colorOfSeat);

		info.cinemaName = filmTicketBooking.getTextViewByID("com.VCB:id/tvTitle");
		info.price = filmTicketBooking.getTextViewByID("com.VCB:id/tvPrince");

		log.info("TC_07_14_Click Thanh toan");
		filmTicketBooking.clickToTextViewByText(FilmTicketBooking_Data.PAY);

		log.info("TC_07_15_Kiem tra Thong tin Thanh toan ve xem phim");
		log.info("TC_07_15_01: Kiem tra ten phim");
		verifyEquals(filmTicketBooking.getDynamicTextInFilmTicketInfoDetail(FilmTicketBooking_Data.FILM), info.filmName);

		log.info("TC_07_15_02: Kiem tra suat chieu");
		verifyTrue(filmTicketBooking.getDynamicTextInFilmTicketInfoDetail(FilmTicketBooking_Data.TIME_SLOT).contains(info.time));

		log.info("TC_07_15_03: Kiem tra thời lượng");
		verifyEquals(filmTicketBooking.getDynamicTextInFilmTicketInfoDetail(FilmTicketBooking_Data.TIME), info.filmDuration);

		log.info("TC_07_15_04: Kiem tra ten rap");
		info.cinemaAddress = filmTicketBooking.getTextViewByID("com.VCB:id/tvCinemaAddress");
		verifyEquals(filmTicketBooking.getDynamicTextInFilmTicketInfoDetail(FilmTicketBooking_Data.MOVIE_THEATER), info.cinemaName);

		log.info("TC_07_15_05: Kiem tra so tien");
		verifyEquals(filmTicketBooking.getDynamicTextInFilmTicketInfoDetail(FilmTicketBooking_Data.MONEY), info.price);

		log.info("TC_07_16_Nhap thong tin nguoi nhan ve");
		log.info("TC_07_16_01_Nhap ten khach hang");
		filmTicketBooking.inputToDynamicInputBoxByID("Duc Do", "com.VCB:id/etCustomerName");

		log.info("TC_07_16_02_Nhap sdt");
		filmTicketBooking.inputToDynamicInputBoxByID(FilmTicketBooking_Data.PHONE_BOOKING, "com.VCB:id/etPhoneNumber");

		log.info("TC_07_16_03_Nhap email");
		filmTicketBooking.scrollDownToText(FilmTicketBooking_Data.PAY);
		filmTicketBooking.inputToDynamicInputBoxByID(FilmTicketBooking_Data.EMAIL_BOOKING, "com.VCB:id/etEmail");

		log.info("TC_07_17_Click Thanh toan");
		filmTicketBooking.clickToTextViewByText(FilmTicketBooking_Data.PAY);

		log.info("TC_07_18_Chon tai khoan nguon");
		filmTicketBooking.clickToDynamicDropDown(FilmTicketBooking_Data.ACCOUNT_FROM_LABEL);
		sourceAccount = filmTicketBooking.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, "VND");
		account = sourceAccount.account;
		surplus = convertAvailableBalanceCurrentcyOrFeeToLong(filmTicketBooking.getDynamicTextInTransactionDetail(FilmTicketBooking_Data.AVAILABLE_BALANCES));

		log.info("TC_07_19_Kiem tra man hinh Thong tin mua ve");
		log.info("TC_07_19_01: Kiem tra ten phim");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(FilmTicketBooking_Data.FILM), info.filmName);

		log.info("TC_07_19_02: Kiem tra suat chieu");
		verifyTrue(filmTicketBooking.getDynamicTextInTransactionDetail(FilmTicketBooking_Data.TIME_SLOT).contains(info.time));

		log.info("TC_07_19_03: Kiem tra thời lượng");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(FilmTicketBooking_Data.TIME), info.filmDuration);

		log.info("TC_07_19_04: Kiem tra ten rap");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(FilmTicketBooking_Data.MOVIE_THEATER), info.cinemaName);

		log.info("TC_07_19_05: Kiem tra dia chi rap");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(FilmTicketBooking_Data.ADDRESS), info.cinemaAddress);

		log.info("TC_07_19_06: Kiem tra so tien");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(FilmTicketBooking_Data.MONEY), info.price);

		log.info("TC_07_19_07: Kiem tra ten khach hang");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(FilmTicketBooking_Data.NAME_CUSTOMER), "Duc Do");

		log.info("TC_07_19_08: Kiem tra sdt");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(FilmTicketBooking_Data.PHONE_TAKE_TICKET), FilmTicketBooking_Data.PHONE_BOOKING);

		log.info("TC_07_19_09: Kiem tra email");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(FilmTicketBooking_Data.EMAIL_TAKE_TICKET), FilmTicketBooking_Data.EMAIL_BOOKING);

		log.info("TC_07_20: Click Tiep tuc");
		filmTicketBooking.clickToDynamicButton(FilmTicketBooking_Data.NEXT);

		log.info("TC_07_21: lay phi");
		fee = convertAvailableBalanceCurrentcyOrFeeToLong(filmTicketBooking.getDynamicTextInTransactionDetail(FilmTicketBooking_Data.FEE));
		
		log.info("TC_07_21_01: Kiem tra tai khoan nguon");
		filmTicketBooking.scrollUpToText(FilmTicketBooking_Data.ACCOUNT_FROM_LABEL);
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(FilmTicketBooking_Data.ACCOUNT_FROM_LABEL), account);

		log.info("TC_07_21_02: Kiem tra so tien");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(FilmTicketBooking_Data.MONEY), info.price);

		log.info("TC_07_22_Chon phuong thuc xac thuc");
		filmTicketBooking.clickToDynamicTextViewByID("com.VCB:id/tvptxt");
		filmTicketBooking.clickToDynamicTextView(FilmTicketBooking_Data.SMART_OTP);

		log.info("TC_07_22_01: Kiem tra so tien phi");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(FilmTicketBooking_Data.FEE), addCommasToLong(fee + "") + " VND");

		log.info("TC_07_23: Click Tiep tuc");
		filmTicketBooking.clickToDynamicButton(FilmTicketBooking_Data.NEXT);

		filmTicketBooking.inputToDynamicSmartOTP(driver, passSmartOTP, "com.VCB:id/otp");
		filmTicketBooking.clickToDynamicAcceptButton(driver, "com.VCB:id/submit");

		filmTicketBooking.clickToDynamicButton(FilmTicketBooking_Data.NEXT);

		log.info("TC_07_24: Kiem tra man hinh thanh toan thanh cong");
		verifyTrue(filmTicketBooking.isDynamicMessageAndLabelTextDisplayed(FilmTicketBooking_Data.MESSEGE_SUCCESS));
		transferTime = filmTicketBooking.getTransferTimeSuccess(FilmTicketBooking_Data.MESSEGE_SUCCESS);
		transactionNumber = filmTicketBooking.getDynamicTextInTransactionDetail(FilmTicketBooking_Data.CODE_TRANSFER);
		ticketCode = filmTicketBooking.getDynamicTextInTransactionDetail(ReportTitle.CODE_TICKET);

		log.info("TC_07_25_Kiem tra nut Thuc hien giao dich moi");
		verifyTrue(filmTicketBooking.isDynamicButtonDisplayed(FilmTicketBooking_Data.NEW_TRANSFER));

		log.info("TC_07_26_Click Thuc hien giao dich moi");
		filmTicketBooking.clickToDynamicButton(FilmTicketBooking_Data.NEW_TRANSFER);

		log.info("TC_07_27_Tinh so du kha dung cua tai khoan sau khi thanh toan thanh cong");
		availableBalance = canculateAvailableBalances(surplus, convertAvailableBalanceCurrentcyOrFeeToLong(info.price), fee);

	}

	@Test
	public void TC_08_DatVeXemPhim_Rap_MegaGS_CineStar_BaoCaoGiaoDich_SmartOtp() {
		log.info("TC_08_1: Click  nut Back");
		filmTicketBooking.clickToDynamicBackIcon(FilmTicketBooking_Data.BUY_TICKET_FILM);

		log.info("TC_08_2: Click vao More Icon");
		filmTicketBooking.clickToDynamicImageViewByID("com.VCB:id/menu_5");

		log.info("TC_08_3: Click Bao Cao giao Dich");
		filmTicketBooking.clickToDynamicTextView(ReportTitle.TRANSACTION_REPORT);

		log.info("TC_08_4: Click Tat Ca Cac Loai Giao Dich");
		filmTicketBooking.clickToDynamicTextView(ReportTitle.ALL_TYPE_TRANSACTION);

		log.info("TC_08_5: Chon Chuyen Tien Trong VCB");
		filmTicketBooking.clickToDynamicTextView(ReportTitle.PAY_FILM_TICKET);

		log.info("TC_08_6: Click Chon Tai Khoan");
		filmTicketBooking.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvSelectAcc");

		log.info("TC_08_7: Lay so du kha dung luc sau");
		actualAvailableBalance = convertAvailableBalanceCurrentcyOrFeeToLong(filmTicketBooking.getDynamicTextInTransactionDetail(account));

		log.info("TC_08_8: Kiem tra so du kha dung sau khi thanh toan thanh cong");
		verifyEquals(actualAvailableBalance, availableBalance);

		log.info("TC_08_9: Chon tai Khoan chuyen");
		filmTicketBooking.clickToDynamicButtonLinkOrLinkText(driver, account);

		log.info("TC_08_10: Click Tim Kiem");
		filmTicketBooking.clickToDynamicButton(ReportTitle.SEARCH_BUTTON);

		log.info("TC_08_11: Kiem tra ngay tao giao dich hien thi");
		String reportTime1 = filmTicketBooking.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
		verifyEquals(convertDateTimeIgnoreHHmmss(reportTime1), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_08_12: Kiem tra so tien chuyen hien thi");
		verifyEquals(filmTicketBooking.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + info.price));

		log.info("TC_08_13: Click vao giao dich");
		filmTicketBooking.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_08_14: Kiem tra thoi gian tao giao dich hien thi");
		String reportTime2 = filmTicketBooking.getDynamicTextInTransactionDetail(ReportTitle.TIME_TRANSFER);
		verifyEquals(reportTime2, reportTime1);

		log.info("TC_08_15: Kiem tra ma giao dich");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(ReportTitle.TRANSACTION_NUMBER), transactionNumber);

		log.info("TC_08_16: Kiem tra so tai khoan trich no");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(ReportTitle.ACCOUNT_CARD), account);

		log.info("TC_08_17: Kiem tra ma ve");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(ReportTitle.CODE_TICKET), ticketCode);

		log.info("TC_08_18: Kiem tra so tien giao dich hien thi");
		verifyTrue(filmTicketBooking.getDynamicTextInTransactionDetail(ReportTitle.TRANSACTION_MONEY).contains(info.price));

		log.info("TC_08_19: Kiem tra so tien phi");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(FilmTicketBooking_Data.FEE), addCommasToLong(fee + "") + " VND");

		log.info("TC_08_20: Kiem tra loai giao dich");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(ReportTitle.TRANSACTION_TYPE), ReportTitle.PAY_FILM_TICKET);

		log.info("------------------------------------TC_08_21: Kiem tra noi dung giao dich------------------------------------");
		String note = "MBVCB." + transactionNumber + ". thanh toan ve xem phim";
		verifyTrue(filmTicketBooking.getDynamicTextInTransactionDetail(ReportTitle.TRANSACTION_CONTENT).contains(note));

		log.info("TC_08_22: Click  nut Back");
		filmTicketBooking.clickToDynamicBackIcon("Chi tiết giao dịch");

		log.info("TC_08_23: Click  nut Back");
		filmTicketBooking.clickToDynamicBackIcon(ReportTitle.TRANSACTION_REPORT);

		log.info("TC_08_24: Click  nut Home");
		filmTicketBooking.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");

	}

	@Test
	public void TC_09_DatVeXemPhim_Rap_CineStar_SmartOTp() {
		log.info("TC_09_01_Click Dat ve xem phim");
		filmTicketBooking.clickToDynamicTextOrButtonLink(FilmTicketBooking_Data.FILM_TITLE);

		log.info("TC_09_02_Click nut Dong y");
		filmTicketBooking.clickToDynamicButton(FilmTicketBooking_Data.AGREE);

		log.info("TC_09_03_Click chon Tinh thanh");
		filmTicketBooking.clickToDynamicTextViewByID("com.VCB:id/tvLocationName");

		log.info("TC_09_04_Tim kiem thanh pho");
		filmTicketBooking.inputIntoEditTextByID(FilmTicketBooking_Data.CITY, "com.VCB:id/edtSearch");

		log.info("TC_09_05_Click chon thanh pho");
		filmTicketBooking.clickToDynamicTextView(FilmTicketBooking_Data.CITY);

		log.info("TC_09_06_Click chon cum rap Cinestar");
		filmTicketBooking.clickToDynamicTextView("Cinestar");

		log.info("TC_09_07_Click chon rap phim");
		List<String> listCinema = filmTicketBooking.getListOfSuggestedMoneyOrListText("com.VCB:id/tvNameCinema");
		String cinemaName = listCinema.get(0);
		filmTicketBooking.clickToDynamicTextView(cinemaName);

		log.info("TC_09_08_Click xem chi tiet phim");
		FilmInfo filmInfo = filmTicketBooking.getInfoOfTheFirstFilm();
		filmTicketBooking.clickToDynamicTextView(filmInfo.filmName);
		info.filmName = filmInfo.filmName;
		info.filmDuration = filmTicketBooking.canculateDurationOfFilm(filmInfo.filmDuration);

		log.info("TC_09_09_Nhan nut Dat ve");
		filmTicketBooking.clickToTextViewByText(FilmTicketBooking_Data.BOOKING_TICKET);

		log.info("TC_09_10_Nhan chon gio chieu");
		info.time = filmTicketBooking.getDynamicTextViewByViewGroupID("com.VCB:id/tagShowtimes2D", "0");
		filmTicketBooking.clickToDynamicTextViewByViewGroupID("com.VCB:id/tagShowtimes2D", "0");

		log.info("TC_09_11_Lay mau cua loai ghe Thuong");
		String colorOfSeat = filmTicketBooking.getColorOfElement(FilmTicketBookingPageUIs.VIEW_BY_TEXT, "Standard");

		log.info("TC_09_12_Click chon cho ngoi loai ghe Thuong");
		filmTicketBooking.chooseSeats(1, colorOfSeat);

		log.info("TC_09_13_Lay ten rap va gia ve");
		info.cinemaName = filmTicketBooking.getTextViewByID("com.VCB:id/tvTitle");
		info.price = filmTicketBooking.getTextViewByID("com.VCB:id/tvPrince");

		log.info("TC_09_14_Click Thanh toan");
		filmTicketBooking.clickToTextViewByText(FilmTicketBooking_Data.PAY);

		log.info("TC_09_15_Kiem tra Thong tin Thanh toan ve xem phim");
		log.info("TC_09_15_01: Kiem tra ten phim");
		verifyEquals(filmTicketBooking.getDynamicTextInFilmTicketInfoDetail(FilmTicketBooking_Data.FILM), info.filmName);

		log.info("TC_09_15_02: Kiem tra suat chieu");
		verifyTrue(filmTicketBooking.getDynamicTextInFilmTicketInfoDetail(FilmTicketBooking_Data.TIME_SLOT).contains(info.time));

		log.info("TC_09_15_03: Kiem tra thời lượng");
		verifyEquals(filmTicketBooking.getDynamicTextInFilmTicketInfoDetail(FilmTicketBooking_Data.TIME), info.filmDuration);

		log.info("TC_09_15_04: Kiem tra ten rap");
		info.cinemaAddress = filmTicketBooking.getTextViewByID("com.VCB:id/tvCinemaAddress");
		verifyEquals(filmTicketBooking.getDynamicTextInFilmTicketInfoDetail(FilmTicketBooking_Data.MOVIE_THEATER), info.cinemaName);

		log.info("TC_09_15_05: Kiem tra so tien");
		verifyEquals(filmTicketBooking.getDynamicTextInFilmTicketInfoDetail(FilmTicketBooking_Data.MONEY), info.price);

		log.info("TC_09_16_Nhap thong tin nguoi nhan ve");
		log.info("TC_09_16_01_Nhap ten khach hang");
		filmTicketBooking.inputToDynamicInputBoxByID("Duc Do", "com.VCB:id/etCustomerName");

		log.info("TC_09_16_02_Nhap sdt");
		filmTicketBooking.inputToDynamicInputBoxByID(FilmTicketBooking_Data.PHONE_BOOKING, "com.VCB:id/etPhoneNumber");

		log.info("TC_09_16_03_Nhap email");
		filmTicketBooking.scrollDownToText(FilmTicketBooking_Data.PAY);
		filmTicketBooking.inputToDynamicInputBoxByID(FilmTicketBooking_Data.EMAIL_BOOKING, "com.VCB:id/etEmail");

		log.info("TC_09_17_Click Thanh toan");
		filmTicketBooking.clickToTextViewByText(FilmTicketBooking_Data.PAY);

		log.info("TC_09_18_Chon tai khoan nguon");
		filmTicketBooking.clickToDynamicDropDown(FilmTicketBooking_Data.ACCOUNT_FROM_LABEL);
		sourceAccount = filmTicketBooking.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, "VND");
		account = sourceAccount.account;
		surplus = convertAvailableBalanceCurrentcyOrFeeToLong(filmTicketBooking.getDynamicTextInTransactionDetail(FilmTicketBooking_Data.AVAILABLE_BALANCES));

		log.info("TC_09_19_Kiem tra man hinh Thong tin mua ve");
		log.info("TC_09_19_01: Kiem tra ten phim");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(FilmTicketBooking_Data.FILM), info.filmName);

		log.info("TC_09_19_02: Kiem tra suat chieu");
		verifyTrue(filmTicketBooking.getDynamicTextInTransactionDetail(FilmTicketBooking_Data.TIME_SLOT).contains(info.time));

		log.info("TC_09_19_03: Kiem tra thời lượng");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(FilmTicketBooking_Data.TIME), info.filmDuration);

		log.info("TC_09_19_04: Kiem tra ten rap");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(FilmTicketBooking_Data.MOVIE_THEATER), info.cinemaName);

		log.info("TC_09_19_05: Kiem tra dia chi rap");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(FilmTicketBooking_Data.ADDRESS), info.cinemaAddress);

		log.info("TC_09_19_06: Kiem tra so tien");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(FilmTicketBooking_Data.MONEY), info.price);

		log.info("TC_09_19_07: Kiem tra ten khach hang");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(FilmTicketBooking_Data.NAME_CUSTOMER), "Duc Do");

		log.info("TC_09_19_08: Kiem tra sdt");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(FilmTicketBooking_Data.PHONE_TAKE_TICKET), FilmTicketBooking_Data.PHONE_BOOKING);

		log.info("TC_09_19_09: Kiem tra email");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(FilmTicketBooking_Data.EMAIL_TAKE_TICKET), FilmTicketBooking_Data.EMAIL_BOOKING);

		log.info("TC_09_20: Click Tiep tuc");
		filmTicketBooking.clickToDynamicButton(FilmTicketBooking_Data.NEXT);

		log.info("TC_09_21: Kiem tra man hinh xac nhan thong tin");
		log.info("TC_09_21_01: Kiem tra tai khoan nguon");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(FilmTicketBooking_Data.ACCOUNT_FROM_LABEL), account);

		log.info("TC_09_21_02: Kiem tra so tien");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(FilmTicketBooking_Data.MONEY), info.price);

		log.info("TC_09_22_Chon phuong thuc xac thuc");
		filmTicketBooking.clickToDynamicTextView(FilmTicketBooking_Data.PASSWORD);
		fee = convertAvailableBalanceCurrentcyOrFeeToLong(filmTicketBooking.getDynamicTextInTransactionDetail(FilmTicketBooking_Data.FEE));
		filmTicketBooking.clickToDynamicTextView(FilmTicketBooking_Data.SMART_OTP);

		log.info("TC_09_22_01: Kiem tra so tien phi");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(FilmTicketBooking_Data.FEE), addCommasToLong(fee + "") + " VND");

		log.info("TC_09_23: Click Tiep tuc");
		filmTicketBooking.clickToDynamicButton(FilmTicketBooking_Data.NEXT);

		filmTicketBooking.inputToDynamicSmartOTP(driver, passSmartOTP, "com.VCB:id/otp");
		filmTicketBooking.clickToDynamicAcceptButton(driver, "com.VCB:id/submit");

		filmTicketBooking.clickToDynamicButton(FilmTicketBooking_Data.NEXT);

		log.info("TC_09_24: Kiem tra man hinh thanh toan thanh cong");
		verifyTrue(filmTicketBooking.isDynamicMessageAndLabelTextDisplayed(FilmTicketBooking_Data.MESSEGE_SUCCESS));
		transferTime = filmTicketBooking.getTransferTimeSuccess(FilmTicketBooking_Data.MESSEGE_SUCCESS);
		transactionNumber = filmTicketBooking.getDynamicTextInTransactionDetail(FilmTicketBooking_Data.CODE_TRANSFER);
		ticketCode = filmTicketBooking.getDynamicTextInTransactionDetail(ReportTitle.CODE_TICKET);

		log.info("TC_09_25_Kiem tra nut Thuc hien giao dich moi");
		verifyTrue(filmTicketBooking.isDynamicButtonDisplayed(FilmTicketBooking_Data.NEW_TRANSFER));

		log.info("TC_09_26_Click Thuc hien giao dich moi");
		filmTicketBooking.clickToDynamicButton(FilmTicketBooking_Data.NEW_TRANSFER);

		log.info("TC_09_27_Tinh so du kha dung cua tai khoan sau khi thanh toan thanh cong");
		availableBalance = canculateAvailableBalances(surplus, convertAvailableBalanceCurrentcyOrFeeToLong(info.price), fee);

	}

	@Test
	public void TC_10_DatVeXemPhim_Rap_CineStar_BaoCaoGiaoDich_SmartOTp() {
		log.info("TC_10_1: Click  nut Back");
		filmTicketBooking.clickToDynamicBackIcon(FilmTicketBooking_Data.BUY_TICKET_FILM);

		log.info("TC_10_2: Click vao More Icon");
		filmTicketBooking.clickToDynamicImageViewByID("com.VCB:id/menu_5");

		log.info("TC_10_3: Click Bao Cao giao Dich");
		filmTicketBooking.clickToDynamicTextView(ReportTitle.TRANSACTION_REPORT);

		log.info("TC_10_4: Click Tat Ca Cac Loai Giao Dich");
		filmTicketBooking.clickToDynamicTextView(ReportTitle.ALL_TYPE_TRANSACTION);

		log.info("TC_10_5: Chon Chuyen Tien Trong VCB");
		filmTicketBooking.clickToDynamicTextView(ReportTitle.PAY_FILM_TICKET);

		log.info("TC_10_6: Click Chon Tai Khoan");
		filmTicketBooking.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvSelectAcc");

		log.info("TC_10_7: Lay so du kha dung luc sau");
		actualAvailableBalance = convertAvailableBalanceCurrentcyOrFeeToLong(filmTicketBooking.getDynamicTextInTransactionDetail(account));

		log.info("TC_10_8: Kiem tra so du kha dung sau khi thanh toan thanh cong");
		verifyEquals(actualAvailableBalance, availableBalance);

		log.info("TC_10_9: Chon tai Khoan chuyen");
		filmTicketBooking.clickToDynamicButtonLinkOrLinkText(driver, account);

		log.info("TC_10_10: Click Tim Kiem");
		filmTicketBooking.clickToDynamicButton(ReportTitle.SEARCH_BUTTON);

		log.info("TC_10_11: Kiem tra ngay tao giao dich hien thi");
		String reportTime1 = filmTicketBooking.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
		verifyEquals(convertDateTimeIgnoreHHmmss(reportTime1), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_10_12: Kiem tra so tien chuyen hien thi");
		verifyEquals(filmTicketBooking.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + info.price));

		log.info("TC_10_13: Click vao giao dich");
		filmTicketBooking.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_10_14: Kiem tra thoi gian tao giao dich hien thi");
		String reportTime2 = filmTicketBooking.getDynamicTextInTransactionDetail(ReportTitle.TIME_TRANSFER);
		verifyEquals(reportTime2, reportTime1);

		log.info("TC_10_15: Kiem tra ma giao dich");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(ReportTitle.TRANSACTION_NUMBER), transactionNumber);

		log.info("TC_10_16: Kiem tra so tai khoan trich no");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(ReportTitle.ACCOUNT_CARD), account);

		log.info("TC_10_17: Kiem tra ma ve");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(ReportTitle.CODE_TICKET), ticketCode);

		log.info("TC_10_18: Kiem tra so tien giao dich hien thi");
		verifyTrue(filmTicketBooking.getDynamicTextInTransactionDetail(ReportTitle.TRANSACTION_MONEY).contains(info.price));

		log.info("TC_10_19: Kiem tra so tien phi");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(FilmTicketBooking_Data.FEE), addCommasToLong(fee + "") + " VND");

		log.info("TC_10_20: Kiem tra loai giao dich");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail(ReportTitle.TRANSACTION_TYPE), ReportTitle.PAY_FILM_TICKET);

		log.info("TC_10_21: Kiem tra noi dung giao dich");
		String note = "MBVCB." + transactionNumber + ". thanh toan ve xem phim";
		verifyTrue(filmTicketBooking.getDynamicTextInTransactionDetail(ReportTitle.TRANSACTION_CONTENT).contains(note));

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
