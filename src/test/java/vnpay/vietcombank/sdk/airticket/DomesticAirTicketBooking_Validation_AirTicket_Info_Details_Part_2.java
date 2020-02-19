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

public class DomesticAirTicketBooking_Validation_AirTicket_Info_Details_Part_2 extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private DynamicAirTicketBookingObjects airTicket;
	private String validName = "ANh TA";

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
	public void TC_01_ThongTinLienHe_KiemTraDienNoiDung() {
		log.info("TC_01_Step 01: Click Khoi Hanh");
		airTicket.clickToDynamicTextOrButtonLink("Khởi hành");

		log.info("TC_01_Step 02: Click Ha Noi");
		airTicket.clickToDynamicTextOrButtonLink("Hà Nội");

		log.info("TC_01_Step 03: Click Diem Den");
		airTicket.clickToDynamicTextOrButtonLink("Điểm đến");

		log.info("TC_01_Step 04: Click TP Ho Chi Minh");
		airTicket.clickToDynamicTextOrButtonLink("TP Hồ Chí Minh");

		log.info("TC_01_Step 05: Click Ngay Đi");
		airTicket.clickToDynamicTextOrButtonLink("Ngày đi");

		log.info("TC_01_Step 06: Click Chon Ngay");
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(1), getForWardDay(1));

		log.info("TC_01_Step 07: Click xac nhan");
		airTicket.clickToDynamicButton("Xác nhận");

		log.info("TC_01_Step 08: Kiem tra so luong nguoi  lon lon nhat ");
		airTicket.clickToDynamicPlusAndMinusIcon("Người lớn (12 tuổi trở lên)", "+");

		log.info("TC_01_Step 09: Click Chon Tre em");
		airTicket.clickToDynamicPlusAndMinusIcon("Trẻ em (2 đến dưới 12 tuổi)", "+");

		log.info("TC_01_Step 10: Click Chon Em be");
		airTicket.clickToDynamicPlusAndMinusIcon("Em bé (Dưới 2 tuổi)", "+");

		log.info("TC_01_Step 11: Click Tim Chuyen Bay ");
		airTicket.clickToDynamicButton("Tìm chuyến bay");

		airTicket.sleep(driver, 10000);

		log.info("TC_01_Step 12: Click Chon VietNamAirLine ");
		airTicket.clickToDynamicFlight(0, "VN");

		log.info("TC_01_Step 13: Click Dat ve ");
		airTicket.clickToDynamicButton("Đặt vé");

		log.info("TC_01_Step 14: Click Tiep Tuc ");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_01_Step 15: Dien  Ten  ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_NAME, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_hoten");

		log.info("TC_01_Step 16: Chon gioi tinh nu");
		airTicket.checkToDynamicTextOrDropDownByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/tv_NuContact");

		log.info("TC_01_Step 17: Dien Email  ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_EMAIL, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_email");

		log.info("TC_11_Step 18: Dien Phone ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_PHONE, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_phonenumber");

		log.info("TC_11_Step 19: Dien Phone ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.CONTENT, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_content");

		log.info("TC_01_Step 20: Kiem tra text Noi dung");
		verifyEquals(airTicket.getTextInDynamicTextBoxByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_content"), DomesticAirTicketBooking_Data.validInput.CONTENT);

	}

	@Test
	public void TC_02_ThongTinHanhKhach_BoTrongHoTen() {
		log.info("TC_02_Step 01: Click Tiep Tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_02_Step 02: Kiem Tra popup yeu cau nhap ho ten");
		verifyEquals(airTicket.getTextInDynamicPopUp("com.VCB:id/tvTitle"), "Quý khách vui lòng nhập đầy đủ Họ và tên hành khách 1");

		log.info("TC_02_Step 03: Click Dong y");
		airTicket.clickToDynamicButton("Đồng ý");

	}

	@Test
	public void TC_03_ThongTinHanhKhach_KiemTraNhapHoTenKhongDungDinhDang() {
		log.info("TC_03_Step 01: Nhap tieng  viet co dau");
		airTicket.inputToDynamicInputBoxByLabelAndIndex("Tiếng việt có dấu", "com.VCB:id/recy_info_book", "0", "com.VCB:id/edt_hoten");

		log.info("TC_03_Step 02: Kiem tra text box ho ten");
		verifyEquals(airTicket.getTextInDynamicTextBoxAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "0", "com.VCB:id/edt_hoten"), "Tieng viet co dau");

		log.info("TC_03_Step 03: Nhap so co dau");
		airTicket.inputToDynamicInputBoxByLabelAndIndex("1234", "com.VCB:id/recy_info_book", "0", "com.VCB:id/edt_hoten");

		log.info("TC_03_Step 04: Kiem tra text box ho ten");
		verifyEquals(airTicket.getTextInDynamicTextBoxAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "0", "com.VCB:id/edt_hoten"), "Họ Đệm và Tên (ví dụ: NGUYEN VAN A)");

		log.info("TC_03_Step 05: Nhap ky tu dac biet ");
		airTicket.inputToDynamicInputBoxByLabelAndIndex("@$$$s", "com.VCB:id/recy_info_book", "0", "com.VCB:id/edt_hoten");

		log.info("TC_03_Step 06: Kiem tra text box ho ten");
		verifyEquals(airTicket.getTextInDynamicTextBoxAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "0", "com.VCB:id/edt_hoten"), "Họ Đệm và Tên (ví dụ: NGUYEN VAN A)");

	}

	@Test
	public void TC_04_ThongTinHanhKhach_KiemTraNhapHoTenDungDinhDangVoiKyThuThuong() {
		log.info("TC_04_Step 01: Nhap ky tu thuong");
		airTicket.inputToDynamicInputBoxByLabelAndIndex("ANH TA", "com.VCB:id/recy_info_book", "0", "com.VCB:id/edt_hoten");

		log.info("TC_04_Step 02: Kiem tra text box ho ten");
		verifyEquals(airTicket.getTextInDynamicTextBoxAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "0", "com.VCB:id/edt_hoten"), "ANH TA");

	}

	@Test
	public void TC_05_ThongTinHanhKhach_KiemTraKhongChonGioiTinh() {
		log.info("TC_02_Step 01: Click Tiep Tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_02_Step 02: Kiem Tra popup yeu cau nhap ho ten");
		verifyEquals(airTicket.getTextInDynamicPopUp("com.VCB:id/tvTitle"), "Quý khách vui lòng nhập đầy đủ Họ và tên hành khách 1");

		log.info("TC_02_Step 03: Click Dong y");
		airTicket.clickToDynamicButton("Đồng ý");

	}

	@Test
	public void TC_06_ThongTinHanhKhach_KiemTraNhapSoTheKhongHopLe() {
		airTicket.clickToDynamicTextOrDropDownByLabelAndIndex("com.VCB:id/recy_info_book", "0", "com.VCB:id/ll_NuNoiDia", "com.VCB:id/tv_NuNoiDia");
		airTicket.clickToDynamicTextOrDropDownByLabelAndIndex("com.VCB:id/recy_info_book", "0", "com.VCB:id/ll_sothe", "com.VCB:id/tv_content_price");

		String invaidCardNumber = "888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888";
		log.info("TC_04_Step 01: Nhap ky tu thuong");
		airTicket.inputToDynamicInputBoxByLabelAndIndex(invaidCardNumber, "com.VCB:id/recy_info_book", "0", "com.VCB:id/edt_CardNo");

		log.info("TC_02_Step 01: Click Tiep Tuc");
		airTicket.clickToDynamicButton("Tiếp tục");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
