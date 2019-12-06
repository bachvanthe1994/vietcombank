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
import pageObjects.TransactionReportPageObject;
import pageObjects.TransferMoneyInVcbPageObject;
import vietcombank_test_data.HomePage_Data;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransferMoneyInVCB_Data;

public class Transfer_Money_Immedidately extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferMoneyInVcbPageObject transferInVCB;
	private TransactionReportPageObject transReport;
	private String transferFee;
	private String transferTime;
	private String transactionNumber;

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
		homePage.scrollToText(driver, "Trạng thái lệnh chuyển tiền");

	}

	@Test
	public void TC_01_ChuyenTienNgayCoPhiGiaoDichNguoiChuyenTraVNDVaXacThucBangOTP() {
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToSourceAccount(driver);

		transferInVCB.clickToDynamicButionLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT2);
		String beforeBalanceOfAccount2 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
		long beforeBalanceAmountOfAccount2 = convertMoneyToLong(beforeBalanceOfAccount2);
		transferInVCB.clickToSourceAccount(driver);
		transferInVCB.clickToDynamicButionLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT1);
		String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
		long beforeBalanceAmountOfAccount1 = convertMoneyToLong(beforeBalanceOfAccount1);
		System.out.println("Before Balance =" + beforeBalanceAmountOfAccount1);
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT2, "Nhập/chọn tài khoản nhận VND");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputData.MONEY, "Số tiền");

		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputData.NOTE, "Nội dung");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");
		verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Hình thức chuyển tiền"), TransferMoneyInVCB_Data.InputData.OPTION_TRANSFER[0]);
		verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Tài khoản nguồn"), TransferMoneyInVCB_Data.InputData.ACCOUNT1);
		verifyTrue(transferInVCB.getDynamicTextInTextView(driver, "Tài khoản đích / VND").contains(TransferMoneyInVCB_Data.InputData.ACCOUNT2));
		verifyTrue(transferInVCB.getDynamicTextInTextView(driver, "Số tiền (VND)").contains(TransferMoneyInVCB_Data.InputData.CONVERT_MONEY));
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.InputData.COST[0]));
		transferFee = transferInVCB.getDynamicTextInTextView(driver, "Số tiền phí");
		long newTransferFee = convertMoneyToLong(transferFee);

		verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Nội dung"), TransferMoneyInVCB_Data.InputData.NOTE);
		transferInVCB.clickToDynamicButionLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.PAYMENT_OPTIONS[0]);
		transferInVCB.clickToDynamicButionLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.PAYMENT_OPTIONS[1]);
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");
		transferInVCB.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE));
		transferTime = transferInVCB.getDynamicTransferTime(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE, "4");
		transactionNumber = transferInVCB.getDynamicTextInTextView(driver, "Mã giao dịch");
		verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Tên người thụ hưởng"), TransferMoneyInVCB_Data.InputData.RECEIVER_NAME);
		verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Tài khoản đích"), TransferMoneyInVCB_Data.InputData.ACCOUNT2);

		verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Nội dung"), TransferMoneyInVCB_Data.InputData.NOTE);
		transferInVCB.clickToDynamicButton(driver, "Thực hiện giao dịch mới");
		transferInVCB.clickToSourceAccount(driver);
		transferInVCB.clickToDynamicButionLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT1);
		String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
		long afterBalanceAmountOfAccount1 = convertMoneyToLong(afterBalanceOfAccount1);
		long transferMoney = convertMoneyToLong(TransferMoneyInVCB_Data.InputData.MONEY);
		verifyEquals(beforeBalanceAmountOfAccount1 - transferMoney - newTransferFee, afterBalanceAmountOfAccount1);
		transferInVCB.clickToSourceAccount(driver);
		transferInVCB.clickToDynamicButionLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT2);
		String afterBalanceOfAccount2 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
		long afterBalanceAmountOfAccount2 = convertMoneyToLong(afterBalanceOfAccount2);
		verifyEquals(beforeBalanceAmountOfAccount2 + transferMoney, afterBalanceAmountOfAccount2);

	}

		@Test
	public void TC_02_KiemTraChiTietGiaoDich() {

		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicBottomMenu(driver, "com.VCB:id/menu_5");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButionLinkOrLinkText(driver,"Báo cáo giao dịch");
		transReport.clickToDynamicButionLinkOrLinkText(driver,"Tất cả các loại giao dịch");
		transReport.clickToDynamicButionLinkOrLinkText(driver,"Chuyển tiền trong Vietcombank");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver,"com.VCB:id/tvSelectAcc");
		transReport.clickToDynamicButionLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT1);
		transReport.clickToDynamicButton(driver, "Tìm kiếm");
		
		verifyTrue(transReport.getTextInDynamicTransaction(driver, "0","com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));
		verifyTrue(transReport.getTextInDynamicTransaction(driver, "0","com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));
		verifyTrue(transReport.getTextInDynamicTransaction(driver, "0","com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputData.NOTE));
		verifyEquals(transReport.getTextInDynamicTransaction(driver, "1","com.VCB:id/tvMoney"),("- "+TransferMoneyInVCB_Data.InputData.CONVERT_MONEY+" VND"));
		transReport.clickToDynamicTransaction(driver, "0","com.VCB:id/tvDate");
		verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").contains(transferTime.split(" ")[0]));
		verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Số lệnh giao dịch"), transactionNumber);
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản/thẻ trích nợ"), TransferMoneyInVCB_Data.InputData.ACCOUNT1);
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản ghi có"), TransferMoneyInVCB_Data.InputData.ACCOUNT2);
		verifyTrue(transReport.getDynamicTextInTextView(driver, "Số tiền giao dịch").contains(TransferMoneyInVCB_Data.InputData.CONVERT_MONEY+" VND"));
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Tên người hưởng"), TransferMoneyInVCB_Data.InputData.RECEIVER_NAME);
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Phí giao dịch"), TransferMoneyInVCB_Data.InputData.COST[0]);
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Số tiền phí"), TransferMoneyInVCB_Data.InputData.PAYMENT_BY_OTP_FEE+" VND");
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Loại giao dịch"), TransferMoneyInVCB_Data.InputData.TRANSFER_TYPE);
		verifyTrue(transReport.getDynamicTextInTextView(driver, "Nội dung giao dịch").contains(TransferMoneyInVCB_Data.InputData.NOTE));

		transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver,"com.VCB:id/tvSelectAcc");
		transReport.clickToDynamicButionLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT2);
		transReport.clickToDynamicButton(driver, "Tìm kiếm");
		
		verifyTrue(transReport.getTextInDynamicTransaction(driver, "0","com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));
		verifyTrue(transReport.getTextInDynamicTransaction(driver, "0","com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));
		verifyTrue(transReport.getTextInDynamicTransaction(driver, "0","com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputData.NOTE));
		verifyEquals(transReport.getTextInDynamicTransaction(driver, "1","com.VCB:id/tvMoney"),("+ "+TransferMoneyInVCB_Data.InputData.CONVERT_MONEY+" VND"));
		transReport.clickToDynamicTransaction(driver, "0","com.VCB:id/tvDate");
		verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").contains(transferTime.split(" ")[0]));
		verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Số lệnh giao dịch"), transactionNumber);
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản/thẻ trích nợ"), TransferMoneyInVCB_Data.InputData.ACCOUNT1);
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản ghi có"), TransferMoneyInVCB_Data.InputData.ACCOUNT2);
		verifyTrue(transReport.getDynamicTextInTextView(driver, "Số tiền giao dịch").contains(TransferMoneyInVCB_Data.InputData.CONVERT_MONEY+" VND"));
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Tên người hưởng"), TransferMoneyInVCB_Data.InputData.RECEIVER_NAME);
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Phí giao dịch"), TransferMoneyInVCB_Data.InputData.COST[0]);
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Số tiền phí"), TransferMoneyInVCB_Data.InputData.PAYMENT_BY_OTP_FEE+" VND");
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Loại giao dịch"), TransferMoneyInVCB_Data.InputData.TRANSFER_TYPE);
		verifyTrue(transReport.getDynamicTextInTextView(driver, "Nội dung giao dịch").contains(TransferMoneyInVCB_Data.InputData.NOTE));
		
		transferInVCB.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");
		transferInVCB.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");
		transferInVCB.navigateBack(driver);
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
