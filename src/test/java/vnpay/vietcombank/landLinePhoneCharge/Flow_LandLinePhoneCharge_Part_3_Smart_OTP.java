package vnpay.vietcombank.landLinePhoneCharge;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import pageObjects.HomePageObject;
import pageObjects.LandLinePhoneChargePageObject;
import pageObjects.LogInPageObject;
import pageObjects.SettingVCBSmartOTPPageObject;
import pageObjects.TransactionReportPageObject;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.LandLinePhoneCharge_Data;
import vietcombank_test_data.LandLinePhoneCharge_Data.Text_Data;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransactionReport_Data.ReportTitle;

public class Flow_LandLinePhoneCharge_Part_3_Smart_OTP extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private LandLinePhoneChargePageObject landLinePhoneCharge;
	private SettingVCBSmartOTPPageObject smartOTP;
	private String otpSmart;
	long fee, money = 0;

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
		login.Global_login1(phone, pass, opt);
		smartOTP = PageFactoryManager.getSettingVCBSmartOTPPageObject(driver);
		homePage = PageFactoryManager.getHomePageObject(driver);
		landLinePhoneCharge = PageFactoryManager.getLandLinePhoneChargePageObject(driver);
		
		otpSmart = getDataInCell(6);

	}

	private long surplus, availableBalance, actualAvailableBalance;

	@Test
	public void TC_01_ThanhToanCuocDienThoaiCoDinh_KhongDay_ThanhToan_Smart_OTP() {
		
		log.info("TC_01_0_Setup smart OTP");
		smartOTP.setupSmartOTP(LogIn_Data.Login_Account.Smart_OTP, otpSmart);

		log.info("TC_01_01_Click Cuoc dien thoai co dinh");
		landLinePhoneCharge.scrollDownToText(driver, Text_Data.PRICE_CABLE);
		homePage.clickToDynamicButtonLinkOrLinkText(driver, Text_Data.LANDLINE_TELEPHONE);

		log.info("TC_01_02_Chon tai khoan nguon");
		landLinePhoneCharge.clickToDynamicDropDown(driver, Text_Data.SOURCE_ACCOUNT);
		landLinePhoneCharge.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		surplus = convertAvailableBalanceCurrentcyOrFeeToLong(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, Text_Data.AVAILABLE_BALANCES));

		log.info("TC_01_02_Chon loai cuoc thanh toan");

		landLinePhoneCharge.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/wrap_tv");
		landLinePhoneCharge.clickToDynamicButtonLinkOrLinkText(driver, Text_Data.NOT_LINE_VIETTEL);

		log.info("TC_01_04_Nhap so dien thoai tra cuoc va bam Tiep tuc");
		landLinePhoneCharge.inputPhoneNumberLandLinePhoneCharge(LandLinePhoneCharge_Data.LIST_LANDLINE_PHONE_NOLINE);

		money = convertAvailableBalanceCurrentcyOrFeeToLong(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, Text_Data.AMOUNT_PAY));
		log.info("TC_01_05_Kiem tra man hinh xac nhan thong tin");
		log.info("TC_01_05_1_Kiem tra tai khoan nguon");
		verifyEquals(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, Text_Data.SOURCE_ACCOUNT), Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_01_05_2_Kiem tra dich vu");
		verifyEquals(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, ReportTitle.SERVICE), Text_Data.LANDLINE_TELEPHONE);

		log.info("TC_01_05_3_Kiem tra nha cung cap");
		verifyEquals(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, ReportTitle.SUPPLIER), Text_Data.NOT_LINE_VIETTEL);

		log.info("TC_01_05_4_Kiem tra so dien thoai");
		verifyEquals(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, Text_Data.PHONE_NUMBER), LandLinePhoneChargePageObject.phoneNumber);

		log.info("TC_01_06_Chon phuong thuc xac thuc");
		landLinePhoneCharge.clickToDynamicButtonLinkOrLinkText(driver, Text_Data.SMART_OTP);
		landLinePhoneCharge.clickToDynamicButtonLinkOrLinkText(driver, Text_Data.SMART_OTP);

		log.info("TC_01_07_01_Kiem tra so tien phi");
		fee = convertAvailableBalanceCurrentcyOrFeeToLong(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, Text_Data.AMOUNT_FEE));

		log.info("TC_01_08_Click Tiep tuc");
		landLinePhoneCharge.clickToDynamicButton(driver, Text_Data.CONTINUE);

		log.info("TC_01_09_Nhap mat khau");
		landLinePhoneCharge.inputToDynamicSmartOtp(driver, otpSmart, Text_Data.CONTINUE);
		landLinePhoneCharge.clickToDynamicButton(driver, Text_Data.CONTINUE);

		log.info("TC_01_10_Kiem tra man hinh Chuyen khoan thanh cong");
		log.info("TC_01_10_1_Kiem tra Chuyen khoan thanh cong");
		verifyTrue(landLinePhoneCharge.isDynamicMessageAndLabelTextDisplayed(driver, LandLinePhoneCharge_Data.SUCCESS_TRANSFER_MONEY));

		log.info("TC_01_10_2_Kiem tra dich vu");
		verifyEquals(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, ReportTitle.SERVICE), Text_Data.LANDLINE_TELEPHONE);

		log.info("TC_01_10_3_Kiem tra nha cung cap");
		verifyEquals(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, ReportTitle.SUPPLIER), Text_Data.NOT_LINE_VIETTEL);

		log.info("TC_01_10_3_So dien thoai");
		verifyEquals(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, Text_Data.PHONE_NUMBER), LandLinePhoneChargePageObject.phoneNumber);

		log.info("TC_01_10_4_Kiem tra nut Thuc hien giao dich moi");
		verifyTrue(landLinePhoneCharge.isDynamicButtonDisplayed(driver, Text_Data.NEW_TRANSFER));


		log.info("TC_01_11_Click Thuc hien giao dich moi");
		landLinePhoneCharge.clickToDynamicButton(driver, Text_Data.NEW_TRANSFER);

		log.info("TC_01_11_Kiem tra so du kha dung luc sau");
		landLinePhoneCharge.clickToDynamicDropDown(driver, Text_Data.SOURCE_ACCOUNT);
		landLinePhoneCharge.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		actualAvailableBalance = convertAvailableBalanceCurrentcyOrFeeToLong(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, Text_Data.AVAILABLE_BALANCES));
		availableBalance = canculateAvailableBalances(surplus, money, fee);
		verifyEquals(actualAvailableBalance, availableBalance);

	}

	@Parameters({"otp"})
	@Test
	public void TC_02_ThanhToanCuocDienThoaiCoDinh_CoDinhCoDay_ThanhToan_Smart_OTP(String otp) {
		homePage = PageFactoryManager.getHomePageObject(driver);
		landLinePhoneCharge = PageFactoryManager.getLandLinePhoneChargePageObject(driver);

		log.info("TC_02_01_Click Cuoc dien thoai co dinh");
		landLinePhoneCharge.scrollDownToText(driver, Text_Data.PRICE_CABLE);
		landLinePhoneCharge.clickToDynamicButtonLinkOrLinkText(driver, Text_Data.LANDLINE_TELEPHONE);

		log.info("TC_02_02_Chon tai khoan nguon");
		landLinePhoneCharge.clickToDynamicDropDown(driver, Text_Data.SOURCE_ACCOUNT);
		landLinePhoneCharge.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		surplus = convertAvailableBalanceCurrentcyOrFeeToLong(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, Text_Data.AVAILABLE_BALANCES));

		log.info("TC_02_03_Chon loai cuoc thanh toan");
		landLinePhoneCharge.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/wrap_tv");
		landLinePhoneCharge.clickToDynamicButtonLinkOrLinkText(driver, Text_Data.LINE_VIETTEL);

		log.info("TC_02_04_Nhap so dien thoai tra cuoc va bam Tiep tuc");
		landLinePhoneCharge.inputPhoneNumberLandLinePhoneCharge(LandLinePhoneCharge_Data.LIST_LANDLINE_PHONE_LINE);

		money = convertAvailableBalanceCurrentcyOrFeeToLong(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, Text_Data.AMOUNT_PAY));
		log.info("TC_02_05_Kiem tra man hinh xac nhan thong tin");
		log.info("TC_02_05_1_Kiem tra tai khoan nguon");
		verifyEquals(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, Text_Data.SOURCE_ACCOUNT), Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_02_05_2_Kiem tra dich vu");
		verifyEquals(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, Text_Data.SERVICE), Text_Data.LANDLINE_TELEPHONE);

		log.info("TC_02_05_3_Kiem tra nha cung cap");
		verifyEquals(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, Text_Data.SUPPLIER), Text_Data.LINE_VIETTEL);

		log.info("TC_02_05_4_Kiem tra so dien thoai");
		verifyEquals(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, Text_Data.PHONE_NUMBER), LandLinePhoneChargePageObject.phoneNumber);

		log.info("TC_02_06_Chon phuong thuc xac thuc");
		landLinePhoneCharge.clickToDynamicButtonLinkOrLinkText(driver, Text_Data.SMART_OTP);
		landLinePhoneCharge.clickToDynamicButtonLinkOrLinkText(driver, Text_Data.SMART_OTP);

		log.info("TC_02_07_01_Kiem tra so tien phi");
		fee = convertAvailableBalanceCurrentcyOrFeeToLong(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, Text_Data.AMOUNT_FEE));

		log.info("TC_02_08_Click Tiep tuc");
		landLinePhoneCharge.clickToDynamicButton(driver, Text_Data.CONTINUE);

		log.info("TC_02_09_Nhap OTP");
		landLinePhoneCharge.inputToDynamicSmartOTP(driver, otpSmart, Text_Data.CONTINUE);
		landLinePhoneCharge.clickToDynamicButton(driver, Text_Data.CONTINUE);

		log.info("TC_02_10_Kiem tra man hinh Chuyen khoan thanh cong");
		log.info("TC_02_10_1_Kiem tra Chuyen khoan thanh cong");
		verifyTrue(landLinePhoneCharge.isDynamicMessageAndLabelTextDisplayed(driver, LandLinePhoneCharge_Data.SUCCESS_TRANSFER_MONEY));

		log.info("TC_02_10_2_Kiem tra dich vu");
		verifyEquals(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, Text_Data.SERVICE), Text_Data.LANDLINE_TELEPHONE);

		log.info("TC_02_10_3_Kiem tra nha cung cap");
		verifyEquals(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, Text_Data.SUPPLIER), Text_Data.LINE_VIETTEL);

		log.info("TC_02_10_3_So dien thoai");
		verifyEquals(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, Text_Data.PHONE_NUMBER), LandLinePhoneChargePageObject.phoneNumber);

		log.info("TC_02_10_4_Kiem tra nut Thuc hien giao dich moi");
		verifyTrue(landLinePhoneCharge.isDynamicButtonDisplayed(driver, Text_Data.NEW_TRANSFER));


		log.info("TC_02_11_Click Thuc hien giao dich moi");
		landLinePhoneCharge.clickToDynamicButton(driver, Text_Data.NEW_TRANSFER);

		log.info("TC_02_11_Kiem tra so du kha dung luc sau");
		landLinePhoneCharge.clickToDynamicDropDown(driver, Text_Data.SOURCE_ACCOUNT);
		landLinePhoneCharge.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		actualAvailableBalance = convertAvailableBalanceCurrentcyOrFeeToLong(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, Text_Data.AVAILABLE_BALANCES));
		availableBalance = canculateAvailableBalances(surplus, money, fee);
		verifyEquals(actualAvailableBalance, availableBalance);

	}

	

	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
