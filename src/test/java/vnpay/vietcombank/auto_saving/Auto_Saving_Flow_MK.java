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
	
	private String transactionID,savingAccount,tranferMoney,transactionDate;
	private long transferFee;
	
	
	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login("0335059860", pass, opt);

	}
	
	@Parameters ({"otp"})
	@Test
	public void TC_01_MoTaiKhoanTietKiem_TaiKhoanNguon_VND (String otp) {
		
		home = PageFactoryManager.getHomePageObject(driver);
		
		log.info("TC_01_1_Click Mo tai khoan tiet kiem");
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
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"),  Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_VND);

		log.info("TC_01_8_2_Kiem tra ky han gui");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Kỳ hạn gửi"), Auto_Saving_Data.TEXT.TERM);

		log.info("TC_01_8_3_Kiem tra lai suat");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Lãi suất"), "4.5%/Năm");

		log.info("TC_01_8_4_Kiem tra so tien gui");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Số tiền gửi"), addCommasToLong(Auto_Saving_Data.TEXT.INPUT_VND) + " VND");
		
		log.info("TC_01_8_5_Kiem tra hinh thuc tra lai");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Hình thức trả lãi"), Auto_Saving_Data.TEXT.FORM_OF_PAYMENT);
		
		log.info("TC_01_09_Chon phuong thuc xac thuc");
		savingOnline.scrollDownToText(driver, "Chọn phương thức xác thực");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		transferFee = convertAvailableBalanceCurrentcyToLong(savingOnline.getDynamicTextInTransactionDetail(driver, "SMS OTP"));
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
		savingOnline.clickToDynamicBottomMenu(driver, "com.VCB:id/ivHome");

	}

	@Parameters ({"pass"})
	@Test
	public void TC_02_TietKiemTuDong_TaiKhoanNguon_VND_MK(String pass) {
		
		home = PageFactoryManager.getHomePageObject(driver);
		
		log.info("TC_02_Step_01: Keo xuong va click vao phan 'Tiet kiem tu dong'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Tiết kiệm tự động");
		autoSaving = PageFactoryManager.getAutoSavingPageObject(driver);
		
		log.info("TC_02_Step_02: Chon tai khoan nguon VND");
		autoSaving.clickToTextViewCombobox(driver, "com.VCB:id/tvContent");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver,  Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_VND);
		
		log.info("TC_02_Step_03: Chon tai khoan tiet kiem");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, "Tài khoản tiết kiệm");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, savingAccount);
		
		log.info("TC_02_Step_04: Chon ngay ket thuc");
		
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, "Ngày kết thúc");
		autoSaving.clickToDynamicDateInDateTimePicker(driver, getForWardDay(3));
		autoSaving.clickToDynamicButton(driver, "OK");
		
		log.info("TC_02_Step_05: Nhap so tien chuyen");
		autoSaving.inputToDynamicInputBox(driver,Auto_Saving_Data.TEXT.INPUT_VND, "Số tiền chuyển");
		
		log.info("TC_02_Step_06: An Tiep tuc");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");
		
		log.info("TC_02_Step_07: Hien thi man hinh xac nhan thong tin");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Xác nhận thông tin");
		
		log.info("TC_02_Step_08: Chon phương thuc xac thuc");
		autoSaving.clickToTextViewCombobox(driver, "com.VCB:id/tvptxt");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");

		log.info("TC_02_Step_09: An nut 'Tiep tuc'");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_02_Step_10: Nhap ki tu vao o nhap mat khau");
		autoSaving.inputIntoEditTextByID(driver, pass, "com.VCB:id/pin");

		log.info("TC_02_Step_11: An tiep button 'Tiep tuc'");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_02_Step_12: Hien thi man hinh thong bao giao dich thanh cong");
		transactionDate = autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTime");
		transactionID = autoSaving.getDynamicTextByLabel(driver, "Mã giao dịch");
		
		log.info("TC_02_Step_13: An thuc hien giao dich moi");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");
		
		log.info("TC_02_Step_14: Click back ve man hinh chinh");
		autoSaving.clickToDynamicBottomMenu(driver, "com.VCB:id/ivTitleLeft");
	}

	@Test
	public void TC_03_TietKiemTuDong_TaiKhoanNguon_VND_MK_BaoCaoGiaoDich() {
		
		log.info("TC_03_Step_01: Mo tab Menu");
		home.clickToDynamicBottomMenu(driver, "com.VCB:id/menu_5");
		
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
		transactionReport.clickToDynamicTransactionInTransactionOrderStatus(driver, "0","com.VCB:id/tvContent");
		
		log.info("TC_03_Step_09: Xac nhan hien thi Title 'Chi tiet giao dich'");
		verifyEquals(transactionReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Chi tiết giao dịch");
		
		log.info("TC_03_Step_10: Xac nhan hien thi thoi gian giao dich");
		verifyTrue(transactionReport.isTextDisplayedInListTextElements(driver, convertDateTimeIgnoreHHmmss(transactionDate), "com.VCB:id/tvContent"));
		
		log.info("TC_03_Step_11: Xac nhan hien thi dung ma giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Số lệnh giao dịch"), transactionID);
		
		log.info("TC_03_Step_12: Xac nhan hien thi so tai khoan giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Tài khoản/thẻ trích nợ"),  Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_VND);
		
		log.info("TC_03_Step_13: Xac nhan hien thi so dien thoai duoc nap");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Tài khoản ghi có"), savingAccount);
		
		log.info("TC_03_Step_14: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicBottomMenu(driver, "com.VCB:id/ivTitleLeft");
		
		log.info("TC_03_Step_15: An nut back ve man hinh menu");
		transactionReport.clickToDynamicBottomMenu(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);
		
		log.info("TC_03_Step_16: Mo tab Home");
		home.clickToDynamicBottomMenu(driver, "com.VCB:id/menu_1");
	}
	
	@Test
	public void TC_04_HuyTietKiemTuDong_TaiKhoanNguon_VND() {
		
		log.info("TC_04_Step_01: Keo xuong va click vao phan 'Huy Tiet kiem tu dong'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Huỷ tiết kiệm tự động");
		autoSaving = PageFactoryManager.getAutoSavingPageObject(driver);
		
		log.info("TC_04_Step_02: Chon tai khoan nguon");
		autoSaving.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/thongTinTaiKhoanThanhToan");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver,  Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_VND);
		
		log.info("TC_04_Step_03: Chon tai khoan tiet kiem");
		autoSaving.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/thongTinTaiKhoanTietKiem");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, savingAccount);
		
		log.info("TC_04_Step_04: An nut Tim kiem");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");
		
		log.info("TC_04_Step_05: Xac nhan hien thi tai khoan nguon");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTaiKhoanNguon"),  Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_VND);
		
		log.info("TC_04_Step_06: Xac nhan hien thi tai khoan tiet kiem");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTaiKhoanTietKiem"), savingAccount);
		
		log.info("TC_04_Step_07: Xac nhan hien thi so tien chuyen");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvSoTienChuyen"), addCommasToLong(Auto_Saving_Data.TEXT.INPUT_VND) + " VND");
		
		log.info("TC_04_Step_09: An nut Huy");
		autoSaving.clickToTextViewCombobox(driver, "com.VCB:id/tvHuy");
		
		log.info("TC_04_Step_10: An nut Dong y");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btCancel");
		
		log.info("TC_04_Step_11: An nut Dong ");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
		
		log.info("TC_04_Step_11: An nut back ve man hinh menu");
		autoSaving.clickToDynamicBottomMenu(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);
		
	}
	
	@Test
	public void TC_05_HuyTietKiemTuDong_TaiKhoanNguon_VND_BaoCaoGiaoDich() {
		
		log.info("TC_05_Step_01: Mo tab Menu");
		home.clickToDynamicBottomMenu(driver, "com.VCB:id/menu_5");
		
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
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver,  Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_VND);
		
		log.info("TC_05_Step_07: An nut Tim kiem");
		transactionReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");
		
		log.info("TC_05_Step_08: An vao giao dich dau tien");
		transactionReport.clickToDynamicTransactionInTransactionOrderStatus(driver, "0","com.VCB:id/tvContent");
		
		log.info("TC_05_Step_09: Xac nhan hien thi Title 'Chi tiet giao dich'");
		verifyEquals(transactionReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Chi tiết giao dịch");
		
		log.info("TC_05_Step_10: Xac nhan hien thi thoi gian giao dich");
		verifyTrue(transactionReport.isTextDisplayedInListTextElements(driver, convertDateTimeIgnoreHHmmss(transactionDate), "com.VCB:id/tvContent"));
		
		log.info("TC_05_Step_11: Xac nhan hien thi so tai khoan giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Tài khoản/thẻ trích nợ"),  Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_VND);
		
		log.info("TC_05_Step_12: Xac nhan hien thi so dien thoai duoc nap");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Tài khoản ghi có"), savingAccount);
		
		log.info("TC_05_Step_13: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicBottomMenu(driver, "com.VCB:id/ivTitleLeft");
		
		log.info("TC_05_Step_14: An nut back ve man hinh menu");
		transactionReport.clickToDynamicBottomMenu(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);
		
		log.info("TC_05_Step_15: Mo tab Home");
		home.clickToDynamicBottomMenu(driver, "com.VCB:id/menu_1");
	}
	
	@Parameters ({"otp"})
	@Test
	public void TC_06_MoTaiKhoanTietKiem_TaiKhoanNguon_USD (String otp) {
		
		home = PageFactoryManager.getHomePageObject(driver);
		
		log.info("TC_06_1_Click Mo tai khoan tiet kiem");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Mở tài khoản tiết kiệm");
		savingOnline = PageFactoryManager.getSavingOnlinePageObject(driver);

		log.info("TC_06_2_Chon so tai khoan");
		savingOnline.clickToDynamicDropDown(driver, "Số tài khoản");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_USD);
		
		log.info("TC_06_3_Chon ky han gui");
		savingOnline.clickToDynamicDropDownListTextViewByHeader(driver, "Thông tin giao dịch", "1");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Data.TEXT.TERM);
		
		log.info("TC_06_4_Nhap so tien gui");
		savingOnline.inputToDynamicInputBoxByHeader(driver, Auto_Saving_Data.TEXT.INPUT_USD, "Thông tin giao dịch", "2");
		
		log.info("TC_06_5_Chon hinh thuc chuyen tien");
		savingOnline.clickToDynamicDropDownListTextViewByHeader(driver, "Thông tin giao dịch", "3");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Data.TEXT.FORM_OF_PAYMENT);
		
		log.info("TC_06_6_Chon dong y tuan thu cam ket");
		savingOnline.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");
		
		log.info("TC_06_7_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_06_8_Kiem tra man hinh xac nhan thong tin");

		log.info("TC_06_8_1_Kiem tra tai khoan nguon");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"),  Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_USD);

		log.info("TC_06_8_2_Kiem tra ky han gui");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Kỳ hạn gửi"), Auto_Saving_Data.TEXT.TERM);

		log.info("TC_06_8_3_Kiem tra lai suat");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Lãi suất"), "4.5%/Năm");

		log.info("TC_06_8_4_Kiem tra so tien gui");
		verifyEquals(savingOnline.getDynamicTextDetailByIDOrPopup(driver,"com.VCB:id/tvSoTien"), Auto_Saving_Data.TEXT.INPUT_USD+".00 USD");
		
		log.info("TC_06_8_5_Kiem tra hinh thuc tra lai");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Hình thức trả lãi"), Auto_Saving_Data.TEXT.FORM_OF_PAYMENT);
		
		log.info("TC_06_09_Chon phuong thuc xac thuc");
		savingOnline.scrollDownToText(driver, "Chọn phương thức xác thực");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		transferFee = convertAvailableBalanceCurrentcyToLong(savingOnline.getDynamicTextInTransactionDetail(driver, "SMS OTP"));
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");
		
		log.info("TC_06_10_Kiem tra so tien phi");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), addCommasToLong(transferFee + "") + " VND");
		
		log.info("TC_06_11_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, "Tiếp tục");
		
		savingOnline.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		savingOnline.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_06_12_Kiem tra man hinh Giao dich thanh cong");
		log.info("TC_06_12_1_Kiem tra Giao dich thanh cong");
		verifyTrue(savingOnline.isDynamicMessageAndLabelTextDisplayed(driver, SavingOnline_Data.SUCCESS_TRANSACTION));

		log.info("TC_06_12_6_Lay ma giao dich");
		savingAccount = savingOnline.getDynamicTextInTransactionDetail(driver, "Số tài khoản tiết kiệm");

		log.info("TC_06_13_Click nut Home ve man hinh chinh");
		savingOnline.clickToDynamicBottomMenu(driver, "com.VCB:id/ivHome");

	}
	
	@Parameters ({"pass"})
	@Test
	public void TC_07_TietKiemTuDong_TaiKhoanNguon_USD_MK(String pass) {
		
		home = PageFactoryManager.getHomePageObject(driver);
		
		log.info("TC_07_Step_01: Keo xuong va click vao phan 'Tiet kiem tu dong'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Tiết kiệm tự động");
		autoSaving = PageFactoryManager.getAutoSavingPageObject(driver);
		
		log.info("TC_07_Step_02: Chon tai khoan nguon USD");
		autoSaving.clickToTextViewCombobox(driver, "com.VCB:id/tvContent");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, "0019967190");
		
		log.info("TC_07_Step_03: Chon tai khoan tiet kiem");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, "Tài khoản tiết kiệm");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, savingAccount);
		
		log.info("TC_07_Step_04: Chon ngay ket thuc");
		
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, "Ngày kết thúc");
		autoSaving.clickToDynamicDateInDateTimePicker(driver, getForWardDay(3));
		autoSaving.clickToDynamicButton(driver, "OK");
		
		log.info("TC_07_Step_05: Nhap so tien chuyen");
		autoSaving.inputToDynamicInputBox(driver,Auto_Saving_Data.TEXT.INPUT_USD, "Số tiền chuyển");
		
		log.info("TC_07_Step_06: An Tiep tuc");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");
		
		log.info("TC_07_Step_07: Hien thi man hinh xac nhan thong tin");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Xác nhận thông tin");
		tranferMoney = autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvQuyDoi");
		
		log.info("TC_07_Step_08: Chon phương thuc xac thuc");
		autoSaving.clickToTextViewCombobox(driver, "com.VCB:id/tvptxt");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");

		log.info("TC_07_Step_09: An nut 'Tiep tuc'");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_07_Step_10: Nhap ki tu vao o nhap mat khau");
		autoSaving.inputIntoEditTextByID(driver, pass, "com.VCB:id/pin");

		log.info("TC_07_Step_11: An tiep button 'Tiep tuc'");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_07_Step_12: Hien thi man hinh thong bao giao dich thanh cong");
		transactionID = autoSaving.getDynamicTextByLabel(driver, "Mã giao dịch");
		
		log.info("TC_07_Step_13: An thuc hien giao dich moi");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");
		
		log.info("TC_07_Step_14: Click back ve man hinh chinh");
		autoSaving.clickToDynamicBottomMenu(driver, "com.VCB:id/ivTitleLeft");
	}

	@Test
	public void TC_08_TietKiemTuDong_TaiKhoanNguon_USD_MK_BaoCaoGiaoDich() {
		
		log.info("TC_08_Step_01: Mo tab Menu");
		home.clickToDynamicBottomMenu(driver, "com.VCB:id/menu_5");
		
		log.info("TC_08_Step_02: Mo sub-menu 'Bao cao giao dich");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");
		transactionReport = PageFactoryManager.getTransactionReportPageObject(driver);
		
		log.info("TC_08_Step_03: An vao Dropdown 'Tat ca cac loai giao dich");
		transactionReport.clickToTextViewCombobox(driver, "com.VCB:id/tvSelectTransType");
		
		log.info("TC_08_Step_04: Chon 'Tiet kiem tu dong'");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, "Tiết kiệm tự động");
		
		log.info("TC_08_Step_05: An vao Dropdown 'Chon tai khoan/the");
		transactionReport.clickToTextViewCombobox(driver, "com.VCB:id/tvSelectAcc");
		
		log.info("TC_08_Step_06: Chon tai khoan vua thuc hien giao dich");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_USD);
		
		log.info("TC_08_Step_07: An nut Tim kiem");
		transactionReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");
		
		log.info("TC_08_Step_08: An vao giao dich dau tien");
		transactionReport.clickToDynamicTransactionInTransactionOrderStatus(driver, "0","com.VCB:id/tvContent");
		
		log.info("TC_08_Step_09: Xac nhan hien thi Title 'Chi tiet giao dich'");
		verifyEquals(transactionReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Chi tiết giao dịch");
		
		log.info("TC_08_Step_10: Xac nhan hien thi dung ma giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Số lệnh giao dịch"), transactionID);
		
		log.info("TC_08_Step_11: Xac nhan hien thi so tai khoan giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Tài khoản/thẻ trích nợ"), Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_USD);
		
		log.info("TC_08_Step_12: Xac nhan hien thi so dien thoai duoc nap");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Tài khoản ghi có"), savingAccount);
		
		log.info("TC_08_Step_13: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicBottomMenu(driver, "com.VCB:id/ivTitleLeft");
		
		log.info("TC_08_Step_14: An nut back ve man hinh menu");
		transactionReport.clickToDynamicBottomMenu(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);
		
		log.info("TC_08_Step_15: Mo tab Home");
		home.clickToDynamicBottomMenu(driver, "com.VCB:id/menu_1");
	}
	
	@Test
	public void TC_09_HuyTietKiemTuDong_TaiKhoanNguon_USD_MK() {
		
		log.info("TC_09_Step_01: Keo xuong va click vao phan 'Huy Tiet kiem tu dong'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Huỷ tiết kiệm tự động");
		autoSaving = PageFactoryManager.getAutoSavingPageObject(driver);
		
		log.info("TC_09_Step_02: Chon tai khoan nguon");
		autoSaving.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/thongTinTaiKhoanThanhToan");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_USD);
		
		log.info("TC_09_Step_03: Chon tai khoan tiet kiem");
		autoSaving.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/thongTinTaiKhoanTietKiem");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, savingAccount);
		
		log.info("TC_09_Step_04: An nut Tim kiem");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");
		
		log.info("TC_09_Step_05: Xac nhan hien thi tai khoan nguon");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTaiKhoanNguon"), Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_USD);
		
		log.info("TC_09_Step_06: Xac nhan hien thi tai khoan tiet kiem");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTaiKhoanTietKiem"), savingAccount);
		
		log.info("TC_09_Step_07: Xac nhan hien thi so tien chuyen");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvSoTienChuyen"), tranferMoney);
		
		log.info("TC_09_Step_09: An nut Huy");
		autoSaving.clickToTextViewCombobox(driver, "com.VCB:id/tvHuy");
		
		log.info("TC_09_Step_10: An nut Dong y");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btCancel");
		
		log.info("TC_09_Step_11: An nut Dong ");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
		
		log.info("TC_09_Step_12: An nut back ve man hinh menu");
		autoSaving.clickToDynamicBottomMenu(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);		
	}
	
	@Test
	public void TC_10_HuyTietKiemTuDong_TaiKhoanNguon_USD_BaoCaoGiaoDich() {
		
		log.info("TC_10_Step_01: Mo tab Menu");
		home.clickToDynamicBottomMenu(driver, "com.VCB:id/menu_5");
		
		log.info("TC_10_Step_02: Mo sub-menu 'Bao cao giao dich");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");
		transactionReport = PageFactoryManager.getTransactionReportPageObject(driver);
		
		log.info("TC_10_Step_03: An vao Dropdown 'Tat ca cac loai giao dich");
		transactionReport.clickToTextViewCombobox(driver, "com.VCB:id/tvSelectTransType");
		
		log.info("TC_10_Step_04: Chon 'Huy tiet kiem tu dong'");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, "Hủy tiết kiệm tự động");
		
		log.info("TC_10_Step_05: An vao Dropdown 'Chon tai khoan/the");
		transactionReport.clickToTextViewCombobox(driver, "com.VCB:id/tvSelectAcc");
		
		log.info("TC_10_Step_06: Chon tai khoan vua thuc hien giao dich");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_USD);
		
		log.info("TC_10_Step_07: An nut Tim kiem");
		transactionReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");
		
		log.info("TC_10_Step_08: An vao giao dich dau tien");
		transactionReport.clickToDynamicTransactionInTransactionOrderStatus(driver, "0","com.VCB:id/tvContent");
		
		log.info("TC_10_Step_09: Xac nhan hien thi Title 'Chi tiet giao dich'");
		verifyEquals(transactionReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Chi tiết giao dịch");
		
		log.info("TC_10_Step_10: Xac nhan hien thi thoi gian giao dich");
		verifyTrue(transactionReport.isTextDisplayedInListTextElements(driver, convertDateTimeIgnoreHHmmss(transactionDate), "com.VCB:id/tvContent"));
		
		log.info("TC_10_Step_11: Xac nhan hien thi so tai khoan giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Tài khoản/thẻ trích nợ"), Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_USD);
		
		log.info("TC_10_Step_12: Xac nhan hien thi so dien thoai duoc nap");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Tài khoản ghi có"), savingAccount);
		
		log.info("TC_10_Step_13: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicBottomMenu(driver, "com.VCB:id/ivTitleLeft");
		
		log.info("TC_10_Step_14: An nut back ve man hinh menu");
		transactionReport.clickToDynamicBottomMenu(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);
		
		log.info("TC_10_Step_15: Mo tab Home");
		home.clickToDynamicBottomMenu(driver, "com.VCB:id/menu_1");
	}
	
	@Parameters ({"otp"})
	@Test
	public void TC_11_MoTaiKhoanTietKiem_TaiKhoanNguon_EUR (String otp) {
		
		home = PageFactoryManager.getHomePageObject(driver);
		
		log.info("TC_11_1_Click Mo tai khoan tiet kiem");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Mở tài khoản tiết kiệm");
		savingOnline = PageFactoryManager.getSavingOnlinePageObject(driver);

		log.info("TC_11_2_Chon so tai khoan");
		savingOnline.clickToDynamicDropDown(driver, "Số tài khoản");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_EUR);
		
		log.info("TC_11_3_Chon ky han gui");
		savingOnline.clickToDynamicDropDownListTextViewByHeader(driver, "Thông tin giao dịch", "1");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Data.TEXT.TERM);
		
		log.info("TC_11_4_Nhap so tien gui");
		savingOnline.inputToDynamicInputBoxByHeader(driver, Auto_Saving_Data.TEXT.INPUT_EUR, "Thông tin giao dịch", "2");
		
		log.info("TC_11_5_Chon hinh thuc chuyen tien");
		savingOnline.clickToDynamicDropDownListTextViewByHeader(driver, "Thông tin giao dịch", "3");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Data.TEXT.FORM_OF_PAYMENT);
		
		log.info("TC_11_6_Chon dong y tuan thu cam ket");
		savingOnline.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");
		
		log.info("TC_11_7_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_11_8_Kiem tra man hinh xac nhan thong tin");

		log.info("TC_11_8_1_Kiem tra tai khoan nguon");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"),  Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_EUR);

		log.info("TC_11_8_2_Kiem tra ky han gui");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Kỳ hạn gửi"), Auto_Saving_Data.TEXT.TERM);

		log.info("TC_11_8_3_Kiem tra lai suat");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Lãi suất"), "4.5%/Năm");

		log.info("TC_11_8_4_Kiem tra so tien gui");
		verifyEquals(savingOnline.getDynamicTextDetailByIDOrPopup(driver,"com.VCB:id/tvSoTien"), Auto_Saving_Data.TEXT.INPUT_EUR+".00 EUR");
		
		log.info("TC_11_8_5_Kiem tra hinh thuc tra lai");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Hình thức trả lãi"), Auto_Saving_Data.TEXT.FORM_OF_PAYMENT);
		
		log.info("TC_11_09_Chon phuong thuc xac thuc");
		savingOnline.scrollDownToText(driver, "Chọn phương thức xác thực");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		transferFee = convertAvailableBalanceCurrentcyToLong(savingOnline.getDynamicTextInTransactionDetail(driver, "SMS OTP"));
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");
		
		log.info("TC_11_10_Kiem tra so tien phi");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), addCommasToLong(transferFee + "") + " VND");
		
		log.info("TC_11_11_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, "Tiếp tục");
		
		savingOnline.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		savingOnline.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_11_12_Kiem tra man hinh Giao dich thanh cong");
		log.info("TC_11_12_1_Kiem tra Giao dich thanh cong");
		verifyTrue(savingOnline.isDynamicMessageAndLabelTextDisplayed(driver, SavingOnline_Data.SUCCESS_TRANSACTION));

		log.info("TC_11_12_6_Lay ma giao dich");
		savingAccount = savingOnline.getDynamicTextInTransactionDetail(driver, "Số tài khoản tiết kiệm");

		log.info("TC_11_13_Click nut Home ve man hinh chinh");
		savingOnline.clickToDynamicBottomMenu(driver, "com.VCB:id/ivHome");

	}
	
	@Parameters ({"pass"})
	@Test
	public void TC_12_TietKiemTuDong_TaiKhoanNguon_EUR_MK(String pass) {
		
		home = PageFactoryManager.getHomePageObject(driver);
		
		log.info("TC_12_Step_01: Keo xuong va click vao phan 'Tiet kiem tu dong'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Tiết kiệm tự động");
		autoSaving = PageFactoryManager.getAutoSavingPageObject(driver);
		
		log.info("TC_12_Step_02: Chon tai khoan nguon EUR");
		autoSaving.clickToTextViewCombobox(driver, "com.VCB:id/tvContent");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, "0019961175");
		
		log.info("TC_12_Step_03: Chon tai khoan tiet kiem");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, "Tài khoản tiết kiệm");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, savingAccount);
		
		log.info("TC_12_Step_04: Chon ngay ket thuc");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, "Ngày kết thúc");
		autoSaving.clickToDynamicDateInDateTimePicker(driver, getForWardDay(3));
		autoSaving.clickToDynamicButton(driver, "OK");
		
		log.info("TC_12_Step_05: Nhap so tien chuyen");
		autoSaving.inputToDynamicInputBox(driver,Auto_Saving_Data.TEXT.INPUT_EUR, "Số tiền chuyển");
		
		log.info("TC_12_Step_06: An Tiep tuc");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");
		
		log.info("TC_12_Step_07: Hien thi man hinh xac nhan thong tin");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Xác nhận thông tin");
		tranferMoney = autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvQuyDoi");
		
		log.info("TC_12_Step_08: Chon phương thuc xac thuc");
		autoSaving.clickToTextViewCombobox(driver, "com.VCB:id/tvptxt");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");

		log.info("TC_12_Step_09: An nut 'Tiep tuc'");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_12_Step_10: Nhap ki tu vao o nhap mat khau");
		autoSaving.inputIntoEditTextByID(driver, pass, "com.VCB:id/pin");

		log.info("TC_12_Step_11: An tiep button 'Tiep tuc'");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_12_Step_12: Hien thi man hinh thong bao giao dich thanh cong");
		transactionID = autoSaving.getDynamicTextByLabel(driver, "Mã giao dịch");
		
		log.info("TC_12_Step_13: An thuc hien giao dich moi");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");
		
		log.info("TC_12_Step_14: Click back ve man hinh chinh");
		autoSaving.clickToDynamicBottomMenu(driver, "com.VCB:id/ivTitleLeft");
	}

	@Test
	public void TC_13_TietKiemTuDong_TaiKhoanNguon_EUR_MK_BaoCaoGiaoDich() {
		
		log.info("TC_13_Step_01: Mo tab Menu");
		home.clickToDynamicBottomMenu(driver, "com.VCB:id/menu_5");
		
		log.info("TC_13_Step_02: Mo sub-menu 'Bao cao giao dich");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");
		transactionReport = PageFactoryManager.getTransactionReportPageObject(driver);
		
		log.info("TC_13_Step_03: An vao Dropdown 'Tat ca cac loai giao dich");
		transactionReport.clickToTextViewCombobox(driver, "com.VCB:id/tvSelectTransType");
		
		log.info("TC_13_Step_04: Chon 'Tiet kiem tu dong'");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, "Tiết kiệm tự động");
		
		log.info("TC_13_Step_05: An vao Dropdown 'Chon tai khoan/the");
		transactionReport.clickToTextViewCombobox(driver, "com.VCB:id/tvSelectAcc");
		
		log.info("TC_13_Step_06: Chon tai khoan vua thuc hien giao dich");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_EUR);
		
		log.info("TC_13_Step_07: An nut Tim kiem");
		transactionReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");
		
		log.info("TC_13_Step_08: An vao giao dich dau tien");
		transactionReport.clickToDynamicTransactionInTransactionOrderStatus(driver, "0","com.VCB:id/tvContent");
		
		log.info("TC_13_Step_09: Xac nhan hien thi Title 'Chi tiet giao dich'");
		verifyEquals(transactionReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Chi tiết giao dịch");
		
		log.info("TC_13_Step_10: Xac nhan hien thi dung ma giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Số lệnh giao dịch"), transactionID);
		
		log.info("TC_13_Step_11: Xac nhan hien thi so tai khoan giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Tài khoản/thẻ trích nợ"), Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_EUR);
		
		log.info("TC_13_Step_12: Xac nhan hien thi so dien thoai duoc nap");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Tài khoản ghi có"), savingAccount);
		
		log.info("TC_13_Step_13: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicBottomMenu(driver, "com.VCB:id/ivTitleLeft");
		
		log.info("TC_13_Step_14: An nut back ve man hinh menu");
		transactionReport.clickToDynamicBottomMenu(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);
		
		log.info("TC_13_Step_15: Mo tab Home");
		home.clickToDynamicBottomMenu(driver, "com.VCB:id/menu_1");
	}
	
	@Test
	public void TC_14_HuyTietKiemTuDong_TaiKhoanNguon_EUR_MK() {
		
		log.info("TC_14_Step_01: Keo xuong va click vao phan 'Huy Tiet kiem tu dong'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Huỷ tiết kiệm tự động");
		autoSaving = PageFactoryManager.getAutoSavingPageObject(driver);
		
		log.info("TC_14_Step_02: Chon tai khoan nguon");
		autoSaving.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/thongTinTaiKhoanThanhToan");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_EUR);
		
		log.info("TC_14_Step_03: Chon tai khoan tiet kiem");
		autoSaving.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/thongTinTaiKhoanTietKiem");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, savingAccount);
		
		log.info("TC_14_Step_04: An nut Tim kiem");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");
		
		log.info("TC_14_Step_05: Xac nhan hien thi tai khoan nguon");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTaiKhoanNguon"), Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_EUR);
		
		log.info("TC_14_Step_06: Xac nhan hien thi tai khoan tiet kiem");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTaiKhoanTietKiem"), savingAccount);
		
		log.info("TC_14_Step_07: Xac nhan hien thi so tien chuyen");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvSoTienChuyen"), tranferMoney);
		
		log.info("TC_14_Step_09: An nut Huy");
		autoSaving.clickToTextViewCombobox(driver, "com.VCB:id/tvHuy");
		
		log.info("TC_14_Step_10: An nut Dong y");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btCancel");
		
		log.info("TC_14_Step_11: An nut Dong ");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
		
		log.info("TC_14_Step_11: An nut back ve man hinh menu");
		autoSaving.clickToDynamicBottomMenu(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);		
	}
	
	@Test
	public void TC_15_HuyTietKiemTuDong_TaiKhoanNguon_EUR_BaoCaoGiaoDich() {
		
		log.info("TC_15_Step_01: Mo tab Menu");
		home.clickToDynamicBottomMenu(driver, "com.VCB:id/menu_5");
		
		log.info("TC_15_Step_02: Mo sub-menu 'Bao cao giao dich");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");
		transactionReport = PageFactoryManager.getTransactionReportPageObject(driver);
		
		log.info("TC_15_Step_03: An vao Dropdown 'Tat ca cac loai giao dich");
		transactionReport.clickToTextViewCombobox(driver, "com.VCB:id/tvSelectTransType");
		
		log.info("TC_15_Step_04: Chon 'Huy tiet kiem tu dong'");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, "Hủy tiết kiệm tự động");
		
		log.info("TC_15_Step_05: An vao Dropdown 'Chon tai khoan/the");
		transactionReport.clickToTextViewCombobox(driver, "com.VCB:id/tvSelectAcc");
		
		log.info("TC_15_Step_06: Chon tai khoan vua thuc hien giao dich");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_EUR);
		
		log.info("TC_15_Step_07: An nut Tim kiem");
		transactionReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");
		
		log.info("TC_15_Step_08: An vao giao dich dau tien");
		transactionReport.clickToDynamicTransactionInTransactionOrderStatus(driver, "0","com.VCB:id/tvContent");
		
		log.info("TC_15_Step_09: Xac nhan hien thi Title 'Chi tiet giao dich'");
		verifyEquals(transactionReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Chi tiết giao dịch");
		
		log.info("TC_15_Step_11: Xac nhan hien thi so tai khoan giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Tài khoản/thẻ trích nợ"), Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_EUR);
		
		log.info("TC_15_Step_12: Xac nhan hien thi so dien thoai duoc nap");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Tài khoản ghi có"), savingAccount);
		
		log.info("TC_15_Step_13: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicBottomMenu(driver, "com.VCB:id/ivTitleLeft");
		
		log.info("TC_15_Step_14: An nut back ve man hinh menu");
		transactionReport.clickToDynamicBottomMenu(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);
		
		log.info("TC_15_Step_15: Mo tab Home");
		home.clickToDynamicBottomMenu(driver, "com.VCB:id/menu_1");
	}
	
	@AfterClass(alwaysRun = true)
	public void afterClass() {
		service.stop();
	}

}
