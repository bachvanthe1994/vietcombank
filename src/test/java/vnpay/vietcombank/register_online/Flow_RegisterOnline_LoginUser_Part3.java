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

public class Flow_RegisterOnline_LoginUser_Part3 extends Base {
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
	}

	@Test
	public void TC_01_DangKyPhatHanhTheGhiNo_ChonHangVangConnect24NopTienMatPhatHanhThePhu() {
		log.info("TC_1_Step: Click menu header");
		registerOnline.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_01_Step: Lay ten user");
		nameCustomer = registerOnline.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvFullname");

		log.info("TC_1_Step: Click chuc nang dang ky truc tuyen");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Đăng ký trực tuyến");

		log.info("TC_1_Step: Click giao dich tien mat/chuyen tien");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Đăng ký dịch vụ ngân hàng điện tử/Đăng ký phát hành thẻ ghi nợ");

		log.info("TC_1_Step: Nhap thong tin man hinh dang ky");
		registerOnline.DangKyPhatHanhTheGhiNo(Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[1], Register_Online_data.Valid_Account.NO_IDENTIFICATION, getBackWardYear(2), Register_Online_data.Valid_Account.LOCATION[1], Register_Online_data.Valid_Account.ADDRESS, Register_Online_data.Valid_Account.EMAIL);

		log.info("TC_1_Step: Verify text man hinh dang ky phat hanh the ghi no");
		verifyEquals(registerOnline.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvTitleBar"), "Đăng ký phát hành thẻ ghi nợ");

		log.info("TC_1_Step: Click hang the, chon hang the la vang");
		registerOnline.clickToTextViewDate(driver, "Hạng thẻ", "1");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.CARD_RANK[1]);

		log.info("TC_1_Step: Click chon loai thanh toan phi");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutThanhToanPhi");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.FEE_PAYMENT[0]);

		log.info("TC_1_Step: Select yeu cau phat hanh the phu");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Yêu cầu phát hành thẻ phụ");

		log.info("TC_1_Step: Nhap ho ten chu the");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, nameCustomer, "com.VCB:id/layoutHoTenDayDu");

		log.info("TC_1_Step: Click giay to tuy than");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutLoaiXacNhan");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[1]);

		log.info("TC_1_Step: Input so ho chieu");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.NO_IDENTIFICATION, "com.VCB:id/layoutCMTND");

		log.info("TC_1_Step: Click ngay cap");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayCap");

		log.info("TC_1_Step: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_1_Step: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(2));

		log.info("TC_1_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, "OK");

		log.info("TC_1_Step: Input noi cap");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.LOCATION[1], "com.VCB:id/layoutNoiCap2");

		log.info("TC_1_Step: Click tiep tuc");
		registerOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_1_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "2"), "ĐĂNG KÝ THÀNH CÔNG");

		log.info("TC_1_Step: verify text dieu kien");
		verifyEquals(registerOnline.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvNote"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_1_Step: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Chia sẻ"));

		log.info("TC_1_Step: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Lưu ảnh"));

		log.info("TC_1_Step: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, "Thực hiện đăng ký khác");

	}

	@Test
	public void TC_02_DangKyPhatHanhTheGhiNo_ChonHangVangVCBCashBankPlusNopTienMatBoPhatHanhThePhu() {
		log.info("TC_02_Step: Nhap thong tin man hinh dang ky");
		registerOnline.DangKyPhatHanhTheGhiNo(Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[1], Register_Online_data.Valid_Account.NO_IDENTIFICATION, getBackWardYear(2), Register_Online_data.Valid_Account.LOCATION[1],  Register_Online_data.Valid_Account.ADDRESS,Register_Online_data.Valid_Account.EMAIL);

		log.info("TC_02_Step: Verify text man hinh dang ky phat hanh the ghi no");
		verifyEquals(registerOnline.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvTitleBar"), "Đăng ký phát hành thẻ ghi nợ");

		log.info("TC_02_Step: Click hang the, chon hang the la vang");
		registerOnline.clickToTextViewDate(driver, "Hạng thẻ", "1");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.CARD_RANK[1]);

		log.info("TC_02_Step: Click loại the ghi no");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Vietcombank CardPlus American Express");

		log.info("TC_02_Step: Click chon loai thanh toan phi");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutThanhToanPhi");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.FEE_PAYMENT[0]);

		log.info("TC_02_Step: Click tiep tuc");
		registerOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_02_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "2"), "ĐĂNG KÝ THÀNH CÔNG");

		log.info("TC_02_Step: verify text dieu kien");
		verifyEquals(registerOnline.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvNote"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_02_Step: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Chia sẻ"));

		log.info("TC_02_Step: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Lưu ảnh"));

		log.info("TC_02_Step: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, "Thực hiện đăng ký khác");
	}

	@Test
	public void TC_03_DangKyPhatHanhTheGhiNo_ChonHangVangVCBCashBankPlusNopTienMatBoPhatHanhThePhu() {
		log.info("TC_03_Step: Nhap thong tin man hinh dang ky");
		registerOnline.DangKyPhatHanhTheGhiNo(Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[1], Register_Online_data.Valid_Account.NO_IDENTIFICATION, getBackWardYear(2), Register_Online_data.Valid_Account.LOCATION[1], Register_Online_data.Valid_Account.ADDRESS, Register_Online_data.Valid_Account.EMAIL);

		log.info("TC_03_Step: Verify text man hinh dang ky phat hanh the ghi no");
		verifyEquals(registerOnline.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvTitleBar"), "Đăng ký phát hành thẻ ghi nợ");

		log.info("TC_03_Step: Click hang the, chon hang the la vang");
		registerOnline.clickToTextViewDate(driver, "Hạng thẻ", "1");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.CARD_RANK[1]);

		log.info("TC_03_Step: Click loại the ghi no");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Vietcombank CardPlus American Express");

		log.info("TC_03_Step: Click chon loai thanh toan phi");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutThanhToanPhi");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.FEE_PAYMENT[0]);

		log.info("TC_03_Step: Click tiep tuc");
		registerOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_03_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "2"), "ĐĂNG KÝ THÀNH CÔNG");

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
	public void TC_04_DangKyPhatHanhTheGhiNo_ChonHangVangVCBConnect24VISATrichNoTuDongPhatHanhThePhu() {
		log.info("TC_04_Step: Nhap thong tin man hinh dang ky");
		registerOnline.DangKyPhatHanhTheGhiNo(Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[1], Register_Online_data.Valid_Account.NO_IDENTIFICATION, getBackWardYear(2), Register_Online_data.Valid_Account.LOCATION[1], Register_Online_data.Valid_Account.ADDRESS, Register_Online_data.Valid_Account.EMAIL);

		log.info("TC_04_Step: Verify text man hinh dang ky phat hanh the ghi no");
		verifyEquals(registerOnline.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvTitleBar"), "Đăng ký phát hành thẻ ghi nợ");

		log.info("TC_04_Step: Click hang the, chon hang the la vang");
		registerOnline.clickToTextViewDate(driver, "Hạng thẻ", "1");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.CARD_RANK[1]);

		log.info("TC_04_Step: Click loại the ghi no");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Vietcombank Connect24 Visa");

		log.info("TC_04_Step: Click chon loai thanh toan phi");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutThanhToanPhi");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.FEE_PAYMENT[0]);

		log.info("TC_04_Step: Select yeu cau phat hanh the phu");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Yêu cầu phát hành thẻ phụ");

		log.info("TC_04_Step: Nhap ho ten chu the");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, nameCustomer, "com.VCB:id/layoutHoTenDayDu");

		log.info("TC_04_Step: Click giay to tuy than");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutLoaiXacNhan");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[1]);

		log.info("TC_04_Step: Input so ho chieu");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.NO_IDENTIFICATION, "com.VCB:id/layoutCMTND");

		log.info("TC_04_Step: Click ngay cap");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayCap");

		log.info("TC_04_Step: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_04_Step: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(2));

		log.info("TC_04_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, "OK");

		log.info("TC_04_Step: Input noi cap");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.LOCATION[1], "com.VCB:id/layoutNoiCap2");

		log.info("TC_04_Step: Click tiep tuc");
		registerOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_04_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "2"), "ĐĂNG KÝ THÀNH CÔNG");

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
	public void TC_05_DangKyPhatHanhTheGhiNo_ChonHangVangUnionPayNopTienMatPhatHanhThePhu() {
		log.info("TC_05_Step: Nhap thong tin man hinh dang ky");
		registerOnline.DangKyPhatHanhTheGhiNo(Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[1], Register_Online_data.Valid_Account.NO_IDENTIFICATION, getBackWardYear(2), Register_Online_data.Valid_Account.LOCATION[1],  Register_Online_data.Valid_Account.ADDRESS,Register_Online_data.Valid_Account.EMAIL);

		log.info("TC_05_Step: Verify text man hinh dang ky phat hanh the ghi no");
		verifyEquals(registerOnline.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvTitleBar"), "Đăng ký phát hành thẻ ghi nợ");

		log.info("TC_05_Step: Click hang the, chon hang the la vang");
		registerOnline.clickToTextViewDate(driver, "Hạng thẻ", "1");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.CARD_RANK[1]);

		log.info("TC_05_Step: Click loại the ghi no");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Vietcombank UnionPay");

		log.info("TC_05_Step: Click chon loai thanh toan phi");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutThanhToanPhi");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.FEE_PAYMENT[1]);

		log.info("TC_05_Step: Select yeu cau phat hanh the phu");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Yêu cầu phát hành thẻ phụ");

		log.info("TC_05_Step: Nhap ho ten chu the");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, nameCustomer, "com.VCB:id/layoutHoTenDayDu");

		log.info("TC_05_Step: Click giay to tuy than");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutLoaiXacNhan");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[1]);

		log.info("TC_05_Step: Input so ho chieu");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.NO_IDENTIFICATION, "com.VCB:id/layoutCMTND");

		log.info("TC_05_Step: Click ngay cap");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayCap");

		log.info("TC_05_Step: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_05_Step: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(2));

		log.info("TC_05_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, "OK");

		log.info("TC_05_Step: Input noi cap");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.LOCATION[1], "com.VCB:id/layoutNoiCap2");

		log.info("TC_05_Step: Click tiep tuc");
		registerOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_05_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "2"), "ĐĂNG KÝ THÀNH CÔNG");

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
	public void TC_06_DangKyPhatHanhTheGhiNo_ChonHangVangVCBConnect24TrichNoTuDongPhatHanhThePhu() {
		log.info("TC_06_Step: Nhap thong tin man hinh dang ky");
		registerOnline.DangKyPhatHanhTheGhiNo(Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[1], Register_Online_data.Valid_Account.NO_IDENTIFICATION, getBackWardYear(2), Register_Online_data.Valid_Account.LOCATION[1],  Register_Online_data.Valid_Account.ADDRESS,Register_Online_data.Valid_Account.EMAIL);

		log.info("TC_06_Step: Verify text man hinh dang ky phat hanh the ghi no");
		verifyEquals(registerOnline.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvTitleBar"), "Đăng ký phát hành thẻ ghi nợ");

		log.info("TC_06_Step: Click hang the, chon hang the la vang");
		registerOnline.clickToTextViewDate(driver, "Hạng thẻ", "1");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.CARD_RANK[1]);

		log.info("TC_06_Step: Click chon loai thanh toan phi");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutThanhToanPhi");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.FEE_PAYMENT[0]);

		log.info("TC_06_Step: Select yeu cau phat hanh the phu");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Yêu cầu phát hành thẻ phụ");

		log.info("TC_06_Step: Nhap ho ten chu the");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, nameCustomer, "com.VCB:id/layoutHoTenDayDu");

		log.info("TC_06_Step: Click giay to tuy than");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutLoaiXacNhan");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[1]);

		log.info("TC_06_Step: Input so ho chieu");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.NO_IDENTIFICATION, "com.VCB:id/layoutCMTND");

		log.info("TC_06_Step: Click ngay cap");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayCap");

		log.info("TC_06_Step: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_06_Step: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(2));

		log.info("TC_06_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, "OK");

		log.info("TC_06_Step: Input noi cap");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.LOCATION[1], "com.VCB:id/layoutNoiCap2");

		log.info("TC_06_Step: Click tiep tuc");
		registerOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_06_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "2"), "ĐĂNG KÝ THÀNH CÔNG");

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
	public void TC_07_DangKyPhatHanhTheGhiNo_ChonHangVangVCBCashBankTrichNoTuDongBoPhatHanhThePhu() {
		log.info("TC_07_Step: Nhap thong tin man hinh dang ky");
		registerOnline.DangKyPhatHanhTheGhiNo(Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[1], Register_Online_data.Valid_Account.NO_IDENTIFICATION, getBackWardYear(2), Register_Online_data.Valid_Account.LOCATION[1], Register_Online_data.Valid_Account.ADDRESS, Register_Online_data.Valid_Account.EMAIL);

		log.info("TC_07_Step: Verify text man hinh dang ky phat hanh the ghi no");
		verifyEquals(registerOnline.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvTitleBar"), "Đăng ký phát hành thẻ ghi nợ");

		log.info("TC_07_Step: Click hang the, chon hang the la vang");
		registerOnline.clickToTextViewDate(driver, "Hạng thẻ", "1");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.CARD_RANK[1]);

		log.info("TC_07_Step: Click loại the ghi no");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Vietcombank CardPlus American Express");

		log.info("TC_07_Step: Click chon loai thanh toan phi");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutThanhToanPhi");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.FEE_PAYMENT[0]);

		log.info("TC_07_Step: Click tiep tuc");
		registerOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_07_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "2"), "ĐĂNG KÝ THÀNH CÔNG");

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
	public void TC_08_DangKyPhatHanhTheGhiNo_ChonHangVangVCBCashBankTrichNoTuDongBoPhatHanhThePhu() {
		log.info("TC_08_Step: Nhap thong tin man hinh dang ky");
		registerOnline.DangKyPhatHanhTheGhiNo(Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[1], Register_Online_data.Valid_Account.NO_IDENTIFICATION, getBackWardYear(2), Register_Online_data.Valid_Account.LOCATION[1], Register_Online_data.Valid_Account.ADDRESS, Register_Online_data.Valid_Account.EMAIL);

		log.info("TC_08_Step: Verify text man hinh dang ky phat hanh the ghi no");
		verifyEquals(registerOnline.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvTitleBar"), "Đăng ký phát hành thẻ ghi nợ");

		log.info("TC_08_Step: Click hang the, chon hang the la vang");
		registerOnline.clickToTextViewDate(driver, "Hạng thẻ", "1");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.CARD_RANK[1]);

		log.info("TC_08_Step: Click loại the ghi no");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Vietcombank MasterCard");

		log.info("TC_08_Step: Click chon loai thanh toan phi");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutThanhToanPhi");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.FEE_PAYMENT[0]);

		log.info("TC_08_Step: Click tiep tuc");
		registerOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_08_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "2"), "ĐĂNG KÝ THÀNH CÔNG");

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
	public void TC_09_DangKyPhatHanhTheGhiNo_ChonHangVangVCBConnect24NopTienMatPhatHanhThePhu() {
		log.info("TC_09_Step: Nhap thong tin man hinh dang ky");
		registerOnline.DangKyPhatHanhTheGhiNo(Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[1], Register_Online_data.Valid_Account.NO_IDENTIFICATION, getBackWardYear(2), Register_Online_data.Valid_Account.LOCATION[1],  Register_Online_data.Valid_Account.ADDRESS,Register_Online_data.Valid_Account.EMAIL);

		log.info("TC_09_Step: Verify text man hinh dang ky phat hanh the ghi no");
		verifyEquals(registerOnline.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvTitleBar"), "Đăng ký phát hành thẻ ghi nợ");

		log.info("TC_09_Step: Click hang the, chon hang the la vang");
		registerOnline.clickToTextViewDate(driver, "Hạng thẻ", "1");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.CARD_RANK[1]);

		log.info("TC_09_Step: Click loại the ghi no");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Vietcombank Connect24 Visa");

		log.info("TC_09_Step: Click chon loai thanh toan phi");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutThanhToanPhi");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.FEE_PAYMENT[1]);

		log.info("TC_09_Step: Select yeu cau phat hanh the phu");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Yêu cầu phát hành thẻ phụ");

		log.info("TC_09_Step: Nhap ho ten chu the");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, nameCustomer, "com.VCB:id/layoutHoTenDayDu");

		log.info("TC_09_Step: Click giay to tuy than");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutLoaiXacNhan");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[1]);

		log.info("TC_09_Step: Input so ho chieu");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.NO_IDENTIFICATION, "com.VCB:id/layoutCMTND");

		log.info("TC_09_Step: Click ngay cap");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayCap");

		log.info("TC_09_Step: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_09_Step: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(2));

		log.info("TC_09_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, "OK");

		log.info("TC_09_Step: Input noi cap");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.LOCATION[1], "com.VCB:id/layoutNoiCap2");

		log.info("TC_09_Step: Click tiep tuc");
		registerOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_09_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "2"), "ĐĂNG KÝ THÀNH CÔNG");

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
	public void TC_10_DangKyPhatHanhTheGhiNo_ChonHangVangUnionPayNopTienMatBoPhatHanhThePhu() {
		log.info("TC_10_Step: Nhap thong tin man hinh dang ky");
		registerOnline.DangKyPhatHanhTheGhiNo(Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[1], Register_Online_data.Valid_Account.NO_IDENTIFICATION, getBackWardYear(2), Register_Online_data.Valid_Account.LOCATION[1],  Register_Online_data.Valid_Account.ADDRESS,Register_Online_data.Valid_Account.EMAIL);

		log.info("TC_10_Step: Verify text man hinh dang ky phat hanh the ghi no");
		verifyEquals(registerOnline.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvTitleBar"), "Đăng ký phát hành thẻ ghi nợ");

		log.info("TC_10_Step: Click hang the, chon hang the la vang");
		registerOnline.clickToTextViewDate(driver, "Hạng thẻ", "1");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.CARD_RANK[1]);

		log.info("TC_10_Step: Click loại the ghi no");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Vietcombank UnionPay");

		log.info("TC_10_Step: Click chon loai thanh toan phi");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutThanhToanPhi");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.FEE_PAYMENT[1]);

		log.info("TC_10_Step: Click tiep tuc");
		registerOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_10_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "2"), "ĐĂNG KÝ THÀNH CÔNG");

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
	public void TC_11_DangKyPhatHanhTheGhiNo_ChonHangDacBietConnect24NopTienMatPhatHanhThePhu() {
		log.info("TC_11_Step: Nhap thong tin man hinh dang ky");
		registerOnline.DangKyPhatHanhTheGhiNo(Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[1], Register_Online_data.Valid_Account.NO_IDENTIFICATION, getBackWardYear(2), Register_Online_data.Valid_Account.LOCATION[1], Register_Online_data.Valid_Account.ADDRESS, Register_Online_data.Valid_Account.EMAIL);

		log.info("TC_11_Step: Verify text man hinh dang ky phat hanh the ghi no");
		verifyEquals(registerOnline.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvTitleBar"), "Đăng ký phát hành thẻ ghi nợ");

		log.info("TC_11_Step: Click hang the, chon hang the la vang");
		registerOnline.clickToTextViewDate(driver, "Hạng thẻ", "1");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.CARD_RANK[2]);

		log.info("TC_11_Step: Click chon loai thanh toan phi");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutThanhToanPhi");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.FEE_PAYMENT[1]);

		log.info("TC_11_Step: Select yeu cau phat hanh the phu");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Yêu cầu phát hành thẻ phụ");

		log.info("TC_11_Step: Nhap ho ten chu the");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, nameCustomer, "com.VCB:id/layoutHoTenDayDu");

		log.info("TC_11_Step: Click giay to tuy than");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutLoaiXacNhan");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[1]);

		log.info("TC_11_Step: Input so ho chieu");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.NO_IDENTIFICATION, "com.VCB:id/layoutCMTND");

		log.info("TC_11_Step: Click ngay cap");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayCap");

		log.info("TC_11_Step: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_11_Step: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(2));

		log.info("TC_11_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, "OK");

		log.info("TC_11_Step: Input noi cap");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.LOCATION[1], "com.VCB:id/layoutNoiCap2");

		log.info("TC_11_Step: Click tiep tuc");
		registerOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_11_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "2"), "ĐĂNG KÝ THÀNH CÔNG");

		log.info("TC_11_Step: verify text dieu kien");
		verifyEquals(registerOnline.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvNote"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_11_Step: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Chia sẻ"));

		log.info("TC_11_Step: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Lưu ảnh"));

		log.info("TC_11_Step: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, "Thực hiện đăng ký khác");
	}

	@Test
	public void TC_12_DangKyPhatHanhTheGhiNo_ChonHangDacBietConnect24NopTienMatPhatHanhThePhu() {
		log.info("TC_12_Step: Nhap thong tin man hinh dang ky");
		registerOnline.DangKyPhatHanhTheGhiNo(Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[1], Register_Online_data.Valid_Account.NO_IDENTIFICATION, getBackWardYear(2), Register_Online_data.Valid_Account.LOCATION[1], Register_Online_data.Valid_Account.ADDRESS, Register_Online_data.Valid_Account.EMAIL);

		log.info("TC_12_Step: Verify text man hinh dang ky phat hanh the ghi no");
		verifyEquals(registerOnline.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvTitleBar"), "Đăng ký phát hành thẻ ghi nợ");

		log.info("TC_12_Step: Click hang the, chon hang the la dac biet");
		registerOnline.clickToTextViewDate(driver, "Hạng thẻ", "1");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.CARD_RANK[2]);

		log.info("TC_12_Step: Click loại the ghi no");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Vietcombank CardPlus American Express");

		log.info("TC_12_Step: Click chon loai thanh toan phi");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutThanhToanPhi");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.FEE_PAYMENT[1]);

		log.info("TC_12_Step: Select yeu cau phat hanh the phu");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Yêu cầu phát hành thẻ phụ");

		log.info("TC_12_Step: Nhap ho ten chu the");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, nameCustomer, "com.VCB:id/layoutHoTenDayDu");

		log.info("TC_12_Step: Click giay to tuy than");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutLoaiXacNhan");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[1]);

		log.info("TC_12_Step: Input so ho chieu");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.NO_IDENTIFICATION, "com.VCB:id/layoutCMTND");

		log.info("TC_12_Step: Click ngay cap");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayCap");

		log.info("TC_12_Step: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_12_Step: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(2));

		log.info("TC_12_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, "OK");

		log.info("TC_12_Step: Input noi cap");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.LOCATION[1], "com.VCB:id/layoutNoiCap2");

		log.info("TC_12_Step: Click tiep tuc");
		registerOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_12_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "2"), "ĐĂNG KÝ THÀNH CÔNG");

		log.info("TC_12_Step: verify text dieu kien");
		verifyEquals(registerOnline.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvNote"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_12_Step: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Chia sẻ"));

		log.info("TC_12_Step: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Lưu ảnh"));

		log.info("TC_12_Step: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, "Thực hiện đăng ký khác");
	}

	@Test
	public void TC_13_DangKyPhatHanhTheGhiNo_ChonHangDacBietVCBMasterCardTrichNoTuDongBoPhatHanhThePhu() {
		log.info("TC_13_Step: Nhap thong tin man hinh dang ky");
		registerOnline.DangKyPhatHanhTheGhiNo(Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[1], Register_Online_data.Valid_Account.NO_IDENTIFICATION, getBackWardYear(2), Register_Online_data.Valid_Account.LOCATION[1], Register_Online_data.Valid_Account.ADDRESS, Register_Online_data.Valid_Account.EMAIL);

		log.info("TC_13_Step: Verify text man hinh dang ky phat hanh the ghi no");
		verifyEquals(registerOnline.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvTitleBar"), "Đăng ký phát hành thẻ ghi nợ");

		log.info("TC_13_Step: Click hang the, chon hang the la đặc biệt");
		registerOnline.clickToTextViewDate(driver, "Hạng thẻ", "1");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.CARD_RANK[2]);

		log.info("TC_13_Step: Click loại the ghi no");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Vietcombank MasterCard");

		log.info("TC_13_Step: Click chon loai thanh toan phi");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutThanhToanPhi");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.FEE_PAYMENT[0]);

		log.info("TC_13_Step: Click tiep tuc");
		registerOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_13_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "2"), "ĐĂNG KÝ THÀNH CÔNG");

		log.info("TC_13_Step: verify text dieu kien");
		verifyEquals(registerOnline.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvNote"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_13_Step: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Chia sẻ"));

		log.info("TC_13_Step: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Lưu ảnh"));

		log.info("TC_13_Step: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, "Thực hiện đăng ký khác");
	}

	@Test
	public void TC_14_DangKyPhatHanhTheGhiNo_ChonHangDacBietConnect24VISATrichNoTuDongBoPhatHanhThePhu() {
		log.info("TC_14_Step: Nhap thong tin man hinh dang ky");
		registerOnline.DangKyPhatHanhTheGhiNo(Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[1], Register_Online_data.Valid_Account.NO_IDENTIFICATION, getBackWardYear(2), Register_Online_data.Valid_Account.LOCATION[1], Register_Online_data.Valid_Account.ADDRESS, Register_Online_data.Valid_Account.EMAIL);

		log.info("TC_14_Step: Verify text man hinh dang ky phat hanh the ghi no");
		verifyEquals(registerOnline.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvTitleBar"), "Đăng ký phát hành thẻ ghi nợ");

		log.info("TC_14_Step: Click hang the, chon hang the la đặc biệt");
		registerOnline.clickToTextViewDate(driver, "Hạng thẻ", "1");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.CARD_RANK[2]);

		log.info("TC_14_Step: Click loại the ghi no");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Vietcombank Connect24 Visa");

		log.info("TC_14_Step: Click chon loai thanh toan phi");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutThanhToanPhi");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.FEE_PAYMENT[0]);

		log.info("TC_14_Step: Click tiep tuc");
		registerOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_14_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "2"), "ĐĂNG KÝ THÀNH CÔNG");

		log.info("TC_14_Step: verify text dieu kien");
		verifyEquals(registerOnline.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvNote"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_14_Step: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Chia sẻ"));

		log.info("TC_14_Step: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Lưu ảnh"));

		log.info("TC_14_Step: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, "Thực hiện đăng ký khác");
	}

	@Test
	public void TC_15_DangKyPhatHanhTheGhiNo_ChonHangDacBietVCBUnionPayNopTienMatPhatHanhThePhu() {
		log.info("TC_15_Step: Nhap thong tin man hinh dang ky");
		registerOnline.DangKyPhatHanhTheGhiNo(Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[1], Register_Online_data.Valid_Account.NO_IDENTIFICATION, getBackWardYear(2), Register_Online_data.Valid_Account.LOCATION[1], Register_Online_data.Valid_Account.ADDRESS, Register_Online_data.Valid_Account.EMAIL);

		log.info("TC_15_Step: Verify text man hinh dang ky phat hanh the ghi no");
		verifyEquals(registerOnline.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvTitleBar"), "Đăng ký phát hành thẻ ghi nợ");

		log.info("TC_15_Step: Click hang the, chon hang the la dac biet");
		registerOnline.clickToTextViewDate(driver, "Hạng thẻ", "1");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.CARD_RANK[2]);

		log.info("TC_15_Step: Click loại the ghi no");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Vietcombank UnionPay");

		log.info("TC_15_Step: Click chon loai thanh toan phi");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutThanhToanPhi");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.FEE_PAYMENT[1]);

		log.info("TC_15_Step: Select yeu cau phat hanh the phu");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Yêu cầu phát hành thẻ phụ");

		log.info("TC_15_Step: Nhap ho ten chu the");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, nameCustomer, "com.VCB:id/layoutHoTenDayDu");

		log.info("TC_15_Step: Click giay to tuy than");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutLoaiXacNhan");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[1]);

		log.info("TC_15_Step: Input so ho chieu");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.NO_IDENTIFICATION, "com.VCB:id/layoutCMTND");

		log.info("TC_15_Step: Click ngay cap");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayCap");

		log.info("TC_15_Step: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_15_Step: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(2));

		log.info("TC_15_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, "OK");

		log.info("TC_15_Step: Input noi cap");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.LOCATION[1], "com.VCB:id/layoutNoiCap2");

		log.info("TC_15_Step: Click tiep tuc");
		registerOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_15_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "2"), "ĐĂNG KÝ THÀNH CÔNG");

		log.info("TC_15_Step: verify text dieu kien");
		verifyEquals(registerOnline.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvNote"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_15_Step: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Chia sẻ"));

		log.info("TC_15_Step: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Lưu ảnh"));

		log.info("TC_15_Step: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, "Thực hiện đăng ký khác");
	}

	@Test
	public void TC_16_DangKyPhatHanhTheGhiNo_ChonHangDacBietVCBConnect24NopTienMatPhatHanhThePhu() {
		log.info("TC_16_Step: Nhap thong tin man hinh dang ky");
		registerOnline.DangKyPhatHanhTheGhiNo(Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[1], Register_Online_data.Valid_Account.NO_IDENTIFICATION, getBackWardYear(2), Register_Online_data.Valid_Account.LOCATION[1], Register_Online_data.Valid_Account.ADDRESS, Register_Online_data.Valid_Account.EMAIL);

		log.info("TC_16_Step: Verify text man hinh dang ky phat hanh the ghi no");
		verifyEquals(registerOnline.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvTitleBar"), "Đăng ký phát hành thẻ ghi nợ");

		log.info("TC_16_Step: Click hang the, chon hang the la dac biet");
		registerOnline.clickToTextViewDate(driver, "Hạng thẻ", "1");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.CARD_RANK[2]);

		log.info("TC_16_Step: Click chon loai thanh toan phi");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutThanhToanPhi");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.FEE_PAYMENT[1]);

		log.info("TC_16_Step: Select yeu cau phat hanh the phu");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Yêu cầu phát hành thẻ phụ");

		log.info("TC_16_Step: Nhap ho ten chu the");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, nameCustomer, "com.VCB:id/layoutHoTenDayDu");

		log.info("TC_16_Step: Click giay to tuy than");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutLoaiXacNhan");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[1]);

		log.info("TC_16_Step: Input so ho chieu");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.NO_IDENTIFICATION, "com.VCB:id/layoutCMTND");

		log.info("TC_16_Step: Click ngay cap");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayCap");

		log.info("TC_16_Step: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_16_Step: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(2));

		log.info("TC_16_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, "OK");

		log.info("TC_16_Step: Input noi cap");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.LOCATION[1], "com.VCB:id/layoutNoiCap2");

		log.info("TC_16_Step: Click tiep tuc");
		registerOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_16_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "2"), "ĐĂNG KÝ THÀNH CÔNG");

		log.info("TC_16_Step: verify text dieu kien");
		verifyEquals(registerOnline.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvNote"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_16_Step: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Chia sẻ"));

		log.info("TC_16_Step: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Lưu ảnh"));

		log.info("TC_16_Step: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, "Thực hiện đăng ký khác");

	}

	@Test
	public void TC_17_DangKyPhatHanhTheGhiNo_ChonHangDacBietVCBCardPlusTrichNoTuDongBoPhatHanhThePhu() {
		log.info("TC_17_Step: Nhap thong tin man hinh dang ky");
		registerOnline.DangKyPhatHanhTheGhiNo(Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[1], Register_Online_data.Valid_Account.NO_IDENTIFICATION, getBackWardYear(2), Register_Online_data.Valid_Account.LOCATION[1], Register_Online_data.Valid_Account.ADDRESS, Register_Online_data.Valid_Account.EMAIL);

		log.info("TC_17_Step: Verify text man hinh dang ky phat hanh the ghi no");
		verifyEquals(registerOnline.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvTitleBar"), "Đăng ký phát hành thẻ ghi nợ");

		log.info("TC_17_Step: Click hang the, chon hang the la dac biet");
		registerOnline.clickToTextViewDate(driver, "Hạng thẻ", "1");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.CARD_RANK[2]);

		log.info("TC_17_Step: Click loại the ghi no");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Vietcombank CardPlus American Express");

		log.info("TC_17_Step: Click chon loai thanh toan phi");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutThanhToanPhi");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.FEE_PAYMENT[0]);

		log.info("TC_17_Step: Click tiep tuc");
		registerOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_17_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "2"), "ĐĂNG KÝ THÀNH CÔNG");

		log.info("TC_17_Step: verify text dieu kien");
		verifyEquals(registerOnline.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvNote"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_17_Step: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Chia sẻ"));

		log.info("TC_17_Step: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Lưu ảnh"));

		log.info("TC_17_Step: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, "Thực hiện đăng ký khác");

	}

	@Test
	public void TC_18_DangKyPhatHanhTheGhiNo_ChonHangDacBietVCBMasterCardTrichNoTuDongBoPhatHanhThePhu() {
		log.info("TC_18_Step: Nhap thong tin man hinh dang ky");
		registerOnline.DangKyPhatHanhTheGhiNo(Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[1], Register_Online_data.Valid_Account.NO_IDENTIFICATION, getBackWardYear(2), Register_Online_data.Valid_Account.LOCATION[1], Register_Online_data.Valid_Account.ADDRESS, Register_Online_data.Valid_Account.EMAIL);

		log.info("TC_18_Step: Verify text man hinh dang ky phat hanh the ghi no");
		verifyEquals(registerOnline.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvTitleBar"), "Đăng ký phát hành thẻ ghi nợ");

		log.info("TC_18_Step: Click hang the, chon hang the la dac biet");
		registerOnline.clickToTextViewDate(driver, "Hạng thẻ", "1");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.CARD_RANK[2]);

		log.info("TC_18_Step: Click loại the ghi no");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Vietcombank MasterCard");

		log.info("TC_18_Step: Click chon loai thanh toan phi");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutThanhToanPhi");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.FEE_PAYMENT[0]);

		log.info("TC_18_Step: Click tiep tuc");
		registerOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_18_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "2"), "ĐĂNG KÝ THÀNH CÔNG");

		log.info("TC_18_Step: verify text dieu kien");
		verifyEquals(registerOnline.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvNote"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_18_Step: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Chia sẻ"));

		log.info("TC_18_Step: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Lưu ảnh"));

		log.info("TC_18_Step: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, "Thực hiện đăng ký khác");

	}

	@Test
	public void TC_19_DangKyPhatHanhTheGhiNo_ChonHangDacBietVCBConnectVISATrichNoTuDongPhatHanhThePhu() {
		log.info("TC_19_Step: Nhap thong tin man hinh dang ky");
		registerOnline.DangKyPhatHanhTheGhiNo(Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[1], Register_Online_data.Valid_Account.NO_IDENTIFICATION, getBackWardYear(2), Register_Online_data.Valid_Account.LOCATION[1], Register_Online_data.Valid_Account.ADDRESS, Register_Online_data.Valid_Account.EMAIL);

		log.info("TC_19_Step: Verify text man hinh dang ky phat hanh the ghi no");
		verifyEquals(registerOnline.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvTitleBar"), "Đăng ký phát hành thẻ ghi nợ");

		log.info("TC_19_Step: Click hang the, chon hang the la dac biet");
		registerOnline.clickToTextViewDate(driver, "Hạng thẻ", "1");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.CARD_RANK[2]);

		log.info("TC_19_Step: Click loại the ghi no");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Vietcombank Connect24 Visa");

		log.info("TC_19_Step: Click chon loai thanh toan phi");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutThanhToanPhi");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.FEE_PAYMENT[0]);

		log.info("TC_19_Step: Select yeu cau phat hanh the phu");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Yêu cầu phát hành thẻ phụ");

		log.info("TC_19_Step: Nhap ho ten chu the");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, nameCustomer, "com.VCB:id/layoutHoTenDayDu");

		log.info("TC_19_Step: Click giay to tuy than");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutLoaiXacNhan");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[1]);

		log.info("TC_19_Step: Input so ho chieu");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.NO_IDENTIFICATION, "com.VCB:id/layoutCMTND");

		log.info("TC_19_Step: Click ngay cap");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayCap");

		log.info("TC_19_Step: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_19_Step: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(2));

		log.info("TC_19_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, "OK");

		log.info("TC_19_Step: Input noi cap");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.LOCATION[1], "com.VCB:id/layoutNoiCap2");

		log.info("TC_19_Step: Click tiep tuc");
		registerOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_19_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "2"), "ĐĂNG KÝ THÀNH CÔNG");

		log.info("TC_19_Step: verify text dieu kien");
		verifyEquals(registerOnline.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvNote"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_19_Step: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Chia sẻ"));

		log.info("TC_19_Step: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Lưu ảnh"));

		log.info("TC_19_Step: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, "Thực hiện đăng ký khác");

	}

	@Test
	public void TC_20_DangKyPhatHanhTheGhiNo_ChonHangDacBietUnionPAYNopTienMatPhatHanhThePhu() {
		log.info("TC_20_Step: Nhap thong tin man hinh dang ky");
		registerOnline.DangKyPhatHanhTheGhiNo(Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[1], Register_Online_data.Valid_Account.NO_IDENTIFICATION, getBackWardYear(2), Register_Online_data.Valid_Account.LOCATION[1], Register_Online_data.Valid_Account.ADDRESS, Register_Online_data.Valid_Account.EMAIL);

		log.info("TC_20_Step: Verify text man hinh dang ky phat hanh the ghi no");
		verifyEquals(registerOnline.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvTitleBar"), "Đăng ký phát hành thẻ ghi nợ");

		log.info("TC_20_Step: Click hang the, chon hang the la dac biet");
		registerOnline.clickToTextViewDate(driver, "Hạng thẻ", "1");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.CARD_RANK[2]);

		log.info("TC_20_Step: Click loại the ghi no");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Vietcombank UnionPay");

		log.info("TC_20_Step: Click chon loai thanh toan phi");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutThanhToanPhi");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.FEE_PAYMENT[1]);

		log.info("TC_20_Step: Select yeu cau phat hanh the phu");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Yêu cầu phát hành thẻ phụ");

		log.info("TC_20_Step: Nhap ho ten chu the");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, nameCustomer, "com.VCB:id/layoutHoTenDayDu");

		log.info("TC_20_Step: Click giay to tuy than");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutLoaiXacNhan");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[1]);

		log.info("TC_20_Step: Input so ho chieu");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.NO_IDENTIFICATION, "com.VCB:id/layoutCMTND");

		log.info("TC_20_Step: Click ngay cap");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayCap");

		log.info("TC_20_Step: Click nam");
		registerOnline.clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		log.info("TC_20_Step: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(2));

		log.info("TC_20_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, "OK");

		log.info("TC_20_Step: Input noi cap");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.LOCATION[1], "com.VCB:id/layoutNoiCap2");

		log.info("TC_20_Step: Click tiep tuc");
		registerOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_20_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "2"), "ĐĂNG KÝ THÀNH CÔNG");

		log.info("TC_20_Step: verify text dieu kien");
		verifyEquals(registerOnline.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvNote"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_20_Step: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Chia sẻ"));

		log.info("TC_20_Step: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Lưu ảnh"));

		log.info("TC_20_Step: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, "Thực hiện đăng ký khác");

	}

}
