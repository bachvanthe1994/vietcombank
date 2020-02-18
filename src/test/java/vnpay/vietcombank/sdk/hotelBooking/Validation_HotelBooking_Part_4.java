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
import pageObjects.LogInPageObject;
import pageObjects.sdk.hotelBooking.HotelBookingPageObject;
import vnpay.vietcombank.sdk.hotelBooking.data.HotelBooking_Data;

public class Validation_HotelBooking_Part_4 extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
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
		hotelBooking = PageFactoryManager.getHotelBookingPageObject(driver);

		log.info("TC_01_01_Click Dat phong khach san");
		hotelBooking.clickToDynamicTextOrButtonLink("Đặt phòng khách sạn");
		
		log.info("TC_01_02_Click nut Dong y");
		hotelBooking.clickToDynamicButton("Đồng ý");
		
		log.info("TC_01_03_Click Tim kiem dia diem");
		hotelBooking.clickToDynamicTextView("Tìm kiếm địa điểm hoặc khách sạn");
		
		log.info("TC_01_04_Click chon dia diem");
		hotelBooking.clickToDynamicTextView("Hà Nội");
		
		log.info("TC_01_05_Click Tim kiem");
		hotelBooking.clickToDynamicTextView("Tìm kiếm");
		
		log.info("TC_01_06_Lay danh sach tim kiem");
		List<HotelBookingInfo> listHotelSearch = hotelBooking.getListHotelSearched();
		
		log.info("TC_01_07_Click chon 1 khach san");
		hotelBooking.clickToDynamicTextView(listHotelSearch.get(0).hotelName);
		
		log.info("TC_01_08_Click Dat phong");
		hotelBooking.waitForTextViewDisplay(listHotelSearch.get(0).hotelName);
		hotelBooking.scrollIDownToText("ĐẶT PHÒNG");
		hotelBooking.clickToDynamicTextView("ĐẶT PHÒNG");
		
	}
	
	@Test
	public void TC_02_DatPhongKhachSan_ManHinhThongTinKhachHang_DeTrongTruongHoTen() {
		log.info("TC_02_01_Nhap ho ten khach hang");
		hotelBooking.inputToDynamicInputBoxByID("", "com.VCB:id/etCustomerName");
		
		log.info("TC_02_02_Nhap so dien thoai khach hang");
		hotelBooking.inputToDynamicInputBoxByID("0363056625", "com.VCB:id/etCustomerPhone");
		
		log.info("TC_02_03_Nhap email khach hang");
		hotelBooking.inputToDynamicInputBoxByID("minhducdo2603@gmail.com", "com.VCB:id/etCustomerEmail");
		
		log.info("TC_02_04_Click chon Thanh toan");
		hotelBooking.swipeElementToElementByText("Bạn có mã giảm giá?", "Đặt phòng");
		hotelBooking.clickToDynamicTextView("Thanh toán");
		
		log.info("TC_02_05_Kiem tra message");
		verifyEquals(hotelBooking.getTextInDynamicPopup("com.VCB:id/tvContent"), HotelBooking_Data.EMPTY_NAME_MESSAGE);
		hotelBooking.clickToDynamicTextView("Đồng ý");
		hotelBooking.swipeElementToElementByText("Bạn có mã giảm giá?", "Thanh toán");
		hotelBooking.inputToDynamicInputBoxByID("Duc Do", "com.VCB:id/etCustomerName");
		
	}
	
	@Test
	public void TC_03_DatPhongKhachSan_ManHinhThongTinKhachHang_DeTrongTruongSoDienThoai() {
		log.info("TC_03_01_Nhap so dien thoai khach hang");
		hotelBooking.inputToDynamicInputBoxByID("", "com.VCB:id/etCustomerPhone");
		
		log.info("TC_03_02_Click Thanh toan");
		hotelBooking.swipeElementToElementByText("Bạn có mã giảm giá?", "Đặt phòng");
		hotelBooking.clickToDynamicTextView("Thanh toán");
		
		log.info("TC_03_03_Kiem tra message");
		verifyEquals(hotelBooking.getTextInDynamicPopup("com.VCB:id/tvContent"), HotelBooking_Data.EMPTY_PHONE_MESSAGE);
		
		log.info("TC_03_04_Click Dong y");
		hotelBooking.clickToDynamicTextView("Đồng ý");
		
		log.info("TC_03_05_Nhap so dien thoai khach hang");
		hotelBooking.swipeElementToElementByText("Bạn có mã giảm giá?", "Thanh toán");
		hotelBooking.inputToDynamicInputBoxByID("0363056625", "com.VCB:id/etCustomerPhone");
		
	}
	
	@Test
	public void TC_04_DatPhongKhachSan_ManHinhThongTinKhachHang_DeTrongTruongEmail() {
		log.info("TC_04_01_Nhap email khach hang");
		hotelBooking.inputToDynamicInputBoxByID("", "com.VCB:id/etCustomerEmail");
		
		log.info("TC_04_02_Click Thanh toan");
		hotelBooking.swipeElementToElementByText("Bạn có mã giảm giá?", "Đặt phòng");
		hotelBooking.clickToDynamicTextView("Thanh toán");
		
		log.info("TC_04_03_Kiem tra message");
		verifyEquals(hotelBooking.getTextInDynamicPopup("com.VCB:id/tvContent"), HotelBooking_Data.EMPTY_EMAIL_MESSAGE);
		
		log.info("TC_04_04_Click Dong y");
		hotelBooking.clickToDynamicTextView("Đồng ý");
	
	}
	
	@Test
	public void TC_05_DatPhongKhachSan_ManHinhThongTinKhachHang_NhapEmailSaiDinhDang() {
		log.info("TC_05_01_Nhap email khach hang");
		hotelBooking.inputToDynamicInputBoxByID("minhducdo2603", "com.VCB:id/etCustomerEmail");
		
		log.info("TC_05_02_Click thanh toan");
		hotelBooking.swipeElementToElementByText("Bạn có mã giảm giá?", "Đặt phòng");
		hotelBooking.clickToDynamicTextView("Thanh toán");
		
		log.info("TC_05_03_Kiem tra message");
		verifyEquals(hotelBooking.getTextInDynamicPopup("com.VCB:id/tvContent"), HotelBooking_Data.NOT_VALID_EMAIL_MESSAGE);
		
		log.info("TC_05_04_Click Dong y");
		hotelBooking.clickToDynamicTextView("Đồng ý");
		
		log.info("TC_05_05_Nhap email khach hang");
		hotelBooking.inputToDynamicInputBoxByID("minhducdo2603@gmail.com", "com.VCB:id/etCustomerEmail");
		
	}
	
	@Test
	public void TC_06_DatPhongKhachSan_ManHinhThongTinKhachHang_NhapSoDienThoaiSaiDinhDang() {
		log.info("TC_06_01_Nhap so dien thoai khach hang");
		hotelBooking.inputToDynamicInputBoxByID("363056625", "com.VCB:id/etCustomerPhone");
		
		log.info("TC_06_02_Click thanh toan");
		hotelBooking.swipeElementToElementByText("Bạn có mã giảm giá?", "Đặt phòng");
		hotelBooking.clickToDynamicTextView("Thanh toán");
		
		log.info("TC_06_03_Kiem tra message");
		verifyEquals(hotelBooking.getTextInDynamicPopup("com.VCB:id/tvContent"), HotelBooking_Data.NOT_VALID_PHONE_MESSAGE);
		
		log.info("TC_06_04_Click Dong y");
		hotelBooking.clickToDynamicTextView("Đồng ý");
		
		log.info("TC_06_05_Nhap so dien thoai khach hang");
		hotelBooking.swipeElementToElementByText("Bạn có mã giảm giá?", "Đặt phòng");
		hotelBooking.inputToDynamicInputBoxByID("0363056625", "com.VCB:id/etCustomerPhone");
		
	}
	
	@Test
	public void TC_07_DatPhongKhachSan_ManHinhThongTinKhachHang_KiemTraNhanNutBack() {
		log.info("TC_07_01_Click nut Back");
		hotelBooking.clickToDynamicBackIcon("Đặt phòng");
		
		log.info("TC_07_02_Kiem tra ve man hinh xem chi tiet khach san");
		hotelBooking.scrollIDownToText("Đặt phòng");
		verifyTrue(hotelBooking.isDynamicTextViewDisplayed("Đặt phòng"));
	}
	
	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();
	}

}
