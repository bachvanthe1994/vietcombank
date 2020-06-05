package vnpay.vietcombank.postpaid_mobile_bills;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.PostpaidMobileBillPageObject;
import pageObjects.TransactionReportPageObject;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.Postpaid_Mobile_Bill_Data;

public class Limit_PostPaid_Mobile_Bill extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject home;
	private PostpaidMobileBillPageObject postpaidMobile;
	private TransactionReportPageObject transactionReport;

	private String mobileBill, transactionID, sourceAccountMoney, transactionDate, mobilePhone;
	private long transferFee;

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);

	}

	@Parameters({ "otp" })
	@Test
	public void TC_01_CuocDiDongTraSau_Viettel_OTP(String otp) {

		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_01_Step_01: Click Cuoc di dong tra sau");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Cước di động trả sau");
		postpaidMobile = PageFactoryManager.getPostpaidMobileBillPageObject(driver);

		log.info("TC_01_Step_02: Hien thi man hinh Cuoc di dong tra sau");
		verifyEquals(postpaidMobile.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), Postpaid_Mobile_Bill_Data.Title.POSTPAID_MOBILE_TITLE);

		log.info("TC_01_Step_03: Chon tai khoan nguon");
		postpaidMobile.clickToDynamicDropdownByHeader(driver, "Tài khoản nguồn", "1");
		postpaidMobile.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);
		sourceAccountMoney = postpaidMobile.getDynamicTextByLabel(driver, "Số dư khả dụng");

		log.info("TC_01_Step_04: Chon nha cung cao Viettel");
		postpaidMobile.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/wrap_tv");
		postpaidMobile.clickToDynamicButtonLinkOrLinkText(driver, Postpaid_Mobile_Bill_Data.DATA.VIETTEL_SUPPLIER);

		log.info("TC_01_Step_05: Nhap so dien thoai va an tiep tuc");
		mobilePhone = postpaidMobile.inputPhoneNumberPostPaidMobile(Postpaid_Mobile_Bill_Data.DATA.LIST_VIETTEL_MOBILE);

		log.info("TC_01_Step_06: Hien thi man hinh xac nhan thong tin");
		verifyEquals(postpaidMobile.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Xác nhận thông tin");

		log.info("TC_01_Step_07: Hien thi thông tin xac nhan");
		verifyEquals(postpaidMobile.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleHead"), Postpaid_Mobile_Bill_Data.Title.VERIFY_INFO_TITLE_HEAD);

		log.info("TC_01_Step_08: Hien thi tai khoan nguon");
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, "Tài khoản nguồn"), Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_01_Step_09: Hien thi ten dich vu");
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, "Dịch vụ"), Postpaid_Mobile_Bill_Data.Title.POSTPAID_MOBILE_TITLE);

		log.info("TC_01_Step_10: Hien thi Nha cung cap");
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, "Nhà cung cấp"), Postpaid_Mobile_Bill_Data.DATA.VIETTEL_SUPPLIER);

		log.info("TC_01_Step_11: Hien thi So dien thoai");
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, "Số điện thoại"), mobilePhone);

		log.info("TC_01_Step_12: Hien thi So tien thanh toan");
		mobileBill = postpaidMobile.getDynamicTextByLabel(driver, "Số tiền thanh toán");

		log.info("TC_01_Step_13: Chon phuong thuc xac thuc");
		postpaidMobile.scrollDownToText(driver, "Chọn phương thức xác thực");
		postpaidMobile.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/llptxt");
		transferFee = convertAvailableBalanceCurrentcyOrFeeToLong(postpaidMobile.getDynamicTextInTransactionDetail(driver, "SMS OTP"));
		postpaidMobile.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_01_Step_14: Kiem tra so tien phi");
		verifyEquals(postpaidMobile.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), addCommasToLong(transferFee + "") + " VND");

		log.info("TC_01_Step_15: An nut Tiep Tuc");
		verifyEquals(postpaidMobile.getDynamicTextButtonById(driver, "com.VCB:id/btContinue"), "Tiếp tục");
		postpaidMobile.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_16: Nhap du ki tu vao o nhap OTP");
		postpaidMobile.inputToDynamicOtp(driver, otp, "Tiếp tục");

		log.info("TC_01_Step_17: An tiep button 'Tiep tuc'");
		postpaidMobile.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_18: Hien thi man hinh giao dich thanh cong");
		verifyEquals(postpaidMobile.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), "GIAO DỊCH THÀNH CÔNG");

		log.info("TC_01_Step_19: Xac nhan hien thi dung so tien thanh toan");
		verifyEquals(postpaidMobile.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount"), mobileBill);

		log.info("TC_01_Step_20: Xac nhan hien thi thoi gian giao dich");
		transactionDate = postpaidMobile.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTime");
		verifyTrue(postpaidMobile.isTextDisplayedInListTextElements(driver, getForWardDay(0), "com.VCB:id/tvTime"));

		log.info("TC_01_Step_21: Hien thi dung ten dich vu");
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, "Dịch vụ"), Postpaid_Mobile_Bill_Data.Title.POSTPAID_MOBILE_TITLE);

		log.info("TC_01_Step_22: Hien thi dung Nha cung cap");
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, "Nhà cung cấp"), Postpaid_Mobile_Bill_Data.DATA.VIETTEL_SUPPLIER);

		log.info("TC_01_Step_23: Hien thi dung So dien thoai");
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, "Số điện thoại"), mobilePhone);

		log.info("TC_01_Step_24: Hien thi ma giao dich");
		transactionID = postpaidMobile.getDynamicTextByLabel(driver, "Mã giao dịch");

		log.info("TC_01_Step_26: Hien thi Icon Chia se");
		verifyTrue(postpaidMobile.isDynamicTextDetailByID(driver, "com.VCB:id/tvShare"));

		log.info("TC_01_Step_27: Hien thi Icon Luu anh");
		verifyTrue(postpaidMobile.isDynamicTextDetailByID(driver, "com.VCB:id/tvSavePhoto"));

		log.info("TC_01_Step_29: An tiep button 'Thuc hien giao dich moi'");
		verifyEquals(postpaidMobile.getDynamicTextButtonById(driver, "com.VCB:id/btContinue"), "Thực hiện giao dịch mới");
		postpaidMobile.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_30: Hien thi man hinh Cuoc di dong tra sau");
		verifyEquals(postpaidMobile.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), Postpaid_Mobile_Bill_Data.Title.POSTPAID_MOBILE_TITLE);

		log.info("TC_01_Step_31: Chon tai khoan nguon");
		postpaidMobile.clickToDynamicDropdownByHeader(driver, "Tài khoản nguồn", "1");
		postpaidMobile.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_01_Step_31: Xac nhan so du TK nguon da bi tru thanh cong");
		sourceAccountMoney = (convertAvailableBalanceCurrentcyOrFeeToLong(sourceAccountMoney) - convertAvailableBalanceCurrentcyOrFeeToLong(mobileBill) - transferFee) + "";
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, "Số dư khả dụng"), addCommasToLong(sourceAccountMoney) + " VND");

		log.info("TC_01_Step_32: Chon nha cung cap Viettel");
		postpaidMobile.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/wrap_tv");
		postpaidMobile.clickToDynamicButtonLinkOrLinkText(driver, Postpaid_Mobile_Bill_Data.DATA.VIETTEL_SUPPLIER);

		log.info("TC_01_Step_33: Nhap so dien thoai");
		postpaidMobile.inputToDynamicEditviewByLinearlayoutId(driver, mobilePhone, "com.VCB:id/llCode");

		log.info("TC_01_Step_34: An nut Tiep Tuc");
		verifyEquals(postpaidMobile.getDynamicTextButtonById(driver, "com.VCB:id/btn_submit"), "Tiếp tục");
		postpaidMobile.clickToDynamicAcceptButton(driver, "com.VCB:id/btn_submit");

		log.info("TC_01_Step_35: Hien thi thong bao So dien thoai khong con no truoc");
		verifyEquals(postpaidMobile.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), Postpaid_Mobile_Bill_Data.Title.POSTPAID_MOBILE_MESSAGE);

		log.info("TC_01_Step_36: Click nut Dong tat pop-up");
		postpaidMobile.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

		log.info("TC_01_Step_37: Click nut Back ve man hinh chinh");
		postpaidMobile.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_01_Step_38: Click nut Back ve man hinh chinh");
		postpaidMobile.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

	}

	@Test
	public void TC_02_CuocDiDongTraSau_Viettel_BaoCaoGiaoDich() {

		log.info("TC_02_Step_01: Mo tab Menu");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_5");

		log.info("TC_02_Step_02: Mo sub-menu 'Bao cao giao dich");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");
		transactionReport = PageFactoryManager.getTransactionReportPageObject(driver);

		log.info("TC_02_Step_03: An vao Dropdown 'Tat ca cac loai giao dich");
//		transactionReport.clickToTextViewCombobox(driver, "com.VCB:id/tvSelectTransType");

		log.info("TC_02_Step_04: Chon 'Thanh toan hoa don'");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, "Thanh toán hóa đơn");

		log.info("TC_02_Step_05: An vao Dropdown 'Chon tai khoan/the");
//		transactionReport.clickToTextViewCombobox(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_02_Step_06: Chon tai khoan vua thuc hien giao dich");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_02_Step_07: An nut Tim kiem");
		transactionReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");

		log.info("TC_02_Step_08: An vao giao dich dau tien");
		transactionReport.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_02_Step_09: Xac nhan hien thi Title 'Chi tiet giao dich'");
		verifyEquals(transactionReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Chi tiết giao dịch");

		log.info("TC_02_Step_10: Xac nhan hien thi thoi gian giao dich");
		verifyTrue(transactionReport.isTextDisplayedInListTextElements(driver, convertDateTimeIgnoreHHmmss(transactionDate), "com.VCB:id/tvContent"));

		log.info("TC_02_Step_11: Xac nhan hien thi dung ma giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Số lệnh giao dịch"), transactionID);

		log.info("TC_02_Step_12: Xac nhan hien thi so tai khoan giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Tài khoản/thẻ trích nợ"), Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_02_Step_13: Hien thi dung ten dich vu");
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, "Dịch vụ"), Postpaid_Mobile_Bill_Data.Title.POSTPAID_MOBILE_TITLE);

		log.info("TC_02_Step_14: Hien thi dung Nha cung cap");
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, "Nhà cung cấp"), Postpaid_Mobile_Bill_Data.DATA.VIETTEL_SUPPLIER);

		log.info("TC_02_Step_15: Hien thi dung So dien thoai");
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, "Số điện thoại"), mobilePhone);

		log.info("TC_02_Step_16: Xac nhan hien thi so tien giao dich");
		verifyTrue(transactionReport.isTextDisplayedInListTextElements(driver, mobileBill, "com.VCB:id/tvContent"));

		log.info("TC_02_Step_17: Xac nhan hien thi so tien phi");
		verifyTrue(transactionReport.isTextDisplayedInListTextElements(driver, transferFee + "", "com.VCB:id/tvContent"));

		log.info("TC_02_Step_18: Hien thi dung Loai giao dich");
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, "Loại giao dịch"), "Thanh toán hóa đơn");

		log.info("TC_02_Step_19: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_02_Step_20: An nut back ve man hinh menu");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_02_Step_21: Mo tab Home");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_1");
	}

	
	@AfterClass(alwaysRun = true)
	public void afterClass() {
		service.stop();
	}

}
