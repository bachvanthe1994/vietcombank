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
import model.SourceAccountModel;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.SettingVCBSmartOTPPageObject;
import pageObjects.TransactionReportPageObject;
import pageObjects.TransferMoneyObject;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransactionReport_Data.ReportTitle;
import vietcombank_test_data.TransferMoneyQuick_Data;
import vietcombank_test_data.TransferMoneyQuick_Data.Tittle_Quick;
import vietcombank_test_data.TransferMoneyQuick_Data.TransferQuick;

public class Flow_QuickMoneyTransfer247_Part1 extends Base {
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
	private String costTranferString, bankOut, accountRecived;
	private long costTranfer;
	private long fee;
	private String exchangeRate;
	private String password = "";
	SourceAccountModel sourceAccount = new SourceAccountModel();
	private SettingVCBSmartOTPPageObject smartOTP;
	String otpSmart, newOTP,nameCustomer;

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
		password = pass;
		transferMoney = PageFactoryManager.getTransferMoneyObject(driver);
		bankOut = getDataInCell(21);
		accountRecived = getDataInCell(4);
		otpSmart = getDataInCell(6);
		nameCustomer  = getDataInCell(40);
		newOTP = "111222";
		smartOTP = PageFactoryManager.getSettingVCBSmartOTPPageObject(driver);
		smartOTP.setupSmartOTP(newOTP, otpSmart);
	}

	@Test
	public void TC_01_ChuyenTienNguoiChuyenTraPhiVNDOTP() throws InterruptedException {
		log.info("TC_01_Step_Click Chuyen tien nhanh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferQuick.TRANSFER_MONEY_LABEL);

		log.info("TC_01_Step_Select Chuyen tien nhanh qua tai khoan");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);

		log.info("TC_01_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, TransferQuick.ACCOUNT_FROM_LABEL);
		sourceAccount = transferMoney.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		log.info("TC_01_Step_Get so du kha dung");
		amountStartString = transferMoney.getDynamicTextByLabel(driver, Tittle_Quick.AVAIBLE_BALANCES).replaceAll("\\D+", "");

		log.info("TC_01_Step_Doi kieu du lieu");
		amountStart = Long.parseLong(amountStartString);

		log.info("TC_01_Step_Nhap so tai khoan chuyen");
		transferMoney.inputToDynamicInputBox(driver, accountRecived, Tittle_Quick.TYPE_SELECT_ACCOUNT);

		log.info("TC_01_Step_Select ngan hang");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Tittle_Quick.BANK_RECIVED);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, bankOut);

		log.info("TC_01_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.MONEY, TransferQuick.MOUNT_LABEL);

		log.info("TC_01_Step_Chon phi giao dich la nguoi chuyen tra");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[0]);

		log.info("TC_01_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE, Tittle_Quick.TRANSACTION_INFO, "3");

		log.info("TC_01_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);

		log.info("TC_01_Step_Lay gia tri so tien chuyen");
		amountTranferString = transferMoney.getDynamicTextByLabel(driver, TransferQuick.MOUNT_LABEL).replaceAll("\\D+", "");

		log.info("TC_01_Step_Verify so tien chuyen");
		verifyEquals(amountTranferString, TransferMoneyQuick_Data.TransferQuick.MONEY);

		log.info("TC_01_Step_doi kieu du lieu string -> long");
		amountTranfer = Long.parseLong(amountTranferString);

		log.info("TC_01_Step_Chon phuong thuc xac thuc");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[1]);

		log.info("TC_01_Step_Lay gia tri so tien phí chuyen");
		costTranferString = transferMoney.getDynamicTextByLabel(driver, TransferQuick.AMOUNT_FEE_LABEL).replaceAll("\\D+", "");

		log.info("TC_01_Step_doi kieu du lieu string -> long");
		costTranfer = Long.parseLong(costTranferString);

		log.info("TC_01_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);

		log.info("TC_01_Step_Nhap ma xac thuc");
		transferMoney.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, Tittle_Quick.CONTINUE_BUTTON);

		log.info("TC_01_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);

		log.info("TC_01_Verify message thanh cong");
		verifyEquals(transferMoney.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY1);

		log.info("TC_01_get thoi gian giao dich thanh cong");
		transferTime = transferMoney.getDynamicTransferTimeAndMoney(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY1, "4").split(" ")[3];

		log.info("TC_01_Step_: Get ma giao dich");
		transactionNumber = transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.CODE_TRANSFER);

		log.info("TC_01_Step_:Ten nguoi thu huong");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.NAME_RECIVED_TEXT), nameCustomer);

		log.info("TC_01_Step_: Tai khoan dich");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.ACCOUNT_RECIVED_TEXT), accountRecived);

		log.info("TC_01_Step_: ngan hang thu huong");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.BANK_RECIVED), bankOut);

		log.info("TC_01_Step_: Noi dung");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.CONTENT), TransferMoneyQuick_Data.TransferQuick.NOTE);

		log.info("TC_01_Step_: Thuc hien giao dich moi");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.NEW_TRANSFER);

		log.info("TC_01_Step_:Tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, TransferQuick.ACCOUNT_FROM_LABEL);

		log.info("TC_01_Step_: Tai khoan chuyen");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_01_Step_:Check so du kha dung sau khi chuyen tien");
		String amountAfterString = transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.AVAIBLE_BALANCES).replaceAll("\\D+", "");

		long amountAfter = Long.parseLong(amountAfterString);

		log.info("TC_01_Step_:Check so du kha dung sau khi chuyen tien");
		verifyEquals(amountStart - amountTranfer - costTranfer, amountAfter);
	}

	@Test
	public void TC_02_BaoCaoChuyenTienNguoiChuyenTraPhiVNDOTP() {
		homePage = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_02: Click back man hinh home");
		homePage.clickToDynamicBackIcon(driver, TransferQuick.TRANSFER_MONEY_LABEL);

		log.info("TC_02: Click menu header");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_02: Click bao cao giao dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_02: chon loai bao cao giao dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.ALL_TYPE_TRANSACTION);

		log.info("TC_Step_: Chon option chuyen tien nhanh qua tai khoan");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSFER_24_7_ACCOUNT);

		log.info("TC_02: Chon so tai khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_02: Chon so tai khoan tra cuu");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_02: verify thoi tim kiem tu ngay");
		String dateStartActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate");
		String dateStartExpect = getBackwardDate(6);
		verifyEquals(dateStartActual, dateStartExpect);

		log.info("TC_02: verify thoi tim kiem tu ngay");
		String dateEndActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate");
		String dateEndtExpect = getForwardDate(0);
		verifyEquals(dateEndActual, dateEndtExpect);

		log.info("TC_02: Tim kiem");
		transReport.clickToDynamicButton(driver, ReportTitle.SEARCH_BUTTON);

		log.info("TC_02_: Lay ngay tao giao dich hien thi");
		String transferTimeInReport = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_02: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport), transferTime);

		log.info("TC_02: Check ghi chu");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyQuick_Data.TransferQuick.NOTE));

		log.info("TC_02: Check so tien chuyen");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(TransferMoneyQuick_Data.TransferQuick.MONEY) + " VND"));

		log.info("TC_02: Click chi tiet giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_02: Lay ngay tao giao dich hien thi");
		String transferTimeInReport1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_02: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport1), transferTime);

		log.info("TC_02: Check so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_NUMBER), transactionNumber);

		log.info("TC_02: Check tao khoan ghi no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_CARD), sourceAccount.account);

		log.info("TC_02: Check tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_CREDITED), accountRecived);

		log.info("TC_02: Check so tien giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_MONEY).contains(addCommasToLong(TransferMoneyQuick_Data.TransferQuick.MONEY) + " VND"));

		log.info("TC_02: Check so nguoi huong");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Tittle_Quick.NAME_RECIVED), nameCustomer);
		
		log.info("TC_10: Check loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TYPE_TRANSFER), TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);

		log.info("TC_02: Check ngan hang huong");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Tittle_Quick.BANK_RECIVED), bankOut);

		log.info("TC_02: Check phi giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.FEE), TransferMoneyQuick_Data.TransferQuick.COST_SUB[0]);

		log.info("TC_02: Check loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TYPE_TRANSFER), TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);

		log.info("TC_02: Check noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.CONTENT_TRANSFER).contains(TransferMoneyQuick_Data.TransferQuick.NOTE));

		log.info("TC_02: Chick chi tiet giao dich");
		transReport.clickToDynamicBackIcon(driver, ReportTitle.DETAIL_TRANSFER);

		log.info("TTC_02: Chon button back");
		transferMoney.clickToDynamicBackIcon(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_02_Click button home");
		transferMoney.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@Test
	// Lỗi app, số tiền bị bỏ đơn vị USD
	public void TC_03_ChuyenTienNhanhQuaTaiKhoanChonUSDNguoiChuyenTraPhiOTP() {
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferQuick.TRANSFER_MONEY_LABEL);

		log.info("TC_03_Step_Select Chuyen tien nhanh qua tai khoan");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);

		log.info("TC_03_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, TransferQuick.ACCOUNT_FROM_LABEL);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.USD_ACCOUNT);

		log.info("TC_03_Step_Get so du kha dung");
		amountStartString = transferMoney.getDynamicTextByLabel(driver, Tittle_Quick.AVAIBLE_BALANCES);
		double amountStart1 = convertMoneyToDouble(amountStartString, Constants.USD_CURRENCY);

		log.info("TC_03_Step_Nhap so tai khoan chuyen");
		transferMoney.inputToDynamicInputBox(driver, accountRecived, Tittle_Quick.TYPE_SELECT_ACCOUNT);

		log.info("TC_03_Step_:Lay so tien quy doi ra VND");
		String exchange = transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.RATE_REFER).split(" ~ ", 2)[1];
		exchangeRate = exchange.replace(" VND", "");

		log.info("TC_03_Step_Select ngan hang");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Tittle_Quick.BANK_RECIVED);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, bankOut);

		log.info("TC_03_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_USD, TransferQuick.MOUNT_LABEL);

		log.info("TC_03_Step_Chon phi giao dich la nguoi chuyen");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[0]);

		log.info("TC_03_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE, Tittle_Quick.TRANSACTION_INFO, "3");

		log.info("TC_03_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);

		log.info("TC_03_Step_Verify so tien chuyen");
		verifyTrue(transferMoney.getDynamicTextInTransactionDetail(driver, "Số tiền(USD)").contains(addCommasToDouble(TransferMoneyQuick_Data.TransferQuick.MONEY_USD) + " USD"));

		log.info("TC_03_Step_Chon phuong thuc xac thuc");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[1]);

		log.info("TC_03_Step 23: Kiem tra so tien phi hien thi");
		String Fee = transferMoney.getDynamicTextInTransactionDetail(driver, TransferQuick.AMOUNT_FEE_LABEL).replaceAll("\\D+", "");

		log.info("TC_03_Step 23: So tien phi chuyen doi ra USD");
		double usdTransferFee = convertMoneyToDouble(Fee, Constants.VND_CURRENCY) / convertMoneyToDouble(exchangeRate, Constants.VND_CURRENCY);

		log.info("TC_03_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);

		log.info("TC_03_Step_Nhap ma xac thuc");
		transferMoney.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, Tittle_Quick.CONTINUE_BUTTON);

		log.info("TC_03_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);

		log.info("TC_03_Verify message thanh cong");
		verifyEquals(transferMoney.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY1);

		log.info("TC_03_get thoi gian giao dich thanh cong");
		transferTime = transferMoney.getDynamicTransferTimeAndMoney(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY1, "4").split(" ")[3];

		log.info("TC_03_Step_: Get ma giao dich");
		transactionNumber = transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.CODE_TRANSFER);

		log.info("TC_03_Step_:Ten nguoi thu huong");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.NAME_RECIVED_TEXT), nameCustomer);

		log.info("TC_03_Step_: Tai khoan dich");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.ACCOUNT_RECIVED_TEXT), accountRecived);

		log.info("TC_03_Step_: Noi dung");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.CONTENT), TransferMoneyQuick_Data.TransferQuick.NOTE);

		log.info("TC_03_Step_: Thuc hien giao dich moi");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.NEW_TRANSFER);

		log.info("TC_03_Step_: Chon tai khoan chuyen den");
		transferMoney.clickToDynamicDropDown(driver, TransferQuick.ACCOUNT_FROM_LABEL);

		log.info("TC_03_Step_: Chon tai khoan chuyen den");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.USD_ACCOUNT);

		log.info("TC_03_Step: Lay so du kha dung tai khoan");
		String amountAfterString = transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.AVAIBLE_BALANCES);
		double amountAfter = convertMoneyToDouble(amountAfterString, Constants.USD_CURRENCY);
		double amountExpect = Math.round((amountAfter) * 100) / 100;

		log.info("TC_03: Convert so tien chuyen");
		double transferMoney = convertMoneyToDouble(TransferMoneyQuick_Data.TransferQuick.MONEY_USD, " USD");

		log.info("TC_03: Kiem tra so du tai khoan USD sau khi chuyen tien");
		double amountAtual = Math.round((amountStart1 - transferMoney - usdTransferFee) * 100) / 100;
		verifyEquals(amountAtual, amountExpect);
	}

	@Test
	public void TC_04_BaoCaoChuyenTienNhanhQuaTaiKhoanChonUSDNguoiChuyenTraPhiOTP() {
		homePage = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_04_Step: Click back man hinh home");
		homePage.clickToDynamicBackIcon(driver, TransferQuick.TRANSFER_MONEY_LABEL);

		log.info("TC_04_Step: Click menu header");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_04_Step: Click bao cao giao dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_04_Step: chon loai bao cao giao dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.ALL_TYPE_TRANSACTION);

		log.info("TC_04_Step: Chon option chuyen tien nhanh qua tai khoan");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSFER_24_7_ACCOUNT);

		log.info("TC_04_Step: Chon so tai khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_04_Step: Chon so tai khoan chuyen");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.USD_ACCOUNT);

		log.info("TC_04_Step: verify thoi tim kiem tu ngay");
		String dateStartActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate");
		String dateStartExpect = getBackwardDate(6);
		verifyEquals(dateStartActual, dateStartExpect);

		log.info("TC_04_Step: verify thoi tim kiem tu ngay");
		String dateEndActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate");
		String dateEndtExpect = getForwardDate(0);
		verifyEquals(dateEndActual, dateEndtExpect);

		log.info("TC_04_Step: Tim kiem");
		transReport.clickToDynamicButton(driver, ReportTitle.SEARCH_BUTTON);

		log.info("TC_04_: Lay ngay tao giao dich hien thi");
		String transferTimeInReport = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_04_: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport), transferTime);

		log.info("TC_04_Step: Check ghi chu");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyQuick_Data.TransferQuick.NOTE));

		log.info("TC_04_Step: Check so tien chuyen");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + TransferMoneyQuick_Data.TransferQuick.MONEY_USD + " USD"));

		log.info("TC_04_Step: Click chi tiet giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_04_: Lay ngay tao giao dich hien thi");
		String transferTimeInReport1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_04_: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport1), transferTime);

		log.info("TC_04_Step: Check so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_NUMBER), transactionNumber);

		log.info("TC_04_Step: Check tao khoan ghi no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_CARD), Account_Data.Valid_Account.USD_ACCOUNT);

		log.info("TC_04_Step: Check tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_CREDITED), accountRecived);

		log.info("TC_04_Step: Check so tien giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_MONEY).contains(addCommasToLong(TransferMoneyQuick_Data.TransferQuick.MONEY_USD) + ".00 USD"));

		log.info("TC_04_Step: So tien quy doi expect");
		double amountAfter = convertMoneyToDouble(TransferMoneyQuick_Data.TransferQuick.MONEY_USD, Constants.USD_CURRENCY);
		double amountExpect = amountAfter * convertMoneyToDouble(exchangeRate, Constants.VND_CURRENCY);

		log.info("TC_04_Step: So tien quy doi atual");
		String amountAfter1 = transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_MONEY).replaceAll(".00 USD", "");
		double amountActual = convertMoneyToDouble(amountAfter1, Constants.VND_CURRENCY) * convertMoneyToDouble(exchangeRate, Constants.VND_CURRENCY);

		log.info("TC_04_Step: verify so tien quy doi");
		verifyEquals(amountActual, amountExpect);

		log.info("TC_04_Step: Check so nguoi huong");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Tittle_Quick.NAME_RECIVED), nameCustomer);

		log.info("TC_04_Step: Check ngan hang huong");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Tittle_Quick.BANK_RECIVED), bankOut);

		log.info("TC_10: Check loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TYPE_TRANSFER), TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		
		log.info("TC_04_Step: Check phi giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.FEE), TransferMoneyQuick_Data.TransferQuick.COST_SUB[0]);

		log.info("TC_04_Step: Check so tien phi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_FEE).replaceAll("\\D+", ""), fee + "");

		log.info("TC_04_Step: Check loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TYPE_TRANSFER), TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);

		log.info("TC_04_Step: Check noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.CONTENT_TRANSFER).contains(TransferMoneyQuick_Data.TransferQuick.NOTE));

		log.info("TC_04_Step: Chon button back");
		transferMoney.clickToDynamicBackIcon(driver, ReportTitle.DETAIL_TRANSFER);

		log.info("TC_04_Step: Chon button back");
		transferMoney.clickToDynamicBackIcon(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_04_Step_Click button home");
		transferMoney.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@Test
	public void TC_05_ChuyenTienNguoiChuyenTraPhiVND_SmartOTP() throws InterruptedException {
		log.info("TC_01_Step_Click Chuyen tien nhanh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferQuick.TRANSFER_MONEY_LABEL);

		log.info("TC_01_Step_Select Chuyen tien nhanh qua tai khoan");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);

		log.info("TC_01_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, TransferQuick.ACCOUNT_FROM_LABEL);
		sourceAccount = transferMoney.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		log.info("TC_01_Step_Get so du kha dung");
		amountStartString = transferMoney.getDynamicTextByLabel(driver, Tittle_Quick.AVAIBLE_BALANCES).replaceAll("\\D+", "");

		log.info("TC_01_Step_Doi kieu du lieu");
		amountStart = Long.parseLong(amountStartString);

		log.info("TC_01_Step_Nhap so tai khoan chuyen");
		transferMoney.inputToDynamicInputBox(driver, accountRecived, Tittle_Quick.TYPE_SELECT_ACCOUNT);

		log.info("TC_01_Step_Select ngan hang");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Tittle_Quick.BANK_RECIVED);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, bankOut);

		log.info("TC_01_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.MONEY, TransferQuick.MOUNT_LABEL);

		log.info("TC_01_Step_Chon phi giao dich la nguoi chuyen tra");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[0]);

		log.info("TC_01_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE, Tittle_Quick.TRANSACTION_INFO, "3");

		log.info("TC_01_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);

		log.info("TC_01_Step_Lay gia tri so tien chuyen");
		amountTranferString = transferMoney.getDynamicTextByLabel(driver, TransferQuick.MOUNT_LABEL).replaceAll("\\D+", "");

		log.info("TC_01_Step_Verify so tien chuyen");
		verifyEquals(amountTranferString, TransferMoneyQuick_Data.TransferQuick.MONEY);

		log.info("TC_01_Step_doi kieu du lieu string -> long");
		amountTranfer = Long.parseLong(amountTranferString);

		log.info("TC_01_Step_Chon phuong thuc xac thuc");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[2]);

		log.info("TC_01_Step_Lay gia tri so tien phí chuyen");
		costTranferString = transferMoney.getDynamicTextByLabel(driver, TransferQuick.AMOUNT_FEE_LABEL).replaceAll("\\D+", "");

		log.info("TC_01_Step_doi kieu du lieu string -> long");
		costTranfer = Long.parseLong(costTranferString);

		log.info("TC_01_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);

		log.info("TC_01_Step_Nhap ma xac thuc");
		transferMoney.inputToDynamicSmartOTP(driver, newOTP, "com.VCB:id/otp");

		log.info("TC_01_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);

		log.info("TC_01_Verify message thanh cong");
		verifyEquals(transferMoney.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY1);

		log.info("TC_01_get thoi gian giao dich thanh cong");
		transferTime = transferMoney.getDynamicTransferTimeAndMoney(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY1, "4").split(" ")[3];

		log.info("TC_01_Step_: Get ma giao dich");
		transactionNumber = transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.CODE_TRANSFER);

		log.info("TC_01_Step_:Ten nguoi thu huong");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.NAME_RECIVED_TEXT), nameCustomer);

		log.info("TC_01_Step_: Tai khoan dich");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.ACCOUNT_RECIVED_TEXT), accountRecived);

		log.info("TC_01_Step_: ngan hang thu huong");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.BANK_RECIVED), bankOut);

		log.info("TC_01_Step_: Noi dung");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.CONTENT), TransferMoneyQuick_Data.TransferQuick.NOTE);

		log.info("TC_01_Step_: Thuc hien giao dich moi");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.NEW_TRANSFER);

		log.info("TC_01_Step_:Tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, TransferQuick.ACCOUNT_FROM_LABEL);

		log.info("TC_01_Step_: Tai khoan chuyen");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_01_Step_:Check so du kha dung sau khi chuyen tien");
		String amountAfterString = transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.AVAIBLE_BALANCES).replaceAll("\\D+", "");

		long amountAfter = Long.parseLong(amountAfterString);

		log.info("TC_01_Step_:Check so du kha dung sau khi chuyen tien");
		verifyEquals(amountStart - amountTranfer - costTranfer, amountAfter);
	}

	@Test
	public void TC_06_BaoCaoChuyenTienNguoiChuyenTraPhiVND_SmartOTP() {
		homePage = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_02: Click back man hinh home");
		homePage.clickToDynamicBackIcon(driver, TransferQuick.TRANSFER_MONEY_LABEL);

		log.info("TC_02: Click menu header");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_02: Click bao cao giao dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_02: chon loai bao cao giao dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.ALL_TYPE_TRANSACTION);

		log.info("TC_Step_: Chon option chuyen tien nhanh qua tai khoan");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSFER_24_7_ACCOUNT);

		log.info("TC_02: Chon so tai khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_02: Chon so tai khoan tra cuu");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_02: verify thoi tim kiem tu ngay");
		String dateStartActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate");
		String dateStartExpect = getBackwardDate(6);
		verifyEquals(dateStartActual, dateStartExpect);

		log.info("TC_02: verify thoi tim kiem tu ngay");
		String dateEndActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate");
		String dateEndtExpect = getForwardDate(0);
		verifyEquals(dateEndActual, dateEndtExpect);

		log.info("TC_02: Tim kiem");
		transReport.clickToDynamicButton(driver, ReportTitle.SEARCH_BUTTON);

		log.info("TC_02_: Lay ngay tao giao dich hien thi");
		String transferTimeInReport = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_02: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport), transferTime);

		log.info("TC_02: Check ghi chu");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyQuick_Data.TransferQuick.NOTE));

		log.info("TC_02: Check so tien chuyen");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(TransferMoneyQuick_Data.TransferQuick.MONEY) + " VND"));

		log.info("TC_02: Click chi tiet giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_02: Lay ngay tao giao dich hien thi");
		String transferTimeInReport1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_02: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport1), transferTime);

		log.info("TC_02: Check so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_NUMBER), transactionNumber);

		log.info("TC_02: Check tao khoan ghi no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_CARD), sourceAccount.account);

		log.info("TC_02: Check tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_CREDITED), accountRecived);

		log.info("TC_02: Check so tien giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_MONEY).contains(addCommasToLong(TransferMoneyQuick_Data.TransferQuick.MONEY) + " VND"));

		log.info("TC_02: Check so nguoi huong");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Tittle_Quick.NAME_RECIVED), nameCustomer);
		
		log.info("TC_10: Check loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TYPE_TRANSFER), TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);

		log.info("TC_02: Check ngan hang huong");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Tittle_Quick.BANK_RECIVED), bankOut);

		log.info("TC_02: Check phi giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.FEE), TransferMoneyQuick_Data.TransferQuick.COST_SUB[0]);

		log.info("TC_02: Check loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TYPE_TRANSFER), TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);

		log.info("TC_02: Check noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.CONTENT_TRANSFER).contains(TransferMoneyQuick_Data.TransferQuick.NOTE));

		log.info("TC_02: Chick chi tiet giao dich");
		transReport.clickToDynamicBackIcon(driver, ReportTitle.DETAIL_TRANSFER);

		log.info("TTC_02: Chon button back");
		transferMoney.clickToDynamicBackIcon(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_02_Click button home");
		transferMoney.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@Test
	public void TC_07_ChuyenTienQuaTKNguoiChuyenTraPhiVNDXacThucMatKhau() throws InterruptedException {
		log.info("TC_05_Step_Click Chuyen tien nhanh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferQuick.TRANSFER_MONEY_LABEL);

		log.info("TC_05_Step_Select Chuyen tien nhanh qua tai khoan");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);

		log.info("TC_05_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, TransferQuick.ACCOUNT_FROM_LABEL);

		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_05_Step_Get so du kha dung");
		amountStartString = transferMoney.getDynamicTextByLabel(driver, Tittle_Quick.AVAIBLE_BALANCES).replaceAll("\\D+", "");

		amountStart = Long.parseLong(amountStartString);

		log.info("TC_05_Step_Nhap so tai khoan chuyen");
		transferMoney.inputToDynamicInputBox(driver, accountRecived, Tittle_Quick.TYPE_SELECT_ACCOUNT);

		log.info("TC_05_Step_Select ngan hang");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Tittle_Quick.BANK_RECIVED);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, bankOut);

		log.info("TC_05_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.MONEY, TransferQuick.MOUNT_LABEL);

		log.info("TC_05_Step_Chon phi giao dich");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[0]);

		log.info("TC_05_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE, Tittle_Quick.TRANSACTION_INFO, "3");

		log.info("TC_05_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);

		log.info("TC_05_Step_Verify so tien chuyen");
		amountTranferString = transferMoney.getDynamicTextByLabel(driver, TransferQuick.MOUNT_LABEL).replaceAll("\\D+", "");
		verifyEquals(amountTranferString, TransferMoneyQuick_Data.TransferQuick.MONEY);
		amountTranfer = Integer.parseInt(amountTranferString);

		log.info("TC_05_Step_Chon phuong thuc xac thuc");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[0]);

		log.info("TC_05_Step_Verify phi chuyen tien");
		costTranferString = transferMoney.getDynamicTextByLabel(driver, TransferQuick.AMOUNT_FEE_LABEL).replaceAll("\\D+", "");
		costTranfer = Long.parseLong(costTranferString);

		log.info("TC_05_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);

		log.info("TC_05_Step_Nhap ma xac thuc");
		transferMoney.inputToDynamicPopupPasswordInput(driver, password, Tittle_Quick.CONTINUE_BUTTON);

		log.info("TC_05_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);

		log.info("TC_05_Verify message thanh cong");
		verifyEquals(transferMoney.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY1);

		log.info("TC_05_get thoi gian giao dich thanh cong");
		transferTime = transferMoney.getDynamicTransferTimeAndMoney(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY1, "4").split(" ")[3];

		log.info("TC_05_Step_:Lay ma giao dich");
		transactionNumber = transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.CODE_TRANSFER);

		log.info("TC_05_Step_:Ten nguoi thu huong");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.NAME_RECIVED_TEXT), nameCustomer);

		log.info("TC_05_Step_: Tai khoan dich");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.ACCOUNT_RECIVED_TEXT), accountRecived);

		log.info("TC_05_Step_: ngan hang thu huong");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.BANK_RECIVED), bankOut);

		log.info("TC_05_Step_: Check noi dung");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.CONTENT), TransferMoneyQuick_Data.TransferQuick.NOTE);

		log.info("TC_05_Step_: Chon thuc hien giao dich");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.NEW_TRANSFER);

		log.info("TC_05_Step_: Chon tai khoan chuyen");
		transferMoney.clickToDynamicDropDown(driver, TransferQuick.ACCOUNT_FROM_LABEL);

		log.info("TC_05_Step_: Chon tai khoan chuyen");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_05_Step_:Check so du kha dung sau khi chuyen tien");
		String amountAfterString = transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.AVAIBLE_BALANCES).replaceAll("\\D+", "");

		long amountAfter = Long.parseLong(amountAfterString);

		log.info("TC_05_Step_:Check so du kha dung sau khi chuyen tien");
		verifyEquals(amountStart - amountTranfer - costTranfer, amountAfter);
	}

	@Test
	public void TC_08_BaoCaoChuyenTienQuaTKNguoiChuyenTraPhiVNDXacThucMatKhau() {
		homePage = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_06_Step_: Click back man hinh home");
		homePage.clickToDynamicBackIcon(driver, TransferQuick.TRANSFER_MONEY_LABEL);

		log.info("TC_06_Step_: Click menu header");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_06_Step_: Click bao cao giao dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_06_Step_: chon loai bao cao giao dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.ALL_TYPE_TRANSACTION);

		log.info("TC_06_Step_: Chon option chuyen tien nhanh qua tai khoan");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSFER_24_7_ACCOUNT);

		log.info("TC_06_Step_: Chon so tai khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_06_Step_: Chon so tai khoan tra cuu");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_06_Step_: verify thoi tim kiem tu ngay");
		String dateStartActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate");
		String dateStartExpect = getBackwardDate(6);
		verifyEquals(dateStartActual, dateStartExpect);

		log.info("TC_06_Step_: verify thoi tim kiem tu ngay");
		String dateEndActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate");
		String dateEndtExpect = getForwardDate(0);
		verifyEquals(dateEndActual, dateEndtExpect);

		log.info("TC_06_Step_: Tim kiem");
		transReport.clickToDynamicButton(driver, ReportTitle.SEARCH_BUTTON);

		log.info("TC_06_: Lay ngay tao giao dich hien thi");
		String transferTimeInReport = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_06: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport), transferTime);

		log.info("TC_06_Step_: Check ghi chu");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyQuick_Data.TransferQuick.NOTE));

		log.info("TC_06_Step_: Check so tien chuyen");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(TransferMoneyQuick_Data.TransferQuick.MONEY) + " VND"));

		log.info("TC_06_Step_: Chon ngay thang");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_06: Lay ngay tao giao dich hien thi");
		String transferTimeInReport1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_06: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport1), transferTime);

		log.info("TC_06_Step_: Check so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_NUMBER), transactionNumber);

		log.info("TC_06_Step_: Check tao khoan ghi no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_CARD), Account_Data.Valid_Account.LIST_ACCOUNT_FROM[0]);

		log.info("TC_06_Step_: Check tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_CREDITED), accountRecived);

		log.info("TC_06_Step_: Check so tien giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_MONEY).contains(addCommasToLong(TransferMoneyQuick_Data.TransferQuick.MONEY) + " VND"));

		log.info("TC_06_Step_: Check so nguoi huong");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Tittle_Quick.NAME_RECIVED), nameCustomer);

		log.info("TC_06_Step_: Check ngan hang huong");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Tittle_Quick.BANK_RECIVED), bankOut);
		
		log.info("TC_10: Check loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TYPE_TRANSFER), TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);

		log.info("TC_06_Step_: Check phi giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.FEE), TransferMoneyQuick_Data.TransferQuick.COST_SUB[0]);

		log.info("TC_06_Step_: Check loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TYPE_TRANSFER), TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);

		log.info("TC_06_Step_: Check noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.CONTENT_TRANSFER).contains(TransferMoneyQuick_Data.TransferQuick.NOTE));

		log.info("TC_06_Step_: Click chi tiet giao dich");
		transReport.clickToDynamicBackIcon(driver, ReportTitle.DETAIL_TRANSFER);

		log.info("TC_06_Step_: Chon button back");
		transReport.clickToDynamicBackIcon(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_06_Step_Click button home");
		transReport.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@Test
	// Lỗi app, trường số tiền bị bỏ đơn vị EUR
	public void TC_09_ChuyenTienQuaTaiKhoanNguoiChuyenTraPhiEURXacThucMatKhau() {
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferQuick.TRANSFER_MONEY_LABEL);

		log.info("TC_07_Step_Select Chuyen tien nhanh qua tai khoan");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);

		log.info("TC_07_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, TransferQuick.ACCOUNT_FROM_LABEL);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.EUR_ACCOUNT);

		log.info("TC_07_Step_Get so du kha dung");
		amountStartString = transferMoney.getDynamicTextByLabel(driver, Tittle_Quick.AVAIBLE_BALANCES);
		double amountStart1 = convertMoneyToDouble(amountStartString, "EUR");

		log.info("TC_07_Step_Nhap so tai khoan chuyen");
		transferMoney.inputToDynamicInputBox(driver, accountRecived, Tittle_Quick.TYPE_SELECT_ACCOUNT);

		log.info("TC_07_Step_Select ngan hang");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Tittle_Quick.BANK_RECIVED);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, bankOut);

		log.info("TC_07_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_EUR, TransferQuick.MOUNT_LABEL);

		log.info("TC_07_Step_:Lay so tien quy doi ra VND");
		String exchange = transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.RATE_REFER).split(" ~ ", 2)[1];
		exchangeRate = exchange.replace(" VND", "");

		log.info("TC_07_Step_Chon phi giao dich la nguoi chuyen");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[0]);

		log.info("TC_07_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE, Tittle_Quick.TRANSACTION_INFO, "3");

		log.info("TC_07_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);

		log.info("TC_07_Step_Verify so tien chuyen");
		verifyTrue(transferMoney.getDynamicTextInTransactionDetail(driver, "Số tiền(EUR)").contains(addCommasToDouble(TransferMoneyQuick_Data.TransferQuick.MONEY_EUR) + " EUR"));

		log.info("TC_07_Step_Chon phuong thuc xac thuc");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[0]);

		log.info("TC_07_Step: Kiem tra so tien phi hien thi");
		String Fee = transferMoney.getDynamicTextInTransactionDetail(driver, TransferQuick.AMOUNT_FEE_LABEL).replaceAll("\\D+", "");

		log.info("TC_07_Step 23: So tien phi chuyen doi ra EUR");
		double EURTransferFee = convertMoneyToDouble(Fee, Constants.VND_CURRENCY) / convertMoneyToDouble(exchangeRate, Constants.VND_CURRENCY);

		log.info("TC_07_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);

		log.info("TC_02_Step_Nhap ma xac thuc");
		transferMoney.inputToDynamicPopupPasswordInput(driver, password, Tittle_Quick.CONTINUE_BUTTON);

		log.info("TC_07_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);

		log.info("TC_07_Verify message thanh cong");
		verifyEquals(transferMoney.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY1);

		log.info("TC_07_get thoi gian giao dich thanh cong");
		transferTime = transferMoney.getDynamicTransferTimeAndMoney(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY1, "4").split(" ")[3];

		log.info("TC_07_Step_: Get ma giao dich");
		transactionNumber = transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.CODE_TRANSFER);

		log.info("TC_07_Step_:Ten nguoi thu huong");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.NAME_RECIVED_TEXT), nameCustomer);

		log.info("TC_07_Step_: Tai khoan dich");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.ACCOUNT_RECIVED_TEXT), accountRecived);

		log.info("TC_07_Step_: Noi dung");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.CONTENT), TransferMoneyQuick_Data.TransferQuick.NOTE);

		log.info("TC_07_Step_: Thuc hien giao dich moi");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.NEW_TRANSFER);

		log.info("TC_07_Step_: Chon tai khoan chuyen den");
		transferMoney.clickToDynamicDropDown(driver, TransferQuick.ACCOUNT_FROM_LABEL);

		log.info("TC_07_Step_: Chon tai khoan chuyen den");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.EUR_ACCOUNT);

		log.info("TC_07: Lay so du kha dung tai khoan");
		String amountAfterString = transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.AVAIBLE_BALANCES);
		double amountAfter = convertMoneyToDouble(amountAfterString, "EUR");
		double amountExpect = Math.round((amountAfter) * 100) / 100;

		log.info("TC_07_Step: Convert so tien chuyen");
		double transferMoney = convertMoneyToDouble(TransferMoneyQuick_Data.TransferQuick.MONEY_EUR, " EUR");

		log.info("TC_07: Kiem tra so du tai khoan EUR sau khi chuyen tien");
		double amountAtual = Math.round((amountStart1 - transferMoney - EURTransferFee) * 100) / 100;
		verifyEquals(amountAtual, amountExpect);
	}

	@Test
	public void TC_10_BaoCaoChuyenTienQuaTaiKhoanNguoiChuyenTraPhiEURXacThucMatKhau() {
		homePage = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_08: Click back man hinh home");
		homePage.clickToDynamicBackIcon(driver, TransferQuick.TRANSFER_MONEY_LABEL);

		log.info("TC_08: Click menu header");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_08: Click bao cao giao dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_08: chon loai bao cao giao dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.ALL_TYPE_TRANSACTION);

		log.info("TC_08: Chon option chuyen tien nhanh qua tai khoan");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSFER_24_7_ACCOUNT);

		log.info("TC_08: Chon so tai khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_08: Chon so tai khoan chuyen");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.EUR_ACCOUNT);

		log.info("TC_08: verify thoi tim kiem tu ngay");
		String dateStartActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate");
		String dateStartExpect = getBackwardDate(6);
		verifyEquals(dateStartActual, dateStartExpect);

		log.info("TC_08: verify thoi tim kiem tu ngay");
		String dateEndActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate");
		String dateEndtExpect = getForwardDate(0);
		verifyEquals(dateEndActual, dateEndtExpect);

		log.info("TC_08: Tim kiem");
		transReport.clickToDynamicButton(driver, ReportTitle.SEARCH_BUTTON);

		log.info("TC_08: Lay ngay tao giao dich hien thi");
		String transferTimeInReport = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_02: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport), transferTime);

		log.info("TC_08: Check ghi chu");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyQuick_Data.TransferQuick.NOTE));

		log.info("TC_08: Check so tien chuyen");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + TransferMoneyQuick_Data.TransferQuick.MONEY_EUR + " EUR"));

		log.info("TC_08: Click chi tiet giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_02: Lay ngay tao giao dich hien thi");
		String transferTimeInReport1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_02: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport1), transferTime);

		log.info("TC_08: Check so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_NUMBER), transactionNumber);
		
		log.info("TC_10: Check loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TYPE_TRANSFER), TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);

		log.info("TC_08: Check tao khoan ghi no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_CARD), Account_Data.Valid_Account.LIST_ACCOUNT_FROM[2]);

		log.info("TC_08: Check tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_CREDITED), accountRecived);

		log.info("TC_08: Check so tien giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_MONEY).contains(addCommasToLong(TransferMoneyQuick_Data.TransferQuick.MONEY_EUR) + ".00 EUR"));

		log.info("TC_08: So tien quy doi expect");
		double amountAfter = convertMoneyToDouble(TransferMoneyQuick_Data.TransferQuick.MONEY_EUR, "EUR");
		double amountExpect = amountAfter * convertMoneyToDouble(exchangeRate, Constants.VND_CURRENCY);

		log.info("TC_08: So tien quy doi atual");
		String amountAfter1 = transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_MONEY).replaceAll(".00 EUR", "");
		double amountActual = convertMoneyToDouble(amountAfter1, Constants.VND_CURRENCY) * convertMoneyToDouble(exchangeRate, Constants.VND_CURRENCY);

		log.info("TC_08: verify so tien quy doi");
		verifyEquals(amountActual, amountExpect);

		log.info("TC_08: Check so nguoi huong");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Tittle_Quick.NAME_RECIVED), nameCustomer);

		log.info("TC_08: Check ngan hang huong");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Tittle_Quick.BANK_RECIVED), bankOut);

		log.info("TC_08: Check phi giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.FEE), TransferMoneyQuick_Data.TransferQuick.COST_SUB[0]);

		log.info("TC_08: Check so tien phi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_FEE).replaceAll("\\D+", ""), fee + "");

		log.info("TC_08: Check loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TYPE_TRANSFER), TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);

		log.info("TC_08: Check noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.CONTENT_TRANSFER).contains(TransferMoneyQuick_Data.TransferQuick.NOTE));

		log.info("TC_08: Chon button back");
		transferMoney.clickToDynamicBackIcon(driver, ReportTitle.DETAIL_TRANSFER);

		log.info("TC_08: Chon button back");
		transferMoney.clickToDynamicBackIcon(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_08_Step_Click button home");
		transferMoney.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();
	}

}