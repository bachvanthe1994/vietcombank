package vnpay.vietcombank.landLinePhoneCharge;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
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
import pageObjects.LandLinePhoneChargePageObject;
import pageObjects.LogInPageObject;
import pageObjects.TransactionReportPageObject;
import vietcombank_test_data.LandLinePhoneCharge_Data;
import vietcombank_test_data.LandLinePhoneCharge_Data.Text_Data;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransactionReport_Data.ReportTitle;

public class Flow_LandLinePhoneCharge_Part_2 extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private LandLinePhoneChargePageObject landLinePhoneCharge;
	private TransactionReportPageObject transReport;
	private String transferTime;
	private String transactionNumber;
	long fee, money = 0;
	String password = "";
	SourceAccountModel sourceAccount = new SourceAccountModel();
	List<String> notLine = new ArrayList<String>();
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
		
		notLine = Arrays.asList(getDataInCell(11).split(";"));
	}

	private long surplus, availableBalance, actualAvailableBalance;

	@Test
	public void TC_01_ThanhToanCuocDienThoaiCoDinh_CoDinhKhongDay_ThanhToanMatKhauDangNhap() {
		homePage = PageFactoryManager.getHomePageObject(driver);
		landLinePhoneCharge = PageFactoryManager.getLandLinePhoneChargePageObject(driver);

		log.info("TC_01_01_Click Cuoc dien thoai co dinh");
		landLinePhoneCharge.scrollDownToText(driver, Text_Data.PRICE_CABLE);
		homePage.clickToDynamicButtonLinkOrLinkText(driver, Text_Data.LANDLINE_TELEPHONE);

		log.info("TC_01_02_Chon tai khoan nguon");
		landLinePhoneCharge.clickToDynamicDropDown(driver, Text_Data.SOURCE_ACCOUNT);
		sourceAccount = landLinePhoneCharge.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);
		surplus = convertAvailableBalanceCurrentcyOrFeeToLong(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, Text_Data.AVAILABLE_BALANCES));

		log.info("TC_01_03_Chon loai cuoc thanh toan");

		landLinePhoneCharge.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/wrap_tv");
		landLinePhoneCharge.clickToDynamicTextContains(driver, Text_Data.NOT_LINE);

		log.info("TC_01_04_Nhap so dien thoai tra cuoc va bam Tiep tuc");
		landLinePhoneCharge.inputPhoneNumberLandLinePhoneCharge(notLine);

		money = convertAvailableBalanceCurrentcyOrFeeToLong(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, Text_Data.AMOUNT_PAY));
		log.info("TC_01_05_Kiem tra man hinh xac nhan thong tin");
		log.info("TC_01_05_1_Kiem tra tai khoan nguon");
		verifyEquals(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, Text_Data.SOURCE_ACCOUNT), sourceAccount.account);

		log.info("TC_01_05_2_Kiem tra dich vu");
		verifyEquals(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, ReportTitle.SERVICE), Text_Data.LANDLINE_TELEPHONE);

		log.info("TC_01_05_3_Kiem tra nha cung cap");
		verifyEquals(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, ReportTitle.SUPPLIER), Text_Data.NOT_LINE_VIETTEL);

		log.info("TC_01_05_4_Kiem tra so dien thoai");
		verifyEquals(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, Text_Data.PHONE_NUMBER), LandLinePhoneChargePageObject.phoneNumber);

		log.info("TC_01_06_Chon phuong thuc xac thuc");
		landLinePhoneCharge.clickToDynamicButtonLinkOrLinkText(driver, Text_Data.PASSWORD_LOGIN);
		landLinePhoneCharge.clickToDynamicButtonLinkOrLinkText(driver, Text_Data.PASSWORD_LOGIN);

		log.info("TC_01_07_01_Kiem tra so tien phi");
		fee = convertAvailableBalanceCurrentcyOrFeeToLong(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, Text_Data.AMOUNT_FEE));

		log.info("TC_01_08_Click Tiep tuc");
		landLinePhoneCharge.clickToDynamicButton(driver, Text_Data.CONTINUE);

		log.info("TC_01_09_Nhap mat khau");
		landLinePhoneCharge.inputToDynamicPopupPasswordInput(driver, password, Text_Data.CONTINUE);
		landLinePhoneCharge.clickToDynamicButton(driver, Text_Data.CONTINUE);

		log.info("TC_01_10_Kiem tra man hinh Chuyen khoan thanh cong");
		log.info("TC_01_10_1_Kiem tra Chuyen khoan thanh cong");
		verifyTrue(landLinePhoneCharge.isDynamicMessageAndLabelTextDisplayed(driver, LandLinePhoneCharge_Data.SUCCESS_TRANSFER_MONEY));

		log.info("TC_01_10_2_Kiem tra dich vu");
		verifyEquals(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, ReportTitle.SERVICE), Text_Data.LANDLINE_TELEPHONE);

		log.info("TC_01_10_3_Kiem tra nha cung cap");
		verifyEquals(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, ReportTitle.SUPPLIER), Text_Data.NOT_LINE_VIETTEL);

		log.info("TC_01_10_3_So dien thoai");
		verifyEquals(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, Text_Data.PHONE_NUMBER), LandLinePhoneChargePageObject.phoneNumber);

		log.info("TC_01_10_4_Kiem tra nut Thuc hien giao dich moi");
		verifyTrue(landLinePhoneCharge.isDynamicButtonDisplayed(driver, Text_Data.NEW_TRANSFER));

		log.info("TC_01_10_5_Lay ma giao dich");
		transferTime = landLinePhoneCharge.getTransferTimeSuccess(driver, LandLinePhoneCharge_Data.SUCCESS_TRANSFER_MONEY);
		transactionNumber = landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, Text_Data.CODE_TRANSFER);

		log.info("TC_01_11_Click Thuc hien giao dich moi");
		landLinePhoneCharge.clickToDynamicButton(driver, Text_Data.NEW_TRANSFER);

		log.info("TC_01_11_Kiem tra so du kha dung luc sau");
		landLinePhoneCharge.clickToDynamicDropDown(driver, Text_Data.SOURCE_ACCOUNT);
		sourceAccount = landLinePhoneCharge.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		actualAvailableBalance = convertAvailableBalanceCurrentcyOrFeeToLong(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, Text_Data.AVAILABLE_BALANCES));
		availableBalance = canculateAvailableBalances(surplus, money, fee);
		verifyEquals(actualAvailableBalance, availableBalance);

	}

	@Test
	public void TC_02_ThanhToanCuocDienThoaiCoDinh_CoDinhKhongDay_BaoCao() {
		log.info("TC_02_1: Click  nut Back");
		landLinePhoneCharge.clickToBackIconOnLandLinePhoneChargeScreen(Text_Data.LANDLINE_TELEPHONE);

		log.info("TC_02_2: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_02_3: Click Bao Cao giao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_02_4: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.ALL_TYPE_TRANSACTION);

		log.info("TC_02_5: Chon Thanh toan hoa don");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.PAYMENT_BILLING);

		log.info("TC_02_6: Click Chon Tai Khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_02_7: Chon tai Khoan chuyen");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_02_8: Click Tim Kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_02_9: Kiem tra ngay tao giao dich hien thi");
		String reportTime1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
		verifyEquals(convertDateTimeIgnoreHHmmss(reportTime1), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_02_10: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(money + "") + " VND"));

		log.info("TC_02_11: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_02_12: Kiem tra thoi gian tao giao dich hien thi");
		String reportTime2 = transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TIME_TRANSACTION);
		verifyEquals(reportTime2, reportTime1);

		log.info("TC_02_13: Kiem tra ma giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_NUMBER), transactionNumber);

		log.info("TC_02_14: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_CARD), sourceAccount.account);

		log.info("TC_02_15_Kiem tra dich vu");
		verifyEquals(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, ReportTitle.SERVICE), Text_Data.LANDLINE_TELEPHONE);

		log.info("TC_02_16_Kiem tra nha cung cap");
		verifyEquals(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, ReportTitle.SUPPLIER), Text_Data.NOT_LINE_VIETTEL);

		log.info("TC_02_18: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.AMOUNT_TRANSFER).contains(addCommasToLong(money + "") + " VND"));

		log.info("TC_02_20: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TYPE_TRANSFER), ReportTitle.PAYMENT_BILLING);

		log.info("TC_02_21: Kiem Tra noi dung giao dich");
		String note = "MBVCB" + transactionNumber ;
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.CONTENT_TRANSFER).contains(note));

		log.info("TC_02_22: Click  nut Back");
		landLinePhoneCharge.clickToDynamicBackIcon(driver, ReportTitle.DETAIL_TRANSFER);

		log.info("TC_02_23: Click  nut Back");
		landLinePhoneCharge.clickToDynamicBackIcon(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_02_24: Click  nut Home");
		landLinePhoneCharge.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@Test
	public void TC_03_ThanhToanCuocDienThoaiCoDinh_CoDinhCoDay_ThanhToanSMSOTP() {
		homePage = PageFactoryManager.getHomePageObject(driver);
		landLinePhoneCharge = PageFactoryManager.getLandLinePhoneChargePageObject(driver);

		log.info("TC_03_01_Click Cuoc dien thoai co dinh");
		landLinePhoneCharge.scrollDownToText(driver, Text_Data.PRICE_CABLE);
		homePage.clickToDynamicButtonLinkOrLinkText(driver, Text_Data.LANDLINE_TELEPHONE);

		log.info("TC_03_02_Chon tai khoan nguon");
		landLinePhoneCharge.clickToDynamicDropDown(driver, Text_Data.SOURCE_ACCOUNT);
		sourceAccount = landLinePhoneCharge.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		surplus = convertAvailableBalanceCurrentcyOrFeeToLong(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, Text_Data.AVAILABLE_BALANCES));

		log.info("TC_03_03_Chon loai cuoc thanh toan");

		landLinePhoneCharge.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/wrap_tv");
		landLinePhoneCharge.clickToDynamicTextContains(driver, Text_Data.NOT_LINE);

		log.info("TC_03_04_Nhap so dien thoai tra cuoc va bam Tiep tuc");
		landLinePhoneCharge.inputPhoneNumberLandLinePhoneCharge(notLine);

		money = convertAvailableBalanceCurrentcyOrFeeToLong(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, Text_Data.AMOUNT_PAY));
		log.info("TC_03_05_Kiem tra man hinh xac nhan thong tin");
		log.info("TC_03_05_1_Kiem tra tai khoan nguon");
		verifyEquals(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, Text_Data.SOURCE_ACCOUNT), sourceAccount.account);

		log.info("TC_03_05_2_Kiem tra dich vu");
		verifyEquals(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, ReportTitle.SERVICE), Text_Data.LANDLINE_TELEPHONE);

		log.info("TC_03_05_3_Kiem tra nha cung cap");
		verifyEquals(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, ReportTitle.SUPPLIER), Text_Data.NOT_LINE_VIETTEL);

		log.info("TC_03_05_4_Kiem tra so dien thoai");
		verifyEquals(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, Text_Data.PHONE_NUMBER), LandLinePhoneChargePageObject.phoneNumber);

		log.info("TC_03_06_Chon phuong thuc xac thuc");
		landLinePhoneCharge.clickToDynamicButtonLinkOrLinkText(driver, Text_Data.PASSWORD_LOGIN);
		landLinePhoneCharge.clickToDynamicButtonLinkOrLinkText(driver, Text_Data.SMS_OTP);

		log.info("TC_03_07_01_Kiem tra so tien phi");
		fee = convertAvailableBalanceCurrentcyOrFeeToLong(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, Text_Data.AMOUNT_FEE));

		log.info("TC_03_08_Click Tiep tuc");
		landLinePhoneCharge.clickToDynamicButton(driver, Text_Data.CONTINUE);

		log.info("TC_03_09_Nhap OTP");
		landLinePhoneCharge.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, Text_Data.CONTINUE);
		landLinePhoneCharge.clickToDynamicButton(driver, Text_Data.CONTINUE);

		log.info("TC_03_10_Kiem tra man hinh Chuyen khoan thanh cong");
		log.info("TC_03_10_1_Kiem tra Chuyen khoan thanh cong");
		verifyTrue(landLinePhoneCharge.isDynamicMessageAndLabelTextDisplayed(driver, LandLinePhoneCharge_Data.SUCCESS_TRANSFER_MONEY));

		log.info("TC_03_10_2_Kiem tra dich vu");
		verifyEquals(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, ReportTitle.SERVICE), Text_Data.LANDLINE_TELEPHONE);

		log.info("TC_03_10_3_Kiem tra nha cung cap");
		verifyEquals(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, ReportTitle.SUPPLIER), Text_Data.NOT_LINE_VIETTEL);

		log.info("TC_03_10_3_So dien thoai");
		verifyEquals(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, Text_Data.PHONE_NUMBER), LandLinePhoneChargePageObject.phoneNumber);

		log.info("TC_03_10_4_Kiem tra nut Thuc hien giao dich moi");
		verifyTrue(landLinePhoneCharge.isDynamicButtonDisplayed(driver, Text_Data.NEW_TRANSFER));

		log.info("TC_03_10_5_Lay ma giao dich");
		transferTime = landLinePhoneCharge.getTransferTimeSuccess(driver, LandLinePhoneCharge_Data.SUCCESS_TRANSFER_MONEY);
		transactionNumber = landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, Text_Data.CODE_TRANSFER);

		log.info("TC_03_11_Click Thuc hien giao dich moi");
		landLinePhoneCharge.clickToDynamicButton(driver, Text_Data.NEW_TRANSFER);

		log.info("TC_03_11_Kiem tra so du kha dung luc sau");
		landLinePhoneCharge.clickToDynamicDropDown(driver, Text_Data.SOURCE_ACCOUNT);
		sourceAccount = landLinePhoneCharge.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		actualAvailableBalance = convertAvailableBalanceCurrentcyOrFeeToLong(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, Text_Data.AVAILABLE_BALANCES));
		availableBalance = canculateAvailableBalances(surplus, money, fee);
		verifyEquals(actualAvailableBalance, availableBalance);

	}

	@Test
	public void TC_04_ThanhToanCuocDienThoaiCoDinh_CoDinhKhongDay_BaoCao() {
		log.info("TC_04_1: Click  nut Back");
		landLinePhoneCharge.clickToBackIconOnLandLinePhoneChargeScreen(Text_Data.LANDLINE_TELEPHONE);

		log.info("TC_04_2: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_04_3: Click Bao Cao giao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_04_4: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.ALL_TYPE_TRANSACTION);

		log.info("TC_04_5: Chon Thanh toan hoa don");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.PAYMENT_BILLING);

		log.info("TC_04_6: Click Chon Tai Khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_04_7: Chon tai Khoan chuyen");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_04_8: Click Tim Kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_04_9: Kiem tra ngay tao giao dich hien thi");
		String reportTime1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
		verifyEquals(convertDateTimeIgnoreHHmmss(reportTime1), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_04_10: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(money + "") + " VND"));

		log.info("TC_04_11: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_04_12: Kiem tra thoi gian tao giao dich hien thi");
		String reportTime2 = transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TIME_TRANSACTION);
		verifyEquals(reportTime2, reportTime1);

		log.info("TC_04_13: Kiem tra ma giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_NUMBER), transactionNumber);

		log.info("TC_04_14: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_CARD), sourceAccount.account);

		log.info("TC_04_15_Kiem tra dich vu");
		verifyEquals(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, ReportTitle.SERVICE), Text_Data.LANDLINE_TELEPHONE);

		log.info("TC_04_16_Kiem tra nha cung cap");
		verifyEquals(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, ReportTitle.SUPPLIER), Text_Data.NOT_LINE_VIETTEL);

		log.info("TC_04_18: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.AMOUNT_TRANSFER).contains(addCommasToLong(money + "") + " VND"));

		log.info("TC_04_20: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TYPE_TRANSFER), ReportTitle.PAYMENT_BILLING);

		log.info("TC_04_21: Kiem Tra noi dung giao dich");
		String note = "MBVCB" + transactionNumber ;
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.CONTENT_TRANSFER).contains(note));

		log.info("TC_04_22: Click  nut Back");
		landLinePhoneCharge.clickToDynamicBackIcon(driver, ReportTitle.DETAIL_TRANSFER);

		log.info("TC_04_23: Click  nut Back");
		landLinePhoneCharge.clickToDynamicBackIcon(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_04_24: Click  nut Home");
		landLinePhoneCharge.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
