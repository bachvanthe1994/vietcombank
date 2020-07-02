package vnpay.vietcombank.water_bills;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
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
import pageObjects.TransactionReportPageObject;
import pageObjects.WaterBillPageObject;
import pageObjects.WebBackendSetupPageObject;
import vietcombank_test_data.HomePage_Data;
import vietcombank_test_data.TransactionReport_Data.ReportTitle;
import vietcombank_test_data.TransferMoneyInVCB_Data.TittleData;
import vietcombank_test_data.Water_Bills_Data;
import vietcombank_test_data.LandLinePhoneCharge_Data.Text_Data;
import vietcombank_test_data.Water_Bills_Data.Tittle_Text;

public class Limit_Water_Bills extends Base {
	AppiumDriver<MobileElement> driver;
	WebDriver driverWeb;
	private LogInPageObject login;
	private HomePageObject home;
	private WaterBillPageObject waterBill;
	private WebBackendSetupPageObject webBackend;
	private String sourceAccountMoney, customerID, moneyBill, transactionDate, transactionID, waterDaNang;
	SourceAccountModel sourceAccount = new SourceAccountModel();
	long amount = 0;
	
	List<String> listCusstomerID = new ArrayList<String>();


	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp", "username", "passWeb" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt, String username, String passWeb) throws IOException, InterruptedException, GeneralSecurityException {
		startServer();
		driverWeb = openMultiBrowser(Constants.BE_BROWSER_CHROME, Constants.BE_BROWSER_VERSION, Constants.BE_URL);
		webBackend = WebPageFactoryManager.getWebBackendSetupPageObject(driverWeb);
		webBackend.Login_Web_Backend(driverWeb, username, passWeb);
		
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		home = PageFactoryManager.getHomePageObject(driver);
		login.Global_login(phone, pass, opt);
		listCusstomerID = Arrays.asList(getDataInCell(30).split(";"));
		waterDaNang = getDataInCell(37);
	}

	@Test
	public void TC_01_ThanhToanTienNuocNhoHonHanMucGiaoDichMotLanGiaoDich() throws InterruptedException {
		log.info("TC_01_Step_01: Hoa don tien dien");
		home.clickToDynamicButtonLinkOrLinkText(driver, HomePage_Data.Home_Text_Elements.PAYMENT_WATER_BILL);
		waterBill = PageFactoryManager.getWaterBillPageObject(driver);

		log.info("TC_01_Step_02: Chon tai khoan nguon");
		waterBill.clickToTextID(driver, "com.VCB:id/number_account");

		sourceAccount = waterBill.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		sourceAccountMoney = waterBill.getDynamicTextByLabel(driver, Tittle_Text.AVAILIBLE_BALANCES);

		log.info("TC_01_Step_03: Chon nha cung cap");
		waterBill.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/wrap_tv");
		waterBill.clickToDynamicButtonLinkOrLinkText(driver, waterDaNang);

		log.info("TC_01_Step_04: Nhap ma khach hang va an tiep tuc");
		customerID = waterBill.inputCustomerId(listCusstomerID);
		
		amount = convertAvailableBalanceCurrentcyOrFeeToLong(waterBill.getDynamicTextInTransactionDetail(driver, Text_Data.AMOUNT_PAY));
		log.info("TC_01_05_Kiem tra man hinh xac nhan thong tin");

		log.info("TC_01_Quay ve man hinh khoi tao");
		waterBill.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

		ServiceLimitInfo inputInfoMin = new ServiceLimitInfo("1000", (amount + 20) + "", (amount + 100) + "", "10000000");

		webBackend.getInfoServiceLimit(driverWeb, "Thanh toán hóa đơn trả sau", inputInfoMin, "TESTBUG");

		log.info("TC_01_Click tiep tuc");
		waterBill.clickToDynamicContinue(driver, "com.VCB:id/btn_submit");

		log.info("TC_01_Step_verify message khi so tien chuyen nho hon han muc toi thieu ");
		verifyEquals(waterBill.getDynamicTextView(driver, "com.VCB:id/tvContent"), "Thanh toán hóa đơn không thành công. Số tiền giao dịch nhỏ hơn hạn mức " + addCommasToLong((amount + 20)+"") + " VND/1 lần, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp");

		waterBill.clickToDynamicContinue(driver, "com.VCB:id/btOK");
		waterBill.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
		webBackend.resetAssignServicesLimit_All(driverWeb, "Thanh toán hóa đơn trả sau", "TESTBUG");

	}
	
	@Test
	public void TC_02_ThanhToanTienNuocLonHonHanMucGiaoDichMotLanGiaoDich() throws InterruptedException {
		log.info("TC_01_Step_01: Hoa don tien dien");
		home.clickToDynamicButtonLinkOrLinkText(driver, HomePage_Data.Home_Text_Elements.PAYMENT_WATER_BILL);
		waterBill = PageFactoryManager.getWaterBillPageObject(driver);

		log.info("TC_01_Step_02: Chon tai khoan nguon");
		waterBill.clickToTextID(driver, "com.VCB:id/number_account");

		sourceAccount = waterBill.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		sourceAccountMoney = waterBill.getDynamicTextByLabel(driver, Tittle_Text.AVAILIBLE_BALANCES);

		log.info("TC_01_Step_03: Chon nha cung cap");
		waterBill.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/wrap_tv");
		waterBill.clickToDynamicButtonLinkOrLinkText(driver, waterDaNang);
		
		log.info("TC_01_Step_04: Nhap ma khach hang va an tiep tuc");
		customerID = waterBill.inputCustomerId(listCusstomerID);
		
		ServiceLimitInfo inputInfoMax = new ServiceLimitInfo("1000", (amount - 20) + "", (amount - 10) + "", "10000000");

		webBackend.getInfoServiceLimit(driverWeb, "Thanh toán hóa đơn trả sau", inputInfoMax, "TESTBUG");
		
		log.info("TC_01_Click tiep tuc");
		waterBill.clickToDynamicContinue(driver, "com.VCB:id/btn_submit");

		log.info("TC_01_Step_verify message khi so tien chuyen nho hon han muc toi thieu ");
		verifyEquals(waterBill.getDynamicTextView(driver, "com.VCB:id/tvContent"), "Thanh toán hóa đơn không thành công. Số tiền giao dịch lớn hơn hạn mức " + addCommasToLong((amount - 10)+"") + " VND/1 lần, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp");

		waterBill.clickToDynamicContinue(driver, "com.VCB:id/btOK");
		waterBill.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
		webBackend.resetAssignServicesLimit_All(driverWeb, "Thanh toán hóa đơn trả sau", "TESTBUG");
	}
	
	@Test
	public void TC_03_ThanhToanTienNuocLonHonHanMucGiaoDichNhomGiaoDich() throws InterruptedException {
		log.info("TC_01_Step_01: Hoa don tien dien");
		home.clickToDynamicButtonLinkOrLinkText(driver, HomePage_Data.Home_Text_Elements.PAYMENT_WATER_BILL);
		waterBill = PageFactoryManager.getWaterBillPageObject(driver);

		log.info("TC_01_Step_02: Chon tai khoan nguon");
		waterBill.clickToTextID(driver, "com.VCB:id/number_account");

		sourceAccount = waterBill.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		sourceAccountMoney = waterBill.getDynamicTextByLabel(driver, Tittle_Text.AVAILIBLE_BALANCES);

		log.info("TC_01_Step_03: Chon nha cung cap");
		waterBill.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/wrap_tv");
		waterBill.clickToDynamicButtonLinkOrLinkText(driver, waterDaNang);
		
		log.info("TC_01_Step_04: Nhap ma khach hang va an tiep tuc");
		customerID = waterBill.inputCustomerId(listCusstomerID);
		
		log.info("TC_01_Quay ve man hinh khoi tao");
		webBackend.Setup_Assign_Services_Type_Limit(driverWeb, "TESTBUG", "Thanh toán hóa đơn" , (amount - 1000) + "");

		log.info("TC_01_Click tiep tuc");
		waterBill.clickToDynamicContinue(driver, "com.VCB:id/btn_submit");

		log.info("TC_01_Step_verify message khi so tien chuyen nho hon han muc toi thieu ");
		verifyEquals(waterBill.getDynamicTextView(driver, "com.VCB:id/tvContent"), "Thanh toán hóa đơn không thành công. Số tiền giao dịch lớn hơn hạn mức " + addCommasToLong((amount - 1000)+"") + " VND/1 ngày của nhóm giao dịch, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp");

		waterBill.clickToDynamicContinue(driver, "com.VCB:id/btOK");
		waterBill.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

		webBackend.Reset_Setup_Assign_Services_Type_Limit(driverWeb, "TESTBUG", "Thanh toán hóa đơn");
	}
	
	@Test
	public void TC_04_ThanhToanTienNuocLonHonHanMucGiaoDichGoiGiaoDich() throws InterruptedException {
		log.info("TC_01_Step_01: Hoa don tien dien");
		home.clickToDynamicButtonLinkOrLinkText(driver, HomePage_Data.Home_Text_Elements.PAYMENT_WATER_BILL);
		waterBill = PageFactoryManager.getWaterBillPageObject(driver);

		log.info("TC_01_Step_02: Chon tai khoan nguon");
		waterBill.clickToTextID(driver, "com.VCB:id/number_account");
		sourceAccount = waterBill.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);
		sourceAccountMoney = waterBill.getDynamicTextByLabel(driver, Tittle_Text.AVAILIBLE_BALANCES);

		log.info("TC_01_Step_03: Chon nha cung cap");
		waterBill.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/wrap_tv");
		waterBill.clickToDynamicButtonLinkOrLinkText(driver, waterDaNang);
		
		log.info("TC_01_Step_04: Nhap ma khach hang va an tiep tuc");
		customerID = waterBill.inputCustomerId(listCusstomerID);
		
		log.info("TC_01_Quay ve man hinh khoi tao");
		webBackend.Setup_Add_Method_Package_Total_Limit(driverWeb, "TESTBUG", "Method Otp", (amount - 1000) + "");
	
		log.info("TC_01_Click tiep tuc");
		waterBill.clickToDynamicContinue(driver, "com.VCB:id/btn_submit");

		log.info("TC_01_Step_verify message khi so tien chuyen nho hon han muc toi thieu ");
		verifyEquals(waterBill.getDynamicTextView(driver, "com.VCB:id/tvContent"), "Thanh toán hóa đơn không thành công. Số tiền giao dịch lớn hơn hạn mức " + addCommasToLong((amount - 1000)+"") + " VND/1 ngày của gói giao dịch, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp");

		waterBill.clickToDynamicContinue(driver, "com.VCB:id/btOK");
		waterBill.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
		webBackend.Reset_Package_Total_Limit(driverWeb, "TESTBUG", "Method Otp");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
//		service.stop();
	}

}
