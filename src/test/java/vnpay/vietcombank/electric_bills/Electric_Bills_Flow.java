package vnpay.vietcombank.electric_bills;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import pageObjects.ElectricBillPageObject;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.TransactionReportPageObject;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.Electric_Bills_Data;

public class Electric_Bills_Flow extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject home;
	private ElectricBillPageObject electricBill;
	private TransactionReportPageObject transactionReport;

	private String sourceAccountMoney, customerID, electricBills, transactionDate, transactionID;
	private long transferFee;

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login("0918679292", "aaaa1111", opt);

	}

	@Parameters("otp")
	@Test
	public void TC_01_ThanhToanTienDien_OTP(String otp) {

		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_01_Step_01: Hoa don tien dien");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Hoá đơn tiền điện");
		electricBill = PageFactoryManager.getElectricBillPageObject(driver);

		log.info("TC_01_Step_02: Chon tai khoan nguon");
		electricBill.clickToTextID(driver, "com.VCB:id/number_account");
		electricBill.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);
		sourceAccountMoney = electricBill.getDynamicTextByLabel(driver, "Số dư khả dụng");

		log.info("TC_01_Step_03: Chon Nha cung cap EVN Mien Trung");
		electricBill.clickToTextID(driver, "com.VCB:id/wrap_tv");
		electricBill.clickToDynamicButtonLinkOrLinkText(driver, Electric_Bills_Data.DATA.EVN_MIEN_TRUNG);

		log.info("TC_01_Step_04: Nhap ma khach hang va an tiep tuc");
		customerID = electricBill.inputCustomerId(Electric_Bills_Data.DATA.LIST_CUSTOMER_ID);

		log.info("TC_01_Step_05: Hien thi man hinh xac nhan thong tin");
		verifyEquals(electricBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Xác nhận thông tin");

		log.info("TC_01_Step_06: Hien thi tai khoan nguon");
		verifyEquals(electricBill.getDynamicTextByLabel(driver, "Tài khoản nguồn"), Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_01_Step_07: Hien thi ten dich vu");
		verifyEquals(electricBill.getDynamicTextByLabel(driver, "Dịch vụ"), Electric_Bills_Data.VALIDATE.ELECTIC_BILL_TITLE);

		log.info("TC_01_Step_08: Hien thi Nha cung cap");
		verifyEquals(electricBill.getDynamicTextByLabel(driver, "Nhà cung cấp"), Electric_Bills_Data.DATA.EVN_MIEN_TRUNG);

		log.info("TC_01_Step_09: Hien thi ma khach hang");
		verifyEquals(electricBill.getDynamicTextByLabel(driver, "Mã khách hàng"), customerID);

		log.info("TC_01_Step_10: Hien thi So tien thanh toan");
		electricBills = electricBill.getDynamicTextByLabel(driver, "Số tiền thanh toán");

		log.info("TC_01_Step_11: Chon phuong thuc xac thuc");
		electricBill.scrollDownToText(driver, "Chọn phương thức xác thực");
		electricBill.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/llptxt");
		transferFee = convertAvailableBalanceCurrentcyOrFeeToLong(electricBill.getDynamicTextInTransactionDetail(driver, "SMS OTP"));
		electricBill.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_01_Step_12: Kiem tra so tien phi");
		verifyEquals(electricBill.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), addCommasToLong(transferFee + "") + " VND");

		log.info("TC_01_Step_13: An nut Tiep Tuc");
		verifyEquals(electricBill.getDynamicTextButtonById(driver, "com.VCB:id/btContinue"), "Tiếp tục");
		electricBill.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_14: Nhap du ki tu vao o nhap OTP");
		electricBill.inputToDynamicOtp(driver, otp, "Tiếp tục");

		log.info("TC_01_Step_15: An tiep button 'Tiep tuc'");
		electricBill.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_16: Hien thi man hinh giao dich thanh cong");
		verifyEquals(electricBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), "GIAO DỊCH THÀNH CÔNG");

		log.info("TC_01_Step_17: Xac nhan hien thi dung so tien thanh toan");
		verifyEquals(electricBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount"), electricBills);

		log.info("TC_01_Step_18: Xac nhan hien thi thoi gian giao dich");
		transactionDate = electricBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTime");
		verifyTrue(electricBill.isTextDisplayedInListTextElements(driver, getForWardDay(0), "com.VCB:id/tvTime"));

		log.info("TC_01_Step_19: Hien thi dung ten dich vu");
		verifyEquals(electricBill.getDynamicTextByLabel(driver, "Dịch vụ"), Electric_Bills_Data.VALIDATE.ELECTIC_BILL_TITLE);

		log.info("TC_01_Step_20: Hien thi dung Nha cung cap");
		verifyEquals(electricBill.getDynamicTextByLabel(driver, "Nhà cung cấp"), Electric_Bills_Data.DATA.EVN_MIEN_TRUNG);

		log.info("TC_01_Step_21: Hien thi dung ma khach hang");
		verifyEquals(electricBill.getDynamicTextByLabel(driver, "Mã khách hàng"), electricBills);

		log.info("TC_01_Step_22: Hien thi ma giao dich");
		transactionID = electricBill.getDynamicTextByLabel(driver, "Mã giao dịch");

		log.info("TC_01_Step_23: An tiep button 'Thuc hien giao dich moi'");
		verifyEquals(electricBill.getDynamicTextButtonById(driver, "com.VCB:id/btContinue"), "Thực hiện giao dịch mới");
		electricBill.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_24: Hien thi man hinh Hoa don tien dien");
		verifyEquals(electricBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), Electric_Bills_Data.VALIDATE.ELECTIC_BILL_TITLE);

		log.info("TC_07_Step_25: Chon tai khoan nguon");
		electricBill.clickToTextID(driver, "com.VCB:id/number_account");
		electricBill.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_01_Step_26: Xac nhan so du TK nguon da bi tru thanh cong");
		sourceAccountMoney = (convertAvailableBalanceCurrentcyOrFeeToLong(sourceAccountMoney) - convertAvailableBalanceCurrentcyOrFeeToLong(electricBills) - transferFee) + "";
		verifyEquals(electricBill.getDynamicTextByLabel(driver, "Số dư khả dụng"), addCommasToLong(sourceAccountMoney) + " VND");

		log.info("TC_07_Step_27: Chon Nha cung cap EVN Mien Trung");
		electricBill.clickToTextID(driver, "com.VCB:id/wrap_tv");
		electricBill.clickToDynamicButtonLinkOrLinkText(driver, Electric_Bills_Data.DATA.EVN_MIEN_TRUNG);

		log.info("TC_01_Step_28: Nhap ma khach hang");
		electricBill.inputToDynamicEditviewByLinearlayoutId(driver, customerID, "com.VCB:id/llCode");

		log.info("TC_01_Step_29: An nut Tiep Tuc");
		verifyEquals(electricBill.getDynamicTextButtonById(driver, "com.VCB:id/btn_submit"), "Tiếp tục");
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
		home.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");
		transactionReport = PageFactoryManager.getTransactionReportPageObject(driver);

		log.info("TC_02_Step_03: An vao Dropdown 'Tat ca cac loai giao dich");
		transactionReport.clickToTextViewCombobox(driver, "com.VCB:id/tvSelectTransType");

		log.info("TC_02_Step_04: Chon 'Thanh toan hoa don'");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, "Thanh toán hóa đơn");

		log.info("TC_02_Step_05: An vao Dropdown 'Chon tai khoan/the");
		transactionReport.clickToTextViewCombobox(driver, "com.VCB:id/tvSelectAcc");

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
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Dịch vụ"), Electric_Bills_Data.VALIDATE.ELECTIC_BILL_TITLE);

		log.info("TC_02_Step_14: Hien thi dung Nha cung cap");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Nhà cung cấp"), Electric_Bills_Data.DATA.EVN_MIEN_TRUNG);

		log.info("TC_02_Step_15: Hien thi dung Ma khach hang");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Mã khách hàng"), customerID);

		log.info("TC_02_Step_16: Xac nhan hien thi so tien giao dich");
		verifyTrue(transactionReport.isTextDisplayedInListTextElements(driver, electricBills, "com.VCB:id/tvContent"));

		log.info("TC_02_Step_17: Xac nhan hien thi so tien phi");
		verifyTrue(transactionReport.isTextDisplayedInListTextElements(driver, transferFee + "", "com.VCB:id/tvContent"));

		log.info("TC_02_Step_18: Hien thi dung Loai giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Loại giao dịch"), "Thanh toán hóa đơn");

		log.info("TC_02_Step_19: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_02_Step_20: An nut back ve man hinh menu");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_02_Step_21: Mo tab Home");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_1");
	}

	@Parameters("pass")
	@Test
	public void TC_03_ThanhToanTienDien_MK(String pass) {

		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_03_Step_01: Hoa don tien dien");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Hoá đơn tiền điện");
		electricBill = PageFactoryManager.getElectricBillPageObject(driver);

		log.info("TC_03_Step_02: Chon tai khoan nguon");
		electricBill.clickToTextID(driver, "com.VCB:id/number_account");
		electricBill.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);
		sourceAccountMoney = electricBill.getDynamicTextByLabel(driver, "Số dư khả dụng");

		log.info("TC_03_Step_03: Chon Nha cung cap EVN Mien Trung");
		electricBill.clickToTextID(driver, "com.VCB:id/wrap_tv");
		electricBill.clickToDynamicButtonLinkOrLinkText(driver, Electric_Bills_Data.DATA.EVN_MIEN_TRUNG);

		log.info("TC_03_Step_04: Nhap ma khach hang va an tiep tuc");
		customerID = electricBill.inputCustomerId(Electric_Bills_Data.DATA.LIST_CUSTOMER_ID);

		log.info("TC_03_Step_05: Hien thi man hinh xac nhan thong tin");
		verifyEquals(electricBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Xác nhận thông tin");

		log.info("TC_03_Step_06: Hien thi tai khoan nguon");
		verifyEquals(electricBill.getDynamicTextByLabel(driver, "Tài khoản nguồn"), Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_03_Step_07: Hien thi ten dich vu");
		verifyEquals(electricBill.getDynamicTextByLabel(driver, "Dịch vụ"), Electric_Bills_Data.VALIDATE.ELECTIC_BILL_TITLE);

		log.info("TC_03_Step_08: Hien thi Nha cung cap");
		verifyEquals(electricBill.getDynamicTextByLabel(driver, "Nhà cung cấp"), Electric_Bills_Data.DATA.EVN_MIEN_TRUNG);

		log.info("TC_03_Step_09: Hien thi ma khach hang");
		verifyEquals(electricBill.getDynamicTextByLabel(driver, "Mã khách hàng"), customerID);

		log.info("TC_03_Step_10: Hien thi So tien thanh toan");
		electricBills = electricBill.getDynamicTextByLabel(driver, "Số tiền thanh toán");

		log.info("TC_03_Step_11: Chon phuong thuc xac thuc");
		electricBill.scrollDownToText(driver, "Chọn phương thức xác thực");
		electricBill.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/llptxt");
		transferFee = convertAvailableBalanceCurrentcyOrFeeToLong(electricBill.getDynamicTextInTransactionDetail(driver, "Mật khẩu đăng nhập"));
		electricBill.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");

		log.info("TC_03_Step_12: Kiem tra so tien phi");
		verifyEquals(electricBill.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), addCommasToLong(transferFee + "") + " VND");

		log.info("TC_03_Step_13: An nut Tiep Tuc");
		verifyEquals(electricBill.getDynamicTextButtonById(driver, "com.VCB:id/btContinue"), "Tiếp tục");
		electricBill.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_03_Step_14: Nhap du ki tu vao o nhap MK");
		electricBill.inputIntoEditTextByID(driver, pass, "com.VCB:id/pin");

		log.info("TC_03_Step_15: An tiep button 'Tiep tuc'");
		electricBill.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_03_Step_16: Hien thi man hinh giao dich thanh cong");
		verifyEquals(electricBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), "GIAO DỊCH THÀNH CÔNG");

		log.info("TC_03_Step_17: Xac nhan hien thi dung so tien thanh toan");
		verifyEquals(electricBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount"), electricBills);

		log.info("TC_03_Step_18: Xac nhan hien thi thoi gian giao dich");
		transactionDate = electricBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTime");
		verifyTrue(electricBill.isTextDisplayedInListTextElements(driver, getForWardDay(0), "com.VCB:id/tvTime"));

		log.info("TC_03_Step_19: Hien thi dung ten dich vu");
		verifyEquals(electricBill.getDynamicTextByLabel(driver, "Dịch vụ"), Electric_Bills_Data.VALIDATE.ELECTIC_BILL_TITLE);

		log.info("TC_03_Step_20: Hien thi dung Nha cung cap");
		verifyEquals(electricBill.getDynamicTextByLabel(driver, "Nhà cung cấp"), Electric_Bills_Data.DATA.EVN_MIEN_TRUNG);

		log.info("TC_03_Step_21: Hien thi dung ma khach hang");
		verifyEquals(electricBill.getDynamicTextByLabel(driver, "Mã khách hàng"), electricBills);

		log.info("TC_03_Step_22: Hien thi ma giao dich");
		transactionID = electricBill.getDynamicTextByLabel(driver, "Mã giao dịch");

		log.info("TC_03_Step_23: An tiep button 'Thuc hien giao dich moi'");
		verifyEquals(electricBill.getDynamicTextButtonById(driver, "com.VCB:id/btContinue"), "Thực hiện giao dịch mới");
		electricBill.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_03_Step_24: Hien thi man hinh Hoa don tien dien");
		verifyEquals(electricBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), Electric_Bills_Data.VALIDATE.ELECTIC_BILL_TITLE);

		log.info("TC_03_Step_25: Chon tai khoan nguon");
		electricBill.clickToTextID(driver, "com.VCB:id/number_account");
		electricBill.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_03_Step_26: Xac nhan so du TK nguon da bi tru thanh cong");
		sourceAccountMoney = (convertAvailableBalanceCurrentcyOrFeeToLong(sourceAccountMoney) - convertAvailableBalanceCurrentcyOrFeeToLong(electricBills) - transferFee) + "";
		verifyEquals(electricBill.getDynamicTextByLabel(driver, "Số dư khả dụng"), addCommasToLong(sourceAccountMoney) + " VND");

		log.info("TC_03_Step_27: Chon Nha cung cap EVN Mien Trung");
		electricBill.clickToTextID(driver, "com.VCB:id/wrap_tv");
		electricBill.clickToDynamicButtonLinkOrLinkText(driver, Electric_Bills_Data.DATA.EVN_MIEN_TRUNG);

		log.info("TC_03_Step_28: Nhap ma khach hang");
		electricBill.inputToDynamicEditviewByLinearlayoutId(driver, customerID, "com.VCB:id/llCode");

		log.info("TC_03_Step_29: An nut Tiep Tuc");
		verifyEquals(electricBill.getDynamicTextButtonById(driver, "com.VCB:id/btn_submit"), "Tiếp tục");
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
		home.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");
		transactionReport = PageFactoryManager.getTransactionReportPageObject(driver);

		log.info("TC_04_Step_03: An vao Dropdown 'Tat ca cac loai giao dich");
		transactionReport.clickToTextViewCombobox(driver, "com.VCB:id/tvSelectTransType");

		log.info("TC_04_Step_04: Chon 'Thanh toan hoa don'");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, "Thanh toán hóa đơn");

		log.info("TC_04_Step_05: An vao Dropdown 'Chon tai khoan/the");
		transactionReport.clickToTextViewCombobox(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_04_Step_06: Chon tai khoan vua thuc hien giao dich");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_04_Step_07: An nut Tim kiem");
		transactionReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");

		log.info("TC_04_Step_08: An vao giao dich dau tien");
		transactionReport.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_04_Step_09: Xac nhan hien thi Title 'Chi tiet giao dich'");
		verifyEquals(transactionReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Chi tiết giao dịch");

		log.info("TC_04_Step_10: Xac nhan hien thi thoi gian giao dich");
		verifyTrue(transactionReport.isTextDisplayedInListTextElements(driver, convertDateTimeIgnoreHHmmss(transactionDate), "com.VCB:id/tvContent"));

		log.info("TC_04_Step_11: Xac nhan hien thi dung ma giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Số lệnh giao dịch"), transactionID);

		log.info("TC_04_Step_12: Xac nhan hien thi so tai khoan giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Tài khoản/thẻ trích nợ"), Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_04_Step_13: Hien thi dung ten dich vu");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Dịch vụ"), Electric_Bills_Data.VALIDATE.ELECTIC_BILL_TITLE);

		log.info("TC_04_Step_14: Hien thi dung Nha cung cap");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Nhà cung cấp"), Electric_Bills_Data.DATA.EVN_MIEN_TRUNG);

		log.info("TC_04_Step_15: Hien thi dung Ma khach hang");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Mã khách hàng"), customerID);

		log.info("TC_04_Step_16: Xac nhan hien thi so tien giao dich");
		verifyTrue(transactionReport.isTextDisplayedInListTextElements(driver, electricBills, "com.VCB:id/tvContent"));

		log.info("TC_04_Step_17: Xac nhan hien thi so tien phi");
		verifyTrue(transactionReport.isTextDisplayedInListTextElements(driver, transferFee + "", "com.VCB:id/tvContent"));

		log.info("TC_04_Step_18: Hien thi dung Loai giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Loại giao dịch"), "Thanh toán hóa đơn");

		log.info("TC_04_Step_19: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_04_Step_20: An nut back ve man hinh menu");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_04_Step_21: Mo tab Home");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_1");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		service.stop();
	}

}
