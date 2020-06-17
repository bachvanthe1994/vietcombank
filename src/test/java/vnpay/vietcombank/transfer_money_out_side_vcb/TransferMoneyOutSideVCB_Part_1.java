package vnpay.vietcombank.transfer_money_out_side_vcb;

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
import model.TransferOutSideVCB_Info;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.TransactionReportPageObject;
import pageObjects.TransferMoneyOutSideVCBPageObject;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransactionReport_Data.ReportTitle;
import vietcombank_test_data.TransferMoneyOutVCB_Data.TitleOutVCB;
import vietcombank_test_data.TransferMoneyQuick_Data;

public class TransferMoneyOutSideVCB_Part_1 extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferMoneyOutSideVCBPageObject transferMoneyOutSide;
	private TransactionReportPageObject transReport;
	private String transferTime;
	private String transactionNumber;
	long transferFee = 0;
	double transferFeeCurrentcy = 0;
	String password, currentcy = "";
	String account, destinationAccount = "";
	SourceAccountModel sourceAccount = new SourceAccountModel();

	TransferOutSideVCB_Info info = new TransferOutSideVCB_Info("", "", TitleOutVCB.NAME_RECIEVED, TitleOutVCB.BANK_RECIEVED, TitleOutVCB.MONEY_VND, TitleOutVCB.TRANSACTION_FEE_SENT, TitleOutVCB.TRANSACTION_CONTENT, TitleOutVCB.TITLE_OTP);
	TransferOutSideVCB_Info info1 = new TransferOutSideVCB_Info("", "", TitleOutVCB.NAME_RECIEVED, TitleOutVCB.BANK_RECIEVED, TitleOutVCB.MONEY_VND, TitleOutVCB.TRANSACTION_FEE_RECIEVED, TitleOutVCB.TRANSACTION_CONTENT, TitleOutVCB.TITLE_OTP);
	TransferOutSideVCB_Info info2 = new TransferOutSideVCB_Info("", "", TitleOutVCB.NAME_RECIEVED, TitleOutVCB.BANK_RECIEVED, TitleOutVCB.MONEY_CURRENCY, TitleOutVCB.TRANSACTION_FEE_SENT, TitleOutVCB.TRANSACTION_CONTENT, TitleOutVCB.TITLE_OTP);
	TransferOutSideVCB_Info info3 = new TransferOutSideVCB_Info("", "", TitleOutVCB.NAME_RECIEVED, TitleOutVCB.BANK_RECIEVED, TitleOutVCB.MONEY_CURRENCY, TitleOutVCB.TRANSACTION_FEE_RECIEVED, TitleOutVCB.TRANSACTION_CONTENT, TitleOutVCB.TITLE_OTP);
	TransferOutSideVCB_Info info4 = new TransferOutSideVCB_Info("", "", TitleOutVCB.NAME_RECIEVED, TitleOutVCB.BANK_RECIEVED, TitleOutVCB.MONEY_VND, TitleOutVCB.TRANSACTION_FEE_SENT, TitleOutVCB.TRANSACTION_CONTENT, TitleOutVCB.PASSWORD_TITLE);
	TransferOutSideVCB_Info info5 = new TransferOutSideVCB_Info("", "", TitleOutVCB.NAME_RECIEVED, TitleOutVCB.BANK_RECIEVED, TitleOutVCB.MONEY_VND, TitleOutVCB.TRANSACTION_FEE_RECIEVED, TitleOutVCB.TRANSACTION_CONTENT, TitleOutVCB.PASSWORD_TITLE);

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException, GeneralSecurityException {
		startServer();
		destinationAccount = getDataInCell(34);
		log.info("Before class: Mo app ");
		if (deviceType.contains("android")) {
			driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		} else if (deviceType.contains("ios")) {
			driver = openIOSApp(deviceName, udid, url);
		}
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
		homePage = PageFactoryManager.getHomePageObject(driver);
		transferMoneyOutSide = PageFactoryManager.getTransferMoneyOutSideVCBPageObject(driver);
		password = pass;

	}

	private long surplus, availableBalance, actualAvailableBalance;
	private double surplusCurrentcy, availableBalanceCurrentcy, actualAvailableBalanceCurrentcy;

	@Test
	public void TC_01_ChuyenTienLienNganHang_VND_CoPhiGiaoDichNguoiChuyenTraXacThucBangOTP() {
		log.info("TC_01_1_Click Chuyen tien toi ngan hang khac");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.TITLE_TRANSFER_OUTSIDE);
		clickPopupAfter15h30();

		log.info("TC_01_2_Chon tai khoan nguon");
		transferMoneyOutSide.clickToTextID(driver, "com.VCB:id/tvContent");
		sourceAccount = transferMoneyOutSide.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, "VND");
		account = sourceAccount.account;
		transferMoneyOutSide.sleep(driver, 1000);
		transferMoneyOutSide.scrollUpToText(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		surplus = convertAvailableBalanceCurrentcyOrFeeToLong(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.SURPLUS));

		log.info("TC_01_3_Nhap tai khoan thu huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, destinationAccount, TitleOutVCB.ACCOUT_TO);

		log.info("TC_01_4_Nhap ten nguoi huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info.name, TitleOutVCB.BENEFICIARY_NAME);

		log.info("TC_01_5_Chon ngan hang huong");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.DESTINATION_BANK);
		transferMoneyOutSide.inputToDynamicInputBox(driver, info.destinationBank, ReportTitle.SEARCH_BUTTON);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info.destinationBank);

		log.info("TC_01_6_Nhap so tien");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info.money, TitleOutVCB.MONEY);

		log.info("TC_01_7_Chọn phí giao dịch");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.TRANSACTION_FEE_SENT);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.TRANSFER_PERSON);

		log.info("TC_01_7_Nhap noi dung chuyen tien");
		transferMoneyOutSide.inputToDynamicInputBoxByHeader(driver, info.note, TitleOutVCB.TRANSACTION_INFOMATION, "3");

		log.info("TC_01_8_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEXT);

		log.info("TC_01_9_Kiem tra man hinh xac nhan thong tin");
		log.info("TC_01_9_1_Kiem tra tai khoan nguon");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.ACCOUNT_FROM_LABEL), account);

		log.info("TC_01_9_2_Kiem tra tai khoan dich");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.DESTINATION_ACCOUNT_VND), destinationAccount);

		log.info("TC_01_9_3_Kiem tra ten nguoi huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.DESTINATION_ACCOUNT_NAME), info.name);

		log.info("TC_01_9_4_Kiem tra ngan hang dich");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.DESTINATION_BANK), info.destinationBank);

		log.info("TC_01_9_5_Kiem tra so tien");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.MONEY), addCommasToLong(info.money) + " VND");

		log.info("TC_01_9_6_Kiem tra noi dung chuyen tien");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.CONTENT), info.note);

		log.info("TC_01_10_Chon phuong thuc xac thuc");
		transferMoneyOutSide.scrollDownToText(driver, TitleOutVCB.CHOOSE_METHOD);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.PASSWORD_TITLE);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info.authenticationMethod);

		log.info("TC_01_10_01_Kiem tra so tien phi");
		String fee = transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.TRANSACTION_FEE);
		transferFee = convertAvailableBalanceCurrentcyOrFeeToLong(fee);

		log.info("TC_01_11_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEXT);

		transferMoneyOutSide.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, TitleOutVCB.NEXT);

		transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEXT);

		log.info("TC_01_12_Kiem tra man hinh Chuyen khoan thanh cong");
		log.info("TC_01_12_1_Kiem tra Chuyen khoan thanh cong");
		verifyEquals(transferMoneyOutSide.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY);

		log.info("TC_01_12_2_Kiem tra ten nguoi thu huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.BENEFICIARY_NAME), info.name);

		log.info("TC_01_12_3_Kiem tra tai khoan dich");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.DESTINATION_ACCOUNT), destinationAccount);

		log.info("TC_01_12_3_Kiem tra ngan hang huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.DESTINATION_BANK), info.destinationBank);

		log.info("TC_01_12_5_Kiem tra noi dung");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.CONTENT), info.note);

		log.info("TC_01_12_5_Kiem tra nut Thuc hien giao dich moi");
		verifyTrue(transferMoneyOutSide.isDynamicButtonDisplayed(driver, TitleOutVCB.NEW_TRANSFER));

		log.info("TC_01_12_6_Lay ma giao dich");
		transferTime = transferMoneyOutSide.getTransferTimeSuccess(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY);
		transactionNumber = transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.TRANSECTION_NUMBER);

		log.info("TC_01_13_Click Thuc hien giao dich moi");
		transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEW_TRANSFER);
		clickPopupAfter15h30();

		log.info("TC_01_14_Kiem tra so du kha dung luc sau");
		transferMoneyOutSide.scrollUpToText(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		transferMoneyOutSide.clickToDynamicDropDown(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, account);
		transferMoneyOutSide.sleep(driver, 1000);
		transferMoneyOutSide.scrollUpToText(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		actualAvailableBalance = convertAvailableBalanceCurrentcyOrFeeToLong(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.SURPLUS));
		availableBalance = canculateAvailableBalances(surplus, Long.parseLong(info.money), transferFee);
		verifyEquals(actualAvailableBalance, availableBalance);

	}

	@Test
	public void TC_02_ChuyenTienLienNganHang_VND_CoPhiGiaoDichNguoiChuyenTraXacThucBangOTP_BaoCao() {
		log.info("TC_02_1: Click  nut Back");
		homePage.clickToDynamicBackIcon(driver, TitleOutVCB.TITLE_TRANSFER_OUTSIDE);

		log.info("TC_02_2: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_02_3: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_02_4: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.ALL_TYPE_TRANSACTION);

		log.info("TC_02_5: Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSFER_OUTSIDE_VCB);

		log.info("TC_02_6: Click Chon Tai Khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_02_7: Chon tai Khoan chuyen");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, account);

		log.info("TC_02_8: Click Tim Kiem");
		transReport.clickToDynamicButton(driver, ReportTitle.SEARCH_BUTTON);

		log.info("TC_02_9: Kiem tra ngay tao giao dich hien thi");
		String reportTime1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
		verifyEquals(convertDateTimeIgnoreHHmmss(reportTime1), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_02_10: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(info.note));

		log.info("TC_02_11: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(info.money) + " VND"));

		log.info("TC_02_12: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_02_13: Kiem tra thoi gian tao giao dich hien thi");
		String reportTime2 = transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TIME_TRANSACTION);
		verifyEquals(reportTime2, reportTime1);

		log.info("TC_02_14: Kiem tra thoi gian tao giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_NUMBER), transactionNumber);

		log.info("TC_02_15: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_TRANSFER), account);

		log.info("TC_02_16: Kiem tra so tai khoan ghi co");

		log.info("TC_02_17: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_MONEY).contains(addCommasToLong(info.money) + " VND"));

		log.info("TC_02_18: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.FEE), TitleOutVCB.TRANSFER_PERSON);

		log.info("TC_02_19: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_TYPE), TitleOutVCB.TITLE_TRANSFER_OUTSIDE);

		log.info("TC_02_20: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_CONTENT).contains(info.note));

		log.info("TC_02_21: Click  nut Back");
		transferMoneyOutSide.clickToDynamicBackIcon(driver, ReportTitle.TRANSACTION_DETAIL);

		log.info("TC_02_22: Click  nut Back");
		transferMoneyOutSide.clickToDynamicBackIcon(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_02_23: Click  nut Home");
		transferMoneyOutSide.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@Test
	public void TC_03_ChuyenTienLienNganHang_VND_CoPhiGiaoDichNguoiNhanTraXacThucBangOTP() {
		log.info("TC_03_1_Click Chuyen tien toi ngan hang khac");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.TITLE_TRANSFER_OUTSIDE);
		clickPopupAfter15h30();

		log.info("TC_03_2_Chon tai khoan nguon");
		transferMoneyOutSide.scrollUpToText(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		transferMoneyOutSide.clickToDynamicDropDown(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		sourceAccount = transferMoneyOutSide.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, "VND");
		account = sourceAccount.account;
		transferMoneyOutSide.sleep(driver, 1000);
		transferMoneyOutSide.scrollUpToText(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		surplus = convertAvailableBalanceCurrentcyOrFeeToLong(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.SURPLUS));

		log.info("TC_03_3_Nhap tai khoan thu huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, destinationAccount, TitleOutVCB.ACCOUT_TO);

		log.info("TC_03_4_Nhap ten nguoi huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info1.name, TitleOutVCB.BENEFICIARY_NAME);

		log.info("TC_03_5_Chon ngan hang huong");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.DESTINATION_BANK);
		transferMoneyOutSide.inputToDynamicInputBox(driver, info1.destinationBank, ReportTitle.SEARCH_BUTTON);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info1.destinationBank);

		log.info("TC_03_6_Nhap so tien");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info1.money, TitleOutVCB.MONEY);

		log.info("TC_03_7_Chọn phí giao dịch");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.TRANSACTION_FEE_SENT);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.RECEIVER_PAYER);

		log.info("TC_03_8_Nhap noi dung chuyen tien");
		transferMoneyOutSide.inputToDynamicInputBoxByHeader(driver, info1.note, TitleOutVCB.TRANSACTION_INFOMATION, "3");

		log.info("TC_03_9_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEXT);

		log.info("TC_03_10_Kiem tra man hinh xac nhan thong tin");
		log.info("TC_03_10_1_Kiem tra tai khoan nguon");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.ACCOUNT_FROM_LABEL), account);

		log.info("TC_03_10_2_Kiem tra tai khoan dich");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.DESTINATION_ACCOUNT_VND), destinationAccount);

		log.info("TC_03_10_3_Kiem tra ten nguoi huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.DESTINATION_ACCOUNT_NAME), info1.name);

		log.info("TC_03_10_4_Kiem tra ngan hang dich");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.DESTINATION_BANK), info1.destinationBank);

		log.info("TC_03_10_5_Kiem tra so tien");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.MONEY), String.format("%,d", Long.parseLong(info1.money)) + " VND");

		log.info("TC_03_10_6_Kiem tra noi dung chuyen tien");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.CONTENT), info1.note);

		log.info("TC_03_11_Chon phuong thuc xac thuc");
		transferMoneyOutSide.scrollDownToText(driver, TitleOutVCB.CHOOSE_METHOD);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.PASSWORD_TITLE);
		transferFee = 0;
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info1.authenticationMethod);

		log.info("TC_03_11_01_Kiem tra so tien phi");
		verifyTrue(transferMoneyOutSide.isDynamicMessageAndLabelTextDisplayed(driver, TitleOutVCB.RECEIVER_PAYER));

		log.info("TC_03_12_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEXT);

		transferMoneyOutSide.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, TitleOutVCB.NEXT);

		transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEXT);

		log.info("TC_03_13_Kiem tra man hinh Chuyen khoan thanh cong");
		log.info("TC_03_13_1_Kiem tra Chuyen khoan thanh cong");
		verifyEquals(transferMoneyOutSide.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY);

		log.info("TC_03_13_2_Kiem tra ten nguoi thu huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.BENEFICIARY_NAME), info1.name);

		log.info("TC_03_13_3_Kiem tra tai khoan dich");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.DESTINATION_ACCOUNT), destinationAccount);

		log.info("TC_03_13_3_Kiem tra ngan hang huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.DESTINATION_BANK), info1.destinationBank);

		log.info("TC_03_13_5_Kiem tra noi dung");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.CONTENT), info1.note);

		log.info("TC_03_13_5_Kiem tra nut Thuc hien giao dich moi");
		verifyTrue(transferMoneyOutSide.isDynamicButtonDisplayed(driver, TitleOutVCB.NEW_TRANSFER));

		log.info("TC_03_13_6_Lay ma giao dich");
		transferTime = transferMoneyOutSide.getTransferTimeSuccess(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY);
		transactionNumber = transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.TRANSECTION_NUMBER);

		log.info("TC_03_14_Click Thuc hien giao dich moi");
		transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEW_TRANSFER);
		clickPopupAfter15h30();

		log.info("TC_03_14_Kiem tra so du kha dung luc sau");
		transferMoneyOutSide.scrollUpToText(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		transferMoneyOutSide.clickToDynamicDropDown(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, account);
		transferMoneyOutSide.sleep(driver, 1000);
		transferMoneyOutSide.scrollUpToText(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		actualAvailableBalance = convertAvailableBalanceCurrentcyOrFeeToLong(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.SURPLUS));
		availableBalance = canculateAvailableBalances(surplus, Long.parseLong(info1.money), 0);
		verifyEquals(actualAvailableBalance, availableBalance);

	}

	@Test
	public void TC_04_ChuyenTienLienNganHangCoPhiGiaoDichNguoiNhanTraXacThucBangOTP_BaoCao() {
		log.info("TC_04_1: Click  nut Back");
		homePage.clickToDynamicBackIcon(driver, TitleOutVCB.TITLE_TRANSFER_OUTSIDE);

		log.info("TC_04_2: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_04_3: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_04_4: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.ALL_TYPE_TRANSACTION);

		log.info("TC_04_5: Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSFER_OUTSIDE_VCB);

		log.info("TC_04_6: Click Chon Tai Khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_04_7: Chon tai Khoan chuyen");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, account);

		log.info("TC_04_8: Click Tim Kiem");
		transReport.clickToDynamicButton(driver, ReportTitle.SEARCH_BUTTON);

		log.info("TC_04_9: Kiem tra ngay tao giao dich hien thi");
		String reportTime1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
		verifyEquals(convertDateTimeIgnoreHHmmss(reportTime1), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_04_10: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(info1.note));

		log.info("TC_04_11: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(info1.money) + " VND"));

		log.info("TC_04_12: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_04_14: Kiem tra ngay tao giao dich hien thi");
		String reportTime2 = transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TIME_TRANSACTION);
		verifyEquals(reportTime2, reportTime1);

		log.info("TC_04_15: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_TRANSFER), account);

		log.info("TC_04_16: Kiem tra so tai khoan ghi co");

		log.info("TC_04_17: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_MONEY).contains(addCommasToLong(info1.money) + " VND"));

		log.info("TC_04_18: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.FEE), TitleOutVCB.RECEIVER_PAYER);

		log.info("TC_04_19: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_TYPE), TitleOutVCB.TITLE_TRANSFER_OUTSIDE);

		log.info("TC_04_20: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_CONTENT).contains(info1.note));

		log.info("TC_04_21: Click  nut Back");
		transferMoneyOutSide.clickToDynamicBackIcon(driver, ReportTitle.TRANSACTION_DETAIL);

		log.info("TC_04_22: Click  nut Back");
		transferMoneyOutSide.clickToDynamicBackIcon(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_04_23: Click  nut Home");
		transferMoneyOutSide.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@Test
	public void TC_05_ChuyenTienLienNganHangNgoaite_EUR_CoPhiGiaoDichNguoiChuyenTraXacThucBangOTP() {
		log.info("TC_05_1_Click Chuyen tien toi ngan hang khac");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.TITLE_TRANSFER_OUTSIDE);
		clickPopupAfter15h30();

		log.info("TC_05_2_Chon tai khoan nguon");
		transferMoneyOutSide.scrollUpToText(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		transferMoneyOutSide.clickToDynamicDropDown(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		sourceAccount = transferMoneyOutSide.chooseSourceAccount(driver, Constants.MONEY_CHECK_EUR, Constants.EUR_CURRENCY);
		account = sourceAccount.account;
		transferMoneyOutSide.sleep(driver, 1000);
		transferMoneyOutSide.scrollUpToText(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		surplusCurrentcy = convertAvailableBalanceCurrentcyToDouble(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.SURPLUS));

		log.info("TC_05_3_Nhap tai khoan thu huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, destinationAccount, TitleOutVCB.ACCOUT_TO);

		log.info("TC_05_4_Nhap ten nguoi huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info2.name, TitleOutVCB.BENEFICIARY_NAME);

		log.info("TC_05_5_Chon ngan hang huong");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.DESTINATION_BANK);
		transferMoneyOutSide.inputToDynamicInputBox(driver, info2.destinationBank, ReportTitle.SEARCH_BUTTON);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info2.destinationBank);

		currentcy = getCurrentcyMoney(transferMoneyOutSide.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTiGia"));
		log.info("TC_05_6_Nhap so tien");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info2.money, TitleOutVCB.MONEY);

		log.info("TC_05_7_Chọn phí giao dịch");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.TRANSACTION_FEE_SENT);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.TRANSFER_PERSON);

		log.info("TC_05_8_Nhap noi dung chuyen tien");
		transferMoneyOutSide.inputToDynamicInputBoxByHeader(driver, info2.note, TitleOutVCB.TRANSACTION_INFOMATION, "3");

		log.info("TC_05_9_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEXT);

		log.info("TC_05_10_Kiem tra man hinh xac nhan thong tin");
		log.info("TC_05_10_1_Kiem tra tai khoan nguon");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.ACCOUNT_FROM_LABEL), account);

		log.info("TC_05_10_2_Kiem tra tai khoan dich");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.DESTINATION_ACCOUNT_VND), destinationAccount);

		log.info("TC_05_10_3_Kiem tra ten nguoi huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.DESTINATION_ACCOUNT_NAME), info2.name);

		log.info("TC_05_10_4_Kiem tra ngan hang dich");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.DESTINATION_BANK), info2.destinationBank);

		log.info("TC_05_10_5_Kiem tra so tien quy doi");
		String actualMoney = transferMoneyOutSide.getDynamicTextInTextViewLine2(driver, TitleOutVCB.MONEY);
		String expectMoney = convertEURO_USDToVNeseMoney(info2.money, currentcy);
		verifyEquals(actualMoney, expectMoney);

		log.info("TC_05_10_6_Kiem tra so tien");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.MONEY), addCommasToDouble(info2.money) + " EUR");
		log.info("TC_05_10_7_Kiem tra noi dung chuyen tien");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.CONTENT), info2.note);

		log.info("TC_05_11_Chon phuong thuc xac thuc");
		transferMoneyOutSide.scrollDownToText(driver, TitleOutVCB.CHOOSE_METHOD);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.PASSWORD_TITLE);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info2.authenticationMethod);

		log.info("TC_05_11_01_Kiem tra so tien phi");
		String fee = transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.TRANSACTION_FEE);
		transferFee = convertAvailableBalanceCurrentcyOrFeeToLong(fee);
		transferFeeCurrentcy = convertVNeseMoneyToEUROOrUSD(String.valueOf(transferFee), currentcy);

		log.info("TC_05_12_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEXT);

		transferMoneyOutSide.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, TitleOutVCB.NEXT);

		transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEXT);

		log.info("TC_05_13_Kiem tra man hinh Chuyen khoan thanh cong");
		log.info("TC_05_13_1_Kiem tra Chuyen khoan thanh cong");
		verifyEquals(transferMoneyOutSide.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY);

		log.info("TC_05_13_2_Kiem tra ten nguoi thu huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.BENEFICIARY_NAME), info2.name);

		log.info("TC_05_13_3_Kiem tra tai khoan dich");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.DESTINATION_ACCOUNT), destinationAccount);

		log.info("TC_05_13_3_Kiem tra ngan hang huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.DESTINATION_BANK), info2.destinationBank);

		log.info("TC_05_13_5_Kiem tra noi dung");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.CONTENT), info2.note);

		log.info("TC_05_13_5_Kiem tra nut Thuc hien giao dich moi");
		verifyTrue(transferMoneyOutSide.isDynamicButtonDisplayed(driver, TitleOutVCB.NEW_TRANSFER));

		log.info("TC_05_13_6_Lay ma giao dich");
		transferTime = transferMoneyOutSide.getTransferTimeSuccess(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY);
		transactionNumber = transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.TRANSECTION_NUMBER);

		log.info("TC_05_14_Click Thuc hien giao dich moi");
		transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEW_TRANSFER);
		clickPopupAfter15h30();

		log.info("TC_05_14_Kiem tra so du kha dung luc sau");
		transferMoneyOutSide.scrollUpToText(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		transferMoneyOutSide.clickToDynamicDropDown(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, account);
		transferMoneyOutSide.sleep(driver, 1000);
		transferMoneyOutSide.scrollUpToText(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		actualAvailableBalanceCurrentcy = convertAvailableBalanceCurrentcyToDouble(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.SURPLUS));
		availableBalanceCurrentcy = canculateAvailableBalancesCurrentcy(surplusCurrentcy, Double.parseDouble(info2.money), transferFeeCurrentcy);
		verifyEquals(actualAvailableBalanceCurrentcy, availableBalanceCurrentcy);

	}

	@Test
	public void TC_06_ChuyenTienLienNganHangNgoaite_EUR_CoPhiGiaoDichNguoiChuyenTraXacThucBangOTP_BaoCao() {
		log.info("TC_06_1: Click  nut Back");
		homePage.clickToDynamicBackIcon(driver, TitleOutVCB.TITLE_TRANSFER_OUTSIDE);

		log.info("TC_06_2: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_06_3: Click Bao cao giao dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_06_4: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.ALL_TYPE_TRANSACTION);

		log.info("TC_06_5: Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSFER_OUTSIDE_VCB);

		log.info("TC_06_6: Click Chon Tai Khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_06_7: Chon tai Khoan chuyen");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, account);

		log.info("TC_06_8: Click Tim Kiem");
		transReport.clickToDynamicButton(driver, ReportTitle.SEARCH_BUTTON);

		log.info("TC_06_9: Kiem tra ngay tao giao dich hien thi");
		String reportTime1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
		verifyEquals(convertDateTimeIgnoreHHmmss(reportTime1), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_06_10: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(info2.note));

		log.info("TC_06_11: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + info2.money + " EUR"));

		log.info("TC_06_12: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_06_13: Kiem tra ngay tao giao dich hien thi");
		String reportTime2 = transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TIME_TRANSACTION);
		verifyEquals(reportTime2, reportTime1);

		log.info("TC_06_14: Kiem tra thoi gian tao giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_NUMBER), transactionNumber);

		log.info("TC_06_15: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_TRANSFER), account);

		log.info("TC_06_16: Kiem tra so tai khoan ghi co");

		log.info("TC_06_17: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_MONEY).contains(addCommasToDouble(info2.money) + " EUR"));

		log.info("TC_06_18: Kiem tra so tien quy doi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền quy đổi").contains(convertEURO_USDToVNeseMoney(info2.money, currentcy)));

		log.info("TC_06_19: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.FEE), TitleOutVCB.TRANSFER_PERSON);

		log.info("TC_06_20: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_TYPE), TitleOutVCB.TITLE_TRANSFER_OUTSIDE);

		log.info("TC_06_21: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_CONTENT).contains(info2.note));

		log.info("TC_06_22: Click  nut Back");
		transferMoneyOutSide.clickToDynamicBackIcon(driver, ReportTitle.TRANSACTION_DETAIL);

		log.info("TC_06_23: Click  nut Back");
		transferMoneyOutSide.clickToDynamicBackIcon(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_06_24: Click  nut Home");
		transferMoneyOutSide.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@Test
	public void TC_07_ChuyenTienLienNganHangNgoaite_EUR_CoPhiGiaoDichNguoiNhanTraXacThucBangOTP() {
		log.info("TC_07_1_Click Chuyen tien toi ngan hang khac");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.TITLE_TRANSFER_OUTSIDE);
		clickPopupAfter15h30();

		log.info("TC_07_2_Chon tai khoan nguon");
		transferMoneyOutSide.scrollUpToText(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		transferMoneyOutSide.clickToDynamicDropDown(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		sourceAccount = transferMoneyOutSide.chooseSourceAccount(driver, Constants.MONEY_CHECK_EUR, "EUR");
		account = sourceAccount.account;
		transferMoneyOutSide.sleep(driver, 1000);
		transferMoneyOutSide.scrollUpToText(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		surplusCurrentcy = convertAvailableBalanceCurrentcyToDouble(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.SURPLUS));

		log.info("TC_07_3_Nhap tai khoan thu huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, destinationAccount, TitleOutVCB.ACCOUT_TO);

		log.info("TC_07_4_Nhap ten nguoi huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info3.name, TitleOutVCB.BENEFICIARY_NAME);

		log.info("TC_07_5_Chon ngan hang huong");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.DESTINATION_BANK);
		transferMoneyOutSide.inputToDynamicInputBox(driver, info3.destinationBank, ReportTitle.SEARCH_BUTTON);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info3.destinationBank);

		currentcy = getCurrentcyMoney(transferMoneyOutSide.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTiGia"));
		log.info("TC_07_6_Nhap so tien");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info3.money, TitleOutVCB.MONEY);

		log.info("TC_07_7_Chọn phí giao dịch");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.TRANSACTION_FEE_SENT);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.RECEIVER_PAYER);

		log.info("TC_07_8_Nhap noi dung chuyen tien");
		transferMoneyOutSide.inputToDynamicInputBoxByHeader(driver, info3.note, TitleOutVCB.TRANSACTION_INFOMATION, "3");

		log.info("TC_07_9_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEXT);

		log.info("TC_07_10_Kiem tra man hinh xac nhan thong tin");
		log.info("TC_07_10_1_Kiem tra tai khoan nguon");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.ACCOUNT_FROM_LABEL), account);

		log.info("TC_07_10_2_Kiem tra tai khoan dich");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.DESTINATION_ACCOUNT_VND), destinationAccount);

		log.info("TC_07_10_3_Kiem tra ten nguoi huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.DESTINATION_ACCOUNT_NAME), info3.name);

		log.info("TC_07_10_4_Kiem tra ngan hang dich");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.DESTINATION_BANK), info3.destinationBank);

		log.info("TC_07_10_5_Kiem tra so tien quy doi");
		String actualMoney = transferMoneyOutSide.getDynamicTextInTextViewLine2(driver, TitleOutVCB.MONEY);
		String expectMoney = convertEURO_USDToVNeseMoney(info3.money, currentcy);
		verifyEquals(actualMoney, expectMoney);

		log.info("TC_07_10_6_Kiem tra so tien");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.MONEY), addCommasToDouble(info3.money) + " EUR");

		log.info("TC_07_10_7_Kiem tra noi dung chuyen tien");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.CONTENT), info3.note);

		log.info("TC_07_11_Chon phuong thuc xac thuc");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.PASSWORD_TITLE);
		transferFee = 0;
		transferFeeCurrentcy = 0;
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info3.authenticationMethod);

		log.info("TC_07_11_01_Kiem tra so tien phi");
		verifyTrue(transferMoneyOutSide.isDynamicMessageAndLabelTextDisplayed(driver, TitleOutVCB.RECEIVER_PAYER));

		log.info("TC_07_12_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEXT);

		transferMoneyOutSide.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, TitleOutVCB.NEXT);

		transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEXT);

		log.info("TC_07_13_Kiem tra man hinh Chuyen khoan thanh cong");
		log.info("TC_07_13_1_Kiem tra Chuyen khoan thanh cong");
		verifyEquals(transferMoneyOutSide.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY);

		log.info("TC_07_13_2_Kiem tra ten nguoi thu huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.BENEFICIARY_NAME), info3.name);

		log.info("TC_07_13_3_Kiem tra tai khoan dich");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.DESTINATION_ACCOUNT), destinationAccount);

		log.info("TC_07_13_3_Kiem tra ngan hang huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.DESTINATION_BANK), info3.destinationBank);

		log.info("TC_07_13_5_Kiem tra noi dung");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.CONTENT), info3.note);

		log.info("TC_07_13_5_Kiem tra nut Thuc hien giao dich moi");
		verifyTrue(transferMoneyOutSide.isDynamicButtonDisplayed(driver, TitleOutVCB.NEW_TRANSFER));

		log.info("TC_07_13_6_Lay ma giao dich");
		transferTime = transferMoneyOutSide.getTransferTimeSuccess(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY);
		transactionNumber = transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.TRANSECTION_NUMBER);

		log.info("TC_07_14_Click Thuc hien giao dich moi");
		transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEW_TRANSFER);
		clickPopupAfter15h30();

		log.info("TC_07_14_Kiem tra so du kha dung luc sau");
		transferMoneyOutSide.scrollUpToText(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		transferMoneyOutSide.clickToDynamicDropDown(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, account);
		transferMoneyOutSide.sleep(driver, 1000);
		transferMoneyOutSide.scrollUpToText(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		actualAvailableBalanceCurrentcy = convertAvailableBalanceCurrentcyToDouble(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.SURPLUS));
		availableBalanceCurrentcy = canculateAvailableBalancesCurrentcy(surplusCurrentcy, Double.parseDouble(info3.money), 0);
		verifyEquals(actualAvailableBalanceCurrentcy, availableBalanceCurrentcy);

	}

	@Test
	public void TC_08_ChuyenTienLienNganHangNgoaite_EUR_CoPhiGiaoDichNguoiNhanTraXacThucBangOTP_BaoCao() {
		log.info("TC_08_1: Click  nut Back");
		homePage.clickToDynamicBackIcon(driver, TitleOutVCB.TITLE_TRANSFER_OUTSIDE);

		log.info("TC_08_2: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_08_3: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_08_4: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.ALL_TYPE_TRANSACTION);

		log.info("TC_08_5: Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSFER_OUTSIDE_VCB);

		log.info("TC_08_6: Click Chon Tai Khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_08_7: Chon tai Khoan chuyen");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, account);

		log.info("TC_08_8: Click Tim Kiem");
		transReport.clickToDynamicButton(driver, ReportTitle.SEARCH_BUTTON);

		log.info("TC_08_9: Kiem tra ngay tao giao dich hien thi");
		String reportTime1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
		verifyEquals(convertDateTimeIgnoreHHmmss(reportTime1), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_08_11: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(info3.note));

		log.info("TC_08_12: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + info2.money + " EUR"));

		log.info("TC_08_13: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_08_14: Kiem tra ngay tao giao dich hien thi");
		String reportTime2 = transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TIME_TRANSACTION);
		verifyEquals(reportTime2, reportTime1);

		log.info("TC_08_15: Kiem tra thoi gian tao giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_NUMBER), transactionNumber);

		log.info("TC_08_16: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_TRANSFER), account);

		log.info("TC_08_17: Kiem tra so tai khoan ghi co");

		log.info("TC_08_18: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_MONEY).contains(addCommasToDouble(info3.money) + " EUR"));

		log.info("TC_08_19: Kiem tra so tien quy doi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền quy đổi").contains(convertEURO_USDToVNeseMoney(info3.money, currentcy)));

		log.info("TC_08_20: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.FEE), TitleOutVCB.RECEIVER_PAYER);

		log.info("TC_08_21: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_TYPE), TitleOutVCB.TITLE_TRANSFER_OUTSIDE);

		log.info("TC_08_22: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_CONTENT).contains(info3.note));

		log.info("TC_08_23: Click  nut Back");
		transferMoneyOutSide.clickToDynamicBackIcon(driver, ReportTitle.TRANSACTION_DETAIL);

		log.info("TC_08_24: Click  nut Back");
		transferMoneyOutSide.clickToDynamicBackIcon(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_08_25: Click  nut Home");
		transferMoneyOutSide.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@Test
	public void TC_09_ChuyenTienLienNganHang_VND_CoPhiGiaoDichNguoiChuyenTraXacThucBangMatKhau_LuuDanhBaThuHuong() {
		log.info("TC_09_1_Click Chuyen tien toi ngan hang khac");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.TITLE_TRANSFER_OUTSIDE);
		clickPopupAfter15h30();

		log.info("TC_09_2_Chon tai khoan nguon");
		transferMoneyOutSide.scrollUpToText(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		transferMoneyOutSide.clickToDynamicDropDown(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		sourceAccount = transferMoneyOutSide.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, "VND");
		account = sourceAccount.account;
		transferMoneyOutSide.sleep(driver, 1000);
		transferMoneyOutSide.scrollUpToText(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		surplus = convertAvailableBalanceCurrentcyOrFeeToLong(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.SURPLUS));

		log.info("TC_09_3_Nhap tai khoan thu huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, destinationAccount, TitleOutVCB.ACCOUT_TO);

		log.info("TC_09_4_Nhap ten nguoi huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info4.name, TitleOutVCB.BENEFICIARY_NAME);

		log.info("TC_09_5_Chon ngan hang huong");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.DESTINATION_BANK);
		transferMoneyOutSide.inputToDynamicInputBox(driver, info4.destinationBank, ReportTitle.SEARCH_BUTTON);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info4.destinationBank);

		log.info("TC_09_6_Nhap so tien");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info4.money, TitleOutVCB.MONEY);

		log.info("TC_09_7_Chọn phí giao dịch");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.TRANSACTION_FEE_SENT);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.TRANSFER_PERSON);

		log.info("TC_09_8_Nhap noi dung chuyen tien");
		transferMoneyOutSide.inputToDynamicInputBoxByHeader(driver, info4.note, TitleOutVCB.TRANSACTION_INFOMATION, "3");

		log.info("TC_09_9_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEXT);

		log.info("TC_09_10_Kiem tra man hinh xac nhan thong tin");
		log.info("TC_09_10_1_Kiem tra tai khoan nguon");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.ACCOUNT_FROM_LABEL), account);

		log.info("TC_09_10_2_Kiem tra tai khoan dich");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.DESTINATION_ACCOUNT_VND), destinationAccount);

		log.info("TC_09_10_3_Kiem tra ten nguoi huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.DESTINATION_ACCOUNT_NAME), info4.name);

		log.info("TC_09_10_4_Kiem tra ngan hang huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.DESTINATION_BANK), info4.destinationBank);

		log.info("TC_09_10_5_Kiem tra so tien");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.MONEY), addCommasToLong(info4.money) + " VND");

		log.info("TC_09_10_6_Kiem tra noi dung");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.CONTENT), info4.note);

		log.info("TC_09_11_Chon phuong thuc xac thuc");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.PASSWORD_TITLE);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info4.authenticationMethod);

		log.info("TC_09_11_01_Kiem tra so tien phi");
		String fee = transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.TRANSACTION_FEE);
		transferFee = convertAvailableBalanceCurrentcyOrFeeToLong(fee);

		log.info("TC_09_12_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEXT);

		transferMoneyOutSide.inputToDynamicPopupPasswordInput(driver, password, TitleOutVCB.NEXT);

		transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEXT);

		log.info("TC_09_13_Kiem tra man hinh Chuyen khoan thanh cong");
		log.info("TC_09_13_1_Kiem tra Chuyen khoan thanh cong");
		verifyEquals(transferMoneyOutSide.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY);

		log.info("TC_09_13_2_Kiem tra ten nguoi thu huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.BENEFICIARY_NAME), info4.name);

		log.info("TC_09_13_3_Kiem tra tai khoan dich");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.DESTINATION_ACCOUNT), destinationAccount);

		log.info("TC_09_13_3_Kiem tra ngan hang huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.DESTINATION_BANK), info4.destinationBank);

		log.info("TC_09_13_5_Kiem tra noi dung");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.CONTENT), info4.note);

		log.info("TC_09_13_5_Kiem tra nut Thuc hien giao dich moi");
		verifyTrue(transferMoneyOutSide.isDynamicButtonDisplayed(driver, TitleOutVCB.NEW_TRANSFER));

		log.info("TC_09_13_6_Lay ma giao dich");
		transferTime = transferMoneyOutSide.getTransferTimeSuccess(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY);
		transactionNumber = transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.TRANSECTION_NUMBER);

		transferMoneyOutSide.scrollIDownOneTime(driver);
		if (transferMoneyOutSide.getPageSource(driver).contains(TitleOutVCB.SAVE_RECEIVED_ACCOUNT_TEXT)) {
			log.info("TC_09_14_01_Click button chia se");
			transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.SAVE_RECEIVED_ACCOUNT_TEXT);

			log.info("TC_09_14_02_Click button Hoan thanh");
			transferMoneyOutSide.clickToDynamicAcceptButton(driver, "com.VCB:id/btSave");

			log.info("TC_09_14_03_Click Dong");
			transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.CLOSE);

		} else {
			log.info("TC_09_14_Click Thuc hien giao dich moi");
			transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEW_TRANSFER);
		}

		clickPopupAfter15h30();

		log.info("TC_09_14_Kiem tra so du kha dung luc sau");
		transferMoneyOutSide.scrollUpToText(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		transferMoneyOutSide.clickToDynamicDropDown(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, account);
		transferMoneyOutSide.sleep(driver, 1000);
		transferMoneyOutSide.scrollUpToText(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		actualAvailableBalance = convertAvailableBalanceCurrentcyOrFeeToLong(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.SURPLUS));
		availableBalance = canculateAvailableBalances(surplus, Long.parseLong(info4.money), transferFee);
		verifyEquals(actualAvailableBalance, availableBalance);

	}

	@Test
	public void TC_10_ChuyenTienLienNganHang_VND_CoPhiGiaoDichNguoiChuyenTraXacThucBangMatKhau_BaoCao() {
		log.info("TC_10_1: Click  nut Back");
		homePage.clickToDynamicBackIcon(driver, TitleOutVCB.TITLE_TRANSFER_OUTSIDE);

		log.info("TC_10_2: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_10_3: Click Bao cao giao dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_10_4: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.ALL_TYPE_TRANSACTION);

		log.info("TC_10_5: Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSFER_OUTSIDE_VCB);

		log.info("TC_10_6: Click Chon Tai Khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_10_7: Chon tai Khoan chuyen");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, account);

		log.info("TC_10_8: Click Tim Kiem");
		transReport.clickToDynamicButton(driver, ReportTitle.SEARCH_BUTTON);

		log.info("TC_10_9: Kiem tra ngay tao giao dich hien thi");
		String reportTime1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
		verifyEquals(convertDateTimeIgnoreHHmmss(reportTime1), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_10_11: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(info4.note));

		log.info("TC_10_12: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(info4.money) + " VND"));

		log.info("TC_10_13: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_10_14: Kiem tra ngay tao giao dich hien thi");
		String reportTime2 = transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TIME_TRANSACTION);
		verifyEquals(reportTime2, reportTime1);

		log.info("TC_10_15: Kiem tra thoi gian tao giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_NUMBER), transactionNumber);

		log.info("TC_10_16: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_TRANSFER), account);

		log.info("TC_10_17: Kiem tra so tai khoan ghi co");

		log.info("TC_10_18: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_MONEY).contains(addCommasToLong(info4.money) + " VND"));

		log.info("TC_10_19: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.FEE), TitleOutVCB.TRANSFER_PERSON);

		log.info("TC_10_20: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_TYPE), TitleOutVCB.TITLE_TRANSFER_OUTSIDE);

		log.info("TC_10_21: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_CONTENT).contains(info4.note));

		log.info("TC_10_22: Click  nut Back");
		transferMoneyOutSide.clickToDynamicBackIcon(driver, ReportTitle.TRANSACTION_DETAIL);

		log.info("TC_10_23: Click  nut Back");
		transferMoneyOutSide.clickToDynamicBackIcon(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_10_24: Click  nut Home");
		transferMoneyOutSide.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@Test
	public void TC_11_ChuyenTienLienNganHang_VND_CoPhiGiaoDichNguoiNhanTraXacThucBangMatKhau_SuDungDanhBaDaLuu() {
		log.info("TC_11_1_Click Chuyen tien toi ngan hang khac");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.TITLE_TRANSFER_OUTSIDE);
		clickPopupAfter15h30();

		log.info("TC_11_2_Chon tai khoan nguon");
		transferMoneyOutSide.scrollUpToText(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		transferMoneyOutSide.clickToDynamicDropDown(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		sourceAccount = transferMoneyOutSide.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, "VND");
		account = sourceAccount.account;
		transferMoneyOutSide.sleep(driver, 1000);
		transferMoneyOutSide.scrollUpToText(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		surplus = convertAvailableBalanceCurrentcyOrFeeToLong(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.SURPLUS));

		log.info("TC_11_3_Nhap tai khoan thu huong");
		log.info("TC_11_3_1_Nhan tai khoan nhan");
		transferMoneyOutSide.clickToDynamicIconByEdit(driver, TitleOutVCB.ACCOUT_TO);
		transferMoneyOutSide.clickToDynamicTextContains(driver, TitleOutVCB.NAME_RECIEVED);

		log.info("TC_11_6_Nhap so tien");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info5.money, TitleOutVCB.MONEY);

		log.info("TC_11_7_Chọn phí giao dịch");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.TRANSACTION_FEE_SENT);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.RECEIVER_PAYER);

		log.info("TC_11_8_Nhap noi dung chuyen tien");
		transferMoneyOutSide.inputToDynamicInputBoxByHeader(driver, info5.note, TitleOutVCB.TRANSACTION_INFOMATION, "3");

		log.info("TC_11_9_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEXT);

		log.info("TC_11_10_Kiem tra man hinh xac nhan thong tin");
		log.info("TC_11_10_1_Kiem tra tai khoan nguon");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.ACCOUNT_FROM_LABEL), account);

		log.info("TC_11_10_2_Kiem tra tai khoan dich");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.DESTINATION_ACCOUNT_VND), destinationAccount);

		log.info("TC_11_10_3_Kiem tra ten nguoi huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.DESTINATION_ACCOUNT_NAME), info5.name);

		log.info("TC_11_10_4_Kiem tra ngan hang huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.DESTINATION_BANK), info5.destinationBank);

		log.info("TC_11_10_5_Kiem tra so tien");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.MONEY), addCommasToLong(info5.money) + " VND");

		log.info("TC_11_10_6_Kiem tra noi dung chuyen tien");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.CONTENT), info5.note);

		log.info("TC_11_11_Chon phuong thuc xac thuc");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.PASSWORD_TITLE);
		transferFee = 0;
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info5.authenticationMethod);

		log.info("TC_11_11_01_Kiem tra so tien phi");
		verifyTrue(transferMoneyOutSide.isDynamicMessageAndLabelTextDisplayed(driver, TitleOutVCB.RECEIVER_PAYER));

		log.info("TC_11_12_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEXT);

		transferMoneyOutSide.inputToDynamicPopupPasswordInput(driver, password, TitleOutVCB.NEXT);

		transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEXT);

		log.info("TC_11_13_Kiem tra man hinh Chuyen khoan thanh cong");
		log.info("TC_11_13_1_Kiem tra Chuyen khoan thanh cong");
		verifyEquals(transferMoneyOutSide.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY);

		log.info("TC_11_13_2_Kiem tra ten nguoi thu huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.BENEFICIARY_NAME), info5.name);

		log.info("TC_11_13_3_Kiem tra tai khoan dich");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.DESTINATION_ACCOUNT), destinationAccount);

		log.info("TC_11_13_3_Kiem tra ngan hang huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.DESTINATION_BANK), info5.destinationBank);

		log.info("TC_11_13_5_Kiem tra noi dung");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.CONTENT), info5.note);

		log.info("TC_11_13_5_Kiem tra nut Thuc hien giao dich moi");
		verifyTrue(transferMoneyOutSide.isDynamicButtonDisplayed(driver, TitleOutVCB.NEW_TRANSFER));

		log.info("TC_11_13_6_Lay ma giao dich");
		transferTime = transferMoneyOutSide.getTransferTimeSuccess(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY);
		transactionNumber = transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.TRANSECTION_NUMBER);

		log.info("TC_11_14_Click Thuc hien giao dich moi");
		transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEW_TRANSFER);
		clickPopupAfter15h30();

		log.info("TC_11_14_Kiem tra so du kha dung luc sau");
		transferMoneyOutSide.scrollUpToText(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		transferMoneyOutSide.clickToDynamicDropDown(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, account);
		transferMoneyOutSide.sleep(driver, 1000);
		transferMoneyOutSide.scrollUpToText(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		actualAvailableBalance = convertAvailableBalanceCurrentcyOrFeeToLong(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.SURPLUS));
		availableBalance = canculateAvailableBalances(surplus, Long.parseLong(info5.money), 0);
		verifyEquals(actualAvailableBalance, availableBalance);

	}

	@Test
	public void TC_12_ChuyenTienLienNganHang_VND_CoPhiGiaoDichNguoiNhanTraXacThucBangMatKhau_BaoCao() {
		log.info("TC_12_1: Click  nut Back");
		homePage.clickToDynamicBackIcon(driver, TitleOutVCB.TITLE_TRANSFER_OUTSIDE);

		log.info("TC_12_2: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_12_3: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_12_4: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.ALL_TYPE_TRANSACTION);

		log.info("TC_12_5: Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSFER_OUTSIDE_VCB);

		log.info("TC_12_6: Click Chon Tai Khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_12_7: Chon tai Khoan chuyen");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, account);

		log.info("TC_12_8: Click Tim Kiem");
		transReport.clickToDynamicButton(driver, ReportTitle.SEARCH_BUTTON);

		log.info("TC_12_9: Kiem tra ngay tao giao dich hien thi");
		String reportTime1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
		verifyEquals(convertDateTimeIgnoreHHmmss(reportTime1), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_12_11: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(info5.note));

		log.info("TC_12_12: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(info5.money) + " VND"));

		log.info("TC_12_13: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_12_14: Kiem tra ngay tao giao dich hien thi");
		String reportTime2 = transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TIME_TRANSACTION);
		verifyEquals(reportTime2, reportTime1);

		log.info("TC_12_15: Kiem tra thoi gian tao giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_NUMBER), transactionNumber);

		log.info("TC_12_16: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_TRANSFER), account);

		log.info("TC_12_17: Kiem tra so tai khoan ghi co");

		log.info("TC_12_18: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_MONEY).contains(addCommasToLong(info5.money) + " VND"));

		log.info("TC_12_19: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.FEE), TitleOutVCB.RECEIVER_PAYER);

		log.info("TC_12_20: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_TYPE), TitleOutVCB.TITLE_TRANSFER_OUTSIDE);

		log.info("TC_12_21: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_CONTENT).contains(info5.note));

		log.info("TC_12_22: Click  nut Back");
		transferMoneyOutSide.clickToDynamicBackIcon(driver, ReportTitle.TRANSACTION_DETAIL);

		log.info("TC_12_23: Click  nut Back");
		transferMoneyOutSide.clickToDynamicBackIcon(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_12_24: Click  nut Home");
		transferMoneyOutSide.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();
	}

	public void clickPopupAfter15h30() {
		if (transferMoneyOutSide.isDynamicButtonDisplayed(driver, "Hủy")) {
			transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEXT);
		}

	}

}
