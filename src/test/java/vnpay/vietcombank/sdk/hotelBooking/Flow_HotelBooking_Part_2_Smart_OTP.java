package vnpay.vietcombank.sdk.hotelBooking;

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
import pageObjects.LogInPageObject;
import pageObjects.SettingVCBSmartOTPPageObject;
import pageObjects.TransactionReportPageObject;
import pageObjects.sdk.hotelBooking.HotelBookingPageObject;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransactionReport_Data.ReportTitle;
import vnpay.vietcombank.sdk.hotelBooking.data.HotelBooking_Data;

public class Flow_HotelBooking_Part_2_Smart_OTP extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HotelBookingPageObject hotelBooking;
	private SettingVCBSmartOTPPageObject smartOTP;
	private TransactionReportPageObject reportPage;

	SourceAccountModel sourceAccount = new SourceAccountModel();
	String customer_name, customer_phone, otpSmart, newOTP;
	private long surplus, availableBalance, actualAvailableBalance, fee, money;
	private String transferTime, transactionNumber;
	String paycode = "";
	String totalPrice = "";

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
		hotelBooking = PageFactoryManager.getHotelBookingPageObject(driver);
		smartOTP = PageFactoryManager.getSettingVCBSmartOTPPageObject(driver);
		otpSmart = getDataInCell(6);
		newOTP = "111222";
		smartOTP.setupSmartOTP(LogIn_Data.Login_Account.Smart_OTP, otpSmart);
		customer_name = getDataInCell(1);
		customer_phone = getDataInCell(8);

	}

	@Test
	public void TC_01_DatPhongKhachSan_SmartOTP() {
		log.info("TC_01_01_Click Dat phong khach san");
		hotelBooking.clickToDynamicTextOrButtonLink(HotelBooking_Data.BOOKING_HOTEL);

		log.info("TC_01_02_Click nut Dong y");
		hotelBooking.clickToDynamicButton(HotelBooking_Data.ACCEPT);

		log.info("TC_01_03_Tim kiem dia diem");
		hotelBooking.clickToDynamicTextViewByID("com.VCB:id/tvPlaceName");
		hotelBooking.inputToDynamicInputBox(HotelBooking_Data.HOTEL_NAME_BOOKING_INPUT, HotelBooking_Data.NAME_HOTEL_OR_PLACE);

		log.info("TC_01_04_Chon dia diem");
		hotelBooking.clickToDynamicTextView(HotelBooking_Data.HOTEL_NAME_BOOKING);

		log.info("TC_01_05_Click dat phong");
		hotelBooking.waitForTextViewDisplay(HotelBooking_Data.HOTEL_NAME_BOOKING);
		hotelBooking.scrollIDownToText(HotelBooking_Data.BOOKING);
		hotelBooking.clickToDynamicTextView(HotelBooking_Data.BOOKING);

		money = convertAvailableBalanceCurrentcyOrFeeToLong(hotelBooking.getTextViewByID("com.VCB:id/tvTotalPrice"));

		log.info("TC_01_06_Nhap ten khach hang");
		hotelBooking.inputToDynamicInputBoxByID(customer_name, "com.VCB:id/etCustomerName");

		log.info("TC_01_07_Nhap so dien thoai khach hang");
		hotelBooking.inputToDynamicInputBoxByID(customer_phone, "com.VCB:id/etCustomerPhone");

		log.info("TC_01_08_Nhap email khach hang");
		hotelBooking.inputToDynamicInputBoxByID(HotelBooking_Data.EMAIL_BOOKING, "com.VCB:id/etCustomerEmail");

		log.info("TC_01_09_Click Thanh toan");
		hotelBooking.clickToDynamicTextView(HotelBooking_Data.PAYMENT);

		log.info("TC_01_10_Chon tai khoan nguon");
		hotelBooking.scrollUpToText(driver, HotelBooking_Data.SOURCE_ACCOUNT);
		hotelBooking.clickToDynamicDropDown(HotelBooking_Data.SOURCE_ACCOUNT);

		sourceAccount = hotelBooking.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		surplus = convertAvailableBalanceCurrentcyOrFeeToLong(hotelBooking.getDynamicTextInTransactionDetail(driver, HotelBooking_Data.AVAILIBLE_BALANCES));

		log.info("TC_01_11_Kiem tra thong tin hoa don");
		hotelBooking.scrollDownToButton(driver, HotelBooking_Data.CONTINUE_TEXT);

		log.info("TC_01_12_Click Tiep tuc");
		hotelBooking.clickToDynamicButton(HotelBooking_Data.CONTINUE_TEXT);

		log.info("TC_01_12_Chon phuong thuc xac thuc");
		hotelBooking.clickToDynamicDropDown(HotelBooking_Data.CHOICE_METHOD_VERIFY);

		log.info("TC_01_12_Chon nhap mat khau");
		hotelBooking.clickToDynamicTextOrButtonLink(HotelBooking_Data.SMART_OTP);

		log.info("TC_01_13_get lay phi");
		fee = convertAvailableBalanceCurrentcyOrFeeToLong(hotelBooking.getDynamicTextInTransactionDetail(driver, HotelBooking_Data.FEE_AMOUNT));

		log.info("TC_01_14_Click tiep tuc");
		hotelBooking.clickToDynamicButton(HotelBooking_Data.CONTINUE_TEXT);

		log.info("TC_01_15_Nhap ma OTP chinh xac");
		hotelBooking.inputToDynamicSmartOTP(driver, newOTP, "com.VCB:id/otp");

		log.info("TC_01_16_Click tiep tuc");
		hotelBooking.clickToDynamicAcceptButton(driver, "com.VCB:id/submit");
		hotelBooking.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_24: Kiem tra man hinh thanh toan thanh cong");
		verifyTrue(hotelBooking.isDynamicMessageAndLabelTextDisplayed(HotelBooking_Data.SUCCESS_TRANSFER));
		transferTime = hotelBooking.getTransferTimeSuccess(HotelBooking_Data.SUCCESS_TRANSFER);
		transactionNumber = hotelBooking.getDynamicTextInTransactionDetail(driver, HotelBooking_Data.CODE_TRANSFER);

		log.info("TC_01_25_Kiem tra nut Thuc hien giao dich moi");
		verifyTrue(hotelBooking.isDynamicButtonDisplayed(HotelBooking_Data.NEW_PERFORM_TRANSFER));

		log.info("TC_01_26_Click Thuc hien giao dich moi");
		hotelBooking.clickToDynamicButton(HotelBooking_Data.NEW_PERFORM_TRANSFER);

		log.info("TC_01_27_Tinh so du kha dung cua tai khoan sau khi thanh toan thanh cong");
		availableBalance = canculateAvailableBalances(surplus, money, fee);
	}

	@Test
	public void TC_02_DatPhongKhachSan_BaoCaoGiaoDich_SmartOTP() {

		reportPage = PageFactoryManager.getTransactionReportPageObject(driver);

		log.info("TC_02_1: Click  nut Back");
		reportPage.clickToDynamicImageViewByID(driver, "com.VCB:id/ivBack");

		log.info("TC_02_2: Click vao More Icon");
		reportPage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_02_3: Click Bao Cao giao Dich");
		reportPage.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_02_4: Click Tat Ca Cac Loai Giao Dich");
		reportPage.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.ALL_TYPE_TRANSACTION);

		log.info("TC_02_5: Chon Chuyen Tien Trong VCB");
		reportPage.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.PAYMENT_HOTEL);

		log.info("TC_02_6: Click Chon Tai Khoan");
		reportPage.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_02_7: Lay so du kha dung luc sau");
		actualAvailableBalance = convertAvailableBalanceCurrentcyOrFeeToLong(reportPage.getDynamicTextInTransactionDetail(driver, sourceAccount.account));

		log.info("TC_02_8: Kiem tra so du kha dung sau khi thanh toan thanh cong");
		verifyEquals(actualAvailableBalance, availableBalance);

		log.info("TC_02_9: Chon tai Khoan chuyen");
		reportPage.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_02_10: Click Tim Kiem");
		reportPage.clickToDynamicButton(driver, ReportTitle.SEARCH_BUTTON);

		log.info("TC_02_11: Kiem tra ngay tao giao dich hien thi");
		String reportTime1 = reportPage.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
		verifyEquals(convertDateTimeIgnoreHHmmss(reportTime1), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_02_12: Kiem tra so tien chuyen hien thi");
		verifyEquals(reportPage.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(money + "") + " VND"));

		log.info("TC_02_13: Click vao giao dich");
		reportPage.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_02_14: Kiem tra thoi gian tao giao dich hien thi");
		String reportTime2 = reportPage.getDynamicTextInTransactionDetail(driver, ReportTitle.TIME_TRANSFER);
		verifyEquals(reportTime2, reportTime1);

		log.info("TC_02_15: Kiem tra ma giao dich");
		verifyEquals(reportPage.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_NUMBER), transactionNumber);

		log.info("TC_02_16: Kiem tra so tai khoan trich no");
		verifyEquals(reportPage.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_TRANSFER), sourceAccount.account);

		log.info("TC_02_17: Kiem tra ma nhan phong");

		log.info("TC_02_18: Kiem tra so tien giao dich hien thi");
		verifyTrue(reportPage.getDynamicTextInTransactionDetail(driver, ReportTitle.AMOUNT_TRANSFER).contains(addCommasToLong(money + "") + " VND"));

		log.info("TC_02_19: Kiem tra so tien phi");
		verifyEquals(reportPage.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_FEE), addCommasToLong(fee + "") + " VND");

		log.info("TC_02_20: Kiem tra loai giao dich");
		verifyEquals(reportPage.getDynamicTextInTransactionDetail(driver, ReportTitle.TYPE_TRANSFER), ReportTitle.PAYMENT_HOTEL);

		log.info("TC_02_21: Kiem tra noi dung giao dich");
		String note = "MBVCB." + transactionNumber + ". thanh toan phong khach san VNP";
		String realNote = reportPage.getDynamicTextInTransactionDetail(driver, ReportTitle.CONTENT_TRANSFER);
		verifyTrue(realNote.contains(note));

		log.info("TC_02_22: Click  nut Back");
		reportPage.clickToDynamicBackIcon(driver, ReportTitle.DETAIL_TRANSFER);

		log.info("TC_02_23: Click  nut Back");
		reportPage.clickToDynamicBackIcon(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_02_24: Click  nut Home");
		reportPage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();
	}

}
