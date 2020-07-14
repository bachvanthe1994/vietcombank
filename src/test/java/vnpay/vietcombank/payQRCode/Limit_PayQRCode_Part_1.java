package vnpay.vietcombank.payQRCode;

import java.io.IOException;
import java.net.MalformedURLException;
import java.security.GeneralSecurityException;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.Constants;
import commons.PageFactoryManager;
import commons.WebPageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import pageObjects.LogInPageObject;
import pageObjects.QRCodePageObject;
import pageObjects.WebBackendSetupPageObject;
import vietcombank_test_data.PayQRCode_Data;

public class Limit_PayQRCode_Part_1 extends Base {
	AppiumDriver<MobileElement> driver;
	private WebDriver driverWeb;
	private LogInPageObject login;
	private QRCodePageObject payQRCode;
	private WebBackendSetupPageObject setupBE;
	long money = 0;

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp", "username", "passWeb" })
	@BeforeMethod
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String otp, String username, String passWeb) throws IOException, InterruptedException, GeneralSecurityException {
		startServer();

		log.info("Before class: Mo app ");
		if (deviceType.contains("android")) {
			driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		} else if (deviceType.contains("ios")) {
			driver = openIOSApp(deviceName, udid, url);
		}
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, otp);
		
		payQRCode = PageFactoryManager.getQRCodePageObject(driver);


	}

	String account, codeOrder = "";
	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp", "username", "passWeb" })
	@Test
	public void TC_01_ThanhToanHoaDon_Type1_QRCode_NhoHon_HanMucToiThieu_TrenMotGiaoDich(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String otp, String username, String passWeb) throws MalformedURLException {
		log.info("TC_01_1_Click QR Pay");
		payQRCode.sleep(driver, 3000);
		payQRCode.scrollUpToText(driver, PayQRCode_Data.QR_PAY);
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.QR_PAY);

		log.info("TC_01_2_Click mo Thu vien anh");
		payQRCode.clickToDynamicAcceptButtonContainOR(driver, "com.android.packageinstaller:id/permission_allow_button", "com.android.permissioncontroller:id/permission_allow_button");
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.IMAGE_LIBRARY);

		log.info("TC_01_3_Click chon Tat ca anh");
		payQRCode.clickToDynamicImageButtonByContentDesc(driver, PayQRCode_Data.TEXT_BUTTON_MENU);
		payQRCode.clickToDynamicTextContains(driver, PayQRCode_Data.TEXT_CATEGORY_MENU);
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.TYPE_1);

		log.info("TC_01_5_Chon anh");
		payQRCode.clickToImageByIndex(0);
		
		money = convertAvailableBalanceCurrentcyOrFeeToLong(payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.MONEY));
		
		closeApp();
		
		log.info("TC_01_06_Mo browser va setup Han muc");
		driverWeb = openMultiBrowser(Constants.BE_BROWSER_CHROME, Constants.BE_BROWSER_VERSION, Constants.BE_URL);

		setupBE = WebPageFactoryManager.getWebBackendSetupPageObject(driverWeb);
		setupBE.Login_Web_Backend(driverWeb, username, passWeb);

		log.info("Setup assign services limit");
		setupBE.Setup_Assign_Services_Type_Limit(driverWeb, Constants.BE_CODE_PACKAGE, PayQRCode_Data.QR_PAY_TEXT, "1000000000");

		log.info("Setup assign services limit");
		setupBE.Setup_Assign_Services_Limit(driverWeb, Constants.BE_CODE_PACKAGE, PayQRCode_Data.PAY_QR_PAY_OFFLINE, money + 1 + "", "100000000", "1000000000");

		log.info("TC_01_00_Mo lai App");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		
		login = PageFactoryManager.getLoginPageObject(driver);
		payQRCode = PageFactoryManager.getQRCodePageObject(driver);
		
		login.Global_login(phone, pass, otp);
		
		log.info("TC_01_1_Click QR Pay");
		payQRCode.sleep(driver, 3000);
		payQRCode.scrollUpToText(driver, PayQRCode_Data.QR_PAY);
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.QR_PAY);
		
		log.info("TC_01_2_Click mo Thu vien anh");
		payQRCode.clickToDynamicAcceptButtonContainOR(driver, "com.android.packageinstaller:id/permission_allow_button", "com.android.permissioncontroller:id/permission_allow_button");
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.IMAGE_LIBRARY);

		log.info("TC_01_3_Click chon Tat ca anh");
		payQRCode.clickToDynamicImageButtonByContentDesc(driver, PayQRCode_Data.TEXT_BUTTON_MENU);
		payQRCode.clickToDynamicTextContains(driver, PayQRCode_Data.TEXT_CATEGORY_MENU);
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.TYPE_1);

		log.info("TC_01_5_Chon anh");
		payQRCode.clickToImageByIndex(0);

		payQRCode.clickToDynamicDropDown(driver, PayQRCode_Data.SOURCE_ACCOUNT);
		payQRCode.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		payQRCode.inputToDynamicInputBox(driver, PayQRCode_Data.MONEY_PAYMENT, PayQRCode_Data.MONEY);
		payQRCode.inputToDynamicInputBox(driver, PayQRCode_Data.NOTE_CONTENT, PayQRCode_Data.NOTE);
		payQRCode.clickToDynamicButton(driver, PayQRCode_Data.PAYMENT_BUTTON);

		log.info("TC_01_11_Kiem tra thong bao");
		verifyEquals(payQRCode.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), PayQRCode_Data.MESSAGE_MIN_LIMIT_PER_TRANS_1 + addCommasToLong((money + 1) + "") + " VND" + PayQRCode_Data.MESSAGE_MIN_LIMIT_PER_TRANS_2);

	}

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp", "username", "passWeb" })
	@Test
	public void TC_02_ThanhToanHoaDon_Type1_QRCode_LonHon_HanMucToiDa_TrenMotGiaoDich(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String otp, String username, String passWeb) throws MalformedURLException{
		log.info("TC_02_1_Click QR Pay");
		payQRCode.sleep(driver, 3000);
		payQRCode.scrollUpToText(driver, PayQRCode_Data.QR_PAY);
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.QR_PAY);

		log.info("TC_02_2_Click mo Thu vien anh");
		payQRCode.clickToDynamicAcceptButtonContainOR(driver, "com.android.packageinstaller:id/permission_allow_button", "com.android.permissioncontroller:id/permission_allow_button");
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.IMAGE_LIBRARY);

		log.info("TC_02_3_Click chon Tat ca anh");
		payQRCode.clickToDynamicImageButtonByContentDesc(driver, PayQRCode_Data.TEXT_BUTTON_MENU);
		payQRCode.clickToDynamicTextContains(driver, PayQRCode_Data.TEXT_CATEGORY_MENU);
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.TYPE_1);

		log.info("TC_02_5_Chon anh");
		payQRCode.clickToImageByIndex(0);
		
		money = convertAvailableBalanceCurrentcyOrFeeToLong(payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.MONEY));
		
		closeApp();
		
		log.info("TC_02_06_Mo browser va setup Han muc");
		driverWeb = openMultiBrowser(Constants.BE_BROWSER_CHROME, Constants.BE_BROWSER_VERSION, Constants.BE_URL);

		setupBE = WebPageFactoryManager.getWebBackendSetupPageObject(driverWeb);
		setupBE.Login_Web_Backend(driverWeb, username, passWeb);

		log.info("Setup assign services limit");
		setupBE.Setup_Assign_Services_Type_Limit(driverWeb, Constants.BE_CODE_PACKAGE, PayQRCode_Data.QR_PAY_TEXT, "1000000000");

		log.info("Setup assign services limit");
		setupBE.Setup_Assign_Services_Limit(driverWeb, Constants.BE_CODE_PACKAGE, PayQRCode_Data.PAY_QR_PAY_OFFLINE, "1000", money - 1 + "", "1000000000");

		log.info("TC_02_00_Mo lai App");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		
		login = PageFactoryManager.getLoginPageObject(driver);
		payQRCode = PageFactoryManager.getQRCodePageObject(driver);
		
		login.Global_login(phone, pass, otp);
		
		log.info("TC_02_1_Click QR Pay");
		payQRCode.sleep(driver, 3000);
		payQRCode.scrollUpToText(driver, PayQRCode_Data.QR_PAY);
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.QR_PAY);
		
		log.info("TC_02_2_Click mo Thu vien anh");
		payQRCode.clickToDynamicAcceptButtonContainOR(driver, "com.android.packageinstaller:id/permission_allow_button", "com.android.permissioncontroller:id/permission_allow_button");
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.IMAGE_LIBRARY);

		log.info("TC_02_3_Click chon Tat ca anh");
		payQRCode.clickToDynamicImageButtonByContentDesc(driver, PayQRCode_Data.TEXT_BUTTON_MENU);
		payQRCode.clickToDynamicTextContains(driver, PayQRCode_Data.TEXT_CATEGORY_MENU);
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.TYPE_1);

		log.info("TC_02_5_Chon anh");
		payQRCode.clickToImageByIndex(0);

		payQRCode.clickToDynamicDropDown(driver, PayQRCode_Data.SOURCE_ACCOUNT);
		payQRCode.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		payQRCode.inputToDynamicInputBox(driver, PayQRCode_Data.MONEY_PAYMENT, PayQRCode_Data.MONEY);
		payQRCode.inputToDynamicInputBox(driver, PayQRCode_Data.NOTE_CONTENT, PayQRCode_Data.NOTE);
		payQRCode.clickToDynamicButton(driver, PayQRCode_Data.PAYMENT_BUTTON);

		log.info("TC_02_11_Kiem tra thong bao");
		verifyEquals(payQRCode.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), PayQRCode_Data.MESSAGE_MAX_LIMIT_PER_TRANS_1 + addCommasToLong((money - 1) + "") + " VND" + PayQRCode_Data.MESSAGE_MAX_LIMIT_PER_TRANS_2);
	
	}
	
	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp", "username", "passWeb" })
	@Test
	public void TC_03_ThanhToanHoaDon_Type1_QRCode_LonHon_HanMucToiDa_NhomDichVu(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String otp, String username, String passWeb) throws MalformedURLException{
		log.info("TC_03_1_Click QR Pay");
		payQRCode.sleep(driver, 3000);
		payQRCode.scrollUpToText(driver, PayQRCode_Data.QR_PAY);
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.QR_PAY);

		log.info("TC_03_2_Click mo Thu vien anh");
		payQRCode.clickToDynamicAcceptButtonContainOR(driver, "com.android.packageinstaller:id/permission_allow_button", "com.android.permissioncontroller:id/permission_allow_button");
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.IMAGE_LIBRARY);

		log.info("TC_03_3_Click chon Tat ca anh");
		payQRCode.clickToDynamicImageButtonByContentDesc(driver, PayQRCode_Data.TEXT_BUTTON_MENU);
		payQRCode.clickToDynamicTextContains(driver, PayQRCode_Data.TEXT_CATEGORY_MENU);
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.TYPE_1);

		log.info("TC_03_5_Chon anh");
		payQRCode.clickToImageByIndex(0);
		
		money = convertAvailableBalanceCurrentcyOrFeeToLong(payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.MONEY));
		
		closeApp();
		
		log.info("TC_03_06_Mo browser va setup Han muc");
		driverWeb = openMultiBrowser(Constants.BE_BROWSER_CHROME, Constants.BE_BROWSER_VERSION, Constants.BE_URL);

		setupBE = WebPageFactoryManager.getWebBackendSetupPageObject(driverWeb);
		setupBE.Login_Web_Backend(driverWeb, username, passWeb);

		log.info("Setup assign services type limit");
		setupBE.Setup_Assign_Services_Type_Limit(driverWeb, Constants.BE_CODE_PACKAGE, PayQRCode_Data.QR_PAY_TEXT, money - 1 + "");

		log.info("TC_03_00_Mo lai App");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		
		login = PageFactoryManager.getLoginPageObject(driver);
		payQRCode = PageFactoryManager.getQRCodePageObject(driver);
		
		login.Global_login(phone, pass, otp);
		
		log.info("TC_03_1_Click QR Pay");
		payQRCode.sleep(driver, 3000);
		payQRCode.scrollUpToText(driver, PayQRCode_Data.QR_PAY);
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.QR_PAY);
		
		log.info("TC_03_2_Click mo Thu vien anh");
		payQRCode.clickToDynamicAcceptButtonContainOR(driver, "com.android.packageinstaller:id/permission_allow_button", "com.android.permissioncontroller:id/permission_allow_button");
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.IMAGE_LIBRARY);

		log.info("TC_03_3_Click chon Tat ca anh");
		payQRCode.clickToDynamicImageButtonByContentDesc(driver, PayQRCode_Data.TEXT_BUTTON_MENU);
		payQRCode.clickToDynamicTextContains(driver, PayQRCode_Data.TEXT_CATEGORY_MENU);
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.TYPE_1);

		log.info("TC_03_5_Chon anh");
		payQRCode.clickToImageByIndex(0);

		payQRCode.clickToDynamicDropDown(driver, PayQRCode_Data.SOURCE_ACCOUNT);
		payQRCode.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		payQRCode.inputToDynamicInputBox(driver, PayQRCode_Data.MONEY_PAYMENT, PayQRCode_Data.MONEY);
		payQRCode.inputToDynamicInputBox(driver, PayQRCode_Data.NOTE_CONTENT, PayQRCode_Data.NOTE);
		payQRCode.clickToDynamicButton(driver, PayQRCode_Data.PAYMENT_BUTTON);

		log.info("TC_03_11_Kiem tra thong bao");
		verifyEquals(payQRCode.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), PayQRCode_Data.MESSAGE_MAX_LIMIT_PER_GROUP_TRANS_1 + addCommasToLong((money - 1) + "") + " VND" + PayQRCode_Data.MESSAGE_MAX_LIMIT_PER_GROUP_TRANS_2);
	
	}
	
	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp", "username", "passWeb" })
	@Test
	public void TC_04_ThanhToanHoaDon_Type1_QRCode_LonHon_HanMucToiDa_NhomDichVu(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String otp, String username, String passWeb) throws MalformedURLException{
		log.info("TC_04_1_Click QR Pay");
		payQRCode.sleep(driver, 3000);
		payQRCode.scrollUpToText(driver, PayQRCode_Data.QR_PAY);
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.QR_PAY);

		log.info("TC_04_2_Click mo Thu vien anh");
		payQRCode.clickToDynamicAcceptButtonContainOR(driver, "com.android.packageinstaller:id/permission_allow_button", "com.android.permissioncontroller:id/permission_allow_button");
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.IMAGE_LIBRARY);

		log.info("TC_04_3_Click chon Tat ca anh");
		payQRCode.clickToDynamicImageButtonByContentDesc(driver, PayQRCode_Data.TEXT_BUTTON_MENU);
		payQRCode.clickToDynamicTextContains(driver, PayQRCode_Data.TEXT_CATEGORY_MENU);
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.TYPE_1);

		log.info("TC_04_5_Chon anh");
		payQRCode.clickToImageByIndex(0);
		
		money = convertAvailableBalanceCurrentcyOrFeeToLong(payQRCode.getDynamicTextInTransactionDetail(driver, PayQRCode_Data.MONEY));
		
		closeApp();
		
		log.info("TC_04_06_Mo browser va setup Han muc");
		driverWeb = openMultiBrowser(Constants.BE_BROWSER_CHROME, Constants.BE_BROWSER_VERSION, Constants.BE_URL);

		setupBE = WebPageFactoryManager.getWebBackendSetupPageObject(driverWeb);
		setupBE.Login_Web_Backend(driverWeb, username, passWeb);

		log.info("Setup add Method total limit");
		setupBE.Setup_Add_Method_Package_Total_Limit(driverWeb, Constants.BE_CODE_PACKAGE, "Method Otp", money - 1 + "");

		log.info("TC_04_00_Mo lai App");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		
		login = PageFactoryManager.getLoginPageObject(driver);
		payQRCode = PageFactoryManager.getQRCodePageObject(driver);
		
		login.Global_login(phone, pass, otp);
		
		log.info("TC_04_1_Click QR Pay");
		payQRCode.sleep(driver, 3000);
		payQRCode.scrollUpToText(driver, PayQRCode_Data.QR_PAY);
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.QR_PAY);
		
		log.info("TC_04_2_Click mo Thu vien anh");
		payQRCode.clickToDynamicAcceptButtonContainOR(driver, "com.android.packageinstaller:id/permission_allow_button", "com.android.permissioncontroller:id/permission_allow_button");
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.IMAGE_LIBRARY);

		log.info("TC_04_3_Click chon Tat ca anh");
		payQRCode.clickToDynamicImageButtonByContentDesc(driver, PayQRCode_Data.TEXT_BUTTON_MENU);
		payQRCode.clickToDynamicTextContains(driver, PayQRCode_Data.TEXT_CATEGORY_MENU);
		payQRCode.clickToDynamicButtonLinkOrLinkText(driver, PayQRCode_Data.TYPE_1);

		log.info("TC_04_5_Chon anh");
		payQRCode.clickToImageByIndex(0);

		payQRCode.clickToDynamicDropDown(driver, PayQRCode_Data.SOURCE_ACCOUNT);
		payQRCode.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		payQRCode.inputToDynamicInputBox(driver, PayQRCode_Data.MONEY_PAYMENT, PayQRCode_Data.MONEY);
		payQRCode.inputToDynamicInputBox(driver, PayQRCode_Data.NOTE_CONTENT, PayQRCode_Data.NOTE);
		payQRCode.clickToDynamicButton(driver, PayQRCode_Data.PAYMENT_BUTTON);

		log.info("TC_04_11_Kiem tra thong bao");
		verifyEquals(payQRCode.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), PayQRCode_Data.MESSAGE_MAX_LIMIT_PER_PACKAGE_TRANS_1 + addCommasToLong((money - 1) + "") + " VND" + PayQRCode_Data.MESSAGE_MAX_LIMIT_PER_PACKAGE_TRANS_2);
	
	}

	@AfterMethod(alwaysRun = true)
	public void afterClass() {
		closeApp();
		setupBE.Reset_Package_Total_Limit(driverWeb, Constants.BE_CODE_PACKAGE, "Method Otp");
		setupBE.Reset_Setup_Assign_Services_Type_Limit(driverWeb, Constants.BE_CODE_PACKAGE, PayQRCode_Data.QR_PAY_TEXT);
		setupBE.Reset_Setup_Assign_Services_Limit(driverWeb, Constants.BE_CODE_PACKAGE, PayQRCode_Data.PAY_QR_PAY_OFFLINE);
		driverWeb.quit();
		service.stop();
	}

}
