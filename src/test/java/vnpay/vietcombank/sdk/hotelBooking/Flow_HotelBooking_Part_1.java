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

	}

	String paycode = "";
	String totalPrice = "";

	@Test
	public void TC_01_ThanhToanDonDatPhong_KiemTraManHinhThanhToan() {
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

		log.info("TC_01_06_Nhap ten khach hang");
		hotelBooking.inputToDynamicInputBoxByID("Duc Do", "com.VCB:id/etCustomerName");

		log.info("TC_01_07_Nhap so dien thoai khach hang");
		hotelBooking.inputToDynamicInputBoxByID("0363056625", "com.VCB:id/etCustomerPhone");

		log.info("TC_01_08_Nhap email khach hang");
		hotelBooking.inputToDynamicInputBoxByID("minhducdo2603@gmail.com", "com.VCB:id/etCustomerEmail");

		log.info("TC_01_09_Click Thanh toan");
		hotelBooking.swipeElementToElementByText("Bạn có mã giảm giá?", "Đặt phòng");
		hotelBooking.clickToDynamicTextView("THANH TOÁN");

		log.info("TC_01_10_Chon tai khoan nguon");
		hotelBooking.clickToDynamicDropDown("Tài khoản nguồn");
		hotelBooking.clickToDynamicTextOrButtonLink(Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_01_11_Kiem tra thong tin hoa don");
		hotelBooking.scrollDownToButton(driver, "Tiếp tục");
	
		log.info("TC_01_12_Click Tiep tuc");
		hotelBooking.clickToDynamicButton("Tiếp tục");
		
		log.info("TC_01_13_Chon phuong thuc xac thuc");
		hotelBooking.clickToDynamicTextOrButtonLink("Mật khẩu đăng nhập");
		hotelBooking.clickToDynamicTextOrButtonLink("SMS OTP");

		log.info("TC_01_14_Click tiep tuc");
		hotelBooking.clickToDynamicButton("Tiếp tục");

		log.info("TC_01_15_Nhap ma OTP chinh xac");
		hotelBooking.inputToDynamicOtpOrPIN(LogIn_Data.Login_Account.OTP, "Tiếp tục");
		
		log.info("TC_01_16_Click tiep tuc");
		hotelBooking.clickToDynamicButton("Tiếp tục");

		
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();
	}

}
