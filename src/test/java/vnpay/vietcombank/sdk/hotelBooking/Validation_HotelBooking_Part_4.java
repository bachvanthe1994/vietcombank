package vnpay.vietcombank.sdk.hotelBooking;

import java.io.IOException;
import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import model.HotelBookingInfo;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.sdk.hotelBooking.HotelBookingPageObject;
import vnpay.vietcombank.sdk.hotelBooking.data.HotelBooking_Data;

public class Validation_HotelBooking_Part_4 extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private HotelBookingPageObject hotelBooking;
	

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })

	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);

		login = PageFactoryManager.getLoginPageObject(driver);

		login.Global_login(phone, pass, opt);
		
	}

	@Test
	public void TC_01_DatPhongKhachSan_ManHinhThongTinKhachHang_KiemTraManHinhNhapThongTinKhachHang() {
		homePage = PageFactoryManager.getHomePageObject(driver);
		hotelBooking = PageFactoryManager.getHotelBookingPageObject(driver);

		log.info("TC_01_01_Click Dat phong khach san");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Đặt phòng khách sạn");
		
		log.info("TC_01_02_Click nut Dong y");
		hotelBooking.clickToDynamicButton(driver, "Đồng ý");
		
		hotelBooking.clickToDynamicTextView("Tìm kiếm địa điểm hoặc khách sạn");
		hotelBooking.clickToDynamicTextView("Hà Nội");
		
		hotelBooking.clickToDynamicTextView("Tìm kiếm");
		
		List<HotelBookingInfo> listHotelSearch = hotelBooking.getListHotelSearched();
		
		hotelBooking.clickToDynamicTextView(listHotelSearch.get(0).hotelName);
		
//		hotelBooking.scrollIDownToText("Đặt phòng");
		hotelBooking.clickToDynamicTextView("Đặt phòng");
		
	}
	
	@Test
	public void TC_02_DatPhongKhachSan_ManHinhThongTinKhachHang_DeTrongTruongHoTen() {
		hotelBooking.inputToDynamicInputBoxByID("", "com.VCB:id/etCustomerName");
		hotelBooking.inputToDynamicInputBoxByID("0363056625", "com.VCB:id/etCustomerPhone");
		hotelBooking.inputToDynamicInputBoxByID("minhducdo2603@gmail.com", "com.VCB:id/etCustomerEmail");
		hotelBooking.swipeElementToElementByText("Bạn có mã giảm giá?", "Đặt phòng");
		hotelBooking.clickToDynamicTextView("Thanh toán");
		verifyEquals(hotelBooking.getTextInDynamicPopup(driver, "com.VCB:id/tvContent"), HotelBooking_Data.EMPTY_NAME_MESSAGE);
		hotelBooking.clickToDynamicTextView("Đồng ý");
		hotelBooking.swipeElementToElementByText("Bạn có mã giảm giá?", "Thanh toán");
		hotelBooking.inputToDynamicInputBoxByID("Duc Do", "com.VCB:id/etCustomerName");
		
	}
	
	@Test
	public void TC_03_DatPhongKhachSan_ManHinhThongTinKhachHang_DeTrongTruongSoDienThoai() {
		hotelBooking.inputToDynamicInputBoxByID("", "com.VCB:id/etCustomerPhone");
		hotelBooking.swipeElementToElementByText("Bạn có mã giảm giá?", "Đặt phòng");
		hotelBooking.clickToDynamicTextView("Thanh toán");
		verifyEquals(hotelBooking.getTextInDynamicPopup(driver, "com.VCB:id/tvContent"), HotelBooking_Data.EMPTY_PHONE_MESSAGE);
		hotelBooking.clickToDynamicTextView("Đồng ý");
		hotelBooking.swipeElementToElementByText("Bạn có mã giảm giá?", "Thanh toán");
		hotelBooking.inputToDynamicInputBoxByID("0363056625", "com.VCB:id/etCustomerPhone");
		
	}
	
	@Test
	public void TC_04_DatPhongKhachSan_ManHinhThongTinKhachHang_DeTrongTruongEmail() {
		hotelBooking.inputToDynamicInputBoxByID("", "com.VCB:id/etCustomerEmail");
		hotelBooking.swipeElementToElementByText("Bạn có mã giảm giá?", "Đặt phòng");
		hotelBooking.clickToDynamicTextView("Thanh toán");
		verifyEquals(hotelBooking.getTextInDynamicPopup(driver, "com.VCB:id/tvContent"), HotelBooking_Data.EMPTY_EMAIL_MESSAGE);
		hotelBooking.clickToDynamicTextView("Đồng ý");
	
	}
	
	@Test
	public void TC_05_DatPhongKhachSan_ManHinhThongTinKhachHang_NhapEmailSaiDinhDang() {
		hotelBooking.inputToDynamicInputBoxByID("minhducdo2603", "com.VCB:id/etCustomerEmail");
		hotelBooking.swipeElementToElementByText("Bạn có mã giảm giá?", "Đặt phòng");
		hotelBooking.clickToDynamicTextView("Thanh toán");
		verifyEquals(hotelBooking.getTextInDynamicPopup(driver, "com.VCB:id/tvContent"), HotelBooking_Data.NOT_VALID_EMAIL_MESSAGE);
		hotelBooking.clickToDynamicTextView("Đồng ý");
		hotelBooking.inputToDynamicInputBoxByID("minhducdo2603@gmail.com", "com.VCB:id/etCustomerEmail");
		
	}
	
	@Test
	public void TC_06_DatPhongKhachSan_ManHinhThongTinKhachHang_NhapSoDienThoaiSaiDinhDang() {
		hotelBooking.inputToDynamicInputBoxByID("363056625", "com.VCB:id/etCustomerPhone");
		hotelBooking.swipeElementToElementByText("Bạn có mã giảm giá?", "Đặt phòng");
		hotelBooking.clickToDynamicTextView("Thanh toán");
		verifyEquals(hotelBooking.getTextInDynamicPopup(driver, "com.VCB:id/tvContent"), HotelBooking_Data.NOT_VALID_PHONE_MESSAGE);
		hotelBooking.clickToDynamicTextView("Đồng ý");
		hotelBooking.swipeElementToElementByText("Bạn có mã giảm giá?", "Đặt phòng");
		hotelBooking.inputToDynamicInputBoxByID("0363056625", "com.VCB:id/etCustomerPhone");
		
	}
	
	@Test
	public void TC_07_DatPhongKhachSan_ManHinhThongTinKhachHang_KiemTraNhanNutBack() {
		hotelBooking.clickToDynamicBackIcon(driver, "Đặt phòng");
		hotelBooking.scrollIDownToText("Đặt phòng");
		verifyTrue(hotelBooking.isDynamicTextViewDisplayed("Đặt phòng"));
	}
	
	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
