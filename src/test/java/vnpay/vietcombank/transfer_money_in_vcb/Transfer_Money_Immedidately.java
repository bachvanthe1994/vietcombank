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
	double rate;
	String[]exchangeRateUSD;

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName) throws IOException, InterruptedException {
		startServer();
		log.info("Before class_Step_00: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);

		login = PageFactoryManager.getLoginPageObject(driver);

		log.info("Before class_Step_01: Click Allow Button");
		login.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");

		log.info("Before class_Step_02: Dien so dien thoai ");
		login.inputToDynamicLogInTextBox(driver, LogIn_Data.Login_Account.PHONE, "Tiếp tục");

		log.info("Before class_Step_03: Click Tiep tuc");
		login.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_05_Step_04:  Dien mat khau");
		login.inputToDynamicInputBox(driver, LogIn_Data.Login_Account.NEW_PASSWORD, LogIn_Data.UI.PASSWORD_LABEL);

		log.info("Before class_Step_05: Click tiep tuc");
		login.clickToDynamicButton(driver, "Tiếp tục");

		log.info("Before class_Step_06: Nhap OTP");
		login.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		log.info("Before class_Step_07: Click tiep tuc");
		login.clickToDynamicButton(driver, "Tiếp tục");

		log.info("Before class_Step_08: Click Huy");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicButton(driver, "Hủy");
		
		log.info("Before class_Step_09: Click icon Dong");
		homePage.clickToDynamicCloseIcon(driver, "Kích hoạt tính năng mới");
		
		log.info("Before class_Step_10: Scroll den trang thai lenh chuyen tien");
		homePage.scrollToText(driver, "Trạng thái lệnh chuyển tiền");
	}

	@Test
	public void TC_01_ChuyenTienNgayCoPhiGiaoDichNguoiChuyenTraVNDVaXacThucBangOTP() {		
		log.info("TC_01_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");
		
		log.info("TC_01_Step_02:Click tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicDropDown(driver,"Tài khoản nguồn");

		log.info("TC_01_Step_03: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT2);
		
		log.info("TC_01_Step_04: Lay so du tai khoan dich");
		String beforeBalanceOfAccount2 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
		long beforeBalanceAmountOfAccount2 = convertMoneyToLong(beforeBalanceOfAccount2,"VND");
		
		log.info("TC_01_Step_05:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver,"Tài khoản nguồn");
		
		log.info("TC_01_Step_06: Chon tai khoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);
		
		log.info("TC_01_Step_07: Lay so du tai khoan chuyen");
		String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
		long beforeBalanceAmountOfAccount1 = convertMoneyToLong(beforeBalanceOfAccount1,"VND");
		
		log.info("TC_01_Step_08: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT2, "Nhập/chọn tài khoản nhận VND");
		
		log.info("TC_01_Step_09: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.MONEY, "Số tiền");
		
		log.info("TC_01_Step_10: Nhap noi dung");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Nội dung");
		
		log.info("TC_01_Step_11: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_01_Step_12: Kiem tra hinh thuc chuyen tien hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Hình thức chuyển tiền"), TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[0]);
		
		log.info("TC_01_Step_13: Kiem tra tai khoan nguon hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Tài khoản nguồn"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);
		
		log.info("TC_01_Step_14: Kiem tra tai khoan dich hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTextView(driver, "Tài khoản đích / VND").contains(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT2));
		
		log.info("TC_01_Step_15: Kiem tra so tien chuyen hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTextView(driver, "Số tiền (VND)").contains(addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.MONEY)));
		
		log.info("TC_01_Step_16: Kiem tra so tien phi hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTextView(driver, "Số tiền phí").contains(TransferMoneyInVCB_Data.InputDataInVCB.PAYMENT_BY_OTP_FEE+" VND"));

		log.info("TC_01_Step_17: Kiem tra phuong thuc thanh toan bang OTP");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.InputDataInVCB.COST[0]));
		
		log.info("TC_01_Step_18: Lay so tien phi");
		transferFee = transferInVCB.getDynamicTextInTextView(driver, "Số tiền phí");
		long newTransferFee = convertMoneyToLong(transferFee,"VND");

		log.info("TC_01_Step_19: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_06_Step_0: Chon Phuong thuc nhap");
		transferInVCB.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");
		
		log.info("TC_01_Step_:");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver,"SMS OTP");

		log.info("TC_01_Step_:");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_01_Step_:");
		transferInVCB.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");
		
		log.info("TC_01_Step_:");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_01_Step_:");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE));
		
		log.info("TC_01_Step_:");
		transferTime = transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE, "4");
		
		log.info("TC_01_Step_:");
		transactionNumber = transferInVCB.getDynamicTextInTextView(driver, "Mã giao dịch");
		
		log.info("TC_01_Step_:");
		verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Tên người thụ hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);
		
		log.info("TC_01_Step_:");
		verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Tài khoản đích"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT2);
		
		log.info("TC_01_Step_:");
		verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);
		
		log.info("TC_01_Step_:");
		transferInVCB.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_01_Step_:");
		transferInVCB.clickToDynamicDropDown(driver,"Tài khoản nguồn");
		
		log.info("TC_01_Step_:");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);
		
		log.info("TC_01_Step_:");
		String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
		long afterBalanceAmountOfAccount1 = convertMoneyToLong(afterBalanceOfAccount1,"VND");
		
		log.info("TC_01_Step_:");
		long transferMoney = convertMoneyToLong(TransferMoneyInVCB_Data.InputDataInVCB.MONEY,"VND");
		verifyEquals(beforeBalanceAmountOfAccount1 - transferMoney - newTransferFee, afterBalanceAmountOfAccount1);

		log.info("TC_01_Step_:");
		transferInVCB.clickToDynamicDropDown(driver,"Tài khoản nguồn");
		
		log.info("TC_01_Step_:");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT2);
		
		log.info("TC_01_Step_:");
		String afterBalanceOfAccount2 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
		
		log.info("TC_01_Step_:");
		long afterBalanceAmountOfAccount2 = convertMoneyToLong(afterBalanceOfAccount2,"VND");
		verifyEquals(beforeBalanceAmountOfAccount2 + transferMoney, afterBalanceAmountOfAccount2);

	}

		@Test
	public void TC_02_KiemTraChiTietGiaoDichChuyenTienNgayCoPhiGiaoDichNguoiChuyenTraVNDVaXacThucBangOTP() {
		log.info("TC_02_Step_:");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");
		
		log.info("TC_02_Step_:");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicBottomMenu(driver, "com.VCB:id/menu_5");
		
		log.info("TC_02_Step_:");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");
		
		log.info("TC_02_Step_:");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");
		
		log.info("TC_02_Step_:");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền trong Vietcombank");
		
		log.info("TC_02_Step_:");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");
		
		log.info("TC_02_Step_:");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);
		
		log.info("TC_02_Step_:");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");
	
		log.info("TC_02_Step_:");
		verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));
		
		log.info("TC_02_Step_:");
		verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));
		
		log.info("TC_02_Step_:");
		verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));
		
		log.info("TC_02_Step_:");
		verifyEquals(transReport.getTextInDynamicTransaction(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.MONEY) + " VND"));
		
		log.info("TC_02_Step_:");
		transReport.clickToDynamicTransaction(driver, "0", "com.VCB:id/tvDate");
		
		log.info("TC_02_Step_:");
		verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").contains(transferTime.split(" ")[0]));
		
		log.info("TC_02_Step_:");
		verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));
		
		log.info("TC_02_Step_:");
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Số lệnh giao dịch"), transactionNumber);
		
		log.info("TC_02_Step_:");
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản/thẻ trích nợ"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);
		
		log.info("TC_02_Step_:");
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản ghi có"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT2);
		
		log.info("TC_02_Step_:");
		verifyTrue(transReport.getDynamicTextInTextView(driver, "Số tiền giao dịch").contains( addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.MONEY) + " VND"));
		
		log.info("TC_02_Step_:");
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Tên người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);
		
		log.info("TC_02_Step_:");
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Phí giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.COST[0]);
		
		log.info("TC_02_Step_:");
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Số tiền phí"), TransferMoneyInVCB_Data.InputDataInVCB.PAYMENT_BY_OTP_FEE + " VND");
		
		log.info("TC_02_Step_:");
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Loại giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.TRANSFER_TYPE);
		
		log.info("TC_02_Step_:");
		verifyTrue(transReport.getDynamicTextInTextView(driver, "Nội dung giao dịch").contains(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_02_Step_:");
		transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");
		
		log.info("TC_02_Step_:");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");
		
		log.info("TC_02_Step_:");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT2);

		log.info("TC_02_Step_:");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_02_Step_:");
		verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));
		
		log.info("TC_02_Step_:");
		verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));
		
		log.info("TC_02_Step_:");
		verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));
		
		log.info("TC_02_Step_:");
		verifyEquals(transReport.getTextInDynamicTransaction(driver, "1", "com.VCB:id/tvMoney"), ("+ " + addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.MONEY) + " VND"));
		
		log.info("TC_02_Step_:");
		transReport.clickToDynamicTransaction(driver, "0", "com.VCB:id/tvDate");
		
		log.info("TC_02_Step_:");
		verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").contains(transferTime.split(" ")[0]));
		
		log.info("TC_02_Step_:");
		verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));
		
		log.info("TC_02_Step_:");
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Số lệnh giao dịch"), transactionNumber);
		
		log.info("TC_02_Step_:");
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản/thẻ trích nợ"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);
		
		log.info("TC_02_Step_:");
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản ghi có"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT2);
		
		log.info("TC_02_Step_:");
		verifyTrue(transReport.getDynamicTextInTextView(driver, "Số tiền giao dịch").contains(addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.MONEY) + " VND"));
		
		log.info("TC_02_Step_:");
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Tên người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);
		
		log.info("TC_02_Step_:");
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Phí giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.COST[0]);
		
		log.info("TC_02_Step_:");
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Số tiền phí"), TransferMoneyInVCB_Data.InputDataInVCB.PAYMENT_BY_OTP_FEE + " VND");
		
		log.info("TC_02_Step_:");
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Loại giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.TRANSFER_TYPE);
		
		log.info("TC_02_Step_:");
		verifyTrue(transReport.getDynamicTextInTextView(driver, "Nội dung giao dịch").contains(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_02_Step_:");
		transferInVCB.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");
		
		log.info("TC_02_Step_:");
		transferInVCB.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");
		
		log.info("TC_02_Step_:");
		transferInVCB.navigateBack(driver);
	}

//	@Test
	public void TC_03_ChuyenTienNgayCoPhiGiaoDichNguoiChuyenTraVNDVaXacThucBangOTP() {
		
		
		log.info("TC_03_Step_:");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");
		
		log.info("TC_03_Step_:");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		
		log.info("TC_03_Step_:");
		transferInVCB.clickToDynamicDropDown(driver,"Tài khoản nguồn");

		log.info("TC_03_Step_:");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);
		
		log.info("TC_03_Step_:");
		String beforeBalanceOfAccount2 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
		long beforeBalanceAmountOfAccount2 = convertMoneyToLong(beforeBalanceOfAccount2,"VND");
		
		log.info("TC_03_Step_:");
		transferInVCB.clickToDynamicDropDown(driver,"Tài khoản nguồn");
		
		log.info("TC_03_Step_:");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT2);
		
		log.info("TC_03_Step_:");
		String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
		long beforeBalanceAmountOfAccount1 = convertMoneyToLong(beforeBalanceOfAccount1,"VND");
		
		log.info("TC_03_Step_:");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1, "Nhập/chọn tài khoản nhận VND");
		
		log.info("TC_03_Step_:");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.MONEY, "Số tiền");
		
		log.info("TC_03_Step_:");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
		
		log.info("TC_03_Step_:");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Người nhận trả");
		
		log.info("TC_03_Step_:");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Nội dung");
		
		log.info("TC_03_Step_:");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_03_Step_:");
		verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Hình thức chuyển tiền"), TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[0]);
		
		log.info("TC_03_Step_:");
		verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Tài khoản nguồn"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT2);
		
		log.info("TC_03_Step_:");
		verifyTrue(transferInVCB.getDynamicTextInTextView(driver, "Tài khoản đích / VND").contains(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1));
		
		log.info("TC_03_Step_:");
		verifyTrue(transferInVCB.getDynamicTextInTextView(driver, "Số tiền (VND)").contains(addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.MONEY)));
		
		log.info("TC_03_Step_:");
		verifyTrue(transferInVCB.getDynamicTextInTextView(driver, "Số tiền phí").contains(TransferMoneyInVCB_Data.InputDataInVCB.PAYMENT_BY_OTP_FEE+" VND"));
		
		log.info("TC_03_Step_:");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.InputDataInVCB.COST[1]));
		
		log.info("TC_03_Step_:");
		transferFee = transferInVCB.getDynamicTextInTextView(driver, "Số tiền phí");
		long newTransferFee = convertMoneyToLong(transferFee,"VND");

		log.info("TC_03_Step_:");		
		verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);
		
		log.info("TC_03_Step_:");
		transferInVCB.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");
		
		log.info("TC_03_Step_:");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver,"SMS OTP");
		
		log.info("TC_03_Step_:");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_03_Step_:");
		transferInVCB.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");
		
		log.info("TC_03_Step_:");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_03_Step_:");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE));
		
		log.info("TC_03_Step_:");
		transferTime = transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE, "4");
		
		log.info("TC_03_Step_:");
		transactionNumber = transferInVCB.getDynamicTextInTextView(driver, "Mã giao dịch");
		
		log.info("TC_03_Step_:");
		verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Tên người thụ hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);
		
		log.info("TC_03_Step_:");
		verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Tài khoản đích"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_03_Step_:");
		verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);
		
		log.info("TC_03_Step_:");
		transferInVCB.clickToDynamicButton(driver, "Thực hiện giao dịch mới");
		
		log.info("TC_03_Step_:");
		transferInVCB.clickToDynamicDropDown(driver,"Tài khoản nguồn");
		
		log.info("TC_03_Step_:");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT2);
		
		log.info("TC_03_Step_:");
		String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
		long afterBalanceAmountOfAccount1 = convertMoneyToLong(afterBalanceOfAccount1,"VND");
		
		log.info("TC_03_Step_:");
		long transferMoney = convertMoneyToLong(TransferMoneyInVCB_Data.InputDataInVCB.MONEY,"VND");
		verifyEquals(beforeBalanceAmountOfAccount1 - transferMoney, afterBalanceAmountOfAccount1);
		
		log.info("TC_03_Step_:");
		transferInVCB.clickToDynamicDropDown(driver,"Tài khoản nguồn");
		
		log.info("TC_03_Step_:");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);
		
		log.info("TC_03_Step_:");
		String afterBalanceOfAccount2 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
		long afterBalanceAmountOfAccount2 = convertMoneyToLong(afterBalanceOfAccount2,"VND");
		
		log.info("TC_03_Step_:");
		verifyEquals(beforeBalanceAmountOfAccount2 + transferMoney-newTransferFee , afterBalanceAmountOfAccount2);

	}
//	@Test
public void TC_04_KiemTraChiTietGiaoDichChuyenTienNgayCoPhiGiaoDichNguoiNhanTraVNDVaXacThucBangOTP() {
	
	log.info("TC_04_Step_:");
	transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");
	
	log.info("TC_04_Step_:");
	homePage = PageFactoryManager.getHomePageObject(driver);
	homePage.clickToDynamicBottomMenu(driver, "com.VCB:id/menu_5");
	
	log.info("TC_04_Step_:");
	transReport = PageFactoryManager.getTransactionReportPageObject(driver);
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");
	
	log.info("TC_04_Step_:");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");
	
	log.info("TC_04_Step_:");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền trong Vietcombank");
	
	log.info("TC_04_Step_:");
	transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");
	
	log.info("TC_04_Step_:");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT2);
	
	log.info("TC_04_Step_:");
	transReport.clickToDynamicButton(driver, "Tìm kiếm");

	log.info("TC_04_Step_:");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));
	
	log.info("TC_04_Step_:");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));
	
	log.info("TC_04_Step_:");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));
	
	log.info("TC_04_Step_:");
	verifyEquals(transReport.getTextInDynamicTransaction(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.MONEY) + " VND"));
	
	log.info("TC_04_Step_:");
	transReport.clickToDynamicTransaction(driver, "0", "com.VCB:id/tvDate");
	
	log.info("TC_04_Step_:");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").contains(transferTime.split(" ")[0]));
	
	log.info("TC_04_Step_:");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));
	
	log.info("TC_04_Step_:");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Số lệnh giao dịch"), transactionNumber);
	
	log.info("TC_04_Step_:");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản/thẻ trích nợ"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT2);
	
	log.info("TC_04_Step_:");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản ghi có"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);
	
	log.info("TC_04_Step_:");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Số tiền giao dịch").contains(addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.MONEY) + " VND"));
	
	log.info("TC_04_Step_:");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tên người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);
	
	log.info("TC_04_Step_:");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Phí giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.COST[1]);
	
	log.info("TC_04_Step_:");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Số tiền phí"), TransferMoneyInVCB_Data.InputDataInVCB.PAYMENT_BY_OTP_FEE + " VND");
	
	log.info("TC_04_Step_:");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Loại giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.TRANSFER_TYPE);
	
	log.info("TC_04_Step_:");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Nội dung giao dịch").contains(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

	log.info("TC_04_Step_:");
	transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");
	
	log.info("TC_04_Step_:");
	transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");
	
	log.info("TC_04_Step_:");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);
	
	log.info("TC_04_Step_:");
	transReport.clickToDynamicButton(driver, "Tìm kiếm");
	
	log.info("TC_04_Step_:");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));
	
	log.info("TC_04_Step_:");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));
	
	log.info("TC_04_Step_:");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));
	
	log.info("TC_04_Step_:");
	verifyEquals(transReport.getTextInDynamicTransaction(driver, "1", "com.VCB:id/tvMoney"), ("+ " + addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.MONEY) + " VND"));
	
	log.info("TC_04_Step_:");
	transReport.clickToDynamicTransaction(driver, "0", "com.VCB:id/tvDate");
	
	log.info("TC_04_Step_:");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").contains(transferTime.split(" ")[0]));
	
	log.info("TC_04_Step_:");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));
	
	log.info("TC_04_Step_:");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Số lệnh giao dịch"), transactionNumber);
	
	log.info("TC_04_Step_:");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản/thẻ trích nợ"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT2);
	
	log.info("TC_04_Step_:");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản ghi có"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);
	
	log.info("TC_04_Step_:");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Số tiền giao dịch").contains(addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.MONEY) + " VND"));
	
	log.info("TC_04_Step_:");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tên người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);
	
	log.info("TC_04_Step_:");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Phí giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.COST[1]);
	
	log.info("TC_04_Step_:");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Số tiền phí"), TransferMoneyInVCB_Data.InputDataInVCB.PAYMENT_BY_OTP_FEE + " VND");
	
	log.info("TC_04_Step_:");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Loại giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.TRANSFER_TYPE);
	
	log.info("TC_04_Step_:");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Nội dung giao dịch").contains(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

	log.info("TC_04_Step_:");
	transferInVCB.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");
	
	log.info("TC_04_Step_:");
	transferInVCB.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");
	
	log.info("TC_04_Step_:");
	transferInVCB.navigateBack(driver);
}


//@Test
public void TC_05_ChuyenTienNgayCoPhiGiaoDichNguoiChuyenTraUSDVaXacThucBangOTP() {
	
	log.info("TC05_Step 01: Click Chuyen tien trong VCB");
	homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");
	
	log.info("TC05_Step 02: Click chon tai khoan nguon");
	transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
	transferInVCB.clickToDynamicDropDown(driver,"Tài khoản nguồn");

	log.info("TC05_Step 03: Chon account 1");
	transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);
	
	log.info("TC05_Step 04: Lay so du kha dung tai khoan 1");
	String beforeBalanceOfAccount2 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
	double beforeBalanceAmountOfAccount2 = convertMoneyToDouble(beforeBalanceOfAccount2,"VND");
	
	log.info("TC05_Step : Click tai khoan nguon");
	transferInVCB.clickToDynamicDropDown(driver,"Tài khoản nguồn");
	
	log.info("TC05_Step : Chon USD account");
	transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.USD_ACCOUNT);
	
	log.info("TC05_Step : Lay so du tai khoan USD");
	String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
	double beforeBalanceAmountOfAccount1 = convertMoneyToDouble(beforeBalanceOfAccount1,"USD");
	
	log.info("TC05_Step : Nhap tai khoan nhan");
	transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1, "Nhập/chọn tài khoản nhận VND");
	
	log.info("TC05_Step : Nhap so tien can chuyen");
	transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER, "Số tiền");
	
	log.info("TC05_Step : Lay ti gia quy doi");
	String exchangeRate = transferInVCB.getDynamicTextInTextView(driver, "Tỷ giá quy đổi tham khảo");
	exchangeRateUSD = exchangeRate.split(" ~ ");
	rate = convertMoneyToDouble(exchangeRateUSD[1], "VND");
	
	log.info("TC05_Step : Chon phuong thuc xac thuc");
	transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
	
	log.info("TC05_Step : Chon phi giao dich nguoi chuyen tra");
	transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Người chuyển trả");
	
	log.info("TC05_Step :  Nhap noi dung");
	transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Nội dung");
	
	log.info("TC05_Step : Click tiep tuc");
	transferInVCB.clickToDynamicButton(driver, "Tiếp tục");
	
	log.info("TC05_Step : Kiem tra hinh thuc chuyen tien hien thi");
	verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Hình thức chuyển tiền"), TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[0]);
	
	log.info("TC05_Step : Kiem tra tai khoan nguon hien thi");
	verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Tài khoản nguồn"), TransferMoneyInVCB_Data.InputDataInVCB.USD_ACCOUNT);
	
	log.info("TC05_Step : Kiem tra tai khoan dich hien thi");
	verifyTrue(transferInVCB.getDynamicTextInTextView(driver, "Tài khoản đích / VND").contains(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1));
	
	log.info("TC05_Step : Kiem tra so tien chuyen hien thi");
	verifyTrue(transferInVCB.getDynamicTextInTextView(driver, "Số tiền (USD)").contains(addCommasToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER)+"USD"));
	
	log.info("TC05_Step : Kiem tra so tien phi hien thi"); 
	verifyTrue(transferInVCB.getDynamicTextInTextView(driver, "Số tiền phí").contains(TransferMoneyInVCB_Data.InputDataInVCB.USD_PAYMENT_BY_OTP_FEE+" USD"));
	
	log.info("TC05_Step : Kiem tra Nguoi chuyen tra hien thi");
	verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.InputDataInVCB.COST[0]));
	
	log.info("TC05_Step : Lay tien phi chuyen ");
	transferFee = transferInVCB.getDynamicTextInTextView(driver, "Số tiền phí");
	
	log.info("TC05_Step : Lay phi chuyen");
	double newTransferFee = convertMoneyToDouble(transferFee,"USD");

	log.info("TC05_Step : Kiem tra noi dung hien thi");
	verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);
	
	log.info("TC05_Step : Chon phuong thuc xac thuc");
	transferInVCB.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");
	
	log.info("TC05_Step : Chon SMS OTP");
	transferInVCB.clickToDynamicButtonLinkOrLinkText(driver,"SMS OTP");
	
	log.info("TC05_Step : Click tiep tuc");
	transferInVCB.clickToDynamicButton(driver, "Tiếp tục");
	
	log.info("TC05_Step : Dien OTP");
	transferInVCB.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");
	
	log.info("TC05_Step : Click tiep tuc");
	transferInVCB.clickToDynamicButton(driver, "Tiếp tục");
	verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE));
	
	log.info("TC05_Step : Lay thoi gian tao");
	transferTime = transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE, "4");
	
	log.info("TC05_Step : Kiem tra so tien chuyen hien thi");
	verifyEquals(transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE, "3"),  addCommasToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER)+" USD");
	
	log.info("TC05_Step : Kiem tra ten nguoi thu huong hien thi");
	verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Tên người thụ hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);
	
	log.info("TC05_Step : Kiem tra tai khoan dich hien thi");
	verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Tài khoản đích"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

	log.info("TC05_Step : Lay ma giao dich");
	transactionNumber = transferInVCB.getDynamicTextInTextView(driver, "Mã giao dịch");
	
	log.info("TC05_Step : Kiem tra noi dung hien thi");
	verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);
	
	log.info("TC05_Step : Click thuc hien giao dich moi");
	transferInVCB.clickToDynamicButton(driver, "Thực hiện giao dịch mới");
	
	log.info("TC05_Step : Click tai khoan nguon");
	transferInVCB.clickToDynamicDropDown(driver,"Tài khoản nguồn");
	
	log.info("TC05_Step :Click USD account");
	transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.USD_ACCOUNT);
	
	log.info("TC05_Step : Lay so du kha dung tai khoan USD");
	String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
	double afterBalanceAmountOfAccount1 = convertMoneyToDouble(afterBalanceOfAccount1,"USD");
	
	log.info("TC05_Step : Convert so tien chuyen");
	double transferMoney = convertMoneyToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER,"USD");
	
	log.info("TC05_Step : Kiem tra so du tai khoan USD sau khi chuyen tien");
	verifyEquals(beforeBalanceAmountOfAccount1 - transferMoney- newTransferFee, afterBalanceAmountOfAccount1);
	
	log.info("TC05_Step : Kiem tra tai khoan nguon");
	transferInVCB.clickToDynamicDropDown(driver,"Tài khoản nguồn");
	
	log.info("TC05_Step : Click chon tai khoan chuyen den");
	transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);
	
	log.info("TC05_Step : Lay so du kha dung tai khoan chuyen den");
	String afterBalanceOfAccount2 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
	double afterBalanceAmountOfAccount2 = convertMoneyToDouble(afterBalanceOfAccount2,"VND");
	
	log.info("TC05_Step : Kiem tra so du tai khoan duoc chuyen den");
	verifyEquals(beforeBalanceAmountOfAccount2 + transferMoney*rate , afterBalanceAmountOfAccount2);

}

//@Test
public void TC_06_KiemTraChiTietGiaoDichChuyenTienNgayCoPhiGiaoDichNguoiChuyenTraUSDVaXacThucBangOTP() throws InterruptedException {
	
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
	transReport.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.USD_ACCOUNT);
	
	log.info("TC06_Step : CLick Tim Kiem");
	transReport.clickToDynamicButton(driver, "Tìm kiếm");
	
	log.info("TC06_Step : Kiem tra ngay tao giao dich hien thi");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));
	
	log.info("TC06_Step : Kiem tra thoi gian tao dao dich");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));
	
	log.info("TC06_Step : Kiem tra noi dung hien thi");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));
	
	log.info("TC06_Step : Kiem tra so tien chuyen hien thi");
	verifyEquals(transReport.getTextInDynamicTransaction(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " USD"));
	
	log.info("TC06_Step : Click vao giao dich");
	transReport.clickToDynamicTransaction(driver, "0", "com.VCB:id/tvDate");
	
	log.info("TC06_Step : Kiem tra ngay giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").contains(transferTime.split(" ")[0]));
	
	log.info("TC06_Step : Kiem tra thoi gian tao giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));
	
	log.info("TC06_Step :Kiem tra so lenh giao dich");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Số lệnh giao dịch"), transactionNumber);
	
	log.info("TC06_Step : Kiem tra so tai khoan trich no");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản/thẻ trích nợ"), TransferMoneyInVCB_Data.InputDataInVCB.USD_ACCOUNT);
	
	log.info("TC06_Step : Kiem tra so tai khoan ghi co");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản ghi có"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);
	
	log.info("TC06_Step : Kiem tra so tien giao dich hien thi ");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Số tiền giao dịch").equals(addCommasToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " USD"));
	
	
	log.info("TC06_Step : Kiem tra so tien quy doi hien thi ");
	String changedMoney = convertMoneyToLong(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER, "USD")*convertMoneyToLong(exchangeRateUSD[1], "VND") +"";
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Số tiền quy đổi").contains(addCommasToLong(changedMoney) + " VND"));
	
	log.info("TC06_Step : Kiem tra ten nguoi huong hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tên người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);
	
	log.info("TC06_Step : Kiem tra phi giao dich hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Phí giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.COST[0]);
	
	log.info("TC06_Step : Kiem tra so tien phi hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Số tiền phí"), TransferMoneyInVCB_Data.InputDataInVCB.PAYMENT_BY_OTP_FEE + " VND");
	
	log.info("TC06_Step : Kiem tra loai giao dich");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Loại giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.TRANSFER_TYPE);
	
	log.info("TC06_Step : Kiem Tra noi dung giao dich");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Nội dung giao dịch").contains(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));
	
	log.info("TC06_Step : Click Back icon");
	transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");
	
	log.info("TC06_Step : Chon Tai Khoan");
	transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");
	
	log.info("TC06_Step : Click chon tai khoan nhan");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);
	
	log.info("TC06_Step : Click Tim Kiem");
	transReport.clickToDynamicButton(driver, "Tìm kiếm");

	log.info("TC06_Step : Kiem tra ngay tao giao dich hien thi");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));
	
	log.info("TC06_Step : Kiem tra thoi gian tao giao dich hiển thị");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));
	
	log.info("TC06_Step : Kiem tra noi dung giao dich hien thi");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));
	
	log.info("TC06_Step : Kiem tra so tien chuyen hien thi");
	verifyEquals(transReport.getTextInDynamicTransaction(driver, "1", "com.VCB:id/tvMoney"), ("+ " + addCommasToLong(changedMoney) + " VND"));
	
	log.info("TC06_Step : Click vao bao bao giao dich chi tiet");
	transReport.clickToDynamicTransaction(driver, "0", "com.VCB:id/tvDate");
	
	log.info("TC06_Step : Kiem tra ngay tao giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").contains(transferTime.split(" ")[0]));
	
	log.info("TC06_Step : Kiem tra thoi gian tao giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));
	
	log.info("TC06_Step : Kiem tra so giao dich hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Số lệnh giao dịch"), transactionNumber);
	
	log.info("TC06_Step :Kiem tra tai khoan trich no hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản/thẻ trích nợ"), TransferMoneyInVCB_Data.InputDataInVCB.USD_ACCOUNT);
	
	log.info("TC06_Step : Kiem tra tai khoan ghi co hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản ghi có"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);
	
	log.info("TC06_Step : Kiem tra so tien giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Số tiền giao dịch").contains(addCommasToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " USD"));
	
	log.info("TC06_Step : Kiem tra so tien quy doi hien thi ");

	verifyTrue(transReport.getDynamicTextInTextView(driver, "Số tiền quy đổi").contains(addCommasToLong(changedMoney) + " VND"));
	
	log.info("TC06_Step : Kiem tra ten nguoi huong hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tên người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);
	
	log.info("TC06_Step : Kiem tra phi giao dich hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Phí giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.COST[0]);
	
	log.info("TC06_Step :Kiem tra so tien phi hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Số tiền phí"), TransferMoneyInVCB_Data.InputDataInVCB.PAYMENT_BY_OTP_FEE + " VND");
	
	log.info("TC06_Step :Kiem tra noi dung giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Nội dung giao dịch").contains(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

	log.info("TC06_Step : Kiem tra loaigiao dich hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Loại giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.TRANSFER_TYPE);
	
	log.info("TC06_Step : Click quay lai");
	transferInVCB.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");
	
	log.info("TC06_Step : Click quay lai");
	transferInVCB.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");
	
	log.info("TC06_Step : Click quay lai");
	transferInVCB.navigateBack(driver);
}
	

@Test
public void TC_07_ChuyenTienNgayCoPhiGiaoDichNguoiNhanTraEURVaXacThucBangOTP() {
	
	log.info("TC05_Step 01: Click Chuyen tien trong VCB");
	homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");
	
	log.info("TC05_Step 02: Click chon tai khoan nguon");
	transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
	transferInVCB.clickToDynamicDropDown(driver,"Tài khoản nguồn");

	log.info("TC05_Step 03: Chon account 1");
	transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);
	
	log.info("TC05_Step 04: Lay so du kha dung tai khoan 1");
	String beforeBalanceOfAccount2 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
	double beforeBalanceAmountOfAccount2 = convertMoneyToDouble(beforeBalanceOfAccount2,"VND");
	
	log.info("TC05_Step : Click tai khoan nguon");
	transferInVCB.clickToDynamicDropDown(driver,"Tài khoản nguồn");
	
	log.info("TC05_Step : Chon USD account");
	transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.EUR_ACCOUNT);
	
	log.info("TC05_Step : Lay so du tai khoan USD");
	String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
	double  beforeBalanceAmountOfAccount1 = convertMoneyToDouble(beforeBalanceOfAccount1,"EUR");
	
	log.info("TC05_Step : Nhap tai khoan nhan");
	transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1, "Nhập/chọn tài khoản nhận VND");
	
	log.info("TC05_Step : Nhap so tien can chuyen");
	transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER, "Số tiền");
	
	log.info("TC05_Step : Lay ti gia quy doi");
	String exchangeRate = transferInVCB.getDynamicTextInTextView(driver, "Tỷ giá quy đổi tham khảo");
	exchangeRateUSD = exchangeRate.split(" ~ ");
	rate = convertMoneyToDouble(exchangeRateUSD[1], "VND");
	
	log.info("TC05_Step : Chon phuong thuc xac thuc");
	transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
	
	log.info("TC05_Step : Chon phi giao dich nguoi nhan tra");
	transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Người nhận trả");
	
	log.info("TC05_Step :  Nhap noi dung");
	transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Nội dung");
	
	log.info("TC05_Step : Click tiep tuc");
	transferInVCB.clickToDynamicButton(driver, "Tiếp tục");
	
	log.info("TC05_Step : Kiem tra hinh thuc chuyen tien hien thi");
	verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Hình thức chuyển tiền"), TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[0]);
	
	log.info("TC05_Step : Kiem tra tai khoan nguon hien thi");
	verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Tài khoản nguồn"), TransferMoneyInVCB_Data.InputDataInVCB.EUR_ACCOUNT);
	
	log.info("TC05_Step : Kiem tra tai khoan dich hien thi");
	verifyTrue(transferInVCB.getDynamicTextInTextView(driver, "Tài khoản đích / VND").contains(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1));
	
	log.info("TC05_Step : Kiem tra so tien chuyen hien thi");
	verifyTrue(transferInVCB.getDynamicTextInTextView(driver, "Số tiền (EUR)").contains(addCommasToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER)+"EUR"));
	
	log.info("TC05_Step : Kiem tra so tien phi hien thi"); 
	verifyTrue(transferInVCB.getDynamicTextInTextView(driver, "Số tiền phí").contains(TransferMoneyInVCB_Data.InputDataInVCB.EUR_PAYMENT_BY_OTP_FEE+" USD"));
	
	log.info("TC05_Step : Kiem tra Nguoi nhan tra hien thi");
	verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.InputDataInVCB.COST[1]));
	
	log.info("TC05_Step : Lay tien phi chuyen ");
	transferFee = transferInVCB.getDynamicTextInTextView(driver, "Số tiền phí");

	log.info("TC05_Step : Kiem tra noi dung hien thi");
	verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);
	
	log.info("TC05_Step : Chon phuong thuc xac thuc");
	transferInVCB.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");
	
	log.info("TC05_Step : Chon SMS OTP");
	transferInVCB.clickToDynamicButtonLinkOrLinkText(driver,"SMS OTP");
	
	log.info("TC05_Step : Click tiep tuc");
	transferInVCB.clickToDynamicButton(driver, "Tiếp tục");
	
	log.info("TC05_Step : Dien OTP");
	transferInVCB.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");
	
	log.info("TC05_Step : Click tiep tuc");
	transferInVCB.clickToDynamicButton(driver, "Tiếp tục");
	verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE));
	
	log.info("TC05_Step : Lay thoi gian tao");
	transferTime = transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE, "4");
	
	log.info("TC05_Step : Kiem tra so tien chuyen hien thi");
	verifyEquals(transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE, "3"),  addCommasToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER)+" EUR");
	
	log.info("TC05_Step : Kiem tra ten nguoi thu huong hien thi");
	verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Tên người thụ hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);
	
	log.info("TC05_Step : Kiem tra tai khoan dich hien thi");
	verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Tài khoản đích"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

	log.info("TC05_Step : Lay ma giao dich");
	transactionNumber = transferInVCB.getDynamicTextInTextView(driver, "Mã giao dịch");
	
	log.info("TC05_Step : Kiem tra noi dung hien thi");
	verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);
	
	log.info("TC05_Step : Click thuc hien giao dich moi");
	transferInVCB.clickToDynamicButton(driver, "Thực hiện giao dịch mới");
	
	log.info("TC05_Step : Click tai khoan nguon");
	transferInVCB.clickToDynamicDropDown(driver,"Tài khoản nguồn");
	
	log.info("TC05_Step :Click EUR account");
	transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.EUR_ACCOUNT);
	
	log.info("TC05_Step : Lay so du kha dung tai khoan EUR");
	String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
	double afterBalanceAmountOfAccount1 = convertMoneyToDouble(afterBalanceOfAccount1,"EUR");
	
	log.info("TC05_Step : Convert so tien chuyen");
	double transferMoney = convertMoneyToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER,"EUR");
	
	log.info("TC05_Step : Kiem tra so du tai khoan USD sau khi chuyen tien");
	verifyEquals(beforeBalanceAmountOfAccount1 - transferMoney, afterBalanceAmountOfAccount1);
	
	log.info("TC05_Step : Kiem tra tai khoan nguon");
	transferInVCB.clickToDynamicDropDown(driver,"Tài khoản nguồn");
	
	log.info("TC05_Step : Click chon tai khoan chuyen den");
	transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);
	
	log.info("TC05_Step : Lay so du kha dung tai khoan chuyen den");
	String afterBalanceOfAccount2 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
	double afterBalanceAmountOfAccount2 = convertMoneyToDouble(afterBalanceOfAccount2,"VND");
	
	log.info("TC05_Step : Kiem tra so du tai khoan duoc chuyen den");
	double transferFee = convertMoneyToDouble(TransferMoneyInVCB_Data.InputDataInVCB.PAYMENT_BY_OTP_FEE, "VND"); 
	verifyEquals(beforeBalanceAmountOfAccount2 + transferMoney*rate-transferFee  , afterBalanceAmountOfAccount2);

}
@Test
public void TC_08_KiemTraChiTietGiaoDichChuyenTienNgayCoPhiGiaoDichNguoiNhanTraEURVaXacThucBangOTP() throws InterruptedException {
	
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
	transReport.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.EUR_ACCOUNT);
	
	log.info("TC06_Step : CLick Tim Kiem");
	transReport.clickToDynamicButton(driver, "Tìm kiếm");
	
	log.info("TC06_Step : Kiem tra ngay tao giao dich hien thi");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));
	
	log.info("TC06_Step : Kiem tra thoi gian tao dao dich");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));
	
	log.info("TC06_Step : Kiem tra noi dung hien thi");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));
	
	log.info("TC06_Step : Kiem tra so tien chuyen hien thi");
	verifyEquals(transReport.getTextInDynamicTransaction(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " EUR"));
	
	log.info("TC06_Step : Click vao giao dich");
	transReport.clickToDynamicTransaction(driver, "0", "com.VCB:id/tvDate");
	
	log.info("TC06_Step : Kiem tra ngay giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").contains(transferTime.split(" ")[0]));
	
	log.info("TC06_Step : Kiem tra thoi gian tao giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));
	
	log.info("TC06_Step :Kiem tra so lenh giao dich");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Số lệnh giao dịch"), transactionNumber);
	
	log.info("TC06_Step : Kiem tra so tai khoan trich no");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản/thẻ trích nợ"), TransferMoneyInVCB_Data.InputDataInVCB.EUR_ACCOUNT);
	
	log.info("TC06_Step : Kiem tra so tai khoan ghi co");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản ghi có"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);
	
	log.info("TC06_Step : Kiem tra so tien giao dich hien thi ");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Số tiền giao dịch").equals(addCommasToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " EUR"));
	
	
	log.info("TC06_Step : Kiem tra so tien quy doi hien thi ");
	String changedMoney = convertMoneyToLong(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER, "EUR")*convertMoneyToLong(exchangeRateUSD[1], "VND") +"";
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Số tiền quy đổi").contains(addCommasToLong(changedMoney) + " VND"));
	
	log.info("TC06_Step : Kiem tra ten nguoi huong hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tên người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);
	
	log.info("TC06_Step : Kiem tra phi giao dich hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Phí giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.COST[1]);
	
	log.info("TC06_Step : Kiem tra so tien phi hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Số tiền phí"), TransferMoneyInVCB_Data.InputDataInVCB.PAYMENT_BY_OTP_FEE + " VND");
	
	log.info("TC06_Step : Kiem tra loai giao dich");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Loại giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.TRANSFER_TYPE);
	
	log.info("TC06_Step : Kiem Tra noi dung giao dich");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Nội dung giao dịch").contains(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));
	
	log.info("TC06_Step : Click Back icon");
	transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");
	
	log.info("TC06_Step : Chon Tai Khoan");
	transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");
	
	log.info("TC06_Step : Click chon tai khoan nhan");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);
	
	log.info("TC06_Step : Click Tim Kiem");
	transReport.clickToDynamicButton(driver, "Tìm kiếm");

	log.info("TC06_Step : Kiem tra ngay tao giao dich hien thi");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));
	
	log.info("TC06_Step : Kiem tra thoi gian tao giao dich hiển thị");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));
	
	log.info("TC06_Step : Kiem tra noi dung giao dich hien thi");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));
	
	log.info("TC06_Step : Kiem tra so tien chuyen hien thi");
	verifyEquals(transReport.getTextInDynamicTransaction(driver, "1", "com.VCB:id/tvMoney"), ("+ " + addCommasToLong(changedMoney) + " VND"));
	
	log.info("TC06_Step : Click vao bao bao giao dich chi tiet");
	transReport.clickToDynamicTransaction(driver, "0", "com.VCB:id/tvDate");
	
	log.info("TC06_Step : Kiem tra ngay tao giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").contains(transferTime.split(" ")[0]));
	
	log.info("TC06_Step : Kiem tra thoi gian tao giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));
	
	log.info("TC06_Step : Kiem tra so giao dich hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Số lệnh giao dịch"), transactionNumber);
	
	log.info("TC06_Step :Kiem tra tai khoan trich no hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản/thẻ trích nợ"), TransferMoneyInVCB_Data.InputDataInVCB.EUR_ACCOUNT);
	
	log.info("TC06_Step : Kiem tra tai khoan ghi co hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản ghi có"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);
	
	log.info("TC06_Step : Kiem tra so tien giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Số tiền giao dịch").contains(addCommasToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " EUR"));
	
	log.info("TC06_Step : Kiem tra so tien quy doi hien thi ");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Số tiền quy đổi").contains(addCommasToLong(changedMoney) + " VND"));
	
	log.info("TC06_Step : Kiem tra ten nguoi huong hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tên người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);
	
	log.info("TC06_Step : Kiem tra phi giao dich hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Phí giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.COST[1]);
	
	log.info("TC06_Step :Kiem tra so tien phi hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Số tiền phí"), TransferMoneyInVCB_Data.InputDataInVCB.PAYMENT_BY_OTP_FEE + " VND");
	
	log.info("TC06_Step :Kiem tra noi dung giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Nội dung giao dịch").contains(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

	log.info("TC06_Step : Kiem tra loaigiao dich hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Loại giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.TRANSFER_TYPE);
	
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
