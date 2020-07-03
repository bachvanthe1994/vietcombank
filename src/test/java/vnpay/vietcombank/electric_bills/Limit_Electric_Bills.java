package vnpay.vietcombank.electric_bills;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
import pageObjects.ElectricBillPageObject;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.WebBackendSetupPageObject;
import vietcombank_test_data.Electric_Bills_Data;
import vietcombank_test_data.Electric_Bills_Data.Data_Limit;
import vietcombank_test_data.Electric_Bills_Data.Electric_Text;
import vietcombank_test_data.LandLinePhoneCharge_Data.Text_Data;
import vietcombank_test_data.LuckyGift_Data.backendTitle;
import vietcombank_test_data.TransferMoneyInVCB_Data.InputText_MoneyRecurrent;

public class Limit_Electric_Bills extends Base {
	AppiumDriver<MobileElement> driver;
	WebDriver driverWeb;
	private LogInPageObject login;
	private HomePageObject home;
	private ElectricBillPageObject electricBill;
	private WebBackendSetupPageObject webBackend;

	SourceAccountModel sourceAccount = new SourceAccountModel();
	private List<String> listElectricBills = new ArrayList<String>();
	private String sourceAccountMoney, customerID;
	long amount = 0;
	ServiceLimitInfo inputInfo = new ServiceLimitInfo("1000", "10000", "1500000000", "1600000000");

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp", "username", "passWeb" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt, String username, String passWeb) throws IOException, InterruptedException, GeneralSecurityException {
		startServer();
		log.info("Before class: Mo backend ");
		driverWeb = openMultiBrowser(Constants.BE_BROWSER_CHROME, Constants.BE_BROWSER_VERSION, Constants.BE_URL);
		webBackend = WebPageFactoryManager.getWebBackendSetupPageObject(driverWeb);
		webBackend.Login_Web_Backend(driverWeb, username, passWeb);
		
		log.info("Before class: setup limit ngay");
		webBackend.setupAssignServicesLimit(driverWeb, Constants.POSTPAID_MOBILE, inputInfo, backendTitle.PACKAGE_CODE);
		webBackend.Setup_Assign_Services_Type_Limit(driverWeb, backendTitle.PACKAGE_CODE, Constants.PAY_BILL, Data_Limit.TOTAL_SERVICE);
		webBackend.Setup_Add_Method_Package_Total_Limit(driverWeb, backendTitle.PACKAGE_CODE, Constants.METHOD_OTP, Data_Limit.PACKAGE_LIMIT_TRANSFER);

		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);

		listElectricBills = Arrays.asList(getDataInCell(28).split(";"));

		electricBill = PageFactoryManager.getElectricBillPageObject(driver);

		home = PageFactoryManager.getHomePageObject(driver);
	}

	@Test
	public void TC_01_ThanhToanTienDienNhoHonHanMucGiaoDichMotLan() throws InterruptedException {

		log.info("TC_01_Step_01: Hoa don tien dien");
		home.clickToDynamicButtonLinkOrLinkText(driver, Electric_Text.ELECTRIC_BILLS_TEXT);

		log.info("TC_01_Step_02: Chon tai khoan nguon");
		electricBill.clickToTextID(driver, "com.VCB:id/number_account");
		sourceAccount = electricBill.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);
		sourceAccountMoney = electricBill.getDynamicTextByLabel(driver, Electric_Text.SURPLUS_TEXT);

		log.info("TC_01_Step_03: Chon Nha cung cap EVN Mien Trung");
		electricBill.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/wrap_tv");
		electricBill.clickToDynamicButtonLinkOrLinkText(driver, Electric_Bills_Data.DATA.EVN_MIEN_TRUNG);

		log.info("TC_01_Step_04: Nhap ma khach hang va an tiep tuc");
		customerID = electricBill.inputCustomerId(listElectricBills);
		amount = convertAvailableBalanceCurrentcyOrFeeToLong(electricBill.getDynamicTextInTransactionDetail(driver, Text_Data.AMOUNT_PAY));

		log.info("TC_01_Quay ve man hinh khoi tao");
		electricBill.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
		
		ServiceLimitInfo inputInfoMin = new ServiceLimitInfo("1000", (amount + 20) + "", (amount + 100) + "", "10000000");

		webBackend.getInfoServiceLimit(driverWeb, Constants.POSTPAID_MOBILE, inputInfoMin, backendTitle.PACKAGE_CODE);

		log.info("TC_01_Click tiep tuc");
		electricBill.clickToDynamicContinue(driver, "com.VCB:id/btn_submit");

		log.info("TC_01_Step_verify message khi so tien chuyen nho hon han muc toi thieu ");
		verifyEquals(electricBill.getDynamicTextView(driver, "com.VCB:id/tvContent"), "Thanh toán hóa đơn không thành công. Số tiền giao dịch nhỏ hơn hạn mức " + addCommasToLong((amount + 20)+"") + " VND/1 lần, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp");

		electricBill.clickToDynamicContinue(driver, "com.VCB:id/btOK");
		electricBill.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
		webBackend.resetAssignServicesLimit_All(driverWeb, Constants.POSTPAID_MOBILE, backendTitle.PACKAGE_CODE);
	}
	
	@Test
	public void TC_02_ThanhToanTienDienLonHonHanMucGiaoDichMotLan() throws InterruptedException {

		log.info("TC_02_Step_01: Hoa don tien dien");
		home.clickToDynamicButtonLinkOrLinkText(driver, Electric_Text.ELECTRIC_BILLS_TEXT);

		log.info("TC_02_Step_02: Chon tai khoan nguon");
		electricBill.clickToTextID(driver, "com.VCB:id/number_account");
		sourceAccount = electricBill.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);
		sourceAccountMoney = electricBill.getDynamicTextByLabel(driver, Electric_Text.SURPLUS_TEXT);

		log.info("TC_02_Step_03: Chon Nha cung cap EVN Mien Trung");
		electricBill.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/wrap_tv");
		electricBill.clickToDynamicButtonLinkOrLinkText(driver, Electric_Bills_Data.DATA.EVN_MIEN_TRUNG);
		
		log.info("TC_02_Step_04: Nhap ma khach hang va an tiep tuc");
		electricBill.inputIntoEditTextByID(driver, customerID, "com.VCB:id/code");
		
		ServiceLimitInfo inputInfoMax = new ServiceLimitInfo("1000", (amount - 20) + "", (amount - 10) + "", "10000000");
		webBackend.getInfoServiceLimit(driverWeb, Constants.POSTPAID_MOBILE, inputInfoMax, backendTitle.PACKAGE_CODE);
		
		log.info("TC_02_Click tiep tuc");
		electricBill.clickToDynamicContinue(driver, "com.VCB:id/btn_submit");

		log.info("TC_02_Step_verify message khi so tien chuyen nho hon han muc toi thieu ");
		verifyEquals(electricBill.getDynamicTextView(driver, "com.VCB:id/tvContent"), "Thanh toán hóa đơn không thành công. Số tiền giao dịch lớn hơn hạn mức " + addCommasToLong((amount - 10)+"") + " VND/1 lần, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp");

		electricBill.clickToDynamicContinue(driver, "com.VCB:id/btOK");
		electricBill.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
		webBackend.setupAssignServicesLimit(driverWeb, Constants.POSTPAID_MOBILE, inputInfo, backendTitle.PACKAGE_CODE);
	}

	@Test
	public void TC_03_ThanhToanTienDienLonHonHanMucGiaoDichNhomGiaoDich() throws InterruptedException {

		log.info("TC_03_Step_01: Hoa don tien dien");
		home.clickToDynamicButtonLinkOrLinkText(driver, Electric_Text.ELECTRIC_BILLS_TEXT);

		log.info("TC_03_Step_02: Chon tai khoan nguon");
		electricBill.clickToTextID(driver, "com.VCB:id/number_account");
		sourceAccount = electricBill.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);
		sourceAccountMoney = electricBill.getDynamicTextByLabel(driver, Electric_Text.SURPLUS_TEXT);

		log.info("TC_03_Step_03: Chon Nha cung cap EVN Mien Trung");
		electricBill.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/wrap_tv");
		electricBill.clickToDynamicButtonLinkOrLinkText(driver, Electric_Bills_Data.DATA.EVN_MIEN_TRUNG);

		log.info("TC_03_Step_04: Nhap ma khach hang va an tiep tuc");
		electricBill.inputIntoEditTextByID(driver, customerID, "com.VCB:id/code");
		
		log.info("TC_03_Quay ve man hinh khoi tao");
		webBackend.Setup_Assign_Services_Type_Limit(driverWeb, backendTitle.PACKAGE_CODE, Constants.PAY_BILL , (amount - 1000) + "");
		
		log.info("TC_03_Click tiep tuc");
		electricBill.clickToDynamicContinue(driver, "com.VCB:id/btn_submit");

		log.info("TC_03_Step_verify message khi so tien chuyen nho hon han muc toi thieu ");
		verifyEquals(electricBill.getDynamicTextView(driver, "com.VCB:id/tvContent"), "Thanh toán hóa đơn không thành công. Số tiền giao dịch lớn hơn hạn mức " + addCommasToLong((amount - 1000)+"") + " VND/1 ngày của nhóm dịch vụ, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline của Vietcombank để được trợ giúp.");

		electricBill.clickToDynamicContinue(driver, "com.VCB:id/btOK");
		electricBill.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
		webBackend.resetAssignServicesLimit_All(driverWeb, Constants.POSTPAID_MOBILE, backendTitle.PACKAGE_CODE);
	}
	
	@Test
	public void TC_04_ThanhToanTienDienLonHonHanMucGiaoDichGoiGiaoDich() throws InterruptedException {

		log.info("TC_04_Step_01: Hoa don tien dien");
		home.clickToDynamicButtonLinkOrLinkText(driver, Electric_Text.ELECTRIC_BILLS_TEXT);

		log.info("TC_04_Step_02: Chon tai khoan nguon");
		electricBill.clickToTextID(driver, "com.VCB:id/number_account");
		sourceAccount = electricBill.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);
		sourceAccountMoney = electricBill.getDynamicTextByLabel(driver, Electric_Text.SURPLUS_TEXT);

		log.info("TC_04_Step_03: Chon Nha cung cap EVN Mien Trung");
		electricBill.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/wrap_tv");
		electricBill.clickToDynamicButtonLinkOrLinkText(driver, Electric_Bills_Data.DATA.EVN_MIEN_TRUNG);

		log.info("TC_04_Step_04: Nhap ma khach hang va an tiep tuc");
		electricBill.inputIntoEditTextByID(driver, customerID, "com.VCB:id/code");
		
		log.info("TC_04_Quay ve man hinh khoi tao");
		webBackend.Setup_Add_Method_Package_Total_Limit(driverWeb, backendTitle.PACKAGE_CODE, Constants.METHOD_OTP, (amount - 1000) + "");
		
		log.info("TC_04_Click tiep tuc");
		electricBill.clickToDynamicContinue(driver, "com.VCB:id/btn_submit");

		log.info("TC_04_Step_verify message khi so tien chuyen nho hon han muc toi thieu ");
		verifyEquals(electricBill.getDynamicTextView(driver, "com.VCB:id/tvContent"), "Thanh toán hóa đơn không thành công. Số tiền giao dịch lớn hơn hạn mức " + addCommasToLong((amount - 1000)+"") + " VND/1 ngày của gói dịch vụ, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline của Vietcombank để được trợ giúp.");

		electricBill.clickToDynamicContinue(driver, "com.VCB:id/btOK");
		electricBill.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
		webBackend.resetAssignServicesLimit_All(driverWeb, Constants.POSTPAID_MOBILE, backendTitle.PACKAGE_CODE);
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();
	}

}
