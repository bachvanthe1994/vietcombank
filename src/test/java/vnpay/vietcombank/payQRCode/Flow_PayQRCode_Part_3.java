package vnpay.vietcombank.payQRCode;

import java.io.IOException;
import java.net.MalformedURLException;

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
import pageObjects.QRCodePageObject;
import pageObjects.TransactionReportPageObject;
import vietcombank_test_data.PayQRCode_Data;
import vietcombank_test_data.TransactionReport_Data;

public class Flow_PayQRCode_Part_3 extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private QRCodePageObject payQRCode;
	private TransactionReportPageObject transReport;
	private String transferTime;
	private String transactionNumber;
	long transferFee = 0;
	String password, device, id, urlServer, phoneNumber, otpNumber = "";
	SourceAccountModel sourceAccount = new SourceAccountModel();

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp", "platformVersion"})
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String otp, String platformVersion) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		if (deviceType.contains("android")) {
			driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		} else if (deviceType.contains("ios")) {
			driver = openIOSApp(deviceName, udid, url);
		}
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, otp);
		password = pass;
		device = deviceName;
		id = udid;
		urlServer = url;
		phoneNumber = phone;
		otpNumber = otp;
		
		log.info("TC_00_1_Tao ma QR code type 4");
		driver = openAndroidBrowser("real", "chrome", platformVersion);
		homePage = PageFactoryManager.getHomePageObject(driver);
		payQRCode = PageFactoryManager.getQRCodePageObject(driver);
		login = PageFactoryManager.getLoginPageObject(driver);
		
		payQRCode.navigateToURL(driver, PayQRCode_Data.LINK_CREATE_ORDER);
		payQRCode.clickToDynamicSpinner(PayQRCode_Data.NO_SELECT);
		payQRCode.clickToDynamicCheckedTextView(PayQRCode_Data.VNPAYQR);
		payQRCode.clickToDynamicSpinner(PayQRCode_Data.VIETNAMESE);
		payQRCode.clickToDynamicCheckedTextView(PayQRCode_Data.VIETNAMESE);
		payQRCode.clickToDynamicButton(driver, PayQRCode_Data.PAY_POPUP);
		payQRCode.waitForTextViewDisplay("qrcode");
		payQRCode.scrollIDownOneTime(driver);
		payQRCode.clickToDynamicViewByText(PayQRCode_Data.VIETCOMBANK);
		
	}

	private long surplus, availableBalance, actualAvailableBalance;

	String account, codeOrder = "";
	long money = 0;
	@Test
	public void TC_01_ThanhToanHoaDon_Type4_QRCode() {
		log.info("TC_01_1_Chon tai khoan nguon");
		payQRCode.clickToDynamicImageViewByID(driver, "com.VCB:id/ivContent");
		sourceAccount = payQRCode.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);
		account = sourceAccount.account;

		surplus = convertAvailableBalanceCurrentcyOrFeeToLong(payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.AVAILABLE_BALANCE));
		
		log.info("TC_01_2_Lay thong tin hoa don");
		String destinationPlace = payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.DESTINATION_PLACE);
		String namePlace = payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.NAME_PLACE);
		codeOrder = payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.ORDER_CODE);
		money = convertAvailableBalanceCurrentcyOrFeeToLong(payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.MONEY_TRANSFER));
		
		log.info("TC_01_3_Kiem tra mo ta");
		verifyEquals(payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.DESCRIPTION), PayQRCode_Data.PAY_QR);
		
		log.info("TC_01_4_Click Thanh toan");
		payQRCode.clickToDynamicButton(driver, PayQRCode_Data.PAYMENT_BUTTON);
		
		log.info("TC_01_11_Kiem tra man hinh xac nhan thong tin");
		payQRCode.scrollUpToText(driver, PayQRCode_Data.SOURCE_ACCOUNT);

		log.info("TC_01_11_1_Kiem tra tai khoan nguon");
		verifyEquals(payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.SOURCE_ACCOUNT), account);

		log.info("TC_01_11_2_Kiem tra thanh toan cho");
		verifyEquals(payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.DESTINATION_PLACE), destinationPlace);

		log.info("TC_01_11_3_Kiem tra ten diem ban");
		verifyEquals(payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.NAME_PLACE), namePlace);

		log.info("TC_01_11_4_Kiem tra thanh toan cho");
		verifyEquals(payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.ORDER_CODE), codeOrder);

		log.info("TC_01_11_5_Kiem tra so tien");
		verifyTrue(payQRCode.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_MONEY).contains(addCommasToLong(money + "") + " VND"));

		log.info("TC_01_12_Chon phuong thuc xac thuc");
		payQRCode.scrollDownToText(driver, PayQRCode_Data.ACCURACY_METHOD);
		payQRCode.clickToDynamicDropDown(driver, PayQRCode_Data.ACCURACY_METHOD);
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.SMS_OTP);

		log.info("TC_01_12_01_Kiem tra so tien phi");
		transferFee = convertAvailableBalanceCurrentcyOrFeeToLong(payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.FEE_TRANSACTION));

		log.info("TC_01_13_Click Tiep tuc");
		payQRCode.clickToDynamicButton(driver, PayQRCode_Data.CONTINUE_BUTTON);

		payQRCode.inputToDynamicOtp(driver, otpNumber, PayQRCode_Data.CONTINUE_BUTTON);

		payQRCode.clickToDynamicButton(driver, PayQRCode_Data.CONTINUE_BUTTON);

		log.info("TC_01_15: Kiem  tra so tien giao dich");
		verifyTrue(payQRCode.isDynamicMessageAndLabelTextDisplayed(driver, addCommasToLong(money + "") + " VND"));

		log.info("TC_01_16: Lay ma giao dich");
		transactionNumber = payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.TRANSFER_CODE);

	}

	@Test
	public void TC_02_ThanhToanHoaDon_Type4_QRCode_BaoCao() throws MalformedURLException {
		log.info("TC_02_00: Mo app va login");
		driver = openIOSApp(device, id, urlServer);
		login = PageFactoryManager.getLoginPageObject(driver);
		log.info("Before class: Log in ");
		login.Global_login(phoneNumber, password, otpNumber);
		
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
		actualAvailableBalance = convertAvailableBalanceCurrentcyOrFeeToLong(payQRCode.getDynamicTextInTransactionDetail(driver, account));

		log.info("TC_02_07: Chon tai khoan nguon");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, account);
		availableBalance = canculateAvailableBalances(surplus, money, transferFee);
		verifyEquals(actualAvailableBalance, availableBalance);
		

		log.info("TC_02_08: CLick Tim Kiem");
		transReport.clickToDynamicButton(driver, PayQRCode_Data.SEARCH_BUTTON);

		log.info("TC_02_09: Lay ngay tao giao dich hien thi");
		String transferTimeInReport = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_02_10: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_02_11: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").contains(PayQRCode_Data.QR_PAY));

		log.info("TC_02_12: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvContent"), "- " +  addCommasToLong(PayQRCode_Data.MONEY_VND) + " VND");

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
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_MONEY).contains(addCommasToLong(PayQRCode_Data.MONEY_VND) + " VND"));

		log.info("TC_02_19: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_TYPE), PayQRCode_Data.PAY_QR_CODE);

		String note = "MBVCB" + transactionNumber + ".QR Pay.Thanh toan cho " + codeOrder; 
		log.info("TC_02_20: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_CONTENT).contains(note));

		log.info("TC_02_21: Click  nut Back");
		transReport.clickToDynamicBackIcon(driver, TransactionReport_Data.ReportTitle.TRANSACTION_DETAIL);

		log.info("TC_02_22: Click  nut Back");
		transReport.clickToDynamicBackIcon(driver, TransactionReport_Data.ReportTitle.TRANSACTION_REPORT);

		log.info("TC_02_23: Click  nut Home");
		transReport.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
		
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();
	}

}
