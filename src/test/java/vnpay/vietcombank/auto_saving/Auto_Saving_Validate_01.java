package vnpay.vietcombank.auto_saving;

import java.io.IOException;

import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import pageObjects.AutoSavingPageObject;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;

import vietcombank_test_data.Auto_Saving_Data;
import vietcombank_test_data.Account_Data;

public class Auto_Saving_Validate_01 extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject home;
	private AutoSavingPageObject autoSaving;
	
	private String sourceAccount,savingAccount,term,savingAccountMoney,startDate,endDate,text;
	private boolean status;

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
		login.Global_login("0335059860", pass, opt);

	}

	@Test
	public void TC_01_TietKiemTuDong_KiemTraManHinhChucNang() {

		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_01_Step_01: Keo xuong va click vao phan 'Tiet kiem tu dong'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Tiết kiệm tự động");
		autoSaving = PageFactoryManager.getAutoSavingPageObject(driver);

		log.info("TC_01_Step_02: Xac nhan hien thi title 'Tiet kiem tu dong");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), Auto_Saving_Data.VALIDATE.AUTO_SAVING_TITLE);

		log.info("TC_01_Step_03: Xac nhan hien thi Icon 'Back");
		verifyTrue(autoSaving.isDynamicImageHomeDisplay(driver, "com.VCB:id/ivTitleLeft"));

		log.info("TC_01_Step_04: Xac nhan hien thi label 'Tai khoan nguon");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvInfoContent"), Auto_Saving_Data.VALIDATE.SOURCE_ACCOUNT_TITLE);

		log.info("TC_01_Step_05: Xac nhan hien thi label 'Tai khoan nguon");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvInfoContent"), Auto_Saving_Data.VALIDATE.SOURCE_ACCOUNT_TITLE);

		log.info("TC_01_Step_06: Xac nhan hien thi label 'Thong tin giao dich");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), Auto_Saving_Data.VALIDATE.TRANSACTION_INFO_TITLE);

		log.info("TC_01_Step_07: Xac nhan hien thi Dropdown 'Tai khoan tiet kiem");
		verifyEquals(autoSaving.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/TaiKhoanTietKiem"), Auto_Saving_Data.VALIDATE.SAVING_ACCOUNT_DEFAULT_TEXT);

		log.info("TC_01_Step_08: Xac nhan hien thi Button 'Tiep tuc");
		verifyTrue(autoSaving.isDynamicButtonByIdDisplayed(driver, "com.VCB:id/btContinue"));
	}

	@Test
	public void TC_02_TietKiemTuDong_KiemTraMacDinhTKNguon() {

		log.info("TC_02_Step_01: Click vao drodown 'Tai khoan nguon");
		sourceAccount = autoSaving.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/llContent");
		autoSaving.clickToTextViewCombobox(driver, "com.VCB:id/tvContent");

		log.info("TC_02_Step_02: Xac nhan 'Tai khoan nguon' dang hien thi la TK dau tien");
		verifyEquals(autoSaving.getFirstOptionInList("com.VCB:id/tvContent1"), sourceAccount);
		autoSaving.clickToTextViewCombobox(driver, "com.VCB:id/cancel_button");
	}

	@Test
	public void TC_03_TietKiemTuDong_KiemTraThongTinTKNguon() {

		log.info("TC_03_Step_01: Xac dinh hien thi dropdown 'Tai khoan nguon'");
		verifyTrue(autoSaving.isDynamicTextViewByLinearLayoutIdDisplayed(driver, "com.VCB:id/llContent"));

		log.info("TC_03_Step_02: Xac dinh hien thi label 'So du kha dung'");
		verifyTrue(autoSaving.isDynamicMessageAndLabelTextDisplayed(driver, "Số dư khả dụng"));

		log.info("TC_03_Step_03: Xac dinh hien thi label so tien TK nguon");
		verifyTrue(autoSaving.isDynamicTextNumberCustomerDisplayed(driver, "Số dư khả dụng"));

	}

	@Test
	public void TC_04_TietKiemTuDong_Chon1TKNguon_VND() {

		log.info("TC_04_Step_01: Click vao drodown 'Tai khoan nguon");
		autoSaving.clickToTextViewCombobox(driver, "com.VCB:id/tvContent");

		log.info("TC_04_Step_02: Tim kiem tai khoan nguon VND");
		status = autoSaving.isTextDisplayedInListTextElements(driver, "VND", "com.VCB:id/tvContent2");
		if (status == true) {
			log.info("TC_04_Step_03: Click vao tai khoan nguon VND dau tien tim duoc");
			text = autoSaving.getFirstElementContainsText("VND", "com.VCB:id/tvContent2");
			autoSaving.clickToDynamicButtonLinkOrLinkText(driver, text);
		} else {
			log.info("TC_04_Step_03: Tat Dropdownlist");
			autoSaving.clickToTextViewCombobox(driver, "com.VCB:id/cancel_button");
			throw new SkipException("These Tests shouldn't be run in Production");
		}
	}

	@Test
	public void TC_05_TietKiemTuDong_Chon1TKNguon_USD() {

		log.info("TC_05_Step_01: Click vao drodown 'Tai khoan nguon");
		autoSaving.clickToTextViewCombobox(driver, "com.VCB:id/tvContent");

		log.info("TC_05_Step_02: Tim kiem tai khoan nguon USD");
		status = autoSaving.isTextDisplayedInListTextElements(driver, "USD", "com.VCB:id/tvContent2");
		if (status == true) {
			log.info("TC_05_Step_03: Click vao tai khoan nguon USD dau tien tim duoc");
			text = autoSaving.getFirstElementContainsText("USD", "com.VCB:id/tvContent2");
			autoSaving.clickToDynamicButtonLinkOrLinkText(driver, text);
		} else {
			log.info("TC_05_Step_03: Tat Dropdownlist");
			autoSaving.clickToTextViewCombobox(driver, "com.VCB:id/cancel_button");
			throw new SkipException("These Tests shouldn't be run in Production");
		}
	}

	@Test
	public void TC_06_TietKiemTuDong_KiemTraTKDichMacDinh() {

		log.info("TC_06_Step_01: Xac nhan hien thi Dropdown 'Tai khoan tiet kiem");
		verifyEquals(autoSaving.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/TaiKhoanTietKiem"), Auto_Saving_Data.VALIDATE.SAVING_ACCOUNT_DEFAULT_TEXT);

	}

	@Test
	public void TC_07_TietKiemTuDong_KiemTraHienThiThongTinTKDich() {

		log.info("TC_07_Step_01: Click vao drodown 'Tai khoan nguon");
		autoSaving.clickToTextViewCombobox(driver, "com.VCB:id/tvContent");

		log.info("TC_07_Step_02: Chon tai khoan nguon");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_07_Step_03: Chon tai khoan dich ");
		autoSaving.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/TaiKhoanTietKiem");
		savingAccount = autoSaving.getFirstOptionInList("com.VCB:id/tvContent1");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, savingAccount);

		log.info("TC_07_Step_04: Xac nhan hien thi dung TK tiet kiem");
		verifyEquals(autoSaving.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/TaiKhoanTietKiem"), savingAccount);

		log.info("TC_07_Step_05: Xac nhan hien thi Ky han");
		verifyTrue(autoSaving.isDynamicMessageAndLabelTextDisplayed(driver, "Kỳ hạn"));
		verifyTrue(autoSaving.isDynamicTextNumberCustomerDisplayed(driver, "Kỳ hạn"));
		term = autoSaving.getDynamicTextByLabel(driver, "Kỳ hạn");

		log.info("TC_07_Step_06: Xac nhan hien thi ngay den han");
		verifyTrue(autoSaving.isDynamicMessageAndLabelTextDisplayed(driver, "Ngày đến hạn"));
		verifyTrue(autoSaving.isDynamicTextNumberCustomerDisplayed(driver, "Ngày đến hạn"));
		startDate = autoSaving.getDynamicTextByLabel(driver, "Ngày đến hạn");

		log.info("TC_07_Step_07: Xac nhan hien thi So du hien tai");
		verifyTrue(autoSaving.isDynamicMessageAndLabelTextDisplayed(driver, "Số dư hiện tại"));
		verifyTrue(autoSaving.isDynamicTextNumberCustomerDisplayed(driver, "Số dư hiện tại"));
		savingAccountMoney = autoSaving.getDynamicTextByLabel(driver, "Số dư hiện tại");

		log.info("TC_07_Step_08: Xac nhan hien thi ngay bat dau / ngay ket thuc");
		verifyTrue(autoSaving.isDynamicTextViewByLinearLayoutIdDisplayed(driver, "com.VCB:id/layoutNgayBatDau"));
		verifyTrue(autoSaving.isDynamicTextViewByLinearLayoutIdDisplayed(driver, "com.VCB:id/layoutNgayKetThuc"));
		verifyEquals(autoSaving.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayBatDau"), startDate);
	}

	@Test
	public void TC_08_TietKiemTuDong_ChonLaiTKDich() {

		log.info("TC_08_Step_01: Chon tai khoan dich ");
		autoSaving.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/TaiKhoanTietKiem");
		savingAccount = autoSaving.getFirstOptionInList("com.VCB:id/tvContent1");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, savingAccount);

		log.info("TC_08_Step_02: Xac nhan hien thi dung TK tiet kiem");
		verifyEquals(autoSaving.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/TaiKhoanTietKiem"), savingAccount);

		log.info("TC_08_Step_03: Xac nhan hien thi dung Ky han");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, "Kỳ hạn"), term);

		log.info("TC_08_Step_04: Xac nhan hien thi dung ngay den han");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, "Ngày đến hạn"), startDate);

		log.info("TC_08_Step_05: Xac nhan hien thi So du hien tai");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, "Số dư hiện tại"), savingAccountMoney);

		log.info("TC_08_Step_06: Xac nhan hien thi ngay bat dau / ngay ket thuc");
		verifyEquals(autoSaving.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayBatDau"), startDate);
	}

	@Test
	public void TC_09_TietKiemTuDong_KiemTraHienThiMacDinhOSoTienChuyen() {

		log.info("TC_09_Step_01: Kiem tra hien thi mac dinh o so tien chuyen");
		verifyTrue(autoSaving.isDynamicTextInInputBoxDisPlayed(driver, Auto_Saving_Data.VALIDATE.TRANSFER_MONEY_TEXT));
	}

	@Test
	public void TC_10_TietKiemTuDong_KiemTraKiTuNhap() {

		log.info("TC_10_Step_01: Nhap ki tu vao o nhap so dien thoai");
		autoSaving.inputIntoEditTextByID(driver, "4nh5jh6nb7", "com.VCB:id/edtContent2");

		log.info("TC_10_Step_02: Xac nhan chi hien thi chu so o o nhap so dien thoai");
		verifyEquals(autoSaving.getTextInEditTextFieldByID(driver, "com.VCB:id/edtContent2"), addCommasToLong("4567"));
	}

	@Test
	public void TC_11_TietKiemTuDong_KiemTraGioiHanNhapKiTu() {

		log.info("TC_11_Step_01: Nhap hon 10 chu so vào o So dien thoai mac dinh");
		autoSaving.inputIntoEditTextByID(driver, "123456789012345", "com.VCB:id/edtContent2");

		log.info("TC_11_Step_02: Xac nhan hien thi o trong ");
		verifyEquals(autoSaving.getTextInEditTextFieldByID(driver, "com.VCB:id/edtContent2"), addCommasToLong("1234567890"));
	}

	@Test
	public void TC_12_TietKiemTuDong_NhapSoThapPhan() {

		log.info("TC_12_Step_01: Nhap hon 10 chu so vào o So dien thoai mac dinh");
		autoSaving.inputIntoEditTextByID(driver, "1.2.3.4.", "com.VCB:id/edtContent2");

		log.info("TC_12_Step_02: Xac nhan chi hien thi duy nhat 10 chu so dau tien o trong o");
		verifyEquals(autoSaving.getTextInEditTextFieldByID(driver, "com.VCB:id/edtContent2"), addCommasToLong("1234"));

		log.info("TC_12_Step_03: Click vao drodown 'Tai khoan nguon");
		autoSaving.clickToTextViewCombobox(driver, "com.VCB:id/tvContent");

		log.info("TC_12_Step_04: Tim kiem tai khoan nguon USD");
		status = autoSaving.isTextDisplayedInListTextElements(driver, "USD", "com.VCB:id/tvContent2");
		if (status == true) {
			log.info("TC_12_Step_05: Click vao tai khoan nguon USD dau tien tim duoc");
			text = autoSaving.getFirstElementContainsText("VND", "com.VCB:id/tvContent2");
			autoSaving.clickToDynamicButtonLinkOrLinkText(driver, text);

			log.info("TC_12_Step_06: Chon tai khoan dich ");
			autoSaving.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/TaiKhoanTietKiem");
			savingAccount = autoSaving.getFirstOptionInList("com.VCB:id/tvContent1");
			autoSaving.clickToDynamicButtonLinkOrLinkText(driver, savingAccount);

			log.info("TC_12_Step_07: Nhap hon 10 chu so vào o So dien thoai mac dinh");
			autoSaving.inputIntoEditTextByID(driver, "1.2.3.4.5.6.7.8.9.0.12345", "com.VCB:id/edtContent2");

			log.info("TC_12_Step_08: Xac nhan chi hien thi duy nhat 10 chu so dau tien o trong o");
			verifyEquals(autoSaving.getTextInEditTextFieldByID(driver, "com.VCB:id/edtContent2"), "1.2");

		} else {
			log.info("TC_12_Step_05: Tat Dropdownlist");
			autoSaving.clickToTextViewCombobox(driver, "com.VCB:id/cancel_button");
			throw new SkipException("These Tests shouldn't be run in Production");
		}
	}

	@Test
	public void TC_13_TietKiemTuDong_KiemTraMacDinhSoTienQuyDoi() {

		log.info("TC_13_Step_01: Click vao drodown 'Tai khoan nguon");
		autoSaving.clickToTextViewCombobox(driver, "com.VCB:id/tvContent");

		log.info("TC_13_Step_02: Tim kiem tai khoan nguon USD");
		status = autoSaving.isTextDisplayedInListTextElements(driver, "USD", "com.VCB:id/tvContent2");
		if (status == true) {
			log.info("TC_13_Step_03: Click vao tai khoan nguon USD dau tien tim duoc");
			text = autoSaving.getFirstElementContainsText("VND", "com.VCB:id/tvContent2");
			autoSaving.clickToDynamicButtonLinkOrLinkText(driver, text);

			log.info("TC_13_Step_04: Chon tai khoan dich ");
			autoSaving.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/TaiKhoanTietKiem");
			savingAccount = autoSaving.getFirstOptionInList("com.VCB:id/tvContent1");
			autoSaving.clickToDynamicButtonLinkOrLinkText(driver, savingAccount);

			log.info("TC_13_Step_05: Xac nhan hien thi label ty gia quy doi");
			verifyTrue(autoSaving.isDynamicMessageAndLabelTextDisplayed(driver, Auto_Saving_Data.VALIDATE.EXCHANGE_RATE_TITLE));
			verifyTrue(autoSaving.isDynamicTextNumberCustomerDisplayed(driver, Auto_Saving_Data.VALIDATE.EXCHANGE_RATE_TITLE));

		} else {
			log.info("TC_13_Step_03: Tat Dropdownlist");
			autoSaving.clickToTextViewCombobox(driver, "com.VCB:id/cancel_button");
			throw new SkipException("These Tests shouldn't be run in Production");
		}
	}

	@Test
	public void TC_14_TietKiemTuDong_KiemTraHienThiMacDinhNgayKetThuc() {

		log.info("TC_14_Step_01: Kiem tra hien thi mac dinh ngay ket thuc");
		verifyEquals(autoSaving.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayKetThuc"), Auto_Saving_Data.VALIDATE.END_DATE_TEXT);
	}

	@Test
	public void TC_15_TietKiemTuDong_KiemTranNhanNgayKetThuc() {

		log.info("TC_15_Step_01: Kiem tra hien thi lich");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, "Ngày kết thúc");
		verifyTrue(autoSaving.isDynamicDatePickerByIdDisplayed(driver, "android:id/datePicker"));
	}

	@Test
	public void TC_16_TietKiemTuDong_KiemTranChonNgayKetThucNhoHonHienTai() {

		log.info("TC_16_Step_01: Chon ngay ket thuc nho hon hien tai");
		autoSaving.clickToDynamicDateInDateTimePicker(driver, getBackWardDay(2));
		autoSaving.clickToDynamicButton(driver, "OK");

		log.info("TC_16_Step_02: Xac nhan hien thi ngay hien tai o o chon ngay ket thuc");
		verifyEquals(autoSaving.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayKetThuc"), getForwardDate(0));

	}

	@Test
	public void TC_17_TietKiemTuDong_KiemTraChonNgayKetThucLonHonHienTai() {

		log.info("TC_17_Step_01: Chon ngay ket thuc lon hon hien tai");
		autoSaving.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayKetThuc");
		autoSaving.clickToDynamicDateInDateTimePicker(driver, getForWardDay(2));
		autoSaving.clickToDynamicButton(driver, "OK");

		log.info("TC_17_Step_02: Xac nhan hien thi dung ngay ket thuc");
		verifyEquals(autoSaving.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayKetThuc"), getForwardDate(2));

	}
	
	@Test
	public void TC_18_TietKiemTuDong_KiemTraDinhDangNgayKetThuc() {
		
		log.info("TC_18_Step_01: Xac nhan hien thi dung dinh dang ngay ket thuc");
		endDate = autoSaving.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayKetThuc");
		verifyTrue(validateDateFormat(endDate, "dd/mm/yyyy"));
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		service.stop();
	}

}
