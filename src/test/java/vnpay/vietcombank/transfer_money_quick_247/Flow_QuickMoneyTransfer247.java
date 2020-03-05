package vnpay.vietcombank.transfer_money_quick_247;

import java.io.IOException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.TransactionReportPageObject;
import pageObjects.TransferMoneyObject;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransferMoneyQuick_Data;

public class Flow_QuickMoneyTransfer247 extends Base {
	AppiumDriver<MobileElement> driver;
	private TransactionReportPageObject transReport;
	private String transferTime;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferMoneyObject transferMoney;
	private String transactionNumber;
	private String amountStartString;
	private long amountStart;
	private String amountTranferString;
	private long amountTranfer;
	private String costTranferString;
	private long costTranfer;

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);

		transferMoney = PageFactoryManager.getTransferMoneyObject(driver);
	}

	 @Test
	public void TC_01_ChuyenTienCoPhiGiaoDichChonNguoiChuyenOTP() throws InterruptedException {
		log.info("TC_01_Step_Click Chuyen tien nhanh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh 24/7");

		log.info("TC_01_Step_Select Chuyen tien nhanh qua tai khoan");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);

		log.info("TC_01_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_ACCOUNT_FROM[0]);

		log.info("TC_01_Step_Get so du kha dung");
		amountStartString = transferMoney.getDynamicAmountLabel(driver, "Số dư khả dụng").replaceAll("\\D+", "");

		log.info("TC_01_Step_Doi kieu du lieu");
		amountStart = Long.parseLong(amountStartString);

		log.info("TC_01_Step_Nhap so tai khoan chuyen");
		transferMoney.inputToDynamicInputBox(driver, Account_Data.Valid_Account.ACCOUNT_TO, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_01_Step_Select ngan hang");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng hưởng");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.BANK[0]);

		log.info("TC_01_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.MONEY, "Số tiền");

		log.info("TC_01_Step_Chon phi giao dich la nguoi chuyen tra");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[0]);

		log.info("TC_01_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE, "Thông tin giao dịch","3");

		log.info("TC_01_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_Lay gia tri so tien chuyen");
		amountTranferString = transferMoney.getDynamicAmountLabel(driver, "Số tiền").replaceAll("\\D+", "");

		log.info("TC_01_Step_Verify so tien chuyen");
		verifyEquals(amountTranferString, TransferMoneyQuick_Data.TransferQuick.MONEY);

		log.info("TC_01_Step_doi kieu du lieu string -> long");
		amountTranfer = Long.parseLong(amountTranferString);

		log.info("TC_01_Step_Lay gia tri so tien phí chuyen");
		costTranferString = transferMoney.getDynamicAmountLabel(driver, "Số tiền phí").replaceAll("\\D+", "");

		log.info("TC_01_Step_Verify so tien phi");
		verifyEquals(costTranferString, TransferMoneyQuick_Data.TransferQuick.COST_AMOUNT_VND);

		log.info("TC_01_Step_doi kieu du lieu string -> long");
		costTranfer = Long.parseLong(costTranferString);

		log.info("TC_01_Step_Chon phuong thuc xac thuc");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[1]);

		log.info("TC_01_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_Nhap ma xac thuc");
		transferMoney.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		log.info("TC_01_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Verify message thanh cong");
		verifyEquals(transferMoney.getTextInDynamicPopup(driver, "com.VCB:id/tvTitle"), TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY);

		log.info("TC_01_get thoi gian giao dich thanh cong");
		transferTime = transferMoney.getDynamicTransferTimeAndMoney(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY, "4");

		log.info("TC_01_Step_: Get ma giao dich");
		transactionNumber = transferMoney.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC_01_Step_:Ten nguoi thu huong");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), TransferMoneyQuick_Data.TransferQuick.RECEIVER_NAME);

		log.info("TC_01_Step_: Tai khoan dich");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, "Tài khoản đích"), Account_Data.Valid_Account.ACCOUNT_TO);

		log.info("TC_01_Step_: Noi dung");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, "Nội dung"), TransferMoneyQuick_Data.TransferQuick.NOTE);

		log.info("TC_01_Step_: Thuc hien giao dich moi");
		transferMoney.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_01_Step_:Tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_01_Step_: Tai khoan chuyen");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_ACCOUNT_FROM[0]);

		log.info("TC_01_Step_:Check so du kha dung sau khi chuyen tien");
		String amountAfterString = transferMoney.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng").replaceAll("\\D+", "");

		long amountAfter = Long.parseLong(amountAfterString);

		log.info("TC_01_Step_:Check so du kha dung sau khi chuyen tien");
		verifyEquals(amountStart - amountTranfer - costTranfer, amountAfter);
	}

	 @Test
	public void TC_02_BaoCaoGiaoDichChuyenTienNhanh() {
		homePage = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_Step_: Click back man hinh home");
		homePage.clickToDynamicBackIcon(driver, "Chuyển tiền nhanh 24/7");

		log.info("TC_Step_: Click menu header");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_Step_: Click bao cao giao dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

		log.info("TC_Step_: chon loai bao cao giao dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

		log.info("TC_Step_: Chon option chuyen tien nhanh qua tai khoan");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh qua số tài khoản");

		log.info("TC_Step_: Chon so tai khoan");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_Step_: Chon so tai khoan tra cuu");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_ACCOUNT_FROM[0]);

		log.info("TC_Step_: verify thoi tim kiem tu ngay");
		String dateStartActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate");
		String dateStartExpect = getBackwardDate(7);
		verifyEquals(dateStartActual, dateStartExpect);

		log.info("TC_Step_: verify thoi tim kiem tu ngay");
		String dateEndActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate");
		String dateEndtExpect = getForwardDate(0);
		verifyEquals(dateEndActual, dateEndtExpect);

		log.info("TC_Step_: Tim kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_Step_: Check ngay chuyen");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));

		log.info("TC_Step_: Check gio chuyen");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_Step_: Check ghi chu");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyQuick_Data.TransferQuick.NOTE));

		log.info("TC_Step_: Check so tien chuyen");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(TransferMoneyQuick_Data.TransferQuick.MONEY) + " VND"));

		log.info("TC_Step_: Click chi tiet giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_Step_: Check ngay tim kiem");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch").contains(transferTime.split(" ")[0]));

		log.info("TC_Step_: check gio tim kiem");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_Step_: Check so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_Step_: Check tao khoan ghi no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), Account_Data.Valid_Account.LIST_ACCOUNT_FROM[0]);

		log.info("TC_Step_: Check tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản ghi có"), Account_Data.Valid_Account.ACCOUNT_TO);

		log.info("TC_Step_: Check so tien giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").contains(addCommasToLong(TransferMoneyQuick_Data.TransferQuick.MONEY) + " VND"));

		log.info("TC_Step_: Check so nguoi huong");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), TransferMoneyQuick_Data.TransferQuick.RECEIVER_NAME);

		log.info("TC_Step_: Check ngan hang huong");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Ngân hàng hưởng"), Account_Data.Valid_Account.BANK[0]);

		log.info("TC_Step_: Check phi giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Phí giao dịch"), TransferMoneyQuick_Data.TransferQuick.COST_SUB[0]);

		log.info("TC_Step_: Check loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Loại giao dịch"), TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);

		log.info("TC_Step_: Check noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Nội dung giao dịch").contains(TransferMoneyQuick_Data.TransferQuick.NOTE));

		log.info("TC_Step_: Chick chi tiet giao dich");
		transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_Step_: Chon button back");
		transferMoney.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

		log.info("TC_01_Step_Click button home");
		transferMoney.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	 @Test
	public void TC_03_ChuyenTienNhanhQuaTaiKhoanChonUSDNguoiChuyenTraPhiOTP() {
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh 24/7");

		log.info("TC_01_Step_Select Chuyen tien nhanh qua tai khoan");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);

		log.info("TC_01_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_ACCOUNT_FROM[1]);

		log.info("TC_04_Step_Get so du kha dung");
		amountStartString = transferMoney.getDynamicAmountLabel(driver, "Số dư khả dụng");

		double amountStart1 = convertMoneyToDouble(amountStartString, "USD");

		log.info("TC_04_Step_Nhap so tai khoan chuyen");
		transferMoney.inputToDynamicInputBox(driver, Account_Data.Valid_Account.ACCOUNT_TO, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_04_Step_Select ngan hang");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng hưởng");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.BANK[0]);

		log.info("TC_04_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_USD, "Số tiền");

		log.info("TC_04_Step_Chon phi giao dich la nguoi chuyen");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[0]);

		log.info("TC_04_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE, "Thông tin giao dịch","3");

		log.info("TC_04_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		
		  log.info("TC_05_Step_Verify so tien chuyen");
		  verifyTrue(transferMoney.getDynamicTextInTransactionDetail(driver,
		  "Số tiền").contains(addCommasToDouble(TransferMoneyQuick_Data.TransferQuick.
		  MONEY_USD) + "USD"));
		 

		log.info("TC_05_Lay phi chuyen tien");
		double costTranfer = convertMoneyToDouble(TransferMoneyQuick_Data.TransferQuick.COST_AMOUNT_USD, "USD");

		log.info("TC_05_Step_Chon phuong thuc xac thuc");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[1]);

		log.info("TC_05_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_05_Step_Nhap ma xac thuc");
		transferMoney.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		log.info("TC_01_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Verify message thanh cong");
		verifyEquals(transferMoney.getTextInDynamicPopup(driver, "com.VCB:id/tvTitle"), TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY);

		log.info("TC_01_get thoi gian giao dich thanh cong");
		transferTime = transferMoney.getDynamicTransferTimeAndMoney(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY, "4");

		log.info("TC_01_Step_: Get ma giao dich");
		transactionNumber = transferMoney.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC_01_Step_:Ten nguoi thu huong");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), TransferMoneyQuick_Data.TransferQuick.RECEIVER_NAME);

		log.info("TC_01_Step_: Tai khoan dich");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, "Tài khoản đích"), Account_Data.Valid_Account.ACCOUNT_TO);

		log.info("TC_01_Step_: Noi dung");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, "Nội dung"), TransferMoneyQuick_Data.TransferQuick.NOTE);

		log.info("TC_01_Step_: Thuc hien giao dich moi");
		transferMoney.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_01_Step_: Chon tai khoan chuyen den");
		transferMoney.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_01_Step_: Chon tai khoan chuyen den");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_ACCOUNT_FROM[1]);

		log.info("TC05_Step 37: Lay so du kha dung tai khoan");
		String amountAfterString = transferMoney.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		double amountAfter = convertMoneyToDouble(amountAfterString, "USD");

		log.info("TC05_Step 38: Convert so tien chuyen");
		double transferMoney = convertMoneyToDouble(TransferMoneyQuick_Data.TransferQuick.MONEY_USD, "USD");

		log.info("TC05_Step 39: Kiem tra so du tai khoan USD sau khi chuyen tien");
		verifyEquals(amountStart1 - transferMoney - costTranfer, amountAfter);

	}

	 @Test
	public void TC_04_BaoCaoGiaoDichChuyenTienNhanh() {
		homePage = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_Step_: Click back man hinh home");
		homePage.clickToDynamicBackIcon(driver, "Chuyển tiền nhanh 24/7");

		log.info("TC_Step_: Click menu header");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_Step_: Click bao cao giao dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

		log.info("TC_Step_: chon loai bao cao giao dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

		log.info("TC_Step_: Chon option chuyen tien nhanh qua tai khoan");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh qua số tài khoản");

		log.info("TC_Step_: Chon so tai khoan");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_Step_: Chon so tai khoan chuyen");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_ACCOUNT_FROM[1]);

		log.info("TC_Step_: verify thoi tim kiem tu ngay");
		String dateStartActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate");
		String dateStartExpect = getBackwardDate(7);
		verifyEquals(dateStartActual, dateStartExpect);

		log.info("TC_Step_: verify thoi tim kiem tu ngay");
		String dateEndActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate");
		String dateEndtExpect = getForwardDate(0);
		verifyEquals(dateEndActual, dateEndtExpect);

		log.info("TC_Step_: Tim kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_Step_: Check ngay chuyen");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));

		log.info("TC_Step_: Check gio chuyen");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_Step_: Check ghi chu");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyQuick_Data.TransferQuick.NOTE));

		log.info("TC_Step_: Check so tien chuyen");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(TransferMoneyQuick_Data.TransferQuick.MONEY_USD) + ".00 USD"));

		log.info("TC_Step_: Chon ngay thang");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_Step_: Verify thoi gian giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch").contains(transferTime.split(" ")[0]));

		log.info("TC_Step_: check gio tim kiem");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_Step_: Check so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_Step_: Check tao khoan ghi no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), Account_Data.Valid_Account.LIST_ACCOUNT_FROM[1]);

		log.info("TC_Step_: Check tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản ghi có"), Account_Data.Valid_Account.ACCOUNT_TO);

		log.info("TC_Step_: Check so tien giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").contains(addCommasToLong(TransferMoneyQuick_Data.TransferQuick.MONEY_USD) + ".00 USD"));

		log.info("TC_Step_: Check so nguoi huong");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), TransferMoneyQuick_Data.TransferQuick.RECEIVER_NAME);

		log.info("TC_Step_: Check ngan hang huong");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Ngân hàng hưởng"), Account_Data.Valid_Account.BANK[0]);

		log.info("TC_Step_: Check phi giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Phí giao dịch"), TransferMoneyQuick_Data.TransferQuick.COST_SUB[0]);

		log.info("TC_Step_: Check loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Loại giao dịch"), TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);

		log.info("TC_Step_: Check noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Nội dung giao dịch").contains(TransferMoneyQuick_Data.TransferQuick.NOTE));

		log.info("TC_Step_: Click chi tiet giao dich");
		transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_Step_: Chon button back");
		transferMoney.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

		log.info("TC_01_Step_Click button home");
		transferMoney.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	 @Test
	public void TC_05_ChuyenTienQuaTKNguoiChuyenTraPhiVNDXacThucMatKhau() throws InterruptedException {
		log.info("TC_01_Step_Click Chuyen tien nhanh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh 24/7");

		log.info("TC_01_Step_Select Chuyen tien nhanh qua tai khoan");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);

		log.info("TC_01_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_ACCOUNT_FROM[0]);

		log.info("TC_01_Step_Get so du kha dung");
		amountStartString = transferMoney.getDynamicAmountLabel(driver, "Số dư khả dụng").replaceAll("\\D+", "");

		amountStart = Long.parseLong(amountStartString);

		log.info("TC_01_Step_Nhap so tai khoan chuyen");
		transferMoney.inputToDynamicInputBox(driver, Account_Data.Valid_Account.ACCOUNT_TO, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_01_Step_Select ngan hang");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng hưởng");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.BANK[0]);

		log.info("TC_01_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.MONEY, "Số tiền");

		log.info("TC_01_Step_Chon phi giao dich");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[0]);

		log.info("TC_01_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE, "Thông tin giao dịch","3");

		log.info("TC_01_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_02_Step_Verify so tien chuyen");
		amountTranferString = transferMoney.getDynamicAmountLabel(driver, "Số tiền").replaceAll("\\D+", "");
		verifyEquals(amountTranferString, TransferMoneyQuick_Data.TransferQuick.MONEY);
		amountTranfer = Integer.parseInt(amountTranferString);

		log.info("TC_02_Step_Verify phi chuyen tien");
		costTranferString = transferMoney.getDynamicAmountLabel(driver, "Số tiền phí").replaceAll("\\D+", "");
		verifyEquals(costTranferString, TransferMoneyQuick_Data.TransferQuick.COST_AMOUNT_VND);
		costTranfer = Long.parseLong(costTranferString);

		log.info("TC_02_Step_Chon phuong thuc xac thuc");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[0]);

		log.info("TC_02_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_02_Step_Nhap ma xac thuc");
		transferMoney.inputToDynamicPopupPasswordInput(driver, LogIn_Data.Login_Account.NEW_PASSWORD, "Tiếp tục");

		log.info("TC_02_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Verify message thanh cong");
		verifyEquals(transferMoney.getTextInDynamicPopup(driver, "com.VCB:id/tvTitle"), TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY);

		log.info("TC_01_get thoi gian giao dich thanh cong");
		transferTime = transferMoney.getDynamicTransferTimeAndMoney(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY, "4");

		log.info("TC_01_Step_:Lay ma giao dich");
		transactionNumber = transferMoney.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC_01_Step_: Check ten nguoi thu huong");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), TransferMoneyQuick_Data.TransferQuick.RECEIVER_NAME);

		log.info("TC_01_Step_: So sanh tai khoan chuyen di");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, "Tài khoản đích"), Account_Data.Valid_Account.ACCOUNT_TO);

		log.info("TC_01_Step_: Check noi dung");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, "Nội dung"), TransferMoneyQuick_Data.TransferQuick.NOTE);

		log.info("TC_01_Step_: Chon thuc hien giao dich");
		transferMoney.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_01_Step_: Chon tai khoan chuyen");
		transferMoney.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_01_Step_: Chon tai khoan chuyen");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_ACCOUNT_FROM[0]);

		log.info("TC_01_Step_:Check so du kha dung sau khi chuyen tien");
		String amountAfterString = transferMoney.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng").replaceAll("\\D+", "");

		long amountAfter = Long.parseLong(amountAfterString);

		log.info("TC_01_Step_:Check so du kha dung sau khi chuyen tien");
		verifyEquals(amountStart - amountTranfer - costTranfer, amountAfter);
	}

	 @Test
	public void TC_06_BaoCaoGiaoDichChuyenTienNhanh() {
		homePage = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_Step_: Click back man hinh home");
		homePage.clickToDynamicBackIcon(driver, "Chuyển tiền nhanh 24/7");

		log.info("TC_Step_: Click menu header");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_Step_: Click bao cao giao dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

		log.info("TC_Step_: chon loai bao cao giao dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

		log.info("TC_Step_: Chon option chuyen tien nhanh qua tai khoan");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh qua số tài khoản");

		log.info("TC_Step_: Chon so tai khoan");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_Step_: Chon so tai khoan tra cuu");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_ACCOUNT_FROM[0]);

		log.info("TC_Step_: verify thoi tim kiem tu ngay");
		String dateStartActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate");
		String dateStartExpect = getBackwardDate(7);
		verifyEquals(dateStartActual, dateStartExpect);

		log.info("TC_Step_: verify thoi tim kiem tu ngay");
		String dateEndActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate");
		String dateEndtExpect = getForwardDate(0);
		verifyEquals(dateEndActual, dateEndtExpect);

		log.info("TC_Step_: Tim kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_Step_: Check ngay chuyen");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));

		log.info("TC_Step_: Check gio chuyen");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_Step_: Check ghi chu");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyQuick_Data.TransferQuick.NOTE));

		log.info("TC_Step_: Check so tien chuyen");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(TransferMoneyQuick_Data.TransferQuick.MONEY) + " VND"));

		log.info("TC_Step_: Chon ngay thang");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_Step_: Check ngay tim kiem");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch").contains(transferTime.split(" ")[0]));

		log.info("TC_Step_: check gio tim kiem");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_Step_: Check so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_Step_: Check tao khoan ghi no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), Account_Data.Valid_Account.LIST_ACCOUNT_FROM[0]);

		log.info("TC_Step_: Check tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản ghi có"), Account_Data.Valid_Account.ACCOUNT_TO);

		log.info("TC_Step_: Check so tien giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").contains(addCommasToLong(TransferMoneyQuick_Data.TransferQuick.MONEY) + " VND"));

		log.info("TC_Step_: Check so nguoi huong");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), TransferMoneyQuick_Data.TransferQuick.RECEIVER_NAME);

		log.info("TC_Step_: Check ngan hang huong");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Ngân hàng hưởng"), Account_Data.Valid_Account.BANK[0]);

		log.info("TC_Step_: Check phi giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Phí giao dịch"), TransferMoneyQuick_Data.TransferQuick.COST_SUB[0]);

		log.info("TC_Step_: Check loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Loại giao dịch"), TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);

		log.info("TC_Step_: Check noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Nội dung giao dịch").contains(TransferMoneyQuick_Data.TransferQuick.NOTE));

		log.info("TC_Step_: Chick chi tiet giao dich");
		transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_Step_: Chon button back");
		transReport.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

		log.info("TC_01_Step_Click button home");
		transReport.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	// @Test
	// Lỗi app, không thực hiện được giao dịch khi chọn phương thức xác thực là mật khẩu với chuyển tiền ngoại tệ (USD + EUR)
	public void TC_07_ChuyenTienQuaTaiKhoanNguoiChuyenTraPhiEURXacThucMatKhau() {
		log.info("TC_01_Step_Click Chuyen tien nhanh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh 24/7");

		log.info("TC_01_Step_Select Chuyen tien nhanh qua tai khoan");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);

		log.info("TC_01_Step_Select tai khoan nguon");

		transferMoney.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_ACCOUNT_FROM[2]);

		log.info("TC_01_Step_Get so du kha dung");
		amountStartString = transferMoney.getDynamicAmountLabel(driver, "Số dư khả dụng");

		double amountStart1 = convertMoneyToDouble(amountStartString, "EUR");

		log.info("TC_01_Step_Nhap so tai khoan chuyen");
		transferMoney.inputToDynamicInputBox(driver, Account_Data.Valid_Account.ACCOUNT_TO, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_01_Step_Select ngan hang");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng hưởng");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.BANK[0]);

		log.info("TC_01_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_EUR, "Số tiền");

		log.info("TC_01_Step_Chon phi giao dich");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[0]);

		log.info("TC_01_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE, "Thông tin giao dịch","3");

		log.info("TC_01_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_02_Step_Verify phi chuyen tien");
		double costTranfer = convertMoneyToDouble(TransferMoneyQuick_Data.TransferQuick.COST_AMOUNT_EUR, "EUR");

		log.info("TC_02_Step_Chon phuong thuc xac thuc");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[0]);

		log.info("TC_02_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_02_Step_Nhap ma xac thuc");
		transferMoney.inputToDynamicPopupPasswordInput(driver, LogIn_Data.Login_Account.NEW_PASSWORD, "Tiếp tục");

		log.info("TC_02_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_02_Verify message thanh cong");
		verifyEquals(transferMoney.getTextInDynamicPopup(driver, "com.VCB:id/tvContent"), TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY);

		log.info("TC_01_Step_: Lay ma giao dich");
		transactionNumber = transferMoney.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC_01_Step_: So sanh ten nguoi thu huong");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), TransferMoneyQuick_Data.TransferQuick.RECEIVER_NAME);

		log.info("TC_01_Step_: Tai khoan dich");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, "Tài khoản đích"), Account_Data.Valid_Account.ACCOUNT_TO);

		log.info("TC_01_Step_: So sanh noi dung");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, "Nội dung"), TransferMoneyQuick_Data.TransferQuick.NOTE);

		log.info("TC_01_Step_: Click giao dich moi");
		transferMoney.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_01_Step_: Click tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_01_Step_: Click tai khoan chuyen den");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_ACCOUNT_FROM[2]);

		log.info("TC_01_Step_Get so du kha dung");
		String amountAfterString = transferMoney.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		double amountAfter = convertMoneyToDouble(amountAfterString, "EUR");

		log.info("TC05_Step 38: Convert so tien chuyen");
		double transferMoney = convertMoneyToDouble(TransferMoneyQuick_Data.TransferQuick.MONEY_EUR, "EUR");

		log.info("TC05_Step 39: Kiem tra so du tai khoan EUR sau khi chuyen tien");
		verifyEquals(amountStart1 - transferMoney - costTranfer, amountAfter);
	}

	// @Test
	// Lỗi case 7 tạo giao dịch, nên case 8 báo cáo giao dịch không chạy đc
	public void TC_08_BaoCaoGiaoDichChuyenTienNhanh() {
		homePage = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_Step_: Click back man hinh home");
		homePage.clickToDynamicBackIcon(driver, "Chuyển tiền nhanh 24/7");

		log.info("TC_Step_: Click menu header");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_Step_: Click bao cao giao dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

		log.info("TC_Step_: chon loai bao cao giao dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

		log.info("TC_Step_: Chon option chuyen tien nhanh qua tai khoan");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh qua số tài khoản");

		log.info("TC_Step_: Chon so tai khoan");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_Step_: Chon so tai khoan chuyen");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_ACCOUNT_FROM[2]);

		log.info("TC_Step_: verify thoi tim kiem tu ngay");
		String dateStartActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate");
		String dateStartExpect = getBackwardDate(7);
		verifyEquals(dateStartActual, dateStartExpect);

		log.info("TC_Step_: verify thoi tim kiem tu ngay");
		String dateEndActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate");
		String dateEndtExpect = getForwardDate(0);
		verifyEquals(dateEndActual, dateEndtExpect);

		log.info("TC_Step_: Tim kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_Step_: Check ngay chuyen");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));

		log.info("TC_Step_: Check gio chuyen");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_Step_: Check ghi chu");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyQuick_Data.TransferQuick.NOTE));

		log.info("TC_Step_: Check so tien chuyen");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(TransferMoneyQuick_Data.TransferQuick.MONEY_EUR) + ".00 EUR"));

		log.info("TC_Step_: Chon ngay thang");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_Step_: Check ngay tim kiem");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch").contains(transferTime.split(" ")[0]));

		log.info("TC_Step_: check gio tim kiem");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_Step_: Check so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_Step_: Check tao khoan ghi no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), Account_Data.Valid_Account.LIST_ACCOUNT_FROM[0]);

		log.info("TC_Step_: Check tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản ghi có"), Account_Data.Valid_Account.ACCOUNT_TO);

		log.info("TC_Step_: Check so tien giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").contains(addCommasToLong(TransferMoneyQuick_Data.TransferQuick.MONEY_EUR) + ".00 EUR"));

		log.info("TC_Step_: Check so nguoi huong");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), TransferMoneyQuick_Data.TransferQuick.RECEIVER_NAME);

		log.info("TC_Step_: Check ngan hang huong");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Ngân hàng hưởng"), Account_Data.Valid_Account.BANK[0]);

		log.info("TC_Step_: Check phi giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Phí giao dịch"), TransferMoneyQuick_Data.TransferQuick.COST_SUB[0]);

		log.info("TC_Step_: Check loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Loại giao dịch"), TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);

		log.info("TC_Step_: Check noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Nội dung giao dịch").contains(TransferMoneyQuick_Data.TransferQuick.NOTE));

		log.info("TC_Step_: Chick chi tiet giao dich");
		transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_Step_: Chon button back");
		transferMoney.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

		log.info("TC_01_Step_Click button home");
		transferMoney.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@Test
	public void TC_09_ChuyenTienQuaSoTheCoPhiGiaoDichNguoiNhanTraVNDVaXacThucBangOTP() {
		log.info("TC_01_Step_Click Chuyen tien nhanh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh 24/7");

		log.info("TC_01_Step_Select Chuyen tien nhanh qua the");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[1]);

		log.info("TC_01_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, "Số thẻ chuyển đi");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_CARD_FROM[0]);

		log.info("TC_01_Step_Get so du kha dung");
		amountStartString = transferMoney.getDynamicAmountLabel(driver, "Số dư khả dụng").replaceAll("\\D+", "");

		log.info("TC_01_Step_Doi kieu du lieu");
		amountStart = Long.parseLong(amountStartString);

		log.info("TC_01_Step_Nhap so tai khoan chuyen");
		transferMoney.inputToDynamicInputBox(driver, Account_Data.Valid_Account.CARD_TO, "Nhập/chọn số thẻ chuyển đến VND");

		log.info("TC_01_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.MONEY, "Số tiền");

		log.info("TC_01_Step_Chon phi giao dich la nguoi nhan tra");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[1]);

		log.info("TC_01_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE, "Thông tin giao dịch","3");

		log.info("TC_01_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_Lay gia tri so tien chuyen");
		amountTranferString = transferMoney.getDynamicAmountLabel(driver, "Số tiền").replaceAll("\\D+", "");

		log.info("TC_01_Step_Verify so tien chuyen");
		verifyEquals(amountTranferString, TransferMoneyQuick_Data.TransferQuick.MONEY);

		log.info("TC_01_Step_doi kieu du lieu string -> long");
		amountTranfer = Long.parseLong(amountTranferString);

		log.info("TC_01_Step_Lay gia tri so tien phí chuyen");
		costTranferString = transferMoney.getDynamicAmountLabel(driver, "Số tiền phí").replaceAll("\\D+", "");

		log.info("TC_01_Step_Verify so tien phi");
		verifyEquals(costTranferString, TransferMoneyQuick_Data.TransferQuick.COST_AMOUNT_VND);

		log.info("TC_01_Step_doi kieu du lieu string -> long");
		costTranfer = Long.parseLong(costTranferString);

		log.info("TC_01_Step_Chon phuong thuc xac thuc");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[1]);

		log.info("TC_01_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_Nhap ma xac thuc");
		transferMoney.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		log.info("TC_01_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Verify message thanh cong");
		verifyEquals(transferMoney.getTextInDynamicPopup(driver, "com.VCB:id/tvTitle"), TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY);

		log.info("TC_01_get thoi gian giao dich thanh cong");
		transferTime = transferMoney.getDynamicTransferTimeAndMoney(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY, "4");

		log.info("TC_01_Step_: Get ma giao dich");
		transactionNumber = transferMoney.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC_01_Step_:Ten nguoi thu huong");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), TransferMoneyQuick_Data.TransferQuick.RECEIVER_NAME);

		log.info("TC_01_Step_: Tai khoan dich");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, "Tài khoản đích"), Account_Data.Valid_Account.ACCOUNT_TO);

		log.info("TC_01_Step_: Noi dung");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, "Nội dung"), TransferMoneyQuick_Data.TransferQuick.NOTE);

		log.info("TC_01_Step_: Thuc hien giao dich moi");
		transferMoney.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_01_Step_:Tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_01_Step_: Tai khoan chuyen");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_ACCOUNT_FROM[0]);

		log.info("TC_01_Step_:Check so du kha dung sau khi chuyen tien");
		String amountAfterString = transferMoney.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng").replaceAll("\\D+", "");

		long amountAfter = Long.parseLong(amountAfterString);

		log.info("TC_01_Step_:Check so du kha dung sau khi chuyen tien");
		verifyEquals(amountStart - amountTranfer - costTranfer, amountAfter);
	}

	// @Test
	public void TC_10_ReportChuyenTienQuaSoTheCoPhiGiaoDichNguoiNhanTraVNDVaXacThucBangOTP() {
		homePage = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_Step_: Click back man hinh home");
		homePage.clickToDynamicBackIcon(driver, "Chuyển tiền nhanh 24/7");

		log.info("TC_Step_: Click menu header");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_Step_: Click bao cao giao dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

		log.info("TC_Step_: chon loai bao cao giao dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

		log.info("TC_Step_: Chon option chuyen tien nhanh qua tai khoan");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh qua số thẻ");

		log.info("TC_Step_: Chon so tai khoan");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_Step_: Chon so tai khoan tra cuu");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_DEBIT_ACCOUNT[0]);

		log.info("TC_Step_: verify thoi tim kiem tu ngay");
		String dateStartActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate");
		String dateStartExpect = getBackwardDate(7);
		verifyEquals(dateStartActual, dateStartExpect);

		log.info("TC_Step_: verify thoi tim kiem tu ngay");
		String dateEndActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate");
		String dateEndtExpect = getForwardDate(0);
		verifyEquals(dateEndActual, dateEndtExpect);

		log.info("TC_Step_: Tim kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_Step_: Check ngay chuyen");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));

		log.info("TC_Step_: Check gio chuyen");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_Step_: Check ghi chu");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyQuick_Data.TransferQuick.NOTE));

		log.info("TC_Step_: Check so tien chuyen");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(TransferMoneyQuick_Data.TransferQuick.MONEY) + " VND"));

		log.info("TC_Step_: Click chi tiet giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_Step_: Check ngay tim kiem");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch").contains(transferTime.split(" ")[0]));

		log.info("TC_Step_: check gio tim kiem");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_Step_: Check so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_Step_: So the chuyen di");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số thẻ chuyển đi"), Account_Data.Valid_Account.LIST_CARD_FROM[0]);

		log.info("TC_Step_: Check tao khoan ghi no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), Account_Data.Valid_Account.LIST_DEBIT_ACCOUNT[0]);

		log.info("TC_Step_: Check tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số thẻ chuyển đến/ VND"), Account_Data.Valid_Account.CARD_TO);

		log.info("TC_Step_: Check so nguoi huong");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), TransferMoneyQuick_Data.TransferQuick.RECEIVER_NAME);

		log.info("TC_Step_: Check so tien giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").contains(addCommasToLong(TransferMoneyQuick_Data.TransferQuick.MONEY) + " VND"));

		log.info("TC_01_Step_Lay gia tri so tien phí chuyen");
		costTranferString = transReport.getDynamicTextInTransactionDetail(driver, "Số tiền phí").replaceAll("\\D+", "");

		log.info("TC_01_Step_Verify so tien phi");
		verifyEquals(costTranferString, TransferMoneyQuick_Data.TransferQuick.COST_AMOUNT_VND);

		log.info("TC_Step_: Check noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Nội dung giao dịch").contains(TransferMoneyQuick_Data.TransferQuick.NOTE));

		log.info("TC_Step_: Chick chi tiet giao dich");
		transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_Step_: Chon button back");
		transferMoney.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

		log.info("TC_01_Step_Click button home");
		transferMoney.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@Test
	public void TC_11_ChuyenTienQuaSoTheCoPhiGiaoDichNguoiNhanTraUSDVaXacThucBangOTP() {
		log.info("TC_01_Step_Click Chuyen tien nhanh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh 24/7");

		log.info("TC_01_Step_Select Chuyen tien nhanh qua the");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[1]);

		log.info("TC_01_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, "Số thẻ chuyển đi");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_CARD_FROM[1]);

		log.info("TC_01_Step_Get so du kha dung");
		amountStartString = transferMoney.getDynamicAmountLabel(driver, "Số dư khả dụng");
		double amountStart1 = convertMoneyToDouble(amountStartString, "USD");


		log.info("TC_01_Step_Doi kieu du lieu");
		amountStart = Long.parseLong(amountStartString);

		log.info("TC_01_Step_Nhap so tai khoan chuyen");
		transferMoney.inputToDynamicInputBox(driver, Account_Data.Valid_Account.CARD_TO, "Nhập/chọn số thẻ chuyển đến VND");

		log.info("TC_01_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_USD, "Số tiền");

		log.info("TC_01_Step_Chon phi giao dich la nguoi nhan tra");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[1]);

		log.info("TC_01_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE, "Thông tin giao dịch","3");

		log.info("TC_01_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_Lay gia tri so tien chuyen");
		amountTranferString = transferMoney.getDynamicAmountLabel(driver, "Số tiền").replaceAll("\\D+", "");

		log.info("TC_01_Step_Verify so tien chuyen");
		verifyEquals(amountTranferString, TransferMoneyQuick_Data.TransferQuick.MONEY);

		log.info("TC_01_Step_doi kieu du lieu string -> long");
		amountTranfer = Long.parseLong(amountTranferString);
//Check lại phí chuyển tiền
		log.info("TC_01_Step_Lay gia tri so tien phí chuyen");
		costTranferString = transferMoney.getDynamicAmountLabel(driver, "Số tiền phí").replaceAll("\\D+", "");
		log.info("TC_05_Lay phi chuyen tien");
		double costTranfer = convertMoneyToDouble(TransferMoneyQuick_Data.TransferQuick.COST_AMOUNT_USD, "USD");

		log.info("TC_01_Step_Verify so tien phi");
		verifyEquals(costTranferString, TransferMoneyQuick_Data.TransferQuick.COST_AMOUNT_VND);

		log.info("TC_01_Step_doi kieu du lieu string -> long");
		costTranfer = Long.parseLong(costTranferString);

		log.info("TC_01_Step_Chon phuong thuc xac thuc");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[1]);

		log.info("TC_01_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_Nhap ma xac thuc");
		transferMoney.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		log.info("TC_01_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Verify message thanh cong");
		verifyEquals(transferMoney.getTextInDynamicPopup(driver, "com.VCB:id/tvTitle"), TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY);

		log.info("TC_01_get thoi gian giao dich thanh cong");
		transferTime = transferMoney.getDynamicTransferTimeAndMoney(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY, "4");

		log.info("TC_01_Step_: Get ma giao dich");
		transactionNumber = transferMoney.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC_01_Step_:Ten nguoi thu huong");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), TransferMoneyQuick_Data.TransferQuick.RECEIVER_NAME);

		log.info("TC_01_Step_: Tai khoan dich");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, "Tài khoản đích"), Account_Data.Valid_Account.ACCOUNT_TO);

		log.info("TC_01_Step_: Noi dung");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, "Nội dung"), TransferMoneyQuick_Data.TransferQuick.NOTE);

		log.info("TC_01_Step_: Thuc hien giao dich moi");
		transferMoney.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_01_Step_:Tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_01_Step_: Tai khoan chuyen");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_ACCOUNT_FROM[1]);

		log.info("TC_01_Step_:Check so du kha dung sau khi chuyen tien");
		String amountAfterString = transferMoney.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		double amountAfter = convertMoneyToDouble(amountAfterString, "USD");

		log.info("TC05_Step 38: Convert so tien chuyen");
		double transferMoney = convertMoneyToDouble(TransferMoneyQuick_Data.TransferQuick.MONEY_USD, "USD");

		log.info("TC05_Step 39: Kiem tra so du tai khoan USD sau khi chuyen tien");
		verifyEquals(amountStart1 - transferMoney - costTranfer, amountAfter);
	}

}