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
import pageObjects.SavingOnlinePageObject;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.Auto_Saving_Data;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.SavingOnline_Data;

public class Auto_Saving_Validate_03 extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject home;
	private AutoSavingPageObject autoSaving;
	private SavingOnlinePageObject savingOnline;

	private String sourceAccountMoney, savingAccount, term, savingAccountMoney, startDate, endDate, exchangeRate;
	private long transferFee;

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);

	}

	@Parameters({ "otp" })
	@Test
	public void TC_00_MoTaiKhoanTietKiem_TaiKhoanNguon_VND(String otp) {

		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_00_1_Click Mo tai khoan tiet kiem");
		home.scrollDownToText(driver, "Tín dụng");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Mở tài khoản tiết kiệm");
		savingOnline = PageFactoryManager.getSavingOnlinePageObject(driver);

		log.info("TC_00_2_Chon so tai khoan");
		savingOnline.clickToDynamicDropDown(driver, "Số tài khoản");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_00_3_Chon ky han gui");
		savingOnline.clickToDynamicDropDownListTextViewByHeader(driver, "Thông tin giao dịch", "1");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Data.TEXT.TERM);

		log.info("TC_00_4_Nhap so tien gui");
		savingOnline.inputToDynamicInputBoxByHeader(driver, Auto_Saving_Data.TEXT.INPUT_VND, "Thông tin giao dịch", "2");

		log.info("TC_00_5_Chon hinh thuc chuyen tien");
		savingOnline.clickToDynamicDropDownListTextViewByHeader(driver, "Thông tin giao dịch", "3");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Data.TEXT.FORM_OF_PAYMENT);

		log.info("TC_00_6_Chon dong y tuan thu cam ket");
		savingOnline.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");

		log.info("TC_00_7_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_00_8_Kiem tra man hinh xac nhan thong tin");

		log.info("TC_00_8_1_Kiem tra tai khoan nguon");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_VND);

		log.info("TC_00_8_2_Kiem tra ky han gui");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Kỳ hạn gửi"), Auto_Saving_Data.TEXT.TERM);

		log.info("TC_00_8_3_Kiem tra lai suat");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Lãi suất"), "4.5%/Năm");

		log.info("TC_00_8_4_Kiem tra so tien gui");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Số tiền gửi"), addCommasToLong(Auto_Saving_Data.TEXT.INPUT_VND) + " VND");

		log.info("TC_00_8_5_Kiem tra hinh thuc tra lai");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Hình thức trả lãi"), Auto_Saving_Data.TEXT.FORM_OF_PAYMENT);

		log.info("TC_00_9_Chon phuong thuc xac thuc");
		savingOnline.scrollDownToText(driver, "Chọn phương thức xác thực");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		transferFee = convertAvailableBalanceCurrentcyOrFeeToLong(savingOnline.getDynamicTextInTransactionDetail(driver, "SMS OTP"));
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_00_10_Kiem tra so tien phi");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), addCommasToLong(transferFee + "") + " VND");

		log.info("TC_00_11_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, "Tiếp tục");

		savingOnline.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		savingOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_00_12_Kiem tra man hinh Giao dich thanh cong");
		log.info("TC_00_12_1_Kiem tra Giao dich thanh cong");
		verifyTrue(savingOnline.isDynamicMessageAndLabelTextDisplayed(driver, SavingOnline_Data.SUCCESS_TRANSACTION));

		log.info("TC_00_12_6_Lay ma giao dich");
		savingAccount = savingOnline.getDynamicTextInTransactionDetail(driver, "Số tài khoản tiết kiệm");

		log.info("TC_00_13_Click nut Home ve man hinh chinh");
		savingOnline.clickToDynamicImageViewByID(driver, "com.VCB:id/ivHome");
	}

	@Parameters({ "pass" })
	@Test
	public void TC_01_TietKiemTuDong_TaiKhoanNguon(String pass) {

		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_01_Step_01: Keo xuong va click vao phan 'Tiet kiem tu dong'");
		home.scrollDownToText(driver, "Tín dụng");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Tiết kiệm tự động");
		autoSaving = PageFactoryManager.getAutoSavingPageObject(driver);

		log.info("TC_01_Step_02: Chon tai khoan nguon VND");
		autoSaving.clickToTextID(driver, "com.VCB:id/tvContent");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);
		sourceAccountMoney = autoSaving.getMoneyByAccount(driver, "Số dư khả dụng");

		log.info("TC_01_Step_03: Chon tai khoan tiet kiem");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, "Tài khoản tiết kiệm");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, savingAccount);

		log.info("TC_01_Step_04: Xac nhan Ky han va so du TK tiet kiem");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, "Kỳ hạn"), capitalizeString(Auto_Saving_Data.TEXT.TERM));
		startDate = autoSaving.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayBatDau");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, "Số dư hiện tại"), addCommasToLong(Auto_Saving_Data.TEXT.INPUT_VND) + " VND");

		log.info("TC_01_Step_05: Chon ngay ket thuc");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, "Ngày kết thúc");
		autoSaving.clickToDynamicDateInDateTimePicker(driver, getForWardDay(3));
		autoSaving.clickToDynamicButton(driver, "OK");
		endDate = autoSaving.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayKetThuc");

		log.info("TC_01_Step_06: Nhap so tien chuyen");
		autoSaving.inputToDynamicInputBox(driver, Auto_Saving_Data.TEXT.INPUT_VND, "Số tiền chuyển");

		log.info("TC_01_Step_07: An Tiep tuc");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_08: Hien thi man hinh xac nhan thong tin");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Xác nhận thông tin");

		log.info("TC_01_Step_09: Chon phương thuc xac thuc");
		autoSaving.clickToTextID(driver, "com.VCB:id/tvptxt");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");

		log.info("TC_01_Step_10: An nut 'Tiep tuc'");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_11: Nhap ki tu vao o nhap mat khau");
		autoSaving.inputIntoEditTextByID(driver, pass, "com.VCB:id/pin");

		log.info("TC_01_Step_12: An tiep button 'Tiep tuc'");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_13: Hien thi man hinh thong bao giao dich thanh cong");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), "GIAO DỊCH THÀNH CÔNG");

		log.info("TC_01_Step_14: Click nut Home ve man hinh chinh");
		savingOnline.clickToDynamicImageViewByID(driver, "com.VCB:id/ivHome");
	}

	@Test
	public void TC_02_HuyTietKiemTuDong_KiemTraManHinhHienThi() {

		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_02_Step_01: Keo xuong va click vao phan 'Huy Tiet kiem tu dong'");
		home.scrollDownToText(driver, "Tín dụng");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Huỷ tiết kiệm tự động");
		autoSaving = PageFactoryManager.getAutoSavingPageObject(driver);

		log.info("TC_02_Step_02: Xac nhan hien thi Title 'Huy tiet kiem tu dong'");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Hủy tiết kiệm tự động");

		log.info("TC_02_Step_03: Xac nhan hien thi Icon 'Back'");
		verifyTrue(autoSaving.isDynamicImageHomeDisplay(driver, "com.VCB:id/ivTitleLeft"));

		log.info("TC_02_Step_04: Xac nhan hien thi comboBox Tai khoan nguon");
		verifyTrue(autoSaving.isDynamicTextViewByLinearLayoutIdDisplayed(driver, "com.VCB:id/thongTinTaiKhoanThanhToan"));

		log.info("TC_02_Step_05: Xac nhan hien thi comboBox Tai khoan tiet kiem");
		verifyTrue(autoSaving.isDynamicTextViewByLinearLayoutIdDisplayed(driver, "com.VCB:id/thongTinTaiKhoanTietKiem"));

		log.info("TC_02_Step_06: Xac nhan button Tim Kiem hien thi va enable");
		verifyTrue(autoSaving.isDynamicButtonByIdEnable(driver, "com.VCB:id/btSearch"));
		verifyEquals(autoSaving.getDynamicTextButtonById(driver, "com.VCB:id/btSearch"), "Tìm kiếm");

		log.info("TC_02_Step_07: Click button Back");
		autoSaving.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_02_Step_08: Xac nhan Hien thi man hinh Home");
		verifyTrue(autoSaving.isDynamicImageHomeDisplay(driver, "com.VCB:id/menu_1"));

	}

	@Test
	public void TC_03_HuyTietKiemTuDong_KiemTraHienThiMacDinhTK() {

		log.info("TC_03_Step_01: Keo xuong va click vao phan 'Huy Tiet kiem tu dong'");
		home.scrollDownToText(driver, "Tín dụng");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Huỷ tiết kiệm tự động");
		autoSaving = PageFactoryManager.getAutoSavingPageObject(driver);

		log.info("TC_03_Step_02: Xac nhan hien thi o TK nguon la 'Tat ca'");
		verifyEquals(autoSaving.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/thongTinTaiKhoanThanhToan"), "Tất cả");

		log.info("TC_03_Step_03: Xac nhan hien thi o TK tiet kiem la 'Tat ca'");
		verifyEquals(autoSaving.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/thongTinTaiKhoanTietKiem"), "Tất cả");

	}

	@Test
	public void TC_04_HuyTietKiemTuDong_KiemTraMacDinhMoiThongTinGiaoDich() {

		log.info("TC_04_Step_01: Click chon tai khoan nguon");
		autoSaving.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/thongTinTaiKhoanThanhToan");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_04_Step_02: Click chon tai khoan tiet kiem");
		autoSaving.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/thongTinTaiKhoanTietKiem");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, savingAccount);

		log.info("TC_04_Step_03: Click button 'Tim kiem'");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");

		log.info("TC_04_Step_04: Xac nhan hien thi Tai khoan nguon");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTaiKhoanNguonDes"), "Tài khoản nguồn");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTaiKhoanNguon"), Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_04_Step_05: Xac nhan hien thi button Huy");
		verifyTrue(autoSaving.isDynamicTextDetailByID(driver, "com.VCB:id/tvHuy"));
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvHuy"), "Hủy");

		log.info("TC_04_Step_06: Xac nhan hien thi Tai khoan tiet kiem");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTaiKhoanTietKiemDes"), "Tài khoản tiết kiệm");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTaiKhoanTietKiem"), savingAccount);

		log.info("TC_04_Step_07: Xac nhan hien thi So tien chuyen");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvSoTienChuyenDes"), "Số tiền chuyển");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvSoTienChuyen"), addCommasToLong(Auto_Saving_Data.TEXT.INPUT_VND) + " VND");

		log.info("TC_04_Step_08: Xac nhan hien thi Chu ky chuyen");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvThoiGianDes"), "Chu kỳ chuyển");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvThoiGian"), startDate + " - " + endDate);
	}

	@Test
	public void TC_05_HuyTietKiemTuDong_AnNutHuy() {

		log.info("TC_05_Step_01: An nut Huy");
		autoSaving.clickToTextID(driver, "com.VCB:id/tvHuy");

		log.info("TC_05_Step_02: Xac nhan hien thi pop-up xac nhan");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), Auto_Saving_Data.TEXT.AUTO_SAVING_CANCEL_MESSAGE);

		log.info("TC_05_Step_03: Xac nhan hien thi button Dong y");
		verifyTrue(autoSaving.isDynamicButtonByIdDisplayed(driver, "com.VCB:id/btCancel"));

		log.info("TC_05_Step_04: Xac nhan hien thi button Huy");
		verifyTrue(autoSaving.isDynamicButtonByIdDisplayed(driver, "com.VCB:id/btOK"));
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
	}

	@Test
	public void TC_06_HuyTietKiemTuDong_AnXemChiTietThongTinGiaoDich() {

		log.info("TC_06_Step_01: Click xem chi tiet thong tin giao dich");
		autoSaving.clickToDynamicGroupviewByListviewId(driver, "0");

		log.info("TC_06_Step_02: Xac nhan hien thi chi tiet giao dich");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Chi tiết giao dịch");
	}

	@Test
	public void TC_07_HuyTietKiemTuDong_KiemTraHienThiChiTietGiaoDich() {

		log.info("TC_07_Step_01: Xac nhan hien thi Title 'Chi tiet giao dich'");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Chi tiết giao dịch");

		log.info("TC_07_Step_02: Hien thi tai khoan nguon");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, "Tài khoản nguồn"), Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_07_Step_03: Hien thi tai khoan tiet kiem");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, "Tài khoản tiết kiệm"), savingAccount);

		log.info("TC_07_Step_04: Hien thi So tien chuyen");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, "Số tiền chuyển"), addCommasToLong(Auto_Saving_Data.TEXT.INPUT_VND) + " VND");

		log.info("TC_07_Step_05: Hien thi Chu ky chuyen");
		verifyTrue(autoSaving.isDynamicMessageAndLabelTextDisplayed(driver, "Chu kỳ chuyển"));

		log.info("TC_07_Step_06: Hien thi ngay bat dau");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, "Ngày bắt đầu"), startDate);

		log.info("TC_07_Step_07: Hien thi ngay ket thuc");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, "Ngày kết thúc"), endDate);

		log.info("TC_07_Step_08: Xac nhan button Huy tiet kiem tu dong hien thi va enable");
		verifyTrue(autoSaving.isDynamicButtonByIdEnable(driver, "com.VCB:id/btPrinter"));

		log.info("TC_07_Step_09: Click button Back");
		autoSaving.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_07_Step_08: Xac nhan Hien thi man hinh Huy tiet kiem tu dong");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Hủy tiết kiệm tự động");
	}

	@Test
	public void TC_08_HuyTietKiemTuDong_KiemTraNhanNutHuyTuChiTietGiaoDich() {

		log.info("TC_08_Step_01: Click xem chi tiet thong tin giao dich");
		autoSaving.clickToDynamicGroupviewByListviewId(driver, "0");

		log.info("TC_08_Step_02: Click button 'Huy tiet kiem tu dong'");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btPrinter");

		log.info("TC_08_Step_03: Xac nhan hien thi pop-up xac nhan");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), Auto_Saving_Data.TEXT.AUTO_SAVING_CANCEL_MESSAGE);

		log.info("TC_08_Step_04: Xac nhan hien thi button Dong y");
		verifyTrue(autoSaving.isDynamicButtonByIdDisplayed(driver, "com.VCB:id/btCancel"));

		log.info("TC_08_Step_05: Xac nhan hien thi button Huy");
		verifyTrue(autoSaving.isDynamicButtonByIdDisplayed(driver, "com.VCB:id/btOK"));
	}

	@Test
	public void TC_09_HuyTietKiemTuDong_HuyTietKiemTuDongTuChiTietGiaoDich() {

		log.info("TC_09_Step_01: An nut Huy");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

		log.info("TC_09_Step_02: Xac nhan quay lai man hinh chi tiet giao dich");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Chi tiết giao dịch");

		log.info("TC_09_Step_03: Click button 'Huy tiet kiem tu dong'");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btPrinter");

		log.info("TC_09_Step_04: An nut Dong y");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btCancel");

		log.info("TC_09_Step_05: Xac nhan Huy GD thanh cong va hien thi thong bao khong co du lieu hien thi");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), "Không có dữ liệu hiển thị");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

		log.info("TC_09_Step_06: Click button Back");
		autoSaving.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
	}

	@Test
	public void TC_10_TatToanTaiKhoanTietKiem_VND_1Thang_LaiNhapGoc() {

		log.info("TC_10_1_Click Tat toan tai khoan tiet kiem");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Tất toán tài khoản tiết kiệm");

		log.info("TC_10_2_Chon so tai khoan tiet kiem");
		savingOnline.clickToDynamicDropDownInSavingOnline("Tài khoản tiết kiệm");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, savingAccount);

		log.info("TC_10_3_Chon tai khoan dich");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, "Chọn tài khoản đích");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_10_4_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_10_5_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_10_6_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, "Tiếp tục");

		savingOnline.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		savingOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_10_7_Click nut Home ve man hinh chinh");
		savingOnline.clickToDynamicImageViewByID(driver, "com.VCB:id/ivHome");

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		service.stop();
	}

}
