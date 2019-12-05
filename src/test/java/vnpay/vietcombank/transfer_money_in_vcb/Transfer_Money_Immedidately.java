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
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);

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

	}

	@Test
	public void TC_01_ChuyenTienNgayCoPhiGiaoDichNguoiChuyenTraVNDVaXacThucBangOTP() {
		homePage.scrollToText(driver, "Trạng thái lệnh chuyển tiền");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToSourceAccount(driver);

		transferInVCB.clickToDynamicButionLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT1);
		String beforeBalance = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
		long beforeBalanceAmount = convertMoneyToLong(beforeBalance);
		System.out.println("Before Balance ="+ beforeBalanceAmount);
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT2, "Nhập/chọn tài khoản nhận VND");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputData.MONEY, "Số tiền");

		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputData.NOTE, "Nội dung");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");
		verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Hình thức chuyển tiền"), TransferMoneyInVCB_Data.InputData.OPTION_TRANSFER[0]);
		verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Tài khoản nguồn"), TransferMoneyInVCB_Data.InputData.ACCOUNT1);
		verifyTrue(transferInVCB.getDynamicTextInTextView(driver, "Tài khoản đích / VND").contains(TransferMoneyInVCB_Data.InputData.ACCOUNT2));
		verifyTrue(transferInVCB.getDynamicTextInTextView(driver, "Số tiền (VND)").contains(TransferMoneyInVCB_Data.InputData.CONVERT_MONEY));
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.InputData.COST[0]));
		String transferFee = transferInVCB.getDynamicTextInTextView(driver, "Số tiền phí");
		long newTransferFee = convertMoneyToLong(transferFee);
		System.out.println("Transfer fee =  "+newTransferFee);
		verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Nội dung"), TransferMoneyInVCB_Data.InputData.NOTE);
		transferInVCB.clickToDynamicButionLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.PAYMENT_OPTIONS[0]);
		transferInVCB.clickToDynamicButionLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.PAYMENT_OPTIONS[1]);
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");
		transferInVCB.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE));
		String transferTime = transferInVCB.getDynamicTransferTime(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE, "4");
		String[] dateTime = transferTime.split(" ");
		System.out.println( "Date0 ="+dateTime[0]);
		System.out.println( "Date1 ="+dateTime[1]);
		System.out.println( "Date2 ="+dateTime[2]);
		verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Tên người thụ hưởng"), TransferMoneyInVCB_Data.InputData.RECEIVE_NAME);
		verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Tài khoản đích"), TransferMoneyInVCB_Data.InputData.ACCOUNT2);

		verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Nội dung"), TransferMoneyInVCB_Data.InputData.NOTE);
		transferInVCB.clickToDynamicButton(driver, "Thực hiện giao dịch mới");
//		transferInVCB.clickToSourceAccount(driver);
//		transferInVCB.clickToDynamicButionLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT1);
//		String afterBalance = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
//		long afterBalanceAmount = convertMoneyToLong(afterBalance);
//		System.out.println("After =  "+afterBalanceAmount);
//		long transferMoney = convertMoneyToLong(TransferMoneyInVCB_Data.InputData.MONEY);
//		verifyEquals(beforeBalanceAmount - transferMoney -newTransferFee , afterBalanceAmount);
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");
		transferInVCB.clickToDynamicBottomMenu(driver, "com.VCB:id/menu_5");
		transferInVCB.clickToDynamicButionLinkOrLinkText(driver,"Báo cáo giao dịch");
		transferInVCB.clickToDynamicButionLinkOrLinkText(driver,"Tất cả các loại giao dịch");
		transferInVCB.clickToDynamicButionLinkOrLinkText(driver,"Chuyển tiền trong Vietcombank");
		transferInVCB.clickToDynamicDropdownAndDateTimePicker(driver,"com.VCB:id/tvSelectAcc");
		transferInVCB.clickToDynamicButionLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT1);
		transferInVCB.clickToDynamicButton(driver, "Tìm kiếm");
		
		verifyTrue(transferInVCB.getTextInDynamicTransaction(driver, "0","com.VCB:id/tvDate").contains(dateTime[0]));
		verifyTrue(transferInVCB.getTextInDynamicTransaction(driver, "0","com.VCB:id/tvDate").split(" ")[0].equals(dateTime[2]));
		System.out.println( "Date2 ="+transferInVCB.getTextInDynamicTransaction(driver, "0","com.VCB:id/tvDate").split(" ")[0]);
		verifyTrue(transferInVCB.getTextInDynamicTransaction(driver, "0","com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputData.NOTE));
		verifyEquals(transferInVCB.getTextInDynamicTransaction(driver, "1","com.VCB:id/tvMoney"),("- "+TransferMoneyInVCB_Data.InputData.CONVERT_MONEY+" VND"));
		transferInVCB.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");
		transferInVCB.BackKeyCode(driver);
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
