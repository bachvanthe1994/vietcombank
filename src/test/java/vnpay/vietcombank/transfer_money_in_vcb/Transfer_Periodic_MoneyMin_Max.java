package vnpay.vietcombank.transfer_money_in_vcb;

import java.io.IOException;
import java.util.List;

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
import vietcombank_test_data.HomePage_Data.Home_Text_Elements;
import vietcombank_test_data.TransferMoneyInVCB_Data;
import vietcombank_test_data.TransferMoneyInVCB_Data.InputDataInVCB;
import vietcombank_test_data.TransferMoneyInVCB_Data.InputData_MoneyRecurrent;
import vietcombank_test_data.TransferMoneyInVCB_Data.InputText_MoneyRecurrent;
import vietcombank_test_data.TransferMoneyInVCB_Data.TittleData;

public class Transfer_Periodic_MoneyMin_Max extends Base {
	AppiumDriver<MobileElement> driver;
	private WebDriver driverWeb;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferMoneyInVcbPageObject transferInVCB;
	private WebBackendSetupPageObject setupBE;

	String[] exchangeRateUSD;
	String today = getCurrentDay() + "/" + getCurrenMonth() + "/" + getCurrentYear();
	String tommorrowDate = getForwardDate(2);
	int timesLimitOfDay = 10;
	private String lowerMin,higherMax,otpNo ;

	SourceAccountModel sourceAccount = new SourceAccountModel();
	SourceAccountModel receiverAccount = new SourceAccountModel();
	ServiceLimitInfo inputInfo = new ServiceLimitInfo("1000", "10000", "1000000", "1500000");
	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp","username","passWeb" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt,String username,String passWeb) throws IOException, InterruptedException {
		startServer();
		
		log.info("Before class: Mo backend ");
		driverWeb = openMultiBrowser(Constants.BE_BROWSER_CHROME, Constants.BE_BROWSER_VERSION, Constants.BE_URL);
		
		setupBE = WebPageFactoryManager.getWebBackendSetupPageObject(driver);

		setupBE.Login_Web_Backend(driverWeb,username, passWeb);

		setupBE.setupAssignServicesLimit(driverWeb,InputText_MoneyRecurrent.BE_TRANSFER_RECURRENT_TEXT,inputInfo);

		
		log.info("Before class: Mo app ");
		if (deviceType.contains("android")) {
			driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		} else if (deviceType.contains("ios")) {
			driver = openIOSApp(deviceName, udid, url);
		}
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
		otpNo = opt;
		log.info("Before class_Step_10: Scroll den trang thai lenh chuyen tien");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.scrollDownToText(driver, InputText_MoneyRecurrent.TRANSFER_MONEY_STATUS_TEXT);
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		lowerMin = (Integer.parseInt(inputInfo.minTran)-1)+"";
		higherMax = (Integer.parseInt(inputInfo.maxTran)+1)+"";

	}

	@Test
	public void TC_01_ChuyenTienDinhKyThapHonHanMucToiThieu() {

		log.info("TC_01_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, Home_Text_Elements.HOME_TRANSFER_IN_VCB);

		log.info("TC_01_02_Chon phuong thuc chuyen tien");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.OPTION_TRANSFER[0]);
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.OPTION_TRANSFER[1]);

		log.info("TC_01_Step_04:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);
		List<String> listDistanceAccount = transferInVCB.getListSourceAccount(driver, Constants.VND_CURRENCY);
		sourceAccount = transferInVCB.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		log.info("TC_02_Step_10: Nhap tai khoan nhan");
		receiverAccount.account = transferInVCB.getDistanceAccount(driver, sourceAccount.account, listDistanceAccount);
		transferInVCB.inputToDynamicInputBox(driver, receiverAccount.account, TittleData.INPUT_ACCOUNT_BENEFICI);

		log.info("TC_01_05_Chon tan suat");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, InputData_MoneyRecurrent.DAY_TEXT);
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, InputData_MoneyRecurrent.DAY_TEXT);
		transferInVCB.inputFrequencyNumber("1");

		log.info("TC_00_05_Chon tan suat");
		transferInVCB.inputToDynamicInputBox(driver, lowerMin, TittleData.AMOUNT);

		log.info("TC_01_Step_10: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_02_Step_15: Verify hien thi man hinh thong bao loi");
		verifyEquals(transferInVCB.getTextDynamicFollowImage(driver, "com.VCB:id/ivTitle"), TransferMoneyInVCB_Data.Output.MESSEGE_ERROR_lOWER_MIN_LIMIT+addCommasToLong(inputInfo.minTran)+TransferMoneyInVCB_Data.Output.LIMIT_TRANSACTION_MESSAGE);
		
		log.info("TC_02_Step_16: Click btn Dong");
		transferInVCB.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
		transferInVCB.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

	}

	@Test
	public void TC_02_ChuyenTienDinhKyCaoHonHanMucToiDa() {

		log.info("TC_02_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, Home_Text_Elements.HOME_TRANSFER_IN_VCB);

		log.info("TC_02_Step_02: Chon chuyen tien ngay gia tri hien tai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.OPTION_TRANSFER[0]);
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.OPTION_TRANSFER[1]);

		log.info("TC_02_Step_04:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		log.info("TC_02_Step_05: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_02_Step_10: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, receiverAccount.account, TittleData.INPUT_ACCOUNT_BENEFICI);

		log.info("TC_00_05_Chon tan suat");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, InputData_MoneyRecurrent.DAY_TEXT);
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, InputData_MoneyRecurrent.DAY_TEXT);
		transferInVCB.inputFrequencyNumber("1");

		log.info("TC_00_05_Chon tan suat");
		transferInVCB.inputToDynamicInputBox(driver, higherMax, TittleData.AMOUNT);

		log.info("TC_01_Step_10: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_02_Step_15: Verify hien thi man hinh thong bao loi");
		verifyEquals(transferInVCB.getTextDynamicFollowImage(driver, "com.VCB:id/ivTitle"), TransferMoneyInVCB_Data.Output.MESSEGE_ERROR_HIGHER_MAX_LIMIT+addCommasToLong(inputInfo.maxTran)+TransferMoneyInVCB_Data.Output.LIMIT_TRANSACTION_MESSAGE);

		log.info("TC_02_Step_16: Click btn Dong");
		transferInVCB.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
		transferInVCB.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
	}

	// Case check hạn mức ngày chỉ check được tại thời điểm hạch toán
//	@Test
	public void TC_03_ChuyenTienTuongLaiVuotQuaHanMucTrongNgay() {

		log.info("TC_03_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, Home_Text_Elements.HOME_TRANSFER_IN_VCB);
		
		int round = Integer.parseInt(inputInfo.totalLimit) / Integer.parseInt(inputInfo.maxTran);
		for (int i = 0; i < round; i++) {
			log.info("TC_03_Step_02: Chon chuyen tien ngay gia tri hien tai");
			transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.OPTION_TRANSFER[0]);
			transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.OPTION_TRANSFER[1]);

			log.info("TC_03_Step_03:Click tai khoan nguon");
			transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

			log.info("TC_03_Step_04: Chon tai khoan dich");
			transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

			log.info("TC_03_Step_05: Nhap tai khoan nhan");
			transferInVCB.inputToDynamicInputBox(driver, receiverAccount.account, TittleData.INPUT_ACCOUNT_BENEFICI);

			log.info("TC_03_Step_06: Chon tan suat");
			transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, InputData_MoneyRecurrent.DAY_TEXT);
			transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, InputData_MoneyRecurrent.DAY_TEXT);
			transferInVCB.inputFrequencyNumber("1");

			log.info("TC_03_Step_07: Nhap so tien");
			transferInVCB.inputToDynamicInputBox(driver, inputInfo.maxTran, TittleData.AMOUNT);

			log.info("TC_03_Step_08: Click tiep tuc");
			transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

			log.info("TC_03_Step_09: verify xac thuc thong tin");
			transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.INFO_VALIDATION);

			log.info("TC_03_Step_10: Chon Phuong thuc nhap");
			transferInVCB.clickToDynamicDropDown(driver, TittleData.METHOD_VALIDATE);

			log.info("TC_03_Step_11: Chon SMS OTP");
			transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, TittleData.SMS_OTP);

			log.info("TC_03_Step_12: Click Tiep tuc");
			transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

			log.info("TC_03_Step_13: Nhap OTP");
			transferInVCB.inputToDynamicOtp(driver, otpNo, TittleData.CONTINUE_BTN);

			log.info("TC_03_Step_14: Click tiep tuc");
			transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

			log.info("TC_03_Step_15: Click thuc hien giao dich moi");
			transferInVCB.clickToDynamicButton(driver, TittleData.NEW_TRANSFER);

		}
		log.info("TC_03_Step_16: Chon chuyen tien ngay gia tri hien tai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.OPTION_TRANSFER[0]);
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, InputDataInVCB.OPTION_TRANSFER[1]);

		log.info("TC_03_Step_17: Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, TittleData.SOURCE_ACCOUNT);

		log.info("TC_03_Step_18: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_03_Step_19: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, receiverAccount.account, TittleData.INPUT_ACCOUNT_BENEFICI);

		log.info("TC_03_Step_20: Chon tan suat");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, InputData_MoneyRecurrent.DAY_TEXT);
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, InputData_MoneyRecurrent.DAY_TEXT);
		transferInVCB.inputFrequencyNumber("1");

		log.info("TC_03_Step_21: Chon tan suat");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInFutureForOTP.HIGHER_TRANSFER_AMOUNT, TittleData.AMOUNT);

		log.info("TC_03_Step_22: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, TittleData.CONTINUE_BTN);

		log.info("TC_03_Step_23: Verify hien thi man hinh thong bao loi");
		verifyEquals(transferInVCB.getTextDynamicFollowImage(driver, "com.VCB:id/ivTitle"), TransferMoneyInVCB_Data.Output.MESSEGE_ERROR_HIGHER_MAX_LIMIT+addCommasToLong(inputInfo.totalLimit)+TransferMoneyInVCB_Data.Output.LIMIT_DAY_MESSAGE);
		
		log.info("TC_03_Step_24: Click btn Dong");
		transferInVCB.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
		transferInVCB.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		setupBE.resetAssignServicesLimit(driverWeb,InputText_MoneyRecurrent.BE_TRANSFER_RECURRENT_TEXT);
		service.stop();
	}

}
