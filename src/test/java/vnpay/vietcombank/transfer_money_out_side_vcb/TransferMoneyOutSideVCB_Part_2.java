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

public class TransferMoneyOutSideVCB_Part_2 extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferMoneyOutSideVCBPageObject transferMoneyOutSide;
	private TransactionReportPageObject transReport;
	private String transferTime;
	private String transactionNumber;
	long transferFee = 0;
	double transferFeeCurrentcy = 0;
	
	TransferOutSideVCB_Info info6 = new TransferOutSideVCB_Info(Account_Data.Valid_Account.EUR_ACCOUNT, "01825909301", "Do Minh Duc", "NHTMCP Tien Phong", "10", "Phí giao dịch người chuyển trả", "test", "Mật khẩu đăng nhập");
	TransferOutSideVCB_Info info7 = new TransferOutSideVCB_Info(Account_Data.Valid_Account.EUR_ACCOUNT, "01825909301", "Do Minh Duc", "NHTMCP Tien Phong", "10", "Phí giao dịch người nhận trả", "test", "Mật khẩu đăng nhập");
	
	TransferOutSideVCB_Info info8 = new TransferOutSideVCB_Info(Account_Data.Valid_Account.USD_ACCOUNT, "01825909301", "Do Minh Duc", "NHTMCP Tien Phong", "10", "Phí giao dịch người chuyển trả", "test", "SMS OTP");
	TransferOutSideVCB_Info info9 = new TransferOutSideVCB_Info(Account_Data.Valid_Account.USD_ACCOUNT, "01825909301", "Do Minh Duc", "NHTMCP Tien Phong", "10", "Phí giao dịch người nhận trả", "test", "Mật khẩu đăng nhập");
	TransferOutSideVCB_Info info10 = new TransferOutSideVCB_Info(Account_Data.Valid_Account.USD_ACCOUNT, "01825909301", "Do Minh Duc", "NHTMCP Tien Phong", "10", "Phí giao dịch người nhận trả", "test", "SMS OTP");
	TransferOutSideVCB_Info info11 = new TransferOutSideVCB_Info(Account_Data.Valid_Account.USD_ACCOUNT, "01825909301", "Do Minh Duc", "NHTMCP Tien Phong", "10", "Phí giao dịch người chuyển trả", "test", "Mật khẩu đăng nhập");
	
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

	private double surplusCurrentcy, availableBalanceCurrentcy, actualAvailableBalanceCurrentcy;

	@Test
	public void TC_13_ChuyenTienLienNganHangCoPhiGiaoDichNguoiChuyenTraNgoaiTe_EUR_XacThucBangMatKhau() {
		log.info("TC_13_1_Click Chuyen tien toi ngan hang khac");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền tới ngân hàng khác");

		log.info("TC_13_2_Chon tai khoan nguon");
		transferMoneyOutSide.scrollUpToText(driver, "Tài khoản nguồn");
		transferMoneyOutSide.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info6.sourceAccount);
		transferMoneyOutSide.scrollUpToText(driver, "Tài khoản nguồn");
		surplusCurrentcy = convertAvailableBalanceCurrentcyToDouble(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));

		log.info("TC_13_3_Nhap tai khoan thu huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info6.destinationAccount, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_13_4_Nhap ten nguoi huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info6.name, "Tên người hưởng");

		log.info("TC_13_5_Chon ngan hang huong");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng hưởng");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info6.destinationBank, "Tìm kiếm");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info6.destinationBank);

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
		String expectMoney = convertEURO_USDToVNeseMoney(info6.money, TransferMoneyQuick_Data.TransferQuick.EXCHANGE_EUR);
		verifyEquals(actualMoney, expectMoney);

		log.info("TC_13_10_6_Kiem tra so tien");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số tiền(EUR)"), addCommasToDouble(info6.money) + " EUR");

		log.info("TC_13_10_7_Kiem tra noi dung chuyen tien");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Nội dung"), info6.note);
		
		log.info("TC_13_11_Chon phuong thuc xac thuc");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		transferFee = convertAvailableBalanceCurrentcyToLong(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, info6.authenticationMethod));
		transferFeeCurrentcy = convertVNeseMoneyToEUROOrUSD(String.valueOf(transferFee), TransferMoneyQuick_Data.TransferQuick.EXCHANGE_EUR);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info6.authenticationMethod);
		
		log.info("TC_13_11_01_Kiem tra so tien phi");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), addCommasToLong(transferFee + "") + " VND");

		log.info("TC_13_12_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");

		transferMoneyOutSide.inputToDynamicPopupPasswordInput(driver, LogIn_Data.Login_Account.NEW_PASSWORD, "Tiếp tục");

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
		transferMoneyOutSide.scrollUpToText(driver, "Tài khoản nguồn");
		transferMoneyOutSide.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info6.sourceAccount);
		transferMoneyOutSide.scrollUpToText(driver, "Tài khoản nguồn");
		actualAvailableBalanceCurrentcy = convertAvailableBalanceCurrentcyToDouble(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));
		availableBalanceCurrentcy = canculateAvailableBalancesCurrentcy(surplusCurrentcy, Double.parseDouble(info6.money), transferFeeCurrentcy);
		verifyEquals(actualAvailableBalanceCurrentcy, availableBalanceCurrentcy);
		
	}
	
	@Test
	public void TC_14_ChuyenTienLienNganHangCoPhiGiaoDichNguoiChuyenTraNgoaiTe_EUR_XacThucBangMatKhau_BaoCao() {
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
		String reportTime1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
		verifyEquals(convertDateTimeIgnoreHHmmss(reportTime1), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_14_10: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(info6.note));

		log.info("TC_14_11: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToDouble(info6.money) + " EUR"));

		log.info("TC_14_12: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_14_13: Kiem tra ngay tao giao dich hien thi");
		String reportTime2 = transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch");
		verifyEquals(reportTime2, reportTime1);

		log.info("TC_14_14: Kiem tra thoi gian tao giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_14_15: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), info6.sourceAccount);

		log.info("TC_14_16: Kiem tra so tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản ghi có"), info6.destinationAccount);

		log.info("TC_14_17: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").contains(addCommasToDouble(info6.money) + " EUR"));

		log.info("TC_14_18: Kiem tra so tien quy doi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền quy đổi").contains(convertEURO_USDToVNeseMoney(info6.money, TransferMoneyQuick_Data.TransferQuick.EXCHANGE_EUR)));

		log.info("TC_14_19: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Phí giao dịch"), "Người chuyển trả");

		log.info("TC_14_20: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Loại giao dịch"), "Chuyển tiền tới NH khác qua TK");

		log.info("TC_14_21: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Nội dung giao dịch").contains(info6.note));

		log.info("TC_14_22: Click  nut Back");
		transferMoneyOutSide.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_14_23: Click  nut Back");
		transferMoneyOutSide.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

		log.info("TC_14_24: Click  nut Home");
		transferMoneyOutSide.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@Test
	public void TC_15_ChuyenTienLienNganHangCoPhiGiaoDichNguoiNhanTraNgoaiTe_EUR_XacThucBangMatKhau() {
		log.info("TC_15_1_Click Chuyen tien toi ngan hang khac");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền tới ngân hàng khác");

		log.info("TC_15_2_Chon tai khoan nguon");
		transferMoneyOutSide.scrollUpToText(driver, "Tài khoản nguồn");
		transferMoneyOutSide.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info7.sourceAccount);
		transferMoneyOutSide.scrollUpToText(driver, "Tài khoản nguồn");
		surplusCurrentcy = convertAvailableBalanceCurrentcyToDouble(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));

		log.info("TC_15_3_Nhap tai khoan thu huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info7.destinationAccount, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_15_4_Nhap ten nguoi huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info7.name, "Tên người hưởng");

		log.info("TC_15_5_Chon ngan hang huong");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng hưởng");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info7.destinationBank, "Tìm kiếm");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info7.destinationBank);

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
		String expectMoney = convertEURO_USDToVNeseMoney(info6.money, TransferMoneyQuick_Data.TransferQuick.EXCHANGE_EUR);
		verifyEquals(actualMoney, expectMoney);

		log.info("TC_15_10_6_Kiem tra so tien");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số tiền(EUR)"), addCommasToDouble(info7.money) + " EUR");

		log.info("TC_15_10_7_Kiem tra noi dung chuyen tien");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Nội dung"), info7.note);
		
		log.info("TC_15_11_Chon phuong thuc xac thuc");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		transferFee = 0;
		transferFeeCurrentcy = 0;
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info7.authenticationMethod);
		
		log.info("TC_15_11_01_Kiem tra so tien phi");
		verifyTrue(transferMoneyOutSide.isDynamicMessageAndLabelTextDisplayed(driver, "Người nhận trả"));

		log.info("TC_15_12_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");

		transferMoneyOutSide.inputToDynamicPopupPasswordInput(driver, LogIn_Data.Login_Account.NEW_PASSWORD, "Tiếp tục");

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
		transferMoneyOutSide.scrollUpToText(driver, "Tài khoản nguồn");
		transferMoneyOutSide.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info7.sourceAccount);
		transferMoneyOutSide.scrollUpToText(driver, "Tài khoản nguồn");
		actualAvailableBalanceCurrentcy = convertAvailableBalanceCurrentcyToDouble(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));
		availableBalanceCurrentcy = canculateAvailableBalancesCurrentcy(surplusCurrentcy, Double.parseDouble(info7.money), 0);
		verifyEquals(actualAvailableBalanceCurrentcy, availableBalanceCurrentcy);
	
	}

	@Test
	public void TC_16_ChuyenTienLienNganHangCoPhiGiaoDichNguoiNhanTraNgoaiTe_EUR_XacThucBangMatKhau_BaoCao() {
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
		String reportTime1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
		verifyEquals(convertDateTimeIgnoreHHmmss(reportTime1), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_16_11: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(info7.note));

		log.info("TC_16_12: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToDouble(info7.money) + " EUR"));

		log.info("TC_16_13: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_16_14: Kiem tra ngay tao giao dich hien thi");
		String reportTime2 = transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch");
		verifyEquals(reportTime2, reportTime1);

		log.info("TC_16_15: Kiem tra thoi gian tao giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_16_16: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), info7.sourceAccount);

		log.info("TC_16_17: Kiem tra so tai khoan ghi co");

		log.info("TC_16_18: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").contains(addCommasToDouble(info7.money) + " EUR"));

		log.info("TC_16_19: Kiem tra so tien quy doi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền quy đổi").contains(convertEURO_USDToVNeseMoney(info7.money, TransferMoneyQuick_Data.TransferQuick.EXCHANGE_EUR)));

		log.info("TC_16_20: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Phí giao dịch"), "Người nhận trả");

		log.info("TC_16_21: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Loại giao dịch"), "Chuyển tiền tới NH khác qua TK");

		log.info("TC_16_22: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Nội dung giao dịch").contains(info7.note));

		log.info("TC_16_23: Click  nut Back");
		transferMoneyOutSide.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_16_24: Click  nut Back");
		transferMoneyOutSide.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

		log.info("TC_16_25: Click  nut Home");
		transferMoneyOutSide.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}
	
	@Test
	public void TC_17_ChuyenTienLienNganHang_USD_CoPhiGiaoDichNguoiChuyenTraXacThucBangOTP() {
		log.info("TC_17_1_Click Chuyen tien toi ngan hang khac");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền tới ngân hàng khác");

		log.info("TC_17_2_Chon tai khoan nguon");
		transferMoneyOutSide.scrollUpToText(driver, "Tài khoản nguồn");
		transferMoneyOutSide.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info8.sourceAccount);
		transferMoneyOutSide.scrollUpToText(driver, "Tài khoản nguồn");
		surplusCurrentcy = convertAvailableBalanceCurrentcyToDouble(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));
		
		log.info("TC_17_3_Nhap tai khoan thu huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info8.destinationAccount, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_17_4_Nhap ten nguoi huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info8.name, "Tên người hưởng");

		log.info("TC_17_5_Chon ngan hang huong");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng hưởng");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info8.destinationBank, "Tìm kiếm");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info8.destinationBank);

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
		String expectMoney = convertEURO_USDToVNeseMoney(info8.money, TransferMoneyQuick_Data.TransferQuick.EXCHANGE_USD);
		verifyEquals(actualMoney, expectMoney);

		log.info("TC_17_10_6_Kiem tra so tien");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số tiền(USD)"), addCommasToDouble(info8.money) + " USD");

		log.info("TC_17_10_7_Kiem tra noi dung chuyen tien");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Nội dung"), info8.note);

		log.info("TC_17_11_Chon phuong thuc xac thuc");
		transferMoneyOutSide.scrollDownToText(driver, "Chọn phương thức xác thực");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		transferFee = convertAvailableBalanceCurrentcyToLong(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, info8.authenticationMethod));
		transferFeeCurrentcy = convertVNeseMoneyToEUROOrUSD(String.valueOf(transferFee), TransferMoneyQuick_Data.TransferQuick.EXCHANGE_USD);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info8.authenticationMethod);
		
		log.info("TC_17_11_01_Kiem tra so tien phi");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), addCommasToLong(transferFee + "") + " VND");

		log.info("TC_17_12_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");

		transferMoneyOutSide.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

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
		transferMoneyOutSide.scrollUpToText(driver, "Tài khoản nguồn");
		transferMoneyOutSide.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info8.sourceAccount);
		transferMoneyOutSide.scrollUpToText(driver, "Tài khoản nguồn");
		actualAvailableBalanceCurrentcy = convertAvailableBalanceCurrentcyToDouble(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));
		availableBalanceCurrentcy = canculateAvailableBalancesCurrentcy(surplusCurrentcy, Double.parseDouble(info8.money), transferFeeCurrentcy);
		verifyEquals(actualAvailableBalanceCurrentcy, availableBalanceCurrentcy);

	}

	@Test
	public void TC_18_ChuyenTienLienNganHangNgoaite_USD_CoPhiGiaoDichNguoiChuyenTraXacThucBangOTP_BaoCao() {
		log.info("TC_18_1: Click  nut Back");
		homePage.clickToDynamicBackIcon(driver, "Chuyển tiền tới NH khác qua TK");

		log.info("TC_18_2: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_18_3: Click Bao cao giao dich");
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
		String reportTime1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
		verifyEquals(convertDateTimeIgnoreHHmmss(reportTime1), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_18_10: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(info8.note));

		log.info("TC_18_11: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToDouble(info8.money) + " USD"));

		log.info("TC_18_12: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_18_13: Kiem tra ngay tao giao dich hien thi");
		String reportTime2 = transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch");
		verifyEquals(reportTime2, reportTime1);

		log.info("TC_18_14: Kiem tra thoi gian tao giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_18_15: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), info8.sourceAccount);

		log.info("TC_18_16: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").contains(addCommasToDouble(info8.money) + " USD"));

		log.info("TC_18_17: Kiem tra so tien quy doi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền quy đổi").contains(convertEURO_USDToVNeseMoney(info8.money, TransferMoneyQuick_Data.TransferQuick.EXCHANGE_USD)));

		log.info("TC_18_18: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Phí giao dịch"), "Người chuyển trả");

		log.info("TC_18_19: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Loại giao dịch"), "Chuyển tiền tới NH khác qua TK");

		log.info("TC_18_20: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Nội dung giao dịch").contains(info8.note));

		log.info("TC_18_21: Click  nut Back");
		transferMoneyOutSide.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_18_22: Click  nut Back");
		transferMoneyOutSide.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

		log.info("TC_18_23: Click  nut Home");
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
		transferMoneyOutSide.scrollUpToText(driver, "Tài khoản nguồn");
		surplusCurrentcy = convertAvailableBalanceCurrentcyToDouble(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));

		log.info("TC_19_3_Nhap tai khoan thu huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info9.destinationAccount, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_19_4_Nhap ten nguoi huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info9.name, "Tên người hưởng");

		log.info("TC_19_5_Chon ngan hang huong");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng hưởng");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info9.destinationBank, "Tìm kiếm");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info9.destinationBank);

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
		String expectMoney = convertEURO_USDToVNeseMoney(info6.money, TransferMoneyQuick_Data.TransferQuick.EXCHANGE_USD);
		verifyEquals(actualMoney, expectMoney);

		log.info("TC_19_10_6_Kiem tra so tien");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số tiền(USD)"), addCommasToDouble(info9.money) + " USD");

		log.info("TC_19_10_7_Kiem tra noi dung chuyen tien");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Nội dung"), info9.note);
		
		log.info("TC_19_11_Chon phuong thuc xac thuc");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		transferFee = 0;
		transferFeeCurrentcy = 0;
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info9.authenticationMethod);
		
		log.info("TC_19_11_01_Kiem tra so tien phi");
		verifyTrue(transferMoneyOutSide.isDynamicMessageAndLabelTextDisplayed(driver, "Người nhận trả"));

		log.info("TC_19_12_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");

		transferMoneyOutSide.inputToDynamicPopupPasswordInput(driver, LogIn_Data.Login_Account.NEW_PASSWORD, "Tiếp tục");

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
		transferMoneyOutSide.scrollUpToText(driver, "Tài khoản nguồn");
		transferMoneyOutSide.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info9.sourceAccount);
		transferMoneyOutSide.scrollUpToText(driver, "Tài khoản nguồn");
		actualAvailableBalanceCurrentcy = convertAvailableBalanceCurrentcyToDouble(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));
		availableBalanceCurrentcy = canculateAvailableBalancesCurrentcy(surplusCurrentcy, Double.parseDouble(info9.money), 0);
		verifyEquals(actualAvailableBalanceCurrentcy, availableBalanceCurrentcy);
	
	}

	@Test
	public void TC_20_ChuyenTienLienNganHangCoPhiGiaoDichNguoiNhanTraNgoaiTe_USD_XacThucBangMatKhau_BaoCao() {
		log.info("TC_20_1: Click  nut Back");
		homePage.clickToDynamicBackIcon(driver, "Chuyển tiền tới NH khác qua TK");

		log.info("TC_20_2: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_20_3: Click Bao cao giao dich");
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
		String reportTime1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
		verifyEquals(convertDateTimeIgnoreHHmmss(reportTime1), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_20_10: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(info9.note));

		log.info("TC_20_11: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToDouble(info9.money) + " USD"));

		log.info("TC_20_12: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_20_13: Kiem tra ngay tao giao dich hien thi");
		String reportTime2 = transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch");
		verifyEquals(reportTime2, reportTime1);

		log.info("TC_20_14: Kiem tra thoi gian tao giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_20_15: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), info9.sourceAccount);

		log.info("TC_20_16: Kiem tra so tai khoan ghi co");

		log.info("TC_20_17: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").contains(addCommasToDouble(info9.money) + " USD"));

		log.info("TC_20_18: Kiem tra so tien quy doi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền quy đổi").contains(convertEURO_USDToVNeseMoney(info9.money, TransferMoneyQuick_Data.TransferQuick.EXCHANGE_USD)));

		log.info("TC_20_19: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Phí giao dịch"), "Người nhận trả");

		log.info("TC_20_20: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Loại giao dịch"), "Chuyển tiền tới NH khác qua TK");

		log.info("TC_20_21: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Nội dung giao dịch").contains(info9.note));

		log.info("TC_20_22: Click  nut Back");
		transferMoneyOutSide.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_20_23: Click  nut Back");
		transferMoneyOutSide.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

		log.info("TC_20_24: Click  nut Home");
		transferMoneyOutSide.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}
	
	@Test
	public void TC_21_ChuyenTienLienNganHang_USD_CoPhiGiaoDichNguoiNhanTraXacThucBangOTP() {
		log.info("TC_21_1_Click Chuyen tien toi ngan hang khac");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền tới ngân hàng khác");

		log.info("TC_21_2_Chon tai khoan nguon");
		transferMoneyOutSide.scrollUpToText(driver, "Tài khoản nguồn");
		transferMoneyOutSide.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info10.sourceAccount);
		transferMoneyOutSide.scrollUpToText(driver, "Tài khoản nguồn");
		surplusCurrentcy = convertAvailableBalanceCurrentcyToDouble(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));
		
		log.info("TC_21_3_Nhap tai khoan thu huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info10.destinationAccount, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_21_4_Nhap ten nguoi huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info10.name, "Tên người hưởng");

		log.info("TC_21_5_Chon ngan hang huong");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng hưởng");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info10.destinationBank, "Tìm kiếm");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info10.destinationBank);

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
		String expectMoney = convertEURO_USDToVNeseMoney(info10.money, TransferMoneyQuick_Data.TransferQuick.EXCHANGE_USD);
		verifyEquals(actualMoney, expectMoney);

		log.info("TC_21_10_6_Kiem tra so tien");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số tiền(USD)"), addCommasToDouble(info10.money) + " USD");

		log.info("TC_21_10_7_Kiem tra noi dung chuyen tien");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Nội dung"), info10.note);

		log.info("TC_21_11_Chon phuong thuc xac thuc");
		transferMoneyOutSide.scrollDownToText(driver, "Chọn phương thức xác thực");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		transferFee = 0;
		transferFeeCurrentcy = 0;
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info10.authenticationMethod);
		
		log.info("TC_21_11_01_Kiem tra so tien phi");
		verifyTrue(transferMoneyOutSide.isDynamicMessageAndLabelTextDisplayed(driver, "Người nhận trả"));

		log.info("TC_21_12_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");

		transferMoneyOutSide.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

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
		transferMoneyOutSide.scrollUpToText(driver, "Tài khoản nguồn");
		transferMoneyOutSide.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info10.sourceAccount);
		transferMoneyOutSide.scrollUpToText(driver, "Tài khoản nguồn");
		actualAvailableBalanceCurrentcy = convertAvailableBalanceCurrentcyToDouble(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));
		availableBalanceCurrentcy = canculateAvailableBalancesCurrentcy(surplusCurrentcy, Double.parseDouble(info8.money), 0);
		verifyEquals(actualAvailableBalanceCurrentcy, availableBalanceCurrentcy);

	}

	@Test
	public void TC_22_ChuyenTienLienNganHangNgoaite_USD_CoPhiGiaoDichNguoiChuyenTraXacThucBangOTP_BaoCao() {
		log.info("TC_22_1: Click  nut Back");
		homePage.clickToDynamicBackIcon(driver, "Chuyển tiền tới NH khác qua TK");

		log.info("TC_22_2: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_22_3: Click Bao cao giao dich");
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
		String reportTime1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
		verifyEquals(convertDateTimeIgnoreHHmmss(reportTime1), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_22_10: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(info10.note));

		log.info("TC_22_11: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToDouble(info10.money) + " USD"));

		log.info("TC_22_12: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_22_13: Kiem tra ngay tao giao dich hien thi");
		String reportTime2 = transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch");
		verifyEquals(reportTime2, reportTime1);

		log.info("TC_22_14: Kiem tra thoi gian tao giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_22_15: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), info10.sourceAccount);

		log.info("TC_22_16: Kiem tra so tai khoan ghi co");

		log.info("TC_22_17: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").contains(addCommasToDouble(info10.money) + " USD"));

		log.info("TC_22_18: Kiem tra so tien quy doi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền quy đổi").contains(convertEURO_USDToVNeseMoney(info10.money, TransferMoneyQuick_Data.TransferQuick.EXCHANGE_USD)));

		log.info("TC_22_19: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Phí giao dịch"), "Người nhận trả");

		log.info("TC_22_20: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Loại giao dịch"), "Chuyển tiền tới NH khác qua TK");

		log.info("TC_22_21: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Nội dung giao dịch").contains(info10.note));

		log.info("TC_22_22: Click  nut Back");
		transferMoneyOutSide.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_22_23: Click  nut Back");
		transferMoneyOutSide.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

		log.info("TC_22_24: Click  nut Home");
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
		transferMoneyOutSide.scrollUpToText(driver, "Tài khoản nguồn");
		surplusCurrentcy = convertAvailableBalanceCurrentcyToDouble(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));

		log.info("TC_23_3_Nhap tai khoan thu huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info11.destinationAccount, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_23_4_Nhap ten nguoi huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info11.name, "Tên người hưởng");

		log.info("TC_23_5_Chon ngan hang huong");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng hưởng");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info11.destinationBank, "Tìm kiếm");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info11.destinationBank);

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
		String expectMoney = convertEURO_USDToVNeseMoney(info11.money, TransferMoneyQuick_Data.TransferQuick.EXCHANGE_USD);
		verifyEquals(actualMoney, expectMoney);

		log.info("TC_23_10_6_Kiem tra so tien");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số tiền(USD)"), addCommasToDouble(info11.money) + " USD");

		log.info("TC_23_10_7_Kiem tra noi dung chuyen tien");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Nội dung"), info11.note);
		
		log.info("TC_23_11_Chon phuong thuc xac thuc");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		transferFee = convertAvailableBalanceCurrentcyToLong(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, info11.authenticationMethod));
		transferFeeCurrentcy = convertVNeseMoneyToEUROOrUSD(String.valueOf(transferFee), TransferMoneyQuick_Data.TransferQuick.EXCHANGE_USD);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info11.authenticationMethod);
		
		log.info("TC_23_11_01_Kiem tra so tien phi");
		verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), addCommasToLong(transferFee + "") + " VND");

		log.info("TC_23_12_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");

		transferMoneyOutSide.inputToDynamicPopupPasswordInput(driver, LogIn_Data.Login_Account.NEW_PASSWORD, "Tiếp tục");

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
		transferMoneyOutSide.scrollUpToText(driver, "Tài khoản nguồn");
		transferMoneyOutSide.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info11.sourceAccount);
		transferMoneyOutSide.scrollUpToText(driver, "Tài khoản nguồn");
		actualAvailableBalanceCurrentcy = convertAvailableBalanceCurrentcyToDouble(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));
		availableBalanceCurrentcy = canculateAvailableBalancesCurrentcy(surplusCurrentcy, Double.parseDouble(info11.money), transferFeeCurrentcy);
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
		String reportTime1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
		verifyEquals(convertDateTimeIgnoreHHmmss(reportTime1), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_24_10: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(info11.note));

		log.info("TC_24_11: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToDouble(info11.money) + " USD"));

		log.info("TC_24_12: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_24_13: Kiem tra ngay tao giao dich hien thi");
		String reportTime2 = transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch");
		verifyEquals(reportTime2, reportTime1);

		log.info("TC_24_14: Kiem tra thoi gian tao giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_24_15: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), info11.sourceAccount);

		log.info("TC_24_16: Kiem tra so tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản ghi có"), info6.destinationAccount);

		log.info("TC_24_17: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").contains(addCommasToDouble(info6.money) + " USD"));

		log.info("TC_24_18: Kiem tra so tien quy doi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền quy đổi").contains(convertEURO_USDToVNeseMoney(info6.money, TransferMoneyQuick_Data.TransferQuick.EXCHANGE_USD)));

		log.info("TC_24_19: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Phí giao dịch"), "Người chuyển trả");

		log.info("TC_24_20: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Loại giao dịch"), "Chuyển tiền tới NH khác qua TK");

		log.info("TC_24_21: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Nội dung giao dịch").contains(info6.note));

		log.info("TC_24_22: Click  nut Back");
		transferMoneyOutSide.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_24_23: Click  nut Back");
		transferMoneyOutSide.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

		log.info("TC_24_24: Click  nut Home");
		transferMoneyOutSide.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}
	
	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();
	}

}
