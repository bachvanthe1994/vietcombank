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
	private String organization,lowerMin,higherMax,userNameBE,passwordBE ;

	ServiceLimitInfo inputInfo = new ServiceLimitInfo("1000", "10000", "1000000", "1500000");
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
		organization = getDataInCell(31);
		lowerMin = (Integer.parseInt(inputInfo.minTran)-1)+"";
		higherMax = (Integer.parseInt(inputInfo.maxTran)+1)+"";
		
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

	@Parameters({"pass"})
	@Test
	public void TC_03_SoTienGiaoDichVuotQuaHanMucToiDa1Ngay(String pass) throws InterruptedException {

		log.info("TC_03_1_Click Chuyen tien tu thien");
		transferMoneyCharity.scrollDownToText(driver, TransferMoneyCharity_Data.STATUS_TRANSFER_MONEY);
		homePage.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyCharity_Data.TRANSFER_CHARITY);

		log.info("TC_03_2_Chon tai khoan nguon");
		transferMoneyCharity.clickToDynamicDropDown(driver, TransferMoneyCharity_Data.SOURCE_ACCOUNT);
		sourceAccount = transferMoneyCharity.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);
	
		log.info("TC_03_3_Chon Quy/ To chuc tu thien");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyCharity_Data.ORGANIRATION_CHARITY);
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, organization);

		log.info("TC_03_4_Nhap so tien ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, inputInfo.maxTran, TransferMoneyCharity_Data.MONEY_CHARITY);

		log.info("TC_03_5_Nhap ten nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.name, TransferMoneyCharity_Data.NAME_CHARITY);

		log.info("TC_03_6_Nhap dia chi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.address, TransferMoneyCharity_Data.ADDRESS_CHARITY);

		log.info("TC_03_7_Hoan canh nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.status, TransferMoneyCharity_Data.STATUS_CHARITY);

		log.info("TC_03_8_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, TransferMoneyCharity_Data.CONTINUE_BUTTON);
		
		log.info("TC_03_10_Chon phuong thuc xac thuc");
		transferMoneyCharity.scrollDownToText(driver, TransferMoneyCharity_Data.ACCURACY_METHOD);
		transferMoneyCharity.clickToDynamicDropDown(driver, TransferMoneyCharity_Data.ACCURACY_METHOD);
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info.authenticationMethod);

		log.info("TC_03_11_Nhap mat khau");
		transferMoneyCharity.clickToDynamicButton(driver, TransferMoneyCharity_Data.CONTINUE_BUTTON);
		transferMoneyCharity.inputToDynamicPopupPasswordInput(driver, pass, TransferMoneyCharity_Data.CONTINUE_BUTTON);
		transferMoneyCharity.clickToDynamicButton(driver, TransferMoneyCharity_Data.CONTINUE_BUTTON);

		log.info("TC_03_12_Kiem tra nut Thuc hien giao dich moi");
		verifyTrue(transferMoneyCharity.isDynamicButtonDisplayed(driver, TransferMoneyCharity_Data.NEW_TRANSACTION_PERFORM));

		log.info("TC_03_13_Click Thuc hien giao dich moi");
		transferMoneyCharity.clickToDynamicButton(driver, TransferMoneyCharity_Data.NEW_TRANSACTION_PERFORM);

		log.info("TC_03_14_Chon tai khoan nguon");
		transferMoneyCharity.clickToDynamicDropDown(driver, TransferMoneyCharity_Data.SOURCE_ACCOUNT);
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);
	
		log.info("TC_03_15_Chon Quy/ To chuc tu thien");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyCharity_Data.ORGANIRATION_CHARITY);
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, organization);

		log.info("TC_03_16_Nhap so tien ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, inputInfo.maxTran, TransferMoneyCharity_Data.MONEY_CHARITY);

		log.info("TC_03_17_Nhap ten nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.name, TransferMoneyCharity_Data.NAME_CHARITY);

		log.info("TC_03_18_Nhap dia chi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.address, TransferMoneyCharity_Data.ADDRESS_CHARITY);

		log.info("TC_03_19_Hoan canh nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.status, TransferMoneyCharity_Data.STATUS_CHARITY);

		log.info("TC_03_20_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, TransferMoneyCharity_Data.CONTINUE_BUTTON);
		
		log.info("TC_03_21_Verify message khi so tien chuyen nho hon han muc toi thieu ");
		verifyEquals(transferMoneyCharity.getTextDynamicFollowImage(driver, "com.VCB:id/ivTitle"), TransferMoneyCharity_Data.HIGHER_THAN_MAX_MESSAGE+addCommasToLong(inputInfo.totalLimit)+TransferMoneyCharity_Data.DETAIL_A_DAY_MESSAGE);

		transferMoneyCharity.clickToDynamicContinue(driver, "com.VCB:id/btOK");
		transferMoneyCharity.clickToDynamicBackIcon(driver, TransferMoneyCharity_Data.TRANSFER_CHARITY);
		
		driverWeb = openMultiBrowser(Constants.BE_BROWSER_CHROME, Constants.BE_BROWSER_VERSION, Constants.BE_URL);
		setupBE.Login_Web_Backend(driverWeb,userNameBE, passwordBE);
		setupBE.resetAssignServicesLimit(driverWeb,TransferMoneyCharity_Data.BE_TRANSFER_CHARITY_TEXT);
	}

//	@Test
	public void TC_04_SoTienGiaoDichVuotQuaHanMucToiDa1NgayCuaNhomDichVu() {
		
		log.info("TC_04_2_Chon tai khoan nguon");
		transferMoneyCharity.clickToDynamicDropDown(driver, TransferMoneyCharity_Data.SOURCE_ACCOUNT);
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_04_3_Chon Quy/ To chuc tu thien");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyCharity_Data.ORGANIRATION_CHARITY);
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info.organization);

		log.info("TC_04_4_Nhap so tien ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.money, TransferMoneyCharity_Data.MONEY_CHARITY);

		log.info("TC_04_5_Nhap ten nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.name, TransferMoneyCharity_Data.NAME_CHARITY);

		log.info("TC_04_6_Nhap dia chi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.address,TransferMoneyCharity_Data.ADDRESS_CHARITY);

		log.info("TC_04_7_Hoan canh nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.status, TransferMoneyCharity_Data.STATUS_CHARITY);

		log.info("TC_04_8_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, TransferMoneyCharity_Data.CONTINUE_BUTTON);

		log.info("TC_04_Step_verify message khi so tien chuyen nho hon han muc toi thieu ");
		verifyTrue(transferMoneyCharity.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyCharity_Data.CONFIRM_MAX_TRANSECTION_LIMIT_DAY_GROUP));

	}

//	@Test
	public void TC_05_SoTienGiaoDichVuotQuaHanMucToiDa1NgayCuaGoiDichVu() {
	
		log.info("TC_05_1_Click Chuyen tien tu thien");
		transferMoneyCharity.scrollDownToText(driver, "Trạng thái lệnh chuyển tiền");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyCharity_Data.TRANSFER_CHARITY);

		log.info("TC_05_2_Chon tai khoan nguon");
		transferMoneyCharity.clickToDynamicDropDown(driver, TransferMoneyCharity_Data.SOURCE_ACCOUNT);
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_05_3_Chon Quy/ To chuc tu thien");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyCharity_Data.ORGANIRATION_CHARITY);
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info.organization);

		log.info("TC_05_4_Nhap so tien ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.money, TransferMoneyCharity_Data.MONEY_CHARITY);

		log.info("TC_05_5_Nhap ten nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.name, TransferMoneyCharity_Data.NAME_CHARITY);

		log.info("TC_05_6_Nhap dia chi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.address,TransferMoneyCharity_Data.ADDRESS_CHARITY);

		log.info("TC_05_7_Hoan canh nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.status, TransferMoneyCharity_Data.STATUS_CHARITY);

		log.info("TC_05_8_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, TransferMoneyCharity_Data.CONTINUE_BUTTON);

		log.info("TC_05_Step_verify message khi so tien chuyen nho hon han muc toi thieu ");
		verifyTrue(transferMoneyCharity.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyCharity_Data.CONFIRM_MAX_TRANSECTION_LIMIT_DAY_PACKAGE));

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();
	}

}
