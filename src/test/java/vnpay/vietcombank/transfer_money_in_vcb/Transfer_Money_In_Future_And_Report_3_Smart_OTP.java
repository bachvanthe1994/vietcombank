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
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransferMoneyInVCB_Data;
import vietcombank_test_data.TransferMoneyInVCB_Data.TittleData;

public class Transfer_Money_In_Future_And_Report_3_Smart_OTP extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferMoneyInVcbPageObject transferInVCB;
	private SettingVCBSmartOTPPageObject smartOTP;

	private String transferTime;
	private String transactionNumber;
	String[] exchangeRateUSD;
	String today = getCurrentDay() + "/" + getCurrenMonth() + "/" + getCurrentYear();
	String tommorrowDate = getForwardDate(1);
	String name_receiver = "";
	SourceAccountModel sourceAccount = new SourceAccountModel();
	SourceAccountModel distanceAccount = new SourceAccountModel();
	String account_other_owner, name_other_owner, otpSmart;

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
		name_receiver = getDataInCell(3);
		log.info("Before class_Step_10: Scroll den trang thai lenh chuyen tien");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.scrollDownToText(driver, TittleData.STATUS_TRANSACTION);
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		smartOTP = PageFactoryManager.getSettingVCBSmartOTPPageObject(driver);
		account_other_owner = getDataInCell(0);
		name_other_owner = getDataInCell(1);
		otpSmart = getDataInCell(6);
	}

	@Test
	public void TC_01_ChuyenTienTuongLaiCoPhiGiaoDichNguoiChuyenTraVNDVaXacThucSmart_OTP() {
		
		log.info("TC_01_0_Setup smart OTP");
		smartOTP.setupSmartOTP(otpSmart, otpSmart);

		log.info("TC_01_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, TransferMoneyInVCB_Data.InputDataInVCB.TRANSFER_TYPE);

		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);

		log.info("TC_01_Step_02: Chon chuyen tien ngay gia tri hien tai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[0]);

		log.info("TC_01_Step_03: Chon chuyen tien ngay gia tri hien tai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[2]);

		log.info("TC_01_Step_04:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);
		List<String> listAccount = transferInVCB.getListSourceAccount(driver, Constants.VND_CURRENCY);
		log.info("TC_01_Step_05: Chon tai khoan dich");
		sourceAccount = transferInVCB.chooseSourceAccount(driver, Constants.AMOUNT_VND, Constants.VND_CURRENCY);
		
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);
		distanceAccount.account = transferInVCB.getDistanceAccount(driver, sourceAccount.account, listAccount);
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, distanceAccount.account);

		
		log.info("TC_01_Step_06: Lay so du tai khoan dich");
		String beforeBalanceOfAccount2 = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS);
		long beforeBalanceAmountOfAccount2 = convertMoneyToLong(beforeBalanceOfAccount2, Constants.VND_CURRENCY);

		log.info("TC_01_Step_07:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		log.info("TC_01_Step_08: Chon tai khoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_01_Step_09: Lay so du tai khoan chuyen");
		String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS);
		long beforeBalanceAmountOfAccount1 = convertMoneyToLong(beforeBalanceOfAccount1, Constants.VND_CURRENCY);

		log.info("TC_01_Step_10: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, distanceAccount.account, TittleData.INPUT_ACCOUNT_BENEFICI);

		log.info("TC_01_Step_11: Kiem tra Ngay hieu luc mac dinh");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, tommorrowDate));

		log.info("TC_01_Step_12: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInFutureForPassword.TRANSFER_AMOUNT, TittleData.AMOUNT);

		log.info("TC_01_Step_13: Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, TittleData.TRANSFER_INFO, "3");

		log.info("TC_01_Step_14: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_01_Step_15: Kiem tra hinh thuc chuyen tien hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.FORM_TRANSFER), TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[2]);

		log.info("TC_01_Step_16: Kiem tra tai khoan nguon hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SOURCE_ACCOUNT), sourceAccount.account);

		log.info("TC_01_Step_17: Kiem tra tai khoan dich hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SOURCE_ACCOUNT_VND).contains(distanceAccount.account));

		log.info("TC_01_Step_18: Kiem tra so tien chuyen hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.AMOUNT), addCommasToLong(TransferMoneyInVCB_Data.InputDataInFutureForPassword.TRANSFER_AMOUNT) + " VND");

		log.info("TC_01_Step_19: Kiem tra Ngay hieu luc hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.EFFECTIVE_DATE), transferInVCB.getDayInWeek(tommorrowDate) + " " + tommorrowDate);

		log.info("TC_01_Step_20: Kiem tra phuong thuc tra phi");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.InputDataInVCB.COST[0]));

		log.info("TC_01_Step_21: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.CONTENT), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_01_Step_22: Chon Phuong thuc nhap");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.METHOD_VALIDATE);

		log.info("TC_01_Step_23: Chon OTP");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.PAYMENT_OPTIONS[2]);

		log.info("TC_01_Step_25: Click Tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_01_Step_26: Nhap Smart OTP");


		transferInVCB.inputToDynamicSmartOTP(driver, otpSmart, "com.VCB:id/otp");

		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);
		
		log.info("TC_01_Step_27: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_01_Step_28: Kiem  tra giao dich thanh cong");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.SUCESSFULL_CREATED_ORDER));

		log.info("TC_01_Step_29: Kiem tra so tien chuyen");
		verifyEquals(transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.SUCESSFULL_CREATED_ORDER, "3"), addCommasToLong(TransferMoneyInVCB_Data.InputDataInFutureForPassword.TRANSFER_AMOUNT) + " VND");

		log.info("TC_01_Step_30: Lay thoi gian tao giao dich");
		transferTime = transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.SUCESSFULL_CREATED_ORDER, "4");
		verifyEquals(transferTime, TransferMoneyInVCB_Data.Output.ORDER_TIME + transferInVCB.getDayInWeek(tommorrowDate) + " " + tommorrowDate);

		log.info("TC_01_Step_31: Lay ma giao dich");
		transactionNumber = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.CODE_TRANSFER);
		log.info("TC_01_Step_32: Lay ma giao dich" + transactionNumber);

		log.info("TC_01_Step_33: Kiem tra ten nguoi thu huong hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.NAME_BENEFICI), name_receiver);

		log.info("TC_01_Step_34: Kiem tra tai khoan dich hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.ACCOUNT_BENEFICI), distanceAccount.account);

		log.info("TC_01_Step_35: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.CONTENT), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);
		
		log.info("TC_01_Step_36: Click thuc hien giao dich moi");
		transferInVCB.clickToDynamicButton(driver, TittleData.NEW_TRANSFER);

		log.info("TC_01_Step_37: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		log.info("TC_01_Step_38: Chon tai ngoan chuyen");
		sourceAccount = transferInVCB.chooseSourceAccount(driver, Constants.AMOUNT_VND, Constants.VND_CURRENCY);
		transferInVCB.sleep(driver, 500);

		log.info("TC_01_Step_39: Lay so du tai khoan chuyen");
		String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS);
		long afterBalanceAmountOfAccount1 = convertMoneyToLong(afterBalanceOfAccount1, Constants.VND_CURRENCY);

		log.info("TC_01_Step_40: Kiem tra so du tai khoan chuyen sau khi thuc hien giao dich");
		verifyEquals(beforeBalanceAmountOfAccount1, afterBalanceAmountOfAccount1);

		log.info("TC_01_Step_41: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		log.info("TC_01_Step_42: Chon tai khoan nguoi nhan");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, distanceAccount.account);
		transferInVCB.sleep(driver, 500);

		log.info("TC_01_Step_43: Lay so du kha dung tai khoan nhan");
		String afterBalanceOfAccount2 = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS);

		log.info("TC_01_Step_44: Kiem tra so du tai khoan nhan sau khi nhan tien");
		long afterBalanceAmountOfAccount2 = convertMoneyToLong(afterBalanceOfAccount2, Constants.VND_CURRENCY);
		verifyEquals(beforeBalanceAmountOfAccount2, afterBalanceAmountOfAccount2);
	}


	@Test
	public void TC_02_ChuyenTienTuongLaiCoPhiGiaoDichNguoiChuyenTraUSDVaXacThucBang_Smart_OTP() {

		log.info("TC_02_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, TransferMoneyInVCB_Data.InputDataInVCB.TRANSFER_TYPE);

		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);

		log.info("TC_02_Step_02: Chon chuyen tien ngay gia tri hien tai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[0]);

		log.info("TC_02_Step_03: Chon chuyen tien ngay gia tri hien tai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[2]);

		log.info("TC_02_Step_04:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);
		List<String> listAccount = transferInVCB.getListSourceAccount(driver, Constants.VND_CURRENCY);
		log.info("TC_02_Step_05: Chon tai khoan dich");
		sourceAccount = transferInVCB.chooseSourceAccount(driver, Constants.MONEY_CHECK_USD, Constants.USD_CURRENCY);
		
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);
		distanceAccount.account = transferInVCB.getDistanceAccount(driver, sourceAccount.account, listAccount);
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, distanceAccount.account);


		log.info("TC_02_Step_06: Lay so du tai khoan dich");
		String beforeBalanceOfAccount2 = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS);
		long beforeBalanceAmountOfAccount2 = convertMoneyToLong(beforeBalanceOfAccount2, Constants.VND_CURRENCY);

		log.info("TC_02_Step_07:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		log.info("TC_02_Step_08: Chon tai khoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, distanceAccount.account);
		transferInVCB.sleep(driver, 500);

		log.info("TC_02_Step_09: Lay so du tai khoan chuyen");
		String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS);
		double beforeBalanceAmountOfAccount1 = convertMoneyToDouble(beforeBalanceOfAccount1, Constants.USD_CURRENCY);

		log.info("TC_02_Step_10: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, distanceAccount.account, TittleData.INPUT_ACCOUNT_BENEFICI);

		log.info("TC_02_Step_11: Kiem tra Ngay hieu luc mac dinh");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, tommorrowDate));

		log.info("TC_02_Step_12: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInFutureForPassword.AMOUNT_OF_EUR_OR_USD_TRANSFER, TittleData.AMOUNT);

		log.info("TC_02_Step_13: Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, TittleData.TRANSFER_INFO, "3");

		log.info("TC_02_Step_14: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_02_Step_15: Kiem tra hinh thuc chuyen tien hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.FORM_TRANSFER), TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[2]);

		log.info("TC_02_Step_16: Kiem tra tai khoan nguon hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SOURCE_ACCOUNT), sourceAccount.account);

		log.info("TC_02_Step_17: Kiem tra tai khoan dich hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SOURCE_ACCOUNT_VND).contains(distanceAccount.account));

		log.info("TC_02_Step_18: Kiem tra so tien chuyen hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.AMOUNT), String.format("%.02f", convertMoneyToDouble(TransferMoneyInVCB_Data.InputDataInFutureForPassword.AMOUNT_OF_EUR_OR_USD_TRANSFER, Constants.USD_CURRENCY)) + " USD");

		log.info("TC_02_Step_19: Kiem tra Ngay hieu luc hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.EFFECTIVE_DATE), transferInVCB.getDayInWeek(tommorrowDate) + " " + tommorrowDate);

		log.info("TC_02_Step_20: Kiem tra phuong thuc tra phi");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.InputDataInVCB.COST[0]));

		log.info("TC_02_Step_21: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.CONTENT), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_02_Step_22: Chon Phuong thuc nhap");
		transferInVCB.scrollDownToButton(driver, TittleData.CONTINUE_BTN);
		transferInVCB.clickToDynamicDropDown(driver, TittleData.METHOD_VALIDATE);

		log.info("TC_02_Step_23: Chon Mật khẩu đăng nhập");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.PAYMENT_OPTIONS[2]);

		log.info("TC_02_Step_25: Click Tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_02_Step_26: Nhap Smart OTP");
		
		transferInVCB.inputToDynamicSmartOTP(driver, otpSmart, "com.VCB:id/otp");

		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);
		log.info("TC_02_Step_27: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_02_Step_28: Kiem  tra giao dich thanh cong");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.SUCESSFULL_CREATED_ORDER));

		log.info("TC_02_Step_29: Kiem tra so tien chuyen");
		verifyEquals(transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.SUCESSFULL_CREATED_ORDER, "3"), addCommasToDouble(TransferMoneyInVCB_Data.InputDataInFutureForPassword.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " USD");

		log.info("TC_02_Step_30: Lay thoi gian tao giao dich");
		transferTime = transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.SUCESSFULL_CREATED_ORDER, "4");
		verifyEquals(transferTime, TransferMoneyInVCB_Data.Output.ORDER_TIME + transferInVCB.getDayInWeek(tommorrowDate) + " " + tommorrowDate);

		log.info("TC_02_Step_31: Lay ma giao dich");
		transactionNumber = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.CODE_TRANSFER);
		log.info("TC_02_Step_32: Lay ma giao dich" + transactionNumber);

		log.info("TC_02_Step_33: Kiem tra ten nguoi thu huong hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.NAME_BENEFICI), name_receiver);

		log.info("TC_02_Step_34: Kiem tra tai khoan dich hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.ACCOUNT_BENEFICI), sourceAccount.account);

		log.info("TC_02_Step_35: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.CONTENT), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_02_Step_36: Click thuc hien giao dich moi");
		transferInVCB.clickToDynamicButton(driver, TittleData.NEW_TRANSFER);

		log.info("TC_02_Step_37: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		log.info("TC_02_Step_38: Chon tai ngoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.USD_ACCOUNT);
		transferInVCB.sleep(driver, 500);

		log.info("TC_02_Step_39: Lay so du tai khoan chuyen");
		String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS);
		double afterBalanceAmountOfAccount1 = convertMoneyToDouble(afterBalanceOfAccount1, Constants.USD_CURRENCY);

		log.info("TC_02_Step_40: Kiem tra so du tai khoan chuyen sau khi thuc hien giao dich");
		verifyEquals(beforeBalanceAmountOfAccount1, afterBalanceAmountOfAccount1);

		log.info("TC_02_Step_41: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		log.info("TC_02_Step_42: Chon tai khoan nguoi nhan");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);
		transferInVCB.sleep(driver, 500);

		log.info("TC_02_Step_43: Lay so du kha dung tai khoan nhan");
		String afterBalanceOfAccount2 = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS);

		log.info("TC_02_Step_44: Kiem tra so du tai khoan nhan sau khi nhan tien");
		long afterBalanceAmountOfAccount2 = convertMoneyToLong(afterBalanceOfAccount2, Constants.VND_CURRENCY);
		verifyEquals(beforeBalanceAmountOfAccount2, afterBalanceAmountOfAccount2);

	}


	@Test
	public void TC_03_ChuyenTienTuongLaiCoPhiGiaoDichNguoiNhanTraEURVaXacThucBangSmartOTP() {

		log.info("TC_03_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, TransferMoneyInVCB_Data.InputDataInVCB.TRANSFER_TYPE);

		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);

		log.info("TC_03_Step_02: Chon chuyen tien ngay gia tri hien tai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[0]);

		log.info("TC_03_Step_03: Chon chuyen tien ngay gia tri hien tai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[2]);
		
		log.info("TC_03_Step_04:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);
		List<String> listAccount = transferInVCB.getListSourceAccount(driver, Constants.VND_CURRENCY);
		log.info("TC_03_Step_05: Chon tai khoan dich");
		sourceAccount = transferInVCB.chooseSourceAccount(driver, Constants.MONEY_CHECK_EUR, Constants.EUR_CURRENCY);
		
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);
		distanceAccount.account = transferInVCB.getDistanceAccount(driver, sourceAccount.account, listAccount);
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, distanceAccount.account);

		log.info("TC_03_Step_06: Lay so du tai khoan dich");
		String beforeBalanceOfAccount2 = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS);
		long beforeBalanceAmountOfAccount2 = convertMoneyToLong(beforeBalanceOfAccount2, Constants.VND_CURRENCY);

		log.info("TC_03_Step_07:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		log.info("TC_03_Step_08: Chon tai khoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, distanceAccount.account);
		transferInVCB.sleep(driver, 500);

		log.info("TC_03_Step_09: Lay so du tai khoan chuyen");
		String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS);
		double beforeBalanceAmountOfAccount1 = convertMoneyToDouble(beforeBalanceOfAccount1, "EUR");

		log.info("TC_03_Step_10: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, distanceAccount.account, TittleData.INPUT_ACCOUNT_BENEFICI);

		log.info("TC_03_Step_11: Kiem tra Ngay hieu luc mac dinh");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, tommorrowDate));

		log.info("TC_03_Step_12: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInFutureForPassword.AMOUNT_OF_EUR_OR_USD_TRANSFER, TittleData.AMOUNT);

		log.info("TC_03_Step_13: Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, TittleData.TRANSFER_INFO, "3");

		log.info("TC_03_Step_14: Click nguoi chuyen tra");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TittleData.FEE_TRANSFER_SOURCE_ACCOUNT_PAY);

		log.info("TC_03_Step_15: Click nguoi chuyen tra");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TittleData.BENEFICI_PAY);

		log.info("TC_03_Step_16: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_03_Step_17: Kiem tra hinh thuc chuyen tien hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.FORM_TRANSFER), TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[2]);

		log.info("TC_03_Step_18: Kiem tra tai khoan nguon hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SOURCE_ACCOUNT), Account_Data.Valid_Account.EUR_ACCOUNT);

		log.info("TC_03_Step_19: Kiem tra tai khoan dich hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SOURCE_ACCOUNT_VND).contains(sourceAccount.account));

		log.info("TC_03_Step_20: Kiem tra so tien chuyen hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.AMOUNT), addCommasToDouble(TransferMoneyInVCB_Data.InputDataInFutureForPassword.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " EUR");

		log.info("TC_03_Step_21: Kiem tra Ngay hieu luc hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.EFFECTIVE_DATE), transferInVCB.getDayInWeek(tommorrowDate) + " " + tommorrowDate);

		log.info("TC_03_Step_22: Kiem tra phuong thuc tra phi");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.InputDataInVCB.COST[1]));

		log.info("TC_03_Step_23: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.CONTENT), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_03_Step_24: Chon Phuong thuc nhap");
		transferInVCB.scrollDownToButton(driver, TittleData.CONTINUE_BTN);
		transferInVCB.clickToDynamicDropDown(driver, TittleData.METHOD_VALIDATE);

		log.info("TC_03_Step_25: Chon Mật khẩu đăng nhập");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.PAYMENT_OPTIONS[2]);

		log.info("TC_03_Step_27: Click Tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_03_Step_28: Nhap OTP");
		
		transferInVCB.inputToDynamicSmartOTP(driver, otpSmart,TittleData.CONTINUE_BTN);

		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);
		log.info("TC_03_Step_29: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_03_Step_30: Kiem  tra giao dich thanh cong");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.SUCESSFULL_CREATED_ORDER));

		log.info("TC_03_Step_31: Kiem tra so tien chuyen");
		verifyEquals(transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.SUCESSFULL_CREATED_ORDER, "3"), addCommasToDouble(TransferMoneyInVCB_Data.InputDataInFutureForPassword.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " EUR");

		log.info("TC_03_Step_32: Lay thoi gian tao giao dich");
		transferTime = transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.SUCESSFULL_CREATED_ORDER, "4");
		verifyEquals(transferTime, TransferMoneyInVCB_Data.Output.ORDER_TIME + transferInVCB.getDayInWeek(tommorrowDate) + " " + tommorrowDate);

		log.info("TC_03_Step_33: Lay ma giao dich");
		transactionNumber = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.CODE_TRANSFER);
		log.info("TC_03_Step_34: Lay ma giao dich" + transactionNumber);

		log.info("TC_03_Step_35: Kiem tra ten nguoi thu huong hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.NAME_BENEFICI), name_receiver);

		log.info("TC_03_Step_36: Kiem tra tai khoan dich hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.ACCOUNT_BENEFICI), distanceAccount.account);

		log.info("TC_03_Step_37: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.CONTENT), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_03_Step_38: Click thuc hien giao dich moi");
		transferInVCB.clickToDynamicButton(driver, TittleData.NEW_TRANSFER);

		log.info("TC_03_Step_39: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		log.info("TC_03_Step_40: Chon tai ngoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.EUR_ACCOUNT);
		transferInVCB.sleep(driver, 500);

		log.info("TC_03_Step_41: Lay so du tai khoan chuyen");
		String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS);
		double afterBalanceAmountOfAccount1 = convertMoneyToDouble(afterBalanceOfAccount1, "EUR");

		log.info("TC_03_Step_42: Kiem tra so du tai khoan chuyen sau khi thuc hien giao dich");
		verifyEquals(beforeBalanceAmountOfAccount1, afterBalanceAmountOfAccount1);

		log.info("TC_03_Step_43: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		log.info("TC_03_Step_44: Chon tai khoan nguoi nhan");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);
		transferInVCB.sleep(driver, 500);

		log.info("TC_03_Step_45: Lay so du kha dung tai khoan nhan");
		String afterBalanceOfAccount2 = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS);

		log.info("TC_03_Step_46: Kiem tra so du tai khoan nhan sau khi nhan tien");
		long afterBalanceAmountOfAccount2 = convertMoneyToLong(afterBalanceOfAccount2, Constants.VND_CURRENCY);
		verifyEquals(beforeBalanceAmountOfAccount2, afterBalanceAmountOfAccount2);

	}

	@AfterClass(alwaysRun = true)

	public void afterClass() {
		closeApp();
		service.stop();
	}

}
