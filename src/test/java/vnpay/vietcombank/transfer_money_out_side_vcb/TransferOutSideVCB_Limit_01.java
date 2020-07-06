package vnpay.vietcombank.transfer_money_out_side_vcb;

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
import model.TransferOutSideVCB_Info;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.TransferMoneyOutSideVCBPageObject;
import pageObjects.WebBackendSetupPageObject;
import vietcombank_test_data.TransferMoneyInVCB_Data.InputText_MoneyRecurrent;
import vietcombank_test_data.TransferMoneyOutVCB_Data.MESSEGE_ERROR;
import vietcombank_test_data.TransferMoneyOutVCB_Data.TitleOutVCB;

public class TransferOutSideVCB_Limit_01 extends Base {
	AppiumDriver<MobileElement> driver;
	private WebDriver driverWeb;
	private WebBackendSetupPageObject setupBE;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferMoneyOutSideVCBPageObject transferMoneyOutSide;

	long transferFee = 0;
	double transferFeeCurrentcy = 0;
	String higherGroup,higherPackage,destinationAccount;
	ServiceLimitInfo inputInfo = new ServiceLimitInfo("1000", "10000", "100000000", "150000000");

	SourceAccountModel sourceAccount = new SourceAccountModel();
	TransferOutSideVCB_Info info = new TransferOutSideVCB_Info("", "", TitleOutVCB.NAME_RECIEVED, TitleOutVCB.BANK_RECIEVED, "", TitleOutVCB.TRANSACTION_FEE_SENT, TitleOutVCB.TRANSACTION_CONTENT, TitleOutVCB.PASSWORD_TITLE);
	
	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp","username","passWeb" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt,String username,String passWeb) throws IOException, InterruptedException, GeneralSecurityException {
		startServer();
		
		destinationAccount = getDataInCell(34);
		log.info("Before class: Mo backend ");
		driverWeb = openMultiBrowser(Constants.BE_BROWSER_CHROME, Constants.BE_BROWSER_VERSION, Constants.BE_URL);

		setupBE = WebPageFactoryManager.getWebBackendSetupPageObject(driver);

		setupBE.Login_Web_Backend(driverWeb, username, passWeb);
		
		setupBE.Setup_Add_Method_Package_Total_Limit(driverWeb, Constants.BE_CODE_PACKAGE, "Method Otp",inputInfo.totalLimit);
		System.out.println("Setup Package : Done");
		setupBE.Setup_Assign_Services_Type_Limit(driverWeb,Constants.BE_CODE_PACKAGE, InputText_MoneyRecurrent.BE_TRANSFER_TEXT, inputInfo.maxTran);
		System.out.println("Setup Group : Done");
		setupBE.clearCacheBE(driverWeb);
	
		
		log.info("Before class: Mo app ");
		if (deviceType.contains("android")) {
			driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		} else if (deviceType.contains("ios")) {
			driver = openIOSApp(deviceName, udid, url);
		}
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
		homePage = PageFactoryManager.getHomePageObject(driver);
		transferMoneyOutSide = PageFactoryManager.getTransferMoneyOutSideVCBPageObject(driver);
	
		higherGroup = (Integer.parseInt(inputInfo.maxTran)+1)+"";
		higherPackage = (Integer.parseInt(inputInfo.totalLimit)+1)+"";
		
		transferMoneyOutSide.scrollDownToText(driver, TitleOutVCB.TRANSFER_MONEY_STATUS_TEXT);
	}

	@Test 
	public void TC_01_ChuyenTienToiTaiKhoanKhac_LonHonNhomDichVu() {

		log.info("TC_01_1_Click Chuyen tien toi ngan hang khac");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.TITLE_TRANSFER_OUTSIDE);
		clickPopupAfter15h30();

		log.info("TC_01_2_Chon tai khoan nguon");
		transferMoneyOutSide.scrollUpToText(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		transferMoneyOutSide.clickToDynamicDropDown(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		sourceAccount = transferMoneyOutSide.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		log.info("TC_01_3_Nhap tai khoan thu huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, destinationAccount, TitleOutVCB.ACCOUT_TO);

		log.info("TC_01_4_Nhap ten nguoi huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info.name, TitleOutVCB.BENEFICIARY_NAME);

		log.info("TC_01_5_Chon ngan hang huong");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.DESTINATION_BANK);
		transferMoneyOutSide.inputToDynamicInputBox(driver, info.destinationBank, TitleOutVCB.SEARCH_BUTTON);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info.destinationBank);

		log.info("TC_01_6_Nhap so tien");
		transferMoneyOutSide.inputToDynamicInputBox(driver, higherGroup, TitleOutVCB.MONEY);

		log.info("TC_01_7_Chọn phí giao dịch");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.TRANSACTION_FEE_SENT);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.TRANSFER_PERSON);

		log.info("TC_01_7_Nhap noi dung chuyen tien");
		transferMoneyOutSide.inputToDynamicInputBoxByHeader(driver, info.note, TitleOutVCB.TRANSACTION_INFOMATION, "3");

		log.info("TC_01_8_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_9: Verify hien thi man hinh thong bao loi");
		verifyEquals(transferMoneyOutSide.getTextDynamicFollowImage(driver, "com.VCB:id/ivTitle"), MESSEGE_ERROR.HIGHER_MAX_A_TRANSACTION+addCommasToLong(inputInfo.maxTran)+MESSEGE_ERROR.DETAIL_A_DAY_GROUP_MESSAGE);

		log.info("TC_01_10_Click Dong");
		transferMoneyOutSide.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
		
		setupBE.Reset_Setup_Assign_Services_Type_Limit(driverWeb,Constants.BE_CODE_PACKAGE, InputText_MoneyRecurrent.BE_TRANSFER_TEXT);
		setupBE.clearCacheBE(driverWeb);

	}

	@Test
	public void TC_02_ChuyenTienToiTaiKhoanKhac_LonHonGoiDichVu() {

		log.info("TC_02_06_Nhap so tien");
		transferMoneyOutSide.inputToDynamicInputBoxByHeader(driver, higherPackage, TitleOutVCB.TRANSACTION_INFOMATION, "1");
		
		log.info("TC_02_9_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_02_Step_10: Verify hien thi man hinh thong bao loi");
		verifyEquals(transferMoneyOutSide.getTextDynamicFollowImage(driver, "com.VCB:id/ivTitle"), MESSEGE_ERROR.HIGHER_MAX_A_TRANSACTION+addCommasToLong(inputInfo.totalLimit)+MESSEGE_ERROR.DETAIL_A_DAY_PACKAGE_MESSAGE);

		log.info("TC_02_11: Click Dong y");
		transferMoneyOutSide.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
	
	}
	
	@AfterClass(alwaysRun = true)
	public void afterClass() {
		setupBE.Reset_Package_Total_Limit(driverWeb, Constants.BE_CODE_PACKAGE, "Method Otp");
		driverWeb.quit();
		service.stop();
	}

	public void clickPopupAfter15h30() {
		if (transferMoneyOutSide.isDynamicButtonDisplayed(driver, "Hủy")) {
			transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEXT);
		}

	}
}
