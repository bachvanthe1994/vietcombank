package vnpay.vietcombank.transfer_money_out_side_vcb;

import java.io.IOException;

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
import pageObjects.TransferMoneyObject;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransferMoney_Data;

public class QuickMoneyTransferViaAccount extends Base {
	AndroidDriver<AndroidElement> driver;
	private TransactionReportPageObject transReport;
	private String transferTime;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferMoneyObject transferMoney;
	private String transactionNumber;
	private String amountOTPString;
	private long amountOTP;
	private String moneyOTPString;
	private int moneyOTP;
	private String costOTPString;
	private int costOTP;

	private String amountStartStringUSD;
	private long amountStartUSD;
	private String moneyStringUSD;
	private int moneyUSD;
	private String costStringUSD;
	private int costUSD;

	private String amountPassString;
	private long amountPass;
	private String moneyPassString;
	private int moneyPass;
	private String costPassString;
	private int costPass;

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

		log.info("TC_06_Step_0");
		login.clickToDynamicButton(driver, "Hủy");

		login.clickToDynamicCloseIcon(driver, "Kích hoạt tính năng mới");

		transferMoney = PageFactoryManager.getTransferMoneyObject(driver);
	}

	@Test
	public void TC_01_ChuyenTienCoPhiGiaoDichChonNguoiChuyen() throws InterruptedException {
		log.info("TC_01_Step_Click Chuyen tien nhanh");

		transferMoney.scrollToText(driver, "Chuyển tiền tới ngân hàng khác");

		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh 24/7");

		log.info("TC_01_Step_Select Chuyen tien nhanh qua tai khoan");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.OPTION_TRANSFER[0]);

		log.info("TC_01_Step_Select tai khoan nguon");

		transferMoney.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.ACCOUNT_FORM);

		log.info("TC_01_Step_Get so du kha dung");
		amountOTPString = transferMoney.getDynamicAmountLabel(driver, "Số dư khả dụng").replaceAll("\\D+", "");

		amountOTP = Long.parseLong(amountOTPString);

		log.info("TC_01_Step_Nhap so tai khoan chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoney_Data.TransferQuick.ACCOUNT_TO,
				"Nhập/chọn tài khoản nhận VND");

		log.info("TC_01_Step_Select ngan hang");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng hưởng");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.BANK);

		log.info("TC_01_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoney_Data.TransferQuick.MONEY, "Số tiền");

		log.info("TC_01_Step_Chon phi giao dich");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.COST_SUB[0]);

		log.info("TC_01_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBox(driver, TransferMoney_Data.TransferQuick.NOTE, "Nội dung");

		log.info("TC_01_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_02_Step_Verify so tien chuyen");
		moneyOTPString = transferMoney.getDynamicAmountLabel(driver, "Số tiền").replaceAll("\\D+", "");
		verifyEquals(moneyOTPString, TransferMoney_Data.TransferQuick.MONEY);
		moneyOTP = Integer.parseInt(moneyOTPString);

		log.info("TC_02_Step_Verify phi chuyen tien");
		costOTPString = transferMoney.getDynamicAmountLabel(driver, "Số tiền phí").replaceAll("\\D+", "");
		verifyEquals(costOTPString, TransferMoney_Data.TransferQuick.COST_AMOUNT);
		costOTP = Integer.parseInt(costOTPString);

		log.info("TC_02_Step_Get ma giao dich");
		transactionNumber = transferMoney.getDynamicTextInTextView(driver, "Mã giao dịch");

		log.info("TC_02_Step_Chon phuong thuc xac thuc");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.ACCURACY[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.ACCURACY[1]);

		log.info("TC_02_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_02_Step_Nhap ma xac thuc");
		transferMoney.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		log.info("TC_02_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_02_Verify message thanh cong");
		verifyEquals(transferMoney.getTextDynamicPopup(driver, TransferMoney_Data.TransferQuick.MESSAGE_SUCCESS),
				"CHUYỂN KHOẢN THÀNH CÔNG");

		log.info("TC_01_Step_:");
		transferTime = transferMoney.getDynamicTransferTimeAndMoney(driver, TransferMoney_Data.TransferQuick.MESSAGE_SUCCESS,
				"4");

		log.info("TC_01_Step_:");
		transactionNumber = transferMoney.getDynamicTextInTextView(driver, "Mã giao dịch");

		log.info("TC_01_Step_:");
		verifyEquals(transferMoney.getDynamicTextInTextView(driver, "Tên người thụ hưởng"),
				TransferMoney_Data.TransferQuick.RECEIVER_NAME);

		log.info("TC_01_Step_:");
		verifyEquals(transferMoney.getDynamicTextInTextView(driver, "Tài khoản đích"),
				TransferMoney_Data.TransferQuick.ACCOUNT_TO);

		log.info("TC_01_Step_:");
		verifyEquals(transferMoney.getDynamicTextInTextView(driver, "Nội dung"), TransferMoney_Data.TransferQuick.NOTE);

		log.info("TC_01_Step_:");
		transferMoney.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_01_Step_:");
		transferMoney.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_01_Step_:");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.ACCOUNT_FORM);

		log.info("TC_01_Step_:");
		String afterBalanceOfAccount1 = transferMoney.getDynamicTextInTextView(driver, "Số dư khả dụng");
		long afterBalanceAmountOfAccount1 = Long.parseLong(afterBalanceOfAccount1);

		log.info("TC_01_Step_:");
		long transferMoney1 = Long.parseLong(TransferMoney_Data.TransferQuick.MONEY);
		verifyEquals(amountOTP - transferMoney1 - costOTP, afterBalanceAmountOfAccount1);
	}

	@Test
	public void TC_02_BaoCaoGiaoDichChuyenTienNhanh() {
		log.info("TC_Step_:");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicBottomMenu(driver, "com.VCB:id/menu_5");

		log.info("TC_Step_:");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

		log.info("TC_Step_:");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

		log.info("TC_Step_:");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh qua số tài khoản");

		log.info("TC_Step_:");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_Step_:");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.ACCOUNT_FORM);

		log.info("TC_Step_:");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		/*
		 * log.info("TC_Step_:");
		 * verifyTrue(transReport.getTextInDynamicTransaction(driver, "0",
		 * "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));
		 * 
		 * log.info("TC_Step_:");
		 * verifyTrue(transReport.getTextInDynamicTransaction(driver, "0",
		 * "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));
		 */
		log.info("TC_Step_:");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent")
				.equals(TransferMoney_Data.TransferQuick.NOTE));

		log.info("TC_Step_:");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"),
				("- " + addCommasToLong(TransferMoney_Data.TransferQuick.MONEY) + " VND"));

		log.info("TC_Step_:");
		transReport.clickToDynamicTransaction(driver, "0", "com.VCB:id/tvDate");

		/*
		 * log.info("TC_Step_:");
		 * verifyTrue(transReport.getDynamicTextInTextView(driver,
		 * "Thời gian giao dịch").contains(transferTime.split(" ")[0]));
		 * 
		 * log.info("TC_Step_:");
		 * verifyTrue(transReport.getDynamicTextInTextView(driver,
		 * "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));
		 */
		log.info("TC_Step_:");
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_Step_:");
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản/thẻ trích nợ"),
				TransferMoney_Data.TransferQuick.ACCOUNT_FORM);

		log.info("TC_Step_:");
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản ghi có"),
				TransferMoney_Data.TransferQuick.ACCOUNT_TO);

		log.info("TC_Step_:");
		verifyTrue(transReport.getDynamicTextInTextView(driver, "Số tiền giao dịch")
				.contains(addCommasToLong(TransferMoney_Data.TransferQuick.MONEY) + " VND"));

		log.info("TC_Step_:");
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Tên người hưởng"),
				TransferMoney_Data.TransferQuick.RECEIVER_NAME);

		log.info("TC_Step_:");
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Ngân hàng hưởng"),
				TransferMoney_Data.TransferQuick.BANK);

		log.info("TC_Step_:");
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Phí giao dịch"),
				TransferMoney_Data.TransferQuick.COST_SUB[0]);

		log.info("TC_Step_:");
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Loại giao dịch"),
				TransferMoney_Data.TransferQuick.TRANSFER_TYPE);

		log.info("TC_Step_:");
		verifyTrue(transReport.getDynamicTextInTextView(driver, "Nội dung giao dịch")
				.contains(TransferMoney_Data.TransferQuick.NOTE));

		log.info("TC_Step_:");
		transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_Step_:");
		transferMoney.navigateBack(driver);
	}

	@Test
	public void TC_03_ChuyenTienNhanhQuaTaiKhoanChonUSDNguoiChuyenTraPhi() {
		log.info("TC_04_Step_Click Chuyen tien nhanh");

		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh 24/7");

		log.info("TC_04_Step_Select Chuyen tien nhanh qua the");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.OPTION_TRANSFER[0]);

		log.info("TC_04_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.ACCOUNT_FORM);

		transferMoney.scrollToText(driver, "Chuyển tiền tới ngân hàng khác");
		transferMoney.clickToDynamicIcon(driver, "Chuyển tiền nhanh 24/7");

		log.info("TC_04_Step_Select Chuyen tien nhanh qua the");
		transferMoney.clickToDynamicIcon(driver, TransferMoney_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicIcon(driver, TransferMoney_Data.TransferQuick.OPTION_TRANSFER[0]);

		log.info("TC_04_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.ACCOUNT_USD_FORM);

		log.info("TC_04_Step_Get so du kha dung");
		amountStartStringUSD = transferMoney.getDynamicAmountLabel(driver, "Số dư khả dụng").replaceAll("\\D+", "");

		amountStartUSD = Long.parseLong(amountStartStringUSD);

		log.info("TC_04_Step_Nhap so tai khoan chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoney_Data.TransferQuick.ACCOUNT_TO,
				"Nhập/chọn tài khoản nhận VND");

		log.info("TC_04_Step_Select ngan hang");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng hưởng");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.BANK);

		log.info("TC_04_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoney_Data.TransferQuick.MONEY_USD, "Số tiền");

		log.info("TC_04_Step_Chon phi giao dich la nguoi chuyen");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.COST_SUB[0]);

		log.info("TC_04_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBox(driver, TransferMoney_Data.TransferQuick.NOTE, "Nội dung");

		log.info("TC_04_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_05_Step_Verify so tien chuyen");
		moneyStringUSD = transferMoney.getDynamicAmountLabel(driver, "Số tiền").replace(".00 USD", "");
		verifyEquals(moneyStringUSD, TransferMoney_Data.TransferQuick.MONEY_USD);
		moneyUSD = Integer.parseInt(moneyStringUSD);
		System.out.println(moneyUSD);

		log.info("TC_05_Step_Verify phi chuyen tien");
		costStringUSD = transferMoney.getDynamicAmountLabel(driver, "Số tiền phí").replace(".00 USD", "");
		verifyEquals(costStringUSD, TransferMoney_Data.TransferQuick.COST_AMOUNT);
		costUSD = Integer.parseInt(costStringUSD);

		log.info("TC_05_Step_Chon phuong thuc xac thuc");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.ACCURACY[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.ACCURACY[1]);

		log.info("TC_05_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_05_Step_Nhap ma xac thuc");
		transferMoney.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		log.info("TC_05_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Verify message thanh cong");
		verifyEquals(transferMoney.getTextDynamicPopup(driver, TransferMoney_Data.TransferQuick.MESSAGE_SUCCESS),
				"CHUYỂN KHOẢN THÀNH CÔNG");
		
		log.info("TC_01_Step_:");
		transferTime = transferMoney.getDynamicTransferTimeAndMoney(driver, TransferMoney_Data.TransferQuick.MESSAGE_SUCCESS,
				"4");

		log.info("TC_01_Step_:");
		transactionNumber = transferMoney.getDynamicTextInTextView(driver, "Mã giao dịch");

		log.info("TC_01_Step_:");
		verifyEquals(transferMoney.getDynamicTextInTextView(driver, "Tên người thụ hưởng"),
				TransferMoney_Data.TransferQuick.RECEIVER_NAME);

		log.info("TC_01_Step_:");
		verifyEquals(transferMoney.getDynamicTextInTextView(driver, "Tài khoản đích"),
				TransferMoney_Data.TransferQuick.ACCOUNT_TO);

		log.info("TC_01_Step_:");
		verifyEquals(transferMoney.getDynamicTextInTextView(driver, "Nội dung"), TransferMoney_Data.TransferQuick.NOTE);

		log.info("TC_01_Step_:");
		transferMoney.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_01_Step_:");
		transferMoney.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_01_Step_:");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.ACCOUNT_FORM);

		log.info("TC_01_Step_:");
		String afterBalanceOfAccount1 = transferMoney.getDynamicTextInTextView(driver, "Số dư khả dụng");
		long afterBalanceAmountOfAccount1 = Long.parseLong(afterBalanceOfAccount1);

		log.info("TC_01_Step_:");
		long transferMoney1 = Long.parseLong(TransferMoney_Data.TransferQuick.MONEY);
		verifyEquals(amountOTP - transferMoney1 - costOTP, afterBalanceAmountOfAccount1);
	}

	@Test
	public void TC_04_BaoCaoGiaoDichChuyenTienNhanh() {
		log.info("TC_Step_:");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicBottomMenu(driver, "com.VCB:id/menu_5");

		log.info("TC_Step_:");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

		log.info("TC_Step_:");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

		log.info("TC_Step_:");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh qua số tài khoản");

		log.info("TC_Step_:");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_Step_:");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.ACCOUNT_FORM);

		log.info("TC_Step_:");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		/*
		 * log.info("TC_Step_:");
		 * verifyTrue(transReport.getTextInDynamicTransaction(driver, "0",
		 * "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));
		 * 
		 * log.info("TC_Step_:");
		 * verifyTrue(transReport.getTextInDynamicTransaction(driver, "0",
		 * "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));
		 */
		log.info("TC_Step_:");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent")
				.equals(TransferMoney_Data.TransferQuick.NOTE));

		log.info("TC_Step_:");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"),
				("- " + addCommasToLong(TransferMoney_Data.TransferQuick.MONEY) + " USD"));

		log.info("TC_Step_:");
		transReport.clickToDynamicTransaction(driver, "0", "com.VCB:id/tvDate");

		/*
		 * log.info("TC_Step_:");
		 * verifyTrue(transReport.getDynamicTextInTextView(driver,
		 * "Thời gian giao dịch").contains(transferTime.split(" ")[0]));
		 * 
		 * log.info("TC_Step_:");
		 * verifyTrue(transReport.getDynamicTextInTextView(driver,
		 * "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));
		 */
		log.info("TC_Step_:");
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_Step_:");
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản/thẻ trích nợ"),
				TransferMoney_Data.TransferQuick.ACCOUNT_FORM);

		log.info("TC_Step_:");
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản ghi có"),
				TransferMoney_Data.TransferQuick.ACCOUNT_TO);

		log.info("TC_Step_:");
		verifyTrue(transReport.getDynamicTextInTextView(driver, "Số tiền giao dịch")
				.contains(addCommasToLong(TransferMoney_Data.TransferQuick.MONEY) + " USD"));

		log.info("TC_Step_:");
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Tên người hưởng"),
				TransferMoney_Data.TransferQuick.RECEIVER_NAME);

		log.info("TC_Step_:");
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Ngân hàng hưởng"),
				TransferMoney_Data.TransferQuick.BANK);

		log.info("TC_Step_:");
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Phí giao dịch"),
				TransferMoney_Data.TransferQuick.COST_SUB[0]);

		log.info("TC_Step_:");
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Loại giao dịch"),
				TransferMoney_Data.TransferQuick.TRANSFER_TYPE);

		log.info("TC_Step_:");
		verifyTrue(transReport.getDynamicTextInTextView(driver, "Nội dung giao dịch")
				.contains(TransferMoney_Data.TransferQuick.NOTE));

		log.info("TC_Step_:");
		transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_Step_:");
		transferMoney.navigateBack(driver);
	}

	@Test
	public void TC_05_ChuyenTienQuaTKNguoiChuyenTraPhiVNDXacThucMatKhau() throws InterruptedException {
		log.info("TC_01_Step_Click Chuyen tien nhanh");
		transferMoney.scrollToText(driver, "Chuyển tiền nhận bằng CMT");

		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh 24/7");

		log.info("TC_01_Step_Select Chuyen tien nhanh qua tai khoan");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.OPTION_TRANSFER[0]);

		log.info("TC_01_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		;
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.ACCOUNT_FORM);

		log.info("TC_01_Step_Get so du kha dung");
		amountPassString = transferMoney.getDynamicAmountLabel(driver, "Số dư khả dụng").replaceAll("\\D+", "");

		amountPass = Long.parseLong(amountOTPString);

		log.info("TC_01_Step_Nhap so tai khoan chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoney_Data.TransferQuick.ACCOUNT_TO,
				"Nhập/chọn tài khoản nhận VND");

		log.info("TC_01_Step_Select ngan hang");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng hưởng");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.BANK);

		log.info("TC_01_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoney_Data.TransferQuick.MONEY, "Số tiền");

		log.info("TC_01_Step_Chon phi giao dich");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.COST_SUB[0]);

		log.info("TC_01_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBox(driver, TransferMoney_Data.TransferQuick.NOTE, "Nội dung");

		log.info("TC_01_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_02_Step_Verify so tien chuyen");
		moneyPassString = transferMoney.getDynamicAmountLabel(driver, "Số tiền").replaceAll("\\D+", "");
		verifyEquals(moneyOTPString, TransferMoney_Data.TransferQuick.MONEY);
		moneyPass = Integer.parseInt(moneyOTPString);

		log.info("TC_02_Step_Verify phi chuyen tien");
		costPassString = transferMoney.getDynamicAmountLabel(driver, "Số tiền phí").replaceAll("\\D+", "");
		verifyEquals(costOTPString, TransferMoney_Data.TransferQuick.COST_AMOUNT);
		costPass = Integer.parseInt(costOTPString);

		log.info("TC_02_Step_Chon phuong thuc xac thuc");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.ACCURACY[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.ACCURACY[0]);

		log.info("TC_02_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_02_Step_Nhap ma xac thuc");
		transferMoney.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.NEW_PASSWORD, "Tiếp tục");

		log.info("TC_02_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_02_Verify message thanh cong");
		verifyEquals(transferMoney.getTextDynamicPopup(driver, TransferMoney_Data.TransferQuick.MESSAGE_SUCCESS),
				"CHUYỂN KHOẢN THÀNH CÔNG");
		
		log.info("TC_01_Step_:");
		transferTime = transferMoney.getDynamicTransferTimeAndMoney(driver, TransferMoney_Data.TransferQuick.MESSAGE_SUCCESS,
				"4");

		log.info("TC_01_Step_:");
		transactionNumber = transferMoney.getDynamicTextInTextView(driver, "Mã giao dịch");

		log.info("TC_01_Step_:");
		verifyEquals(transferMoney.getDynamicTextInTextView(driver, "Tên người thụ hưởng"),
				TransferMoney_Data.TransferQuick.RECEIVER_NAME);

		log.info("TC_01_Step_:");
		verifyEquals(transferMoney.getDynamicTextInTextView(driver, "Tài khoản đích"),
				TransferMoney_Data.TransferQuick.ACCOUNT_TO);

		log.info("TC_01_Step_:");
		verifyEquals(transferMoney.getDynamicTextInTextView(driver, "Nội dung"), TransferMoney_Data.TransferQuick.NOTE);

		log.info("TC_01_Step_:");
		transferMoney.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_01_Step_:");
		transferMoney.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_01_Step_:");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.ACCOUNT_FORM);

		log.info("TC_01_Step_:");
		String afterBalanceOfAccount1 = transferMoney.getDynamicTextInTextView(driver, "Số dư khả dụng");
		long afterBalanceAmountOfAccount1 = Long.parseLong(afterBalanceOfAccount1);

		log.info("TC_01_Step_:");
		long transferMoney1 = Long.parseLong(TransferMoney_Data.TransferQuick.MONEY);
		verifyEquals(amountOTP - transferMoney1 - costOTP, afterBalanceAmountOfAccount1);
	}

	public void TC_06_BaoCaoGiaoDichChuyenTienNhanh() {
		log.info("TC_Step_:");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicBottomMenu(driver, "com.VCB:id/menu_5");

		log.info("TC_Step_:");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

		log.info("TC_Step_:");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

		log.info("TC_Step_:");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh qua số tài khoản");

		log.info("TC_Step_:");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_Step_:");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.ACCOUNT_FORM);

		log.info("TC_Step_:");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		/*
		 * log.info("TC_Step_:");
		 * verifyTrue(transReport.getTextInDynamicTransaction(driver, "0",
		 * "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));
		 * 
		 * log.info("TC_Step_:");
		 * verifyTrue(transReport.getTextInDynamicTransaction(driver, "0",
		 * "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));
		 */
		log.info("TC_Step_:");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent")
				.equals(TransferMoney_Data.TransferQuick.NOTE));

		log.info("TC_Step_:");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"),
				("- " + addCommasToLong(TransferMoney_Data.TransferQuick.MONEY) + " VND"));

		log.info("TC_Step_:");
		transReport.clickToDynamicTransaction(driver, "0", "com.VCB:id/tvDate");

		/*
		 * log.info("TC_Step_:");
		 * verifyTrue(transReport.getDynamicTextInTextView(driver,
		 * "Thời gian giao dịch").contains(transferTime.split(" ")[0]));
		 * 
		 * log.info("TC_Step_:");
		 * verifyTrue(transReport.getDynamicTextInTextView(driver,
		 * "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));
		 */
		log.info("TC_Step_:");
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_Step_:");
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản/thẻ trích nợ"),
				TransferMoney_Data.TransferQuick.ACCOUNT_FORM);

		log.info("TC_Step_:");
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản ghi có"),
				TransferMoney_Data.TransferQuick.ACCOUNT_TO);

		log.info("TC_Step_:");
		verifyTrue(transReport.getDynamicTextInTextView(driver, "Số tiền giao dịch")
				.contains(addCommasToLong(TransferMoney_Data.TransferQuick.MONEY) + " VND"));

		log.info("TC_Step_:");
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Tên người hưởng"),
				TransferMoney_Data.TransferQuick.RECEIVER_NAME);

		log.info("TC_Step_:");
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Ngân hàng hưởng"),
				TransferMoney_Data.TransferQuick.BANK);

		log.info("TC_Step_:");
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Phí giao dịch"),
				TransferMoney_Data.TransferQuick.COST_SUB[0]);

		log.info("TC_Step_:");
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Loại giao dịch"),
				TransferMoney_Data.TransferQuick.TRANSFER_TYPE);

		log.info("TC_Step_:");
		verifyTrue(transReport.getDynamicTextInTextView(driver, "Nội dung giao dịch")
				.contains(TransferMoney_Data.TransferQuick.NOTE));

		log.info("TC_Step_:");
		transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_Step_:");
		transferMoney.navigateBack(driver);
	}

	@Test
	public void TC_07_ChuyenTienQuaTaiKhoanNguoiChuyenTraPhiEURXacThucMatKhau() {
		log.info("TC_01_Step_Click Chuyen tien nhanh");

		transferMoney.scrollToText(driver, "Chuyển tiền tới ngân hàng khác");

		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh 24/7");

		log.info("TC_01_Step_Select Chuyen tien nhanh qua tai khoan");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.OPTION_TRANSFER[0]);

		log.info("TC_01_Step_Select tai khoan nguon");

		transferMoney.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.ACCOUNT_EUR_FORM);

		log.info("TC_01_Step_Get so du kha dung");
		amountOTPString = transferMoney.getDynamicAmountLabel(driver, "Số dư khả dụng").replaceAll("\\D+", "");

		amountOTP = Long.parseLong(amountOTPString);

		log.info("TC_01_Step_Nhap so tai khoan chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoney_Data.TransferQuick.ACCOUNT_TO,
				"Nhập/chọn tài khoản nhận VND");

		log.info("TC_01_Step_Select ngan hang");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng hưởng");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.BANK);

		log.info("TC_01_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoney_Data.TransferQuick.MONEY_EUR, "Số tiền");

		log.info("TC_01_Step_Chon phi giao dich");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.COST_SUB[0]);

		log.info("TC_01_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBox(driver, TransferMoney_Data.TransferQuick.NOTE, "Nội dung");

		log.info("TC_01_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_02_Step_Verify so tien chuyen");

		moneyOTPString = transferMoney.getDynamicAmountLabel(driver, "Số tiền").replaceAll("\\D+", "");
		verifyEquals(moneyOTPString, TransferMoney_Data.TransferQuick.MONEY);
		moneyOTP = Integer.parseInt(moneyOTPString);

		log.info("TC_02_Step_Verify phi chuyen tien");
		costOTPString = transferMoney.getDynamicAmountLabel(driver, "Số tiền phí").replaceAll("\\D+", "");
		verifyEquals(costOTPString, TransferMoney_Data.TransferQuick.COST_AMOUNT);
		costOTP = Integer.parseInt(costOTPString);

		log.info("TC_02_Step_Get ma giao dich");
		transactionNumber = transferMoney.getDynamicTextInTextView(driver, "Mã giao dịch");

		log.info("TC_02_Step_Chon phuong thuc xac thuc");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.ACCURACY[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.ACCURACY[0]);

		log.info("TC_02_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_02_Step_Nhap ma xac thuc");
		transferMoney.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.NEW_PASSWORD, "Tiếp tục");

		log.info("TC_02_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_02_Verify message thanh cong");
		verifyEquals(transferMoney.getTextDynamicPopup(driver, TransferMoney_Data.TransferQuick.MESSAGE_SUCCESS),
				"CHUYỂN KHOẢN THÀNH CÔNG");

		log.info("TC_01_Step_:");
		transactionNumber = transferMoney.getDynamicTextInTextView(driver, "Mã giao dịch");

		log.info("TC_01_Step_:");
		verifyEquals(transferMoney.getDynamicTextInTextView(driver, "Tên người thụ hưởng"),
				TransferMoney_Data.TransferQuick.RECEIVER_NAME);

		log.info("TC_01_Step_:");
		verifyEquals(transferMoney.getDynamicTextInTextView(driver, "Tài khoản đích"),
				TransferMoney_Data.TransferQuick.ACCOUNT_TO);

		log.info("TC_01_Step_:");
		verifyEquals(transferMoney.getDynamicTextInTextView(driver, "Nội dung"), TransferMoney_Data.TransferQuick.NOTE);

		log.info("TC_01_Step_:");
		transferMoney.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_01_Step_:");
		transferMoney.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_01_Step_:");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.ACCOUNT_FORM);

		log.info("TC_01_Step_Get so du kha dung");
		String amountOTPString = transferMoney.getDynamicAmountLabel(driver, "Số dư khả dụng").replaceAll("\\D+", "");

		long amountOTP1 = Long.parseLong(amountOTPString);

		log.info("TC_01_Step_:");
		long transferMoney1 = Long.parseLong(TransferMoney_Data.TransferQuick.MONEY);

		verifyEquals(amountOTP - transferMoney1 - costOTP, amountOTP1);
	}

	@Test
	public void TC_08_BaoCaoGiaoDichChuyenTienNhanh() {
		log.info("TC_Step_:");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicBottomMenu(driver, "com.VCB:id/menu_5");

		log.info("TC_Step_:");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

		log.info("TC_Step_:");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

		log.info("TC_Step_:");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh qua số tài khoản");

		log.info("TC_Step_:");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_Step_:");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.ACCOUNT_FORM);

		log.info("TC_Step_:");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		/*
		 * log.info("TC_Step_:");
		 * verifyTrue(transReport.getTextInDynamicTransaction(driver, "0",
		 * "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));
		 * 
		 * log.info("TC_Step_:");
		 * verifyTrue(transReport.getTextInDynamicTransaction(driver, "0",
		 * "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));
		 */
		log.info("TC_Step_:");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent")
				.equals(TransferMoney_Data.TransferQuick.NOTE));

		log.info("TC_Step_:");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"),
				("- " + addCommasToLong(TransferMoney_Data.TransferQuick.MONEY) + " VND"));

		log.info("TC_Step_:");
		transReport.clickToDynamicTransaction(driver, "0", "com.VCB:id/tvDate");

		/*
		 * log.info("TC_Step_:");
		 * verifyTrue(transReport.getDynamicTextInTextView(driver,
		 * "Thời gian giao dịch").contains(transferTime.split(" ")[0]));
		 * 
		 * log.info("TC_Step_:");
		 * verifyTrue(transReport.getDynamicTextInTextView(driver,
		 * "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));
		 */
		log.info("TC_Step_:");
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_Step_:");
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản/thẻ trích nợ"),
				TransferMoney_Data.TransferQuick.ACCOUNT_FORM);

		log.info("TC_Step_:");
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản ghi có"),
				TransferMoney_Data.TransferQuick.ACCOUNT_TO);

		log.info("TC_Step_:");
		verifyTrue(transReport.getDynamicTextInTextView(driver, "Số tiền giao dịch")
				.contains(addCommasToLong(TransferMoney_Data.TransferQuick.MONEY) + " VND"));

		log.info("TC_Step_:");
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Tên người hưởng"),
				TransferMoney_Data.TransferQuick.RECEIVER_NAME);

		log.info("TC_Step_:");
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Ngân hàng hưởng"),
				TransferMoney_Data.TransferQuick.BANK);

		log.info("TC_Step_:");
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Phí giao dịch"),
				TransferMoney_Data.TransferQuick.COST_SUB[0]);

		log.info("TC_Step_:");
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Loại giao dịch"),
				TransferMoney_Data.TransferQuick.TRANSFER_TYPE);

		log.info("TC_Step_:");
		verifyTrue(transReport.getDynamicTextInTextView(driver, "Nội dung giao dịch")
				.contains(TransferMoney_Data.TransferQuick.NOTE));

		log.info("TC_Step_:");
		transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_Step_:");
		transferMoney.navigateBack(driver);
	}

}
