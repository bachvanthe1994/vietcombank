package vnpay.vietcombank.register_online;

import java.io.IOException;

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

public class Flow_RegisterOnline_DoNotLoginUser extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private RegisterOnlinePageObject registerOnline;

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		registerOnline = PageFactoryManager.getRegisterOnlinePageObject(driver);

		registerOnline.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");
	}

	@Test
	public void TC_01_GiaoDichChuyenTienTrongVietComBankThanhCong() throws InterruptedException {
		log.info("TC_01_Step: Click dang ky truc tuyen");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Đăng ký trực tuyến");

		log.info("TC_01_Step: Click giao dich tien mat/chuyen tien");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Giao dịch tiền mặt/chuyển tiền");

		log.info("TC_01_Step: Click chuyen tien");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền");

		log.info("TC_01_Step: Nhap thong tin khach hang");
		registerOnline.inputIntoEditTextByID(driver, Register_Online_data.Valid_Account.ACC_NAME, "com.VCB:id/edtFullName");

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
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(5));

		log.info("TC_01_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, "OK");

		log.info("TC_1_Step: Click noi cap");
		registerOnline.clickToDynamicComboboxText(driver, "Giấy tờ tùy thân", "3");
		registerOnline.inputIntoEditTextByID(driver, Register_Online_data.Valid_Account.LOCATION[0], "com.VCB:id/edtSearch");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.LOCATION[0]);
		
		log.info("TC_01_Step: Nhap email");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.EMAIL, "Loại xác nhận", "4");
		
		log.info("TC_01_Step: Nhap so tai khoan nhan");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.ACCOUNT_TAKE[0], "Thông tin tài khoản ghi nợ", "1");

		log.info("TC_01_Step: Nhap ten chu tai khoan");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.ACC_NAME, "Thông tin tài khoản ghi nợ", "2");

		log.info("TC_01_Step: Nhap so tai khoan phan ghi co");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.ACCOUNT_TAKE[1], "Thông tin tài khoản ghi có", "5");

		log.info("TC_01_Step: Nhap ten tai khoan phan ghi co");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.CUSTOMER_NAME_ACCEPT, "Thông tin tài khoản ghi có", "6");

		log.info("TC_01_Step: Nhap so tien chuyen");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.AMOUNT, "Thông tin tài khoản ghi có", "8");

		log.info("TC_01_Step: Nhap noi dung");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.NOTE, "Thông tin tài khoản ghi có", "9");

		log.info("TC_01_Step: Click button xac nhan");
		registerOnline.clickToDynamicButton(driver, "Xác nhận");

		Thread.sleep(5000);
		log.info("TC_01_Step: verify man hinh dang ky thanh cong");
		System.out.print(registerOnline.getTextDynamicFollowImageIndex(driver, "0","1"));
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "0","1"), "ĐĂNG KÝ THÀNH CÔNG");
		
		log.info("TC_01_Step: verify text dieu kien");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "0","4"),  Register_Online_data.Message.MESSAGE_SUCCESS);
		
		log.info("TC_01_Step: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Chia sẻ"));
		
		log.info("TC_01_Step: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Lưu ảnh"));

		log.info("TC_01_Step: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, "Thực hiện đăng ký khác");
	}

	@Test
	public void TC_02_GiaoDichChuyenTienNgoaiVietComBankThanhCong() throws InterruptedException {
		log.info("TC_02_Step: Click chuyen tien");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền");

		log.info("TC_02_Step: Nhap thong tin khach hang");
		registerOnline.inputIntoEditTextByID(driver, Register_Online_data.Valid_Account.ACC_NAME, "com.VCB:id/edtFullName");

		log.info("TC_02_Step: Click chon giay to tuy than");
		registerOnline.clickToTextViewCombobox(driver, "com.VCB:id/tvContentConfirm");

		log.info("TC_02_Step: Click chon ho chieu");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[1]);

		log.info("TC_02_Step: Input so ho chieu");
		registerOnline.inputIntoEditTextByID(driver, Register_Online_data.Valid_Account.NO_IDENTIFICATION, "com.VCB:id/edtIDPp");

		log.info("TC_02_Step: Click ngay cap");
		registerOnline.clickToTextViewCombobox(driver, "com.VCB:id/tvContentDate");

		log.info("TC_02_Step: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_02_Step: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(5));

		log.info("TC_02_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, "OK");

		log.info("TC_02_Step: Nhap noi cap");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.LOCATION[1], "Loại xác nhận", "3");

		log.info("TC_02_Step: Nhap email");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.EMAIL, "Loại xác nhận", "4");

		log.info("TC_02_Step: Nhap so tai khoan nhan");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.ACCOUNT_TAKE[0], "Thông tin tài khoản ghi nợ", "1");

		log.info("TC_02_Step: Nhap ten chu tai khoan");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.ACC_NAME, "Thông tin tài khoản ghi nợ", "2");

		log.info("TC_02_Step: Click tab ngoai he thong VCB");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Ngoài hệ thống Vietcombank");

		log.info("TC_02_Step: Nhap so tai khoan phan ghi co");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.ACCOUNT_TAKE[1], "Thông tin tài khoản ghi có", "5");

		log.info("TC_02_Step: Nhap ten tai khoan phan ghi co");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.CUSTOMER_NAME_ACCEPT, "Thông tin tài khoản ghi có", "6");
		
		log.info("TC_02_Step: Chon ngan hang");
		registerOnline.clickToDynamicComboboxText(driver, "Thông tin tài khoản ghi có", "7");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.BANK_NAME);

		log.info("TC_02_Step: Nhap so tien chuyen");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.AMOUNT, "Thông tin tài khoản ghi có", "8");

		log.info("TC_02_Step: Nhap noi dung");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.NOTE, "Thông tin tài khoản ghi có", "9");

		log.info("TC_02_Step: Click button xac nhan");
		registerOnline.clickToDynamicButton(driver, "Xác nhận");

		Thread.sleep(5000);
		log.info("TC_02_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "0","1"), "ĐĂNG KÝ THÀNH CÔNG");
		
		log.info("TC_02_Step: verify text dieu kien");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "0","4"),  Register_Online_data.Message.MESSAGE_SUCCESS);
		
		log.info("TC_02_Step: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Chia sẻ"));
		
		log.info("TC_02_Step: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Lưu ảnh"));

		log.info("TC_02_Step: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, "Thực hiện đăng ký khác");

	}
	
	@Test
	public void TC_03_GiaoDichNopTienThanhCong() throws InterruptedException {
		log.info("TC_03_Step: Click nop tien");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Nộp tiền");

		log.info("TC_03_Step: Nhap thong tin khach hang");
		registerOnline.inputIntoEditTextByID(driver, Register_Online_data.Valid_Account.ACC_NAME, "com.VCB:id/edtFullName");

		log.info("TC_03_Step: Click chon giay to tuy than");
		registerOnline.clickToTextViewCombobox(driver, "com.VCB:id/tvContentConfirm");

		log.info("TC_03_Step: Click chon ho chieu");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[1]);

		log.info("TC_03_Step: Input so ho chieu");
		registerOnline.inputIntoEditTextByID(driver, Register_Online_data.Valid_Account.NO_IDENTIFICATION, "com.VCB:id/edtIDPp");

		log.info("TC_03_Step: Click ngay cap");
		registerOnline.clickToTextViewCombobox(driver, "com.VCB:id/tvContentDate");

		log.info("TC_03_Step: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_03_Step: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(5));

		log.info("TC_03_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, "OK");

		log.info("TC_03_Step: Nhap noi cap");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.LOCATION[1], "Loại xác nhận", "3");

		log.info("TC_03_Step: Nhap email");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.EMAIL, "Loại xác nhận", "4");

		log.info("TC_03_Step: Nhap so tai khoan phan ghi co");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.ACCOUNT_TAKE[1], "Thông tin tài khoản ghi có", "3");

		log.info("TC_03_Step: Nhap ten tai khoan phan ghi co");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.CUSTOMER_NAME_ACCEPT, "Thông tin tài khoản ghi có", "4");
		
		log.info("TC_03_Step: Chon ngan hang");
		registerOnline.clickToDynamicComboboxText(driver, "Thông tin tài khoản ghi có", "5");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.BANK_NAME);

		log.info("TC_03_Step: Nhap so tien chuyen");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.AMOUNT, "Thông tin giao dịch", "7");

		log.info("TC_03_Step: Nhap nguoi nop tien");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.ACC_NAME, "Thông tin giao dịch", "8");
		
		log.info("TC_03_Step: Nhap noi dung chuyen");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.NOTE, "Thông tin giao dịch", "9");

		log.info("TC_03_Step: Click button xac nhan");
		registerOnline.clickToDynamicButton(driver, "Xác nhận");

		Thread.sleep(5000);
		log.info("TC_03_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "0","1"), "ĐĂNG KÝ THÀNH CÔNG");
		
		log.info("TC_03_Step: verify text dieu kien");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "0","4"),  Register_Online_data.Message.MESSAGE_SUCCESS);
		
		log.info("TC_03_Step: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Chia sẻ"));
		
		log.info("TC_03_Step: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Lưu ảnh"));

		log.info("TC_03_Step: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, "Thực hiện đăng ký khác");

	}
	
	@Test
	public void TC_04_GiaoDichRutTienUSDThanhCong() throws InterruptedException {
		log.info("TC_04_Step: Click nop tien");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Rút tiền");

		log.info("TC_04_Step: Nhap thong tin khach hang");
		registerOnline.inputIntoEditTextByID(driver, Register_Online_data.Valid_Account.ACC_NAME, "com.VCB:id/edtFullName");

		log.info("TC_04_Step: Click chon giay to tuy than");
		registerOnline.clickToTextViewCombobox(driver, "com.VCB:id/tvContentConfirm");

		log.info("TC_04_Step: Click chon ho chieu");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[1]);

		log.info("TC_04_Step: Input so ho chieu");
		registerOnline.inputIntoEditTextByID(driver, Register_Online_data.Valid_Account.NO_IDENTIFICATION, "com.VCB:id/edtIDPp");

		log.info("TC_04_Step: Click ngay cap");
		registerOnline.clickToTextViewCombobox(driver, "com.VCB:id/tvContentDate");

		log.info("TC_04_Step: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_04_Step: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(5));

		log.info("TC_04_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, "OK");

		log.info("TC_04_Step: Nhap noi cap");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.LOCATION[1], "Loại xác nhận", "3");

		log.info("TC_04_Step: Nhap email");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.EMAIL, "Loại xác nhận", "4");

		log.info("TC_04_Step: Nhap so tai khoan phan ghi nợ");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.ACCOUNT_TAKE[1], "Thông tin tài khoản ghi nợ", "3");

		log.info("TC_04_Step: Nhap ten tai khoan phan ghi nợ");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.CUSTOMER_NAME_ACCEPT, "Thông tin tài khoản ghi nợ", "4");
		
		log.info("TC_04_Step: Chon loai tien la USD");
		registerOnline.clickToDynamicImageCombobox(driver, "Thông tin giao dịch","7");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "USD");
		
		log.info("TC_04_Step: Verify loai tien la USD");
		verifyEquals(registerOnline.getDynamicTextInDropDownByHeader(driver, "Thông tin giao dịch","7"), "USD");

		log.info("TC_04_Step: Nhap so tien chuyen");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.AMOUNT_USD_EUR, "Thông tin giao dịch", "7");
		
		log.info("TC_04_Step: Nhap noi dung chuyen");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.NOTE, "Thông tin giao dịch", "8");

		log.info("TC_04_Step: Click button xac nhan");
		registerOnline.clickToDynamicButton(driver, "Xác nhận");

		Thread.sleep(5000);
		log.info("TC_04_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "0","1"), "ĐĂNG KÝ THÀNH CÔNG");
		
		log.info("TC_04_Step: verify text dieu kien");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "0","4"),  Register_Online_data.Message.MESSAGE_SUCCESS);
		
		log.info("TC_04_Step: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Chia sẻ"));
		
		log.info("TC_04_Step: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Lưu ảnh"));

		log.info("TC_04_Step: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, "Thực hiện đăng ký khác");
	}


}
