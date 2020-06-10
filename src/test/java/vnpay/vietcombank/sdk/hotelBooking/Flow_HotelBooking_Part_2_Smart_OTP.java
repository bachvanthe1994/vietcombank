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
import pageObjects.sdk.hotelBooking.HotelBookingPageObject;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransferMoneyInVCB_Data.TittleData;
import vnpay.vietcombank.sdk.hotelBooking.data.HotelBooking_Data;

public class Flow_HotelBooking_Part_2_Smart_OTP extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HotelBookingPageObject hotelBooking;
	private SettingVCBSmartOTPPageObject smartOTP;

	SourceAccountModel sourceAccount = new SourceAccountModel();
	String password, customer_name, customer_phone, otpSmart, newOTP;

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

		login.Global_login1(phone, pass, opt);

		password = pass;

		hotelBooking = PageFactoryManager.getHotelBookingPageObject(driver);
		smartOTP = PageFactoryManager.getSettingVCBSmartOTPPageObject(driver);
		customer_name = getDataInCell(1);
		customer_phone = getDataInCell(8);

		otpSmart = getDataInCell(6);
		newOTP = "111222";
		smartOTP.setupSmartOTP(LogIn_Data.Login_Account.Smart_OTP, otpSmart);
	}

	String paycode = "";
	String totalPrice = "";

	@Test
	public void TC_01_DatPhongKhachSan() {
		log.info("TC_01_01_Click Dat phong khach san");
		hotelBooking.clickToDynamicTextOrButtonLink(HotelBooking_Data.BOOKING_HOTEL);

		log.info("TC_01_02_Click nut Dong y");
		hotelBooking.clickToDynamicButton(HotelBooking_Data.ACCEPT);

		log.info("TC_01_02_Tim kiem dia diem");
		hotelBooking.clickToDynamicTextViewByID("com.VCB:id/tvPlaceName");
		hotelBooking.inputToDynamicInputBox(HotelBooking_Data.HOTEL_NAME_BOOKING_INPUT, HotelBooking_Data.NAME_HOTEL_OR_PLACE);

		log.info("TC_01_04_Chon dia diem");
		hotelBooking.clickToDynamicTextView(HotelBooking_Data.HOTEL_NAME_BOOKING);

		log.info("TC_01_05_Click dat phong");
		hotelBooking.waitForTextViewDisplay(HotelBooking_Data.HOTEL_NAME_BOOKING);
		hotelBooking.scrollIDownToText(HotelBooking_Data.BOOKING);
		hotelBooking.clickToDynamicTextView(HotelBooking_Data.BOOKING);


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


		log.info("TC_01_11_Kiem tra thong tin hoa don");
		hotelBooking.scrollDownToButton(driver, HotelBooking_Data.CONTINUE_TEXT);

		log.info("TC_01_12_Click Tiep tuc");
		hotelBooking.clickToDynamicButton(HotelBooking_Data.CONTINUE_TEXT);

		log.info("TC_01_13_Chon phuong thuc xac thuc");
		hotelBooking.clickToDynamicDropDown(HotelBooking_Data.CHOICE_METHOD_VERIFY);
		hotelBooking.clickToDynamicTextOrButtonLink(HotelBooking_Data.SMART_OTP);

		log.info("TC_01_14_Click tiep tuc");
		hotelBooking.clickToDynamicButton(HotelBooking_Data.CONTINUE_TEXT);

		log.info("TC_01_Step_23: Nhap Smart OTP");
		hotelBooking.inputToDynamicSmartOTP(driver, newOTP, "com.VCB:id/otp");

		log.info("TC_01_Step_24: Click tiep tuc");
		hotelBooking.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);
		hotelBooking.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);



		log.info("TC_01_25_Kiem tra nut Thuc hien giao dich moi");
		verifyTrue(hotelBooking.isDynamicButtonDisplayed(HotelBooking_Data.NEW_PERFORM_TRANSFER));

		log.info("TC_01_26_Click Thuc hien giao dich moi");
		hotelBooking.clickToDynamicButton(HotelBooking_Data.NEW_PERFORM_TRANSFER);
		
		log.info("TC_01_27_Click back");
		hotelBooking.clickToDynamicBottomMenuOrCloseIcon("com.VCB:id/ivBack");
		
		
	}

	@Parameters({ "otp" })
	@Test
	public void TC_02_DatPhongKhachSan_ThanhToanOTP(String otp) {
		hotelBooking = PageFactoryManager.getHotelBookingPageObject(driver);

		log.info("TC_02_01_Click Dat phong khach san");
		hotelBooking.clickToDynamicTextOrButtonLink(HotelBooking_Data.BOOKING_HOTEL);

		log.info("TC_02_02_Click nut Dong y");
		hotelBooking.clickToDynamicButton(HotelBooking_Data.ACCEPT);

		log.info("TC_02_03_Tim kiem dia diem");
		hotelBooking.clickToDynamicTextViewByID("com.VCB:id/tvPlaceName");
		hotelBooking.inputToDynamicInputBox(HotelBooking_Data.HOTEL_NAME_BOOKING_INPUT, HotelBooking_Data.NAME_HOTEL_OR_PLACE);

		log.info("TC_02_04_Chon dia diem");
		hotelBooking.clickToDynamicTextView(HotelBooking_Data.HOTEL_NAME_BOOKING);

		log.info("TC_02_05_Click dat phong");
		hotelBooking.waitForTextViewDisplay(HotelBooking_Data.HOTEL_NAME_BOOKING);
		hotelBooking.scrollIDownToText(HotelBooking_Data.BOOKING);
		hotelBooking.clickToDynamicTextView(HotelBooking_Data.BOOKING);


		log.info("TC_02_06_Nhap ten khach hang");
		hotelBooking.inputToDynamicInputBoxByID(customer_name, "com.VCB:id/etCustomerName");

		log.info("TC_02_07_Nhap so dien thoai khach hang");
		hotelBooking.inputToDynamicInputBoxByID(customer_phone, "com.VCB:id/etCustomerPhone");

		log.info("TC_02_08_Nhap email khach hang");
		hotelBooking.inputToDynamicInputBoxByID(HotelBooking_Data.EMAIL_BOOKING, "com.VCB:id/etCustomerEmail");

		log.info("TC_02_09_Click Thanh toan");
		hotelBooking.clickToDynamicTextView(HotelBooking_Data.PAYMENT);

		log.info("TC_02_10_Chon tai khoan nguon");
		hotelBooking.scrollUpToText(driver, HotelBooking_Data.SOURCE_ACCOUNT);
		hotelBooking.clickToDynamicDropDown(HotelBooking_Data.SOURCE_ACCOUNT);

		sourceAccount = hotelBooking.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);


		log.info("TC_02_11_Kiem tra thong tin hoa don");
		hotelBooking.scrollDownToButton(driver, HotelBooking_Data.CONTINUE_TEXT);

		log.info("TC_02_12_Click Tiep tuc");
		hotelBooking.clickToDynamicButton(HotelBooking_Data.CONTINUE_TEXT);

		log.info("TC_02_13_Chon phuong thuc xac thuc");
		hotelBooking.clickToDynamicDropDown(HotelBooking_Data.CHOICE_METHOD_VERIFY);
		hotelBooking.clickToDynamicTextOrButtonLink(HotelBooking_Data.SMART_OTP);

		log.info("TC_02_14_Click tiep tuc");
		hotelBooking.clickToDynamicButton(HotelBooking_Data.CONTINUE_TEXT);

		log.info("TC_01_Step_23: Nhap Smart OTP");
		hotelBooking.inputToDynamicSmartOTP(driver, newOTP, "com.VCB:id/otp");

		log.info("TC_01_Step_24: Click tiep tuc");
		hotelBooking.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);
		hotelBooking.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

	
		log.info("TC_02_25_Kiem tra nut Thuc hien giao dich moi");
		verifyTrue(hotelBooking.isDynamicButtonDisplayed(HotelBooking_Data.NEW_PERFORM_TRANSFER));

		log.info("TC_02_26_Click Thuc hien giao dich moi");
		hotelBooking.clickToDynamicButton(HotelBooking_Data.NEW_PERFORM_TRANSFER);

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();
	}

}
