package vnpay.vietcombank.transfer_money_quick_247;

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
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.TransactionReportPageObject;
import pageObjects.TransferMoneyObject;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransactionReport_Data.ReportTitle;
import vietcombank_test_data.TransferMoneyQuick_Data;
import vietcombank_test_data.TransferMoneyQuick_Data.Tittle_Quick;
import vietcombank_test_data.TransferMoneyQuick_Data.TransferQuick;

public class Flow_QuickMoneyTransfer247_Part2 extends Base {
	AppiumDriver<MobileElement> driver;
	private TransactionReportPageObject transReport;
	private String transferTime;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferMoneyObject transferMoney;
	private String transactionNumber;
	private String amountStartString;
	private long amountStart;
	private String amountTranferString;
	private long amountTranfer;
	private String costTranferString,cardTo;
	private long costTranfer;
	private String accountStart;
	private long fee;
	private String exchangeRate;
	private String password = "";

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String pass, String opt) throws IOException, InterruptedException, GeneralSecurityException {
		startServer();
		log.info("Before class: Mo app ");
		if (deviceType.contains("android")) {
			driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		} else if (deviceType.contains("")) {
			driver = openIOSApp(deviceName, udid, url);
		}
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login1("0904797864", pass, opt);
		password = pass;
		transferMoney = PageFactoryManager.getTransferMoneyObject(driver);
		cardTo = getDataInCell(20);
	}

	@Test
	public void TC_09_ChuyenTienQuaSoTheCoPhiGiaoDichNguoiNhanTraVNDVaXacThucBangOTP() {
		log.info("TC_09_Step_Click Chuyen tien nhanh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferQuick.TRANSFER_MONEY_LABEL);

		log.info("TC_09_Step_Select Chuyen tien nhanh qua the");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[1]);

		log.info("TC_09_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, Tittle_Quick.CARD_TRIP);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_CARD_FROM[0]);

		log.info("TC_09_Step_Get so tai khoan nguon");
		accountStart = transferMoney.getDynamicTextByLabel(driver, TransferQuick.ACCOUNT_FROM_LABEL);

		log.info("TC_09_Step_Get so du kha dung");
		amountStartString = transferMoney.getDynamicTextByLabel(driver, Tittle_Quick.AVAIBLE_BALANCES).replaceAll("\\D+", "");

		log.info("TC_09_Step_Doi kieu du lieu");
		amountStart = Long.parseLong(amountStartString);

		log.info("TC_09_Step_Nhap so tai khoan chuyen");
		transferMoney.inputToDynamicInputBox(driver, cardTo, Tittle_Quick.TYPE_SELECT_CARD_NUMBER);

		log.info("TC_09_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.MONEY, TransferQuick.MOUNT_LABEL);

		log.info("TC_09_Step_Chon phi giao dich la nguoi nhan tra");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[1]);

		log.info("TC_09_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE, Tittle_Quick.TRANSACTION_INFO, "3");

		log.info("TC_09_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);

		log.info("TC_09_Step_Chon phuong thuc xac thuc");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[1]);

		log.info("TC_09_Step_Lay gia tri so tien chuyen");
		amountTranferString = transferMoney.getDynamicTextByLabel(driver, TransferQuick.MOUNT_LABEL).replaceAll("\\D+", "");

		log.info("TC_09_Step_Verify so tien chuyen");
		verifyEquals(amountTranferString, TransferMoneyQuick_Data.TransferQuick.MONEY);

		log.info("TC_09_Step_doi kieu du lieu string -> long");
		amountTranfer = Long.parseLong(amountTranferString);

		log.info("TC_09_Step_Lay gia tri so tien phí chuyen");
		costTranferString = transferMoney.getDynamicTextByLabel(driver, TransferQuick.AMOUNT_FEE_LABEL).replaceAll("\\D+", "");


		log.info("TC_09_Step_doi kieu du lieu string -> long");
		costTranfer = Long.parseLong(costTranferString);

		log.info("TC_09_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);

		log.info("TC_09_Step_Nhap ma xac thuc");
		transferMoney.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, Tittle_Quick.CONTINUE_BUTTON);

		log.info("TC_09_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);

		log.info("TC_09_Verify message thanh cong");
		verifyEquals(transferMoney.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY1);

		log.info("TC_09_get thoi gian giao dich thanh cong");
		transferTime = transferMoney.getDynamicTransferTimeAndMoney(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY1, "4").split(" ")[3];

		log.info("TC_09_Step_: Get ma giao dich");
		transactionNumber = transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.CODE_TRANSFER);

		log.info("TC_09_Step_:Ten nguoi thu huong");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.NAME_RECIVED_TEXT), TransferMoneyQuick_Data.TransferQuick.RECEIVER_NAME);

		log.info("TC_09_Step_: Tai khoan dich");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.CAR_NUMBER), cardTo);

		log.info("TC_09_Step_: Noi dung");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.CONTENT), TransferMoneyQuick_Data.TransferQuick.NOTE);

		log.info("TC_09_Step_: Thuc hien giao dich moi");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.NEW_TRANSFER);

		log.info("TC_09_Step_Select Chuyen tien nhanh qua the");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[1]);

		log.info("TC_09_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, Tittle_Quick.CARD_TRIP);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_CARD_FROM[0]);

		log.info("TC_09_Step_:Check so du kha dung sau khi chuyen tien");
		String amountAfterString = transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.AVAIBLE_BALANCES).replaceAll("\\D+", "");

		long amountAfter = Long.parseLong(amountAfterString);

		log.info("TC_09_Step_:Check so du kha dung sau khi chuyen tien");
		System.out.print("amountStart =" + amountStart + "-------amountTranfer =" + amountTranfer + "-------costTranfer =" + costTranfer);
		verifyEquals(amountStart - amountTranfer, amountAfter);
	}

	@Test
	public void TC_10_ReportChuyenTienQuaSoTheCoPhiGiaoDichNguoiNhanTraVNDVaXacThucBangOTP() {
		homePage = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_10: Click back man hinh home");
		homePage.clickToDynamicBackIcon(driver, TransferQuick.TRANSFER_MONEY_LABEL);

		log.info("TC_10: Click menu header");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_10: Click bao cao giao dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_10: chon loai bao cao giao dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.ALL_TYPE_TRANSACTION);

		log.info("TC_10: Chon option chuyen tien nhanh qua tai khoan");

		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSFER_24_7_CARD);

		log.info("TC_10: Chon so tai khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_10: Chon so tai khoan tra cuu");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, accountStart);

		log.info("TC_10: verify thoi tim kiem tu ngay");
		String dateStartActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate");
		String dateStartExpect = getBackwardDate(7);
		verifyEquals(dateStartActual, dateStartExpect);

		log.info("TC_10: verify thoi tim kiem tu ngay");
		String dateEndActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate");
		String dateEndtExpect = getForwardDate(0);
		verifyEquals(dateEndActual, dateEndtExpect);

		log.info("TC_10: Tim kiem");
		transReport.clickToDynamicButton(driver, ReportTitle.SEARCH_BUTTON);

		log.info("TC_10_: Lay ngay tao giao dich hien thi");
		String transferTimeInReport = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_10: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport), transferTime);

		log.info("TC_10: Check ghi chu");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyQuick_Data.TransferQuick.NOTE));

		log.info("TC_10: Check so tien chuyen");// lỗi app
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(TransferMoneyQuick_Data.TransferQuick.MONEY) + " VND"));

		log.info("TC_10: Click chi tiet giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_10: Lay ngay tao giao dich hien thi");
		String transferTimeInReport1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_10: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport1), transferTime);

		log.info("TC_10: Check so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_NUMBER), transactionNumber);

		log.info("TC_10: So the chuyen di");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Tittle_Quick.CARD_TRIP), Account_Data.Valid_Account.LIST_CARD_FROM[0]);

		log.info("TC_10: Check tao khoan ghi no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_CARD_SOURCE), accountStart);

		log.info("TC_10: Check tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.NUMBER_CARD_VND), cardTo);

		log.info("TC_10: Check so nguoi huong");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Tittle_Quick.NAME_RECIVED), TransferMoneyQuick_Data.TransferQuick.RECEIVER_NAME);

		log.info("TC_10: Check so tien giao dich");// Lỗi app
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_MONEY).contains(addCommasToLong(TransferMoneyQuick_Data.TransferQuick.MONEY) + " VND"));

		log.info("TC_10_Step_Lay gia tri so tien phí chuyen");
		costTranferString = transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_FEE).replaceAll("\\D+", "");

		log.info("TC_10_Step_Verify so tien phi");
		verifyEquals(costTranferString, fee + "");

		log.info("TC_10: Check noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.CONTENT_TRANSFER).contains(TransferMoneyQuick_Data.TransferQuick.NOTE));

		log.info("TC_10: click button back");
		transReport.clickToDynamicBackIcon(driver, ReportTitle.DETAIL_TRANSFER);

		log.info("TC_10: Chon button back");
		transferMoney.clickToDynamicBackIcon(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_10_Step_Click button home");
		transferMoney.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@Test
//Lỗi app, số tiền bị thiếu đơn vị USD
	public void TC_11_ChuyenTienQuaSoTheCoPhiGiaoDichNguoiNhanTraUSDVaXacThucBangOTP() {
		log.info("TC_11_Step_Click Chuyen tien nhanh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferQuick.TRANSFER_MONEY_LABEL);

		log.info("TC_11_Step_Select Chuyen tien nhanh qua the");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[1]);

		log.info("TC_11_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, Tittle_Quick.CARD_TRIP);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_CARD_FROM[0]);

		log.info("TC_11_Step_Get so tai khoan nguon");
		accountStart = transferMoney.getDynamicTextByLabel(driver, TransferQuick.ACCOUNT_FROM_LABEL);

		log.info("TC_04_Step_Get so du kha dung");
		amountStartString = transferMoney.getDynamicTextByLabel(driver, Tittle_Quick.AVAIBLE_BALANCES);
		double amountStart = convertMoneyToDouble(amountStartString, Constants.USD_CURRENCY);

		log.info("TC_11_Step_Nhap so tai khoan chuyen");
		transferMoney.inputToDynamicInputBox(driver, cardTo, Tittle_Quick.TYPE_SELECT_CARD_NUMBER);

		log.info("TC_11_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_USD, TransferQuick.MOUNT_LABEL);

		log.info("TC_11_Step_:Lay so tien quy doi ra VND");
		String exchange = transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.RATE_REFER).split(" ~ ", 2)[1];
		exchangeRate = exchange.replace(" VND", "");

		log.info("TC_11_Step_Chon phi giao dich la nguoi nhan tra");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[1]);

		log.info("TC_11_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE, Tittle_Quick.TRANSACTION_INFO, "3");

		log.info("TC_11_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);

		log.info("TC_11_Step_Lay gia tri so tien chuyen");
		amountTranferString = transferMoney.getDynamicTextByLabel(driver, TransferQuick.MOUNT_LABEL).replaceAll(".00 USD", "");

		log.info("TC_11_Step_Verify so tien chuyen");
		verifyEquals(amountTranferString, TransferMoneyQuick_Data.TransferQuick.MONEY_USD);

		log.info("TC_11_Step_doi kieu du lieu string -> long");
		amountTranfer = Long.parseLong(amountTranferString);

		log.info("TC_11_Step_Chon phuong thuc xac thuc");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[0]);
		fee = convertAvailableBalanceCurrentcyOrFeeToLong(transferMoney.getDynamicTextInTransactionDetail(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[1]));
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[1]);

		log.info("TC_11_Step_Lay gia tri so tien phí chuyen");
		costTranferString = transferMoney.getDynamicTextByLabel(driver, TransferQuick.AMOUNT_FEE_LABEL).replaceAll("\\D+", "");

		log.info("TC_11_Step_Verify so tien phi");
		verifyEquals(costTranferString, fee + "");

		log.info("TC_11_Step_doi kieu du lieu string -> long");
		costTranfer = Long.parseLong(costTranferString);

		log.info("TC_11_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);

		log.info("TC_11_Step_Nhap ma xac thuc");
		transferMoney.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, Tittle_Quick.CONTINUE_BUTTON);

		log.info("TC_11_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);

		log.info("TC_11_Verify message thanh cong");
		verifyEquals(transferMoney.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY1);

		log.info("TC_11_get thoi gian giao dich thanh cong");
		transferTime = transferMoney.getDynamicTransferTimeAndMoney(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY1, "4").split(" ")[3];

		log.info("TC_11_Step_: Get ma giao dich");
		transactionNumber = transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.CODE_TRANSFER);

		log.info("TC_11_Step_:Ten nguoi thu huong");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.NAME_RECIVED_TEXT), TransferMoneyQuick_Data.TransferQuick.RECEIVER_NAME);

		log.info("TC_11_Step_: Tai khoan dich");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.CAR_NUMBER), cardTo);

		log.info("TC_11_Step_: Noi dung");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.CONTENT), TransferMoneyQuick_Data.TransferQuick.NOTE);

		log.info("TC_11_Step_: Thuc hien giao dich moi");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.NEW_TRANSFER);

		log.info("TC_11_Step_Select Chuyen tien nhanh qua the");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[1]);

		log.info("TC_11_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, Tittle_Quick.CARD_TRIP);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_CARD_FROM[0]);

		log.info("TC_11: Lay so du kha dung the");
		String amountAfterString = transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.AVAIBLE_BALANCES);
		double amountAfter = convertMoneyToDouble(amountAfterString, Constants.USD_CURRENCY);
		double amountExpect = Math.round((amountAfter) * 100) / 100;

		log.info("TC_11: Convert so tien chuyen");
		double transferMoney = convertMoneyToDouble(TransferMoneyQuick_Data.TransferQuick.MONEY_USD, " USD");

		log.info("TC_11: Kiem tra so du tai khoan USD sau khi chuyen tien");
		double amountAtual = Math.round((amountStart - transferMoney) * 100) / 100;
		verifyEquals(amountAtual, amountExpect);
	}

	@Test
	public void TC_12_ReportChuyenTienQuaSoTheCoPhiGiaoDichNguoiNhanTraUSDVaXacThucBangOTP() {
		homePage = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_12: Click back man hinh home");
		homePage.clickToDynamicBackIcon(driver, TransferQuick.TRANSFER_MONEY_LABEL);

		log.info("TC_12: Click menu header");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_12: Click bao cao giao dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_12: chon loai bao cao giao dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.ALL_TYPE_TRANSACTION);

		log.info("TC_12: Chon option chuyen tien nhanh qua tai khoan");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSFER_24_7_CARD);

		log.info("TC_12: Chon so tai khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_12: Chon so tai khoan tra cuu");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, accountStart);

		log.info("TC_12: verify thoi tim kiem tu ngay");
		String dateStartActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate");
		String dateStartExpect = getBackwardDate(7);
		verifyEquals(dateStartActual, dateStartExpect);

		log.info("TC_12: verify thoi tim kiem tu ngay");
		String dateEndActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate");
		String dateEndtExpect = getForwardDate(0);
		verifyEquals(dateEndActual, dateEndtExpect);

		log.info("TC_12: Tim kiem");
		transReport.clickToDynamicButton(driver, ReportTitle.SEARCH_BUTTON);

		log.info("TC_02_: Lay ngay tao giao dich hien thi");
		String transferTimeInReport = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_02: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport), transferTime);

		log.info("TC_12: Check ghi chu");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyQuick_Data.TransferQuick.NOTE));

		log.info("TC_12: Check so tien chuyen");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(TransferMoneyQuick_Data.TransferQuick.MONEY_USD) + ".00 USD"));

		log.info("TC_12: Click chi tiet giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_12: Lay ngay tao giao dich hien thi");
		String transferTimeInReport1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_12: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport1), transferTime);

		log.info("TC_12: Check so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_NUMBER), transactionNumber);

		log.info("TC_12: So the chuyen di");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Tittle_Quick.CARD_TRIP), Account_Data.Valid_Account.LIST_CARD_FROM[0]);

		log.info("TC_12: Check tao khoan ghi no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_CARD_SOURCE), accountStart);

		log.info("TC_12: Check tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.NUMBER_CARD_VND), cardTo);

		log.info("TC_12: Check ten nguoi huong");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Tittle_Quick.NAME_RECIVED), TransferMoneyQuick_Data.TransferQuick.RECEIVER_NAME);

		log.info("TC_12: Check so tien giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_MONEY), TransferMoneyQuick_Data.TransferQuick.MONEY_USD + ".00 USD");

		log.info("TC_12: So tien quy doi expect");
		double amountExpect = convertMoneyToDouble(TransferMoneyQuick_Data.TransferQuick.MONEY_USD, Constants.USD_CURRENCY) * convertMoneyToDouble(exchangeRate, Constants.VND_CURRENCY);

		log.info("TC_12: So tien quy doi atual");
		String amountAfter1 = transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_MONEY).replaceAll(".00 USD", "");
		double amountActual = convertMoneyToDouble(amountAfter1, Constants.VND_CURRENCY) * convertMoneyToDouble(exchangeRate, Constants.VND_CURRENCY);

		log.info("TC_12: verify so tien quy doi");
		verifyEquals(amountActual, amountExpect);

		log.info("TC_12_Step_Lay gia tri so tien phí chuyen");
		costTranferString = transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_FEE).replaceAll("\\D+", "");

		log.info("TC_12_Step_Verify so tien phi");
		verifyEquals(costTranferString, fee + "");

		log.info("TC_12: Check noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.CONTENT_TRANSFER).contains(TransferMoneyQuick_Data.TransferQuick.NOTE));

		log.info("TC_12: click button back");
		transReport.clickToDynamicBackIcon(driver, ReportTitle.DETAIL_TRANSFER);

		log.info("TC_12: Chon button back");
		transferMoney.clickToDynamicBackIcon(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_12_Step_Click button home");
		transferMoney.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@Test
	public void TC_13_ChuyenTienQuaTheNguoiNhanTRaPhiVNDVaXacThucBangMatKhau() {
		log.info("TC_13_Step_Click Chuyen tien nhanh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferQuick.TRANSFER_MONEY_LABEL);

		log.info("TC_13_Step_Select Chuyen tien nhanh qua the");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[1]);

		log.info("TC_13_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, Tittle_Quick.CARD_TRIP);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_CARD_FROM[0]);

		log.info("TC_13_Step_Get so tai khoan nguon");
		accountStart = transferMoney.getDynamicTextByLabel(driver, TransferQuick.ACCOUNT_FROM_LABEL);

		log.info("TC_13_Step_Get so du kha dung");
		amountStartString = transferMoney.getDynamicTextByLabel(driver, Tittle_Quick.AVAIBLE_BALANCES).replaceAll("\\D+", "");

		log.info("TC_13_Step_Doi kieu du lieu");
		amountStart = Long.parseLong(amountStartString);

		log.info("TC_13_Step_Nhap so tai khoan chuyen");
		transferMoney.inputToDynamicInputBox(driver, cardTo, Tittle_Quick.TYPE_SELECT_CARD_NUMBER);

		log.info("TC_13_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.MONEY, TransferQuick.MOUNT_LABEL);

		log.info("TC_13_Step_Chon phi giao dich la nguoi nhan tra");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[1]);

		log.info("TC_13_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE, Tittle_Quick.TRANSACTION_INFO, "3");

		log.info("TC_13_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);

		log.info("TC_13_Step_Chon phuong thuc xac thuc");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[0]);

		log.info("TC_13_Step_Lay gia tri so tien chuyen");
		amountTranferString = transferMoney.getDynamicTextByLabel(driver, TransferQuick.MOUNT_LABEL).replaceAll("\\D+", "");

		log.info("TC_13_Step_Verify so tien chuyen");
		verifyEquals(amountTranferString, TransferMoneyQuick_Data.TransferQuick.MONEY);

		log.info("TC_13_Step_doi kieu du lieu string -> long");
		amountTranfer = Long.parseLong(amountTranferString);

		log.info("TC_13_Step_Lay gia tri so tien phí chuyen");
		costTranferString = transferMoney.getDynamicTextByLabel(driver, TransferQuick.AMOUNT_FEE_LABEL).replaceAll("\\D+", "");


		log.info("TC_13_Step_doi kieu du lieu string -> long");
		costTranfer = Long.parseLong(costTranferString);

		log.info("TC_13_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);

		log.info("TC_02_Step_Nhap ma xac thuc");
		transferMoney.inputToDynamicPopupPasswordInput(driver, password, Tittle_Quick.CONTINUE_BUTTON);

		log.info("TC_13_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);

		log.info("TC_13_Verify message thanh cong");
		verifyEquals(transferMoney.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY1);

		log.info("TC_13_get thoi gian giao dich thanh cong");
		transferTime = transferMoney.getDynamicTransferTimeAndMoney(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY1, "4").split(" ")[3];

		log.info("TC_13_Step_: Get ma giao dich");
		transactionNumber = transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.CODE_TRANSFER);

		log.info("TC_13_Step_:Ten nguoi thu huong");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.NAME_RECIVED_TEXT), TransferMoneyQuick_Data.TransferQuick.RECEIVER_NAME);

		log.info("TC_13_Step_: Tai khoan dich");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.CAR_NUMBER), cardTo);

		log.info("TC_13_Step_: Noi dung");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.CONTENT), TransferMoneyQuick_Data.TransferQuick.NOTE);

		log.info("TC_13_Step_: Thuc hien giao dich moi");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.NEW_TRANSFER);

		log.info("TC_13_Step_Select Chuyen tien nhanh qua the");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[1]);

		log.info("TC_13_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, Tittle_Quick.CARD_TRIP);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_CARD_FROM[0]);

		log.info("TC_13_Step_:Check so du kha dung sau khi chuyen tien");
		String amountAfterString = transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.AVAIBLE_BALANCES).replaceAll("\\D+", "");

		long amountAfter = Long.parseLong(amountAfterString);

		log.info("TC_13_Step_:Check so du kha dung sau khi chuyen tien");
		verifyEquals(amountStart - amountTranfer, amountAfter);
	}

	@Test
	// App lỗi số tiền giao dịch, hiện tiền VND hiển thị dạng số thập phân VD:
	// 10000.00 VND => tiền ghi bị sai, ghi là "Một triệu đồng", expect ghi "mười
	// nghìn đồng"

	public void TC_14_ReportChuyenTienQuaTheNguoiNhanTRaPhiVNDVaXacThucBangMatKhau() {
		homePage = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_14: Click back man hinh home");
		homePage.clickToDynamicBackIcon(driver, TransferQuick.TRANSFER_MONEY_LABEL);

		log.info("TC_14: Click menu header");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_14: Click bao cao giao dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_14: chon loai bao cao giao dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.ALL_TYPE_TRANSACTION);

		log.info("TC_14: Chon option chuyen tien nhanh qua tai khoan");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSFER_24_7_CARD);

		log.info("TC_14: Chon so tai khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_14: Chon so tai khoan tra cuu");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, accountStart);

		log.info("TC_14: verify thoi tim kiem tu ngay");
		String dateStartActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate");
		String dateStartExpect = getBackwardDate(7);
		verifyEquals(dateStartActual, dateStartExpect);

		log.info("TC_14: verify thoi tim kiem tu ngay");
		String dateEndActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate");
		String dateEndtExpect = getForwardDate(0);
		verifyEquals(dateEndActual, dateEndtExpect);

		log.info("TC_14: Tim kiem");
		transReport.clickToDynamicButton(driver, ReportTitle.SEARCH_BUTTON);

		log.info("TC_14_: Lay ngay tao giao dich hien thi");
		String transferTimeInReport = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_14: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport), transferTime);

		log.info("TC_14: Check ghi chu");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyQuick_Data.TransferQuick.NOTE));

		log.info("TC_14: Check so tien chuyen");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(TransferMoneyQuick_Data.TransferQuick.MONEY) + " VND"));

		log.info("TC_14: Click chi tiet giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_14: Lay ngay tao giao dich hien thi");
		String transferTimeInReport1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_14: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport1), transferTime);

		log.info("TC_14: Check so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_NUMBER), transactionNumber);

		log.info("TC_14: So the chuyen di");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Tittle_Quick.CARD_TRIP), Account_Data.Valid_Account.LIST_CARD_FROM[0]);

		log.info("TC_14: Check tao khoan ghi no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_CARD_SOURCE), accountStart);

		log.info("TC_14: Check tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.NUMBER_CARD_VND), cardTo);

		log.info("TC_14: Check so nguoi huong");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Tittle_Quick.NAME_RECIVED), TransferMoneyQuick_Data.TransferQuick.RECEIVER_NAME);

		log.info("TC_14: Check so tien giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_MONEY).contains(addCommasToLong(TransferMoneyQuick_Data.TransferQuick.MONEY) + " VND"));

		log.info("TC_14_Step_Lay gia tri so tien phí chuyen");
		costTranferString = transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_FEE).replaceAll("\\D+", "");

		log.info("TC_14_Step_Verify so tien phi");
		verifyEquals(costTranferString, fee + "");

		log.info("TC_14: Check noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.CONTENT_TRANSFER).contains(TransferMoneyQuick_Data.TransferQuick.NOTE));

		log.info("TC_14: click button back");
		transReport.clickToDynamicBackIcon(driver, ReportTitle.DETAIL_TRANSFER);

		log.info("TC_14: Chon button back");
		transferMoney.clickToDynamicBackIcon(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_14_Step_Click button home");
		transferMoney.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");

	}

	@Test
	// Lỗi app, số tiền bị bỏ đơn vị USD
	public void TC_15_ChuyenTienQuaSoTheCoPhiGiaoDichNguoiNhanTraUSDVaXacThucBangMatKhau() {
		log.info("TC_15_Step_Click Chuyen tien nhanh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferQuick.TRANSFER_MONEY_LABEL);

		log.info("TC_15_Step_Select Chuyen tien nhanh qua the");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[1]);

		log.info("TC_15_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, Tittle_Quick.CARD_TRIP);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_CARD_FROM[0]);

		log.info("TC_15_Step_Get so tai khoan nguon");
		accountStart = transferMoney.getDynamicTextByLabel(driver, TransferQuick.ACCOUNT_FROM_LABEL);

		log.info("TC_15_Step_Get so du kha dung");
		amountStartString = transferMoney.getDynamicTextByLabel(driver, Tittle_Quick.AVAIBLE_BALANCES);
		double amountStart = convertMoneyToDouble(amountStartString, Constants.USD_CURRENCY);

		log.info("TC_15_Step_Nhap so tai khoan chuyen");
		transferMoney.inputToDynamicInputBox(driver, cardTo, Tittle_Quick.TYPE_SELECT_CARD_NUMBER);

		log.info("TC_15_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_USD, TransferQuick.MOUNT_LABEL);

		log.info("TC_15_Step_Chon phi giao dich la nguoi nhan tra");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[1]);

		log.info("TC_15_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE, Tittle_Quick.TRANSACTION_INFO, "3");

		log.info("TC_15_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);

		log.info("TC_15_Step_Lay gia tri so tien chuyen");// Loi app, số tiền bỏ đơn vị USD
		amountTranferString = transferMoney.getDynamicTextByLabel(driver, TransferQuick.MOUNT_LABEL).replaceAll(".00 USD", "");

		log.info("TC_15_Step_Verify so tien chuyen");
		verifyEquals(amountTranferString, TransferMoneyQuick_Data.TransferQuick.MONEY_USD);

		log.info("TC_15_Step_doi kieu du lieu string -> long");
		amountTranfer = Long.parseLong(amountTranferString);

		log.info("TC_15_Step_Chon phuong thuc xac thuc");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[0]);

		log.info("TC_15_Step_Lay gia tri so tien phí chuyen");
		costTranferString = transferMoney.getDynamicTextByLabel(driver, TransferQuick.AMOUNT_FEE_LABEL).replaceAll("\\D+", "");

//		double usdTransferFee = convertMoneyToDouble(fee + "", Constants.VND_CURRENCY) / convertMoneyToDouble(exchangeRate, Constants.VND_CURRENCY);


		log.info("TC_15_Step_doi kieu du lieu string -> long");
		costTranfer = Long.parseLong(costTranferString);

		log.info("TC_15_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);

		log.info("TC_15_Step_Nhap ma xac thuc");
		transferMoney.inputToDynamicPopupPasswordInput(driver, password, Tittle_Quick.CONTINUE_BUTTON);

		log.info("TC_15_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);

		log.info("TC_15_Verify message thanh cong");
		verifyEquals(transferMoney.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY1);

		log.info("TC_15_get thoi gian giao dich thanh cong");
		transferTime = transferMoney.getDynamicTransferTimeAndMoney(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY1, "4").split(" ")[3];

		log.info("TC_15_Step_: Get ma giao dich");
		transactionNumber = transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.CODE_TRANSFER);

		log.info("TC_15_Step_:Ten nguoi thu huong");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.NAME_RECIVED_TEXT), TransferMoneyQuick_Data.TransferQuick.RECEIVER_NAME);

		log.info("TC_15_Step_: Tai khoan dich");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.CAR_NUMBER), cardTo);

		log.info("TC_15_Step_: Noi dung");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.CONTENT), TransferMoneyQuick_Data.TransferQuick.NOTE);

		log.info("TC_15_Step_: Thuc hien giao dich moi");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.NEW_TRANSFER);

		log.info("TC_15_Step_Select Chuyen tien nhanh qua the");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[1]);

		log.info("TC_15_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, Tittle_Quick.CARD_TRIP);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_CARD_FROM[0]);

		log.info("TC_15: Lay so du kha dung the");
		String amountAfterString = transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.AVAIBLE_BALANCES);
		double amountAfter = convertMoneyToDouble(amountAfterString, Constants.USD_CURRENCY);
		double amountExpect = Math.round((amountAfter) * 100) / 100;

		log.info("TC_15: Convert so tien chuyen");
		double transferMoney = convertMoneyToDouble(TransferMoneyQuick_Data.TransferQuick.MONEY_USD, " USD");

		log.info("TC_15: Kiem tra so du tai khoan USD sau khi chuyen tien");
		double amountAtual = Math.round((amountStart - transferMoney) * 100) / 100;
		verifyEquals(amountAtual, amountExpect);
	}

	@Test
	public void TC_16_ReportChuyenTienQuaSoTheCoPhiGiaoDichNguoiNhanTraUSDVaXacThucBangMatKhau() {
		homePage = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_16: Click back man hinh home");
		homePage.clickToDynamicBackIcon(driver, TransferQuick.TRANSFER_MONEY_LABEL);

		log.info("TC_16: Click menu header");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_16: Click bao cao giao dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_16: chon loai bao cao giao dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.ALL_TYPE_TRANSACTION);

		log.info("TC_16: Chon option chuyen tien nhanh qua tai khoan");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSFER_24_7_CARD);

		log.info("TC_16: Chon so tai khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_16: Chon so tai khoan tra cuu");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, accountStart);

		log.info("TC_16: verify thoi tim kiem tu ngay");
		String dateStartActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate");
		String dateStartExpect = getBackwardDate(7);
		verifyEquals(dateStartActual, dateStartExpect);

		log.info("TC_16: verify thoi tim kiem tu ngay");
		String dateEndActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate");
		String dateEndtExpect = getForwardDate(0);
		verifyEquals(dateEndActual, dateEndtExpect);

		log.info("TC_16: Tim kiem");
		transReport.clickToDynamicButton(driver, ReportTitle.SEARCH_BUTTON);

		log.info("TC_16_: Lay ngay tao giao dich hien thi");
		String transferTimeInReport = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_16: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport), transferTime);

		log.info("TC_16: Check ghi chu");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyQuick_Data.TransferQuick.NOTE));

		log.info("TC_16: Check so tien chuyen");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(TransferMoneyQuick_Data.TransferQuick.MONEY_USD) + ".00 USD"));

		log.info("TC_16: Click chi tiet giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_16: Lay ngay tao giao dich hien thi");
		String transferTimeInReport1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_16: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport1), transferTime);

		log.info("TC_16: Check so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_NUMBER), transactionNumber);

		log.info("TC_16: So the chuyen di");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Tittle_Quick.CARD_TRIP), Account_Data.Valid_Account.LIST_CARD_FROM[0]);

		log.info("TC_16: Check tao khoan ghi no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_CARD_SOURCE), accountStart);

		log.info("TC_16: Check tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.NUMBER_CARD_VND), cardTo);

		log.info("TC_16: Check so nguoi huong");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Tittle_Quick.NAME_RECIVED), TransferMoneyQuick_Data.TransferQuick.RECEIVER_NAME);

		log.info("TC_16: Check so tien giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_MONEY), TransferMoneyQuick_Data.TransferQuick.MONEY_USD + ".00 USD");

		log.info("TC_16: So tien quy doi expect");
		double amountAfter = convertMoneyToDouble(TransferMoneyQuick_Data.TransferQuick.MONEY_USD, Constants.USD_CURRENCY);
		double amountExpect = amountAfter * convertMoneyToDouble(exchangeRate, Constants.VND_CURRENCY);

		log.info("TC_16: So tien quy doi atual");
		String amountAfter1 = transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_MONEY).replaceAll(".00 USD", "");
		double amountActual = convertMoneyToDouble(amountAfter1, Constants.VND_CURRENCY) * convertMoneyToDouble(exchangeRate, Constants.VND_CURRENCY);

		log.info("TC_16: verify so tien quy doi");
		verifyEquals(amountActual, amountExpect);

		log.info("TC_16_Step_Lay gia tri so tien phí chuyen");
		costTranferString = transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_FEE).replaceAll("\\D+", "");

		log.info("TC_16_Step_Verify so tien phi");
		verifyEquals(costTranferString, fee + "");

		log.info("TC_16: Check noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.CONTENT_TRANSFER).contains(TransferMoneyQuick_Data.TransferQuick.NOTE));

		log.info("TC_16: click button back");
		transReport.clickToDynamicBackIcon(driver, ReportTitle.DETAIL_TRANSFER);

		log.info("TC_16: Chon button back");
		transferMoney.clickToDynamicBackIcon(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_16_Step_Click button home");
		transferMoney.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@AfterClass(alwaysRun = true)

	public void afterClass() {
		closeApp();
		service.stop();
	}
}