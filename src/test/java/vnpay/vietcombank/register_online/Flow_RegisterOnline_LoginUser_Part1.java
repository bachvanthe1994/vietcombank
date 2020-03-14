package vnpay.vietcombank.register_online;

import java.io.IOException;

import org.apache.tools.ant.taskdefs.Sleep;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import pageObjects.LogInPageObject;
import pageObjects.RegisterOnlinePageObject;
import vietcombank_test_data.Register_Online_data;

public class Flow_RegisterOnline_LoginUser_Part1 extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private RegisterOnlinePageObject registerOnline;
	String phoneNumber = "";

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
		phoneNumber = phone;
		registerOnline = PageFactoryManager.getRegisterOnlinePageObject(driver);
	}

	@Test
	public void TC_01_GiaoDichNopTienThanhCong() throws InterruptedException {
		log.info("TC_01_Step: Click menu header");
		registerOnline.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_01_Step: Click chuc nang dang ky truc tuyen");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Đăng ký trực tuyến");

		log.info("TC_01_Step: Click giao dich tien mat/chuyen tien");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Giao dịch tiền mặt/chuyển tiền");

		log.info("TC_01_Step: Click nop tien");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Nộp tiền");

		log.info("TC_01_Step: verify ten khach hang, defaul fill ten khach hang");
		verifyEquals(registerOnline.getDynamicTextInInputBoxByHeader(driver, "Thông tin khách hàng", "1"), Register_Online_data.Valid_Account.ACC_NAME);

		log.info("TC_01_Step: Click chon giay to tuy than");
		registerOnline.clickToTextViewCombobox(driver, "com.VCB:id/tvContentConfirm");

		log.info("TC_01_Step: Click chon CMT");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[0]);

		log.info("TC_01_Step: Input so ho chieu");
		registerOnline.inputIntoEditTextByID(driver, Register_Online_data.Valid_Account.NO_IDENTIFICATION, "com.VCB:id/edtIDPp");

		log.info("TC_01_Step: Click ngay cap");
		registerOnline.clickToTextViewCombobox(driver, "com.VCB:id/tvContentDate");

		log.info("TC_01_Step: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_01_Step: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(1));

		log.info("TC_01_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, "OK");
		
		log.info("TC_01_Step: Nhap email");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.EMAIL, "Giấy tờ tùy thân", "4");
		
		log.info("TC_01_Step: Click chon so tai khoan nguoi nhan");
		registerOnline.clickToTextViewCombobox(driver, "com.VCB:id/edtContent2");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.ACCOUNT_TAKE[0]);

		log.info("TTC_01_Step: Nhap so tien chuyen");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.AMOUNT, "Thông tin giao dịch", "7");

		log.info("TC_01_Step: Nhap noi dung");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.NOTE, "Thông tin giao dịch", "9");

		log.info("TC_01_Step: Click button xac nhan");
		registerOnline.clickToDynamicButton(driver, "Xác nhận");

		log.info("TC_01_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "2"), "ĐĂNG KÝ THÀNH CÔNG");

		log.info("TC_01_Step: verify text dieu kien");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "5"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_01_Step: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Chia sẻ"));

		log.info("TC_01_Step: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Lưu ảnh"));

		log.info("TC_01_Step: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, "Thực hiện đăng ký khác");
	}

@Test
	public void TC_02_GiaoDichRutTienVNDThanhCong() throws InterruptedException {
		log.info("TC_02_Step: Click rut tien");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Rút tiền");

		log.info("TC_02_Step: verify ten khach hang, defaul fill ten khach hang");
		verifyEquals(registerOnline.getDynamicTextInInputBoxByHeader(driver, "Thông tin khách hàng", "1"), Register_Online_data.Valid_Account.ACC_NAME);

		log.info("TC_02_Step: Click chon giay to tuy than");
		registerOnline.clickToTextViewCombobox(driver, "com.VCB:id/tvContentConfirm");

		log.info("TC_02_Step: Click chon ho chieu");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[0]);

		log.info("TC_02_Step: Input so ho chieu");
		registerOnline.inputIntoEditTextByID(driver, Register_Online_data.Valid_Account.NO_IDENTIFICATION, "com.VCB:id/edtIDPp");

		log.info("TC_02_Step: Click ngay cap");
		registerOnline.clickToTextViewCombobox(driver, "com.VCB:id/tvContentDate");

		log.info("TC_02_Step: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_02_Step: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(1));

		log.info("TC_02_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, "OK");

		log.info("TC_02_Step: Nhap email");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.EMAIL, "Giấy tờ tùy thân", "4");
		
		log.info("TC_02_Step: Click chon so tai khoan nguoi nhan");
		registerOnline.clickToTextViewCombobox(driver, "com.VCB:id/edtContent2");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.ACCOUNT_TAKE[1]);

		log.info("TC_02_Step: Chon loai tien la EUR");
		registerOnline.clickToDynamicImageCombobox(driver, "Thông tin giao dịch", "6");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "EUR");

		log.info("TC_02_Step: Verify loai tien la EUR");
		verifyEquals(registerOnline.getDynamicTextInDropDownByHeader(driver, "Thông tin giao dịch", "6"), "EUR");

		log.info("TC_02_Step: Nhap so tien chuyen");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.AMOUNT_USD_EUR, "Thông tin giao dịch", "6");

		log.info("TC_02_Step: Nhap noi dung");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.NOTE, "Thông tin giao dịch", "7");

		log.info("TC_02_Step: Click button xac nhan");
		registerOnline.clickToDynamicButton(driver, "Xác nhận");

		log.info("TC_02_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "2"), "ĐĂNG KÝ THÀNH CÔNG");

		log.info("TC_02_Step: verify text dieu kien");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "5"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_02_Step: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Chia sẻ"));

		log.info("TC_02_Step: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Lưu ảnh"));

		log.info("TC_02_Step: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, "Thực hiện đăng ký khác");
	}


@Test
	public void TC_03_MoTheTietKiem_TaiKhoanTienGuiCoKyHanTienMat() throws InterruptedException {
		log.info("TC_03_Step: Click giao dich Gui/rut tiết kiệm");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Gửi/rút tiết kiệm");

		log.info("TC_03_Step: Click gui tiet kiem");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Gửi tiết kiệm");

		log.info("TC_03_Step: verify ten khach hang, defaul fill ten khach hang");
		verifyEquals(registerOnline.getDynamicTextInInputBoxByHeader(driver, "Thông tin khách hàng", "2"), Register_Online_data.Valid_Account.ACC_NAME);

		log.info("TC_03_Step: Click giay to tuy than");
		registerOnline.clickToDynamicDropDown(driver, "Giấy tờ tùy thân");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[0]);

		log.info("TC_03_Step: Input so ho chieu");
		registerOnline.inputToDynamicInputText(driver, Register_Online_data.Valid_Account.NO_IDENTIFICATION, "Giấy tờ tùy thân", "5");

		log.info("TC_03_Step: Click ngay cap");
		registerOnline.clickToTextViewDate(driver, "Giấy tờ tùy thân", "6");

		log.info("TC_03_Step: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_03_Step: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(1));

		log.info("TC_03_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, "OK");
		
		log.info("TC_03_Step: Nhap noi cap");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.LOCATION[1], "Giấy tờ tùy thân", "8");

		log.info("TC_03_Step: Nhap email");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.EMAIL, "Giấy tờ tùy thân", "9");

		log.info("TC_03_Step: Chon hinh thuc gui tien co ky han");
		registerOnline.clickToDynamicImageCombobox(driver, "Thông tin giao dịch", "11");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.LIST_SEND_MONEY[0]);

		log.info("TC_03_Step: Chon ky han thoi gian");
		registerOnline.clickToDynamicImageCombobox(driver, "Thông tin giao dịch", "12");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.LIST_PERIOD[1]);

		log.info("TC_03_Step: Nhap so tien gui");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.AMOUNT, "Thông tin giao dịch", "14");

		log.info("TC_03_Step: Chon ky han thoi gian");
		registerOnline.clickToDynamicImageCombobox(driver, "Thông tin giao dịch", "15");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.INTEREST_RECEPT_TYPE[0]);

		log.info("TC_03_Step: Tien mat mac dinh chon, verify text 'Tien mat' hien thi");
		verifyEquals(registerOnline.getTextDynamicFollowLayout(driver, "com.VCB:id/llTienMat", "0"), "Tiền mặt");

		log.info("TC_03_Step: Nhap so tien nop");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.AMOUNT, "Thông tin giao dịch", "17");

		log.info("TC_03_Step: Click button xac nhan");
		registerOnline.clickToDynamicButton(driver, "Xác nhận");

		Thread.sleep(20000);
		log.info("TC_03_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "2"), "ĐĂNG KÝ THÀNH CÔNG");

		log.info("TC_03_Step: verify text dieu kien");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "5"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_03_Step: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Chia sẻ"));

		log.info("TC_03_Step: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Lưu ảnh"));

		log.info("TC_03_Step: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, "Thực hiện đăng ký khác");
	}

	@Test
	public void TC_04_MoTheTietKiem_TaiKhoanTienGuiKhongKyHan() throws InterruptedException {
		log.info("TC_04_Step: Click gui tiet kiem");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Gửi tiết kiệm");

		log.info("TC_04_Step: verify ten khach hang, defaul fill ten khach hang");
		verifyEquals(registerOnline.getDynamicTextInInputBoxByHeader(driver, "Thông tin khách hàng", "2"), Register_Online_data.Valid_Account.ACC_NAME);

		log.info("TC_04_Step: Click giay to tuy than");
		registerOnline.clickToDynamicDropDown(driver, "Giấy tờ tùy thân");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[0]);

		log.info("TC_04_Step: Input so ho chieu");
		registerOnline.inputToDynamicInputText(driver, Register_Online_data.Valid_Account.NO_IDENTIFICATION, "Giấy tờ tùy thân", "5");

		log.info("TC_04_Step: Click ngay cap");
		registerOnline.clickToTextViewDate(driver, "Giấy tờ tùy thân", "6");

		log.info("TC_04_Step: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_04_Step: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(1));

		log.info("TC_04_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, "OK");

		log.info("TC_04_Step: Nhap noi cap");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.LOCATION[1], "Giấy tờ tùy thân", "8");

		log.info("TC_04_Step: Nhap email");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.EMAIL, "Giấy tờ tùy thân", "9");

		log.info("TC_04_Step: Chon hinh thuc gui tien co ky han");
		registerOnline.clickToDynamicImageCombobox(driver, "Thông tin giao dịch", "11");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.LIST_SEND_MONEY[2]);

		log.info("TC_04_Step: Chon ky han thoi gian");
		registerOnline.clickToDynamicImageCombobox(driver, "Thông tin giao dịch", "12");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.LIST_PERIOD[0]);

		log.info("TC_04_Step: Nhap so tien gui");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.AMOUNT, "Thông tin giao dịch", "13");

		log.info("TC_04_Step: Chon ky han thoi gian");
		registerOnline.clickToDynamicImageCombobox(driver, "Thông tin giao dịch", "15");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.INTEREST_RECEPT_TYPE[1]);

		log.info("TC_04_Step: Tien mat mac dinh chon, verify text 'Tien mat' hien thi");
		verifyEquals(registerOnline.getTextDynamicFollowLayout(driver, "com.VCB:id/llTienMat", "0"), "Tiền mặt");

		log.info("TC_04_Step: Nhap so tien nop");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.AMOUNT, "Thông tin giao dịch", "16");

		log.info("TC_04_Step: Click button xac nhan");
		registerOnline.clickToDynamicButton(driver, "Xác nhận");

		Thread.sleep(5000);
		log.info("TC_04_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "2"), "ĐĂNG KÝ THÀNH CÔNG");

		log.info("TC_04_Step: verify text dieu kien");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "5"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_04_Step: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Chia sẻ"));

		log.info("TC_04_Step: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Lưu ảnh"));

		log.info("TC_04_Step: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, "Thực hiện đăng ký khác");
	}

	@Test
	public void TC_05_MoTheTietKiem_TaiKhoanTienGuiCoKyHanChuyenKhoan() throws InterruptedException {
		log.info("TC_05_Step: Click gui tiet kiem");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Gửi tiết kiệm");

		log.info("TC_05_Step: verify ten khach hang, defaul fill ten khach hang");
		verifyEquals(registerOnline.getDynamicTextInInputBoxByHeader(driver, "Thông tin khách hàng", "2"), Register_Online_data.Valid_Account.ACC_NAME);

		log.info("TC_05_Step: Click giay to tuy than");
		registerOnline.clickToDynamicDropDown(driver, "Giấy tờ tùy thân");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[0]);

		log.info("TC_05_Step: Input so ho chieu");
		registerOnline.inputToDynamicInputText(driver, Register_Online_data.Valid_Account.NO_IDENTIFICATION, "Giấy tờ tùy thân", "5");

		log.info("TC_05_Step: Click ngay cap");
		registerOnline.clickToTextViewDate(driver, "Giấy tờ tùy thân", "6");

		log.info("TC_05_Step: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_05_Step: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(1));

		log.info("TC_05_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, "OK");

		log.info("TC_05_Step: Nhap noi cap");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.LOCATION[1], "Giấy tờ tùy thân", "8");

		log.info("TC_05_Step: Nhap email");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.EMAIL, "Giấy tờ tùy thân", "9");


		log.info("TC_05_Step: Chon hinh thuc gui tien co ky han");
		registerOnline.clickToDynamicImageCombobox(driver, "Thông tin giao dịch", "11");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.LIST_SEND_MONEY[0]);

		log.info("TC_05_Step: Chon ky han thoi gian");
		registerOnline.clickToDynamicImageCombobox(driver, "Thông tin giao dịch", "12");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.LIST_PERIOD[1]);

		log.info("TC_05_Step: Nhap so tien gui");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.AMOUNT, "Thông tin giao dịch", "14");

		log.info("TC_05_Step: Chon ky han thoi gian");
		registerOnline.clickToDynamicImageCombobox(driver, "Thông tin giao dịch", "15");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.INTEREST_RECEPT_TYPE[0]);

		log.info("TC_05_Step: Click chon chuyen khoan");
		registerOnline.clickToImageRadio(driver, "Chuyển khoản");

		log.info("TC_05_Step: Click bo chon mac dinh tien mat");
		registerOnline.clickToImageRadio(driver, "Tiền mặt");

		log.info("TC_05_Step: check text da chon la chuyen khoan");
		verifyEquals(registerOnline.getTextDynamicFollowLayout(driver, "com.VCB:id/llChuyenKhoan", "0"), "Chuyển khoản");

		log.info("TC_05_Step: Nhap so tai khoan");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.ACCOUNT_TAKE[1], "Chuyển khoản", "1");

		log.info("TC_05_Step: Nhap so tien nop");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.AMOUNT, "Chuyển khoản", "2");

		log.info("TC_05_Step: Click button xac nhan");
		registerOnline.clickToDynamicButton(driver, "Xác nhận");

		Thread.sleep(5000);
		log.info("TC_05_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "2"), "ĐĂNG KÝ THÀNH CÔNG");

		log.info("TC_05_Step: verify text dieu kien");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "5"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_05_Step: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Chia sẻ"));

		log.info("TC_05_Step: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Lưu ảnh"));

		log.info("TC_05_Step: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, "Thực hiện đăng ký khác");
	}

	@Test
	public void TC_06_MoTheTietKiem_TheTietKiemCoKyHanChuyenKhoanVaTienMatNhanGoc() throws InterruptedException {
		
		log.info("TC_06_Step: Click gui tiet kiem");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Gửi tiết kiệm");

		log.info("TC_06_Step: verify ten khach hang, defaul fill ten khach hang");
		verifyEquals(registerOnline.getDynamicTextInInputBoxByHeader(driver, "Thông tin khách hàng", "2"), Register_Online_data.Valid_Account.ACC_NAME);

		log.info("TC_06_Step: Click giay to tuy than");
		registerOnline.clickToDynamicDropDown(driver, "Giấy tờ tùy thân");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[0]);

		log.info("TC_06_Step: Input so ho chieu");
		registerOnline.inputToDynamicInputText(driver, Register_Online_data.Valid_Account.NO_IDENTIFICATION, "Giấy tờ tùy thân", "5");

		log.info("TC_06_Step: Click ngay cap");
		registerOnline.clickToTextViewDate(driver, "Giấy tờ tùy thân", "6");

		log.info("TC_06_Step: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_06_Step: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(1));

		log.info("TC_06_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, "OK");

		log.info("TC_06_Step: Nhap noi cap");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.LOCATION[1], "Giấy tờ tùy thân", "8");

		log.info("TC_06_Step: Nhap email");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.EMAIL, "Giấy tờ tùy thân", "9");

		log.info("TC_06_Step: Chon hinh thuc gui tien co ky han");
		registerOnline.clickToDynamicImageCombobox(driver, "Thông tin giao dịch", "11");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.LIST_SEND_MONEY[0]);

		log.info("TC_06_Step: Chon ky han thoi gian");
		registerOnline.clickToDynamicImageCombobox(driver, "Thông tin giao dịch", "12");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.LIST_PERIOD[1]);

		log.info("TC_06_Step: Nhap so tien gui");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.AMOUNT_OPEN_CARD, "Thông tin giao dịch", "14");

		log.info("TC_06_Step: Chon ky han thoi gian");
		registerOnline.clickToDynamicImageCombobox(driver, "Thông tin giao dịch", "15");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.INTEREST_RECEPT_TYPE[2]);

		log.info("TC_06_Step: Click chon chuyen khoan");
		registerOnline.clickToImageRadio(driver, "Chuyển khoản");

		log.info("TC_06_Step: Nhap so tai khoan nhan lai");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.ACCOUNT_TAKE[0], "Thông tin giao dịch", "16");

		log.info("TC_06_Step: check text mac dinh chon la tien mat");
		verifyEquals(registerOnline.getTextDynamicFollowLayout(driver, "com.VCB:id/llTienMat", "0"), "Tiền mặt");

		log.info("TC_06_Step: check text da chon la chuyen khoan");
		verifyEquals(registerOnline.getTextDynamicFollowLayout(driver, "com.VCB:id/llChuyenKhoan", "0"), "Chuyển khoản");

		log.info("TC_06_Step: Nhap so tien mat nop");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.AMOUNT, "Tiền mặt", "1");

		log.info("TC_06_Step: Nhap so tai khoan");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.ACCOUNT_TAKE[1], "Chuyển khoản", "1");

		log.info("TC_06_Step: Nhap so tien nop");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.AMOUNT, "Chuyển khoản", "2");

		log.info("TC_06_Step: Click button xac nhan");
		registerOnline.clickToDynamicButton(driver, "Xác nhận");

		Thread.sleep(5000);
		log.info("TC_06_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "2"), "ĐĂNG KÝ THÀNH CÔNG");

		log.info("TC_06_Step: verify text dieu kien");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "5"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_06_Step: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Chia sẻ"));

		log.info("TC_06_Step: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Lưu ảnh"));

		log.info("TC_06_Step: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, "Thực hiện đăng ký khác");

	}

	@Test
	public void TC_07_MoTheTietKiem_TheTietKiemKhongKyHanTienMatNhanGoc() throws InterruptedException {
		log.info("TC_07_Step: Click gui tiet kiem");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Gửi tiết kiệm");

		log.info("TC_07_Step: verify ten khach hang, defaul fill ten khach hang");
		verifyEquals(registerOnline.getDynamicTextInInputBoxByHeader(driver, "Thông tin khách hàng", "2"), Register_Online_data.Valid_Account.ACC_NAME);

		log.info("TC_07_Step: Click giay to tuy than");
		registerOnline.clickToDynamicDropDown(driver, "Giấy tờ tùy thân");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[0]);

		log.info("TC_07_Step: Input so ho chieu");
		registerOnline.inputToDynamicInputText(driver, Register_Online_data.Valid_Account.NO_IDENTIFICATION, "Giấy tờ tùy thân", "5");

		log.info("TC_07_Step: Click ngay cap");
		registerOnline.clickToTextViewDate(driver, "Giấy tờ tùy thân", "6");

		log.info("TC_07_Step: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_07_Step: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(1));

		log.info("TC_07_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, "OK");

		log.info("TC_07_Step: Nhap noi cap");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.LOCATION[1], "Giấy tờ tùy thân", "8");

		log.info("TC_07_Step: Nhap email");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.EMAIL, "Giấy tờ tùy thân", "9");

		log.info("TC_07_Step: Chon hinh thuc gui tien khong ky han");
		registerOnline.clickToDynamicImageCombobox(driver, "Thông tin giao dịch", "11");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.LIST_SEND_MONEY[2]);

		log.info("TC_07_Step: Chon ky han thoi gian");
		registerOnline.clickToDynamicImageCombobox(driver, "Thông tin giao dịch", "12");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.LIST_PERIOD[1]);

		log.info("TC_07_Step: Nhap so tien gui");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.AMOUNT, "Thông tin giao dịch", "13");

		log.info("TC_07_Step: Chon ky han thoi gian");
		registerOnline.clickToDynamicImageCombobox(driver, "Thông tin giao dịch", "14");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.INTEREST_RECEPT_TYPE[0]);

		log.info("TC_07_Step: check text mac dinh chon la tien mat");
		verifyEquals(registerOnline.getTextDynamicFollowLayout(driver, "com.VCB:id/llTienMat", "0"), "Tiền mặt");

		log.info("TC_07_Step: Nhap so tien nop");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.AMOUNT, "Thông tin giao dịch", "16");

		log.info("TC_07_Step: Click button xac nhan");
		registerOnline.clickToDynamicButton(driver, "Xác nhận");

		Thread.sleep(5000);
		log.info("TC_07_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "2"), "ĐĂNG KÝ THÀNH CÔNG");

		log.info("TC_07_Step: verify text dieu kien");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "5"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_07_Step: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Chia sẻ"));

		log.info("TC_07_Step: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Lưu ảnh"));
		Thread.sleep(2000);

		log.info("TC_07_Step: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, "Thực hiện đăng ký khác");

	}

	@Test
	public void TC_08_MoTheTietKiem_TaiKhoanTienGuiCoKyHanTienMatNhanGoc() throws InterruptedException {
		log.info("TC_08_Step: Click gui tiet kiem");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Gửi tiết kiệm");

		log.info("TC_08_Step: verify ten khach hang, defaul fill ten khach hang");
		verifyEquals(registerOnline.getDynamicTextInInputBoxByHeader(driver, "Thông tin khách hàng", "2"), Register_Online_data.Valid_Account.ACC_NAME);

		log.info("TC_08_Step: Click giay to tuy than");
		registerOnline.clickToDynamicDropDown(driver, "Giấy tờ tùy thân");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[0]);

		log.info("TC_08_Step: Input so ho chieu");
		registerOnline.inputToDynamicInputText(driver, Register_Online_data.Valid_Account.NO_IDENTIFICATION, "Giấy tờ tùy thân", "5");

		log.info("TC_08_Step: Click ngay cap");
		registerOnline.clickToTextViewDate(driver, "Giấy tờ tùy thân", "6");

		log.info("TC_08_Step: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_08_Step: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(1));

		log.info("TC_08_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, "OK");

		log.info("TC_08_Step: Nhap noi cap");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.LOCATION[1], "Giấy tờ tùy thân", "8");

		log.info("TC_08_Step: Nhap email");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.EMAIL, "Giấy tờ tùy thân", "9");

		log.info("TC_08_Step: Chon hinh thuc gui tien khong ky han");
		registerOnline.clickToDynamicImageCombobox(driver, "Thông tin giao dịch", "11");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.LIST_SEND_MONEY[2]);

		log.info("TC_08_Step: Chon ky han thoi gian");
		registerOnline.clickToDynamicImageCombobox(driver, "Thông tin giao dịch", "12");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.LIST_PERIOD[1]);

		log.info("TC_08_Step: Nhap so tien gui");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.AMOUNT, "Thông tin giao dịch", "13");

		log.info("TC_08_Step: Chon ky han thoi gian");
		registerOnline.clickToDynamicImageCombobox(driver, "Thông tin giao dịch", "14");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.INTEREST_RECEPT_TYPE[0]);

		log.info("TC_08_Step: check text mac dinh chon la tien mat");
		verifyEquals(registerOnline.getTextDynamicFollowLayout(driver, "com.VCB:id/llTienMat", "0"), "Tiền mặt");

		log.info("TC_08_Step: Nhap so tien nop");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.AMOUNT, "Thông tin giao dịch", "16");

		log.info("TC_08_Step: Click button xac nhan");
		registerOnline.clickToDynamicButton(driver, "Xác nhận");

		Thread.sleep(5000);
		log.info("TC_08_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "2"), "ĐĂNG KÝ THÀNH CÔNG");

		log.info("TC_08_Step: verify text dieu kien");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "5"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_08_Step: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Chia sẻ"));

		log.info("TC_08_Step: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Lưu ảnh"));
		Thread.sleep(2000);

		log.info("TC_08_Step: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, "Thực hiện đăng ký khác");

	}

	@Test
	public void TC_09_RutSoTietKiem_RutLaiPhuongThucTienMat() throws InterruptedException {
		log.info("TC_09_Step: Click rut tiet kiem");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Rút tiết kiệm");

		log.info("TC_09_Step: verify ten khach hang, defaul fill ten khach hang");
		verifyEquals(registerOnline.getDynamicTextInInputBoxByHeader(driver, "Thông tin khách hàng", "2"), Register_Online_data.Valid_Account.ACC_NAME);

		log.info("TC_09_Step: Click giay to tuy than");
		registerOnline.clickToDynamicDropDown(driver, "Giấy tờ tùy thân");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[0]);

		log.info("TC_09_Step: Input so ho chieu");
		registerOnline.inputToDynamicInputText(driver, Register_Online_data.Valid_Account.NO_IDENTIFICATION, "Giấy tờ tùy thân", "5");

		log.info("TC_09_Step: Click ngay cap");
		registerOnline.clickToTextViewDate(driver, "Giấy tờ tùy thân", "6");

		log.info("TC_09_Step: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_09_Step: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(1));

		log.info("TC_09_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, "OK");

		log.info("TC_09_Step: Nhap email");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.EMAIL, "Giấy tờ tùy thân", "8");

		log.info("TC_09_Step: Nhap so tai khoan ghi no");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.ACCOUNT_TAKE[1], "Thông tin giao dịch", "10");

		log.info("TC_09_Step: Click chọn rut lai");
		registerOnline.clickToDynamicImageCombobox(driver, "Thông tin giao dịch", "11");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_RETURN_MONEY[0]);

		log.info("TC_09_Step: Nhap so tien");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.AMOUNT, "Thông tin giao dịch", "12");

		log.info("TC_09_Step: Nhap noi dung");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.NOTE, "Thông tin giao dịch", "13");

		log.info("TC_09_Step: check text mac dinh chon la tien mat");
		verifyEquals(registerOnline.getTextDynamicFollowLayout(driver, "com.VCB:id/llTienMat", "0"), "Tiền mặt");

		log.info("TC_09_Step: Nhap so tien nhan");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.AMOUNT, "Thông tin giao dịch", "15");

		log.info("TC_09_Step: Click button xac nhan");
		registerOnline.clickToDynamicButton(driver, "Xác nhận");

		Thread.sleep(5000);
		log.info("TC_09_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "2"), "ĐĂNG KÝ THÀNH CÔNG");

		log.info("TC_09_Step: verify text dieu kien");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "5"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_09_Step: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Chia sẻ"));

		log.info("TC_09_Step: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Lưu ảnh"));
		Thread.sleep(2000);

		log.info("TC_09_Step: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, "Thực hiện đăng ký khác");
	}

	@Test
	public void TC_10_RutSoTietKiem_RutGocPhuongThucChuyenKhoanVaTienMat() throws InterruptedException {
	
		log.info("TC_10_Step: Click rut tiet kiem");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Rút tiết kiệm");

		log.info("TC_10_Step: verify ten khach hang, defaul fill ten khach hang");
		verifyEquals(registerOnline.getDynamicTextInInputBoxByHeader(driver, "Thông tin khách hàng", "2"), Register_Online_data.Valid_Account.ACC_NAME);

		log.info("TC_10_Step: Click giay to tuy than");
		registerOnline.clickToDynamicDropDown(driver, "Giấy tờ tùy thân");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[0]);

		log.info("TC_10_Step: Input so ho chieu");
		registerOnline.inputToDynamicInputText(driver, Register_Online_data.Valid_Account.NO_IDENTIFICATION, "Giấy tờ tùy thân", "5");

		log.info("TC_10_Step: Click ngay cap");
		registerOnline.clickToTextViewDate(driver, "Giấy tờ tùy thân", "6");

		log.info("TC_10_Step: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_10_Step: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(1));

		log.info("TC_10_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, "OK");

		log.info("TC_10_Step: Nhap email");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.EMAIL, "Giấy tờ tùy thân", "8");

		log.info("TC_10_Step: Nhap so tai khoan ghi no");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.ACCOUNT_TAKE[1], "Thông tin giao dịch", "10");

		log.info("TC_10_Step: Click chọn rut goc");
		registerOnline.clickToDynamicImageCombobox(driver, "Thông tin giao dịch", "11");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_RETURN_MONEY[1]);

		log.info("TC_10_Step: Nhap so tien");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.AMOUNT_OPEN_CARD, "Thông tin giao dịch", "12");

		log.info("TC_10_Step: Nhap noi dung");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.NOTE, "Thông tin giao dịch", "13");

		log.info("TC_10_Step: Click chon chuyen khoan");
		registerOnline.clickToImageRadio(driver, "Chuyển khoản");

		log.info("TC_10_Step: check text mac dinh chon la tien mat");
		verifyEquals(registerOnline.getTextDynamicFollowLayout(driver, "com.VCB:id/llTienMat", "0"), "Tiền mặt");

		log.info("TC_10_Step: check text da chon la chuyen khoan");
		verifyEquals(registerOnline.getTextDynamicFollowLayout(driver, "com.VCB:id/llChuyenKhoan", "0"), "Chuyển khoản");

		log.info("TC_10_Step: Nhap so tien mat nop");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.AMOUNT, "Tiền mặt", "1");

		log.info("TC_10_Step: Nhap so tai khoan");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.ACCOUNT_TAKE[1], "Chuyển khoản", "1");

		log.info("TC_10_Step: Nhap ten chu tai khoan");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.ACC_NAME, "Chuyển khoản", "2");

		log.info("TC_10_Step: Nhap so tien nop");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.AMOUNT, "Chuyển khoản", "3");

		log.info("TC_10_Step: Click button xac nhan");
		registerOnline.clickToDynamicButton(driver, "Xác nhận");

		Thread.sleep(5000);
		log.info("TC_10_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "2"), "ĐĂNG KÝ THÀNH CÔNG");

		log.info("TC_10_Step: verify text dieu kien");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "5"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_10_Step: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Chia sẻ"));

		log.info("TC_10_Step: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Lưu ảnh"));
		Thread.sleep(2000);

		log.info("TC_10_Step: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, "Thực hiện đăng ký khác");

	}

	@Test
	public void TC_11_MoTaiKhoanThanhToan_ManHinhThongTinKhachHang() throws InterruptedException {
		log.info("TC_11_Step: Click giao dich mo tai khoan thanh toan");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Mở tài khoản thanh toán");

		log.info("TC_11_Step: verify ten khach hang, defaul fill ten khach hang");
		verifyEquals(registerOnline.getDynamicTextInInputBoxByHeader(driver, "Thông tin khách hàng", "1"), Register_Online_data.Valid_Account.ACC_NAME);

		log.info("TC_11_Step: Click giay to tuy than");
		registerOnline.clickToDynamicDropDown(driver, "Giấy tờ tùy thân");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[0]);

		log.info("TC_11_Step: Input so ho chieu");
		registerOnline.inputToDynamicInputText(driver, Register_Online_data.Valid_Account.NO_IDENTIFICATION, "Giấy tờ tùy thân", "6");

		log.info("TC_11_Step: Click ngay cap");
		registerOnline.clickToTextViewDate(driver, "Giấy tờ tùy thân", "7");

		log.info("TC_11_Step: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_11_Step: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(1));

		log.info("TC_11_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, "OK");

		log.info("TC_11_Step: verify so account");
		System.out.print(registerOnline.getTextDynamicFollowIndex(driver, "Giấy tờ tùy thân", "10"));
		verifyEquals(registerOnline.getTextDynamicFollowIndex(driver, "Giấy tờ tùy thân", "10"), phoneNumber);

		log.info("TC_11_Step: Input email");
		registerOnline.inputToDynamicInputText(driver, Register_Online_data.Valid_Account.EMAIL, "Giấy tờ tùy thân", "11");

		log.info("TC_11_Step: Click button xac nhan");
		registerOnline.clickToDynamicButton(driver, "Xác nhận");

	}

@Test
	public void TC_12_MoTaiKhoanThanhToan_ManHinhDangKyDichVuDienTu() throws InterruptedException {
		log.info("TC_12_Step: Verify text man hinh dang ky dich vu ngan hang dien tu");
		verifyEquals(registerOnline.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvTitleBar"), "Đăng ký dịch vụ ngân hàng điện tử");

		log.info("TC_12_Step: Select option VCB-iB@nKing");
		registerOnline.clickToDynamicTextIndex(driver, "0", "VCB-iB@nking");

		log.info("TC_12_Step: Select option VCB-SMS B@nking");
		registerOnline.clickToDynamicTextIndex(driver, "1", "VCB-SMS B@nking");

		log.info("TC_12_Step: Nhap so dien thoai");
		registerOnline.inputToDynamicInputBoxByHeader(driver, phoneNumber, "VCB-SMS B@nking", "1");
		registerOnline.clickToDynamicImageViewByID(driver, "com.VCB:id/ivAdd");

		log.info("TC_12_Step: Select option VCB-SMS B@nking");
		registerOnline.clickToDynamicTextIndex(driver, "2", "VCB-Mobile B@nking");

		log.info("TC_12_Step: Chon so dien thoai dang ky");
		registerOnline.clickToDynamicComboboxText(driver, "VCB-Mobile B@nking", "1");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, phoneNumber);

		log.info("TC_12_Step: Click button tiep tuc");
		registerOnline.clickToDynamicButton(driver, "Tiếp tục");
	}

	@Test
	public void TC_13_MoTaiKhoanThanhToan_ManHinhConnect24_ChonMotThePhu() throws InterruptedException {
		log.info("TC_13_Step: Verify text man hinh dang ky phat hanh the connect24");
		verifyEquals(registerOnline.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvTitleBar"), "Đăng ký phát hành thẻ Connect24");

		log.info("TC_13_Step: Click hang the, chon hang the la vang");
		registerOnline.clickToTextViewDate(driver, "Hạng thẻ", "1");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.CARD_RANK[1]);

		log.info("TC_13_Step: Click hang the, chon hang the la vang");
		registerOnline.clickToTextViewDate(driver, "Số lượng thẻ chính 01", "3");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.FEE_PAYMENT[0]);

		log.info("TC_13_Step: Select option yeu cau phat hanh the phu");
		registerOnline.clickToDynamicTextIndex(driver, "4", "Yêu cầu phát hành thẻ phụ");

		log.info("TC_13_Step: Nhap ho ten chu the");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.ACC_NAME, "Chủ thẻ phụ 1", "1");

		log.info("TC_13_Step: Click giay to tuy than");
		registerOnline.clickToDynamicDropDown(driver, "Giấy tờ tùy thân");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[0]);

		log.info("TC_13_Step: Input so ho chieu");
		registerOnline.inputToDynamicInputText(driver, Register_Online_data.Valid_Account.NO_IDENTIFICATION, "Giấy tờ tùy thân", "3");

		log.info("TC_13_Step: Click ngay cap");
		registerOnline.clickToTextViewDate(driver, "Giấy tờ tùy thân", "4");

		log.info("TC_13_Step: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_13_Step: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(1));

		log.info("TC_13_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, "OK");

		log.info("TC_13_Step: Click noi cap");
		registerOnline.clickToTextViewDate(driver, "Giấy tờ tùy thân", "5");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.LOCATION[0]);

		log.info("TC_13_Step: Click tiep tuc");
		registerOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_13_Step: Click radio toi dong y");
		registerOnline.clickToDynamicRadioIndex(driver, "agreement", "3");

		log.info("TC_13_Step: Click dong y");
		registerOnline.clickToDynamicDateInDateTimePicker(driver, "Đồng ý");
		Thread.sleep(20000);

		log.info("TC_13_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "2"), "ĐĂNG KÝ THÀNH CÔNG");

		log.info("TC_13_Step: verify text dieu kien");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "5"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_13_Step: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Chia sẻ"));

		log.info("TC_13_Step: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Lưu ảnh"));
		Thread.sleep(2000);

		log.info("TC_13_Step: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, "Thực hiện đăng ký khác");

	}

	@Test
	public void TC_14_MoTaiKhoanThanhToan_ManHinhConnect24_ChonHaiThePhu() throws InterruptedException {
		log.info("TC_14_Step: Click giao dich mo tai khoan thanh toan");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Mở tài khoản thanh toán");

		log.info("TC_14_Step: verify ten khach hang, defaul fill ten khach hang");
		verifyEquals(registerOnline.getDynamicTextInInputBoxByHeader(driver, "Thông tin khách hàng", "1"), Register_Online_data.Valid_Account.ACC_NAME);

		log.info("TC_14_Step: Click giay to tuy than");
		registerOnline.clickToDynamicDropDown(driver, "Giấy tờ tùy thân");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[0]);

		log.info("TC_14_Step: Click ngay cap");
		registerOnline.clickToTextViewDate(driver, "Giấy tờ tùy thân", "7");

		log.info("TC_14_Step: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_14_Step: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(1));

		log.info("TC_14_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, "OK");

		log.info("TC_14_Step: Input noi cap");
		registerOnline.inputToDynamicInputText(driver, Register_Online_data.Valid_Account.LOCATION[0], "Giấy tờ tùy thân", "8");

		log.info("TC_14_Step: verify so account");
		System.out.print(registerOnline.getTextDynamicFollowIndex(driver, "Giấy tờ tùy thân", "10"));
		verifyEquals(registerOnline.getTextDynamicFollowIndex(driver, "Giấy tờ tùy thân", "10"), phoneNumber);

		log.info("TC_14_Step: Nhap email");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.EMAIL, "Giấy tờ tùy thân", "11");

		log.info("TC_14_Step: Click button xac nhan");
		registerOnline.clickToDynamicButton(driver, "Xác nhận");

		log.info("TC_14_Step: Verify text man hinh dang ky dich vu ngan hang dien tu");
		verifyEquals(registerOnline.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvTitleBar"), "Đăng ký dịch vụ ngân hàng điện tử");

		log.info("TC_14_Step: Select option VCB-iB@nKing");
		registerOnline.clickToDynamicTextIndex(driver, "0", "VCB-iB@nking");

		log.info("TC_14_Step: Select option VCB-SMS B@nking");
		registerOnline.clickToDynamicTextIndex(driver, "1", "VCB-SMS B@nking");

		log.info("TC_14_Step: Nhap so dien thoai");
		registerOnline.inputToDynamicInputBoxByHeader(driver, phoneNumber, "VCB-SMS B@nking", "1");
		registerOnline.clickToDynamicImageViewByID(driver, "com.VCB:id/ivAdd");

		log.info("TC_14_Step: Select option VCB-SMS B@nking");
		registerOnline.clickToDynamicTextIndex(driver, "2", "VCB-Mobile B@nking");

		log.info("TC_14_Step: Chon so dien thoai dang ky");
		registerOnline.clickToDynamicComboboxText(driver, "VCB-Mobile B@nking", "1");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, phoneNumber);

		log.info("TC_14_Step: Click button tiep tuc");
		registerOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_14_Step: Verify text man hinh dang ky phat hanh the connect24");
		verifyEquals(registerOnline.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvTitleBar"), "Đăng ký phát hành thẻ Connect24");

		log.info("TC_14_Step: Click hang the, chon hang the la vang");
		registerOnline.clickToTextViewDate(driver, "Hạng thẻ", "1");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.CARD_RANK[1]);

		log.info("TC_14_Step: Click hang the, chon hang the la vang");
		registerOnline.clickToTextViewDate(driver, "Số lượng thẻ chính 01", "3");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.FEE_PAYMENT[0]);

		log.info("TC_14_Step: Select option yeu cau phat hanh the phu");
		registerOnline.clickToDynamicTextIndex(driver, "4", "Yêu cầu phát hành thẻ phụ");

		log.info("TC_14_Step: Select option yeu cau phat hanh the phu");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "02 thẻ");

		log.info("TC_14_Step: Nhap ho ten chu the");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.ACC_NAME, "Chủ thẻ phụ 1", "1");

		log.info("TC_14_Step: Click giay to tuy than");
		registerOnline.clickToDynamicDropDown(driver, "Giấy tờ tùy thân");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[0]);

		log.info("TC_14_Step: Input so ho chieu");
		registerOnline.inputToDynamicInputText(driver, Register_Online_data.Valid_Account.NO_IDENTIFICATION, "Giấy tờ tùy thân", "3");

		log.info("TC_14_Step: Click ngay cap");
		registerOnline.clickToTextViewDate(driver, "Giấy tờ tùy thân", "4");

		log.info("TC_14_Step: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_14_Step: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(1));

		log.info("TC_14_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, "OK");

		log.info("TC_13_Step: Click noi cap");
		registerOnline.clickToTextViewDate(driver, "Giấy tờ tùy thân", "5");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.LOCATION[0]);

		log.info("TC_14_Step: Nhap ho ten chu the");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.CUSTOMER_NAME_ACCEPT, "Chủ thẻ phụ 2", "1");

		log.info("TC_14_Step: Click giay to tuy than");
		registerOnline.clickToDynamicComboboxText(driver, "Chủ thẻ phụ 2", "3");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[0]);

		log.info("TC_14_Step: Input so ho chieu");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.NO_IDENTIFICATION, "Chủ thẻ phụ 2", "3");

		log.info("TC_14_Step: Click ngay cap");
		registerOnline.clickToDynamicComboboxText(driver, "Chủ thẻ phụ 2", "4");

		log.info("TC_14_Step: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_14_Step: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(1));

		log.info("TC_14_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, "OK");
		
		log.info("TC_13_Step: Click noi cap");
		registerOnline.clickToTextViewDate(driver, "Chủ thẻ phụ 2", "5");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.LOCATION[0]);

		log.info("TC_14_Step: Click tiep tuc");
		registerOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_14_Step: Click radio toi dong y");
		registerOnline.clickToDynamicRadioIndex(driver, "agreement", "3");

		log.info("TC_14_Step: Click dong y");
		registerOnline.clickToDynamicDateInDateTimePicker(driver, "Đồng ý");
		Thread.sleep(10000);

		log.info("TC_14_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "2"), "ĐĂNG KÝ THÀNH CÔNG");

		log.info("TC_14_Step: verify text dieu kien");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "5"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_14_Step: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Chia sẻ"));

		log.info("TC_14_Step: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Lưu ảnh"));
		Thread.sleep(2000);

		log.info("TC_14_Step: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, "Thực hiện đăng ký khác");

	}
}
