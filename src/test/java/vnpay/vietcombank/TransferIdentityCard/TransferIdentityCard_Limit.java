package vnpay.vietcombank.TransferIdentityCard;

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
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.TransferIdentiryPageObject;
import pageObjects.WebBackendSetupPageObject;
import vietcombank_test_data.TransferIdentity_Data.textCheckElement;
import vietcombank_test_data.TransferIdentity_Data.textDataInputForm;

public class TransferIdentityCard_Limit extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferIdentiryPageObject trasferPage;
	WebDriver driverWeb;
	private WebBackendSetupPageObject loginWeb;

	String cardNumber, nameBenefical;
	SourceAccountModel sourceAccount = new SourceAccountModel();
	ServiceLimitInfo inputInfo = new ServiceLimitInfo("1000", "1000", "2000000000", "2000000001");

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp", "username", "passWeb" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String otp, String username, String passWeb) throws IOException, InterruptedException, GeneralSecurityException {

	
		driverWeb = openMultiBrowser(Constants.BE_BROWSER_CHROME, Constants.BE_BROWSER_VERSION, Constants.BE_URL);
		loginWeb = WebPageFactoryManager.getWebBackendSetupPageObject(driverWeb);
		loginWeb.Login_Web_Backend(driverWeb, username, passWeb);
		//loginWeb.resetAssignServicesLimit_All(driverWeb, textCheckElement.PAGE_TRANSFER, textCheckElement.PACKAGE_NAME);
//		loginWeb.Reset_Setup_Assign_Services_Type_Limit(driverWeb, "TESTBUG", "Chuyển khoản" );
//		loginWeb.Reset_Package_Total_Limit(driverWeb, "TESTBUG", "Method Otp");

		loginWeb.setupAssignServicesLimit_All(driverWeb, textCheckElement.PAGE_TRANSFER, inputInfo, textCheckElement.PACKAGE_NAME);
		startServer();
		log.info("Before class: Mo app ");
		if (deviceType.contains("android")) {
			driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		} else if (deviceType.contains("ios")) {
			driver = openIOSApp(deviceName, udid, url);
		}
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, otp);

		homePage = PageFactoryManager.getHomePageObject(driver);
		trasferPage = PageFactoryManager.getTransferIdentiryPageObject(driver);
		cardNumber = getDataInCell(19);
		nameBenefical = getDataInCell(1);
	}

	@Test
	public void TC_01_SoTienGiaoDichNhoHonHanMucToiThieuTren1GiaoDich() throws InterruptedException {
		log.info("TC_01_STEP_1: chon chuyen tien bang tien mat");
		homePage.clickToDynamicIcon(driver, textCheckElement.TRANSFER_MONEY);

		log.info("TC_01_STEP_2: chon tai khoan");
		trasferPage.clickToTextID(driver, "com.VCB:id/tvContent");
		sourceAccount = trasferPage.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		log.info("TC_01_Step_4: nhap ten nguoi thu huong");
		trasferPage.inputToDynamicInputBox(nameBenefical, textCheckElement.BENEFICIARY_NAME);

		log.info("TC_01_Step_5: chon giay to tuy than");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.IDENTITY);
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.IDENTITY_CARD);

		log.info("TC_01_Step_6: so CMT");
		trasferPage.inputToDynamicInputBox(driver, cardNumber, textCheckElement.NUMBER);

		log.info("TC_01_Step_7: ngay cap");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.DATE);
		trasferPage.clickToDynamicButton(driver, textCheckElement.OK);

		log.info("TC_01_Step_8: noi cap");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.ISSUED);
		trasferPage.clickToDynamicTextIndex(driver, "0", textDataInputForm.ISSUED);

		log.info("TC_01_STEP_9: nhap so tien bat dau la khong");
		trasferPage.inputToDynamicInputBox(driver, Integer.parseInt(inputInfo.minTran) - 1 + "", textCheckElement.MONEY);
		trasferPage.clickToTextID(driver, "com.VCB:id/tvTitle");

		log.info("TC_01_Step_10: noi dung");
		trasferPage.inputToDynamicInputBoxContent(driver, textDataInputForm.CONTENT_TRANSFER, "3");

		log.info("TC_01_STEP_11: chon tiep tuc");
		trasferPage.clickToDynamicButton(driver, textCheckElement.NEXT);

		verifyEquals(trasferPage.getDynamicTextView(driver, "com.VCB:id/tvContent"), textDataInputForm.CONFIRM_MIN_TRANSFER_TOP + addCommasToLong(inputInfo.minTran) + textDataInputForm.TRANSFER_LAST);

		trasferPage.clickToDynamicContinue(driver, "com.VCB:id/btOK");

		trasferPage.clickToDynamicBackIcon(driver, textCheckElement.TRANSFER_MONEY);

	}

	@Test
	public void TC_02_SoTienGiaoDiVuotQuaHanMucToiDaTren1GiaoDich() throws InterruptedException {
		homePage.clickToDynamicIcon(driver, textCheckElement.TRANSFER_MONEY);
		log.info("TC_02_STEP_2: chon tai khoan");
		trasferPage.clickToTextID(driver, "com.VCB:id/tvContent");
		sourceAccount = trasferPage.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		log.info("TC_02_Step_4: nhap ten nguoi thu huong");
		trasferPage.inputToDynamicInputBox(nameBenefical, textCheckElement.BENEFICIARY_NAME);

		log.info("TC_02_Step_5: chon giay to tuy than");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.IDENTITY);
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.IDENTITY_CARD);

		log.info("TC_02_Step_6: so CMT");
		trasferPage.inputToDynamicInputBox(driver, cardNumber, textCheckElement.NUMBER);

		log.info("TC_02_Step_7: ngay cap");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.DATE);
		trasferPage.clickToDynamicButton(driver, textCheckElement.OK);

		log.info("TC_02_Step_8: noi cap");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.ISSUED);
		trasferPage.clickToDynamicTextIndex(driver, "0", textDataInputForm.ISSUED);

		log.info("TC_02_STEP_9: nhap so tien bat dau la khong");
		trasferPage.inputToDynamicInputBox(driver, Integer.parseInt(inputInfo.maxTran) + 1 + "", textCheckElement.MONEY);
		trasferPage.clickToTextID(driver, "com.VCB:id/tvTitle");

		log.info("TC_02_Step_10: noi dung");
		trasferPage.inputToDynamicInputBoxContent(driver, textDataInputForm.CONTENT_TRANSFER, "3");

		log.info("TC_02_STEP_11: chon tiep tuc");
		trasferPage.clickToDynamicButton(driver, textCheckElement.NEXT);

		log.info("TC_02_STEP_12: chon tiep tuc");
		verifyEquals(trasferPage.getDynamicTextView(driver, "com.VCB:id/tvContent"), textDataInputForm.MAX_TRANSFER_TOP + addCommasToLong(inputInfo.maxTran) + textDataInputForm.TRANSFER_LAST);

		log.info("TC_02_STEP_12: chon tiep tuc");
		trasferPage.clickToDynamicContinue(driver, "com.VCB:id/btOK");

		log.info("TC_02_STEP_12: chon tiep tuc");
		trasferPage.clickToDynamicBackIcon(driver, textCheckElement.TRANSFER_MONEY);

	}

	@Parameters({ "pass","otp" })
	@Test
	public void TC_03_SoTienGiaoVuotQuaHanMucToiDaTrenNgayCuaDichVu(String pass, String otp) throws InterruptedException {
		log.info("TC_03_STEP_2: chon tai khoan");
		homePage.clickToDynamicIcon(driver, textCheckElement.TRANSFER_MONEY);
		trasferPage.clickToTextID(driver, "com.VCB:id/tvContent");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);
		
		log.info("TC_03_Step_4: nhap ten nguoi thu huong");
		trasferPage.inputToDynamicInputBox(nameBenefical, textCheckElement.BENEFICIARY_NAME);

		log.info("TC_03_Step_5: chon giay to tuy than");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.IDENTITY);
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.IDENTITY_CARD);

		log.info("TC_03_Step_6: so CMT");
		trasferPage.inputToDynamicInputBox(driver, cardNumber, textCheckElement.NUMBER);

		log.info("TC_03_Step_7: ngay cap");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.DATE);
		trasferPage.clickToDynamicButton(driver, textCheckElement.OK);

		log.info("TC_03_Step_8: noi cap");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.ISSUED);
		trasferPage.clickToDynamicTextIndex(driver, "0", textDataInputForm.ISSUED);

		log.info("TC_03_STEP_9: nhap so tien bat dau la khong");
		trasferPage.inputToDynamicInputBox(driver, Integer.parseInt(inputInfo.minTran) + "", textCheckElement.MONEY);
		trasferPage.clickToTextID(driver, "com.VCB:id/tvTitle");

		log.info("TC_03_Step_10: noi dung");
		trasferPage.inputToDynamicInputBoxContent(driver, textDataInputForm.CONTENT_TRANSFER, "3");

		log.info("TC_03_STEP_11: chon tiep tuc");
		trasferPage.clickToDynamicButton(driver, textCheckElement.NEXT);

		log.info("TC_03_STEP_11: chon Phuong thuc xac thuc");
		trasferPage.clickToTextID(driver, "com.VCB:id/tvptxt");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.OTP_SMS);

		log.info("TC_03_STEP_11: chon tiep tuc");
		trasferPage.clickToDynamicButton(driver, textCheckElement.NEXT);

		log.info("TC_03_STEP_14: điền mật khẩu");
		trasferPage.inputToDynamicOtp(driver, otp, textCheckElement.NEXT);

		log.info("TC_03_STEP_15: chon tiep tuc");
		trasferPage.clickToDynamicButton(driver, textCheckElement.NEXT);

		log.info("TC_03_STEP_23: chọn thực hiện giao dịch mới");
		trasferPage.clickToDynamicButton(driver, textCheckElement.NEW_TRANSFER);
		
		
		trasferPage.clickToTextID(driver, "com.VCB:id/tvContent");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_03_Step_4: nhap ten nguoi thu huong");
		trasferPage.inputToDynamicInputBox(nameBenefical, textCheckElement.BENEFICIARY_NAME);

		log.info("TC_03_Step_5: chon giay to tuy than");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.IDENTITY);
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.IDENTITY_CARD);

		log.info("TC_03_Step_6: so CMT");
		trasferPage.inputToDynamicInputBox(driver, cardNumber, textCheckElement.NUMBER);

		log.info("TC_03_Step_7: ngay cap");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.DATE);
		trasferPage.clickToDynamicButton(driver, textCheckElement.OK);

		log.info("TC_03_Step_8: noi cap");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.ISSUED);
		trasferPage.clickToDynamicTextIndex(driver, "0", textDataInputForm.ISSUED);

		log.info("TC_03_STEP_9: nhap so tien bat dau la khong");
		trasferPage.inputToDynamicInputBox(driver, Integer.parseInt(inputInfo.minTran) + "", textCheckElement.MONEY);
		trasferPage.clickToTextID(driver, "com.VCB:id/tvTitle");

		log.info("TC_03_STEP_11: chon tiep tuc");
		trasferPage.clickToDynamicButton(driver, textCheckElement.NEXT);
		
		log.info("TC_03_STEP_11: chon Phuong thuc xac thuc");
		trasferPage.clickToTextID(driver, "com.VCB:id/tvptxt");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.PASSWORD_TITLE);

		log.info("TC_03_STEP_11: chon tiep tuc");
		trasferPage.clickToDynamicButton(driver, textCheckElement.NEXT);

		log.info("TC_03_STEP_14: điền mật khẩu");
		trasferPage.inputToDynamicInputBox(driver, pass, textCheckElement.INPUT_PASSWORD);

		log.info("TC_03_STEP_15: chon tiep tuc");
		trasferPage.clickToDynamicButton(driver, textCheckElement.NEXT);

		log.info("TC_03_STEP_23: chọn thực hiện giao dịch mới");
		trasferPage.clickToDynamicButton(driver, textCheckElement.NEW_TRANSFER);
		
		trasferPage.clickToTextID(driver, "com.VCB:id/tvContent");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_03_Step_4: nhap ten nguoi thu huong");
		trasferPage.inputToDynamicInputBox(nameBenefical, textCheckElement.BENEFICIARY_NAME);

		log.info("TC_03_Step_5: chon giay to tuy than");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.IDENTITY);
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.IDENTITY_CARD);

		log.info("TC_03_Step_6: so CMT");
		trasferPage.inputToDynamicInputBox(driver, cardNumber, textCheckElement.NUMBER);

		log.info("TC_03_Step_7: ngay cap");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.DATE);
		trasferPage.clickToDynamicButton(driver, textCheckElement.OK);

		log.info("TC_03_Step_8: noi cap");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.ISSUED);
		trasferPage.clickToDynamicTextIndex(driver, "0", textDataInputForm.ISSUED);

		log.info("TC_03_STEP_9: nhap so tien bat dau la khong");
		trasferPage.inputToDynamicInputBox(driver, Integer.parseInt(inputInfo.maxTran) + "", textCheckElement.MONEY);
		trasferPage.clickToTextID(driver, "com.VCB:id/tvTitle");

		log.info("TC_03_Step_10: noi dung");
		trasferPage.inputToDynamicInputBoxContent(driver, textDataInputForm.CONTENT_TRANSFER, "3");

		log.info("TC_03_STEP_11: chon tiep tuc");
		trasferPage.clickToDynamicButton(driver, textCheckElement.NEXT);
		verifyEquals(trasferPage.getDynamicTextView(driver, "com.VCB:id/tvContent"), textDataInputForm.MAX_TRANSFER_TOP + addCommasToLong(inputInfo.totalLimit) + textDataInputForm.DAY_MAX_TRANSFER_LAST);

		log.info("TC_03_STEP_11: chon tiep tuc");
		trasferPage.clickToDynamicContinue(driver, "com.VCB:id/btOK");


	}

	@Test
	public void TC_04_SoTienGiaoVuotQuaHanMucToiDaTrenNgayCuaNhomDichVu() throws InterruptedException {

		loginWeb.Setup_Assign_Services_Type_Limit(driverWeb, textCheckElement.PACKAGE_NAME, textCheckElement.TRANSFER_TEXT, inputInfo.totalLimit);


		log.info("TC_04_STEP_9: nhap so tien bat dau la khong");
		trasferPage.inputToDynamicInputBoxByHeader(driver, (Integer.parseInt(inputInfo.totalLimit)+1) +"", textCheckElement.TRANSACTION_INFO, "1");

		log.info("TC_04_STEP_11: chon tiep tuc");
		trasferPage.clickToDynamicButton(driver, textCheckElement.NEXT);

		verifyEquals(trasferPage.getDynamicTextView(driver, "com.VCB:id/tvContent"), textDataInputForm.MAX_TRANSFER_TOP + addCommasToLong(inputInfo.totalLimit) + textDataInputForm.GROUP_MAX_TRANSFER_LAST);

	

	}

	@Test
	public void TC_05_SoTienGiaoVuotQuaHanMucToiDaTrenNgayCuaGoiDichVu() throws InterruptedException {
		loginWeb.Reset_Setup_Assign_Services_Type_Limit(driverWeb, textCheckElement.PACKAGE_NAME, textCheckElement.TRANSFER_TEXT);
		trasferPage.clickToDynamicContinue(driver, "com.VCB:id/btOK");
		
		loginWeb.Setup_Add_Method_Package_Total_Limit(driverWeb, textCheckElement.PACKAGE_NAME, textCheckElement.TITTLE_METHOD, inputInfo.totalLimit);
		
		
		log.info("TC_05_STEP_9: nhap so tien bat dau la khong");
		trasferPage.inputToDynamicInputBoxByHeader(driver, (Integer.parseInt(inputInfo.totalLimit)+1) +"", textCheckElement.TRANSACTION_INFO, "1");

		log.info("TC_05_STEP_11: chon tiep tuc");
		trasferPage.clickToDynamicButton(driver, textCheckElement.NEXT);

		verifyEquals(trasferPage.getDynamicTextView(driver, "com.VCB:id/tvContent"), textDataInputForm.MAX_TRANSFER_TOP + addCommasToLong(inputInfo.totalLimit) + textDataInputForm.PACKAGE_MAX_TRANSFER_LAST);

		trasferPage.clickToDynamicContinue(driver, "com.VCB:id/btOK");
		loginWeb.Reset_Package_Total_Limit(driverWeb, textCheckElement.PACKAGE_NAME, textCheckElement.TITTLE_METHOD);

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		driverWeb.quit();
		closeApp();
		service.stop();
	}

}
