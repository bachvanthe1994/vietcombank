package vnpay.vietcombank.transfer_money_quick_247;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

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

public class Flow_QuickMoneyTransfer247_Part3_Smart_OTP extends Base {
	AppiumDriver<MobileElement> driver;
	private TransactionReportPageObject transReport;
	private String transferTime;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferMoneyObject transferMoney;
	private String transactionNumber;
	private long amountStart;
	private String amountTranferString;
	private String amountStartString;
	private long amountTranfer;
	private String costTranferString;
	private long costTranfer;
	private String cardNumber, cardTo, fee = "";
	private String bankOut, accountRecived;
	private SettingVCBSmartOTPPageObject smartOTP;

	SourceAccountModel sourceAccount = new SourceAccountModel();
	List<String> listCard = new ArrayList<String>();
	String otpSmart, newOTP;

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
		smartOTP = PageFactoryManager.getSettingVCBSmartOTPPageObject(driver);
		login.Global_login(phone, pass, opt);
		transferMoney = PageFactoryManager.getTransferMoneyObject(driver);
		otpSmart = getDataInCell(6);
		newOTP = "111222";
		smartOTP = PageFactoryManager.getSettingVCBSmartOTPPageObject(driver);
		smartOTP.setupSmartOTP(LogIn_Data.Login_Account.Smart_OTP, otpSmart);
	}

	@Parameters({ "otp" })
	@Test
	public void TC_01_ChuyenTienQuaSoTheCoPhiGiaoDichNguoiNhanTraVNDVaXacThucBangOTP(String otp) {
		log.info("TC_01_Step_Click Chuyen tien nhanh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferQuick.TRANSFER_MONEY_LABEL);

		log.info("TC_01_Step_Select Chuyen tien nhanh qua the");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[1]);

		log.info("TC_01_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, Tittle_Quick.CARD_TRIP);
		listCard = transferMoney.getListCard();
		transferMoney.scrollUpToText(driver, listCard.get(0));

		for (String card : listCard) {
			transferMoney.scrollDownToText(driver, card);
			log.info("TC_01_Step_06: Chon tai khoan the tin dung:");
			transferMoney.clickToDynamicButtonLinkOrLinkText(driver, card);
			if (transferMoney.getPageSource(driver).contains(Tittle_Quick.UNSATISFACTORY)) {
				log.info("TC_01_Step_07: Click btn Dong y:");
				transferMoney.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
				log.info("TC_01_Step_08: Click chon so tai khoan the :");
				transferMoney.clickToDynamicLinerLayoutID(driver, "com.VCB:id/llContent1");
				continue;
			} else {
				cardNumber = transferMoney.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContentCard");
				sourceAccount.account = transferMoney.getDynamicTextByLabel(driver, TransferQuick.ACCOUNT_FROM_LABEL);
				sourceAccount.currentcy = transferMoney.getDynamicTextByLabel(driver, Tittle_Quick.AVAIBLE_BALANCES);
				sourceAccount.currentcy = sourceAccount.currentcy.split(" ")[1];
				sourceAccount.balance = transferMoney.getDynamicTextByLabel(driver, Tittle_Quick.AVAIBLE_BALANCES);
				if (!sourceAccount.currentcy.equals(Constants.VND_CURRENCY) || convertAvailableBalanceCurrentcyOrFeeToLong(sourceAccount.balance) < Long.valueOf(Constants.MONEY_CHECK_VND)) {
					transferMoney.clickToDynamicDropDown(driver, Tittle_Quick.CARD_TRIP);
					transferMoney.scrollUpToText(driver, listCard.get(0));
					continue;
				} else {
					break;
				}
			}

		}

		log.info("TC_01_Step_Doi kieu du lieu");
		amountStart = convertAvailableBalanceCurrentcyOrFeeToLong(sourceAccount.balance);

		log.info("TC_01_Step_Nhap so tai khoan chuyen");
		transferMoney.inputToDynamicInputBox(driver, cardTo, Tittle_Quick.TYPE_SELECT_CARD_NUMBER);

		log.info("TC_01_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.MONEY, TransferQuick.MOUNT_LABEL);

		log.info("TC_01_Step_Chon phi giao dich la nguoi nhan tra");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[1]);

		log.info("TC_01_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE, Tittle_Quick.TRANSACTION_INFO, "3");

		log.info("TC_01_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);

		log.info("TC_01_Step_Chon phuong thuc xac thuc");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[1]);

		log.info("TC_01_Step_Lay gia tri so tien chuyen");
		amountTranferString = transferMoney.getDynamicTextByLabel(driver, TransferQuick.MOUNT_LABEL).replaceAll("\\D+", "");

		log.info("TC_01_Step_Verify so tien chuyen");
		verifyEquals(amountTranferString, TransferMoneyQuick_Data.TransferQuick.MONEY);

		log.info("TC_01_Step_doi kieu du lieu string -> long");
		amountTranfer = Long.parseLong(amountTranferString);
		transferMoney.clickToDynamicDropDown(driver, Tittle_Quick.METHOD_VALIDATE);

		log.info("TC_03_Step_21: Chon Smart OTP");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferQuick.ACCURACY[2]);

		log.info("TC_01_Step_Lay gia tri so tien phí chuyen");
		costTranferString = transferMoney.getDynamicTextByLabel(driver, TransferQuick.AMOUNT_FEE_LABEL).replaceAll("\\D+", "");

		log.info("TC_01_Step_doi kieu du lieu string -> long");
		costTranfer = Long.parseLong(costTranferString);

		log.info("TC_01_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);

		log.info("TC_03_Step_23: Nhap OTP");
		transferMoney.inputToDynamicSmartOTP(driver, newOTP, "com.VCB:id/otp");

		log.info("TC_01_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);
		log.info("TC_01_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);

		log.info("TC_01_Verify message thanh cong");
		verifyEquals(transferMoney.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY1);

		log.info("TC_01_get thoi gian giao dich thanh cong");
		transferTime = transferMoney.getDynamicTransferTimeAndMoney(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY1, "4").split(" ")[3];

		log.info("TC_01_Step_: Get ma giao dich");
		transactionNumber = transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.CODE_TRANSFER);

		log.info("TC_01_Step_:Ten nguoi thu huong");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.NAME_RECIVED_TEXT), TransferMoneyQuick_Data.TransferQuick.RECEIVER_NAME);

		log.info("TC_01_Step_: Tai khoan dich");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.CAR_NUMBER), cardTo);

		log.info("TC_01_Step_: Noi dung");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.CONTENT), TransferMoneyQuick_Data.TransferQuick.NOTE);

		log.info("TC_01_Step_: Thuc hien giao dich moi");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.NEW_TRANSFER);

		log.info("TC_01_Step_Select Chuyen tien nhanh qua the");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[1]);

		log.info("TC_01_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, Tittle_Quick.CARD_TRIP);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, cardNumber);

		log.info("TC_01_Step_:Check so du kha dung sau khi chuyen tien");
		String amountAfterString = transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.AVAIBLE_BALANCES).replaceAll("\\D+", "");

		long amountAfter = Long.parseLong(amountAfterString);

		log.info("TC_01_Step_:Check so du kha dung sau khi chuyen tien");
		System.out.print("amountStart =" + amountStart + "-------amountTranfer =" + amountTranfer + "-------costTranfer =" + costTranfer);
		verifyEquals(amountStart - amountTranfer, amountAfter);
	}

	@Test
	public void TC_02_ReportChuyenTienQuaSoTheCoPhiGiaoDichNguoiNhanTraVNDVaXacThucBangOTP() {
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

		log.info("TC_02: Chon option chuyen tien nhanh qua tai khoan");

		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSFER_24_7_CARD);

		log.info("TC_02: Chon so tai khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_02: Chon so tai khoan tra cuu");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_02: verify thoi tim kiem tu ngay");
		String dateStartActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate");
		String dateStartExpect = getBackwardDate(7);
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

		log.info("TC_02: Check so tien chuyen");// lỗi app
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(TransferMoneyQuick_Data.TransferQuick.MONEY) + " VND"));

		log.info("TC_02: Click chi tiet giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_02: Lay ngay tao giao dich hien thi");
		String transferTimeInReport1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_02: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport1), transferTime);

		log.info("TC_02: Check so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_NUMBER), transactionNumber);

		log.info("TC_02: So the chuyen di");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Tittle_Quick.CARD_TRIP), Account_Data.Valid_Account.LIST_CARD_FROM[0]);

		log.info("TC_02: Check tao khoan ghi no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_CARD_SOURCE), sourceAccount.account);

		log.info("TC_02: Check tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.NUMBER_CARD_VND), cardTo);

		log.info("TC_02: Check so nguoi huong");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Tittle_Quick.NAME_RECIVED), TransferMoneyQuick_Data.TransferQuick.RECEIVER_NAME);

		log.info("TC_02: Check so tien giao dich");// Lỗi app
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_MONEY).contains(addCommasToLong(TransferMoneyQuick_Data.TransferQuick.MONEY) + " VND"));

		log.info("TC_02_Step_Lay gia tri so tien phí chuyen");
		costTranferString = transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_FEE).replaceAll("\\D+", "");

		log.info("TC_02_Step_Verify so tien phi");
		verifyEquals(costTranferString, fee + "");

		log.info("TC_02: Check noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.CONTENT_TRANSFER).contains(TransferMoneyQuick_Data.TransferQuick.NOTE));

		log.info("TC_02: click button back");
		transReport.clickToDynamicBackIcon(driver, ReportTitle.DETAIL_TRANSFER);

		log.info("TC_02: Chon button back");
		transferMoney.clickToDynamicBackIcon(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_02_Step_Click button home");
		transferMoney.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@Test
	public void TC_03_ChuyenTienNguoiChuyenTraPhiVNDOTP() throws InterruptedException {
		log.info("TC_03_Step_Click Chuyen tien nhanh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferQuick.TRANSFER_MONEY_LABEL);

		log.info("TC_03_Step_Select Chuyen tien nhanh qua tai khoan");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);

		log.info("TC_03_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, TransferQuick.ACCOUNT_FROM_LABEL);
		sourceAccount = transferMoney.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		log.info("TC_03_Step_Get so du kha dung");
		amountStartString = transferMoney.getDynamicTextByLabel(driver, Tittle_Quick.AVAIBLE_BALANCES).replaceAll("\\D+", "");

		log.info("TC_03_Step_Doi kieu du lieu");
		amountStart = Long.parseLong(amountStartString);

		log.info("TC_03_Step_Nhap so tai khoan chuyen");
		transferMoney.inputToDynamicInputBox(driver, accountRecived, Tittle_Quick.TYPE_SELECT_ACCOUNT);

		log.info("TC_03_Step_Select ngan hang");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Tittle_Quick.BANK_RECIVED);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, bankOut);

		log.info("TC_03_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.MONEY, TransferQuick.MOUNT_LABEL);

		log.info("TC_03_Step_Chon phi giao dich la nguoi chuyen tra");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[0]);

		log.info("TC_03_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE, Tittle_Quick.TRANSACTION_INFO, "3");

		log.info("TC_03_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);

		log.info("TC_03_Step_23: Nhap OTP");
		transferMoney.inputToDynamicSmartOTP(driver, newOTP, "com.VCB:id/otp");

		log.info("TC_03_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);
		log.info("TC_03_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);

		log.info("TC_03_Step_Lay gia tri so tien chuyen");
		amountTranferString = transferMoney.getDynamicTextByLabel(driver, TransferQuick.MOUNT_LABEL).replaceAll("\\D+", "");

		log.info("TC_03_Step_Verify so tien chuyen");
		verifyEquals(amountTranferString, TransferMoneyQuick_Data.TransferQuick.MONEY);

		log.info("TC_03_Step_doi kieu du lieu string -> long");
		amountTranfer = Long.parseLong(amountTranferString);

		log.info("TC_03_Step_Chon phuong thuc xac thuc");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[2]);

		log.info("TC_03_Step_Lay gia tri so tien phí chuyen");
		costTranferString = transferMoney.getDynamicTextByLabel(driver, TransferQuick.AMOUNT_FEE_LABEL).replaceAll("\\D+", "");

		log.info("TC_03_Step_doi kieu du lieu string -> long");
		costTranfer = Long.parseLong(costTranferString);

		log.info("TC_03_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);

		log.info("TC_03_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);

		log.info("TC_03_Verify message thanh cong");
		verifyEquals(transferMoney.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY1);

		log.info("TC_03_get thoi gian giao dich thanh cong");
		transferTime = transferMoney.getDynamicTransferTimeAndMoney(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY1, "4").split(" ")[3];

		log.info("TC_03_Step_: Get ma giao dich");
		transactionNumber = transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.CODE_TRANSFER);

		log.info("TC_03_Step_:Ten nguoi thu huong");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.NAME_RECIVED_TEXT), TransferMoneyQuick_Data.TransferQuick.RECEIVER_NAME);

		log.info("TC_03_Step_: Tai khoan dich");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.ACCOUNT_RECIVED_TEXT), accountRecived);

		log.info("TC_03_Step_: ngan hang thu huong");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.BANK_RECIVED), bankOut);

		log.info("TC_03_Step_: Noi dung");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.CONTENT), TransferMoneyQuick_Data.TransferQuick.NOTE);

		log.info("TC_03_Step_: Thuc hien giao dich moi");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.NEW_TRANSFER);

		log.info("TC_03_Step_:Tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, TransferQuick.ACCOUNT_FROM_LABEL);

		log.info("TC_03_Step_: Tai khoan chuyen");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_03_Step_:Check so du kha dung sau khi chuyen tien");
		String amountAfterString = transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.AVAIBLE_BALANCES).replaceAll("\\D+", "");

		long amountAfter = Long.parseLong(amountAfterString);

		log.info("TC_03_Step_:Check so du kha dung sau khi chuyen tien");
		verifyEquals(amountStart - amountTranfer - costTranfer, amountAfter);
	}

	@Test
	public void TC_04_BaoCaoChuyenTienNguoiChuyenTraPhiVNDOTP() {
		homePage = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_04: Click back man hinh home");
		homePage.clickToDynamicBackIcon(driver, TransferQuick.TRANSFER_MONEY_LABEL);

		log.info("TC_04: Click menu header");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_04: Click bao cao giao dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_04: chon loai bao cao giao dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.ALL_TYPE_TRANSACTION);

		log.info("TC_Step_: Chon option chuyen tien nhanh qua tai khoan");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSFER_24_7_ACCOUNT);

		log.info("TC_04: Chon so tai khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_04: Chon so tai khoan tra cuu");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_04: verify thoi tim kiem tu ngay");
		String dateStartActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate");
		String dateStartExpect = getBackwardDate(6);
		verifyEquals(dateStartActual, dateStartExpect);

		log.info("TC_04: verify thoi tim kiem tu ngay");
		String dateEndActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate");
		String dateEndtExpect = getForwardDate(0);
		verifyEquals(dateEndActual, dateEndtExpect);

		log.info("TC_04: Tim kiem");
		transReport.clickToDynamicButton(driver, ReportTitle.SEARCH_BUTTON);

		log.info("TC_04_: Lay ngay tao giao dich hien thi");
		String transferTimeInReport = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_04: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport), transferTime);

		log.info("TC_04: Check ghi chu");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyQuick_Data.TransferQuick.NOTE));

		log.info("TC_04: Check so tien chuyen");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(TransferMoneyQuick_Data.TransferQuick.MONEY) + " VND"));

		log.info("TC_04: Click chi tiet giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_04: Lay ngay tao giao dich hien thi");
		String transferTimeInReport1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_04: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport1), transferTime);

		log.info("TC_04: Check so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_NUMBER), transactionNumber);

		log.info("TC_04: Check tao khoan ghi no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_CARD_SOURCE), sourceAccount.account);

		log.info("TC_04: Check tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_CREDITED), accountRecived);

		log.info("TC_04: Check so tien giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_MONEY).contains(addCommasToLong(TransferMoneyQuick_Data.TransferQuick.MONEY) + " VND"));

		log.info("TC_04: Check so nguoi huong");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Tittle_Quick.NAME_RECIVED), TransferMoneyQuick_Data.TransferQuick.RECEIVER_NAME);

		log.info("TC_04: Check ngan hang huong");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Tittle_Quick.BANK_RECIVED), bankOut);

		log.info("TC_04: Check phi giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.FEE), TransferMoneyQuick_Data.TransferQuick.COST_SUB[0]);

		log.info("TC_04: Check loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TYPE_TRANSFER), TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);

		log.info("TC_04: Check noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.CONTENT_TRANSFER).contains(TransferMoneyQuick_Data.TransferQuick.NOTE));

		log.info("TC_04: Chick chi tiet giao dich");
		transReport.clickToDynamicBackIcon(driver, ReportTitle.DETAIL_TRANSFER);

		log.info("TTC_04: Chon button back");
		transferMoney.clickToDynamicBackIcon(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_04_Click button home");
		transferMoney.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();
	}

}