package vnpay.vietcombank.TransferIdentityCard;

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
import pageObjects.TransferIdentiryPageObject;
import vietcombank_test_data.Account_Data.Valid_Account;
import vietcombank_test_data.TransferIdentity_Data.textCheckElement;
import vietcombank_test_data.TransferIdentity_Data.textDataInputForm;
import vietcombank_test_data.TransferIdentity_Data;

public class Transection_limit extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferIdentiryPageObject trasferPage;
	
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

		homePage = PageFactoryManager.getHomePageObject(driver);
		trasferPage = PageFactoryManager.getTransferIdentiryPageObject(driver);

	}

	@Test
	public void TC_01_SoTienGiaoDichNhoHonHanMucToiThieuTren1GiaoDich() {
		log.info("TC_01_STEP_1: chon Chuyển tiền nhận bằng tiền mặt");
		homePage.clickToDynamicIcon(driver, textCheckElement.TRANSFER_MONEY);

		log.info("TC_01_STEP_2: chon tài khoản");
		trasferPage.clickToTextID(driver, "com.VCB:id/tvContent");
		sourceAccount = trasferPage.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		log.info("TC_01_Step_4: nhap ten nguoi thu huong");
		trasferPage.inputToDynamicInputBox(textDataInputForm.USER_NAME, textCheckElement.BENEFICIARY_NAME);

		log.info("TC_01_Step_5: chon giay to tuy than");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.IDENTITY);
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.IDENTITY_CARD);

		log.info("TC_01_Step_6: so CMT");
		trasferPage.inputToDynamicInputBox(driver, textDataInputForm.IDENTITY_NUMBER, textCheckElement.NUMBER);

		log.info("TC_01_Step_7: ngay cap");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.DATE);
		trasferPage.clickToDynamicButton(driver, textCheckElement.OK);

		log.info("TC_01_Step_8: noi cap");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.ISSUED);
		trasferPage.clickToDynamicTextIndex(driver, "0", textDataInputForm.ISSUED);

		log.info("TC_01_STEP_9: nhap so tien bat dau la khong");
		trasferPage.inputToDynamicInputBox(driver, textDataInputForm.MONEY_TRANSFER_VND, textCheckElement.MONEY);
		trasferPage.clickToTextID(driver, "com.VCB:id/tvTitle");

		log.info("TC_01_Step_10: noi dung");
		trasferPage.inputToDynamicInputBoxContent(driver, textDataInputForm.CONTENT_TRANSFER, "3");

		log.info("TC_01_STEP_11: chon tiep tuc");
		trasferPage.clickToDynamicButton(driver, textCheckElement.NEXT);


		log.info("TC_01_STEP_13: chon tiep tuc");
		trasferPage.clickToDynamicButton(driver, textCheckElement.NEXT);

	}

	@Test
	public void TC_02_SoTienGiaoDiVuotQuaHanMucToiDaTren1GiaoDich() {
		
	}

	@Test
	public void TC_03_SoTienGiaoVuotQuaHanMucToiDaTrenNgayCuaDichVu() {
		
	}

	@Test
	public void TC_04_SoTienGiaoVuotQuaHanMucToiDaTrenNgayCuaNhomDichVu() {
		
	}

	@Test
	public void TC_05_SoTienGiaoVuotQuaHanMucToiDaTrenNgayCuaGoiDichVu() {
		
	}

	@Test
	public void TC_06_LoiKhongDuSoDu() {
		
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();
	}

}
