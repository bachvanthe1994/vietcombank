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
import pageObjects.TransactionReportPageObject;
import pageObjects.TransferMoneyInVcbPageObject;
import vietcombank_test_data.TransferMoneyInVCB_Data;
import vietcombank_test_data.TransferMoneyInVCB_Data.InputDataInVCB;
import vietcombank_test_data.TransferMoneyInVCB_Data.TittleData;
import vietcombank_test_data.TransferMoneyStatus_Data.Text;

public class Transfer_Money_Immedidately_And_Report_2 extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferMoneyInVcbPageObject transferInVCB;
	private TransactionReportPageObject transReport;
	private String transferTime;
	private String transactionNumber;
	private double exchangeRate;
	private long fee;
	private String transferMoneyInVND;
	SourceAccountModel sourceAccount = new SourceAccountModel();
	SourceAccountModel distanceAccount = new SourceAccountModel();
	
	String account_other_owner, name_other_owner,nameReceived;


	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass","otp" })
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
		login.Global_login1(phone, pass, opt);

		log.info("Before class_Step_10: Scroll den trang thai lenh chuyen tien");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.scrollDownToText(driver, TittleData.STATUS_TRANSACTION);
		nameReceived = getDataInCell(3);
		account_other_owner = getDataInCell(0);
		name_other_owner = getDataInCell(1);
	}

	@Parameters({"pass"})
	@Test
	public void TC_01_ChuyenTienNgayCoPhiGiaoDichNguoiChuyenTraVNDVaXacThucBangMatKhau(String pass) {

		log.info("TC_01_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, InputDataInVCB.TRANSFER_TYPE);

		log.info("TC_01_Step_02:Click tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);

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
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SOURCE_ACCOUNT_VND), distanceAccount.account + "/ ");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, nameReceived));

		log.info("TC_01_Step_15: Kiem tra so tien chuyen hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.AMOUNT).contains(addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY)));

		log.info("TC_01_Step_17: Kiem tra phuong thuc tra phi");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.InputDataInVCB.COST[0]));

		log.info("TC_01_Step_19: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.CONTENT), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_01_Step_20: Chon Phuong thuc nhap");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.METHOD_VALIDATE);

		log.info("TC_01_Step_21: Chon SMS mat khau");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TittleData.PASSWORD);

		log.info("TC_01_Step_16: Kiem tra so tien phi hien thi");
		String transferFee = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.FEE_AMOUNT);
		fee = convertAvailableBalanceCurrentcyOrFeeToLong(transferFee);

		log.info("TC_01_Step_18: Lay so tien phi");

		log.info("TC_01_Step_22: Click Tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_01_Step_23: Nhap mat khau");
		transferInVCB.inputToDynamicPopupPasswordInput(driver, pass, TittleData.CONTINUE_BTN);

		log.info("TC_01_Step_24: Click tiep tuc");
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
	public void TC_02_KiemTraChiTietGiaoDichChuyenTienNgayCoPhiGiaoDichNguoiChuyenTraVNDVaXacThucBangMatKhau() {
		log.info("TC_02_Step_01 : Click  nut Back");
		transferInVCB.clickToDynamicBackIcon(driver, TittleData.TRANSFER_IN_VCB);

		log.info("TC_02_Step_02: Click vao More Icon");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_02_Step_03: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, Text.REPORT_TRANSFER);

		transReport.clickToTextID(driver, "com.VCB:id/tvSelectTransType");

		log.info("TC_02_Step_05: Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TittleData.TRANSFER_IN_VCB);

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
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

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
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.FEE_TRANSFER), TransferMoneyInVCB_Data.InputDataInVCB.COST[0]);

		log.info("TC_02_Step_22: Kiem tra so tien phi hien thi");
		if (fee > 0) {
			verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.FEE_AMOUNT), addCommasToLong(fee + "") + " VND");
		}
		log.info("TC_02_Step_23: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.TYPE_TRANSACTION), TittleData.TRANSFER_IN_VCB);

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
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

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
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.FEE_TRANSFER), TransferMoneyInVCB_Data.InputDataInVCB.COST[0]);

		log.info("TC_02_Step_32: Kiem tra so tien phi hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.FEE_AMOUNT), addCommasToLong(fee + "") + " VND");

		log.info("TC_02_Step_33: Kiem tra noi dung giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.TYPE_TRANSACTION), TittleData.TRANSFER_IN_VCB);

		log.info("TC_02_Step_34: Kiem tra loaigiao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, Text.CONTENT_TRANSACTION).contains(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_02_Step_35: Click quay lai");
		transferInVCB.clickToDynamicBackIcon(driver, Text.DETAIL_TRANSACTION);

		log.info("TC_02_Step_36: Click quay lai");
		transferInVCB.clickToDynamicBackIcon(driver, Text.REPORT_TRANSFER);

		transferInVCB.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@Parameters({"pass"})
	@Test
	public void TC_03_ChuyenTienNgayCoPhiGiaoDichNguoiNhanTraVNDVaXacThucBangMatKhau(String pass) {


		log.info("TC_03_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, InputDataInVCB.TRANSFER_TYPE);

		log.info("TC_03_Step_02:Click tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);

		log.info("TC_03_Step_04:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);
		List<String> listAccount = transferInVCB.getListSourceAccount(driver, Constants.VND_CURRENCY);
		log.info("TC_03_Step_05: Chon tai khoan dich");
		sourceAccount = transferInVCB.chooseSourceAccount(driver, Constants.AMOUNT_VND, Constants.VND_CURRENCY);

		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);
		distanceAccount.account = transferInVCB.getDistanceAccount(driver, sourceAccount.account, listAccount);
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, distanceAccount.account);
		log.info("TC_03_Step_04: Lay so du tai khoan dich");
		String beforeBalanceOfAccount2 = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS);
		long beforeBalanceAmountOfAccount2 = convertMoneyToLong(beforeBalanceOfAccount2, Constants.VND_CURRENCY);
		
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

	

		log.info("TC_03_Step_07: Lay so du tai khoan chuyen");
		String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS);
		long beforeBalanceAmountOfAccount1 = convertMoneyToLong(beforeBalanceOfAccount1, Constants.VND_CURRENCY);

		log.info("TC_03_Step_08: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, distanceAccount.account, TittleData.INPUT_ACCOUNT_BENEFICI);

		log.info("TC_03_Step_09: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY, TittleData.AMOUNT);

		log.info("TC_03_Step_10: Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, TittleData.TRANSFER_INFO, "3");
		
		log.info("TC_03_Step_10: Click phi giao dich nguoi chuyen tra");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TittleData.FEE_TRANSFER_SOURCE_ACCOUNT_PAY);

		log.info("TC_03_Step_11: Chon nguoi nhan tra");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TittleData.BENEFICI_PAY);
		
		log.info("TC_03_Step_11: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_03_Step_12: Kiem tra hinh thuc chuyen tien hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.FORM_TRANSFER), TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[0]);

		log.info("TC_03_Step_13: Kiem tra tai khoan nguon hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SOURCE_ACCOUNT), sourceAccount.account);

		log.info("TC_03_Step_14: Kiem tra tai khoan dich hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SOURCE_ACCOUNT_VND), distanceAccount.account + "/ ");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, nameReceived));

		log.info("TC_03_Step_15: Kiem tra so tien chuyen hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.AMOUNT).contains(addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY)));

		log.info("TC_03_Step_17: Kiem tra phuong thuc tra phi");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.InputDataInVCB.COST[1]));

		log.info("TC_03_Step_19: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.CONTENT), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_03_Step_20: Chon Phuong thuc nhap");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.METHOD_VALIDATE);

		log.info("TC_03_Step_21: Chon SMS mat khau");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TittleData.PASSWORD);

		log.info("TC_03_Step_16: Kiem tra so tien phi hien thi");
		String transferFee = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.FEE_AMOUNT);
		fee = convertAvailableBalanceCurrentcyOrFeeToLong(transferFee);

		log.info("TC_03_Step_18: Lay so tien phi");

		log.info("TC_03_Step_22: Click Tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_03_Step_23: Nhap mat khau");
		transferInVCB.inputToDynamicPopupPasswordInput(driver,pass , TittleData.CONTINUE_BTN);

		log.info("TC_03_Step_24: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_03_Step_25: Kiem  tra giao dich thanh cong");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE));

		log.info("TC_03_Step_26: Lay thoi gian tao giao dich");
		transferTime = transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE, "4").split(" ")[3];

		log.info("TC_03_Step_27: Lay ma giao dich");
		transactionNumber = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.CODE_TRANSFER);

		log.info("TC_03_Step_28: Kiem tra ten nguoi thu huong hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.NAME_BENEFICI), nameReceived);

		log.info("TC_03_Step_29: Kiem tra tai khoan dich hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.ACCOUNT_BENEFICI), distanceAccount.account);

		log.info("TC_03_Step_30: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.CONTENT), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_03_Step_31: Click thuc hien giao dich moi");
		transferInVCB.clickToDynamicButton(driver, TittleData.NEW_TRANSFER);

		log.info("TC_03_Step_32: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		log.info("TC_03_Step_33: Chon tai ngoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);
		transferInVCB.sleep(driver, 1000);

		log.info("TC_03_Step_34: Lay so du tai khoan chuyen");
		String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS);
		long afterBalanceAmountOfAccount1 = convertMoneyToLong(afterBalanceOfAccount1, Constants.VND_CURRENCY);

		log.info("TC_03_Step_35: Kiem tra so du tai khoan chuyen sau khi thuc hien giao dich");
		long transferMoney = convertMoneyToLong(TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY, Constants.VND_CURRENCY);
		verifyEquals(beforeBalanceAmountOfAccount1 - transferMoney, afterBalanceAmountOfAccount1);

		log.info("TC_03_Step_36: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		log.info("TC_03_Step_37: Chon tai khoan nguoi nhan");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, distanceAccount.account);

		log.info("TC_03_Step_38: Lay so du kha dung tai khoan nhan");
		String afterBalanceOfAccount2 = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS);

		log.info("TC_03_Step_39: Kiem tra so du tai khoan nhan sau khi nhan tien");
		long afterBalanceAmountOfAccount2 = convertMoneyToLong(afterBalanceOfAccount2, Constants.VND_CURRENCY);
		verifyEquals(beforeBalanceAmountOfAccount2 + transferMoney-fee, afterBalanceAmountOfAccount2);

	}

	@Test
	public void TC_04_KiemTraChiTietGiaoDichChuyenTienNgayCoPhiGiaoDichNguoiNhanTraVNDVaXacThucBangMatKhau() {

		log.info("TC_04_Step_01:Click  nut Back");
		transferInVCB.clickToDynamicBackIcon(driver, TittleData.TRANSFER_IN_VCB);

		log.info("TC_04_Step_02:Click vao More Icon");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_04_Step_03:Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, Text.REPORT_TRANSFER);

		log.info("TC_04_Step_04: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, Text.ALL_TYPE_TRANSFER);

		log.info("TC_04_Step_05: Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TittleData.TRANSFER_IN_VCB);

		log.info("TC_04_Step_06: Click Chon Tai Khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_04_Step_07: Chon tai Khoan trich no");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, distanceAccount.account);

		log.info("TC_04_Step_08: CLick Tim Kiem");
		transReport.clickToDynamicButton(driver, Text.SEARCH);

		log.info("TC_02_Step_09: Lay ngay tao giao dich hien thi");
		String transferTimeIneport = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_02_Step_10: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeIneport), transferTime);

		log.info("TC_04_Step_11: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_04_Step_12: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("+ " + addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY) + " VND"));

		log.info("TC_04_Step_13: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_04_Step_14: Kiem tra ngay giao dich hien thi");
		String reportTime1 = transReport.getDynamicTextInTransactionDetail(driver, Text.TIME_TRANSFER);
		verifyEquals(reportTime1, transferTimeIneport);

		log.info("TC_04_Step_16: Kiem tra so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.NUMBER_TRANSFER), transactionNumber);

		log.info("TC_04_Step_17: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.ACCOUNT_SOURCE), sourceAccount.account);

		log.info("TC_04_Step_18: Kiem tra so tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.AMOUNT_SURPLUS), distanceAccount.account);

		log.info("TC_04_Step_19: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, Text.AMOUNT_TRANSFER).contains(addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY) + " VND"));

		log.info("TC_04_Step_20: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.NAME_OF_BENEFICI), nameReceived);

		log.info("TC_04_Step_21: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.FEE_TRANSFER), TransferMoneyInVCB_Data.InputDataInVCB.COST[1]);

		log.info("TC_04_Step_22: Kiem tra so tien phi hien thi");
		if (fee > 0) {
			verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.FEE_AMOUNT), addCommasToLong(fee + "") + " VND");
		}
		log.info("TC_04_Step_23: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.TYPE_TRANSACTION), TittleData.TRANSFER_IN_VCB);

		log.info("TC_04_Step_24: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, Text.CONTENT_TRANSACTION).contains(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_04_Step_25: Click Back icon");
		transReport.clickToDynamicBackIcon(driver, Text.DETAIL_TRANSACTION);

		log.info("TC_04_Step_26: Chon Tai Khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_04_Step_27: Click chon tai khoan nhan");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_04_Step_28: Click Tim Kiem");
		transReport.clickToDynamicButton(driver, Text.SEARCH);

		log.info("TC_04_Step_29: Kiem tra ngay tao giao dich hien thi");
		String transferTimeIneport2 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_04_Step_30: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeIneport2), transferTime);

		log.info("TC_04_Step_31: Kiem tra noi dung giao dich hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_04_Step_32: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY) + " VND"));

		log.info("TC_04_Step_33: Click vao bao bao giao dich chi tiet");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_04_Step_34: Kiem tra ngay tao giao dich hien thi");
		String reportTime2 = transReport.getDynamicTextInTransactionDetail(driver, Text.TIME_TRANSFER);
		verifyEquals(reportTime2, transferTimeIneport2);

		log.info("TC_04_Step_36: Kiem tra so giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.NUMBER_TRANSFER), transactionNumber);

		log.info("TC_04_Step_37: Kiem tra tai khoan trich no hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.ACCOUNT_SOURCE), sourceAccount.account);

		log.info("TC_04_Step_38: Kiem tra tai khoan ghi co hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.AMOUNT_SURPLUS), distanceAccount.account);

		log.info("TC_04_Step_39: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, Text.AMOUNT_TRANSFER).contains(addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY) + " VND"));

		log.info("TC_04_Step_40: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.NAME_OF_BENEFICI), nameReceived);

		log.info("TC_04_Step_41: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.FEE_TRANSFER), TransferMoneyInVCB_Data.InputDataInVCB.COST[1]);

		log.info("TC_04_Step_42: Kiem tra so tien phi hien thi");
		if (fee > 0) {
			verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.FEE_AMOUNT), addCommasToLong(fee + "") + " VND");
		}
		log.info("TC_04_Step_43: Kiem tra loai giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.TYPE_TRANSACTION), TittleData.TRANSFER_IN_VCB);

		log.info("TC_04_Step_44: Kiem tra noi dung giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, Text.CONTENT_TRANSACTION).contains(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_04_Step_45: Click quay lai");
		transferInVCB.clickToDynamicBackIcon(driver, Text.DETAIL_TRANSACTION);

		log.info("TC_04_Step_46: Click quay lai");
		transferInVCB.clickToDynamicBackIcon(driver, Text.REPORT_TRANSFER);
		transferInVCB.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}
	
	@Parameters({"pass"})
	@Test
	public void TC_05_ChuyenTienNgayCoPhiGiaoDichNguoiChuyenTraUSDVaXacThucBangMatKhau(String pass) {

		log.info("TC05_Step 01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, InputDataInVCB.TRANSFER_TYPE);

		log.info("TC05_Step 02: Click chon tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);

		log.info("TC_01_Step_04:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);
		List<String> listAccount = transferInVCB.getListSourceAccount(driver, Constants.VND_CURRENCY);
		log.info("TC_01_Step_05: Chon tai khoan dich");
		sourceAccount = transferInVCB.chooseSourceAccount(driver, Constants.MONEY_CHECK_USD, Constants.USD_CURRENCY);

		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);
		distanceAccount.account = transferInVCB.getDistanceAccount(driver, sourceAccount.account, listAccount);
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, distanceAccount.account);
		log.info("TC_01_Step_04: Lay so du tai khoan dich");
		String beforeBalanceOfAccount2 = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS);
		long beforeBalanceAmountOfAccount2 = convertMoneyToLong(beforeBalanceOfAccount2, Constants.VND_CURRENCY);
		
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);


		log.info("TC05_Step 07: Lay so du tai khoan USD");
		String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS);
		double beforeBalanceAmountOfAccount1 = convertMoneyToDouble(beforeBalanceOfAccount1, Constants.USD_CURRENCY);

		log.info("TC05_Step 08: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, distanceAccount.account, TittleData.INPUT_ACCOUNT_BENEFICI);

		log.info("TC05_Step 09: Nhap so tien can chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER, TittleData.AMOUNT);

		log.info("TC05_Step 11: Chon phuong thuc xac thuc");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TittleData.FEE_TRANSFER_SOURCE_ACCOUNT_PAY);

		log.info("TC05_Step 12: Chon phi giao dich nguoi chuyen tra");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.COST[0]);
		
		log.info("TC05_Step 13:  Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, TittleData.TRANSFER_INFO, "3");

		log.info("TC05_Step 14: Click tiep tuc");
		exchangeRate = convertAvailableBalanceCurrentcyToDouble(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tỷ giá quy đổi tham khảo").split("~")[1]);
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC05_Step 15: Kiem tra hinh thuc chuyen tien hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.FORM_TRANSFER), TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[0]);

		log.info("TC05_Step 16: Kiem tra tai khoan nguon hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SOURCE_ACCOUNT), sourceAccount.account);

		log.info("TC05_Step 17: Kiem tra tai khoan dich hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SOURCE_ACCOUNT_VND).contains(distanceAccount.account));

		log.info("TC05_Step 18: Kiem tra so tien chuyen hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.AMOUNT).contains(addCommasToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " USD"));
		transferMoneyInVND = transferInVCB.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvQuyDoi");
		double transferMoneyInVND1 = convertMoneyToDouble(transferMoneyInVND, Constants.VND_CURRENCY);

		log.info("TC05_Step 19: Kiem tra Nguoi chuyen tra hien thi");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.InputDataInVCB.COST[0]));

		log.info("TC05_Step 20: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.CONTENT), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC05_Step 21: Chon phuong thuc xac thuc");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.METHOD_VALIDATE);

		log.info("TC05_Step 22: Chon SMS mat khau");
		;
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TittleData.PASSWORD);

		log.info("TC05_Step 23: Kiem tra so tien phi hien thi");
		String transferFee = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.FEE_AMOUNT);
		fee = convertAvailableBalanceCurrentcyOrFeeToLong(transferFee);

		log.info("TC05_Step 24: Lay tien phi chuyen USD");
		double usdTransferFee = convertVNeseMoneyToEUROOrUSD(fee + "", exchangeRate + "");

		log.info("TC05_Step 25: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC05_Step 26: Dien mat khau");
		transferInVCB.inputToDynamicPopupPasswordInput(driver, pass, TittleData.CONTINUE_BTN);

		log.info("TC05_Step 27: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE));

		log.info("TC05_Step 28: Lay thoi gian tao");
		transferTime = transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE, "4").split(" ")[3];

		log.info("TC05_Step 29: Kiem tra so tien chuyen hien thi");
		verifyEquals(transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE, "3"), addCommasToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " USD");

		log.info("TC05_Step 30: Kiem tra ten nguoi thu huong hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.NAME_BENEFICI).contains(nameReceived));

		log.info("TC05_Step 31: Kiem tra tai khoan dich hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.ACCOUNT_BENEFICI), distanceAccount.account);

		log.info("TC05_Step 32: Lay ma giao dich");
		transactionNumber = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.CODE_TRANSFER);

		log.info("TC05_Step 33: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.CONTENT), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC05_Step 34: Click thuc hien giao dich moi");
		transferInVCB.clickToDynamicButton(driver, TittleData.NEW_TRANSFER);

		log.info("TC05_Step 35: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		log.info("TC05_Step 36:Click USD account");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);
		transferInVCB.sleep(driver, 1000);

		log.info("TC05_Step 37: Lay so du kha dung tai khoan USD");
		String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS);
		double afterBalanceAmountOfAccount1 = convertMoneyToDouble(afterBalanceOfAccount1, Constants.USD_CURRENCY);

		log.info("TC05_Step 38: Convert so tien chuyen");
		double transferMoney = convertMoneyToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER, Constants.USD_CURRENCY);

		log.info("TC05_Step 39: Kiem tra so du tai khoan USD sau khi chuyen tien");
		verifyEquals(convertMoneyToDouble((beforeBalanceAmountOfAccount1 - transferMoney - usdTransferFee) + "", Constants.USD_CURRENCY), afterBalanceAmountOfAccount1);

		log.info("TC05_Step 40: Kiem tra tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		log.info("TC05_Step 41: Click chon tai khoan chuyen den");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, distanceAccount.account);

		log.info("TC05_Step 41: Lay so du kha dung tai khoan chuyen den");
		String afterBalanceOfAccount2 = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS);
		double afterBalanceAmountOfAccount2 = convertMoneyToDouble(afterBalanceOfAccount2, Constants.VND_CURRENCY);

		log.info("TC05_Step 43: Kiem tra so du tai khoan duoc chuyen den");
		verifyEquals(beforeBalanceAmountOfAccount2 + transferMoneyInVND1, afterBalanceAmountOfAccount2);

	}

	@Test
	public void TC_06_KiemTraChiTietGiaoDichChuyenTienNgayCoPhiGiaoDichNguoiChuyenTraUSDVaXacThucBangMatKhau() {

		log.info("TC06_Step 01 : Click  nut Back");
		transferInVCB.clickToDynamicBackIcon(driver, TittleData.TRANSFER_IN_VCB);

		log.info("TC06_Step 02: Click vao More Icon");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC06_Step 03: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, Text.REPORT_TRANSFER);

		log.info("TC06_Step 04: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, Text.ALL_TYPE_TRANSFER);

		log.info("TC06_Step 05:Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TittleData.TRANSFER_IN_VCB);

		log.info("TC06_Step 06: Click Chon Tai Khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC06_Step 07: Chon 1 tai Khoan");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC06_Step 08: CLick Tim Kiem");
		transReport.clickToDynamicButton(driver, Text.SEARCH);

		log.info("TC06_Step 09:  Lay ngay tao giao dich hien thi");
		String transferTimeInReport = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_06_Step_10: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport), transferTime);

		log.info("TC06_Step 11: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		
		log.info("TC06_Step 13: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC06_Step 14: Kiem tra ngay giao dich hien thi");
		String reportTime1 = transReport.getDynamicTextInTransactionDetail(driver, Text.TIME_TRANSFER);
		verifyEquals(reportTime1, transferTimeInReport);

		log.info("TC06_Step 16:Kiem tra so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.NUMBER_TRANSFER), transactionNumber);

		log.info("TC06_Step 17: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.ACCOUNT_SOURCE), sourceAccount.account);

		log.info("TC06_Step 18: Kiem tra so tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.AMOUNT_SURPLUS), distanceAccount.account);

		log.info("TC06_Step 19: Kiem tra so tien giao dich hien thi ");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, Text.AMOUNT_TRANSFER).equals(addCommasToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " USD"));

		log.info("TC06_Step 20: Kiem tra so tien quy doi hien thi ");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, Text.AMOUNT_CHANGE).contains(transferMoneyInVND));

		log.info("TC06_Step 21: Kiem tra ten nguoi huong hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, Text.NAME_OF_BENEFICI).contains(nameReceived));

		log.info("TC06_Step 22: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.FEE_TRANSFER), TransferMoneyInVCB_Data.InputDataInVCB.COST[0]);

		log.info("TC06_Step 23: Kiem tra so tien phi hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.FEE_AMOUNT), addCommasToLong(fee + "") + " VND");

		log.info("TC06_Step 24: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.TYPE_TRANSACTION), TittleData.TRANSFER_IN_VCB);

		log.info("TC06_Step 25: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, Text.CONTENT_TRANSACTION).contains(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC06_Step 26: Click Back icon");
		transReport.clickToDynamicBackIcon(driver, Text.DETAIL_TRANSACTION);

		log.info("TC06_Step 27: Chon Tai Khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC06_Step 28: Click chon tai khoan nhan");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC06_Step 29: Click Tim Kiem");
		transReport.clickToDynamicButton(driver, Text.SEARCH);

		log.info("TC_06_Step_30: Lay ngay tao giao dich hien thi");
		String transferTimeInReport2 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_06_Step_31: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport2), transferTime);

		log.info("TC06_Step 32: Kiem tra noi dung giao dich hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC06_Step 34: Click vao bao bao giao dich chi tiet");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC06_Step 35: Kiem tra thoi gian giao dich");
		String reportTime2 = transReport.getDynamicTextInTransactionDetail(driver, Text.TIME_TRANSFER);
		verifyEquals(reportTime2, transferTimeInReport2);

		log.info("TC06_Step 37: Kiem tra so giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.NUMBER_TRANSFER), transactionNumber);

		log.info("TC06_Step 38:Kiem tra tai khoan trich no hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TittleData.ACCOUNT_SOURCE), sourceAccount.account);

		log.info("TC06_Step 39: Kiem tra tai khoan ghi co hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.AMOUNT_SURPLUS), distanceAccount.account);

		log.info("TC06_Step 40: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, Text.AMOUNT_TRANSFER).contains(addCommasToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " USD"));

		log.info("TC06_Step 41: Kiem tra so tien quy doi hien thi ");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, Text.AMOUNT_CHANGE).contains(transferMoneyInVND));

		log.info("TC06_Step 42: Kiem tra ten nguoi huong hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, Text.NAME_OF_BENEFICI).contains( nameReceived));

		log.info("TC06_Step 43: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.FEE_TRANSFER), TransferMoneyInVCB_Data.InputDataInVCB.COST[0]);

		log.info("TC06_Step 44:Kiem tra so tien phi hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.FEE_AMOUNT), addCommasToLong(fee + "") + " VND");

		log.info("TC06_Step 45:Kiem tra noi dung giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, Text.CONTENT_TRANSACTION).contains(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC06_Step 46: Kiem tra loaigiao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.TYPE_TRANSACTION), TittleData.TRANSFER_IN_VCB);

		log.info("TC06_Step 47: Click quay lai");
		transferInVCB.clickToDynamicBackIcon(driver, Text.DETAIL_TRANSACTION);

		log.info("TC06_Step 48: Click quay lai");
		transferInVCB.clickToDynamicBackIcon(driver, Text.REPORT_TRANSFER);
		transferInVCB.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");

	}

	@Parameters({"pass"})
	@Test
	public void TC_07_ChuyenTienNgayCoPhiGiaoDichNguoiNhanTraEURVaXacThucBangMatKhau(String pass) {

		log.info("TC_07_Step 01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, InputDataInVCB.TRANSFER_TYPE);

		log.info("TC_07_Step 02: Click chon tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);

		log.info("TC_01_Step_04:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);
		List<String> listAccount = transferInVCB.getListSourceAccount(driver, Constants.VND_CURRENCY);
		log.info("TC_01_Step_05: Chon tai khoan dich");
		sourceAccount = transferInVCB.chooseSourceAccount(driver, Constants.MONEY_CHECK_EUR, Constants.EUR_CURRENCY);

		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);
		distanceAccount.account = transferInVCB.getDistanceAccount(driver, sourceAccount.account, listAccount);
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, distanceAccount.account);
		log.info("TC_01_Step_04: Lay so du tai khoan dich");
		String beforeBalanceOfAccount2 = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS);
		long beforeBalanceAmountOfAccount2 = convertMoneyToLong(beforeBalanceOfAccount2, Constants.VND_CURRENCY);
		
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_07_Step 07: Lay so du tai khoan USD");
		String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS);
		double beforeBalanceAmountOfAccount1 = convertMoneyToDouble(beforeBalanceOfAccount1, Constants.EUR_CURRENCY);

		log.info("TC_07_Step 08: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, distanceAccount.account, TittleData.INPUT_ACCOUNT_BENEFICI);

		log.info("TC_07_Step 09: Nhap so tien can chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER, TittleData.AMOUNT);

		log.info("TC_07_Step 11: Chon phuong thuc xac thuc");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TittleData.FEE_TRANSFER_SOURCE_ACCOUNT_PAY);

		log.info("TC_07_Step 12: Chon phi giao dich nguoi nhan tra");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TittleData.BENEFICI_PAY);

		log.info("TC_07_Step 13:  Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, TittleData.TRANSFER_INFO, "3");

		log.info("TC_07_Step 14: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_07_Step 15: Kiem tra hinh thuc chuyen tien hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.FORM_TRANSFER), TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[0]);

		log.info("TC_07_Step 16: Kiem tra tai khoan nguon hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SOURCE_ACCOUNT), sourceAccount.account);

		log.info("TC_07_Step 17: Kiem tra tai khoan dich hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SOURCE_ACCOUNT_VND).contains(distanceAccount.account));

		log.info("TC_07_Step 18: Kiem tra so tien chuyen hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.AMOUNT).contains(addCommasToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " EUR"));
		transferMoneyInVND = transferInVCB.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvQuyDoi");
		double transferMoneyInVND1 = convertMoneyToDouble(transferMoneyInVND, Constants.VND_CURRENCY);

		log.info("TC_07_Step 19: Kiem tra Nguoi nhan tra hien thi");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.InputDataInVCB.COST[1]));

		log.info("TC_07_Step 20: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.CONTENT), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_07_Step 21: Chon phuong thuc xac thuc");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.METHOD_VALIDATE);

		log.info("TC_07_Step 22: Chon SMS mat khau");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TittleData.PASSWORD);

		log.info("TC_07_Step 23: Kiem tra so tien phi hien thi");
		String transferFee = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.FEE_AMOUNT);
		fee = convertAvailableBalanceCurrentcyOrFeeToLong(transferFee);

		log.info("TC_07_Step 25: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_07_Step 26: Dien mat khau");
		transferInVCB.inputToDynamicPopupPasswordInput(driver, pass, TittleData.CONTINUE_BTN);

		log.info("TC_07_Step 27: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE));

		log.info("TC_07_Step 28: Lay thoi gian tao");
		log.info("TC_01_Step_26: Lay thoi gian tao giao dich");
		transferTime = transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE, "4").split(" ")[3];

		log.info("TC_07_Step 29: Kiem tra so tien chuyen hien thi");
		verifyEquals(transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE, "3"), addCommasToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " EUR");

		log.info("TC_07_Step 30: Kiem tra ten nguoi thu huong hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.NAME_BENEFICI).contains(nameReceived));

		log.info("TC_07_Step 31: Kiem tra tai khoan dich hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.ACCOUNT_BENEFICI), distanceAccount.account);

		log.info("TC_07_Step 32: Lay ma giao dich");
		transactionNumber = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.CODE_TRANSFER);

		log.info("TC_07_Step 33: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.CONTENT), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_07_Step 34: Click thuc hien giao dich moi");
		transferInVCB.clickToDynamicButton(driver, TittleData.NEW_TRANSFER);

		log.info("TC_07_Step 35: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		log.info("TC_07_Step 36:Click EUR account");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);
		transferInVCB.sleep(driver, 1000);

		log.info("TC_07_Step 37: Lay so du kha dung tai khoan EUR");
		double afterBalanceAmountOfAccount1 = convertMoneyToDouble(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS), Constants.EUR_CURRENCY);

		log.info("TC_07_Step 38: Convert so tien chuyen");
		double transferMoney = convertMoneyToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER, Constants.EUR_CURRENCY);

		log.info("TC_07_Step 39: Kiem tra so du tai khoan USD sau khi chuyen tien");
		verifyEquals(beforeBalanceAmountOfAccount1 - transferMoney, afterBalanceAmountOfAccount1);

		log.info("TC_07_Step 40: Kiem tra tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		log.info("TC_07_Step 41: Click chon tai khoan chuyen den");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, distanceAccount.account);
		transferInVCB.sleep(driver, 1000);

		log.info("TC_07_Step 42: Lay so du kha dung tai khoan chuyen den");
		double afterBalanceAmountOfAccount2 = convertMoneyToDouble(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS), Constants.VND_CURRENCY);

		log.info("TC_07_Step 43: Kiem tra so du tai khoan duoc chuyen den");
		verifyEquals(beforeBalanceAmountOfAccount2 + transferMoneyInVND1 - convertMoneyToDouble(fee + "", Constants.VND_CURRENCY), afterBalanceAmountOfAccount2);

	}

	@Test
	public void TC_08_KiemTraChiTietGiaoDichChuyenTienNgayCoPhiGiaoDichNguoiNhanTraEURVaXacThucBangMatKhau() {

		log.info("TC_08_Step 01 : Click  nut Back");
		transferInVCB.clickToDynamicBackIcon(driver, TittleData.TRANSFER_IN_VCB);

		log.info("TC_08_Step 02: Click vao More Icon");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_08_Step 03: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, Text.REPORT_TRANSFER);

		log.info("TC_08_Step 04: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, Text.ALL_TYPE_TRANSFER);

		log.info("TC_08_Step 05:Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TittleData.TRANSFER_IN_VCB);

		log.info("TC_08_Step 06: Click Chon Tai Khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_08_Step 07: Chon 1 tai Khoan");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_08_Step 08: CLick Tim Kiem");
		transReport.clickToDynamicButton(driver, Text.SEARCH);

		log.info("TC_08_Step 09: Kiem tra ngay tao giao dich hien thi");
		String transferTimeInReport = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_08_Step_10: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport), transferTime);

		log.info("TC_08_Step 11: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_08_Step 12: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER + " EUR"));

		log.info("TC_08_Step 13: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_08_Step 14: Kiem tra ngay giao dich hien thi");
		String reportTime1 = transReport.getDynamicTextInTransactionDetail(driver, Text.TIME_TRANSFER);
		verifyEquals(reportTime1, transferTimeInReport);

		log.info("TC_08_Step 16:Kiem tra so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.NUMBER_TRANSFER), transactionNumber);

		log.info("TC_08_Step 17: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.ACCOUNT_SOURCE), sourceAccount.account);

		log.info("TC_08_Step 18: Kiem tra so tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.AMOUNT_SURPLUS), distanceAccount.account);

		log.info("TC_08_Step 19: Kiem tra so tien giao dich hien thi ");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, Text.AMOUNT_TRANSFER).equals(addCommasToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " EUR"));
		log.info("TC_08_Step 20: Kiem tra so tien quy doi hien thi ");
		String changedMoney = TransferMoneyInVCB_Data.InputDataInVCB.EUR_CHANGED_MONEY;
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, Text.AMOUNT_CHANGE).contains(addCommasToLong(changedMoney) + " VND"));

		log.info("TC_08_Step 21: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.NAME_OF_BENEFICI), nameReceived);

		log.info("TC_08_Step 22: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.FEE_TRANSFER), TransferMoneyInVCB_Data.InputDataInVCB.COST[1]);

		log.info("TC_08_Step 23: Kiem tra so tien phi hien thi");
		if (fee > 0) {
			verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.FEE_AMOUNT), addCommasToLong(fee + "") + " VND");
		}
		log.info("TC_08_Step 24: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.TYPE_TRANSACTION), TittleData.TRANSFER_IN_VCB);

		log.info("TC_08_Step 25: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, Text.CONTENT_TRANSACTION).contains(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_08_Step 26: Click Back icon");
		transReport.clickToDynamicBackIcon(driver, Text.DETAIL_TRANSACTION);

		log.info("TC_08_Step 27: Chon Tai Khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_08_Step 28: Click chon tai khoan nhan");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_08_Step 29: Click Tim Kiem");
		transReport.clickToDynamicButton(driver, Text.SEARCH);

		log.info("TC_08_Step 30: Kiem tra ngay tao giao dich hien thi");
		String transferTimeInReport2 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_08_Step_31: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport2), transferTime);

		log.info("TC_08_Step 32: Kiem tra noi dung giao dich hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_08_Step 34: Click vao bao bao giao dich chi tiet");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_08_Step 35: Kiem tra ngay tao giao dich hien thi");
		String reportTime2 = transReport.getDynamicTextInTransactionDetail(driver, Text.TIME_TRANSFER);
		verifyEquals(reportTime2, transferTimeInReport);

		log.info("TC_08_Step 37: Kiem tra so giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.NUMBER_TRANSFER), transactionNumber);

		log.info("TC_08_Step 38:Kiem tra tai khoan trich no hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.ACCOUNT_SOURCE), sourceAccount.account);

		log.info("TC_08_Step 39: Kiem tra tai khoan ghi co hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.AMOUNT_SURPLUS), distanceAccount.account);

		log.info("TC_08_Step 40: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, Text.AMOUNT_TRANSFER).contains(addCommasToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " EUR"));

		log.info("TC_08_Step 41: Kiem tra so tien quy doi hien thi ");

		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, Text.AMOUNT_CHANGE).contains(transferMoneyInVND));

		log.info("TC_08_Step 43: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.FEE_TRANSFER), TransferMoneyInVCB_Data.InputDataInVCB.COST[1]);

		log.info("TC_08_Step 44:Kiem tra so tien phi hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.FEE_AMOUNT), addCommasToLong(fee + "") + " VND");

		log.info("TC_08_Step 45:Kiem tra noi dung giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, Text.CONTENT_TRANSACTION).contains(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_08_Step 46: Kiem tra loaigiao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.TYPE_TRANSACTION), TittleData.TRANSFER_IN_VCB);

		log.info("TC_08_Step 47: Click quay lai");
		transferInVCB.clickToDynamicBackIcon(driver, Text.DETAIL_TRANSACTION);

		log.info("TC_08_Step 48: Click quay lai");
		transferInVCB.clickToDynamicBackIcon(driver, Text.REPORT_TRANSFER);

		transferInVCB.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");

	}

	@Parameters({"pass"})
	@Test
	public void TC_09_ChuyenTienNgayDenTaiKhoanKhacChuSoHuuCoPhiGiaoDichNguoiChuyenTraVNDVaXacThucBangMatKhau(String pass) {

		log.info("TC_09_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, InputDataInVCB.TRANSFER_TYPE);

		log.info("TC_09_Step_02:Click tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		log.info("TC_09_Step_03: Chon tai khoan chuyen");
		sourceAccount = transferInVCB.chooseSourceAccount(driver, Constants.AMOUNT_VND, Constants.VND_CURRENCY);

		log.info("TC_09_Step_04: Lay so du tai khoan chuyen");
		String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS);
		long beforeBalanceAmountOfAccount1 = convertMoneyToLong(beforeBalanceOfAccount1, Constants.VND_CURRENCY);

		log.info("TC_09_Step_05: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, account_other_owner, TittleData.INPUT_ACCOUNT_BENEFICI);

		log.info("TC_09_Step_06: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY, TittleData.AMOUNT);

		log.info("TC_09_Step_07: Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, TittleData.TRANSFER_INFO, "3");
		

		log.info("TC_09_Step_08: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_09_Step_09: Kiem tra hinh thuc chuyen tien hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.FORM_TRANSFER), TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[0]);

		log.info("TC_09_Step_10: Kiem tra tai khoan nguon hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SOURCE_ACCOUNT), sourceAccount.account);

		log.info("TC_09_Step_11: Kiem tra tai khoan dich hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SOURCE_ACCOUNT_VND).contains(account_other_owner));

		log.info("TC_09_Step_12: Kiem tra so tien chuyen hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.AMOUNT).contains(addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY)));

		log.info("TC_09_Step_14: Kiem tra phuong thuc thanh toan bang mat khau");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.InputDataInVCB.COST[0]));

		log.info("TC_09_Step_16: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.CONTENT), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_09_Step_17: Chon Phuong thuc nhap");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.METHOD_VALIDATE);

		log.info("TC_09_Step_18: Chon SMS mat khau va lay phi");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TittleData.PASSWORD);

		log.info("TC_07_Step 23: Kiem tra so tien phi hien thi");
		String transferFee = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.FEE_AMOUNT);
		fee = convertAvailableBalanceCurrentcyOrFeeToLong(transferFee);

		log.info("TC_09_Step_19: Click Tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_09_Step_20: Nhap mat khau");
		transferInVCB.inputToDynamicPopupPasswordInput(driver, pass , TittleData.CONTINUE_BTN);

		log.info("TC_09_Step_21: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_09_Step_21: Kiem  tra giao dich thanh cong");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE));

		log.info("TC_09_Step_23: Lay thoi gian tao giao dich");
		log.info("TC_01_Step_26: Lay thoi gian tao giao dich");
		transferTime = transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE, "4").split(" ")[3];

		log.info("TC_09_Step_24: Lay ma giao dich");
		transactionNumber = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.CODE_TRANSFER);

		log.info("TC_09_Step_25: Kiem tra ten nguoi thu huong hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.NAME_BENEFICI), name_other_owner);

		log.info("TC_09_Step_26: Kiem tra tai khoan dich hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.ACCOUNT_BENEFICI), account_other_owner);

		log.info("TC_09_Step_27: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.CONTENT), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_09_Step_28: Click thuc hien giao dich moi");
		transferInVCB.clickToDynamicButton(driver, TittleData.NEW_TRANSFER);

		log.info("TC_09_Step_29: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		log.info("TC_09_Step_30: Chon tai ngoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_09_Step_31: Lay so du tai khoan chuyen");
		String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS);
		long afterBalanceAmountOfAccount1 = convertMoneyToLong(afterBalanceOfAccount1, Constants.VND_CURRENCY);

		log.info("TC_09_Step_32: Kiem tra so du tai khoan chuyen sau khi thuc hien giao dich");
		long transferMoney = convertMoneyToLong(TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY, Constants.VND_CURRENCY);
		verifyEquals(beforeBalanceAmountOfAccount1 - transferMoney - fee, afterBalanceAmountOfAccount1);

	}

	@Test
	public void TC_10_KiemTraChiTietGiaoDichChuyenTienNgayDenTaiKhoanKhacChuSoHuuCoPhiGiaoDichNguoiChuyenTraVNDVaXacThucBangMatKhau() {
		log.info("TC_10_Step_01 : Click  nut Back");
		transferInVCB.clickToDynamicBackIcon(driver, TittleData.TRANSFER_IN_VCB);

		log.info("TC_10_Step_02: Click vao More Icon");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_10_Step_03: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, Text.REPORT_TRANSFER);

		log.info("TC_10_Step_04: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, Text.ALL_TYPE_TRANSFER);

		log.info("TC_10_Step_05: Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TittleData.TRANSFER_IN_VCB);

		log.info("TC_10_Step_06: Click Chon Tai Khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_10_Step_07: Chon tai Khoan chuyen");
		sourceAccount = transferInVCB.chooseSourceAccount(driver, Constants.AMOUNT_VND, Constants.VND_CURRENCY);

		log.info("TC_10_Step_08: CLick Tim Kiem");
		transReport.clickToDynamicButton(driver, Text.SEARCH);

		log.info("TC_10_Step_09: Lay ngay tao giao dich hien thi");
		String transferTimeInReport = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_10_Step_10: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport), transferTime);

		log.info("TC_10_Step_11: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_10_Step_12: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY) + " VND"));

		log.info("TC_10_Step_13: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_10_Step_14: Kiem tra ngay giao dich hien thi");
		String reportTime1 = transReport.getDynamicTextInTransactionDetail(driver, Text.TIME_TRANSFER);
		verifyEquals(reportTime1, transferTimeInReport);

		log.info("TC_10_Step_16: Kiem tra thoi gian tao giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.NUMBER_TRANSFER), transactionNumber);

		log.info("TC_10_Step_17: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.ACCOUNT_SOURCE), sourceAccount.account);

		log.info("TC_10_Step_18: Kiem tra so tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.AMOUNT_SURPLUS), account_other_owner);

		log.info("TC_10_Step_19: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, Text.AMOUNT_TRANSFER).contains(addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY) + " VND"));

		log.info("TC_10_Step_20: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.NAME_OF_BENEFICI), name_other_owner);

		log.info("TC_10_Step_21: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.FEE_TRANSFER), TransferMoneyInVCB_Data.InputDataInVCB.COST[0]);

		log.info("TC_10_Step_22: Kiem tra so tien phi hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.FEE_AMOUNT), addCommasToLong(fee + "") + " VND");

		log.info("TC_10_Step_23: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.TYPE_TRANSACTION), TittleData.TRANSFER_IN_VCB);

		log.info("TC_10_Step_24: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, Text.CONTENT_TRANSACTION).contains(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_10_Step_25: Click Back icon");
		transReport.clickToDynamicBackIcon(driver, Text.DETAIL_TRANSACTION);

		log.info("TC_10_Step_36: Click quay lai");
		transReport.clickToDynamicBackIcon(driver, Text.REPORT_TRANSFER);
		transReport.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");

	}

	@Parameters({"pass"})
	@Test
	public void TC_11_ChuyenTienNgayDenTaiKhoanKhacChuSoHuuCoPhiGiaoDichNguoiNhanTraVNDVaXacThucBangMatKhau(String pass) {

		log.info("TC_11_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, InputDataInVCB.TRANSFER_TYPE);

		log.info("TC_11_Step_02:Click tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);

		log.info("TC_01_Step_04:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);
		log.info("TC_01_Step_05: Chon tai khoan dich");
		sourceAccount = transferInVCB.chooseSourceAccount(driver, Constants.AMOUNT_VND, Constants.VND_CURRENCY);	
		
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_11_Step_04: Lay so du tai khoan chuyen");
		String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS);
		long beforeBalanceAmountOfAccount1 = convertMoneyToLong(beforeBalanceOfAccount1, Constants.VND_CURRENCY);

		log.info("TC_11_Step_05: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver,account_other_owner , TittleData.INPUT_ACCOUNT_BENEFICI);

		log.info("TC_11_Step_06: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY, TittleData.AMOUNT);

		log.info("TC_11_Step_07: Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, TittleData.TRANSFER_INFO, "3");

		log.info("TC_11_Step_10: Click phi giao dich nguoi chuyen tra");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TittleData.FEE_TRANSFER_SOURCE_ACCOUNT_PAY);

		log.info("TC_11_Step_11: Chon nguoi nhan tra");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TittleData.BENEFICI_PAY);

		log.info("TC_11_Step_08: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_11_Step_09: Kiem tra hinh thuc chuyen tien hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.FORM_TRANSFER), TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[0]);

		log.info("TC_11_Step_10: Kiem tra tai khoan nguon hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SOURCE_ACCOUNT), sourceAccount.account);

		log.info("TC_11_Step_11: Kiem tra tai khoan dich hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SOURCE_ACCOUNT_VND).contains(account_other_owner));

		log.info("TC_11_Step_12: Kiem tra so tien chuyen hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.AMOUNT).contains(addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY)));

		log.info("TC_11_Step_14: Kiem tra phuong thuc thanh toan bang mat khau");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.InputDataInVCB.COST[1]));

		log.info("TC_11_Step_16: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.CONTENT), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_11_Step_17: Chon Phuong thuc nhap");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.METHOD_VALIDATE);

		log.info("TC_11_Step_18: Chon SMS mat khau va lay phi");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TittleData.PASSWORD);

		log.info("TC_11_Step 18 : Kiem tra so tien phi hien thi");
		String transferFee = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.FEE_AMOUNT);
		fee = convertAvailableBalanceCurrentcyOrFeeToLong(transferFee);

		log.info("TC_11_Step_19: Click Tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_11_Step_20: Nhap mat khau");

		transferInVCB.inputToDynamicPopupPasswordInput(driver, pass, TittleData.CONTINUE_BTN);

		log.info("TC_11_Step_21: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_11_Step_21: Kiem  tra giao dich thanh cong");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE));

		log.info("TC_11_Step_23: Lay thoi gian tao giao dich");
		transferTime = transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE, "4").split(" ")[3];

		log.info("TC_11_Step_24: Lay ma giao dich");
		transactionNumber = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.CODE_TRANSFER);

		log.info("TC_11_Step_25: Kiem tra ten nguoi thu huong hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.NAME_BENEFICI), name_other_owner);

		log.info("TC_11_Step_26: Kiem tra tai khoan dich hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.ACCOUNT_BENEFICI), account_other_owner);

		log.info("TC_11_Step_27: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.CONTENT), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_11_Step_28: Click thuc hien giao dich moi");
		transferInVCB.clickToDynamicButton(driver, TittleData.NEW_TRANSFER);

		log.info("TC_11_Step_29: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		log.info("TC_11_Step_30: Chon tai ngoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_11_Step_31: Lay so du tai khoan chuyen");
		String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS);
		long afterBalanceAmountOfAccount1 = convertMoneyToLong(afterBalanceOfAccount1, Constants.VND_CURRENCY);

		log.info("TC_11_Step_32: Kiem tra so du tai khoan chuyen sau khi thuc hien giao dich");
		long transferMoney = convertMoneyToLong(TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY, Constants.VND_CURRENCY);
		verifyEquals(beforeBalanceAmountOfAccount1 - transferMoney, afterBalanceAmountOfAccount1);

	}

	@Test
	public void TC_12_KiemTraChiTietGiaoDichChuyenTienNgayDenTaiKhoanKhacChuSoHuuCoPhiGiaoDichNguoiNhanTraVNDVaXacThucBangMatKhau() {
		log.info("TC_12_Step_01 : Click  nut Back");
		transferInVCB.clickToDynamicBackIcon(driver, TittleData.TRANSFER_IN_VCB);

		log.info("TC_12_Step_02: Click vao More Icon");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_12_Step_03: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, Text.REPORT_TRANSFER);

		log.info("TC_12_Step_04: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, Text.ALL_TYPE_TRANSFER);

		log.info("TC_12_Step_05: Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TittleData.TRANSFER_IN_VCB);

		log.info("TC_12_Step_06: Click Chon Tai Khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_12_Step_07: Chon tai Khoan chuyen");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_12_Step_08: CLick Tim Kiem");
		transReport.clickToDynamicButton(driver, Text.SEARCH);

		log.info("TC_12_Step_09: Kiem tra ngay tao giao dich hien thi");
		String transferTimeInReport = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_12_Step_10: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport), transferTime);

		log.info("TC_12_Step_11: Kiem tra noi dung hien thi");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_12_Step_12: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY) + " VND"));

		log.info("TC_12_Step_13: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_12_Step_14: Kiem tra ngay giao dich hien thi");
		String reportTime1 = transReport.getDynamicTextInTransactionDetail(driver, Text.TIME_TRANSFER);
		verifyEquals(reportTime1, transferTimeInReport);

		log.info("TC_12_Step_16: Kiem tra thoi gian tao giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.NUMBER_TRANSFER), transactionNumber);

		log.info("TC_12_Step_17: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.ACCOUNT_SOURCE), sourceAccount.account);

		log.info("TC_12_Step_18: Kiem tra so tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.AMOUNT_SURPLUS), account_other_owner);

		log.info("TC_12_Step_19: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, Text.AMOUNT_TRANSFER).contains(addCommasToLong(TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY) + " VND"));

		log.info("TC_12_Step_20: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.NAME_OF_BENEFICI), name_other_owner);

		log.info("TC_12_Step_21: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.FEE_TRANSFER), TransferMoneyInVCB_Data.InputDataInVCB.COST[1]);

		log.info("TC_12_Step_22: Kiem tra so tien phi hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.FEE_AMOUNT), addCommasToLong(fee + "") + " VND");

		log.info("TC_12_Step_23: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, Text.TYPE_TRANSACTION), TittleData.TRANSFER_IN_VCB);

		log.info("TC_12_Step_24: Kiem Tra noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, Text.CONTENT_TRANSACTION).contains(TransferMoneyInVCB_Data.InputDataInVCB.NOTE));

		log.info("TC_12_Step_25: Click Back icon");
		transReport.clickToDynamicBackIcon(driver, Text.DETAIL_TRANSACTION);

		log.info("TC_12_Step_36: Click quay lai");
		transferInVCB.clickToDynamicBackIcon(driver, Text.REPORT_TRANSFER);

	}

	@AfterClass(alwaysRun = true)

	public void afterClass() {
		closeApp();
		service.stop();
	}

}
