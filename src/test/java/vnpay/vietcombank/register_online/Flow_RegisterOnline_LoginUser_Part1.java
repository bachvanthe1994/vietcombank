package vnpay.vietcombank.register_online;

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
import pageObjects.RegisterOnlinePageObject;
import vietcombank_test_data.Register_Online_data;

public class Flow_RegisterOnline_LoginUser_Part1 extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private RegisterOnlinePageObject registerOnline;
	String phoneNumber = "";
	String nameCustomer = "";

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

		log.info("Before class: Click menu header");
		registerOnline.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("Before class: Lay ten user");
		nameCustomer = registerOnline.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvFullname");

		log.info("Before class: Click chuc nang dang ky truc tuyen");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Đăng ký trực tuyến");
	}

	@Test
	public void TC_01_GiaoDichNopTienThanhCong() {

		log.info("TC_01_Step: Click giao dich tien mat/chuyen tien");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Giao dịch tiền mặt/chuyển tiền");

		log.info("TC_01_Step: Click nop tien");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Nộp tiền");

		log.info("TC_01_Step: verify ten khach hang, defaul fill ten khach hang");
		verifyEquals(registerOnline.getTextInDynamicPasswordInput(driver, "com.VCB:id/edtFullName"), nameCustomer);

		log.info("TC_01_Step: Click chon giay to tuy than");
		registerOnline.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvContentConfirm");

		log.info("TC_01_Step: Click chon CMT");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[0]);

		log.info("TC_01_Step: Input so ho chieu");
		registerOnline.inputIntoEditTextByID(driver, Register_Online_data.Valid_Account.NO_IDENTIFICATION, "com.VCB:id/edtIDPp");

		log.info("TC_01_Step: Click ngay cap");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/llIssueDate");

		log.info("TC_01_Step: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_01_Step: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(1));

		log.info("TC_01_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, "OK");

		log.info("TC_01_Step: Click noi cap");
		registerOnline.clickToTextViewCombobox(driver, "com.VCB:id/edtIssuePlace");
		registerOnline.inputIntoEditTextByID(driver, "THANH PHO CAN THO", "com.VCB:id/edtSearch");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "THANH PHO CAN THO");

		log.info("TC_01_Step: Nhap email");
		registerOnline.inputIntoEditTextByID(driver, Register_Online_data.Valid_Account.EMAIL, "com.VCB:id/edtEmail");

		log.info("TC_01_Step: Click chon so tai khoan nguoi nhan");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutSoTk2");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.ACCOUNT_TAKE[0]);

		log.info("TTC_01_Step: Nhap so tien chuyen");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.AMOUNT, "com.VCB:id/layoutSoTien");

		log.info("TC_01_Step: Nhap noi dung");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.NOTE, "com.VCB:id/layoutNoiDungNop");

		log.info("TC_01_Step: Click button xac nhan");
		registerOnline.clickToDynamicButton(driver, "Xác nhận");

		log.info("TC_01_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getDynamicTextInPopUp(driver, "ĐĂNG KÝ THÀNH CÔNG"), "ĐĂNG KÝ THÀNH CÔNG");

		log.info("TC_01_Step: verify text dieu kien");
		verifyEquals(registerOnline.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvNote"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_01_Step: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Chia sẻ"));

		log.info("TC_01_Step: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Lưu ảnh"));

		log.info("TC_01_Step: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, "Thực hiện đăng ký khác");
	}

	@Test
	public void TC_15_GiaoDichRutTienVNDThanhCong() {

		log.info("TC_02_Step: Click rut tien");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Giao dịch tiền mặt/chuyển tiền");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Rút tiền");

		log.info("TC_02_Step: verify ten khach hang, defaul fill ten khach hang");
		verifyEquals(registerOnline.getTextInDynamicPasswordInput(driver, "com.VCB:id/edtFullName"), nameCustomer);

		log.info("TC_02_Step: Click chon giay to tuy than");
		registerOnline.clickToTextViewCombobox(driver, "com.VCB:id/tvContentConfirm");

		log.info("TC_02_Step: Click chon ho chieu");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[1]);

		log.info("TC_02_Step: Input so ho chieu");
		registerOnline.inputIntoEditTextByID(driver, Register_Online_data.Valid_Account.NO_IDENTIFICATION, "com.VCB:id/edtIDPp");

		log.info("TC_02_Step: Click ngay cap");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/llIssueDate");

		log.info("TC_02_Step: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_02_Step: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(1));

		log.info("TC_02_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, "OK");

		log.info("TC_02_Step: Nhap noi cap");
		registerOnline.inputIntoEditTextByID(driver, "THANH PHO CAN THO", "com.VCB:id/edtNoiCap2");

		log.info("TC_02_Step: Nhap email");
		registerOnline.inputIntoEditTextByID(driver, Register_Online_data.Valid_Account.EMAIL, "com.VCB:id/edtEmail");

		log.info("TC_02_Step: Click chon so tai khoan nguoi nhan");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutSoTk2");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.ACCOUNT_TAKE[1]);
		registerOnline.scrollIDownOneTime(driver);

		log.info("TC_02_Step: Chon loai tien la EUR");
		registerOnline.clickToDynamicImageViewByID(driver, "com.VCB:id/ivContent3");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "EUR");

		log.info("TC_02_Step: Nhap so tien chuyen");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.AMOUNT, "com.VCB:id/layoutSoTien");

		log.info("TC_02_Step: Nhap noi dung");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.NOTE, "com.VCB:id/layoutNoiDungNop");

		log.info("TC_02_Step: Click button xac nhan");
		registerOnline.clickToDynamicButton(driver, "Xác nhận");

		log.info("TC_02_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getDynamicTextInPopUp(driver, "ĐĂNG KÝ THÀNH CÔNG"), "ĐĂNG KÝ THÀNH CÔNG");

		log.info("TC_02Step: verify text dieu kien");
		verifyEquals(registerOnline.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvNote"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_02_Step: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Chia sẻ"));

		log.info("TC_02_Step: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Lưu ảnh"));

		log.info("TC_02_Step: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, "Thực hiện đăng ký khác");
	}

	@Test
	public void TC_16_GuiTietKiem_TaiKhoanTienGuiCoKyHanTienMat() {

		log.info("TC_03_Step: Click gui tiet kiem");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Gửi tiết kiệm");

		log.info("TC_03_Step: Nhap dia chi lien he");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.ADDRESS, "com.VCB:id/layoutDiaChiLH");

		log.info("TC_03_Step: Click giay to tuy than");
		registerOnline.clickToDynamicDropDown(driver, "Giấy tờ tùy thân");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[1]);

		log.info("TC_03_Step: Input so ho chieu");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.NO_IDENTIFICATION, "com.VCB:id/layoutCMTND");

		log.info("TC_03_Step: Click ngay cap");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayCap");

		log.info("TC_03_Step: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_03_Step: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(1));

		log.info("TC_03_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, "OK");

		log.info("TC_03_Step: Input noi cap");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.LOCATION[1], "com.VCB:id/layoutNoiCap2");

		log.info("TC_03_Step: Nhap email");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.EMAIL, "com.VCB:id/layoutEmail");

		log.info("TC_03_Step: Chon hinh thuc gui tien co ky han");
		registerOnline.scrollDownToText(driver, "Tiền mặt");
		registerOnline.clickToDynamicLinerLayoutID(driver, "com.VCB:id/layoutHinhThucGuiTien");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.LIST_SEND_MONEY[0]);

		log.info("TC_03_Step: Chon ky han thoi gian");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Kỳ hạn");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.LIST_PERIOD[1]);

		log.info("TC_03_Step: Nhap so tien gui");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.AMOUNT, "com.VCB:id/layoutAmount1");

		log.info("TC_03_Step: Chon ky han thoi gian");
		registerOnline.clickToTextID(driver, "com.VCB:id/ivContent3");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "VND");

		log.info("TC_03_Step: Tien mat mac dinh chon, verify text 'Tien mat' hien thi");
		verifyEquals(registerOnline.getTextDynamicFollowLayout(driver, "com.VCB:id/llTienMat"), "Tiền mặt");

		log.info("TC_03_Step: Nhap so tien nop");
		registerOnline.scrollDownToButton(driver, "Xác nhận");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.AMOUNT, "com.VCB:id/layoutAmount2");

		log.info("TC_03_Step: Click button xac nhan");
		registerOnline.clickToDynamicButton(driver, "Xác nhận");

		log.info("TC_03_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getDynamicTextInPopUp(driver, "ĐĂNG KÝ THÀNH CÔNG"), "ĐĂNG KÝ THÀNH CÔNG");

		log.info("TC_03_Step: verify text dieu kien");
		verifyEquals(registerOnline.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvNote"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_03_Step: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Chia sẻ"));

		log.info("TC_03_Step: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Lưu ảnh"));

		log.info("TC_03_Step: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, "Thực hiện đăng ký khác");
	}

	@Test
	public void TC_04_GuiTietKiem_TaiKhoanTienGuiKhongKyHan() {

		log.info("TC_04_Step: Click gui tiet kiem");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Gửi/rút tiết kiệm");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Gửi tiết kiệm");

		log.info("TC_04_Step: Input dia chi lien he");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.LOCATION[0], "com.VCB:id/layoutDiaChiLH");

		log.info("TC_04_Step: Click giay to tuy than");
		registerOnline.clickToDynamicDropDown(driver, "Giấy tờ tùy thân");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[0]);

		log.info("TC_04_Step: Input so ho chieu");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.NO_IDENTIFICATION, "com.VCB:id/layoutCMTND");

		log.info("TC_04_Step: Click ngay cap");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayCap");

		log.info("TC_04_Step: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_04_Step: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(1));

		log.info("TC_04_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, "OK");

		log.info("TC_04_Step: Click noi cap");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNoiCap");
		registerOnline.inputIntoEditTextByID(driver, "THANH PHO CAN THO", "com.VCB:id/edtSearch");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "THANH PHO CAN THO");

		log.info("TC_04_Step: Nhap noi cap");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, "THANH PHO CAN THO", "com.VCB:id/layoutNoiCap2");

		log.info("TC_04_Step: Nhap email");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.EMAIL, "com.VCB:id/layoutEmail");

		log.info("TC_04_Step: Chon hinh thuc gui tien co ky han");
		registerOnline.clickToDynamicLinerLayoutID(driver, "com.VCB:id/layoutHinhThucGuiTien");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.LIST_SEND_MONEY[2]);

		log.info("TC_04_Step: Chon ky han thoi gian");
		registerOnline.scrollIDownOneTime(driver);
		registerOnline.clickToDynamicLinerLayoutID(driver, "com.VCB:id/layoutKyHan");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.LIST_PERIOD[0]);

		log.info("TC_04_Step: Nhap so tien gui");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.AMOUNT, "com.VCB:id/layoutAmount1");

		log.info("TC_04_Step: Chon phuong thuc nhan lai");
		registerOnline.clickToDynamicLinerLayoutID(driver, "com.VCB:id/layoutPhuongThucNhanLai");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.INTEREST_RECEPT_TYPE[1]);

		log.info("TC_04_Step: Tien mat mac dinh chon, verify text 'Tien mat' hien thi");
		verifyEquals(registerOnline.getTextDynamicFollowLayout(driver, "com.VCB:id/llTienMat"), "Tiền mặt");

		log.info("TC_04_Step: Nhap so tien nop");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.AMOUNT, "com.VCB:id/layoutAmount2");

		log.info("TC_04_Step: Click button xac nhan");
		registerOnline.clickToDynamicButton(driver, "Xác nhận");

		log.info("TC_04_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getDynamicTextInPopUp(driver, "ĐĂNG KÝ THÀNH CÔNG"), "ĐĂNG KÝ THÀNH CÔNG");

		log.info("TC_04_Step: verify text dieu kien");
		verifyEquals(registerOnline.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvNote"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_04_Step: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Chia sẻ"));

		log.info("TC_04_Step: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Lưu ảnh"));

		log.info("TC_04_Step: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, "Thực hiện đăng ký khác");
	}

	@Test
	public void TC_05_GuiTietKiem_TaiKhoanTienGuiCoKyHanChuyenKhoan() {

		log.info("TC_05_Step: Click gui tiet kiem");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Gửi tiết kiệm");

		log.info("TC_05_Step: Input dia chi lien he");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.LOCATION[0], "com.VCB:id/layoutDiaChiLH");

		log.info("TC_05_Step: Click giay to tuy than");
		registerOnline.clickToDynamicDropDown(driver, "Giấy tờ tùy thân");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[0]);

		log.info("TC_05_Step: Input so ho chieu");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.NO_IDENTIFICATION, "com.VCB:id/layoutCMTND");

		log.info("TC_05_Step: Click ngay cap");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayCap");

		log.info("TC_05_Step: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_05_Step: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker",

				getBackWardYear(1));

		log.info("TC_05_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, "OK");

		log.info("TC_05_Step: Click noi cap");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNoiCap");
		registerOnline.inputIntoEditTextByID(driver, "THANH PHO CAN THO", "com.VCB:id/edtSearch");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "THANH PHO CAN THO");

		log.info("TC_05_Step: Nhap noi cap");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, "THANH PHO CAN THO", "com.VCB:id/layoutNoiCap2");

		log.info("TC_05_Step: Nhap email");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.EMAIL, "com.VCB:id/layoutEmail");

		log.info("TC_05_Step: Chon hinh thuc gui tien co ky han");
		registerOnline.clickToDynamicLinerLayoutID(driver, "com.VCB:id/layoutHinhThucGuiTien");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.LIST_SEND_MONEY[2]);

		log.info("TC_05_Step: Chon ky han thoi gian");
		registerOnline.scrollIDownOneTime(driver);
		registerOnline.clickToDynamicLinerLayoutID(driver, "com.VCB:id/layoutKyHan");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.LIST_PERIOD[0]);

		log.info("TC_05_Step: Nhap so tien gui");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.AMOUNT, "com.VCB:id/layoutAmount1");

		log.info("TC_05_Step: Chon phuong thuc nhan lai");
		registerOnline.clickToDynamicLinerLayoutID(driver, "com.VCB:id/layoutPhuongThucNhanLai");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.INTEREST_RECEPT_TYPE[0]);

		log.info("TC_05_Step: Click chon chuyen khoan");
		registerOnline.scrollIDownOneTime(driver);
		registerOnline.clickToImageRadio(driver, "Chuyển khoản");

		log.info("TC_05_Step: Click bo chon mac dinh tien mat");
		registerOnline.clickToImageRadio(driver, "Tiền mặt");

		log.info("TC_05_Step: check text da chon la chuyen khoan");
		verifyEquals(registerOnline.getTextDynamicFollowLayout(driver, "com.VCB:id/llChuyenKhoan"), "Chuyển khoản");

		log.info("TC_05_Step: Nhap so tai khoan");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.ACCOUNT_TAKE[1], "com.VCB:id/layoutSoTaiKhoan");

		log.info("TC_05_Step: Nhap so tien nop");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.AMOUNT, "com.VCB:id/layoutAmount3");

		log.info("TC_05_Step: Click button xac nhan");
		registerOnline.clickToDynamicButton(driver, "Xác nhận");

		log.info("TC_05_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getDynamicTextInPopUp(driver, "ĐĂNG KÝ THÀNH CÔNG"), "ĐĂNG KÝ THÀNH CÔNG");

		log.info("TC_05_Step: verify text dieu kien");
		verifyEquals(registerOnline.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvNote"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_05_Step: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Chia sẻ"));

		log.info("TC_05_Step: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Lưu ảnh"));

		log.info("TC_05_Step: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, "Thực hiện đăng ký khác");
	}

	@Test
	public void TC_06_GuiTietKiem_TheTietKiemCoKyHanChuyenKhoanVaTienMatNhanGoc() {

		log.info("TC_06_Step: Click gui tiet kiem");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Gửi tiết kiệm");

		log.info("TC_06_Step: Input dia chi lien he");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.LOCATION[0], "com.VCB:id/layoutDiaChiLH");

		log.info("TC_06_Step: Click giay to tuy than");
		registerOnline.clickToDynamicDropDown(driver, "Giấy tờ tùy thân");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[0]);

		log.info("TC_06_Step: Input so ho chieu");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.NO_IDENTIFICATION, "com.VCB:id/layoutCMTND");

		log.info("TC_06_Step: Click ngay cap");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayCap");

		log.info("TC_06_Step: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_06_Step: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(1));

		log.info("TC_06_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, "OK");

		log.info("TC_06_Step: Click noi cap");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNoiCap");
		registerOnline.inputIntoEditTextByID(driver, "THANH PHO CAN THO", "com.VCB:id/edtSearch");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "THANH PHO CAN THO");

		log.info("TC_06_Step: Nhap noi cap");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, "THANH PHO CAN THO", "com.VCB:id/layoutNoiCap2");

		log.info("TC_06_Step: Nhap email");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.EMAIL, "com.VCB:id/layoutEmail");

		log.info("TC_06_Step: Chon hinh thuc gui tien co ky han");
		registerOnline.clickToDynamicLinerLayoutID(driver, "com.VCB:id/layoutHinhThucGuiTien");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.LIST_SEND_MONEY[0]);

		log.info("TC_06_Step: Chon ky han thoi gian");
		registerOnline.scrollIDownOneTime(driver);
		registerOnline.clickToDynamicLinerLayoutID(driver, "com.VCB:id/layoutKyHan");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.LIST_PERIOD[1]);

		log.info("TC_06_Step: Nhap so tien nop");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.AMOUNT_OPEN_CARD, "com.VCB:id/layoutAmount1");

		log.info("TC_06_Step: Chon phuong thuc nhan lai");
		registerOnline.clickToDynamicLinerLayoutID(driver, "com.VCB:id/layoutPhuongThucNhanLai");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.INTEREST_RECEPT_TYPE[2]);

		log.info("TC_06_Step: Click chon chuyen khoan");
		registerOnline.clickToImageRadio(driver, "Chuyển khoản");

		log.info("TC_06_Step: Nhap so tai khoan");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.ACCOUNT_TAKE[0], "com.VCB:id/layoutTaiKhoanNhanLai");

		log.info("TC_06_Step: check text mac dinh chon la tien mat");
		registerOnline.scrollIDownOneTime(driver);
		verifyEquals(registerOnline.getTextDynamicFollowLayout(driver, "com.VCB:id/llTienMat"), "Tiền mặt");

		log.info("TC_06_Step: check text da chon la chuyen khoan");
		verifyEquals(registerOnline.getTextDynamicFollowLayout(driver, "com.VCB:id/llChuyenKhoan"), "Chuyển khoản");

		log.info("TC_06_Step: Nhap so tien nop");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.AMOUNT, "com.VCB:id/layoutAmount2");

		log.info("TC_06_Step: Nhap so tai khoan");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.ACCOUNT_TAKE[1], "com.VCB:id/layoutSoTaiKhoan");

		log.info("TC_06_Step: Nhap so tien nop");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.AMOUNT, "com.VCB:id/layoutAmount3");

		log.info("TC_06_Step: Click button xac nhan");
		registerOnline.clickToDynamicButton(driver, "Xác nhận");

		log.info("TC_06_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getDynamicTextInPopUp(driver, "ĐĂNG KÝ THÀNH CÔNG"), "ĐĂNG KÝ THÀNH CÔNG");

		log.info("TC_06_Step: verify text dieu kien");
		verifyEquals(registerOnline.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvNote"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_06_Step: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Chia sẻ"));

		log.info("TC_06_Step: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Lưu ảnh"));

		log.info("TC_06_Step: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, "Thực hiện đăng ký khác");

	}

	@Test
	public void TC_07_MoTheTietKiem_TheTietKiemKhongKyHanTienMatNhanGoc() {

		log.info("TC_07_Step: Click gui tiet kiem");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Gửi tiết kiệm");

		log.info("TC_07_Step: Input dia chi lien he");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.LOCATION[0], "com.VCB:id/layoutDiaChiLH");

		log.info("TC_07_Step: Click giay to tuy than");
		registerOnline.clickToDynamicDropDown(driver, "Giấy tờ tùy thân");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[0]);

		log.info("TC_07_Step: Input so ho chieu");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.NO_IDENTIFICATION, "com.VCB:id/layoutCMTND");

		log.info("TC_07_Step: Click ngay cap");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayCap");

		log.info("TC_07_Step: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_07_Step: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(1));

		log.info("TC_07_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, "OK");

		log.info("TC_07_Step: Click noi cap");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNoiCap");
		registerOnline.inputIntoEditTextByID(driver, "THANH PHO CAN THO", "com.VCB:id/edtSearch");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "THANH PHO CAN THO");

		log.info("TC_07_Step: Nhap noi cap");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, "THANH PHO CAN THO", "com.VCB:id/layoutNoiCap2");

		log.info("TC_07_Step: Nhap email");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.EMAIL, "com.VCB:id/layoutEmail");

		log.info("TC_07_Step: Chon hinh thuc gui tien co ky han");
		registerOnline.scrollIDownOneTime(driver);
		registerOnline.clickToDynamicLinerLayoutID(driver, "com.VCB:id/layoutHinhThucGuiTien");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.LIST_SEND_MONEY[2]);

		log.info("TC_07_Step: Chon ky han thoi gian");
		registerOnline.clickToDynamicLinerLayoutID(driver, "com.VCB:id/layoutKyHan");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.LIST_PERIOD[2]);

		log.info("TC_07_Step: Nhap so tien gui");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.AMOUNT, "com.VCB:id/layoutAmount1");

		log.info("TC_07_Step: Chon phuong thuc nhan lai");
		registerOnline.clickToDynamicLinerLayoutID(driver, "com.VCB:id/layoutPhuongThucNhanLai");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.INTEREST_RECEPT_TYPE[0]);

		log.info("TC_07_Step: check text mac dinh chon la tien mat");
		verifyEquals(registerOnline.getTextDynamicFollowLayout(driver, "com.VCB:id/llTienMat"), "Tiền mặt");

		log.info("TC_07_Step: Nhap so tien nop");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.AMOUNT, "com.VCB:id/layoutAmount2");

		log.info("TC_07_Step: Click button xac nhan");
		registerOnline.clickToDynamicButton(driver, "Xác nhận");

		log.info("TC_07_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getDynamicTextInPopUp(driver, "ĐĂNG KÝ THÀNH CÔNG"), "ĐĂNG KÝ THÀNH CÔNG");

		log.info("TC_07_Step: verify text dieu kien");
		verifyEquals(registerOnline.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvNote"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_07_Step: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Chia sẻ"));

		log.info("TC_07_Step: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Lưu ảnh"));

		log.info("TC_07_Step: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, "Thực hiện đăng ký khác");
	}

	@Test
	public void TC_08_MoTheTietKiem_TaiKhoanTienGuiCoKyHanTienMatNhanGoc() {

		log.info("TC_08_Step: Click gui tiet kiem");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Gửi tiết kiệm");

		log.info("TC_08_Step: Input dia chi lien he");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.LOCATION[0], "com.VCB:id/layoutDiaChiLH");

		log.info("TC_08_Step: Click giay to tuy than");
		registerOnline.clickToDynamicDropDown(driver, "Giấy tờ tùy thân");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[0]);

		log.info("TC_08_Step: Input so ho chieu");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.NO_IDENTIFICATION, "com.VCB:id/layoutCMTND");

		log.info("TC_08_Step: Click ngay cap");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayCap");

		log.info("TC_08_Step: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_08_Step: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(1));

		log.info("TC_08_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, "OK");

		log.info("TC_08_Step: Click noi cap");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNoiCap");
		registerOnline.inputIntoEditTextByID(driver, "THANH PHO CAN THO", "com.VCB:id/edtSearch");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "THANH PHO CAN THO");

		log.info("TC_08_Step: Nhap noi cap");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, "THANH PHO CAN THO", "com.VCB:id/layoutNoiCap2");

		log.info("TC_08_Step: Nhap email");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.EMAIL, "com.VCB:id/layoutEmail");

		log.info("TC_08_Step: Chon hinh thuc gui tien co ky han");
		registerOnline.scrollIDownOneTime(driver);
		registerOnline.clickToDynamicLinerLayoutID(driver, "com.VCB:id/layoutHinhThucGuiTien");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.LIST_SEND_MONEY[2]);

		log.info("TC_08_Step: Chon ky han thoi gian");
		registerOnline.clickToDynamicLinerLayoutID(driver, "com.VCB:id/layoutKyHan");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.LIST_PERIOD[1]);

		log.info("TC_08_Step: Nhap so tien gui");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.AMOUNT, "com.VCB:id/layoutAmount1");

		log.info("TC_08_Step: Chon phuong thuc nhan lai");
		registerOnline.clickToDynamicLinerLayoutID(driver, "com.VCB:id/layoutPhuongThucNhanLai");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.INTEREST_RECEPT_TYPE[0]);

		log.info("TC_08_Step: check text mac dinh chon la tien mat");
		verifyEquals(registerOnline.getTextDynamicFollowLayout(driver, "com.VCB:id/llTienMat"), "Tiền mặt");

		log.info("TC_08_Step: Nhap so tien nop");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.AMOUNT, "com.VCB:id/layoutAmount2");

		log.info("TC_08_Step: Click button xac nhan");
		registerOnline.clickToDynamicButton(driver, "Xác nhận");

		log.info("TC_08_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getDynamicTextInPopUp(driver, "ĐĂNG KÝ THÀNH CÔNG"), "ĐĂNG KÝ THÀNH CÔNG");

		log.info("TC_08_Step: verify text dieu kien");
		verifyEquals(registerOnline.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvNote"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_08_Step: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Chia sẻ"));

		log.info("TC_08_Step: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Lưu ảnh"));

		log.info("TC_08_Step: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, "Thực hiện đăng ký khác");

	}

	@Test
	public void TC_09_RutSoTietKiem_RutLaiPhuongThucTienMat() {

		log.info("TC_09_Step: Click rut tiet kiem");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Rút tiết kiệm");

		log.info("TC_09_Step: Input dia chi lien he");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.LOCATION[0], "com.VCB:id/layoutDiaChiLH");

		log.info("TC_09_Step: Click giay to tuy than");
		registerOnline.clickToDynamicDropDown(driver, "Giấy tờ tùy thân");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[0]);

		log.info("TC_09_Step: Input so ho chieu");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.NO_IDENTIFICATION, "com.VCB:id/layoutCMTND");

		log.info("TC_09_Step: Click ngay cap");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayCap");

		log.info("TC_09_Step: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_09_Step: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(1));

		log.info("TC_09_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, "OK");

		log.info("TC_09_Step: Click noi cap");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNoiCap");
		registerOnline.inputIntoEditTextByID(driver, "THANH PHO CAN THO", "com.VCB:id/edtSearch");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "THANH PHO CAN THO");

		log.info("TC_09_Step: Nhap email");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.EMAIL, "com.VCB:id/layoutEmail");

		log.info("TC_09_Step: Nhap tai khoan ghi no");
		registerOnline.scrollIDownOneTime(driver);
		registerOnline.inputToDynamicInputBox(driver, Register_Online_data.Valid_Account.ACCOUNT_TAKE[1], "Số tài khoản ghi nợ");

		log.info("TC_09_Step: Click chọn rut lai");
		registerOnline.clickToDynamicLinerLayoutID(driver, "com.VCB:id/layoutYeuCauRutTien");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_RETURN_MONEY[0]);

		log.info("TC_09_Step: Nhap so tien ");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.AMOUNT, "com.VCB:id/layoutAmount1");

		log.info("TC_09_Step: Nhap so Noi Dung");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.NOTE, "com.VCB:id/layoutNoiDung");

		log.info("TC_09_Step: check text mac dinh chon la tien mat");
		verifyEquals(registerOnline.getTextDynamicFollowLayout(driver, "com.VCB:id/llTienMat"), "Tiền mặt");

		log.info("TC_09_Step: Nhap so tien nhan");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.AMOUNT, "com.VCB:id/layoutAmount2");

		log.info("TC_09_Step: Click button xac nhan");
		registerOnline.clickToDynamicButton(driver, "Xác nhận");

		log.info("TC_09_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getDynamicTextInPopUp(driver, "ĐĂNG KÝ THÀNH CÔNG"), "ĐĂNG KÝ THÀNH CÔNG");

		log.info("TC_09_Step: verify text dieu kien");
		verifyEquals(registerOnline.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvNote"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_09_Step: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Chia sẻ"));

		log.info("TC_09_Step: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Lưu ảnh"));

		log.info("TC_09_Step: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, "Thực hiện đăng ký khác");
	}

	@Test
	public void TC_10_RutSoTietKiem_RutGocPhuongThucChuyenKhoanVaTienMat() {

		log.info("TC_10_Step: Click rut tiet kiem");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Rút tiết kiệm");

		log.info("TC_10_Step: Input dia chi lien he");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.LOCATION[0], "com.VCB:id/layoutDiaChiLH");

		log.info("TC_10_Step: Click giay to tuy than");
		registerOnline.clickToDynamicDropDown(driver, "Giấy tờ tùy thân");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[0]);

		log.info("TC_10_Step: Input so ho chieu");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.NO_IDENTIFICATION, "com.VCB:id/layoutCMTND");

		log.info("TC_10_Step: Click ngay cap");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayCap");

		log.info("TC_10_Step: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_10_Step: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(1));

		log.info("TC_10_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, "OK");

		log.info("TC_10_Step: Click noi cap");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNoiCap");
		registerOnline.inputIntoEditTextByID(driver, "THANH PHO CAN THO", "com.VCB:id/edtSearch");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "THANH PHO CAN THO");

		log.info("TC_10_Step: Nhap email");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.EMAIL, "com.VCB:id/layoutEmail");

		log.info("TC_10_Step: Nhap tai khoan ghi no");
		registerOnline.scrollIDownOneTime(driver);
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.ACCOUNT_TAKE[1], "com.VCB:id/layoutSoTaiKhoanGhiNo");

		log.info("TC_10_Step: Click chọn rut lai");
		registerOnline.clickToDynamicLinerLayoutID(driver, "com.VCB:id/layoutYeuCauRutTien");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_RETURN_MONEY[1]);

		log.info("TC_10_Step: Nhap so tien ");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.AMOUNT_OPEN_CARD, "com.VCB:id/layoutAmount1");

		log.info("TC_10_Step: Nhap so tai khoan");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.NOTE, "com.VCB:id/layoutNoiDung");

		log.info("TC_10_Step: Click chon chuyen khoan");
		registerOnline.clickToImageRadio(driver, "Chuyển khoản");
		registerOnline.scrollIDownOneTime(driver);

		log.info("TC_10_Step: check text mac dinh chon la tien mat");
		verifyEquals(registerOnline.getTextDynamicFollowLayout(driver, "com.VCB:id/llTienMat"), "Tiền mặt");

		log.info("TC_10_Step: check text da chon la chuyen khoan");
		verifyEquals(registerOnline.getTextDynamicFollowLayout(driver, "com.VCB:id/llChuyenKhoan"), "Chuyển khoản");

		log.info("TC_10_Step: Nhap so tien mat nop");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.AMOUNT, "com.VCB:id/layoutAmount2");

		log.info("TC_10_Step: Nhap so tai khoan");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.ACCOUNT_TAKE[1], "com.VCB:id/layoutSoTaiKhoan");

		log.info("TC_10_Step: Nhap ten chu tai khoan");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.ACC_NAME, "com.VCB:id/layoutTenChuTk");

		log.info("TC_10_Step: Nhap so tien nop");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.AMOUNT, "com.VCB:id/layoutAmount3");

		log.info("TC_10_Step: Click button xac nhan");
		registerOnline.clickToDynamicButton(driver, "Xác nhận");

		log.info("TC_10_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getDynamicTextInPopUp(driver, "ĐĂNG KÝ THÀNH CÔNG"), "ĐĂNG KÝ THÀNH CÔNG");

		log.info("TC_10_Step: verify text dieu kien");
		verifyEquals(registerOnline.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvNote"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_10_Step: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Chia sẻ"));

		log.info("TC_10_Step: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Lưu ảnh"));

		log.info("TC_10_Step: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, "Thực hiện đăng ký khác");

	}

	@Test
	public void TC_11_MoTaiKhoanThanhToan_ManHinhThongTinKhachHang() {

		log.info("TC_11_Step: Click giao dich mo tai khoan thanh toan");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Mở tài khoản thanh toán");

		log.info("TC_11_Step: Click ngay cap");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgaySinh");

		log.info("TC_11_Step: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_11_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, "OK");

		log.info("TC_11_Step: Click giay to tuy than");
		registerOnline.clickToDynamicDropDown(driver, "Giấy tờ tùy thân");

		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[0]);

		log.info("TC_11_Step: Input so ho chieu");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.NO_IDENTIFICATION, "com.VCB:id/layoutCMTND");

		log.info("TC_11_Step: Click ngay cap");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayCap");

		log.info("TC_11_Step: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_11_Step: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(1));

		log.info("TC_11_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, "OK");
		registerOnline.scrollIDownOneTime(driver);

		log.info("TC_11_Step: Click noi cap");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNoiCap");
		registerOnline.inputIntoEditTextByID(driver, "THANH PHO CAN THO", "com.VCB:id/edtSearch");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "THANH PHO CAN THO");

		log.info("TC_11_Step: Input dia chi hien tai");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.LOCATION[2], "com.VCB:id/layoutDiaChiHienTai");

		log.info("TC_11_Step: Input email");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.EMAIL, "com.VCB:id/layoutDiaChiEmail");

		log.info("TC_11_Step: Click button xac nhan");
		registerOnline.clickToDynamicButton(driver, "Xác nhận");

	}

	@Test
	public void TC_12_MoTaiKhoanThanhToan_ManHinhDangKyDichVuDienTu() {
		log.info("TC_12_Step: Verify text man hinh dang ky dich vu ngan hang dien tu");
		verifyEquals(registerOnline.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvTitleBar"), "Đăng ký dịch vụ ngân hàng điện tử");

		log.info("TC_12_Step: Select option VCB-iB@nKing");
		registerOnline.clickToDynamicButtonLinkOrLinkTextNotScroll(driver, "VCB-iB@nking");

		log.info("TC_12_Step: Select option VCB-SMS B@nking");
		registerOnline.clickToDynamicButtonLinkOrLinkTextNotScroll(driver, "VCB-SMS B@nking");

		log.info("TC_12_Step: Nhap so dien thoai");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, "0904797866", "com.VCB:id/inputSoDienThoai");
		registerOnline.clickToDynamicImageViewByID(driver, "com.VCB:id/ivAdd");

		log.info("TC_12_Step: Select option VCB-SMS B@nking");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "VCB-Mobile B@nking");

		log.info("TC_12_Step: Chon so dien thoai dang ky");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutSDTMB");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "0904797866");

		log.info("TC_12_Step: Click button tiep tuc");
		registerOnline.clickToDynamicButton(driver, "Tiếp tục");
	}

	@Test
	public void TC_13_MoTaiKhoanThanhToan_ManHinhConnect24_ChonMotThePhu() {
		log.info("TC_13_Step: Verify text man hinh dang ky phat hanh the connect24");
		verifyEquals(registerOnline.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvTitleBar"), "Đăng ký phát hành thẻ Connect24");

		log.info("TC_13_Step: Click hang the, chon hang the la vang");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutHangThe");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.CARD_RANK[1]);

		log.info("TC_13_Step: Click hang the, chon thanh toan phi");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutThanhToanPhi");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.FEE_PAYMENT[0]);

		log.info("TC_13_Step: Select option yeu cau phat hanh the phu");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/titleYeuCauPhatHanhThePhu");

		log.info("TC_13_Step: Nhap ho ten chu the");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.ACC_NAME, "com.VCB:id/layoutHoTenDayDu");

		log.info("TC_13_Step: Click giay to tuy than");
		registerOnline.clickToDynamicDropDown(driver, "Giấy tờ tùy thân");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[0]);

		log.info("TC_13_Step: Input so ho chieu");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.NO_IDENTIFICATION, "com.VCB:id/layoutCMTND");

		log.info("TC_13_Step: Click ngay cap");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayCap");

		log.info("TC_13_Step: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_13_Step: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(1));

		log.info("TC_13_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, "OK");

		log.info("TC_13_Step: Click noi cap");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNoiCap");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.LOCATION[0]);

		log.info("TC_13_Step: Click tiep tuc");
		registerOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_13_Step: Click radio toi dong y");
		registerOnline.clickToDynamicRadioIndex(driver, "agreement", "3");

		log.info("TC_13_Step: Click dong y");
		registerOnline.scrollIDownOneTime(driver);
		registerOnline.clickToDynamicDateInDateTimePicker(driver, "Đồng ý");

		log.info("TC_03_Step: verify man hinh dang ky thanh cong");
		verifyTrue(registerOnline.isDynamicMessageAndLabelTextDisplayed(driver, "ĐĂNG KÝ THÀNH CÔNG"));

		log.info("TC_03_Step: verify text dieu kien");
		verifyEquals(registerOnline.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvNote"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_13_Step: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Chia sẻ"));

		log.info("TC_13_Step: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Lưu ảnh"));

		log.info("TC_13_Step: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, "Thực hiện đăng ký khác");

	}

	@Test
	public void TC_14_MoTaiKhoanThanhToan_ManHinhConnect24_ChonHaiThePhu() {
		log.info("TC_14_Step: Click giao dich mo tai khoan thanh toan");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Mở tài khoản thanh toán");

		log.info("TC_11_Step: Click ngay cap");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgaySinh");

		log.info("TC_11_Step: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_11_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, "OK");

		log.info("TC_11_Step: Click giay to tuy than");
		registerOnline.clickToDynamicDropDown(driver, "Giấy tờ tùy thân");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[0]);

		log.info("TC_11_Step: Input so ho chieu");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.NO_IDENTIFICATION, "com.VCB:id/layoutCMTND");

		log.info("TC_14_Step: Click ngay cap");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayCap");

		log.info("TC_14_Step: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_14_Step: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(1));

		log.info("TC_14_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, "OK");

		log.info("TC_14_Step: Chon noi cap");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNoiCap");
		registerOnline.inputIntoEditTextByID(driver, Register_Online_data.Valid_Account.LOCATION[0], "com.VCB:id/edtSearch");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.LOCATION[0]);

		log.info("TC_14_Step: Input dia chi hien tai");
		registerOnline.scrollIDownOneTime(driver);
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.LOCATION[0], "com.VCB:id/layoutDiaChiHienTai");

		log.info("TC_14_Step: verify so account");
		verifyEquals(registerOnline.getTextEditViewByLinearLayoutID(driver, "com.VCB:id/layoutSoDienThoai"), phoneNumber);

		log.info("TC_14_Step: Nhap email");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.EMAIL, "com.VCB:id/layoutDiaChiEmail");

		log.info("TC_14_Step: Click button xac nhan");
		registerOnline.clickToDynamicButton(driver, "Xác nhận");

		log.info("TC_14_Step: Verify text man hinh dang ky dich vu ngan hang dien tu");
		verifyEquals(registerOnline.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvTitleBar"), "Đăng ký dịch vụ ngân hàng điện tử");

		log.info("TC_14_Step: Select option VCB-iB@nKing");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "VCB-iB@nking");

		log.info("TC_14_Step: Select option VCB-SMS B@nking");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "VCB-SMS B@nking");

		log.info("TC_14_Step: Nhap so dien thoai");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, "0904797866", "com.VCB:id/inputSoDienThoai");
		registerOnline.clickToDynamicImageViewByID(driver, "com.VCB:id/ivAdd");

		log.info("TC_14_Step: Select option VCB-SMS B@nking");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "VCB-Mobile B@nking");

		log.info("TC_14_Step: Chon so dien thoai dang ky");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutSDTMB");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "0904797866");

		log.info("TC_14_Step: Click button tiep tuc");
		registerOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_14_Step: Verify text man hinh dang ky phat hanh the connect24");
		verifyEquals(registerOnline.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvTitleBar"), "Đăng ký phát hành thẻ Connect24");

		log.info("TC_14_Step: Click hang the, chon hang the la vang");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutHangThe");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.CARD_RANK[1]);

		log.info("TC_14_Step: Click chon thanh toan phi");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutThanhToanPhi");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.FEE_PAYMENT[0]);

		log.info("TC_14_Step: Select option yeu cau phat hanh the phu");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/titleYeuCauPhatHanhThePhu");

		log.info("TC_14_Step: Select option yeu cau phat hanh the phu");
		registerOnline.clickToTextID(driver, "com.VCB:id/btnThe2");

		log.info("TC_14_Step: Nhap ho ten chu the");
		registerOnline.inputToDynamicEditviewBy2LinearlayoutId(driver, Register_Online_data.Valid_Account.ACC_NAME, "com.VCB:id/layoutThongTinChuThe1", "com.VCB:id/layoutHoTenDayDu");

		log.info("TC_14_Step: Click giay to tuy than CMT");
		registerOnline.clickToDynamicDropDown(driver, "Giấy tờ tùy thân");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[0]);

		log.info("TC_14_Step: Input so CMT");
		registerOnline.inputToDynamicEditviewBy2LinearlayoutId(driver, Register_Online_data.Valid_Account.NO_IDENTIFICATION, "com.VCB:id/layoutThongTinChuThe1", "com.VCB:id/layoutCMTND");

		log.info("TC_14_Step: Click ngay cap");
		registerOnline.clickToTextViewBy2LinearLayoutID(driver, "com.VCB:id/layoutThongTinChuThe1", "com.VCB:id/layoutNgayCap");
		registerOnline.scrollIDownOneTime(driver);

		log.info("TC_14_Step: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_14_Step: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(1));

		log.info("TC_14_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, "OK");

		log.info("TC_14_Step: Click noi cap");
		registerOnline.clickToTextViewBy2LinearLayoutID(driver, "com.VCB:id/layoutThongTinChuThe1", "com.VCB:id/layoutNoiCap");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.LOCATION[0]);

		log.info("TC_14_Step: Nhap ho ten chu the");
		registerOnline.inputToDynamicEditviewBy2LinearlayoutId(driver, Register_Online_data.Valid_Account.CUSTOMER_NAME_ACCEPT, "com.VCB:id/layoutThongTinChuThe2", "com.VCB:id/layoutHoTenDayDu");

		log.info("TC_14_Step: Click giay to tuy than");
		registerOnline.clickToTextViewBy2LinearLayoutID(driver, "com.VCB:id/layoutThongTinChuThe2", "com.VCB:id/layoutLoaiXacNhan");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[0]);

		log.info("TC_14_Step: Input so ho chieu");
		registerOnline.inputToDynamicEditviewBy2LinearlayoutId(driver, Register_Online_data.Valid_Account.NO_IDENTIFICATION, "com.VCB:id/layoutThongTinChuThe2", "com.VCB:id/layoutCMTND");

		log.info("TC_14_Step: Click ngay cap");
		registerOnline.clickToTextViewBy2LinearLayoutID(driver, "com.VCB:id/layoutThongTinChuThe2", "com.VCB:id/layoutNgayCap");

		log.info("TC_14_Step: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_14_Step: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(1));

		log.info("TC_14_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, "OK");

		log.info("TC_14_Step: Click noi cap");
		registerOnline.clickToTextViewBy2LinearLayoutID(driver, "com.VCB:id/layoutThongTinChuThe2", "com.VCB:id/layoutNoiCap");
		registerOnline.inputIntoEditTextByID(driver, Register_Online_data.Valid_Account.LOCATION[0], "com.VCB:id/edtSearch");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.LOCATION[0]);

		log.info("TC_14_Step: Click tiep tuc");
		registerOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_14_Step: Click radio toi dong y");
		registerOnline.clickToDynamicRadioIndex(driver, "agreement", "3");

		log.info("TC_14_Step: Click dong y");
		registerOnline.clickToDynamicDateInDateTimePicker(driver, "Đồng ý");

		log.info("TC_14_Step: verify man hinh dang ky thanh cong");
		verifyTrue(registerOnline.isDynamicMessageAndLabelTextDisplayed(driver, "ĐĂNG KÝ THÀNH CÔNG"));

		log.info("TC_14_Step: verify text dieu kien");
		verifyEquals(registerOnline.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvNote"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_14_Step: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Chia sẻ"));

		log.info("TC_14_Step: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Lưu ảnh"));

		log.info("TC_14_Step: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, "Thực hiện đăng ký khác");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
