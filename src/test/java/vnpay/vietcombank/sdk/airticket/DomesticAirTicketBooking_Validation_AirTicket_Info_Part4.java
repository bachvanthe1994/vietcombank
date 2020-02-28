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
import vnpay.vietcombank.sdk.airticket.data.DomesticAirTicketBooking_Data;

public class DomesticAirTicketBooking_Validation_AirTicket_Info_Part4 extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private DynamicAirTicketBookingObjects airTicket;

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
		log.info("TC_01_Step 01: Click Khoi Hanh");
		airTicket.clickToDynamicTextOrButtonLink("Khởi hành");

		log.info("TC_01_Step 02: Click Ha Noi");
		airTicket.clickToDynamicTextOrButtonLink(DomesticAirTicketBooking_Data.validInput.HANOI_PLACE);

		log.info("TC_01_Step 03: Click Diem Den");
		airTicket.clickToDynamicTextOrButtonLink("Điểm đến");

		log.info("TC_01_Step 04: Click TP Ho Chi Minh");
		airTicket.clickToDynamicTextOrButtonLink(DomesticAirTicketBooking_Data.validInput.HCM_PLACE);

		log.info("TC_01_Step 05: Click Ngay Đi");
		airTicket.clickToDynamicTextOrButtonLink("Ngày đi");

		log.info("TC_01_Step 06: Click Chon Ngay");
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(1), getForWardDay(1));

		log.info("TC_01_Step 17: Click xac nhan");
		airTicket.clickToDynamicButton("Xác nhận");

		log.info("TC_01_Step 08: Click Chon Tre em");
		airTicket.clickToDynamicPlusAndMinusIcon("Trẻ em (2 đến dưới 12 tuổi)", "+");

		log.info("TC_01_Step 09: Click Chon Em be");
		airTicket.clickToDynamicPlusAndMinusIcon("Em bé (Dưới 2 tuổi)", "+");

		log.info("TC_01_Step 12: Click Tim Chuyen Bay ");
		airTicket.clickToDynamicButton("Tìm chuyến bay");

		airTicket.sleep(driver, 10000);

		log.info("TC_01_Step 13: Click Chon Viet Jet ");
		airTicket.clickToDynamicFlight(0, "VJ");

		log.info("TC_01_Step 14: Click Dat ve ");
		airTicket.clickToDynamicButton("Đặt vé");

		log.info("TC_01_Step 15: Kiem tra title Danh sach chuyen bay");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvTitle1"), "Thông tin đặt vé");

		log.info("TC_01_Step 16: Kiem tra Dack icon");
		verifyTrue(airTicket.isDynamicIconDisplayed("com.VCB:id/ivTitleLeft"));

		log.info("TC_01_Step 17: Kiem tra Call icon");
		verifyTrue(airTicket.isDynamicIconDisplayed("com.VCB:id/ivTitleRight"));

		log.info("TC_01_Step 18 : Kiem tra text ho dem va ten");
		verifyEquals(airTicket.getTextInDynamicTextBoxByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_hoten"), "Họ Đệm và Tên");

		log.info("TC_01_Step 19:Kiem tra button chon Nam");
		verifyEquals(airTicket.getTextInDynamicDropDownByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/tv_NamContact"), "Nam");

		log.info("TC_01_Step 20:Kiem tra button chon nu");
		verifyEquals(airTicket.getTextInDynamicDropDownByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/tv_NuContact"), "Nữ");

		log.info("TC_01_Step 21:Kiem tra text dung lam thong tin khach hang bay");
		verifyEquals(airTicket.getTextInDynamicCheckboxByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/iv_check_active"), "Dùng làm thông tin hành khách bay");

		log.info("TC_01_Step 22:Kiem tra text thu dien tu");
		verifyEquals(airTicket.getTextInDynamicTextBoxByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_email"), "Thư điện tử");

		log.info("TC_01_Step 23:Kiem tra text so dien thoai");
		verifyEquals(airTicket.getTextInDynamicTextBoxByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_phonenumber"), "Số điện thoại");

		log.info("TC_01_Step 24: Kiem tra text Noi dung");
		verifyEquals(airTicket.getTextInDynamicTextBoxByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_content"), "Nội dung (tùy chọn)");

		log.info("TC_01_Step 25: Kiem tra text thong tin khach hang bay");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvInfoAirport"), "THÔNG TIN HÀNH KHÁCH BAY");

		log.info("TC_01_Step 26: Kiem tra chu y");
		verifyTrue(airTicket.isDynamicTextDisplayed("Vui lòng nhập tiếng Việt không dấu, nhập đúng theo thứ tự Họ đệm và tên trên CMND hoặc giấy khai sinh với trẻ em."));

		log.info("TC_01_Step 27: Kiem tra so luong nguoi lon");
		verifyEquals(airTicket.getTextInDynamicHeaderViewAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "0", "com.VCB:id/tv_header_nguoi"), "Người lớn 1");

		log.info("TC_01_Step 28: Kiem tra button nam");
		verifyEquals(airTicket.getTextInDynamicTextViewAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "0", "com.VCB:id/ll_NamNoiDia", "com.VCB:id/tv_NamNoiDia"), "Nam");

		log.info("TC_01_Step 29: Kiem tra button nu");
		verifyEquals(airTicket.getTextInDynamicTextViewAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "0", "com.VCB:id/ll_NuNoiDia", "com.VCB:id/tv_NuNoiDia"), "Nữ");

		log.info("TC_01_Step 30: Kiem tra text box ho ten");
		verifyEquals(airTicket.getTextInDynamicTextBoxAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "0", "com.VCB:id/edt_hoten"), "Họ Đệm và Tên (ví dụ: NGUYEN VAN A)");

		log.info("TC_01_Step 31: Kiem tra text hanh ly chieu di");
		verifyEquals(airTicket.getTextInDynamicTextViewAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "0", "com.VCB:id/hanhlychieudi", "com.VCB:id/tv_content_price"), "Hành lý chiều đi");

		log.info("TC_01_Step 32: Kiem tra so luong tre em");
		verifyEquals(airTicket.getTextInDynamicHeaderViewAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "1", "com.VCB:id/tv_header_nguoi"), "Trẻ em 1");

		log.info("TC_01_Step 33: Kiem tra button nam");
		verifyEquals(airTicket.getTextInDynamicTextViewAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "1", "com.VCB:id/ll_NamNoiDia", "com.VCB:id/tv_NamNoiDia"), "Nam");

		log.info("TC_01_Step 34: Kiem tra button nu");
		verifyEquals(airTicket.getTextInDynamicTextViewAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "1", "com.VCB:id/ll_NuNoiDia", "com.VCB:id/tv_NuNoiDia"), "Nữ");

		log.info("TC_01_Step 35: Kiem tra text box ho ten");
		verifyEquals(airTicket.getTextInDynamicTextBoxAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "1", "com.VCB:id/edt_hoten"), "Họ Đệm và Tên (ví dụ: NGUYEN VAN A)");

		log.info("TC_01_Step 36: Kiem tra text ngay sinh");
		verifyEquals(airTicket.getTextInDynamicTextViewAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "1", "com.VCB:id/birthday_child", "com.VCB:id/tv_content_price"), "Ngày sinh");

		log.info("TC_01_Step 37: Kiem tra text hanh ly chieu di");
		verifyEquals(airTicket.getTextInDynamicTextViewAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "1", "com.VCB:id/hanhlychieudi", "com.VCB:id/tv_content_price"), "Hành lý chiều đi");

		log.info("TC_01_Step 38: Kiem tra so luong em be");
		verifyEquals(airTicket.getTextInDynamicHeaderViewAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "2", "com.VCB:id/tv_header_nguoi"), "Em bé 1");

		log.info("TC_01_Step 39: Kiem tra button nam");
		verifyEquals(airTicket.getTextInDynamicTextViewAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "2", "com.VCB:id/ll_NamNoiDia", "com.VCB:id/tv_NamNoiDia"), "Nam");

		log.info("TC_01_Step 40: Kiem tra button nu");
		verifyEquals(airTicket.getTextInDynamicTextViewAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "2", "com.VCB:id/ll_NuNoiDia", "com.VCB:id/tv_NuNoiDia"), "Nữ");

		log.info("TC_01_Step 41: Kiem tra text box ho ten");
		verifyEquals(airTicket.getTextInDynamicTextBoxAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "2", "com.VCB:id/edt_hoten"), "Họ Đệm và Tên (ví dụ: NGUYEN VAN A)");

		log.info("TC_01_Step 42: Kiem tra text so luong nguoi");
		verifyEquals(airTicket.getTextInDynamicTextViewAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "2", "com.VCB:id/birthday_child", "com.VCB:id/tv_content_price"), "Ngày sinh");

		log.info("TC_01_Step 43: Kiem tra text ngay sinh");
		verifyTrue(airTicket.isDynamicButtonDisplayed("Tiếp tục"));
	}

	@Test
	public void TC_02_KiemTraManHinhVeMotChieuJesStar() {
		log.info("TC_02_Step 13: CLick quay lai ");
		airTicket.clickToDynamicIcon("com.VCB:id/ivTitleLeft");

		log.info("TC_02_Step 13: Click Chon Viet Jet ");
		airTicket.clickToDynamicFlight(0, "BL");

		log.info("TC_02_Step 14: Click Dat ve ");
		airTicket.clickToDynamicButton("Đặt vé");

		log.info("TC_02_Step 16: Kiem tra Dack icon");
		verifyTrue(airTicket.isDynamicIconDisplayed("com.VCB:id/ivTitleLeft"));

		log.info("TC_02_Step 17: Kiem tra Call icon");
		verifyTrue(airTicket.isDynamicIconDisplayed("com.VCB:id/ivTitleRight"));

		log.info("TC_02_Step 18 : Kiem tra text ho dem va ten");
		verifyEquals(airTicket.getTextInDynamicTextBoxByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_hoten"), "Họ Đệm và Tên");

		log.info("TC_02_Step 19:Kiem tra button chon Nam");
		verifyEquals(airTicket.getTextInDynamicDropDownByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/tv_NamContact"), "Nam");

		log.info("TC_02_Step 20:Kiem tra button chon nu");
		verifyEquals(airTicket.getTextInDynamicDropDownByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/tv_NuContact"), "Nữ");

		log.info("TC_02_Step 21:Kiem tra text dung lam thong tin khach hang bay");
		verifyEquals(airTicket.getTextInDynamicCheckboxByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/iv_check_active"), "Dùng làm thông tin hành khách bay");

		log.info("TC_02_Step 22:Kiem tra text thu dien tu");
		verifyEquals(airTicket.getTextInDynamicTextBoxByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_email"), "Thư điện tử");

		log.info("TC_02_Step 23:Kiem tra text so dien thoai");
		verifyEquals(airTicket.getTextInDynamicTextBoxByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_phonenumber"), "Số điện thoại");

		log.info("TC_02_Step 24: Kiem tra text Noi dung");
		verifyEquals(airTicket.getTextInDynamicTextBoxByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_content"), "Nội dung (tùy chọn)");

		log.info("TC_02_Step 25: Kiem tra text thong tin khach hang bay");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvInfoAirport"), "THÔNG TIN HÀNH KHÁCH BAY");

		log.info("TC_02_Step 26: Kiem tra chu y");
		verifyTrue(airTicket.isDynamicTextDisplayed("Vui lòng nhập tiếng Việt không dấu, nhập đúng theo thứ tự Họ đệm và tên trên CMND hoặc giấy khai sinh với trẻ em."));

		log.info("TC_02_Step 27: Kiem tra so luong nguoi lon");
		verifyEquals(airTicket.getTextInDynamicHeaderViewAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "0", "com.VCB:id/tv_header_nguoi"), "Người lớn 1");

		log.info("TC_02_Step 28: Kiem tra button nam");
		verifyEquals(airTicket.getTextInDynamicTextViewAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "0", "com.VCB:id/ll_NamNoiDia", "com.VCB:id/tv_NamNoiDia"), "Nam");

		log.info("TC_02_Step 29: Kiem tra button nu");
		verifyEquals(airTicket.getTextInDynamicTextViewAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "0", "com.VCB:id/ll_NuNoiDia", "com.VCB:id/tv_NuNoiDia"), "Nữ");

		log.info("TC_02_Step 30: Kiem tra text box ho ten");
		verifyEquals(airTicket.getTextInDynamicTextBoxAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "0", "com.VCB:id/edt_hoten"), "Họ Đệm và Tên (ví dụ: NGUYEN VAN A)");

		log.info("TC_02_Step 31: Kiem tra text hanh ly chieu di");
		verifyEquals(airTicket.getTextInDynamicTextViewAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "0", "com.VCB:id/hanhlychieudi", "com.VCB:id/tv_content_price"), "Hành lý chiều đi");

		log.info("TC_02_Step 32: Kiem tra so luong tre em");
		verifyEquals(airTicket.getTextInDynamicHeaderViewAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "1", "com.VCB:id/tv_header_nguoi"), "Trẻ em 1");

		log.info("TC_02_Step 33: Kiem tra button nam");
		verifyEquals(airTicket.getTextInDynamicTextViewAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "1", "com.VCB:id/ll_NamNoiDia", "com.VCB:id/tv_NamNoiDia"), "Nam");

		log.info("TC_02_Step 34: Kiem tra button nu");
		verifyEquals(airTicket.getTextInDynamicTextViewAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "1", "com.VCB:id/ll_NuNoiDia", "com.VCB:id/tv_NuNoiDia"), "Nữ");

		log.info("TC_02_Step 35: Kiem tra text box ho ten");
		verifyEquals(airTicket.getTextInDynamicTextBoxAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "1", "com.VCB:id/edt_hoten"), "Họ Đệm và Tên (ví dụ: NGUYEN VAN A)");

		log.info("TC_02_Step 36: Kiem tra text ngay sinh");
		verifyEquals(airTicket.getTextInDynamicTextViewAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "1", "com.VCB:id/birthday_child", "com.VCB:id/tv_content_price"), "Ngày sinh");

		log.info("TC_02_Step 37: Kiem tra text hanh ly chieu di");
		verifyEquals(airTicket.getTextInDynamicTextViewAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "1", "com.VCB:id/hanhlychieudi", "com.VCB:id/tv_content_price"), "Hành lý chiều đi");

		log.info("TC_02_Step 38: Kiem tra so luong em be");
		verifyEquals(airTicket.getTextInDynamicHeaderViewAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "2", "com.VCB:id/tv_header_nguoi"), "Em bé 1");

		log.info("TC_02_Step 39: Kiem tra button nam");
		verifyEquals(airTicket.getTextInDynamicTextViewAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "2", "com.VCB:id/ll_NamNoiDia", "com.VCB:id/tv_NamNoiDia"), "Nam");

		log.info("TC_02_Step 40: Kiem tra button nu");
		verifyEquals(airTicket.getTextInDynamicTextViewAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "2", "com.VCB:id/ll_NuNoiDia", "com.VCB:id/tv_NuNoiDia"), "Nữ");

		log.info("TC_02_Step 41: Kiem tra text box ho ten");
		verifyEquals(airTicket.getTextInDynamicTextBoxAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "2", "com.VCB:id/edt_hoten"), "Họ Đệm và Tên (ví dụ: NGUYEN VAN A)");

		log.info("TC_02_Step 42: Kiem tra text so luong nguoi");
		verifyEquals(airTicket.getTextInDynamicTextViewAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "2", "com.VCB:id/birthday_child", "com.VCB:id/tv_content_price"), "Ngày sinh");

		log.info("TC_14_Step 43: Kiem tra text ngay sinh");
		verifyTrue(airTicket.isDynamicButtonDisplayed("Tiếp tục"));

	}

	@Test
	public void TC_03_KiemTraManHinhVeMotChieuVNAirLine() {
		log.info("TC_03_Step 13: CLick quay lai ");
		airTicket.clickToDynamicIcon("com.VCB:id/ivTitleLeft");

		log.info("TC_03_Step 13: Click Chon Viet Jet ");
		airTicket.clickToDynamicFlight(0, "VN");

		log.info("TC_03_Step 14: Click Dat ve ");
		airTicket.clickToDynamicButton("Đặt vé");

		log.info("TC_03_Step 16: Kiem tra Dack icon");
		verifyTrue(airTicket.isDynamicIconDisplayed("com.VCB:id/ivTitleLeft"));

		log.info("TC_03_Step 17: Kiem tra Call icon");
		verifyTrue(airTicket.isDynamicIconDisplayed("com.VCB:id/ivTitleRight"));

		log.info("TC_03_Step 18 : Kiem tra text ho dem va ten");
		verifyEquals(airTicket.getTextInDynamicTextBoxByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_hoten"), "Họ Đệm và Tên");

		log.info("TC_03_Step 19:Kiem tra button chon Nam");
		verifyEquals(airTicket.getTextInDynamicDropDownByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/tv_NamContact"), "Nam");

		log.info("TC_03_Step 20:Kiem tra button chon nu");
		verifyEquals(airTicket.getTextInDynamicDropDownByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/tv_NuContact"), "Nữ");

		log.info("TC_03_Step 21:Kiem tra text dung lam thong tin khach hang bay");
		verifyEquals(airTicket.getTextInDynamicCheckboxByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/iv_check_active"), "Dùng làm thông tin hành khách bay");

		log.info("TC_03_Step 22:Kiem tra text thu dien tu");
		verifyEquals(airTicket.getTextInDynamicTextBoxByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_email"), "Thư điện tử");

		log.info("TC_03_Step 23:Kiem tra text so dien thoai");
		verifyEquals(airTicket.getTextInDynamicTextBoxByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_phonenumber"), "Số điện thoại");

		log.info("TC_03_Step 24: Kiem tra text Noi dung");
		verifyEquals(airTicket.getTextInDynamicTextBoxByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_content"), "Nội dung (tùy chọn)");

		log.info("TC_03_Step 25: Kiem tra text thong tin khach hang bay");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvInfoAirport"), "THÔNG TIN HÀNH KHÁCH BAY");

		log.info("TC_03_Step 26: Kiem tra chu y");
		verifyTrue(airTicket.isDynamicTextDisplayed("Vui lòng nhập tiếng Việt không dấu, nhập đúng theo thứ tự Họ đệm và tên trên CMND hoặc giấy khai sinh với trẻ em."));

		log.info("TC_03_Step 27: Kiem tra so luong nguoi lon");
		verifyEquals(airTicket.getTextInDynamicHeaderViewAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "0", "com.VCB:id/tv_header_nguoi"), "Người lớn 1");

		log.info("TC_03_Step 28: Kiem tra button nam");
		verifyEquals(airTicket.getTextInDynamicTextViewAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "0", "com.VCB:id/ll_NamNoiDia", "com.VCB:id/tv_NamNoiDia"), "Nam");

		log.info("TC_03_Step 29: Kiem tra button nu");
		verifyEquals(airTicket.getTextInDynamicTextViewAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "0", "com.VCB:id/ll_NuNoiDia", "com.VCB:id/tv_NuNoiDia"), "Nữ");

		log.info("TC_03_Step 30: Kiem tra text box ho ten");
		verifyEquals(airTicket.getTextInDynamicTextBoxAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "0", "com.VCB:id/edt_hoten"), "Họ Đệm và Tên (ví dụ: NGUYEN VAN A)");

		log.info("TC_03_Step 31: Kiem tra text hanh ly chieu di");
		verifyEquals(airTicket.getTextInDynamicTextViewAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "0", "com.VCB:id/ll_sothe", "com.VCB:id/tv_content_price"), "Số thẻ khách hàng thường xuyên");

		log.info("TC_03_Step 32: Kiem tra so luong tre em");
		verifyEquals(airTicket.getTextInDynamicHeaderViewAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "1", "com.VCB:id/tv_header_nguoi"), "Trẻ em 1");

		log.info("TC_03_Step 33: Kiem tra button nam");
		verifyEquals(airTicket.getTextInDynamicTextViewAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "1", "com.VCB:id/ll_NamNoiDia", "com.VCB:id/tv_NamNoiDia"), "Nam");

		log.info("TC_03_Step 34: Kiem tra button nu");
		verifyEquals(airTicket.getTextInDynamicTextViewAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "1", "com.VCB:id/ll_NuNoiDia", "com.VCB:id/tv_NuNoiDia"), "Nữ");

		log.info("TC_03_Step 35: Kiem tra text box ho ten");
		verifyEquals(airTicket.getTextInDynamicTextBoxAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "1", "com.VCB:id/edt_hoten"), "Họ Đệm và Tên (ví dụ: NGUYEN VAN A)");

		log.info("TC_03_Step 36: Kiem tra text ngay sinh");
		verifyEquals(airTicket.getTextInDynamicTextViewAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "1", "com.VCB:id/birthday_child", "com.VCB:id/tv_content_price"), "Ngày sinh");

		log.info("TC_03_Step 37: Kiem tra text hanh ly chieu di");
		verifyEquals(airTicket.getTextInDynamicTextViewAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "1", "com.VCB:id/ll_sothe", "com.VCB:id/tv_content_price"), "Số thẻ khách hàng thường xuyên");

		log.info("TC_03_Step 38: Kiem tra so luong em be");
		verifyEquals(airTicket.getTextInDynamicHeaderViewAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "2", "com.VCB:id/tv_header_nguoi"), "Em bé 1");

		log.info("TC_03_Step 39: Kiem tra button nam");
		verifyEquals(airTicket.getTextInDynamicTextViewAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "2", "com.VCB:id/ll_NamNoiDia", "com.VCB:id/tv_NamNoiDia"), "Nam");

		log.info("TC_03_Step 40: Kiem tra button nu");
		verifyEquals(airTicket.getTextInDynamicTextViewAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "2", "com.VCB:id/ll_NuNoiDia", "com.VCB:id/tv_NuNoiDia"), "Nữ");

		log.info("TC_03_Step 41: Kiem tra text box ho ten");
		verifyEquals(airTicket.getTextInDynamicTextBoxAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "2", "com.VCB:id/edt_hoten"), "Họ Đệm và Tên (ví dụ: NGUYEN VAN A)");

		log.info("TC_03_Step 42: Kiem tra text so luong nguoi");
		verifyEquals(airTicket.getTextInDynamicTextViewAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "2", "com.VCB:id/birthday_child", "com.VCB:id/tv_content_price"), "Ngày sinh");

		log.info("TC_14_Step 43: Kiem tra text ngay sinh");
		verifyTrue(airTicket.isDynamicButtonDisplayed("Tiếp tục"));

	}

	@Test
	public void TC_04_KiemTraManHinhVeMotChieuBamBoo() {
		log.info("TC_04_Step 13: CLick quay lai ");
		airTicket.clickToDynamicIcon("com.VCB:id/ivTitleLeft");

		log.info("TC_04_Step 13: Click Chon Viet Jet ");
		airTicket.clickToDynamicFlight(0, "QH");

		log.info("TC_04_Step 14: Click Dat ve ");
		airTicket.clickToDynamicButton("Đặt vé");

		log.info("TC_04_Step 16: Kiem tra Dack icon");
		verifyTrue(airTicket.isDynamicIconDisplayed("com.VCB:id/ivTitleLeft"));

		log.info("TC_04_Step 17: Kiem tra Call icon");
		verifyTrue(airTicket.isDynamicIconDisplayed("com.VCB:id/ivTitleRight"));

		log.info("TC_04_Step 18 : Kiem tra text ho dem va ten");
		verifyEquals(airTicket.getTextInDynamicTextBoxByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_hoten"), "Họ Đệm và Tên");

		log.info("TC_04_Step 19:Kiem tra button chon Nam");
		verifyEquals(airTicket.getTextInDynamicDropDownByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/tv_NamContact"), "Nam");

		log.info("TC_04_Step 20:Kiem tra button chon nu");
		verifyEquals(airTicket.getTextInDynamicDropDownByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/tv_NuContact"), "Nữ");

		log.info("TC_04_Step 21:Kiem tra text dung lam thong tin khach hang bay");
		verifyEquals(airTicket.getTextInDynamicCheckboxByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/iv_check_active"), "Dùng làm thông tin hành khách bay");

		log.info("TC_04_Step 22:Kiem tra text thu dien tu");
		verifyEquals(airTicket.getTextInDynamicTextBoxByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_email"), "Thư điện tử");

		log.info("TC_04_Step 23:Kiem tra text so dien thoai");
		verifyEquals(airTicket.getTextInDynamicTextBoxByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_phonenumber"), "Số điện thoại");

		log.info("TC_04_Step 24: Kiem tra text Noi dung");
		verifyEquals(airTicket.getTextInDynamicTextBoxByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_content"), "Nội dung (tùy chọn)");

		log.info("TC_04_Step 25: Kiem tra text thong tin khach hang bay");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvInfoAirport"), "THÔNG TIN HÀNH KHÁCH BAY");

		log.info("TC_04_Step 26: Kiem tra chu y");
		verifyTrue(airTicket.isDynamicTextDisplayed("Vui lòng nhập tiếng Việt không dấu, nhập đúng theo thứ tự Họ đệm và tên trên CMND hoặc giấy khai sinh với trẻ em."));

		log.info("TC_04_Step 27: Kiem tra so luong nguoi lon");
		verifyEquals(airTicket.getTextInDynamicHeaderViewAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "0", "com.VCB:id/tv_header_nguoi"), "Người lớn 1");

		log.info("TC_04_Step 28: Kiem tra button nam");
		verifyEquals(airTicket.getTextInDynamicTextViewAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "0", "com.VCB:id/ll_NamNoiDia", "com.VCB:id/tv_NamNoiDia"), "Nam");

		log.info("TC_04_Step 29: Kiem tra button nu");
		verifyEquals(airTicket.getTextInDynamicTextViewAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "0", "com.VCB:id/ll_NuNoiDia", "com.VCB:id/tv_NuNoiDia"), "Nữ");

		log.info("TC_04_Step 30: Kiem tra text box ho ten");
		verifyEquals(airTicket.getTextInDynamicTextBoxAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "0", "com.VCB:id/edt_hoten"), "Họ Đệm và Tên (ví dụ: NGUYEN VAN A)");

		log.info("TC_04_Step 31: Kiem tra text hanh ly chieu di");
		verifyEquals(airTicket.getTextInDynamicTextViewAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "0", "com.VCB:id/hanhlychieudi", "com.VCB:id/tv_content_price"), "Hành lý chiều đi");

		log.info("TC_04_Step 32: Kiem tra so luong tre em");
		verifyEquals(airTicket.getTextInDynamicHeaderViewAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "1", "com.VCB:id/tv_header_nguoi"), "Trẻ em 1");

		log.info("TC_04_Step 33: Kiem tra button nam");
		verifyEquals(airTicket.getTextInDynamicTextViewAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "1", "com.VCB:id/ll_NamNoiDia", "com.VCB:id/tv_NamNoiDia"), "Nam");

		log.info("TC_04_Step 34: Kiem tra button nu");
		verifyEquals(airTicket.getTextInDynamicTextViewAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "1", "com.VCB:id/ll_NuNoiDia", "com.VCB:id/tv_NuNoiDia"), "Nữ");

		log.info("TC_04_Step 35: Kiem tra text box ho ten");
		verifyEquals(airTicket.getTextInDynamicTextBoxAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "1", "com.VCB:id/edt_hoten"), "Họ Đệm và Tên (ví dụ: NGUYEN VAN A)");

		log.info("TC_04_Step 36: Kiem tra text ngay sinh");
		verifyEquals(airTicket.getTextInDynamicTextViewAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "1", "com.VCB:id/birthday_child", "com.VCB:id/tv_content_price"), "Ngày sinh");

		log.info("TC_04_Step 37: Kiem tra text hanh ly chieu di");
		verifyEquals(airTicket.getTextInDynamicTextViewAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "1", "com.VCB:id/hanhlychieudi", "com.VCB:id/tv_content_price"), "Hành lý chiều đi");

		log.info("TC_04_Step 38: Kiem tra so luong em be");
		verifyEquals(airTicket.getTextInDynamicHeaderViewAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "2", "com.VCB:id/tv_header_nguoi"), "Em bé 1");

		log.info("TC_04_Step 39: Kiem tra button nam");
		verifyEquals(airTicket.getTextInDynamicTextViewAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "2", "com.VCB:id/ll_NamNoiDia", "com.VCB:id/tv_NamNoiDia"), "Nam");

		log.info("TC_04_Step 40: Kiem tra button nu");
		verifyEquals(airTicket.getTextInDynamicTextViewAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "2", "com.VCB:id/ll_NuNoiDia", "com.VCB:id/tv_NuNoiDia"), "Nữ");

		log.info("TC_04_Step 41: Kiem tra text box ho ten");
		verifyEquals(airTicket.getTextInDynamicTextBoxAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "2", "com.VCB:id/edt_hoten"), "Họ Đệm và Tên (ví dụ: NGUYEN VAN A)");

		log.info("TC_04_Step 42: Kiem tra text so luong nguoi");
		verifyEquals(airTicket.getTextInDynamicTextViewAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "2", "com.VCB:id/birthday_child", "com.VCB:id/tv_content_price"), "Ngày sinh");

		log.info("TC_14_Step 43: Kiem tra text ngay sinh");
		verifyTrue(airTicket.isDynamicButtonDisplayed("Tiếp tục"));

	}

//	

	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
