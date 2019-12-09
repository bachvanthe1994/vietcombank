package vnpay.vietcombank.transfer_money_in_vcb;

import java.io.IOException;

import org.testng.annotations.AfterClass;
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
import pageObjects.TransferMoneyInVcbPageObject;
import vietcombank_test_data.HomePage_Data;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransferMoneyInVCB_Data;

public class Transfer_Money_Immedidately extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferMoneyInVcbPageObject transferInVCB;
	private TransactionReportPageObject transReport;
	private String transferFee;
	private String transferTime;
	private String transactionNumber;
	double rate;
	String[]exchangeRateUSD;

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName) throws IOException, InterruptedException {
		startServer();
		log.info("Before class_Step_00: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);

		login = PageFactoryManager.getLoginPageObject(driver);

		log.info("Before class_Step_01: Click Allow Button");
		login.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");

		log.info("Before class_Step_02: Dien so dien thoai ");
		login.inputToDynamicLogInTextBox(driver, LogIn_Data.Login_Account.PHONE, "Tiếp tục");

		log.info("Before class_Step_03: Click Tiep tuc");
		login.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_05_Step_04:  Dien mat khau");
		login.inputToDynamicInputBox(driver, LogIn_Data.Login_Account.NEW_PASSWORD, LogIn_Data.UI.PASSWORD_LABEL);

		log.info("Before class_Step_05: Click tiep tuc");
		login.clickToDynamicButton(driver, "Tiếp tục");

		log.info("Before class_Step_06: Nhap OTP");
		login.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		log.info("Before class_Step_07: Click tiep tuc");
		login.clickToDynamicButton(driver, "Tiếp tục");

		log.info("Before class_Step_08: Click Huy");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicButton(driver, "Hủy");
		
		log.info("Before class_Step_09: Click icon Dong");
		homePage.clickToDynamicCloseIcon(driver, "Kích hoạt tính năng mới");
		
		log.info("Before class_Step_10: Scroll den trang thai lenh chuyen tien");
		homePage.scrollToText(driver, "Trạng thái lệnh chuyển tiền");

	}

//	@Test
	public void TC_01_ChuyenTienNgayCoPhiGiaoDichNguoiChuyenTraVNDVaXacThucBangOTP() {
		
		log.info("TC_01_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");
		
		log.info("TC_01_Step_02:Click tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicDropDown(driver,"Tài khoản nguồn");

		log.info("TC_01_Step_03: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT2);
		
		log.info("TC_01_Step_04: Lay so du tai khoan dich");
		String beforeBalanceOfAccount2 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
		long beforeBalanceAmountOfAccount2 = convertMoneyToLong(beforeBalanceOfAccount2,"VND");
		
		log.info("TC_01_Step_05:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver,"Tài khoản nguồn");
		
		log.info("TC_01_Step_06: Chon tai khoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT1);
		
		log.info("TC_01_Step_07: Lay so du tai khoan chuyen");
		String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
		long beforeBalanceAmountOfAccount1 = convertMoneyToLong(beforeBalanceOfAccount1,"VND");
		
		log.info("TC_01_Step_08: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT2, "Nhập/chọn tài khoản nhận VND");
		
		log.info("TC_01_Step_09: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputData.MONEY, "Số tiền");
		
		log.info("TC_01_Step_10: Nhap noi dung");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputData.NOTE, "Nội dung");
		
		log.info("TC_01_Step_11: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_01_Step_12: Kiem tra hinh thuc chuyen tien hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Hình thức chuyển tiền"), TransferMoneyInVCB_Data.InputData.OPTION_TRANSFER[0]);
		
		log.info("TC_01_Step_13: Kiem tra tai khoan nguon hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Tài khoản nguồn"), TransferMoneyInVCB_Data.InputData.ACCOUNT1);
		
		log.info("TC_01_Step_14: Kiem tra tai khoan dich hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTextView(driver, "Tài khoản đích / VND").contains(TransferMoneyInVCB_Data.InputData.ACCOUNT2));
		
		log.info("TC_01_Step_15: Kiem tra so tien chuyen hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTextView(driver, "Số tiền (VND)").contains(addCommasToLong(TransferMoneyInVCB_Data.InputData.MONEY)));

		log.info("TC_01_Step_17: Kiem tra phuong thuc tra phi");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.InputData.COST[0]));

		log.info("TC_01_Step_19: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Nội dung"), TransferMoneyInVCB_Data.InputData.NOTE);

		log.info("TC_06_Step_20: Chon Phuong thuc nhap");
		transferInVCB.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");
		
		log.info("TC_01_Step_21: Chon SMS OTP");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver,"SMS OTP");
		
		log.info("TC_01_Step_16: Kiem tra so tien phi hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTextView(driver, "Số tiền phí").contains(TransferMoneyInVCB_Data.InputData.PAYMENT_BY_OTP_FEE+" VND"));
		
		log.info("TC_01_Step_18: Lay so tien phi");
		transferFee = transferInVCB.getDynamicTextInTextView(driver, "Số tiền phí");
		long newTransferFee = convertMoneyToLong(transferFee,"VND");

		log.info("TC_01_Step_22: Click Tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_01_Step_23: Nhap OTP");
		transferInVCB.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");
		
		log.info("TC_01_Step_24: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_01_Step_25: Kiem  tra giao dich thanh cong");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE));
		
		log.info("TC_01_Step_26: Lay thoi gian tao giao dich");
		transferTime = transferInVCB.getDynamicTransferTime(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE, "4");
		
		log.info("TC_01_Step_27: Lay ma giao dich");
		transactionNumber = transferInVCB.getDynamicTextInTextView(driver, "Mã giao dịch");
		
		log.info("TC_01_Step_28: Kiem tra ten nguoi thu huong hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Tên người thụ hưởng"), TransferMoneyInVCB_Data.InputData.RECEIVER_NAME);
		
		log.info("TC_01_Step_29: Kiem tra tai khoan dich hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Tài khoản đích"), TransferMoneyInVCB_Data.InputData.ACCOUNT2);
		
		log.info("TC_01_Step_30: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Nội dung"), TransferMoneyInVCB_Data.InputData.NOTE);
		
		log.info("TC_01_Step_31: Click thuc hien giao dich moi");
		transferInVCB.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_01_Step_32: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver,"Tài khoản nguồn");
		
		log.info("TC_01_Step_33: Chon tai ngoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT1);
		
		log.info("TC_01_Step_34: Lay so du tai khoan chuyen");
		String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
		long afterBalanceAmountOfAccount1 = convertMoneyToLong(afterBalanceOfAccount1,"VND");
		
		log.info("TC_01_Step_35: Kiem tra so du tai khoan chuyen sau khi thuc hien giao dich");
		long transferMoney = convertMoneyToLong(TransferMoneyInVCB_Data.InputData.MONEY,"VND");
		verifyEquals(beforeBalanceAmountOfAccount1 - transferMoney - newTransferFee, afterBalanceAmountOfAccount1);

		log.info("TC_01_Step_36: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver,"Tài khoản nguồn");
		
		log.info("TC_01_Step_37: Chon tai khoan nguoi nhan");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT2);

		
		log.info("TC_01_Step_38: Lay so du kha dung tai khoan nhan");
		String afterBalanceOfAccount2 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
		
		log.info("TC_01_Step_39: Kiem tra so du tai khoan nhan sau khi nhan tien");
		long afterBalanceAmountOfAccount2 = convertMoneyToLong(afterBalanceOfAccount2,"VND");
		verifyEquals(beforeBalanceAmountOfAccount2 + transferMoney, afterBalanceAmountOfAccount2);

	}

//		@Test
	public void TC_02_KiemTraChiTietGiaoDichChuyenTienNgayCoPhiGiaoDichNguoiChuyenTraVNDVaXacThucBangOTP() {
		log.info("TC_02_Step_01 : Click  nut Back");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");
		
		log.info("TC_02_Step_02: Click vao More Icon");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicBottomMenu(driver, "com.VCB:id/menu_5");
		
		log.info("TC_02_Step_03: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");
		
		log.info("TC_02_Step_04: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");
		
		log.info("TC_02_Step_05: Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền trong Vietcombank");
		
		log.info("TC_02_Step_06: Click Chon Tai Khoan");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");
		
		log.info("TC_02_Step_07: Chon tai Khoan chuyen");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT1);
		
		log.info("TC_02_Step_08: CLick Tim Kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");
	
		log.info("TC_02_Step_09: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));
		
		log.info("TC_02_Step_10: Kiem tra thoi gian tao dao dich");
		verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));
		
		log.info("TC_02_Step_11: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputData.NOTE));
		
		log.info("TC_02_Step_12: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransaction(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(TransferMoneyInVCB_Data.InputData.MONEY) + " VND"));
		
		log.info("TC_02_Step_13: Click vao giao dich");
		transReport.clickToDynamicTransaction(driver, "0", "com.VCB:id/tvDate");
		
		log.info("TC_02_Step_14: Kiem tra ngay giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").contains(transferTime.split(" ")[0]));
		
		log.info("TC_02_Step_15: Kiem tra thoi gian tao giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));
		
		log.info("TC_02_Step_16: Kiem tra thoi gian tao giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Số lệnh giao dịch"), transactionNumber);
		
		log.info("TC_02_Step_17: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản/thẻ trích nợ"), TransferMoneyInVCB_Data.InputData.ACCOUNT1);
		
		log.info("TC_02_Step_18: Kiem tra so tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản ghi có"), TransferMoneyInVCB_Data.InputData.ACCOUNT2);
		
		log.info("TC_02_Step_19: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTextView(driver, "Số tiền giao dịch").contains( addCommasToLong(TransferMoneyInVCB_Data.InputData.MONEY) + " VND"));
		
		log.info("TC_02_Step_20: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Tên người hưởng"), TransferMoneyInVCB_Data.InputData.RECEIVER_NAME);
		
		log.info("TC_02_Step_21: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Phí giao dịch"), TransferMoneyInVCB_Data.InputData.COST[0]);
		
		log.info("TC_02_Step_22: Kiem tra so tien phi hien thi");
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Số tiền phí"), TransferMoneyInVCB_Data.InputData.PAYMENT_BY_OTP_FEE + " VND");
		
		log.info("TC_02_Step_23: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Loại giao dịch"), TransferMoneyInVCB_Data.InputData.TRANSFER_TYPE);
		
		log.info("TC_02_Step_24: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTextView(driver, "Nội dung giao dịch").contains(TransferMoneyInVCB_Data.InputData.NOTE));

		log.info("TC_02_Step_25: Click Back icon");
		transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");
		
		log.info("TC_02_Step_26: Chon Tai Khoan");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");
		
		log.info("TC_02_Step_27: Click chon tai khoan nhan");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT2);

		log.info("TC_02_Step_28: Click Tim Kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_02_Step_29: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));
		
		log.info("TC_02_Step_20: Kiem tra thoi gian tao giao dich hiển thị");
		verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));
		
		log.info("TC_02_Step_21: Kiem tra noi dung giao dich hien thi");
		verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputData.NOTE));
		
		log.info("TC_02_Step_22: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransaction(driver, "1", "com.VCB:id/tvMoney"), ("+ " + addCommasToLong(TransferMoneyInVCB_Data.InputData.MONEY) + " VND"));
		
		log.info("TC_02_Step_23: Click vao bao bao giao dich chi tiet");
		transReport.clickToDynamicTransaction(driver, "0", "com.VCB:id/tvDate");
		
		log.info("TC_02_Step_24: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").contains(transferTime.split(" ")[0]));
		
		log.info("TC_02_Step_25: Kiem tra thoi gian tao giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));
		
		log.info("TC_02_Step_26: Kiem tra so giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Số lệnh giao dịch"), transactionNumber);
		
		log.info("TC_02_Step_27: Kiem tra tai khoan trich no hien thi");
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản/thẻ trích nợ"), TransferMoneyInVCB_Data.InputData.ACCOUNT1);
		
		log.info("TC_02_Step_28: Kiem tra tai khoan ghi co hien thi");
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản ghi có"), TransferMoneyInVCB_Data.InputData.ACCOUNT2);
		
		log.info("TC_02_Step_29: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTextView(driver, "Số tiền giao dịch").contains(addCommasToLong(TransferMoneyInVCB_Data.InputData.MONEY) + " VND"));
		
		log.info("TC_02_Step_30: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Tên người hưởng"), TransferMoneyInVCB_Data.InputData.RECEIVER_NAME);
		
		log.info("TC_02_Step_31: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Phí giao dịch"), TransferMoneyInVCB_Data.InputData.COST[0]);
		
		log.info("TC_02_Step_32: Kiem tra so tien phi hien thi");
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Số tiền phí"), TransferMoneyInVCB_Data.InputData.PAYMENT_BY_OTP_FEE + " VND");
		
		log.info("TC_02_Step_33: Kiem tra noi dung giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTextView(driver, "Loại giao dịch"), TransferMoneyInVCB_Data.InputData.TRANSFER_TYPE);
		
		log.info("TC_02_Step_34: Kiem tra loaigiao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTextView(driver, "Nội dung giao dịch").contains(TransferMoneyInVCB_Data.InputData.NOTE));

		log.info("TC_02_Step_35: Click quay lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");
		
		log.info("TC_02_Step_36: Click quay lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");
		
		log.info("TC_02_Step_37: Click quay lai");
		transferInVCB.navigateBack(driver);
	}

//	@Test
	public void TC_03_ChuyenTienNgayCoPhiGiaoDichNguoiNhanTraVNDVaXacThucBangOTP() {
		
		
		log.info("TC_03_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");
		
		log.info("TC_03_Step_02:Click tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicDropDown(driver,"Tài khoản nguồn");

		log.info("TC_03_Step_03: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT1);
		
		log.info("TC_03_Step_04: Lay so du tai khoan dich");
		String beforeBalanceOfAccount2 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
		long beforeBalanceAmountOfAccount2 = convertMoneyToLong(beforeBalanceOfAccount2,"VND");
		
		log.info("TC_03_Step_05:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver,"Tài khoản nguồn");
		
		log.info("TC_03_Step_06: Chon tai khoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT2);
		
		log.info("TC_03_Step_07: Lay so du tai khoan chuyen");
		String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
		long beforeBalanceAmountOfAccount1 = convertMoneyToLong(beforeBalanceOfAccount1,"VND");
		
		log.info("TC_03_Step_08: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT1, "Nhập/chọn tài khoản nhận VND");
		
		log.info("TC_03_Step_09: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputData.MONEY, "Số tiền");
		
		log.info("TC_03_Step_10: Click phi giao dich nguoi chuyen tra");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
		
		log.info("TC_03_Step_11: Chon nguoi nhan tra");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Người nhận trả");
		
		log.info("TC_03_Step_12: Nhap noi dung");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputData.NOTE, "Nội dung");
		
		log.info("TC_03_Step_13: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_03_Step_14:  Kiem tra hinh thuc chuyen tien hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Hình thức chuyển tiền"), TransferMoneyInVCB_Data.InputData.OPTION_TRANSFER[0]);
		
		log.info("TC_03_Step_15: Kiem tra tai khoan nguon hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Tài khoản nguồn"), TransferMoneyInVCB_Data.InputData.ACCOUNT2);
		
		log.info("TC_03_Step_16: Kiem tra tai khoan dich hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTextView(driver, "Tài khoản đích / VND").contains(TransferMoneyInVCB_Data.InputData.ACCOUNT1));
		
		log.info("TC_03_Step_17: Kiem tra so tien chuyen hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTextView(driver, "Số tiền (VND)").contains(addCommasToLong(TransferMoneyInVCB_Data.InputData.MONEY)));
		
		log.info("TC_03_Step_19: Kiem tra nguoi nhan tra");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.InputData.COST[1]));
		

		log.info("TC_03_Step_21:Kiem tra noi dung hien thi");		
		verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Nội dung"), TransferMoneyInVCB_Data.InputData.NOTE);
		
		log.info("TC_03_Step_22: Chon Phuong thuc xac thuc ");
		transferInVCB.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");
		
		log.info("TC_03_Step_23: Chon SMS OTP");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver,"SMS OTP");
		
		log.info("TC_03_Step_18: Kiem tra so tien phi hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTextView(driver, "Số tiền phí").contains(TransferMoneyInVCB_Data.InputData.PAYMENT_BY_OTP_FEE+" VND"));
		
		log.info("TC_03_Step_20:  Lay so tien phi giao dich");
		transferFee = transferInVCB.getDynamicTextInTextView(driver, "Số tiền phí");
		long newTransferFee = convertMoneyToLong(transferFee,"VND");
		
		log.info("TC_03_Step_24: Click Tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_03_Step_25: Nhap OTP");
		transferInVCB.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");
		
		log.info("TC_03_Step_26: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_03_Step_27: Kiem  tra giao dich thanh cong");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE));
		
		log.info("TC_03_Step_28: Lay thoi gian tao giao dich");
		transferTime = transferInVCB.getDynamicTransferTime(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE, "4");
		
		log.info("TC_03_Step_29: Lay ma giao dich");
		transactionNumber = transferInVCB.getDynamicTextInTextView(driver, "Mã giao dịch");
		
		log.info("TC_03_Step_30:  Kiem tra ten nguoi thu huong hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Tên người thụ hưởng"), TransferMoneyInVCB_Data.InputData.RECEIVER_NAME);
		
		log.info("TC_03_Step_31: Kiem tra tai khoan dich hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Tài khoản đích"), TransferMoneyInVCB_Data.InputData.ACCOUNT1);

		log.info("TC_03_Step_23: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Nội dung"), TransferMoneyInVCB_Data.InputData.NOTE);
		
		log.info("TC_03_Step_24: Click thuc hien giao dich moi");
		transferInVCB.clickToDynamicButton(driver, "Thực hiện giao dịch mới");
		
		log.info("TC_03_Step_25: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver,"Tài khoản nguồn");
		
		log.info("TC_03_Step_26: Chon tai ngoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT2);
		
		log.info("TC_03_Step_27: Lay so du tai khoan chuyen");
		String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
		long afterBalanceAmountOfAccount1 = convertMoneyToLong(afterBalanceOfAccount1,"VND");
		
		log.info("TC_03_Step_28: Kiem tra so du tai khoan chuyen sau khi thuc hien giao dich");
		long transferMoney = convertMoneyToLong(TransferMoneyInVCB_Data.InputData.MONEY,"VND");
		verifyEquals(beforeBalanceAmountOfAccount1 - transferMoney, afterBalanceAmountOfAccount1);
		
		log.info("TC_03_Step_29:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver,"Tài khoản nguồn");
		
		log.info("TC_03_Step_30: Chon tai khoan nguoi nhan");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT1);
		
		log.info("TC_03_Step_31:Lay so du kha dung tai khoan nhan");
		String afterBalanceOfAccount2 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
		long afterBalanceAmountOfAccount2 = convertMoneyToLong(afterBalanceOfAccount2,"VND");
		
		log.info("TC_03_Step_32: Kiem tra so du tai khoan nhan sau khi nhan tien");
		verifyEquals(beforeBalanceAmountOfAccount2 + transferMoney-newTransferFee , afterBalanceAmountOfAccount2);

	}
//	@Test
public void TC_04_KiemTraChiTietGiaoDichChuyenTienNgayCoPhiGiaoDichNguoiNhanTraVNDVaXacThucBangOTP() {
	
	log.info("TC_04_Step_01:Click  nut Back");
	transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");
	
	log.info("TC_04_Step_02:Click vao More Icon");
	homePage = PageFactoryManager.getHomePageObject(driver);
	homePage.clickToDynamicBottomMenu(driver, "com.VCB:id/menu_5");
	
	log.info("TC_04_Step_03:Click Bao Cao Dao Dich");
	transReport = PageFactoryManager.getTransactionReportPageObject(driver);
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");
	
	log.info("TC_04_Step_04: Click Tat Ca Cac Loai Giao Dich");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");
	
	log.info("TC_04_Step_05: Chon Chuyen Tien Trong VCB");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền trong Vietcombank");
	
	log.info("TC_04_Step_06: Click Chon Tai Khoan");
	transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");
	
	log.info("TC_04_Step_07: Chon tai Khoan trich no");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT2);
	
	log.info("TC_04_Step_08: CLick Tim Kiem");
	transReport.clickToDynamicButton(driver, "Tìm kiếm");

	log.info("TC_04_Step_09: Kiem tra ngay tao giao dich hien thi");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));
	
	log.info("TC_04_Step_10: Kiem tra thoi gian tao dao dich");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));
	
	log.info("TC_04_Step_11: Kiem tra noi dung hien thi");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputData.NOTE));
	
	log.info("TC_04_Step_12: Kiem tra so tien chuyen hien thi");
	verifyEquals(transReport.getTextInDynamicTransaction(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(TransferMoneyInVCB_Data.InputData.MONEY) + " VND"));
	
	log.info("TC_04_Step_13: Click vao giao dich");
	transReport.clickToDynamicTransaction(driver, "0", "com.VCB:id/tvDate");
	
	log.info("TC_04_Step_14: Kiem tra ngay giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").contains(transferTime.split(" ")[0]));
	
	log.info("TC_04_Step_15: Kiem tra thoi gian tao giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));
	
	log.info("TC_04_Step_16: Kiem tra so lenh giao dich");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Số lệnh giao dịch"), transactionNumber);
	
	log.info("TC_04_Step_17: Kiem tra so tai khoan trich no");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản/thẻ trích nợ"), TransferMoneyInVCB_Data.InputData.ACCOUNT2);
	
	log.info("TC_04_Step_18: Kiem tra so tai khoan ghi co");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản ghi có"), TransferMoneyInVCB_Data.InputData.ACCOUNT1);
	
	log.info("TC_04_Step_19: Kiem tra so tien giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Số tiền giao dịch").contains(addCommasToLong(TransferMoneyInVCB_Data.InputData.MONEY) + " VND"));
	
	log.info("TC_04_Step_20: Kiem tra ten nguoi huong hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tên người hưởng"), TransferMoneyInVCB_Data.InputData.RECEIVER_NAME);
	
	log.info("TC_04_Step_21: Kiem tra phi giao dich hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Phí giao dịch"), TransferMoneyInVCB_Data.InputData.COST[1]);
	
	log.info("TC_04_Step_22: Kiem tra so tien phi hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Số tiền phí"), TransferMoneyInVCB_Data.InputData.PAYMENT_BY_OTP_FEE + " VND");
	
	log.info("TC_04_Step_23: Kiem tra loai giao dich");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Loại giao dịch"), TransferMoneyInVCB_Data.InputData.TRANSFER_TYPE);
	
	log.info("TC_04_Step_24: Kiem Tra noi dung giao dich");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Nội dung giao dịch").contains(TransferMoneyInVCB_Data.InputData.NOTE));

	log.info("TC_04_Step_25: Click Back icon");
	transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");
	
	log.info("TC_04_Step_26: Chon Tai Khoan");
	transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");
	
	log.info("TC_04_Step_27: Click chon tai khoan nhan");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT1);
	
	log.info("TC_04_Step_28: Click Tim Kiem");
	transReport.clickToDynamicButton(driver, "Tìm kiếm");
	
	log.info("TC_04_Step_29: Kiem tra ngay tao giao dich hien thi");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));
	
	log.info("TC_04_Step_30: Kiem tra thoi gian tao giao dich hiển thị");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));
	
	log.info("TC_04_Step_31: Kiem tra noi dung giao dich hien thi");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputData.NOTE));
	
	log.info("TC_04_Step_32: Kiem tra so tien chuyen hien thi");
	verifyEquals(transReport.getTextInDynamicTransaction(driver, "1", "com.VCB:id/tvMoney"), ("+ " + addCommasToLong(TransferMoneyInVCB_Data.InputData.MONEY) + " VND"));
	
	log.info("TC_04_Step_33: Click vao bao bao giao dich chi tiet");
	transReport.clickToDynamicTransaction(driver, "0", "com.VCB:id/tvDate");
	
	log.info("TC_04_Step_34: Kiem tra ngay tao giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").contains(transferTime.split(" ")[0]));
	
	log.info("TC_04_Step_35: Kiem tra thoi gian tao giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));
	
	log.info("TC_04_Step_36: Kiem tra so giao dich hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Số lệnh giao dịch"), transactionNumber);
	
	log.info("TC_04_Step_37: Kiem tra tai khoan trich no hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản/thẻ trích nợ"), TransferMoneyInVCB_Data.InputData.ACCOUNT2);
	
	log.info("TC_04_Step_38: Kiem tra tai khoan ghi co hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản ghi có"), TransferMoneyInVCB_Data.InputData.ACCOUNT1);
	
	log.info("TC_04_Step_39: Kiem tra so tien giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Số tiền giao dịch").contains(addCommasToLong(TransferMoneyInVCB_Data.InputData.MONEY) + " VND"));
	
	log.info("TC_04_Step_40: Kiem tra ten nguoi huong hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tên người hưởng"), TransferMoneyInVCB_Data.InputData.RECEIVER_NAME);
	
	log.info("TC_04_Step_41: Kiem tra phi giao dich hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Phí giao dịch"), TransferMoneyInVCB_Data.InputData.COST[1]);
	
	log.info("TC_04_Step_42: Kiem tra so tien phi hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Số tiền phí"), TransferMoneyInVCB_Data.InputData.PAYMENT_BY_OTP_FEE + " VND");
	
	log.info("TC_04_Step_43: Kiem tra loai giao dich hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Loại giao dịch"), TransferMoneyInVCB_Data.InputData.TRANSFER_TYPE);
	
	log.info("TC_04_Step_44: Kiem tra noi dung giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Nội dung giao dịch").contains(TransferMoneyInVCB_Data.InputData.NOTE));

	log.info("TC_04_Step_45: Click quay lai");
	transferInVCB.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");
	
	log.info("TC_04_Step_46: Click quay lai");
	transferInVCB.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");
	
	log.info("TC_04_Step_47: Click quay lai");
	transferInVCB.navigateBack(driver);
}


//@Test
public void TC_05_ChuyenTienNgayCoPhiGiaoDichNguoiChuyenTraUSDVaXacThucBangOTP() {
	
	log.info("TC05_Step 01: Click Chuyen tien trong VCB");
	homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");
	
	log.info("TC05_Step 02: Click chon tai khoan nguon");
	transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
	transferInVCB.clickToDynamicDropDown(driver,"Tài khoản nguồn");

	log.info("TC05_Step 03: Chon account 1");
	transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT1);
	
	log.info("TC05_Step 04: Lay so du kha dung tai khoan 1");
	String beforeBalanceOfAccount2 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
	double beforeBalanceAmountOfAccount2 = convertMoneyToDouble(beforeBalanceOfAccount2,"VND");
	
	log.info("TC05_Step : Click tai khoan nguon");
	transferInVCB.clickToDynamicDropDown(driver,"Tài khoản nguồn");
	
	log.info("TC05_Step : Chon USD account");
	transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.USD_ACCOUNT);
	
	log.info("TC05_Step : Lay so du tai khoan USD");
	String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
	double beforeBalanceAmountOfAccount1 = convertMoneyToDouble(beforeBalanceOfAccount1,"USD");
	
	log.info("TC05_Step : Nhap tai khoan nhan");
	transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT1, "Nhập/chọn tài khoản nhận VND");
	
	log.info("TC05_Step : Nhap so tien can chuyen");
	transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputData.AMOUNT_OF_EUR_OR_USD_TRANSFER, "Số tiền");
	
	log.info("TC05_Step : Lay ti gia quy doi");
	String exchangeRate = transferInVCB.getDynamicTextInTextView(driver, "Tỷ giá quy đổi tham khảo");
	exchangeRateUSD = exchangeRate.split(" ~ ");
	rate = convertMoneyToDouble(exchangeRateUSD[1], "VND");
	
	log.info("TC05_Step : Chon phuong thuc xac thuc");
	transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
	
	log.info("TC05_Step : Chon phi giao dich nguoi chuyen tra");
	transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Người chuyển trả");
	
	log.info("TC05_Step :  Nhap noi dung");
	transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputData.NOTE, "Nội dung");
	
	log.info("TC05_Step : Click tiep tuc");
	transferInVCB.clickToDynamicButton(driver, "Tiếp tục");
	
	log.info("TC05_Step : Kiem tra hinh thuc chuyen tien hien thi");
	verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Hình thức chuyển tiền"), TransferMoneyInVCB_Data.InputData.OPTION_TRANSFER[0]);
	
	log.info("TC05_Step : Kiem tra tai khoan nguon hien thi");
	verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Tài khoản nguồn"), TransferMoneyInVCB_Data.InputData.USD_ACCOUNT);
	
	log.info("TC05_Step : Kiem tra tai khoan dich hien thi");
	verifyTrue(transferInVCB.getDynamicTextInTextView(driver, "Tài khoản đích / VND").contains(TransferMoneyInVCB_Data.InputData.ACCOUNT1));
	
	log.info("TC05_Step : Kiem tra so tien chuyen hien thi");
	verifyTrue(transferInVCB.getDynamicTextInTextView(driver, "Số tiền (USD)").contains(addCommasToDouble(TransferMoneyInVCB_Data.InputData.AMOUNT_OF_EUR_OR_USD_TRANSFER)+"USD"));
	
	log.info("TC05_Step : Kiem tra Nguoi chuyen tra hien thi");
	verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.InputData.COST[0]));

	log.info("TC05_Step : Kiem tra noi dung hien thi");
	verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Nội dung"), TransferMoneyInVCB_Data.InputData.NOTE);
	
	log.info("TC05_Step : Chon phuong thuc xac thuc");
	transferInVCB.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");
	
	log.info("TC05_Step : Chon SMS OTP");
	transferInVCB.clickToDynamicButtonLinkOrLinkText(driver,"SMS OTP");
	
	log.info("TC05_Step : Kiem tra so tien phi hien thi"); 
	verifyTrue(transferInVCB.getDynamicTextInTextView(driver, "Số tiền phí").contains(TransferMoneyInVCB_Data.InputData.USD_PAYMENT_BY_OTP_FEE+" USD"));
	
	log.info("TC05_Step : Lay tien phi chuyen ");
	transferFee = transferInVCB.getDynamicTextInTextView(driver, "Số tiền phí");
	double newTransferFee = convertMoneyToDouble(transferFee,"USD");
	
	log.info("TC05_Step : Click tiep tuc");
	transferInVCB.clickToDynamicButton(driver, "Tiếp tục");
	
	log.info("TC05_Step : Dien OTP");
	transferInVCB.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");
	
	log.info("TC05_Step : Click tiep tuc");
	transferInVCB.clickToDynamicButton(driver, "Tiếp tục");
	verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE));
	
	log.info("TC05_Step : Lay thoi gian tao");
	transferTime = transferInVCB.getDynamicTransferTime(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE, "4");
	
	log.info("TC05_Step : Kiem tra so tien chuyen hien thi");
	verifyEquals(transferInVCB.getDynamicTransferTime(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE, "3"),  addCommasToDouble(TransferMoneyInVCB_Data.InputData.AMOUNT_OF_EUR_OR_USD_TRANSFER)+" USD");
	
	log.info("TC05_Step : Kiem tra ten nguoi thu huong hien thi");
	verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Tên người thụ hưởng"), TransferMoneyInVCB_Data.InputData.RECEIVER_NAME);
	
	log.info("TC05_Step : Kiem tra tai khoan dich hien thi");
	verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Tài khoản đích"), TransferMoneyInVCB_Data.InputData.ACCOUNT1);

	log.info("TC05_Step : Lay ma giao dich");
	transactionNumber = transferInVCB.getDynamicTextInTextView(driver, "Mã giao dịch");
	
	log.info("TC05_Step : Kiem tra noi dung hien thi");
	verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Nội dung"), TransferMoneyInVCB_Data.InputData.NOTE);
	
	log.info("TC05_Step : Click thuc hien giao dich moi");
	transferInVCB.clickToDynamicButton(driver, "Thực hiện giao dịch mới");
	
	log.info("TC05_Step : Click tai khoan nguon");
	transferInVCB.clickToDynamicDropDown(driver,"Tài khoản nguồn");
	
	log.info("TC05_Step :Click USD account");
	transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.USD_ACCOUNT);
	
	log.info("TC05_Step : Lay so du kha dung tai khoan USD");
	String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
	double afterBalanceAmountOfAccount1 = convertMoneyToDouble(afterBalanceOfAccount1,"USD");
	
	log.info("TC05_Step : Convert so tien chuyen");
	double transferMoney = convertMoneyToDouble(TransferMoneyInVCB_Data.InputData.AMOUNT_OF_EUR_OR_USD_TRANSFER,"USD");
	
	log.info("TC05_Step : Kiem tra so du tai khoan USD sau khi chuyen tien");
	verifyEquals(beforeBalanceAmountOfAccount1 - transferMoney- newTransferFee, afterBalanceAmountOfAccount1);
	
	log.info("TC05_Step : Kiem tra tai khoan nguon");
	transferInVCB.clickToDynamicDropDown(driver,"Tài khoản nguồn");
	
	log.info("TC05_Step : Click chon tai khoan chuyen den");
	transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT1);
	
	log.info("TC05_Step : Lay so du kha dung tai khoan chuyen den");
	String afterBalanceOfAccount2 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
	double afterBalanceAmountOfAccount2 = convertMoneyToDouble(afterBalanceOfAccount2,"VND");
	
	log.info("TC05_Step : Kiem tra so du tai khoan duoc chuyen den");
	verifyEquals(beforeBalanceAmountOfAccount2 + transferMoney*rate , afterBalanceAmountOfAccount2);

}

//@Test
public void TC_06_KiemTraChiTietGiaoDichChuyenTienNgayCoPhiGiaoDichNguoiChuyenTraUSDVaXacThucBangOTP() throws InterruptedException {
	
	log.info("TC06_Step 01 : Click  nut Back");
	transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");
	
	log.info("TC06_Step 02: Click vao More Icon");
	homePage = PageFactoryManager.getHomePageObject(driver);
	homePage.clickToDynamicBottomMenu(driver, "com.VCB:id/menu_5");
	
	log.info("TC06_Step : Click Bao Cao Dao Dich");
	transReport = PageFactoryManager.getTransactionReportPageObject(driver);
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");
	
	log.info("TC06_Step : Click Tat Ca Cac Loai Giao Dich");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");
	
	log.info("TC06_Step :Chon Chuyen Tien Trong VCB");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền trong Vietcombank");
	
	log.info("TC06_Step : Click Chon Tai Khoan");
	transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");
	
	log.info("TC06_Step : Chon 1 tai Khoan");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.USD_ACCOUNT);
	
	log.info("TC06_Step : CLick Tim Kiem");
	transReport.clickToDynamicButton(driver, "Tìm kiếm");
	
	log.info("TC06_Step : Kiem tra ngay tao giao dich hien thi");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));
	
	log.info("TC06_Step : Kiem tra thoi gian tao dao dich");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));
	
	log.info("TC06_Step : Kiem tra noi dung hien thi");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputData.NOTE));
	
	log.info("TC06_Step : Kiem tra so tien chuyen hien thi");
	verifyEquals(transReport.getTextInDynamicTransaction(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToDouble(TransferMoneyInVCB_Data.InputData.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " USD"));
	
	log.info("TC06_Step : Click vao giao dich");
	transReport.clickToDynamicTransaction(driver, "0", "com.VCB:id/tvDate");
	
	log.info("TC06_Step : Kiem tra ngay giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").contains(transferTime.split(" ")[0]));
	
	log.info("TC06_Step : Kiem tra thoi gian tao giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));
	
	log.info("TC06_Step :Kiem tra so lenh giao dich");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Số lệnh giao dịch"), transactionNumber);
	
	log.info("TC06_Step : Kiem tra so tai khoan trich no");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản/thẻ trích nợ"), TransferMoneyInVCB_Data.InputData.USD_ACCOUNT);
	
	log.info("TC06_Step : Kiem tra so tai khoan ghi co");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản ghi có"), TransferMoneyInVCB_Data.InputData.ACCOUNT1);
	
	log.info("TC06_Step : Kiem tra so tien giao dich hien thi ");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Số tiền giao dịch").equals(addCommasToDouble(TransferMoneyInVCB_Data.InputData.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " USD"));
	
	
	log.info("TC06_Step : Kiem tra so tien quy doi hien thi ");
	String changedMoney = convertMoneyToLong(TransferMoneyInVCB_Data.InputData.AMOUNT_OF_EUR_OR_USD_TRANSFER, "USD")*convertMoneyToLong(exchangeRateUSD[1], "VND") +"";
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Số tiền quy đổi").contains(addCommasToLong(changedMoney) + " VND"));
	
	log.info("TC06_Step : Kiem tra ten nguoi huong hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tên người hưởng"), TransferMoneyInVCB_Data.InputData.RECEIVER_NAME);
	
	log.info("TC06_Step : Kiem tra phi giao dich hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Phí giao dịch"), TransferMoneyInVCB_Data.InputData.COST[0]);
	
	log.info("TC06_Step : Kiem tra so tien phi hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Số tiền phí"), TransferMoneyInVCB_Data.InputData.PAYMENT_BY_OTP_FEE + " VND");
	
	log.info("TC06_Step : Kiem tra loai giao dich");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Loại giao dịch"), TransferMoneyInVCB_Data.InputData.TRANSFER_TYPE);
	
	log.info("TC06_Step : Kiem Tra noi dung giao dich");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Nội dung giao dịch").contains(TransferMoneyInVCB_Data.InputData.NOTE));
	
	log.info("TC06_Step : Click Back icon");
	transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");
	
	log.info("TC06_Step : Chon Tai Khoan");
	transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");
	
	log.info("TC06_Step : Click chon tai khoan nhan");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT1);
	
	log.info("TC06_Step : Click Tim Kiem");
	transReport.clickToDynamicButton(driver, "Tìm kiếm");

	log.info("TC06_Step : Kiem tra ngay tao giao dich hien thi");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));
	
	log.info("TC06_Step : Kiem tra thoi gian tao giao dich hiển thị");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));
	
	log.info("TC06_Step : Kiem tra noi dung giao dich hien thi");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputData.NOTE));
	
	log.info("TC06_Step : Kiem tra so tien chuyen hien thi");
	verifyEquals(transReport.getTextInDynamicTransaction(driver, "1", "com.VCB:id/tvMoney"), ("+ " + addCommasToLong(changedMoney) + " VND"));
	
	log.info("TC06_Step : Click vao bao bao giao dich chi tiet");
	transReport.clickToDynamicTransaction(driver, "0", "com.VCB:id/tvDate");
	
	log.info("TC06_Step : Kiem tra ngay tao giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").contains(transferTime.split(" ")[0]));
	
	log.info("TC06_Step : Kiem tra thoi gian tao giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));
	
	log.info("TC06_Step : Kiem tra so giao dich hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Số lệnh giao dịch"), transactionNumber);
	
	log.info("TC06_Step :Kiem tra tai khoan trich no hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản/thẻ trích nợ"), TransferMoneyInVCB_Data.InputData.USD_ACCOUNT);
	
	log.info("TC06_Step : Kiem tra tai khoan ghi co hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản ghi có"), TransferMoneyInVCB_Data.InputData.ACCOUNT1);
	
	log.info("TC06_Step : Kiem tra so tien giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Số tiền giao dịch").contains(addCommasToDouble(TransferMoneyInVCB_Data.InputData.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " USD"));
	
	log.info("TC06_Step : Kiem tra so tien quy doi hien thi ");

	verifyTrue(transReport.getDynamicTextInTextView(driver, "Số tiền quy đổi").contains(addCommasToLong(changedMoney) + " VND"));
	
	log.info("TC06_Step : Kiem tra ten nguoi huong hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tên người hưởng"), TransferMoneyInVCB_Data.InputData.RECEIVER_NAME);
	
	log.info("TC06_Step : Kiem tra phi giao dich hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Phí giao dịch"), TransferMoneyInVCB_Data.InputData.COST[0]);
	
	log.info("TC06_Step :Kiem tra so tien phi hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Số tiền phí"), TransferMoneyInVCB_Data.InputData.PAYMENT_BY_OTP_FEE + " VND");
	
	log.info("TC06_Step :Kiem tra noi dung giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Nội dung giao dịch").contains(TransferMoneyInVCB_Data.InputData.NOTE));

	log.info("TC06_Step : Kiem tra loaigiao dich hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Loại giao dịch"), TransferMoneyInVCB_Data.InputData.TRANSFER_TYPE);
	
	log.info("TC06_Step : Click quay lai");
	transferInVCB.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");
	
	log.info("TC06_Step : Click quay lai");
	transferInVCB.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");
	
	log.info("TC06_Step : Click quay lai");
	transferInVCB.navigateBack(driver);
}
	

//@Test
public void TC_07_ChuyenTienNgayCoPhiGiaoDichNguoiNhanTraEURVaXacThucBangOTP() {
	
	log.info("TC05_Step 01: Click Chuyen tien trong VCB");
	homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");
	
	log.info("TC05_Step 02: Click chon tai khoan nguon");
	transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
	transferInVCB.clickToDynamicDropDown(driver,"Tài khoản nguồn");

	log.info("TC05_Step 03: Chon account 1");
	transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT1);
	
	log.info("TC05_Step 04: Lay so du kha dung tai khoan 1");
	String beforeBalanceOfAccount2 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
	double beforeBalanceAmountOfAccount2 = convertMoneyToDouble(beforeBalanceOfAccount2,"VND");
	
	log.info("TC05_Step : Click tai khoan nguon");
	transferInVCB.clickToDynamicDropDown(driver,"Tài khoản nguồn");
	
	log.info("TC05_Step : Chon USD account");
	transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.EUR_ACCOUNT);
	
	log.info("TC05_Step : Lay so du tai khoan USD");
	String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
	double  beforeBalanceAmountOfAccount1 = convertMoneyToDouble(beforeBalanceOfAccount1,"EUR");
	
	log.info("TC05_Step : Nhap tai khoan nhan");
	transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT1, "Nhập/chọn tài khoản nhận VND");
	
	log.info("TC05_Step : Nhap so tien can chuyen");
	transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputData.AMOUNT_OF_EUR_OR_USD_TRANSFER, "Số tiền");
	
	log.info("TC05_Step : Lay ti gia quy doi");
	String exchangeRate = transferInVCB.getDynamicTextInTextView(driver, "Tỷ giá quy đổi tham khảo");
	exchangeRateUSD = exchangeRate.split(" ~ ");
	rate = convertMoneyToDouble(exchangeRateUSD[1], "VND");
	
	log.info("TC05_Step : Chon phuong thuc xac thuc");
	transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
	
	log.info("TC05_Step : Chon phi giao dich nguoi nhan tra");
	transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Người nhận trả");
	
	log.info("TC05_Step :  Nhap noi dung");
	transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputData.NOTE, "Nội dung");
	
	log.info("TC05_Step : Click tiep tuc");
	transferInVCB.clickToDynamicButton(driver, "Tiếp tục");
	
	log.info("TC05_Step : Kiem tra hinh thuc chuyen tien hien thi");
	verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Hình thức chuyển tiền"), TransferMoneyInVCB_Data.InputData.OPTION_TRANSFER[0]);
	
	log.info("TC05_Step : Kiem tra tai khoan nguon hien thi");
	verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Tài khoản nguồn"), TransferMoneyInVCB_Data.InputData.EUR_ACCOUNT);
	
	log.info("TC05_Step : Kiem tra tai khoan dich hien thi");
	verifyTrue(transferInVCB.getDynamicTextInTextView(driver, "Tài khoản đích / VND").contains(TransferMoneyInVCB_Data.InputData.ACCOUNT1));
	log.info("TC05_Step : Kiem tra so tien chuyen hien thi");
	verifyTrue(transferInVCB.getDynamicTextInTextView(driver, "Số tiền (EUR)").contains(addCommasToDouble(TransferMoneyInVCB_Data.InputData.AMOUNT_OF_EUR_OR_USD_TRANSFER)+"EUR"));
	
	log.info("TC05_Step : Kiem tra Nguoi nhan tra hien thi");
	verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.InputData.COST[1]));

	log.info("TC05_Step : Kiem tra noi dung hien thi");
	verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Nội dung"), TransferMoneyInVCB_Data.InputData.NOTE);
	
	log.info("TC05_Step : Chon phuong thuc xac thuc");
	transferInVCB.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");
	
	log.info("TC05_Step : Chon SMS OTP");
	transferInVCB.clickToDynamicButtonLinkOrLinkText(driver,"SMS OTP");
	
	
	log.info("TC05_Step : Kiem tra so tien phi hien thi"); 
	verifyTrue(transferInVCB.getDynamicTextInTextView(driver, "Số tiền phí").contains(TransferMoneyInVCB_Data.InputData.EUR_PAYMENT_BY_OTP_FEE+" USD"));
	
	log.info("TC05_Step : Lay tien phi chuyen ");
	transferFee = transferInVCB.getDynamicTextInTextView(driver, "Số tiền phí");
	
	log.info("TC05_Step : Click tiep tuc");
	transferInVCB.clickToDynamicButton(driver, "Tiếp tục");
	
	log.info("TC05_Step : Dien OTP");
	transferInVCB.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");
	
	log.info("TC05_Step : Click tiep tuc");
	transferInVCB.clickToDynamicButton(driver, "Tiếp tục");
	verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE));
	
	log.info("TC05_Step : Lay thoi gian tao");
	transferTime = transferInVCB.getDynamicTransferTime(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE, "4");
	
	log.info("TC05_Step : Kiem tra so tien chuyen hien thi");
	verifyEquals(transferInVCB.getDynamicTransferTime(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE, "3"),  addCommasToDouble(TransferMoneyInVCB_Data.InputData.AMOUNT_OF_EUR_OR_USD_TRANSFER)+" EUR");
	
	log.info("TC05_Step : Kiem tra ten nguoi thu huong hien thi");
	verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Tên người thụ hưởng"), TransferMoneyInVCB_Data.InputData.RECEIVER_NAME);
	
	log.info("TC05_Step : Kiem tra tai khoan dich hien thi");
	verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Tài khoản đích"), TransferMoneyInVCB_Data.InputData.ACCOUNT1);

	log.info("TC05_Step : Lay ma giao dich");
	transactionNumber = transferInVCB.getDynamicTextInTextView(driver, "Mã giao dịch");
	
	log.info("TC05_Step : Kiem tra noi dung hien thi");
	verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Nội dung"), TransferMoneyInVCB_Data.InputData.NOTE);
	
	log.info("TC05_Step : Click thuc hien giao dich moi");
	transferInVCB.clickToDynamicButton(driver, "Thực hiện giao dịch mới");
	
	log.info("TC05_Step : Click tai khoan nguon");
	transferInVCB.clickToDynamicDropDown(driver,"Tài khoản nguồn");
	
	log.info("TC05_Step :Click EUR account");
	transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.EUR_ACCOUNT);
	
	log.info("TC05_Step : Lay so du kha dung tai khoan EUR");
	String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
	double afterBalanceAmountOfAccount1 = convertMoneyToDouble(afterBalanceOfAccount1,"EUR");
	
	log.info("TC05_Step : Convert so tien chuyen");
	double transferMoney = convertMoneyToDouble(TransferMoneyInVCB_Data.InputData.AMOUNT_OF_EUR_OR_USD_TRANSFER,"EUR");
	
	log.info("TC05_Step : Kiem tra so du tai khoan USD sau khi chuyen tien");
	verifyEquals(beforeBalanceAmountOfAccount1 - transferMoney, afterBalanceAmountOfAccount1);
	
	log.info("TC05_Step : Kiem tra tai khoan nguon");
	transferInVCB.clickToDynamicDropDown(driver,"Tài khoản nguồn");
	
	log.info("TC05_Step : Click chon tai khoan chuyen den");
	transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT1);
	
	log.info("TC05_Step : Lay so du kha dung tai khoan chuyen den");
	String afterBalanceOfAccount2 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
	double afterBalanceAmountOfAccount2 = convertMoneyToDouble(afterBalanceOfAccount2,"VND");
	
	log.info("TC05_Step : Kiem tra so du tai khoan duoc chuyen den");
	double transferFee = convertMoneyToDouble(TransferMoneyInVCB_Data.InputData.PAYMENT_BY_OTP_FEE, "VND"); 
	verifyEquals(beforeBalanceAmountOfAccount2 + transferMoney*rate-transferFee  , afterBalanceAmountOfAccount2);

}
//@Test
public void TC_08_KiemTraChiTietGiaoDichChuyenTienNgayCoPhiGiaoDichNguoiNhanTraEURVaXacThucBangOTP() throws InterruptedException {
	
	log.info("TC06_Step 01 : Click  nut Back");
	transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");
	
	log.info("TC06_Step 02: Click vao More Icon");
	homePage = PageFactoryManager.getHomePageObject(driver);
	homePage.clickToDynamicBottomMenu(driver, "com.VCB:id/menu_5");
	
	log.info("TC06_Step : Click Bao Cao Dao Dich");
	transReport = PageFactoryManager.getTransactionReportPageObject(driver);
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");
	
	log.info("TC06_Step : Click Tat Ca Cac Loai Giao Dich");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");
	
	log.info("TC06_Step :Chon Chuyen Tien Trong VCB");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền trong Vietcombank");
	
	log.info("TC06_Step : Click Chon Tai Khoan");
	transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");
	
	log.info("TC06_Step : Chon 1 tai Khoan");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.EUR_ACCOUNT);
	
	log.info("TC06_Step : CLick Tim Kiem");
	transReport.clickToDynamicButton(driver, "Tìm kiếm");
	
	log.info("TC06_Step : Kiem tra ngay tao giao dich hien thi");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));
	
	log.info("TC06_Step : Kiem tra thoi gian tao dao dich");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));
	
	log.info("TC06_Step : Kiem tra noi dung hien thi");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputData.NOTE));
	
	log.info("TC06_Step : Kiem tra so tien chuyen hien thi");
	verifyEquals(transReport.getTextInDynamicTransaction(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToDouble(TransferMoneyInVCB_Data.InputData.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " EUR"));
	
	log.info("TC06_Step : Click vao giao dich");
	transReport.clickToDynamicTransaction(driver, "0", "com.VCB:id/tvDate");
	
	log.info("TC06_Step : Kiem tra ngay giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").contains(transferTime.split(" ")[0]));
	
	log.info("TC06_Step : Kiem tra thoi gian tao giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));
	
	log.info("TC06_Step :Kiem tra so lenh giao dich");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Số lệnh giao dịch"), transactionNumber);
	
	log.info("TC06_Step : Kiem tra so tai khoan trich no");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản/thẻ trích nợ"), TransferMoneyInVCB_Data.InputData.EUR_ACCOUNT);
	
	log.info("TC06_Step : Kiem tra so tai khoan ghi co");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản ghi có"), TransferMoneyInVCB_Data.InputData.ACCOUNT1);
	
	log.info("TC06_Step : Kiem tra so tien giao dich hien thi ");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Số tiền giao dịch").equals(addCommasToDouble(TransferMoneyInVCB_Data.InputData.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " EUR"));
	
	
	log.info("TC06_Step : Kiem tra so tien quy doi hien thi ");
	String changedMoney = convertMoneyToLong(TransferMoneyInVCB_Data.InputData.AMOUNT_OF_EUR_OR_USD_TRANSFER, "EUR")*convertMoneyToLong(exchangeRateUSD[1], "VND") +"";
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Số tiền quy đổi").contains(addCommasToLong(changedMoney) + " VND"));
	
	log.info("TC06_Step : Kiem tra ten nguoi huong hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tên người hưởng"), TransferMoneyInVCB_Data.InputData.RECEIVER_NAME);
	
	log.info("TC06_Step : Kiem tra phi giao dich hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Phí giao dịch"), TransferMoneyInVCB_Data.InputData.COST[1]);
	
	log.info("TC06_Step : Kiem tra so tien phi hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Số tiền phí"), TransferMoneyInVCB_Data.InputData.PAYMENT_BY_OTP_FEE + " VND");
	
	log.info("TC06_Step : Kiem tra loai giao dich");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Loại giao dịch"), TransferMoneyInVCB_Data.InputData.TRANSFER_TYPE);
	
	log.info("TC06_Step : Kiem Tra noi dung giao dich");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Nội dung giao dịch").contains(TransferMoneyInVCB_Data.InputData.NOTE));
	
	log.info("TC06_Step : Click Back icon");
	transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");
	
	log.info("TC06_Step : Chon Tai Khoan");
	transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");
	
	log.info("TC06_Step : Click chon tai khoan nhan");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT1);
	
	log.info("TC06_Step : Click Tim Kiem");
	transReport.clickToDynamicButton(driver, "Tìm kiếm");

	log.info("TC06_Step : Kiem tra ngay tao giao dich hien thi");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));
	
	log.info("TC06_Step : Kiem tra thoi gian tao giao dich hiển thị");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));
	
	log.info("TC06_Step : Kiem tra noi dung giao dich hien thi");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputData.NOTE));
	
	log.info("TC06_Step : Kiem tra so tien chuyen hien thi");
	verifyEquals(transReport.getTextInDynamicTransaction(driver, "1", "com.VCB:id/tvMoney"), ("+ " + addCommasToLong(changedMoney) + " VND"));
	
	log.info("TC06_Step : Click vao bao bao giao dich chi tiet");
	transReport.clickToDynamicTransaction(driver, "0", "com.VCB:id/tvDate");
	
	log.info("TC06_Step : Kiem tra ngay tao giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").contains(transferTime.split(" ")[0]));
	
	log.info("TC06_Step : Kiem tra thoi gian tao giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));
	
	log.info("TC06_Step : Kiem tra so giao dich hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Số lệnh giao dịch"), transactionNumber);
	
	log.info("TC06_Step :Kiem tra tai khoan trich no hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản/thẻ trích nợ"), TransferMoneyInVCB_Data.InputData.EUR_ACCOUNT);
	
	log.info("TC06_Step : Kiem tra tai khoan ghi co hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản ghi có"), TransferMoneyInVCB_Data.InputData.ACCOUNT1);
	
	log.info("TC06_Step : Kiem tra so tien giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Số tiền giao dịch").contains(addCommasToDouble(TransferMoneyInVCB_Data.InputData.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " EUR"));
	
	log.info("TC06_Step : Kiem tra so tien quy doi hien thi ");

	verifyTrue(transReport.getDynamicTextInTextView(driver, "Số tiền quy đổi").contains(addCommasToLong(changedMoney) + " VND"));
	
	log.info("TC06_Step : Kiem tra ten nguoi huong hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tên người hưởng"), TransferMoneyInVCB_Data.InputData.RECEIVER_NAME);
	
	log.info("TC06_Step : Kiem tra phi giao dich hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Phí giao dịch"), TransferMoneyInVCB_Data.InputData.COST[1]);
	
	log.info("TC06_Step :Kiem tra so tien phi hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Số tiền phí"), TransferMoneyInVCB_Data.InputData.PAYMENT_BY_OTP_FEE + " VND");
	
	log.info("TC06_Step :Kiem tra noi dung giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Nội dung giao dịch").contains(TransferMoneyInVCB_Data.InputData.NOTE));

	log.info("TC06_Step : Kiem tra loaigiao dich hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Loại giao dịch"), TransferMoneyInVCB_Data.InputData.TRANSFER_TYPE);
	
	log.info("TC06_Step : Click quay lai");
	transferInVCB.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");
	
	log.info("TC06_Step : Click quay lai");
	transferInVCB.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");
	
	log.info("TC06_Step : Click quay lai");
	transferInVCB.navigateBack(driver);
}
//@Test
public void TC_09_ChuyenTienNgayDenTaiKhoanKhacChuSoHuuCoPhiGiaoDichNguoiChuyenTraVNDVaXacThucBangOTP() {
	
	log.info("TC_01_Step_01: Click Chuyen tien trong VCB");
	homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");
	
	log.info("TC_01_Step_02:Click tai khoan nguon");
	transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
	transferInVCB.clickToDynamicDropDown(driver,"Tài khoản nguồn");
	
	log.info("TC_01_Step_03: Chon tai khoan chuyen");
	transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT1);
	
	log.info("TC_01_Step_04: Lay so du tai khoan chuyen");
	String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
	long beforeBalanceAmountOfAccount1 = convertMoneyToLong(beforeBalanceOfAccount1,"VND");
	
	log.info("TC_01_Step_05: Nhap tai khoan nhan");
	transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputData.DIFFERENT_OWNER_ACCOUNT, "Nhập/chọn tài khoản nhận VND");
	
	log.info("TC_01_Step_06: Nhap so tien chuyen");
	transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputData.MONEY, "Số tiền");
	
	log.info("TC_01_Step_07: Nhap noi dung");
	transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputData.NOTE, "Nội dung");
	
	log.info("TC_01_Step_08: Click tiep tuc");
	transferInVCB.clickToDynamicButton(driver, "Tiếp tục");
	
	log.info("TC_01_Step_09: Kiem tra hinh thuc chuyen tien hien thi");
	verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Hình thức chuyển tiền"), TransferMoneyInVCB_Data.InputData.OPTION_TRANSFER[0]);
	
	log.info("TC_01_Step_10: Kiem tra tai khoan nguon hien thi");
	verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Tài khoản nguồn"), TransferMoneyInVCB_Data.InputData.ACCOUNT1);
	
	log.info("TC_01_Step_11: Kiem tra tai khoan dich hien thi");
	verifyTrue(transferInVCB.getDynamicTextInTextView(driver, "Tài khoản đích / VND").contains(TransferMoneyInVCB_Data.InputData.DIFFERENT_OWNER_ACCOUNT));
	
	log.info("TC_01_Step_12: Kiem tra so tien chuyen hien thi");
	verifyTrue(transferInVCB.getDynamicTextInTextView(driver, "Số tiền (VND)").contains(addCommasToLong(TransferMoneyInVCB_Data.InputData.MONEY)));
	
	log.info("TC_01_Step_14: Kiem tra phuong thuc thanh toan bang OTP");
	verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.InputData.COST[0]));
	

	log.info("TC_01_Step_16: Kiem tra noi dung hien thi");
	verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Nội dung"), TransferMoneyInVCB_Data.InputData.NOTE);

	log.info("TC_06_Step_17: Chon Phuong thuc nhap");
	transferInVCB.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");
	
	log.info("TC_01_Step_18: Chon SMS OTP");
	transferInVCB.clickToDynamicButtonLinkOrLinkText(driver,"SMS OTP");

	log.info("TC_01_Step_13: Kiem tra so tien phi hien thi");
	verifyTrue(transferInVCB.getDynamicTextInTextView(driver, "Số tiền phí").contains(TransferMoneyInVCB_Data.InputData.TRANSFER_FEE_TO_OTHER_ACCOUNT_OWNER+" VND"));
	
	log.info("TC_01_Step_15: Lay so tien phi");
	transferFee = transferInVCB.getDynamicTextInTextView(driver, "Số tiền phí");
	long newTransferFee = convertMoneyToLong(transferFee,"VND");
	
	log.info("TC_01_Step_19: Click Tiep tuc");
	transferInVCB.clickToDynamicButton(driver, "Tiếp tục");
	
	log.info("TC_01_Step_20: Nhap OTP");
	transferInVCB.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");
	
	log.info("TC_01_Step_21: Click tiep tuc");
	transferInVCB.clickToDynamicButton(driver, "Tiếp tục");
	
	log.info("TC_01_Step_21: Kiem  tra giao dich thanh cong");
	verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE));
	
	log.info("TC_01_Step_23: Lay thoi gian tao giao dich");
	transferTime = transferInVCB.getDynamicTransferTime(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE, "4");
	
	log.info("TC_01_Step_24: Lay ma giao dich");
	transactionNumber = transferInVCB.getDynamicTextInTextView(driver, "Mã giao dịch");
	
	log.info("TC_01_Step_25: Kiem tra ten nguoi thu huong hien thi");
	verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Tên người thụ hưởng"), TransferMoneyInVCB_Data.InputData.DIFFERENT_OWNER_NAME);
	
	log.info("TC_01_Step_26: Kiem tra tai khoan dich hien thi");
	verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Tài khoản đích"), TransferMoneyInVCB_Data.InputData.DIFFERENT_OWNER_ACCOUNT);
	
	log.info("TC_01_Step_27: Kiem tra noi dung hien thi");
	verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Nội dung"), TransferMoneyInVCB_Data.InputData.NOTE);
	
	log.info("TC_01_Step_28: Click thuc hien giao dich moi");
	transferInVCB.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

	log.info("TC_01_Step_29: Click tai khoan nguon");
	transferInVCB.clickToDynamicDropDown(driver,"Tài khoản nguồn");
	
	log.info("TC_01_Step_30: Chon tai ngoan chuyen");
	transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT1);
	
	log.info("TC_01_Step_31: Lay so du tai khoan chuyen");
	String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
	long afterBalanceAmountOfAccount1 = convertMoneyToLong(afterBalanceOfAccount1,"VND");
	
	log.info("TC_01_Step_32: Kiem tra so du tai khoan chuyen sau khi thuc hien giao dich");
	long transferMoney = convertMoneyToLong(TransferMoneyInVCB_Data.InputData.MONEY,"VND");
	verifyEquals(beforeBalanceAmountOfAccount1 - transferMoney - newTransferFee, afterBalanceAmountOfAccount1);


}

//@Test
public void TC_10_KiemTraChiTietGiaoDichChuyenTienNgayDenTaiKhoanKhacChuSoHuuCoPhiGiaoDichNguoiChuyenTraVNDVaXacThucBangOTP() {
	log.info("TC_02_Step_01 : Click  nut Back");
	transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");
	
	log.info("TC_02_Step_02: Click vao More Icon");
	homePage = PageFactoryManager.getHomePageObject(driver);
	homePage.clickToDynamicBottomMenu(driver, "com.VCB:id/menu_5");
	
	log.info("TC_02_Step_03: Click Bao Cao Dao Dich");
	transReport = PageFactoryManager.getTransactionReportPageObject(driver);
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");
	
	log.info("TC_02_Step_04: Click Tat Ca Cac Loai Giao Dich");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");
	
	log.info("TC_02_Step_05: Chon Chuyen Tien Trong VCB");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền trong Vietcombank");
	
	log.info("TC_02_Step_06: Click Chon Tai Khoan");
	transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");
	
	log.info("TC_02_Step_07: Chon tai Khoan chuyen");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT1);
	
	log.info("TC_02_Step_08: CLick Tim Kiem");
	transReport.clickToDynamicButton(driver, "Tìm kiếm");

	log.info("TC_02_Step_09: Kiem tra ngay tao giao dich hien thi");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));
	
	log.info("TC_02_Step_10: Kiem tra thoi gian tao dao dich");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));
	
	log.info("TC_02_Step_11: Kiem tra noi dung hien thi");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputData.NOTE));
	
	log.info("TC_02_Step_12: Kiem tra so tien chuyen hien thi");
	verifyEquals(transReport.getTextInDynamicTransaction(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(TransferMoneyInVCB_Data.InputData.MONEY) + " VND"));
	
	log.info("TC_02_Step_13: Click vao giao dich");
	transReport.clickToDynamicTransaction(driver, "0", "com.VCB:id/tvDate");
	
	log.info("TC_02_Step_14: Kiem tra ngay giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").contains(transferTime.split(" ")[0]));
	
	log.info("TC_02_Step_15: Kiem tra thoi gian tao giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));
	
	log.info("TC_02_Step_16: Kiem tra thoi gian tao giao dich hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Số lệnh giao dịch"), transactionNumber);
	
	log.info("TC_02_Step_17: Kiem tra so tai khoan trich no");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản/thẻ trích nợ"), TransferMoneyInVCB_Data.InputData.ACCOUNT1);
	
	log.info("TC_02_Step_18: Kiem tra so tai khoan ghi co");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản ghi có"), TransferMoneyInVCB_Data.InputData.DIFFERENT_OWNER_ACCOUNT);
	
	log.info("TC_02_Step_19: Kiem tra so tien giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Số tiền giao dịch").contains( addCommasToLong(TransferMoneyInVCB_Data.InputData.MONEY) + " VND"));
	
	log.info("TC_02_Step_20: Kiem tra ten nguoi huong hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tên người hưởng"), TransferMoneyInVCB_Data.InputData.DIFFERENT_OWNER_NAME);
	
	log.info("TC_02_Step_21: Kiem tra phi giao dich hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Phí giao dịch"), TransferMoneyInVCB_Data.InputData.COST[0]);
	
	log.info("TC_02_Step_22: Kiem tra so tien phi hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Số tiền phí"), TransferMoneyInVCB_Data.InputData.TRANSFER_FEE_TO_OTHER_ACCOUNT_OWNER + " VND");
	
	log.info("TC_02_Step_23: Kiem tra loai giao dich");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Loại giao dịch"), TransferMoneyInVCB_Data.InputData.TRANSFER_TYPE);
	
	log.info("TC_02_Step_24: Kiem Tra noi dung giao dich");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Nội dung giao dịch").contains(TransferMoneyInVCB_Data.InputData.NOTE));

	log.info("TC_02_Step_25: Click Back icon");
	transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");
	
	log.info("TC_02_Step_36: Click quay lai");
	transferInVCB.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");
	
	log.info("TC_02_Step_37: Click quay lai");
	transferInVCB.navigateBack(driver);
}


//@Test
public void TC_11_ChuyenTienNgayDenTaiKhoanKhacChuSoHuuCoPhiGiaoDichNguoiNhanTraVNDVaXacThucBangOTP() {
	
	log.info("TC_01_Step_01: Click Chuyen tien trong VCB");
	homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");
	
	log.info("TC_01_Step_02:Click tai khoan nguon");
	transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
	transferInVCB.clickToDynamicDropDown(driver,"Tài khoản nguồn");
	
	log.info("TC_01_Step_03: Chon tai khoan chuyen");
	transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT1);
	
	log.info("TC_01_Step_04: Lay so du tai khoan chuyen");
	String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
	long beforeBalanceAmountOfAccount1 = convertMoneyToLong(beforeBalanceOfAccount1,"VND");
	
	log.info("TC_01_Step_05: Nhap tai khoan nhan");
	transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputData.DIFFERENT_OWNER_ACCOUNT, "Nhập/chọn tài khoản nhận VND");
	
	log.info("TC_01_Step_06: Nhap so tien chuyen");
	transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputData.MONEY, "Số tiền");
	
	log.info("TC_01_Step_07: Nhap noi dung");
	transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputData.NOTE, "Nội dung");
	
	log.info("TC_03_Step_10: Click phi giao dich nguoi chuyen tra");
	transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
	
	log.info("TC_03_Step_11: Chon nguoi nhan tra");
	transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Người nhận trả");
	
	log.info("TC_01_Step_08: Click tiep tuc");
	transferInVCB.clickToDynamicButton(driver, "Tiếp tục");
	
	log.info("TC_01_Step_09: Kiem tra hinh thuc chuyen tien hien thi");
	verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Hình thức chuyển tiền"), TransferMoneyInVCB_Data.InputData.OPTION_TRANSFER[0]);
	
	log.info("TC_01_Step_10: Kiem tra tai khoan nguon hien thi");
	verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Tài khoản nguồn"), TransferMoneyInVCB_Data.InputData.ACCOUNT1);
	
	log.info("TC_01_Step_11: Kiem tra tai khoan dich hien thi");
	verifyTrue(transferInVCB.getDynamicTextInTextView(driver, "Tài khoản đích / VND").contains(TransferMoneyInVCB_Data.InputData.DIFFERENT_OWNER_ACCOUNT));
	
	log.info("TC_01_Step_12: Kiem tra so tien chuyen hien thi");
	verifyTrue(transferInVCB.getDynamicTextInTextView(driver, "Số tiền (VND)").contains(addCommasToLong(TransferMoneyInVCB_Data.InputData.MONEY)));
	
	log.info("TC_01_Step_14: Kiem tra phuong thuc thanh toan bang OTP");
	verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.InputData.COST[1]));

	log.info("TC_01_Step_16: Kiem tra noi dung hien thi");
	verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Nội dung"), TransferMoneyInVCB_Data.InputData.NOTE);

	log.info("TC_06_Step_17: Chon Phuong thuc nhap");
	transferInVCB.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");
	
	log.info("TC_01_Step_18: Chon SMS OTP");
	transferInVCB.clickToDynamicButtonLinkOrLinkText(driver,"SMS OTP");

	log.info("TC_01_Step_13: Kiem tra so tien phi hien thi");
	verifyTrue(transferInVCB.getDynamicTextInTextView(driver, "Số tiền phí").contains(TransferMoneyInVCB_Data.InputData.TRANSFER_FEE_TO_OTHER_ACCOUNT_OWNER+" VND"));
	
	log.info("TC_01_Step_19: Click Tiep tuc");
	transferInVCB.clickToDynamicButton(driver, "Tiếp tục");
	
	log.info("TC_01_Step_20: Nhap OTP");
	transferInVCB.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");
	
	log.info("TC_01_Step_21: Click tiep tuc");
	transferInVCB.clickToDynamicButton(driver, "Tiếp tục");
	
	log.info("TC_01_Step_21: Kiem  tra giao dich thanh cong");
	verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE));
	
	log.info("TC_01_Step_23: Lay thoi gian tao giao dich");
	transferTime = transferInVCB.getDynamicTransferTime(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE, "4");
	
	log.info("TC_01_Step_24: Lay ma giao dich");
	transactionNumber = transferInVCB.getDynamicTextInTextView(driver, "Mã giao dịch");
	
	log.info("TC_01_Step_25: Kiem tra ten nguoi thu huong hien thi");
	verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Tên người thụ hưởng"), TransferMoneyInVCB_Data.InputData.DIFFERENT_OWNER_NAME);
	
	log.info("TC_01_Step_26: Kiem tra tai khoan dich hien thi");
	verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Tài khoản đích"), TransferMoneyInVCB_Data.InputData.DIFFERENT_OWNER_ACCOUNT);
	
	log.info("TC_01_Step_27: Kiem tra noi dung hien thi");
	verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Nội dung"), TransferMoneyInVCB_Data.InputData.NOTE);
	
	log.info("TC_01_Step_28: Click thuc hien giao dich moi");
	transferInVCB.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

	log.info("TC_01_Step_29: Click tai khoan nguon");
	transferInVCB.clickToDynamicDropDown(driver,"Tài khoản nguồn");
	
	log.info("TC_01_Step_30: Chon tai ngoan chuyen");
	transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT1);
	
	log.info("TC_01_Step_31: Lay so du tai khoan chuyen");
	String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
	long afterBalanceAmountOfAccount1 = convertMoneyToLong(afterBalanceOfAccount1,"VND");
	
	log.info("TC_01_Step_32: Kiem tra so du tai khoan chuyen sau khi thuc hien giao dich");
	long transferMoney = convertMoneyToLong(TransferMoneyInVCB_Data.InputData.MONEY,"VND");
	verifyEquals(beforeBalanceAmountOfAccount1 - transferMoney, afterBalanceAmountOfAccount1);


}
//@Test
public void TC_12_KiemTraChiTietGiaoDichChuyenTienNgayDenTaiKhoanKhacChuSoHuuCoPhiGiaoDichNguoiNhanTraVNDVaXacThucBangOTP() {
	log.info("TC_02_Step_01 : Click  nut Back");
	transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");
	
	log.info("TC_02_Step_02: Click vao More Icon");
	homePage = PageFactoryManager.getHomePageObject(driver);
	homePage.clickToDynamicBottomMenu(driver, "com.VCB:id/menu_5");
	
	log.info("TC_02_Step_03: Click Bao Cao Dao Dich");
	transReport = PageFactoryManager.getTransactionReportPageObject(driver);
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");
	
	log.info("TC_02_Step_04: Click Tat Ca Cac Loai Giao Dich");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");
	
	log.info("TC_02_Step_05: Chon Chuyen Tien Trong VCB");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền trong Vietcombank");
	
	log.info("TC_02_Step_06: Click Chon Tai Khoan");
	transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");
	
	log.info("TC_02_Step_07: Chon tai Khoan chuyen");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT1);
	
	log.info("TC_02_Step_08: CLick Tim Kiem");
	transReport.clickToDynamicButton(driver, "Tìm kiếm");

	log.info("TC_02_Step_09: Kiem tra ngay tao giao dich hien thi");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));
	
	log.info("TC_02_Step_10: Kiem tra thoi gian tao dao dich");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));
	
	log.info("TC_02_Step_11: Kiem tra noi dung hien thi");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputData.NOTE));
	
	log.info("TC_02_Step_12: Kiem tra so tien chuyen hien thi");
	verifyEquals(transReport.getTextInDynamicTransaction(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(TransferMoneyInVCB_Data.InputData.MONEY) + " VND"));
	
	log.info("TC_02_Step_13: Click vao giao dich");
	transReport.clickToDynamicTransaction(driver, "0", "com.VCB:id/tvDate");
	
	log.info("TC_02_Step_14: Kiem tra ngay giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").contains(transferTime.split(" ")[0]));
	
	log.info("TC_02_Step_15: Kiem tra thoi gian tao giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));
	
	log.info("TC_02_Step_16: Kiem tra thoi gian tao giao dich hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Số lệnh giao dịch"), transactionNumber);
	
	log.info("TC_02_Step_17: Kiem tra so tai khoan trich no");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản/thẻ trích nợ"), TransferMoneyInVCB_Data.InputData.ACCOUNT1);
	
	log.info("TC_02_Step_18: Kiem tra so tai khoan ghi co");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản ghi có"), TransferMoneyInVCB_Data.InputData.DIFFERENT_OWNER_ACCOUNT);
	
	log.info("TC_02_Step_19: Kiem tra so tien giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Số tiền giao dịch").contains( addCommasToLong(TransferMoneyInVCB_Data.InputData.MONEY) + " VND"));
	
	log.info("TC_02_Step_20: Kiem tra ten nguoi huong hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tên người hưởng"), TransferMoneyInVCB_Data.InputData.DIFFERENT_OWNER_NAME);
	
	log.info("TC_02_Step_21: Kiem tra phi giao dich hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Phí giao dịch"), TransferMoneyInVCB_Data.InputData.COST[1]);
	
	log.info("TC_02_Step_22: Kiem tra so tien phi hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Số tiền phí"), TransferMoneyInVCB_Data.InputData.TRANSFER_FEE_TO_OTHER_ACCOUNT_OWNER + " VND");
	
	log.info("TC_02_Step_23: Kiem tra loai giao dich");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Loại giao dịch"), TransferMoneyInVCB_Data.InputData.TRANSFER_TYPE);
	
	log.info("TC_02_Step_24: Kiem Tra noi dung giao dich");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Nội dung giao dịch").contains(TransferMoneyInVCB_Data.InputData.NOTE));

	log.info("TC_02_Step_25: Click Back icon");
	transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");
	
	log.info("TC_02_Step_36: Click quay lai");
	transferInVCB.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");
	
	log.info("TC_02_Step_37: Click quay lai");
	transferInVCB.navigateBack(driver);
}

//@Test
public void TC_13_ChuyenTienNgayCoPhiGiaoDichNguoiChuyenTraVNDVaXacThucBangMatKhau() {
	
	log.info("TC_01_Step_01: Click Chuyen tien trong VCB");
	homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");
	
	log.info("TC_01_Step_02:Click tai khoan nguon");
	transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
	transferInVCB.clickToDynamicDropDown(driver,"Tài khoản nguồn");

	log.info("TC_01_Step_03: Chon tai khoan dich");
	transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT2);
	
	log.info("TC_01_Step_04: Lay so du tai khoan dich");
	String beforeBalanceOfAccount2 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
	long beforeBalanceAmountOfAccount2 = convertMoneyToLong(beforeBalanceOfAccount2,"VND");
	
	log.info("TC_01_Step_05:Click tai khoan nguon");
	transferInVCB.clickToDynamicDropDown(driver,"Tài khoản nguồn");
	
	log.info("TC_01_Step_06: Chon tai khoan chuyen");
	transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT1);
	
	log.info("TC_01_Step_07: Lay so du tai khoan chuyen");
	String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
	long beforeBalanceAmountOfAccount1 = convertMoneyToLong(beforeBalanceOfAccount1,"VND");
	
	log.info("TC_01_Step_08: Nhap tai khoan nhan");
	transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT2, "Nhập/chọn tài khoản nhận VND");
	
	log.info("TC_01_Step_09: Nhap so tien chuyen");
	transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputData.MONEY, "Số tiền");
	
	log.info("TC_01_Step_10: Nhap noi dung");
	transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputData.NOTE, "Nội dung");
	
	log.info("TC_01_Step_11: Click tiep tuc");
	transferInVCB.clickToDynamicButton(driver, "Tiếp tục");
	
	log.info("TC_01_Step_12: Kiem tra hinh thuc chuyen tien hien thi");
	verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Hình thức chuyển tiền"), TransferMoneyInVCB_Data.InputData.OPTION_TRANSFER[0]);
	
	log.info("TC_01_Step_13: Kiem tra tai khoan nguon hien thi");
	verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Tài khoản nguồn"), TransferMoneyInVCB_Data.InputData.ACCOUNT1);
	
	log.info("TC_01_Step_14: Kiem tra tai khoan dich hien thi");
	verifyTrue(transferInVCB.getDynamicTextInTextView(driver, "Tài khoản đích / VND").contains(TransferMoneyInVCB_Data.InputData.ACCOUNT2));
	
	log.info("TC_01_Step_15: Kiem tra so tien chuyen hien thi");
	verifyTrue(transferInVCB.getDynamicTextInTextView(driver, "Số tiền (VND)").contains(addCommasToLong(TransferMoneyInVCB_Data.InputData.MONEY)));
	

	log.info("TC_01_Step_17: Kiem tra phuong thuc thanh toan bang OTP");
	verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.InputData.COST[0]));
	

	log.info("TC_01_Step_19: Kiem tra noi dung hien thi");
	verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Nội dung"), TransferMoneyInVCB_Data.InputData.NOTE);

	log.info("TC_06_Step_20: Chon Phuong thuc nhap");
	transferInVCB.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");
	
	log.info("TC_01_Step_21: Chon SMS OTP");
	transferInVCB.clickToDynamicButtonLinkOrLinkText(driver,"Mật khẩu đăng nhập");

	log.info("TC_01_Step_16: Kiem tra so tien phi hien thi");
	verifyTrue(transferInVCB.getDynamicTextInTextView(driver, "Số tiền phí").contains(TransferMoneyInVCB_Data.InputData.PAYMENT_BY_PASSWORD_FEE+" VND"));
	
	log.info("TC_01_Step_18: Lay so tien phi");
	transferFee = transferInVCB.getDynamicTextInTextView(driver, "Số tiền phí");
	long newTransferFee = convertMoneyToLong(transferFee,"VND");
	
	log.info("TC_01_Step_22: Click Tiep tuc");
	transferInVCB.clickToDynamicButton(driver, "Tiếp tục");
	
	log.info("TC_01_Step_23: Nhap OTP");
	transferInVCB.inputToDynamicPopupPasswordInput(driver, LogIn_Data.Login_Account.NEW_PASSWORD, "Tiếp tục");
	
	log.info("TC_01_Step_24: Click tiep tuc");
	transferInVCB.clickToDynamicButton(driver, "Tiếp tục");
	
	log.info("TC_01_Step_25: Kiem  tra giao dich thanh cong");
	verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE));
	
	log.info("TC_01_Step_26: Lay thoi gian tao giao dich");
	transferTime = transferInVCB.getDynamicTransferTime(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE, "4");
	
	log.info("TC_01_Step_27: Lay ma giao dich");
	transactionNumber = transferInVCB.getDynamicTextInTextView(driver, "Mã giao dịch");
	
	log.info("TC_01_Step_28: Kiem tra ten nguoi thu huong hien thi");
	verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Tên người thụ hưởng"), TransferMoneyInVCB_Data.InputData.RECEIVER_NAME);
	
	log.info("TC_01_Step_29: Kiem tra tai khoan dich hien thi");
	verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Tài khoản đích"), TransferMoneyInVCB_Data.InputData.ACCOUNT2);
	
	log.info("TC_01_Step_30: Kiem tra noi dung hien thi");
	verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Nội dung"), TransferMoneyInVCB_Data.InputData.NOTE);
	
	log.info("TC_01_Step_31: Click thuc hien giao dich moi");
	transferInVCB.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

	log.info("TC_01_Step_32: Click tai khoan nguon");
	transferInVCB.clickToDynamicDropDown(driver,"Tài khoản nguồn");
	
	log.info("TC_01_Step_33: Chon tai ngoan chuyen");
	transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT1);
	
	log.info("TC_01_Step_34: Lay so du tai khoan chuyen");
	String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
	long afterBalanceAmountOfAccount1 = convertMoneyToLong(afterBalanceOfAccount1,"VND");
	
	log.info("TC_01_Step_35: Kiem tra so du tai khoan chuyen sau khi thuc hien giao dich");
	long transferMoney = convertMoneyToLong(TransferMoneyInVCB_Data.InputData.MONEY,"VND");
	verifyEquals(beforeBalanceAmountOfAccount1 - transferMoney - newTransferFee, afterBalanceAmountOfAccount1);

	log.info("TC_01_Step_36: Click tai khoan nguon");
	transferInVCB.clickToDynamicDropDown(driver,"Tài khoản nguồn");
	
	log.info("TC_01_Step_37: Chon tai khoan nguoi nhan");
	transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT2);

	
	log.info("TC_01_Step_38: Lay so du kha dung tai khoan nhan");
	String afterBalanceOfAccount2 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
	
	log.info("TC_01_Step_39: Kiem tra so du tai khoan nhan sau khi nhan tien");
	long afterBalanceAmountOfAccount2 = convertMoneyToLong(afterBalanceOfAccount2,"VND");
	verifyEquals(beforeBalanceAmountOfAccount2 + transferMoney, afterBalanceAmountOfAccount2);

}
//@Test
public void TC_14_KiemTraChiTietGiaoDichChuyenTienNgayCoPhiGiaoDichNguoiChuyenTraVNDVaXacThucBangMatKhau() {
	log.info("TC_02_Step_01 : Click  nut Back");
	transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");
	
	log.info("TC_02_Step_02: Click vao More Icon");
	homePage = PageFactoryManager.getHomePageObject(driver);
	homePage.clickToDynamicBottomMenu(driver, "com.VCB:id/menu_5");
	
	log.info("TC_02_Step_03: Click Bao Cao Dao Dich");
	transReport = PageFactoryManager.getTransactionReportPageObject(driver);
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");
	
	log.info("TC_02_Step_04: Click Tat Ca Cac Loai Giao Dich");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");
	
	log.info("TC_02_Step_05: Chon Chuyen Tien Trong VCB");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền trong Vietcombank");
	
	log.info("TC_02_Step_06: Click Chon Tai Khoan");
	transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");
	
	log.info("TC_02_Step_07: Chon tai Khoan chuyen");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT1);
	
	log.info("TC_02_Step_08: CLick Tim Kiem");
	transReport.clickToDynamicButton(driver, "Tìm kiếm");

	log.info("TC_02_Step_09: Kiem tra ngay tao giao dich hien thi");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));
	
	log.info("TC_02_Step_10: Kiem tra thoi gian tao dao dich");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));
	
	log.info("TC_02_Step_11: Kiem tra noi dung hien thi");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputData.NOTE));
	
	log.info("TC_02_Step_12: Kiem tra so tien chuyen hien thi");
	verifyEquals(transReport.getTextInDynamicTransaction(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(TransferMoneyInVCB_Data.InputData.MONEY) + " VND"));
	
	log.info("TC_02_Step_13: Click vao giao dich");
	transReport.clickToDynamicTransaction(driver, "0", "com.VCB:id/tvDate");
	
	log.info("TC_02_Step_14: Kiem tra ngay giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").contains(transferTime.split(" ")[0]));
	
	log.info("TC_02_Step_15: Kiem tra thoi gian tao giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));
	
	log.info("TC_02_Step_16: Kiem tra thoi gian tao giao dich hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Số lệnh giao dịch"), transactionNumber);
	
	log.info("TC_02_Step_17: Kiem tra so tai khoan trich no");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản/thẻ trích nợ"), TransferMoneyInVCB_Data.InputData.ACCOUNT1);
	
	log.info("TC_02_Step_18: Kiem tra so tai khoan ghi co");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản ghi có"), TransferMoneyInVCB_Data.InputData.ACCOUNT2);
	
	log.info("TC_02_Step_19: Kiem tra so tien giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Số tiền giao dịch").contains( addCommasToLong(TransferMoneyInVCB_Data.InputData.MONEY) + " VND"));
	
	log.info("TC_02_Step_20: Kiem tra ten nguoi huong hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tên người hưởng"), TransferMoneyInVCB_Data.InputData.RECEIVER_NAME);
	
	log.info("TC_02_Step_21: Kiem tra phi giao dich hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Phí giao dịch"), TransferMoneyInVCB_Data.InputData.COST[0]);
	
	log.info("TC_02_Step_22: Kiem tra so tien phi hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Số tiền phí"), TransferMoneyInVCB_Data.InputData.PAYMENT_BY_PASSWORD_FEE + " VND");
	
	log.info("TC_02_Step_23: Kiem tra loai giao dich");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Loại giao dịch"), TransferMoneyInVCB_Data.InputData.TRANSFER_TYPE);
	
	log.info("TC_02_Step_24: Kiem Tra noi dung giao dich");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Nội dung giao dịch").contains(TransferMoneyInVCB_Data.InputData.NOTE));

	log.info("TC_02_Step_25: Click Back icon");
	transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");
	
	log.info("TC_02_Step_26: Chon Tai Khoan");
	transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");
	
	log.info("TC_02_Step_27: Click chon tai khoan nhan");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT2);

	log.info("TC_02_Step_28: Click Tim Kiem");
	transReport.clickToDynamicButton(driver, "Tìm kiếm");

	log.info("TC_02_Step_29: Kiem tra ngay tao giao dich hien thi");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));
	
	log.info("TC_02_Step_20: Kiem tra thoi gian tao giao dich hiển thị");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));
	
	log.info("TC_02_Step_21: Kiem tra noi dung giao dich hien thi");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputData.NOTE));
	
	log.info("TC_02_Step_22: Kiem tra so tien chuyen hien thi");
	verifyEquals(transReport.getTextInDynamicTransaction(driver, "1", "com.VCB:id/tvMoney"), ("+ " + addCommasToLong(TransferMoneyInVCB_Data.InputData.MONEY) + " VND"));
	
	log.info("TC_02_Step_23: Click vao bao bao giao dich chi tiet");
	transReport.clickToDynamicTransaction(driver, "0", "com.VCB:id/tvDate");
	
	log.info("TC_02_Step_24: Kiem tra ngay tao giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").contains(transferTime.split(" ")[0]));
	
	log.info("TC_02_Step_25: Kiem tra thoi gian tao giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));
	
	log.info("TC_02_Step_26: Kiem tra so giao dich hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Số lệnh giao dịch"), transactionNumber);
	
	log.info("TC_02_Step_27: Kiem tra tai khoan trich no hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản/thẻ trích nợ"), TransferMoneyInVCB_Data.InputData.ACCOUNT1);
	
	log.info("TC_02_Step_28: Kiem tra tai khoan ghi co hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản ghi có"), TransferMoneyInVCB_Data.InputData.ACCOUNT2);
	
	log.info("TC_02_Step_29: Kiem tra so tien giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Số tiền giao dịch").contains(addCommasToLong(TransferMoneyInVCB_Data.InputData.MONEY) + " VND"));
	
	log.info("TC_02_Step_30: Kiem tra ten nguoi huong hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tên người hưởng"), TransferMoneyInVCB_Data.InputData.RECEIVER_NAME);
	
	log.info("TC_02_Step_31: Kiem tra phi giao dich hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Phí giao dịch"), TransferMoneyInVCB_Data.InputData.COST[0]);
	
	log.info("TC_02_Step_32: Kiem tra so tien phi hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Số tiền phí"), TransferMoneyInVCB_Data.InputData.PAYMENT_BY_PASSWORD_FEE + " VND");
	
	log.info("TC_02_Step_33: Kiem tra noi dung giao dich hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Loại giao dịch"), TransferMoneyInVCB_Data.InputData.TRANSFER_TYPE);
	
	log.info("TC_02_Step_34: Kiem tra loaigiao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Nội dung giao dịch").contains(TransferMoneyInVCB_Data.InputData.NOTE));

	log.info("TC_02_Step_35: Click quay lai");
	transferInVCB.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");
	
	log.info("TC_02_Step_36: Click quay lai");
	transferInVCB.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");
	
	log.info("TC_02_Step_37: Click quay lai");
	transferInVCB.navigateBack(driver);
}
//@Test
public void TC_15_ChuyenTienNgayCoPhiGiaoDichNguoiNhanTraVNDVaXacThucBangMatKhau() {
	
	
	log.info("TC_03_Step_01: Click Chuyen tien trong VCB");
	homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");
	
	log.info("TC_03_Step_02:Click tai khoan nguon");
	transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
	transferInVCB.clickToDynamicDropDown(driver,"Tài khoản nguồn");

	log.info("TC_03_Step_03: Chon tai khoan dich");
	transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT1);
	
	log.info("TC_03_Step_04: Lay so du tai khoan dich");
	String beforeBalanceOfAccount2 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
	long beforeBalanceAmountOfAccount2 = convertMoneyToLong(beforeBalanceOfAccount2,"VND");
	
	log.info("TC_03_Step_05:Click tai khoan nguon");
	transferInVCB.clickToDynamicDropDown(driver,"Tài khoản nguồn");
	
	log.info("TC_03_Step_06: Chon tai khoan chuyen");
	transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT2);
	
	log.info("TC_03_Step_07: Lay so du tai khoan chuyen");
	String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
	long beforeBalanceAmountOfAccount1 = convertMoneyToLong(beforeBalanceOfAccount1,"VND");
	
	log.info("TC_03_Step_08: Nhap tai khoan nhan");
	transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT1, "Nhập/chọn tài khoản nhận VND");
	
	log.info("TC_03_Step_09: Nhap so tien chuyen");
	transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputData.MONEY, "Số tiền");
	
	log.info("TC_03_Step_10: Click phi giao dich nguoi chuyen tra");
	transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
	
	log.info("TC_03_Step_11: Chon nguoi nhan tra");
	transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Người nhận trả");
	
	log.info("TC_03_Step_12: Nhap noi dung");
	transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputData.NOTE, "Nội dung");
	
	log.info("TC_03_Step_13: Click tiep tuc");
	transferInVCB.clickToDynamicButton(driver, "Tiếp tục");
	
	log.info("TC_03_Step_14:  Kiem tra hinh thuc chuyen tien hien thi");
	verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Hình thức chuyển tiền"), TransferMoneyInVCB_Data.InputData.OPTION_TRANSFER[0]);
	
	log.info("TC_03_Step_15: Kiem tra tai khoan nguon hien thi");
	verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Tài khoản nguồn"), TransferMoneyInVCB_Data.InputData.ACCOUNT2);
	
	log.info("TC_03_Step_16: Kiem tra tai khoan dich hien thi");
	verifyTrue(transferInVCB.getDynamicTextInTextView(driver, "Tài khoản đích / VND").contains(TransferMoneyInVCB_Data.InputData.ACCOUNT1));
	
	log.info("TC_03_Step_17: Kiem tra so tien chuyen hien thi");
	verifyTrue(transferInVCB.getDynamicTextInTextView(driver, "Số tiền (VND)").contains(addCommasToLong(TransferMoneyInVCB_Data.InputData.MONEY)));
	
	log.info("TC_03_Step_19: Kiem tra nguoi nhan tra");
	verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.InputData.COST[1]));
	

	log.info("TC_03_Step_21:Kiem tra noi dung hien thi");		
	verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Nội dung"), TransferMoneyInVCB_Data.InputData.NOTE);
	
	log.info("TC_03_Step_22: Chon Phuong thuc xac thuc ");
	transferInVCB.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");
	
	log.info("TC_03_Step_23: Chon Mat khau dang nhap");
	transferInVCB.clickToDynamicButtonLinkOrLinkText(driver,"Mật khẩu đăng nhập");
	
	log.info("TC_03_Step_18: Kiem tra so tien phi hien thi");
	verifyTrue(transferInVCB.getDynamicTextInTextView(driver, "Số tiền phí").contains(TransferMoneyInVCB_Data.InputData.PAYMENT_BY_PASSWORD_FEE+" VND"));
	
	log.info("TC_03_Step_20:  Lay so tien phi giao dich");
	transferFee = transferInVCB.getDynamicTextInTextView(driver, "Số tiền phí");
	long newTransferFee = convertMoneyToLong(transferFee,"VND");
	
	log.info("TC_03_Step_24: Click Tiep tuc");
	transferInVCB.clickToDynamicButton(driver, "Tiếp tục");
	
	log.info("TC_03_Step_25: Nhap Mat Khau");
	transferInVCB.inputToDynamicPopupPasswordInput(driver, LogIn_Data.Login_Account.NEW_PASSWORD, "Tiếp tục");
	
	log.info("TC_03_Step_26: Click tiep tuc");
	transferInVCB.clickToDynamicButton(driver, "Tiếp tục");
	
	log.info("TC_03_Step_27: Kiem  tra giao dich thanh cong");
	verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE));
	
	log.info("TC_03_Step_28: Lay thoi gian tao giao dich");
	transferTime = transferInVCB.getDynamicTransferTime(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE, "4");
	
	log.info("TC_03_Step_29: Lay ma giao dich");
	transactionNumber = transferInVCB.getDynamicTextInTextView(driver, "Mã giao dịch");
	
	log.info("TC_03_Step_30:  Kiem tra ten nguoi thu huong hien thi");
	verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Tên người thụ hưởng"), TransferMoneyInVCB_Data.InputData.RECEIVER_NAME);
	
	log.info("TC_03_Step_31: Kiem tra tai khoan dich hien thi");
	verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Tài khoản đích"), TransferMoneyInVCB_Data.InputData.ACCOUNT1);

	log.info("TC_03_Step_23: Kiem tra noi dung hien thi");
	verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Nội dung"), TransferMoneyInVCB_Data.InputData.NOTE);
	
	log.info("TC_03_Step_24: Click thuc hien giao dich moi");
	transferInVCB.clickToDynamicButton(driver, "Thực hiện giao dịch mới");
	
	log.info("TC_03_Step_25: Click tai khoan nguon");
	transferInVCB.clickToDynamicDropDown(driver,"Tài khoản nguồn");
	
	log.info("TC_03_Step_26: Chon tai ngoan chuyen");
	transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT2);
	
	log.info("TC_03_Step_27: Lay so du tai khoan chuyen");
	String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
	long afterBalanceAmountOfAccount1 = convertMoneyToLong(afterBalanceOfAccount1,"VND");
	
	log.info("TC_03_Step_28: Kiem tra so du tai khoan chuyen sau khi thuc hien giao dich");
	long transferMoney = convertMoneyToLong(TransferMoneyInVCB_Data.InputData.MONEY,"VND");
	verifyEquals(beforeBalanceAmountOfAccount1 - transferMoney, afterBalanceAmountOfAccount1);
	
	log.info("TC_03_Step_29:Click tai khoan nguon");
	transferInVCB.clickToDynamicDropDown(driver,"Tài khoản nguồn");
	
	log.info("TC_03_Step_30: Chon tai khoan nguoi nhan");
	transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT1);
	
	log.info("TC_03_Step_31:Lay so du kha dung tai khoan nhan");
	String afterBalanceOfAccount2 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
	long afterBalanceAmountOfAccount2 = convertMoneyToLong(afterBalanceOfAccount2,"VND");
	
	log.info("TC_03_Step_32: Kiem tra so du tai khoan nhan sau khi nhan tien");
	verifyEquals(beforeBalanceAmountOfAccount2 + transferMoney-newTransferFee , afterBalanceAmountOfAccount2);

}
//@Test
public void TC_16_KiemTraChiTietGiaoDichChuyenTienNgayCoPhiGiaoDichNguoiNhanTraVNDVaXacThucMatKhau() {
	
	log.info("TC_04_Step_01:Click  nut Back");
	transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");
	
	log.info("TC_04_Step_02:Click vao More Icon");
	homePage = PageFactoryManager.getHomePageObject(driver);
	homePage.clickToDynamicBottomMenu(driver, "com.VCB:id/menu_5");
	
	log.info("TC_04_Step_03:Click Bao Cao Dao Dich");
	transReport = PageFactoryManager.getTransactionReportPageObject(driver);
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");
	
	log.info("TC_04_Step_04: Click Tat Ca Cac Loai Giao Dich");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");
	
	log.info("TC_04_Step_05: Chon Chuyen Tien Trong VCB");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền trong Vietcombank");
	
	log.info("TC_04_Step_06: Click Chon Tai Khoan");
	transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");
	
	log.info("TC_04_Step_07: Chon tai Khoan trich no");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT2);
	
	log.info("TC_04_Step_08: CLick Tim Kiem");
	transReport.clickToDynamicButton(driver, "Tìm kiếm");

	log.info("TC_04_Step_09: Kiem tra ngay tao giao dich hien thi");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));
	
	log.info("TC_04_Step_10: Kiem tra thoi gian tao dao dich");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));
	
	log.info("TC_04_Step_11: Kiem tra noi dung hien thi");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputData.NOTE));
	
	log.info("TC_04_Step_12: Kiem tra so tien chuyen hien thi");
	verifyEquals(transReport.getTextInDynamicTransaction(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(TransferMoneyInVCB_Data.InputData.MONEY) + " VND"));
	
	log.info("TC_04_Step_13: Click vao giao dich");
	transReport.clickToDynamicTransaction(driver, "0", "com.VCB:id/tvDate");
	
	log.info("TC_04_Step_14: Kiem tra ngay giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").contains(transferTime.split(" ")[0]));
	
	log.info("TC_04_Step_15: Kiem tra thoi gian tao giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));
	
	log.info("TC_04_Step_16: Kiem tra so lenh giao dich");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Số lệnh giao dịch"), transactionNumber);
	
	log.info("TC_04_Step_17: Kiem tra so tai khoan trich no");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản/thẻ trích nợ"), TransferMoneyInVCB_Data.InputData.ACCOUNT2);
	
	log.info("TC_04_Step_18: Kiem tra so tai khoan ghi co");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản ghi có"), TransferMoneyInVCB_Data.InputData.ACCOUNT1);
	
	log.info("TC_04_Step_19: Kiem tra so tien giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Số tiền giao dịch").contains(addCommasToLong(TransferMoneyInVCB_Data.InputData.MONEY) + " VND"));
	
	log.info("TC_04_Step_20: Kiem tra ten nguoi huong hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tên người hưởng"), TransferMoneyInVCB_Data.InputData.RECEIVER_NAME);
	
	log.info("TC_04_Step_21: Kiem tra phi giao dich hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Phí giao dịch"), TransferMoneyInVCB_Data.InputData.COST[1]);
	
	log.info("TC_04_Step_22: Kiem tra so tien phi hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Số tiền phí"), TransferMoneyInVCB_Data.InputData.PAYMENT_BY_PASSWORD_FEE + " VND");
	
	log.info("TC_04_Step_23: Kiem tra loai giao dich");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Loại giao dịch"), TransferMoneyInVCB_Data.InputData.TRANSFER_TYPE);
	
	log.info("TC_04_Step_24: Kiem Tra noi dung giao dich");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Nội dung giao dịch").contains(TransferMoneyInVCB_Data.InputData.NOTE));

	log.info("TC_04_Step_25: Click Back icon");
	transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");
	
	log.info("TC_04_Step_26: Chon Tai Khoan");
	transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");
	
	log.info("TC_04_Step_27: Click chon tai khoan nhan");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT1);
	
	log.info("TC_04_Step_28: Click Tim Kiem");
	transReport.clickToDynamicButton(driver, "Tìm kiếm");
	
	log.info("TC_04_Step_29: Kiem tra ngay tao giao dich hien thi");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));
	
	log.info("TC_04_Step_30: Kiem tra thoi gian tao giao dich hiển thị");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));
	
	log.info("TC_04_Step_31: Kiem tra noi dung giao dich hien thi");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputData.NOTE));
	
	log.info("TC_04_Step_32: Kiem tra so tien chuyen hien thi");
	verifyEquals(transReport.getTextInDynamicTransaction(driver, "1", "com.VCB:id/tvMoney"), ("+ " + addCommasToLong(TransferMoneyInVCB_Data.InputData.MONEY) + " VND"));
	
	log.info("TC_04_Step_33: Click vao bao bao giao dich chi tiet");
	transReport.clickToDynamicTransaction(driver, "0", "com.VCB:id/tvDate");
	
	log.info("TC_04_Step_34: Kiem tra ngay tao giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").contains(transferTime.split(" ")[0]));
	
	log.info("TC_04_Step_35: Kiem tra thoi gian tao giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));
	
	log.info("TC_04_Step_36: Kiem tra so giao dich hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Số lệnh giao dịch"), transactionNumber);
	
	log.info("TC_04_Step_37: Kiem tra tai khoan trich no hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản/thẻ trích nợ"), TransferMoneyInVCB_Data.InputData.ACCOUNT2);
	
	log.info("TC_04_Step_38: Kiem tra tai khoan ghi co hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản ghi có"), TransferMoneyInVCB_Data.InputData.ACCOUNT1);
	
	log.info("TC_04_Step_39: Kiem tra so tien giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Số tiền giao dịch").contains(addCommasToLong(TransferMoneyInVCB_Data.InputData.MONEY) + " VND"));
	
	log.info("TC_04_Step_40: Kiem tra ten nguoi huong hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tên người hưởng"), TransferMoneyInVCB_Data.InputData.RECEIVER_NAME);
	
	log.info("TC_04_Step_41: Kiem tra phi giao dich hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Phí giao dịch"), TransferMoneyInVCB_Data.InputData.COST[1]);
	
	log.info("TC_04_Step_42: Kiem tra so tien phi hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Số tiền phí"), TransferMoneyInVCB_Data.InputData.PAYMENT_BY_PASSWORD_FEE + " VND");
	
	log.info("TC_04_Step_43: Kiem tra loai giao dich hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Loại giao dịch"), TransferMoneyInVCB_Data.InputData.TRANSFER_TYPE);
	
	log.info("TC_04_Step_44: Kiem tra noi dung giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Nội dung giao dịch").contains(TransferMoneyInVCB_Data.InputData.NOTE));

	log.info("TC_04_Step_45: Click quay lai");
	transferInVCB.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");
	
	log.info("TC_04_Step_46: Click quay lai");
	transferInVCB.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");
	
	log.info("TC_04_Step_47: Click quay lai");
	transferInVCB.navigateBack(driver);
}
@Test
public void TC_17_ChuyenTienNgayCoPhiGiaoDichNguoiChuyenTraUSDVaXacThucBangMatKhau() {
	
	log.info("TC05_Step 01: Click Chuyen tien trong VCB");
	homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");
	
	log.info("TC05_Step 02: Click chon tai khoan nguon");
	transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
	transferInVCB.clickToDynamicDropDown(driver,"Tài khoản nguồn");

	log.info("TC05_Step 03: Chon account 1");
	transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT1);
	
	log.info("TC05_Step 04: Lay so du kha dung tai khoan 1");
	String beforeBalanceOfAccount2 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
	double beforeBalanceAmountOfAccount2 = convertMoneyToDouble(beforeBalanceOfAccount2,"VND");
	
	log.info("TC05_Step : Click tai khoan nguon");
	transferInVCB.clickToDynamicDropDown(driver,"Tài khoản nguồn");
	
	log.info("TC05_Step : Chon USD account");
	transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.USD_ACCOUNT);
	
	log.info("TC05_Step : Lay so du tai khoan USD");
	String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
	double beforeBalanceAmountOfAccount1 = convertMoneyToDouble(beforeBalanceOfAccount1,"USD");
	
	log.info("TC05_Step : Nhap tai khoan nhan");
	transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT1, "Nhập/chọn tài khoản nhận VND");
	
	log.info("TC05_Step : Nhap so tien can chuyen");
	transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputData.AMOUNT_OF_EUR_OR_USD_TRANSFER, "Số tiền");
	
	log.info("TC05_Step : Lay ti gia quy doi");
	String exchangeRate = transferInVCB.getDynamicTextInTextView(driver, "Tỷ giá quy đổi tham khảo");
	exchangeRateUSD = exchangeRate.split(" ~ ");
	rate = convertMoneyToDouble(exchangeRateUSD[1], "VND");
	
	log.info("TC05_Step : Chon phuong thuc xac thuc");
	transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
	
	log.info("TC05_Step : Chon phi giao dich nguoi chuyen tra");
	transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Người chuyển trả");
	
	log.info("TC05_Step :  Nhap noi dung");
	transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputData.NOTE, "Nội dung");
	
	log.info("TC05_Step : Click tiep tuc");
	transferInVCB.clickToDynamicButton(driver, "Tiếp tục");
	
	log.info("TC05_Step : Kiem tra hinh thuc chuyen tien hien thi");
	verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Hình thức chuyển tiền"), TransferMoneyInVCB_Data.InputData.OPTION_TRANSFER[0]);
	
	log.info("TC05_Step : Kiem tra tai khoan nguon hien thi");
	verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Tài khoản nguồn"), TransferMoneyInVCB_Data.InputData.USD_ACCOUNT);
	
	log.info("TC05_Step : Kiem tra tai khoan dich hien thi");
	verifyTrue(transferInVCB.getDynamicTextInTextView(driver, "Tài khoản đích / VND").contains(TransferMoneyInVCB_Data.InputData.ACCOUNT1));
	
	log.info("TC05_Step : Kiem tra so tien chuyen hien thi");
	verifyTrue(transferInVCB.getDynamicTextInTextView(driver, "Số tiền (USD)").contains(addCommasToDouble(TransferMoneyInVCB_Data.InputData.AMOUNT_OF_EUR_OR_USD_TRANSFER)+"USD"));
	
	log.info("TC05_Step : Kiem tra Nguoi chuyen tra hien thi");
	verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.InputData.COST[0]));

	log.info("TC05_Step : Kiem tra noi dung hien thi");
	verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Nội dung"), TransferMoneyInVCB_Data.InputData.NOTE);
	
	log.info("TC05_Step : Chon phuong thuc xac thuc");
	transferInVCB.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");
	
	log.info("TC05_Step : Chon SMS OTP");
	transferInVCB.clickToDynamicButtonLinkOrLinkText(driver,"Mật khẩu đăng nhập");
	
	log.info("TC05_Step : Kiem tra so tien phi hien thi"); 
	verifyTrue(transferInVCB.getDynamicTextInTextView(driver, "Số tiền phí").contains(TransferMoneyInVCB_Data.InputData.USD_PAYMENT_BY_OTP_FEE+" USD"));
	
	log.info("TC05_Step : Lay tien phi chuyen ");
	transferFee = transferInVCB.getDynamicTextInTextView(driver, "Số tiền phí");
	double newTransferFee = convertMoneyToDouble(transferFee,"USD");
	
	log.info("TC05_Step : Click tiep tuc");
	transferInVCB.clickToDynamicButton(driver, "Tiếp tục");
	
	log.info("TC05_Step : Dien OTP");
	transferInVCB.inputToDynamicPopupPasswordInput(driver, LogIn_Data.Login_Account.NEW_PASSWORD, "Tiếp tục");
	
	log.info("TC05_Step : Click tiep tuc");
	transferInVCB.clickToDynamicButton(driver, "Tiếp tục");
	verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE));
	
	log.info("TC05_Step : Lay thoi gian tao");
	transferTime = transferInVCB.getDynamicTransferTime(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE, "4");
	
	log.info("TC05_Step : Kiem tra so tien chuyen hien thi");
	verifyEquals(transferInVCB.getDynamicTransferTime(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE, "3"),  addCommasToDouble(TransferMoneyInVCB_Data.InputData.AMOUNT_OF_EUR_OR_USD_TRANSFER)+" USD");
	
	log.info("TC05_Step : Kiem tra ten nguoi thu huong hien thi");
	verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Tên người thụ hưởng"), TransferMoneyInVCB_Data.InputData.RECEIVER_NAME);
	
	log.info("TC05_Step : Kiem tra tai khoan dich hien thi");
	verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Tài khoản đích"), TransferMoneyInVCB_Data.InputData.ACCOUNT1);

	log.info("TC05_Step : Lay ma giao dich");
	transactionNumber = transferInVCB.getDynamicTextInTextView(driver, "Mã giao dịch");
	
	log.info("TC05_Step : Kiem tra noi dung hien thi");
	verifyEquals(transferInVCB.getDynamicTextInTextView(driver, "Nội dung"), TransferMoneyInVCB_Data.InputData.NOTE);
	
	log.info("TC05_Step : Click thuc hien giao dich moi");
	transferInVCB.clickToDynamicButton(driver, "Thực hiện giao dịch mới");
	
	log.info("TC05_Step : Click tai khoan nguon");
	transferInVCB.clickToDynamicDropDown(driver,"Tài khoản nguồn");
	
	log.info("TC05_Step :Click USD account");
	transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.USD_ACCOUNT);
	
	log.info("TC05_Step : Lay so du kha dung tai khoan USD");
	String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
	double afterBalanceAmountOfAccount1 = convertMoneyToDouble(afterBalanceOfAccount1,"USD");
	
	log.info("TC05_Step : Convert so tien chuyen");
	double transferMoney = convertMoneyToDouble(TransferMoneyInVCB_Data.InputData.AMOUNT_OF_EUR_OR_USD_TRANSFER,"USD");
	
	log.info("TC05_Step : Kiem tra so du tai khoan USD sau khi chuyen tien");
	verifyEquals(beforeBalanceAmountOfAccount1 - transferMoney- newTransferFee, afterBalanceAmountOfAccount1);
	
	log.info("TC05_Step : Kiem tra tai khoan nguon");
	transferInVCB.clickToDynamicDropDown(driver,"Tài khoản nguồn");
	
	log.info("TC05_Step : Click chon tai khoan chuyen den");
	transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT1);
	
	log.info("TC05_Step : Lay so du kha dung tai khoan chuyen den");
	String afterBalanceOfAccount2 = transferInVCB.getDynamicTextInTextView(driver, "Số dư khả dụng");
	double afterBalanceAmountOfAccount2 = convertMoneyToDouble(afterBalanceOfAccount2,"VND");
	
	log.info("TC05_Step : Kiem tra so du tai khoan duoc chuyen den");
	verifyEquals(beforeBalanceAmountOfAccount2 + transferMoney*rate , afterBalanceAmountOfAccount2);

}
@Test
public void TC_18_KiemTraChiTietGiaoDichChuyenTienNgayCoPhiGiaoDichNguoiChuyenTraUSDVaXacThucBangMatKhau() throws InterruptedException {
	
	log.info("TC06_Step 01 : Click  nut Back");
	transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");
	
	log.info("TC06_Step 02: Click vao More Icon");
	homePage = PageFactoryManager.getHomePageObject(driver);
	homePage.clickToDynamicBottomMenu(driver, "com.VCB:id/menu_5");
	
	log.info("TC06_Step : Click Bao Cao Dao Dich");
	transReport = PageFactoryManager.getTransactionReportPageObject(driver);
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");
	
	log.info("TC06_Step : Click Tat Ca Cac Loai Giao Dich");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");
	
	log.info("TC06_Step :Chon Chuyen Tien Trong VCB");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền trong Vietcombank");
	
	log.info("TC06_Step : Click Chon Tai Khoan");
	transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");
	
	log.info("TC06_Step : Chon 1 tai Khoan");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.USD_ACCOUNT);
	
	log.info("TC06_Step : CLick Tim Kiem");
	transReport.clickToDynamicButton(driver, "Tìm kiếm");
	
	log.info("TC06_Step : Kiem tra ngay tao giao dich hien thi");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));
	
	log.info("TC06_Step : Kiem tra thoi gian tao dao dich");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));
	
	log.info("TC06_Step : Kiem tra noi dung hien thi");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputData.NOTE));
	
	log.info("TC06_Step : Kiem tra so tien chuyen hien thi");
	verifyEquals(transReport.getTextInDynamicTransaction(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToDouble(TransferMoneyInVCB_Data.InputData.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " USD"));
	
	log.info("TC06_Step : Click vao giao dich");
	transReport.clickToDynamicTransaction(driver, "0", "com.VCB:id/tvDate");
	
	log.info("TC06_Step : Kiem tra ngay giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").contains(transferTime.split(" ")[0]));
	
	log.info("TC06_Step : Kiem tra thoi gian tao giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));
	
	log.info("TC06_Step :Kiem tra so lenh giao dich");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Số lệnh giao dịch"), transactionNumber);
	
	log.info("TC06_Step : Kiem tra so tai khoan trich no");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản/thẻ trích nợ"), TransferMoneyInVCB_Data.InputData.USD_ACCOUNT);
	
	log.info("TC06_Step : Kiem tra so tai khoan ghi co");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản ghi có"), TransferMoneyInVCB_Data.InputData.ACCOUNT1);
	
	log.info("TC06_Step : Kiem tra so tien giao dich hien thi ");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Số tiền giao dịch").equals(addCommasToDouble(TransferMoneyInVCB_Data.InputData.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " USD"));
	
	
	log.info("TC06_Step : Kiem tra so tien quy doi hien thi ");
	String changedMoney = convertMoneyToLong(TransferMoneyInVCB_Data.InputData.AMOUNT_OF_EUR_OR_USD_TRANSFER, "USD")*convertMoneyToLong(exchangeRateUSD[1], "VND") +"";
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Số tiền quy đổi").contains(addCommasToLong(changedMoney) + " VND"));
	
	log.info("TC06_Step : Kiem tra ten nguoi huong hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tên người hưởng"), TransferMoneyInVCB_Data.InputData.RECEIVER_NAME);
	
	log.info("TC06_Step : Kiem tra phi giao dich hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Phí giao dịch"), TransferMoneyInVCB_Data.InputData.COST[0]);
	
	log.info("TC06_Step : Kiem tra so tien phi hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Số tiền phí"), TransferMoneyInVCB_Data.InputData.PAYMENT_BY_PASSWORD_FEE + " VND");
	
	log.info("TC06_Step : Kiem tra loai giao dich");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Loại giao dịch"), TransferMoneyInVCB_Data.InputData.TRANSFER_TYPE);
	
	log.info("TC06_Step : Kiem Tra noi dung giao dich");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Nội dung giao dịch").contains(TransferMoneyInVCB_Data.InputData.NOTE));
	
	log.info("TC06_Step : Click Back icon");
	transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");
	
	log.info("TC06_Step : Chon Tai Khoan");
	transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");
	
	log.info("TC06_Step : Click chon tai khoan nhan");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputData.ACCOUNT1);
	
	log.info("TC06_Step : Click Tim Kiem");
	transReport.clickToDynamicButton(driver, "Tìm kiếm");

	log.info("TC06_Step : Kiem tra ngay tao giao dich hien thi");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));
	
	log.info("TC06_Step : Kiem tra thoi gian tao giao dich hiển thị");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));
	
	log.info("TC06_Step : Kiem tra noi dung giao dich hien thi");
	verifyTrue(transReport.getTextInDynamicTransaction(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputData.NOTE));
	
	log.info("TC06_Step : Kiem tra so tien chuyen hien thi");
	verifyEquals(transReport.getTextInDynamicTransaction(driver, "1", "com.VCB:id/tvMoney"), ("+ " + addCommasToLong(changedMoney) + " VND"));
	
	log.info("TC06_Step : Click vao bao bao giao dich chi tiet");
	transReport.clickToDynamicTransaction(driver, "0", "com.VCB:id/tvDate");
	
	log.info("TC06_Step : Kiem tra ngay tao giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").contains(transferTime.split(" ")[0]));
	
	log.info("TC06_Step : Kiem tra thoi gian tao giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));
	
	log.info("TC06_Step : Kiem tra so giao dich hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Số lệnh giao dịch"), transactionNumber);
	
	log.info("TC06_Step :Kiem tra tai khoan trich no hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản/thẻ trích nợ"), TransferMoneyInVCB_Data.InputData.USD_ACCOUNT);
	
	log.info("TC06_Step : Kiem tra tai khoan ghi co hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tài khoản ghi có"), TransferMoneyInVCB_Data.InputData.ACCOUNT1);
	
	log.info("TC06_Step : Kiem tra so tien giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Số tiền giao dịch").contains(addCommasToDouble(TransferMoneyInVCB_Data.InputData.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " USD"));
	
	log.info("TC06_Step : Kiem tra so tien quy doi hien thi ");

	verifyTrue(transReport.getDynamicTextInTextView(driver, "Số tiền quy đổi").contains(addCommasToLong(changedMoney) + " VND"));
	
	log.info("TC06_Step : Kiem tra ten nguoi huong hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Tên người hưởng"), TransferMoneyInVCB_Data.InputData.RECEIVER_NAME);
	
	log.info("TC06_Step : Kiem tra phi giao dich hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Phí giao dịch"), TransferMoneyInVCB_Data.InputData.COST[0]);
	
	log.info("TC06_Step :Kiem tra so tien phi hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Số tiền phí"), TransferMoneyInVCB_Data.InputData.PAYMENT_BY_PASSWORD_FEE + " VND");
	
	log.info("TC06_Step :Kiem tra noi dung giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTextView(driver, "Nội dung giao dịch").contains(TransferMoneyInVCB_Data.InputData.NOTE));

	log.info("TC06_Step : Kiem tra loaigiao dich hien thi");
	verifyEquals(transReport.getDynamicTextInTextView(driver, "Loại giao dịch"), TransferMoneyInVCB_Data.InputData.TRANSFER_TYPE);
	
	log.info("TC06_Step : Click quay lai");
	transferInVCB.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");
	
	log.info("TC06_Step : Click quay lai");
	transferInVCB.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");
	
	log.info("TC06_Step : Click quay lai");
	transferInVCB.navigateBack(driver);
}
	



@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
