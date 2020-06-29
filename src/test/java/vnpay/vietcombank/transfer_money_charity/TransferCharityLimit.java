package vnpay.vietcombank.transfer_money_charity;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.Constants;
import commons.PageFactoryManager;
import commons.WebPageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import model.ServiceLimitInfo;
import model.SourceAccountModel;
import model.TransferCharity;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.TransferMoneyCharityPageObject;
import pageObjects.WebBackendSetupPageObject;
import vietcombank_test_data.TransferMoneyCharity_Data;

public class TransferCharityLimit extends Base {
	AppiumDriver<MobileElement> driver;
	WebDriver driverWeb;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferMoneyCharityPageObject transferMoneyCharity;
	private WebBackendSetupPageObject setupBE;
	
	SourceAccountModel sourceAccount = new SourceAccountModel();
	private String organization,lowerMin,higherMax,higherTotal,userNameBE,passwordBE,otpNo ;

	ServiceLimitInfo inputInfo = new ServiceLimitInfo("1000", "10000", "1000000", "2500001");
	TransferCharity info = new TransferCharity("", "", "1000", "Do Minh Duc", "So 18 ngo 3 Thai Ha", "Ho ngheo", "Mật khẩu đăng nhập");
	
	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp","username","passWeb" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt,String username,String passWeb) throws IOException, InterruptedException, GeneralSecurityException {
		startServer();
		
		log.info("Before class: Mo backend ");
		driverWeb = openMultiBrowser(Constants.BE_BROWSER_CHROME, Constants.BE_BROWSER_VERSION, Constants.BE_URL);
		
		setupBE = WebPageFactoryManager.getWebBackendSetupPageObject(driver);
		setupBE.Login_Web_Backend(driverWeb,username, passWeb);
		userNameBE = username;
		passwordBE = passWeb;
		setupBE.setupAssignServicesLimit(driverWeb,TransferMoneyCharity_Data.BE_TRANSFER_CHARITY_TEXT,inputInfo);
		log.info("Before class: Mo app ");
		if (deviceType.contains("android")) {
			driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		} else if (deviceType.contains("ios")) {
			driver = openIOSApp(deviceName, udid, url);
		}
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
		otpNo = opt;
		organization = getDataInCell(31);
		lowerMin = (Integer.parseInt(inputInfo.minTran)-1)+"";
		higherMax = (Integer.parseInt(inputInfo.maxTran)+1)+"";
		higherTotal = (Integer.parseInt(inputInfo.totalLimit)+1)+"";
		
		transferMoneyCharity = PageFactoryManager.getTransferMoneyCharityPageObject(driver);
		homePage = PageFactoryManager.getHomePageObject(driver);

	}

	@Test
	public void TC_01_SoTienGiaoDichNhoHonHanMucToiThieu() throws InterruptedException {
		
		log.info("TC_01_1_Click Chuyen tien tu thien");
		transferMoneyCharity.scrollDownToText(driver, TransferMoneyCharity_Data.STATUS_TRANSFER_MONEY);
		homePage.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyCharity_Data.TRANSFER_CHARITY);

		log.info("TC_01_2_Chon tai khoan nguon");
		transferMoneyCharity.clickToDynamicDropDown(driver, TransferMoneyCharity_Data.SOURCE_ACCOUNT);
		sourceAccount = transferMoneyCharity.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		log.info("TC_01_3_Chon Quy/ To chuc tu thien");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyCharity_Data.ORGANIRATION_CHARITY);
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, organization);

		log.info("TC_01_4_Nhap so tien ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, lowerMin, TransferMoneyCharity_Data.MONEY_CHARITY);

		log.info("TC_01_5_Nhap ten nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.name, TransferMoneyCharity_Data.NAME_CHARITY);

		log.info("TC_01_6_Nhap dia chi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.address,TransferMoneyCharity_Data.ADDRESS_CHARITY);

		log.info("TC_01_7_Hoan canh nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.status, TransferMoneyCharity_Data.STATUS_CHARITY);

		log.info("TC_01_8_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, TransferMoneyCharity_Data.CONTINUE_BUTTON);

		log.info("TC_01_9_Verify message khi so tien chuyen nho hon han muc toi thieu ");
		verifyEquals(transferMoneyCharity.getTextDynamicFollowImage(driver, "com.VCB:id/ivTitle"), TransferMoneyCharity_Data.LOWER_THAN_MIN_MESSAGE+addCommasToLong(inputInfo.minTran)+TransferMoneyCharity_Data.DETAIL_A_TRANSACTION_MESSAGE);

		transferMoneyCharity.clickToDynamicContinue(driver, "com.VCB:id/btOK");

	}

	@Test
	public void TC_02_SoTienGiaoDichVuotQuaHanMucToiDa() throws InterruptedException {

		log.info("TC_02_1_Nhap so tien ung ho");
		transferMoneyCharity.inputIntoEditTextByID(driver, higherMax, "com.VCB:id/edtContent1");

		log.info("TC_02_2_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, TransferMoneyCharity_Data.CONTINUE_BUTTON);

		log.info("TC_02_3_Verify message khi so tien chuyen lon hon han muc toi da ");
		verifyEquals(transferMoneyCharity.getTextDynamicFollowImage(driver, "com.VCB:id/ivTitle"), TransferMoneyCharity_Data.HIGHER_THAN_MAX_MESSAGE+addCommasToLong(inputInfo.maxTran)+TransferMoneyCharity_Data.DETAIL_A_TRANSACTION_MESSAGE);

		transferMoneyCharity.clickToDynamicContinue(driver, "com.VCB:id/btOK");
		transferMoneyCharity.clickToDynamicBackIcon(driver, TransferMoneyCharity_Data.TRANSFER_CHARITY);

	}
	
	@Test
	public void TC_03_ResetHanMucMinMax_Va_SuaHanMucNhom_GoiDichVu() {
		
		driverWeb = openMultiBrowser(Constants.BE_BROWSER_CHROME, Constants.BE_BROWSER_VERSION, Constants.BE_URL);
		setupBE.Login_Web_Backend(driverWeb,userNameBE, passwordBE);
		setupBE.resetAssignServicesLimit(driverWeb,TransferMoneyCharity_Data.BE_TRANSFER_CHARITY_TEXT);
		
		driverWeb = openMultiBrowser(Constants.BE_BROWSER_CHROME, Constants.BE_BROWSER_VERSION, Constants.BE_URL);
		setupBE.Login_Web_Backend(driverWeb,userNameBE, passwordBE);
		setupBE.Setup_Add_Method_Package_Total_Limit(driverWeb, "PKG1", "Method Otp");
		
	}

	@Test
	public void TC_04_SoTienGiaoDichVuotQuaHanMucToiDa1NgayCuaNhomDichVu() {
		
		log.info("TC_04_2_Chon tai khoan nguon");
		transferMoneyCharity.clickToDynamicDropDown(driver, TransferMoneyCharity_Data.SOURCE_ACCOUNT);
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_04_3_Chon Quy/ To chuc tu thien");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyCharity_Data.ORGANIRATION_CHARITY);
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info.organization);

		log.info("TC_04_4_Nhap so tien ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, higherTotal, TransferMoneyCharity_Data.MONEY_CHARITY);

		log.info("TC_04_5_Nhap ten nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.name, TransferMoneyCharity_Data.NAME_CHARITY);

		log.info("TC_04_6_Nhap dia chi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.address,TransferMoneyCharity_Data.ADDRESS_CHARITY);

		log.info("TC_04_7_Hoan canh nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.status, TransferMoneyCharity_Data.STATUS_CHARITY);

		log.info("TC_04_8_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, TransferMoneyCharity_Data.CONTINUE_BUTTON);

		log.info("TC_04_Step_verify message khi so tien chuyen nho hon han muc toi thieu ");
		verifyEquals(transferMoneyCharity.getTextDynamicFollowImage(driver, "com.VCB:id/ivTitle"), TransferMoneyCharity_Data.HIGHER_THAN_MAX_MESSAGE+addCommasToLong(inputInfo.totalLimit)+TransferMoneyCharity_Data.DETAIL_A_DAY_GROUP_MESSAGE);


	}

	@Test
	public void TC_05_SoTienGiaoDichVuotQuaHanMucToiDa1NgayCuaGoiDichVu() {
	
		log.info("TC_05_1_Click Chuyen tien tu thien");
		transferMoneyCharity.scrollDownToText(driver, TransferMoneyCharity_Data.STATUS_TRANSFER_MONEY);
		homePage.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyCharity_Data.TRANSFER_CHARITY);

		log.info("TC_05_2_Chon tai khoan nguon");
		transferMoneyCharity.clickToDynamicDropDown(driver, TransferMoneyCharity_Data.SOURCE_ACCOUNT);
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_05_3_Chon Quy/ To chuc tu thien");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyCharity_Data.ORGANIRATION_CHARITY);
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info.organization);

		log.info("TC_05_4_Nhap so tien ung ho");
		String higherMoney = (Integer.parseInt(Constants.AMOUNT_DEFAULT_MIN_PACKAGE)+1)+"";
		transferMoneyCharity.inputToDynamicInputBox(driver, higherMoney, TransferMoneyCharity_Data.MONEY_CHARITY);

		log.info("TC_05_5_Nhap ten nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.name, TransferMoneyCharity_Data.NAME_CHARITY);

		log.info("TC_05_6_Nhap dia chi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.address,TransferMoneyCharity_Data.ADDRESS_CHARITY);

		log.info("TC_05_7_Hoan canh nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.status, TransferMoneyCharity_Data.STATUS_CHARITY);

		log.info("TC_05_8_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, TransferMoneyCharity_Data.CONTINUE_BUTTON);

		log.info("TC_05_Step_verify message khi so tien chuyen nho hon han muc toi thieu ");
		verifyEquals(transferMoneyCharity.getTextDynamicFollowImage(driver, "com.VCB:id/ivTitle"), TransferMoneyCharity_Data.HIGHER_THAN_MAX_MESSAGE+addCommasToLong(Constants.AMOUNT_DEFAULT_MIN_PACKAGE)+TransferMoneyCharity_Data.DETAIL_A_DAY_GROUP_MESSAGE);

		driverWeb = openMultiBrowser(Constants.BE_BROWSER_CHROME, Constants.BE_BROWSER_VERSION, Constants.BE_URL);
		setupBE.Login_Web_Backend(driverWeb,userNameBE, passwordBE);
		setupBE.Reset_Package_Total_Limit(driverWeb, "PKG1", "Method Otp");
	}
	
	@Parameters({"pass"})
	@Test
	public void TC_06_SoTienGiaoDichVuotQuaHanMucToiDa1Ngay(String pass) throws InterruptedException {

		setupBE.Login_Web_Backend(driverWeb,userNameBE, passwordBE);
		setupBE.setupAssignServicesLimit(driverWeb,TransferMoneyCharity_Data.BE_TRANSFER_CHARITY_TEXT,inputInfo);
		
		log.info("TC_06_01_Click Chuyen tien tu thien");
		transferMoneyCharity.scrollDownToText(driver, TransferMoneyCharity_Data.STATUS_TRANSFER_MONEY);
		homePage.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyCharity_Data.TRANSFER_CHARITY);

		log.info("TC_06_02_Chon tai khoan nguon");
		transferMoneyCharity.clickToDynamicDropDown(driver, TransferMoneyCharity_Data.SOURCE_ACCOUNT);
		sourceAccount = transferMoneyCharity.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);
	
		log.info("TC_06_03_Chon Quy/ To chuc tu thien");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyCharity_Data.ORGANIRATION_CHARITY);
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, organization);

		log.info("TC_06_04_Nhap so tien ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, inputInfo.maxTran, TransferMoneyCharity_Data.MONEY_CHARITY);

		log.info("TC_06_05_Nhap ten nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.name, TransferMoneyCharity_Data.NAME_CHARITY);

		log.info("TC_06_06_Nhap dia chi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.address, TransferMoneyCharity_Data.ADDRESS_CHARITY);

		log.info("TC_06_07_Hoan canh nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.status, TransferMoneyCharity_Data.STATUS_CHARITY);

		log.info("TC_06_08_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, TransferMoneyCharity_Data.CONTINUE_BUTTON);
		
		log.info("TC_06_10_Chon phuong thuc xac thuc");
		transferMoneyCharity.scrollDownToText(driver, TransferMoneyCharity_Data.ACCURACY_METHOD);
		transferMoneyCharity.clickToDynamicDropDown(driver, TransferMoneyCharity_Data.ACCURACY_METHOD);
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info.authenticationMethod);

		log.info("TC_06_11_Nhap mat khau");
		transferMoneyCharity.clickToDynamicButton(driver, TransferMoneyCharity_Data.CONTINUE_BUTTON);
		transferMoneyCharity.inputToDynamicPopupPasswordInput(driver, pass, TransferMoneyCharity_Data.CONTINUE_BUTTON);
		transferMoneyCharity.clickToDynamicButton(driver, TransferMoneyCharity_Data.CONTINUE_BUTTON);

		log.info("TC_06_12_Kiem tra nut Thuc hien giao dich moi");
		verifyTrue(transferMoneyCharity.isDynamicButtonDisplayed(driver, TransferMoneyCharity_Data.NEW_TRANSACTION_PERFORM));

		log.info("TC_06_13_Click Thuc hien giao dich moi");
		transferMoneyCharity.clickToDynamicButton(driver, TransferMoneyCharity_Data.NEW_TRANSACTION_PERFORM);
		
		log.info("TC_06_14_Chon tai khoan nguon");
		transferMoneyCharity.clickToDynamicDropDown(driver, TransferMoneyCharity_Data.SOURCE_ACCOUNT);
		sourceAccount = transferMoneyCharity.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);
	
		log.info("TC_06_15_Chon Quy/ To chuc tu thien");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyCharity_Data.ORGANIRATION_CHARITY);
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, organization);

		log.info("TC_06_16_Nhap so tien ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, inputInfo.maxTran, TransferMoneyCharity_Data.MONEY_CHARITY);

		log.info("TC_06_17_Nhap ten nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.name, TransferMoneyCharity_Data.NAME_CHARITY);

		log.info("TC_06_18_Nhap dia chi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.address, TransferMoneyCharity_Data.ADDRESS_CHARITY);

		log.info("TC_06_19_Hoan canh nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.status, TransferMoneyCharity_Data.STATUS_CHARITY);

		log.info("TC_06_20_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, TransferMoneyCharity_Data.CONTINUE_BUTTON);
		
		log.info("TC_06_21_Chon phuong thuc xac thuc");
		transferMoneyCharity.scrollDownToText(driver, TransferMoneyCharity_Data.ACCURACY_METHOD);
		transferMoneyCharity.clickToDynamicDropDown(driver, TransferMoneyCharity_Data.ACCURACY_METHOD);
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyCharity_Data.SMS_OTP_TEXT);

		log.info("TC_06_22_Nhap SMS OTP");
		transferMoneyCharity.clickToDynamicButton(driver, TransferMoneyCharity_Data.CONTINUE_BUTTON);
		transferMoneyCharity.inputToDynamicOtp(driver, otpNo, TransferMoneyCharity_Data.CONTINUE_BUTTON);
		transferMoneyCharity.clickToDynamicButton(driver, TransferMoneyCharity_Data.CONTINUE_BUTTON);

		log.info("TC_06_23_Kiem tra nut Thuc hien giao dich moi");
		verifyTrue(transferMoneyCharity.isDynamicButtonDisplayed(driver, TransferMoneyCharity_Data.NEW_TRANSACTION_PERFORM));

		log.info("TC_06_24_Click Thuc hien giao dich moi");
		transferMoneyCharity.clickToDynamicButton(driver, TransferMoneyCharity_Data.NEW_TRANSACTION_PERFORM);

		log.info("TC_06_25_Chon tai khoan nguon");
		transferMoneyCharity.clickToDynamicDropDown(driver, TransferMoneyCharity_Data.SOURCE_ACCOUNT);
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);
	
		log.info("TC_06_26_Chon Quy/ To chuc tu thien");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyCharity_Data.ORGANIRATION_CHARITY);
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, organization);

		log.info("TC_06_27_Nhap so tien ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, inputInfo.maxTran, TransferMoneyCharity_Data.MONEY_CHARITY);

		log.info("TC_06_28_Nhap ten nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.name, TransferMoneyCharity_Data.NAME_CHARITY);

		log.info("TC_06_29_Nhap dia chi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.address, TransferMoneyCharity_Data.ADDRESS_CHARITY);

		log.info("TC_06_30_Hoan canh nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.status, TransferMoneyCharity_Data.STATUS_CHARITY);

		log.info("TC_06_31_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, TransferMoneyCharity_Data.CONTINUE_BUTTON);
		
		log.info("TC_06_32_Verify message khi so tien chuyen nho hon han muc toi thieu ");
		verifyEquals(transferMoneyCharity.getTextDynamicFollowImage(driver, "com.VCB:id/ivTitle"), TransferMoneyCharity_Data.HIGHER_THAN_MAX_MESSAGE+addCommasToLong(inputInfo.totalLimit)+TransferMoneyCharity_Data.DETAIL_A_DAY_MESSAGE);

		transferMoneyCharity.clickToDynamicContinue(driver, "com.VCB:id/btOK");
		transferMoneyCharity.clickToDynamicBackIcon(driver, TransferMoneyCharity_Data.TRANSFER_CHARITY);
		
		
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		driverWeb = openMultiBrowser(Constants.BE_BROWSER_CHROME, Constants.BE_BROWSER_VERSION, Constants.BE_URL);
		setupBE.Login_Web_Backend(driverWeb,userNameBE, passwordBE);
		setupBE.resetAssignServicesLimit(driverWeb,TransferMoneyCharity_Data.BE_TRANSFER_CHARITY_TEXT);
		service.stop();
	}

}
