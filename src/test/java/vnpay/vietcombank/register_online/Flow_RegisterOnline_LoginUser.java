package vnpay.vietcombank.register_online;

import java.io.IOException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.MobileCapabilityType;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.RegisterOnlinePageObject;
import pageObjects.TransactionReportPageObject;
import pageObjects.TransferMoneyObject;
import pageObjects.sdk.trainTicket.TrainTicketPageObject;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.Register_Online_data;
import vietcombank_test_data.TransferMoneyQuick_Data;

public class Flow_RegisterOnline_LoginUser extends Base {
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
		login.Global_login(phone, pass, opt);
		registerOnline = PageFactoryManager.getRegisterOnlinePageObject(driver);
	}

 //@Test
	public void TC_01_GiaoDichNopTienThanhCong() throws InterruptedException {
		log.info("TC_Step_: Click menu header");
		registerOnline.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_Step_: Click chuc nang dang ky truc tuyen");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Đăng ký trực tuyến");

		log.info("TC_Step_: Click giao dich tien mat/chuyen tien");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Giao dịch tiền mặt/chuyển tiền");

		log.info("TC_Step_: Click nop tien");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Nộp tiền");

		log.info("TC_Step_: verify ten khach hang, defaul fill ten khach hang");
		verifyEquals(registerOnline.getTextInDynamicPasswordInput(driver, "com.VCB:id/edtFullName"), Register_Online_data.Valid_Account.ACC_NAME);

		log.info("TC_Step_: Click chon giay to tuy than");
		registerOnline.clickToTextViewCombobox(driver, "com.VCB:id/tvContentConfirm");

		log.info("TC_Step_: Click chon ho chieu");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[1]);

		log.info("TC_Step_: Input so ho chieu");
		registerOnline.inputIntoEditTextByID(driver, Register_Online_data.Valid_Account.NO_IDENTIFICATION, "com.VCB:id/edtIDPp");

		log.info("TC_Step_: Click ngay cap");
		registerOnline.clickToTextViewCombobox(driver, "com.VCB:id/tvContentDate");

		log.info("TC_Step_: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_Step_: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_Step_: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(5));

		log.info("TC_Step_: Click OK");
		registerOnline.clickToDynamicButton(driver, "OK");

		log.info("TC_Step_: Nhap email");
		registerOnline.inputIntoEditTextByID(driver, Register_Online_data.Valid_Account.EMAIL, "com.VCB:id/edtEmail");

		log.info("TC_Step_: Click chon so tai khoan nguoi nhan");
		registerOnline.clickToTextViewCombobox(driver, "com.VCB:id/edtContent2");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.ACCOUNT_TAKE[0]);

		log.info("TC_Step_: Nhap so tien chuyen");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.AMOUNT, "Thông tin giao dịch", "7");

		log.info("TC_Step_: Nhap noi dung");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.NOTE, "Thông tin giao dịch", "9");

		log.info("TC_Step_: Click button xac nhan");
		registerOnline.clickToDynamicButton(driver, "Xác nhận");

		log.info("TC_Step_: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1","2"), "ĐĂNG KÝ THÀNH CÔNG");
		
		log.info("TC_Step_: verify text dieu kien");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1","5"),  Register_Online_data.Message.MESSAGE_SUCCESS);
		
		log.info("TC_Step_: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Chia sẻ"));
		
		log.info("TC_Step_: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Lưu ảnh"));

		log.info("TC_Step_: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, "Thực hiện đăng ký khác");
	}

//@Test
	public void TC_02_GiaoDichRutTienVNDThanhCong() throws InterruptedException {
		log.info("TC_Step_: Click rut tien");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Rút tiền");

		log.info("TC_Step_: verify ten khach hang, defaul fill ten khach hang");
		verifyEquals(registerOnline.getTextInDynamicPasswordInput(driver, "com.VCB:id/edtFullName"), Register_Online_data.Valid_Account.ACC_NAME);

		log.info("TC_Step_: Click chon giay to tuy than");
		registerOnline.clickToTextViewCombobox(driver, "com.VCB:id/tvContentConfirm");

		log.info("TC_Step_: Click chon ho chieu");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[1]);

		log.info("TC_Step_: Input so ho chieu");
		registerOnline.inputIntoEditTextByID(driver, Register_Online_data.Valid_Account.NO_IDENTIFICATION, "com.VCB:id/edtIDPp");

		log.info("TC_Step_: Click ngay cap");
		registerOnline.clickToTextViewCombobox(driver, "com.VCB:id/tvContentDate");

		log.info("TC_Step_: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_Step_: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_Step_: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(5));

		log.info("TC_Step_: Click OK");
		registerOnline.clickToDynamicButton(driver, "OK");

		log.info("TC_Step_: Nhap email");
		registerOnline.inputIntoEditTextByID(driver, Register_Online_data.Valid_Account.EMAIL, "com.VCB:id/edtEmail");

		log.info("TC_Step_: Click chon so tai khoan nguoi nhan");
		registerOnline.clickToTextViewCombobox(driver, "com.VCB:id/edtContent2");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.ACCOUNT_TAKE[1]);
		
		log.info("TC_Step_: Chon loai tien la EUR");
		registerOnline.clickToDynamicImageCombobox(driver, "Thông tin giao dịch","6");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "EUR");
		
		log.info("TC_Step_: Verify loai tien la EUR");
		verifyEquals(registerOnline.getDynamicTextInDropDownByHeader(driver, "Thông tin giao dịch","6"), "EUR");

		log.info("TC_Step_: Nhap so tien chuyen");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.AMOUNT_USD_EUR, "Thông tin giao dịch", "6");

		log.info("TC_Step_: Nhap noi dung");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.NOTE, "Thông tin giao dịch", "7");

		log.info("TC_Step_: Click button xac nhan");
		registerOnline.clickToDynamicButton(driver, "Xác nhận");

		log.info("TC_Step_: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1","2"), "ĐĂNG KÝ THÀNH CÔNG");
		
		log.info("TC_Step_: verify text dieu kien");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1","5"),  Register_Online_data.Message.MESSAGE_SUCCESS);
		
		log.info("TC_Step_: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Chia sẻ"));
		
		log.info("TC_Step_: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Lưu ảnh"));

		log.info("TC_Step_: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, "Thực hiện đăng ký khác");
	}

@Test
public void TC_03_MoTheTietKiem_TaiKhoanTienGuiCoKyHan() throws InterruptedException {
	log.info("TC_Step_: Click menu header");
	registerOnline.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

	log.info("TC_Step_: Click chuc nang dang ky truc tuyen");
	registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Đăng ký trực tuyến");
	log.info("TC_Step_: Click giao dich Gui/rut tiết kiệm");
	registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Gửi/rút tiết kiệm");
	
	log.info("TC_Step_: Click gui tiet kiem");
	registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Gửi tiết kiệm");
	
	log.info("TC_Step_: verify ten khach hang, defaul fill ten khach hang");
	verifyEquals(registerOnline.getTextInDynamicPasswordInput(driver, "com.VCB:id/edtContent"), Register_Online_data.Valid_Account.ACC_NAME);

	log.info("TC_Step_: Click giay to tuy than");
	registerOnline.clickToDynamicDropDown(driver, "Giấy tờ tùy thân");
	registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[1]);

	log.info("TC_Step_: Input so ho chieu");
	registerOnline.inputToDynamicInputText(driver, Register_Online_data.Valid_Account.NO_IDENTIFICATION, "Giấy tờ tùy thân","5");

	log.info("TC_Step_: Click ngay cap");
	registerOnline.clickToTextViewDate(driver, "Giấy tờ tùy thân","6");

	log.info("TC_Step_: Click nam");
	registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

	log.info("TC_Step_: Click nam");
	registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

	log.info("TC_Step_: Chon nam cap");
	registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(5));

	log.info("TC_Step_: Click OK");
	registerOnline.clickToDynamicButton(driver, "OK");

	log.info("TC_Step_: Input so ho chieu");
	registerOnline.inputToDynamicInputText(driver, Register_Online_data.Valid_Account.EMAIL, "Giấy tờ tùy thân","8");

	log.info("TC_Step_: Chon hinh thuc gui tien co ky han");
	registerOnline.clickToDynamicImageCombobox(driver, "Thông tin giao dịch","10");
	registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.LIST_SEND_MONEY[0]);
	
	log.info("TC_Step_: Chon ky han thoi gian");
	registerOnline.clickToDynamicImageCombobox(driver, "Thông tin giao dịch","11");
	registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.LIST_PERIOD[1]);
	
	log.info("TC_Step_: Nhap so tien gui");
	registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.AMOUNT, "Thông tin giao dịch", "13");
	
	log.info("TC_Step_: Chon ky han thoi gian");
	registerOnline.clickToDynamicImageCombobox(driver, "Thông tin giao dịch","14");
	registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.INTEREST_RECEPT_TYPE[0]);
	
	log.info("TC_Step_: Tien mat mac dinh chon, verify text 'Tien mat' hien thi");
	registerOnline.clickToDynamicImageCombobox(driver, "Thông tin giao dịch","14");
	registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.INTEREST_RECEPT_TYPE[0]);
	
	log.info("TC_Step_: Nhap so tien nop");
	registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.AMOUNT, "Thông tin giao dịch", "16");
	
	log.info("TC_Step_: Click button xac nhan");
	registerOnline.clickToDynamicButton(driver, "Xác nhận");

	log.info("TC_Step_: verify man hinh dang ky thanh cong");
	verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1","2"), "ĐĂNG KÝ THÀNH CÔNG");
	
	log.info("TC_Step_: verify text dieu kien");
	verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1","5"),  Register_Online_data.Message.MESSAGE_SUCCESS);
	
	log.info("TC_Step_: verify image chia se");
	verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Chia sẻ"));
	
	log.info("TC_Step_: verify image luu anh");
	verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Lưu ảnh"));

	log.info("TC_Step_: Click button thuc hien giao dich khác");
	registerOnline.clickToDynamicButton(driver, "Thực hiện đăng ký khác");
}
}