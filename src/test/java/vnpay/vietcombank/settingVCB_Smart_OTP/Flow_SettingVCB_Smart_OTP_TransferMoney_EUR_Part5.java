package vnpay.vietcombank.settingVCB_Smart_OTP;

import java.io.IOException;
import java.math.BigDecimal;

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
import pageObjects.LogInPageObject;
import pageObjects.SettingVCBSmartOTPPageObject;
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
import vietcombank_test_data.SettingVCBSmartOTP_Data;
import vietcombank_test_data.TransferIdentity_Data;
import vietcombank_test_data.TransferMoneyCharity_Data;
import vietcombank_test_data.TransferMoneyInVCB_Data;
import vietcombank_test_data.TransferMoneyQuick_Data;

public class Flow_SettingVCB_Smart_OTP_TransferMoney_EUR_Part5 extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private SettingVCBSmartOTPPageObject smartOTP;
	private TransferMoneyObject transferMoney;
	private TransferMoneyInVcbPageObject transferInVCB;
	private TransferMoneyInVcbPageObject transferRecurrent;
	private TransferMoneyCharityPageObject transferMoneyCharity;
	private TransferIdentiryPageObject trasferPage;
	private String transferTime, transactionNumber, amountStartString,  exchangeRate, currentcy;
	private TransactionReportPageObject transReport;
	private TransferMoneyOutSideVCBPageObject transferMoneyOutSide;
	private String[] transferDate;
	String tommorrowDate = getForwardDate(1);
	String today = getCurrentDay() + "/" + getCurrenMonth() + "/" + getCurrentYear();
	String expectAvailableBalance, moneyTransfer, account, user, content, code,source_account,beneficiary_account,money,wishes,surplusString, moneyFee;
	long transferFee ,fee= 0;
	double transferFeeCurrentcy = 0;
	String password = "";

	private double surplusCurrentcy, availableBalanceCurrentcy, actualAvailableBalanceCurrentcy, toltalMoney, money_transferred,exchangeRate1;

	TransferInVCBRecurrent info = new TransferInVCBRecurrent(Account_Data.Valid_Account.EUR_ACCOUNT, Account_Data.Valid_Account.DEFAULT_ACCOUNT3, "1", "Ngày", "", "", "500000", "Người chuyển trả", "test", "VCB - Smart OTP");
	TransferCharity info1 = new TransferCharity(Account_Data.Valid_Account.EUR_ACCOUNT, TransferMoneyCharity_Data.ORGANIZATION, "10", "Do Minh Duc", "So 18 ngo 3 Thai Ha", "Ho ngheo", "VCB - Smart OTP");
	TransferOutSideVCB_Info info2 = new TransferOutSideVCB_Info(Account_Data.Valid_Account.EUR_ACCOUNT, "01825909301", "Do Minh Duc", "NHTMCP Tien Phong", "5", "Phí giao dịch người chuyển trả", "test", "VCB - Smart OTP");


	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);

		smartOTP = PageFactoryManager.getSettingVCBSmartOTPPageObject(driver);
		transferMoney = PageFactoryManager.getTransferMoneyObject(driver);
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferRecurrent = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferMoneyCharity = PageFactoryManager.getTransferMoneyCharityPageObject(driver);
		trasferPage = PageFactoryManager.getTransferIdentiryPageObject(driver);
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transferMoneyOutSide = PageFactoryManager.getTransferMoneyOutSideVCBPageObject(driver);
	}

	@Parameters({ "pass" })
@Test
	public void TC_01_CaiDatPhuongThucXacThucOTP() {
		log.info("TC_01_Step: Click menu header");
		smartOTP.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_01_Step: Click cai dat");
		smartOTP.clickToDynamicButtonLinkOrLinkText(driver, "Cài đặt");

		log.info("TC_01_Step: Click cai dat Smart OTP");
		smartOTP.clickToDynamicButtonLinkOrLinkText(driver, "Cài đặt VCB-Smart OTP");

		log.info("TC_01_Step: Click cai dat cho tai khoan");
		smartOTP.clickToDynamicTextFollowText(driver, "Chưa kích hoạt");

		log.info("TC_01_Step: Click toi dong y");
		smartOTP.clickToTextID(driver, "com.VCB:id/rule");

		log.info("TC_01_Step_click button dong y");
		smartOTP.clickToDynamicButton(driver, "Đồng ý");

		log.info("TC_01_Step_Nhap mat khau");
		smartOTP.inputToDynamicInputBox(driver, LogIn_Data.Login_Account.Smart_OTP, "Nhập mật khẩu");

		log.info("TC_01_Step_Nhap lai mat khau");
		smartOTP.inputToDynamicInputBox(driver, LogIn_Data.Login_Account.Smart_OTP, "Nhập lại mật khẩu");

		log.info("TC_01_Step_click button tiep tuc");
		smartOTP.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_Nhap ma xac thuc");
		smartOTP.inputToDynamicSmartOtp(driver, "666888", "com.VCB:id/otp");

		log.info("TC_01_Step_click button tiep tuc");
		smartOTP.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_Verify cai dat thanh cong");
		smartOTP.waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, "Cài đặt VCB-Smart OTP");
		verifyEquals(smartOTP.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/state_vnpay"), "Đã kích hoạt");

		log.info("TC_01_Step_click button quay lai cai dat");
		smartOTP.clickToDynamicImageViewID(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_01_Click button home");
		smartOTP.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	// Chuyển tiền nhanh 247
@Test
 public void TC_02_ChuyenTienNhanhQuaTaiKhoanChonEURNguoiChuyenTraPhiSmartOTP() throws InterruptedException {
	transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh 24/7");

	log.info("TC_02_Step_Select Chuyen tien nhanh qua tai khoan");
	transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
	transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);

	log.info("TC_02_Step_Select tai khoan nguon");
	transferMoney.clickToDynamicDropDown(driver, "Tài khoản nguồn");
	transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.EUR_ACCOUNT);

	log.info("TC_02_Step_Get so du kha dung");
	amountStartString = transferMoney.getDynamicTextByLabel(driver, "Số dư khả dụng");
	double amountStart1 = convertMoneyToDouble(amountStartString, "EUR");

	log.info("TC_02_Step_Nhap so tai khoan chuyen");
	transferMoney.inputIntoEditTextByID(driver, Account_Data.Valid_Account.ACCOUNT_TO, "com.VCB:id/edtContent1");
	

	log.info("TC_02_Step_:Lay so tien quy doi ra VND");
	String exchange = transferMoney.getDynamicTextInTransactionDetail(driver, "Tỷ giá quy đổi tham khảo").split(" ~ ", 2)[1];
	exchangeRate = exchange.replace(" VND", "");

	log.info("TC_02_Step_Select ngan hang");
	transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng thụ hưởng");
	transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.BANK[0]);

	log.info("TC_02_Step_Nhap so tien chuyen");
	transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_EUR, "Số tiền");

	log.info("TC_02_Step_Chon phi giao dich la nguoi chuyen");
	transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[0]);
	transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[0]);

	log.info("TC_02_Step_Nhap noi dung");
	transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE, "Thông tin giao dịch", "3");

	log.info("TC_02_Step_Tiep tuc");
	transferMoney.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_02_Step_Verify so tien chuyen");
	//Lỗi app
//	verifyTrue(transferMoney.getDynamicTextInTransactionDetail(driver, "Số tiền(EUR)").contains(addCommasToDouble(TransferMoneyQuick_Data.TransferQuick.MONEY_EUR) + " EUR"));

	log.info("TC_02_Step_Chon phuong thuc xac thuc");
	transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[0]);
	transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[2]);

	log.info("TC_02_Step 23: Kiem tra so tien phi hien thi");
	String Fee = transferMoney.getDynamicTextInTransactionDetail(driver, "Số tiền phí").replaceAll("\\D+", "");

	log.info("TC_02_Step 23: So tien phi chuyen doi ra EUR");
	double EURTransferFee = convertMoneyToDouble(Fee, "VND") / convertMoneyToDouble(exchangeRate, "VND");

	log.info("TC_02_Step_Tiep tuc");
	transferMoney.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_02_Step_Nhap ma xac thuc");
	transferMoney.inputToDynamicSmartOTP(driver, LogIn_Data.Login_Account.Smart_OTP, "com.VCB:id/otp");

	log.info("TC_02_Step_Tiep tuc");
	transferMoney.clickToDynamicContinue(driver, "com.VCB:id/submit");
	transferMoney.clickToDynamicContinue(driver, "com.VCB:id/btContinue");

	log.info("TC_02_Verify message thanh cong");
	transferMoney.scrollUpToText(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY);
	verifyEquals(transferMoney.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY);

	log.info("TC_02_get thoi gian giao dich thanh cong");
	transferTime = transferMoney.getDynamicTransferTimeAndMoney(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY, "4").split(" ")[3];

	log.info("TC_02_Step_: Get ma giao dich");
	transactionNumber = transferMoney.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

	log.info("TC_02_Step_:Ten nguoi thu huong");
	verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, "Tên người thụ hưởng"), TransferMoneyQuick_Data.TransferQuick.RECEIVER_NAME);

	log.info("TC_02_Step_: Tai khoan dich");
	verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, "Tài khoản thụ hưởng"), Account_Data.Valid_Account.ACCOUNT_TO);

	log.info("TC_02_Step_: ngan hang thu huong");
	verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, "Ngân hàng thụ hưởng"), Account_Data.Valid_Account.BANK[0]);

	log.info("TC_02_Step_: Noi dung");
	verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, "Nội dung"), TransferMoneyQuick_Data.TransferQuick.NOTE);

	log.info("TC_02_Step_: Thuc hien giao dich moi");
	transferMoney.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

	log.info("TC_02_Step_: Chon tai khoan chuyen den");
	transferMoney.clickToDynamicDropDown(driver, "Tài khoản nguồn");

	log.info("TC_02_Step_: Chon tai khoan chuyen den");
	transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.EUR_ACCOUNT);

	log.info("TC_02_Step: Lay so du kha dung tai khoan");
	String amountAfterString = transferMoney.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
	double amountAfter = convertMoneyToDouble(amountAfterString, "EUR");
	double amountExpect = Math.round((amountAfter) * 100) / 100;

	log.info("TC_02: Convert so tien chuyen");
	double transferMoney1 = convertMoneyToDouble(TransferMoneyQuick_Data.TransferQuick.MONEY_EUR, " EUR");

	log.info("TC_02: Kiem tra so du tai khoan EUR sau khi chuyen tien");
	double  amountAtual = Math.round((amountStart1 - transferMoney1 - EURTransferFee) * 100) / 100 ;
	verifyEquals(amountAtual, amountExpect );
	
	log.info("TC_02: Click back man hinh home");
	transferMoney.clickToDynamicImageViewID(driver, "com.VCB:id/ivTitleLeft");
	}

	// Chuyển tiền ngay trong VCB  ---Da chuyen
 @Test
 public void TC_03_ChuyenTienNgayCoPhiGiaoDichNguoiChuyenTraEURVaXacThucBangSmartOTP() throws InterruptedException {
	 log.info("TC_03_Step 01: Click Chuyen tien trong VCB");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền trong VCB");

		log.info("TC_03_Step 02: Click chon tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_03_Step 06: Chon EUR account");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.EUR_ACCOUNT);
		transferInVCB.sleep(driver, 1000);

		log.info("TC_03_Step 07: Lay so du tai khoan EUR");
		String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		double beforeBalanceAmountOfAccount1 = convertMoneyToDouble(beforeBalanceOfAccount1, "EUR");

		log.info("TC_03_Step 08: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, Account_Data.Valid_Account.DEFAULT_ACCOUNT3, "Nhập/ chọn tài khoản thụ hưởng");

		log.info("TC_03_Step 09: Nhap so tien can chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER, "Số tiền");
		
		log.info("TC_03_Step_:Lay so tien quy doi ra VND");
		String exchange = transferMoney.getDynamicTextInTransactionDetail(driver, "Tỷ giá quy đổi tham khảo").split(" ~ ", 2)[1];
		exchangeRate = exchange.replace(" VND", "");

		log.info("TC_03_Step 11: Chon phuong thuc xac thuc");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");

		log.info("TC_03_Step 12: Chon phi giao dich nguoi chuyen tra");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Người chuyển trả");

		log.info("TC_03_Step 13:  Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Thông tin giao dịch", "3");

		log.info("TC_03_Step 14: Click tiep tuc");

		exchangeRate1 = convertAvailableBalanceCurrentcyToDouble(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tỷ giá quy đổi tham khảo"));
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_03_Step 15: Kiem tra hinh thuc chuyen tien hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Hình thức chuyển tiền"), TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[0]);

		log.info("TC_03_Step 16: Kiem tra tai khoan nguon hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), Account_Data.Valid_Account.EUR_ACCOUNT);

		log.info("TC_03_Step 17: Kiem tra tai khoan dich hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tài khoản đích/ VND").contains(Account_Data.Valid_Account.DEFAULT_ACCOUNT3));

		log.info("TC_03_Step 18: Kiem tra so tien chuyen hien thi");
//		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, "Số tiền(EUR)").contains(addCommasToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " EUR"));

		log.info("TC_03_Step 19: Kiem tra Nguoi chuyen tra hien thi");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.InputDataInVCB.COST[0]));

		log.info("TC_03_Step 20: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_03_Step 21: Chon phuong thuc xac thuc");
		transferInVCB.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");

		log.info("TC_03_Step 22: Chon Smart OTP");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "VCB - Smart OTP");
		
		log.info("TC_03_Step 23: Kiem tra so tien phi hien thi");
		String Fee = transferMoney.getDynamicTextInTransactionDetail(driver, "Số tiền phí").replaceAll("\\D+", "");

		log.info("TC_03_Step 23: So tien phi chuyen doi ra EUR");
		double EURTransferFee = convertMoneyToDouble(Fee, "VND") / convertMoneyToDouble(exchangeRate, "VND");

		log.info("TC_03_Step 25: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_03_Step_Nhap ma xac thuc");
		transferInVCB.inputToDynamicSmartOTP(driver, LogIn_Data.Login_Account.Smart_OTP, "com.VCB:id/otp");

		log.info("TC_03_Step_Tiep tuc");
		transferInVCB.clickToDynamicContinue(driver, "com.VCB:id/submit");
		transferInVCB.clickToDynamicContinue(driver, "com.VCB:id/btContinue");
		
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE));

		log.info("TC_03_Step 28: Lay thoi gian tao");
		transferTime = transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE, "4").split(" ")[3];

		log.info("TC_03_Step 29: Kiem tra so tien chuyen hien thi");
		verifyEquals(transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE, "3"), addCommasToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " EUR");

		log.info("TC_03_Step 30: Kiem tra ten nguoi thu huong hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tên người thụ hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME_ACCOUNT_2);

		log.info("TC_03_Step 31: Kiem tra tai khoan dich hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tài khoản thụ hưởng"), Account_Data.Valid_Account.DEFAULT_ACCOUNT3);

		log.info("TC_03_Step 32: Lay ma giao dich");
		transactionNumber = transferInVCB.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC_03_Step 33: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_03_Step 34: Click thuc hien giao dich moi");
		transferInVCB.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_03_Step 35: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_03_Step 36:Click EUR account");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.EUR_ACCOUNT);
		transferInVCB.sleep(driver, 1000);

		log.info("TC_03_Step 37: Lay so du kha dung tai khoan EUR");
		String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		double afterBalanceAmountOfAccount1 = convertMoneyToDouble(afterBalanceOfAccount1, "EUR");

		log.info("TC_03_Step 38: Convert so tien chuyen");
		double transferMoney = convertMoneyToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER, "EUR");

		log.info("TC_03_Step 39: Kiem tra so du tai khoan EUR sau khi chuyen tien");
		verifyEquals(beforeBalanceAmountOfAccount1 - transferMoney - EURTransferFee, afterBalanceAmountOfAccount1);
		
		log.info("TC_02: Click back man hinh home");
		transferInVCB.clickToDynamicImageViewID(driver, "com.VCB:id/ivTitleLeft");
	}

	// Chuyển tiền tương lai --Da chuyen 
 @Test
	 public void TC_04_ChuyenTienTuongLaiCoPhiGiaoDichNguoiChuyenTraEURVaXacThucBangSmartOTP() throws InterruptedException {
	 log.info("TC_04_Step_01: Click Chuyen tien trong VCB");
		transferInVCB.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_04_Step_02: Chon chuyen tien ngay gia tri hien tai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày giá trị hiện tại");

		log.info("TC_04_Step_03: Chon chuyen tien ngay gia tri hien tai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày tương lai");
		
		log.info("TC05_Step 02: Click chon tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_04_Step_08: Chon tai khoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.EUR_ACCOUNT);

		log.info("TC_04_Step_09: Lay so du tai khoan chuyen");
		String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		double beforeBalanceAmountOfAccount1 = convertMoneyToDouble(beforeBalanceOfAccount1, "EUR");

		log.info("TC_04_Step_10: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, Account_Data.Valid_Account.ACCOUNT2, "Nhập/ chọn tài khoản thụ hưởng");

		log.info("TC_04_Step_11: Kiem tra Ngay hieu luc mac dinh");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, tommorrowDate));

		log.info("TC_04_Step_12: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInFutureForOTP.AMOUNT_OF_EUR_OR_USD_TRANSFER, "Số tiền");
		
		log.info("TC_03_Step_:Lay so tien quy doi ra VND");
		String exchange = transferInVCB.getDynamicTextInTransactionDetail(driver, "Tỷ giá quy đổi tham khảo").split(" ~ ", 2)[1];
		exchangeRate = exchange.replace(" VND", "");

		log.info("TC_04_Step_13: Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Thông tin giao dịch", "3");

		log.info("TC_04_Step_14: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_04_Step_15: Kiem tra hinh thuc chuyen tien hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Hình thức chuyển tiền"), TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[2]);

		log.info("TC_04_Step_16: Kiem tra tai khoan nguon hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), Account_Data.Valid_Account.EUR_ACCOUNT);

		log.info("TC_04_Step_17: Kiem tra tai khoan dich hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tài khoản đích/ VND").contains(Account_Data.Valid_Account.ACCOUNT2));

		log.info("TC_04_Step_18: Kiem tra so tien chuyen hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Số tiền"), String.format("%.02f", convertMoneyToDouble(TransferMoneyInVCB_Data.InputDataInFutureForPassword.AMOUNT_OF_EUR_OR_USD_TRANSFER, "EUR")) + " EUR");
		
		log.info("TC_04_Step_19: Kiem tra Ngay hieu luc hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Ngày hiệu lực"), transferInVCB.getDayInWeek(tommorrowDate) + " " + tommorrowDate);

		log.info("TC_04_Step_20: Kiem tra phuong thuc tra phi");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.InputDataInVCB.COST[0]));

		log.info("TC_04_Step_21: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_04_Step_22: Chon Phuong thuc nhap");
		transferInVCB.scrollDownToButton(driver, "Tiếp tục");
		transferInVCB.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");

		log.info("TC_04_Step_23: Chon VCB - Smart OTP");
		String transferFee = transferInVCB.getDynamicTextByLabel(driver, "VCB - Smart OTP");
		fee = convertAvailableBalanceCurrentcyOrFeeToLong(transferFee);
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "VCB - Smart OTP");

		log.info("TC_04_Step_24: Kiem tra so tien phi hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), addCommasToLong(fee + "") + " VND");

		log.info("TC_04_Step_25: Click Tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_04_Step_Nhap ma xac thuc");
		transferInVCB.inputToDynamicSmartOTP(driver, LogIn_Data.Login_Account.Smart_OTP, "com.VCB:id/otp");

		log.info("TC_04_Step_Tiep tuc");
		transferInVCB.clickToDynamicContinue(driver, "com.VCB:id/submit");
		transferInVCB.clickToDynamicContinue(driver, "com.VCB:id/btContinue");

		log.info("TC_04_Step_28: Kiem  tra giao dich thanh cong");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.SUCESSFULL_CREATED_ORDER));

		log.info("TC_04_Step_29: Kiem tra so tien chuyen");
		verifyEquals(transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.SUCESSFULL_CREATED_ORDER, "3"), (String.format("%.02f", convertMoneyToDouble(TransferMoneyInVCB_Data.InputDataInFutureForOTP.AMOUNT_OF_EUR_OR_USD_TRANSFER, "EUR")) + " EUR"));

		log.info("TC_04_Step_30: Lay thoi gian tao giao dich");
		transferTime = transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.SUCESSFULL_CREATED_ORDER, "4");
		verifyEquals(transferTime, TransferMoneyInVCB_Data.Output.ORDER_TIME + transferInVCB.getDayInWeek(tommorrowDate) + " " + tommorrowDate);

		log.info("TC_04_Step_31: Lay ma giao dich");
		transactionNumber = transferInVCB.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");
		log.info("TC_04_Step_32: Lay ma giao dich" + transactionNumber);

		log.info("TC_04_Step_34: Kiem tra tai khoan dich hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tài khoản thụ hưởng"), Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_04_Step_35: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_04_Step_36: Click thuc hien giao dich moi");
		transferInVCB.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_04_Step_37: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_04_Step_38: Chon tai ngoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.EUR_ACCOUNT);

		log.info("TC_04_Step_39: Lay so du tai khoan chuyen");
		String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		double afterBalanceAmountOfAccount1 = convertMoneyToDouble(afterBalanceOfAccount1, "EUR");

		log.info("TC_04_Step_40: Kiem tra so du tai khoan chuyen sau khi thuc hien giao dich");
		verifyEquals(beforeBalanceAmountOfAccount1 , afterBalanceAmountOfAccount1);

		log.info("TC_02: Click back man hinh home");
		transferInVCB.clickToDynamicImageViewID(driver, "com.VCB:id/ivTitleLeft");

		}

	// Chuyển tiền định kỳ
@Test
	 public void TC_05_ChuyenTien_NgoaiTe_DinhKy_2Ngay_CoPhiGiaoDichNguoiNhanTra_XacThucBangSmartOTP() throws InterruptedException {
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

			log.info("TC_01_Step_Nhap ma xac thuc");
			transferRecurrent.inputToDynamicSmartOTP(driver, LogIn_Data.Login_Account.Smart_OTP, "com.VCB:id/otp");

			log.info("TC_01_Step_Tiep tuc");
			transferRecurrent.clickToDynamicContinue(driver, "com.VCB:id/submit");
			transferRecurrent.clickToDynamicContinue(driver, "com.VCB:id/btContinue");

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
			
			log.info("TC_05_01_Chon tai khoan nguon");
			transferRecurrent.clickToDynamicDropDown(driver, "Tài khoản nguồn");
			transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info1.sourceAccount);

			transferRecurrent.scrollUpToText(driver, "Tài khoản nguồn");
			log.info("TC_05_02_Lay so du");
			String actualAvailableBalance = transferRecurrent.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Số dư khả dụng");

			log.info("TC_05_03_Kiem tra so du khong thay doi khi chua den han");
			verifyEquals(actualAvailableBalance, expectAvailableBalance);
			
			log.info("TC_05: Click back man hinh home");
			transferRecurrent.clickToDynamicImageViewID(driver, "com.VCB:id/ivTitleLeft");

		}

	// Chuyển tiền từ thiện
@Test
public void TC_06_ChuyenTienTuThienBangNgoaiTeSmartOTP() throws InterruptedException {
	log.info("TC_06_1_Click Chuyen tien tu thien");
	transferMoneyCharity.scrollDownToText(driver, "Trạng thái lệnh chuyển tiền");
	transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền từ thiện");

	log.info("TC_06_2_Chon tai khoan nguon");
	transferMoneyCharity.clickToDynamicDropDown(driver, "Tài khoản nguồn");
	transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info1.sourceAccount);

	surplusCurrentcy = convertAvailableBalanceCurrentcyToDouble(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));

	log.info("TC_06_3_Chon Quy/ To chuc tu thien");
	transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, "Quỹ/ Tổ chức từ thiện");
	transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info1.organization);

	currentcy = getCurrentcyMoney(transferMoneyCharity.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTiGia"));
	log.info("TC_06_4_Nhap so tien ung ho");
	transferMoneyCharity.inputToDynamicInputBox(driver, info1.money, "Số tiền ủng hộ");

	log.info("TC_06_5_Nhap ten nguoi ung ho");
	transferMoneyCharity.inputToDynamicInputBox(driver, info1.name, "Tên người ủng hộ");

	log.info("TC_06_6_Nhap dia chi ung ho");
	transferMoneyCharity.inputToDynamicInputBox(driver, info1.address, "Địa chỉ người ủng hộ");

	log.info("TC_06_7_Hoan canh nguoi ung ho");
	transferMoneyCharity.inputToDynamicInputBox(driver, info1.status, "Hoàn cảnh ủng hộ");

	log.info("TC_06_8_Click Tiep tuc");
	transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_06_9_Kiem tra man hinh xac nhan thong tin");
	log.info("TC_06_9_1_Kiem tra man tai khoan nguon");
	verifyEquals(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), info1.sourceAccount);

	log.info("TC_06_9_2_Kiem tra to chuc");
	verifyEquals(transferMoneyCharity.getDynamicTextInLine2DestinationAccount(driver, "Tài khoản đích"), info1.organization.toUpperCase());

	log.info("TC_06_9_3_Kiem tra tai khoan dich");
	String destinationAccount = transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Tài khoản đích").split("/")[0].trim();

	log.info("TC_06_9_4_Kiem tra so tien ung ho");
	String actualMoney = transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Số tiền ủng hộ");
	String expectedMoney = String.format("%.2f", new BigDecimal(Double.parseDouble(info1.money))) + " EUR";
	verifyEquals(actualMoney, expectedMoney);

	log.info("TC_06_9_5_Kiem tra ten nguoi ung ho");
	verifyEquals(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Tên người ủng hộ"), info1.name);

	log.info("TC_06_9_6_Kiem tra dia chia nguoi chuyen");
	verifyEquals(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Địa chỉ người ủng hộ"), info1.address);

	log.info("TC_06_9_7_Kiem tra hoan canh");
	verifyEquals(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Hoàn cảnh ủng hộ"), info1.status);

	log.info("TC_06_10_Chon phuong thuc xac thuc");
	transferMoneyCharity.scrollDownToText(driver, "Mật khẩu đăng nhập");
	transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
	fee = convertAvailableBalanceCurrentcyOrFeeToLong(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, info1.authenticationMethod));
	transferFeeCurrentcy = convertVNeseMoneyToEUROOrUSD(String.valueOf(fee), currentcy);
	transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info1.authenticationMethod);

	log.info("TC_06_11_Click Tiep tuc");
	transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_06_Step_Nhap ma xac thuc");
	transferMoneyCharity.inputToDynamicSmartOTP(driver, LogIn_Data.Login_Account.Smart_OTP, "com.VCB:id/otp");

	log.info("TC_06_Step_Tiep tuc");
	transferMoneyCharity.clickToDynamicContinue(driver, "com.VCB:id/submit");
	transferMoneyCharity.clickToDynamicContinue(driver, "com.VCB:id/btContinue");

	log.info("TC_06_12_Kiem tra man hinh Chuyen khoan thanh cong");
	verifyTrue(transferMoneyCharity.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyCharity_Data.SUCCESS_TRANSFER_MONEY));

	log.info("TC_06_12_1_Kiem tra ten nguoi huong");
	verifyEquals(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Tên người thụ hưởng"), info1.organization);

	log.info("TC_06_12_2_Kiem tra tai khoan dich");
	verifyEquals(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Tài khoản thụ hưởng"), destinationAccount);

	log.info("TC_06_12_3_Kiem tra nut thuc hien giao dich moi");
	verifyTrue(transferMoneyCharity.isDynamicButtonDisplayed(driver, "Thực hiện giao dịch mới"));

	log.info("TC_06_12_4_Lay ma giao dich");
	transferTime = transferMoneyCharity.getTransferTimeSuccess(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY);
	transactionNumber = transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

	log.info("TC_06_13_Click Thuc hien giao dich moi");
	transferMoneyCharity.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

	log.info("TC_06_14_Kiem tra so du kha dung luc sau");
	transferMoneyCharity.clickToDynamicDropDown(driver, "Tài khoản nguồn");
	transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info1.sourceAccount);
	actualAvailableBalanceCurrentcy = convertAvailableBalanceCurrentcyToDouble(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));
	availableBalanceCurrentcy = canculateAvailableBalancesCurrentcy(surplusCurrentcy, Double.parseDouble(info1.money), transferFeeCurrentcy);
	verifyEquals(actualAvailableBalanceCurrentcy, availableBalanceCurrentcy);
	
	log.info("TC_02: Click back man hinh home");
	transferMoneyCharity.clickToDynamicImageViewID(driver, "com.VCB:id/ivTitleLeft");
}

	//Chuyển tiền chứng minh thư 
@Parameters({ "pass" })
	//@Test
	//Lỗi app không cập nhật số dưtài khoản sau khi chuyển
	   public void TC_07_ChuyenTienEURChoNguoNhanTaiQuayBangCMTXacThucSmartOTPNguoiNhanTraPhi(String pass) throws InterruptedException {
	log.info("TC_07_STEP_1: chon Chuyển tiền nhận bằng tiền mặt");
	trasferPage.clickToDynamicIcon(driver, "Chuyển tiền nhận bằng tiền mặt");

	log.info("TC_07_STEP_2: chon tài khoản");

	trasferPage.clickToTextID(driver, "com.VCB:id/tvContent");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, Valid_Account.EUR_ACCOUNT);

	log.info("TC_07_Step_3: nhap ten nguoi thu huong");
	trasferPage.inputToDynamicInputBox(TransferIdentity_Data.textDataInputForm.USER_NAME, "Tên người thụ hưởng");

	log.info("TC_07_STEP_4: lấy ra số dư");
	trasferPage.scrollUpToText(driver, "Tài khoản nguồn");
	String getToltalMoney = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvInfoBottomRight");
	String[] toltal_money = getToltalMoney.split(" ");
	toltalMoney = Double.parseDouble(toltal_money[0].replace(",", ""));

	log.info("TC_07_Step_5: chon giay to tuy than");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chứng minh nhân dân");

	log.info("TC_07_Step_6: so CMT");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER, "Số");

	log.info("TC_07_Step_7: ngay cap");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Ngày cấp");
	trasferPage.clickToDynamicButton(driver, "OK");

	log.info("TC_07_Step_8: noi cap");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Nơi cấp");
	trasferPage.clickToDynamicTextIndex(driver, "0", TransferIdentity_Data.textDataInputForm.ISSUED);

	log.info("TC_07_STEP_9: nhap so tien bat dau la khong");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textCheckElement.AMOUNT_USD, "Số tiền");
	trasferPage.clickToTextID(driver, "com.VCB:id/tvTitle");

	log.info("TC_07_STEP_9: chọn người trả phí");
	trasferPage.clickToTextID(driver, "com.VCB:id/tvContent3");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Người nhận trả");

	log.info("TC_07_Step_10: noi dung");
	trasferPage.inputToDynamicInputBoxContent(driver, TransferIdentity_Data.textDataInputForm.CONTENT_TRANSFER, "3");

	log.info("TC_07_STEP_11: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_07_STEP_20: lấy nội dung giao dịch");
	content = trasferPage.getMoneyByAccount(driver, "Nội dung");
	
	log.info("TC_05_Step_06: Chon phuong thuc xac thuc SMS OTP");
	trasferPage.clickToTextID(driver, "com.VCB:id/tvptxt");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "VCB - Smart OTP");

	log.info("TC_04_15: lấy ra phí giao dịch");
	String getFee = transReport.getDynamicTextInTransactionDetail(driver, "Số tiền phí");
	String[] feeSplit = getFee.split(" ");
	fee = Integer.parseInt(feeSplit[0].replace(",", ""));

	log.info("TC_07_STEP_12: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_07_Step_Nhap ma xac thuc");
	trasferPage.inputToDynamicSmartOTP(driver, LogIn_Data.Login_Account.Smart_OTP, "com.VCB:id/otp");

	log.info("TC_07_Step_Tiep tuc");
	trasferPage.clickToDynamicContinue(driver, "com.VCB:id/submit");
	trasferPage.clickToDynamicContinue(driver, "com.VCB:id/btContinue");

	log.info("TC_07_STEP_15: lấy tra số tiền chuyển đi");
	moneyTransfer = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount");
	String[] getMoneyTransfer = moneyTransfer.split(" ");
	money_transferred = Double.parseDouble(getMoneyTransfer[0].replace(",", ""));

	log.info("TC_07_STEP_16: lấy ra time chuyển");
	String getDate = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTime");
	transferDate = getDate.split(" ");

	log.info("TC_07_STEP_17: lấy tên người hưởng");
	user = trasferPage.getMoneyByAccount(driver, "Tên người thụ hưởng");

	log.info("TC_07_STEP_19: lấy mã giao dịch");
	code = trasferPage.getMoneyByAccount(driver, "Mã giao dịch");

	log.info("TC_07_STEP_21: chọn thực hiện giao dịch mới");
	trasferPage.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

	log.info("TC_04_17: kiểm tra số dư");
	trasferPage.scrollUpToText(driver, "Tài khoản nguồn");
	String surplus = transReport.getMoneyByAccount(driver, "Số dư khả dụng");
	String[] surplusSplit = surplus.split(" ");
	double surplusInt = Double.parseDouble(surplusSplit[0].replace(",", ""));
	double canculateAvailable = canculateAvailableBalancesCurrentcy(toltalMoney, fee, money_transferred);
	verifyEquals(surplusInt, canculateAvailable);

	log.info("TC_07_STEP_22: chọn back");
	trasferPage.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
	    }
	
	//Chuyển tiền ngoài VCB
@Test
public void TC_08_ChuyenTienLienNganHangNgoaite_EUR_CoPhiGiaoDichNguoiChuyenTraXacThucBangSmartOTP() throws InterruptedException {
	log.info("TC_08_1_Click Chuyen tien toi ngan hang khac");
	transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền tới ngân hàng khác");

	log.info("TC_08_2_Chon tai khoan nguon");
	transferMoneyOutSide.scrollUpToText(driver, "Tài khoản nguồn");
	transferMoneyOutSide.clickToDynamicDropDown(driver, "Tài khoản nguồn");
	transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info2.sourceAccount);
	transferMoneyOutSide.sleep(driver, 1000);
	transferMoneyOutSide.scrollUpToText(driver, "Tài khoản nguồn");
	surplusCurrentcy = convertAvailableBalanceCurrentcyToDouble(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));

	log.info("TC_08_3_Nhap tai khoan thu huong");
	transferMoneyOutSide.inputToDynamicInputBox(driver, info2.destinationAccount, "Nhập/ chọn tài khoản thụ hưởng");

	log.info("TC_08_4_Nhap ten nguoi huong");
	transferMoneyOutSide.inputToDynamicInputBox(driver, info2.name, "Tên người thụ hưởng");

	log.info("TC_08_5_Chon ngan hang huong");
	transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng thụ hưởng");
	transferMoneyOutSide.inputToDynamicInputBox(driver, info2.destinationBank, "Tìm kiếm");
	transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info2.destinationBank);

	currentcy = getCurrentcyMoney(transferMoneyOutSide.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTiGia"));
	log.info("TC_08_6_Nhap so tien");
	transferMoneyOutSide.inputToDynamicInputBox(driver, info2.money, "Số tiền");

	log.info("TC_08_7_Chọn phí giao dịch");
	transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
	transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Người chuyển trả");

	log.info("TC_08_8_Nhap noi dung chuyen tien");
	transferMoneyOutSide.inputToDynamicInputBoxByHeader(driver, info2.note, "Thông tin giao dịch", "3");

	log.info("TC_08_9_Click Tiep tuc");
	transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_08_10_Kiem tra man hinh xac nhan thong tin");
	log.info("TC_08_10_1_Kiem tra tai khoan nguon");
	verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), info2.sourceAccount);

	log.info("TC_08_10_2_Kiem tra tai khoan dich");
	verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tài khoản đích/ VND"), info2.destinationAccount + "/");

	log.info("TC_08_10_3_Kiem tra ten nguoi huong");
	verifyEquals(transferMoneyOutSide.getDynamicTextInLine2DestinationAccount(driver, "Tài khoản đích/ VND"), info2.name.toUpperCase());

	log.info("TC_08_10_4_Kiem tra ngan hang dich");
	verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Ngân hàng thụ hưởng"), info2.destinationBank);

	log.info("TC_08_10_5_Kiem tra so tien quy doi");
	String actualMoney = transferMoneyOutSide.getDynamicTextInTextViewLine2(driver, "Số tiền");
	String expectMoney = convertEURO_USDToVNeseMoney(info2.money, currentcy);
	verifyEquals(actualMoney, expectMoney);

	log.info("TC_08_10_6_Kiem tra so tien");
	verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số tiền"), addCommasToDouble(info2.money) + " EUR");
	log.info("TC_08_10_7_Kiem tra noi dung chuyen tien");
	verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Nội dung"), info2.note);

	log.info("TC_08_11_Chon phuong thuc xac thuc");
	transferMoneyOutSide.scrollDownToText(driver, "Chọn phương thức xác thực");
	transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
	transferFee = convertAvailableBalanceCurrentcyOrFeeToLong(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, info2.authenticationMethod));
	transferFeeCurrentcy = convertVNeseMoneyToEUROOrUSD(String.valueOf(transferFee), currentcy);
	transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info2.authenticationMethod);

	log.info("TC_08_11_01_Kiem tra so tien phi");
	verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), addCommasToLong(transferFee + "") + " VND");

	log.info("TC_08_12_Click Tiep tuc");
	transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_08_Step_Nhap ma xac thuc");
	transferMoneyOutSide.inputToDynamicSmartOTP(driver, LogIn_Data.Login_Account.Smart_OTP, "com.VCB:id/otp");

	log.info("TC_08_Step_Tiep tuc");
	transferMoneyOutSide.clickToDynamicContinue(driver, "com.VCB:id/submit");
	transferMoneyOutSide.clickToDynamicContinue(driver, "com.VCB:id/btContinue");

	log.info("TC_08_13_Kiem tra man hinh Chuyen khoan thanh cong");
	log.info("TC_08_13_1_Kiem tra Chuyen khoan thanh cong");
	verifyTrue(transferMoneyOutSide.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY));

	log.info("TC_08_13_2_Kiem tra ten nguoi thu huong");
	verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tên người thụ hưởng"), info2.name);

	log.info("TC_08_13_3_Kiem tra tai khoan dich");
	verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Tài khoản thụ hưởng"), info2.destinationAccount);

	log.info("TC_08_13_3_Kiem tra ngan hang huong");
	verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Ngân hàng thụ hưởng"), info2.destinationBank);

	log.info("TC_08_13_5_Kiem tra noi dung");
	verifyEquals(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Nội dung"), info2.note);

	log.info("TC_08_13_5_Kiem tra nut Thuc hien giao dich moi");
	verifyTrue(transferMoneyOutSide.isDynamicButtonDisplayed(driver, "Thực hiện giao dịch mới"));

	log.info("TC_08_13_6_Lay ma giao dich");
	transferTime = transferMoneyOutSide.getTransferTimeSuccess(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY);
	transactionNumber = transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

	log.info("TC_08_14_Click Thuc hien giao dich moi");
	transferMoneyOutSide.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

	log.info("TC_08_14_Kiem tra so du kha dung luc sau");
	transferMoneyOutSide.scrollUpToText(driver, "Tài khoản nguồn");
	transferMoneyOutSide.clickToDynamicDropDown(driver, "Tài khoản nguồn");
	transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info2.sourceAccount);
	transferMoneyOutSide.sleep(driver, 1000);
	transferMoneyOutSide.scrollUpToText(driver, "Tài khoản nguồn");
	actualAvailableBalanceCurrentcy = convertAvailableBalanceCurrentcyToDouble(transferMoneyOutSide.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));
	availableBalanceCurrentcy = canculateAvailableBalancesCurrentcy(surplusCurrentcy, Double.parseDouble(info2.money), transferFeeCurrentcy);
	verifyEquals(actualAvailableBalanceCurrentcy, availableBalanceCurrentcy);
	
	log.info("TC_02: Click back man hinh home");
	transferMoneyOutSide.clickToDynamicImageViewID(driver, "com.VCB:id/ivTitleLeft");

}


@Test
	public void TC_09_HuyKichHoatVCBSmartOPT() {
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

		log.info("TC_02_Step_05: verify Trang thai dã kich hoat Smart OTP");
		smartOTP.waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, "Cài đặt VCB-Smart OTP");
		smartOTP.isDynamicMessageAndLabelTextDisplayed(driver, "Chưa kích hoạt");
	}

}
