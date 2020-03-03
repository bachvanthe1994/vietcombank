package vnpay.vietcombank.sdk.airticket;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.sdk.airTicketBooking.DynamicAirTicketBookingObjects;
import vnpay.vietcombank.sdk.airticket.data.DomesticAirTicketBooking_Data;

public class DomesticAirTicketBooking_Validation_AirTicket_Info_Details_Part_6 extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private DynamicAirTicketBookingObjects airTicket;
	String cardNumber = "888888888888888888888888888888";

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
		airTicket.clickToDynamicTextOrButtonLink(DomesticAirTicketBooking_Data.validInput.HANOI_PLACE);

		log.info("TC_01_Step 03: Click Diem Den");
		airTicket.clickToDynamicTextOrButtonLink("Điểm đến");

		log.info("TC_01_Step 04: Click TP Ho Chi Minh");
		airTicket.clickToDynamicTextOrButtonLink(DomesticAirTicketBooking_Data.validInput.HCM_PLACE);

		log.info("TC_01_Step 05: Click Ngay Đi");
		airTicket.clickToDynamicTextOrButtonLink("Ngày đi");

		log.info("TC_01_Step 06: Click Chon Ngay");
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(1), getForWardDay(1));

		log.info("TC_01_Step 07: Click xac nhan");
		airTicket.clickToDynamicButton("Xác nhận");

		log.info("TC_01_Step 09: Click Chon Tre em");
		airTicket.clickToDynamicPlusAndMinusIcon("Trẻ em (2 đến dưới 12 tuổi)", "+");

		log.info("TC_01_Step 10: Click Chon Em be");
		airTicket.clickToDynamicPlusAndMinusIcon("Em bé (Dưới 2 tuổi)", "+");

		log.info("TC_01_Step 11: Click Tim Chuyen Bay ");
		airTicket.clickToDynamicButton("Tìm chuyến bay");

		airTicket.sleep(driver, 10000);

		log.info("TC_01_Step 12: Click Chon VietNamAirLine ");
		airTicket.clickToDynamicFlight(0, "VJ");

		log.info("TC_01_Step 13: Click Dat ve ");
		airTicket.clickToDynamicButton("Đặt vé");

		log.info("TC_01_Step 15: Dien  Ten  Nguoi Lien He");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_NAME, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_hoten");

		log.info("TC_01_Step 16: Chon gioi tinh nu");
		airTicket.checkToDynamicTextOrDropDownByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/tv_NuContact");

		log.info("TC_01_Step 17: Dien Email  ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_EMAIL, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_email");

		log.info("TC_11_Step 18: Dien Phone ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_PHONE, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_phonenumber");

		log.info("TC_11_Step 19: Dien Noi dung");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.CONTENT, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_content");

		log.info("TC_01_Step 20: Kiem tra text Noi dung");
		verifyEquals(airTicket.getTextInDynamicTextBoxByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_content"), DomesticAirTicketBooking_Data.validInput.CONTENT);

	}

	@Test
	public void TC_02_ThongTinHanhKhach_ThongTinNguoiLon_BoTrongHoTen() {
		log.info("TC_02_Step 01: Click Tiep Tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_02_Step 02: Kiem Tra popup yeu cau nhap ho ten");
		verifyEquals(airTicket.getTextInDynamicPopUpAndTitle("com.VCB:id/tvTitle"), "Quý khách vui lòng nhập đầy đủ Họ và tên hành khách 1");

		log.info("TC_02_Step 03: Click Dong y");
		airTicket.clickToDynamicButton("Đồng ý");

	}

	@Test
	public void TC_03_ThongTinHanhKhach_ThongTinNguoiLon_KiemTraNhapHoTenKhongDungDinhDang() {
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
	public void TC_04_ThongTinHanhKhach_ThongTinNguoiLon_KiemTraNhapHoTenDungDinhDangVoiKyThuThuong() {
		log.info("TC_04_Step 01: Nhap ten dung dinh dang");
		airTicket.inputToDynamicInputBoxByLabelAndIndex("ANH TA", "com.VCB:id/recy_info_book", "0", "com.VCB:id/edt_hoten");

		log.info("TC_04_Step 02: Kiem tra text box ho ten");
		verifyEquals(airTicket.getTextInDynamicTextBoxAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "0", "com.VCB:id/edt_hoten"), "ANH TA");

	}

	@Test
	public void TC_05_ThongTinHanhKhach_ThongTinNguoiLon_KiemTraKhongChonGioiTinh() {
		log.info("TC_05_Step 01: Click Tiep Tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_05_Step 02: Kiem Tra popup yeu cau nhap ho ten");
		verifyEquals(airTicket.getTextInDynamicPopUpAndTitle("com.VCB:id/tvTitle"), "Quý khách vui lòng chọn giới tính của hành khách Người lớn 1.");

		log.info("TC_05_Step 03: Click Dong y");
		airTicket.clickToDynamicButton("Đồng ý");

	}

	@Test
	public void TC_06_ThongTinHanhKhach_ThongTinNguoiLon_KiemTraNhapSoTheKhongHopLe() {
		log.info("TC_06_Step 01: Chon Gioi tinh");
		airTicket.clickToDynamicTextOrDropDownByLabelAndIndex("com.VCB:id/recy_info_book", "0", "com.VCB:id/ll_NuNoiDia", "com.VCB:id/tv_NuNoiDia");

		log.info("TC_06_Step 02: Click so the thuong xuyen");
		airTicket.clickToDynamicTextOrDropDownByLabelAndIndex("com.VCB:id/recy_info_book", "0", "com.VCB:id/ll_sothe", "com.VCB:id/tv_content_price");

		String invaidCardNumber = "888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888";
		log.info("TC_06_Step 03: Nhap card lon hon 50 ky tu ");
		airTicket.inputToDynamicInputBoxByLabelAndIndex(invaidCardNumber, "com.VCB:id/recy_info_book", "0", "com.VCB:id/edt_CardNo");

		log.info("TC_06_Step 04: Kiem tra chi nhap duoc 50 ky tu");
		verifyEquals(airTicket.getTextInDynamicTextBoxAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "0", "com.VCB:id/edt_CardNo").length(), 50);

		log.info("TC_06_Step 05: Click Quay lai");
		airTicket.clickToDynamicIcon("com.VCB:id/ivTitleLeft");
	}

	@Test
	public void TC_07_ThongTinHanhKhach_ThongTinNguoiLon_KiemTraManHinhChonGoiHanhLyVaDong() {
		log.info("TC_01_Step 12: Click Chon VietJet ");
		airTicket.clickToDynamicFlight(0, "VJ");

		log.info("TC_01_Step 13: Click Dat ve ");
		airTicket.clickToDynamicButton("Đặt vé");

		log.info("TC_01_Step 14: Click hanh ly chieu di");
		airTicket.clickToDynamicTextOrDropDownByLabelAndIndex("com.VCB:id/recy_info_book", "0", "com.VCB:id/hanhlychieudi", "com.VCB:id/tv_content_price");

		log.info("TC_01_Step 14: Kiem tra title Chon hanh ly hien thi");
		verifyEquals(airTicket.getTextInDynamicPopUpAndTitle("com.VCB:id/tv_title_dialog"), "Chọn hành lý");

		log.info("TC_01_Step 14:  Kiem tra icon Dong hien thi ");
		verifyTrue(airTicket.isDynamicIconDisplayed("com.VCB:id/iv_close"));

		log.info("TC_01_Step 14: Kiem tra co goi hanh ly ");
		verifyTrue(airTicket.isDynamicTextByIdDisplayed("com.VCB:id/tv_content_price"));

		log.info("TC_01_Step 14: Dong pop-up");
		airTicket.clickToDynamicIcon("com.VCB:id/iv_close");
	}

	@Test
	public void TC_08_ThongTinHanhKhach_ThongTinNguoiLon_KiemTraChonGoiHanhLy() {
		for (int i = 0; i < 2; i++) {

			log.info("TC_08_Step 01: Click hanh ly chieu di");
			airTicket.clickToDynamicTextOrDropDownByLabelAndIndex("com.VCB:id/recy_info_book", "0", "com.VCB:id/hanhlychieudi", "com.VCB:id/tv_content_price");

			log.info("TC_08_Step 02: Click hanh ly chieu di");
			String selectedPackage = airTicket.getTextInOneOfDropDownValue(i, "com.VCB:id/tv_content_price");

			log.info("TC_08_Step 03: Click hanh ly chieu di");
			String[] packageName = selectedPackage.split(" - ");

			airTicket.clickToDynamicPackage(i, "com.VCB:id/tv_content_price");

			log.info("TC_08_Step 04: Click hanh ly chieu di");
			verifyEquals(airTicket.getTextInDynamicTextViewAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "0", "com.VCB:id/hanhlychieudi", "com.VCB:id/tv_content_price"), packageName[0]);
		}
		log.info("TC_08_Step 05: CLick quay lai ");
		airTicket.clickToDynamicIcon("com.VCB:id/ivTitleLeft");
	}

	@Test
	public void TC_09_ThongTinHanhKhach_ThongTinTreEm_ThongTinTreEm_KiemTraBoTrongHoTen() {
		log.info("TC_09_Step 01: Click Chon VietNamAirLine ");
		airTicket.clickToDynamicFlight(0, "VN");

		log.info("TC_09_Step 02: Click Dat ve ");
		airTicket.clickToDynamicButton("Đặt vé");

		log.info("TC_09_Step 03: Dien  Ten nguoi lien he ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_NAME, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_hoten");

		log.info("TC_09_Step 04: Chon gioi tinh nu");
		airTicket.checkToDynamicTextOrDropDownByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/tv_NuContact");

		log.info("TC_09_Step 05: Dien Email  ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_EMAIL, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_email");

		log.info("TC_09_Step 06: Dien Phone ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_PHONE, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_phonenumber");

		log.info("TC_09_Step 07: Dien Noi Dung ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.CONTENT, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_content");

		log.info("TC_09_Step 08: Chon Gioi Tinh Nguoi Lon 1 ");
		airTicket.clickToDynamicTextOrDropDownByLabelAndIndex("com.VCB:id/recy_info_book", "0", "com.VCB:id/ll_NuNoiDia", "com.VCB:id/tv_NuNoiDia");

		log.info("TC_09_Step 09: Dien ten nguoi lon 1 ");
		airTicket.inputToDynamicInputBoxByLabelAndIndex(DomesticAirTicketBooking_Data.validInput.ADULT_NAME, "com.VCB:id/recy_info_book", "0", "com.VCB:id/edt_hoten");

		log.info("TC_09_Step 10: Chon gioi tinh");
		airTicket.clickToDynamicTextOrDropDownByLabelAndIndex("com.VCB:id/recy_info_book", "0", "com.VCB:id/ll_NuNoiDia", "com.VCB:id/tv_NuNoiDia");

		log.info("TC_09_Step 12: Click so the");
		airTicket.clickToDynamicTextOrDropDownByLabelAndIndex("com.VCB:id/recy_info_book", "0", "com.VCB:id/ll_sothe", "com.VCB:id/tv_content_price");

		log.info("TC_09_Step 13: Nhap Card Number");
		airTicket.inputToDynamicInputBoxByLabelAndIndex(cardNumber, "com.VCB:id/recy_info_book", "0", "com.VCB:id/edt_CardNo");

		log.info("TC_09_Step 13: An Ban Phim");
		airTicket.hideKeyBoard(driver);

		log.info("TC_09_Step 14: Click Tiep Tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_09_Step 15: Kiem Tra popup yeu cau nhap ho ten tre em");
		verifyEquals(airTicket.getTextInDynamicPopUpAndTitle("com.VCB:id/tvTitle"), "Quý khách vui lòng nhập đầy đủ Họ và tên hành khách 2");

		log.info("TC_09_Step 16: Click Dong y");
		airTicket.clickToDynamicButton("Đồng ý");

	}

	@Test
	public void TC_10_ThongTinHanhKhach_ThongTinTreEm_KiemTraNhapHoTenKhongDungDinhDang() {
		log.info("TC_10_Step 01: Nhap tieng  viet co dau");
		airTicket.inputToDynamicInputBoxByLabelAndIndex("Tiếng việt có dấu", "com.VCB:id/recy_info_book", "1", "com.VCB:id/edt_hoten");

		log.info("TC_10_Step 02: Kiem tra text box ho ten");
		verifyEquals(airTicket.getTextInDynamicTextBoxAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "1", "com.VCB:id/edt_hoten"), "Tieng viet co dau");

		log.info("TC_10_Step 03: Nhap so co dau");
		airTicket.inputToDynamicInputBoxByLabelAndIndex("1234", "com.VCB:id/recy_info_book", "1", "com.VCB:id/edt_hoten");

		log.info("TC_10_Step 04: Kiem tra text box ho ten");
		verifyEquals(airTicket.getTextInDynamicTextBoxAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "1", "com.VCB:id/edt_hoten"), "Họ Đệm và Tên (ví dụ: NGUYEN VAN A)");

		log.info("TC_10_Step 05: Nhap ky tu dac biet ");
		airTicket.inputToDynamicInputBoxByLabelAndIndex("@$$$s", "com.VCB:id/recy_info_book", "1", "com.VCB:id/edt_hoten");

		log.info("TC_10_Step 06: Kiem tra text box ho ten");
		verifyEquals(airTicket.getTextInDynamicTextBoxAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "1", "com.VCB:id/edt_hoten"), "Họ Đệm và Tên (ví dụ: NGUYEN VAN A)");
	}

	@Test
	public void TC_11_ThongTinHanhKhach_ThongTinTreEm_KiemTraNhapHoTenDungDinhDang() {

		log.info("TC_11_Step 01: Nhap tieng  viet co dau");
		airTicket.inputToDynamicInputBoxByLabelAndIndex(DomesticAirTicketBooking_Data.validInput.CHILD_NAME, "com.VCB:id/recy_info_book", "1", "com.VCB:id/edt_hoten");

		log.info("TC_11_Step 02: Kiem tra text box ho ten");
		verifyEquals(airTicket.getTextInDynamicTextBoxAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "1", "com.VCB:id/edt_hoten"), DomesticAirTicketBooking_Data.validInput.CHILD_NAME);

	}

	@Test
	public void TC_12_ThongTinHanhKhach_ThongTinTreEm_KiemTraKhongChonGioiTinh() {
		log.info("TC_12_Step 01: Click Tiep Tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_12_Step 02: Kiem Tra popup yeu cau nhap ho ten");
		verifyEquals(airTicket.getTextInDynamicPopUpAndTitle("com.VCB:id/tvTitle"), "Quý khách vui lòng chọn giới tính của hành khách Trẻ em 1.");

		log.info("TC_12_Step 03: Click Dong y");
		airTicket.clickToDynamicButton("Đồng ý");

		log.info("TC_12_Step 03: Chon Gioi Tinh Nu");
		airTicket.clickToDynamicTextOrDropDownByLabelAndIndex("com.VCB:id/recy_info_book", "1", "com.VCB:id/ll_NuNoiDia", "com.VCB:id/tv_NuNoiDia");

	}

	@Test
	public void TC_13ThongTinHanhKhach_ThongTinTreEm_KiemTraChonNgaySinh() {

		log.info("TC_13_Step 01: Lay time cach hien tai 12 năm ");
		LocalDate now = LocalDate.now();
		LocalDate date = now.minusYears(12);
		String currentfocusday = date.plusDays(2).getDayOfMonth() + "";
		LocalDate curentfocusDate = date.plusDays(2);
		String[] day = curentfocusDate.toString().split("-");
		if (!currentfocusday.equals("1")) {
			log.info("TC_13_Step 02: Click chon ngay sinh");
			airTicket.clickToDynamicTextOrDropDownByLabelAndIndex("com.VCB:id/recy_info_book", "1", "com.VCB:id/birthday_child", "com.VCB:id/tv_content_price");

			log.info("TC_13_Step 03: Chon ngay nho hon 12 nam ");
			airTicket.clickToDynamicDateInCalendar(Integer.parseInt(currentfocusday) - 1 + "");

			log.info("TC_13_Step 04: Click OK");
			airTicket.clickToDynamicButton("OK");

			log.info("TC_13_Step 05: Kiem tra ngay sinh la ngay mac dinh cach hien tai 12 nam");
			verifyEquals(airTicket.getTextInDynamicTextViewAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "1", "com.VCB:id/birthday_child", "com.VCB:id/tv_content_price"), day[2] + "/" + day[1] + "/" + day[0]);

		}
		log.info("TC_13_Step 06: Click hanh ly chieu di");
		airTicket.clickToDynamicTextOrDropDownByLabelAndIndex("com.VCB:id/recy_info_book", "1", "com.VCB:id/birthday_child", "com.VCB:id/tv_content_price");

		airTicket.clickToDynamicDateInCalendar(currentfocusday);
		log.info("TC_13_Step 07: Click OK");
		airTicket.clickToDynamicButton("OK");

		log.info("TC_13_Step 08: Kiem tra text ngay sinh");
		verifyEquals(airTicket.getTextInDynamicTextViewAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "1", "com.VCB:id/birthday_child", "com.VCB:id/tv_content_price"), day[2] + "/" + day[1] + "/" + day[0]);

		log.info("TC_13_Step 09:  Lay ngay cach ngay hien tai 2 nam");
		LocalDate twoYearAgo = now.minusYears(2);
		LocalDate curentfocusDate1 = twoYearAgo.plusDays(1);
		LocalDate newDate = now.plusDays(2);
		String[] dayAftTer = newDate.toString().split("-");

		log.info("TC_13_Step 09: Lay ngay cuoi cung cua thang");
		Calendar calendar = Calendar.getInstance();
		int lastDate = calendar.getActualMaximum(Calendar.DATE);
		calendar.set(Calendar.DATE, lastDate);
		int lastDay = calendar.get(Calendar.DAY_OF_MONTH);

		if (!(curentfocusDate1.getDayOfMonth() + "").equals(lastDay + "")) {
			log.info("TC_13_Step 10: Click ngay sinh");
			airTicket.clickToDynamicTextOrDropDownByLabelAndIndex("com.VCB:id/recy_info_book", "1", "com.VCB:id/birthday_child", "com.VCB:id/tv_content_price");

			log.info("TC_13_Step 11: Click chon nam");
			airTicket.clickToDynamicTextByID("android:id/date_picker_header_year");

			log.info("TC_13_Step 12: Chon Nam cach nam hien tai 2 nam");
			airTicket.clickToDynamicTextOrButtonLink(twoYearAgo.getYear() + "");

			log.info("TC_13_Step 13: Chon ngay trong khoang khong cho  phep");
			airTicket.clickToDynamicDateInCalendar(curentfocusDate1.getDayOfMonth() + 1 + "");

			log.info("TC_13_Step 14: Click OK");
			airTicket.clickToDynamicButton("OK");

			log.info("TC_13_Step 15: Kiem tra text ngay sinh");
			verifyEquals(airTicket.getTextInDynamicTextViewAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "1", "com.VCB:id/birthday_child", "com.VCB:id/tv_content_price"), dayAftTer[2] + "/" + dayAftTer[1] + "/" + dayAftTer[0]);

		}
	}

	@Test
	public void TC_14ThongTinHanhKhach_ThongTinTreEm_KiemTraMaxSoTheKhachHangThuongXuyen() {

		log.info("TC_14_Step 01: Click chon text so the");
		airTicket.clickToDynamicTextOrDropDownByLabelAndIndex("com.VCB:id/recy_info_book", "1", "com.VCB:id/ll_sothe", "com.VCB:id/tv_content_price");

		String invaidCardNumber = "888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888";
		log.info("TC_14_Step 02: Nhap ky tu thuong");
		airTicket.inputToDynamicInputBoxByLabelAndIndex(invaidCardNumber, "com.VCB:id/recy_info_book", "1", "com.VCB:id/edt_CardNo");

		log.info("TC_14_Step 03: Kiem tra text box ho ten");
		verifyEquals(airTicket.getTextInDynamicTextBoxAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "1", "com.VCB:id/edt_CardNo").length(), 50);

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();
	}

}
