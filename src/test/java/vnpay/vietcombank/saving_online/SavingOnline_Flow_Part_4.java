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

public class SavingOnline_Flow_Part_4 extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private SavingOnlinePageObject savingOnline;
	private TransactionReportPageObject transReport;
	long transferFee = 0;
	double transferFeeDouble = 0;
	double transferFeeCurrentcy = 0;
	private String transferTime;
	private String savingAccount;
	private String transactionNumber;
	String currentcy = "";

	SavingOnlineInfo info = new SavingOnlineInfo(Account_Data.Valid_Account.USD_ACCOUNT, "1 tháng", "100", "Lãi nhập gốc");
	SavingOnlineInfo info1 = new SavingOnlineInfo(Account_Data.Valid_Account.USD_ACCOUNT, "3 tháng", "100", "Lãi nhập gốc");

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		if (deviceType.contains("android")) {
			driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		} else if (deviceType.contains("ios")) {
			driver = openIOSApp(deviceName, udid, url);
		}
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
		homePage = PageFactoryManager.getHomePageObject(driver);
		savingOnline = PageFactoryManager.getSavingOnlinePageObject(driver);
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		
	}

	private double surplus, availableBalance, actualAvailableBalance;
	private long surplusLong, availableBalanceLong, actualAvailableBalanceLong;

	@Test
	public void TC_01_MoTaiKhoanTietKiem_USD_1Thang_LaiNhapGoc() {
		log.info("TC_01_1_Click Mo tai khoan tiet kiem");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Mở tài khoản tiết kiệm");

		log.info("TC_01_2_Chon so tai khoan");
		savingOnline.clickToDynamicDropDown(driver, "Số tài khoản");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);

		currentcy = getCurrentcyMoney(savingOnline.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTiGia"));
		surplus = convertAvailableBalanceCurrentcyToDouble(savingOnline.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));

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
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Số tiền gửi"), addCommasToDouble(info.money) + " USD");

		log.info("TC_01_8_5_Kiem tra hinh thuc tra lai");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Hình thức trả lãi"), info.formOfPayment);

		log.info("TC_01_09_Chon phuong thuc xac thuc");
		savingOnline.scrollDownToText(driver, "Chọn phương thức xác thực");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		transferFee = convertAvailableBalanceCurrentcyOrFeeToLong(savingOnline.getDynamicTextInTransactionDetail(driver, "SMS OTP"));
		transferFeeDouble = convertVNeseMoneyToEUROOrUSD(transferFee + "", currentcy);
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
		transferTime = savingOnline.getTransferTimeSuccess(SavingOnline_Data.SUCCESS_TRANSACTION);
		savingAccount = savingOnline.getDynamicTextInTransactionDetail(driver, "Số tài khoản tiết kiệm");
		transactionNumber = savingOnline.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC_01_13_Click Thuc hien giao dich moi");
		savingOnline.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_01_14_Kiem tra so du kha dung luc sau");
		savingOnline.clickToDynamicDropDown(driver, "Số tài khoản");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);
		actualAvailableBalance = convertAvailableBalanceCurrentcyToDouble(savingOnline.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));
		availableBalance = canculateAvailableBalancesCurrentcy(surplus, Double.parseDouble(info.money), transferFeeDouble);
		verifyEquals(actualAvailableBalance, availableBalance);

	}

	@Test
	public void TC_02_MoTaiKhoanTietKiem_USD_1Thang_LaiNhapGoc_BaoCao() {
		log.info("TC_02_1: Click  nut Back");
		savingOnline.clickToDynamicBackIcon(driver, "Mở tài khoản tiết kiệm");

		log.info("TC_02_2: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_02_3: Click Bao Cao giao Dich");
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
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToDouble(info.money) + " USD"));

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
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").contains(addCommasToDouble(info.money) + " USD"));

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
	public void TC_03_TatToanTaiKhoanTietKiem_USD_1Thang_LaiNhapGoc() {
		String savingDate = "";
		String expiredDate = "";

		log.info("TC_03_1_Click Tat toan tai khoan tiet kiem");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Tất toán tài khoản tiết kiệm");

		log.info("TC_03_2_Chon so tai khoan tiet kiem");
		homePage.scrollDownToText(driver, "Tín dụng");
		savingOnline.clickToDynamicDropDownInSavingOnline("Tài khoản tiết kiệm");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, savingAccount);

		log.info("TC_03_3_Chon tai khoan dich");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, "Chọn tài khoản đích");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.DEFAULT_ACCOUNT3);

		surplusLong = convertAvailableBalanceCurrentcyOrFeeToLong(savingOnline.getDynamicTextAvailableBalanceInSavingOnline("Số dư khả dụng"));

		log.info("TC_03_4_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_03_5_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_03_06_Kiem tra man hinh xac thuc thong tin");
		log.info("TC_03_06_1: Kiem tra tai khoan tiet kiem");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản tiết kiệm"), savingAccount);

		log.info("TC_03_06_2: Kiem tra ten tai khoan tiet kiem");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tên tài khoản tiết kiệm"), "NGUYEN NGOC TOAN");

		log.info("TC_03_06_3: Kiem tra ky han gui");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Kỳ hạn gửi").toLowerCase(), info.term.toLowerCase());

		log.info("TC_03_06_4: Kiem tra lai suat");

		log.info("TC_03_06_5: Kiem tra ngay gui tien");
		savingDate = transReport.getDynamicTextInTransactionDetail(driver, "Ngày gửi tiền");
		verifyTrue(checkDateLessThanNow(savingDate));

		log.info("TC_03_06_6: Kiem tra ngay den han");
		expiredDate = getForwardMonthAndForwardDayFolowDate(savingDate, 1, 0);
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Ngày đến hạn"), expiredDate);

		log.info("TC_03_06_7: Kiem tra so tien gui goc");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền gửi gốc"), convertEURO_USDToVNeseMoney(info.money, currentcy));

		log.info("TC_03_06_8: Kiem tra so tien thuc huong");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền thực hưởng"), convertEURO_USDToVNeseMoney(info.money, currentcy));

		log.info("TC_03_06_9: Kiem tra tai khoan dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản đích"), Account_Data.Valid_Account.DEFAULT_ACCOUNT3);
		
		log.info("TC_03_06_10_Chon phuong thuc xac thuc");
		savingOnline.scrollDownToText(driver, "Chọn phương thức xác thực");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");
		transferFee = convertAvailableBalanceCurrentcyOrFeeToLong(savingOnline.getDynamicTextInTransactionDetail(driver, "SMS OTP"));
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_03_06_11: Kiem tra so tien phi");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), addCommasToLong(transferFee + "") + " VND");

		log.info("TC_03_7_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, "Tiếp tục");

		savingOnline.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		savingOnline.clickToDynamicButton(driver, "Tiếp tục");

		transactionNumber = savingOnline.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC_03_8_Click Thuc hien giao dich moi");
		savingOnline.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_03_9_Chon tai khoan dich");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, "Chọn tài khoản đích");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.DEFAULT_ACCOUNT3);

		actualAvailableBalanceLong = convertAvailableBalanceCurrentcyOrFeeToLong(savingOnline.getDynamicTextAvailableBalanceInSavingOnline("Số dư khả dụng"));
		availableBalanceLong = canculateAvailableBalances(surplusLong, - convertAvailableBalanceCurrentcyOrFeeToLong(convertEURO_USDToVNeseMoney(info.money, currentcy)), transferFee);
		verifyEquals(actualAvailableBalanceLong, availableBalanceLong);

	}

	@Test
	public void TC_04_TatToanTaiKhoanTietKiem_USD_1Thang_LaiNhapGoc_BaoCao() {
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
		transReport.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.DEFAULT_ACCOUNT3);

		log.info("TC_04_8: Click Tim Kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_04_9: Kiem tra ngay tao giao dich hien thi");
		String reportTime = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
		verifyEquals(convertDateTimeIgnoreHHmmss(reportTime), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_04_10: Kiem tra so tien tat toan");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("+ " + convertEURO_USDToVNeseMoney(info.money, currentcy)));

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
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản ghi có"), Account_Data.Valid_Account.DEFAULT_ACCOUNT3);

		log.info("TC_04_15: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").contains(convertEURO_USDToVNeseMoney(info.money, currentcy)));

		log.info("TC_04_16: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Loại giao dịch"), "Tất toán tài khoản tiết kiệm");

		log.info("TC_04_17: Kiem tra noi dung giao dich");
		String expectContent = "MBVCB." + transactionNumber + ".Tat toan TK tiet kiem " + savingAccount + " " + convertVietNameseStringToString(info.term);
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Nội dung giao dịch"), expectContent);

		log.info("TC_04_18: Click  nut Back");
		savingOnline.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_04_19: Click  nut Back");
		savingOnline.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

		log.info("TC_04_20: Click  nut Home");
		savingOnline.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");

	}

	@Test
	public void TC_05_MoTaiKhoanTietKiem_USD_3Thang_LaiNhapGoc() {
		log.info("TC_05_1_Click Mo tai khoan tiet kiem");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Mở tài khoản tiết kiệm");

		log.info("TC_05_2_Chon so tai khoan");
		savingOnline.clickToDynamicDropDown(driver, "Số tài khoản");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, info1.sourceAccount);

		currentcy = getCurrentcyMoney(savingOnline.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTiGia"));
		surplus = convertAvailableBalanceCurrentcyToDouble(savingOnline.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));

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
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Lãi suất"), "4.6%/Năm");

		log.info("TC_05_8_4_Kiem tra so tien gui");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Số tiền gửi"), addCommasToDouble(info1.money) + " USD");

		log.info("TC_05_8_5_Kiem tra hinh thuc tra lai");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Hình thức trả lãi"), info1.formOfPayment);

		log.info("TC_05_09_Chon phuong thuc xac thuc");
		savingOnline.scrollDownToText(driver, "Chọn phương thức xác thực");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		transferFee = convertAvailableBalanceCurrentcyOrFeeToLong(savingOnline.getDynamicTextInTransactionDetail(driver, "SMS OTP"));
		transferFeeDouble = convertVNeseMoneyToEUROOrUSD(transferFee + "", currentcy);
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
		transferTime = savingOnline.getTransferTimeSuccess(SavingOnline_Data.SUCCESS_TRANSACTION);
		savingAccount = savingOnline.getDynamicTextInTransactionDetail(driver, "Số tài khoản tiết kiệm");
		transactionNumber = savingOnline.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC_05_13_Click Thuc hien giao dich moi");
		savingOnline.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_05_14_Kiem tra so du kha dung luc sau");
		savingOnline.clickToDynamicDropDown(driver, "Số tài khoản");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, info1.sourceAccount);
		actualAvailableBalance = convertAvailableBalanceCurrentcyToDouble(savingOnline.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));
		availableBalance = canculateAvailableBalancesCurrentcy(surplus, Double.parseDouble(info1.money), transferFeeDouble);
		verifyEquals(actualAvailableBalance, availableBalance);

	}

	@Test
	public void TC_06_MoTaiKhoanTietKiem_USD_3Thang_LaiNhapGoc_BaoCao() {
		log.info("TC_06_1: Click  nut Back");
		savingOnline.clickToDynamicBackIcon(driver, "Mở tài khoản tiết kiệm");

		log.info("TC_06_2: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_06_3: Click Bao Cao giao Dich");
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
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToDouble(info1.money) + " USD"));

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
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").contains(addCommasToDouble(info1.money) + " USD"));

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
	public void TC_07_TatToanTaiKhoanTietKiem_USD_3Thang_LaiNhapGoc() {
		String savingDate = "";
		String expiredDate = "";

		log.info("TC_07_1_Click Tat toan tai khoan tiet kiem");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Tất toán tài khoản tiết kiệm");

		log.info("TC_07_2_Chon so tai khoan tiet kiem");
		homePage.scrollDownToText(driver, "Tín dụng");
		savingOnline.clickToDynamicDropDownInSavingOnline("Tài khoản tiết kiệm");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, savingAccount);

		log.info("TC_07_3_Chon tai khoan dich");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, "Chọn tài khoản đích");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.DEFAULT_ACCOUNT3);

		surplusLong = convertAvailableBalanceCurrentcyOrFeeToLong(savingOnline.getDynamicTextAvailableBalanceInSavingOnline("Số dư khả dụng"));

		log.info("TC_07_4_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_07_5_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_07_06_Kiem tra man hinh xac thuc thong tin");
		log.info("TC_07_06_1: Kiem tra tai khoan tiet kiem");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản tiết kiệm"), savingAccount);

		log.info("TC_07_06_2: Kiem tra ten tai khoan tiet kiem");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tên tài khoản tiết kiệm"), "NGUYEN NGOC TOAN");

		log.info("TC_07_06_3: Kiem tra ky han gui");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Kỳ hạn gửi").toLowerCase(), info1.term.toLowerCase());

		log.info("TC_07_06_4: Kiem tra lai suat");

		log.info("TC_07_06_5: Kiem tra ngay gui tien");
		savingDate = transReport.getDynamicTextInTransactionDetail(driver, "Ngày gửi tiền");
		verifyTrue(checkDateLessThanNow(savingDate));

		log.info("TC_07_06_6: Kiem tra ngay den han");
		expiredDate = getForwardMonthAndForwardDayFolowDate(savingDate, 3, 0);
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Ngày đến hạn"), expiredDate);

		log.info("TC_07_06_7: Kiem tra so tien gui goc");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền gửi gốc"), convertEURO_USDToVNeseMoney(info1.money, currentcy));

		log.info("TC_07_06_8: Kiem tra so tien thuc huong");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền thực hưởng"), convertEURO_USDToVNeseMoney(info1.money, currentcy));

		log.info("TC_07_06_9: Kiem tra tai khoan dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản đích"), Account_Data.Valid_Account.DEFAULT_ACCOUNT3);
		
		log.info("TC_07_06_10_Chon phuong thuc xac thuc");
		savingOnline.scrollDownToText(driver, "Chọn phương thức xác thực");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");
		transferFee = convertAvailableBalanceCurrentcyOrFeeToLong(savingOnline.getDynamicTextInTransactionDetail(driver, "SMS OTP"));
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_07_06_11: Kiem tra so tien phi");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), addCommasToLong(transferFee + "") + " VND");

		log.info("TC_07_7_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, "Tiếp tục");

		savingOnline.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		savingOnline.clickToDynamicButton(driver, "Tiếp tục");

		transactionNumber = savingOnline.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC_07_8_Click Thuc hien giao dich moi");
		savingOnline.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_07_9_Chon tai khoan dich");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, "Chọn tài khoản đích");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.DEFAULT_ACCOUNT3);

		actualAvailableBalanceLong = convertAvailableBalanceCurrentcyOrFeeToLong(savingOnline.getDynamicTextAvailableBalanceInSavingOnline("Số dư khả dụng"));
		availableBalanceLong = canculateAvailableBalances(surplusLong, - convertAvailableBalanceCurrentcyOrFeeToLong(convertEURO_USDToVNeseMoney(info1.money, currentcy)), transferFee);
		verifyEquals(actualAvailableBalanceLong, availableBalanceLong);

	}

	@Test
	public void TC_08_TatToanTaiKhoanTietKiem_USD_3Thang_LaiNhapGoc_BaoCao() {
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
		transReport.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.DEFAULT_ACCOUNT3);

		log.info("TC_08_8: Click Tim Kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_08_9: Kiem tra ngay tao giao dich hien thi");
		String reportTime = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
		verifyEquals(convertDateTimeIgnoreHHmmss(reportTime), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_08_10: Kiem tra so tien tat toan");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("+ " + convertEURO_USDToVNeseMoney(info1.money, currentcy)));

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
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản ghi có"), Account_Data.Valid_Account.DEFAULT_ACCOUNT3);

		log.info("TC_08_15: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").contains(convertEURO_USDToVNeseMoney(info1.money, currentcy)));

		log.info("TC_08_16: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Loại giao dịch"), "Tất toán tài khoản tiết kiệm");

		log.info("TC_08_17: Kiem tra noi dung giao dich");
		String expectContent = "MBVCB." + transactionNumber + ".Tat toan TK tiet kiem " + savingAccount + " " + convertVietNameseStringToString(info1.term);
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Nội dung giao dịch"), expectContent);

		log.info("TC_08_18: Click  nut Back");
		savingOnline.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_08_19: Click  nut Back");
		savingOnline.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

		log.info("TC_08_20: Click  nut Home");
		savingOnline.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
