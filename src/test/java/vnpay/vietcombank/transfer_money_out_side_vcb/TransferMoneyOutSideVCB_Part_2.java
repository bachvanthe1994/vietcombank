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

public class TransferMoneyOutSideVCB_Part_2 extends Base {
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
	String account, destinationAccount;
	SourceAccountModel sourceAccount = new SourceAccountModel();

	TransferOutSideVCB_Info info6 = new TransferOutSideVCB_Info("", "", TitleOutVCB.NAME_RECIEVED, TitleOutVCB.BANK_RECIEVED, TitleOutVCB.MONEY_CURRENCY, TitleOutVCB.TRANSACTION_FEE_SENT, TitleOutVCB.TRANSACTION_CONTENT, TitleOutVCB.PASSWORD_TITLE);
	TransferOutSideVCB_Info info7 = new TransferOutSideVCB_Info("", "", TitleOutVCB.NAME_RECIEVED, TitleOutVCB.BANK_RECIEVED, TitleOutVCB.MONEY_CURRENCY, TitleOutVCB.TRANSACTION_FEE_RECIEVED, TitleOutVCB.TRANSACTION_CONTENT, TitleOutVCB.PASSWORD_TITLE);

	TransferOutSideVCB_Info info8 = new TransferOutSideVCB_Info("", "", TitleOutVCB.NAME_RECIEVED, TitleOutVCB.BANK_RECIEVED, TitleOutVCB.MONEY_CURRENCY, TitleOutVCB.TRANSACTION_FEE_SENT, TitleOutVCB.TRANSACTION_CONTENT, TitleOutVCB.TITLE_OTP);
	TransferOutSideVCB_Info info9 = new TransferOutSideVCB_Info("", "", TitleOutVCB.NAME_RECIEVED, TitleOutVCB.BANK_RECIEVED, TitleOutVCB.MONEY_CURRENCY, TitleOutVCB.TRANSACTION_FEE_RECIEVED, TitleOutVCB.TRANSACTION_CONTENT, TitleOutVCB.PASSWORD_TITLE);
	TransferOutSideVCB_Info info10 = new TransferOutSideVCB_Info("", "", TitleOutVCB.NAME_RECIEVED, TitleOutVCB.BANK_RECIEVED, TitleOutVCB.MONEY_CURRENCY, TitleOutVCB.TRANSACTION_FEE_RECIEVED, TitleOutVCB.TRANSACTION_CONTENT, TitleOutVCB.TITLE_OTP);
	TransferOutSideVCB_Info info11 = new TransferOutSideVCB_Info("", "", TitleOutVCB.NAME_RECIEVED, TitleOutVCB.BANK_RECIEVED, TitleOutVCB.MONEY_CURRENCY, TitleOutVCB.TRANSACTION_FEE_SENT, TitleOutVCB.TRANSACTION_CONTENT, TitleOutVCB.PASSWORD_TITLE);

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

	private double surplusCurrentcy, availableBalanceCurrentcy, actualAvailableBalanceCurrentcy;

	@Test
	public void TC_13_ChuyenTienLienNganHangCoPhiGiaoDichNguoiChuyenTraNgoaiTe_EUR_XacThucBangMatKhau() {
		log.info("TC_13_1_Click Chuyen tien toi ngan hang khac");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.TITLE_TRANSFER_OUTSIDE);
		clickPopupAfter15h30();

		log.info("TC_13_2_Chon tai khoan nguon");
		transferMoneyOutSide.scrollUpToText(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		transferMoneyOutSide.clickToDynamicDropDown(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		sourceAccount = transferMoneyOutSide.chooseSourceAccount(driver, Constants.MONEY_CHECK_EUR, "EUR");
		account = sourceAccount.account;
		transferMoneyOutSide.sleep(driver, 1000);
		transferMoneyOutSide.scrollUpToText(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		surplusCurrentcy = convertAvailableBalanceCurrentcyToDouble(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.SURPLUS));

		log.info("TC_13_3_Nhap tai khoan thu huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, destinationAccount, TitleOutVCB.ACCOUT_TO);

		log.info("TC_13_4_Nhap ten nguoi huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info6.name, TitleOutVCB.BENEFICIARY_NAME);

		log.info("TC_13_5_Chon ngan hang huong");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.DESTINATION_BANK);
		transferMoneyOutSide.inputToDynamicInputBox(driver, info6.destinationBank, ReportTitle.SEARCH_BUTTON);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info6.destinationBank);

		currentcy = getCurrentcyMoney(transferMoneyOutSide.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTiGia"));
		log.info("TC_13_6_Nhap so tien");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info6.money, TitleOutVCB.MONEY);

		log.info("TC_13_7_Chọn phí giao dịch");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.TRANSACTION_FEE_SENT);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.TRANSFER_PERSON);

		log.info("TC_13_8_Nhap noi dung chuyen tien");
		transferMoneyOutSide.inputToDynamicInputBoxByHeader(driver, info6.note, TitleOutVCB.TRANSACTION_INFOMATION, "3");

		log.info("TC_13_9_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEXT);

		log.info("TC_13_10_Kiem tra man hinh xac nhan thong tin");
		log.info("TC_13_10_1_Kiem tra tai khoan nguon");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.ACCOUNT_FROM_LABEL), account);

		log.info("TC_13_10_2_Kiem tra tai khoan dich");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.DESTINATION_ACCOUNT_VND), destinationAccount);

		log.info("TC_13_10_3_Kiem tra ten nguoi huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.DESTINATION_ACCOUNT_NAME), info6.name);

		log.info("TC_13_10_4_Kiem tra ngan hang huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.DESTINATION_BANK), info6.destinationBank);

		log.info("TC_13_10_5_Kiem tra so tien quy doi");
		String actualMoney = transferMoneyOutSide.getDynamicTextInTextViewLine2(driver, TitleOutVCB.MONEY);
		String expectMoney = convertEURO_USDToVNeseMoney(info6.money, currentcy);
		verifyEquals(actualMoney, expectMoney);

		log.info("TC_13_10_6_Kiem tra so tien");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.MONEY), addCommasToDouble(info6.money) + " EUR");

		log.info("TC_13_10_7_Kiem tra noi dung chuyen tien");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.CONTENT), info6.note);

		log.info("TC_13_11_Chon phuong thuc xac thuc");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.PASSWORD_TITLE);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info6.authenticationMethod);

		log.info("TC_13_11_01_Kiem tra so tien phi");
		String fee = transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.TRANSACTION_FEE);
		transferFee = convertAvailableBalanceCurrentcyOrFeeToLong(fee);
		transferFeeCurrentcy = convertVNeseMoneyToEUROOrUSD(String.valueOf(transferFee), currentcy);

		log.info("TC_13_12_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEXT);

		transferMoneyOutSide.inputToDynamicPopupPasswordInput(driver, password, TitleOutVCB.NEXT);

		transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEXT);

		log.info("TC_13_13_Kiem tra man hinh Chuyen khoan thanh cong");
		log.info("TC_13_13_1_Kiem tra Chuyen khoan thanh cong");
		verifyEquals(transferMoneyOutSide.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY);

		log.info("TC_13_13_2_Kiem tra ten nguoi thu huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.BENEFICIARY_NAME), info6.name);

		log.info("TC_13_13_3_Kiem tra tai khoan dich");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.DESTINATION_ACCOUNT), destinationAccount);

		log.info("TC_13_13_3_Kiem tra ngan hang huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.DESTINATION_BANK), info6.destinationBank);

		log.info("TC_13_13_5_Kiem tra noi dung");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.CONTENT), info6.note);

		log.info("TC_13_13_5_Kiem tra nut Thuc hien giao dich moi");
		verifyTrue(transferMoneyOutSide.isDynamicButtonDisplayed(driver, TitleOutVCB.NEW_TRANSFER));

		log.info("TC_13_13_6_Lay ma giao dich");
		transferTime = transferMoneyOutSide.getTransferTimeSuccess(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY);
		transactionNumber = transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.TRANSECTION_NUMBER);

		log.info("TC_13_14_Click Thuc hien giao dich moi");
		transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEW_TRANSFER);
		clickPopupAfter15h30();

		log.info("TC_13_14_Kiem tra so du kha dung luc sau");
		transferMoneyOutSide.scrollUpToText(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		transferMoneyOutSide.clickToDynamicDropDown(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, account);
		transferMoneyOutSide.sleep(driver, 1000);
		transferMoneyOutSide.scrollUpToText(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		actualAvailableBalanceCurrentcy = convertAvailableBalanceCurrentcyToDouble(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.SURPLUS));
		availableBalanceCurrentcy = canculateAvailableBalancesCurrentcy(surplusCurrentcy, Double.parseDouble(info6.money), transferFeeCurrentcy);
		verifyEquals(actualAvailableBalanceCurrentcy, availableBalanceCurrentcy);

	}

	@Test
	public void TC_14_ChuyenTienLienNganHangCoPhiGiaoDichNguoiChuyenTraNgoaiTe_EUR_XacThucBangMatKhau_BaoCao() {
		log.info("TC_14_1: Click  nut Back");
		homePage.clickToDynamicBackIcon(driver, TitleOutVCB.TITLE_TRANSFER_OUTSIDE);

		log.info("TC_14_2: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_14_3: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_14_4: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.ALL_TYPE_TRANSACTION);

		log.info("TC_14_5: Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSFER_OUTSIDE_VCB);

		log.info("TC_14_6: Click Chon Tai Khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_14_7: Chon tai Khoan chuyen");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, account);

		log.info("TC_14_8: Click Tim Kiem");
		transReport.clickToDynamicButton(driver, ReportTitle.SEARCH_BUTTON);

		log.info("TC_14_9: Kiem tra ngay tao giao dich hien thi");
		String reportTime1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
		verifyEquals(convertDateTimeIgnoreHHmmss(reportTime1), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_14_10: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(info6.note));

		log.info("TC_14_11: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + info6.money + " EUR"));

		log.info("TC_14_12: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_14_13: Kiem tra ngay tao giao dich hien thi");
		String reportTime2 = transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TIME_TRANSACTION);
		verifyEquals(reportTime2, reportTime1);

		log.info("TC_14_14: Kiem tra thoi gian tao giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_NUMBER), transactionNumber);

		log.info("TC_14_15: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_CARD_SOURCE), account);

		log.info("TC_14_16: Kiem tra so tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.DESTINATION_ACCOUNT_CARD), destinationAccount);

		log.info("TC_14_17: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.AMOUNT_TRANSFER).contains(addCommasToDouble(info6.money) + " EUR"));

		log.info("TC_14_18: Kiem tra so tien quy doi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.CHANGE_MONEY).contains(convertEURO_USDToVNeseMoney(info6.money, currentcy)));

		log.info("TC_14_19: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.FEE), TitleOutVCB.TRANSFER_PERSON);

		log.info("TC_14_20: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TYPE_TRANSFER), TitleOutVCB.TITLE_TRANSFER_OUTSIDE);

		log.info("TC_14_21: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_CONTENT).contains(info6.note));

		log.info("TC_14_22: Click  nut Back");
		transferMoneyOutSide.clickToDynamicBackIcon(driver, ReportTitle.DETAIL_TRANSFER);

		log.info("TC_14_23: Click  nut Back");
		transferMoneyOutSide.clickToDynamicBackIcon(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_14_24: Click  nut Home");
		transferMoneyOutSide.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@Test
	public void TC_15_ChuyenTienLienNganHangCoPhiGiaoDichNguoiNhanTraNgoaiTe_EUR_XacThucBangMatKhau() {
		log.info("TC_15_1_Click Chuyen tien toi ngan hang khac");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.TITLE_TRANSFER_OUTSIDE);
		clickPopupAfter15h30();

		log.info("TC_15_2_Chon tai khoan nguon");
		transferMoneyOutSide.scrollUpToText(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		transferMoneyOutSide.clickToDynamicDropDown(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		sourceAccount = transferMoneyOutSide.chooseSourceAccount(driver, Constants.MONEY_CHECK_EUR, "EUR");
		account = sourceAccount.account;
		transferMoneyOutSide.sleep(driver, 1000);
		transferMoneyOutSide.scrollUpToText(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		surplusCurrentcy = convertAvailableBalanceCurrentcyToDouble(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.SURPLUS));

		log.info("TC_15_3_Nhap tai khoan thu huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, destinationAccount, TitleOutVCB.ACCOUT_TO);

		log.info("TC_15_4_Nhap ten nguoi huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info7.name, TitleOutVCB.BENEFICIARY_NAME);

		log.info("TC_15_5_Chon ngan hang huong");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.DESTINATION_BANK);
		transferMoneyOutSide.inputToDynamicInputBox(driver, info7.destinationBank, ReportTitle.SEARCH_BUTTON);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info7.destinationBank);

		currentcy = getCurrentcyMoney(transferMoneyOutSide.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTiGia"));
		log.info("TC_15_6_Nhap so tien");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info7.money, TitleOutVCB.MONEY);

		log.info("TC_15_7_Chọn phí giao dịch");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.TRANSACTION_FEE_SENT);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.RECEIVER_PAYER);

		log.info("TC_15_8_Nhap noi dung chuyen tien");
		transferMoneyOutSide.inputToDynamicInputBoxByHeader(driver, info7.note, TitleOutVCB.TRANSACTION_INFOMATION, "3");

		log.info("TC_15_9_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEXT);

		log.info("TC_15_10_Kiem tra man hinh xac nhan thong tin");
		log.info("TC_15_10_1_Kiem tra tai khoan nguon");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.ACCOUNT_FROM_LABEL), info7.sourceAccount);

		log.info("TC_15_10_2_Kiem tra tai khoan dich");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.DESTINATION_ACCOUNT_VND), destinationAccount);

		log.info("TC_15_10_3_Kiem tra ten nguoi huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.DESTINATION_ACCOUNT_NAME), info7.name);
		
		log.info("TC_15_10_4_Kiem tra ngan hang huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.DESTINATION_BANK), info7.destinationBank);

		log.info("TC_15_10_5_Kiem tra so tien quy doi");
		String actualMoney = transferMoneyOutSide.getDynamicTextInTextViewLine2(driver, TitleOutVCB.MONEY);
		String expectMoney = convertEURO_USDToVNeseMoney(info6.money, currentcy);
		verifyEquals(actualMoney, expectMoney);

		log.info("TC_15_10_6_Kiem tra so tien");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.MONEY), addCommasToDouble(info7.money) + " EUR");

		log.info("TC_15_10_7_Kiem tra noi dung chuyen tien");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.CONTENT), info7.note);

		log.info("TC_15_11_Chon phuong thuc xac thuc");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.PASSWORD_TITLE);
		transferFee = 0;
		transferFeeCurrentcy = 0;
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info7.authenticationMethod);

		log.info("TC_15_11_01_Kiem tra so tien phi");
		verifyTrue(transferMoneyOutSide.isDynamicMessageAndLabelTextDisplayed(driver, TitleOutVCB.RECEIVER_PAYER));

		log.info("TC_15_12_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEXT);

		transferMoneyOutSide.inputToDynamicPopupPasswordInput(driver, password, TitleOutVCB.NEXT);

		transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEXT);

		log.info("TC_15_13_Kiem tra man hinh Chuyen khoan thanh cong");
		log.info("TC_15_13_1_Kiem tra Chuyen khoan thanh cong");
		verifyEquals(transferMoneyOutSide.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY);

		log.info("TC_15_13_2_Kiem tra ten nguoi thu huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.BENEFICIARY_NAME), info7.name);

		log.info("TC_15_13_3_Kiem tra tai khoan dich");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.DESTINATION_ACCOUNT), destinationAccount);

		log.info("TC_15_13_3_Kiem tra ngan hang huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.DESTINATION_BANK), info7.destinationBank);

		log.info("TC_15_13_5_Kiem tra noi dung");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.CONTENT), info7.note);

		log.info("TC_15_13_5_Kiem tra nut Thuc hien giao dich moi");
		verifyTrue(transferMoneyOutSide.isDynamicButtonDisplayed(driver, TitleOutVCB.NEW_TRANSFER));
		clickPopupAfter15h30();

		log.info("TC_15_13_6_Lay ma giao dich");
		transferTime = transferMoneyOutSide.getTransferTimeSuccess(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY);
		transactionNumber = transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.TRANSECTION_NUMBER);

		log.info("TC_15_14_Click Thuc hien giao dich moi");
		transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEW_TRANSFER);

		log.info("TC_15_14_Kiem tra so du kha dung luc sau");
		transferMoneyOutSide.scrollUpToText(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		transferMoneyOutSide.clickToDynamicDropDown(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, account);
		transferMoneyOutSide.sleep(driver, 1000);
		transferMoneyOutSide.scrollUpToText(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		actualAvailableBalanceCurrentcy = convertAvailableBalanceCurrentcyToDouble(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.SURPLUS));
		availableBalanceCurrentcy = canculateAvailableBalancesCurrentcy(surplusCurrentcy, Double.parseDouble(info7.money), 0);
		verifyEquals(actualAvailableBalanceCurrentcy, availableBalanceCurrentcy);

	}

	@Test
	public void TC_16_ChuyenTienLienNganHangCoPhiGiaoDichNguoiNhanTraNgoaiTe_EUR_XacThucBangMatKhau_BaoCao() {
		log.info("TC_16_1: Click  nut Back");
		homePage.clickToDynamicBackIcon(driver, TitleOutVCB.TITLE_TRANSFER_OUTSIDE);

		log.info("TC_16_2: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_16_3: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_16_4: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.ALL_TYPE_TRANSACTION);

		log.info("TC_16_5: Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSFER_OUTSIDE_VCB);

		log.info("TC_16_6: Click Chon Tai Khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_16_7: Chon tai Khoan chuyen");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, info7.sourceAccount);

		log.info("TC_16_8: Click Tim Kiem");
		transReport.clickToDynamicButton(driver, ReportTitle.SEARCH_BUTTON);

		log.info("TC_16_9: Kiem tra ngay tao giao dich hien thi");
		String reportTime1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
		verifyEquals(convertDateTimeIgnoreHHmmss(reportTime1), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_16_11: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(info7.note));

		log.info("TC_16_12: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + info7.money + " EUR"));

		log.info("TC_16_13: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_16_14: Kiem tra ngay tao giao dich hien thi");
		String reportTime2 = transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TIME_TRANSACTION);
		verifyEquals(reportTime2, reportTime1);

		log.info("TC_16_15: Kiem tra thoi gian tao giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_NUMBER), transactionNumber);

		log.info("TC_16_16: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_CARD_SOURCE), info7.sourceAccount);

		log.info("TC_16_17: Kiem tra so tai khoan ghi co");

		log.info("TC_16_18: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.AMOUNT_TRANSFER).contains(addCommasToDouble(info7.money) + " EUR"));

		log.info("TC_16_19: Kiem tra so tien quy doi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.CHANGE_MONEY).contains(convertEURO_USDToVNeseMoney(info7.money, currentcy)));

		log.info("TC_16_20: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.FEE), TitleOutVCB.RECEIVER_PAYER);

		log.info("TC_16_21: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TYPE_TRANSFER), TitleOutVCB.TITLE_TRANSFER_OUTSIDE);

		log.info("TC_16_22: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_CONTENT).contains(info7.note));

		log.info("TC_16_23: Click  nut Back");
		transferMoneyOutSide.clickToDynamicBackIcon(driver, ReportTitle.DETAIL_TRANSFER);

		log.info("TC_16_24: Click  nut Back");
		transferMoneyOutSide.clickToDynamicBackIcon(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_16_25: Click  nut Home");
		transferMoneyOutSide.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@Test
	public void TC_17_ChuyenTienLienNganHang_USD_CoPhiGiaoDichNguoiChuyenTraXacThucBangOTP() {
		log.info("TC_17_1_Click Chuyen tien toi ngan hang khac");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.TITLE_TRANSFER_OUTSIDE);
		clickPopupAfter15h30();

		log.info("TC_17_2_Chon tai khoan nguon");
		transferMoneyOutSide.scrollUpToText(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		transferMoneyOutSide.clickToDynamicDropDown(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		sourceAccount = transferMoneyOutSide.chooseSourceAccount(driver, Constants.MONEY_CHECK_USD, "USD");
		account = sourceAccount.account;
		transferMoneyOutSide.sleep(driver, 1000);
		transferMoneyOutSide.scrollUpToText(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		surplusCurrentcy = convertAvailableBalanceCurrentcyToDouble(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.SURPLUS));

		log.info("TC_17_3_Nhap tai khoan thu huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, destinationAccount, TitleOutVCB.ACCOUT_TO);

		log.info("TC_17_4_Nhap ten nguoi huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info8.name, TitleOutVCB.BENEFICIARY_NAME);

		log.info("TC_17_5_Chon ngan hang huong");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.DESTINATION_BANK);
		transferMoneyOutSide.inputToDynamicInputBox(driver, info8.destinationBank, ReportTitle.SEARCH_BUTTON);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info8.destinationBank);

		currentcy = getCurrentcyMoney(transferMoneyOutSide.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTiGia"));
		log.info("TC_17_6_Nhap so tien");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info8.money, TitleOutVCB.MONEY);

		log.info("TC_17_7_Chọn phí giao dịch");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.TRANSACTION_FEE_SENT);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.TRANSFER_PERSON);

		log.info("TC_17_8_Nhap noi dung chuyen tien");
		transferMoneyOutSide.inputToDynamicInputBoxByHeader(driver, info8.note, TitleOutVCB.TRANSACTION_INFOMATION, "3");

		log.info("TC_17_9_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEXT);

		log.info("TC_17_10_Kiem tra man hinh xac nhan thong tin");
		log.info("TC_17_10_1_Kiem tra tai khoan nguon");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.ACCOUNT_FROM_LABEL), info8.sourceAccount);

		log.info("TC_17_10_2_Kiem tra tai khoan dich");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.DESTINATION_ACCOUNT_VND), destinationAccount);

		log.info("TC_17_10_3_Kiem tra ten nguoi huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.DESTINATION_ACCOUNT_NAME), info8.name);
		
		log.info("TC_17_10_4_Kiem tra ngan hang dich");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.DESTINATION_BANK), info8.destinationBank);

		log.info("TC_17_10_5_Kiem tra so tien quy doi");
		String actualMoney = transferMoneyOutSide.getDynamicTextInTextViewLine2(driver, TitleOutVCB.MONEY);
		String expectMoney = convertEURO_USDToVNeseMoney(info8.money, currentcy);
		verifyEquals(actualMoney, expectMoney);

		log.info("TC_17_10_6_Kiem tra so tien");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.MONEY), addCommasToDouble(info8.money) + " USD");

		log.info("TC_17_10_7_Kiem tra noi dung chuyen tien");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.CONTENT), info8.note);

		log.info("TC_17_11_Chon phuong thuc xac thuc");
		transferMoneyOutSide.scrollDownToText(driver, TitleOutVCB.CHOOSE_METHOD);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.PASSWORD_TITLE);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info8.authenticationMethod);

		log.info("TC_17_11_01_Kiem tra so tien phi");
		String fee = transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.TRANSACTION_FEE);
		transferFee = convertAvailableBalanceCurrentcyOrFeeToLong(fee);
		transferFeeCurrentcy = convertVNeseMoneyToEUROOrUSD(String.valueOf(transferFee), currentcy);

		log.info("TC_17_12_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEXT);

		transferMoneyOutSide.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, TitleOutVCB.NEXT);

		transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEXT);

		log.info("TC_17_13_Kiem tra man hinh Chuyen khoan thanh cong");
		log.info("TC_17_13_1_Kiem tra Chuyen khoan thanh cong");
		verifyEquals(transferMoneyOutSide.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY);

		log.info("TC_17_13_2_Kiem tra ten nguoi thu huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.BENEFICIARY_NAME), info8.name);

		log.info("TC_17_13_3_Kiem tra tai khoan dich");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.DESTINATION_ACCOUNT), destinationAccount);

		log.info("TC_17_13_3_Kiem tra ngan hang huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.DESTINATION_BANK), info8.destinationBank);

		log.info("TC_17_13_5_Kiem tra noi dung");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.CONTENT), info8.note);

		log.info("TC_17_13_5_Kiem tra nut Thuc hien giao dich moi");
		verifyTrue(transferMoneyOutSide.isDynamicButtonDisplayed(driver, TitleOutVCB.NEW_TRANSFER));
		clickPopupAfter15h30();

		log.info("TC_17_13_6_Lay ma giao dich");
		transferTime = transferMoneyOutSide.getTransferTimeSuccess(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY);
		transactionNumber = transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.TRANSECTION_NUMBER);

		log.info("TC_17_14_Click Thuc hien giao dich moi");
		transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEW_TRANSFER);

		log.info("TC_17_14_Kiem tra so du kha dung luc sau");
		transferMoneyOutSide.scrollUpToText(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		transferMoneyOutSide.clickToDynamicDropDown(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, account);
		transferMoneyOutSide.sleep(driver, 1000);
		transferMoneyOutSide.scrollUpToText(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		actualAvailableBalanceCurrentcy = convertAvailableBalanceCurrentcyToDouble(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.SURPLUS));
		availableBalanceCurrentcy = canculateAvailableBalancesCurrentcy(surplusCurrentcy, Double.parseDouble(info8.money), transferFeeCurrentcy);
		verifyEquals(actualAvailableBalanceCurrentcy, availableBalanceCurrentcy);

	}

	@Test
	public void TC_18_ChuyenTienLienNganHangNgoaite_USD_CoPhiGiaoDichNguoiChuyenTraXacThucBangOTP_BaoCao() {
		log.info("TC_18_1: Click  nut Back");
		homePage.clickToDynamicBackIcon(driver, TitleOutVCB.TITLE_TRANSFER_OUTSIDE);

		log.info("TC_18_2: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_18_3: Click Bao cao giao dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_18_4: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.ALL_TYPE_TRANSACTION);

		log.info("TC_18_5: Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSFER_OUTSIDE_VCB);

		log.info("TC_18_6: Click Chon Tai Khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_18_7: Chon tai Khoan chuyen");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, info8.sourceAccount);

		log.info("TC_18_8: Click Tim Kiem");
		transReport.clickToDynamicButton(driver, ReportTitle.SEARCH_BUTTON);

		log.info("TC_18_9: Kiem tra ngay tao giao dich hien thi");
		String reportTime1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
		verifyEquals(convertDateTimeIgnoreHHmmss(reportTime1), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_18_10: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(info8.note));

		log.info("TC_18_11: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + info8.money + " USD"));

		log.info("TC_18_12: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_18_13: Kiem tra ngay tao giao dich hien thi");
		String reportTime2 = transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TIME_TRANSACTION);
		verifyEquals(reportTime2, reportTime1);

		log.info("TC_18_14: Kiem tra thoi gian tao giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_NUMBER), transactionNumber);

		log.info("TC_18_15: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_CARD_SOURCE), info8.sourceAccount);

		log.info("TC_18_16: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.AMOUNT_TRANSFER).contains(addCommasToDouble(info8.money) + " USD"));

		log.info("TC_18_17: Kiem tra so tien quy doi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.CHANGE_MONEY).contains(convertEURO_USDToVNeseMoney(info8.money, currentcy)));

		log.info("TC_18_18: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.FEE), TitleOutVCB.TRANSFER_PERSON);

		log.info("TC_18_19: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TYPE_TRANSFER), TitleOutVCB.TITLE_TRANSFER_OUTSIDE);

		log.info("TC_18_20: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_CONTENT).contains(info8.note));

		log.info("TC_18_21: Click  nut Back");
		transferMoneyOutSide.clickToDynamicBackIcon(driver, ReportTitle.DETAIL_TRANSFER);

		log.info("TC_18_22: Click  nut Back");
		transferMoneyOutSide.clickToDynamicBackIcon(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_18_23: Click  nut Home");
		transferMoneyOutSide.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@Test
	public void TC_19_ChuyenTienLienNganHangCoPhiGiaoDichNguoiNhanTraNgoaiTe_USD_XacThucBangMatKhau() {
		log.info("TC_19_1_Click Chuyen tien toi ngan hang khac");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.TITLE_TRANSFER_OUTSIDE);
		clickPopupAfter15h30();

		log.info("TC_19_2_Chon tai khoan nguon");
		transferMoneyOutSide.scrollUpToText(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		transferMoneyOutSide.clickToDynamicDropDown(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		sourceAccount = transferMoneyOutSide.chooseSourceAccount(driver, Constants.MONEY_CHECK_USD, "USD");
		account = sourceAccount.account;
		transferMoneyOutSide.sleep(driver, 1000);
		transferMoneyOutSide.scrollUpToText(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		surplusCurrentcy = convertAvailableBalanceCurrentcyToDouble(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.SURPLUS));

		log.info("TC_19_3_Nhap tai khoan thu huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, destinationAccount, TitleOutVCB.ACCOUT_TO);

		log.info("TC_19_4_Nhap ten nguoi huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info9.name, TitleOutVCB.BENEFICIARY_NAME);

		log.info("TC_19_5_Chon ngan hang huong");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.DESTINATION_BANK);
		transferMoneyOutSide.inputToDynamicInputBox(driver, info9.destinationBank, ReportTitle.SEARCH_BUTTON);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info9.destinationBank);

		currentcy = getCurrentcyMoney(transferMoneyOutSide.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTiGia"));
		log.info("TC_19_6_Nhap so tien");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info9.money, TitleOutVCB.MONEY);

		log.info("TC_19_7_Chọn phí giao dịch");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.TRANSACTION_FEE_SENT);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.RECEIVER_PAYER);

		log.info("TC_19_8_Nhap noi dung chuyen tien");
		transferMoneyOutSide.inputToDynamicInputBoxByHeader(driver, info9.note, TitleOutVCB.TRANSACTION_INFOMATION, "3");

		log.info("TC_19_9_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEXT);

		log.info("TC_19_10_Kiem tra man hinh xac nhan thong tin");
		log.info("TC_19_10_1_Kiem tra tai khoan nguon");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.ACCOUNT_FROM_LABEL), info9.sourceAccount);

		log.info("TC_19_10_2_Kiem tra tai khoan dich");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.DESTINATION_ACCOUNT_VND), destinationAccount);

		log.info("TC_19_10_3_Kiem tra ten nguoi huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.DESTINATION_ACCOUNT_NAME), info9.name);
		
		log.info("TC_19_10_4_Kiem tra ngan hang huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.DESTINATION_BANK), info9.destinationBank);

		log.info("TC_19_10_5_Kiem tra so tien quy doi");
		String actualMoney = transferMoneyOutSide.getDynamicTextInTextViewLine2(driver, TitleOutVCB.MONEY);
		String expectMoney = convertEURO_USDToVNeseMoney(info6.money, currentcy);
		verifyEquals(actualMoney, expectMoney);

		log.info("TC_19_10_6_Kiem tra so tien");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.MONEY), addCommasToDouble(info9.money) + " USD");

		log.info("TC_19_10_7_Kiem tra noi dung chuyen tien");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.CONTENT), info9.note);

		log.info("TC_19_11_Chon phuong thuc xac thuc");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.PASSWORD_TITLE);
		transferFee = 0;
		transferFeeCurrentcy = 0;
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info9.authenticationMethod);

		log.info("TC_19_11_01_Kiem tra so tien phi");
		verifyTrue(transferMoneyOutSide.isDynamicMessageAndLabelTextDisplayed(driver, TitleOutVCB.RECEIVER_PAYER));

		log.info("TC_19_12_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEXT);

		transferMoneyOutSide.inputToDynamicPopupPasswordInput(driver, password, TitleOutVCB.NEXT);

		transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEXT);

		log.info("TC_19_13_Kiem tra man hinh Chuyen khoan thanh cong");
		log.info("TC_19_13_1_Kiem tra Chuyen khoan thanh cong");
		verifyEquals(transferMoneyOutSide.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY);

		log.info("TC_19_13_2_Kiem tra ten nguoi thu huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.BENEFICIARY_NAME), info9.name);

		log.info("TC_19_13_3_Kiem tra tai khoan dich");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.DESTINATION_ACCOUNT), destinationAccount);

		log.info("TC_19_13_3_Kiem tra ngan hang huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.DESTINATION_BANK), info9.destinationBank);

		log.info("TC_19_13_5_Kiem tra noi dung");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.CONTENT), info9.note);

		log.info("TC_19_13_5_Kiem tra nut Thuc hien giao dich moi");
		verifyTrue(transferMoneyOutSide.isDynamicButtonDisplayed(driver, TitleOutVCB.NEW_TRANSFER));

		log.info("TC_19_13_6_Lay ma giao dich");
		transferTime = transferMoneyOutSide.getTransferTimeSuccess(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY);
		transactionNumber = transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.TRANSECTION_NUMBER);

		log.info("TC_19_14_Click Thuc hien giao dich moi");
		transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEW_TRANSFER);

		log.info("TC_19_14_Kiem tra so du kha dung luc sau");
		transferMoneyOutSide.scrollUpToText(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		transferMoneyOutSide.clickToDynamicDropDown(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, account);
		transferMoneyOutSide.sleep(driver, 1000);
		transferMoneyOutSide.scrollUpToText(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		actualAvailableBalanceCurrentcy = convertAvailableBalanceCurrentcyToDouble(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.SURPLUS));
		availableBalanceCurrentcy = canculateAvailableBalancesCurrentcy(surplusCurrentcy, Double.parseDouble(info9.money), 0);
		verifyEquals(actualAvailableBalanceCurrentcy, availableBalanceCurrentcy);

	}

	@Test
	public void TC_20_ChuyenTienLienNganHangCoPhiGiaoDichNguoiNhanTraNgoaiTe_USD_XacThucBangMatKhau_BaoCao() {
		log.info("TC_20_1: Click  nut Back");
		homePage.clickToDynamicBackIcon(driver, TitleOutVCB.TITLE_TRANSFER_OUTSIDE);

		log.info("TC_20_2: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_20_3: Click Bao cao giao dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_20_4: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.ALL_TYPE_TRANSACTION);

		log.info("TC_20_5: Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSFER_OUTSIDE_VCB);

		log.info("TC_20_6: Click Chon Tai Khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_20_7: Chon tai Khoan chuyen");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, account);

		log.info("TC_20_8: Click Tim Kiem");
		transReport.clickToDynamicButton(driver, ReportTitle.SEARCH_BUTTON);

		log.info("TC_20_9: Kiem tra ngay tao giao dich hien thi");
		String reportTime1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
		verifyEquals(convertDateTimeIgnoreHHmmss(reportTime1), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_20_10: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(info9.note));

		log.info("TC_20_11: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + info9.money + " USD"));

		log.info("TC_20_12: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_20_13: Kiem tra ngay tao giao dich hien thi");
		String reportTime2 = transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TIME_TRANSACTION);
		verifyEquals(reportTime2, reportTime1);

		log.info("TC_20_14: Kiem tra thoi gian tao giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_NUMBER), transactionNumber);

		log.info("TC_20_15: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_CARD_SOURCE), info9.sourceAccount);

		log.info("TC_20_16: Kiem tra so tai khoan ghi co");

		log.info("TC_20_17: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.AMOUNT_TRANSFER).contains(addCommasToDouble(info9.money) + " USD"));

		log.info("TC_20_18: Kiem tra so tien quy doi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.CHANGE_MONEY).contains(convertEURO_USDToVNeseMoney(info9.money, currentcy)));

		log.info("TC_20_19: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.FEE), TitleOutVCB.RECEIVER_PAYER);

		log.info("TC_20_20: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TYPE_TRANSFER), TitleOutVCB.TITLE_TRANSFER_OUTSIDE);

		log.info("TC_20_21: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_CONTENT).contains(info9.note));

		log.info("TC_20_22: Click  nut Back");
		transferMoneyOutSide.clickToDynamicBackIcon(driver, ReportTitle.DETAIL_TRANSFER);

		log.info("TC_20_23: Click  nut Back");
		transferMoneyOutSide.clickToDynamicBackIcon(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_20_24: Click  nut Home");
		transferMoneyOutSide.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@Test
	public void TC_21_ChuyenTienLienNganHang_USD_CoPhiGiaoDichNguoiNhanTraXacThucBangOTP() {
		log.info("TC_21_1_Click Chuyen tien toi ngan hang khac");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.TITLE_TRANSFER_OUTSIDE);
		clickPopupAfter15h30();

		log.info("TC_21_2_Chon tai khoan nguon");
		transferMoneyOutSide.scrollUpToText(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		transferMoneyOutSide.clickToDynamicDropDown(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		sourceAccount = transferMoneyOutSide.chooseSourceAccount(driver, Constants.MONEY_CHECK_USD, "USD");
		account = sourceAccount.account;
		transferMoneyOutSide.sleep(driver, 1000);
		transferMoneyOutSide.scrollUpToText(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		surplusCurrentcy = convertAvailableBalanceCurrentcyToDouble(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.SURPLUS));

		log.info("TC_21_3_Nhap tai khoan thu huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, destinationAccount, TitleOutVCB.ACCOUT_TO);

		log.info("TC_21_4_Nhap ten nguoi huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info10.name, TitleOutVCB.BENEFICIARY_NAME);

		log.info("TC_21_5_Chon ngan hang huong");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.DESTINATION_BANK);
		transferMoneyOutSide.inputToDynamicInputBox(driver, info10.destinationBank, ReportTitle.SEARCH_BUTTON);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info10.destinationBank);

		currentcy = getCurrentcyMoney(transferMoneyOutSide.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTiGia"));
		log.info("TC_21_6_Nhap so tien");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info10.money, TitleOutVCB.MONEY);

		log.info("TC_21_7_Chọn phí giao dịch");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.TRANSACTION_FEE_SENT);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.RECEIVER_PAYER);

		log.info("TC_21_8_Nhap noi dung chuyen tien");
		transferMoneyOutSide.inputToDynamicInputBoxByHeader(driver, info10.note, TitleOutVCB.TRANSACTION_INFOMATION, "3");

		log.info("TC_21_9_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEXT);

		log.info("TC_21_10_Kiem tra man hinh xac nhan thong tin");
		log.info("TC_21_10_1_Kiem tra tai khoan nguon");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.ACCOUNT_FROM_LABEL), info10.sourceAccount);

		log.info("TC_21_10_2_Kiem tra tai khoan dich");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.DESTINATION_ACCOUNT_VND), destinationAccount);

		log.info("TC_21_10_3_Kiem tra ten nguoi huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.DESTINATION_ACCOUNT_NAME), info10.name);
		
		log.info("TC_21_10_4_Kiem tra ngan hang dich");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.DESTINATION_BANK), info10.destinationBank);

		log.info("TC_21_10_5_Kiem tra so tien quy doi");
		String actualMoney = transferMoneyOutSide.getDynamicTextInTextViewLine2(driver, TitleOutVCB.MONEY);
		String expectMoney = convertEURO_USDToVNeseMoney(info10.money, currentcy);
		verifyEquals(actualMoney, expectMoney);

		log.info("TC_21_10_6_Kiem tra so tien");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.MONEY), addCommasToDouble(info10.money) + " USD");

		log.info("TC_21_10_7_Kiem tra noi dung chuyen tien");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.CONTENT), info10.note);

		log.info("TC_21_11_Chon phuong thuc xac thuc");
		transferMoneyOutSide.scrollDownToText(driver, TitleOutVCB.CHOOSE_METHOD);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.PASSWORD_TITLE);
		transferFee = 0;
		transferFeeCurrentcy = 0;
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info10.authenticationMethod);

		log.info("TC_21_11_01_Kiem tra so tien phi");
		verifyTrue(transferMoneyOutSide.isDynamicMessageAndLabelTextDisplayed(driver, TitleOutVCB.RECEIVER_PAYER));

		log.info("TC_21_12_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEXT);

		transferMoneyOutSide.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, TitleOutVCB.NEXT);

		transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEXT);

		log.info("TC_21_13_Kiem tra man hinh Chuyen khoan thanh cong");
		log.info("TC_21_13_1_Kiem tra Chuyen khoan thanh cong");
		verifyEquals(transferMoneyOutSide.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY);

		log.info("TC_21_13_2_Kiem tra ten nguoi thu huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.BENEFICIARY_NAME), info10.name);

		log.info("TC_21_13_3_Kiem tra tai khoan dich");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.DESTINATION_ACCOUNT), destinationAccount);

		log.info("TC_21_13_3_Kiem tra ngan hang huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.DESTINATION_BANK), info10.destinationBank);

		log.info("TC_21_13_5_Kiem tra noi dung");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.CONTENT), info10.note);

		log.info("TC_21_13_5_Kiem tra nut Thuc hien giao dich moi");
		verifyTrue(transferMoneyOutSide.isDynamicButtonDisplayed(driver, TitleOutVCB.NEW_TRANSFER));
		clickPopupAfter15h30();

		log.info("TC_21_13_6_Lay ma giao dich");
		transferTime = transferMoneyOutSide.getTransferTimeSuccess(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY);
		transactionNumber = transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.TRANSECTION_NUMBER);

		log.info("TC_21_14_Click Thuc hien giao dich moi");
		transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEW_TRANSFER);

		log.info("TC_21_14_Kiem tra so du kha dung luc sau");
		transferMoneyOutSide.scrollUpToText(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		transferMoneyOutSide.clickToDynamicDropDown(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, account);
		transferMoneyOutSide.sleep(driver, 1000);
		transferMoneyOutSide.scrollUpToText(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		actualAvailableBalanceCurrentcy = convertAvailableBalanceCurrentcyToDouble(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.SURPLUS));
		availableBalanceCurrentcy = canculateAvailableBalancesCurrentcy(surplusCurrentcy, Double.parseDouble(info8.money), 0);
		verifyEquals(actualAvailableBalanceCurrentcy, availableBalanceCurrentcy);

	}

	@Test
	public void TC_22_ChuyenTienLienNganHangNgoaite_USD_CoPhiGiaoDichNguoiChuyenTraXacThucBangOTP_BaoCao() {
		log.info("TC_22_1: Click  nut Back");
		homePage.clickToDynamicBackIcon(driver, TitleOutVCB.TITLE_TRANSFER_OUTSIDE);

		log.info("TC_22_2: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_22_3: Click Bao cao giao dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_22_4: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.ALL_TYPE_TRANSACTION);

		log.info("TC_22_5: Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSFER_OUTSIDE_VCB);

		log.info("TC_22_6: Click Chon Tai Khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_22_7: Chon tai Khoan chuyen");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, info10.sourceAccount);

		log.info("TC_22_8: Click Tim Kiem");
		transReport.clickToDynamicButton(driver, ReportTitle.SEARCH_BUTTON);

		log.info("TC_22_9: Kiem tra ngay tao giao dich hien thi");
		String reportTime1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
		verifyEquals(convertDateTimeIgnoreHHmmss(reportTime1), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_22_10: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(info10.note));

		log.info("TC_22_11: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + info10.money + " USD"));

		log.info("TC_22_12: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_22_13: Kiem tra ngay tao giao dich hien thi");
		String reportTime2 = transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TIME_TRANSACTION);
		verifyEquals(reportTime2, reportTime1);

		log.info("TC_22_14: Kiem tra thoi gian tao giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_NUMBER), transactionNumber);

		log.info("TC_22_15: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_CARD_SOURCE), info10.sourceAccount);

		log.info("TC_22_16: Kiem tra so tai khoan ghi co");

		log.info("TC_22_17: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.AMOUNT_TRANSFER).contains(addCommasToDouble(info10.money) + " USD"));

		log.info("TC_22_18: Kiem tra so tien quy doi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.CHANGE_MONEY).contains(convertEURO_USDToVNeseMoney(info10.money, currentcy)));

		log.info("TC_22_19: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.FEE), TitleOutVCB.RECEIVER_PAYER);

		log.info("TC_22_20: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TYPE_TRANSFER), TitleOutVCB.TITLE_TRANSFER_OUTSIDE);

		log.info("TC_22_21: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_CONTENT).contains(info10.note));

		log.info("TC_22_22: Click  nut Back");
		transferMoneyOutSide.clickToDynamicBackIcon(driver, ReportTitle.DETAIL_TRANSFER);

		log.info("TC_22_23: Click  nut Back");
		transferMoneyOutSide.clickToDynamicBackIcon(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_22_24: Click  nut Home");
		transferMoneyOutSide.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@Test
	public void TC_23_ChuyenTienLienNganHangCoPhiGiaoDichNguoiChuyenTraNgoaiTe_USD_XacThucBangMatKhau() {
		log.info("TC_23_1_Click Chuyen tien toi ngan hang khac");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.TITLE_TRANSFER_OUTSIDE);
		clickPopupAfter15h30();

		log.info("TC_23_2_Chon tai khoan nguon");
		transferMoneyOutSide.scrollUpToText(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		transferMoneyOutSide.clickToDynamicDropDown(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		sourceAccount = transferMoneyOutSide.chooseSourceAccount(driver, Constants.MONEY_CHECK_USD, "USD");
		account = sourceAccount.account;
		transferMoneyOutSide.sleep(driver, 1000);
		transferMoneyOutSide.scrollUpToText(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		surplusCurrentcy = convertAvailableBalanceCurrentcyToDouble(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.SURPLUS));

		log.info("TC_23_3_Nhap tai khoan thu huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, destinationAccount, TitleOutVCB.ACCOUT_TO);

		log.info("TC_23_4_Nhap ten nguoi huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info11.name, TitleOutVCB.BENEFICIARY_NAME);

		log.info("TC_23_5_Chon ngan hang huong");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.DESTINATION_BANK);
		transferMoneyOutSide.inputToDynamicInputBox(driver, info11.destinationBank, ReportTitle.SEARCH_BUTTON);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info11.destinationBank);

		currentcy = getCurrentcyMoney(transferMoneyOutSide.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTiGia"));
		log.info("TC_23_6_Nhap so tien");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info11.money, TitleOutVCB.MONEY);

		log.info("TC_23_7_Chọn phí giao dịch");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.TRANSACTION_FEE_SENT);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.TRANSFER_PERSON);

		log.info("TC_23_8_Nhap noi dung chuyen tien");
		transferMoneyOutSide.inputToDynamicInputBoxByHeader(driver, info11.note, TitleOutVCB.TRANSACTION_INFOMATION, "3");

		log.info("TC_23_9_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEXT);

		log.info("TC_23_10_Kiem tra man hinh xac nhan thong tin");
		log.info("TC_23_10_1_Kiem tra tai khoan nguon");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.ACCOUNT_FROM_LABEL), info11.sourceAccount);

		log.info("TC_23_10_2_Kiem tra tai khoan dich");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.DESTINATION_ACCOUNT_VND), destinationAccount);

		log.info("TC_23_10_3_Kiem tra ten nguoi huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.DESTINATION_ACCOUNT_NAME), info11.name);
		
		log.info("TC_23_10_4_Kiem tra ngan hang huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.DESTINATION_BANK), info11.destinationBank);

		log.info("TC_23_10_5_Kiem tra so tien quy doi");
		String actualMoney = transferMoneyOutSide.getDynamicTextInTextViewLine2(driver, TitleOutVCB.MONEY);
		String expectMoney = convertEURO_USDToVNeseMoney(info11.money, currentcy);
		verifyEquals(actualMoney, expectMoney);

		log.info("TC_23_10_6_Kiem tra so tien");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.MONEY), addCommasToDouble(info11.money) + " USD");

		log.info("TC_23_10_7_Kiem tra noi dung chuyen tien");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.CONTENT), info11.note);

		log.info("TC_23_11_Chon phuong thuc xac thuc");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.PASSWORD_TITLE);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info11.authenticationMethod);

		log.info("TC_23_11_01_Kiem tra so tien phi");
		String fee = transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.TRANSACTION_FEE);
		transferFee = convertAvailableBalanceCurrentcyOrFeeToLong(fee);
		transferFeeCurrentcy = convertVNeseMoneyToEUROOrUSD(String.valueOf(transferFee), currentcy);

		log.info("TC_23_12_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEXT);

		transferMoneyOutSide.inputToDynamicPopupPasswordInput(driver, password, TitleOutVCB.NEXT);

		transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEXT);

		log.info("TC_23_13_Kiem tra man hinh Chuyen khoan thanh cong");
		log.info("TC_23_13_1_Kiem tra Chuyen khoan thanh cong");
		verifyEquals(transferMoneyOutSide.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY);

		log.info("TC_23_13_2_Kiem tra ten nguoi thu huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.BENEFICIARY_NAME), info11.name);

		log.info("TC_23_13_3_Kiem tra tai khoan dich");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.DESTINATION_ACCOUNT), destinationAccount);

		log.info("TC_23_13_3_Kiem tra ngan hang huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.DESTINATION_BANK), info11.destinationBank);

		log.info("TC_23_13_5_Kiem tra noi dung");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.CONTENT), info11.note);

		log.info("TC_23_13_5_Kiem tra nut Thuc hien giao dich moi");
		verifyTrue(transferMoneyOutSide.isDynamicButtonDisplayed(driver, TitleOutVCB.NEW_TRANSFER));
		clickPopupAfter15h30();

		log.info("TC_23_13_6_Lay ma giao dich");
		transferTime = transferMoneyOutSide.getTransferTimeSuccess(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY);
		transactionNumber = transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.TRANSECTION_NUMBER);

		log.info("TC_23_14_Click Thuc hien giao dich moi");
		transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEW_TRANSFER);

		log.info("TC_23_14_Kiem tra so du kha dung luc sau");
		transferMoneyOutSide.scrollUpToText(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		transferMoneyOutSide.clickToDynamicDropDown(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, account);
		transferMoneyOutSide.sleep(driver, 1000);
		transferMoneyOutSide.scrollUpToText(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		actualAvailableBalanceCurrentcy = convertAvailableBalanceCurrentcyToDouble(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, TitleOutVCB.SURPLUS));
		availableBalanceCurrentcy = canculateAvailableBalancesCurrentcy(surplusCurrentcy, Double.parseDouble(info11.money), transferFeeCurrentcy);
		verifyEquals(actualAvailableBalanceCurrentcy, availableBalanceCurrentcy);

	}

	@Test
	public void TC_24_ChuyenTienLienNganHangCoPhiGiaoDichNguoiChuyenTraNgoaiTe_USD_XacThucBangMatKhau_BaoCao() {
		log.info("TC_24_1: Click  nut Back");
		homePage.clickToDynamicBackIcon(driver, TitleOutVCB.TITLE_TRANSFER_OUTSIDE);

		log.info("TC_24_2: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_24_3: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_24_4: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.ALL_TYPE_TRANSACTION);

		log.info("TC_24_5: Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSFER_OUTSIDE_VCB);

		log.info("TC_24_6: Click Chon Tai Khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_24_7: Chon tai Khoan chuyen");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, info11.sourceAccount);

		log.info("TC_24_8: Click Tim Kiem");
		transReport.clickToDynamicButton(driver, ReportTitle.SEARCH_BUTTON);

		log.info("TC_24_9: Kiem tra ngay tao giao dich hien thi");
		String reportTime1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
		verifyEquals(convertDateTimeIgnoreHHmmss(reportTime1), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_24_10: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(info11.note));

		log.info("TC_24_11: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + info11.money + " USD"));

		log.info("TC_24_12: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_24_13: Kiem tra ngay tao giao dich hien thi");
		String reportTime2 = transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TIME_TRANSACTION);
		verifyEquals(reportTime2, reportTime1);

		log.info("TC_24_14: Kiem tra thoi gian tao giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_NUMBER), transactionNumber);

		log.info("TC_24_15: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_CARD_SOURCE), info11.sourceAccount);

		log.info("TC_24_16: Kiem tra so tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.DESTINATION_ACCOUNT_CARD), destinationAccount);

		log.info("TC_24_17: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.AMOUNT_TRANSFER).contains(addCommasToDouble(info6.money) + " USD"));

		log.info("TC_24_18: Kiem tra so tien quy doi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.CHANGE_MONEY).contains(convertEURO_USDToVNeseMoney(info6.money, currentcy)));

		log.info("TC_24_19: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.FEE), TitleOutVCB.TRANSFER_PERSON);

		log.info("TC_24_20: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TYPE_TRANSFER), TitleOutVCB.TITLE_TRANSFER_OUTSIDE);

		log.info("TC_24_21: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_CONTENT).contains(info6.note));

		log.info("TC_24_22: Click  nut Back");
		transferMoneyOutSide.clickToDynamicBackIcon(driver, ReportTitle.DETAIL_TRANSFER);

		log.info("TC_24_23: Click  nut Back");
		transferMoneyOutSide.clickToDynamicBackIcon(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_24_24: Click  nut Home");
		transferMoneyOutSide.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();
	}

	public void clickPopupAfter15h30() {
		if (transferMoneyOutSide.getPageSource(driver).contains(TitleOutVCB.NEXT)) {
			transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEXT);
		}
	}

}