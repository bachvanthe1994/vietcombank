package vnpay.vietcombank.TransferMoney;

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
import pageObjects.LogInPageObject;
import pageObjects.TransferMoney;
import pageObjects.TransferMoneyObject;
import vietcombankUI.DynamicPageUIs;
import vietcombank_test_data.HomePage_Data;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransferMoney_Data;
import vietcombank_test_data.TransferMoney_Data.TransferQuick;

public class QuickMoneyTransferViaAccount extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferMoneyObject transferMoney;

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities,
			String appPackage, String appName) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver= openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		
		login = PageFactoryManager.getLoginPageObject(driver);
		
		log.info("Before class: Click Allow Button");
		login.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");
		log.info("Before class");
		login.inputToDynamicLogInTextBox(driver, LogIn_Data.Login_Account.PHONE, "Tiếp tục");
		
		log.info("Before class");
		login.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("Before class");
		login.inputToDynamicInputBox(driver, LogIn_Data.Login_Account.NEW_PASSWORD, LogIn_Data.UI.PASSWORD_LABEL);
		
		log.info("Before class");
		login.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("Before class");
		login.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");
		
		log.info("Before class");
		login.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("Before class");
		homePage = PageFactoryManager.getHomePageObject(driver);
		verifyTrue(homePage.isDynamicMessageAndLabelTextDisplayed(driver, HomePage_Data.Message.HOME_MESSAGE));
		
		log.info("Before class");
		login.clickToDynamicButton(driver, "Hủy");
		
		log.info("Before class");
		login.clickToDynamicClosedImage(driver, "1");
Thread.sleep(3000);
	}

	@Test
	public void TC_01_ChuyenTienCoPhiGiaoDichChonNguoiChuyen() {
		log.info("TC_01_Step_Click Chuyen tien nhanh");
		transferMoney.ScrollToText(driver, "Chuyển tiền trong VCB");
		transferMoney.clickToDynamicButionLinkOrLinkText(driver, "Chuyển tiền nhanh 24/7");
		
		log.info("TC_01_Step_Select Chuyen tien nhanh qua tai khoan");
		transferMoney.clickToDynamicButionLinkOrLinkText(driver,TransferMoney_Data.TransferQuick.OPTION_TRANSFER[1]);
		
		log.info("TC_01_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicButionLinkOrLinkText(driver,TransferMoney_Data.TransferQuick.ACCOUNT_FORM);
		
	}


	

	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
