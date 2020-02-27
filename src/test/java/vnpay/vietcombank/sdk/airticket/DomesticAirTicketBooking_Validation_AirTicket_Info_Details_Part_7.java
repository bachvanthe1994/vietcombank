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
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.sdk.airTicketBooking.DynamicAirTicketBookingObjects;
import vnpay.vietcombank.sdk.airticket.data.DomesticAirTicketBooking_Data;

public class DomesticAirTicketBooking_Validation_AirTicket_Info_Details_Part_7 extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private DynamicAirTicketBookingObjects airTicket;

	private String ticketPrice;
	private String flightCode;
	private String departureTime;
	private String arrivalTime;
	private String duration;
	private String packagePrice1;
	private String packagePrice2;
	Long subTotal1;
	Long subTotal2;
	Long subTotal3;

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
	public void TC_01_ThongTinLienHe_ThongTinTreEm_KiemTraManHinhChonGoiHanhLy() {
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
		flightCode = airTicket.getAirTicketInfoByFlightCode1Way("com.VCB:id/recycler_view_one", "QH", "com.VCB:id/tv_flightNo");
		departureTime = airTicket.getAirTicketInfoByFlightCode1Way("com.VCB:id/recycler_view_one", "QH", "com.VCB:id/tv_time_deptTime");
		arrivalTime = airTicket.getAirTicketInfoByFlightCode1Way("com.VCB:id/recycler_view_one", "QH", "com.VCB:id/tv_time_deptTime_arrival");
		duration = airTicket.getAirTicketInfoByFlightCode1Way("com.VCB:id/recycler_view_one", "QH", "com.VCB:id/tv_timeduration");

		airTicket.clickToDynamicFlight(0, "QH");
		ticketPrice = airTicket.getDynamicTextByID("com.VCB:id/tv_amount_flight_selected");

		log.info("TC_01_Step 13: Click Dat ve ");
		airTicket.clickToDynamicButton("Đặt vé");

		log.info("TC_01_Step 15: Dien  Ten Nguoi lon ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_NAME, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_hoten");

		log.info("TC_01_Step 16: Chon gioi tinh nu");
		airTicket.checkToDynamicTextOrDropDownByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/tv_NuContact");

		log.info("TC_01_Step 17: Dien Email  ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_EMAIL, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_email");

		log.info("TC_11_Step 18: Dien Phone ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_PHONE, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_phonenumber");

		log.info("TC_11_Step 19: Dien Noi dung");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.CONTENT, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_content");

		log.info("TC_11_Step 09: Dien ten nguoi lon 1 ");
		airTicket.inputToDynamicInputBoxByLabelAndIndex(DomesticAirTicketBooking_Data.validInput.ADULT_NAME, "com.VCB:id/recy_info_book", "0", "com.VCB:id/edt_hoten");

		airTicket.clickToDynamicTextOrDropDownByLabelAndIndex("com.VCB:id/recy_info_book", "0", "com.VCB:id/ll_NuNoiDia", "com.VCB:id/tv_NuNoiDia");

		log.info("TC_01_Step 14: Click hanh ly chieu di");
		airTicket.clickToDynamicTextOrDropDownByLabelAndIndex("com.VCB:id/recy_info_book", "0", "com.VCB:id/hanhlychieudi", "com.VCB:id/tv_content_price");

		log.info("TC_02_Step 02: Click chon goi hanh ly 1");
		String selectedPackage = airTicket.getTextInOneOfDropDownValue(1, "com.VCB:id/tv_content_price");

		log.info("TC_02_Step 03: Click hanh ly chieu di");
		String[] packageName = selectedPackage.split(" - ");

		log.info("TC_01_Step 14: Chon goi hanh ly 1");
		airTicket.clickToDynamicPackage(1, "com.VCB:id/tv_content_price");

		packagePrice1 = packageName[1];

		log.info("TC_11_Step 01: Nhap ten tre em");
		airTicket.inputToDynamicInputBoxByLabelAndIndex(DomesticAirTicketBooking_Data.validInput.CHILD_NAME, "com.VCB:id/recy_info_book", "1", "com.VCB:id/edt_hoten");

		log.info("TC_11_Step 01: Chon Gioi tinh tre em");
		airTicket.clickToDynamicTextOrDropDownByLabelAndIndex("com.VCB:id/recy_info_book", "1", "com.VCB:id/ll_NuNoiDia", "com.VCB:id/tv_NuNoiDia");

		log.info("TC_01_Step 14: Click Ngay sinh tre em");
		airTicket.clickToDynamicTextOrDropDownByLabelAndIndex("com.VCB:id/recy_info_book", "1", "com.VCB:id/birthday_child", "com.VCB:id/tv_content_price");

		log.info("TC_02_Step 03: Click OK");
		airTicket.clickToDynamicButton("OK");

		log.info("TC_01_Step 14: Click hanh ly chieu di");
		airTicket.clickToDynamicTextOrDropDownByLabelAndIndex("com.VCB:id/recy_info_book", "1", "com.VCB:id/hanhlychieudi", "com.VCB:id/tv_content_price");

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
	public void TC_02_ThongTinHanhKhach_ThongTinTreEm_KiemTraChonGoiHanhLy() {
		for (int i = 0; i < 2; i++) {

			log.info("TC_02_Step 01: Click hanh ly chieu di");
			airTicket.clickToDynamicTextOrDropDownByLabelAndIndex("com.VCB:id/recy_info_book", "1", "com.VCB:id/hanhlychieudi", "com.VCB:id/tv_content_price");

			log.info("TC_02_Step 02: Click hanh ly chieu di");
			String selectedPackage = airTicket.getTextInOneOfDropDownValue(i, "com.VCB:id/tv_content_price");

			log.info("TC_02_Step 03: Click hanh ly chieu di");
			String[] packageName = selectedPackage.split(" - ");

			airTicket.clickToDynamicPackage(i, "com.VCB:id/tv_content_price");

			log.info("TC_02_Step 04: Click hanh ly chieu di");
			verifyEquals(airTicket.getTextInDynamicTextViewAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "1", "com.VCB:id/hanhlychieudi", "com.VCB:id/tv_content_price"), packageName[0]);

			packagePrice2 = packageName[1];
		}

	}

	@Test
	public void TC_03_ThongTinHanhKhach_ThongTinEmbe_BoTrongTenEmbe() {
		log.info("TC_03_Step 01: Click Tiep Tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_03_Step 02: Kiem Tra popup yeu cau nhap ho ten tre em");
		verifyEquals(airTicket.getTextInDynamicPopUpAndTitle("com.VCB:id/tvTitle"), "Quý khách vui lòng nhập đầy đủ Họ và tên hành khách 3");

		log.info("TC_03_Step 03: Click Dong y");
		airTicket.clickToDynamicButton("Đồng ý");

	}

	@Test
	public void TC_04_ThongTinHanhKhach_ThongTinEmB3_KiemTraNhapHoTenKhongDungDinhDang() {
		log.info("TC_04_Step 01: Nhap tieng  viet co dau");
		airTicket.inputToDynamicInputBoxByLabelAndIndex("Tiếng việt có dấu", "com.VCB:id/recy_info_book", "2", "com.VCB:id/edt_hoten");

		log.info("TC_04_Step 02: Kiem tra text box ho ten");
		verifyEquals(airTicket.getTextInDynamicTextBoxAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "2", "com.VCB:id/edt_hoten"), "Tieng viet co dau");

		log.info("TC_04_Step 03: Nhap so co dau");
		airTicket.inputToDynamicInputBoxByLabelAndIndex("1234", "com.VCB:id/recy_info_book", "2", "com.VCB:id/edt_hoten");

		log.info("TC_04_Step 04: Kiem tra text box ho ten");
		verifyEquals(airTicket.getTextInDynamicTextBoxAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "2", "com.VCB:id/edt_hoten"), "Họ Đệm và Tên (ví dụ: NGUYEN VAN A)");

		log.info("TC_04_Step 05: Nhap ky tu dac biet ");
		airTicket.inputToDynamicInputBoxByLabelAndIndex("@$$$s", "com.VCB:id/recy_info_book", "2", "com.VCB:id/edt_hoten");

		log.info("TC_04_Step 06: Kiem tra text box ho ten");
		verifyEquals(airTicket.getTextInDynamicTextBoxAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "2", "com.VCB:id/edt_hoten"), "Họ Đệm và Tên (ví dụ: NGUYEN VAN A)");
	}

	@Test
	public void TC_05_ThongTinHanhKhach_ThongTinEmBe_KiemTraNhapHoTenDungDinhDang() {

		log.info("TC_05_Step 01: Nhap tieng  viet co dau");
		airTicket.inputToDynamicInputBoxByLabelAndIndex(DomesticAirTicketBooking_Data.validInput.BABY_NAME, "com.VCB:id/recy_info_book", "2", "com.VCB:id/edt_hoten");

		log.info("TC_05_Step 02: Kiem tra text box ho ten");
		verifyEquals(airTicket.getTextInDynamicTextBoxAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "2", "com.VCB:id/edt_hoten"), DomesticAirTicketBooking_Data.validInput.BABY_NAME);

	}

	@Test
	public void TC_06_ThongTinHanhKhach_ThongTinEmBe_KiemTraKhongChonGioiTinh() {
		log.info("TC_06_Step 01: Click Tiep Tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_06_Step 02: Kiem Tra popup yeu cau nhap ho ten");
		verifyEquals(airTicket.getTextInDynamicPopUpAndTitle("com.VCB:id/tvTitle"), "Quý khách vui lòng chọn giới tính của hành khách Em bé 1.");

		log.info("TC_06_Step 03: Click Dong y");
		airTicket.clickToDynamicButton("Đồng ý");

		log.info("TC_06_Step 03: Chon Gioi Tinh Nu");
		airTicket.clickToDynamicTextOrDropDownByLabelAndIndex("com.VCB:id/recy_info_book", "2", "com.VCB:id/ll_NuNoiDia", "com.VCB:id/tv_NuNoiDia");

	}

	@Test
	public void TC_07_ThongTinHanhKhach_ThongTinEmBe_KiemTraKhongChonNgaySinh() {
		log.info("TC_07_Step 01: Click Tiep Tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_07_Step 02: Kiem Tra popup yeu cau nhap ho ten tre em");
		verifyEquals(airTicket.getTextInDynamicPopUpAndTitle("com.VCB:id/tvTitle"), "Quý khách chưa chọn ngày sinh của em bé. Vui lòng kiểm tra lại");

		log.info("TC_07_Step 03: Click Dong y");
		airTicket.clickToDynamicButton("Đồng ý");

	}

	@Test
	public void TC_08_ThongTinHanhKhach_ThongTinEmBe_KiemTraChonNgaySinh() {
		log.info("TC_08_Step 01: Lay time cach hien tai 2 năm ");
		LocalDate now = LocalDate.now();
		LocalDate date = now.minusYears(2);
		String currentfocusday = date.plusDays(2).getDayOfMonth() + "";
		LocalDate curentfocusDate = date.plusDays(2);
		String[] day = curentfocusDate.toString().split("-");

		if ((currentfocusday != "1")) {
			log.info("TC_08_Step 10: Click ngay sinh");
			airTicket.clickToDynamicTextOrDropDownByLabelAndIndex("com.VCB:id/recy_info_book", "2", "com.VCB:id/birthday_child", "com.VCB:id/tv_content_price");

			log.info("TC_08_Step 03: Chon ngay nho hon 2 nam ");
			airTicket.clickToDynamicDateInCalendar(Integer.parseInt(currentfocusday) - 1 + "");

			log.info("TC_08_Step 04: Click OK");
			airTicket.clickToDynamicButton("OK");

			log.info("TC_08_Step 15: Kiem tra text ngay sinh");
			verifyEquals(airTicket.getTextInDynamicTextViewAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "2", "com.VCB:id/birthday_child", "com.VCB:id/tv_content_price"), day[2] + "/" + day[1] + "/" + day[0]);

		}
		log.info("TC_08_Step 06: Click hanh ly chieu di");
		airTicket.clickToDynamicTextOrDropDownByLabelAndIndex("com.VCB:id/recy_info_book", "2", "com.VCB:id/birthday_child", "com.VCB:id/tv_content_price");

		airTicket.clickToDynamicDateInCalendar(currentfocusday);
		log.info("TC_08_Step 07: Click OK");
		airTicket.clickToDynamicButton("OK");

		log.info("TC_08_Step 08: Kiem tra text ngay sinh");
		verifyEquals(airTicket.getTextInDynamicTextViewAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "2", "com.VCB:id/birthday_child", "com.VCB:id/tv_content_price"), day[2] + "/" + day[1] + "/" + day[0]);

		log.info("TC_08_Step 09: Lay ngay cuoi cung cua thang");
		Calendar calendar = Calendar.getInstance();
		int lastDate = calendar.getActualMaximum(Calendar.DATE);
		calendar.set(Calendar.DATE, lastDate);
		int lastDay = calendar.get(Calendar.DAY_OF_MONTH);
		String currentfocusday1 = now.plusDays(1).getDayOfMonth() + "";

		if (currentfocusday1 != lastDay + "") {
			log.info("TC_08_Step 10: Click ngay sinh");
			airTicket.clickToDynamicTextOrDropDownByLabelAndIndex("com.VCB:id/recy_info_book", "2", "com.VCB:id/birthday_child", "com.VCB:id/tv_content_price");

			log.info("TC_08_Step 11: Click chon nam");
			airTicket.clickToDynamicTextByID("android:id/date_picker_header_year");

			log.info("TC_08_Step 12: Chon  nam hien tai");
			airTicket.clickToDynamicTextOrButtonLink(now.getYear() + "");

			log.info("TC_08_Step 13: Chon ngay trong khoang khong cho  phep, luu y do cong them ngay bay nen ngay sinh se lech today 1 ngay");
			airTicket.clickToDynamicDateInCalendar(now.plusDays(1).getDayOfMonth() + 1 + "");

			log.info("TC_08_Step 14: Click OK");
			airTicket.clickToDynamicButton("OK");

			log.info("TC_08_Step 08: Kiem tra text ngay sinh");
			verifyEquals(airTicket.getTextInDynamicTextViewAirTicketInfoOfCustomer("com.VCB:id/recy_info_book", "2", "com.VCB:id/birthday_child", "com.VCB:id/tv_content_price"), day[2] + "/" + day[1] + "/" + now.getYear());

		}
	}

	@Test
	public void TC_09_KiemTraManHinhXacNhanChuyenBay() {
		log.info("TC_09_Step 0: Click Tiep tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_09_Step 01: Kiem tra man hinh xac nhan chuyen bay hien thi");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvTitle1"), "Xác nhận chuyến bay");

		log.info("TC_09_Step 02: Kiem tra icon Back hien thi");
		verifyTrue(airTicket.isDynamicIconDisplayed("com.VCB:id/ivTitleLeft"));

		log.info("TC_09_Step 03: Kiem tra icon Call hien thi");
		verifyTrue(airTicket.isDynamicIconDisplayed("com.VCB:id/ivTitleRight"));

		log.info("TC_09_Step 04: Kiem tra text Thong tin khach hang hie thi");
		verifyTrue(airTicket.isDynamicTextDisplayed("THÔNG TIN HÀNH KHÁCH"));

		log.info("TC_09_Step 05: Kiem tra text nguoi lon 1 hien thi");
		verifyTrue(airTicket.isDynamicTextDisplayed("Người lớn 1"));

		log.info("TC_09_Step 06: Kiem tra ho ten nguoi lon 1");
		verifyTrue(airTicket.isDynamicPlaceAndCustomerNameDisplayed("Họ tên", DomesticAirTicketBooking_Data.validInput.ADULT_NAME));

		log.info("TC_09_Step 07: Kiem tra text tre em 1 hien thi");
		verifyTrue(airTicket.isDynamicTextDisplayed("Trẻ em 1"));

		log.info("TC_09_Step 08: Kiem tra ho ten tre em hien thi");
		verifyTrue(airTicket.isDynamicPlaceAndCustomerNameDisplayed("Họ tên", DomesticAirTicketBooking_Data.validInput.CHILD_NAME));

		log.info("TC_09_Step 09: Kiem tra em be 1 hien thi");
		verifyTrue(airTicket.isDynamicTextDisplayed("Em bé 1"));

		log.info("TC_09_Step 10: Kiem tra ho ten em be hien thi");
		verifyTrue(airTicket.isDynamicPlaceAndCustomerNameDisplayed("Họ tên", DomesticAirTicketBooking_Data.validInput.BABY_NAME));

		log.info("TC_09_Step 11: Kiem tra diem khoi hanh hien thi");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvTextttdiadiem1"), DomesticAirTicketBooking_Data.validInput.HANOI_PLACE);

		log.info("TC_09_Step 12: Kiem tra ma diem khoi hanh hien thi");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvTextttdiadiem2"), DomesticAirTicketBooking_Data.validInput.HANOI_CODE);

		log.info("TC_09_Step 13: Kiem tra diem den hien thi");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvTextttdiadiem3"), DomesticAirTicketBooking_Data.validInput.HCM_PLACE);

		log.info("TC_09_Step 14: Kiem tra code diem den hien thi");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvTextttdiadiem4"), DomesticAirTicketBooking_Data.validInput.HCM_CODE);

		log.info("TC_09_Step 15: Kiem tra icon dia diem hien thi");
		verifyTrue(airTicket.isDynamicIconDisplayed("com.VCB:id/ivttdiadiem1"));

		log.info("TC_09_Step 16: Kiem tra icon mui ten");
		verifyTrue(airTicket.isDynamicIconDisplayed("com.VCB:id/ivLeft"));

		log.info("TC_09_Step 17: Kiem tra text hanh trinh hien thi");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvTextLeft1"), "HÀNH TRÌNH");

		log.info("TC_09_Step 18: Kiem tra ");
		verifyTrue(airTicket.isDynamicIconDisplayed("com.VCB:id/iv_right"));

		log.info("TC_09_Step 19: Kiem tra ma chuyen bay hien thi");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvFlightId"), flightCode);

		log.info("TC_09_Step 20: Kiem tra ngay khoi hanh");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvTextLeft2"), airTicket.getDayInWeek(getForwardDate(1)) + " " + getForwardDate(1));

		log.info("TC_09_Step 22: Kiem tra ngay den");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvTextRight1"), airTicket.getDayInWeek(getForwardDate(1)) + " " + getForwardDate(1));

		log.info("TC_09_Step 23: Kiem tra gio khoi hanh");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvTextLeft3"), DomesticAirTicketBooking_Data.validInput.HANOI_CODE + " " + departureTime);

		log.info("TC_09_Step 24: Kiem tra gio den");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvTextRight2"), DomesticAirTicketBooking_Data.validInput.HCM_CODE + " " + arrivalTime);

		log.info("TC_09_Step 25: Kiem tra thoi gian bay");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvDuration"), duration);

		log.info("TC_09_Step 26: Lay tong so tien");
		long ticketPrice1 = convertMoneyToLong(ticketPrice, "VND");
		long packagePriceforAdult = convertMoneyToLong(packagePrice1, "VND");
		long packagePriceforChild = convertMoneyToLong(packagePrice2, "VND");
		String totalMoney = addCommasToLong(ticketPrice1 + packagePriceforAdult + packagePriceforChild + "");

		log.info("TC_09_Step 27: Kiem tra tong so tien hien thi dung");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Tổng tiền", "com.VCB:id/tvText2"), totalMoney + " VND");

		log.info("TC_09_Step 28: Kiem tra text da bao gom  chi phi khac hien thi");
		verifyTrue(airTicket.isDynamicTextDisplayed("Đã bao gồm các chi phí khác"));

		log.info("TC_09_Step 29: Kiem tra o nhap ma giam gia");
		verifyEquals(airTicket.getTextInDynamicTextBox("com.VCB:id/edtPromotion"), "Nhập mã giảm giá (nếu có)");

		log.info("TC_09_Step 30: Kiem tra text dieu khoan dat ve");
		verifyTrue(airTicket.isDynamicTextDisplayed("Tôi đồng ý với Điều kiện điều khoản đặt vé."));

		log.info("TC_09_Step 31: Kiem tra nut thanh toan hien thi");
		verifyTrue(airTicket.isDynamicButtonDisplayed("Thanh toán"));

	}

	@Test
	public void TC_10_XacNhanChuyenBayKhongCheckDongY() {
		log.info("TC_10_Step 01: Click Tiep tuc");
		airTicket.clickToDynamicButton("Thanh toán");

		log.info("TC_10_Step 02: Kiem Tra popup yeu cau nhap ho ten tre em");
		verifyEquals(airTicket.getTextInDynamicPopUpAndTitle("com.VCB:id/tvTitle"), "Quý khách chưa đồng ý với Điều khoản và điều kiện đặt vé. Vui lòng kiểm tra lại.");

		log.info("TC_10_Step 03: Click Dong y");
		airTicket.clickToDynamicButton("Đồng ý");

	}

	@Test
	public void TC_11_KiemTraDropDownIconTaiNguoiLon1() {

		log.info("TC_11_Step 01: Click Dropdown Icon Nguoi lon 1");
		airTicket.clickToDynamicIconByLabel("Người lớn 1", "com.VCB:id/ivImage");

		log.info("TC_11_Step 02: Kiem tra text gia ve nguoi lon 1 hien thi");
		verifyTrue(airTicket.isDynamicPaymentInfoByName(DomesticAirTicketBooking_Data.validInput.ADULT_NAME, "Giá vé"));
		Long adultPrice = convertMoneyToLong(airTicket.getTextAmountOfMoneyInPayment(DomesticAirTicketBooking_Data.validInput.ADULT_NAME, "Giá vé"), "VND");

		log.info("TC_11_Step 03: Kiem tra text Phi Va Thue nguoi lon 1 hien thi");
		verifyTrue(airTicket.isDynamicPaymentInfoByName(DomesticAirTicketBooking_Data.validInput.ADULT_NAME, "Phí và thuế"));
		Long adultTax = convertMoneyToLong(airTicket.getTextAmountOfMoneyInPayment(DomesticAirTicketBooking_Data.validInput.ADULT_NAME, "Phí và thuế"), "VND");

		log.info("TC_11_Step 04: Kiem tra text Phi Dai Ly Ban ve nguoi lon 1 hien thi");
		verifyTrue(airTicket.isDynamicPaymentInfoByName(DomesticAirTicketBooking_Data.validInput.ADULT_NAME, "Phí đại lý bán vé"));
		Long adultAgentFee = convertMoneyToLong(airTicket.getTextAmountOfMoneyInPayment(DomesticAirTicketBooking_Data.validInput.ADULT_NAME, "Phí đại lý bán vé"), "VND");

		log.info("TC_11_Step 04: Kiem tra text Phi Hanh ly ve nguoi lon 1 hien thi");
		verifyTrue(airTicket.isDynamicPaymentInfoByName(DomesticAirTicketBooking_Data.validInput.ADULT_NAME, "Hành lý chiều đi"));
		Long packageFee = convertMoneyToLong(airTicket.getTextAmountOfMoneyInPayment(DomesticAirTicketBooking_Data.validInput.ADULT_NAME, "Hành lý chiều đi").split(" - ")[1], "VND");

		subTotal1 = adultPrice + adultTax + adultAgentFee + packageFee;
	}

	@Test
	public void TC_12_KiemTraDropDownIconTaiTreEm1() {
		log.info("TC_12_Step 01: Click Dropdown Icon Nguoi lon 1");
		airTicket.clickToDynamicIconByLabel("Trẻ em 1", "com.VCB:id/ivImage");

		log.info("TC_12_Step 02: Kiem tra text gia ve nguoi lon 1 hien thi");
		verifyTrue(airTicket.isDynamicPaymentInfoByName(DomesticAirTicketBooking_Data.validInput.CHILD_NAME, "Giá vé"));
		Long childPrice = convertMoneyToLong(airTicket.getTextAmountOfMoneyInPayment(DomesticAirTicketBooking_Data.validInput.CHILD_NAME, "Giá vé"), "VND");

		log.info("TC_12_Step 03: Kiem tra text Phi Va Thue nguoi lon 1 hien thi");
		verifyTrue(airTicket.isDynamicPaymentInfoByName(DomesticAirTicketBooking_Data.validInput.CHILD_NAME, "Phí và thuế"));
		Long childTax = convertMoneyToLong(airTicket.getTextAmountOfMoneyInPayment(DomesticAirTicketBooking_Data.validInput.CHILD_NAME, "Phí và thuế"), "VND");

		log.info("TC_12_Step 04: Kiem tra text Phi Dai Ly Ban ve nguoi lon 1 hien thi");
		verifyTrue(airTicket.isDynamicPaymentInfoByName(DomesticAirTicketBooking_Data.validInput.CHILD_NAME, "Phí đại lý bán vé"));
		Long childAgentFee = convertMoneyToLong(airTicket.getTextAmountOfMoneyInPayment(DomesticAirTicketBooking_Data.validInput.CHILD_NAME, "Phí đại lý bán vé"), "VND");

		log.info("TC_11_Step 04: Kiem tra text Phi Hanh ly ve nguoi lon 1 hien thi");
		verifyTrue(airTicket.isDynamicPaymentInfoByName(DomesticAirTicketBooking_Data.validInput.CHILD_NAME, "Hành lý chiều đi"));
		Long packageFee = convertMoneyToLong(airTicket.getTextAmountOfMoneyInPayment(DomesticAirTicketBooking_Data.validInput.CHILD_NAME, "Hành lý chiều đi").split(" - ")[1], "VND");

		subTotal2 = childPrice + childTax + childAgentFee + packageFee;
	}

	@Test
	public void TC_13_KiemTraDropDownIconTaiEmBe1() {
		log.info("TC_13_Step 01: Click Dropdown Icon Nguoi lon 1");
		airTicket.clickToDynamicIconByLabel("Em bé 1", "com.VCB:id/ivImage");

		log.info("TC_13_Step 02: Kiem tra text gia ve nguoi lon 1 hien thi");
		verifyTrue(airTicket.isDynamicPaymentInfoByName(DomesticAirTicketBooking_Data.validInput.BABY_NAME, "Giá vé"));
		Long babyTicketPrice = convertMoneyToLong(airTicket.getTextAmountOfMoneyInPayment(DomesticAirTicketBooking_Data.validInput.BABY_NAME, "Giá vé"), "VND");

		log.info("TC_13_Step 03: Kiem tra text Phi Va Thue nguoi lon 1 hien thi");
		verifyTrue(airTicket.isDynamicPaymentInfoByName(DomesticAirTicketBooking_Data.validInput.BABY_NAME, "Phí và thuế"));
		Long babyTax = convertMoneyToLong(airTicket.getTextAmountOfMoneyInPayment(DomesticAirTicketBooking_Data.validInput.BABY_NAME, "Phí và thuế"), "VND");

		log.info("TC_13_Step 04: Kiem tra text Phi Dai Ly Ban ve nguoi lon 1 hien thi");
		verifyTrue(airTicket.isDynamicPaymentInfoByName(DomesticAirTicketBooking_Data.validInput.BABY_NAME, "Phí đại lý bán vé"));
		Long babyAgentFee = convertMoneyToLong(airTicket.getTextAmountOfMoneyInPayment(DomesticAirTicketBooking_Data.validInput.BABY_NAME, "Phí đại lý bán vé"), " VND");
		subTotal3 = babyTicketPrice + babyTax + babyAgentFee;
		String totalAmount = addCommasToLong(subTotal1 + subTotal2 + subTotal3 + "");

		log.info("TC_13_Step 05: Kiem tra tong so tien hien thi dung");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Tổng tiền", "com.VCB:id/tvText2"), totalAmount + " VND");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
