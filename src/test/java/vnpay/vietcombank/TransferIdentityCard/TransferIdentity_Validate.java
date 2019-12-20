package vnpay.vietcombank.TransferIdentityCard;

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
import pageObjects.TransactionReportPageObject;
import pageObjects.TransferIdentiryPageObject;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransferIdentity_Data;

public class TransferIdentity_Validate extends Base {
    AndroidDriver<AndroidElement> driver;
    private LogInPageObject login;
    private HomePageObject homePage;
    private TransferIdentiryPageObject trasferPage;
    private TransactionReportPageObject transReport;
    private String transferTime;
    private String transactionNumber;

    @Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName" })
    @BeforeClass
    public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName) throws IOException, InterruptedException {
	startServer();
	log.info("Before class: Mo app ");
	driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);

	login = PageFactoryManager.getLoginPageObject(driver);
	homePage = PageFactoryManager.getHomePageObject(driver);
	trasferPage = PageFactoryManager.getTransferIdentiryPageObject(driver);
	transReport = PageFactoryManager.getTransactionReportPageObject(driver);

	log.info("Before class: Click Allow Button");
	login.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");

	log.info("TC_06_Step_0");
	login.inputToDynamicLogInTextBox(driver, LogIn_Data.Login_Account.PHONE, "Tiếp tục");

	log.info("TC_06_Step_0");
	login.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_05_Step_0");
	login.inputToDynamicInputBox(driver, LogIn_Data.Login_Account.NEW_PASSWORD, LogIn_Data.UI.PASSWORD_LABEL);

	log.info("TC_06_Step_0");
	login.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_06_Step_0");
	login.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

	log.info("TC_06_Step_0");
	login.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_06_Step_0");
	login.clickToDynamicButton(driver, "TỪ CHỐI");

	log.info("TC_01: chon chuyển tiền nhận bằng CMT");
	homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

    }

    @Test
    public void TC_01_TaiKhoanNguon() {

    }

    @Test
    public void TC_02_TenNguoiThuHuong() {
	log.info("TC_02_STEP_1: kiem tra hien thị mac dinh");
	String beneficiary = trasferPage.getTextDynamicTextInInputBox(driver, "Tên người hưởng");
	verifyEquals(beneficiary, TransferIdentity_Data.textCheckElement.BENEFICIARY_NAME);

	log.info("TC_02_STEP_2: nhap ten nguoi thu huong");
	trasferPage.inputToDynamicInputBoxUsedValidate(driver, TransferIdentity_Data.textDataInputForm.USER_NAME, "Tên người hưởng");

	log.info("TC_02_STEP_3: lay ten nguoi thu huong vua nhap");
	String beneficiaryInput = trasferPage.getTextDynamicTextInInputBox(driver, TransferIdentity_Data.textDataInputForm.USER_NAME);

	log.info("TC_02_STEP_4: kiem tra hien thi ten vua nhap");
	verifyEquals(beneficiaryInput, TransferIdentity_Data.textDataInputForm.USER_NAME);

	trasferPage.navigateBack(driver);
	trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");

	log.info("TC_02_STEP_5: nhap ten nguoi thu huong gom ki tu so");
	trasferPage.inputToDynamicInputBoxUsedValidate(driver, "hoangkm123", "Hoangkm");

	log.info("TC_02_STEP_6: lay ten nguoi thu huong vua nhap");
	String beneficiaryInputNumber = trasferPage.getTextDynamicTextInInputBox(driver, "Tên người hưởng");

	log.info("TC_02_STEP_7: kiem tra hien thi ten vua nhap");
	verifyEquals(beneficiaryInputNumber, "hoangkm123");
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
//	closeApp();
//	service.stop();
    }

}
