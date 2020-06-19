package vnpay.vietcombank.sdk.airticket;

import java.io.IOException;
import java.security.GeneralSecurityException;

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
import pageObjects.SettingVCBSmartOTPPageObject;
import pageObjects.TransactionReportPageObject;
import pageObjects.sdk.airTicketBooking.DynamicAirTicketBookingObjects;
import vietcombank_test_data.Notify_Management_Data.Notify_Text;
import vietcombank_test_data.TransactionReport_Data.ReportTitle;
import vnpay.vietcombank.sdk.airticket.data.DomesticAirTicketBooking_Data;
import vnpay.vietcombank.sdk.airticket.data.DomesticAirTicketBooking_Data.Air_Text;

public class DomesticAirTicketBooking_MainFlow03 extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransactionReportPageObject transactionReport;
	private DynamicAirTicketBookingObjects airTicket;
	private SettingVCBSmartOTPPageObject smartOTP;

	SourceAccountModel sourceAccount = new SourceAccountModel();
	private String fifthDay = getForWardDay(5);
	private String ninthDay = getForWardDay(9);
	private String ticketPrice, payID, transactionID, fee, flightCode01, departureTime01, arrivalTime01, duration01, flightCode02, departureTime02, arrivalTime02, time, source, sourceDetail, dest, destDetail, smartOtpPass;

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException, GeneralSecurityException {
		startServer();
		log.info("Before class: Mo app ");
		if (deviceType.contains("android")) {
			driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		} else if (deviceType.contains("ios")) {
			driver = openIOSApp(deviceName, udid, url);
		}
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);

		smartOtpPass = getDataInCell(6).trim();

		airTicket = PageFactoryManager.getDynamicAirTicketBooking(driver);
		transactionReport = PageFactoryManager.getTransactionReportPageObject(driver);
		smartOTP = PageFactoryManager.getSettingVCBSmartOTPPageObject(driver);
		smartOTP.setupSmartOTP(smartOtpPass, smartOtpPass);
		homePage = PageFactoryManager.getHomePageObject(driver);

	}

	@Test
	public void TC_01_DatVeMayBayNoiDiaMotChieuThanhCong_1Nguoi_ThanhToanSmartOTP() {

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

		flightCode01 = airTicket.getDynamicFlightData("com.VCB:id/recycler_view_one", "0", "com.VCB:id/tv_flightNo");
		departureTime01 = airTicket.getDynamicFlightData("com.VCB:id/recycler_view_one", "0", "com.VCB:id/tv_time_deptTime");
		arrivalTime01 = airTicket.getDynamicFlightData("com.VCB:id/recycler_view_one", "0", "com.VCB:id/tv_time_deptTime_arrival");
		duration01 = airTicket.getDynamicFlightData("com.VCB:id/recycler_view_one", "0", "com.VCB:id/tv_timeduration");

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
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvFlightId"), flightCode01);

		log.info("TC_01_Step 14_5: Kiem tra thong tin ngay di");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextLeft2"), time);

		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextRight1"), time);

		log.info("TC_01_Step 14_6: Kiem tra thong tin gio di ");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextLeft3"), source + " " + departureTime01);

		log.info("TC_01_Step 14_7: Kiem tra thong tin gio den");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextRight2"), dest + " " + arrivalTime01);

		log.info("TC_01_Step 14_8: Kiem trathông tin thoi gian bay");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvDuration"), duration01);

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
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("5", Air_Text.FLIGHT_CODE_TEXT).replace("-", ""), flightCode01);

		log.info("TC_01_Step_18_4: Xac nhan thoi gian bay chieu di");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("6", Air_Text.FLIGHT_TIME_TEXT), departureTime01 + " - " + arrivalTime01);

		log.info("TC_01_Step_19: Click Tiep tuc");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.TOTAL_PAY_TEXT, "com.VCB:id/tvContent"), ticketPrice);

		log.info("TC_01_Step 20: Click Tiep Tuc");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_01_Step_21: Xac nhan giao dich");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.FROM_ACCOUNT_TEXT, "com.VCB:id/tvTu_tai_khoan"), sourceAccount.account);
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.PAY_ID_TEXT, "com.VCB:id/tvBooking_number"), payID);
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.MONEY_AMOUNT_TEXT, "com.VCB:id/tvAmount"), ticketPrice);

		log.info("TC_01_Step_22: Chon phuong thuc xac thuc Smart OTP");
		airTicket.clickToDynamicTextByID("com.VCB:id/tvptxt");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.SMART_OTP_TEXT);
		fee = airTicket.getAirTicketPriceInfo1Way(Air_Text.FEE_VALUE_TEXT, "com.VCB:id/tvPhiGiaoDich");

		log.info("TC_01_Step_23: Click Tiep Tuc");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_01_Step_24: Nhap Ma Smart OTP roi click Tiep tuc");
		airTicket.inputToDynamicSmartOTP(driver, smartOtpPass, "com.VCB:id/otp");
		airTicket.clickToDynamicAcceptButton("com.VCB:id/submit");
		airTicket.clickToDynamicAcceptButton("com.VCB:id/btContinue");

		log.info("TC_01_Step_25: Xac nhan hien thi man hinh thanh toan thanh cong");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.PAY_ID_TEXT, "com.VCB:id/tvContent"), payID);
		transactionID = airTicket.getAirTicketPriceInfo1Way(Air_Text.TRANSACTION_ID_TEXT, "com.VCB:id/tvContent");

		log.info("TC_01_Step_26: Clich nut Home");
		airTicket.clickToDynamicIcon("com.VCB:id/ivHome");

	}

	@Test
	public void TC_02_XacNhanDatVeMayBayNoiDiaMotChieuThanhCong_1Nguoi_TrongBaoCaoGiaoDich() {

		log.info("TC_02_Step_01: Mo tab Menu");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

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
		transactionReport.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_02_Step_16: An nut back ve man hinh menu");
		transactionReport.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_02_Step_17: Mo tab Home");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}
	
	@Test
	public void TC_03_KiemTra_HienThiThongBao_DaDangNhap() {
		
		log.info("TC_03_Step_01: Click vao Inbox");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_3");
		
		log.info("TC_03_Step_02: Click vao tab Tat ca");
		homePage.clickToTextID(driver, "com.VCB:id/radioAll");
		
		log.info("TC_03_Step_03: Lay du lieu hien thi");
		String inboxContent = homePage.getDynamicTextByContentID(driver, "com.VCB:id/recycleview","com.VCB:id/content",payID);
		
		log.info("TC_03_Step_04: So sanh thong tin thanh toan");
		verifyTrue(inboxContent.contains(payID));
		verifyTrue(inboxContent.contains(addCommasToLong(convertAvailableBalanceCurrentcyOrFeeToLong(ticketPrice)+"")));
		verifyTrue(inboxContent.contains(sourceDetail+"-"+destDetail));
		verifyTrue(inboxContent.contains(flightCode01));
		
		log.info("TC_03_Step_05: Click vao tab Khac");
		homePage.clickToTextID(driver, "com.VCB:id/radioOther");
		
		log.info("TC_03_Step_06: Lay du lieu hien thi");
		inboxContent = homePage.getDynamicTextByContentID(driver, "com.VCB:id/recycleview","com.VCB:id/content",payID);
		
		log.info("TC_03_Step_07: So sanh thong tin thanh toan");
		verifyTrue(inboxContent.contains(payID));
		verifyTrue(inboxContent.contains(addCommasToLong(convertAvailableBalanceCurrentcyOrFeeToLong(ticketPrice)+"")));
		verifyTrue(inboxContent.contains(sourceDetail+"-"+destDetail));
		verifyTrue(inboxContent.contains(flightCode01));
		
		log.info("TC_03_Step_08: Mo tab Home");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@Test
	public void TC_04_DatVeMayBayNoiDiaKhuHoi_ThanhToanSau_1Nguoi_ThanhToanSmartOTP() {

		log.info("TC_04_Step_01: Click Dat ve may bay");
		homePage.clickToDynamicIcon(driver, Air_Text.AIR_TICKET_TEXT);

		log.info("TC_04_Step_02: Click dat ve may bay noi dia ");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.DOMESTIC_AIR_TICKET_TEXT);

		log.info("TC_04_Step_03: Click Dong y ");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_AGREE_TEXT);

		log.info("TC_04_Step_04: Chon khu hoi ");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.TWO_WAYS_TEXT);

		log.info("TC_04_Step_05: Chon diem khoi hanh");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.DEPARTURE_PLACE_TEXT);
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.HAN_PLACE);

		log.info("TC_04_Step_06: Chon diem den");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.ARRIVAL_PLACE_TEXT);
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.SGN_PLACE);

		log.info("TC_04_Step_07: Chon ngay di / ngay ve");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.DEPARTURE_DATE_TEXT);
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(5), fifthDay);
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(9), ninthDay);
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONFIRM_TEXT);

		log.info("TC_04_Step_08: Tim chuyen bay");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_SEARCH_FLIGHT_TEXT);
		source = airTicket.getDynamicTextByID("com.VCB:id/tvTitle2Left");
		dest = airTicket.getDynamicTextByID("com.VCB:id/tvTitle2Right");
		sourceDetail = airTicket.getDynamicTextByID("com.VCB:id/tvDiemDi");
		destDetail = airTicket.getDynamicTextByID("com.VCB:id/tvDiemDen");
		time = airTicket.getDynamicTextByID("com.VCB:id/tvTitle2");

		log.info("TC_04_Step_09_1: Chon chuyen bay chieu di");
		airTicket.clickToDynamicFlightTwoWays(0, "com.VCB:id/recy_chieudi", "com.VCB:id/tv_flightcode_internal", Air_Text.SUGGEST_FLIGHT_CODE_VN);

		log.info("TC_04_Step_09_2: Chon chuyen bay chieu ve");
		airTicket.clickToDynamicFlightTwoWays(0, "com.VCB:id/recy_chieuve", "com.VCB:id/tv_flightcode_internal", Air_Text.SUGGEST_FLIGHT_CODE_VN);

		flightCode01 = airTicket.getDynamicTextByID("com.VCB:id/tv_flightCode_internal_chieudi");
		departureTime01 = getRawSplitStringIndex(airTicket.getDynamicTextByID("com.VCB:id/tv_time_internal_chieudi"), "-", 0);
		arrivalTime01 = getRawSplitStringIndex(airTicket.getDynamicTextByID("com.VCB:id/tv_time_internal_chieudi"), "-", 1);

		flightCode02 = airTicket.getDynamicTextByID("com.VCB:id/tv_flightCode_internal_chieuve");
		departureTime02 = getRawSplitStringIndex(airTicket.getDynamicTextByID("com.VCB:id/tv_time_internal_chieuve"), "-", 0);
		arrivalTime02 = getRawSplitStringIndex(airTicket.getDynamicTextByID("com.VCB:id/tv_time_internal_chieuve"), "-", 1);

		log.info("TC_04_Step_09_3: Click dat ve");
		ticketPrice = airTicket.getDynamicTextByID("com.VCB:id/tv_Sum_amount_flight_selected");
		airTicket.clickToDynamicTextByID("com.VCB:id/btn_book");

		log.info("TC_04_Step 10: Dien Ten Nguoi lon ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_NAME, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_hoten");

		log.info("TC_04_Step 11: Chon gioi tinh nu");
		airTicket.checkToDynamicTextOrDropDownByLabel(Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/tv_NuContact");

		log.info("TC_04_Step 12: Dien Email  ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_EMAIL, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_email");

		log.info("TC_04_Step 13: Dien Phone ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_PHONE, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_phonenumber");

		log.info("TC_04_Step 14: Dien Noi dung");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.CONTENT, Air_Text.CONTACT_INFO_TEXT, "com.VCB:id/edt_content");

		log.info("TC_04_Step 15: Click vao check box Dung lam thong tin hanh khach bay");
		airTicket.clickToDynamicCheckBoxByID("com.VCB:id/iv_check_active");

		log.info("TC_04_Step 16: Click Tiep Tuc");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_04_Step 17: Kiem tra man hinh xac nhan chuyen bay hien thi");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvTitle1"), Air_Text.FLIGHT_CONFIRM_TEXT);

		log.info("TC_04_Step 17_1: Kiem tra dia diem khoi hanh");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem1"), sourceDetail);
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem2"), source);

		log.info("TC_04_Step 17_2: Kiem tra dia diem den");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem3"), destDetail);
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem4"), dest);

		log.info("TC_04_Step 17_3: Kiem tra thong tin chieu di");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextLeft1"), Air_Text.DEPARTURE_WAY_TEXT);

		log.info("TC_04_Step 17_4: Kiem tra thong tin ma chieu di");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvFlightId"), flightCode01);

		log.info("TC_04_Step 17_5: Kiem tra thong tin chieu di : ngay di");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextLeft2"), getRawSplitStringIndex(time, "-", 0).trim());

		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextRight1"), getRawSplitStringIndex(time, "-", 0).trim());

		log.info("TC_04_Step 17_6: Kiem tra thong tin chieu di : gio di ");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextLeft3"), source + " " + departureTime01);

		log.info("TC_04_Step 17_7: Kiem tra thong tin chieu di : gio den");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextRight2"), dest + " " + arrivalTime01);

		log.info("TC_04_Step 17_9: Kiem tra thong tin chieu ve");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextLeft1"), Air_Text.ARRIVAL_WAY_TEXT);

		log.info("TC_04_Step 17_10: Kiem tra thong tin ma chieu ve");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvFlightId"), flightCode02);

		log.info("TC_04_Step 17_11: Kiem tra thong tin chieu ve : ngay di");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextLeft2"), getRawSplitStringIndex(time, "-", 1).trim());

		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextRight1"), getRawSplitStringIndex(time, "-", 1).trim());

		log.info("TC_04_Step 17_12: Kiem tra thong tin chieu ve : gio di ");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextLeft3"), dest + " " + departureTime02);

		log.info("TC_04_Step 17_13: Kiem tra thong tin chieu ve : gio den");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuVe", "com.VCB:id/tvTextRight2"), source + " " + arrivalTime02);

		log.info("TC_04_Step 18: Kiem tra tong so tien hien thi dung");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.TOTAL_MONEY_TEXT, "com.VCB:id/tvText2"), ticketPrice);

		log.info("TC_04_Step_19: Click checkbox dong y dieu khoan dat ve");
		airTicket.clickToDynamicCheckBoxByID("com.VCB:id/checkBoxCondition");

		log.info("TC_04_Step_20: Click Thanh toan");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_PAY_TEXT);

		log.info("TC_04_Step_27_1: Chọn tai khoan nguon");
		airTicket.scrollUpToTextView(Air_Text.SURPLUS_TEXT);
		airTicket.clickToDynamicTextByID("com.VCB:id/number_account");
		sourceAccount = airTicket.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		log.info("TC_04_Step_27_2: Xac nhan ma thanh toan");
		payID = airTicket.getAirTicketPriceInfo1Way(Air_Text.PAY_ID_TEXT, "com.VCB:id/tvContent");

		log.info("TC_04_Step_27_3: Xac nhan chieu di bay ");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("4", Air_Text.FLIGHT_WAY_TEXT), source + " → " + dest);

		log.info("TC_04_Step_27_4: Xac nhan so hieu chuyen bay chieu di");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("5", Air_Text.FLIGHT_CODE_TEXT).replace("-", ""), flightCode01);

		log.info("TC_04_Step_27_5: Xac nhan thoi gian bay chieu di");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("6", Air_Text.FLIGHT_TIME_TEXT), departureTime01 + " - " + arrivalTime01);

		log.info("TC_04_Step_27_6: Xac nhan chieu ve bay ");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("9", Air_Text.FLIGHT_WAY_TEXT), dest + " → " + source);

		log.info("TC_04_Step_27_7: Xac nhan so hieu chuyen bay chieu ve");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("10", Air_Text.FLIGHT_CODE_TEXT).replace("-", ""), flightCode02);

		log.info("TC_04_Step_27_8: Xac nhan thoi gian bay chieu ve");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("11", Air_Text.FLIGHT_TIME_TEXT), departureTime02 + " - " + arrivalTime02);

		log.info("TC_04_Step_27_9: Xac nhan Tong tien thanh toan");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.TOTAL_PAY_TEXT, "com.VCB:id/tvContent"), ticketPrice);

		log.info("TC_04_Step_28: An tiep tuc");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_04_Step_29_1: Xac nhan giao dich");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.FROM_ACCOUNT_TEXT, "com.VCB:id/tvTu_tai_khoan"), sourceAccount.account);
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.PAY_ID_TEXT, "com.VCB:id/tvBooking_number"), payID);
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.MONEY_AMOUNT_TEXT, "com.VCB:id/tvAmount"), ticketPrice);

		log.info("TC_04_Step_29_2: Chon phuong thuc xac thuc Smart OTP");
		airTicket.clickToDynamicTextByID("com.VCB:id/tvptxt");
		airTicket.clickToDynamicTextOrButtonLink(Air_Text.SMART_OTP_TEXT);
		fee = airTicket.getAirTicketPriceInfo1Way(Air_Text.FEE_VALUE_TEXT, "com.VCB:id/tvPhiGiaoDich");

		log.info("TC_04_Step_30: Click Tiep Tuc");
		airTicket.clickToDynamicButton(Air_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_04_Step_31: Nhap Ma Smart OTP roi click Tiep tuc");
		airTicket.inputToDynamicSmartOTP(driver, smartOtpPass, "com.VCB:id/otp");
		airTicket.clickToDynamicAcceptButton("com.VCB:id/submit");
		airTicket.clickToDynamicAcceptButton("com.VCB:id/btContinue");

		log.info("TC_04_Step_32: Xac nhan hien thi man hinh thanh toan thanh cong");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way(Air_Text.PAY_ID_TEXT, "com.VCB:id/tvContent"), payID);
		transactionID = airTicket.getAirTicketPriceInfo1Way(Air_Text.TRANSACTION_ID_TEXT, "com.VCB:id/tvContent");

		log.info("TC_04_Step_33: Clich nut Home");
		airTicket.clickToDynamicIcon("com.VCB:id/ivHome");

	}

	@Test
	public void TC_05_DatVeMayBayNoiDiaKhuHoi_ThanhToanSau_1Nguoi_BaoCaoGiaoDich() {

		log.info("TC_05_Step_01: Mo tab Menu");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_05_Step_02: Mo sub-menu 'Bao cao giao dich");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_05_Step_03: An vao Dropdown 'Tat ca cac loai giao dich");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectTransType");

		log.info("TC_05_Step_04: Chon 'Thanh toan ve may bay'");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.AIR_TICKET);

		log.info("TC_05_Step_05: An vao Dropdown 'Chon tai khoan/the");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_05_Step_06: Chon tai khoan vua thuc hien giao dich");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_05_Step_07: An nut Tim kiem");
		transactionReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");

		log.info("TC_05_Step_08: An vao giao dich dau tien");
		transactionReport.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_05_Step_09: Xac nhan hien thi Title 'Chi tiet giao dich'");
		verifyEquals(transactionReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), ReportTitle.TRANSACTION_DETAIL);

		log.info("TC_05_Step_10: Xac nhan hien thi dung ma giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.TRANSACTION_NUMBER), transactionID);

		log.info("TC_05_Step_11: Xac nhan hien thi so tai khoan giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.ACCOUNT_CARD), sourceAccount.account);

		log.info("TC_05_Step_12: Xac nhan hien thi Ma Thanh toan");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.CODE_TRANSFER), payID);

		log.info("TC_05_Step_13: Xac nhan hien thi So tien phi");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.TRANSACTION_FEE), fee);

		log.info("TC_05_Step_15: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_05_Step_16: An nut back ve man hinh menu");
		transactionReport.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_05_Step_17: Mo tab Home");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}
	
	@Parameters ({"pass"})
	@Test
	public void TC_06_KiemTra_HienThiThongBao_ChuaDangNhap(String password) {
		
		log.info("TC_06_Step_01: Click vao Menu");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");
		
		log.info("TC_06_Step_02: Click vao Thoat Ung Dung");
		homePage.scrollUpToText(driver, Notify_Text.LOG_OUT_TEXT);
		homePage.clickToDynamicButtonLinkOrLinkTextNotScroll(driver, Notify_Text.LOG_OUT_TEXT);
		
		log.info("TC_06_Step_03: Click vao Dong y");
		homePage.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
		
		log.info("TC_06_Step_04: Click vao Inbox");
		homePage.clickToDynamicImageView(driver, "com.VCB:id/ivOTT");
		
		log.info("TC_06_Step_05: Click vao tab Tat ca");
		homePage.clickToTextID(driver, "com.VCB:id/radioAll");
		
		log.info("TC_06_Step_06: Lay du lieu hien thi");
		String inboxContent = homePage.getDynamicTextByContentID(driver, "com.VCB:id/recycleview","com.VCB:id/content",payID);
		
		log.info("TC_06_Step_07: So sanh thong tin thanh toan");
		verifyTrue(inboxContent.contains(payID));
		verifyTrue(inboxContent.contains(addCommasToLong(convertAvailableBalanceCurrentcyOrFeeToLong(ticketPrice)+"")));
		verifyTrue(inboxContent.contains(sourceDetail+"-"+destDetail));
		verifyTrue(inboxContent.contains(destDetail+"-"+sourceDetail));
		verifyTrue(inboxContent.contains(flightCode01));
		verifyTrue(inboxContent.contains(flightCode02));
		
		log.info("TC_06_Step_08: Click vao tab Khac");
		homePage.clickToTextID(driver, "com.VCB:id/radioOther");
		
		log.info("TC_06_Step_09: Lay du lieu hien thi");
		inboxContent = homePage.getDynamicTextByContentID(driver, "com.VCB:id/recycleview","com.VCB:id/content",payID);

		log.info("TC_06_Step_10: So sanh thong tin thanh toan");
		verifyTrue(inboxContent.contains(payID));
		verifyTrue(inboxContent.contains(addCommasToLong(convertAvailableBalanceCurrentcyOrFeeToLong(ticketPrice)+"")));
		verifyTrue(inboxContent.contains(sourceDetail+"-"+destDetail));
		verifyTrue(inboxContent.contains(destDetail+"-"+sourceDetail));
		verifyTrue(inboxContent.contains(flightCode01));
		verifyTrue(inboxContent.contains(flightCode02));
		
		log.info("TC_06_Step_11: Back ve man Log In");
		homePage.clickToDynamicImageView(driver, "com.VCB:id/back");
		
		log.info("TC_06_Step_12: Nhap mat khau va dang nhap");
		homePage.inputIntoEditTextByID(driver, password, "com.VCB:id/edtInput");
		homePage.clickToDynamicAcceptButton(driver, "com.VCB:id/btnNext");
		
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		service.stop();
	}

}