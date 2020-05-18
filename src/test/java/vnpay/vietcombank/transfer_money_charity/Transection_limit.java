package vnpay.vietcombank.transfer_money_charity;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import model.TransferCharity;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.TransferMoneyCharityPageObject;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.TransferMoneyCharity_Data;

public class Transection_limit extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferMoneyCharityPageObject transferMoneyCharity;

	TransferCharity info = new TransferCharity(Account_Data.Valid_Account.ACCOUNT2, TransferMoneyCharity_Data.ORGANIZATION, "1000", "Do Minh Duc", "So 18 ngo 3 Thai Ha", "Ho ngheo", "Mật khẩu đăng nhập");
	TransferCharity info1 = new TransferCharity(Account_Data.Valid_Account.ACCOUNT2, TransferMoneyCharity_Data.ORGANIZATION, "300000", "Do Minh Duc", "So 18 ngo 3 Thai Ha", "Ho ngheo", "Mật khẩu đăng nhập");
	TransferCharity info2 = new TransferCharity(Account_Data.Valid_Account.ACCOUNT2, TransferMoneyCharity_Data.ORGANIZATION, "100000", "Do Minh Duc", "So 18 ngo 3 Thai Ha", "Ho ngheo", "SMS OTP");
	TransferCharity info3 = new TransferCharity(Account_Data.Valid_Account.ACCOUNT2, TransferMoneyCharity_Data.ORGANIZATION, "400000000", "Do Minh Duc", "So 18 ngo 3 Thai Ha", "Ho ngheo", "SMS OTP");

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

//	@Test
	public void TC_01_SoTienGiaoDichNhoHonHanMucToiThieu() {
		homePage = PageFactoryManager.getHomePageObject(driver);
		transferMoneyCharity = PageFactoryManager.getTransferMoneyCharityPageObject(driver);
		log.info("TC_01_1_Click Chuyen tien tu thien");
		transferMoneyCharity.scrollDownToText(driver, "Trạng thái lệnh chuyển tiền");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền từ thiện");

		log.info("TC_01_2_Chon tai khoan nguon");
		transferMoneyCharity.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);

		log.info("TC_01_3_Chon Quy/ To chuc tu thien");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, "Quỹ/ Tổ chức từ thiện");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info.organization);

		log.info("TC_01_4_Nhap so tien ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.money, "Số tiền ủng hộ");

		log.info("TC_01_5_Nhap ten nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.name, "Tên người ủng hộ");

		log.info("TC_01_6_Nhap dia chi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.address, "Địa chỉ người ủng hộ");

		log.info("TC_01_7_Hoan canh nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.status, "Hoàn cảnh ủng hộ");

		log.info("TC_01_8_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_verify message khi so tien chuyen nho hon han muc toi thieu ");
		verifyTrue(transferMoneyCharity.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyCharity_Data.MONEY_INPUT_NOT_ENOUGH_PER_A_TRANSACTION_MESSAGE));

	}
	
//	@Test
	public void TC_02_SoTienGiaoDichVuotQuaHanMucToiDa() {
		homePage = PageFactoryManager.getHomePageObject(driver);
		transferMoneyCharity = PageFactoryManager.getTransferMoneyCharityPageObject(driver);
		log.info("TC_02_1_Click Chuyen tien tu thien");
		transferMoneyCharity.scrollDownToText(driver, "Trạng thái lệnh chuyển tiền");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền từ thiện");

		log.info("TC_02_2_Chon tai khoan nguon");
		transferMoneyCharity.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);

		log.info("TC_02_3_Chon Quy/ To chuc tu thien");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, "Quỹ/ Tổ chức từ thiện");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info.organization);

		log.info("TC_02_4_Nhap so tien ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info1.money, "Số tiền ủng hộ");

		log.info("TC_02_5_Nhap ten nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.name, "Tên người ủng hộ");

		log.info("TC_02_6_Nhap dia chi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.address, "Địa chỉ người ủng hộ");

		log.info("TC_02_7_Hoan canh nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.status, "Hoàn cảnh ủng hộ");

		log.info("TC_02_8_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_02_Step_verify message khi so tien chuyen nho hon han muc toi thieu ");
		verifyTrue(transferMoneyCharity.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyCharity_Data.MONEY_INPUT_OVER_MESSAGE));

	}
	
//	@Test
	public void TC_03_SoTienGiaoDichVuotQuaHanMucToiDa1Ngay() {
		homePage = PageFactoryManager.getHomePageObject(driver);
		transferMoneyCharity = PageFactoryManager.getTransferMoneyCharityPageObject(driver);
		log.info("TC_03_1_Click Chuyen tien tu thien");
		transferMoneyCharity.scrollDownToText(driver, "Trạng thái lệnh chuyển tiền");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền từ thiện");

		log.info("TC_03_2_Chon tai khoan nguon");
		transferMoneyCharity.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);

		log.info("TC_03_3_Chon Quy/ To chuc tu thien");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, "Quỹ/ Tổ chức từ thiện");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info.organization);

		log.info("TC_03_4_Nhap so tien ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info1.money, "Số tiền ủng hộ");

		log.info("TC_03_5_Nhap ten nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.name, "Tên người ủng hộ");

		log.info("TC_03_6_Nhap dia chi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.address, "Địa chỉ người ủng hộ");

		log.info("TC_03_7_Hoan canh nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.status, "Hoàn cảnh ủng hộ");

		log.info("TC_03_8_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_03_Step_verify message khi so tien chuyen nho hon han muc toi thieu ");
		verifyTrue(transferMoneyCharity.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyCharity_Data.CONFIRM_MAX_TRANSECTION_LIMIT_DAY));

	}
	
//	@Test
	public void TC_04_SoTienGiaoDichVuotQuaHanMucToiDa1NgayCuaNhomDichVu() {
		homePage = PageFactoryManager.getHomePageObject(driver);
		transferMoneyCharity = PageFactoryManager.getTransferMoneyCharityPageObject(driver);
		log.info("TC_04_1_Click Chuyen tien tu thien");
		transferMoneyCharity.scrollDownToText(driver, "Trạng thái lệnh chuyển tiền");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền từ thiện");

		log.info("TC_04_2_Chon tai khoan nguon");
		transferMoneyCharity.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);

		log.info("TC_04_3_Chon Quy/ To chuc tu thien");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, "Quỹ/ Tổ chức từ thiện");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info.organization);

		log.info("TC_04_4_Nhap so tien ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info3.money, "Số tiền ủng hộ");

		log.info("TC_04_5_Nhap ten nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.name, "Tên người ủng hộ");

		log.info("TC_04_6_Nhap dia chi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.address, "Địa chỉ người ủng hộ");

		log.info("TC_04_7_Hoan canh nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.status, "Hoàn cảnh ủng hộ");

		log.info("TC_04_8_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_04_Step_verify message khi so tien chuyen nho hon han muc toi thieu ");
		verifyTrue(transferMoneyCharity.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyCharity_Data.CONFIRM_MAX_TRANSECTION_LIMIT_DAY_GROUP));

	}

	@Test
	public void TC_05_SoTienGiaoDichVuotQuaHanMucToiDa1NgayCuaGoiDichVu() {
		homePage = PageFactoryManager.getHomePageObject(driver);
		transferMoneyCharity = PageFactoryManager.getTransferMoneyCharityPageObject(driver);
		log.info("TC_05_1_Click Chuyen tien tu thien");
		transferMoneyCharity.scrollDownToText(driver, "Trạng thái lệnh chuyển tiền");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền từ thiện");

		log.info("TC_05_2_Chon tai khoan nguon");
		transferMoneyCharity.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);

		log.info("TC_05_3_Chon Quy/ To chuc tu thien");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, "Quỹ/ Tổ chức từ thiện");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info.organization);

		log.info("TC_05_4_Nhap so tien ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info3.money, "Số tiền ủng hộ");

		log.info("TC_05_5_Nhap ten nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.name, "Tên người ủng hộ");

		log.info("TC_05_6_Nhap dia chi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.address, "Địa chỉ người ủng hộ");

		log.info("TC_05_7_Hoan canh nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.status, "Hoàn cảnh ủng hộ");

		log.info("TC_05_8_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_05_Step_verify message khi so tien chuyen nho hon han muc toi thieu ");
		verifyTrue(transferMoneyCharity.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyCharity_Data.CONFIRM_MAX_TRANSECTION_LIMIT_DAY_PACKAGE));

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();
	}

}
