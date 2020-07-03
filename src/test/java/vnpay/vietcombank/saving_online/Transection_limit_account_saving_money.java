package vnpay.vietcombank.saving_online;

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
import model.SavingOnlineInfo;
import model.ServiceLimitInfo;
import model.SourceAccountModel;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.SavingOnlinePageObject;
import pageObjects.WebBackendSetupPageObject;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.SavingOnline_Data;
import vietcombank_test_data.HomePage_Data.Home_Text_Elements;
import vietcombank_test_data.TransferMoneyInVCB_Data.TittleData;

public class Transection_limit_account_saving_money extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private WebBackendSetupPageObject loginWeb;
	private SavingOnlinePageObject savingOnline;
	WebDriver driverWeb;

	long transferFee = 0;
	double transferFeeCurrentcy = 0;
	String account, balance, currentcy = "";
	SourceAccountModel sourceAccount = new SourceAccountModel();
	ServiceLimitInfo inputInfo = new ServiceLimitInfo("1000", "1000", "1000000", "1000001");

	SavingOnlineInfo info = new SavingOnlineInfo(Account_Data.Valid_Account.ACCOUNT2, "1 tháng", inputInfo.minTran, "Lãi nhập gốc");
	SavingOnlineInfo info1 = new SavingOnlineInfo(Account_Data.Valid_Account.ACCOUNT2, "3 tháng", "20000000", "Lãi nhập gốc");
	SavingOnlineInfo info2 = new SavingOnlineInfo(Account_Data.Valid_Account.ACCOUNT2, "3 tháng", "10000000", "Lãi nhập gốc");
	SavingOnlineInfo info3 = new SavingOnlineInfo(Account_Data.Valid_Account.ACCOUNT2, "3 tháng", "50000000", "Lãi nhập gốc");

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp", "username", "passWeb" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt, String username, String passWeb) throws IOException, InterruptedException {
		log.info("Before class: Mo backend ");
		startServer();
		driverWeb = openMultiBrowser(Constants.BE_BROWSER_CHROME, Constants.BE_BROWSER_VERSION, Constants.BE_URL);
		loginWeb = WebPageFactoryManager.getWebBackendSetupPageObject(driverWeb);
		loginWeb.Login_Web_Backend(driverWeb, username, passWeb);

		loginWeb.setupAssignServicesLimit(driverWeb, SavingOnline_Data.OPEN_SAVING, inputInfo, SavingOnline_Data.PACKAGE_NAME);

		log.info("Before class: Mo app ");
		if (deviceType.contains("android")) {
			driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		} else if (deviceType.contains("ios")) {
			driver = openIOSApp(deviceName, udid, url);
		}
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
		homePage = PageFactoryManager.getHomePageObject(driver);
		savingOnline = PageFactoryManager.getSavingOnlinePageObject(driver);

	}

	@Test
	public void TC_01_SoTienGiaoDichNhoHonHanMucToiThieuMotLanGiaoDich() throws InterruptedException {
		log.info("TC_01_1_Click Mo tai khoan tiet kiem");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, SavingOnline_Data.OPEN_SAVING_ACCOUNT);

		log.info("TC_01_2_Chon so tai khoan");
		savingOnline.clickToDynamicDropDown(driver, SavingOnline_Data.ACCOUNT_NUMBER);
		sourceAccount = savingOnline.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);
		account = sourceAccount.account;

		log.info("TC_01_3_Chon ky han gui");
		savingOnline.clickToDynamicDropDownListTextViewByHeader(driver, SavingOnline_Data.TRANSACTION_INFO, "1");
		savingOnline.clickToDynamicTextContains(driver, info.term);

		log.info("TC_01_4_Nhap so tien gui");
		savingOnline.inputToDynamicInputBoxByHeader(driver, Integer.parseInt(info.money) - 1 + "", SavingOnline_Data.TRANSACTION_INFO, "2");

		log.info("TC_01_5_Chon hinh thuc chuyen tien");
		savingOnline.clickToDynamicDropDownListTextViewByHeader(driver, SavingOnline_Data.TRANSACTION_INFO, "3");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, info.formOfPayment);

		log.info("TC_01_6_Chon dong y tuan thu cam ket");
		savingOnline.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");

		log.info("TC_01_7_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, SavingOnline_Data.CONTINUE_BUTTON);

		log.info("TC_01_8_ Kiem tra man hinh xac nhan thong tin");

		verifyTrue(savingOnline.getDynamicTextView(driver, "com.VCB:id/tvContent").contains("Mở tài khoản tiết kiệm không thành công."));

		savingOnline.clickToDynamicContinue(driver, "com.VCB:id/btOK");

		savingOnline.clickToDynamicBackIcon(driver, SavingOnline_Data.OPEN_SAVING_ACCOUNT);

		homePage.clickToDynamicIcon(driver, Home_Text_Elements.OPEN_SAVING_ACCOUNT);

	}

	@Test
	public void TC_02_SoTienGiaoDichVuotQuaHanMucToiDaMotLanGiaoDich() throws InterruptedException {

		log.info("TC_01_2_Chon so tai khoan");
		savingOnline.clickToDynamicDropDown(driver, SavingOnline_Data.ACCOUNT_NUMBER);
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, account);

		log.info("TC_01_3_Chon ky han gui");
		savingOnline.clickToDynamicDropDownListTextViewByHeader(driver, SavingOnline_Data.TRANSACTION_INFO, "1");
		savingOnline.clickToDynamicTextContains(driver, info.term);

		log.info("TC_01_4_Nhap so tien gui");
		savingOnline.inputToDynamicInputBoxByHeader(driver, Integer.parseInt(inputInfo.maxTran) + 1 + "", SavingOnline_Data.TRANSACTION_INFO, "2");

		log.info("TC_01_5_Chon hinh thuc chuyen tien");
		savingOnline.clickToDynamicDropDownListTextViewByHeader(driver, SavingOnline_Data.TRANSACTION_INFO, "3");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, info1.formOfPayment);

		log.info("TC_01_6_Chon dong y tuan thu cam ket");
		savingOnline.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");

		log.info("TC_01_7_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, SavingOnline_Data.CONTINUE_BUTTON);

		log.info("TC_01_8_ Kiem tra man hinh xac nhan thong tin");

		verifyTrue(savingOnline.getDynamicTextView(driver, "com.VCB:id/tvContent").contains("Mở tài khoản tiết kiệm không thành công."));

		savingOnline.clickToDynamicContinue(driver, "com.VCB:id/btOK");

		savingOnline.clickToDynamicBackIcon(driver, SavingOnline_Data.OPEN_SAVING_ACCOUNT);

	}

	@Parameters({ "pass" })
	@Test
	public void TC_03_SoTienGiaoDichVuotQuaHanMucToiDaMotNgay(String pass) throws InterruptedException {
		homePage.clickToDynamicIcon(driver, Home_Text_Elements.OPEN_SAVING_ACCOUNT);
		loginWeb.setupAssignServicesLimit_Total_Day(driverWeb, SavingOnline_Data.OPEN_SAVING, inputInfo, SavingOnline_Data.PACKAGE_NAME);

		log.info("TC_03_2_Chon so tai khoan");
		savingOnline.clickToDynamicDropDown(driver, SavingOnline_Data.ACCOUNT_NUMBER);
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, account);

		log.info("TC_03_3_Chon ky han gui");
		savingOnline.clickToDynamicDropDownListTextViewByHeader(driver, SavingOnline_Data.TRANSACTION_INFO, "1");
		savingOnline.clickToDynamicTextContains(driver, info1.term);

		log.info("TC_03_4_Nhap so tien gui");
		savingOnline.inputToDynamicInputBoxByHeader(driver, Integer.parseInt(inputInfo.minTran) + "", SavingOnline_Data.TRANSACTION_INFO, "2");

		log.info("TC_03_5_Chon hinh thuc chuyen tien");
		savingOnline.clickToDynamicDropDownListTextViewByHeader(driver, SavingOnline_Data.TRANSACTION_INFO, "3");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, info1.formOfPayment);

		log.info("TC_03_6_Chon dong y tuan thu cam ket");
		savingOnline.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");

		log.info("TC_03_7_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, SavingOnline_Data.CONTINUE_BUTTON);

		log.info("TC__03_09_Chon phuong thuc xac thuc");
		savingOnline.scrollDownToText(driver, SavingOnline_Data.ACCURACY_METHOD);

		log.info("TC_03_8_ Kiem tra man hinh xac nhan thong tin");
		savingOnline.clickToDynamicDropDown(driver, SavingOnline_Data.ACCURACY_METHOD);

		log.info("TC_03_8_ Kiem tra man hinh xac nhan thong tin");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, SavingOnline_Data.PASSWORD);

		log.info("TC_03_11_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, SavingOnline_Data.CONTINUE_BUTTON);

		log.info("TC_03_8_ Kiem tra man hinh xac nhan thong tin");
		savingOnline.inputToDynamicPopupPasswordInput(driver, pass, SavingOnline_Data.CONTINUE_BUTTON);

		log.info("TC_03_8_ Kiem tra man hinh xac nhan thong tin");
		savingOnline.clickToDynamicButton(driver, SavingOnline_Data.CONTINUE_BUTTON);

		log.info("TC_03_13_Click Thuc hien giao dich moi");
		savingOnline.clickToDynamicButton(driver, SavingOnline_Data.NEW_TRANSACTION_PERFORM);

		log.info("TC_03_2_Chon so tai khoan");
		savingOnline.clickToDynamicDropDown(driver, SavingOnline_Data.ACCOUNT_NUMBER);
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, account);

		log.info("TC_03_3_Chon ky han gui");
		savingOnline.clickToDynamicDropDownListTextViewByHeader(driver, SavingOnline_Data.TRANSACTION_INFO, "1");
		savingOnline.clickToDynamicTextContains(driver, info1.term);

		log.info("TC_03_4_Nhap so tien gui");
		savingOnline.inputToDynamicInputBoxByHeader(driver, Integer.parseInt(inputInfo.maxTran) + "", SavingOnline_Data.TRANSACTION_INFO, "2");

		log.info("TC_03_5_Chon hinh thuc chuyen tien");
		savingOnline.clickToDynamicDropDownListTextViewByHeader(driver, SavingOnline_Data.TRANSACTION_INFO, "3");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, info1.formOfPayment);

		log.info("TC_03_6_Chon dong y tuan thu cam ket");
		savingOnline.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");

		log.info("TC_03_7_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, SavingOnline_Data.CONTINUE_BUTTON);

		log.info("TC_03_8_ Kiem tra man hinh xac nhan thong tin");
		verifyTrue(savingOnline.getDynamicTextView(driver, "com.VCB:id/tvContent").contains("Mở tài khoản tiết kiệm không thành công."));

		log.info("TC_03_8_ Kiem tra man hinh xac nhan thong tin");
		savingOnline.clickToDynamicContinue(driver, "com.VCB:id/btOK");

		log.info("TC_03_8_ Kiem tra man hinh xac nhan thong tin");
		savingOnline.clickToDynamicBackIcon(driver, SavingOnline_Data.OPEN_SAVING_ACCOUNT);

		log.info("TC_03_8_ Kiem tra man hinh xac nhan thong tin");
		homePage.clickToDynamicIcon(driver, Home_Text_Elements.OPEN_SAVING_ACCOUNT);
		loginWeb.resetAssignServicesLimit(driverWeb, SavingOnline_Data.OPEN_SAVING, SavingOnline_Data.PACKAGE_NAME);

	}

	@Test
	public void TC_04_SoTienGiaoDichVuotQuaHanMucToiDaNhomGiaoDich() throws InterruptedException {

		homePage.clickToDynamicIcon(driver, Home_Text_Elements.OPEN_SAVING_ACCOUNT);
		log.info("TC_01_2_Chon so tai khoan");
		savingOnline.clickToDynamicDropDown(driver, SavingOnline_Data.ACCOUNT_NUMBER);
				
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, account);

		loginWeb.Setup_Assign_Services_Type_Limit(driverWeb, SavingOnline_Data.PACKAGE_NAME, SavingOnline_Data.SAVING_ONLINE, inputInfo.totalLimit);

		log.info("TC_01_3_Chon ky han gui");
		savingOnline.clickToDynamicDropDownListTextViewByHeader(driver, SavingOnline_Data.TRANSACTION_INFO, "1");
		savingOnline.clickToDynamicTextContains(driver, info.term);

		log.info("TC_01_4_Nhap so tien gui");
		savingOnline.inputToDynamicInputBoxByHeader(driver, Integer.parseInt(inputInfo.totalLimit) + 1 + "", SavingOnline_Data.TRANSACTION_INFO, "2");

		log.info("TC_01_5_Chon hinh thuc chuyen tien");
		savingOnline.clickToDynamicDropDownListTextViewByHeader(driver, SavingOnline_Data.TRANSACTION_INFO, "3");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, info.formOfPayment);

		log.info("TC_01_6_Chon dong y tuan thu cam ket");
		savingOnline.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");

		log.info("TC_01_7_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, SavingOnline_Data.CONTINUE_BUTTON);

		log.info("TC_01_8_ Kiem tra man hinh xac nhan thong tin");

		verifyTrue(savingOnline.getDynamicTextView(driver, "com.VCB:id/tvContent").contains("Mở tài khoản tiết kiệm không thành công."));

		savingOnline.clickToDynamicContinue(driver, "com.VCB:id/btOK");

		savingOnline.clickToDynamicBackIcon(driver, SavingOnline_Data.OPEN_SAVING_ACCOUNT);

		homePage.clickToDynamicIcon(driver, Home_Text_Elements.OPEN_SAVING_ACCOUNT);

		loginWeb.Reset_Setup_Assign_Services_Type_Limit(driverWeb, SavingOnline_Data.PACKAGE_NAME, SavingOnline_Data.SAVING_ONLINE);

	}

	@Test
	public void TC_05_SoTienGiaoDichVuotQuaHanMucToiDaGoiDichVu() throws InterruptedException {

		log.info("TC_01_1_Click Mo tai khoan tiet kiem");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, SavingOnline_Data.OPEN_SAVING_ACCOUNT);

		loginWeb.Setup_Add_Method_Package_Total_Limit(driverWeb, SavingOnline_Data.PACKAGE_NAME, SavingOnline_Data.METHOD_OTP, inputInfo.totalLimit);

		log.info("TC_01_2_Chon so tai khoan");
		savingOnline.clickToDynamicDropDown(driver, SavingOnline_Data.ACCOUNT_NUMBER);
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, account);

		log.info("TC_01_3_Chon ky han gui");
		savingOnline.clickToDynamicDropDownListTextViewByHeader(driver, SavingOnline_Data.TRANSACTION_INFO, "1");
		savingOnline.clickToDynamicTextContains(driver, info.term);

		log.info("TC_01_4_Nhap so tien gui");
		savingOnline.inputToDynamicInputBoxByHeader(driver, Integer.parseInt(inputInfo.totalLimit) + 1 + "", SavingOnline_Data.TRANSACTION_INFO, "2");

		log.info("TC_01_5_Chon hinh thuc chuyen tien");
		savingOnline.clickToDynamicDropDownListTextViewByHeader(driver, SavingOnline_Data.TRANSACTION_INFO, "3");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, info.formOfPayment);

		log.info("TC_01_6_Chon dong y tuan thu cam ket");
		savingOnline.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");

		log.info("TC_01_7_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, SavingOnline_Data.CONTINUE_BUTTON);

		log.info("TC_01_8_ Kiem tra man hinh xac nhan thong tin");

		verifyTrue(savingOnline.getDynamicTextView(driver, "com.VCB:id/tvContent").contains("Mở tài khoản tiết kiệm không thành công."));

		savingOnline.clickToDynamicContinue(driver, "com.VCB:id/btOK");

		loginWeb.Reset_Package_Total_Limit(driverWeb, SavingOnline_Data.PACKAGE_NAME, SavingOnline_Data.METHOD_OTP);

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
//		service.stop();
	}

}
