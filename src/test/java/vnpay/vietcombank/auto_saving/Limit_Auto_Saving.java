package vnpay.vietcombank.auto_saving;

import java.io.IOException;

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
import pageObjects.SavingOnlinePageObject;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.Auto_Saving_Data;
import vietcombank_test_data.SavingOnline_Data;


public class Limit_Auto_Saving extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject home;

	private SavingOnlinePageObject savingOnline;
	SourceAccountModel sourceAccount = new SourceAccountModel();

	

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

	}

	@Parameters({ "otp" })
	@Test
	public void TC_01_MoTaiKhoanTietKiem_TaiKhoanNguon_VND(String otp) {

		log.info("TC_01_01_Click Mo tai khoan tiet kiem");
		home.clickToDynamicButtonLinkOrLinkText(driver, SavingOnline_Data.OPEN_SAVING_ACCOUNT);

		log.info("TC_01_02_Chon so tai khoan");
		savingOnline.clickToDynamicDropDown(driver, SavingOnline_Data.ACCOUNT_NUMBER);
		sourceAccount = savingOnline.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		log.info("TC_01_03_Chon ky han gui");
		savingOnline.clickToDynamicDropDownListTextViewByHeader(driver, SavingOnline_Data.TRANSACTION_INFO, "1");
		savingOnline.clickToDynamicTextContains(driver, SavingOnline_Data.THREE_MONTH);

		log.info("TC_01_04_Nhap so tien gui");
		savingOnline.inputToDynamicInputBoxByHeader(driver, SavingOnline_Data.MONEY, SavingOnline_Data.TRANSACTION_INFO, "2");

		log.info("TC_01_05_Chon hinh thuc chuyen tien");
		savingOnline.clickToDynamicDropDownListTextViewByHeader(driver, SavingOnline_Data.TRANSACTION_INFO, "3");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, SavingOnline_Data.PAY_INTEREST_METHOD_01);

		log.info("TC_01_06_Chon dong y tuan thu cam ket");
		savingOnline.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");

		log.info("TC_01_07_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, SavingOnline_Data.CONTINUE_BUTTON);

//		savingMoney = convertAvailableBalanceCurrentcyOrFeeToLong(savingOnline.getDynamicTextInTransactionDetail(driver, SavingOnline_Data.MONEY_SAVING));

		log.info("TC_01_08_Chon phuong thuc xac thuc");
		savingOnline.scrollDownToText(driver, SavingOnline_Data.ACCURACY_METHOD);
		savingOnline.clickToDynamicDropDown(driver, SavingOnline_Data.ACCURACY_METHOD);
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, SavingOnline_Data.SMS_OTP);

		log.info("TC_01_09_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, SavingOnline_Data.CONTINUE_BUTTON);

		savingOnline.inputToDynamicOtp(driver, otp, SavingOnline_Data.CONTINUE_BUTTON);

		savingOnline.clickToDynamicButton(driver, SavingOnline_Data.CONTINUE_BUTTON);

		log.info("TC_01_10_Lay ma giao dich");
//		savingAccount = savingOnline.getDynamicTextInTransactionDetail(driver, SavingOnline_Data.SAVING_NUMBER);

		log.info("TC_01_11_Ve man hinh chinh");
		savingOnline.clickToDynamicButton(driver, SavingOnline_Data.NEW_TRANSACTION_PERFORM);
		savingOnline.clickToDynamicBackIcon(driver, SavingOnline_Data.OPEN_SAVING_ACCOUNT);
	}
	@AfterClass(alwaysRun = true)
	public void afterClass() {
		service.stop();
	}

}
