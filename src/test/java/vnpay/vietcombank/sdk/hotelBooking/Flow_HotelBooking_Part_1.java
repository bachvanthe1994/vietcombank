package vnpay.vietcombank.sdk.hotelBooking;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import pageObjects.LogInPageObject;
import pageObjects.sdk.hotelBooking.HotelBookingPageObject;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.LogIn_Data;
import vnpay.vietcombank.sdk.hotelBooking.data.HotelBooking_Data;

public class Flow_HotelBooking_Part_1 extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HotelBookingPageObject hotelBooking;
	private String transferTime, transactionNumber, ticketCode;
	private long surplus, availableBalance, actualAvailableBalance, fee, money;
	String password = "";
	
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

		password = pass;
		
	}

	String paycode = "";
	String totalPrice = "";

	@Test
	public void TC_01_DatPhongKhachSan() {
		hotelBooking = PageFactoryManager.getHotelBookingPageObject(driver);

		log.info("TC_01_01_Click Dat phong khach san");
		hotelBooking.clickToDynamicTextOrButtonLink("Đặt phòng khách sạn");

		log.info("TC_01_02_Click nut Dong y");
		hotelBooking.clickToDynamicButton("Đồng ý");

		log.info("TC_01_03_Tim kiem dia diem");
		hotelBooking.clickToDynamicTextViewByID("com.VCB:id/tvPlaceName");
		hotelBooking.inputToDynamicInputBox(HotelBooking_Data.HOTEL_NAME_BOOKING_INPUT, "Tên khách sạn hoặc điểm đến");

		log.info("TC_01_04_Chon dia diem");
		hotelBooking.clickToDynamicTextView(HotelBooking_Data.HOTEL_NAME_BOOKING);

		log.info("TC_01_05_Click dat phong");
		hotelBooking.waitForTextViewDisplay(HotelBooking_Data.HOTEL_NAME_BOOKING);
		hotelBooking.scrollIDownToText("ĐẶT PHÒNG");
		hotelBooking.clickToDynamicTextView("ĐẶT PHÒNG");

		money = convertAvailableBalanceCurrentcyOrFeeToLong(hotelBooking.getTextViewByID("com.VCB:id/tvTotalPrice"));
		
		log.info("TC_01_06_Nhap ten khach hang");
		hotelBooking.inputToDynamicInputBoxByID("Duc Do", "com.VCB:id/etCustomerName");

		log.info("TC_01_07_Nhap so dien thoai khach hang");
		hotelBooking.inputToDynamicInputBoxByID(HotelBooking_Data.PHONE_BOOKING, "com.VCB:id/etCustomerPhone");

		log.info("TC_01_08_Nhap email khach hang");
		hotelBooking.inputToDynamicInputBoxByID(HotelBooking_Data.EMAIL_BOOKING, "com.VCB:id/etCustomerEmail");

		log.info("TC_01_09_Click Thanh toan");
		hotelBooking.swipeElementToElementByText("Bạn có mã giảm giá?", "Đặt phòng");
		hotelBooking.clickToDynamicTextView("THANH TOÁN");

		log.info("TC_01_10_Chon tai khoan nguon");
		hotelBooking.clickToDynamicDropDown("Tài khoản nguồn");
		hotelBooking.clickToDynamicTextOrButtonLink(Account_Data.Valid_Account.ACCOUNT2);
		surplus = convertAvailableBalanceCurrentcyOrFeeToLong(hotelBooking.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));

		log.info("TC_01_11_Kiem tra thong tin hoa don");
		hotelBooking.scrollDownToButton(driver, "Tiếp tục");
	
		log.info("TC_01_12_Click Tiep tuc");
		hotelBooking.clickToDynamicButton("Tiếp tục");
		
		log.info("TC_01_13_Chon phuong thuc xac thuc");
		hotelBooking.clickToDynamicTextOrButtonLink("Mật khẩu đăng nhập");
		fee = convertAvailableBalanceCurrentcyOrFeeToLong(hotelBooking.getDynamicTextInTransactionDetail(driver, "Mật khẩu đăng nhập"));
		hotelBooking.clickToDynamicTextOrButtonLink("Mật khẩu đăng nhập");

		log.info("TC_01_14_Click tiep tuc");
		hotelBooking.clickToDynamicButton("Tiếp tục");

		log.info("TC_01_15_Nhap ma OTP chinh xac");
		hotelBooking.inputToDynamicPopupPasswordInput(password, "Tiếp tục");
		
		log.info("TC_01_16_Click tiep tuc");
		hotelBooking.clickToDynamicButton("Tiếp tục");

		log.info("TC_01_24: Kiem tra man hinh thanh toan thanh cong");
		verifyTrue(hotelBooking.isDynamicMessageAndLabelTextDisplayed("THANH TOÁN THÀNH CÔNG"));
		transferTime = hotelBooking.getTransferTimeSuccess("THANH TOÁN THÀNH CÔNG");
		transactionNumber = hotelBooking.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");
		ticketCode = hotelBooking.getDynamicTextInTransactionDetail(driver, "Mã nhận phòng");
		
		log.info("TC_01_25_Kiem tra nut Thuc hien giao dich moi");
		verifyTrue(hotelBooking.isDynamicButtonDisplayed("Thực hiện giao dịch mới"));
		
		log.info("TC_01_26_Click Thuc hien giao dich moi");
		hotelBooking.clickToDynamicButton("Thực hiện giao dịch mới");
		
		log.info("TC_01_27_Tinh so du kha dung cua tai khoan sau khi thanh toan thanh cong");
		availableBalance = canculateAvailableBalances(surplus, money, fee);
	}
	
	@Test
	public void TC_02_DatPhongKhachSan_BaoCaoGiaoDich() {
		log.info("TC_02_1: Click  nut Back");
		hotelBooking.clickToDynamicImageViewByID("com.VCB:id/ivBack");

		log.info("TC_02_2: Click vao More Icon");
		hotelBooking.clickToDynamicImageViewByID("com.VCB:id/menu_5");

		log.info("TC_02_3: Click Bao Cao giao Dich");
		hotelBooking.clickToDynamicTextView("Báo cáo giao dịch");

		log.info("TC_02_4: Click Tat Ca Cac Loai Giao Dich");
		hotelBooking.clickToDynamicTextView("Tất cả các loại giao dịch");

		log.info("TC_02_5: Chon Chuyen Tien Trong VCB");
		hotelBooking.clickToDynamicTextView("Thanh toán đặt phòng khách sạn");

		log.info("TC_02_6: Click Chon Tai Khoan");
		hotelBooking.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvSelectAcc");
		
		log.info("TC_02_7: Lay so du kha dung luc sau");
		actualAvailableBalance = convertAvailableBalanceCurrentcyOrFeeToLong(hotelBooking.getDynamicTextInTransactionDetail(driver, Account_Data.Valid_Account.ACCOUNT2));
		
		log.info("TC_02_8: Kiem tra so du kha dung sau khi thanh toan thanh cong");
		verifyEquals(actualAvailableBalance, availableBalance);
		
		log.info("TC_02_9: Chon tai Khoan chuyen");
		hotelBooking.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_02_10: Click Tim Kiem");
		hotelBooking.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_02_11: Kiem tra ngay tao giao dich hien thi");
		String reportTime1 = hotelBooking.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
		verifyEquals(convertDateTimeIgnoreHHmmss(reportTime1), convertTransferTimeToReportDateTime(transferTime));
		
		log.info("TC_02_12: Kiem tra so tien chuyen hien thi");
		verifyEquals(hotelBooking.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(money + "") + " VND"));
		
		log.info("TC_02_13: Click vao giao dich");
		hotelBooking.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
		
		log.info("TC_02_14: Kiem tra thoi gian tao giao dich hien thi");
		String reportTime2 = hotelBooking.getDynamicTextInTransactionDetail("Thời gian giao dịch");
		verifyEquals(reportTime2, reportTime1);

		log.info("TC_02_15: Kiem tra ma giao dich");
		verifyEquals(hotelBooking.getDynamicTextInTransactionDetail("Số lệnh giao dịch"), transactionNumber);
		
		log.info("TC_02_16: Kiem tra so tai khoan trich no");
		verifyEquals(hotelBooking.getDynamicTextInTransactionDetail("Tài khoản/thẻ trích nợ"), Account_Data.Valid_Account.ACCOUNT2);
		
		log.info("TC_02_17: Kiem tra ma nhan phong");
		
		log.info("TC_02_18: Kiem tra so tien giao dich hien thi");
		verifyTrue(hotelBooking.getDynamicTextInTransactionDetail("Số tiền giao dịch").contains(addCommasToLong(money + "") + " VND"));
		
		log.info("TC_02_19: Kiem tra so tien phi");
		verifyEquals(hotelBooking.getDynamicTextInTransactionDetail("Số tiền phí"), addCommasToLong(fee + "") + " VND");
		
		log.info("TC_02_20: Kiem tra loai giao dich");
		verifyEquals(hotelBooking.getDynamicTextInTransactionDetail("Loại giao dịch"), "Thanh toán đặt phòng khách sạn");
		
		log.info("TC_02_21: Kiem tra noi dung giao dich");
		String note = "MBVCB" + transactionNumber + ". thanh toan phong khach san VNP";
		verifyTrue(hotelBooking.getDynamicTextInTransactionDetail("Nội dung giao dịch").contains(note));
		
		log.info("TC_02_22: Click  nut Back");
		hotelBooking.clickToDynamicBackIcon("Chi tiết giao dịch");

		log.info("TC_02_23: Click  nut Back");
		hotelBooking.clickToDynamicBackIcon("Báo cáo giao dịch");

		log.info("TC_02_24: Click  nut Home");
		hotelBooking.clickToDynamicImageViewByID("com.VCB:id/menu_1");
		
	}
	
	@Test
	public void TC_03_DatPhongKhachSan_ThanhToanOTP() {
		hotelBooking = PageFactoryManager.getHotelBookingPageObject(driver);

		log.info("TC_03_01_Click Dat phong khach san");
		hotelBooking.clickToDynamicTextOrButtonLink("Đặt phòng khách sạn");

		log.info("TC_03_02_Click nut Dong y");
		hotelBooking.clickToDynamicButton("Đồng ý");

		log.info("TC_03_03_Tim kiem dia diem");
		hotelBooking.clickToDynamicTextViewByID("com.VCB:id/tvPlaceName");
		hotelBooking.inputToDynamicInputBox(HotelBooking_Data.HOTEL_NAME_BOOKING_INPUT, "Tên khách sạn hoặc điểm đến");

		log.info("TC_03_04_Chon dia diem");
		hotelBooking.clickToDynamicTextView(HotelBooking_Data.HOTEL_NAME_BOOKING);

		log.info("TC_03_05_Click dat phong");
		hotelBooking.waitForTextViewDisplay(HotelBooking_Data.HOTEL_NAME_BOOKING);
		hotelBooking.scrollIDownToText("ĐẶT PHÒNG");
		hotelBooking.clickToDynamicTextView("ĐẶT PHÒNG");

		money = convertAvailableBalanceCurrentcyOrFeeToLong(hotelBooking.getTextViewByID("com.VCB:id/tvTotalPrice"));
		
		log.info("TC_03_06_Nhap ten khach hang");
		hotelBooking.inputToDynamicInputBoxByID("Duc Do", "com.VCB:id/etCustomerName");

		log.info("TC_03_07_Nhap so dien thoai khach hang");
		hotelBooking.inputToDynamicInputBoxByID(HotelBooking_Data.PHONE_BOOKING, "com.VCB:id/etCustomerPhone");

		log.info("TC_03_08_Nhap email khach hang");
		hotelBooking.inputToDynamicInputBoxByID(HotelBooking_Data.EMAIL_BOOKING, "com.VCB:id/etCustomerEmail");

		log.info("TC_03_09_Click Thanh toan");
		hotelBooking.swipeElementToElementByText("Bạn có mã giảm giá?", "Đặt phòng");
		hotelBooking.clickToDynamicTextView("THANH TOÁN");

		log.info("TC_03_10_Chon tai khoan nguon");
		hotelBooking.clickToDynamicDropDown("Tài khoản nguồn");
		hotelBooking.clickToDynamicTextOrButtonLink(Account_Data.Valid_Account.ACCOUNT2);
		surplus = convertAvailableBalanceCurrentcyOrFeeToLong(hotelBooking.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));

		log.info("TC_03_11_Kiem tra thong tin hoa don");
		hotelBooking.scrollDownToButton(driver, "Tiếp tục");
	
		log.info("TC_03_12_Click Tiep tuc");
		hotelBooking.clickToDynamicButton("Tiếp tục");
		
		log.info("TC_03_13_Chon phuong thuc xac thuc");
		hotelBooking.clickToDynamicTextOrButtonLink("Mật khẩu đăng nhập");
		fee = convertAvailableBalanceCurrentcyOrFeeToLong(hotelBooking.getDynamicTextInTransactionDetail(driver, "SMS OTP"));
		hotelBooking.clickToDynamicTextOrButtonLink("SMS OTP");

		log.info("TC_03_14_Click tiep tuc");
		hotelBooking.clickToDynamicButton("Tiếp tục");

		log.info("TC_03_15_Nhap ma OTP chinh xac");
		hotelBooking.inputToDynamicOtp(LogIn_Data.Login_Account.OTP, "Tiếp tục");
		
		log.info("TC_03_16_Click tiep tuc");
		hotelBooking.clickToDynamicButton("Tiếp tục");

		log.info("TC_03_24: Kiem tra man hinh thanh toan thanh cong");
		verifyTrue(hotelBooking.isDynamicMessageAndLabelTextDisplayed("THANH TOÁN THÀNH CÔNG"));
		transferTime = hotelBooking.getTransferTimeSuccess("THANH TOÁN THÀNH CÔNG");
		transactionNumber = hotelBooking.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");
		ticketCode = hotelBooking.getDynamicTextInTransactionDetail(driver, "Mã nhận phòng");
		
		log.info("TC_03_25_Kiem tra nut Thuc hien giao dich moi");
		verifyTrue(hotelBooking.isDynamicButtonDisplayed("Thực hiện giao dịch mới"));
		
		log.info("TC_03_26_Click Thuc hien giao dich moi");
		hotelBooking.clickToDynamicButton("Thực hiện giao dịch mới");
		
		log.info("TC_03_27_Tinh so du kha dung cua tai khoan sau khi thanh toan thanh cong");
		availableBalance = canculateAvailableBalances(surplus, money, fee);
	}
	
	@Test
	public void TC_04_DatPhongKhachSan_ThanhToanOTP_BaoCaoGiaoDich() {
		log.info("TC_04_1: Click  nut Back");
		hotelBooking.clickToDynamicImageViewByID("com.VCB:id/ivBack");

		log.info("TC_04_2: Click vao More Icon");
		hotelBooking.clickToDynamicImageViewByID("com.VCB:id/menu_5");

		log.info("TC_04_3: Click Bao Cao giao Dich");
		hotelBooking.clickToDynamicTextView("Báo cáo giao dịch");

		log.info("TC_04_4: Click Tat Ca Cac Loai Giao Dich");
		hotelBooking.clickToDynamicTextView("Tất cả các loại giao dịch");

		log.info("TC_04_5: Chon Chuyen Tien Trong VCB");
		hotelBooking.clickToDynamicTextView("Thanh toán đặt phòng khách sạn");

		log.info("TC_04_6: Click Chon Tai Khoan");
		hotelBooking.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvSelectAcc");
		
		log.info("TC_04_7: Lay so du kha dung luc sau");
		actualAvailableBalance = convertAvailableBalanceCurrentcyOrFeeToLong(hotelBooking.getDynamicTextInTransactionDetail(driver, Account_Data.Valid_Account.ACCOUNT2));
		
		log.info("TC_04_8: Kiem tra so du kha dung sau khi thanh toan thanh cong");
		verifyEquals(actualAvailableBalance, availableBalance);
		
		log.info("TC_04_9: Chon tai Khoan chuyen");
		hotelBooking.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_04_10: Click Tim Kiem");
		hotelBooking.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_04_11: Kiem tra ngay tao giao dich hien thi");
		String reportTime1 = hotelBooking.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
		verifyEquals(convertDateTimeIgnoreHHmmss(reportTime1), convertTransferTimeToReportDateTime(transferTime));
		
		log.info("TC_04_12: Kiem tra so tien chuyen hien thi");
		verifyEquals(hotelBooking.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(money + "") + " VND"));
		
		log.info("TC_04_13: Click vao giao dich");
		hotelBooking.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
		
		log.info("TC_04_14: Kiem tra thoi gian tao giao dich hien thi");
		String reportTime2 = hotelBooking.getDynamicTextInTransactionDetail("Thời gian giao dịch");
		verifyEquals(reportTime2, reportTime1);

		log.info("TC_04_15: Kiem tra ma giao dich");
		verifyEquals(hotelBooking.getDynamicTextInTransactionDetail("Số lệnh giao dịch"), transactionNumber);
		
		log.info("TC_04_16: Kiem tra so tai khoan trich no");
		verifyEquals(hotelBooking.getDynamicTextInTransactionDetail("Tài khoản/thẻ trích nợ"), Account_Data.Valid_Account.ACCOUNT2);
		
		log.info("TC_04_17: Kiem tra ma nhan phong");
		
		log.info("TC_04_18: Kiem tra so tien giao dich hien thi");
		verifyTrue(hotelBooking.getDynamicTextInTransactionDetail("Số tiền giao dịch").contains(addCommasToLong(money + "") + " VND"));
		
		log.info("TC_04_19: Kiem tra so tien phi");
		verifyEquals(hotelBooking.getDynamicTextInTransactionDetail("Số tiền phí"), addCommasToLong(fee + "") + " VND");
		
		log.info("TC_04_20: Kiem tra loai giao dich");
		verifyEquals(hotelBooking.getDynamicTextInTransactionDetail("Loại giao dịch"), "Thanh toán đặt phòng khách sạn");
		
		log.info("TC_04_21: Kiem tra noi dung giao dich");
		String note = "MBVCB" + transactionNumber + ". thanh toan phong khach san VNP";
		verifyTrue(hotelBooking.getDynamicTextInTransactionDetail("Nội dung giao dịch").contains(note));
		
		log.info("TC_04_22: Click  nut Back");
		hotelBooking.clickToDynamicBackIcon("Chi tiết giao dịch");

		log.info("TC_04_23: Click  nut Back");
		hotelBooking.clickToDynamicBackIcon("Báo cáo giao dịch");

		log.info("TC_04_24: Click  nut Home");
		hotelBooking.clickToDynamicImageViewByID("com.VCB:id/menu_1");
		
	}


	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
