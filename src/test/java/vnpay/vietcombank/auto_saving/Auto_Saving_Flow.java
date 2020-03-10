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
import vietcombank_test_data.Auto_Saving_Data;

public class Auto_Saving_Flow extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject home;
	private AutoSavingPageObject autoSaving;
	private TransactionReportPageObject transactionReport;
	
	private String transactionID;
	private String savingAccount;
	private String tranferMoney;
	private String transactionDate;

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
	public void TC_01_TietKiemTuDong_TaiKhoanNguon_VND_OTP(String otp) {
		
		home = PageFactoryManager.getHomePageObject(driver);
		
		log.info("TC_01_Step_01: Keo xuong va click vao phan 'Tiet kiem tu dong'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Tiết kiệm tự động");
		autoSaving = PageFactoryManager.getAutoSavingPageObject(driver);
		
		log.info("TC_01_Step_02: Chon tai khoan nguon VND");
		autoSaving.clickToTextViewCombobox(driver, "com.VCB:id/tvContent");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_VND);
		
		log.info("TC_01_Step_03: Chon tai khoan tiet kiem");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, "Tài khoản tiết kiệm");
		savingAccount = autoSaving.getFirstOptionDataInDropdown("com.VCB:id/tvContent1");
		autoSaving.clickToFirstOptionInDropdown("com.VCB:id/tvContent1");
		
		log.info("TC_01_Step_04: Chon ngay ket thuc");
		
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, "Ngày kết thúc");
		autoSaving.clickToDynamicDateInDateTimePicker(driver, getForWardDay(3));
		autoSaving.clickToDynamicButton(driver, "OK");
		
		log.info("TC_01_Step_05: Nhap so tien chuyen");
		autoSaving.inputToDynamicInputBox(driver,Auto_Saving_Data.INPUT_MONEY.INPUT_VND, "Số tiền chuyển");
		
		log.info("TC_01_Step_06: An Tiep tuc");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");
		
		log.info("TC_01_Step_07: Hien thi man hinh xac nhan thong tin");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Xác nhận thông tin");
		
		log.info("TC_01_Step_08: Chon phương thuc xac thuc");
		autoSaving.clickToTextViewCombobox(driver, "com.VCB:id/tvptxt");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");
		
		log.info("TC_01_Step_09: An Tiep tuc");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");
		
		log.info("TC_01_Step_10: Nhap du ki tu vao o nhap OTP");
		autoSaving.inputToDynamicOtp(driver, otp, "Tiếp tục");;

		log.info("TC_01_Step_11: An tiep button 'Tiep tuc'");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_12: Hien thi man hinh thong bao giao dich thanh cong");
		transactionDate = autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTime");
		transactionID = autoSaving.getDynamicTextByLabel(driver, "Mã giao dịch");
		
		log.info("TC_01_Step_13: An thuc hien giao dich moi");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");
		
		log.info("TC_01_Step_14: Click back ve man hinh chinh");
		autoSaving.clickToDynamicBottomMenu(driver, "com.VCB:id/ivTitleLeft");
	}

	@Test
	public void TC_02_TietKiemTuDong_TaiKhoanNguon_VND_OTP_BaoCaoGiaoDich() {
		
		log.info("TC_02_Step_01: Mo tab Menu");
		home.clickToDynamicBottomMenu(driver, "com.VCB:id/menu_5");
		
		log.info("TC_03_Step_02: Mo sub-menu 'Bao cao giao dich");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");
		transactionReport = PageFactoryManager.getTransactionReportPageObject(driver);
		
		log.info("TC_02_Step_03: An vao Dropdown 'Tat ca cac loai giao dich");
		transactionReport.clickToTextViewCombobox(driver, "com.VCB:id/tvSelectTransType");
		
		log.info("TC_02_Step_04: Chon 'Tiet kiem tu dong'");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, "Tiết kiệm tự động");
		
		log.info("TC_02_Step_05: An vao Dropdown 'Chon tai khoan/the");
		transactionReport.clickToTextViewCombobox(driver, "com.VCB:id/tvSelectAcc");
		
		log.info("TC_02_Step_06: Chon tai khoan vua thuc hien giao dich");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_VND);
		
		log.info("TC_02_Step_07: An nut Tim kiem");
		transactionReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");
		
		log.info("TC_02_Step_08: An vao giao dich dau tien");
		transactionReport.clickToDynamicTransactionInTransactionOrderStatus(driver, "0","com.VCB:id/tvContent");
		
		log.info("TC_02_Step_09: Xac nhan hien thi Title 'Chi tiet giao dich'");
		verifyEquals(transactionReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Chi tiết giao dịch");
		
		log.info("TC_02_Step_10: Xac nhan hien thi thoi gian giao dich");
		verifyTrue(transactionReport.isTextDisplayedInListTextElements(driver, convertDateTimeIgnoreHHmmss(transactionDate), "com.VCB:id/tvContent"));
		
		log.info("TC_02_Step_11: Xac nhan hien thi dung ma giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Số lệnh giao dịch"), transactionID);
		
		log.info("TC_02_Step_12: Xac nhan hien thi so tai khoan giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Tài khoản/thẻ trích nợ"), Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_VND);
		
		log.info("TC_02_Step_13: Xac nhan hien thi so dien thoai duoc nap");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Tài khoản ghi có"), savingAccount);
		
		log.info("TC_02_Step_14: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicBottomMenu(driver, "com.VCB:id/ivTitleLeft");
		
		log.info("TC_02_Step_15: An nut back ve man hinh menu");
		transactionReport.clickToDynamicBottomMenu(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);
		
		log.info("TC_02_Step_16: Mo tab Home");
		home.clickToDynamicBottomMenu(driver, "com.VCB:id/menu_1");
	}
	
	@Test
	public void TC_03_HuyTietKiemTuDong_TaiKhoanNguon_VND() {
		
		log.info("TC_03_Step_01: Keo xuong va click vao phan 'Huy Tiet kiem tu dong'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Huỷ tiết kiệm tự động");
		autoSaving = PageFactoryManager.getAutoSavingPageObject(driver);
		
		log.info("TC_03_Step_02: Chon tai khoan nguon");
		autoSaving.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/thongTinTaiKhoanThanhToan");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_VND);
		
		log.info("TC_03_Step_03: Chon tai khoan tiet kiem");
		autoSaving.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/thongTinTaiKhoanTietKiem");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, savingAccount);
		
		log.info("TC_03_Step_04: An nut Tim kiem");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");
		
		log.info("TC_03_Step_05: Xac nhan hien thi tai khoan nguon");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTaiKhoanNguon"), Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_VND);
		
		log.info("TC_03_Step_06: Xac nhan hien thi tai khoan tiet kiem");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTaiKhoanTietKiem"), savingAccount);
		
		log.info("TC_03_Step_07: Xac nhan hien thi so tien chuyen");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvSoTienChuyen"), Auto_Saving_Data.INPUT_MONEY.INPUT_VND + " VND");
		
		log.info("TC_03_Step_09: An nut Huy");
		autoSaving.clickToTextViewCombobox(driver, "com.VCB:id/tvHuy");
		
		log.info("TC_03_Step_10: An nut Dong y");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btCancel");
		
		log.info("TC_03_Step_11: An nut Dong ");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
		
		log.info("TC_03_Step_11: An nut back ve man hinh menu");
		autoSaving.clickToDynamicBottomMenu(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);
		
	}
	
	@Test
	public void TC_04_HuyTietKiemTuDong_TaiKhoanNguon_VND_BaoCaoGiaoDich() {
		
		log.info("TC_04_Step_01: Mo tab Menu");
		home.clickToDynamicBottomMenu(driver, "com.VCB:id/menu_5");
		
		log.info("TC_04_Step_02: Mo sub-menu 'Bao cao giao dich");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");
		transactionReport = PageFactoryManager.getTransactionReportPageObject(driver);
		
		log.info("TC_04_Step_03: An vao Dropdown 'Tat ca cac loai giao dich");
		transactionReport.clickToTextViewCombobox(driver, "com.VCB:id/tvSelectTransType");
		
		log.info("TC_04_Step_04: Chon 'Huy tiet kiem tu dong'");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, "Hủy tiết kiệm tự động");
		
		log.info("TC_04_Step_05: An vao Dropdown 'Chon tai khoan/the");
		transactionReport.clickToTextViewCombobox(driver, "com.VCB:id/tvSelectAcc");
		
		log.info("TC_04_Step_06: Chon tai khoan vua thuc hien giao dich");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_VND);
		
		log.info("TC_04_Step_07: An nut Tim kiem");
		transactionReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");
		
		log.info("TC_04_Step_08: An vao giao dich dau tien");
		transactionReport.clickToDynamicTransactionInTransactionOrderStatus(driver, "0","com.VCB:id/tvContent");
		
		log.info("TC_04_Step_09: Xac nhan hien thi Title 'Chi tiet giao dich'");
		verifyEquals(transactionReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Chi tiết giao dịch");
		
		log.info("TC_04_Step_10: Xac nhan hien thi thoi gian giao dich");
		verifyTrue(transactionReport.isTextDisplayedInListTextElements(driver, convertDateTimeIgnoreHHmmss(transactionDate), "com.VCB:id/tvContent"));
		
		log.info("TC_04_Step_11: Xac nhan hien thi so tai khoan giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Tài khoản/thẻ trích nợ"), Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_VND);
		
		log.info("TC_04_Step_12: Xac nhan hien thi so dien thoai duoc nap");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Tài khoản ghi có"), savingAccount);
		
		log.info("TC_04_Step_13: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicBottomMenu(driver, "com.VCB:id/ivTitleLeft");
		
		log.info("TC_04_Step_14: An nut back ve man hinh menu");
		transactionReport.clickToDynamicBottomMenu(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);
		
		log.info("TC_04_Step_15: Mo tab Home");
		home.clickToDynamicBottomMenu(driver, "com.VCB:id/menu_1");
	}
	
	@Parameters ({"otp"})
	@Test
	public void TC_05_TietKiemTuDong_TaiKhoanNguon_USD_OTP(String otp) {
		
		home = PageFactoryManager.getHomePageObject(driver);
		
		log.info("TC_05_Step_01: Keo xuong va click vao phan 'Tiet kiem tu dong'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Tiết kiệm tự động");
		autoSaving = PageFactoryManager.getAutoSavingPageObject(driver);
		
		log.info("TC_05_Step_02: Chon tai khoan nguon USD");
		autoSaving.clickToTextViewCombobox(driver, "com.VCB:id/tvContent");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_USD);
		
		log.info("TC_05_Step_03: Chon tai khoan tiet kiem");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, "Tài khoản tiết kiệm");
		savingAccount = autoSaving.getFirstOptionDataInDropdown("com.VCB:id/tvContent1");
		autoSaving.clickToFirstOptionInDropdown("com.VCB:id/tvContent1");
		
		log.info("TC_05_Step_04: Chon ngay ket thuc");
		
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, "Ngày kết thúc");
		autoSaving.clickToDynamicDateInDateTimePicker(driver, getForWardDay(3));
		autoSaving.clickToDynamicButton(driver, "OK");
		
		log.info("TC_05_Step_05: Nhap so tien chuyen");
		autoSaving.inputToDynamicInputBox(driver,Auto_Saving_Data.INPUT_MONEY.INPUT_USD, "Số tiền chuyển");
		
		log.info("TC_05_Step_06: An Tiep tuc");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");
		
		log.info("TC_05_Step_07: Hien thi man hinh xac nhan thong tin");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Xác nhận thông tin");
		tranferMoney = autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvQuyDoi");
		
		log.info("TC_05_Step_08: Chon phương thuc xac thuc");
		autoSaving.clickToTextViewCombobox(driver, "com.VCB:id/tvptxt");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");
		
		log.info("TC_05_Step_09: An Tiep tuc");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");
		
		log.info("TC_05_Step_10: Nhap du ki tu vao o nhap OTP");
		autoSaving.inputToDynamicOtp(driver, otp, "Tiếp tục");;

		log.info("TC_05_Step_11: An tiep button 'Tiep tuc'");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_05_Step_12: Hien thi man hinh thong bao giao dich thanh cong");
		transactionID = autoSaving.getDynamicTextByLabel(driver, "Mã giao dịch");
		
		log.info("TC_05_Step_13: An thuc hien giao dich moi");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");
		
		log.info("TC_05_Step_14: Click back ve man hinh chinh");
		autoSaving.clickToDynamicBottomMenu(driver, "com.VCB:id/ivTitleLeft");
	}

	@Test
	public void TC_06_TietKiemTuDong_TaiKhoanNguon_USD_OTP_BaoCaoGiaoDich() {
		
		log.info("TC_06_Step_01: Mo tab Menu");
		home.clickToDynamicBottomMenu(driver, "com.VCB:id/menu_5");
		
		log.info("TC_06_Step_02: Mo sub-menu 'Bao cao giao dich");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");
		transactionReport = PageFactoryManager.getTransactionReportPageObject(driver);
		
		log.info("TC_06_Step_03: An vao Dropdown 'Tat ca cac loai giao dich");
		transactionReport.clickToTextViewCombobox(driver, "com.VCB:id/tvSelectTransType");
		
		log.info("TC_06_Step_04: Chon 'Tiet kiem tu dong'");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, "Tiết kiệm tự động");
		
		log.info("TC_06_Step_05: An vao Dropdown 'Chon tai khoan/the");
		transactionReport.clickToTextViewCombobox(driver, "com.VCB:id/tvSelectAcc");
		
		log.info("TC_06_Step_06: Chon tai khoan vua thuc hien giao dich");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_USD);
		
		log.info("TC_06_Step_07: An nut Tim kiem");
		transactionReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");
		
		log.info("TC_06_Step_08: An vao giao dich dau tien");
		transactionReport.clickToDynamicTransactionInTransactionOrderStatus(driver, "0","com.VCB:id/tvContent");
		
		log.info("TC_06_Step_09: Xac nhan hien thi Title 'Chi tiet giao dich'");
		verifyEquals(transactionReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Chi tiết giao dịch");
		
		log.info("TC_06_Step_10: Xac nhan hien thi dung ma giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Số lệnh giao dịch"), transactionID);
		
		log.info("TC_06_Step_11: Xac nhan hien thi so tai khoan giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Tài khoản/thẻ trích nợ"), Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_USD);
		
		log.info("TC_06_Step_12: Xac nhan hien thi so dien thoai duoc nap");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Tài khoản ghi có"), savingAccount);
		
		log.info("TC_06_Step_13: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicBottomMenu(driver, "com.VCB:id/ivTitleLeft");
		
		log.info("TC_06_Step_14: An nut back ve man hinh menu");
		transactionReport.clickToDynamicBottomMenu(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);
		
		log.info("TC_06_Step_15: Mo tab Home");
		home.clickToDynamicBottomMenu(driver, "com.VCB:id/menu_1");
	}
	
	@Test
	public void TC_07_HuyTietKiemTuDong_TaiKhoanNguon_USD_OTP() {
		
		log.info("TC_07_Step_01: Keo xuong va click vao phan 'Huy Tiet kiem tu dong'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Huỷ tiết kiệm tự động");
		autoSaving = PageFactoryManager.getAutoSavingPageObject(driver);
		
		log.info("TC_07_Step_02: Chon tai khoan nguon");
		autoSaving.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/thongTinTaiKhoanThanhToan");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_USD);
		
		log.info("TC_07_Step_03: Chon tai khoan tiet kiem");
		autoSaving.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/thongTinTaiKhoanTietKiem");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, savingAccount);
		
		log.info("TC_07_Step_04: An nut Tim kiem");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");
		
		log.info("TC_07_Step_05: Xac nhan hien thi tai khoan nguon");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTaiKhoanNguon"), Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_USD);
		
		log.info("TC_07_Step_06: Xac nhan hien thi tai khoan tiet kiem");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTaiKhoanTietKiem"), savingAccount);
		
		log.info("TC_07_Step_07: Xac nhan hien thi so tien chuyen");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvSoTienChuyen"), tranferMoney);
		
		log.info("TC_07_Step_09: An nut Huy");
		autoSaving.clickToTextViewCombobox(driver, "com.VCB:id/tvHuy");
		
		log.info("TC_07_Step_10: An nut Dong y");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btCancel");
		
		log.info("TC_07_Step_11: An nut Dong ");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
		
		log.info("TC_07_Step_11: An nut back ve man hinh menu");
		autoSaving.clickToDynamicBottomMenu(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);		
	}
	
	@Test
	public void TC_08_HuyTietKiemTuDong_TaiKhoanNguon_USD_BaoCaoGiaoDich() {
		
		log.info("TC_08_Step_01: Mo tab Menu");
		home.clickToDynamicBottomMenu(driver, "com.VCB:id/menu_5");
		
		log.info("TC_08_Step_02: Mo sub-menu 'Bao cao giao dich");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");
		transactionReport = PageFactoryManager.getTransactionReportPageObject(driver);
		
		log.info("TC_08_Step_03: An vao Dropdown 'Tat ca cac loai giao dich");
		transactionReport.clickToTextViewCombobox(driver, "com.VCB:id/tvSelectTransType");
		
		log.info("TC_08_Step_04: Chon 'Huy tiet kiem tu dong'");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, "Hủy tiết kiệm tự động");
		
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
		
		log.info("TC_02_Step_10: Xac nhan hien thi thoi gian giao dich");
		verifyTrue(transactionReport.isTextDisplayedInListTextElements(driver, convertDateTimeIgnoreHHmmss(transactionDate), "com.VCB:id/tvContent"));
		
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
	
	@Parameters ({"otp"})
	//@Test
	public void TC_09_TietKiemTuDong_TaiKhoanNguon_EUR_OTP(String otp) {
		
		home = PageFactoryManager.getHomePageObject(driver);
		
		log.info("TC_09_Step_01: Keo xuong va click vao phan 'Tiet kiem tu dong'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Tiết kiệm tự động");
		autoSaving = PageFactoryManager.getAutoSavingPageObject(driver);
		
		log.info("TC_09_Step_02: Chon tai khoan nguon EUR");
		autoSaving.clickToTextViewCombobox(driver, "com.VCB:id/tvContent");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_EUR);
		
		log.info("TC_09_Step_03: Chon tai khoan tiet kiem");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, "Tài khoản tiết kiệm");
		savingAccount = autoSaving.getFirstOptionDataInDropdown("com.VCB:id/tvContent1");
		autoSaving.clickToFirstOptionInDropdown("com.VCB:id/tvContent1");
		
		log.info("TC_09_Step_04: Chon ngay ket thuc");
		
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, "Ngày kết thúc");
		autoSaving.clickToDynamicDateInDateTimePicker(driver, getForWardDay(3));
		autoSaving.clickToDynamicButton(driver, "OK");
		
		log.info("TC_09_Step_05: Nhap so tien chuyen");
		autoSaving.inputToDynamicInputBox(driver,Auto_Saving_Data.INPUT_MONEY.INPUT_EUR, "Số tiền chuyển");
		tranferMoney = autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent1More");
		
		log.info("TC_09_Step_06: An Tiep tuc");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");
		
		log.info("TC_09_Step_07: Hien thi man hinh xac nhan thong tin");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Xác nhận thông tin");
		
		log.info("TC_09_Step_08: Chon phương thuc xac thuc");
		autoSaving.clickToTextViewCombobox(driver, "com.VCB:id/tvptxt");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");
		
		log.info("TC_09_Step_09: An Tiep tuc");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");
		
		log.info("TC_09_Step_10: Nhap du ki tu vao o nhap OTP");
		autoSaving.inputToDynamicOtp(driver, otp, "Tiếp tục");;

		log.info("TC_09_Step_11: An tiep button 'Tiep tuc'");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_09_Step_12: Hien thi man hinh thong bao giao dich thanh cong");
		transactionID = autoSaving.getDynamicTextByLabel(driver, "Mã giao dịch");
		
		log.info("TC_09_Step_13: An thuc hien giao dich moi");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");
		
		log.info("TC_09_Step_14: Click back ve man hinh chinh");
		autoSaving.clickToDynamicBottomMenu(driver, "com.VCB:id/ivTitleLeft");
	}

	//@Test
	public void TC_10_TietKiemTuDong_TaiKhoanNguon_EUR_OTP_BaoCaoGiaoDich() {
		
		log.info("TC_10_Step_01: Mo tab Menu");
		home.clickToDynamicBottomMenu(driver, "com.VCB:id/menu_5");
		
		log.info("TC_10_Step_02: Mo sub-menu 'Bao cao giao dich");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");
		transactionReport = PageFactoryManager.getTransactionReportPageObject(driver);
		
		log.info("TC_10_Step_03: An vao Dropdown 'Tat ca cac loai giao dich");
		transactionReport.clickToTextViewCombobox(driver, "com.VCB:id/tvSelectTransType");
		
		log.info("TC_10_Step_04: Chon 'Tiet kiem tu dong'");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, "Tiết kiệm tự động");
		
		log.info("TC_10_Step_05: An vao Dropdown 'Chon tai khoan/the");
		transactionReport.clickToTextViewCombobox(driver, "com.VCB:id/tvSelectAcc");
		
		log.info("TC_10_Step_06: Chon tai khoan vua thuc hien giao dich");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_EUR);
		
		log.info("TC_10_Step_07: An nut Tim kiem");
		transactionReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");
		
		log.info("TC_10_Step_08: An vao giao dich dau tien");
		transactionReport.clickToDynamicTransactionInTransactionOrderStatus(driver, "0","com.VCB:id/tvContent");
		
		log.info("TC_10_Step_09: Xac nhan hien thi Title 'Chi tiet giao dich'");
		verifyEquals(transactionReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Chi tiết giao dịch");
		
		log.info("TC_10_Step_10: Xac nhan hien thi dung ma giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Số lệnh giao dịch"), transactionID);
		
		log.info("TC_10_Step_11: Xac nhan hien thi so tai khoan giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Tài khoản/thẻ trích nợ"), Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_EUR);
		
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
	
	//@Test
	public void TC_11_HuyTietKiemTuDong_TaiKhoanNguon_EUR_OTP() {
		
		log.info("TC_11_Step_01: Keo xuong va click vao phan 'Huy Tiet kiem tu dong'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Huỷ tiết kiệm tự động");
		autoSaving = PageFactoryManager.getAutoSavingPageObject(driver);
		
		log.info("TC_11_Step_02: Chon tai khoan nguon");
		autoSaving.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/thongTinTaiKhoanThanhToan");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_EUR);
		
		log.info("TC_11_Step_03: Chon tai khoan tiet kiem");
		autoSaving.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/thongTinTaiKhoanTietKiem");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, savingAccount);
		
		log.info("TC_11_Step_04: An nut Tim kiem");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");
		
		log.info("TC_11_Step_05: Xac nhan hien thi tai khoan nguon");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTaiKhoanNguon"), "Tài khoản nguồn/"+Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_EUR);
		
		log.info("TC_11_Step_06: Xac nhan hien thi tai khoan tiet kiem");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTaiKhoanTietKiem"), "Tài khoản tiết kiệm/"+savingAccount);
		
		log.info("TC_1_Step_07: Xac nhan hien thi so tien chuyen");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvSoTienChuyen"), tranferMoney+ " VND");
		
		log.info("TC_11_Step_09: An nut Huy");
		autoSaving.clickToTextViewCombobox(driver, "com.VCB:id/tvHuy");
		
		log.info("TC_11_Step_10: An nut Dong y");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btCancel");
		
		log.info("TC_11_Step_11: An nut Dong ");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
		
		log.info("TC_11_Step_11: An nut back ve man hinh menu");
		autoSaving.clickToDynamicBottomMenu(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);		
	}
	
	//@Test
	public void TC_12_HuyTietKiemTuDong_TaiKhoanNguon_EUR_BaoCaoGiaoDich() {
		
		log.info("TC_12_Step_01: Mo tab Menu");
		home.clickToDynamicBottomMenu(driver, "com.VCB:id/menu_5");
		
		log.info("TC_12_Step_02: Mo sub-menu 'Bao cao giao dich");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");
		transactionReport = PageFactoryManager.getTransactionReportPageObject(driver);
		
		log.info("TC_12_Step_03: An vao Dropdown 'Tat ca cac loai giao dich");
		transactionReport.clickToTextViewCombobox(driver, "com.VCB:id/tvSelectTransType");
		
		log.info("TC_12_Step_04: Chon 'Huy tiet kiem tu dong'");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, "Hủy tiết kiệm tự động");
		
		log.info("TC_12_Step_05: An vao Dropdown 'Chon tai khoan/the");
		transactionReport.clickToTextViewCombobox(driver, "com.VCB:id/tvSelectAcc");
		
		log.info("TC_12_Step_06: Chon tai khoan vua thuc hien giao dich");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_EUR);
		
		log.info("TC_12_Step_07: An nut Tim kiem");
		transactionReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");
		
		log.info("TC_12_Step_08: An vao giao dich dau tien");
		transactionReport.clickToDynamicTransactionInTransactionOrderStatus(driver, "0","com.VCB:id/tvContent");
		
		log.info("TC_12_Step_09: Xac nhan hien thi Title 'Chi tiet giao dich'");
		verifyEquals(transactionReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Chi tiết giao dịch");
		
		log.info("TC_12_Step_11: Xac nhan hien thi so tai khoan giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Tài khoản/thẻ trích nợ"), Auto_Saving_Data.ORIGIN_ACCOUNT.ACCOUNT_EUR);
		
		log.info("TC_12_Step_12: Xac nhan hien thi so dien thoai duoc nap");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Tài khoản ghi có"), savingAccount);
		
		log.info("TC_12_Step_13: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicBottomMenu(driver, "com.VCB:id/ivTitleLeft");
		
		log.info("TC_12_Step_14: An nut back ve man hinh menu");
		transactionReport.clickToDynamicBottomMenu(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);
		
		log.info("TC_12_Step_15: Mo tab Home");
		home.clickToDynamicBottomMenu(driver, "com.VCB:id/menu_1");
	}
	
	@AfterClass(alwaysRun = true)
	public void afterClass() {
		service.stop();
	}

}
