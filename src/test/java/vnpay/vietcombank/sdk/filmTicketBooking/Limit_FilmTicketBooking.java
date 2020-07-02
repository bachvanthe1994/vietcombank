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
import commons.Constants;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import model.FilmInfo;
import model.FilmTicketInfo;
import model.SeatType;
import pageObjects.LogInPageObject;
import pageObjects.sdk.filmTicketBooking.FilmTicketBookingPageObject;
import vietcombankUI.sdk.filmTicketBooking.FilmTicketBookingPageUIs;
import vietcombank_test_data.TransactionReport_Data.ReportTitle;
import vnpay.vietcombank.sdk.filmTicketBooking.data.FilmTicketBooking_Data;

public class Limit_FilmTicketBooking extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private FilmTicketBookingPageObject filmTicketBooking;

	String price = "";


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
	public void TC_01_ThanhToanVeXeNhoHonHanMucToiThieu() {

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

		price = filmTicketBooking.getTextViewByID("com.VCB:id/tvPrince");

		log.info("TC_01_14_Click Thanh toan");
		filmTicketBooking.clickToTextViewByText(FilmTicketBooking_Data.PAY);

		log.info("TC_01_15_Kiem tra Thong tin Thanh toan ve xem phim");
		log.info("TC_01_15_01: Kiem tra ten phim");
		filmTicketBooking.scrollUpToText(FilmTicketBooking_Data.FILM);
		
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

		
		
		log.info("TC_01_20: Click Tiep tuc");
		filmTicketBooking.clickToDynamicButton(FilmTicketBooking_Data.NEXT);

		log.info("TC_01_21: Kiem tra man hinh xac nhan thong tin");
		
		log.info("TC_01_21_01: Kiem tra tai khoan nguon");
		filmTicketBooking.scrollUpToText(FilmTicketBooking_Data.ACCOUNT_FROM_LABEL);

		
		log.info("TC_01_22_Chon phuong thuc xac thuc");
		filmTicketBooking.clickToDynamicTextViewByID("com.VCB:id/tvptxt");
		filmTicketBooking.clickToDynamicTextView(FilmTicketBooking_Data.PASSWORD);

		log.info("TC_01_23: Click Tiep tuc");
		filmTicketBooking.clickToDynamicButton(FilmTicketBooking_Data.NEXT);


		filmTicketBooking.clickToDynamicButton(FilmTicketBooking_Data.NEXT);

		log.info("TC_01_24: Kiem tra man hinh thanh toan thanh cong");
		verifyTrue(filmTicketBooking.isDynamicMessageAndLabelTextDisplayed(FilmTicketBooking_Data.MESSEGE_SUCCESS));

		log.info("TC_01_25_Kiem tra nut Thuc hien giao dich moi");
		verifyTrue(filmTicketBooking.isDynamicButtonDisplayed(FilmTicketBooking_Data.NEW_TRANSFER));

		log.info("TC_01_26_Click Thuc hien giao dich moi");
		filmTicketBooking.clickToDynamicButton(FilmTicketBooking_Data.NEW_TRANSFER);

		log.info("TC_01_27_Tinh so du kha dung cua tai khoan sau khi thanh toan thanh cong");
	}

	@Test
	public void TC_02_ThanhToanVeXeLonHonHanMucToiDa() {
		
		
	}



	// Set han muc nhom la 50 000 VND
	public void TC_03_ThanhToanVeXeNhoHonHanMucDaNhom() {

	}

	// Set han muc goi la 50 000 VND
	public void TC_05_ThanhToanVeXeNhoHonHanMucToiDaGoi() {

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
