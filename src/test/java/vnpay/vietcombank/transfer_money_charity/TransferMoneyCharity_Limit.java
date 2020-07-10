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
import vietcombank_test_data.TransferMoneyInVCB_Data.InputText_MoneyRecurrent;

public class TransferMoneyCharity_Limit extends Base {
	AppiumDriver<MobileElement> driver;
	WebDriver driverWeb;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferMoneyCharityPageObject transferMoneyCharity;
	private WebBackendSetupPageObject setupBE;
	
	SourceAccountModel sourceAccount = new SourceAccountModel();
	private String higherGroup,higherPackage,organization,lowerMin,higherMax ;

	ServiceLimitInfo inputPackageGroupInfo = new ServiceLimitInfo("1000", "10000", "100000000", "150000000");
	ServiceLimitInfo inputInfo = new ServiceLimitInfo("1000", "10000", "200000100", "200000200");
	TransferCharity info = new TransferCharity("", "", "1000", "Do Minh Duc", "So 18 ngo 3 Thai Ha", "Ho ngheo", "Mật khẩu đăng nhập");
	
	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp","username","passWeb" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt,String username,String passWeb) throws IOException, InterruptedException, GeneralSecurityException {
		startServer();
		
		log.info("Before class: Mo backend ");
		driverWeb = openMultiBrowser(Constants.BE_BROWSER_CHROME, Constants.BE_BROWSER_VERSION, Constants.BE_URL);
		organization = getDataInCell(31);
		setupBE = WebPageFactoryManager.getWebBackendSetupPageObject(driverWeb);
		setupBE.Login_Web_Backend(driverWeb,username, passWeb);
		
		setupBE.Setup_Add_Method_Package_Total_Limit(driverWeb, Constants.BE_CODE_PACKAGE, Constants.METHOD_OTP,inputPackageGroupInfo.totalLimit);
		setupBE.Setup_Assign_Services_Type_Limit(driverWeb, Constants.BE_CODE_PACKAGE, InputText_MoneyRecurrent.BE_TRANSFER_TEXT, inputPackageGroupInfo.maxTran);
		setupBE.addMethodOtpLimit(driverWeb, TransferMoneyCharity_Data.BE_TRANSFER_CHARITY_TEXT);
		setupBE.addMethodServicesLimit(driverWeb, TransferMoneyCharity_Data.BE_TRANSFER_CHARITY_TEXT, inputInfo, Constants.BE_CODE_PACKAGE);
		setupBE.setupAssignServicesLimit_All(driverWeb,TransferMoneyCharity_Data.BE_TRANSFER_CHARITY_TEXT,inputInfo,Constants.BE_CODE_PACKAGE);
		setupBE.clearCacheBE(driverWeb);
		driverWeb.quit();
		
		log.info("Before class: Mo app ");
		if (deviceType.contains("android")) {
			driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		} else if (deviceType.contains("ios")) {
			driver = openIOSApp(deviceName, udid, url);
		}
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
		
		transferMoneyCharity = PageFactoryManager.getTransferMoneyCharityPageObject(driver);
		homePage = PageFactoryManager.getHomePageObject(driver);
		higherGroup = (Integer.parseInt(inputPackageGroupInfo.maxTran)+1)+"";
		higherPackage = (Integer.parseInt(inputPackageGroupInfo.totalLimit)+1)+"";
		lowerMin = (Integer.parseInt(inputInfo.minTran)-1)+"";
		higherMax = (Integer.parseInt(inputInfo.maxTran)+1)+"";

	}

	@Parameters ({"username","passWeb"})
	@Test
	public void TC_01_SoTienGiaoDich_VuotQuaHanMucToiDa_1_NgayCua_NhomDichVu(String username,String passWeb) {
		
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
		transferMoneyCharity.inputToDynamicInputBox(driver, higherGroup, TransferMoneyCharity_Data.MONEY_CHARITY);

		log.info("TC_01_5_Nhap ten nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.name, TransferMoneyCharity_Data.NAME_CHARITY);

		log.info("TC_01_6_Nhap dia chi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.address,TransferMoneyCharity_Data.ADDRESS_CHARITY);

		log.info("TC_01_7_Hoan canh nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.status, TransferMoneyCharity_Data.STATUS_CHARITY);

		driverWeb = openMultiBrowser(Constants.BE_BROWSER_CHROME, Constants.BE_BROWSER_VERSION, Constants.BE_URL);
		setupBE.Login_Web_Backend(driverWeb,username, passWeb);
		
		log.info("TC_01_8_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, TransferMoneyCharity_Data.CONTINUE_BUTTON);

		log.info("TC_01_9_verify message khi so tien chuyen nho hon han muc toi thieu ");
		verifyEquals(transferMoneyCharity.getTextDynamicFollowImage(driver, "com.VCB:id/ivTitle"), TransferMoneyCharity_Data.HIGHER_THAN_MAX_MESSAGE+addCommasToLong(inputPackageGroupInfo.maxTran)+TransferMoneyCharity_Data.DETAIL_A_DAY_GROUP_MESSAGE);
		
		setupBE.Reset_Setup_Assign_Services_Type_Limit(driverWeb,Constants.BE_CODE_PACKAGE, InputText_MoneyRecurrent.BE_TRANSFER_TEXT);
		setupBE.clearCacheBE(driverWeb);
		
		log.info("TC_01_10_Tat pop-up");
		transferMoneyCharity.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
	}

	@Test
	public void TC_02_SoTienGiaoDich_VuotQuaHanMucToiDa_1_NgayCua_GoiDichVu() {
	
		log.info("TC_02_1_Nhap so tien ung ho");
		transferMoneyCharity.inputIntoEditTextByID(driver, higherPackage, "com.VCB:id/edtContent1");

		log.info("TC_02_2_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, TransferMoneyCharity_Data.CONTINUE_BUTTON);

		log.info("TC_02_3_verify message khi so tien chuyen nho hon han muc toi thieu ");
		verifyEquals(transferMoneyCharity.getTextDynamicFollowImage(driver, "com.VCB:id/ivTitle"), TransferMoneyCharity_Data.HIGHER_THAN_MAX_MESSAGE+addCommasToLong(inputPackageGroupInfo.totalLimit)+TransferMoneyCharity_Data.DETAIL_A_DAY_PACKAGE_MESSAGE);
		
		setupBE.Reset_Package_Total_Limit(driverWeb, Constants.BE_CODE_PACKAGE, Constants.METHOD_OTP);
		driverWeb.quit();
		
		log.info("TC_02_4_Tat pop-up");
		transferMoneyCharity.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
	}

	@Test
	public void TC_03_SoTienGiaoDich_NhoHonHanMucToiThieu()  {

		log.info("TC_03_1_Nhap so tien ung ho");
		transferMoneyCharity.inputIntoEditTextByID(driver, lowerMin, "com.VCB:id/edtContent1");

		log.info("TC_03_2_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, TransferMoneyCharity_Data.CONTINUE_BUTTON);

		log.info("TC_03_3_Verify message khi so tien chuyen nho hon han muc toi thieu ");
		verifyEquals(transferMoneyCharity.getTextDynamicFollowImage(driver, "com.VCB:id/ivTitle"), TransferMoneyCharity_Data.LOWER_THAN_MIN_MESSAGE+addCommasToLong(inputInfo.minTran)+TransferMoneyCharity_Data.DETAIL_A_TRANSACTION_MESSAGE);

		transferMoneyCharity.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

	}
	

	@Test
	public void TC_04_SoTienGiaoDichVuotQuaHanMucToiDa() {

		log.info("TC_04_1_Nhap so tien ung ho");
		transferMoneyCharity.inputIntoEditTextByID(driver, higherMax, "com.VCB:id/edtContent1");

		log.info("TC_04_2_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, TransferMoneyCharity_Data.CONTINUE_BUTTON);

		log.info("TC_04_3_Verify message khi so tien chuyen lon hon han muc toi da ");
		verifyEquals(transferMoneyCharity.getTextDynamicFollowImage(driver, "com.VCB:id/ivTitle"), TransferMoneyCharity_Data.HIGHER_THAN_MAX_MESSAGE+addCommasToLong(inputInfo.maxTran)+TransferMoneyCharity_Data.DETAIL_A_TRANSACTION_MESSAGE);

		transferMoneyCharity.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

	}
	
	@Parameters({"pass","otp"})
	@Test
	public void TC_05_SoTienGiaoDichVuotQuaHanMucToiDa1Ngay(String pass,String otpNo ) {

		log.info("TC_05_04_Nhap so tien ung ho");
		transferMoneyCharity.inputIntoEditTextByID(driver, inputInfo.minTran, "com.VCB:id/edtContent1");

		log.info("TC_05_08_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, TransferMoneyCharity_Data.CONTINUE_BUTTON);
		
		log.info("TC_05_10_Chon phuong thuc xac thuc");
		transferMoneyCharity.scrollDownToText(driver, TransferMoneyCharity_Data.ACCURACY_METHOD);
		transferMoneyCharity.clickToDynamicDropDown(driver, TransferMoneyCharity_Data.ACCURACY_METHOD);
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info.authenticationMethod);

		log.info("TC_05_11_Nhap mat khau");
		transferMoneyCharity.clickToDynamicButton(driver, TransferMoneyCharity_Data.CONTINUE_BUTTON);
		transferMoneyCharity.inputToDynamicPopupPasswordInput(driver, pass, TransferMoneyCharity_Data.CONTINUE_BUTTON);
		transferMoneyCharity.clickToDynamicButton(driver, TransferMoneyCharity_Data.CONTINUE_BUTTON);

		log.info("TC_05_13_Click Thuc hien giao dich moi");
		transferMoneyCharity.clickToDynamicButton(driver, TransferMoneyCharity_Data.NEW_TRANSACTION_PERFORM);
		
		log.info("TC_05_02_Chon tai khoan nguon");
		transferMoneyCharity.clickToDynamicDropDown(driver, TransferMoneyCharity_Data.SOURCE_ACCOUNT);
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);
	
		log.info("TC_05_03_Chon Quy/ To chuc tu thien");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyCharity_Data.ORGANIRATION_CHARITY);
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, organization);

		log.info("TC_05_04_Nhap so tien ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, inputInfo.minTran, TransferMoneyCharity_Data.MONEY_CHARITY);

		log.info("TC_05_05_Nhap ten nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.name, TransferMoneyCharity_Data.NAME_CHARITY);

		log.info("TC_05_06_Nhap dia chi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.address, TransferMoneyCharity_Data.ADDRESS_CHARITY);

		log.info("TC_05_07_Hoan canh nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.status, TransferMoneyCharity_Data.STATUS_CHARITY);

		log.info("TC_05_08_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, TransferMoneyCharity_Data.CONTINUE_BUTTON);
		
		log.info("TC_05_10_Chon phuong thuc xac thuc");
		transferMoneyCharity.scrollDownToText(driver, TransferMoneyCharity_Data.ACCURACY_METHOD);
		transferMoneyCharity.clickToDynamicDropDown(driver, TransferMoneyCharity_Data.ACCURACY_METHOD);
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyCharity_Data.SMS_OTP_TEXT);

		log.info("TC_05_11_Nhap SMS OTP");
		transferMoneyCharity.clickToDynamicButton(driver, TransferMoneyCharity_Data.CONTINUE_BUTTON);
		transferMoneyCharity.inputToDynamicOtp(driver, otpNo, TransferMoneyCharity_Data.CONTINUE_BUTTON);
		transferMoneyCharity.clickToDynamicButton(driver, TransferMoneyCharity_Data.CONTINUE_BUTTON);

		log.info("TC_05_13_Click Thuc hien giao dich moi");
		transferMoneyCharity.clickToDynamicButton(driver, TransferMoneyCharity_Data.NEW_TRANSACTION_PERFORM);

		log.info("TC_05_25_Chon tai khoan nguon");
		transferMoneyCharity.clickToDynamicDropDown(driver, TransferMoneyCharity_Data.SOURCE_ACCOUNT);
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);
	
		log.info("TC_05_26_Chon Quy/ To chuc tu thien");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyCharity_Data.ORGANIRATION_CHARITY);
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, organization);

		log.info("TC_05_27_Nhap so tien ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, inputInfo.maxTran, TransferMoneyCharity_Data.MONEY_CHARITY);

		log.info("TC_05_28_Nhap ten nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.name, TransferMoneyCharity_Data.NAME_CHARITY);

		log.info("TC_05_29_Nhap dia chi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.address, TransferMoneyCharity_Data.ADDRESS_CHARITY);

		log.info("TC_05_30_Hoan canh nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.status, TransferMoneyCharity_Data.STATUS_CHARITY);

		log.info("TC_05_31_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, TransferMoneyCharity_Data.CONTINUE_BUTTON);
		
		log.info("TC_05_32_Verify message khi so tien chuyen nho hon han muc toi thieu ");
		verifyEquals(transferMoneyCharity.getTextDynamicFollowImage(driver, "com.VCB:id/ivTitle"), TransferMoneyCharity_Data.HIGHER_THAN_MAX_MESSAGE+addCommasToLong(inputInfo.totalLimit)+TransferMoneyCharity_Data.DETAIL_A_DAY_MESSAGE);

		transferMoneyCharity.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
		transferMoneyCharity.clickToDynamicBackIcon(driver, TransferMoneyCharity_Data.TRANSFER_CHARITY);
			
	}

	@Parameters ({"username","passWeb"})
	@AfterClass(alwaysRun = true)
	public void afterClass(String username,String passWeb) {
		
		driverWeb = openMultiBrowser(Constants.BE_BROWSER_CHROME, Constants.BE_BROWSER_VERSION, Constants.BE_URL);
		setupBE.Login_Web_Backend(driverWeb,username, passWeb);
		
		setupBE.resetAssignServicesLimit_All(driverWeb,TransferMoneyCharity_Data.BE_TRANSFER_CHARITY_TEXT,Constants.BE_CODE_PACKAGE);
		driverWeb.quit();
		service.stop();
	}

}
