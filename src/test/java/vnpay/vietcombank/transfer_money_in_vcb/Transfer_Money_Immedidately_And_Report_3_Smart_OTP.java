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
import pageObjects.TransferMoneyInVcbPageObject;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransferMoneyInVCB_Data;
import vietcombank_test_data.TransferMoneyInVCB_Data.InputDataInVCB;
import vietcombank_test_data.TransferMoneyInVCB_Data.TittleData;

public class Transfer_Money_Immedidately_And_Report_3_Smart_OTP extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferMoneyInVcbPageObject transferInVCB;
	private SettingVCBSmartOTPPageObject smartOTP;

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
		login.Global_login1(phone, pass, opt);

		log.info("Before class_Step_10: Scroll den trang thai lenh chuyen tien");
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

	@Test
	public void TC_01_ChuyenTienNgayCoPhiGiaoDichNguoiChuyenTraVNDVaXacThucBang_Smart_OTP() {

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

		log.info("TC_01_Step_21: Chon Smart OTP");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.PAYMENT_OPTIONS[2]);

		log.info("TC_01_Step_16: Kiem tra so tien phi hien thi");
		String transferFee = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.FEE_AMOUNT);
		fee = convertAvailableBalanceCurrentcyOrFeeToLong(transferFee);

		log.info("TC_01_Step_22: Click Tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_01_Step_23: Nhap OTP");
		transferInVCB.inputToDynamicSmartOTP(driver, newOTP, "com.VCB:id/otp");

		log.info("TC_01_Step_24: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_01_Step_25: Kiem  tra giao dich thanh cong");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE));

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
	public void TC_02_ChuyenTienNgayCoPhiGiaoDichNguoiChuyenTraUSDVaXacThucBang_Smart_OTP() {
		log.info("TC_02_Step_01 : Click  nut Back");
		transferInVCB.clickToDynamicBackIcon(driver, TittleData.TRANSFER_IN_VCB);

		log.info("TC_01_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, InputDataInVCB.TRANSFER_TYPE);

		log.info("TC_01_Step_02:Click tai khoan nguon");
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

		log.info("TC_02_Step 07: Lay so du tai khoan USD");
		String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS);
		double beforeBalanceAmountOfAccount1 = convertMoneyToDouble(beforeBalanceOfAccount1, Constants.USD_CURRENCY);

		log.info("TC_02_Step 08: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, distanceAccount.account, TittleData.INPUT_ACCOUNT_BENEFICI);

		log.info("TC_02_Step 09: Nhap so tien can chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER, TittleData.AMOUNT);

		log.info("TC_02_Step 11: Chon phuong thuc xac thuc");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TittleData.FEE_TRANSFER_SOURCE_ACCOUNT_PAY);

		log.info("TC_02_Step 12: Chon phi giao dich nguoi chuyen tra");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.COST[0]);

		log.info("TC_02_Step 13:  Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, TittleData.TRANSFER_INFO, "3");

		log.info("TC_02_Step 14: Click tiep tuc");
		exchangeRate = convertAvailableBalanceCurrentcyToDouble(transferInVCB.getDynamicTextInTransactionDetail(driver, "Tỷ giá quy đổi tham khảo").split("~")[1]);
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_02_Step 15: Kiem tra hinh thuc chuyen tien hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.FORM_TRANSFER), TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[0]);

		log.info("TC_02_Step 16: Kiem tra tai khoan nguon hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SOURCE_ACCOUNT), sourceAccount.account);

		log.info("TC_02_Step 17: Kiem tra tai khoan dich hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SOURCE_ACCOUNT_VND).contains(distanceAccount.account));

		log.info("TC_02_Step 18: Kiem tra so tien chuyen hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.AMOUNT).contains(addCommasToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " USD"));
		transferMoneyInVND = transferInVCB.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvQuyDoi");
		double transferMoneyInVND1 = convertMoneyToDouble(transferMoneyInVND, Constants.VND_CURRENCY);

		log.info("TC_02_Step 19: Kiem tra Nguoi chuyen tra hien thi");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.InputDataInVCB.COST[0]));

		log.info("TC_02_Step 20: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.CONTENT), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_02_Step 21: Chon phuong thuc xac thuc");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.METHOD_VALIDATE);

		log.info("TC_02_Step 22: Chon Smart OTP");
		;
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.PAYMENT_OPTIONS[2]);

		log.info("TC_02_Step 23: Kiem tra so tien phi hien thi");
		String transferFee = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.FEE_AMOUNT);
		fee = convertAvailableBalanceCurrentcyOrFeeToLong(transferFee);

		log.info("TC_02_Step 24: Lay tien phi chuyen USD");
		double usdTransferFee = convertVNeseMoneyToEUROOrUSD(fee + "", exchangeRate + "");

		log.info("TC_02_Step 25: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_02_Step 26: Dien OTP");
		transferInVCB.inputToDynamicSmartOTP(driver, newOTP, "com.VCB:id/otp");

		log.info("TC_02_Step 27: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE));

		log.info("TC_02_Step 29: Kiem tra so tien chuyen hien thi");
		verifyEquals(transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE, "3"), addCommasToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " USD");

		log.info("TC_02_Step 30: Kiem tra ten nguoi thu huong hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.NAME_BENEFICI).contains(nameReceived));

		log.info("TC_02_Step 31: Kiem tra tai khoan dich hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.ACCOUNT_BENEFICI), sourceAccount.account);

		log.info("TC_02_Step 33: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.CONTENT), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_02_Step 34: Click thuc hien giao dich moi");
		transferInVCB.clickToDynamicButton(driver, TittleData.NEW_TRANSFER);

		log.info("TC_02_Step 35: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		log.info("TC_02_Step 36:Click USD account");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);
		transferInVCB.sleep(driver, 1000);

		log.info("TC_02_Step 37: Lay so du kha dung tai khoan USD");
		String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS);
		double afterBalanceAmountOfAccount1 = convertMoneyToDouble(afterBalanceOfAccount1, Constants.USD_CURRENCY);

		log.info("TC_02_Step 38: Convert so tien chuyen");
		double transferMoney = convertMoneyToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER, Constants.USD_CURRENCY);

		log.info("TC_02_Step 39: Kiem tra so du tai khoan USD sau khi chuyen tien");
		verifyEquals(convertMoneyToDouble((beforeBalanceAmountOfAccount1 - transferMoney - usdTransferFee) + "", Constants.USD_CURRENCY), afterBalanceAmountOfAccount1);

		log.info("TC_02_Step 40: Kiem tra tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		log.info("TC_02_Step 41: Click chon tai khoan chuyen den");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, distanceAccount.account);

		log.info("TC_02_Step 41: Lay so du kha dung tai khoan chuyen den");
		String afterBalanceOfAccount2 = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS);
		double afterBalanceAmountOfAccount2 = convertMoneyToDouble(afterBalanceOfAccount2, Constants.VND_CURRENCY);

		log.info("TC_02_Step 43: Kiem tra so du tai khoan duoc chuyen den");
		verifyEquals(beforeBalanceAmountOfAccount2 + transferMoneyInVND1, afterBalanceAmountOfAccount2);

	}

	@Test
	public void TC_03_ChuyenTienNgayCoPhiGiaoDichNguoiNhanTraEURVaXacThucBang_Smart_OTP() {

		log.info("TC_02_Step_01 : Click  nut Back");
		transferInVCB.clickToDynamicBackIcon(driver, TittleData.TRANSFER_IN_VCB);

		log.info("TC_01_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, InputDataInVCB.TRANSFER_TYPE);

		log.info("TC_01_Step_02:Click tai khoan nguon");
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

		log.info("TC_03_Step 07: Lay so du tai khoan USD");
		String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS);
		double beforeBalanceAmountOfAccount1 = convertMoneyToDouble(beforeBalanceOfAccount1, Constants.EUR_CURRENCY);

		log.info("TC_03_Step 08: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, distanceAccount.account, TittleData.INPUT_ACCOUNT_BENEFICI);

		log.info("TC_03_Step 09: Nhap so tien can chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER, TittleData.AMOUNT);

		log.info("TC_03_Step 11: Chon phuong thuc xac thuc");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TittleData.FEE_TRANSFER_SOURCE_ACCOUNT_PAY);

		log.info("TC_03_Step 12: Chon phi giao dich nguoi nhan tra");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TittleData.BENEFICI_PAY);

		log.info("TC_03_Step 13:  Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, TittleData.TRANSFER_INFO, "3");

		log.info("TC_03_Step 14: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_03_Step 15: Kiem tra hinh thuc chuyen tien hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.FORM_TRANSFER), TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[0]);

		log.info("TC_03_Step 16: Kiem tra tai khoan nguon hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SOURCE_ACCOUNT), sourceAccount.account);

		log.info("TC_03_Step 17: Kiem tra tai khoan dich hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SOURCE_ACCOUNT_VND).contains(distanceAccount.account));

		log.info("TC_03_Step 18: Kiem tra so tien chuyen hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.AMOUNT).contains(addCommasToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " EUR"));
		transferMoneyInVND = transferInVCB.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvQuyDoi");
		double transferMoneyInVND1 = convertMoneyToDouble(transferMoneyInVND, Constants.VND_CURRENCY);

		log.info("TC_03_Step 19: Kiem tra Nguoi nhan tra hien thi");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.InputDataInVCB.COST[1]));

		log.info("TC_03_Step 20: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.CONTENT), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_03_Step 21: Chon phuong thuc xac thuc");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.METHOD_VALIDATE);

		log.info("TC_03_Step 22: Chon Smart OTP");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.PAYMENT_OPTIONS[2]);

		log.info("TC_03_Step 23: Kiem tra so tien phi hien thi");
		String transferFee = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.FEE_AMOUNT);
		fee = convertAvailableBalanceCurrentcyOrFeeToLong(transferFee);

		log.info("TC_03_Step 25: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_03_Step 26: Dien OTP");
		transferInVCB.inputToDynamicSmartOTP(driver, newOTP, "com.VCB:id/otp");

		log.info("TC_03_Step 27: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE));

		log.info("TC_03_Step 29: Kiem tra so tien chuyen hien thi");
		verifyEquals(transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE, "3"), addCommasToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " EUR");

		log.info("TC_03_Step 30: Kiem tra ten nguoi thu huong hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.NAME_BENEFICI).contains(nameReceived));

		log.info("TC_03_Step 31: Kiem tra tai khoan dich hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.ACCOUNT_BENEFICI), distanceAccount.account);

		log.info("TC_03_Step 33: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.CONTENT), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_03_Step 34: Click thuc hien giao dich moi");
		transferInVCB.clickToDynamicButton(driver, TittleData.NEW_TRANSFER);

		log.info("TC_03_Step 35: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		log.info("TC_03_Step 36:Click EUR account");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);
		transferInVCB.sleep(driver, 1000);

		log.info("TC_03_Step 37: Lay so du kha dung tai khoan EUR");
		double afterBalanceAmountOfAccount1 = convertMoneyToDouble(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS), Constants.EUR_CURRENCY);

		log.info("TC_03_Step 38: Convert so tien chuyen");
		double transferMoney = convertMoneyToDouble(TransferMoneyInVCB_Data.InputDataInVCB.AMOUNT_OF_EUR_OR_USD_TRANSFER, Constants.EUR_CURRENCY);

		log.info("TC_03_Step 39: Kiem tra so du tai khoan USD sau khi chuyen tien");
		verifyEquals(beforeBalanceAmountOfAccount1 - transferMoney, afterBalanceAmountOfAccount1);

		log.info("TC_03_Step 40: Kiem tra tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		log.info("TC_03_Step 41: Click chon tai khoan chuyen den");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, distanceAccount.account);
		transferInVCB.sleep(driver, 1000);

		log.info("TC_03_Step 42: Lay so du kha dung tai khoan chuyen den");
		double afterBalanceAmountOfAccount2 = convertMoneyToDouble(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS), Constants.VND_CURRENCY);

		log.info("TC_03_Step 43: Kiem tra so du tai khoan duoc chuyen den");
		verifyEquals(beforeBalanceAmountOfAccount2 + transferMoneyInVND1 - convertMoneyToDouble(fee + "", Constants.VND_CURRENCY), afterBalanceAmountOfAccount2);

	}

	@AfterClass(alwaysRun = true)

	public void afterClass() {
		closeApp();
		service.stop();
	}

}
