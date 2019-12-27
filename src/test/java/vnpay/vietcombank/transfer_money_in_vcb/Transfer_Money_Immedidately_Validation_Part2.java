package vnpay.vietcombank.transfer_money_in_vcb;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.TransferMoneyInVcbPageObject;
import vietcombank_test_data.LogIn_Data;

public class Transfer_Money_Immedidately_Validation_Part2 extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferMoneyInVcbPageObject transferInVCB;

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName) throws IOException, InterruptedException {
		startServer();
		log.info("Before class_Step_00: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);

		login = PageFactoryManager.getLoginPageObject(driver);

		log.info("Before class_Step_01: Click Allow Button");
		login.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");

		log.info("Before class_Step_02: Dien so dien thoai ");
		login.inputToDynamicLogInTextBox(driver, LogIn_Data.Login_Account.PHONE, "Tiếp tục");

		log.info("Before class_Step_03: Click Tiep tuc");
		login.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_05_Step_04:  Dien mat khau");
		login.inputToDynamicInputBox(driver, LogIn_Data.Login_Account.NEW_PASSWORD, LogIn_Data.UI.PASSWORD_LABEL);

		log.info("Before class_Step_05: Click tiep tuc");
		login.clickToDynamicButton(driver, "Tiếp tục");

		log.info("Before class_Step_06: Nhap OTP");
		login.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		log.info("Before class_Step_07: Click tiep tuc");
		login.clickToDynamicButton(driver, "Tiếp tục");

		log.info("Before class_Step_01: Click Allow Button");
		login.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");

		log.info("Before class_Step_10: Scroll den trang thai lenh chuyen tien");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.scrollToText(driver, "Trạng thái lệnh chuyển tiền");

	}

	@Test
	public void TC_01_KiemTraHienThiMacDinhPhiGiaoDich() {

		log.info("TC_01_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_01_Step_02: Kiem tra Chuyen Tien Ngay hien thi");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, "Phí giao dịch người chuyển trả"));

	}

	@Test
	public void TC_02_KiemTraDanhSachPhiGiaoDich() {

		log.info("TC_01_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_01_Step_03: Kiem tra Chuyen Tien Ngay hien thi");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");

		log.info("TC_01_Step_04: Kiem tra Chuyen Tien Ngay hien thi");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, "Phí giao dịch"));

		log.info("TC_01_Step_05: Kiem tra Chuyen Tien Ngay hien thi");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, "Đóng"));

		log.info("TC_01_Step_06: Kiem tra Chuyen Tien Ngay hien thi");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, "Người chuyển trả"));

		log.info("TC_01_Step_07: Kiem tra Chuyen Tien Ngay hien thi");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, "Người nhận trả"));

		log.info("TC_01_Step_08: Kiem tra Chuyen Tien Ngay hien thi");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Đóng");

		log.info("TC_01_Step_09: Kiem tra Chuyen Tien Ngay hien thi");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");

	}

	@Test
	public void TC_03_KiemTraHienThiMacDinhPhiGiaoDich() {

	}

	@AfterClass(alwaysRun = true)

	public void afterClass() {
		closeApp();
		service.stop();
	}

}
