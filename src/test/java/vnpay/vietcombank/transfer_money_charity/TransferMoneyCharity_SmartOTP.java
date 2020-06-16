package vnpay.vietcombank.transfer_money_charity;

import java.io.IOException;
import java.math.BigDecimal;
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
import model.TransferCharity;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.SettingVCBSmartOTPPageObject;
import pageObjects.TransactionReportPageObject;
import pageObjects.TransferMoneyCharityPageObject;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransactionReport_Data;
import vietcombank_test_data.TransferMoneyCharity_Data;

public class TransferMoneyCharity_SmartOTP extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferMoneyCharityPageObject transferMoneyCharity;
	private TransactionReportPageObject transReport;
	private SettingVCBSmartOTPPageObject setupSmartOTP;
	private String transferTime;
	private String transactionNumber;
	long fee = 0;
	double transferFeeCurrentcy = 0;
	String password, currentcy = "";
	SourceAccountModel sourceAccount = new SourceAccountModel();
	String account, smartOTP, organization = "";

	TransferCharity info = new TransferCharity("", "", "100000", "Do Minh Duc", "So 18 ngo 3 Thai Ha", "Ho ngheo", "VCB - Smart OTP");
	TransferCharity info1 = new TransferCharity("", "", "10", "Do Minh Duc", "So 18 ngo 3 Thai Ha", "Ho ngheo", "VCB - Smart OTP");
	TransferCharity info2 = new TransferCharity("", "", "10", "Do Minh Duc", "So 18 ngo 3 Thai Ha", "Ho ngheo", "VCB - Smart OTP");
	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException, GeneralSecurityException {
		startServer();

		smartOTP = getDataInCell(6);
		organization = getDataInCell(31);
		
		log.info("Before class: Mo app ");
		if (deviceType.contains("android")) {
			driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		} else if (deviceType.contains("ios")) {
			driver = openIOSApp(deviceName, udid, url);
		}
		login = PageFactoryManager.getLoginPageObject(driver);
		homePage = PageFactoryManager.getHomePageObject(driver);
		transferMoneyCharity = PageFactoryManager.getTransferMoneyCharityPageObject(driver);
		setupSmartOTP = PageFactoryManager.getSettingVCBSmartOTPPageObject(driver);
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
				
		login.Global_login(phone, pass, opt);

		password = pass;
		
		setupSmartOTP.setupSmartOTP(LogIn_Data.Login_Account.Smart_OTP, smartOTP);
		
	}

	private long surplus, availableBalance, actualAvailableBalance;
	private double surplusCurrentcy, availableBalanceCurrentcy, actualAvailableBalanceCurrentcy;

	@Test
	public void TC_01_ChuyenTienTuThienBangVND_SmartOTP() throws GeneralSecurityException, IOException {
		log.info("TC_01_1_Click Chuyen tien tu thien");
		transferMoneyCharity.scrollDownToText(driver, TransferMoneyCharity_Data.STATUS_TRANSFER_MONEY);
		homePage.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyCharity_Data.TRANSFER_CHARITY);

		log.info("TC_01_2_Chon tai khoan nguon");
		transferMoneyCharity.clickToDynamicDropDown(driver, TransferMoneyCharity_Data.SOURCE_ACCOUNT);
		sourceAccount = transferMoneyCharity.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);
		account = sourceAccount.account;

		surplus = convertAvailableBalanceCurrentcyOrFeeToLong(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, TransferMoneyCharity_Data.AVAILABLE_BALANCE));

		log.info("TC_01_3_Chon Quy/ To chuc tu thien");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyCharity_Data.ORGANIRATION_CHARITY);
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, organization);

		log.info("TC_01_4_Nhap so tien ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.money, TransferMoneyCharity_Data.MONEY_CHARITY);

		log.info("TC_01_5_Nhap ten nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.name, TransferMoneyCharity_Data.NAME_CHARITY);

		log.info("TC_01_6_Nhap dia chi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.address, TransferMoneyCharity_Data.ADDRESS_CHARITY);

		log.info("TC_01_7_Hoan canh nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.status, TransferMoneyCharity_Data.STATUS_CHARITY);

		log.info("TC_01_8_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, TransferMoneyCharity_Data.CONTINUE_BUTTON);

		log.info("TC_01_9_Kiem tra man hinh xac nhan thong tin");

		log.info("TC_01_9_1_Kiem tra tai khoan nguon");
		verifyEquals(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, TransferMoneyCharity_Data.SOURCE_ACCOUNT), account);

		log.info("TC_01_9_2_Kiem tra to chuc");
		verifyEquals(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, TransferMoneyCharity_Data.DESTINATION_NAME), organization);

		log.info("TC_01_9_2_Kiem tra tai khoan dich");
		String destinationAccount = transferMoneyCharity.getDynamicTextInTransactionDetail(driver, TransferMoneyCharity_Data.DESTINATION_ACCOUNT);

		String expectMoney = addCommasToLong(info.money) + " VND";
		log.info("TC_01_9_3_Kiem tra so tien ung ho");
		verifyEquals(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, TransferMoneyCharity_Data.MONEY_CHARITY), expectMoney);

		log.info("TC_01_9_4_Kiem tra ten nguoi ung ho");
		verifyEquals(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, TransferMoneyCharity_Data.NAME_CHARITY), info.name);

		log.info("TC_01_9_5_Kiem tra dia chi nguoi chuyen");
		verifyEquals(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, TransferMoneyCharity_Data.ADDRESS_CHARITY), info.address);

		log.info("TC_01_9_6_Kiem tra hoan canh");
		verifyEquals(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, TransferMoneyCharity_Data.STATUS_CHARITY), info.status);

		log.info("TC_01_10_Chon phuong thuc xac thuc");
		transferMoneyCharity.scrollDownToText(driver, TransferMoneyCharity_Data.ACCURACY_METHOD);
		transferMoneyCharity.clickToDynamicDropDown(driver, TransferMoneyCharity_Data.ACCURACY_METHOD);
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info.authenticationMethod);
		
		String transferFee = transferMoneyCharity.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_FEE);
		fee = convertAvailableBalanceCurrentcyOrFeeToLong(transferFee);
		
		
		log.info("TC_01_11_Nhap smart otp");
		transferMoneyCharity.clickToDynamicButton(driver, TransferMoneyCharity_Data.CONTINUE_BUTTON);

		transferMoneyCharity.inputToDynamicSmartOTP(driver, LogIn_Data.Login_Account.Smart_OTP, "com.VCB:id/otp");

		transferMoneyCharity.clickToDynamicButton(driver, TransferMoneyCharity_Data.CONTINUE_BUTTON);
		
		transferMoneyCharity.clickToDynamicButton(driver, TransferMoneyCharity_Data.CONTINUE_BUTTON);

		log.info("TC_01_12_Kiem tra man hinh Chuyen khoan thanh cong");
		log.info("TC_01_12_1_Kiem tra Chuyen khoan thanh cong");
		verifyTrue(transferMoneyCharity.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyCharity_Data.SUCCESS_TRANSFER_MONEY));

		log.info("TC_01_12_2_Kiem tra ten nguoi thu huong");
		verifyEquals(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, TransferMoneyCharity_Data.RECIEVED_NAME), organization);

		log.info("TC_01_12_3_Kiem tra tai khoan dich");
		verifyEquals(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, TransferMoneyCharity_Data.RECIEVED_ACCOUNT), destinationAccount);

		log.info("TC_01_12_4_Kiem tra nut Thuc hien giao dich moi");
		verifyTrue(transferMoneyCharity.isDynamicButtonDisplayed(driver, TransferMoneyCharity_Data.NEW_TRANSACTION_PERFORM));

		log.info("TC_01_12_5_Lay ma giao dich");
		transferTime = transferMoneyCharity.getTransferTimeSuccess(driver, TransferMoneyCharity_Data.SUCCESS_TRANSFER_MONEY);
		transactionNumber = transferMoneyCharity.getDynamicTextInTransactionDetail(driver, TransferMoneyCharity_Data.TRANSACTION_CODE);

		log.info("TC_01_13_Click Thuc hien giao dich moi");
		transferMoneyCharity.clickToDynamicButton(driver, TransferMoneyCharity_Data.NEW_TRANSACTION_PERFORM);

		log.info("TC_01_14_Kiem tra so du kha dung luc sau");
		transferMoneyCharity.clickToDynamicDropDown(driver, TransferMoneyCharity_Data.SOURCE_ACCOUNT);
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, account);

		actualAvailableBalance = convertAvailableBalanceCurrentcyOrFeeToLong(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, TransferMoneyCharity_Data.AVAILABLE_BALANCE));
		availableBalance = canculateAvailableBalances(surplus, Long.parseLong(info.money), fee);
		verifyEquals(actualAvailableBalance, availableBalance);

	}

	@Test
	public void TC_02_ChuyenTienTuThienBangVND_SmartOTP_BaoCao() {
		log.info("TC_02_1: Click  nut Back");
		transferMoneyCharity.clickToDynamicBackIcon(driver, TransferMoneyCharity_Data.TRANSFER_CHARITY);

		log.info("TC_02_2: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_02_3: Click Bao Cao giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransactionReport_Data.ReportTitle.TRANSACTION_REPORT);

		log.info("TC_02_4: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransactionReport_Data.ReportTitle.ALL_TYPE_TRANSACTION);

		log.info("TC_02_5: Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyCharity_Data.TRANSFER_CHARITY);

		log.info("TC_02_6: Click Chon Tai Khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_02_7: Chon tai Khoan chuyen");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, account);

		log.info("TC_02_8: Click Tim Kiem");
		transReport.clickToDynamicButton(driver, TransactionReport_Data.ReportTitle.SEARCH_BUTTON);

		log.info("TC_02_9: Kiem tra ngay tao giao dich hien thi");
		String reportTime1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
		verifyEquals(convertDateTimeIgnoreHHmmss(reportTime1), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_02_10: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(info.status));

		log.info("TC_02_11: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(info.money) + " VND"));

		log.info("TC_02_12: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_02_13: Kiem tra thoi gian tao giao dich hien thi");
		String reportTime2 = transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TIME_TRANSACTION);
		verifyEquals(reportTime2, reportTime1);

		log.info("TC_02_14: Kiem tra ma giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_NUMBER), transactionNumber);

		log.info("TC_02_15: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.ACCOUNT_CARD), account);

		log.info("TC_02_16: Kiem tra so tai khoan ghi co");

		log.info("TC_02_17: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_MONEY).contains(addCommasToLong(info.money) + " VND"));

		log.info("TC_02_18: Kiem tra ten quy, to chuc tu thien");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransferMoneyCharity_Data.ORGANIRATION_CHARITY_NAME), organization);

		log.info("TC_02_19: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_FEE), addCommasToLong(fee + "") + " VND");

		log.info("TC_02_20: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_TYPE), TransferMoneyCharity_Data.TRANSFER_CHARITY);

		log.info("TC_02_21: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_CONTENT).contains(info.status));

		log.info("TC_02_22: Click  nut Back");
		transferMoneyCharity.clickToDynamicBackIcon(driver, TransactionReport_Data.ReportTitle.TRANSACTION_DETAIL);

		log.info("TC_02_23: Click  nut Back");
		transferMoneyCharity.clickToDynamicBackIcon(driver, TransactionReport_Data.ReportTitle.TRANSACTION_REPORT);

		log.info("TC_02_24: Click  nut Home");
		transferMoneyCharity.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@Test
	public void TC_03_ChuyenTienTuThienBangNgoaiTe_SmartOTP() {
		log.info("TC_03_1_Click Chuyen tien tu thien");
		transferMoneyCharity.scrollDownToText(driver, TransferMoneyCharity_Data.STATUS_TRANSFER_MONEY);
		homePage.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyCharity_Data.TRANSFER_CHARITY);

		log.info("TC_03_2_Chon tai khoan nguon");
		transferMoneyCharity.clickToDynamicDropDown(driver, TransferMoneyCharity_Data.SOURCE_ACCOUNT);
		sourceAccount = transferMoneyCharity.chooseSourceAccount(driver, Constants.MONEY_CHECK_EUR, Constants.EUR_CURRENCY);
		account = sourceAccount.account;

		surplusCurrentcy = convertAvailableBalanceCurrentcyToDouble(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, TransferMoneyCharity_Data.AVAILABLE_BALANCE));

		log.info("TC_03_3_Chon Quy/ To chuc tu thien");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyCharity_Data.ORGANIRATION_CHARITY);
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, organization);

		currentcy = getCurrentcyMoney(transferMoneyCharity.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTiGia"));
		log.info("TC_03_4_Nhap so tien ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info1.money, TransferMoneyCharity_Data.MONEY_CHARITY);

		log.info("TC_03_5_Nhap ten nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info1.name, TransferMoneyCharity_Data.NAME_CHARITY);

		log.info("TC_03_6_Nhap dia chi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info1.address, TransferMoneyCharity_Data.ADDRESS_CHARITY);

		log.info("TC_03_7_Hoan canh nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info1.status, TransferMoneyCharity_Data.STATUS_CHARITY);

		log.info("TC_03_8_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, TransferMoneyCharity_Data.CONTINUE_BUTTON);

		log.info("TC_03_9_Kiem tra man hinh xac nhan thong tin");
		log.info("TC_03_9_1_Kiem tra man tai khoan nguon");
		verifyEquals(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, TransferMoneyCharity_Data.SOURCE_ACCOUNT), account);

		log.info("TC_03_9_2_Kiem tra to chuc");
		verifyEquals(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, TransferMoneyCharity_Data.DESTINATION_NAME), organization);

		log.info("TC_03_9_3_Kiem tra tai khoan dich");
		String destinationAccount = transferMoneyCharity.getDynamicTextInTransactionDetail(driver, TransferMoneyCharity_Data.DESTINATION_ACCOUNT);

		log.info("TC_03_9_4_Kiem tra so tien ung ho");
		String actualMoney = transferMoneyCharity.getDynamicTextInTransactionDetail(driver, TransferMoneyCharity_Data.MONEY_CHARITY);
		String expectedMoney = String.format("%.2f", new BigDecimal(Double.parseDouble(info1.money))) + " EUR";
		verifyEquals(actualMoney, expectedMoney);

		log.info("TC_03_9_5_Kiem tra ten nguoi ung ho");
		verifyEquals(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, TransferMoneyCharity_Data.NAME_CHARITY), info1.name);

		log.info("TC_03_9_6_Kiem tra dia chia nguoi chuyen");
		verifyEquals(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, TransferMoneyCharity_Data.ADDRESS_CHARITY), info1.address);

		log.info("TC_03_9_7_Kiem tra hoan canh");
		verifyEquals(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, TransferMoneyCharity_Data.STATUS_CHARITY), info1.status);

		log.info("TC_03_10_Chon phuong thuc xac thuc");
		transferMoneyCharity.scrollDownToText(driver, TransferMoneyCharity_Data.ACCURACY_METHOD);
		transferMoneyCharity.clickToDynamicDropDown(driver, TransferMoneyCharity_Data.ACCURACY_METHOD);
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info1.authenticationMethod);
		
		String transferFee = transferMoneyCharity.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_FEE);
		fee = convertAvailableBalanceCurrentcyOrFeeToLong(transferFee);
		transferFeeCurrentcy = convertVNeseMoneyToEUROOrUSD(String.valueOf(fee), currentcy);


		log.info("TC_03_11_Nhap smart otp");
		transferMoneyCharity.clickToDynamicButton(driver, TransferMoneyCharity_Data.CONTINUE_BUTTON);

		transferMoneyCharity.inputToDynamicSmartOTP(driver, LogIn_Data.Login_Account.Smart_OTP, "com.VCB:id/otp");

		transferMoneyCharity.clickToDynamicButton(driver, TransferMoneyCharity_Data.CONTINUE_BUTTON);
		
		transferMoneyCharity.clickToDynamicButton(driver, TransferMoneyCharity_Data.CONTINUE_BUTTON);

		log.info("TC_03_12_Kiem tra man hinh Chuyen khoan thanh cong");
		verifyTrue(transferMoneyCharity.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyCharity_Data.SUCCESS_TRANSFER_MONEY));

		log.info("TC_03_12_1_Kiem tra ten nguoi huong");
		verifyEquals(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, TransferMoneyCharity_Data.RECIEVED_NAME), organization);

		log.info("TC_02_12_2_Kiem tra tai khoan dich");
		verifyEquals(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, TransferMoneyCharity_Data.RECIEVED_ACCOUNT), destinationAccount);

		log.info("TC_03_12_3_Kiem tra nut thuc hien giao dich moi");
		verifyTrue(transferMoneyCharity.isDynamicButtonDisplayed(driver, TransferMoneyCharity_Data.NEW_TRANSACTION_PERFORM));

		log.info("TC_03_12_4_Lay ma giao dich");
		transferTime = transferMoneyCharity.getTransferTimeSuccess(driver, TransferMoneyCharity_Data.SUCCESS_TRANSFER_MONEY);
		transactionNumber = transferMoneyCharity.getDynamicTextInTransactionDetail(driver, TransferMoneyCharity_Data.TRANSACTION_CODE);

		log.info("TC_03_13_Click Thuc hien giao dich moi");
		transferMoneyCharity.clickToDynamicButton(driver, TransferMoneyCharity_Data.NEW_TRANSACTION_PERFORM);

		log.info("TC_03_14_Kiem tra so du kha dung luc sau");
		transferMoneyCharity.clickToDynamicDropDown(driver, TransferMoneyCharity_Data.SOURCE_ACCOUNT);
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, account);
		actualAvailableBalanceCurrentcy = convertAvailableBalanceCurrentcyToDouble(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, TransferMoneyCharity_Data.AVAILABLE_BALANCE));
		availableBalanceCurrentcy = canculateAvailableBalancesCurrentcy(surplusCurrentcy, Double.parseDouble(info1.money), transferFeeCurrentcy);
		verifyEquals(actualAvailableBalanceCurrentcy, availableBalanceCurrentcy);
	}

	@Test
	public void TC_04_ChuyenTienTuThienBangNgoaiTe_SmartOTP_BaoCao() {
		log.info("TC_04_1: Click  nut Back");
		transferMoneyCharity.clickToDynamicBackIcon(driver, TransferMoneyCharity_Data.TRANSFER_CHARITY);

		log.info("TC_04_2: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_04_3: Click Bao Cao giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransactionReport_Data.ReportTitle.TRANSACTION_REPORT);

		log.info("TC_04_5: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransactionReport_Data.ReportTitle.ALL_TYPE_TRANSACTION);

		log.info("TC_04_6: Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyCharity_Data.TRANSFER_CHARITY);

		log.info("TC_04_7: Click Chon Tai Khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_04_8: Chon tai Khoan chuyen");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, account);

		log.info("TC_04_9: Click Tim Kiem");
		transReport.clickToDynamicButton(driver, TransactionReport_Data.ReportTitle.SEARCH_BUTTON);

		log.info("TC_04_10: Kiem tra thoi gian tao giao dich");
		String reportTime1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
		verifyEquals(convertDateTimeIgnoreHHmmss(reportTime1), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_04_11: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(info1.status));

		log.info("TC_04_12: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + info1.money + " EUR"));

		log.info("TC_04_13: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_04_14: Kiem tra thoi gian tao giao dich hien thi");
		String reportTime2 = transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TIME_TRANSACTION);
		verifyEquals(reportTime2, reportTime1);

		log.info("TC_04_15: Kiem tra thoi gian tao giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_NUMBER), transactionNumber);

		log.info("TC_04_16: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.ACCOUNT_CARD), account);

		log.info("TC_04_17: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_FEE), addCommasToLong(fee + "") + " VND");

		log.info("TC_04_18: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_MONEY).contains(addCommasToDouble(info1.money) + " EUR"));

		log.info("TC_04_19: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.CHANGE_MONEY).contains(convertEURO_USDToVNeseMoney(info1.money, currentcy)));

		log.info("TC_04_20: Kiem tra ten quy, to chuc tu thien");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransferMoneyCharity_Data.ORGANIRATION_CHARITY_NAME), organization);

		log.info("TC_04_21: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_FEE), addCommasToLong(fee + "") + " VND");

		log.info("TC_04_22: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_TYPE), TransferMoneyCharity_Data.TRANSFER_CHARITY);

		log.info("TC_04_23: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_CONTENT).contains(info1.status));

		log.info("TC_04_24: Click  nut Back");
		transferMoneyCharity.clickToDynamicBackIcon(driver, TransactionReport_Data.ReportTitle.TRANSACTION_DETAIL);

		log.info("TC_04_25: Click  nut Back");
		transferMoneyCharity.clickToDynamicBackIcon(driver, TransactionReport_Data.ReportTitle.TRANSACTION_REPORT);

		log.info("TC_04_26: Click  nut Home");
		transferMoneyCharity.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@Test
	public void TC_05_ChuyenTienTuThienBangVND_SmartOTP() {
		log.info("TC_05_1_Click Chuyen tien tu thien");
		transferMoneyCharity.scrollDownToText(driver, TransferMoneyCharity_Data.STATUS_TRANSFER_MONEY);
		homePage.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyCharity_Data.TRANSFER_CHARITY);

		log.info("TC_05_2_Chon tai khoan nguon");
		transferMoneyCharity.clickToDynamicDropDown(driver, TransferMoneyCharity_Data.SOURCE_ACCOUNT);
		sourceAccount = transferMoneyCharity.chooseSourceAccount(driver, Constants.MONEY_CHECK_USD, Constants.USD_CURRENCY);
		account = sourceAccount.account;

		surplusCurrentcy = convertAvailableBalanceCurrentcyToDouble(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, TransferMoneyCharity_Data.AVAILABLE_BALANCE));

		log.info("TC_05_3_Chon Quy/ To chuc tu thien");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyCharity_Data.ORGANIRATION_CHARITY);
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, organization);

		currentcy = getCurrentcyMoney(transferMoneyCharity.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTiGia"));
		log.info("TC_05_4_Nhap so tien ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info2.money, TransferMoneyCharity_Data.MONEY_CHARITY);

		log.info("TC_05_5_Nhap ten nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info2.name, TransferMoneyCharity_Data.NAME_CHARITY);

		log.info("TC_05_6_Nhap dia chi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info2.address, TransferMoneyCharity_Data.ADDRESS_CHARITY);

		log.info("TC_05_7_Hoan canh nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info2.status, TransferMoneyCharity_Data.STATUS_CHARITY);

		log.info("TC_05_8_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, TransferMoneyCharity_Data.CONTINUE_BUTTON);

		log.info("TC_05_9_Kiem tra man xac dinh giao dich");
		log.info("TC_05_9_1_Kiem tra tai khoan nguon");
		verifyEquals(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, TransferMoneyCharity_Data.SOURCE_ACCOUNT), account);

		log.info("TC_05_9_2_Kiem tra to chuc");
		verifyEquals(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, TransferMoneyCharity_Data.DESTINATION_NAME), organization);

		log.info("TC_05_9_3_Kiem tra tai khoan dich");
		String destinationAccount = transferMoneyCharity.getDynamicTextInTransactionDetail(driver, TransferMoneyCharity_Data.DESTINATION_ACCOUNT);

		String expectMoney = addCommasToLong(info2.money) + " VND";
		log.info("TC_05_9_4_Kiem tra so tien ung ho");
		verifyEquals(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, TransferMoneyCharity_Data.MONEY_CHARITY), expectMoney);

		log.info("TC_05_9_5_Kiem tra ten nguoi ung ho");
		verifyEquals(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, TransferMoneyCharity_Data.NAME_CHARITY), info2.name);

		log.info("TC_05_9_6_Kiem tra dia chi nguoi chuyen");
		verifyEquals(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, TransferMoneyCharity_Data.ADDRESS_CHARITY), info2.address);

		log.info("TC_05_9_7_Kiem tra hoan canh");
		verifyEquals(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, TransferMoneyCharity_Data.STATUS_CHARITY), info2.status);

		log.info("TC_05_10_Chon phuong thuc xac thuc");
		transferMoneyCharity.scrollDownToText(driver, TransferMoneyCharity_Data.ACCURACY_METHOD);
		transferMoneyCharity.clickToDynamicDropDown(driver, TransferMoneyCharity_Data.ACCURACY_METHOD);
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info2.authenticationMethod);

		log.info("TC_05_10_01_Kiem tra so tien phi");

		String transferFee = transferMoneyCharity.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_FEE);
		fee = convertAvailableBalanceCurrentcyOrFeeToLong(transferFee);
		transferFeeCurrentcy = convertVNeseMoneyToEUROOrUSD(String.valueOf(fee), currentcy);
		
		log.info("TC_05_11_Nhap smart otp");
		transferMoneyCharity.clickToDynamicButton(driver, TransferMoneyCharity_Data.CONTINUE_BUTTON);

		transferMoneyCharity.inputToDynamicSmartOTP(driver, LogIn_Data.Login_Account.Smart_OTP, "com.VCB:id/otp");

		transferMoneyCharity.clickToDynamicButton(driver, TransferMoneyCharity_Data.CONTINUE_BUTTON);
		
		transferMoneyCharity.clickToDynamicButton(driver, TransferMoneyCharity_Data.CONTINUE_BUTTON);

		log.info("TC_05_12_Kiem tra man hinh Chuyen khoan thanh cong");
		log.info("TC_05_12_1_Kiem tra chuyen khoan thanh cong");
		verifyTrue(transferMoneyCharity.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyCharity_Data.SUCCESS_TRANSFER_MONEY));

		log.info("TC_05_12_2_Kiem tra tai khoan dich");
		verifyEquals(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, TransferMoneyCharity_Data.RECIEVED_ACCOUNT), destinationAccount);

		log.info("TC_05_12_3_Kiem tra nut thuc hien giao dich moi");
		verifyTrue(transferMoneyCharity.isDynamicButtonDisplayed(driver, TransferMoneyCharity_Data.NEW_TRANSACTION_PERFORM));

		log.info("TC_05_12_4_Lay ma giao dich");
		transferTime = transferMoneyCharity.getTransferTimeSuccess(driver, TransferMoneyCharity_Data.SUCCESS_TRANSFER_MONEY);
		transactionNumber = transferMoneyCharity.getDynamicTextInTransactionDetail(driver, TransferMoneyCharity_Data.TRANSACTION_CODE);

		log.info("TC_05_13_Click Thuc hien giao dich moi");
		transferMoneyCharity.clickToDynamicButton(driver, TransferMoneyCharity_Data.NEW_TRANSACTION_PERFORM);

		log.info("TC_05_14_Kiem tra so du kha dung luc sau");
		transferMoneyCharity.clickToDynamicDropDown(driver, TransferMoneyCharity_Data.SOURCE_ACCOUNT);
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, account);
		actualAvailableBalanceCurrentcy = convertAvailableBalanceCurrentcyToDouble(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, TransferMoneyCharity_Data.AVAILABLE_BALANCE));
		availableBalanceCurrentcy = canculateAvailableBalancesCurrentcy(surplusCurrentcy, Double.parseDouble(info1.money), transferFeeCurrentcy);
		verifyEquals(actualAvailableBalanceCurrentcy, availableBalanceCurrentcy);

	}

	@Test
	public void TC_06_ChuyenTienTuThienBangVND_SmartOTP_BaoCao() {
		log.info("TC_06_1 : Click  nut Back");
		transferMoneyCharity.clickToDynamicBackIcon(driver, TransferMoneyCharity_Data.TRANSFER_CHARITY);

		log.info("TC_06_2: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_06_3: Click Bao cao giao dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransactionReport_Data.ReportTitle.TRANSACTION_REPORT);

		log.info("TC_06_4: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransactionReport_Data.ReportTitle.ALL_TYPE_TRANSACTION);

		log.info("TC_06_5: Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyCharity_Data.TRANSFER_CHARITY);

		log.info("TC_06_6: Click Chon Tai Khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_06_7: Chon tai Khoan chuyen");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, account);

		log.info("TC_06_8: Click Tim Kiem");
		transReport.clickToDynamicButton(driver, TransactionReport_Data.ReportTitle.SEARCH_BUTTON);

		log.info("TC_06_09: Kiem tra thoi gian tao dao dich");
		String reportTime1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
		verifyEquals(convertDateTimeIgnoreHHmmss(reportTime1), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_06_10: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(info2.status));

		log.info("TC_06_11: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(info2.money) + " VND"));

		log.info("TC_06_12: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_06_13: Kiem tra thoi gian tao giao dich hien thi");
		String reportTime2 = transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TIME_TRANSACTION);
		verifyEquals(reportTime2, reportTime1);

		log.info("TC_06_14: Kiem tra thoi gian tao giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_NUMBER), transactionNumber);

		log.info("TC_06_15: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.ACCOUNT_CARD), account);

		log.info("TC_06_16: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_MONEY).contains(addCommasToLong(info2.money) + " VND"));

		log.info("TC_06_17: Kiem tra ten quy, to chuc tu thien");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransferMoneyCharity_Data.ORGANIRATION_CHARITY_NAME), organization);

		log.info("TC_06_18: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_FEE), addCommasToLong(fee + "") + " VND");

		log.info("TC_06_19: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_TYPE), TransferMoneyCharity_Data.TRANSFER_CHARITY);

		log.info("TC_06_20: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_CONTENT).contains(info2.status));

		log.info("TC_06_21: Click  nut Back");
		transferMoneyCharity.clickToDynamicBackIcon(driver, TransactionReport_Data.ReportTitle.TRANSACTION_DETAIL);

		log.info("TC_06_22: Click  nut Back");
		transferMoneyCharity.clickToDynamicBackIcon(driver, TransactionReport_Data.ReportTitle.TRANSACTION_REPORT);

		log.info("TC_06_23: Click  nut Home");
		transferMoneyCharity.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
