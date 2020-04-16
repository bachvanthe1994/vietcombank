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
import pageObjects.saving_online.SavingOnlinePageObject;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.Auto_Saving_Data;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.SavingOnline_Data;

public class Auto_Saving_Validate_01 extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject home;
	private AutoSavingPageObject autoSaving;
	private SavingOnlinePageObject savingOnline;

	private String sourceAccount, savingAccount, startDate, endDate, text, sourceAccountMoney, account;

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone",
			"pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities,
			String appPackage, String appName, String phone, String pass, String opt)
			throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		if (deviceType.contains("android")) {
			driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		} else if (deviceType.contains("ios")) {
			driver = openIOSApp(deviceName, udid, url);
		}
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
		home = PageFactoryManager.getHomePageObject(driver);
		savingOnline = PageFactoryManager.getSavingOnlinePageObject(driver);
	}

	@Parameters({ "otp" })
	@Test
	public void TC_00_MoTaiKhoanTietKiem_TaiKhoanNguon_VND(String otp) {

		log.info("TC_00_1_Click Mo tai khoan tiet kiem");
		home.scrollDownToText(driver, "Tín dụng");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Mở tài khoản tiết kiệm");

		log.info("TC_00_2_Chon so tai khoan");
		savingOnline.clickToDynamicDropDown(driver, "Số tài khoản");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_00_3_Chon ky han gui");
		savingOnline.clickToDynamicDropDownListTextViewByHeader(driver, "Thông tin giao dịch", "1");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Data.TEXT.TERM);

		log.info("TC_00_4_Nhap so tien gui");
		savingOnline.inputToDynamicInputBoxByHeader(driver, Auto_Saving_Data.TEXT.INPUT_VND, "Thông tin giao dịch",
				"2");

		log.info("TC_00_5_Chon hinh thuc chuyen tien");
		savingOnline.clickToDynamicDropDownListTextViewByHeader(driver, "Thông tin giao dịch", "3");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Data.TEXT.FORM_OF_PAYMENT);

		log.info("TC_00_6_Chon dong y tuan thu cam ket");
		savingOnline.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");

		log.info("TC_00_7_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_00_8_Kiem tra man hinh xac nhan thong tin");

		log.info("TC_00_8_1_Kiem tra tai khoan nguon");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"),
				Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_VND);

		log.info("TC_00_8_2_Kiem tra ky han gui");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Kỳ hạn gửi"), Auto_Saving_Data.TEXT.TERM);

		log.info("TC_00_8_3_Kiem tra lai suat");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Lãi suất"), "4.5%/Năm");

		log.info("TC_00_8_4_Kiem tra so tien gui");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Số tiền gửi"),
				addCommasToLong(Auto_Saving_Data.TEXT.INPUT_VND) + " VND");

		log.info("TC_00_8_5_Kiem tra hinh thuc tra lai");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Hình thức trả lãi"),
				Auto_Saving_Data.TEXT.FORM_OF_PAYMENT);

		log.info("TC_00_9_Chon phuong thuc xac thuc");
		savingOnline.scrollDownToText(driver, "Chọn phương thức xác thực");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_00_10_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, "Tiếp tục");

		savingOnline.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		savingOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_00_11_Kiem tra man hinh Giao dich thanh cong");
		log.info("TC_00_12_1_Kiem tra Giao dich thanh cong");
		verifyTrue(savingOnline.isDynamicMessageAndLabelTextDisplayed(driver, SavingOnline_Data.SUCCESS_TRANSACTION));

		log.info("TC_00_12_2_Lay ma giao dich");
		savingAccount = savingOnline.getDynamicTextInTransactionDetail(driver, "Số tài khoản tiết kiệm");

		log.info("TC_00_13_Click nut Home ve man hinh chinh");
		savingOnline.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivHome");
	}

	@Test
	public void TC_01_TietKiemTuDong_KiemTraManHinhChucNang() {

		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_01_Step_01: Keo xuong va click vao phan 'Tiet kiem tu dong'");
		home.scrollDownToText(driver, "Tín dụng");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Tiết kiệm tự động");

		log.info("TC_01_Step_02: Xac nhan hien thi title 'Tiet kiem tu dong");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"),
				Auto_Saving_Data.VALIDATE.AUTO_SAVING_TITLE);

		log.info("TC_01_Step_03: Xac nhan hien thi Icon 'Back");
		verifyTrue(autoSaving.isDynamicImageHomeDisplay(driver, "com.VCB:id/ivTitleLeft"));

		log.info("TC_01_Step_04: Xac nhan hien thi label 'Tai khoan nguon'");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvInfoContent"),
				Auto_Saving_Data.VALIDATE.SOURCE_ACCOUNT_TITLE);

		log.info("TC_01_Step_05: Xac nhan hien thi Combobox 'Tai khoan nguon'");
		verifyTrue(autoSaving.isDynamicLinearlayoutByIdDisplayed(driver, "com.VCB:id/llContent"));

		log.info("TC_01_Step_06: Xac nhan hien thi So du kha dung");
		verifyTrue(autoSaving.isDynamicLinearlayoutByIdDisplayed(driver, "com.VCB:id/llInfoBottom"));

		log.info("TC_01_Step_07: Xac nhan hien thi label 'Thong tin giao dich");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"),
				Auto_Saving_Data.VALIDATE.TRANSACTION_INFO_TITLE);

		log.info("TC_01_Step_08: Xac nhan hien thi Dropdown 'Tai khoan tiet kiem");
		verifyEquals(autoSaving.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/TaiKhoanTietKiem"),
				Auto_Saving_Data.VALIDATE.SAVING_ACCOUNT_DEFAULT_TEXT);

		log.info("TC_01_Step_09: Xac nhan hien thi Chu ky chuyen");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvChuKyChuyen"), "Chu kỳ chuyển");

		log.info("TC_01_Step_10: Xac nhan hien thi textbox ngay bat dau / ngay ket thuc");
		verifyTrue(autoSaving.isDynamicTextViewByLinearLayoutIdDisplayed(driver, "com.VCB:id/layoutNgayBatDau"));
		verifyTrue(autoSaving.isDynamicTextViewByLinearLayoutIdDisplayed(driver, "com.VCB:id/layoutNgayKetThuc"));

		log.info("TC_01_Step_11: Xac nhan hien thi textbox so tien chuyen");
		verifyTrue(autoSaving.isDynamicTextViewByLinearLayoutIdDisplayed(driver, "com.VCB:id/SoTien"));

		log.info("TC_01_Step_12: Xac nhan hien thi Button 'Tiep tuc");
		verifyTrue(autoSaving.isDynamicButtonByIdDisplayed(driver, "com.VCB:id/btContinue"));
	}

	@Test
	public void TC_02_TietKiemTuDong_KiemTraMacDinhTKNguon() {

		log.info("TC_02_Step_01: Click vao drodown 'Tai khoan nguon");
		sourceAccount = autoSaving.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/llContent");
		autoSaving.clickToTextID(driver, "com.VCB:id/tvContent");

		log.info("TC_02_Step_02: Xac nhan 'Tai khoan nguon' dang hien thi la TK dau tien");
		verifyEquals(autoSaving.getFirstOptionInList("com.VCB:id/tvContent1"), sourceAccount);
		autoSaving.clickToTextID(driver, "com.VCB:id/cancel_button");
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
		autoSaving.clickToTextID(driver, "com.VCB:id/tvContent");

		log.info("TC_04_Step_02: Click vao tai khoan nguon VND ");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_04_Step_03: Xac nhan hien thi dung so du la VND");
		sourceAccountMoney = autoSaving.getDynamicTextByLabel(driver, "Số dư khả dụng");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"),
				Account_Data.Valid_Account.ACCOUNT2);
		verifyTrue(autoSaving.isTextDisplayedInListTextElements(driver, sourceAccountMoney,
				"com.VCB:id/tvInfoBottomRight"));
	}

	@Test
	public void TC_05_TietKiemTuDong_Chon1TKNguon_USD() {

		log.info("TC_05_Step_01: Click vao drodown 'Tai khoan nguon");
		autoSaving.clickToTextID(driver, "com.VCB:id/tvContent");

		log.info("TC_05_Step_02: Click vao tai khoan nguon USD");
		sourceAccount = Account_Data.Valid_Account.USD_ACCOUNT;
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount);
		sourceAccountMoney = autoSaving.getMoneyByAccount(driver, "Số dư khả dụng");
		if (sourceAccountMoney.contains("VND")) {
			autoSaving.clickToTextID(driver, "com.VCB:id/tvContent");
			sourceAccount = Account_Data.Valid_Account.LIST_ACCOUNT_FROM[4];
			autoSaving.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount);
		}

		log.info("TC_05_Step_03: Xac nhan hien thi dung so du la USD");
		sourceAccountMoney = autoSaving.getDynamicTextByLabel(driver, "Số dư khả dụng");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), sourceAccount);
		verifyTrue(autoSaving.isTextDisplayedInListTextElements(driver, sourceAccountMoney,
				"com.VCB:id/tvInfoBottomRight"));
		verifyTrue(autoSaving.isDynamicMessageAndLabelTextDisplayed(driver,
				Auto_Saving_Data.VALIDATE.EXCHANGE_RATE_TITLE));
		verifyTrue(autoSaving.isDynamicMessageAndLabelTextDisplayed(driver, Auto_Saving_Data.RATE.USD_RATE));
	}

	@Test
	public void TC_06_TietKiemTuDong_KiemTraTKDichMacDinh() {

		log.info("TC_06_Step_01: Xac nhan hien thi Dropdown 'Tai khoan tiet kiem");
		verifyEquals(autoSaving.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/TaiKhoanTietKiem"),
				Auto_Saving_Data.VALIDATE.SAVING_ACCOUNT_DEFAULT_TEXT);

	}

	@Test
	public void TC_07_TietKiemTuDong_KiemTraHienThiThongTinTKDich() {

		log.info("TC_07_Step_01: Click vao drodown 'Tai khoan nguon");
		autoSaving.clickToTextID(driver, "com.VCB:id/tvContent");

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

		log.info("TC_07_Step_06: Xac nhan hien thi ngay den han");
		verifyTrue(autoSaving.isDynamicMessageAndLabelTextDisplayed(driver, "Ngày đến hạn"));
		verifyTrue(autoSaving.isDynamicTextNumberCustomerDisplayed(driver, "Ngày đến hạn"));
		startDate = autoSaving.getDynamicTextByLabel(driver, "Ngày đến hạn");

		log.info("TC_07_Step_07: Xac nhan hien thi So du hien tai");
		verifyTrue(autoSaving.isDynamicMessageAndLabelTextDisplayed(driver, "Số dư hiện tại"));
		verifyTrue(autoSaving.isDynamicTextNumberCustomerDisplayed(driver, "Số dư hiện tại"));

		log.info("TC_07_Step_08: Xac nhan hien thi ngay bat dau / ngay ket thuc");
		verifyEquals(autoSaving.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayKetThuc"),
				"Ngày kết thúc");
		verifyEquals(autoSaving.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayBatDau"), startDate);

		log.info("TC_07_Step_10_Click nut Back ve man hinh chinh");
		savingOnline.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_01_Step_11: Keo xuong va click vao phan 'Tiet kiem tu dong'");
		home.scrollDownToText(driver, "Tín dụng");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Tiết kiệm tự động");
		autoSaving = PageFactoryManager.getAutoSavingPageObject(driver);

		log.info("TC_07_Step_12: Chon tai khoan nguon USD/EUR");
		autoSaving.clickToTextID(driver, "com.VCB:id/tvContent");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, "0019967190");

		log.info("TC_07_Step_13: Chon tai khoan dich ");
		autoSaving.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/TaiKhoanTietKiem");
		savingAccount = autoSaving.getFirstOptionInList("com.VCB:id/tvContent1");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, savingAccount);

		log.info("TC_07_Step_14: Xac nhan  hien thi ty gia quy doi");
		verifyTrue(autoSaving.isDynamicLinearlayoutByIdDisplayed(driver, "com.VCB:id/llContent1More"));
	}

	@Test
	public void TC_08_TietKiemTuDong_ChonLaiTKDich() {

		log.info("TC_08_Step_01: Chon tai khoan nguon");
		autoSaving.clickToTextID(driver, "com.VCB:id/tvContent");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_08_Step_02: Chon tai khoan dich ");
		autoSaving.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/TaiKhoanTietKiem");
		savingAccount = autoSaving.getFirstOptionInList("com.VCB:id/tvContent1");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, savingAccount);

		log.info("TC_08_Step_02: Xac nhan hien thi dung tk dich vua chon");
		verifyEquals(autoSaving.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/TaiKhoanTietKiem"), savingAccount);
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
		verifyEquals(autoSaving.getTextInEditTextFieldByID(driver, "com.VCB:id/edtContent2"), "Số tiền chuyển");
	}

	@Test
	public void TC_12_TietKiemTuDong_NhapSoThapPhan() {

		log.info("TC_12_Step_01: Click vao drodown 'Tai khoan nguon");
		autoSaving.clickToTextID(driver, "com.VCB:id/tvContent");

		log.info("TC_12_Step_02: Click vao tai khoan nguon VND dau tien tim duoc");
		sourceAccount = Account_Data.Valid_Account.USD_ACCOUNT;
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount);
		sourceAccountMoney = autoSaving.getMoneyByAccount(driver, "Số dư khả dụng");
		if (sourceAccountMoney.contains("VND")) {
			autoSaving.clickToTextID(driver, "com.VCB:id/tvContent");
			sourceAccount = Account_Data.Valid_Account.LIST_ACCOUNT_FROM[4];
			autoSaving.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount);
		}

		log.info("TC_12_Step_03: Chon tai khoan dich ");
		autoSaving.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/TaiKhoanTietKiem");
		savingAccount = autoSaving.getFirstOptionInList("com.VCB:id/tvContent1");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, savingAccount);

		log.info("TC_12_Step_04: Nhap so thap phan vao o So tien chuyen");
		autoSaving.inputIntoEditTextByID(driver, "1.2", "com.VCB:id/edtContent2");

		log.info("TC_12_Step_05: Xac nhan chi hien thi duy nhat 10 chu so dau tien o trong o");
		verifyEquals(autoSaving.getTextInEditTextFieldByID(driver, "com.VCB:id/edtContent2"), "Số tiền chuyển");

		log.info("TC_12_Step_06: Click vao drodown 'Tai khoan nguon");
		autoSaving.clickToTextID(driver, "com.VCB:id/tvContent");

		log.info("TC_12_Step_07: Click vao tai khoan nguon USD");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount);

		log.info("TC_12_Step_08: Chon tai khoan dich ");
		autoSaving.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/TaiKhoanTietKiem");
		savingAccount = autoSaving.getFirstOptionInList("com.VCB:id/tvContent1");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, savingAccount);

		log.info("TC_12_Step_09: Nhap so thap phan vao o So tien chuyen");
		autoSaving.inputIntoEditTextByID(driver, "1.2", "com.VCB:id/edtContent2");
		autoSaving.sleep(driver, 2000);

		log.info("TC_12_Step_10: Xac nhan chi hien thi duy nhat 10 chu so dau tien o trong o");
		verifyEquals(autoSaving.getTextInEditTextFieldByID(driver, "com.VCB:id/edtContent2"), "1.2");
	}

	@Test
	public void TC_13_TietKiemTuDong_KiemTraMacDinhSoTienQuyDoi() {

		log.info("TC_13_Step_01: Xac nhan hien thi label ty gia quy doi");
		verifyTrue(autoSaving.isDynamicMessageAndLabelTextDisplayed(driver,
				Auto_Saving_Data.VALIDATE.EXCHANGE_RATE_TITLE));
		verifyTrue(autoSaving.isDynamicMessageAndLabelTextDisplayed(driver, Auto_Saving_Data.RATE.USD_RATE));

		log.info("TC_13_Step_02: Click vao drodown 'Tai khoan nguon");
		autoSaving.clickToTextID(driver, "com.VCB:id/tvContent");

		log.info("TC_13_Step_03: Click vao tai khoan nguon EUR");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, "0019967190");

		log.info("TC_13_Step_04: Chon tai khoan dich ");
		autoSaving.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/TaiKhoanTietKiem");
		savingAccount = autoSaving.getFirstOptionInList("com.VCB:id/tvContent1");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, savingAccount);

		log.info("TC_13_Step_05: Xac nhan hien thi label ty gia quy doi");
		verifyTrue(autoSaving.isDynamicMessageAndLabelTextDisplayed(driver,
				Auto_Saving_Data.VALIDATE.EXCHANGE_RATE_TITLE));
		verifyTrue(autoSaving.isDynamicMessageAndLabelTextDisplayed(driver, Auto_Saving_Data.RATE.EUR_RATE));

	}

	@Test
	public void TC_14_TietKiemTuDong_KiemTraHienThiMacDinhNgayKetThuc() {

		log.info("TC_14_Step_01: Kiem tra hien thi mac dinh ngay ket thuc");
		verifyEquals(autoSaving.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayKetThuc"),
				Auto_Saving_Data.VALIDATE.END_DATE_TEXT);
	}

	@Test
	public void TC_15_TietKiemTuDong_KiemTranNhanNgayKetThuc() {

		log.info("TC_15_Step_01: Kiem tra hien thi lich");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, "Ngày kết thúc");
		verifyTrue(autoSaving.isDynamicDatePickerByIdDisplayed(driver, "android:id/datePicker"));
	}

	@Test
	public void TC_16_TietKiemTuDong_KiemTranChonNgayKetThucNhoHonNgayBatDau() {

		if (getForWardDay(0) == "1") {
			throw new SkipException("Skip TC 16");
		} else {

			log.info("TC_16_Step_01: Chon ngay ket thuc nho hon hien tai");
			autoSaving.clickToDynamicDateInDateTimePicker(driver, getBackWardDay(1));
			autoSaving.clickToDynamicButton(driver, "OK");

			log.info("TC_16_Step_02: Xac nhan hien thi ngay hien tai o o chon ngay ket thuc");
			verifyEquals(autoSaving.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayKetThuc"),
					getForwardDate(0));
		}

	}

	@Test
	public void TC_17_TietKiemTuDong_KiemTraChonNgayKetThucLonHonHienTai() {

		log.info("TC_17_Step_01: Chon ngay ket thuc lon hon hien tai");
		autoSaving.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayKetThuc");
		if (getForWardDay(1) == "1") {
			autoSaving.clickToDynamicRadioIndex(driver, "android:id/next");
		}
		autoSaving.clickToDynamicDateInDateTimePicker(driver, getForWardDay(1));
		autoSaving.clickToDynamicButton(driver, "OK");

		log.info("TC_17_Step_02: Xac nhan hien thi dung ngay ket thuc");
		verifyEquals(autoSaving.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayKetThuc"),
				getForwardDate(1));

	}

	@Test
	public void TC_18_TietKiemTuDong_KiemTraDinhDangNgayKetThuc() {

		log.info("TC_18_Step_01: Xac nhan hien thi dung dinh dang ngay ket thuc");
		endDate = autoSaving.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayKetThuc");
		verifyTrue(validateDateFormat(endDate, "dd/mm/yyyy"));

		log.info("TC_18_Step_02: Click button Back");
		autoSaving.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
	}

	@Test
	public void TC_19_TatToanTaiKhoanTietKiem_VND_1Thang_LaiNhapGoc() {

		log.info("TC_19_1_Click Tat toan tai khoan tiet kiem");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Tất toán tài khoản tiết kiệm");

		log.info("TC_19_2_Chon so tai khoan tiet kiem");
		savingOnline.clickToDynamicDropDownInSavingOnline("Tài khoản tiết kiệm");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, savingAccount);

		log.info("TC_19_3_Chon tai khoan dich");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, "Chọn tài khoản đích");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_19_4_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_19_5_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_19_6_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, "Tiếp tục");

		savingOnline.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		savingOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_19_7_Click nut Home ve man hinh chinh");
		savingOnline.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivHome");

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		service.stop();
	}

}
