package vnpay.vietcombank.auto_saving;

import java.io.IOException;
import java.security.GeneralSecurityException;

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
import pageObjects.AutoSavingPageObject;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.SavingOnlinePageObject;
import pageObjects.SettingVCBSmartOTPPageObject;
import pageObjects.TransactionReportPageObject;
import vietcombank_test_data.Auto_Saving_Data;
import vietcombank_test_data.Auto_Saving_Data.Auto_Saving_Text;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.SavingOnline_Data;
import vietcombank_test_data.TransactionReport_Data.ReportTitle;

public class Auto_Saving_Flow_Smart_OTP extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject home;
	private AutoSavingPageObject autoSaving;
	private TransactionReportPageObject transactionReport;
	private SavingOnlinePageObject savingOnline;
	private SettingVCBSmartOTPPageObject smartOTP;

	private String transactionID, savingAccount, transactionDate, startDate, endDate, sourceAccountMoney, sourceAccount,savingTerm;
	private long savingMoney;
	
	SourceAccountModel sourceAccountMol = new SourceAccountModel();

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException, GeneralSecurityException {
		startServer();
		log.info("Before class: Mo app ");
		if (deviceType.contains("android")) {
			driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		} else if (deviceType.contains("ios")) {
			driver = openIOSApp(deviceName, udid, url);
		}
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);

		autoSaving = PageFactoryManager.getAutoSavingPageObject(driver);
		transactionReport = PageFactoryManager.getTransactionReportPageObject(driver);
		savingOnline = PageFactoryManager.getSavingOnlinePageObject(driver);
		smartOTP = PageFactoryManager.getSettingVCBSmartOTPPageObject(driver);
		smartOTP.setupSmartOTP(LogIn_Data.Login_Account.Smart_OTP, getDataInCell(6));
		
		home = PageFactoryManager.getHomePageObject(driver);
	}

	@Parameters ({"otp"})
	@Test
	public void TC_01_MoTaiKhoanTietKiem_VND_3Thang_LaiNhapGoc_PTXT_OTP(String otp) {
		
		log.info("TC_01_01_Click Mo tai khoan tiet kiem");
		home.clickToDynamicButtonLinkOrLinkText(driver, SavingOnline_Data.OPEN_SAVING_ACCOUNT);

		log.info("TC_01_02_Chon so tai khoan");
		savingOnline.clickToDynamicDropDown(driver, SavingOnline_Data.ACCOUNT_NUMBER);
		sourceAccountMol = savingOnline.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);
		sourceAccount = sourceAccountMol.account;

		log.info("TC_01_03_Chon ky han gui");
		savingOnline.clickToDynamicDropDownListTextViewByHeader(driver, SavingOnline_Data.TRANSACTION_INFO, "1");
		savingOnline.clickToDynamicTextContains(driver, SavingOnline_Data.THREE_MONTH);

		log.info("TC_01_04_Nhap so tien gui");
		savingOnline.inputToDynamicInputBoxByHeader(driver, SavingOnline_Data.MONEY, SavingOnline_Data.TRANSACTION_INFO, "2");

		log.info("TC_01_05_Chon hinh thuc chuyen tien");
		savingOnline.clickToDynamicDropDownListTextViewByHeader(driver, SavingOnline_Data.TRANSACTION_INFO, "3");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, SavingOnline_Data.PAY_INTEREST_METHOD_01);

		log.info("TC_01_06_Chon dong y tuan thu cam ket");
		savingOnline.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");

		log.info("TC_01_07_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, SavingOnline_Data.CONTINUE_BUTTON);

		savingTerm = savingOnline.getDynamicTextInTransactionDetail(driver, SavingOnline_Data.IN_TERM);
		savingMoney = convertAvailableBalanceCurrentcyOrFeeToLong(savingOnline.getDynamicTextInTransactionDetail(driver, SavingOnline_Data.MONEY_SAVING));
		
		log.info("TC_01_08_Chon phuong thuc xac thuc");
		savingOnline.scrollDownToText(driver, SavingOnline_Data.ACCURACY_METHOD);
		savingOnline.clickToDynamicDropDown(driver, SavingOnline_Data.ACCURACY_METHOD);
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, SavingOnline_Data.SMS_OTP);

		log.info("TC_01_09_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, SavingOnline_Data.CONTINUE_BUTTON);

		savingOnline.inputToDynamicOtp(driver, otp, SavingOnline_Data.CONTINUE_BUTTON);

		savingOnline.clickToDynamicButton(driver, SavingOnline_Data.CONTINUE_BUTTON);

		log.info("TC_01_10_Lay ma giao dich");
		savingAccount = savingOnline.getDynamicTextInTransactionDetail(driver, SavingOnline_Data.SAVING_NUMBER);

		log.info("TC_01_11_Ve man hinh chinh");
		savingOnline.clickToDynamicButton(driver, SavingOnline_Data.NEW_TRANSACTION_PERFORM);
		savingOnline.clickToDynamicBackIcon(driver, SavingOnline_Data.OPEN_SAVING_ACCOUNT);
		
	}

	@Test
	public void TC_02_TietKiemTuDong_TaiKhoanNguon_VND_SmartOTP() {

		log.info("TC_02_Step_01: Keo xuong va click vao phan 'Tiet kiem tu dong'");
		home.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Text.AUTO_SAVING_TEXT);
		
		log.info("TC_02_Step_02: Chon tai khoan nguon VND");
		autoSaving.clickToTextID(driver, "com.VCB:id/tvContent");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount);
		sourceAccountMoney = autoSaving.getMoneyByAccount(driver, Auto_Saving_Text.SURPLUS_TEXT);

		log.info("TC_02_Step_03: Chon tai khoan tiet kiem");
		autoSaving.clickToDynamicLinerLayoutID(driver, "com.VCB:id/TaiKhoanTietKiem");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, savingAccount);

		log.info("TC_02_Step_04: Xac nhan Ky han va so du TK tiet kiem");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, Auto_Saving_Text.TERM_TEXT), capitalizeString(savingTerm));
		startDate = autoSaving.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayBatDau");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, Auto_Saving_Text.SAVING_SURPLUS_TEXT), addCommasToLong(savingMoney+"") + " VND");

		log.info("TC_02_Step_05: Chon ngay ket thuc");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Text.END_DATE_TEXT);
		autoSaving.clickToDynamicDateInDateTimePicker(driver, getForWardDay(3));
		autoSaving.clickToDynamicButton(driver, Auto_Saving_Text.BUTTON_OK_TEXT);
		endDate = autoSaving.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayKetThuc");

		log.info("TC_02_Step_06: Nhap so tien chuyen");
		autoSaving.inputToDynamicInputBox(driver, savingMoney+"", Auto_Saving_Text.TRANSFER_MONEY_TEXT);

		log.info("TC_02_Step_07: An Tiep tuc");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_02_Step_08: Hien thi man hinh xac nhan thong tin");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), Auto_Saving_Text.CONFIRM_INFO_TEXT);

		log.info("TC_02_Step_09: Hien thi thông tin xac nhan");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleHead"), Auto_Saving_Text.TRANSACTION_CONFIRM_MESSAGE);

		log.info("TC_02_Step_10: Hien thi tai khoan nguon");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, Auto_Saving_Text.SOURCE_ACCOUNT_TEXT), sourceAccount);

		log.info("TC_02_Step_11: Hien thi tai khoan tiet kiem");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, Auto_Saving_Text.SAVING_ACCOUNT_TEXT), savingAccount);

		log.info("TC_02_Step_12: Hien thi ky han gui");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, Auto_Saving_Text.TERM_TIME_TEXT), capitalizeString(savingTerm));

		log.info("TC_02_Step_13: Hien thi ngay den han");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, Auto_Saving_Text.DUE_DATE_TEXT), startDate);

		log.info("TC_02_Step_14: Hien thi So tien hien tai");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, Auto_Saving_Text.CURRENT_MONEY_TEXT), addCommasToLong(savingMoney+"") + " VND");

		log.info("TC_02_Step_15: Hien thi Chu ky chuyen");
		verifyTrue(autoSaving.isDynamicMessageAndLabelTextDisplayed(driver, Auto_Saving_Text.TRANSFER_FREQUENCY_TEXT));

		log.info("TC_02_Step_16: Hien thi ngay bat dau");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, Auto_Saving_Text.START_DATE_TEXT), startDate);

		log.info("TC_02_Step_17: Hien thi ngay ket thuc");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, Auto_Saving_Text.END_DATE_TEXT), endDate);

		log.info("TC_02_Step_18: Hien thi so tien chuyen");
		verifyTrue(autoSaving.getDynamicTextByLabel(driver, Auto_Saving_Text.TRANSFER_MONEY_TEXT).contains(addCommasToLong(savingMoney+"") + " VND"));
		
		log.info("TC_02_Step_19: Chon phương thuc xac thuc");
		autoSaving.clickToTextID(driver, "com.VCB:id/tvptxt");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Text.SMART_OTP_AUTHEN_TEXT);

		log.info("TC_02_Step_20: An Tiep tuc");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_02_Step_21: Nhap du ki tu vao o nhap OTP");
		autoSaving.inputToDynamicSmartOTP(driver, LogIn_Data.Login_Account.Smart_OTP, "com.VCB:id/otp");

		log.info("TC_02_Step_22: An tiep button 'Tiep tuc'");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/submit");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_02_Step_23: Hien thi man hinh thong bao giao dich thanh cong");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), Auto_Saving_Text.TRANSACTION_SUCCESS_TEXT);
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount"), addCommasToLong(savingMoney+"") + " VND");
		verifyEquals(autoSaving.getDynamicTextByLabel(driver, Auto_Saving_Text.SAVING_ACCOUNT_TEXT), savingAccount);
		transactionDate = autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTime");
		transactionID = autoSaving.getDynamicTextByLabel(driver, Auto_Saving_Text.TRANSACTION_ID_TEXT);

		log.info("TC_02_Step_24: An thuc hien giao dich moi");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_02_Step_25: Chon tai khoan nguon VND");
		autoSaving.clickToTextID(driver, "com.VCB:id/tvContent");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount);
		verifyEquals(autoSaving.getMoneyByAccount(driver, Auto_Saving_Text.SURPLUS_TEXT), sourceAccountMoney);

		log.info("TC_02_Step_26: Click back ve man hinh chinh");
		autoSaving.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

	}

	@Test
	public void TC_03_TietKiemTuDong_TaiKhoanNguon_VND_SmartOTP_BaoCaoGiaoDich() {

		log.info("TC_03_Step_01: Mo tab Menu");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_5");

		log.info("TC_03_Step_02: Mo sub-menu 'Bao cao giao dich");
		home.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSACTION_REPORT);
		
		log.info("TC_03_Step_03: An vao Dropdown 'Tat ca cac loai giao dich");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectTransType");

		log.info("TC_03_Step_04: Chon 'Tiet kiem tu dong'");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.AUTO_SAVING_TEXT);

		log.info("TC_03_Step_05: An vao Dropdown 'Chon tai khoan/the");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_03_Step_06: Chon tai khoan vua thuc hien giao dich");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount);

		log.info("TC_03_Step_07: An nut Tim kiem");
		transactionReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");

		log.info("TC_03_Step_08: An vao giao dich dau tien");
		transactionReport.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_03_Step_09: Xac nhan hien thi Title 'Chi tiet giao dich'");
		verifyEquals(transactionReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), ReportTitle.TRANSACTION_DETAIL);

		log.info("TC_03_Step_10: Xac nhan hien thi thoi gian giao dich");
		verifyTrue(transactionReport.isTextDisplayedInListTextElements(driver, convertDateTimeIgnoreHHmmss(transactionDate), "com.VCB:id/tvContent"));

		log.info("TC_03_Step_11: Xac nhan hien thi dung ma giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.TRANSACTION_NUMBER), transactionID);

		log.info("TC_03_Step_12: Xac nhan hien thi so tai khoan giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.ACCOUNT_CARD), sourceAccount);

		log.info("TC_03_Step_13: Xac nhan hien thi tai khoan ghi co");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.DESTINATION_ACCOUNT_CARD), savingAccount);

		log.info("TC_03_Step_14: Xac nhan hien thi so tien giao dich");
		verifyTrue(transactionReport.isTextDisplayedInListTextElements(driver, addCommasToLong(savingMoney+"") + " VND", "com.VCB:id/tvContent"));

		log.info("TC_03_Step_15: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_03_Step_16: An nut back ve man hinh menu");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
	
		log.info("TC_03_Step_17: Mo tab Home");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_1");

	}

	@Test
	public void TC_04_HuyTietKiemTuDong_TaiKhoanNguon_VND() {

		log.info("TC_04_Step_01: Keo xuong va click vao phan 'Huy Tiet kiem tu dong'");
		home.clickToDynamicButtonLinkOrLinkText(driver, Auto_Saving_Text.CANCEL_AUTO_SAVING_TEXT);
		
		log.info("TC_04_Step_02: Chon tai khoan nguon");
		autoSaving.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/thongTinTaiKhoanThanhToan");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount);

		log.info("TC_04_Step_03: Chon tai khoan tiet kiem");
		autoSaving.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/thongTinTaiKhoanTietKiem");
		autoSaving.clickToDynamicButtonLinkOrLinkText(driver, savingAccount);

		log.info("TC_04_Step_04: An nut Tim kiem");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");

		log.info("TC_04_Step_05: Xac nhan hien thi tai khoan nguon");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTaiKhoanNguon"), sourceAccount);

		log.info("TC_04_Step_06: Xac nhan hien thi tai khoan tiet kiem");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTaiKhoanTietKiem"), savingAccount);

		log.info("TC_04_Step_07: Xac nhan hien thi so tien chuyen");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvSoTienChuyen"), addCommasToLong(savingMoney+"") + " VND");

		log.info("TC_04_Step_08: Xac nhan hien thi chu ky chuyen");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvThoiGian"), startDate + " - " + endDate);

		log.info("TC_04_Step_09: An nut Huy");
		autoSaving.clickToTextID(driver, "com.VCB:id/tvHuy");
		verifyEquals(autoSaving.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), Auto_Saving_Data.TEXT.AUTO_SAVING_CANCEL_MESSAGE);

		log.info("TC_04_Step_10: An nut Dong y");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btCancel");

		log.info("TC_04_Step_11: An nut Dong ");
		autoSaving.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

		log.info("TC_04_Step_12: An nut back ve man hinh menu");
		autoSaving.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

	}

	@Test
	public void TC_05_HuyTietKiemTuDong_TaiKhoanNguon_VND_BaoCaoGiaoDich() {

		log.info("TC_05_Step_01: Mo tab Menu");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_5");

		log.info("TC_05_Step_02: Mo sub-menu 'Bao cao giao dich");
		home.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSACTION_REPORT);
		
		log.info("TC_05_Step_03: An vao Dropdown 'Tat ca cac loai giao dich");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectTransType");

		log.info("TC_05_Step_04: Chon 'Huy tiet kiem tu dong'");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.CANCEL_AUTO_SAVING_TEXT);

		log.info("TC_05_Step_05: An vao Dropdown 'Chon tai khoan/the");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_05_Step_06: Chon tai khoan vua thuc hien giao dich");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount);

		log.info("TC_05_Step_07: An nut Tim kiem");
		transactionReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");

		log.info("TC_05_Step_08: An vao giao dich dau tien");
		transactionReport.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_05_Step_09: Xac nhan hien thi Title 'Chi tiet giao dich'");
		verifyEquals(transactionReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), ReportTitle.TRANSACTION_DETAIL);

		log.info("TC_05_Step_10: Xac nhan hien thi thoi gian giao dich");
		verifyTrue(transactionReport.isTextDisplayedInListTextElements(driver, convertDateTimeIgnoreHHmmss(transactionDate), "com.VCB:id/tvContent"));

		log.info("TC_05_Step_12: Xac nhan hien thi so tai khoan giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.ACCOUNT_CARD), sourceAccount);

		log.info("TC_05_Step_13: Xac nhan hien thi tai khoan ghi co");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.DESTINATION_ACCOUNT_CARD), savingAccount);

		log.info("TC_03_Step_14: Xac nhan hien thi so tien giao dich");
		verifyTrue(transactionReport.isTextDisplayedInListTextElements(driver, savingTerm + " VND", "com.VCB:id/tvContent"));

		log.info("TC_05_Step_15: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_05_Step_16: An nut back ve man hinh menu");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
	
		log.info("TC_05_Step_17: Mo tab Home");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_1");

	}

	@Parameters ({"otp"})
	@Test
	public void TC_06_TatToanTaiKhoanTietKiem_VND_3Thang_LaiNhapGoc_PTXT_OTP(String otp) {

		log.info("TC_06_01_Click Tat toan tai khoan tiet kiem");
		home.clickToDynamicButtonLinkOrLinkText(driver, SavingOnline_Data.EXPIRE_SAVING_ACCOUNT);

		log.info("TC_06_02_Chon so tai khoan tiet kiem");
		savingOnline.clickToDynamicDropDownInSavingOnline(SavingOnline_Data.SAVING_NUMBER_ACCOUNT);
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, savingAccount);

		log.info("TC_06_03_Chon tai khoan dich");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, SavingOnline_Data.CHOOSE_DESTINATION_ACCOUNT);
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount);

		log.info("TC_06_04_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, SavingOnline_Data.CONTINUE_BUTTON);

		log.info("TC_06_05_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, SavingOnline_Data.CONTINUE_BUTTON);

		log.info("TC_06_06_Chon phuong thuc xac thuc");
		savingOnline.scrollDownToText(driver, SavingOnline_Data.ACCURACY_METHOD);
		savingOnline.clickToDynamicDropDown(driver, SavingOnline_Data.ACCURACY_METHOD);
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, SavingOnline_Data.SMS_OTP);

		log.info("TC_06_07_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, SavingOnline_Data.CONTINUE_BUTTON);

		savingOnline.inputToDynamicOtp(driver, otp, SavingOnline_Data.CONTINUE_BUTTON);

		savingOnline.clickToDynamicButton(driver, SavingOnline_Data.CONTINUE_BUTTON);

		log.info("TC_06_08_Click Thuc hien giao dich moi");
		savingOnline.clickToDynamicButton(driver, SavingOnline_Data.NEW_TRANSACTION_PERFORM);
		savingOnline.clickToDynamicBackIcon(driver, SavingOnline_Data.EXPIRE_SAVING_ACCOUNT);

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		service.stop();
	}

}
