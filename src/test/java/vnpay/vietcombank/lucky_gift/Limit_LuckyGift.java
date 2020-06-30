package vnpay.vietcombank.lucky_gift;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;

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
import model.ServiceTypeLimitInfo;
import model.SourceAccountModel;
import pageObjects.LogInPageObject;
import pageObjects.LuckyGiftPageObject;
import pageObjects.WebBackendSetupPageObject;
import vietcombank_test_data.LuckyGift_Data;
import vietcombank_test_data.LuckyGift_Data.Limit_Money_Gift;
import vietcombank_test_data.LuckyGift_Data.TitleLuckyGift;
import vietcombank_test_data.LuckyGift_Data.backendTitle;

public class Limit_LuckyGift extends Base {
	AppiumDriver<MobileElement> driver;
	WebDriver driverWeb;
	private LogInPageObject login;
	private LuckyGiftPageObject luckyGift;
	private WebBackendSetupPageObject loginWeb;
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	ServiceLimitInfo inputInfo = new ServiceLimitInfo("1000", "10000", "100000000", "400000000");
	String account;
	SourceAccountModel sourceAccount = new SourceAccountModel();
	ServiceTypeLimitInfo inputInfoType = new ServiceTypeLimitInfo("PIN", "Việt Nam Đồng", "40000000");

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp", "username", "passWeb" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt, String username, String passWeb) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo backend ");
		driverWeb = openMultiBrowser(Constants.BE_BROWSER_CHROME, Constants.BE_BROWSER_VERSION, Constants.BE_URL);
		loginWeb = WebPageFactoryManager.getWebBackendSetupPageObject(driverWeb);
		loginWeb.Login_Web_Backend(driverWeb, username, passWeb);
		
		log.info("Before class: setup limit ngay");
		loginWeb.setupAssignServicesLimit(driverWeb, backendTitle.SERVICE, inputInfo, backendTitle.PACKAGE_CODE);
		loginWeb.Setup_Assign_Services_Type_Limit(driverWeb, backendTitle.PACKAGE_CODE, backendTitle.TRANSFER, Limit_Money_Gift.SERVICE_LIMIT_TRANSFER);
		loginWeb.Setup_Add_Method_Package_Total_Limit(driverWeb, backendTitle.PACKAGE_CODE, "Method Otp", Limit_Money_Gift.PACKAGE_LIMIT_TRANSFER);
		
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
		luckyGift = PageFactoryManager.getLuckyGiftPageObject(driver);

	}

	@Test
	public void TC_01_ChuyenTienQuaThapHonHanMucToiThieu() throws GeneralSecurityException, IOException {
		log.info("TC_01_Step_1: Chọn quà tặng may mắn");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.TitleLuckyGift.TITLE);

		log.info("TC_01_Step_2: chọn tài khoản nguồn");
		luckyGift.clickToDynamicDropDown(driver, TitleLuckyGift.SOURCE_ACCOUNT);
		sourceAccount = luckyGift.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, "VND");
		account = sourceAccount.account;

		log.info("-TC_01_Step_3: Thêm người nhận");
		luckyGift.clickToDynamicImageViewByID(driver, "com.VCB:id/ivAdd");
		luckyGift.clickToDynamicButton(driver, TitleLuckyGift.CONTINUE);

		log.info("TC_01_Step_5: chọn hình thức nhận");
		luckyGift.clickToTextID(driver, "com.VCB:id/tvHinhThucNhan");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, TitleLuckyGift.TITLE_PHONE_NUMBER);

		log.info("TC_01_Step_6: nhập số điện thoại");
		luckyGift.inputToDynamicInputBox(driver, getDataInCell(5), TitleLuckyGift.TITLE_PHONE_NUMBER);

		log.info("TC_01_Step_7: Nhap so tien chuyen");
		luckyGift.inputToDynamicInputBox(driver, LuckyGift_Data.Limit_Money_Gift.LOWER_MIN_MONEY_A_TRANSACTION, TitleLuckyGift.TITLE_AMOUNT_MONEY);

		log.info("-TC_01_Step_8: Chon loi chuc");
		luckyGift.clickToDynamicWishes(driver, TitleLuckyGift.TITLE_WISHES);
		luckyGift.inputIntoEditTextByID(driver, LuckyGift_Data.LuckyGift.WISHES_OPTION, "com.VCB:id/content");
		luckyGift.clickToDynamicImageViewByID(driver, "com.VCB:id/ivReceiver");

		log.info("TC_01_Step_9: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, TitleLuckyGift.CONTINUE);

		log.info("TC_01_Step_10: Verify hien thi man hinh thong bao loi");
		luckyGift.isDynamicMessageAndLabelTextDisplayed(driver, LuckyGift_Data.Messege_Limit.MESSEGE_ERROR_LOWER_MIN_TRAN);

		log.info("TC_01_Step_11: Click btn Dong");
		luckyGift.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

	}

	@Test
	public void TC_02_ChuyenTienQuaCaoHonHanMucToiDa() {

		log.info("TC_02_Step_01: Nhap so tien chuyen");
		luckyGift.inputIntoEditTextByID(driver, LuckyGift_Data.Limit_Money_Gift.HIGHER_MAX_MONEY_A_TRANSACTION, "com.VCB:id/amount");

		log.info("-TC_02_Step_02: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, TitleLuckyGift.CONTINUE);

		log.info("-TC_02_Step_03: Verify hien thi man hinh thong bao loi");
		luckyGift.isDynamicMessageAndLabelTextDisplayed(driver, LuckyGift_Data.Messege_Limit.MESSEGE_ERROR_HIGHER_MAX_TRAN);

		log.info("TC_02_Step_04: Click btn Dong");
		luckyGift.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

	}
	
	@Test
	public void TC_03_ChuyenTienQuaCaoHonHanMucToiDaTrongNgay() {
		log.info("TC_02_Step_01: Nhap so tien chuyen");
		luckyGift.inputIntoEditTextByID(driver, LuckyGift_Data.Limit_Money_Gift.MAX_MONEY_A_TRANSACTION, "com.VCB:id/amount");

		log.info("-TC_02_Step_02: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, TitleLuckyGift.CONTINUE);

		log.info("-TC_02_Step_03: Verify hien thi man hinh thong bao loi");
		luckyGift.isDynamicMessageAndLabelTextDisplayed(driver, LuckyGift_Data.Messege_Limit.MESSEGE_ERROR_HIGHER_MAX_DAY);

		log.info("TC_02_Step_04: Click btn Dong");
		luckyGift.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

	}
	
	// Set BE goi han nhom dich vu la 100 trieu
	@Test
	public void TC_04_ChuyenTienQuaCaoHonHanMucToiDaNhomDichVu() throws GeneralSecurityException, IOException {
		log.info("TC_04_Step_1: Nhap so tien chuyen");
		luckyGift.inputIntoEditTextByID(driver, Limit_Money_Gift.SERVICE_LIMIT, "com.VCB:id/amount");
		
		log.info("TC_04_Step_2: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, TitleLuckyGift.CONTINUE);

		log.info("TC_04_Step_3: Verify hien thi man hinh thong bao loi");
		luckyGift.isDynamicMessageAndLabelTextDisplayed(driver, LuckyGift_Data.Messege_Limit.MESSEGE_ERROR_HIGHER_MAX_GROUP);

		log.info("TC_04_Step_4: Click btn Dong");
		luckyGift.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

	}

	// Set BE goi han goi dich vu la 100 trieu
	@Test
	public void TC_05_ChuyenTienQuaCaoHonHanMucToiDaGoiDichVu() throws GeneralSecurityException, IOException {
		log.info("-TC_05_Step_1: Nhap so tien chuyen");
		luckyGift.inputIntoEditTextByID(driver, Limit_Money_Gift.PACKAGE_LIMIT, "com.VCB:id/amount");

		log.info("TC_05_Step_2: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, TitleLuckyGift.CONTINUE);

		log.info("TC_05_Step_3: Verify hien thi man hinh thong bao loi");
		luckyGift.isDynamicMessageAndLabelTextDisplayed(driver, LuckyGift_Data.Messege_Limit.MESSEGE_ERROR_HIGHER_MAX_PACKAGE);

		log.info("TC_05_Step_4: Click btn Dong");
		luckyGift.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		loginWeb.resetAssignServicesLimit(driver, backendTitle.SERVICE, backendTitle.PACKAGE_CODE);
		closeApp();
		service.stop();
	}

}
