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

public class DomesticAirTicketBooking_MainFlow extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransactionReportPageObject transactionReport;
	private DynamicAirTicketBookingObjects airTicket;
	private String tomorrowDay = getForWardDay(1);
	private String seventhDay = getForWardDay(7);
	private String ticketPrice;
	private String payID;
	private String transactionID;
	private String fee;

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
		homePage.clickToDynamicIcon(driver, "Đặt vé máy bay");
		airTicket = PageFactoryManager.getDynamicAirTicketBooking(driver);

		log.info("Before class: Click dat ve may bay noi dia ");
		airTicket = PageFactoryManager.getDynamicAirTicketBooking(driver);
		airTicket.clickToDynamicTextOrButtonLink("Đặt vé máy bay Nội địa");

		log.info("Before class: Click Dong y ");
		airTicket.clickToDynamicButton("Đồng ý");

		log.info("Before class: Chon mot chieu ");
		airTicket.clickToDynamicTextOrButtonLink("Một chiều");
	}

	@Parameters({ "otp" })
	@Test
	public void TC_01_DatVeMayBayNoiDiaMotChieuThanhCong_1Nguoi_ThanhToanOTP(String otp) {

		log.info("TC_01_Step_01: Chon diem khoi hanh");
		airTicket.clickToDynamicTextOrButtonLink("Khởi hành");
		airTicket.clickToDynamicTextOrButtonLink("Hà Nội");

		log.info("TC_01_Step_02: Chon diem den");
		airTicket.clickToDynamicTextOrButtonLink("Điểm đến");
		airTicket.clickToDynamicTextOrButtonLink("Đà Nẵng");

		log.info("TC_01_Step_03: Chon ngay di");
		airTicket.clickToDynamicTextOrButtonLink("Ngày đi");
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(1), tomorrowDay);
		airTicket.clickToDynamicButton("Xác nhận");

		log.info("TC_01_Step_04: Tim chuyen bay");
		airTicket.clickToDynamicButton("Tìm chuyến bay");

		log.info("TC_01_Step_05: Chon chuyen bay va dat ve");
		airTicket.clickToDynamicFlight(0, "VJ");
		ticketPrice = airTicket.getDynamicTextByID("com.VCB:id/tv_amount_flight_selected");
		airTicket.clickToDynamicTextByID("com.VCB:id/btn_book");

		log.info("TC_01_Step 06: Dien Ten Nguoi lon ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_NAME, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_hoten");

		log.info("TC_01_Step 07: Chon gioi tinh nu");
		airTicket.checkToDynamicTextOrDropDownByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/tv_NuContact");

		log.info("TC_01_Step 08: Dien Email  ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_EMAIL, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_email");

		log.info("TC_11_Step 09: Dien Phone ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_PHONE, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_phonenumber");

		log.info("TC_11_Step 10: Dien Noi dung");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.CONTENT, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_content");

		log.info("TC_11_Step 11: Dien ten nguoi lon 1 ");
		airTicket.inputToDynamicInputBoxByLabelAndIndex(DomesticAirTicketBooking_Data.validInput.ADULT_NAME, "com.VCB:id/recy_info_book", "0", "com.VCB:id/edt_hoten");

		log.info("TC_01_Step 12: Chon gioi tinh nu");
		airTicket.clickToDynamicTextOrDropDownByLabelAndIndex("com.VCB:id/recy_info_book", "0", "com.VCB:id/ll_NuNoiDia", "com.VCB:id/tv_NuNoiDia");

		log.info("TC_01_Step 13: Click Tiep Tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_01_Step 14: Kiem tra man hinh xac nhan chuyen bay hien thi");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvTitle1"), "Xác nhận chuyến bay");

		log.info("TC_01_Step 15: Kiem tra tong so tien hien thi dung");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Tổng tiền", "com.VCB:id/tvText2"), ticketPrice);

		log.info("TC_01_Step_16: Click checkbox dong y dieu khoan dat ve");
		airTicket.clickToDynamicCheckBoxByID("com.VCB:id/checkBoxCondition");

		log.info("TC_01_Step_17: Click Thanh toan");
		airTicket.clickToDynamicButton("Thanh toán");

		log.info("TC_01_Step_18: Chọn tai khoan nguon");
		airTicket.clickToDynamicTextByID("com.VCB:id/number_account");
		airTicket.clickToDynamicTextOrButtonLink(DomesticAirTicketBooking_Data.validInput.ACCOUNT2);

		log.info("TC_01_Step_19: Click Tiep tuc");
		payID = airTicket.getAirTicketPriceInfo1Way("Mã thanh toán", "com.VCB:id/tvContent");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Tổng tiền thanh toán", "com.VCB:id/tvContent"), ticketPrice);

		log.info("TC_01_Step 20: Click Tiep Tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_01_Step_21: Xac nhan giao dich");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Từ tài khoản", "com.VCB:id/tvTu_tai_khoan"), DomesticAirTicketBooking_Data.validInput.ACCOUNT2);
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Mã thanh toán", "com.VCB:id/tvBooking_number"), payID);
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Số tiền", "com.VCB:id/tvAmount"), ticketPrice);
		fee = airTicket.getAirTicketPriceInfo1Way("Phí giao dịch", "com.VCB:id/tvPhiGiaoDich");

		log.info("TC_01_Step_22: Chon phuong thuc xac thuc SMS OTP");
		airTicket.clickToDynamicTextByID("com.VCB:id/tvptxt");
		airTicket.clickToDynamicTextOrButtonLink("SMS OTP");

		log.info("TC_01_Step_23: Click Tiep Tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_01_Step_24: Nhap Ma OTP roi click Tiep tuc");
		airTicket.inputToDynamicOtp(driver, otp, "Tiếp tục");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_01_Step_25: Xac nhan hien thi man hinh thanh toan thanh cong");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Mã thanh toán", "com.VCB:id/tvContent"), payID);
		transactionID = airTicket.getAirTicketPriceInfo1Way("Mã giao dịch", "com.VCB:id/tvContent");

		log.info("TC_01_Step_26: Clich nut Home");
		airTicket.clickToDynamicIcon("com.VCB:id/ivHome");
		homePage = PageFactoryManager.getHomePageObject(driver);
	}

	@Test
	public void TC_02_XacNhanDatVeMayBayNoiDiaMotChieuThanhCong_1Nguoi_TrongBaoCaoGiaoDich() {

		log.info("TC_02_Step_01: Mo tab Menu");
		homePage.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_5");

		log.info("TC_02_Step_02: Mo sub-menu 'Bao cao giao dich");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");
		transactionReport = PageFactoryManager.getTransactionReportPageObject(driver);

		log.info("TC_02_Step_03: An vao Dropdown 'Tat ca cac loai giao dich");
		transactionReport.clickToTextViewCombobox(driver, "com.VCB:id/tvSelectTransType");

		log.info("TC_02_Step_04: Chon 'Thanh toan ve may ba'");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, "Thanh toán vé máy bay");

		log.info("TC_02_Step_05: An vao Dropdown 'Chon tai khoan/the");
		transactionReport.clickToTextViewCombobox(driver, "com.VCB:id/tvSelectAcc");

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

	@Parameters({ "otp" })
	@Test
	public void TC_03_DatVeMayBayNoiDiaKhuHoiThanhCong_1Nguoi_ThanhToanOTP(String otp) {

		homePage.clickToDynamicIcon(driver, "Đặt vé máy bay");
		airTicket = PageFactoryManager.getDynamicAirTicketBooking(driver);

		log.info("TC_03_Step_01: Click dat ve may bay noi dia ");
		airTicket = PageFactoryManager.getDynamicAirTicketBooking(driver);
		airTicket.clickToDynamicTextOrButtonLink("Đặt vé máy bay Nội địa");

		log.info("TC_03_Step_02: Click Dong y ");
		airTicket.clickToDynamicButton("Đồng ý");

		log.info("TC_03_Step_03: Chon khu hoi ");
		airTicket.clickToDynamicTextOrButtonLink("Khứ hồi");

		log.info("TC_03_Step_04: Chon diem khoi hanh");
		airTicket.clickToDynamicTextOrButtonLink("Khởi hành");
		airTicket.clickToDynamicTextOrButtonLink("Hà Nội");

		log.info("TC_03_Step_05: Chon diem den");
		airTicket.clickToDynamicTextOrButtonLink("Điểm đến");
		airTicket.clickToDynamicTextOrButtonLink("TP Hồ Chí Minh");

		log.info("TC_03_Step_05: Chon ngay di");
		airTicket.clickToDynamicTextOrButtonLink("Ngày đi");
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(1), tomorrowDay);

		log.info("TC_03_Step_06: Chon ngay ve");
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(7), seventhDay);
		airTicket.clickToDynamicButton("Xác nhận");

		log.info("TC_03_Step_07: Tim chuyen bay");
		airTicket.clickToDynamicButton("Tìm chuyến bay");

		log.info("TC_03_Step_08: Chon Ve Chieu di - Chieu ve");
		airTicket.clickToDynamicFlight2WayCode("com.VCB:id/recy_chieudi", "0");
		airTicket.clickToDynamicFlight2WayCode("com.VCB:id/recy_chieuve", "0");

		log.info("TC_03_Step_09: Click Dat ve");
		ticketPrice = airTicket.getDynamicTextByID("com.VCB:id/tv_Sum_amount_flight_selected");
		airTicket.clickToDynamicTextByID("com.VCB:id/btn_book");

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

		log.info("TC_03_Step 15: Dien ten nguoi lon 1 ");
		airTicket.inputToDynamicInputBoxByLabelAndIndex(DomesticAirTicketBooking_Data.validInput.ADULT_NAME, "com.VCB:id/recy_info_book", "0", "com.VCB:id/edt_hoten");

		log.info("TC_03_Step 16: Chon gioi tinh nu");
		airTicket.clickToDynamicTextOrDropDownByLabelAndIndex("com.VCB:id/recy_info_book", "0", "com.VCB:id/ll_NuNoiDia", "com.VCB:id/tv_NuNoiDia");

		log.info("TC_03_Step 17: Click Tiep Tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_03_Step 18: Kiem tra man hinh xac nhan chuyen bay hien thi");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvTitle1"), "Xác nhận chuyến bay");

		log.info("TC_03_Step 19: Kiem tra tong so tien hien thi dung");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Tổng tiền", "com.VCB:id/tvText2"), ticketPrice);

		log.info("TC_03_Step_20: Click checkbox dong y dieu khoan dat ve");
		airTicket.clickToDynamicCheckBoxByID("com.VCB:id/checkBoxCondition");

		log.info("TC_03_Step_21: Click Thanh toan");
		airTicket.clickToDynamicButton("Thanh toán");

		log.info("TC_03_Step_22: Chọn tai khoan nguon");
		airTicket.clickToDynamicTextByID("com.VCB:id/number_account");
		airTicket.clickToDynamicTextOrButtonLink(DomesticAirTicketBooking_Data.validInput.ACCOUNT2);

		log.info("TC_03_Step_24: Click Xac nhan thong tin");
		payID = airTicket.getAirTicketPriceInfo1Way("Mã thanh toán", "com.VCB:id/tvContent");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Tổng tiền thanh toán", "com.VCB:id/tvContent"), ticketPrice);

		log.info("TC_03_Step 25: Click Tiep Tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_03_Step_26: Xac nhan giao dich");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Từ tài khoản", "com.VCB:id/tvTu_tai_khoan"), DomesticAirTicketBooking_Data.validInput.ACCOUNT2);
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Mã thanh toán", "com.VCB:id/tvBooking_number"), payID);
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Số tiền", "com.VCB:id/tvAmount"), ticketPrice);
		fee = airTicket.getAirTicketPriceInfo1Way("Phí giao dịch", "com.VCB:id/tvPhiGiaoDich");

		log.info("TC_03_Step_27: Chon phuong thuc xac thuc SMS OTP");
		airTicket.clickToDynamicTextByID("com.VCB:id/tvptxt");
		airTicket.clickToDynamicTextOrButtonLink("SMS OTP");

		log.info("TC_03_Step_28: Click Tiep Tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_01_Step_24: Nhap Ma OTP roi click Tiep tuc");
		airTicket.inputToDynamicOtp(driver, otp, "Tiếp tục");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_01_Step_25: Xac nhan hien thi man hinh thanh toan thanh cong");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Mã thanh toán", "com.VCB:id/tvContent"), payID);
		transactionID = airTicket.getAirTicketPriceInfo1Way("Mã giao dịch", "com.VCB:id/tvContent");

		log.info("TC_01_Step_26: Clich nut Home");
		airTicket.clickToDynamicIcon("com.VCB:id/ivHome");
		homePage = PageFactoryManager.getHomePageObject(driver);
	}

	@Test
	public void TC_04_XacNhanDatVeMayBayNoiDiaKhuHoiThanhCong_1Nguoi_TrongBaoCaoGiaoDich() {

		log.info("TC_04_Step_01: Mo tab Menu");
		homePage.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_5");

		log.info("TC_04_Step_02: Mo sub-menu 'Bao cao giao dich");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");
		transactionReport = PageFactoryManager.getTransactionReportPageObject(driver);

		log.info("TC_04_Step_03: An vao Dropdown 'Tat ca cac loai giao dich");
		transactionReport.clickToTextViewCombobox(driver, "com.VCB:id/tvSelectTransType");

		log.info("TC_04_Step_04: Chon 'Thanh toan ve may bay'");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, "Thanh toán vé máy bay");

		log.info("TC_04_Step_05: An vao Dropdown 'Chon tai khoan/the");
		transactionReport.clickToTextViewCombobox(driver, "com.VCB:id/tvSelectAcc");

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

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		service.stop();
	}

}
