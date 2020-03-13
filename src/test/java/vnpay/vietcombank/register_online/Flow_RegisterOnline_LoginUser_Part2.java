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
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.Register_Online_data;

public class Flow_RegisterOnline_LoginUser_Part2 extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private RegisterOnlinePageObject registerOnline;
	String phoneNumber = "";

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
		phoneNumber = phone;
		registerOnline = PageFactoryManager.getRegisterOnlinePageObject(driver);
	}

@Test
	public void TC_01_DangKyDichVuNganHangDienTu_ManHinhDangKyDVNHDT() throws InterruptedException {
		log.info("TC_Step_: Click menu header");
		registerOnline.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_Step_: Click chuc nang dang ky truc tuyen");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Đăng ký trực tuyến");

		log.info("TC_Step_: Click giao dich tien mat/chuyen tien");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Đăng ký dịch vụ ngân hàng điện tử/Đăng ký phát hành thẻ ghi nợ");

		log.info("TC_Step_: Click dang ky dich vu ngan hang dien tu");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Đăng ký dịch vụ ngân hàng điện tử");

		log.info("TC_Step_: verify ten khach hang, defaul fill ten khach hang");
		verifyEquals(registerOnline.getDynamicTextInInputBoxByHeader(driver, "Thông tin khách hàng", "1"), Register_Online_data.Valid_Account.ACC_NAME);

		log.info("TC_Step_: Click giay to tuy than");
		registerOnline.clickToDynamicDropDown(driver, "Giấy tờ tùy thân");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[1]);

		log.info("TC_Step_: Input so ho chieu");
		registerOnline.inputToDynamicInputText(driver, Register_Online_data.Valid_Account.NO_IDENTIFICATION, "Giấy tờ tùy thân", "6");

		log.info("TC_Step_: Click ngay cap");
		registerOnline.clickToTextViewDate(driver, "Giấy tờ tùy thân", "7");

		log.info("TC_Step_: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_Step_: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(2));

		log.info("TC_Step_: Click OK");
		registerOnline.clickToDynamicButton(driver, "OK");

		log.info("TC_Step_: Input noi cap");
		registerOnline.inputToDynamicInputText(driver, Register_Online_data.Valid_Account.LOCATION[1], "Giấy tờ tùy thân", "8");

		log.info("TC_Step_: verify so account");
		System.out.print(registerOnline.getTextDynamicFollowIndex(driver, "Giấy tờ tùy thân", "10"));
		verifyEquals(registerOnline.getTextDynamicFollowIndex(driver, "Giấy tờ tùy thân", "10"), phoneNumber);

		log.info("TC_Step_: Nhap email");
		registerOnline.inputToDynamicInputText(driver, Register_Online_data.Valid_Account.EMAIL, "Giấy tờ tùy thân", "11");

		log.info("TC_Step_: Click button xac nhan");
		registerOnline.clickToDynamicButton(driver, "Xác nhận");
	}

@Test
	public void TC_02_DangKyDichVuNganHangDienTu_ManHinhDangKyDichVuDienTu() throws InterruptedException {
		log.info("TC_Step_: Verify text man hinh dang ky dich vu ngan hang dien tu");
		verifyEquals(registerOnline.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvTitleBar"), "Đăng ký dịch vụ ngân hàng điện tử");

		log.info("TC_Step_: Select option VCB-iB@nKing");
		registerOnline.clickToDynamicTextIndex(driver, "0", "VCB-iB@nking");

		log.info("TC_Step_: Select option VCB-SMS B@nking");
		registerOnline.clickToDynamicTextIndex(driver, "1", "VCB-SMS B@nking");

		log.info("TC_Step_: Nhap so dien thoai");
		registerOnline.inputToDynamicInputBoxByHeader(driver, phoneNumber, "VCB-SMS B@nking", "1");
		registerOnline.clickToDynamicImageViewByID(driver, "com.VCB:id/ivAdd");

		log.info("TC_Step_: Select option VCB-SMS B@nking");
		registerOnline.clickToDynamicTextIndex(driver, "2", "VCB-Mobile B@nking");

		log.info("TC_Step_: Chon so dien thoai dang ky");
		registerOnline.clickToDynamicComboboxText(driver, "VCB-Mobile B@nking", "1");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, phoneNumber);

		log.info("TC_Step_: Chon tai khoan dang ky");
		registerOnline.clickToDynamicComboboxText(driver, "Tài khoản đăng ký", "1");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_Step_: Click button tiep tuc");
		registerOnline.clickToDynamicButton(driver, "Tiếp tục");
		Thread.sleep(20000);

		log.info("TC_Step_: Click radio toi dong y");
		registerOnline.clickToDynamicRadioIndex(driver, "agreement","3");

		log.info("TC_Step_: Click dong y");
		registerOnline.clickToDynamicDateInDateTimePicker(driver, "Đồng ý");
		Thread.sleep(40000);

		log.info("TC_Step_: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "2"), "ĐĂNG KÝ THÀNH CÔNG");

		log.info("TC_Step_: verify text dieu kien");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "5"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_Step_: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Chia sẻ"));

		log.info("TC_Step_: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Lưu ảnh"));
		Thread.sleep(2000);

		log.info("TC_Step_: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, "Thực hiện đăng ký khác");
	}

	@Test
	public void TC_03_DangKyPhatHanhTheGhiNo_ManHinhThongTinKhachHang() throws InterruptedException {
		log.info("TC_Step_: Click menu header");
		registerOnline.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_Step_: Click chuc nang dang ky truc tuyen");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Đăng ký trực tuyến");

		log.info("TC_Step_: Click giao dich tien mat/chuyen tien");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Đăng ký dịch vụ ngân hàng điện tử/Đăng ký phát hành thẻ ghi nợ");

		log.info("TC_Step_: Click phat hanh the ghi no");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Đăng ký phát hành thẻ ghi nợ");

		log.info("TC_Step_: verify ten khach hang, defaul fill ten khach hang");
		verifyEquals(registerOnline.getDynamicTextInInputBoxByHeader(driver, "Thông tin khách hàng", "1"), Register_Online_data.Valid_Account.ACC_NAME);

		log.info("TC_Step_: Click giay to tuy than");
		registerOnline.clickToDynamicDropDown(driver, "Giấy tờ tùy thân");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[1]);

		log.info("TC_Step_: Input so ho chieu");
		registerOnline.inputToDynamicInputText(driver, Register_Online_data.Valid_Account.NO_IDENTIFICATION, "Giấy tờ tùy thân", "6");

		log.info("TC_Step_: Click ngay cap");
		registerOnline.clickToTextViewDate(driver, "Giấy tờ tùy thân", "7");

		log.info("TC_Step_: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_Step_: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_Step_: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(2));

		log.info("TC_Step_: Click OK");
		registerOnline.clickToDynamicButton(driver, "OK");

		log.info("TC_Step_: Input noi cap");
		registerOnline.inputToDynamicInputText(driver, Register_Online_data.Valid_Account.LOCATION[1], "Giấy tờ tùy thân", "8");

		log.info("TC_Step_: verify so account");
		System.out.print(registerOnline.getTextDynamicFollowIndex(driver, "Giấy tờ tùy thân", "10"));
		verifyEquals(registerOnline.getTextDynamicFollowIndex(driver, "Giấy tờ tùy thân", "10"), phoneNumber);

		log.info("TC_Step_: Nhap email");
		registerOnline.inputToDynamicInputText(driver, Register_Online_data.Valid_Account.EMAIL, "Giấy tờ tùy thân", "11");

		log.info("TC_Step_: Click button xac nhan");
		registerOnline.clickToDynamicButton(driver, "Xác nhận");
	}

	@Test
	public void TC_04_DangKyPhatHanhTheGhiNo_ChonHangChuanTheGhiNoVCBconnectNopTienMat() throws InterruptedException {
		log.info("TC_Step_: Verify text man hinh dang ky phat hanh the ghi no");
		verifyEquals(registerOnline.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvTitleBar"), "Đăng ký phát hành thẻ ghi nợ");

		log.info("TC_Step_: Click hang the, chon hang the la vang");
		registerOnline.clickToTextViewDate(driver, "Hạng thẻ", "1");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.CARD_RANK[0]);

		log.info("TC_Step_: Click chon loai thanh toan phi");
		registerOnline.clickToTextViewDate(driver, "Thanh toán phí", "4");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.FEE_PAYMENT[1]);

		log.info("TC_Step_: Select yeu cau phat hanh the phu");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Yêu cầu phát hành thẻ phụ");

		log.info("TC_Step_: Nhap ho ten chu the");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.ACC_NAME, "Chủ thẻ phụ 1", "1");

		log.info("TC_Step_: Click giay to tuy than");
		registerOnline.clickToDynamicDropDown(driver, "Giấy tờ tùy thân");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[1]);

		log.info("TC_Step_: Input so ho chieu");
		registerOnline.inputToDynamicInputText(driver, Register_Online_data.Valid_Account.NO_IDENTIFICATION, "Giấy tờ tùy thân", "3");

		log.info("TC_Step_: Click ngay cap");
		registerOnline.clickToTextViewDate(driver, "Giấy tờ tùy thân", "4");

		log.info("TC_Step_: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_Step_: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_Step_: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(5));

		log.info("TC_Step_: Click OK");
		registerOnline.clickToDynamicButton(driver, "OK");

		log.info("TC_Step_: Input noi cap");
		registerOnline.inputToDynamicInputText(driver, Register_Online_data.Valid_Account.LOCATION[1], "Giấy tờ tùy thân", "5");

		log.info("TC_Step_: Click tiep tuc");
		registerOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_Step_: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "2"), "ĐĂNG KÝ THÀNH CÔNG");

		log.info("TC_Step_: verify text dieu kien");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "5"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_Step_: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Chia sẻ"));

		log.info("TC_Step_: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Lưu ảnh"));
		Thread.sleep(2000);

		log.info("TC_Step_: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, "Thực hiện đăng ký khác");
	}

	@Test
	public void TC_05_DangKyPhatHanhTheGhiNo_ChonHangChuanTheVCBCashBankPlusAmericalTrichNoTuDongTuTK() throws InterruptedException {
		log.info("TC_Step_: Nhap thong tin man hinh dang ky");
		registerOnline.DangKyPhatHanhTheGhiNo(Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[1], Register_Online_data.Valid_Account.NO_IDENTIFICATION, getBackWardYear(2), Register_Online_data.Valid_Account.LOCATION[1], Register_Online_data.Valid_Account.EMAIL);

		log.info("TC_Step_: Verify text man hinh dang ky phat hanh the ghi no");
		verifyEquals(registerOnline.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvTitleBar"), "Đăng ký phát hành thẻ ghi nợ");

		log.info("TC_Step_: Click hang the, chon hang the la vang");
		registerOnline.clickToTextViewDate(driver, "Hạng thẻ", "1");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.CARD_RANK[0]);

		log.info("TC_Step_: Click loại the ghi no");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Vietcombank CardPlus American Express");

		log.info("TC_Step_: Click chon loai thanh toan phi");
		registerOnline.clickToTextViewDate(driver, "Thanh toán phí", "4");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.FEE_PAYMENT[0]);

		log.info("TC_Step_: Click tiep tuc");
		registerOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_Step_: verify man hinh dang ky thanh cong");
		Thread.sleep(50000);
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "2"), "ĐĂNG KÝ THÀNH CÔNG");

		log.info("TC_Step_: verify text dieu kien");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "5"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_Step_: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Chia sẻ"));

		log.info("TC_Step_: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Lưu ảnh"));
		Thread.sleep(2000);

		log.info("TC_Step_: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, "Thực hiện đăng ký khác");
	}

	@Test
	public void TC_06_DangKyPhatHanhTheGhiNo_ChonHangChuanVCBMasterCardNopTienMat() throws InterruptedException {
		log.info("TC_Step_: Nhap thong tin man hinh dang ky");
		registerOnline.DangKyPhatHanhTheGhiNo(Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[1], Register_Online_data.Valid_Account.NO_IDENTIFICATION, getBackWardYear(2), Register_Online_data.Valid_Account.LOCATION[1], Register_Online_data.Valid_Account.EMAIL);

		log.info("TC_Step_: Verify text man hinh dang ky phat hanh the ghi no");
		verifyEquals(registerOnline.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvTitleBar"), "Đăng ký phát hành thẻ ghi nợ");

		log.info("TC_Step_: Click hang the, chon hang the la vang");
		registerOnline.clickToTextViewDate(driver, "Hạng thẻ", "1");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.CARD_RANK[0]);

		log.info("TC_Step_: Click loại the ghi no");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Vietcombank MasterCard");

		log.info("TC_Step_: Click chon loai thanh toan phi");
		registerOnline.clickToTextViewDate(driver, "Thanh toán phí", "4");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.FEE_PAYMENT[1]);

		log.info("TC_Step_: Select yeu cau phat hanh the phu");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Yêu cầu phát hành thẻ phụ");

		log.info("TC_Step_: Nhap ho ten chu the");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.ACC_NAME, "Chủ thẻ phụ 1", "1");

		log.info("TC_Step_: Click giay to tuy than");
		registerOnline.clickToDynamicDropDown(driver, "Giấy tờ tùy thân");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[1]);

		log.info("TC_Step_: Input so ho chieu");
		registerOnline.inputToDynamicInputText(driver, Register_Online_data.Valid_Account.NO_IDENTIFICATION, "Giấy tờ tùy thân", "3");

		log.info("TC_Step_: Click ngay cap");
		registerOnline.clickToTextViewDate(driver, "Giấy tờ tùy thân", "4");

		log.info("TC_Step_: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_Step_: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_Step_: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(5));

		log.info("TC_Step_: Click OK");
		registerOnline.clickToDynamicButton(driver, "OK");

		log.info("TC_Step_: Input noi cap");
		registerOnline.inputToDynamicInputText(driver, Register_Online_data.Valid_Account.LOCATION[1], "Giấy tờ tùy thân", "5");

		log.info("TC_Step_: Click tiep tuc");
		registerOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_Step_: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "2"), "ĐĂNG KÝ THÀNH CÔNG");

		log.info("TC_Step_: verify text dieu kien");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "5"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_Step_: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Chia sẻ"));

		log.info("TC_Step_: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Lưu ảnh"));
		Thread.sleep(2000);

		log.info("TC_Step_: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, "Thực hiện đăng ký khác");
	}

	@Test
	public void TC_07_DangKyPhatHanhTheGhiNo_ChonHangChuanVCBConnect24VISATrichNoTuDongTuTK() throws InterruptedException {
		log.info("TC_Step_: Nhap thong tin man hinh dang ky");
		registerOnline.DangKyPhatHanhTheGhiNo(Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[1], Register_Online_data.Valid_Account.NO_IDENTIFICATION, getBackWardYear(2), Register_Online_data.Valid_Account.LOCATION[1], Register_Online_data.Valid_Account.EMAIL);

		log.info("TC_Step_: Verify text man hinh dang ky phat hanh the ghi no");
		verifyEquals(registerOnline.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvTitleBar"), "Đăng ký phát hành thẻ ghi nợ");

		log.info("TC_Step_: Click hang the, chon hang the la vang");
		registerOnline.clickToTextViewDate(driver, "Hạng thẻ", "1");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.CARD_RANK[0]);

		log.info("TC_Step_: Click loại the ghi no");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Vietcombank Connect24 Visa");

		log.info("TC_Step_: Click chon loai thanh toan phi");
		registerOnline.clickToTextViewDate(driver, "Thanh toán phí", "4");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.FEE_PAYMENT[0]);

		log.info("TC_Step_: Select yeu cau phat hanh the phu");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Yêu cầu phát hành thẻ phụ");

		log.info("TC_Step_: Nhap ho ten chu the");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.ACC_NAME, "Chủ thẻ phụ 1", "1");

		log.info("TC_Step_: Click giay to tuy than");
		registerOnline.clickToDynamicDropDown(driver, "Giấy tờ tùy thân");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[1]);

		log.info("TC_Step_: Input so ho chieu");
		registerOnline.inputToDynamicInputText(driver, Register_Online_data.Valid_Account.NO_IDENTIFICATION, "Giấy tờ tùy thân", "3");

		log.info("TC_Step_: Click ngay cap");
		registerOnline.clickToTextViewDate(driver, "Giấy tờ tùy thân", "4");

		log.info("TC_Step_: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_Step_: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_Step_: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(5));

		log.info("TC_Step_: Click OK");
		registerOnline.clickToDynamicButton(driver, "OK");

		log.info("TC_Step_: Input noi cap");
		registerOnline.inputToDynamicInputText(driver, Register_Online_data.Valid_Account.LOCATION[1], "Giấy tờ tùy thân", "5");

		log.info("TC_Step_: Click tiep tuc");
		registerOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_Step_: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "2"), "ĐĂNG KÝ THÀNH CÔNG");

		log.info("TC_Step_: verify text dieu kien");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "5"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_Step_: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Chia sẻ"));

		log.info("TC_Step_: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Lưu ảnh"));
		Thread.sleep(2000);

		log.info("TC_Step_: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, "Thực hiện đăng ký khác");
	}

	@Test
	public void TC_08_DangKyPhatHanhTheGhiNo_ChonHangChuanUnionPAYNopTienMat() throws InterruptedException {
		log.info("TC_Step_: Nhap thong tin man hinh dang ky");
		registerOnline.DangKyPhatHanhTheGhiNo(Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[1], Register_Online_data.Valid_Account.NO_IDENTIFICATION, getBackWardYear(2), Register_Online_data.Valid_Account.LOCATION[1], Register_Online_data.Valid_Account.EMAIL);

		log.info("TC_Step_: Verify text man hinh dang ky phat hanh the ghi no");
		verifyEquals(registerOnline.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvTitleBar"), "Đăng ký phát hành thẻ ghi nợ");

		log.info("TC_Step_: Click hang the, chon hang the la vang");
		registerOnline.clickToTextViewDate(driver, "Hạng thẻ", "1");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.CARD_RANK[0]);

		log.info("TC_Step_: Click loại the ghi no");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Vietcombank UnionPay");

		log.info("TC_Step_: Click chon loai thanh toan phi");
		registerOnline.clickToTextViewDate(driver, "Thanh toán phí", "4");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.FEE_PAYMENT[1]);

		log.info("TC_Step_: Click tiep tuc");
		registerOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_Step_: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "2"), "ĐĂNG KÝ THÀNH CÔNG");

		log.info("TC_Step_: verify text dieu kien");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "5"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_Step_: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Chia sẻ"));

		log.info("TC_Step_: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Lưu ảnh"));
		Thread.sleep(2000);

		log.info("TC_Step_: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, "Thực hiện đăng ký khác");
	}

	@Test
	public void TC_09_DangKyPhatHanhTheGhiNo_ChonHangChuanVCBConnect24NopTienMatPhatHanhThePhu() throws InterruptedException {
		log.info("TC_Step_: Nhap thong tin man hinh dang ky");
		registerOnline.DangKyPhatHanhTheGhiNo(Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[1], Register_Online_data.Valid_Account.NO_IDENTIFICATION, getBackWardYear(2), Register_Online_data.Valid_Account.LOCATION[1], Register_Online_data.Valid_Account.EMAIL);

		log.info("TC_Step_: Verify text man hinh dang ky phat hanh the ghi no");
		verifyEquals(registerOnline.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvTitleBar"), "Đăng ký phát hành thẻ ghi nợ");

		log.info("TC_Step_: Click hang the, chon hang the la vang");
		registerOnline.clickToTextViewDate(driver, "Hạng thẻ", "1");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.CARD_RANK[0]);

		log.info("TC_Step_: Click chon loai thanh toan phi");
		registerOnline.clickToTextViewDate(driver, "Thanh toán phí", "4");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.FEE_PAYMENT[1]);

		log.info("TC_Step_: Select yeu cau phat hanh the phu");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Yêu cầu phát hành thẻ phụ");

		log.info("TC_Step_: Nhap ho ten chu the");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.ACC_NAME, "Chủ thẻ phụ 1", "1");

		log.info("TC_Step_: Click giay to tuy than");
		registerOnline.clickToDynamicDropDown(driver, "Giấy tờ tùy thân");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[1]);

		log.info("TC_Step_: Input so ho chieu");
		registerOnline.inputToDynamicInputText(driver, Register_Online_data.Valid_Account.NO_IDENTIFICATION, "Giấy tờ tùy thân", "3");

		log.info("TC_Step_: Click ngay cap");
		registerOnline.clickToTextViewDate(driver, "Giấy tờ tùy thân", "4");

		log.info("TC_Step_: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_Step_: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_Step_: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(5));

		log.info("TC_Step_: Click OK");
		registerOnline.clickToDynamicButton(driver, "OK");

		log.info("TC_Step_: Input noi cap");
		registerOnline.inputToDynamicInputText(driver, Register_Online_data.Valid_Account.LOCATION[1], "Giấy tờ tùy thân", "5");

		log.info("TC_Step_: Click tiep tuc");
		registerOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_Step_: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "2"), "ĐĂNG KÝ THÀNH CÔNG");

		log.info("TC_Step_: verify text dieu kien");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "5"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_Step_: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Chia sẻ"));

		log.info("TC_Step_: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Lưu ảnh"));
		Thread.sleep(2000);

		log.info("TC_Step_: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, "Thực hiện đăng ký khác");
	}

	@Test
	public void TC_10_DangKyPhatHanhTheGhiNo_ChonHangChuanVCBCashbankPlusAmericalNopTienMatKhongPhatThePhu() throws InterruptedException {
		log.info("TC_Step_: Nhap thong tin man hinh dang ky");
		registerOnline.DangKyPhatHanhTheGhiNo(Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[1], Register_Online_data.Valid_Account.NO_IDENTIFICATION, getBackWardYear(2), Register_Online_data.Valid_Account.LOCATION[1], Register_Online_data.Valid_Account.EMAIL);

		log.info("TC_Step_: Verify text man hinh dang ky phat hanh the ghi no");
		verifyEquals(registerOnline.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvTitleBar"), "Đăng ký phát hành thẻ ghi nợ");

		log.info("TC_Step_: Click hang the, chon hang the la vang");
		registerOnline.clickToTextViewDate(driver, "Hạng thẻ", "1");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.CARD_RANK[0]);

		log.info("TC_Step_: Click loại the ghi no");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Vietcombank CardPlus American Express");

		log.info("TC_Step_: Click chon loai thanh toan phi");
		registerOnline.clickToTextViewDate(driver, "Thanh toán phí", "4");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.FEE_PAYMENT[1]);

		log.info("TC_Step_: Click tiep tuc");
		registerOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_Step_: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "2"), "ĐĂNG KÝ THÀNH CÔNG");

		log.info("TC_Step_: verify text dieu kien");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "5"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_Step_: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Chia sẻ"));

		log.info("TC_Step_: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Lưu ảnh"));
		Thread.sleep(2000);

		log.info("TC_Step_: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, "Thực hiện đăng ký khác");
	}

	@Test
	public void TC_11_DangKyPhatHanhTheGhiNo_ChonHangChuanVCBMasterCardTrichNoTuDongPhatHanhThePhu() throws InterruptedException {
		log.info("TC_Step_: Nhap thong tin man hinh dang ky");
		registerOnline.DangKyPhatHanhTheGhiNo(Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[1], Register_Online_data.Valid_Account.NO_IDENTIFICATION, getBackWardYear(2), Register_Online_data.Valid_Account.LOCATION[1], Register_Online_data.Valid_Account.EMAIL);

		log.info("TC_Step_: Verify text man hinh dang ky phat hanh the ghi no");
		verifyEquals(registerOnline.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvTitleBar"), "Đăng ký phát hành thẻ ghi nợ");

		log.info("TC_Step_: Click hang the, chon hang the la vang");
		registerOnline.clickToTextViewDate(driver, "Hạng thẻ", "1");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.CARD_RANK[0]);

		log.info("TC_Step_: Click loại the ghi no");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Vietcombank MasterCard");

		log.info("TC_Step_: Click chon loai thanh toan phi");
		registerOnline.clickToTextViewDate(driver, "Thanh toán phí", "4");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.FEE_PAYMENT[0]);

		log.info("TC_Step_: Select yeu cau phat hanh the phu");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Yêu cầu phát hành thẻ phụ");

		log.info("TC_Step_: Nhap ho ten chu the");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.ACC_NAME, "Chủ thẻ phụ 1", "1");

		log.info("TC_Step_: Click giay to tuy than");
		registerOnline.clickToDynamicDropDown(driver, "Giấy tờ tùy thân");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[1]);

		log.info("TC_Step_: Input so ho chieu");
		registerOnline.inputToDynamicInputText(driver, Register_Online_data.Valid_Account.NO_IDENTIFICATION, "Giấy tờ tùy thân", "3");

		log.info("TC_Step_: Click ngay cap");
		registerOnline.clickToTextViewDate(driver, "Giấy tờ tùy thân", "4");

		log.info("TC_Step_: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_Step_: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_Step_: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(5));

		log.info("TC_Step_: Click OK");
		registerOnline.clickToDynamicButton(driver, "OK");

		log.info("TC_Step_: Input noi cap");
		registerOnline.inputToDynamicInputText(driver, Register_Online_data.Valid_Account.LOCATION[1], "Giấy tờ tùy thân", "5");

		log.info("TC_Step_: Click tiep tuc");
		registerOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_Step_: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "2"), "ĐĂNG KÝ THÀNH CÔNG");

		log.info("TC_Step_: verify text dieu kien");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "5"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_Step_: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Chia sẻ"));

		log.info("TC_Step_: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Lưu ảnh"));
		Thread.sleep(2000);

		log.info("TC_Step_: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, "Thực hiện đăng ký khác");
	}

	@Test
	public void TC_12_DangKyPhatHanhTheGhiNo_ChonHangChuanConnect24VISATrichNoTuDongBoPhatHanhThePhu() throws InterruptedException {
		log.info("TC_Step_: Nhap thong tin man hinh dang ky");
		registerOnline.DangKyPhatHanhTheGhiNo(Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[1], Register_Online_data.Valid_Account.NO_IDENTIFICATION, getBackWardYear(2), Register_Online_data.Valid_Account.LOCATION[1], Register_Online_data.Valid_Account.EMAIL);

		log.info("TC_Step_: Verify text man hinh dang ky phat hanh the ghi no");
		verifyEquals(registerOnline.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvTitleBar"), "Đăng ký phát hành thẻ ghi nợ");

		log.info("TC_Step_: Click hang the, chon hang the la vang");
		registerOnline.clickToTextViewDate(driver, "Hạng thẻ", "1");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.CARD_RANK[0]);

		log.info("TC_Step_: Click loại the ghi no");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Vietcombank Connect24 Visa");

		log.info("TC_Step_: Click chon loai thanh toan phi");
		registerOnline.clickToTextViewDate(driver, "Thanh toán phí", "4");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.FEE_PAYMENT[0]);

		log.info("TC_Step_: Click tiep tuc");
		registerOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_Step_: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "2"), "ĐĂNG KÝ THÀNH CÔNG");

		log.info("TC_Step_: verify text dieu kien");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "5"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_Step_: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Chia sẻ"));

		log.info("TC_Step_: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Lưu ảnh"));
		Thread.sleep(2000);

		log.info("TC_Step_: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, "Thực hiện đăng ký khác");
	}

	@Test
	public void TC_13_DangKyPhatHanhTheGhiNo_ChonHangChuanUnionPAYNopTienMatBoPhatHanhThePhu() throws InterruptedException {
		log.info("TC_Step_: Nhap thong tin man hinh dang ky");
		registerOnline.DangKyPhatHanhTheGhiNo(Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[1], Register_Online_data.Valid_Account.NO_IDENTIFICATION, getBackWardYear(2), Register_Online_data.Valid_Account.LOCATION[1], Register_Online_data.Valid_Account.EMAIL);

		log.info("TC_Step_: Verify text man hinh dang ky phat hanh the ghi no");
		verifyEquals(registerOnline.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvTitleBar"), "Đăng ký phát hành thẻ ghi nợ");

		log.info("TC_Step_: Click hang the, chon hang the la vang");
		registerOnline.clickToTextViewDate(driver, "Hạng thẻ", "1");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.CARD_RANK[1]);

		log.info("TC_Step_: Click loại the ghi no");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Vietcombank UnionPay");

		log.info("TC_Step_: Click chon loai thanh toan phi");
		registerOnline.clickToTextViewDate(driver, "Thanh toán phí", "4");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.FEE_PAYMENT[0]);

		log.info("TC_Step_: Select yeu cau phat hanh the phu");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Yêu cầu phát hành thẻ phụ");

		log.info("TC_Step_: Nhap ho ten chu the");
		registerOnline.inputToDynamicInputBoxByHeader(driver, Register_Online_data.Valid_Account.ACC_NAME, "Chủ thẻ phụ 1", "1");

		log.info("TC_Step_: Click giay to tuy than");
		registerOnline.clickToDynamicDropDown(driver, "Giấy tờ tùy thân");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[1]);

		log.info("TC_Step_: Input so ho chieu");
		registerOnline.inputToDynamicInputText(driver, Register_Online_data.Valid_Account.NO_IDENTIFICATION, "Giấy tờ tùy thân", "3");

		log.info("TC_Step_: Click ngay cap");
		registerOnline.clickToTextViewDate(driver, "Giấy tờ tùy thân", "4");

		log.info("TC_Step_: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_Step_: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_Step_: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(5));

		log.info("TC_Step_: Click OK");
		registerOnline.clickToDynamicButton(driver, "OK");

		log.info("TC_Step_: Input noi cap");
		registerOnline.inputToDynamicInputText(driver, Register_Online_data.Valid_Account.LOCATION[1], "Giấy tờ tùy thân", "5");

		log.info("TC_Step_: Click tiep tuc");
		registerOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_Step_: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "2"), "ĐĂNG KÝ THÀNH CÔNG");

		log.info("TC_Step_: verify text dieu kien");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "5"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_Step_: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Chia sẻ"));

		log.info("TC_Step_: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Lưu ảnh"));
		Thread.sleep(2000);

		log.info("TC_Step_: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, "Thực hiện đăng ký khác");
	}
}
