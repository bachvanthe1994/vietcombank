package vnpay.vietcombank.postpaid_mobile_bills;

import java.io.IOException;
import java.security.GeneralSecurityException;

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
import pageObjects.PostpaidMobileBillPageObject;
import pageObjects.SettingVCBSmartOTPPageObject;
import pageObjects.TransactionReportPageObject;
import vietcombank_test_data.Postpaid_Mobile_Bill_Data;
import vietcombank_test_data.Postpaid_Mobile_Bill_Data.Title;
import vietcombank_test_data.TransactionReport_Data.ReportTitle;

public class PostPaid_Mobile_Bill_SmartOTP extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject home;
	private PostpaidMobileBillPageObject postpaidMobile;
	private SettingVCBSmartOTPPageObject smartOTP;
	private TransactionReportPageObject transactionReport;
	private String account;
	SourceAccountModel sourceAccount = new SourceAccountModel();
	String passSmartOTP = "111222";

	private String mobileBill, transactionID, sourceAccountMoney, transactionDate, mobilePhone;
	private long transferFee;

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException, GeneralSecurityException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);

		home = PageFactoryManager.getHomePageObject(driver);
		postpaidMobile = PageFactoryManager.getPostpaidMobileBillPageObject(driver);
		transactionReport = PageFactoryManager.getTransactionReportPageObject(driver);
		smartOTP = PageFactoryManager.getSettingVCBSmartOTPPageObject(driver);
		smartOTP.setupSmartOTP(passSmartOTP, getDataInCell(6));
	}

	@Test
	public void TC_01_CuocDiDongTraSau_Viettel_SmartOTP() {
		log.info("TC_01_Step_01: Click Cuoc di dong tra sau");
		home.scrollDownToText(driver, Title.POSTAGE_VNPT);
		home.clickToDynamicButtonLinkOrLinkText(driver, Title.POSTPAID_MOBILE_TITLE);

		log.info("TC_01_Step_02: Hien thi man hinh Cuoc di dong tra sau");
		verifyEquals(postpaidMobile.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), Title.MOBILE_TITLE);

		log.info("TC_01_Step_03: Chon tai khoan nguon");
		postpaidMobile.clickToTextViewDate(driver, Title.SOURCE_ACCOUNT, "1");
		sourceAccount = postpaidMobile.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, "VND");
		account = sourceAccount.account;
		sourceAccountMoney = postpaidMobile.getDynamicTextByLabel(driver, Title.AVAILABLE_BALANCES);

		log.info("TC_01_Step_04: Chon nha cung cao Viettel");
		postpaidMobile.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/wrap_tv");
		postpaidMobile.clickToDynamicButtonLinkOrLinkText(driver, Postpaid_Mobile_Bill_Data.DATA.VIETTEL_SUPPLIER);

		log.info("TC_01_Step_05: Nhap so dien thoai va an tiep tuc");
		mobilePhone = postpaidMobile.inputPhoneNumberPostPaidMobile(Postpaid_Mobile_Bill_Data.DATA.LIST_VIETTEL_MOBILE);

		log.info("TC_01_Step_06: Hien thi man hinh xac nhan thong tin");
		verifyEquals(postpaidMobile.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), Title.CONFIRM_INFOMATION);

		log.info("TC_01_Step_07: Hien thi thông tin xac nhan");
		verifyEquals(postpaidMobile.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleHead"), Postpaid_Mobile_Bill_Data.Title.VERIFY_INFO_TITLE_HEAD);

		log.info("TC_01_Step_08: Hien thi tai khoan nguon");
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, Title.SOURCE_ACCOUNT), account);

		log.info("TC_01_Step_09: Hien thi ten dich vu");
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, Title.SERVICE), Postpaid_Mobile_Bill_Data.Title.MOBILE_TITLE);

		log.info("TC_01_Step_10: Hien thi Nha cung cap");
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, Title.SUPPLIER), Postpaid_Mobile_Bill_Data.DATA.VIETTEL_SUPPLIER);

		log.info("TC_01_Step_11: Hien thi So dien thoai");
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, Title.PHONE_NUMBER), mobilePhone);

		log.info("TC_01_Step_12: Hien thi So tien thanh toan");
		mobileBill = postpaidMobile.getDynamicTextByLabel(driver, Title.MONEY_FEE);

		log.info("TC_01_Step_13: Chon phuong thuc xac thuc");
		postpaidMobile.scrollDownToText(driver, Title.AUTHENTICATION_METHOD);
		postpaidMobile.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/llptxt");
		postpaidMobile.clickToDynamicButtonLinkOrLinkText(driver, Title.SMART_OTP);

		log.info("TC_01_Step_14: Kiem tra so tien phi");
		transferFee = convertAvailableBalanceCurrentcyOrFeeToLong(postpaidMobile.getDynamicTextInTransactionDetail(driver, Title.FEE));

		log.info("TC_01_Step_15: An nut Tiep Tuc");
		verifyEquals(postpaidMobile.getDynamicTextButtonById(driver, "com.VCB:id/btContinue"), Title.NEXT);
		postpaidMobile.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_16: Nhap du ki tu vao o nhap OTP");
		postpaidMobile.inputToDynamicSmartOTP(driver, passSmartOTP, "com.VCB:id/otp");

		log.info("TC_01_Step_17: An tiep button 'Tiep tuc'");
		postpaidMobile.clickToDynamicAcceptButton(driver, "com.VCB:id/submit");

		log.info("TC_01_Step_17: An tiep button 'Tiep tuc'");
		postpaidMobile.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_18: Hien thi man hinh giao dich thanh cong");
		verifyEquals(postpaidMobile.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), Title.TITLE_SUCCESS);

		log.info("TC_01_Step_19: Xac nhan hien thi dung so tien thanh toan");
		verifyEquals(postpaidMobile.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount"), mobileBill);

		log.info("TC_01_Step_20: Xac nhan hien thi thoi gian giao dich");
		transactionDate = postpaidMobile.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTime");
		verifyTrue(postpaidMobile.isTextDisplayedInListTextElements(driver, getForWardDay(0), "com.VCB:id/tvTime"));

		log.info("TC_01_Step_21: Hien thi dung ten dich vu");
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, Title.SERVICE), Postpaid_Mobile_Bill_Data.Title.MOBILE_TITLE);

		log.info("TC_01_Step_22: Hien thi dung Nha cung cap");
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, Title.SUPPLIER), Postpaid_Mobile_Bill_Data.DATA.VIETTEL_SUPPLIER);

		log.info("TC_01_Step_23: Hien thi dung So dien thoai");
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, Title.PHONE_NUMBER), mobilePhone);

		log.info("TC_01_Step_24: Hien thi ma giao dich");
		transactionID = postpaidMobile.getDynamicTextByLabel(driver, Title.CODE_TRANSFER);

		log.info("TC_01_Step_26: Hien thi Icon Chia se");
		verifyTrue(postpaidMobile.isDynamicTextDetailByID(driver, "com.VCB:id/tvShare"));

		log.info("TC_01_Step_27: Hien thi Icon Luu anh");
		verifyTrue(postpaidMobile.isDynamicTextDetailByID(driver, "com.VCB:id/tvSavePhoto"));

		log.info("TC_01_Step_29: An tiep button 'Thuc hien giao dich moi'");
		verifyEquals(postpaidMobile.getDynamicTextButtonById(driver, "com.VCB:id/btContinue"), Title.NEW_TRANSFER);
		postpaidMobile.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_30: Hien thi man hinh Cuoc di dong tra sau");
		verifyEquals(postpaidMobile.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), Postpaid_Mobile_Bill_Data.Title.MOBILE_TITLE);

		log.info("TC_01_Step_31: Chon tai khoan nguon");
		postpaidMobile.clickToDynamicDropdownByHeader(driver, Title.SOURCE_ACCOUNT, "1");
		postpaidMobile.clickToDynamicButtonLinkOrLinkText(driver, account);

		log.info("TC_01_Step_31: Xac nhan so du TK nguon da bi tru thanh cong");
		sourceAccountMoney = (convertAvailableBalanceCurrentcyOrFeeToLong(sourceAccountMoney) - convertAvailableBalanceCurrentcyOrFeeToLong(mobileBill) - transferFee) + "";
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, Title.AVAILABLE_BALANCES), addCommasToLong(sourceAccountMoney) + " VND");

		log.info("TC_01_Step_32: Chon nha cung cap Viettel");
		postpaidMobile.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/wrap_tv");
		postpaidMobile.clickToDynamicButtonLinkOrLinkText(driver, Postpaid_Mobile_Bill_Data.DATA.VIETTEL_SUPPLIER);

		log.info("TC_01_Step_33: Nhap so dien thoai");
		postpaidMobile.inputToDynamicEditviewByLinearlayoutId(driver, mobilePhone, "com.VCB:id/llCode");

		log.info("TC_01_Step_34: An nut Tiep Tuc");
		verifyEquals(postpaidMobile.getDynamicTextButtonById(driver, "com.VCB:id/btn_submit"), Title.NEXT);
		postpaidMobile.clickToDynamicAcceptButton(driver, "com.VCB:id/btn_submit");

		log.info("TC_01_Step_35: Hien thi thong bao So dien thoai khong con no truoc");
		verifyEquals(postpaidMobile.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), Postpaid_Mobile_Bill_Data.Title.POSTPAID_MOBILE_MESSAGE);

		log.info("TC_01_Step_36: Click nut Dong tat pop-up");
		postpaidMobile.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

		log.info("TC_01_Step_37: Click nut Back ve man hinh chinh");
		postpaidMobile.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_01_Step_38: Click nut Back ve man hinh chinh");
		postpaidMobile.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_01_Step_38: Click nut Back ve man hinh chinh");
		postpaidMobile.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

	}

	@Test
	public void TC_02_CuocDiDongTraSau_Viettel_SmartOTP_BaoCaoGiaoDich() {

		log.info("TC_02_Step_01: Mo tab Menu");
		home.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_02_Step_02: Mo sub-menu 'Bao cao giao dich");
		home.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_02_Step_03: An vao Dropdown 'Tat ca cac loai giao dich");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectTransType");

		log.info("TC_02_Step_04: Chon 'Thanh toan hoa don'");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.PAYMENT_BILLING);

		log.info("TC_02_Step_05: An vao Dropdown 'Chon tai khoan/the");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_02_Step_06: Chon tai khoan vua thuc hien giao dich");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, account);

		log.info("TC_02_Step_07: An nut Tim kiem");
		transactionReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");

		log.info("TC_02_Step_08: An vao giao dich dau tien");
		transactionReport.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_02_Step_09: Xac nhan hien thi Title 'Chi tiet giao dich'");
		verifyEquals(transactionReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), Title.DETAIL_TRANSFER);

		log.info("TC_02_Step_10: Xac nhan hien thi thoi gian giao dich");
		verifyTrue(transactionReport.isTextDisplayedInListTextElements(driver, convertDateTimeIgnoreHHmmss(transactionDate), "com.VCB:id/tvContent"));

		log.info("TC_02_Step_11: Xac nhan hien thi dung ma giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.TRANSACTION_NUMBER), transactionID);

		log.info("TC_02_Step_12: Xac nhan hien thi so tai khoan giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.ACCOUNT_TRANSFER), account);

		log.info("TC_02_Step_13: Hien thi dung ten dich vu");
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, ReportTitle.SERVICE_TEXT), Postpaid_Mobile_Bill_Data.Title.MOBILE_TITLE);

		log.info("TC_02_Step_14: Hien thi dung Nha cung cap");
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, ReportTitle.SUPPLIER_NAME_TEXT), Postpaid_Mobile_Bill_Data.DATA.VIETTEL_SUPPLIER);

		log.info("TC_02_Step_15: Hien thi dung So dien thoai");
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, ReportTitle.TITLE_PHONE_NUMBER), mobilePhone);

		log.info("TC_02_Step_16: Xac nhan hien thi so tien giao dich");
		verifyTrue(transactionReport.isTextDisplayedInListTextElements(driver, mobileBill, "com.VCB:id/tvContent"));

		log.info("TC_02_Step_17: Xac nhan hien thi so tien phi");
		verifyTrue(transactionReport.isTextDisplayedInListTextElements(driver, transferFee + "", "com.VCB:id/tvContent"));

		log.info("TC_02_Step_18: Hien thi dung Loai giao dich");
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, ReportTitle.TRANSACTION_TYPE), ReportTitle.PAYMENT_BILLING);

		log.info("TC_02_Step_19: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_02_Step_20: An nut back ve man hinh menu");
		transactionReport.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_02_Step_21: Mo tab Home");
		home.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@Test
	public void TC_03_CuocDiDongTraSau_Vinaphone_SmartOTP() {
		log.info("TC_03_Step_01: Click Cuoc di dong tra sau");
		home.scrollDownToText(driver, Title.POSTAGE_VNPT);
		home.clickToDynamicButtonLinkOrLinkText(driver, Title.POSTPAID_MOBILE_TITLE);
		postpaidMobile = PageFactoryManager.getPostpaidMobileBillPageObject(driver);

		log.info("TC_03_Step_02: Hien thi man hinh Cuoc di dong tra sau");
		verifyEquals(postpaidMobile.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), Postpaid_Mobile_Bill_Data.Title.MOBILE_TITLE);

		log.info("TC_03_Step_03: Chon tai khoan nguon");
		postpaidMobile.clickToDynamicDropdownByHeader(driver, Title.SOURCE_ACCOUNT, "1");
		sourceAccount = postpaidMobile.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, "VND");
		account = sourceAccount.account;
		sourceAccountMoney = postpaidMobile.getDynamicTextByLabel(driver, Title.AVAILABLE_BALANCES);

		log.info("TC_03_Step_04: Chon nha cung cap Vinaphone");
		postpaidMobile.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/wrap_tv");
		postpaidMobile.clickToDynamicButtonLinkOrLinkText(driver, Postpaid_Mobile_Bill_Data.DATA.VINAPHONE_SUPPLIER);

		log.info("TC_03_Step_05: Nhap so dien thoai va an tiep tuc");
		mobilePhone = postpaidMobile.inputPhoneNumberPostPaidMobile(Postpaid_Mobile_Bill_Data.DATA.LIST_VINAPHONE_MOBILE);

		log.info("TC_03_Step_06: Hien thi man hinh xac nhan thong tin");
		verifyEquals(postpaidMobile.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), Title.CONFIRM_INFOMATION);

		log.info("TC_03_Step_07: Hien thi thông tin xac nhan");
		verifyEquals(postpaidMobile.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleHead"), Postpaid_Mobile_Bill_Data.Title.VERIFY_INFO_TITLE_HEAD);

		log.info("TC_03_Step_08: Hien thi tai khoan nguon");
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, Title.SOURCE_ACCOUNT), account);

		log.info("TC_03_Step_09: Hien thi ten dich vu");
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, Title.SERVICE), Postpaid_Mobile_Bill_Data.Title.MOBILE_TITLE);

		log.info("TC_03_Step_10: Hien thi Nha cung cap");
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, Title.SUPPLIER), Postpaid_Mobile_Bill_Data.DATA.VINAPHONE_SUPPLIER);

		log.info("TC_03_Step_11: Hien thi So dien thoai");
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, Title.PHONE_NUMBER), mobilePhone);

		log.info("TC_03_Step_12: Hien thi So tien thanh toan");
		mobileBill = postpaidMobile.getDynamicTextByLabel(driver, Title.MONEY_FEE);

		log.info("TC_03_Step_13: Chon phuong thuc xac thuc");
		postpaidMobile.scrollDownToText(driver, Title.AUTHENTICATION_METHOD);
		postpaidMobile.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/llptxt");
		postpaidMobile.clickToDynamicButtonLinkOrLinkText(driver, Title.SMART_OTP);

		log.info("TC_03_Step_14: Kiem tra so tien phi");
		transferFee = convertAvailableBalanceCurrentcyOrFeeToLong(postpaidMobile.getDynamicTextInTransactionDetail(driver, Title.FEE));

		log.info("TC_03_Step_15: An nut Tiep Tuc");
		verifyEquals(postpaidMobile.getDynamicTextButtonById(driver, "com.VCB:id/btContinue"), Title.NEXT);
		postpaidMobile.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_03_Step_16: Nhap du ki tu vao o nhap OTP");
		postpaidMobile.inputToDynamicSmartOTP(driver, passSmartOTP, "com.VCB:id/otp");

		log.info("TC_03_Step_17: An tiep button 'Tiep tuc'");
		postpaidMobile.clickToDynamicAcceptButton(driver, "com.VCB:id/submit");

		log.info("TC_01_Step_17: An tiep button 'Tiep tuc'");
		postpaidMobile.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_03_Step_18: Hien thi man hinh giao dich thanh cong");
		verifyEquals(postpaidMobile.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), Title.TITLE_SUCCESS);

		log.info("TC_03_Step_19: Xac nhan hien thi dung so tien thanh toan");
		verifyEquals(postpaidMobile.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount"), mobileBill);

		log.info("TC_03_Step_20: Xac nhan hien thi thoi gian giao dich");
		transactionDate = postpaidMobile.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTime");
		verifyTrue(postpaidMobile.isTextDisplayedInListTextElements(driver, getForWardDay(0), "com.VCB:id/tvTime"));

		log.info("TC_03_Step_21: Hien thi dung ten dich vu");
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, Title.SERVICE), Postpaid_Mobile_Bill_Data.Title.MOBILE_TITLE);

		log.info("TC_03_Step_22: Hien thi dung Nha cung cap");
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, Title.SUPPLIER), Postpaid_Mobile_Bill_Data.DATA.VINAPHONE_SUPPLIER);

		log.info("TC_03_Step_23: Hien thi dung So dien thoai");
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, Title.PHONE_NUMBER), mobilePhone);

		log.info("TC_03_Step_24: Hien thi ma giao dich");
		transactionID = postpaidMobile.getDynamicTextByLabel(driver, Title.CODE_TRANSFER);

		log.info("TC_03_Step_26: Hien thi Icon Chia se");
		verifyTrue(postpaidMobile.isDynamicTextDetailByID(driver, "com.VCB:id/tvShare"));

		log.info("TC_03_Step_27: Hien thi Icon Luu anh");
		verifyTrue(postpaidMobile.isDynamicTextDetailByID(driver, "com.VCB:id/tvSavePhoto"));

		log.info("TC_03_Step_29: An tiep button 'Thuc hien giao dich moi'");
		verifyEquals(postpaidMobile.getDynamicTextButtonById(driver, "com.VCB:id/btContinue"), Title.NEW_TRANSFER);
		postpaidMobile.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_03_Step_30: Hien thi man hinh Cuoc di dong tra sau");
		verifyEquals(postpaidMobile.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), Postpaid_Mobile_Bill_Data.Title.MOBILE_TITLE);

		log.info("TC_03_Step_31: Chon tai khoan nguon");
		postpaidMobile.clickToDynamicDropdownByHeader(driver, Title.SOURCE_ACCOUNT, "1");
		postpaidMobile.clickToDynamicButtonLinkOrLinkText(driver, account);

		log.info("TC_03_Step_32: Xac nhan so du TK nguon da bi tru thanh cong");
		sourceAccountMoney = (convertAvailableBalanceCurrentcyOrFeeToLong(sourceAccountMoney) - convertAvailableBalanceCurrentcyOrFeeToLong(mobileBill) - transferFee) + "";
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, Title.AVAILABLE_BALANCES), addCommasToLong(sourceAccountMoney) + " VND");

		log.info("TC_03_Step_33: Chon nha cung cap Vinaphone");
		postpaidMobile.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/wrap_tv");
		postpaidMobile.clickToDynamicButtonLinkOrLinkText(driver, Postpaid_Mobile_Bill_Data.DATA.VINAPHONE_SUPPLIER);

		log.info("TC_03_Step_34: Nhap so dien thoai");
		postpaidMobile.inputToDynamicEditviewByLinearlayoutId(driver, mobilePhone, "com.VCB:id/llCode");

		log.info("TC_03_Step_35: An nut Tiep Tuc");
		verifyEquals(postpaidMobile.getDynamicTextButtonById(driver, "com.VCB:id/btn_submit"), Title.NEXT);
		postpaidMobile.clickToDynamicAcceptButton(driver, "com.VCB:id/btn_submit");

		log.info("TC_03_Step_36: Hien thi thong bao So dien thoai khong con no truoc");
		verifyEquals(postpaidMobile.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), Postpaid_Mobile_Bill_Data.Title.POSTPAID_MOBILE_MESSAGE);

		log.info("TC_03_Step_37: Click nut Dong tat pop-up");
		postpaidMobile.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

		log.info("TC_03_Step_38: Click nut Back ve man hinh chinh");
		postpaidMobile.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_03_Step_39: Click nut Back ve man hinh chinh");
		postpaidMobile.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_03_Step_40: Click nut Back ve man hinh chinh");
		postpaidMobile.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

	}

	@Test
	public void TC_04_CuocDiDongTraSau_Vinaphone_SmartOTP_BaoCaoGiaoDich() {

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
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, account);

		log.info("TC_04_Step_07: An nut Tim kiem");
		transactionReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");

		log.info("TC_04_Step_08: An vao giao dich dau tien");
		transactionReport.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_04_Step_09: Xac nhan hien thi Title 'Chi tiet giao dich'");
		verifyEquals(transactionReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), Title.DETAIL_TRANSFER);

		log.info("TC_04_Step_10: Xac nhan hien thi thoi gian giao dich");
		verifyTrue(transactionReport.isTextDisplayedInListTextElements(driver, convertDateTimeIgnoreHHmmss(transactionDate), "com.VCB:id/tvContent"));

		log.info("TC_04_Step_11: Xac nhan hien thi dung ma giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.TRANSACTION_NUMBER), transactionID);

		log.info("TC_04_Step_12: Xac nhan hien thi so tai khoan giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.ACCOUNT_TRANSFER), account);

		log.info("TC_04_Step_13: Hien thi dung ten dich vu");
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, ReportTitle.SERVICE_TEXT), Postpaid_Mobile_Bill_Data.Title.MOBILE_TITLE);

		log.info("TC_04_Step_14: Hien thi dung Nha cung cap");
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, ReportTitle.SUPPLIER_NAME_TEXT), Postpaid_Mobile_Bill_Data.DATA.VINAPHONE_SUPPLIER);

		log.info("TC_04_Step_15: Hien thi dung So dien thoai");
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, ReportTitle.TITLE_PHONE_NUMBER), mobilePhone);

		log.info("TC_04_Step_16: Xac nhan hien thi so tien giao dich");
		verifyTrue(transactionReport.isTextDisplayedInListTextElements(driver, mobileBill, "com.VCB:id/tvContent"));

		log.info("TC_04_Step_17: Xac nhan hien thi so tien phi");
		verifyTrue(transactionReport.isTextDisplayedInListTextElements(driver, transferFee + "", "com.VCB:id/tvContent"));

		log.info("TC_04_Step_18: Hien thi dung Loai giao dich");
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, ReportTitle.TRANSACTION_TYPE), ReportTitle.PAYMENT_BILLING);

		log.info("TC_04_Step_19: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_04_Step_20: An nut back ve man hinh menu");
		transactionReport.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_04_Step_21: Mo tab Home");
		home.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@Test
	public void TC_05_CuocDiDongTraSau_Mobifone_SmartOTP() {
		log.info("TC_05_Step_01: Click Cuoc di dong tra sau");
		home.scrollDownToText(driver, Title.POSTAGE_VNPT);
		home.clickToDynamicButtonLinkOrLinkText(driver, Title.POSTPAID_MOBILE_TITLE);
		postpaidMobile = PageFactoryManager.getPostpaidMobileBillPageObject(driver);

		log.info("TC_05_Step_02: Hien thi man hinh Cuoc di dong tra sau");
		verifyEquals(postpaidMobile.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), Postpaid_Mobile_Bill_Data.Title.MOBILE_TITLE);

		log.info("TC_05_Step_03: Chon tai khoan nguon");
		postpaidMobile.clickToDynamicDropdownByHeader(driver, Title.SOURCE_ACCOUNT, "1");
		sourceAccount = postpaidMobile.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, "VND");
		account = sourceAccount.account;
		sourceAccountMoney = postpaidMobile.getDynamicTextByLabel(driver, Title.AVAILABLE_BALANCES);

		log.info("TC_05_Step_04: Chon nha cung cap Mobifone");
		postpaidMobile.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/wrap_tv");
		postpaidMobile.clickToDynamicButtonLinkOrLinkText(driver, Postpaid_Mobile_Bill_Data.DATA.MOBIFONE_SUPPLIER);

		log.info("TC_03_Step_05: Nhap so dien thoai va an tiep tuc");
		mobilePhone = postpaidMobile.inputPhoneNumberPostPaidMobile(Postpaid_Mobile_Bill_Data.DATA.LIST_MOBIFONE_MOBILE);

		log.info("TC_05_Step_06: Hien thi man hinh xac nhan thong tin");
		verifyEquals(postpaidMobile.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), Title.CONFIRM_INFOMATION);

		log.info("TC_05_Step_07: Hien thi thông tin xac nhan");
		verifyEquals(postpaidMobile.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleHead"), Postpaid_Mobile_Bill_Data.Title.VERIFY_INFO_TITLE_HEAD);

		log.info("TC_05_Step_08: Hien thi tai khoan nguon");
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, Title.SOURCE_ACCOUNT), account);

		log.info("TC_05_Step_09: Hien thi ten dich vu");
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, Title.SERVICE), Postpaid_Mobile_Bill_Data.Title.MOBILE_TITLE);

		log.info("TC_05_Step_10: Hien thi Nha cung cap");
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, Title.SUPPLIER), Postpaid_Mobile_Bill_Data.DATA.MOBIFONE_SUPPLIER);

		log.info("TC_05_Step_11: Hien thi So dien thoai");
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, Title.PHONE_NUMBER), mobilePhone);

		log.info("TC_05_Step_12: Hien thi So tien thanh toan");
		mobileBill = postpaidMobile.getDynamicTextByLabel(driver, Title.MONEY_FEE);

		log.info("TC_05_Step_13: Chon phuong thuc xac thuc");
		postpaidMobile.scrollDownToText(driver, Title.AUTHENTICATION_METHOD);
		postpaidMobile.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/llptxt");
		postpaidMobile.clickToDynamicButtonLinkOrLinkText(driver, Title.SMART_OTP);

		log.info("TC_05_Step_14: Kiem tra so tien phi");
		transferFee = convertAvailableBalanceCurrentcyOrFeeToLong(postpaidMobile.getDynamicTextInTransactionDetail(driver, Title.FEE));

		log.info("TC_05_Step_15: An nut Tiep Tuc");
		verifyEquals(postpaidMobile.getDynamicTextButtonById(driver, "com.VCB:id/btContinue"), Title.NEXT);
		postpaidMobile.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_05_Step_16: Nhap du ki tu vao o nhap OTP");
		postpaidMobile.inputToDynamicSmartOTP(driver, passSmartOTP, "com.VCB:id/otp");

		log.info("TC_05_Step_17: An tiep button 'Tiep tuc'");
		postpaidMobile.clickToDynamicAcceptButton(driver, "com.VCB:id/submit");

		log.info("TC_01_Step_17: An tiep button 'Tiep tuc'");
		postpaidMobile.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_05_Step_18: Hien thi man hinh giao dich thanh cong");
		verifyEquals(postpaidMobile.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), Title.TITLE_SUCCESS);

		log.info("TC_05_Step_19: Xac nhan hien thi dung so tien thanh toan");
		verifyEquals(postpaidMobile.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount"), mobileBill);

		log.info("TC_05_Step_20: Xac nhan hien thi thoi gian giao dich");
		transactionDate = postpaidMobile.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTime");
		verifyTrue(postpaidMobile.isTextDisplayedInListTextElements(driver, getForWardDay(0), "com.VCB:id/tvTime"));

		log.info("TC_05_Step_21: Hien thi dung ten dich vu");
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, Title.SERVICE), Postpaid_Mobile_Bill_Data.Title.MOBILE_TITLE);

		log.info("TC_05_Step_22: Hien thi dung Nha cung cap");
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, Title.SUPPLIER), Postpaid_Mobile_Bill_Data.DATA.MOBIFONE_SUPPLIER);

		log.info("TC_05_Step_23: Hien thi dung So dien thoai");
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, Title.PHONE_NUMBER), mobilePhone);

		log.info("TC_05_Step_25: Hien thi ma giao dich");
		transactionID = postpaidMobile.getDynamicTextByLabel(driver, Title.CODE_TRANSFER);

		log.info("TC_05_Step_27: Hien thi Icon Chia se");
		verifyTrue(postpaidMobile.isDynamicTextDetailByID(driver, "com.VCB:id/tvShare"));

		log.info("TC_05_Step_28: Hien thi Icon Luu anh");
		verifyTrue(postpaidMobile.isDynamicTextDetailByID(driver, "com.VCB:id/tvSavePhoto"));

		log.info("TC_03_Step_29: An tiep button 'Thuc hien giao dich moi'");
		verifyEquals(postpaidMobile.getDynamicTextButtonById(driver, "com.VCB:id/btContinue"), Title.NEW_TRANSFER);
		postpaidMobile.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_05_Step_31: Hien thi man hinh Cuoc di dong tra sau");
		verifyEquals(postpaidMobile.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), Postpaid_Mobile_Bill_Data.Title.MOBILE_TITLE);

		log.info("TC_05_Step_32: Chon tai khoan nguon");
		postpaidMobile.clickToDynamicDropdownByHeader(driver, Title.SOURCE_ACCOUNT, "1");
		postpaidMobile.clickToDynamicButtonLinkOrLinkText(driver, account);

		log.info("TC_05_Step_33: Xac nhan so du TK nguon da bi tru thanh cong");
		sourceAccountMoney = (convertAvailableBalanceCurrentcyOrFeeToLong(sourceAccountMoney) - convertAvailableBalanceCurrentcyOrFeeToLong(mobileBill) - transferFee) + "";
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, Title.AVAILABLE_BALANCES), addCommasToLong(sourceAccountMoney) + " VND");

		log.info("TC_05_Step_34: Chon nha cung cap Viettel");
		postpaidMobile.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/wrap_tv");
		postpaidMobile.clickToDynamicButtonLinkOrLinkText(driver, Postpaid_Mobile_Bill_Data.DATA.MOBIFONE_SUPPLIER);

		log.info("TC_05_Step_35: Nhap so dien thoai");
		postpaidMobile.inputToDynamicEditviewByLinearlayoutId(driver, mobilePhone, "com.VCB:id/llCode");

		log.info("TC_05_Step_36: An nut Tiep Tuc");
		verifyEquals(postpaidMobile.getDynamicTextButtonById(driver, "com.VCB:id/btn_submit"), Title.NEXT);
		postpaidMobile.clickToDynamicAcceptButton(driver, "com.VCB:id/btn_submit");

		log.info("TC_05_Step_37: Hien thi thong bao So dien thoai khong con no truoc");
		verifyEquals(postpaidMobile.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), Postpaid_Mobile_Bill_Data.Title.POSTPAID_MOBILE_MESSAGE);

		log.info("TC_05_Step_38: Click nut Dong tat danh sach hoa don duoc luu");
		postpaidMobile.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

		log.info("TC_05_Step_39: Click nut Back ve man hinh chinh");
		postpaidMobile.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_05_Step_40: Click nut Back ve man hinh chinh");
		postpaidMobile.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_05_Step_41: Click nut Back ve man hinh chinh");
		postpaidMobile.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

	}

	@Test
	public void TC_06_CuocDiDongTraSau_Mobifone_SmartOTP_BaoCaoGiaoDich() {

		log.info("TC_06_Step_01: Mo tab Menu");
		home.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_06_Step_02: Mo sub-menu 'Bao cao giao dich");
		home.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSACTION_REPORT);
		transactionReport = PageFactoryManager.getTransactionReportPageObject(driver);

		log.info("TC_06_Step_03: An vao Dropdown 'Tat ca cac loai giao dich");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectTransType");

		log.info("TC_06_Step_04: Chon 'Thanh toan hoa don'");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.PAYMENT_BILLING);

		log.info("TC_06_Step_05: An vao Dropdown 'Chon tai khoan/the");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_06_Step_06: Chon tai khoan vua thuc hien giao dich");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, account);

		log.info("TC_06_Step_07: An nut Tim kiem");
		transactionReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");

		log.info("TC_06_Step_08: An vao giao dich dau tien");
		transactionReport.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_06_Step_09: Xac nhan hien thi Title 'Chi tiet giao dich'");
		verifyEquals(transactionReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), Title.DETAIL_TRANSFER);

		log.info("TC_06_Step_10: Xac nhan hien thi thoi gian giao dich");
		verifyTrue(transactionReport.isTextDisplayedInListTextElements(driver, convertDateTimeIgnoreHHmmss(transactionDate), "com.VCB:id/tvContent"));

		log.info("TC_06_Step_11: Xac nhan hien thi dung ma giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.TRANSACTION_NUMBER), transactionID);

		log.info("TC_06_Step_12: Xac nhan hien thi so tai khoan giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.ACCOUNT_TRANSFER), account);

		log.info("TC_06_Step_13: Hien thi dung ten dich vu");
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, ReportTitle.SERVICE_TEXT), Postpaid_Mobile_Bill_Data.Title.MOBILE_TITLE);

		log.info("TC_06_Step_14: Hien thi dung Nha cung cap");
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, ReportTitle.SUPPLIER_NAME_TEXT), Postpaid_Mobile_Bill_Data.DATA.MOBIFONE_SUPPLIER);

		log.info("TC_06_Step_15: Hien thi dung So dien thoai");
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, ReportTitle.TITLE_PHONE_NUMBER), mobilePhone);

		log.info("TC_06_Step_16: Xac nhan hien thi so tien giao dich");
		verifyTrue(transactionReport.isTextDisplayedInListTextElements(driver, mobileBill, "com.VCB:id/tvContent"));

		log.info("TC_06_Step_17: Xac nhan hien thi so tien phi");
		verifyTrue(transactionReport.isTextDisplayedInListTextElements(driver, transferFee + "", "com.VCB:id/tvContent"));

		log.info("TC_06_Step_18: Hien thi dung Loai giao dich");
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, ReportTitle.TRANSACTION_TYPE), ReportTitle.PAYMENT_BILLING);

		log.info("TC_06_Step_19: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_06_Step_20: An nut back ve man hinh menu");
		transactionReport.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_06_Step_21: Mo tab Home");
		home.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();
	}

}
