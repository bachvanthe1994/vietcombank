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

public class DomesticAirTicketBooking_MainFlow01 extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransactionReportPageObject transactionReport;
	private DynamicAirTicketBookingObjects airTicket;
	
	SourceAccountModel sourceAccount = new SourceAccountModel();
	private String fifthDay = getForWardDay(3);
	private String ticketPrice, payID, transactionID, fee, sourceDetail, source, destDetail, dest, time, flightCode, departureTime, arrivalTime, duration;

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

		log.info("Before class: Dang nhap ");
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
		airTicket = PageFactoryManager.getDynamicAirTicketBooking(driver);
		transactionReport = PageFactoryManager.getTransactionReportPageObject(driver);
		homePage = PageFactoryManager.getHomePageObject(driver);

	}
// 	Done 
//	@Parameters({ "otp" })
//	@Test
	public void TC_01_DatVeMayBayNoiDiaMotChieuThanhCong_1Nguoi_ThanhToanOTP(String otp) {

		homePage.clickToDynamicIcon(driver, Air_Text.AIR_TICKET_TEXT);
	
		log.info("Before class: Click dat ve may bay noi dia ");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.DOMESTIC_AIR_TICKET_TEXT);

		log.info("Before class: Click Dong y ");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_AGREE_TEXT);

		log.info("Before class: Chon mot chieu ");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.ONE_WAY_TEXT);

		log.info("TC_01_Step_01: Chon diem khoi hanh");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.DEPARTURE_PLACE_TEXT);
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.HAN_PLACE);

		log.info("TC_01_Step_02: Chon diem den");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.ARRIVAL_PLACE_TEXT);
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.SGN_PLACE);

		log.info("TC_01_Step_03: Chon ngay di");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.DEPARTURE_DATE_TEXT);
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(3), fifthDay);
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONFIRM_TEXT);

		log.info("TC_01_Step_04: Tim chuyen bay");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_SEARCH_FLIGHT_TEXT);
		sourceDetail = airTicket.getDynamicTextByID("com.VCB:id/tv_sourceDetail");
		source = airTicket.getDynamicTextByID("com.VCB:id/tv_source");
		destDetail = airTicket.getDynamicTextByID("com.VCB:id/tv_DestDetail");
		dest = airTicket.getDynamicTextByID("com.VCB:id/tv_Dest");
		time = airTicket.getDynamicTextByID("com.VCB:id/tv_thu");

		log.info("TC_01_Step_05: Chon chuyen bay va dat ve");
		airTicket.clickToDynamicFlight2WayCode("com.VCB:id/recycler_view_one", "0");

		flightCode = airTicket.getDynamicFlightData("com.VCB:id/recycler_view_one", "0", "com.VCB:id/tv_flightNo");
		departureTime = airTicket.getDynamicFlightData("com.VCB:id/recycler_view_one", "0", "com.VCB:id/tv_time_deptTime");
		arrivalTime = airTicket.getDynamicFlightData("com.VCB:id/recycler_view_one", "0", "com.VCB:id/tv_time_deptTime_arrival");
		duration = airTicket.getDynamicFlightData("com.VCB:id/recycler_view_one", "0", "com.VCB:id/tv_timeduration");

		ticketPrice = airTicket.getDynamicTextByID("com.VCB:id/tv_amount_flight_selected");
		airTicket.clickToDynamicTextByID("com.VCB:id/btn_book");

		log.info("TC_01_Step 06: Dien Ten Nguoi lon ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_NAME, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_hoten");

		log.info("TC_01_Step 07: Chon gioi tinh nu");
		airTicket.checkToDynamicTextOrDropDownByLabel(Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/tv_NuContact");

		log.info("TC_01_Step 08: Dien Email  ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_EMAIL, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_email");

		log.info("TC_01_Step 09: Dien Phone ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_PHONE, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_phonenumber");

		log.info("TC_01_Step 10: Dien Noi dung");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.CONTENT, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_content");

		log.info("TC_01_Step 11: Dien ten nguoi lon 1 ");
		airTicket.inputToDynamicInputBoxByLabelAndIndex(DomesticAirTicketBooking_Data.validInput.ADULT_NAME, "com.VCB:id/recy_info_book", "0", "com.VCB:id/edt_hoten");

		log.info("TC_01_Step 12: Chon gioi tinh nu");
		airTicket.clickToDynamicTextOrDropDownByLabelAndIndex("com.VCB:id/recy_info_book", "0", "com.VCB:id/ll_NuNoiDia", "com.VCB:id/tv_NuNoiDia");

		log.info("TC_01_Step 13: Click Tiep Tuc");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_01_Step 14: Kiem tra man hinh xac nhan chuyen bay hien thi");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvTitle1"), Air_Text.FLIGHT_CONFIRM_TEXT);

		log.info("TC_01_Step 14_1: Kiem tra dia diem khoi hanh");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem1"), sourceDetail);
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem2"), source);

		log.info("TC_01_Step 14_2: Kiem tra dia diem den");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem3"), destDetail);
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem4"), dest);

		log.info("TC_01_Step 14_3: Kiem tra thong tin hanh trinh");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextLeft1"), Air_Text.JOURNEY_TEXT);

		log.info("TC_01_Step 14_4: Kiem tra thong tin ma may bay");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvFlightId"), flightCode);

		log.info("TC_01_Step 14_5: Kiem tra thong tin ngay di");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextLeft2"), time);

		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextRight1"), time);

		log.info("TC_01_Step 14_6: Kiem tra thong tin gio di ");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextLeft3"), source + " " + departureTime);

		log.info("TC_01_Step 14_7: Kiem tra thong tin gio den");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextRight2"), dest + " " + arrivalTime);

		log.info("TC_01_Step 14_8: Kiem trathông tin thoi gian bay");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvDuration"), duration);

		log.info("TC_01_Step 15: Kiem tra tong so tien hien thi dung");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.TOTAL_MONEY_TEXT, "com.VCB:id/tvText2"), ticketPrice);

		log.info("TC_01_Step_16: Click checkbox dong y dieu khoan dat ve");
		airTicket.clickToDynamicCheckBoxByID("com.VCB:id/checkBoxCondition");

		log.info("TC_01_Step_17: Click Thanh toan");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_PAY_TEXT);

		log.info("TC_01_Step_18_1: Chọn tai khoan nguon");
		airTicket.scrollUpToTextView(Air_Text.SURPLUS_TEXT);
		airTicket.clickToDynamicTextByID("com.VCB:id/number_account");
		sourceAccount = airTicket.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);
		payID = airTicket.getAirTicketPriceInfo1Way(Air_Text.PAY_ID_TEXT, "com.VCB:id/tvContent");

		log.info("TC_01_Step_18_2: Xac nhan chieu di ");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("4", Air_Text.FLIGHT_WAY_TEXT), sourceDetail + " → " + destDetail);

		log.info("TC_01_Step_18_3: Xac nhan so hieu chuyen bay chieu di");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("5", Air_Text.FLIGHT_CODE_TEXT).replace("-", ""), flightCode);

		log.info("TC_01_Step_18_4: Xac nhan thoi gian bay chieu di");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("6", Air_Text.FLIGHT_TIME_TEXT), departureTime + " - " + arrivalTime);

		log.info("TC_01_Step_19: Click Tiep tuc");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.TOTAL_PAY_TEXT, "com.VCB:id/tvContent"), ticketPrice);

		log.info("TC_01_Step 20: Click Tiep Tuc");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_01_Step_21: Xac nhan giao dich");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.FROM_ACCOUNT_TEXT, "com.VCB:id/tvTu_tai_khoan"), sourceAccount.account);
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.PAY_ID_TEXT, "com.VCB:id/tvBooking_number"), payID);
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.MONEY_AMOUNT_TEXT, "com.VCB:id/tvAmount"), ticketPrice);

		log.info("TC_01_Step_22: Chon phuong thuc xac thuc SMS OTP");
		airTicket.clickToDynamicTextByID("com.VCB:id/tvptxt");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.SMS_OTP_TEXT);
		fee = airTicket.getAirTicketPriceInfo1Way(Air_Text.FEE_VALUE_TEXT, "com.VCB:id/tvPhiGiaoDich");

		log.info("TC_01_Step_23: Click Tiep Tuc");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_01_Step_24: Nhap Ma OTP roi click Tiep tuc");
		airTicket.inputToDynamicOtp(driver, otp, Air_Text.BUTTON_CONTINUE_TEXT);
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_01_Step_25: Xac nhan hien thi man hinh thanh toan thanh cong");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.PAY_ID_TEXT, "com.VCB:id/tvContent"), payID);
		transactionID = airTicket.getAirTicketPriceInfo1Way(Air_Text.TRANSACTION_ID_TEXT, "com.VCB:id/tvContent");

		log.info("TC_01_Step_26: Clich nut Home");
		airTicket.clickToDynamicIcon("com.VCB:id/ivHome");
		
	}
// 	Done 
//	@Test
	public void TC_02_XacNhanDatVeMayBayNoiDiaMotChieuThanhCong_1Nguoi_TrongBaoCaoGiaoDich() {

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

		log.info("TC_02_Step_15: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_02_Step_16: An nut back ve man hinh menu");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		
		log.info("TC_02_Step_17: Mo tab Home");
		homePage.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_1");
	}

//	@Parameters({ "pass" })
//	@Test
	public void TC_03_DatVeMayBayNoiDiaMotChieuThanhCong_1Nguoi_ThanhToanMK(String password) {

		homePage.clickToDynamicIcon(driver, Air_Text.AIR_TICKET_TEXT);
		
		log.info("Before class: Click dat ve may bay noi dia ");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.DOMESTIC_AIR_TICKET_TEXT);

		log.info("Before class: Click Dong y ");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_AGREE_TEXT);

		log.info("Before class: Chon mot chieu ");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.ONE_WAY_TEXT);

		log.info("TC_03_Step_01: Chon diem khoi hanh");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.DEPARTURE_PLACE_TEXT);
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.HAN_PLACE);

		log.info("TC_03_Step_02: Chon diem den");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.ARRIVAL_PLACE_TEXT);
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.SGN_PLACE);

		log.info("TC_03_Step_03: Chon ngay di");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.DEPARTURE_DATE_TEXT);
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(3), fifthDay);
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONFIRM_TEXT);

		log.info("TC_03_Step_04: Tim chuyen bay");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_SEARCH_FLIGHT_TEXT);
		sourceDetail = airTicket.getDynamicTextByID("com.VCB:id/tv_sourceDetail");
		source = airTicket.getDynamicTextByID("com.VCB:id/tv_source");
		destDetail = airTicket.getDynamicTextByID("com.VCB:id/tv_DestDetail");
		dest = airTicket.getDynamicTextByID("com.VCB:id/tv_Dest");
		time = airTicket.getDynamicTextByID("com.VCB:id/tv_thu");

		log.info("TC_03_Step_05: Chon chuyen bay va dat ve");
		airTicket.clickToDynamicFlight2WayCode("com.VCB:id/recycler_view_one", "0");

		flightCode = airTicket.getDynamicFlightData("com.VCB:id/recycler_view_one", "0", "com.VCB:id/tv_flightNo");
		departureTime = airTicket.getDynamicFlightData("com.VCB:id/recycler_view_one", "0", "com.VCB:id/tv_time_deptTime");
		arrivalTime = airTicket.getDynamicFlightData("com.VCB:id/recycler_view_one", "0", "com.VCB:id/tv_time_deptTime_arrival");
		duration = airTicket.getDynamicFlightData("com.VCB:id/recycler_view_one", "0", "com.VCB:id/tv_timeduration");

		ticketPrice = airTicket.getDynamicTextByID("com.VCB:id/tv_amount_flight_selected");
		airTicket.clickToDynamicTextByID("com.VCB:id/btn_book");

		log.info("TC_03_Step 06: Dien Ten Nguoi lon ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_NAME, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_hoten");

		log.info("TC_03_Step 07: Chon gioi tinh nu");
		airTicket.checkToDynamicTextOrDropDownByLabel(Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/tv_NuContact");

		log.info("TC_03_Step 08: Dien Email  ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_EMAIL, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_email");

		log.info("TC_03_Step 09: Dien Phone ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_PHONE, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_phonenumber");

		log.info("TC_03_Step 10: Dien Noi dung");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.CONTENT, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_content");

		log.info("TC_03_Step 11: Dien ten nguoi lon 1 ");
		airTicket.inputToDynamicInputBoxByLabelAndIndex(DomesticAirTicketBooking_Data.validInput.ADULT_NAME, "com.VCB:id/recy_info_book", "0", "com.VCB:id/edt_hoten");

		log.info("TC_03_Step 12: Chon gioi tinh nu");
		airTicket.clickToDynamicTextOrDropDownByLabelAndIndex("com.VCB:id/recy_info_book", "0", "com.VCB:id/ll_NuNoiDia", "com.VCB:id/tv_NuNoiDia");

		log.info("TC_03_Step 13: Click Tiep Tuc");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_03_Step 14: Kiem tra man hinh xac nhan chuyen bay hien thi");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvTitle1"), Air_Text.FLIGHT_CONFIRM_TEXT);

		log.info("TC_03_Step 14_1: Kiem tra dia diem khoi hanh");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem1"), sourceDetail);
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem2"), source);

		log.info("TC_03_Step 14_2: Kiem tra dia diem den");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem3"), destDetail);
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem4"), dest);

		log.info("TC_03_Step 14_3: Kiem tra thong tin hanh trinh");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextLeft1"), Air_Text.JOURNEY_TEXT);

		log.info("TC_03_Step 14_4: Kiem tra thong tin ma may bay");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvFlightId"), flightCode);

		log.info("TC_03_Step 14_5: Kiem tra thong tin ngay di");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextLeft2"), time);

		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextRight1"), time);

		log.info("TC_03_Step 14_6: Kiem tra thong tin gio di ");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextLeft3"), source + " " + departureTime);

		log.info("TC_03_Step 14_7: Kiem tra thong tin gio den");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextRight2"), dest + " " + arrivalTime);

		log.info("TC_03_Step 14_8: Kiem trathông tin thoi gian bay");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvDuration"), duration);

		log.info("TC_03_Step 15: Kiem tra tong so tien hien thi dung");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.TOTAL_MONEY_TEXT, "com.VCB:id/tvText2"), ticketPrice);

		log.info("TC_03_Step_16: Click checkbox dong y dieu khoan dat ve");
		airTicket.clickToDynamicCheckBoxByID("com.VCB:id/checkBoxCondition");

		log.info("TC_03_Step_17: Click Thanh toan");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_PAY_TEXT);

		log.info("TC_03_Step_18_1: Chọn tai khoan nguon");
		airTicket.scrollUpToTextView(Air_Text.SURPLUS_TEXT);
		airTicket.clickToDynamicTextByID("com.VCB:id/number_account");
		sourceAccount = airTicket.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);
		payID = airTicket.getAirTicketPriceInfo1Way(Air_Text.PAY_ID_TEXT, "com.VCB:id/tvContent");

		log.info("TC_03_Step_18_2: Xac nhan chieu di ");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("4", Air_Text.FLIGHT_WAY_TEXT), sourceDetail + " → " + destDetail);

		log.info("TC_03_Step_18_3: Xac nhan so hieu chuyen bay chieu di");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("5", Air_Text.FLIGHT_CODE_TEXT).replace("-", ""), flightCode);

		log.info("TC_03_Step_18_4: Xac nhan thoi gian bay chieu di");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("6", Air_Text.FLIGHT_TIME_TEXT), departureTime + " - " + arrivalTime);

		log.info("TC_03_Step_19: Click Tiep tuc");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.TOTAL_PAY_TEXT, "com.VCB:id/tvContent"), ticketPrice);

		log.info("TC_03_Step 20: Click Tiep Tuc");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_03_Step_21: Xac nhan giao dich");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.FROM_ACCOUNT_TEXT, "com.VCB:id/tvTu_tai_khoan"), sourceAccount.account);
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.PAY_ID_TEXT, "com.VCB:id/tvBooking_number"), payID);
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.MONEY_AMOUNT_TEXT, "com.VCB:id/tvAmount"), ticketPrice);

		log.info("TC_03_Step_22: Chon phuong thuc xac thuc Mat khau");
		airTicket.clickToDynamicTextByID("com.VCB:id/tvptxt");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.PASSWORD_TEXT);
		fee = airTicket.getAirTicketPriceInfo1Way(Air_Text.FEE_VALUE_TEXT, "com.VCB:id/tvPhiGiaoDich");

		log.info("TC_03_Step_23: Click Tiep Tuc");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_03_Step_24: Nhap Ma OTP roi click Tiep tuc");
		airTicket.inputToDynamicPopupPasswordInput(driver, password, Air_Text.BUTTON_CONTINUE_TEXT);
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_03_Step_25: Xac nhan hien thi man hinh thanh toan thanh cong");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.PAY_ID_TEXT, "com.VCB:id/tvContent"), payID);
		transactionID = airTicket.getAirTicketPriceInfo1Way(Air_Text.TRANSACTION_ID_TEXT, "com.VCB:id/tvContent");

		log.info("TC_03_Step_26: Clich nut Home");
		airTicket.clickToDynamicIcon("com.VCB:id/ivHome");
		
	}

//	@Test
	public void TC_04_XacNhanDatVeMayBayNoiDiaMotChieuThanhCong_1Nguoi_TrongBaoCaoGiaoDich() {

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

		log.info("TC_04_Step_15: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_04_Step_16: An nut back ve man hinh menu");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
	
		log.info("TC_04_Step_17: Mo tab Home");
		homePage.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_1");
	}

	@Parameters({ "otp" })
	@Test
	public void TC_05_DatVeMayBayQuocTeMotChieuThanhCong_1Nguoi_ThanhToanOTP(String otp) {

		log.info("TC_05_Step_01: Click dat ve may bay ");
		homePage.clickToDynamicIcon(driver, Air_Text.AIR_TICKET_TEXT);
	
		log.info("TC_05_Step_02: Click dat ve may bay quoc te ");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.INTERNATIONAL_AIR_TICKET_TEXT);

		log.info("TC_05_Step_03: Click Dong y ");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_AGREE_TEXT);

		log.info("TC_05_Step_04: Chon mot chieu ");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.ONE_WAY_TEXT);

		log.info("TC_05_Step_05: Chon diem khoi hanh");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.DEPARTURE_PLACE_TEXT);
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.HAN_PLACE);

		log.info("TC_05_Step_06: Chon diem den");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.ARRIVAL_PLACE_TEXT);
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.BKK_PLACE);

		log.info("TC_05_Step_07: Chon ngay di");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.DEPARTURE_DATE_TEXT);
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(3), fifthDay);
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONFIRM_TEXT);

		log.info("TC_05_Step_08: Tim chuyen bay");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_SEARCH_FLIGHT_TEXT);
		sourceDetail = airTicket.getDynamicTextByID("com.VCB:id/tv_sourceDetail");
		source = airTicket.getDynamicTextByID("com.VCB:id/tv_source");
		destDetail = airTicket.getDynamicTextByID("com.VCB:id/tv_DestDetail");
		dest = airTicket.getDynamicTextByID("com.VCB:id/tv_Dest");
		time = airTicket.getDynamicTextByID("com.VCB:id/tv_thu");

		log.info("TC_05_Step_09: Chon chuyen bay va dat ve");
		airTicket.clickToDynamicFlight2WayCode("com.VCB:id/recycler_view_one", "0");

		flightCode = airTicket.getDynamicFlightData("com.VCB:id/recycler_view_one", "0", "com.VCB:id/tv_flightNo");
		departureTime = airTicket.getDynamicFlightData("com.VCB:id/recycler_view_one", "0", "com.VCB:id/tv_time_deptTime");
		arrivalTime = airTicket.getDynamicFlightData("com.VCB:id/recycler_view_one", "0", "com.VCB:id/tv_time_deptTime_arrival");
		duration = airTicket.getDynamicFlightData("com.VCB:id/recycler_view_one", "0", "com.VCB:id/tv_timeduration");
		ticketPrice = airTicket.getDynamicTextByID("com.VCB:id/tv_amount_flight_selected");
		airTicket.clickToDynamicTextByID("com.VCB:id/btn_book");

		log.info("TC_05_Step 10: Dien Ten Nguoi lon ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_NAME, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_hoten");

		log.info("TC_05_Step 11: Chon gioi tinh nu");
		airTicket.checkToDynamicTextOrDropDownByLabel(Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/tv_Nu");

		log.info("TC_05_Step 12: Dien Email  ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_EMAIL, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_email");

		log.info("TC_05_Step 13: Dien Phone ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_PHONE, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_phonenumber");

		log.info("TC_05_Step 14: Dien Noi dung");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.CONTENT, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_content");

		log.info("TC_05_Step 15: Click checkbox dung lam thong tin hanh khach bay");
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

		log.info("TC_05_Step 18_3: Kiem tra thong tin hanh trinh");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextLeft1"), Air_Text.JOURNEY_TEXT);

		log.info("TC_05_Step 18_4: Kiem tra thong tin ma may bay");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvFlightId"), flightCode);

		log.info("TC_05_Step 18_5: Kiem tra thong tin ngay di");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextLeft2"), time);

		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextRight1"), time);

		log.info("TC_05_Step 18_6: Kiem tra thong tin gio di ");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextLeft3"), source + " " + departureTime);

		log.info("TC_05_Step 18_7: Kiem tra thong tin gio den");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextRight2"), dest + " " + arrivalTime);

		log.info("TC_05_Step 18_8: Kiem trathông tin thoi gian bay");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvDuration"), duration);

		log.info("TC_05_Step 18_9: Kiem tra tong so tien hien thi dung");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.TOTAL_MONEY_TEXT, "com.VCB:id/tvText2"), ticketPrice);

		log.info("TC_05_Step_19: Click checkbox dong y dieu khoan dat ve");
		airTicket.clickToDynamicCheckBoxByID("com.VCB:id/checkBoxCondition");

		log.info("TC_05_Step_20: Click Thanh toan");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_PAY_TEXT);

		log.info("TC_05_Step_21: Chọn tai khoan nguon");
		airTicket.scrollUpToTextView(Air_Text.SURPLUS_TEXT);
		airTicket.clickToDynamicTextByID("com.VCB:id/number_account");
		sourceAccount = airTicket.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);
		payID = airTicket.getAirTicketPriceInfo1Way(Air_Text.PAY_ID_TEXT, "com.VCB:id/tvContent");

		log.info("TC_05_Step_22_1: Xac nhan chieu di ");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("4", Air_Text.FLIGHT_WAY_TEXT), sourceDetail + " → " + destDetail);

		log.info("TC_05_Step_22_2: Xac nhan so hieu chuyen bay chieu di");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("5", Air_Text.FLIGHT_CODE_TEXT).replace("-", ""), flightCode);

		log.info("TC_05_Step_22_3: Xac nhan thoi gian bay chieu di");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("6", Air_Text.FLIGHT_TIME_TEXT), departureTime + " - " + arrivalTime);

		log.info("TC_05_Step_22_4: Xac nhan tong tien ve may bay");
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
	public void TC_06_DatVeMayBayQuocTeMotChieuThanhCong_1Nguoi_BaoCaoGiaoDich() {

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

		log.info("TC_06_Step_15: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_06_Step_16: An nut back ve man hinh menu");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
	
		log.info("TC_06_Step_17: Mo tab Home");
		homePage.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_1");
	}

	@Parameters({ "pass" })
	@Test
	public void TC_07_DatVeMayBayQuocTeMotChieuThanhCong_1Nguoi_ThanhToanMK(String password) {

		log.info("TC_07_Step_01: Click dat ve may bay ");
		homePage.clickToDynamicIcon(driver, Air_Text.AIR_TICKET_TEXT);
	
		log.info("TC_07_Step_02: Click dat ve may bay quoc te ");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.INTERNATIONAL_AIR_TICKET_TEXT);

		log.info("TC_07_Step_03: Click Dong y ");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_AGREE_TEXT);

		log.info("TC_07_Step_04: Chon mot chieu ");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.ONE_WAY_TEXT);

		log.info("TC_07_Step_05: Chon diem khoi hanh");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.DEPARTURE_PLACE_TEXT);
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.HAN_PLACE);

		log.info("TC_07_Step_06: Chon diem den");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.ARRIVAL_PLACE_TEXT);
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.BKK_PLACE);

		log.info("TC_07_Step_07: Chon ngay di");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.DEPARTURE_DATE_TEXT);
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(3), fifthDay);
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONFIRM_TEXT);

		log.info("TC_07_Step_08: Tim chuyen bay");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_SEARCH_FLIGHT_TEXT);
		sourceDetail = airTicket.getDynamicTextByID("com.VCB:id/tv_sourceDetail");
		source = airTicket.getDynamicTextByID("com.VCB:id/tv_source");
		destDetail = airTicket.getDynamicTextByID("com.VCB:id/tv_DestDetail");
		dest = airTicket.getDynamicTextByID("com.VCB:id/tv_Dest");
		time = airTicket.getDynamicTextByID("com.VCB:id/tv_thu");

		log.info("TC_07_Step_09: Chon chuyen bay va dat ve");
		airTicket.clickToDynamicFlight2WayCode("com.VCB:id/recycler_view_one", "0");

		flightCode = airTicket.getDynamicFlightData("com.VCB:id/recycler_view_one", "0", "com.VCB:id/tv_flightNo");
		departureTime = airTicket.getDynamicFlightData("com.VCB:id/recycler_view_one", "0", "com.VCB:id/tv_time_deptTime");
		arrivalTime = airTicket.getDynamicFlightData("com.VCB:id/recycler_view_one", "0", "com.VCB:id/tv_time_deptTime_arrival");
		duration = airTicket.getDynamicFlightData("com.VCB:id/recycler_view_one", "0", "com.VCB:id/tv_timeduration");
		ticketPrice = airTicket.getDynamicTextByID("com.VCB:id/tv_amount_flight_selected");
		airTicket.clickToDynamicTextByID("com.VCB:id/btn_book");

		log.info("TC_07_Step 10: Dien Ten Nguoi lon ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_NAME, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_hoten");

		log.info("TC_07_Step 11: Chon gioi tinh nu");
		airTicket.checkToDynamicTextOrDropDownByLabel(Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/tv_Nu");

		log.info("TC_07_Step 12: Dien Email  ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_EMAIL, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_email");

		log.info("TC_07_Step 13: Dien Phone ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_PHONE, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_phonenumber");

		log.info("TC_07_Step 14: Dien Noi dung");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.CONTENT, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_content");

		log.info("TC_07_Step 15: Click checkbox dung lam thong tin hanh khach bay");
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

		log.info("TC_07_Step 18_3: Kiem tra thong tin hanh trinh");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextLeft1"), Air_Text.JOURNEY_TEXT);

		log.info("TC_07_Step 18_4: Kiem tra thong tin ma may bay");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvFlightId"), flightCode);

		log.info("TC_07_Step 18_5: Kiem tra thong tin ngay di");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextLeft2"), time);

		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextRight1"), time);

		log.info("TC_07_Step 18_6: Kiem tra thong tin gio di ");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextLeft3"), source + " " + departureTime);

		log.info("TC_07_Step 18_7: Kiem tra thong tin gio den");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextRight2"), dest + " " + arrivalTime);

		log.info("TC_07_Step 18_8: Kiem trathông tin thoi gian bay");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvDuration"), duration);

		log.info("TC_07_Step 18_9: Kiem tra tong so tien hien thi dung");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.TOTAL_MONEY_TEXT, "com.VCB:id/tvText2"), ticketPrice);

		log.info("TC_07_Step_19: Click checkbox dong y dieu khoan dat ve");
		airTicket.clickToDynamicCheckBoxByID("com.VCB:id/checkBoxCondition");

		log.info("TC_07_Step_20: Click Thanh toan");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_PAY_TEXT);

		log.info("TC_07_Step_21: Chọn tai khoan nguon");
		airTicket.scrollUpToTextView(Air_Text.SURPLUS_TEXT);
		airTicket.clickToDynamicTextByID("com.VCB:id/number_account");
		sourceAccount = airTicket.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);
		payID = airTicket.getAirTicketPriceInfo1Way(Air_Text.PAY_ID_TEXT, "com.VCB:id/tvContent");

		log.info("TC_07_Step_22_1: Xac nhan chieu di ");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("4", Air_Text.FLIGHT_WAY_TEXT), sourceDetail + " → " + destDetail);

		log.info("TC_07_Step_22_2: Xac nhan so hieu chuyen bay chieu di");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("5", Air_Text.FLIGHT_CODE_TEXT).replace("-", ""), flightCode);

		log.info("TC_07_Step_22_3: Xac nhan thoi gian bay chieu di");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("6", Air_Text.FLIGHT_TIME_TEXT), departureTime + " - " + arrivalTime);

		log.info("TC_07_Step_22_4: Xac nhan tong tien ve may bay");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.TOTAL_PAY_TEXT, "com.VCB:id/tvContent"), ticketPrice);

		log.info("TC_07_Step 23: Click Tiep Tuc");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_07_Step_24: Xac nhan giao dich");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.FROM_ACCOUNT_TEXT, "com.VCB:id/tvTu_tai_khoan"), sourceAccount.account);
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.PAY_ID_TEXT, "com.VCB:id/tvBooking_number"), payID);
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.MONEY_AMOUNT_TEXT, "com.VCB:id/tvAmount"), ticketPrice);

		log.info("TC_07_Step_25: Chon phuong thuc xac thuc Mat khau");
		airTicket.clickToDynamicTextByID("com.VCB:id/tvptxt");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.PASSWORD_TEXT);
		fee = airTicket.getAirTicketPriceInfo1Way(Air_Text.FEE_VALUE_TEXT, "com.VCB:id/tvPhiGiaoDich");

		log.info("TC_07_Step_26: Click Tiep Tuc");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_07_Step_27: Nhap MK roi click Tiep tuc");
		airTicket.inputToDynamicPopupPasswordInput(driver, password, Air_Text.BUTTON_CONTINUE_TEXT);
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_07_Step_28: Xac nhan hien thi man hinh thanh toan thanh cong");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.PAY_ID_TEXT, "com.VCB:id/tvContent"), payID);
		transactionID = airTicket.getAirTicketPriceInfo1Way(Air_Text.TRANSACTION_ID_TEXT, "com.VCB:id/tvContent");

		log.info("TC_07_Step_29: Clich nut Home");
		airTicket.clickToDynamicIcon("com.VCB:id/ivHome");
		
	}

	@Test
	public void TC_08_DatVeMayBayQuocTeMotChieuThanhCong_1Nguoi_BaoCaoGiaoDich() {

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

		log.info("TC_08_Step_15: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_08_Step_16: An nut back ve man hinh menu");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
	
		log.info("TC_08_Step_17: Mo tab Home");
		homePage.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_1");
	}
//  Done 
//	@Parameters({ "otp" })
//	@Test
	public void TC_09_DatVeMayBayNoiDiaMotChieu_ThanhToanSau_1Nguoi_ThanhToanOTP(String otp) {

		log.info("TC_09_Step_01: Click vao phan Dat ve may bay");
		homePage.clickToDynamicIcon(driver, Air_Text.AIR_TICKET_TEXT);
	
		log.info("TC_09_Step_02: Click dat ve may bay noi dia ");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.DOMESTIC_AIR_TICKET_TEXT);

		log.info("TC_09_Step_03: Click Dong y ");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_AGREE_TEXT);

		log.info("TC_09_Step_04: Chon mot chieu ");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.ONE_WAY_TEXT);

		log.info("TC_09_Step_05: Chon diem khoi hanh");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.DEPARTURE_PLACE_TEXT);
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.HAN_PLACE);

		log.info("TC_09_Step_06: Chon diem den");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.ARRIVAL_PLACE_TEXT);
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.SGN_PLACE);

		log.info("TC_09_Step_07: Chon ngay di");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.DEPARTURE_DATE_TEXT);
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(3), fifthDay);
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONFIRM_TEXT);

		log.info("TC_09_Step_08: Tim chuyen bay");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_SEARCH_FLIGHT_TEXT);

		log.info("TC_09_Step_09: Chon chuyen bay va dat ve");
		airTicket.clickToDynamicFlight(0, Air_Text.SUGGEST_FLIGHT_CODE_BL);
		ticketPrice = airTicket.getDynamicTextByID("com.VCB:id/tv_amount_flight_selected");
		airTicket.clickToDynamicTextByID("com.VCB:id/btn_book");

		log.info("TC_09_Step_10: Dien Ten Nguoi lon ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_NAME, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_hoten");

		log.info("TC_09_Step_11: Chon gioi tinh nu");
		airTicket.checkToDynamicTextOrDropDownByLabel(Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/tv_NuContact");

		log.info("TC_09_Step_12: Dien Email  ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_EMAIL, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_email");

		log.info("TC_09_Step_13: Dien Phone ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_PHONE, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_phonenumber");

		log.info("TC_09_Step_14: Dien Noi dung");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.CONTENT, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_content");

		log.info("TC_09_Step_15: Dien ten nguoi lon 1 ");
		airTicket.inputToDynamicInputBoxByLabelAndIndex(DomesticAirTicketBooking_Data.validInput.ADULT_NAME, "com.VCB:id/recy_info_book", "0", "com.VCB:id/edt_hoten");

		log.info("TC_09_Step_16: Chon gioi tinh nu");
		airTicket.clickToDynamicTextOrDropDownByLabelAndIndex("com.VCB:id/recy_info_book", "0", "com.VCB:id/ll_NuNoiDia", "com.VCB:id/tv_NuNoiDia");

		log.info("TC_09_Step_17: Click Tiep Tuc");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_09_Step_18: Kiem tra man hinh xac nhan chuyen bay hien thi");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvTitle1"), Air_Text.FLIGHT_CONFIRM_TEXT);

		log.info("TC_09_Step_19: Kiem tra tong so tien hien thi dung");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.TOTAL_MONEY_TEXT, "com.VCB:id/tvText2"), ticketPrice);

		log.info("TC_09_Step_20: Click checkbox dong y dieu khoan dat ve");
		airTicket.clickToDynamicCheckBoxByID("com.VCB:id/checkBoxCondition");

		log.info("TC_09_Step_21: Click Thanh toan sau");
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

		log.info("TC_09_Step_27: Chọn tai khoan nguon");
		airTicket.scrollUpToTextView(Air_Text.SURPLUS_TEXT);
		airTicket.clickToDynamicTextByID("com.VCB:id/number_account");
		sourceAccount = airTicket.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		log.info("TC_09_Step_28: An tiep tuc");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_09_Step_29: Chon phuong thuc xac thuc SMS OTP");
		airTicket.clickToDynamicTextByID("com.VCB:id/tvptxt");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.SMS_OTP_TEXT);
		fee = airTicket.getAirTicketPriceInfo1Way(Air_Text.FEE_VALUE_TEXT, "com.VCB:id/tvPhiGiaoDich");

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
//	Done 
//	@Test
	public void TC_10_DatVeMayBayNoiDiaMotChieu_ThanhToanSau_1Nguoi_BaoCaoGiaoDich() {

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
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver,sourceAccount.account);

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

		log.info("TC_10_Step_15: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_10_Step_16: An nut back ve man hinh menu");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		
		log.info("TC_10_Step_17: Mo tab Home");
		homePage.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_1");
	}

//	@Parameters({ "pass" })
//	@Test
	public void TC_11_DatVeMayBayNoiDiaMotChieu_ThanhToanSau_1Nguoi_ThanhToanMK(String password) {

		log.info("TC_11_Step_01: Click vao phan Dat ve may bay");
		homePage.clickToDynamicIcon(driver, Air_Text.AIR_TICKET_TEXT);
	
		log.info("TC_11_Step_02: Click dat ve may bay noi dia ");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.DOMESTIC_AIR_TICKET_TEXT);

		log.info("TC_11_Step_03: Click Dong y ");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_AGREE_TEXT);

		log.info("TC_11_Step_04: Chon mot chieu ");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.ONE_WAY_TEXT);

		log.info("TC_11_Step_05: Chon diem khoi hanh");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.DEPARTURE_PLACE_TEXT);
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.HAN_PLACE);

		log.info("TC_11_Step_06: Chon diem den");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.ARRIVAL_PLACE_TEXT);
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.SGN_PLACE);

		log.info("TC_11_Step_07: Chon ngay di");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.DEPARTURE_DATE_TEXT);
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(3), fifthDay);
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONFIRM_TEXT);

		log.info("TC_11_Step_08: Tim chuyen bay");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_SEARCH_FLIGHT_TEXT);

		log.info("TC_11_Step_09: Chon chuyen bay va dat ve");
		airTicket.clickToDynamicFlight(0, Air_Text.SUGGEST_FLIGHT_CODE_BL);
		ticketPrice = airTicket.getDynamicTextByID("com.VCB:id/tv_amount_flight_selected");
		airTicket.clickToDynamicTextByID("com.VCB:id/btn_book");

		log.info("TC_11_Step_10: Dien Ten Nguoi lon ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_NAME, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_hoten");

		log.info("TC_11_Step_11: Chon gioi tinh nu");
		airTicket.checkToDynamicTextOrDropDownByLabel(Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/tv_NuContact");

		log.info("TC_11_Step_12: Dien Email  ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_EMAIL, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_email");

		log.info("TC_11_Step_13: Dien Phone ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_PHONE, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_phonenumber");

		log.info("TC_11_Step_14: Dien Noi dung");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.CONTENT, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_content");

		log.info("TC_11_Step_15: Dien ten nguoi lon 1 ");
		airTicket.inputToDynamicInputBoxByLabelAndIndex(DomesticAirTicketBooking_Data.validInput.ADULT_NAME, "com.VCB:id/recy_info_book", "0", "com.VCB:id/edt_hoten");

		log.info("TC_11_Step_16: Chon gioi tinh nu");
		airTicket.clickToDynamicTextOrDropDownByLabelAndIndex("com.VCB:id/recy_info_book", "0", "com.VCB:id/ll_NuNoiDia", "com.VCB:id/tv_NuNoiDia");

		log.info("TC_11_Step_17: Click Tiep Tuc");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_11_Step_18: Kiem tra man hinh xac nhan chuyen bay hien thi");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvTitle1"), Air_Text.FLIGHT_CONFIRM_TEXT);

		log.info("TC_11_Step_19: Kiem tra tong so tien hien thi dung");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.TOTAL_MONEY_TEXT, "com.VCB:id/tvText2"), ticketPrice);

		log.info("TC_11_Step_20: Click checkbox dong y dieu khoan dat ve");
		airTicket.clickToDynamicCheckBoxByID("com.VCB:id/checkBoxCondition");

		log.info("TC_11_Step_21: Click Thanh toan sau");
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

		log.info("TC_11_Step_27: Chọn tai khoan nguon");
		airTicket.scrollUpToTextView(Air_Text.SURPLUS_TEXT);
		airTicket.clickToDynamicTextByID("com.VCB:id/number_account");
		sourceAccount = airTicket.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		log.info("TC_11_Step_28: An tiep tuc");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_11_Step_29: Chon phuong thuc xac thuc MK");
		airTicket.clickToDynamicTextByID("com.VCB:id/tvptxt");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.PASSWORD_TEXT);
		fee = airTicket.getAirTicketPriceInfo1Way(Air_Text.FEE_VALUE_TEXT, "com.VCB:id/tvPhiGiaoDich");

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

//	@Test
	public void TC_12_DatVeMayBayNoiDiaMotChieu_ThanhToanSau_1Nguoi_BaoCaoGiaoDich() {

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

		log.info("TC_12_Step_15: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_12_Step_16: An nut back ve man hinh menu");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
	
		log.info("TC_12_Step_17: Mo tab Home");
		homePage.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_1");
	}

	@Parameters({ "otp" })
	@Test
	public void TC_13_DatVeMayBayQuocTeMotChieu_ThanhToanSau_1Nguoi_ThanhToanOTP(String otp) {

		log.info("TC_13_Step_01: Click dat ve may bay ");
		homePage.clickToDynamicIcon(driver, Air_Text.AIR_TICKET_TEXT);
	
		log.info("TC_13_Step_02: Click dat ve may bay quoc te ");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.INTERNATIONAL_AIR_TICKET_TEXT);

		log.info("TC_13_Step_03: Click Dong y ");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_AGREE_TEXT);

		log.info("TC_13_Step_04: Chon mot chieu ");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.ONE_WAY_TEXT);

		log.info("TC_13_Step_05: Chon diem khoi hanh");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.DEPARTURE_PLACE_TEXT);
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.HAN_PLACE);

		log.info("TC_13_Step_06: Chon diem den");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.ARRIVAL_PLACE_TEXT);
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.BKK_PLACE);

		log.info("TC_13_Step_07: Chon ngay di");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.DEPARTURE_DATE_TEXT);
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(3), fifthDay);
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONFIRM_TEXT);

		log.info("TC_13_Step_08: Tim chuyen bay");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_SEARCH_FLIGHT_TEXT);

		log.info("TC_13_Step_09: Chon chuyen bay va dat ve");
		airTicket.clickToDynamicFlight(0, Air_Text.SUGGEST_FLIGHT_CODE_TG);
		ticketPrice = airTicket.getDynamicTextByID("com.VCB:id/tv_amount_flight_selected");
		airTicket.clickToDynamicTextByID("com.VCB:id/btn_book");

		log.info("TC_13_Step 10: Dien Ten Nguoi lon ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_NAME, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_hoten");

		log.info("TC_13_Step 11: Chon gioi tinh nu");
		airTicket.checkToDynamicTextOrDropDownByLabel(Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/tv_Nu");

		log.info("TC_13_Step 12: Dien Email  ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_EMAIL, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_email");

		log.info("TC_13_Step 13: Dien Phone ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_PHONE, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_phonenumber");

		log.info("TC_13_Step 14: Dien Noi dung");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.CONTENT, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_content");

		log.info("TC_13_Step 15: Click checkbox dung lam thong tin hanh khach bay");
		airTicket.clickToDynamicCheckBoxByID("com.VCB:id/iv_check_active");

		log.info("TC_13_Step 16: Click Tiep Tuc");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_13_Step 17: Kiem tra man hinh xac nhan chuyen bay hien thi");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvTitle1"), Air_Text.FLIGHT_CONFIRM_TEXT);

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
		airTicket.scrollUpToTextView(Air_Text.SURPLUS_TEXT);
		airTicket.clickToDynamicTextByID("com.VCB:id/number_account");
		sourceAccount = airTicket.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		log.info("TC_13_Step_28: An tiep tuc");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_13_Step_29: Chon phuong thuc xac thuc SMS OTP");
		airTicket.clickToDynamicTextByID("com.VCB:id/tvptxt");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.SMS_OTP_TEXT);
		fee = airTicket.getAirTicketPriceInfo1Way(Air_Text.FEE_VALUE_TEXT, "com.VCB:id/tvPhiGiaoDich");

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
	public void TC_14_DatVeMayBayQuocTeMotChieu_ThanhToanSau_1Nguoi_BaoCaoGiaoDich() {

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

		log.info("TC_14_Step_15: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_14_Step_16: An nut back ve man hinh menu");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
	
		log.info("TC_14_Step_17: Mo tab Home");
		homePage.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_1");
	}

	@Parameters({ "pass" })
	@Test
	public void TC_15_DatVeMayBayQuocTeMotChieu_ThanhToanSau_1Nguoi_ThanhToanMK(String password) {

		log.info("TC_15_Step_01: Click dat ve may bay ");
		homePage.clickToDynamicIcon(driver, Air_Text.AIR_TICKET_TEXT);
		
		log.info("TC_15_Step_02: Click dat ve may bay quoc te ");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.INTERNATIONAL_AIR_TICKET_TEXT);

		log.info("TC_15_Step_03: Click Dong y ");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_AGREE_TEXT);

		log.info("TC_15_Step_04: Chon mot chieu ");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.ONE_WAY_TEXT);

		log.info("TC_15_Step_05: Chon diem khoi hanh");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.DEPARTURE_PLACE_TEXT);
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.HAN_PLACE);

		log.info("TC_15_Step_06: Chon diem den");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.ARRIVAL_PLACE_TEXT);
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.BKK_PLACE);

		log.info("TC_15_Step_07: Chon ngay di");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.DEPARTURE_DATE_TEXT);
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(3), fifthDay);
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONFIRM_TEXT);

		log.info("TC_15_Step_08: Tim chuyen bay va dat ve");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_SEARCH_FLIGHT_TEXT);
		airTicket.clickToDynamicIcon("com.VCB:id/iv_imgflight");
		ticketPrice = airTicket.getDynamicTextByID("com.VCB:id/tv_amount_flight_selected");
		airTicket.clickToDynamicTextByID("com.VCB:id/btn_book");

		log.info("TC_15_Step 10: Dien Ten Nguoi lon ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_NAME, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_hoten");

		log.info("TC_15_Step 11: Chon gioi tinh nu");
		airTicket.checkToDynamicTextOrDropDownByLabel(Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/tv_Nu");

		log.info("TC_15_Step 12: Dien Email  ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_EMAIL, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_email");

		log.info("TC_15_Step 13: Dien Phone ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_PHONE, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_phonenumber");

		log.info("TC_15_Step 14: Dien Noi dung");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.CONTENT, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_content");

		log.info("TC_15_Step 15: Click checkbox dung lam thong tin hanh khach bay");
		airTicket.clickToDynamicCheckBoxByID("com.VCB:id/iv_check_active");

		log.info("TC_15_Step 16: Click Tiep Tuc");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_15_Step 17: Kiem tra man hinh xac nhan chuyen bay hien thi");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvTitle1"), Air_Text.FLIGHT_CONFIRM_TEXT);

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
		airTicket.scrollUpToTextView(Air_Text.SURPLUS_TEXT);
		airTicket.clickToDynamicTextByID("com.VCB:id/number_account");
		sourceAccount = airTicket.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		log.info("TC_15_Step_28: An tiep tuc");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_15_Step_29: Chon phuong thuc xac thuc Mat khau");
		airTicket.clickToDynamicTextByID("com.VCB:id/tvptxt");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.PASSWORD_TEXT);
		fee = airTicket.getAirTicketPriceInfo1Way(Air_Text.FEE_VALUE_TEXT, "com.VCB:id/tvPhiGiaoDich");

		log.info("TC_15_Step_30: Click Tiep Tuc");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_15_Step_31: Nhap Mat khau roi click Tiep tuc");
		airTicket.inputToDynamicPopupPasswordInput(driver, password, Air_Text.BUTTON_CONTINUE_TEXT);
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_15_Step_32: Xac nhan hien thi man hinh thanh toan thanh cong");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.PAY_ID_TEXT, "com.VCB:id/tvContent"), payID);
		transactionID = airTicket.getAirTicketPriceInfo1Way(Air_Text.TRANSACTION_ID_TEXT, "com.VCB:id/tvContent");

		log.info("TC_15_Step_33: Clich nut Home");
		airTicket.clickToDynamicIcon("com.VCB:id/ivHome");
		
	}

	@Test
	public void TC_16_DatVeMayBayQuocTeMotChieu_ThanhToanSau_1Nguoi_BaoCaoGiaoDich() {

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
