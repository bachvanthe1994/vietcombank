package vnpay.vietcombank.settingVCB_Smart_OTP;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import model.TransferCharity;
import model.TransferInVCBRecurrent;
import model.TransferOutSideVCB_Info;
import pageObjects.ConfirmMethodObject;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.LuckyGiftPageObject;
import pageObjects.TransactionReportPageObject;
import pageObjects.TransferIdentiryPageObject;
import pageObjects.TransferMoneyCharityPageObject;
import pageObjects.TransferMoneyInVcbPageObject;
import pageObjects.TransferMoneyObject;
import pageObjects.TransferMoneyOutSideVCBPageObject;
import vietcombankUI.DynamicPageUIs;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.Account_Data.Valid_Account;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.LuckyGift_Data;
import vietcombank_test_data.LuckyGift_Data.TitleLuckyGift;
import vietcombank_test_data.SettingVCBSmartOTP_Data;
import vietcombank_test_data.TransferIdentity_Data;
import vietcombank_test_data.TransferMoneyCharity_Data;
import vietcombank_test_data.TransferMoneyInVCB_Data;
import vietcombank_test_data.TransferMoneyQuick_Data;

public class Flow_SettingVCB_Smart_OTP_Part4 extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private ConfirmMethodObject smartOTP;
	private TransferMoneyObject transferMoney;
	private TransferMoneyInVcbPageObject transferInVCB;
	private TransferMoneyInVcbPageObject transferRecurrent;
	private TransferMoneyCharityPageObject transferMoneyCharity;
	private LuckyGiftPageObject luckyGift;
	private TransferIdentiryPageObject trasferPage;
	private String transferTime, transactionNumber, amountStartString, costTranferString, amountTranferString, exchangeRate, currentcy;
	private TransactionReportPageObject transReport;
	private TransferMoneyOutSideVCBPageObject transferMoneyOutSide;
	
	String tommorrowDate = getForwardDate(1);
	String today = getCurrentDay() + "/" + getCurrenMonth() + "/" + getCurrentYear();
	String expectAvailableBalance, moneyTransfer, account, user, content, code,source_account,beneficiary_account,money,wishes,surplusString, moneyFee;
	private String[] transferDate;
	private String[] getName;
	private Date date;
	long transferFee = 0;
	double transferFeeCurrentcy = 0;
	String password = "";
	private long surplus, availableBalance, actualAvailableBalance;
	private double surplusCurrentcy, availableBalanceCurrentcy, actualAvailableBalanceCurrentcy, toltalMoney, money_transferred;

	TransferInVCBRecurrent info = new TransferInVCBRecurrent(Account_Data.Valid_Account.ACCOUNT2, Account_Data.Valid_Account.DEFAULT_ACCOUNT3, "1", "Ngày", "", "", "500000", "Người chuyển trả", "test", "VCB - Smart OTP");
	TransferCharity info1 = new TransferCharity(Account_Data.Valid_Account.ACCOUNT2, TransferMoneyCharity_Data.ORGANIZATION, "100000", "Do Minh Duc", "So 18 ngo 3 Thai Ha", "Ho ngheo", "VCB - Smart OTP");
	TransferOutSideVCB_Info info2 = new TransferOutSideVCB_Info(Account_Data.Valid_Account.ACCOUNT2, "01825909301", "Do Minh Duc", "NHTMCP Tien Phong", "50000", "Phí giao dịch người chuyển trả", "test", "VCB - Smart OTP");
	private long amountStart, amountTranfer, fee, costTranfer;

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);

		smartOTP = PageFactoryManager.getConfirmMethodObject(driver);
		transferMoney = PageFactoryManager.getTransferMoneyObject(driver);
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferRecurrent = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferMoneyCharity = PageFactoryManager.getTransferMoneyCharityPageObject(driver);
		trasferPage = PageFactoryManager.getTransferIdentiryPageObject(driver);
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		luckyGift = PageFactoryManager.getLuckyGiftPageObject(driver);
		transferMoneyOutSide = PageFactoryManager.getTransferMoneyOutSideVCBPageObject(driver);
	}

	@Parameters({ "pass" })
@Test
	public void TC_01_CaiDatPhuongThucXacThucOTP() {
		log.info("TC_02_Step: Click menu header");
		smartOTP.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_02_Step: Click cai dat");
		smartOTP.clickToDynamicButtonLinkOrLinkText(driver, "Cài đặt");

		log.info("TC_02_Step: Click cai dat Smart OTP");
		smartOTP.clickToDynamicButtonLinkOrLinkText(driver, "Cài đặt VCB-Smart OTP");

		log.info("TC_02_Step: Click cai dat cho tai khoan");
		smartOTP.clickToDynamicTextFollowText(driver, "Chưa kích hoạt");

		log.info("TC_02_Step: Click toi dong y");
		smartOTP.clickToTextID(driver, "com.VCB:id/rule");

		log.info("TC_02_Step_click button dong y");
		smartOTP.clickToDynamicButton(driver, "Đồng ý");

		log.info("TC_02_Step_Nhap mat khau");
		smartOTP.inputToDynamicInputBox(driver, LogIn_Data.Login_Account.Smart_OTP, "Nhập mật khẩu");

		log.info("TC_02_Step_Nhap lai mat khau");
		smartOTP.inputToDynamicInputBox(driver, LogIn_Data.Login_Account.Smart_OTP, "Nhập lại mật khẩu");

		log.info("TC_02_Step_click button tiep tuc");
		smartOTP.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_02_Step_Nhap ma xac thuc");
		smartOTP.inputToDynamicSmartOtp(driver, "666888", "com.VCB:id/otp");

		log.info("TC_02_Step_click button tiep tuc");
		smartOTP.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_02_Step_Verify cai dat thanh cong");
		smartOTP.waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, "Cài đặt VCB-Smart OTP");
		verifyEquals(smartOTP.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/state_vnpay"), "Đã kích hoạt");

		log.info("TC_02_Step_click button quay lai cai dat");
		smartOTP.clickToDynamicImageViewID(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_02_Click button home");
		smartOTP.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	// Chuyển tiền nhanh 247
 @Test
 public void TC_03_ChuyenTienNhanhQuaTaiKhoanChonUSDNguoiChuyenTraPhiOTP() throws InterruptedException {
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh 24/7");

		log.info("TC_03_Step_Select Chuyen tien nhanh qua tai khoan");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);

		log.info("TC_03_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_ACCOUNT_FROM[1]);

		log.info("TC_03_Step_Get so du kha dung");
		amountStartString = transferMoney.getDynamicTextByLabel(driver, "Số dư khả dụng");
		double amountStart1 = convertMoneyToDouble(amountStartString, "USD");

		log.info("TC_03_Step_Nhap so tai khoan chuyen");
		transferMoney.inputIntoEditTextByID(driver, Account_Data.Valid_Account.ACCOUNT_TO, "com.VCB:id/edtContent1");

		log.info("TC_03_Step_:Lay so tien quy doi ra VND");
		String exchange = transferMoney.getDynamicTextInTransactionDetail(driver, "Tỷ giá quy đổi tham khảo").split(" ~ ", 2)[1];
		exchangeRate = exchange.replace(" VND", "");

		log.info("TC_03_Step_Select ngan hang");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng thụ hưởng");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.BANK[0]);

		log.info("TC_03_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_USD, "Số tiền");

		log.info("TC_03_Step_Chon phi giao dich la nguoi chuyen");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[0]);

		log.info("TC_03_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE, "Thông tin giao dịch", "3");

		log.info("TC_03_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_03_Step_Verify so tien chuyen");
//		verifyTrue(transferMoney.getDynamicTextInTransactionDetail(driver, "Số tiền(USD)").contains(addCommasToDouble(TransferMoneyQuick_Data.TransferQuick.MONEY_USD) + " USD"));

		log.info("TC_03_Step_Chon phuong thuc xac thuc");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[2]);

		log.info("TC_03_Step 23: Kiem tra so tien phi hien thi");
		String Fee = transferMoney.getDynamicTextInTransactionDetail(driver, "Số tiền phí").replaceAll("\\D+", "");

		log.info("TC_03_Step 23: So tien phi chuyen doi ra USD");
		double usdTransferFee = convertMoneyToDouble(Fee, "VND") / convertMoneyToDouble(exchangeRate, "VND");

		log.info("TC_03_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_Nhap ma xac thuc");
		transferMoney.inputToDynamicSmartOTP(driver, LogIn_Data.Login_Account.Smart_OTP, "com.VCB:id/otp");

		log.info("TC_01_Step_Tiep tuc");
		transferMoney.clickToDynamicContinue(driver, "com.VCB:id/submit");
		transferMoney.clickToDynamicContinue(driver, "com.VCB:id/btContinue");

		log.info("TC_03_Verify message thanh cong");
		verifyEquals(transferMoney.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY);

		log.info("TC_03_get thoi gian giao dich thanh cong");
		transferTime = transferMoney.getDynamicTransferTimeAndMoney(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY, "4").split(" ")[3];

		log.info("TC_03_Step_: Get ma giao dich");
		transactionNumber = transferMoney.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC_03_Step_:Ten nguoi thu huong");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, "Tên người thụ hưởng"), TransferMoneyQuick_Data.TransferQuick.RECEIVER_NAME);

		log.info("TC_03_Step_: Tai khoan dich");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, "Tài khoản thụ hưởng"), Account_Data.Valid_Account.ACCOUNT_TO);

		log.info("TC_03_Step_: Noi dung");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, "Nội dung"), TransferMoneyQuick_Data.TransferQuick.NOTE);

		log.info("TC_03_Step_: Thuc hien giao dich moi");
		transferMoney.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_03_Step_: Chon tai khoan chuyen den");
		transferMoney.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_03_Step_: Chon tai khoan chuyen den");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_ACCOUNT_FROM[1]);

		log.info("TC_03_Step: Lay so du kha dung tai khoan");
		String amountAfterString = transferMoney.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		double amountAfter = convertMoneyToDouble(amountAfterString, "USD");
		double amountExpect = Math.round((amountAfter) * 100) / 100;

		log.info("TC_03: Convert so tien chuyen");
		double transferMoney = convertMoneyToDouble(TransferMoneyQuick_Data.TransferQuick.MONEY_USD, " USD");

		log.info("TC_03: Kiem tra so du tai khoan USD sau khi chuyen tien");
		double amountAtual = Math.round((amountStart1 - transferMoney - usdTransferFee) * 100) / 100;
		verifyEquals(amountAtual, amountExpect);
	}

	

	// Chuyển tiền ngay trong VCB  
	 @Test
	public void TC_03_ChuyenTienNgayCoPhiGiaoDichNguoiChuyenTraVNDVaXacThucBangSmartOTP() throws InterruptedException {
		log.info("TC_01_Step_01: Click Chuyen tien trong VCB");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền trong VCB");

		log.info("TC_01_Step_02:Click tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_01_Step_03: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);
		transferInVCB.sleep(driver, 1000);

		log.info("TC_01_Step_04: Lay so du tai khoan dich");
		String beforeBalanceOfAccount2 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		long beforeBalanceAmountOfAccount2 = convertMoneyToLong(beforeBalanceOfAccount2, "VND");

		log.info("TC_01_Step_05:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_01_Step_06: Chon tai khoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.DEFAULT_ACCOUNT3);
		transferInVCB.sleep(driver, 1000);

		log.info("TC_01_Step_07: Lay so du tai khoan chuyen");
		String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		long beforeBalanceAmountOfAccount1 = convertMoneyToLong(beforeBalanceOfAccount1, "VND");

		log.info("TC_01_Step_08: Nhap tai khoan nhan");
		transferInVCB.inputIntoEditTextByID(driver, Account_Data.Valid_Account.ACCOUNT2, "com.VCB:id/edtContent1");

		log.info("TC_01_Step_09: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY, "Số tiền");

		log.info("TC_01_Step_10: Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Thông tin giao dịch", "3");

		log.info("TC_01_Step_11: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_12: Kiem tra hinh thuc chuyen tien hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Hình thức chuyển tiền"), TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[0]);

		log.info("TC_01_Step_13: Kiem tra tai khoan nguon hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), Account_Data.Valid_Account.DEFAULT_ACCOUNT3);

		log.info("TC_01_Step_14: Kiem tra tai khoan dich hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tài khoản đích/ VND"), Account_Data.Valid_Account.ACCOUNT2 + "/ ");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME_ACCOUNT_2));

		log.info("TC_01_Step_15: Kiem tra so tien chuyen hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, "Số tiền").contains(addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY)));

		log.info("TC_01_Step_17: Kiem tra phuong thuc tra phi");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.InputDataInVCB.COST[0]));

		log.info("TC_01_Step_19: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_01_Step_20: Chon Phuong thuc nhap");
		transferInVCB.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");

		log.info("TC_01_Step_21: Chon SMS OTP");
		String transferFee = transferInVCB.getDynamicTextByLabel(driver, "VCB - Smart OTP");
		fee = convertAvailableBalanceCurrentcyOrFeeToLong(transferFee);
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "VCB - Smart OTP");

		log.info("TC_01_Step_16: Kiem tra so tien phi hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, "Số tiền phí").contains(addCommasToLong(fee + "") + " VND"));

		log.info("TC_01_Step_18: Lay so tien phi");

		log.info("TC_01_Step_22: Click Tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_Nhap ma xac thuc");
		transferInVCB.inputToDynamicSmartOTP(driver, LogIn_Data.Login_Account.Smart_OTP, "com.VCB:id/otp");

		log.info("TC_01_Step_Tiep tuc");
		transferInVCB.clickToDynamicContinue(driver, "com.VCB:id/submit");
		transferInVCB.clickToDynamicContinue(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_25: Kiem  tra giao dich thanh cong");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE));

		log.info("TC_01_Step_26: Lay thoi gian tao giao dich");
		transferTime = transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE, "4").split(" ")[3];

		log.info("TC_01_Step_27: Lay ma giao dich");
		transactionNumber = transferInVCB.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC_01_Step_28: Kiem tra ten nguoi thu huong hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tên người thụ hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME_ACCOUNT_2);

		log.info("TC_01_Step_29: Kiem tra tai khoan dich hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tài khoản thụ hưởng"), Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_01_Step_30: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_01_Step_31: Click thuc hien giao dich moi");
		transferInVCB.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_01_Step_32: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_01_Step_33: Chon tai ngoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.DEFAULT_ACCOUNT3);
		transferInVCB.sleep(driver, 1000);

		log.info("TC_01_Step_34: Lay so du tai khoan chuyen");
		String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		long afterBalanceAmountOfAccount1 = convertMoneyToLong(afterBalanceOfAccount1, "VND");

		log.info("TC_01_Step_35: Kiem tra so du tai khoan chuyen sau khi thuc hien giao dich");
		long transferMoney = convertMoneyToLong(TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY, "VND");
		verifyEquals(beforeBalanceAmountOfAccount1 - transferMoney - fee, afterBalanceAmountOfAccount1);

		log.info("TC_01_Step_36: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_01_Step_37: Chon tai khoan nguoi nhan");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_01_Step_38: Lay so du kha dung tai khoan nhan");
		String afterBalanceOfAccount2 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");

		log.info("TC_01_Step_39: Kiem tra so du tai khoan nhan sau khi nhan tien");
		long afterBalanceAmountOfAccount2 = convertMoneyToLong(afterBalanceOfAccount2, "VND");
		verifyEquals(beforeBalanceAmountOfAccount2 + transferMoney, afterBalanceAmountOfAccount2);

		log.info("TC_02: Click back man hinh home");
		transferInVCB.clickToDynamicImageViewID(driver, "com.VCB:id/ivTitleLeft");
	}

	// Chuyển tiền tương lai --Da chuyen 
	 @Test
	 public void TC_07_ChuyenTienTuongLaiCoPhiGiaoDichNguoiChuyenTraUSDVaXacThucBangSmartOTP() {

			log.info("TC_07_Step_01: Click Chuyen tien trong VCB");
			transferInVCB.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

			log.info("TC_07_Step_02: Chon chuyen tien ngay gia tri hien tai");
			transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày giá trị hiện tại");

			log.info("TC_07_Step_03: Chon chuyen tien ngay gia tri hien tai");
			transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày tương lai");

			log.info("TC_07_Step_04:Click tai khoan nguon");
			transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

			log.info("TC_07_Step_05: Chon tai khoan dich");
			transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

			log.info("TC_07_Step_06: Lay so du tai khoan dich");
			String beforeBalanceOfAccount2 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
			long beforeBalanceAmountOfAccount2 = convertMoneyToLong(beforeBalanceOfAccount2, "VND");

			log.info("TC_07_Step_07:Click tai khoan nguon");
			transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

			log.info("TC_07_Step_08: Chon tai khoan chuyen");
			transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.USD_ACCOUNT);

			log.info("TC_07_Step_09: Lay so du tai khoan chuyen");
			String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
			double beforeBalanceAmountOfAccount1 = convertMoneyToDouble(beforeBalanceOfAccount1, "USD");

			log.info("TC_07_Step_10: Nhap tai khoan nhan");
			transferInVCB.inputToDynamicInputBox(driver, Account_Data.Valid_Account.ACCOUNT2, "Nhập/ chọn tài khoản thụ hưởng");

			log.info("TC_07_Step_11: Kiem tra Ngay hieu luc mac dinh");
			verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, tommorrowDate));

			log.info("TC_07_Step_12: Nhap so tien chuyen");
			transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInFutureForOTP.AMOUNT_OF_EUR_OR_USD_TRANSFER, "Số tiền");

			log.info("TC_07_Step_13: Nhap noi dung");
			transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Thông tin giao dịch", "3");

			log.info("TC_07_Step_14: Click tiep tuc");
			transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

			log.info("TC_07_Step_15: Kiem tra hinh thuc chuyen tien hien thi");
			verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Hình thức chuyển tiền"), TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[2]);

			log.info("TC_07_Step_16: Kiem tra tai khoan nguon hien thi");
			verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), Account_Data.Valid_Account.USD_ACCOUNT);

			log.info("TC_07_Step_17: Kiem tra tai khoan dich hien thi");
			verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tài khoản đích/ VND").contains(Account_Data.Valid_Account.ACCOUNT2));

			log.info("TC_07_Step_18: Kiem tra so tien chuyen hien thi");
			verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Số tiền"), String.format("%.02f", convertMoneyToDouble(TransferMoneyInVCB_Data.InputDataInFutureForPassword.AMOUNT_OF_EUR_OR_USD_TRANSFER, "USD")) + " USD");

			log.info("TC_07_Step_19: Kiem tra Ngay hieu luc hien thi");
			verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Ngày hiệu lực"), transferInVCB.getDayInWeek(tommorrowDate) + " " + tommorrowDate);

			log.info("TC_07_Step_20: Kiem tra phuong thuc tra phi");
			verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.InputDataInVCB.COST[0]));

			log.info("TC_07_Step_21: Kiem tra noi dung hien thi");
			verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

			log.info("TC_07_Step_22: Chon Phuong thuc nhap");
			transferInVCB.scrollDownToButton(driver, "Tiếp tục");
			transferInVCB.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");

			log.info("TC_07_Step_23: Chon SMS OTP");
			String transferFee = transferInVCB.getDynamicTextByLabel(driver, "SMS OTP");
			fee = convertAvailableBalanceCurrentcyOrFeeToLong(transferFee);
			transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

			log.info("TC_07_Step_24: Kiem tra so tien phi hien thi");
			verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), addCommasToLong(fee + "") + " VND");

			log.info("TC_07_Step_25: Click Tiep tuc");
			transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

			log.info("TC_07_Step_26: Nhap OTP");
			transferInVCB.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

			log.info("TC_07_Step_27: Click tiep tuc");
			transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

			log.info("TC_07_Step_28: Kiem  tra giao dich thanh cong");
			verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.SUCESSFULL_CREATED_ORDER));

			log.info("TC_07_Step_29: Kiem tra so tien chuyen");
			verifyEquals(transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.SUCESSFULL_CREATED_ORDER, "3"), (String.format("%.02f", convertMoneyToDouble(TransferMoneyInVCB_Data.InputDataInFutureForOTP.AMOUNT_OF_EUR_OR_USD_TRANSFER, "USD")) + " USD"));

			log.info("TC_07_Step_30: Lay thoi gian tao giao dich");
			transferTime = transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.SUCESSFULL_CREATED_ORDER, "4");
			verifyEquals(transferTime, TransferMoneyInVCB_Data.Output.ORDER_TIME + transferInVCB.getDayInWeek(tommorrowDate) + " " + tommorrowDate);

			log.info("TC_07_Step_31: Lay ma giao dich");
			transactionNumber = transferInVCB.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");
			log.info("TC_07_Step_32: Lay ma giao dich" + transactionNumber);

			log.info("TC_07_Step_33: Kiem tra ten nguoi thu huong hien thi");
			verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tên người thụ hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME_ACCOUNT_2);

			log.info("TC_07_Step_34: Kiem tra tai khoan dich hien thi");
			verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tài khoản thụ hưởng"), Account_Data.Valid_Account.ACCOUNT2);

			log.info("TC_07_Step_35: Kiem tra noi dung hien thi");
			verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

			log.info("TC_07_Step_36: Click thuc hien giao dich moi");
			transferInVCB.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

			log.info("TC_07_Step_37: Click tai khoan nguon");
			transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

			log.info("TC_07_Step_38: Chon tai ngoan chuyen");
			transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.USD_ACCOUNT);

			log.info("TC_07_Step_39: Lay so du tai khoan chuyen");
			String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
			double afterBalanceAmountOfAccount1 = convertMoneyToDouble(afterBalanceOfAccount1, "USD");

			log.info("TC_07_Step_40: Kiem tra so du tai khoan chuyen sau khi thuc hien giao dich");
			verifyEquals(beforeBalanceAmountOfAccount1, afterBalanceAmountOfAccount1);

			log.info("TC_07_Step_41: Click tai khoan nguon");
			transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

			log.info("TC_07_Step_42: Chon tai khoan nguoi nhan");
			transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

			log.info("TC_07_Step_43: Lay so du kha dung tai khoan nhan");
			String afterBalanceOfAccount2 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");

			log.info("TC_07_Step_44: Kiem tra so du tai khoan nhan sau khi nhan tien");
			long afterBalanceAmountOfAccount2 = convertMoneyToLong(afterBalanceOfAccount2, "VND");
			verifyEquals(beforeBalanceAmountOfAccount2, afterBalanceAmountOfAccount2);

		}

	// Chuyển tiền định kỳ
	//Lỗi crash app khi nhập xong phương thức xác thực smart OTP
	@Test
	 public void TC_05_ChuyenTien_NgoaiTe_DinhKy_2Ngay_CoPhiGiaoDichNguoiNhanTra_XacThucBangOTP() {
			log.info("TC_05_1_Click Chuyen tien trong ngan hang");
			transferRecurrent.scrollDownToText(driver, "Chuyển tiền tới ngân hàng khác");
			transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền trong VCB");

			log.info("TC_05_2_Chon phuong thuc chuyen tien");
			transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày giá trị hiện tại");
			transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");

			log.info("TC_05_3_Chon tai khoan nguon");
			transferRecurrent.clickToDynamicDropDown(driver, "Tài khoản nguồn");
			transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info1.sourceAccount);

			transferRecurrent.scrollUpToText(driver, "Tài khoản nguồn");
			expectAvailableBalance = transferRecurrent.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Số dư khả dụng");

			transferRecurrent.inputToDynamicInputBox(driver, info.destinationAccount, "Nhập/ chọn tài khoản thụ hưởng");

			log.info("TC_05_4_Chon tan suat");
			transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Ngày");
			transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info.frequencyCategory);
			transferRecurrent.inputFrequencyNumber(info.frequencyNumber);

			log.info("TC_05_5_Nhap so tien");
			transferRecurrent.inputToDynamicInputBox(driver, info1.money, "Số tiền");

			log.info("TC_05_6_Chon nguoi tra phi giao dich");
			transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
			transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info.fee);

			log.info("TC_05_7_Nhap noi dung");
			transferRecurrent.inputToDynamicInputBoxByHeader(driver, info.note, "Thông tin giao dịch", "3");

			log.info("TC_05_8_Click Tiep tuc");
			transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");

			log.info("TC_05_9_Kiem tra man hinh xac nhan thong tin");
			transferRecurrent.scrollUpToText(driver, "Hình thức chuyển tiền");
			verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Hình thức chuyển tiền"), "Chuyển tiền định kỳ");

			log.info("TC_05_9_1_Kiem tra tai khoan nguon");
			verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), info1.sourceAccount);

			log.info("TC_05_9_2_Kiem tra tai khoan dich");
			verifyTrue(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tài khoản đích/ VND").contains(info.destinationAccount));

			log.info("TC_05_9_3_Kiem tra tan suat");
			verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tần suất chuyển"), info.frequencyNumber + " " + info.frequencyCategory + "/ lần");

			log.info("TC_05_9_4_Kiem tra tan suat");
			verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Số lần giao dịch"), "2");

			log.info("TC_05_9_5_Kiem tra noi dung");
			verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Nội dung"), info.note);

			log.info("TC_05_10_Chon phuong thuc xac thuc");
			transferRecurrent.scrollDownToText(driver, "Chọn phương thức xác thực");
			transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
			transferFee = convertAvailableBalanceCurrentcyOrFeeToLong(transferRecurrent.getDynamicTextInTransactionDetail(driver, info1.authenticationMethod));
			transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info1.authenticationMethod);

			log.info("TC_05_11_Kiem tra so tien phi");
			verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), addCommasToLong(transferFee + "") + " VND");

			log.info("TC_05_12_Click Tiep tuc");
			transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");

			transferRecurrent.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

			transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");

			log.info("TC_05_13_Kiem tra man hinh Lap lenh thanh cong");
			verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY_IN_VCB_RECURRENT));

			transferTime = transferRecurrent.getTransferMoneyRecurrentTimeSuccess(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY_IN_VCB_RECURRENT);

			log.info("TC_05_13_1_Kiem tra ten nguoi huong thu");
			verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tên người thụ hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME_ACCOUNT_2);

			log.info("TC_05_13_2_Kiem tra tai khoan dich");
			verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tài khoản thụ hưởng"), info.destinationAccount);

			log.info("TC_05_13_3_Kiem tra ten noi dung");
			verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Nội dung"), info.note);

			log.info("TC_05_13_4_Kiem tra nut thuc hien giao dich moi");
			verifyTrue(transferRecurrent.isDynamicButtonDisplayed(driver, "Thực hiện giao dịch mới"));

			log.info("TC_05_14_Click Thuc hien giao dich moi");
			transferRecurrent.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		}

	// Chuyển tiền từ thiện
@Test
public void TC_03_ChuyenTienTuThienBangNgoaiTeThanhToanMatKhau() {
	log.info("TC_03_1_Click Chuyen tien tu thien");
	transferMoneyCharity.scrollDownToText(driver, "Trạng thái lệnh chuyển tiền");
	homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền từ thiện");

	log.info("TC_03_2_Chon tai khoan nguon");
	transferMoneyCharity.clickToDynamicDropDown(driver, "Tài khoản nguồn");
	transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info1.sourceAccount);

	surplusCurrentcy = convertAvailableBalanceCurrentcyToDouble(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));

	log.info("TC_03_3_Chon Quy/ To chuc tu thien");
	transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, "Quỹ/ Tổ chức từ thiện");
	transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info1.organization);

	currentcy = getCurrentcyMoney(transferMoneyCharity.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTiGia"));
	log.info("TC_03_4_Nhap so tien ung ho");
	transferMoneyCharity.inputToDynamicInputBox(driver, info1.money, "Số tiền ủng hộ");

	log.info("TC_03_5_Nhap ten nguoi ung ho");
	transferMoneyCharity.inputToDynamicInputBox(driver, info1.name, "Tên người ủng hộ");

	log.info("TC_03_6_Nhap dia chi ung ho");
	transferMoneyCharity.inputToDynamicInputBox(driver, info1.address, "Địa chỉ người ủng hộ");

	log.info("TC_03_7_Hoan canh nguoi ung ho");
	transferMoneyCharity.inputToDynamicInputBox(driver, info1.status, "Hoàn cảnh ủng hộ");

	log.info("TC_03_8_Click Tiep tuc");
	transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_03_9_Kiem tra man hinh xac nhan thong tin");
	log.info("TC_03_9_1_Kiem tra man tai khoan nguon");
	verifyEquals(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), info1.sourceAccount);

	log.info("TC_03_9_2_Kiem tra to chuc");
	verifyEquals(transferMoneyCharity.getDynamicTextInLine2DestinationAccount(driver, "Tài khoản đích"), info1.organization.toUpperCase());

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
	verifyEquals(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Hoàn cảnh ủng hộ"), info1.status);

	log.info("TC_03_10_Chon phuong thuc xac thuc");
	transferMoneyCharity.scrollDownToText(driver, "Mật khẩu đăng nhập");
	transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
	fee = convertAvailableBalanceCurrentcyOrFeeToLong(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, info1.authenticationMethod));
	transferFeeCurrentcy = convertVNeseMoneyToEUROOrUSD(String.valueOf(fee), currentcy);
	transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info1.authenticationMethod);

	log.info("TC_03_11_Click Tiep tuc");
	transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");

	transferMoneyCharity.inputToDynamicPopupPasswordInput(driver, password, "Tiếp tục");

	transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_03_12_Kiem tra man hinh Chuyen khoan thanh cong");
	verifyTrue(transferMoneyCharity.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyCharity_Data.SUCCESS_TRANSFER_MONEY));

	log.info("TC_03_12_1_Kiem tra ten nguoi huong");
	verifyEquals(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Tên người thụ hưởng"), info1.organization);

	log.info("TC_02_12_2_Kiem tra tai khoan dich");
	verifyEquals(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Tài khoản thụ hưởng"), destinationAccount);

	log.info("TC_03_12_3_Kiem tra nut thuc hien giao dich moi");
	verifyTrue(transferMoneyCharity.isDynamicButtonDisplayed(driver, "Thực hiện giao dịch mới"));

	log.info("TC_03_12_4_Lay ma giao dich");
	transferTime = transferMoneyCharity.getTransferTimeSuccess(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY);
	transactionNumber = transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

	log.info("TC_03_13_Click Thuc hien giao dich moi");
	transferMoneyCharity.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

	log.info("TC_03_14_Kiem tra so du kha dung luc sau");
	transferMoneyCharity.clickToDynamicDropDown(driver, "Tài khoản nguồn");
	transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info1.sourceAccount);
	actualAvailableBalanceCurrentcy = convertAvailableBalanceCurrentcyToDouble(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));
	availableBalanceCurrentcy = canculateAvailableBalancesCurrentcy(surplusCurrentcy, Double.parseDouble(info1.money), transferFeeCurrentcy);
	verifyEquals(actualAvailableBalanceCurrentcy, availableBalanceCurrentcy);
}

	//Chuyển tiền chứng minh thư
	@Test
	//Lỗi app không cập nhật số dưtài khoản sau khi chuyển
	@Parameters({ "pass" })
	public void TC_07_ChuyenTienVNDChoNguoNhanTaiQuayBangCMT_NguoiChuyenTraPhi_XacThucSmartOTP(String pass) throws InterruptedException {
		log.info("TC_01_STEP_1: chon Chuyển tiền nhận bằng tiền mặt");
		trasferPage.clickToDynamicIcon(driver, "Chuyển tiền nhận bằng tiền mặt");

		log.info("TC_01_STEP_2: chon tài khoản");
		trasferPage.clickToTextID(driver, "com.VCB:id/tvContent");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, Valid_Account.ACCOUNT2);

		log.info("TC_01_STEP_3: lấy ra số dư");
		trasferPage.scrollUpToText(driver, "Tài khoản nguồn");
		String getToltalMoney = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvInfoBottomRight");
		String[] toltal_money = getToltalMoney.split(" ");
		toltalMoney = Integer.parseInt(toltal_money[0].replace(",", ""));

		log.info("TC_01_Step_4: nhap ten nguoi thu huong");
		trasferPage.inputToDynamicInputBox(TransferIdentity_Data.textDataInputForm.USER_NAME, "Tên người thụ hưởng");

		log.info("TC_01_Step_5: chon giay to tuy than");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chứng minh nhân dân");

		log.info("TC_01_Step_6: so CMT");
		trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER, "Số");

		log.info("TC_01_Step_7: ngay cap");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Ngày cấp");
		trasferPage.clickToDynamicButton(driver, "OK");

		log.info("TC_01_Step_8: noi cap");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Nơi cấp");
		trasferPage.clickToDynamicTextIndex(driver, "0", TransferIdentity_Data.textDataInputForm.ISSUED);

		log.info("TC_01_STEP_9: nhap so tien bat dau la khong");
		trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_VND, "Số tiền");
		trasferPage.clickToTextID(driver, "com.VCB:id/tvTitle");

		log.info("TC_01_Step_10: noi dung");
		trasferPage.inputToDynamicInputBoxContent(driver, TransferIdentity_Data.textDataInputForm.CONTENT_TRANSFER, "3");

		log.info("TC_01_STEP_11: chon tiep tuc");
		trasferPage.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_05_STEP_12: chon phương thức xác thực");
		trasferPage.clickToTextID("com.VCB:id/tvptxt");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "VCB - Smart OTP");

		log.info("TC_01_STEP_12: lấy nội dung giao dịch");
		content = trasferPage.getMoneyByAccount(driver, "Nội dung");

		log.info("TC_01_STEP_21: lấy ra phí giao dịch");
		String getFee = trasferPage.getMoneyByAccount(driver, "Số tiền phí");
		String[] feeSplit = getFee.split(" ");
		fee = Integer.parseInt(feeSplit[0].replace(",", ""));

		log.info("TC_01_STEP_13: chon tiep tuc");
		trasferPage.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_Nhap ma xac thuc");
		trasferPage.inputToDynamicSmartOTP(driver, LogIn_Data.Login_Account.Smart_OTP, "com.VCB:id/otp");

		log.info("TC_01_Step_Tiep tuc");
		trasferPage.clickToDynamicContinue(driver, "com.VCB:id/submit");
		trasferPage.clickToDynamicContinue(driver, "com.VCB:id/btContinue");

		log.info("TC_01_STEP_16: lấy tra số tiền chuyển đi");
		moneyTransfer = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount");
		String[] getMoneyTransfer = moneyTransfer.split(" ");
		money_transferred = Integer.parseInt(getMoneyTransfer[0].replace(",", ""));

		log.info("TC_01_STEP_17: lấy ra time chuyển");
		String getDate = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTime");
		transferDate = getDate.split(" ");

		log.info("TC_01_STEP_18: lấy tên người hưởng");
		user = trasferPage.getMoneyByAccount(driver, "Tên người thụ hưởng");

		log.info("TC_01_STEP_19: lấy số CMT");
		user = trasferPage.getMoneyByAccount(driver, "Tài khoản thụ hưởng");

		log.info("TC_01_STEP_20: lấy mã giao dịch");
		code = trasferPage.getMoneyByAccount(driver, "Mã giao dịch");

		log.info("TC_01_STEP_23: chọn thực hiện giao dịch mới");
		trasferPage.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_02_STEP_22: kiểm tra số dư");
		trasferPage.scrollUpToText(driver, "Tài khoản nguồn");
		String surplus = trasferPage.getMoneyByAccount(driver, "Số dư khả dụng");
		String[] surplusSplit = surplus.split(" ");
		double surplusInt = Integer.parseInt(surplusSplit[0].replace(",", ""));
		double canculateAvailable = canculateAvailableBalances((long) toltalMoney, (long) fee, (long) money_transferred);
		//Lỗi app không cập nhật lại số dư tài khoản sau khi chuyển
		verifyEquals(surplusInt, canculateAvailable);

		log.info("TC_02: Click back man hinh home");
		trasferPage.clickToDynamicImageViewID(driver, "com.VCB:id/ivTitleLeft");
	}
	
	//Chuyển tiền ngoài VCB--da 
@Test
public void TC_05_ChuyenTienLienNganHangNgoaite_EUR_CoPhiGiaoDichNguoiChuyenTraXacThucBangOTP() {
	log.info("TC_05_1_Click Chuyen tien toi ngan hang khac");
	homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền tới ngân hàng khác");

	log.info("TC_05_2_Chon tai khoan nguon");
	transferMoneyOutSide.scrollUpToText(driver, "Tài khoản nguồn");
	transferMoneyOutSide.clickToDynamicDropDown(driver, "Tài khoản nguồn");
	transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info2.sourceAccount);
	transferMoneyOutSide.sleep(driver, 1000);
	transferMoneyOutSide.scrollUpToText(driver, "Tài khoản nguồn");
	surplusCurrentcy = convertAvailableBalanceCurrentcyToDouble(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));

	log.info("TC_05_3_Nhap tai khoan thu huong");
	transferMoneyOutSide.inputToDynamicInputBox(driver, info2.destinationAccount, "Nhập/ chọn tài khoản thụ hưởng");

	log.info("TC_05_4_Nhap ten nguoi huong");
	transferMoneyOutSide.inputToDynamicInputBox(driver, info2.name, "Tên người thụ hưởng");

	log.info("TC_05_5_Chon ngan hang huong");
	transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng thụ hưởng");
	transferMoneyOutSide.inputToDynamicInputBox(driver, info2.destinationBank, "Tìm kiếm");
	transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info2.destinationBank);

	currentcy = getCurrentcyMoney(transferMoneyOutSide.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTiGia"));
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
	verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tài khoản đích/ VND"), info2.destinationAccount + "/");

	log.info("TC_05_10_3_Kiem tra ten nguoi huong");
	verifyEquals(transferMoneyOutSide.getDynamicTextInLine2DestinationAccount(driver, "Tài khoản đích/ VND"), info2.name.toUpperCase());

	log.info("TC_05_10_4_Kiem tra ngan hang dich");
	verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Ngân hàng thụ hưởng"), info2.destinationBank);

	log.info("TC_05_10_5_Kiem tra so tien quy doi");
	String actualMoney = transferMoneyOutSide.getDynamicTextInTextViewLine2(driver, "Số tiền");
	String expectMoney = convertEURO_USDToVNeseMoney(info2.money, currentcy);
	verifyEquals(actualMoney, expectMoney);

	log.info("TC_05_10_6_Kiem tra so tien");
	verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số tiền"), addCommasToDouble(info2.money) + " EUR");
	log.info("TC_05_10_7_Kiem tra noi dung chuyen tien");
	verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Nội dung"), info2.note);

	log.info("TC_05_11_Chon phuong thuc xac thuc");
	transferMoneyOutSide.scrollDownToText(driver, "Chọn phương thức xác thực");
	transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
	transferFee = convertAvailableBalanceCurrentcyOrFeeToLong(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, info2.authenticationMethod));
	transferFeeCurrentcy = convertVNeseMoneyToEUROOrUSD(String.valueOf(transferFee), currentcy);
	transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info2.authenticationMethod);

	log.info("TC_05_11_01_Kiem tra so tien phi");
	verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), addCommasToLong(transferFee + "") + " VND");

	log.info("TC_05_12_Click Tiep tuc");
	transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");

	transferMoneyOutSide.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

	transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_05_13_Kiem tra man hinh Chuyen khoan thanh cong");
	log.info("TC_05_13_1_Kiem tra Chuyen khoan thanh cong");
	verifyTrue(transferMoneyOutSide.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY));

	log.info("TC_05_13_2_Kiem tra ten nguoi thu huong");
	verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tên người thụ hưởng"), info2.name);

	log.info("TC_05_13_3_Kiem tra tai khoan dich");
	verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tài khoản thụ hưởng"), info2.destinationAccount);

	log.info("TC_05_13_3_Kiem tra ngan hang huong");
	verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Ngân hàng thụ hưởng"), info2.destinationBank);

	log.info("TC_05_13_5_Kiem tra noi dung");
	verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Nội dung"), info2.note);

	log.info("TC_05_13_5_Kiem tra nut Thuc hien giao dich moi");
	verifyTrue(transferMoneyOutSide.isDynamicButtonDisplayed(driver, "Thực hiện giao dịch mới"));

	log.info("TC_05_13_6_Lay ma giao dich");
	transferTime = transferMoneyOutSide.getTransferTimeSuccess(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY);
	transactionNumber = transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

	log.info("TC_05_14_Click Thuc hien giao dich moi");
	transferMoneyOutSide.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

	log.info("TC_05_14_Kiem tra so du kha dung luc sau");
	transferMoneyOutSide.scrollUpToText(driver, "Tài khoản nguồn");
	transferMoneyOutSide.clickToDynamicDropDown(driver, "Tài khoản nguồn");
	transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info2.sourceAccount);
	transferMoneyOutSide.sleep(driver, 1000);
	transferMoneyOutSide.scrollUpToText(driver, "Tài khoản nguồn");
	actualAvailableBalanceCurrentcy = convertAvailableBalanceCurrentcyToDouble(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));
	availableBalanceCurrentcy = canculateAvailableBalancesCurrentcy(surplusCurrentcy, Double.parseDouble(info2.money), transferFeeCurrentcy);
	verifyEquals(actualAvailableBalanceCurrentcy, availableBalanceCurrentcy);

}

	//Quà tặng may mắn
	//@Test
	@Parameters({ "pass" })
	public void TC_09_NGuoiNhanTrongVCBBangSDTXacThucBangSmartOTP(String pass) throws InterruptedException {
		log.info("TC_01_Step_1: Chọn quà tặng may mắn");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.TitleLuckyGift.TITLE);

		log.info("TC_01_Step_2: chọn tài khoản nguồn");
		luckyGift.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);

		String moneySurplus = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/available_balance");
		String[] moneySurplusSplit = moneySurplus.split(" ");
		String moneySurplusReplace = moneySurplusSplit[0].replace(",", "");
		int surplus = Integer.parseInt(moneySurplusReplace);

		log.info("TC_01_Step_3: Thêm người nhận");
		luckyGift.clickToDynamicImageViewByID(driver, "com.VCB:id/ivAdd");

		log.info("TC_01_Step_4: Click tiep tuc popup");
		luckyGift.waitUntilPopUpDisplay("Hướng dẫn");
		luckyGift.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_5: chọn hình thức nhận");
		luckyGift.clickToTextID(driver, "com.VCB:id/tvHinhThucNhan");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, "Số điện thoại");

		log.info("TC_01_Step_6: nhập số điện thoại");
		luckyGift.inputToDynamicInputBox(driver, LuckyGift_Data.LuckyGift.MOBI_ACCEPT, TitleLuckyGift.TITLE_CHOISE_ACCOUNT);

		log.info("TC_01_Step_7: Nhap so tien chuyen");
		luckyGift.inputToDynamicInputBox(driver, LuckyGift_Data.LuckyGift.MONEY, TitleLuckyGift.TITLE_AMOUNT_MONEY);

		log.info("TC_01_Step_8: Chon loi chuc");
		luckyGift.clickToDynamicWishes(driver, "Nhập/chọn lời chúc");
		luckyGift.inputIntoEditTextByID(driver, LuckyGift_Data.LuckyGift.WISHES_OPTION, "com.VCB:id/content");
		luckyGift.clickToTextID(driver, "com.VCB:id/content_counter");

		log.info("TC_01_Step_9: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_10: lấy ra tài khoản nguồn");
		source_account = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAccSource");

		log.info("TC_01_Step_11: lấy ra lời chúc");
		wishes = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvLoichuc");

		log.info("TC_01_Step_12: lấy ra ten nguoi nhan");
		String userName = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvReceiver");
		getName = userName.split("/");

		log.info("TC_01_Step_13: lấy ra số tiền chuyển");
		money = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvSumAmount");

		log.info("TC_01_Step_14: lấy ra phí chuyển");
		moneyFee = luckyGift.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvSumfee");
		String[] sumFee = moneyFee.split(" ");
		int sumFeeInt = Integer.parseInt(sumFee[0].replace(",", ""));
		int surplusTotal = surplus - Integer.parseInt(LuckyGift_Data.LuckyGift.MONEY) - sumFeeInt;
		surplusString = String.valueOf(surplusTotal);

		log.info("TC_07_Step_14: chọn phương thức xác thực");
		luckyGift.clickToTextID(driver, "com.VCB:id/tvptxt");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, "VCB - Smart OTP");

		log.info("TC_07_Step_15: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_Nhap ma xac thuc");
		luckyGift.inputToDynamicSmartOTP(driver, LogIn_Data.Login_Account.Smart_OTP, "com.VCB:id/otp");

		log.info("TC_01_Step_Tiep tuc");
		luckyGift.clickToDynamicContinue(driver, "com.VCB:id/submit");
		luckyGift.clickToDynamicContinue(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_18: kiem tra loi chuc");
		String getWish = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/content");
		verifyEquals(wishes, getWish);

		log.info("TC_01_Step_19: lấy ra tên người thụ hưởng");
		beneficiary_account = luckyGift.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/info_receiver");
		verifyEquals(beneficiary_account, getName[1]);

		log.info("TC_01_Step_20: kiem tra so tien chuyen di");
		String getMoney = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/amount");
		verifyEquals(money, getMoney);

		log.info("TC_01_Step_21: kiem tra so tai khoan nguoi nhan");
		String getAccount = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAcc");
		verifyEquals(getName[0], getAccount);

		log.info("TC_01_Step_22: Click giao dịch mới");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, "Giao dịch mới");

		log.info("TC_01_Step_23: chọn tai khoản");
		luckyGift.clickToTextID(driver, "com.VCB:id/number_account");

		log.info("TC_01_Step_24: kiểm tra số dư");
		String moneyTransfer = luckyGift.getDynamicTextInTransactionDetail(driver, Account_Data.Valid_Account.ACCOUNT1);
		String[] moneyTransferSplit = moneyTransfer.split(" ");
		String moneyTransferReplace = moneyTransferSplit[0].replace(",", "");
		verifyEquals(moneyTransferReplace, surplusString);

		log.info("TC_07_Step_25: chọn đóng");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, "Đóng");

		log.info("TC_01_Step_26: Lấy thời gian thực hiện giao dịch");
		date = new Date();

		log.info("TC_01_Step_27 : Click home");
		luckyGift.clickToDynamicImageViewByID("com.VCB:id/left");
	}



	public void TC_111_HuyKichHoatVCBSmartOPT() {

		log.info("TC_01_Step_01: Click menu header");
		smartOTP.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_01_Step_02: Click thanh Cai dat VCB-Smart OTP");
		smartOTP.clickToDynamicButtonLinkOrLinkText(driver, "Cài đặt");

		log.info("TC_01_Step_03: Click Cai dat VCB Smart OTP");
		smartOTP.waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, "Cài đặt VCB-Smart OTP");
		smartOTP.clickToDynamicButtonLinkOrLinkText(driver, "Cài đặt VCB-Smart OTP");

		log.info("TC_01_Step_04: Verify man hinh cai dat VCB Smart OTP");
		smartOTP.isDynamicMessageAndLabelTextDisplayed(driver, "Cài đặt VCB-Smart OTP");

		log.info("TC_02_Step_01: Click btn Huy Cai dat");
		smartOTP.clickToDynamicButtonLinkOrLinkText(driver, "Hủy");

		log.info("TC_02_Step_02: Verify hien thi popup xac nhan huy cai dat OTP");
		smartOTP.isDynamicMessageAndLabelTextDisplayed(driver, SettingVCBSmartOTP_Data.MESSEGE_CONFIRM_CANCEL);

		log.info("TC_02_Step_03: Verify hien thi popup xac nhan huy cai dat OTP");
		smartOTP.clickToDynamicButtonLinkOrLinkText(driver, "Có");

//		log.info("-------------------------------TC_02_Step_04: Verify xac nhan huy Smart OTP thanh cong----------------");
//		smartOTP.isDynamicMessageAndLabelTextDisplayed(driver, SettingVCBSmartOTP_Data.MESSEGE_CANCEL_SMART_OTP);

		log.info("TC_02_Step_05: verify Trang thai dã kich hoat Smart OTP");
		smartOTP.waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, "Cài đặt VCB-Smart OTP");
		smartOTP.isDynamicMessageAndLabelTextDisplayed(driver, "Chưa kích hoạt");
	}

}