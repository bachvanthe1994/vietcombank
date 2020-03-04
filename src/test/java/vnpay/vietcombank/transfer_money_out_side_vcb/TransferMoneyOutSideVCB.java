package vnpay.vietcombank.transfer_money_out_side_vcb;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import model.TransferOutSideVCB_Info;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.TransactionReportPageObject;
import pageObjects.TransferMoneyOutSideVCBPageObject;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransferMoneyQuick_Data;

public class TransferMoneyOutSideVCB extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferMoneyOutSideVCBPageObject transferMoneyOutSide;
	private TransactionReportPageObject transReport;
	private String transferTime;
	private String transactionNumber;
	long transferFee = 0;
	double transferFeeCurrentcy = 0;

	TransferOutSideVCB_Info info = new TransferOutSideVCB_Info(Account_Data.Valid_Account.ACCOUNT2, "01825909301", "Do Minh Duc", "NHTMCP Tien Phong", "500000", "Phí giao dịch người chuyển trả", "test", "SMS OTP");
	TransferOutSideVCB_Info info1 = new TransferOutSideVCB_Info(Account_Data.Valid_Account.ACCOUNT2, "01825909301", "Do Minh Duc", "NHTMCP Tien Phong", "500000", "Phí giao dịch người nhận trả", "test", "SMS OTP");
	TransferOutSideVCB_Info info2 = new TransferOutSideVCB_Info(Account_Data.Valid_Account.EUR_ACCOUNT, "01825909301", "Do Minh Duc", "NHTMCP Tien Phong", "50", "Phí giao dịch người chuyển trả", "test", "SMS OTP");
	TransferOutSideVCB_Info info3 = new TransferOutSideVCB_Info(Account_Data.Valid_Account.EUR_ACCOUNT, "01825909301", "Do Minh Duc", "NHTMCP Tien Phong", "50", "Phí giao dịch người nhận trả", "test", "SMS OTP");
	
	TransferOutSideVCB_Info info4 = new TransferOutSideVCB_Info(Account_Data.Valid_Account.ACCOUNT2, "01825909301", "Do Minh Duc", "NHTMCP Tien Phong", "500000", "Phí giao dịch người chuyển trả", "test", "Mật khẩu đăng nhập");
	TransferOutSideVCB_Info info5 = new TransferOutSideVCB_Info(Account_Data.Valid_Account.ACCOUNT2, "01825909301", "Do Minh Duc", "NHTMCP Tien Phong", "500000", "Phí giao dịch người nhận trả", "test", "Mật khẩu đăng nhập");
	TransferOutSideVCB_Info info6 = new TransferOutSideVCB_Info(Account_Data.Valid_Account.EUR_ACCOUNT, "01825909301", "Do Minh Duc", "NHTMCP Tien Phong", "50", "Phí giao dịch người chuyển trả", "test", "Mật khẩu đăng nhập");
	TransferOutSideVCB_Info info7 = new TransferOutSideVCB_Info(Account_Data.Valid_Account.EUR_ACCOUNT, "01825909301", "Do Minh Duc", "NHTMCP Tien Phong", "50", "Phí giao dịch người nhận trả", "test", "Mật khẩu đăng nhập");
	
	TransferOutSideVCB_Info info8 = new TransferOutSideVCB_Info(Account_Data.Valid_Account.USD_ACCOUNT, "01825909301", "Do Minh Duc", "NHTMCP Tien Phong", "50", "Phí giao dịch người chuyển trả", "test", "SMS OTP");
	TransferOutSideVCB_Info info9 = new TransferOutSideVCB_Info(Account_Data.Valid_Account.USD_ACCOUNT, "01825909301", "Do Minh Duc", "NHTMCP Tien Phong", "50", "Phí giao dịch người nhận trả", "test", "Mật khẩu đăng nhập");
	TransferOutSideVCB_Info info10 = new TransferOutSideVCB_Info(Account_Data.Valid_Account.USD_ACCOUNT, "01825909301", "Do Minh Duc", "NHTMCP Tien Phong", "50", "Phí giao dịch người nhận trả", "test", "SMS OTP");
	TransferOutSideVCB_Info info11 = new TransferOutSideVCB_Info(Account_Data.Valid_Account.USD_ACCOUNT, "01825909301", "Do Minh Duc", "NHTMCP Tien Phong", "50", "Phí giao dịch người chuyển trả", "test", "Mật khẩu đăng nhập");
	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
		homePage = PageFactoryManager.getHomePageObject(driver);
		transferMoneyOutSide = PageFactoryManager.getTransferMoneyOutSideVCBPageObject(driver);
	}

	private long surplus, availableBalance, actualAvailableBalance;
	private double surplusCurrentcy, availableBalanceCurrentcy, actualAvailableBalanceCurrentcy;
	@Test
	public void TC_01_ChuyenTienLienNganHang_VND_CoPhiGiaoDichNguoiChuyenTraXacThucBangOTP() {
		log.info("TC_01_1_Click Chuyen tien toi ngan hang khac");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền tới ngân hàng khác");

		log.info("TC_01_2_Chon tai khoan nguon");
		transferMoneyOutSide.scrollUpToText(driver, "Tài khoản nguồn");
		transferMoneyOutSide.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyOutSide.scrollDownToText(driver, info.sourceAccount);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);
		
		surplus = transferMoneyOutSide.convertAvailableBalanceCurrentcyToLong(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));

		log.info("TC_01_3_Nhap tai khoan thu huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info.destinationAccount, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_01_4_Nhap ten nguoi huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info.name, "Tên người hưởng");

		log.info("TC_01_5_Chon ngan hang huong");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng hưởng");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info.destinationBank, "Tìm kiếm");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info.destinationBank);

		log.info("TC_01_6_Nhap so tien");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info.money, "Số tiền");

		log.info("TC_01_7_Chọn phí giao dịch");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Người chuyển trả");

		log.info("TC_01_7_Nhap noi dung chuyen tien");
		transferMoneyOutSide.inputToDynamicInputBoxByHeader(driver, info.note, "Thông tin giao dịch", "3");

		log.info("TC_01_8_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_9_Kiem tra man hinh xac nhan thong tin");
		log.info("TC_01_9_1_Kiem tra tai khoan nguon");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), info.sourceAccount);

		log.info("TC_01_9_2_Kiem tra tai khoan dich");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tài khoản đích/ VND"), info.destinationAccount);

		log.info("TC_01_9_3_Kiem tra ten nguoi huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), info.name);

		log.info("TC_01_9_4_Kiem tra tai khoan dich");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Ngân hàng hưởng"), info.destinationBank);

		log.info("TC_01_9_5_Kiem tra so tien");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số tiền"), String.format("%,d", Long.parseLong(info.money)) + " VND");

		log.info("TC_01_9_6_Kiem tra noi dung chuyen tien");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Nội dung"), info.note);

		log.info("TC_01_10_Chon phuong thuc xac thuc");
		transferMoneyOutSide.scrollDownToText(driver, "Chọn phương thức xác thực");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		transferFee = transferMoneyOutSide.convertAvailableBalanceCurrentcyToLong(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, info.authenticationMethod));
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info.authenticationMethod);
		
		log.info("TC_01_10_01_Kiem tra so tien phi");
//		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), String.format("%,d", transferFee) + " VND");

		log.info("TC_01_11_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");

		transferMoneyOutSide.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_01_12_Kiem tra man hinh Chuyen khoan thanh cong");
		log.info("TC_01_12_1_Kiem tra Chuyen khoan thanh cong");
		verifyTrue(transferMoneyOutSide.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY));

		log.info("TC_01_12_2_Kiem tra ten nguoi thu huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), info.name);

		log.info("TC_01_12_3_Kiem tra tai khoan dich");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tài khoản đích"), info.destinationAccount);

		log.info("TC_01_12_3_Kiem tra ngan hang huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Ngân hàng hưởng"), info.destinationBank);

		log.info("TC_01_12_5_Kiem tra noi dung");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Nội dung"), info.note);
		
		log.info("TC_01_12_5_Kiem tra nut Thuc hien giao dich moi");
		verifyTrue(transferMoneyOutSide.isDynamicButtonDisplayed(driver, "Thực hiện giao dịch mới"));

		log.info("TC_01_12_6_Lay ma giao dich");
		transferTime = transferMoneyOutSide.getDynamicTransferTimeAndMoney(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY, "4");
		transactionNumber = transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC_01_13_Click Thuc hien giao dich moi");
		transferMoneyOutSide.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_01_14_Kiem tra so du kha dung luc sau");
		transferMoneyOutSide.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);
		actualAvailableBalance = transferMoneyOutSide.convertAvailableBalanceCurrentcyToLong(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));
		availableBalance = transferMoneyOutSide.canculateAvailableBalances(surplus, Long.parseLong(info.money), transferFee);
		verifyEquals(actualAvailableBalance, availableBalance);
		
	}
	
	@Test
	public void TC_02_ChuyenTienLienNganHang_VND_CoPhiGiaoDichNguoiChuyenTraXacThucBangOTP_BaoCao() {
		log.info("TC_02_1: Click  nut Back");
		homePage.clickToDynamicBackIcon(driver, "Chuyển tiền tới NH khác qua TK");

		log.info("TC_02_2: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_02_3: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

		log.info("TC_02_4: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

		log.info("TC_02_5: Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền tới NH khác qua TK");

		log.info("TC_02_6: Click Chon Tai Khoan");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_02_7: Chon tai Khoan chuyen");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);

		log.info("TC_02_8: Click Tim Kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_02_9: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));

		log.info("TC_02_10: Kiem tra thoi gian tao dao dich");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_02_11: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(info.note));

		log.info("TC_02_12: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(info.money) + " VND"));

		log.info("TC_02_13: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_02_14: Kiem tra ngay giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch").contains(transferTime.split(" ")[0]));

		log.info("TC_02_15: Kiem tra thoi gian tao giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_02_16: Kiem tra thoi gian tao giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_02_17: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), info.sourceAccount);

		log.info("TC_02_18: Kiem tra so tai khoan ghi co");

		log.info("TC_02_19: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").contains(addCommasToLong(info.money) + " VND"));

		log.info("TC_02_21: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Phí giao dịch"), "Người chuyển trả");

		log.info("TC_02_22: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Loại giao dịch"), "Chuyển tiền tới NH khác qua TK");

		log.info("TC_02_23: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Nội dung giao dịch").contains(info.note));

		log.info("TC_02_24: Click  nut Back");
		transferMoneyOutSide.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_02_25: Click  nut Back");
		transferMoneyOutSide.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

		log.info("TC_02_26: Click  nut Home");
		transferMoneyOutSide.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@Test
	public void TC_03_ChuyenTienLienNganHang_VND_CoPhiGiaoDichNguoiNhanTraXacThucBangOTP() {
		log.info("TC_03_1_Click Chuyen tien toi ngan hang khac");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền tới ngân hàng khác");

		log.info("TC_03_2_Chon tai khoan nguon");
		transferMoneyOutSide.scrollUpToText(driver, "Tài khoản nguồn");
		transferMoneyOutSide.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyOutSide.scrollDownToText(driver, info1.sourceAccount);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info1.sourceAccount);
		surplus = transferMoneyOutSide.convertAvailableBalanceCurrentcyToLong(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));

		log.info("TC_03_3_Nhap tai khoan thu huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info1.destinationAccount, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_03_4_Nhap ten nguoi huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info1.name, "Tên người hưởng");

		log.info("TC_03_5_Chon ngan hang huong");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng hưởng");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info1.destinationBank, "Tìm kiếm");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info.destinationBank);

		log.info("TC_03_6_Nhap so tien");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info1.money, "Số tiền");

		log.info("TC_03_7_Chọn phí giao dịch");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Người nhận trả");

		log.info("TC_03_8_Nhap noi dung chuyen tien");
		transferMoneyOutSide.inputToDynamicInputBoxByHeader(driver, info1.note, "Thông tin giao dịch", "3");

		log.info("TC_03_9_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_03_10_Kiem tra man hinh xac nhan thong tin");
		log.info("TC_03_10_1_Kiem tra tai khoan nguon");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), info1.sourceAccount);

		log.info("TC_03_10_2_Kiem tra tai khoan dich");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tài khoản đích/ VND"), info1.destinationAccount);

		log.info("TC_03_10_3_Kiem tra ten nguoi huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), info1.name);

		log.info("TC_03_10_4_Kiem tra ngan hang dich");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Ngân hàng hưởng"), info1.destinationBank);

		log.info("TC_03_10_5_Kiem tra so tien");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số tiền"), String.format("%,d", Long.parseLong(info1.money)) + " VND");

		log.info("TC_03_10_6_Kiem tra noi dung chuyen tien");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Nội dung"), info1.note);

		log.info("TC_03_11_Chon phuong thuc xac thuc");
		transferMoneyOutSide.scrollDownToText(driver, "Chọn phương thức xác thực");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		transferFee = transferMoneyOutSide.convertAvailableBalanceCurrentcyToLong(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, info1.authenticationMethod));
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info1.authenticationMethod);

		log.info("TC_03_11_01_Kiem tra so tien phi");
//		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), String.format("%,d", transferFee) + " VND");

		log.info("TC_03_12_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");

		transferMoneyOutSide.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_03_13_Kiem tra man hinh Chuyen khoan thanh cong");
		log.info("TC_03_13_1_Kiem tra Chuyen khoan thanh cong");
		verifyTrue(transferMoneyOutSide.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY));

		log.info("TC_03_13_2_Kiem tra ten nguoi thu huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), info1.name);

		log.info("TC_03_13_3_Kiem tra tai khoan dich");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tài khoản đích"), info1.destinationAccount);

		log.info("TC_03_13_3_Kiem tra ngan hang huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Ngân hàng hưởng"), info1.destinationBank);

		log.info("TC_03_13_5_Kiem tra noi dung");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Nội dung"), info1.note);
		
		log.info("TC_03_13_5_Kiem tra nut Thuc hien giao dich moi");
		verifyTrue(transferMoneyOutSide.isDynamicButtonDisplayed(driver, "Thực hiện giao dịch mới"));

		log.info("TC_03_13_6_Lay ma giao dich");
		transferTime = transferMoneyOutSide.getDynamicTransferTimeAndMoney(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY, "4");
		transactionNumber = transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC_03_14_Click Thuc hien giao dich moi");
		transferMoneyOutSide.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_03_14_Kiem tra so du kha dung luc sau");
		transferMoneyOutSide.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info1.sourceAccount);
		actualAvailableBalance = transferMoneyOutSide.convertAvailableBalanceCurrentcyToLong(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));
		availableBalance = transferMoneyOutSide.canculateAvailableBalances(surplus, Long.parseLong(info1.money), 0);
		verifyEquals(actualAvailableBalance, availableBalance);

	}
	
	@Test
	public void TC_04_ChuyenTienLienNganHangCoPhiGiaoDichNguoiNhanTraXacThucBangOTP_BaoCao() {
		log.info("TC_04_1: Click  nut Back");
		homePage.clickToDynamicBackIcon(driver, "Chuyển tiền tới NH khác qua TK");

		log.info("TC_04_2: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_04_3: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

		log.info("TC_04_4: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

		log.info("TC_04_5: Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền tới NH khác qua TK");

		log.info("TC_04_6: Click Chon Tai Khoan");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_04_7: Chon tai Khoan chuyen");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, info1.sourceAccount);

		log.info("TC_04_8: Click Tim Kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_04_9: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));

		log.info("TC_04_10: Kiem tra thoi gian tao dao dich");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_04_11: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(info1.note));

		log.info("TC_04_12: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(info1.money) + " VND"));

		log.info("TC_04_13: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_04_14: Kiem tra ngay giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch").contains(transferTime.split(" ")[0]));

		log.info("TC_04_15: Kiem tra thoi gian tao giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_04_16: Kiem tra thoi gian tao giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_04_17: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), info1.sourceAccount);

		log.info("TC_04_18: Kiem tra so tai khoan ghi co");

		log.info("TC_04_19: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").contains(addCommasToLong(info1.money) + " VND"));

		log.info("TC_04_21: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Phí giao dịch"), "Người nhận trả");

		log.info("TC_04_22: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Loại giao dịch"), "Chuyển tiền tới NH khác qua TK");

		log.info("TC_04_23: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Nội dung giao dịch").contains(info1.note));

		log.info("TC_04_24: Click  nut Back");
		transferMoneyOutSide.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_04_25: Click  nut Back");
		transferMoneyOutSide.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

		log.info("TC_04_26: Click  nut Home");
		transferMoneyOutSide.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@Test
	public void TC_05_ChuyenTienLienNganHangNgoaite_EUR_CoPhiGiaoDichNguoiChuyenTraXacThucBangOTP() {
		log.info("TC_05_1_Click Chuyen tien toi ngan hang khac");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền tới ngân hàng khác");

		log.info("TC_05_2_Chon tai khoan nguon");
		transferMoneyOutSide.scrollUpToText(driver, "Tài khoản nguồn");
		transferMoneyOutSide.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyOutSide.scrollDownToText(driver, info2.sourceAccount);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info2.sourceAccount);
		
		surplusCurrentcy = transferMoneyOutSide.convertAvailableBalanceCurrentcyToDouble(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));
		
		log.info("TC_05_3_Nhap tai khoan thu huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info2.destinationAccount, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_05_4_Nhap ten nguoi huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info2.name, "Tên người hưởng");

		log.info("TC_05_5_Chon ngan hang huong");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng hưởng");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info2.destinationBank, "Tìm kiếm");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info.destinationBank);

		log.info("TC_05_6_Nhap so tien");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info2.money, "Số tiền");

		log.info("TC_05_7_Chọn phí giao dịch");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Người chuyển trả");

		log.info("TC_05_8_Nhap noi dung chuyen tien");
		transferMoneyOutSide.inputToDynamicInputBoxByHeader(driver, info2.note, "Thông tin giao dịch", "3");

		log.info("TC_05_9_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_05_10_Kiem tra man hinh xac nhan thong tin");
		log.info("TC_05_10_1_Kiem tra tai khoan nguon");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), info2.sourceAccount);

		log.info("TC_05_10_2_Kiem tra tai khoan dich");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tài khoản đích/ VND"), info2.destinationAccount);

		log.info("TC_05_10_3_Kiem tra ten nguoi huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), info2.name);

		log.info("TC_05_10_4_Kiem tra ngan hang dich");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Ngân hàng hưởng"), info2.destinationBank);

		log.info("TC_05_10_5_Kiem tra so tien quy doi");
		String actualMoney = transferMoneyOutSide.getDynamicTextInTextViewLine2(driver, "Số tiền(EUR)");
		String expectMoney = transferMoneyOutSide.convertEURO_USDToVNeseMoney(info2.money, TransferMoneyQuick_Data.TransferQuick.EXCHANGE_EUR);
		verifyEquals(actualMoney, expectMoney);

		log.info("TC_05_10_6_Kiem tra so tien");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số tiền(EUR)"), String.format("%.2f", Double.parseDouble(info2.money)) + " EUR");

		log.info("TC_05_10_7_Kiem tra noi dung chuyen tien");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Nội dung"), info2.note);

		transferFeeCurrentcy = 0.32;
		
		log.info("TC_05_11_Chon phuong thuc xac thuc");
		transferMoneyOutSide.scrollDownToText(driver, "Chọn phương thức xác thực");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		transferFee = transferMoneyOutSide.convertAvailableBalanceCurrentcyToLong(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, info2.authenticationMethod));
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info2.authenticationMethod);
		
		log.info("TC_05_11_01_Kiem tra so tien phi");
//		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), transferFeeCurrentcy + " EUR");

		log.info("TC_05_12_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");

		transferMoneyOutSide.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_05_13_Kiem tra man hinh Chuyen khoan thanh cong");
		log.info("TC_05_13_1_Kiem tra Chuyen khoan thanh cong");
		verifyTrue(transferMoneyOutSide.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY));

		log.info("TC_05_13_2_Kiem tra ten nguoi thu huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), info2.name);

		log.info("TC_05_13_3_Kiem tra tai khoan dich");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tài khoản đích"), info2.destinationAccount);

		log.info("TC_05_13_3_Kiem tra ngan hang huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Ngân hàng hưởng"), info2.destinationBank);

		log.info("TC_05_13_5_Kiem tra noi dung");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Nội dung"), info2.note);
		
		log.info("TC_05_13_5_Kiem tra nut Thuc hien giao dich moi");
		verifyTrue(transferMoneyOutSide.isDynamicButtonDisplayed(driver, "Thực hiện giao dịch mới"));

		log.info("TC_05_13_6_Lay ma giao dich");
		transferTime = transferMoneyOutSide.getDynamicTransferTimeAndMoney(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY, "4");
		transactionNumber = transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC_05_14_Click Thuc hien giao dich moi");
		transferMoneyOutSide.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_05_14_Kiem tra so du kha dung luc sau");
		transferMoneyOutSide.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info2.sourceAccount);
		actualAvailableBalanceCurrentcy = transferMoneyOutSide.convertAvailableBalanceCurrentcyToDouble(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));
		availableBalanceCurrentcy = transferMoneyOutSide.canculateAvailableBalancesCurrentcy(surplusCurrentcy, Double.parseDouble(info2.money), transferFeeCurrentcy);
		verifyEquals(actualAvailableBalanceCurrentcy, availableBalanceCurrentcy);

	}

	@Test
	public void TC_06_ChuyenTienLienNganHangNgoaite_EUR_CoPhiGiaoDichNguoiChuyenTraXacThucBangOTP_BaoCao() {
		log.info("TC_06_1: Click  nut Back");
		homePage.clickToDynamicBackIcon(driver, "Chuyển tiền tới NH khác qua TK");

		log.info("TC_06_2: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_06_3: Click Bao cao giao dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

		log.info("TC_06_4: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

		log.info("TC_06_5: Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền tới NH khác qua TK");

		log.info("TC_06_6: Click Chon Tai Khoan");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_06_7: Chon tai Khoan chuyen");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, info2.sourceAccount);

		log.info("TC_06_8: Click Tim Kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_06_9: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));

		log.info("TC_06_10: Kiem tra thoi gian tao dao dich");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_06_11: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(info2.note));

		log.info("TC_06_12: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToDouble(info2.money) + " EUR"));

		log.info("TC_06_13: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_06_14: Kiem tra ngay giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch").contains(transferTime.split(" ")[0]));

		log.info("TC_06_15: Kiem tra thoi gian tao giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_06_16: Kiem tra thoi gian tao giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_06_17: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), info2.sourceAccount);

		log.info("TC_06_18: Kiem tra so tai khoan ghi co");

		log.info("TC_06_19: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").contains(addCommasToDouble(info2.money) + " EUR"));

		log.info("TC_06_20: Kiem tra so tien quy doi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền quy đổi").contains(transferMoneyOutSide.convertEURO_USDToVNeseMoney(info2.money, TransferMoneyQuick_Data.TransferQuick.EXCHANGE_EUR)));

		log.info("TC_06_21: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Phí giao dịch"), "Người chuyển trả");

		log.info("TC_06_22: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Loại giao dịch"), "Chuyển tiền tới NH khác qua TK");

		log.info("TC_06_23: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Nội dung giao dịch").contains(info2.note));

		log.info("TC_06_24: Click  nut Back");
		transferMoneyOutSide.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_06_25: Click  nut Back");
		transferMoneyOutSide.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

		log.info("TC_06_26: Click  nut Home");
		transferMoneyOutSide.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}
	

	
	@Test
	public void TC_07_ChuyenTienLienNganHangNgoaite_EUR_CoPhiGiaoDichNguoiNhanTraXacThucBangOTP() {
		log.info("TC_07_1_Click Chuyen tien toi ngan hang khac");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền tới ngân hàng khác");

		log.info("TC_07_2_Chon tai khoan nguon");
		transferMoneyOutSide.scrollUpToText(driver, "Tài khoản nguồn");
		transferMoneyOutSide.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info3.sourceAccount);

		surplusCurrentcy = transferMoneyOutSide.convertAvailableBalanceCurrentcyToDouble(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));

		log.info("TC_07_3_Nhap tai khoan thu huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info3.destinationAccount, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_07_4_Nhap ten nguoi huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info3.name, "Tên người hưởng");

		log.info("TC_07_5_Chon ngan hang huong");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng hưởng");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info3.destinationBank, "Tìm kiếm");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info.destinationBank);

		log.info("TC_07_6_Nhap so tien");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info3.money, "Số tiền");

		log.info("TC_07_7_Chọn phí giao dịch");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Người nhận trả");

		log.info("TC_07_8_Nhap noi dung chuyen tien");
		transferMoneyOutSide.inputToDynamicInputBoxByHeader(driver, info3.note, "Thông tin giao dịch", "3");

		log.info("TC_07_9_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_07_10_Kiem tra man hinh xac nhan thong tin");
		log.info("TC_07_10_1_Kiem tra tai khoan nguon");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), info3.sourceAccount);

		log.info("TC_07_10_2_Kiem tra tai khoan dich");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tài khoản đích/ VND"), info3.destinationAccount);

		log.info("TC_07_10_3_Kiem tra ten nguoi huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), info3.name);

		log.info("TC_07_10_4_Kiem tra ngan hang dich");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Ngân hàng hưởng"), info3.destinationBank);

		log.info("TC_07_10_5_Kiem tra so tien quy doi");
		String actualMoney = transferMoneyOutSide.getDynamicTextInTextViewLine2(driver, "Số tiền(EUR)");
		String expectMoney = transferMoneyOutSide.convertEURO_USDToVNeseMoney(info3.money, TransferMoneyQuick_Data.TransferQuick.EXCHANGE_EUR);
		verifyEquals(actualMoney, expectMoney);

		log.info("TC_07_10_6_Kiem tra so tien");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số tiền(EUR)"), String.format("%.2f", Double.parseDouble(info2.money)) + " EUR");

		log.info("TC_07_10_7_Kiem tra noi dung chuyen tien");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Nội dung"), info3.note);

		transferFeeCurrentcy = 0.32;
		
		log.info("TC_07_11_Chon phuong thuc xac thuc");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		transferFee = transferMoneyOutSide.convertAvailableBalanceCurrentcyToLong(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, info3.authenticationMethod));
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info3.authenticationMethod);
		
		log.info("TC_07_11_01_Kiem tra so tien phi");
//		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), transferFeeCurrentcy + " EUR");

		log.info("TC_07_12_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");

		transferMoneyOutSide.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_07_13_Kiem tra man hinh Chuyen khoan thanh cong");
		log.info("TC_07_13_1_Kiem tra Chuyen khoan thanh cong");
		verifyTrue(transferMoneyOutSide.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY));

		log.info("TC_07_13_2_Kiem tra ten nguoi thu huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), info3.name);

		log.info("TC_07_13_3_Kiem tra tai khoan dich");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tài khoản đích"), info3.destinationAccount);

		log.info("TC_07_13_3_Kiem tra ngan hang huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Ngân hàng hưởng"), info3.destinationBank);

		log.info("TC_07_13_5_Kiem tra noi dung");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Nội dung"), info3.note);
		
		log.info("TC_07_13_5_Kiem tra nut Thuc hien giao dich moi");
		verifyTrue(transferMoneyOutSide.isDynamicButtonDisplayed(driver, "Thực hiện giao dịch mới"));

		log.info("TC_07_13_6_Lay ma giao dich");
		transferTime = transferMoneyOutSide.getDynamicTransferTimeAndMoney(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY, "4");
		transactionNumber = transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC_07_14_Click Thuc hien giao dich moi");
		transferMoneyOutSide.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_07_14_Kiem tra so du kha dung luc sau");
		transferMoneyOutSide.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info3.sourceAccount);
		actualAvailableBalanceCurrentcy = transferMoneyOutSide.convertAvailableBalanceCurrentcyToDouble(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));
		availableBalanceCurrentcy = transferMoneyOutSide.canculateAvailableBalancesCurrentcy(surplusCurrentcy, Double.parseDouble(info3.money), 0);
		verifyEquals(actualAvailableBalanceCurrentcy, availableBalanceCurrentcy);
	
	}
	
	@Test
	public void TC_08_ChuyenTienLienNganHangNgoaite_EUR_CoPhiGiaoDichNguoiNhanTraXacThucBangOTP_BaoCao() {
		log.info("TC_08_1: Click  nut Back");
		homePage.clickToDynamicBackIcon(driver, "Chuyển tiền tới NH khác qua TK");

		log.info("TC_08_2: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_08_3: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

		log.info("TC_08_4: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

		log.info("TC_08_5: Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền tới NH khác qua TK");

		log.info("TC_08_6: Click Chon Tai Khoan");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_08_7: Chon tai Khoan chuyen");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, info3.sourceAccount);

		log.info("TC_08_8: Click Tim Kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_08_9: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));

		log.info("TC_08_10: Kiem tra thoi gian tao dao dich");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_08_11: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(info3.note));

		log.info("TC_08_12: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToDouble(info2.money) + " EUR"));

		log.info("TC_08_13: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_08_14: Kiem tra ngay giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch").contains(transferTime.split(" ")[0]));

		log.info("TC_08_15: Kiem tra thoi gian tao giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_08_16: Kiem tra thoi gian tao giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_08_17: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), info3.sourceAccount);

		log.info("TC_08_18: Kiem tra so tai khoan ghi co");

		log.info("TC_08_19: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").contains(addCommasToDouble(info3.money) + " EUR"));

		log.info("TC_08_20: Kiem tra so tien quy doi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền quy đổi").contains(transferMoneyOutSide.convertEURO_USDToVNeseMoney(info3.money, TransferMoneyQuick_Data.TransferQuick.EXCHANGE_EUR)));

		log.info("TC_08_21: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Phí giao dịch"), "Người nhận trả");

		log.info("TC_08_22: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Loại giao dịch"), "Chuyển tiền tới NH khác qua TK");

		log.info("TC_08_23: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Nội dung giao dịch").contains(info3.note));

		log.info("TC_08_24: Click  nut Back");
		transferMoneyOutSide.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_08_25: Click  nut Back");
		transferMoneyOutSide.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

		log.info("TC_08_26: Click  nut Home");
		transferMoneyOutSide.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@Test
	public void TC_09_ChuyenTienLienNganHang_VND_CoPhiGiaoDichNguoiChuyenTraXacThucBangMatKhau() {
		log.info("TC_09_1_Click Chuyen tien toi ngan hang khac");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền tới ngân hàng khác");

		log.info("TC_09_2_Chon tai khoan nguon");
		transferMoneyOutSide.scrollUpToText(driver, "Tài khoản nguồn");
		transferMoneyOutSide.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info4.sourceAccount);

		surplus = transferMoneyOutSide.convertAvailableBalanceCurrentcyToLong(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));

		log.info("TC_09_3_Nhap tai khoan thu huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info4.destinationAccount, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_09_4_Nhap ten nguoi huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info4.name, "Tên người hưởng");

		log.info("TC_09_5_Chon ngan hang huong");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng hưởng");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info4.destinationBank, "Tìm kiếm");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info.destinationBank);

		log.info("TC_09_6_Nhap so tien");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info4.money, "Số tiền");

		log.info("TC_09_7_Chọn phí giao dịch");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Người chuyển trả");

		log.info("TC_09_8_Nhap noi dung chuyen tien");
		transferMoneyOutSide.inputToDynamicInputBoxByHeader(driver, info4.note, "Thông tin giao dịch", "3");

		log.info("TC_09_9_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_09_10_Kiem tra man hinh xac nhan thong tin");
		log.info("TC_09_10_1_Kiem tra tai khoan nguon");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), info4.sourceAccount);

		log.info("TC_09_10_2_Kiem tra tai khoan dich");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tài khoản đích/ VND"), info4.destinationAccount);

		log.info("TC_09_10_3_Kiem tra ten nguoi huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), info4.name);

		log.info("TC_09_10_4_Kiem tra ngan hang huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Ngân hàng hưởng"), info4.destinationBank);

		log.info("TC_09_10_5_Kiem tra so tien");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số tiền"), String.format("%,d", Long.parseLong(info4.money)) + " VND");

		log.info("TC_09_10_6_Kiem tra noi dung");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Nội dung"), info4.note);

		log.info("TC_09_11_Chon phuong thuc xac thuc");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		transferFee = transferMoneyOutSide.convertAvailableBalanceCurrentcyToLong(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, info4.authenticationMethod));
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info4.authenticationMethod);
		
		log.info("TC_09_11_01_Kiem tra so tien phi");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), String.format("%,d", transferFee) + " VND");

		log.info("TC_09_12_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");

		transferMoneyOutSide.inputToPasswordConfirm(driver, LogIn_Data.Login_Account.NEW_PASSWORD);

		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_09_13_Kiem tra man hinh Chuyen khoan thanh cong");
		log.info("TC_09_13_1_Kiem tra Chuyen khoan thanh cong");
		verifyTrue(transferMoneyOutSide.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY));

		log.info("TC_09_13_2_Kiem tra ten nguoi thu huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), info4.name);

		log.info("TC_09_13_3_Kiem tra tai khoan dich");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tài khoản đích"), info4.destinationAccount);

		log.info("TC_09_13_3_Kiem tra ngan hang huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Ngân hàng hưởng"), info4.destinationBank);

		log.info("TC_09_13_5_Kiem tra noi dung");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Nội dung"), info4.note);
		
		log.info("TC_09_13_5_Kiem tra nut Thuc hien giao dich moi");
		verifyTrue(transferMoneyOutSide.isDynamicButtonDisplayed(driver, "Thực hiện giao dịch mới"));

		log.info("TC_09_13_6_Lay ma giao dich");
		transferTime = transferMoneyOutSide.getDynamicTransferTimeAndMoney(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY, "4");
		transactionNumber = transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC_09_14_Click Thuc hien giao dich moi");
		transferMoneyOutSide.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_09_14_Kiem tra so du kha dung luc sau");
		transferMoneyOutSide.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info4.sourceAccount);
		actualAvailableBalance = transferMoneyOutSide.convertAvailableBalanceCurrentcyToLong(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));
		availableBalance = transferMoneyOutSide.canculateAvailableBalances(surplus, Long.parseLong(info4.money), transferFee);
		verifyEquals(actualAvailableBalance, availableBalance);

	}
	
	@Test
	public void TC_10_ChuyenTienLienNganHang_VND_CoPhiGiaoDichNguoiChuyenTraXacThucBangMatKhau_BaoCao() {
		log.info("TC_10_1: Click  nut Back");
		homePage.clickToDynamicBackIcon(driver, "Chuyển tiền tới NH khác qua TK");

		log.info("TC_10_2: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_10_3: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

		log.info("TC_10_4: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

		log.info("TC_10_5: Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền tới NH khác qua TK");

		log.info("TC_10_6: Click Chon Tai Khoan");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_10_7: Chon tai Khoan chuyen");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, info4.sourceAccount);

		log.info("TC_10_8: Click Tim Kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_10_9: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));

		log.info("TC_10_10: Kiem tra thoi gian tao dao dich");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_10_11: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(info4.note));

		log.info("TC_10_12: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(info4.money) + " VND"));

		log.info("TC_10_13: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_10_14: Kiem tra ngay giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch").contains(transferTime.split(" ")[0]));

		log.info("TC_10_15: Kiem tra thoi gian tao giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_10_16: Kiem tra thoi gian tao giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_10_17: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), info4.sourceAccount);

		log.info("TC_10_18: Kiem tra so tai khoan ghi co");

		log.info("TC_10_19: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").contains(addCommasToLong(info4.money) + " VND"));

		log.info("TC_10_21: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Phí giao dịch"), "Người chuyển trả");

		log.info("TC_10_22: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Loại giao dịch"), "Chuyển tiền tới NH khác qua TK");

		log.info("TC_10_23: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Nội dung giao dịch").contains(info4.note));

		log.info("TC_10_24: Click  nut Back");
		transferMoneyOutSide.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_10_25: Click  nut Back");
		transferMoneyOutSide.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

		log.info("TC_10_26: Click  nut Home");
		transferMoneyOutSide.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}
	
	@Test
	public void TC_11_ChuyenTienLienNganHang_VND_CoPhiGiaoDichNguoiNhanTraXacThucBangMatKhau() {
		log.info("TC_11_1_Click Chuyen tien toi ngan hang khac");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền tới ngân hàng khác");

		log.info("TC_11_2_Chon tai khoan nguon");
		transferMoneyOutSide.scrollUpToText(driver, "Tài khoản nguồn");
		transferMoneyOutSide.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info5.sourceAccount);

		surplus = transferMoneyOutSide.convertAvailableBalanceCurrentcyToLong(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));

		log.info("TC_11_3_Nhap tai khoan thu huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info5.destinationAccount, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_11_4_Nhap ten nguoi huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info5.name, "Tên người hưởng");

		log.info("TC_11_5_Chon ngan hang huong");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng hưởng");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info5.destinationBank, "Tìm kiếm");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info.destinationBank);

		log.info("TC_11_6_Nhap so tien");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info5.money, "Số tiền");

		log.info("TC_11_7_Chọn phí giao dịch");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Người nhận trả");

		log.info("TC_11_8_Nhap noi dung chuyen tien");
		transferMoneyOutSide.inputToDynamicInputBoxByHeader(driver, info5.note, "Thông tin giao dịch", "3");

		log.info("TC_11_9_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_11_10_Kiem tra man hinh xac nhan thong tin");
		log.info("TC_11_10_1_Kiem tra tai khoan nguon");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), info5.sourceAccount);

		log.info("TC_11_10_2_Kiem tra tai khoan dich");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tài khoản đích/ VND"), info5.destinationAccount);

		log.info("TC_11_10_3_Kiem tra ten nguoi huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), info5.name);

		log.info("TC_11_10_4_Kiem tra ngan hang huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Ngân hàng hưởng"), info5.destinationBank);

		log.info("TC_11_10_5_Kiem tra so tien");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số tiền"), String.format("%,d", Long.parseLong(info5.money)) + " VND");

		log.info("TC_11_10_6_Kiem tra noi dung chuyen tien");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Nội dung"), info5.note);

		log.info("TC_11_11_Chon phuong thuc xac thuc");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		transferFee = transferMoneyOutSide.convertAvailableBalanceCurrentcyToLong(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, info5.authenticationMethod));
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info5.authenticationMethod);

		log.info("TC_11_11_01_Kiem tra so tien phi");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), String.format("%,d", transferFee) + " VND");

		log.info("TC_11_12_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");

		transferMoneyOutSide.inputToPasswordConfirm(driver, LogIn_Data.Login_Account.NEW_PASSWORD);

		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_11_13_Kiem tra man hinh Chuyen khoan thanh cong");
		log.info("TC_11_13_1_Kiem tra Chuyen khoan thanh cong");
		verifyTrue(transferMoneyOutSide.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY));

		log.info("TC_11_13_2_Kiem tra ten nguoi thu huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), info5.name);

		log.info("TC_11_13_3_Kiem tra tai khoan dich");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tài khoản đích"), info5.destinationAccount);

		log.info("TC_11_13_3_Kiem tra ngan hang huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Ngân hàng hưởng"), info5.destinationBank);

		log.info("TC_11_13_5_Kiem tra noi dung");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Nội dung"), info5.note);
		
		log.info("TC_11_13_5_Kiem tra nut Thuc hien giao dich moi");
		verifyTrue(transferMoneyOutSide.isDynamicButtonDisplayed(driver, "Thực hiện giao dịch mới"));

		log.info("TC_11_13_6_Lay ma giao dich");
		transferTime = transferMoneyOutSide.getDynamicTransferTimeAndMoney(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY, "4");
		transactionNumber = transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC_11_14_Click Thuc hien giao dich moi");
		transferMoneyOutSide.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_11_14_Kiem tra so du kha dung luc sau");
		transferMoneyOutSide.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info5.sourceAccount);
		actualAvailableBalance = transferMoneyOutSide.convertAvailableBalanceCurrentcyToLong(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));
		availableBalance = transferMoneyOutSide.canculateAvailableBalances(surplus, Long.parseLong(info5.money), 0);
		verifyEquals(actualAvailableBalance, availableBalance);

	}
	
	@Test
	public void TC_12_ChuyenTienLienNganHang_VND_CoPhiGiaoDichNguoiNhanTraXacThucBangMatKhau_BaoCao() {
		log.info("TC_12_1: Click  nut Back");
		homePage.clickToDynamicBackIcon(driver, "Chuyển tiền tới NH khác qua TK");

		log.info("TC_12_2: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_12_3: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

		log.info("TC_12_4: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

		log.info("TC_12_5: Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền tới NH khác qua TK");

		log.info("TC_12_6: Click Chon Tai Khoan");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_12_7: Chon tai Khoan chuyen");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, info5.sourceAccount);

		log.info("TC_12_8: Click Tim Kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_12_9: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));

		log.info("TC_12_10: Kiem tra thoi gian tao dao dich");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_12_11: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(info5.note));

		log.info("TC_12_12: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(info5.money) + " VND"));

		log.info("TC_12_13: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_12_14: Kiem tra ngay giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch").contains(transferTime.split(" ")[0]));

		log.info("TC_12_15: Kiem tra thoi gian tao giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_12_16: Kiem tra thoi gian tao giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_12_17: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), info5.sourceAccount);

		log.info("TC_12_18: Kiem tra so tai khoan ghi co");

		log.info("TC_12_19: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").contains(addCommasToLong(info5.money) + " VND"));

		log.info("TC_12_21: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Phí giao dịch"), "Người nhận trả");

		log.info("TC_12_22: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Loại giao dịch"), "Chuyển tiền tới NH khác qua TK");

		log.info("TC_12_23: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Nội dung giao dịch").contains(info5.note));

		log.info("TC_12_24: Click  nut Back");
		transferMoneyOutSide.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_12_25: Click  nut Back");
		transferMoneyOutSide.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

		log.info("TC_12_26: Click  nut Home");
		transferMoneyOutSide.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@Test
	public void TC_13_ChuyenTienLienNganHangCoPhiGiaoDichNguoiChuyenTraNgoaiTeXacThucBangMatKhau() {
		log.info("TC_13_1_Click Chuyen tien toi ngan hang khac");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền tới ngân hàng khác");

		log.info("TC_13_2_Chon tai khoan nguon");
		transferMoneyOutSide.scrollUpToText(driver, "Tài khoản nguồn");
		transferMoneyOutSide.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info6.sourceAccount);

		surplusCurrentcy = transferMoneyOutSide.convertAvailableBalanceCurrentcyToDouble(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));

		log.info("TC_13_3_Nhap tai khoan thu huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info6.destinationAccount, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_13_4_Nhap ten nguoi huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info6.name, "Tên người hưởng");

		log.info("TC_13_5_Chon ngan hang huong");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng hưởng");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info6.destinationBank, "Tìm kiếm");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info.destinationBank);

		log.info("TC_13_6_Nhap so tien");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info6.money, "Số tiền");

		log.info("TC_13_7_Chọn phí giao dịch");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Người chuyển trả");

		log.info("TC_13_8_Nhap noi dung chuyen tien");
		transferMoneyOutSide.inputToDynamicInputBoxByHeader(driver, info6.note, "Thông tin giao dịch", "3");

		log.info("TC_13_9_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_13_10_Kiem tra man hinh xac nhan thong tin");
		log.info("TC_13_10_1_Kiem tra tai khoan nguon");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), info6.sourceAccount);

		log.info("TC_13_10_2_Kiem tra tai khoan dich");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tài khoản đích/ VND"), info6.destinationAccount);

		log.info("TC_13_10_3_Kiem tra ten nguoi huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), info6.name);

		log.info("TC_13_10_4_Kiem tra ngan hang huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Ngân hàng hưởng"), info6.destinationBank);

		log.info("TC_13_10_5_Kiem tra so tien quy doi");
		String actualMoney = transferMoneyOutSide.getDynamicTextInTextViewLine2(driver, "Số tiền(EUR)");
		String expectMoney = transferMoneyOutSide.convertEURO_USDToVNeseMoney(info6.money, TransferMoneyQuick_Data.TransferQuick.EXCHANGE_EUR);
		verifyEquals(actualMoney, expectMoney);

		log.info("TC_13_10_6_Kiem tra so tien");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số tiền(EUR)"), String.format("%.2f", Double.parseDouble(info6.money)) + " EUR");

		log.info("TC_13_10_7_Kiem tra noi dung chuyen tien");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Nội dung"), info6.note);
		
		transferFeeCurrentcy = transferMoneyOutSide.convertAvailableBalanceCurrentcyToDouble(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số tiền phí"));

		log.info("TC_13_11_Chon phuong thuc xac thuc");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		transferFee = transferMoneyOutSide.convertAvailableBalanceCurrentcyToLong(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, info6.authenticationMethod));
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info6.authenticationMethod);
		
		log.info("TC_13_11_01_Kiem tra so tien phi");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), transferFeeCurrentcy + " EUR");

		log.info("TC_13_12_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");

		transferMoneyOutSide.inputToPasswordConfirm(driver, LogIn_Data.Login_Account.NEW_PASSWORD);

		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_13_13_Kiem tra man hinh Chuyen khoan thanh cong");
		log.info("TC_13_13_1_Kiem tra Chuyen khoan thanh cong");
		verifyTrue(transferMoneyOutSide.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY));

		log.info("TC_13_13_2_Kiem tra ten nguoi thu huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), info6.name);

		log.info("TC_13_13_3_Kiem tra tai khoan dich");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tài khoản đích"), info6.destinationAccount);

		log.info("TC_13_13_3_Kiem tra ngan hang huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Ngân hàng hưởng"), info6.destinationBank);

		log.info("TC_13_13_5_Kiem tra noi dung");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Nội dung"), info6.note);
		
		log.info("TC_13_13_5_Kiem tra nut Thuc hien giao dich moi");
		verifyTrue(transferMoneyOutSide.isDynamicButtonDisplayed(driver, "Thực hiện giao dịch mới"));

		log.info("TC_13_13_6_Lay ma giao dich");
		transferTime = transferMoneyOutSide.getDynamicTransferTimeAndMoney(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY, "4");
		transactionNumber = transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC_13_14_Click Thuc hien giao dich moi");
		transferMoneyOutSide.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_13_14_Kiem tra so du kha dung luc sau");
		transferMoneyOutSide.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info6.sourceAccount);
		actualAvailableBalanceCurrentcy = transferMoneyOutSide.convertAvailableBalanceCurrentcyToDouble(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));
		availableBalanceCurrentcy = transferMoneyOutSide.canculateAvailableBalancesCurrentcy(surplusCurrentcy, Double.parseDouble(info6.money), transferFeeCurrentcy);
		verifyEquals(actualAvailableBalanceCurrentcy, availableBalanceCurrentcy);
		
	}
	
	@Test
	public void TC_14_ChuyenTienLienNganHangCoPhiGiaoDichNguoiChuyenTraNgoaiTeXacThucBangMatKhau_BaoCao() {
		log.info("TC_14_1: Click  nut Back");
		homePage.clickToDynamicBackIcon(driver, "Chuyển tiền tới NH khác qua TK");

		log.info("TC_14_2: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_14_3: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

		log.info("TC_14_4: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

		log.info("TC_14_5: Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền tới NH khác qua TK");

		log.info("TC_14_6: Click Chon Tai Khoan");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_14_7: Chon tai Khoan chuyen");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, info6.sourceAccount);

		log.info("TC_14_8: Click Tim Kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_14_9: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));

		log.info("TC_14_10: Kiem tra thoi gian tao dao dich");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_14_11: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(info6.note));

		log.info("TC_14_12: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToDouble(info6.money) + " EUR"));

		log.info("TC_14_13: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_14_14: Kiem tra ngay giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch").contains(transferTime.split(" ")[0]));

		log.info("TC_14_15: Kiem tra thoi gian tao giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_14_16: Kiem tra thoi gian tao giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_14_17: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), info6.sourceAccount);

		log.info("TC_14_18: Kiem tra so tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản ghi có"), info6.destinationAccount);

		log.info("TC_14_19: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").contains(addCommasToDouble(info6.money) + " EUR"));

		log.info("TC_14_20: Kiem tra so tien quy doi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền quy đổi").contains(transferMoneyOutSide.convertEURO_USDToVNeseMoney(info6.money, TransferMoneyQuick_Data.TransferQuick.EXCHANGE_EUR)));

		log.info("TC_14_21: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Phí giao dịch"), "Người chuyển trả");

		log.info("TC_14_22: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Loại giao dịch"), "Chuyển tiền tới NH khác qua TK");

		log.info("TC_14_23: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Nội dung giao dịch").contains(info6.note));

		log.info("TC_14_24: Click  nut Back");
		transferMoneyOutSide.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_14_25: Click  nut Back");
		transferMoneyOutSide.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

		log.info("TC_14_26: Click  nut Home");
		transferMoneyOutSide.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@Test
	public void TC_15_ChuyenTienLienNganHangCoPhiGiaoDichNguoiNhanTraNgoaiTeXacThucBangMatKhau() {
		log.info("TC_15_1_Click Chuyen tien toi ngan hang khac");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền tới ngân hàng khác");

		log.info("TC_15_2_Chon tai khoan nguon");
		transferMoneyOutSide.scrollUpToText(driver, "Tài khoản nguồn");
		transferMoneyOutSide.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info7.sourceAccount);

		surplusCurrentcy = transferMoneyOutSide.convertAvailableBalanceCurrentcyToDouble(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));

		log.info("TC_15_3_Nhap tai khoan thu huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info7.destinationAccount, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_15_4_Nhap ten nguoi huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info7.name, "Tên người hưởng");

		log.info("TC_15_5_Chon ngan hang huong");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng hưởng");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info7.destinationBank, "Tìm kiếm");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info.destinationBank);

		log.info("TC_15_6_Nhap so tien");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info7.money, "Số tiền");

		log.info("TC_15_7_Chọn phí giao dịch");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Người nhận trả");

		log.info("TC_15_8_Nhap noi dung chuyen tien");
		transferMoneyOutSide.inputToDynamicInputBoxByHeader(driver, info7.note, "Thông tin giao dịch", "3");

		log.info("TC_15_9_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_15_10_Kiem tra man hinh xac nhan thong tin");
		log.info("TC_15_10_1_Kiem tra tai khoan nguon");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), info7.sourceAccount);

		log.info("TC_15_10_2_Kiem tra tai khoan dich");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tài khoản đích/ VND"), info7.destinationAccount);

		log.info("TC_15_10_3_Kiem tra ten nguoi huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), info7.name);

		log.info("TC_15_10_4_Kiem tra ngan hang huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Ngân hàng hưởng"), info7.destinationBank);

		log.info("TC_15_10_5_Kiem tra so tien quy doi");
		String actualMoney = transferMoneyOutSide.getDynamicTextInTextViewLine2(driver, "Số tiền(EUR)");
		String expectMoney = transferMoneyOutSide.convertEURO_USDToVNeseMoney(info6.money, TransferMoneyQuick_Data.TransferQuick.EXCHANGE_EUR);
		verifyEquals(actualMoney, expectMoney);

		log.info("TC_15_10_6_Kiem tra so tien");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số tiền(EUR)"), String.format("%.2f", Double.parseDouble(info7.money)) + " EUR");

		log.info("TC_15_10_7_Kiem tra noi dung chuyen tien");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Nội dung"), info7.note);
		
		transferFeeCurrentcy = transferMoneyOutSide.convertAvailableBalanceCurrentcyToDouble(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số tiền phí"));

		log.info("TC_15_11_Chon phuong thuc xac thuc");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		transferFee = transferMoneyOutSide.convertAvailableBalanceCurrentcyToLong(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, info7.authenticationMethod));
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info7.authenticationMethod);
		
		log.info("TC_15_11_01_Kiem tra so tien phi");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), transferFeeCurrentcy + " EUR");

		log.info("TC_15_12_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");

		transferMoneyOutSide.inputToPasswordConfirm(driver, LogIn_Data.Login_Account.NEW_PASSWORD);

		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_15_13_Kiem tra man hinh Chuyen khoan thanh cong");
		log.info("TC_15_13_1_Kiem tra Chuyen khoan thanh cong");
		verifyTrue(transferMoneyOutSide.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY));

		log.info("TC_15_13_2_Kiem tra ten nguoi thu huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), info7.name);

		log.info("TC_15_13_3_Kiem tra tai khoan dich");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tài khoản đích"), info7.destinationAccount);

		log.info("TC_15_13_3_Kiem tra ngan hang huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Ngân hàng hưởng"), info7.destinationBank);

		log.info("TC_15_13_5_Kiem tra noi dung");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Nội dung"), info7.note);
		
		log.info("TC_15_13_5_Kiem tra nut Thuc hien giao dich moi");
		verifyTrue(transferMoneyOutSide.isDynamicButtonDisplayed(driver, "Thực hiện giao dịch mới"));

		log.info("TC_15_13_6_Lay ma giao dich");
		transferTime = transferMoneyOutSide.getDynamicTransferTimeAndMoney(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY, "4");
		transactionNumber = transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC_15_14_Click Thuc hien giao dich moi");
		transferMoneyOutSide.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_15_14_Kiem tra so du kha dung luc sau");
		transferMoneyOutSide.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info7.sourceAccount);
		actualAvailableBalanceCurrentcy = transferMoneyOutSide.convertAvailableBalanceCurrentcyToDouble(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));
		availableBalanceCurrentcy = transferMoneyOutSide.canculateAvailableBalancesCurrentcy(surplusCurrentcy, Double.parseDouble(info7.money), 0);
		verifyEquals(actualAvailableBalanceCurrentcy, availableBalanceCurrentcy);
	
	}

	@Test
	public void TC_16_ChuyenTienLienNganHangCoPhiGiaoDichNguoiNhanTraNgoaiTeXacThucBangMatKhau_BaoCao() {
		log.info("TC_16_1: Click  nut Back");
		homePage.clickToDynamicBackIcon(driver, "Chuyển tiền tới NH khác qua TK");

		log.info("TC_16_2: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_16_3: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

		log.info("TC_16_4: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

		log.info("TC_16_5: Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền tới NH khác qua TK");

		log.info("TC_16_6: Click Chon Tai Khoan");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_16_7: Chon tai Khoan chuyen");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, info7.sourceAccount);

		log.info("TC_16_8: Click Tim Kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_16_9: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));

		log.info("TC_16_10: Kiem tra thoi gian tao dao dich");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_16_11: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(info7.note));

		log.info("TC_16_12: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToDouble(info7.money) + " EUR"));

		log.info("TC_16_13: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_16_14: Kiem tra ngay giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch").contains(transferTime.split(" ")[0]));

		log.info("TC_16_15: Kiem tra thoi gian tao giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_16_16: Kiem tra thoi gian tao giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_16_17: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), info7.sourceAccount);

		log.info("TC_16_18: Kiem tra so tai khoan ghi co");

		log.info("TC_16_19: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").contains(addCommasToDouble(info7.money) + " EUR"));

		log.info("TC_16_20: Kiem tra so tien quy doi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền quy đổi").contains(transferMoneyOutSide.convertEURO_USDToVNeseMoney(info7.money, TransferMoneyQuick_Data.TransferQuick.EXCHANGE_EUR)));

		log.info("TC_16_21: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Phí giao dịch"), "Người nhận trả");

		log.info("TC_16_22: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Loại giao dịch"), "Chuyển tiền tới NH khác qua TK");

		log.info("TC_16_23: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Nội dung giao dịch").contains(info7.note));

		log.info("TC_16_24: Click  nut Back");
		transferMoneyOutSide.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_16_25: Click  nut Back");
		transferMoneyOutSide.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

		log.info("TC_16_26: Click  nut Home");
		transferMoneyOutSide.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}
	
	@Test
	public void TC_17_ChuyenTienLienNganHang_USD_CoPhiGiaoDichNguoiChuyenTraXacThucBangOTP() {
		log.info("TC_17_1_Click Chuyen tien toi ngan hang khac");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền tới ngân hàng khác");

		log.info("TC_17_2_Chon tai khoan nguon");
		transferMoneyOutSide.scrollUpToText(driver, "Tài khoản nguồn");
		transferMoneyOutSide.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyOutSide.scrollDownToText(driver, info8.sourceAccount);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info8.sourceAccount);
		
		surplusCurrentcy = transferMoneyOutSide.convertAvailableBalanceCurrentcyToDouble(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));
		
		log.info("TC_17_3_Nhap tai khoan thu huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info8.destinationAccount, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_17_4_Nhap ten nguoi huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info8.name, "Tên người hưởng");

		log.info("TC_17_5_Chon ngan hang huong");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng hưởng");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info8.destinationBank, "Tìm kiếm");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info.destinationBank);

		log.info("TC_17_6_Nhap so tien");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info8.money, "Số tiền");

		log.info("TC_17_7_Chọn phí giao dịch");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Người chuyển trả");

		log.info("TC_17_8_Nhap noi dung chuyen tien");
		transferMoneyOutSide.inputToDynamicInputBoxByHeader(driver, info8.note, "Thông tin giao dịch", "3");

		log.info("TC_17_9_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_17_10_Kiem tra man hinh xac nhan thong tin");
		log.info("TC_17_10_1_Kiem tra tai khoan nguon");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), info8.sourceAccount);

		log.info("TC_17_10_2_Kiem tra tai khoan dich");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tài khoản đích/ VND"), info8.destinationAccount);

		log.info("TC_17_10_3_Kiem tra ten nguoi huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), info8.name);

		log.info("TC_17_10_4_Kiem tra ngan hang dich");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Ngân hàng hưởng"), info8.destinationBank);

		log.info("TC_17_10_5_Kiem tra so tien quy doi");
		String actualMoney = transferMoneyOutSide.getDynamicTextInTextViewLine2(driver, "Số tiền(USD)");
		String expectMoney = transferMoneyOutSide.convertEURO_USDToVNeseMoney(info8.money, TransferMoneyQuick_Data.TransferQuick.EXCHANGE_USD);
		verifyEquals(actualMoney, expectMoney);

		log.info("TC_17_10_6_Kiem tra so tien");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số tiền(USD)"), String.format("%.2f", Double.parseDouble(info8.money)) + " USD");

		log.info("TC_17_10_7_Kiem tra noi dung chuyen tien");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Nội dung"), info8.note);

		transferFeeCurrentcy = 0.33;
		
		log.info("TC_17_11_Chon phuong thuc xac thuc");
		transferMoneyOutSide.scrollDownToText(driver, "Chọn phương thức xác thực");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		transferFee = transferMoneyOutSide.convertAvailableBalanceCurrentcyToLong(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, info8.authenticationMethod));
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info8.authenticationMethod);
		
		log.info("TC_17_11_01_Kiem tra so tien phi");
//		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), transferFeeCurrentcy + " USD");

		log.info("TC_17_12_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");

		transferMoneyOutSide.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_17_13_Kiem tra man hinh Chuyen khoan thanh cong");
		log.info("TC_17_13_1_Kiem tra Chuyen khoan thanh cong");
		verifyTrue(transferMoneyOutSide.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY));

		log.info("TC_17_13_2_Kiem tra ten nguoi thu huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), info8.name);

		log.info("TC_17_13_3_Kiem tra tai khoan dich");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tài khoản đích"), info8.destinationAccount);

		log.info("TC_17_13_3_Kiem tra ngan hang huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Ngân hàng hưởng"), info8.destinationBank);

		log.info("TC_17_13_5_Kiem tra noi dung");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Nội dung"), info8.note);
		
		log.info("TC_17_13_5_Kiem tra nut Thuc hien giao dich moi");
		verifyTrue(transferMoneyOutSide.isDynamicButtonDisplayed(driver, "Thực hiện giao dịch mới"));

		log.info("TC_17_13_6_Lay ma giao dich");
		transferTime = transferMoneyOutSide.getDynamicTransferTimeAndMoney(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY, "4");
		transactionNumber = transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC_17_14_Click Thuc hien giao dich moi");
		transferMoneyOutSide.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_17_14_Kiem tra so du kha dung luc sau");
		transferMoneyOutSide.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info8.sourceAccount);
		actualAvailableBalanceCurrentcy = transferMoneyOutSide.convertAvailableBalanceCurrentcyToDouble(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));
		availableBalanceCurrentcy = transferMoneyOutSide.canculateAvailableBalancesCurrentcy(surplusCurrentcy, Double.parseDouble(info8.money), transferFeeCurrentcy);
		verifyEquals(actualAvailableBalanceCurrentcy, availableBalanceCurrentcy);

	}

	@Test
	public void TC_18_ChuyenTienLienNganHangNgoaite_USD_CoPhiGiaoDichNguoiChuyenTraXacThucBangOTP_BaoCao() {
		log.info("TC_18_1: Click  nut Back");
		homePage.clickToDynamicBackIcon(driver, "Chuyển tiền tới NH khác qua TK");

		log.info("TC_18_2: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_18_3: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

		log.info("TC_18_4: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

		log.info("TC_18_5: Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền tới NH khác qua TK");

		log.info("TC_18_6: Click Chon Tai Khoan");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_18_7: Chon tai Khoan chuyen");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, info8.sourceAccount);

		log.info("TC_18_8: Click Tim Kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_18_9: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));

		log.info("TC_18_10: Kiem tra thoi gian tao dao dich");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_18_11: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(info8.note));

		log.info("TC_18_12: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToDouble(info8.money) + " USD"));

		log.info("TC_18_13: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_18_14: Kiem tra ngay giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch").contains(transferTime.split(" ")[0]));

		log.info("TC_18_15: Kiem tra thoi gian tao giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_18_16: Kiem tra thoi gian tao giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_18_17: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), info8.sourceAccount);

		log.info("TC_18_18: Kiem tra so tai khoan ghi co");

		log.info("TC_18_19: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").contains(addCommasToDouble(info8.money) + " USD"));

		log.info("TC_18_20: Kiem tra so tien quy doi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền quy đổi").contains(transferMoneyOutSide.convertEURO_USDToVNeseMoney(info2.money, TransferMoneyQuick_Data.TransferQuick.EXCHANGE_USD)));

		log.info("TC_18_21: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Phí giao dịch"), "Người chuyển trả");

		log.info("TC_18_22: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Loại giao dịch"), "Chuyển tiền tới NH khác qua TK");

		log.info("TC_18_23: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Nội dung giao dịch").contains(info8.note));

		log.info("TC_18_24: Click  nut Back");
		transferMoneyOutSide.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_18_25: Click  nut Back");
		transferMoneyOutSide.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

		log.info("TC_18_26: Click  nut Home");
		transferMoneyOutSide.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}
	
	@Test
	public void TC_19_ChuyenTienLienNganHangCoPhiGiaoDichNguoiNhanTraNgoaiTe_USD_XacThucBangMatKhau() {
		log.info("TC_19_1_Click Chuyen tien toi ngan hang khac");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền tới ngân hàng khác");

		log.info("TC_19_2_Chon tai khoan nguon");
		transferMoneyOutSide.scrollUpToText(driver, "Tài khoản nguồn");
		transferMoneyOutSide.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info9.sourceAccount);

		surplusCurrentcy = transferMoneyOutSide.convertAvailableBalanceCurrentcyToDouble(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));

		log.info("TC_19_3_Nhap tai khoan thu huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info9.destinationAccount, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_19_4_Nhap ten nguoi huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info9.name, "Tên người hưởng");

		log.info("TC_19_5_Chon ngan hang huong");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng hưởng");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info9.destinationBank, "Tìm kiếm");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info.destinationBank);

		log.info("TC_19_6_Nhap so tien");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info9.money, "Số tiền");

		log.info("TC_19_7_Chọn phí giao dịch");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Người nhận trả");

		log.info("TC_19_8_Nhap noi dung chuyen tien");
		transferMoneyOutSide.inputToDynamicInputBoxByHeader(driver, info9.note, "Thông tin giao dịch", "3");

		log.info("TC_19_9_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_19_10_Kiem tra man hinh xac nhan thong tin");
		log.info("TC_19_10_1_Kiem tra tai khoan nguon");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), info9.sourceAccount);

		log.info("TC_19_10_2_Kiem tra tai khoan dich");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tài khoản đích/ VND"), info9.destinationAccount);

		log.info("TC_19_10_3_Kiem tra ten nguoi huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), info9.name);

		log.info("TC_19_10_4_Kiem tra ngan hang huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Ngân hàng hưởng"), info9.destinationBank);

		log.info("TC_19_10_5_Kiem tra so tien quy doi");
		String actualMoney = transferMoneyOutSide.getDynamicTextInTextViewLine2(driver, "Số tiền(USD)");
		String expectMoney = transferMoneyOutSide.convertEURO_USDToVNeseMoney(info6.money, TransferMoneyQuick_Data.TransferQuick.EXCHANGE_USD);
		verifyEquals(actualMoney, expectMoney);

		log.info("TC_19_10_6_Kiem tra so tien");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số tiền(USD)"), String.format("%.2f", Double.parseDouble(info9.money)) + " USD");

		log.info("TC_19_10_7_Kiem tra noi dung chuyen tien");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Nội dung"), info9.note);
		
		transferFeeCurrentcy = transferMoneyOutSide.convertAvailableBalanceCurrentcyToDouble(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số tiền phí"));

		log.info("TC_19_11_Chon phuong thuc xac thuc");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		transferFee = transferMoneyOutSide.convertAvailableBalanceCurrentcyToLong(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, info9.authenticationMethod));
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info9.authenticationMethod);
		
		log.info("TC_19_11_01_Kiem tra so tien phi");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), transferFeeCurrentcy + " USD");

		log.info("TC_19_12_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");

		transferMoneyOutSide.inputToPasswordConfirm(driver, LogIn_Data.Login_Account.NEW_PASSWORD);

		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_19_13_Kiem tra man hinh Chuyen khoan thanh cong");
		log.info("TC_19_13_1_Kiem tra Chuyen khoan thanh cong");
		verifyTrue(transferMoneyOutSide.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY));

		log.info("TC_19_13_2_Kiem tra ten nguoi thu huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), info9.name);

		log.info("TC_19_13_3_Kiem tra tai khoan dich");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tài khoản đích"), info9.destinationAccount);

		log.info("TC_19_13_3_Kiem tra ngan hang huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Ngân hàng hưởng"), info9.destinationBank);

		log.info("TC_19_13_5_Kiem tra noi dung");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Nội dung"), info9.note);
		
		log.info("TC_19_13_5_Kiem tra nut Thuc hien giao dich moi");
		verifyTrue(transferMoneyOutSide.isDynamicButtonDisplayed(driver, "Thực hiện giao dịch mới"));

		log.info("TC_19_13_6_Lay ma giao dich");
		transferTime = transferMoneyOutSide.getDynamicTransferTimeAndMoney(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY, "4");
		transactionNumber = transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC_19_14_Click Thuc hien giao dich moi");
		transferMoneyOutSide.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_19_14_Kiem tra so du kha dung luc sau");
		transferMoneyOutSide.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info9.sourceAccount);
		actualAvailableBalanceCurrentcy = transferMoneyOutSide.convertAvailableBalanceCurrentcyToDouble(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));
		availableBalanceCurrentcy = transferMoneyOutSide.canculateAvailableBalancesCurrentcy(surplusCurrentcy, Double.parseDouble(info9.money), 0);
		verifyEquals(actualAvailableBalanceCurrentcy, availableBalanceCurrentcy);
	
	}

	@Test
	public void TC_20_ChuyenTienLienNganHangCoPhiGiaoDichNguoiNhanTraNgoaiTe_USD_XacThucBangMatKhau_BaoCao() {
		log.info("TC_20_1: Click  nut Back");
		homePage.clickToDynamicBackIcon(driver, "Chuyển tiền tới NH khác qua TK");

		log.info("TC_20_2: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_20_3: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

		log.info("TC_20_4: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

		log.info("TC_20_5: Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền tới NH khác qua TK");

		log.info("TC_20_6: Click Chon Tai Khoan");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_20_7: Chon tai Khoan chuyen");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, info9.sourceAccount);

		log.info("TC_20_8: Click Tim Kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_20_9: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));

		log.info("TC_20_10: Kiem tra thoi gian tao dao dich");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_20_11: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(info9.note));

		log.info("TC_20_12: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToDouble(info9.money) + " USD"));

		log.info("TC_20_13: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_20_14: Kiem tra ngay giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch").contains(transferTime.split(" ")[0]));

		log.info("TC_20_15: Kiem tra thoi gian tao giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_20_16: Kiem tra thoi gian tao giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_20_17: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), info9.sourceAccount);

		log.info("TC_20_18: Kiem tra so tai khoan ghi co");

		log.info("TC_20_19: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").contains(addCommasToDouble(info9.money) + " USD"));

		log.info("TC_20_20: Kiem tra so tien quy doi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền quy đổi").contains(transferMoneyOutSide.convertEURO_USDToVNeseMoney(info7.money, TransferMoneyQuick_Data.TransferQuick.EXCHANGE_USD)));

		log.info("TC_20_21: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Phí giao dịch"), "Người nhận trả");

		log.info("TC_20_22: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Loại giao dịch"), "Chuyển tiền tới NH khác qua TK");

		log.info("TC_20_23: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Nội dung giao dịch").contains(info9.note));

		log.info("TC_20_24: Click  nut Back");
		transferMoneyOutSide.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_20_25: Click  nut Back");
		transferMoneyOutSide.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

		log.info("TC_20_26: Click  nut Home");
		transferMoneyOutSide.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}
	
	@Test
	public void TC_21_ChuyenTienLienNganHang_USD_CoPhiGiaoDichNguoiNhanTraXacThucBangOTP() {
		log.info("TC_21_1_Click Chuyen tien toi ngan hang khac");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền tới ngân hàng khác");

		log.info("TC_21_2_Chon tai khoan nguon");
		transferMoneyOutSide.scrollUpToText(driver, "Tài khoản nguồn");
		transferMoneyOutSide.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyOutSide.scrollDownToText(driver, info10.sourceAccount);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info10.sourceAccount);
		
		surplusCurrentcy = transferMoneyOutSide.convertAvailableBalanceCurrentcyToDouble(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));
		
		log.info("TC_21_3_Nhap tai khoan thu huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info10.destinationAccount, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_21_4_Nhap ten nguoi huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info10.name, "Tên người hưởng");

		log.info("TC_21_5_Chon ngan hang huong");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng hưởng");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info10.destinationBank, "Tìm kiếm");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info.destinationBank);

		log.info("TC_21_6_Nhap so tien");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info10.money, "Số tiền");

		log.info("TC_21_7_Chọn phí giao dịch");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Người nhận trả");

		log.info("TC_21_8_Nhap noi dung chuyen tien");
		transferMoneyOutSide.inputToDynamicInputBoxByHeader(driver, info10.note, "Thông tin giao dịch", "3");

		log.info("TC_21_9_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_21_10_Kiem tra man hinh xac nhan thong tin");
		log.info("TC_21_10_1_Kiem tra tai khoan nguon");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), info10.sourceAccount);

		log.info("TC_21_10_2_Kiem tra tai khoan dich");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tài khoản đích/ VND"), info10.destinationAccount);

		log.info("TC_21_10_3_Kiem tra ten nguoi huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), info10.name);

		log.info("TC_21_10_4_Kiem tra ngan hang dich");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Ngân hàng hưởng"), info10.destinationBank);

		log.info("TC_21_10_5_Kiem tra so tien quy doi");
		String actualMoney = transferMoneyOutSide.getDynamicTextInTextViewLine2(driver, "Số tiền(USD)");
		String expectMoney = transferMoneyOutSide.convertEURO_USDToVNeseMoney(info10.money, TransferMoneyQuick_Data.TransferQuick.EXCHANGE_USD);
		verifyEquals(actualMoney, expectMoney);

		log.info("TC_21_10_6_Kiem tra so tien");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số tiền(USD)"), String.format("%.2f", Double.parseDouble(info10.money)) + " USD");

		log.info("TC_21_10_7_Kiem tra noi dung chuyen tien");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Nội dung"), info10.note);

		transferFeeCurrentcy = 0.33;
		
		log.info("TC_21_11_Chon phuong thuc xac thuc");
		transferMoneyOutSide.scrollDownToText(driver, "Chọn phương thức xác thực");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		transferFee = transferMoneyOutSide.convertAvailableBalanceCurrentcyToLong(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, info10.authenticationMethod));
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info10.authenticationMethod);
		
		log.info("TC_21_11_01_Kiem tra so tien phi");
//		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), transferFeeCurrentcy + " USD");

		log.info("TC_21_12_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");

		transferMoneyOutSide.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_21_13_Kiem tra man hinh Chuyen khoan thanh cong");
		log.info("TC_21_13_1_Kiem tra Chuyen khoan thanh cong");
		verifyTrue(transferMoneyOutSide.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY));

		log.info("TC_21_13_2_Kiem tra ten nguoi thu huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), info10.name);

		log.info("TC_21_13_3_Kiem tra tai khoan dich");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tài khoản đích"), info10.destinationAccount);

		log.info("TC_21_13_3_Kiem tra ngan hang huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Ngân hàng hưởng"), info10.destinationBank);

		log.info("TC_21_13_5_Kiem tra noi dung");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Nội dung"), info10.note);
		
		log.info("TC_21_13_5_Kiem tra nut Thuc hien giao dich moi");
		verifyTrue(transferMoneyOutSide.isDynamicButtonDisplayed(driver, "Thực hiện giao dịch mới"));

		log.info("TC_21_13_6_Lay ma giao dich");
		transferTime = transferMoneyOutSide.getDynamicTransferTimeAndMoney(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY, "4");
		transactionNumber = transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC_21_14_Click Thuc hien giao dich moi");
		transferMoneyOutSide.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_21_14_Kiem tra so du kha dung luc sau");
		transferMoneyOutSide.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info10.sourceAccount);
		actualAvailableBalanceCurrentcy = transferMoneyOutSide.convertAvailableBalanceCurrentcyToDouble(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));
		availableBalanceCurrentcy = transferMoneyOutSide.canculateAvailableBalancesCurrentcy(surplusCurrentcy, Double.parseDouble(info8.money), 0);
		verifyEquals(actualAvailableBalanceCurrentcy, availableBalanceCurrentcy);

	}

	@Test
	public void TC_22_ChuyenTienLienNganHangNgoaite_USD_CoPhiGiaoDichNguoiChuyenTraXacThucBangOTP_BaoCao() {
		log.info("TC_22_1: Click  nut Back");
		homePage.clickToDynamicBackIcon(driver, "Chuyển tiền tới NH khác qua TK");

		log.info("TC_22_2: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_22_3: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

		log.info("TC_22_4: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

		log.info("TC_22_5: Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền tới NH khác qua TK");

		log.info("TC_22_6: Click Chon Tai Khoan");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_22_7: Chon tai Khoan chuyen");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, info10.sourceAccount);

		log.info("TC_22_8: Click Tim Kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_22_9: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));

		log.info("TC_22_10: Kiem tra thoi gian tao dao dich");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_22_11: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(info10.note));

		log.info("TC_22_12: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToDouble(info10.money) + " USD"));

		log.info("TC_22_13: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_22_14: Kiem tra ngay giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch").contains(transferTime.split(" ")[0]));

		log.info("TC_22_15: Kiem tra thoi gian tao giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_22_16: Kiem tra thoi gian tao giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_22_17: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), info10.sourceAccount);

		log.info("TC_22_18: Kiem tra so tai khoan ghi co");

		log.info("TC_22_19: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").contains(addCommasToDouble(info10.money) + " USD"));

		log.info("TC_22_20: Kiem tra so tien quy doi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền quy đổi").contains(transferMoneyOutSide.convertEURO_USDToVNeseMoney(info2.money, TransferMoneyQuick_Data.TransferQuick.EXCHANGE_USD)));

		log.info("TC_22_21: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Phí giao dịch"), "Người nhận trả");

		log.info("TC_22_22: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Loại giao dịch"), "Chuyển tiền tới NH khác qua TK");

		log.info("TC_22_23: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Nội dung giao dịch").contains(info10.note));

		log.info("TC_22_24: Click  nut Back");
		transferMoneyOutSide.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_22_25: Click  nut Back");
		transferMoneyOutSide.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

		log.info("TC_22_26: Click  nut Home");
		transferMoneyOutSide.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}
	
	@Test
	public void TC_23_ChuyenTienLienNganHangCoPhiGiaoDichNguoiChuyenTraNgoaiTe_USD_XacThucBangMatKhau() {
		log.info("TC_23_1_Click Chuyen tien toi ngan hang khac");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền tới ngân hàng khác");

		log.info("TC_23_2_Chon tai khoan nguon");
		transferMoneyOutSide.scrollUpToText(driver, "Tài khoản nguồn");
		transferMoneyOutSide.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info11.sourceAccount);

		surplusCurrentcy = transferMoneyOutSide.convertAvailableBalanceCurrentcyToDouble(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));

		log.info("TC_23_3_Nhap tai khoan thu huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info11.destinationAccount, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_23_4_Nhap ten nguoi huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info11.name, "Tên người hưởng");

		log.info("TC_23_5_Chon ngan hang huong");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng hưởng");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info11.destinationBank, "Tìm kiếm");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info.destinationBank);

		log.info("TC_23_6_Nhap so tien");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info11.money, "Số tiền");

		log.info("TC_23_7_Chọn phí giao dịch");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Người chuyển trả");

		log.info("TC_23_8_Nhap noi dung chuyen tien");
		transferMoneyOutSide.inputToDynamicInputBoxByHeader(driver, info11.note, "Thông tin giao dịch", "3");

		log.info("TC_23_9_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_23_10_Kiem tra man hinh xac nhan thong tin");
		log.info("TC_23_10_1_Kiem tra tai khoan nguon");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), info11.sourceAccount);

		log.info("TC_23_10_2_Kiem tra tai khoan dich");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tài khoản đích/ VND"), info11.destinationAccount);

		log.info("TC_23_10_3_Kiem tra ten nguoi huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), info11.name);

		log.info("TC_23_10_4_Kiem tra ngan hang huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Ngân hàng hưởng"), info11.destinationBank);

		log.info("TC_23_10_5_Kiem tra so tien quy doi");
		String actualMoney = transferMoneyOutSide.getDynamicTextInTextViewLine2(driver, "Số tiền(USD)");
		String expectMoney = transferMoneyOutSide.convertEURO_USDToVNeseMoney(info11.money, TransferMoneyQuick_Data.TransferQuick.EXCHANGE_USD);
		verifyEquals(actualMoney, expectMoney);

		log.info("TC_23_10_6_Kiem tra so tien");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số tiền(USD)"), String.format("%.2f", Double.parseDouble(info6.money)) + " USD");

		log.info("TC_23_10_7_Kiem tra noi dung chuyen tien");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Nội dung"), info11.note);
		
		transferFeeCurrentcy = transferMoneyOutSide.convertAvailableBalanceCurrentcyToDouble(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số tiền phí"));

		log.info("TC_23_11_Chon phuong thuc xac thuc");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		transferFee = transferMoneyOutSide.convertAvailableBalanceCurrentcyToLong(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, info11.authenticationMethod));
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info11.authenticationMethod);
		
		log.info("TC_23_11_01_Kiem tra so tien phi");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), transferFeeCurrentcy + " USD");

		log.info("TC_23_12_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");

		transferMoneyOutSide.inputToPasswordConfirm(driver, LogIn_Data.Login_Account.NEW_PASSWORD);

		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_23_13_Kiem tra man hinh Chuyen khoan thanh cong");
		log.info("TC_23_13_1_Kiem tra Chuyen khoan thanh cong");
		verifyTrue(transferMoneyOutSide.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY));

		log.info("TC_23_13_2_Kiem tra ten nguoi thu huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), info11.name);

		log.info("TC_23_13_3_Kiem tra tai khoan dich");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tài khoản đích"), info11.destinationAccount);

		log.info("TC_23_13_3_Kiem tra ngan hang huong");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Ngân hàng hưởng"), info11.destinationBank);

		log.info("TC_23_13_5_Kiem tra noi dung");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Nội dung"), info11.note);
		
		log.info("TC_23_13_5_Kiem tra nut Thuc hien giao dich moi");
		verifyTrue(transferMoneyOutSide.isDynamicButtonDisplayed(driver, "Thực hiện giao dịch mới"));

		log.info("TC_23_13_6_Lay ma giao dich");
		transferTime = transferMoneyOutSide.getDynamicTransferTimeAndMoney(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY, "4");
		transactionNumber = transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC_23_14_Click Thuc hien giao dich moi");
		transferMoneyOutSide.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_23_14_Kiem tra so du kha dung luc sau");
		transferMoneyOutSide.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info11.sourceAccount);
		actualAvailableBalanceCurrentcy = transferMoneyOutSide.convertAvailableBalanceCurrentcyToDouble(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));
		availableBalanceCurrentcy = transferMoneyOutSide.canculateAvailableBalancesCurrentcy(surplusCurrentcy, Double.parseDouble(info11.money), transferFeeCurrentcy);
		verifyEquals(actualAvailableBalanceCurrentcy, availableBalanceCurrentcy);
		
	}
	
	@Test
	public void TC_24_ChuyenTienLienNganHangCoPhiGiaoDichNguoiChuyenTraNgoaiTe_USD_XacThucBangMatKhau_BaoCao() {
		log.info("TC_24_1: Click  nut Back");
		homePage.clickToDynamicBackIcon(driver, "Chuyển tiền tới NH khác qua TK");

		log.info("TC_24_2: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_24_3: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

		log.info("TC_24_4: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

		log.info("TC_24_5: Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền tới NH khác qua TK");

		log.info("TC_24_6: Click Chon Tai Khoan");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_24_7: Chon tai Khoan chuyen");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, info11.sourceAccount);

		log.info("TC_24_8: Click Tim Kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_24_9: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));

		log.info("TC_24_10: Kiem tra thoi gian tao dao dich");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_24_11: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(info11.note));

		log.info("TC_24_12: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToDouble(info11.money) + " USD"));

		log.info("TC_24_13: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_24_14: Kiem tra ngay giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch").contains(transferTime.split(" ")[0]));

		log.info("TC_24_15: Kiem tra thoi gian tao giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_24_16: Kiem tra thoi gian tao giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_24_17: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), info11.sourceAccount);

		log.info("TC_24_18: Kiem tra so tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản ghi có"), info6.destinationAccount);

		log.info("TC_24_19: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").contains(addCommasToDouble(info6.money) + " USD"));

		log.info("TC_24_20: Kiem tra so tien quy doi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền quy đổi").contains(transferMoneyOutSide.convertEURO_USDToVNeseMoney(info6.money, TransferMoneyQuick_Data.TransferQuick.EXCHANGE_USD)));

		log.info("TC_24_21: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Phí giao dịch"), "Người chuyển trả");

		log.info("TC_24_22: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Loại giao dịch"), "Chuyển tiền tới NH khác qua TK");

		log.info("TC_24_23: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Nội dung giao dịch").contains(info6.note));

		log.info("TC_24_24: Click  nut Back");
		transferMoneyOutSide.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_24_25: Click  nut Back");
		transferMoneyOutSide.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

		log.info("TC_24_26: Click  nut Home");
		transferMoneyOutSide.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}
	
	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();
	}

}
