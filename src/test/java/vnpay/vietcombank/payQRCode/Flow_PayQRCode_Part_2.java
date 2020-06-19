package vnpay.vietcombank.payQRCode;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import model.OrderQRCode_Type1_Info;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.QRCodePageObject;
import pageObjects.TransactionReportPageObject;
import vietcombank_test_data.PayQRCode_Data;
import vietcombank_test_data.TransactionReport_Data;
import vietcombank_test_data.Notify_Management_Data.Notify_Text;

public class Flow_PayQRCode_Part_2 extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private QRCodePageObject payQRCode;
	private TransactionReportPageObject transReport;
	private String transferTime;
	private String transactionNumber;
	long money,transferFee = 0;
	String password, otpNumber = "";

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String otp) throws IOException, InterruptedException {
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
		otpNumber = otp;

		homePage = PageFactoryManager.getHomePageObject(driver);
		payQRCode = PageFactoryManager.getQRCodePageObject(driver);

	}

	private long surplus, availableBalance, actualAvailableBalance;

	String account, namePlace,codeOrder = "";
	@Test
	public void TC_01_ThanhToanHoaDon_VCB_Type1_QRCode() {
		
		log.info("TC_01_1_Click QR Pay");
		homePage.sleep(driver, 3000);
		homePage.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.QR_PAY);

		log.info("TC_01_2_Click mo Thu vien anh");
		payQRCode.clickToDynamicAcceptButtonContainOR(driver, "com.android.packageinstaller:id/permission_allow_button", "com.android.permissioncontroller:id/permission_allow_button");
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, "Thư viện ảnh");

		log.info("TC_01_3_Click chon Tat ca anh");
		payQRCode.clickToDynamicImageButtonByContentDesc(driver, PayQRCode_Data.TEXT_BUTTON_MENU);
		payQRCode.clickToDynamicTextContains(driver, PayQRCode_Data.TEXT_CATEGORY_MENU);
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.VCB_TYPE_1);

		log.info("TC_01_4_Lay so luong anh");
		int numberOfImage = payQRCode.getNumberOfImageInLibrary();;

		log.info("TC_01_5_Chon anh");
		OrderQRCode_Type1_Info qrCode = payQRCode.chooseQRCodeType1(numberOfImage);
		String destinationPlace = qrCode.destinationPlace;
		namePlace = qrCode.namePlace;
		String codePlace = qrCode.codePlace;
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
		verifyTrue(payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.MONEY_NEEDED_TRANSFER).contains(addCommasToLong(PayQRCode_Data.MONEY_VND) + " VND"));

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

		log.info("TC_01_14: Kiem  tra giao dich thanh cong");
		verifyTrue(payQRCode.isDynamicMessageAndLabelTextDisplayed(driver, PayQRCode_Data.SUCCESS_TRANSACTION));

		log.info("TC_01_15: Kiem  tra so tien giao dich");
		verifyTrue(payQRCode.isDynamicMessageAndLabelTextDisplayed(driver, addCommasToLong(PayQRCode_Data.MONEY_VND) + " VND"));

		log.info("TC_01_16: Lay thoi gian tao giao dich");
		transferTime = payQRCode.getTransferTimeSuccess(driver,  PayQRCode_Data.SUCCESS_TRANSACTION);

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
		availableBalance = canculateAvailableBalances(surplus, Long.parseLong(PayQRCode_Data.MONEY_VND), transferFee);
		verifyEquals(actualAvailableBalance, availableBalance);

	}

	@Test
	public void TC_02_ThanhToanHoaDon_VCB_Type1_QRCode_BaoCao() {
		
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
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").contains(PayQRCode_Data.QR_PAY));

		log.info("TC_02_12: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), "- " +  addCommasToLong(PayQRCode_Data.MONEY_VND) + " VND");

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

		log.info("TC_02_22: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_TYPE), PayQRCode_Data.PAY_QR_CODE);

		String note = "MBVCB" + transactionNumber + ".QR Pay.Thanh toan cho " + codeOrder; 
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
	public void TC_03_KiemTra_HienThiThongBao_DaDangNhap() {
		
		log.info("TC_03_Step_01: Click vao Inbox");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_3");
		
		log.info("TC_03_Step_02: Click vao tab Tat ca");
		homePage.clickToTextID(driver, "com.VCB:id/radioAll");
		
		log.info("TC_03_Step_03: Lay du lieu hien thi");
		String inboxContent = homePage.getDynamicTextByContentID(driver, "com.VCB:id/recycleview","com.VCB:id/content",codeOrder);
		
		log.info("TC_03_Step_04: So sanh thong tin thanh toan");
		verifyTrue(inboxContent.contains(codeOrder));
		verifyTrue(inboxContent.contains(addCommasToLong(PayQRCode_Data.MONEY_VND) + " VND"));
		verifyTrue(inboxContent.contains(namePlace));
		
		log.info("TC_03_Step_05: Click vao tab Khac");
		homePage.clickToTextID(driver, "com.VCB:id/radioOther");
		
		log.info("TC_03_Step_06: Lay du lieu hien thi");
		inboxContent = homePage.getDynamicTextByContentID(driver, "com.VCB:id/recycleview","com.VCB:id/content",codeOrder);
		
		log.info("TC_03_Step_07: So sanh thong tin thanh toan");
		verifyTrue(inboxContent.contains(codeOrder));
		verifyTrue(inboxContent.contains(addCommasToLong(PayQRCode_Data.MONEY_VND) + " VND"));
		verifyTrue(inboxContent.contains(namePlace));
		
		log.info("TC_03_Step_08: Mo tab Home");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}
	
	@Test
	public void TC_04_ThanhToanHoaDon_VCB_Type3_QRCode() {
		
		log.info("TC_04_1_Click QR Pay");
		homePage.sleep(driver, 3000);
		payQRCode.scrollUpToText(driver, PayQRCode_Data.QR_PAY);
		homePage.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.QR_PAY);

		log.info("TC_04_2_Click mo Thu vien anh");
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, "Thư viện ảnh");

		log.info("TC_04_3_Click chon Tat ca anh");
		payQRCode.clickToDynamicImageButtonByContentDesc(driver, PayQRCode_Data.TEXT_BUTTON_MENU);
		payQRCode.clickToDynamicTextContains(driver, PayQRCode_Data.TEXT_CATEGORY_MENU);
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.VCB_TYPE_3);

		log.info("TC_04_4_Lay so luong anh");
		int numberOfImage = payQRCode.getNumberOfImageInLibrary();

		log.info("TC_04_5_Chon anh");
		OrderQRCode_Type1_Info qrCode = payQRCode.chooseQRCodeType3(numberOfImage);
		String destinationPlace = qrCode.destinationPlace;
		namePlace = qrCode.namePlace;
		account = qrCode.account;

		log.info("TC_04_7_Lay so du");
		surplus = convertAvailableBalanceCurrentcyOrFeeToLong(qrCode.surplus);
		
		log.info("TC_04_8_Nhap thong tin lien he");
		payQRCode.inputContactInfomation();

		log.info("TC_04_09_Kiem tra man hinh xac nhan thong tin");
		payQRCode.scrollUpToText(driver, PayQRCode_Data.SOURCE_ACCOUNT);

		log.info("TC_04_09_1_Kiem tra tai khoan nguon");
		verifyEquals(payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.SOURCE_ACCOUNT), account);

		log.info("TC_04_09_2_Kiem tra thanh toan cho");
		verifyEquals(payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.DESTINATION_PLACE), destinationPlace);
		
		log.info("TC_04_09_3_Kiem tra ten diem ban");
		verifyEquals(payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.NAME_PLACE), namePlace);
		codeOrder = payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.ORDER_CODE);

		money = convertAvailableBalanceCurrentcyOrFeeToLong(payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.AMOUNT_PAYMENT));

		log.info("TC_04_12_Chon phuong thuc xac thuc");
		payQRCode.scrollDownToText(driver, PayQRCode_Data.ACCURACY_METHOD);
		payQRCode.clickToDynamicDropDown(driver, PayQRCode_Data.ACCURACY_METHOD);
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.SMS_OTP);

		log.info("TC_04_11_Kiem tra so tien phi");
		transferFee = convertAvailableBalanceCurrentcyOrFeeToLong(payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.FEE_TRANSACTION));

		log.info("TC_04_12_Click Tiep tuc");
		payQRCode.clickToDynamicButton(driver, PayQRCode_Data.CONTINUE_BUTTON);

		payQRCode.inputToDynamicOtp(driver, otpNumber, PayQRCode_Data.CONTINUE_BUTTON);

		payQRCode.clickToDynamicButton(driver, PayQRCode_Data.CONTINUE_BUTTON);

		log.info("TC_04_13: Kiem  tra giao dich thanh cong");
		verifyTrue(payQRCode.isDynamicMessageAndLabelTextDisplayed(driver, PayQRCode_Data.SUCCESS_TRANSACTION));

		log.info("TC_04_14: Kiem  tra so tien giao dich");
		verifyTrue(payQRCode.isDynamicMessageAndLabelTextDisplayed(driver, addCommasToLong(money + "") + " VND"));

		log.info("TC_04_15: Lay thoi gian tao giao dich");
		transferTime = payQRCode.getTransferTimeSuccess(driver, PayQRCode_Data.SUCCESS_TRANSACTION);

		log.info("TC_04_16: Lay ma giao dich");
		transactionNumber = payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.TRANSFER_CODE);

		log.info("TC_04_17_Kiem tra nha cung cap");
		verifyEquals(payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.DESTINATION_PLACE), destinationPlace);

		log.info("TC_04_18_Kiem tra ten dich vu");
		verifyEquals(payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.PLACE_NAME), namePlace);

		log.info("TC_04_19: Click thuc hien giao dich moi");
		payQRCode.clickToDynamicButton(driver, PayQRCode_Data.PERFORM_OTHER_TRANSACTION);

		log.info("TC_04_20_Chon tai khoan nguon, kiem tra tai khoan chuyen bi tru tien");
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
	public void TC_05_ThanhToanHoaDon_VCB_Type3_QRCode_BaoCao() {
		
		log.info("TC_05_01: Click quay lai");
		payQRCode.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_05_02: Click vao Menu Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_05_03: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransactionReport_Data.ReportTitle.TRANSACTION_REPORT);

		log.info("TC_05_04: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransactionReport_Data.ReportTitle.ALL_TYPE_TRANSACTION);

		log.info("TC_05_05: Chon Thanh toan QR Code");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.PAY_QR_CODE);

		log.info("TC_05_06: Click Chon Tai Khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_05_07: Chon tai khoan nguon");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, account);

		log.info("TC_05_08: CLick Tim Kiem");
		transReport.clickToDynamicButton(driver, PayQRCode_Data.SEARCH_BUTTON);

		log.info("TC_05_09: Lay ngay tao giao dich hien thi");
		String transferTimeInReport = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
		
		log.info("TC_05_10: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_05_11: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(PayQRCode_Data.QR_PAY));

		log.info("TC_05_12: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), "- " +  addCommasToLong(PayQRCode_Data.MONEY_VND) + " VND");

		log.info("TC_05_13: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_05_14: Kiem tra thoi gian tao giao dich hien thi");
		String reportTime1 = transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TIME_TRANSACTION);
		verifyEquals(reportTime1, transferTimeInReport);

		log.info("TC_05_15: Kiem tra thoi gian tao giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_NUMBER), transactionNumber);

		log.info("TC_05_16: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.ACCOUNT_CARD), account);

		log.info("TC_05_17: Kiem tra so tai khoan ghi co");

		log.info("TC_05_18: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_MONEY).contains(addCommasToLong(PayQRCode_Data.MONEY_VND) + " VND"));

		log.info("TC_05_22: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_TYPE), PayQRCode_Data.PAY_QR_CODE);

		String note = "MBVCB" + transactionNumber + ".QR Pay.Thanh toan cho " + codeOrder; 
		log.info("TC_05_23: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_CONTENT).contains(note));

		log.info("TC_05_24: Click  nut Back");
		transReport.clickToDynamicBackIcon(driver, TransactionReport_Data.ReportTitle.TRANSACTION_DETAIL);

		log.info("TC_05_25: Click  nut Back");
		transReport.clickToDynamicBackIcon(driver, TransactionReport_Data.ReportTitle.TRANSACTION_REPORT);

		log.info("TC_05_26: Click  nut Home");
		transReport.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
		
	}
	
	@Parameters ({"pass"})
	@Test
	public void TC_06_KiemTra_HienThiThongBao_ChuaDangNhap(String password) {
		
		log.info("TC_06_Step_01: Click vao Menu");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");
		
		log.info("TC_06_Step_02: Click vao Thoat Ung Dung");
		homePage.scrollUpToText(driver, Notify_Text.LOG_OUT_TEXT);
		homePage.clickToDynamicButtonLinkOrLinkTextNotScroll(driver, Notify_Text.LOG_OUT_TEXT);
		
		log.info("TC_06_Step_03: Click vao Dong y");
		homePage.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
		
		log.info("TC_06_Step_04: Click vao Inbox");
		homePage.clickToDynamicImageView(driver, "com.VCB:id/ivOTT");
		
		log.info("TC_06_Step_05: Click vao tab Tat ca");
		homePage.clickToTextID(driver, "com.VCB:id/radioAll");
		
		log.info("TC_06_Step_06: Lay du lieu hien thi");
		String inboxContent = homePage.getDynamicTextByContentID(driver, "com.VCB:id/recycleview","com.VCB:id/content",codeOrder);
		
		log.info("TC_06_Step_07: So sanh thong tin thanh toan");
		verifyTrue(inboxContent.contains(codeOrder));
		verifyTrue(inboxContent.contains(addCommasToLong(money+"")+" VND"));
		verifyTrue(inboxContent.contains(namePlace));
		
		log.info("TC_06_Step_08: Click vao tab Khac");
		homePage.clickToTextID(driver, "com.VCB:id/radioOther");
		
		log.info("TC_06_Step_09: Lay du lieu hien thi");
		inboxContent = homePage.getDynamicTextByContentID(driver, "com.VCB:id/recycleview","com.VCB:id/content",codeOrder);

		log.info("TC_06_Step_10: So sanh thong tin thanh toan");
		verifyTrue(inboxContent.contains(codeOrder));
		verifyTrue(inboxContent.contains(addCommasToLong(money+"")+" VND"));
		verifyTrue(inboxContent.contains(namePlace));
		
		log.info("TC_06_Step_11: Back ve man Log In");
		homePage.clickToDynamicImageView(driver, "com.VCB:id/back");
		
		log.info("TC_06_Step_12: Nhap mat khau va dang nhap");
		homePage.inputIntoEditTextByID(driver, password, "com.VCB:id/edtInput");
		homePage.clickToDynamicAcceptButton(driver, "com.VCB:id/btnNext");
		
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();
	}

}
