package vnpay.vietcombank.TransferIdentityCard;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import pageObjects.HomePageObject;
import pageObjects.TransferIdentiryPageObject;
import pageObjects.LogInPageObject;
import vietcombank_test_data.HomePage_Data;
import vietcombank_test_data.LogIn_Data;

public class TransferIdentity extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferIdentiryPageObject trasferPage;


	String userName = "Hoangkm";
	String Identtity = "123456789";

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities,
			String appPackage, String appName) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);

		login = PageFactoryManager.getLoginPageObject(driver);

		log.info("Before class: Click Allow Button");
		login.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");

		// login
		log.info("TC_06_Step_0");
		login.inputToDynamicLogInTextBox(driver, LogIn_Data.Login_Account.PHONE, "Tiếp tục");

		log.info("TC_06_Step_0");
		login.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_05_Step_0");
		login.inputToDynamicInputBox(driver, LogIn_Data.Login_Account.NEW_PASSWORD, LogIn_Data.UI.PASSWORD_LABEL);

		log.info("TC_06_Step_0");
		login.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_06_Step_0");
		login.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		log.info("TC_06_Step_0");
		login.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_06_Step_0");
		homePage = PageFactoryManager.getHomePageObject(driver);
		trasferPage = PageFactoryManager.getTransferPageObject(driver);
		verifyTrue(homePage.isDynamicMessageAndLabelTextDisplayed(driver, HomePage_Data.Message.HOME_MESSAGE));

		log.info("TC_06_Step_0");
		login.clickToDynamicButton(driver, "Hủy");

		homePage.clickToDynamicClosetButton(driver, "com.VCB:id/close");
		
		homePage.ScrollToText(driver, "Chuyển tiền nhận bằng CMT");

		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");
		
		String text = homePage.getTextDynamicPopup(driver, Transfer_Data.textCheckElement.PAGE_TRANSFER).trim();
		verifyTrue(text.equals(Transfer_Data.textCheckElement.PAGE_TRANSFER));
	}

//	@Test
//	public void TC_01_ChuyenTienNoiTe(){
//		log.info("TC_01: nhap ten nguoi thu huong");
//		trasferPage.inputBeneficiary("Hoangkm");
//		
//		log.info("TC_01: chon giay to tuy than");
//		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");
//		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chứng minh nhân dân");
//		
//		log.info("TC_01: so CMT");
//		trasferPage.inputIdentityNumber("123456789");
//		
//		log.info("TC_01: ngay cap");
//		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Ngày cấp");
//		trasferPage.clickToDynamicButton(driver, "OK");
//		
//		trasferPage.ScrollToText(driver, "Thông tin giao dịch");
//		
//		log.info("TC_01: noi cap");
//		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Nơi cấp");
//		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Thành phố Hà Nội ");
//		
//		log.info("TC_01: chon so tien");
//		trasferPage.inputMoney("10000");
//		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Thông tin giao dịch");
//		
//		log.info("TC_01: noi dung");
//		trasferPage.inputContent("abc123");
//		
//		log.info("TC_01: noi dung");
//		trasferPage.clickToDynamicButton(driver, "Tiếp tục");
//	}
	
	@Test
	public void TC_02_ChuyenTienNgoaiTe(){
		log.info("TC_01: chon tai khoan ngoai te");
		trasferPage.clickToDynamicAccept(driver, "com.VCB:id/tvContent");
		trasferPage.clickToDynamicAcceptText(driver, "0012370377247");
		
		log.info("TC_01: nhap ten nguoi thu huong");
		trasferPage.inputBeneficiary("Hoangkm");
		
		log.info("TC_01: chon giay to tuy than");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chứng minh nhân dân");
		
		log.info("TC_01: so CMT");
		trasferPage.inputIdentityNumber("123456789");
		
		log.info("TC_01: ngay cap");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Ngày cấp");
		trasferPage.clickToDynamicButton(driver, "OK");
		
		trasferPage.ScrollToText(driver, "Thông tin giao dịch");
		
		log.info("TC_01: noi cap");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Nơi cấp");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Thành phố Hà Nội ");
		
		log.info("TC_01: chon so tien");
		trasferPage.inputMoney("1000");
		
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Thông tin giao dịch");
		
		log.info("TC_01: noi dung");
		trasferPage.inputContent("abc123");
		
		log.info("TC_01: noi dung");
		trasferPage.clickToDynamicButton(driver, "Tiếp tục");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
