package vnpay.vietcombank.transfer_money_in_vcb;

import java.io.IOException;
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
import vietcombank_test_data.HomePage_Data.Home_Text_Elements;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransferMoneyInVCB_Data;
import vietcombank_test_data.TransferMoneyInVCB_Data.InputText_MoneyRecurrent;
import vietcombank_test_data.TransferMoneyInVCB_Data.TittleData;

public class Transfer_Money_Present extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferMoneyInVcbPageObject transferInVCB;
	
	SourceAccountModel sourceAccount = new SourceAccountModel();
	SourceAccountModel receiverAccount = new SourceAccountModel();

	String[] exchangeRateUSD;
	String today = getCurrentDay() + "/" + getCurrenMonth() + "/" + getCurrentYear();
	String tommorrowDate = getForwardDate(1);

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
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
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.scrollDownToText(driver, InputText_MoneyRecurrent.TRANSFER_MONEY_STATUS_TEXT);
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);

	}

	// Set up BE han muc nho nhat 10.000vnd
	// Muc cao nhat 100.000.000vnd
	// Han Muc cao nhan trong ngay là 400.000.000vnd

	@Test
	public void TC_01_ChuyenTienHienTaiThapHonHanMucToiThieu() {

		log.info("TC_01_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, Home_Text_Elements.HOME_TRANSFER_IN_VCB);

		log.info("TC_01_Step_04:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);
		List<String> listDistanceAccount = transferInVCB.getListSourceAccount(driver, Constants.VND_CURRENCY);
		sourceAccount = transferInVCB.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		log.info("TC_01_Step_06: Nhap tai khoan nhan");
		receiverAccount.account = transferInVCB.getDistanceAccount(driver, sourceAccount.account, listDistanceAccount);
		transferInVCB.inputToDynamicInputBox(driver, receiverAccount.account, TittleData.INPUT_ACCOUNT_BENEFICI);

		log.info("TC_01_Step_08: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInFutureForOTP.LOWER_TRANSFER_AMOUNT, TittleData.AMOUNT);

		log.info("TC_01_Step_09: Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, TittleData.TRANSFER_INFO, "3");

		log.info("TC_01_Step_10: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_01_Step_11: Verify hien thi man hinh thong bao loi");
		transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.MESSEGE_ERROR_lOWER_MIN_LIMIT_NOW);

		log.info("TC_01_Step_12: Click btn Dong");
		transferInVCB.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

	}

	@Test
	public void TC_02_ChuyenTienHienTaiCaoHonHanMucToiDa() {

		log.info("TC_02_Step_01 : Click  nut Back");
		transferInVCB.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_02_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, Home_Text_Elements.HOME_TRANSFER_IN_VCB);

		log.info("TC_02_Step_04:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		log.info("TC_02_Step_05: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_02_Step_06: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, receiverAccount.account, TittleData.INPUT_ACCOUNT_BENEFICI);

		log.info("TC_02_Step_08: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInFutureForOTP.HIGHER_TRANSFER_AMOUNT, TittleData.AMOUNT);

		log.info("TC_02_Step_09: Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, TittleData.TRANSFER_INFO, "3");

		log.info("TC_02_Step_10: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_02_Step_11: Verify hien thi man hinh thong bao loi");
		transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.MESSEGE_ERROR_HIGHER_MAX_LIMIT);

		log.info("TC_02_Step_12: Click btn Dong");
		transferInVCB.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
	}

	@Test
	public void TC_03_ChuyenTienHienTaiVuotQuaHanMucTrongNgay() {

		log.info("TC_03_Step_02: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, Home_Text_Elements.HOME_TRANSFER_IN_VCB);
		int round = Integer.parseInt(TransferMoneyInVCB_Data.InputDataInFutureForOTP.TOTAL_LIMIT_AMOUNT) / Integer.parseInt(TransferMoneyInVCB_Data.InputDataInFutureForOTP.MAX_TRANFER_AMOUNT);
		for (int i = 0; i < round; i++) {

			log.info("TC_03_Step_05:Click tai khoan nguon");
			transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

			log.info("TC_03_Step_06: Chon tai khoan dich");
			transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);
			List<String> listDistanceAccount = transferInVCB.getListSourceAccount(driver, Constants.VND_CURRENCY);
			sourceAccount = transferInVCB.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

			log.info("TC_03_Step_07: Nhap tai khoan nhan");
			receiverAccount.account = transferInVCB.getDistanceAccount(driver, sourceAccount.account, listDistanceAccount);
			transferInVCB.inputToDynamicInputBox(driver, receiverAccount.account, TittleData.INPUT_ACCOUNT_BENEFICI);

			log.info("TC_03_Step_09: Nhap so tien chuyen");
			transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInFutureForOTP.MAX_TRANFER_AMOUNT, TittleData.AMOUNT);

			log.info("TC_03_Step_10: Nhap noi dung");
			transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, TittleData.TRANSFER_INFO, "3");

			log.info("TC_03_Step_11: Click tiep tuc");
			transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

			log.info("TC_03_Step_12: Click tiep tuc");
			transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.INFO_VALIDATION);

			log.info("TC_03_Step_13: Chon Phuong thuc nhap");
			transferInVCB.clickToDynamicDropDown(driver, TittleData.METHOD_VALIDATE);

			log.info("TC_03_Step_14: Chon SMS OTP");
			transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TittleData.SMS_OTP);

			log.info("TC_03_Step_15: Click Tiep tuc");
			transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

			log.info("TC_03_Step_16: Nhap OTP");
			transferInVCB.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, TittleData.CONTINUE_BTN);

			log.info("TC_03_Step_17: Click tiep tuc");
			transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

			log.info("TC_03_Step_18: Kiem  tra giao dich thanh cong");
			verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE));

			log.info("TC_03_Step_19: Click thuc hien giao dich moi");
			transferInVCB.clickToDynamicButton(driver, TittleData.NEW_TRANSFER);

		}

		log.info("TC_03_Step_22:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		log.info("TC_03_Step_23: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_03_Step_24: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, receiverAccount.account, TittleData.INPUT_ACCOUNT_BENEFICI);

		log.info("TC_03_Step_26: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInFutureForOTP.MAX_TRANFER_AMOUNT, TittleData.AMOUNT);

		log.info("TC_03_Step_27: Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, TittleData.TRANSFER_INFO, "3");

		log.info("TC_03_Step_28: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_03_Step_29: Verify hien thi man hinh thong bao loi");
		transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.MESSEGE_ERROR_HIGHER_LIMIT_DAY_NOW);

		log.info("TC_03_Step_30: Click btn Dong");
		transferInVCB.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

	}

	// Truoc khi chay case nay can set Up Nhom dich vu vs han muc la 99.999.999 VND

	public void TC_04_ChuyenTienHienTaiVuotQuaNhomDichVu() {

		log.info("TC_04_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, Home_Text_Elements.HOME_TRANSFER_IN_VCB);

		log.info("TC_04_Step_04:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		log.info("TC_04_Step_05: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_04_Step_06: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, receiverAccount.account, TittleData.INPUT_ACCOUNT_BENEFICI);

		log.info("TC_04_Step_08: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInFutureForOTP.MAX_TRANFER_AMOUNT, TittleData.AMOUNT);

		log.info("TC_04_Step_09: Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, TittleData.TRANSFER_INFO, "3");

		log.info("TC_04_Step_10: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_04_Step_11: Verify hien thi man hinh thong bao loi");
		transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.MESSEGE_ERROR_HIGHER_LIMIT_GROUP_SERVICES_NOW);

		log.info("TC_04_Step_12: Click btn Dong");
		transferInVCB.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

	}

	// Truoc khi chay case nay can set Up Goi dich vu vs han muc la 99.999.999 VND
	@Test
	public void TC_05_ChuyenTienTuongLaiVuotQuaGoiDichVu() {

		log.info("TC_05_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, Home_Text_Elements.HOME_TRANSFER_IN_VCB);

		log.info("TC_05_Step_04:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		log.info("TC_05_Step_05: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_05_Step_06: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, receiverAccount.account, TittleData.INPUT_ACCOUNT_BENEFICI);

		log.info("TC_05_Step_08: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInFutureForOTP.MAX_TRANFER_AMOUNT, TittleData.AMOUNT);

		log.info("TC_05_Step_09: Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, TittleData.TRANSFER_INFO, "3");

		log.info("TC_05_Step_10: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_05_Step_11: Verify hien thi man hinh thong bao loi");
		transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.MESSEGE_ERROR_HIGHER_LIMIT_PACKAGE_SERVICES_NOW);

		log.info("TC_05_Step_12: Click btn Dong");
		transferInVCB.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

	}

	@AfterClass(alwaysRun = true)

	public void afterClass() {
		closeApp();
		service.stop();
	}

}
