package vnpay.vietcombank.electric_bills;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
import pageObjects.ElectricBillPageObject;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.SettingVCBSmartOTPPageObject;
import pageObjects.TransactionReportPageObject;
import vietcombank_test_data.Electric_Bills_Data;
import vietcombank_test_data.Electric_Bills_Data.Electric_Text;
import vietcombank_test_data.TransactionReport_Data.ReportTitle;

public class Electric_Bills_Flow extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject home;
	private ElectricBillPageObject electricBill;
	private TransactionReportPageObject transactionReport;
	private SettingVCBSmartOTPPageObject smartOTP;

	SourceAccountModel sourceAccount = new SourceAccountModel();
	private List<String> listElectricBills = new ArrayList<String>();
	private List<String> listElectricBilltoUpperCase = new ArrayList<String>();
	private List<String> listElectricBilltoLowerCase = new ArrayList<String>();
	private String sourceAccountMoney, customerID, moneyBill, transactionDate, transactionID,smartOtpPass;
	private long transferFee;

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException, GeneralSecurityException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
		
		smartOtpPass = getDataInCell(6).trim();
		listElectricBills = Arrays.asList(getDataInCell(28).split(";"));
		listElectricBilltoUpperCase = listElectricBills.stream().map(String::toUpperCase).collect(Collectors.toList());
		listElectricBilltoLowerCase = listElectricBills.stream().map(String::toLowerCase).collect(Collectors.toList());
		
		electricBill = PageFactoryManager.getElectricBillPageObject(driver);
		transactionReport = PageFactoryManager.getTransactionReportPageObject(driver);
		
		smartOTP = PageFactoryManager.getSettingVCBSmartOTPPageObject(driver);
		smartOTP.setupSmartOTP(smartOtpPass, smartOtpPass);
		home = PageFactoryManager.getHomePageObject(driver);
	}

	@Parameters("otp")
	@Test
	public void TC_01_ThanhToanTienDien_OTP(String otp) {

		log.info("TC_01_Step_01: Hoa don tien dien");
		home.clickToDynamicButtonLinkOrLinkText(driver, Electric_Text.ELECTRIC_BILLS_TEXT);
	
		log.info("TC_01_Step_02: Chon tai khoan nguon");
		electricBill.clickToTextID(driver, "com.VCB:id/number_account");
		sourceAccount = electricBill.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);
		sourceAccountMoney = electricBill.getDynamicTextByLabel(driver, Electric_Text.SURPLUS_TEXT);

		log.info("TC_01_Step_03: Chon Nha cung cap EVN Mien Trung");
		electricBill.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/wrap_tv");
		electricBill.clickToDynamicButtonLinkOrLinkText(driver, Electric_Bills_Data.DATA.EVN_MIEN_TRUNG);

		log.info("TC_01_Step_04: Nhap ma khach hang va an tiep tuc");
		customerID = electricBill.inputCustomerId(listElectricBills);

		log.info("TC_01_Step_05: Hien thi man hinh xac nhan thong tin");
		verifyEquals(electricBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), Electric_Text.CONFIRM_INFO_TEXT);

		log.info("TC_01_Step_06: Hien thi tai khoan nguon");
		verifyEquals(electricBill.getDynamicTextByLabel(driver, Electric_Text.SOURCE_ACCOUNT_TEXT), sourceAccount.account);

		log.info("TC_01_Step_07: Hien thi ten dich vu");
		verifyEquals(electricBill.getDynamicTextByLabel(driver, Electric_Text.SERVICE_TEXT), Electric_Bills_Data.VALIDATE.ELECTIC_BILL_TITLE);

		log.info("TC_01_Step_08: Hien thi Nha cung cap");
		verifyEquals(electricBill.getDynamicTextByLabel(driver, Electric_Text.SUPPLIER_TEXT), Electric_Bills_Data.DATA.EVN_MIEN_TRUNG);

		log.info("TC_01_Step_09: Hien thi ma khach hang");
		verifyEquals(electricBill.getDynamicTextByLabel(driver, Electric_Text.CUSTOMER_ID_TEXT), customerID);

		log.info("TC_01_Step_10: Hien thi So tien thanh toan");
		moneyBill = electricBill.getDynamicTextByLabel(driver, Electric_Text.PAY_AMOUNT_TEXT);

		log.info("TC_01_Step_11: Chon phuong thuc xac thuc");
		electricBill.scrollDownToText(driver, Electric_Text.CHOOSE_AUTHEN_ACCOUNT_TEXT);
		electricBill.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/llptxt");
		electricBill.clickToDynamicButtonLinkOrLinkText(driver, Electric_Text.SMS_OTP_TEXT);

		log.info("TC_01_Step_12: Kiem tra so tien phi");
		transferFee = convertAvailableBalanceCurrentcyOrFeeToLong(electricBill.getDynamicTextInTransactionDetail(driver, Electric_Text.FEE_VALUE_TEXT));

		log.info("TC_01_Step_13: An nut Tiep Tuc");
		verifyEquals(electricBill.getDynamicTextButtonById(driver, "com.VCB:id/btContinue"), Electric_Text.BUTTON_CONTINUE_TEXT);
		electricBill.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_14: Nhap du ki tu vao o nhap OTP");
		electricBill.inputToDynamicOtp(driver, otp, Electric_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_01_Step_15: An tiep button 'Tiep tuc'");
		electricBill.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_16: Hien thi man hinh giao dich thanh cong");
		verifyEquals(electricBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), Electric_Text.TRANSACTION_SUCCESS_MESSAGE);

		log.info("TC_01_Step_17: Xac nhan hien thi dung so tien thanh toan");
		verifyEquals(electricBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount"), moneyBill);

		log.info("TC_01_Step_18: Xac nhan hien thi thoi gian giao dich");
		transactionDate = electricBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTime");
		verifyTrue(electricBill.isTextDisplayedInListTextElements(driver, getForWardDay(0), "com.VCB:id/tvTime"));

		log.info("TC_01_Step_19: Hien thi dung ten dich vu");
		verifyEquals(electricBill.getDynamicTextByLabel(driver, Electric_Text.SERVICE_TEXT), Electric_Bills_Data.VALIDATE.ELECTIC_BILL_TITLE);

		log.info("TC_01_Step_20: Hien thi dung Nha cung cap");
		verifyEquals(electricBill.getDynamicTextByLabel(driver, Electric_Text.SUPPLIER_TEXT), Electric_Bills_Data.DATA.EVN_MIEN_TRUNG);

		log.info("TC_01_Step_21: Hien thi dung ma khach hang");
		verifyEquals(electricBill.getDynamicTextByLabel(driver, Electric_Text.CUSTOMER_ID_TEXT), customerID);

		log.info("TC_01_Step_22: Hien thi ma giao dich");
		transactionID = electricBill.getDynamicTextByLabel(driver, Electric_Text.TRANSACTION_ID_TEXT);

		log.info("TC_01_Step_23: An tiep button 'Thuc hien giao dich moi'");
		verifyEquals(electricBill.getDynamicTextButtonById(driver, "com.VCB:id/btContinue"), Electric_Text.BUTTON_NEW_TRANSACTION_TEXT);
		electricBill.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_24: Hien thi man hinh Hoa don tien dien");
		verifyEquals(electricBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), Electric_Bills_Data.VALIDATE.ELECTIC_BILL_TITLE);

		log.info("TC_07_Step_25: Chon tai khoan nguon");
		electricBill.clickToTextID(driver, "com.VCB:id/number_account");
		electricBill.clickToDynamicButtonLinkOrLinkText(driver,sourceAccount.account);

		log.info("TC_01_Step_26: Xac nhan so du TK nguon da bi tru thanh cong");
		sourceAccountMoney = (convertAvailableBalanceCurrentcyOrFeeToLong(sourceAccountMoney) - convertAvailableBalanceCurrentcyOrFeeToLong(moneyBill) - transferFee) + "";
		verifyEquals(electricBill.getDynamicTextByLabel(driver, Electric_Text.SURPLUS_TEXT), addCommasToLong(sourceAccountMoney) + " VND");

		log.info("TC_01_Step_27: Chon Nha cung cap EVN Mien Trung");
		electricBill.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/wrap_tv");
		electricBill.clickToDynamicButtonLinkOrLinkText(driver, Electric_Bills_Data.DATA.EVN_MIEN_TRUNG);

		log.info("TC_01_Step_28: Nhap ma khach hang");
		electricBill.inputToDynamicEditviewByLinearlayoutId(driver, customerID, "com.VCB:id/llCode");

		log.info("TC_01_Step_29: An nut Tiep Tuc");
		verifyEquals(electricBill.getDynamicTextButtonById(driver, "com.VCB:id/btn_submit"), Electric_Text.BUTTON_CONTINUE_TEXT);
		electricBill.clickToDynamicAcceptButton(driver, "com.VCB:id/btn_submit");

		log.info("TC_01_Step_30: Hien thi thong bao Ma khach hang khong con no truoc");
		verifyEquals(electricBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), Electric_Bills_Data.VALIDATE.ELECTRIC_BILL_MESSAGE);

		log.info("TC_01_Step_31: Click nut Dong tat pop-up");
		electricBill.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

		log.info("TC_01_Step_32: Click nut Back ve man hinh chinh");
		electricBill.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_01_Step_33: Click nut Back ve man hinh chinh");
		electricBill.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
	}

	@Test
	public void TC_02_ThanhToanTienDien_OTP_BaoCaoGiaoDich() {

		log.info("TC_02_Step_01: Mo tab Menu");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_5");

		log.info("TC_02_Step_02: Mo sub-menu 'Bao cao giao dich");
		home.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSACTION_REPORT);
	
		log.info("TC_02_Step_03: An vao Dropdown 'Tat ca cac loai giao dich");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectTransType");

		log.info("TC_02_Step_04: Chon 'Thanh toan hoa don'");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.PAYMENT_BILLING);

		log.info("TC_02_Step_05: An vao Dropdown 'Chon tai khoan/the");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_02_Step_06: Chon tai khoan vua thuc hien giao dich");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_02_Step_07: An nut Tim kiem");
		transactionReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");

		log.info("TC_02_Step_08: An vao giao dich dau tien");
		transactionReport.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_02_Step_09: Xac nhan hien thi Title 'Chi tiet giao dich'");
		verifyEquals(transactionReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), ReportTitle.TRANSACTION_DETAIL);

		log.info("TC_02_Step_10: Xac nhan hien thi thoi gian giao dich");
		verifyTrue(transactionReport.isTextDisplayedInListTextElements(driver, convertDateTimeIgnoreHHmmss(transactionDate), "com.VCB:id/tvContent"));

		log.info("TC_02_Step_11: Xac nhan hien thi dung ma giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.TRANSACTION_NUMBER), transactionID);

		log.info("TC_02_Step_12: Xac nhan hien thi so tai khoan giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.ACCOUNT_CARD), sourceAccount.account);

		log.info("TC_02_Step_13: Hien thi dung ten dich vu");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.SERVICE_TEXT), Electric_Bills_Data.VALIDATE.ELECTIC_BILL_TITLE);

		log.info("TC_02_Step_14: Hien thi dung Nha cung cap");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.SUPPLIER_NAME_TEXT), Electric_Bills_Data.DATA.EVN_MIEN_TRUNG);

		log.info("TC_02_Step_15: Hien thi dung Ma khach hang");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.CUSTOMER_CODE_TEXT), customerID);

		log.info("TC_02_Step_16: Xac nhan hien thi so tien giao dich");
		verifyTrue(transactionReport.getDynamicTextByLabel(driver, ReportTitle.TRANSACTION_MONEY).contains(addCommasToLong(moneyBill)));

		log.info("TC_02_Step_17: Xac nhan hien thi so tien phi");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.TRANSACTION_FEE), addCommasToLong(transferFee+"")+" VND");

		log.info("TC_02_Step_19: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_02_Step_20: An nut back ve man hinh menu");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
	
		log.info("TC_02_Step_21: Mo tab Home");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_1");
	}

	@Parameters("pass")
	@Test
	public void TC_03_ThanhToanTienDien_MK(String pass) {

		log.info("TC_03_Step_01: Hoa don tien dien");
		home.clickToDynamicButtonLinkOrLinkText(driver, Electric_Text.ELECTRIC_BILLS_TEXT);
		
		log.info("TC_03_Step_02: Chon tai khoan nguon");
		electricBill.clickToTextID(driver, "com.VCB:id/number_account");
		sourceAccount = electricBill.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);
		sourceAccountMoney = electricBill.getDynamicTextByLabel(driver, Electric_Text.SURPLUS_TEXT);

		log.info("TC_03_Step_03: Chon Nha cung cap EVN Mien Trung");
		electricBill.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/wrap_tv");
		electricBill.clickToDynamicButtonLinkOrLinkText(driver, Electric_Bills_Data.DATA.EVN_MIEN_TRUNG);

		log.info("TC_03_Step_04: Nhap ma khach hang va an tiep tuc");
		customerID = electricBill.inputCustomerId(listElectricBills);

		log.info("TC_03_Step_05: Hien thi man hinh xac nhan thong tin");
		verifyEquals(electricBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), Electric_Text.CONFIRM_INFO_TEXT);

		log.info("TC_03_Step_06: Hien thi tai khoan nguon");
		verifyEquals(electricBill.getDynamicTextByLabel(driver, Electric_Text.SOURCE_ACCOUNT_TEXT), sourceAccount.account);

		log.info("TC_03_Step_07: Hien thi ten dich vu");
		verifyEquals(electricBill.getDynamicTextByLabel(driver, Electric_Text.SERVICE_TEXT), Electric_Bills_Data.VALIDATE.ELECTIC_BILL_TITLE);

		log.info("TC_03_Step_08: Hien thi Nha cung cap");
		verifyEquals(electricBill.getDynamicTextByLabel(driver, Electric_Text.SUPPLIER_TEXT), Electric_Bills_Data.DATA.EVN_MIEN_TRUNG);

		log.info("TC_03_Step_09: Hien thi ma khach hang");
		verifyEquals(electricBill.getDynamicTextByLabel(driver, Electric_Text.CUSTOMER_ID_TEXT), customerID);

		log.info("TC_03_Step_10: Hien thi So tien thanh toan");
		moneyBill = electricBill.getDynamicTextByLabel(driver, Electric_Text.PAY_AMOUNT_TEXT);

		log.info("TC_03_Step_11: Chon phuong thuc xac thuc");
		electricBill.scrollDownToText(driver, Electric_Text.CHOOSE_AUTHEN_ACCOUNT_TEXT);
		electricBill.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/llptxt");
		electricBill.clickToDynamicButtonLinkOrLinkText(driver, Electric_Text.PASSWORD_TEXT);

		log.info("TC_03_Step_12: Kiem tra so tien phi");
		transferFee = convertAvailableBalanceCurrentcyOrFeeToLong(electricBill.getDynamicTextInTransactionDetail(driver, Electric_Text.FEE_VALUE_TEXT));

		log.info("TC_03_Step_13: An nut Tiep Tuc");
		verifyEquals(electricBill.getDynamicTextButtonById(driver, "com.VCB:id/btContinue"), Electric_Text.BUTTON_CONTINUE_TEXT);
		electricBill.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_03_Step_14: Nhap du ki tu vao o nhap MK");
		electricBill.inputIntoEditTextByID(driver, pass, "com.VCB:id/pin");

		log.info("TC_03_Step_15: An tiep button 'Tiep tuc'");
		electricBill.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_03_Step_16: Hien thi man hinh giao dich thanh cong");
		verifyEquals(electricBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), Electric_Text.TRANSACTION_SUCCESS_MESSAGE);

		log.info("TC_03_Step_17: Xac nhan hien thi dung so tien thanh toan");
		verifyEquals(electricBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount"), moneyBill);

		log.info("TC_03_Step_18: Xac nhan hien thi thoi gian giao dich");
		transactionDate = electricBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTime");
		verifyTrue(electricBill.isTextDisplayedInListTextElements(driver, getForWardDay(0), "com.VCB:id/tvTime"));

		log.info("TC_03_Step_19: Hien thi dung ten dich vu");
		verifyEquals(electricBill.getDynamicTextByLabel(driver, Electric_Text.SERVICE_TEXT), Electric_Bills_Data.VALIDATE.ELECTIC_BILL_TITLE);

		log.info("TC_03_Step_20: Hien thi dung Nha cung cap");
		verifyEquals(electricBill.getDynamicTextByLabel(driver, Electric_Text.SUPPLIER_TEXT), Electric_Bills_Data.DATA.EVN_MIEN_TRUNG);

		log.info("TC_03_Step_21: Hien thi dung ma khach hang");
		verifyEquals(electricBill.getDynamicTextByLabel(driver, Electric_Text.CUSTOMER_ID_TEXT), customerID);

		log.info("TC_03_Step_22: Hien thi ma giao dich");
		transactionID = electricBill.getDynamicTextByLabel(driver, Electric_Text.TRANSACTION_ID_TEXT);

		log.info("TC_03_Step_23: An tiep button 'Thuc hien giao dich moi'");
		verifyEquals(electricBill.getDynamicTextButtonById(driver, "com.VCB:id/btContinue"), Electric_Text.BUTTON_NEW_TRANSACTION_TEXT);
		electricBill.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_03_Step_24: Hien thi man hinh Hoa don tien dien");
		verifyEquals(electricBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), Electric_Bills_Data.VALIDATE.ELECTIC_BILL_TITLE);

		log.info("TC_03_Step_25: Chon tai khoan nguon");
		electricBill.clickToTextID(driver, "com.VCB:id/number_account");
		electricBill.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_03_Step_26: Xac nhan so du TK nguon da bi tru thanh cong");
		sourceAccountMoney = (convertAvailableBalanceCurrentcyOrFeeToLong(sourceAccountMoney) - convertAvailableBalanceCurrentcyOrFeeToLong(moneyBill) - transferFee) + "";
		verifyEquals(electricBill.getDynamicTextByLabel(driver, Electric_Text.SURPLUS_TEXT), addCommasToLong(sourceAccountMoney) + " VND");

		log.info("TC_03_Step_27: Chon Nha cung cap EVN Mien Trung");
		electricBill.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/wrap_tv");
		electricBill.clickToDynamicButtonLinkOrLinkText(driver, Electric_Bills_Data.DATA.EVN_MIEN_TRUNG);

		log.info("TC_03_Step_28: Nhap ma khach hang");
		electricBill.inputToDynamicEditviewByLinearlayoutId(driver, customerID, "com.VCB:id/llCode");

		log.info("TC_03_Step_29: An nut Tiep Tuc");
		verifyEquals(electricBill.getDynamicTextButtonById(driver, "com.VCB:id/btn_submit"), Electric_Text.BUTTON_CONTINUE_TEXT);
		electricBill.clickToDynamicAcceptButton(driver, "com.VCB:id/btn_submit");

		log.info("TC_03_Step_30: Hien thi thong bao Ma khach hang khong con no truoc");
		verifyEquals(electricBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), Electric_Bills_Data.VALIDATE.ELECTRIC_BILL_MESSAGE);

		log.info("TC_03_Step_31: Click nut Dong tat pop-up");
		electricBill.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

		log.info("TC_03_Step_32: Click nut Back ve man hinh chinh");
		electricBill.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_03_Step_33: Click nut Back ve man hinh chinh");
		electricBill.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
	}

	@Test
	public void TC_04_ThanhToanTienDien_MK_BaoCaoGiaoDich() {

		log.info("TC_04_Step_01: Mo tab Menu");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_5");

		log.info("TC_04_Step_02: Mo sub-menu 'Bao cao giao dich");
		home.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSACTION_REPORT);
	
		log.info("TC_04_Step_03: An vao Dropdown 'Tat ca cac loai giao dich");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectTransType");

		log.info("TC_04_Step_04: Chon 'Thanh toan hoa don'");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.PAYMENT_BILLING);

		log.info("TC_04_Step_05: An vao Dropdown 'Chon tai khoan/the");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_04_Step_06: Chon tai khoan vua thuc hien giao dich");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_04_Step_07: An nut Tim kiem");
		transactionReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");

		log.info("TC_04_Step_08: An vao giao dich dau tien");
		transactionReport.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_04_Step_09: Xac nhan hien thi Title 'Chi tiet giao dich'");
		verifyEquals(transactionReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), ReportTitle.TRANSACTION_DETAIL);

		log.info("TC_04_Step_10: Xac nhan hien thi thoi gian giao dich");
		verifyTrue(transactionReport.isTextDisplayedInListTextElements(driver, convertDateTimeIgnoreHHmmss(transactionDate), "com.VCB:id/tvContent"));

		log.info("TC_04_Step_11: Xac nhan hien thi dung ma giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.TRANSACTION_NUMBER), transactionID);

		log.info("TC_04_Step_12: Xac nhan hien thi so tai khoan giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.ACCOUNT_CARD),sourceAccount.account);

		log.info("TC_04_Step_13: Hien thi dung ten dich vu");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.SERVICE_TEXT), Electric_Bills_Data.VALIDATE.ELECTIC_BILL_TITLE);

		log.info("TC_04_Step_14: Hien thi dung Nha cung cap");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.SUPPLIER_NAME_TEXT), Electric_Bills_Data.DATA.EVN_MIEN_TRUNG);

		log.info("TC_04_Step_15: Hien thi dung Ma khach hang");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.CUSTOMER_CODE_TEXT), customerID);

		log.info("TC_04_Step_16: Xac nhan hien thi so tien giao dich");
		verifyTrue(transactionReport.getDynamicTextByLabel(driver, ReportTitle.TRANSACTION_MONEY).contains(addCommasToLong(moneyBill)));

		log.info("TC_04_Step_17: Xac nhan hien thi so tien phi");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.TRANSACTION_FEE), addCommasToLong(transferFee+"")+" VND");

		log.info("TC_04_Step_19: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_04_Step_20: An nut back ve man hinh menu");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
	
		log.info("TC_04_Step_21: Mo tab Home");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_1");
	}
	
	@Test
	public void TC_05_ThanhToanTienDien_ToUpperCase_SmartOTP() {

		log.info("TC_05_Step_01: Hoa don tien dien");
		home.clickToDynamicButtonLinkOrLinkText(driver, Electric_Text.ELECTRIC_BILLS_TEXT);
	
		log.info("TC_05_Step_02: Chon tai khoan nguon");
		electricBill.clickToTextID(driver, "com.VCB:id/number_account");
		sourceAccount = electricBill.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);
		sourceAccountMoney = electricBill.getDynamicTextByLabel(driver, Electric_Text.SURPLUS_TEXT);

		log.info("TC_05_Step_03: Chon Nha cung cap EVN Mien Trung");
		electricBill.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/wrap_tv");
		electricBill.clickToDynamicButtonLinkOrLinkText(driver, Electric_Bills_Data.DATA.EVN_MIEN_TRUNG);

		log.info("TC_05_Step_04: Nhap ma khach hang va an tiep tuc");
		customerID = electricBill.inputCustomerId(listElectricBilltoUpperCase);

		log.info("TC_05_Step_05: Hien thi man hinh xac nhan thong tin");
		verifyEquals(electricBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), Electric_Text.CONFIRM_INFO_TEXT);

		log.info("TC_05_Step_06: Hien thi tai khoan nguon");
		verifyEquals(electricBill.getDynamicTextByLabel(driver, Electric_Text.SOURCE_ACCOUNT_TEXT), sourceAccount.account);

		log.info("TC_05_Step_07: Hien thi ten dich vu");
		verifyEquals(electricBill.getDynamicTextByLabel(driver, Electric_Text.SERVICE_TEXT), Electric_Bills_Data.VALIDATE.ELECTIC_BILL_TITLE);

		log.info("TC_05_Step_08: Hien thi Nha cung cap");
		verifyEquals(electricBill.getDynamicTextByLabel(driver, Electric_Text.SUPPLIER_TEXT), Electric_Bills_Data.DATA.EVN_MIEN_TRUNG);

		log.info("TC_05_Step_09: Hien thi ma khach hang");
		verifyEquals(electricBill.getDynamicTextByLabel(driver, Electric_Text.CUSTOMER_ID_TEXT), customerID);

		log.info("TC_05_Step_10: Hien thi So tien thanh toan");
		moneyBill = electricBill.getDynamicTextByLabel(driver, Electric_Text.PAY_AMOUNT_TEXT);

		log.info("TC_05_Step_11: Chon phuong thuc xac thuc");
		electricBill.scrollDownToText(driver, Electric_Text.CHOOSE_AUTHEN_ACCOUNT_TEXT);
		electricBill.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/llptxt");
		electricBill.clickToDynamicButtonLinkOrLinkText(driver, Electric_Text.SMART_OTP_TEXT);

		log.info("TC_05_Step_12: Kiem tra so tien phi");
		transferFee = convertAvailableBalanceCurrentcyOrFeeToLong(electricBill.getDynamicTextInTransactionDetail(driver, Electric_Text.FEE_VALUE_TEXT));

		log.info("TC_05_Step_13: An nut Tiep Tuc");
		verifyEquals(electricBill.getDynamicTextButtonById(driver, "com.VCB:id/btContinue"), Electric_Text.BUTTON_CONTINUE_TEXT);
		electricBill.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_05_Step_14: Nhap du ki tu vao o nhap Smart OTP");
		electricBill.inputToDynamicSmartOTP(driver, smartOtpPass, "com.VCB:id/otp");

		log.info("TC_05_Step_15: An tiep button 'Tiep tuc'");
		electricBill.clickToDynamicAcceptButton(driver, "com.VCB:id/submit");
		electricBill.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");
		
		log.info("TC_05_Step_16: Hien thi man hinh giao dich thanh cong");
		verifyEquals(electricBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), Electric_Text.TRANSACTION_SUCCESS_MESSAGE);

		log.info("TC_05_Step_17: Xac nhan hien thi dung so tien thanh toan");
		verifyEquals(electricBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount"), moneyBill);

		log.info("TC_05_Step_18: Xac nhan hien thi thoi gian giao dich");
		transactionDate = electricBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTime");
		verifyTrue(electricBill.isTextDisplayedInListTextElements(driver, getForWardDay(0), "com.VCB:id/tvTime"));

		log.info("TC_05_Step_19: Hien thi dung ten dich vu");
		verifyEquals(electricBill.getDynamicTextByLabel(driver, Electric_Text.SERVICE_TEXT), Electric_Bills_Data.VALIDATE.ELECTIC_BILL_TITLE);

		log.info("TC_05_Step_20: Hien thi dung Nha cung cap");
		verifyEquals(electricBill.getDynamicTextByLabel(driver, Electric_Text.SUPPLIER_TEXT), Electric_Bills_Data.DATA.EVN_MIEN_TRUNG);

		log.info("TC_05_Step_21: Hien thi dung ma khach hang");
		verifyEquals(electricBill.getDynamicTextByLabel(driver, Electric_Text.CUSTOMER_ID_TEXT), customerID);

		log.info("TC_05_Step_22: Hien thi ma giao dich");
		transactionID = electricBill.getDynamicTextByLabel(driver, Electric_Text.TRANSACTION_ID_TEXT);

		log.info("TC_05_Step_23: An tiep button 'Thuc hien giao dich moi'");
		verifyEquals(electricBill.getDynamicTextButtonById(driver, "com.VCB:id/btContinue"), Electric_Text.BUTTON_NEW_TRANSACTION_TEXT);
		electricBill.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_05_Step_24: Hien thi man hinh Hoa don tien dien");
		verifyEquals(electricBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), Electric_Bills_Data.VALIDATE.ELECTIC_BILL_TITLE);

		log.info("TC_07_Step_25: Chon tai khoan nguon");
		electricBill.clickToTextID(driver, "com.VCB:id/number_account");
		electricBill.clickToDynamicButtonLinkOrLinkText(driver,sourceAccount.account);

		log.info("TC_05_Step_26: Xac nhan so du TK nguon da bi tru thanh cong");
		sourceAccountMoney = (convertAvailableBalanceCurrentcyOrFeeToLong(sourceAccountMoney) - convertAvailableBalanceCurrentcyOrFeeToLong(moneyBill) - transferFee) + "";
		verifyEquals(electricBill.getDynamicTextByLabel(driver, Electric_Text.SURPLUS_TEXT), addCommasToLong(sourceAccountMoney) + " VND");

		log.info("TC_05_Step_27: Chon Nha cung cap EVN Mien Trung");
		electricBill.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/wrap_tv");
		electricBill.clickToDynamicButtonLinkOrLinkText(driver, Electric_Bills_Data.DATA.EVN_MIEN_TRUNG);

		log.info("TC_05_Step_28: Nhap ma khach hang");
		electricBill.inputToDynamicEditviewByLinearlayoutId(driver, customerID, "com.VCB:id/llCode");

		log.info("TC_05_Step_29: An nut Tiep Tuc");
		verifyEquals(electricBill.getDynamicTextButtonById(driver, "com.VCB:id/btn_submit"), Electric_Text.BUTTON_CONTINUE_TEXT);
		electricBill.clickToDynamicAcceptButton(driver, "com.VCB:id/btn_submit");

		log.info("TC_05_Step_30: Hien thi thong bao Ma khach hang khong con no truoc");
		verifyEquals(electricBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), Electric_Bills_Data.VALIDATE.ELECTRIC_BILL_MESSAGE);

		log.info("TC_05_Step_31: Click nut Dong tat pop-up");
		electricBill.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

		log.info("TC_05_Step_32: Click nut Back ve man hinh chinh");
		electricBill.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_05_Step_33: Click nut Back ve man hinh chinh");
		electricBill.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
	}

	@Test
	public void TC_06_ThanhToanTienDien_ToUpperCase_SmartOTP_BaoCaoGiaoDich() {

		log.info("TC_06_Step_01: Mo tab Menu");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_5");

		log.info("TC_06_Step_02: Mo sub-menu 'Bao cao giao dich");
		home.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSACTION_REPORT);
	
		log.info("TC_06_Step_03: An vao Dropdown 'Tat ca cac loai giao dich");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectTransType");

		log.info("TC_06_Step_04: Chon 'Thanh toan hoa don'");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.PAYMENT_BILLING);

		log.info("TC_06_Step_05: An vao Dropdown 'Chon tai khoan/the");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_06_Step_06: Chon tai khoan vua thuc hien giao dich");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_06_Step_07: An nut Tim kiem");
		transactionReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");

		log.info("TC_06_Step_08: An vao giao dich dau tien");
		transactionReport.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_06_Step_09: Xac nhan hien thi Title 'Chi tiet giao dich'");
		verifyEquals(transactionReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), ReportTitle.TRANSACTION_DETAIL);

		log.info("TC_06_Step_10: Xac nhan hien thi thoi gian giao dich");
		verifyTrue(transactionReport.isTextDisplayedInListTextElements(driver, convertDateTimeIgnoreHHmmss(transactionDate), "com.VCB:id/tvContent"));

		log.info("TC_06_Step_11: Xac nhan hien thi dung ma giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.TRANSACTION_NUMBER), transactionID);

		log.info("TC_06_Step_12: Xac nhan hien thi so tai khoan giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.ACCOUNT_CARD), sourceAccount.account);

		log.info("TC_06_Step_13: Hien thi dung ten dich vu");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.SERVICE_TEXT), Electric_Bills_Data.VALIDATE.ELECTIC_BILL_TITLE);

		log.info("TC_06_Step_14: Hien thi dung Nha cung cap");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.SUPPLIER_NAME_TEXT), Electric_Bills_Data.DATA.EVN_MIEN_TRUNG);

		log.info("TC_06_Step_15: Hien thi dung Ma khach hang");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.CUSTOMER_CODE_TEXT), customerID);

		log.info("TC_06_Step_16: Xac nhan hien thi so tien giao dich");
		verifyTrue(transactionReport.getDynamicTextByLabel(driver, ReportTitle.TRANSACTION_MONEY).contains(addCommasToLong(moneyBill)));

		log.info("TC_06_Step_19: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_06_Step_20: An nut back ve man hinh menu");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
	
		log.info("TC_06_Step_21: Mo tab Home");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_1");
	}
	
	@Test
	public void TC_07_ThanhToanTienDien_ToLowerCase_SmartOTP() {

		log.info("TC_07_Step_01: Hoa don tien dien");
		home.clickToDynamicButtonLinkOrLinkText(driver, Electric_Text.ELECTRIC_BILLS_TEXT);
	
		log.info("TC_07_Step_02: Chon tai khoan nguon");
		electricBill.clickToTextID(driver, "com.VCB:id/number_account");
		sourceAccount = electricBill.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);
		sourceAccountMoney = electricBill.getDynamicTextByLabel(driver, Electric_Text.SURPLUS_TEXT);

		log.info("TC_07_Step_03: Chon Nha cung cap EVN Mien Trung");
		electricBill.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/wrap_tv");
		electricBill.clickToDynamicButtonLinkOrLinkText(driver, Electric_Bills_Data.DATA.EVN_MIEN_TRUNG);

		log.info("TC_07_Step_04: Nhap ma khach hang va an tiep tuc");
		customerID = electricBill.inputCustomerId(listElectricBilltoLowerCase);

		log.info("TC_07_Step_05: Hien thi man hinh xac nhan thong tin");
		verifyEquals(electricBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), Electric_Text.CONFIRM_INFO_TEXT);

		log.info("TC_07_Step_06: Hien thi tai khoan nguon");
		verifyEquals(electricBill.getDynamicTextByLabel(driver, Electric_Text.SOURCE_ACCOUNT_TEXT), sourceAccount.account);

		log.info("TC_07_Step_07: Hien thi ten dich vu");
		verifyEquals(electricBill.getDynamicTextByLabel(driver, Electric_Text.SERVICE_TEXT), Electric_Bills_Data.VALIDATE.ELECTIC_BILL_TITLE);

		log.info("TC_07_Step_08: Hien thi Nha cung cap");
		verifyEquals(electricBill.getDynamicTextByLabel(driver, Electric_Text.SUPPLIER_TEXT), Electric_Bills_Data.DATA.EVN_MIEN_TRUNG);

		log.info("TC_07_Step_09: Hien thi ma khach hang");
		verifyEquals(electricBill.getDynamicTextByLabel(driver, Electric_Text.CUSTOMER_ID_TEXT), customerID.toUpperCase());

		log.info("TC_07_Step_10: Hien thi So tien thanh toan");
		moneyBill = electricBill.getDynamicTextByLabel(driver, Electric_Text.PAY_AMOUNT_TEXT);

		log.info("TC_07_Step_11: Chon phuong thuc xac thuc");
		electricBill.scrollDownToText(driver, Electric_Text.CHOOSE_AUTHEN_ACCOUNT_TEXT);
		electricBill.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/llptxt");
		electricBill.clickToDynamicButtonLinkOrLinkText(driver, Electric_Text.SMART_OTP_TEXT);

		log.info("TC_07_Step_12: Kiem tra so tien phi");
		transferFee = convertAvailableBalanceCurrentcyOrFeeToLong(electricBill.getDynamicTextInTransactionDetail(driver, Electric_Text.FEE_VALUE_TEXT));

		log.info("TC_07_Step_13: An nut Tiep Tuc");
		verifyEquals(electricBill.getDynamicTextButtonById(driver, "com.VCB:id/btContinue"), Electric_Text.BUTTON_CONTINUE_TEXT);
		electricBill.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_07_Step_14: Nhap du ki tu vao o nhap OTP");
		electricBill.inputToDynamicSmartOTP(driver, smartOtpPass, "com.VCB:id/otp");

		log.info("TC_07_Step_15: An tiep button 'Tiep tuc'");
		electricBill.clickToDynamicAcceptButton(driver, "com.VCB:id/submit");
		electricBill.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_07_Step_16: Hien thi man hinh giao dich thanh cong");
		verifyEquals(electricBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), Electric_Text.TRANSACTION_SUCCESS_MESSAGE);

		log.info("TC_07_Step_17: Xac nhan hien thi dung so tien thanh toan");
		verifyEquals(electricBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount"), moneyBill);

		log.info("TC_07_Step_18: Xac nhan hien thi thoi gian giao dich");
		transactionDate = electricBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTime");
		verifyTrue(electricBill.isTextDisplayedInListTextElements(driver, getForWardDay(0), "com.VCB:id/tvTime"));

		log.info("TC_07_Step_19: Hien thi dung ten dich vu");
		verifyEquals(electricBill.getDynamicTextByLabel(driver, Electric_Text.SERVICE_TEXT), Electric_Bills_Data.VALIDATE.ELECTIC_BILL_TITLE);

		log.info("TC_07_Step_20: Hien thi dung Nha cung cap");
		verifyEquals(electricBill.getDynamicTextByLabel(driver, Electric_Text.SUPPLIER_TEXT), Electric_Bills_Data.DATA.EVN_MIEN_TRUNG);

		log.info("TC_07_Step_21: Hien thi dung ma khach hang");
		verifyEquals(electricBill.getDynamicTextByLabel(driver, Electric_Text.CUSTOMER_ID_TEXT), customerID.toUpperCase());

		log.info("TC_07_Step_22: Hien thi ma giao dich");
		transactionID = electricBill.getDynamicTextByLabel(driver, Electric_Text.TRANSACTION_ID_TEXT);

		log.info("TC_07_Step_23: An tiep button 'Thuc hien giao dich moi'");
		verifyEquals(electricBill.getDynamicTextButtonById(driver, "com.VCB:id/btContinue"), Electric_Text.BUTTON_NEW_TRANSACTION_TEXT);
		electricBill.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_07_Step_24: Hien thi man hinh Hoa don tien dien");
		verifyEquals(electricBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), Electric_Bills_Data.VALIDATE.ELECTIC_BILL_TITLE);

		log.info("TC_07_Step_25: Chon tai khoan nguon");
		electricBill.clickToTextID(driver, "com.VCB:id/number_account");
		electricBill.clickToDynamicButtonLinkOrLinkText(driver,sourceAccount.account);

		log.info("TC_07_Step_26: Xac nhan so du TK nguon da bi tru thanh cong");
		sourceAccountMoney = (convertAvailableBalanceCurrentcyOrFeeToLong(sourceAccountMoney) - convertAvailableBalanceCurrentcyOrFeeToLong(moneyBill) - transferFee) + "";
		verifyEquals(electricBill.getDynamicTextByLabel(driver, Electric_Text.SURPLUS_TEXT), addCommasToLong(sourceAccountMoney) + " VND");

		log.info("TC_07_Step_27: Chon Nha cung cap EVN Mien Trung");
		electricBill.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/wrap_tv");
		electricBill.clickToDynamicButtonLinkOrLinkText(driver, Electric_Bills_Data.DATA.EVN_MIEN_TRUNG);

		log.info("TC_07_Step_28: Nhap ma khach hang");
		electricBill.inputToDynamicEditviewByLinearlayoutId(driver, customerID, "com.VCB:id/llCode");

		log.info("TC_07_Step_29: An nut Tiep Tuc");
		verifyEquals(electricBill.getDynamicTextButtonById(driver, "com.VCB:id/btn_submit"), Electric_Text.BUTTON_CONTINUE_TEXT);
		electricBill.clickToDynamicAcceptButton(driver, "com.VCB:id/btn_submit");

		log.info("TC_07_Step_30: Hien thi thong bao Ma khach hang khong con no truoc");
		verifyEquals(electricBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), Electric_Bills_Data.VALIDATE.ELECTRIC_BILL_MESSAGE);

		log.info("TC_07_Step_31: Click nut Dong tat pop-up");
		electricBill.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

		log.info("TC_07_Step_32: Click nut Back ve man hinh chinh");
		electricBill.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_07_Step_33: Click nut Back ve man hinh chinh");
		electricBill.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
	}

	@Test
	public void TC_08_ThanhToanTienDien_ToLowerCase_SmartOTP_BaoCaoGiaoDich() {

		log.info("TC_08_Step_01: Mo tab Menu");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_5");

		log.info("TC_08_Step_02: Mo sub-menu 'Bao cao giao dich");
		home.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSACTION_REPORT);
	
		log.info("TC_08_Step_03: An vao Dropdown 'Tat ca cac loai giao dich");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectTransType");

		log.info("TC_08_Step_04: Chon 'Thanh toan hoa don'");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.PAYMENT_BILLING);

		log.info("TC_08_Step_05: An vao Dropdown 'Chon tai khoan/the");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_08_Step_06: Chon tai khoan vua thuc hien giao dich");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_08_Step_07: An nut Tim kiem");
		transactionReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");

		log.info("TC_08_Step_08: An vao giao dich dau tien");
		transactionReport.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_08_Step_09: Xac nhan hien thi Title 'Chi tiet giao dich'");
		verifyEquals(transactionReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), ReportTitle.TRANSACTION_DETAIL);

		log.info("TC_08_Step_10: Xac nhan hien thi thoi gian giao dich");
		verifyTrue(transactionReport.isTextDisplayedInListTextElements(driver, convertDateTimeIgnoreHHmmss(transactionDate), "com.VCB:id/tvContent"));

		log.info("TC_08_Step_11: Xac nhan hien thi dung ma giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.TRANSACTION_NUMBER), transactionID);

		log.info("TC_08_Step_12: Xac nhan hien thi so tai khoan giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.ACCOUNT_CARD), sourceAccount.account);

		log.info("TC_08_Step_13: Hien thi dung ten dich vu");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.SERVICE_TEXT), Electric_Bills_Data.VALIDATE.ELECTIC_BILL_TITLE);

		log.info("TC_08_Step_14: Hien thi dung Nha cung cap");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.SUPPLIER_NAME_TEXT), Electric_Bills_Data.DATA.EVN_MIEN_TRUNG);

		log.info("TC_08_Step_15: Hien thi dung Ma khach hang");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.CUSTOMER_CODE_TEXT), customerID.toUpperCase());

		log.info("TC_08_Step_16: Xac nhan hien thi so tien giao dich");
		verifyTrue(transactionReport.getDynamicTextByLabel(driver, ReportTitle.TRANSACTION_MONEY).contains(addCommasToLong(moneyBill)));

		log.info("TC_08_Step_19: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_08_Step_20: An nut back ve man hinh menu");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
	
		log.info("TC_08_Step_21: Mo tab Home");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_1");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		service.stop();
	}

}
