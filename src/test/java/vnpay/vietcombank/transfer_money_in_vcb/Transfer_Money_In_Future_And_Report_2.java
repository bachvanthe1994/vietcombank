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
import pageObjects.TransferMoneyInVcbPageObject;
import pageObjects.TransferMoneyStatusPageObject;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransferMoneyInVCB_Data;
import vietcombank_test_data.TransferMoneyInVCB_Data.TittleData;
import vietcombank_test_data.TransferMoneyStatus_Data;

public class Transfer_Money_In_Future_And_Report_2 extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferMoneyInVcbPageObject transferInVCB;
	private TransferMoneyStatusPageObject transStatus;

	private String transferTime;
	private String transactionNumber;
	private long fee;
	String[] exchangeRateUSD;
	String today = getCurrentDay() + "/" + getCurrenMonth() + "/" + getCurrentYear();
	String tommorrowDate = getForwardDate(1);
	String name_receiver = "";
	SourceAccountModel sourceAccount = new SourceAccountModel();
	SourceAccountModel distanceAccount = new SourceAccountModel();
	String account_other_owner, name_other_owner;

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
		name_receiver = getDataInCell(3);
		log.info("Before class_Step_10: Scroll den trang thai lenh chuyen tien");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.scrollDownToText(driver, TittleData.STATUS_TRANSACTION);
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		account_other_owner = getDataInCell(0);
		name_other_owner = getDataInCell(1);
	}

	@Test
	public void TC_01_ChuyenTienTuongLaiCoPhiGiaoDichNguoiChuyenTraVNDVaXacThucBangOTP() {

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
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.PAYMENT_OPTIONS[1]);

		log.info("TC_04_Step_26: Kiem tra so tien phi hien thi");
		String transferFee = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.FEE_AMOUNT);
		fee = convertAvailableBalanceCurrentcyOrFeeToLong(transferFee);

		log.info("TC_01_Step_25: Click Tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_01_Step_26: Nhap OTP");
		transferInVCB.inputToDynamicPopupPasswordInput(driver, LogIn_Data.Login_Account.NEW_PASSWORD, TittleData.CONTINUE_BTN);

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
	public void TC_02_KiemTraTrạngThaiLenhChuyenTienCuaGiaoDichTuongLaiCoPhiGiaoDichNguoiChuyenTraVNDVaXacThucOTP() {
		log.info("TC_02_Step_01 : Click  nut Back");
		transferInVCB.clickToDynamicBackIcon(driver, TransferMoneyInVCB_Data.InputDataInVCB.TRANSFER_TYPE_VIETCOM_BANL);

		log.info("TC_02_Step_02: Click vao More Icon");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicIcon(driver, TittleData.STATUS_TRANSACTION);

		log.info("TC_02_Step_03: Click Tat Ca Cac Loai Giao Dich");
		transStatus = PageFactoryManager.getTransferMoneyStatusPageObject(driver);
		transStatus.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[1]);

		log.info("TC_02_Step_04: Chon Chuyen Tien Trong VCB");
		transStatus.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[2]);

		log.info("TC_02_Step_05: Kiem tra from date hien thi");
		verifyEquals(transStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate"), getBackwardDate(29));

		log.info("TC_02_Step_06: Kiem tra todate hien thi");
		verifyEquals(transStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate"), today);

		log.info("TC_02_Step_07: Kiem tra Note hien thi");
		verifyTrue(transStatus.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyStatus_Data.Output.NOTE));

		log.info("TC_02_Step_08: CLick Tim Kiem");
		transStatus.clickToDynamicButton(driver, TittleData.SEARCH);

		log.info("TC_02_Step 09: Kiem tra ngay tao giao dich hien thi");
		String orderTime = transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvDate");
		verifyTrue(orderTime.contains(getCurrentDay() + "/" + getCurrenMonth() + "/" + getCurrentYear()));

		log.info("TC_02_Step 10: Kiem tra thoi gian tao dao dich");

		log.info("TC_02_Step 11: Kiem tra nguoi nhan");
		verifyEquals(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvContent"), name_receiver);

		log.info("TC_02_Step 12: Kiem tra trang thai giao dich");
		verifyEquals(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvStatus"), TransferMoneyStatus_Data.Output.WAITING_STATUS);

		log.info("TC_02_Step 13: Kiem tra so tien giao dich");
		verifyEquals(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "1", "com.VCB:id/tvMoney"), addCommasToLong(TransferMoneyInVCB_Data.InputDataInFutureForOTP.TRANSFER_AMOUNT) + " VND");

		log.info("TC_02_Step 14: click vao giao dich");
		transStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_02_Step 15: Kiem tra ngay giao dich hien thi");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, TittleData.CREAT_DATE), orderTime);

		log.info("TC_02_Step 16: Kiem tra thoi gian tao giao dich hien thi");

		log.info("TC_02_Step 17: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, TittleData.NAME_RECEIPTER), name_receiver);

		log.info("TC_02_Step 18: Kiem tra so tien thi");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, TittleData.AMOUNT), addCommasToLong(TransferMoneyInVCB_Data.InputDataInFutureForOTP.TRANSFER_AMOUNT) + " VND");

		log.info("TC_02_Step 19: Kiem tra ngay hieu luc");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, TittleData.EFFECTIVE_DATE), tommorrowDate);

		log.info("TC_02_Step 20: Kiem tra ghi chú");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, TittleData.NOTE), "");

		log.info("TC_02_Step 21: Kiem tra trang thai hieu luc");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, TittleData.STATUS), TransferMoneyStatus_Data.Output.WAITING_STATUS);

		log.info("TC_02_Step_22: Click quay lai");
		transStatus.clickToDynamicBackIcon(driver, TittleData.DETAIL_TRANSFER);
	}

	@Test
	public void TC_03_HuyLenhGiaoDichTuongLaiXacThucOTP() {

		log.info("TC_03_Step_01: Click Huy Lenh");
		transStatus.clickToDynamicIconInOrderStatus(driver, "0", "com.VCB:id/tvHuyLenh");

		log.info("TC_03_Step_02: Click Dong Y");
		transStatus.clickToDynamicButton(driver, TittleData.ACCEPT);

		log.info("TC_03_Step_04: Kiem TraThanh Cong hiển thị");
		verifyEquals(transStatus.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), TittleData.SUCCESS);

		log.info("TC_03_Step_05: Click quay lai");
		transStatus.clickToDynamicButton(driver, TittleData.CLOSE);

		log.info("TC_03_Step_06: Kiem tra trang thai Da Huy hien thi");
		verifyEquals(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvStatus"), TransferMoneyStatus_Data.Output.CANCEL_STATUS);

		log.info("TC_03_Step_07: click vao giao dich");
		transStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_03_Step_08: Kiem tra trang thai  Da Huy hien thi");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, TittleData.STATUS), TransferMoneyStatus_Data.Output.CANCEL_STATUS);

		log.info("TC_03_Step_09: Click quay lai");
		transStatus.clickToDynamicBackIcon(driver, TittleData.DETAIL_TRANSFER);

		log.info("TC_03_Step_10: Click quay lai");
		transStatus.clickToDynamicBackIcon(driver, TittleData.STATUS_TRANSACTION);

	}

	@Test
	public void TC_04_ChuyenTienTuongLaiCoPhiGiaoDichNguoiNhanTraVNDVaXacThucBangOTP() {

		log.info("TC_04_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, TransferMoneyInVCB_Data.InputDataInVCB.TRANSFER_TYPE);

		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);

		log.info("TC_04_Step_02: Chon chuyen tien ngay gia tri hien tai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[0]);

		log.info("TC_04_Step_03: Chon chuyen tien ngay gia tri hien tai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[2]);

		log.info("TC_04_Step_04:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);
		List<String> listAccount = transferInVCB.getListSourceAccount(driver, Constants.VND_CURRENCY);
		log.info("TC_04_Step_05: Chon tai khoan dich");
		sourceAccount = transferInVCB.chooseSourceAccount(driver, Constants.AMOUNT_VND, Constants.VND_CURRENCY);
		
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);
		distanceAccount.account = transferInVCB.getDistanceAccount(driver, sourceAccount.account, listAccount);
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, distanceAccount.account);

		log.info("TC_04_Step_06: Lay so du tai khoan dich");
		String beforeBalanceOfAccount2 = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS);
		long beforeBalanceAmountOfAccount2 = convertMoneyToLong(beforeBalanceOfAccount2, Constants.VND_CURRENCY);

		log.info("TC_04_Step_07:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		log.info("TC_04_Step_09: Chon tai khoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);
		transferInVCB.sleep(driver, 500);

		log.info("TC_04_Step_09: Lay so du tai khoan chuyen");
		String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS);
		long beforeBalanceAmountOfAccount1 = convertMoneyToLong(beforeBalanceOfAccount1, Constants.VND_CURRENCY);

		log.info("TC_04_Step_10: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, distanceAccount.account, TittleData.INPUT_ACCOUNT_BENEFICI);

		log.info("TC_04_Step_11: Kiem tra Ngay hieu luc mac dinh");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, tommorrowDate));

		log.info("TC_04_Step_12: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInFutureForPassword.TRANSFER_AMOUNT, TittleData.AMOUNT);

		log.info("TC_04_Step_13: Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, TittleData.TRANSFER_INFO, "3");

		log.info("TC_04_Step_14: Click nguoi chuyen tra");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TittleData.FEE_TRANSFER_SOURCE_ACCOUNT_PAY);

		log.info("TC_04_Step_15: Click nguoi chuyen tra");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TittleData.BENEFICI_PAY);

		log.info("TC_04_Step_16: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_04_Step_17: Kiem tra hinh thuc chuyen tien hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.FORM_TRANSFER), TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[2]);

		log.info("TC_04_Step_18: Kiem tra tai khoan nguon hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SOURCE_ACCOUNT), sourceAccount);

		log.info("TC_04_Step_19: Kiem tra tai khoan dich hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SOURCE_ACCOUNT_VND).contains(sourceAccount.account));

		log.info("TC_04_Step_20: Kiem tra so tien chuyen hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.AMOUNT), addCommasToLong(TransferMoneyInVCB_Data.InputDataInFutureForPassword.TRANSFER_AMOUNT) + " VND");

		log.info("TC_04_Step_21: Kiem tra Ngay hieu luc hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.EFFECTIVE_DATE), transferInVCB.getDayInWeek(tommorrowDate) + " " + tommorrowDate);

		log.info("TC_04_Step_22: Kiem tra phuong thuc tra phi");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.InputDataInVCB.COST[1]));

		log.info("TC_04_Step_23: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.CONTENT), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_04_Step_24: Chon Phuong thuc nhap");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.METHOD_VALIDATE);

		log.info("TC_04_Step_25: Chon Mật khẩu đăng nhập");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.PAYMENT_OPTIONS[0]);

		log.info("TC_04_Step_26: Kiem tra so tien phi hien thi");
		String transferFee = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.FEE_AMOUNT);
		fee = convertAvailableBalanceCurrentcyOrFeeToLong(transferFee);

		log.info("TC_04_Step_27: Click Tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_04_Step_28: Nhap OTP");
		transferInVCB.inputToDynamicSmartOTP(driver, LogIn_Data.Login_Account.NEW_PASSWORD, TittleData.CONTINUE_BTN);

		log.info("TC_04_Step_29: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_04_Step_30: Kiem  tra giao dich thanh cong");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.SUCESSFULL_CREATED_ORDER));

		log.info("TC_04_Step_31: Kiem tra so tien chuyen");
		verifyEquals(transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.SUCESSFULL_CREATED_ORDER, "3"), addCommasToLong(TransferMoneyInVCB_Data.InputDataInFutureForPassword.TRANSFER_AMOUNT) + " VND");

		log.info("TC_04_Step_32: Lay thoi gian tao giao dich");
		transferTime = transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.SUCESSFULL_CREATED_ORDER, "4");
		verifyEquals(transferTime, TransferMoneyInVCB_Data.Output.ORDER_TIME + transferInVCB.getDayInWeek(tommorrowDate) + " " + tommorrowDate);

		log.info("TC_04_Step_33: Lay ma giao dich");
		transactionNumber = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.CODE_TRANSFER);
		log.info("TC_04_Step_27: Lay ma giao dich" + transactionNumber);

		log.info("TC_04_Step_34: Kiem tra ten nguoi thu huong hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.NAME_BENEFICI), name_receiver);

		log.info("TC_04_Step_35: Kiem tra tai khoan dich hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.ACCOUNT_BENEFICI), distanceAccount.account);

		log.info("TC_04_Step_36: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.CONTENT), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_04_Step_37: Click thuc hien giao dich moi");
		transferInVCB.clickToDynamicButton(driver, TittleData.NEW_TRANSFER);

		log.info("TC_04_Step_38: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		log.info("TC_04_Step_39: Chon tai ngoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);
		transferInVCB.sleep(driver, 500);

		log.info("TC_04_Step_40: Lay so du tai khoan chuyen");
		String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS);
		long afterBalanceAmountOfAccount1 = convertMoneyToLong(afterBalanceOfAccount1, Constants.VND_CURRENCY);

		log.info("TC_04_Step_41: Kiem tra so du tai khoan chuyen sau khi thuc hien giao dich");
		verifyEquals(beforeBalanceAmountOfAccount1, afterBalanceAmountOfAccount1);

		log.info("TC_04_Step_42: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		log.info("TC_04_Step_43: Chon tai khoan nguoi nhan");
		sourceAccount = transferInVCB.chooseSourceAccount(driver, Constants.AMOUNT_VND, Constants.VND_CURRENCY);
		transferInVCB.sleep(driver, 500);

		log.info("TC_04_Step_44: Lay so du kha dung tai khoan nhan");
		String afterBalanceOfAccount2 = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS);

		log.info("TC_04_Step_45: Kiem tra so du tai khoan nhan sau khi nhan tien");
		long afterBalanceAmountOfAccount2 = convertMoneyToLong(afterBalanceOfAccount2, Constants.VND_CURRENCY);
		verifyEquals(beforeBalanceAmountOfAccount2, afterBalanceAmountOfAccount2);

	}

	@Test
	public void TC_05_KiemTraChiTietGiaoDichChuyenTienTuongLaiCoPhiGiaoDichNguoiNhanTraVNDVaXacThucBangOTP() {
		log.info("TC_05_Step_01 : Click  nut Back");
		transferInVCB.clickToDynamicBackIcon(driver, TransferMoneyInVCB_Data.InputDataInVCB.TRANSFER_TYPE_VIETCOM_BANL);

		log.info("TC_05_Step_02: Click vao More Icon");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicIcon(driver, TittleData.STATUS_TRANSACTION);

		log.info("TC_05_Step_03: Click Tat Ca Cac Loai Giao Dich");
		transStatus = PageFactoryManager.getTransferMoneyStatusPageObject(driver);
		transStatus.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[1]);

		log.info("TC_05_Step_04 Chon Chuyen Tien Trong VCB");
		transStatus.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[2]);

		log.info("TC_05_Step_05: Kiem tra from date hien thi");
		verifyEquals(transStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate"), getBackwardDate(29));

		log.info("TC_05_Step_06: Kiem tra todate hien thi");
		verifyEquals(transStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate"), today);

		log.info("TC_05_Step_07: Kiem tra Note hien thi");
		verifyTrue(transStatus.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyStatus_Data.Output.NOTE));

		log.info("TC_05_Step_08: CLick Tim Kiem");
		transStatus.clickToDynamicButton(driver, TittleData.SEARCH);

		log.info("TC_05_Step 09: Kiem tra ngay tao giao dich hien thi");
		String orderTime = transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvDate");
		verifyTrue(orderTime.contains(getCurrentDay() + "/" + getCurrenMonth() + "/" + getCurrentYear()));

		log.info("TC_05_Step 11: Kiem tra nguoi nhan");
		verifyEquals(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvContent"), name_receiver);

		log.info("TC_05_Step 12: Kiem tra trang thai giao dich");
		verifyEquals(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvStatus"), TransferMoneyStatus_Data.Output.WAITING_STATUS);

		log.info("TC_05_Step 13: Kiem tra so tien giao dich");
		verifyEquals(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "1", "com.VCB:id/tvMoney"), addCommasToLong(TransferMoneyInVCB_Data.InputDataInFutureForOTP.TRANSFER_AMOUNT) + " VND");

		log.info("TC_05_Step 14: click vao giao dich");
		transStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_05_Step 15: Kiem tra ngay giao dich hien thi");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, TittleData.CREAT_DATE), orderTime);

		log.info("TC_05_Step 17: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, TittleData.NAME_RECEIPTER), name_receiver);

		log.info("TC_05_Step 18: Kiem tra so tien thi");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, TittleData.AMOUNT), addCommasToLong(TransferMoneyInVCB_Data.InputDataInFutureForOTP.TRANSFER_AMOUNT) + " VND");

		log.info("TC_05_Step 19: Kiem tra ngay hieu luc");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, TittleData.EFFECTIVE_DATE), tommorrowDate);

		log.info("TC_05_Step 20: Kiem tra ghi chú");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, TittleData.NOTE), "");

		log.info("TC_05_Step 21: Kiem tra trang thai hieu luc");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, TittleData.STATUS), TransferMoneyStatus_Data.Output.WAITING_STATUS);

		log.info("TC_05_Step_22: Click quay lai");
		transStatus.clickToDynamicBackIcon(driver, TittleData.DETAIL_TRANSFER);

	}

	@Test
	public void TC_06_HuyLenhGiaoDichTuongLaiXacThucOTP() {

		log.info("TC_06_Step_01: Click Huy Lenh");
		transStatus.clickToDynamicIconInOrderStatus(driver, "0", "com.VCB:id/tvHuyLenh");

		log.info("TC_06_Step_02: Click Dong Y");
		transStatus.clickToDynamicButton(driver, TittleData.ACCEPT);

		log.info("TC_06_Step_04: Kiem Tra Success hiển thị");
		verifyEquals(transStatus.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), TittleData.SUCCESS);

		log.info("TC_06_Step_05: Click quay lai");
		transStatus.clickToDynamicButton(driver, TittleData.CLOSE);

		log.info("TC_06_Step_06: Kiem tra trang thai Da Huy hien thi");
		verifyEquals(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvStatus"), TransferMoneyStatus_Data.Output.CANCEL_STATUS);

		log.info("TC_06_Step_07: click vao giao dich");
		transStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_06_Step_08: Kiem tra trang thai  Da Huy hien thi");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, TittleData.STATUS), TransferMoneyStatus_Data.Output.CANCEL_STATUS);

		log.info("TC_06_Step_09: Click quay lai");
		transStatus.clickToDynamicBackIcon(driver, TittleData.DETAIL_TRANSFER);

		log.info("TC_06_Step_10: Click quay lai");
		transStatus.clickToDynamicBackIcon(driver, TittleData.STATUS_TRANSACTION);

	}

	@Test
	public void TC_07_ChuyenTienTuongLaiCoPhiGiaoDichNguoiChuyenTraUSDVaXacThucBangOTP() {

		log.info("TC_07_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, TransferMoneyInVCB_Data.InputDataInVCB.TRANSFER_TYPE);

		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);

		log.info("TC_07_Step_02: Chon chuyen tien ngay gia tri hien tai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[0]);

		log.info("TC_07_Step_03: Chon chuyen tien ngay gia tri hien tai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[2]);

		log.info("TC_07_Step_04:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);
		List<String> listAccount = transferInVCB.getListSourceAccount(driver, Constants.VND_CURRENCY);
		log.info("TC_07_Step_05: Chon tai khoan dich");
		sourceAccount = transferInVCB.chooseSourceAccount(driver, Constants.MONEY_CHECK_USD, Constants.USD_CURRENCY);
		
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);
		distanceAccount.account = transferInVCB.getDistanceAccount(driver, sourceAccount.account, listAccount);
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, distanceAccount.account);


		log.info("TC_07_Step_06: Lay so du tai khoan dich");
		String beforeBalanceOfAccount2 = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS);
		long beforeBalanceAmountOfAccount2 = convertMoneyToLong(beforeBalanceOfAccount2, Constants.VND_CURRENCY);

		log.info("TC_07_Step_07:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		log.info("TC_07_Step_08: Chon tai khoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);
		transferInVCB.sleep(driver, 500);

		log.info("TC_07_Step_09: Lay so du tai khoan chuyen");
		String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS);
		double beforeBalanceAmountOfAccount1 = convertMoneyToDouble(beforeBalanceOfAccount1, Constants.USD_CURRENCY);

		log.info("TC_07_Step_10: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, distanceAccount.account, TittleData.INPUT_ACCOUNT_BENEFICI);

		log.info("TC_07_Step_11: Kiem tra Ngay hieu luc mac dinh");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, tommorrowDate));

		log.info("TC_07_Step_12: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInFutureForPassword.AMOUNT_OF_EUR_OR_USD_TRANSFER, TittleData.AMOUNT);

		log.info("TC_07_Step_13: Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, TittleData.TRANSFER_INFO, "3");

		log.info("TC_07_Step_14: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_07_Step_15: Kiem tra hinh thuc chuyen tien hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.FORM_TRANSFER), TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[2]);

		log.info("TC_07_Step_16: Kiem tra tai khoan nguon hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SOURCE_ACCOUNT), sourceAccount.account);

		log.info("TC_07_Step_17: Kiem tra tai khoan dich hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SOURCE_ACCOUNT_VND).contains(distanceAccount.account));

		log.info("TC_07_Step_18: Kiem tra so tien chuyen hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.AMOUNT), String.format("%.02f", convertMoneyToDouble(TransferMoneyInVCB_Data.InputDataInFutureForPassword.AMOUNT_OF_EUR_OR_USD_TRANSFER, Constants.USD_CURRENCY)) + " USD");

		log.info("TC_07_Step_19: Kiem tra Ngay hieu luc hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.EFFECTIVE_DATE), transferInVCB.getDayInWeek(tommorrowDate) + " " + tommorrowDate);

		log.info("TC_07_Step_20: Kiem tra phuong thuc tra phi");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.InputDataInVCB.COST[0]));

		log.info("TC_07_Step_21: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.CONTENT), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_07_Step_22: Chon Phuong thuc nhap");
		transferInVCB.scrollDownToButton(driver, TittleData.CONTINUE_BTN);
		transferInVCB.clickToDynamicDropDown(driver, TittleData.METHOD_VALIDATE);

		log.info("TC_07_Step_23: Chon Mật khẩu đăng nhập");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.PAYMENT_OPTIONS[1]);

		log.info("TC_07_Step_24: Kiem tra so tien phi hien thi");
		String transferFee = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.FEE_AMOUNT);
		fee = convertAvailableBalanceCurrentcyOrFeeToLong(transferFee);

		log.info("TC_07_Step_25: Click Tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_07_Step_26: Nhap OTP");
		transferInVCB.inputToDynamicPopupPasswordInput(driver, LogIn_Data.Login_Account.NEW_PASSWORD, TittleData.CONTINUE_BTN);

		log.info("TC_07_Step_27: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_07_Step_28: Kiem  tra giao dich thanh cong");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.SUCESSFULL_CREATED_ORDER));

		log.info("TC_07_Step_29: Kiem tra so tien chuyen");
		verifyEquals(transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.SUCESSFULL_CREATED_ORDER, "3"), addCommasToDouble(TransferMoneyInVCB_Data.InputDataInFutureForPassword.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " USD");

		log.info("TC_07_Step_30: Lay thoi gian tao giao dich");
		transferTime = transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.SUCESSFULL_CREATED_ORDER, "4");
		verifyEquals(transferTime, TransferMoneyInVCB_Data.Output.ORDER_TIME + transferInVCB.getDayInWeek(tommorrowDate) + " " + tommorrowDate);

		log.info("TC_07_Step_31: Lay ma giao dich");
		transactionNumber = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.CODE_TRANSFER);
		log.info("TC_07_Step_32: Lay ma giao dich" + transactionNumber);

		log.info("TC_07_Step_33: Kiem tra ten nguoi thu huong hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.NAME_BENEFICI), name_receiver);

		log.info("TC_07_Step_34: Kiem tra tai khoan dich hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.ACCOUNT_BENEFICI), sourceAccount.account);

		log.info("TC_07_Step_35: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.CONTENT), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_07_Step_36: Click thuc hien giao dich moi");
		transferInVCB.clickToDynamicButton(driver, TittleData.NEW_TRANSFER);

		log.info("TC_07_Step_37: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		log.info("TC_07_Step_38: Chon tai ngoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.USD_ACCOUNT);
		transferInVCB.sleep(driver, 500);

		log.info("TC_07_Step_39: Lay so du tai khoan chuyen");
		String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS);
		double afterBalanceAmountOfAccount1 = convertMoneyToDouble(afterBalanceOfAccount1, Constants.USD_CURRENCY);

		log.info("TC_07_Step_40: Kiem tra so du tai khoan chuyen sau khi thuc hien giao dich");
		verifyEquals(beforeBalanceAmountOfAccount1, afterBalanceAmountOfAccount1);

		log.info("TC_07_Step_41: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		log.info("TC_07_Step_42: Chon tai khoan nguoi nhan");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);
		transferInVCB.sleep(driver, 500);

		log.info("TC_07_Step_43: Lay so du kha dung tai khoan nhan");
		String afterBalanceOfAccount2 = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS);

		log.info("TC_07_Step_44: Kiem tra so du tai khoan nhan sau khi nhan tien");
		long afterBalanceAmountOfAccount2 = convertMoneyToLong(afterBalanceOfAccount2, Constants.VND_CURRENCY);
		verifyEquals(beforeBalanceAmountOfAccount2, afterBalanceAmountOfAccount2);

	}

	@Test
	public void TC_08_KiemTraChiTietGiaoDichChuyenTienTuongLaiCoPhiGiaoDichNguoiChuyenTraUSDVaXacThucBangOTP() {
		log.info("TC_08_Step_01 : Click  nut Back");
		transferInVCB.clickToDynamicBackIcon(driver, TransferMoneyInVCB_Data.InputDataInVCB.TRANSFER_TYPE_VIETCOM_BANL);

		log.info("TC_08_Step_02: Click vao More Icon");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicIcon(driver, TittleData.STATUS_TRANSACTION);

		log.info("TC_08_Step_03: Click Tat Ca Cac Loai Giao Dich");
		transStatus = PageFactoryManager.getTransferMoneyStatusPageObject(driver);
		transStatus.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[1]);

		log.info("TC_08_Step_04: Chon Chuyen Tien Trong VCB");
		transStatus.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[2]);

		log.info("TC_08_Step_05: Kiem tra from date hien thi");
		verifyEquals(transStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate"), getBackwardDate(29));

		log.info("TC_08_Step_06: Kiem tra todate hien thi");
		verifyEquals(transStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate"), today);

		log.info("TC_08_Step_07: Kiem tra Note hien thi");
		verifyTrue(transStatus.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyStatus_Data.Output.NOTE));

		log.info("TC_08_Step_08: CLick Tim Kiem");
		transStatus.clickToDynamicButton(driver, TittleData.SEARCH);

		log.info("TC_08_Step 09: Kiem tra ngay tao giao dich hien thi");
		String orderTime = transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvDate");
		verifyTrue(orderTime.contains(getCurrentDay() + "/" + getCurrenMonth() + "/" + getCurrentYear()));

		log.info("TC_08_Step 10: Kiem tra thoi gian tao dao dich");

		log.info("TC_08_Step 11: Kiem tra nguoi nhan");
		verifyEquals(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvContent"), name_receiver);

		log.info("TC_08_Step 12: Kiem tra trang thai giao dich");
		verifyEquals(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvStatus"), TransferMoneyStatus_Data.Output.WAITING_STATUS);

		log.info("TC_08_Step 13: Kiem tra so tien giao dich");
		verifyEquals(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "1", "com.VCB:id/tvMoney"), TransferMoneyInVCB_Data.InputDataInFutureForOTP.AMOUNT_OF_EUR_OR_USD_TRANSFER + " USD");

		log.info("TC_08_Step 14: click vao giao dich");
		transStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_08_Step 15: Kiem tra ngay giao dich hien thi");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, TittleData.CREAT_DATE), orderTime);

		log.info("TC_08_Step 16: Kiem tra thoi gian tao giao dich hien thi");

		log.info("TC_08_Step 17: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, TittleData.NAME_RECEIPTER), name_receiver);

		log.info("TC_08_Step 18: Kiem tra so tien thi");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, TittleData.AMOUNT), TransferMoneyInVCB_Data.InputDataInFutureForOTP.AMOUNT_OF_EUR_OR_USD_TRANSFER + " USD");

		log.info("TC_08_Step 19: Kiem tra ngay hieu luc");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, TittleData.EFFECTIVE_DATE), tommorrowDate);

		log.info("TC_08_Step 20: Kiem tra ghi chú");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, TittleData.NOTE), "");

		log.info("TC_08_Step 21: Kiem tra trang thai hieu luc");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, TittleData.STATUS), TransferMoneyStatus_Data.Output.WAITING_STATUS);

		log.info("TC_08_Step_22: Click quay lai");
		transStatus.clickToDynamicBackIcon(driver, TittleData.DETAIL_TRANSFER);

	}

	@Test
	public void TC_09_HuyLenhGiaoDichTuongLaiXacThucOTP() {

		log.info("TC_09_Step_01: Click Huy Lenh");
		transStatus.clickToDynamicIconInOrderStatus(driver, "0", "com.VCB:id/tvHuyLenh");

		log.info("TC_09_Step_02: Click Dong Y");
		transStatus.clickToDynamicButton(driver, TittleData.ACCEPT);

		log.info("TC_09_Step_04: Kiem Tra Success hiển thị");
		verifyEquals(transStatus.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), TittleData.SUCCESS);

		log.info("TC_09_Step_05: Click quay lai");
		transStatus.clickToDynamicButton(driver, TittleData.CLOSE);

		log.info("TC_09_Step_06: Kiem tra trang thai Da Huy hien thi");
		verifyEquals(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvStatus"), TransferMoneyStatus_Data.Output.CANCEL_STATUS);

		log.info("TC_09_Step_07: click vao giao dich");
		transStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_09_Step_08: Kiem tra trang thai  Da Huy hien thi");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, TittleData.STATUS), TransferMoneyStatus_Data.Output.CANCEL_STATUS);

		log.info("TC_09_Step_09: Click quay lai");
		transStatus.clickToDynamicBackIcon(driver, TittleData.DETAIL_TRANSFER);

		log.info("TC_09_Step_10: Click quay lai");
		transStatus.clickToDynamicBackIcon(driver, TittleData.STATUS_TRANSACTION);

	}

	@Test
	public void TC_10_ChuyenTienTuongLaiCoPhiGiaoDichNguoiNhanTraEURVaXacThucBangOTP() {

		log.info("TC_10_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, TransferMoneyInVCB_Data.InputDataInVCB.TRANSFER_TYPE);

		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);

		log.info("TC_10_Step_02: Chon chuyen tien ngay gia tri hien tai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[0]);

		log.info("TC_10_Step_03: Chon chuyen tien ngay gia tri hien tai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[2]);
		
		log.info("TC_10_Step_04:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);
		List<String> listAccount = transferInVCB.getListSourceAccount(driver, Constants.VND_CURRENCY);
		log.info("TC_10_Step_05: Chon tai khoan dich");
		sourceAccount = transferInVCB.chooseSourceAccount(driver, Constants.MONEY_CHECK_EUR, Constants.EUR_CURRENCY);
		
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);
		distanceAccount.account = transferInVCB.getDistanceAccount(driver, sourceAccount.account, listAccount);
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, distanceAccount.account);

		log.info("TC_10_Step_06: Lay so du tai khoan dich");
		String beforeBalanceOfAccount2 = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS);
		long beforeBalanceAmountOfAccount2 = convertMoneyToLong(beforeBalanceOfAccount2, Constants.VND_CURRENCY);

		log.info("TC_10_Step_07:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		log.info("TC_10_Step_08: Chon tai khoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);
		transferInVCB.sleep(driver, 500);

		log.info("TC_10_Step_09: Lay so du tai khoan chuyen");
		String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS);
		double beforeBalanceAmountOfAccount1 = convertMoneyToDouble(beforeBalanceOfAccount1, "EUR");

		log.info("TC_10_Step_10: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, distanceAccount.account, TittleData.INPUT_ACCOUNT_BENEFICI);

		log.info("TC_10_Step_11: Kiem tra Ngay hieu luc mac dinh");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, tommorrowDate));

		log.info("TC_10_Step_12: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInFutureForPassword.AMOUNT_OF_EUR_OR_USD_TRANSFER, TittleData.AMOUNT);

		log.info("TC_10_Step_13: Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, TittleData.TRANSFER_INFO, "3");

		log.info("TC_10_Step_14: Click nguoi chuyen tra");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TittleData.FEE_TRANSFER_SOURCE_ACCOUNT_PAY);

		log.info("TC_10_Step_15: Click nguoi chuyen tra");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TittleData.BENEFICI_PAY);

		log.info("TC_10_Step_16: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_10_Step_17: Kiem tra hinh thuc chuyen tien hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.FORM_TRANSFER), TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[2]);

		log.info("TC_10_Step_18: Kiem tra tai khoan nguon hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SOURCE_ACCOUNT), Account_Data.Valid_Account.EUR_ACCOUNT);

		log.info("TC_10_Step_19: Kiem tra tai khoan dich hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SOURCE_ACCOUNT_VND).contains(sourceAccount.account));

		log.info("TC_10_Step_20: Kiem tra so tien chuyen hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.AMOUNT), addCommasToDouble(TransferMoneyInVCB_Data.InputDataInFutureForPassword.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " EUR");

		log.info("TC_10_Step_21: Kiem tra Ngay hieu luc hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.EFFECTIVE_DATE), transferInVCB.getDayInWeek(tommorrowDate) + " " + tommorrowDate);

		log.info("TC_10_Step_22: Kiem tra phuong thuc tra phi");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.InputDataInVCB.COST[1]));

		log.info("TC_10_Step_23: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.CONTENT), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_10_Step_24: Chon Phuong thuc nhap");
		transferInVCB.scrollDownToButton(driver, TittleData.CONTINUE_BTN);
		transferInVCB.clickToDynamicDropDown(driver, TittleData.METHOD_VALIDATE);

		log.info("TC_10_Step_25: Chon Mật khẩu đăng nhập");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.PAYMENT_OPTIONS[1]);

		log.info("TC_10_Step_26: Kiem tra so tien phi hien thi");
		String transferFee = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.FEE_AMOUNT);
		fee = convertAvailableBalanceCurrentcyOrFeeToLong(transferFee);

		log.info("TC_10_Step_27: Click Tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_10_Step_28: Nhap OTP");
		transferInVCB.inputToDynamicPopupPasswordInput(driver, LogIn_Data.Login_Account.NEW_PASSWORD, TittleData.CONTINUE_BTN);

		log.info("TC_10_Step_29: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_10_Step_30: Kiem  tra giao dich thanh cong");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.SUCESSFULL_CREATED_ORDER));

		log.info("TC_10_Step_31: Kiem tra so tien chuyen");
		verifyEquals(transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.SUCESSFULL_CREATED_ORDER, "3"), addCommasToDouble(TransferMoneyInVCB_Data.InputDataInFutureForPassword.AMOUNT_OF_EUR_OR_USD_TRANSFER) + " EUR");

		log.info("TC_10_Step_32: Lay thoi gian tao giao dich");
		transferTime = transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.SUCESSFULL_CREATED_ORDER, "4");
		verifyEquals(transferTime, TransferMoneyInVCB_Data.Output.ORDER_TIME + transferInVCB.getDayInWeek(tommorrowDate) + " " + tommorrowDate);

		log.info("TC_10_Step_33: Lay ma giao dich");
		transactionNumber = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.CODE_TRANSFER);
		log.info("TC_10_Step_34: Lay ma giao dich" + transactionNumber);

		log.info("TC_10_Step_35: Kiem tra ten nguoi thu huong hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.NAME_BENEFICI), name_receiver);

		log.info("TC_10_Step_36: Kiem tra tai khoan dich hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.ACCOUNT_BENEFICI), distanceAccount.account);

		log.info("TC_10_Step_37: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.CONTENT), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_10_Step_38: Click thuc hien giao dich moi");
		transferInVCB.clickToDynamicButton(driver, TittleData.NEW_TRANSFER);

		log.info("TC_10_Step_39: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		log.info("TC_10_Step_40: Chon tai ngoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.EUR_ACCOUNT);
		transferInVCB.sleep(driver, 500);

		log.info("TC_10_Step_41: Lay so du tai khoan chuyen");
		String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS);
		double afterBalanceAmountOfAccount1 = convertMoneyToDouble(afterBalanceOfAccount1, "EUR");

		log.info("TC_10_Step_42: Kiem tra so du tai khoan chuyen sau khi thuc hien giao dich");
		verifyEquals(beforeBalanceAmountOfAccount1, afterBalanceAmountOfAccount1);

		log.info("TC_10_Step_43: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		log.info("TC_10_Step_44: Chon tai khoan nguoi nhan");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);
		transferInVCB.sleep(driver, 500);

		log.info("TC_10_Step_45: Lay so du kha dung tai khoan nhan");
		String afterBalanceOfAccount2 = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS);

		log.info("TC_10_Step_46: Kiem tra so du tai khoan nhan sau khi nhan tien");
		long afterBalanceAmountOfAccount2 = convertMoneyToLong(afterBalanceOfAccount2, Constants.VND_CURRENCY);
		verifyEquals(beforeBalanceAmountOfAccount2, afterBalanceAmountOfAccount2);

	}

	@Test
	public void TC_11_KiemTraChiTietGiaoDichChuyenTienTuongLaiCoPhiGiaoDichNguoiNhanTraEURVaXacThucBangOTP() {
		log.info("TC_11_Step_01 : Click  nut Back");
		transferInVCB.clickToDynamicBackIcon(driver, TransferMoneyInVCB_Data.InputDataInVCB.TRANSFER_TYPE_VIETCOM_BANL);

		log.info("TC_11_Step_02: Click vao More Icon");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicIcon(driver, TittleData.STATUS_TRANSACTION);

		log.info("TC_11_Step_03: Click Tat Ca Cac Loai Giao Dich");
		transStatus = PageFactoryManager.getTransferMoneyStatusPageObject(driver);
		transStatus.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[1]);

		log.info("TC_11_Step_04: Chon Chuyen Tien Trong VCB");
		transStatus.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[2]);

		log.info("TC_11_Step_05: Kiem tra from date hien thi");
		verifyEquals(transStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate"), getBackwardDate(29));

		log.info("TC_11_Step_06: Kiem tra todate hien thi");
		verifyEquals(transStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate"), today);

		log.info("TC_11_Step_07: Kiem tra Note hien thi");
		verifyTrue(transStatus.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyStatus_Data.Output.NOTE));

		log.info("TC_11_Step_08: CLick Tim Kiem");
		transStatus.clickToDynamicButton(driver, TittleData.SEARCH);

		log.info("TC_11_Step 09: Kiem tra ngay tao giao dich hien thi");
		String orderTime = transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvDate");
		verifyTrue(orderTime.contains(getCurrentDay() + "/" + getCurrenMonth() + "/" + getCurrentYear()));

		log.info("TC_11_Step 11: Kiem tra nguoi nhan");
		verifyEquals(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvContent"), name_receiver);

		log.info("TC_11_Step 12: Kiem tra trang thai giao dich");
		verifyEquals(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvStatus"), TransferMoneyStatus_Data.Output.WAITING_STATUS);

		log.info("TC_11_Step 13: Kiem tra so tien giao dich");
		verifyEquals(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "1", "com.VCB:id/tvMoney"), TransferMoneyInVCB_Data.InputDataInFutureForOTP.AMOUNT_OF_EUR_OR_USD_TRANSFER + " EUR");

		log.info("TC_11_Step 14: click vao giao dich");
		transStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_11_Step 15: Kiem tra ngay giao dich hien thi");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, TittleData.CREAT_DATE), orderTime);

		log.info("TC_11_Step 16: Kiem tra thoi gian tao giao dich hien thi");

		log.info("TC_11_Step 17: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, TittleData.NAME_RECEIPTER), name_receiver);

		log.info("TC_11_Step 18: Kiem tra so tien thi");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, TittleData.AMOUNT), TransferMoneyInVCB_Data.InputDataInFutureForOTP.AMOUNT_OF_EUR_OR_USD_TRANSFER + " EUR");

		log.info("TC_11_Step 19: Kiem tra ngay hieu luc");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, TittleData.EFFECTIVE_DATE), tommorrowDate);

		log.info("TC_11_Step 20: Kiem tra ghi chú");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, TittleData.NOTE), "");

		log.info("TC_11_Step 21: Kiem tra trang thai hieu luc");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, TittleData.STATUS), TransferMoneyStatus_Data.Output.WAITING_STATUS);

		log.info("TC_11_Step_22: Click quay lai");
		transStatus.clickToDynamicBackIcon(driver, TittleData.DETAIL_TRANSFER);

	}

	@Test
	public void TC_12_HuyLenhGiaoDichTuongLaiXacThucOTP() {

		log.info("TC_12_Step_01: Click Huy Lenh");
		transStatus.clickToDynamicIconInOrderStatus(driver, "0", "com.VCB:id/tvHuyLenh");

		log.info("TC_12_Step_02: Click Dong Y");
		transStatus.clickToDynamicButton(driver, TittleData.ACCEPT);

		log.info("TC_12_Step_04: Kiem Tra Success hiển thị");
		verifyEquals(transStatus.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), TittleData.SUCCESS);

		log.info("TC_12_Step_05: Click quay lai");
		transStatus.clickToDynamicButton(driver, TittleData.CLOSE);

		log.info("TC_12_Step_06: Kiem tra trang thai Da Huy hien thi");
		verifyEquals(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvStatus"), TransferMoneyStatus_Data.Output.CANCEL_STATUS);

		log.info("TC_12_Step_07: click vao giao dich");
		transStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_12_Step_08: Kiem tra trang thai  Da Huy hien thi");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, TittleData.STATUS), TransferMoneyStatus_Data.Output.CANCEL_STATUS);

		log.info("TC_12_Step_09: Click quay lai");
		transStatus.clickToDynamicBackIcon(driver, TittleData.DETAIL_TRANSFER);

		log.info("TC_12_Step_10: Click quay lai");
		transStatus.clickToDynamicBackIcon(driver, TittleData.STATUS_TRANSACTION);

	}

	@Test
	public void TC_13_ChuyenTienTuongLaiDenTaiKhoanKhacChuSoHuuCoPhiGiaoDichNguoiChuyenTraVNDVaXacThucBangOTP() {
		log.info("TC_13_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, TransferMoneyInVCB_Data.InputDataInVCB.TRANSFER_TYPE);

		log.info("TC_13_Step_02: Chon chuyen tien ngay gia tri hien tai");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[0]);

		log.info("TC_13_Step_03: Chon chuyen tien ngay gia tri hien tai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[2]);

		log.info("TC_13_Step_04:Click tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		log.info("TC_13_Step_05: Chon tai khoan chuyen");
		sourceAccount = transferInVCB.chooseSourceAccount(driver, Constants.AMOUNT_VND, Constants.VND_CURRENCY);

		log.info("TC_13_Step_06: Lay so du tai khoan chuyen");
		String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS);
		long beforeBalanceAmountOfAccount1 = convertMoneyToLong(beforeBalanceOfAccount1, Constants.VND_CURRENCY);

		log.info("TC_13_Step_07: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, account_other_owner, TittleData.INPUT_ACCOUNT_BENEFICI);

		log.info("TC_13_Step_08: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInFutureForOTP.TRANSFER_AMOUNT, TittleData.AMOUNT);

		log.info("TC_13_Step_09: Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "3");

		log.info("TC_13_Step_10: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_13_Step_11: Kiem tra hinh thuc chuyen tien hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.FORM_TRANSFER), TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[2]);

		log.info("TC_13_Step_12: Kiem tra tai khoan nguon hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SOURCE_ACCOUNT), sourceAccount.account);

		log.info("TC_13_Step_13: Kiem tra tai khoan dich hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SOURCE_ACCOUNT_VND).contains(name_other_owner));

		log.info("TC_13_Step_14: Kiem tra so tien chuyen hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.AMOUNT), (addCommasToLong(TransferMoneyInVCB_Data.InputDataInFutureForOTP.TRANSFER_AMOUNT)) + " VND");

		log.info("TC_13_Step_15: Kiem tra phuong thuc thanh toan phi bang OTP");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.InputDataInVCB.COST[0]));

		log.info("TC_13_Step_16: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.CONTENT), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_13_Step_17: Chon Phuong thuc nhap");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.METHOD_VALIDATE);

		log.info("TC_13_Step_18: Chon SMS OTP");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TittleData.SMS_OTP);

		log.info("TC_13_Step_19: Kiem tra so tien phi hien thi");
		String transferFee = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.FEE_AMOUNT);
		fee = convertAvailableBalanceCurrentcyOrFeeToLong(transferFee);

		log.info("TC_13_Step_20: Click Tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_13_Step_21: Nhap OTP");
		transferInVCB.inputToDynamicPopupPasswordInput(driver, LogIn_Data.Login_Account.NEW_PASSWORD, TittleData.CONTINUE_BTN);

		log.info("TC_13_Step_22: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_13_Step_23: Kiem  tra giao dich thanh cong");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.SUCESSFULL_CREATED_ORDER));

		log.info("TC_13_Step_24: Lay thoi gian tao giao dich");
		transferTime = transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.SUCESSFULL_CREATED_ORDER, "4");
		verifyEquals(transferTime, TransferMoneyInVCB_Data.Output.ORDER_TIME + transferInVCB.getDayInWeek(tommorrowDate) + " " + tommorrowDate);

		log.info("TC_13_Step_25: Lay ma giao dich");
		transactionNumber = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.CODE_TRANSFER);

		log.info("TC_13_Step_26: Kiem tra ten nguoi thu huong hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.NAME_BENEFICI), name_other_owner);

		log.info("TC_13_Step_27: Kiem tra tai khoan dich hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.ACCOUNT_BENEFICI), account_other_owner);

		log.info("TC_13_Step_28: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.CONTENT), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_13_Step_29: Click thuc hien giao dich moi");
		transferInVCB.clickToDynamicButton(driver, TittleData.NEW_TRANSFER);

		log.info("TC_13_Step_30: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		log.info("TC_13_Step_31: Chon tai ngoan chuyen");
		sourceAccount = transferInVCB.chooseSourceAccount(driver, Constants.AMOUNT_VND, Constants.VND_CURRENCY);

		log.info("TC_13_Step_32: Lay so du tai khoan chuyen");
		String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS);
		long afterBalanceAmountOfAccount1 = convertMoneyToLong(afterBalanceOfAccount1, Constants.VND_CURRENCY);

		log.info("TC_13_Step_33: Kiem tra so du tai khoan chuyen sau khi thuc hien giao dich");
		verifyEquals(beforeBalanceAmountOfAccount1, afterBalanceAmountOfAccount1);

	}

	@Test
	public void TC_14_KiemTraChiTietGiaoDichChuyenTienTuongLaiCoPhiGiaoDichNguoiChuyenTraEURVaXacThucBangOTP() {
		log.info("TC_14_Step_01 : Click  nut Back");
		transferInVCB.clickToDynamicBackIcon(driver, TransferMoneyInVCB_Data.InputDataInVCB.TRANSFER_TYPE_VIETCOM_BANL);

		log.info("TC_14_Step_02: Click vao Trang Thai lenh chuyen Tien");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicIcon(driver, TittleData.STATUS_TRANSACTION);

		log.info("TC_14_Step_03: Click Tat Ca Cac Loai Giao Dich");
		transStatus = PageFactoryManager.getTransferMoneyStatusPageObject(driver);
		transStatus.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[1]);

		log.info("TC_14_Step_04: Chon Chuyen Tien Trong VCB");
		transStatus.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[2]);

		log.info("TC_14_Step_05: Kiem tra from date hien thi");
		verifyEquals(transStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate"), getBackwardDate(29));

		log.info("TC_14_Step_06: Kiem tra todate hien thi");
		verifyEquals(transStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate"), today);

		log.info("TC_14_Step_07: Kiem tra Note hien thi");
		verifyTrue(transStatus.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyStatus_Data.Output.NOTE));

		log.info("TC_14_Step_08: CLick Tim Kiem");
		transStatus.clickToDynamicButton(driver, TittleData.SEARCH);

		log.info("TC_14_Step 09: Kiem tra ngay tao giao dich hien thi");
		String orderTime = transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvDate");
		verifyTrue(orderTime.contains(getCurrentDay() + "/" + getCurrenMonth() + "/" + getCurrentYear()));

		log.info("TC_14_Step 10: Kiem tra thoi gian tao dao dich");

		log.info("TC_14_Step 11: Kiem tra nguoi nhan");
		verifyEquals(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvContent"), name_other_owner);

		log.info("TC_14_Step 12: Kiem tra trang thai giao dich");
		verifyEquals(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvStatus"), TransferMoneyStatus_Data.Output.WAITING_STATUS);

		log.info("TC_14_Step 13: Kiem tra so tien giao dich");
		verifyEquals(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "1", "com.VCB:id/tvMoney"), addCommasToLong(TransferMoneyInVCB_Data.InputDataInFutureForOTP.TRANSFER_AMOUNT) + " VND");

		log.info("TC_14_Step 14: click vao giao dich");
		transStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_14_Step 15: Kiem tra ngay giao dich hien thi");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, TittleData.CREAT_DATE), orderTime);

		log.info("TC_14_Step 17: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, TittleData.NAME_RECEIPTER), name_other_owner);

		log.info("TC_14_Step 18: Kiem tra so tien thi");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, TittleData.AMOUNT), addCommasToLong(TransferMoneyInVCB_Data.InputDataInFutureForOTP.TRANSFER_AMOUNT) + " VND");

		log.info("TC_14_Step 19: Kiem tra ngay hieu luc");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, TittleData.EFFECTIVE_DATE), tommorrowDate);

		log.info("TC_14_Step 20: Kiem tra ghi chú");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, TittleData.NOTE), "");

		log.info("TC_14_Step 21: Kiem tra trang thai hieu luc");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, TittleData.STATUS), TransferMoneyStatus_Data.Output.WAITING_STATUS);

		log.info("TC_14_Step_22: Click quay lai");
		transStatus.clickToDynamicBackIcon(driver, TittleData.DETAIL_TRANSFER);

	}

	@Test
	public void TC_15_HuyLenhGiaoDichTuongLaiXacThucOTP() {

		log.info("TC_15_Step_01: Click Huy Lenh");
		transStatus.clickToDynamicIconInOrderStatus(driver, "0", "com.VCB:id/tvHuyLenh");

		log.info("TC_15_Step_02: Click Dong Y");
		transStatus.clickToDynamicButton(driver, TittleData.ACCEPT);

		log.info("TC_15_Step_04: Kiem Tra Success hiển thị");
		verifyEquals(transStatus.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), TittleData.SUCCESS);

		log.info("TC_15_Step_05: Click quay lai");
		transStatus.clickToDynamicButton(driver, TittleData.CLOSE);

		log.info("TC_15_Step_06: Kiem tra trang thai Da Huy hien thi");
		verifyEquals(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvStatus"), TransferMoneyStatus_Data.Output.CANCEL_STATUS);

		log.info("TC_15_Step_07: click vao giao dich");
		transStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_15_Step_08: Kiem tra trang thai  Da Huy hien thi");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, TittleData.STATUS), TransferMoneyStatus_Data.Output.CANCEL_STATUS);

		log.info("TC_15_Step_09: Click quay lai");
		transStatus.clickToDynamicBackIcon(driver, TittleData.DETAIL_TRANSFER);

		log.info("TC_15_Step_10: Click quay lai");
		transStatus.clickToDynamicBackIcon(driver, TittleData.STATUS_TRANSACTION);

	}

	@Test
	public void TC_16_ChuyenTienTuongLaiDenTaiKhoanKhacChuSoHuuCoPhiGiaoDichNguoiNhanTraVNDVaXacThucBangOTP() {
		log.info("TC_16_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, TransferMoneyInVCB_Data.InputDataInVCB.TRANSFER_TYPE);

		log.info("TC_16_Step_02: Chon chuyen tien ngay gia tri hien tai");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[0]);

		log.info("TC_16_Step_03: Chon chuyen tien tuong lai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[2]);

		log.info("TC_16_Step_04:Click tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		log.info("TC_16_Step_05: Chon tai khoan chuyen");
		sourceAccount = transferInVCB.chooseSourceAccount(driver, Constants.AMOUNT_VND, Constants.VND_CURRENCY);

		log.info("TC_16_Step_06: Lay so du tai khoan chuyen");
		String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS);
		long beforeBalanceAmountOfAccount1 = convertMoneyToLong(beforeBalanceOfAccount1, Constants.VND_CURRENCY);

		log.info("TC_16_Step_07: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, account_other_owner, TittleData.INPUT_ACCOUNT_BENEFICI);

		log.info("TC_16_Step_08: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInFutureForOTP.TRANSFER_AMOUNT, TittleData.AMOUNT);

		log.info("TC_16_Step_09: Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, TittleData.TRANSFER_INFO, "3");

		log.info("TC_16_Step_14: Click nguoi chuyen tra");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TittleData.FEE_TRANSFER_SOURCE_ACCOUNT_PAY);

		log.info("TC_16_Step_15: Click nguoi chuyen tra");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TittleData.BENEFICI_PAY);

		log.info("TC_16_Step_10: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_16_Step_11: Kiem tra hinh thuc chuyen tien hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.FORM_TRANSFER), TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[2]);

		log.info("TC_16_Step_12: Kiem tra tai khoan nguon hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SOURCE_ACCOUNT), sourceAccount.account);

		log.info("TC_16_Step_13: Kiem tra tai khoan dich hien thi");
		verifyTrue(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SOURCE_ACCOUNT_VND).contains(account_other_owner));

		log.info("TC_16_Step_14: Kiem tra so tien chuyen hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.AMOUNT), (addCommasToLong(TransferMoneyInVCB_Data.InputDataInFutureForOTP.TRANSFER_AMOUNT)) + " VND");

		log.info("TC_16_Step_15: Kiem tra nguoi thanh toan phi chuyen bang OTP");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.InputDataInVCB.COST[1]));

		log.info("TC_16_Step_16: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.CONTENT), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_16_Step_17: Chon Phuong thuc nhap");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.METHOD_VALIDATE);

		log.info("TC_16_Step_18: Chon SMS OTP");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TittleData.SMS_OTP);

		log.info("TC_16_Step_19: Kiem tra so tien phi hien thi");
		String transferFee = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.FEE_AMOUNT);
		fee = convertAvailableBalanceCurrentcyOrFeeToLong(transferFee);

		log.info("TC_16_Step_20: Click Tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_16_Step_21: Nhap OTP");
		transferInVCB.inputToDynamicPopupPasswordInput(driver, LogIn_Data.Login_Account.NEW_PASSWORD, TittleData.CONTINUE_BTN);

		log.info("TC_16_Step_22: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_16_Step_23: Kiem  tra giao dich thanh cong");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.SUCESSFULL_CREATED_ORDER));

		log.info("TC_16_Step_24: Lay thoi gian tao giao dich");
		transferTime = transferInVCB.getDynamicTransferTimeAndMoney(driver, TransferMoneyInVCB_Data.Output.SUCESSFULL_CREATED_ORDER, "4");
		verifyEquals(transferTime, TransferMoneyInVCB_Data.Output.ORDER_TIME + transferInVCB.getDayInWeek(tommorrowDate) + " " + tommorrowDate);

		log.info("TC_16_Step_25: Lay ma giao dich");
		transactionNumber = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.CODE_TRANSFER);

		log.info("TC_16_Step_26: Kiem tra ten nguoi thu huong hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.NAME_BENEFICI), name_other_owner);

		log.info("TC_16_Step_27: Kiem tra tai khoan dich hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.ACCOUNT_BENEFICI), account_other_owner);

		log.info("TC_16_Step_28: Kiem tra noi dung hien thi");
		verifyEquals(transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.CONTENT), TransferMoneyInVCB_Data.InputDataInVCB.NOTE);

		log.info("TC_16_Step_29: Click thuc hien giao dich moi");
		transferInVCB.clickToDynamicButton(driver, TittleData.NEW_TRANSFER);

		log.info("TC_16_Step_30: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		log.info("TC_16_Step_31: Chon tai ngoan chuyen");
		sourceAccount = transferInVCB.chooseSourceAccount(driver, Constants.AMOUNT_VND, Constants.VND_CURRENCY);

		log.info("TC_16_Step_32: Lay so du tai khoan chuyen");
		String afterBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS);
		long afterBalanceAmountOfAccount1 = convertMoneyToLong(afterBalanceOfAccount1, Constants.VND_CURRENCY);

		log.info("TC_16_Step_33: Kiem tra so du tai khoan chuyen sau khi thuc hien giao dich");
		verifyEquals(beforeBalanceAmountOfAccount1, afterBalanceAmountOfAccount1);

	}

	@Test
	public void TC_17_KiemTraChiTietGiaoDichChuyenTienTuongLaiCoPhiGiaoDichNguoiNhanTraEURVaXacThucBangOTP() {
		log.info("TC_17_Step_01 : Click  nut Back");
		transferInVCB.clickToDynamicBackIcon(driver, TransferMoneyInVCB_Data.InputDataInVCB.TRANSFER_TYPE_VIETCOM_BANL);

		log.info("TC_17_Step_02: Click vao Trang Thai lenh chuyen Tien");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicIcon(driver, TittleData.STATUS_TRANSACTION);

		log.info("TC_17_Step_03: Click Tat Ca Cac Loai Giao Dich");
		transStatus = PageFactoryManager.getTransferMoneyStatusPageObject(driver);
		transStatus.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[1]);

		log.info("TC_17_Step_04: Chon Chuyen Tien Trong VCB");
		transStatus.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.OPTION_TRANSFER[2]);

		log.info("TC_17_Step_05: Kiem tra from date hien thi");
		verifyEquals(transStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate"), getBackwardDate(29));

		log.info("TC_17_Step_06: Kiem tra todate hien thi");
		verifyEquals(transStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate"), today);

		log.info("TC_17_Step_07: Kiem tra Note hien thi");
		verifyTrue(transStatus.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyStatus_Data.Output.NOTE));

		log.info("TC_17_Step_08: CLick Tim Kiem");
		transStatus.clickToDynamicButton(driver, TittleData.SEARCH);

		log.info("TC_17_Step 09: Kiem tra ngay tao giao dich hien thi");
		String orderTime = transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvDate");
		verifyTrue(orderTime.contains(getCurrentDay() + "/" + getCurrenMonth() + "/" + getCurrentYear()));

		log.info("TC_17_Step 11: Kiem tra nguoi nhan");
		verifyEquals(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvContent"), name_other_owner);

		log.info("TC_17_Step 12: Kiem tra trang thai giao dich");
		verifyEquals(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvStatus"), TransferMoneyStatus_Data.Output.WAITING_STATUS);

		log.info("TC_17_Step 13: Kiem tra so tien giao dich");
		verifyEquals(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "1", "com.VCB:id/tvMoney"), addCommasToLong(TransferMoneyInVCB_Data.InputDataInFutureForOTP.TRANSFER_AMOUNT) + " VND");

		log.info("TC_17_Step 14: click vao giao dich");
		transStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_17_Step 15: Kiem tra ngay giao dich hien thi");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, TittleData.CREAT_DATE), orderTime);

		log.info("TC_17_Step 17: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, TittleData.NAME_RECEIPTER), name_other_owner);

		log.info("TC_17_Step 18: Kiem tra so tien thi");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, TittleData.AMOUNT), addCommasToLong(TransferMoneyInVCB_Data.InputDataInFutureForOTP.TRANSFER_AMOUNT) + " VND");

		log.info("TC_17_Step 19: Kiem tra ngay hieu luc");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, TittleData.EFFECTIVE_DATE), tommorrowDate);

		log.info("TC_17_Step 20: Kiem tra ghi chú");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, TittleData.NOTE), "");

		log.info("TC_17_Step 21: Kiem tra trang thai hieu luc");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, TittleData.STATUS), TransferMoneyStatus_Data.Output.WAITING_STATUS);

		log.info("TC_17_Step_22: Click quay lai");
		transStatus.clickToDynamicBackIcon(driver, TittleData.DETAIL_TRANSFER);

	}

	@Test
	public void TC_18_HuyLenhGiaoDichTuongLaiXacThucOTP() {

		log.info("TC_18_Step_01: Click Huy Lenh");
		transStatus.clickToDynamicIconInOrderStatus(driver, "0", "com.VCB:id/tvHuyLenh");

		log.info("TC_18_Step_02: Click Dong Y");
		transStatus.clickToDynamicButton(driver, TittleData.ACCEPT);

		log.info("TC_18_Step_04: Kiem Tra Success hiển thị");
		verifyEquals(transStatus.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), TittleData.SUCCESS);

		log.info("TC_18_Step_05: Click quay lai");
		transStatus.clickToDynamicButton(driver, TittleData.CLOSE);

		log.info("TC_18_Step_06: Kiem tra trang thai Da Huy hien thi");
		verifyEquals(transStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvStatus"), TransferMoneyStatus_Data.Output.CANCEL_STATUS);

		log.info("TC_18_Step_07: click vao giao dich");
		transStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_18_Step_08: Kiem tra trang thai  Da Huy hien thi");
		verifyEquals(transStatus.getDynamicTextInTransactionDetail(driver, TittleData.STATUS), TransferMoneyStatus_Data.Output.CANCEL_STATUS);

		log.info("TC_18_Step_09: Click quay lai");
		transStatus.clickToDynamicBackIcon(driver, TittleData.DETAIL_TRANSFER);

		log.info("TC_18_Step_10: Click quay lai");
		transStatus.clickToDynamicBackIcon(driver, TittleData.STATUS_TRANSACTION);

	}

	@AfterClass(alwaysRun = true)

	public void afterClass() {
		closeApp();
		service.stop();
	}

}
