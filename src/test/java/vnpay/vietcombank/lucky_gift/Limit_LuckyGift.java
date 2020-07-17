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
import vietcombank_test_data.TransferMoneyInVCB_Data.TittleData;

public class Limit_LuckyGift extends Base {
	AppiumDriver<MobileElement> driver;
	WebDriver driverWeb;
	private LogInPageObject login;
	private LuckyGiftPageObject luckyGift;
	private WebBackendSetupPageObject loginWeb;
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	ServiceLimitInfo inputInfo = new ServiceLimitInfo("1000", "10000", "400000000", "400000010");
	String account, password, otp  ;
	SourceAccountModel sourceAccount = new SourceAccountModel();

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp", "username", "passWeb" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt, String username, String passWeb) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo backend ");
		driverWeb = openMultiBrowser(Constants.BE_BROWSER_CHROME, Constants.BE_BROWSER_VERSION, Constants.BE_URL);
		loginWeb = WebPageFactoryManager.getWebBackendSetupPageObject(driverWeb);
		loginWeb.Login_Web_Backend(driverWeb, username, passWeb);

		log.info("Before class: setup limit ngay");
		loginWeb.resetAssignServicesLimit(driverWeb, backendTitle.SERVICE, backendTitle.PACKAGE_CODE);
		loginWeb.Reset_Setup_Assign_Services_Type_Limit(driverWeb, backendTitle.PACKAGE_CODE, "Chuyển khoản" );
		loginWeb.Reset_Package_Total_Limit(driverWeb, backendTitle.PACKAGE_CODE, "Method Otp");
		loginWeb.setupAssignServicesLimit_All(driverWeb, backendTitle.SERVICE, inputInfo, backendTitle.PACKAGE_CODE);

		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
		password = pass;
		otp = opt ;
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
		luckyGift.hideKeyBoard(driver);
		luckyGift.scrollIDownOneTime(driver);

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
		luckyGift.inputIntoEditTextByID(driver,Integer.parseInt( inputInfo.maxTran +1) +"", "com.VCB:id/amount");

		log.info("-TC_02_Step_02: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, TitleLuckyGift.CONTINUE);

		log.info("-TC_02_Step_03: Verify hien thi man hinh thong bao loi");
		luckyGift.isDynamicMessageAndLabelTextDisplayed(driver, "Giao dịch không thành công. Số tiền giao dịch lớn hơn hạn mức "+addCommasToLong(inputInfo.maxTran)  +" VND/1 lần, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline của Vietcombank để được trợ giúp.");

		log.info("TC_02_Step_04: Click btn Dong");
		luckyGift.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

	}

	@Test
	public void TC_03_ChuyenTienQuaCaoHonHanMucToiDaTrongNgay() throws GeneralSecurityException, IOException {
		log.info("TC_02_Step_01: Nhap so tien chuyen");
		luckyGift.inputIntoEditTextByID(driver, inputInfo.minTran, "com.VCB:id/amount");
		
		log.info("-TC_02_Step_02: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, TitleLuckyGift.CONTINUE);
		
		log.info("TC_01_Step_Chon phuong thuc xac thuc");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, TitleLuckyGift.PASSWORD);
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, TitleLuckyGift.OTP);

		log.info("TC_01_Step_15: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, TitleLuckyGift.NEXT);

		log.info("TC_01_Step_16: điền mât khẩu");
		luckyGift.inputToDynamicOtp(driver, otp, TitleLuckyGift.NEXT);

		log.info("TC_01_Step_17: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, TitleLuckyGift.NEXT);

		log.info("TC_01_Step_22: Click giao dịch mới");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, TitleLuckyGift.NEW_TRANSFER);
		
		log.info("TC_01_Step_2: chọn tài khoản nguồn");
		luckyGift.clickToDynamicDropDown(driver, TitleLuckyGift.SOURCE_ACCOUNT);
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("-TC_01_Step_3: Thêm người nhận");
		luckyGift.clickToDynamicImageViewByID(driver, "com.VCB:id/ivAdd");

		log.info("TC_01_Step_5: chọn hình thức nhận");
		luckyGift.clickToTextID(driver, "com.VCB:id/tvHinhThucNhan");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, TitleLuckyGift.TITLE_PHONE_NUMBER);

		log.info("TC_01_Step_6: nhập số điện thoại");
		luckyGift.inputToDynamicInputBox(driver, getDataInCell(5), TitleLuckyGift.TITLE_PHONE_NUMBER);

		log.info("TC_01_Step_7: Nhap so tien chuyen");
		luckyGift.inputToDynamicInputBox(driver, inputInfo.minTran, TitleLuckyGift.TITLE_AMOUNT_MONEY);

		log.info("-TC_01_Step_8: Chon loi chuc");
		luckyGift.clickToDynamicWishes(driver, TitleLuckyGift.TITLE_WISHES);
		luckyGift.inputIntoEditTextByID(driver, LuckyGift_Data.LuckyGift.WISHES_OPTION, "com.VCB:id/content");
		luckyGift.clickToDynamicImageViewByID(driver, "com.VCB:id/ivReceiver");
		luckyGift.hideKeyBoard(driver);
		luckyGift.scrollIDownOneTime(driver);

		log.info("TC_01_Step_9: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, TitleLuckyGift.CONTINUE);
		
		log.info("TC_01_Step_Chon phuong thuc xac thuc");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, TitleLuckyGift.PASSWORD);
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, TitleLuckyGift.PASSWORD);

		log.info("TC_01_Step_15: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, TitleLuckyGift.NEXT);

		log.info("TC_01_Step_16: điền mât khẩu");
		luckyGift.inputToDynamicInputBox(driver, password, TitleLuckyGift.INPUT_PASSWORD);

		log.info("TC_01_Step_17: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, TitleLuckyGift.NEXT);

		log.info("TC_01_Step_22: Click giao dịch mới");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, TitleLuckyGift.NEW_TRANSFER);
		
		
		log.info("TC_01_Step_2: chọn tài khoản nguồn");
		luckyGift.clickToDynamicDropDown(driver, TitleLuckyGift.SOURCE_ACCOUNT);
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("-TC_01_Step_3: Thêm người nhận");
		luckyGift.clickToDynamicImageViewByID(driver, "com.VCB:id/ivAdd");

		log.info("TC_01_Step_5: chọn hình thức nhận");
		luckyGift.clickToTextID(driver, "com.VCB:id/tvHinhThucNhan");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, TitleLuckyGift.TITLE_PHONE_NUMBER);

		log.info("TC_01_Step_6: nhập số điện thoại");
		luckyGift.inputToDynamicInputBox(driver, getDataInCell(5), TitleLuckyGift.TITLE_PHONE_NUMBER);

		log.info("TC_01_Step_7: Nhap so tien chuyen");
		luckyGift.inputToDynamicInputBox(driver, inputInfo.maxTran, TitleLuckyGift.TITLE_AMOUNT_MONEY);

		log.info("-TC_01_Step_8: Chon loi chuc");
		luckyGift.clickToDynamicWishes(driver, TitleLuckyGift.TITLE_WISHES);
		luckyGift.inputIntoEditTextByID(driver, LuckyGift_Data.LuckyGift.WISHES_OPTION, "com.VCB:id/content");
		luckyGift.clickToDynamicImageViewByID(driver, "com.VCB:id/ivReceiver");
		luckyGift.hideKeyBoard(driver);
		luckyGift.scrollIDownOneTime(driver);

		log.info("TC_01_Step_9: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, TitleLuckyGift.CONTINUE);

		log.info("-TC_02_Step_03: Verify hien thi man hinh thong bao loi");
		luckyGift.isDynamicMessageAndLabelTextDisplayed(driver, "Giao dịch không thành công. Số tiền giao dịch lớn hơn hạn mức "+addCommasToLong(inputInfo.totalLimit)+" VND/1 ngày, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline của Vietcombank để được trợ giúp.");


	}

	// Set BE goi han nhom dich vu la 100 trieu
	@Test
	public void TC_04_ChuyenTienQuaCaoHonHanMucToiDaNhomDichVu() throws GeneralSecurityException, IOException {
		loginWeb.resetAssignServicesLimit(driverWeb, backendTitle.SERVICE, backendTitle.PACKAGE_CODE);
		
		luckyGift.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
		
		loginWeb.Setup_Assign_Services_Type_Limit(driverWeb, backendTitle.PACKAGE_CODE, backendTitle.TRANSFER, Limit_Money_Gift.SERVICE_LIMIT_TRANSFER);
		log.info("TC_04_Step_1: Nhap so tien chuyen");
		luckyGift.inputIntoEditTextByID(driver, Limit_Money_Gift.SERVICE_LIMIT, "com.VCB:id/amount");

		log.info("TC_04_Step_2: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, TitleLuckyGift.CONTINUE);

		log.info("TC_04_Step_3: Verify hien thi man hinh thong bao loi");
		luckyGift.isDynamicMessageAndLabelTextDisplayed(driver, LuckyGift_Data.Messege_Limit.MESSEGE_ERROR_HIGHER_MAX_GROUP);

		

	}

	// Set BE goi han goi dich vu la 100 trieu
	@Test
	public void TC_05_ChuyenTienQuaCaoHonHanMucToiDaGoiDichVu() throws GeneralSecurityException, IOException {
		loginWeb.Reset_Setup_Assign_Services_Type_Limit(driverWeb, backendTitle.PACKAGE_CODE, "Chuyển khoản" );
		log.info("TC_04_Step_4: Click btn Dong");
		luckyGift.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
		
		loginWeb.Setup_Add_Method_Package_Total_Limit(driverWeb, backendTitle.PACKAGE_CODE, TittleData.TITTLE_METHOD, Limit_Money_Gift.PACKAGE_LIMIT_TRANSFER);
		
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
		loginWeb.Reset_Package_Total_Limit(driverWeb, backendTitle.PACKAGE_CODE, "Method Otp");
		driverWeb.quit();
		closeApp();
		service.stop();
	}

}
