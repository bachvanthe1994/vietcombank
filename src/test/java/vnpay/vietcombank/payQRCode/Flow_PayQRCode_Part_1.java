package vnpay.vietcombank.payQRCode;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import model.OrderQRCode_Type1_Info;
import model.OrderQRCode_Type2_Info;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.QRCodePageObject;
import pageObjects.SettingVCBSmartOTPPageObject;
import pageObjects.TransactionReportPageObject;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.PayQRCode_Data;
import vietcombank_test_data.TransactionReport_Data;

public class Flow_PayQRCode_Part_1 extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private QRCodePageObject payQRCode;
	private TransactionReportPageObject transReport;
	private SettingVCBSmartOTPPageObject setupSmartOTP;
	private String transferTime;
	private String transactionNumber;
	long transferFee, money = 0;
	String password, otpNumber, smartOTP = "";

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String otp) throws IOException, InterruptedException, GeneralSecurityException {
		startServer();
		smartOTP = getDataInCell(6);
		
		log.info("Before class: Mo app ");
		if (deviceType.contains("android")) {
			driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		} else if (deviceType.contains("ios")) {
			driver = openIOSApp(deviceName, udid, url);
		}
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, otp);

		password = pass;
		otpNumber = otp;
		
		homePage = PageFactoryManager.getHomePageObject(driver);
		payQRCode = PageFactoryManager.getQRCodePageObject(driver);
		setupSmartOTP = PageFactoryManager.getSettingVCBSmartOTPPageObject(driver);
		setupSmartOTP.setupSmartOTP(LogIn_Data.Login_Account.Smart_OTP, smartOTP);

	}

	private long surplus, availableBalance, actualAvailableBalance;
	String account, codeOrder = "";

	@Test
	public void TC_01_ThanhToanHoaDon_Type1_QRCode() {
		log.info("TC_01_1_Click QR Pay");
		homePage.sleep(driver, 3000);
		payQRCode.scrollUpToText(driver, PayQRCode_Data.QR_PAY_UPPERCASE);
		homePage.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.QR_PAY_UPPERCASE);

		log.info("TC_01_2_Click mo Thu vien anh");
		payQRCode.clickToDynamicAcceptButtonContainOR(driver, "com.android.packageinstaller:id/permission_allow_button", "com.android.permissioncontroller:id/permission_allow_button");
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.IMAGE_LIBRARY);

		log.info("TC_01_3_Click chon Tat ca anh");
		payQRCode.clickToDynamicAcceptButtonContainOR(driver, "com.android.packageinstaller:id/permission_allow_button", "com.android.permissioncontroller:id/permission_allow_button");
		payQRCode.clickToDynamicImageButtonByContentDesc(driver, PayQRCode_Data.TEXT_BUTTON_MENU);
		payQRCode.clickToDynamicTextContains(driver, PayQRCode_Data.TEXT_CATEGORY_MENU);
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.TYPE_1);

		log.info("TC_01_4_Lay so luong anh");
		int numberOfImage = payQRCode.getNumberOfImageInLibrary();

		log.info("TC_01_5_Chon anh");
		OrderQRCode_Type1_Info qrCode = payQRCode.chooseQRCodeType1(numberOfImage);
		String destinationPlace = qrCode.destinationPlace;
		String namePlace = qrCode.namePlace;
		String codePlace = qrCode.codePlace;
		money = convertAvailableBalanceCurrentcyOrFeeToLong(qrCode.money);
		account = qrCode.account;

		log.info("TC_01_6_Lay so du");
		surplus = convertAvailableBalanceCurrentcyOrFeeToLong(qrCode.surplus);

		log.info("TC_01_11_Kiem tra man hinh xac nhan thong tin");
		payQRCode.scrollUpToText(driver, PayQRCode_Data.SOURCE_ACCOUNT);

		log.info("TC_01_11_1_Kiem tra tai khoan nguon");
		verifyEquals(payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.SOURCE_ACCOUNT), account);

		log.info("TC_01_11_2_Kiem tra thanh toan cho");
		verifyEquals(payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.DESTINATION_PLACE), destinationPlace);

		log.info("TC_01_11_3_Kiem tra ten diem ban");
		verifyEquals(payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.NAME_PLACE), namePlace);

		log.info("TC_01_11_4_Kiem tra thanh toan cho");
		verifyEquals(payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.CODE_PLACE), codePlace);

		codeOrder = payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.ORDER_CODE);

		log.info("TC_01_11_5_Kiem tra so tien");
		verifyTrue(payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.AMOUNT_PAYMENT).contains(addCommasToLong(money + "") + " VND"));

		log.info("TC_01_12_Chon phuong thuc xac thuc");
		payQRCode.scrollDownToText(driver, PayQRCode_Data.ACCURACY_METHOD);
		payQRCode.clickToDynamicDropDown(driver, PayQRCode_Data.ACCURACY_METHOD);
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.SMS_OTP);

		log.info("TC_01_12_01_Kiem tra so tien phi");
		transferFee = 0;

		log.info("TC_01_13_Click Tiep tuc");
		payQRCode.clickToDynamicButton(driver, PayQRCode_Data.CONTINUE_BUTTON);

		payQRCode.inputToDynamicOtp(driver, otpNumber, PayQRCode_Data.CONTINUE_BUTTON);

		payQRCode.clickToDynamicButton(driver, PayQRCode_Data.CONTINUE_BUTTON);

		log.info("TC_01_14: Kiem  tra giao dich thanh cong");
		verifyTrue(payQRCode.isDynamicMessageAndLabelTextDisplayed(driver, PayQRCode_Data.SUCCESS_TRANSACTION));

		log.info("TC_01_15: Kiem  tra so tien giao dich");
		verifyTrue(payQRCode.isDynamicMessageAndLabelTextDisplayed(driver, addCommasToLong(money + "") + " VND"));

		log.info("TC_01_16: Lay thoi gian tao giao dich");
		transferTime = payQRCode.getTransferTimeSuccess(driver, PayQRCode_Data.SUCCESS_TRANSACTION);

		log.info("TC_01_17: Lay ma giao dich");
		transactionNumber = payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.TRANSFER_CODE);

		log.info("TC_01_18: Click thuc hien giao dich moi");
		payQRCode.clickToDynamicButton(driver, PayQRCode_Data.PERFORM_OTHER_TRANSACTION);

		log.info("TC_01_19_Chon tai khoan nguon, kiem tra tai khoan chuyen bi tru tien");
		payQRCode.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
		payQRCode.scrollDownToText(driver, PayQRCode_Data.STATUS_TRANSFER_MONEY);
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.TRANSFER_MONEY_CHARITY);
		payQRCode.scrollUpToText(driver, PayQRCode_Data.SOURCE_ACCOUNT);
		payQRCode.clickToDynamicDropDown(driver, PayQRCode_Data.SOURCE_ACCOUNT);
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, account);
		payQRCode.scrollUpToText(driver, PayQRCode_Data.SOURCE_ACCOUNT);
		actualAvailableBalance = convertAvailableBalanceCurrentcyOrFeeToLong(payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.AVAILABLE_BALANCE));
		availableBalance = canculateAvailableBalances(surplus, money, transferFee);
		verifyEquals(actualAvailableBalance, availableBalance);

	}

	@Test
	public void TC_02_ThanhToanHoaDon_Type1_QRCode_BaoCao() {
		log.info("TC_02_01: Click quay lai");
		payQRCode.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_02_02: Click vao Menu Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_02_03: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransactionReport_Data.ReportTitle.TRANSACTION_REPORT);

		log.info("TC_02_04: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransactionReport_Data.ReportTitle.ALL_TYPE_TRANSACTION);

		log.info("TC_02_05: Chon Thanh toan QR Code");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.PAY_QR_CODE);

		log.info("TC_02_06: Click Chon Tai Khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_02_07: Chon tai khoan nguon");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, account);

		log.info("TC_02_08: CLick Tim Kiem");
		transReport.clickToDynamicButton(driver, PayQRCode_Data.SEARCH_BUTTON);

		log.info("TC_02_09: Lay ngay tao giao dich hien thi");
		String transferTimeInReport = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_02_10: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_02_11: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").contains(PayQRCode_Data.QR_PAY_UPPERCASE));

		log.info("TC_02_12: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), "- " +  addCommasToLong(money + "") + " VND");

		log.info("TC_02_13: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_02_14: Kiem tra thoi gian tao giao dich hien thi");
		String reportTime1 = transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TIME_TRANSACTION);
		verifyEquals(reportTime1, transferTimeInReport);

		log.info("TC_02_15: Kiem tra thoi gian tao giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_NUMBER), transactionNumber);

		log.info("TC_02_16: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.ACCOUNT_CARD), account);

		log.info("TC_02_17: Kiem tra so tai khoan ghi co");

		log.info("TC_02_18: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_MONEY).contains(addCommasToLong(money + "") + " VND"));

		log.info("TC_02_22: Kiem tra loai giao dich");  
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_TYPE), PayQRCode_Data.PAY_QR_CODE);

		String note = "MBVCB." + transactionNumber + ".QR Pay.Thanh toan cho ";
		log.info("TC_02_23: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_CONTENT).contains(note));

		log.info("TC_02_24: Click  nut Back");
		transReport.clickToDynamicBackIcon(driver, TransactionReport_Data.ReportTitle.TRANSACTION_DETAIL);

		log.info("TC_02_25: Click  nut Back");
		transReport.clickToDynamicBackIcon(driver, TransactionReport_Data.ReportTitle.TRANSACTION_REPORT);

		log.info("TC_02_26: Click  nut Home");
		transReport.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");

	}

	@Test
	public void TC_03_ThanhToanHoaDon_Type2_QRCode() {
		log.info("TC_03_1_Click QR Pay");
		homePage.sleep(driver, 3000);
		payQRCode.scrollUpToText(driver, PayQRCode_Data.QR_PAY_UPPERCASE);
		homePage.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.QR_PAY_UPPERCASE);

		log.info("TC_03_2_Click mo Thu vien anh");
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.IMAGE_LIBRARY);

		log.info("TC_03_3_Click chon Tat ca anh");
		payQRCode.clickToDynamicImageButtonByContentDesc(driver, PayQRCode_Data.TEXT_BUTTON_MENU);
		payQRCode.clickToDynamicTextContains(driver, PayQRCode_Data.TEXT_CATEGORY_MENU);
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.TYPE_2);

		log.info("TC_03_4_Lay so luong anh");
		int numberOfImage = payQRCode.getNumberOfImageInLibrary();

		log.info("TC_03_5_Chon anh");
		OrderQRCode_Type2_Info qrCode = payQRCode.chooseQRCodeType2(numberOfImage);
		String provider = qrCode.provider;
		String service = qrCode.service;
		String codeCustomer = qrCode.codeCustomer;
		account = qrCode.account;

		log.info("TC_03_7_Lay so du");
		surplus = convertAvailableBalanceCurrentcyOrFeeToLong(qrCode.surplus);

		log.info("TC_03_09_Kiem tra man hinh xac nhan thong tin");
		payQRCode.scrollUpToText(driver, PayQRCode_Data.SOURCE_ACCOUNT);

		log.info("TC_03_09_1_Kiem tra tai khoan nguon");
		verifyEquals(payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.SOURCE_ACCOUNT), account);

		log.info("TC_03_09_2_Kiem tra nha cung cap");
		verifyEquals(payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.PROVIDER), provider);

		log.info("TC_03_09_3_Kiem tra dich vu");
		verifyEquals(payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.SERVICE), service);

		log.info("TC_03_09_4_Kiem tra ma khach hang");
		verifyEquals(payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.CUSTOMER_CODE), codeCustomer);

		money = convertAvailableBalanceCurrentcyOrFeeToLong(payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.AMOUNT_PAYMENT));

		log.info("TC_03_10_Chon phuong thuc xac thuc");
		payQRCode.scrollDownToText(driver, PayQRCode_Data.ACCURACY_METHOD);
		payQRCode.clickToDynamicDropDown(driver, PayQRCode_Data.ACCURACY_METHOD);
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.SMS_OTP);

		log.info("TC_03_11_Kiem tra so tien phi");
		transferFee = 0;
		
		log.info("TC_03_12_Click Tiep tuc");
		payQRCode.clickToDynamicButton(driver, PayQRCode_Data.CONTINUE_BUTTON);

		payQRCode.inputToDynamicOtp(driver, otpNumber, PayQRCode_Data.CONTINUE_BUTTON);

		payQRCode.clickToDynamicButton(driver, PayQRCode_Data.CONTINUE_BUTTON);

		log.info("TC_03_13: Kiem  tra giao dich thanh cong");
		verifyTrue(payQRCode.isDynamicMessageAndLabelTextDisplayed(driver, PayQRCode_Data.SUCCESS_TRANSACTION));

		log.info("TC_03_14: Kiem  tra so tien giao dich");
		verifyTrue(payQRCode.isDynamicMessageAndLabelTextDisplayed(driver, addCommasToLong(money + "") + " VND"));

		log.info("TC_03_15: Lay thoi gian tao giao dich");
		transferTime = payQRCode.getTransferTimeSuccess(driver, PayQRCode_Data.SUCCESS_TRANSACTION);

		log.info("TC_03_16: Lay ma giao dich");
		transactionNumber = payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.TRANSFER_CODE);

		log.info("TC_03_19: Click thuc hien giao dich moi");
		payQRCode.clickToDynamicButton(driver, PayQRCode_Data.PERFORM_OTHER_TRANSACTION);

		log.info("TC_03_20_Chon tai khoan nguon, kiem tra tai khoan chuyen bi tru tien");
		payQRCode.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
		payQRCode.scrollDownToText(driver, PayQRCode_Data.STATUS_TRANSFER_MONEY);
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.TRANSFER_MONEY_CHARITY);
		payQRCode.scrollUpToText(driver, PayQRCode_Data.SOURCE_ACCOUNT);
		payQRCode.clickToDynamicDropDown(driver, PayQRCode_Data.SOURCE_ACCOUNT);
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, account);
		payQRCode.scrollUpToText(driver, PayQRCode_Data.SOURCE_ACCOUNT);
		actualAvailableBalance = convertAvailableBalanceCurrentcyOrFeeToLong(payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.AVAILABLE_BALANCE));
		availableBalance = canculateAvailableBalances(surplus, money, transferFee);
		verifyEquals(actualAvailableBalance, availableBalance);

	}

	@Test
	public void TC_04_ThanhToanHoaDon_Type2_QRCode_BaoCao() {
		if (QRCodePageObject.checkTestScriptFailed) {
			QRCodePageObject.checkTestScriptFailed = false;
			throw new SkipException("Khong ton tai bao cao cua giao dich nay");
		}
		
		log.info("TC_04_01: Click quay lai");
		payQRCode.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_04_02: Click vao Menu Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_04_03: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransactionReport_Data.ReportTitle.TRANSACTION_REPORT);

		log.info("TC_04_04: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransactionReport_Data.ReportTitle.ALL_TYPE_TRANSACTION);

		log.info("TC_04_05: Chon Thanh toan QR Code");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.PAY_QR_CODE);

		log.info("TC_04_06: Click Chon Tai Khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_04_07: Chon tai khoan nguon");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, account);

		log.info("TC_04_08: CLick Tim Kiem");
		transReport.clickToDynamicButton(driver, PayQRCode_Data.SEARCH_BUTTON);

		log.info("TC_04_09: Lay ngay tao giao dich hien thi");
		String transferTimeInReport = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_04_10: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_04_11: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(PayQRCode_Data.QR_PAY_UPPERCASE));

		log.info("TC_04_12: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), "- " +  addCommasToLong(money + "") + " VND");

		log.info("TC_04_13: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_04_14: Kiem tra thoi gian tao giao dich hien thi");
		String reportTime1 = transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TIME_TRANSACTION);
		verifyEquals(reportTime1, transferTimeInReport);

		log.info("TC_04_15: Kiem tra thoi gian tao giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_NUMBER), transactionNumber);

		log.info("TC_04_16: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.ACCOUNT_CARD), account);

		log.info("TC_04_17: Kiem tra so tai khoan ghi co");

		log.info("TC_04_18: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_MONEY).contains(addCommasToLong(money + "") + " VND"));

		log.info("TC_04_22: Kiem tra loai giao dich"); 
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_TYPE), PayQRCode_Data.PAY_QR_CODE);

		String note = "MBVCB." + transactionNumber + ".QR Pay.Thanh toan cho ";
		log.info("TC_04_23: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_CONTENT).contains(note));

		log.info("TC_04_24: Click  nut Back");
		transReport.clickToDynamicBackIcon(driver, TransactionReport_Data.ReportTitle.TRANSACTION_DETAIL);

		log.info("TC_04_25: Click  nut Back");
		transReport.clickToDynamicBackIcon(driver, TransactionReport_Data.ReportTitle.TRANSACTION_REPORT);

		log.info("TC_04_26: Click  nut Home");
		transReport.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");

	}

	@Test
	public void TC_05_ThanhToanHoaDon_Type3_QRCode() {
		log.info("TC_05_1_Click QR Pay");
		homePage.sleep(driver, 3000);
		payQRCode.scrollUpToText(driver, PayQRCode_Data.QR_PAY_UPPERCASE);
		homePage.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.QR_PAY_UPPERCASE);

		log.info("TC_05_2_Click mo Thu vien anh");
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.IMAGE_LIBRARY);

		log.info("TC_05_3_Click chon Tat ca anh");
		payQRCode.clickToDynamicImageButtonByContentDesc(driver, PayQRCode_Data.TEXT_BUTTON_MENU);
		payQRCode.clickToDynamicTextContains(driver, PayQRCode_Data.TEXT_CATEGORY_MENU);
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.TYPE_3);

		log.info("TC_05_4_Lay so luong anh");
		int numberOfImage = payQRCode.getNumberOfImageInLibrary();

		log.info("TC_05_5_Chon anh");
		OrderQRCode_Type1_Info qrCode = payQRCode.chooseQRCodeType3(numberOfImage);
		String destinationPlace = qrCode.destinationPlace;
		String namePlace = qrCode.namePlace;
		account = qrCode.account;

		log.info("TC_05_7_Lay so du");
		surplus = convertAvailableBalanceCurrentcyOrFeeToLong(qrCode.surplus);

		log.info("TC_05_8_Nhap thong tin lien he");
		payQRCode.inputContactInfomation();

		log.info("TC_05_09_Kiem tra man hinh xac nhan thong tin");
		payQRCode.scrollUpToText(driver, PayQRCode_Data.SOURCE_ACCOUNT);

		log.info("TC_05_09_1_Kiem tra tai khoan nguon");
		verifyEquals(payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.SOURCE_ACCOUNT), account);

		log.info("TC_05_09_2_Kiem tra thanh toan cho");
		verifyEquals(payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.DESTINATION_PLACE), destinationPlace);

		log.info("TC_05_09_3_Kiem tra ten diem ban");
		verifyEquals(payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.NAME_PLACE), namePlace);

		money = convertAvailableBalanceCurrentcyOrFeeToLong(payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.AMOUNT_PAYMENT));

		log.info("TC_05_12_Chon phuong thuc xac thuc");
		payQRCode.scrollDownToText(driver, PayQRCode_Data.ACCURACY_METHOD);
		payQRCode.clickToDynamicDropDown(driver, PayQRCode_Data.ACCURACY_METHOD);
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.SMS_OTP);

		log.info("TC_05_11_Kiem tra so tien phi");
		transferFee = 0;
		
		log.info("TC_05_12_Click Tiep tuc");
		payQRCode.clickToDynamicButton(driver, PayQRCode_Data.CONTINUE_BUTTON);

		payQRCode.inputToDynamicOtp(driver, otpNumber, PayQRCode_Data.CONTINUE_BUTTON);

		payQRCode.clickToDynamicButton(driver, PayQRCode_Data.CONTINUE_BUTTON);

		log.info("TC_05_13: Kiem  tra giao dich thanh cong");
		verifyTrue(payQRCode.isDynamicMessageAndLabelTextDisplayed(driver, PayQRCode_Data.SUCCESS_TRANSACTION));

		log.info("TC_05_14: Kiem  tra so tien giao dich");
		verifyTrue(payQRCode.isDynamicMessageAndLabelTextDisplayed(driver, addCommasToLong(money + "") + " VND"));

		log.info("TC_05_15: Lay thoi gian tao giao dich");
		transferTime = payQRCode.getTransferTimeSuccess(driver, PayQRCode_Data.SUCCESS_TRANSACTION);

		log.info("TC_05_16: Lay ma giao dich");
		transactionNumber = payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.TRANSFER_CODE);

		log.info("TC_05_17_Kiem tra nha cung cap");
		verifyEquals(payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.DESTINATION_PLACE), destinationPlace);

		log.info("TC_05_18_Kiem tra ten dich vu");
		verifyEquals(payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.PLACE_NAME), namePlace);

		log.info("TC_05_19: Click thuc hien giao dich moi");
		payQRCode.clickToDynamicButton(driver, PayQRCode_Data.PERFORM_OTHER_TRANSACTION);

		log.info("TC_05_20_Chon tai khoan nguon, kiem tra tai khoan chuyen bi tru tien");
		payQRCode.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
		payQRCode.scrollDownToText(driver, PayQRCode_Data.STATUS_TRANSFER_MONEY);
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.TRANSFER_MONEY_CHARITY);
		payQRCode.scrollUpToText(driver, PayQRCode_Data.SOURCE_ACCOUNT);
		payQRCode.clickToDynamicDropDown(driver, PayQRCode_Data.SOURCE_ACCOUNT);
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, account);
		payQRCode.scrollUpToText(driver, PayQRCode_Data.SOURCE_ACCOUNT);
		actualAvailableBalance = convertAvailableBalanceCurrentcyOrFeeToLong(payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.AVAILABLE_BALANCE));
		availableBalance = canculateAvailableBalances(surplus, money, transferFee);
		verifyEquals(actualAvailableBalance, availableBalance);

	}

	@Test
	public void TC_06_ThanhToanHoaDon_Type3_QRCode_BaoCao() {
		log.info("TC_06_01: Click quay lai");
		payQRCode.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_06_02: Click vao Menu Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_06_03: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransactionReport_Data.ReportTitle.TRANSACTION_REPORT);

		log.info("TC_06_04: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransactionReport_Data.ReportTitle.ALL_TYPE_TRANSACTION);

		log.info("TC_06_05: Chon Thanh toan QR Code");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.PAY_QR_CODE);

		log.info("TC_06_06: Click Chon Tai Khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_06_07: Chon tai khoan nguon");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, account);

		log.info("TC_06_08: CLick Tim Kiem");
		transReport.clickToDynamicButton(driver, PayQRCode_Data.SEARCH_BUTTON);

		log.info("TC_06_09: Lay ngay tao giao dich hien thi");
		String transferTimeInReport = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_06_10: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_06_11: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(PayQRCode_Data.QR_PAY_UPPERCASE));

		log.info("TC_06_12: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), "- " +  addCommasToLong(money + "") + " VND");

		log.info("TC_06_13: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_06_14: Kiem tra thoi gian tao giao dich hien thi");
		String reportTime1 = transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TIME_TRANSACTION);
		verifyEquals(reportTime1, transferTimeInReport);

		log.info("TC_06_15: Kiem tra thoi gian tao giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_NUMBER), transactionNumber);

		log.info("TC_06_16: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.ACCOUNT_CARD), account);

		log.info("TC_06_17: Kiem tra so tai khoan ghi co");

		log.info("TC_06_18: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_MONEY).contains(addCommasToLong(money + "") + " VND"));

		log.info("TC_06_22: Kiem tra loai giao dich"); 
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_TYPE), PayQRCode_Data.PAY_QR_CODE);

		String note = "MBVCB." + transactionNumber + ".QR Pay.Thanh toan cho ";
		log.info("TC_06_23: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_CONTENT).contains(note));

		log.info("TC_06_24: Click  nut Back");
		transReport.clickToDynamicBackIcon(driver, TransactionReport_Data.ReportTitle.TRANSACTION_DETAIL);

		log.info("TC_06_25: Click  nut Back");
		transReport.clickToDynamicBackIcon(driver, TransactionReport_Data.ReportTitle.TRANSACTION_REPORT);

		log.info("TC_06_26: Click  nut Home");
		transReport.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");

	}
	
	@Test
	public void TC_07_ThanhToanHoaDon_Type1_QRCode_SmartOTP() {
		log.info("TC_07_1_Click QR Pay");
		homePage.sleep(driver, 3000);
		payQRCode.scrollUpToText(driver, PayQRCode_Data.QR_PAY_UPPERCASE);
		homePage.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.QR_PAY_UPPERCASE);

		log.info("TC_07_2_Click mo Thu vien anh");
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.IMAGE_LIBRARY);

		log.info("TC_07_3_Click chon Tat ca anh");
		payQRCode.clickToDynamicImageButtonByContentDesc(driver, PayQRCode_Data.TEXT_BUTTON_MENU);
		payQRCode.clickToDynamicTextContains(driver, PayQRCode_Data.TEXT_CATEGORY_MENU);
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.TYPE_1);

		log.info("TC_07_4_Lay so luong anh");
		int numberOfImage = payQRCode.getNumberOfImageInLibrary();

		log.info("TC_07_5_Chon anh");
		OrderQRCode_Type1_Info qrCode = payQRCode.chooseQRCodeType1(numberOfImage);
		String destinationPlace = qrCode.destinationPlace;
		String namePlace = qrCode.namePlace;
		String codePlace = qrCode.codePlace;
		money = convertAvailableBalanceCurrentcyOrFeeToLong(qrCode.money);
		account = qrCode.account;

		log.info("TC_07_6_Lay so du");
		surplus = convertAvailableBalanceCurrentcyOrFeeToLong(qrCode.surplus);

		log.info("TC_07_11_Kiem tra man hinh xac nhan thong tin");
		payQRCode.scrollUpToText(driver, PayQRCode_Data.SOURCE_ACCOUNT);

		log.info("TC_07_11_1_Kiem tra tai khoan nguon");
		verifyEquals(payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.SOURCE_ACCOUNT), account);

		log.info("TC_07_11_2_Kiem tra thanh toan cho");
		verifyEquals(payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.DESTINATION_PLACE), destinationPlace);

		log.info("TC_07_11_3_Kiem tra ten diem ban");
		verifyEquals(payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.NAME_PLACE), namePlace);

		log.info("TC_07_11_4_Kiem tra thanh toan cho");
		verifyEquals(payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.CODE_PLACE), codePlace);

		codeOrder = payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.ORDER_CODE);

		log.info("TC_07_11_5_Kiem tra so tien");
		verifyTrue(payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.AMOUNT_PAYMENT).contains(addCommasToLong(money + "") + " VND"));

		log.info("TC_07_12_Chon phuong thuc xac thuc");
		payQRCode.scrollDownToText(driver, PayQRCode_Data.ACCURACY_METHOD);
		payQRCode.clickToDynamicDropDown(driver, PayQRCode_Data.ACCURACY_METHOD);
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.VCB_SMART_OTP);

		log.info("TC_07_12_01_Kiem tra so tien phi");
		transferFee = 0;

		log.info("TC_07_13_Click Tiep tuc");
		payQRCode.clickToDynamicButton(driver, PayQRCode_Data.CONTINUE_BUTTON);

		payQRCode.inputToDynamicSmartOTP(driver, LogIn_Data.Login_Account.Smart_OTP, "com.VCB:id/otp");

		payQRCode.clickToDynamicButton(driver, PayQRCode_Data.CONTINUE_BUTTON);
		
		payQRCode.clickToDynamicButton(driver, PayQRCode_Data.CONTINUE_BUTTON);

		log.info("TC_07_14: Kiem  tra giao dich thanh cong");
		verifyTrue(payQRCode.isDynamicMessageAndLabelTextDisplayed(driver, PayQRCode_Data.SUCCESS_TRANSACTION));

		log.info("TC_07_15: Kiem  tra so tien giao dich");
		verifyTrue(payQRCode.isDynamicMessageAndLabelTextDisplayed(driver, addCommasToLong(money + "") + " VND"));

		log.info("TC_07_16: Lay thoi gian tao giao dich");
		transferTime = payQRCode.getTransferTimeSuccess(driver, PayQRCode_Data.SUCCESS_TRANSACTION);

		log.info("TC_07_17: Lay ma giao dich");
		transactionNumber = payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.TRANSFER_CODE);

		log.info("TC_07_18: Click thuc hien giao dich moi");
		payQRCode.clickToDynamicButton(driver, PayQRCode_Data.PERFORM_OTHER_TRANSACTION);

		log.info("TC_07_19_Chon tai khoan nguon, kiem tra tai khoan chuyen bi tru tien");
		payQRCode.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
		payQRCode.scrollDownToText(driver, PayQRCode_Data.STATUS_TRANSFER_MONEY);
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.TRANSFER_MONEY_CHARITY);
		payQRCode.scrollUpToText(driver, PayQRCode_Data.SOURCE_ACCOUNT);
		payQRCode.clickToDynamicDropDown(driver, PayQRCode_Data.SOURCE_ACCOUNT);
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, account);
		payQRCode.scrollUpToText(driver, PayQRCode_Data.SOURCE_ACCOUNT);
		actualAvailableBalance = convertAvailableBalanceCurrentcyOrFeeToLong(payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.AVAILABLE_BALANCE));
		availableBalance = canculateAvailableBalances(surplus, money, transferFee);
		verifyEquals(actualAvailableBalance, availableBalance);

	}

	@Test
	public void TC_08_ThanhToanHoaDon_Type1_QRCode_SmartOTP_BaoCao() {
		log.info("TC_08_01: Click quay lai");
		payQRCode.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_08_02: Click vao Menu Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_08_03: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransactionReport_Data.ReportTitle.TRANSACTION_REPORT);

		log.info("TC_08_04: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransactionReport_Data.ReportTitle.ALL_TYPE_TRANSACTION);

		log.info("TC_08_05: Chon Thanh toan QR Code");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.PAY_QR_CODE);

		log.info("TC_08_06: Click Chon Tai Khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_08_07: Chon tai khoan nguon");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, account);

		log.info("TC_08_08: CLick Tim Kiem");
		transReport.clickToDynamicButton(driver, PayQRCode_Data.SEARCH_BUTTON);

		log.info("TC_08_09: Lay ngay tao giao dich hien thi");
		String transferTimeInReport = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_08_10: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_08_11: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").contains(PayQRCode_Data.QR_PAY_UPPERCASE));

		log.info("TC_08_12: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), "- " +  addCommasToLong(money + "") + " VND");

		log.info("TC_08_13: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_08_14: Kiem tra thoi gian tao giao dich hien thi");
		String reportTime1 = transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TIME_TRANSACTION);
		verifyEquals(reportTime1, transferTimeInReport);

		log.info("TC_08_15: Kiem tra thoi gian tao giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_NUMBER), transactionNumber);

		log.info("TC_08_16: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.ACCOUNT_CARD), account);

		log.info("TC_08_17: Kiem tra so tai khoan ghi co");

		log.info("TC_08_18: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_MONEY).contains(addCommasToLong(money + "") + " VND"));

		log.info("TC_08_22: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_TYPE), PayQRCode_Data.PAY_QR_CODE);

		String note = "MBVCB." + transactionNumber + ".QR Pay.Thanh toan cho ";
		log.info("TC_08_23: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_CONTENT).contains(note));

		log.info("TC_08_24: Click  nut Back");
		transReport.clickToDynamicBackIcon(driver, TransactionReport_Data.ReportTitle.TRANSACTION_DETAIL);

		log.info("TC_08_25: Click  nut Back");
		transReport.clickToDynamicBackIcon(driver, TransactionReport_Data.ReportTitle.TRANSACTION_REPORT);

		log.info("TC_08_26: Click  nut Home");
		transReport.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");

	}

	@Test
	public void TC_09_ThanhToanHoaDon_Type2_QRCode_SmartOTP() {
		log.info("TC_09_1_Click QR Pay");
		homePage.sleep(driver, 3000);
		payQRCode.scrollUpToText(driver, PayQRCode_Data.QR_PAY_UPPERCASE);
		homePage.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.QR_PAY_UPPERCASE);

		log.info("TC_09_2_Click mo Thu vien anh");
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.IMAGE_LIBRARY);

		log.info("TC_09_3_Click chon Tat ca anh");
		payQRCode.clickToDynamicImageButtonByContentDesc(driver, PayQRCode_Data.TEXT_BUTTON_MENU);
		payQRCode.clickToDynamicTextContains(driver, PayQRCode_Data.TEXT_CATEGORY_MENU);
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.TYPE_2);

		log.info("TC_09_4_Lay so luong anh");
		int numberOfImage = payQRCode.getNumberOfImageInLibrary();

		log.info("TC_09_5_Chon anh");
		OrderQRCode_Type2_Info qrCode = payQRCode.chooseQRCodeType2(numberOfImage);
		String provider = qrCode.provider;
		String service = qrCode.service;
		String codeCustomer = qrCode.codeCustomer;
		account = qrCode.account;

		log.info("TC_09_7_Lay so du");
		surplus = convertAvailableBalanceCurrentcyOrFeeToLong(qrCode.surplus);

		log.info("TC_09_09_Kiem tra man hinh xac nhan thong tin");
		payQRCode.scrollUpToText(driver, PayQRCode_Data.SOURCE_ACCOUNT);

		log.info("TC_09_09_1_Kiem tra tai khoan nguon");
		verifyEquals(payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.SOURCE_ACCOUNT), account);

		log.info("TC_09_09_2_Kiem tra nha cung cap");
		verifyEquals(payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.PROVIDER), provider);

		log.info("TC_09_09_3_Kiem tra dich vu");
		verifyEquals(payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.SERVICE), service);

		log.info("TC_09_09_4_Kiem tra ma khach hang");
		verifyEquals(payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.CUSTOMER_CODE), codeCustomer);

		money = convertAvailableBalanceCurrentcyOrFeeToLong(payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.AMOUNT_PAYMENT));

		log.info("TC_09_10_Chon phuong thuc xac thuc");
		payQRCode.scrollDownToText(driver, PayQRCode_Data.ACCURACY_METHOD);
		payQRCode.clickToDynamicDropDown(driver, PayQRCode_Data.ACCURACY_METHOD);
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.VCB_SMART_OTP);

		log.info("TC_09_11_Kiem tra so tien phi");
		transferFee = 0;
		
		log.info("TC_09_12_Click Tiep tuc");
		payQRCode.clickToDynamicButton(driver, PayQRCode_Data.CONTINUE_BUTTON);

		payQRCode.inputToDynamicSmartOTP(driver, LogIn_Data.Login_Account.Smart_OTP, "com.VCB:id/otp");

		payQRCode.clickToDynamicButton(driver, PayQRCode_Data.CONTINUE_BUTTON);
		
		payQRCode.clickToDynamicButton(driver, PayQRCode_Data.CONTINUE_BUTTON);

		log.info("TC_09_13: Kiem  tra giao dich thanh cong");
		verifyTrue(payQRCode.isDynamicMessageAndLabelTextDisplayed(driver, PayQRCode_Data.SUCCESS_TRANSACTION));

		log.info("TC_09_14: Kiem  tra so tien giao dich");
		verifyTrue(payQRCode.isDynamicMessageAndLabelTextDisplayed(driver, addCommasToLong(money + "") + " VND"));

		log.info("TC_09_15: Lay thoi gian tao giao dich");
		transferTime = payQRCode.getTransferTimeSuccess(driver, PayQRCode_Data.SUCCESS_TRANSACTION);

		log.info("TC_09_16: Lay ma giao dich");
		transactionNumber = payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.TRANSFER_CODE);

		log.info("TC_09_19: Click thuc hien giao dich moi");
		payQRCode.clickToDynamicButton(driver, PayQRCode_Data.PERFORM_OTHER_TRANSACTION);

		log.info("TC_09_20_Chon tai khoan nguon, kiem tra tai khoan chuyen bi tru tien");
		payQRCode.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
		payQRCode.scrollDownToText(driver, PayQRCode_Data.STATUS_TRANSFER_MONEY);
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.TRANSFER_MONEY_CHARITY);
		payQRCode.scrollUpToText(driver, PayQRCode_Data.SOURCE_ACCOUNT);
		payQRCode.clickToDynamicDropDown(driver, PayQRCode_Data.SOURCE_ACCOUNT);
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, account);
		payQRCode.scrollUpToText(driver, PayQRCode_Data.SOURCE_ACCOUNT);
		actualAvailableBalance = convertAvailableBalanceCurrentcyOrFeeToLong(payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.AVAILABLE_BALANCE));
		availableBalance = canculateAvailableBalances(surplus, money, transferFee);
		verifyEquals(actualAvailableBalance, availableBalance);

	}

	@Test
	public void TC_10_ThanhToanHoaDon_Type2_QRCode_BaoCao_SmartOTP() {
		if (QRCodePageObject.checkTestScriptFailed) {
			QRCodePageObject.checkTestScriptFailed = false;
			throw new SkipException("Khong ton tai bao cao cua giao dich nay");
		}
		
		log.info("TC_10_01: Click quay lai");
		payQRCode.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_10_02: Click vao Menu Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_10_03: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransactionReport_Data.ReportTitle.TRANSACTION_REPORT);

		log.info("TC_10_04: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransactionReport_Data.ReportTitle.ALL_TYPE_TRANSACTION);

		log.info("TC_10_05: Chon Thanh toan QR Code");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.PAY_QR_CODE);

		log.info("TC_10_06: Click Chon Tai Khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_10_07: Chon tai khoan nguon");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, account);

		log.info("TC_10_08: CLick Tim Kiem");
		transReport.clickToDynamicButton(driver, PayQRCode_Data.SEARCH_BUTTON);

		log.info("TC_10_09: Lay ngay tao giao dich hien thi");
		String transferTimeInReport = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_10_10: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_10_11: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(PayQRCode_Data.QR_PAY_UPPERCASE));

		log.info("TC_10_12: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), "- " +  addCommasToLong(money + "") + " VND");

		log.info("TC_10_13: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_10_14: Kiem tra thoi gian tao giao dich hien thi");
		String reportTime1 = transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TIME_TRANSACTION);
		verifyEquals(reportTime1, transferTimeInReport);

		log.info("TC_10_15: Kiem tra thoi gian tao giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_NUMBER), transactionNumber);

		log.info("TC_10_16: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.ACCOUNT_CARD), account);

		log.info("TC_10_17: Kiem tra so tai khoan ghi co");

		log.info("TC_10_18: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_MONEY).contains(addCommasToLong(money + "") + " VND"));

		log.info("TC_10_22: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_TYPE), PayQRCode_Data.PAY_QR_CODE);

		String note = "MBVCB." + transactionNumber + ".QR Pay.Thanh toan cho ";
		log.info("TC_10_23: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_CONTENT).contains(note));

		log.info("TC_10_24: Click  nut Back");
		transReport.clickToDynamicBackIcon(driver, TransactionReport_Data.ReportTitle.TRANSACTION_DETAIL);

		log.info("TC_10_25: Click  nut Back");
		transReport.clickToDynamicBackIcon(driver, TransactionReport_Data.ReportTitle.TRANSACTION_REPORT);

		log.info("TC_10_26: Click  nut Home");
		transReport.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");

	}

	@Test
	public void TC_11_ThanhToanHoaDon_Type3_QRCode_SmartOTP() {
		log.info("TC_11_1_Click QR Pay");
		homePage.sleep(driver, 3000);
		payQRCode.scrollUpToText(driver, PayQRCode_Data.QR_PAY_UPPERCASE);
		homePage.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.QR_PAY_UPPERCASE);

		log.info("TC_11_2_Click mo Thu vien anh");
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.IMAGE_LIBRARY);

		log.info("TC_11_3_Click chon Tat ca anh");
		payQRCode.clickToDynamicImageButtonByContentDesc(driver, PayQRCode_Data.TEXT_BUTTON_MENU);
		payQRCode.clickToDynamicTextContains(driver, PayQRCode_Data.TEXT_CATEGORY_MENU);
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.TYPE_3);

		log.info("TC_11_4_Lay so luong anh");
		int numberOfImage = payQRCode.getNumberOfImageInLibrary();

		log.info("TC_11_5_Chon anh");
		OrderQRCode_Type1_Info qrCode = payQRCode.chooseQRCodeType3(numberOfImage);
		String destinationPlace = qrCode.destinationPlace;
		String namePlace = qrCode.namePlace;
		account = qrCode.account;

		log.info("TC_11_7_Lay so du");
		surplus = convertAvailableBalanceCurrentcyOrFeeToLong(qrCode.surplus);

		log.info("TC_11_8_Nhap thong tin lien he");
		payQRCode.inputContactInfomation();

		log.info("TC_11_09_Kiem tra man hinh xac nhan thong tin");
		payQRCode.scrollUpToText(driver, PayQRCode_Data.SOURCE_ACCOUNT);

		log.info("TC_11_09_1_Kiem tra tai khoan nguon");
		verifyEquals(payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.SOURCE_ACCOUNT), account);

		log.info("TC_11_09_2_Kiem tra thanh toan cho");
		verifyEquals(payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.DESTINATION_PLACE), destinationPlace);

		log.info("TC_11_09_3_Kiem tra ten diem ban");
		verifyEquals(payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.NAME_PLACE), namePlace);

		money = convertAvailableBalanceCurrentcyOrFeeToLong(payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.AMOUNT_PAYMENT));

		log.info("TC_11_12_Chon phuong thuc xac thuc");
		payQRCode.scrollDownToText(driver, PayQRCode_Data.ACCURACY_METHOD);
		payQRCode.clickToDynamicDropDown(driver, PayQRCode_Data.ACCURACY_METHOD);
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.VCB_SMART_OTP);

		log.info("TC_11_11_Kiem tra so tien phi");
		transferFee = 0;
		
		log.info("TC_11_12_Click Tiep tuc");
		payQRCode.clickToDynamicButton(driver, PayQRCode_Data.CONTINUE_BUTTON);

		payQRCode.inputToDynamicSmartOTP(driver, LogIn_Data.Login_Account.Smart_OTP, "com.VCB:id/otp");

		payQRCode.clickToDynamicButton(driver, PayQRCode_Data.CONTINUE_BUTTON);
		
		payQRCode.clickToDynamicButton(driver, PayQRCode_Data.CONTINUE_BUTTON);

		log.info("TC_11_13: Kiem  tra giao dich thanh cong");
		verifyTrue(payQRCode.isDynamicMessageAndLabelTextDisplayed(driver, PayQRCode_Data.SUCCESS_PAYMENT));

		log.info("TC_11_14: Kiem  tra so tien giao dich");
		verifyTrue(payQRCode.isDynamicMessageAndLabelTextDisplayed(driver, addCommasToLong(money + "") + " VND"));

		log.info("TC_11_15: Lay thoi gian tao giao dich");
		transferTime = payQRCode.getTransferTimeSuccess(driver, PayQRCode_Data.SUCCESS_TRANSACTION);

		log.info("TC_11_16: Lay ma giao dich");
		transactionNumber = payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.TRANSFER_CODE);

		log.info("TC_11_17_Kiem tra nha cung cap");
		verifyEquals(payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.DESTINATION_PLACE), destinationPlace);

		log.info("TC_11_18_Kiem tra ten dich vu");
		verifyEquals(payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.PLACE_NAME), namePlace);

		log.info("TC_11_19: Click thuc hien giao dich moi");
		payQRCode.clickToDynamicButton(driver, PayQRCode_Data.PERFORM_OTHER_TRANSACTION);

		log.info("TC_11_20_Chon tai khoan nguon, kiem tra tai khoan chuyen bi tru tien");
		payQRCode.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
		payQRCode.scrollDownToText(driver, PayQRCode_Data.STATUS_TRANSFER_MONEY);
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.TRANSFER_MONEY_CHARITY);
		payQRCode.scrollUpToText(driver, PayQRCode_Data.SOURCE_ACCOUNT);
		payQRCode.clickToDynamicDropDown(driver, PayQRCode_Data.SOURCE_ACCOUNT);
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, account);
		payQRCode.scrollUpToText(driver, PayQRCode_Data.SOURCE_ACCOUNT);
		actualAvailableBalance = convertAvailableBalanceCurrentcyOrFeeToLong(payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.AVAILABLE_BALANCE));
		availableBalance = canculateAvailableBalances(surplus, money, transferFee);
		verifyEquals(actualAvailableBalance, availableBalance);

	}

	@Test
	public void TC_12_ThanhToanHoaDon_Type3_QRCode_SmartOTP_BaoCao() {
		log.info("TC_12_01: Click quay lai");
		payQRCode.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_12_02: Click vao Menu Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_12_03: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransactionReport_Data.ReportTitle.TRANSACTION_REPORT);

		log.info("TC_12_04: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransactionReport_Data.ReportTitle.ALL_TYPE_TRANSACTION);

		log.info("TC_12_05: Chon Thanh toan QR Code");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.PAY_QR_CODE);

		log.info("TC_12_06: Click Chon Tai Khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_12_07: Chon tai khoan nguon");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, account);

		log.info("TC_12_08: CLick Tim Kiem");
		transReport.clickToDynamicButton(driver, PayQRCode_Data.SEARCH_BUTTON);

		log.info("TC_12_09: Lay ngay tao giao dich hien thi");
		String transferTimeInReport = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_12_10: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_12_11: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(PayQRCode_Data.QR_PAY_UPPERCASE));

		log.info("TC_12_12: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), "- " +  addCommasToLong(money + "") + " VND");

		log.info("TC_12_13: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_12_14: Kiem tra thoi gian tao giao dich hien thi");
		String reportTime1 = transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TIME_TRANSACTION);
		verifyEquals(reportTime1, transferTimeInReport);

		log.info("TC_12_15: Kiem tra thoi gian tao giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_NUMBER), transactionNumber);

		log.info("TC_12_16: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.ACCOUNT_CARD), account);

		log.info("TC_12_17: Kiem tra so tai khoan ghi co");

		log.info("TC_12_18: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_MONEY).contains(addCommasToLong(money + "") + " VND"));

		log.info("TC_12_22: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_TYPE), PayQRCode_Data.PAY_QR_CODE);

		String note = "MBVCB." + transactionNumber + ".QR Pay.Thanh toan cho ";
		log.info("TC_12_23: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_CONTENT).contains(note));

		log.info("TC_12_24: Click  nut Back");
		transReport.clickToDynamicBackIcon(driver, TransactionReport_Data.ReportTitle.TRANSACTION_DETAIL);

		log.info("TC_12_25: Click  nut Back");
		transReport.clickToDynamicBackIcon(driver, TransactionReport_Data.ReportTitle.TRANSACTION_REPORT);

		log.info("TC_12_26: Click  nut Home");
		transReport.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();
	}

}
