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
	private String exchangeRate;

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

//	@Test
	public void TC_01_ChuyenTienNgayCoPhiGiaoDichNguoiChuyenTraVNDVaXacThucBangOTP() {
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicDropDown(driver,"Tài khoản nguồn");

		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT2);
		String beforeBalanceOfAccount2 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");

		long beforeBalanceAmountOfAccount2 = convertMoneyToLong(beforeBalanceOfAccount2,"VND");
		transferInVCB.clickToDynamicDropDown(driver,"Tài khoản nguồn");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT1);

		String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
		long beforeBalanceAmountOfAccount1 = convertMoneyToLong(beforeBalanceOfAccount1,"VND");
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
		long newTransferFee = convertMoneyToLong(transferFee,"VND");

		verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Nội dung"), TransferMoneyInVCB_Data.InputData.NOTE);

		log.info("TC_06_Step_0: Chon Phuong thuc nhap");
		transferInVCB.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver,"SMS OTP");

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

		transferInVCB.clickToDynamicDropDown(driver,"Tài khoản nguồn");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT1);

		String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
		long afterBalanceAmountOfAccount1 = convertMoneyToLong(afterBalanceOfAccount1,"VND");
		long transferMoney = convertMoneyToLong(TransferMoneyInVCB_Data.InputData.MONEY,"VND");
		verifyEquals(beforeBalanceAmountOfAccount1 - transferMoney - newTransferFee, afterBalanceAmountOfAccount1);

		transferInVCB.clickToDynamicDropDown(driver,"Tài khoản nguồn");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT2);

		String afterBalanceOfAccount2 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
		long afterBalanceAmountOfAccount2 = convertMoneyToLong(afterBalanceOfAccount2,"VND");
		verifyEquals(beforeBalanceAmountOfAccount2 + transferMoney, afterBalanceAmountOfAccount2);

	}

//		@Test
	public void TC_02_KiemTraChiTietGiaoDichChuyenTienNgayCoPhiGiaoDichNguoiChuyenTraVNDVaXacThucBangOTP() {

		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicBottomMenu(driver, "com.VCB:id/menu_5");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);

		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền trong Vietcombank");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT1);

		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));
		verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));
		verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputData.NOTE));
		verifyEquals(transReport.getTextInDynamicTransaction(driver, "1", "com.VCB:id/tvMoney"), ("- " + TransferMoneyInVCB_Data.InputData.CONVERT_MONEY + " VND"));
		transReport.clickToDynamicTransaction(driver, "0", "com.VCB:id/tvDate");
		verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").contains(transferTime.split(" ")[0]));
		verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Số lệnh giao dịch"), transactionNumber);
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản/thẻ trích nợ"), TransferMoneyInVCB_Data.InputData.ACCOUNT1);
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản ghi có"), TransferMoneyInVCB_Data.InputData.ACCOUNT2);
		verifyTrue(transReport.getDynamicTextInTextView(driver, "Số tiền giao dịch").contains(TransferMoneyInVCB_Data.InputData.CONVERT_MONEY + " VND"));
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Tên người hưởng"), TransferMoneyInVCB_Data.InputData.RECEIVER_NAME);
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Phí giao dịch"), TransferMoneyInVCB_Data.InputData.COST[0]);
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Số tiền phí"), TransferMoneyInVCB_Data.InputData.PAYMENT_BY_OTP_FEE + " VND");
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Loại giao dịch"), TransferMoneyInVCB_Data.InputData.TRANSFER_TYPE);
		verifyTrue(transReport.getDynamicTextInTextView(driver, "Nội dung giao dịch").contains(TransferMoneyInVCB_Data.InputData.NOTE));

		transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT2);

		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));
		verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));
		verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputData.NOTE));
		verifyEquals(transReport.getTextInDynamicTransaction(driver, "1", "com.VCB:id/tvMoney"), ("+ " + TransferMoneyInVCB_Data.InputData.CONVERT_MONEY + " VND"));
		transReport.clickToDynamicTransaction(driver, "0", "com.VCB:id/tvDate");
		verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").contains(transferTime.split(" ")[0]));
		verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Số lệnh giao dịch"), transactionNumber);
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản/thẻ trích nợ"), TransferMoneyInVCB_Data.InputData.ACCOUNT1);
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản ghi có"), TransferMoneyInVCB_Data.InputData.ACCOUNT2);
		verifyTrue(transReport.getDynamicTextInTextView(driver, "Số tiền giao dịch").contains(TransferMoneyInVCB_Data.InputData.CONVERT_MONEY + " VND"));
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Tên người hưởng"), TransferMoneyInVCB_Data.InputData.RECEIVER_NAME);
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Phí giao dịch"), TransferMoneyInVCB_Data.InputData.COST[0]);
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Số tiền phí"), TransferMoneyInVCB_Data.InputData.PAYMENT_BY_OTP_FEE + " VND");
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Loại giao dịch"), TransferMoneyInVCB_Data.InputData.TRANSFER_TYPE);
		verifyTrue(transReport.getDynamicTextInTextView(driver, "Nội dung giao dịch").contains(TransferMoneyInVCB_Data.InputData.NOTE));

		transferInVCB.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");
		transferInVCB.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");
		transferInVCB.navigateBack(driver);
	}

//	@Test
	public void TC_03_ChuyenTienNgayCoPhiGiaoDichNguoiChuyenTraVNDVaXacThucBangOTP() {
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicDropDown(driver,"Tài khoản nguồn");

		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT1);
		String beforeBalanceOfAccount2 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
		long beforeBalanceAmountOfAccount2 = convertMoneyToLong(beforeBalanceOfAccount2,"VND");
		transferInVCB.clickToDynamicDropDown(driver,"Tài khoản nguồn");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT2);
		String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
		long beforeBalanceAmountOfAccount1 = convertMoneyToLong(beforeBalanceOfAccount1,"VND");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT1, "Nhập/chọn tài khoản nhận VND");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputData.MONEY, "Số tiền");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Người nhận trả");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputData.NOTE, "Nội dung");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");
		verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Hình thức chuyển tiền"), TransferMoneyInVCB_Data.InputData.OPTION_TRANSFER[0]);
		verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Tài khoản nguồn"), TransferMoneyInVCB_Data.InputData.ACCOUNT2);
		verifyTrue(transferInVCB.getDynamicTextInTextView(driver, "Tài khoản đích / VND").contains(TransferMoneyInVCB_Data.InputData.ACCOUNT1));
		verifyTrue(transferInVCB.getDynamicTextInTextView(driver, "Số tiền (VND)").contains(TransferMoneyInVCB_Data.InputData.CONVERT_MONEY));
		verifyTrue(transferInVCB.getDynamicTextInTextView(driver, "Số tiền phí").contains(TransferMoneyInVCB_Data.InputData.PAYMENT_BY_OTP_FEE+" VND"));
		
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.InputData.COST[1]));
		transferFee = transferInVCB.getDynamicTextInTextView(driver, "Số tiền phí");
		long newTransferFee = convertMoneyToLong(transferFee,"VND");

		verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Nội dung"), TransferMoneyInVCB_Data.InputData.NOTE);
		transferInVCB.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver,"SMS OTP");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");
		transferInVCB.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE));
		transferTime = transferInVCB.getDynamicTransferTime(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE, "4");
		transactionNumber = transferInVCB.getDynamicTextInTextView(driver, "Mã giao dịch");
		verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Tên người thụ hưởng"), TransferMoneyInVCB_Data.InputData.RECEIVER_NAME);
		verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Tài khoản đích"), TransferMoneyInVCB_Data.InputData.ACCOUNT1);

		verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Nội dung"), TransferMoneyInVCB_Data.InputData.NOTE);
		transferInVCB.clickToDynamicButton(driver, "Thực hiện giao dịch mới");
		transferInVCB.clickToDynamicDropDown(driver,"Tài khoản nguồn");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT2);
		String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
		long afterBalanceAmountOfAccount1 = convertMoneyToLong(afterBalanceOfAccount1,"VND");
		long transferMoney = convertMoneyToLong(TransferMoneyInVCB_Data.InputData.MONEY,"VND");
		verifyEquals(beforeBalanceAmountOfAccount1 - transferMoney, afterBalanceAmountOfAccount1);
		transferInVCB.clickToDynamicDropDown(driver,"Tài khoản nguồn");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT1);
		String afterBalanceOfAccount2 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
		long afterBalanceAmountOfAccount2 = convertMoneyToLong(afterBalanceOfAccount2,"VND");
		verifyEquals(beforeBalanceAmountOfAccount2 + transferMoney-newTransferFee , afterBalanceAmountOfAccount2);

	}
//	@Test
public void TC_04_KiemTraChiTietGiaoDichChuyenTienNgayCoPhiGiaoDichNguoiNhanTraVNDVaXacThucBangOTP() {

	transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");
	homePage = PageFactoryManager.getHomePageObject(driver);
	homePage.clickToDynamicBottomMenu(driver, "com.VCB:id/menu_5");
	transReport = PageFactoryManager.getTransactionReportPageObject(driver);
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền trong Vietcombank");
	transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT2);
	transReport.clickToDynamicButton(driver, "Tìm kiếm");

	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputData.NOTE));
	verifyEquals(transReport.getTextInDynamicTransaction(driver, "1", "com.VCB:id/tvMoney"), ("- " + TransferMoneyInVCB_Data.InputData.CONVERT_MONEY + " VND"));
	transReport.clickToDynamicTransaction(driver, "0", "com.VCB:id/tvDate");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").contains(transferTime.split(" ")[0]));
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Số lệnh giao dịch"), transactionNumber);
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản/thẻ trích nợ"), TransferMoneyInVCB_Data.InputData.ACCOUNT2);
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản ghi có"), TransferMoneyInVCB_Data.InputData.ACCOUNT1);
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Số tiền giao dịch").contains(TransferMoneyInVCB_Data.InputData.CONVERT_MONEY + " VND"));
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tên người hưởng"), TransferMoneyInVCB_Data.InputData.RECEIVER_NAME);
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Phí giao dịch"), TransferMoneyInVCB_Data.InputData.COST[1]);
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Số tiền phí"), TransferMoneyInVCB_Data.InputData.PAYMENT_BY_OTP_FEE + " VND");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Loại giao dịch"), TransferMoneyInVCB_Data.InputData.TRANSFER_TYPE);
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Nội dung giao dịch").contains(TransferMoneyInVCB_Data.InputData.NOTE));

	transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");
	transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT1);
	transReport.clickToDynamicButton(driver, "Tìm kiếm");

	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputData.NOTE));
	verifyEquals(transReport.getTextInDynamicTransaction(driver, "1", "com.VCB:id/tvMoney"), ("+ " + TransferMoneyInVCB_Data.InputData.CONVERT_MONEY + " VND"));
	transReport.clickToDynamicTransaction(driver, "0", "com.VCB:id/tvDate");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").contains(transferTime.split(" ")[0]));
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Số lệnh giao dịch"), transactionNumber);
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản/thẻ trích nợ"), TransferMoneyInVCB_Data.InputData.ACCOUNT2);
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản ghi có"), TransferMoneyInVCB_Data.InputData.ACCOUNT1);
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Số tiền giao dịch").contains(TransferMoneyInVCB_Data.InputData.CONVERT_MONEY + " VND"));
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tên người hưởng"), TransferMoneyInVCB_Data.InputData.RECEIVER_NAME);
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Phí giao dịch"), TransferMoneyInVCB_Data.InputData.COST[1]);
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Số tiền phí"), TransferMoneyInVCB_Data.InputData.PAYMENT_BY_OTP_FEE + " VND");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Loại giao dịch"), TransferMoneyInVCB_Data.InputData.TRANSFER_TYPE);
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Nội dung giao dịch").contains(TransferMoneyInVCB_Data.InputData.NOTE));

	transferInVCB.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");
	transferInVCB.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");
	transferInVCB.navigateBack(driver);
}


@Test
public void TC_05_ChuyenTienNgayCoPhiGiaoDichNguoiChuyenTraUSDVaXacThucBangOTP() {
	
	log.info("TC05_Step :");
	homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");
	
	log.info("TC05_Step :");
	transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
	transferInVCB.clickToDynamicDropDown(driver,"Tài khoản nguồn");

	log.info("TC05_Step :");
	transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT1);
	
	log.info("TC05_Step :");
	String beforeBalanceOfAccount2 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
	float beforeBalanceAmountOfAccount2 = convertMoneyToFloat(beforeBalanceOfAccount2,"VND");
	
	log.info("TC05_Step :");
	transferInVCB.clickToDynamicDropDown(driver,"Tài khoản nguồn");
	
	log.info("TC05_Step :");
	transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.USD_ACCOUNT);
	
	log.info("TC05_Step :");
	String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
	float beforeBalanceAmountOfAccount1 = convertMoneyToFloat(beforeBalanceOfAccount1,"USD");
	
	log.info("TC05_Step :");
	transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT1, "Nhập/chọn tài khoản nhận VND");
	
	log.info("TC05_Step :");
	transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputData.AMOUNT_OF_EUR_OR_USD_TRANSFER, "Số tiền");
	
	log.info("TC05_Step :");
	exchangeRate = transferInVCB.getDynamicTextInTextView(driver, "Tỷ giá quy đổi tham khảo");
	
	log.info("TC05_Step :");
	String[]exchangeRateUSD = exchangeRate.split("~");
	float usdRate = convertMoneyToFloat(exchangeRateUSD[1], "VND");
	
	log.info("TC05_Step :");
	transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
	
	log.info("TC05_Step :");
	transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Người chuyển trả");
	
	log.info("TC05_Step :");
	transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputData.NOTE, "Nội dung");
	
	log.info("TC05_Step :");
	transferInVCB.clickToDynamicButton(driver, "Tiếp tục");
	
	log.info("TC05_Step :");
	verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Hình thức chuyển tiền"), TransferMoneyInVCB_Data.InputData.OPTION_TRANSFER[0]);
	
	log.info("TC05_Step :");
	verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Tài khoản nguồn"), TransferMoneyInVCB_Data.InputData.USD_ACCOUNT);
	
	log.info("TC05_Step :");
	verifyTrue(transferInVCB.getDynamicTextInTextView(driver, "Tài khoản đích / VND").contains(TransferMoneyInVCB_Data.InputData.ACCOUNT1));
	
	log.info("TC05_Step :");
	verifyTrue(transferInVCB.getDynamicTextInTextView(driver, "Số tiền (USD)").contains(TransferMoneyInVCB_Data.InputData.CONVERTED_AMOUNT_OF_EUR_OR_USD_TRANSFER+"USD"));
	
	log.info("TC05_Step :");
	verifyTrue(transferInVCB.getDynamicTextInTextView(driver, "Số tiền phí").contains(TransferMoneyInVCB_Data.InputData.USD_PAYMENT_BY_OTP_FEE+" USD"));
	
	log.info("TC05_Step :");
	verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.InputData.COST[0]));
	
	log.info("TC05_Step :");
	transferFee = transferInVCB.getDynamicTextInTextView(driver, "Số tiền phí");
	
	log.info("TC05_Step :");
	float newTransferFee = convertMoneyToFloat(transferFee,"USD");

	log.info("TC05_Step :");
	verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Nội dung"), TransferMoneyInVCB_Data.InputData.NOTE);
	
	log.info("TC05_Step :");
	transferInVCB.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");
	
	log.info("TC05_Step :");
	transferInVCB.clickToDynamicButtonLinkOrLinkText(driver,"SMS OTP");
	
	log.info("TC05_Step :");
	transferInVCB.clickToDynamicButton(driver, "Tiếp tục");
	
	log.info("TC05_Step :");
	transferInVCB.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");
	
	log.info("TC05_Step :");
	transferInVCB.clickToDynamicButton(driver, "Tiếp tục");
	verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE));
	
	log.info("TC05_Step : Lay thoi gian tao");
	transferTime = transferInVCB.getDynamicTransferTime(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE, "4");
	
	log.info("TC05_Step :");
	verifyEquals(transferInVCB.getDynamicTransferTime(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE, "3"),  TransferMoneyInVCB_Data.InputData.CONVERTED_AMOUNT_OF_EUR_OR_USD_TRANSFER+" USD");
	
	log.info("TC05_Step :");
	verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Tên người thụ hưởng"), TransferMoneyInVCB_Data.InputData.RECEIVER_NAME);
	
	log.info("TC05_Step :");
	verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Tài khoản đích"), TransferMoneyInVCB_Data.InputData.ACCOUNT1);

	log.info("TC05_Step :");
	transactionNumber = transferInVCB.getDynamicTextInTextView(driver, "Mã giao dịch");
	
	log.info("TC05_Step :");
	verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Nội dung"), TransferMoneyInVCB_Data.InputData.NOTE);
	
	log.info("TC05_Step :");
	transferInVCB.clickToDynamicButton(driver, "Thực hiện giao dịch mới");
	
	log.info("TC05_Step :");
	transferInVCB.clickToDynamicDropDown(driver,"Tài khoản nguồn");
	
	log.info("TC05_Step :");
	transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.USD_ACCOUNT);
	
	log.info("TC05_Step :");
	String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
	
	log.info("TC05_Step :");
	float afterBalanceAmountOfAccount1 = convertMoneyToFloat(afterBalanceOfAccount1,"USD");
	
	log.info("TC05_Step :");
	float transferMoney = convertMoneyToFloat(TransferMoneyInVCB_Data.InputData.AMOUNT_OF_EUR_OR_USD_TRANSFER,"USD");
	
	log.info("TC05_Step :");
	verifyEquals(beforeBalanceAmountOfAccount1 - transferMoney- newTransferFee, afterBalanceAmountOfAccount1);
	
	log.info("TC05_Step :");
	transferInVCB.clickToDynamicDropDown(driver,"Tài khoản nguồn");
	
	log.info("TC05_Step :");
	transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT1);
	
	log.info("TC05_Step :");
	String afterBalanceOfAccount2 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
	
	log.info("TC05_Step :");
	float afterBalanceAmountOfAccount2 = convertMoneyToFloat(afterBalanceOfAccount2,"VND");
	
	log.info("TC05_Step :");
	verifyEquals(beforeBalanceAmountOfAccount2 + transferMoney*usdRate , afterBalanceAmountOfAccount2);

}

@Test
public void TC_06_KiemTraChiTietGiaoDichChuyenTienNgayCoPhiGiaoDichNguoiChuyenTraUSDVaXacThucBangOTP() {
	
	log.info("TC06_Step 01 : Click  nut Back");
	transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");
	
	log.info("TC06_Step 02: Click vao More Icon");
	homePage = PageFactoryManager.getHomePageObject(driver);
	homePage.clickToDynamicBottomMenu(driver, "com.VCB:id/menu_5");
	
	log.info("TC06_Step : Click Bao Cao Dao Dich");
	transReport = PageFactoryManager.getTransactionReportPageObject(driver);
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");
	
	log.info("TC06_Step : Click Tat Ca Cac Loai Giao Dich");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");
	
	log.info("TC06_Step :Chon Chuyen Tien Trong VCB");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền trong Vietcombank");
	
	log.info("TC06_Step : Click Chon Tai Khoan");
	transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");
	
	log.info("TC06_Step : Chon 1 tai Khoan");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT1);
	
	log.info("TC06_Step : CLick Tim Kiem");
	transReport.clickToDynamicButton(driver, "Tìm kiếm");
	
	log.info("TC06_Step : Kiem tra ngay tao giao dich hien thi");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));
	
	log.info("TC06_Step : Kiem tra thoi gian tao dao dich");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));
	
	log.info("TC06_Step : Kiem tra noi dung hien thi");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputData.NOTE));
	
	log.info("TC06_Step : Kiem tra so tien chuyen hien thi");
	verifyEquals(transReport.getTextInDynamicTransaction(driver, "1", "com.VCB:id/tvMoney"), ("- " + TransferMoneyInVCB_Data.InputData.CONVERTED_AMOUNT_OF_EUR_OR_USD_TRANSFER + " USD"));
	
	log.info("TC06_Step : Click vao giao dich");
	transReport.clickToDynamicTransaction(driver, "0", "com.VCB:id/tvDate");
	
	log.info("TC06_Step : Kiem tra ngay giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").contains(transferTime.split(" ")[0]));
	
	log.info("TC06_Step : Kiem tra thoi gian tao giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));
	
	log.info("TC06_Step :Kiem tra so lenh giao dich");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Số lệnh giao dịch"), transactionNumber);
	
	log.info("TC06_Step : Kiem tra so tai khoan trich no");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản/thẻ trích nợ"), TransferMoneyInVCB_Data.InputData.ACCOUNT1);
	
	log.info("TC06_Step : Kiem tra so tai khoan ghi co");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản ghi có"), TransferMoneyInVCB_Data.InputData.ACCOUNT2);
	
	log.info("TC06_Step : Kiem tra so tien giao dich hien thi ");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Số tiền giao dịch").equals(TransferMoneyInVCB_Data.InputData.CONVERT_MONEY + " VND"));
	
	
	log.info("TC06_Step : Kiem tra so tien quy doi hien thi ");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Số tiền giao dịch").equals(TransferMoneyInVCB_Data.InputData.CONVERT_MONEY + " VND"));
	
	log.info("TC06_Step : Kiem tra ten nguoi huong hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tên người hưởng"), TransferMoneyInVCB_Data.InputData.RECEIVER_NAME);
	
	log.info("TC06_Step : Kiem tra phi giao dich hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Phí giao dịch"), TransferMoneyInVCB_Data.InputData.COST[0]);
	
	log.info("TC06_Step : Kiem tra so tien phi hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Số tiền phí"), TransferMoneyInVCB_Data.InputData.PAYMENT_BY_OTP_FEE + " VND");
	
	log.info("TC06_Step : Kiem tra loai giao dich");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Loại giao dịch"), TransferMoneyInVCB_Data.InputData.TRANSFER_TYPE);
	
	log.info("TC06_Step : Kiem Tra noi dung giao dich");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Nội dung giao dịch").contains(TransferMoneyInVCB_Data.InputData.NOTE));
	
	log.info("TC06_Step : Click Back icon");
	transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");
	
	log.info("TC06_Step : Chon Tai Khoan");
	transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");
	
	log.info("TC06_Step : Click chon tai khoan nhan");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT2);
	
	log.info("TC06_Step : Click Tim Kiem");
	transReport.clickToDynamicButton(driver, "Tìm kiếm");

	log.info("TC06_Step : Kiem tra ngay tao giao dich hien thi");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));
	
	log.info("TC06_Step : Kiem tra thoi gian tao giao dich hiển thị");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));
	
	log.info("TC06_Step : Kiem tra noi dung giao dich hien thi");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputData.NOTE));
	
	log.info("TC06_Step : Kiem tra so tien chuyen hien thi");
	verifyEquals(transReport.getTextInDynamicTransaction(driver, "1", "com.VCB:id/tvMoney"), ("+ " + TransferMoneyInVCB_Data.InputData.CONVERT_MONEY + " VND"));
	
	log.info("TC06_Step : Click vao bao bao giao dich chi tiet");
	transReport.clickToDynamicTransaction(driver, "0", "com.VCB:id/tvDate");
	
	log.info("TC06_Step : Kiem tra ngay tao giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").contains(transferTime.split(" ")[0]));
	
	log.info("TC06_Step : Kiem tra thoi gian tao giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));
	
	log.info("TC06_Step : Kiem tra so giao dich hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Số lệnh giao dịch"), transactionNumber);
	
	log.info("TC06_Step :Kiem tra tai khoan trich no hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản/thẻ trích nợ"), TransferMoneyInVCB_Data.InputData.ACCOUNT1);
	
	log.info("TC06_Step : Kiem tra tai khoan ghi co hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản ghi có"), TransferMoneyInVCB_Data.InputData.ACCOUNT2);
	
	log.info("TC06_Step : Kiem tra so tien giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Số tiền giao dịch").contains(TransferMoneyInVCB_Data.InputData.CONVERT_MONEY + " VND"));
	
	log.info("TC06_Step : Kiem tra so tien quy doi hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Số tiền giao dịch").contains(TransferMoneyInVCB_Data.InputData.CONVERT_MONEY + " VND"));
	
	log.info("TC06_Step : Kiem tra ten nguoi huong hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tên người hưởng"), TransferMoneyInVCB_Data.InputData.RECEIVER_NAME);
	
	log.info("TC06_Step : Kiem tra phi giao dich hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Phí giao dịch"), TransferMoneyInVCB_Data.InputData.COST[0]);
	
	log.info("TC06_Step :Kiem tra so tien phi hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Số tiền phí"), TransferMoneyInVCB_Data.InputData.PAYMENT_BY_OTP_FEE + " VND");
	
	log.info("TC06_Step :Kiem tra noi dung giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Nội dung giao dịch").contains(TransferMoneyInVCB_Data.InputData.NOTE));

	log.info("TC06_Step : Kiem tra loaigiao dich hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Loại giao dịch"), TransferMoneyInVCB_Data.InputData.TRANSFER_TYPE);
	
	log.info("TC06_Step : Click quay lai");
	transferInVCB.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");
	
	log.info("TC06_Step : Click quay lai");
	transferInVCB.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");
	
	log.info("TC06_Step : Click quay lai");
	transferInVCB.navigateBack(driver);
}
	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
