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
	private long amountStart;
	private String amountTranferString;
	private long amountTranfer;
	private String costTranferString, cardTo;
	private long costTranfer;
	private String exchangeRate;
	private String password, cardNumber = "";
	List<String> listCard = new ArrayList<String>();
	SourceAccountModel sourceAccount = new SourceAccountModel();
	String otpSmart, newOTP, nameCustomer;
	private SettingVCBSmartOTPPageObject smartOTP;

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "pass", "otp", "phone" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String pass, String opt, String phone) throws IOException, InterruptedException, GeneralSecurityException {
		startServer();
		log.info("Before class: Mo app ");
		if (deviceType.contains("android")) {
			driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		} else if (deviceType.contains("")) {
			driver = openIOSApp(deviceName, udid, url);
		}
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(getDataInCell(45), getDataInCell(46), opt);
		password = pass;
		transferMoney = PageFactoryManager.getTransferMoneyObject(driver);
		cardTo = getDataInCell(20);
		otpSmart = getDataInCell(6);
		nameCustomer = getDataInCell(40);
		newOTP = "111222";
		smartOTP = PageFactoryManager.getSettingVCBSmartOTPPageObject(driver);
		smartOTP.setupSmartOTP(newOTP, otpSmart);

		log.info("TC_00_Step_Click Chuyen tien nhanh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferQuick.TRANSFER_MONEY_LABEL);
	}

	@Parameters({ "otp" })
	@Test(invocationCount = 2)
	public void TC_01_ChuyenTienQuaSoTheCoPhiGiaoDichNguoiNhanTraVNDVaXacThucBangOTP(String otp) {

		log.info("TC_00_Step_Select Chuyen tien nhanh qua the");
		transferMoney.clickToTextID(driver, "com.VCB:id/tvContent");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[1]);

		log.info("TC_00_Step_Select tai khoan nguon");
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
				if (sourceAccount.currentcy.length() > 3) {
					sourceAccount.currentcy = sourceAccount.currentcy.split(" ")[1];
					sourceAccount.balance = transferMoney.getDynamicTextByLabel(driver, Tittle_Quick.AVAIBLE_BALANCES);
					if (!sourceAccount.currentcy.equals(Constants.VND_CURRENCY) || convertAvailableBalanceCurrentcyOrFeeToLong(sourceAccount.balance) < Long.valueOf(Constants.MONEY_CHECK_VND)) {
						transferMoney.clickToDynamicDropDown(driver, Tittle_Quick.CARD_TRIP);
						transferMoney.scrollUpToText(driver, listCard.get(0));
						continue;
					} else {
						break;
					}
				} else {
					break;
				}

			}
		}

		log.info("TC_00_Step_Doi kieu du lieu");
		amountStart = convertAvailableBalanceCurrentcyOrFeeToLong(sourceAccount.balance);

		log.info("TC_00_Step_Nhap so tai khoan chuyen");
		transferMoney.inputToDynamicInputBox(driver, cardTo, Tittle_Quick.TYPE_SELECT_CARD_NUMBER);

		log.info("TC_00_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.MONEY, TransferQuick.MOUNT_LABEL);

		log.info("TC_00_Step_Chon phi giao dich la nguoi nhan tra");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[1]);

		log.info("TC_00_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE, Tittle_Quick.TRANSACTION_INFO, "3");

		log.info("TC_00_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);

		log.info("TC_00_Step_Chon phuong thuc xac thuc");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[1]);

		log.info("TC_00_Step_Lay gia tri so tien chuyen");
		amountTranferString = transferMoney.getDynamicTextByLabel(driver, TransferQuick.MOUNT_LABEL).replaceAll("\\D+", "");

		log.info("TC_00_Step_Verify so tien chuyen");
		verifyEquals(amountTranferString, TransferMoneyQuick_Data.TransferQuick.MONEY);

		log.info("TC_00_Step_doi kieu du lieu string -> long");
		amountTranfer = Long.parseLong(amountTranferString);

		log.info("TC_00_Step_Lay gia tri so tien phí chuyen");
		costTranferString = transferMoney.getDynamicTextByLabel(driver, TransferQuick.AMOUNT_FEE_LABEL).replaceAll("\\D+", "");

		log.info("TC_00_Step_doi kieu du lieu string -> long");
		costTranfer = Long.parseLong(costTranferString);

		log.info("TC_00_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);

		log.info("TC_00_Step_Nhap ma xac thuc");
		transferMoney.inputToDynamicOtp(driver, otp, Tittle_Quick.CONTINUE_BUTTON);

		log.info("TC_00_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);

		log.info("TC_00_Verify message thanh cong");
		verifyEquals(transferMoney.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY1);

		log.info("TC_00_get thoi gian giao dich thanh cong");
		transferTime = transferMoney.getDynamicTransferTimeAndMoney(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY1, "4").split(" ")[3];

		log.info("TC_00_Step_: Get ma giao dich");
		transactionNumber = transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.CODE_TRANSFER);

		log.info("TC_00_Step_:Ten nguoi thu huong");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.NAME_RECIVED_TEXT), nameCustomer);

		log.info("TC_00_Step_: Tai khoan dich");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.CAR_NUMBER), cardTo);

		log.info("TC_00_Step_: Noi dung");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.CONTENT), TransferMoneyQuick_Data.TransferQuick.NOTE);

		log.info("TC_00_Step_: Thuc hien giao dich moi");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.NEW_TRANSFER);

		log.info("TC_00_Step_Select Chuyen tien nhanh qua the");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[1]);

		log.info("TC_00_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, Tittle_Quick.CARD_TRIP);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, cardNumber);

		log.info("TC_00_Step_:Check so du kha dung sau khi chuyen tien");
		String amountAfterString = transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.AVAIBLE_BALANCES).replaceAll("\\D+", "");

		long amountAfter = Long.parseLong(amountAfterString);

		log.info("TC_00_Step_:Check so du kha dung sau khi chuyen tien");
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
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Tittle_Quick.CARD_TRIP), cardNumber);

		log.info("TC_02: Check tao khoan ghi no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_CARD), sourceAccount.account);

		log.info("TC_02: Check tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.NUMBER_CARD_VND), cardTo);

		log.info("TC_02: Check loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TYPE_TRANSFER), TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[1]);

		log.info("TC_02: Check so nguoi huong");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Tittle_Quick.NAME_RECIVED), nameCustomer);

		log.info("TC_02: Check so tien giao dich");// Lỗi app
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_MONEY).contains(addCommasToLong(TransferMoneyQuick_Data.TransferQuick.MONEY) + " VND"));

		log.info("TC_02_Step_Lay gia tri so tien phí chuyen");
		costTranferString = transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_FEE).replaceAll("\\D+", "");

		log.info("TC_02_Step_Verify so tien phi");
		verifyEquals(costTranferString, costTranfer + "");

		log.info("TC_02: Check noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.CONTENT_TRANSFER).contains(TransferMoneyQuick_Data.TransferQuick.NOTE));

		log.info("TC_02: click button back");
		transReport.clickToDynamicBackIcon(driver, ReportTitle.DETAIL_TRANSFER);

		log.info("TC_02: Chon button back");
		transferMoney.clickToDynamicBackIcon(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_02_Step_Click button home");
		transferMoney.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@Parameters({ "otp" })
	@Test
//Lỗi app, số tiền bị thiếu đơn vị USD
	public void TC_05_ChuyenTienQuaSoTheCoPhiGiaoDichNguoiNhanTraUSDVaXacThucBangOTP(String otp) {
		log.info("TC_03_Step_Click Chuyen tien nhanh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferQuick.TRANSFER_MONEY_LABEL);

		log.info("TC_03_Step_Select Chuyen tien nhanh qua the");
		transferMoney.clickToTextID(driver, "com.VCB:id/tvContent");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[1]);

		log.info("TC_09_Step_Select tai khoan nguon");
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
				if (!sourceAccount.currentcy.equals(Constants.USD_CURRENCY) || convertAvailableBalanceCurrentcyToDouble(sourceAccount.balance) < Double.valueOf(Constants.MONEY_CHECK_USD)) {
					transferMoney.clickToDynamicDropDown(driver, Tittle_Quick.CARD_TRIP);
					transferMoney.scrollUpToText(driver, listCard.get(0));
					continue;
				} else {
					break;
				}
			}

		}

		log.info("TC_03_Step_Nhap so tai khoan chuyen");
		transferMoney.inputToDynamicInputBox(driver, cardTo, Tittle_Quick.TYPE_SELECT_CARD_NUMBER);

		log.info("TC_03_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_USD, TransferQuick.MOUNT_LABEL);

		log.info("TC_03_Step_:Lay so tien quy doi ra VND");
		String exchange = transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.RATE_REFER).split(" ~ ", 2)[1];
		exchangeRate = exchange.replace(" VND", "");

		log.info("TC_03_Step_Chon phi giao dich la nguoi nhan tra");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[1]);

		log.info("TC_03_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE, Tittle_Quick.TRANSACTION_INFO, "3");

		log.info("TC_03_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);

		log.info("TC_03_Step_Lay gia tri so tien chuyen");
		amountTranferString = transferMoney.getDynamicTextByLabel(driver, TransferQuick.MOUNT_LABEL).replaceAll(".00 USD", "");

		log.info("TC_03_Step_Verify so tien chuyen");
		verifyEquals(amountTranferString, TransferMoneyQuick_Data.TransferQuick.MONEY_USD);

		log.info("TC_03_Step_doi kieu du lieu string -> long");
		amountTranfer = Long.parseLong(amountTranferString);

		log.info("TC_03_Step_Chon phuong thuc xac thuc");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[1]);

		log.info("TC_09_Step_Lay gia tri so tien phí chuyen");
		costTranferString = transferMoney.getDynamicTextByLabel(driver, TransferQuick.AMOUNT_FEE_LABEL).replaceAll("\\D+", "");

		log.info("TC_09_Step_doi kieu du lieu string -> long");
		costTranfer = Long.parseLong(costTranferString);

		log.info("TC_03_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);

		log.info("TC_03_Step_Nhap ma xac thuc");
		transferMoney.inputToDynamicOtp(driver, otp, Tittle_Quick.CONTINUE_BUTTON);

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
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.CAR_NUMBER), cardTo);

		log.info("TC_03_Step_: Noi dung");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.CONTENT), TransferMoneyQuick_Data.TransferQuick.NOTE);

		log.info("TC_03_Step_: Thuc hien giao dich moi");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.NEW_TRANSFER);

		log.info("TC_03_Step_Select Chuyen tien nhanh qua the");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[1]);

		log.info("TC_03_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, Tittle_Quick.CARD_TRIP);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, cardNumber);

		log.info("TC_03: Lay so du kha dung the");
		String amountAfterString = transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.AVAIBLE_BALANCES);
		double amountAfter = convertMoneyToDouble(amountAfterString, Constants.USD_CURRENCY);
		double amountExpect = Math.round((amountAfter) * 100) / 100;

		log.info("TC_03: Convert so tien chuyen");
		double transferMoney = convertMoneyToDouble(TransferMoneyQuick_Data.TransferQuick.MONEY_USD, " USD");

		log.info("TC_03: Kiem tra so du tai khoan USD sau khi chuyen tien");
		double amountAtual = Math.round((amountStart - transferMoney) * 100) / 100;
		verifyEquals(amountAtual, amountExpect);
	}

	@Test
	public void TC_06_ReportChuyenTienQuaSoTheCoPhiGiaoDichNguoiNhanTraUSDVaXacThucBangOTP() {
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

		log.info("TC_04: Chon option chuyen tien nhanh qua tai khoan");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSFER_24_7_CARD);

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

		log.info("TC_02_: Lay ngay tao giao dich hien thi");
		String transferTimeInReport = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_02: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport), transferTime);

		log.info("TC_04: Check ghi chu");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyQuick_Data.TransferQuick.NOTE));

		log.info("TC_04: Check so tien chuyen");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(TransferMoneyQuick_Data.TransferQuick.MONEY_USD) + " USD"));

		log.info("TC_04: Click chi tiet giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_04: Lay ngay tao giao dich hien thi");
		String transferTimeInReport1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_04: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport1), transferTime);

		log.info("TC_04: Check so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_NUMBER), transactionNumber);

		log.info("TC_04: So the chuyen di");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Tittle_Quick.CARD_TRIP), cardNumber);

		log.info("TC_04: Check tao khoan ghi no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_CARD), sourceAccount.account);

		log.info("TC_04: Check tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.NUMBER_CARD_VND), cardTo);

		log.info("TC_04: Check ten nguoi huong");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Tittle_Quick.NAME_RECIVED), nameCustomer);

		log.info("TC_10: Check loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TYPE_TRANSFER), TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[1]);

		log.info("TC_04: Check so tien giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_MONEY), TransferMoneyQuick_Data.TransferQuick.MONEY_USD + ".00 USD");

		log.info("TC_04: So tien quy doi expect");
		double amountExpect = convertMoneyToDouble(TransferMoneyQuick_Data.TransferQuick.MONEY_USD, Constants.USD_CURRENCY) * convertMoneyToDouble(exchangeRate, Constants.VND_CURRENCY);

		log.info("TC_04: So tien quy doi atual");
		String amountAfter1 = transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_MONEY).replaceAll(".00 USD", "");
		double amountActual = convertMoneyToDouble(amountAfter1, Constants.VND_CURRENCY) * convertMoneyToDouble(exchangeRate, Constants.VND_CURRENCY);

		log.info("TC_04: verify so tien quy doi");
		verifyEquals(amountActual, amountExpect);

		log.info("TC_04_Step_Lay gia tri so tien phí chuyen");
		costTranferString = transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_FEE).replaceAll("\\D+", "");

		log.info("TC_04_Step_Verify so tien phi");
		verifyEquals(costTranferString, costTranfer + "");

		log.info("TC_04: Check noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.CONTENT_TRANSFER).contains(TransferMoneyQuick_Data.TransferQuick.NOTE));

		log.info("TC_04: click button back");
		transReport.clickToDynamicBackIcon(driver, ReportTitle.DETAIL_TRANSFER);

		log.info("TC_04: Chon button back");
		transferMoney.clickToDynamicBackIcon(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_04_Step_Click button home");
		transferMoney.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@Test
	public void TC_03_ChuyenTienQuaSoTheCoPhiGiaoDichNguoiNhanTraVND_SmartOTP() {
		log.info("TC_05_Step_Click Chuyen tien nhanh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferQuick.TRANSFER_MONEY_LABEL);

		log.info("TC_05_Step_Select Chuyen tien nhanh qua the");
		transferMoney.clickToTextID(driver, "com.VCB:id/tvContent");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[1]);

		log.info("TC_05_Step_Select tai khoan nguon");
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
				if (sourceAccount.currentcy.length() > 3) {
					sourceAccount.currentcy = sourceAccount.currentcy.split(" ")[1];
					sourceAccount.balance = transferMoney.getDynamicTextByLabel(driver, Tittle_Quick.AVAIBLE_BALANCES);
					if (!sourceAccount.currentcy.equals(Constants.VND_CURRENCY) || convertAvailableBalanceCurrentcyOrFeeToLong(sourceAccount.balance) < Long.valueOf(Constants.MONEY_CHECK_VND)) {
						transferMoney.clickToDynamicDropDown(driver, Tittle_Quick.CARD_TRIP);
						transferMoney.scrollUpToText(driver, listCard.get(0));
						continue;
					} else {
						break;
					}
				} else {
					break;
				}

			}
		}

		log.info("TC_05_Step_Doi kieu du lieu");
		amountStart = convertAvailableBalanceCurrentcyOrFeeToLong(sourceAccount.balance);

		log.info("TC_05_Step_Nhap so tai khoan chuyen");
		transferMoney.inputToDynamicInputBox(driver, cardTo, Tittle_Quick.TYPE_SELECT_CARD_NUMBER);

		log.info("TC_05_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.MONEY, TransferQuick.MOUNT_LABEL);

		log.info("TC_05_Step_Chon phi giao dich la nguoi nhan tra");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[1]);

		log.info("TC_05_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE, Tittle_Quick.TRANSACTION_INFO, "3");

		log.info("TC_05_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);

		log.info("TC_05_Step_Chon phuong thuc xac thuc");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[2]);

		log.info("TC_05_Step_Lay gia tri so tien chuyen");
		amountTranferString = transferMoney.getDynamicTextByLabel(driver, TransferQuick.MOUNT_LABEL).replaceAll("\\D+", "");

		log.info("TC_05_Step_Verify so tien chuyen");
		verifyEquals(amountTranferString, TransferMoneyQuick_Data.TransferQuick.MONEY);

		log.info("TC_05_Step_doi kieu du lieu string -> long");
		amountTranfer = Long.parseLong(amountTranferString);

		log.info("TC_05_Step_Lay gia tri so tien phí chuyen");
		costTranferString = transferMoney.getDynamicTextByLabel(driver, TransferQuick.AMOUNT_FEE_LABEL).replaceAll("\\D+", "");

		log.info("TC_05_Step_doi kieu du lieu string -> long");
		costTranfer = Long.parseLong(costTranferString);

		log.info("TC_05_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);

		log.info("TC_05_Step_Nhap ma xac thuc");
		transferMoney.inputToDynamicSmartOTP(driver, newOTP, "com.VCB:id/otp");

		log.info("TC_05_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);

		log.info("TC_05_Verify message thanh cong");
		verifyEquals(transferMoney.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY1);

		log.info("TC_05_get thoi gian giao dich thanh cong");
		transferTime = transferMoney.getDynamicTransferTimeAndMoney(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY1, "4").split(" ")[3];

		log.info("TC_05_Step_: Get ma giao dich");
		transactionNumber = transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.CODE_TRANSFER);

		log.info("TC_05_Step_:Ten nguoi thu huong");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.NAME_RECIVED_TEXT), nameCustomer);

		log.info("TC_05_Step_: Tai khoan dich");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.CAR_NUMBER), cardTo);

		log.info("TC_05_Step_: Noi dung");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.CONTENT), TransferMoneyQuick_Data.TransferQuick.NOTE);

		log.info("TC_05_Step_: Thuc hien giao dich moi");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.NEW_TRANSFER);

		log.info("TC_05_Step_Select Chuyen tien nhanh qua the");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[1]);

		log.info("TC_05_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, Tittle_Quick.CARD_TRIP);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, cardNumber);

		log.info("TC_05_Step_:Check so du kha dung sau khi chuyen tien");
		String amountAfterString = transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.AVAIBLE_BALANCES).replaceAll("\\D+", "");

		long amountAfter = Long.parseLong(amountAfterString);

		log.info("TC_05_Step_:Check so du kha dung sau khi chuyen tien");
		verifyEquals(amountStart - amountTranfer, amountAfter);
	}

	@Test
	public void TC_04_ReportChuyenTienQuaSoTheCoPhiGiaoDichNguoiNhanTraVND_SmartOTP() {
		homePage = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_06: Click back man hinh home");
		homePage.clickToDynamicBackIcon(driver, TransferQuick.TRANSFER_MONEY_LABEL);

		log.info("TC_06: Click menu header");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_06: Click bao cao giao dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_06: chon loai bao cao giao dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.ALL_TYPE_TRANSACTION);

		log.info("TC_06: Chon option chuyen tien nhanh qua tai khoan");

		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSFER_24_7_CARD);

		log.info("TC_06: Chon so tai khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_06: Chon so tai khoan tra cuu");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_06: verify thoi tim kiem tu ngay");
		String dateStartActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate");
		String dateStartExpect = getBackwardDate(6);
		verifyEquals(dateStartActual, dateStartExpect);

		log.info("TC_06: verify thoi tim kiem tu ngay");
		String dateEndActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate");
		String dateEndtExpect = getForwardDate(0);
		verifyEquals(dateEndActual, dateEndtExpect);

		log.info("TC_06: Tim kiem");
		transReport.clickToDynamicButton(driver, ReportTitle.SEARCH_BUTTON);

		log.info("TC_06_: Lay ngay tao giao dich hien thi");
		String transferTimeInReport = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_06: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport), transferTime);

		log.info("TC_06: Check ghi chu");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyQuick_Data.TransferQuick.NOTE));

		log.info("TC_06: Check so tien chuyen");// lỗi app
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(TransferMoneyQuick_Data.TransferQuick.MONEY) + " VND"));

		log.info("TC_06: Click chi tiet giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_06: Lay ngay tao giao dich hien thi");
		String transferTimeInReport1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_06: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport1), transferTime);

		log.info("TC_06: Check so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_NUMBER), transactionNumber);

		log.info("TC_06: So the chuyen di");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Tittle_Quick.CARD_TRIP), cardNumber);

		log.info("TC_06: Check tao khoan ghi no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_CARD), sourceAccount.account);

		log.info("TC_06: Check tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.NUMBER_CARD_VND), cardTo);

		log.info("TC_06: Check so nguoi huong");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Tittle_Quick.NAME_RECIVED), nameCustomer);

		log.info("TC_06: Check loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TYPE_TRANSFER), TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[1]);

		log.info("TC_06: Check so tien giao dich");// Lỗi app
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_MONEY).contains(addCommasToLong(TransferMoneyQuick_Data.TransferQuick.MONEY) + " VND"));

		log.info("TC_06_Step_Lay gia tri so tien phí chuyen");
		costTranferString = transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_FEE).replaceAll("\\D+", "");

		log.info("TC_06_Step_Verify so tien phi");
		verifyEquals(costTranferString, costTranfer + "");

		log.info("TC_06: Check noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.CONTENT_TRANSFER).contains(TransferMoneyQuick_Data.TransferQuick.NOTE));

		log.info("TC_06: click button back");
		transReport.clickToDynamicBackIcon(driver, ReportTitle.DETAIL_TRANSFER);

		log.info("TC_06: Chon button back");
		transferMoney.clickToDynamicBackIcon(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_06_Step_Click button home");
		transferMoney.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@Test
	public void TC_07_ChuyenTienQuaTheNguoiNhanTRaPhiVNDVaXacThucBangMatKhau() {
		log.info("TC_07_Step_Click Chuyen tien nhanh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferQuick.TRANSFER_MONEY_LABEL);

		log.info("TC_07_Step_Select Chuyen tien nhanh qua the");
		transferMoney.clickToTextID(driver, "com.VCB:id/tvContent");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[1]);

		log.info("TC_09_Step_Select tai khoan nguon");
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
				if (!sourceAccount.currentcy.equals(Constants.VND_CURRENCY) || convertAvailableBalanceCurrentcyToDouble(sourceAccount.balance) < Double.valueOf(Constants.MONEY_CHECK_VND)) {
					transferMoney.clickToDynamicDropDown(driver, Tittle_Quick.CARD_TRIP);
					transferMoney.scrollUpToText(driver, listCard.get(0));
					continue;
				} else {
					break;
				}
			}
		}

		log.info("TC_07_Step_Doi kieu du lieu");
		amountStart = Long.parseLong(sourceAccount.balance);

		log.info("TC_07_Step_Nhap so tai khoan chuyen");
		transferMoney.inputToDynamicInputBox(driver, cardTo, Tittle_Quick.TYPE_SELECT_CARD_NUMBER);

		log.info("TC_07_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.MONEY, TransferQuick.MOUNT_LABEL);

		log.info("TC_07_Step_Chon phi giao dich la nguoi nhan tra");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[1]);

		log.info("TC_07_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE, Tittle_Quick.TRANSACTION_INFO, "3");

		log.info("TC_07_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);

		log.info("TC_07_Step_Chon phuong thuc xac thuc");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[0]);

		log.info("TC_07_Step_Lay gia tri so tien chuyen");
		amountTranferString = transferMoney.getDynamicTextByLabel(driver, TransferQuick.MOUNT_LABEL).replaceAll("\\D+", "");

		log.info("TC_07_Step_Verify so tien chuyen");
		verifyEquals(amountTranferString, TransferMoneyQuick_Data.TransferQuick.MONEY);

		log.info("TC_07_Step_doi kieu du lieu string -> long");
		amountTranfer = Long.parseLong(amountTranferString);

		log.info("TC_09_Step_Lay gia tri so tien phí chuyen");
		costTranferString = transferMoney.getDynamicTextByLabel(driver, TransferQuick.AMOUNT_FEE_LABEL).replaceAll("\\D+", "");

		log.info("TC_09_Step_doi kieu du lieu string -> long");
		costTranfer = Long.parseLong(costTranferString);

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
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.CAR_NUMBER), cardTo);

		log.info("TC_07_Step_: Noi dung");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.CONTENT), TransferMoneyQuick_Data.TransferQuick.NOTE);

		log.info("TC_07_Step_: Thuc hien giao dich moi");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.NEW_TRANSFER);

		log.info("TC_07_Step_Select Chuyen tien nhanh qua the");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[1]);

		log.info("TC_07_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, Tittle_Quick.CARD_TRIP);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, cardNumber);

		log.info("TC_07_Step_:Check so du kha dung sau khi chuyen tien");
		String amountAfterString = transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.AVAIBLE_BALANCES).replaceAll("\\D+", "");

		long amountAfter = Long.parseLong(amountAfterString);

		log.info("TC_07_Step_:Check so du kha dung sau khi chuyen tien");
		verifyEquals(amountStart - amountTranfer, amountAfter);
	}

	@Test
	// App lỗi số tiền giao dịch, hiện tiền VND hiển thị dạng số thập phân VD:
	// 10000.00 VND => tiền ghi bị sai, ghi là "Một triệu đồng", expect ghi "mười
	// nghìn đồng"

	public void TC_08_ReportChuyenTienQuaTheNguoiNhanTRaPhiVNDVaXacThucBangMatKhau() {
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
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSFER_24_7_CARD);

		log.info("TC_08: Chon so tai khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_08: Chon so tai khoan tra cuu");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

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

		log.info("TC_08_: Lay ngay tao giao dich hien thi");
		String transferTimeInReport = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_08: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport), transferTime);

		log.info("TC_08: Check ghi chu");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyQuick_Data.TransferQuick.NOTE));

		log.info("TC_08: Check so tien chuyen");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(TransferMoneyQuick_Data.TransferQuick.MONEY) + " VND"));

		log.info("TC_08: Click chi tiet giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_08: Lay ngay tao giao dich hien thi");
		String transferTimeInReport1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_08: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport1), transferTime);

		log.info("TC_08: Check so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_NUMBER), transactionNumber);

		log.info("TC_08: So the chuyen di");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Tittle_Quick.CARD_TRIP), cardNumber);

		log.info("TC_08: Check tao khoan ghi no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_CARD), sourceAccount.account);

		log.info("TC_08: Check tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.NUMBER_CARD_VND), cardTo);

		log.info("TC_10: Check loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TYPE_TRANSFER), TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[1]);

		log.info("TC_08: Check so nguoi huong");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Tittle_Quick.NAME_RECIVED), nameCustomer);

		log.info("TC_08: Check so tien giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_MONEY).contains(addCommasToLong(TransferMoneyQuick_Data.TransferQuick.MONEY) + " VND"));

		log.info("TC_08_Step_Lay gia tri so tien phí chuyen");
		costTranferString = transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_FEE).replaceAll("\\D+", "");

		log.info("TC_08_Step_Verify so tien phi");
		verifyEquals(costTranferString, costTranfer + "");

		log.info("TC_08: Check noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.CONTENT_TRANSFER).contains(TransferMoneyQuick_Data.TransferQuick.NOTE));

		log.info("TC_08: click button back");
		transReport.clickToDynamicBackIcon(driver, ReportTitle.DETAIL_TRANSFER);

		log.info("TC_08: Chon button back");
		transferMoney.clickToDynamicBackIcon(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_08_Step_Click button home");
		transferMoney.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@Test
	// Lỗi app, số tiền bị bỏ đơn vị USD
	public void TC_09_ChuyenTienQuaSoTheCoPhiGiaoDichNguoiNhanTraUSDVaXacThucBangMatKhau() {
		log.info("TC_09_Step_Click Chuyen tien nhanh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferQuick.TRANSFER_MONEY_LABEL);

		log.info("TC_09_Step_Select Chuyen tien nhanh qua the");
		transferMoney.clickToTextID(driver, "com.VCB:id/tvContent");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[1]);

		log.info("TC_09_Step_Select tai khoan nguon");
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
				if (!sourceAccount.currentcy.equals(Constants.USD_CURRENCY) || convertAvailableBalanceCurrentcyToDouble(sourceAccount.balance) < Double.valueOf(Constants.MONEY_CHECK_USD)) {
					transferMoney.clickToDynamicDropDown(driver, Tittle_Quick.CARD_TRIP);
					transferMoney.scrollUpToText(driver, listCard.get(0));
					continue;
				} else {
					break;
				}
			}

		}
		log.info("TC_09_Step_Nhap so tai khoan chuyen");
		transferMoney.inputToDynamicInputBox(driver, cardTo, Tittle_Quick.TYPE_SELECT_CARD_NUMBER);

		log.info("TC_09_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_USD, TransferQuick.MOUNT_LABEL);

		log.info("TC_09_Step_Chon phi giao dich la nguoi nhan tra");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[1]);

		log.info("TC_09_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE, Tittle_Quick.TRANSACTION_INFO, "3");

		log.info("TC_09_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);

		log.info("TC_09_Step_Lay gia tri so tien chuyen");// Loi app, số tiền bỏ đơn vị USD
		amountTranferString = transferMoney.getDynamicTextByLabel(driver, TransferQuick.MOUNT_LABEL).replaceAll(".00 USD", "");

		log.info("TC_09_Step_Verify so tien chuyen");
		verifyEquals(amountTranferString, TransferMoneyQuick_Data.TransferQuick.MONEY_USD);

		log.info("TC_09_Step_doi kieu du lieu string -> long");
		amountTranfer = Long.parseLong(amountTranferString);

		log.info("TC_09_Step_Chon phuong thuc xac thuc");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[0]);

		log.info("TC_09_Step_Lay gia tri so tien phí chuyen");
		costTranferString = transferMoney.getDynamicTextByLabel(driver, TransferQuick.AMOUNT_FEE_LABEL).replaceAll("\\D+", "");

		log.info("TC_09_Step_doi kieu du lieu string -> long");
		costTranfer = Long.parseLong(costTranferString);

		log.info("TC_09_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);

		log.info("TC_09_Step_Nhap ma xac thuc");
		transferMoney.inputToDynamicPopupPasswordInput(driver, password, Tittle_Quick.CONTINUE_BUTTON);

		log.info("TC_09_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);

		log.info("TC_09_Verify message thanh cong");
		verifyEquals(transferMoney.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY1);

		log.info("TC_09_get thoi gian giao dich thanh cong");
		transferTime = transferMoney.getDynamicTransferTimeAndMoney(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY1, "4").split(" ")[3];

		log.info("TC_09_Step_: Get ma giao dich");
		transactionNumber = transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.CODE_TRANSFER);

		log.info("TC_09_Step_:Ten nguoi thu huong");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.NAME_RECIVED_TEXT), nameCustomer);

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
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, cardNumber);

		log.info("TC_09: Lay so du kha dung the");
		String amountAfterString = transferMoney.getDynamicTextInTransactionDetail(driver, Tittle_Quick.AVAIBLE_BALANCES);
		double amountAfter = convertMoneyToDouble(amountAfterString, Constants.USD_CURRENCY);
		double amountExpect = Math.round((amountAfter) * 100) / 100;

		log.info("TC_09: Convert so tien chuyen");
		double transferMoney = convertMoneyToDouble(TransferMoneyQuick_Data.TransferQuick.MONEY_USD, " USD");

		log.info("TC_09: Kiem tra so du tai khoan USD sau khi chuyen tien");
		double amountAtual = Math.round((amountStart - transferMoney) * 100) / 100;
		verifyEquals(amountAtual, amountExpect);
	}

	@Test
	public void TC_10_ReportChuyenTienQuaSoTheCoPhiGiaoDichNguoiNhanTraUSDVaXacThucBangMatKhau() {
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
		transReport.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_10: verify thoi tim kiem tu ngay");
		String dateStartActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate");
		String dateStartExpect = getBackwardDate(6);
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

		log.info("TC_10: Check so tien chuyen");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(TransferMoneyQuick_Data.TransferQuick.MONEY_USD) + ".00 USD"));

		log.info("TC_10: Click chi tiet giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_10: Lay ngay tao giao dich hien thi");
		String transferTimeInReport1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_10: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport1), transferTime);

		log.info("TC_10: Check so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_NUMBER), transactionNumber);

		log.info("TC_10: So the chuyen di");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Tittle_Quick.CARD_TRIP), cardNumber);

		log.info("TC_10: Check tao khoan ghi no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_CARD), sourceAccount.account);

		log.info("TC_10: Check tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.NUMBER_CARD_VND), cardTo);

		log.info("TC_10: Check so nguoi huong");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Tittle_Quick.NAME_RECIVED), nameCustomer);

		log.info("TC_10: Check loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TYPE_TRANSFER), TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[1]);

		log.info("TC_10: Check so tien giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_MONEY), TransferMoneyQuick_Data.TransferQuick.MONEY_USD + ".00 USD");

		log.info("TC_10: So tien quy doi expect");
		double amountAfter = convertMoneyToDouble(TransferMoneyQuick_Data.TransferQuick.MONEY_USD, Constants.USD_CURRENCY);
		double amountExpect = amountAfter * convertMoneyToDouble(exchangeRate, Constants.VND_CURRENCY);

		log.info("TC_10: So tien quy doi atual");
		String amountAfter1 = transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_MONEY).replaceAll(".00 USD", "");
		double amountActual = convertMoneyToDouble(amountAfter1, Constants.VND_CURRENCY) * convertMoneyToDouble(exchangeRate, Constants.VND_CURRENCY);

		log.info("TC_10: verify so tien quy doi");
		verifyEquals(amountActual, amountExpect);

		log.info("TC_10_Step_Lay gia tri so tien phí chuyen");
		costTranferString = transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_FEE).replaceAll("\\D+", "");

		log.info("TC_10_Step_Verify so tien phi");
		verifyEquals(costTranferString, costTranfer + "");

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
	// Lỗi app, số tiền bị bỏ đơn vị USD
	public void TC_11_ChuyenTienQuaSoTheCoPhiGiaoDichNguoiNhanTraUSDVaXacThucBangMatKhau() {
		log.info("TC_11_Step_Click Chuyen tien nhanh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferQuick.TRANSFER_MONEY_LABEL);

		log.info("TC_11_Step_Select Chuyen tien nhanh qua the");
		transferMoney.clickToTextID(driver, "com.VCB:id/tvContent");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[1]);

		log.info("TC_09_Step_Select tai khoan nguon");
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
				if (!sourceAccount.currentcy.equals(Constants.USD_CURRENCY) || convertAvailableBalanceCurrentcyToDouble(sourceAccount.balance) < Double.valueOf(Constants.MONEY_CHECK_USD)) {
					transferMoney.clickToDynamicDropDown(driver, Tittle_Quick.CARD_TRIP);
					transferMoney.scrollUpToText(driver, listCard.get(0));
					continue;
				} else {
					break;
				}
			}

		}
		log.info("TC_11_Step_Nhap so tai khoan chuyen");
		transferMoney.inputToDynamicInputBox(driver, cardTo, Tittle_Quick.TYPE_SELECT_CARD_NUMBER);

		log.info("TC_11_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_USD, TransferQuick.MOUNT_LABEL);

		log.info("TC_11_Step_Chon phi giao dich la nguoi nhan tra");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[1]);

		log.info("TC_11_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE, Tittle_Quick.TRANSACTION_INFO, "3");

		log.info("TC_11_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);

		log.info("TC_11_Step_Lay gia tri so tien chuyen");// Loi app, số tiền bỏ đơn vị USD
		amountTranferString = transferMoney.getDynamicTextByLabel(driver, TransferQuick.MOUNT_LABEL).replaceAll(".00 USD", "");

		log.info("TC_11_Step_Verify so tien chuyen");
		verifyEquals(amountTranferString, TransferMoneyQuick_Data.TransferQuick.MONEY_USD);

		log.info("TC_11_Step_doi kieu du lieu string -> long");
		amountTranfer = Long.parseLong(amountTranferString);

		log.info("TC_11_Step_Chon phuong thuc xac thuc");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[0]);

		log.info("TC_11_Step_Lay gia tri so tien phí chuyen");
		costTranferString = transferMoney.getDynamicTextByLabel(driver, TransferQuick.AMOUNT_FEE_LABEL).replaceAll("\\D+", "");

		log.info("TC_11_Step_doi kieu du lieu string -> long");
		costTranfer = Long.parseLong(costTranferString);

		log.info("TC_11_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);

		log.info("TC_11_Step_Nhap ma xac thuc");
		transferMoney.inputToDynamicPopupPasswordInput(driver, password, Tittle_Quick.CONTINUE_BUTTON);

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
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, cardNumber);

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

	@AfterClass(alwaysRun = true)

	public void afterClass() {
		closeApp();
		service.stop();
	}
}