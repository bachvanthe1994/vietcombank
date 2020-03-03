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
import pageObjects.LogInPageObject;
import pageObjects.sdk.hotelBooking.HotelBookingPageObject;
import vietcombankUI.DynamicPageUIs;
import vietcombank_test_data.LogIn_Data;
import vnpay.vietcombank.sdk.hotelBooking.data.HotelBooking_Data;

public class Validation_HotelBooking_Part_5 extends Base {
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
		hotelBooking.clickToDynamicTextView("Tìm kiếm địa điểm hoặc khách sạn");
		hotelBooking.inputToDynamicInputBox("CLASSY HOLIDAY HOTEL & SPA", "Tên khách sạn hoặc điểm đến");

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
		hotelBooking.clickToDynamicDropDown("Tài khoản nguồn");
		hotelBooking.clickToDynamicTextOrButtonLink("0541001555240");

		log.info("TC_01_12_01_Kiem tra Ho ten");
		verifyEquals(hotelBooking.getDynamicTextInTransactionDetail("Họ tên"), "Duc Do");

		log.info("TC_01_12_02_Kiem tra SDT");
		verifyEquals(hotelBooking.getDynamicTextInTransactionDetail("Số điện thoại"), "0363056625");

		log.info("TC_01_12_03_Kiem tra Email");
		verifyEquals(hotelBooking.getDynamicTextInTransactionDetail("Email"), "minhducdo2603@gmail.com");

		log.info("TC_01_13_Kiem tra thong tin hoa don");
		hotelBooking.scrollDownToButton(driver, "Tiếp tục");
		verifyTrue(hotelBooking.isDynamicTextViewDisplayed("Thông tin hóa đơn"));

		log.info("TC_01_13_01_Kiem tra Ma giao dich");
		verifyTrue(hotelBooking.isDynamicTextViewDisplayed("Mã giao dịch"));

		paycode = hotelBooking.getDynamicTextInTransactionDetail("Mã giao dịch");

		log.info("TC_01_13_02_Kiem tra Tong gia tri thanh toan");
		verifyEquals(hotelBooking.getDynamicTextInTransactionDetail("Tổng giá trị thanh toán"), totalPrice);

		log.info("TC_01_13_Click Tiep tuc");
		hotelBooking.clickToDynamicButton("Tiếp tục");

	}

	@Test
	public void TC_02_ThanhToanDonDatPhong_KiemTraManHinhXacNhanGiaoDich() {
		log.info("TC_02_01_Kiem tra man hinh xac nhan thong tin");
		verifyTrue(hotelBooking.isDynamicTextViewDisplayed("Xác nhận thông tin"));
		verifyTrue(hotelBooking.isDynamicTextViewDisplayed("Quý khách vui lòng kiểm tra thông tin giao dịch đã khởi tạo"));

		log.info("TC_02_02_Kiem tra thong tin");
		log.info("TC_02_02_01_Kiem tra tai khoan nguon");
		verifyEquals(hotelBooking.getDynamicTextInTransactionDetail("Tài khoản nguồn"), "0541001555240");

		log.info("TC_02_02_02_Kiem tra Ma giao dich");
		verifyEquals(hotelBooking.getDynamicTextInTransactionDetail("Mã giao dịch"), paycode);

		log.info("TC_02_02_02_Kiem tra so tien");
		verifyEquals(hotelBooking.getDynamicTextInTransactionDetail("Số tiền"), totalPrice);

		log.info("TC_02_02_01_Kiem tra So tien phi");
		verifyEquals(hotelBooking.getDynamicTextInTransactionDetail("Số tiền phí"), "0 VND");

		log.info("TC_02_03_Kiem tra Chon phuong thuc xac thuc");
		verifyTrue(hotelBooking.isDynamicTextViewDisplayed("Chọn phương thức xác thực"));

		log.info("TC_02_04_Chon phuong thuc xac thuc");
		hotelBooking.clickToDynamicTextOrButtonLink("Mật khẩu đăng nhập");
		hotelBooking.clickToDynamicTextOrButtonLink("SMS OTP");

		log.info("TC_02_05_Click tiep tuc");
		hotelBooking.clickToDynamicButton("Tiếp tục");

	}

	@Test
	public void TC_03_OTP_KiemTraManHinhHienThi() {
		log.info("TC_03_01_Kiem tra title Xac thuc giao dich");
		verifyTrue(hotelBooking.isDynamicMessageAndLabelTextDisplayed(HotelBooking_Data.TRANSACTION_VALIDATION));

		log.info("TC_03_02_Kiem tra text Ma OTP da duoc gui den SDT ...");
		verifyTrue(hotelBooking.isDynamicMessageAndLabelTextDisplayed(HotelBooking_Data.OTP_NOTIFICATION_SENDED));

		log.info("TC_03_03_Kiem tra button Tiep tuc");
		verifyTrue(hotelBooking.isDynamicButtonDisplayed("Tiếp tục"));

	}

	@Test
	public void TC_04_OTP_NutTiepTuc_BoTrongOTP() {
		log.info("TC_04_01_Click nut Tiep tuc");
		hotelBooking.clickToDynamicButton("Tiếp tục");

		log.info("TC_04_02_Kiem tra message thong bao loi");
		verifyTrue(hotelBooking.isDynamicMessageAndLabelTextDisplayed(HotelBooking_Data.OTP_EMPTY));

		log.info("TC_04_03_Click nut Dong");
		hotelBooking.clickToDynamicButton("Đóng");
	}

	@Test
	public void TC_05_OTP_NutTiepTuc_NhapOTPNhoHon6KyTu() {
		log.info("TC_05_01_Nhap ma OTP nho hon 6 Ky tu");
		hotelBooking.inputToDynamicOtpOrPIN("123", "Tiếp tục");

		log.info("TC_05_02_Click nut Tiep tuc");
		hotelBooking.clickToDynamicButton("Tiếp tục");

		log.info("TC_05_03_Kiem tra message thong bao loi");
		verifyTrue(hotelBooking.isDynamicMessageAndLabelTextDisplayed(HotelBooking_Data.OTP_LESS_THAN_6_CHARACTER));

		log.info("TC_05_04_Click nut Dong");
		hotelBooking.clickToDynamicButton("Đóng");
	}

	@Test
	public void TC_07_OTP_NutTiepTuc_NhapOTPKhongChinhXac() {
		log.info("TC_07_01_Nhap ma OTP khong chinh xac");
		hotelBooking.inputToDynamicOtpOrPIN("213456", "Tiếp tục");

		log.info("TC_07_02_Click nut Tiep tuc");
		hotelBooking.clickToDynamicButton("Tiếp tục");

		log.info("TC_07_03_Kiem tra message thong bao loi");
		verifyTrue(hotelBooking.isDynamicMessageAndLabelTextDisplayed(HotelBooking_Data.OTP_INVALID));

		log.info("TC_07_04_Click nut Dong");
		hotelBooking.clickToDynamicButton("Đóng");

		log.info("TC_07_05_Click nut Quay lai");
		hotelBooking.clickToDynamicTextOrButtonLink("Quay lại");

		log.info("TC_07_06_Click Tiep tuc");
		hotelBooking.clickToDynamicButton("Tiếp tục");

	}

	@Test
	public void TC_08_ThanhToanDonDatPhong_DatPhongThanhCong() {
		log.info("TC_08_01_Nhap ma OTP chinh xac");
		hotelBooking.inputToDynamicOtpOrPIN(LogIn_Data.Login_Account.OTP, "Tiếp tục");

		log.info("TC_08_02_Click tiep tuc");
		hotelBooking.clickToDynamicButton("Tiếp tục");

		log.info("TC_08_03_Kiem tra dat phong thanh cong");
		verifyTrue(hotelBooking.isDynamicMessageAndLabelTextDisplayed("Thanh toán thành công"));
		verifyTrue(hotelBooking.isDynamicMessageAndLabelTextDisplayed("Lấy hóa đơn thanh toán"));

	}

	@Test
	public void TC_09_ThanhToanHoaDon_XuatHoaDon_KiemTraManHinhHienThi() {
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

	@Test
	public void TC_10_XuatHoaDon_TextBoxTenCongTy_KiemTraGiaTriHienThiMacDinh() {
		log.info("TC_10_01_Kiem tra o ten con ty hien thi mac dinh");
		verifyTrue(hotelBooking.isDynamicInputBoxByTextDisPlayed("Tên công ty"));

	}

	@Test
	public void TC_11_XuatHoaDon_TextBoxTenCongTy_KiemTraKyTuNhap() {
		log.info("TC_11_01_Nhap ky tu vao o Ten cong ty");
		hotelBooking.inputToDynamicInputBoxByID(HotelBooking_Data.SPECIAL_TEXT_NUMBER_OVER_100_CHARACTERS, "com.VCB:id/etCompanyName");

		log.info("TC_11_02_Kiem tra ky tu nhap");
		String actualText = hotelBooking.getTextInEditTextFieldByResourceID("com.VCB:id/etCompanyName");
		verifyEquals(actualText, HotelBooking_Data.SPECIAL_TEXT_NUMBER_OVER_100_CHARACTERS.substring(0, 100));

		log.info("TC_11_03_Kiem tra so luong ky tu cho phep");
		verifyEquals(actualText.length(), 100);

	}

	@Test
	public void TC_12_XuatHoaDon_TextBoxTenCongTy_DeTrongTruongTenCongTy() {
		log.info("TC_12_01_Nhap ky tu vao o Ten cong ty");
		hotelBooking.inputToDynamicInputBoxByID("", "com.VCB:id/etCompanyName");

		log.info("TC_12_02_Nhap ky tu vao o Ma so thue");
		hotelBooking.inputToDynamicInputBoxByID("123456", "com.VCB:id/etTaxCode");

		log.info("TC_12_03_Nhap ky tu vao o Dia chi cong ty");
		hotelBooking.inputToDynamicInputBoxByID("So 2, Lang Ha", "com.VCB:id/etCompanyAddress");

		log.info("TC_12_04_Nhap ky tu vao o Email nhan hoa don");
		hotelBooking.inputToDynamicInputBoxByID("minhducdo2603@gmail.com", "com.VCB:id/etEmail");

		log.info("TC_12_05_Click nut Gui thong tin");
		hotelBooking.clickToDynamicTextView("Gửi thông tin");

		log.info("TC_12_06_Kiem tra messge thong bao");
		verifyEquals(hotelBooking.getTextInDynamicPopup("com.VCB:id/tvContent"), HotelBooking_Data.EMPTY_HOTEL_NAME);

		log.info("TC_12_07_Click nut Dong y");
		hotelBooking.clickToDynamicTextView("Đồng ý");

		log.info("TC_12_08_Nhap ky tu vao o Ten cong ty");
		hotelBooking.inputToDynamicInputBoxByID("VNPay", "com.VCB:id/etCompanyName");

	}

	@Test
	public void TC_13_XuatHoaDon_TextBoxMaSoThue_KiemTraGiaTriHienThiMacDinh() {
		log.info("TC_13_01_Kiem tra o ten con ty hien thi mac dinh");
		hotelBooking.inputToDynamicInputBoxByID("", "com.VCB:id/etTaxCode");
		verifyTrue(hotelBooking.isDynamicInputBoxByTextDisPlayed("Mã số thuế"));

	}

	@Test
	public void TC_14_XuatHoaDon_TextBoxMaSoThue_KiemTraKyTuNhap() {
		log.info("TC_14_01_Nhap ky tu vao o Ma so thue");
		hotelBooking.inputToDynamicInputBoxByID(HotelBooking_Data.NUMBER_OVER_15_CHARACTERS, "com.VCB:id/etTaxCode");

		log.info("TC_14_02_Kiem tra ky tu nhap");
		String actualText = hotelBooking.getTextInEditTextFieldByResourceID("com.VCB:id/etTaxCode");
		verifyEquals(actualText, HotelBooking_Data.NUMBER_OVER_15_CHARACTERS.substring(0, 15));

		log.info("TC_14_03_Kiem tra so luong ky tu cho phep");
		verifyEquals(actualText.length(), 15);

	}

	@Test
	public void TC_15_XuatHoaDon_TextBoxMaSoThue_DeTrongTruongMaSoThue() {
		log.info("TC_15_01_Nhap ky tu vao o Ma so thue");
		hotelBooking.inputToDynamicInputBoxByID("", "com.VCB:id/etTaxCode");

		log.info("TC_15_02_Click nut Gui thong tin");
		hotelBooking.clickToDynamicTextView("Gửi thông tin");

		log.info("TC_15_03_Kiem tra messge thong bao");
		verifyEquals(hotelBooking.getTextInDynamicPopup("com.VCB:id/tvContent"), HotelBooking_Data.EMPTY_TAX_CODE);

		log.info("TC_15_04_Click nut Dong y");
		hotelBooking.clickToDynamicTextView("Đồng ý");

		log.info("TC_15_05_Nhap ky tu vao o Ma so thue");
		hotelBooking.inputToDynamicInputBoxByID("123456", "com.VCB:id/etTaxCode");

	}

	@Test
	public void TC_16_XuatHoaDon_TextBoxDiaChiCongTy_KiemTraGiaTriHienThiMacDinh() {
		log.info("TC_16_01_Kiem tra o Dia chi cong ty hien thi mac dinh");
		hotelBooking.inputToDynamicInputBoxByID("", "com.VCB:id/etCompanyAddress");
		verifyTrue(hotelBooking.isDynamicInputBoxByTextDisPlayed("Địa chỉ công ty"));

	}

	@Test
	public void TC_17_XuatHoaDon_TextBoxDiaChiCongTy_KiemTraKyTuNhap() {
		log.info("TC_17_01_Nhap ky tu vao o Dia chi cong ty");
		hotelBooking.inputToDynamicInputBoxByID(HotelBooking_Data.SPECIAL_TEXT_NUMBER_OVER_100_CHARACTERS, "com.VCB:id/etCompanyAddress");

		log.info("TC_17_02_Kiem tra ky tu nhap");
		String actualText = hotelBooking.getTextInEditTextFieldByResourceID("com.VCB:id/etCompanyAddress");
		verifyEquals(actualText, HotelBooking_Data.SPECIAL_TEXT_NUMBER_OVER_100_CHARACTERS.substring(0, 150));

		log.info("TC_17_03_Kiem tra so luong ky tu cho phep");
		verifyEquals(actualText.length(), 150);

	}

	@Test
	public void TC_18_XuatHoaDon_TextBoxDiaChiCongTy_DeTrongTruongDiaChiCongTy() {
		log.info("TC_18_01_Nhap ky tu vao o Dia chi cong ty");
		hotelBooking.inputToDynamicInputBoxByID("", "com.VCB:id/etCompanyAddress");

		log.info("TC_18_02_Click nut Gui thong tin");
		hotelBooking.clickToDynamicTextView("Gửi thông tin");

		log.info("TC_18_03_Kiem tra messge thong bao");
		verifyEquals(hotelBooking.getTextInDynamicPopup("com.VCB:id/tvContent"), HotelBooking_Data.EMPTY_HOTEL_ADDRESS);

		log.info("TC_18_04_Click nut Dong y");
		hotelBooking.clickToDynamicTextView("Đồng ý");

		log.info("TC_18_05_Nhap ky tu vao o Ma so thue");
		hotelBooking.inputToDynamicInputBoxByID("So 2, Lang Ha", "com.VCB:id/etCompanyAddress");

	}

	@Test
	public void TC_19_XuatHoaDon_TextBoxEmailNhanHoaDon_KiemTraGiaTriHienThiMacDinh() {
		log.info("TC_19_01_Kiem tra o Email nhan hoa don hien thi mac dinh");
		hotelBooking.inputToDynamicInputBoxByID("", "com.VCB:id/etEmail");
		verifyTrue(hotelBooking.isDynamicInputBoxByTextDisPlayed("Email nhận hóa đơn"));

	}

	@Test
	public void TC_20_XuatHoaDon_TextBoxEmailNhanHoaDon_KiemTraKyTuNhap() {
		log.info("TC_20_01_Nhap ky tu vao o Email nhan hoa don");
		hotelBooking.inputToDynamicInputBoxByID(HotelBooking_Data.SPECIAL_TEXT_NUMBER_OVER_100_CHARACTERS, "com.VCB:id/etEmail");

		log.info("TC_20_02_Kiem tra ky tu nhap");
		String actualText = hotelBooking.getTextInEditTextFieldByResourceID("com.VCB:id/etEmail");
		verifyEquals(actualText, HotelBooking_Data.SPECIAL_TEXT_NUMBER_OVER_100_CHARACTERS.substring(0, 100));

		log.info("TC_20_03_Kiem tra so luong ky tu cho phep");
		verifyEquals(actualText.length(), 100);

	}

	@Test
	public void TC_21_XuatHoaDon_TextBoxEmailNhanHoaDon_DeTrongEmailNhanHoaDon() {
		log.info("TC_21_01_Nhap ky tu vao o Email nhan hoa don");
		hotelBooking.inputToDynamicInputBoxByID("", "com.VCB:id/etEmail");

		log.info("TC_21_02_Click nut Gui thong tin");
		hotelBooking.clickToDynamicTextView("Gửi thông tin");

		log.info("TC_21_03_Kiem tra messge thong bao");
		verifyEquals(hotelBooking.getTextInDynamicPopup("com.VCB:id/tvContent"), HotelBooking_Data.EMPTY_HOTEL_ADDRESS);

		log.info("TC_21_04_Click nut Dong y");
		hotelBooking.clickToDynamicTextView("Đồng ý");

	}

	@Test
	public void TC_22_XuatHoaDon_TextBoxEmailNhanHoaDon_EmailNhanHoaDonSaiCuPhap() {
		log.info("TC_22_01_Nhap ky tu vao o Email nhan hoa don");
		hotelBooking.inputToDynamicInputBoxByID("minhducdo2603", "com.VCB:id/etEmail");

		log.info("TC_22_02_Click nut Gui thong tin");
		hotelBooking.clickToDynamicTextView("Gửi thông tin");

		log.info("TC_22_03_Kiem tra messge thong bao");
		verifyEquals(hotelBooking.getTextInDynamicPopup("com.VCB:id/tvContent"), HotelBooking_Data.NOT_VALID_EMAIL_MESSAGE);

		log.info("TC_22_04_Click nut Dong y");
		hotelBooking.clickToDynamicTextView("Đồng ý");

		log.info("TC_22_05_Nhap ky tu vao o Ma so thue");
		hotelBooking.inputToDynamicInputBoxByID("minhducdo2603@gmail.com", "com.VCB:id/etEmail");

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();
	}

}
