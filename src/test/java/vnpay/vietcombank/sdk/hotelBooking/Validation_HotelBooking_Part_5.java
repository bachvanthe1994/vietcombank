package vnpay.vietcombank.sdk.hotelBooking;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.sdk.hotelBooking.HotelBookingPageObject;
import vietcombankUI.DynamicPageUIs;
import vietcombank_test_data.LogIn_Data;
import vnpay.vietcombank.sdk.hotelBooking.data.HotelBooking_Data;

public class Validation_HotelBooking_Part_5 extends Base {
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

	String paycode = "";
	String totalPrice = "";
	@Test
	public void TC_01_ThanhToanDonDatPhong_KiemTraManHinhThanhToan() {
		homePage = PageFactoryManager.getHomePageObject(driver);
		hotelBooking = PageFactoryManager.getHotelBookingPageObject(driver);

		log.info("TC_01_01_Click Dat phong khach san");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Đặt phòng khách sạn");
		
		log.info("TC_01_02_Click nut Dong y");
		hotelBooking.clickToDynamicButton(driver, "Đồng ý");
		
		log.info("TC_01_03_Tim kiem dia diem");
		hotelBooking.clickToDynamicTextView("Tìm kiếm địa điểm hoặc khách sạn");
		hotelBooking.inputToDynamicInputBox(driver, "CLASSY HOLIDAY HOTEL & SPA", "Tên khách sạn hoặc điểm đến");
		
		log.info("TC_01_04_Chon dia diem");
		hotelBooking.clickToDynamicTextView("CLASSY HOLIDAY HOTEL & SPA");
		
		log.info("TC_01_05_Click dat phong");
		hotelBooking.waitForTextViewDisplay("CLASSY HOLIDAY HOTEL & SPA");
		hotelBooking.scrollIDownToText("Đặt phòng");
		hotelBooking.clickToDynamicTextView("Đặt phòng");

		totalPrice = hotelBooking.getTextViewByID("com.VCB:id/tvTotalPrice").split(" ")[0] + " VND";
		
		log.info("TC_01_06_Nhap ten khach hang");
		hotelBooking.inputToDynamicInputBoxByID("Duc Do", "com.VCB:id/etCustomerName");
		
		log.info("TC_01_07_Nhap so dien thoai khach hang");
		hotelBooking.inputToDynamicInputBoxByID("0363056625", "com.VCB:id/etCustomerPhone");
		
		log.info("TC_01_08_Nhap email khach hang");
		hotelBooking.inputToDynamicInputBoxByID("minhducdo2603@gmail.com", "com.VCB:id/etCustomerEmail");
		
		log.info("TC_01_09_Click Thanh toan");
		hotelBooking.swipeElementToElementByText("Bạn có mã giảm giá?", "Đặt phòng");
		hotelBooking.clickToDynamicTextView("Thanh toán");
		
		log.info("TC_01_10_Kiem tra man hinh thanh toan");
		verifyTrue(hotelBooking.isDynamicTextViewDisplayed("Thông tin hóa đơn"));
		
		log.info("TC_01_11_Kiem tra thong tin nguoi chuyen");
		hotelBooking.swipeElementToElementByText("Họ tên", "Mã giao dịch");
		verifyTrue(hotelBooking.isDynamicTextViewDisplayed("Tài khoản nguồn"));

		log.info("TC_01_12_Kiem tra thong tin khach hang");
		verifyTrue(hotelBooking.isDynamicTextViewDisplayed("Thông tin khách hàng"));
		
		log.info("TC_01_12_00_Chon tai khoan nguon");
		hotelBooking.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		hotelBooking.clickToDynamicButtonLinkOrLinkText(driver, "0541001555240");
		
		log.info("TC_01_12_01_Kiem tra Ho ten");
		verifyEquals(hotelBooking.getDynamicTextInTransactionDetail(driver, "Họ tên"), "Duc Do");
		
		log.info("TC_01_12_02_Kiem tra SDT");
		verifyEquals(hotelBooking.getDynamicTextInTransactionDetail(driver, "Số điện thoại"), "0363056625");
		
		log.info("TC_01_12_03_Kiem tra Email");
		verifyEquals(hotelBooking.getDynamicTextInTransactionDetail(driver, "Email"), "minhducdo2603@gmail.com");
		
		log.info("TC_01_13_Kiem tra thong tin hoa don");
		hotelBooking.scrollIDown(driver, DynamicPageUIs.DYNAMIC_BUTTON, "Tiếp tục");
		verifyTrue(hotelBooking.isDynamicTextViewDisplayed("Thông tin hóa đơn"));
		
		log.info("TC_01_13_01_Kiem tra Ma giao dich");
		verifyTrue(hotelBooking.isDynamicTextViewDisplayed("Mã giao dịch"));
		
		paycode = hotelBooking.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");
		
		log.info("TC_01_13_02_Kiem tra Tong gia tri thanh toan");
		verifyEquals(hotelBooking.getDynamicTextInTransactionDetail(driver, "Tổng giá trị thanh toán"), totalPrice);
		
		log.info("TC_01_13_Click Tiep tuc");
		hotelBooking.clickToDynamicButton(driver, "Tiếp tục");
		
	}
	
	@Test
	public void TC_02_ThanhToanDonDatPhong_KiemTraManHinhXacNhanGiaoDich() {
		log.info("TC_02_01_Kiem tra man hinh xac nhan thong tin");
		verifyTrue(hotelBooking.isDynamicTextViewDisplayed("Xác nhận thông tin"));
		verifyTrue(hotelBooking.isDynamicTextViewDisplayed("Quý khách vui lòng kiểm tra thông tin giao dịch đã khởi tạo"));
		
		log.info("TC_02_02_Kiem tra thong tin");
		log.info("TC_02_02_01_Kiem tra tai khoan nguon");
		verifyEquals(hotelBooking.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), "0541001555240");
		
		log.info("TC_02_02_02_Kiem tra Ma giao dich");
		verifyEquals(hotelBooking.getDynamicTextInTransactionDetail(driver, "Mã giao dịch"), paycode);
		
		log.info("TC_02_02_02_Kiem tra so tien");
		verifyEquals(hotelBooking.getDynamicTextInTransactionDetail(driver, "Số tiền"), totalPrice);
		
		log.info("TC_02_02_01_Kiem tra So tien phi");
		verifyEquals(hotelBooking.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), "0 VND");
		
		log.info("TC_02_03_Kiem tra Chon phuong thuc xac thuc");
		verifyTrue(hotelBooking.isDynamicTextViewDisplayed("Chọn phương thức xác thực"));
		
		log.info("TC_02_04_Chon phuong thuc xac thuc");
		hotelBooking.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		hotelBooking.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");
		
		log.info("TC_02_05_Click tiep tuc");
		hotelBooking.clickToDynamicButton(driver, "Tiếp tục");
		
	}
	
	@Test
	public void TC_03_OTP_KiemTraManHinhHienThi() {
		log.info("TC_03_01_Kiem tra title Xac thuc giao dich");
		verifyTrue(hotelBooking.isDynamicMessageAndLabelTextDisplayed(driver, HotelBooking_Data.TRANSACTION_VALIDATION));

		log.info("TC_03_02_Kiem tra text Ma OTP da duoc gui den SDT ...");
		verifyTrue(hotelBooking.isDynamicMessageAndLabelTextDisplayed(driver, HotelBooking_Data.OTP_NOTIFICATION_SENDED));

		log.info("TC_03_03_Kiem tra button Tiep tuc");
		verifyTrue(hotelBooking.isDynamicButtonDisplayed(driver, "Tiếp tục"));

	}

	@Test
	public void TC_04_OTP_NutTiepTuc_BoTrongOTP() {
		log.info("TC_04_01_Click nut Tiep tuc");
		hotelBooking.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_04_02_Kiem tra message thong bao loi");
		verifyTrue(hotelBooking.isDynamicMessageAndLabelTextDisplayed(driver, HotelBooking_Data.OTP_EMPTY));

		log.info("TC_04_03_Click nut Dong");
		hotelBooking.clickToDynamicButton(driver, "Đóng");
	}

	@Test
	public void TC_05_OTP_NutTiepTuc_NhapOTPNhoHon6KyTu() {
		log.info("TC_05_01_Nhap ma OTP nho hon 6 Ky tu");
		hotelBooking.inputToDynamicOtpOrPIN(driver, "123", "Tiếp tục");

		log.info("TC_05_02_Click nut Tiep tuc");
		hotelBooking.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_05_03_Kiem tra message thong bao loi");
		verifyTrue(hotelBooking.isDynamicMessageAndLabelTextDisplayed(driver, HotelBooking_Data.OTP_LESS_THAN_6_CHARACTER));

		log.info("TC_05_04_Click nut Dong");
		hotelBooking.clickToDynamicButton(driver, "Đóng");
	}

//	@Test
	public void TC_06_OTP_NutTiepTuc_NhapOTPLonHon6KyTu() {
		log.info("TC_06_01_Nhap ma OTP lon hon 6 Ky tu");
		hotelBooking.inputToDynamicOtpOrPIN(driver, "1234567", "Tiếp tục");

	}

	@Test
	public void TC_07_OTP_NutTiepTuc_NhapOTPKhongChinhXac() {
		log.info("TC_07_01_Nhap ma OTP khong chinh xac");
		hotelBooking.inputToDynamicOtpOrPIN(driver, "213456", "Tiếp tục");

		log.info("TC_07_02_Click nut Tiep tuc");
		hotelBooking.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_07_03_Kiem tra message thong bao loi");
		verifyTrue(hotelBooking.isDynamicMessageAndLabelTextDisplayed(driver, HotelBooking_Data.OTP_INVALID));

		log.info("TC_07_04_Click nut Dong");
		hotelBooking.clickToDynamicButton(driver, "Đóng");

		log.info("TC_07_05_Click nut Quay lai");
		hotelBooking.clickToDynamicButtonLinkOrLinkText(driver, "Quay lại");

		log.info("TC_07_06_Click Tiep tuc");
		hotelBooking.clickToDynamicButton(driver, "Tiếp tục");

	}

	@Test
	public void TC_08_ThanhToanDonDatPhong_DatPhongThanhCong() {
		log.info("TC_08_01_Nhap ma OTP chinh xac");
		hotelBooking.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");
		
		log.info("TC_08_02_Click tiep tuc");
		hotelBooking.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_08_03_Kiem tra dat phong thanh cong");
		verifyTrue(hotelBooking.isDynamicMessageAndLabelTextDisplayed(driver, "Thanh toán thành công"));
		verifyTrue(hotelBooking.isDynamicMessageAndLabelTextDisplayed(driver, "Lấy hóa đơn thanh toán"));
		
	}
	
	@Test
	public void TC_09_ThanhToanDonDatPhong_LayHoaDonDatPhong_KiemTraManHinhHienThi() {
		log.info("TC_09_00_Click Lay hoa don thanh toan");
		hotelBooking.clickToDynamicTextView("Lấy hóa đơn thanh toán");
		
		log.info("TC_09_01_Kiem tra man hinh lay hoa don thanh toan");
		verifyTrue(hotelBooking.isDynamicTextViewDisplayed("Thông tin xuất hóa đơn"));
		
		log.info("TC_09_01_02_Kiem tra o ten con ty");
		verifyTrue(hotelBooking.isDynamicInputBoxByTextDisPlayed("Tên công ty"));
		
		log.info("TC_09_01_03_Kiem tra o ma so thue");
		verifyTrue(hotelBooking.isDynamicInputBoxByTextDisPlayed("Mã số thuế"));
		
		log.info("TC_09_01_04_Kiem tra o dia chi cong ty");
		verifyTrue(hotelBooking.isDynamicInputBoxByTextDisPlayed("Địa chỉ công ty"));
		
		log.info("TC_09_01_05_Kiem tra o email nhan hoa don");
		verifyTrue(hotelBooking.isDynamicInputBoxByTextDisPlayed("Email nhận hóa đơn"));
		
	}
	
	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
