package vnpay.vietcombank.transfer_money_in_vcb;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.TransactionReportPageObject;
import pageObjects.TransferMoneyInVcbPageObject;
import pageObjects.TransferMoneyStatusPageObject;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransferMoneyInVCB_Data;

public class Transfer_Money_In_Future_And_Report extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferMoneyInVcbPageObject transferInVCB;
	private TransactionReportPageObject transReport;
	private TransferMoneyStatusPageObject transStatus;
	private String transferFee;
	private String transferTime;
	private String transactionNumber;
	double rate;
	String[] exchangeRateUSD;
	String today = getCurrentDay() + "/" + getCurrenMonth() + "/" + getCurrentYear();
	String tommorrowDate = getNextDay() + "/" + getMonth() + "/" + getYear();

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName) throws IOException, InterruptedException {
		startServer();
		log.info("Before class_Step_00: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);

		login = PageFactoryManager.getLoginPageObject(driver);

		log.info("Before class_Step_01: Click Allow Button");
		login.clickToDynamicAcceptButton("com.android.packageinstaller:id/permission_allow_button");

		log.info("Before class_Step_02: Dien so dien thoai ");
		login.inputToDynamicLogInTextBox(LogIn_Data.Login_Account.PHONE, "Tiếp tục");

		log.info("Before class_Step_03: Click Tiep tuc");
		login.clickToDynamicButton("Tiếp tục");

		log.info("TC_05_Step_04:  Dien mat khau");
		login.inputToDynamicInputBox(LogIn_Data.Login_Account.NEW_PASSWORD, LogIn_Data.UI.PASSWORD_LABEL);

		log.info("Before class_Step_05: Click tiep tuc");
		login.clickToDynamicButton("Tiếp tục");

		log.info("Before class_Step_06: Nhap OTP");
		login.inputToDynamicOtpOrPIN(LogIn_Data.Login_Account.OTP, "Tiếp tục");

		log.info("Before class_Step_07: Click tiep tuc");
		login.clickToDynamicButton("Tiếp tục");

		log.info("Before class_Step_08: Click Huy");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicButton("Hủy");

		log.info("Before class_Step_09: Click icon Dong");
		homePage.clickToDynamicCloseIcon("Kích hoạt tính năng mới");

		log.info("Before class_Step_10: Scroll den trang thai lenh chuyen tien");
		homePage.scrollToText("Trạng thái lệnh chuyển tiền");

	}

//	@Test
	public void TC_01_ChuyenTienTuongLaiCoPhiGiaoDichNguoiChuyenTraVNDVaXacThucBangOTP() {

		log.info("TC_01_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon("Chuyển tiền trong VCB");

		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);

		log.info("TC_01_Step_03: Chon chuyển tiền ngay");
		transferInVCB.clickToDynamicButtonLinkOrLinkText("Chuyển tiền ngay");

		log.info("TC_01_Step_03: Chon chuyển tiền ngay");
		transferInVCB.clickToDynamicButtonLinkOrLinkText("Chuyển tiền ngày tương lai");

		log.info("TC_01_Step_02:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown("Tài khoản nguồn");

		log.info("TC_01_Step_03: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT2);

		log.info("TC_01_Step_04: Lay so du tai khoan dich");
		String beforeBalanceOfAccount2 = transferInVCB.getDynamicTextInTextView("Số dư khả dụng");
		long beforeBalanceAmountOfAccount2 = convertMoneyToLong(beforeBalanceOfAccount2, "VND");

		log.info("TC_01_Step_05:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown("Tài khoản nguồn");

		log.info("TC_01_Step_06: Chon tai khoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_01_Step_07: Lay so du tai khoan chuyen");
		String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTextView("Số dư khả dụng");
		long beforeBalanceAmountOfAccount1 = convertMoneyToLong(beforeBalanceOfAccount1, "VND");

		log.info("TC_01_Step_08: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT2, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_01_Step_14: Kiem tra Ngay hieu luc mac dinh");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(tommorrowDate));

		log.info("TC_01_Step_09: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(TransferMoneyInVCB_Data.InputDataInFuture.TRANSFER_AMOUNT, "Số tiền");

		log.info("TC_01_Step_10: Nhap noi dung");
		transferInVCB.inputToDynamicInputBox(TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Nội dung");

		log.info("TC_01_Step_11: Click tiep tuc");
		transferInVCB.clickToDynamicButton("Tiếp tục");

		log.info("TC_01_Step_12: Kiem tra hinh thuc chuyen tien hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Hình thức chuyển tiền"), TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[2]);

		log.info("TC_01_Step_13: Kiem tra tai khoan nguon hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Tài khoản nguồn"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_01_Step_14: Kiem tra tai khoan dich hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTextView("Tài khoản đích / VND").contains(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT2));

		log.info("TC_01_Step_15: Kiem tra so tien chuyen hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Số tiền"), addCommasToLong(TransferMoneyInVCB_Data.InputDataInFuture.TRANSFER_AMOUNT) + " VND");

		log.info("TC_01_Step_14: Kiem tra Ngay hieu luc hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Ngày hiệu lực"), tommorrowDate);

		log.info("TC_01_Step_17: Kiem tra phuong thuc tra phi");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(TransferMoneyInVCB_Data.InputDataInVCB.COST[0]));

		log.info("TC_01_Step_19: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_01_Step_20: Chon Phuong thuc nhap");
		transferInVCB.clickToDynamicDropDown("Chọn phương thức xác thực");

		log.info("TC_01_Step_21: Chon SMS OTP");
		transferInVCB.clickToDynamicButtonLinkOrLinkText("SMS OTP");

		log.info("TC_01_Step_16: Kiem tra so tien phi hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTextView("Số tiền phí").contains(TransferMoneyInVCB_Data.InputDataInFuture.OTP_FEE + " VND"));

		log.info("TC_01_Step_22: Click Tiep tuc");
		transferInVCB.clickToDynamicButton("Tiếp tục");

		log.info("TC_01_Step_23: Nhap OTP");
		transferInVCB.inputToDynamicOtpOrPIN(LogIn_Data.Login_Account.OTP, "Tiếp tục");

		log.info("TC_01_Step_24: Click tiep tuc");
		transferInVCB.clickToDynamicButton("Tiếp tục");

		log.info("TC_01_Step_25: Kiem  tra giao dich thanh cong");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(TransferMoneyInVCB_Data.Output.SUCESSFULL_CREATED_ORDER));

		log.info("TC_01_Step_26: Kiem tra so tien chuyen");
		verifyEquals(transferInVCB.getDynamicTransferTimeAndMoney(TransferMoneyInVCB_Data.Output.SUCESSFULL_CREATED_ORDER, "3"), addCommasToLong(TransferMoneyInVCB_Data.InputDataInFuture.TRANSFER_AMOUNT) + " VND");

		log.info("TC_01_Step_26: Lay thoi gian tao giao dich");
		transferTime = transferInVCB.getDynamicTransferTimeAndMoney(TransferMoneyInVCB_Data.Output.SUCESSFULL_CREATED_ORDER, "4");

		log.info("TC_01_Step_27: Lay ma giao dich");
		transactionNumber = transferInVCB.getDynamicTextInTextView("Mã giao dịch");

		log.info("TC_01_Step_28: Kiem tra ten nguoi thu huong hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Tên người thụ hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);

		log.info("TC_01_Step_29: Kiem tra tai khoan dich hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Tài khoản đích"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT2);

		log.info("TC_01_Step_30: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_01_Step_31: Click thuc hien giao dich moi");
		transferInVCB.clickToDynamicButton("Thực hiện giao dịch mới");

		log.info("TC_01_Step_32: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown("Tài khoản nguồn");

		log.info("TC_01_Step_33: Chon tai ngoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_01_Step_34: Lay so du tai khoan chuyen");
		String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTextView("Số dư khả dụng");
		long afterBalanceAmountOfAccount1 = convertMoneyToLong(afterBalanceOfAccount1, "VND");

		log.info("TC_01_Step_35: Kiem tra so du tai khoan chuyen sau khi thuc hien giao dich");
		verifyEquals(beforeBalanceAmountOfAccount1, afterBalanceAmountOfAccount1);

		log.info("TC_01_Step_36: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown("Tài khoản nguồn");

		log.info("TC_01_Step_37: Chon tai khoan nguoi nhan");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT2);

		log.info("TC_01_Step_38: Lay so du kha dung tai khoan nhan");
		String afterBalanceOfAccount2 = transferInVCB.getDynamicTextInTextView("Số dư khả dụng");

		log.info("TC_01_Step_39: Kiem tra so du tai khoan nhan sau khi nhan tien");
		long afterBalanceAmountOfAccount2 = convertMoneyToLong(afterBalanceOfAccount2, "VND");
		verifyEquals(beforeBalanceAmountOfAccount2, afterBalanceAmountOfAccount2);

	}

//	@Test
	public void TC_02_KiemTraTrạngThaiLenhChuyenTienCuaGiaoDichTuongLaiXacThucOTP() {
		log.info("TC_02_Step_01 : Click  nut Back");
		transferInVCB.clickToDynamicBackIcon("Chuyển tiền trong Vietcombank");

		log.info("TC_02_Step_02: Click vao More Icon");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicIcon("Trạng thái lệnh chuyển tiền");

		log.info("TC_02_Step_04: Click Tat Ca Cac Loai Giao Dich");
		transStatus = PageFactoryManager.getTransferMoneyStatusPageObject(driver);
		transStatus.clickToDynamicButtonLinkOrLinkText("Chuyển tiền định kỳ");

		log.info("TC_02_Step_05: Chon Chuyen Tien Trong VCB");
		transStatus.clickToDynamicButtonLinkOrLinkText("Chuyển tiền ngày tương lai");

		log.info("TC_02_Step_12: Kiem tra from date hien thi");
		verifyEquals(transStatus.getTextInDynamicDropdownOrDateTimePicker("com.VCB:id/tvFromDate"), today);

		transStatus.clickToDynamicDropdownAndDateTimePicker("");
		log.info("TC_02_Step_12: Kiem tra todate hien thi");
		verifyEquals(transStatus.getTextInDynamicDropdownOrDateTimePicker("com.VCB:id/tvToDate"), today);

//		log.info("TC_02_Step_08: CLick Tim Kiem");
//		transStatus.clickToDynamicButton(driver, "Tìm kiếm");
//
//		log.info("TC_02_Step_35: Click quay lai");
//		transStatus.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");
//
//		log.info("TC_02_Step_36: Click quay lai");
//		transStatus.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

		log.info("TC_02_Step_37: Click quay lai");
		transStatus.navigateBack();
	}

//	@Test
	public void TC_03_ChuyenTienTuongLaiCoPhiGiaoDichNguoiNhanTraVNDVaXacThucBangOTP() {

		log.info("TC_01_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon("Chuyển tiền trong VCB");

		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);

		log.info("TC_01_Step_03: Chon chuyển tiền ngay");
		transferInVCB.clickToDynamicButtonLinkOrLinkText("Chuyển tiền ngay");

		log.info("TC_01_Step_03: Chon chuyển tiền ngay");
		transferInVCB.clickToDynamicButtonLinkOrLinkText("Chuyển tiền ngày tương lai");

		log.info("TC_01_Step_02:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown("Tài khoản nguồn");

		log.info("TC_01_Step_03: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_01_Step_04: Lay so du tai khoan dich");
		String beforeBalanceOfAccount2 = transferInVCB.getDynamicTextInTextView("Số dư khả dụng");
		long beforeBalanceAmountOfAccount2 = convertMoneyToLong(beforeBalanceOfAccount2, "VND");

		log.info("TC_01_Step_05:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown("Tài khoản nguồn");

		log.info("TC_01_Step_06: Chon tai khoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT2);

		log.info("TC_01_Step_07: Lay so du tai khoan chuyen");
		String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTextView("Số dư khả dụng");
		long beforeBalanceAmountOfAccount1 = convertMoneyToLong(beforeBalanceOfAccount1, "VND");

		log.info("TC_01_Step_08: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_01_Step_14: Kiem tra Ngay hieu luc mac dinh");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(tommorrowDate));

		log.info("TC_01_Step_09: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(TransferMoneyInVCB_Data.InputDataInFuture.TRANSFER_AMOUNT, "Số tiền");

		log.info("TC_01_Step_10: Nhap noi dung");
		transferInVCB.inputToDynamicInputBox(TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Nội dung");

		log.info("TC_01_Step_06: Click nguoi chuyen tra");
		transferInVCB.clickToDynamicButtonLinkOrLinkText("Phí giao dịch người chuyển trả");

		log.info("TC_01_Step_06: Click nguoi chuyen tra");
		transferInVCB.clickToDynamicButtonLinkOrLinkText("Người nhận trả");

		log.info("TC_01_Step_11: Click tiep tuc");
		transferInVCB.clickToDynamicButton("Tiếp tục");

		log.info("TC_01_Step_12: Kiem tra hinh thuc chuyen tien hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Hình thức chuyển tiền"), TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[2]);

		log.info("TC_01_Step_13: Kiem tra tai khoan nguon hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Tài khoản nguồn"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT2);

		log.info("TC_01_Step_14: Kiem tra tai khoan dich hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTextView("Tài khoản đích / VND").contains(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1));

		log.info("TC_01_Step_15: Kiem tra so tien chuyen hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Số tiền"), addCommasToLong(TransferMoneyInVCB_Data.InputDataInFuture.TRANSFER_AMOUNT) + " VND");

		log.info("TC_01_Step_14: Kiem tra Ngay hieu luc hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Ngày hiệu lực"), tommorrowDate);

		log.info("TC_01_Step_17: Kiem tra phuong thuc tra phi");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(TransferMoneyInVCB_Data.InputDataInVCB.COST[1]));

		log.info("TC_01_Step_19: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_01_Step_20: Chon Phuong thuc nhap");
		transferInVCB.clickToDynamicDropDown("Chọn phương thức xác thực");

		log.info("TC_01_Step_21: Chon SMS OTP");
		transferInVCB.clickToDynamicButtonLinkOrLinkText("SMS OTP");

		log.info("TC_01_Step_16: Kiem tra so tien phi hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTextView("Số tiền phí").contains(TransferMoneyInVCB_Data.InputDataInFuture.OTP_FEE + " VND"));

		log.info("TC_01_Step_22: Click Tiep tuc");
		transferInVCB.clickToDynamicButton("Tiếp tục");

		log.info("TC_01_Step_23: Nhap OTP");
		transferInVCB.inputToDynamicOtpOrPIN(LogIn_Data.Login_Account.OTP, "Tiếp tục");

		log.info("TC_01_Step_24: Click tiep tuc");
		transferInVCB.clickToDynamicButton("Tiếp tục");

		log.info("TC_01_Step_25: Kiem  tra giao dich thanh cong");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(TransferMoneyInVCB_Data.Output.SUCESSFULL_CREATED_ORDER));

		log.info("TC_01_Step_26: Kiem tra so tien chuyen");
		verifyEquals(transferInVCB.getDynamicTransferTimeAndMoney(TransferMoneyInVCB_Data.Output.SUCESSFULL_CREATED_ORDER, "3"), addCommasToLong(TransferMoneyInVCB_Data.InputDataInFuture.TRANSFER_AMOUNT) + " VND");

		log.info("TC_01_Step_26: Lay thoi gian tao giao dich");
		transferTime = transferInVCB.getDynamicTransferTimeAndMoney(TransferMoneyInVCB_Data.Output.SUCESSFULL_CREATED_ORDER, "4");

		log.info("TC_01_Step_27: Lay ma giao dich");
		transactionNumber = transferInVCB.getDynamicTextInTextView("Mã giao dịch");

		log.info("TC_01_Step_28: Kiem tra ten nguoi thu huong hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Tên người thụ hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);

		log.info("TC_01_Step_29: Kiem tra tai khoan dich hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Tài khoản đích"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_01_Step_30: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_01_Step_31: Click thuc hien giao dich moi");
		transferInVCB.clickToDynamicButton("Thực hiện giao dịch mới");

		log.info("TC_01_Step_32: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown("Tài khoản nguồn");

		log.info("TC_01_Step_33: Chon tai ngoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT2);

		log.info("TC_01_Step_34: Lay so du tai khoan chuyen");
		String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTextView("Số dư khả dụng");
		long afterBalanceAmountOfAccount1 = convertMoneyToLong(afterBalanceOfAccount1, "VND");

		log.info("TC_01_Step_35: Kiem tra so du tai khoan chuyen sau khi thuc hien giao dich");
		verifyEquals(beforeBalanceAmountOfAccount1, afterBalanceAmountOfAccount1);

		log.info("TC_01_Step_36: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown("Tài khoản nguồn");

		log.info("TC_01_Step_37: Chon tai khoan nguoi nhan");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_01_Step_38: Lay so du kha dung tai khoan nhan");
		String afterBalanceOfAccount2 = transferInVCB.getDynamicTextInTextView("Số dư khả dụng");

		log.info("TC_01_Step_39: Kiem tra so du tai khoan nhan sau khi nhan tien");
		long afterBalanceAmountOfAccount2 = convertMoneyToLong(afterBalanceOfAccount2, "VND");
		verifyEquals(beforeBalanceAmountOfAccount2, afterBalanceAmountOfAccount2);

	}

//	@Test
	public void TC_04_KiemTraChiTietGiaoDichChuyenTienTuongLaiCoPhiGiaoDichNguoiNhanTraVNDVaXacThucBangOTP() {

		log.info("TC_02_Step_01 : Click  nut Back");
		transferInVCB.clickToDynamicBackIcon("Chuyển tiền trong Vietcombank");

		log.info("TC_02_Step_02: Click vao More Icon");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicIcon("Trạng thái lệnh chuyển tiền");

		log.info("TC_02_Step_04: Click Tat Ca Cac Loai Giao Dich");
		transStatus = PageFactoryManager.getTransferMoneyStatusPageObject(driver);
		transStatus.clickToDynamicButtonLinkOrLinkText("Chuyển tiền định kỳ");

		log.info("TC_02_Step_05: Chon Chuyen Tien Trong VCB");
		transStatus.clickToDynamicButtonLinkOrLinkText("Chuyển tiền ngày tương lai");

		log.info("TC_02_Step_12: Kiem tra from date hien thi");
		verifyEquals(transStatus.getTextInDynamicDropdownOrDateTimePicker("com.VCB:id/tvFromDate"), today);

		log.info("TC_02_Step_12: Kiem tra todate hien thi");
		verifyEquals(transStatus.getTextInDynamicDropdownOrDateTimePicker("com.VCB:id/tvToDate"), tommorrowDate);

//		log.info("TC_02_Step_08: CLick Tim Kiem");
//		transStatus.clickToDynamicButton(driver, "Tìm kiếm");
//
//		log.info("TC_02_Step_35: Click quay lai");
//		transStatus.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");
//
//		log.info("TC_02_Step_36: Click quay lai");
//		transStatus.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

		log.info("TC_02_Step_37: Click quay lai");
		transStatus.navigateBack();
	}

//	@Test
	public void TC_05_ChuyenTienNgayCoPhiGiaoDichNguoiChuyenTraUSDVaXacThucBangOTP() {

		log.info("TC05_Step 01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon("Chuyển tiền trong VCB");

		log.info("TC05_Step 02: Click chon tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicDropDown("Tài khoản nguồn");

		log.info("TC05_Step 03: Chon account 1");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC05_Step 04: Lay so du kha dung tai khoan 1");
		String beforeBalanceOfAccount2 = transferInVCB.getDynamicTextInTextView("Số dư khả dụng");
		double beforeBalanceAmountOfAccount2 = convertMoneyToDouble(beforeBalanceOfAccount2, "VND");

		log.info("TC05_Step 05 : Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown("Tài khoản nguồn");

		log.info("TC05_Step 06: Chon USD account");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(TransferMoneyInVCB_Data.InputDataInVCB.USD_ACCOUNT);

		log.info("TC05_Step 07: Lay so du tai khoan USD");
		String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTextView("Số dư khả dụng");
		double beforeBalanceAmountOfAccount1 = convertMoneyToDouble(beforeBalanceOfAccount1, "USD");

		log.info("TC05_Step 08: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1, "Nhập/chọn tài khoản nhận VND");

		log.info("TC05_Step 09: Nhap so tien can chuyen");
		transferInVCB.inputToDynamicInputBox(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER, "Số tiền");

		log.info("TC05_Step 10: Lay ti gia quy doi");
		String exchangeRate = transferInVCB.getDynamicTextInTextView("Tỷ giá quy đổi tham khảo");
		exchangeRateUSD = exchangeRate.split(" ~ ");
		rate = convertMoneyToDouble(exchangeRateUSD[1], "VND");

		log.info("TC05_Step 11: Chon phuong thuc xac thuc");
		transferInVCB.clickToDynamicButtonLinkOrLinkText("Phí giao dịch người chuyển trả");

		log.info("TC05_Step 12: Chon phi giao dich nguoi chuyen tra");
		transferInVCB.clickToDynamicButtonLinkOrLinkText("Người chuyển trả");

		log.info("TC05_Step 13:  Nhap noi dung");
		transferInVCB.inputToDynamicInputBox(TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Nội dung");

		log.info("TC05_Step 14: Click tiep tuc");
		transferInVCB.clickToDynamicButton("Tiếp tục");

		log.info("TC05_Step 15: Kiem tra hinh thuc chuyen tien hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Hình thức chuyển tiền"), TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[0]);

		log.info("TC05_Step 16: Kiem tra tai khoan nguon hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Tài khoản nguồn"), TransferMoneyInVCB_Data.InputDataInVCB.USD_ACCOUNT);

		log.info("TC05_Step 17: Kiem tra tai khoan dich hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTextView("Tài khoản đích / VND").contains(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1));

		log.info("TC05_Step 18: Kiem tra so tien chuyen hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTextView("Số tiền (USD)").contains(addCommasToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER) + "USD"));

		log.info("TC05_Step 19: Kiem tra Nguoi chuyen tra hien thi");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(TransferMoneyInVCB_Data.InputDataInVCB.COST[0]));

		log.info("TC05_Step 20: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC05_Step 21: Chon phuong thuc xac thuc");
		transferInVCB.clickToDynamicDropDown("Chọn phương thức xác thực");

		log.info("TC05_Step 22: Chon SMS OTP");
		transferInVCB.clickToDynamicButtonLinkOrLinkText("SMS OTP");

		log.info("TC05_Step 23: Kiem tra so tien phi hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTextView("Số tiền phí").contains(TransferMoneyInVCB_Data.InputDataInVCB.USD_PAYMENT_BY_OTP_FEE + " USD"));

		log.info("TC05_Step 24: Lay tien phi chuyen ");
		transferFee = transferInVCB.getDynamicTextInTextView("Số tiền phí");
		double newTransferFee = convertMoneyToDouble(transferFee, "USD");

		log.info("TC05_Step 25: Click tiep tuc");
		transferInVCB.clickToDynamicButton("Tiếp tục");

		log.info("TC05_Step 26: Dien OTP");
		transferInVCB.inputToDynamicOtpOrPIN(LogIn_Data.Login_Account.OTP, "Tiếp tục");

		log.info("TC05_Step 27: Click tiep tuc");
		transferInVCB.clickToDynamicButton("Tiếp tục");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE));

		log.info("TC05_Step 28: Lay thoi gian tao");
		transferTime = transferInVCB.getDynamicTransferTimeAndMoney(TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE, "4");

		log.info("TC05_Step 29: Kiem tra so tien chuyen hien thi");
		verifyEquals(transferInVCB.getDynamicTransferTimeAndMoney(TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE, "3"), addCommasToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " USD");

		log.info("TC05_Step 30: Kiem tra ten nguoi thu huong hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Tên người thụ hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);

		log.info("TC05_Step 31: Kiem tra tai khoan dich hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Tài khoản đích"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC05_Step 32: Lay ma giao dich");
		transactionNumber = transferInVCB.getDynamicTextInTextView("Mã giao dịch");

		log.info("TC05_Step 33: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC05_Step 34: Click thuc hien giao dich moi");
		transferInVCB.clickToDynamicButton("Thực hiện giao dịch mới");

		log.info("TC05_Step 35: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown("Tài khoản nguồn");

		log.info("TC05_Step 36:Click USD account");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(TransferMoneyInVCB_Data.InputDataInVCB.USD_ACCOUNT);

		log.info("TC05_Step 37: Lay so du kha dung tai khoan USD");
		String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTextView("Số dư khả dụng");
		double afterBalanceAmountOfAccount1 = convertMoneyToDouble(afterBalanceOfAccount1, "USD");

		log.info("TC05_Step 38: Convert so tien chuyen");
		double transferMoney = convertMoneyToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER, "USD");

		log.info("TC05_Step 39: Kiem tra so du tai khoan USD sau khi chuyen tien");
		verifyEquals(beforeBalanceAmountOfAccount1 - transferMoney - newTransferFee, afterBalanceAmountOfAccount1);

		log.info("TC05_Step 40: Kiem tra tai khoan nguon");
		transferInVCB.clickToDynamicDropDown("Tài khoản nguồn");

		log.info("TC05_Step 41: Click chon tai khoan chuyen den");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC05_Step 41: Lay so du kha dung tai khoan chuyen den");
		String afterBalanceOfAccount2 = transferInVCB.getDynamicTextInTextView("Số dư khả dụng");
		double afterBalanceAmountOfAccount2 = convertMoneyToDouble(afterBalanceOfAccount2, "VND");

		log.info("TC05_Step 43: Kiem tra so du tai khoan duoc chuyen den");
		verifyEquals(beforeBalanceAmountOfAccount2 + transferMoney * rate, afterBalanceAmountOfAccount2);

	}

//	@Test
	public void TC_06_KiemTraChiTietGiaoDichChuyenTienNgayCoPhiGiaoDichNguoiChuyenTraUSDVaXacThucBangOTP() throws InterruptedException {

		log.info("TC06_Step 01 : Click  nut Back");
		transferInVCB.clickToDynamicBackIcon("Chuyển tiền trong Vietcombank");

		log.info("TC06_Step 02: Click vao More Icon");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicBottomMenu("com.VCB:id/menu_5");

		log.info("TC06_Step 03: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText("Báo cáo giao dịch");

		log.info("TC06_Step 04: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText("Tất cả các loại giao dịch");

		log.info("TC06_Step 05:Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText("Chuyển tiền trong Vietcombank");

		log.info("TC06_Step 06: Click Chon Tai Khoan");
		transReport.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvSelectAcc");

		log.info("TC06_Step 07: Chon 1 tai Khoan");
		transReport.clickToDynamicButtonLinkOrLinkText(TransferMoneyInVCB_Data.InputDataInVCB.USD_ACCOUNT);

		log.info("TC06_Step 08: CLick Tim Kiem");
		transReport.clickToDynamicButton("Tìm kiếm");

		log.info("TC06_Step 09: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transReport.getTextInDynamicTransaction("0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));

		log.info("TC06_Step 10: Kiem tra thoi gian tao dao dich");
		verifyTrue(transReport.getTextInDynamicTransaction("0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC06_Step 11: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransaction("0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC06_Step 12: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransaction("1", "com.VCB:id/tvMoney"), ("- " + addCommasToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " USD"));

		log.info("TC06_Step 13: Click vao giao dich");
		transReport.clickToDynamicTransaction("0", "com.VCB:id/tvDate");

		log.info("TC06_Step 14: Kiem tra ngay giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTextView("Thời gian giao dịch").contains(transferTime.split(" ")[0]));

		log.info("TC06_Step 15: Kiem tra thoi gian tao giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTextView("Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC06_Step 16:Kiem tra so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTextView("Số lệnh giao dịch"), transactionNumber);

		log.info("TC06_Step 17: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTextView("Tài khoản/thẻ trích nợ"), TransferMoneyInVCB_Data.InputDataInVCB.USD_ACCOUNT);

		log.info("TC06_Step 18: Kiem tra so tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTextView("Tài khoản ghi có"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC06_Step 19: Kiem tra so tien giao dich hien thi ");
		verifyTrue(transReport.getDynamicTextInTextView("Số tiền giao dịch").equals(addCommasToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " USD"));

		log.info("TC06_Step 20: Kiem tra so tien quy doi hien thi ");
		String changedMoney = convertMoneyToLong(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER, "USD") * convertMoneyToLong(exchangeRateUSD[1], "VND") + "";
		verifyTrue(transReport.getDynamicTextInTextView("Số tiền quy đổi").contains(addCommasToLong(changedMoney) + " VND"));

		log.info("TC06_Step 21: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Tên người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);

		log.info("TC06_Step 22: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Phí giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.COST[0]);

		log.info("TC06_Step 23: Kiem tra so tien phi hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Số tiền phí"), TransferMoneyInVCB_Data.InputDataInVCB.PAYMENT_BY_OTP_FEE + " VND");

		log.info("TC06_Step 24: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTextView("Loại giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.TRANSFER_TYPE);

		log.info("TC06_Step 25: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTextView("Nội dung giao dịch").contains(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC06_Step 26: Click Back icon");
		transReport.clickToDynamicBackIcon("Chi tiết giao dịch");

		log.info("TC06_Step 27: Chon Tai Khoan");
		transReport.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvSelectAcc");

		log.info("TC06_Step 28: Click chon tai khoan nhan");
		transReport.clickToDynamicButtonLinkOrLinkText(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC06_Step 29: Click Tim Kiem");
		transReport.clickToDynamicButton("Tìm kiếm");

		log.info("TC06_Step 30: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transReport.getTextInDynamicTransaction("0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));

		log.info("TC06_Step 31: Kiem tra thoi gian tao giao dich hiển thị");
		verifyTrue(transReport.getTextInDynamicTransaction("0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC06_Step 32: Kiem tra noi dung giao dich hien thi");
		verifyTrue(transReport.getTextInDynamicTransaction("0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC06_Step 33: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransaction("1", "com.VCB:id/tvMoney"), ("+ " + addCommasToLong(changedMoney) + " VND"));

		log.info("TC06_Step 34: Click vao bao bao giao dich chi tiet");
		transReport.clickToDynamicTransaction("0", "com.VCB:id/tvDate");

		log.info("TC06_Step 35: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTextView("Thời gian giao dịch").contains(transferTime.split(" ")[0]));

		log.info("TC06_Step 36: Kiem tra thoi gian tao giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTextView("Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC06_Step 37: Kiem tra so giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Số lệnh giao dịch"), transactionNumber);

		log.info("TC06_Step 38:Kiem tra tai khoan trich no hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Tài khoản/thẻ trích nợ"), TransferMoneyInVCB_Data.InputDataInVCB.USD_ACCOUNT);

		log.info("TC06_Step 39: Kiem tra tai khoan ghi co hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Tài khoản ghi có"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC06_Step 40: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTextView("Số tiền giao dịch").contains(addCommasToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " USD"));

		log.info("TC06_Step 41: Kiem tra so tien quy doi hien thi ");

		verifyTrue(transReport.getDynamicTextInTextView("Số tiền quy đổi").contains(addCommasToLong(changedMoney) + " VND"));

		log.info("TC06_Step 42: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Tên người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);

		log.info("TC06_Step 43: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Phí giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.COST[0]);

		log.info("TC06_Step 44:Kiem tra so tien phi hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Số tiền phí"), TransferMoneyInVCB_Data.InputDataInVCB.PAYMENT_BY_OTP_FEE + " VND");

		log.info("TC06_Step 45:Kiem tra noi dung giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTextView("Nội dung giao dịch").contains(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC06_Step 46: Kiem tra loaigiao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Loại giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.TRANSFER_TYPE);

		log.info("TC06_Step 47: Click quay lai");
		transferInVCB.clickToDynamicBackIcon("Chi tiết giao dịch");

		log.info("TC06_Step 48: Click quay lai");
		transferInVCB.clickToDynamicBackIcon("Báo cáo giao dịch");

		log.info("TC06_Step 49: Click quay lai");
		transferInVCB.navigateBack();
	}

//	@Test
	public void TC_07_ChuyenTienNgayCoPhiGiaoDichNguoiNhanTraEURVaXacThucBangOTP() {

		log.info("TC_07_Step 01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon("Chuyển tiền trong VCB");

		log.info("TC_07_Step 02: Click chon tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicDropDown("Tài khoản nguồn");

		log.info("TC_07_Step 03: Chon account 1");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_07_Step 04: Lay so du kha dung tai khoan 1");
		String beforeBalanceOfAccount2 = transferInVCB.getDynamicTextInTextView("Số dư khả dụng");
		double beforeBalanceAmountOfAccount2 = convertMoneyToDouble(beforeBalanceOfAccount2, "VND");

		log.info("TC_07_Step 05: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown("Tài khoản nguồn");

		log.info("TC_07_Step 06: Chon USD account");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(TransferMoneyInVCB_Data.InputDataInVCB.EUR_ACCOUNT);

		log.info("TC_07_Step 07: Lay so du tai khoan USD");
		String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTextView("Số dư khả dụng");
		double beforeBalanceAmountOfAccount1 = convertMoneyToDouble(beforeBalanceOfAccount1, "EUR");

		log.info("TC_07_Step 08: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_07_Step 09: Nhap so tien can chuyen");
		transferInVCB.inputToDynamicInputBox(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER, "Số tiền");

		log.info("TC_07_Step 10: Lay ti gia quy doi");
		String exchangeRate = transferInVCB.getDynamicTextInTextView("Tỷ giá quy đổi tham khảo");
		exchangeRateUSD = exchangeRate.split(" ~ ");
		rate = convertMoneyToDouble(exchangeRateUSD[1], "VND");

		log.info("TC_07_Step 11: Chon phuong thuc xac thuc");
		transferInVCB.clickToDynamicButtonLinkOrLinkText("Phí giao dịch người chuyển trả");

		log.info("TC_07_Step 12: Chon phi giao dich nguoi nhan tra");
		transferInVCB.clickToDynamicButtonLinkOrLinkText("Người nhận trả");

		log.info("TC_07_Step 13:  Nhap noi dung");
		transferInVCB.inputToDynamicInputBox(TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Nội dung");

		log.info("TC_07_Step 14: Click tiep tuc");
		transferInVCB.clickToDynamicButton("Tiếp tục");

		log.info("TC_07_Step 15: Kiem tra hinh thuc chuyen tien hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Hình thức chuyển tiền"), TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[0]);

		log.info("TC_07_Step 16: Kiem tra tai khoan nguon hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Tài khoản nguồn"), TransferMoneyInVCB_Data.InputDataInVCB.EUR_ACCOUNT);

		log.info("TC_07_Step 17: Kiem tra tai khoan dich hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTextView("Tài khoản đích / VND").contains(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1));

		log.info("TC_07_Step 18: Kiem tra so tien chuyen hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTextView("Số tiền (EUR)").contains(addCommasToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER) + "EUR"));

		log.info("TC_07_Step 19: Kiem tra Nguoi nhan tra hien thi");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(TransferMoneyInVCB_Data.InputDataInVCB.COST[1]));

		log.info("TC_07_Step 20: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_07_Step 21: Chon phuong thuc xac thuc");
		transferInVCB.clickToDynamicDropDown("Chọn phương thức xác thực");

		log.info("TC_07_Step 22: Chon SMS OTP");
		transferInVCB.clickToDynamicButtonLinkOrLinkText("SMS OTP");

		log.info("TC_07_Step 23: Kiem tra so tien phi hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTextView("Số tiền phí").contains(TransferMoneyInVCB_Data.InputDataInVCB.EUR_PAYMENT_BY_OTP_FEE + " EUR"));

		log.info("TC_07_Step 24: Lay tien phi chuyen ");
		transferFee = transferInVCB.getDynamicTextInTextView("Số tiền phí");

		log.info("TC_07_Step 25: Click tiep tuc");
		transferInVCB.clickToDynamicButton("Tiếp tục");

		log.info("TC_07_Step 26: Dien OTP");
		transferInVCB.inputToDynamicOtpOrPIN(LogIn_Data.Login_Account.OTP, "Tiếp tục");

		log.info("TC_07_Step 27: Click tiep tuc");
		transferInVCB.clickToDynamicButton("Tiếp tục");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE));

		log.info("TC_07_Step 28: Lay thoi gian tao");
		transferTime = transferInVCB.getDynamicTransferTimeAndMoney(TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE, "4");

		log.info("TC_07_Step 29: Kiem tra so tien chuyen hien thi");
		verifyEquals(transferInVCB.getDynamicTransferTimeAndMoney(TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE, "3"), addCommasToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " EUR");

		log.info("TC_07_Step 30: Kiem tra ten nguoi thu huong hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Tên người thụ hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);

		log.info("TC_07_Step 31: Kiem tra tai khoan dich hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Tài khoản đích"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_07_Step 32: Lay ma giao dich");
		transactionNumber = transferInVCB.getDynamicTextInTextView("Mã giao dịch");

		log.info("TC_07_Step 33: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_07_Step 34: Click thuc hien giao dich moi");
		transferInVCB.clickToDynamicButton("Thực hiện giao dịch mới");

		log.info("TC_07_Step 35: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown("Tài khoản nguồn");

		log.info("TC_07_Step 36:Click EUR account");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(TransferMoneyInVCB_Data.InputDataInVCB.EUR_ACCOUNT);

		log.info("TC_07_Step 37: Lay so du kha dung tai khoan EUR");
		String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTextView("Số dư khả dụng");
		double afterBalanceAmountOfAccount1 = convertMoneyToDouble(afterBalanceOfAccount1, "EUR");

		log.info("TC_07_Step 38: Convert so tien chuyen");
		double transferMoney = convertMoneyToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER, "EUR");

		log.info("TC_07_Step 39: Kiem tra so du tai khoan USD sau khi chuyen tien");
		verifyEquals(beforeBalanceAmountOfAccount1 - transferMoney, afterBalanceAmountOfAccount1);

		log.info("TC_07_Step 40: Kiem tra tai khoan nguon");
		transferInVCB.clickToDynamicDropDown("Tài khoản nguồn");

		log.info("TC_07_Step 41: Click chon tai khoan chuyen den");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_07_Step 42: Lay so du kha dung tai khoan chuyen den");
		String afterBalanceOfAccount2 = transferInVCB.getDynamicTextInTextView("Số dư khả dụng");
		double afterBalanceAmountOfAccount2 = convertMoneyToDouble(afterBalanceOfAccount2, "VND");

		log.info("TC_07_Step 43: Kiem tra so du tai khoan duoc chuyen den");
		double transferFee = convertMoneyToDouble(TransferMoneyInVCB_Data.InputDataInVCB.PAYMENT_BY_OTP_FEE, "VND");
		verifyEquals(beforeBalanceAmountOfAccount2 + transferMoney * rate - transferFee, afterBalanceAmountOfAccount2);

	}

//	@Test
	public void TC_08_KiemTraChiTietGiaoDichChuyenTienNgayCoPhiGiaoDichNguoiNhanTraEURVaXacThucBangOTP() throws InterruptedException {

		log.info("TC_08_Step 01 : Click  nut Back");
		transferInVCB.clickToDynamicBackIcon("Chuyển tiền trong Vietcombank");

		log.info("TC_08_Step 02: Click vao More Icon");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicBottomMenu("com.VCB:id/menu_5");

		log.info("TC_08_Step 03: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText("Báo cáo giao dịch");

		log.info("TC_08_Step 04: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText("Tất cả các loại giao dịch");

		log.info("TC_08_Step 05:Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText("Chuyển tiền trong Vietcombank");

		log.info("TC_08_Step 06: Click Chon Tai Khoan");
		transReport.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvSelectAcc");

		log.info("TC_08_Step 07: Chon 1 tai Khoan");
		transReport.clickToDynamicButtonLinkOrLinkText(TransferMoneyInVCB_Data.InputDataInVCB.EUR_ACCOUNT);

		log.info("TC_08_Step 08: CLick Tim Kiem");
		transReport.clickToDynamicButton("Tìm kiếm");

		log.info("TC_08_Step 09: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transReport.getTextInDynamicTransaction("0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));

		log.info("TC_08_Step 10: Kiem tra thoi gian tao dao dich");
		verifyTrue(transReport.getTextInDynamicTransaction("0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_08_Step 11: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransaction("0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_08_Step 12: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransaction("1", "com.VCB:id/tvMoney"), ("- " + addCommasToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " EUR"));

		log.info("TC_08_Step 13: Click vao giao dich");
		transReport.clickToDynamicTransaction("0", "com.VCB:id/tvDate");

		log.info("TC_08_Step 14: Kiem tra ngay giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTextView("Thời gian giao dịch").contains(transferTime.split(" ")[0]));

		log.info("TC_08_Step 15: Kiem tra thoi gian tao giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTextView("Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_08_Step 16:Kiem tra so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTextView("Số lệnh giao dịch"), transactionNumber);

		log.info("TC_08_Step 17: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTextView("Tài khoản/thẻ trích nợ"), TransferMoneyInVCB_Data.InputDataInVCB.EUR_ACCOUNT);

		log.info("TC_08_Step 18: Kiem tra so tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTextView("Tài khoản ghi có"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_08_Step 19: Kiem tra so tien giao dich hien thi ");
		verifyTrue(transReport.getDynamicTextInTextView("Số tiền giao dịch").equals(addCommasToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " EUR"));

		log.info("TC_08_Step 20: Kiem tra so tien quy doi hien thi ");
		String changedMoney = convertMoneyToLong(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER, "EUR") * convertMoneyToLong(exchangeRateUSD[1], "VND") + "";
		verifyTrue(transReport.getDynamicTextInTextView("Số tiền quy đổi").contains(addCommasToLong(changedMoney) + " VND"));

		log.info("TC_08_Step 21: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Tên người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);

		log.info("TC_08_Step 22: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Phí giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.COST[1]);

		log.info("TC_08_Step 23: Kiem tra so tien phi hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Số tiền phí"), TransferMoneyInVCB_Data.InputDataInVCB.PAYMENT_BY_OTP_FEE + " VND");

		log.info("TC_08_Step 24: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTextView("Loại giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.TRANSFER_TYPE);

		log.info("TC_08_Step 25: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTextView("Nội dung giao dịch").contains(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_08_Step 26: Click Back icon");
		transReport.clickToDynamicBackIcon("Chi tiết giao dịch");

		log.info("TC_08_Step 27: Chon Tai Khoan");
		transReport.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvSelectAcc");

		log.info("TC_08_Step 28: Click chon tai khoan nhan");
		transReport.clickToDynamicButtonLinkOrLinkText(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_08_Step 29: Click Tim Kiem");
		transReport.clickToDynamicButton("Tìm kiếm");

		log.info("TC_08_Step 30: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transReport.getTextInDynamicTransaction("0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));

		log.info("TC_08_Step 31: Kiem tra thoi gian tao giao dich hiển thị");
		verifyTrue(transReport.getTextInDynamicTransaction("0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_08_Step 32: Kiem tra noi dung giao dich hien thi");
		verifyTrue(transReport.getTextInDynamicTransaction("0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_08_Step 33: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransaction("1", "com.VCB:id/tvMoney"), ("+ " + addCommasToLong(changedMoney) + " VND"));

		log.info("TC_08_Step 34: Click vao bao bao giao dich chi tiet");
		transReport.clickToDynamicTransaction("0", "com.VCB:id/tvDate");

		log.info("TC_08_Step 35: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTextView("Thời gian giao dịch").contains(transferTime.split(" ")[0]));

		log.info("TC_08_Step 36: Kiem tra thoi gian tao giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTextView("Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_08_Step 37: Kiem tra so giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Số lệnh giao dịch"), transactionNumber);

		log.info("TC_08_Step 38:Kiem tra tai khoan trich no hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Tài khoản/thẻ trích nợ"), TransferMoneyInVCB_Data.InputDataInVCB.EUR_ACCOUNT);

		log.info("TC_08_Step 39: Kiem tra tai khoan ghi co hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Tài khoản ghi có"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_08_Step 40: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTextView("Số tiền giao dịch").contains(addCommasToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " EUR"));

		log.info("TC_08_Step 41: Kiem tra so tien quy doi hien thi ");

		verifyTrue(transReport.getDynamicTextInTextView("Số tiền quy đổi").contains(addCommasToLong(changedMoney) + " VND"));

		log.info("TC_08_Step 42: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Tên người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);

		log.info("TC_08_Step 43: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Phí giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.COST[1]);

		log.info("TC_08_Step 44:Kiem tra so tien phi hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Số tiền phí"), TransferMoneyInVCB_Data.InputDataInVCB.PAYMENT_BY_OTP_FEE + " VND");

		log.info("TC_08_Step 45:Kiem tra noi dung giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTextView("Nội dung giao dịch").contains(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_08_Step 46: Kiem tra loaigiao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Loại giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.TRANSFER_TYPE);

		log.info("TC_08_Step 47: Click quay lai");
		transferInVCB.clickToDynamicBackIcon("Chi tiết giao dịch");

		log.info("TC_08_Step 48: Click quay lai");
		transferInVCB.clickToDynamicBackIcon("Báo cáo giao dịch");

		log.info("TC_08_Step 49: Click quay lai");
		transferInVCB.navigateBack();
	}

//	@Test
	public void TC_09_ChuyenTienNgayDenTaiKhoanKhacChuSoHuuCoPhiGiaoDichNguoiChuyenTraVNDVaXacThucBangOTP() {

		log.info("TC_09_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon("Chuyển tiền trong VCB");

		log.info("TC_09_Step_02:Click tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicDropDown("Tài khoản nguồn");

		log.info("TC_09_Step_03: Chon tai khoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_09_Step_04: Lay so du tai khoan chuyen");
		String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTextView("Số dư khả dụng");
		long beforeBalanceAmountOfAccount1 = convertMoneyToLong(beforeBalanceOfAccount1, "VND");

		log.info("TC_09_Step_05: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(TransferMoneyInVCB_Data.InputDataInVCB.DIFFERENT_OWNER_ACCOUNT, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_09_Step_06: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(TransferMoneyInVCB_Data.InputDataInVCB.MONEY, "Số tiền");

		log.info("TC_09_Step_07: Nhap noi dung");
		transferInVCB.inputToDynamicInputBox(TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Nội dung");

		log.info("TC_09_Step_08: Click tiep tuc");
		transferInVCB.clickToDynamicButton("Tiếp tục");

		log.info("TC_09_Step_09: Kiem tra hinh thuc chuyen tien hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Hình thức chuyển tiền"), TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[0]);

		log.info("TC_09_Step_10: Kiem tra tai khoan nguon hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Tài khoản nguồn"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_09_Step_11: Kiem tra tai khoan dich hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTextView("Tài khoản đích / VND").contains(TransferMoneyInVCB_Data.InputDataInVCB.DIFFERENT_OWNER_ACCOUNT));

		log.info("TC_09_Step_12: Kiem tra so tien chuyen hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTextView("Số tiền (VND)").contains(addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.MONEY)));

		log.info("TC_09_Step_14: Kiem tra phuong thuc thanh toan bang OTP");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(TransferMoneyInVCB_Data.InputDataInVCB.COST[0]));

		log.info("TC_09_Step_16: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_09_Step_17: Chon Phuong thuc nhap");
		transferInVCB.clickToDynamicDropDown("Chọn phương thức xác thực");

		log.info("TC_09_Step_18: Chon SMS OTP");
		transferInVCB.clickToDynamicButtonLinkOrLinkText("SMS OTP");

		log.info("TC_09_Step_13: Kiem tra so tien phi hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTextView("Số tiền phí").contains(TransferMoneyInVCB_Data.InputDataInVCB.TRANSFER_FEE_TO_OTHER_ACCOUNT_OWNER + " VND"));

		log.info("TC_09_Step_15: Lay so tien phi");
		transferFee = transferInVCB.getDynamicTextInTextView("Số tiền phí");
		long newTransferFee = convertMoneyToLong(transferFee, "VND");

		log.info("TC_09_Step_19: Click Tiep tuc");
		transferInVCB.clickToDynamicButton("Tiếp tục");

		log.info("TC_09_Step_20: Nhap OTP");
		transferInVCB.inputToDynamicOtpOrPIN(LogIn_Data.Login_Account.OTP, "Tiếp tục");

		log.info("TC_09_Step_21: Click tiep tuc");
		transferInVCB.clickToDynamicButton("Tiếp tục");

		log.info("TC_09_Step_21: Kiem  tra giao dich thanh cong");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE));

		log.info("TC_09_Step_23: Lay thoi gian tao giao dich");
		transferTime = transferInVCB.getDynamicTransferTimeAndMoney(TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE, "4");

		log.info("TC_09_Step_24: Lay ma giao dich");
		transactionNumber = transferInVCB.getDynamicTextInTextView("Mã giao dịch");

		log.info("TC_09_Step_25: Kiem tra ten nguoi thu huong hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Tên người thụ hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.DIFFERENT_OWNER_NAME);

		log.info("TC_09_Step_26: Kiem tra tai khoan dich hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Tài khoản đích"), TransferMoneyInVCB_Data.InputDataInVCB.DIFFERENT_OWNER_ACCOUNT);

		log.info("TC_09_Step_27: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_09_Step_28: Click thuc hien giao dich moi");
		transferInVCB.clickToDynamicButton("Thực hiện giao dịch mới");

		log.info("TC_09_Step_29: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown("Tài khoản nguồn");

		log.info("TC_09_Step_30: Chon tai ngoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_09_Step_31: Lay so du tai khoan chuyen");
		String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTextView("Số dư khả dụng");
		long afterBalanceAmountOfAccount1 = convertMoneyToLong(afterBalanceOfAccount1, "VND");

		log.info("TC_09_Step_32: Kiem tra so du tai khoan chuyen sau khi thuc hien giao dich");
		long transferMoney = convertMoneyToLong(TransferMoneyInVCB_Data.InputDataInVCB.MONEY, "VND");
		verifyEquals(beforeBalanceAmountOfAccount1 - transferMoney - newTransferFee, afterBalanceAmountOfAccount1);

	}

//	@Test
	public void TC_10_KiemTraChiTietGiaoDichChuyenTienNgayDenTaiKhoanKhacChuSoHuuCoPhiGiaoDichNguoiChuyenTraVNDVaXacThucBangOTP() {
		log.info("TC_10_Step_01 : Click  nut Back");
		transferInVCB.clickToDynamicBackIcon("Chuyển tiền trong Vietcombank");

		log.info("TC_10_Step_02: Click vao More Icon");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicBottomMenu("com.VCB:id/menu_5");

		log.info("TC_10_Step_03: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText("Báo cáo giao dịch");

		log.info("TC_10_Step_04: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText("Tất cả các loại giao dịch");

		log.info("TC_10_Step_05: Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText("Chuyển tiền trong Vietcombank");

		log.info("TC_10_Step_06: Click Chon Tai Khoan");
		transReport.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvSelectAcc");

		log.info("TC_10_Step_07: Chon tai Khoan chuyen");
		transReport.clickToDynamicButtonLinkOrLinkText(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_10_Step_08: CLick Tim Kiem");
		transReport.clickToDynamicButton("Tìm kiếm");

		log.info("TC_10_Step_09: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transReport.getTextInDynamicTransaction("0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));

		log.info("TC_10_Step_10: Kiem tra thoi gian tao dao dich");
		verifyTrue(transReport.getTextInDynamicTransaction("0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_10_Step_11: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransaction("0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_10_Step_12: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransaction("1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.MONEY) + " VND"));

		log.info("TC_10_Step_13: Click vao giao dich");
		transReport.clickToDynamicTransaction("0", "com.VCB:id/tvDate");

		log.info("TC_10_Step_14: Kiem tra ngay giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTextView("Thời gian giao dịch").contains(transferTime.split(" ")[0]));

		log.info("TC_10_Step_15: Kiem tra thoi gian tao giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTextView("Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_10_Step_16: Kiem tra thoi gian tao giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Số lệnh giao dịch"), transactionNumber);

		log.info("TC_10_Step_17: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTextView("Tài khoản/thẻ trích nợ"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_10_Step_18: Kiem tra so tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTextView("Tài khoản ghi có"), TransferMoneyInVCB_Data.InputDataInVCB.DIFFERENT_OWNER_ACCOUNT);

		log.info("TC_10_Step_19: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTextView("Số tiền giao dịch").contains(addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.MONEY) + " VND"));

		log.info("TC_10_Step_20: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Tên người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.DIFFERENT_OWNER_NAME);

		log.info("TC_10_Step_21: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Phí giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.COST[0]);

		log.info("TC_10_Step_22: Kiem tra so tien phi hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Số tiền phí"), TransferMoneyInVCB_Data.InputDataInVCB.TRANSFER_FEE_TO_OTHER_ACCOUNT_OWNER + " VND");

		log.info("TC_10_Step_23: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTextView("Loại giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.TRANSFER_TYPE);

		log.info("TC_10_Step_24: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTextView("Nội dung giao dịch").contains(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_10_Step_25: Click Back icon");
		transReport.clickToDynamicBackIcon("Chi tiết giao dịch");

		log.info("TC_10_Step_36: Click quay lai");
		transferInVCB.clickToDynamicBackIcon("Báo cáo giao dịch");

		log.info("TC_10_Step_37: Click quay lai");
		transferInVCB.navigateBack();
	}

//	@Test
	public void TC_11_ChuyenTienNgayDenTaiKhoanKhacChuSoHuuCoPhiGiaoDichNguoiNhanTraVNDVaXacThucBangOTP() {

		log.info("TC_11_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon("Chuyển tiền trong VCB");

		log.info("TC_11_Step_02:Click tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicDropDown("Tài khoản nguồn");

		log.info("TC_11_Step_03: Chon tai khoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_11_Step_04: Lay so du tai khoan chuyen");
		String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTextView("Số dư khả dụng");
		long beforeBalanceAmountOfAccount1 = convertMoneyToLong(beforeBalanceOfAccount1, "VND");

		log.info("TC_11_Step_05: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(TransferMoneyInVCB_Data.InputDataInVCB.DIFFERENT_OWNER_ACCOUNT, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_11_Step_06: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(TransferMoneyInVCB_Data.InputDataInVCB.MONEY, "Số tiền");

		log.info("TC_11_Step_07: Nhap noi dung");
		transferInVCB.inputToDynamicInputBox(TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Nội dung");

		log.info("TC_03_Step_10: Click phi giao dich nguoi chuyen tra");
		transferInVCB.clickToDynamicButtonLinkOrLinkText("Phí giao dịch người chuyển trả");

		log.info("TC_03_Step_11: Chon nguoi nhan tra");
		transferInVCB.clickToDynamicButtonLinkOrLinkText("Người nhận trả");

		log.info("TC_11_Step_08: Click tiep tuc");
		transferInVCB.clickToDynamicButton("Tiếp tục");

		log.info("TC_11_Step_09: Kiem tra hinh thuc chuyen tien hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Hình thức chuyển tiền"), TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[0]);

		log.info("TC_11_Step_10: Kiem tra tai khoan nguon hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Tài khoản nguồn"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_11_Step_11: Kiem tra tai khoan dich hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTextView("Tài khoản đích / VND").contains(TransferMoneyInVCB_Data.InputDataInVCB.DIFFERENT_OWNER_ACCOUNT));

		log.info("TC_11_Step_12: Kiem tra so tien chuyen hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTextView("Số tiền (VND)").contains(addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.MONEY)));

		log.info("TC_11_Step_14: Kiem tra phuong thuc thanh toan bang OTP");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(TransferMoneyInVCB_Data.InputDataInVCB.COST[1]));

		log.info("TC_11_Step_16: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_11_Step_17: Chon Phuong thuc nhap");
		transferInVCB.clickToDynamicDropDown("Chọn phương thức xác thực");

		log.info("TC_11_Step_18: Chon SMS OTP");
		transferInVCB.clickToDynamicButtonLinkOrLinkText("SMS OTP");

		log.info("TC_11_Step_13: Kiem tra so tien phi hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTextView("Số tiền phí").contains(TransferMoneyInVCB_Data.InputDataInVCB.TRANSFER_FEE_TO_OTHER_ACCOUNT_OWNER + " VND"));

		log.info("TC_11_Step_19: Click Tiep tuc");
		transferInVCB.clickToDynamicButton("Tiếp tục");

		log.info("TC_11_Step_20: Nhap OTP");
		transferInVCB.inputToDynamicOtpOrPIN(LogIn_Data.Login_Account.OTP, "Tiếp tục");

		log.info("TC_11_Step_21: Click tiep tuc");
		transferInVCB.clickToDynamicButton("Tiếp tục");

		log.info("TC_11_Step_21: Kiem  tra giao dich thanh cong");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE));

		log.info("TC_11_Step_23: Lay thoi gian tao giao dich");
		transferTime = transferInVCB.getDynamicTransferTimeAndMoney(TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE, "4");

		log.info("TC_11_Step_24: Lay ma giao dich");
		transactionNumber = transferInVCB.getDynamicTextInTextView("Mã giao dịch");

		log.info("TC_11_Step_25: Kiem tra ten nguoi thu huong hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Tên người thụ hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.DIFFERENT_OWNER_NAME);

		log.info("TC_11_Step_26: Kiem tra tai khoan dich hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Tài khoản đích"), TransferMoneyInVCB_Data.InputDataInVCB.DIFFERENT_OWNER_ACCOUNT);

		log.info("TC_11_Step_27: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_11_Step_28: Click thuc hien giao dich moi");
		transferInVCB.clickToDynamicButton("Thực hiện giao dịch mới");

		log.info("TC_11_Step_29: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown("Tài khoản nguồn");

		log.info("TC_11_Step_30: Chon tai ngoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_11_Step_31: Lay so du tai khoan chuyen");
		String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTextView("Số dư khả dụng");
		long afterBalanceAmountOfAccount1 = convertMoneyToLong(afterBalanceOfAccount1, "VND");

		log.info("TC_11_Step_32: Kiem tra so du tai khoan chuyen sau khi thuc hien giao dich");
		long transferMoney = convertMoneyToLong(TransferMoneyInVCB_Data.InputDataInVCB.MONEY, "VND");
		verifyEquals(beforeBalanceAmountOfAccount1 - transferMoney, afterBalanceAmountOfAccount1);

	}

//	@Test
	public void TC_12_KiemTraChiTietGiaoDichChuyenTienNgayDenTaiKhoanKhacChuSoHuuCoPhiGiaoDichNguoiNhanTraVNDVaXacThucBangOTP() {
		log.info("TC_12_Step_01 : Click  nut Back");
		transferInVCB.clickToDynamicBackIcon("Chuyển tiền trong Vietcombank");

		log.info("TC_12_Step_02: Click vao More Icon");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicBottomMenu("com.VCB:id/menu_5");

		log.info("TC_12_Step_03: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText("Báo cáo giao dịch");

		log.info("TC_12_Step_04: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText("Tất cả các loại giao dịch");

		log.info("TC_12_Step_05: Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText("Chuyển tiền trong Vietcombank");

		log.info("TC_12_Step_06: Click Chon Tai Khoan");
		transReport.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvSelectAcc");

		log.info("TC_12_Step_07: Chon tai Khoan chuyen");
		transReport.clickToDynamicButtonLinkOrLinkText(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_12_Step_08: CLick Tim Kiem");
		transReport.clickToDynamicButton("Tìm kiếm");

		log.info("TC_12_Step_09: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transReport.getTextInDynamicTransaction("0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));

		log.info("TC_12_Step_10: Kiem tra thoi gian tao dao dich");
		verifyTrue(transReport.getTextInDynamicTransaction("0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_12_Step_11: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransaction("0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_12_Step_12: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransaction("1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.MONEY) + " VND"));

		log.info("TC_12_Step_13: Click vao giao dich");
		transReport.clickToDynamicTransaction("0", "com.VCB:id/tvDate");

		log.info("TC_12_Step_14: Kiem tra ngay giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTextView("Thời gian giao dịch").contains(transferTime.split(" ")[0]));

		log.info("TC_12_Step_15: Kiem tra thoi gian tao giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTextView("Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_12_Step_16: Kiem tra thoi gian tao giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Số lệnh giao dịch"), transactionNumber);

		log.info("TC_12_Step_17: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTextView("Tài khoản/thẻ trích nợ"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_12_Step_18: Kiem tra so tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTextView("Tài khoản ghi có"), TransferMoneyInVCB_Data.InputDataInVCB.DIFFERENT_OWNER_ACCOUNT);

		log.info("TC_12_Step_19: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTextView("Số tiền giao dịch").contains(addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.MONEY) + " VND"));

		log.info("TC_12_Step_20: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Tên người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.DIFFERENT_OWNER_NAME);

		log.info("TC_12_Step_21: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Phí giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.COST[1]);

		log.info("TC_12_Step_22: Kiem tra so tien phi hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Số tiền phí"), TransferMoneyInVCB_Data.InputDataInVCB.TRANSFER_FEE_TO_OTHER_ACCOUNT_OWNER + " VND");

		log.info("TC_12_Step_23: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTextView("Loại giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.TRANSFER_TYPE);

		log.info("TC_12_Step_24: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTextView("Nội dung giao dịch").contains(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_12_Step_25: Click Back icon");
		transReport.clickToDynamicBackIcon("Chi tiết giao dịch");

		log.info("TC_12_Step_36: Click quay lai");
		transferInVCB.clickToDynamicBackIcon("Báo cáo giao dịch");

		log.info("TC_12_Step_37: Click quay lai");
		transferInVCB.navigateBack();
	}

//	@Test
	public void TC_13_ChuyenTienNgayCoPhiGiaoDichNguoiChuyenTraVNDVaXacThucBangMatKhau() {

		log.info("TC_13_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon("Chuyển tiền trong VCB");

		log.info("TC_13_Step_02:Click tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicDropDown("Tài khoản nguồn");

		log.info("TC_13_Step_03: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT2);

		log.info("TC_13_Step_04: Lay so du tai khoan dich");
		String beforeBalanceOfAccount2 = transferInVCB.getDynamicTextInTextView("Số dư khả dụng");
		long beforeBalanceAmountOfAccount2 = convertMoneyToLong(beforeBalanceOfAccount2, "VND");

		log.info("TC_13_Step_05:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown("Tài khoản nguồn");

		log.info("TC_13_Step_06: Chon tai khoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_13_Step_07: Lay so du tai khoan chuyen");
		String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTextView("Số dư khả dụng");
		long beforeBalanceAmountOfAccount1 = convertMoneyToLong(beforeBalanceOfAccount1, "VND");

		log.info("TC_13_Step_08: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT2, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_13_Step_09: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(TransferMoneyInVCB_Data.InputDataInVCB.MONEY, "Số tiền");

		log.info("TC_13_Step_10: Nhap noi dung");
		transferInVCB.inputToDynamicInputBox(TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Nội dung");

		log.info("TC_13_Step_11: Click tiep tuc");
		transferInVCB.clickToDynamicButton("Tiếp tục");

		log.info("TC_13_Step_12: Kiem tra hinh thuc chuyen tien hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Hình thức chuyển tiền"), TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[0]);

		log.info("TC_13_Step_13: Kiem tra tai khoan nguon hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Tài khoản nguồn"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_13_Step_14: Kiem tra tai khoan dich hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTextView("Tài khoản đích / VND").contains(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT2));

		log.info("TC_13_Step_15: Kiem tra so tien chuyen hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTextView("Số tiền (VND)").contains(addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.MONEY)));

		log.info("TC_13_Step_17: Kiem tra phuong thuc thanh toan bang OTP");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(TransferMoneyInVCB_Data.InputDataInVCB.COST[0]));

		log.info("TC_13_Step_19: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_06_Step_20: Chon Phuong thuc nhap");
		transferInVCB.clickToDynamicDropDown("Chọn phương thức xác thực");

		log.info("TC_13_Step_21: Chon SMS OTP");
		transferInVCB.clickToDynamicButtonLinkOrLinkText("Mật khẩu đăng nhập");

		log.info("TC_13_Step_16: Kiem tra so tien phi hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTextView("Số tiền phí").contains(TransferMoneyInVCB_Data.InputDataInVCB.PAYMENT_BY_PASSWORD_FEE + " VND"));

		log.info("TC_13_Step_18: Lay so tien phi");
		transferFee = transferInVCB.getDynamicTextInTextView("Số tiền phí");
		long newTransferFee = convertMoneyToLong(transferFee, "VND");

		log.info("TC_13_Step_22: Click Tiep tuc");
		transferInVCB.clickToDynamicButton("Tiếp tục");

		log.info("TC_13_Step_23: Nhap OTP");
		transferInVCB.inputToDynamicPopupPasswordInput(LogIn_Data.Login_Account.NEW_PASSWORD, "Tiếp tục");

		log.info("TC_13_Step_24: Click tiep tuc");
		transferInVCB.clickToDynamicButton("Tiếp tục");

		log.info("TC_13_Step_25: Kiem  tra giao dich thanh cong");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE));

		log.info("TC_13_Step_26: Lay thoi gian tao giao dich");
		transferTime = transferInVCB.getDynamicTransferTimeAndMoney(TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE, "4");

		log.info("TC_13_Step_27: Lay ma giao dich");
		transactionNumber = transferInVCB.getDynamicTextInTextView("Mã giao dịch");

		log.info("TC_13_Step_28: Kiem tra ten nguoi thu huong hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Tên người thụ hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);

		log.info("TC_13_Step_29: Kiem tra tai khoan dich hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Tài khoản đích"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT2);

		log.info("TC_13_Step_30: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_13_Step_31: Click thuc hien giao dich moi");
		transferInVCB.clickToDynamicButton("Thực hiện giao dịch mới");

		log.info("TC_13_Step_32: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown("Tài khoản nguồn");

		log.info("TC_13_Step_33: Chon tai ngoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_13_Step_34: Lay so du tai khoan chuyen");
		String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTextView("Số dư khả dụng");
		long afterBalanceAmountOfAccount1 = convertMoneyToLong(afterBalanceOfAccount1, "VND");

		log.info("TC_13_Step_35: Kiem tra so du tai khoan chuyen sau khi thuc hien giao dich");
		long transferMoney = convertMoneyToLong(TransferMoneyInVCB_Data.InputDataInVCB.MONEY, "VND");
		verifyEquals(beforeBalanceAmountOfAccount1 - transferMoney - newTransferFee, afterBalanceAmountOfAccount1);

		log.info("TC_13_Step_36: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown("Tài khoản nguồn");

		log.info("TC_13_Step_37: Chon tai khoan nguoi nhan");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT2);

		log.info("TC_13_Step_38: Lay so du kha dung tai khoan nhan");
		String afterBalanceOfAccount2 = transferInVCB.getDynamicTextInTextView("Số dư khả dụng");

		log.info("TC_13_Step_39: Kiem tra so du tai khoan nhan sau khi nhan tien");
		long afterBalanceAmountOfAccount2 = convertMoneyToLong(afterBalanceOfAccount2, "VND");
		verifyEquals(beforeBalanceAmountOfAccount2 + transferMoney, afterBalanceAmountOfAccount2);

	}

//	@Test
	public void TC_14_KiemTraChiTietGiaoDichChuyenTienNgayCoPhiGiaoDichNguoiChuyenTraVNDVaXacThucBangMatKhau() {
		log.info("TC_14_Step_01 : Click  nut Back");
		transferInVCB.clickToDynamicBackIcon("Chuyển tiền trong Vietcombank");

		log.info("TC_14_Step_02: Click vao More Icon");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicBottomMenu("com.VCB:id/menu_5");

		log.info("TC_14_Step_03: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText("Báo cáo giao dịch");

		log.info("TC_14_Step_04: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText("Tất cả các loại giao dịch");

		log.info("TC_14_Step_05: Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText("Chuyển tiền trong Vietcombank");

		log.info("TC_14_Step_06: Click Chon Tai Khoan");
		transReport.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvSelectAcc");

		log.info("TC_14_Step_07: Chon tai Khoan chuyen");
		transReport.clickToDynamicButtonLinkOrLinkText(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_14_Step_08: CLick Tim Kiem");
		transReport.clickToDynamicButton("Tìm kiếm");

		log.info("TC_14_Step_09: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transReport.getTextInDynamicTransaction("0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));

		log.info("TC_14_Step_10: Kiem tra thoi gian tao dao dich");
		verifyTrue(transReport.getTextInDynamicTransaction("0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_14_Step_11: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransaction("0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_14_Step_12: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransaction("1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.MONEY) + " VND"));

		log.info("TC_14_Step_13: Click vao giao dich");
		transReport.clickToDynamicTransaction("0", "com.VCB:id/tvDate");

		log.info("TC_14_Step_14: Kiem tra ngay giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTextView("Thời gian giao dịch").contains(transferTime.split(" ")[0]));

		log.info("TC_14_Step_15: Kiem tra thoi gian tao giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTextView("Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_14_Step_16: Kiem tra thoi gian tao giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Số lệnh giao dịch"), transactionNumber);

		log.info("TC_14_Step_17: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTextView("Tài khoản/thẻ trích nợ"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_14_Step_18: Kiem tra so tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTextView("Tài khoản ghi có"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT2);

		log.info("TC_14_Step_19: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTextView("Số tiền giao dịch").contains(addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.MONEY) + " VND"));

		log.info("TC_14_Step_20: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Tên người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);

		log.info("TC_14_Step_21: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Phí giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.COST[0]);

		log.info("TC_14_Step_22: Kiem tra so tien phi hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Số tiền phí"), TransferMoneyInVCB_Data.InputDataInVCB.PAYMENT_BY_PASSWORD_FEE + " VND");

		log.info("TC_14_Step_23: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTextView("Loại giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.TRANSFER_TYPE);

		log.info("TC_14_Step_24: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTextView("Nội dung giao dịch").contains(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_14_Step_25: Click Back icon");
		transReport.clickToDynamicBackIcon("Chi tiết giao dịch");

		log.info("TC_14_Step_26: Chon Tai Khoan");
		transReport.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvSelectAcc");

		log.info("TC_14_Step_27: Click chon tai khoan nhan");
		transReport.clickToDynamicButtonLinkOrLinkText(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT2);

		log.info("TC_14_Step_28: Click Tim Kiem");
		transReport.clickToDynamicButton("Tìm kiếm");

		log.info("TC_14_Step_29: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transReport.getTextInDynamicTransaction("0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));

		log.info("TC_14_Step_20: Kiem tra thoi gian tao giao dich hiển thị");
		verifyTrue(transReport.getTextInDynamicTransaction("0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_14_Step_21: Kiem tra noi dung giao dich hien thi");
		verifyTrue(transReport.getTextInDynamicTransaction("0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_14_Step_22: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransaction("1", "com.VCB:id/tvMoney"), ("+ " + addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.MONEY) + " VND"));

		log.info("TC_14_Step_23: Click vao bao bao giao dich chi tiet");
		transReport.clickToDynamicTransaction("0", "com.VCB:id/tvDate");

		log.info("TC_14_Step_24: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTextView("Thời gian giao dịch").contains(transferTime.split(" ")[0]));

		log.info("TC_14_Step_25: Kiem tra thoi gian tao giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTextView("Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_14_Step_26: Kiem tra so giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Số lệnh giao dịch"), transactionNumber);

		log.info("TC_14_Step_27: Kiem tra tai khoan trich no hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Tài khoản/thẻ trích nợ"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_14_Step_28: Kiem tra tai khoan ghi co hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Tài khoản ghi có"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT2);

		log.info("TC_14_Step_29: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTextView("Số tiền giao dịch").contains(addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.MONEY) + " VND"));

		log.info("TC_14_Step_30: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Tên người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);

		log.info("TC_14_Step_31: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Phí giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.COST[0]);

		log.info("TC_14_Step_32: Kiem tra so tien phi hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Số tiền phí"), TransferMoneyInVCB_Data.InputDataInVCB.PAYMENT_BY_PASSWORD_FEE + " VND");

		log.info("TC_14_Step_33: Kiem tra noi dung giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Loại giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.TRANSFER_TYPE);

		log.info("TC_14_Step_34: Kiem tra loaigiao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTextView("Nội dung giao dịch").contains(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_14_Step_35: Click quay lai");
		transferInVCB.clickToDynamicBackIcon("Chi tiết giao dịch");

		log.info("TC_14_Step_36: Click quay lai");
		transferInVCB.clickToDynamicBackIcon("Báo cáo giao dịch");

		log.info("TC_14_Step_37: Click quay lai");
		transferInVCB.navigateBack();
	}

//	@Test
	public void TC_15_ChuyenTienNgayCoPhiGiaoDichNguoiNhanTraVNDVaXacThucBangMatKhau() {

		log.info("TC_15_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon("Chuyển tiền trong VCB");

		log.info("TC_15_Step_02:Click tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicDropDown("Tài khoản nguồn");

		log.info("TC_15_Step_03: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_15_Step_04: Lay so du tai khoan dich");
		String beforeBalanceOfAccount2 = transferInVCB.getDynamicTextInTextView("Số dư khả dụng");
		long beforeBalanceAmountOfAccount2 = convertMoneyToLong(beforeBalanceOfAccount2, "VND");

		log.info("TC_15_Step_05:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown("Tài khoản nguồn");

		log.info("TC_15_Step_06: Chon tai khoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT2);

		log.info("TC_15_Step_07: Lay so du tai khoan chuyen");
		String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTextView("Số dư khả dụng");
		long beforeBalanceAmountOfAccount1 = convertMoneyToLong(beforeBalanceOfAccount1, "VND");

		log.info("TC_15_Step_08: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_15_Step_09: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(TransferMoneyInVCB_Data.InputDataInVCB.MONEY, "Số tiền");

		log.info("TC_15_Step_10: Click phi giao dich nguoi chuyen tra");
		transferInVCB.clickToDynamicButtonLinkOrLinkText("Phí giao dịch người chuyển trả");

		log.info("TC_15_Step_11: Chon nguoi nhan tra");
		transferInVCB.clickToDynamicButtonLinkOrLinkText("Người nhận trả");

		log.info("TC_15_Step_12: Nhap noi dung");
		transferInVCB.inputToDynamicInputBox(TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Nội dung");

		log.info("TC_15_Step_13: Click tiep tuc");
		transferInVCB.clickToDynamicButton("Tiếp tục");

		log.info("TC_15_Step_14:  Kiem tra hinh thuc chuyen tien hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Hình thức chuyển tiền"), TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[0]);

		log.info("TC_15_Step_15: Kiem tra tai khoan nguon hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Tài khoản nguồn"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT2);

		log.info("TC_15_Step_16: Kiem tra tai khoan dich hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTextView("Tài khoản đích / VND").contains(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1));

		log.info("TC_15_Step_17: Kiem tra so tien chuyen hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTextView("Số tiền (VND)").contains(addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.MONEY)));

		log.info("TC_15_Step_19: Kiem tra nguoi nhan tra");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(TransferMoneyInVCB_Data.InputDataInVCB.COST[1]));

		log.info("TC_15_Step_21:Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_15_Step_22: Chon Phuong thuc xac thuc ");
		transferInVCB.clickToDynamicDropDown("Chọn phương thức xác thực");

		log.info("TC_15_Step_23: Chon Mat khau dang nhap");
		transferInVCB.clickToDynamicButtonLinkOrLinkText("Mật khẩu đăng nhập");

		log.info("TC_15_Step_18: Kiem tra so tien phi hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTextView("Số tiền phí").contains(TransferMoneyInVCB_Data.InputDataInVCB.PAYMENT_BY_PASSWORD_FEE + " VND"));

		log.info("TC_15_Step_20:  Lay so tien phi giao dich");
		transferFee = transferInVCB.getDynamicTextInTextView("Số tiền phí");
		long newTransferFee = convertMoneyToLong(transferFee, "VND");

		log.info("TC_15_Step_24: Click Tiep tuc");
		transferInVCB.clickToDynamicButton("Tiếp tục");

		log.info("TC_15_Step_25: Nhap Mat Khau");
		transferInVCB.inputToDynamicPopupPasswordInput(LogIn_Data.Login_Account.NEW_PASSWORD, "Tiếp tục");

		log.info("TC_15_Step_26: Click tiep tuc");
		transferInVCB.clickToDynamicButton("Tiếp tục");

		log.info("TC_15_Step_27: Kiem  tra giao dich thanh cong");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE));

		log.info("TC_15_Step_28: Lay thoi gian tao giao dich");
		transferTime = transferInVCB.getDynamicTransferTimeAndMoney(TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE, "4");

		log.info("TC_15_Step_29: Lay ma giao dich");
		transactionNumber = transferInVCB.getDynamicTextInTextView("Mã giao dịch");

		log.info("TC_15_Step_30:  Kiem tra ten nguoi thu huong hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Tên người thụ hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);

		log.info("TC_15_Step_31: Kiem tra tai khoan dich hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Tài khoản đích"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_15_Step_23: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_15_Step_24: Click thuc hien giao dich moi");
		transferInVCB.clickToDynamicButton("Thực hiện giao dịch mới");

		log.info("TC_15_Step_25: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown("Tài khoản nguồn");

		log.info("TC_15_Step_26: Chon tai ngoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT2);

		log.info("TC_15_Step_27: Lay so du tai khoan chuyen");
		String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTextView("Số dư khả dụng");
		long afterBalanceAmountOfAccount1 = convertMoneyToLong(afterBalanceOfAccount1, "VND");

		log.info("TC_15_Step_28: Kiem tra so du tai khoan chuyen sau khi thuc hien giao dich");
		long transferMoney = convertMoneyToLong(TransferMoneyInVCB_Data.InputDataInVCB.MONEY, "VND");
		verifyEquals(beforeBalanceAmountOfAccount1 - transferMoney, afterBalanceAmountOfAccount1);

		log.info("TC_15_Step_29:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown("Tài khoản nguồn");

		log.info("TC_15_Step_30: Chon tai khoan nguoi nhan");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_15_Step_31:Lay so du kha dung tai khoan nhan");
		String afterBalanceOfAccount2 = transferInVCB.getDynamicTextInTextView("Số dư khả dụng");
		long afterBalanceAmountOfAccount2 = convertMoneyToLong(afterBalanceOfAccount2, "VND");

		log.info("TC_15_Step_32: Kiem tra so du tai khoan nhan sau khi nhan tien");
		verifyEquals(beforeBalanceAmountOfAccount2 + transferMoney - newTransferFee, afterBalanceAmountOfAccount2);

	}

//	@Test
	public void TC_16_KiemTraChiTietGiaoDichChuyenTienNgayCoPhiGiaoDichNguoiNhanTraVNDVaXacThucMatKhau() {

		log.info("TC_16_Step_01:Click  nut Back");
		transferInVCB.clickToDynamicBackIcon("Chuyển tiền trong Vietcombank");

		log.info("TC_16_Step_02:Click vao More Icon");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicBottomMenu("com.VCB:id/menu_5");

		log.info("TC_16_Step_03:Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText("Báo cáo giao dịch");

		log.info("TC_16_Step_04: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText("Tất cả các loại giao dịch");

		log.info("TC_16_Step_05: Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText("Chuyển tiền trong Vietcombank");

		log.info("TC_16_Step_06: Click Chon Tai Khoan");
		transReport.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvSelectAcc");

		log.info("TC_16_Step_07: Chon tai Khoan trich no");
		transReport.clickToDynamicButtonLinkOrLinkText(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT2);

		log.info("TC_16_Step_08: CLick Tim Kiem");
		transReport.clickToDynamicButton("Tìm kiếm");

		log.info("TC_16_Step_09: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transReport.getTextInDynamicTransaction("0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));

		log.info("TC_16_Step_10: Kiem tra thoi gian tao dao dich");
		verifyTrue(transReport.getTextInDynamicTransaction("0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_16_Step_11: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransaction("0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_16_Step_12: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransaction("1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.MONEY) + " VND"));

		log.info("TC_16_Step_13: Click vao giao dich");
		transReport.clickToDynamicTransaction("0", "com.VCB:id/tvDate");

		log.info("TC_16_Step_14: Kiem tra ngay giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTextView("Thời gian giao dịch").contains(transferTime.split(" ")[0]));

		log.info("TC_16_Step_15: Kiem tra thoi gian tao giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTextView("Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_16_Step_16: Kiem tra so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTextView("Số lệnh giao dịch"), transactionNumber);

		log.info("TC_16_Step_17: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTextView("Tài khoản/thẻ trích nợ"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT2);

		log.info("TC_16_Step_18: Kiem tra so tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTextView("Tài khoản ghi có"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_16_Step_19: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTextView("Số tiền giao dịch").contains(addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.MONEY) + " VND"));

		log.info("TC_16_Step_20: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Tên người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);

		log.info("TC_16_Step_21: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Phí giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.COST[1]);

		log.info("TC_16_Step_22: Kiem tra so tien phi hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Số tiền phí"), TransferMoneyInVCB_Data.InputDataInVCB.PAYMENT_BY_PASSWORD_FEE + " VND");

		log.info("TC_16_Step_23: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTextView("Loại giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.TRANSFER_TYPE);

		log.info("TC_16_Step_24: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTextView("Nội dung giao dịch").contains(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_16_Step_25: Click Back icon");
		transReport.clickToDynamicBackIcon("Chi tiết giao dịch");

		log.info("TC_16_Step_26: Chon Tai Khoan");
		transReport.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvSelectAcc");

		log.info("TC_16_Step_27: Click chon tai khoan nhan");
		transReport.clickToDynamicButtonLinkOrLinkText(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_16_Step_28: Click Tim Kiem");
		transReport.clickToDynamicButton("Tìm kiếm");

		log.info("TC_16_Step_29: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transReport.getTextInDynamicTransaction("0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));

		log.info("TC_16_Step_30: Kiem tra thoi gian tao giao dich hiển thị");
		verifyTrue(transReport.getTextInDynamicTransaction("0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_16_Step_31: Kiem tra noi dung giao dich hien thi");
		verifyTrue(transReport.getTextInDynamicTransaction("0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_16_Step_32: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransaction("1", "com.VCB:id/tvMoney"), ("+ " + addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.MONEY) + " VND"));

		log.info("TC_16_Step_33: Click vao bao bao giao dich chi tiet");
		transReport.clickToDynamicTransaction("0", "com.VCB:id/tvDate");

		log.info("TC_16_Step_34: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTextView("Thời gian giao dịch").contains(transferTime.split(" ")[0]));

		log.info("TC_16_Step_35: Kiem tra thoi gian tao giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTextView("Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_16_Step_36: Kiem tra so giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Số lệnh giao dịch"), transactionNumber);

		log.info("TC_16_Step_37: Kiem tra tai khoan trich no hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Tài khoản/thẻ trích nợ"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT2);

		log.info("TC_16_Step_38: Kiem tra tai khoan ghi co hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Tài khoản ghi có"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_16_Step_39: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTextView("Số tiền giao dịch").contains(addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.MONEY) + " VND"));

		log.info("TC_16_Step_40: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Tên người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);

		log.info("TC_16_Step_41: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Phí giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.COST[1]);

		log.info("TC_16_Step_42: Kiem tra so tien phi hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Số tiền phí"), TransferMoneyInVCB_Data.InputDataInVCB.PAYMENT_BY_PASSWORD_FEE + " VND");

		log.info("TC_16_Step_43: Kiem tra loai giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Loại giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.TRANSFER_TYPE);

		log.info("TC_16_Step_44: Kiem tra noi dung giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTextView("Nội dung giao dịch").contains(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_16_Step_45: Click quay lai");
		transferInVCB.clickToDynamicBackIcon("Chi tiết giao dịch");

		log.info("TC_16_Step_46: Click quay lai");
		transferInVCB.clickToDynamicBackIcon("Báo cáo giao dịch");

		log.info("TC_16_Step_47: Click quay lai");
		transferInVCB.navigateBack();
	}

//	@Test
	public void TC_17_ChuyenTienNgayCoPhiGiaoDichNguoiChuyenTraUSDVaXacThucBangMatKhau() {

		log.info("TC_17_Step 01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon("Chuyển tiền trong VCB");

		log.info("TC_17_Step 02: Click chon tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicDropDown("Tài khoản nguồn");

		log.info("TC_17_Step 03: Chon account 1");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_17_Step 04: Lay so du kha dung tai khoan 1");
		String beforeBalanceOfAccount2 = transferInVCB.getDynamicTextInTextView("Số dư khả dụng");
		double beforeBalanceAmountOfAccount2 = convertMoneyToDouble(beforeBalanceOfAccount2, "VND");

		log.info("TC_17_Step 05: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown("Tài khoản nguồn");

		log.info("TC_17_Step 06: Chon USD account");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(TransferMoneyInVCB_Data.InputDataInVCB.USD_ACCOUNT);

		log.info("TC_17_Step 07: Lay so du tai khoan USD");
		String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTextView("Số dư khả dụng");
		double beforeBalanceAmountOfAccount1 = convertMoneyToDouble(beforeBalanceOfAccount1, "USD");

		log.info("TC_17_Step 08: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_17_Step 09: Nhap so tien can chuyen");
		transferInVCB.inputToDynamicInputBox(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER, "Số tiền");

		log.info("TC_17_Step 10: Lay ti gia quy doi");
		String exchangeRate = transferInVCB.getDynamicTextInTextView("Tỷ giá quy đổi tham khảo");
		exchangeRateUSD = exchangeRate.split(" ~ ");
		rate = convertMoneyToDouble(exchangeRateUSD[1], "VND");

		log.info("TC_17_Step 11: Chon phuong thuc xac thuc");
		transferInVCB.clickToDynamicButtonLinkOrLinkText("Phí giao dịch người chuyển trả");

		log.info("TC_17_Step 12: Chon phi giao dich nguoi chuyen tra");
		transferInVCB.clickToDynamicButtonLinkOrLinkText("Người chuyển trả");

		log.info("TC_17_Step 13:  Nhap noi dung");
		transferInVCB.inputToDynamicInputBox(TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Nội dung");

		log.info("TC_17_Step 14: Click tiep tuc");
		transferInVCB.clickToDynamicButton("Tiếp tục");

		log.info("TC_17_Step 15: Kiem tra hinh thuc chuyen tien hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Hình thức chuyển tiền"), TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[0]);

		log.info("TC_17_Step 16: Kiem tra tai khoan nguon hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Tài khoản nguồn"), TransferMoneyInVCB_Data.InputDataInVCB.USD_ACCOUNT);

		log.info("TC_17_Step 17: Kiem tra tai khoan dich hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTextView("Tài khoản đích / VND").contains(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1));

		log.info("TC_17_Step 18: Kiem tra so tien chuyen hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTextView("Số tiền (USD)").contains(addCommasToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER) + "USD"));

		log.info("TC_17_Step 18: Kiem tra Nguoi chuyen tra hien thi");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(TransferMoneyInVCB_Data.InputDataInVCB.COST[0]));

		log.info("TC_17_Step 20: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_17_Step 21: Chon phuong thuc xac thuc");
		transferInVCB.clickToDynamicDropDown("Chọn phương thức xác thực");

		log.info("TC_17_Step 22: Chon Mat khau dang nhap");
		transferInVCB.clickToDynamicButtonLinkOrLinkText("Mật khẩu đăng nhập");

		log.info("TC_17_Step 23: Kiem tra so tien phi hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTextView("Số tiền phí").contains(TransferMoneyInVCB_Data.InputDataInVCB.USD_PAYMENT_BY_OTP_FEE + " USD"));

		log.info("TC_17_Step 24: Lay tien phi chuyen ");
		transferFee = transferInVCB.getDynamicTextInTextView("Số tiền phí");
		double newTransferFee = convertMoneyToDouble(transferFee, "USD");

		log.info("TC_17_Step 25: Click tiep tuc");
		transferInVCB.clickToDynamicButton("Tiếp tục");

		log.info("TC_17_Step 26: Dien OTP");
		transferInVCB.inputToDynamicPopupPasswordInput(LogIn_Data.Login_Account.NEW_PASSWORD, "Tiếp tục");

		log.info("TC_17_Step 27: Click tiep tuc");
		transferInVCB.clickToDynamicButton("Tiếp tục");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE));

		log.info("TC_17_Step 28: Lay thoi gian tao");
		transferTime = transferInVCB.getDynamicTransferTimeAndMoney(TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE, "4");

		log.info("TC_17_Step 29: Kiem tra so tien chuyen hien thi");
		verifyEquals(transferInVCB.getDynamicTransferTimeAndMoney(TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE, "3"), addCommasToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " USD");

		log.info("TC_17_Step 30: Kiem tra ten nguoi thu huong hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Tên người thụ hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);

		log.info("TC_17_Step 31: Kiem tra tai khoan dich hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Tài khoản đích"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_17_Step 32: Lay ma giao dich");
		transactionNumber = transferInVCB.getDynamicTextInTextView("Mã giao dịch");

		log.info("TC_17_Step 33: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_17_Step 34: Click thuc hien giao dich moi");
		transferInVCB.clickToDynamicButton("Thực hiện giao dịch mới");

		log.info("TC_17_Step 35: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown("Tài khoản nguồn");

		log.info("TC_17_Step 36:Click USD account");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(TransferMoneyInVCB_Data.InputDataInVCB.USD_ACCOUNT);

		log.info("TC_17_Step 37: Lay so du kha dung tai khoan USD");
		String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTextView("Số dư khả dụng");
		double afterBalanceAmountOfAccount1 = convertMoneyToDouble(afterBalanceOfAccount1, "USD");

		log.info("TC_17_Step 38: Convert so tien chuyen");
		double transferMoney = convertMoneyToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER, "USD");

		log.info("TC_17_Step 39: Kiem tra so du tai khoan USD sau khi chuyen tien");
		verifyEquals(beforeBalanceAmountOfAccount1 - transferMoney - newTransferFee, afterBalanceAmountOfAccount1);

		log.info("TC_17_Step 40: Kiem tra tai khoan nguon");
		transferInVCB.clickToDynamicDropDown("Tài khoản nguồn");

		log.info("TC_17_Step 41: Click chon tai khoan chuyen den");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_17_Step 42: Lay so du kha dung tai khoan chuyen den");
		String afterBalanceOfAccount2 = transferInVCB.getDynamicTextInTextView("Số dư khả dụng");
		double afterBalanceAmountOfAccount2 = convertMoneyToDouble(afterBalanceOfAccount2, "VND");

		log.info("TC_17_Step 43: Kiem tra so du tai khoan duoc chuyen den");
		verifyEquals(beforeBalanceAmountOfAccount2 + transferMoney * rate, afterBalanceAmountOfAccount2);

	}

//	@Test
	public void TC_18_KiemTraChiTietGiaoDichChuyenTienNgayCoPhiGiaoDichNguoiChuyenTraUSDVaXacThucBangMatKhau() {

		log.info("TC_18_Step 01 : Click  nut Back");
		transferInVCB.clickToDynamicBackIcon("Chuyển tiền trong Vietcombank");

		log.info("TC_18_Step 02: Click vao More Icon");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicBottomMenu("com.VCB:id/menu_5");

		log.info("TC_18_Step 03: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText("Báo cáo giao dịch");

		log.info("TC_18_Step 04: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText("Tất cả các loại giao dịch");

		log.info("TC_18_Step 05:Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText("Chuyển tiền trong Vietcombank");

		log.info("TC_18_Step 06: Click Chon Tai Khoan");
		transReport.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvSelectAcc");

		log.info("TC_18_Step 07: Chon 1 tai Khoan");
		transReport.clickToDynamicButtonLinkOrLinkText(TransferMoneyInVCB_Data.InputDataInVCB.USD_ACCOUNT);

		log.info("TC_18_Step 08: CLick Tim Kiem");
		transReport.clickToDynamicButton("Tìm kiếm");

		log.info("TC_18_Step 09: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transReport.getTextInDynamicTransaction("0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));

		log.info("TC_18_Step 10: Kiem tra thoi gian tao dao dich");
		verifyTrue(transReport.getTextInDynamicTransaction("0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_18_Step 11: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransaction("0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_18_Step 12: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransaction("1", "com.VCB:id/tvMoney"), ("- " + addCommasToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " USD"));

		log.info("TC_18_Step 13: Click vao giao dich");
		transReport.clickToDynamicTransaction("0", "com.VCB:id/tvDate");

		log.info("TC_18_Step 14: Kiem tra ngay giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTextView("Thời gian giao dịch").contains(transferTime.split(" ")[0]));

		log.info("TC_18_Step 15: Kiem tra thoi gian tao giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTextView("Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_18_Step 16:Kiem tra so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTextView("Số lệnh giao dịch"), transactionNumber);

		log.info("TC_18_Step 17: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTextView("Tài khoản/thẻ trích nợ"), TransferMoneyInVCB_Data.InputDataInVCB.USD_ACCOUNT);

		log.info("TC_18_Step 18: Kiem tra so tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTextView("Tài khoản ghi có"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_18_Step 19: Kiem tra so tien giao dich hien thi ");
		verifyTrue(transReport.getDynamicTextInTextView("Số tiền giao dịch").equals(addCommasToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " USD"));

		log.info("TC_18_Step 20: Kiem tra so tien quy doi hien thi ");
		String changedMoney = convertMoneyToLong(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER, "USD") * convertMoneyToLong(exchangeRateUSD[1], "VND") + "";
		verifyTrue(transReport.getDynamicTextInTextView("Số tiền quy đổi").contains(addCommasToLong(changedMoney) + " VND"));

		log.info("TC_18_Step 21: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Tên người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);

		log.info("TC_18_Step 22: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Phí giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.COST[0]);

		log.info("TC_18_Step 23: Kiem tra so tien phi hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Số tiền phí"), TransferMoneyInVCB_Data.InputDataInVCB.PAYMENT_BY_PASSWORD_FEE + " VND");

		log.info("TC_18_Step 24: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTextView("Loại giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.TRANSFER_TYPE);

		log.info("TC_18_Step 25: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTextView("Nội dung giao dịch").contains(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_18_Step 26: Click Back icon");
		transReport.clickToDynamicBackIcon("Chi tiết giao dịch");

		log.info("TC_18_Step 27: Chon Tai Khoan");
		transReport.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvSelectAcc");

		log.info("TC_18_Step 28: Click chon tai khoan nhan");
		transReport.clickToDynamicButtonLinkOrLinkText(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_18_Step 29: Click Tim Kiem");
		transReport.clickToDynamicButton("Tìm kiếm");

		log.info("TC_18_Step 30: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transReport.getTextInDynamicTransaction("0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));

		log.info("TC_18_Step 31: Kiem tra thoi gian tao giao dich hiển thị");
		verifyTrue(transReport.getTextInDynamicTransaction("0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_18_Step 32: Kiem tra noi dung giao dich hien thi");
		verifyTrue(transReport.getTextInDynamicTransaction("0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_18_Step 33: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransaction("1", "com.VCB:id/tvMoney"), ("+ " + addCommasToLong(changedMoney) + " VND"));

		log.info("TC_18_Step 34: Click vao bao bao giao dich chi tiet");
		transReport.clickToDynamicTransaction("0", "com.VCB:id/tvDate");

		log.info("TC_18_Step 35: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTextView("Thời gian giao dịch").contains(transferTime.split(" ")[0]));

		log.info("TC_18_Step 36: Kiem tra thoi gian tao giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTextView("Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_18_Step 37: Kiem tra so giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Số lệnh giao dịch"), transactionNumber);

		log.info("TC_18_Step 38:Kiem tra tai khoan trich no hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Tài khoản/thẻ trích nợ"), TransferMoneyInVCB_Data.InputDataInVCB.USD_ACCOUNT);

		log.info("TC_18_Step 39: Kiem tra tai khoan ghi co hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Tài khoản ghi có"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_18_Step 40: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTextView("Số tiền giao dịch").contains(addCommasToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " USD"));

		log.info("TC_18_Step 41: Kiem tra so tien quy doi hien thi ");

		verifyTrue(transReport.getDynamicTextInTextView("Số tiền quy đổi").contains(addCommasToLong(changedMoney) + " VND"));

		log.info("TC_18_Step 42: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Tên người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);

		log.info("TC_18_Step 43: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Phí giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.COST[0]);

		log.info("TC_18_Step 44:Kiem tra so tien phi hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Số tiền phí"), TransferMoneyInVCB_Data.InputDataInVCB.PAYMENT_BY_PASSWORD_FEE + " VND");

		log.info("TC_18_Step 45:Kiem tra noi dung giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTextView("Nội dung giao dịch").contains(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_18_Step 46: Kiem tra loaigiao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Loại giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.TRANSFER_TYPE);

		log.info("TC_18_Step 47: Click quay lai");
		transferInVCB.clickToDynamicBackIcon("Chi tiết giao dịch");

		log.info("TC_18_Step 48: Click quay lai");
		transferInVCB.clickToDynamicBackIcon("Báo cáo giao dịch");

		log.info("TC_18_Step 49: Click quay lai");
		transferInVCB.navigateBack();
	}

//	@Test
	public void TC_19_ChuyenTienNgayCoPhiGiaoDichNguoiNhanTraEURVaXacThucBangMatKhau() {

		log.info("TC_19_Step 01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon("Chuyển tiền trong VCB");

		log.info("TC_19_Step 02: Click chon tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicDropDown("Tài khoản nguồn");

		log.info("TC_19_Step 03: Chon account 1");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_19_Step 04: Lay so du kha dung tai khoan 1");
		String beforeBalanceOfAccount2 = transferInVCB.getDynamicTextInTextView("Số dư khả dụng");
		double beforeBalanceAmountOfAccount2 = convertMoneyToDouble(beforeBalanceOfAccount2, "VND");

		log.info("TC_19_Step 05: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown("Tài khoản nguồn");

		log.info("TC_19_Step 06: Chon EUR account");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(TransferMoneyInVCB_Data.InputDataInVCB.EUR_ACCOUNT);

		log.info("TC_19_Step 07: Lay so du tai khoan EUR");
		String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTextView("Số dư khả dụng");
		double beforeBalanceAmountOfAccount1 = convertMoneyToDouble(beforeBalanceOfAccount1, "EUR");

		log.info("TC_19_Step 08: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_19_Step 09: Nhap so tien can chuyen");
		transferInVCB.inputToDynamicInputBox(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER, "Số tiền");

		log.info("TC_19_Step 10: Lay ti gia quy doi");
		String exchangeRate = transferInVCB.getDynamicTextInTextView("Tỷ giá quy đổi tham khảo");
		exchangeRateUSD = exchangeRate.split(" ~ ");
		rate = convertMoneyToDouble(exchangeRateUSD[1], "VND");

		log.info("TC_19_Step 11: Chon phuong thuc xac thuc");
		transferInVCB.clickToDynamicButtonLinkOrLinkText("Phí giao dịch người chuyển trả");

		log.info("TC_19_Step 12: Chon phi giao dich nguoi nhan tra");
		transferInVCB.clickToDynamicButtonLinkOrLinkText("Người nhận trả");

		log.info("TC_19_Step 13:  Nhap noi dung");
		transferInVCB.inputToDynamicInputBox(TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Nội dung");

		log.info("TC_19_Step 14: Click tiep tuc");
		transferInVCB.clickToDynamicButton("Tiếp tục");

		log.info("TC_19_Step 15: Kiem tra hinh thuc chuyen tien ngay hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Hình thức chuyển tiền"), TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[0]);

		log.info("TC_19_Step 16: Kiem tra tai khoan nguon hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Tài khoản nguồn"), TransferMoneyInVCB_Data.InputDataInVCB.EUR_ACCOUNT);

		log.info("TC_19_Step 17: Kiem tra tai khoan dich hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTextView("Tài khoản đích / VND").contains(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1));
		log.info("TC_19_Step 18: Kiem tra so tien chuyen hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTextView("Số tiền (EUR)").contains(addCommasToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " EUR"));

		log.info("TC_19_Step 19: Kiem tra Nguoi nhan tra hien thi");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(TransferMoneyInVCB_Data.InputDataInVCB.COST[1]));

		log.info("TC_19_Step 20: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_19_Step 21: Chon phuong thuc xac thuc");
		transferInVCB.clickToDynamicDropDown("Chọn phương thức xác thực");

		log.info("TC_19_Step 22: Chon Mât khau dang nhap");
		transferInVCB.clickToDynamicButtonLinkOrLinkText("Mật khẩu đăng nhập");

		log.info("TC_19_Step 23: Kiem tra so tien phi hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTextView("Số tiền phí").contains(TransferMoneyInVCB_Data.InputDataInVCB.EUR_PAYMENT_BY_PASSWORD_FEE + " EUR"));

		log.info("TC_19_Step 24: Lay tien phi chuyen ");
		transferFee = transferInVCB.getDynamicTextInTextView("Số tiền phí");

		log.info("TC_19_Step 25: Click tiep tuc");
		transferInVCB.clickToDynamicButton("Tiếp tục");

		log.info("TC_19_Step 26: Dien Mat khau");
		transferInVCB.inputToDynamicPopupPasswordInput(LogIn_Data.Login_Account.NEW_PASSWORD, "Tiếp tục");

		log.info("TC_19_Step 27: Click tiep tuc");
		transferInVCB.clickToDynamicButton("Tiếp tục");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE));

		log.info("TC_19_Step 28: Lay thoi gian tao");
		transferTime = transferInVCB.getDynamicTransferTimeAndMoney(TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE, "4");

		log.info("TC_19_Step 29: Kiem tra so tien chuyen hien thi");
		verifyEquals(transferInVCB.getDynamicTransferTimeAndMoney(TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE, "3"), addCommasToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " EUR");

		log.info("TC_19_Step 30: Kiem tra ten nguoi thu huong hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Tên người thụ hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);

		log.info("TC_19_Step 31: Kiem tra tai khoan dich hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Tài khoản đích"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_19_Step 32: Lay ma giao dich");
		transactionNumber = transferInVCB.getDynamicTextInTextView("Mã giao dịch");

		log.info("TC_19_Step 33: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_19_Step 34: Click thuc hien giao dich moi");
		transferInVCB.clickToDynamicButton("Thực hiện giao dịch mới");

		log.info("TC_19_Step 35: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown("Tài khoản nguồn");

		log.info("TC_19_Step 36:Click EUR account");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(TransferMoneyInVCB_Data.InputDataInVCB.EUR_ACCOUNT);

		log.info("TC_19_Step 37: Lay so du kha dung tai khoan EUR");
		String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTextView("Số dư khả dụng");
		double afterBalanceAmountOfAccount1 = convertMoneyToDouble(afterBalanceOfAccount1, "EUR");

		log.info("TC_19_Step 38: Convert so tien chuyen");
		double transferMoney = convertMoneyToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER, "EUR");

		log.info("TC_19_Step 39: Kiem tra so du tai khoan USD sau khi chuyen tien");
		verifyEquals(beforeBalanceAmountOfAccount1 - transferMoney, afterBalanceAmountOfAccount1);

		log.info("TC_19_Step 40: Kiem tra tai khoan nguon");
		transferInVCB.clickToDynamicDropDown("Tài khoản nguồn");

		log.info("TC_19_Step 41: Click chon tai khoan chuyen den");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_19_Step 42: Lay so du kha dung tai khoan chuyen den");
		String afterBalanceOfAccount2 = transferInVCB.getDynamicTextInTextView("Số dư khả dụng");
		double afterBalanceAmountOfAccount2 = convertMoneyToDouble(afterBalanceOfAccount2, "VND");

		log.info("TC_19_Step 43: Kiem tra so du tai khoan duoc chuyen den");
		double transferFee = convertMoneyToDouble(TransferMoneyInVCB_Data.InputDataInVCB.PAYMENT_BY_PASSWORD_FEE, "VND");
		verifyEquals(beforeBalanceAmountOfAccount2 + transferMoney * rate - transferFee, afterBalanceAmountOfAccount2);

	}

//	@Test
	public void TC_20_KiemTraChiTietGiaoDichChuyenTienNgayCoPhiGiaoDichNguoiNhanTraEURVaXacThucBangMatKhau() {

		log.info("TC_20_Step 01 : Click  nut Back");
		transferInVCB.clickToDynamicBackIcon("Chuyển tiền trong Vietcombank");

		log.info("TC_20_Step 02: Click vao More Icon");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicBottomMenu("com.VCB:id/menu_5");

		log.info("TC_20_Step 03: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText("Báo cáo giao dịch");

		log.info("TC_20_Step 04: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText("Tất cả các loại giao dịch");

		log.info("TC_20_Step 05:Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText("Chuyển tiền trong Vietcombank");

		log.info("TC_20_Step 06: Click Chon Tai Khoan");
		transReport.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvSelectAcc");

		log.info("TC_20_Step 07: Chon 1 tai Khoan");
		transReport.clickToDynamicButtonLinkOrLinkText(TransferMoneyInVCB_Data.InputDataInVCB.EUR_ACCOUNT);

		log.info("TC_20_Step 08: CLick Tim Kiem");
		transReport.clickToDynamicButton("Tìm kiếm");

		log.info("TC_20_Step 09: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transReport.getTextInDynamicTransaction("0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));

		log.info("TC_20_Step 10: Kiem tra thoi gian tao dao dich");
		verifyTrue(transReport.getTextInDynamicTransaction("0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_20_Step 11: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransaction("0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_20_Step 12: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransaction("1", "com.VCB:id/tvMoney"), ("- " + addCommasToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " EUR"));

		log.info("TC_20_Step 13: Click vao giao dich");
		transReport.clickToDynamicTransaction("0", "com.VCB:id/tvDate");

		log.info("TC_20_Step 14: Kiem tra ngay giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTextView("Thời gian giao dịch").contains(transferTime.split(" ")[0]));

		log.info("TC_20_Step 15: Kiem tra thoi gian tao giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTextView("Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_20_Step 16:Kiem tra so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTextView("Số lệnh giao dịch"), transactionNumber);

		log.info("TC_20_Step 17: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTextView("Tài khoản/thẻ trích nợ"), TransferMoneyInVCB_Data.InputDataInVCB.EUR_ACCOUNT);

		log.info("TC_20_Step 18: Kiem tra so tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTextView("Tài khoản ghi có"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_20_Step 19: Kiem tra so tien giao dich hien thi ");
		verifyTrue(transReport.getDynamicTextInTextView("Số tiền giao dịch").equals(addCommasToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " EUR"));

		log.info("TC_20_Step 20: Kiem tra so tien quy doi hien thi ");
		String changedMoney = convertMoneyToLong(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER, "EUR") * convertMoneyToLong(exchangeRateUSD[1], "VND") + "";
		verifyTrue(transReport.getDynamicTextInTextView("Số tiền quy đổi").contains(addCommasToLong(changedMoney) + " VND"));

		log.info("TC_20_Step 21: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Tên người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);

		log.info("TC_20_Step 22: Kiem tra phi giao dich nguoi chuyen tra hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Phí giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.COST[1]);

		log.info("TC_20_Step 23: Kiem tra so tien phi hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Số tiền phí"), TransferMoneyInVCB_Data.InputDataInVCB.PAYMENT_BY_PASSWORD_FEE + " VND");

		log.info("TC_20_Step 24: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTextView("Loại giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.TRANSFER_TYPE);

		log.info("TC_20_Step 25: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTextView("Nội dung giao dịch").contains(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_20_Step 26: Click Back icon");
		transReport.clickToDynamicBackIcon("Chi tiết giao dịch");

		log.info("TC_20_Step 27: Chon Tai Khoan");
		transReport.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvSelectAcc");

		log.info("TC_20_Step 28: Click chon tai khoan nhan");
		transReport.clickToDynamicButtonLinkOrLinkText(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_20_Step 29: Click Tim Kiem");
		transReport.clickToDynamicButton("Tìm kiếm");

		log.info("TC_20_Step 30: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transReport.getTextInDynamicTransaction("0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));

		log.info("TC_20_Step 31: Kiem tra thoi gian tao giao dich hiển thị");
		verifyTrue(transReport.getTextInDynamicTransaction("0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_20_Step 32: Kiem tra noi dung giao dich hien thi");
		verifyTrue(transReport.getTextInDynamicTransaction("0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_20_Step 33: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransaction("1", "com.VCB:id/tvMoney"), ("+ " + addCommasToLong(changedMoney) + " VND"));

		log.info("TC_20_Step 34: Click vao bao bao giao dich chi tiet");
		transReport.clickToDynamicTransaction("0", "com.VCB:id/tvDate");

		log.info("TC_20_Step 35: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTextView("Thời gian giao dịch").contains(transferTime.split(" ")[0]));

		log.info("TC_20_Step 36: Kiem tra thoi gian tao giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTextView("Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_20_Step 37: Kiem tra so giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Số lệnh giao dịch"), transactionNumber);

		log.info("TC_20_Step 38:Kiem tra tai khoan trich no hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Tài khoản/thẻ trích nợ"), TransferMoneyInVCB_Data.InputDataInVCB.EUR_ACCOUNT);

		log.info("TC_20_Step 39: Kiem tra tai khoan ghi co hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Tài khoản ghi có"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_20_Step 40: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTextView("Số tiền giao dịch").contains(addCommasToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " EUR"));

		log.info("TC_20_Step 41: Kiem tra so tien quy doi hien thi ");

		verifyTrue(transReport.getDynamicTextInTextView("Số tiền quy đổi").contains(addCommasToLong(changedMoney) + " VND"));

		log.info("TC_20_Step 42: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Tên người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME);

		log.info("TC_20_Step 43: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Phí giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.COST[1]);

		log.info("TC_20_Step 44:Kiem tra so tien phi hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Số tiền phí"), TransferMoneyInVCB_Data.InputDataInVCB.PAYMENT_BY_PASSWORD_FEE + " VND");

		log.info("TC_20_Step 45:Kiem tra noi dung giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTextView("Nội dung giao dịch").contains(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_20_Step 46: Kiem tra loaigiao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Loại giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.TRANSFER_TYPE);

		log.info("TC_20_Step 47: Click quay lai");
		transferInVCB.clickToDynamicBackIcon("Chi tiết giao dịch");

		log.info("TC_20_Step 48: Click quay lai");
		transferInVCB.clickToDynamicBackIcon("Báo cáo giao dịch");

		log.info("TC_20_Step 49: Click quay lai");
		transferInVCB.navigateBack();
	}

//	@Test
	public void TC_21_ChuyenTienNgayDenTaiKhoanKhacChuSoHuuCoPhiGiaoDichNguoiChuyenTraVNDVaXacThucBangMatKhau() {

		log.info("TC_21_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon("Chuyển tiền trong VCB");

		log.info("TC_21_Step_02:Click tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicDropDown("Tài khoản nguồn");

		log.info("TC_21_Step_03: Chon tai khoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_21_Step_04: Lay so du tai khoan chuyen");
		String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTextView("Số dư khả dụng");
		long beforeBalanceAmountOfAccount1 = convertMoneyToLong(beforeBalanceOfAccount1, "VND");

		log.info("TC_21_Step_05: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(TransferMoneyInVCB_Data.InputDataInVCB.DIFFERENT_OWNER_ACCOUNT, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_21_Step_06: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(TransferMoneyInVCB_Data.InputDataInVCB.MONEY, "Số tiền");

		log.info("TC_21_Step_07: Nhap noi dung");
		transferInVCB.inputToDynamicInputBox(TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Nội dung");

		log.info("TC_21_Step_08: Click tiep tuc");
		transferInVCB.clickToDynamicButton("Tiếp tục");

		log.info("TC_21_Step_09: Kiem tra hinh thuc chuyen tien hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Hình thức chuyển tiền"), TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[0]);

		log.info("TC_21_Step_10: Kiem tra tai khoan nguon hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Tài khoản nguồn"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_21_Step_11: Kiem tra tai khoan dich hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTextView("Tài khoản đích / VND").contains(TransferMoneyInVCB_Data.InputDataInVCB.DIFFERENT_OWNER_ACCOUNT));

		log.info("TC_21_Step_12: Kiem tra so tien chuyen hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTextView("Số tiền (VND)").contains(addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.MONEY)));

		log.info("TC_21_Step_14: Kiem tra phuong thuc thanh toan bang OTP");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(TransferMoneyInVCB_Data.InputDataInVCB.COST[0]));

		log.info("TC_21_Step_16: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_06_Step_17: Chon Phuong thuc nhap");
		transferInVCB.clickToDynamicDropDown("Chọn phương thức xác thực");

		log.info("TC_21_Step_18: Chon Mật khẩu đăng nhập");
		transferInVCB.clickToDynamicButtonLinkOrLinkText("Mật khẩu đăng nhập");

		log.info("TC_21_Step_13: Kiem tra so tien phi hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTextView("Số tiền phí").contains(TransferMoneyInVCB_Data.InputDataInVCB.TRANSFER_FEE_TO_OTHER_ACCOUNT_OWNER + " VND"));

		log.info("TC_21_Step_15: Lay so tien phi");
		transferFee = transferInVCB.getDynamicTextInTextView("Số tiền phí");
		long newTransferFee = convertMoneyToLong(transferFee, "VND");

		log.info("TC_21_Step_19: Click Tiep tuc");
		transferInVCB.clickToDynamicButton("Tiếp tục");

		log.info("TC05_Step : Dien Mat khau");
		transferInVCB.inputToDynamicPopupPasswordInput(LogIn_Data.Login_Account.NEW_PASSWORD, "Tiếp tục");

		log.info("TC_21_Step_21: Click tiep tuc");
		transferInVCB.clickToDynamicButton("Tiếp tục");

		log.info("TC_21_Step_21: Kiem  tra giao dich thanh cong");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE));

		log.info("TC_21_Step_23: Lay thoi gian tao giao dich");
		transferTime = transferInVCB.getDynamicTransferTimeAndMoney(TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE, "4");

		log.info("TC_21_Step_24: Lay ma giao dich");
		transactionNumber = transferInVCB.getDynamicTextInTextView("Mã giao dịch");

		log.info("TC_21_Step_25: Kiem tra ten nguoi thu huong hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Tên người thụ hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.DIFFERENT_OWNER_NAME);

		log.info("TC_21_Step_26: Kiem tra tai khoan dich hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Tài khoản đích"), TransferMoneyInVCB_Data.InputDataInVCB.DIFFERENT_OWNER_ACCOUNT);

		log.info("TC_21_Step_27: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_21_Step_28: Click thuc hien giao dich moi");
		transferInVCB.clickToDynamicButton("Thực hiện giao dịch mới");

		log.info("TC_21_Step_29: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown("Tài khoản nguồn");

		log.info("TC_21_Step_30: Chon tai ngoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_21_Step_31: Lay so du tai khoan chuyen");
		String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTextView("Số dư khả dụng");
		long afterBalanceAmountOfAccount1 = convertMoneyToLong(afterBalanceOfAccount1, "VND");

		log.info("TC_21_Step_32: Kiem tra so du tai khoan chuyen sau khi thuc hien giao dich");
		long transferMoney = convertMoneyToLong(TransferMoneyInVCB_Data.InputDataInVCB.MONEY, "VND");
		verifyEquals(beforeBalanceAmountOfAccount1 - transferMoney - newTransferFee, afterBalanceAmountOfAccount1);

	}

//	@Test
	public void TC_22_KiemTraChiTietGiaoDichChuyenTienNgayDenTaiKhoanKhacChuSoHuuCoPhiGiaoDichNguoiChuyenTraVNDVaXacThucBangMatKhau() {
		log.info("TC_22_Step_01 : Click  nut Back");
		transferInVCB.clickToDynamicBackIcon("Chuyển tiền trong Vietcombank");

		log.info("TC_22_Step_02: Click vao More Icon");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicBottomMenu("com.VCB:id/menu_5");

		log.info("TC_22_Step_03: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText("Báo cáo giao dịch");

		log.info("TC_22_Step_04: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText("Tất cả các loại giao dịch");

		log.info("TC_22_Step_05: Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText("Chuyển tiền trong Vietcombank");

		log.info("TC_22_Step_06: Click Chon Tai Khoan");
		transReport.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvSelectAcc");

		log.info("TC_22_Step_07: Chon tai Khoan chuyen");
		transReport.clickToDynamicButtonLinkOrLinkText(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_22_Step_08: CLick Tim Kiem");
		transReport.clickToDynamicButton("Tìm kiếm");

		log.info("TC_22_Step_09: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transReport.getTextInDynamicTransaction("0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));

		log.info("TC_22_Step_10: Kiem tra thoi gian tao dao dich");
		verifyTrue(transReport.getTextInDynamicTransaction("0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_22_Step_11: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransaction("0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_22_Step_12: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransaction("1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.MONEY) + " VND"));

		log.info("TC_22_Step_13: Click vao giao dich");
		transReport.clickToDynamicTransaction("0", "com.VCB:id/tvDate");

		log.info("TC_22_Step_14: Kiem tra ngay giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTextView("Thời gian giao dịch").contains(transferTime.split(" ")[0]));

		log.info("TC_22_Step_15: Kiem tra thoi gian tao giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTextView("Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_22_Step_16: Kiem tra thoi gian tao giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Số lệnh giao dịch"), transactionNumber);

		log.info("TC_22_Step_17: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTextView("Tài khoản/thẻ trích nợ"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_22_Step_18: Kiem tra so tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTextView("Tài khoản ghi có"), TransferMoneyInVCB_Data.InputDataInVCB.DIFFERENT_OWNER_ACCOUNT);

		log.info("TC_22_Step_19: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTextView("Số tiền giao dịch").contains(addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.MONEY) + " VND"));

		log.info("TC_22_Step_20: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Tên người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.DIFFERENT_OWNER_NAME);

		log.info("TC_22_Step_21: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Phí giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.COST[0]);

		log.info("TC_22_Step_22: Kiem tra so tien phi hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Số tiền phí"), TransferMoneyInVCB_Data.InputDataInVCB.TRANSFER_FEE_TO_OTHER_ACCOUNT_OWNER + " VND");

		log.info("TC_22_Step_23: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTextView("Loại giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.TRANSFER_TYPE);

		log.info("TC_22_Step_24: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTextView("Nội dung giao dịch").contains(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_22_Step_25: Click Back icon");
		transReport.clickToDynamicBackIcon("Chi tiết giao dịch");

		log.info("TC_22_Step_36: Click quay lai");
		transferInVCB.clickToDynamicBackIcon("Báo cáo giao dịch");

		log.info("TC_22_Step_37: Click quay lai");
		transferInVCB.navigateBack();
	}

//	@Test
	public void TC_23_ChuyenTienNgayDenTaiKhoanKhacChuSoHuuCoPhiGiaoDichNguoiNhanTraVNDVaXacThucBangMatKhau() {

		log.info("TC_23_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon("Chuyển tiền trong VCB");

		log.info("TC_23_Step_02:Click tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicDropDown("Tài khoản nguồn");

		log.info("TC_23_Step_03: Chon tai khoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_23_Step_04: Lay so du tai khoan chuyen");
		String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTextView("Số dư khả dụng");
		long beforeBalanceAmountOfAccount1 = convertMoneyToLong(beforeBalanceOfAccount1, "VND");

		log.info("TC_23_Step_05: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(TransferMoneyInVCB_Data.InputDataInVCB.DIFFERENT_OWNER_ACCOUNT, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_23_Step_06: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(TransferMoneyInVCB_Data.InputDataInVCB.MONEY, "Số tiền");

		log.info("TC_23_Step_07: Nhap noi dung");
		transferInVCB.inputToDynamicInputBox(TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Nội dung");

		log.info("TC_03_Step_10: Click phi giao dich nguoi chuyen tra");
		transferInVCB.clickToDynamicButtonLinkOrLinkText("Phí giao dịch người chuyển trả");

		log.info("TC_03_Step_11: Chon nguoi nhan tra");
		transferInVCB.clickToDynamicButtonLinkOrLinkText("Người nhận trả");

		log.info("TC_23_Step_08: Click tiep tuc");
		transferInVCB.clickToDynamicButton("Tiếp tục");

		log.info("TC_23_Step_09: Kiem tra hinh thuc chuyen tien hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Hình thức chuyển tiền"), TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[0]);

		log.info("TC_23_Step_10: Kiem tra tai khoan nguon hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Tài khoản nguồn"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_23_Step_11: Kiem tra tai khoan dich hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTextView("Tài khoản đích / VND").contains(TransferMoneyInVCB_Data.InputDataInVCB.DIFFERENT_OWNER_ACCOUNT));

		log.info("TC_23_Step_12: Kiem tra so tien chuyen hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTextView("Số tiền (VND)").contains(addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.MONEY)));

		log.info("TC_23_Step_14: Kiem tra phuong thuc thanh toan bang OTP");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(TransferMoneyInVCB_Data.InputDataInVCB.COST[1]));

		log.info("TC_23_Step_16: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_06_Step_17: Chon Phuong thuc nhap");
		transferInVCB.clickToDynamicDropDown("Chọn phương thức xác thực");

		log.info("TC_23_Step_18: Chon Mat khau dang nhap");
		transferInVCB.clickToDynamicButtonLinkOrLinkText("Mật khẩu đăng nhập");

		log.info("TC_23_Step_13: Kiem tra so tien phi hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTextView("Số tiền phí").contains(TransferMoneyInVCB_Data.InputDataInVCB.TRANSFER_FEE_TO_OTHER_ACCOUNT_OWNER + " VND"));

		log.info("TC_23_Step_19: Click Tiep tuc");
		transferInVCB.clickToDynamicButton("Tiếp tục");

		log.info("TC_23_Step : Dien OTP");
		transferInVCB.inputToDynamicPopupPasswordInput(LogIn_Data.Login_Account.NEW_PASSWORD, "Tiếp tục");

		log.info("TC_23_Step_21: Click tiep tuc");
		transferInVCB.clickToDynamicButton("Tiếp tục");

		log.info("TC_23_Step_21: Kiem  tra giao dich thanh cong");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE));

		log.info("TC_23_Step_23: Lay thoi gian tao giao dich");
		transferTime = transferInVCB.getDynamicTransferTimeAndMoney(TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE, "4");

		log.info("TC_23_Step_24: Lay ma giao dich");
		transactionNumber = transferInVCB.getDynamicTextInTextView("Mã giao dịch");

		log.info("TC_23_Step_25: Kiem tra ten nguoi thu huong hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Tên người thụ hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.DIFFERENT_OWNER_NAME);

		log.info("TC_23_Step_26: Kiem tra tai khoan dich hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Tài khoản đích"), TransferMoneyInVCB_Data.InputDataInVCB.DIFFERENT_OWNER_ACCOUNT);

		log.info("TC_23_Step_27: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTextView("Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_23_Step_28: Click thuc hien giao dich moi");
		transferInVCB.clickToDynamicButton("Thực hiện giao dịch mới");

		log.info("TC_23_Step_29: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown("Tài khoản nguồn");

		log.info("TC_23_Step_30: Chon tai ngoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_23_Step_31: Lay so du tai khoan chuyen");
		String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTextView("Số dư khả dụng");
		long afterBalanceAmountOfAccount1 = convertMoneyToLong(afterBalanceOfAccount1, "VND");

		log.info("TC_23_Step_32: Kiem tra so du tai khoan chuyen sau khi thuc hien giao dich");
		long transferMoney = convertMoneyToLong(TransferMoneyInVCB_Data.InputDataInVCB.MONEY, "VND");
		verifyEquals(beforeBalanceAmountOfAccount1 - transferMoney, afterBalanceAmountOfAccount1);

	}

//	@Test
	public void TC_24_KiemTraChiTietGiaoDichChuyenTienNgayDenTaiKhoanKhacChuSoHuuCoPhiGiaoDichNguoiNhanTraVNDVaXacThucBangMatKhau() {
		log.info("TC_24_Step_01 : Click  nut Back");
		transferInVCB.clickToDynamicBackIcon("Chuyển tiền trong Vietcombank");

		log.info("TC_24_Step_02: Click vao More Icon");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicBottomMenu("com.VCB:id/menu_5");

		log.info("TC_24_Step_03: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText("Báo cáo giao dịch");

		log.info("TC_24_Step_04: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText("Tất cả các loại giao dịch");

		log.info("TC_24_Step_05: Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText("Chuyển tiền trong Vietcombank");

		log.info("TC_24_Step_06: Click Chon Tai Khoan");
		transReport.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvSelectAcc");

		log.info("TC_24_Step_07: Chon tai Khoan chuyen");
		transReport.clickToDynamicButtonLinkOrLinkText(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_24_Step_08: CLick Tim Kiem");
		transReport.clickToDynamicButton("Tìm kiếm");

		log.info("TC_24_Step_09: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transReport.getTextInDynamicTransaction("0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));

		log.info("TC_24_Step_10: Kiem tra thoi gian tao dao dich");
		verifyTrue(transReport.getTextInDynamicTransaction("0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_24_Step_11: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransaction("0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_24_Step_12: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransaction("1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.MONEY) + " VND"));

		log.info("TC_24_Step_13: Click vao giao dich");
		transReport.clickToDynamicTransaction("0", "com.VCB:id/tvDate");

		log.info("TC_24_Step_14: Kiem tra ngay giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTextView("Thời gian giao dịch").contains(transferTime.split(" ")[0]));

		log.info("TC_24_Step_15: Kiem tra thoi gian tao giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTextView("Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_24_Step_16: Kiem tra thoi gian tao giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Số lệnh giao dịch"), transactionNumber);

		log.info("TC_24_Step_17: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTextView("Tài khoản/thẻ trích nợ"), TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT1);

		log.info("TC_24_Step_18: Kiem tra so tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTextView("Tài khoản ghi có"), TransferMoneyInVCB_Data.InputDataInVCB.DIFFERENT_OWNER_ACCOUNT);

		log.info("TC_24_Step_19: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTextView("Số tiền giao dịch").contains(addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.MONEY) + " VND"));

		log.info("TC_24_Step_20: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Tên người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.DIFFERENT_OWNER_NAME);

		log.info("TC_24_Step_21: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Phí giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.COST[1]);

		log.info("TC_24_Step_22: Kiem tra so tien phi hien thi");
		verifyEquals(transReport.getDynamicTextInTextView("Số tiền phí"), TransferMoneyInVCB_Data.InputDataInVCB.TRANSFER_FEE_TO_OTHER_ACCOUNT_OWNER + " VND");

		log.info("TC_24_Step_23: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTextView("Loại giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.TRANSFER_TYPE);

		log.info("TC_24_Step_24: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTextView("Nội dung giao dịch").contains(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_24_Step_25: Click Back icon");
		transReport.clickToDynamicBackIcon("Chi tiết giao dịch");

		log.info("TC_24_Step_36: Click quay lai");
		transferInVCB.clickToDynamicBackIcon("Báo cáo giao dịch");

		log.info("TC_24_Step_37: Click quay lai");
		transferInVCB.navigateBack();
	}

	@AfterClass(alwaysRun = true)

	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
