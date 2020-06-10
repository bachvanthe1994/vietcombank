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
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.HomePage_Data.Home_Text_Elements;
import vietcombank_test_data.MobileTopupPage_Data.Text;
import vietcombank_test_data.MobileTopupPage_Data.UIs;

public class Mobile_Topup_Flow_Smart_OTP extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject home;
	private MobileTopupPageObject mobileTopup;
	private SettingVCBSmartOTPPageObject smartOTP;

	SourceAccountModel sourceAcoount = new SourceAccountModel();

	private String accountMoneyBefore, other_Phone_Number, otpSmart,newOtp = "";

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
		otpSmart = getDataInCell(6);
		
		log.info("Setup smart OTP");
		smartOTP.setupSmartOTP(LogIn_Data.Login_Account.Smart_OTP, otpSmart);
		newOtp="11112222";

	}

	@Parameters({ "phone" })
	@Test
	public void TC_01_NapTheDienThoai_GiaTriMin_QuaMK( String phone) {


		log.info("TC_01_Step_01: Keo xuong va click vao phan 'Nap tien dien thoai'");
		home.clickToDynamicButtonLinkOrLinkText(driver, Home_Text_Elements.MOBILE_TOPUP);
		mobileTopup = PageFactoryManager.getMobileTopupPageObject(driver);

		log.info("TC_01_Step_02: Click vào DrodownList 'Tai khoan nguon' ");
		mobileTopup.clickToDynamicAcceptButtonContainOR(driver, "com.android.packageinstaller:id/permission_allow_button", "com.android.permissioncontroller:id/permission_allow_button");
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

		log.info("TC_01_Step_09: Chon phuong thuc xac thuc SMS OTP");
		mobileTopup.clickToTextID(driver, "com.VCB:id/tvptxt");
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, Text.PASSWORD);

		log.info("TC_01_Step_10: An nut 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_11: Nhap ki tu vao o nhap mat khau");
		mobileTopup.inputToDynamicSmartOTP(driver, newOtp, "com.VCB:id/otp");

		log.info("TC_01_Step_12: An tiep button 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");


		verifyEquals(mobileTopup.getDynamicTextFollowingText(driver, Text.SUCCESS_TRANSFER), UIs.LIST_UNIT_VALUE[0] + " VND");
		verifyEquals(mobileTopup.getDynamicTextByLabel(driver, Text.PHONE_TOPUP), phone);

		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_15: Click back ve man hinh chinh");
		mobileTopup.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
	}

	@Parameters({ "phone" })
	@Test
	public void TC_02_NapTheDienThoai_GiaTriMax_QuaMK( String phone) {

		log.info("TC_02_Step_01: Keo xuong va click vao phan 'Nap tien dien thoai'");
		home.clickToDynamicButtonLinkOrLinkText(driver, Home_Text_Elements.MOBILE_TOPUP);

		log.info("TC_02_Step_02: Click vào DrodownList 'Tai khoan nguon' ");
		mobileTopup.clickToTextID(driver, "com.VCB:id/number_account");

		log.info("TC_02_Step_03: Chon tai khoan nguon");

		sourceAcoount = mobileTopup.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);
		accountMoneyBefore = sourceAcoount.account;

		log.info("TC_02_Step_04: Click vao menh gia 30,000");
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, UIs.LIST_UNIT_VALUE[5]);

		log.info("TC_02_Step_05: An nut 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btn_submit");

		log.info("TC_02_Step_06: Xac nhan tai khoan nguon");
		verifyEquals(mobileTopup.getDynamicTextByLabel(driver, Text.SOURCE_ACCOUNT), accountMoneyBefore);

		log.info("TC_02_Step_07: Xac nhan so dien thoai duoc nap");
		verifyEquals(mobileTopup.getDynamicTextByLabel(driver, Text.PHONE_TOPUP), phone);

		log.info("TC_02_Step_08: Xac nhan menh gia the");
		verifyTrue(mobileTopup.getDynamicTextByLabel(driver, Text.PRICE_CARD).contains(UIs.LIST_UNIT_VALUE[5] + " VND"));

		mobileTopup.clickToTextID(driver, "com.VCB:id/tvptxt");
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, Text.PASSWORD);

		log.info("TC_02_Step_10: An nut 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_02_Step_11: Nhap ki tu vao o nhap mat khau");
		mobileTopup.inputToDynamicSmartOTP(driver, newOtp, "com.VCB:id/otp");

		log.info("TC_02_Step_12: An tiep button 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		verifyEquals(mobileTopup.getDynamicTextFollowingText(driver, Text.SUCCESS_TRANSFER), UIs.LIST_UNIT_VALUE[5] + " VND");
		verifyEquals(mobileTopup.getDynamicTextByLabel(driver, Text.PHONE_TOPUP), phone);

		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_02_Step_15: Click back ve man hinh chinh");
		mobileTopup.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
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

		log.info("TC_03_Step_09: Chon phuong thuc xac thuc SMS OTP");
		mobileTopup.clickToTextID(driver, "com.VCB:id/tvptxt");
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, Text.PASSWORD);

		log.info("TC_03_Step_10: An nut 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_03_Step_11: Nhap ki tu vao o nhap mat khau");
		mobileTopup.inputToDynamicSmartOTP(driver, newOtp, "com.VCB:id/otp");

		log.info("TC_03_Step_12: An tiep button 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		verifyEquals(mobileTopup.getDynamicTextFollowingText(driver, Text.SUCCESS_TRANSFER), UIs.LIST_UNIT_VALUE[2] + " VND");
		verifyEquals(mobileTopup.getDynamicTextByLabel(driver, Text.PHONE_TOPUP), other_Phone_Number);

		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_03_Step_15: Click back ve man hinh chinh");
		mobileTopup.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		service.stop();
	}

}
