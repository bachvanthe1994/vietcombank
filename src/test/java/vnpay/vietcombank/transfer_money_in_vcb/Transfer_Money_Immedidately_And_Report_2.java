package vnpay.vietcombank.transfer_money_in_vcb;

import java.io.IOException;

import org.testng.annotations.AfterClass;
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
import pageObjects.TransferMoneyInVcbPageObject;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.TransferMoneyInVCB_Data;

public class Transfer_Money_Immedidately_And_Report_2 extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferMoneyInVcbPageObject transferInVCB;
	private TransactionReportPageObject transReport;
	private String transferFee;
	private String transferTime;
	private String transactionNumber;
	double rate;
	private long fee;
	String[] exchangeRateUSD;

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);

		log.info("Before class_Step_10: Scroll den trang thai lenh chuyen tien");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.scrollDownToText(driver, "Trạng thái lệnh chuyển tiền");

	}

	@Parameters({ "pass" })
	@Test
	public void TC_13_ChuyenTienNgayCoPhiGiaoDichNguoiChuyenTraVNDVaXacThucBangMatKhau(String pass) {

		log.info("TC_13_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_13_Step_02:Click tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_13_Step_03: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);
		transferInVCB.sleep(driver, 2000);

		log.info("TC_13_Step_04: Lay so du tai khoan dich");
		String beforeBalanceOfAccount2 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		long beforeBalanceAmountOfAccount2 = convertMoneyToLong(beforeBalanceOfAccount2, "VND");

		log.info("TC_13_Step_05:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_13_Step_06: Chon tai khoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);
		transferInVCB.sleep(driver, 2000);

		log.info("TC_13_Step_07: Lay so du tai khoan chuyen");
		String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		long beforeBalanceAmountOfAccount1 = convertMoneyToLong(beforeBalanceOfAccount1, "VND");

		log.info("TC_13_Step_08: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, Account_Data.Valid_Account.ACCOUNT2, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_13_Step_09: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY, "Số tiền");

		log.info("TC_13_Step_10: Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Thông tin giao dịch", "3");

		log.info("TC_13_Step_11: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_13_Step_12: Kiem tra hinh thuc chuyen tien hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Hình thức chuyển tiền"), TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[0]);

		log.info("TC_13_Step_13: Kiem tra tai khoan nguon hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_13_Step_14: Kiem tra tai khoan dich hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tài khoản đích/ VND").contains(Account_Data.Valid_Account.ACCOUNT2));

		log.info("TC_13_Step_15: Kiem tra so tien chuyen hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, "Số tiền").contains(addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY)));

		log.info("TC_13_Step_17: Kiem tra phuong thuc thanh toan bang OTP");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.InputDataInVCB.COST[0]));

		log.info("TC_13_Step_19: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_06_Step_20: Chon Phuong thuc nhap");
		transferInVCB.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");

		log.info("TC_13_Step_21: Chon SMS OTP");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		String transferFee = transferInVCB.getDynamicTextByLabel(driver, "Số tiền phí").replaceAll("\\D+", "");
		fee = convertMoneyToLong(transferFee, "VND");

		log.info("TC_13_Step_16: Kiem tra so tien phi hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, "Số tiền phí").contains(addCommasToLong(fee + "") + " VND"));

		log.info("TC_13_Step_22: Click Tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_13_Step_23: Nhap Password");
		transferInVCB.inputToDynamicPopupPasswordInput(driver, pass, "Tiếp tục");

		log.info("TC_13_Step_24: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_13_Step_25: Kiem  tra giao dich thanh cong");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE));

		log.info("TC_13_Step_26: Lay thoi gian tao giao dich");
		transferTime = transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE, "4").split(" ")[3];

		log.info("TC_13_Step_27: Lay ma giao dich");
		transactionNumber = transferInVCB.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC_13_Step_28: Kiem tra ten nguoi thu huong hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tên người thụ hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME_ACCOUNT_2);

		log.info("TC_13_Step_29: Kiem tra tai khoan dich hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tài khoản thụ hưởng"), Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_13_Step_30: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_13_Step_31: Click thuc hien giao dich moi");
		transferInVCB.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_13_Step_32: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_13_Step_33: Chon tai ngoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);
		transferInVCB.sleep(driver, 2000);

		log.info("TC_13_Step_34: Lay so du tai khoan chuyen");
		String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		long afterBalanceAmountOfAccount1 = convertMoneyToLong(afterBalanceOfAccount1, "VND");

		log.info("TC_13_Step_35: Kiem tra so du tai khoan chuyen sau khi thuc hien giao dich");
		long transferMoney = convertMoneyToLong(TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY, "VND");
		verifyEquals(beforeBalanceAmountOfAccount1 - transferMoney - fee, afterBalanceAmountOfAccount1);

		log.info("TC_13_Step_36: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_13_Step_37: Chon tai khoan nguoi nhan");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);
		transferInVCB.sleep(driver, 2000);

		log.info("TC_13_Step_38: Lay so du kha dung tai khoan nhan");
		String afterBalanceOfAccount2 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");

		log.info("TC_13_Step_39: Kiem tra so du tai khoan nhan sau khi nhan tien");
		long afterBalanceAmountOfAccount2 = convertMoneyToLong(afterBalanceOfAccount2, "VND");
		verifyEquals(beforeBalanceAmountOfAccount2 + transferMoney, afterBalanceAmountOfAccount2);

	}

	@Test
	public void TC_14_KiemTraChiTietGiaoDichChuyenTienNgayCoPhiGiaoDichNguoiChuyenTraVNDVaXacThucBangMatKhau() {
		log.info("TC_14_Step_01 : Click  nut Back");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");

		log.info("TC_14_Step_02: Click vao More Icon");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_14_Step_03: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

		log.info("TC_14_Step_04: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

		log.info("TC_14_Step_05: Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền trong Vietcombank");

		log.info("TC_14_Step_06: Click Chon Tai Khoan");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_14_Step_07: Chon tai Khoan chuyen");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_14_Step_08: CLick Tim Kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_14_Step_09: Lay ngay tao giao dich hien thi");
		String transferTimeInReport = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_14_Step_10: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_14_Step_11: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_14_Step_12: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY) + " VND"));

		log.info("TC_14_Step_13: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_14_Step_14: Kiem tra ngay giao dich hien thi");
		String reportTime1 = transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch");
		verifyEquals(reportTime1, transferTimeInReport);

		log.info("TC_14_Step_16: Kiem tra thoi gian tao giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_14_Step_17: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_14_Step_18: Kiem tra so tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản ghi có"), Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_14_Step_19: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").contains(addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY) + " VND"));

		log.info("TC_14_Step_20: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME_ACCOUNT_2);

		log.info("TC_14_Step_21: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Phí giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.COST[0]);

		log.info("TC_14_Step_22: Kiem tra so tien phi hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), addCommasToLong(fee + "") + " VND");

		log.info("TC_14_Step_23: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Loại giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.TRANSFER_TYPE);

		log.info("TC_14_Step_24: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Nội dung giao dịch").contains(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_14_Step_25: Click Back icon");
		transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_14_Step_26: Chon Tai Khoan");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_14_Step_27: Click chon tai khoan nhan");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_14_Step_28: Click Tim Kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_14_Step_29: Lay ngay tao giao dich hien thi");
		String transferTimeInReport2 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_14_Step_30: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport2), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_14_Step_31: Kiem tra noi dung giao dich hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_14_Step_32: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("+ " + addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY) + " VND"));

		log.info("TC_14_Step_33: Click vao bao bao giao dich chi tiet");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_14_Step_34: Kiem tra ngay tao giao dich hien thi");
		String reportTime2 = transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch");

		log.info("TC_14_Step_35: Kiem tra time trong bao cao giao dich");
		verifyEquals(reportTime2, transferTimeInReport);

		log.info("TC_14_Step_36: Kiem tra so giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_14_Step_37: Kiem tra tai khoan trich no hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_14_Step_38: Kiem tra tai khoan ghi co hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản ghi có"), Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_14_Step_39: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").contains(addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY) + " VND"));

		log.info("TC_14_Step_40: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME_ACCOUNT_2);

		log.info("TC_14_Step_41: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Phí giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.COST[0]);

		log.info("TC_14_Step_42: Kiem tra so tien phi hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), TransferMoneyInVCB_Data.InputDataInVCB.PAYMENT_BY_PASSWORD_FEE + " VND");

		log.info("TC_14_Step_43: Kiem tra noi dung giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Loại giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.TRANSFER_TYPE);

		log.info("TC_14_Step_44: Kiem tra loaigiao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Nội dung giao dịch").contains(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_14_Step_45: Click quay lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_14_Step_46: Click quay lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

		log.info("TC_14_Step_47: Click Home icon");
		transferInVCB.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");

	}

	@Parameters({ "pass" })
	@Test
	public void TC_15_ChuyenTienNgayCoPhiGiaoDichNguoiNhanTraVNDVaXacThucBangMatKhau(String pass) {

		log.info("TC_15_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_15_Step_02:Click tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_15_Step_03: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);
		transferInVCB.sleep(driver, 2000);

		log.info("TC_15_Step_04: Lay so du tai khoan dich");
		String beforeBalanceOfAccount2 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		long beforeBalanceAmountOfAccount2 = convertMoneyToLong(beforeBalanceOfAccount2, "VND");

		log.info("TC_15_Step_05:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_15_Step_06: Chon tai khoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);
		transferInVCB.sleep(driver, 2000);

		log.info("TC_15_Step_07: Lay so du tai khoan chuyen");
		String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		long beforeBalanceAmountOfAccount1 = convertMoneyToLong(beforeBalanceOfAccount1, "VND");

		log.info("TC_15_Step_08: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, Account_Data.Valid_Account.ACCOUNT1, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_15_Step_09: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY, "Số tiền");

		log.info("TC_15_Step_10: Click phi giao dich nguoi chuyen tra");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");

		log.info("TC_15_Step_11: Chon nguoi nhan tra");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Người nhận trả");

		log.info("TC_15_Step_12: Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Thông tin giao dịch", "3");

		log.info("TC_15_Step_13: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_15_Step_14:  Kiem tra hinh thuc chuyen tien hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Hình thức chuyển tiền"), TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[0]);

		log.info("TC_15_Step_15: Kiem tra tai khoan nguon hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_15_Step_16: Kiem tra tai khoan dich hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tài khoản đích/ VND").contains(Account_Data.Valid_Account.ACCOUNT1));

		log.info("TC_15_Step_17: Kiem tra so tien chuyen hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, "Số tiền").contains(addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY)));

		log.info("TC_15_Step_19: Kiem tra nguoi nhan tra");
//		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.InputDataInVCB.COST[1]));

		log.info("TC_15_Step_21:Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_15_Step_22: Chon Phuong thuc xac thuc ");
		transferInVCB.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");

		log.info("TC_15_Step_23: Chon Mat khau dang nhap");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		String transferFee = transferInVCB.getDynamicTextByLabel(driver, "Số tiền phí").replaceAll("\\D+", "");
		fee = convertMoneyToLong(transferFee, "VND");

		log.info("TC_15_Step_18: Kiem tra so tien phi hien thi");
//		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, "Số tiền phí").contains(addCommasToLong(fee + "") + " VND"));

		log.info("TC_15_Step_24: Click Tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_15_Step_25: Nhap Mat Khau");
		transferInVCB.inputToDynamicPopupPasswordInput(driver, pass, "Tiếp tục");

		log.info("TC_15_Step_26: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_15_Step_27: Kiem  tra giao dich thanh cong");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE));

		log.info("TC_15_Step_28: Lay thoi gian tao giao dich");
		transferTime = transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE, "4").split(" ")[3];

		log.info("TC_15_Step_29: Lay ma giao dich");
		transactionNumber = transferInVCB.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC_15_Step_30:  Kiem tra ten nguoi thu huong hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tên người thụ hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME_ACCOUNT_1);

		log.info("TC_15_Step_31: Kiem tra tai khoan dich hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tài khoản thụ hưởng"), Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_15_Step_23: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_15_Step_24: Click thuc hien giao dich moi");
		transferInVCB.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_15_Step_25: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_15_Step_26: Chon tai ngoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);
		transferInVCB.sleep(driver, 2000);

		log.info("TC_15_Step_27: Lay so du tai khoan chuyen");
		String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		long afterBalanceAmountOfAccount1 = convertMoneyToLong(afterBalanceOfAccount1, "VND");

		log.info("TC_15_Step_28: Kiem tra so du tai khoan chuyen sau khi thuc hien giao dich");
		long transferMoney = convertMoneyToLong(TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY, "VND");
		verifyEquals(beforeBalanceAmountOfAccount1 - transferMoney, afterBalanceAmountOfAccount1);

		log.info("TC_15_Step_29:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_15_Step_30: Chon tai khoan nguoi nhan");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);
		transferInVCB.sleep(driver, 2000);

		log.info("TC_15_Step_31:Lay so du kha dung tai khoan nhan");
		String afterBalanceOfAccount2 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		long afterBalanceAmountOfAccount2 = convertMoneyToLong(afterBalanceOfAccount2, "VND");

		log.info("TC_15_Step_32: Kiem tra so du tai khoan nhan sau khi nhan tien");
		verifyEquals(beforeBalanceAmountOfAccount2 + transferMoney - fee, afterBalanceAmountOfAccount2);

	}

	@Test
	public void TC_16_KiemTraChiTietGiaoDichChuyenTienNgayCoPhiGiaoDichNguoiNhanTraVNDVaXacThucMatKhau() {

		log.info("TC_16_Step_01:Click  nut Back");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");

		log.info("TC_16_Step_02:Click vao More Icon");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_16_Step_03:Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

		log.info("TC_16_Step_04: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

		log.info("TC_16_Step_05: Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền trong Vietcombank");

		log.info("TC_16_Step_06: Click Chon Tai Khoan");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_16_Step_07: Chon tai Khoan trich no");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_16_Step_08: CLick Tim Kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_16_Step_09: Kiem tra ngay tao giao dich hien thi");
		String transferTimeIneport = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_16_Step_10: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeIneport), transferTime);

		log.info("TC_16_Step_11: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_16_Step_12: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY) + " VND"));

		log.info("TC_16_Step_13: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_16_Step_14: Kiem tra ngay giao dich hien thi");
		String reportTime1 = transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch");
		verifyEquals(reportTime1, transferTimeIneport);

		log.info("TC_16_Step_16: Kiem tra so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_16_Step_17: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_16_Step_18: Kiem tra so tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản ghi có"), Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_16_Step_19: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").contains(addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY) + " VND"));

		log.info("TC_16_Step_20: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME_ACCOUNT_1);

		log.info("TC_16_Step_21: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Phí giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.COST[1]);

		log.info("TC_16_Step_22: Kiem tra so tien phi hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), addCommasToLong(fee + "") + " VND");

		log.info("TC_16_Step_23: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Loại giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.TRANSFER_TYPE);

		log.info("TC_16_Step_24: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Nội dung giao dịch").contains(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_16_Step_25: Click Back icon");
		transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_16_Step_26: Chon Tai Khoan");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_16_Step_27: Click chon tai khoan nhan");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_16_Step_28: Click Tim Kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_16_Step_29: Kiem tra ngay tao giao dich hien thi");
		String transferTimeIneport2 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_16_Step_30: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeIneport2), transferTime);

		log.info("TC_16_Step_31: Kiem tra noi dung giao dich hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_16_Step_32: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("+ " + addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY) + " VND"));

		log.info("TC_16_Step_33: Click vao bao bao giao dich chi tiet");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_16_Step_34: Kiem tra ngay tao giao dich hien thi");
		String reportTime2 = transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch");
		verifyEquals(reportTime2, transferTimeIneport2);

		log.info("TC_16_Step_36: Kiem tra so giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_16_Step_37: Kiem tra tai khoan trich no hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_16_Step_38: Kiem tra tai khoan ghi co hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản ghi có"), Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_16_Step_39: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").contains(addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY) + " VND"));

		log.info("TC_16_Step_40: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME_ACCOUNT_1);

		log.info("TC_16_Step_41: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Phí giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.COST[1]);

		log.info("TC_16_Step_42: Kiem tra so tien phi hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), addCommasToLong(fee + "") + " VND");

		log.info("TC_16_Step_43: Kiem tra loai giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Loại giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.TRANSFER_TYPE);

		log.info("TC_16_Step_44: Kiem tra noi dung giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Nội dung giao dịch").contains(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_16_Step_45: Click quay lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_16_Step_46: Click quay lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

		log.info("TC_16_Step_47: Click Home icon");
		transferInVCB.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");

	}

	@Parameters({ "pass" })
	@Test
	public void TC_17_ChuyenTienNgayCoPhiGiaoDichNguoiChuyenTraUSDVaXacThucBangMatKhau(String pass) {

		log.info("TC_17_Step 01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_17_Step 02: Click chon tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_17_Step 03: Chon account 1");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);
		transferInVCB.sleep(driver, 2000);

		log.info("TC_17_Step 04: Lay so du kha dung tai khoan 1");
		String beforeBalanceOfAccount2 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		double beforeBalanceAmountOfAccount2 = convertMoneyToDouble(beforeBalanceOfAccount2, "VND");

		log.info("TC_17_Step 05: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_17_Step 06: Chon USD account");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.USD_ACCOUNT);
		transferInVCB.sleep(driver, 2000);

		log.info("TC_17_Step 07: Lay so du tai khoan USD");
		String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		double beforeBalanceAmountOfAccount1 = convertMoneyToDouble(beforeBalanceOfAccount1, "USD");

		log.info("TC_17_Step 08: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, Account_Data.Valid_Account.ACCOUNT1, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_17_Step 09: Nhap so tien can chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER, "Số tiền");

		log.info("TC_17_Step 11: Chon phuong thuc xac thuc");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");

		log.info("TC_17_Step 12: Chon phi giao dich nguoi chuyen tra");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Người chuyển trả");

		log.info("TC_17_Step 13:  Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Thông tin giao dịch", "3");

		log.info("TC_17_Step 14: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_17_Step 15: Kiem tra hinh thuc chuyen tien hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Hình thức chuyển tiền"), TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[0]);

		log.info("TC_17_Step 16: Kiem tra tai khoan nguon hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), Account_Data.Valid_Account.USD_ACCOUNT);

		log.info("TC_17_Step 17: Kiem tra tai khoan dich hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tài khoản đích/ VND").contains(Account_Data.Valid_Account.ACCOUNT1));

		log.info("TC_17_Step 18: Kiem tra so tien chuyen hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, "Số tiền(USD)").contains(addCommasToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " USD"));

		log.info("TC_17_Step 18: Kiem tra Nguoi chuyen tra hien thi");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.InputDataInVCB.COST[0]));

		log.info("TC_17_Step 20: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_17_Step 21: Chon phuong thuc xac thuc");
		transferInVCB.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");

		log.info("TC_17_Step 22: Chon Mat khau dang nhap");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		String transferFee = transferInVCB.getDynamicTextByLabel(driver, "Số tiền phí").replaceAll("\\D+", "");
		fee = convertMoneyToLong(transferFee, "VND");

		log.info("TC_17_Step 23: Kiem tra so tien phi hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, "Số tiền phí").contains(addCommasToLong(fee + "") + " VND"));

		log.info("TC_17_Step 24: Lay tien phi chuyen ");
		double newTransferFee = convertMoneyToDouble(TransferMoneyInVCB_Data.InputDataInVCB.USD_PAYMENT_BY_PASSWORD_FEE, "USD");

		log.info("TC_17_Step 25: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_17_Step 26: Dien Mat khau");
		transferInVCB.inputToDynamicPopupPasswordInput(driver, pass, "Tiếp tục");

		log.info("TC_17_Step 27: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE));

		log.info("TC_17_Step 28: Lay thoi gian tao");
		transferTime = transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE, "4").split(" ")[3];

		log.info("TC_17_Step 29: Kiem tra so tien chuyen hien thi");
		verifyEquals(transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE, "3"), addCommasToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " USD");

		log.info("TC_17_Step 30: Kiem tra ten nguoi thu huong hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tên người thụ hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME_ACCOUNT_1);

		log.info("TC_17_Step 31: Kiem tra tai khoan dich hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tài khoản thụ hưởng"), Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_17_Step 32: Lay ma giao dich");
		transactionNumber = transferInVCB.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC_17_Step 33: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_17_Step 34: Click thuc hien giao dich moi");
		transferInVCB.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_17_Step 35: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_17_Step 36:Click USD account");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.USD_ACCOUNT);

		log.info("TC_17_Step 37: Lay so du kha dung tai khoan USD");
		String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		double afterBalanceAmountOfAccount1 = convertMoneyToDouble(afterBalanceOfAccount1, "USD");

		log.info("TC_17_Step 38: Convert so tien chuyen");
		double transferMoney = convertMoneyToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER, "USD");

		log.info("TC_17_Step 39: Kiem tra so du tai khoan USD sau khi chuyen tien");
		verifyEquals(beforeBalanceAmountOfAccount1 - transferMoney - newTransferFee, afterBalanceAmountOfAccount1);

		log.info("TC_17_Step 40: Kiem tra tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_17_Step 41: Click chon tai khoan chuyen den");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_17_Step 42: Lay so du kha dung tai khoan chuyen den");
		String afterBalanceOfAccount2 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		double afterBalanceAmountOfAccount2 = convertMoneyToDouble(afterBalanceOfAccount2, "VND");

		log.info("TC_17_Step 43: Kiem tra so du tai khoan duoc chuyen den");
		verifyEquals(beforeBalanceAmountOfAccount2 + Double.parseDouble(TransferMoneyInVCB_Data.InputDataInVCB.USD_CHANGED_MONEY), afterBalanceAmountOfAccount2);

	}

	@Test
	public void TC_18_KiemTraChiTietGiaoDichChuyenTienNgayCoPhiGiaoDichNguoiChuyenTraUSDVaXacThucBangMatKhau() {

		log.info("TC_18_Step 01 : Click  nut Back");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");

		log.info("TC_18_Step 02: Click vao More Icon");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_18_Step 03: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

		log.info("TC_18_Step 04: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

		log.info("TC_18_Step 05:Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền trong Vietcombank");

		log.info("TC_18_Step 06: Click Chon Tai Khoan");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_18_Step 07: Chon 1 tai Khoan");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.USD_ACCOUNT);

		log.info("TC_18_Step 08: CLick Tim Kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_18_Step 09: Kiem tra ngay tao giao dich hien thi");
		String transferTimeInReport = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_18_Step_10: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport), transferTime);

		log.info("TC_18_Step 11: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_18_Step 12: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " USD"));

		log.info("TC_18_Step 13: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_18_Step 14: Lay va kiem tra ngay giao dich hien thi");
		String reportTime1 = transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch");
		verifyEquals(reportTime1, transferTimeInReport);

		log.info("TC_18_Step 16:Kiem tra so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_18_Step 17: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), Account_Data.Valid_Account.USD_ACCOUNT);

		log.info("TC_18_Step 18: Kiem tra so tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản ghi có"), Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_18_Step 19: Kiem tra so tien giao dich hien thi ");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").equals(addCommasToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " USD"));

		log.info("TC_18_Step 19: Kiem tra so tien quy đổi ");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền quy đổi").contains(addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.USD_CHANGED_MONEY) + " VND"));

		log.info("TC_18_Step 21: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME_ACCOUNT_1);

		log.info("TC_18_Step 22: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Phí giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.COST[0]);

		log.info("TC_18_Step 23: Kiem tra so tien phi hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), addCommasToLong(fee + "") + " VND");

		log.info("TC_18_Step 24: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Loại giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.TRANSFER_TYPE);

		log.info("TC_18_Step 25: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Nội dung giao dịch").contains(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_18_Step 26: Click Back icon");
		transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_18_Step 27: Chon Tai Khoan");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_18_Step 28: Click chon tai khoan nhan");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_18_Step 29: Click Tim Kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_18_Step 30: Kiem tra ngay tao giao dich hien thi");
		String transferTimeInReport2 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_18_Step_31: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport2), transferTime);

		log.info("TC_18_Step 32: Kiem tra noi dung giao dich hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_18_Step 33: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("+ " + addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.USD_CHANGED_MONEY) + " VND"));

		log.info("TC_18_Step 34: Click vao bao bao giao dich chi tiet");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_18_Step 35: Kiem tra ngay tao giao dich hien thi");
		String reportTime2 = transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch");
		verifyEquals(reportTime2, transferTimeInReport2);

		log.info("TC_18_Step 37: Kiem tra so giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_18_Step 38:Kiem tra tai khoan trich no hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), Account_Data.Valid_Account.USD_ACCOUNT);

		log.info("TC_18_Step 39: Kiem tra tai khoan ghi co hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản ghi có"), Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_18_Step 40: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").contains(addCommasToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " USD"));

		log.info("TC_18_Step 41: Kiem tra so tien quy doi hien thi ");

		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền quy đổi").contains(addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.USD_CHANGED_MONEY) + " VND"));

		log.info("TC_18_Step 42: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME_ACCOUNT_1);

		log.info("TC_18_Step 43: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Phí giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.COST[0]);

		log.info("TC_18_Step 44:Kiem tra so tien phi hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), addCommasToLong(fee + "") + " VND");

		log.info("TC_18_Step 45:Kiem tra noi dung giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Nội dung giao dịch").contains(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_18_Step 46: Kiem tra loaigiao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Loại giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.TRANSFER_TYPE);

		log.info("TC_18_Step 47: Click quay lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_18_Step 48: Click quay lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

		log.info("TC_18_Step_49: Click Home icon");
		transferInVCB.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");

	}

	@Parameters({ "pass" })
	@Test
	public void TC_19_ChuyenTienNgayCoPhiGiaoDichNguoiNhanTraEURVaXacThucBangMatKhau(String pass) {

		log.info("TC_19_Step 01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_19_Step 02: Click chon tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_19_Step 03: Chon account 1");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);
		transferInVCB.sleep(driver, 2000);

		log.info("TC_19_Step 04: Lay so du kha dung tai khoan 1");
		String beforeBalanceOfAccount2 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		double beforeBalanceAmountOfAccount2 = convertMoneyToDouble(beforeBalanceOfAccount2, "VND");

		log.info("TC_19_Step 05: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_19_Step 06: Chon EUR account");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.EUR_ACCOUNT);
		transferInVCB.sleep(driver, 2000);

		log.info("TC_19_Step 07: Lay so du tai khoan EUR");
		String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		double beforeBalanceAmountOfAccount1 = convertMoneyToDouble(beforeBalanceOfAccount1, "EUR");

		log.info("TC_19_Step 08: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, Account_Data.Valid_Account.ACCOUNT1, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_19_Step 09: Nhap so tien can chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER, "Số tiền");

		log.info("TC_19_Step 11: Chon phuong thuc xac thuc");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");

		log.info("TC_19_Step 12: Chon phi giao dich nguoi nhan tra");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Người nhận trả");

		log.info("TC_19_Step 13:  Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Thông tin giao dịch", "3");

		log.info("TC_19_Step 14: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_19_Step 15: Kiem tra hinh thuc chuyen tien ngay hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Hình thức chuyển tiền"), TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[0]);

		log.info("TC_19_Step 16: Kiem tra tai khoan nguon hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), Account_Data.Valid_Account.EUR_ACCOUNT);

		log.info("TC_19_Step 17: Kiem tra tai khoan dich hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tài khoản đích/ VND").contains(Account_Data.Valid_Account.ACCOUNT1));

		log.info("TC_19_Step 18: Kiem tra so tien chuyen hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, "Số tiền(EUR)").contains(addCommasToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " EUR"));

		log.info("TC_19_Step 19: Kiem tra Nguoi nhan tra hien thi");
//		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.InputDataInVCB.COST[1]));

		log.info("TC_19_Step 20: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_19_Step 21: Chon phuong thuc xac thuc");
		transferInVCB.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");

		log.info("TC_19_Step 22: Chon Mât khau dang nhap");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		String transferFee = transferInVCB.getDynamicTextByLabel(driver, "Số tiền phí").replaceAll("\\D+", "");
		fee = convertMoneyToLong(transferFee, "VND");

		log.info("TC_19_Step 23: Kiem tra so tien phi hien thi");
//		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, "Số tiền phí").contains(addCommasToLong(fee + "") + " VND"));

		log.info("TC_19_Step 25: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_19_Step 26: Dien Mat khau");
		transferInVCB.inputToDynamicPopupPasswordInput(driver, pass, "Tiếp tục");

		log.info("TC_19_Step 27: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE));

		log.info("TC_19_Step 28: Lay thoi gian tao");
		transferTime = transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE, "4").split(" ")[3];

		log.info("TC_19_Step 29: Kiem tra so tien chuyen hien thi");
		verifyEquals(transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE, "3"), addCommasToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " EUR");

		log.info("TC_19_Step 30: Kiem tra ten nguoi thu huong hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tên người thụ hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME_ACCOUNT_1);

		log.info("TC_19_Step 31: Kiem tra tai khoan dich hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tài khoản thụ hưởng"), Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_19_Step 32: Lay ma giao dich");
		transactionNumber = transferInVCB.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC_19_Step 33: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_19_Step 34: Click thuc hien giao dich moi");
		transferInVCB.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_19_Step 35: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_19_Step 36:Click EUR account");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.EUR_ACCOUNT);

		log.info("TC_19_Step 37: Lay so du kha dung tai khoan EUR");
		String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		double afterBalanceAmountOfAccount1 = convertMoneyToDouble(afterBalanceOfAccount1, "EUR");

		log.info("TC_19_Step 38: Convert so tien chuyen");
		double transferMoney = convertMoneyToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER, "EUR");

		log.info("TC_19_Step 39: Kiem tra so du tai khoan USD sau khi chuyen tien");
		verifyEquals(beforeBalanceAmountOfAccount1 - transferMoney, afterBalanceAmountOfAccount1);

		log.info("TC_19_Step 40: Kiem tra tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_19_Step 41: Click chon tai khoan chuyen den");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_19_Step 42: Lay so du kha dung tai khoan chuyen den");
		String afterBalanceOfAccount2 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		double afterBalanceAmountOfAccount2 = convertMoneyToDouble(afterBalanceOfAccount2, "VND");

		log.info("TC_19_Step 43: Kiem tra so du tai khoan duoc chuyen den");
		verifyEquals(beforeBalanceAmountOfAccount2 + Double.parseDouble(TransferMoneyInVCB_Data.InputDataInVCB.EUR_CHANGED_MONEY) - Double.parseDouble(fee + ""), afterBalanceAmountOfAccount2);

	}

	@Test
	public void TC_20_KiemTraChiTietGiaoDichChuyenTienNgayCoPhiGiaoDichNguoiNhanTraEURVaXacThucBangMatKhau() {

		log.info("TC_20_Step 01 : Click  nut Back");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");

		log.info("TC_20_Step 02: Click vao More Icon");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_20_Step 03: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

		log.info("TC_20_Step 04: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

		log.info("TC_20_Step 05:Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền trong Vietcombank");

		log.info("TC_20_Step 06: Click Chon Tai Khoan");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_20_Step 07: Chon 1 tai Khoan");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.EUR_ACCOUNT);

		log.info("TC_20_Step 08: CLick Tim Kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_20_Step 09: Kiem tra ngay tao giao dich hien thi");
		String transferTimeInReport = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_20_Step_10: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport), transferTime);

		log.info("TC_20_Step 11: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_20_Step 12: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " EUR"));

		log.info("TC_20_Step 13: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_20_Step 14: Kiem tra ngay giao dich hien thi");
		String reportTime1 = transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch");
		verifyEquals(reportTime1, transferTimeInReport);

		log.info("TC_20_Step 16:Kiem tra so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_20_Step 17: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), Account_Data.Valid_Account.EUR_ACCOUNT);

		log.info("TC_20_Step 18: Kiem tra so tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản ghi có"), Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_20_Step 19: Kiem tra so tien giao dich hien thi ");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").equals(addCommasToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " EUR"));

		log.info("TC_20_Step 20: Kiem tra so tien quy doi hien thi ");
		String changedMoney = TransferMoneyInVCB_Data.InputDataInVCB.EUR_CHANGED_MONEY;
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền quy đổi").contains(addCommasToLong(changedMoney) + " VND"));

		log.info("TC_20_Step 21: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME_ACCOUNT_1);

		log.info("TC_20_Step 22: Kiem tra phi giao dich nguoi chuyen tra hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Phí giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.COST[1]);

		log.info("TC_20_Step 23: Kiem tra so tien phi hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), addCommasToLong(fee + "") + " VND");

		log.info("TC_20_Step 24: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Loại giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.TRANSFER_TYPE);

		log.info("TC_20_Step 25: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Nội dung giao dịch").contains(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_20_Step 26: Click Back icon");
		transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_20_Step 27: Chon Tai Khoan");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_20_Step 28: Click chon tai khoan nhan");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_20_Step 29: Click Tim Kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_20_Step 30: Kiem tra ngay tao giao dich hien thi");
		String transferTimeInReport2 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_20_Step_31: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport2), transferTime);

		log.info("TC_20_Step 32: Kiem tra noi dung giao dich hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_20_Step 33: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("+ " + addCommasToLong(changedMoney) + " VND"));

		log.info("TC_20_Step 34: Click vao bao bao giao dich chi tiet");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_20_Step 35: Kiem tra ngay tao giao dich hien thi");
		String reportTime2 = transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch");
		verifyEquals(reportTime2, transferTimeInReport);

		log.info("TC_20_Step 37: Kiem tra so giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_20_Step 38:Kiem tra tai khoan trich no hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), Account_Data.Valid_Account.EUR_ACCOUNT);

		log.info("TC_20_Step 39: Kiem tra tai khoan ghi co hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản ghi có"), Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_20_Step 40: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").contains(addCommasToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " EUR"));

		log.info("TC_20_Step 41: Kiem tra so tien quy doi hien thi ");

		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền quy đổi").contains(addCommasToLong(changedMoney) + " VND"));

		log.info("TC_20_Step 42: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.RECEIVER_NAME_ACCOUNT_1);

		log.info("TC_20_Step 43: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Phí giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.COST[1]);

		log.info("TC_20_Step 44:Kiem tra so tien phi hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), addCommasToLong(fee + "") + " VND");

		log.info("TC_20_Step 45:Kiem tra noi dung giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Nội dung giao dịch").contains(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_20_Step 46: Kiem tra loaigiao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Loại giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.TRANSFER_TYPE);

		log.info("TC_20_Step 47: Click quay lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_20_Step 48: Click quay lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

		log.info("TC_20_Step_49: Click Home icon");
		transferInVCB.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");

	}

	@Parameters({ "pass" })
	@Test
	public void TC_21_ChuyenTienNgayDenTaiKhoanKhacChuSoHuuCoPhiGiaoDichNguoiChuyenTraVNDVaXacThucBangMatKhau(String pass) {

		log.info("TC_21_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_21_Step_02:Click tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_21_Step_03: Chon tai khoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);
		transferInVCB.sleep(driver, 2000);

		log.info("TC_21_Step_04: Lay so du tai khoan chuyen");
		String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		long beforeBalanceAmountOfAccount1 = convertMoneyToLong(beforeBalanceOfAccount1, "VND");

		log.info("TC_21_Step_05: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, Account_Data.Valid_Account.DIFFERENT_OWNER_ACCOUNT, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_21_Step_06: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY, "Số tiền");

		log.info("TC_21_Step_07: Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Thông tin giao dịch", "3");

		log.info("TC_21_Step_08: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_21_Step_09: Kiem tra hinh thuc chuyen tien hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Hình thức chuyển tiền"), TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[0]);

		log.info("TC_21_Step_10: Kiem tra tai khoan nguon hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_21_Step_11: Kiem tra tai khoan dich hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tài khoản đích/ VND").contains(Account_Data.Valid_Account.DIFFERENT_OWNER_ACCOUNT));

		log.info("TC_21_Step_12: Kiem tra so tien chuyen hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, "Số tiền").contains(addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY)));

		log.info("TC_21_Step_14: Kiem tra phuong thuc thanh toan bang OTP");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.InputDataInVCB.COST[0]));

		log.info("TC_21_Step_16: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_06_Step_17: Chon Phuong thuc nhap");
		transferInVCB.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");

		log.info("TC_21_Step_18: Chon Mật khẩu đăng nhập");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		String transferFee = transferInVCB.getDynamicTextByLabel(driver, "Số tiền phí").replaceAll("\\D+", "");
		fee = convertMoneyToLong(transferFee, "VND");

		log.info("TC_21_Step_13: Kiem tra so tien phi hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, "Số tiền phí").contains(addCommasToLong(fee + "") + " VND"));

		log.info("TC_21_Step_19: Click Tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC05_Step : Dien Mat khau");
		transferInVCB.inputToDynamicPopupPasswordInput(driver, pass, "Tiếp tục");

		log.info("TC_21_Step_21: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_21_Step_21: Kiem  tra giao dich thanh cong");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE));

		log.info("TC_21_Step_23: Lay thoi gian tao giao dich");
		transferTime = transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE, "4").split(" ")[3];

		log.info("TC_21_Step_24: Lay ma giao dich");
		transactionNumber = transferInVCB.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC_21_Step_25: Kiem tra ten nguoi thu huong hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tên người thụ hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.DIFFERENT_OWNER_NAME);

		log.info("TC_21_Step_26: Kiem tra tai khoan dich hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tài khoản thụ hưởng"), Account_Data.Valid_Account.DIFFERENT_OWNER_ACCOUNT);

		log.info("TC_21_Step_27: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_21_Step_28: Click thuc hien giao dich moi");
		transferInVCB.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_21_Step_29: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_21_Step_30: Chon tai ngoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);
		transferInVCB.sleep(driver, 2000);

		log.info("TC_21_Step_31: Lay so du tai khoan chuyen");
		String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		long afterBalanceAmountOfAccount1 = convertMoneyToLong(afterBalanceOfAccount1, "VND");

		log.info("TC_21_Step_32: Kiem tra so du tai khoan chuyen sau khi thuc hien giao dich");
		long transferMoney = convertMoneyToLong(TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY, "VND");
		verifyEquals(beforeBalanceAmountOfAccount1 - transferMoney - fee, afterBalanceAmountOfAccount1);

	}

	@Test
	public void TC_22_KiemTraChiTietGiaoDichChuyenTienNgayDenTaiKhoanKhacChuSoHuuCoPhiGiaoDichNguoiChuyenTraVNDVaXacThucBangMatKhau() {
		log.info("TC_22_Step_01 : Click  nut Back");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");

		log.info("TC_22_Step_02: Click vao More Icon");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_22_Step_03: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

		log.info("TC_22_Step_04: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

		log.info("TC_22_Step_05: Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền trong Vietcombank");

		log.info("TC_22_Step_06: Click Chon Tai Khoan");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_22_Step_07: Chon tai Khoan chuyen");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_22_Step_08: CLick Tim Kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_22_Step_09: Kiem tra ngay tao giao dich hien thi");
		String transferTimeInReport = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_10_Step_10: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport), transferTime);

		log.info("TC_22_Step_11: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_22_Step_12: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY) + " VND"));

		log.info("TC_22_Step_13: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_22_Step_14: Kiem tra ngay giao dich hien thi");
		String reportTime1 = transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch");
		verifyEquals(reportTime1, transferTimeInReport);

		log.info("TC_22_Step_16: Kiem tra thoi gian tao giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_22_Step_17: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_22_Step_18: Kiem tra so tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản ghi có"), Account_Data.Valid_Account.DIFFERENT_OWNER_ACCOUNT);

		log.info("TC_22_Step_19: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").contains(addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY) + " VND"));

		log.info("TC_22_Step_20: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.DIFFERENT_OWNER_NAME);

		log.info("TC_22_Step_21: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Phí giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.COST[0]);

		log.info("TC_22_Step_22: Kiem tra so tien phi hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), addCommasToLong(fee + "") + " VND");

		log.info("TC_22_Step_23: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Loại giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.TRANSFER_TYPE);

		log.info("TC_22_Step_24: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Nội dung giao dịch").contains(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_22_Step_25: Click Back icon");
		transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_22_Step_36: Click quay lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

		log.info("TC_22_Step_37: Click Home icon");
		transferInVCB.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");

	}

	@Parameters({ "pass" })
	@Test
	public void TC_23_ChuyenTienNgayDenTaiKhoanKhacChuSoHuuCoPhiGiaoDichNguoiNhanTraVNDVaXacThucBangMatKhau(String pass) {

		log.info("TC_23_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_23_Step_02:Click tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_23_Step_03: Chon tai khoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);
		transferInVCB.sleep(driver, 2000);

		log.info("TC_23_Step_04: Lay so du tai khoan chuyen");
		String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		long beforeBalanceAmountOfAccount1 = convertMoneyToLong(beforeBalanceOfAccount1, "VND");

		log.info("TC_23_Step_05: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, Account_Data.Valid_Account.DIFFERENT_OWNER_ACCOUNT, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_23_Step_06: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY, "Số tiền");

		log.info("TC_23_Step_07: Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Thông tin giao dịch", "3");

		log.info("TC_03_Step_10: Click phi giao dich nguoi chuyen tra");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");

		log.info("TC_03_Step_11: Chon nguoi nhan tra");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Người nhận trả");

		log.info("TC_23_Step_08: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_23_Step_09: Kiem tra hinh thuc chuyen tien hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Hình thức chuyển tiền"), TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[0]);

		log.info("TC_23_Step_10: Kiem tra tai khoan nguon hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_23_Step_11: Kiem tra tai khoan dich hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tài khoản đích/ VND").contains(Account_Data.Valid_Account.DIFFERENT_OWNER_ACCOUNT));

		log.info("TC_23_Step_12: Kiem tra so tien chuyen hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, "Số tiền").contains(addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY)));

		log.info("TC_23_Step_14: Kiem tra phuong thuc thanh toan bang OTP");
//		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.InputDataInVCB.COST[1]));

		log.info("TC_23_Step_16: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_06_Step_17: Chon Phuong thuc nhap");
		transferInVCB.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");

		log.info("TC_23_Step_18: Chon Mat khau dang nhap");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		String transferFee = transferInVCB.getDynamicTextByLabel(driver, "Số tiền phí").replaceAll("\\D+", "");
		fee = convertMoneyToLong(transferFee, "VND");

		log.info("TC_23_Step_13: Kiem tra so tien phi hien thi");
//		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, "Số tiền phí").contains(addCommasToLong(fee + "") + " VND"));

		log.info("TC_23_Step_19: Click Tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_23_Step : Dien OTP");
		transferInVCB.inputToDynamicPopupPasswordInput(driver, pass, "Tiếp tục");

		log.info("TC_23_Step_21: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_23_Step_21: Kiem  tra giao dich thanh cong");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE));

		log.info("TC_23_Step_23: Lay thoi gian tao giao dich");
		transferTime = transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE, "4").split(" ")[3];

		log.info("TC_23_Step_24: Lay ma giao dich");
		transactionNumber = transferInVCB.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC_23_Step_25: Kiem tra ten nguoi thu huong hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tên người thụ hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.DIFFERENT_OWNER_NAME);

		log.info("TC_23_Step_26: Kiem tra tai khoan dich hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tài khoản thụ hưởng"), Account_Data.Valid_Account.DIFFERENT_OWNER_ACCOUNT);

		log.info("TC_23_Step_27: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, "Nội dung"), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_23_Step_28: Click thuc hien giao dich moi");
		transferInVCB.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_23_Step_29: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_23_Step_30: Chon tai ngoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);
		transferInVCB.sleep(driver, 2000);

		log.info("TC_23_Step_31: Lay so du tai khoan chuyen");
		String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		long afterBalanceAmountOfAccount1 = convertMoneyToLong(afterBalanceOfAccount1, "VND");

		log.info("TC_23_Step_32: Kiem tra so du tai khoan chuyen sau khi thuc hien giao dich");
		long transferMoney = convertMoneyToLong(TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY, "VND");
		verifyEquals(beforeBalanceAmountOfAccount1 - transferMoney, afterBalanceAmountOfAccount1);

	}

	@Test
	public void TC_24_KiemTraChiTietGiaoDichChuyenTienNgayDenTaiKhoanKhacChuSoHuuCoPhiGiaoDichNguoiNhanTraVNDVaXacThucBangMatKhau() {
		log.info("TC_24_Step_01 : Click  nut Back");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");

		log.info("TC_24_Step_02: Click vao More Icon");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_24_Step_03: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

		log.info("TC_24_Step_04: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

		log.info("TC_24_Step_05: Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền trong Vietcombank");

		log.info("TC_24_Step_06: Click Chon Tai Khoan");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_24_Step_07: Chon tai Khoan chuyen");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_24_Step_08: CLick Tim Kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_24_Step_09: Kiem tra ngay tao giao dich hien thi");
		String transferTimeInReport = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_24_Step_10: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport), transferTime);

		log.info("TC_24_Step_11: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_24_Step_12: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY) + " VND"));

		log.info("TC_24_Step_13: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_24_Step_14: Kiem tra ngay giao dich hien thi");
		String reportTime1 = transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch");
		verifyEquals(reportTime1, transferTimeInReport);

		log.info("TC_24_Step_16: Kiem tra thoi gian tao giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_24_Step_17: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_24_Step_18: Kiem tra so tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản ghi có"), Account_Data.Valid_Account.DIFFERENT_OWNER_ACCOUNT);

		log.info("TC_24_Step_19: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").contains(addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY) + " VND"));

		log.info("TC_24_Step_20: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), TransferMoneyInVCB_Data.InputDataInVCB.DIFFERENT_OWNER_NAME);

		log.info("TC_24_Step_21: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Phí giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.COST[1]);

		log.info("TC_24_Step_22: Kiem tra so tien phi hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), addCommasToLong(fee + "") + " VND");

		log.info("TC_24_Step_23: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Loại giao dịch"), TransferMoneyInVCB_Data.InputDataInVCB.TRANSFER_TYPE);

		log.info("TC_24_Step_24: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Nội dung giao dịch").contains(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_24_Step_25: Click Back icon");
		transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_24_Step_36: Click quay lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

		log.info("TC_18_Step_49: Click Home icon");
		transferInVCB.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@AfterClass(alwaysRun = true)

	public void afterClass() {
		closeApp();
		service.stop();
	}

}
