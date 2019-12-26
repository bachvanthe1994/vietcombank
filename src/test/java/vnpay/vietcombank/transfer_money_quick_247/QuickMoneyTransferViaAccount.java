package vnpay.vietcombank.transfer_money_quick_247;

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
import vietcombank_test_data.TransferMoneyQuick_Data;

public class QuickMoneyTransferViaAccount extends Base {
	AndroidDriver<AndroidElement> driver;
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

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName) throws IOException, InterruptedException {
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

		log.info("Before class");
		login.clickToDynamicButton(driver, "CHO PHÉP");

		transferMoney = PageFactoryManager.getTransferMoneyObject(driver);
	}

	@Test
	public void TC_01_ChuyenTienCoPhiGiaoDichChonNguoiChuyenOTP() throws InterruptedException {
		log.info("TC_01_Step_Scoll den man hinh chuyen tien nhanh");
		transferMoney.scrollToText(driver, "Chuyển tiền tới ngân hàng khác");

		log.info("TC_01_Step_Click Chuyen tien nhanh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh 24/7");

		log.info("TC_01_Step_Select Chuyen tien nhanh qua tai khoan");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);

		log.info("TC_01_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.LIST_ACCOUNT_FROM[0]);

		log.info("TC_01_Step_Get so du kha dung");
		amountStartString = transferMoney.getDynamicAmountLabel(driver, "Số dư khả dụng").replaceAll("\\D+", "");

		log.info("TC_01_Step_Doi kieu du lieu");
		amountStart = Long.parseLong(amountStartString);

		log.info("TC_01_Step_Nhap so tai khoan chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.ACCOUNT_TO, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_01_Step_Select ngan hang");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng hưởng");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.BANK);

		log.info("TC_01_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.MONEY, "Số tiền");

		log.info("TC_01_Step_Chon phi giao dich la nguoi chuyen tra");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[0]);

		log.info("TC_01_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.NOTE, "Nội dung");

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
		verifyEquals(costTranferString, TransferMoneyQuick_Data.TransferQuick.COST_AMOUNT);

		log.info("TC_01_Step_doi kieu du lieu string -> long");
		costTranfer = Long.parseLong(costTranferString);

		log.info("TC_01_Step_Get ma giao dich");
		transactionNumber = transferMoney.getDynamicTextInTextViewLine2(driver, "Mã giao dịch");

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
		verifyEquals(transferMoney.getTextDynamicPopup(driver, TransferMoneyQuick_Data.TransferQuick.MESSAGE_SUCCESS), "CHUYỂN KHOẢN THÀNH CÔNG");

		log.info("TC_01_Verify message thanh cong");
		transferTime = transferMoney.getDynamicTransferTimeAndMoney(driver, TransferMoneyQuick_Data.TransferQuick.MESSAGE_SUCCESS, "4");

		log.info("TC_01_Step_: Get ma giao dich");
		transactionNumber = transferMoney.getDynamicTextInTextViewLine2(driver, "Mã giao dịch");

		log.info("TC_01_Step_:Ten nguoi thu huong");
		verifyEquals(transferMoney.getDynamicTextInTextViewLine2(driver, "Tên người thụ hưởng"), TransferMoneyQuick_Data.TransferQuick.RECEIVER_NAME);

		log.info("TC_01_Step_: Tai khoan dich");
		verifyEquals(transferMoney.getDynamicTextInTextViewLine2(driver, "Tài khoản đích"), TransferMoneyQuick_Data.TransferQuick.ACCOUNT_TO);

		log.info("TC_01_Step_: Noi dung");
		verifyEquals(transferMoney.getDynamicTextInTextViewLine2(driver, "Nội dung"), TransferMoneyQuick_Data.TransferQuick.NOTE);

		log.info("TC_01_Step_: Thuc hien giao dich moi");
		transferMoney.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_01_Step_:Tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_01_Step_: Tai khoan chuyen");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.LIST_ACCOUNT_FROM[0]);

		log.info("TC_01_Step_:Check so du kha dung sau khi chuyen tien");
		String amountAfterString = transferMoney.getDynamicTextInTextViewLine2(driver, "Số dư khả dụng");
		long amountAfter = Long.parseLong(amountAfterString);

		log.info("TC_01_Step_:Check so du kha dung sau khi chuyen tien");
		verifyEquals(amountStart - amountTranfer - costTranfer, amountAfter);
	}

	@Test
	public void TC_02_BaoCaoGiaoDichChuyenTienNhanh() {
		homePage = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_Step_: Click menu header");
		homePage.clickToDynamicBottomMenu(driver, "com.VCB:id/menu_5");

		log.info("TC_Step_: Click bao cao giao dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

		log.info("TC_Step_: chon loai bao cao giao dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

		log.info("TC_Step_: Chon option chuyen tien nhanh qua tai khoan");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh qua số tài khoản");

		log.info("TC_Step_: Chon time");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_Step_: Chon so tai khoan tra cuu");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.LIST_ACCOUNT_FROM[0]);

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
		verifyTrue(transReport.getDynamicTextInTextViewLine2(driver, "Thời gian giao dịch").contains(transferTime.split(" ")[0]));

		log.info("TC_Step_: check gio tim kiem");
		verifyTrue(transReport.getDynamicTextInTextViewLine2(driver, "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_Step_: Check so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTextViewLine2(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_Step_: Check tao khoan ghi no");
		verifyEquals(transReport.getDynamicTextInTextViewLine2(driver, "Tài khoản/thẻ trích nợ"), TransferMoneyQuick_Data.TransferQuick.LIST_ACCOUNT_FROM[0]);

		log.info("TC_Step_: Check tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTextViewLine2(driver, "Tài khoản ghi có"), TransferMoneyQuick_Data.TransferQuick.ACCOUNT_TO);

		log.info("TC_Step_: Check so tien giao dich");
		verifyTrue(transReport.getDynamicTextInTextViewLine2(driver, "Số tiền giao dịch").contains(addCommasToLong(TransferMoneyQuick_Data.TransferQuick.MONEY) + " VND"));

		log.info("TC_Step_: Check so nguoi huong");
		verifyEquals(transReport.getDynamicTextInTextViewLine2(driver, "Tên người hưởng"), TransferMoneyQuick_Data.TransferQuick.RECEIVER_NAME);

		log.info("TC_Step_: Check ngan hang huong");
		verifyEquals(transReport.getDynamicTextInTextViewLine2(driver, "Ngân hàng hưởng"), TransferMoneyQuick_Data.TransferQuick.BANK);

		log.info("TC_Step_: Check phi giao dich");
		verifyEquals(transReport.getDynamicTextInTextViewLine2(driver, "Phí giao dịch"), TransferMoneyQuick_Data.TransferQuick.COST_SUB[0]);

		log.info("TC_Step_: Check loai giao dich");
		verifyEquals(transReport.getDynamicTextInTextViewLine2(driver, "Loại giao dịch"), TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);

		log.info("TC_Step_: Check noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTextViewLine2(driver, "Nội dung giao dịch").contains(TransferMoneyQuick_Data.TransferQuick.NOTE));

		log.info("TC_Step_: Chick chi tiet giao dich");
		transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_Step_: Chon button back");
		transferMoney.navigateBack(driver);
	}

	@Test
	public void TC_03_ChuyenTienNhanhQuaTaiKhoanChonUSDNguoiChuyenTraPhiOTP() {
		log.info("TC_04_Step_Click Chuyen tien nhanh");
		transferMoney.scrollToText(driver, "Chuyển tiền tới ngân hàng khác");

		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh 24/7");

		log.info("TC_01_Step_Select Chuyen tien nhanh qua tai khoan");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);

		log.info("TC_04_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCOUNT_USD_FROM);

		log.info("TC_04_Step_Get so du kha dung");
		amountStartString = transferMoney.getDynamicAmountLabel(driver, "Số dư khả dụng").replaceAll("\\D+", "");

		amountStart = Long.parseLong(amountStartString);

		log.info("TC_04_Step_Nhap so tai khoan chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.ACCOUNT_TO, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_04_Step_Select ngan hang");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng hưởng");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.BANK);

		log.info("TC_04_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_USD, "Số tiền");

		log.info("TC_04_Step_Chon phi giao dich la nguoi chuyen");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[0]);

		log.info("TC_04_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.NOTE, "Nội dung");

		log.info("TC_04_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_05_Step_Verify so tien chuyen");
		amountTranferString = transferMoney.getDynamicAmountLabel(driver, "Số tiền").replace(".00 USD", "");
		verifyEquals(amountTranferString, TransferMoneyQuick_Data.TransferQuick.MONEY_USD);
		amountTranfer = Long.parseLong(amountTranferString);
		System.out.println(amountTranfer);

		log.info("TC_05_Step_Verify phi chuyen tien");
		costTranferString = transferMoney.getDynamicAmountLabel(driver, "Số tiền phí").replace(".00 USD", "");
		verifyEquals(costTranferString, TransferMoneyQuick_Data.TransferQuick.COST_AMOUNT);
		costTranfer = Long.parseLong(costTranferString);

		log.info("TC_05_Step_Chon phuong thuc xac thuc");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[1]);

		log.info("TC_05_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_05_Step_Nhap ma xac thuc");
		transferMoney.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		log.info("TC_05_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Verify message thanh cong");
		verifyEquals(transferMoney.getTextDynamicPopup(driver, TransferMoneyQuick_Data.TransferQuick.MESSAGE_SUCCESS), "CHUYỂN KHOẢN THÀNH CÔNG");

		log.info("TC_01_Step_:");
		transferTime = transferMoney.getDynamicTransferTimeAndMoney(driver, TransferMoneyQuick_Data.TransferQuick.MESSAGE_SUCCESS, "4");

		log.info("TC_01_Step_: Check ma giao dich");
		transactionNumber = transferMoney.getDynamicTextInTextViewLine2(driver, "Mã giao dịch");

		log.info("TC_01_Step_:Check ten nguoi thu huong");
		verifyEquals(transferMoney.getDynamicTextInTextViewLine2(driver, "Tên người thụ hưởng"), TransferMoneyQuick_Data.TransferQuick.RECEIVER_NAME);

		log.info("TC_01_Step_: Ccheck tai khoan dich");
		verifyEquals(transferMoney.getDynamicTextInTextViewLine2(driver, "Tài khoản đích"), TransferMoneyQuick_Data.TransferQuick.ACCOUNT_TO);

		log.info("TC_01_Step_: Check noi dung");
		verifyEquals(transferMoney.getDynamicTextInTextViewLine2(driver, "Nội dung"), TransferMoneyQuick_Data.TransferQuick.NOTE);

		log.info("TC_01_Step_: Click giao dich moi");
		transferMoney.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_01_Step_: Chon tai khoan chuyen den");
		transferMoney.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_01_Step_: Chon tai khoan chuyen den");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.LIST_ACCOUNT_FROM[0]);

		log.info("TC_01_Step_: Check so du");
		String amountAfterString = transferMoney.getDynamicTextInTextViewLine2(driver, "Số dư khả dụng");
		long amountAfter = Long.parseLong(amountAfterString);

		log.info("TC_01_Step_: Verify tai khoan sau khi chuyen");
		verifyEquals(amountStart - amountTranfer - costTranfer, amountAfter);
	}

	@Test
	public void TC_04_BaoCaoGiaoDichChuyenTienNhanh() {
		homePage = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_Step_: Click menu header");
		homePage.clickToDynamicBottomMenu(driver, "com.VCB:id/menu_5");

		log.info("TC_Step_: Click bao cao giao dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

		log.info("TC_Step_: chon loai bao cao giao dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

		log.info("TC_Step_: Chon option chuyen tien nhanh qua tai khoan");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh qua số tài khoản");

		log.info("TC_Step_: Chon time");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_Step_: Chon so tai khoan tra cuu");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.LIST_ACCOUNT_FROM[0]);

		log.info("TC_Step_: Tim kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_Step_: Check ngay chuyen");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));

		log.info("TC_Step_: Check gio chuyen");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_Step_: Check ghi chu");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyQuick_Data.TransferQuick.NOTE));

		log.info("TC_Step_: Check so tien chuyen");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(TransferMoneyQuick_Data.TransferQuick.MONEY) + ".00 USD"));

		log.info("TC_Step_: Chon ngay thang");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_Step_: Check ngay tim kiem");
		verifyTrue(transReport.getDynamicTextInTextViewLine2(driver, "Thời gian giao dịch").contains(transferTime.split(" ")[0]));

		log.info("TC_Step_: check gio tim kiem");
		verifyTrue(transReport.getDynamicTextInTextViewLine2(driver, "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_Step_: Check so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTextViewLine2(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_Step_: Check tao khoan ghi no");
		verifyEquals(transReport.getDynamicTextInTextViewLine2(driver, "Tài khoản/thẻ trích nợ"), TransferMoneyQuick_Data.TransferQuick.LIST_ACCOUNT_FROM[0]);

		log.info("TC_Step_: Check tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTextViewLine2(driver, "Tài khoản ghi có"), TransferMoneyQuick_Data.TransferQuick.ACCOUNT_TO);

		log.info("TC_Step_: Check so tien giao dich");
		verifyTrue(transReport.getDynamicTextInTextViewLine2(driver, "Số tiền giao dịch").contains(addCommasToLong(TransferMoneyQuick_Data.TransferQuick.MONEY) + ".00 USD"));

		log.info("TC_Step_: Check so nguoi huong");
		verifyEquals(transReport.getDynamicTextInTextViewLine2(driver, "Tên người hưởng"), TransferMoneyQuick_Data.TransferQuick.RECEIVER_NAME);

		log.info("TC_Step_: Check ngan hang huong");
		verifyEquals(transReport.getDynamicTextInTextViewLine2(driver, "Ngân hàng hưởng"), TransferMoneyQuick_Data.TransferQuick.BANK);

		log.info("TC_Step_: Check phi giao dich");
		verifyEquals(transReport.getDynamicTextInTextViewLine2(driver, "Phí giao dịch"), TransferMoneyQuick_Data.TransferQuick.COST_SUB[0]);

		log.info("TC_Step_: Check loai giao dich");
		verifyEquals(transReport.getDynamicTextInTextViewLine2(driver, "Loại giao dịch"), TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);

		log.info("TC_Step_: Check noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTextViewLine2(driver, "Nội dung giao dịch").contains(TransferMoneyQuick_Data.TransferQuick.NOTE));

		log.info("TC_Step_: Chick chi tiet giao dich");
		transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_Step_: Chon button back");
		transferMoney.navigateBack(driver);
	}

	@Test
	public void TC_05_ChuyenTienQuaTKNguoiChuyenTraPhiVNDXacThucMatKhau() throws InterruptedException {
		log.info("TC_01_Step_Click Chuyen tien nhanh");
		transferMoney.scrollToText(driver, "Chuyển tiền nhận bằng CMT");

		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh 24/7");

		log.info("TC_01_Step_Select Chuyen tien nhanh qua tai khoan");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);

		log.info("TC_01_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		;
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.LIST_ACCOUNT_FROM[0]);

		log.info("TC_01_Step_Get so du kha dung");
		amountStartString = transferMoney.getDynamicAmountLabel(driver, "Số dư khả dụng").replaceAll("\\D+", "");

		amountStart = Long.parseLong(amountStartString);

		log.info("TC_01_Step_Nhap so tai khoan chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.ACCOUNT_TO, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_01_Step_Select ngan hang");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng hưởng");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.BANK);

		log.info("TC_01_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.MONEY, "Số tiền");

		log.info("TC_01_Step_Chon phi giao dich");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[0]);

		log.info("TC_01_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.NOTE, "Nội dung");

		log.info("TC_01_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_02_Step_Verify so tien chuyen");
		amountTranferString = transferMoney.getDynamicAmountLabel(driver, "Số tiền").replaceAll("\\D+", "");
		verifyEquals(amountTranferString, TransferMoneyQuick_Data.TransferQuick.MONEY);
		amountTranfer = Integer.parseInt(amountTranferString);

		log.info("TC_02_Step_Verify phi chuyen tien");
		costTranferString = transferMoney.getDynamicAmountLabel(driver, "Số tiền phí").replaceAll("\\D+", "");
		verifyEquals(costTranferString, TransferMoneyQuick_Data.TransferQuick.COST_AMOUNT);
		costTranfer = Long.parseLong(costTranferString);

		log.info("TC_02_Step_Chon phuong thuc xac thuc");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[0]);

		log.info("TC_02_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_02_Step_Nhap ma xac thuc");
		transferMoney.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.NEW_PASSWORD, "Tiếp tục");

		log.info("TC_02_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_02_Verify message thanh cong");
		verifyEquals(transferMoney.getTextDynamicPopup(driver, TransferMoneyQuick_Data.TransferQuick.MESSAGE_SUCCESS), "CHUYỂN KHOẢN THÀNH CÔNG");

		log.info("TC_01_Step_:Verify message thanh cong ");
		transferTime = transferMoney.getDynamicTransferTimeAndMoney(driver, TransferMoneyQuick_Data.TransferQuick.MESSAGE_SUCCESS, "4");

		log.info("TC_01_Step_:Lay ma giao dich");
		transactionNumber = transferMoney.getDynamicTextInTextViewLine2(driver, "Mã giao dịch");

		log.info("TC_01_Step_: Check ten nguoi thu huong");
		verifyEquals(transferMoney.getDynamicTextInTextViewLine2(driver, "Tên người thụ hưởng"), TransferMoneyQuick_Data.TransferQuick.RECEIVER_NAME);

		log.info("TC_01_Step_: So sanh tai khoan chuyen di");
		verifyEquals(transferMoney.getDynamicTextInTextViewLine2(driver, "Tài khoản đích"), TransferMoneyQuick_Data.TransferQuick.ACCOUNT_TO);

		log.info("TC_01_Step_: Check noi dung");
		verifyEquals(transferMoney.getDynamicTextInTextViewLine2(driver, "Nội dung"), TransferMoneyQuick_Data.TransferQuick.NOTE);

		log.info("TC_01_Step_: Chon thuc hien giao dich");
		transferMoney.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_01_Step_: Chon tai khoan chuyen");
		transferMoney.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_01_Step_: Chon tai khoan chuyen");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.LIST_ACCOUNT_FROM[0]);

		log.info("TC_01_Step_: Lay so du kha dung");
		String afterBalanceOfAccount1 = transferMoney.getDynamicTextInTextViewLine2(driver, "Số dư khả dụng");
		long afterBalanceAmountOfAccount1 = Long.parseLong(afterBalanceOfAccount1);

		log.info("TC_01_Step_: So sanh so du con lai");
		long transferMoney1 = Long.parseLong(TransferMoneyQuick_Data.TransferQuick.MONEY);
		verifyEquals(amountStart - transferMoney1 - costTranfer, afterBalanceAmountOfAccount1);
	}

	public void TC_06_BaoCaoGiaoDichChuyenTienNhanh() {
		homePage = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_Step_: Click menu header");
		homePage.clickToDynamicBottomMenu(driver, "com.VCB:id/menu_5");

		log.info("TC_Step_: Click bao cao giao dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

		log.info("TC_Step_: chon loai bao cao giao dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

		log.info("TC_Step_: Chon option chuyen tien nhanh qua tai khoan");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh qua số tài khoản");

		log.info("TC_Step_: Chon time");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_Step_: Chon so tai khoan tra cuu");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.LIST_ACCOUNT_FROM[0]);

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
		verifyTrue(transReport.getDynamicTextInTextViewLine2(driver, "Thời gian giao dịch").contains(transferTime.split(" ")[0]));

		log.info("TC_Step_: check gio tim kiem");
		verifyTrue(transReport.getDynamicTextInTextViewLine2(driver, "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_Step_: Check so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTextViewLine2(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_Step_: Check tao khoan ghi no");
		verifyEquals(transReport.getDynamicTextInTextViewLine2(driver, "Tài khoản/thẻ trích nợ"), TransferMoneyQuick_Data.TransferQuick.LIST_ACCOUNT_FROM[0]);

		log.info("TC_Step_: Check tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTextViewLine2(driver, "Tài khoản ghi có"), TransferMoneyQuick_Data.TransferQuick.ACCOUNT_TO);

		log.info("TC_Step_: Check so tien giao dich");
		verifyTrue(transReport.getDynamicTextInTextViewLine2(driver, "Số tiền giao dịch").contains(addCommasToLong(TransferMoneyQuick_Data.TransferQuick.MONEY) + " VND"));

		log.info("TC_Step_: Check so nguoi huong");
		verifyEquals(transReport.getDynamicTextInTextViewLine2(driver, "Tên người hưởng"), TransferMoneyQuick_Data.TransferQuick.RECEIVER_NAME);

		log.info("TC_Step_: Check ngan hang huong");
		verifyEquals(transReport.getDynamicTextInTextViewLine2(driver, "Ngân hàng hưởng"), TransferMoneyQuick_Data.TransferQuick.BANK);

		log.info("TC_Step_: Check phi giao dich");
		verifyEquals(transReport.getDynamicTextInTextViewLine2(driver, "Phí giao dịch"), TransferMoneyQuick_Data.TransferQuick.COST_SUB[0]);

		log.info("TC_Step_: Check loai giao dich");
		verifyEquals(transReport.getDynamicTextInTextViewLine2(driver, "Loại giao dịch"), TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);

		log.info("TC_Step_: Check noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTextViewLine2(driver, "Nội dung giao dịch").contains(TransferMoneyQuick_Data.TransferQuick.NOTE));

		log.info("TC_Step_: Chick chi tiet giao dich");
		transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_Step_: Chon button back");
		transferMoney.navigateBack(driver);
	}

	@Test
	public void TC_07_ChuyenTienQuaTaiKhoanNguoiChuyenTraPhiEURXacThucMatKhau() {
		log.info("TC_01_Step_Click Chuyen tien nhanh");

		transferMoney.scrollToText(driver, "Chuyển tiền tới ngân hàng khác");

		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh 24/7");

		log.info("TC_01_Step_Select Chuyen tien nhanh qua tai khoan");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);

		log.info("TC_01_Step_Select tai khoan nguon");

		transferMoney.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCOUNT_EUR_FROM);

		log.info("TC_01_Step_Get so du kha dung");
		amountStartString = transferMoney.getDynamicAmountLabel(driver, "Số dư khả dụng").replaceAll("\\D+", "");

		amountStart = Long.parseLong(amountStartString);

		log.info("TC_01_Step_Nhap so tai khoan chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.ACCOUNT_TO, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_01_Step_Select ngan hang");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng hưởng");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.BANK);

		log.info("TC_01_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_EUR, "Số tiền");

		log.info("TC_01_Step_Chon phi giao dich");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[0]);

		log.info("TC_01_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.NOTE, "Nội dung");

		log.info("TC_01_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_02_Step_Verify so tien chuyen");

		amountTranferString = transferMoney.getDynamicAmountLabel(driver, "Số tiền").replaceAll("\\D+", "");
		verifyEquals(amountTranferString, TransferMoneyQuick_Data.TransferQuick.MONEY);
		amountTranfer = Integer.parseInt(amountTranferString);

		log.info("TC_02_Step_Verify phi chuyen tien");
		costTranferString = transferMoney.getDynamicAmountLabel(driver, "Số tiền phí").replaceAll("\\D+", "");
		verifyEquals(costTranferString, TransferMoneyQuick_Data.TransferQuick.COST_AMOUNT);
		costTranfer = Integer.parseInt(costTranferString);

		log.info("TC_02_Step_Get ma giao dich");
		transactionNumber = transferMoney.getDynamicTextInTextViewLine2(driver, "Mã giao dịch");

		log.info("TC_02_Step_Chon phuong thuc xac thuc");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[0]);

		log.info("TC_02_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_02_Step_Nhap ma xac thuc");
		transferMoney.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.NEW_PASSWORD, "Tiếp tục");

		log.info("TC_02_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_02_Verify message thanh cong");
		verifyEquals(transferMoney.getTextDynamicPopup(driver, TransferMoneyQuick_Data.TransferQuick.MESSAGE_SUCCESS), "CHUYỂN KHOẢN THÀNH CÔNG");

		log.info("TC_01_Step_: Lay ma giao dich");
		transactionNumber = transferMoney.getDynamicTextInTextViewLine2(driver, "Mã giao dịch");

		log.info("TC_01_Step_: So sanh ten nguoi thu huong");
		verifyEquals(transferMoney.getDynamicTextInTextViewLine2(driver, "Tên người thụ hưởng"), TransferMoneyQuick_Data.TransferQuick.RECEIVER_NAME);

		log.info("TC_01_Step_: Tai khoan dich");
		verifyEquals(transferMoney.getDynamicTextInTextViewLine2(driver, "Tài khoản đích"), TransferMoneyQuick_Data.TransferQuick.ACCOUNT_TO);

		log.info("TC_01_Step_: So sanh noi dung");
		verifyEquals(transferMoney.getDynamicTextInTextViewLine2(driver, "Nội dung"), TransferMoneyQuick_Data.TransferQuick.NOTE);

		log.info("TC_01_Step_: Click giao dich moi");
		transferMoney.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_01_Step_: Click tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_01_Step_: Click tai khoan chuyen den");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.LIST_ACCOUNT_FROM[0]);

		log.info("TC_01_Step_Get so du kha dung");
		String amountAfterString = transferMoney.getDynamicAmountLabel(driver, "Số dư khả dụng").replaceAll("\\D+", "");
		long amountAfter = Long.parseLong(amountAfterString);

		log.info("TC_01_Step_: So sanh so du con lai");
		verifyEquals(amountStart - amountTranfer - costTranfer, amountAfter);
	}

	@Test
	public void TC_08_BaoCaoGiaoDichChuyenTienNhanh() {
		homePage = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_Step_: Click menu header");
		homePage.clickToDynamicBottomMenu(driver, "com.VCB:id/menu_5");

		log.info("TC_Step_: Click bao cao giao dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

		log.info("TC_Step_: chon loai bao cao giao dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

		log.info("TC_Step_: Chon option chuyen tien nhanh qua tai khoan");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh qua số tài khoản");

		log.info("TC_Step_: Chon time");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_Step_: Chon so tai khoan tra cuu");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.LIST_ACCOUNT_FROM[0]);

		log.info("TC_Step_: Tim kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_Step_: Check ngay chuyen");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));

		log.info("TC_Step_: Check gio chuyen");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_Step_: Check ghi chu");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyQuick_Data.TransferQuick.NOTE));

		log.info("TC_Step_: Check so tien chuyen");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(TransferMoneyQuick_Data.TransferQuick.MONEY) + ".00 EUR"));

		log.info("TC_Step_: Chon ngay thang");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_Step_: Check ngay tim kiem");
		verifyTrue(transReport.getDynamicTextInTextViewLine2(driver, "Thời gian giao dịch").contains(transferTime.split(" ")[0]));

		log.info("TC_Step_: check gio tim kiem");
		verifyTrue(transReport.getDynamicTextInTextViewLine2(driver, "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_Step_: Check so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTextViewLine2(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_Step_: Check tao khoan ghi no");
		verifyEquals(transReport.getDynamicTextInTextViewLine2(driver, "Tài khoản/thẻ trích nợ"), TransferMoneyQuick_Data.TransferQuick.LIST_ACCOUNT_FROM[0]);

		log.info("TC_Step_: Check tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTextViewLine2(driver, "Tài khoản ghi có"), TransferMoneyQuick_Data.TransferQuick.ACCOUNT_TO);

		log.info("TC_Step_: Check so tien giao dich");
		verifyTrue(transReport.getDynamicTextInTextViewLine2(driver, "Số tiền giao dịch").contains(addCommasToLong(TransferMoneyQuick_Data.TransferQuick.MONEY) + ".00 EUR"));

		log.info("TC_Step_: Check so nguoi huong");
		verifyEquals(transReport.getDynamicTextInTextViewLine2(driver, "Tên người hưởng"), TransferMoneyQuick_Data.TransferQuick.RECEIVER_NAME);

		log.info("TC_Step_: Check ngan hang huong");
		verifyEquals(transReport.getDynamicTextInTextViewLine2(driver, "Ngân hàng hưởng"), TransferMoneyQuick_Data.TransferQuick.BANK);

		log.info("TC_Step_: Check phi giao dich");
		verifyEquals(transReport.getDynamicTextInTextViewLine2(driver, "Phí giao dịch"), TransferMoneyQuick_Data.TransferQuick.COST_SUB[0]);

		log.info("TC_Step_: Check loai giao dich");
		verifyEquals(transReport.getDynamicTextInTextViewLine2(driver, "Loại giao dịch"), TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);

		log.info("TC_Step_: Check noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTextViewLine2(driver, "Nội dung giao dịch").contains(TransferMoneyQuick_Data.TransferQuick.NOTE));

		log.info("TC_Step_: Chick chi tiet giao dich");
		transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_Step_: Chon button back");
		transferMoney.navigateBack(driver);
	}

}
