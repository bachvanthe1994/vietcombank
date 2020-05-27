package vnpay.vietcombank.mobile_topup;

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
import pageObjects.MobileTopupPageObject;
import pageObjects.TransactionReportPageObject;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.MobileTopupPage_Data.UIs;

public class Mobile_Topup_Flow extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject home;
	private MobileTopupPageObject mobileTopup;
	private TransactionReportPageObject transactionReport;

	private String accountMoneyBefore = "";
	private String accountFee = "";
	private String transactionID = "";

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		if (deviceType.contains("android")) {
			driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		} else if (deviceType.contains("ios")) {
			driver = openIOSApp(deviceName, udid, url);
		}
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);

		home = PageFactoryManager.getHomePageObject(driver);
	}

	@Parameters({ "pass", "phone" })
	@Test
	public void TC_01_NapTheDienThoai_GiaTriMin_QuaMK(String pass, String phone) {

		log.info("TC_01_Step_01: Keo xuong va click vao phan 'Nap tien dien thoai'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Nạp tiền điện thoại");
		mobileTopup = PageFactoryManager.getMobileTopupPageObject(driver);

		log.info("TC_01_Step_02: Click vào DrodownList 'Tai khoan nguon' ");
		mobileTopup.clickToDynamicAcceptButtonContainOR(driver, "com.android.packageinstaller:id/permission_allow_button", "com.android.permissioncontroller:id/permission_allow_button");
		mobileTopup.clickToTextID(driver, "com.VCB:id/number_account");

		log.info("TC_01_Step_03: Chon tai khoan nguon");
		accountMoneyBefore = mobileTopup.getDynamicTextByLabel(driver, Account_Data.Valid_Account.ACCOUNT2);
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_01_Step_04: Click vao menh gia 30,000");
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, UIs.LIST_UNIT_VALUE[0]);

		log.info("TC_01_Step_05: An nut 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btn_submit");

		log.info("TC_01_Step_06: Xac nhan tai khoan nguon");
		verifyEquals(mobileTopup.getDynamicTextByLabel(driver, "Tài khoản nguồn"), Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_01_Step_07: Xac nhan so dien thoai duoc nap");
		verifyEquals(mobileTopup.getDynamicTextByLabel(driver, "Số điện thoại được nạp"), phone);

		log.info("TC_01_Step_08: Xac nhan menh gia the");
		verifyTrue(mobileTopup.getDynamicTextByLabel(driver, "Mệnh giá thẻ").contains(UIs.LIST_UNIT_VALUE[0] + " VND"));

		log.info("TC_01_Step_09: Chon phuong thuc xac thuc SMS OTP");
		accountFee = mobileTopup.getDynamicTextByLabel(driver, "Số tiền phí");
		mobileTopup.clickToTextID(driver, "com.VCB:id/tvptxt");
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");

		log.info("TC_01_Step_10: An nut 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_11: Nhap ki tu vao o nhap mat khau");
		mobileTopup.inputIntoEditTextByID(driver, pass, "com.VCB:id/pin");

		log.info("TC_01_Step_12: An tiep button 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_13: Lay ma giao dich roi an nut tiep tuc");
		transactionID = mobileTopup.getDynamicTextByLabel(driver, "Mã giao dịch");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_14: Xac nhan so tien o tai khoan nguon bi tru dung");
		mobileTopup.clickToTextID(driver, "com.VCB:id/number_account");
		verifyEquals(mobileTopup.getDynamicTextByLabel(driver, Account_Data.Valid_Account.ACCOUNT2), mobileTopup.getStringNumberAfterCaculate(accountMoneyBefore, UIs.LIST_UNIT_VALUE[0], accountFee) + " VND");
		mobileTopup.clickToTextID(driver, "com.VCB:id/cancel_button");

		log.info("TC_01_Step_15: Click back ve man hinh chinh");
		mobileTopup.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
	}

	@Parameters({ "phone" })
	@Test
	public void TC_02_NapTheDienThoai_GiaTriMin_QuaMK_BaoCaoGiaoDich(String phone) {

		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_02_Step_01: Mo tab Menu");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_5");

		log.info("TC_02_Step_02: Mo sub-menu 'Bao cao giao dich");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");
		transactionReport = PageFactoryManager.getTransactionReportPageObject(driver);

		log.info("TC_02_Step_03: An vao Dropdown 'Tat ca cac loai giao dich");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectTransType");

		log.info("TC_02_Step_04: Chon 'Nap tien dien thoai'");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, UIs.MOBILE_TOPUP_TITLE);

		log.info("TC_02_Step_05: An vao Dropdown 'Chon tai khoan/the");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_02_Step_06: Chon tai khoan vua thuc hien giao dich");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_02_Step_07: An nut Tim kiem");
		transactionReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");

		log.info("TC_02_Step_08: An vao giao dich dau tien");
		transactionReport.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_02_Step_09: Xac nhan hien thi Title 'Chi tiet giao dich'");
		verifyEquals(transactionReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Chi tiết giao dịch");

		log.info("TC_02_Step_10: Xac nhan hien thi dung ma giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Số lệnh giao dịch"), transactionID);

		log.info("TC_02_Step_11: Xac nhan hien thi so tai khoan giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Tài khoản/thẻ trích nợ"), Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_02_Step_12: Xac nhan hien thi so dien thoai duoc nap");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Số điện thoại được nạp"), phone);

		log.info("TC_02_Step_13: Xac nhan hien thi loại giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Loại giao dịch"), UIs.MOBILE_TOPUP_TITLE);

		log.info("TC_02_Step_14: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_02_Step_15: An nut back ve man hinh menu");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_02_Step_16: Mo tab Home");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_1");
	}

	@Parameters({ "pass", "phone" })
	@Test
	public void TC_03_NapTheDienThoai_GiaTriMax_QuaMK(String pass, String phone) {

		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_03_Step_01: Keo xuong va click vao phan 'Nap tien dien thoai'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Nạp tiền điện thoại");
		mobileTopup = PageFactoryManager.getMobileTopupPageObject(driver);

		log.info("TC_03_Step_02: Click vào DrodownList 'Tai khoan nguon' ");
		mobileTopup.clickToTextID(driver, "com.VCB:id/number_account");

		log.info("TC_03_Step_03: Chon tai khoan nguon");
		accountMoneyBefore = mobileTopup.getDynamicTextByLabel(driver, Account_Data.Valid_Account.ACCOUNT2);
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_03_Step_04: Click vao menh gia 500,000");
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, UIs.LIST_UNIT_VALUE[5]);

		log.info("TC_03_Step_05: An nut 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btn_submit");

		log.info("TC_03_Step_06: Xac nhan tai khoan nguon");
		verifyEquals(mobileTopup.getDynamicTextByLabel(driver, "Tài khoản nguồn"), Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_03_Step_07: Xac nhan so dien thoai duoc nap");
		verifyEquals(mobileTopup.getDynamicTextByLabel(driver, "Số điện thoại được nạp"), phone);

		log.info("TC_03_Step_08: Xac nhan menh gia the");
		verifyTrue(mobileTopup.getDynamicTextByLabel(driver, "Mệnh giá thẻ").contains(UIs.LIST_UNIT_VALUE[5] + " VND"));

		log.info("TC_03_Step_09: Chon phuong thuc xac thuc SMS OTP");
		accountFee = mobileTopup.getDynamicTextByLabel(driver, "Số tiền phí");
		mobileTopup.clickToTextID(driver, "com.VCB:id/tvptxt");
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");

		log.info("TC_03_Step_10: An nut 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_03_Step_11: Nhap ki tu vao o nhap mat khau");
		mobileTopup.inputIntoEditTextByID(driver, pass, "com.VCB:id/pin");

		log.info("TC_03_Step_12: An tiep button 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_03_Step_13: Lay ma giao dich roi an nut tiep tuc");
		transactionID = mobileTopup.getDynamicTextByLabel(driver, "Mã giao dịch");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_03_Step_14: Xac nhan so tien o tai khoan nguon bi tru dung");
		mobileTopup.clickToTextID(driver, "com.VCB:id/number_account");
		verifyEquals(mobileTopup.getDynamicTextByLabel(driver, Account_Data.Valid_Account.ACCOUNT2), mobileTopup.getStringNumberAfterCaculate(accountMoneyBefore, UIs.LIST_UNIT_VALUE[5], accountFee) + " VND");
		mobileTopup.clickToTextID(driver, "com.VCB:id/cancel_button");

		log.info("TC_03_Step_15: Click back ve man hinh chinh");
		mobileTopup.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
	}

	@Parameters({ "phone" })
	@Test
	public void TC_04_NapTheDienThoai_GiaTriMax_QuaMK_BaoCaoGiaoDich(String phone) {

		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_04_Step_01: Mo tab Menu");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_5");

		log.info("TC_04_Step_02: Mo sub-menu 'Bao cao giao dich");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");
		transactionReport = PageFactoryManager.getTransactionReportPageObject(driver);

		log.info("TC_04_Step_03: An vao Dropdown 'Tat ca cac loai giao dich");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectTransType");

		log.info("TC_04_Step_04: Chon 'Nap tien dien thoai'");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, UIs.MOBILE_TOPUP_TITLE);

		log.info("TC_04_Step_05: An vao Dropdown 'Chon tai khoan/the");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_04_Step_06: Chon tai khoan vua thuc hien giao dich");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_04_Step_07: An nut Tim kiem");
		transactionReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");

		log.info("TC_04_Step_08: An vao giao dich dau tien");
		transactionReport.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_04_Step_09: Xac nhan hien thi Title 'Chi tiet giao dich'");
		verifyEquals(transactionReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Chi tiết giao dịch");

		log.info("TC_04_Step_10: Xac nhan hien thi dung ma giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Số lệnh giao dịch"), transactionID);

		log.info("TC_04_Step_11: Xac nhan hien thi so tai khoan giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Tài khoản/thẻ trích nợ"), Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_04_Step_12: Xac nhan hien thi so dien thoai duoc nap");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Số điện thoại được nạp"), phone);

		log.info("TC_04_Step_13: Xac nhan hien thi loại giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Loại giao dịch"), UIs.MOBILE_TOPUP_TITLE);

		log.info("TC_04_Step_14: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_04_Step_15: An nut back ve man hinh menu");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_04_Step_16: Mo tab Home");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_1");
	}

	@Parameters({ "otp", "phone" })
	@Test
	public void TC_05_NapTheDienThoai_GiaTriMin_QuaOTP(String otp, String phone) {

		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_05_Step_01: Keo xuong va click vao phan 'Nap tien dien thoai'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Nạp tiền điện thoại");
		mobileTopup = PageFactoryManager.getMobileTopupPageObject(driver);

		log.info("TC_05_Step_02: Click vào DrodownList 'Tai khoan nguon' ");
		mobileTopup.clickToTextID(driver, "com.VCB:id/number_account");

		log.info("TC_05_Step_03: Chon tai khoan nguon");
		accountMoneyBefore = mobileTopup.getDynamicTextByLabel(driver, Account_Data.Valid_Account.ACCOUNT2);
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_05_Step_04: Click vao menh gia 30,000");
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, UIs.LIST_UNIT_VALUE[0]);

		log.info("TC_05_Step_05: An nut 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btn_submit");

		log.info("TC_05_Step_06: Xac nhan tai khoan nguon");
		verifyEquals(mobileTopup.getDynamicTextByLabel(driver, "Tài khoản nguồn"), Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_05_Step_07: Xac nhan so dien thoai duoc nap");
		verifyEquals(mobileTopup.getDynamicTextByLabel(driver, "Số điện thoại được nạp"), phone);

		log.info("TC_05_Step_08: Xac nhan menh gia the");
		verifyTrue(mobileTopup.getDynamicTextByLabel(driver, "Mệnh giá thẻ").contains(UIs.LIST_UNIT_VALUE[0] + " VND"));

		log.info("TC_05_Step_09: Chon phuong thuc xac thuc SMS OTP");
		accountFee = mobileTopup.getDynamicTextByLabel(driver, "Số tiền phí");
		mobileTopup.clickToTextID(driver, "com.VCB:id/tvptxt");
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_05_Step_10: An nut 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_05_Step_11: Nhap du ki tu vao o nhap OTP");
		mobileTopup.inputToDynamicOtp(driver, otp, "Tiếp tục");

		log.info("TC_05_Step_12: An tiep button 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_05_Step_13: Lay ma giao dich roi an nut tiep tuc");
		transactionID = mobileTopup.getDynamicTextByLabel(driver, "Mã giao dịch");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_05_Step_14: Xac nhan so tien o tai khoan nguon bi tru dung");
		mobileTopup.clickToTextID(driver, "com.VCB:id/number_account");
		verifyEquals(mobileTopup.getDynamicTextByLabel(driver, Account_Data.Valid_Account.ACCOUNT2), mobileTopup.getStringNumberAfterCaculate(accountMoneyBefore, UIs.LIST_UNIT_VALUE[0], accountFee) + " VND");
		mobileTopup.clickToTextID(driver, "com.VCB:id/cancel_button");

		log.info("TC_05_Step_15: Click back ve man hinh chinh");
		mobileTopup.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
	}

	@Parameters({ "phone" })
	@Test
	public void TC_06_NapTheDienThoai_GiaTriMin_QuaOTP_BaoCaoGiaoDich(String phone) {

		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_06_Step_01: Mo tab Menu");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_5");

		log.info("TC_06_Step_02: Mo sub-menu 'Bao cao giao dich");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");
		transactionReport = PageFactoryManager.getTransactionReportPageObject(driver);

		log.info("TC_06_Step_03: An vao Dropdown 'Tat ca cac loai giao dich");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectTransType");

		log.info("TC_06_Step_04: Chon 'Nap tien dien thoai'");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, UIs.MOBILE_TOPUP_TITLE);

		log.info("TC_06_Step_05: An vao Dropdown 'Chon tai khoan/the");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_06_Step_06: Chon tai khoan vua thuc hien giao dich");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_06_Step_07: An nut Tim kiem");
		transactionReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");

		log.info("TC_06_Step_08: An vao giao dich dau tien");
		transactionReport.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_06_Step_09: Xac nhan hien thi Title 'Chi tiet giao dich'");
		verifyEquals(transactionReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Chi tiết giao dịch");

		log.info("TC_06_Step_10: Xac nhan hien thi dung ma giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Số lệnh giao dịch"), transactionID);

		log.info("TC_06_Step_11: Xac nhan hien thi so tai khoan giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Tài khoản/thẻ trích nợ"), Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_06_Step_12: Xac nhan hien thi so dien thoai duoc nap");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Số điện thoại được nạp"), phone);

		log.info("TC_06_Step_13: Xac nhan hien thi loại giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Loại giao dịch"), UIs.MOBILE_TOPUP_TITLE);

		log.info("TC_06_Step_14: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_06_Step_15: An nut back ve man hinh menu");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_06_Step_16: Mo tab Home");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_1");
	}

	@Parameters({ "otp", "phone" })
	@Test
	public void TC_07_NapTheDienThoai_GiaTriMax_QuaOTP(String otp, String phone) {

		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_07_Step_01: Keo xuong va click vao phan 'Nap tien dien thoai'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Nạp tiền điện thoại");
		mobileTopup = PageFactoryManager.getMobileTopupPageObject(driver);

		log.info("TC_07_Step_02: Click vào DrodownList 'Tai khoan nguon' ");
		mobileTopup.clickToTextID(driver, "com.VCB:id/number_account");

		log.info("TC_07_Step_03: Chon tai khoan nguon");
		accountMoneyBefore = mobileTopup.getDynamicTextByLabel(driver, Account_Data.Valid_Account.ACCOUNT2);
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_07_Step_04: Click vao menh gia 30,000");
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, UIs.LIST_UNIT_VALUE[5]);

		log.info("TC_07_Step_05: An nut 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btn_submit");

		log.info("TC_07_Step_06: Xac nhan tai khoan nguon");
		verifyEquals(mobileTopup.getDynamicTextByLabel(driver, "Tài khoản nguồn"), Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_07_Step_07: Xac nhan so dien thoai duoc nap");
		verifyEquals(mobileTopup.getDynamicTextByLabel(driver, "Số điện thoại được nạp"), phone);

		log.info("TC_07_Step_08: Xac nhan menh gia the");
		verifyTrue(mobileTopup.getDynamicTextByLabel(driver, "Mệnh giá thẻ").contains(UIs.LIST_UNIT_VALUE[5] + " VND"));

		log.info("TC_07_Step_09: Chon phuong thuc xac thuc SMS OTP");
		accountFee = mobileTopup.getDynamicTextByLabel(driver, "Số tiền phí");
		mobileTopup.clickToTextID(driver, "com.VCB:id/tvptxt");
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_07_Step_10: An nut 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_07_Step_11: Nhap du ki tu vao o nhap OTP");
		mobileTopup.inputToDynamicOtp(driver, otp, "Tiếp tục");

		log.info("TC_07_Step_12: An tiep button 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_07_Step_13: Lay ma giao dich roi an nut tiep tuc");
		transactionID = mobileTopup.getDynamicTextByLabel(driver, "Mã giao dịch");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_07_Step_14: Xac nhan so tien o tai khoan nguon bi tru dung");
		mobileTopup.clickToTextID(driver, "com.VCB:id/number_account");
		verifyEquals(mobileTopup.getDynamicTextByLabel(driver, Account_Data.Valid_Account.ACCOUNT2), mobileTopup.getStringNumberAfterCaculate(accountMoneyBefore, UIs.LIST_UNIT_VALUE[5], accountFee) + " VND");
		mobileTopup.clickToTextID(driver, "com.VCB:id/cancel_button");

		log.info("TC_07_Step_15: Click back ve man hinh chinh");
		mobileTopup.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
	}

	@Parameters({ "phone" })
	@Test
	public void TC_08_NapTheDienThoai_GiaTriMax_QuaOTP_BaoCaoGiaoDich(String phone) {

		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_08_Step_01: Mo tab Menu");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_5");

		log.info("TC_08_Step_02: Mo sub-menu 'Bao cao giao dich");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");
		transactionReport = PageFactoryManager.getTransactionReportPageObject(driver);

		log.info("TC_08_Step_03: An vao Dropdown 'Tat ca cac loai giao dich");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectTransType");

		log.info("TC_08_Step_04: Chon 'Nap tien dien thoai'");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, UIs.MOBILE_TOPUP_TITLE);

		log.info("TC_08_Step_05: An vao Dropdown 'Chon tai khoan/the");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_08_Step_06: Chon tai khoan vua thuc hien giao dich");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_08_Step_07: An nut Tim kiem");
		transactionReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");

		log.info("TC_08_Step_08: An vao giao dich dau tien");
		transactionReport.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_08_Step_09: Xac nhan hien thi Title 'Chi tiet giao dich'");
		verifyEquals(transactionReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Chi tiết giao dịch");

		log.info("TC_08_Step_10: Xac nhan hien thi dung ma giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Số lệnh giao dịch"), transactionID);

		log.info("TC_08_Step_11: Xac nhan hien thi so tai khoan giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Tài khoản/thẻ trích nợ"), Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_08_Step_12: Xac nhan hien thi so dien thoai duoc nap");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Số điện thoại được nạp"), phone);

		log.info("TC_08_Step_13: Xac nhan hien thi loại giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Loại giao dịch"), UIs.MOBILE_TOPUP_TITLE);

		log.info("TC_08_Step_14: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_08_Step_15: An nut back ve man hinh menu");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_08_Step_16: Mo tab Home");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_1");
	}

	@Parameters({ "pass" })
	@Test
	public void TC_09_NapTheDienThoai_QuaSoDienThoaiKhac_QuaMK(String pass) {

		log.info("TC_09_Step_01: Keo xuong va click vao phan 'Nap tien dien thoai'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Nạp tiền điện thoại");
		mobileTopup = PageFactoryManager.getMobileTopupPageObject(driver);

		log.info("TC_09_Step_02: Click vào DrodownList 'Tai khoan nguon' ");
		mobileTopup.clickToTextID(driver, "com.VCB:id/number_account");

		log.info("TC_09_Step_03: Chon tai khoan nguon");
		accountMoneyBefore = mobileTopup.getDynamicTextByLabel(driver, Account_Data.Valid_Account.ACCOUNT2);
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_09_Step_04: Nhap so dien thoai");
		mobileTopup.inputIntoEditTextByID(driver, UIs.OTHER_PHONE_NUMBER, "com.VCB:id/mobile");

		log.info("TC_09_Step_05: An nut 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btn_submit");

		log.info("TC_09_Step_06: Xac nhan tai khoan nguon");
		verifyEquals(mobileTopup.getDynamicTextByLabel(driver, "Tài khoản nguồn"), Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_09_Step_07: Xac nhan so dien thoai duoc nap");
		verifyEquals(mobileTopup.getDynamicTextByLabel(driver, "Số điện thoại được nạp"), UIs.OTHER_PHONE_NUMBER);

		log.info("TC_09_Step_08: Xac nhan menh gia the");
		verifyTrue(mobileTopup.getDynamicTextByLabel(driver, "Mệnh giá thẻ").contains(UIs.DEFAULT_UNIT_VALUE + " VND"));

		log.info("TC_09_Step_09: Chon phuong thuc xac thuc SMS OTP");
		accountFee = mobileTopup.getDynamicTextByLabel(driver, "Số tiền phí");
		mobileTopup.clickToTextID(driver, "com.VCB:id/tvptxt");
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");

		log.info("TC_09_Step_10: An nut 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_09_Step_11: Nhap ki tu vao o nhap mat khau");
		mobileTopup.inputIntoEditTextByID(driver, pass, "com.VCB:id/pin");

		log.info("TC_09_Step_12: An tiep button 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_09_Step_13: Lay ma giao dich roi an nut tiep tuc");
		transactionID = mobileTopup.getDynamicTextByLabel(driver, "Mã giao dịch");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_09_Step_14: Xac nhan so tien o tai khoan nguon bi tru dung");
		mobileTopup.clickToTextID(driver, "com.VCB:id/number_account");
		verifyEquals(mobileTopup.getDynamicTextByLabel(driver, Account_Data.Valid_Account.ACCOUNT2), mobileTopup.getStringNumberAfterCaculate(accountMoneyBefore, UIs.DEFAULT_UNIT_VALUE, accountFee) + " VND");
		mobileTopup.clickToTextID(driver, "com.VCB:id/cancel_button");

		log.info("TC_09_Step_15: Click back ve man hinh chinh");
		mobileTopup.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
	}

	@Test
	public void TC_10_NapTheDienThoai_QuaSoDienThoaiKhac_BaoCaoGiaoDich() {

		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_10_Step_01: Mo tab Menu");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_5");

		log.info("TC_10_Step_02: Mo sub-menu 'Bao cao giao dich");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");
		transactionReport = PageFactoryManager.getTransactionReportPageObject(driver);

		log.info("TC_10_Step_03: An vao Dropdown 'Tat ca cac loai giao dich");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectTransType");

		log.info("TC_10_Step_04: Chon 'Nap tien dien thoai'");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, UIs.MOBILE_TOPUP_TITLE);

		log.info("TC_10_Step_05: An vao Dropdown 'Chon tai khoan/the");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_10_Step_06: Chon tai khoan vua thuc hien giao dich");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_10_Step_07: An nut Tim kiem");
		transactionReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");

		log.info("TC_10_Step_08: An vao giao dich dau tien");
		transactionReport.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_10_Step_09: Xac nhan hien thi Title 'Chi tiet giao dich'");
		verifyEquals(transactionReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Chi tiết giao dịch");

		log.info("TC_10_Step_10: Xac nhan hien thi dung ma giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Số lệnh giao dịch"), transactionID);

		log.info("TC_10_Step_11: Xac nhan hien thi so tai khoan giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Tài khoản/thẻ trích nợ"), Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_10_Step_12: Xac nhan hien thi so dien thoai duoc nap");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Số điện thoại được nạp"), UIs.OTHER_PHONE_NUMBER);

		log.info("TC_10_Step_13: Xac nhan hien thi loại giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Loại giao dịch"), UIs.MOBILE_TOPUP_TITLE);

		log.info("TC_10_Step_14: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_10_Step_15: An nut back ve man hinh menu");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_10_Step_16: Mo tab Home");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_1");
	}

	@Parameters({ "otp" })
	@Test
	public void TC_11_NapTheDienThoai_QuaSoDienThoaiKhac_QuaOTP(String otp) {

		log.info("TC_11_Step_01: Keo xuong va click vao phan 'Nap tien dien thoai'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Nạp tiền điện thoại");
		mobileTopup = PageFactoryManager.getMobileTopupPageObject(driver);

		log.info("TC_11_Step_02: Click vào DrodownList 'Tai khoan nguon' ");
		mobileTopup.clickToTextID(driver, "com.VCB:id/number_account");

		log.info("TC_11_Step_03: Chon tai khoan nguon");
		accountMoneyBefore = mobileTopup.getDynamicTextByLabel(driver, Account_Data.Valid_Account.ACCOUNT2);
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_11_Step_04: Nhap so dien thoai");
		mobileTopup.inputIntoEditTextByID(driver, UIs.OTHER_PHONE_NUMBER, "com.VCB:id/mobile");

		log.info("TC_11_Step_05: An nut 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btn_submit");

		log.info("TC_11_Step_06: Xac nhan tai khoan nguon");
		verifyEquals(mobileTopup.getDynamicTextByLabel(driver, "Tài khoản nguồn"), Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_11_Step_07: Xac nhan so dien thoai duoc nap");
		verifyEquals(mobileTopup.getDynamicTextByLabel(driver, "Số điện thoại được nạp"), UIs.OTHER_PHONE_NUMBER);

		log.info("TC_11_Step_08: Xac nhan menh gia the");
		verifyTrue(mobileTopup.getDynamicTextByLabel(driver, "Mệnh giá thẻ").contains(UIs.DEFAULT_UNIT_VALUE + " VND"));

		log.info("TC_11_Step_09: Chon phuong thuc xac thuc SMS OTP");
		accountFee = mobileTopup.getDynamicTextByLabel(driver, "Số tiền phí");
		mobileTopup.clickToTextID(driver, "com.VCB:id/tvptxt");
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_11_Step_10: An nut 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_11_Step_11: Nhap ki tu vao o nhap OTP");
		mobileTopup.inputToDynamicOtp(driver, otp, "Tiếp tục");

		log.info("TC_11_Step_12: An tiep button 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_11_Step_13: Lay ma giao dich roi an nut tiep tuc");
		transactionID = mobileTopup.getDynamicTextByLabel(driver, "Mã giao dịch");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_11_Step_14: Xac nhan so tien o tai khoan nguon bi tru dung");
		mobileTopup.clickToTextID(driver, "com.VCB:id/number_account");
		verifyEquals(mobileTopup.getDynamicTextByLabel(driver, Account_Data.Valid_Account.ACCOUNT2), mobileTopup.getStringNumberAfterCaculate(accountMoneyBefore, UIs.DEFAULT_UNIT_VALUE, accountFee) + " VND");
		mobileTopup.clickToTextID(driver, "com.VCB:id/cancel_button");

		log.info("TC_11_Step_15: Click back ve man hinh chinh");
		mobileTopup.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
	}

	@Test
	public void TC_12_NapTheDienThoai_QuaSoDienThoaiKhac_BaoCaoGiaoDich() {

		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_12_Step_01: Mo tab Menu");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_5");

		log.info("TC_12_Step_02: Mo sub-menu 'Bao cao giao dich");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");
		transactionReport = PageFactoryManager.getTransactionReportPageObject(driver);

		log.info("TC_12_Step_03: An vao Dropdown 'Tat ca cac loai giao dich");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectTransType");

		log.info("TC_12_Step_04: Chon 'Nap tien dien thoai'");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, UIs.MOBILE_TOPUP_TITLE);

		log.info("TC_12_Step_05: An vao Dropdown 'Chon tai khoan/the");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_12_Step_06: Chon tai khoan vua thuc hien giao dich");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_12_Step_07: An nut Tim kiem");
		transactionReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");

		log.info("TC_12_Step_08: An vao giao dich dau tien");
		transactionReport.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_12_Step_09: Xac nhan hien thi Title 'Chi tiet giao dich'");
		verifyEquals(transactionReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Chi tiết giao dịch");

		log.info("TC_12_Step_10: Xac nhan hien thi dung ma giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Số lệnh giao dịch"), transactionID);

		log.info("TC_12_Step_11: Xac nhan hien thi so tai khoan giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Tài khoản/thẻ trích nợ"), Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_12_Step_12: Xac nhan hien thi so dien thoai duoc nap");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Số điện thoại được nạp"), UIs.OTHER_PHONE_NUMBER);

		log.info("TC_12_Step_13: Xac nhan hien thi loại giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, "Loại giao dịch"), UIs.MOBILE_TOPUP_TITLE);

		log.info("TC_12_Step_14: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_12_Step_15: An nut back ve man hinh menu");
		transactionReport.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_12_Step_16: Mo tab Home");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_1");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		service.stop();
	}

}
