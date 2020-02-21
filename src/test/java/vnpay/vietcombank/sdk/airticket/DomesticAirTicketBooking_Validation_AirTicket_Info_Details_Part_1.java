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

public class DomesticAirTicketBooking_Validation_AirTicket_Info_Details_Part_1 extends Base {
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
	public void TC_01_ThongTinLienHe_KiemTraBoTrongHoTen() {
		log.info("TC_01_Step 01: Click Khoi Hanh");
		airTicket.clickToDynamicTextOrButtonLink("Khởi hành");

		log.info("TC_01_Step 02: Click Ha Noi");
		airTicket.clickToDynamicTextOrButtonLink(DomesticAirTicketBooking_Data.validInput.DEPARTURE_PLACE);

		log.info("TC_01_Step 03: Click Diem Den");
		airTicket.clickToDynamicTextOrButtonLink("Điểm đến");

		log.info("TC_01_Step 04: Click TP Ho Chi Minh");
		airTicket.clickToDynamicTextOrButtonLink(DomesticAirTicketBooking_Data.validInput.ARRIVAL_PLACE);

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
		airTicket.clickToDynamicFlight(0, "QH");

		log.info("TC_01_Step 14: Click Dat ve ");
		airTicket.clickToDynamicButton("Đặt vé");

		log.info("TC_01_Step 15: Click Tiep Tuc ");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_01_Step 16: Kiem Tra popup yeu cau nhap ho ten");
		verifyEquals(airTicket.getTextInDynamicPopUp("com.VCB:id/tvTitle"), "Quý khách vui lòng nhập đầy đủ Họ tên người liên hệ.");

		log.info("TC_02_Step 02: Click Dong y");
		airTicket.clickToDynamicButton("Đồng ý");

	}

	@Test
	public void TC_02_ThongTinLienHe_KiemTraDinhDangHoTenKhongHopLe() {
		log.info("TC_02_Step 01: Dien 1 Ten khong dung dinh dang ");
		airTicket.inputToDynamicInputBoxByLabel("ABC", "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_hoten");

		log.info("TC_02_Step 02: Click Tiep Tuc ");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_02_Step 03: Kiem Tra popup ten khong dung dinh dang");
		verifyEquals(airTicket.getTextInDynamicPopUp("com.VCB:id/tvTitle"), "Định dạng họ tên hành khách bay không hợp lệ. Quý khách vui lòng kiểm tra lại hoặc liên hệ 1900555520 để được hỗ trợ.");

		log.info("TC_02_Step 02: Click Dong y");
		airTicket.clickToDynamicButton("Đồng ý");
	}

	@Test
	public void TC_03_ThongTinLienHe_KiemTraDinhDangHoTenKhongDungDinhDang() {
		log.info("TC_03_Step 01: Dien 1 Ten khong dung dinh dang ");
		airTicket.inputToDynamicInputBoxByLabel("1234 !@#$%^&*()", "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_hoten");

		log.info("TC_03_Step 02 : Kiem tra text khong duoc input vao");
		verifyEquals(airTicket.getTextInDynamicTextBoxByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_hoten"), "Họ Đệm và Tên");

	}

	@Test
	public void TC_04_ThongTinLienHe_KiemTraDinhDangHoTenDungDinhDang() {

		log.info("TC_04_Step 01: Dien 1 Ten khong dung dinh dang ");
		airTicket.inputToDynamicInputBoxByLabel("ANH TA", "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_hoten");

		log.info("TC_04_Step 18 : Kiem tra ten da duoc dien");
		verifyEquals(airTicket.getTextInDynamicTextBoxByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_hoten"), "ANH TA");

	}

	@Test
	public void TC_05_ThongTinLienHe_KiemTraKhongChonGioiTinh() {
		log.info("TC_05_Step 02: Click Tiep Tuc ");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_05_Step 03: Kiem Tra popup ten khong dung dinh dang");
		verifyEquals(airTicket.getTextInDynamicPopUp("com.VCB:id/tvTitle"), "Quý khách vui lòng chọn giới tính của người liên hệ");

		log.info("TC_05_Step 02: Click Dong y");
		airTicket.clickToDynamicButton("Đồng ý");

	}

	@Test
	public void TC_06_ThongTinLienHe_KiemTraKhongCheckVaoCheckBox() {
		log.info("TC_06_Step 01: Chon gioi tinh");
		airTicket.checkToDynamicTextOrDropDownByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/tv_NuContact");

		log.info("TC_06_Step 02: Click Tiep Tuc ");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_06_Step 03: Kiem Tra popup ten khong dung dinh dang");
		verifyEquals(airTicket.getTextInDynamicPopUp("com.VCB:id/tvTitle"), "Quý khách vui lòng nhập Email liên hệ.");

		log.info("TC_06_Step 02: Click Dong y");
		airTicket.clickToDynamicButton("Đồng ý");

	}

	@Test
	public void TC_07_ThongTinLienHe_KiemTraChonCheckBox() {

		log.info("TC_07_Step 01: Check vao check box chon dung lam thong tin hanh khach bay");
		airTicket.clickToDynamicCheckBoxByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/iv_check_active");

		log.info("TC_07_Step 02: Kiem tra check box da duoc chon");
		verifyTrue(airTicket.isDynamicCheckBoxChecked("THÔNG TIN LIÊN HỆ", "com.VCB:id/iv_check_active"));
	}

	@Test
	public void TC_08_ThongTinLienHe_KiemTraBoTrongThuDienTu() {
		log.info("TC_08_Step 01: Click Tiep Tuc ");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_08_Step 02: Kiem Tra popup ten khong dung dinh dang");
		verifyEquals(airTicket.getTextInDynamicPopUp("com.VCB:id/tvTitle"), "Quý khách vui lòng nhập Email liên hệ.");

		log.info("TC_08_Step 03: Click Dong y");
		airTicket.clickToDynamicButton("Đồng ý");
	}

	@Test
	public void TC_09_ThongTinLienHe_KiemTraNhapThuDienTuKhongDungDinhDang() {

		String[] invalidEmails = { "abc", "abc@", "abc@gmail", "abc  @gmail.com", "abc12$%^&&@gmail.com" };
		for (String email : invalidEmails) {
			log.info("TC_09_Step 01: Dien Email khong dung dinh dang ");
			airTicket.inputToDynamicInputBoxByLabel(email, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_email");

			log.info("TC_09_Step 02: Click Tiep Tuc ");
			airTicket.clickToDynamicButton("Tiếp tục");

			log.info("TC_09_Step 02: Kiem tra pop-up Email khong dung dinh dang hien thi");
			verifyEquals(airTicket.getTextInDynamicPopUp("com.VCB:id/tvTitle"), "Email không đúng định dạng. Quý khách vui lòng kiểm tra lại.");

			log.info("TC_09_Step 03: Click Dong y");
			airTicket.clickToDynamicButton("Đồng ý");
		}

	}

	@Test
	public void TC_10_ThongTinLienHe_KiemTraNhapThuDienTuDungDinhDang() {

		log.info("TC_10_Step 01: Dien Email khong dung dinh dang ");
		airTicket.inputToDynamicInputBoxByLabel("abc@gmail.com", "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_email");

		log.info("TC_10_Step 02:Kiem tra text thu dien tu");
		verifyEquals(airTicket.getTextInDynamicTextBoxByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_email"), "abc@gmail.com");

	}

	@Test
	public void TC_11_ThongTinLienHe_KiemTraBoTrongSoDienThoai() {

		log.info("TC_11_Step 01: Click Tiep Tuc ");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_11_Step 02: Kiem tra pop-up Email khong dung dinh dang hien thi");
		verifyEquals(airTicket.getTextInDynamicPopUp("com.VCB:id/tvTitle"), "Quý khách vui lòng nhập Số điện thoại người liên hệ.");

	}

	@Test
	public void TC_12_ThongTinLienHe_KiemTraNhapSoDienThoaiKhongHopLe() {
		String[] invalidPhone = { "abc" };
		for (String phone : invalidPhone) {
			log.info("TC_12_Step 01: Dien Phone khong dung dinh dang ");
			airTicket.inputToDynamicInputBoxByLabel(phone, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_phonenumber");

			log.info("TC_12_Step 23:Kiem tra text so dien thoai");
			verifyEquals(airTicket.getTextInDynamicTextBoxByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_phonenumber"), "Số điện thoại");

		}

		String[] invalidPhoneNumber = { "1234", "1234567891" };
		for (String phone : invalidPhoneNumber) {
			log.info("TC_12_Step 01: Dien Phone khong dung dinh dang ");
			airTicket.inputToDynamicInputBoxByLabel(phone, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_phonenumber");

			log.info("TC_12_Step 01: Click Tiep Tuc ");
			airTicket.clickToDynamicButton("Tiếp tục");

			log.info("TC_12_Step 02: Kiem tra pop-up Email khong dung dinh dang hien thi");
			verifyEquals(airTicket.getTextInDynamicPopUp("com.VCB:id/tvTitle"), "Số điện thoại không đúng định dạng. Quý khách vui lòng kiểm tra lại.");

		}

	}

	@Test
	public void TC_13_ThongTinLienHe_KiemTraNhapSoDienThoaiHopLe() {
		String validPhone = "0123456789";
		log.info("TC_13_Step 01: Dien Phone khong dung dinh dang ");
		airTicket.inputToDynamicInputBoxByLabel(validPhone, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_phonenumber");

		log.info("TC_13_Step 02:Kiem tra text so dien thoai");
		verifyEquals(airTicket.getTextInDynamicTextBoxByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_phonenumber"), validPhone);

	}

	@Test
	public void TC_14_ThongTinLienHe_KiemTraDoDaiHoTenCuaVietNamAirLine() {

		log.info("TC_02_Step 13: CLick quay lai ");
		airTicket.clickToDynamicIcon("com.VCB:id/ivTitleLeft");

		log.info("TC_02_Step 13: CLick quay lai ");
		airTicket.clickToDynamicIcon("com.VCB:id/ivTitleLeft");

		log.info("TC_01_Step 12: Click Tim Chuyen Bay ");
		airTicket.clickToDynamicButton("Tìm chuyến bay");

		airTicket.sleep(driver, 10000);

		log.info("TC_01_Step 13: Click Chon Viet Jet ");
		airTicket.clickToDynamicFlight(0, "VN");

		log.info("TC_01_Step 14: Click Dat ve ");
		airTicket.clickToDynamicButton("Đặt vé");

		log.info("TC_03_Step 01: Dien 1 Ten khong dung dinh dang ");
		String invalidText = "ANh TA ANh TA ANh TA ANh TAANh TAANh TAANh TA ANh TAANh TA ANh TA ANh TA";
		airTicket.inputToDynamicInputBoxByLabel(invalidText, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_hoten");

		log.info("TC_07_Step 01: Check vao check box chon dung lam thong tin hanh khach bay");
		airTicket.clickToDynamicCheckBoxByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/iv_check_active");

		log.info("TC_12_Step 02: Kiem tra pop-up Email khong dung dinh dang hien thi");
		verifyEquals(airTicket.getTextInDynamicPopUp("com.VCB:id/tvTitle"), "Độ dài họ tên của Hành khách bay vượt quá quy định của hãng hàng không (tối đa 28 ký tự). Quý khách vui lòng nhập lại. Vui lòng liên hệ tổng đài 1900555520 hoặc 0962555520 để được hỗ trợ. Xin cảm ơn!");

	}

	@Test
	public void TC_15_ThongTinLienHe_KiemTraNhapDungHoTenCuaVietNamAirLine() {
		log.info("TC_15_Step 01: Dien 1 Ten khong dung dinh dang ");
		airTicket.inputToDynamicInputBoxByLabel(validName, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_hoten");

		log.info("TC_15_Step 01 : Kiem tra ten da duoc dien");
		verifyEquals(airTicket.getTextInDynamicTextBoxByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_hoten"), validName);

	}

	@Test
	public void TC_16_ThongTinLienHe_KiemTraBoChonCHeckBox() {
		log.info("TC_16_Step 01: Check vao check box chon dung lam thong tin hanh khach bay");
		airTicket.clickToDynamicCheckBoxByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/iv_check_active");

		log.info("TC_16_Step 01: Kiem tra text box ho ten");
		verifyEquals(airTicket.getTextInDynamicTextBoxAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "0", "com.VCB:id/edt_hoten"), validName);

		log.info("TC_16_Step 02: Check vao check box chon dung lam thong tin hanh khach bay");
		airTicket.uncheckToDynamicCheckBoxByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/iv_check_active");

		log.info("TC_16_Step 03: Kiem tra text box ho ten");
		verifyEquals(airTicket.getTextInDynamicTextBoxAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "0", "com.VCB:id/edt_hoten"), "Họ Đệm và Tên (ví dụ: NGUYEN VAN A)");

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
