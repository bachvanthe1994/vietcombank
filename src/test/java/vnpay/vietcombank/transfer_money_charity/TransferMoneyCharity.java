package vnpay.vietcombank.transfer_money_charity;

import java.io.IOException;
import java.math.BigDecimal;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import model.TransferCharity;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.TransactionReportPageObject;
import pageObjects.TransferMoneyCharityPageObject;
import vietcombankUI.DynamicPageUIs;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransferMoneyQuick_Data;

public class TransferMoneyCharity extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferMoneyCharityPageObject transferMoneyCharity;
	private TransactionReportPageObject transReport;
	private String transferTime;
	private String transactionNumber;
	long fee;

	TransferCharity info = new TransferCharity(Account_Data.Valid_Account.ACCOUNT3, "Test order", "1000000", "Do Minh Duc", "So 18 ngo 3 Thai Ha", "Ho ngheo", "Mật khẩu đăng nhập");
	TransferCharity info1 = new TransferCharity(Account_Data.Valid_Account.EUR_ACCOUNT, "Test order", "10", "Do Minh Duc", "So 18 ngo 3 Thai Ha", "Ho ngheo", "Mật khẩu đăng nhập");
	TransferCharity info2 = new TransferCharity(Account_Data.Valid_Account.ACCOUNT3, "Test order", "1000000", "Do Minh Duc", "So 18 ngo 3 Thai Ha", "Ho ngheo", "SMS OTP");
	TransferCharity info3 = new TransferCharity(Account_Data.Valid_Account.EUR_ACCOUNT, "Test order", "10", "Do Minh Duc", "So 18 ngo 3 Thai Ha", "Ho ngheo", "SMS OTP");

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName" })

	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);

		login = PageFactoryManager.getLoginPageObject(driver);

		login.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");

		login.inputToDynamicLogInTextBox(driver, LogIn_Data.Login_Account.PHONE, "Tiếp tục");

		login.clickToDynamicButton(driver, "Tiếp tục");

		login.inputToDynamicInputBox(driver, LogIn_Data.Login_Account.NEW_PASSWORD, LogIn_Data.UI.PASSWORD_LABEL);

		login.clickToDynamicButton(driver, "Tiếp tục");

		login.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		login.clickToDynamicButton(driver, "Tiếp tục");

		login.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");
	}

	private long surplus, availableBalance, actualAvailableBalance;

	@Test
	public void TC_01_ChuyenTienTuThienBangVNDThanhToanMatKhau() {
		homePage = PageFactoryManager.getHomePageObject(driver);
		transferMoneyCharity = PageFactoryManager.getTransferMoneyCharityPageObject(driver);

		log.info("TC_01_1_Click Chuyen tien tu thien");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền từ thiện");

		log.info("TC_01_2_Chon tai khoan nguon");

		transferMoneyCharity.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);

		surplus = Long.parseLong(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng").replaceAll("\\D+", ""));

		log.info("TC_01_3_Chon Quy/ To chuc tu thien");
		transferMoneyCharity.clickToDynamicInput(driver, "Quỹ/ Tổ chức từ thiện");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info.organization);

		log.info("TC_01_4_Nhap so tien ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.money, "Số tiền ủng hộ");

		log.info("TC_01_5_Nhap ten nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.name, "Tên người ủng hộ");

		log.info("TC_01_6_Nhap dia chi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.address, "Địa chỉ người ủng hộ");

		log.info("TC_01_7_Hoan canh nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.status, "Hoàn cảnh người ủng hộ");

		log.info("TC_01_8_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_9_Kiem tra man hinh xac nhan thong tin");

		log.info("TC_01_9_1_Kiem tra tai khoan nguon");
		verifyEquals(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), info.sourceAccount);

		log.info("TC_01_9_2_Kiem tra to chuc");
		verifyTrue(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Tài khoản đích").contains(info.organization));

		log.info("TC_01_9_2_Kiem tra tai khoan nguon");
		String destinationAccount = transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Tài khoản đích").split("/")[0].trim();

		String expectMoney = addCommasToLong(info.money) + " VND";
		log.info("TC_01_9_3_Kiem tra so tien ung ho");
		verifyEquals(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Số tiền ủng hộ"), expectMoney);

		log.info("TC_01_9_4_Kiem tra ten nguoi ung ho");
		verifyEquals(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Tên người ủng hộ"), info.name);

		log.info("TC_01_9_5_Kiem tra dia chi nguoi chuyen");
		verifyEquals(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Địa chỉ người ủng hộ"), info.address);

		log.info("TC_01_9_6_Kiem tra hoan canh");
		verifyEquals(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Hoàn cảnh người ủng hộ"), info.status);

		log.info("TC_01_10_Chon phuong thuc xac thuc");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");

		fee = Long.parseLong(transferMoneyCharity.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Mật khẩu đăng nhập").replaceAll("\\D+", "")) + 1000;

		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info.authenticationMethod);

		log.info("TC_01_11_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");

		transferMoneyCharity.inputToPasswordConfirm(driver, LogIn_Data.Login_Account.NEW_PASSWORD);

		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_12_Kiem tra man hinh Chuyen khoan thanh cong");
		log.info("TC_01_12_1_Kiem tra Chuyen khoan thanh cong");
		verifyTrue(transferMoneyCharity.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY));

		log.info("TC_01_12_2_Kiem tra ten nguoi thu huong");
		verifyEquals(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Tên người thụ hưởng"), info.organization);

		log.info("TC_01_12_3_Kiem tra tai khoan dich");
		verifyEquals(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Tài khoản đích"), destinationAccount);

		log.info("TC_01_12_4_Kiem tra noi dung chuyen tien");
		verifyEquals(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Nội dung"), info.status);

		log.info("TC_01_12_5_Kiem tra nut Thuc hien giao dich moi");
		verifyTrue(transferMoneyCharity.isDynamicButtonDisplayed(driver, "Thực hiện giao dịch mới"));

		log.info("TC_01_12_6_Lay ma giao dich");
		transferTime = transferMoneyCharity.getDynamicTransferTimeAndMoney(driver, "Chuyển khoản thành công", "4");
		transactionNumber = transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC_01_13_Click Thuc hien giao dich moi");
		transferMoneyCharity.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_01_14_Kiem tra so du kha dung luc sau");
		transferMoneyCharity.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);

		actualAvailableBalance = Long.parseLong(transferMoneyCharity.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Số dư khả dụng").replaceAll("\\D+", ""));

		availableBalance = surplus - Long.parseLong(info.money) - fee;
		verifyEquals(actualAvailableBalance, availableBalance);

	}

	@Test
	public void TC_02_ChuyenTienTuThienBangVNDThanhToanMatKhau_BaoCao() {
		log.info("TC_02_1: Click  nut Back");
		transferMoneyCharity.clickToDynamicBackIcon(driver, "Chuyển tiền từ thiện");

		log.info("TC_02_2: Click vao More Icon");
		homePage.clickToDynamicBottomMenuOrCloseIcon(driver, "com.VCB:id/menu_5");

		log.info("TC_02_3: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

		log.info("TC_02_4: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

		log.info("TC_02_5: Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền từ thiện");

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
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(info.status));

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

		log.info("TC_02_20: Kiem tra ten quy, to chuc tu thien");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tên Quỹ/Tổ chức từ thiện"), info.organization);

		log.info("TC_02_21: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), String.format("%,d", fee) + " VND");

		log.info("TC_02_22: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Loại giao dịch"), "Chuyển tiền từ thiện");

		log.info("TC_02_23: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Nội dung giao dịch").contains(info.status));

		log.info("TC_02_24: Click  nut Back");
		transferMoneyCharity.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_02_25: Click  nut Back");
		transferMoneyCharity.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

		log.info("TC_02_26: Click  nut Home");
		transferMoneyCharity.clickToDynamicBottomMenuOrCloseIcon(driver, "com.VCB:id/menu_1");
	}

	@Test
	public void TC_03_ChuyenTienTuThienBangNgoaiTeThanhToanMatKhau() {
		log.info("TC_03_1_Click Chuyen tien tu thien");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền từ thiện");

		log.info("TC_03_2_Chon tai khoan nguon");
		transferMoneyCharity.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info1.sourceAccount);
		surplus = Long.parseLong(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng").replaceAll("\\D+", ""));

		log.info("TC_03_3_Chon Quy/ To chuc tu thien");
		transferMoneyCharity.clickToDynamicInput(driver, "Quỹ/ Tổ chức từ thiện");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info1.organization);

		log.info("TC_03_4_Nhap so tien ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info1.money, "Số tiền ủng hộ");

		log.info("TC_03_5_Nhap ten nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info1.name, "Tên người ủng hộ");

		log.info("TC_03_6_Nhap dia chi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info1.address, "Địa chỉ người ủng hộ");

		log.info("TC_03_7_Hoan canh nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info1.status, "Hoàn cảnh người ủng hộ");

		log.info("TC_03_8_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_03_9_Kiem tra man hinh xac nhan thong tin");
		log.info("TC_03_9_1_Kiem tra man tai khoan nguon");
		verifyEquals(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), info1.sourceAccount);

		log.info("TC_03_9_2_Kiem tra to chuc");
		verifyTrue(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Tài khoản đích").contains(info1.organization));

		log.info("TC_03_9_3_Kiem tra tai khoan dich");
		String destinationAccount = transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Tài khoản đích").split("/")[0].trim();

		log.info("TC_03_9_4_Kiem tra so tien ung ho");
		String actualMoney = transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Số tiền ủng hộ");
		String expectedMoney = String.format("%.2f", new BigDecimal(Double.parseDouble(info1.money))) + " EUR";
		verifyEquals(actualMoney, expectedMoney);

		log.info("TC_03_9_5_Kiem tra ten nguoi ung ho");
		verifyEquals(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Tên người ủng hộ"), info1.name);

		log.info("TC_03_9_6_Kiem tra dia chia nguoi chuyen");
		verifyEquals(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Địa chỉ người ủng hộ"), info1.address);

		log.info("TC_03_9_7_Kiem tra hoan canh");
		verifyEquals(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Hoàn cảnh người ủng hộ"), info1.status);

		log.info("TC_03_10_Chon phuong thuc xac thuc");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");

		fee = Long.parseLong(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Mật khẩu đăng nhập").replaceAll("\\D+", "")) / 100;

		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info1.authenticationMethod);

		log.info("TC_03_11_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");

		transferMoneyCharity.inputToPasswordConfirm(driver, LogIn_Data.Login_Account.NEW_PASSWORD);

		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_03_12_Kiem tra man hinh Chuyen khoan thanh cong");
		verifyTrue(transferMoneyCharity.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY));

		log.info("TC_03_12_1_Kiem tra to chuc");
		verifyEquals(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Tên người thụ hưởng"), info1.organization);

		log.info("TC_02_12_2_Kiem tra tai khoan dich");
		verifyEquals(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Tài khoản đích"), destinationAccount);

		log.info("TC_03_12_3_Kiem tra hoan canh");
		verifyEquals(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Nội dung"), info1.status);

		log.info("TC_03_12_4_Kiem tra nut thuc hien giao dich moi");
		verifyTrue(transferMoneyCharity.isDynamicButtonDisplayed(driver, "Thực hiện giao dịch mới"));

		log.info("TC_03_12_5_Lay ma giao dich");
		transferTime = transferMoneyCharity.getDynamicTransferTimeAndMoney(driver, "Chuyển khoản thành công", "4");
		transactionNumber = transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC_03_13_Click Thuc hien giao dich moi");
		transferMoneyCharity.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_03_14_Kiem tra so du kha dung luc sau");
		transferMoneyCharity.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info1.sourceAccount);
		actualAvailableBalance = Long.parseLong(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng").replaceAll("\\D+", ""));

	}

	@Test
	public void TC_04_ChuyenTienTuThienBangNgoaiTeThanhToanMatKhau_BaoCao() {
		log.info("TC_04_1: Click  nut Back");
		transferMoneyCharity.clickToDynamicBackIcon(driver, "Chuyển tiền từ thiện");

		log.info("TC_04_2: Click vao More Icon");
		homePage.clickToDynamicBottomMenuOrCloseIcon(driver, "com.VCB:id/menu_5");

		log.info("TC_04_3: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

		log.info("TC_04_5: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

		log.info("TC_04_6: Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền từ thiện");

		log.info("TC_04_7: Click Chon Tai Khoan");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_04_8: Chon tai Khoan chuyen");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, info1.sourceAccount);

		log.info("TC_04_9: Click Tim Kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_04_10: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));

		log.info("TC_04_11: Kiem tra thoi gian tao dao dich");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_04_12: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(info1.status));

		log.info("TC_04_13: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToDouble(info1.money) + " EUR"));

		log.info("TC_04_15: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_04_16: Kiem tra ngay giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch").contains(transferTime.split(" ")[0]));

		log.info("TC_04_17: Kiem tra thoi gian tao giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_04_18: Kiem tra thoi gian tao giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_04_19: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), info1.sourceAccount);

		log.info("TC_04_20: Kiem tra so tai khoan ghi co");

		log.info("TC_04_21: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").contains(addCommasToDouble(info1.money) + " EUR"));

		log.info("TC_04_22: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền quy đổi").contains(String.format("%,d", Long.parseLong(info1.money) * 27006) + " VND"));

		log.info("TC_04_23: Kiem tra ten quy, to chuc tu thien");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tên Quỹ/Tổ chức từ thiện"), info1.organization);

		log.info("TC_04_24: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), String.format("%,d", 2100) + " VND");

		log.info("TC_04_25: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Loại giao dịch"), "Chuyển tiền từ thiện");

		log.info("TC_04_26: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Nội dung giao dịch").contains(info1.status));

		log.info("TC_04_27: Click  nut Back");
		transferMoneyCharity.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_04_28: Click  nut Back");
		transferMoneyCharity.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

		log.info("TC_04_29: Click  nut Home");
		transferMoneyCharity.clickToDynamicBottomMenuOrCloseIcon(driver, "com.VCB:id/menu_1");
	}

	@Test
	public void TC_05_ChuyenTienTuThienBangVNDThanhToanSMSOTP() {
		log.info("TC_05_1_Click Chuyen tien tu thien");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền từ thiện");

		log.info("TC_05_2_Chon tai khoan nguon");
		transferMoneyCharity.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info2.sourceAccount);
		surplus = Long.parseLong(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng").replaceAll("\\D+", ""));

		log.info("TC_05_3_Chon Quy/ To chuc tu thien");
		transferMoneyCharity.clickToDynamicInput(driver, "Quỹ/ Tổ chức từ thiện");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info2.organization);

		log.info("TC_05_4_Nhap so tien ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info2.money, "Số tiền ủng hộ");

		log.info("TC_05_5_Nhap ten nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info2.name, "Tên người ủng hộ");

		log.info("TC_05_6_Nhap dia chi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info2.address, "Địa chỉ người ủng hộ");

		log.info("TC_05_7_Hoan canh nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info2.status, "Hoàn cảnh người ủng hộ");

		log.info("TC_05_8_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_05_9_Kiem tra man xac dinh giao dich");
		log.info("TC_05_9_1_Kiem tra tai khoan nguon");
		verifyEquals(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), info2.sourceAccount);

		log.info("TC_05_9_2_Kiem tra to chuc");
		verifyTrue(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Tài khoản đích").contains(info2.organization));

		log.info("TC_05_9_3_Kiem tra tai khoan dich");
		String destinationAccount = transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Tài khoản đích").split("/")[0].trim();

		String expectMoney = addCommasToLong(info2.money) + " VND";
		log.info("TC_05_9_4_Kiem tra so tien ung ho");
		verifyEquals(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Số tiền ủng hộ"), expectMoney);

		log.info("TC_05_9_5_Kiem tra ten nguoi ung ho");
		verifyEquals(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Tên người ủng hộ"), info2.name);

		log.info("TC_05_9_6_Kiem tra dia chi nguoi chuyen");
		verifyEquals(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Địa chỉ người ủng hộ"), info2.address);

		log.info("TC_05_9_7_Kiem tra hoan canh");
		verifyEquals(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Hoàn cảnh người ủng hộ"), info2.status);

		log.info("TC_05_10_Chon phuong thuc xac thuc");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");

		long fee = Long.parseLong(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "SMS OTP").replaceAll("\\D+", ""));

		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info2.authenticationMethod);

		log.info("TC_05_11_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");

		transferMoneyCharity.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_05_12_Kiem tra man hinh Chuyen khoan thanh cong");
		log.info("TC_05_12_1_Kiem tra chuyen khoan thanh cong");
		verifyTrue(transferMoneyCharity.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY));

		log.info("TC_05_12_2_Kiem tra tai khoan dich");
		verifyEquals(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Tài khoản đích"), destinationAccount);

		log.info("TC_05_12_3_Kiem tra noi dung chuyen tien");
		verifyEquals(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Nội dung"), info2.status);

		log.info("TC_05_12_4_Kiem tra nut thuc hien giao dich moi");
		verifyTrue(transferMoneyCharity.isDynamicButtonDisplayed(driver, "Thực hiện giao dịch mới"));

		log.info("TC_05_12_5_Lay ma giao dich");
		transferTime = transferMoneyCharity.getDynamicTransferTimeAndMoney(driver, "Chuyển khoản thành công", "4");
		transactionNumber = transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC_05_13_Click Thuc hien giao dich moi");
		transferMoneyCharity.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_05_14_Kiem tra so du kha dung luc sau");
		transferMoneyCharity.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info2.sourceAccount);
		actualAvailableBalance = Long.parseLong(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng").replaceAll("\\D+", ""));

		availableBalance = surplus - Long.parseLong(info2.money) - fee;
		verifyEquals(actualAvailableBalance, availableBalance);

	}

	@Test
	public void TC_06_ChuyenTienTuThienBangVNDThanhToanSMSOTP_BaoCao() {
		log.info("TC_06_1 : Click  nut Back");
		transferMoneyCharity.clickToDynamicBackIcon(driver, "Chuyển tiền từ thiện");

		log.info("TC_06_2: Click vao More Icon");
		homePage.clickToDynamicBottomMenuOrCloseIcon(driver, "com.VCB:id/menu_5");

		log.info("TC_06_3: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

		log.info("TC_06_4: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

		log.info("TC_06_5: Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền từ thiện");

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
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(info2.status));

		log.info("TC_06_12: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(info2.money) + " VND"));

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
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").contains(addCommasToLong(info2.money) + " VND"));

		log.info("TC_06_20: Kiem tra ten quy, to chuc tu thien");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tên Quỹ/Tổ chức từ thiện"), info2.organization);

		log.info("TC_06_21: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), String.format("%,d", fee) + " VND");

		log.info("TC_06_22: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Loại giao dịch"), "Chuyển tiền từ thiện");

		log.info("TC_06_23: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Nội dung giao dịch").contains(info2.status));

		log.info("TC_06_24: Click  nut Back");
		transferMoneyCharity.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_06_25: Click  nut Back");
		transferMoneyCharity.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

		log.info("TC_06_26: Click  nut Home");
		transferMoneyCharity.clickToDynamicBottomMenuOrCloseIcon(driver, "com.VCB:id/menu_1");
	}

	@Test
	public void TC_07_ChuyenTienTuThienBangNgoaiTeThanhToanOTP() {
		log.info("TC_07_1_Click Chuyen tien tu thien");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền từ thiện");

		log.info("TC_07_2_Chon tai khoan nguon");
		transferMoneyCharity.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info3.sourceAccount);
		surplus = Long.parseLong(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng").replaceAll("\\D+", ""));

		log.info("TC_07_3_Chon Quy/ To chuc tu thien");
		transferMoneyCharity.clickToDynamicInput(driver, "Quỹ/ Tổ chức từ thiện");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info3.organization);

		log.info("TC_07_4_Nhap so tien ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info3.money, "Số tiền ủng hộ");

		log.info("TC_07_5_Nhap ten nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info3.name, "Tên người ủng hộ");

		log.info("TC_07_6_Nhap dia chi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info3.address, "Địa chỉ người ủng hộ");

		log.info("TC_07_7_Hoan canh nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info3.status, "Hoàn cảnh người ủng hộ");

		log.info("TC_07_8_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_07_9_Kiem tra man xac nhan thong tin");
		log.info("TC_07_9_1_Kiem tra tai khoan nguon");
		verifyEquals(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), info3.sourceAccount);

		log.info("TC_07_9_2_Kiem tra to chuc");
		verifyTrue(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Tài khoản đích").contains(info3.organization));

		log.info("TC_07_9_3_Kiem tra tai khoan dich");
		String destinationAccount = transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Tài khoản đích").split("/")[0].trim();

		log.info("TC_07_9_4_Kiem tra so tien ung ho");
		String actualMoney = transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Số tiền ủng hộ");
		String expectedMoney = String.format("%.2f", new BigDecimal(Double.parseDouble(info1.money))) + " EUR";
		verifyEquals(actualMoney, expectedMoney);

		log.info("TC_07_9_5_Kiem tra ten nguoi chuyen");
		verifyEquals(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Tên người ủng hộ"), info3.name);

		log.info("TC_07_9_6_Kiem tra dia chi nguoi chuyen");
		verifyEquals(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Địa chỉ người ủng hộ"), info3.address);

		log.info("TC_07_9_7_Kiem tra hoan canh");
		verifyEquals(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Hoàn cảnh người ủng hộ"), info3.status);

		log.info("TC_07_10_Chon phuong thuc xac thuc");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");

		fee = Long.parseLong(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "SMS OTP").replaceAll("\\D+", ""));

		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info3.authenticationMethod);

		log.info("TC_07_11_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");

		transferMoneyCharity.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_07_12_Kiem tra man hinh Chuyen khoan thanh cong");
		log.info("TC_07_12_1_Kiem tra chuyen khoan thanh cong");
		verifyTrue(transferMoneyCharity.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY));

		log.info("TC_07_12_2_Kiem tra ten nguoi thu huong");
		verifyEquals(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Tên người thụ hưởng"), info3.organization);

		log.info("TC_07_12_3_Kiem tra tai khoan dich");
		verifyEquals(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Tài khoản đích"), destinationAccount);

		log.info("TC_07_12_4_Kiem tra hoan canh");
		verifyEquals(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Nội dung"), info3.status);

		log.info("TC_07_12_5_Kiem tra nut thuc hien giao dich");
		verifyTrue(transferMoneyCharity.isDynamicButtonDisplayed(driver, "Thực hiện giao dịch mới"));

		log.info("TC_07_12_6_Lay ma chuy tien");
		transferTime = transferMoneyCharity.getDynamicTransferTimeAndMoney(driver, "Chuyển khoản thành công", "4");
		transactionNumber = transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC_07_13_Click Thuc hien giao dich moi");
		transferMoneyCharity.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_07_14_Kiem tra so du kha dung luc sau");
		transferMoneyCharity.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info3.sourceAccount);
		actualAvailableBalance = Long.parseLong(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng").replaceAll("\\D+", ""));

	}

	@Test
	public void TC_08_ChuyenTienTuThienBangNgoaiTeThanhToanOTP_BaoCao() {
		log.info("TC_08_1 : Click  nut Back");
		transferMoneyCharity.clickToDynamicBackIcon(driver, "Chuyển tiền từ thiện");

		log.info("TC_08_2: Click vao More Icon");
		homePage.clickToDynamicBottomMenuOrCloseIcon(driver, "com.VCB:id/menu_5");

		log.info("TC_08_3: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

		log.info("TC_08_4: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

		log.info("TC_08_5: Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền từ thiện");

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
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(info3.status));

		log.info("TC_08_12: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToDouble(info3.money) + " EUR"));

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

		log.info("TC_08_20: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền quy đổi").contains(String.format("%,d", Long.parseLong(info3.money) * 27006) + " VND"));

		log.info("TC_08_21: Kiem tra ten quy, to chuc tu thien");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tên Quỹ/Tổ chức từ thiện"), info3.organization);

		log.info("TC_08_22: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), String.format("%,d", 0) + " VND");

		log.info("TC_08_23: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Loại giao dịch"), "Chuyển tiền từ thiện");

		log.info("TC_08_24: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Nội dung giao dịch").contains(info3.status));

		log.info("TC_08_25: Click  nut Back");
		transferMoneyCharity.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_08_26: Click  nut Back");
		transferMoneyCharity.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

		log.info("TC_08_27: Click  nut Home");
		transferMoneyCharity.clickToDynamicBottomMenuOrCloseIcon(driver, "com.VCB:id/menu_1");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();
	}

}
