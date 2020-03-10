package vnpay.vietcombank.saving_online;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import model.SavingOnlineInfo;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.TransactionReportPageObject;
import pageObjects.saving_online.SavingOnlinePageObject;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.SavingOnline_Data;

public class SavingOnline_Folow extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private SavingOnlinePageObject savingOnline;
	private TransactionReportPageObject transReport;
	long transferFee = 0;
	double transferFeeCurrentcy = 0;
	private String transferTime;
	private String savingAccount;
	private String transactionNumber;
	
	SavingOnlineInfo info = new SavingOnlineInfo(Account_Data.Valid_Account.ACCOUNT2, "1 tháng", "2000000", "Lãi nhập gốc");
	SavingOnlineInfo info1 = new SavingOnlineInfo(Account_Data.Valid_Account.ACCOUNT2, "3 tháng", "2000000", "Lãi nhập gốc");
	SavingOnlineInfo info2 = new SavingOnlineInfo(Account_Data.Valid_Account.ACCOUNT2, "6 tháng", "2000000", "Lãi nhập gốc");
	SavingOnlineInfo info3 = new SavingOnlineInfo(Account_Data.Valid_Account.ACCOUNT2, "9 tháng", "2000000", "Lãi nhập gốc");
	SavingOnlineInfo info4 = new SavingOnlineInfo(Account_Data.Valid_Account.ACCOUNT2, "12 tháng", "2000000", "Lãi nhập gốc");
	SavingOnlineInfo info5 = new SavingOnlineInfo(Account_Data.Valid_Account.ACCOUNT2, "24 tháng", "2000000", "Lãi nhập gốc");
	
	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
		homePage = PageFactoryManager.getHomePageObject(driver);
		savingOnline = PageFactoryManager.getSavingOnlinePageObject(driver);
		
	}

	private long surplus, availableBalance, actualAvailableBalance;
	private double surplusCurrentcy, availableBalanceCurrentcy, actualAvailableBalanceCurrentcy;

	@Test
	public void TC_01_MoTaiKhoanTietKiem_VND_1Thang_LaiNhapGoc() {
		log.info("TC_01_1_Click Mo tai khoan tiet kiem");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Mở tài khoản tiết kiệm");

		log.info("TC_01_2_Chon so tai khoan");
		savingOnline.clickToDynamicDropDown(driver, "Số tài khoản");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);
		
		surplus = convertAvailableBalanceCurrentcyToLong(savingOnline.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));
		
		log.info("TC_01_3_Chon ky han gui");
		savingOnline.clickToDynamicDropDownListTextViewByHeader(driver, "Thông tin giao dịch", "1");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, info.term);
		
		log.info("TC_01_4_Nhap so tien gui");
		savingOnline.inputToDynamicInputBoxByHeader(driver, info.money, "Thông tin giao dịch", "2");
		
		log.info("TC_01_5_Chon hinh thuc chuyen tien");
		savingOnline.clickToDynamicDropDownListTextViewByHeader(driver, "Thông tin giao dịch", "3");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, info.formOfPayment);
		
		log.info("TC_01_6_Chon dong y tuan thu cam ket");
		savingOnline.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");
		
		log.info("TC_01_7_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_01_8_Kiem tra man hinh xac nhan thong tin");

		log.info("TC_01_8_1_Kiem tra tai khoan nguon");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), info.sourceAccount);

		log.info("TC_01_8_2_Kiem tra ky han gui");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Kỳ hạn gửi"), info.term);

		log.info("TC_01_8_3_Kiem tra lai suat");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Lãi suất"), "4.5%/Năm");

		log.info("TC_01_8_4_Kiem tra so tien gui");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Số tiền gửi"), addCommasToLong(info.money) + " VND");
		
		log.info("TC_01_8_5_Kiem tra hinh thuc tra lai");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Hình thức trả lãi"), info.formOfPayment);
		
		log.info("TC_01_09_Chon phuong thuc xac thuc");
		savingOnline.scrollDownToText(driver, "Phương thức xác thực");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		transferFee = convertAvailableBalanceCurrentcyToLong(savingOnline.getDynamicTextInTransactionDetail(driver, "SMS OTP"));
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");
		
		log.info("TC_01_10_Kiem tra so tien phi");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), addCommasToLong(transferFee + "") + " VND");
		
		log.info("TC_01_11_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, "Tiếp tục");
		
		savingOnline.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		savingOnline.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_01_12_Kiem tra man hinh Giao dich thanh cong");
		log.info("TC_01_12_1_Kiem tra Giao dich thanh cong");
		verifyTrue(savingOnline.isDynamicMessageAndLabelTextDisplayed(driver, SavingOnline_Data.SUCCESS_TRANSACTION));

		log.info("TC_01_12_6_Lay ma giao dich");
		transferTime = savingOnline.getDynamicTransferTimeAndMoney(driver, SavingOnline_Data.SUCCESS_TRANSACTION, "4");
		savingAccount = savingOnline.getDynamicTextInTransactionDetail(driver, "Số tài khoản tiết kiệm");
		transactionNumber = savingOnline.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC_01_13_Click Thuc hien giao dich moi");
		savingOnline.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_01_14_Kiem tra so du kha dung luc sau");
		savingOnline.clickToDynamicDropDown(driver, "Số tài khoản");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);
		actualAvailableBalance = convertAvailableBalanceCurrentcyToLong(savingOnline.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));
		availableBalance = canculateAvailableBalances(surplus, Long.parseLong(info.money), transferFee);
		verifyEquals(actualAvailableBalance, availableBalance);
		
	}
	
	@Test
	public void TC_02_MoTaiKhoanTietKiem_VND_1Thang_LaiNhapGoc_BaoCao() {
		log.info("TC_02_1: Click  nut Back");
		savingOnline.clickToDynamicBackIcon(driver, "Mở tài khoản tiết kiệm");

		log.info("TC_02_2: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_02_3: Click Bao Cao giao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

		log.info("TC_02_4: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

		log.info("TC_02_5: Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Mở tài khoản tiết kiệm");

		log.info("TC_02_6: Click Chon Tai Khoan");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_02_7: Chon tai Khoan chuyen");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);

		log.info("TC_02_8: Click Tim Kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_02_9: Kiem tra ngay tao giao dich hien thi");
		String reportTime = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
		verifyEquals(convertDateTimeIgnoreHHmmss(reportTime), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_02_10: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(info.money) + " VND"));

		log.info("TC_02_11: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_02_12: Kiem tra thoi gian tao giao dich hien thi");
		reportTime = transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch");
		verifyEquals(convertDateTimeIgnoreHHmmss(reportTime), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_02_13: Kiem tra so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_02_14: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), info.sourceAccount);

		log.info("TC_02_15: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").contains(addCommasToLong(info.money) + " VND"));

		log.info("TC_02_16: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Loại giao dịch"), "Mở tài khoản tiết kiệm");
		
		log.info("TC_02_17: Kiem tra noi dung giao dich");
		String expectContent = "MBVCB." + transactionNumber + ".Mo TK tiet kiem tu TK " + info.sourceAccount + " " + convertVietNameseStringToString(info.term);
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Nội dung giao dịch"), expectContent);

		log.info("TC_02_18: Click  nut Back");
		savingOnline.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_02_19: Click  nut Back");
		savingOnline.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

		log.info("TC_02_20: Click  nut Home");
		savingOnline.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
		
	}
	
	@Test
	public void TC_03_TatToanTaiKhoanTietKiem_VND_1Thang_LaiNhapGoc() {
		log.info("TC_03_1_Click Tat toan tai khoan tiet kiem");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Tất toán tài khoản tiết kiệm");

		log.info("TC_03_2_Chon so tai khoan tiet kiem");
		savingOnline.clickToDynamicDropDownInSavingOnline("Tài khoản tiết kiệm");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, savingAccount);
		
		log.info("TC_03_3_Chon tai khoan dich");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, "Chọn tài khoản đích");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);
		
		surplus = convertAvailableBalanceCurrentcyToLong(savingOnline.getDynamicTextAvailableBalanceInSavingOnline("Số dư khả dụng"));
		
		log.info("TC_03_4_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_03_5_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton("Tiếp tục");
		
		log.info("TC_03_6_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, "Tiếp tục");
		
		savingOnline.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		savingOnline.clickToDynamicButton(driver, "Tiếp tục");
		
		transactionNumber = savingOnline.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");
		
		log.info("TC_05_13_Click Thuc hien giao dich moi");
		savingOnline.clickToDynamicButton(driver, "Thực hiện giao dịch mới");
		
		log.info("TC_03_3_Chon tai khoan dich");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, "Chọn tài khoản đích");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);
		
		actualAvailableBalance = convertAvailableBalanceCurrentcyToLong(savingOnline.getDynamicTextAvailableBalanceInSavingOnline("Số dư khả dụng"));
		availableBalance = canculateAvailableBalances(surplus, - Long.parseLong(info2.money), transferFee);
		verifyEquals(actualAvailableBalance, availableBalance);
		
	}
	
	@Test
	public void TC_04_TatToanTaiKhoanTietKiem_VND_1Thang_LaiNhapGoc_BaoCao() {
		log.info("TC_04_1: Click  nut Back");
		savingOnline.clickToDynamicBackIcon(driver, "Tất toán tài khoản tiết kiệm");

		log.info("TC_04_2: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_04_3: Click Bao Cao giao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

		log.info("TC_04_4: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

		log.info("TC_04_5: Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất toán tài khoản tiết kiệm");

		log.info("TC_04_6: Click Chon Tai Khoan");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_04_7: Chon tai Khoan chuyen");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);

		log.info("TC_04_8: Click Tim Kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_04_9: Kiem tra ngay tao giao dich hien thi");
		String reportTime = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
		verifyEquals(convertDateTimeIgnoreHHmmss(reportTime), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_04_10: Kiem tra so tien tat toan");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("+ " + addCommasToLong(info.money) + " VND"));

		log.info("TC_04_11: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_04_12: Kiem tra thoi gian tao giao dich hien thi");
		reportTime = transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch");
		verifyEquals(convertDateTimeIgnoreHHmmss(reportTime), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_04_13: Kiem tra so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_04_14: Kiem tra tai khoan/the trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), savingAccount);
		
		log.info("TC_04_14: Kiem tra so tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), info.sourceAccount);

		log.info("TC_04_15: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").contains(addCommasToLong(info.money) + " VND"));

		log.info("TC_04_16: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Loại giao dịch"), "Tất toán tài khoản tiết kiệm");
		
		log.info("TC_04_17: Kiem tra noi dung giao dich");
		String expectContent = "MBVCB." + transactionNumber + ".Tat toan TK tiet kiem tu TK " + savingAccount + " " + convertVietNameseStringToString(info.term);
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Nội dung giao dịch"), expectContent);

		log.info("TC_04_18: Click  nut Back");
		savingOnline.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_04_19: Click  nut Back");
		savingOnline.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

		log.info("TC_04_20: Click  nut Home");
		savingOnline.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
		
	}

	@Test
	public void TC_05_MoTaiKhoanTietKiem_VND_3Thang_LaiNhapGoc() {
		log.info("TC_05_1_Click Mo tai khoan tiet kiem");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Mở tài khoản tiết kiệm");

		log.info("TC_05_2_Chon so tai khoan");
		savingOnline.clickToDynamicDropDown(driver, "Số tài khoản");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, info1.sourceAccount);
		
		surplus = convertAvailableBalanceCurrentcyToLong(savingOnline.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));
		
		log.info("TC_05_3_Chon ky han gui");
		savingOnline.clickToDynamicDropDownListTextViewByHeader(driver, "Thông tin giao dịch", "1");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, info1.term);
		
		log.info("TC_05_4_Nhap so tien gui");
		savingOnline.inputToDynamicInputBoxByHeader(driver, info1.money, "Thông tin giao dịch", "2");
		
		log.info("TC_05_5_Chon hinh thuc chuyen tien");
		savingOnline.clickToDynamicDropDownListTextViewByHeader(driver, "Thông tin giao dịch", "3");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, info1.formOfPayment);
		
		log.info("TC_05_6_Chon dong y tuan thu cam ket");
		savingOnline.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");
		
		log.info("TC_05_7_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_05_8_Kiem tra man hinh xac nhan thong tin");

		log.info("TC_05_8_1_Kiem tra tai khoan nguon");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), info1.sourceAccount);

		log.info("TC_05_8_2_Kiem tra ky han gui");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Kỳ hạn gửi"), info1.term);

		log.info("TC_05_8_3_Kiem tra lai suat");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Lãi suất"), "4.5%/Năm");

		log.info("TC_05_8_4_Kiem tra so tien gui");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Số tiền gửi"), addCommasToLong(info1.money) + " VND");
		
		log.info("TC_05_8_5_Kiem tra hinh thuc tra lai");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Hình thức trả lãi"), info1.formOfPayment);
		
		log.info("TC_05_09_Chon phuong thuc xac thuc");
		savingOnline.scrollDownToText(driver, "Phương thức xác thực");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		transferFee = convertAvailableBalanceCurrentcyToLong(savingOnline.getDynamicTextInTransactionDetail(driver, "SMS OTP"));
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");
		
		log.info("TC_05_10_Kiem tra so tien phi");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), addCommasToLong(transferFee + "") + " VND");
		
		log.info("TC_05_11_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, "Tiếp tục");
		
		savingOnline.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		savingOnline.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_05_12_Kiem tra man hinh Giao dich thanh cong");
		log.info("TC_05_12_1_Kiem tra Giao dich thanh cong");
		verifyTrue(savingOnline.isDynamicMessageAndLabelTextDisplayed(driver, SavingOnline_Data.SUCCESS_TRANSACTION));

		log.info("TC_05_12_6_Lay ma giao dich");
		transferTime = savingOnline.getDynamicTransferTimeAndMoney(driver, SavingOnline_Data.SUCCESS_TRANSACTION, "4");
		savingAccount = savingOnline.getDynamicTextInTransactionDetail(driver, "Số tài khoản tiết kiệm");
		transactionNumber = savingOnline.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC_05_13_Click Thuc hien giao dich moi");
		savingOnline.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_05_14_Kiem tra so du kha dung luc sau");
		savingOnline.clickToDynamicDropDown(driver, "Số tài khoản");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, info1.sourceAccount);
		actualAvailableBalance = convertAvailableBalanceCurrentcyToLong(savingOnline.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));
		availableBalance = canculateAvailableBalances(surplus, Long.parseLong(info1.money), transferFee);
		verifyEquals(actualAvailableBalance, availableBalance);
		
	}
	
	@Test
	public void TC_06_MoTaiKhoanTietKiem_VND_3Thang_LaiNhapGoc_BaoCao() {
		log.info("TC_06_1: Click  nut Back");
		savingOnline.clickToDynamicBackIcon(driver, "Mở tài khoản tiết kiệm");

		log.info("TC_06_2: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_06_3: Click Bao Cao giao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

		log.info("TC_06_4: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

		log.info("TC_06_5: Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Mở tài khoản tiết kiệm");

		log.info("TC_06_6: Click Chon Tai Khoan");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_06_7: Chon tai Khoan chuyen");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, info1.sourceAccount);

		log.info("TC_06_8: Click Tim Kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_06_9: Kiem tra ngay tao giao dich hien thi");
		String reportTime = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
		verifyEquals(convertDateTimeIgnoreHHmmss(reportTime), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_06_10: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(info1.money) + " VND"));

		log.info("TC_06_11: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_06_12: Kiem tra thoi gian tao giao dich hien thi");
		reportTime = transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch");
		verifyEquals(convertDateTimeIgnoreHHmmss(reportTime), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_06_13: Kiem tra so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_06_14: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), info1.sourceAccount);

		log.info("TC_06_15: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").contains(addCommasToLong(info1.money) + " VND"));

		log.info("TC_06_16: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Loại giao dịch"), "Mở tài khoản tiết kiệm");
		
		log.info("TC_06_17: Kiem tra noi dung giao dich");
		String expectContent = "MBVCB." + transactionNumber + ".Mo TK tiet kiem tu TK " + info1.sourceAccount + " " + convertVietNameseStringToString(info1.term);
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Nội dung giao dịch"), expectContent);

		log.info("TC_06_18: Click  nut Back");
		savingOnline.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_06_19: Click  nut Back");
		savingOnline.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

		log.info("TC_06_20: Click  nut Home");
		savingOnline.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
		
	}
	
	@Test
	public void TC_07_TatToanTaiKhoanTietKiem_VND_3Thang_LaiNhapGoc() {
		log.info("TC_07_1_Click Tat toan tai khoan tiet kiem");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Tất toán tài khoản tiết kiệm");

		log.info("TC_07_2_Chon so tai khoan tiet kiem");
		savingOnline.clickToDynamicDropDownInSavingOnline("Tài khoản tiết kiệm");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, savingAccount);
		
		log.info("TC_07_3_Chon tai khoan dich");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, "Chọn tài khoản đích");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, info1.sourceAccount);
		
		surplus = convertAvailableBalanceCurrentcyToLong(savingOnline.getDynamicTextAvailableBalanceInSavingOnline("Số dư khả dụng"));
		
		log.info("TC_07_4_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_07_5_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton("Tiếp tục");
		
		log.info("TC_07_6_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, "Tiếp tục");
		
		savingOnline.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		savingOnline.clickToDynamicButton(driver, "Tiếp tục");
		
		transactionNumber = savingOnline.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");
		
		log.info("TC_07_13_Click Thuc hien giao dich moi");
		savingOnline.clickToDynamicButton(driver, "Thực hiện giao dịch mới");
		
		log.info("TC_07_3_Chon tai khoan dich");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, "Chọn tài khoản đích");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, info1.sourceAccount);
		
		actualAvailableBalance = convertAvailableBalanceCurrentcyToLong(savingOnline.getDynamicTextAvailableBalanceInSavingOnline("Số dư khả dụng"));
		availableBalance = canculateAvailableBalances(surplus, - Long.parseLong(info2.money), transferFee);
		verifyEquals(actualAvailableBalance, availableBalance);
		
	}
	
	@Test
	public void TC_08_TatToanTaiKhoanTietKiem_VND_3Thang_LaiNhapGoc_BaoCao() {
		log.info("TC_08_1: Click  nut Back");
		savingOnline.clickToDynamicBackIcon(driver, "Tất toán tài khoản tiết kiệm");

		log.info("TC_08_2: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_08_3: Click Bao Cao giao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

		log.info("TC_08_4: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

		log.info("TC_08_5: Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất toán tài khoản tiết kiệm");

		log.info("TC_08_6: Click Chon Tai Khoan");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_08_7: Chon tai Khoan chuyen");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, info1.sourceAccount);

		log.info("TC_08_8: Click Tim Kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_08_9: Kiem tra ngay tao giao dich hien thi");
		String reportTime = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
		verifyEquals(convertDateTimeIgnoreHHmmss(reportTime), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_08_10: Kiem tra so tien tat toan");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("+ " + addCommasToLong(info1.money) + " VND"));

		log.info("TC_08_11: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_08_12: Kiem tra thoi gian tao giao dich hien thi");
		reportTime = transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch");
		verifyEquals(convertDateTimeIgnoreHHmmss(reportTime), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_08_13: Kiem tra so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_08_14: Kiem tra tai khoan/the trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), savingAccount);
		
		log.info("TC_08_14: Kiem tra so tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), info1.sourceAccount);

		log.info("TC_08_15: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").contains(addCommasToLong(info1.money) + " VND"));

		log.info("TC_08_16: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Loại giao dịch"), "Tất toán tài khoản tiết kiệm");
		
		log.info("TC_08_17: Kiem tra noi dung giao dich");
		String expectContent = "MBVCB." + transactionNumber + ".Tat toan TK tiet kiem tu TK " + savingAccount + " " + convertVietNameseStringToString(info1.term);
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Nội dung giao dịch"), expectContent);

		log.info("TC_08_18: Click  nut Back");
		savingOnline.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_08_19: Click  nut Back");
		savingOnline.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

		log.info("TC_08_20: Click  nut Home");
		savingOnline.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
		
	}
	
	@Test
	public void TC_09_MoTaiKhoanTietKiem_VND_6Thang_LaiNhapGoc() {
		log.info("TC_09_1_Click Mo tai khoan tiet kiem");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Mở tài khoản tiết kiệm");

		log.info("TC_09_2_Chon so tai khoan");
		savingOnline.clickToDynamicDropDown(driver, "Số tài khoản");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, info2.sourceAccount);
		
		surplus = convertAvailableBalanceCurrentcyToLong(savingOnline.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));
		
		log.info("TC_09_3_Chon ky han gui");
		savingOnline.clickToDynamicDropDownListTextViewByHeader(driver, "Thông tin giao dịch", "1");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, info2.term);
		
		log.info("TC_09_4_Nhap so tien gui");
		savingOnline.inputToDynamicInputBoxByHeader(driver, info2.money, "Thông tin giao dịch", "2");
		
		log.info("TC_09_5_Chon hinh thuc chuyen tien");
		savingOnline.clickToDynamicDropDownListTextViewByHeader(driver, "Thông tin giao dịch", "3");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, info2.formOfPayment);
		
		log.info("TC_09_6_Chon dong y tuan thu cam ket");
		savingOnline.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");
		
		log.info("TC_09_7_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_09_8_Kiem tra man hinh xac nhan thong tin");

		log.info("TC_09_8_1_Kiem tra tai khoan nguon");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), info2.sourceAccount);

		log.info("TC_09_8_2_Kiem tra ky han gui");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Kỳ hạn gửi"), info2.term);

		log.info("TC_09_8_3_Kiem tra lai suat");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Lãi suất"), "4.5%/Năm");

		log.info("TC_09_8_4_Kiem tra so tien gui");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Số tiền gửi"), addCommasToLong(info2.money) + " VND");
		
		log.info("TC_09_8_5_Kiem tra hinh thuc tra lai");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Hình thức trả lãi"), info2.formOfPayment);
		
		log.info("TC_09_09_Chon phuong thuc xac thuc");
		savingOnline.scrollDownToText(driver, "Phương thức xác thực");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		transferFee = convertAvailableBalanceCurrentcyToLong(savingOnline.getDynamicTextInTransactionDetail(driver, "SMS OTP"));
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");
		
		log.info("TC_09_10_Kiem tra so tien phi");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), addCommasToLong(transferFee + "") + " VND");
		
		log.info("TC_09_11_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, "Tiếp tục");
		
		savingOnline.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		savingOnline.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_09_12_Kiem tra man hinh Giao dich thanh cong");
		log.info("TC_09_12_1_Kiem tra Giao dich thanh cong");
		verifyTrue(savingOnline.isDynamicMessageAndLabelTextDisplayed(driver, SavingOnline_Data.SUCCESS_TRANSACTION));

		log.info("TC_09_12_6_Lay ma giao dich");
		transferTime = savingOnline.getDynamicTransferTimeAndMoney(driver, SavingOnline_Data.SUCCESS_TRANSACTION, "4");
		savingAccount = savingOnline.getDynamicTextInTransactionDetail(driver, "Số tài khoản tiết kiệm");
		transactionNumber = savingOnline.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC_09_13_Click Thuc hien giao dich moi");
		savingOnline.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_09_14_Kiem tra so du kha dung luc sau");
		savingOnline.clickToDynamicDropDown(driver, "Số tài khoản");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, info2.sourceAccount);
		actualAvailableBalance = convertAvailableBalanceCurrentcyToLong(savingOnline.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));
		availableBalance = canculateAvailableBalances(surplus, Long.parseLong(info2.money), transferFee);
		verifyEquals(actualAvailableBalance, availableBalance);
		
	}
	
	@Test
	public void TC_10_MoTaiKhoanTietKiem_VND_6Thang_LaiNhapGoc_BaoCao() {
		log.info("TC_10_1: Click  nut Back");
		savingOnline.clickToDynamicBackIcon(driver, "Mở tài khoản tiết kiệm");

		log.info("TC_10_2: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_10_3: Click Bao Cao giao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

		log.info("TC_10_4: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

		log.info("TC_10_5: Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Mở tài khoản tiết kiệm");

		log.info("TC_10_6: Click Chon Tai Khoan");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_10_7: Chon tai Khoan chuyen");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, info2.sourceAccount);

		log.info("TC_10_8: Click Tim Kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_10_9: Kiem tra ngay tao giao dich hien thi");
		String reportTime = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
		verifyEquals(convertDateTimeIgnoreHHmmss(reportTime), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_10_10: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(info2.money) + " VND"));

		log.info("TC_10_11: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_10_12: Kiem tra thoi gian tao giao dich hien thi");
		reportTime = transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch");
		verifyEquals(convertDateTimeIgnoreHHmmss(reportTime), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_10_13: Kiem tra so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_10_14: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), info2.sourceAccount);

		log.info("TC_10_15: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").contains(addCommasToLong(info2.money) + " VND"));

		log.info("TC_10_16: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Loại giao dịch"), "Mở tài khoản tiết kiệm");
		
		log.info("TC_10_17: Kiem tra noi dung giao dich");
		String expectContent = "MBVCB." + transactionNumber + ".Mo TK tiet kiem tu TK " + info2.sourceAccount + " " + convertVietNameseStringToString(info2.term);
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Nội dung giao dịch"), expectContent);

		log.info("TC_10_18: Click  nut Back");
		savingOnline.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_10_19: Click  nut Back");
		savingOnline.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

		log.info("TC_10_20: Click  nut Home");
		savingOnline.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
		
	}
	
	@Test
	public void TC_11_TatToanTaiKhoanTietKiem_VND_6Thang_LaiNhapGoc() {
		log.info("TC_11_1_Click Tat toan tai khoan tiet kiem");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Tất toán tài khoản tiết kiệm");

		log.info("TC_11_2_Chon so tai khoan tiet kiem");
		savingOnline.clickToDynamicDropDownInSavingOnline("Tài khoản tiết kiệm");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, savingAccount);
		
		log.info("TC_11_3_Chon tai khoan dich");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, "Chọn tài khoản đích");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, info2.sourceAccount);
		
		surplus = convertAvailableBalanceCurrentcyToLong(savingOnline.getDynamicTextAvailableBalanceInSavingOnline("Số dư khả dụng"));
		
		log.info("TC_11_4_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_11_5_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton("Tiếp tục");
		
		log.info("TC_11_6_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, "Tiếp tục");
		
		savingOnline.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		savingOnline.clickToDynamicButton(driver, "Tiếp tục");
		
		transactionNumber = savingOnline.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");
		
		log.info("TC_09_13_Click Thuc hien giao dich moi");
		savingOnline.clickToDynamicButton(driver, "Thực hiện giao dịch mới");
		
		log.info("TC_11_3_Chon tai khoan dich");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, "Chọn tài khoản đích");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, info2.sourceAccount);
		
		actualAvailableBalance = convertAvailableBalanceCurrentcyToLong(savingOnline.getDynamicTextAvailableBalanceInSavingOnline("Số dư khả dụng"));
		availableBalance = canculateAvailableBalances(surplus, - Long.parseLong(info2.money), transferFee);
		verifyEquals(actualAvailableBalance, availableBalance);
		
	}
	
	@Test
	public void TC_12_TatToanTaiKhoanTietKiem_VND_6Thang_LaiNhapGoc_BaoCao() {
		log.info("TC_12_1: Click  nut Back");
		savingOnline.clickToDynamicBackIcon(driver, "Tất toán tài khoản tiết kiệm");

		log.info("TC_12_2: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_12_3: Click Bao Cao giao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

		log.info("TC_12_4: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

		log.info("TC_12_5: Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất toán tài khoản tiết kiệm");

		log.info("TC_12_6: Click Chon Tai Khoan");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_12_7: Chon tai Khoan chuyen");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, info2.sourceAccount);

		log.info("TC_12_8: Click Tim Kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_12_9: Kiem tra ngay tao giao dich hien thi");
		String reportTime = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
		verifyEquals(convertDateTimeIgnoreHHmmss(reportTime), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_12_10: Kiem tra so tien tat toan");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("+ " + addCommasToLong(info2.money) + " VND"));

		log.info("TC_12_11: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_12_12: Kiem tra thoi gian tao giao dich hien thi");
		reportTime = transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch");
		verifyEquals(convertDateTimeIgnoreHHmmss(reportTime), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_12_13: Kiem tra so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_12_14: Kiem tra tai khoan/the trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), savingAccount);
		
		log.info("TC_12_14: Kiem tra so tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), info2.sourceAccount);

		log.info("TC_12_15: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").contains(addCommasToLong(info2.money) + " VND"));

		log.info("TC_12_16: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Loại giao dịch"), "Tất toán tài khoản tiết kiệm");
		
		log.info("TC_12_17: Kiem tra noi dung giao dich");
		String expectContent = "MBVCB." + transactionNumber + ".Tat toan TK tiet kiem tu TK " + savingAccount + " " + convertVietNameseStringToString(info2.term);
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Nội dung giao dịch"), expectContent);

		log.info("TC_12_18: Click  nut Back");
		savingOnline.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_12_19: Click  nut Back");
		savingOnline.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

		log.info("TC_12_20: Click  nut Home");
		savingOnline.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
		
	}
	
	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}