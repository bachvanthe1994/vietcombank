package vnpay.vietcombank.sdk.filmTicketBooking;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.Constants;
import commons.PageFactoryManager;
import commons.WebPageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import model.FilmInfo;
import model.SeatType;
import model.ServiceLimitInfo;
import model.SourceAccountModel;
import pageObjects.LogInPageObject;
import pageObjects.WebBackendSetupPageObject;
import pageObjects.sdk.filmTicketBooking.FilmTicketBookingPageObject;
import vietcombankUI.sdk.filmTicketBooking.FilmTicketBookingPageUIs;
import vietcombank_test_data.TransactionReport_Data.ReportTitle;
import vnpay.vietcombank.sdk.filmTicketBooking.data.FilmTicketBooking_Data;

public class Limit_FilmTicketBooking extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private FilmTicketBookingPageObject filmTicketBooking;
	private WebBackendSetupPageObject webBackend;
	SourceAccountModel sourceAccount = new SourceAccountModel();
	String account = "";
	
	WebDriver driverWeb;
	long amount = 0;
	String name;
	ServiceLimitInfo inputInfo = new ServiceLimitInfo("1000", "10000", "5000000", "5500000");

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp", "username", "passWeb" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt, String username, String passWeb) throws IOException, InterruptedException, GeneralSecurityException {
		log.info("Before class: Mo backend ");
		startServer();
		driverWeb = openMultiBrowser(Constants.BE_BROWSER_CHROME, Constants.BE_BROWSER_VERSION, Constants.BE_URL);
		webBackend = WebPageFactoryManager.getWebBackendSetupPageObject(driverWeb);
		webBackend.Login_Web_Backend(driverWeb, username, passWeb);

		webBackend.addMethod(driverWeb, "Thanh toán vé xem phim", inputInfo, "TESTBUG");

		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
		filmTicketBooking = PageFactoryManager.getFilmTicketBookingPageObject(driver);
		name = getDataInCell(1);
	}

	@Test
	public void TC_01_ThanhToanVeXeNhoHonHanMucToiThieu() throws InterruptedException {

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

		log.info("TC_01_09_Nhan nut Dat ve");
		filmTicketBooking.clickToTextViewByText(FilmTicketBooking_Data.BOOKING_TICKET);

		log.info("TC_01_10_Nhan chon gio chieu");
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
		
		amount = convertAvailableBalanceCurrentcyOrFeeToLong(filmTicketBooking.getTextViewByID("com.VCB:id/tvPrince"));
		ServiceLimitInfo inputInfoMin = new ServiceLimitInfo("1000", (amount + 20) + "", (amount + 100) + "", "10000000");

		webBackend.getInfoServiceLimit(driverWeb, "Thanh toán vé xem phim", inputInfoMin, "TESTBUG");
	
		log.info("TC_01_14_Click Thanh toan");
		filmTicketBooking.clickToTextViewByText(FilmTicketBooking_Data.PAY);
		

		log.info("TC_01_15_Kiem tra Thong tin Thanh toan ve xem phim");
		log.info("TC_01_15_01: Kiem tra ten phim");
		filmTicketBooking.scrollUpToText(FilmTicketBooking_Data.FILM);

		log.info("TC_01_16_Nhap thong tin nguoi nhan ve");
		log.info("TC_01_16_01_Nhap ten khach hang");
		filmTicketBooking.inputToDynamicInputBoxByID(name, "com.VCB:id/etCustomerName");

		log.info("TC_01_16_02_Nhap sdt");
		filmTicketBooking.inputToDynamicInputBoxByID(FilmTicketBooking_Data.PHONE_BOOKING, "com.VCB:id/etPhoneNumber");

		log.info("TC_01_16_03_Nhap email");
		filmTicketBooking.scrollDownToText(FilmTicketBooking_Data.PAY);
		filmTicketBooking.inputToDynamicInputBoxByID(FilmTicketBooking_Data.EMAIL_BOOKING, "com.VCB:id/etEmail");

		log.info("TC_01_17_Click Thanh toan");
		filmTicketBooking.clickToTextViewByText(FilmTicketBooking_Data.PAY);
		
		log.info("TC_01_18 Lay thong tin tai khoan nguon");
		filmTicketBooking.scrollUpToText(ReportTitle.SOURCE_ACCOUNT);
		filmTicketBooking.clickToDynamicTextByID("com.VCB:id/number_account");
		sourceAccount = filmTicketBooking.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, "VND");
		account = sourceAccount.account;
		
		filmTicketBooking.clickToDynamicContinue( "com.VCB:id/btn_submit");

		log.info("TC_01_Step_verify message khi so tien chuyen nho hon han muc toi thieu ");
		verifyEquals(filmTicketBooking.getDynamicTextView(driver, "com.VCB:id/tvContent"), "Giao dịch không thành công. Số tiền giao dịch nhỏ hơn hạn mức " + addCommasToLong((amount + 20) + "") + " VND/1 lần, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900 545413 để được trợ giúp.");

		filmTicketBooking.clickToDynamicContinue("com.VCB:id/btOK");

		webBackend.resetAssignServicesLimit_All(driverWeb, "Thanh toán vé xem phim", "TESTBUG");

	}

	@Test
	public void TC_02_ThanhToanVeXeLonHonHanMucToiDa() throws InterruptedException {

		filmTicketBooking.clickToDynamicImageViewByID("com.VCB:id/ivTitleLeft");
		filmTicketBooking.clickToDynamicButton("Quay lại");

	
		log.info("TC_01_08_Click xem chi tiet phim");
		FilmInfo filmInfo = filmTicketBooking.getInfoOfTheFirstFilm();
		filmTicketBooking.clickToDynamicTextView(filmInfo.filmName);

		log.info("TC_01_09_Nhan nut Dat ve");
		filmTicketBooking.clickToTextViewByText(FilmTicketBooking_Data.BOOKING_TICKET);

		log.info("TC_01_10_Nhan chon gio chieu");
		filmTicketBooking.clickToDynamicTextViewByViewGroupID("com.VCB:id/tagShowtimes2D", "0");

		log.info("TC_01_11_Chon 1 ghe");
		filmTicketBooking.clickToDynamicTextViewByID("com.VCB:id/tvPlus");

		List<SeatType> listSeatType = filmTicketBooking.getListSeatType();
		String type = filmTicketBooking.getTypeOfSeat(listSeatType);

		log.info("TC_02_12_Click chon cho ngoi");
		filmTicketBooking.clickToTextViewByText("Chọn chỗ ngồi");

		log.info("TC_02_13_Chon cho ngoi nhu da dang ky");
		String colorOfSeat = filmTicketBooking.getColorOfElement(FilmTicketBookingPageUIs.VIEW_BY_TEXT, type);
		filmTicketBooking.chooseSeats(1, colorOfSeat);
		
		amount = convertAvailableBalanceCurrentcyOrFeeToLong(filmTicketBooking.getTextViewByID("com.VCB:id/tvPrince"));
		
		
		ServiceLimitInfo inputInfoMax = new ServiceLimitInfo("1000", (amount - 20) + "", (amount - 10) + "", "10000000");

		webBackend.getInfoServiceLimit(driverWeb, "Thanh toán vé xem phim", inputInfoMax, "TESTBUG");
		
		log.info("TC_02_14_Click Thanh toan");
		filmTicketBooking.clickToTextViewByText(FilmTicketBooking_Data.PAY);

		log.info("TC_02_15_Kiem tra Thong tin Thanh toan ve xem phim");
		log.info("TC_02_15_01: Kiem tra ten phim");
		filmTicketBooking.scrollUpToText(FilmTicketBooking_Data.FILM);

		log.info("TC_02_16_Nhap thong tin nguoi nhan ve");
		log.info("TC_02_16_01_Nhap ten khach hang");
		filmTicketBooking.inputToDynamicInputBoxByID(name, "com.VCB:id/etCustomerName");

		log.info("TC_02_16_02_Nhap sdt");
		filmTicketBooking.inputToDynamicInputBoxByID(FilmTicketBooking_Data.PHONE_BOOKING, "com.VCB:id/etPhoneNumber");

		log.info("TC_02_16_03_Nhap email");
		filmTicketBooking.scrollDownToText(FilmTicketBooking_Data.PAY);
		filmTicketBooking.inputToDynamicInputBoxByID(FilmTicketBooking_Data.EMAIL_BOOKING, "com.VCB:id/etEmail");

		log.info("TC_02_17_Click Thanh toan");
		filmTicketBooking.clickToTextViewByText(FilmTicketBooking_Data.PAY);
		
		log.info("TC_02_18 Lay thong tin tai khoan nguon");
		filmTicketBooking.scrollUpToText(ReportTitle.SOURCE_ACCOUNT);
		filmTicketBooking.clickToDynamicTextByID("com.VCB:id/number_account");
		sourceAccount = filmTicketBooking.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, "VND");
		account = sourceAccount.account;
		
		filmTicketBooking.clickToDynamicContinue( "com.VCB:id/btn_submit");
		

		log.info("TC_02_Step_verify message khi so tien chuyen nho hon han muc toi thieu ");
		verifyEquals(filmTicketBooking.getDynamicTextView(driver, "com.VCB:id/tvContent"), "Giao dịch không thành công. Số tiền giao dịch lớn hơn hạn mức " + addCommasToLong((amount - 10) + "") + " VND/1 lần, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900 545413 để được trợ giúp.");

		filmTicketBooking.clickToDynamicContinue("com.VCB:id/btOK");

		webBackend.resetAssignServicesLimit_All(driverWeb, "Thanh toán vé xem phim", "TESTBUG");

	}

	@Test
	public void TC_03_ThanhToanVeXeNhoHonHanMucDaNhom() throws InterruptedException {
		filmTicketBooking.clickToDynamicImageViewByID("com.VCB:id/ivTitleLeft");
		filmTicketBooking.clickToDynamicButton("Quay lại");

		log.info("TC_03_08_Click xem chi tiet phim");
		FilmInfo filmInfo = filmTicketBooking.getInfoOfTheFirstFilm();
		filmTicketBooking.clickToDynamicTextView(filmInfo.filmName);

		log.info("TC_03_09_Nhan nut Dat ve");
		filmTicketBooking.clickToTextViewByText(FilmTicketBooking_Data.BOOKING_TICKET);

		log.info("TC_03_10_Nhan chon gio chieu");
		filmTicketBooking.clickToDynamicTextViewByViewGroupID("com.VCB:id/tagShowtimes2D", "0");

		log.info("TC_03_11_Chon 1 ghe");
		filmTicketBooking.clickToDynamicTextViewByID("com.VCB:id/tvPlus");

		List<SeatType> listSeatType = filmTicketBooking.getListSeatType();
		String type = filmTicketBooking.getTypeOfSeat(listSeatType);

		log.info("TC_03_12_Click chon cho ngoi");
		filmTicketBooking.clickToTextViewByText("Chọn chỗ ngồi");

		log.info("TC_03_13_Chon cho ngoi nhu da dang ky");
		String colorOfSeat = filmTicketBooking.getColorOfElement(FilmTicketBookingPageUIs.VIEW_BY_TEXT, type);
		filmTicketBooking.chooseSeats(1, colorOfSeat);
		
		amount = convertAvailableBalanceCurrentcyOrFeeToLong(filmTicketBooking.getTextViewByID("com.VCB:id/tvPrince"));

		webBackend.Setup_Assign_Services_Type_Limit(driverWeb, "TESTBUG", "Thanh toán hóa đơn", (amount - 10000) + "");
	
		log.info("TC_03_14_Click Thanh toan");
		filmTicketBooking.clickToTextViewByText(FilmTicketBooking_Data.PAY);
		

		log.info("TC_03_15_Kiem tra Thong tin Thanh toan ve xem phim");
		log.info("TC_03_15_01: Kiem tra ten phim");
		filmTicketBooking.scrollUpToText(FilmTicketBooking_Data.FILM);

		log.info("TC_03_16_Nhap thong tin nguoi nhan ve");
		log.info("TC_03_16_02_Nhap ten khach hang");
		filmTicketBooking.inputToDynamicInputBoxByID(name, "com.VCB:id/etCustomerName");

		log.info("TC_03_16_02_Nhap sdt");
		filmTicketBooking.inputToDynamicInputBoxByID(FilmTicketBooking_Data.PHONE_BOOKING, "com.VCB:id/etPhoneNumber");

		log.info("TC_01_16_03_Nhap email");
		filmTicketBooking.scrollDownToText(FilmTicketBooking_Data.PAY);
		filmTicketBooking.inputToDynamicInputBoxByID(FilmTicketBooking_Data.EMAIL_BOOKING, "com.VCB:id/etEmail");

		log.info("TC_03_17_Click Thanh toan");
		filmTicketBooking.clickToTextViewByText(FilmTicketBooking_Data.PAY);
		
		log.info("TC_03_18 Lay thong tin tai khoan nguon");
		filmTicketBooking.scrollUpToText(ReportTitle.SOURCE_ACCOUNT);
		filmTicketBooking.clickToDynamicTextByID("com.VCB:id/number_account");
		sourceAccount = filmTicketBooking.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, "VND");
		account = sourceAccount.account;
		
		filmTicketBooking.clickToDynamicContinue( "com.VCB:id/btn_submit");

		log.info("TC_03_Step_verify message khi so tien chuyen nho hon han muc toi thieu ");
		verifyEquals(filmTicketBooking.getDynamicTextView(driver, "com.VCB:id/tvContent"), "Thanh toán không thành công. Số tiền giao dịch nhỏ hơn hạn mức " + addCommasToLong((amount - 10000) + "") + " VND/1 lần, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp");

		filmTicketBooking.clickToDynamicContinue("com.VCB:id/btOK");

		webBackend.Reset_Setup_Assign_Services_Type_Limit(driverWeb, "TESTBUG", "Thanh toán hóa đơn");

	}

	@Test
	public void TC_04_ThanhToanVeXeNhoHonHanMucToiDaGoi() throws InterruptedException {
		filmTicketBooking.clickToDynamicImageViewByID("com.VCB:id/ivTitleLeft");
		filmTicketBooking.clickToDynamicButton("Quay lại");


		log.info("TC_04_08_Click xem chi tiet phim");
		FilmInfo filmInfo = filmTicketBooking.getInfoOfTheFirstFilm();
		filmTicketBooking.clickToDynamicTextView(filmInfo.filmName);

		log.info("TC_04_09_Nhan nut Dat ve");
		filmTicketBooking.clickToTextViewByText(FilmTicketBooking_Data.BOOKING_TICKET);

		log.info("TC_04_10_Nhan chon gio chieu");
		filmTicketBooking.clickToDynamicTextViewByViewGroupID("com.VCB:id/tagShowtimes2D", "0");

		log.info("TC_04_11_Chon 1 ghe");
		filmTicketBooking.clickToDynamicTextViewByID("com.VCB:id/tvPlus");

		List<SeatType> listSeatType = filmTicketBooking.getListSeatType();
		String type = filmTicketBooking.getTypeOfSeat(listSeatType);

		log.info("TC_04_12_Click chon cho ngoi");
		filmTicketBooking.clickToTextViewByText("Chọn chỗ ngồi");

		log.info("TC_04_13_Chon cho ngoi nhu da dang ky");
		String colorOfSeat = filmTicketBooking.getColorOfElement(FilmTicketBookingPageUIs.VIEW_BY_TEXT, type);
		filmTicketBooking.chooseSeats(1, colorOfSeat);
		
		amount = convertAvailableBalanceCurrentcyOrFeeToLong(filmTicketBooking.getTextViewByID("com.VCB:id/tvPrince"));

		webBackend.Setup_Add_Method_Package_Total_Limit(driverWeb, "TESTBUG", "Method Otp", (amount - 1000) + "");
	
		log.info("TC_04_14_Click Thanh toan");
		filmTicketBooking.clickToTextViewByText(FilmTicketBooking_Data.PAY);
		

		log.info("TC_04_15_Kiem tra Thong tin Thanh toan ve xem phim");
		log.info("TC_04_15_01: Kiem tra ten phim");
		filmTicketBooking.scrollUpToText(FilmTicketBooking_Data.FILM);

		log.info("TC_04_16_Nhap thong tin nguoi nhan ve");
		log.info("TC_04_16_01_Nhap ten khach hang");
		filmTicketBooking.inputToDynamicInputBoxByID(name, "com.VCB:id/etCustomerName");

		log.info("TC_04_16_02_Nhap sdt");
		filmTicketBooking.inputToDynamicInputBoxByID(FilmTicketBooking_Data.PHONE_BOOKING, "com.VCB:id/etPhoneNumber");

		log.info("TC_04_16_03_Nhap email");
		filmTicketBooking.scrollDownToText(FilmTicketBooking_Data.PAY);
		filmTicketBooking.inputToDynamicInputBoxByID(FilmTicketBooking_Data.EMAIL_BOOKING, "com.VCB:id/etEmail");

		log.info("TC_04_17_Click Thanh toan");
		filmTicketBooking.clickToTextViewByText(FilmTicketBooking_Data.PAY);
		
		log.info("TC_04_18 Lay thong tin tai khoan nguon");
		filmTicketBooking.scrollUpToText(ReportTitle.SOURCE_ACCOUNT);
		filmTicketBooking.clickToDynamicTextByID("com.VCB:id/number_account");
		sourceAccount = filmTicketBooking.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, "VND");
		account = sourceAccount.account;
		
		filmTicketBooking.clickToDynamicContinue( "com.VCB:id/btn_submit");
		
		log.info("TC_04_Step_verify message khi so tien chuyen nho hon han muc toi thieu ");
		verifyEquals(filmTicketBooking.getDynamicTextView(driver, "com.VCB:id/tvContent"), "Thanh toán không thành công. Số tiền giao dịch nhỏ hơn hạn mức " + addCommasToLong((amount - 1000) + "") + " VND/1 lần, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp");

		filmTicketBooking.clickToDynamicContinue("com.VCB:id/btOK");

		webBackend.Reset_Package_Total_Limit(driverWeb, "TESTBUG", "Method Otp");

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
//		service.stop();
	}

}
