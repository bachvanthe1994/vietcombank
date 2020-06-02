package vnpay.vietcombank.saving_online;

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
import model.SavingOnlineInfo;
import model.SourceAccountModel;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.SavingOnlinePageObject;
import pageObjects.SettingVCBSmartOTPPageObject;
import pageObjects.TransactionReportPageObject;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.SavingOnline_Data;
import vietcombank_test_data.TransactionReport_Data;

public class SavingOnline_Flow_Part_3_SmartOTP extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private SavingOnlinePageObject savingOnline;
	private TransactionReportPageObject transReport;
	private SettingVCBSmartOTPPageObject smartOTP;
	long transferFee = 0;
	double transferFeeCurrentcy = 0;
	private String transferTime;
	String savingAccount;
	private String transactionNumber;
	SourceAccountModel sourceAccount = new SourceAccountModel();
	String account, balance, currentcy, nameCustomer = "";

	SavingOnlineInfo info = new SavingOnlineInfo("", SavingOnline_Data.ONE_MONTH, SavingOnline_Data.MONEY, SavingOnline_Data.PAY_INTEREST_METHOD_01);
	SavingOnlineInfo info1 = new SavingOnlineInfo("", SavingOnline_Data.THREE_MONTH, SavingOnline_Data.MONEY, SavingOnline_Data.PAY_INTEREST_METHOD_01);

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
		smartOTP = PageFactoryManager.getSettingVCBSmartOTPPageObject(driver);

	}

	private long surplus, availableBalance, actualAvailableBalance;

	@Test
	public void TC_01_MoTaiKhoanTietKiem_VND_1Thang_LaiNhapGoc() throws GeneralSecurityException, IOException {
		log.info("TC_01_0_Setup smart OTP");
		smartOTP.setupSmartOTP(LogIn_Data.Login_Account.Smart_OTP, getDataInCell(6));
		
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
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, SavingOnline_Data.VCB_SMART_OTP);

		log.info("TC_01_10_Kiem tra so tien phi");
		transferFee = 0;

		log.info("TC_01_11_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, SavingOnline_Data.CONTINUE_BUTTON);

		savingOnline.inputToDynamicSmartOTP(driver, LogIn_Data.Login_Account.Smart_OTP, "com.VCB:id/otp");

		savingOnline.clickToDynamicButton(driver, SavingOnline_Data.CONTINUE_BUTTON);
		
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
	
	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();
	}

}
