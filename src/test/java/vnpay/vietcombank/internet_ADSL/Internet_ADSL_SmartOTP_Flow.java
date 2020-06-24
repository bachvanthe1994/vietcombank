package vnpay.vietcombank.internet_ADSL;

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
import pageObjects.InternetADSLPageObject;
import pageObjects.LogInPageObject;
import pageObjects.SettingVCBSmartOTPPageObject;
import pageObjects.TransactionReportPageObject;
import vietcombank_test_data.Internet_ADSL_Data;
import vietcombank_test_data.TransactionReport_Data;

public class Internet_ADSL_SmartOTP_Flow extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private InternetADSLPageObject adsl;
	private TransactionReportPageObject transReport;
	private SettingVCBSmartOTPPageObject smartOTP;
	String transferTime, password;
	String transactionNumber;
	long amount, fee, amountStart, feeView, amountView, amountAfter = 0;
	SourceAccountModel sourceAccount = new SourceAccountModel();
	String account = "";
	String passSmartOTP = "111222";
	String otp;
	List<String> codeViettel = new ArrayList<String>();
	List<String> codeFpt = new ArrayList<String>();

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException, GeneralSecurityException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		password = pass;
		otp = opt;
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
		adsl = PageFactoryManager.getInternetADSLPageObject(driver);
		smartOTP = PageFactoryManager.getSettingVCBSmartOTPPageObject(driver);
		smartOTP.setupSmartOTP(passSmartOTP, getDataInCell(6));
		codeViettel = Arrays.asList(getDataInCell(17).split(";"));
		codeFpt = Arrays.asList(getDataInCell(18).split(";"));
	}

	@Parameters({ "otp" })
	@Test(invocationCount = 2)
	public void TC_00_ThanhToanCuocViettelXacThucOTP(String otp) {
		log.info("TC_03_Step_Click cuoc ADSL");
		adsl.scrollDownToText(driver, Internet_ADSL_Data.Valid_Account.SAVE);
		adsl.clickToDynamicButtonLinkOrLinkText(driver, Internet_ADSL_Data.Valid_Account.FEE_ADSL_INTERNET);

		log.info("TC_03_Step_Select tai khoan nguon");
		adsl.clickToDynamicDropDown(driver, Internet_ADSL_Data.Valid_Account.SOURCE_ACCOUNT);
		sourceAccount = adsl.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		log.info("TC_03_Step_Thong tin giao dich chon Viettel");

		adsl.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/wrap_tv");
		adsl.clickToDynamicButtonLinkOrLinkText(driver, Internet_ADSL_Data.Valid_Account.VIETTEL);

		log.info("TC_03_Input ma khach hang");
		adsl.inputCustomerCode(codeViettel);

		log.info("TC_03_Click Tiep tuc");
		adsl.clickToDynamicButton(driver, Internet_ADSL_Data.Valid_Account.CONTINUE);

		log.info("TC_03_Step_Nhap ma xac thuc");
		adsl.inputToDynamicOtp(driver, otp, Internet_ADSL_Data.Valid_Account.CONTINUE);

		log.info("TC_03_Step_Tiep tuc");
		adsl.clickToDynamicButton(driver, Internet_ADSL_Data.Valid_Account.CONTINUE);

		log.info("TC_03_Verify message thanh cong");
		verifyEquals(adsl.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), Internet_ADSL_Data.Valid_Account.TRANSACTION_SUCCESS);

		adsl.clickToDynamicButton(driver, Internet_ADSL_Data.Valid_Account.NEW_TRANSACTION);

		adsl.clickImageBack("com.VCB:id/ivTitleLeft");

	}

	@Test
	@Parameters({ "pass" })
	public void TC_01_ThanhToanCuocViettelXacThucSmartOTP(String pass) throws InterruptedException {
		log.info("TC_02_Step_Click cuoc ADSL");
		adsl.clickToDynamicButtonLinkOrLinkText(driver, Internet_ADSL_Data.Valid_Account.FEE_ADSL_INTERNET);

		log.info("TC_02_Step_Select tai khoan nguon");
		adsl.clickToDynamicDropDown(driver, Internet_ADSL_Data.Valid_Account.SOURCE_ACCOUNT);
		sourceAccount = adsl.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);
		account = sourceAccount.account;

		log.info("TC_02_Step_Get so du kha dung");
		amountStart = convertAvailableBalanceCurrentcyOrFeeToLong(adsl.getDynamicTextByLabel(driver, Internet_ADSL_Data.Valid_Account.AMOUNT));

		log.info("TC_02_Step_Thong tin giao dich chon Viettel");
		adsl.clickToTextID(driver, "com.VCB:id/content");
		adsl.clickToDynamicButtonLinkOrLinkText(driver, Internet_ADSL_Data.Valid_Account.VIETTEL);

		log.info("TC_02_Input ma khach hang");
		adsl.inputCustomerCode(codeViettel);

		log.info("TC_02_Kiem tra man hinh xac nhan thong tin");
		log.info("TC_02_Kiem tra tai khoan nguon");
		verifyEquals(adsl.getDynamicTextInTransactionDetail(driver, Internet_ADSL_Data.Valid_Account.SOURCE_ACCOUNT), account);

		log.info("TC_02_Kiem tra dich vu");
		verifyEquals(adsl.getDynamicTextInTransactionDetail(driver, Internet_ADSL_Data.Valid_Account.SERVICE), Internet_ADSL_Data.Valid_Account.FEE_ADSL);

		log.info("TC_02_Kiem tra nha cung cap");

		verifyEquals(adsl.getDynamicTextInTransactionDetail(driver, Internet_ADSL_Data.Valid_Account.PROVICE), Internet_ADSL_Data.Valid_Account.VIETTEL);

		log.info("TC_02_Kiem tra ma khach hang");
		verifyEquals(adsl.getDynamicTextInTransactionDetail(driver, Internet_ADSL_Data.Valid_Account.CUSTOMER_CODE), InternetADSLPageObject.codeADSL.toUpperCase());

		log.info("TC_02_Get so tien thanh toan");
		amount = convertAvailableBalanceCurrentcyOrFeeToLong(adsl.getDynamicTextInTransactionDetail(driver, Internet_ADSL_Data.Valid_Account.PAYMENT_AMOUNT));

		log.info("TC_02_Chon phuong thuc xac thuc");
		adsl.clickToDynamicTextFollowingLinearlayout(driver, Internet_ADSL_Data.Valid_Account.SELECT_OPTION);
		adsl.clickToDynamicButtonLinkOrLinkText(driver, "VCB - Smart OTP");

		log.info("TC_02_verify so tien phi");
		fee = convertAvailableBalanceCurrentcyOrFeeToLong(adsl.getDynamicTextInTransactionDetail(driver, Internet_ADSL_Data.Valid_Account.FEE_AMOUNT));

		log.info("TC_02_Click Tiep tuc");
		adsl.clickToDynamicButton(driver, Internet_ADSL_Data.Valid_Account.CONTINUE);

		log.info("TC_02_Step_Nhap ma xac thuc");
		adsl.inputToDynamicSmartOTP(driver, passSmartOTP, "com.VCB:id/otp");

		log.info("TC_02_Step_Tiep tuc");
		adsl.clickToDynamicContinue(driver, "com.VCB:id/submit");
		adsl.clickToDynamicContinue(driver, "com.VCB:id/btContinue");

		log.info("TC_02_Verify message thanh cong");
		verifyEquals(adsl.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), Internet_ADSL_Data.Valid_Account.TRANSACTION_SUCCESS);

		log.info("TC_02_Verify so tien thanh toan");
		amountView = convertAvailableBalanceCurrentcyOrFeeToLong(adsl.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount"));
		verifyEquals(amountView, amount);

		log.info("TC_02_get thoi gian giao dich thanh cong");
		transferTime = adsl.getDynamicTransferTimeAndMoney(driver, Internet_ADSL_Data.Valid_Account.TRANSACTION_SUCCESS, "4").split(" ")[3];

		log.info("TC_02_Kiem tra dich vu");
		verifyEquals(adsl.getDynamicTextInTransactionDetail(driver, Internet_ADSL_Data.Valid_Account.SERVICE), Internet_ADSL_Data.Valid_Account.FEE_ADSL);

		log.info("TC_02_Kiem tra nha cung cap");
		verifyEquals(adsl.getDynamicTextInTransactionDetail(driver, Internet_ADSL_Data.Valid_Account.PROVICE), Internet_ADSL_Data.Valid_Account.VIETTEL);

		log.info("TC_02_Kiem tra ma khach hang");
		verifyEquals(adsl.getDynamicTextInTransactionDetail(driver, Internet_ADSL_Data.Valid_Account.CUSTOMER_CODE), InternetADSLPageObject.codeADSL.toUpperCase().toUpperCase());

		log.info("TC_02_Step_:Lay ma giao dich");
		transactionNumber = adsl.getDynamicTextInTransactionDetail(driver, Internet_ADSL_Data.Valid_Account.TRANSACTION_CODE);

		log.info("TC_02_Step_: Chon thuc hien giao dich");
		adsl.clickToDynamicButton(driver, Internet_ADSL_Data.Valid_Account.NEW_TRANSACTION);

		log.info("TC_02_Step_: Chon tai khoan chuyen");
		adsl.clickToDynamicDropDown(driver, Internet_ADSL_Data.Valid_Account.SOURCE_ACCOUNT);

		log.info("TC_02_Step_: Chon tai khoan chuyen");
		adsl.clickToDynamicButtonLinkOrLinkText(driver, account);

		log.info("TC_02_Step_:Check so du kha dung sau khi chuyen tien");
		amountAfter = convertAvailableBalanceCurrentcyOrFeeToLong(adsl.getDynamicTextInTransactionDetail(driver, Internet_ADSL_Data.Valid_Account.AMOUNT));

		log.info("TC_02_Step_:Check so du kha dung sau khi chuyen tien");
		verifyEquals(amountStart - amount - fee, amountAfter);
	}

	@Test
	public void TC_02_Report_ThanhToanCuocViettelXacThucSmartOTP() {
		log.info("TC_02_Step: Click back man hinh home");
		adsl.clickImageBack("com.VCB:id/ivTitleLeft");

		log.info("TC_02_Step: Click menu header");
		adsl.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_02_Step: Click bao cao giao dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransactionReport_Data.ReportTitle.TRANSACTION_REPORT);

		log.info("TC_02_Step: chon loai bao cao giao dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransactionReport_Data.ReportTitle.ALL_TYPE_TRANSACTION);

		log.info("TC_02_Step: Chon option chuyen tien nhanh qua tai khoan");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransactionReport_Data.ReportTitle.PAYMENT_BILLING);

		log.info("TC_02_Step: Chon so tai khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_02_Step: Chon so tai khoan tra cuu");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, account);

		log.info("TC_02_Step: verify thoi tim kiem tu ngay");
		String dateStartActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate");
		String dateStartExpect = getBackwardDate(6);
		verifyEquals(dateStartActual, dateStartExpect);

		log.info("TC_02: Step: verify thoi tim kiem den ngay");
		String dateEndActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate");
		String dateEndtExpect = getForwardDate(0);
		verifyEquals(dateEndActual, dateEndtExpect);

		log.info("TC_02_Step: Tim kiem");
		transReport.clickToDynamicButton(driver, TransactionReport_Data.ReportTitle.SEARCH_BUTTON);

		log.info("TC_02: Step: Lay ngay tao giao dich hien thi");
		String transferTimeInReport = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_02: Step: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport), transferTime);

		log.info("TC_02: Step: Check so tien chuyen");
		String amountExpect = "- " + addCommasToLong(amount + "") + " VND";
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), amountExpect);

		log.info("TC_02: Click chi tiet giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_02: Lay ngay tao giao dich hien thi");
		String transferTimeInReport1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_02: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport1), transferTime);

		log.info("TC_02: Check so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_NUMBER), transactionNumber);

		log.info("TC_02: Check tao khoan ghi no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.ACCOUNT_CARD), account);

		log.info("TC_02: Kiem tra dich vu");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Internet_ADSL_Data.Valid_Account.SERVICE), Internet_ADSL_Data.Valid_Account.FEE_ADSL);

		log.info("TC_02: Kiem tra nha cung cap");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Internet_ADSL_Data.Valid_Account.PROVICE), Internet_ADSL_Data.Valid_Account.VIETTEL);

		log.info("TC_02: Kiem tra ma khach hang");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Internet_ADSL_Data.Valid_Account.CUSTOMER_CODE), InternetADSLPageObject.codeADSL.toUpperCase().toUpperCase());

		log.info("TC_02: Check so tien giao dich");
		verifyEquals(convertAvailableBalanceCurrentcyOrFeeToLong(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_MONEY)), amount);

		log.info("TC_02: Check loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_TYPE), TransactionReport_Data.ReportTitle.PAYMENT_BILLING);

		log.info("TC_02: Chick chi tiet giao dich");
		transReport.clickToDynamicBackIcon(driver, TransactionReport_Data.ReportTitle.TRANSACTION_DETAIL);

		log.info("TTC_02: Chon button back");
		adsl.clickToDynamicBackIcon(driver, TransactionReport_Data.ReportTitle.TRANSACTION_REPORT);

		log.info("TC_02: Click button home");
		adsl.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	//@Test
	@Parameters({ "pass" })
	public void TC_03_ThanhToanCuocFPTXacThucSmartOTP(String pass) throws InterruptedException {
		log.info("TC_02_Step_Click cuoc ADSL");
		adsl.clickToDynamicButtonLinkOrLinkText(driver, Internet_ADSL_Data.Valid_Account.FEE_ADSL_INTERNET);

		log.info("TC_02_Step_Select tai khoan nguon");
		adsl.clickToDynamicDropDown(driver, Internet_ADSL_Data.Valid_Account.SOURCE_ACCOUNT);
		sourceAccount = adsl.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);
		account = sourceAccount.account;

		log.info("TC_02_Step_Get so du kha dung");
		amountStart = convertAvailableBalanceCurrentcyOrFeeToLong(adsl.getDynamicTextByLabel(driver, Internet_ADSL_Data.Valid_Account.AMOUNT));

		log.info("TC_02_Step_Thong tin giao dich chon Viettel");
		adsl.clickToTextID(driver, "com.VCB:id/content");
		adsl.clickToDynamicButtonLinkOrLinkText(driver, Internet_ADSL_Data.Valid_Account.FPT);

		log.info("TC_02_Input ma khach hang");
		adsl.inputCustomerCode(codeFpt);

		log.info("TC_02_Kiem tra man hinh xac nhan thong tin");
		log.info("TC_02_Kiem tra tai khoan nguon");
		verifyEquals(adsl.getDynamicTextInTransactionDetail(driver, Internet_ADSL_Data.Valid_Account.SOURCE_ACCOUNT), account);

		log.info("TC_02_Kiem tra dich vu");
		verifyEquals(adsl.getDynamicTextInTransactionDetail(driver, Internet_ADSL_Data.Valid_Account.SERVICE), Internet_ADSL_Data.Valid_Account.FEE_ADSL);

		log.info("TC_02_Kiem tra nha cung cap");

		verifyEquals(adsl.getDynamicTextInTransactionDetail(driver, Internet_ADSL_Data.Valid_Account.PROVICE), Internet_ADSL_Data.Valid_Account.FPT);

		log.info("TC_02_Kiem tra ma khach hang");
		verifyEquals(adsl.getDynamicTextInTransactionDetail(driver, Internet_ADSL_Data.Valid_Account.CUSTOMER_CODE), InternetADSLPageObject.codeADSL.toUpperCase());

		log.info("TC_02_Get so tien thanh toan");
		amount = convertAvailableBalanceCurrentcyOrFeeToLong(adsl.getDynamicTextInTransactionDetail(driver, Internet_ADSL_Data.Valid_Account.PAYMENT_AMOUNT));

		log.info("TC_02_Chon phuong thuc xac thuc");
		adsl.clickToDynamicTextFollowingLinearlayout(driver, Internet_ADSL_Data.Valid_Account.SELECT_OPTION);
		adsl.clickToDynamicButtonLinkOrLinkText(driver, "VCB - Smart OTP");

		log.info("TC_02_verify so tien phi");
		fee = convertAvailableBalanceCurrentcyOrFeeToLong(adsl.getDynamicTextInTransactionDetail(driver, Internet_ADSL_Data.Valid_Account.FEE_AMOUNT));

		log.info("TC_02_Click Tiep tuc");
		adsl.clickToDynamicButton(driver, Internet_ADSL_Data.Valid_Account.CONTINUE);

		log.info("TC_02_Step_Nhap ma xac thuc");
		adsl.inputToDynamicSmartOTP(driver, passSmartOTP, "com.VCB:id/otp");

		log.info("TC_02_Step_Tiep tuc");
		adsl.clickToDynamicContinue(driver, "com.VCB:id/submit");
		adsl.clickToDynamicContinue(driver, "com.VCB:id/btContinue");

		log.info("TC_02_Verify message thanh cong");
		verifyEquals(adsl.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), Internet_ADSL_Data.Valid_Account.TRANSACTION_SUCCESS);

		log.info("TC_02_Verify so tien thanh toan");
		amountView = convertAvailableBalanceCurrentcyOrFeeToLong(adsl.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount"));
		verifyEquals(amountView, amount);

		log.info("TC_02_get thoi gian giao dich thanh cong");
		transferTime = adsl.getDynamicTransferTimeAndMoney(driver, Internet_ADSL_Data.Valid_Account.TRANSACTION_SUCCESS, "4").split(" ")[3];

		log.info("TC_02_Kiem tra dich vu");
		verifyEquals(adsl.getDynamicTextInTransactionDetail(driver, Internet_ADSL_Data.Valid_Account.SERVICE), Internet_ADSL_Data.Valid_Account.FEE_ADSL);

		log.info("TC_02_Kiem tra nha cung cap");
		verifyEquals(adsl.getDynamicTextInTransactionDetail(driver, Internet_ADSL_Data.Valid_Account.PROVICE), Internet_ADSL_Data.Valid_Account.FPT);

		log.info("TC_02_Kiem tra ma khach hang");
		verifyEquals(adsl.getDynamicTextInTransactionDetail(driver, Internet_ADSL_Data.Valid_Account.CUSTOMER_CODE), InternetADSLPageObject.codeADSL.toUpperCase().toUpperCase());

		log.info("TC_02_Step_:Lay ma giao dich");
		transactionNumber = adsl.getDynamicTextInTransactionDetail(driver, Internet_ADSL_Data.Valid_Account.TRANSACTION_CODE);

		log.info("TC_02_Step_: Chon thuc hien giao dich");
		adsl.clickToDynamicButton(driver, Internet_ADSL_Data.Valid_Account.NEW_TRANSACTION);

		log.info("TC_02_Step_: Chon tai khoan chuyen");
		adsl.clickToDynamicDropDown(driver, Internet_ADSL_Data.Valid_Account.SOURCE_ACCOUNT);

		log.info("TC_02_Step_: Chon tai khoan chuyen");
		adsl.clickToDynamicButtonLinkOrLinkText(driver, account);

		log.info("TC_02_Step_:Check so du kha dung sau khi chuyen tien");
		amountAfter = convertAvailableBalanceCurrentcyOrFeeToLong(adsl.getDynamicTextInTransactionDetail(driver, Internet_ADSL_Data.Valid_Account.AMOUNT));

		log.info("TC_02_Step_:Check so du kha dung sau khi chuyen tien");
		verifyEquals(amountStart - amount - fee, amountAfter);
	}

	//@Test
	public void TC_04_Report_ThanhToanCuocFPTXacThucSmartOTP() {
		log.info("TC_02_Step: Click back man hinh home");
		adsl.clickImageBack("com.VCB:id/ivTitleLeft");

		log.info("TC_02_Step: Click menu header");
		adsl.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_02_Step: Click bao cao giao dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransactionReport_Data.ReportTitle.TRANSACTION_REPORT);

		log.info("TC_02_Step: chon loai bao cao giao dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransactionReport_Data.ReportTitle.ALL_TYPE_TRANSACTION);

		log.info("TC_02_Step: Chon option chuyen tien nhanh qua tai khoan");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransactionReport_Data.ReportTitle.PAYMENT_BILLING);

		log.info("TC_02_Step: Chon so tai khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_02_Step: Chon so tai khoan tra cuu");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, account);

		log.info("TC_02_Step: verify thoi tim kiem tu ngay");
		String dateStartActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate");
		String dateStartExpect = getBackwardDate(6);
		verifyEquals(dateStartActual, dateStartExpect);

		log.info("TC_02: Step: verify thoi tim kiem den ngay");
		String dateEndActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate");
		String dateEndtExpect = getForwardDate(0);
		verifyEquals(dateEndActual, dateEndtExpect);

		log.info("TC_02_Step: Tim kiem");
		transReport.clickToDynamicButton(driver, TransactionReport_Data.ReportTitle.SEARCH_BUTTON);

		log.info("TC_02: Step: Lay ngay tao giao dich hien thi");
		String transferTimeInReport = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_02: Step: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport), transferTime);

		log.info("TC_02: Step: Check so tien chuyen");
		String amountExpect = "- " + addCommasToLong(amount + "") + " VND";
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), amountExpect);

		log.info("TC_02: Click chi tiet giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_02: Lay ngay tao giao dich hien thi");
		String transferTimeInReport1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_02: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport1), transferTime);

		log.info("TC_02: Check so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_NUMBER), transactionNumber);

		log.info("TC_02: Check tao khoan ghi no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.ACCOUNT_CARD), account);

		log.info("TC_02: Kiem tra dich vu");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Internet_ADSL_Data.Valid_Account.SERVICE), Internet_ADSL_Data.Valid_Account.FEE_ADSL);

		log.info("TC_02: Kiem tra nha cung cap");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Internet_ADSL_Data.Valid_Account.PROVICE), Internet_ADSL_Data.Valid_Account.FPT);

		log.info("TC_02: Kiem tra ma khach hang");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Internet_ADSL_Data.Valid_Account.CUSTOMER_CODE), InternetADSLPageObject.codeADSL.toUpperCase().toUpperCase());

		log.info("TC_02: Check so tien giao dich");
		verifyEquals(convertAvailableBalanceCurrentcyOrFeeToLong(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_MONEY)), amount);

		log.info("TC_02: Check loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_TYPE), TransactionReport_Data.ReportTitle.PAYMENT_BILLING);

		log.info("TC_02: Chick chi tiet giao dich");
		transReport.clickToDynamicBackIcon(driver, TransactionReport_Data.ReportTitle.TRANSACTION_DETAIL);

		log.info("TTC_02: Chon button back");
		adsl.clickToDynamicBackIcon(driver, TransactionReport_Data.ReportTitle.TRANSACTION_REPORT);

		log.info("TC_02: Click button home");
		adsl.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		smartOTP.cancelSetupSmartOTP();
	}

}
