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

public class Transfer_Money_In_Future_And_Report_1 extends Base {
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

		log.info("TC_01_Step_02: Chon chuyển tiền ngay");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");

		log.info("TC_01_Step_03: Chon chuyển tiền ngay");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày tương lai");

		log.info("TC_01_Step_04:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_01_Step_05: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT2);

		log.info("TC_01_Step_06: Lay so du tai khoan dich");
		String beforeBalanceOfAccount2 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		long beforeBalanceAmountOfAccount2 = convertMoneyToLong(beforeBalanceOfAccount2, "VND");

		log.info("TC_01_Step_07:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_01_Step_08: Chon tai khoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_01_Step_09: Lay so du tai khoan chuyen");
		String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		long beforeBalanceAmountOfAccount1 = convertMoneyToLong(beforeBalanceOfAccount1, "VND");

		log.info("TC_01_Step_10: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT2, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_01_Step_11: Kiem tra Ngay hieu luc mac dinh");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, tommorrowDate));

		log.info("TC_01_Step_12: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInFutureForOTP.TRANSFER_AMOUNT, "Số tiền");

		log.info("TC_01_Step_13: Nhap noi dung");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Nội dung");

		log.info("TC_01_Step_14: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_15: Kiem tra hinh thuc chuyen tien hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Hình thức chuyển tiền"), TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[2]);

		log.info("TC_01_Step_16: Kiem tra tai khoan nguon hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_01_Step_17: Kiem tra tai khoan dich hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tài khoản đích/ VND").contains(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT2));

		log.info("TC_01_Step_18: Kiem tra so tien chuyen hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Số tiền"), addCommasToLong(TransferMoneyInVCB_Data.InputDataInFutureForOTP.TRANSFER_AMOUNT) + " VND");

		log.info("TC_01_Step_19: Kiem tra Ngay hieu luc hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Ngày hiệu lực"), tommorrowDate);

		log.info("TC_01_Step_20: Kiem tra phuong thuc tra phi");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.InputDataInVCB.COST[0]));

		log.info("TC_01_Step_21: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_01_Step_22: Chon Phuong thuc nhap");
		transferInVCB.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");

		log.info("TC_01_Step_23: Chon SMS OTP");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_01_Step_24: Kiem tra so tien phi hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), (TransferMoneyInVCB_Data.InputDataInFutureForOTP.OTP_FEE + " VND"));

		log.info("TC_01_Step_25: Click Tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_26: Nhap OTP");
		transferInVCB.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		log.info("TC_01_Step_27: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_28: Kiem  tra giao dich thanh cong");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.SUCESSFULL_CREATED_ORDER));

		log.info("TC_01_Step_29: Kiem tra so tien chuyen");
		verifyEquals(transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.SUCESSFULL_CREATED_ORDER, "3"), addCommasToLong(TransferMoneyInVCB_Data.InputDataInFutureForOTP.TRANSFER_AMOUNT) + " VND");

		log.info("TC_01_Step_30: Lay thoi gian tao giao dich");
		transferTime = transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.SUCESSFULL_CREATED_ORDER, "4");

		log.info("TC_01_Step_31: Lay ma giao dich");
		transactionNumber = transferInVCB.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");
		log.info("TC_01_Step_32: Lay ma giao dich" + transactionNumber);

		log.info("TC_01_Step_33: Kiem tra ten nguoi thu huong hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);

		log.info("TC_01_Step_34: Kiem tra tai khoan dich hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tài khoản đích"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT2);

		log.info("TC_01_Step_35: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_01_Step_36: Click thuc hien giao dich moi");
		transferInVCB.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_01_Step_37: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_01_Step_38: Chon tai ngoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_01_Step_39: Lay so du tai khoan chuyen");
		String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		long afterBalanceAmountOfAccount1 = convertMoneyToLong(afterBalanceOfAccount1, "VND");

		log.info("TC_01_Step_40: Kiem tra so du tai khoan chuyen sau khi thuc hien giao dich");
		verifyEquals(beforeBalanceAmountOfAccount1, afterBalanceAmountOfAccount1);

		log.info("TC_01_Step_41: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_01_Step_42: Chon tai khoan nguoi nhan");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT2);

		log.info("TC_01_Step_43: Lay so du kha dung tai khoan nhan");
		String afterBalanceOfAccount2 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");

		log.info("TC_01_Step_44: Kiem tra so du tai khoan nhan sau khi nhan tien");
		long afterBalanceAmountOfAccount2 = convertMoneyToLong(afterBalanceOfAccount2, "VND");
		verifyEquals(beforeBalanceAmountOfAccount2, afterBalanceAmountOfAccount2);
	}

	@Test
	public void TC_02_KiemTraTrạngThaiLenhChuyenTienCuaGiaoDichTuongLaiCoPhiGiaoDichNguoiChuyenTraVNDVaXacThucOTP() {
		log.info("TC_02_Step_01 : Click  nut Back");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");

		log.info("TC_02_Step_02: Click vao More Icon");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicIcon(driver, "Trạng thái lệnh chuyển tiền");

		log.info("TC_02_Step_03: Click Tat Ca Cac Loai Giao Dich");
		transStatus = PageFactoryManager.getTransferMoneyStatusPageObject(driver);
		transStatus.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");

		log.info("TC_02_Step_04: Chon Chuyen Tien Trong VCB");
		transStatus.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày tương lai");

		log.info("TC_02_Step_05: Kiem tra from date hien thi");
		verifyEquals(transStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate"), getBackwardDate(29));

		log.info("TC_02_Step_06: Kiem tra todate hien thi");
		verifyEquals(transStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate"), today);

		log.info("TC_02_Step_07: Kiem tra Note hien thi");
		verifyTrue(transStatus.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyStatus_Data.Output.NOTE));

		log.info("TC_02_Step_08: CLick Tim Kiem");
		transStatus.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_02_Step 09: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));

		log.info("TC_02_Step 10: Kiem tra thoi gian tao dao dich");
		verifyTrue(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_02_Step 11: Kiem tra nguoi nhan");
		verifyEquals(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvContent"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);

		log.info("TC_02_Step 12: Kiem tra trang thai giao dich");
		verifyEquals(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvStatus"), TransferMoneyStatus_Data.Output.WAITING_STATUS);

		log.info("TC_02_Step 13: Kiem tra so tien giao dich");
		verifyEquals(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "1", "com.VCB:id/tvMoney"), addCommasToLong(TransferMoneyInVCB_Data.InputDataInFutureForOTP.TRANSFER_AMOUNT) + " VND");

		log.info("TC_02_Step 14: click vao giao dich");
		transStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_02_Step 15: Kiem tra ngay giao dich hien thi");
		verifyTrue(transStatus.getDynamicTextInTransactionDetail(driver, "Ngày lập lệnh").contains(transferTime.split(" ")[0]));

		log.info("TC_02_Step 16: Kiem tra thoi gian tao giao dich hien thi");
		verifyTrue(transStatus.getDynamicTextInTransactionDetail(driver, "Ngày lập lệnh").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_02_Step 17: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, "Người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);

		log.info("TC_02_Step 18: Kiem tra so tien thi");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, "Số tiền"), addCommasToLong(TransferMoneyInVCB_Data.InputDataInFutureForOTP.TRANSFER_AMOUNT) + " VND");

		log.info("TC_02_Step 19: Kiem tra ngay hieu luc");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, "Ngày hiệu lực"), tommorrowDate);

		log.info("TC_02_Step 20: Kiem tra ghi chú");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, "Ghi chú"), "");

		log.info("TC_02_Step 21: Kiem tra trang thai hieu luc");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, "Trạng thái"), TransferMoneyStatus_Data.Output.WAITING_STATUS);

		log.info("TC_02_Step_22: Click quay lai");
		transStatus.clickToDynamicBackIcon(driver, "Chi tiết lệnh chuyển tiền");
	}

	@Test
	public void TC_03_HuyLenhGiaoDichTuongLaiXacThucOTP() {

		log.info("TC_03_Step_01: Click Huy Lenh");
		transStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "1", "com.VCB:id/tvHuyLenh");

		log.info("TC_03_Step_02: Click Dong Y");
		transStatus.clickToDynamicButton(driver, "Đồng ý");

		log.info("TC_03_Step_03: Kiem Tra Thong Bao hien thi");
		verifyTrue(transStatus.isDynamicMessageAndLabelTextDisplayed(driver, "Thông báo"));

		log.info("TC_03_Step_04: Kiem Tra Success hiển thị");
		verifyTrue(transStatus.isDynamicMessageAndLabelTextDisplayed(driver, "Success"));

		log.info("TC_03_Step_05: Click quay lai");
		transStatus.clickToDynamicButton(driver, "Đóng");

		log.info("TC_03_Step_06: Kiem tra trang thai Da Huy hien thi");
		verifyEquals(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvStatus"), TransferMoneyStatus_Data.Output.CANCEL_STATUS);

		log.info("TC_03_Step_07: click vao giao dich");
		transStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_03_Step_08: Kiem tra trang thai  Da Huy hien thi");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, "Trạng thái"), TransferMoneyStatus_Data.Output.CANCEL_STATUS);

		log.info("TC_03_Step_09: Click quay lai");
		transStatus.clickToDynamicBackIcon(driver, "Chi tiết lệnh chuyển tiền");

		log.info("TC_03_Step_10: Click quay lai");
		transStatus.clickToDynamicBackIcon(driver, "Trạng thái lệnh chuyển tiền");

	}

	@Test
	public void TC_04_ChuyenTienTuongLaiCoPhiGiaoDichNguoiNhanTraVNDVaXacThucBangOTP() {

		log.info("TC_04_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);

		log.info("TC_04_Step_02: Chon chuyển tiền ngay");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");

		log.info("TC_04_Step_03: Chon chuyển tiền ngay");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày tương lai");

		log.info("TC_04_Step_04:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_04_Step_05: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_04_Step_06: Lay so du tai khoan dich");
		String beforeBalanceOfAccount2 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		long beforeBalanceAmountOfAccount2 = convertMoneyToLong(beforeBalanceOfAccount2, "VND");

		log.info("TC_04_Step_07:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_04_Step_09: Chon tai khoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT2);

		log.info("TC_04_Step_09: Lay so du tai khoan chuyen");
		String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		long beforeBalanceAmountOfAccount1 = convertMoneyToLong(beforeBalanceOfAccount1, "VND");

		log.info("TC_04_Step_10: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_04_Step_11: Kiem tra Ngay hieu luc mac dinh");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, tommorrowDate));

		log.info("TC_04_Step_12: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInFutureForOTP.TRANSFER_AMOUNT, "Số tiền");

		log.info("TC_04_Step_13: Nhap noi dung");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Nội dung");

		log.info("TC_04_Step_14: Click nguoi chuyen tra");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");

		log.info("TC_04_Step_15: Click nguoi chuyen tra");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Người nhận trả");

		log.info("TC_04_Step_16: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_04_Step_17: Kiem tra hinh thuc chuyen tien hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Hình thức chuyển tiền"), TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[2]);

		log.info("TC_04_Step_18: Kiem tra tai khoan nguon hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT2);

		log.info("TC_04_Step_19: Kiem tra tai khoan dich hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tài khoản đích/ VND").contains(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1));

		log.info("TC_04_Step_20: Kiem tra so tien chuyen hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Số tiền"), addCommasToLong(TransferMoneyInVCB_Data.InputDataInFutureForOTP.TRANSFER_AMOUNT) + " VND");

		log.info("TC_04_Step_21: Kiem tra Ngay hieu luc hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Ngày hiệu lực"), tommorrowDate);

		log.info("TC_04_Step_22: Kiem tra phuong thuc tra phi");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.InputDataInVCB.COST[1]));

		log.info("TC_04_Step_23: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_04_Step_24: Chon Phuong thuc nhap");
		transferInVCB.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");

		log.info("TC_04_Step_25: Chon SMS OTP");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_04_Step_26: Kiem tra so tien phi hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), (TransferMoneyInVCB_Data.InputDataInFutureForOTP.OTP_FEE + " VND"));

		log.info("TC_04_Step_27: Click Tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_04_Step_28: Nhap OTP");
		transferInVCB.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		log.info("TC_04_Step_29: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_04_Step_30: Kiem  tra giao dich thanh cong");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.SUCESSFULL_CREATED_ORDER));

		log.info("TC_04_Step_31: Kiem tra so tien chuyen");
		verifyEquals(transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.SUCESSFULL_CREATED_ORDER, "3"), addCommasToLong(TransferMoneyInVCB_Data.InputDataInFutureForOTP.TRANSFER_AMOUNT) + " VND");

		log.info("TC_04_Step_32: Lay thoi gian tao giao dich");
		transferTime = transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.SUCESSFULL_CREATED_ORDER, "4");

		log.info("TC_04_Step_33: Lay ma giao dich");
		transactionNumber = transferInVCB.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");
		log.info("TC_04_Step_27: Lay ma giao dich" + transactionNumber);

		log.info("TC_04_Step_34: Kiem tra ten nguoi thu huong hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);

		log.info("TC_04_Step_35: Kiem tra tai khoan dich hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tài khoản đích"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_04_Step_36: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_04_Step_37: Click thuc hien giao dich moi");
		transferInVCB.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_04_Step_38: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_04_Step_39: Chon tai ngoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT2);

		log.info("TC_04_Step_40: Lay so du tai khoan chuyen");
		String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		long afterBalanceAmountOfAccount1 = convertMoneyToLong(afterBalanceOfAccount1, "VND");

		log.info("TC_04_Step_41: Kiem tra so du tai khoan chuyen sau khi thuc hien giao dich");
		verifyEquals(beforeBalanceAmountOfAccount1, afterBalanceAmountOfAccount1);

		log.info("TC_04_Step_42: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_04_Step_43: Chon tai khoan nguoi nhan");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_04_Step_44: Lay so du kha dung tai khoan nhan");
		String afterBalanceOfAccount2 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");

		log.info("TC_04_Step_45: Kiem tra so du tai khoan nhan sau khi nhan tien");
		long afterBalanceAmountOfAccount2 = convertMoneyToLong(afterBalanceOfAccount2, "VND");
		verifyEquals(beforeBalanceAmountOfAccount2, afterBalanceAmountOfAccount2);

	}

	@Test
	public void TC_05_KiemTraChiTietGiaoDichChuyenTienTuongLaiCoPhiGiaoDichNguoiNhanTraVNDVaXacThucBangOTP() {
		log.info("TC_05_Step_01 : Click  nut Back");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");

		log.info("TC_05_Step_02: Click vao More Icon");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicIcon(driver, "Trạng thái lệnh chuyển tiền");

		log.info("TC_05_Step_03: Click Tat Ca Cac Loai Giao Dich");
		transStatus = PageFactoryManager.getTransferMoneyStatusPageObject(driver);
		transStatus.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");

		log.info("TC_05_Step_04 Chon Chuyen Tien Trong VCB");
		transStatus.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày tương lai");

		log.info("TC_05_Step_05: Kiem tra from date hien thi");
		verifyEquals(transStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate"), getBackwardDate(29));

		log.info("TC_05_Step_06: Kiem tra todate hien thi");
		verifyEquals(transStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate"), today);

		log.info("TC_05_Step_07: Kiem tra Note hien thi");
		verifyTrue(transStatus.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyStatus_Data.Output.NOTE));

		log.info("TC_05_Step_08: CLick Tim Kiem");
		transStatus.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_05_Step 09: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));

		log.info("TC_05_Step 10: Kiem tra thoi gian tao dao dich");
		verifyTrue(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_05_Step 11: Kiem tra nguoi nhan");
		verifyEquals(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvContent"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);

		log.info("TC_05_Step 12: Kiem tra trang thai giao dich");
		verifyEquals(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvStatus"), TransferMoneyStatus_Data.Output.WAITING_STATUS);

		log.info("TC_05_Step 13: Kiem tra so tien giao dich");
		verifyEquals(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "1", "com.VCB:id/tvMoney"), addCommasToLong(TransferMoneyInVCB_Data.InputDataInFutureForOTP.TRANSFER_AMOUNT) + " VND");

		log.info("TC_05_Step 14: click vao giao dich");
		transStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_05_Step 15: Kiem tra ngay giao dich hien thi");
		verifyTrue(transStatus.getDynamicTextInTransactionDetail(driver, "Ngày lập lệnh").contains(transferTime.split(" ")[0]));

		log.info("TC_05_Step 16: Kiem tra thoi gian tao giao dich hien thi");
		verifyTrue(transStatus.getDynamicTextInTransactionDetail(driver, "Ngày lập lệnh").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_05_Step 17: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, "Người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);

		log.info("TC_05_Step 18: Kiem tra so tien thi");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, "Số tiền"), addCommasToLong(TransferMoneyInVCB_Data.InputDataInFutureForOTP.TRANSFER_AMOUNT) + " VND");

		log.info("TC_05_Step 19: Kiem tra ngay hieu luc");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, "Ngày hiệu lực"), tommorrowDate);

		log.info("TC_05_Step 20: Kiem tra ghi chú");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, "Ghi chú"), "");

		log.info("TC_05_Step 21: Kiem tra trang thai hieu luc");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, "Trạng thái"), TransferMoneyStatus_Data.Output.WAITING_STATUS);

		log.info("TC_05_Step_22: Click quay lai");
		transStatus.clickToDynamicBackIcon(driver, "Chi tiết lệnh chuyển tiền");

	}

	@Test
	public void TC_06_HuyLenhGiaoDichTuongLaiXacThucOTP() {

		log.info("TC_06_Step_01: Click Huy Lenh");
		transStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "1", "com.VCB:id/tvHuyLenh");

		log.info("TC_06_Step_02: Click Dong Y");
		transStatus.clickToDynamicButton(driver, "Đồng ý");

		log.info("TC_06_Step_03: Kiem Tra Thong Bao hien thi");
		verifyTrue(transStatus.isDynamicMessageAndLabelTextDisplayed(driver, "Thông báo"));

		log.info("TC_06_Step_04: Kiem Tra Success hiển thị");
		verifyTrue(transStatus.isDynamicMessageAndLabelTextDisplayed(driver, "Success"));

		log.info("TC_06_Step_05: Click quay lai");
		transStatus.clickToDynamicButton(driver, "Đóng");

		log.info("TC_06_Step_06: Kiem tra trang thai Da Huy hien thi");
		verifyEquals(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvStatus"), TransferMoneyStatus_Data.Output.CANCEL_STATUS);

		log.info("TC_06_Step_07: click vao giao dich");
		transStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_06_Step_08: Kiem tra trang thai  Da Huy hien thi");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, "Trạng thái"), TransferMoneyStatus_Data.Output.CANCEL_STATUS);

		log.info("TC_06_Step_09: Click quay lai");
		transStatus.clickToDynamicBackIcon(driver, "Chi tiết lệnh chuyển tiền");

		log.info("TC_06_Step_10: Click quay lai");
		transStatus.clickToDynamicBackIcon(driver, "Trạng thái lệnh chuyển tiền");

	}

	@Test
	public void TC_07_ChuyenTienTuongLaiCoPhiGiaoDichNguoiChuyenTraUSDVaXacThucBangOTP() {

		log.info("TC_07_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);

		log.info("TC_07_Step_02: Chon chuyển tiền ngay");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");

		log.info("TC_07_Step_03: Chon chuyển tiền ngay");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày tương lai");

		log.info("TC_07_Step_04:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_07_Step_05: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT2);

		log.info("TC_07_Step_06: Lay so du tai khoan dich");
		String beforeBalanceOfAccount2 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		long beforeBalanceAmountOfAccount2 = convertMoneyToLong(beforeBalanceOfAccount2, "VND");

		log.info("TC_07_Step_07:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_07_Step_08: Chon tai khoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.USD_ACCOUNT);

		log.info("TC_07_Step_09: Lay so du tai khoan chuyen");
		String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		double beforeBalanceAmountOfAccount1 = convertMoneyToDouble(beforeBalanceOfAccount1, "USD");

		log.info("TC_07_Step_10: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT2, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_07_Step_11: Kiem tra Ngay hieu luc mac dinh");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, tommorrowDate));

		log.info("TC_07_Step_12: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInFutureForOTP.AMOUNT_OF_EUR_OR_USD_TRANSFER, "Số tiền");

		log.info("TC_07_Step_13: Nhap noi dung");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Nội dung");

		log.info("TC_07_Step_14: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_07_Step_15: Kiem tra hinh thuc chuyen tien hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Hình thức chuyển tiền"), TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[2]);

		log.info("TC_07_Step_16: Kiem tra tai khoan nguon hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), TransferMoneyInVCB_Data.InputDataInVCB.USD_ACCOUNT);

		log.info("TC_07_Step_17: Kiem tra tai khoan dich hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tài khoản đích/ VND").contains(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT2));

		log.info("TC_07_Step_18: Kiem tra so tien chuyen hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Số tiền"), (TransferMoneyInVCB_Data.InputDataInFutureForOTP.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " USD");

		log.info("TC_07_Step_19: Kiem tra Ngay hieu luc hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Ngày hiệu lực"), tommorrowDate);

		log.info("TC_07_Step_20: Kiem tra phuong thuc tra phi");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.InputDataInVCB.COST[0]));

		log.info("TC_07_Step_21: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_07_Step_22: Chon Phuong thuc nhap");
		transferInVCB.scrollToText(driver, "Tiếp tục");
		transferInVCB.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");

		log.info("TC_07_Step_23: Chon SMS OTP");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_07_Step_24: Kiem tra so tien phi hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), (TransferMoneyInVCB_Data.InputDataInFutureForOTP.USD_SMS_OTP_FEE + " USD"));

		log.info("TC_07_Step_25: Click Tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_07_Step_26: Nhap OTP");
		transferInVCB.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		log.info("TC_07_Step_27: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_07_Step_28: Kiem  tra giao dich thanh cong");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.SUCESSFULL_CREATED_ORDER));

		log.info("TC_07_Step_29: Kiem tra so tien chuyen");
		verifyEquals(transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.SUCESSFULL_CREATED_ORDER, "3"), (TransferMoneyInVCB_Data.InputDataInFutureForOTP.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " USD");

		log.info("TC_07_Step_30: Lay thoi gian tao giao dich");
		transferTime = transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.SUCESSFULL_CREATED_ORDER, "4");

		log.info("TC_07_Step_31: Lay ma giao dich");
		transactionNumber = transferInVCB.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");
		log.info("TC_07_Step_32: Lay ma giao dich" + transactionNumber);

		log.info("TC_07_Step_33: Kiem tra ten nguoi thu huong hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);

		log.info("TC_07_Step_34: Kiem tra tai khoan dich hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tài khoản đích"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT2);

		log.info("TC_07_Step_35: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_07_Step_36: Click thuc hien giao dich moi");
		transferInVCB.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_07_Step_37: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_07_Step_38: Chon tai ngoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.USD_ACCOUNT);

		log.info("TC_07_Step_39: Lay so du tai khoan chuyen");
		String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		double afterBalanceAmountOfAccount1 = convertMoneyToDouble(afterBalanceOfAccount1, "USD");

		log.info("TC_07_Step_40: Kiem tra so du tai khoan chuyen sau khi thuc hien giao dich");
		verifyEquals(beforeBalanceAmountOfAccount1, afterBalanceAmountOfAccount1);

		log.info("TC_07_Step_41: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_07_Step_42: Chon tai khoan nguoi nhan");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT2);

		log.info("TC_07_Step_43: Lay so du kha dung tai khoan nhan");
		String afterBalanceOfAccount2 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");

		log.info("TC_07_Step_44: Kiem tra so du tai khoan nhan sau khi nhan tien");
		long afterBalanceAmountOfAccount2 = convertMoneyToLong(afterBalanceOfAccount2, "VND");
		verifyEquals(beforeBalanceAmountOfAccount2, afterBalanceAmountOfAccount2);

	}

	@Test
	public void TC_08_KiemTraChiTietGiaoDichChuyenTienTuongLaiCoPhiGiaoDichNguoiChuyenTraUSDVaXacThucBangOTP() {
		log.info("TC_08_Step_01 : Click  nut Back");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");

		log.info("TC_08_Step_02: Click vao More Icon");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicIcon(driver, "Trạng thái lệnh chuyển tiền");

		log.info("TC_08_Step_03: Click Tat Ca Cac Loai Giao Dich");
		transStatus = PageFactoryManager.getTransferMoneyStatusPageObject(driver);
		transStatus.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");

		log.info("TC_08_Step_04: Chon Chuyen Tien Trong VCB");
		transStatus.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày tương lai");

		log.info("TC_08_Step_05: Kiem tra from date hien thi");
		verifyEquals(transStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate"), getBackwardDate(29));

		log.info("TC_08_Step_06: Kiem tra todate hien thi");
		verifyEquals(transStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate"), today);

		log.info("TC_08_Step_07: Kiem tra Note hien thi");
		verifyTrue(transStatus.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyStatus_Data.Output.NOTE));

		log.info("TC_08_Step_08: CLick Tim Kiem");
		transStatus.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_08_Step 09: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));

		log.info("TC_08_Step 10: Kiem tra thoi gian tao dao dich");
		verifyTrue(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_08_Step 11: Kiem tra nguoi nhan");
		verifyEquals(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvContent"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);

		log.info("TC_08_Step 12: Kiem tra trang thai giao dich");
		verifyEquals(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvStatus"), TransferMoneyStatus_Data.Output.WAITING_STATUS);

		log.info("TC_08_Step 13: Kiem tra so tien giao dich");
		verifyEquals(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "1", "com.VCB:id/tvMoney"), TransferMoneyInVCB_Data.InputDataInFutureForOTP.AMOUNT_OF_EUR_OR_USD_TRANSFER + " USD");

		log.info("TC_08_Step 14: click vao giao dich");
		transStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_08_Step 15: Kiem tra ngay giao dich hien thi");
		verifyTrue(transStatus.getDynamicTextInTransactionDetail(driver, "Ngày lập lệnh").contains(transferTime.split(" ")[0]));

		log.info("TC_08_Step 16: Kiem tra thoi gian tao giao dich hien thi");
		verifyTrue(transStatus.getDynamicTextInTransactionDetail(driver, "Ngày lập lệnh").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_08_Step 17: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, "Người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);

		log.info("TC_08_Step 18: Kiem tra so tien thi");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, "Số tiền"), TransferMoneyInVCB_Data.InputDataInFutureForOTP.AMOUNT_OF_EUR_OR_USD_TRANSFER + " USD");

		log.info("TC_08_Step 19: Kiem tra ngay hieu luc");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, "Ngày hiệu lực"), tommorrowDate);

		log.info("TC_08_Step 20: Kiem tra ghi chú");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, "Ghi chú"), "");

		log.info("TC_08_Step 21: Kiem tra trang thai hieu luc");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, "Trạng thái"), TransferMoneyStatus_Data.Output.WAITING_STATUS);

		log.info("TC_08_Step_22: Click quay lai");
		transStatus.clickToDynamicBackIcon(driver, "Chi tiết lệnh chuyển tiền");

	}

	@Test
	public void TC_09_HuyLenhGiaoDichTuongLaiXacThucOTP() {

		log.info("TC_09_Step_01: Click Huy Lenh");
		transStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "1", "com.VCB:id/tvHuyLenh");

		log.info("TC_09_Step_02: Click Dong Y");
		transStatus.clickToDynamicButton(driver, "Đồng ý");

		log.info("TC_09_Step_03: Kiem Tra Thong Bao hien thi");
		verifyTrue(transStatus.isDynamicMessageAndLabelTextDisplayed(driver, "Thông báo"));

		log.info("TC_09_Step_04: Kiem Tra Success hiển thị");
		verifyTrue(transStatus.isDynamicMessageAndLabelTextDisplayed(driver, "Success"));

		log.info("TC_09_Step_05: Click quay lai");
		transStatus.clickToDynamicButton(driver, "Đóng");

		log.info("TC_09_Step_06: Kiem tra trang thai Da Huy hien thi");
		verifyEquals(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvStatus"), TransferMoneyStatus_Data.Output.CANCEL_STATUS);

		log.info("TC_09_Step_07: click vao giao dich");
		transStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_09_Step_08: Kiem tra trang thai  Da Huy hien thi");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, "Trạng thái"), TransferMoneyStatus_Data.Output.CANCEL_STATUS);

		log.info("TC_09_Step_09: Click quay lai");
		transStatus.clickToDynamicBackIcon(driver, "Chi tiết lệnh chuyển tiền");

		log.info("TC_09_Step_10: Click quay lai");
		transStatus.clickToDynamicBackIcon(driver, "Trạng thái lệnh chuyển tiền");

	}

	@Test
	public void TC_10_ChuyenTienTuongLaiCoPhiGiaoDichNguoiNhanTraEURVaXacThucBangOTP() {

		log.info("TC_10_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);

		log.info("TC_10_Step_02: Chon chuyển tiền ngay");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");

		log.info("TC_10_Step_03: Chon chuyển tiền ngay");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày tương lai");

		log.info("TC_10_Step_04:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_10_Step_05: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT2);

		log.info("TC_10_Step_06: Lay so du tai khoan dich");
		String beforeBalanceOfAccount2 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		long beforeBalanceAmountOfAccount2 = convertMoneyToLong(beforeBalanceOfAccount2, "VND");

		log.info("TC_10_Step_07:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_10_Step_08: Chon tai khoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.EUR_ACCOUNT);

		log.info("TC_10_Step_09: Lay so du tai khoan chuyen");
		String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		double beforeBalanceAmountOfAccount1 = convertMoneyToDouble(beforeBalanceOfAccount1, "EUR");

		log.info("TC_10_Step_10: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT2, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_10_Step_11: Kiem tra Ngay hieu luc mac dinh");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, tommorrowDate));

		log.info("TC_10_Step_12: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInFutureForOTP.AMOUNT_OF_EUR_OR_USD_TRANSFER, "Số tiền");

		log.info("TC_10_Step_13: Nhap noi dung");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Nội dung");

		log.info("TC_10_Step_14: Click nguoi chuyen tra");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");

		log.info("TC_10_Step_15: Click nguoi chuyen tra");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Người nhận trả");

		log.info("TC_10_Step_16: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_10_Step_17: Kiem tra hinh thuc chuyen tien hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Hình thức chuyển tiền"), TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[2]);

		log.info("TC_10_Step_18: Kiem tra tai khoan nguon hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), TransferMoneyInVCB_Data.InputDataInVCB.EUR_ACCOUNT);

		log.info("TC_10_Step_19: Kiem tra tai khoan dich hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tài khoản đích/ VND").contains(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT2));

		log.info("TC_10_Step_20: Kiem tra so tien chuyen hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Số tiền"), (TransferMoneyInVCB_Data.InputDataInFutureForOTP.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " EUR");

		log.info("TC_10_Step_21: Kiem tra Ngay hieu luc hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Ngày hiệu lực"), tommorrowDate);

		log.info("TC_10_Step_22: Kiem tra phuong thuc tra phi");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.InputDataInVCB.COST[1]));

		log.info("TC_10_Step_23: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_10_Step_24: Chon Phuong thuc nhap");
		transferInVCB.scrollToText(driver, "Tiếp tục");
		transferInVCB.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");

		log.info("TC_10_Step_25: Chon SMS OTP");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_10_Step_26: Kiem tra so tien phi hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), (TransferMoneyInVCB_Data.InputDataInFutureForOTP.EUR_SMS_OTP_FEE + " EUR"));

		log.info("TC_10_Step_27: Click Tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_10_Step_28: Nhap OTP");
		transferInVCB.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		log.info("TC_10_Step_29: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_10_Step_30: Kiem  tra giao dich thanh cong");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.SUCESSFULL_CREATED_ORDER));

		log.info("TC_10_Step_31: Kiem tra so tien chuyen");
		verifyEquals(transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.SUCESSFULL_CREATED_ORDER, "3"), (TransferMoneyInVCB_Data.InputDataInFutureForOTP.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " EUR");

		log.info("TC_10_Step_32: Lay thoi gian tao giao dich");
		transferTime = transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.SUCESSFULL_CREATED_ORDER, "4");

		log.info("TC_10_Step_33: Lay ma giao dich");
		transactionNumber = transferInVCB.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");
		log.info("TC_10_Step_34: Lay ma giao dich" + transactionNumber);

		log.info("TC_10_Step_35: Kiem tra ten nguoi thu huong hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);

		log.info("TC_10_Step_36: Kiem tra tai khoan dich hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tài khoản đích"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT2);

		log.info("TC_10_Step_37: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_10_Step_38: Click thuc hien giao dich moi");
		transferInVCB.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_10_Step_39: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_10_Step_40: Chon tai ngoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.EUR_ACCOUNT);

		log.info("TC_10_Step_41: Lay so du tai khoan chuyen");
		String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		double afterBalanceAmountOfAccount1 = convertMoneyToDouble(afterBalanceOfAccount1, "EUR");

		log.info("TC_10_Step_42: Kiem tra so du tai khoan chuyen sau khi thuc hien giao dich");
		verifyEquals(beforeBalanceAmountOfAccount1, afterBalanceAmountOfAccount1);

		log.info("TC_10_Step_43: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_10_Step_44: Chon tai khoan nguoi nhan");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT2);

		log.info("TC_10_Step_45: Lay so du kha dung tai khoan nhan");
		String afterBalanceOfAccount2 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");

		log.info("TC_10_Step_46: Kiem tra so du tai khoan nhan sau khi nhan tien");
		long afterBalanceAmountOfAccount2 = convertMoneyToLong(afterBalanceOfAccount2, "VND");
		verifyEquals(beforeBalanceAmountOfAccount2, afterBalanceAmountOfAccount2);

	}

	@Test
	public void TC_11_KiemTraChiTietGiaoDichChuyenTienTuongLaiCoPhiGiaoDichNguoiNhanTraEURVaXacThucBangOTP() {
		log.info("TC_11_Step_01 : Click  nut Back");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");

		log.info("TC_11_Step_02: Click vao More Icon");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicIcon(driver, "Trạng thái lệnh chuyển tiền");

		log.info("TC_11_Step_03: Click Tat Ca Cac Loai Giao Dich");
		transStatus = PageFactoryManager.getTransferMoneyStatusPageObject(driver);
		transStatus.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");

		log.info("TC_11_Step_04: Chon Chuyen Tien Trong VCB");
		transStatus.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày tương lai");

		log.info("TC_11_Step_05: Kiem tra from date hien thi");
		verifyEquals(transStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate"), getBackwardDate(29));

		log.info("TC_11_Step_06: Kiem tra todate hien thi");
		verifyEquals(transStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate"), today);

		log.info("TC_11_Step_07: Kiem tra Note hien thi");
		verifyTrue(transStatus.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyStatus_Data.Output.NOTE));

		log.info("TC_11_Step_08: CLick Tim Kiem");
		transStatus.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_11_Step 09: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));

		log.info("TC_11_Step 10: Kiem tra thoi gian tao dao dich");
		verifyTrue(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_11_Step 11: Kiem tra nguoi nhan");
		verifyEquals(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvContent"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);

		log.info("TC_11_Step 12: Kiem tra trang thai giao dich");
		verifyEquals(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvStatus"), TransferMoneyStatus_Data.Output.WAITING_STATUS);

		log.info("TC_11_Step 13: Kiem tra so tien giao dich");
		verifyEquals(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "1", "com.VCB:id/tvMoney"), TransferMoneyInVCB_Data.InputDataInFutureForOTP.AMOUNT_OF_EUR_OR_USD_TRANSFER + " EUR");

		log.info("TC_11_Step 14: click vao giao dich");
		transStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_11_Step 15: Kiem tra ngay giao dich hien thi");
		verifyTrue(transStatus.getDynamicTextInTransactionDetail(driver, "Ngày lập lệnh").contains(transferTime.split(" ")[0]));

		log.info("TC_11_Step 16: Kiem tra thoi gian tao giao dich hien thi");
		verifyTrue(transStatus.getDynamicTextInTransactionDetail(driver, "Ngày lập lệnh").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_11_Step 17: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, "Người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);

		log.info("TC_11_Step 18: Kiem tra so tien thi");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, "Số tiền"), TransferMoneyInVCB_Data.InputDataInFutureForOTP.AMOUNT_OF_EUR_OR_USD_TRANSFER + " EUR");

		log.info("TC_11_Step 19: Kiem tra ngay hieu luc");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, "Ngày hiệu lực"), tommorrowDate);

		log.info("TC_11_Step 20: Kiem tra ghi chú");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, "Ghi chú"), "");

		log.info("TC_11_Step 21: Kiem tra trang thai hieu luc");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, "Trạng thái"), TransferMoneyStatus_Data.Output.WAITING_STATUS);

		log.info("TC_11_Step_22: Click quay lai");
		transStatus.clickToDynamicBackIcon(driver, "Chi tiết lệnh chuyển tiền");

	}

	@Test
	public void TC_12_HuyLenhGiaoDichTuongLaiXacThucOTP() {

		log.info("TC_12_Step_01: Click Huy Lenh");
		transStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "1", "com.VCB:id/tvHuyLenh");

		log.info("TC_12_Step_02: Click Dong Y");
		transStatus.clickToDynamicButton(driver, "Đồng ý");

		log.info("TC_12_Step_03: Kiem Tra Thong Bao hien thi");
		verifyTrue(transStatus.isDynamicMessageAndLabelTextDisplayed(driver, "Thông báo"));

		log.info("TC_12_Step_04: Kiem Tra Success hiển thị");
		verifyTrue(transStatus.isDynamicMessageAndLabelTextDisplayed(driver, "Success"));

		log.info("TC_12_Step_05: Click quay lai");
		transStatus.clickToDynamicButton(driver, "Đóng");

		log.info("TC_12_Step_06: Kiem tra trang thai Da Huy hien thi");
		verifyEquals(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvStatus"), TransferMoneyStatus_Data.Output.CANCEL_STATUS);

		log.info("TC_12_Step_07: click vao giao dich");
		transStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_12_Step_08: Kiem tra trang thai  Da Huy hien thi");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, "Trạng thái"), TransferMoneyStatus_Data.Output.CANCEL_STATUS);

		log.info("TC_12_Step_09: Click quay lai");
		transStatus.clickToDynamicBackIcon(driver, "Chi tiết lệnh chuyển tiền");

		log.info("TC_12_Step_10: Click quay lai");
		transStatus.clickToDynamicBackIcon(driver, "Trạng thái lệnh chuyển tiền");

	}

	@Test
	public void TC_13_ChuyenTienTuongLaiDenTaiKhoanKhacChuSoHuuCoPhiGiaoDichNguoiChuyenTraVNDVaXacThucBangOTP() {
		log.info("TC_13_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_13_Step_02: Chon chuyển tiền ngay");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");

		log.info("TC_13_Step_03: Chon chuyển tiền ngay");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày tương lai");

		log.info("TC_13_Step_04:Click tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_13_Step_05: Chon tai khoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_13_Step_06: Lay so du tai khoan chuyen");
		String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		long beforeBalanceAmountOfAccount1 = convertMoneyToLong(beforeBalanceOfAccount1, "VND");

		log.info("TC_13_Step_07: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.DIFFERENT_OWNER_ACCOUNT, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_13_Step_08: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInFutureForOTP.TRANSFER_AMOUNT, "Số tiền");

		log.info("TC_13_Step_09: Nhap noi dung");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Nội dung");

		log.info("TC_13_Step_10: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_13_Step_11: Kiem tra hinh thuc chuyen tien hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Hình thức chuyển tiền"), TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[2]);

		log.info("TC_13_Step_12: Kiem tra tai khoan nguon hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_13_Step_13: Kiem tra tai khoan dich hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tài khoản đích/ VND").contains(TransferMoneyInVCB_Data.InputDataInVCB.DIFFERENT_OWNER_ACCOUNT));

		log.info("TC_13_Step_14: Kiem tra so tien chuyen hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Số tiền"), (addCommasToLong(TransferMoneyInVCB_Data.InputDataInFutureForOTP.TRANSFER_AMOUNT)) + " VND");

		log.info("TC_13_Step_15: Kiem tra phuong thuc thanh toan phi bang OTP");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.InputDataInVCB.COST[0]));

		log.info("TC_13_Step_16: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_13_Step_17: Chon Phuong thuc nhap");
		transferInVCB.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");

		log.info("TC_13_Step_18: Chon SMS OTP");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_13_Step_19: Kiem tra so tien phi hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), TransferMoneyInVCB_Data.InputDataInVCB.TRANSFER_OTP_FEE_TO_OTHER_ACCOUNT_OWNER + " VND");

		log.info("TC_13_Step_20: Click Tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_13_Step_21: Nhap OTP");
		transferInVCB.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		log.info("TC_13_Step_22: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_13_Step_23: Kiem  tra giao dich thanh cong");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.SUCESSFULL_CREATED_ORDER));

		log.info("TC_13_Step_24: Lay thoi gian tao giao dich");
		transferTime = transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.SUCESSFULL_CREATED_ORDER, "4");

		log.info("TC_13_Step_25: Lay ma giao dich");
		transactionNumber = transferInVCB.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC_13_Step_26: Kiem tra ten nguoi thu huong hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.DIFFERENT_OWNER_NAME);

		log.info("TC_13_Step_27: Kiem tra tai khoan dich hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tài khoản đích"), TransferMoneyInVCB_Data.InputDataInVCB.DIFFERENT_OWNER_ACCOUNT);

		log.info("TC_13_Step_28: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_13_Step_29: Click thuc hien giao dich moi");
		transferInVCB.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_13_Step_30: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_13_Step_31: Chon tai ngoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_13_Step_32: Lay so du tai khoan chuyen");
		String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		long afterBalanceAmountOfAccount1 = convertMoneyToLong(afterBalanceOfAccount1, "VND");

		log.info("TC_13_Step_33: Kiem tra so du tai khoan chuyen sau khi thuc hien giao dich");
		verifyEquals(beforeBalanceAmountOfAccount1, afterBalanceAmountOfAccount1);

	}

	@Test
	public void TC_14_KiemTraChiTietGiaoDichChuyenTienTuongLaiCoPhiGiaoDichNguoiChuyenTraEURVaXacThucBangOTP() {
		log.info("TC_14_Step_01 : Click  nut Back");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");

		log.info("TC_14_Step_02: Click vao Trang Thai lenh chuyen Tien");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicIcon(driver, "Trạng thái lệnh chuyển tiền");

		log.info("TC_14_Step_03: Click Tat Ca Cac Loai Giao Dich");
		transStatus = PageFactoryManager.getTransferMoneyStatusPageObject(driver);
		transStatus.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");

		log.info("TC_14_Step_04: Chon Chuyen Tien Trong VCB");
		transStatus.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày tương lai");

		log.info("TC_14_Step_05: Kiem tra from date hien thi");
		verifyEquals(transStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate"), getBackwardDate(29));

		log.info("TC_14_Step_06: Kiem tra todate hien thi");
		verifyEquals(transStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate"), today);

		log.info("TC_14_Step_07: Kiem tra Note hien thi");
		verifyTrue(transStatus.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyStatus_Data.Output.NOTE));

		log.info("TC_14_Step_08: CLick Tim Kiem");
		transStatus.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_14_Step 09: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));

		log.info("TC_14_Step 10: Kiem tra thoi gian tao dao dich");
		verifyTrue(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_14_Step 11: Kiem tra nguoi nhan");
		verifyEquals(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvContent"), TransferMoneyInVCB_Data.InputDataInVCB.DIFFERENT_OWNER_NAME);

		log.info("TC_14_Step 12: Kiem tra trang thai giao dich");
		verifyEquals(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvStatus"), TransferMoneyStatus_Data.Output.WAITING_STATUS);

		log.info("TC_14_Step 13: Kiem tra so tien giao dich");
		verifyEquals(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "1", "com.VCB:id/tvMoney"), addCommasToLong(TransferMoneyInVCB_Data.InputDataInFutureForOTP.TRANSFER_AMOUNT) + " VND");

		log.info("TC_14_Step 14: click vao giao dich");
		transStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_14_Step 15: Kiem tra ngay giao dich hien thi");
		verifyTrue(transStatus.getDynamicTextInTransactionDetail(driver, "Ngày lập lệnh").contains(transferTime.split(" ")[0]));

		log.info("TC_14_Step 16: Kiem tra thoi gian tao giao dich hien thi");
		verifyTrue(transStatus.getDynamicTextInTransactionDetail(driver, "Ngày lập lệnh").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_14_Step 17: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, "Người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.DIFFERENT_OWNER_NAME);

		log.info("TC_14_Step 18: Kiem tra so tien thi");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, "Số tiền"), addCommasToLong(TransferMoneyInVCB_Data.InputDataInFutureForOTP.TRANSFER_AMOUNT) + " VND");

		log.info("TC_14_Step 19: Kiem tra ngay hieu luc");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, "Ngày hiệu lực"), tommorrowDate);

		log.info("TC_14_Step 20: Kiem tra ghi chú");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, "Ghi chú"), "");

		log.info("TC_14_Step 21: Kiem tra trang thai hieu luc");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, "Trạng thái"), TransferMoneyStatus_Data.Output.WAITING_STATUS);

		log.info("TC_14_Step_22: Click quay lai");
		transStatus.clickToDynamicBackIcon(driver, "Chi tiết lệnh chuyển tiền");

	}

	@Test
	public void TC_15_HuyLenhGiaoDichTuongLaiXacThucOTP() {

		log.info("TC_15_Step_01: Click Huy Lenh");
		transStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "1", "com.VCB:id/tvHuyLenh");

		log.info("TC_15_Step_02: Click Dong Y");
		transStatus.clickToDynamicButton(driver, "Đồng ý");

		log.info("TC_15_Step_03: Kiem Tra Thong Bao hien thi");
		verifyTrue(transStatus.isDynamicMessageAndLabelTextDisplayed(driver, "Thông báo"));

		log.info("TC_15_Step_04: Kiem Tra Success hiển thị");
		verifyTrue(transStatus.isDynamicMessageAndLabelTextDisplayed(driver, "Success"));

		log.info("TC_15_Step_05: Click quay lai");
		transStatus.clickToDynamicButton(driver, "Đóng");

		log.info("TC_15_Step_06: Kiem tra trang thai Da Huy hien thi");
		verifyEquals(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvStatus"), TransferMoneyStatus_Data.Output.CANCEL_STATUS);

		log.info("TC_15_Step_07: click vao giao dich");
		transStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_15_Step_08: Kiem tra trang thai  Da Huy hien thi");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, "Trạng thái"), TransferMoneyStatus_Data.Output.CANCEL_STATUS);

		log.info("TC_15_Step_09: Click quay lai");
		transStatus.clickToDynamicBackIcon(driver, "Chi tiết lệnh chuyển tiền");

		log.info("TC_15_Step_10: Click quay lai");
		transStatus.clickToDynamicBackIcon(driver, "Trạng thái lệnh chuyển tiền");

	}

	@Test
	public void TC_16_ChuyenTienTuongLaiDenTaiKhoanKhacChuSoHuuCoPhiGiaoDichNguoiNhanTraVNDVaXacThucBangOTP() {
		log.info("TC_16_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_16_Step_02: Chon chuyển tiền ngay");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");

		log.info("TC_16_Step_03: Chon chuyển tiền ngay");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày tương lai");

		log.info("TC_16_Step_04:Click tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_16_Step_05: Chon tai khoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_16_Step_06: Lay so du tai khoan chuyen");
		String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		long beforeBalanceAmountOfAccount1 = convertMoneyToLong(beforeBalanceOfAccount1, "VND");

		log.info("TC_16_Step_07: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.DIFFERENT_OWNER_ACCOUNT, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_16_Step_08: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInFutureForOTP.TRANSFER_AMOUNT, "Số tiền");

		log.info("TC_16_Step_09: Nhap noi dung");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Nội dung");

		log.info("TC_16_Step_14: Click nguoi chuyen tra");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");

		log.info("TC_16_Step_15: Click nguoi chuyen tra");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Người nhận trả");

		log.info("TC_16_Step_10: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_16_Step_11: Kiem tra hinh thuc chuyen tien hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Hình thức chuyển tiền"), TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[2]);

		log.info("TC_16_Step_12: Kiem tra tai khoan nguon hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_16_Step_13: Kiem tra tai khoan dich hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tài khoản đích/ VND").contains(TransferMoneyInVCB_Data.InputDataInVCB.DIFFERENT_OWNER_ACCOUNT));

		log.info("TC_16_Step_14: Kiem tra so tien chuyen hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Số tiền"), (addCommasToLong(TransferMoneyInVCB_Data.InputDataInFutureForOTP.TRANSFER_AMOUNT)) + " VND");

		log.info("TC_16_Step_15: Kiem tra nguoi thanh toan phi chuyen bang OTP");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.InputDataInVCB.COST[1]));

		log.info("TC_16_Step_16: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_16_Step_17: Chon Phuong thuc nhap");
		transferInVCB.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");

		log.info("TC_16_Step_18: Chon SMS OTP");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_16_Step_19: Kiem tra so tien phi hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), TransferMoneyInVCB_Data.InputDataInVCB.TRANSFER_OTP_FEE_TO_OTHER_ACCOUNT_OWNER + " VND");

		log.info("TC_16_Step_20: Click Tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_16_Step_21: Nhap OTP");
		transferInVCB.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		log.info("TC_16_Step_22: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_16_Step_23: Kiem  tra giao dich thanh cong");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.SUCESSFULL_CREATED_ORDER));

		log.info("TC_16_Step_24: Lay thoi gian tao giao dich");
		transferTime = transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.SUCESSFULL_CREATED_ORDER, "4");

		log.info("TC_16_Step_25: Lay ma giao dich");
		transactionNumber = transferInVCB.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC_16_Step_26: Kiem tra ten nguoi thu huong hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.DIFFERENT_OWNER_NAME);

		log.info("TC_16_Step_27: Kiem tra tai khoan dich hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tài khoản đích"), TransferMoneyInVCB_Data.InputDataInVCB.DIFFERENT_OWNER_ACCOUNT);

		log.info("TC_16_Step_28: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_16_Step_29: Click thuc hien giao dich moi");
		transferInVCB.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_16_Step_30: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_16_Step_31: Chon tai ngoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_16_Step_32: Lay so du tai khoan chuyen");
		String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		long afterBalanceAmountOfAccount1 = convertMoneyToLong(afterBalanceOfAccount1, "VND");

		log.info("TC_16_Step_33: Kiem tra so du tai khoan chuyen sau khi thuc hien giao dich");
		verifyEquals(beforeBalanceAmountOfAccount1, afterBalanceAmountOfAccount1);

	}

	@Test
	public void TC_17_KiemTraChiTietGiaoDichChuyenTienTuongLaiCoPhiGiaoDichNguoiNhanTraEURVaXacThucBangOTP() {
		log.info("TC_17_Step_01 : Click  nut Back");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");

		log.info("TC_17_Step_02: Click vao Trang Thai lenh chuyen Tien");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicIcon(driver, "Trạng thái lệnh chuyển tiền");

		log.info("TC_17_Step_03: Click Tat Ca Cac Loai Giao Dich");
		transStatus = PageFactoryManager.getTransferMoneyStatusPageObject(driver);
		transStatus.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");

		log.info("TC_17_Step_04: Chon Chuyen Tien Trong VCB");
		transStatus.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày tương lai");

		log.info("TC_17_Step_05: Kiem tra from date hien thi");
		verifyEquals(transStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate"), getBackwardDate(29));

		log.info("TC_17_Step_06: Kiem tra todate hien thi");
		verifyEquals(transStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate"), today);

		log.info("TC_17_Step_07: Kiem tra Note hien thi");
		verifyTrue(transStatus.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyStatus_Data.Output.NOTE));

		log.info("TC_17_Step_08: CLick Tim Kiem");
		transStatus.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_17_Step 09: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));

		log.info("TC_17_Step 10: Kiem tra thoi gian tao dao dich");
		verifyTrue(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_17_Step 11: Kiem tra nguoi nhan");
		verifyEquals(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvContent"), TransferMoneyInVCB_Data.InputDataInVCB.DIFFERENT_OWNER_NAME);

		log.info("TC_17_Step 12: Kiem tra trang thai giao dich");
		verifyEquals(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvStatus"), TransferMoneyStatus_Data.Output.WAITING_STATUS);

		log.info("TC_17_Step 13: Kiem tra so tien giao dich");
		verifyEquals(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "1", "com.VCB:id/tvMoney"), addCommasToLong(TransferMoneyInVCB_Data.InputDataInFutureForOTP.TRANSFER_AMOUNT) + " VND");

		log.info("TC_17_Step 14: click vao giao dich");
		transStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_17_Step 15: Kiem tra ngay giao dich hien thi");
		verifyTrue(transStatus.getDynamicTextInTransactionDetail(driver, "Ngày lập lệnh").contains(transferTime.split(" ")[0]));

		log.info("TC_17_Step 16: Kiem tra thoi gian tao giao dich hien thi");
		verifyTrue(transStatus.getDynamicTextInTransactionDetail(driver, "Ngày lập lệnh").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_17_Step 17: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, "Người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.DIFFERENT_OWNER_NAME);

		log.info("TC_17_Step 18: Kiem tra so tien thi");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, "Số tiền"), addCommasToLong(TransferMoneyInVCB_Data.InputDataInFutureForOTP.TRANSFER_AMOUNT) + " VND");

		log.info("TC_17_Step 19: Kiem tra ngay hieu luc");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, "Ngày hiệu lực"), tommorrowDate);

		log.info("TC_17_Step 20: Kiem tra ghi chú");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, "Ghi chú"), "");

		log.info("TC_17_Step 21: Kiem tra trang thai hieu luc");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, "Trạng thái"), TransferMoneyStatus_Data.Output.WAITING_STATUS);

		log.info("TC_17_Step_22: Click quay lai");
		transStatus.clickToDynamicBackIcon(driver, "Chi tiết lệnh chuyển tiền");

	}

	@Test
	public void TC_18_HuyLenhGiaoDichTuongLaiXacThucOTP() {

		log.info("TC_18_Step_01: Click Huy Lenh");
		transStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "1", "com.VCB:id/tvHuyLenh");

		log.info("TC_18_Step_02: Click Dong Y");
		transStatus.clickToDynamicButton(driver, "Đồng ý");

		log.info("TC_18_Step_03: Kiem Tra Thong Bao hien thi");
		verifyTrue(transStatus.isDynamicMessageAndLabelTextDisplayed(driver, "Thông báo"));

		log.info("TC_18_Step_04: Kiem Tra Success hiển thị");
		verifyTrue(transStatus.isDynamicMessageAndLabelTextDisplayed(driver, "Success"));

		log.info("TC_18_Step_05: Click quay lai");
		transStatus.clickToDynamicButton(driver, "Đóng");

		log.info("TC_18_Step_06: Kiem tra trang thai Da Huy hien thi");
		verifyEquals(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvStatus"), TransferMoneyStatus_Data.Output.CANCEL_STATUS);

		log.info("TC_18_Step_07: click vao giao dich");
		transStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_18_Step_08: Kiem tra trang thai  Da Huy hien thi");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, "Trạng thái"), TransferMoneyStatus_Data.Output.CANCEL_STATUS);

		log.info("TC_18_Step_09: Click quay lai");
		transStatus.clickToDynamicBackIcon(driver, "Chi tiết lệnh chuyển tiền");

		log.info("TC_18_Step_10: Click quay lai");
		transStatus.clickToDynamicBackIcon(driver, "Trạng thái lệnh chuyển tiền");

	}

	@AfterClass(alwaysRun = true)

	public void afterClass() {
//		closeApp();
		service.stop();
	}

}