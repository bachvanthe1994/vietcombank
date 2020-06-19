package vnpay.vietcombank.insurance_fee;

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
import pageObjects.InsuranceFeePageObject;
import pageObjects.LogInPageObject;
import pageObjects.SettingVCBSmartOTPPageObject;
import pageObjects.TransactionReportPageObject;
import vietcombank_test_data.Insurance_Fee_Data;
import vietcombank_test_data.TransactionReport_Data;

public class Insurance_Fee_Flow extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private InsuranceFeePageObject insurrance ;
	private TransactionReportPageObject transReport;
	String transferTime, password;
	String transactionNumber;
	long amount,  amountStart, feeView, amountView, amountAfter = 0;
	SourceAccountModel sourceAccount = new SourceAccountModel();
	String account = "";
	List<String> codePeriodic = new ArrayList<String>();
	List<String> codeFirst  = new ArrayList<String>();
	List<String> codeAdvance  = new ArrayList<String>();
	private SettingVCBSmartOTPPageObject smartOTP;

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException, GeneralSecurityException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		password = pass;
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
		insurrance = PageFactoryManager.getInsurranceFeePageObject(driver);
		codePeriodic = Arrays.asList(getDataInCell(41).split(";"));
		codeFirst = Arrays.asList(getDataInCell(42).split(";"));
		codeAdvance = Arrays.asList(getDataInCell(43).split(";"));
		String passSmartOTP = getDataInCell(6);
		smartOTP = PageFactoryManager.getSettingVCBSmartOTPPageObject(driver);
		smartOTP.setupSmartOTP(passSmartOTP, getDataInCell(6));
	}
	@Parameters({ "pass" })
	@Test
	public void TC_01_ThanhToanPhiBaoHiem_DinhKy_PTXTMatKhau(String pass) throws InterruptedException {
		log.info("TC_01_Step_Click phi bao hiem");
		insurrance.scrollDownToText(driver, Insurance_Fee_Data.Valid_Account.INSURRANCE_FEE);
		insurrance.clickToDynamicButtonLinkOrLinkText(driver, Insurance_Fee_Data.Valid_Account.INSURRANCE_FEE);

		log.info("TC_01_Step_Select tai khoan nguon");
		insurrance.clickToDynamicDropDown(driver, Insurance_Fee_Data.Valid_Account.SOURCE_ACCOUNT);
		sourceAccount = insurrance.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);
		account = sourceAccount.account;

		log.info("TC_01_Step_Get so du kha dung");
		amountStart = convertAvailableBalanceCurrentcyOrFeeToLong(insurrance.getDynamicTextByLabel(driver, Insurance_Fee_Data.Valid_Account.AMOUNT));

		log.info("TC_01_Step_Chon cong ty bao hiem");
		insurrance.clickToDynamicRelavitelayout(driver, "com.VCB:id/provider");
		insurrance.clickToDynamicButtonLinkOrLinkText(driver, Insurance_Fee_Data.Valid_Account.INSURRANCE_FWD);
		
		log.info("TC_01_Step_Chon loai giao dich");
		insurrance.clickToDynamicTextID(driver, "com.VCB:id/subProvider");
		insurrance.clickToDynamicButtonLinkOrLinkText(driver, Insurance_Fee_Data.Valid_Account.PAYMENT_INSURRANCE_RECURRENT);

		log.info("TC_01_Input ma khach hang");
		insurrance.inputCustomerCode(codePeriodic);

		log.info("TC_01_Kiem tra man hinh xac nhan thong tin");
		log.info("TC_01_Kiem tra tai khoan nguon");
		verifyEquals(insurrance.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.SOURCE_ACCOUNT), account);

		log.info("TC_01_Kiem tra dich vu");
		verifyEquals(insurrance.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.SERVICE), Insurance_Fee_Data.Valid_Account.INSURRANCE_FEE);

		log.info("TC_01_Kiem tra nha cung cap");
		verifyEquals(insurrance.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.PROVICE), Insurance_Fee_Data.Valid_Account.INSURRANCE_FWD);
		
		log.info("TC_01_Kiem tra loai giao dich");
		verifyEquals(insurrance.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.TRANSACTION_TYPE), Insurance_Fee_Data.Valid_Account.PAYMENT_INSURRANCE_RECURRENT);

		log.info("TC_01_Kiem tra ma khach hang");
		verifyEquals(insurrance.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.CUSTOMER_CODE), InsuranceFeePageObject.codeInsurrance);
		
		log.info("TC_01_get ten chu hop dong");
		String name = insurrance.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.CUSTOMER_NAME);

		log.info("TC_01_Get so tien thanh toan");
		amount = convertAvailableBalanceCurrentcyOrFeeToLong(insurrance.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.PAYMENT_AMOUNT));
		
		log.info("TC_01_Verify thong tin them");
		verifyEquals(insurrance.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.ADD_INFO), "Chủ hợp đồng: "+ name + ", Tên loại phí: Thanh toán phí bảo hiể" );

		log.info("TC_01_Chon phuong thuc xac thuc");
		insurrance.clickToDynamicTextFollowingLinearlayout(driver, Insurance_Fee_Data.Valid_Account.SELECT_OPTION);
		insurrance.clickToDynamicButtonLinkOrLinkText(driver, Insurance_Fee_Data.Valid_Account.PASSWORD);

		log.info("TC_01_get so tien phi");
		feeView = convertAvailableBalanceCurrentcyOrFeeToLong(insurrance.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.FEE_AMOUNT));

		log.info("TC_01_Click Tiep tuc");
		insurrance.clickToDynamicContinue(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_Nhap ma xac thuc");
		insurrance.inputToDynamicPopupPasswordInput(driver, pass, Insurance_Fee_Data.Valid_Account.CONTINUE);

		log.info("TC_01_Step_Tiep tuc");
		insurrance.clickToDynamicButton(driver, Insurance_Fee_Data.Valid_Account.CONTINUE);

		log.info("TC_01_Verify message thanh cong");
		verifyEquals(insurrance.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), Insurance_Fee_Data.Valid_Account.TRANSACTION_SUCCESS);

		log.info("TC_01_Verify so tien thanh toan");
		amountView = convertAvailableBalanceCurrentcyOrFeeToLong(insurrance.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount"));
		verifyEquals(amountView, amount);

		log.info("TC_01_get thoi gian giao dich thanh cong");
		transferTime = insurrance.getAccountNumber(driver, "com.VCB:id/tvTime").split(" ")[3];

		log.info("TC_01_Kiem tra dich vu");
		verifyEquals(insurrance.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.SERVICE), Insurance_Fee_Data.Valid_Account.INSURRANCE_FEE);

		log.info("TC_01_Kiem tra nha cung cap");
		verifyEquals(insurrance.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.PROVICE), Insurance_Fee_Data.Valid_Account.INSURRANCE_FWD);
		
		log.info("TC_01_Kiem tra loại giao dich");
		verifyEquals(insurrance.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.TRANSACTION_TYPE), Insurance_Fee_Data.Valid_Account.PAYMENT_INSURRANCE_RECURRENT);

		log.info("TC_01_Kiem tra ma khach hang");
		verifyEquals(insurrance.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.CUSTOMER_CODE), InsuranceFeePageObject.codeInsurrance);
		
		log.info("TC_01_verify noi dung");
		verifyTrue(insurrance.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent").contains(InsuranceFeePageObject.codeInsurrance));

		log.info("TC_01_Step_:Lay ma giao dich");
		transactionNumber = insurrance.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.TRANSACTION_CODE);

		log.info("TC_01_Step_:Click thuc hien giao dich moi");
		insurrance.clickToDynamicButton(driver, Insurance_Fee_Data.Valid_Account.NEW_TRANSACTION);

		log.info("TC_01_Step_: Chon tai khoan chuyen");
		insurrance.clickToDynamicDropDown(driver, Insurance_Fee_Data.Valid_Account.SOURCE_ACCOUNT);

		log.info("TC_01_Step_: Chon tai khoan chuyen");
		insurrance.clickToDynamicButtonLinkOrLinkText(driver, account);

		log.info("TC_01_Step_:Check so du kha dung sau khi chuyen tien");
		amountAfter = convertAvailableBalanceCurrentcyOrFeeToLong(insurrance.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.AMOUNT));

		log.info("TC_01_Step_:Check so du kha dung sau khi chuyen tien");
		verifyEquals(amountStart - amount - feeView, amountAfter);
	}

	@Test
	public void TC_02_Report_ThanhToanPhiBaoHiem_DinhKy_PTXTMatKhau() {
		log.info("TC_02_Step: Click back man hinh home");
		insurrance.clickImageBack("com.VCB:id/ivTitleLeft");

		log.info("TC_02_Step: Click menu header");
		insurrance.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

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
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.SERVICE), Insurance_Fee_Data.Valid_Account.INSURRANCE_FEE);

		log.info("TC_02: Kiem tra nha cung cap");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.PROVICE), Insurance_Fee_Data.Valid_Account.INSURRANCE_FWD);
		
		log.info("TC_02: Kiem tra don vi truc thuoc");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.UNIT), Insurance_Fee_Data.Valid_Account.PAYMENT_INSURRANCE_RECURRENT);

		log.info("TC_02: Kiem tra ma khach hang");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.CUSTOMER_CODE), InsuranceFeePageObject.codeInsurrance);

		log.info("TC_02: Check so tien giao dich");
		verifyEquals(convertAvailableBalanceCurrentcyOrFeeToLong(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_MONEY)), amount);

		log.info("TC_02: Check loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_TYPE), TransactionReport_Data.ReportTitle.PAYMENT_BILLING);

		log.info("TC_02: Click chi tiet giao dich");
		transReport.clickToDynamicBackIcon(driver, TransactionReport_Data.ReportTitle.TRANSACTION_DETAIL);

		log.info("TTC_02: Chon button back");
		insurrance.clickToDynamicBackIcon(driver, TransactionReport_Data.ReportTitle.TRANSACTION_REPORT);

		log.info("TC_02: Click button home");
		insurrance.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}
	
	@Parameters({ "otp" })
	@Test
	public void TC_03_ThanhToanPhiBaoHiem_DinhKy_PTXTSMSOTP(String otp) throws InterruptedException {
		log.info("TC_01_Step_Click phi bao hiem");
		insurrance.scrollDownToText(driver, Insurance_Fee_Data.Valid_Account.INSURRANCE_FEE);
		insurrance.clickToDynamicButtonLinkOrLinkText(driver, Insurance_Fee_Data.Valid_Account.INSURRANCE_FEE);

		log.info("TC_01_Step_Select tai khoan nguon");
		insurrance.clickToDynamicDropDown(driver, Insurance_Fee_Data.Valid_Account.SOURCE_ACCOUNT);
		sourceAccount = insurrance.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);
		account = sourceAccount.account;

		log.info("TC_01_Step_Get so du kha dung");
		amountStart = convertAvailableBalanceCurrentcyOrFeeToLong(insurrance.getDynamicTextByLabel(driver, Insurance_Fee_Data.Valid_Account.AMOUNT));

		log.info("TC_01_Step_Chon cong ty bao hiem");
		insurrance.clickToDynamicRelavitelayout(driver, "com.VCB:id/provider");
		insurrance.clickToDynamicButtonLinkOrLinkText(driver, Insurance_Fee_Data.Valid_Account.INSURRANCE_FWD);
		
		log.info("TC_01_Step_Chon loai giao dich");
		insurrance.clickToDynamicTextID(driver, "com.VCB:id/subProvider");
		insurrance.clickToDynamicButtonLinkOrLinkText(driver, Insurance_Fee_Data.Valid_Account.PAYMENT_INSURRANCE_RECURRENT);

		log.info("TC_01_Input ma khach hang");
		insurrance.inputCustomerCode(codePeriodic);

		log.info("TC_01_Kiem tra man hinh xac nhan thong tin");
		log.info("TC_01_Kiem tra tai khoan nguon");
		verifyEquals(insurrance.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.SOURCE_ACCOUNT), account);

		log.info("TC_01_Kiem tra dich vu");
		verifyEquals(insurrance.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.SERVICE), Insurance_Fee_Data.Valid_Account.INSURRANCE_FEE);

		log.info("TC_01_Kiem tra nha cung cap");
		verifyEquals(insurrance.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.PROVICE), Insurance_Fee_Data.Valid_Account.INSURRANCE_FWD);
		
		log.info("TC_01_Kiem tra loai giao dich");
		verifyEquals(insurrance.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.TRANSACTION_TYPE), Insurance_Fee_Data.Valid_Account.PAYMENT_INSURRANCE_RECURRENT);

		log.info("TC_01_Kiem tra ma khach hang");
		verifyEquals(insurrance.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.CUSTOMER_CODE), InsuranceFeePageObject.codeInsurrance);
		
		log.info("TC_01_get ten chu hop dong");
		String name = insurrance.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.CUSTOMER_NAME);

		log.info("TC_01_Get so tien thanh toan");
		amount = convertAvailableBalanceCurrentcyOrFeeToLong(insurrance.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.PAYMENT_AMOUNT));
		
		log.info("TC_01_Verify thong tin them");
		verifyEquals(insurrance.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.ADD_INFO), "Chủ hợp đồng: "+ name + ", Tên loại phí: Thanh toán phí bảo hiể" );

		log.info("TC_01_Chon phuong thuc xac thuc");
		insurrance.clickToDynamicTextFollowingLinearlayout(driver, Insurance_Fee_Data.Valid_Account.SELECT_OPTION);
		insurrance.clickToDynamicButtonLinkOrLinkText(driver, Insurance_Fee_Data.Valid_Account.SMS);

		log.info("TC_01_get so tien phi");
		feeView = convertAvailableBalanceCurrentcyOrFeeToLong(insurrance.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.FEE_AMOUNT));

		log.info("TC_01_Click Tiep tuc");
		insurrance.clickToDynamicContinue(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_Nhap ma xac thuc");
		insurrance.inputToDynamicOtp(driver, otp, Insurance_Fee_Data.Valid_Account.CONTINUE);

		log.info("TC_01_Step_Tiep tuc");
		insurrance.clickToDynamicButton(driver, Insurance_Fee_Data.Valid_Account.CONTINUE);

		log.info("TC_01_Verify message thanh cong");
		verifyEquals(insurrance.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), Insurance_Fee_Data.Valid_Account.TRANSACTION_SUCCESS);

		log.info("TC_01_Verify so tien thanh toan");
		amountView = convertAvailableBalanceCurrentcyOrFeeToLong(insurrance.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount"));
		verifyEquals(amountView, amount);

		log.info("TC_01_get thoi gian giao dich thanh cong");
		transferTime = insurrance.getAccountNumber(driver, "com.VCB:id/tvTime").split(" ")[3];

		log.info("TC_01_Kiem tra dich vu");
		verifyEquals(insurrance.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.SERVICE), Insurance_Fee_Data.Valid_Account.INSURRANCE_FEE);

		log.info("TC_01_Kiem tra nha cung cap");
		verifyEquals(insurrance.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.PROVICE), Insurance_Fee_Data.Valid_Account.INSURRANCE_FWD);
		
		log.info("TC_01_Kiem tra loại giao dich");
		verifyEquals(insurrance.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.TRANSACTION_TYPE), Insurance_Fee_Data.Valid_Account.PAYMENT_INSURRANCE_RECURRENT);

		log.info("TC_01_Kiem tra ma khach hang");
		verifyEquals(insurrance.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.CUSTOMER_CODE), InsuranceFeePageObject.codeInsurrance);
		
		log.info("TC_01_verify noi dung");
		verifyTrue(insurrance.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent").contains(InsuranceFeePageObject.codeInsurrance));

		log.info("TC_01_Step_:Lay ma giao dich");
		transactionNumber = insurrance.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.TRANSACTION_CODE);

		log.info("TC_01_Step_:Click thuc hien giao dich moi");
		insurrance.clickToDynamicButton(driver, Insurance_Fee_Data.Valid_Account.NEW_TRANSACTION);

		log.info("TC_01_Step_: Chon tai khoan chuyen");
		insurrance.clickToDynamicDropDown(driver, Insurance_Fee_Data.Valid_Account.SOURCE_ACCOUNT);

		log.info("TC_01_Step_: Chon tai khoan chuyen");
		insurrance.clickToDynamicButtonLinkOrLinkText(driver, account);

		log.info("TC_01_Step_:Check so du kha dung sau khi chuyen tien");
		amountAfter = convertAvailableBalanceCurrentcyOrFeeToLong(insurrance.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.AMOUNT));

		log.info("TC_01_Step_:Check so du kha dung sau khi chuyen tien");
		verifyEquals(amountStart - amount - feeView, amountAfter);
	}

	@Test
	public void TC_04_Report_ThanhToanPhiBaoHiem_DinhKy_PTXTSMSOTP () {
		log.info("TC_02_Step: Click back man hinh home");
		insurrance.clickImageBack("com.VCB:id/ivTitleLeft");

		log.info("TC_02_Step: Click menu header");
		insurrance.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

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
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.SERVICE), Insurance_Fee_Data.Valid_Account.INSURRANCE_FEE);

		log.info("TC_02: Kiem tra nha cung cap");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.PROVICE), Insurance_Fee_Data.Valid_Account.INSURRANCE_FWD);
		
		log.info("TC_02: Kiem tra don vi truc thuoc");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.UNIT), Insurance_Fee_Data.Valid_Account.PAYMENT_INSURRANCE_RECURRENT);

		log.info("TC_02: Kiem tra ma khach hang");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.CUSTOMER_CODE), InsuranceFeePageObject.codeInsurrance);

		log.info("TC_02: Check so tien giao dich");
		verifyEquals(convertAvailableBalanceCurrentcyOrFeeToLong(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_MONEY)), amount);

		log.info("TC_02: Check loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_TYPE), TransactionReport_Data.ReportTitle.PAYMENT_BILLING);

		log.info("TC_02: Click chi tiet giao dich");
		transReport.clickToDynamicBackIcon(driver, TransactionReport_Data.ReportTitle.TRANSACTION_DETAIL);

		log.info("TTC_02: Chon button back");
		insurrance.clickToDynamicBackIcon(driver, TransactionReport_Data.ReportTitle.TRANSACTION_REPORT);

		log.info("TC_02: Click button home");
		insurrance.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}
	
	@Test
	public void TC_05_ThanhToanPhiBaoHiem_LanDau_PTXTSMSOTP(String otp) throws InterruptedException {
		log.info("TC_01_Step_Click phi bao hiem");
		insurrance.scrollDownToText(driver, Insurance_Fee_Data.Valid_Account.INSURRANCE_FEE);
		insurrance.clickToDynamicButtonLinkOrLinkText(driver, Insurance_Fee_Data.Valid_Account.INSURRANCE_FEE);

		log.info("TC_01_Step_Select tai khoan nguon");
		insurrance.clickToDynamicDropDown(driver, Insurance_Fee_Data.Valid_Account.SOURCE_ACCOUNT);
		sourceAccount = insurrance.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);
		account = sourceAccount.account;

		log.info("TC_01_Step_Get so du kha dung");
		amountStart = convertAvailableBalanceCurrentcyOrFeeToLong(insurrance.getDynamicTextByLabel(driver, Insurance_Fee_Data.Valid_Account.AMOUNT));

		log.info("TC_01_Step_Chon cong ty bao hiem");
		insurrance.clickToDynamicRelavitelayout(driver, "com.VCB:id/provider");
		insurrance.clickToDynamicButtonLinkOrLinkText(driver, Insurance_Fee_Data.Valid_Account.INSURRANCE_FWD);
		
		log.info("TC_01_Step_Chon loai giao dich");
		insurrance.clickToDynamicTextID(driver, "com.VCB:id/subProvider");
		insurrance.clickToDynamicButtonLinkOrLinkText(driver, Insurance_Fee_Data.Valid_Account.PAYMENT_INSURRANCE_FIRST);

		log.info("TC_01_Input ma khach hang");
		insurrance.inputCustomerCode(codeFirst);

		log.info("TC_01_Kiem tra man hinh xac nhan thong tin");
		log.info("TC_01_Kiem tra tai khoan nguon");
		verifyEquals(insurrance.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.SOURCE_ACCOUNT), account);

		log.info("TC_01_Kiem tra dich vu");
		verifyEquals(insurrance.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.SERVICE), Insurance_Fee_Data.Valid_Account.INSURRANCE_FEE);

		log.info("TC_01_Kiem tra nha cung cap");
		verifyEquals(insurrance.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.PROVICE), Insurance_Fee_Data.Valid_Account.INSURRANCE_FWD);
		
		log.info("TC_01_Kiem tra loai giao dich");
		verifyEquals(insurrance.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.TRANSACTION_TYPE), Insurance_Fee_Data.Valid_Account.PAYMENT_INSURRANCE_FIRST);

		log.info("TC_01_Kiem tra ma khach hang");
		verifyEquals(insurrance.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.CUSTOMER_CODE), InsuranceFeePageObject.codeInsurrance);
		
		log.info("TC_01_get ten chu hop dong");
		String name = insurrance.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.CUSTOMER_NAME);

		log.info("TC_01_Get so tien thanh toan");
		amount = convertAvailableBalanceCurrentcyOrFeeToLong(insurrance.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.PAYMENT_AMOUNT));
		
		log.info("TC_01_Verify thong tin them");
		verifyEquals(insurrance.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.ADD_INFO), "Chủ hợp đồng: "+ name + ", Tên loại phí: Thanh toán phí bảo hiể" );

		log.info("TC_01_Chon phuong thuc xac thuc");
		insurrance.clickToDynamicTextFollowingLinearlayout(driver, Insurance_Fee_Data.Valid_Account.SELECT_OPTION);
		insurrance.clickToDynamicButtonLinkOrLinkText(driver, Insurance_Fee_Data.Valid_Account.SMS);

		log.info("TC_01_get so tien phi");
		feeView = convertAvailableBalanceCurrentcyOrFeeToLong(insurrance.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.FEE_AMOUNT));

		log.info("TC_01_Click Tiep tuc");
		insurrance.clickToDynamicContinue(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_Nhap ma xac thuc");
		insurrance.inputToDynamicOtp(driver, otp, Insurance_Fee_Data.Valid_Account.CONTINUE);

		log.info("TC_01_Step_Tiep tuc");
		insurrance.clickToDynamicButton(driver, Insurance_Fee_Data.Valid_Account.CONTINUE);

		log.info("TC_01_Verify message thanh cong");
		verifyEquals(insurrance.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), Insurance_Fee_Data.Valid_Account.TRANSACTION_SUCCESS);

		log.info("TC_01_Verify so tien thanh toan");
		amountView = convertAvailableBalanceCurrentcyOrFeeToLong(insurrance.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount"));
		verifyEquals(amountView, amount);

		log.info("TC_01_get thoi gian giao dich thanh cong");
		transferTime = insurrance.getAccountNumber(driver, "com.VCB:id/tvTime").split(" ")[3];

		log.info("TC_01_Kiem tra dich vu");
		verifyEquals(insurrance.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.SERVICE), Insurance_Fee_Data.Valid_Account.INSURRANCE_FEE);

		log.info("TC_01_Kiem tra nha cung cap");
		verifyEquals(insurrance.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.PROVICE), Insurance_Fee_Data.Valid_Account.INSURRANCE_FWD);
		
		log.info("TC_01_Kiem tra loại giao dich");
		verifyEquals(insurrance.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.TRANSACTION_TYPE), Insurance_Fee_Data.Valid_Account.PAYMENT_INSURRANCE_FIRST);

		log.info("TC_01_Kiem tra ma khach hang");
		verifyEquals(insurrance.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.CUSTOMER_CODE), InsuranceFeePageObject.codeInsurrance);
		
		log.info("TC_01_verify noi dung");
		verifyTrue(insurrance.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent").contains(InsuranceFeePageObject.codeInsurrance));

		log.info("TC_01_Step_:Lay ma giao dich");
		transactionNumber = insurrance.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.TRANSACTION_CODE);

		log.info("TC_01_Step_:Click thuc hien giao dich moi");
		insurrance.clickToDynamicButton(driver, Insurance_Fee_Data.Valid_Account.NEW_TRANSACTION);

		log.info("TC_01_Step_: Chon tai khoan chuyen");
		insurrance.clickToDynamicDropDown(driver, Insurance_Fee_Data.Valid_Account.SOURCE_ACCOUNT);

		log.info("TC_01_Step_: Chon tai khoan chuyen");
		insurrance.clickToDynamicButtonLinkOrLinkText(driver, account);

		log.info("TC_01_Step_:Check so du kha dung sau khi chuyen tien");
		amountAfter = convertAvailableBalanceCurrentcyOrFeeToLong(insurrance.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.AMOUNT));

		log.info("TC_01_Step_:Check so du kha dung sau khi chuyen tien");
		verifyEquals(amountStart - amount - feeView, amountAfter);
	}

	@Test
	public void TC_06_Report_ThanhToanPhiBaoHiem_LanDau_PTXTSMSOTP () {
		log.info("TC_02_Step: Click back man hinh home");
		insurrance.clickImageBack("com.VCB:id/ivTitleLeft");

		log.info("TC_02_Step: Click menu header");
		insurrance.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

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
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.SERVICE), Insurance_Fee_Data.Valid_Account.INSURRANCE_FEE);

		log.info("TC_02: Kiem tra nha cung cap");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.PROVICE), Insurance_Fee_Data.Valid_Account.INSURRANCE_FWD);
		
		log.info("TC_02: Kiem tra don vi truc thuoc");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.UNIT), Insurance_Fee_Data.Valid_Account.PAYMENT_INSURRANCE_FIRST);

		log.info("TC_02: Kiem tra ma khach hang");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.CUSTOMER_CODE), InsuranceFeePageObject.codeInsurrance);

		log.info("TC_02: Check so tien giao dich");
		verifyEquals(convertAvailableBalanceCurrentcyOrFeeToLong(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_MONEY)), amount);

		log.info("TC_02: Check loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_TYPE), TransactionReport_Data.ReportTitle.PAYMENT_BILLING);

		log.info("TC_02: Click chi tiet giao dich");
		transReport.clickToDynamicBackIcon(driver, TransactionReport_Data.ReportTitle.TRANSACTION_DETAIL);

		log.info("TTC_02: Chon button back");
		insurrance.clickToDynamicBackIcon(driver, TransactionReport_Data.ReportTitle.TRANSACTION_REPORT);

		log.info("TC_02: Click button home");
		insurrance.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}
	
	@Test
	public void TC_07_ThanhToanPhiBaoHiem_TamUng_PTXTSmartOTP () throws InterruptedException, GeneralSecurityException, IOException {
		String passSmartOTP = getDataInCell(6);
		log.info("TC_01_Step_Click phi bao hiem");
		insurrance.scrollDownToText(driver, Insurance_Fee_Data.Valid_Account.INSURRANCE_FEE);
		insurrance.clickToDynamicButtonLinkOrLinkText(driver, Insurance_Fee_Data.Valid_Account.INSURRANCE_FEE);

		log.info("TC_01_Step_Select tai khoan nguon");
		insurrance.clickToDynamicDropDown(driver, Insurance_Fee_Data.Valid_Account.SOURCE_ACCOUNT);
		sourceAccount = insurrance.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);
		account = sourceAccount.account;

		log.info("TC_01_Step_Get so du kha dung");
		amountStart = convertAvailableBalanceCurrentcyOrFeeToLong(insurrance.getDynamicTextByLabel(driver, Insurance_Fee_Data.Valid_Account.AMOUNT));

		log.info("TC_01_Step_Chon cong ty bao hiem");
		insurrance.clickToDynamicRelavitelayout(driver, "com.VCB:id/provider");
		insurrance.clickToDynamicButtonLinkOrLinkText(driver, Insurance_Fee_Data.Valid_Account.INSURRANCE_FWD);
		
		log.info("TC_01_Step_Chon loai giao dich");
		insurrance.clickToDynamicTextID(driver, "com.VCB:id/subProvider");
		insurrance.clickToDynamicButtonLinkOrLinkText(driver, Insurance_Fee_Data.Valid_Account.PAYMENT_INSURRANCE_ADVANCE);

		log.info("TC_01_Input ma khach hang");
		insurrance.inputCustomerCode(codeAdvance);

		log.info("TC_01_Kiem tra man hinh xac nhan thong tin");
		log.info("TC_01_Kiem tra tai khoan nguon");
		verifyEquals(insurrance.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.SOURCE_ACCOUNT), account);

		log.info("TC_01_Kiem tra dich vu");
		verifyEquals(insurrance.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.SERVICE), Insurance_Fee_Data.Valid_Account.INSURRANCE_FEE);

		log.info("TC_01_Kiem tra nha cung cap");
		verifyEquals(insurrance.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.PROVICE), Insurance_Fee_Data.Valid_Account.INSURRANCE_FWD);
		
		log.info("TC_01_Kiem tra loai giao dich");
		verifyEquals(insurrance.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.TRANSACTION_TYPE), Insurance_Fee_Data.Valid_Account.PAYMENT_INSURRANCE_ADVANCE);

		log.info("TC_01_Kiem tra ma khach hang");
		verifyEquals(insurrance.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.CUSTOMER_CODE), InsuranceFeePageObject.codeInsurrance);
		
		log.info("TC_01_get ten chu hop dong");
		String name = insurrance.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.CUSTOMER_NAME);

		log.info("TC_01_Get so tien thanh toan");
		amount = convertAvailableBalanceCurrentcyOrFeeToLong(insurrance.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.PAYMENT_AMOUNT));
		
		log.info("TC_01_Verify thong tin them");
		verifyEquals(insurrance.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.ADD_INFO), "Chủ hợp đồng: "+ name + ", Tên loại phí: Thanh toán phí bảo hiể" );

		log.info("TC_01_Chon phuong thuc xac thuc");
		insurrance.clickToDynamicTextFollowingLinearlayout(driver, Insurance_Fee_Data.Valid_Account.SELECT_OPTION);
		insurrance.clickToDynamicButtonLinkOrLinkText(driver, "VCB - Smart OTP");

		log.info("TC_01_get so tien phi");
		feeView = convertAvailableBalanceCurrentcyOrFeeToLong(insurrance.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.FEE_AMOUNT));

		log.info("TC_01_Click Tiep tuc");
		insurrance.clickToDynamicContinue(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_Nhap ma xac thuc");
		insurrance.inputToDynamicSmartOTP(driver, passSmartOTP, "com.VCB:id/otp");

		log.info("TC_01_Step_Tiep tuc");
		insurrance.clickToDynamicContinue(driver, "com.VCB:id/submit");
		insurrance.clickToDynamicContinue(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Verify message thanh cong");
		verifyEquals(insurrance.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), Insurance_Fee_Data.Valid_Account.TRANSACTION_SUCCESS);

		log.info("TC_01_Verify so tien thanh toan");
		amountView = convertAvailableBalanceCurrentcyOrFeeToLong(insurrance.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount"));
		verifyEquals(amountView, amount);

		log.info("TC_01_get thoi gian giao dich thanh cong");
		transferTime = insurrance.getAccountNumber(driver, "com.VCB:id/tvTime").split(" ")[3];

		log.info("TC_01_Kiem tra dich vu");
		verifyEquals(insurrance.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.SERVICE), Insurance_Fee_Data.Valid_Account.INSURRANCE_FEE);

		log.info("TC_01_Kiem tra nha cung cap");
		verifyEquals(insurrance.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.PROVICE), Insurance_Fee_Data.Valid_Account.INSURRANCE_FWD);
		
		log.info("TC_01_Kiem tra loại giao dich");
		verifyEquals(insurrance.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.TRANSACTION_TYPE), Insurance_Fee_Data.Valid_Account.PAYMENT_INSURRANCE_ADVANCE);

		log.info("TC_01_Kiem tra ma khach hang");
		verifyEquals(insurrance.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.CUSTOMER_CODE), InsuranceFeePageObject.codeInsurrance);
		
		log.info("TC_01_verify noi dung");
		verifyTrue(insurrance.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent").contains(InsuranceFeePageObject.codeInsurrance));

		log.info("TC_01_Step_:Lay ma giao dich");
		transactionNumber = insurrance.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.TRANSACTION_CODE);

		log.info("TC_01_Step_:Click thuc hien giao dich moi");
		insurrance.clickToDynamicButton(driver, Insurance_Fee_Data.Valid_Account.NEW_TRANSACTION);

		log.info("TC_01_Step_: Chon tai khoan chuyen");
		insurrance.clickToDynamicDropDown(driver, Insurance_Fee_Data.Valid_Account.SOURCE_ACCOUNT);

		log.info("TC_01_Step_: Chon tai khoan chuyen");
		insurrance.clickToDynamicButtonLinkOrLinkText(driver, account);

		log.info("TC_01_Step_:Check so du kha dung sau khi chuyen tien");
		amountAfter = convertAvailableBalanceCurrentcyOrFeeToLong(insurrance.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.AMOUNT));

		log.info("TC_01_Step_:Check so du kha dung sau khi chuyen tien");
		verifyEquals(amountStart - amount - feeView, amountAfter);
	}

	@Test
	public void TC_08_Report_ThanhToanPhiBaoHiem_LanDau_PTXTSmartOTP () {
		log.info("TC_02_Step: Click back man hinh home");
		insurrance.clickImageBack("com.VCB:id/ivTitleLeft");

		log.info("TC_02_Step: Click menu header");
		insurrance.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

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
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.SERVICE), Insurance_Fee_Data.Valid_Account.INSURRANCE_FEE);

		log.info("TC_02: Kiem tra nha cung cap");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.PROVICE), Insurance_Fee_Data.Valid_Account.INSURRANCE_FWD);
		
		log.info("TC_02: Kiem tra don vi truc thuoc");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.UNIT), Insurance_Fee_Data.Valid_Account.PAYMENT_INSURRANCE_ADVANCE);

		log.info("TC_02: Kiem tra ma khach hang");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Insurance_Fee_Data.Valid_Account.CUSTOMER_CODE), InsuranceFeePageObject.codeInsurrance);

		log.info("TC_02: Check so tien giao dich");
		verifyEquals(convertAvailableBalanceCurrentcyOrFeeToLong(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_MONEY)), amount);

		log.info("TC_02: Check loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_TYPE), TransactionReport_Data.ReportTitle.PAYMENT_BILLING);

		log.info("TC_02: Click chi tiet giao dich");
		transReport.clickToDynamicBackIcon(driver, TransactionReport_Data.ReportTitle.TRANSACTION_DETAIL);

		log.info("TTC_02: Chon button back");
		insurrance.clickToDynamicBackIcon(driver, TransactionReport_Data.ReportTitle.TRANSACTION_REPORT);

		log.info("TC_02: Click button home");
		insurrance.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}
	

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		service.stop();
	}

}
