package vnpay.vietcombank.auto_saving;

import java.io.IOException;

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

public class Auto_Saving_Validate_02 extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject home;
	private AutoSavingPageObject autoSaving;

	private String sourceAccountMoney, savingAccount, term, savingAccountMoney, startDate, endDate, exchangeRate;

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);

	}

	@Test
	public void TC_01_TietKiemTuDong_KiemTraHienThiMacDinhNutTiepTuc() {

		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_01_Step_01: Keo xuong va click vao phan 'Tiet kiem tu dong'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Tiết kiệm tự động");
		autoSaving = PageFactoryManager.getAutoSavingPageObject(driver);

		log.info("TC_01_Step_02: Kiem tra trang thai mac dich cua Button 'Tiep tuc'");
		verifyTrue(autoSaving.isDynamicButtonByIdEnable(driver, "com.VCB:id/btContinue"));

	}

	@Test
	public void TC_02_TietKiemTuDong_BoTrongSoTienChuyen() {

		log.info("TC_02_Step_01: Chon tai khoan nguon");
		autoSaving.clickToTextViewCombobox(driver, "com.VCB:id/tvContent");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_VND);

		log.info("TC_02_Step_02: Chon tai khoan tiet kiem");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, "Tài khoản tiết kiệm");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, autoSaving.getFirstOptionInList("com.VCB:id/tvContent1"));

		log.info("TC_02_Step_03: Chon ngay ket thuc");
		autoSaving.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayKetThuc");
		autoSaving.clickToDynamicDateInDateTimePicker(driver, getForWardDay(2));
		autoSaving.clickToDynamicButton(driver, "OK");

		log.info("TC_02_Step_04: Bo trong o so tien chuyen");
		autoSaving.inputIntoEditTextByID(driver, "", "com.VCB:id/edtContent2");

		log.info("TC_02_Step_05: An Tiep tuc");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_02_Step_06: Xac nhan hien thi thong bao chua nhap so tien");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), Auto_Saving_Data.VALIDATE.TRANSFER_MONEY_EMPTY_MESSAGE);
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

	}

	@Test
	public void TC_03_TietKiemTuDong_BoTrongNgayKetThuc() {

		log.info("TC_03_Step_01: Chon tai khoan tiet kiem");
		autoSaving.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/TaiKhoanTietKiem");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, autoSaving.getFirstOptionInList("com.VCB:id/tvContent1"));

		log.info("TC_03_Step_02: Nhap so tien chuyen, bo trong o Ngay ket thuc");
		autoSaving.inputIntoEditTextByID(driver, "2000000", "com.VCB:id/edtContent2");

		log.info("TC_03_Step_03: An Tiep tuc");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_02_Step_07: Xac nhan hien thi thong bao chua nhap ngay ket thuc");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), Auto_Saving_Data.VALIDATE.ENDDATE_EMPTY_MESSAGE);
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
	}

	@Test
	public void TC_04_TietKiemTuDong_NhapSoTienHopLe() {

		log.info("TC_04_Step_01: Chon tai khoan nguon VND");
		autoSaving.clickToTextViewCombobox(driver, "com.VCB:id/tvContent");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_VND);

		log.info("TC_04_Step_02: Chon tai khoan tiet kiem");
		autoSaving.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/TaiKhoanTietKiem");
		savingAccount = autoSaving.getFirstOptionInList("com.VCB:id/tvContent1");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, savingAccount);

		log.info("TC_04_Step_03: Xac nhan Ky han va so du TK tiet kiem");
		term = autoSaving.getDynamicTextByLabel(driver, "Kỳ hạn");
		startDate = autoSaving.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayBatDau");
		savingAccountMoney = autoSaving.getDynamicTextByLabel(driver, "Số dư hiện tại");

		log.info("TC_04_Step_04: Chon ngay ket thuc");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, "Ngày kết thúc");
		autoSaving.clickToDynamicDateInDateTimePicker(driver, getForWardDay(3));
		autoSaving.clickToDynamicButton(driver, "OK");
		endDate = autoSaving.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayKetThuc");

		log.info("TC_04_Step_05: Nhap so tien chuyen");
		autoSaving.inputToDynamicInputBox(driver, Auto_Saving_Data.TEXT.INPUT_VND, "Số tiền chuyển");

		log.info("TC_04_Step_06: An Tiep tuc");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_04_Step_07: Hien thi man hinh xac nhan thong tin");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Xác nhận thông tin");
	}

	@Test
	public void TC_05_TietKiemTuDong_ManHinhXacNhanThongTin_TKNguon_VND() {

		log.info("TC_05_Step_01: Hien thi title xac nhan thong tin");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Xác nhận thông tin");

		log.info("TC_05_Step_02: Hien thi Icon back");
		verifyTrue(autoSaving.isDynamicImageHomeDisplay(driver, "com.VCB:id/ivTitleLeft"));

		log.info("TC_05_Step_03: Hien thi label thong tin xac nhan");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleHead"), "Quý khách vui lòng kiểm tra thông tin giao dịch đã khởi tạo");

		log.info("TC_05_Step_04: Hien thi tai khoan nguon");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, "Tài khoản nguồn"), Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_VND);

		log.info("TC_05_Step_05: Hien thi tai khoan tiet kiem");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, "Tài khoản tiết kiệm"), savingAccount);

		log.info("TC_05_Step_06: Hien thi ky han gui");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, "Kỳ hạn gửi"), term);

		log.info("TC_05_Step_07: Hien thi ngay den han");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, "Ngày đến hạn"), endDate);

		log.info("TC_05_Step_08: Hien thi So tien hien tai");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, "Số tiền hiện tại"), savingAccountMoney);

		log.info("TC_05_Step_09: Hien thi Chu ky chuyen");
		verifyTrue(autoSaving.isDynamicMessageAndLabelTextDisplayed(driver, "Chu kỳ chuyển"));

		log.info("TC_05_Step_10: Hien thi ngay bat dau");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, "Ngày bắt đầu"), startDate);

		log.info("TC_05_Step_11: Hien thi ngay ket thuc");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, "Ngày kết thúc"), endDate);

		log.info("TC_05_Step_12: Hien thi so tien chuyen");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, "Số tiền chuyển"), addCommasToLong(Auto_Saving_Data.TEXT.INPUT_VND) + " VND");

		log.info("TC_05_Step_13: Hien thi so tien phi");
		verifyTrue(autoSaving.isDynamicTextNumberCustomerDisplayed(driver, "Số tiền phí"));

		log.info("TC_05_Step_14: Hien thi text va dropdown chon phuong thuc xac thuc");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), "Chọn phương thức xác thực");
		verifyTrue(autoSaving.isDynamicTextDetailByID(driver, "com.VCB:id/tvptxt"));

		log.info("TC_05_Step_15: Hien thi button Tiep Tuc");
		verifyTrue(autoSaving.isDynamicButtonByIdDisplayed(driver, "com.VCB:id/btContinue"));
		autoSaving.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_05_Step_16: Kiem tra trang thai mac dich cua Button 'Tiep tuc'");
		verifyTrue(autoSaving.isDynamicButtonByIdEnable(driver, "com.VCB:id/btContinue"));
	}

	@Test
	public void TC_06_TietKiemTuDong_ManHinhXacNhanThongTin_TKNguon_USD() {

		log.info("TC_06_Step_01: Chon tai khoan nguon USD");
		autoSaving.clickToTextViewCombobox(driver, "com.VCB:id/tvContent");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_USD);

		log.info("TC_06_Step_02: Chon tai khoan tiet kiem");
		autoSaving.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/TaiKhoanTietKiem");
		savingAccount = autoSaving.getFirstOptionInList("com.VCB:id/tvContent1");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, savingAccount);

		log.info("TC_06_Step_03: Xac nhan Ky han va so du TK tiet kiem");
		term = autoSaving.getDynamicTextByLabel(driver, "Kỳ hạn");
		startDate = autoSaving.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayBatDau");
		savingAccountMoney = autoSaving.getDynamicTextByLabel(driver, "Số dư hiện tại");
		exchangeRate = getCurrentcyMoney(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTiGia"));

		log.info("TC_06_Step_04: Chon ngay ket thuc");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, "Ngày kết thúc");
		autoSaving.clickToDynamicDateInDateTimePicker(driver, getForWardDay(3));
		autoSaving.clickToDynamicButton(driver, "OK");
		endDate = autoSaving.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayKetThuc");

		log.info("TC_06_Step_05: Nhap so tien chuyen");
		autoSaving.inputToDynamicInputBox(driver, Auto_Saving_Data.TEXT.INPUT_USD, "Số tiền chuyển");

		log.info("TC_06_Step_06: An Tiep tuc");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_06_Step_07: Hien thi man hinh xac nhan thong tin");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Xác nhận thông tin");

		log.info("TC_06_Step_08: Hien thi Icon back");
		verifyTrue(autoSaving.isDynamicImageHomeDisplay(driver, "com.VCB:id/ivTitleLeft"));

		log.info("TC_06_Step_09: Hien thi thông tin xac nhan");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleHead"), "Quý khách vui lòng kiểm tra thông tin giao dịch đã khởi tạo");

		log.info("TC_06_Step_10: Hien thi tai khoan nguon");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, "Tài khoản nguồn"), Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_USD);

		log.info("TC_06_Step_11: Hien thi tai khoan tiet kiem");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, "Tài khoản tiết kiệm"), savingAccount);

		log.info("TC_06_Step_12: Hien thi ky han gui");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, "Kỳ hạn gửi"), term);

		log.info("TC_06_Step_13: Hien thi ngay den han");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, "Ngày đến hạn"), endDate);

		log.info("TC_06_Step_14: Hien thi So tien hien tai");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, "Số tiền hiện tại"), savingAccountMoney);

		log.info("TC_06_Step_15: Hien thi Chu ky chuyen");
		verifyTrue(autoSaving.isDynamicMessageAndLabelTextDisplayed(driver, "Chu kỳ chuyển"));

		log.info("TC_06_Step_16: Hien thi ngay bat dau");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, "Ngày bắt đầu"), startDate);

		log.info("TC_06_Step_17: Hien thi ngay ket thuc");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, "Ngày kết thúc"), endDate);

		log.info("TC_06_Step_18: Hien thi so tien chuyen");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvSoTien"), addCommasToDouble(Auto_Saving_Data.TEXT.INPUT_USD) + " USD");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvQuyDoi"), convertEURO_USDToVNeseMoney(Auto_Saving_Data.TEXT.INPUT_USD, exchangeRate));

		log.info("TC_06_Step_19: Hien thi so tien phi");
		verifyTrue(autoSaving.isDynamicTextNumberCustomerDisplayed(driver, "Số tiền phí"));

		log.info("TC_06_Step_20: Hien thi text va dropdown chon phuong thuc xac thuc");
		autoSaving.scrollDownToText(driver, "Chọn phương thức xác thực");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), "Chọn phương thức xác thực");
		verifyTrue(autoSaving.isDynamicTextDetailByID(driver, "com.VCB:id/tvptxt"));

		log.info("TC_06_Step_21: Hien thi button Tiep Tuc");
		verifyTrue(autoSaving.isDynamicButtonByIdDisplayed(driver, "com.VCB:id/btContinue"));

		log.info("TC_06_Step_22: Kiem tra trang thai mac dich cua Button 'Tiep tuc'");
		verifyTrue(autoSaving.isDynamicButtonByIdEnable(driver, "com.VCB:id/btContinue"));
	}

	@Test
	public void TC_07_TietKiemTuDong_NhanIconBack() {

		log.info("TC_07_Step_01: Nhan Icon back");
		autoSaving.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_07_Step_02: Xac nhan quay ve man hinh tiet kiem tu dong");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), Auto_Saving_Data.VALIDATE.AUTO_SAVING_TITLE);
	}

	@Parameters({ "phone" })
	@Test
	public void TC_08_TietKiemTuDong_ManHinhXacThucBangOTP(String phone) {

		log.info("TC_08_Step_01: Chon tai khoan nguon VND");
		autoSaving.clickToTextViewCombobox(driver, "com.VCB:id/tvContent");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_VND);

		log.info("TC_08_Step_02: Chon tai khoan tiet kiem");
		autoSaving.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/TaiKhoanTietKiem");
		savingAccount = autoSaving.getFirstOptionInList("com.VCB:id/tvContent1");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, savingAccount);

		log.info("TC_08_Step_03: Xac nhan Ky han va so du TK tiet kiem");
		term = autoSaving.getDynamicTextByLabel(driver, "Kỳ hạn");
		startDate = autoSaving.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayBatDau");
		savingAccountMoney = autoSaving.getDynamicTextByLabel(driver, "Số dư hiện tại");

		log.info("TC_08_Step_04: Chon ngay ket thuc");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, "Ngày kết thúc");
		autoSaving.clickToDynamicDateInDateTimePicker(driver, getForWardDay(3));
		autoSaving.clickToDynamicButton(driver, "OK");
		endDate = autoSaving.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayKetThuc");

		log.info("TC_08_Step_05: Nhap so tien chuyen");
		autoSaving.inputToDynamicInputBox(driver, Auto_Saving_Data.TEXT.INPUT_VND, "Số tiền chuyển");

		log.info("TC_08_Step_06: An Tiep tuc");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_08_Step_07: Hien thi man hinh xac nhan thong tin");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Xác nhận thông tin");

		log.info("TC_08_Step_08: Chon phương thuc xac thuc");
		autoSaving.clickToTextViewCombobox(driver, "com.VCB:id/tvptxt");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_08_Step_09: An Tiep tuc");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_08_Step_09: Xac nhan hien thi Title 'Xac thuc giao dich'");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/lblTitle"), Auto_Saving_Data.VALIDATE.OTP_METHOD_CONFIRM_TRANSACTION_TITLE);

		log.info("TC_08_Step_10: Xac nhan hien thi Icon 'Quay lai'");
		verifyTrue(autoSaving.isDynamicLinearlayoutByIdDisplayed(driver, "com.VCB:id/layout_back"));
		verifyEquals(autoSaving.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/layout_back"), "Quay lại");

		log.info("TC_08_Step_11: Xac nhan hien thi dung message thong bao");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/lblMessage"), Auto_Saving_Data.VALIDATE.OTP_METHOD_CONFIRM_TRANSACTION_MESSAGE + " " + phone.substring(0, 3) + "*****" + phone.substring(8, 10));

		log.info("TC_08_Step_12: Xac nhan hien thi textbox 'Nhap OTP'");
		verifyTrue(autoSaving.isDynamicTextDetailByID(driver, "com.VCB:id/otp"));

		log.info("TC_08_Step_13: Xac nhan hien thi button 'Tiep tuc'");
		verifyTrue(autoSaving.isDynamicButtonByIdDisplayed(driver, "com.VCB:id/btContinue"));
	}

	@Test
	public void TC_09_TietKiemTuDong_KiemTraBatBuocNhapOTP() {

		log.info("TC_09_Step_01: Khong nhap OTP va click button 'Tiep tuc'");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_09_Step_02: Xac nhan hien thi thong bao nhap ma OTP");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), Auto_Saving_Data.VALIDATE.OTP_METHOD_EMPTY_MESSAGE);
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

	}

	@Test
	public void TC_10_TietKiemTuDong_KiemTraNhapOTPKhongDuKiTu() {

		log.info("TC_10_Step_01: Nhap it hon 6 ki tu o o nhap OTP");
		autoSaving.inputToDynamicOtp(driver, "23456", "Tiếp tục");

		log.info("TC_10_Step_02: An tiep nut 'Tiep tuc'");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_10_Step_03: Xac nhan thong bao loi hien thi");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), Auto_Saving_Data.VALIDATE.OTP_METHOD_LESS_THAN_SIX_CHARACTER_MESSAGE);
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
	}

	@Test
	public void TC_11_TietKiemTuDong_NhapSaiMaOTP() {

		log.info("TC_11_Step_01: Nhap du 6 ki tu o o nhap OTP");
		autoSaving.inputToDynamicOtp(driver, "234567", "Tiếp tục");

		log.info("TC_11_Step_02: An tiep nut 'Tiep tuc'");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_11_Step_03: Xac nhan thong bao loi hien thi");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), Auto_Saving_Data.VALIDATE.OTP_METHOD_ERROR_MESSAGE);
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
	}

	@Parameters({ "otp" })
	@Test
	public void TC_12_TietKiemTuDong_NhapDungMaOTP(String otp) {

		log.info("TC_12_Step_01: Nhap du ki tu vao o nhap OTP");
		autoSaving.inputToDynamicOtp(driver, otp, "Tiếp tục");
		;

		log.info("TC_12_Step_02: An tiep button 'Tiep tuc'");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_12_Step_03: Hien thi man hinh thong bao giao dich thanh cong");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), "GIAO DỊCH THÀNH CÔNG");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");
	}

	@Parameters({ "pass" })
	@Test
	public void TC_13_TietKiemTuDong_ManHinhXacThucBangMK(String pass) {

		log.info("TC_13_Step_01: Chon tai khoan nguon");
		autoSaving.clickToTextViewCombobox(driver, "com.VCB:id/tvContent");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_USD);

		log.info("TC_13_Step_02: Chon tai khoan tiet kiem");
		autoSaving.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/TaiKhoanTietKiem");
		savingAccount = autoSaving.getFirstOptionInList("com.VCB:id/tvContent1");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, savingAccount);

		log.info("TC_13_Step_03: Xac nhan Ky han va so du TK tiet kiem");
		term = autoSaving.getDynamicTextByLabel(driver, "Kỳ hạn");
		startDate = autoSaving.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayBatDau");
		savingAccountMoney = autoSaving.getDynamicTextByLabel(driver, "Số dư hiện tại");
		exchangeRate = getCurrentcyMoney(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTiGia"));

		log.info("TC_13_Step_04: Chon ngay ket thuc");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, "Ngày kết thúc");
		autoSaving.clickToDynamicDateInDateTimePicker(driver, getForWardDay(3));
		autoSaving.clickToDynamicButton(driver, "OK");
		endDate = autoSaving.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayKetThuc");

		log.info("TC_13_Step_05: Nhap so tien chuyen");
		autoSaving.inputToDynamicInputBox(driver, Auto_Saving_Data.TEXT.INPUT_USD, "Số tiền chuyển");

		log.info("TC_13_Step_06: An Tiep tuc");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_13_Step_07: Hien thi man hinh xac nhan thong tin");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Xác nhận thông tin");

		log.info("TC_13_Step_08: Chon phương thuc xac thuc");
		autoSaving.clickToTextViewCombobox(driver, "com.VCB:id/tvptxt");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");

		log.info("TC_13_Step_09: An Tiep tuc");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_13_Step_10: Xac nhan hien thi Title 'Xac thuc giao dich'");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/lblTitle"), Auto_Saving_Data.VALIDATE.OTP_METHOD_CONFIRM_TRANSACTION_TITLE);

		log.info("TC_13_Step_11: Xac nhan hien thi Icon 'Quay lai'");
		verifyTrue(autoSaving.isDynamicLinearlayoutByIdDisplayed(driver, "com.VCB:id/layout_back"));
		verifyEquals(autoSaving.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/layout_back"), "Quay lại");

		log.info("TC_13_Step_12: Xac nhan hien thi dung message thong bao");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/lblMessage"), Auto_Saving_Data.VALIDATE.PASSWORD_METHOD_CONFIRM_TRANSACTION_MESSAGE);

		log.info("TC_13_Step_13: Xac nhan hien thi textbox 'Nhap mat khau'");
		verifyTrue(autoSaving.isDynamicEditTexByIdDisplayed(driver, "com.VCB:id/pin"));

		log.info("TC_13_Step_14: Xac nhan hien thi button 'Tiep tuc'");
		verifyTrue(autoSaving.isDynamicButtonByIdDisplayed(driver, "com.VCB:id/btContinue"));
	}

	@Test
	public void TC_14_TietKiemTuDong_KiemTraAnNutBack() {

		log.info("TC_14_Step_01: An Nut Back");
		autoSaving.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layout_back");

		log.info("TC_14_Step_02: Xac nhan hien thi man hinh xac nhan thong tin");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Xác nhận thông tin");

	}

	@Test
	public void TC_15_TietKiemTuDong_KiemTraBatBuocNhapMK() {

		log.info("TC_15_Step_01: Chon phương thuc xac thuc");
		autoSaving.clickToTextViewCombobox(driver, "com.VCB:id/tvptxt");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");

		log.info("TC_15_Step_02: An Tiep tuc");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_15_Step_03: Khong nhap MK va click button 'Tiep tuc'");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC16_Step_04: Xac nhan hien thi thong bao nhap ma MK");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), Auto_Saving_Data.VALIDATE.PASSWORD_METHOD_EMPTY_MESSAGE);
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
	}

	@Test
	public void TC_16_TietKiemTuDong_NhapSaiMatKhau() {

		log.info("TC_16_Step_01: Nhap ki tu vao o nhap mat khau");
		autoSaving.inputIntoEditTextByID(driver, "ASbc!@#$%^&*hdj364726kd", "com.VCB:id/pin");

		log.info("TC_16_Step_02: Xac nhan nhap duoc toi da 20 Ki tu");
		verifyEquals(autoSaving.getTextInDynamicPasswordInput(driver, "com.VCB:id/pin").length(), 20);

		log.info("TC_16_Step_03: Nhap sai mat khau");
		autoSaving.inputIntoEditTextByID(driver, "sadaasd", "com.VCB:id/pin");

		log.info("TC_16_Step_04: Click button 'Tiep tuc'");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_16_Step_05: Xac nhan hien thi thong bao nhap ma MK");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), Auto_Saving_Data.VALIDATE.PASSWORD_METHOD_ERROR_MESSAGE);
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
	}

	@Parameters({ "pass" })
	@Test
	public void TC_17_TietKiemTuDong_GiaoDichThanhCong(String pass) {

		log.info("TC_17_Step_01: Nhap dung mat khau");
		autoSaving.inputIntoEditTextByID(driver, pass, "com.VCB:id/pin");

		log.info("TC_17_Step_02: Click button 'Tiep tuc'");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_17_Step_03: Hien thi man hinh thong bao giao dich thanh cong");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), "GIAO DỊCH THÀNH CÔNG");

		log.info("TC_17_Step_04: Hien thi Icon Home");
		verifyTrue(autoSaving.isDynamicImageHomeDisplay(driver, "com.VCB:id/ivHome"));

		log.info("TC_17_Step_05: Hien thi icon Thanh cong");
		verifyTrue(autoSaving.isDynamicImageByFollowingImageIdDisplayed(driver, "com.VCB:id/ivHome"));

		log.info("TC_17_Step_06: Hien thi so tien giao dich");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount"), convertEURO_USDToVNeseMoney(Auto_Saving_Data.TEXT.INPUT_USD, exchangeRate));

		log.info("TC_17_Step_07: Hien thi thoi gian giao dich");
		verifyTrue(autoSaving.isTextDisplayedInListTextElements(driver, getForWardDay(0), "com.VCB:id/tvTime"));

		log.info("TC_17_Step_08: Hien thi tai khoan tiet kiem");
		verifyTrue(autoSaving.isDynamicMessageAndLabelTextDisplayed(driver, "Tài khoản tiết kiệm"));
		verifyTrue(autoSaving.isDynamicTextNumberCustomerDisplayed(driver, "Tài khoản tiết kiệm"));

		log.info("TC_17_Step_09: Hien thi man hinh thong bao giao dich thanh cong");
		verifyTrue(autoSaving.isDynamicMessageAndLabelTextDisplayed(driver, "Mã giao dịch"));
		verifyTrue(autoSaving.isDynamicTextNumberCustomerDisplayed(driver, "Mã giao dịch"));

		log.info("TC_17_Step_10: Hien thi Icon Chia se");
		verifyTrue(autoSaving.isDynamicTextDetailByID(driver, "com.VCB:id/tvShare"));

		log.info("TC_17_Step_11: Hien thi Icon Luu anh");
		verifyTrue(autoSaving.isDynamicTextDetailByID(driver, "com.VCB:id/tvSavePhoto"));

		log.info("TC_17_Step_12: Hien thi Button Thuc hien giao dich moi");
		verifyTrue(autoSaving.isDynamicButtonByIdDisplayed(driver, "com.VCB:id/btContinue"));

		log.info("TC_17_Step_13: An nut Home");
		autoSaving.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivHome");

		log.info("TC_17_Step_14: Xac nhan qua ve man hinh chinh");
		verifyTrue(autoSaving.isDynamicImageHomeDisplay(driver, "com.VCB:id/menu_1"));
	}

	@Parameters({ "pass" })
	@Test
	public void TC_18_TietKiemTuDong_KiemTraSoDuTKNguon_SauKhiGiaoDichXong(String pass) {

		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_18_Step_01: Keo xuong va click vao phan 'Tiet kiem tu dong'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Tiết kiệm tự động");
		autoSaving = PageFactoryManager.getAutoSavingPageObject(driver);

		log.info("TC_18_Step_02: Chon tai khoan nguon");
		autoSaving.clickToTextViewCombobox(driver, "com.VCB:id/tvContent");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_USD);
		sourceAccountMoney = autoSaving.getMoneyByAccount(driver, "Số dư khả dụng");

		log.info("TC_18_Step_03: Chon tai khoan tiet kiem");
		autoSaving.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/TaiKhoanTietKiem");
		savingAccount = autoSaving.getFirstOptionInList("com.VCB:id/tvContent1");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, savingAccount);

		log.info("TC_18_Step_04: Xac nhan Ky han va so du TK tiet kiem");
		term = autoSaving.getDynamicTextByLabel(driver, "Kỳ hạn");
		startDate = autoSaving.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayBatDau");
		savingAccountMoney = autoSaving.getDynamicTextByLabel(driver, "Số dư hiện tại");
		exchangeRate = getCurrentcyMoney(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTiGia"));

		log.info("TC_18_Step_05: Chon ngay ket thuc");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, "Ngày kết thúc");
		autoSaving.clickToDynamicDateInDateTimePicker(driver, getForWardDay(3));
		autoSaving.clickToDynamicButton(driver, "OK");
		endDate = autoSaving.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayKetThuc");

		log.info("TC_18_Step_06: Nhap so tien chuyen");
		autoSaving.inputToDynamicInputBox(driver, Auto_Saving_Data.TEXT.INPUT_USD, "Số tiền chuyển");

		log.info("TC_18_Step_07: An Tiep tuc");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_18_Step_08: Hien thi man hinh xac nhan thong tin");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Xác nhận thông tin");

		log.info("TC_18_Step_09: Chon phương thuc xac thuc");
		autoSaving.clickToTextViewCombobox(driver, "com.VCB:id/tvptxt");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");

		log.info("TC_18_Step_10: An Tiep tuc");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_18_Step_11: Xac nhan hien thi Title 'Xac thuc giao dich'");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/lblTitle"), Auto_Saving_Data.VALIDATE.OTP_METHOD_CONFIRM_TRANSACTION_TITLE);

		log.info("TC_18_Step_12: Nhap dung mat khau");
		autoSaving.inputIntoEditTextByID(driver, pass, "com.VCB:id/pin");

		log.info("TC_18_Step_13: Click button 'Tiep tuc'");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_18_Step_14: Hien thi man hinh thong bao giao dich thanh cong");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), "GIAO DỊCH THÀNH CÔNG");

		log.info("TC_18_Step_15: Hien thi Icon Home");
		verifyTrue(autoSaving.isDynamicImageHomeDisplay(driver, "com.VCB:id/ivHome"));

		log.info("TC_18_Step_16: Hien thi icon Thanh cong");
		verifyTrue(autoSaving.isDynamicImageByFollowingImageIdDisplayed(driver, "com.VCB:id/ivHome"));

		log.info("TC_18_Step_17: Hien thi so tien giao dich");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount"), convertEURO_USDToVNeseMoney(Auto_Saving_Data.TEXT.INPUT_USD, exchangeRate));

		log.info("TC_18_Step_18: Hien thi thoi gian giao dich");
		verifyTrue(autoSaving.isTextDisplayedInListTextElements(driver, getForWardDay(0), "com.VCB:id/tvTime"));

		log.info("TC_18_Step_19: An Button Thuc hien giao dich moi");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_18_Step_20: Xac nhan quay ve man hinh tiet kiem tu dong");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), Auto_Saving_Data.VALIDATE.AUTO_SAVING_TITLE);

		log.info("TC_18_Step_21: Xac nhan so du TK nguon");
		autoSaving.clickToTextViewCombobox(driver, "com.VCB:id/tvContent");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_USD);
		verifyEquals(autoSaving.getMoneyByAccount(driver, "Số dư khả dụng"), sourceAccountMoney);

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		service.stop();
		
	}

}
