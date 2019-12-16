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
import pageObjects.TransferMoneyInVcbPageObject;
import pageObjects.TransferMoneyStatusPageObject;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransferMoneyInVCB_Data;
import vietcombank_test_data.TransferMoneyStatus_Data;

public class Transfer_Money_In_Future_And_Report extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferMoneyInVcbPageObject transferInVCB;
	private TransferMoneyStatusPageObject transStatus;
	private String transferFee;
	private String transferTime;
	private String transactionNumber;
	double rate;
	String[] exchangeRateUSD;
	String today = getCurrentDay() + "/" + getCurrenMonth() + "/" + getCurrentYear();
	String tommorrowDate = getForwardDate(1);

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

		log.info("Before class_Step_01: Click Allow Button");
		login.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");

		log.info("Before class_Step_10: Scroll den trang thai lenh chuyen tien");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.scrollToText(driver, "Trạng thái lệnh chuyển tiền");

	}

	@Test
	public void TC_01_ChuyenTienTuongLaiCoPhiGiaoDichNguoiChuyenTraVNDVaXacThucBangOTP() {

		log.info("TC_01_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);

		log.info("TC_01_Step_03: Chon chuyển tiền ngay");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");

		log.info("TC_01_Step_03: Chon chuyển tiền ngay");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày tương lai");

		log.info("TC_01_Step_02:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_01_Step_03: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT2);

		log.info("TC_01_Step_04: Lay so du tai khoan dich");
		String beforeBalanceOfAccount2 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		long beforeBalanceAmountOfAccount2 = convertMoneyToLong(beforeBalanceOfAccount2, "VND");

		log.info("TC_01_Step_05:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_01_Step_06: Chon tai khoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_01_Step_07: Lay so du tai khoan chuyen");
		String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		long beforeBalanceAmountOfAccount1 = convertMoneyToLong(beforeBalanceOfAccount1, "VND");

		log.info("TC_01_Step_08: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT2, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_01_Step_14: Kiem tra Ngay hieu luc mac dinh");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, tommorrowDate));

		log.info("TC_01_Step_09: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInFuture.TRANSFER_AMOUNT, "Số tiền");

		log.info("TC_01_Step_10: Nhap noi dung");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Nội dung");

		log.info("TC_01_Step_11: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_12: Kiem tra hinh thuc chuyen tien hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Hình thức chuyển tiền"), TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[2]);

		log.info("TC_01_Step_13: Kiem tra tai khoan nguon hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_01_Step_14: Kiem tra tai khoan dich hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tài khoản đích/ VND").contains(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT2));

		log.info("TC_01_Step_15: Kiem tra so tien chuyen hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Số tiền"), addCommasToLong(TransferMoneyInVCB_Data.InputDataInFuture.TRANSFER_AMOUNT) + " VND");

		log.info("TC_01_Step_14: Kiem tra Ngay hieu luc hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Ngày hiệu lực"), tommorrowDate);

		log.info("TC_01_Step_17: Kiem tra phuong thuc tra phi");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.InputDataInVCB.COST[0]));

		log.info("TC_01_Step_19: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_01_Step_20: Chon Phuong thuc nhap");
		transferInVCB.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");

		log.info("TC_01_Step_21: Chon SMS OTP");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_01_Step_16: Kiem tra so tien phi hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, "Số tiền phí").contains(TransferMoneyInVCB_Data.InputDataInFuture.OTP_FEE + " VND"));

		log.info("TC_01_Step_22: Click Tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_23: Nhap OTP");
		transferInVCB.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		log.info("TC_01_Step_24: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_25: Kiem  tra giao dich thanh cong");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.SUCESSFULL_CREATED_ORDER));

		log.info("TC_01_Step_26: Kiem tra so tien chuyen");
		verifyEquals(transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.SUCESSFULL_CREATED_ORDER, "3"), addCommasToLong(TransferMoneyInVCB_Data.InputDataInFuture.TRANSFER_AMOUNT) + " VND");

		log.info("TC_01_Step_26: Lay thoi gian tao giao dich");
		transferTime = transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.SUCESSFULL_CREATED_ORDER, "4");

		log.info("TC_01_Step_27: Lay ma giao dich");
		transactionNumber = transferInVCB.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC_01_Step_28: Kiem tra ten nguoi thu huong hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tên người thụ hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);

		log.info("TC_01_Step_29: Kiem tra tai khoan dich hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tài khoản đích"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT2);

		log.info("TC_01_Step_30: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_01_Step_31: Click thuc hien giao dich moi");
		transferInVCB.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_01_Step_32: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_01_Step_33: Chon tai ngoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_01_Step_34: Lay so du tai khoan chuyen");
		String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		long afterBalanceAmountOfAccount1 = convertMoneyToLong(afterBalanceOfAccount1, "VND");

		log.info("TC_01_Step_35: Kiem tra so du tai khoan chuyen sau khi thuc hien giao dich");
		verifyEquals(beforeBalanceAmountOfAccount1, afterBalanceAmountOfAccount1);

		log.info("TC_01_Step_36: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_01_Step_37: Chon tai khoan nguoi nhan");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT2);

		log.info("TC_01_Step_38: Lay so du kha dung tai khoan nhan");
		String afterBalanceOfAccount2 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");

		log.info("TC_01_Step_39: Kiem tra so du tai khoan nhan sau khi nhan tien");
		long afterBalanceAmountOfAccount2 = convertMoneyToLong(afterBalanceOfAccount2, "VND");
		verifyEquals(beforeBalanceAmountOfAccount2, afterBalanceAmountOfAccount2);
	}

	@Test
	public void TC_02_KiemTraTrạngThaiLenhChuyenTienCuaGiaoDichTuongLaiXacThucOTP() {
		log.info("TC_02_Step_01 : Click  nut Back");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");

		log.info("TC_02_Step_02: Click vao More Icon");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicIcon(driver, "Trạng thái lệnh chuyển tiền");

		log.info("TC_02_Step_04: Click Tat Ca Cac Loai Giao Dich");
		transStatus = PageFactoryManager.getTransferMoneyStatusPageObject(driver);
		transStatus.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");

		log.info("TC_02_Step_05: Chon Chuyen Tien Trong VCB");
		transStatus.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày tương lai");

		log.info("TC_02_Step_12: Kiem tra from date hien thi");
		verifyEquals(transStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate"), getBackwardDate(29));

		log.info("TC_02_Step_12: Kiem tra todate hien thi");
		verifyEquals(transStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate"), today);

		log.info("TC_02_Step_12: Kiem tra Note hien thi");
		verifyTrue(transStatus.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyStatus_Data.Output.NOTE));

		log.info("TC_02_Step_08: CLick Tim Kiem");
		transStatus.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC06_Step 09: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));

		log.info("TC06_Step 10: Kiem tra thoi gian tao dao dich");
		verifyTrue(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC06_Step 10: Kiem tra nguoi nhan");
		verifyEquals(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvContent"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);

		log.info("TC06_Step 10: Kiem tra trang thai giao dich");
		verifyEquals(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvStatus"), TransferMoneyStatus_Data.Output.WAITING_STATUS);

		log.info("TC06_Step 10: Kiem tra so tien giao dich");
		verifyEquals(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "1", "com.VCB:id/tvMoney"), addCommasToLong(TransferMoneyInVCB_Data.InputDataInFuture.TRANSFER_AMOUNT) + " VND");

		log.info("TC06_Step 10: click vao giao dich");
		transStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvDate");

		log.info("TC06_Step 14: Kiem tra ngay giao dich hien thi");
		verifyTrue(transStatus.getDynamicTextInTransactionDetail(driver, "Ngày lập lệnh").contains(transferTime.split(" ")[0]));

		log.info("TC06_Step 15: Kiem tra thoi gian tao giao dich hien thi");
		verifyTrue(transStatus.getDynamicTextInTransactionDetail(driver, "Ngày lập lệnh").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC06_Step 15: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, "Người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);

		log.info("TC06_Step 15: Kiem tra so tien thi");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, "Số tiền"), addCommasToLong(TransferMoneyInVCB_Data.InputDataInFuture.TRANSFER_AMOUNT) + " VND");

		log.info("TC06_Step 15: Kiem tra ngay hieu luc");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, "Ngày hiệu lực"), tommorrowDate);

		log.info("TC06_Step 15: Kiem tra ghi chú");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, "Ghi chú"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC06_Step 15: Kiem tra trang thai hieu luc");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, "Trạng thái"), TransferMoneyStatus_Data.Output.WAITING_STATUS);

		log.info("TC_02_Step_35: Click quay lai");
		transStatus.clickToDynamicBackIcon(driver, "Chi tiết lệnh chuyển tiền");

		log.info("TC_02_Step_36: Click quay lai");
		transStatus.clickToDynamicBackIcon(driver, "Trạng thái lệnh chuyển tiền");

	}

	@Test
	public void TC_03_ChuyenTienTuongLaiCoPhiGiaoDichNguoiNhanTraVNDVaXacThucBangOTP() {

		log.info("TC_01_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);

		log.info("TC_01_Step_03: Chon chuyển tiền ngay");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");

		log.info("TC_01_Step_03: Chon chuyển tiền ngay");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày tương lai");

		log.info("TC_01_Step_02:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_01_Step_03: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_01_Step_04: Lay so du tai khoan dich");
		String beforeBalanceOfAccount2 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		long beforeBalanceAmountOfAccount2 = convertMoneyToLong(beforeBalanceOfAccount2, "VND");

		log.info("TC_01_Step_05:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_01_Step_06: Chon tai khoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT2);

		log.info("TC_01_Step_07: Lay so du tai khoan chuyen");
		String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		long beforeBalanceAmountOfAccount1 = convertMoneyToLong(beforeBalanceOfAccount1, "VND");

		log.info("TC_01_Step_08: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_01_Step_14: Kiem tra Ngay hieu luc mac dinh");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, tommorrowDate));

		log.info("TC_01_Step_09: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInFuture.TRANSFER_AMOUNT, "Số tiền");

		log.info("TC_01_Step_10: Nhap noi dung");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Nội dung");

		log.info("TC_01_Step_06: Click nguoi chuyen tra");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");

		log.info("TC_01_Step_06: Click nguoi chuyen tra");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Người nhận trả");

		log.info("TC_01_Step_11: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_12: Kiem tra hinh thuc chuyen tien hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Hình thức chuyển tiền"), TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[2]);

		log.info("TC_01_Step_13: Kiem tra tai khoan nguon hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT2);

		log.info("TC_01_Step_14: Kiem tra tai khoan dich hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tài khoản đích/ VND").contains(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1));

		log.info("TC_01_Step_15: Kiem tra so tien chuyen hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Số tiền"), addCommasToLong(TransferMoneyInVCB_Data.InputDataInFuture.TRANSFER_AMOUNT) + " VND");

		log.info("TC_01_Step_14: Kiem tra Ngay hieu luc hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Ngày hiệu lực"), tommorrowDate);

		log.info("TC_01_Step_17: Kiem tra phuong thuc tra phi");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.InputDataInVCB.COST[1]));

		log.info("TC_01_Step_19: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_01_Step_20: Chon Phuong thuc nhap");
		transferInVCB.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");

		log.info("TC_01_Step_21: Chon SMS OTP");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_01_Step_16: Kiem tra so tien phi hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, "Số tiền phí").contains(TransferMoneyInVCB_Data.InputDataInFuture.OTP_FEE + " VND"));

		log.info("TC_01_Step_22: Click Tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_23: Nhap OTP");
		transferInVCB.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		log.info("TC_01_Step_24: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_25: Kiem  tra giao dich thanh cong");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.SUCESSFULL_CREATED_ORDER));

		log.info("TC_01_Step_26: Kiem tra so tien chuyen");
		verifyEquals(transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.SUCESSFULL_CREATED_ORDER, "3"), addCommasToLong(TransferMoneyInVCB_Data.InputDataInFuture.TRANSFER_AMOUNT) + " VND");

		log.info("TC_01_Step_26: Lay thoi gian tao giao dich");
		transferTime = transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.SUCESSFULL_CREATED_ORDER, "4");

		log.info("TC_01_Step_27: Lay ma giao dich");
		transactionNumber = transferInVCB.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC_01_Step_28: Kiem tra ten nguoi thu huong hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tên người thụ hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);

		log.info("TC_01_Step_29: Kiem tra tai khoan dich hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tài khoản đích"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_01_Step_30: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_01_Step_31: Click thuc hien giao dich moi");
		transferInVCB.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_01_Step_32: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_01_Step_33: Chon tai ngoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT2);

		log.info("TC_01_Step_34: Lay so du tai khoan chuyen");
		String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		long afterBalanceAmountOfAccount1 = convertMoneyToLong(afterBalanceOfAccount1, "VND");

		log.info("TC_01_Step_35: Kiem tra so du tai khoan chuyen sau khi thuc hien giao dich");
		verifyEquals(beforeBalanceAmountOfAccount1, afterBalanceAmountOfAccount1);

		log.info("TC_01_Step_36: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_01_Step_37: Chon tai khoan nguoi nhan");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_01_Step_38: Lay so du kha dung tai khoan nhan");
		String afterBalanceOfAccount2 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");

		log.info("TC_01_Step_39: Kiem tra so du tai khoan nhan sau khi nhan tien");
		long afterBalanceAmountOfAccount2 = convertMoneyToLong(afterBalanceOfAccount2, "VND");
		verifyEquals(beforeBalanceAmountOfAccount2, afterBalanceAmountOfAccount2);

	}

	@Test
	public void TC_04_KiemTraChiTietGiaoDichChuyenTienTuongLaiCoPhiGiaoDichNguoiNhanTraVNDVaXacThucBangOTP() {
		log.info("TC_02_Step_01 : Click  nut Back");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");

		log.info("TC_02_Step_02: Click vao More Icon");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicIcon(driver, "Trạng thái lệnh chuyển tiền");

		log.info("TC_02_Step_04: Click Tat Ca Cac Loai Giao Dich");
		transStatus = PageFactoryManager.getTransferMoneyStatusPageObject(driver);
		transStatus.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");

		log.info("TC_02_Step_05: Chon Chuyen Tien Trong VCB");
		transStatus.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày tương lai");

		log.info("TC_02_Step_12: Kiem tra from date hien thi");
		verifyEquals(transStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate"), getBackwardDate(29));

		log.info("TC_02_Step_12: Kiem tra todate hien thi");
		verifyEquals(transStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate"), today);

		log.info("TC_02_Step_12: Kiem tra Note hien thi");
		verifyTrue(transStatus.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyStatus_Data.Output.NOTE));

		log.info("TC_02_Step_08: CLick Tim Kiem");
		transStatus.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC06_Step 09: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));

		log.info("TC06_Step 10: Kiem tra thoi gian tao dao dich");
		verifyTrue(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC06_Step 10: Kiem tra nguoi nhan");
		verifyEquals(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvContent"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);

		log.info("TC06_Step 10: Kiem tra trang thai giao dich");
		verifyEquals(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvStatus"), TransferMoneyStatus_Data.Output.WAITING_STATUS);

		log.info("TC06_Step 10: Kiem tra so tien giao dich");
		verifyEquals(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "1", "com.VCB:id/tvMoney"), addCommasToLong(TransferMoneyInVCB_Data.InputDataInFuture.TRANSFER_AMOUNT) + " VND");

		log.info("TC06_Step 10: click vao giao dich");
		transStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvDate");

		log.info("TC06_Step 14: Kiem tra ngay giao dich hien thi");
		verifyTrue(transStatus.getDynamicTextInTransactionDetail(driver, "Ngày lập lệnh").contains(transferTime.split(" ")[0]));

		log.info("TC06_Step 15: Kiem tra thoi gian tao giao dich hien thi");
		verifyTrue(transStatus.getDynamicTextInTransactionDetail(driver, "Ngày lập lệnh").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC06_Step 15: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, "Người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);

		log.info("TC06_Step 15: Kiem tra so tien thi");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, "Số tiền"), addCommasToLong(TransferMoneyInVCB_Data.InputDataInFuture.TRANSFER_AMOUNT) + " VND");

		log.info("TC06_Step 15: Kiem tra ngay hieu luc");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, "Ngày hiệu lực"), tommorrowDate);

		log.info("TC06_Step 15: Kiem tra ghi chú");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, "ghi chú"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC06_Step 15: Kiem tra trang thai hieu luc");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, "tommorrowDate"), TransferMoneyStatus_Data.Output.WAITING_STATUS);

		log.info("TC_02_Step_35: Click quay lai");
		transStatus.clickToDynamicBackIcon(driver, "Chi tiết lệnh chuyển tiền");

		log.info("TC_02_Step_36: Click quay lai");
		transStatus.clickToDynamicBackIcon(driver, "Trạng thái lệnh chuyển tiền");

	}

//	@Test
	public void TC_05_ChuyenTienNgayCoPhiGiaoDichNguoiChuyenTraUSDVaXacThucBangOTP() {

		log.info("TC05_Step 01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC05_Step 02: Click chon tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC05_Step 03: Chon account 1");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC05_Step 04: Lay so du kha dung tai khoan 1");
		String beforeBalanceOfAccount2 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		double beforeBalanceAmountOfAccount2 = convertMoneyToDouble(beforeBalanceOfAccount2, "VND");

		log.info("TC05_Step 05 : Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC05_Step 06: Chon USD account");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.USD_ACCOUNT);

		log.info("TC05_Step 07: Lay so du tai khoan USD");
		String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		double beforeBalanceAmountOfAccount1 = convertMoneyToDouble(beforeBalanceOfAccount1, "USD");

		log.info("TC05_Step 08: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1, "Nhập/chọn tài khoản nhận VND");

		log.info("TC05_Step 09: Nhap so tien can chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER, "Số tiền");

		log.info("TC05_Step 10: Lay ti gia quy doi");
		String exchangeRate = transferInVCB.getDynamicTextInTransactionDetail(driver, "Tỷ giá quy đổi tham khảo");
		exchangeRateUSD = exchangeRate.split(" ~ ");
		rate = convertMoneyToDouble(exchangeRateUSD[1], "VND");

		log.info("TC05_Step 11: Chon phuong thuc xac thuc");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");

		log.info("TC05_Step 12: Chon phi giao dich nguoi chuyen tra");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Người chuyển trả");

		log.info("TC05_Step 13:  Nhap noi dung");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Nội dung");

		log.info("TC05_Step 14: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC05_Step 15: Kiem tra hinh thuc chuyen tien hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Hình thức chuyển tiền"), TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[0]);

		log.info("TC05_Step 16: Kiem tra tai khoan nguon hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), TransferMoneyInVCB_Data.InputDataInVCB.USD_ACCOUNT);

		log.info("TC05_Step 17: Kiem tra tai khoan dich hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tài khoản đích / VND").contains(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1));

		log.info("TC05_Step 18: Kiem tra so tien chuyen hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, "Số tiền (USD)").contains(addCommasToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER) + "USD"));

		log.info("TC05_Step 19: Kiem tra Nguoi chuyen tra hien thi");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.InputDataInVCB.COST[0]));

		log.info("TC05_Step 20: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC05_Step 21: Chon phuong thuc xac thuc");
		transferInVCB.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");

		log.info("TC05_Step 22: Chon SMS OTP");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC05_Step 23: Kiem tra so tien phi hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, "Số tiền phí").contains(TransferMoneyInVCB_Data.InputDataInVCB.USD_PAYMENT_BY_OTP_FEE + " USD"));

		log.info("TC05_Step 24: Lay tien phi chuyen ");
		transferFee = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số tiền phí");
		double newTransferFee = convertMoneyToDouble(transferFee, "USD");

		log.info("TC05_Step 25: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC05_Step 26: Dien OTP");
		transferInVCB.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		log.info("TC05_Step 27: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE));

		log.info("TC05_Step 28: Lay thoi gian tao");
		transferTime = transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE, "4");

		log.info("TC05_Step 29: Kiem tra so tien chuyen hien thi");
		verifyEquals(transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE, "3"), addCommasToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " USD");

		log.info("TC05_Step 30: Kiem tra ten nguoi thu huong hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tên người thụ hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);

		log.info("TC05_Step 31: Kiem tra tai khoan dich hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tài khoản đích"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC05_Step 32: Lay ma giao dich");
		transactionNumber = transferInVCB.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC05_Step 33: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC05_Step 34: Click thuc hien giao dich moi");
		transferInVCB.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC05_Step 35: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC05_Step 36:Click USD account");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.USD_ACCOUNT);

		log.info("TC05_Step 37: Lay so du kha dung tai khoan USD");
		String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		double afterBalanceAmountOfAccount1 = convertMoneyToDouble(afterBalanceOfAccount1, "USD");

		log.info("TC05_Step 38: Convert so tien chuyen");
		double transferMoney = convertMoneyToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER, "USD");

		log.info("TC05_Step 39: Kiem tra so du tai khoan USD sau khi chuyen tien");
		verifyEquals(beforeBalanceAmountOfAccount1 - transferMoney - newTransferFee, afterBalanceAmountOfAccount1);

		log.info("TC05_Step 40: Kiem tra tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC05_Step 41: Click chon tai khoan chuyen den");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC05_Step 41: Lay so du kha dung tai khoan chuyen den");
		String afterBalanceOfAccount2 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		double afterBalanceAmountOfAccount2 = convertMoneyToDouble(afterBalanceOfAccount2, "VND");

		log.info("TC05_Step 43: Kiem tra so du tai khoan duoc chuyen den");
		verifyEquals(beforeBalanceAmountOfAccount2 + transferMoney * rate, afterBalanceAmountOfAccount2);

	}

	@AfterClass(alwaysRun = true)

	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
