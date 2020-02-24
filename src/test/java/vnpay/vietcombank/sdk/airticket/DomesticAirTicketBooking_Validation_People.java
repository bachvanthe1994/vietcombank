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

public class DomesticAirTicketBooking_Validation_People extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private DynamicAirTicketBookingObjects airTicket;
	private String selectedAirTicketPrice;
	private String totalPrice;
	private long haftOfPrice;

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
	public void TC_01_KiemTraSoLuongHanhKhachToiDa() {

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

		log.info("TC_01_Step 07: Click xac nhan");
		airTicket.clickToDynamicButton("Xác nhận");

		log.info("TC_01_Step 08: Chon 9 nguoi lon ");
		for (int i = 1; i < 6; i++) {
			airTicket.clickToDynamicPlusAndMinusIcon("Người lớn (12 tuổi trở lên)", "+");
		}

		log.info("TC_01_Step 09: Chon 1 tre em ");
		for (int i = 0; i < 4; i++) {
			airTicket.clickToDynamicPlusAndMinusIcon("Trẻ em (2 đến dưới 12 tuổi)", "+");
		}
		log.info("TC_01_Step 10: Click Tim Chuyen Bay ");
		airTicket.clickToDynamicButton("Tìm chuyến bay");

		log.info("TC_01_Step 11: Kiem tra thong bao loi hien thi ");
		verifyEquals(airTicket.getTextInDynamicPopUpAndTitle("com.VCB:id/tvContent"), DomesticAirTicketBooking_Data.Message.OVER_NUMBER_OF_ALLOWED_PEOPLE);

		log.info("TC_01_Step 12: Click Dong y ");
		airTicket.clickToDynamicButton("Đồng ý");

		log.info("TC_01_Step 13: CLick quay lai ");
		airTicket.clickToDynamicIcon("com.VCB:id/ivTitleLeft");

	}

	@Test
	public void TC_02_KiemTraSoLuongHanhKhongHopLeVaDongY() {
		log.info("Before class: Click Dong y ");
		airTicket.clickToDynamicTextOrButtonLink("Đặt vé máy bay Nội địa");

		log.info("Before class: Click Dong y ");
		airTicket.clickToDynamicButton("Đồng ý");

		log.info("Before class: Chon mot chieu ");
		airTicket.clickToDynamicTextOrButtonLink("Một chiều");

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

		log.info("TC_01_Step 07: Click xac nhan");
		airTicket.clickToDynamicButton("Xác nhận");

		log.info("TC_01_Step 08: Chon 9 nguoi lon ");
		for (int i = 1; i < 4; i++) {
			airTicket.clickToDynamicPlusAndMinusIcon("Người lớn (12 tuổi trở lên)", "+");
		}

		log.info("TC_01_Step 09: Chon 1 tre em ");
		for (int i = 0; i < 4; i++) {
			airTicket.clickToDynamicPlusAndMinusIcon("Trẻ em (2 đến dưới 12 tuổi)", "+");
		}
		log.info("TC_01_Step 09: Chon 1 tre em ");
		for (int i = 0; i < 4; i++) {
			airTicket.clickToDynamicPlusAndMinusIcon("Em bé (Dưới 2 tuổi)", "+");
		}
		log.info("TC_01_Step 10: Click Tim Chuyen Bay ");
		airTicket.clickToDynamicButton("Tìm chuyến bay");

		log.info("TC_01_Step 11: Kiem tra thong bao loi hien thi ");
		verifyEquals(airTicket.getTextInDynamicPopUpAndTitle("com.VCB:id/tvTitle"), DomesticAirTicketBooking_Data.Message.INVALID_NUMBER_OF_ALLOWED_PEOPLE);
	}

	@Test
	public void TC_03_KiemTraSoLuongHanhHopLeVaDongY() {
		log.info("TC_03_Step 1: Click Dong y ");
		airTicket.clickToDynamicButton("Đồng ý");

		log.info("TC_03_Step 2: Kiem tra text danh sach chuyen bay hien thi ");
		verifyTrue(airTicket.isDynamicTextDisplayed("Danh sách chuyến bay"));

		log.info("TC_03_Step 3: CLick quay lai ");
		airTicket.clickToDynamicIcon("com.VCB:id/ivTitleLeft");

	}

	@Test
	public void TC_04_KiemTraSoLuongHanhHopLeVaHuy() {
		log.info("TC_02_Step 10: Click Tim Chuyen Bay ");
		airTicket.clickToDynamicButton("Tìm chuyến bay");

		log.info("TC_02_Step 12: Click Dong y ");
		airTicket.clickToDynamicButton("Hủy");

		log.info("TC_02_Step 03: Kiem tra text dat ve may bay noi dia hien thi ");
		verifyTrue(airTicket.isDynamicTextDisplayed("Đặt vé máy bay nội địa"));

		log.info("TC_01_Step 14: CLick quay lai ");
		airTicket.clickToDynamicIcon("com.VCB:id/ivTitleLeft");

	}

	@Test
	public void TC_05_KiemTraSoLuongEmBeLonHonNguoiLon() {
		log.info("Before class: Click Dong y ");
		airTicket.clickToDynamicTextOrButtonLink("Đặt vé máy bay Nội địa");

		log.info("Before class: Click Dong y ");
		airTicket.clickToDynamicButton("Đồng ý");

		log.info("Before class: Chon mot chieu ");
		airTicket.clickToDynamicTextOrButtonLink("Một chiều");

		log.info("TC_05_Step 01: Click Khoi Hanh");
		airTicket.clickToDynamicTextOrButtonLink("Khởi hành");

		log.info("TC_05_Step 02: Click Ha Noi");
		airTicket.clickToDynamicTextOrButtonLink(DomesticAirTicketBooking_Data.validInput.DEPARTURE_PLACE);

		log.info("TC_05_Step 03: Click Diem Den");
		airTicket.clickToDynamicTextOrButtonLink("Điểm đến");

		log.info("TC_05_Step 04: Click TP Ho Chi Minh");
		airTicket.clickToDynamicTextOrButtonLink(DomesticAirTicketBooking_Data.validInput.ARRIVAL_PLACE);

		log.info("TC_05_Step 05: Click Ngay Đi");
		airTicket.clickToDynamicTextOrButtonLink("Ngày đi");

		log.info("TC_05_Step 06: Click Chon Ngay");
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(1), getForWardDay(1));

		log.info("TC_05_Step 07: Click xac nhan");
		airTicket.clickToDynamicButton("Xác nhận");

		log.info("TC_05_Step 08: Chon 2 nguoi lon ");
		airTicket.clickToDynamicPlusAndMinusIcon("Người lớn (12 tuổi trở lên)", "+");

		log.info("TC_05_Step 09: Chon 3 tre em ");
		for (int i = 0; i < 3; i++) {
			airTicket.clickToDynamicPlusAndMinusIcon("Em bé (Dưới 2 tuổi)", "+");
		}
		log.info("TC_05_Step 10: Click Tim Chuyen Bay ");
		airTicket.clickToDynamicButton("Tìm chuyến bay");

		log.info("TC_05_Step 11: Kiem tra thong bao loi hien thi ");
		verifyEquals(airTicket.getTextInDynamicPopUpAndTitle("com.VCB:id/tvTitle"), DomesticAirTicketBooking_Data.Message.INVALID_NUMBER_OF_BABIES);

		log.info("TC_05_Step 12: Click Dong y ");
		airTicket.clickToDynamicButton("Đồng ý");

	}

	@Test
	public void TC_06_KiemTraSoLuongEmBeVaTreEmGapDoiNguoiLon() {
		log.info("TC_06_Step 01: Chon 2 em be ");
		airTicket.clickToDynamicPlusAndMinusIcon("Em bé (Dưới 2 tuổi)", "-");

		log.info("TC_06_Step 02: Chon 3 tre em ");
		for (int i = 0; i < 3; i++) {
			airTicket.clickToDynamicPlusAndMinusIcon("Trẻ em (2 đến dưới 12 tuổi)", "+");
		}

		log.info("TC_06_Step 10: Click Tim Chuyen Bay ");
		airTicket.clickToDynamicButton("Tìm chuyến bay");

		log.info("TC_06_Step 11: Kiem tra thong bao loi hien thi ");
		verifyEquals(airTicket.getTextInDynamicPopUpAndTitle("com.VCB:id/tvTitle"), DomesticAirTicketBooking_Data.Message.INVALID_NUMBER_OF_CHILDREN);

		log.info("TC_06_Step 12: Click Dong y ");
		airTicket.clickToDynamicButton("Đồng ý");

		log.info("TC_06_Step 3: CLick quay lai ");
		airTicket.clickToDynamicIcon("com.VCB:id/ivTitleLeft");

	}

	@Test
	public void TC_07_KiemTraManHinhChoVeMotChieu() {
		log.info("TC_07_Step 01: Click Dat ve may bay noi dia ");
		airTicket.clickToDynamicTextOrButtonLink("Đặt vé máy bay Nội địa");

		log.info("TC_07_Step 02: Click Dong y ");
		airTicket.clickToDynamicButton("Đồng ý");

		log.info("TC_07_Step 03: Chon mot chieu ");
		airTicket.clickToDynamicTextOrButtonLink("Một chiều");

		log.info("TC_07_Step 04: Click Khoi Hanh");
		airTicket.clickToDynamicTextOrButtonLink("Khởi hành");

		log.info("TC_07_Step 05: Click Ha Noi");
		airTicket.clickToDynamicTextOrButtonLink(DomesticAirTicketBooking_Data.validInput.DEPARTURE_PLACE);

		log.info("TC_07_Step 06: Click Diem Den");
		airTicket.clickToDynamicTextOrButtonLink("Điểm đến");

		log.info("TC_07_Step 07: Click TP Ho Chi Minh");
		airTicket.clickToDynamicTextOrButtonLink(DomesticAirTicketBooking_Data.validInput.ARRIVAL_PLACE);

		log.info("TC_07_Step 08: Click Ngay Đi");
		airTicket.clickToDynamicTextOrButtonLink("Ngày đi");

		log.info("TC_07_Step 09: Click Chon Ngay");
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(1), getForWardDay(1));

		log.info("TC_07_Step 10: Click xac nhan");
		airTicket.clickToDynamicButton("Xác nhận");

		log.info("TC_07_Step 11: Click Tim Chuyen Bay ");
		airTicket.clickToDynamicButton("Tìm chuyến bay");

		log.info("TC_07_Step 12:  Kiem tra ma chuyen bay hien thi ");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvTitle2Left"), DomesticAirTicketBooking_Data.validInput.DEPARTURE_CODE);
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvTitle2Right"), DomesticAirTicketBooking_Data.validInput.ARRIVAL_CODE);

		log.info("TC_06_Step 13: CLick quay lai ");
		airTicket.clickToDynamicIcon("com.VCB:id/ivTitleLeft");

		log.info("TC_07_Step 14: Click Tim Chuyen Bay ");
		airTicket.clickToDynamicButton("Tìm chuyến bay");

		log.info("TC_07_Step 15: Kiem tra thoi gian bay ");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvTitle2"), airTicket.getDayInWeek(getForwardDate(1)) + " " + getForwardDate(1));

		log.info("TC_07_Step 16: Kiem tra diem di ");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvDiemDi"), DomesticAirTicketBooking_Data.validInput.DEPARTURE_PLACE);

		log.info("TC_06_Step 17: CLick quay lai ");
		airTicket.clickToDynamicIcon("com.VCB:id/ivTitleLeft");

		log.info("TC_07_Step 18: Click Tim Chuyen Bay ");
		airTicket.clickToDynamicButton("Tìm chuyến bay");

		log.info("TC_07_Step 19: Kiem tra diem den ");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvDiemDen"), DomesticAirTicketBooking_Data.validInput.ARRIVAL_PLACE);

		log.info("TC_07_Step 20: Kiem tra text dang tim chuyen bay ");
		verifyTrue(airTicket.isDynamicTextDisplayed("Đang tìm kiếm chuyến bay"));

		log.info("TC_07_Step 21: CLick quay lai ");
		airTicket.clickToDynamicIcon("com.VCB:id/ivTitleLeft");

		log.info("TC_07_Step 22: Click Tim Chuyen Bay ");
		airTicket.clickToDynamicButton("Tìm chuyến bay");

		log.info("TC_07_Step 22: kiem tra text so luong hang hang khong ");
		verifyTrue(airTicket.isDynamicTextDisplayed("Chúng tôi làm việc với hơn\n" + "30 hãng hàng không trên khắp thế giới"));
		airTicket.sleep(driver, 10000);

	}

	@Test
	public void TC_08_KiemTraGiaoDienDanhSachChuyenBay1Chieu() {

		log.info("TC_08_Step 01: Kiem tra title Danh sach chuyen bay");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvTitle1"), "Danh sách chuyến bay");

		log.info("TC_08_Step 02: Kiem tra Dack icon");
		verifyTrue(airTicket.isDynamicIconDisplayed("com.VCB:id/ivTitleLeft"));

		log.info("TC_08_Step 03: Kiem tra Call icon");
		verifyTrue(airTicket.isDynamicIconDisplayed("com.VCB:id/ivTitleRight"));

		log.info("TC_08_Step 04: Kiem tra diem di hien thi");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tv_sourceDetail"), DomesticAirTicketBooking_Data.validInput.DEPARTURE_PLACE);

		log.info("TC_08_Step 05: Kiem tra ma diem di hien thi");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tv_source"), DomesticAirTicketBooking_Data.validInput.DEPARTURE_CODE);

		log.info("TC_08_Step 06: kiem tra ngay di hien thi");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tv_thu"), airTicket.getDayInWeek(getForwardDate(1)) + " " + getForwardDate(1));

		log.info("TC_08_Step 07: Kiem tra diem den hien thi");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tv_DestDetail"), DomesticAirTicketBooking_Data.validInput.ARRIVAL_PLACE);

		log.info("TC_08_Step 08: Kiem tra ma diem den hien thi");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tv_Dest"), DomesticAirTicketBooking_Data.validInput.ARRIVAL_CODE);

		log.info("TC_08_Step 09: Kiem tra code bay hien thi");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tv_flightName"), "HAN - SGN");

		log.info("TC_08_Step 10: Kiem tra icon loc hien thi");
		verifyTrue(airTicket.isDynamicIconDisplayed("com.VCB:id/iv_Filter"));

		log.info("TC_08_Step 11: Kiem tra icon chon hien thi");
		verifyTrue(airTicket.isDynamicIconDisplayed("com.VCB:id/iv_selected"));

		log.info("TC_08_Step 12: Kiem tra text sap sep va loc hien thi");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tv_content_price"), "Sắp xếp & lọc");

		log.info("TC_08_Step 13: Kiem tra logo hang bay hien thi");
		verifyTrue(airTicket.isDynamicIconDisplayed("com.VCB:id/iv_imgflight"));

		log.info("TC_08_Step 14: Kiem tra so hieu may bay");
		verifyTrue(airTicket.isDynamicTextByIdDisplayed("com.VCB:id/tv_flightNo"));

		log.info("TC_08_Step 15: Kiem tra thoi gian bat dau bay");
		verifyTrue(airTicket.isDynamicTextByIdDisplayed("com.VCB:id/tv_time_deptTime"));

		log.info("TC_08_Step 16: Kiem tra khoang thoi gian bay hien thi");
		verifyTrue(airTicket.isDynamicTextByIdDisplayed("com.VCB:id/tv_timeduration"));

		log.info("TC_08_Step 17: Kiem tra thoi gian ha canh hien thi");
		verifyTrue(airTicket.isDynamicTextByIdDisplayed("com.VCB:id/tv_time_deptTime_arrival"));

		log.info("TC_08_Step 18: Kiem tra gia tien hien thi");
		verifyTrue(airTicket.isDynamicTextByIdDisplayed("com.VCB:id/tv_amount"));

		log.info("TC_08_Step 19: Kiem tra loai ve hien thi");
		verifyTrue(airTicket.isDynamicTextByIdDisplayed("com.VCB:id/tv_fclass"));

	}

	@Test
	public void TC_09_KiemTraDanhSachChuyenBay1ChieuDaChon() {
		airTicket.clickToDynamicFlight(0, "VJ");

		String flightCode = airTicket.getAirTicketInfo(0, "com.VCB:id/tv_flightNo");
		String depTime = airTicket.getAirTicketInfo(0, "com.VCB:id/tv_time_deptTime");
		String arriTime = airTicket.getAirTicketInfo(0, "com.VCB:id/tv_time_deptTime_arrival");
		selectedAirTicketPrice = airTicket.getAirTicketInfo(0, "com.VCB:id/tv_amount");

		log.info("TC_09_Step 1: Kiem tra logo hang bay hien thi");
		verifyTrue(airTicket.isDynamicIconDisplayed("com.VCB:id/iv_flight_selected"));

		log.info("TC_09_Step 2: Kiem tra so hieu may bay");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tv_flight_code"), flightCode);

		log.info("TC_09_Step 4: Kiem tra khoang thoi gian bay hien thi");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tv_timeflight_selected"), depTime + " - " + arriTime);

		log.info("TC_09_Step 5: Kiem tra thoi gian ha canh hien thi");
		verifyTrue(airTicket.isDynamicTextByIdDisplayed("com.VCB:id/tv_amount_flight_selected"));

		log.info("TC_09_Step 6: Kiem tra Tong hien thi");
		verifyTrue(airTicket.isDynamicTextDisplayed("Tổng:"));

		totalPrice = airTicket.getDynamicTextByID("com.VCB:id/tv_amount_flight_selected");

		log.info("TC_09_Step 7: Kiem tra text xem chi tiet gia hien thi");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tv_viewDetail_one_way"), "Xem chi tiết giá");

		log.info("TC_09_Step 8: Kiem tra button dat ve hien thi");
		verifyTrue(airTicket.isDynamicButtonDisplayed("Đặt vé"));
	}

	@Test
	public void TC_10_KiemTraXemChiTietGia() {
		log.info("TC_10_Step 01: Click Chi Tiet gia");
		airTicket.clickToDynamicTextOrButtonLink("Xem chi tiết giá");

		log.info("TC_10_Step 02: Kiem tra title chi tiet gia hien thi ");
		verifyTrue(airTicket.isDynamicTextDisplayed("Chi tiết giá"));

		log.info("TC_10_Step 03: Kiem tra tong so tien ");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Chiều đi", "com.VCB:id/tv_sumAmount_chieudi"), totalPrice);

		log.info("TC_10_Step 04: Kiem tra title gia ve co ban ");
		verifyTrue(airTicket.isDynamicTextDisplayed("GIÁ VÉ CƠ BẢN"));

		log.info("TC_10_Step 05: Kiem tra title Thue Phi ");
		verifyTrue(airTicket.isDynamicTextDisplayed("THUẾ & PHÍ"));

		log.info("TC_10_Step 06: Kiem tra title dai ly ban ve ");
		verifyTrue(airTicket.isDynamicTextDisplayed("PHÍ ĐẠI LÝ BÁN VÉ"));

		log.info("TC_10_Step 07: kiem tra tong so tien ");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Tổng cộng", "com.VCB:id/tv_sum"), totalPrice);

		log.info("TC_10_Step 08: Kiem tra text dieu kien dat ve ");
		verifyTrue(airTicket.isDynamicTextDisplayed("Điều khoản và Điều kiện đặt vé"));

		log.info("TC_10_Step 11: Kiem tra button dat ve hien thi");
		verifyTrue(airTicket.isDynamicButtonDisplayed("Đặt vé"));

		log.info("TC_10_Step 12: CLick Dong");
		airTicket.clickToDynamicIcon("com.VCB:id/ivTitleLeft");

		log.info("TC_10_Step 13: CLick quay lai ");
		airTicket.clickToDynamicIcon("com.VCB:id/ivTitleLeft");

	}

	@Test
	public void TC_11_KiemTraManHinhChoVeKhuHoi() {
		log.info("TC_11_Step 01: Chon 2 chieu ");
		airTicket.clickToDynamicTextOrButtonLink("Khứ hồi");

		log.info("TC_11_Step 02: Click Ngay Đi");
		airTicket.scrollUpToTextView("Ngày về");
		airTicket.clickToDynamicTextOrButtonLink("Ngày về");

		log.info("TC_11_Step 03: Click Chon Ngay về");
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(2), getForWardDay(2));

		log.info("TC_11_Step 04: Click xac nhan");
		airTicket.clickToDynamicButton("Xác nhận");

		log.info("TC_11_Step 05: Click Tim Chuyen Bay ");
		airTicket.clickToDynamicButton("Tìm chuyến bay");

		log.info("TC_11_Step 06:  Kiem tra ma chuyen bay hien thi ");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvTitle2Left"), DomesticAirTicketBooking_Data.validInput.DEPARTURE_CODE);
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvTitle2Right"), DomesticAirTicketBooking_Data.validInput.ARRIVAL_CODE);

		log.info("TC_11_Step 07: CLick quay lai ");
		airTicket.clickToDynamicIcon("com.VCB:id/ivTitleLeft");

		log.info("TC_11_Step 08: Click Tim Chuyen Bay ");
		airTicket.clickToDynamicButton("Tìm chuyến bay");

		log.info("TC_11_Step 09: Kiem tra ma chuyen bay va diem den ");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvTitle2"), airTicket.getDayInWeek(getForwardDate(1)) + " " + getForwardDate(1) + " - " + airTicket.getDayInWeek(getForwardDate(2)) + " " + getForwardDate(2));
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvDiemDi"), DomesticAirTicketBooking_Data.validInput.DEPARTURE_PLACE);

		log.info("TC_11_Step 10: CLick quay lai ");
		airTicket.clickToDynamicIcon("com.VCB:id/ivTitleLeft");

		log.info("TC_11_Step 11: Click Tim Chuyen Bay ");
		airTicket.clickToDynamicButton("Tìm chuyến bay");

		log.info("TC_11_Step 12: Kiem tra diem di va text dang tim chuyen bay ");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvDiemDen"), DomesticAirTicketBooking_Data.validInput.ARRIVAL_PLACE);
		verifyTrue(airTicket.isDynamicTextDisplayed("Đang tìm kiếm chuyến bay"));

		log.info("TC_11_Step 13: CLick quay lai ");
		airTicket.clickToDynamicIcon("com.VCB:id/ivTitleLeft");

		log.info("TC_11_Step 14: Click Tim Chuyen Bay ");
		airTicket.clickToDynamicButton("Tìm chuyến bay");

		log.info("TC_11_Step 15: kiem tra text so luong hang hang khong ");
		verifyTrue(airTicket.isDynamicTextDisplayed("Chúng tôi làm việc với hơn\n" + "30 hãng hàng không trên khắp thế giới"));
		airTicket.sleep(driver, 10000);

	}

	@Test
	public void TC_12_KiemTraGiaoDienDanhSachChuyenBay2Chieu() {

		log.info("TC_12_Step 01: Kiem tra title Danh sach chuyen bay");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvTitle1"), "Danh sách chuyến bay");

		log.info("TC_12_Step 02: Kiem tra Dack icon");
		verifyTrue(airTicket.isDynamicIconDisplayed("com.VCB:id/ivTitleLeft"));

		log.info("TC_12_Step 03: Kiem tra Call icon");
		verifyTrue(airTicket.isDynamicIconDisplayed("com.VCB:id/ivTitleRight"));

		log.info("TC_12_Step 04: Kiem tra diem di hien thi");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tv_sourceDetail"), DomesticAirTicketBooking_Data.validInput.DEPARTURE_PLACE);

		log.info("TC_12_Step 05: Kiem tra ma diem di hien thi");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tv_source"), DomesticAirTicketBooking_Data.validInput.DEPARTURE_CODE);

		log.info("TC_12_Step 06: kiem tra ngay di hien thi");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tv_date_ngaydi"), airTicket.getDayInWeek(getForwardDate(1)) + " " + getForwardDate(1));

		log.info("TC_12_Step 07: Kiem tra diem den hien thi");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tv_DestDetail"), DomesticAirTicketBooking_Data.validInput.ARRIVAL_PLACE);

		log.info("TC_12_Step 08: Kiem tra ma diem den hien thi");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tv_Dest"), DomesticAirTicketBooking_Data.validInput.ARRIVAL_CODE);

		log.info("TC_12_Step 06: kiem tra ngay di hien thi");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tv_date_ngayve"), airTicket.getDayInWeek(getForwardDate(2)) + " " + getForwardDate(2));

		log.info("TC_12_Step 10: Kiem tra icon loc hien thi");
		verifyTrue(airTicket.isDynamicIconDisplayed("com.VCB:id/iv_Filter"));

		log.info("TC_12_Step 11: Kiem text chieu di va loc hien thi");
		verifyTrue(airTicket.isDynamicDropdownByLabelDisplayed("CHIỀU ĐI", "Sắp xếp & lọc"));

		log.info("TC_12_Step 11: Kiem text chieu ve va loc hien thi");
		verifyTrue(airTicket.isDynamicDropdownByLabelDisplayed("CHIỀU VỀ", "Sắp xếp & lọc"));

		log.info("TC_12_Step 13: Kiem tra logo hang bay hien thi");
		verifyTrue(airTicket.isDynamicIconDisplayed("com.VCB:id/iv_flight_rountrip_internal"));

		log.info("TC_12_Step 14: Kiem tra so hieu may bay");
		verifyTrue(airTicket.isDynamicTextByIdDisplayed("com.VCB:id/tv_flightcode_internal"));

		log.info("TC_12_Step 15: Kiem tra thoi gian bat dau bay");
		verifyTrue(airTicket.isDynamicTextByIdDisplayed("com.VCB:id/tv_time_roundtrip_internal"));

		log.info("TC_12_Step 16: Kiem tra khoang thoi gian bay hien thi");
		verifyTrue(airTicket.isDynamicTextByIdDisplayed("com.VCB:id/tvDuration"));

		log.info("TC_12_Step 17: Kiem tra thoi gian ha canh hien thi");
		verifyTrue(airTicket.isDynamicTextByIdDisplayed("com.VCB:id/tv_time_roundtrip_internal_arrival"));

		log.info("TC_12_Step 18: Kiem tra gia tien hien thi");
		verifyTrue(airTicket.isDynamicTextByIdDisplayed("com.VCB:id/tv_amount_roundtrip_internal"));

		log.info("TC_12_Step 19: Kiem tra loai ve hien thi");
		verifyTrue(airTicket.isDynamicTextByIdDisplayed("com.VCB:id/tvClass"));

	}

	@Test
	public void TC_13_KiemTraDanhSachGiaoDienChuyenBay2ChieuDaChon() {
		log.info("TC_13_Step 1: Chon ve may bay chieu di");
		airTicket.clickToDynamicFirstFlightShiftByFlightCode("com.VCB:id/recy_chieudi", "VJ");

		log.info("TC_13_Step 2: Chon ve may bay chieu ve");
		airTicket.clickToDynamicFirstFlightShiftByFlightCode("com.VCB:id/recy_chieuve", "VJ");

		String firstTurnCode = airTicket.getAirTicketInfoByFlightCode2Way("com.VCB:id/recy_chieudi", "VJ", "com.VCB:id/tv_flightcode_internal");
		String firstTurnDepartureTime = airTicket.getAirTicketInfoByFlightCode2Way("com.VCB:id/recy_chieudi", "VJ", "com.VCB:id/tv_time_roundtrip_internal");
		String firstTurnArrivalTime = airTicket.getAirTicketInfoByFlightCode2Way("com.VCB:id/recy_chieudi", "VJ", "com.VCB:id/tv_time_roundtrip_internal_arrival");
		String secondTurnCode = airTicket.getAirTicketInfoByFlightCode2Way("com.VCB:id/recy_chieuve", "VJ", "com.VCB:id/tv_flightcode_internal");
		String secondTurnDepartureTime = airTicket.getAirTicketInfoByFlightCode2Way("com.VCB:id/recy_chieuve", "VJ", "com.VCB:id/tv_time_roundtrip_internal");
		String secondTurnArrivalTime = airTicket.getAirTicketInfoByFlightCode2Way("com.VCB:id/recy_chieuve", "VJ", "com.VCB:id/tv_time_roundtrip_internal_arrival");

		log.info("TC_13_Step 3: Kiem tra logo hang bay hien thi");
		verifyTrue(airTicket.isDynamicIconDisplayed("com.VCB:id/iv_flight_internal_chieudi"));

		log.info("TC_13_Step 4: Kiem tra so hieu may bay");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tv_flightCode_internal_chieudi"), firstTurnCode);

		log.info("TC_13_Step 5: Kiem tra khoang thoi gian bay hien thi");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tv_time_internal_chieudi"), firstTurnDepartureTime + "-" + firstTurnArrivalTime);

		log.info("TC_13_Step 6: Kiem tra logo hang bay hien thi");
		verifyTrue(airTicket.isDynamicIconDisplayed("com.VCB:id/iv_flight_internal_chieuve"));

		log.info("TC_13_Step 7: Kiem tra so hieu may bay");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tv_flightCode_internal_chieuve"), secondTurnCode);

		log.info("TC_13_Step 8: Kiem tra khoang thoi gian bay hien thi");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tv_time_internal_chieuve"), secondTurnDepartureTime + "-" + secondTurnArrivalTime);

		log.info("TC_13_Step 9: Kiem tra Tong hien thi");
		verifyTrue(airTicket.isDynamicTextDisplayed("Tổng:"));

		log.info("TC_13_Step 10: Lay Tong tien");
		totalPrice = airTicket.getDynamicTextByID("com.VCB:id/tv_Sum_amount_flight_selected");
		haftOfPrice = convertMoneyToLong(totalPrice, "VND") / 2;

		log.info("TC_13_Step 11: Kiem tra text xem chi tiet gia hien thi");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tv_detail_tecket"), "Xem chi tiết giá");

		log.info("TC_13_Step 12: Kiem tra button dat ve hien thi");
		verifyTrue(airTicket.isDynamicButtonDisplayed("Đặt vé"));

	}

	@Test
	public void TC_14_KiemTraXemChiTietGiaVe2Chieu() {
		log.info("TC_14_Step 01: Click Chi Tiet gia");
		airTicket.clickToDynamicTextOrButtonLink("Xem chi tiết giá");

		log.info("TC_14_Step 02: Kiem tra title chi tiet gia hien thi ");
		verifyTrue(airTicket.isDynamicTextDisplayed("Chi tiết giá"));

		log.info("TC_14_Step 03: Kiem tra tong so tien ");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Chiều đi", "com.VCB:id/tv_sumAmount_chieudi"), addCommasToLong(haftOfPrice + "") + " VND");

		log.info("TC_14_Step 04: Kiem tra title gia ve co ban ");
		verifyTrue(airTicket.isDynamicTextByLabel("Chiều đi", "GIÁ VÉ CƠ BẢN"));

		log.info("TC_14_Step 05: Kiem tra title Thue Phi ");
		verifyTrue(airTicket.isDynamicTextByLabel("Chiều đi", "THUẾ & PHÍ"));

		log.info("TC_14_Step 06: Kiem tra title dai ly ban ve ");
		verifyTrue(airTicket.isDynamicTextByLabel("Chiều đi", "PHÍ ĐẠI LÝ BÁN VÉ"));

		log.info("TC_14_Step 07: Kiem tra tong so tien ");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Chiều về", "com.VCB:id/tv_sumAmount_chieuve"), addCommasToLong(haftOfPrice + "") + " VND");

		log.info("TC_14_Step 08: Kiem tra title gia ve co ban ");
		verifyTrue(airTicket.isDynamicTextByLabel("Chiều về", "GIÁ VÉ CƠ BẢN"));

		log.info("TC_14_Step 09: Kiem tra title Thue Phi ");
		verifyTrue(airTicket.isDynamicTextByLabel("Chiều về", "THUẾ & PHÍ"));

		log.info("TC_14_Step 10: Kiem tra title dai ly ban ve ");
		verifyTrue(airTicket.isDynamicTextByLabel("Chiều về", "PHÍ ĐẠI LÝ BÁN VÉ"));

		log.info("TC_14_Step 11: kiem tra tong so tien ");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Tổng cộng", "com.VCB:id/tv_sum"), totalPrice);

		log.info("TC_14_Step 12: Kiem tra text dieu kien dat ve ");
		verifyTrue(airTicket.isDynamicTextDisplayed("Điều khoản và Điều kiện đặt vé"));

		log.info("TC_14_Step 13: Kiem tra button dat ve hien thi");
		verifyTrue(airTicket.isDynamicButtonDisplayed("Đặt vé"));

		log.info("TC_14_Step 14: CLick Dong");
		airTicket.clickToDynamicIcon("com.VCB:id/ivTitleLeft");

		log.info("TC_14_Step 15: CLick quay lai ");
		airTicket.clickToDynamicIcon("com.VCB:id/ivTitleLeft");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();
	}

}
