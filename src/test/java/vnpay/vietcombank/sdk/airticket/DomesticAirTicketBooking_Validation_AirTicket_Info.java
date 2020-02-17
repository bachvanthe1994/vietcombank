package vnpay.vietcombank.sdk.airticket;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.sdk.airTicketBooking.DynamicAirTicketBookingObjects;

public class DomesticAirTicketBooking_Validation_AirTicket_Info extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private DynamicAirTicketBookingObjects airTicket;
	private String selectedAirTicketPrice;
	private String totalPrice;

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);

		log.info("Before class: Dang nhap ");
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);

		log.info("Before class: Click Dat ve may bay ");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicIcon(driver, "Đặt vé máy bay");

		log.info("Before class: Click dat ve may bay noi dia ");
		airTicket = PageFactoryManager.getDynamicAirTicketBooking(driver);
		airTicket.clickToDynamicTextOrButtonLink("Đặt vé máy bay Nội địa");

		log.info("Before class: Click Dong y ");
		airTicket.clickToDynamicButton("Đồng ý");

		log.info("Before class: Chon mot chieu ");
		airTicket.clickToDynamicTextOrButtonLink("Một chiều");

	}

	@Test
	public void TC_01_KiemTraManHinhDatVeMotChieuVietJet() {
		log.info("TC_07_Step 04: Click Khoi Hanh");
		airTicket.clickToDynamicTextOrButtonLink("Khởi hành");

		log.info("TC_07_Step 05: Click Ha Noi");
		airTicket.clickToDynamicTextOrButtonLink("Hà Nội");

		log.info("TC_07_Step 06: Click Diem Den");
		airTicket.clickToDynamicTextOrButtonLink("Điểm đến");

		log.info("TC_07_Step 07: Click TP Ho Chi Minh");
		airTicket.clickToDynamicTextOrButtonLink("TP Hồ Chí Minh");

		log.info("TC_07_Step 08: Click Ngay Đi");
		airTicket.clickToDynamicTextOrButtonLink("Ngày đi");

		log.info("TC_07_Step 09: Click Chon Ngay");
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(1), getForWardDay(1));

		log.info("TC_07_Step 10: Click xac nhan");
		airTicket.clickToDynamicButton("Xác nhận");

		log.info("TC_07_Step 11: Click Tim Chuyen Bay ");
		airTicket.clickToDynamicButton("Tìm chuyến bay");

		log.info("TC_07_Step 12:  Kiem tra ma chuyen bay hien thi ");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvTitle2Left"), "HAN");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvTitle2Right"), "SGN");

		log.info("TC_06_Step 13: CLick quay lai ");
		airTicket.clickToDynamicIcon("com.VCB:id/ivTitleLeft");

		log.info("TC_07_Step 14: Click Tim Chuyen Bay ");
		airTicket.clickToDynamicButton("Tìm chuyến bay");

		airTicket.clickToDynamicFlight(0, "VJ");

		log.info("TC_07_Step 18: Click Dat ve ");
		airTicket.clickToDynamicButton("Đặt vé");

		log.info("TC_08_Step 01: Kiem tra title Danh sach chuyen bay");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvTitle1"), "Thông tin đặt vé");

		log.info("TC_08_Step 02: Kiem tra Dack icon");
		verifyTrue(airTicket.isDynamicIconDisplayed("com.VCB:id/ivTitleLeft"));

		log.info("TC_08_Step 03: Kiem tra Call icon");
		verifyTrue(airTicket.isDynamicIconDisplayed("com.VCB:id/ivTitleRight"));

		verifyEquals(airTicket.getTextInDynamicTextBoxByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_hoten"), "Họ Đệm và Tên");
		verifyEquals(airTicket.getTextInDynamicDropDownByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/tv_NamContact"), "Nam");
		verifyEquals(airTicket.getTextInDynamicDropDownByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/tv_NuContact"), "Nữ");
		verifyEquals(airTicket.getTextInDynamicCheckboxByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/iv_check_active"), "Dùng làm thông tin hành khách bay");
		verifyEquals(airTicket.getTextInDynamicTextBoxByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_email"), "Thư điện tử");
		verifyEquals(airTicket.getTextInDynamicTextBoxByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_phonenumber"), "Số điện thoại");
		verifyEquals(airTicket.getTextInDynamicTextBoxByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_content"), "Nội dung (tùy chọn)");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvInfoAirport"), "THÔNG TIN HÀNH KHÁCH BAY");
		verifyTrue(airTicket.isDynamicTextDisplayed("Vui lòng nhập tiếng Việt không dấu, nhập đúng theo thứ tự Họ đệm và tên trên CMND hoặc giấy khai sinh với trẻ em."));
		verifyEquals(airTicket.getTextInDynamicTextViewAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "0", "com.VCB:id/tv_header_nguoi"), "Người lớn 1");

	}

//	

	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
