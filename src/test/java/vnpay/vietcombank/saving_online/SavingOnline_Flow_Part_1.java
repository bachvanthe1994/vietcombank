package vnpay.vietcombank.saving_online;

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
import model.SavingOnlineInfo;
import model.SourceAccountModel;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.SavingOnlinePageObject;
import pageObjects.TransactionReportPageObject;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.SavingOnline_Data;
import vietcombank_test_data.TransactionReport_Data;

public class SavingOnline_Flow_Part_1 extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private SavingOnlinePageObject savingOnline;
	private TransactionReportPageObject transReport;
	long transferFee = 0;
	double transferFeeCurrentcy = 0;
	private String transferTime;
	private String savingAccount;
	private String transactionNumber;
	SourceAccountModel sourceAccount = new SourceAccountModel();
	String account, balance, currentcy = "";

	SavingOnlineInfo info = new SavingOnlineInfo(Account_Data.Valid_Account.ACCOUNT2, SavingOnline_Data.ONE_MONTH, SavingOnline_Data.MONEY, SavingOnline_Data.PAY_INTEREST_METHOD_01);
	SavingOnlineInfo info1 = new SavingOnlineInfo(Account_Data.Valid_Account.ACCOUNT2, SavingOnline_Data.THREE_MONTH, SavingOnline_Data.MONEY, SavingOnline_Data.PAY_INTEREST_METHOD_01);

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
		homePage = PageFactoryManager.getHomePageObject(driver);
		savingOnline = PageFactoryManager.getSavingOnlinePageObject(driver);
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);

	}

	private long surplus, availableBalance, actualAvailableBalance;

	@Test
	public void TC_01_MoTaiKhoanTietKiem_VND_1Thang_LaiNhapGoc() {
		log.info("TC_01_1_Click Mo tai khoan tiet kiem");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, SavingOnline_Data.OPEN_SAVING_ACCOUNT);

		log.info("TC_01_2_Chon so tai khoan");
		savingOnline.clickToDynamicDropDown(driver, SavingOnline_Data.ACCOUNT_NUMBER);
		sourceAccount = savingOnline.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);
		account = sourceAccount.account;

		surplus = convertAvailableBalanceCurrentcyOrFeeToLong(savingOnline.getDynamicTextInTransactionDetail(driver, SavingOnline_Data.AVAILABLE_BALANCE));

		log.info("TC_01_3_Chon ky han gui");
		savingOnline.clickToDynamicDropDownListTextViewByHeader(driver, SavingOnline_Data.TRANSACTION_INFO, "1");
		savingOnline.clickToDynamicTextContains(driver, info.term);

		log.info("TC_01_4_Nhap so tien gui");
		savingOnline.inputToDynamicInputBoxByHeader(driver, info.money, SavingOnline_Data.TRANSACTION_INFO, "2");

		log.info("TC_01_5_Chon hinh thuc chuyen tien");
		savingOnline.clickToDynamicDropDownListTextViewByHeader(driver, SavingOnline_Data.TRANSACTION_INFO, "3");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, info.formOfPayment);

		log.info("TC_01_6_Chon dong y tuan thu cam ket");
		savingOnline.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");

		log.info("TC_01_7_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, SavingOnline_Data.CONTINUE_BUTTON);

		log.info("TC_01_8_Kiem tra man hinh xac nhan thong tin");

		log.info("TC_01_8_1_Kiem tra tai khoan nguon");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, SavingOnline_Data.SOURCE_ACCOUNT), account);

		log.info("TC_01_8_2_Kiem tra ky han gui");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, SavingOnline_Data.IN_TERM), SavingOnline_Data.ONE_MONTH);

		log.info("TC_01_8_3_Kiem tra lai suat");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, SavingOnline_Data.INTEREST_RATE), SavingOnline_Data.ONE_MONTH_INTEREST);

		log.info("TC_01_8_4_Kiem tra so tien gui");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, SavingOnline_Data.MONEY_SAVING), addCommasToLong(info.money) + " VND");

		log.info("TC_01_8_5_Kiem tra hinh thuc tra lai");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, SavingOnline_Data.INTEREST_PAYMENT_METHOD), info.formOfPayment);

		log.info("TC_01_09_Chon phuong thuc xac thuc");
		savingOnline.scrollDownToText(driver, SavingOnline_Data.ACCURACY_METHOD);
		savingOnline.clickToDynamicDropDown(driver, SavingOnline_Data.ACCURACY_METHOD);
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, SavingOnline_Data.SMS_OTP);

		log.info("TC_01_10_Kiem tra so tien phi");
		transferFee = 0;

		log.info("TC_01_11_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, SavingOnline_Data.CONTINUE_BUTTON);

		savingOnline.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, SavingOnline_Data.CONTINUE_BUTTON);

		savingOnline.clickToDynamicButton(driver, SavingOnline_Data.CONTINUE_BUTTON);

		log.info("TC_01_12_Kiem tra man hinh Giao dich thanh cong");
		log.info("TC_01_12_1_Kiem tra Giao dich thanh cong");
		verifyTrue(savingOnline.isDynamicMessageAndLabelTextDisplayed(driver, SavingOnline_Data.SUCCESS_TRANSACTION));

		log.info("TC_01_12_6_Lay ma giao dich");
		transferTime = savingOnline.getTransferTimeSuccess(SavingOnline_Data.SUCCESS_TRANSACTION);
		savingAccount = savingOnline.getDynamicTextInTransactionDetail(driver, SavingOnline_Data.SAVING_NUMBER);
		transactionNumber = savingOnline.getDynamicTextInTransactionDetail(driver, SavingOnline_Data.TRANSACTION_CODE);

		log.info("TC_01_13_Click Thuc hien giao dich moi");
		savingOnline.clickToDynamicButton(driver, SavingOnline_Data.NEW_TRANSACTION_PERFORM);

		log.info("TC_01_14_Kiem tra so du kha dung luc sau");
		savingOnline.clickToDynamicDropDown(driver, SavingOnline_Data.ACCOUNT_NUMBER);
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, account);
		actualAvailableBalance = convertAvailableBalanceCurrentcyOrFeeToLong(savingOnline.getDynamicTextInTransactionDetail(driver, SavingOnline_Data.AVAILABLE_BALANCE));
		availableBalance = canculateAvailableBalances(surplus, Long.parseLong(info.money), transferFee);
		verifyEquals(actualAvailableBalance, availableBalance);

	}

	@Test
	public void TC_02_MoTaiKhoanTietKiem_VND_1Thang_LaiNhapGoc_BaoCao() {
		log.info("TC_02_1: Click  nut Back");
		savingOnline.clickToDynamicBackIcon(driver, SavingOnline_Data.OPEN_SAVING_ACCOUNT);

		log.info("TC_02_2: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_02_3: Click Bao Cao giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransactionReport_Data.ReportTite.TRANSACTION_REPORT);

		log.info("TC_02_4: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransactionReport_Data.ReportTite.ALL_TYPE_TRANSACTION);

		log.info("TC_02_5: Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, SavingOnline_Data.OPEN_SAVING_ACCOUNT);

		log.info("TC_02_6: Click Chon Tai Khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_02_7: Chon tai Khoan chuyen");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, account);

		log.info("TC_02_8: Click Tim Kiem");
		transReport.clickToDynamicButton(driver, SavingOnline_Data.SEARCH_BUTTON);

		log.info("TC_02_9: Kiem tra ngay tao giao dich hien thi");
		String reportTime = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
		verifyEquals(convertDateTimeIgnoreHHmmss(reportTime), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_02_10: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(info.money) + " VND"));

		log.info("TC_02_11: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_02_12: Kiem tra thoi gian tao giao dich hien thi");
		reportTime = transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch");
		verifyEquals(convertDateTimeIgnoreHHmmss(reportTime), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_02_13: Kiem tra so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTite.TRANSACTION_NUMBER), transactionNumber);

		log.info("TC_02_14: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTite.ACCOUNT_CARD), account);

		log.info("TC_02_15: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTite.TRANSACTION_MONEY).contains(addCommasToLong(info.money) + " VND"));

		log.info("TC_02_16: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTite.TRANSACTION_TYPE), SavingOnline_Data.OPEN_SAVING_ACCOUNT);

		log.info("TC_02_17: Kiem tra noi dung giao dich");
		String expectContent = "MBVCB." + transactionNumber + ".FDONLINE FROM ACT " + account + ", SO TIEN " + addCommasToLong(info.money) + ", LOAI TIEN VND, KY HAN " + convertVietNameseStringToString(SavingOnline_Data.ONE_MONTH);
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTite.TRANSACTION_CONTENT), expectContent);

		log.info("TC_02_18: Click  nut Back");
		savingOnline.clickToDynamicBackIcon(driver, TransactionReport_Data.ReportTite.TRANSACTION_DETAIL);

		log.info("TC_02_19: Click  nut Back");
		savingOnline.clickToDynamicBackIcon(driver, TransactionReport_Data.ReportTite.TRANSACTION_REPORT);

		log.info("TC_02_20: Click  nut Home");
		savingOnline.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");

	}

	@Test
	public void TC_03_TatToanTaiKhoanTietKiem_VND_1Thang_LaiNhapGoc() {
		String savingDate = "";
		String expiredDate = "";

		log.info("TC_03_1_Click Tat toan tai khoan tiet kiem");
		homePage.scrollDownToText(driver, "Tín dụng");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, SavingOnline_Data.EXPIRE_SAVING_ACCOUNT);

		log.info("TC_03_2_Chon so tai khoan tiet kiem");
		savingOnline.clickToDynamicDropDownInSavingOnline(SavingOnline_Data.SAVING_NUMBER_ACCOUNT);
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, savingAccount);

		log.info("TC_03_3_Chon tai khoan dich");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, SavingOnline_Data.CHOOSE_DESTINATION_ACCOUNT);
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, account);

		surplus = convertAvailableBalanceCurrentcyOrFeeToLong(savingOnline.getDynamicTextAvailableBalanceInSavingOnline(SavingOnline_Data.AVAILABLE_BALANCE));

		log.info("TC_03_4_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, SavingOnline_Data.CONTINUE_BUTTON);

		log.info("TC_03_5_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, SavingOnline_Data.CONTINUE_BUTTON);

		log.info("TC_03_06_Kiem tra man hinh xac thuc thong tin");
		log.info("TC_03_06_1: Kiem tra tai khoan tiet kiem");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, SavingOnline_Data.SAVING_NUMBER_ACCOUNT), savingAccount);

		log.info("TC_03_06_2: Kiem tra ten tai khoan tiet kiem");

		log.info("TC_03_06_3: Kiem tra ky han gui");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, SavingOnline_Data.IN_TERM).toLowerCase(), SavingOnline_Data.ONE_MONTH.toLowerCase());

		log.info("TC_03_06_4: Kiem tra lai suat");

		log.info("TC_03_06_5: Kiem tra ngay gui tien");
		savingDate = transReport.getDynamicTextInTransactionDetail(driver, SavingOnline_Data.SAVING_DATE);
		verifyTrue(checkDateLessThanNow(savingDate));

		log.info("TC_03_06_6: Kiem tra ngay den han");
		expiredDate = getForwardMonthAndForwardDayFolowDate(savingDate, 1, 0);
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, SavingOnline_Data.EXPIRE_DATE), expiredDate);

		log.info("TC_03_06_7: Kiem tra so tien gui goc");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, SavingOnline_Data.ORIGINAL_MONEY), addCommasToLong(info.money) + " VND");

		log.info("TC_03_06_8: Kiem tra so tien thuc huong");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, SavingOnline_Data.REAL_MONEY), addCommasToLong(info.money) + " VND");

		log.info("TC_03_06_9: Kiem tra tai khoan dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, SavingOnline_Data.DESTINATION_ACCOUNT), account);

		log.info("TC_03_07_Chon phuong thuc xac thuc");
		savingOnline.scrollDownToText(driver, SavingOnline_Data.ACCURACY_METHOD);
		savingOnline.clickToDynamicDropDown(driver, SavingOnline_Data.ACCURACY_METHOD);
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, SavingOnline_Data.SMS_OTP);

		log.info("TC_03_08_Kiem tra so tien phi");
		transferFee = 0;

		log.info("TC_03_09_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, SavingOnline_Data.CONTINUE_BUTTON);

		savingOnline.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, SavingOnline_Data.CONTINUE_BUTTON);

		savingOnline.clickToDynamicButton(driver, SavingOnline_Data.CONTINUE_BUTTON);

		transactionNumber = savingOnline.getDynamicTextInTransactionDetail(driver, SavingOnline_Data.TRANSACTION_CODE);

		log.info("TC_03_8_Click Thuc hien giao dich moi");
		savingOnline.clickToDynamicButton(driver, SavingOnline_Data.NEW_TRANSACTION_PERFORM);

		log.info("TC_03_9_Chon tai khoan dich");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, SavingOnline_Data.CHOOSE_DESTINATION_ACCOUNT);
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, account);

		actualAvailableBalance = convertAvailableBalanceCurrentcyOrFeeToLong(savingOnline.getDynamicTextAvailableBalanceInSavingOnline(SavingOnline_Data.AVAILABLE_BALANCE));
		availableBalance = canculateAvailableBalances(surplus, -Long.parseLong(info.money), transferFee);
		verifyEquals(actualAvailableBalance, availableBalance);

	}

	@Test
	public void TC_04_TatToanTaiKhoanTietKiem_VND_1Thang_LaiNhapGoc_BaoCao() {
		log.info("TC_04_1: Click  nut Back");
		savingOnline.clickToDynamicBackIcon(driver, SavingOnline_Data.EXPIRE_SAVING_ACCOUNT);

		log.info("TC_04_2: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_04_3: Click Bao Cao giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransactionReport_Data.ReportTite.TRANSACTION_REPORT);

		log.info("TC_04_4: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransactionReport_Data.ReportTite.ALL_TYPE_TRANSACTION);

		log.info("TC_04_5: Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, SavingOnline_Data.EXPIRE_SAVING_ACCOUNT);

		log.info("TC_04_6: Click Chon Tai Khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_04_7: Chon tai Khoan chuyen");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, account);

		log.info("TC_04_8: Click Tim Kiem");
		transReport.clickToDynamicButton(driver, SavingOnline_Data.SEARCH_BUTTON);

		log.info("TC_04_9: Kiem tra ngay tao giao dich hien thi");
		String reportTime = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
		verifyEquals(convertDateTimeIgnoreHHmmss(reportTime), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_04_10: Kiem tra so tien tat toan");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("+ " + addCommasToLong(info.money) + " VND"));

		log.info("TC_04_11: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_04_12: Kiem tra thoi gian tao giao dich hien thi");
		reportTime = transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch");
		verifyEquals(convertDateTimeIgnoreHHmmss(reportTime), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_04_13: Kiem tra so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTite.TRANSACTION_NUMBER), transactionNumber);

		log.info("TC_04_14: Kiem tra tai khoan/the trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTite.ACCOUNT_CARD), savingAccount);

		log.info("TC_04_14: Kiem tra so tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTite.DESTINATION_ACCOUNT_CARD), account);

		log.info("TC_04_15: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTite.TRANSACTION_MONEY).contains(addCommasToLong(info.money) + " VND"));

		log.info("TC_04_16: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTite.TRANSACTION_TYPE), SavingOnline_Data.EXPIRE_SAVING_ACCOUNT);

		log.info("TC_04_17: Kiem tra noi dung giao dich");
		String expectContent = "MBVCB." + transactionNumber + ".Tat toan TK FD, SO TK " + savingAccount + ", KY HAN " + convertVietNameseStringToString(SavingOnline_Data.ONE_MONTH);
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTite.TRANSACTION_CONTENT), expectContent);

		log.info("TC_04_18: Click  nut Back");
		savingOnline.clickToDynamicBackIcon(driver, TransactionReport_Data.ReportTite.TRANSACTION_DETAIL);

		log.info("TC_04_19: Click  nut Back");
		savingOnline.clickToDynamicBackIcon(driver, TransactionReport_Data.ReportTite.TRANSACTION_REPORT);

		log.info("TC_04_20: Click  nut Home");
		savingOnline.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");

	}

	@Test
	public void TC_05_MoTaiKhoanTietKiem_VND_3Thang_LaiNhapGoc() {
		log.info("TC_05_1_Click Mo tai khoan tiet kiem");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, SavingOnline_Data.OPEN_SAVING_ACCOUNT);

		log.info("TC_05_2_Chon so tai khoan");
		savingOnline.clickToDynamicDropDown(driver, SavingOnline_Data.ACCOUNT_NUMBER);
		sourceAccount = savingOnline.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);
		account = sourceAccount.account;

		surplus = convertAvailableBalanceCurrentcyOrFeeToLong(savingOnline.getDynamicTextInTransactionDetail(driver, SavingOnline_Data.AVAILABLE_BALANCE));

		log.info("TC_05_3_Chon ky han gui");
		savingOnline.clickToDynamicDropDownListTextViewByHeader(driver, SavingOnline_Data.TRANSACTION_INFO, "1");
		savingOnline.clickToDynamicTextContains(driver, info1.term);

		log.info("TC_05_4_Nhap so tien gui");
		savingOnline.inputToDynamicInputBoxByHeader(driver, info1.money, SavingOnline_Data.TRANSACTION_INFO, "2");

		log.info("TC_05_5_Chon hinh thuc chuyen tien");
		savingOnline.clickToDynamicDropDownListTextViewByHeader(driver, SavingOnline_Data.TRANSACTION_INFO, "3");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, info1.formOfPayment);

		log.info("TC_05_6_Chon dong y tuan thu cam ket");
		savingOnline.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");

		log.info("TC_05_7_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, SavingOnline_Data.CONTINUE_BUTTON);

		log.info("TC_05_8_Kiem tra man hinh xac nhan thong tin");

		log.info("TC_05_8_1_Kiem tra tai khoan nguon");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, SavingOnline_Data.SOURCE_ACCOUNT), account);

		log.info("TC_05_8_2_Kiem tra ky han gui");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, SavingOnline_Data.IN_TERM), SavingOnline_Data.THREE_MONTH);

		log.info("TC_05_8_3_Kiem tra lai suat");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, SavingOnline_Data.INTEREST_RATE), SavingOnline_Data.THREE_MONTH_INTEREST);

		log.info("TC_05_8_4_Kiem tra so tien gui");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, SavingOnline_Data.MONEY_SAVING), addCommasToLong(info1.money) + " VND");

		log.info("TC_05_8_5_Kiem tra hinh thuc tra lai");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, SavingOnline_Data.INTEREST_PAYMENT_METHOD), info1.formOfPayment);

		log.info("TC_05_09_Chon phuong thuc xac thuc");
		savingOnline.scrollDownToText(driver, SavingOnline_Data.ACCURACY_METHOD);
		savingOnline.clickToDynamicDropDown(driver, SavingOnline_Data.ACCURACY_METHOD);
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, SavingOnline_Data.SMS_OTP);

		log.info("TC_05_10_Kiem tra so tien phi");
		transferFee = 0;

		log.info("TC_05_11_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, SavingOnline_Data.CONTINUE_BUTTON);

		savingOnline.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, SavingOnline_Data.CONTINUE_BUTTON);

		savingOnline.clickToDynamicButton(driver, SavingOnline_Data.CONTINUE_BUTTON);

		log.info("TC_05_12_Kiem tra man hinh Giao dich thanh cong");
		log.info("TC_05_12_1_Kiem tra Giao dich thanh cong");
		verifyTrue(savingOnline.isDynamicMessageAndLabelTextDisplayed(driver, SavingOnline_Data.SUCCESS_TRANSACTION));

		log.info("TC_05_12_6_Lay ma giao dich");
		transferTime = savingOnline.getTransferTimeSuccess(SavingOnline_Data.SUCCESS_TRANSACTION);
		savingAccount = savingOnline.getDynamicTextInTransactionDetail(driver, SavingOnline_Data.SAVING_NUMBER);
		transactionNumber = savingOnline.getDynamicTextInTransactionDetail(driver, SavingOnline_Data.TRANSACTION_CODE);

		log.info("TC_05_13_Click Thuc hien giao dich moi");
		savingOnline.clickToDynamicButton(driver, SavingOnline_Data.NEW_TRANSACTION_PERFORM);

		log.info("TC_05_14_Kiem tra so du kha dung luc sau");
		savingOnline.clickToDynamicDropDown(driver, SavingOnline_Data.ACCOUNT_NUMBER);
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, account);
		actualAvailableBalance = convertAvailableBalanceCurrentcyOrFeeToLong(savingOnline.getDynamicTextInTransactionDetail(driver, SavingOnline_Data.AVAILABLE_BALANCE));
		availableBalance = canculateAvailableBalances(surplus, Long.parseLong(info1.money), transferFee);
		verifyEquals(actualAvailableBalance, availableBalance);

	}

	@Test
	public void TC_06_MoTaiKhoanTietKiem_VND_3Thang_LaiNhapGoc_BaoCao() {
		log.info("TC_06_1: Click  nut Back");
		savingOnline.clickToDynamicBackIcon(driver, SavingOnline_Data.OPEN_SAVING_ACCOUNT);

		log.info("TC_06_2: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_06_3: Click Bao Cao giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransactionReport_Data.ReportTite.TRANSACTION_REPORT);

		log.info("TC_06_4: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransactionReport_Data.ReportTite.ALL_TYPE_TRANSACTION);

		log.info("TC_06_5: Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, SavingOnline_Data.OPEN_SAVING_ACCOUNT);

		log.info("TC_06_6: Click Chon Tai Khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_06_7: Chon tai Khoan chuyen");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, account);

		log.info("TC_06_8: Click Tim Kiem");
		transReport.clickToDynamicButton(driver, SavingOnline_Data.SEARCH_BUTTON);

		log.info("TC_06_9: Kiem tra ngay tao giao dich hien thi");
		String reportTime = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
		verifyEquals(convertDateTimeIgnoreHHmmss(reportTime), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_06_10: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(info1.money) + " VND"));

		log.info("TC_06_11: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_06_12: Kiem tra thoi gian tao giao dich hien thi");
		reportTime = transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch");
		verifyEquals(convertDateTimeIgnoreHHmmss(reportTime), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_06_13: Kiem tra so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTite.TRANSACTION_NUMBER), transactionNumber);

		log.info("TC_06_14: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTite.ACCOUNT_CARD), account);

		log.info("TC_06_15: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTite.TRANSACTION_MONEY).contains(addCommasToLong(info1.money) + " VND"));

		log.info("TC_06_16: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTite.TRANSACTION_TYPE), SavingOnline_Data.OPEN_SAVING_ACCOUNT);

		log.info("TC_06_17: Kiem tra noi dung giao dich");
		String expectContent = "MBVCB." + transactionNumber + ".FDONLINE FROM ACT " + account + ", SO TIEN " + addCommasToLong(info1.money) + ", LOAI TIEN VND, KY HAN " + convertVietNameseStringToString(SavingOnline_Data.THREE_MONTH);
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTite.TRANSACTION_CONTENT), expectContent);

		log.info("TC_06_18: Click  nut Back");
		savingOnline.clickToDynamicBackIcon(driver, TransactionReport_Data.ReportTite.TRANSACTION_DETAIL);

		log.info("TC_06_19: Click  nut Back");
		savingOnline.clickToDynamicBackIcon(driver, TransactionReport_Data.ReportTite.TRANSACTION_REPORT);

		log.info("TC_06_20: Click  nut Home");
		savingOnline.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");

	}

	@Test
	public void TC_07_TatToanTaiKhoanTietKiem_VND_3Thang_LaiNhapGoc() {
		String savingDate = "";
		String expiredDate = "";

		log.info("TC_07_1_Click Tat toan tai khoan tiet kiem");
		homePage.scrollDownToText(driver, "Tín dụng");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, SavingOnline_Data.EXPIRE_SAVING_ACCOUNT);

		log.info("TC_07_2_Chon so tai khoan tiet kiem");
		savingOnline.clickToDynamicDropDownInSavingOnline(SavingOnline_Data.SAVING_NUMBER_ACCOUNT);
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, savingAccount);

		log.info("TC_07_3_Chon tai khoan dich");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, SavingOnline_Data.CHOOSE_DESTINATION_ACCOUNT);
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, account);

		surplus = convertAvailableBalanceCurrentcyOrFeeToLong(savingOnline.getDynamicTextAvailableBalanceInSavingOnline(SavingOnline_Data.AVAILABLE_BALANCE));

		log.info("TC_07_4_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, SavingOnline_Data.CONTINUE_BUTTON);

		log.info("TC_07_5_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, SavingOnline_Data.CONTINUE_BUTTON);

		log.info("TC_07_06_Kiem tra man hinh xac thuc thong tin");
		log.info("TC_07_06_1: Kiem tra tai khoan tiet kiem");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, SavingOnline_Data.SAVING_NUMBER_ACCOUNT), savingAccount);

		log.info("TC_07_06_2: Kiem tra ten tai khoan tiet kiem");

		log.info("TC_07_06_3: Kiem tra ky han gui");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, SavingOnline_Data.IN_TERM).toLowerCase(), SavingOnline_Data.THREE_MONTH.toLowerCase());

		log.info("TC_07_06_4: Kiem tra lai suat");

		log.info("TC_07_06_5: Kiem tra ngay gui tien");
		savingDate = transReport.getDynamicTextInTransactionDetail(driver, SavingOnline_Data.SAVING_DATE);
		verifyTrue(checkDateLessThanNow(savingDate));

		log.info("TC_07_06_6: Kiem tra ngay den han");
		expiredDate = getForwardMonthAndForwardDayFolowDate(savingDate, 3, 0);
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, SavingOnline_Data.EXPIRE_DATE), expiredDate);

		log.info("TC_07_06_7: Kiem tra so tien gui goc");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, SavingOnline_Data.ORIGINAL_MONEY), addCommasToLong(info1.money) + " VND");

		log.info("TC_07_06_8: Kiem tra so tien thuc huong");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, SavingOnline_Data.REAL_MONEY), addCommasToLong(info1.money) + " VND");

		log.info("TC_07_06_9: Kiem tra tai khoan dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, SavingOnline_Data.DESTINATION_ACCOUNT), account);

		log.info("TC_07_07_Chon phuong thuc xac thuc");
		savingOnline.scrollDownToText(driver, SavingOnline_Data.ACCURACY_METHOD);
		savingOnline.clickToDynamicDropDown(driver, SavingOnline_Data.ACCURACY_METHOD);
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, SavingOnline_Data.SMS_OTP);

		log.info("TC_07_08_Kiem tra so tien phi");
		transferFee = 0;

		log.info("TC_07_09_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, SavingOnline_Data.CONTINUE_BUTTON);

		savingOnline.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, SavingOnline_Data.CONTINUE_BUTTON);

		savingOnline.clickToDynamicButton(driver, SavingOnline_Data.CONTINUE_BUTTON);

		transactionNumber = savingOnline.getDynamicTextInTransactionDetail(driver, SavingOnline_Data.TRANSACTION_CODE);

		log.info("TC_07_10_Click Thuc hien giao dich moi");
		savingOnline.clickToDynamicButton(driver, SavingOnline_Data.NEW_TRANSACTION_PERFORM);

		log.info("TC_07_11_Chon tai khoan dich");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, SavingOnline_Data.CHOOSE_DESTINATION_ACCOUNT);
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, account);

		actualAvailableBalance = convertAvailableBalanceCurrentcyOrFeeToLong(savingOnline.getDynamicTextAvailableBalanceInSavingOnline(SavingOnline_Data.AVAILABLE_BALANCE));
		availableBalance = canculateAvailableBalances(surplus, -Long.parseLong(info1.money), transferFee);
		verifyEquals(actualAvailableBalance, availableBalance);

	}

	@Test
	public void TC_08_TatToanTaiKhoanTietKiem_VND_3Thang_LaiNhapGoc_BaoCao() {
		log.info("TC_08_1: Click  nut Back");
		savingOnline.clickToDynamicBackIcon(driver, SavingOnline_Data.EXPIRE_SAVING_ACCOUNT);

		log.info("TC_08_2: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_08_3: Click Bao Cao giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransactionReport_Data.ReportTite.TRANSACTION_REPORT);

		log.info("TC_08_4: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransactionReport_Data.ReportTite.ALL_TYPE_TRANSACTION);

		log.info("TC_08_5: Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, SavingOnline_Data.EXPIRE_SAVING_ACCOUNT);

		log.info("TC_08_6: Click Chon Tai Khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_08_7: Chon tai Khoan chuyen");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, account);

		log.info("TC_08_8: Click Tim Kiem");
		transReport.clickToDynamicButton(driver, SavingOnline_Data.SEARCH_BUTTON);

		log.info("TC_08_9: Kiem tra ngay tao giao dich hien thi");
		String reportTime = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
		verifyEquals(convertDateTimeIgnoreHHmmss(reportTime), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_08_10: Kiem tra so tien tat toan");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("+ " + addCommasToLong(info1.money) + " VND"));

		log.info("TC_08_11: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_08_12: Kiem tra thoi gian tao giao dich hien thi");
		reportTime = transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch");
		verifyEquals(convertDateTimeIgnoreHHmmss(reportTime), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_08_13: Kiem tra so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTite.TRANSACTION_NUMBER), transactionNumber);

		log.info("TC_08_14: Kiem tra tai khoan/the trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTite.ACCOUNT_CARD), savingAccount);

		log.info("TC_08_14: Kiem tra so tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTite.DESTINATION_ACCOUNT_CARD), account);

		log.info("TC_08_15: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTite.TRANSACTION_MONEY).contains(addCommasToLong(info1.money) + " VND"));

		log.info("TC_08_16: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTite.TRANSACTION_TYPE), SavingOnline_Data.EXPIRE_SAVING_ACCOUNT);

		log.info("TC_08_17: Kiem tra noi dung giao dich");
		String expectContent = "MBVCB." + transactionNumber + ".Tat toan TK FD, SO TK " + savingAccount + ", KY HAN " + convertVietNameseStringToString(SavingOnline_Data.THREE_MONTH);
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTite.TRANSACTION_CONTENT), expectContent);

		log.info("TC_08_18: Click  nut Back");
		savingOnline.clickToDynamicBackIcon(driver, TransactionReport_Data.ReportTite.TRANSACTION_DETAIL);

		log.info("TC_08_19: Click  nut Back");
		savingOnline.clickToDynamicBackIcon(driver, TransactionReport_Data.ReportTite.TRANSACTION_REPORT);

		log.info("TC_08_20: Click  nut Home");
		savingOnline.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();
	}

}
