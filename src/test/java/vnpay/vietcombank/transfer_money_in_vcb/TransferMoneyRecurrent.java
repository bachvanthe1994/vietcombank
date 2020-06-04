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
import model.TransferInVCBRecurrent;
import pageObjects.LogInPageObject;
import pageObjects.TransferMoneyInVcbPageObject;
import pageObjects.TransferMoneyStatusPageObject;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransferMoneyInVCB_Data.InputDataInVCB;
import vietcombank_test_data.TransferMoneyInVCB_Data.InputData_MoneyRecurrent;
import vietcombank_test_data.TransferMoneyInVCB_Data.InputText_MoneyRecurrent;
import vietcombank_test_data.TransferMoneyInVCB_Data.TittleData;
import vietcombank_test_data.TransferMoneyQuick_Data;
import vietcombank_test_data.TransferMoneyStatus_Data;

public class TransferMoneyRecurrent extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private TransferMoneyInVcbPageObject transferRecurrent;
	private TransferMoneyStatusPageObject transferStatus;
	String today = getCurrentDay() + "/" + getCurrenMonth() + "/" + getCurrentYear();
	private String transferTime, expectAvailableBalance,receivedAccount,receivedName,sameOwnerName;
	long transferFee = 0;
	double transferFeeCurrentcy = 0;
	String password = "";

	SourceAccountModel sourceAccount = new SourceAccountModel();
	SourceAccountModel receiverAccount = new SourceAccountModel();
	
	TransferInVCBRecurrent info = new TransferInVCBRecurrent("","", "1", InputData_MoneyRecurrent.DAY_TEXT, "", "", InputData_MoneyRecurrent.VND_AMOUNT, InputData_MoneyRecurrent.TRANSFER_PAY, InputData_MoneyRecurrent.CONTENT, InputDataInVCB.PAYMENT_OPTIONS[1]);
	TransferInVCBRecurrent info1 = new TransferInVCBRecurrent("","", "2", InputData_MoneyRecurrent.DAY_TEXT, "", "", InputData_MoneyRecurrent.USD_EUR_AMOUNT, InputData_MoneyRecurrent.TRANSFER_PAY, InputData_MoneyRecurrent.CONTENT, InputDataInVCB.PAYMENT_OPTIONS[1]);
	TransferInVCBRecurrent info2 = new TransferInVCBRecurrent("","", "1", InputData_MoneyRecurrent.MONTH_TEXT, "", "", InputData_MoneyRecurrent.VND_AMOUNT, InputData_MoneyRecurrent.RECEIVED_PAY, InputData_MoneyRecurrent.CONTENT, InputDataInVCB.PAYMENT_OPTIONS[0]);
	TransferInVCBRecurrent info3 = new TransferInVCBRecurrent("","", "2", InputData_MoneyRecurrent.MONTH_TEXT, "", "", InputData_MoneyRecurrent.USD_EUR_AMOUNT, InputData_MoneyRecurrent.RECEIVED_PAY, InputData_MoneyRecurrent.CONTENT, InputDataInVCB.PAYMENT_OPTIONS[0]);
	TransferInVCBRecurrent info4 = new TransferInVCBRecurrent("","", "2", InputData_MoneyRecurrent.DAY_TEXT, "", "", InputData_MoneyRecurrent.USD_EUR_AMOUNT, InputData_MoneyRecurrent.TRANSFER_PAY, InputData_MoneyRecurrent.CONTENT, InputDataInVCB.PAYMENT_OPTIONS[1]);
	TransferInVCBRecurrent info5 = new TransferInVCBRecurrent("","", "2", InputData_MoneyRecurrent.DAY_TEXT, "", "", InputData_MoneyRecurrent.USD_EUR_AMOUNT, InputData_MoneyRecurrent.RECEIVED_PAY, InputData_MoneyRecurrent.CONTENT, InputDataInVCB.PAYMENT_OPTIONS[0]);

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
		
		receivedAccount = getDataInCell(0).trim();
		receivedName = getDataInCell(1).trim();
		
		password = pass;
		
		transferStatus = PageFactoryManager.getTransferMoneyStatusPageObject(driver);
		transferRecurrent = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		log.info("TC_01_1_Click Chuyen tien trong ngan hang");
		
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.TRANSFER_TYPE);
	}

	@Test
	public void TC_01_ChuyenTien_VND_DinhKy_1Ngay_CoPhiGiaoDichNguoiChuyenTra_CungChuTK_XacThucBangOTP() {

		log.info("TC_01_1_Chon phuong thuc chuyen tien");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.OPTION_TRANSFER[0]);
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.OPTION_TRANSFER[1]);

		log.info("TC_01_2_Chon tai khoan nguon");
		transferRecurrent.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		List<String> listDistanceAccount = transferRecurrent.getListSourceAccount(driver, Constants.VND_CURRENCY);

		sourceAccount = transferRecurrent.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);
		expectAvailableBalance = sourceAccount.balance;
		
		log.info("TC_01_3_Nhap tai khoan nhan");
		receiverAccount.account = transferRecurrent.getDistanceAccount(driver, sourceAccount.account, listDistanceAccount);
		transferRecurrent.inputToDynamicInputBox(driver, receiverAccount.account, TittleData.INPUT_ACCOUNT_BENEFICI);

		log.info("TC_01_4_Chon tan suat");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, InputData_MoneyRecurrent.DAY_TEXT);
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info.frequencyCategory);
		transferRecurrent.inputFrequencyNumber(info.frequencyNumber);

		log.info("TC_01_5_Nhap so tien");
		transferRecurrent.inputToDynamicInputBox(driver, info.money, TittleData.AMOUNT);

		log.info("TC_01_6_Chon nguoi tra phi giao dich");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, TittleData.FEE_TRANSFER_SOURCE_ACCOUNT_PAY);
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info.fee);

		log.info("TC_01_7_Nhap noi dung");
		transferRecurrent.inputToDynamicInputBoxByHeader(driver, info.note, TittleData.TRANSFER_INFO, "3");

		log.info("TC_01_8_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_01_9_Kiem tra man hinh xac nhan thong tin");
		log.info("TC_01_9_1_Kiem tra hinh thuc chuyen tien");
		transferRecurrent.scrollUpToText(driver, TittleData.FORM_TRANSFER);
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, TittleData.FORM_TRANSFER), InputDataInVCB.OPTION_TRANSFER[1]);

		log.info("TC_01_9_2_Kiem tra tai khoan nguon");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, TittleData.SOURCE_ACCOUNT), sourceAccount.account);

		log.info("TC_01_9_3_Kiem tra tai khoan dich");
		verifyTrue(transferRecurrent.getDynamicTextInTransactionDetail(driver, TittleData.SOURCE_ACCOUNT_VND).contains(receiverAccount.account));

		log.info("TC_01_9_4_Kiem tra so tien");
		verifyTrue(transferRecurrent.getDynamicTextInTransactionDetail(driver, TittleData.AMOUNT).contains(addCommasToLong(info.money)));

		log.info("TC_01_9_5_Kiem tra tan suat");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, InputText_MoneyRecurrent.TRANSFER_FREQUENCY_TEXT), info.frequencyNumber + " " + info.frequencyCategory + InputText_MoneyRecurrent.TRANSFER_PER_TIMES_TEXT);
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, InputText_MoneyRecurrent.TRANSACTION_TIMES_TEXT), "2");

		log.info("TC_01_9_7_Kiem tra noi dung");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, TittleData.CONTENT), info.note);

		log.info("TC_01_10_Chon phuong thuc xac thuc");
		transferRecurrent.scrollDownToText(driver, TittleData.METHOD_VALIDATE);
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.PAYMENT_OPTIONS[0]);
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info.authenticationMethod);

		log.info("TC_01_11_Kiem tra so tien phi");
		String fee= transferRecurrent.getDynamicTextInTransactionDetail(driver, InputText_MoneyRecurrent.FEE_PER_TIMES_TEXT);
		transferFee = convertAvailableBalanceCurrentcyOrFeeToLong(fee);

		log.info("TC_01_12_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_01_12_1_Nhap OTP va click Tiep tuc");
		transferRecurrent.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, TittleData.CONTINUE_BTN);
		transferRecurrent.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_01_13_Kiem tra man hinh Lap lenh thanh cong");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY_IN_VCB_RECURRENT));

		log.info("TC_01_13_1_Lay thoi gian giao dich");
		transferTime = transferRecurrent.getTransferMoneyRecurrentTimeSuccess(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY_IN_VCB_RECURRENT);

		log.info("TC_01_13_2_Lay ten nguoi huong thu");
		sameOwnerName = transferRecurrent.getDynamicTextInTransactionDetail(driver, TittleData.NAME_BENEFICI);

		log.info("TC_01_13_3_Kiem tra tai khoan dich");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, TittleData.ACCOUNT_BENEFICI), receiverAccount.account);

		log.info("TC_01_13_4_Kiem tra noi dung");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, TittleData.CONTENT), info.note);
		verifyTrue(transferRecurrent.isDynamicButtonDisplayed(driver, TittleData.NEW_TRANSFER));

		log.info("TC_01_14_Click Thuc hien giao dich moi");
		login.clickToDynamicButton(driver, TittleData.NEW_TRANSFER);
	}

	@Test
	public void TC_02_ChuyenTien_VND_KiemTraSoDuSauGiaoDich_PhiGiaoDich_NguoiChuyenTra_CungChuTK() {
		
		log.info("TC_02_01_Chon tai khoan nguon");
		transferRecurrent.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_02_02_Lay so du");
		transferRecurrent.scrollUpToText(driver, TittleData.SOURCE_ACCOUNT);
		String actualAvailableBalance = transferRecurrent.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS);
		
		log.info("TC_02_03_Kiem tra so du khong thay doi khi chua den han");
		verifyEquals(actualAvailableBalance, expectAvailableBalance+" "+sourceAccount.currentcy);

		log.info("TC_03_1: Click  nut Back");
		transferRecurrent.clickToDynamicBackIcon(driver, TittleData.TRANSFER_IN_VCB);

	}

	@Test
	public void TC_03_TrangThaiGiaoDich_ChuyenTien_VND_DinhKy_1Ngay_CoPhiGiaoDichNguoiChuyenTra_CungChuTK_XacThucBangOTP() {
		
		String startDate = getForwardDate(1);
		String endDate = getForwardDate(1 + Integer.parseInt(info.frequencyNumber));

		log.info("TC_03_2: Click Trang thai lenh chuyen tien");
		transferRecurrent.scrollDownToText(driver, InputText_MoneyRecurrent.BILLS_PAYMENT_TEXT);
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, InputText_MoneyRecurrent.TRANSFER_MONEY_STATUS_TEXT);

		log.info("TC_03_3: Chon loai giao dich");
		transferStatus.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyStatus_Data.Input.OPTION_TRANSFER[1]);
		transferStatus.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyStatus_Data.Input.OPTION_TRANSFER[1]);

		log.info("TC_03_04: Kiem tra from date hien thi");
		verifyEquals(transferStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate"), getBackwardDate(29));

		log.info("TC_03_05: Kiem tra todate hien thi");
		verifyEquals(transferStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate"), today);

		log.info("TC_03_06: Kiem tra Note hien thi");
		verifyTrue(transferStatus.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyStatus_Data.Output.NOTE));

		log.info("TC_03_07: Click Tim Kiem");
		transferStatus.clickToDynamicButton(driver, TransferMoneyStatus_Data.Text.SEARCH);

		log.info("TC_03_8: Kiem tra ngay tao giao dich hien thi");
		String reportTime1 = transferStatus.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
		verifyEquals(convertDateTimeIgnoreHHmmss(reportTime1), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_03_9: Kiem tra nguoi nhan");
		verifyEquals(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvContent"), sameOwnerName);

		log.info("TC_03_10: Kiem tra trang thai giao dich");
		verifyEquals(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvStatus"), TransferMoneyStatus_Data.Output.WAITING_STATUS);

		log.info("TC_03_11: Kiem tra so tien giao dich");
		verifyEquals(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "1", "com.VCB:id/tvMoney"), addCommasToLong(info.money) + " VND");

		log.info("TC_03_12: click vao giao dich");
		transferStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_03_13: Kiem tra thoi gian tao giao dich hien thi");
		String reportTime2 = transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.CREAT_DATE);
		verifyEquals(reportTime2, reportTime1);

		log.info("TC_03_14: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.NAME_RECEIPTER), sameOwnerName);

		log.info("TC_03_15: Kiem tra so tien thi");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.AMOUNT), addCommasToLong(info.money) + " VND");

		log.info("TC_03_16: Kiem tra tan suat");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, InputText_MoneyRecurrent.FREQUENCY_TEXT), info.frequencyNumber + " " + info.frequencyCategory + TransferMoneyStatus_Data.Text.TRANSFER_PER_TIMES_TEXT);

		log.info("TC_03_17: Kiem ngay bat dau");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.START_DATE_TEXT), startDate);

		log.info("TC_03_18: Kiem ngay ket thuc");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.END_DATE_TEXT), endDate);

		log.info("TC_03_19: Click  nut Back");
		transferStatus.clickToDynamicBackIcon(driver, TransferMoneyStatus_Data.Text.DETAIL_TRANSFER);

	}

	@Test
	public void TC_04_HuyGiaoDich_ChuyenTien_VND_DinhKy_1Ngay_CoPhiGiaoDichNguoiChuyenTra_CungChuTK_XacThucBangOTP() {
		
		String startDate = getForwardDate(1);
		String endDate = getForwardDate(1 + Integer.parseInt(info.frequencyNumber));

		log.info("TC_04_01: Click Huy Lenh");
		transferStatus.clickToDynamicIconInOrderStatus(driver, "0", "com.VCB:id/tvHuyLenh");

		log.info("TC_04_02: Click Dong Y");
		transferStatus.clickToDynamicButton(driver, TransferMoneyStatus_Data.Text.ACCEPT);

		log.info("TC_04_03: Kiem Tra Success hiển thị");
		verifyTrue(transferStatus.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyStatus_Data.Text.SUCCESS));

		log.info("TC_04_04: Click nut Dong");
		transferStatus.clickToDynamicButton(driver, TransferMoneyStatus_Data.Text.CLOSE);

		log.info("TC_04_05: Kiem tra trang thai Da Huy hien thi");
		verifyEquals(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvStatus"), TransferMoneyStatus_Data.Output.CANCEL_STATUS);

		log.info("TC_04_06: click vao giao dich");
		transferStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_04_07: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.CREAT_DATE).contains(convertTransferTimeToReportDateTime(transferTime)));

		log.info("TC_04_08: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.NAME_RECEIPTER), sameOwnerName);

		log.info("TC_04_09: Kiem tra so tien thi");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.AMOUNT), addCommasToLong(info.money) + " VND");

		log.info("TC_04_10: Kiem tra tan suat");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.FREQUENCY_TEXT), info.frequencyNumber + " " + info.frequencyCategory + TransferMoneyStatus_Data.Text.TRANSFER_PER_TIMES_TEXT);

		log.info("TC_04_11: Kiem ngay bat dau");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.START_DATE_TEXT), startDate);

		log.info("TC_04_12: Kiem ngay ket thuc");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.END_DATE_TEXT), endDate);

		log.info("TC_04_13: Kiem tra trang thai  Da Huy hien thi");
		verifyTrue(transferStatus.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyStatus_Data.Text.UNSUCCESS_MESSAGE));

		log.info("TC_04_14: Click  nut Back");
		transferStatus.clickToDynamicBackIcon(driver, TransferMoneyStatus_Data.Text.DETAIL_TRANSFER);

		log.info("TC_04_15: Click  nut Back");
		transferStatus.clickToDynamicBackIcon(driver, TransferMoneyStatus_Data.Text.TRANSFER_MONEY_STATUS_TEXT);
	}

	@Test
	public void TC_05_ChuyenTien_NgoaiTe_DinhKy_2Ngay_CoPhiGiaoDichNguoiNhanTra_XacThucBangOTP() {
		
		log.info("TC_05_1_Click Chuyen tien trong ngan hang");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.TRANSFER_TYPE);

		log.info("TC_05_2_Chon phuong thuc chuyen tien");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.OPTION_TRANSFER[0]);
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.OPTION_TRANSFER[1]);

		log.info("TC_05_3_Chon tai khoan nguon");
		transferRecurrent.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);
		sourceAccount = transferRecurrent.chooseSourceAccount(driver, Constants.MONEY_CHECK_EUR, Constants.EUR_CURRENCY);
		expectAvailableBalance = sourceAccount.balance;

		log.info("TC_05_3_1_Nhan tai khoan nhan");
		transferRecurrent.inputToDynamicInputBox(driver, receivedAccount, TittleData.INPUT_ACCOUNT_BENEFICI);

		log.info("TC_05_4_Chon tan suat");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, InputData_MoneyRecurrent.DAY_TEXT);
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info1.frequencyCategory);
		transferRecurrent.inputFrequencyNumber(info1.frequencyNumber);

		log.info("TC_05_5_Nhap so tien");
		transferRecurrent.inputToDynamicInputBox(driver, info1.money, TittleData.AMOUNT);

		log.info("TC_05_6_Chon nguoi tra phi giao dich");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, TittleData.FEE_TRANSFER_SOURCE_ACCOUNT_PAY);
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info1.fee);

		log.info("TC_05_7_Nhap noi dung");
		transferRecurrent.inputToDynamicInputBoxByHeader(driver, info1.note, TittleData.TRANSFER_INFO, "3");

		log.info("TC_05_8_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_05_9_Kiem tra man hinh xac nhan thong tin");
		transferRecurrent.scrollUpToText(driver, TittleData.FORM_TRANSFER);
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, TittleData.FORM_TRANSFER), InputDataInVCB.OPTION_TRANSFER[1]);

		log.info("TC_05_9_1_Kiem tra tai khoan nguon");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, TittleData.SOURCE_ACCOUNT), sourceAccount.account);

		log.info("TC_05_9_2_Kiem tra tai khoan dich");
		verifyTrue(transferRecurrent.getDynamicTextInTransactionDetail(driver, TittleData.SOURCE_ACCOUNT_VND).contains(receivedAccount));

		log.info("TC_05_9_3_Kiem tra tan suat");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, InputText_MoneyRecurrent.TRANSFER_FREQUENCY_TEXT), info1.frequencyNumber + " " + info1.frequencyCategory + InputText_MoneyRecurrent.TRANSFER_PER_TIMES_TEXT);

		log.info("TC_05_9_4_Kiem tra tan suat");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, InputText_MoneyRecurrent.TRANSACTION_TIMES_TEXT), "2");

		log.info("TC_05_9_5_Kiem tra noi dung");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, TittleData.CONTENT), info1.note);

		log.info("TC_05_10_Chon phuong thuc xac thuc");
		transferRecurrent.scrollDownToText(driver, TittleData.METHOD_VALIDATE);
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.PAYMENT_OPTIONS[0]);
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info1.authenticationMethod);

		log.info("TC_05_11_Kiem tra so tien phi");
		String fee= transferRecurrent.getDynamicTextInTransactionDetail(driver, InputText_MoneyRecurrent.FEE_PER_TIMES_TEXT);
		transferFee = convertAvailableBalanceCurrentcyOrFeeToLong(fee);
		
		log.info("TC_05_12_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_05_12_1_Nhap OTP va click Tiep tuc");
		transferRecurrent.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, TittleData.CONTINUE_BTN);
		transferRecurrent.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_05_13_Kiem tra man hinh Lap lenh thanh cong");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY_IN_VCB_RECURRENT));
		
		log.info("TC_05_13_0_Lay thoi gian giao dich");
		transferTime = transferRecurrent.getTransferMoneyRecurrentTimeSuccess(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY_IN_VCB_RECURRENT);

		log.info("TC_05_13_1_Kiem tra ten nguoi huong thu");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, TittleData.NAME_BENEFICI), receivedName);

		log.info("TC_05_13_2_Kiem tra tai khoan dich");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, TittleData.ACCOUNT_BENEFICI), receivedAccount);

		log.info("TC_05_13_3_Kiem tra ten noi dung");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, TittleData.CONTENT), info1.note);

		log.info("TC_05_13_4_Kiem tra nut thuc hien giao dich moi");
		verifyTrue(transferRecurrent.isDynamicButtonDisplayed(driver, TittleData.NEW_TRANSFER));

		log.info("TC_05_14_Click Thuc hien giao dich moi");
		transferRecurrent.clickToDynamicButton(driver, TittleData.NEW_TRANSFER);

	}

	@Test
	public void TC_06_ChuyenTien_EUR_KiemTraSoDuSauGiaoDich_PhiGiaoDich_NguoiChuyenTra() {
		
		log.info("TC_06_01_Chon tai khoan nguon");
		transferRecurrent.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_06_02_Lay so du");
		transferRecurrent.scrollUpToText(driver, TittleData.SOURCE_ACCOUNT);
		String actualAvailableBalance = transferRecurrent.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS);
		
		log.info("TC_06_03_Kiem tra so du khong thay doi khi chua den han");
		verifyEquals(actualAvailableBalance, expectAvailableBalance+" "+sourceAccount.currentcy);

	}

	@Test
	public void TC_07_TrangThaiGiaoDich_ChuyenTien_NgoaiTe_DinhKy_2Ngay_CoPhiGiaoDichNguoiNhanTra_XacThucBangOTP() {
		
		String startDate = getForwardDate(1);
		String endDate = getForwardDate(1 + Integer.parseInt(info1.frequencyNumber));

		log.info("TC_07_01: Click  nut Back");
		transferRecurrent.clickToDynamicBackIcon(driver, TittleData.TRANSFER_IN_VCB);

		log.info("TC_07_02: Click Trang thai lenh chuyen tien");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, InputText_MoneyRecurrent.TRANSFER_MONEY_STATUS_TEXT);

		log.info("TC_07_03: Chon loai giao dich");
		transferStatus.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyStatus_Data.Input.OPTION_TRANSFER[1]);
		transferStatus.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyStatus_Data.Input.OPTION_TRANSFER[1]);

		log.info("TC_07_04: Kiem tra from date hien thi");
		verifyEquals(transferStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate"), getBackwardDate(29));

		log.info("TC_07_05: Kiem tra todate hien thi");
		verifyEquals(transferStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate"), today);

		log.info("TC_07_06: Kiem tra Note hien thi");
		verifyTrue(transferStatus.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyStatus_Data.Output.NOTE));

		log.info("TC_07_07: Click Tim Kiem");
		transferStatus.clickToDynamicButton(driver, TransferMoneyStatus_Data.Text.SEARCH);

		log.info("TC_07_8: Kiem tra ngay tao giao dich hien thi");
		String reportTime1 = transferStatus.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
		verifyEquals(convertDateTimeIgnoreHHmmss(reportTime1), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_07_9: Kiem tra nguoi nhan");
		verifyEquals(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvContent"), receivedName);

		log.info("TC_07_10: Kiem tra trang thai giao dich");
		verifyEquals(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvStatus"), TransferMoneyStatus_Data.Output.WAITING_STATUS);

		log.info("TC_07_11: Kiem tra so tien giao dich");
		verifyEquals(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "1", "com.VCB:id/tvMoney"), info1.money + " EUR");

		log.info("TC_07_12: click vao giao dich");
		transferStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_07_13: Kiem tra thoi gian tao giao dich hien thi");
		String reportTime2 = transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.CREAT_DATE);
		verifyEquals(reportTime2, reportTime1);

		log.info("TC_07_15: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.NAME_RECEIPTER), receivedName);

		log.info("TC_07_16: Kiem tra so tien thi");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.AMOUNT), info1.money + " EUR");

		log.info("TC_07_17: Kiem tra tan suat");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.FREQUENCY_TEXT), info1.frequencyNumber + " " + info1.frequencyCategory + TransferMoneyStatus_Data.Text.TRANSFER_PER_TIMES_TEXT);

		log.info("TC_07_18: Kiem ngay bat dau");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.START_DATE_TEXT), startDate);

		log.info("TC_07_19: Kiem ngay ket thuc");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.END_DATE_TEXT), endDate);

		log.info("TC_07_20: Click  nut Back");
		transferStatus.clickToDynamicBackIcon(driver, TransferMoneyStatus_Data.Text.DETAIL_TRANSFER);

	}

	@Test
	public void TC_08_HuyGiaoDich_ChuyenTien_NgoaiTe_DinhKy_2Ngay_CoPhiGiaoDichNguoiNhanTra_XacThucBangOTP() {
		
		String startDate = getForwardDate(1);
		String endDate = getForwardDate(1 + Integer.parseInt(info1.frequencyNumber));

		log.info("TC_08_01: Click Huy Lenh");
		transferStatus.clickToDynamicIconInOrderStatus(driver, "0", "com.VCB:id/tvHuyLenh");

		log.info("TC_08_02: Click Dong Y");
		transferStatus.clickToDynamicButton(driver, TransferMoneyStatus_Data.Text.ACCEPT);

		log.info("TC_08_03: Kiem Tra Success hiển thị");
		verifyTrue(transferStatus.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyStatus_Data.Text.SUCCESS));

		log.info("TC_08_04: Click nut Dong");
		transferStatus.clickToDynamicButton(driver, TransferMoneyStatus_Data.Text.CLOSE);

		log.info("TC_08_05: Kiem tra trang thai Da Huy hien thi");
		verifyEquals(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvStatus"), TransferMoneyStatus_Data.Output.CANCEL_STATUS);

		log.info("TC_08_06: click vao giao dich");
		transferStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_08_07: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.CREAT_DATE).contains(convertTransferTimeToReportDateTime(transferTime)));

		log.info("TC_08_08: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.NAME_RECEIPTER), receivedName);

		log.info("TC_08_09: Kiem tra so tien thi");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.AMOUNT), addCommasToLong(info1.money) + " EUR");

		log.info("TC_08_10: Kiem tra tan suat");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.FREQUENCY_TEXT), info1.frequencyNumber + " " + info1.frequencyCategory + TransferMoneyStatus_Data.Text.TRANSFER_PER_TIMES_TEXT);

		log.info("TC_08_11: Kiem ngay bat dau");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.START_DATE_TEXT), startDate);

		log.info("TC_08_12: Kiem ngay ket thuc");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.END_DATE_TEXT), endDate);

		log.info("TC_08_13: Kiem tra trang thai  Da Huy hien thi");
		verifyTrue(transferStatus.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyStatus_Data.Text.UNSUCCESS_MESSAGE));

		log.info("TC_08_14: Click  nut Back");
		transferStatus.clickToDynamicBackIcon(driver, TransferMoneyStatus_Data.Text.DETAIL_TRANSFER);

		log.info("TC_08_15: Click  nut Back");
		transferStatus.clickToDynamicBackIcon(driver, TransferMoneyStatus_Data.Text.TRANSFER_MONEY_STATUS_TEXT);
	}

	@Test
	public void TC_09_ChuyenTien_VND_DinhKy_1Thang_CoPhiGiaoDichNguoiChuyenTra_XacThucBangMatKhau() {
		
		log.info("TC_09_01_Click Chuyen tien trong ngan hang");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.TRANSFER_TYPE);

		log.info("TC_09_02_Chon phuong thuc chuyen tien");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.OPTION_TRANSFER[0]);
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.OPTION_TRANSFER[1]);

		log.info("TC_09_03_Chon tai khoan nguon");
		transferRecurrent.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);
		sourceAccount = transferRecurrent.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);
		expectAvailableBalance = sourceAccount.balance;
		
		log.info("TC_09_03_1_Nhap tai khoan nhan");
		transferRecurrent.inputToDynamicInputBox(driver, receivedAccount, TittleData.INPUT_ACCOUNT_BENEFICI);

		log.info("TC_09_04_Chon tan suat");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, InputData_MoneyRecurrent.DAY_TEXT);
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info2.frequencyCategory);
		transferRecurrent.inputFrequencyNumber(info2.frequencyNumber);

		log.info("TC_09_05_Nhap so tien");
		transferRecurrent.inputToDynamicInputBox(driver, info2.money, TittleData.AMOUNT);

		log.info("TC_09_06_Chon nguoi tra phi giao dich");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, TittleData.FEE_TRANSFER_SOURCE_ACCOUNT_PAY);
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info2.fee);

		log.info("TC_09_07_Nhap noi dung");
		transferRecurrent.inputToDynamicInputBoxByHeader(driver, info2.note, TittleData.TRANSFER_INFO, "3");

		log.info("TC_09_08_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_09_09_Kiem tra man hinh xac nhan thong tin");
		log.info("TC_09_09_1_Kiem tra hinh thuc chuyen tien");
		transferRecurrent.scrollUpToText(driver, TittleData.FORM_TRANSFER);
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, TittleData.FORM_TRANSFER), InputDataInVCB.OPTION_TRANSFER[1]);

		log.info("TC_09_09_2_Kiem tra tai khoan nguon");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, TittleData.SOURCE_ACCOUNT), sourceAccount.account);

		log.info("TC_09_09_3_Kiem tra tai khoan dich");
		verifyTrue(transferRecurrent.getDynamicTextInTransactionDetail(driver, TittleData.SOURCE_ACCOUNT_VND).contains(receivedAccount));

		log.info("TC_09_09_4_Kiem tra tan suat");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, InputText_MoneyRecurrent.TRANSFER_FREQUENCY_TEXT), info2.frequencyNumber + " " + info2.frequencyCategory + InputText_MoneyRecurrent.TRANSFER_PER_TIMES_TEXT);
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, InputText_MoneyRecurrent.TRANSACTION_TIMES_TEXT), "2");

		log.info("TC_09_9_5_Kiem tra noi dung");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, TittleData.CONTENT), info2.note);

		log.info("TC_09_10_Chon phuong thuc xac thuc");
		transferRecurrent.scrollDownToText(driver, TittleData.METHOD_VALIDATE);
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.PAYMENT_OPTIONS[0]);
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info2.authenticationMethod);

		log.info("TC_09_11_Kiem tra so tien phi");
		String fee= transferRecurrent.getDynamicTextInTransactionDetail(driver, InputText_MoneyRecurrent.FEE_PER_TIMES_TEXT);
		transferFee = convertAvailableBalanceCurrentcyOrFeeToLong(fee);
		
		log.info("TC_09_12_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_09_12_1_Nhap MK va click Tiep tuc");
		transferRecurrent.inputToDynamicPopupPasswordInput(driver, password, TittleData.CONTINUE_BTN);
		transferRecurrent.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_09_13_Kiem tra man hinh Lap lenh thanh cong");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY_IN_VCB_RECURRENT));

		log.info("TC_09_13_0_Lay thoi gian giao dich");
		transferTime = transferRecurrent.getTransferMoneyRecurrentTimeSuccess(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY_IN_VCB_RECURRENT);

		log.info("TC_09_13_1_Kiem tra ten nguoi huong thu");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, TittleData.NAME_BENEFICI), receivedName);

		log.info("TC_09_13_2_Kiem tra tai khoan dich");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, TittleData.ACCOUNT_BENEFICI), receivedAccount);

		log.info("TC_09_13_3_Kiem tra noi dung");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, TittleData.CONTENT), info2.note);

		log.info("TC_09_13_4_Kiem tra nut Thuc hien giao dich moi");
		verifyTrue(transferRecurrent.isDynamicButtonDisplayed(driver, TittleData.NEW_TRANSFER));

		log.info("TC_09_14_Click Thuc hien giao dich moi");
		transferRecurrent.clickToDynamicButton(driver, TittleData.NEW_TRANSFER);

	}

	@Test
	public void TC_10_ChuyenTien_VND_KiemTraSoDuSauGiaoDich_PhiGiaoDich_NguoiNhanTra() {
		
		log.info("TC_10_01_Chon tai khoan nguon");
		transferRecurrent.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_10_02_Lay so du");
		transferRecurrent.scrollUpToText(driver, TittleData.SOURCE_ACCOUNT);
		String actualAvailableBalance = transferRecurrent.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS);
		
		log.info("TC_10_03_Kiem tra so du khong thay doi khi chua den han");
		verifyEquals(actualAvailableBalance, expectAvailableBalance+" "+sourceAccount.currentcy);

	}

	@Test
	public void TC_11_TrangThaiGiaoDich_ChuyenTien_VND_DinhKy_1Thang_CoPhiGiaoDichNguoiChuyenTra_XacThucBangMatKhau() {
		
		String startDate = getForwardDate(1);
		String endDate = getForwardMonthAndForwardDay(Long.parseLong(info2.frequencyNumber), 1);

		log.info("TC_11_01: Click  nut Back");
		transferRecurrent.clickToDynamicBackIcon(driver, TittleData.TRANSFER_IN_VCB);

		log.info("TC_11_02: Click Trang thai lenh chuyen tien");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, InputText_MoneyRecurrent.TRANSFER_MONEY_STATUS_TEXT);

		log.info("TC_11_03: Chon loai giao dich");
		transferStatus.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyStatus_Data.Input.OPTION_TRANSFER[1]);
		transferStatus.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyStatus_Data.Input.OPTION_TRANSFER[1]);

		log.info("TC_11_04: Kiem tra from date hien thi");
		verifyEquals(transferStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate"), getBackwardDate(29));

		log.info("TC_11_05: Kiem tra todate hien thi");
		verifyEquals(transferStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate"), today);

		log.info("TC_11_06: Kiem tra Note hien thi");
		verifyTrue(transferStatus.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyStatus_Data.Output.NOTE));

		log.info("TC_11_07: Click Tim Kiem");
		transferStatus.clickToDynamicButton(driver, TransferMoneyStatus_Data.Text.SEARCH);

		log.info("TC_11_08: Kiem tra ngay tao giao dich hien thi");
		String reportTime1 = transferStatus.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
		verifyEquals(convertDateTimeIgnoreHHmmss(reportTime1), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_11_09: Kiem tra nguoi nhan");
		verifyEquals(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvContent"), receivedName);

		log.info("TC_11_10: Kiem tra trang thai giao dich");
		verifyEquals(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvStatus"), TransferMoneyStatus_Data.Output.WAITING_STATUS);

		log.info("TC_11_11: Kiem tra so tien giao dich");
		verifyEquals(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "1", "com.VCB:id/tvMoney"), addCommasToLong(info2.money) + " VND");

		log.info("TC_11_12: click vao giao dich");
		transferStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_11_13: Kiem tra thoi gian tao giao dich hien thi");
		String reportTime2 = transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.CREAT_DATE);
		verifyEquals(reportTime2, reportTime1);

		log.info("TC_11_14: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.NAME_RECEIPTER), receivedName);

		log.info("TC_11_15: Kiem tra so tien thi");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.AMOUNT), addCommasToLong(info2.money) + " VND");

		log.info("TC_11_16: Kiem tra tan suat");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.FREQUENCY_TEXT), info2.frequencyNumber + " " + info2.frequencyCategory + TransferMoneyStatus_Data.Text.TRANSFER_PER_TIMES_TEXT);

		log.info("TC_11_17: Kiem ngay bat dau");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.START_DATE_TEXT), startDate);

		log.info("TC_11_18: Kiem ngay ket thuc");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.END_DATE_TEXT), endDate);

		log.info("TC_11_19: Click  nut Back");
		transferStatus.clickToDynamicBackIcon(driver, TransferMoneyStatus_Data.Text.DETAIL_TRANSFER);

	}

	@Test
	public void TC_12_HuyGiaoDich_ChuyenTien_VND_DinhKy_1Thang_CoPhiGiaoDichNguoiChuyenTra_XacThucBangMatKhau() {
		
		String startDate = getForwardDate(1);
		String endDate = getForwardMonthAndForwardDay(Long.parseLong(info2.frequencyNumber), 1);

		log.info("TC_12_01: Click Huy Lenh");
		transferStatus.clickToDynamicIconInOrderStatus(driver, "0", "com.VCB:id/tvHuyLenh");

		log.info("TC_12_02: Click Dong Y");
		transferStatus.clickToDynamicButton(driver, TransferMoneyStatus_Data.Text.ACCEPT);

		log.info("TC_12_03: Kiem Tra Success hiển thị");
		verifyTrue(transferStatus.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyStatus_Data.Text.SUCCESS));

		log.info("TC_12_04: Click nut Dong");
		transferStatus.clickToDynamicButton(driver, TransferMoneyStatus_Data.Text.CLOSE);

		log.info("TC_12_05: Kiem tra trang thai Da Huy hien thi");
		verifyEquals(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvStatus"), TransferMoneyStatus_Data.Output.CANCEL_STATUS);

		log.info("TC_12_06: click vao giao dich");
		transferStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_12_07: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.CREAT_DATE).contains(convertTransferTimeToReportDateTime(transferTime)));

		log.info("TC_12_08: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.NAME_RECEIPTER), receivedName);

		log.info("TC_12_09: Kiem tra so tien thi");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.AMOUNT), addCommasToLong(info2.money) + " VND");

		log.info("TC_12_10: Kiem tra tan suat");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.FREQUENCY_TEXT), info2.frequencyNumber + " " + info2.frequencyCategory + TransferMoneyStatus_Data.Text.TRANSFER_PER_TIMES_TEXT);

		log.info("TC_12_11: Kiem ngay bat dau");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.START_DATE_TEXT), startDate);

		log.info("TC_12_12: Kiem ngay ket thuc");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.END_DATE_TEXT), endDate);

		log.info("TC_12_13: Kiem tra trang thai  Da Huy hien thi");
		verifyTrue(transferStatus.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyStatus_Data.Text.UNSUCCESS_MESSAGE));

		log.info("TC_12_14: Click  nut Back");
		transferStatus.clickToDynamicBackIcon(driver, TransferMoneyStatus_Data.Text.DETAIL_TRANSFER);

		log.info("TC_12_15: Click  nut Back");
		transferStatus.clickToDynamicBackIcon(driver, TransferMoneyStatus_Data.Text.TRANSFER_MONEY_STATUS_TEXT);
	}

	@Test
	public void TC_13_ChuyenTien_NgoaiTe_DinhKy_2Thang_CoPhiGiaoDichNguoiNhanTra_XacThucBangMatKhauDangNhap() {
		
		log.info("TC_13_01_Click Chuyen tien trong ngan hang");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.TRANSFER_TYPE);

		log.info("TC_13_02_Chon phuong thuc chuyen tien");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.OPTION_TRANSFER[0]);
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.OPTION_TRANSFER[1]);

		log.info("TC_13_03_Chon tai khoan nguon");
		transferRecurrent.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);
		sourceAccount = transferRecurrent.chooseSourceAccount(driver, Constants.MONEY_CHECK_EUR, Constants.EUR_CURRENCY);
		expectAvailableBalance = sourceAccount.balance;
		
		log.info("TC_13_03_1_Nhap tai khoan nhan");
		transferRecurrent.inputToDynamicInputBox(driver, receivedAccount, TittleData.INPUT_ACCOUNT_BENEFICI);

		log.info("TC_13_04_Chon tan suat");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, InputData_MoneyRecurrent.DAY_TEXT);
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info3.frequencyCategory);
		transferRecurrent.inputFrequencyNumber(info3.frequencyNumber);

		log.info("TC_13_05_Nhap so tien");
		transferRecurrent.inputToDynamicInputBox(driver, info3.money, TittleData.AMOUNT);

		log.info("TC_13_06_Chon nguoi tra phi giao dich");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, TittleData.FEE_TRANSFER_SOURCE_ACCOUNT_PAY);
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info3.fee);

		log.info("TC_13_07_Nhap noi dung");
		transferRecurrent.inputToDynamicInputBoxByHeader(driver, info3.note, TittleData.TRANSFER_INFO, "3");

		log.info("TC_13_08_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_13_09_Kiem tra man hinh xac nhan thong tin");
		log.info("TC_13_09_1_Kiem tra hinh thuc chuyen tien");
		transferRecurrent.scrollUpToText(driver, TittleData.FORM_TRANSFER);
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, TittleData.FORM_TRANSFER), InputDataInVCB.OPTION_TRANSFER[1]);

		log.info("TC_13_09_2_Kiem tra tai khoan nguon");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, TittleData.SOURCE_ACCOUNT), sourceAccount.account);

		log.info("TC_13_09_3_Kiem tra tai khoan dich");
		verifyTrue(transferRecurrent.getDynamicTextInTransactionDetail(driver, TittleData.SOURCE_ACCOUNT_VND).contains(receivedAccount));

		log.info("TC_13_09_4_Kiem tra tan suat");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, InputText_MoneyRecurrent.TRANSFER_FREQUENCY_TEXT), info3.frequencyNumber + " " + info3.frequencyCategory + InputText_MoneyRecurrent.TRANSFER_PER_TIMES_TEXT);
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, InputText_MoneyRecurrent.TRANSACTION_TIMES_TEXT), "2");

		log.info("TC_13_09_5_Kiem tra noi dung");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, TittleData.CONTENT), info3.note);

		log.info("TC_13_10_Chon phuong thuc xac thuc");
		transferRecurrent.scrollDownToText(driver, TittleData.METHOD_VALIDATE);
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.PAYMENT_OPTIONS[0]);
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info3.authenticationMethod);

		log.info("TC_13_11_Kiem tra so tien phi");
		String fee= transferRecurrent.getDynamicTextInTransactionDetail(driver, InputText_MoneyRecurrent.FEE_PER_TIMES_TEXT);
		transferFee = convertAvailableBalanceCurrentcyOrFeeToLong(fee);
		
		log.info("TC_13_12_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_13_12_1_Nhap MK va click Tiep tuc");
		transferRecurrent.inputToDynamicPopupPasswordInput(driver, password, TittleData.CONTINUE_BTN);
		transferRecurrent.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_13_13_Kiem tra man hinh Lap lenh thanh cong");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY_IN_VCB_RECURRENT));

		log.info("TC_13_13_0_Lay thoi gian giao dich");
		transferTime = transferRecurrent.getTransferMoneyRecurrentTimeSuccess(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY_IN_VCB_RECURRENT);

		log.info("TC_13_13_1_Kiem tra ten nguoi huong thu");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, TittleData.NAME_BENEFICI), receivedName);

		log.info("TC_13_123_2_Kiem tra tai khoan dich");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, TittleData.ACCOUNT_BENEFICI), receivedAccount);

		log.info("TC_13_13_3_Kiem tra noi dung");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, TittleData.CONTENT), info3.note);

		log.info("TC_13_13_4_Kiem tra nut thuc hien giao dich moi");
		verifyTrue(transferRecurrent.isDynamicButtonDisplayed(driver, TittleData.NEW_TRANSFER));

		log.info("TC_13_14_Click Thuc hien giao dich moi");
		transferRecurrent.clickToDynamicButton(driver, TittleData.NEW_TRANSFER);

	}

	@Test
	public void TC_14_ChuyenTien_EUR_KiemTraSoDuSauGiaoDich_PhiGiaoDich_NguoiNhanTra() {
		
		log.info("TC_14_01_Chon tai khoan nguon");
		transferRecurrent.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_14_02_Lay so du");
		transferRecurrent.scrollUpToText(driver, TittleData.SOURCE_ACCOUNT);
		String actualAvailableBalance = transferRecurrent.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS);
		
		log.info("TC_14_03_Kiem tra so du khong thay doi khi chua den han");
		verifyEquals(actualAvailableBalance, expectAvailableBalance+" "+sourceAccount.currentcy);

	}

	@Test
	public void TC_15_TrangThaiGiaoDich_ChuyenTien_NgoaiTe_DinhKy_2Thang_CoPhiGiaoDichNguoiNhanTra_XacThucBangMatKhauDangNhap() {
		
		String startDate = getForwardDate(1);
		String endDate = getForwardMonthAndForwardDay(Long.parseLong(info3.frequencyNumber), 1);

		log.info("TC_15_01: Click  nut Back");
		transferRecurrent.clickToDynamicBackIcon(driver, TittleData.TRANSFER_IN_VCB);

		log.info("TC_15_02: Click Trang thai lenh chuyen tien");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, InputText_MoneyRecurrent.TRANSFER_MONEY_STATUS_TEXT);

		log.info("TC_15_03: Chon loai giao dich");
		transferStatus.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyStatus_Data.Input.OPTION_TRANSFER[1]);
		transferStatus.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyStatus_Data.Input.OPTION_TRANSFER[1]);

		log.info("TC_15_04: Kiem tra from date hien thi");
		verifyEquals(transferStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate"), getBackwardDate(29));

		log.info("TC_15_05: Kiem tra todate hien thi");
		verifyEquals(transferStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate"), today);

		log.info("TC_15_06: Kiem tra Note hien thi");
		verifyTrue(transferStatus.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyStatus_Data.Output.NOTE));

		log.info("TC_15_07: Click Tim Kiem");
		transferStatus.clickToDynamicButton(driver, TransferMoneyStatus_Data.Text.SEARCH);

		log.info("TC_15_08: Kiem tra ngay tao giao dich hien thi");
		String reportTime1 = transferStatus.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
		verifyEquals(convertDateTimeIgnoreHHmmss(reportTime1), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_15_09: Kiem tra nguoi nhan");
		verifyEquals(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvContent"), receivedName);

		log.info("TC_15_10: Kiem tra trang thai giao dich");
		verifyEquals(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvStatus"), TransferMoneyStatus_Data.Output.WAITING_STATUS);

		log.info("TC_15_11: Kiem tra so tien giao dich");
		verifyEquals(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "1", "com.VCB:id/tvMoney"), info3.money + " EUR");

		log.info("TC_15_12: click vao giao dich");
		transferStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_15_13: Kiem tra thoi gian tao giao dich hien thi");
		String reportTime2 = transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.CREAT_DATE);
		verifyEquals(reportTime2, reportTime1);

		log.info("TC_15_14: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.NAME_RECEIPTER), receivedName);

		log.info("TC_15_15: Kiem tra so tien thi");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.AMOUNT), info3.money + " EUR");

		log.info("TC_15_16: Kiem tra tan suat");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.FREQUENCY_TEXT), info3.frequencyNumber + " " + info3.frequencyCategory + TransferMoneyStatus_Data.Text.TRANSFER_PER_TIMES_TEXT);

		log.info("TC_15_17: Kiem ngay bat dau");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.START_DATE_TEXT), startDate);

		log.info("TC_15_18: Kiem ngay ket thuc");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.END_DATE_TEXT), endDate);

		log.info("TC_15_19: Click  nut Back");
		transferStatus.clickToDynamicBackIcon(driver, TransferMoneyStatus_Data.Text.DETAIL_TRANSFER);

	}

	@Test
	public void TC_16_HuyGiaoDich_ChuyenTien_NgoaiTe_DinhKy_2Thang_CoPhiGiaoDichNguoiNhanTra_XacThucBangMatKhauDangNhap() {
		
		String startDate = getForwardDate(1);
		String endDate = getForwardMonthAndForwardDay(Long.parseLong(info3.frequencyNumber), 1);

		log.info("TC_16_01: Click Huy Lenh");
		transferStatus.clickToDynamicIconInOrderStatus(driver, "0", "com.VCB:id/tvHuyLenh");

		log.info("TC_16_02: Click Dong Y");
		transferStatus.clickToDynamicButton(driver, TransferMoneyStatus_Data.Text.ACCEPT);

		log.info("TC_16_03: Kiem Tra Success hiển thị");
		verifyTrue(transferStatus.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyStatus_Data.Text.SUCCESS));

		log.info("TC_16_04: Click nut Dong");
		transferStatus.clickToDynamicButton(driver, TransferMoneyStatus_Data.Text.CLOSE);

		log.info("TC_16_05: Kiem tra trang thai Da Huy hien thi");
		verifyEquals(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvStatus"), TransferMoneyStatus_Data.Output.CANCEL_STATUS);

		log.info("TC_16_06: click vao giao dich");
		transferStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_16_07: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.CREAT_DATE).contains(convertTransferTimeToReportDateTime(transferTime)));

		log.info("TC_16_08: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.NAME_RECEIPTER), receivedName);

		log.info("TC_16_09: Kiem tra so tien thi");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.AMOUNT), addCommasToLong(info3.money) + " EUR");

		log.info("TC_16_10: Kiem tra tan suat");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.FREQUENCY_TEXT), info3.frequencyNumber + " " + info3.frequencyCategory + TransferMoneyStatus_Data.Text.TRANSFER_PER_TIMES_TEXT);

		log.info("TC_16_11: Kiem ngay bat dau");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.START_DATE_TEXT), startDate);

		log.info("TC_16_12: Kiem ngay ket thuc");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.END_DATE_TEXT), endDate);

		log.info("TC_16_13: Kiem tra trang thai  Da Huy hien thi");
		verifyTrue(transferStatus.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyStatus_Data.Text.UNSUCCESS_MESSAGE));

		log.info("TC_16_14: Click  nut Back");
		transferStatus.clickToDynamicBackIcon(driver, TransferMoneyStatus_Data.Text.DETAIL_TRANSFER);

		log.info("TC_16_15: Click  nut Back");
		transferStatus.clickToDynamicBackIcon(driver, TransferMoneyStatus_Data.Text.TRANSFER_MONEY_STATUS_TEXT);
	}

	@Test
	public void TC_17_ChuyenTien_NgoaiTe_USD_DinhKy_2Ngay_CoPhiGiaoDichNguoiChuyenTra_XacThucBangOTP() {
		
		log.info("TC_17_1_Click Chuyen tien trong ngan hang");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.TRANSFER_TYPE);

		log.info("TC_17_2_Chon phuong thuc chuyen tien");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.OPTION_TRANSFER[0]);
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.OPTION_TRANSFER[1]);

		log.info("TC_17_3_Chon tai khoan nguon");
		transferRecurrent.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);
		sourceAccount = transferRecurrent.chooseSourceAccount(driver, Constants.MONEY_CHECK_USD, Constants.USD_CURRENCY);
		expectAvailableBalance = sourceAccount.balance;

		log.info("TC_17_3_1_Nhap tai khoan nhan");
		transferRecurrent.inputToDynamicInputBox(driver, receivedAccount, TittleData.INPUT_ACCOUNT_BENEFICI);

		log.info("TC_17_4_Chon tan suat");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, InputData_MoneyRecurrent.DAY_TEXT);
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info4.frequencyCategory);
		transferRecurrent.inputFrequencyNumber(info4.frequencyNumber);

		log.info("TC_17_5_Nhap so tien");
		transferRecurrent.inputToDynamicInputBox(driver, info4.money, TittleData.AMOUNT);

		log.info("TC_17_6_Chon nguoi tra phi giao dich");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, TittleData.FEE_TRANSFER_SOURCE_ACCOUNT_PAY);
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info4.fee);

		log.info("TC_17_7_Nhap noi dung");
		transferRecurrent.inputToDynamicInputBoxByHeader(driver, info4.note, TittleData.TRANSFER_INFO, "3");

		log.info("TC_17_8_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_17_9_Kiem tra man hinh xac nhan thong tin");
		transferRecurrent.scrollUpToText(driver, TittleData.FORM_TRANSFER);
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, TittleData.FORM_TRANSFER), InputDataInVCB.OPTION_TRANSFER[1]);

		log.info("TC_17_9_1_Kiem tra tai khoan nguon");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, TittleData.SOURCE_ACCOUNT), sourceAccount.account);

		log.info("TC_17_9_2_Kiem tra tai khoan dich");
		verifyTrue(transferRecurrent.getDynamicTextInTransactionDetail(driver, TittleData.SOURCE_ACCOUNT_VND).contains(receivedAccount));

		log.info("TC_17_9_3_Kiem tra tan suat");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, InputText_MoneyRecurrent.TRANSFER_FREQUENCY_TEXT), info4.frequencyNumber + " " + info4.frequencyCategory + InputText_MoneyRecurrent.TRANSFER_PER_TIMES_TEXT);

		log.info("TC_17_9_4_Kiem tra tan suat");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, InputText_MoneyRecurrent.TRANSACTION_TIMES_TEXT), "2");

		log.info("TC_17_9_5_Kiem tra noi dung");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, TittleData.CONTENT), info4.note);

		log.info("TC_17_10_Chon phuong thuc xac thuc");
		transferRecurrent.scrollDownToText(driver, TittleData.METHOD_VALIDATE);
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.PAYMENT_OPTIONS[0]);
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info4.authenticationMethod);

		log.info("TC_17_11_Kiem tra so tien phi");
		String fee= transferRecurrent.getDynamicTextInTransactionDetail(driver, InputText_MoneyRecurrent.FEE_PER_TIMES_TEXT);
		transferFee = convertAvailableBalanceCurrentcyOrFeeToLong(fee);
		
		log.info("TC_17_12_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_17_12_1_Nhap OTP va click Tiep tuc");
		transferRecurrent.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, TittleData.CONTINUE_BTN);
		transferRecurrent.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_17_13_Kiem tra man hinh Lap lenh thanh cong");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY_IN_VCB_RECURRENT));

		log.info("TC_17_13_0_Lay thoi gian giao dich");
		transferTime = transferRecurrent.getTransferMoneyRecurrentTimeSuccess(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY_IN_VCB_RECURRENT);

		log.info("TC_17_13_1_Kiem tra ten nguoi huong thu");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, TittleData.NAME_BENEFICI), receivedName);

		log.info("TC_17_13_2_Kiem tra tai khoan dich");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, TittleData.ACCOUNT_BENEFICI), receivedAccount);

		log.info("TC_17_13_3_Kiem tra ten noi dung");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, TittleData.CONTENT), info4.note);

		log.info("TC_17_13_4_Kiem tra nut thuc hien giao dich moi");
		verifyTrue(transferRecurrent.isDynamicButtonDisplayed(driver, TittleData.NEW_TRANSFER));

		log.info("TC_17_14_Click Thuc hien giao dich moi");
		transferRecurrent.clickToDynamicButton(driver, TittleData.NEW_TRANSFER);

	}

	@Test
	public void TC_18_ChuyenTien_USD_KiemTraSoDuSauGiaoDich_PhiGiaoDich_NguoiChuyenTra() {
		
		log.info("TC_18_01_Chon tai khoan nguon");
		transferRecurrent.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_18_02_Lay so du");
		transferRecurrent.scrollUpToText(driver, TittleData.SOURCE_ACCOUNT);
		String actualAvailableBalance = transferRecurrent.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS);
		
		log.info("TC_18_03_Kiem tra so du khong thay doi khi chua den han");
		verifyEquals(actualAvailableBalance, expectAvailableBalance+" "+sourceAccount.currentcy);

	}

	@Test
	public void TC_19_TrangThaiGiaoDich_ChuyenTien_NgoaiTe_DinhKy_2Ngay_CoPhiGiaoDichNguoiNhanTra_XacThucBangOTP() {
		
		String startDate = getForwardDate(1);
		String endDate = getForwardDate(1 + Integer.parseInt(info4.frequencyNumber));

		log.info("TC_19_01: Click  nut Back");
		transferRecurrent.clickToDynamicBackIcon(driver, TittleData.TRANSFER_IN_VCB);

		log.info("TC_19_02: Click Trang thai lenh chuyen tien");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, InputText_MoneyRecurrent.TRANSFER_MONEY_STATUS_TEXT);

		log.info("TC_19_03: Chon loai giao dich");
		transferStatus.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyStatus_Data.Input.OPTION_TRANSFER[1]);
		transferStatus.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyStatus_Data.Input.OPTION_TRANSFER[1]);

		log.info("TC_19_04: Kiem tra from date hien thi");
		verifyEquals(transferStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate"), getBackwardDate(29));

		log.info("TC_19_05: Kiem tra todate hien thi");
		verifyEquals(transferStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate"), today);

		log.info("TC_19_06: Kiem tra Note hien thi");
		verifyTrue(transferStatus.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyStatus_Data.Output.NOTE));

		log.info("TC_19_07: Click Tim Kiem");
		transferStatus.clickToDynamicButton(driver, TransferMoneyStatus_Data.Text.SEARCH);

		log.info("TC_19_08: Kiem tra ngay tao giao dich hien thi");
		String reportTime1 = transferStatus.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
		verifyEquals(convertDateTimeIgnoreHHmmss(reportTime1), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_19_09: Kiem tra nguoi nhan");
		verifyEquals(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvContent"), receivedName);

		log.info("TC_19_10: Kiem tra trang thai giao dich");
		verifyEquals(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvStatus"), TransferMoneyStatus_Data.Output.WAITING_STATUS);

		log.info("TC_19_11: Kiem tra so tien giao dich");
		verifyEquals(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "1", "com.VCB:id/tvMoney"), info4.money + " USD");

		log.info("TC_19_12: click vao giao dich");
		transferStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_19_13: Kiem tra thoi gian tao giao dich hien thi");
		String reportTime2 = transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.CREAT_DATE);
		verifyEquals(reportTime2, reportTime1);

		log.info("TC_19_14: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.NAME_RECEIPTER), receivedName);

		log.info("TC_19_15: Kiem tra so tien thi");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.AMOUNT), info4.money + " USD");

		log.info("TC_19_16: Kiem tra tan suat");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.FREQUENCY_TEXT), info4.frequencyNumber + " " + info4.frequencyCategory + TransferMoneyStatus_Data.Text.TRANSFER_PER_TIMES_TEXT);

		log.info("TC_19_17: Kiem ngay bat dau");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.START_DATE_TEXT), startDate);

		log.info("TC_19_18: Kiem ngay ket thuc");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.END_DATE_TEXT), endDate);

		log.info("TC_19_19: Click  nut Back");
		transferStatus.clickToDynamicBackIcon(driver, TransferMoneyStatus_Data.Text.DETAIL_TRANSFER);

	}

	@Test
	public void TC_20_HuyGiaoDich_ChuyenTien_NgoaiTe_USD_DinhKy_2Ngay_CoPhiGiaoDichNguoiNhanTra_XacThucBangOTP() {
		
		String startDate = getForwardDate(1);
		String endDate = getForwardDate(1 + Integer.parseInt(info5.frequencyNumber));

		log.info("TC_20_01: Click Huy Lenh");
		transferStatus.clickToDynamicIconInOrderStatus(driver, "0", "com.VCB:id/tvHuyLenh");

		log.info("TC_20_02: Click Dong Y");
		transferStatus.clickToDynamicButton(driver, TransferMoneyStatus_Data.Text.ACCEPT);

		log.info("TC_20_03: Kiem Tra Success hiển thị");
		verifyTrue(transferStatus.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyStatus_Data.Text.SUCCESS));

		log.info("TC_20_04: Click nut Dong");
		transferStatus.clickToDynamicButton(driver, TransferMoneyStatus_Data.Text.CLOSE);

		log.info("TC_20_05: Kiem tra trang thai Da Huy hien thi");
		verifyEquals(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvStatus"), TransferMoneyStatus_Data.Output.CANCEL_STATUS);

		log.info("TC_20_06: click vao giao dich");
		transferStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_20_07: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.CREAT_DATE).contains(convertTransferTimeToReportDateTime(transferTime)));

		log.info("TC_20_08: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.NAME_RECEIPTER), receivedName);

		log.info("TC_20_09: Kiem tra so tien thi");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.AMOUNT), addCommasToLong(info4.money) + " USD");

		log.info("TC_20_10: Kiem tra tan suat");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.FREQUENCY_TEXT), info4.frequencyNumber + " " + info4.frequencyCategory + TransferMoneyStatus_Data.Text.TRANSFER_PER_TIMES_TEXT);

		log.info("TC_20_11: Kiem ngay bat dau");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.START_DATE_TEXT), startDate);

		log.info("TC_20_12: Kiem ngay ket thuc");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.END_DATE_TEXT), endDate);

		log.info("TC_20_13: Kiem tra trang thai  Da Huy hien thi");
		verifyTrue(transferStatus.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyStatus_Data.Text.UNSUCCESS_MESSAGE));

		log.info("TC_20_14: Click  nut Back");
		transferStatus.clickToDynamicBackIcon(driver, TransferMoneyStatus_Data.Text.DETAIL_TRANSFER);

		log.info("TC_20_15: Click  nut Back");
		transferStatus.clickToDynamicBackIcon(driver, TransferMoneyStatus_Data.Text.TRANSFER_MONEY_STATUS_TEXT);
	}

	@Test
	public void TC_21_ChuyenTien_NgoaiTe_USD_DinhKy_2Ngay_CoPhiGiaoDichNguoiNhanTra_XacThucBangOTP() {
		
		log.info("TC_21_01_Click Chuyen tien trong ngan hang");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.TRANSFER_TYPE);

		log.info("TC_21_02_Chon phuong thuc chuyen tien");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.OPTION_TRANSFER[0]);
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.OPTION_TRANSFER[1]);

		log.info("TC_21_03_Chon tai khoan nguon");
		transferRecurrent.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);
		sourceAccount = transferRecurrent.chooseSourceAccount(driver, Constants.MONEY_CHECK_USD, Constants.USD_CURRENCY);
		expectAvailableBalance = sourceAccount.balance;

		log.info("TC_21_03_1_Nhap tai khoan nhan");
		transferRecurrent.inputToDynamicInputBox(driver, receivedAccount, TittleData.INPUT_ACCOUNT_BENEFICI);

		log.info("TC_21_04_Chon tan suat");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, InputData_MoneyRecurrent.DAY_TEXT);
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info5.frequencyCategory);
		transferRecurrent.inputFrequencyNumber(info5.frequencyNumber);

		log.info("TC_21_05_Nhap so tien");
		transferRecurrent.inputToDynamicInputBox(driver, info5.money, TittleData.AMOUNT);

		log.info("TC_21_06_Chon nguoi tra phi giao dich");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, TittleData.FEE_TRANSFER_SOURCE_ACCOUNT_PAY);
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info5.fee);

		log.info("TC_21_07_Nhap noi dung");
		transferRecurrent.inputToDynamicInputBoxByHeader(driver, info5.note, TittleData.TRANSFER_INFO, "3");

		log.info("TC_21_08_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_21_09_Kiem tra man hinh xac nhan thong tin");
		transferRecurrent.scrollUpToText(driver, TittleData.FORM_TRANSFER);
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, TittleData.FORM_TRANSFER), InputDataInVCB.OPTION_TRANSFER[1]);

		log.info("TC_21_09_1_Kiem tra tai khoan nguon");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, TittleData.SOURCE_ACCOUNT), sourceAccount.account);

		log.info("TC_21_09_2_Kiem tra tai khoan dich");
		verifyTrue(transferRecurrent.getDynamicTextInTransactionDetail(driver, TittleData.SOURCE_ACCOUNT_VND).contains(receivedAccount));

		log.info("TC_21_09_3_Kiem tra tan suat");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, InputText_MoneyRecurrent.TRANSFER_FREQUENCY_TEXT), info5.frequencyNumber + " " + info5.frequencyCategory + InputText_MoneyRecurrent.TRANSFER_PER_TIMES_TEXT);

		log.info("TC_21_09_4_Kiem tra tan suat");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, InputText_MoneyRecurrent.TRANSACTION_TIMES_TEXT), "2");

		log.info("TC_21_09_5_Kiem tra noi dung");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, TittleData.CONTENT), info5.note);

		log.info("TC_21_10_Chon phuong thuc xac thuc");
		transferRecurrent.scrollDownToText(driver, TittleData.METHOD_VALIDATE);
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.PAYMENT_OPTIONS[0]);
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info5.authenticationMethod);

		log.info("TC_21_11_Kiem tra so tien phi");
		String fee= transferRecurrent.getDynamicTextInTransactionDetail(driver, InputText_MoneyRecurrent.FEE_PER_TIMES_TEXT);
		transferFee = convertAvailableBalanceCurrentcyOrFeeToLong(fee);
		
		log.info("TC_21_12_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_21_12_1_Nhap MK va click Tiep tuc");
		transferRecurrent.inputToDynamicPopupPasswordInput(driver, password, TittleData.CONTINUE_BTN);
		transferRecurrent.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_21_13_Kiem tra man hinh Lap lenh thanh cong");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY_IN_VCB_RECURRENT));
		
		log.info("TC_21_13_0_Lay thoi gian giao dich");
		transferTime = transferRecurrent.getTransferMoneyRecurrentTimeSuccess(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY_IN_VCB_RECURRENT);

		log.info("TC_21_13_1_Kiem tra ten nguoi huong thu");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, TittleData.NAME_BENEFICI), receivedName);

		log.info("TC_21_13_2_Kiem tra tai khoan dich");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, TittleData.ACCOUNT_BENEFICI), receivedAccount);

		log.info("TC_21_13_3_Kiem tra ten noi dung");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, TittleData.CONTENT), info5.note);

		log.info("TC_21_13_4_Kiem tra nut thuc hien giao dich moi");
		verifyTrue(transferRecurrent.isDynamicButtonDisplayed(driver, TittleData.NEW_TRANSFER));

		log.info("TC_21_13_Click Thuc hien giao dich moi");
		transferRecurrent.clickToDynamicButton(driver, TittleData.NEW_TRANSFER);

	}

	@Test
	public void TC_22_ChuyenTien_USD_KiemTraSoDuSauGiaoDich_PhiGiaoDich_NguoiNhanTra() {
		
		log.info("TC_22_01_Chon tai khoan nguon");
		transferRecurrent.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_22_02_Lay so du");
		transferRecurrent.scrollUpToText(driver, TittleData.SOURCE_ACCOUNT);
		String actualAvailableBalance = transferRecurrent.getDynamicTextInTransactionDetail(driver, TittleData.SURPLUS);
		
		log.info("TC_22_03_Kiem tra so du khong thay doi khi chua den han");
		verifyEquals(actualAvailableBalance, expectAvailableBalance+" "+sourceAccount.currentcy);

	}

	@Test
	public void TC_23_TrangThaiGiaoDich_ChuyenTien_NgoaiTe_DinhKy_2Ngay_CoPhiGiaoDichNguoiNhanTra_XacThucBangOTP() {
		
		String startDate = getForwardDate(1);
		String endDate = getForwardDate(1 + Integer.parseInt(info5.frequencyNumber));

		log.info("TC_23_01: Click  nut Back");
		transferRecurrent.clickToDynamicBackIcon(driver, TittleData.TRANSFER_IN_VCB);

		log.info("TC_23_02: Click Trang thai lenh chuyen tien");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, InputText_MoneyRecurrent.TRANSFER_MONEY_STATUS_TEXT);

		log.info("TC_23_03: Chon loai giao dich");
		transferStatus.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyStatus_Data.Input.OPTION_TRANSFER[1]);
		transferStatus.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyStatus_Data.Input.OPTION_TRANSFER[1]);

		log.info("TC_23_04: Kiem tra from date hien thi");
		verifyEquals(transferStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate"), getBackwardDate(29));

		log.info("TC_23_05: Kiem tra todate hien thi");
		verifyEquals(transferStatus.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate"), today);

		log.info("TC_23_06: Kiem tra Note hien thi");
		verifyTrue(transferStatus.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyStatus_Data.Output.NOTE));

		log.info("TC_23_07: Click Tim Kiem");
		transferStatus.clickToDynamicButton(driver, TransferMoneyStatus_Data.Text.SEARCH);

		log.info("TC_23_08: Kiem tra ngay tao giao dich hien thi");
		String reportTime1 = transferStatus.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
		verifyEquals(convertDateTimeIgnoreHHmmss(reportTime1), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_23_09: Kiem tra nguoi nhan");
		verifyEquals(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvContent"), receivedName);

		log.info("TC_23_10: Kiem tra trang thai giao dich");
		verifyEquals(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvStatus"), TransferMoneyStatus_Data.Output.WAITING_STATUS);

		log.info("TC_23_11: Kiem tra so tien giao dich");
		verifyEquals(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "1", "com.VCB:id/tvMoney"), info5.money + " USD");

		log.info("TC_23_12: click vao giao dich");
		transferStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_23_13: Kiem tra thoi gian tao giao dich hien thi");
		String reportTime2 = transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.CREAT_DATE);
		verifyEquals(reportTime2, reportTime1);

		log.info("TC_23_14: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.NAME_RECEIPTER), receivedName);

		log.info("TC_23_15: Kiem tra so tien thi");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.AMOUNT), info5.money + " USD");

		log.info("TC_23_16: Kiem tra tan suat");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.FREQUENCY_TEXT), info5.frequencyNumber + " " + info5.frequencyCategory + TransferMoneyStatus_Data.Text.TRANSFER_PER_TIMES_TEXT);

		log.info("TC_23_17: Kiem ngay bat dau");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.START_DATE_TEXT), startDate);

		log.info("TC_23_18: Kiem ngay ket thuc");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.END_DATE_TEXT), endDate);

		log.info("TC_23_19: Click  nut Back");
		transferStatus.clickToDynamicBackIcon(driver, TransferMoneyStatus_Data.Text.DETAIL_TRANSFER);

	}

	@Test
	public void TC_24_HuyGiaoDich_ChuyenTien_NgoaiTe_USD_DinhKy_2Ngay_CoPhiGiaoDichNguoiNhanTra_XacThucBangOTP() {
		
		String startDate = getForwardDate(1);
		String endDate = getForwardDate(1 + Integer.parseInt(info5.frequencyNumber));

		log.info("TC_24_01: Click Huy Lenh");
		transferStatus.clickToDynamicIconInOrderStatus(driver, "0", "com.VCB:id/tvHuyLenh");

		log.info("TC_24_02: Click Dong Y");
		transferStatus.clickToDynamicButton(driver, TransferMoneyStatus_Data.Text.ACCEPT);

		log.info("TC_24_03: Kiem Tra Success hiển thị");
		verifyTrue(transferStatus.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyStatus_Data.Text.SUCCESS));

		log.info("TC_24_04: Click nut Dong");
		transferStatus.clickToDynamicButton(driver, TransferMoneyStatus_Data.Text.CLOSE);

		log.info("TC_24_05: Kiem tra trang thai Da Huy hien thi");
		verifyEquals(transferStatus.getTextInDynamicTransactionInTransferOrderStatus(driver, "0", "com.VCB:id/tvStatus"), TransferMoneyStatus_Data.Output.CANCEL_STATUS);

		log.info("TC_24_06: click vao giao dich");
		transferStatus.clickToDynamicTransactionInTransactionOrderStatus(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_24_07: Kiem tra ngay tao giao dich hien thi");
		verifyTrue(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.CREAT_DATE).contains(convertTransferTimeToReportDateTime(transferTime)));

		log.info("TC_24_08: Kiem tra ten nguoi huong hien thi");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.NAME_RECEIPTER), receivedName);

		log.info("TC_24_09: Kiem tra so tien thi");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.AMOUNT), addCommasToLong(info5.money) + " USD");

		log.info("TC_24_10: Kiem tra tan suat");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.FREQUENCY_TEXT), info5.frequencyNumber + " " + info5.frequencyCategory + TransferMoneyStatus_Data.Text.TRANSFER_PER_TIMES_TEXT);

		log.info("TC_24_11: Kiem ngay bat dau");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.START_DATE_TEXT), startDate);

		log.info("TC_24_12: Kiem ngay ket thuc");
		verifyEquals(transferStatus.getDynamicTextInTransactionDetail(driver, TransferMoneyStatus_Data.Text.END_DATE_TEXT), endDate);

		log.info("TC_24_13: Kiem tra trang thai  Da Huy hien thi");
		verifyTrue(transferStatus.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyStatus_Data.Text.UNSUCCESS_MESSAGE));

		log.info("TC_24_14: Click  nut Back");
		transferStatus.clickToDynamicBackIcon(driver, TransferMoneyStatus_Data.Text.DETAIL_TRANSFER);

		log.info("TC_24_15: Click  nut Back");
		transferStatus.clickToDynamicBackIcon(driver, TransferMoneyStatus_Data.Text.TRANSFER_MONEY_STATUS_TEXT);
	}
	
	@Test
	public void TC_25_ChuyenTien_VND_DinhKy_2Ngay_CoPhiGiaoDichNguoiNhanTra_XacThucBangOTP_LuuDanhBaThuHuong() {
		
		log.info("TC_25_1_Click Chuyen tien trong ngan hang");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.TRANSFER_TYPE);

		log.info("TC_25_2_Chon phuong thuc chuyen tien");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.OPTION_TRANSFER[0]);
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.OPTION_TRANSFER[1]);

		log.info("TC_25_3_Chon tai khoan nguon");
		transferRecurrent.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);
		sourceAccount = transferRecurrent.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);
		expectAvailableBalance = sourceAccount.balance;

		log.info("TC_25_3_1_Nhan tai khoan nhan");
		transferRecurrent.inputToDynamicInputBox(driver, receivedAccount, TittleData.INPUT_ACCOUNT_BENEFICI);

		log.info("TC_25_4_Chon tan suat");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, InputData_MoneyRecurrent.DAY_TEXT);
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info1.frequencyCategory);
		transferRecurrent.inputFrequencyNumber(info1.frequencyNumber);

		log.info("TC_25_5_Nhap so tien");
		transferRecurrent.inputToDynamicInputBox(driver, info1.money, TittleData.AMOUNT);

		log.info("TC_25_6_Chon nguoi tra phi giao dich");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, TittleData.FEE_TRANSFER_SOURCE_ACCOUNT_PAY);
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info1.fee);

		log.info("TC_25_7_Nhap noi dung");
		transferRecurrent.inputToDynamicInputBoxByHeader(driver, info1.note, TittleData.TRANSFER_INFO, "3");

		log.info("TC_25_8_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_25_9_Kiem tra man hinh xac nhan thong tin");
		transferRecurrent.scrollUpToText(driver, TittleData.FORM_TRANSFER);
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, TittleData.FORM_TRANSFER), InputDataInVCB.OPTION_TRANSFER[1]);

		log.info("TC_25_9_1_Kiem tra tai khoan nguon");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, TittleData.SOURCE_ACCOUNT), sourceAccount.account);

		log.info("TC_25_9_2_Kiem tra tai khoan dich");
		verifyTrue(transferRecurrent.getDynamicTextInTransactionDetail(driver, TittleData.SOURCE_ACCOUNT_VND).contains(receivedAccount));

		log.info("TC_25_9_3_Kiem tra tan suat");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, InputText_MoneyRecurrent.TRANSFER_FREQUENCY_TEXT), info1.frequencyNumber + " " + info1.frequencyCategory + InputText_MoneyRecurrent.TRANSFER_PER_TIMES_TEXT);

		log.info("TC_25_9_4_Kiem tra tan suat");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, InputText_MoneyRecurrent.TRANSACTION_TIMES_TEXT), "2");

		log.info("TC_25_9_5_Kiem tra noi dung");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, TittleData.CONTENT), info1.note);

		log.info("TC_25_10_Chon phuong thuc xac thuc");
		transferRecurrent.scrollDownToText(driver, TittleData.METHOD_VALIDATE);
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.PAYMENT_OPTIONS[0]);
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info1.authenticationMethod);

		log.info("TC_25_11_Kiem tra so tien phi");
		String fee= transferRecurrent.getDynamicTextInTransactionDetail(driver, InputText_MoneyRecurrent.FEE_PER_TIMES_TEXT);
		transferFee = convertAvailableBalanceCurrentcyOrFeeToLong(fee);
		
		log.info("TC_25_12_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_25_12_1_Nhap OTP va click Tiep tuc");
		transferRecurrent.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, TittleData.CONTINUE_BTN);
		transferRecurrent.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_25_13_Kiem tra man hinh Lap lenh thanh cong");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY_IN_VCB_RECURRENT));
		
		log.info("TC_25_13_0_Lay thoi gian giao dich");
		transferTime = transferRecurrent.getTransferMoneyRecurrentTimeSuccess(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY_IN_VCB_RECURRENT);

		log.info("TC_25_13_1_Kiem tra ten nguoi huong thu");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, TittleData.NAME_BENEFICI), receivedName);

		log.info("TC_25_13_2_Kiem tra tai khoan dich");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, TittleData.ACCOUNT_BENEFICI), receivedAccount);

		log.info("TC_25_13_3_Kiem tra ten noi dung");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, TittleData.CONTENT), info1.note);

		log.info("TC_25_13_4_Kiem tra nut thuc hien giao dich moi");
		verifyTrue(transferRecurrent.isDynamicButtonDisplayed(driver, TittleData.NEW_TRANSFER));
		
		if (transferRecurrent.getPageSource(driver).contains(InputText_MoneyRecurrent.SAVE_RECEIVED_ACCOUNT_TEXT)) {
			log.info("TC_25_14_01_Click button chia se");
			transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, InputText_MoneyRecurrent.SAVE_RECEIVED_ACCOUNT_TEXT);
			
			log.info("TC_25_14_02_Xac nhan hien thi dung loai dich vu");
			verifyEquals(transferRecurrent.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvType"), TittleData.TRANSFER_IN_VCB);
			
			log.info("TC_25_14_03_Xac nhan hien thi dung ten chu tai khoan");
			verifyEquals(transferRecurrent.getTextInEditTextFieldByID(driver, "com.VCB:id/edtCusName"), receivedName);
			
			log.info("TC_25_14_04_Xac nhan hien thi dung so tai khoan");
			verifyEquals(transferRecurrent.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), receivedAccount);
			
			log.info("TC_25_14_05_Click button Hoan thanh");
			transferRecurrent.clickToDynamicAcceptButton(driver, "com.VCB:id/btSave");

			log.info("TC_25_14_06_Kiem tra luu thanh cong");
			verifyEquals(transferRecurrent.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), InputText_MoneyRecurrent.SAVE_CONTACT_SUCCESS_MESSAGE);

			log.info("TC_25_14_07_Click Dong");
			transferRecurrent.clickToDynamicButton(driver, TittleData.CLOSE);
		}

		log.info("TC_25_15_Click Thuc hien giao dich moi");
		transferRecurrent.clickToDynamicButton(driver, TittleData.NEW_TRANSFER);

	}
	
	@Test
	public void TC_26_ChuyenTien_VND_DinhKy_2Ngay_CoPhiGiaoDichNguoiNhanTra_XacThucBangOTP_LayDanhBaThuHuong() {
		
		log.info("TC_26_1_Click Chuyen tien trong ngan hang");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.TRANSFER_TYPE);

		log.info("TC_26_2_Chon phuong thuc chuyen tien");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.OPTION_TRANSFER[0]);
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.OPTION_TRANSFER[1]);

		log.info("TC_26_3_Chon tai khoan nguon");
		transferRecurrent.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);
		sourceAccount = transferRecurrent.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);
		expectAvailableBalance = sourceAccount.balance;

		log.info("TC_26_3_1_Nhan tai khoan nhan");
		transferRecurrent.clickToDynamicIconByText(driver, TittleData.INPUT_ACCOUNT_BENEFICI);
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, receivedAccount);

		log.info("TC_26_4_Chon tan suat");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, InputData_MoneyRecurrent.DAY_TEXT);
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info1.frequencyCategory);
		transferRecurrent.inputFrequencyNumber(info1.frequencyNumber);

		log.info("TC_26_5_Nhap so tien");
		transferRecurrent.inputToDynamicInputBox(driver, info1.money, TittleData.AMOUNT);

		log.info("TC_26_6_Chon nguoi tra phi giao dich");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, TittleData.FEE_TRANSFER_SOURCE_ACCOUNT_PAY);
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info1.fee);

		log.info("TC_26_7_Nhap noi dung");
		transferRecurrent.inputToDynamicInputBoxByHeader(driver, info1.note, TittleData.TRANSFER_INFO, "3");

		log.info("TC_26_8_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_26_9_Kiem tra man hinh xac nhan thong tin");
		transferRecurrent.scrollUpToText(driver, TittleData.FORM_TRANSFER);
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, TittleData.FORM_TRANSFER), InputDataInVCB.OPTION_TRANSFER[1]);

		log.info("TC_26_9_1_Kiem tra tai khoan nguon");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, TittleData.SOURCE_ACCOUNT), sourceAccount.account);

		log.info("TC_26_9_2_Kiem tra tai khoan dich");
		verifyTrue(transferRecurrent.getDynamicTextInTransactionDetail(driver, TittleData.SOURCE_ACCOUNT_VND).contains(receivedAccount));

		log.info("TC_26_9_3_Kiem tra tan suat");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, InputText_MoneyRecurrent.TRANSFER_FREQUENCY_TEXT), info1.frequencyNumber + " " + info1.frequencyCategory + InputText_MoneyRecurrent.TRANSFER_PER_TIMES_TEXT);

		log.info("TC_26_9_4_Kiem tra tan suat");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, InputText_MoneyRecurrent.TRANSACTION_TIMES_TEXT), "2");

		log.info("TC_26_9_5_Kiem tra noi dung");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, TittleData.CONTENT), info1.note);

		log.info("TC_26_10_Chon phuong thuc xac thuc");
		transferRecurrent.scrollDownToText(driver, TittleData.METHOD_VALIDATE);
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.PAYMENT_OPTIONS[0]);
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info1.authenticationMethod);

		log.info("TC_26_11_Kiem tra so tien phi");
		String fee= transferRecurrent.getDynamicTextInTransactionDetail(driver, InputText_MoneyRecurrent.FEE_PER_TIMES_TEXT);
		transferFee = convertAvailableBalanceCurrentcyOrFeeToLong(fee);
		
		log.info("TC_26_12_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_26_12_1_Nhap OTP va click Tiep tuc");
		transferRecurrent.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, TittleData.CONTINUE_BTN);
		transferRecurrent.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_26_13_Kiem tra man hinh Lap lenh thanh cong");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY_IN_VCB_RECURRENT));
		
		log.info("TC_26_13_0_Lay thoi gian giao dich");
		transferTime = transferRecurrent.getTransferMoneyRecurrentTimeSuccess(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY_IN_VCB_RECURRENT);

		log.info("TC_26_13_1_Kiem tra ten nguoi huong thu");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, TittleData.NAME_BENEFICI), receivedName);

		log.info("TC_26_13_2_Kiem tra tai khoan dich");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, TittleData.ACCOUNT_BENEFICI), receivedAccount);

		log.info("TC_26_13_3_Kiem tra ten noi dung");
		verifyEquals(transferRecurrent.getDynamicTextInTransactionDetail(driver, TittleData.CONTENT), info1.note);

		log.info("TC_26_13_4_Kiem tra nut thuc hien giao dich moi");
		verifyTrue(transferRecurrent.isDynamicButtonDisplayed(driver, TittleData.NEW_TRANSFER));

		log.info("TC_26_15_Click Thuc hien giao dich moi");
		transferRecurrent.clickToDynamicButton(driver, TittleData.NEW_TRANSFER);

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();
	}

}
