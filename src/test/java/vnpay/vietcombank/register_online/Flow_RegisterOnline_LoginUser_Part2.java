package vnpay.vietcombank.register_online;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.Constants;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import model.SourceAccountModel;
import pageObjects.LogInPageObject;
import pageObjects.RegisterOnlinePageObject;
import vietcombank_test_data.Register_Online_data;
import vietcombank_test_data.Register_Online_data.Register_Text;

public class Flow_RegisterOnline_LoginUser_Part2 extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private RegisterOnlinePageObject registerOnline;
	String phoneNumber = "";
	String nameCustomer = "";
	
	SourceAccountModel sourceAccount = new SourceAccountModel();

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

		log.info("TC_01_Step: Click chuc nang dang ky truc tuyen");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Text.REGISTER_ONLINE_TEXT);
	}

	@Test
	public void TC_01_DangKyDichVuNganHangDienTu_ManHinhDangKyDVNHDT() {

		log.info("TC_01_Step: Click dang ky dich vu ngan hang - the ghi no");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Text.REGISTER_IB_SERVICE_OR_PUBLISH_DEBIT_CARD_TEXT);

		log.info("TC_01_Step: Click dang ky dich vu ngan hang dien tu");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Text.REGISTER_IB_SERVICE_TEXT);

		log.info("TC_01_Step: verify ten khach hang, defaul fill ten khach hang");
		verifyEquals(registerOnline.getTextEditViewByLinearLayoutID(driver, "com.VCB:id/layoutHoTenDayDu"), nameCustomer);

		log.info("TC_01_Step: Chon ngay sinh");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgaySinh");
		registerOnline.clickToDynamicButton(driver, Register_Text.BUTTON_OK_TEXT);

		log.info("TC_01_Step: Click giay to tuy than");
		registerOnline.clickToDynamicDropDown(driver, Register_Text.IDENTIFICATION_TEXT);
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Register_Onl_Data.TYPE_IDENTIFICATION[1]);

		log.info("TC_01_Step: Input so ho chieu");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Register_Onl_Data.NO_IDENTIFICATION, "com.VCB:id/layoutCMTND");

		log.info("TC_01_Step: Click ngay cap");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayCap");

		log.info("TC_01_Step: Click nam");
		registerOnline.clickToTextID(driver, "android:id/date_picker_header_year");

		log.info("TC_01_Step: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(1));

		log.info("TC_01_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, Register_Text.BUTTON_OK_TEXT);

		log.info("TC_01_Step: Input noi cap");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Register_Onl_Data.LOCATION[1], "com.VCB:id/layoutNoiCap2");

		log.info("TC_01_Step: verify so account");
		verifyEquals(registerOnline.getTextEditViewByLinearLayoutID(driver, "com.VCB:id/layoutSoDienThoai"), phoneNumber);

		log.info("TC_01_Step: Nhap email");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Register_Onl_Data.EMAIL, "com.VCB:id/layoutDiaChiEmail");

		log.info("TC_01_Step: Nhap dia chi hien tai");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Register_Onl_Data.ADDRESS, "com.VCB:id/layoutDiaChiHienTai");

		log.info("TC_01_Step: Click button xac nhan");
		registerOnline.clickToDynamicButton(driver, Register_Text.BUTTON_CONFIRM_TEXT);
	}

	@Test
	public void TC_02_DangKyDichVuNganHangDienTu_ManHinhDangKyDichVuDienTu() {
		
		log.info("TC_02_Step: Verify text man hinh dang ky dich vu ngan hang dien tu");
		verifyEquals(registerOnline.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvTitleBar"), Register_Text.REGISTER_IB_SERVICE_TEXT);

		log.info("TC_02_Step: Select option VCB-iB@nKing");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Text.VCB_I_BANKING_TEXT);

		log.info("TC_02_Step: Select option VCB-SMS B@nking");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Text.VCB_SMS_BANKING_TEXT);

		log.info("TC_02_Step: Nhap so dien thoai");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, phoneNumber, "com.VCB:id/inputSoDienThoai");
		registerOnline.clickToDynamicImageViewByID(driver, "com.VCB:id/ivAdd");

		log.info("TC_02_Step: Select option VCB-SMS B@nking");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Text.VCB_MOBILE_BANKING_TEXT);

		log.info("TC_02_Step: Chon so dien thoai dang ky");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutSDTMB");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, phoneNumber);

		log.info("TC_02_Step: Chon tai khoan dang ky");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/SoTk2");
		sourceAccount = registerOnline.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);
		
		log.info("TC_02_Step: Click button tiep tuc");
		registerOnline.clickToDynamicButton(driver, Register_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_02_Step: Click radio toi dong y");
		registerOnline.clickToDynamicRadioIndex(driver, Register_Text.AGREE_RADIO_BUTTON_TEXT, "3");

		log.info("TC_02_Step: Click dong y");
		registerOnline.scrollIDownOneTime(driver);
		registerOnline.clickToDynamicDateInDateTimePicker(driver, Register_Text.BUTTON_AGREE_TEXT);

		log.info("TC_02_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "2"), Register_Text.REGISTER_SUCCESS_TEXT);

		log.info("TC_02_Step: verify text dieu kien");
		verifyEquals(registerOnline.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvNote"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_02_Step: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, Register_Text.SHARE_ICON_TEXT));

		log.info("TC_02_Step: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, Register_Text.SAVE_IMG_ICON_TEXT));

		log.info("TC_02_Step: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, Register_Text.BUTTON_NEW_TRANSACTION_TEXT);
	}

	@Test
	public void TC_03_DangKyPhatHanhTheGhiNo_ManHinhThongTinKhachHang() {
		
		log.info("TC_03_Step: Click phat hanh the ghi no");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Text.REGISTER_PUBLISH_DEBIT_CARD_TEXT);

		log.info("TC_03_Step: verify ten khach hang, defaul fill ten khach hang");
		verifyEquals(registerOnline.getTextEditViewByLinearLayoutID(driver, "com.VCB:id/layoutHoTenDayDu"), nameCustomer);

		log.info("TC_03_Step: Chon ngay sinh");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgaySinh");
		registerOnline.clickToDynamicButton(driver, Register_Text.BUTTON_OK_TEXT);

		log.info("TC_03_Step: Click giay to tuy than");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutLoaiXacNhan");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Register_Onl_Data.TYPE_IDENTIFICATION[1]);

		log.info("TC_03_Step: Input so ho chieu");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Register_Onl_Data.NO_IDENTIFICATION, "com.VCB:id/layoutCMTND");

		log.info("TC_03_Step: Click ngay cap");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayCap");

		log.info("TC_03_Step: Click nam");
		registerOnline.clickToTextID(driver, "android:id/date_picker_header_year");

		log.info("TC_03_Step: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(1));

		log.info("TC_03_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, Register_Text.BUTTON_OK_TEXT);

		log.info("TC_03_Step: Input noi cap");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Register_Onl_Data.LOCATION[1], "com.VCB:id/layoutNoiCap2");

		log.info("TC_03_Step: Nhap dia chi hien tai");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Register_Onl_Data.ADDRESS, "com.VCB:id/layoutDiaChiHienTai");

		log.info("TC_03_Step: verify so account");
		verifyEquals(registerOnline.getTextEditViewByLinearLayoutID(driver, "com.VCB:id/layoutSoDienThoai"), phoneNumber);

		log.info("TC_03_Step: Nhap email");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Register_Onl_Data.EMAIL, "com.VCB:id/layoutDiaChiEmail");

		log.info("TC_03_Step: Click button xac nhan");
		registerOnline.clickToDynamicButton(driver, Register_Text.BUTTON_CONFIRM_TEXT);
	}

	@Test
	public void TC_04_DangKyPhatHanhTheGhiNo_ChonHangChuanTheGhiNoVCBconnectNopTienMat() {
		
		log.info("TC_04_Step: Verify text man hinh dang ky phat hanh the ghi no");
		verifyEquals(registerOnline.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvTitleBar"), Register_Text.REGISTER_PUBLISH_DEBIT_CARD_TEXT);

		log.info("TC_04_Step: Click hang the, chon hang the la vang");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutHangThe");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Register_Onl_Data.CARD_RANK[0]);

		log.info("TC_04_Step: Click chon loai thanh toan phi");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutThanhToanPhi");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Register_Onl_Data.FEE_PAYMENT[1]);

		log.info("TC_04_Step: Select yeu cau phat hanh the phu");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Text.PUBLISH_SECOND_CARD_REQUEST_TEXT);

		log.info("TC_04_Step: Nhap ho ten chu the");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, nameCustomer, "com.VCB:id/layoutHoTenDayDu");

		log.info("TC_04_Step: Click giay to tuy than");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutLoaiXacNhan");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Register_Onl_Data.TYPE_IDENTIFICATION[1]);

		log.info("TC_04_Step: Input so ho chieu");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Register_Onl_Data.NO_IDENTIFICATION, "com.VCB:id/layoutCMTND");

		log.info("TC_04_Step: Click ngay cap");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayCap");

		log.info("TC_04_Step: Click nam");
		registerOnline.clickToTextID(driver, "android:id/date_picker_header_year");

		log.info("TC_04_Step: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(5));

		log.info("TC_04_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, Register_Text.BUTTON_OK_TEXT);

		log.info("TC_04_Step: Input noi cap");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Register_Onl_Data.LOCATION[1], "com.VCB:id/layoutNoiCap2");

		log.info("TC_04_Step: Click tiep tuc");
		registerOnline.clickToDynamicButton(driver, Register_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_04_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "2"), Register_Text.REGISTER_SUCCESS_TEXT);

		log.info("TC_04_Step: verify text dieu kien");
		verifyEquals(registerOnline.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvNote"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_04_Step: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, Register_Text.SHARE_ICON_TEXT));

		log.info("TC_04_Step: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, Register_Text.SAVE_IMG_ICON_TEXT));

		log.info("TC_04_Step: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, Register_Text.BUTTON_NEW_TRANSACTION_TEXT);

	}

	@Test
	public void TC_05_DangKyPhatHanhTheGhiNo_ChonHangChuanTheVCBCashBankPlusAmericalTrichNoTuDongTuTK() {
		
		log.info("TC_05_Step: Nhap thong tin man hinh dang ky");
		registerOnline.DangKyPhatHanhTheGhiNo(Register_Online_data.Register_Onl_Data.TYPE_IDENTIFICATION[1], Register_Online_data.Register_Onl_Data.NO_IDENTIFICATION, getBackWardYear(1), Register_Online_data.Register_Onl_Data.LOCATION[1], Register_Online_data.Register_Onl_Data.ADDRESS, Register_Online_data.Register_Onl_Data.EMAIL);

		log.info("TC_05_Step: Verify text man hinh dang ky phat hanh the ghi no");
		verifyEquals(registerOnline.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvTitleBar"), Register_Text.REGISTER_PUBLISH_DEBIT_CARD_TEXT);

		log.info("TC_05_Step: Click hang the, chon hang the la vang");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutHangThe");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Register_Onl_Data.CARD_RANK[0]);

		log.info("TC_05_Step: Click loại the ghi no");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Text.VCB_CARDPLUS_TEXT);

		log.info("TC_05_Step: Click chon loai thanh toan phi");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutThanhToanPhi");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Register_Onl_Data.FEE_PAYMENT[0]);

		log.info("TC_05_Step: Click tiep tuc");
		registerOnline.clickToDynamicButton(driver, Register_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_05_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "2"), Register_Text.REGISTER_SUCCESS_TEXT);

		log.info("TC_05_Step: verify text dieu kien");
		verifyEquals(registerOnline.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvNote"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_05_Step: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, Register_Text.SHARE_ICON_TEXT));

		log.info("TC_05_Step: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, Register_Text.SAVE_IMG_ICON_TEXT));

		log.info("TC_05_Step: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, Register_Text.BUTTON_NEW_TRANSACTION_TEXT);
	}

	@Test
	public void TC_06_DangKyPhatHanhTheGhiNo_ChonHangChuanVCBMasterCardNopTienMat() {
		
		log.info("TC_06_Step: Nhap thong tin man hinh dang ky");
		registerOnline.DangKyPhatHanhTheGhiNo(Register_Online_data.Register_Onl_Data.TYPE_IDENTIFICATION[1], Register_Online_data.Register_Onl_Data.NO_IDENTIFICATION, getBackWardYear(1), Register_Online_data.Register_Onl_Data.LOCATION[1], Register_Online_data.Register_Onl_Data.ADDRESS, Register_Online_data.Register_Onl_Data.EMAIL);

		log.info("TC_06_Step: Verify text man hinh dang ky phat hanh the ghi no");
		verifyEquals(registerOnline.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvTitleBar"), Register_Text.REGISTER_PUBLISH_DEBIT_CARD_TEXT);

		log.info("TC_06_Step: Click hang the, chon hang the la vang");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutHangThe");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Register_Onl_Data.CARD_RANK[0]);

		log.info("TC_06_Step: Click loại the ghi no");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Text.VCB_MASTER_CARD_TEXT);

		log.info("TC_06_Step: Click chon loai thanh toan phi");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutThanhToanPhi");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Register_Onl_Data.FEE_PAYMENT[1]);

		log.info("TC_06_Step: Select yeu cau phat hanh the phu");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Text.PUBLISH_SECOND_CARD_REQUEST_TEXT);

		log.info("TC_06_Step: Nhap ho ten chu the");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, nameCustomer, "com.VCB:id/layoutHoTenDayDu");

		log.info("TC_06_Step: Click giay to tuy than");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutLoaiXacNhan");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Register_Onl_Data.TYPE_IDENTIFICATION[1]);

		log.info("TC_06_Step: Input so ho chieu");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Register_Onl_Data.NO_IDENTIFICATION, "com.VCB:id/layoutCMTND");

		log.info("TC_06_Step: Click ngay cap");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayCap");

		log.info("TC_06_Step: Click nam");
		registerOnline.clickToTextID(driver, "android:id/date_picker_header_year");

		log.info("TC_06_Step: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(5));

		log.info("TC_06_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, Register_Text.BUTTON_OK_TEXT);

		log.info("TC_06_Step: Input noi cap");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Register_Onl_Data.LOCATION[1], "com.VCB:id/layoutNoiCap2");

		log.info("TC_06_Step: Click tiep tuc");
		registerOnline.clickToDynamicButton(driver, Register_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_06_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "2"), Register_Text.REGISTER_SUCCESS_TEXT);

		log.info("TC_06_Step: verify text dieu kien");
		verifyEquals(registerOnline.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvNote"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_06_Step: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, Register_Text.SHARE_ICON_TEXT));

		log.info("TC_06_Step: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, Register_Text.SAVE_IMG_ICON_TEXT));

		log.info("TC_06_Step: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, Register_Text.BUTTON_NEW_TRANSACTION_TEXT);
	}

	@Test
	public void TC_07_DangKyPhatHanhTheGhiNo_ChonHangChuanVCBConnect24VISATrichNoTuDongTuTK() {
		
		log.info("TC_07_Step: Nhap thong tin man hinh dang ky");
		registerOnline.DangKyPhatHanhTheGhiNo(Register_Online_data.Register_Onl_Data.TYPE_IDENTIFICATION[1], Register_Online_data.Register_Onl_Data.NO_IDENTIFICATION, getBackWardYear(1), Register_Online_data.Register_Onl_Data.LOCATION[1], Register_Online_data.Register_Onl_Data.ADDRESS, Register_Online_data.Register_Onl_Data.EMAIL);

		log.info("TC_07_Step: Verify text man hinh dang ky phat hanh the ghi no");
		verifyEquals(registerOnline.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvTitleBar"), Register_Text.REGISTER_PUBLISH_DEBIT_CARD_TEXT);

		log.info("TC_07_Step: Click hang the, chon hang the la vang");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutHangThe");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Register_Onl_Data.CARD_RANK[0]);

		log.info("TC_07_Step: Click loại the ghi no");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Text.VCB_CONNECT_24H_VISA_TEXT);

		log.info("TC_07_Step: Click chon loai thanh toan phi");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutThanhToanPhi");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Register_Onl_Data.FEE_PAYMENT[0]);

		log.info("TC_07_Step: Select yeu cau phat hanh the phu");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Text.PUBLISH_SECOND_CARD_REQUEST_TEXT);

		log.info("TC_07_Step: Nhap ho ten chu the");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, nameCustomer, "com.VCB:id/layoutHoTenDayDu");

		log.info("TC_07_Step: Click giay to tuy than");
		registerOnline.clickToDynamicDropDown(driver, Register_Text.IDENTIFICATION_TEXT);
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Register_Onl_Data.TYPE_IDENTIFICATION[1]);

		log.info("TC_07_Step: Input so ho chieu");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Register_Onl_Data.NO_IDENTIFICATION, "com.VCB:id/layoutCMTND");

		log.info("TC_07_Step: Click ngay cap");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayCap");

		log.info("TC_07_Step: Click nam");
		registerOnline.clickToTextID(driver, "android:id/date_picker_header_year");

		log.info("TC_07_Step: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(5));

		log.info("TC_07_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, Register_Text.BUTTON_OK_TEXT);

		log.info("TC_07_Step: Input noi cap");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Register_Onl_Data.LOCATION[1], "com.VCB:id/layoutNoiCap2");

		log.info("TC_07_Step: Click tiep tuc");
		registerOnline.clickToDynamicButton(driver, Register_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_07_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "2"), Register_Text.REGISTER_SUCCESS_TEXT);

		log.info("TC_07_Step: verify text dieu kien");
		verifyEquals(registerOnline.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvNote"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_07_Step: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, Register_Text.SHARE_ICON_TEXT));

		log.info("TC_07_Step: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, Register_Text.SAVE_IMG_ICON_TEXT));

		log.info("TC_07_Step: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, Register_Text.BUTTON_NEW_TRANSACTION_TEXT);
	}

	@Test
	public void TC_08_DangKyPhatHanhTheGhiNo_ChonHangChuanUnionPAYNopTienMat() {
		
		log.info("TC_08_Step: Nhap thong tin man hinh dang ky");
		registerOnline.DangKyPhatHanhTheGhiNo(Register_Online_data.Register_Onl_Data.TYPE_IDENTIFICATION[1], Register_Online_data.Register_Onl_Data.NO_IDENTIFICATION, getBackWardYear(1), Register_Online_data.Register_Onl_Data.LOCATION[1], Register_Online_data.Register_Onl_Data.ADDRESS, Register_Online_data.Register_Onl_Data.EMAIL);

		log.info("TC_08_Step: Verify text man hinh dang ky phat hanh the ghi no");
		verifyEquals(registerOnline.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvTitleBar"), Register_Text.REGISTER_PUBLISH_DEBIT_CARD_TEXT);

		log.info("TC_08_Step: Click hang the, chon hang the la vang");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutHangThe");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Register_Onl_Data.CARD_RANK[0]);

		log.info("TC_08_Step: Click loại the ghi no");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Text.VCB_CONNECT_24H_VISA_TEXT);

		log.info("TC_08_Step: Click chon loai thanh toan phi");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutThanhToanPhi");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Register_Onl_Data.FEE_PAYMENT[0]);

		log.info("TC_08_Step: Select yeu cau phat hanh the phu");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Text.PUBLISH_SECOND_CARD_REQUEST_TEXT);

		log.info("TC_08_Step: Nhap ho ten chu the");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, nameCustomer, "com.VCB:id/layoutHoTenDayDu");

		log.info("TC_08_Step: Click giay to tuy than");
		registerOnline.clickToDynamicDropDown(driver, Register_Text.IDENTIFICATION_TEXT);
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Register_Onl_Data.TYPE_IDENTIFICATION[1]);

		log.info("TC_08_Step: Input so ho chieu");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Register_Onl_Data.NO_IDENTIFICATION, "com.VCB:id/layoutCMTND");

		log.info("TC_08_Step: Click ngay cap");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayCap");

		log.info("TC_08_Step: Click nam");
		registerOnline.clickToTextID(driver, "android:id/date_picker_header_year");

		log.info("TC_08_Step: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(5));

		log.info("TC_08_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, Register_Text.BUTTON_OK_TEXT);

		log.info("TC_08_Step: Input noi cap");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Register_Onl_Data.LOCATION[1], "com.VCB:id/layoutNoiCap2");

		log.info("TC_08_Step: Click tiep tuc");
		registerOnline.clickToDynamicButton(driver, Register_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_08_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "2"), Register_Text.REGISTER_SUCCESS_TEXT);

		log.info("TC_08_Step: verify text dieu kien");
		verifyEquals(registerOnline.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvNote"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_08_Step: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, Register_Text.SHARE_ICON_TEXT));

		log.info("TC_08_Step: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, Register_Text.SAVE_IMG_ICON_TEXT));

		log.info("TC_08_Step: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, Register_Text.BUTTON_NEW_TRANSACTION_TEXT);

	}

	@Test
	public void TC_09_DangKyPhatHanhTheGhiNo_ChonHangChuanVCBConnect24NopTienMatPhatHanhThePhu() {
		
		log.info("TC_09_Step: Nhap thong tin man hinh dang ky");
		registerOnline.DangKyPhatHanhTheGhiNo(Register_Online_data.Register_Onl_Data.TYPE_IDENTIFICATION[1], Register_Online_data.Register_Onl_Data.NO_IDENTIFICATION, getBackWardYear(1), Register_Online_data.Register_Onl_Data.LOCATION[1], Register_Online_data.Register_Onl_Data.ADDRESS, Register_Online_data.Register_Onl_Data.EMAIL);

		log.info("TC_09_Step: Verify text man hinh dang ky phat hanh the ghi no");
		verifyEquals(registerOnline.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvTitleBar"), Register_Text.REGISTER_PUBLISH_DEBIT_CARD_TEXT);

		log.info("TC_09_Step: Click hang the, chon hang the la vang");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutHangThe");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Register_Onl_Data.CARD_RANK[0]);

		log.info("TC_09_Step: Click chon loai thanh toan phi");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutThanhToanPhi");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Register_Onl_Data.FEE_PAYMENT[1]);

		log.info("TC_09_Step: Select yeu cau phat hanh the phu");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Text.PUBLISH_SECOND_CARD_REQUEST_TEXT);

		log.info("TC_09_Step: Nhap ho ten chu the");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, nameCustomer, "com.VCB:id/layoutHoTenDayDu");

		log.info("TC_09_Step: Click giay to tuy than");
		registerOnline.clickToDynamicDropDown(driver, Register_Text.IDENTIFICATION_TEXT);
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Register_Onl_Data.TYPE_IDENTIFICATION[1]);

		log.info("TC_09_Step: Input so ho chieu");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Register_Onl_Data.NO_IDENTIFICATION, "com.VCB:id/layoutCMTND");

		log.info("TC_09_Step: Click ngay cap");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayCap");

		log.info("TC_09_Step: Click nam");
		registerOnline.clickToTextID(driver, "android:id/date_picker_header_year");

		log.info("TC_09_Step: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(5));

		log.info("TC_09_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, Register_Text.BUTTON_OK_TEXT);

		log.info("TC_09_Step: Input noi cap");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Register_Onl_Data.LOCATION[1], "com.VCB:id/layoutNoiCap2");

		log.info("TC_09_Step: Click tiep tuc");
		registerOnline.clickToDynamicButton(driver, Register_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_09_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "2"), Register_Text.REGISTER_SUCCESS_TEXT);

		log.info("TC_09_Step: verify text dieu kien");
		verifyEquals(registerOnline.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvNote"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_09_Step: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, Register_Text.SHARE_ICON_TEXT));

		log.info("TC_09_Step: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, Register_Text.SAVE_IMG_ICON_TEXT));

		log.info("TC_09_Step: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, Register_Text.BUTTON_NEW_TRANSACTION_TEXT);
	}

	@Test
	public void TC_10_DangKyPhatHanhTheGhiNo_ChonHangChuanVCBCashbankPlusAmericalNopTienMatKhongPhatThePhu() {
		
		log.info("TC_10_Step: Nhap thong tin man hinh dang ky");
		registerOnline.DangKyPhatHanhTheGhiNo(Register_Online_data.Register_Onl_Data.TYPE_IDENTIFICATION[1], Register_Online_data.Register_Onl_Data.NO_IDENTIFICATION, getBackWardYear(1), Register_Online_data.Register_Onl_Data.LOCATION[1], Register_Online_data.Register_Onl_Data.ADDRESS, Register_Online_data.Register_Onl_Data.EMAIL);

		log.info("TC_10_Step: Verify text man hinh dang ky phat hanh the ghi no");
		verifyEquals(registerOnline.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvTitleBar"), Register_Text.REGISTER_PUBLISH_DEBIT_CARD_TEXT);

		log.info("TC_10_Step: Click hang the, chon hang the la vang");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutHangThe");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Register_Onl_Data.CARD_RANK[0]);

		log.info("TC_10_Step: Click loại the ghi no");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Text.VCB_CARDPLUS_TEXT);

		log.info("TC_10_Step: Click chon loai thanh toan phi");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutThanhToanPhi");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Register_Onl_Data.FEE_PAYMENT[1]);

		log.info("TC_10_Step: Click tiep tuc");
		registerOnline.clickToDynamicButton(driver, Register_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_10_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "2"), Register_Text.REGISTER_SUCCESS_TEXT);

		log.info("TC_10_Step: verify text dieu kien");
		verifyEquals(registerOnline.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvNote"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_10_Step: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, Register_Text.SHARE_ICON_TEXT));

		log.info("TC_10_Step: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, Register_Text.SAVE_IMG_ICON_TEXT));

		log.info("TC_10_Step: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, Register_Text.BUTTON_NEW_TRANSACTION_TEXT);

	}

	@Test
	public void TC_11_DangKyPhatHanhTheGhiNo_ChonHangChuanVCBMasterCardTrichNoTuDongPhatHanhThePhu() {
		
		log.info("TC_11_Step: Nhap thong tin man hinh dang ky");
		registerOnline.DangKyPhatHanhTheGhiNo(Register_Online_data.Register_Onl_Data.TYPE_IDENTIFICATION[1], Register_Online_data.Register_Onl_Data.NO_IDENTIFICATION, getBackWardYear(1), Register_Online_data.Register_Onl_Data.LOCATION[1], Register_Online_data.Register_Onl_Data.ADDRESS, Register_Online_data.Register_Onl_Data.EMAIL);

		log.info("TC_11_Step: Verify text man hinh dang ky phat hanh the ghi no");
		verifyEquals(registerOnline.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvTitleBar"), Register_Text.REGISTER_PUBLISH_DEBIT_CARD_TEXT);

		log.info("TC_11_Step: Click hang the, chon hang the la vang");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutHangThe");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Register_Onl_Data.CARD_RANK[0]);

		log.info("TC_11_Step: Click loại the ghi no");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Text.VCB_MASTER_CARD_TEXT);

		log.info("TC_11_Step: Click chon loai thanh toan phi");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutThanhToanPhi");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Register_Onl_Data.FEE_PAYMENT[0]);

		log.info("TC_11_Step: Select yeu cau phat hanh the phu");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Text.PUBLISH_SECOND_CARD_REQUEST_TEXT);

		log.info("TC_11_Step: Nhap ho ten chu the");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, nameCustomer, "com.VCB:id/layoutHoTenDayDu");

		log.info("TC_11_Step: Click giay to tuy than");
		registerOnline.clickToDynamicDropDown(driver, Register_Text.IDENTIFICATION_TEXT);
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Register_Onl_Data.TYPE_IDENTIFICATION[1]);

		log.info("TC_11_Step: Input so ho chieu");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Register_Onl_Data.NO_IDENTIFICATION, "com.VCB:id/layoutCMTND");

		log.info("TC_11_Step: Click ngay cap");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayCap");

		log.info("TC_11_Step: Click nam");
		registerOnline.clickToTextID(driver, "android:id/date_picker_header_year");

		log.info("TC_11_Step: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(5));

		log.info("TC_11_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, Register_Text.BUTTON_OK_TEXT);

		log.info("TC_11_Step: Input noi cap");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Register_Onl_Data.LOCATION[1], "com.VCB:id/layoutNoiCap2");

		log.info("TC_11_Step: Click tiep tuc");
		registerOnline.clickToDynamicButton(driver, Register_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_11_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "2"), Register_Text.REGISTER_SUCCESS_TEXT);

		log.info("TC_11_Step: verify text dieu kien");
		verifyEquals(registerOnline.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvNote"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_11_Step: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, Register_Text.SHARE_ICON_TEXT));

		log.info("TC_11_Step: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, Register_Text.SAVE_IMG_ICON_TEXT));

		log.info("TC_11_Step: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, Register_Text.BUTTON_NEW_TRANSACTION_TEXT);
	}

	@Test
	public void TC_12_DangKyPhatHanhTheGhiNo_ChonHangChuanConnect24VISATrichNoTuDongBoPhatHanhThePhu() {
		
		log.info("TC_12_Step: Nhap thong tin man hinh dang ky");
		registerOnline.DangKyPhatHanhTheGhiNo(Register_Online_data.Register_Onl_Data.TYPE_IDENTIFICATION[1], Register_Online_data.Register_Onl_Data.NO_IDENTIFICATION, getBackWardYear(1), Register_Online_data.Register_Onl_Data.LOCATION[1], Register_Online_data.Register_Onl_Data.ADDRESS, Register_Online_data.Register_Onl_Data.EMAIL);

		log.info("TC_12_Step: Verify text man hinh dang ky phat hanh the ghi no");
		verifyEquals(registerOnline.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvTitleBar"), Register_Text.REGISTER_PUBLISH_DEBIT_CARD_TEXT);

		log.info("TC_12_Step: Click hang the, chon hang the la vang");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutHangThe");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Register_Onl_Data.CARD_RANK[0]);

		log.info("TC_12_Step: Click loại the ghi no");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Text.VCB_CONNECT_24H_VISA_TEXT);

		log.info("TC_12_Step: Click chon loai thanh toan phi");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutThanhToanPhi");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Register_Onl_Data.FEE_PAYMENT[0]);

		log.info("TC_12_Step: Click tiep tuc");
		registerOnline.clickToDynamicButton(driver, Register_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_12_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "2"), Register_Text.REGISTER_SUCCESS_TEXT);

		log.info("TC_12_Step: verify text dieu kien");
		verifyEquals(registerOnline.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvNote"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_12_Step: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, Register_Text.SHARE_ICON_TEXT));

		log.info("TC_12_Step: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, Register_Text.SAVE_IMG_ICON_TEXT));

		log.info("TC_12_Step: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, Register_Text.BUTTON_NEW_TRANSACTION_TEXT);

	}

	@Test
	public void TC_13_DangKyPhatHanhTheGhiNo_ChonHangChuanUnionPAYNopTienMatBoPhatHanhThePhu() {
		
		log.info("TC_13_Step: Nhap thong tin man hinh dang ky");
		registerOnline.DangKyPhatHanhTheGhiNo(Register_Online_data.Register_Onl_Data.TYPE_IDENTIFICATION[1], Register_Online_data.Register_Onl_Data.NO_IDENTIFICATION, getBackWardYear(1), Register_Online_data.Register_Onl_Data.LOCATION[1], Register_Online_data.Register_Onl_Data.ADDRESS, Register_Online_data.Register_Onl_Data.EMAIL);

		log.info("TC_13_Step: Verify text man hinh dang ky phat hanh the ghi no");
		verifyEquals(registerOnline.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvTitleBar"), Register_Text.REGISTER_PUBLISH_DEBIT_CARD_TEXT);

		log.info("TC_13_Step: Click hang the, chon hang the la vang");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutHangThe");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Register_Onl_Data.CARD_RANK[1]);

		log.info("TC_13_Step: Click loại the ghi no");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Text.VCB_UNION_PAY_TEXT);

		log.info("TC_13_Step: Click chon loai thanh toan phi");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutThanhToanPhi");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Register_Onl_Data.FEE_PAYMENT[0]);

		log.info("TC_13_Step: Select yeu cau phat hanh the phu");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Text.PUBLISH_SECOND_CARD_REQUEST_TEXT);

		log.info("TC_13_Step: Nhap ho ten chu the");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, nameCustomer, "com.VCB:id/layoutHoTenDayDu");

		log.info("TC_13_Step: Click giay to tuy than");
		registerOnline.clickToDynamicDropDown(driver, Register_Text.IDENTIFICATION_TEXT);
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Register_Onl_Data.TYPE_IDENTIFICATION[1]);

		log.info("TC_13_Step: Input so ho chieu");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Register_Onl_Data.NO_IDENTIFICATION, "com.VCB:id/layoutCMTND");

		log.info("TC_13_Step: Click ngay cap");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayCap");

		log.info("TC_13_Step: Click nam");
		registerOnline.clickToTextID(driver, "android:id/date_picker_header_year");

		log.info("TC_13_Step: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(5));

		log.info("TC_13_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, Register_Text.BUTTON_OK_TEXT);

		log.info("TC_13_Step: Input noi cap");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Register_Onl_Data.LOCATION[1], "com.VCB:id/layoutNoiCap2");

		log.info("TC_13_Step: Click tiep tuc");
		registerOnline.clickToDynamicButton(driver, Register_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_13_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "1", "2"), Register_Text.REGISTER_SUCCESS_TEXT);

		log.info("TC_13_Step: verify text dieu kien");
		verifyEquals(registerOnline.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvNote"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_13_Step: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, Register_Text.SHARE_ICON_TEXT));

		log.info("TC_13_Step: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, Register_Text.SAVE_IMG_ICON_TEXT));

		log.info("TC_13_Step: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, Register_Text.BUTTON_NEW_TRANSACTION_TEXT);
	}
	
	@AfterClass(alwaysRun = true)
	public void afterClass() {
		service.stop();
	}
}
