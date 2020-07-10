package vnpay.vietcombank.mobile_topup;

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
import pageObjects.MobileTopupPageObject;
import pageObjects.SettingVCBSmartOTPPageObject;
import pageObjects.TransactionReportPageObject;
import vietcombank_test_data.HomePage_Data.Home_Text_Elements;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.MobileTopupPage_Data.Text;
import vietcombank_test_data.MobileTopupPage_Data.UIs;
import vietcombank_test_data.TransactionReport_Data.ReportTitle;

public class Mobile_Topup_Flow_Smart_OTP extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject home;
	private MobileTopupPageObject mobileTopup;
	private SettingVCBSmartOTPPageObject smartOTP;
	private TransactionReportPageObject transactionReport;

	SourceAccountModel sourceAcoount = new SourceAccountModel();

	private String accountMoneyBefore, other_Phone_Number, otpSmart, newOtp, transactionID = "";

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

		home = PageFactoryManager.getHomePageObject(driver);
		other_Phone_Number = getDataInCell(8);
		home = PageFactoryManager.getHomePageObject(driver);
		mobileTopup = PageFactoryManager.getMobileTopupPageObject(driver);
		smartOTP = PageFactoryManager.getSettingVCBSmartOTPPageObject(driver);
		transactionReport = PageFactoryManager.getTransactionReportPageObject(driver);

		otpSmart = getDataInCell(6);

		log.info("Setup smart OTP");
		smartOTP.setupSmartOTP(LogIn_Data.Login_Account.Smart_OTP, otpSmart);
		newOtp = "111222";
		log.info("TC_00_Step_01: Keo xuong va click vao phan 'Nap tien dien thoai'");
		home.clickToDynamicButtonLinkOrLinkText(driver, Home_Text_Elements.MOBILE_TOPUP);
		mobileTopup.clickToDynamicAcceptButtonContainOR(driver, "com.android.packageinstaller:id/permission_allow_button", "com.android.permissioncontroller:id/permission_allow_button");


	}
	
	
	@Parameters({ "otp" })
	@Test(invocationCount=2)
	public void TC_00_NapTheDienThoai_QuaSoDienThoaiKhac_QuaOTP(String otp) {

		log.info("TC_00_Step_02: Click vào DrodownList 'Tai khoan nguon' ");
		mobileTopup.clickToTextID(driver, "com.VCB:id/number_account");

		log.info("TC_00_Step_03: Chon tai khoan nguon");

		sourceAcoount = mobileTopup.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		log.info("TC_00_Step_04: Nhap so dien thoai");
		mobileTopup.inputIntoEditTextByID(driver, other_Phone_Number, "com.VCB:id/mobile");

		log.info("TC_00_Step_05: An nut 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btn_submit");

		
		log.info("TC_00_Step_09: Chon phuong thuc xac thuc SMS OTP");
		mobileTopup.clickToTextID(driver, "com.VCB:id/tvptxt");

		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, Text.SMS_OTP);

		log.info("TC_00_Step_10: An nut 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_00_Step_11: Nhap ki tu vao o nhap OTP");
		mobileTopup.inputToDynamicOtp(driver, otp, Text.COUTINUE);

		log.info("TC_00_Step_12: An tiep button 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

	}

	@Parameters({ "phone" })
	@Test
	public void TC_01_NapTheDienThoai_GiaTriMin_QuaMK(String phone) {

		log.info("TC_01_Step_01: Keo xuong va click vao phan 'Nap tien dien thoai'");
		log.info("TC_01_Step_02: Click vào DrodownList 'Tai khoan nguon' ");
		mobileTopup.clickToTextID(driver, "com.VCB:id/number_account");

		log.info("TC_01_Step_03: Chon tai khoan nguon");

		sourceAcoount = mobileTopup.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);
		accountMoneyBefore = sourceAcoount.account;

		log.info("TC_01_Step_04: Click vao menh gia 30,000");
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, UIs.LIST_UNIT_VALUE[0]);

		log.info("TC_01_Step_05: An nut 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btn_submit");

		log.info("TC_01_Step_06: Xac nhan tai khoan nguon");
		verifyEquals(mobileTopup.getDynamicTextByLabel(driver, Text.SOURCE_ACCOUNT), accountMoneyBefore);

		log.info("TC_01_Step_07: Xac nhan so dien thoai duoc nap");
		verifyEquals(mobileTopup.getDynamicTextByLabel(driver, Text.PHONE_TOPUP), phone);

		log.info("TC_01_Step_08: Xac nhan menh gia the");
		verifyTrue(mobileTopup.getDynamicTextByLabel(driver, Text.PRICE_CARD).contains(UIs.LIST_UNIT_VALUE[0] + " VND"));

		log.info("TC_01_Step_09: Chon phuong thuc xac thuc Smart OTP");
		mobileTopup.clickToTextID(driver, "com.VCB:id/tvptxt");
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, Text.SMART_OTP);

		log.info("TC_01_Step_10: An nut 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_11: Nhap ki tu vao o nhap mat khau");
		mobileTopup.inputToDynamicSmartOTP(driver, newOtp, "com.VCB:id/otp");

		log.info("TC_01_Step_12: An tiep button 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/submit");

		log.info("TC_01_Step_12: An tiep button 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		verifyEquals(mobileTopup.getDynamicTextFollowingText(driver, Text.SUCCESS_TRANSFER), UIs.LIST_UNIT_VALUE[0] + " VND");
		verifyEquals(mobileTopup.getDynamicTextByLabel(driver, Text.PHONE_TOPUP), phone);

		log.info("TC_01_Step_13: Lay ma giao dich roi an nut tiep tuc");
		transactionID = mobileTopup.getDynamicTextByLabel(driver, Text.CODE_TRANSFER);

		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_15: Click back ve man hinh chinh");
		mobileTopup.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
	}

	@Parameters({ "phone" })
	@Test
	public void TC_02_NapTheDienThoai_GiaTriMin_QuaMK_BaoCaoGiaoDich(String phone) {

		log.info("TC_02_Step_01: Mo tab Menu");
		home.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_02_Step_02: Mo sub-menu 'Bao cao giao dich");
		home.clickToDynamicButtonLinkOrLinkText(driver, Home_Text_Elements.TRANSFER_REPORT);
		transactionReport = PageFactoryManager.getTransactionReportPageObject(driver);

		log.info("TC_02_Step_03: An vao Dropdown 'Tat ca cac loai giao dich");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectTransType");

		log.info("TC_02_Step_04: Chon 'Nap tien dien thoai'");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.MOBILE_TOPUP_TITLE);

		log.info("TC_02_Step_05: An vao Dropdown 'Chon tai khoan/the");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_02_Step_06: Chon tai khoan vua thuc hien giao dich");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, sourceAcoount.account);

		log.info("TC_02_Step_07: An nut Tim kiem");
		transactionReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");

		log.info("TC_02_Step_08: An vao giao dich dau tien");
		transactionReport.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_02_Step_09: Xac nhan hien thi Title 'Chi tiet giao dich'");
		verifyEquals(transactionReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), ReportTitle.DETAIL_TRANSFER);

		log.info("TC_02_Step_10: Xac nhan hien thi dung ma giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.TRANSACTION_NUMBER), transactionID);

		log.info("TC_02_Step_11: Xac nhan hien thi so tai khoan giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.ACCOUNT_CARD), sourceAcoount.account);

		log.info("TC_02_Step_12: Xac nhan hien thi so dien thoai duoc nap");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.PHONE_TOPUP), phone);

		log.info("TC_02_Step_13: Xac nhan hien thi loại giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.TRANSACTION_TYPE), ReportTitle.MOBILE_TOPUP_TITLE);

		log.info("TC_02_Step_14: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_02_Step_15: An nut back ve man hinh menu");
		transactionReport.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_02_Step_16: Mo tab Home");
		home.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@Test
	public void TC_03_NapTheDienThoai_QuaSoDienThoaiKhac_QuaMK() {

		log.info("TC_03_Step_01: Keo xuong va click vao phan 'Nap tien dien thoai'");
		home.clickToDynamicButtonLinkOrLinkText(driver, Home_Text_Elements.MOBILE_TOPUP);

		log.info("TC_03_Step_02: Click vào DrodownList 'Tai khoan nguon' ");
		mobileTopup.clickToTextID(driver, "com.VCB:id/number_account");

		log.info("TC_03_Step_03: Chon tai khoan nguon");
		sourceAcoount = mobileTopup.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);
		accountMoneyBefore = sourceAcoount.account;

		log.info("TC_03_Step_04: Nhap so dien thoai");
		mobileTopup.inputIntoEditTextByID(driver, other_Phone_Number, "com.VCB:id/mobile");

		log.info("TC_03_Step_05: An nut 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btn_submit");

		log.info("TC_03_Step_06: Xac nhan tai khoan nguon");
		verifyEquals(mobileTopup.getDynamicTextByLabel(driver, Text.SOURCE_ACCOUNT), accountMoneyBefore);

		log.info("TC_03_Step_07: Xac nhan so dien thoai duoc nap");
		verifyEquals(mobileTopup.getDynamicTextByLabel(driver, Text.PHONE_TOPUP), other_Phone_Number);

		log.info("TC_03_Step_08: Xac nhan menh gia the");
		verifyTrue(mobileTopup.getDynamicTextByLabel(driver, Text.PRICE_CARD).contains(UIs.DEFAULT_UNIT_VALUE + " VND"));

		log.info("TC_03_Step_09: Chon phuong thuc xac thuc Smart OTP");
		mobileTopup.clickToTextID(driver, "com.VCB:id/tvptxt");
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, Text.SMART_OTP);

		log.info("TC_03_Step_10: An nut 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_03_Step_11: Nhap ki tu vao o nhap mat khau");
		mobileTopup.inputToDynamicSmartOTP(driver, newOtp, "com.VCB:id/otp");

		log.info("TC_03_Step_12: An tiep button 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/submit");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		verifyEquals(mobileTopup.getDynamicTextFollowingText(driver, Text.SUCCESS_TRANSFER), UIs.LIST_UNIT_VALUE[2] + " VND");
		verifyEquals(mobileTopup.getDynamicTextByLabel(driver, Text.PHONE_TOPUP), other_Phone_Number);

		log.info("TC_03_Step_13: Lay ma giao dich roi an nut tiep tuc");
		transactionID = mobileTopup.getDynamicTextByLabel(driver, Text.CODE_TRANSFER);

		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_03_Step_15: Click back ve man hinh chinh");
		mobileTopup.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
	}

	@Parameters({ "phone" })
	@Test
	public void TC_04_NapTheDienThoai_GiaTriMax_QuaMK_BaoCaoGiaoDich(String phone) {

		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_04_Step_01: Mo tab Menu");
		home.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_04_Step_02: Mo sub-menu 'Bao cao giao dich");
		home.clickToDynamicButtonLinkOrLinkText(driver, Home_Text_Elements.TRANSFER_REPORT);
		transactionReport = PageFactoryManager.getTransactionReportPageObject(driver);

		log.info("TC_04_Step_03: An vao Dropdown 'Tat ca cac loai giao dich");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectTransType");

		log.info("TC_04_Step_04: Chon 'Nap tien dien thoai'");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.MOBILE_TOPUP_TITLE);

		log.info("TC_04_Step_05: An vao Dropdown 'Chon tai khoan/the");
		transactionReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_04_Step_06: Chon tai khoan vua thuc hien giao dich");
		transactionReport.clickToDynamicButtonLinkOrLinkText(driver, sourceAcoount.account);

		log.info("TC_04_Step_07: An nut Tim kiem");
		transactionReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");

		log.info("TC_04_Step_08: An vao giao dich dau tien");
		transactionReport.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_04_Step_09: Xac nhan hien thi Title 'Chi tiet giao dich'");
		verifyEquals(transactionReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), ReportTitle.DETAIL_TRANSFER);

		log.info("TC_04_Step_10: Xac nhan hien thi dung ma giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.TRANSACTION_NUMBER), transactionID);

		log.info("TC_04_Step_11: Xac nhan hien thi so tai khoan giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.ACCOUNT_CARD), sourceAcoount.account);

		log.info("TC_04_Step_12: Xac nhan hien thi so dien thoai duoc nap");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.PHONE_TOPUP), other_Phone_Number);

		log.info("TC_04_Step_13: Xac nhan hien thi loại giao dich");
		verifyEquals(transactionReport.getDynamicTextByLabel(driver, ReportTitle.TRANSACTION_TYPE), ReportTitle.MOBILE_TOPUP_TITLE);

		log.info("TC_04_Step_14: An nut back ve man hinh bao cao giao dich");
		transactionReport.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_04_Step_15: An nut back ve man hinh menu");
		transactionReport.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_04_Step_16: Mo tab Home");
		home.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		service.stop();
	}

}
