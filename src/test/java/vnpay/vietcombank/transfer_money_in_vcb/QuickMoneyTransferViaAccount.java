package vnpay.vietcombank.transfer_money_in_vcb;

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
import pageObjects.TransferMoneyObject;
import vietcombankUI.DynamicPageUIs;
import vietcombank_test_data.HomePage_Data;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransferMoney_Data;

public class QuickMoneyTransferViaAccount extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferMoneyObject transferMoney;
	private String amountStart;

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
		// login.clickToDynamicCloseIcon(driver, "Kích hoạt tính năng mới");

		transferMoney = PageFactoryManager.getTransferMoneyObject(driver);
	}

	@Test
	public void TC_01_ChuyenTienCoPhiGiaoDichChonNguoiChuyen() throws InterruptedException {
		log.info("TC_01_Step_Click Chuyen tien nhanh");
		transferMoney.ScrollToText(driver, "Chuyển tiền tới ngân hàng khác");
		transferMoney.clickToDynamicButionLinkOrLinkText(driver, "Chuyển tiền nhanh 24/7");

		  log.info("TC_01_Step_Select Chuyen tien nhanh qua tai khoan");
		  transferMoney.clickToDynamicButionLinkOrLinkText(driver,TransferMoney_Data.TransferQuick.OPTION_TRANSFER[0]);
		  transferMoney.clickToDynamicButionLinkOrLinkText(driver,TransferMoney_Data.TransferQuick.OPTION_TRANSFER[0]);
		  
		  log.info("TC_01_Step_Select tai khoan nguon");
		  transferMoney.clickToDynamicButionLinkOrLinkText(driver,TransferMoney_Data.TransferQuick.ACCOUNT_FORM);
		  transferMoney.clickToDynamicButionLinkOrLinkText(driver,TransferMoney_Data.TransferQuick.ACCOUNT_FORM);
		  
		  log.info("TC_01_Step_Get so du kha dung");
		  amountStart = transferMoney.getDynamicAmountLabel(driver, "Số dư khả dụng");
		  System.out.println(amountStart);
		  
		  log.info("TC_01_Step_Nhap so tai khoan chuyen");
		  transferMoney.inputToDynamicInputBox(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, TransferMoney_Data.TransferQuick.ACCOUNT_TO);
		  
		  log.info("TC_01_Step_Select ngan hang");
		  transferMoney.clickToDynamicButionLinkOrLinkText(driver,"Ngân hàng hưởng");
		  transferMoney.clickToDynamicButionLinkOrLinkText(driver,TransferMoney_Data.TransferQuick.BANK);
		  
		  log.info("TC_01_Step_Nhap so tien chuyen");
		  transferMoney.inputToDynamicInputBox(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, TransferMoney_Data.TransferQuick.MONEY);
		  
		  log.info("TC_01_Step_Chon phi giao dich");
		  transferMoney.clickToDynamicButionLinkOrLinkText(driver,TransferMoney_Data.TransferQuick.COST[0]);
		  transferMoney.clickToDynamicButionLinkOrLinkText(driver,TransferMoney_Data.TransferQuick.COST[0]);
		  
		  log.info("TC_01_Step_Nhap noi dung");
		  transferMoney.inputToDynamicInputBox(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, TransferMoney_Data.TransferQuick.NOTE);
		  
			log.info("TC_01_Step_Tiep tuc");
			login.clickToDynamicButton(driver, "Tiếp tục");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
