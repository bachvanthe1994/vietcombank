package vnpay.vietcombank.transfer_money_in_vcb;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.Constants;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import model.SourceAccountModel;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.SettingVCBSmartOTPPageObject;
import pageObjects.TransactionReportPageObject;
import pageObjects.TransferMoneyInVcbPageObject;
import vietcombank_test_data.HomePage_Data.Home_Text_Elements;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransactionReport_Data.Text;
import vietcombank_test_data.TransferMoneyInVCB_Data;
import vietcombank_test_data.TransferMoneyInVCB_Data.InputDataInVCB;
import vietcombank_test_data.TransferMoneyInVCB_Data.TittleData;

public class Transfer_Money_Immedidately_And_Report_3_Smart_OTP extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferMoneyInVcbPageObject transferInVCB;
	private TransactionReportPageObject transReport;
	private SettingVCBSmartOTPPageObject smartOTP;
	private String transferTime;
	private String transactionNumber;
	private double exchangeRate;
	private long fee;
	private String transferMoneyInVND;
	SourceAccountModel sourceAccount = new SourceAccountModel();
	SourceAccountModel distanceAccount = new SourceAccountModel();

	String account_other_owner, name_other_owner, nameReceived, otpSmart, newOTP;

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException, GeneralSecurityException {
		startServer();
		log.info("Before class: Mo app ");
		if (deviceType.contains("android")) {
			driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		} else if (deviceType.contains("ios")) {
			driver = openIOSApp(deviceName, udid, url);
		}
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);

		log.info("Before class_Step_10: Scroll den trang thai lenh chuyen tien");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.scrollDownToText(driver, TittleData.STATUS_TRANSACTION);
		smartOTP = PageFactoryManager.getSettingVCBSmartOTPPageObject(driver);
		nameReceived = getDataInCell(3);
		account_other_owner = getDataInCell(0);
		name_other_owner = getDataInCell(1);
		otpSmart = getDataInCell(6);
		newOTP = "111222";
		smartOTP.setupSmartOTP(LogIn_Data.Login_Account.Smart_OTP, otpSmart);
	}

	@Parameters({ "otp" })
	@Test(invocationCount = 2)
	public void TC_00_SMS(String otp) {

		log.info("TC_01_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, Home_Text_Elements.HOME_TRANSFER_IN_VCB);

		log.info("TC_01_Step_04:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);
		List<String> listAccount = transferInVCB.getListSourceAccount(driver, Constants.VND_CURRENCY);
		log.info("TC_01_Step_05: Chon tai khoan dich");
		sourceAccount = transferInVCB.chooseSourceAccount(driver, Constants.AMOUNT_VND, Constants.VND_CURRENCY);

		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);
		distanceAccount.account = transferInVCB.getDistanceAccount(driver, sourceAccount.account, listAccount);
		transferInVCB.clickToTextID(driver, "com.VCB:id/cancel_button");
		log.info("TC_01_Step_08: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, distanceAccount.account, TittleData.INPUT_ACCOUNT_BENEFICI);

		log.info("TC_01_Step_09: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY, TittleData.AMOUNT);

		log.info("TC_01_Step_10: Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, TittleData.TRANSFER_INFO, "3");

		log.info("TC_01_Step_11: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_01_Step_20: Chon Phuong thuc nhap");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.METHOD_VALIDATE);

		log.info("TC_01_Step_21: Chon SMS OTP");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TittleData.SMS_OTP);

		log.info("TC_01_Step_22: Click Tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_01_Step_23: Nhap OTP");
		transferInVCB.inputToDynamicOtp(driver, otp, TittleData.CONTINUE_BTN);

		log.info("TC_01_Step_24: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_01_Step_31: Click thuc hien giao dich moi");
		transferInVCB.clickToDynamicButton(driver, TittleData.NEW_TRANSFER);

		transferInVCB.clickToDynamicBackIcon(driver, TittleData.TRANSFER_IN_VCBANK);

	}

	@Parameters({ "otp" })
	@Test
	public void TC_01_ChuyenTienNgayCoPhiGiaoDichNguoiChuyenTraVNDVaXacThucBangSmartOTP() {

		log.info("TC_01_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, Home_Text_Elements.HOME_TRANSFER_IN_VCB);

		log.info("TC_01_Step_04:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);
		List<String> listAccount = transferInVCB.getListSourceAccount(driver, Constants.VND_CURRENCY);
		log.info("TC_01_Step_05: Chon tai khoan dich");
		sourceAccount = transferInVCB.chooseSourceAccount(driver, Constants.AMOUNT_VND, Constants.VND_CURRENCY);

		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);
		distanceAccount.account = transferInVCB.getDistanceAccount(driver, sourceAccount.account, listAccount);
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, distanceAccount.account);
		log.info("TC_01_Step_04: Lay so du tai khoan dich");
		String beforeBalanceOfAccount2 = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS);
		long beforeBalanceAmountOfAccount2 = convertMoneyToLong(beforeBalanceOfAccount2, Constants.VND_CURRENCY);

		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_01_Step_07: Lay so du tai khoan chuyen");
		String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS);
		long beforeBalanceAmountOfAccount1 = convertMoneyToLong(beforeBalanceOfAccount1, Constants.VND_CURRENCY);

		log.info("TC_01_Step_08: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, distanceAccount.account, TittleData.INPUT_ACCOUNT_BENEFICI);

		log.info("TC_01_Step_09: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY, TittleData.AMOUNT);

		log.info("TC_01_Step_10: Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, TittleData.TRANSFER_INFO, "3");

		log.info("TC_01_Step_11: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_01_Step_12: Kiem tra hinh thuc chuyen tien hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.FORM_TRANSFER), TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[0]);

		log.info("TC_01_Step_13: Kiem tra tai khoan nguon hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SOURCE_ACCOUNT), sourceAccount.account);

		log.info("TC_01_Step_14: Kiem tra tai khoan dich hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SOURCE_ACCOUNT_VND).contains(distanceAccount.account));
		nameReceived = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.NAME_BENEFICI);

		log.info("TC_01_Step_15: Kiem tra so tien chuyen hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.AMOUNT).contains(addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY)));

		log.info("TC_01_Step_19: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.CONTENT), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_01_Step_20: Chon Phuong thuc nhap");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.METHOD_VALIDATE);

		log.info("TC_01_Step_21: Chon SMS OTP");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TittleData.SMART_OTP);

		log.info("TC_01_Step_16: Kiem tra so tien phi hien thi");
		String transferFee = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.FEE_AMOUNT);
		fee = convertAvailableBalanceCurrentcyOrFeeToLong(transferFee);

		log.info("TC_01_Step_18: Lay so tien phi");

		log.info("TC_01_Step_22: Click Tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_01_Step_23: Nhap OTP");
		transferInVCB.inputToDynamicSmartOTP(driver, newOTP, "com.VCB:id/otp");

		log.info("TC_01_Step_24: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_01_Step_25: Kiem  tra giao dich thanh cong");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE));

		log.info("TC_01_Step_26: Lay thoi gian tao giao dich");
		transferTime = transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE, "4").split(" ")[3];

		log.info("TC_01_Step_27: Lay ma giao dich");
		transactionNumber = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.CODE_TRANSFER);

		log.info("TC_01_Step_28: Kiem tra ten nguoi thu huong hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.NAME_BENEFICI), nameReceived);

		log.info("TC_01_Step_29: Kiem tra tai khoan dich hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.ACCOUNT_BENEFICI), distanceAccount.account);

		log.info("TC_01_Step_30: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.CONTENT), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_01_Step_31: Click thuc hien giao dich moi");
		transferInVCB.clickToDynamicButton(driver, TittleData.NEW_TRANSFER);

		log.info("TC_01_Step_32: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		log.info("TC_01_Step_33: Chon tai ngoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);
		transferInVCB.sleep(driver, 1000);

		log.info("TC_01_Step_34: Lay so du tai khoan chuyen");
		String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS);
		long afterBalanceAmountOfAccount1 = convertMoneyToLong(afterBalanceOfAccount1, Constants.VND_CURRENCY);

		log.info("TC_01_Step_35: Kiem tra so du tai khoan chuyen sau khi thuc hien giao dich");
		long transferMoney = convertMoneyToLong(TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY, Constants.VND_CURRENCY);
		verifyEquals(beforeBalanceAmountOfAccount1 - transferMoney - fee, afterBalanceAmountOfAccount1);

		log.info("TC_01_Step_36: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		log.info("TC_01_Step_37: Chon tai khoan nguoi nhan");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, distanceAccount.account);

		log.info("TC_01_Step_38: Lay so du kha dung tai khoan nhan");
		String afterBalanceOfAccount2 = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS);

		log.info("TC_01_Step_39: Kiem tra so du tai khoan nhan sau khi nhan tien");
		long afterBalanceAmountOfAccount2 = convertMoneyToLong(afterBalanceOfAccount2, Constants.VND_CURRENCY);
		verifyEquals(beforeBalanceAmountOfAccount2 + transferMoney, afterBalanceAmountOfAccount2);

	}

	@Test
	public void TC_02_KiemTraChiTietGiaoDichChuyenTienNgayCoPhiGiaoDichNguoiChuyenTraVNDVaXacThucBangSmartOTP() {

		log.info("TC_02_Step_01 : Click  nut Back");
		transferInVCB.clickToDynamicBackIcon(driver, TittleData.TRANSFER_IN_VCBANK);

		log.info("TC_02_Step_02: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_02_Step_03: Click Bao Cao Dao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, Text.REPORT_TRANSFER);

		transReport.clickToTextID(driver, "com.VCB:id/tvSelectTransType");

		log.info("TC_02_Step_05: Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, Text.TRANSFER_TYPE);

		log.info("TC_02_Step_06: Click Chon Tai Khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_02_Step_07: Chon tai Khoan chuyen");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_02_Step_08: CLick Tim Kiem");
		transReport.clickToDynamicButton(driver, Text.SEARCH);

		log.info("TC_02_Step_09: Lay ngay tao giao dich hien thi");
		String transferTimeInReport = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_02_Step_10: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport), transferTime);

		log.info("TC_02_Step_11: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(Text.NOTE_TEXT));

		log.info("TC_02_Step_12: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY) + " VND"));

		log.info("TC_02_Step_13: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_02_13: Kiem tra thoi gian tao giao dich hien thi");
		String reportTime1 = transReport.getDynamicTextInTransactionDetail(driver, Text.TIME_TRANSFER);
		verifyEquals(reportTime1, transferTimeInReport);

		log.info("TC_02_Step_16: Kiem tra thoi gian tao giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.NUMBER_TRANSFER), transactionNumber);

		log.info("TC_02_Step_17: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.ACCOUNT_SOURCE), sourceAccount.account);

		log.info("TC_02_Step_18: Kiem tra so tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.AMOUNT_SURPLUS), distanceAccount.account);

		log.info("TC_02_Step_19: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, Text.AMOUNT_TRANSFER).contains(addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY) + " VND"));

		log.info("TC_02_Step_20: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.NAME_OF_BENEFICI), nameReceived);

		log.info("TC_02_Step_21: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.FEE_TRANSFER), Text.SOURCE_ACCOUNT_PAY);

		log.info("TC_02_Step_22: Kiem tra so tien phi hien thi");
		if (fee > 0) {
			verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.FEE_AMOUNT), addCommasToLong(fee + "") + " VND");
		}
		log.info("TC_02_Step_23: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.TYPE_TRANSACTION), Text.TRANSFER_TYPE);

		log.info("TC_02_Step_24: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, Text.CONTENT_TRANSACTION).contains(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_02_Step_25: Click Back icon");
		transReport.clickToDynamicBackIcon(driver, Text.DETAIL_TRANSACTION);

		log.info("TC_02_Step_26: Chon Tai Khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_02_Step_27: Click chon tai khoan nhan");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, distanceAccount.account);

		log.info("TC_02_Step_28: Click Tim Kiem");
		transReport.clickToDynamicButton(driver, Text.SEARCH);

		log.info("TC_02_Step_09: Lay ngay tao giao dich hien thi");
		String transferTimeInReport2 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_02_Step_10: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport2), transferTime);

		log.info("TC_02_Step_21: Kiem tra noi dung giao dich hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(Text.NOTE_TEXT));

		log.info("TC_02_Step_22: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("+ " + addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY) + " VND"));

		log.info("TC_02_Step_23: Click vao bao bao giao dich chi tiet");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_02_Step_24: Lay time trong bao cao giao dich");
		String reportTime2 = transReport.getDynamicTextInTransactionDetail(driver, Text.TIME_TRANSFER);

		log.info("TC_02_Step_25: Kiem tra time trong bao cao giao dich");
		verifyEquals(reportTime2, transferTimeInReport);

		log.info("TC_02_Step_26: Kiem tra so giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.NUMBER_TRANSFER), transactionNumber);

		log.info("TC_02_Step_27: Kiem tra tai khoan trich no hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.ACCOUNT_SOURCE), sourceAccount.account);

		log.info("TC_02_Step_28: Kiem tra tai khoan ghi co hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.AMOUNT_SURPLUS), distanceAccount.account);

		log.info("TC_02_Step_29: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, Text.AMOUNT_TRANSFER).contains(addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY) + " VND"));

		log.info("TC_02_Step_30: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.NAME_OF_BENEFICI), nameReceived);

		log.info("TC_02_Step_31: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.FEE_TRANSFER), Text.SOURCE_ACCOUNT_PAY);

		log.info("TC_02_Step_32: Kiem tra so tien phi hien thi");
		if (fee > 0) {
			verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.FEE_AMOUNT), addCommasToLong(fee + "") + " VND");
		}
		log.info("TC_02_Step_33: Kiem tra noi dung giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.TYPE_TRANSACTION), Text.TRANSFER_TYPE);

		log.info("TC_02_Step_34: Kiem tra loaigiao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, Text.CONTENT_TRANSACTION).contains(Text.NOTE_TEXT));

		log.info("TC_02_Step_35: Click quay lai");
		transferInVCB.clickToDynamicBackIcon(driver, Text.DETAIL_TRANSACTION);

		log.info("TC_02_Step_36: Click quay lai");
		transferInVCB.clickToDynamicBackIcon(driver, Text.REPORT_TRANSFER);

		transferInVCB.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@Parameters({ "otp" })
	@Test
	public void TC_03_ChuyenTienNgayCoPhiGiaoDichNguoiChuyenTraUSDVaXacThucBangSmartOTP(String otp) {

		log.info("TC_03_Step 01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, Home_Text_Elements.HOME_TRANSFER_IN_VCB);

		log.info("TC_03_Step 02: Click chon tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		log.info("TC__03_Step_04:Click tai khoan nguon");
		List<String> listAccount = transferInVCB.getListSourceAccount(driver, Constants.VND_CURRENCY);
		log.info("TC_01_Step_05: Chon tai khoan dich");
		sourceAccount = transferInVCB.chooseSourceAccount(driver, Constants.MONEY_CHECK_USD, Constants.USD_CURRENCY);

		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);
		distanceAccount.account = transferInVCB.getDistanceAccount(driver, sourceAccount.account, listAccount);
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, distanceAccount.account);

		log.info("TC__03_Step_04: Lay so du tai khoan dich");
		String beforeBalanceOfAccount2 = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS);
		long beforeBalanceAmountOfAccount2 = convertMoneyToLong(beforeBalanceOfAccount2, Constants.VND_CURRENCY);

		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_03_Step 07: Lay so du tai khoan USD");
		String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS);
		double beforeBalanceAmountOfAccount1 = convertMoneyToDouble(beforeBalanceOfAccount1, Constants.USD_CURRENCY);

		log.info("TC_03_Step 08: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, distanceAccount.account, TittleData.INPUT_ACCOUNT_BENEFICI);

		log.info("TC_03_Step 09: Nhap so tien can chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER, TittleData.AMOUNT);

		log.info("TC_03_Step 11: Chon phuong thuc xac thuc");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TittleData.FEE_TRANSFER_SOURCE_ACCOUNT_PAY);

		log.info("TC_03_Step 12: Chon phi giao dich nguoi chuyen tra");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.COST[0]);

		log.info("TC_03_Step 13:  Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, TittleData.TRANSFER_INFO, "3");

		log.info("TC_03_Step 14: Click tiep tuc");
		exchangeRate = convertAvailableBalanceCurrentcyToDouble(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.REFERENCE_RATE).split("~")[1]);
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_03_Step 15: Kiem tra hinh thuc chuyen tien hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.FORM_TRANSFER), TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[0]);

		log.info("TC_03_Step 16: Kiem tra tai khoan nguon hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SOURCE_ACCOUNT), sourceAccount.account);

		log.info("TC_03_Step 17: Kiem tra tai khoan dich hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SOURCE_ACCOUNT_VND).contains(distanceAccount.account));

		log.info("TC_03_Step 18: Kiem tra so tien chuyen hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.AMOUNT).contains(addCommasToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " USD"));
		transferMoneyInVND = transferInVCB.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvQuyDoi");
		double transferMoneyInVND1 = convertMoneyToDouble(transferMoneyInVND, Constants.VND_CURRENCY);

		log.info("TC_03_Step 20: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.CONTENT), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_03_Step 21: Chon phuong thuc xac thuc");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.METHOD_VALIDATE);

		log.info("TC_03_Step 22: Chon SMS OTP");

		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TittleData.SMART_OTP);

		log.info("TC_03_Step 23: Kiem tra so tien phi hien thi");
		String transferFee = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.FEE_AMOUNT);
		fee = convertAvailableBalanceCurrentcyOrFeeToLong(transferFee);
		transferTime = transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE, "4").split(" ")[3];

		log.info("TC_03_Step 24: Lay tien phi chuyen USD");
		double usdTransferFee = convertVNeseMoneyToEUROOrUSD(fee + "", exchangeRate + "");

		log.info("TC_03_Step 25: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_01_Step_23: Nhap OTP");
		transferInVCB.inputToDynamicSmartOTP(driver, newOTP, "com.VCB:id/otp");

		log.info("TC_01_Step_24: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE));

		transferTime = transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE, "4").split(" ")[3];

		log.info("TC_03_Step 29: Kiem tra so tien chuyen hien thi");
		verifyEquals(transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE, "3"), addCommasToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " USD");

		log.info("TC_03_Step 30: Kiem tra ten nguoi thu huong hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.NAME_BENEFICI).contains(nameReceived));

		log.info("TC_03_Step 31: Kiem tra tai khoan dich hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.ACCOUNT_BENEFICI), distanceAccount.account);

		log.info("TC_03_Step 32: Lay ma giao dich");
		transactionNumber = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.CODE_TRANSFER);

		log.info("TC_03_Step 33: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.CONTENT), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_03_Step 34: Click thuc hien giao dich moi");
		transferInVCB.clickToDynamicButton(driver, TittleData.NEW_TRANSFER);

		log.info("TC_03_Step 35: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		log.info("TC_03_Step 36:Click USD account");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);
		transferInVCB.sleep(driver, 1000);

		log.info("TC_03_Step 37: Lay so du kha dung tai khoan USD");
		String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS);
		double afterBalanceAmountOfAccount1 = convertMoneyToDouble(afterBalanceOfAccount1, Constants.USD_CURRENCY);

		log.info("TC_03_Step 38: Convert so tien chuyen");
		double transferMoney = convertMoneyToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER, Constants.USD_CURRENCY);

		log.info("TC_03_Step 39: Kiem tra so du tai khoan USD sau khi chuyen tien");
		verifyEquals(convertMoneyToDouble((beforeBalanceAmountOfAccount1 - transferMoney - usdTransferFee) + "", Constants.USD_CURRENCY), afterBalanceAmountOfAccount1);

		log.info("TC_03_Step 40: Kiem tra tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		log.info("TC_03_Step 41: Click chon tai khoan chuyen den");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, distanceAccount.account);

		log.info("TC_03_Step 41: Lay so du kha dung tai khoan chuyen den");
		String afterBalanceOfAccount2 = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS);
		double afterBalanceAmountOfAccount2 = convertMoneyToDouble(afterBalanceOfAccount2, Constants.VND_CURRENCY);

		log.info("TC_03_Step 43: Kiem tra so du tai khoan duoc chuyen den");
		verifyEquals(beforeBalanceAmountOfAccount2 + transferMoneyInVND1, afterBalanceAmountOfAccount2);

	}

	@Test
	public void TC_04_KiemTraChiTietGiaoDichChuyenTienNgayCoPhiGiaoDichNguoiChuyenTraUSDVaXacThucBangSmartOTP() {

		log.info("TC_04_Step 01 : Click  nut Back");
		transferInVCB.clickToDynamicBackIcon(driver, TittleData.TRANSFER_IN_VCBANK);

		log.info("TC_04_Step 02: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_04_Step 03: Click Bao Cao Dao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, Text.REPORT_TRANSFER);

		log.info("TC_04_Step 04: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, Text.ALL_TYPE_TRANSFER);

		log.info("TC_04_Step 05:Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, Text.TRANSFER_TYPE);

		log.info("TC_04_Step 06: Click Chon Tai Khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_04_Step 07: Chon 1 tai Khoan");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_04_Step 08: CLick Tim Kiem");
		transReport.clickToDynamicButton(driver, Text.SEARCH);

		log.info("TC_04_Step 09:  Lay ngay tao giao dich hien thi");
		String transferTimeInReport = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC__04_Step_10: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport), convertDateTimeIgnoreHHmmss(transferTime));

		log.info("TC_04_Step 11: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_04_Step 12: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER + " USD"));

		log.info("TC_04_Step 13: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		String transferTime = transReport.getDynamicTextInTransactionDetail(driver, Text.TIME_TRANSFER);

		log.info("TC_04_Step 16:Kiem tra so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.NUMBER_TRANSFER), transactionNumber);

		log.info("TC_04_Step 17: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.ACCOUNT_SOURCE), sourceAccount.account);

		log.info("TC_04_Step 18: Kiem tra so tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.AMOUNT_SURPLUS), distanceAccount.account);

		log.info("TC_04_Step 19: Kiem tra so tien giao dich hien thi ");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, Text.AMOUNT_TRANSFER).equals(addCommasToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " USD"));

		log.info("TC_04_Step 20: Kiem tra so tien quy doi hien thi ");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, Text.AMOUNT_CHANGE).contains(transferMoneyInVND));

		log.info("TC_04_Step 22: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.FEE_TRANSFER), TransferMoneyInVCB_Data.InputDataInVCB.COST[0]);

		log.info("TC_04_Step 23: Kiem tra so tien phi hien thi");
		if (fee > 0) {
			verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.FEE_AMOUNT), addCommasToLong(fee + "") + " VND");
		}
		log.info("TC_04_Step 25: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, Text.CONTENT_TRANSACTION).contains(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_04_Step 26: Click Back icon");
		transReport.clickToDynamicBackIcon(driver, Text.DETAIL_TRANSACTION);

		log.info("TC_04_Step 27: Chon Tai Khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_04_Step 28: Click chon tai khoan nhan");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_04_Step 29: Click Tim Kiem");
		transReport.clickToDynamicButton(driver, Text.SEARCH);

		log.info("TC__04_Step_30: Lay ngay tao giao dich hien thi");
		String transferTimeInReport2 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC__04_Step_31: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport2), convertDateTimeIgnoreHHmmss(transferTime));

		log.info("TC_04_Step 32: Kiem tra noi dung giao dich hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(Text.NOTE_TEXT));

		log.info("TC_04_Step 34: Click vao bao bao giao dich chi tiet");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_04_Step 35: Kiem tra thoi gian giao dich");
		String reportTime2 = transReport.getDynamicTextInTransactionDetail(driver, Text.TIME_TRANSFER);
		verifyEquals(reportTime2, transferTimeInReport2);

		log.info("TC_04_Step 37: Kiem tra so giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.NUMBER_TRANSFER), transactionNumber);

		log.info("TC_04_Step 38:Kiem tra tai khoan trich no hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.ACCOUNT_SOURCE), sourceAccount.account);

		log.info("TC_04_Step 39: Kiem tra tai khoan ghi co hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.AMOUNT_SURPLUS), distanceAccount.account);

		log.info("TC_04_Step 40: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, Text.AMOUNT_TRANSFER).contains(addCommasToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " USD"));

		log.info("TC_04_Step 41: Kiem tra so tien quy doi hien thi ");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, Text.AMOUNT_CHANGE).contains(transferMoneyInVND));

		log.info("TC_04_Step 43: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.FEE_TRANSFER), Text.SOURCE_ACCOUNT_PAY);

		log.info("TC_04_Step 44:Kiem tra so tien phi hien thi");
		if (fee > 0) {
			verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.FEE_AMOUNT), addCommasToLong(fee + "") + " VND");
		}
		log.info("TC_04_Step 45:Kiem tra noi dung giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, Text.CONTENT_TRANSACTION).contains(Text.NOTE_TEXT));

		log.info("TC_04_Step 46: Kiem tra loaigiao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.TYPE_TRANSACTION), Text.TRANSFER_TYPE);

		transferInVCB.clickToDynamicBackIcon(driver, Text.DETAIL_TRANSACTION);

		log.info("TC_04_Step 48: Click quay lai");
		transferInVCB.clickToDynamicBackIcon(driver, Text.REPORT_TRANSFER);
		transferInVCB.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");

	}

	@AfterClass(alwaysRun = true)

	public void afterClass() {
		closeApp();
		service.stop();
	}

}
