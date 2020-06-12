package vnpay.vietcombank.sdk.airticket;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.Constants;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import model.SourceAccountModel;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.TransactionReportPageObject;
import pageObjects.sdk.airTicketBooking.DynamicAirTicketBookingObjects;
import vietcombank_test_data.TransactionReport_Data.ReportTitle;
import vnpay.vietcombank.sdk.airticket.data.DomesticAirTicketBooking_Data;
import vnpay.vietcombank.sdk.airticket.data.DomesticAirTicketBooking_Data.Air_Text;

public class DomesticAirTicketBooking_MainFlow02 extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransactionReportPageObject transactionReport;
	private DynamicAirTicketBookingObjects airTicket;
	
	SourceAccountModel sourceAccount = new SourceAccountModel();
	private String fifthDay = getForWardDay(5);
	private String ninthDay = getForWardDay(9);
	private String ticketPrice, payID, transactionID, fee, flightCode01, departureTime01, arrivalTime01, duration01, flightCode02, departureTime02, arrivalTime02, duration02, time,source,sourceDetail,dest,destDetail;

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
		airTicket = PageFactoryManager.getDynamicAirTicketBooking(driver);
		transactionReport = PageFactoryManager.getTransactionReportPageObject(driver);
		homePage = PageFactoryManager.getHomePageObject(driver);

	}

	@Parameters({ "otp" })
	@Test
	public void TC_01_DatVeMayBayNoiDiaKhuHoiThanhCong_1Nguoi_ThanhToanOTP(String otp) {

		log.info("TC_01_Step_01: Click Dat ve may bay");
		homePage.clickToDynamicIcon(driver, Air_Text.AIR_TICKET_TEXT);
	
		log.info("TC_01_Step_02: Click dat ve may bay noi dia ");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.DOMESTIC_AIR_TICKET_TEXT);

		log.info("TC_01_Step_03: Click Dong y ");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_AGREE_TEXT);

		log.info("TC_01_Step_04: Chon khu hoi ");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.TWO_WAYS_TEXT);

		log.info("TC_01_Step_05: Chon diem khoi hanh");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.DEPARTURE_PLACE_TEXT);
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.HAN_PLACE);

		log.info("TC_01_Step_06: Chon diem den");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.ARRIVAL_PLACE_TEXT);
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.SGN_PLACE);

		log.info("TC_01_Step_07: Chon ngay di / ngay ve");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.DEPARTURE_DATE_TEXT);
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(5), fifthDay);
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(9), ninthDay);
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONFIRM_TEXT);

		log.info("TC_01_Step_08: Tim chuyen bay");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_SEARCH_FLIGHT_TEXT);
		source = airTicket.getDynamicTextByID("com.VCB:id/tvTitle2Left");
		dest = airTicket.getDynamicTextByID("com.VCB:id/tvTitle2Right");
		sourceDetail = airTicket.getDynamicTextByID("com.VCB:id/tvDiemDi");
		destDetail = airTicket.getDynamicTextByID("com.VCB:id/tvDiemDen");
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
		airTicket.clickToDynamicButton(Air_Text.BUTTON_ORDER_TICKET_TEXT);

		log.info("TC_01_Step 10: Dien Ten Nguoi lon ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_NAME, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_hoten");

		log.info("TC_01_Step 11: Chon gioi tinh nu");
		airTicket.checkToDynamicTextOrDropDownByLabel(Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/tv_NuContact");

		log.info("TC_01_Step 12: Dien Email  ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_EMAIL, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_email");

		log.info("TC_01_Step 13: Dien Phone ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_PHONE, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_phonenumber");

		log.info("TC_01_Step 14: Dien Noi dung");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.CONTENT, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_content");

		log.info("TC_01_Step 15: Click vao check box Dung lam thong tin hanh khach bay");
		airTicket.clickToDynamicCheckBoxByID("com.VCB:id/iv_check_active");

		log.info("TC_01_Step 16: Click Tiep Tuc");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_01_Step 17: Kiem tra man hinh xac nhan chuyen bay hien thi");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvTitle1"), Air_Text.FLIGHT_CONFIRM_TEXT);

		log.info("TC_01_Step 18_1: Kiem tra dia diem khoi hanh");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem1"), sourceDetail);
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem2"), source);

		log.info("TC_01_Step 18_2: Kiem tra dia diem den");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem3"), destDetail);
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem4"), dest);

		log.info("TC_01_Step 19_1: Kiem tra thong tin chieu di");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextLeft1"), Air_Text.DEPARTURE_WAY_TEXT);

		log.info("TC_01_Step 19_2: Kiem tra thong tin ma chieu di");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvFlightId"), flightCode01);

		log.info("TC_01_Step 19_3: Kiem tra thong tin chieu di : ngay di");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextLeft2"), getRawSplitStringIndex(time, "-", 0).trim());

		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextRight1"), getRawSplitStringIndex(time, "-", 0).trim());

		log.info("TC_01_Step 19_4: Kiem tra thong tin chieu di : gio di ");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextLeft3"), source+" "+departureTime01);

		log.info("TC_01_Step 19_5: Kiem tra thong tin chieu di : gio den");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextRight2"), dest+" "+arrivalTime01);

		log.info("TC_01_Step 19_6: Kiem trathông tin chieu di : thoi gian bay");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvDuration"), duration01);

		log.info("TC_01_Step 20_1: Kiem tra thong tin chieu ve");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextLeft1"), Air_Text.ARRIVAL_WAY_TEXT);

		log.info("TC_01_Step 20_2: Kiem tra thong tin ma chieu ve");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvFlightId"), flightCode02);

		log.info("TC_01_Step 20_3: Kiem tra thong tin chieu ve : ngay di");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextLeft2"), getRawSplitStringIndex(time, "-", 1).trim());

		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextRight1"), getRawSplitStringIndex(time, "-", 1).trim());

		log.info("TC_01_Step 20_4: Kiem tra thong tin chieu ve : gio di ");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextLeft3"), dest+ " "+departureTime02);

		log.info("TC_01_Step 20_5: Kiem tra thong tin chieu ve : gio den");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextRight2"), source+" "+arrivalTime02);

		log.info("TC_01_Step 20_6: Kiem trathông tin chieu ve : thoi gian bay");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvDuration"), duration02);

		log.info("TC_01_Step 21_1: Kiem tra tong so tien hien thi dung");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.TOTAL_MONEY_TEXT, "com.VCB:id/tvText2"), ticketPrice);

		log.info("TC_01_Step_21_2: Click checkbox dong y dieu khoan dat ve");
		airTicket.clickToDynamicCheckBoxByID("com.VCB:id/checkBoxCondition");

		log.info("TC_01_Step_21_3: Click Thanh toan");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_PAY_TEXT);

		log.info("TC_01_Step_22_1: Hien thị man hinh xac nhan thong tin ");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvTitleBar"), Air_Text.PAY_INFO_TEXT);

		log.info("TC_01_Step_22_2: Chọn tai khoan nguon");
		airTicket.clickToDynamicTextByID("com.VCB:id/number_account");
		sourceAccount = airTicket.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		log.info("TC_01_Step_22_3: Xac nhan ma thanh toan");
		payID = airTicket.getAirTicketPriceInfo1Way(Air_Text.PAY_ID_TEXT, "com.VCB:id/tvContent");

		log.info("TC_01_Step_22_4: Xac nhan chieu di bay ");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("4", Air_Text.FLIGHT_WAY_TEXT), source+" → "+dest);

		log.info("TC_01_Step_22_5: Xac nhan so hieu chuyen bay chieu di");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("5", Air_Text.FLIGHT_CODE_TEXT).replace("-", ""), flightCode01);

		log.info("TC_01_Step_22_6: Xac nhan thoi gian bay chieu di");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("6", Air_Text.FLIGHT_TIME_TEXT), departureTime01 + " - " + arrivalTime01);

		log.info("TC_01_Step_22_7: Xac nhan chieu ve bay ");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("9", Air_Text.FLIGHT_WAY_TEXT), dest+" → "+source);

		log.info("TC_01_Step_22_8: Xac nhan so hieu chuyen bay chieu ve");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("10", Air_Text.FLIGHT_CODE_TEXT).replace("-", ""), flightCode02);

		log.info("TC_01_Step_22_6: Xac nhan thoi gian bay chieu ve");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("11", Air_Text.FLIGHT_TIME_TEXT), departureTime02 + " - " + arrivalTime02);

		log.info("TC_01_Step_22_7: Xac nhan Tong tien thanh toan");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.TOTAL_PAY_TEXT, "com.VCB:id/tvContent"), ticketPrice);

		log.info("TC_01_Step 23: Click Tiep Tuc");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_01_Step_24: Xac nhan giao dich");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.FROM_ACCOUNT_TEXT, "com.VCB:id/tvTu_tai_khoan"), sourceAccount.account);
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.PAY_ID_TEXT, "com.VCB:id/tvBooking_number"), payID);
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.MONEY_AMOUNT_TEXT, "com.VCB:id/tvAmount"), ticketPrice);

		log.info("TC_01_Step_25: Chon phuong thuc xac thuc SMS OTP");
		airTicket.clickToDynamicTextByID("com.VCB:id/tvptxt");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.SMS_OTP_TEXT);
		fee = airTicket.getAirTicketPriceInfo1Way(Air_Text.FEE_VALUE_TEXT, "com.VCB:id/tvPhiGiaoDich");

		log.info("TC_01_Step_26: Click Tiep Tuc");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_01_Step_27: Nhap Ma OTP roi click Tiep tuc");
		airTicket.inputToDynamicOtp(driver, otp, Air_Text.BUTTON_CONTINUE_TEXT);
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_01_Step_28: Xac nhan hien thi man hinh thanh toan thanh cong");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.PAY_ID_TEXT, "com.VCB:id/tvContent"), payID);
		transactionID = airTicket.getAirTicketPriceInfo1Way(Air_Text.TRANSACTION_ID_TEXT, "com.VCB:id/tvContent");

		log.info("TC_01_Step_29: Clich nut Home");
		airTicket.clickToDynamicIcon("com.VCB:id/ivHome");
		
	}

	@Test
	public void TC_02_DatVeMayBayNoiDiaKhuHoiThanhCong_1Nguoi_BaoCaoGiaoDich() {

		log.info("TC_02_Step_01: Mo tab Menu");
		homePage.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_5");

		log.info("TC_02_Step_02: Mo sub-menu 'Bao cao giao dich");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSACTION_REPORT);
	
		log.info("TC_02_Step_03: An vao Dropdown 'Tat ca cac loai giao dich");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectTransType");

		log.info("TC_02_Step_04: Chon 'Thanh toan ve may bay'");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.AIR_TICKET);

		log.info("TC_02_Step_05: An vao Dropdown 'Chon tai khoan/the");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_02_Step_06: Chon tai khoan vua thuc hien giao dich");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_02_Step_07: An nut Tim kiem");
		transactionReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");

		log.info("TC_02_Step_08: An vao giao dich dau tien");
		transactionReport.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_02_Step_09: Xac nhan hien thi Title 'Chi tiet giao dich'");
		verifyEquals(transactionReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), ReportTitle.TRANSACTION_DETAIL);

		log.info("TC_02_Step_10: Xac nhan hien thi dung ma giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.TRANSACTION_NUMBER), transactionID);

		log.info("TC_02_Step_11: Xac nhan hien thi so tai khoan giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.ACCOUNT_CARD), sourceAccount.account);

		log.info("TC_02_Step_12: Xac nhan hien thi Ma Thanh toan");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.CODE_TRANSFER), payID);

		log.info("TC_02_Step_13: Xac nhan hien thi So tien phi");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.TRANSACTION_FEE), fee);

		log.info("TC_02_Step_14: Xac nhan hien thi Loai giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.TRANSACTION_TYPE), ReportTitle.AIR_TICKET);

		log.info("TC_02_Step_15: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_02_Step_16: An nut back ve man hinh menu");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_02_Step_17: Mo tab Home");
		homePage.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_1");
	}

	@Parameters({ "pass" })
	@Test
	public void TC_03_DatVeMayBayNoiDiaKhuHoiThanhCong_1Nguoi_ThanhToanMK(String pass) {

		log.info("TC_03_Step_01: Click Dat ve may bay");
		homePage.clickToDynamicIcon(driver, Air_Text.AIR_TICKET_TEXT);

		log.info("TC_03_Step_02: Click dat ve may bay noi dia ");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.DOMESTIC_AIR_TICKET_TEXT);

		log.info("TC_03_Step_03: Click Dong y ");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_AGREE_TEXT);

		log.info("TC_03_Step_04: Chon khu hoi ");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.TWO_WAYS_TEXT);

		log.info("TC_03_Step_05: Chon diem khoi hanh");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.DEPARTURE_PLACE_TEXT);
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.HAN_PLACE);

		log.info("TC_03_Step_06: Chon diem den");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.ARRIVAL_PLACE_TEXT);
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.SGN_PLACE);

		log.info("TC_03_Step_07: Chon ngay di / ngay ve");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.DEPARTURE_DATE_TEXT);
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(5), fifthDay);
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(9), ninthDay);
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONFIRM_TEXT);

		log.info("TC_03_Step_08: Tim chuyen bay");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_SEARCH_FLIGHT_TEXT);
		source = airTicket.getDynamicTextByID("com.VCB:id/tvTitle2Left");
		dest = airTicket.getDynamicTextByID("com.VCB:id/tvTitle2Right");
		sourceDetail = airTicket.getDynamicTextByID("com.VCB:id/tvDiemDi");
		destDetail = airTicket.getDynamicTextByID("com.VCB:id/tvDiemDen");
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
		airTicket.clickToDynamicButton(Air_Text.BUTTON_ORDER_TICKET_TEXT);

		log.info("TC_03_Step 10: Dien Ten Nguoi lon ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_NAME, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_hoten");

		log.info("TC_03_Step 11: Chon gioi tinh nu");
		airTicket.checkToDynamicTextOrDropDownByLabel(Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/tv_NuContact");

		log.info("TC_03_Step 12: Dien Email  ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_EMAIL, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_email");

		log.info("TC_03_Step 13: Dien Phone ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_PHONE, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_phonenumber");

		log.info("TC_03_Step 14: Dien Noi dung");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.CONTENT, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_content");

		log.info("TC_03_Step 15: Click vao check box Dung lam thong tin hanh khach bay");
		airTicket.clickToDynamicCheckBoxByID("com.VCB:id/iv_check_active");

		log.info("TC_03_Step 16: Click Tiep Tuc");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_03_Step 17: Kiem tra man hinh xac nhan chuyen bay hien thi");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvTitle1"), Air_Text.FLIGHT_CONFIRM_TEXT);

		log.info("TC_03_Step 18_1: Kiem tra dia diem khoi hanh");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem1"), sourceDetail);
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem2"), source);

		log.info("TC_03_Step 18_2: Kiem tra dia diem den");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem3"), destDetail);
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem4"), dest);

		log.info("TC_03_Step 19_1: Kiem tra thong tin chieu di");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextLeft1"), Air_Text.DEPARTURE_WAY_TEXT);

		log.info("TC_03_Step 19_2: Kiem tra thong tin ma chieu di");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvFlightId"), flightCode01);

		log.info("TC_03_Step 19_3: Kiem tra thong tin chieu di : ngay di");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextLeft2"), getRawSplitStringIndex(time, "-", 0).trim());

		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextRight1"), getRawSplitStringIndex(time, "-", 0).trim());

		log.info("TC_03_Step 19_4: Kiem tra thong tin chieu di : gio di ");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextLeft3"), source+" " + departureTime01);

		log.info("TC_03_Step 19_5: Kiem tra thong tin chieu di : gio den");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextRight2"), dest+" " + arrivalTime01);

		log.info("TC_03_Step 19_6: Kiem trathông tin chieu di : thoi gian bay");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvDuration"), duration01);

		log.info("TC_03_Step 20_1: Kiem tra thong tin chieu ve");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextLeft1"), Air_Text.ARRIVAL_WAY_TEXT);

		log.info("TC_03_Step 20_2: Kiem tra thong tin ma chieu ve");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvFlightId"), flightCode02);

		log.info("TC_03_Step 20_3: Kiem tra thong tin chieu ve : ngay di");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextLeft2"), getRawSplitStringIndex(time, "-", 1).trim());

		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextRight1"), getRawSplitStringIndex(time, "-", 1).trim());

		log.info("TC_03_Step 20_4: Kiem tra thong tin chieu ve : gio di ");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextLeft3"), dest+" " + departureTime02);

		log.info("TC_03_Step 20_5: Kiem tra thong tin chieu ve : gio den");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextRight2"), source+" " + arrivalTime02);

		log.info("TC_03_Step 20_6: Kiem tra thong tin chieu ve : thoi gian bay");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvDuration"), duration02);

		log.info("TC_03_Step 20_7: Kiem tra tong so tien hien thi dung");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.TOTAL_MONEY_TEXT, "com.VCB:id/tvText2"), ticketPrice);

		log.info("TC_03_Step_20_8: Click checkbox dong y dieu khoan dat ve");
		airTicket.clickToDynamicCheckBoxByID("com.VCB:id/checkBoxCondition");

		log.info("TC_03_Step_20_9: Click Thanh toan");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_PAY_TEXT);

		log.info("TC_03_Step_21: Chọn tai khoan nguon");
		airTicket.clickToDynamicTextByID("com.VCB:id/number_account");
		sourceAccount = airTicket.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		log.info("TC_03_Step_22_1: Xac nhan ma thanh toan");
		payID = airTicket.getAirTicketPriceInfo1Way(Air_Text.PAY_ID_TEXT, "com.VCB:id/tvContent");

		log.info("TC_03_Step_22_2: Xac nhan chieu di bay ");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("4", Air_Text.FLIGHT_WAY_TEXT), source+" → "+dest);

		log.info("TC_03_Step_22_3: Xac nhan so hieu chuyen bay chieu di");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("5", Air_Text.FLIGHT_CODE_TEXT).replace("-", ""), flightCode01);

		log.info("TC_03_Step_22_4: Xac nhan thoi gian bay chieu di");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("6", Air_Text.FLIGHT_TIME_TEXT), departureTime01 + " - " + arrivalTime01);

		log.info("TC_03_Step_22_5: Xac nhan chieu ve bay ");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("9", Air_Text.FLIGHT_WAY_TEXT), dest+" → "+source);

		log.info("TC_03_Step_22_6: Xac nhan so hieu chuyen bay chieu ve");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("10", Air_Text.FLIGHT_CODE_TEXT).replace("-", ""), flightCode02);

		log.info("TC_03_Step_22_7: Xac nhan thoi gian bay chieu ve");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("11", Air_Text.FLIGHT_TIME_TEXT), departureTime02 + " - " + arrivalTime02);

		log.info("TC_03_Step_22_8: Xac nhan Tong tien thanh toan");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.TOTAL_PAY_TEXT, "com.VCB:id/tvContent"), ticketPrice);

		log.info("TC_03_Step_22_9: Click Tiep tuc");
		payID = airTicket.getAirTicketPriceInfo1Way(Air_Text.PAY_ID_TEXT, "com.VCB:id/tvContent");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.TOTAL_PAY_TEXT, "com.VCB:id/tvContent"), ticketPrice);

		log.info("TC_03_Step 23: Click Tiep Tuc");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_03_Step_24: Xac nhan giao dich");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.FROM_ACCOUNT_TEXT, "com.VCB:id/tvTu_tai_khoan"), sourceAccount.account);
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.PAY_ID_TEXT, "com.VCB:id/tvBooking_number"), payID);
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.MONEY_AMOUNT_TEXT, "com.VCB:id/tvAmount"), ticketPrice);

		log.info("TC_03_Step_25: Chon phuong thuc xac thuc SMS OTP");
		airTicket.clickToDynamicTextByID("com.VCB:id/tvptxt");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.SMS_OTP_TEXT);
		fee = airTicket.getAirTicketPriceInfo1Way(Air_Text.FEE_VALUE_TEXT, "com.VCB:id/tvPhiGiaoDich");

		log.info("TC_03_Step_26: Click Tiep Tuc");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_03_Step_27: Nhap Ma OTP roi click Tiep tuc");
		airTicket.inputToDynamicPopupPasswordInput(driver, pass, Air_Text.BUTTON_CONTINUE_TEXT);
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_03_Step_28: Xac nhan hien thi man hinh thanh toan thanh cong");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.PAY_ID_TEXT, "com.VCB:id/tvContent"), payID);
		transactionID = airTicket.getAirTicketPriceInfo1Way(Air_Text.TRANSACTION_ID_TEXT, "com.VCB:id/tvContent");

		log.info("TC_03_Step_29: Clich nut Home");
		airTicket.clickToDynamicIcon("com.VCB:id/ivHome");
	}

	@Test
	public void TC_04_DatVeMayBayNoiDiaKhuHoiThanhCong_1Nguoi_BaoCaoGiaoDich() {

		log.info("TC_04_Step_01: Mo tab Menu");
		homePage.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_5");

		log.info("TC_04_Step_02: Mo sub-menu 'Bao cao giao dich");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSACTION_REPORT);
		
		log.info("TC_04_Step_03: An vao Dropdown 'Tat ca cac loai giao dich");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectTransType");

		log.info("TC_04_Step_04: Chon 'Thanh toan ve may bay'");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.AIR_TICKET);

		log.info("TC_04_Step_05: An vao Dropdown 'Chon tai khoan/the");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_04_Step_06: Chon tai khoan vua thuc hien giao dich");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_04_Step_07: An nut Tim kiem");
		transactionReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");

		log.info("TC_04_Step_08: An vao giao dich dau tien");
		transactionReport.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_04_Step_09: Xac nhan hien thi Title 'Chi tiet giao dich'");
		verifyEquals(transactionReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), ReportTitle.TRANSACTION_DETAIL);

		log.info("TC_04_Step_10: Xac nhan hien thi dung ma giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.TRANSACTION_NUMBER), transactionID);

		log.info("TC_04_Step_11: Xac nhan hien thi so tai khoan giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.ACCOUNT_CARD), sourceAccount.account);

		log.info("TC_04_Step_12: Xac nhan hien thi Ma Thanh toan");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.CODE_TRANSFER), payID);

		log.info("TC_04_Step_13: Xac nhan hien thi So tien phi");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.TRANSACTION_FEE), fee);

		log.info("TC_04_Step_14: Xac nhan hien thi Loai giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.TRANSACTION_TYPE), ReportTitle.AIR_TICKET);

		log.info("TC_04_Step_15: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_04_Step_16: An nut back ve man hinh menu");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		
		log.info("TC_04_Step_17: Mo tab Home");
		homePage.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_1");
	}

	@Parameters({ "otp" })
	@Test
	public void TC_05_DatVeMayBayQuocTeKhuHoiThanhCong_1Nguoi_ThanhToanOTP(String otp) {

		log.info("TC_05_Step_01: Click Dat ve may bay");
		homePage.clickToDynamicIcon(driver, Air_Text.AIR_TICKET_TEXT);
	
		log.info("TC_05_Step_02: Click dat ve may bay quoc te ");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.INTERNATIONAL_AIR_TICKET_TEXT);

		log.info("TC_05_Step_03: Click Dong y ");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_AGREE_TEXT);

		log.info("TC_05_Step_04: Chon khu hoi ");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.TWO_WAYS_TEXT);

		log.info("TC_05_Step_05: Chon diem khoi hanh");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.DEPARTURE_PLACE_TEXT);
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.HAN_PLACE);

		log.info("TC_05_Step_06: Chon diem den");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.ARRIVAL_PLACE_TEXT);
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.BKK_PLACE);

		log.info("TC_05_Step_07: Chon ngay di / ngay ve");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.DEPARTURE_DATE_TEXT);
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(5), fifthDay);
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(9), ninthDay);
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONFIRM_TEXT);

		log.info("TC_05_Step_08: Tim chuyen bay");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_SEARCH_FLIGHT_TEXT);
		source = airTicket.getDynamicTextByID("com.VCB:id/tvTitle2Left");
		dest = airTicket.getDynamicTextByID("com.VCB:id/tvTitle2Right");
		sourceDetail = airTicket.getDynamicTextByID("com.VCB:id/tvDiemDi");
		destDetail = airTicket.getDynamicTextByID("com.VCB:id/tvDiemDen");
		time = airTicket.getDynamicTextByID("com.VCB:id/tvTitle2");

		log.info("TC_05_Step_09_1: Chon chuyen bay chieu di");
		airTicket.clickToDynamicFlight2WayCode("com.VCB:id/recycle_international", "0");
		
		flightCode01 = airTicket.getDynamicTextByID("com.VCB:id/tv_flightCode_External_chieudi");
		departureTime01 = getRawSplitStringIndex(airTicket.getDynamicTextByID("com.VCB:id/tv_time_External_chieudi"), "-", 0);
		arrivalTime01 = getRawSplitStringIndex(airTicket.getDynamicTextByID("com.VCB:id/tv_time_External_chieudi"), "-", 1);
		
		flightCode02 = airTicket.getDynamicTextByID("com.VCB:id/tv_flightCode_External_chieuve");
		departureTime02 = getRawSplitStringIndex(airTicket.getDynamicTextByID("com.VCB:id/tv_time_External_chieuve"), "-", 0);
		arrivalTime02 = getRawSplitStringIndex(airTicket.getDynamicTextByID("com.VCB:id/tv_time_External_chieuve"), "-", 1);

		log.info("TC_05_Step_09_3: Click dat ve");
		ticketPrice = airTicket.getDynamicTextByID("com.VCB:id/tv_amount_flight_selected");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_ORDER_TICKET_TEXT);

		log.info("TC_05_Step 10: Dien Ten Nguoi lon ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_NAME, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_hoten");

		log.info("TC_05_Step 11: Chon gioi tinh nu");
		airTicket.checkToDynamicTextOrDropDownByLabel(Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/tv_NuContact");

		log.info("TC_05_Step 12: Dien Email  ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_EMAIL, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_email");

		log.info("TC_05_Step 13: Dien Phone ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_PHONE, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_phonenumber");

		log.info("TC_05_Step 14: Dien Noi dung");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.CONTENT, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_content");

		log.info("TC_05_Step 15: Click vao check box Dung lam thong tin hanh khach bay");
		airTicket.clickToDynamicCheckBoxByID("com.VCB:id/iv_check_active");

		log.info("TC_05_Step 16: Click Tiep Tuc");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_05_Step 17: Kiem tra man hinh xac nhan chuyen bay hien thi");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvTitle1"), Air_Text.FLIGHT_CONFIRM_TEXT);

		log.info("TC_05_Step 18_1: Kiem tra dia diem khoi hanh");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem1"), sourceDetail);
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem2"), source);

		log.info("TC_05_Step 18_2: Kiem tra dia diem den");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem3"), destDetail);
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem4"), dest);

		log.info("TC_05_Step 19_1: Kiem tra thong tin chieu di");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextLeft1"), Air_Text.DEPARTURE_WAY_TEXT);

		log.info("TC_05_Step 19_2: Kiem tra thong tin ma chieu di");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvFlightId"), flightCode01);

		log.info("TC_05_Step 19_3: Kiem tra thong tin chieu di : ngay di");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextLeft2"), getRawSplitStringIndex(time, "-", 0).trim());

		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextRight1"), getRawSplitStringIndex(time, "-", 0).trim());

		log.info("TC_05_Step 19_4: Kiem tra thong tin chieu di : gio di ");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextLeft3"), source+" " + departureTime01);

		log.info("TC_05_Step 19_5: Kiem tra thong tin chieu di : gio den");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextRight2"), dest+" " + arrivalTime01);

		log.info("TC_05_Step 19_6: Kiem trathông tin chieu di : thoi gian bay");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvDuration"), duration01);

		log.info("TC_05_Step 20_1: Kiem tra thong tin chieu ve");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextLeft1"), Air_Text.ARRIVAL_WAY_TEXT);

		log.info("TC_05_Step 20_2: Kiem tra thong tin ma chieu ve");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvFlightId"), flightCode02);

		log.info("TC_05_Step 20_3: Kiem tra thong tin chieu ve : ngay di");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextLeft2"), getRawSplitStringIndex(time, "-", 1).trim());

		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextRight1"), getRawSplitStringIndex(time, "-", 1).trim());

		log.info("TC_05_Step 20_4: Kiem tra thong tin chieu ve : gio di ");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextLeft3"), dest+" " + departureTime02);

		log.info("TC_05_Step 20_5: Kiem tra thong tin chieu ve : gio den");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextRight2"), source+" " + arrivalTime02);

		log.info("TC_05_Step 20_6: Kiem trathông tin chieu ve : thoi gian bay");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvDuration"), duration02);

		log.info("TC_05_Step 18: Kiem tra tong so tien hien thi dung");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.TOTAL_MONEY_TEXT, "com.VCB:id/tvText2"), ticketPrice);

		log.info("TC_05_Step_19: Click checkbox dong y dieu khoan dat ve");
		airTicket.clickToDynamicCheckBoxByID("com.VCB:id/checkBoxCondition");

		log.info("TC_05_Step_20: Click Thanh toan");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_PAY_TEXT);

		log.info("TC_05_Step_21: Chọn tai khoan nguon");
		airTicket.clickToDynamicTextByID("com.VCB:id/number_account");
		sourceAccount = airTicket.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		log.info("TC_05_Step_22_3: Xac nhan ma thanh toan");
		payID = airTicket.getAirTicketPriceInfo1Way(Air_Text.PAY_ID_TEXT, "com.VCB:id/tvContent");

		log.info("TC_05_Step_22_4: Xac nhan chieu di bay ");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("4", Air_Text.FLIGHT_WAY_TEXT), source+" → "+dest);

		log.info("TC_05_Step_22_5: Xac nhan so hieu chuyen bay chieu di");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("5", Air_Text.FLIGHT_CODE_TEXT).replace("-", ""), flightCode01);

		log.info("TC_05_Step_22_6: Xac nhan thoi gian bay chieu di");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("6", Air_Text.FLIGHT_TIME_TEXT), departureTime01 + " - " + arrivalTime01);

		log.info("TC_05_Step_22_7: Xac nhan chieu ve bay ");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("9", Air_Text.FLIGHT_WAY_TEXT), dest+" → "+source);

		log.info("TC_05_Step_22_8: Xac nhan so hieu chuyen bay chieu ve");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("10", Air_Text.FLIGHT_CODE_TEXT).replace("-", ""), flightCode02);

		log.info("TC_05_Step_22_6: Xac nhan thoi gian bay chieu ve");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("11", Air_Text.FLIGHT_TIME_TEXT), departureTime02 + " - " + arrivalTime02);

		log.info("TC_05_Step_22_7: Xac nhan Tong tien thanh toan");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.TOTAL_PAY_TEXT, "com.VCB:id/tvContent"), ticketPrice);

		log.info("TC_05_Step 23: Click Tiep Tuc");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_05_Step_24: Xac nhan giao dich");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.FROM_ACCOUNT_TEXT, "com.VCB:id/tvTu_tai_khoan"), sourceAccount.account);
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.PAY_ID_TEXT, "com.VCB:id/tvBooking_number"), payID);
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.MONEY_AMOUNT_TEXT, "com.VCB:id/tvAmount"), ticketPrice);

		log.info("TC_05_Step_25: Chon phuong thuc xac thuc SMS OTP");
		airTicket.clickToDynamicTextByID("com.VCB:id/tvptxt");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.SMS_OTP_TEXT);
		fee = airTicket.getAirTicketPriceInfo1Way(Air_Text.FEE_VALUE_TEXT, "com.VCB:id/tvPhiGiaoDich");

		log.info("TC_05_Step_26: Click Tiep Tuc");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_05_Step_27: Nhap Ma OTP roi click Tiep tuc");
		airTicket.inputToDynamicOtp(driver, otp, Air_Text.BUTTON_CONTINUE_TEXT);
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_05_Step_28: Xac nhan hien thi man hinh thanh toan thanh cong");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.PAY_ID_TEXT, "com.VCB:id/tvContent"), payID);
		transactionID = airTicket.getAirTicketPriceInfo1Way(Air_Text.TRANSACTION_ID_TEXT, "com.VCB:id/tvContent");

		log.info("TC_05_Step_29: Clich nut Home");
		airTicket.clickToDynamicIcon("com.VCB:id/ivHome");
		
	}

	@Test
	public void TC_06_DatVeMayBayQuocTeKhuHoiThanhCong_1Nguoi_BaoCaoGiaoDich() {

		log.info("TC_06_Step_01: Mo tab Menu");
		homePage.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_5");

		log.info("TC_06_Step_02: Mo sub-menu 'Bao cao giao dich");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSACTION_REPORT);
		
		log.info("TC_06_Step_03: An vao Dropdown 'Tat ca cac loai giao dich");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectTransType");

		log.info("TC_06_Step_04: Chon 'Thanh toan ve may bay'");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.AIR_TICKET);

		log.info("TC_06_Step_05: An vao Dropdown 'Chon tai khoan/the");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_06_Step_06: Chon tai khoan vua thuc hien giao dich");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_06_Step_07: An nut Tim kiem");
		transactionReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");

		log.info("TC_06_Step_08: An vao giao dich dau tien");
		transactionReport.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_06_Step_09: Xac nhan hien thi Title 'Chi tiet giao dich'");
		verifyEquals(transactionReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), ReportTitle.TRANSACTION_DETAIL);

		log.info("TC_06_Step_10: Xac nhan hien thi dung ma giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.TRANSACTION_NUMBER), transactionID);

		log.info("TC_06_Step_11: Xac nhan hien thi so tai khoan giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.ACCOUNT_CARD), sourceAccount.account);

		log.info("TC_06_Step_12: Xac nhan hien thi Ma Thanh toan");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.CODE_TRANSFER), payID);

		log.info("TC_06_Step_13: Xac nhan hien thi So tien phi");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.TRANSACTION_FEE), fee);

		log.info("TC_06_Step_14: Xac nhan hien thi Loai giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.TRANSACTION_TYPE), ReportTitle.AIR_TICKET);

		log.info("TC_06_Step_15: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_06_Step_16: An nut back ve man hinh menu");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
	
		log.info("TC_06_Step_17: Mo tab Home");
		homePage.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_1");
	}

	@Parameters({ "pass" })
	@Test
	public void TC_07_DatVeMayBayQuocTeKhuHoiThanhCong_1Nguoi_ThanhToanMK(String pass) {

		log.info("TC_07_Step_01: Click Dat ve may bay");
		homePage.clickToDynamicIcon(driver, Air_Text.AIR_TICKET_TEXT);
	
		log.info("TC_07_Step_02: Click dat ve may bay noi dia ");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.DOMESTIC_AIR_TICKET_TEXT);

		log.info("TC_07_Step_03: Click Dong y ");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_AGREE_TEXT);

		log.info("TC_07_Step_04: Chon khu hoi ");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.TWO_WAYS_TEXT);

		log.info("TC_07_Step_05: Chon diem khoi hanh");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.DEPARTURE_PLACE_TEXT);
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.HAN_PLACE);

		log.info("TC_07_Step_06: Chon diem den");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.ARRIVAL_PLACE_TEXT);
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.SGN_PLACE);

		log.info("TC_07_Step_07: Chon ngay di / ngay ve");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.DEPARTURE_DATE_TEXT);
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(5), fifthDay);
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(9), ninthDay);
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONFIRM_TEXT);

		log.info("TC_07_Step_08: Tim chuyen bay");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_SEARCH_FLIGHT_TEXT);

		log.info("TC_07_Step_08: Tim chuyen bay");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_SEARCH_FLIGHT_TEXT);
		source = airTicket.getDynamicTextByID("com.VCB:id/tvTitle2Left");
		dest = airTicket.getDynamicTextByID("com.VCB:id/tvTitle2Right");
		sourceDetail = airTicket.getDynamicTextByID("com.VCB:id/tvDiemDi");
		destDetail = airTicket.getDynamicTextByID("com.VCB:id/tvDiemDen");
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
		airTicket.clickToDynamicButton(Air_Text.BUTTON_ORDER_TICKET_TEXT);

		log.info("TC_07_Step 10: Dien Ten Nguoi lon ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_NAME, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_hoten");

		log.info("TC_07_Step 11: Chon gioi tinh nu");
		airTicket.checkToDynamicTextOrDropDownByLabel(Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/tv_NuContact");

		log.info("TC_07_Step 12: Dien Email  ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_EMAIL, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_email");

		log.info("TC_07_Step 13: Dien Phone ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_PHONE, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_phonenumber");

		log.info("TC_07_Step 14: Dien Noi dung");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.CONTENT, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_content");

		log.info("TC_07_Step 15: Click vao check box Dung lam thong tin hanh khach bay");
		airTicket.clickToDynamicCheckBoxByID("com.VCB:id/iv_check_active");

		log.info("TC_07_Step 16: Click Tiep Tuc");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_07_Step 17: Kiem tra man hinh xac nhan chuyen bay hien thi");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvTitle1"), Air_Text.FLIGHT_CONFIRM_TEXT);

		log.info("TC_07_Step 18_1: Kiem tra dia diem khoi hanh");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem1"), sourceDetail);
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem2"), source);

		log.info("TC_07_Step 18_2: Kiem tra dia diem den");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem3"), destDetail);
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem4"), dest);

		log.info("TC_07_Step 19_1: Kiem tra thong tin chieu di");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextLeft1"), Air_Text.DEPARTURE_WAY_TEXT);

		log.info("TC_07_Step 19_2: Kiem tra thong tin ma chieu di");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvFlightId"), flightCode01);

		log.info("TC_07_Step 19_3: Kiem tra thong tin chieu di : ngay di");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextLeft2"), getRawSplitStringIndex(time, "-", 0).trim());

		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextRight1"), getRawSplitStringIndex(time, "-", 0).trim());

		log.info("TC_07_Step 19_4: Kiem tra thong tin chieu di : gio di ");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextLeft3"), source+" " + departureTime01);

		log.info("TC_07_Step 19_5: Kiem tra thong tin chieu di : gio den");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextRight2"), dest+" " + arrivalTime01);

		log.info("TC_07_Step 19_6: Kiem trathông tin chieu di : thoi gian bay");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvDuration"), duration01);

		log.info("TC_07_Step 20_1: Kiem tra thong tin chieu ve");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextLeft1"), Air_Text.ARRIVAL_WAY_TEXT);

		log.info("TC_07_Step 20_2: Kiem tra thong tin ma chieu ve");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvFlightId"), flightCode02);

		log.info("TC_07_Step 20_3: Kiem tra thong tin chieu ve : ngay di");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextLeft2"), getRawSplitStringIndex(time, "-", 1).trim());

		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextRight1"), getRawSplitStringIndex(time, "-", 1).trim());

		log.info("TC_07_Step 20_4: Kiem tra thong tin chieu ve : gio di ");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextLeft3"), dest+" " + departureTime02);

		log.info("TC_07_Step 20_5: Kiem tra thong tin chieu ve : gio den");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextRight2"), source+" " + arrivalTime02);

		log.info("TC_07_Step 20_6: Kiem trathông tin chieu ve : thoi gian bay");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvDuration"), duration02);

		log.info("TC_07_Step 18: Kiem tra tong so tien hien thi dung");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.TOTAL_MONEY_TEXT, "com.VCB:id/tvText2"), ticketPrice);

		log.info("TC_07_Step_19: Click checkbox dong y dieu khoan dat ve");
		airTicket.clickToDynamicCheckBoxByID("com.VCB:id/checkBoxCondition");

		log.info("TC_07_Step_20: Click Thanh toan");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_PAY_TEXT);

		log.info("TC_07_Step_21: Chọn tai khoan nguon");
		airTicket.clickToDynamicTextByID("com.VCB:id/number_account");
		sourceAccount = airTicket.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		log.info("TC_07_Step_22_3: Xac nhan ma thanh toan");
		payID = airTicket.getAirTicketPriceInfo1Way(Air_Text.PAY_ID_TEXT, "com.VCB:id/tvContent");

		log.info("TC_07_Step_22_4: Xac nhan chieu di bay ");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("4", Air_Text.FLIGHT_WAY_TEXT), source+" → "+dest);

		log.info("TC_07_Step_22_5: Xac nhan so hieu chuyen bay chieu di");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("5", Air_Text.FLIGHT_CODE_TEXT).replace("-", ""), flightCode01);

		log.info("TC_07_Step_22_6: Xac nhan thoi gian bay chieu di");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("6", Air_Text.FLIGHT_TIME_TEXT), departureTime01 + " - " + arrivalTime01);

		log.info("TC_07_Step_22_7: Xac nhan chieu ve bay ");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("9", Air_Text.FLIGHT_WAY_TEXT), dest+" → "+source);

		log.info("TC_07_Step_22_8: Xac nhan so hieu chuyen bay chieu ve");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("10", Air_Text.FLIGHT_CODE_TEXT).replace("-", ""), flightCode02);

		log.info("TC_07_Step_22_6: Xac nhan thoi gian bay chieu ve");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("11", Air_Text.FLIGHT_TIME_TEXT), departureTime02 + " - " + arrivalTime02);

		log.info("TC_07_Step_22_7: Xac nhan Tong tien thanh toan");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.TOTAL_PAY_TEXT, "com.VCB:id/tvContent"), ticketPrice);

		log.info("TC_07_Step_22: Click Tiep tuc");
		payID = airTicket.getAirTicketPriceInfo1Way(Air_Text.PAY_ID_TEXT, "com.VCB:id/tvContent");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.TOTAL_PAY_TEXT, "com.VCB:id/tvContent"), ticketPrice);

		log.info("TC_07_Step 23: Click Tiep Tuc");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_07_Step_24: Xac nhan giao dich");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.FROM_ACCOUNT_TEXT, "com.VCB:id/tvTu_tai_khoan"), sourceAccount.account);
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.PAY_ID_TEXT, "com.VCB:id/tvBooking_number"), payID);
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.MONEY_AMOUNT_TEXT, "com.VCB:id/tvAmount"), ticketPrice);

		log.info("TC_07_Step_25: Chon phuong thuc xac thuc SMS OTP");
		airTicket.clickToDynamicTextByID("com.VCB:id/tvptxt");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.SMS_OTP_TEXT);
		fee = airTicket.getAirTicketPriceInfo1Way(Air_Text.FEE_VALUE_TEXT, "com.VCB:id/tvPhiGiaoDich");

		log.info("TC_07_Step_26: Click Tiep Tuc");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_07_Step_27: Nhap Ma OTP roi click Tiep tuc");
		airTicket.inputToDynamicPopupPasswordInput(driver, pass, Air_Text.BUTTON_CONTINUE_TEXT);
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_07_Step_28: Xac nhan hien thi man hinh thanh toan thanh cong");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.PAY_ID_TEXT, "com.VCB:id/tvContent"), payID);
		transactionID = airTicket.getAirTicketPriceInfo1Way(Air_Text.TRANSACTION_ID_TEXT, "com.VCB:id/tvContent");

		log.info("TC_07_Step_29: Clich nut Home");
		airTicket.clickToDynamicIcon("com.VCB:id/ivHome");
		
	}

	@Test
	public void TC_08_DatVeMayBayQuocTeKhuHoiThanhCong_1Nguoi_BaoCaoGiaoDich() {

		log.info("TC_08_Step_01: Mo tab Menu");
		homePage.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_5");

		log.info("TC_08_Step_02: Mo sub-menu 'Bao cao giao dich");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSACTION_REPORT);
	
		log.info("TC_08_Step_03: An vao Dropdown 'Tat ca cac loai giao dich");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectTransType");

		log.info("TC_08_Step_04: Chon 'Thanh toan ve may bay'");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.AIR_TICKET);

		log.info("TC_08_Step_05: An vao Dropdown 'Chon tai khoan/the");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_08_Step_06: Chon tai khoan vua thuc hien giao dich");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_08_Step_07: An nut Tim kiem");
		transactionReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");

		log.info("TC_08_Step_08: An vao giao dich dau tien");
		transactionReport.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_08_Step_09: Xac nhan hien thi Title 'Chi tiet giao dich'");
		verifyEquals(transactionReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), ReportTitle.TRANSACTION_DETAIL);

		log.info("TC_08_Step_10: Xac nhan hien thi dung ma giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.TRANSACTION_NUMBER), transactionID);

		log.info("TC_08_Step_11: Xac nhan hien thi so tai khoan giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.ACCOUNT_CARD), sourceAccount.account);

		log.info("TC_08_Step_12: Xac nhan hien thi Ma Thanh toan");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.CODE_TRANSFER), payID);

		log.info("TC_08_Step_13: Xac nhan hien thi So tien phi");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.TRANSACTION_FEE), fee);

		log.info("TC_08_Step_14: Xac nhan hien thi Loai giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.TRANSACTION_TYPE), ReportTitle.AIR_TICKET);

		log.info("TC_08_Step_15: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_08_Step_16: An nut back ve man hinh menu");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
	
		log.info("TC_08_Step_17: Mo tab Home");
		homePage.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_1");
	}

	@Parameters({ "otp" })
	@Test
	public void TC_09_DatVeMayBayNoiDiaKhuHoi_ThanhToanSau_1Nguoi_ThanhToanOTP(String otp) {

		log.info("TC_09_Step_01: Click Dat ve may bay");
		homePage.clickToDynamicIcon(driver, Air_Text.AIR_TICKET_TEXT);
	
		log.info("TC_09_Step_02: Click dat ve may bay noi dia ");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.DOMESTIC_AIR_TICKET_TEXT);

		log.info("TC_09_Step_03: Click Dong y ");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_AGREE_TEXT);

		log.info("TC_09_Step_04: Chon khu hoi ");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.TWO_WAYS_TEXT);

		log.info("TC_09_Step_05: Chon diem khoi hanh");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.DEPARTURE_PLACE_TEXT);
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.HAN_PLACE);

		log.info("TC_09_Step_06: Chon diem den");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.ARRIVAL_PLACE_TEXT);
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.SGN_PLACE);

		log.info("TC_09_Step_07: Chon ngay di / ngay ve");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.DEPARTURE_DATE_TEXT);
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(5), fifthDay);
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(9), ninthDay);
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONFIRM_TEXT);

		log.info("TC_09_Step_08: Tim chuyen bay");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_SEARCH_FLIGHT_TEXT);
		source = airTicket.getDynamicTextByID("com.VCB:id/tvTitle2Left");
		dest = airTicket.getDynamicTextByID("com.VCB:id/tvTitle2Right");
		sourceDetail = airTicket.getDynamicTextByID("com.VCB:id/tvDiemDi");
		destDetail = airTicket.getDynamicTextByID("com.VCB:id/tvDiemDen");
		time = airTicket.getDynamicTextByID("com.VCB:id/tvTitle2");

		log.info("TC_09_Step_09_1: Chon chuyen bay chieu di");
		airTicket.clickToDynamicFlightTwoWays(0, "com.VCB:id/recy_chieudi", "com.VCB:id/tv_flightcode_internal", Air_Text.SUGGEST_FLIGHT_CODE_VN);
		
		log.info("TC_09_Step_09_2: Chon chuyen bay chieu ve");
		airTicket.clickToDynamicFlightTwoWays(0, "com.VCB:id/recy_chieuve", "com.VCB:id/tv_flightcode_internal", Air_Text.SUGGEST_FLIGHT_CODE_VN);
		
		flightCode01 = airTicket.getDynamicTextByID("com.VCB:id/tv_flightCode_internal_chieudi");
		departureTime01 = getRawSplitStringIndex(airTicket.getDynamicTextByID("com.VCB:id/tv_time_internal_chieudi"), "-", 0);
		arrivalTime01 = getRawSplitStringIndex(airTicket.getDynamicTextByID("com.VCB:id/tv_time_internal_chieudi"), "-", 1);
		
		flightCode02 = airTicket.getDynamicTextByID("com.VCB:id/tv_flightCode_internal_chieuve");
		departureTime02 = getRawSplitStringIndex(airTicket.getDynamicTextByID("com.VCB:id/tv_time_internal_chieuve"), "-", 0);
		arrivalTime02 = getRawSplitStringIndex(airTicket.getDynamicTextByID("com.VCB:id/tv_time_internal_chieuve"), "-", 1);

		log.info("TC_09_Step_09_3: Click dat ve");
		ticketPrice = airTicket.getDynamicTextByID("com.VCB:id/tv_Sum_amount_flight_selected");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_ORDER_TICKET_TEXT);

		log.info("TC_09_Step 10: Dien Ten Nguoi lon ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_NAME, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_hoten");

		log.info("TC_09_Step 11: Chon gioi tinh nu");
		airTicket.checkToDynamicTextOrDropDownByLabel(Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/tv_NuContact");

		log.info("TC_09_Step 12: Dien Email  ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_EMAIL, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_email");

		log.info("TC_09_Step 13: Dien Phone ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_PHONE, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_phonenumber");

		log.info("TC_09_Step 14: Dien Noi dung");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.CONTENT, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_content");

		log.info("TC_09_Step 15: Click vao check box Dung lam thong tin hanh khach bay");
		airTicket.clickToDynamicCheckBoxByID("com.VCB:id/iv_check_active");

		log.info("TC_09_Step 16: Click Tiep Tuc");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_09_Step 17: Kiem tra man hinh xac nhan chuyen bay hien thi");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvTitle1"), Air_Text.FLIGHT_CONFIRM_TEXT);
		
		log.info("TC_09_Step 17_1: Kiem tra dia diem khoi hanh");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem1"), sourceDetail);
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem2"), source);

		log.info("TC_09_Step 17_2: Kiem tra dia diem den");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem3"), destDetail);
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem4"), dest);

		log.info("TC_09_Step 17_3: Kiem tra thong tin chieu di");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextLeft1"), Air_Text.DEPARTURE_WAY_TEXT);

		log.info("TC_09_Step 17_4: Kiem tra thong tin ma chieu di");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvFlightId"), flightCode01);

		log.info("TC_09_Step 17_5: Kiem tra thong tin chieu di : ngay di");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextLeft2"), getRawSplitStringIndex(time, "-", 0).trim());

		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextRight1"), getRawSplitStringIndex(time, "-", 0).trim());

		log.info("TC_09_Step 17_6: Kiem tra thong tin chieu di : gio di ");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextLeft3"), source+" "+departureTime01);

		log.info("TC_09_Step 17_7: Kiem tra thong tin chieu di : gio den");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextRight2"), dest+" "+arrivalTime01);

		log.info("TC_09_Step 17_8: Kiem trathông tin chieu di : thoi gian bay");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvDuration"), duration01);

		log.info("TC_09_Step 17_9: Kiem tra thong tin chieu ve");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextLeft1"), Air_Text.ARRIVAL_WAY_TEXT);

		log.info("TC_09_Step 17_10: Kiem tra thong tin ma chieu ve");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvFlightId"), flightCode02);

		log.info("TC_09_Step 17_11: Kiem tra thong tin chieu ve : ngay di");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextLeft2"), getRawSplitStringIndex(time, "-", 1).trim());

		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextRight1"), getRawSplitStringIndex(time, "-", 1).trim());

		log.info("TC_09_Step 17_12: Kiem tra thong tin chieu ve : gio di ");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextLeft3"), dest+ " "+departureTime02);

		log.info("TC_09_Step 17_13: Kiem tra thong tin chieu ve : gio den");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextRight2"), source+" "+arrivalTime02);

		log.info("TC_09_Step 17_14: Kiem trathông tin chieu ve : thoi gian bay");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvDuration"), duration02);

		log.info("TC_09_Step 18: Kiem tra tong so tien hien thi dung");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.TOTAL_MONEY_TEXT, "com.VCB:id/tvText2"), ticketPrice);

		log.info("TC_09_Step_19: Click checkbox dong y dieu khoan dat ve");
		airTicket.clickToDynamicCheckBoxByID("com.VCB:id/checkBoxCondition");

		log.info("TC_09_Step_20: Click Thanh toan sau");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_PAY_LATER_TEXT);

		log.info("TC_09_Step_22: Hien thi man hinh Ket qua");
		payID = airTicket.getDynamicTextByID("com.VCB:id/tvBookCode");

		log.info("TC_09_Step_23: Ve man hinh ve may bay");
		airTicket.clickToDynamicIcon("com.VCB:id/ivTitleRight");

		log.info("TC_09_Step_24: Chon Thanh toan ve may bay");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.AIR_TICKET_PAY_TEXT);

		log.info("TC_09_Step_25: Nhap ma thanh toan");
		airTicket.inputToDynamicInputBoxById(payID, "com.VCB:id/edtBookCode");

		log.info("TC_09_Step_26: An tiep tuc");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_09_Step_27_1: Chọn tai khoan nguon");
		airTicket.clickToDynamicTextByID("com.VCB:id/number_account");
		sourceAccount = airTicket.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		log.info("TC_09_Step_27_2: Xac nhan ma thanh toan");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.PAY_ID_TEXT, "com.VCB:id/tvContent"), payID);
		
		log.info("TC_09_Step_27_3: Xac nhan chieu di bay ");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("4", Air_Text.FLIGHT_WAY_TEXT), source+" → "+dest);

		log.info("TC_09_Step_27_4: Xac nhan so hieu chuyen bay chieu di");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("5", Air_Text.FLIGHT_CODE_TEXT).replace("-", ""), flightCode01);

		log.info("TC_09_Step_27_5: Xac nhan thoi gian bay chieu di");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("6", Air_Text.FLIGHT_TIME_TEXT), departureTime01 + " - " + arrivalTime01);

		log.info("TC_09_Step_27_6: Xac nhan chieu ve bay ");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("9", Air_Text.FLIGHT_WAY_TEXT), dest+" → "+source);

		log.info("TC_09_Step_27_7: Xac nhan so hieu chuyen bay chieu ve");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("10", Air_Text.FLIGHT_CODE_TEXT).replace("-", ""), flightCode02);

		log.info("TC_09_Step_27_8: Xac nhan thoi gian bay chieu ve");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("11", Air_Text.FLIGHT_TIME_TEXT), departureTime02 + " - " + arrivalTime02);

		log.info("TC_09_Step_27_9: Xac nhan Tong tien thanh toan");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.TOTAL_PAY_TEXT, "com.VCB:id/tvContent"), ticketPrice);
		
		log.info("TC_09_Step_28: An tiep tuc");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_09_Step_29_1: Xac nhan giao dich");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.FROM_ACCOUNT_TEXT, "com.VCB:id/tvTu_tai_khoan"), sourceAccount.account);
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.PAY_ID_TEXT, "com.VCB:id/tvBooking_number"), payID);
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.MONEY_AMOUNT_TEXT, "com.VCB:id/tvAmount"), ticketPrice);
		
		log.info("TC_09_Step_29_2: Chon phuong thuc xac thuc SMS OTP");
		airTicket.clickToDynamicTextByID("com.VCB:id/tvptxt");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.SMS_OTP_TEXT);

		log.info("TC_09_Step_30: Click Tiep Tuc");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_09_Step_31: Nhap Ma OTP roi click Tiep tuc");
		airTicket.inputToDynamicOtp(driver, otp, Air_Text.BUTTON_CONTINUE_TEXT);
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_09_Step_32: Xac nhan hien thi man hinh thanh toan thanh cong");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.PAY_ID_TEXT, "com.VCB:id/tvContent"), payID);
		transactionID = airTicket.getAirTicketPriceInfo1Way(Air_Text.TRANSACTION_ID_TEXT, "com.VCB:id/tvContent");

		log.info("TC_09_Step_33: Clich nut Home");
		airTicket.clickToDynamicIcon("com.VCB:id/ivHome");
		
	}

	@Test
	public void TC_10_DatVeMayBayNoiDiaKhuHoi_ThanhToanSau_1Nguoi_BaoCaoGiaoDich() {

		log.info("TC_10_Step_01: Mo tab Menu");
		homePage.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_5");

		log.info("TC_10_Step_02: Mo sub-menu 'Bao cao giao dich");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSACTION_REPORT);
	
		log.info("TC_10_Step_03: An vao Dropdown 'Tat ca cac loai giao dich");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectTransType");

		log.info("TC_10_Step_04: Chon 'Thanh toan ve may bay'");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.AIR_TICKET);

		log.info("TC_10_Step_05: An vao Dropdown 'Chon tai khoan/the");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_10_Step_06: Chon tai khoan vua thuc hien giao dich");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_10_Step_07: An nut Tim kiem");
		transactionReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");

		log.info("TC_10_Step_08: An vao giao dich dau tien");
		transactionReport.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_10_Step_09: Xac nhan hien thi Title 'Chi tiet giao dich'");
		verifyEquals(transactionReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), ReportTitle.TRANSACTION_DETAIL);

		log.info("TC_10_Step_10: Xac nhan hien thi dung ma giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.TRANSACTION_NUMBER), transactionID);

		log.info("TC_10_Step_11: Xac nhan hien thi so tai khoan giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.ACCOUNT_CARD), sourceAccount.account);

		log.info("TC_10_Step_12: Xac nhan hien thi Ma Thanh toan");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.CODE_TRANSFER), payID);

		log.info("TC_10_Step_13: Xac nhan hien thi So tien phi");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.TRANSACTION_FEE), fee);

		log.info("TC_10_Step_14: Xac nhan hien thi Loai giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.TRANSACTION_TYPE), ReportTitle.AIR_TICKET);

		log.info("TC_10_Step_15: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_10_Step_16: An nut back ve man hinh menu");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_10_Step_17: Mo tab Home");
		homePage.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_1");
	}

	@Parameters({ "pass" })
	@Test
	public void TC_11_DatVeMayBayNoiDiaKhuHoi_ThanhToanSau_1Nguoi_ThanhToanMK(String password) {

		log.info("TC_11_Step_01: Click Dat ve may bay");
		homePage.clickToDynamicIcon(driver, Air_Text.AIR_TICKET_TEXT);
	
		log.info("TC_11_Step_02: Click dat ve may bay noi dia ");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.DOMESTIC_AIR_TICKET_TEXT);

		log.info("TC_11_Step_03: Click Dong y ");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_AGREE_TEXT);

		log.info("TC_11_Step_04: Chon khu hoi ");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.TWO_WAYS_TEXT);

		log.info("TC_11_Step_05: Chon diem khoi hanh");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.DEPARTURE_PLACE_TEXT);
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.HAN_PLACE);

		log.info("TC_11_Step_06: Chon diem den");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.ARRIVAL_PLACE_TEXT);
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.SGN_PLACE);

		log.info("TC_11_Step_07: Chon ngay di / ngay ve");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.DEPARTURE_DATE_TEXT);
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(5), fifthDay);
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(9), ninthDay);
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONFIRM_TEXT);

		log.info("TC_11_Step_08: Tim chuyen bay");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_SEARCH_FLIGHT_TEXT);
		source = airTicket.getDynamicTextByID("com.VCB:id/tvTitle2Left");
		dest = airTicket.getDynamicTextByID("com.VCB:id/tvTitle2Right");
		sourceDetail = airTicket.getDynamicTextByID("com.VCB:id/tvDiemDi");
		destDetail = airTicket.getDynamicTextByID("com.VCB:id/tvDiemDen");
		time = airTicket.getDynamicTextByID("com.VCB:id/tvTitle2");

		log.info("TC_11_Step_09_1: Chon chuyen bay chieu di");
		airTicket.clickToDynamicFlightTwoWays(0, "com.VCB:id/recy_chieudi", "com.VCB:id/tv_flightcode_internal", Air_Text.SUGGEST_FLIGHT_CODE_VN);

		log.info("TC_11_Step_09_2: Chon chuyen bay chieu ve");
		airTicket.clickToDynamicFlightTwoWays(0, "com.VCB:id/recy_chieuve", "com.VCB:id/tv_flightcode_internal", Air_Text.SUGGEST_FLIGHT_CODE_VN);

		flightCode01 = airTicket.getDynamicTextByID("com.VCB:id/tv_flightCode_internal_chieudi");
		departureTime01 = getRawSplitStringIndex(airTicket.getDynamicTextByID("com.VCB:id/tv_time_internal_chieudi"), "-", 0);
		arrivalTime01 = getRawSplitStringIndex(airTicket.getDynamicTextByID("com.VCB:id/tv_time_internal_chieudi"), "-", 1);
		
		flightCode02 = airTicket.getDynamicTextByID("com.VCB:id/tv_flightCode_internal_chieuve");
		departureTime02 = getRawSplitStringIndex(airTicket.getDynamicTextByID("com.VCB:id/tv_time_internal_chieuve"), "-", 0);
		arrivalTime02 = getRawSplitStringIndex(airTicket.getDynamicTextByID("com.VCB:id/tv_time_internal_chieuve"), "-", 1);
		
		log.info("TC_11_Step_09_3: Click dat ve");
		ticketPrice = airTicket.getDynamicTextByID("com.VCB:id/tv_Sum_amount_flight_selected");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_ORDER_TICKET_TEXT);

		log.info("TC_11_Step 10: Dien Ten Nguoi lon ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_NAME, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_hoten");

		log.info("TC_11_Step 11: Chon gioi tinh nu");
		airTicket.checkToDynamicTextOrDropDownByLabel(Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/tv_NuContact");

		log.info("TC_11_Step 12: Dien Email  ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_EMAIL, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_email");

		log.info("TC_11_Step 13: Dien Phone ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_PHONE, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_phonenumber");

		log.info("TC_11_Step 14: Dien Noi dung");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.CONTENT, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_content");

		log.info("TC_11_Step 15: Click vao check box Dung lam thong tin hanh khach bay");
		airTicket.clickToDynamicCheckBoxByID("com.VCB:id/iv_check_active");

		log.info("TC_11_Step 16: Click Tiep Tuc");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_11_Step 17: Kiem tra man hinh xac nhan chuyen bay hien thi");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvTitle1"), Air_Text.FLIGHT_CONFIRM_TEXT);

		log.info("TC_11_Step 17_1: Kiem tra dia diem khoi hanh");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem1"), sourceDetail);
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem2"), source);

		log.info("TC_11_Step 17_2: Kiem tra dia diem den");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem3"), destDetail);
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem4"), dest);

		log.info("TC_11_Step 17_3: Kiem tra thong tin chieu di");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextLeft1"), Air_Text.DEPARTURE_WAY_TEXT);

		log.info("TC_11_Step 17_4: Kiem tra thong tin ma chieu di");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvFlightId"), flightCode01);

		log.info("TC_11_Step 17_5: Kiem tra thong tin chieu di : ngay di");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextLeft2"), getRawSplitStringIndex(time, "-", 0).trim());

		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextRight1"), getRawSplitStringIndex(time, "-", 0).trim());

		log.info("TC_11_Step 17_6: Kiem tra thong tin chieu di : gio di ");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextLeft3"), source+" "+departureTime01);

		log.info("TC_11_Step 17_7: Kiem tra thong tin chieu di : gio den");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextRight2"), dest+" "+arrivalTime01);

		log.info("TC_11_Step 17_8: Kiem trathông tin chieu di : thoi gian bay");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvDuration"), duration01);

		log.info("TC_11_Step 17_9: Kiem tra thong tin chieu ve");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextLeft1"), Air_Text.ARRIVAL_WAY_TEXT);

		log.info("TC_11_Step 17_10: Kiem tra thong tin ma chieu ve");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvFlightId"), flightCode02);

		log.info("TC_11_Step 17_11: Kiem tra thong tin chieu ve : ngay di");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextLeft2"), getRawSplitStringIndex(time, "-", 1).trim());

		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextRight1"), getRawSplitStringIndex(time, "-", 1).trim());

		log.info("TC_11_Step 17_12: Kiem tra thong tin chieu ve : gio di ");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextLeft3"), dest+ " "+departureTime02);

		log.info("TC_11_Step 17_13: Kiem tra thong tin chieu ve : gio den");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextRight2"), source+" "+arrivalTime02);

		log.info("TC_11_Step 17_14: Kiem trathông tin chieu ve : thoi gian bay");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvDuration"), duration02);

		log.info("TC_11_Step 18: Kiem tra tong so tien hien thi dung");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.TOTAL_MONEY_TEXT, "com.VCB:id/tvText2"), ticketPrice);

		log.info("TC_11_Step_19: Click checkbox dong y dieu khoan dat ve");
		airTicket.clickToDynamicCheckBoxByID("com.VCB:id/checkBoxCondition");

		log.info("TC_11_Step_20: Click Thanh toan sau");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_PAY_LATER_TEXT);

		log.info("TC_11_Step_22: Hien thi man hinh Ket qua");
		payID = airTicket.getDynamicTextByID("com.VCB:id/tvBookCode");

		log.info("TC_11_Step_23: Ve man hinh ve may bay");
		airTicket.clickToDynamicIcon("com.VCB:id/ivTitleRight");

		log.info("TC_11_Step_24: Chon Thanh toan ve may bay");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.AIR_TICKET_PAY_TEXT);

		log.info("TC_11_Step_25: Nhap ma thanh toan");
		airTicket.inputToDynamicInputBoxById(payID, "com.VCB:id/edtBookCode");

		log.info("TC_11_Step_26: An tiep tuc");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_11_Step_27_1: Chọn tai khoan nguon");
		airTicket.clickToDynamicTextByID("com.VCB:id/number_account");
		sourceAccount = airTicket.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		log.info("TC_11_Step_27_2: Xac nhan ma thanh toan");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.PAY_ID_TEXT, "com.VCB:id/tvContent"), payID);
		
		log.info("TC_11_Step_27_3: Xac nhan chieu di bay ");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("4", Air_Text.FLIGHT_WAY_TEXT), source+" → "+dest);

		log.info("TC_11_Step_27_4: Xac nhan so hieu chuyen bay chieu di");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("5", Air_Text.FLIGHT_CODE_TEXT).replace("-", ""), flightCode01);

		log.info("TC_11_Step_27_5: Xac nhan thoi gian bay chieu di");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("6", Air_Text.FLIGHT_TIME_TEXT), departureTime01 + " - " + arrivalTime01);

		log.info("TC_11_Step_27_6: Xac nhan chieu ve bay ");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("9", Air_Text.FLIGHT_WAY_TEXT), dest+" → "+source);

		log.info("TC_11_Step_27_7: Xac nhan so hieu chuyen bay chieu ve");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("10", Air_Text.FLIGHT_CODE_TEXT).replace("-", ""), flightCode02);

		log.info("TC_11_Step_27_8: Xac nhan thoi gian bay chieu ve");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("11", Air_Text.FLIGHT_TIME_TEXT), departureTime02 + " - " + arrivalTime02);

		log.info("TC_11_Step_27_9: Xac nhan Tong tien thanh toan");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.TOTAL_PAY_TEXT, "com.VCB:id/tvContent"), ticketPrice);

		log.info("TC_11_Step_28: An tiep tuc");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_01_Step_29_1: Xac nhan giao dich");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.FROM_ACCOUNT_TEXT, "com.VCB:id/tvTu_tai_khoan"), sourceAccount.account);
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.PAY_ID_TEXT, "com.VCB:id/tvBooking_number"), payID);
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.MONEY_AMOUNT_TEXT, "com.VCB:id/tvAmount"), ticketPrice);
		
		log.info("TC_11_Step_29_2: Chon phuong thuc xac thuc MK");
		airTicket.clickToDynamicTextByID("com.VCB:id/tvptxt");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.PASSWORD_TEXT);

		log.info("TC_11_Step_30: Click Tiep Tuc");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_11_Step_31: Nhap Mat khau roi click Tiep tuc");
		airTicket.inputToDynamicPopupPasswordInput(driver, password, Air_Text.BUTTON_CONTINUE_TEXT);
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_11_Step_32: Xac nhan hien thi man hinh thanh toan thanh cong");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.PAY_ID_TEXT, "com.VCB:id/tvContent"), payID);
		transactionID = airTicket.getAirTicketPriceInfo1Way(Air_Text.TRANSACTION_ID_TEXT, "com.VCB:id/tvContent");

		log.info("TC_11_Step_33: Clich nut Home");
		airTicket.clickToDynamicIcon("com.VCB:id/ivHome");
		
	}

	@Test
	public void TC_12_DatVeMayBayNoiDiaKhuHoi_ThanhToanSau_1Nguoi_BaoCaoGiaoDich() {

		log.info("TC_12_Step_01: Mo tab Menu");
		homePage.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_5");

		log.info("TC_12_Step_02: Mo sub-menu 'Bao cao giao dich");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSACTION_REPORT);
	
		log.info("TC_12_Step_03: An vao Dropdown 'Tat ca cac loai giao dich");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectTransType");

		log.info("TC_12_Step_04: Chon 'Thanh toan ve may bay'");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.AIR_TICKET);

		log.info("TC_12_Step_05: An vao Dropdown 'Chon tai khoan/the");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_12_Step_06: Chon tai khoan vua thuc hien giao dich");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_12_Step_07: An nut Tim kiem");
		transactionReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");

		log.info("TC_12_Step_08: An vao giao dich dau tien");
		transactionReport.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_12_Step_09: Xac nhan hien thi Title 'Chi tiet giao dich'");
		verifyEquals(transactionReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), ReportTitle.TRANSACTION_DETAIL);

		log.info("TC_12_Step_10: Xac nhan hien thi dung ma giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.TRANSACTION_NUMBER), transactionID);

		log.info("TC_12_Step_11: Xac nhan hien thi so tai khoan giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.ACCOUNT_CARD), sourceAccount.account);

		log.info("TC_12_Step_12: Xac nhan hien thi Ma Thanh toan");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.CODE_TRANSFER), payID);

		log.info("TC_12_Step_13: Xac nhan hien thi So tien phi");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.TRANSACTION_FEE), fee);

		log.info("TC_12_Step_14: Xac nhan hien thi Loai giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.TRANSACTION_TYPE), ReportTitle.AIR_TICKET);

		log.info("TC_12_Step_15: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_12_Step_16: An nut back ve man hinh menu");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		
		log.info("TC_10_Step_17: Mo tab Home");
		homePage.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_1");
	}

	@Parameters({ "otp" })
	@Test
	public void TC_13_DatVeMayBayQuocTeKhuHoi_ThanhToanSau_1Nguoi_ThanhToanOTP(String otp) {

		log.info("TC_13_Step_01: Click Dat ve may bay");
		homePage.clickToDynamicIcon(driver, Air_Text.AIR_TICKET_TEXT);
	
		log.info("TC_13_Step_02: Click dat ve may bay quoc te ");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.INTERNATIONAL_AIR_TICKET_TEXT);

		log.info("TC_13_Step_03: Click Dong y ");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_AGREE_TEXT);

		log.info("TC_13_Step_04: Chon khu hoi ");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.TWO_WAYS_TEXT);

		log.info("TC_13_Step_05: Chon diem khoi hanh");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.DEPARTURE_PLACE_TEXT);
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.HAN_PLACE);

		log.info("TC_13_Step_06: Chon diem den");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.ARRIVAL_PLACE_TEXT);
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.BKK_PLACE);

		log.info("TC_13_Step_07: Chon ngay di / ngay ve");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.DEPARTURE_DATE_TEXT);
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(5), fifthDay);
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(9), ninthDay);
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONFIRM_TEXT);

		log.info("TC_13_Step_08: Tim chuyen bay");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_SEARCH_FLIGHT_TEXT);
		source = airTicket.getDynamicTextByID("com.VCB:id/tvTitle2Left");
		dest = airTicket.getDynamicTextByID("com.VCB:id/tvTitle2Right");
		sourceDetail = airTicket.getDynamicTextByID("com.VCB:id/tvDiemDi");
		destDetail = airTicket.getDynamicTextByID("com.VCB:id/tvDiemDen");
		time = airTicket.getDynamicTextByID("com.VCB:id/tvTitle2");

		log.info("TC_13_Step_09: Chon chuyen bay và dat ve");
		airTicket.clickToDynamicFlightTwoWays(0, "com.VCB:id/recycle_international", "com.VCB:id/tv_international_flightNo_chieudi", Air_Text.SUGGEST_FLIGHT_CODE_VN);
		
		flightCode01 = airTicket.getDynamicTextByID("com.VCB:id/tv_flightCode_External_chieudi");
		departureTime01 = getRawSplitStringIndex(airTicket.getDynamicTextByID("com.VCB:id/tv_time_External_chieudi"), "-", 0);
		arrivalTime01 = getRawSplitStringIndex(airTicket.getDynamicTextByID("com.VCB:id/tv_time_External_chieudi"), "-", 1);
		
		flightCode02 = airTicket.getDynamicTextByID("com.VCB:id/tv_flightCode_External_chieuve");
		departureTime02 = getRawSplitStringIndex(airTicket.getDynamicTextByID("com.VCB:id/tv_time_External_chieuve"), "-", 0);
		arrivalTime02 = getRawSplitStringIndex(airTicket.getDynamicTextByID("com.VCB:id/tv_time_External_chieuve"), "-", 1);
		
		ticketPrice = airTicket.getDynamicTextByID("com.VCB:id/tv_amount_flight_selected");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_ORDER_TICKET_TEXT);

		log.info("TC_13_Step 10: Dien Ten Nguoi lon ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_NAME, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_hoten");

		log.info("TC_13_Step 11: Chon gioi tinh nu");
		airTicket.checkToDynamicTextOrDropDownByLabel(Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/tv_NuContact");

		log.info("TC_13_Step 12: Dien Email  ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_EMAIL, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_email");

		log.info("TC_13_Step 13: Dien Phone ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_PHONE, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_phonenumber");

		log.info("TC_13_Step 14: Dien Noi dung");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.CONTENT, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_content");

		log.info("TC_13_Step 15: Click vao check box Dung lam thong tin hanh khach bay");
		airTicket.clickToDynamicCheckBoxByID("com.VCB:id/iv_check_active");

		log.info("TC_13_Step 16: Click Tiep Tuc");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_13_Step 17: Kiem tra man hinh xac nhan chuyen bay hien thi");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvTitle1"), Air_Text.FLIGHT_CONFIRM_TEXT);

		log.info("TC_13_Step 18_1: Kiem tra dia diem khoi hanh");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem1"), sourceDetail);
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem2"), source);

		log.info("TC_13_Step 18_2: Kiem tra dia diem den");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem3"), destDetail);
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem4"), dest);

		log.info("TC_13_Step 19_1: Kiem tra thong tin chieu di");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextLeft1"), Air_Text.DEPARTURE_WAY_TEXT);

		log.info("TC_13_Step 19_2: Kiem tra thong tin ma chieu di");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvFlightId"), flightCode01);

		log.info("TC_13_Step 19_3: Kiem tra thong tin chieu di : ngay di");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextLeft2"), getRawSplitStringIndex(time, "-", 0).trim());

		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextRight1"), getRawSplitStringIndex(time, "-", 0).trim());

		log.info("TC_13_Step 19_4: Kiem tra thong tin chieu di : gio di ");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextLeft3"), source+" " + departureTime01);

		log.info("TC_13_Step 19_5: Kiem tra thong tin chieu di : gio den");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextRight2"), dest+" " + arrivalTime01);

		log.info("TC_13_Step 19_6: Kiem trathông tin chieu di : thoi gian bay");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvDuration"), duration01);

		log.info("TC_13_Step 20_1: Kiem tra thong tin chieu ve");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextLeft1"), Air_Text.ARRIVAL_WAY_TEXT);

		log.info("TC_13_Step 20_2: Kiem tra thong tin ma chieu ve");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvFlightId"), flightCode02);

		log.info("TC_13_Step 20_3: Kiem tra thong tin chieu ve : ngay di");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextLeft2"), getRawSplitStringIndex(time, "-", 1).trim());

		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextRight1"), getRawSplitStringIndex(time, "-", 1).trim());

		log.info("TC_13_Step 20_4: Kiem tra thong tin chieu ve : gio di ");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextLeft3"), dest+" " + departureTime02);

		log.info("TC_13_Step 20_5: Kiem tra thong tin chieu ve : gio den");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextRight2"), source+" " + arrivalTime02);

		log.info("TC_13_Step 20_6: Kiem trathông tin chieu ve : thoi gian bay");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvDuration"), duration02);

		log.info("TC_13_Step 18: Kiem tra tong so tien hien thi dung");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.TOTAL_MONEY_TEXT, "com.VCB:id/tvText2"), ticketPrice);

		log.info("TC_13_Step_19: Click checkbox dong y dieu khoan dat ve");
		airTicket.clickToDynamicCheckBoxByID("com.VCB:id/checkBoxCondition");

		log.info("TC_13_Step_20: Click Thanh toan sau");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_PAY_LATER_TEXT);

		log.info("TC_13_Step_22: Hien thi man hinh Ket qua");
		payID = airTicket.getDynamicTextByID("com.VCB:id/tvBookCode");

		log.info("TC_13_Step_23: Ve man hinh ve may bay");
		airTicket.clickToDynamicIcon("com.VCB:id/ivTitleRight");

		log.info("TC_13_Step_24: Chon Thanh toan ve may bay");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.AIR_TICKET_PAY_TEXT);

		log.info("TC_13_Step_25: Nhap ma thanh toan");
		airTicket.inputToDynamicInputBoxById(payID, "com.VCB:id/edtBookCode");

		log.info("TC_13_Step_26: An tiep tuc");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_13_Step_27: Chọn tai khoan nguon");
		airTicket.clickToDynamicTextByID("com.VCB:id/number_account");
		sourceAccount = airTicket.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		log.info("TC_13_Step_22_3: Xac nhan ma thanh toan");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.PAY_ID_TEXT, "com.VCB:id/tvContent"), payID);
	
		log.info("TC_13_Step_22_4: Xac nhan chieu di bay ");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("4", Air_Text.FLIGHT_WAY_TEXT), source+" → "+dest);

		log.info("TC_13_Step_22_5: Xac nhan so hieu chuyen bay chieu di");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("5", Air_Text.FLIGHT_CODE_TEXT).replace("-", ""), flightCode01);

		log.info("TC_13_Step_22_6: Xac nhan thoi gian bay chieu di");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("6", Air_Text.FLIGHT_TIME_TEXT), departureTime01 + " - " + arrivalTime01);

		log.info("TC_13_Step_22_7: Xac nhan chieu ve bay ");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("9", Air_Text.FLIGHT_WAY_TEXT), dest+" → "+source);

		log.info("TC_13_Step_22_8: Xac nhan so hieu chuyen bay chieu ve");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("10", Air_Text.FLIGHT_CODE_TEXT).replace("-", ""), flightCode02);

		log.info("TC_13_Step_22_6: Xac nhan thoi gian bay chieu ve");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("11", Air_Text.FLIGHT_TIME_TEXT), departureTime02 + " - " + arrivalTime02);

		log.info("TC_13_Step_22_7: Xac nhan Tong tien thanh toan");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.TOTAL_PAY_TEXT, "com.VCB:id/tvContent"), ticketPrice);

		log.info("TC_13_Step_28: An tiep tuc");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_13_Step_24: Xac nhan giao dich");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.FROM_ACCOUNT_TEXT, "com.VCB:id/tvTu_tai_khoan"), sourceAccount.account);
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.PAY_ID_TEXT, "com.VCB:id/tvBooking_number"), payID);
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.MONEY_AMOUNT_TEXT, "com.VCB:id/tvAmount"), ticketPrice);
		
		log.info("TC_13_Step_29: Chon phuong thuc xac thuc SMS OTP");
		airTicket.clickToDynamicTextByID("com.VCB:id/tvptxt");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.SMS_OTP_TEXT);

		log.info("TC_13_Step_30: Click Tiep Tuc");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_13_Step_31: Nhap Ma OTP roi click Tiep tuc");
		airTicket.inputToDynamicOtp(driver, otp, Air_Text.BUTTON_CONTINUE_TEXT);
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_13_Step_32: Xac nhan hien thi man hinh thanh toan thanh cong");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.PAY_ID_TEXT, "com.VCB:id/tvContent"), payID);
		transactionID = airTicket.getAirTicketPriceInfo1Way(Air_Text.TRANSACTION_ID_TEXT, "com.VCB:id/tvContent");

		log.info("TC_13_Step_33: Clich nut Home");
		airTicket.clickToDynamicIcon("com.VCB:id/ivHome");
		
	}

	@Test
	public void TC_14_DatVeMayBayQuocTeKhuHoi_ThanhToanSau_1Nguoi_BaoCaoGiaoDich() {

		log.info("TC_14_Step_01: Mo tab Menu");
		homePage.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_5");

		log.info("TC_14_Step_02: Mo sub-menu 'Bao cao giao dich");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_14_Step_03: An vao Dropdown 'Tat ca cac loai giao dich");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectTransType");

		log.info("TC_14_Step_04: Chon 'Thanh toan ve may bay'");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.AIR_TICKET);

		log.info("TC_14_Step_05: An vao Dropdown 'Chon tai khoan/the");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_14_Step_06: Chon tai khoan vua thuc hien giao dich");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_14_Step_07: An nut Tim kiem");
		transactionReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");

		log.info("TC_14_Step_08: An vao giao dich dau tien");
		transactionReport.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_14_Step_09: Xac nhan hien thi Title 'Chi tiet giao dich'");
		verifyEquals(transactionReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), ReportTitle.TRANSACTION_DETAIL);

		log.info("TC_14_Step_10: Xac nhan hien thi dung ma giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.TRANSACTION_NUMBER), transactionID);

		log.info("TC_14_Step_11: Xac nhan hien thi so tai khoan giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.ACCOUNT_CARD), sourceAccount.account);

		log.info("TC_14_Step_12: Xac nhan hien thi Ma Thanh toan");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.CODE_TRANSFER), payID);

		log.info("TC_14_Step_13: Xac nhan hien thi So tien phi");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.TRANSACTION_FEE), fee);

		log.info("TC_14_Step_14: Xac nhan hien thi Loai giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.TRANSACTION_TYPE), ReportTitle.AIR_TICKET);

		log.info("TC_14_Step_15: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_14_Step_16: An nut back ve man hinh menu");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
	
		log.info("TC_14_Step_17: Mo tab Home");
		homePage.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_1");
	}

	@Parameters({ "pass" })
	@Test
	public void TC_15_DatVeMayBayQuocTeKhuHoi_ThanhToanSau_1Nguoi_ThanhToanMK(String password) {

		log.info("TC_15_Step_01: Click Dat ve may bay");
		homePage.clickToDynamicIcon(driver, Air_Text.AIR_TICKET_TEXT);
	
		log.info("TC_15_Step_02: Click dat ve may bay quoc te ");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.INTERNATIONAL_AIR_TICKET_TEXT);

		log.info("TC_15_Step_03: Click Dong y ");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_AGREE_TEXT);

		log.info("TC_15_Step_04: Chon khu hoi ");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.TWO_WAYS_TEXT);

		log.info("TC_15_Step_05: Chon diem khoi hanh");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.DEPARTURE_PLACE_TEXT);
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.HAN_PLACE);

		log.info("TC_15_Step_06: Chon diem den");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.ARRIVAL_PLACE_TEXT);
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.BKK_PLACE);

		log.info("TC_15_Step_07: Chon ngay di / ngay ve");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.DEPARTURE_DATE_TEXT);
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(5), fifthDay);
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(9), ninthDay);
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONFIRM_TEXT);

		log.info("TC_15_Step_08: Tim chuyen bay");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_SEARCH_FLIGHT_TEXT);
		source = airTicket.getDynamicTextByID("com.VCB:id/tvTitle2Left");
		dest = airTicket.getDynamicTextByID("com.VCB:id/tvTitle2Right");
		sourceDetail = airTicket.getDynamicTextByID("com.VCB:id/tvDiemDi");
		destDetail = airTicket.getDynamicTextByID("com.VCB:id/tvDiemDen");
		time = airTicket.getDynamicTextByID("com.VCB:id/tvTitle2");
		
		log.info("TC_15_Step_09: Chon chuyen bay và dat ve");
		airTicket.clickToDynamicFlightTwoWays(0, "com.VCB:id/recycle_international", "com.VCB:id/tv_international_flightNo_chieudi", Air_Text.SUGGEST_FLIGHT_CODE_VN);
		
		flightCode01 = airTicket.getDynamicTextByID("com.VCB:id/tv_flightCode_External_chieudi");
		departureTime01 = getRawSplitStringIndex(airTicket.getDynamicTextByID("com.VCB:id/tv_time_External_chieudi"), "-", 0);
		arrivalTime01 = getRawSplitStringIndex(airTicket.getDynamicTextByID("com.VCB:id/tv_time_External_chieudi"), "-", 1);
		
		flightCode02 = airTicket.getDynamicTextByID("com.VCB:id/tv_flightCode_External_chieuve");
		departureTime02 = getRawSplitStringIndex(airTicket.getDynamicTextByID("com.VCB:id/tv_time_External_chieuve"), "-", 0);
		arrivalTime02 = getRawSplitStringIndex(airTicket.getDynamicTextByID("com.VCB:id/tv_time_External_chieuve"), "-", 1);
		
		ticketPrice = airTicket.getDynamicTextByID("com.VCB:id/tv_amount_flight_selected");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_ORDER_TICKET_TEXT);

		log.info("TC_15_Step 10: Dien Ten Nguoi lon ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_NAME, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_hoten");

		log.info("TC_15_Step 11: Chon gioi tinh nu");
		airTicket.checkToDynamicTextOrDropDownByLabel(Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/tv_NuContact");

		log.info("TC_15_Step 12: Dien Email  ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_EMAIL, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_email");

		log.info("TC_15_Step 13: Dien Phone ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_PHONE, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_phonenumber");

		log.info("TC_15_Step 14: Dien Noi dung");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.CONTENT, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_content");

		log.info("TC_15_Step 15: Click vao check box Dung lam thong tin hanh khach bay");
		airTicket.clickToDynamicCheckBoxByID("com.VCB:id/iv_check_active");

		log.info("TC_15_Step 16: Click Tiep Tuc");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_15_Step 17: Kiem tra man hinh xac nhan chuyen bay hien thi");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvTitle1"), Air_Text.FLIGHT_CONFIRM_TEXT);

		log.info("TC_15_Step 18_1: Kiem tra dia diem khoi hanh");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem1"), sourceDetail);
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem2"), source);

		log.info("TC_15_Step 18_2: Kiem tra dia diem den");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem3"), destDetail);
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem4"), dest);

		log.info("TC_15_Step 19_1: Kiem tra thong tin chieu di");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextLeft1"), Air_Text.DEPARTURE_WAY_TEXT);

		log.info("TC_15_Step 19_2: Kiem tra thong tin ma chieu di");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvFlightId"), flightCode01);

		log.info("TC_15_Step 19_3: Kiem tra thong tin chieu di : ngay di");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextLeft2"), getRawSplitStringIndex(time, "-", 0).trim());

		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextRight1"), getRawSplitStringIndex(time, "-", 0).trim());

		log.info("TC_15_Step 19_4: Kiem tra thong tin chieu di : gio di ");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextLeft3"), source+" " + departureTime01);

		log.info("TC_15_Step 19_5: Kiem tra thong tin chieu di : gio den");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextRight2"), dest+" " + arrivalTime01);

		log.info("TC_15_Step 19_6: Kiem trathông tin chieu di : thoi gian bay");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvDuration"), duration01);

		log.info("TC_15_Step 20_1: Kiem tra thong tin chieu ve");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextLeft1"), Air_Text.ARRIVAL_WAY_TEXT);

		log.info("TC_15_Step 20_2: Kiem tra thong tin ma chieu ve");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvFlightId"), flightCode02);

		log.info("TC_15_Step 20_3: Kiem tra thong tin chieu ve : ngay di");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextLeft2"), getRawSplitStringIndex(time, "-", 1).trim());

		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextRight1"), getRawSplitStringIndex(time, "-", 1).trim());

		log.info("TC_15_Step 20_4: Kiem tra thong tin chieu ve : gio di ");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextLeft3"), dest+" " + departureTime02);

		log.info("TC_15_Step 20_5: Kiem tra thong tin chieu ve : gio den");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextRight2"), source+" " + arrivalTime02);

		log.info("TC_15_Step 20_6: Kiem trathông tin chieu ve : thoi gian bay");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvDuration"), duration02);

		log.info("TC_15_Step 18: Kiem tra tong so tien hien thi dung");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.TOTAL_MONEY_TEXT, "com.VCB:id/tvText2"), ticketPrice);

		log.info("TC_15_Step_19: Click checkbox dong y dieu khoan dat ve");
		airTicket.clickToDynamicCheckBoxByID("com.VCB:id/checkBoxCondition");

		log.info("TC_15_Step_20: Click Thanh toan sau");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_PAY_LATER_TEXT);

		log.info("TC_15_Step_22: Hien thi man hinh Ket qua");
		payID = airTicket.getDynamicTextByID("com.VCB:id/tvBookCode");

		log.info("TC_15_Step_23: Ve man hinh ve may bay");
		airTicket.clickToDynamicIcon("com.VCB:id/ivTitleRight");

		log.info("TC_15_Step_24: Chon Thanh toan ve may bay");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.AIR_TICKET_PAY_TEXT);

		log.info("TC_15_Step_25: Nhap ma thanh toan");
		airTicket.inputToDynamicInputBoxById(payID, "com.VCB:id/edtBookCode");

		log.info("TC_15_Step_26: An tiep tuc");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_15_Step_27: Chọn tai khoan nguon");
		airTicket.clickToDynamicTextByID("com.VCB:id/number_account");
		sourceAccount = airTicket.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		log.info("TC_15_Step_22_3: Xac nhan ma thanh toan");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.PAY_ID_TEXT, "com.VCB:id/tvContent"), payID);
	
		log.info("TC_15_Step_22_4: Xac nhan chieu di bay ");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("4", Air_Text.FLIGHT_WAY_TEXT), source+" → "+dest);

		log.info("TC_15_Step_22_5: Xac nhan so hieu chuyen bay chieu di");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("5", Air_Text.FLIGHT_CODE_TEXT).replace("-", ""), flightCode01);

		log.info("TC_15_Step_22_6: Xac nhan thoi gian bay chieu di");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("6", Air_Text.FLIGHT_TIME_TEXT), departureTime01 + " - " + arrivalTime01);

		log.info("TC_15_Step_22_7: Xac nhan chieu ve bay ");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("9", Air_Text.FLIGHT_WAY_TEXT), dest+" → "+source);

		log.info("TC_15_Step_22_8: Xac nhan so hieu chuyen bay chieu ve");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("10", Air_Text.FLIGHT_CODE_TEXT).replace("-", ""), flightCode02);

		log.info("TC_15_Step_22_6: Xac nhan thoi gian bay chieu ve");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("11", Air_Text.FLIGHT_TIME_TEXT), departureTime02 + " - " + arrivalTime02);

		log.info("TC_15_Step_22_7: Xac nhan Tong tien thanh toan");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.TOTAL_PAY_TEXT, "com.VCB:id/tvContent"), ticketPrice);
	
		log.info("TC_15_Step_28: An tiep tuc");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_15_Step_24: Xac nhan giao dich");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.FROM_ACCOUNT_TEXT, "com.VCB:id/tvTu_tai_khoan"), sourceAccount.account);
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.PAY_ID_TEXT, "com.VCB:id/tvBooking_number"), payID);
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.MONEY_AMOUNT_TEXT, "com.VCB:id/tvAmount"), ticketPrice);
		
		log.info("TC_15_Step_29: Chon phuong thuc xac thuc MK");
		airTicket.clickToDynamicTextByID("com.VCB:id/tvptxt");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.PASSWORD_TEXT);

		log.info("TC_15_Step_30: Click Tiep Tuc");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_15_Step_31: Nhap Ma OTP roi click Tiep tuc");
		airTicket.inputToDynamicPopupPasswordInput(driver, password, Air_Text.BUTTON_CONTINUE_TEXT);
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_15_Step_32: Xac nhan hien thi man hinh thanh toan thanh cong");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.PAY_ID_TEXT, "com.VCB:id/tvContent"), payID);
		transactionID = airTicket.getAirTicketPriceInfo1Way(Air_Text.TRANSACTION_ID_TEXT, "com.VCB:id/tvContent");

		log.info("TC_15_Step_33: Clich nut Home");
		airTicket.clickToDynamicIcon("com.VCB:id/ivHome");
		
	}

	@Test
	public void TC_16_DatVeMayBayQuocTeKhuHoi_ThanhToanSau_1Nguoi_BaoCaoGiaoDich() {

		log.info("TC_16_Step_01: Mo tab Menu");
		homePage.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_5");

		log.info("TC_16_Step_02: Mo sub-menu 'Bao cao giao dich");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSACTION_REPORT);
	
		log.info("TC_16_Step_03: An vao Dropdown 'Tat ca cac loai giao dich");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectTransType");

		log.info("TC_16_Step_04: Chon 'Thanh toan ve may bay'");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.AIR_TICKET);

		log.info("TC_16_Step_05: An vao Dropdown 'Chon tai khoan/the");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_16_Step_06: Chon tai khoan vua thuc hien giao dich");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_16_Step_07: An nut Tim kiem");
		transactionReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");

		log.info("TC_16_Step_08: An vao giao dich dau tien");
		transactionReport.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_16_Step_09: Xac nhan hien thi Title 'Chi tiet giao dich'");
		verifyEquals(transactionReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), ReportTitle.TRANSACTION_DETAIL);

		log.info("TC_16_Step_10: Xac nhan hien thi dung ma giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.TRANSACTION_NUMBER), transactionID);

		log.info("TC_16_Step_11: Xac nhan hien thi so tai khoan giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.ACCOUNT_CARD), sourceAccount.account);

		log.info("TC_16_Step_12: Xac nhan hien thi Ma Thanh toan");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.CODE_TRANSFER), payID);

		log.info("TC_16_Step_13: Xac nhan hien thi So tien phi");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.TRANSACTION_FEE), fee);

		log.info("TC_16_Step_14: Xac nhan hien thi Loai giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.TRANSACTION_TYPE), ReportTitle.AIR_TICKET);

		log.info("TC_16_Step_15: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_16_Step_16: An nut back ve man hinh menu");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		
		log.info("TC_16_Step_17: Mo tab Home");
		homePage.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_1");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		service.stop();
	}

}
