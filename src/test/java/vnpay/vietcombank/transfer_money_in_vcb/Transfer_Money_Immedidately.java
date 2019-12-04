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
import pageObjects.TransferMoneyInVcbPageObject;
import vietcombank_test_data.HomePage_Data;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransferMoneyInVCB_Data;

public class Transfer_Money_Immedidately extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferMoneyInVcbPageObject transferInVCB;

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
		verifyTrue(homePage.isDynamicMessageAndLabelTextDisplayed(driver, HomePage_Data.Message.HOME_MESSAGE));
		
		log.info("TC_06_Step_0");
		homePage.clickToDynamicButton(driver, "Hủy");
		 homePage.clickToDynamicCloseIcon(driver, "Kích hoạt tính năng mới");
		 
		 homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver)	;

		

	}

	@Test
	public void TC_01_ChuyenTienNgayCoPhiGiaoDichNguoiChuyenTraVNDVaXacThucBangOTP() {
		transferInVCB.clickToSourceAccount(driver);
		
		transferInVCB.clickToDynamicButionLinkOrLinkText(driver,TransferMoneyInVCB_Data.InputData.SOURCE );
		String balance = transferInVCB.getTextInDynamicConfirmPage(driver, "Số dư khả dụng");
		int balanceAmount = convertMoneytoInt(balance);
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputData.RECEIVE_ACCOUNT, "Nhập/chọn tài khoản nhận VND");
		
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputData.NOTE, "Nội dung");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");
		verifyEquals(transferInVCB.getTextInDynamicConfirmPage(driver, "Hình thức chuyển tiền"),TransferMoneyInVCB_Data.InputData.OPTION_TRANSFER[1]);
		verifyEquals(transferInVCB.getTextInDynamicConfirmPage(driver, "Tài khoản nguồn"),TransferMoneyInVCB_Data.InputData.SOURCE);
		verifyTrue(transferInVCB.getTextInDynamicConfirmPage(driver, "Tài khoản đích / VND").contains(TransferMoneyInVCB_Data.InputData.RECEIVE_ACCOUNT));
		verifyEquals(transferInVCB.getTextInDynamicConfirmPage(driver, "Số tiền (VND)"),TransferMoneyInVCB_Data.InputData.MONEY);
		String transferFee = transferInVCB.getTextInDynamicConfirmPage(driver, "Hình thức chuyển tiền");
		int newTransferFee = convertMoneytoInt(transferFee);
		verifyEquals(transferInVCB.getTextInDynamicConfirmPage(driver, "Nội dung"),TransferMoneyInVCB_Data.InputData.NOTE);
		transferInVCB.inputToDynamicInputBox(driver, LogIn_Data.Login_Account.NEW_PASSWORD, "Nhập mật khẩu");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE));
		String transferTime = transferInVCB.getDynamicTransferTime(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE,"1");
		verifyEquals(transferInVCB.getTextInDynamicConfirmPage(driver, "Tên người thụ hưởng"),TransferMoneyInVCB_Data.InputData.RECEIVE_NAME);
		verifyEquals(transferInVCB.getTextInDynamicConfirmPage(driver, "Tài khoản đích"),TransferMoneyInVCB_Data.InputData.RECEIVE_ACCOUNT);
		
		verifyEquals(transferInVCB.getTextInDynamicConfirmPage(driver, "Nội dung"),TransferMoneyInVCB_Data.InputData.NOTE);
		transferInVCB.clickToDynamicButton(driver, "Thực hiện giao dịch mới");
		
		
	}
	

	

	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
