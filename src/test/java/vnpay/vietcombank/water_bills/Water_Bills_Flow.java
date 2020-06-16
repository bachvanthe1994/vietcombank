package vnpay.vietcombank.water_bills;

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
import pageObjects.TransactionReportPageObject;
import pageObjects.WaterBillPageObject;
import vietcombank_test_data.HomePage_Data;
import vietcombank_test_data.TransactionReport_Data.ReportTitle;
import vietcombank_test_data.TransferMoneyInVCB_Data.TittleData;
import vietcombank_test_data.Water_Bills_Data;
import vietcombank_test_data.Water_Bills_Data.TITTLE;

public class Water_Bills_Flow extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject home;
	private WaterBillPageObject waterBill;
	private TransactionReportPageObject transactionReport;

	private String sourceAccountMoney, customerID, moneyBill, transactionDate, transactionID;
	SourceAccountModel sourceAccount = new SourceAccountModel();
	private long transferFee;
	
	List<String> listCusstomerID = new ArrayList<String>();


	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException, GeneralSecurityException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
		listCusstomerID = Arrays.asList(getDataInCell(35).split(";"));

	}

	@Parameters("otp")
	@Test
	public void TC_01_ThanhToanTienNuoc_OTP(String otp) {

		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_01_Step_01: Hoa don tien dien");
		home.clickToDynamicButtonLinkOrLinkText(driver, HomePage_Data.Home_Text_Elements.PAYMENT_WATER_BILL);
		waterBill = PageFactoryManager.getWaterBillPageObject(driver);

		log.info("TC_01_Step_02: Chon tai khoan nguon");
		waterBill.clickToTextID(driver, "com.VCB:id/number_account");

		sourceAccount = waterBill.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		sourceAccountMoney = waterBill.getDynamicTextByLabel(driver, TITTLE.AVAILIBLE_BALANCES);

		log.info("TC_01_Step_03: Chon nha cung cap");
		waterBill.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/wrap_tv");
		waterBill.clickToDynamicButtonLinkOrLinkText(driver, Water_Bills_Data.DATA.WATER_DANANG);

		log.info("TC_01_Step_04: Nhap ma khach hang va an tiep tuc");
		customerID = waterBill.inputCustomerId(listCusstomerID);

		log.info("TC_01_Step_05: Hien thi man hinh xac nhan thong tin");
		verifyEquals(waterBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), TITTLE.CONFIRM_INFO);

		log.info("TC_01_Step_06: Hien thi tai khoan nguon");
		verifyEquals(waterBill.getDynamicTextByLabel(driver, TITTLE.SOURCE_ACCOUNT), sourceAccount.account);

		log.info("TC_01_Step_07: Hien thi ten dich vu");
		verifyEquals(waterBill.getDynamicTextByLabel(driver, TITTLE.SERVICE), Water_Bills_Data.DATA.WATER_BILL_TEXT);

		log.info("TC_01_Step_08: Hien thi Nha cung cap");
		verifyEquals(waterBill.getDynamicTextByLabel(driver, TITTLE.SUPPLIER), Water_Bills_Data.DATA.WATER_DANANG);

		log.info("TC_01_Step_09: Hien thi ma khach hang");
		verifyEquals(waterBill.getDynamicTextByLabel(driver, TITTLE.CODE_CUSTOMER), customerID);

		log.info("TC_01_Step_10: Hien thi So tien thanh toan");
		moneyBill = waterBill.getDynamicTextByLabel(driver, TITTLE.PAYMENT_AMOUNT);

		log.info("TC_01_Step_11: Chon phuong thuc xac thuc");
		waterBill.scrollDownToText(driver, TITTLE.CHOICE_METHOD_VERIFY);
		waterBill.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/llptxt");
		waterBill.clickToDynamicButtonLinkOrLinkText(driver, TITTLE.SMS_OTP);

		transferFee = convertAvailableBalanceCurrentcyOrFeeToLong(waterBill.getDynamicTextInTransactionDetail(driver, TITTLE.FEE_AMOUNT));

		log.info("TC_01_Step_12: Kiem tra so tien phi");
		verifyEquals(waterBill.getDynamicTextInTransactionDetail(driver, TittleData.FEE_AMOUNT), addCommasToLong(transferFee + "") + " VND");

		log.info("TC_01_Step_13: An nut Tiep Tuc");
		verifyEquals(waterBill.getDynamicTextButtonById(driver, "com.VCB:id/btContinue"), TITTLE.CONTINUE_BUTTON);
		waterBill.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_14: Nhap du ki tu vao o nhap OTP");
		waterBill.inputToDynamicOtp(driver, otp, TITTLE.CONTINUE_BUTTON);

		log.info("TC_01_Step_15: An tiep button 'Tiep tuc'");
		waterBill.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_16: Hien thi man hinh giao dich thanh cong");
		verifyEquals(waterBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), TITTLE.TRANSACTION_SUCCESS);

		log.info("TC_01_Step_17: Xac nhan hien thi dung so tien thanh toan");
		verifyEquals(waterBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount"), moneyBill);

		log.info("TC_01_Step_18: Xac nhan hien thi thoi gian giao dich");
		transactionDate = waterBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTime");
		verifyTrue(waterBill.isTextDisplayedInListTextElements(driver, getForWardDay(0), "com.VCB:id/tvTime"));

		log.info("TC_01_Step_19: Hien thi dung ten dich vu");
		verifyEquals(waterBill.getDynamicTextByLabel(driver, TITTLE.SERVICE), Water_Bills_Data.DATA.WATER_BILL_TEXT);

		log.info("TC_01_Step_20: Hien thi dung Nha cung cap");
		verifyEquals(waterBill.getDynamicTextByLabel(driver, TITTLE.SUPPLIER), Water_Bills_Data.DATA.WATER_DANANG);

		log.info("TC_01_Step_21: Hien thi dung ma khach hang");
		verifyEquals(waterBill.getDynamicTextByLabel(driver, TITTLE.CODE_CUSTOMER), customerID);

		log.info("TC_01_Step_22: Hien thi ma giao dich");
		transactionID = waterBill.getDynamicTextByLabel(driver, TITTLE.CODE_TRANSACTION);

		log.info("TC_01_Step_23: An tiep button 'Thuc hien giao dich moi'");
		verifyEquals(waterBill.getDynamicTextButtonById(driver, "com.VCB:id/btContinue"), TITTLE.PERFORM_NEW_TRANSFER);
		waterBill.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_24: Hien thi man hinh Hoa don tien dien");
		verifyEquals(waterBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), Water_Bills_Data.DATA.WATER_BILL_TEXT);

		log.info("TC_07_Step_25: Chon tai khoan nguon");
		waterBill.clickToTextID(driver, "com.VCB:id/number_account");
		waterBill.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_01_Step_26: Xac nhan so du TK nguon da bi tru thanh cong");
		sourceAccountMoney = (convertAvailableBalanceCurrentcyOrFeeToLong(sourceAccountMoney) - convertAvailableBalanceCurrentcyOrFeeToLong(moneyBill) - transferFee) + "";
		verifyEquals(waterBill.getDynamicTextByLabel(driver, TITTLE.AVAILIBLE_BALANCES), addCommasToLong(sourceAccountMoney) + " VND");

		log.info("TC_07_Step_27: Chon nha cung cap");
		waterBill.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/wrap_tv");
		waterBill.clickToDynamicButtonLinkOrLinkText(driver, Water_Bills_Data.DATA.WATER_DANANG);

		log.info("TC_01_Step_28: Nhap ma khach hang");
		waterBill.inputToDynamicEditviewByLinearlayoutId(driver, customerID, "com.VCB:id/llCode");

		log.info("TC_01_Step_29: An nut Tiep Tuc");
		verifyEquals(waterBill.getDynamicTextButtonById(driver, "com.VCB:id/btn_submit"), TITTLE.CONTINUE_BUTTON);
		waterBill.clickToDynamicAcceptButton(driver, "com.VCB:id/btn_submit");

		log.info("TC_01_Step_30: Hien thi thong bao Ma khach hang khong con no truoc");
		verifyEquals(waterBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), Water_Bills_Data.DATA.BILL_MESSAGE);

		log.info("TC_01_Step_31: Click nut Dong tat pop-up");
		waterBill.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

		log.info("TC_01_Step_32: Click nut Back ve man hinh chinh");
		waterBill.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_01_Step_33: Click nut Back ve man hinh chinh");
		waterBill.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
	}

	@Test
	public void TC_02_ThanhToanTienNuoc_OTP_BaoCaoGiaoDich() {

		log.info("TC_02_Step_01: Mo tab Menu");
		home.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_02_Step_02: Mo sub-menu 'Bao cao giao dich");
		home.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSACTION_REPORT);
		transactionReport = PageFactoryManager.getTransactionReportPageObject(driver);

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
		verifyEquals(transactionReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), ReportTitle.DETAIL_TRANSFER);

		log.info("TC_02_Step_10: Xac nhan hien thi thoi gian giao dich");
		verifyTrue(transactionReport.isTextDisplayedInListTextElements(driver, convertDateTimeIgnoreHHmmss(transactionDate), "com.VCB:id/tvContent"));

		log.info("TC_02_Step_11: Xac nhan hien thi dung ma giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.TRANSACTION_NUMBER), transactionID);

		log.info("TC_02_Step_12: Xac nhan hien thi so tai khoan giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.ACCOUNT_CARD_SOURCE), sourceAccount.account);

		log.info("TC_02_Step_13: Hien thi dung ten dich vu");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.SERVICE), Water_Bills_Data.DATA.WATER_BILL_TEXT);

		log.info("TC_02_Step_14: Hien thi dung Nha cung cap");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.SUPPLIER), Water_Bills_Data.DATA.WATER_DANANG);

		log.info("TC_02_Step_15: Hien thi dung Ma khach hang");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.CUSTOMER_CODE_TEXT), customerID);

		log.info("TC_02_Step_16: Xac nhan hien thi so tien giao dich");
		verifyTrue(transactionReport.isTextDisplayedInListTextElements(driver, moneyBill, "com.VCB:id/tvContent"));

		log.info("TC_02_Step_17: Xac nhan hien thi so tien phi");
		verifyTrue(transactionReport.isTextDisplayedInListTextElements(driver, transferFee + "", "com.VCB:id/tvContent"));

		log.info("TC_02_Step_18: Hien thi dung Loai giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.TRANSACTION_TYPE), ReportTitle.PAYMENT_BILLING);

		log.info("TC_02_Step_19: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_02_Step_20: An nut back ve man hinh menu");
		transactionReport.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_02_Step_21: Mo tab Home");
		home.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@Parameters("pass")
	@Test
	public void TC_03_ThanhToanTienNuoc_MK(String pass) {

		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_03_Step_01: Hoa don tien dien");
		home.clickToDynamicButtonLinkOrLinkText(driver, HomePage_Data.Home_Text_Elements.PAYMENT_WATER_BILL);
		waterBill = PageFactoryManager.getWaterBillPageObject(driver);

		log.info("TC_03_Step_02: Chon tai khoan nguon");
		waterBill.clickToTextID(driver, "com.VCB:id/number_account");

		sourceAccount = waterBill.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		sourceAccountMoney = waterBill.getDynamicTextByLabel(driver, TITTLE.AVAILIBLE_BALANCES);

		log.info("TC_03_Step_03: Chon nha cung cap");
		waterBill.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/wrap_tv");
		waterBill.clickToDynamicButtonLinkOrLinkText(driver, Water_Bills_Data.DATA.WATER_DANANG);

		log.info("TC_03_Step_04: Nhap ma khach hang va an tiep tuc");
		customerID = waterBill.inputCustomerId(listCusstomerID);

		log.info("TC_03_Step_05: Hien thi man hinh xac nhan thong tin");
		verifyEquals(waterBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), TITTLE.CONFIRM_INFO);

		log.info("TC_03_Step_06: Hien thi tai khoan nguon");
		verifyEquals(waterBill.getDynamicTextByLabel(driver, TITTLE.SOURCE_ACCOUNT), sourceAccount.account);

		log.info("TC_03_Step_07: Hien thi ten dich vu");
		verifyEquals(waterBill.getDynamicTextByLabel(driver, TITTLE.SERVICE), Water_Bills_Data.DATA.WATER_BILL_TEXT);

		log.info("TC_03_Step_08: Hien thi Nha cung cap");
		verifyEquals(waterBill.getDynamicTextByLabel(driver, TITTLE.SUPPLIER), Water_Bills_Data.DATA.WATER_DANANG);

		log.info("TC_03_Step_09: Hien thi ma khach hang");
		verifyEquals(waterBill.getDynamicTextByLabel(driver, TITTLE.CODE_CUSTOMER), customerID);

		log.info("TC_03_Step_10: Hien thi So tien thanh toan");
		moneyBill = waterBill.getDynamicTextByLabel(driver, TITTLE.PAYMENT_AMOUNT);

		log.info("TC_03_Step_11: Chon phuong thuc xac thuc");
		waterBill.scrollDownToText(driver, TITTLE.CHOICE_METHOD_VERIFY);
		waterBill.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/llptxt");
		waterBill.clickToDynamicButtonLinkOrLinkText(driver, TITTLE.LOGIN_PASSWORD);

		transferFee = convertAvailableBalanceCurrentcyOrFeeToLong(waterBill.getDynamicTextInTransactionDetail(driver, TITTLE.FEE_AMOUNT));

		log.info("TC_03_Step_12: Kiem tra so tien phi");
		verifyEquals(waterBill.getDynamicTextInTransactionDetail(driver, TittleData.FEE_AMOUNT), addCommasToLong(transferFee + "") + " VND");

		log.info("TC_03_Step_13: An nut Tiep Tuc");
		verifyEquals(waterBill.getDynamicTextButtonById(driver, "com.VCB:id/btContinue"), TITTLE.CONTINUE_BUTTON);
		waterBill.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_03_Step_14: Nhap du ki tu vao o nhap MK");
		waterBill.inputIntoEditTextByID(driver, pass, "com.VCB:id/pin");

		log.info("TC_03_Step_15: An tiep button 'Tiep tuc'");
		waterBill.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_03_Step_16: Hien thi man hinh giao dich thanh cong");
		verifyEquals(waterBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), TITTLE.TRANSACTION_SUCCESS);

		log.info("TC_03_Step_17: Xac nhan hien thi dung so tien thanh toan");
		verifyEquals(waterBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount"), moneyBill);

		log.info("TC_03_Step_18: Xac nhan hien thi thoi gian giao dich");
		transactionDate = waterBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTime");
		verifyTrue(waterBill.isTextDisplayedInListTextElements(driver, getForWardDay(0), "com.VCB:id/tvTime"));

		log.info("TC_03_Step_19: Hien thi dung ten dich vu");
		verifyEquals(waterBill.getDynamicTextByLabel(driver, TITTLE.SERVICE), Water_Bills_Data.DATA.WATER_BILL_TEXT);

		log.info("TC_03_Step_20: Hien thi dung Nha cung cap");
		verifyEquals(waterBill.getDynamicTextByLabel(driver, TITTLE.SUPPLIER), Water_Bills_Data.DATA.WATER_DANANG);

		log.info("TC_03_Step_21: Hien thi dung ma khach hang");
		verifyEquals(waterBill.getDynamicTextByLabel(driver, TITTLE.CODE_CUSTOMER), customerID);

		log.info("TC_03_Step_22: Hien thi ma giao dich");
		transactionID = waterBill.getDynamicTextByLabel(driver, TITTLE.CODE_TRANSACTION);

		log.info("TC_03_Step_23: An tiep button 'Thuc hien giao dich moi'");
		verifyEquals(waterBill.getDynamicTextButtonById(driver, "com.VCB:id/btContinue"), TITTLE.PERFORM_NEW_TRANSFER);
		waterBill.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_03_Step_24: Hien thi man hinh Hoa don tien dien");
		verifyEquals(waterBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), Water_Bills_Data.DATA.WATER_BILL_TEXT);

		log.info("TC_03_Step_25: Chon tai khoan nguon");
		waterBill.clickToTextID(driver, "com.VCB:id/number_account");
		waterBill.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_03_Step_26: Xac nhan so du TK nguon da bi tru thanh cong");
		sourceAccountMoney = (convertAvailableBalanceCurrentcyOrFeeToLong(sourceAccountMoney) - convertAvailableBalanceCurrentcyOrFeeToLong(moneyBill) - transferFee) + "";
		verifyEquals(waterBill.getDynamicTextByLabel(driver, TITTLE.AVAILIBLE_BALANCES), addCommasToLong(sourceAccountMoney) + " VND");

		log.info("TC_03_Step_27: Chon nha cung cap");
		waterBill.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/wrap_tv");
		waterBill.clickToDynamicButtonLinkOrLinkText(driver, Water_Bills_Data.DATA.WATER_DANANG);

		log.info("TC_03_Step_28: Nhap ma khach hang");
		waterBill.inputToDynamicEditviewByLinearlayoutId(driver, customerID, "com.VCB:id/llCode");

		log.info("TC_03_Step_29: An nut Tiep Tuc");
		verifyEquals(waterBill.getDynamicTextButtonById(driver, "com.VCB:id/btn_submit"), TITTLE.CONTINUE_BUTTON);
		waterBill.clickToDynamicAcceptButton(driver, "com.VCB:id/btn_submit");

		log.info("TC_03_Step_30: Hien thi thong bao Ma khach hang khong con no truoc");
		verifyEquals(waterBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), Water_Bills_Data.DATA.BILL_MESSAGE);

		log.info("TC_03_Step_31: Click nut Dong tat pop-up");
		waterBill.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

		log.info("TC_03_Step_32: Click nut Back ve man hinh chinh");
		waterBill.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_03_Step_33: Click nut Back ve man hinh chinh");
		waterBill.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
	}

	@Test
	public void TC_04_ThanhToanTienNuoc_MK_BaoCaoGiaoDich() {

		log.info("TC_04_Step_01: Mo tab Menu");
		home.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_04_Step_02: Mo sub-menu 'Bao cao giao dich");
		home.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSACTION_REPORT);
		transactionReport = PageFactoryManager.getTransactionReportPageObject(driver);

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
		verifyEquals(transactionReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), ReportTitle.DETAIL_TRANSFER);

		log.info("TC_04_Step_10: Xac nhan hien thi thoi gian giao dich");
		verifyTrue(transactionReport.isTextDisplayedInListTextElements(driver, convertDateTimeIgnoreHHmmss(transactionDate), "com.VCB:id/tvContent"));

		log.info("TC_04_Step_11: Xac nhan hien thi dung ma giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.TRANSACTION_NUMBER), transactionID);

		log.info("TC_04_Step_12: Xac nhan hien thi so tai khoan giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.ACCOUNT_CARD_SOURCE), sourceAccount.account);

		log.info("TC_04_Step_13: Hien thi dung ten dich vu");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.SERVICE), Water_Bills_Data.DATA.WATER_BILL_TEXT);

		log.info("TC_04_Step_14: Hien thi dung Nha cung cap");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.SUPPLIER), Water_Bills_Data.DATA.WATER_DANANG);

		log.info("TC_04_Step_15: Hien thi dung Ma khach hang");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.CUSTOMER_CODE_TEXT), customerID);

		log.info("TC_04_Step_16: Xac nhan hien thi so tien giao dich");
		verifyTrue(transactionReport.isTextDisplayedInListTextElements(driver, moneyBill, "com.VCB:id/tvContent"));

		log.info("TC_04_Step_17: Xac nhan hien thi so tien phi");
		verifyTrue(transactionReport.isTextDisplayedInListTextElements(driver, transferFee + "", "com.VCB:id/tvContent"));

		log.info("TC_04_Step_18: Hien thi dung Loai giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.TRANSACTION_TYPE), ReportTitle.PAYMENT_BILLING);

		log.info("TC_04_Step_19: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_04_Step_20: An nut back ve man hinh menu");
		transactionReport.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_04_Step_21: Mo tab Home");
		home.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		service.stop();
	}

}
