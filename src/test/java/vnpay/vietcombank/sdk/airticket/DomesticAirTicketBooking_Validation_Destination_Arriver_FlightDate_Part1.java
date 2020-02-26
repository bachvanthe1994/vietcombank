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

public class DomesticAirTicketBooking_Validation_Destination_Arriver_FlightDate_Part1 extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private DynamicAirTicketBookingObjects airTicket;
	private String yesterday = getBackWardDay(1);
	private String twoDaysAgo = getBackWardDay(2);

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
	public void TC_01_KiemTraGiaoDienManHinhDatVeMayBayNoiDiaMotChieu() {
		log.info("TC_01_Step 01: Kiem tra title dat ve may bay noi dia");
		verifyTrue(airTicket.isDynamicTextDisplayed("Đặt vé máy bay nội địa"));

		log.info("TC_01_Step 02: Kiem tra icon back");
		verifyTrue(airTicket.isDynamicIconDisplayed("com.VCB:id/ivTitleLeft"));

		log.info("TC_01_Step 03: Kiem tra icon Call");
		verifyTrue(airTicket.isDynamicIconDisplayed("com.VCB:id/ivTitleRight"));

		log.info("TC_01_Step 04: Kiem tra text khoi hanh");
		verifyTrue(airTicket.isDynamicTextDisplayed("Khởi hành"));

		log.info("TC_01_Step 05: Kiem tra text chon diem khoi hanh");
		verifyTrue(airTicket.isDynamicTextDisplayed("Chọn điểm khởi hành"));

		log.info("TC_01_Step 06: Kiem tra text diem den");
		verifyTrue(airTicket.isDynamicTextDisplayed("Điểm đến"));

		log.info("TC_01_Step 07: Kiem tra text chon diem den");
		verifyTrue(airTicket.isDynamicTextDisplayed("Chọn điểm đến"));

		log.info("TC_01_Step 08: Kiem tra  text ngay di");
		verifyTrue(airTicket.isDynamicTextDisplayed("Ngày đi"));

		log.info("TC_01_Step 09: Kiem tra text chon ngay");
		verifyTrue(airTicket.isDynamicTextByLabel("Ngày đi", "Chọn ngày"));

		log.info("TC_01_Step 10: Kiem tra icon - cua nguoi lon");
		verifyTrue(airTicket.isDynamicTextByLabel("Người lớn (12 tuổi trở lên)", "-"));

		log.info("TC_01_Step 11: Kiem tra icon + cua nguoi lon");
		verifyTrue(airTicket.isDynamicTextByLabel("Người lớn (12 tuổi trở lên)", "+"));

		log.info("TC_01_Step 12: Kiem tra icon - cua tre em");
		verifyTrue(airTicket.isDynamicTextByLabel("Trẻ em (2 đến dưới 12 tuổi)", "-"));

		log.info("TC_01_Step 13: Kiem tra icon + cua tre em");
		verifyTrue(airTicket.isDynamicTextByLabel("Trẻ em (2 đến dưới 12 tuổi)", "+"));

		log.info("TC_01_Step 14: Kiem tra icon - cua em be");
		verifyTrue(airTicket.isDynamicTextByLabel("Em bé (Dưới 2 tuổi)", "-"));

		log.info("TC_01_Step 15: Kiem tra icon + cua em be");
		verifyTrue(airTicket.isDynamicTextByLabel("Em bé (Dưới 2 tuổi)", "+"));

		log.info("TC_01_Step 16: Kiem tra text hang hang khong ua thich");
		verifyTrue(airTicket.isDynamicTextDisplayed("Hãng hàng không ưa thích"));

		log.info("TC_01_Step 17: Kiem tra check box viet Nam Airline va duoc check lam mac dinh");
		verifyTrue(airTicket.isDynamicCheckBoxDisplayed("Vietnam Airlines"));
		verifyTrue(airTicket.isDynamicCheckBoxByLabelChecked("Vietnam Airlines"));

		log.info("TC_01_Step 18: Kiem tra radio Pho thong duoc check mac dinh");
		verifyTrue(airTicket.isDynamicRadioButtonByLabelDisplayed("Vietnam Airlines", "Phổ thông"));
		verifyTrue(airTicket.isDynamicRadioButtonByLabelChecked("Vietnam Airlines", "Phổ thông"));

		log.info("TC_01_Step 19: Kiem tra radio Thuong gia hien thi ");
		verifyTrue(airTicket.isDynamicRadioButtonByLabelDisplayed("Vietnam Airlines", "Thương gia"));

		log.info("TC_01_Step 11: Kiem check box VietJet Air hien thi va duoc check mac dinh");
		verifyTrue(airTicket.isDynamicCheckBoxDisplayed("VietJet Air"));
		verifyTrue(airTicket.isDynamicCheckBoxByLabelChecked("VietJet Air"));

		log.info("TC_01_Step 12: Kiem tra radio Pho thong hien thi va duoc check mac dinh");
		verifyTrue(airTicket.isDynamicRadioButtonByLabelDisplayed("VietJet Air", "Phổ thông"));
		verifyTrue(airTicket.isDynamicRadioButtonByLabelChecked("VietJet Air", "Phổ thông"));

		log.info("TC_01_Step 13: Kiem tra radio button Skyboss hien thi");
		verifyTrue(airTicket.isDynamicRadioButtonByLabelDisplayed("VietJet Air", "Skyboss"));

		log.info("TC_01_Step 14: Kiem tra Bamboo checkbox hien thi va duoc check mac dinh");
		verifyTrue(airTicket.isDynamicCheckBoxDisplayed("Bamboo Airways"));
		verifyTrue(airTicket.isDynamicCheckBoxByLabelChecked("Bamboo Airways"));

		log.info("TC_01_Step 15: Kiem tra radio Bamboo AirWay hien thi va duoc check mac dinh");
		verifyTrue(airTicket.isDynamicRadioButtonByLabelDisplayed("Bamboo Airways", "Phổ thông"));
		verifyTrue(airTicket.isDynamicRadioButtonByLabelChecked("Bamboo Airways", "Phổ thông"));

		log.info("TC_01_Step 16: Kiem tra  radio hien thi");
		verifyTrue(airTicket.isDynamicRadioButtonByLabelDisplayed("Bamboo Airways", "Thương gia"));

		log.info("TC_01_Step 17: Kiem tra check box Jestar Pacific hien thi va duoc check mac dinh");
		verifyTrue(airTicket.isDynamicCheckBoxDisplayed("Jetstar Pacific"));
		verifyTrue(airTicket.isDynamicCheckBoxByLabelChecked("Jetstar Pacific"));

	}

	@Test
	public void TC_02_KiemTraGiaoDienManHinhDatVeMayBayNoiDiaHaiChieu() {

		log.info("TC_02_Step 00: Chon mot chieu ");
		airTicket.scrollUpToTextView("Khởi hành");
		airTicket.clickToDynamicTextOrButtonLink("Khứ hồi");

		log.info("TC_02_Step 01: Kiem tra text khoi hanh");
		verifyTrue(airTicket.isDynamicTextDisplayed("Khởi hành"));

		log.info("TC_02_Step 02: Kiem tra text chon diem khoi hanh");
		verifyTrue(airTicket.isDynamicTextDisplayed("Chọn điểm khởi hành"));

		log.info("TC_02_Step 03: Kiem tra text diem den");
		verifyTrue(airTicket.isDynamicTextDisplayed("Điểm đến"));

		log.info("TC_02_Step 04: Kiem tra text chon diem den");
		verifyTrue(airTicket.isDynamicTextDisplayed("Chọn điểm đến"));

		log.info("TC_02_Step 05: Kiem tra  text ngay di");
		verifyTrue(airTicket.isDynamicTextDisplayed("Ngày đi"));

		log.info("TC_02_Step 06: Kiem tra text chon ngay");
		verifyTrue(airTicket.isDynamicTextByLabel("Ngày đi", "Chọn ngày"));

		log.info("TC_02_Step 07: Kiem tra  text ngay ve");
		verifyTrue(airTicket.isDynamicTextDisplayed("Ngày về"));

		log.info("TC_02_Step 08: Kiem tra text chon ngay ve");
		verifyTrue(airTicket.isDynamicTextByLabel("Ngày về", "Chọn ngày"));

		log.info("TC_02_Step 09: Kiem tra icon - cua nguoi lon");
		verifyTrue(airTicket.isDynamicTextByLabel("Người lớn (12 tuổi trở lên)", "-"));

		log.info("TC_02_Step 10: Kiem tra icon + cua nguoi lon");
		verifyTrue(airTicket.isDynamicTextByLabel("Người lớn (12 tuổi trở lên)", "+"));

		log.info("TC_02_Step 11: Kiem tra icon - cua tre em");
		verifyTrue(airTicket.isDynamicTextByLabel("Trẻ em (2 đến dưới 12 tuổi)", "-"));

		log.info("TC_02_Step 12: Kiem tra icon + cua tre em");
		verifyTrue(airTicket.isDynamicTextByLabel("Trẻ em (2 đến dưới 12 tuổi)", "+"));

		log.info("TC_02_Step 13: Kiem tra icon - cua em be");
		verifyTrue(airTicket.isDynamicTextByLabel("Em bé (Dưới 2 tuổi)", "-"));

		log.info("TC_02_Step 14: Kiem tra icon + cua em be");
		verifyTrue(airTicket.isDynamicTextByLabel("Em bé (Dưới 2 tuổi)", "+"));

		log.info("TC_02_Step 15: Kiem tra text hang hang khong ua thich");
		verifyTrue(airTicket.isDynamicTextDisplayed("Hãng hàng không ưa thích"));

		log.info("TC_02_Step 16: Kiem tra check box viet Nam Airline va duoc check lam mac dinh");
		verifyTrue(airTicket.isDynamicCheckBoxDisplayed("Vietnam Airlines"));
		verifyTrue(airTicket.isDynamicCheckBoxByLabelChecked("Vietnam Airlines"));

		log.info("TC_02_Step 17: Kiem tra radio Pho thong duoc check mac dinh");
		verifyTrue(airTicket.isDynamicRadioButtonByLabelDisplayed("Vietnam Airlines", "Phổ thông"));
		verifyTrue(airTicket.isDynamicRadioButtonByLabelChecked("Vietnam Airlines", "Phổ thông"));

		log.info("TC_02_Step 18: Kiem tra radio Thuong gia hien thi ");
		verifyTrue(airTicket.isDynamicRadioButtonByLabelDisplayed("Vietnam Airlines", "Thương gia"));

		log.info("TC_02_Step 19: Kiem check box VietJet Air hien thi va duoc check mac dinh");
		verifyTrue(airTicket.isDynamicCheckBoxDisplayed("VietJet Air"));
		verifyTrue(airTicket.isDynamicCheckBoxByLabelChecked("VietJet Air"));

		log.info("TC_02_Step 20: Kiem tra radio Pho thong hien thi va duoc check mac dinh");
		verifyTrue(airTicket.isDynamicRadioButtonByLabelDisplayed("VietJet Air", "Phổ thông"));
		verifyTrue(airTicket.isDynamicRadioButtonByLabelChecked("VietJet Air", "Phổ thông"));

		log.info("TC_02_Step 21: Kiem tra radio button Skyboss hien thi");
		verifyTrue(airTicket.isDynamicRadioButtonByLabelDisplayed("VietJet Air", "Skyboss"));

		log.info("TC_02_Step 22: Kiem tra Bamboo checkbox hien thi va duoc check mac dinh");
		verifyTrue(airTicket.isDynamicCheckBoxDisplayed("Bamboo Airways"));
		verifyTrue(airTicket.isDynamicCheckBoxByLabelChecked("Bamboo Airways"));

		log.info("TC_02_Step 23: Kiem tra radio Bamboo AirWay hien thi va duoc check mac dinh");
		verifyTrue(airTicket.isDynamicRadioButtonByLabelDisplayed("Bamboo Airways", "Phổ thông"));
		verifyTrue(airTicket.isDynamicRadioButtonByLabelChecked("Bamboo Airways", "Phổ thông"));

		log.info("TC_02_Step 24: Kiem tra  radio hien thi");
		verifyTrue(airTicket.isDynamicRadioButtonByLabelDisplayed("Bamboo Airways", "Thương gia"));

		log.info("TC_02_Step 25: Kiem tra check box Jestar Pacific hien thi va duoc check mac dinh");
		verifyTrue(airTicket.isDynamicCheckBoxDisplayed("Jetstar Pacific"));
		verifyTrue(airTicket.isDynamicCheckBoxByLabelChecked("Jetstar Pacific"));
	}

	@Test
	public void TC_03_KiemTraDanhSachDiemKhoiHanh() {

		log.info("TC_03_Step 00: Chon mot chieu ");
		airTicket.scrollUpToTextView("Một chiều");
		airTicket.clickToDynamicTextOrButtonLink("Một chiều");

		log.info("TC-03_Step 01: Click Khoi Hanh ");
		airTicket.clickToDynamicTextOrButtonLink("Khởi hành");

		log.info("TC-03_Step 02: Kiem tra text chon diem khoi hanh ");
		verifyEquals(airTicket.getTextInDynamicTextBox("com.VCB:id/edtSearch"), "Chọn điểm khởi hành");

		log.info("TC-03_Step 03: Kiem tra text diem khoi hanh gan day hien thi ");
		verifyTrue(airTicket.isDynamicTextDisplayed("Điểm khởi hành gần đây"));

		log.info("TC-03_Step 04: Kiem tra mau dia diem ");
		verifyTrue(airTicket.isDynamicPlaceAndCustomerNameDisplayed(DomesticAirTicketBooking_Data.validInput.HANOI_CODE, DomesticAirTicketBooking_Data.validInput.HANOI_PLACE));

	}

	@Test
	public void TC_04_KiemTraChonDiemKhoiHanh() {

		log.info("TC_04_Step 01: Chon Ha Noi ");
		airTicket.clickToDynamicTextOrButtonLink(DomesticAirTicketBooking_Data.validInput.HANOI_PLACE);

		log.info("TC_04_Step 02: Kiem tra Ha Noi da duoc chon ");
		verifyEquals(airTicket.getTextInDynamicDropDownByLabel("Khởi hành", "com.VCB:id/tvValue"), DomesticAirTicketBooking_Data.validInput.HANOI_PLACE);

		log.info("TC_04_Step 03: Click Khoi Hanh");
		airTicket.clickToDynamicTextOrButtonLink("Khởi hành");

		log.info("TC_04_Step 04: Chon TP Ho Chi Minh ");
		airTicket.clickToDynamicTextOrButtonLink(DomesticAirTicketBooking_Data.validInput.HCM_PLACE);

		log.info("TC_04_Step 05: Kiem tra TP Ho Chi Minh da duoc chon ");
		verifyEquals(airTicket.getTextInDynamicDropDownByLabel("Khởi hành", "com.VCB:id/tvValue"), DomesticAirTicketBooking_Data.validInput.HCM_PLACE);

		log.info("TC_04_Step 06: Nhan nut back ");
		airTicket.clickToDynamicIcon("com.VCB:id/ivTitleLeft");
	}

	@Test
	public void TC_05_KiemTraKhongChonDiemKhoiHanh() {
		log.info("TC_05_Step 01: Click Dat ve may bay noi dia ");
		airTicket.clickToDynamicTextOrButtonLink("Đặt vé máy bay Nội địa");

		log.info("TC_05_Step 02: Click Dong y ");
		airTicket.clickToDynamicButton("Đồng ý");

		log.info("TC_05_Step 03: Click Tim Chuyen Bay ");
		airTicket.clickToDynamicButton("Tìm chuyến bay");

		log.info("TC_05_Step 04: Kiem tra thong bao loi hien thi ");
		verifyEquals(airTicket.getTextInDynamicPopUpAndTitle("com.VCB:id/tvTitle"), DomesticAirTicketBooking_Data.Message.EMPTY_DEPARTURE_POINT);

		log.info("TC_05_Step 05: Click Dong y ");
		airTicket.clickToDynamicButton("Đồng ý");

		log.info("TC_05_Step 06: CLick quay lai ");
		airTicket.clickToDynamicIcon("com.VCB:id/ivTitleLeft");

	}

	@Test
	public void TC_06_KiemTrChonDiemDenKhiKhongChonDiemKhoiHanh() {
		log.info("TC_06_Step 01: Click Dat ve may bay noi dia ");
		airTicket.clickToDynamicTextOrButtonLink("Đặt vé máy bay Nội địa");

		log.info("TC_06_Step 02: Click Dong y ");
		airTicket.clickToDynamicButton("Đồng ý");

		log.info("TC_06_Step 03: Click Diem den ");
		airTicket.clickToDynamicTextOrButtonLink("Điểm đến");

		log.info("TC_06_Step 04: Kiem tra thong bao loi hien thi ");
		verifyEquals(airTicket.getTextInDynamicPopUpAndTitle("com.VCB:id/tvTitle"), DomesticAirTicketBooking_Data.Message.EMPTY_DEPARTURE_POINT);

		log.info("TC_06_Step 05: Click Dong y ");
		airTicket.clickToDynamicButton("Đồng ý");

	}

	@Test
	public void TC_07_KiemTraDanhSachDiemDen() {
		log.info("TC_07_Step 01: Click Khoi hanh ");
		airTicket.clickToDynamicTextOrButtonLink("Khởi hành");

		log.info("TC_07_Step 02: Chon Ha Noi ");
		airTicket.clickToDynamicTextOrButtonLink(DomesticAirTicketBooking_Data.validInput.HANOI_PLACE);

		log.info("TC_07_Step 03: Click Diem den ");
		airTicket.clickToDynamicTextOrButtonLink("Điểm đến");

		log.info("TC_07_Step 04: Kiem tra text chon diem den hien thi");
		verifyEquals(airTicket.getTextInDynamicTextBox("com.VCB:id/edtSearch"), "Chọn điểm đến");

		log.info("TC_07_Step 05: Kiem tra text diem den gan day ");
		verifyTrue(airTicket.isDynamicTextDisplayed("Điểm đến gần đây"));

		log.info("TC_07_Step 06: Kiem tra mau dia diem ");
		verifyTrue(airTicket.isDynamicPlaceAndCustomerNameDisplayed(DomesticAirTicketBooking_Data.validInput.HCM_CODE, DomesticAirTicketBooking_Data.validInput.HCM_PLACE));

	}

	@Test
	public void TC_08_KiemTraChonDiemDen() {
		log.info("TC_08_Step 01: Click TP Ho CHi Minh ");
		airTicket.clickToDynamicTextOrButtonLink(DomesticAirTicketBooking_Data.validInput.HCM_PLACE);

		log.info("TC_08_Step 02: Kiem tra TP Ho Chi Minh duoc chon ");
		verifyEquals(airTicket.getTextInDynamicDropDownByLabel("Điểm đến", "com.VCB:id/tvValue"), DomesticAirTicketBooking_Data.validInput.HCM_PLACE);

		log.info("TC_08_Step 03: Click diem den ");
		airTicket.clickToDynamicTextOrButtonLink("Điểm đến");

		log.info("TC_08_Step 04: Chon Da Nang ");
		airTicket.clickToDynamicTextOrButtonLink("Đà Nẵng");

		log.info("TC_08_Step 05: Kiem tra Da Nang hien thi ");
		verifyEquals(airTicket.getTextInDynamicDropDownByLabel("Điểm đến", "com.VCB:id/tvValue"), "Đà Nẵng");

		log.info("TC_08_Step 06: Click Quay lai ");
		airTicket.clickToDynamicIcon("com.VCB:id/ivTitleLeft");
	}

	@Test
	public void TC_09_KiemTraKhongChonDiemDen() {
		log.info("TC_09_Step 01: Click dat ve may bay noi dia");
		airTicket.clickToDynamicTextOrButtonLink("Đặt vé máy bay Nội địa");

		log.info("TC_09_Step 02: Click Dong y");
		airTicket.clickToDynamicButton("Đồng ý");

		log.info("TC_09_Step 03: Click Khoi Hanh");
		airTicket.clickToDynamicTextOrButtonLink("Khởi hành");

		log.info("TC_09_Step 04: Click Ha Noi");
		airTicket.clickToDynamicTextOrButtonLink(DomesticAirTicketBooking_Data.validInput.HANOI_PLACE);

		log.info("TC_09_Step 05: Tim Chuyen bay");
		airTicket.clickToDynamicButton("Tìm chuyến bay");

		log.info("TC_09_Step 06: Kiem tra thong bao loi hien thi");
		verifyEquals(airTicket.getTextInDynamicPopUpAndTitle("com.VCB:id/tvTitle"), DomesticAirTicketBooking_Data.Message.EMPTY_ARRIVE_POINT);

		log.info("TC_09_Step 07: Click Dong y");
		airTicket.clickToDynamicButton("Đồng ý");

		log.info("TC_09_Step 08: Click quay lai");
		airTicket.clickToDynamicIcon("com.VCB:id/ivTitleLeft");

	}

	@Test
	public void TC_10_KiemTraKhongChonNgayDi() {
		log.info("TC_10_Step 01: Click Dat ve may bay noi dia");
		airTicket.clickToDynamicTextOrButtonLink("Đặt vé máy bay Nội địa");

		log.info("TC_10_Step 02: Click Dong y");
		airTicket.clickToDynamicButton("Đồng ý");

		log.info("TC_10_Step 03: Click Khoi Hanh");
		airTicket.clickToDynamicTextOrButtonLink("Khởi hành");

		log.info("TC_10_Step 04: Click Ha Noi");
		airTicket.clickToDynamicTextOrButtonLink(DomesticAirTicketBooking_Data.validInput.HANOI_PLACE);

		log.info("TC_10_Step 05: Click Diem Den");
		airTicket.clickToDynamicTextOrButtonLink("Điểm đến");

		log.info("TC_10_Step 06: Click TP Ho Chi Minh");
		airTicket.clickToDynamicTextOrButtonLink(DomesticAirTicketBooking_Data.validInput.HCM_PLACE);

		log.info("TC_10_Step 07: Click Tim Chuyen bay");
		airTicket.clickToDynamicButton("Tìm chuyến bay");

		log.info("TC_10_Step 08: Kiem tra thong bao loi hien thi");
		verifyEquals(airTicket.getTextInDynamicPopUpAndTitle("com.VCB:id/tvTitle"), DomesticAirTicketBooking_Data.Message.EMPTY_TIME_FLIGHT);

		log.info("TC_10_Step 09: Click Dong y");
		airTicket.clickToDynamicButton("Đồng ý");

		log.info("TC_10_Step 10: Click quay lai");
		airTicket.clickToDynamicIcon("com.VCB:id/ivTitleLeft");

	}

	@Test
	public void TC_11_KiemTraGiaoDienManHinhNgayDiVe1Chieu() {
		log.info("TC_11_Step 01: Click Dat ve may bay noi dia");
		airTicket.clickToDynamicTextOrButtonLink("Đặt vé máy bay Nội địa");

		log.info("TC_11_Step 02: Click Dong y");
		airTicket.clickToDynamicButton("Đồng ý");

		log.info("TC_11_Step 03: Click Ngay Đi");
		airTicket.clickToDynamicTextOrButtonLink("Ngày đi");

		log.info("TC_11_Step 03: Kiem tra icon back, icon call, va text chon ngay bay hien thi");
		verifyTrue(airTicket.isDynamicIconDisplayed("com.VCB:id/ivTitleLeft"));
		verifyTrue(airTicket.isDynamicIconDisplayed("com.VCB:id/ivTitleRight"));
		verifyTrue(airTicket.isDynamicTextDisplayed("Chọn ngày bay"));

	}

	@Test
	public void TC_12_KiemTraNgayDiNhoHonNgayHienTai() {
		LocalDate now = LocalDate.now();
		if (!(now.getDayOfMonth() + "").equals("1")) {
			log.info("TC_12_Step 01: Chon  2 ngay truoc");
			airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearMinusDays(2), twoDaysAgo);

			log.info("TC_12_Step 02: Chon ngay hom qua");
			airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearMinusDays(1), yesterday);

			log.info("TC_12_Step 03: Click xac nhan");
			airTicket.clickToDynamicButton("Xác nhận");

			log.info("TC_12_Step 04: Click quay lai");
			airTicket.clickToDynamicIcon("com.VCB:id/ivTitleLeft");

			log.info("TC_12_Step 05: Kiem tra text chon ngay hien thi");
			verifyEquals(airTicket.getTextInDynamicDropDownByLabel("Ngày đi", "com.VCB:id/tvValue"), "Chọn ngày");

			log.info("TC_12_Step 06: Kiem tra text chon ngay hien thi");
			verifyEquals(airTicket.getTextInDynamicDropDownByLabel("Ngày về", "com.VCB:id/tvValue"), "Chọn ngày");
		}
	}

	@Test
	public void TC_13_KiemTraNgayDiLonHonNgayHienTai11Thang() {

		log.info("TC_13_Step 01: Click Ngay Đi");
		airTicket.clickToDynamicTextOrButtonLink("Ngày đi");

		Calendar date = Calendar.getInstance();
		date.add(Calendar.MONTH, 11);
		String next11Month = "THÁNG " + (date.get(Calendar.MONTH) + 1) + " " + date.get(Calendar.YEAR);
		String nextDay = getForWardDay(1);
		String next2Days = getForWardDay(2);

		log.info("TC_13_Step 02: Chon  1 ngay truoc ngay hien tai 11 thang");
		airTicket.clickToDynamicDay(next11Month, nextDay);

		log.info("TC_13_Step 02: Chon 2 ngay truoc ngay hien tai 11 thang");
		airTicket.clickToDynamicDay(next11Month, next2Days);

		log.info("TC_13_Step 04: Click xac nhan");
		airTicket.clickToDynamicButton("Xác nhận");

		log.info("TC_13_Step 05: Click quay lai");
		airTicket.clickToDynamicIcon("com.VCB:id/ivTitleLeft");

		log.info("TC_13_Step 06: Kiem tra text chon ngay hien thi");
		verifyEquals(airTicket.getTextInDynamicDropDownByLabel("Ngày đi", "com.VCB:id/tvValue"), "Chọn ngày");

		log.info("TC_13_Step 07: Kiem tra text chon ngay hien thi");
		verifyEquals(airTicket.getTextInDynamicDropDownByLabel("Ngày về", "com.VCB:id/tvValue"), "Chọn ngày");

	}

	@Test
	public void TC_14_KiemTracChonjNgayHopLeVoiVe1Chieu() {
		log.info("TC_14_Step 01: Click Mot Chieu");
		airTicket.clickToDynamicTextOrButtonLink("Một chiều");

		log.info("TC_14_Step 02: Click Ngay Đi");
		airTicket.clickToDynamicTextOrButtonLink("Ngày đi");

		log.info("TC_14_Step 03: Click ngay mai");
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(1), getForWardDay(1));

		log.info("TC_14_Step 04: Click xac nhan");
		airTicket.clickToDynamicButton("Xác nhận");

		log.info("TC_14_Step 05: Kiem tra ngay da chon hien thi");
		verifyEquals(airTicket.getTextInDynamicDropDownByLabel("Ngày đi", "com.VCB:id/tvValue"), getForwardDate(1));

		log.info("TC_14_Step 12: Click quay lai");
		airTicket.clickToDynamicIcon("com.VCB:id/ivTitleLeft");

	}

	@Test
	public void TC_15_KiemTraGiaoDienChonNgayVeKhuHoi() {

		log.info("TC_09_Step 01: Click Dat ve may bay noi dia");
		airTicket.clickToDynamicTextOrButtonLink("Đặt vé máy bay Nội địa");

		log.info("TC_09_Step 02: Click Dong y");
		airTicket.clickToDynamicButton("Đồng ý");

		log.info("TC_15_Step 01: Click Khu Hoi");
		airTicket.clickToDynamicTextOrButtonLink("Khứ hồi");

		log.info("TC_15_Step 02: Click Ngay Đi");
		airTicket.clickToDynamicTextOrButtonLink("Ngày đi");

		log.info("TC_15_Step 03: Kiem tra giao dien cua chon ngay ve khu hoi");
		verifyTrue(airTicket.isDynamicIconDisplayed("com.VCB:id/ivTitleLeft"));
		verifyTrue(airTicket.isDynamicIconDisplayed("com.VCB:id/ivTitleRight"));
		verifyTrue(airTicket.isDynamicTextDisplayed("Chọn ngày bay"));
		verifyTrue(airTicket.isDynamicTextDisplayed("Ngày đi"));
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvNgayDi"), "Chọn ngày");
		verifyTrue(airTicket.isDynamicTextDisplayed("Ngày về"));
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvNgayVe"), "Chọn ngày");
	}

	@Test
	public void TC_16_KiemTracChonNgayHopLeVoiVe2Chieu() {

		log.info("TC_16_Step 03: Click Chon Ngay Di va Ngay Ve");
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(1), getForWardDay(1));
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(2), getForWardDay(2));

		log.info("TC_16_Step 04: Click xac nhan");
		airTicket.clickToDynamicButton("Xác nhận");

		log.info("TC_16_Step 05: Kiem tra ngay di ngay ve da duoc chon");
		verifyEquals(airTicket.getTextInDynamicDropDownByLabel("Ngày đi", "com.VCB:id/tvValue"), getForwardDate(1));
		verifyEquals(airTicket.getTextInDynamicDropDownByLabel("Ngày về", "com.VCB:id/tvValue"), getForwardDate(2));

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();
	}

}
