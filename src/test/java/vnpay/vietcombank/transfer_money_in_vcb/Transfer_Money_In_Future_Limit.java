package vnpay.vietcombank.transfer_money_in_vcb;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.Constants;
import commons.PageFactoryManager;
import commons.WebPageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import model.ServiceLimitInfo;
import model.SourceAccountModel;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.TransferMoneyInVcbPageObject;
import pageObjects.WebBackendSetupPageObject;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.HomePage_Data.Home_Text_Elements;
import vietcombank_test_data.LuckyGift_Data.Limit_Money_Gift;
import vietcombank_test_data.LuckyGift_Data.backendTitle;
import vietcombank_test_data.TransferIdentity_Data.textCheckElement;
import vietcombank_test_data.TransferMoneyInVCB_Data;
import vietcombank_test_data.TransferMoneyInVCB_Data.InputDataInFutureForOTP;
import vietcombank_test_data.TransferMoneyInVCB_Data.InputText_MoneyRecurrent;

public class Transfer_Money_In_Future_Limit extends Base {
	AppiumDriver<MobileElement> driver;
	WebDriver driverWeb;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferMoneyInVcbPageObject transferInVCB;
	private WebBackendSetupPageObject loginWeb;
	String account;
	SourceAccountModel sourceAccount = new SourceAccountModel();

	String[] exchangeRateUSD;
	String today = getCurrentDay() + "/" + getCurrenMonth() + "/" + getCurrentYear();
	String tommorrowDate = getForwardDate(1);
	ServiceLimitInfo inputInfo = new ServiceLimitInfo("1000", "100000", "1000000", "1001000");

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp", "username", "passWeb" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt, String username, String passWeb) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo backend ");
		driverWeb = openMultiBrowser(Constants.BE_BROWSER_CHROME, Constants.BE_BROWSER_VERSION, Constants.BE_URL);
		loginWeb = WebPageFactoryManager.getWebBackendSetupPageObject(driverWeb);
		loginWeb.Login_Web_Backend(driverWeb, username, passWeb);
		loginWeb.resetAssignServicesLimit(driverWeb, InputText_MoneyRecurrent.TRANSFER_FUTURE, backendTitle.PACKAGE_CODE);
		loginWeb.Reset_Setup_Assign_Services_Type_Limit(driverWeb, backendTitle.PACKAGE_CODE, "Chuyển khoản" );
		loginWeb.Reset_Package_Total_Limit(driverWeb, backendTitle.PACKAGE_CODE, "Method Otp");
		
		log.info("Before class: setup limit ngay");
		loginWeb.setupAssignServicesLimit(driverWeb, InputText_MoneyRecurrent.TRANSFER_FUTURE, inputInfo, backendTitle.PACKAGE_CODE);
		
		
		
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
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);

	}

	// Set up BE han muc nho nhat 10.000vnd
	// Muc cao nhat 1.000.000vnd
	// Han Muc cao nhan trong ngay là 1.500.000vnd
	@Test
	public void TC_01_ChuyenTienTuongLaiThapHonHanMucToiThieu() {
		log.info("TC_01_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, Home_Text_Elements.HOME_TRANSFER_IN_VCB);

		log.info("TC_01_Step_02: Chon chuyen tien ngay gia tri hien tai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Home_Text_Elements.TRANSFER_NOW);

		log.info("TC_01_Step_03: Chon chuyen tien ngay gia tri hien tai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Home_Text_Elements.TRANSFER_FUTURE);

		log.info("TC_01_Step_04:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, Home_Text_Elements.SOURCE_ACCOUNT);
		sourceAccount = transferInVCB.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, "VND");
		account = sourceAccount.account;

		log.info("TC_01_Step_06: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, Account_Data.Valid_Account.ACCOUNT1, Home_Text_Elements.INPUT_ACCOUNT_BENEFICI);

		log.info("TC_01_Step_07: Kiem tra Ngay hieu luc mac dinh");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, tommorrowDate));

		log.info("TC_01_Step_08: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, Integer.parseInt( inputInfo.minTran) - 1 +"", Home_Text_Elements.AMOUNT);

		log.info("TC_01_Step_09: Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, Home_Text_Elements.TRANSACTION_INFO, "3");

		log.info("TC_01_Step_10: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, Home_Text_Elements.CONTINUE_BTN);

		log.info("TC_01_Step_11: Verify hien thi man hinh thong bao loi");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.MESSEGE_ERROR_lOWER_MIN_LIMIT));

		log.info("TC_01_Step_12: Click btn Dong");
		transferInVCB.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

	}

	@Test
	public void TC_02_ChuyenTienTuongLaiCaoHonHanMucToiDa() {

		log.info("TC_02_Step_08: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, Integer.parseInt( inputInfo.maxTran) + 1 +"", Home_Text_Elements.TRANSACTION_INFO, "1");

		log.info("TC_02_Step_10: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, Home_Text_Elements.CONTINUE_BTN);

		log.info("TC_02_Step_11: Verify hien thi man hinh thong bao loi");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.MESSEGE_ERROR_HIGHER_MAX_LIMIT));


	}

	 @Parameters({ "pass","otp" })
	@Test
	public void TC_03_ChuyenTienTuongLaiVuotQuaHanMucTrongNgay_Otp(String pass, String otp) {
		 loginWeb.setupAssignServicesLimit_Total_Day(driverWeb, "Chuyển khoản nhanh qua số tài khoản", inputInfo,Constants.BE_CODE_PACKAGE);
			log.info("TC_02_Step_12: Click btn Dong");
			transferInVCB.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

		log.info("TC_03_Step_02: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, Home_Text_Elements.HOME_TRANSFER_IN_VCB);
		
			log.info("TC_03_Step_03: Chon chuyen tien ngay gia tri hien tai");
			transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Home_Text_Elements.TRANSFER_NOW);

			log.info("TC_03_Step_04: Chon chuyen tien ngay gia tri hien tai");
			transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Home_Text_Elements.TRANSFER_FUTURE);

			log.info("TC_03_Step_05:Click tai khoan nguon");
			transferInVCB.clickToDynamicDropDown(driver, Home_Text_Elements.SOURCE_ACCOUNT);
			sourceAccount = transferInVCB.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, "VND");
			account = sourceAccount.account;

			log.info("TC_03_Step_06: Nhap tai khoan nhan");
			transferInVCB.inputToDynamicInputBox(driver, Account_Data.Valid_Account.ACCOUNT2, Home_Text_Elements.INPUT_ACCOUNT_BENEFICI);

			log.info("TC_03_Step_07: Kiem tra Ngay hieu luc mac dinh");
			verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, tommorrowDate));

			log.info("TC_03_Step_08: Nhap so tien chuyen");
			transferInVCB.inputToDynamicInputBox(driver,inputInfo.minTran , Home_Text_Elements.AMOUNT);

			log.info("TC_03_Step_09: Nhap noi dung");
			transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, Home_Text_Elements.TRANSACTION_INFO, "3");

			log.info("TC_03_Step_10: Click tiep tuc");
			transferInVCB.clickToDynamicButton(driver, Home_Text_Elements.CONTINUE_BTN);
			
			log.info("TC_03_Step_10: Chon PTXT");
			transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.PAYMENT_OPTIONS[0]);
			transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.PAYMENT_OPTIONS[1]);
			
			log.info("TC_03_Step_11: Click tiep tuc");
			transferInVCB.clickToDynamicButton(driver, Home_Text_Elements.CONTINUE_BTN);
			
			log.info("TC_03_Step_12: Click tiep tuc");
			transferInVCB.inputToDynamicOtp(driver, otp, Home_Text_Elements.CONTINUE_BTN);
			
			log.info("TC_03_Step_13: Click tiep tuc");
			transferInVCB.clickToDynamicButton(driver, Home_Text_Elements.CONTINUE_BTN);

			log.info("TC_03_Step_14: Kiem  tra giao dich thanh cong");
			verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.SUCESSFULL_CREATED_ORDER));

			log.info("TC_03_Step_15: Click thuc hien giao dich moi");
			transferInVCB.clickToDynamicButton(driver, "Thực hiện giao dịch mới");
			
			
			log.info("TC_03_Step_16: Chon chuyen tien ngay gia tri hien tai");
			transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Home_Text_Elements.TRANSFER_NOW);

			log.info("TC_03_Step_17: Chon chuyen tien ngay gia tri hien tai");
			transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Home_Text_Elements.TRANSFER_FUTURE);

			log.info("TC_03_Step_18:Click tai khoan nguon");
			transferInVCB.clickToDynamicDropDown(driver, Home_Text_Elements.SOURCE_ACCOUNT);
			transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

			log.info("TC_03_Step_19: Nhap tai khoan nhan");
			transferInVCB.inputToDynamicInputBox(driver, Account_Data.Valid_Account.ACCOUNT2, Home_Text_Elements.INPUT_ACCOUNT_BENEFICI);

			log.info("TC_03_Step_20: Nhap so tien chuyen");
			transferInVCB.inputToDynamicInputBox(driver, inputInfo.minTran , Home_Text_Elements.AMOUNT);

			log.info("TC_03_Step_21: Nhap noi dung");
			transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, Home_Text_Elements.TRANSACTION_INFO, "3");

			log.info("TC_03_Step_22: Click tiep tuc");
			transferInVCB.clickToDynamicButton(driver, Home_Text_Elements.CONTINUE_BTN);
			
			log.info("TC_03_Step_10: Chon PTXT");
			transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.PAYMENT_OPTIONS[0]);
			transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyInVCB_Data.InputDataInVCB.PAYMENT_OPTIONS[0]);
			
			log.info("TC_03_Step_11: Click tiep tuc");
			transferInVCB.clickToDynamicButton(driver, Home_Text_Elements.CONTINUE_BTN);
			
			log.info("TC_03_Step_12: Click tiep tuc");
			transferInVCB.inputToDynamicInputBox(driver, pass, textCheckElement.INPUT_PASSWORD);
			
			log.info("TC_03_Step_13: Click tiep tuc");
			transferInVCB.clickToDynamicButton(driver, Home_Text_Elements.CONTINUE_BTN);

			log.info("TC_03_Step_14: Kiem  tra giao dich thanh cong");
			verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.SUCESSFULL_CREATED_ORDER));

			log.info("TC_03_Step_15: Click thuc hien giao dich moi");
			transferInVCB.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

	

		log.info("TC_03_Step_16: Chon chuyen tien ngay gia tri hien tai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Home_Text_Elements.TRANSFER_NOW);

		log.info("TC_03_Step_17: Chon chuyen tien ngay gia tri hien tai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Home_Text_Elements.TRANSFER_FUTURE);

		log.info("TC_03_Step_18:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, Home_Text_Elements.SOURCE_ACCOUNT);
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_03_Step_19: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, Account_Data.Valid_Account.ACCOUNT2, Home_Text_Elements.INPUT_ACCOUNT_BENEFICI);

		log.info("TC_03_Step_20: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, inputInfo.maxTran , Home_Text_Elements.AMOUNT);

		log.info("TC_03_Step_21: Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, Home_Text_Elements.TRANSACTION_INFO, "3");

		log.info("TC_03_Step_22: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, Home_Text_Elements.CONTINUE_BTN);

		log.info("TC_03_Step_23: Verify hien thi man hinh thong bao loi");
		transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.MESSEGE_ERROR_HIGHER_LIMIT_DAY);
		
	}

	@Test
	public void TC_04_ChuyenTienTuongLaiVuotQuaNhomDichVu() {
		loginWeb.resetAssignServicesLimit(driverWeb, InputText_MoneyRecurrent.TRANSFER_FUTURE,Constants.BE_CODE_PACKAGE);
		
		log.info("TC_03_Step_24: Click btn Dong");
		transferInVCB.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
		
		loginWeb.Setup_Assign_Services_Type_Limit(driverWeb, backendTitle.PACKAGE_CODE, InputText_MoneyRecurrent.BE_TRANSFER_TEXT, InputDataInFutureForOTP.TOTAL_SERVICE);
		log.info("TC_04_Step_01: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, Integer.parseInt(InputDataInFutureForOTP.TOTAL_SERVICE) + 1 +"", Home_Text_Elements.TRANSACTION_INFO, "1");

		log.info("TC_04_Step_02: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, Home_Text_Elements.CONTINUE_BTN);

		log.info("TC_04_Step_03: Verify hien thi man hinh thong bao loi");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.MESSEGE_ERROR_HIGHER_LIMIT_GROUP_SERVICES));

	}

	@Test
	public void TC_05_ChuyenTienTuongLaiVuotQuaGoiDichVu() {
		loginWeb.Reset_Setup_Assign_Services_Type_Limit(driverWeb, backendTitle.PACKAGE_CODE, "Chuyển khoản" );
		
		log.info("TC_03_Step_24: Click btn Dong");
		transferInVCB.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
		loginWeb.Setup_Add_Method_Package_Total_Limit(driverWeb, backendTitle.PACKAGE_CODE, "Method Otp", InputDataInFutureForOTP.PACKAGE_LIMIT_TRANSFER);
		
		log.info("TC_05_Step_01: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBoxByHeader(driver,Integer.parseInt( InputDataInFutureForOTP.PACKAGE_LIMIT_TRANSFER )+ 1 +"", Home_Text_Elements.TRANSACTION_INFO, "1");

		log.info("TC_05_Step_01: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, Home_Text_Elements.CONTINUE_BTN);

		log.info("TC_05_Step_02: Verify hien thi man hinh thong bao loi");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.MESSEGE_ERROR_HIGHER_LIMIT_PACKAGE_SERVICES));

		log.info("TC_05_Step_03: Click btn Dong");
		transferInVCB.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

	}

	@AfterClass(alwaysRun = true)

	public void afterClass() {
//		closeApp();
//		service.stop();
	}

}
