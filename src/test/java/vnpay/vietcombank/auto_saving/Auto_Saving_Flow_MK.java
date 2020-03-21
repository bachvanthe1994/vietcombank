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
import pageObjects.TransactionReportPageObject;
import pageObjects.saving_online.SavingOnlinePageObject;
import vietcombank_test_data.Auto_Saving_Data;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.SavingOnline_Data;

public class Auto_Saving_Flow_MK extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject home;
	private AutoSavingPageObject autoSaving;
	private TransactionReportPageObject transactionReport;
	private SavingOnlinePageObject savingOnline;

	private String transactionID, savingAccount, tranferMoney, transactionDate, startDate, endDate, sourceAccountMoney;
	private long transferFee;

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

	}

	@Parameters({ "otp" })
	@Test
	public void TC_01_MoTaiKhoanTietKiem_TaiKhoanNguon_VND(String otp) {

		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_01_1_Click Mo tai khoan tiet kiem");
		home.scrollDownToText(driver, "Tín dụng");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Mở tài khoản tiết kiệm");
		savingOnline = PageFactoryManager.getSavingOnlinePageObject(driver);

		log.info("TC_01_2_Chon so tai khoan");
		savingOnline.clickToDynamicDropDown(driver, "Số tài khoản");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_VND);

		log.info("TC_01_3_Chon ky han gui");
		savingOnline.clickToDynamicDropDownListTextViewByHeader(driver, "Thông tin giao dịch", "1");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Data.TEXT.TERM);

		log.info("TC_01_4_Nhap so tien gui");
		savingOnline.inputToDynamicInputBoxByHeader(driver, Auto_Saving_Data.TEXT.INPUT_VND, "Thông tin giao dịch", "2");

		log.info("TC_01_5_Chon hinh thuc chuyen tien");
		savingOnline.clickToDynamicDropDownListTextViewByHeader(driver, "Thông tin giao dịch", "3");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Data.TEXT.FORM_OF_PAYMENT);

		log.info("TC_01_6_Chon dong y tuan thu cam ket");
		savingOnline.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");

		log.info("TC_01_7_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_8_Kiem tra man hinh xac nhan thong tin");

		log.info("TC_01_8_1_Kiem tra tai khoan nguon");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_VND);

		log.info("TC_01_8_2_Kiem tra ky han gui");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Kỳ hạn gửi"), Auto_Saving_Data.TEXT.TERM);

		log.info("TC_01_8_3_Kiem tra lai suat");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Lãi suất"), "4.5%/Năm");

		log.info("TC_01_8_4_Kiem tra so tien gui");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Số tiền gửi"), addCommasToLong(Auto_Saving_Data.TEXT.INPUT_VND) + " VND");

		log.info("TC_01_8_5_Kiem tra hinh thuc tra lai");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Hình thức trả lãi"), Auto_Saving_Data.TEXT.FORM_OF_PAYMENT);

		log.info("TC_01_9_Chon phuong thuc xac thuc");
		savingOnline.scrollDownToText(driver, "Chọn phương thức xác thực");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		transferFee = convertAvailableBalanceCurrentcyOrFeeToLong(savingOnline.getDynamicTextInTransactionDetail(driver, "SMS OTP"));
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_01_10_Kiem tra so tien phi");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), addCommasToLong(transferFee + "") + " VND");

		log.info("TC_01_11_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, "Tiếp tục");

		savingOnline.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		savingOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_12_Kiem tra man hinh Giao dich thanh cong");
		log.info("TC_01_12_1_Kiem tra Giao dich thanh cong");
		verifyTrue(savingOnline.isDynamicMessageAndLabelTextDisplayed(driver, SavingOnline_Data.SUCCESS_TRANSACTION));

		log.info("TC_01_12_6_Lay ma giao dich");
		savingAccount = savingOnline.getDynamicTextInTransactionDetail(driver, "Số tài khoản tiết kiệm");

		log.info("TC_01_13_Click nut Home ve man hinh chinh");
		savingOnline.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivHome");

	}

	@Parameters({ "pass" })
	@Test
	public void TC_02_TietKiemTuDong_TaiKhoanNguon_VND_MK(String pass) {

		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_02_Step_01: Keo xuong va click vao phan 'Tiet kiem tu dong'");
		home.scrollDownToText(driver, "Tín dụng");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Tiết kiệm tự động");
		autoSaving = PageFactoryManager.getAutoSavingPageObject(driver);

		log.info("TC_02_Step_02: Chon tai khoan nguon VND");
		autoSaving.clickToTextViewCombobox(driver, "com.VCB:id/tvContent");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_VND);
		sourceAccountMoney = autoSaving.getMoneyByAccount(driver, "Số dư khả dụng");

		log.info("TC_02_Step_03: Chon tai khoan tiet kiem");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, "Tài khoản tiết kiệm");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, savingAccount);

		log.info("TC_02_Step_04: Xac nhan Ky han va so du TK tiet kiem");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, "Kỳ hạn"), capitalizeString(Auto_Saving_Data.TEXT.TERM));
		startDate = autoSaving.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayBatDau");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, "Số dư hiện tại"), addCommasToLong(Auto_Saving_Data.TEXT.INPUT_VND) + " VND");

		log.info("TC_02_Step_05: Chon ngay ket thuc");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, "Ngày kết thúc");
		autoSaving.clickToDynamicDateInDateTimePicker(driver, getForWardDay(3));
		autoSaving.clickToDynamicButton(driver, "OK");
		endDate = autoSaving.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayKetThuc");

		log.info("TC_02_Step_06: Nhap so tien chuyen");
		autoSaving.inputToDynamicInputBox(driver, Auto_Saving_Data.TEXT.INPUT_VND, "Số tiền chuyển");

		log.info("TC_02_Step_07: An Tiep tuc");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_02_Step_08: Hien thi man hinh xac nhan thong tin");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Xác nhận thông tin");

		log.info("TC_02_Step_09: Hien thi thông tin xac nhan");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleHead"), "Quý khách vui lòng kiểm tra thông tin giao dịch đã khởi tạo");

		log.info("TC_02_Step_10: Hien thi tai khoan nguon");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, "Tài khoản nguồn"), Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_VND);

		log.info("TC_02_Step_11: Hien thi tai khoan tiet kiem");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, "Tài khoản tiết kiệm"), savingAccount);

		log.info("TC_02_Step_12: Hien thi ky han gui");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, "Kỳ hạn gửi"), capitalizeString(Auto_Saving_Data.TEXT.TERM));

		log.info("TC_02_Step_13: Hien thi ngay den han");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, "Ngày đến hạn"), endDate);

		log.info("TC_02_Step_14: Hien thi So tien hien tai");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, "Số tiền hiện tại"), addCommasToLong(Auto_Saving_Data.TEXT.INPUT_VND) + " VND");

		log.info("TC_02_Step_15: Hien thi Chu ky chuyen");
		verifyTrue(autoSaving.isDynamicMessageAndLabelTextDisplayed(driver, "Chu kỳ chuyển"));

		log.info("TC_02_Step_16: Hien thi ngay bat dau");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, "Ngày bắt đầu"), startDate);

		log.info("TC_02_Step_17: Hien thi ngay ket thuc");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, "Ngày kết thúc"), endDate);

		log.info("TC_02_Step_18: Hien thi so tien chuyen");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, "Số tiền chuyển"), addCommasToLong(Auto_Saving_Data.TEXT.INPUT_VND) + " VND");

		log.info("TC_02_Step_19: Chon phương thuc xac thuc");
		autoSaving.clickToTextViewCombobox(driver, "com.VCB:id/tvptxt");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");

		log.info("TC_02_Step_20: An nut 'Tiep tuc'");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_02_Step_21: Nhap ki tu vao o nhap mat khau");
		autoSaving.inputIntoEditTextByID(driver, pass, "com.VCB:id/pin");

		log.info("TC_02_Step_22: An tiep button 'Tiep tuc'");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_02_Step_23: Hien thi man hinh thong bao giao dich thanh cong");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), "GIAO DỊCH THÀNH CÔNG");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount"), addCommasToLong(Auto_Saving_Data.TEXT.INPUT_VND) + " VND");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, "Tài khoản tiết kiệm"), savingAccount);
		transactionDate = autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTime");
		transactionID = autoSaving.getDynamicTextByLabel(driver, "Mã giao dịch");

		log.info("TC_02_Step_24: An thuc hien giao dich moi");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_02_Step_25: Chon tai khoan nguon VND");
		autoSaving.clickToTextViewCombobox(driver, "com.VCB:id/tvContent");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_VND);
		verifyEquals(autoSaving.getMoneyByAccount(driver, "Số dư khả dụng"), sourceAccountMoney);

		log.info("TC_02_Step_26: Click back ve man hinh chinh");
		autoSaving.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

	}

	@Test
	public void TC_03_TietKiemTuDong_TaiKhoanNguon_VND_MK_BaoCaoGiaoDich() {

		log.info("TC_03_Step_01: Mo tab Menu");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_5");

		log.info("TC_03_Step_02: Mo sub-menu 'Bao cao giao dich");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");
		transactionReport = PageFactoryManager.getTransactionReportPageObject(driver);

		log.info("TC_03_Step_03: An vao Dropdown 'Tat ca cac loai giao dich");
		transactionReport.clickToTextViewCombobox(driver, "com.VCB:id/tvSelectTransType");

		log.info("TC_03_Step_04: Chon 'Tiet kiem tu dong'");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, "Tiết kiệm tự động");

		log.info("TC_03_Step_05: An vao Dropdown 'Chon tai khoan/the");
		transactionReport.clickToTextViewCombobox(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_03_Step_06: Chon tai khoan vua thuc hien giao dich");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_VND);

		log.info("TC_03_Step_07: An nut Tim kiem");
		transactionReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");

		log.info("TC_03_Step_08: An vao giao dich dau tien");
		transactionReport.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_03_Step_09: Xac nhan hien thi Title 'Chi tiet giao dich'");
		verifyEquals(transactionReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Chi tiết giao dịch");

		log.info("TC_03_Step_10: Xac nhan hien thi thoi gian giao dich");
		verifyTrue(transactionReport.isTextDisplayedInListTextElements(driver, convertDateTimeIgnoreHHmmss(transactionDate), "com.VCB:id/tvContent"));

		log.info("TC_03_Step_11: Xac nhan hien thi dung ma giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Số lệnh giao dịch"), transactionID);

		log.info("TC_03_Step_12: Xac nhan hien thi so tai khoan giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Tài khoản/thẻ trích nợ"), Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_VND);

		log.info("TC_03_Step_13: Xac nhan hien thi tai khoan ghi co");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Tài khoản ghi có"), savingAccount);

		log.info("TC_03_Step_14: Xac nhan hien thi so tien giao dich");
		verifyTrue(transactionReport.isTextDisplayedInListTextElements(driver, addCommasToLong(Auto_Saving_Data.TEXT.INPUT_VND) + " VND", "com.VCB:id/tvContent"));

		log.info("TC_03_Step_15: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_03_Step_16: An nut back ve man hinh menu");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_03_Step_17: Mo tab Home");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_1");

	}

//	@Test
	public void TC_04_HuyTietKiemTuDong_TaiKhoanNguon_VND() {

		log.info("TC_04_Step_01: Keo xuong va click vao phan 'Huy Tiet kiem tu dong'");
		home.scrollDownToText(driver, "Tín dụng");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Huỷ tiết kiệm tự động");
		autoSaving = PageFactoryManager.getAutoSavingPageObject(driver);

		log.info("TC_04_Step_02: Chon tai khoan nguon");
		autoSaving.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/thongTinTaiKhoanThanhToan");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_VND);

		log.info("TC_04_Step_03: Chon tai khoan tiet kiem");
		autoSaving.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/thongTinTaiKhoanTietKiem");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, savingAccount);

		log.info("TC_04_Step_04: An nut Tim kiem");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");

		log.info("TC_04_Step_05: Xac nhan hien thi tai khoan nguon");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTaiKhoanNguon"), Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_VND);

		log.info("TC_04_Step_06: Xac nhan hien thi tai khoan tiet kiem");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTaiKhoanTietKiem"), savingAccount);

		log.info("TC_04_Step_07: Xac nhan hien thi so tien chuyen");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvSoTienChuyen"), addCommasToLong(Auto_Saving_Data.TEXT.INPUT_VND) + " VND");

		log.info("TC_04_Step_08: Xac nhan hien thi chu ky chuyen");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvThoiGian"), startDate + " - " + endDate);

		log.info("TC_04_Step_09: An nut Huy");
		autoSaving.clickToTextViewCombobox(driver, "com.VCB:id/tvHuy");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), Auto_Saving_Data.TEXT.AUTO_SAVING_CANCEL_MESSAGE);

		log.info("TC_04_Step_10: An nut Dong y");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btCancel");

		log.info("TC_04_Step_11: An nut Dong ");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

		log.info("TC_04_Step_12: An nut back ve man hinh menu");
		autoSaving.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		home = PageFactoryManager.getHomePageObject(driver);

	}

//	@Test
	public void TC_05_HuyTietKiemTuDong_TaiKhoanNguon_VND_BaoCaoGiaoDich() {

		log.info("TC_05_Step_01: Mo tab Menu");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_5");

		log.info("TC_05_Step_02: Mo sub-menu 'Bao cao giao dich");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");
		transactionReport = PageFactoryManager.getTransactionReportPageObject(driver);

		log.info("TC_05_Step_03: An vao Dropdown 'Tat ca cac loai giao dich");
		transactionReport.clickToTextViewCombobox(driver, "com.VCB:id/tvSelectTransType");

		log.info("TC_05_Step_04: Chon 'Huy tiet kiem tu dong'");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, "Hủy tiết kiệm tự động");

		log.info("TC_05_Step_05: An vao Dropdown 'Chon tai khoan/the");
		transactionReport.clickToTextViewCombobox(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_05_Step_06: Chon tai khoan vua thuc hien giao dich");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_VND);

		log.info("TC_05_Step_07: An nut Tim kiem");
		transactionReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");

		log.info("TC_05_Step_08: An vao giao dich dau tien");
		transactionReport.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_05_Step_09: Xac nhan hien thi Title 'Chi tiet giao dich'");
		verifyEquals(transactionReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Chi tiết giao dịch");

		log.info("TC_05_Step_10: Xac nhan hien thi thoi gian giao dich");
		verifyTrue(transactionReport.isTextDisplayedInListTextElements(driver, convertDateTimeIgnoreHHmmss(transactionDate), "com.VCB:id/tvContent"));

		log.info("TC_05_Step_12: Xac nhan hien thi so tai khoan giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Tài khoản/thẻ trích nợ"), Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_VND);

		log.info("TC_05_Step_13: Xac nhan hien thi tai khoan ghi co");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Tài khoản ghi có"), savingAccount);

		log.info("TC_03_Step_14: Xac nhan hien thi so tien giao dich");
		verifyTrue(transactionReport.isTextDisplayedInListTextElements(driver, Auto_Saving_Data.TEXT.INPUT_VND + " VND", "com.VCB:id/tvContent"));

		log.info("TC_05_Step_15: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_05_Step_16: An nut back ve man hinh menu");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_05_Step_17: Mo tab Home");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_1");

	}

	@Test
	public void TC_06_TatToanTaiKhoanTietKiem_VND_1Thang_LaiNhapGoc() {

		log.info("TC_06_1_Click Tat toan tai khoan tiet kiem");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Tất toán tài khoản tiết kiệm");

		log.info("TC_06_2_Chon so tai khoan tiet kiem");
		savingOnline.clickToDynamicDropDownInSavingOnline("Tài khoản tiết kiệm");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, savingAccount);

		log.info("TC_06_3_Chon tai khoan dich");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, "Chọn tài khoản đích");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_VND);

		log.info("TC_06_4_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_06_5_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_06_7_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, "Tiếp tục");

		savingOnline.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		savingOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_06_8_Click nut Home ve man hinh chinh");
		savingOnline.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivHome");

	}

	@Parameters({ "otp" })
	@Test
	public void TC_07_MoTaiKhoanTietKiem_TaiKhoanNguon_USD(String otp) {

		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_07_1_Click Mo tai khoan tiet kiem");
		home.scrollDownToText(driver, "Tín dụng");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Mở tài khoản tiết kiệm");
		savingOnline = PageFactoryManager.getSavingOnlinePageObject(driver);

		log.info("TC_07_2_Chon so tai khoan");
		savingOnline.clickToDynamicDropDown(driver, "Số tài khoản");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_USD);

		log.info("TC_07_3_Chon ky han gui");
		savingOnline.clickToDynamicDropDownListTextViewByHeader(driver, "Thông tin giao dịch", "1");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Data.TEXT.TERM);

		log.info("TC_07_4_Nhap so tien gui");
		savingOnline.inputToDynamicInputBoxByHeader(driver, Auto_Saving_Data.TEXT.INPUT_USD, "Thông tin giao dịch", "2");

		log.info("TC_07_5_Chon hinh thuc chuyen tien");
		savingOnline.clickToDynamicDropDownListTextViewByHeader(driver, "Thông tin giao dịch", "3");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Data.TEXT.FORM_OF_PAYMENT);

		log.info("TC_07_6_Chon dong y tuan thu cam ket");
		savingOnline.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");

		log.info("TC_07_7_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_07_8_Kiem tra man hinh xac nhan thong tin");

		log.info("TC_07_8_1_Kiem tra tai khoan nguon");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_USD);

		log.info("TC_07_8_2_Kiem tra ky han gui");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Kỳ hạn gửi"), Auto_Saving_Data.TEXT.TERM);

		log.info("TC_07_8_3_Kiem tra lai suat");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Lãi suất"), "4.5%/Năm");

		log.info("TC_07_8_4_Kiem tra so tien gui");
		verifyEquals(savingOnline.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvSoTien"), Auto_Saving_Data.TEXT.INPUT_USD + ".00 USD");
		tranferMoney = savingOnline.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvQuyDoi");

		log.info("TC_07_8_5_Kiem tra hinh thuc tra lai");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Hình thức trả lãi"), Auto_Saving_Data.TEXT.FORM_OF_PAYMENT);

		log.info("TC_07_09_Chon phuong thuc xac thuc");
		savingOnline.scrollDownToText(driver, "Chọn phương thức xác thực");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		transferFee = convertAvailableBalanceCurrentcyOrFeeToLong(savingOnline.getDynamicTextInTransactionDetail(driver, "SMS OTP"));
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_07_10_Kiem tra so tien phi");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), addCommasToLong(transferFee + "") + " VND");

		log.info("TC_07_11_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, "Tiếp tục");

		savingOnline.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		savingOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_07_12_Kiem tra man hinh Giao dich thanh cong");
		log.info("TC_07_12_1_Kiem tra Giao dich thanh cong");
		verifyTrue(savingOnline.isDynamicMessageAndLabelTextDisplayed(driver, SavingOnline_Data.SUCCESS_TRANSACTION));

		log.info("TC_07_12_6_Lay ma giao dich");
		savingAccount = savingOnline.getDynamicTextInTransactionDetail(driver, "Số tài khoản tiết kiệm");

		log.info("TC_07_13_Click nut Home ve man hinh chinh");
		savingOnline.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivHome");

	}

	@Parameters({ "pass" })
	@Test
	public void TC_08_TietKiemTuDong_TaiKhoanNguon_USD_MK(String pass) {

		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_08_Step_01: Keo xuong va click vao phan 'Tiet kiem tu dong'");
		home.scrollDownToText(driver, "Tín dụng");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Tiết kiệm tự động");
		autoSaving = PageFactoryManager.getAutoSavingPageObject(driver);

		log.info("TC_08_Step_02: Chon tai khoan nguon USD");
		autoSaving.clickToTextViewCombobox(driver, "com.VCB:id/tvContent");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_USD);
		sourceAccountMoney = autoSaving.getMoneyByAccount(driver, "Số dư khả dụng");

		log.info("TC_08_Step_03: Chon tai khoan tiet kiem");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, "Tài khoản tiết kiệm");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, savingAccount);

		log.info("TC_08_Step_04: Xac nhan Ky han va so du TK tiet kiem");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, "Kỳ hạn"), capitalizeString(Auto_Saving_Data.TEXT.TERM));
		startDate = autoSaving.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayBatDau");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, "Số dư hiện tại"), tranferMoney);

		log.info("TC_08_Step_05: Chon ngay ket thuc");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, "Ngày kết thúc");
		autoSaving.clickToDynamicDateInDateTimePicker(driver, getForWardDay(3));
		autoSaving.clickToDynamicButton(driver, "OK");
		endDate = autoSaving.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayKetThuc");

		log.info("TC_08_Step_06: Nhap so tien chuyen");
		autoSaving.inputToDynamicInputBox(driver, Auto_Saving_Data.TEXT.INPUT_USD, "Số tiền chuyển");

		log.info("TC_08_Step_07: An Tiep tuc");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_08_Step_08: Hien thi man hinh xac nhan thong tin");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Xác nhận thông tin");

		log.info("TC_08_Step_09: Hien thi thông tin xac nhan");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleHead"), "Quý khách vui lòng kiểm tra thông tin giao dịch đã khởi tạo");

		log.info("TC_08_Step_10: Hien thi tai khoan nguon");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, "Tài khoản nguồn"), Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_USD);

		log.info("TC_08_Step_11: Hien thi tai khoan tiet kiem");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, "Tài khoản tiết kiệm"), savingAccount);

		log.info("TC_08_Step_12: Hien thi ky han gui");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, "Kỳ hạn gửi"), capitalizeString(Auto_Saving_Data.TEXT.TERM));

		log.info("TC_08_Step_13: Hien thi ngay den han");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, "Ngày đến hạn"), endDate);

		log.info("TC_08_Step_14: Hien thi So tien hien tai");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, "Số tiền hiện tại"), tranferMoney);

		log.info("TC_08_Step_15: Hien thi Chu ky chuyen");
		verifyTrue(autoSaving.isDynamicMessageAndLabelTextDisplayed(driver, "Chu kỳ chuyển"));

		log.info("TC_08_Step_16: Hien thi ngay bat dau");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, "Ngày bắt đầu"), startDate);

		log.info("TC_08_Step_17: Hien thi ngay ket thuc");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, "Ngày kết thúc"), endDate);

		log.info("TC_08_Step_18: Hien thi so tien chuyen");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvSoTien"), addCommasToDouble(Auto_Saving_Data.TEXT.INPUT_USD) + " USD");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvQuyDoi"), tranferMoney);

		log.info("TC_08_Step_19: Chon phương thuc xac thuc");
		autoSaving.clickToTextViewCombobox(driver, "com.VCB:id/tvptxt");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");

		log.info("TC_08_Step_20: An nut 'Tiep tuc'");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_08_Step_21: Nhap ki tu vao o nhap mat khau");
		autoSaving.inputIntoEditTextByID(driver, pass, "com.VCB:id/pin");

		log.info("TC_08_Step_22: An tiep button 'Tiep tuc'");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_08_Step_23: Hien thi man hinh thong bao giao dich thanh cong");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), "GIAO DỊCH THÀNH CÔNG");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount"), tranferMoney);
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, "Tài khoản tiết kiệm"), savingAccount);
		transactionDate = autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTime");
		transactionID = autoSaving.getDynamicTextByLabel(driver, "Mã giao dịch");

		log.info("TC_08_Step_24: An thuc hien giao dich moi");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_08_Step_25: Chon tai khoan nguon USD");
		autoSaving.clickToTextViewCombobox(driver, "com.VCB:id/tvContent");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_USD);
		verifyEquals(autoSaving.getMoneyByAccount(driver, "Số dư khả dụng"), sourceAccountMoney);

		log.info("TC_08_Step_26: Click back ve man hinh chinh");
		autoSaving.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

	}

	@Test
	public void TC_09_TietKiemTuDong_TaiKhoanNguon_USD_MK_BaoCaoGiaoDich() {

		log.info("TC_09_Step_01: Mo tab Menu");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_5");

		log.info("TC_09_Step_02: Mo sub-menu 'Bao cao giao dich");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");
		transactionReport = PageFactoryManager.getTransactionReportPageObject(driver);

		log.info("TC_09_Step_03: An vao Dropdown 'Tat ca cac loai giao dich");
		transactionReport.clickToTextViewCombobox(driver, "com.VCB:id/tvSelectTransType");

		log.info("TC_09_Step_04: Chon 'Tiet kiem tu dong'");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, "Tiết kiệm tự động");

		log.info("TC_09_Step_05: An vao Dropdown 'Chon tai khoan/the");
		transactionReport.clickToTextViewCombobox(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_09_Step_06: Chon tai khoan vua thuc hien giao dich");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_USD);

		log.info("TC_09_Step_07: An nut Tim kiem");
		transactionReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");

		log.info("TC_09_Step_08: An vao giao dich dau tien");
		transactionReport.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_09_Step_09: Xac nhan hien thi Title 'Chi tiet giao dich'");
		verifyEquals(transactionReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Chi tiết giao dịch");

		log.info("TC_09_Step_10: Xac nhan hien thi thoi gian giao dich");
		verifyTrue(transactionReport.isTextDisplayedInListTextElements(driver, convertDateTimeIgnoreHHmmss(transactionDate), "com.VCB:id/tvContent"));

		log.info("TC_09_Step_11: Xac nhan hien thi dung ma giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Số lệnh giao dịch"), transactionID);

		log.info("TC_09_Step_12: Xac nhan hien thi so tai khoan giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Tài khoản/thẻ trích nợ"), Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_USD);

		log.info("TC_09_Step_13: Xac nhan hien thi tai khoan ghi co");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Tài khoản ghi có"), savingAccount);

		log.info("TC_09_Step_14: Xac nhan hien thi so tien giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Số tiền giao dịch"), addCommasToDouble(Auto_Saving_Data.TEXT.INPUT_USD) + " USD");

		log.info("TC_09_Step_15: Xac nhan hien thi so tien quy doi");
		verifyTrue(transactionReport.isTextDisplayedInListTextElements(driver, addCommasToLong(tranferMoney) + " VND", "com.VCB:id/tvContent"));

		log.info("TC_09_Step_16: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_09_Step_17: An nut back ve man hinh menu");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_09_Step_18: Mo tab Home");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_1");

	}

//	@Test
	public void TC_10_HuyTietKiemTuDong_TaiKhoanNguon_USD_MK() {

		log.info("TC_10_Step_01: Keo xuong va click vao phan 'Huy Tiet kiem tu dong'");
		home.scrollDownToText(driver, "Tín dụng");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Huỷ tiết kiệm tự động");
		autoSaving = PageFactoryManager.getAutoSavingPageObject(driver);

		log.info("TC_10_Step_02: Chon tai khoan nguon");
		autoSaving.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/thongTinTaiKhoanThanhToan");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_USD);

		log.info("TC_10_Step_03: Chon tai khoan tiet kiem");
		autoSaving.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/thongTinTaiKhoanTietKiem");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, savingAccount);

		log.info("TC_10_Step_04: An nut Tim kiem");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");

		log.info("TC_10_Step_05: Xac nhan hien thi tai khoan nguon");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTaiKhoanNguon"), Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_USD);

		log.info("TC_10_Step_06: Xac nhan hien thi tai khoan tiet kiem");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTaiKhoanTietKiem"), savingAccount);

		log.info("TC_10_Step_07: Xac nhan hien thi so tien chuyen");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvSoTienChuyen"), tranferMoney);

		log.info("TC_10_Step_08: Xac nhan hien thi chu ky chuyen");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvThoiGian"), startDate + " - " + endDate);

		log.info("TC_10_Step_09: An nut Huy");
		autoSaving.clickToTextViewCombobox(driver, "com.VCB:id/tvHuy");

		log.info("TC_10_Step_10: An nut Dong y");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btCancel");

		log.info("TC_10_Step_11: An nut Dong ");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

		log.info("TC_10_Step_12: An nut back ve man hinh menu");
		autoSaving.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);
	}

//	@Test
	public void TC_11_HuyTietKiemTuDong_TaiKhoanNguon_USD_BaoCaoGiaoDich() {

		log.info("TC_11_Step_01: Mo tab Menu");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_5");

		log.info("TC_11_Step_02: Mo sub-menu 'Bao cao giao dich");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");
		transactionReport = PageFactoryManager.getTransactionReportPageObject(driver);

		log.info("TC_11_Step_03: An vao Dropdown 'Tat ca cac loai giao dich");
		transactionReport.clickToTextViewCombobox(driver, "com.VCB:id/tvSelectTransType");

		log.info("TC_11_Step_04: Chon 'Huy tiet kiem tu dong'");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, "Hủy tiết kiệm tự động");

		log.info("TC_11_Step_05: An vao Dropdown 'Chon tai khoan/the");
		transactionReport.clickToTextViewCombobox(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_11_Step_06: Chon tai khoan vua thuc hien giao dich");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_USD);

		log.info("TC_11_Step_07: An nut Tim kiem");
		transactionReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");

		log.info("TC_11_Step_08: An vao giao dich dau tien");
		transactionReport.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_11_Step_09: Xac nhan hien thi Title 'Chi tiet giao dich'");
		verifyEquals(transactionReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Chi tiết giao dịch");

		log.info("TC_11_Step_10: Xac nhan hien thi thoi gian giao dich");
		verifyTrue(transactionReport.isTextDisplayedInListTextElements(driver, convertDateTimeIgnoreHHmmss(transactionDate), "com.VCB:id/tvContent"));

		log.info("TC_11_Step_11: Xac nhan hien thi so tai khoan giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Tài khoản/thẻ trích nợ"), Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_USD);

		log.info("TC_11_Step_12: Xac nhan hien thi tai khoan ghi co");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Tài khoản ghi có"), savingAccount);

		log.info("TC_11_Step_13: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_11_Step_14: An nut back ve man hinh menu");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_11_Step_15: Mo tab Home");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_1");
	}

	@Test
	public void TC_12_TatToanTaiKhoanTietKiem_VND_1Thang_LaiNhapGoc() {

		log.info("TC_12_1_Click Tat toan tai khoan tiet kiem");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Tất toán tài khoản tiết kiệm");

		log.info("TC_12_2_Chon so tai khoan tiet kiem");
		savingOnline.clickToDynamicDropDownInSavingOnline("Tài khoản tiết kiệm");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, savingAccount);

		log.info("TC_12_3_Chon tai khoan dich");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, "Chọn tài khoản đích");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_VND);

		log.info("TC_12_4_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_12_5_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_12_7_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, "Tiếp tục");

		savingOnline.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		savingOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_12_8_Click nut Home ve man hinh chinh");
		savingOnline.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivHome");
	}

	@Parameters({ "otp" })
	@Test
	public void TC_13_MoTaiKhoanTietKiem_TaiKhoanNguon_EUR(String otp) {

		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_13_1_Click Mo tai khoan tiet kiem");
		home.scrollDownToText(driver, "Tín dụng");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Mở tài khoản tiết kiệm");
		savingOnline = PageFactoryManager.getSavingOnlinePageObject(driver);

		log.info("TC_13_2_Chon so tai khoan");
		savingOnline.clickToDynamicDropDown(driver, "Số tài khoản");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_EUR);

		log.info("TC_13_3_Chon ky han gui");
		savingOnline.clickToDynamicDropDownListTextViewByHeader(driver, "Thông tin giao dịch", "1");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Data.TEXT.TERM);

		log.info("TC_13_4_Nhap so tien gui");
		savingOnline.inputToDynamicInputBoxByHeader(driver, Auto_Saving_Data.TEXT.INPUT_EUR, "Thông tin giao dịch", "2");

		log.info("TC_13_5_Chon hinh thuc chuyen tien");
		savingOnline.clickToDynamicDropDownListTextViewByHeader(driver, "Thông tin giao dịch", "3");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Data.TEXT.FORM_OF_PAYMENT);

		log.info("TC_13_6_Chon dong y tuan thu cam ket");
		savingOnline.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");

		log.info("TC_13_7_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_13_8_Kiem tra man hinh xac nhan thong tin");

		log.info("TC_13_8_1_Kiem tra tai khoan nguon");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_EUR);

		log.info("TC_13_8_2_Kiem tra ky han gui");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Kỳ hạn gửi"), Auto_Saving_Data.TEXT.TERM);

		log.info("TC_13_8_3_Kiem tra lai suat");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Lãi suất"), "4.5%/Năm");

		log.info("TC_13_8_4_Kiem tra so tien gui");
		verifyEquals(savingOnline.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvSoTien"), Auto_Saving_Data.TEXT.INPUT_EUR + ".00 EUR");
		tranferMoney = savingOnline.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvQuyDoi");

		log.info("TC_13_8_5_Kiem tra hinh thuc tra lai");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Hình thức trả lãi"), Auto_Saving_Data.TEXT.FORM_OF_PAYMENT);

		log.info("TC_13_09_Chon phuong thuc xac thuc");
		savingOnline.scrollDownToText(driver, "Chọn phương thức xác thực");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		transferFee = convertAvailableBalanceCurrentcyOrFeeToLong(savingOnline.getDynamicTextInTransactionDetail(driver, "SMS OTP"));
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_13_10_Kiem tra so tien phi");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), addCommasToLong(transferFee + "") + " VND");

		log.info("TC_13_11_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, "Tiếp tục");

		savingOnline.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		savingOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_13_12_Kiem tra man hinh Giao dich thanh cong");
		log.info("TC_13_12_1_Kiem tra Giao dich thanh cong");
		verifyTrue(savingOnline.isDynamicMessageAndLabelTextDisplayed(driver, SavingOnline_Data.SUCCESS_TRANSACTION));

		log.info("TC_13_12_6_Lay ma giao dich");
		savingAccount = savingOnline.getDynamicTextInTransactionDetail(driver, "Số tài khoản tiết kiệm");

		log.info("TC_13_13_Click nut Home ve man hinh chinh");
		savingOnline.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivHome");

	}

	@Parameters({ "pass" })
	@Test
	public void TC_14_TietKiemTuDong_TaiKhoanNguon_EUR_MK(String pass) {

		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_14_Step_01: Keo xuong va click vao phan 'Tiet kiem tu dong'");
		home.scrollDownToText(driver, "Tín dụng");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Tiết kiệm tự động");
		autoSaving = PageFactoryManager.getAutoSavingPageObject(driver);

		log.info("TC_14_Step_02: Chon tai khoan nguon EUR");
		autoSaving.clickToTextViewCombobox(driver, "com.VCB:id/tvContent");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, "0019967190");
		sourceAccountMoney = autoSaving.getMoneyByAccount(driver, "Số dư khả dụng");

		log.info("TC_14_Step_03: Chon tai khoan tiet kiem");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, "Tài khoản tiết kiệm");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, savingAccount);

		log.info("TC_14_Step_04: Xac nhan Ky han va so du TK tiet kiem");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, "Kỳ hạn"), capitalizeString(Auto_Saving_Data.TEXT.TERM));
		startDate = autoSaving.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayBatDau");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, "Số dư hiện tại"), tranferMoney);

		log.info("TC_14_Step_05: Chon ngay ket thuc");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, "Ngày kết thúc");
		autoSaving.clickToDynamicDateInDateTimePicker(driver, getForWardDay(3));
		autoSaving.clickToDynamicButton(driver, "OK");
		endDate = autoSaving.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayKetThuc");

		log.info("TC_14_Step_06: Nhap so tien chuyen");
		autoSaving.inputToDynamicInputBox(driver, Auto_Saving_Data.TEXT.INPUT_EUR, "Số tiền chuyển");

		log.info("TC_14_Step_07: An Tiep tuc");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_14_Step_08: Hien thi man hinh xac nhan thong tin");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Xác nhận thông tin");

		log.info("TC_14_Step_09: Hien thi thông tin xac nhan");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleHead"), "Quý khách vui lòng kiểm tra thông tin giao dịch đã khởi tạo");

		log.info("TC_14_Step_10: Hien thi tai khoan nguon");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, "Tài khoản nguồn"), Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_EUR);

		log.info("TC_14_Step_11: Hien thi tai khoan tiet kiem");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, "Tài khoản tiết kiệm"), savingAccount);

		log.info("TC_14_Step_12: Hien thi ky han gui");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, "Kỳ hạn gửi"), capitalizeString(Auto_Saving_Data.TEXT.TERM));

		log.info("TC_14_Step_13: Hien thi ngay den han");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, "Ngày đến hạn"), endDate);

		log.info("TC_14_Step_14: Hien thi So tien hien tai");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, "Số tiền hiện tại"), tranferMoney);

		log.info("TC_14_Step_15: Hien thi Chu ky chuyen");
		verifyTrue(autoSaving.isDynamicMessageAndLabelTextDisplayed(driver, "Chu kỳ chuyển"));

		log.info("TC_14_Step_16: Hien thi ngay bat dau");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, "Ngày bắt đầu"), startDate);

		log.info("TC_14_Step_17: Hien thi ngay ket thuc");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, "Ngày kết thúc"), endDate);

		log.info("TC_14_Step_18: Hien thi so tien chuyen");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvSoTien"), addCommasToDouble(Auto_Saving_Data.TEXT.INPUT_EUR) + " EUR");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvQuyDoi"), tranferMoney);

		log.info("TC_14_Step_19: Chon phương thuc xac thuc");
		autoSaving.clickToTextViewCombobox(driver, "com.VCB:id/tvptxt");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");

		log.info("TC_14_Step_20: An nut 'Tiep tuc'");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_14_Step_21: Nhap ki tu vao o nhap mat khau");
		autoSaving.inputIntoEditTextByID(driver, pass, "com.VCB:id/pin");

		log.info("TC_14_Step_22: An tiep button 'Tiep tuc'");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_14_Step_23: Hien thi man hinh thong bao giao dich thanh cong");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), "GIAO DỊCH THÀNH CÔNG");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount"), tranferMoney);
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, "Tài khoản tiết kiệm"), savingAccount);
		transactionDate = autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTime");
		transactionID = autoSaving.getDynamicTextByLabel(driver, "Mã giao dịch");

		log.info("TC_14_Step_24: An thuc hien giao dich moi");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_14_Step_25: Chon tai khoan nguon EUR");
		autoSaving.clickToTextViewCombobox(driver, "com.VCB:id/tvContent");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, "0019967190");
		verifyEquals(autoSaving.getMoneyByAccount(driver, "Số dư khả dụng"), sourceAccountMoney);

		log.info("TC_14_Step_26: Click back ve man hinh chinh");
		autoSaving.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

	}

	@Test
	public void TC_15_TietKiemTuDong_TaiKhoanNguon_EUR_MK_BaoCaoGiaoDich() {

		log.info("TC_15_Step_01: Mo tab Menu");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_5");

		log.info("TC_15_Step_02: Mo sub-menu 'Bao cao giao dich");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");
		transactionReport = PageFactoryManager.getTransactionReportPageObject(driver);

		log.info("TC_15_Step_03: An vao Dropdown 'Tat ca cac loai giao dich");
		transactionReport.clickToTextViewCombobox(driver, "com.VCB:id/tvSelectTransType");

		log.info("TC_15_Step_04: Chon 'Tiet kiem tu dong'");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, "Tiết kiệm tự động");

		log.info("TC_15_Step_05: An vao Dropdown 'Chon tai khoan/the");
		transactionReport.clickToTextViewCombobox(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_15_Step_06: Chon tai khoan vua thuc hien giao dich");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, "0019961175");

		log.info("TC_15_Step_07: An nut Tim kiem");
		transactionReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");

		log.info("TC_15_Step_08: An vao giao dich dau tien");
		transactionReport.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_15_Step_09: Xac nhan hien thi Title 'Chi tiet giao dich'");
		verifyEquals(transactionReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Chi tiết giao dịch");

		log.info("TC_15_Step_10: Xac nhan hien thi thoi gian giao dich");
		verifyTrue(transactionReport.isTextDisplayedInListTextElements(driver, convertDateTimeIgnoreHHmmss(transactionDate), "com.VCB:id/tvContent"));

		log.info("TC_15_Step_11: Xac nhan hien thi dung ma giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Số lệnh giao dịch"), transactionID);

		log.info("TC_15_Step_12: Xac nhan hien thi so tai khoan giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Tài khoản/thẻ trích nợ"), Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_EUR);

		log.info("TC_15_Step_13: Xac nhan hien thi tai khoan ghi co");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Tài khoản ghi có"), savingAccount);

		log.info("TC_15_Step_14: Xac nhan hien thi so tien giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Số tiền giao dịch"), addCommasToDouble(Auto_Saving_Data.TEXT.INPUT_EUR) + " EUR");

		log.info("TC_15_Step_15: Xac nhan hien thi so tien quy doi");
		verifyTrue(transactionReport.isTextDisplayedInListTextElements(driver, addCommasToLong(tranferMoney) + " VND", "com.VCB:id/tvContent"));

		log.info("TC_15_Step_16: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_15_Step_17: An nut back ve man hinh menu");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_15_Step_18: Mo tab Home");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_1");

	}

//	@Test
	public void TC_16_HuyTietKiemTuDong_TaiKhoanNguon_EUR_MK() {

		log.info("TC_16_Step_01: Keo xuong va click vao phan 'Huy Tiet kiem tu dong'");
		home.scrollDownToText(driver, "Tín dụng");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Huỷ tiết kiệm tự động");
		autoSaving = PageFactoryManager.getAutoSavingPageObject(driver);

		log.info("TC_16_Step_02: Chon tai khoan nguon");
		autoSaving.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/thongTinTaiKhoanThanhToan");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_EUR);

		log.info("TC_16_Step_03: Chon tai khoan tiet kiem");
		autoSaving.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/thongTinTaiKhoanTietKiem");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, savingAccount);

		log.info("TC_16_Step_04: An nut Tim kiem");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");

		log.info("TC_16_Step_05: Xac nhan hien thi tai khoan nguon");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTaiKhoanNguon"), Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_EUR);

		log.info("TC_16_Step_06: Xac nhan hien thi tai khoan tiet kiem");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTaiKhoanTietKiem"), savingAccount);

		log.info("TC_16_Step_07: Xac nhan hien thi so tien chuyen");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvSoTienChuyen"), tranferMoney);

		log.info("TC_16_Step_08: Xac nhan hien thi chu ky chuyen");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvThoiGian"), startDate + " - " + endDate);

		log.info("TC_16_Step_09: An nut Huy");
		autoSaving.clickToTextViewCombobox(driver, "com.VCB:id/tvHuy");

		log.info("TC_16_Step_10: An nut Dong y");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btCancel");

		log.info("TC_16_Step_11: An nut Dong ");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

		log.info("TC_16_Step_12: An nut back ve man hinh menu");
		autoSaving.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);

	}

//	@Test
	public void TC_17_HuyTietKiemTuDong_TaiKhoanNguon_EUR_BaoCaoGiaoDich() {

		log.info("TC_17_Step_01: Mo tab Menu");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_5");

		log.info("TC_17_Step_02: Mo sub-menu 'Bao cao giao dich");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");
		transactionReport = PageFactoryManager.getTransactionReportPageObject(driver);

		log.info("TC_17_Step_03: An vao Dropdown 'Tat ca cac loai giao dich");
		transactionReport.clickToTextViewCombobox(driver, "com.VCB:id/tvSelectTransType");

		log.info("TC_17_Step_04: Chon 'Huy tiet kiem tu dong'");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, "Hủy tiết kiệm tự động");

		log.info("TC_17_Step_05: An vao Dropdown 'Chon tai khoan/the");
		transactionReport.clickToTextViewCombobox(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_17_Step_06: Chon tai khoan vua thuc hien giao dich");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_EUR);

		log.info("TC_17_Step_07: An nut Tim kiem");
		transactionReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");

		log.info("TC_17_Step_08: An vao giao dich dau tien");
		transactionReport.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_17_Step_09: Xac nhan hien thi Title 'Chi tiet giao dich'");
		verifyEquals(transactionReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Chi tiết giao dịch");

		log.info("TC_17_Step_10: Xac nhan hien thi thoi gian giao dich");
		verifyTrue(transactionReport.isTextDisplayedInListTextElements(driver, convertDateTimeIgnoreHHmmss(transactionDate), "com.VCB:id/tvContent"));

		log.info("TC_17_Step_11: Xac nhan hien thi so tai khoan giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Tài khoản/thẻ trích nợ"), Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_EUR);

		log.info("TC_17_Step_12: Xac nhan hien thi tai khoan ghi co");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Tài khoản ghi có"), savingAccount);

		log.info("TC_17_Step_13: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_17_Step_14: An nut back ve man hinh menu");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_17_Step_15: Mo tab Home");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_1");
	}

	@Test
	public void TC_18_TatToanTaiKhoanTietKiem_VND_1Thang_LaiNhapGoc() {

		log.info("TC_18_1_Click Tat toan tai khoan tiet kiem");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Tất toán tài khoản tiết kiệm");

		log.info("TC_18_2_Chon so tai khoan tiet kiem");
		savingOnline.clickToDynamicDropDownInSavingOnline("Tài khoản tiết kiệm");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, savingAccount);

		log.info("TC_18_3_Chon tai khoan dich");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, "Chọn tài khoản đích");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_VND);

		log.info("TC_18_4_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_18_5_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_18_6_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, "Tiếp tục");

		savingOnline.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		savingOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_18_7_Click nut Home ve man hinh chinh");
		savingOnline.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivHome");

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		service.stop();
	}

}
