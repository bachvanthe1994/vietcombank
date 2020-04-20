package vnpay.vietcombank.confirmation_method;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import model.TransferInVCBRecurrent;
import pageObjects.ConfirmMethodObject;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.TransferMoneyInVcbPageObject;
import pageObjects.TransferMoneyObject;
import vietcombankUI.DynamicPageUIs;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransferMoneyInVCB_Data;
import vietcombank_test_data.TransferMoneyQuick_Data;


public class smartOTP_flow extends Base {
    AppiumDriver<MobileElement> driver;
    private LogInPageObject login;
    private HomePageObject homePage;
    private ConfirmMethodObject smartOTP;
    private TransferMoneyObject transferMoney;
	private TransferMoneyInVcbPageObject transferInVCB;
	private TransferMoneyInVcbPageObject transferRecurrent;
    private String  transferTime, transactionNumber, amountStartString, costTranferString, amountTranferString;
	String tommorrowDate = getForwardDate(1);
	String today = getCurrentDay() + "/" + getCurrenMonth() + "/" + getCurrentYear();
	String expectAvailableBalance;
	long transferFee = 0;
	double transferFeeCurrentcy = 0;
	String password = "";

	TransferInVCBRecurrent info = new TransferInVCBRecurrent(Account_Data.Valid_Account.ACCOUNT2, Account_Data.Valid_Account.DEFAULT_ACCOUNT3, "1", "Ngày", "", "", "500000", "Người chuyển trả", "test", "VCB - Smart OTP");
	private long amountStart, amountTranfer, fee, costTranfer;


    @Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
    @BeforeClass
    public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt)
	    throws IOException, InterruptedException {
	startServer();
	driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
	login = PageFactoryManager.getLoginPageObject(driver);
	login.Global_login(phone, pass, opt);

	smartOTP = PageFactoryManager.getConfirmMethodObject(driver);
	transferMoney = PageFactoryManager.getTransferMoneyObject(driver);
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
		verifyEquals(smartOTP.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/state_vnpay"), "Đã kích hoạt");
		
		log.info("TC_02_Step_click button quay lai cai dat");
		smartOTP.clickToDynamicImageViewID(driver, "com.VCB:id/ivTitleLeft");
		
		log.info("TC_02_Click button home");
		smartOTP.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
    }
	
    //Chuyển tiền nhanh 247
	@Test
	public void TC_02_ChuyenTienNhanh247NguoiChuyenTraPhiVNDSmartOTP() throws InterruptedException {
		log.info("TC_01_Step_Click Chuyen tien nhanh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh 24/7");

		log.info("TC_01_Step_Select Chuyen tien nhanh qua tai khoan");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);

		log.info("TC_01_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_01_Step_Get so du kha dung");
		amountStartString = transferMoney.getDynamicTextByLabel(driver, "Số dư khả dụng").replaceAll("\\D+", "");

		log.info("TC_01_Step_Doi kieu du lieu");
		amountStart = Long.parseLong(amountStartString);

		log.info("TC_01_Step_Nhap so tai khoan chuyen");
		transferMoney.inputIntoEditTextByID(driver, Account_Data.Valid_Account.ACCOUNT_TO, "com.VCB:id/edtContent1");

		log.info("TC_01_Step_Select ngan hang");
		transferMoney.clickToDynamicImageViewID(driver, "com.VCB:id/ivContent3");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.BANK[0]);

		log.info("TC_01_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.MONEY, "Số tiền");

		log.info("TC_01_Step_Chon phi giao dich la nguoi chuyen tra");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[0]);

		log.info("TC_01_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE, "Thông tin giao dịch", "3");

		log.info("TC_01_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_Lay gia tri so tien chuyen");
		amountTranferString = transferMoney.getDynamicTextByLabel(driver, "Số tiền").replaceAll("\\D+", "");

		log.info("TC_01_Step_Verify so tien chuyen");
		verifyEquals(amountTranferString, TransferMoneyQuick_Data.TransferQuick.MONEY);

		log.info("TC_01_Step_doi kieu du lieu string -> long");
		amountTranfer = Long.parseLong(amountTranferString);

		log.info("TC_01_Step_Chon phuong thuc xac thuc");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[0]);
		fee = convertAvailableBalanceCurrentcyOrFeeToLong(transferMoney.getDynamicTextInTransactionDetail(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[2]));
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[2]);

		log.info("TC_01_Step_Lay gia tri so tien phí chuyen");
		costTranferString = transferMoney.getDynamicTextByLabel(driver, "Số tiền phí").replaceAll("\\D+", "");

		log.info("TC_01_Step_Verify so tien phi");
		verifyEquals(costTranferString, fee + "");

		log.info("TC_01_Step_doi kieu du lieu string -> long");
		costTranfer = Long.parseLong(costTranferString);

		log.info("TC_01_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_Nhap ma xac thuc");
		transferMoney.inputToDynamicSmartOTP(driver, LogIn_Data.Login_Account.Smart_OTP, "com.VCB:id/otp");

		log.info("TC_01_Step_Tiep tuc");
		transferMoney.clickToDynamicContinue(driver, "com.VCB:id/submit");
		transferMoney.clickToDynamicContinue(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Verify message thanh cong");
		verifyEquals(transferMoney.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY);

		log.info("TC_01_get thoi gian giao dich thanh cong");
		transferTime = transferMoney.getDynamicTransferTimeAndMoney(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY, "4").split(" ")[3];

		log.info("TC_01_Step_: Get ma giao dich");
		transactionNumber = transferMoney.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC_01_Step_:Ten nguoi thu huong");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, "Tên người thụ hưởng"), TransferMoneyQuick_Data.TransferQuick.RECEIVER_NAME);

		log.info("TC_01_Step_: Tai khoan dich");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, "Tài khoản thụ hưởng"), Account_Data.Valid_Account.ACCOUNT_TO);

		log.info("TC_01_Step_: ngan hang thu huong");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, "Ngân hàng thụ hưởng"), Account_Data.Valid_Account.BANK[0]);

		log.info("TC_01_Step_: Noi dung");
		verifyEquals(transferMoney.getDynamicTextInTransactionDetail(driver, "Nội dung"), TransferMoneyQuick_Data.TransferQuick.NOTE);

		log.info("TC_01_Step_: Thuc hien giao dich moi");
		transferMoney.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_01_Step_:Tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_01_Step_: Tai khoan chuyen");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_01_Step_:Check so du kha dung sau khi chuyen tien");
		String amountAfterString = transferMoney.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng").replaceAll("\\D+", "");

		long amountAfter = Long.parseLong(amountAfterString);

		log.info("TC_01_Step_:Check so du kha dung sau khi chuyen tien");
		verifyEquals(amountStart - amountTranfer - costTranfer, amountAfter);
		
		log.info("TC_02: Click back man hinh home");
		transferMoney.clickToDynamicImageViewID(driver, "com.VCB:id/ivTitleLeft");
	}
	
	//Chuyển tiền ngay trong VCB
	@Test
	public void TC_03_ChuyenTienNgayCoPhiGiaoDichNguoiChuyenTraVNDVaXacThucBangSmartOTP() throws InterruptedException {
		log.info("TC_01_Step_01: Click Chuyen tien trong VCB");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền trong VCB");

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
		transferMoney.inputIntoEditTextByID(driver, Account_Data.Valid_Account.ACCOUNT2, "com.VCB:id/edtContent1");

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
		String transferFee = transferInVCB.getDynamicTextByLabel(driver, "SMS OTP");
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
	
	//Chuyển tiền tương lai
	@Test
	public void TC_04_ChuyenTienTuongLaiCoPhiGiaoDichNguoiChuyenTraVNDVaXacThucBangSmartOTP() throws InterruptedException {
		log.info("TC_01_Step_01: Click Chuyen tien trong VCB");
		transferInVCB.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_01_Step_02: Chon chuyen tien ngay gia tri hien tai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày giá trị hiện tại");

		log.info("TC_01_Step_03: Chon chuyen tien ngay gia tri hien tai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày tương lai");

		log.info("TC_01_Step_04:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_01_Step_05: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);
		
		transferInVCB.sleep(driver, 1000);

		log.info("TC_01_Step_06: Lay so du tai khoan dich");
		String beforeBalanceOfAccount2 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		long beforeBalanceAmountOfAccount2 = convertMoneyToLong(beforeBalanceOfAccount2, "VND");

		log.info("TC_01_Step_07:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_01_Step_08: Chon tai khoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.DEFAULT_ACCOUNT3);
		transferInVCB.sleep(driver, 1000);

		log.info("TC_01_Step_09: Lay so du tai khoan chuyen");
		String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		long beforeBalanceAmountOfAccount1 = convertMoneyToLong(beforeBalanceOfAccount1, "VND");

		log.info("TC_01_Step_10: Nhap tai khoan nhan");
		transferInVCB.inputIntoEditTextByID(driver, Account_Data.Valid_Account.ACCOUNT2, "com.VCB:id/edtContent1");

		log.info("TC_01_Step_11: Kiem tra Ngay hieu luc mac dinh");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, tommorrowDate));

		log.info("TC_01_Step_12: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInFutureForOTP.TRANSFER_AMOUNT, "Số tiền");

		log.info("TC_01_Step_13: Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Thông tin giao dịch", "3");

		log.info("TC_01_Step_14: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_15: Kiem tra hinh thuc chuyen tien hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Hình thức chuyển tiền"), TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[2]);

		log.info("TC_01_Step_16: Kiem tra tai khoan nguon hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), Account_Data.Valid_Account.DEFAULT_ACCOUNT3);

		log.info("TC_01_Step_17: Kiem tra tai khoan dich hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tài khoản đích/ VND").contains(Account_Data.Valid_Account.ACCOUNT2));

		log.info("TC_01_Step_18: Kiem tra so tien chuyen hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Số tiền"), addCommasToLong(TransferMoneyInVCB_Data.InputDataInFutureForOTP.TRANSFER_AMOUNT) + " VND");

		log.info("TC_01_Step_19: Kiem tra Ngay hieu luc hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Ngày hiệu lực"), transferInVCB.getDayInWeek(tommorrowDate) + " " + tommorrowDate);

		log.info("TC_01_Step_20: Kiem tra phuong thuc tra phi");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.InputDataInVCB.COST[0]));

		log.info("TC_01_Step_21: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_01_Step_22: Chon Phuong thuc nhap");
		transferInVCB.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");

		log.info("TC_01_Step_23: Chon SMS OTP");
		String transferFee = transferInVCB.getDynamicTextByLabel(driver, "SMS OTP");
		fee = convertAvailableBalanceCurrentcyOrFeeToLong(transferFee);
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "VCB - Smart OTP");

		log.info("TC_01_Step_24: Kiem tra so tien phi hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), (addCommasToLong(fee + "") + " VND"));

		log.info("TC_01_Step_25: Click Tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_Nhap ma xac thuc");
		transferRecurrent.inputToDynamicSmartOTP(driver, LogIn_Data.Login_Account.Smart_OTP, "com.VCB:id/otp");

		log.info("TC_01_Step_Tiep tuc");
		transferRecurrent.clickToDynamicContinue(driver, "com.VCB:id/submit");
		transferRecurrent.clickToDynamicContinue(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_28: Kiem  tra giao dich thanh cong");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.SUCESSFULL_CREATED_ORDER));

		log.info("TC_01_Step_29: Kiem tra so tien chuyen");
		verifyEquals(transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.SUCESSFULL_CREATED_ORDER, "3"), addCommasToLong(TransferMoneyInVCB_Data.InputDataInFutureForOTP.TRANSFER_AMOUNT) + " VND");

		log.info("TC_01_Step_30: Lay thoi gian tao giao dich");
		transferTime = transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.SUCESSFULL_CREATED_ORDER, "4");
		verifyEquals(transferTime, TransferMoneyInVCB_Data.Output.ORDER_TIME + transferInVCB.getDayInWeek(tommorrowDate) + " " + tommorrowDate);

		log.info("TC_01_Step_31: Lay ma giao dich");
		transactionNumber = transferInVCB.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");
		log.info("TC_01_Step_32: Lay ma giao dich" + transactionNumber);

		log.info("TC_01_Step_33: Kiem tra ten nguoi thu huong hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tên người thụ hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME_ACCOUNT_2);

		log.info("TC_01_Step_34: Kiem tra tai khoan dich hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tài khoản thụ hưởng"), Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_01_Step_35: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_01_Step_36: Click thuc hien giao dich moi");
		transferInVCB.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_01_Step_37: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_01_Step_38: Chon tai ngoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.DEFAULT_ACCOUNT3);
		transferInVCB.sleep(driver, 1000);

		log.info("TC_01_Step_39: Lay so du tai khoan chuyen");
		String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		long afterBalanceAmountOfAccount1 = convertMoneyToLong(afterBalanceOfAccount1, "VND");

		log.info("TC_01_Step_40: Kiem tra so du tai khoan chuyen sau khi thuc hien giao dich");
		verifyEquals(beforeBalanceAmountOfAccount1, afterBalanceAmountOfAccount1);

		log.info("TC_01_Step_41: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_01_Step_42: Chon tai khoan nguoi nhan");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);
		transferInVCB.sleep(driver, 1000);

		log.info("TC_01_Step_43: Lay so du kha dung tai khoan nhan");
		String afterBalanceOfAccount2 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");

		log.info("TC_01_Step_44: Kiem tra so du tai khoan nhan sau khi nhan tien");
		long afterBalanceAmountOfAccount2 = convertMoneyToLong(afterBalanceOfAccount2, "VND");
		verifyEquals(beforeBalanceAmountOfAccount2, afterBalanceAmountOfAccount2);
		
		log.info("TC_02: Click back man hinh home");
		transferInVCB.clickToDynamicImageViewID(driver, "com.VCB:id/ivTitleLeft");
	}

	//Chuyển tiền định kỳ
	@Test
	public void TC_05_ChuyenTien_VND_DinhKy_1Ngay_CoPhiGiaoDichNguoiChuyenTra_XacThucBangOTP() throws InterruptedException {
		log.info("TC_01_1_Click Chuyen tien trong ngan hang");
		transferRecurrent.scrollDownToText(driver, "Chuyển tiền tới ngân hàng khác");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền trong VCB");
		
		log.info("TC_01_2_Chon phuong thuc chuyen tien");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày giá trị hiện tại");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");

		log.info("TC_01_3_Chon tai khoan nguon");
		transferRecurrent.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);

		transferRecurrent.scrollUpToText(driver, "Tài khoản nguồn");
		expectAvailableBalance = transferRecurrent.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Số dư khả dụng");

		transferRecurrent.inputToDynamicInputBox(driver, info.destinationAccount, "Nhập/ chọn tài khoản thụ hưởng");

		log.info("TC_01_4_Chon tan suat");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Ngày");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info.frequencyCategory);
		transferRecurrent.inputFrequencyNumber(info.frequencyNumber);

		log.info("TC_01_5_Nhap so tien");
		transferRecurrent.inputToDynamicInputBox(driver, info.money, "Số tiền");

		log.info("TC_01_6_Chon nguoi tra phi giao dich");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info.fee);

		log.info("TC_01_7_Nhap noi dung");
		transferRecurrent.inputToDynamicInputBoxByHeader(driver, info.note, "Thông tin giao dịch", "3");

		log.info("TC_01_8_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_9_Kiem tra man hinh xac nhan thong tin");
		log.info("TC_01_9_1_Kiem tra hinh thuc chuyen tien");
		transferRecurrent.scrollUpToText(driver, "Hình thức chuyển tiền");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Hình thức chuyển tiền"), "Chuyển tiền định kỳ");

		log.info("TC_01_9_2_Kiem tra tai khoan nguon");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), info.sourceAccount);

		log.info("TC_01_9_3_Kiem tra tai khoan dich");
		verifyTrue(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tài khoản đích/ VND").contains(info.destinationAccount));

		log.info("TC_01_9_4_Kiem tra so tien");
		verifyTrue(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Số tiền").contains(addCommasToLong(info.money)));

		log.info("TC_01_9_5_Kiem tra tan suat");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tần suất chuyển"), info.frequencyNumber + " " + info.frequencyCategory + "/ lần");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Số lần giao dịch"), "2");

		log.info("TC_01_9_7_Kiem tra noi dung");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Nội dung"), info.note);

		log.info("TC_01_10_Chon phuong thuc xac thuc");
		transferRecurrent.scrollDownToText(driver, "Chọn phương thức xác thực");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		transferFee = convertAvailableBalanceCurrentcyOrFeeToLong(transferRecurrent.getDynamicTextInTransactionDetail(driver, info.authenticationMethod));
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info.authenticationMethod);

		log.info("TC_01_11_Kiem tra so tien phi");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), addCommasToLong(transferFee + "") + " VND");

		log.info("TC_01_12_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_Nhap ma xac thuc");
		transferRecurrent.inputToDynamicSmartOTP(driver, LogIn_Data.Login_Account.Smart_OTP, "com.VCB:id/otp");

		log.info("TC_01_Step_Tiep tuc");
		transferRecurrent.clickToDynamicContinue(driver, "com.VCB:id/submit");
		transferRecurrent.clickToDynamicContinue(driver, "com.VCB:id/btContinue");

		log.info("TC_01_13_Kiem tra man hinh Lap lenh thanh cong");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY_IN_VCB_RECURRENT));

		transferTime = transferRecurrent.getTransferMoneyRecurrentTimeSuccess(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY_IN_VCB_RECURRENT);

		log.info("TC_01_13_1_Kiem tra ten nguoi huong thu");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tên người thụ hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME_ACCOUNT_2);

		log.info("TC_01_13_2_Kiem tra tai khoan dich");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Tài khoản thụ hưởng"), info.destinationAccount);

		log.info("TC_01_13_3_Kiem tra noi dung");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Nội dung"), info.note);
		verifyTrue(transferRecurrent.isDynamicButtonDisplayed(driver, "Thực hiện giao dịch mới"));

		log.info("TC_01_14_Click Thuc hien giao dich moi");
		login.clickToDynamicButton(driver, "Thực hiện giao dịch mới");
		
		log.info("TC_02: Click back man hinh home");
		transferRecurrent.clickToDynamicImageViewID(driver, "com.VCB:id/ivTitleLeft");
	}

    @AfterClass(alwaysRun = true)
    public void afterClass() {
//	closeApp();
//	service.stop();
    }

}
