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

public class TransferMoneyCharity_Limit_01 extends Base {
	AppiumDriver<MobileElement> driver;
	WebDriver driverWeb;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferMoneyCharityPageObject transferMoneyCharity;
	private WebBackendSetupPageObject setupBE;
	
	SourceAccountModel sourceAccount = new SourceAccountModel();
	private String higherTotal,organization ;

	ServiceLimitInfo inputInfo = new ServiceLimitInfo("1000", "10000", "20000", "30000");
	TransferCharity info = new TransferCharity("", "", "1000", "Do Minh Duc", "So 18 ngo 3 Thai Ha", "Ho ngheo", "Mật khẩu đăng nhập");
	
	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp","username","passWeb" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt,String username,String passWeb) throws IOException, InterruptedException, GeneralSecurityException {
		startServer();
		
		log.info("Before class: Mo backend ");
		driverWeb = openMultiBrowser(Constants.BE_BROWSER_CHROME, Constants.BE_BROWSER_VERSION, Constants.BE_URL);
		
		setupBE = WebPageFactoryManager.getWebBackendSetupPageObject(driverWeb);
		setupBE.Login_Web_Backend(driverWeb,username, passWeb);
		
		setupBE.Setup_Add_Method_Package_Total_Limit(driverWeb, Constants.BE_CODE_PACKAGE, "Method Otp",inputInfo.totalLimit);
		setupBE.Setup_Assign_Services_Type_Limit(driverWeb, Constants.BE_CODE_PACKAGE, InputText_MoneyRecurrent.BE_TRANSFER_TEXT, inputInfo.maxTran);
		setupBE.clearCacheBE(driverWeb);
		
		organization = getDataInCell(31);
		log.info("Before class: Mo app ");
		if (deviceType.contains("android")) {
			driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		} else if (deviceType.contains("ios")) {
			driver = openIOSApp(deviceName, udid, url);
		}
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
		higherTotal = (Integer.parseInt(inputInfo.maxTran)+1)+"";
		
		transferMoneyCharity = PageFactoryManager.getTransferMoneyCharityPageObject(driver);
		homePage = PageFactoryManager.getHomePageObject(driver);

	}

	@Test
	public void TC_01_SoTienGiaoDichVuotQuaHanMucToiDa1NgayCuaNhomDichVu() {
		log.info("TC_02_1_Click Chuyen tien tu thien");
		transferMoneyCharity.scrollDownToText(driver, TransferMoneyCharity_Data.STATUS_TRANSFER_MONEY);
		homePage.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyCharity_Data.TRANSFER_CHARITY);
		
		log.info("TC_01_2_Chon tai khoan nguon");
		transferMoneyCharity.clickToDynamicDropDown(driver, TransferMoneyCharity_Data.SOURCE_ACCOUNT);
		sourceAccount = transferMoneyCharity.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		log.info("TC_01_3_Chon Quy/ To chuc tu thien");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyCharity_Data.ORGANIRATION_CHARITY);
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, organization);

		log.info("TC_01_4_Nhap so tien ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, higherTotal, TransferMoneyCharity_Data.MONEY_CHARITY);

		log.info("TC_01_5_Nhap ten nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.name, TransferMoneyCharity_Data.NAME_CHARITY);

		log.info("TC_01_6_Nhap dia chi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.address,TransferMoneyCharity_Data.ADDRESS_CHARITY);

		log.info("TC_01_7_Hoan canh nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.status, TransferMoneyCharity_Data.STATUS_CHARITY);

		log.info("TC_01_8_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, TransferMoneyCharity_Data.CONTINUE_BUTTON);

		log.info("TC_01_Step_verify message khi so tien chuyen nho hon han muc toi thieu ");
		verifyEquals(transferMoneyCharity.getTextDynamicFollowImage(driver, "com.VCB:id/ivTitle"), TransferMoneyCharity_Data.HIGHER_THAN_MAX_MESSAGE+addCommasToLong(inputInfo.maxTran)+TransferMoneyCharity_Data.DETAIL_A_DAY_GROUP_MESSAGE);
		transferMoneyCharity.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
		transferMoneyCharity.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
		
		setupBE.Reset_Setup_Assign_Services_Type_Limit(driverWeb,Constants.BE_CODE_PACKAGE, InputText_MoneyRecurrent.BE_TRANSFER_TEXT);
		setupBE.clearCacheBE(driverWeb);
	}

	@Test
	public void TC_02_SoTienGiaoDichVuotQuaHanMucToiDa1NgayCuaGoiDichVu() {
	
		log.info("TC_02_1_Click Chuyen tien tu thien");
		transferMoneyCharity.scrollDownToText(driver, TransferMoneyCharity_Data.STATUS_TRANSFER_MONEY);
		homePage.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyCharity_Data.TRANSFER_CHARITY);

		log.info("TC_02_2_Chon tai khoan nguon");
		transferMoneyCharity.clickToDynamicDropDown(driver, TransferMoneyCharity_Data.SOURCE_ACCOUNT);
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_02_3_Chon Quy/ To chuc tu thien");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyCharity_Data.ORGANIRATION_CHARITY);
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, organization);

		log.info("TC_02_4_Nhap so tien ung ho");
		String higherMoney = (Integer.parseInt(inputInfo.totalLimit)+1)+"";
		transferMoneyCharity.inputToDynamicInputBox(driver, higherMoney, TransferMoneyCharity_Data.MONEY_CHARITY);

		log.info("TC_02_5_Nhap ten nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.name, TransferMoneyCharity_Data.NAME_CHARITY);

		log.info("TC_02_6_Nhap dia chi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.address,TransferMoneyCharity_Data.ADDRESS_CHARITY);

		log.info("TC_02_7_Hoan canh nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.status, TransferMoneyCharity_Data.STATUS_CHARITY);

		log.info("TC_02_8_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, TransferMoneyCharity_Data.CONTINUE_BUTTON);

		log.info("TC_02_Step_verify message khi so tien chuyen nho hon han muc toi thieu ");
		verifyEquals(transferMoneyCharity.getTextDynamicFollowImage(driver, "com.VCB:id/ivTitle"), TransferMoneyCharity_Data.HIGHER_THAN_MAX_MESSAGE+addCommasToLong(inputInfo.totalLimit)+TransferMoneyCharity_Data.DETAIL_A_DAY_PACKAGE_MESSAGE);
		transferMoneyCharity.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
		transferMoneyCharity.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
		
	}
		

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		setupBE.Reset_Package_Total_Limit(driverWeb, Constants.BE_CODE_PACKAGE, "Method Otp");
		driverWeb.quit();
		service.stop();
	}

}
