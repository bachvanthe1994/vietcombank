package vnpay.vietcombank.telecommunication_fee_VNPT;

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
import pageObjects.LogInPageObject;
import pageObjects.SettingVCBSmartOTPPageObject;
import pageObjects.TelecommunicationFeeVNPTPageObject;
import pageObjects.TransactionReportPageObject;
import vietcombank_test_data.TelecommunicationFee_VNPT_Data;
import vietcombank_test_data.TransactionReport_Data;

public class Telecommunication_Fee_VNPT_SmartOTP extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject home;
	private TelecommunicationFeeVNPTPageObject telecomFeeVNPT;
	private TransactionReportPageObject transactionReport;
	SourceAccountModel sourceAccount = new SourceAccountModel();
	private String sourceAccountMoney,vnpt_provider, customerID, moneyBill, transactionDate, transactionID,account,fee;
	private long transferFee;
	private SettingVCBSmartOTPPageObject smartOTP;
	List<String> vnpt_code = new ArrayList<String>();
	String passSmartOTP = "111222";
	
	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException, GeneralSecurityException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
		vnpt_code = Arrays.asList(getDataInCell(32).split(";"));
		vnpt_provider = getDataInCell(33);
		smartOTP = PageFactoryManager.getSettingVCBSmartOTPPageObject(driver);
		smartOTP.setupSmartOTP(passSmartOTP, getDataInCell(6));
	}

	
	@Test
	public void TC_01_ThanhToanCuocVienThongVNPT_SmartOTP () {

		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_01_Step_01: Hoa don VNPT ");
		home.clickToDynamicButtonLinkOrLinkText(driver, TelecommunicationFee_VNPT_Data.data.VNPT_PAYMENT);
		telecomFeeVNPT = PageFactoryManager.getTelecommunicationFeeVNPT(driver);

		log.info("TC_01_Step_02: Chon tai khoan nguon");
		telecomFeeVNPT.clickToTextID(driver, "com.VCB:id/number_account");
		sourceAccount = telecomFeeVNPT.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);
		account = sourceAccount.account;
		sourceAccountMoney = telecomFeeVNPT.getDynamicTextByLabel(driver, TelecommunicationFee_VNPT_Data.data.AMOUNT);

		log.info("TC_01_Step_03: Chon nha cung cap");
		telecomFeeVNPT.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/wrap_tv");
		telecomFeeVNPT.clickToDynamicButtonLinkOrLinkText(driver, vnpt_provider);

		log.info("TC_01_Step_04: Nhap ma khach hang va an tiep tuc");
		customerID = telecomFeeVNPT.inputCustomerId(vnpt_code);

		log.info("TC_01_Step_05: Hien thi man hinh xac nhan thong tin");
		verifyEquals(telecomFeeVNPT.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), TelecommunicationFee_VNPT_Data.data.VERIFY_INFO);

		log.info("TC_01_Step_06: Hien thi tai khoan nguon");
		verifyEquals(telecomFeeVNPT.getDynamicTextByLabel(driver, TelecommunicationFee_VNPT_Data.data.SOURCE_ACCOUNT), account);

		log.info("TC_01_Step_07: Hien thi ten dich vu");
		verifyEquals(telecomFeeVNPT.getDynamicTextByLabel(driver, TransactionReport_Data.ReportTitle.SERVICE), TelecommunicationFee_VNPT_Data.data.BILL_MESSAGE);

		log.info("TC_01_Step_08: Hien thi Nha cung cap");
		verifyEquals(telecomFeeVNPT.getDynamicTextByLabel(driver, TransactionReport_Data.ReportTitle.SUPPLIER), vnpt_provider);

		log.info("TC_01_Step_09: Hien thi ma khach hang");
		verifyEquals(telecomFeeVNPT.getDynamicTextByLabel(driver, TransactionReport_Data.ReportTitle.CUSTOMER_CODE_TEXT), customerID);

		log.info("TC_01_Step_10: Hien thi So tien thanh toan");
		moneyBill = telecomFeeVNPT.getDynamicTextByLabel(driver, TelecommunicationFee_VNPT_Data.data.PAYMENT_AMOUNT);

		log.info("TC_01_Step_11: Chon phuong thuc xac thuc");
		telecomFeeVNPT.scrollDownToText(driver, TelecommunicationFee_VNPT_Data.data.METHOD_VERIFY);
		telecomFeeVNPT.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/llptxt");
		telecomFeeVNPT.clickToDynamicButtonLinkOrLinkText(driver, TelecommunicationFee_VNPT_Data.data.Smart_OTP);

		log.info("TC_01_Step_12: Kiem tra so tien phi");
		fee = telecomFeeVNPT.getDynamicTextInTransactionDetail(driver, TelecommunicationFee_VNPT_Data.data.TRANSACTION_FEE);
		  transferFee = convertAvailableBalanceCurrentcyOrFeeToLong(fee);

		log.info("TC_01_Step_13: An nut Tiep Tuc");
		verifyEquals(telecomFeeVNPT.getDynamicTextButtonById(driver, "com.VCB:id/btContinue"), TelecommunicationFee_VNPT_Data.data.CONTINUE);
		telecomFeeVNPT.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_14: Nhap du ki tu vao o nhap OTP");
		telecomFeeVNPT.inputToDynamicSmartOTP(driver, passSmartOTP, "com.VCB:id/otp");

		log.info("TC_01_Step_15: An tiep button 'Tiep tuc'");
		telecomFeeVNPT.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");
		telecomFeeVNPT.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_16: Hien thi man hinh giao dich thanh cong");
		verifyEquals(telecomFeeVNPT.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), TelecommunicationFee_VNPT_Data.data.TRANSACTION_SUCCESS);

		log.info("TC_01_Step_17: Xac nhan hien thi dung so tien thanh toan");
		verifyEquals(telecomFeeVNPT.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount"), moneyBill);

		log.info("TC_01_Step_18: Xac nhan hien thi thoi gian giao dich");
		transactionDate = telecomFeeVNPT.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTime");
		verifyTrue(telecomFeeVNPT.isTextDisplayedInListTextElements(driver, getForWardDay(0), "com.VCB:id/tvTime"));

		log.info("TC_01_Step_19: Hien thi dung ten dich vu");
		verifyEquals(telecomFeeVNPT.getDynamicTextByLabel(driver, TransactionReport_Data.ReportTitle.SERVICE), TelecommunicationFee_VNPT_Data.data.VNPT_BILL_TEXT);

		log.info("TC_01_Step_20: Hien thi dung Nha cung cap");
		verifyEquals(telecomFeeVNPT.getDynamicTextByLabel(driver, TransactionReport_Data.ReportTitle.SUPPLIER), vnpt_provider);

		log.info("TC_01_Step_21: Hien thi dung ma khach hang");
		verifyEquals(telecomFeeVNPT.getDynamicTextByLabel(driver, TransactionReport_Data.ReportTitle.CUSTOMER_CODE_TEXT), customerID);

		log.info("TC_01_Step_22: Hien thi ma giao dich");
		transactionID = telecomFeeVNPT.getDynamicTextByLabel(driver, TelecommunicationFee_VNPT_Data.data.TRANSACTION_CODE);

		log.info("TC_01_Step_23: An tiep button 'Thuc hien giao dich moi'");
		verifyEquals(telecomFeeVNPT.getDynamicTextButtonById(driver, "com.VCB:id/btContinue"), TelecommunicationFee_VNPT_Data.data.NEW_TRANSACTION);
		telecomFeeVNPT.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_24: Hien thi man hinh Hoa don tien dien");
		verifyEquals(telecomFeeVNPT.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), TelecommunicationFee_VNPT_Data.data.VNPT_BILL_TEXT);

		log.info("TC_07_Step_25: Chon tai khoan nguon");
		telecomFeeVNPT.clickToTextID(driver, "com.VCB:id/number_account");
		telecomFeeVNPT.clickToDynamicButtonLinkOrLinkText(driver, account);

		log.info("TC_01_Step_26: Xac nhan so du TK nguon da bi tru thanh cong");
		sourceAccountMoney = (convertAvailableBalanceCurrentcyOrFeeToLong(sourceAccountMoney) - convertAvailableBalanceCurrentcyOrFeeToLong(moneyBill) - transferFee) + "";
		verifyEquals(telecomFeeVNPT.getDynamicTextByLabel(driver, TelecommunicationFee_VNPT_Data.data.AMOUNT), addCommasToLong(sourceAccountMoney) + " VND");

		log.info("TC_07_Step_27: Chon nha cung cap");
		telecomFeeVNPT.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/wrap_tv");
		telecomFeeVNPT.clickToDynamicButtonLinkOrLinkText(driver, vnpt_provider);

		log.info("TC_01_Step_28: Nhap ma khach hang");
		telecomFeeVNPT.inputToDynamicEditviewByLinearlayoutId(driver, customerID, "com.VCB:id/llCode");

		log.info("TC_01_Step_29: An nut Tiep Tuc");
		verifyEquals(telecomFeeVNPT.getDynamicTextButtonById(driver, "com.VCB:id/btn_submit"), TelecommunicationFee_VNPT_Data.data.CONTINUE);
		telecomFeeVNPT.clickToDynamicAcceptButton(driver, "com.VCB:id/btn_submit");

		log.info("TC_01_Step_30: Hien thi thong bao Ma khach hang khong con no truoc");
		verifyEquals(telecomFeeVNPT.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), TelecommunicationFee_VNPT_Data.data.BILL_MESSAGE);

		log.info("TC_01_Step_31: Click nut Dong tat pop-up");
		telecomFeeVNPT.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

		log.info("TC_01_Step_32: Click nut Back ve man hinh chinh");
		telecomFeeVNPT.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_01_Step_33: Click nut Back ve man hinh chinh");
		telecomFeeVNPT.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
	}

	@Test
	public void TC_02_ThanhToanCuocVienThongVNPT_OTP_BaoCaoGiaoDich() {

		log.info("TC_02_Step_01: Mo tab Menu");
		home.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_02_Step_02: Mo sub-menu 'Bao cao giao dich");
		home.clickToDynamicButtonLinkOrLinkText(driver, TransactionReport_Data.ReportTitle.TRANSACTION_REPORT);
		transactionReport = PageFactoryManager.getTransactionReportPageObject(driver);

		log.info("TC_02_Step_03: An vao Dropdown 'Tat ca cac loai giao dich");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectTransType");

		log.info("TC_02_Step_04: Chon 'Thanh toan hoa don'");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, TelecommunicationFee_VNPT_Data.data.VNPT_PAYMENT);

		log.info("TC_02_Step_05: An vao Dropdown 'Chon tai khoan/the");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_02_Step_06: Chon tai khoan vua thuc hien giao dich");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, account);

		log.info("TC_02_Step_07: An nut Tim kiem");
		transactionReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");

		log.info("TC_02_Step_08: An vao giao dich dau tien");
		transactionReport.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_02_Step_09: Xac nhan hien thi Title 'Chi tiet giao dich'");
		verifyEquals(transactionReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), TransactionReport_Data.ReportTitle.TRANSACTION_DETAIL);

		log.info("TC_02_Step_10: Xac nhan hien thi thoi gian giao dich");
		verifyTrue(transactionReport.isTextDisplayedInListTextElements(driver, convertDateTimeIgnoreHHmmss(transactionDate), "com.VCB:id/tvContent"));

		log.info("TC_02_Step_11: Xac nhan hien thi dung ma giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, TransactionReport_Data.ReportTitle.TRANSACTION_NUMBER), transactionID);

		log.info("TC_02_Step_12: Xac nhan hien thi so tai khoan giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, TransactionReport_Data.ReportTitle.ACCOUNT_TRANSFER), account);

		log.info("TC_02_Step_13: Hien thi dung ten dich vu");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, TransactionReport_Data.ReportTitle.SERVICE), TelecommunicationFee_VNPT_Data.data.VNPT_BILL_TEXT);

		log.info("TC_02_Step_14: Hien thi dung Nha cung cap");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, TransactionReport_Data.ReportTitle.SUPPLIER), vnpt_provider);

		log.info("TC_02_Step_15: Hien thi dung Ma khach hang");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, TransactionReport_Data.ReportTitle.CUSTOMER_CODE_TEXT), customerID);

		log.info("TC_02_Step_16: Xac nhan hien thi so tien giao dich");
		verifyTrue(transactionReport.isTextDisplayedInListTextElements(driver, moneyBill, "com.VCB:id/tvContent"));

		log.info("TC_02_Step_17: Xac nhan hien thi so tien phi");
		verifyTrue(transactionReport.isTextDisplayedInListTextElements(driver, transferFee + "", "com.VCB:id/tvContent"));

		log.info("TC_02_Step_18: Hien thi dung Loai giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, TransactionReport_Data.ReportTitle.TYPE_TRANSFER), TelecommunicationFee_VNPT_Data.data.VNPT_PAYMENT);

		log.info("TC_02_Step_19: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_02_Step_20: An nut back ve man hinh menu");
		transactionReport.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_02_Step_21: Mo tab Home");
		home.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		service.stop();
	}

}
