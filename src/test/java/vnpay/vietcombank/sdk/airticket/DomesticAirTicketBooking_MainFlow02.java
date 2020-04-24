package vnpay.vietcombank.sdk.airticket;

import java.io.IOException;

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
import pageObjects.TransactionReportPageObject;
import pageObjects.sdk.airTicketBooking.DynamicAirTicketBookingObjects;
import vnpay.vietcombank.sdk.airticket.data.DomesticAirTicketBooking_Data;

public class DomesticAirTicketBooking_MainFlow02 extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransactionReportPageObject transactionReport;
	private DynamicAirTicketBookingObjects airTicket;
	private String fifthDay = getForWardDay(5);
	private String ninthDay = getForWardDay(9);
	private String ticketPrice, payID, transactionID, fee, flightCode01, departureTime01, arrivalTime01, duration01, flightCode02, departureTime02, arrivalTime02, duration02, time;

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		if (deviceType.contains("android")) {
			driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		} else if (deviceType.contains("ios")) {
			driver = openIOSApp(deviceName, udid, url);
		}
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
		homePage = PageFactoryManager.getHomePageObject(driver);

	}

	@Parameters({ "otp" })
	@Test
	public void TC_01_DatVeMayBayNoiDiaKhuHoiThanhCong_1Nguoi_ThanhToanOTP(String otp) {

		log.info("TC_01_Step_01: Click Dat ve may bay");
		homePage.clickToDynamicIcon(driver, "Đặt vé máy bay");
		airTicket = PageFactoryManager.getDynamicAirTicketBooking(driver);

		log.info("TC_01_Step_02: Click dat ve may bay noi dia ");
		airTicket = PageFactoryManager.getDynamicAirTicketBooking(driver);
		airTicket.clickToDynamicTextOrButtonLink("Đặt vé máy bay Nội địa");

		log.info("TC_01_Step_03: Click Dong y ");
		airTicket.clickToDynamicButton("Đồng ý");

		log.info("TC_01_Step_04: Chon khu hoi ");
		airTicket.clickToDynamicTextOrButtonLink("Khứ hồi");

		log.info("TC_01_Step_05: Chon diem khoi hanh");
		airTicket.clickToDynamicTextOrButtonLink("Khởi hành");
		airTicket.clickToDynamicTextOrButtonLink("Hà Nội");

		log.info("TC_01_Step_06: Chon diem den");
		airTicket.clickToDynamicTextOrButtonLink("Điểm đến");
		airTicket.clickToDynamicTextOrButtonLink("TP Hồ Chí Minh");

		log.info("TC_01_Step_07: Chon ngay di / ngay ve");
		airTicket.clickToDynamicTextOrButtonLink("Ngày đi");
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(5), fifthDay);
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(9), ninthDay);
		airTicket.clickToDynamicButton("Xác nhận");

		log.info("TC_01_Step_08: Tim chuyen bay");
		airTicket.clickToDynamicButton("Tìm chuyến bay");
		time = airTicket.getDynamicTextByID("com.VCB:id/tvTitle2");

		log.info("TC_01_Step_09_1: Chon chuyen bay chieu di");
		airTicket.clickToDynamicFlight2WayCode("com.VCB:id/recy_chieudi", "0");
		flightCode01 = airTicket.getDynamicFlightData("com.VCB:id/recy_chieudi", "0", "com.VCB:id/tv_flightcode_internal");
		departureTime01 = airTicket.getDynamicFlightData("com.VCB:id/recy_chieudi", "0", "com.VCB:id/tv_time_roundtrip_internal");
		arrivalTime01 = airTicket.getDynamicFlightData("com.VCB:id/recy_chieudi", "0", "com.VCB:id/tv_time_roundtrip_internal_arrival");
		duration01 = airTicket.getDynamicFlightData("com.VCB:id/recy_chieudi", "0", "com.VCB:id/tvDuration");

		log.info("TC_01_Step_09_2: Chon chuyen bay chieu ve");
		airTicket.clickToDynamicFlight2WayCode("com.VCB:id/recy_chieuve", "0");
		flightCode02 = airTicket.getDynamicFlightData("com.VCB:id/recy_chieuve", "0", "com.VCB:id/tv_flightcode_internal");
		departureTime02 = airTicket.getDynamicFlightData("com.VCB:id/recy_chieuve", "0", "com.VCB:id/tv_time_roundtrip_internal");
		arrivalTime02 = airTicket.getDynamicFlightData("com.VCB:id/recy_chieuve", "0", "com.VCB:id/tv_time_roundtrip_internal_arrival");
		duration02 = airTicket.getDynamicFlightData("com.VCB:id/recy_chieuve", "0", "com.VCB:id/tvDuration");

		log.info("TC_01_Step_09_3: Click dat ve");
		ticketPrice = airTicket.getDynamicTextByID("com.VCB:id/tv_Sum_amount_flight_selected");
		airTicket.clickToDynamicButton("Đặt vé");

		log.info("TC_01_Step 10: Dien Ten Nguoi lon ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_NAME, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_hoten");

		log.info("TC_01_Step 11: Chon gioi tinh nu");
		airTicket.checkToDynamicTextOrDropDownByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/tv_NuContact");

		log.info("TC_01_Step 12: Dien Email  ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_EMAIL, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_email");

		log.info("TC_01_Step 13: Dien Phone ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_PHONE, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_phonenumber");

		log.info("TC_01_Step 14: Dien Noi dung");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.CONTENT, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_content");

		log.info("TC_01_Step 15: Click vao check box Dung lam thong tin hanh khach bay");
		airTicket.clickToDynamicCheckBoxByID("com.VCB:id/iv_check_active");

		log.info("TC_01_Step 16: Click Tiep Tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_01_Step 17: Kiem tra man hinh xac nhan chuyen bay hien thi");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvTitle1"), "Xác nhận chuyến bay");

		log.info("TC_01_Step 18_1: Kiem tra dia diem khoi hanh");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem1"), "Hà Nội");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem2"), "HAN");

		log.info("TC_01_Step 18_2: Kiem tra dia diem den");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem3"), "TP Hồ Chí Minh");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem4"), "SGN");

		log.info("TC_01_Step 19_1: Kiem tra thong tin chieu di");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextLeft1"), "CHIỀU ĐI");

		log.info("TC_01_Step 19_2: Kiem tra thong tin ma chieu di");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvFlightId"), flightCode01);

		log.info("TC_01_Step 19_3: Kiem tra thong tin chieu di : ngay di");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextLeft2"), getRawSplitStringIndex(time, "-", 0).trim());

		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextRight1"), getRawSplitStringIndex(time, "-", 0).trim());

		log.info("TC_01_Step 19_4: Kiem tra thong tin chieu di : gio di ");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextLeft3"), "HAN " + departureTime01);

		log.info("TC_01_Step 19_5: Kiem tra thong tin chieu di : gio den");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextRight2"), "SGN " + arrivalTime01);

		log.info("TC_01_Step 19_6: Kiem trathông tin chieu di : thoi gian bay");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvDuration"), duration01);

		log.info("TC_01_Step 20_1: Kiem tra thong tin chieu ve");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextLeft1"), "CHIỀU VỀ");

		log.info("TC_01_Step 20_2: Kiem tra thong tin ma chieu ve");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvFlightId"), flightCode02);

		log.info("TC_01_Step 20_3: Kiem tra thong tin chieu ve : ngay di");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextLeft2"), getRawSplitStringIndex(time, "-", 1).trim());

		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextRight1"), getRawSplitStringIndex(time, "-", 1).trim());

		log.info("TC_01_Step 20_4: Kiem tra thong tin chieu ve : gio di ");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextLeft3"), "SGN " + departureTime02);

		log.info("TC_01_Step 20_5: Kiem tra thong tin chieu ve : gio den");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextRight2"), "HAN " + arrivalTime02);

		log.info("TC_01_Step 20_6: Kiem trathông tin chieu ve : thoi gian bay");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvDuration"), duration02);

		log.info("TC_01_Step 21_1: Kiem tra tong so tien hien thi dung");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Tổng tiền", "com.VCB:id/tvText2"), ticketPrice);

		log.info("TC_01_Step_21_2: Click checkbox dong y dieu khoan dat ve");
		airTicket.clickToDynamicCheckBoxByID("com.VCB:id/checkBoxCondition");

		log.info("TC_01_Step_21_3: Click Thanh toan");
		airTicket.clickToDynamicButton("Thanh toán");

		log.info("TC_01_Step_22_1: Hien thị man hinh xac nhan thong tin ");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvTitleBar"), "Thông tin thanh toán");

		log.info("TC_01_Step_22_2: Chọn tai khoan nguon");
		airTicket.clickToDynamicTextByID("com.VCB:id/number_account");
		airTicket.clickToDynamicTextOrButtonLink(DomesticAirTicketBooking_Data.validInput.ACCOUNT2);

		log.info("TC_01_Step_22_3: Xac nhan ma thanh toan");
		payID = airTicket.getAirTicketPriceInfo1Way("Mã thanh toán", "com.VCB:id/tvContent");

		log.info("TC_01_Step_22_4: Xac nhan chieu di bay ");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("4", "Chiều bay"), "HAN → SGN");

		log.info("TC_01_Step_22_5: Xac nhan so hieu chuyen bay chieu di");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("5", "Số hiệu chuyến bay").replace("-", ""), flightCode01);

		log.info("TC_01_Step_22_6: Xac nhan thoi gian bay chieu di");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("6", "Thời gian bay"), departureTime01 + " - " + arrivalTime01);

		log.info("TC_01_Step_22_7: Xac nhan chieu ve bay ");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("9", "Chiều bay"), "SGN → HAN");

		log.info("TC_01_Step_22_8: Xac nhan so hieu chuyen bay chieu ve");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("10", "Số hiệu chuyến bay").replace("-", ""), flightCode02);

		log.info("TC_01_Step_22_6: Xac nhan thoi gian bay chieu ve");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("11", "Thời gian bay"), departureTime02 + " - " + arrivalTime02);

		log.info("TC_01_Step_22_7: Xac nhan Tong tien thanh toan");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Tổng tiền thanh toán", "com.VCB:id/tvContent"), ticketPrice);

		log.info("TC_01_Step 23: Click Tiep Tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_01_Step_24: Xac nhan giao dich");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Từ tài khoản", "com.VCB:id/tvTu_tai_khoan"), DomesticAirTicketBooking_Data.validInput.ACCOUNT2);
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Mã thanh toán", "com.VCB:id/tvBooking_number"), payID);
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Số tiền", "com.VCB:id/tvAmount"), ticketPrice);

		log.info("TC_01_Step_25: Chon phuong thuc xac thuc SMS OTP");
		airTicket.clickToDynamicTextByID("com.VCB:id/tvptxt");
		airTicket.clickToDynamicTextOrButtonLink("SMS OTP");
		fee = airTicket.getAirTicketPriceInfo1Way("Phí giao dịch", "com.VCB:id/tvPhiGiaoDich");

		log.info("TC_01_Step_26: Click Tiep Tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_01_Step_27: Nhap Ma OTP roi click Tiep tuc");
		airTicket.inputToDynamicOtp(driver, otp, "Tiếp tục");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_01_Step_28: Xac nhan hien thi man hinh thanh toan thanh cong");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Mã thanh toán", "com.VCB:id/tvContent"), payID);
		transactionID = airTicket.getAirTicketPriceInfo1Way("Mã giao dịch", "com.VCB:id/tvContent");

		log.info("TC_01_Step_29: Clich nut Home");
		airTicket.clickToDynamicIcon("com.VCB:id/ivHome");
		homePage = PageFactoryManager.getHomePageObject(driver);
	}

	@Test
	public void TC_02_DatVeMayBayNoiDiaKhuHoiThanhCong_1Nguoi_BaoCaoGiaoDich() {

		log.info("TC_02_Step_01: Mo tab Menu");
		homePage.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_5");

		log.info("TC_02_Step_02: Mo sub-menu 'Bao cao giao dich");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");
		transactionReport = PageFactoryManager.getTransactionReportPageObject(driver);

		log.info("TC_02_Step_03: An vao Dropdown 'Tat ca cac loai giao dich");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectTransType");

		log.info("TC_02_Step_04: Chon 'Thanh toan ve may bay'");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, "Thanh toán vé máy bay");

		log.info("TC_02_Step_05: An vao Dropdown 'Chon tai khoan/the");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_02_Step_06: Chon tai khoan vua thuc hien giao dich");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, DomesticAirTicketBooking_Data.validInput.ACCOUNT2);

		log.info("TC_02_Step_07: An nut Tim kiem");
		transactionReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");

		log.info("TC_02_Step_08: An vao giao dich dau tien");
		transactionReport.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_02_Step_09: Xac nhan hien thi Title 'Chi tiet giao dich'");
		verifyEquals(transactionReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Chi tiết giao dịch");

		log.info("TC_02_Step_10: Xac nhan hien thi dung ma giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Số lệnh giao dịch"), transactionID);

		log.info("TC_02_Step_11: Xac nhan hien thi so tai khoan giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Tài khoản/thẻ trích nợ"), DomesticAirTicketBooking_Data.validInput.ACCOUNT2);

		log.info("TC_02_Step_12: Xac nhan hien thi Ma Thanh toan");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Mã thanh toán"), payID);

		log.info("TC_02_Step_13: Xac nhan hien thi So tien phi");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Số tiền phí"), fee);

		log.info("TC_02_Step_14: Xac nhan hien thi Loai giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Loại giao dịch"), "Thanh toán vé máy bay");

		log.info("TC_02_Step_15: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_02_Step_16: An nut back ve man hinh menu");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		homePage = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_02_Step_17: Mo tab Home");
		homePage.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_1");
	}

	@Parameters({ "pass" })
	@Test
	public void TC_03_DatVeMayBayNoiDiaKhuHoiThanhCong_1Nguoi_ThanhToanMK(String pass) {

		log.info("TC_03_Step_01: Click Dat ve may bay");
		homePage.clickToDynamicIcon(driver, "Đặt vé máy bay");
		airTicket = PageFactoryManager.getDynamicAirTicketBooking(driver);

		log.info("TC_03_Step_02: Click dat ve may bay noi dia ");
		airTicket = PageFactoryManager.getDynamicAirTicketBooking(driver);
		airTicket.clickToDynamicTextOrButtonLink("Đặt vé máy bay Nội địa");

		log.info("TC_03_Step_03: Click Dong y ");
		airTicket.clickToDynamicButton("Đồng ý");

		log.info("TC_03_Step_04: Chon khu hoi ");
		airTicket.clickToDynamicTextOrButtonLink("Khứ hồi");

		log.info("TC_03_Step_05: Chon diem khoi hanh");
		airTicket.clickToDynamicTextOrButtonLink("Khởi hành");
		airTicket.clickToDynamicTextOrButtonLink("Hà Nội");

		log.info("TC_03_Step_06: Chon diem den");
		airTicket.clickToDynamicTextOrButtonLink("Điểm đến");
		airTicket.clickToDynamicTextOrButtonLink("TP Hồ Chí Minh");

		log.info("TC_03_Step_07: Chon ngay di / ngay ve");
		airTicket.clickToDynamicTextOrButtonLink("Ngày đi");
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(5), fifthDay);
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(9), ninthDay);
		airTicket.clickToDynamicButton("Xác nhận");

		log.info("TC_03_Step_08: Tim chuyen bay");
		airTicket.clickToDynamicButton("Tìm chuyến bay");
		time = airTicket.getDynamicTextByID("com.VCB:id/tvTitle2");

		log.info("TC_03_Step_09_1: Chon chuyen bay chieu di");
		airTicket.clickToDynamicFlight2WayCode("com.VCB:id/recy_chieudi", "0");
		flightCode01 = airTicket.getDynamicFlightData("com.VCB:id/recy_chieudi", "0", "com.VCB:id/tv_flightcode_internal");
		departureTime01 = airTicket.getDynamicFlightData("com.VCB:id/recy_chieudi", "0", "com.VCB:id/tv_time_roundtrip_internal");
		arrivalTime01 = airTicket.getDynamicFlightData("com.VCB:id/recy_chieudi", "0", "com.VCB:id/tv_time_roundtrip_internal_arrival");
		duration01 = airTicket.getDynamicFlightData("com.VCB:id/recy_chieudi", "0", "com.VCB:id/tvDuration");

		log.info("TC_03_Step_09_2: Chon chuyen bay chieu ve");
		airTicket.clickToDynamicFlight2WayCode("com.VCB:id/recy_chieuve", "0");
		flightCode02 = airTicket.getDynamicFlightData("com.VCB:id/recy_chieuve", "0", "com.VCB:id/tv_flightcode_internal");
		departureTime02 = airTicket.getDynamicFlightData("com.VCB:id/recy_chieuve", "0", "com.VCB:id/tv_time_roundtrip_internal");
		arrivalTime02 = airTicket.getDynamicFlightData("com.VCB:id/recy_chieuve", "0", "com.VCB:id/tv_time_roundtrip_internal_arrival");
		duration02 = airTicket.getDynamicFlightData("com.VCB:id/recy_chieuve", "0", "com.VCB:id/tvDuration");

		log.info("TC_03_Step_09_3: Click dat ve");
		ticketPrice = airTicket.getDynamicTextByID("com.VCB:id/tv_Sum_amount_flight_selected");
		airTicket.clickToDynamicButton("Đặt vé");

		log.info("TC_03_Step 10: Dien Ten Nguoi lon ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_NAME, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_hoten");

		log.info("TC_03_Step 11: Chon gioi tinh nu");
		airTicket.checkToDynamicTextOrDropDownByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/tv_NuContact");

		log.info("TC_03_Step 12: Dien Email  ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_EMAIL, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_email");

		log.info("TC_03_Step 13: Dien Phone ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_PHONE, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_phonenumber");

		log.info("TC_03_Step 14: Dien Noi dung");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.CONTENT, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_content");

		log.info("TC_03_Step 15: Click vao check box Dung lam thong tin hanh khach bay");
		airTicket.clickToDynamicCheckBoxByID("com.VCB:id/iv_check_active");

		log.info("TC_03_Step 16: Click Tiep Tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_03_Step 17: Kiem tra man hinh xac nhan chuyen bay hien thi");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvTitle1"), "Xác nhận chuyến bay");

		log.info("TC_03_Step 18_1: Kiem tra dia diem khoi hanh");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem1"), "Hà Nội");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem2"), "HAN");

		log.info("TC_03_Step 18_2: Kiem tra dia diem den");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem3"), "Singapore");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem4"), "SIN");

		log.info("TC_03_Step 19_1: Kiem tra thong tin chieu di");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextLeft1"), "CHIỀU ĐI");

		log.info("TC_03_Step 19_2: Kiem tra thong tin ma chieu di");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvFlightId"), flightCode01);

		log.info("TC_03_Step 19_3: Kiem tra thong tin chieu di : ngay di");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextLeft2"), getRawSplitStringIndex(time, "-", 0).trim());

		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextRight1"), getRawSplitStringIndex(time, "-", 0).trim());

		log.info("TC_03_Step 19_4: Kiem tra thong tin chieu di : gio di ");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextLeft3"), "HAN " + departureTime01);

		log.info("TC_03_Step 19_5: Kiem tra thong tin chieu di : gio den");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextRight2"), "SIN " + arrivalTime01);

		log.info("TC_03_Step 19_6: Kiem trathông tin chieu di : thoi gian bay");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvDuration"), duration01);

		log.info("TC_03_Step 20_1: Kiem tra thong tin chieu ve");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextLeft1"), "CHIỀU VỀ");

		log.info("TC_03_Step 20_2: Kiem tra thong tin ma chieu ve");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvFlightId"), flightCode02);

		log.info("TC_03_Step 20_3: Kiem tra thong tin chieu ve : ngay di");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextLeft2"), getRawSplitStringIndex(time, "-", 1).trim());

		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextRight1"), getRawSplitStringIndex(time, "-", 1).trim());

		log.info("TC_03_Step 20_4: Kiem tra thong tin chieu ve : gio di ");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextLeft3"), "SIN " + departureTime02);

		log.info("TC_03_Step 20_5: Kiem tra thong tin chieu ve : gio den");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextRight2"), "HAN " + arrivalTime02);

		log.info("TC_03_Step 20_6: Kiem tra thong tin chieu ve : thoi gian bay");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvDuration"), duration02);

		log.info("TC_03_Step 20_7: Kiem tra tong so tien hien thi dung");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Tổng tiền", "com.VCB:id/tvText2"), ticketPrice);

		log.info("TC_03_Step_20_8: Click checkbox dong y dieu khoan dat ve");
		airTicket.clickToDynamicCheckBoxByID("com.VCB:id/checkBoxCondition");

		log.info("TC_03_Step_20_9: Click Thanh toan");
		airTicket.clickToDynamicButton("Thanh toán");

		log.info("TC_03_Step_21: Chọn tai khoan nguon");
		airTicket.clickToDynamicTextByID("com.VCB:id/number_account");
		airTicket.clickToDynamicTextOrButtonLink(DomesticAirTicketBooking_Data.validInput.ACCOUNT2);

		log.info("TC_03_Step_22_1: Xac nhan ma thanh toan");
		payID = airTicket.getAirTicketPriceInfo1Way("Mã thanh toán", "com.VCB:id/tvContent");

		log.info("TC_03_Step_22_2: Xac nhan chieu di bay ");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("4", "Chiều bay"), "HAN → SIN");

		log.info("TC_03_Step_22_3: Xac nhan so hieu chuyen bay chieu di");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("5", "Số hiệu chuyến bay").replace("-", ""), flightCode01);

		log.info("TC_03_Step_22_4: Xac nhan thoi gian bay chieu di");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("6", "Thời gian bay"), departureTime01 + " - " + arrivalTime01);

		log.info("TC_03_Step_22_5: Xac nhan chieu ve bay ");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("9", "Chiều bay"), "SIN → HAN");

		log.info("TC_03_Step_22_6: Xac nhan so hieu chuyen bay chieu ve");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("10", "Số hiệu chuyến bay").replace("-", ""), flightCode02);

		log.info("TC_03_Step_22_7: Xac nhan thoi gian bay chieu ve");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("11", "Thời gian bay"), departureTime02 + " - " + arrivalTime02);

		log.info("TC_03_Step_22_8: Xac nhan Tong tien thanh toan");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Tổng tiền thanh toán", "com.VCB:id/tvContent"), ticketPrice);

		log.info("TC_03_Step_22_9: Click Tiep tuc");
		payID = airTicket.getAirTicketPriceInfo1Way("Mã thanh toán", "com.VCB:id/tvContent");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Tổng tiền thanh toán", "com.VCB:id/tvContent"), ticketPrice);

		log.info("TC_03_Step 23: Click Tiep Tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_03_Step_24: Xac nhan giao dich");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Từ tài khoản", "com.VCB:id/tvTu_tai_khoan"), DomesticAirTicketBooking_Data.validInput.ACCOUNT2);
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Mã thanh toán", "com.VCB:id/tvBooking_number"), payID);
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Số tiền", "com.VCB:id/tvAmount"), ticketPrice);

		log.info("TC_03_Step_25: Chon phuong thuc xac thuc SMS OTP");
		airTicket.clickToDynamicTextByID("com.VCB:id/tvptxt");
		airTicket.clickToDynamicTextOrButtonLink("SMS OTP");
		fee = airTicket.getAirTicketPriceInfo1Way("Phí giao dịch", "com.VCB:id/tvPhiGiaoDich");

		log.info("TC_03_Step_26: Click Tiep Tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_03_Step_27: Nhap Ma OTP roi click Tiep tuc");
		airTicket.inputToDynamicPopupPasswordInput(driver, pass, "Tiếp tục");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_03_Step_28: Xac nhan hien thi man hinh thanh toan thanh cong");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Mã thanh toán", "com.VCB:id/tvContent"), payID);
		transactionID = airTicket.getAirTicketPriceInfo1Way("Mã giao dịch", "com.VCB:id/tvContent");

		log.info("TC_03_Step_29: Clich nut Home");
		airTicket.clickToDynamicIcon("com.VCB:id/ivHome");
		homePage = PageFactoryManager.getHomePageObject(driver);
	}

	@Test
	public void TC_04_DatVeMayBayNoiDiaKhuHoiThanhCong_1Nguoi_BaoCaoGiaoDich() {

		log.info("TC_04_Step_01: Mo tab Menu");
		homePage.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_5");

		log.info("TC_04_Step_02: Mo sub-menu 'Bao cao giao dich");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");
		transactionReport = PageFactoryManager.getTransactionReportPageObject(driver);

		log.info("TC_04_Step_03: An vao Dropdown 'Tat ca cac loai giao dich");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectTransType");

		log.info("TC_04_Step_04: Chon 'Thanh toan ve may bay'");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, "Thanh toán vé máy bay");

		log.info("TC_04_Step_05: An vao Dropdown 'Chon tai khoan/the");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_04_Step_06: Chon tai khoan vua thuc hien giao dich");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, DomesticAirTicketBooking_Data.validInput.ACCOUNT2);

		log.info("TC_04_Step_07: An nut Tim kiem");
		transactionReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");

		log.info("TC_04_Step_08: An vao giao dich dau tien");
		transactionReport.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_04_Step_09: Xac nhan hien thi Title 'Chi tiet giao dich'");
		verifyEquals(transactionReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Chi tiết giao dịch");

		log.info("TC_04_Step_10: Xac nhan hien thi dung ma giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Số lệnh giao dịch"), transactionID);

		log.info("TC_04_Step_11: Xac nhan hien thi so tai khoan giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Tài khoản/thẻ trích nợ"), DomesticAirTicketBooking_Data.validInput.ACCOUNT2);

		log.info("TC_04_Step_12: Xac nhan hien thi Ma Thanh toan");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Mã thanh toán"), payID);

		log.info("TC_04_Step_13: Xac nhan hien thi So tien phi");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Số tiền phí"), fee);

		log.info("TC_04_Step_14: Xac nhan hien thi Loai giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Loại giao dịch"), "Thanh toán vé máy bay");

		log.info("TC_04_Step_15: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_04_Step_16: An nut back ve man hinh menu");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		homePage = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_04_Step_17: Mo tab Home");
		homePage.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_1");
	}

	@Parameters({ "otp" })
	@Test
	public void TC_05_DatVeMayBayQuocTeKhuHoiThanhCong_1Nguoi_ThanhToanOTP(String otp) {

		log.info("TC_05_Step_01: Click Dat ve may bay");
		homePage.clickToDynamicIcon(driver, "Đặt vé máy bay");
		airTicket = PageFactoryManager.getDynamicAirTicketBooking(driver);

		log.info("TC_05_Step_02: Click dat ve may bay quoc te ");
		airTicket = PageFactoryManager.getDynamicAirTicketBooking(driver);
		airTicket.clickToDynamicTextOrButtonLink("Đặt vé máy bay Quốc tế");

		log.info("TC_05_Step_03: Click Dong y ");
		airTicket.clickToDynamicButton("Đồng ý");

		log.info("TC_05_Step_04: Chon khu hoi ");
		airTicket.clickToDynamicTextOrButtonLink("Khứ hồi");

		log.info("TC_05_Step_05: Chon diem khoi hanh");
		airTicket.clickToDynamicTextOrButtonLink("Khởi hành");
		airTicket.clickToDynamicTextOrButtonLink("Hà Nội");

		log.info("TC_05_Step_06: Chon diem den");
		airTicket.clickToDynamicTextOrButtonLink("Điểm đến");
		airTicket.clickToDynamicTextOrButtonLink("Singapore");

		log.info("TC_05_Step_07: Chon ngay di / ngay ve");
		airTicket.clickToDynamicTextOrButtonLink("Ngày đi");
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(5), fifthDay);
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(9), ninthDay);
		airTicket.clickToDynamicButton("Xác nhận");

		log.info("TC_05_Step_08: Tim chuyen bay");
		airTicket.clickToDynamicButton("Tìm chuyến bay");
		time = airTicket.getDynamicTextByID("com.VCB:id/tvTitle2");

		log.info("TC_05_Step_09_1: Chon chuyen bay chieu di");
		airTicket.clickToDynamicFlight2WayCode("com.VCB:id/recy_chieudi", "0");
		flightCode01 = airTicket.getDynamicFlightData("com.VCB:id/recy_chieudi", "0", "com.VCB:id/tv_flightcode_internal");
		departureTime01 = airTicket.getDynamicFlightData("com.VCB:id/recy_chieudi", "0", "com.VCB:id/tv_time_roundtrip_internal");
		arrivalTime01 = airTicket.getDynamicFlightData("com.VCB:id/recy_chieudi", "0", "com.VCB:id/tv_time_roundtrip_internal_arrival");
		duration01 = airTicket.getDynamicFlightData("com.VCB:id/recy_chieudi", "0", "com.VCB:id/tvDuration");

		log.info("TC_05_Step_09_2: Chon chuyen bay chieu ve");
		airTicket.clickToDynamicFlight2WayCode("com.VCB:id/recy_chieuve", "0");
		flightCode02 = airTicket.getDynamicFlightData("com.VCB:id/recy_chieuve", "0", "com.VCB:id/tv_flightcode_internal");
		departureTime02 = airTicket.getDynamicFlightData("com.VCB:id/recy_chieuve", "0", "com.VCB:id/tv_time_roundtrip_internal");
		arrivalTime02 = airTicket.getDynamicFlightData("com.VCB:id/recy_chieuve", "0", "com.VCB:id/tv_time_roundtrip_internal_arrival");
		duration02 = airTicket.getDynamicFlightData("com.VCB:id/recy_chieuve", "0", "com.VCB:id/tvDuration");

		log.info("TC_05_Step_09_3: Click dat ve");
		ticketPrice = airTicket.getDynamicTextByID("com.VCB:id/tv_Sum_amount_flight_selected");
		airTicket.clickToDynamicButton("Đặt vé");

		log.info("TC_05_Step 10: Dien Ten Nguoi lon ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_NAME, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_hoten");

		log.info("TC_05_Step 11: Chon gioi tinh nu");
		airTicket.checkToDynamicTextOrDropDownByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/tv_NuContact");

		log.info("TC_05_Step 12: Dien Email  ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_EMAIL, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_email");

		log.info("TC_05_Step 13: Dien Phone ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_PHONE, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_phonenumber");

		log.info("TC_05_Step 14: Dien Noi dung");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.CONTENT, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_content");

		log.info("TC_05_Step 15: Click vao check box Dung lam thong tin hanh khach bay");
		airTicket.clickToDynamicCheckBoxByID("com.VCB:id/iv_check_active");

		log.info("TC_05_Step 16: Click Tiep Tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_05_Step 17: Kiem tra man hinh xac nhan chuyen bay hien thi");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvTitle1"), "Xác nhận chuyến bay");

		log.info("TC_05_Step 18_1: Kiem tra dia diem khoi hanh");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem1"), "Hà Nội");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem2"), "HAN");

		log.info("TC_05_Step 18_2: Kiem tra dia diem den");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem3"), "Singapore");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem4"), "SIN");

		log.info("TC_05_Step 19_1: Kiem tra thong tin chieu di");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextLeft1"), "CHIỀU ĐI");

		log.info("TC_05_Step 19_2: Kiem tra thong tin ma chieu di");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvFlightId"), flightCode01);

		log.info("TC_05_Step 19_3: Kiem tra thong tin chieu di : ngay di");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextLeft2"), getRawSplitStringIndex(time, "-", 0).trim());

		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextRight1"), getRawSplitStringIndex(time, "-", 0).trim());

		log.info("TC_05_Step 19_4: Kiem tra thong tin chieu di : gio di ");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextLeft3"), "HAN " + departureTime01);

		log.info("TC_05_Step 19_5: Kiem tra thong tin chieu di : gio den");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextRight2"), "SIN " + arrivalTime01);

		log.info("TC_05_Step 19_6: Kiem trathông tin chieu di : thoi gian bay");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvDuration"), duration01);

		log.info("TC_05_Step 20_1: Kiem tra thong tin chieu ve");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextLeft1"), "CHIỀU VỀ");

		log.info("TC_05_Step 20_2: Kiem tra thong tin ma chieu ve");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvFlightId"), flightCode02);

		log.info("TC_05_Step 20_3: Kiem tra thong tin chieu ve : ngay di");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextLeft2"), getRawSplitStringIndex(time, "-", 1).trim());

		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextRight1"), getRawSplitStringIndex(time, "-", 1).trim());

		log.info("TC_05_Step 20_4: Kiem tra thong tin chieu ve : gio di ");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextLeft3"), "SIN " + departureTime02);

		log.info("TC_05_Step 20_5: Kiem tra thong tin chieu ve : gio den");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextRight2"), "HAN " + arrivalTime02);

		log.info("TC_05_Step 20_6: Kiem trathông tin chieu ve : thoi gian bay");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvDuration"), duration02);

		log.info("TC_05_Step 18: Kiem tra tong so tien hien thi dung");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Tổng tiền", "com.VCB:id/tvText2"), ticketPrice);

		log.info("TC_05_Step_19: Click checkbox dong y dieu khoan dat ve");
		airTicket.clickToDynamicCheckBoxByID("com.VCB:id/checkBoxCondition");

		log.info("TC_05_Step_20: Click Thanh toan");
		airTicket.clickToDynamicButton("Thanh toán");

		log.info("TC_05_Step_21: Chọn tai khoan nguon");
		airTicket.clickToDynamicTextByID("com.VCB:id/number_account");
		airTicket.clickToDynamicTextOrButtonLink(DomesticAirTicketBooking_Data.validInput.ACCOUNT2);

		log.info("TC_05_Step_22_3: Xac nhan ma thanh toan");
		payID = airTicket.getAirTicketPriceInfo1Way("Mã thanh toán", "com.VCB:id/tvContent");

		log.info("TC_05_Step_22_4: Xac nhan chieu di bay ");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("4", "Chiều bay"), "HAN → SIN");

		log.info("TC_05_Step_22_5: Xac nhan so hieu chuyen bay chieu di");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("5", "Số hiệu chuyến bay").replace("-", ""), flightCode01);

		log.info("TC_05_Step_22_6: Xac nhan thoi gian bay chieu di");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("6", "Thời gian bay"), departureTime01 + " - " + arrivalTime01);

		log.info("TC_05_Step_22_7: Xac nhan chieu ve bay ");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("9", "Chiều bay"), "SIN → HAN");

		log.info("TC_05_Step_22_8: Xac nhan so hieu chuyen bay chieu ve");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("10", "Số hiệu chuyến bay").replace("-", ""), flightCode02);

		log.info("TC_05_Step_22_6: Xac nhan thoi gian bay chieu ve");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("11", "Thời gian bay"), departureTime02 + " - " + arrivalTime02);

		log.info("TC_05_Step_22_7: Xac nhan Tong tien thanh toan");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Tổng tiền thanh toán", "com.VCB:id/tvContent"), ticketPrice);

		log.info("TC_05_Step_22: Click Tiep tuc");
		payID = airTicket.getAirTicketPriceInfo1Way("Mã thanh toán", "com.VCB:id/tvContent");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Tổng tiền thanh toán", "com.VCB:id/tvContent"), ticketPrice);

		log.info("TC_05_Step 23: Click Tiep Tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_05_Step_24: Xac nhan giao dich");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Từ tài khoản", "com.VCB:id/tvTu_tai_khoan"), DomesticAirTicketBooking_Data.validInput.ACCOUNT2);
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Mã thanh toán", "com.VCB:id/tvBooking_number"), payID);
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Số tiền", "com.VCB:id/tvAmount"), ticketPrice);

		log.info("TC_05_Step_25: Chon phuong thuc xac thuc SMS OTP");
		airTicket.clickToDynamicTextByID("com.VCB:id/tvptxt");
		airTicket.clickToDynamicTextOrButtonLink("SMS OTP");
		fee = airTicket.getAirTicketPriceInfo1Way("Phí giao dịch", "com.VCB:id/tvPhiGiaoDich");

		log.info("TC_05_Step_26: Click Tiep Tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_05_Step_27: Nhap Ma OTP roi click Tiep tuc");
		airTicket.inputToDynamicOtp(driver, otp, "Tiếp tục");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_05_Step_28: Xac nhan hien thi man hinh thanh toan thanh cong");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Mã thanh toán", "com.VCB:id/tvContent"), payID);
		transactionID = airTicket.getAirTicketPriceInfo1Way("Mã giao dịch", "com.VCB:id/tvContent");

		log.info("TC_05_Step_29: Clich nut Home");
		airTicket.clickToDynamicIcon("com.VCB:id/ivHome");
		homePage = PageFactoryManager.getHomePageObject(driver);
	}

	@Test
	public void TC_06_DatVeMayBayQuocTeKhuHoiThanhCong_1Nguoi_BaoCaoGiaoDich() {

		log.info("TC_06_Step_01: Mo tab Menu");
		homePage.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_5");

		log.info("TC_06_Step_02: Mo sub-menu 'Bao cao giao dich");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");
		transactionReport = PageFactoryManager.getTransactionReportPageObject(driver);

		log.info("TC_06_Step_03: An vao Dropdown 'Tat ca cac loai giao dich");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectTransType");

		log.info("TC_06_Step_04: Chon 'Thanh toan ve may bay'");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, "Thanh toán vé máy bay");

		log.info("TC_06_Step_05: An vao Dropdown 'Chon tai khoan/the");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_06_Step_06: Chon tai khoan vua thuc hien giao dich");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, DomesticAirTicketBooking_Data.validInput.ACCOUNT2);

		log.info("TC_06_Step_07: An nut Tim kiem");
		transactionReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");

		log.info("TC_06_Step_08: An vao giao dich dau tien");
		transactionReport.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_06_Step_09: Xac nhan hien thi Title 'Chi tiet giao dich'");
		verifyEquals(transactionReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Chi tiết giao dịch");

		log.info("TC_06_Step_10: Xac nhan hien thi dung ma giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Số lệnh giao dịch"), transactionID);

		log.info("TC_06_Step_11: Xac nhan hien thi so tai khoan giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Tài khoản/thẻ trích nợ"), DomesticAirTicketBooking_Data.validInput.ACCOUNT2);

		log.info("TC_06_Step_12: Xac nhan hien thi Ma Thanh toan");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Mã thanh toán"), payID);

		log.info("TC_06_Step_13: Xac nhan hien thi So tien phi");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Số tiền phí"), fee);

		log.info("TC_06_Step_14: Xac nhan hien thi Loai giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Loại giao dịch"), "Thanh toán vé máy bay");

		log.info("TC_06_Step_15: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_06_Step_16: An nut back ve man hinh menu");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		homePage = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_06_Step_17: Mo tab Home");
		homePage.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_1");
	}

	@Parameters({ "pass" })
	@Test
	public void TC_07_DatVeMayBayQuocTeKhuHoiThanhCong_1Nguoi_ThanhToanMK(String pass) {

		log.info("TC_07_Step_01: Click Dat ve may bay");
		homePage.clickToDynamicIcon(driver, "Đặt vé máy bay");
		airTicket = PageFactoryManager.getDynamicAirTicketBooking(driver);

		log.info("TC_07_Step_02: Click dat ve may bay noi dia ");
		airTicket = PageFactoryManager.getDynamicAirTicketBooking(driver);
		airTicket.clickToDynamicTextOrButtonLink("Đặt vé máy bay Nội địa");

		log.info("TC_07_Step_03: Click Dong y ");
		airTicket.clickToDynamicButton("Đồng ý");

		log.info("TC_07_Step_04: Chon khu hoi ");
		airTicket.clickToDynamicTextOrButtonLink("Khứ hồi");

		log.info("TC_07_Step_05: Chon diem khoi hanh");
		airTicket.clickToDynamicTextOrButtonLink("Khởi hành");
		airTicket.clickToDynamicTextOrButtonLink("Hà Nội");

		log.info("TC_07_Step_06: Chon diem den");
		airTicket.clickToDynamicTextOrButtonLink("Điểm đến");
		airTicket.clickToDynamicTextOrButtonLink("TP Hồ Chí Minh");

		log.info("TC_07_Step_07: Chon ngay di / ngay ve");
		airTicket.clickToDynamicTextOrButtonLink("Ngày đi");
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(5), fifthDay);
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(9), ninthDay);
		airTicket.clickToDynamicButton("Xác nhận");

		log.info("TC_07_Step_08: Tim chuyen bay");
		airTicket.clickToDynamicButton("Tìm chuyến bay");

		log.info("TC_07_Step_08: Tim chuyen bay");
		airTicket.clickToDynamicButton("Tìm chuyến bay");
		time = airTicket.getDynamicTextByID("com.VCB:id/tvTitle2");

		log.info("TC_07_Step_09_1: Chon chuyen bay chieu di");
		airTicket.clickToDynamicFlight2WayCode("com.VCB:id/recy_chieudi", "0");
		flightCode01 = airTicket.getDynamicFlightData("com.VCB:id/recy_chieudi", "0", "com.VCB:id/tv_flightcode_internal");
		departureTime01 = airTicket.getDynamicFlightData("com.VCB:id/recy_chieudi", "0", "com.VCB:id/tv_time_roundtrip_internal");
		arrivalTime01 = airTicket.getDynamicFlightData("com.VCB:id/recy_chieudi", "0", "com.VCB:id/tv_time_roundtrip_internal_arrival");
		duration01 = airTicket.getDynamicFlightData("com.VCB:id/recy_chieudi", "0", "com.VCB:id/tvDuration");

		log.info("TC_07_Step_09_2: Chon chuyen bay chieu ve");
		airTicket.clickToDynamicFlight2WayCode("com.VCB:id/recy_chieuve", "0");
		flightCode02 = airTicket.getDynamicFlightData("com.VCB:id/recy_chieuve", "0", "com.VCB:id/tv_flightcode_internal");
		departureTime02 = airTicket.getDynamicFlightData("com.VCB:id/recy_chieuve", "0", "com.VCB:id/tv_time_roundtrip_internal");
		arrivalTime02 = airTicket.getDynamicFlightData("com.VCB:id/recy_chieuve", "0", "com.VCB:id/tv_time_roundtrip_internal_arrival");
		duration02 = airTicket.getDynamicFlightData("com.VCB:id/recy_chieuve", "0", "com.VCB:id/tvDuration");

		log.info("TC_07_Step_09_3: Click dat ve");
		ticketPrice = airTicket.getDynamicTextByID("com.VCB:id/tv_Sum_amount_flight_selected");
		airTicket.clickToDynamicButton("Đặt vé");

		log.info("TC_07_Step 10: Dien Ten Nguoi lon ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_NAME, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_hoten");

		log.info("TC_07_Step 11: Chon gioi tinh nu");
		airTicket.checkToDynamicTextOrDropDownByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/tv_NuContact");

		log.info("TC_07_Step 12: Dien Email  ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_EMAIL, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_email");

		log.info("TC_07_Step 13: Dien Phone ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_PHONE, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_phonenumber");

		log.info("TC_07_Step 14: Dien Noi dung");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.CONTENT, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_content");

		log.info("TC_07_Step 15: Click vao check box Dung lam thong tin hanh khach bay");
		airTicket.clickToDynamicCheckBoxByID("com.VCB:id/iv_check_active");

		log.info("TC_07_Step 16: Click Tiep Tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_07_Step 17: Kiem tra man hinh xac nhan chuyen bay hien thi");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvTitle1"), "Xác nhận chuyến bay");

		log.info("TC_07_Step 18_1: Kiem tra dia diem khoi hanh");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem1"), "Hà Nội");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem2"), "HAN");

		log.info("TC_07_Step 18_2: Kiem tra dia diem den");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem3"), "Singapore");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem4"), "SIN");

		log.info("TC_07_Step 19_1: Kiem tra thong tin chieu di");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextLeft1"), "CHIỀU ĐI");

		log.info("TC_07_Step 19_2: Kiem tra thong tin ma chieu di");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvFlightId"), flightCode01);

		log.info("TC_07_Step 19_3: Kiem tra thong tin chieu di : ngay di");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextLeft2"), getRawSplitStringIndex(time, "-", 0).trim());

		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextRight1"), getRawSplitStringIndex(time, "-", 0).trim());

		log.info("TC_07_Step 19_4: Kiem tra thong tin chieu di : gio di ");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextLeft3"), "HAN " + departureTime01);

		log.info("TC_07_Step 19_5: Kiem tra thong tin chieu di : gio den");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextRight2"), "SIN " + arrivalTime01);

		log.info("TC_07_Step 19_6: Kiem trathông tin chieu di : thoi gian bay");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvDuration"), duration01);

		log.info("TC_07_Step 20_1: Kiem tra thong tin chieu ve");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextLeft1"), "CHIỀU VỀ");

		log.info("TC_07_Step 20_2: Kiem tra thong tin ma chieu ve");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvFlightId"), flightCode02);

		log.info("TC_07_Step 20_3: Kiem tra thong tin chieu ve : ngay di");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextLeft2"), getRawSplitStringIndex(time, "-", 1).trim());

		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextRight1"), getRawSplitStringIndex(time, "-", 1).trim());

		log.info("TC_07_Step 20_4: Kiem tra thong tin chieu ve : gio di ");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextLeft3"), "SIN " + departureTime02);

		log.info("TC_07_Step 20_5: Kiem tra thong tin chieu ve : gio den");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextRight2"), "HAN " + arrivalTime02);

		log.info("TC_07_Step 20_6: Kiem trathông tin chieu ve : thoi gian bay");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvDuration"), duration02);

		log.info("TC_07_Step 18: Kiem tra tong so tien hien thi dung");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Tổng tiền", "com.VCB:id/tvText2"), ticketPrice);

		log.info("TC_07_Step_19: Click checkbox dong y dieu khoan dat ve");
		airTicket.clickToDynamicCheckBoxByID("com.VCB:id/checkBoxCondition");

		log.info("TC_07_Step_20: Click Thanh toan");
		airTicket.clickToDynamicButton("Thanh toán");

		log.info("TC_07_Step_21: Chọn tai khoan nguon");
		airTicket.clickToDynamicTextByID("com.VCB:id/number_account");
		airTicket.clickToDynamicTextOrButtonLink(DomesticAirTicketBooking_Data.validInput.ACCOUNT2);

		log.info("TC_07_Step_22_3: Xac nhan ma thanh toan");
		payID = airTicket.getAirTicketPriceInfo1Way("Mã thanh toán", "com.VCB:id/tvContent");

		log.info("TC_07_Step_22_4: Xac nhan chieu di bay ");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("4", "Chiều bay"), "HAN → SIN");

		log.info("TC_07_Step_22_5: Xac nhan so hieu chuyen bay chieu di");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("5", "Số hiệu chuyến bay").replace("-", ""), flightCode01);

		log.info("TC_07_Step_22_6: Xac nhan thoi gian bay chieu di");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("6", "Thời gian bay"), departureTime01 + " - " + arrivalTime01);

		log.info("TC_07_Step_22_7: Xac nhan chieu ve bay ");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("9", "Chiều bay"), "SIN → HAN");

		log.info("TC_07_Step_22_8: Xac nhan so hieu chuyen bay chieu ve");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("10", "Số hiệu chuyến bay").replace("-", ""), flightCode02);

		log.info("TC_07_Step_22_6: Xac nhan thoi gian bay chieu ve");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("11", "Thời gian bay"), departureTime02 + " - " + arrivalTime02);

		log.info("TC_07_Step_22_7: Xac nhan Tong tien thanh toan");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Tổng tiền thanh toán", "com.VCB:id/tvContent"), ticketPrice);

		log.info("TC_07_Step_22: Click Tiep tuc");
		payID = airTicket.getAirTicketPriceInfo1Way("Mã thanh toán", "com.VCB:id/tvContent");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Tổng tiền thanh toán", "com.VCB:id/tvContent"), ticketPrice);

		log.info("TC_07_Step 23: Click Tiep Tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_07_Step_24: Xac nhan giao dich");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Từ tài khoản", "com.VCB:id/tvTu_tai_khoan"), DomesticAirTicketBooking_Data.validInput.ACCOUNT2);
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Mã thanh toán", "com.VCB:id/tvBooking_number"), payID);
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Số tiền", "com.VCB:id/tvAmount"), ticketPrice);

		log.info("TC_07_Step_25: Chon phuong thuc xac thuc SMS OTP");
		airTicket.clickToDynamicTextByID("com.VCB:id/tvptxt");
		airTicket.clickToDynamicTextOrButtonLink("SMS OTP");
		fee = airTicket.getAirTicketPriceInfo1Way("Phí giao dịch", "com.VCB:id/tvPhiGiaoDich");

		log.info("TC_07_Step_26: Click Tiep Tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_07_Step_27: Nhap Ma OTP roi click Tiep tuc");
		airTicket.inputToDynamicPopupPasswordInput(driver, pass, "Tiếp tục");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_07_Step_28: Xac nhan hien thi man hinh thanh toan thanh cong");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Mã thanh toán", "com.VCB:id/tvContent"), payID);
		transactionID = airTicket.getAirTicketPriceInfo1Way("Mã giao dịch", "com.VCB:id/tvContent");

		log.info("TC_07_Step_29: Clich nut Home");
		airTicket.clickToDynamicIcon("com.VCB:id/ivHome");
		homePage = PageFactoryManager.getHomePageObject(driver);
	}

	@Test
	public void TC_08_DatVeMayBayQuocTeKhuHoiThanhCong_1Nguoi_BaoCaoGiaoDich() {

		log.info("TC_08_Step_01: Mo tab Menu");
		homePage.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_5");

		log.info("TC_08_Step_02: Mo sub-menu 'Bao cao giao dich");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");
		transactionReport = PageFactoryManager.getTransactionReportPageObject(driver);

		log.info("TC_08_Step_03: An vao Dropdown 'Tat ca cac loai giao dich");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectTransType");

		log.info("TC_08_Step_04: Chon 'Thanh toan ve may bay'");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, "Thanh toán vé máy bay");

		log.info("TC_08_Step_05: An vao Dropdown 'Chon tai khoan/the");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_08_Step_06: Chon tai khoan vua thuc hien giao dich");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, DomesticAirTicketBooking_Data.validInput.ACCOUNT2);

		log.info("TC_08_Step_07: An nut Tim kiem");
		transactionReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");

		log.info("TC_08_Step_08: An vao giao dich dau tien");
		transactionReport.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_08_Step_09: Xac nhan hien thi Title 'Chi tiet giao dich'");
		verifyEquals(transactionReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Chi tiết giao dịch");

		log.info("TC_08_Step_10: Xac nhan hien thi dung ma giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Số lệnh giao dịch"), transactionID);

		log.info("TC_08_Step_11: Xac nhan hien thi so tai khoan giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Tài khoản/thẻ trích nợ"), DomesticAirTicketBooking_Data.validInput.ACCOUNT2);

		log.info("TC_08_Step_12: Xac nhan hien thi Ma Thanh toan");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Mã thanh toán"), payID);

		log.info("TC_08_Step_13: Xac nhan hien thi So tien phi");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Số tiền phí"), fee);

		log.info("TC_08_Step_14: Xac nhan hien thi Loai giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Loại giao dịch"), "Thanh toán vé máy bay");

		log.info("TC_08_Step_15: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_08_Step_16: An nut back ve man hinh menu");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		homePage = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_08_Step_17: Mo tab Home");
		homePage.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_1");
	}

	@Parameters({ "otp" })
	@Test
	public void TC_09_DatVeMayBayNoiDiaKhuHoi_ThanhToanSau_1Nguoi_ThanhToanOTP(String otp) {

		log.info("TC_09_Step_01: Click Dat ve may bay");
		homePage.clickToDynamicIcon(driver, "Đặt vé máy bay");
		airTicket = PageFactoryManager.getDynamicAirTicketBooking(driver);

		log.info("TC_09_Step_02: Click dat ve may bay noi dia ");
		airTicket = PageFactoryManager.getDynamicAirTicketBooking(driver);
		airTicket.clickToDynamicTextOrButtonLink("Đặt vé máy bay Nội địa");

		log.info("TC_09_Step_03: Click Dong y ");
		airTicket.clickToDynamicButton("Đồng ý");

		log.info("TC_09_Step_04: Chon khu hoi ");
		airTicket.clickToDynamicTextOrButtonLink("Khứ hồi");

		log.info("TC_09_Step_05: Chon diem khoi hanh");
		airTicket.clickToDynamicTextOrButtonLink("Khởi hành");
		airTicket.clickToDynamicTextOrButtonLink("Hà Nội");

		log.info("TC_09_Step_06: Chon diem den");
		airTicket.clickToDynamicTextOrButtonLink("Điểm đến");
		airTicket.clickToDynamicTextOrButtonLink("TP Hồ Chí Minh");

		log.info("TC_09_Step_07: Chon ngay di / ngay ve");
		airTicket.clickToDynamicTextOrButtonLink("Ngày đi");
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(5), fifthDay);
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(9), ninthDay);
		airTicket.clickToDynamicButton("Xác nhận");

		log.info("TC_09_Step_08: Tim chuyen bay");
		airTicket.clickToDynamicButton("Tìm chuyến bay");

		log.info("TC_09_Step_09_1: Chon chuyen bay chieu di");
		airTicket.clickToDynamicFlightTwoWays(0, "com.VCB:id/recy_chieudi", "com.VCB:id/tv_flightcode_internal", "VN");

		log.info("TC_09_Step_09_2: Chon chuyen bay chieu ve");
		airTicket.clickToDynamicFlightTwoWays(0, "com.VCB:id/recy_chieuve", "com.VCB:id/tv_flightcode_internal", "VN");

		log.info("TC_09_Step_09_3: Click dat ve");
		ticketPrice = airTicket.getDynamicTextByID("com.VCB:id/tv_Sum_amount_flight_selected");
		airTicket.clickToDynamicButton("Đặt vé");

		log.info("TC_09_Step 10: Dien Ten Nguoi lon ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_NAME, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_hoten");

		log.info("TC_09_Step 11: Chon gioi tinh nu");
		airTicket.checkToDynamicTextOrDropDownByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/tv_NuContact");

		log.info("TC_09_Step 12: Dien Email  ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_EMAIL, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_email");

		log.info("TC_09_Step 13: Dien Phone ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_PHONE, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_phonenumber");

		log.info("TC_09_Step 14: Dien Noi dung");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.CONTENT, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_content");

		log.info("TC_09_Step 15: Click vao check box Dung lam thong tin hanh khach bay");
		airTicket.clickToDynamicCheckBoxByID("com.VCB:id/iv_check_active");

		log.info("TC_09_Step 16: Click Tiep Tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_09_Step 17: Kiem tra man hinh xac nhan chuyen bay hien thi");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvTitle1"), "Xác nhận chuyến bay");

		log.info("TC_09_Step 18: Kiem tra tong so tien hien thi dung");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Tổng tiền", "com.VCB:id/tvText2"), ticketPrice);

		log.info("TC_09_Step_19: Click checkbox dong y dieu khoan dat ve");
		airTicket.clickToDynamicCheckBoxByID("com.VCB:id/checkBoxCondition");

		log.info("TC_09_Step_20: Click Thanh toan sau");
		airTicket.clickToDynamicButton("Thanh toán sau");

		log.info("TC_09_Step_22: Hien thi man hinh Ket qua");
		payID = airTicket.getDynamicTextByID("com.VCB:id/tvBookCode");

		log.info("TC_09_Step_23: Ve man hinh ve may bay");
		airTicket.clickToDynamicIcon("com.VCB:id/ivTitleRight");

		log.info("TC_09_Step_24: Chon Thanh toan ve may bay");
		airTicket.clickToDynamicTextOrButtonLink("Thanh toán vé máy bay");

		log.info("TC_09_Step_25: Nhap ma thanh toan");
		airTicket.inputToDynamicInputBoxById(payID, "com.VCB:id/edtBookCode");

		log.info("TC_09_Step_26: An tiep tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_09_Step_27: Chọn tai khoan nguon");
		airTicket.clickToDynamicTextByID("com.VCB:id/number_account");
		airTicket.clickToDynamicTextOrButtonLink(DomesticAirTicketBooking_Data.validInput.ACCOUNT2);

		log.info("TC_09_Step_28: An tiep tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_09_Step_29: Chon phuong thuc xac thuc SMS OTP");
		airTicket.clickToDynamicTextByID("com.VCB:id/tvptxt");
		airTicket.clickToDynamicTextOrButtonLink("SMS OTP");

		log.info("TC_09_Step_30: Click Tiep Tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_09_Step_31: Nhap Ma OTP roi click Tiep tuc");
		airTicket.inputToDynamicOtp(driver, otp, "Tiếp tục");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_09_Step_32: Xac nhan hien thi man hinh thanh toan thanh cong");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Mã thanh toán", "com.VCB:id/tvContent"), payID);
		transactionID = airTicket.getAirTicketPriceInfo1Way("Mã giao dịch", "com.VCB:id/tvContent");

		log.info("TC_09_Step_33: Clich nut Home");
		airTicket.clickToDynamicIcon("com.VCB:id/ivHome");
		homePage = PageFactoryManager.getHomePageObject(driver);
	}

	@Test
	public void TC_10_DatVeMayBayNoiDiaKhuHoi_ThanhToanSau_1Nguoi_BaoCaoGiaoDich() {

		log.info("TC_10_Step_01: Mo tab Menu");
		homePage.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_5");

		log.info("TC_10_Step_02: Mo sub-menu 'Bao cao giao dich");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");
		transactionReport = PageFactoryManager.getTransactionReportPageObject(driver);

		log.info("TC_10_Step_03: An vao Dropdown 'Tat ca cac loai giao dich");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectTransType");

		log.info("TC_10_Step_04: Chon 'Thanh toan ve may bay'");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, "Thanh toán vé máy bay");

		log.info("TC_10_Step_05: An vao Dropdown 'Chon tai khoan/the");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_10_Step_06: Chon tai khoan vua thuc hien giao dich");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, DomesticAirTicketBooking_Data.validInput.ACCOUNT2);

		log.info("TC_10_Step_07: An nut Tim kiem");
		transactionReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");

		log.info("TC_10_Step_08: An vao giao dich dau tien");
		transactionReport.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_10_Step_09: Xac nhan hien thi Title 'Chi tiet giao dich'");
		verifyEquals(transactionReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Chi tiết giao dịch");

		log.info("TC_10_Step_10: Xac nhan hien thi dung ma giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Số lệnh giao dịch"), transactionID);

		log.info("TC_10_Step_11: Xac nhan hien thi so tai khoan giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Tài khoản/thẻ trích nợ"), DomesticAirTicketBooking_Data.validInput.ACCOUNT2);

		log.info("TC_10_Step_12: Xac nhan hien thi Ma Thanh toan");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Mã thanh toán"), payID);

		log.info("TC_10_Step_13: Xac nhan hien thi So tien phi");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Số tiền phí"), fee);

		log.info("TC_10_Step_14: Xac nhan hien thi Loai giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Loại giao dịch"), "Thanh toán vé máy bay");

		log.info("TC_10_Step_15: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_10_Step_16: An nut back ve man hinh menu");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		homePage = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_10_Step_17: Mo tab Home");
		homePage.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_1");
	}

	@Parameters({ "pass" })
	@Test
	public void TC_11_DatVeMayBayNoiDiaKhuHoi_ThanhToanSau_1Nguoi_ThanhToanMK(String password) {

		log.info("TC_11_Step_01: Click Dat ve may bay");
		homePage.clickToDynamicIcon(driver, "Đặt vé máy bay");
		airTicket = PageFactoryManager.getDynamicAirTicketBooking(driver);

		log.info("TC_11_Step_02: Click dat ve may bay noi dia ");
		airTicket = PageFactoryManager.getDynamicAirTicketBooking(driver);
		airTicket.clickToDynamicTextOrButtonLink("Đặt vé máy bay Nội địa");

		log.info("TC_11_Step_03: Click Dong y ");
		airTicket.clickToDynamicButton("Đồng ý");

		log.info("TC_11_Step_04: Chon khu hoi ");
		airTicket.clickToDynamicTextOrButtonLink("Khứ hồi");

		log.info("TC_11_Step_05: Chon diem khoi hanh");
		airTicket.clickToDynamicTextOrButtonLink("Khởi hành");
		airTicket.clickToDynamicTextOrButtonLink("Hà Nội");

		log.info("TC_11_Step_06: Chon diem den");
		airTicket.clickToDynamicTextOrButtonLink("Điểm đến");
		airTicket.clickToDynamicTextOrButtonLink("TP Hồ Chí Minh");

		log.info("TC_11_Step_07: Chon ngay di / ngay ve");
		airTicket.clickToDynamicTextOrButtonLink("Ngày đi");
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(5), fifthDay);
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(9), ninthDay);
		airTicket.clickToDynamicButton("Xác nhận");

		log.info("TC_11_Step_08: Tim chuyen bay");
		airTicket.clickToDynamicButton("Tìm chuyến bay");

		log.info("TC_11_Step_09_1: Chon chuyen bay chieu di");
		airTicket.clickToDynamicFlightTwoWays(0, "com.VCB:id/recy_chieudi", "com.VCB:id/tv_flightcode_internal", "VN");

		log.info("TC_11_Step_09_2: Chon chuyen bay chieu ve");
		airTicket.clickToDynamicFlightTwoWays(0, "com.VCB:id/recy_chieuve", "com.VCB:id/tv_flightcode_internal", "VN");

		log.info("TC_11_Step_09_3: Click dat ve");
		ticketPrice = airTicket.getDynamicTextByID("com.VCB:id/tv_Sum_amount_flight_selected");
		airTicket.clickToDynamicButton("Đặt vé");

		log.info("TC_11_Step 10: Dien Ten Nguoi lon ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_NAME, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_hoten");

		log.info("TC_11_Step 11: Chon gioi tinh nu");
		airTicket.checkToDynamicTextOrDropDownByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/tv_NuContact");

		log.info("TC_11_Step 12: Dien Email  ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_EMAIL, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_email");

		log.info("TC_11_Step 13: Dien Phone ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_PHONE, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_phonenumber");

		log.info("TC_11_Step 14: Dien Noi dung");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.CONTENT, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_content");

		log.info("TC_11_Step 15: Click vao check box Dung lam thong tin hanh khach bay");
		airTicket.clickToDynamicCheckBoxByID("com.VCB:id/iv_check_active");

		log.info("TC_11_Step 16: Click Tiep Tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_11_Step 17: Kiem tra man hinh xac nhan chuyen bay hien thi");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvTitle1"), "Xác nhận chuyến bay");

		log.info("TC_11_Step 18: Kiem tra tong so tien hien thi dung");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Tổng tiền", "com.VCB:id/tvText2"), ticketPrice);

		log.info("TC_11_Step_19: Click checkbox dong y dieu khoan dat ve");
		airTicket.clickToDynamicCheckBoxByID("com.VCB:id/checkBoxCondition");

		log.info("TC_11_Step_20: Click Thanh toan sau");
		airTicket.clickToDynamicButton("Thanh toán sau");

		log.info("TC_11_Step_22: Hien thi man hinh Ket qua");
		payID = airTicket.getDynamicTextByID("com.VCB:id/tvBookCode");

		log.info("TC_11_Step_23: Ve man hinh ve may bay");
		airTicket.clickToDynamicIcon("com.VCB:id/ivTitleRight");

		log.info("TC_11_Step_24: Chon Thanh toan ve may bay");
		airTicket.clickToDynamicTextOrButtonLink("Thanh toán vé máy bay");

		log.info("TC_11_Step_25: Nhap ma thanh toan");
		airTicket.inputToDynamicInputBoxById(payID, "com.VCB:id/edtBookCode");

		log.info("TC_11_Step_26: An tiep tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_11_Step_27: Chọn tai khoan nguon");
		airTicket.clickToDynamicTextByID("com.VCB:id/number_account");
		airTicket.clickToDynamicTextOrButtonLink(DomesticAirTicketBooking_Data.validInput.ACCOUNT2);

		log.info("TC_11_Step_28: An tiep tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_11_Step_29: Chon phuong thuc xac thuc MK");
		airTicket.clickToDynamicTextByID("com.VCB:id/tvptxt");
		airTicket.clickToDynamicTextOrButtonLink("Mật khẩu đăng nhập");

		log.info("TC_11_Step_30: Click Tiep Tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_11_Step_31: Nhap Mat khau roi click Tiep tuc");
		airTicket.inputToDynamicPopupPasswordInput(driver, password, "Tiếp tục");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_11_Step_32: Xac nhan hien thi man hinh thanh toan thanh cong");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Mã thanh toán", "com.VCB:id/tvContent"), payID);
		transactionID = airTicket.getAirTicketPriceInfo1Way("Mã giao dịch", "com.VCB:id/tvContent");

		log.info("TC_11_Step_33: Clich nut Home");
		airTicket.clickToDynamicIcon("com.VCB:id/ivHome");
		homePage = PageFactoryManager.getHomePageObject(driver);
	}

	@Test
	public void TC_12_DatVeMayBayNoiDiaKhuHoi_ThanhToanSau_1Nguoi_BaoCaoGiaoDich() {

		log.info("TC_12_Step_01: Mo tab Menu");
		homePage.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_5");

		log.info("TC_12_Step_02: Mo sub-menu 'Bao cao giao dich");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");
		transactionReport = PageFactoryManager.getTransactionReportPageObject(driver);

		log.info("TC_12_Step_03: An vao Dropdown 'Tat ca cac loai giao dich");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectTransType");

		log.info("TC_12_Step_04: Chon 'Thanh toan ve may bay'");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, "Thanh toán vé máy bay");

		log.info("TC_12_Step_05: An vao Dropdown 'Chon tai khoan/the");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_12_Step_06: Chon tai khoan vua thuc hien giao dich");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, DomesticAirTicketBooking_Data.validInput.ACCOUNT2);

		log.info("TC_12_Step_07: An nut Tim kiem");
		transactionReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");

		log.info("TC_12_Step_08: An vao giao dich dau tien");
		transactionReport.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_12_Step_09: Xac nhan hien thi Title 'Chi tiet giao dich'");
		verifyEquals(transactionReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Chi tiết giao dịch");

		log.info("TC_12_Step_10: Xac nhan hien thi dung ma giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Số lệnh giao dịch"), transactionID);

		log.info("TC_12_Step_11: Xac nhan hien thi so tai khoan giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Tài khoản/thẻ trích nợ"), DomesticAirTicketBooking_Data.validInput.ACCOUNT2);

		log.info("TC_12_Step_12: Xac nhan hien thi Ma Thanh toan");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Mã thanh toán"), payID);

		log.info("TC_12_Step_13: Xac nhan hien thi So tien phi");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Số tiền phí"), fee);

		log.info("TC_12_Step_14: Xac nhan hien thi Loai giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Loại giao dịch"), "Thanh toán vé máy bay");

		log.info("TC_12_Step_15: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_12_Step_16: An nut back ve man hinh menu");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		homePage = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_10_Step_17: Mo tab Home");
		homePage.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_1");
	}

	@Parameters({ "otp" })
	@Test
	public void TC_13_DatVeMayBayQuocTeKhuHoi_ThanhToanSau_1Nguoi_ThanhToanOTP(String otp) {

		log.info("TC_13_Step_01: Click Dat ve may bay");
		homePage.clickToDynamicIcon(driver, "Đặt vé máy bay");
		airTicket = PageFactoryManager.getDynamicAirTicketBooking(driver);

		log.info("TC_13_Step_02: Click dat ve may bay quoc te ");
		airTicket = PageFactoryManager.getDynamicAirTicketBooking(driver);
		airTicket.clickToDynamicTextOrButtonLink("Đặt vé máy bay Quốc tế");

		log.info("TC_13_Step_03: Click Dong y ");
		airTicket.clickToDynamicButton("Đồng ý");

		log.info("TC_13_Step_04: Chon khu hoi ");
		airTicket.clickToDynamicTextOrButtonLink("Khứ hồi");

		log.info("TC_13_Step_05: Chon diem khoi hanh");
		airTicket.clickToDynamicTextOrButtonLink("Khởi hành");
		airTicket.clickToDynamicTextOrButtonLink("Hà Nội");

		log.info("TC_13_Step_06: Chon diem den");
		airTicket.clickToDynamicTextOrButtonLink("Điểm đến");
		airTicket.clickToDynamicTextOrButtonLink("Singapore");

		log.info("TC_13_Step_07: Chon ngay di / ngay ve");
		airTicket.clickToDynamicTextOrButtonLink("Ngày đi");
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(5), fifthDay);
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(9), ninthDay);
		airTicket.clickToDynamicButton("Xác nhận");

		log.info("TC_13_Step_08: Tim chuyen bay");
		airTicket.clickToDynamicButton("Tìm chuyến bay");

		log.info("TC_13_Step_09: Chon chuyen bay và dat ve");
		airTicket.clickToDynamicFlightTwoWays(0, "com.VCB:id/recycle_international", "com.VCB:id/tv_international_flightNo_chieudi", "VN");
		ticketPrice = airTicket.getDynamicTextByID("com.VCB:id/tv_Sum_amount_flight_selected");
		airTicket.clickToDynamicButton("Đặt vé");

		log.info("TC_13_Step 10: Dien Ten Nguoi lon ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_NAME, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_hoten");

		log.info("TC_13_Step 11: Chon gioi tinh nu");
		airTicket.checkToDynamicTextOrDropDownByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/tv_NuContact");

		log.info("TC_13_Step 12: Dien Email  ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_EMAIL, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_email");

		log.info("TC_13_Step 13: Dien Phone ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_PHONE, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_phonenumber");

		log.info("TC_13_Step 14: Dien Noi dung");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.CONTENT, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_content");

		log.info("TC_13_Step 15: Click vao check box Dung lam thong tin hanh khach bay");
		airTicket.clickToDynamicCheckBoxByID("com.VCB:id/iv_check_active");

		log.info("TC_13_Step 16: Click Tiep Tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_13_Step 17: Kiem tra man hinh xac nhan chuyen bay hien thi");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvTitle1"), "Xác nhận chuyến bay");

		log.info("TC_13_Step 18: Kiem tra tong so tien hien thi dung");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Tổng tiền", "com.VCB:id/tvText2"), ticketPrice);

		log.info("TC_13_Step_19: Click checkbox dong y dieu khoan dat ve");
		airTicket.clickToDynamicCheckBoxByID("com.VCB:id/checkBoxCondition");

		log.info("TC_13_Step_20: Click Thanh toan sau");
		airTicket.clickToDynamicButton("Thanh toán sau");

		log.info("TC_13_Step_22: Hien thi man hinh Ket qua");
		payID = airTicket.getDynamicTextByID("com.VCB:id/tvBookCode");

		log.info("TC_13_Step_23: Ve man hinh ve may bay");
		airTicket.clickToDynamicIcon("com.VCB:id/ivTitleRight");

		log.info("TC_13_Step_24: Chon Thanh toan ve may bay");
		airTicket.clickToDynamicTextOrButtonLink("Thanh toán vé máy bay");

		log.info("TC_13_Step_25: Nhap ma thanh toan");
		airTicket.inputToDynamicInputBoxById(payID, "com.VCB:id/edtBookCode");

		log.info("TC_13_Step_26: An tiep tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_13_Step_27: Chọn tai khoan nguon");
		airTicket.clickToDynamicTextByID("com.VCB:id/number_account");
		airTicket.clickToDynamicTextOrButtonLink(DomesticAirTicketBooking_Data.validInput.ACCOUNT2);

		log.info("TC_13_Step_28: An tiep tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_13_Step_29: Chon phuong thuc xac thuc SMS OTP");
		airTicket.clickToDynamicTextByID("com.VCB:id/tvptxt");
		airTicket.clickToDynamicTextOrButtonLink("SMS OTP");

		log.info("TC_13_Step_30: Click Tiep Tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_13_Step_31: Nhap Ma OTP roi click Tiep tuc");
		airTicket.inputToDynamicOtp(driver, otp, "Tiếp tục");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_13_Step_32: Xac nhan hien thi man hinh thanh toan thanh cong");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Mã thanh toán", "com.VCB:id/tvContent"), payID);
		transactionID = airTicket.getAirTicketPriceInfo1Way("Mã giao dịch", "com.VCB:id/tvContent");

		log.info("TC_13_Step_33: Clich nut Home");
		airTicket.clickToDynamicIcon("com.VCB:id/ivHome");
		homePage = PageFactoryManager.getHomePageObject(driver);
	}

	@Test
	public void TC_14_DatVeMayBayQuocTeKhuHoi_ThanhToanSau_1Nguoi_BaoCaoGiaoDich() {

		log.info("TC_14_Step_01: Mo tab Menu");
		homePage.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_5");

		log.info("TC_14_Step_02: Mo sub-menu 'Bao cao giao dich");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");
		transactionReport = PageFactoryManager.getTransactionReportPageObject(driver);

		log.info("TC_14_Step_03: An vao Dropdown 'Tat ca cac loai giao dich");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectTransType");

		log.info("TC_14_Step_04: Chon 'Thanh toan ve may bay'");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, "Thanh toán vé máy bay");

		log.info("TC_14_Step_05: An vao Dropdown 'Chon tai khoan/the");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_14_Step_06: Chon tai khoan vua thuc hien giao dich");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, DomesticAirTicketBooking_Data.validInput.ACCOUNT2);

		log.info("TC_14_Step_07: An nut Tim kiem");
		transactionReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");

		log.info("TC_14_Step_08: An vao giao dich dau tien");
		transactionReport.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_14_Step_09: Xac nhan hien thi Title 'Chi tiet giao dich'");
		verifyEquals(transactionReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Chi tiết giao dịch");

		log.info("TC_14_Step_10: Xac nhan hien thi dung ma giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Số lệnh giao dịch"), transactionID);

		log.info("TC_14_Step_11: Xac nhan hien thi so tai khoan giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Tài khoản/thẻ trích nợ"), DomesticAirTicketBooking_Data.validInput.ACCOUNT2);

		log.info("TC_14_Step_12: Xac nhan hien thi Ma Thanh toan");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Mã thanh toán"), payID);

		log.info("TC_14_Step_13: Xac nhan hien thi So tien phi");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Số tiền phí"), fee);

		log.info("TC_14_Step_14: Xac nhan hien thi Loai giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Loại giao dịch"), "Thanh toán vé máy bay");

		log.info("TC_14_Step_15: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_14_Step_16: An nut back ve man hinh menu");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		homePage = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_14_Step_17: Mo tab Home");
		homePage.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_1");
	}

	@Parameters({ "pass" })
	@Test
	public void TC_15_DatVeMayBayQuocTeKhuHoi_ThanhToanSau_1Nguoi_ThanhToanMK(String password) {

		log.info("TC_15_Step_01: Click Dat ve may bay");
		homePage.clickToDynamicIcon(driver, "Đặt vé máy bay");
		airTicket = PageFactoryManager.getDynamicAirTicketBooking(driver);

		log.info("TC_15_Step_02: Click dat ve may bay quoc te ");
		airTicket = PageFactoryManager.getDynamicAirTicketBooking(driver);
		airTicket.clickToDynamicTextOrButtonLink("Đặt vé máy bay Quốc tế");

		log.info("TC_15_Step_03: Click Dong y ");
		airTicket.clickToDynamicButton("Đồng ý");

		log.info("TC_15_Step_04: Chon khu hoi ");
		airTicket.clickToDynamicTextOrButtonLink("Khứ hồi");

		log.info("TC_15_Step_05: Chon diem khoi hanh");
		airTicket.clickToDynamicTextOrButtonLink("Khởi hành");
		airTicket.clickToDynamicTextOrButtonLink("Hà Nội");

		log.info("TC_15_Step_06: Chon diem den");
		airTicket.clickToDynamicTextOrButtonLink("Điểm đến");
		airTicket.clickToDynamicTextOrButtonLink("Singapore");

		log.info("TC_15_Step_07: Chon ngay di / ngay ve");
		airTicket.clickToDynamicTextOrButtonLink("Ngày đi");
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(5), fifthDay);
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(9), ninthDay);
		airTicket.clickToDynamicButton("Xác nhận");

		log.info("TC_15_Step_08: Tim chuyen bay");
		airTicket.clickToDynamicButton("Tìm chuyến bay");

		log.info("TC_15_Step_09: Chon chuyen bay và dat ve");
		airTicket.clickToDynamicFlightTwoWays(0, "com.VCB:id/recycle_international", "com.VCB:id/tv_international_flightNo_chieudi", "VN");
		ticketPrice = airTicket.getDynamicTextByID("com.VCB:id/tv_Sum_amount_flight_selected");
		airTicket.clickToDynamicButton("Đặt vé");

		log.info("TC_15_Step 10: Dien Ten Nguoi lon ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_NAME, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_hoten");

		log.info("TC_15_Step 11: Chon gioi tinh nu");
		airTicket.checkToDynamicTextOrDropDownByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/tv_NuContact");

		log.info("TC_15_Step 12: Dien Email  ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_EMAIL, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_email");

		log.info("TC_15_Step 13: Dien Phone ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_PHONE, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_phonenumber");

		log.info("TC_15_Step 14: Dien Noi dung");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.CONTENT, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_content");

		log.info("TC_15_Step 15: Click vao check box Dung lam thong tin hanh khach bay");
		airTicket.clickToDynamicCheckBoxByID("com.VCB:id/iv_check_active");

		log.info("TC_15_Step 16: Click Tiep Tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_15_Step 17: Kiem tra man hinh xac nhan chuyen bay hien thi");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvTitle1"), "Xác nhận chuyến bay");

		log.info("TC_15_Step 18: Kiem tra tong so tien hien thi dung");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Tổng tiền", "com.VCB:id/tvText2"), ticketPrice);

		log.info("TC_15_Step_19: Click checkbox dong y dieu khoan dat ve");
		airTicket.clickToDynamicCheckBoxByID("com.VCB:id/checkBoxCondition");

		log.info("TC_15_Step_20: Click Thanh toan sau");
		airTicket.clickToDynamicButton("Thanh toán sau");

		log.info("TC_15_Step_22: Hien thi man hinh Ket qua");
		payID = airTicket.getDynamicTextByID("com.VCB:id/tvBookCode");

		log.info("TC_15_Step_23: Ve man hinh ve may bay");
		airTicket.clickToDynamicIcon("com.VCB:id/ivTitleRight");

		log.info("TC_15_Step_24: Chon Thanh toan ve may bay");
		airTicket.clickToDynamicTextOrButtonLink("Thanh toán vé máy bay");

		log.info("TC_15_Step_25: Nhap ma thanh toan");
		airTicket.inputToDynamicInputBoxById(payID, "com.VCB:id/edtBookCode");

		log.info("TC_15_Step_26: An tiep tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_15_Step_27: Chọn tai khoan nguon");
		airTicket.clickToDynamicTextByID("com.VCB:id/number_account");
		airTicket.clickToDynamicTextOrButtonLink(DomesticAirTicketBooking_Data.validInput.ACCOUNT2);

		log.info("TC_15_Step_28: An tiep tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_15_Step_29: Chon phuong thuc xac thuc MK");
		airTicket.clickToDynamicTextByID("com.VCB:id/tvptxt");
		airTicket.clickToDynamicTextOrButtonLink("Mật khẩu đăng nhập");

		log.info("TC_15_Step_30: Click Tiep Tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_15_Step_31: Nhap Ma OTP roi click Tiep tuc");
		airTicket.inputToDynamicPopupPasswordInput(driver, password, "Tiếp tục");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_15_Step_32: Xac nhan hien thi man hinh thanh toan thanh cong");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Mã thanh toán", "com.VCB:id/tvContent"), payID);
		transactionID = airTicket.getAirTicketPriceInfo1Way("Mã giao dịch", "com.VCB:id/tvContent");

		log.info("TC_15_Step_33: Clich nut Home");
		airTicket.clickToDynamicIcon("com.VCB:id/ivHome");
		homePage = PageFactoryManager.getHomePageObject(driver);
	}

	@Test
	public void TC_16_DatVeMayBayQuocTeKhuHoi_ThanhToanSau_1Nguoi_BaoCaoGiaoDich() {

		log.info("TC_16_Step_01: Mo tab Menu");
		homePage.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_5");

		log.info("TC_16_Step_02: Mo sub-menu 'Bao cao giao dich");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");
		transactionReport = PageFactoryManager.getTransactionReportPageObject(driver);

		log.info("TC_16_Step_03: An vao Dropdown 'Tat ca cac loai giao dich");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectTransType");

		log.info("TC_16_Step_04: Chon 'Thanh toan ve may bay'");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, "Thanh toán vé máy bay");

		log.info("TC_16_Step_05: An vao Dropdown 'Chon tai khoan/the");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_16_Step_06: Chon tai khoan vua thuc hien giao dich");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, DomesticAirTicketBooking_Data.validInput.ACCOUNT2);

		log.info("TC_16_Step_07: An nut Tim kiem");
		transactionReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");

		log.info("TC_16_Step_08: An vao giao dich dau tien");
		transactionReport.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_16_Step_09: Xac nhan hien thi Title 'Chi tiet giao dich'");
		verifyEquals(transactionReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Chi tiết giao dịch");

		log.info("TC_16_Step_10: Xac nhan hien thi dung ma giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Số lệnh giao dịch"), transactionID);

		log.info("TC_16_Step_11: Xac nhan hien thi so tai khoan giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Tài khoản/thẻ trích nợ"), DomesticAirTicketBooking_Data.validInput.ACCOUNT2);

		log.info("TC_16_Step_12: Xac nhan hien thi Ma Thanh toan");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Mã thanh toán"), payID);

		log.info("TC_16_Step_13: Xac nhan hien thi So tien phi");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Số tiền phí"), fee);

		log.info("TC_16_Step_14: Xac nhan hien thi Loai giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Loại giao dịch"), "Thanh toán vé máy bay");

		log.info("TC_16_Step_15: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_16_Step_16: An nut back ve man hinh menu");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		homePage = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_16_Step_17: Mo tab Home");
		homePage.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_1");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		service.stop();
	}

}
