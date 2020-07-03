package vnpay.vietcombank.sdk.hotelBooking;

import java.io.IOException;
import java.net.MalformedURLException;
import java.security.GeneralSecurityException;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.Constants;
import commons.PageFactoryManager;
import commons.WebPageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import model.SourceAccountModel;
import pageObjects.LogInPageObject;
import pageObjects.WebBackendSetupPageObject;
import pageObjects.sdk.hotelBooking.HotelBookingPageObject;
import vnpay.vietcombank.sdk.hotelBooking.data.HotelBooking_Data;

public class Limit_HotelBooking extends Base {
	AppiumDriver<MobileElement> driver;
	private WebDriver driverWeb;
	private LogInPageObject login;
	private HotelBookingPageObject hotelBooking;
	private WebBackendSetupPageObject setupBE;
	
	long surplus, money;
	SourceAccountModel sourceAccount = new SourceAccountModel();
	String password, customer_name, customer_phone;
	String paycode = "";
	String totalPrice = "";

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })

	@BeforeMethod
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException, GeneralSecurityException {
		startServer();
		log.info("Before class: Mo app ");
		if (deviceType.contains("android")) {
			driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		} else if (deviceType.contains("ios")) {
			driver = openIOSApp(deviceName, udid, url);
		}

		login = PageFactoryManager.getLoginPageObject(driver);

		login.Global_login(phone, pass, opt);

		password = pass;

		hotelBooking = PageFactoryManager.getHotelBookingPageObject(driver);

		customer_name = getDataInCell(1);
		customer_phone = getDataInCell(8);
	}


	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp", "username", "passWeb" })
	@Test
	public void TC_01_DatPhongKhachSan_HanMuc_ThapHon_HanMuc_ToiThieu_MotLanGiaoDich(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt, String username, String passWeb) throws MalformedURLException {
		log.info("TC_01_01_Click Dat phong khach san");
		hotelBooking.clickToDynamicTextOrButtonLink(HotelBooking_Data.BOOKING_HOTEL);

		log.info("TC_01_02_Click nut Dong y");
		hotelBooking.clickToDynamicButton(HotelBooking_Data.ACCEPT);

		log.info("TC_01_03_Tim kiem dia diem");
		hotelBooking.clickToDynamicTextViewByID("com.VCB:id/tvPlaceName");
		hotelBooking.inputToDynamicInputBox(HotelBooking_Data.HOTEL_NAME_BOOKING_INPUT, HotelBooking_Data.NAME_HOTEL_OR_PLACE);

		log.info("TC_01_04_Chon dia diem");
		hotelBooking.clickToDynamicTextView(HotelBooking_Data.HOTEL_NAME_BOOKING);

		log.info("TC_01_05_Click dat phong");
		hotelBooking.waitForTextViewDisplay(HotelBooking_Data.HOTEL_NAME_BOOKING);
		hotelBooking.scrollIDownToText(HotelBooking_Data.BOOKING);
		hotelBooking.clickToDynamicTextView(HotelBooking_Data.BOOKING);

		money = convertAvailableBalanceCurrentcyOrFeeToLong(hotelBooking.getTextViewByID("com.VCB:id/tvTotalPrice"));
		
		log.info("TC_01_05_Dong App");
		driver.quit();
		
		log.info("TC_01_05_Mo browser va setup Han muc");
		driverWeb = openMultiBrowser(Constants.BE_BROWSER_CHROME, Constants.BE_BROWSER_VERSION, Constants.BE_URL);
		
		setupBE = WebPageFactoryManager.getWebBackendSetupPageObject(driverWeb);
		setupBE.Login_Web_Backend(driverWeb, username, passWeb);
		
		log.info("Setup assign services limit");
		setupBE.Setup_Assign_Services_Type_Limit(driverWeb, Constants.BE_CODE_PACKAGE, "Thanh toán hóa đơn", "1000000000");

		log.info("Setup assign services limit");
		setupBE.Setup_Assign_Services_Limit(driverWeb, Constants.BE_CODE_PACKAGE, "Thanh toán phòng khách sạn", money + 1 + "", "100000000", "1000000000");

		setupBE.clearCacheBE(driverWeb);
		
		log.info("TC_01_00_Mo lai App");
		
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		
		login = PageFactoryManager.getLoginPageObject(driver);

		login.Global_login(phone, pass, opt);

		hotelBooking = PageFactoryManager.getHotelBookingPageObject(driver);
		
		log.info("TC_01_01_Click Dat phong khach san");
		hotelBooking.clickToDynamicTextOrButtonLink(HotelBooking_Data.BOOKING_HOTEL);

		log.info("TC_01_02_Click nut Dong y");
		hotelBooking.clickToDynamicButton(HotelBooking_Data.ACCEPT);

		log.info("TC_01_03_Tim kiem dia diem");
		hotelBooking.clickToDynamicTextViewByID("com.VCB:id/tvPlaceName");
		hotelBooking.inputToDynamicInputBox(HotelBooking_Data.HOTEL_NAME_BOOKING_INPUT, HotelBooking_Data.NAME_HOTEL_OR_PLACE);

		log.info("TC_01_04_Chon dia diem");
		hotelBooking.clickToDynamicTextView(HotelBooking_Data.HOTEL_NAME_BOOKING);

		log.info("TC_01_05_Click dat phong");
		hotelBooking.waitForTextViewDisplay(HotelBooking_Data.HOTEL_NAME_BOOKING);
		hotelBooking.scrollIDownToText(HotelBooking_Data.BOOKING);
		hotelBooking.clickToDynamicTextView(HotelBooking_Data.BOOKING);

		log.info("TC_01_06_Nhap ten khach hang");
		hotelBooking.inputToDynamicInputBoxByID(customer_name, "com.VCB:id/etCustomerName");

		log.info("TC_01_07_Nhap so dien thoai khach hang");
		hotelBooking.inputToDynamicInputBoxByID(customer_phone, "com.VCB:id/etCustomerPhone");

		log.info("TC_01_08_Nhap email khach hang");
		hotelBooking.inputToDynamicInputBoxByID(HotelBooking_Data.EMAIL_BOOKING, "com.VCB:id/etCustomerEmail");

		log.info("TC_01_09_Click Thanh toan");
		hotelBooking.clickToDynamicTextView(HotelBooking_Data.PAYMENT);

		log.info("TC_01_10_Chon tai khoan nguon");
		hotelBooking.scrollUpToText(driver, HotelBooking_Data.SOURCE_ACCOUNT);
		hotelBooking.clickToDynamicDropDown(HotelBooking_Data.SOURCE_ACCOUNT);

		sourceAccount = hotelBooking.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		surplus = convertAvailableBalanceCurrentcyOrFeeToLong(hotelBooking.getDynamicTextInTransactionDetail(driver, HotelBooking_Data.AVAILIBLE_BALANCES));

		log.info("TC_01_11_Kiem tra thong tin hoa don");
		hotelBooking.scrollDownToButton(driver, HotelBooking_Data.CONTINUE_TEXT);

		log.info("TC_01_12_Click Tiep tuc");
		hotelBooking.clickToDynamicButton(HotelBooking_Data.CONTINUE_TEXT);
		
		log.info("TC_01_13_Kiem tra thong bao");
		verifyEquals(hotelBooking.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), HotelBooking_Data.MESSAGE_MIN_LIMIT_PER_TRANS_1 + addCommasToLong((money + 1) + "") + " VND" + HotelBooking_Data.MESSAGE_MIN_LIMIT_PER_TRANS_2);
		
	}
	
	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp", "username", "passWeb" })
	@Test
	public void TC_02_DatPhongKhachSan_HanMuc_CaoHon_HanMuc_ToiDa_MotLanGiaoDich(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt, String username, String passWeb) throws MalformedURLException {
		log.info("TC_02_01_Click Dat phong khach san");
		hotelBooking.clickToDynamicTextOrButtonLink(HotelBooking_Data.BOOKING_HOTEL);

		log.info("TC_02_02_Click nut Dong y");
		hotelBooking.clickToDynamicButton(HotelBooking_Data.ACCEPT);

		log.info("TC_02_03_Tim kiem dia diem");
		hotelBooking.clickToDynamicTextViewByID("com.VCB:id/tvPlaceName");
		hotelBooking.inputToDynamicInputBox(HotelBooking_Data.HOTEL_NAME_BOOKING_INPUT, HotelBooking_Data.NAME_HOTEL_OR_PLACE);

		log.info("TC_02_04_Chon dia diem");
		hotelBooking.clickToDynamicTextView(HotelBooking_Data.HOTEL_NAME_BOOKING);

		log.info("TC_02_05_Click dat phong");
		hotelBooking.waitForTextViewDisplay(HotelBooking_Data.HOTEL_NAME_BOOKING);
		hotelBooking.scrollIDownToText(HotelBooking_Data.BOOKING);
		hotelBooking.clickToDynamicTextView(HotelBooking_Data.BOOKING);

		money = convertAvailableBalanceCurrentcyOrFeeToLong(hotelBooking.getTextViewByID("com.VCB:id/tvTotalPrice"));
		
		log.info("TC_02_05_Dong App");
		driver.quit();
		
		log.info("TC_02_05_Mo browser va setup Han muc");
		driverWeb = openMultiBrowser(Constants.BE_BROWSER_CHROME, Constants.BE_BROWSER_VERSION, Constants.BE_URL);
		
		setupBE = WebPageFactoryManager.getWebBackendSetupPageObject(driverWeb);
		setupBE.Login_Web_Backend(driverWeb, username, passWeb);
		
		log.info("Setup assign services limit");
		setupBE.Setup_Assign_Services_Type_Limit(driverWeb, Constants.BE_CODE_PACKAGE, "Thanh toán hóa đơn", "1000000000");

		log.info("Setup assign services limit");
		setupBE.Setup_Assign_Services_Limit(driverWeb, Constants.BE_CODE_PACKAGE, "Thanh toán phòng khách sạn", "10000", money - 1 + "", "1000000000");

		setupBE.clearCacheBE(driverWeb);
		
		log.info("TC_02_00_Mo lai App");
		
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		
		login = PageFactoryManager.getLoginPageObject(driver);

		login.Global_login(phone, pass, opt);

		hotelBooking = PageFactoryManager.getHotelBookingPageObject(driver);
		
		log.info("TC_02_01_Click Dat phong khach san");
		hotelBooking.clickToDynamicTextOrButtonLink(HotelBooking_Data.BOOKING_HOTEL);

		log.info("TC_02_02_Click nut Dong y");
		hotelBooking.clickToDynamicButton(HotelBooking_Data.ACCEPT);

		log.info("TC_02_03_Tim kiem dia diem");
		hotelBooking.clickToDynamicTextViewByID("com.VCB:id/tvPlaceName");
		hotelBooking.inputToDynamicInputBox(HotelBooking_Data.HOTEL_NAME_BOOKING_INPUT, HotelBooking_Data.NAME_HOTEL_OR_PLACE);

		log.info("TC_02_04_Chon dia diem");
		hotelBooking.clickToDynamicTextView(HotelBooking_Data.HOTEL_NAME_BOOKING);

		log.info("TC_02_05_Click dat phong");
		hotelBooking.waitForTextViewDisplay(HotelBooking_Data.HOTEL_NAME_BOOKING);
		hotelBooking.scrollIDownToText(HotelBooking_Data.BOOKING);
		hotelBooking.clickToDynamicTextView(HotelBooking_Data.BOOKING);

		log.info("TC_02_06_Nhap ten khach hang");
		hotelBooking.inputToDynamicInputBoxByID(customer_name, "com.VCB:id/etCustomerName");

		log.info("TC_02_07_Nhap so dien thoai khach hang");
		hotelBooking.inputToDynamicInputBoxByID(customer_phone, "com.VCB:id/etCustomerPhone");

		log.info("TC_02_08_Nhap email khach hang");
		hotelBooking.inputToDynamicInputBoxByID(HotelBooking_Data.EMAIL_BOOKING, "com.VCB:id/etCustomerEmail");

		log.info("TC_02_09_Click Thanh toan");
		hotelBooking.clickToDynamicTextView(HotelBooking_Data.PAYMENT);

		log.info("TC_02_10_Chon tai khoan nguon");
		hotelBooking.scrollUpToText(driver, HotelBooking_Data.SOURCE_ACCOUNT);
		hotelBooking.clickToDynamicDropDown(HotelBooking_Data.SOURCE_ACCOUNT);

		sourceAccount = hotelBooking.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		surplus = convertAvailableBalanceCurrentcyOrFeeToLong(hotelBooking.getDynamicTextInTransactionDetail(driver, HotelBooking_Data.AVAILIBLE_BALANCES));

		log.info("TC_02_11_Kiem tra thong tin hoa don");
		hotelBooking.scrollDownToButton(driver, HotelBooking_Data.CONTINUE_TEXT);

		log.info("TC_02_12_Click Tiep tuc");
		hotelBooking.clickToDynamicButton(HotelBooking_Data.CONTINUE_TEXT);
		
		log.info("TC_02_13_Kiem tra thong bao");
		verifyEquals(hotelBooking.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), HotelBooking_Data.MESSAGE_MAX_LIMIT_PER_TRANS_1 + addCommasToLong((money - 1) + "") + " VND" + HotelBooking_Data.MESSAGE_MAX_LIMIT_PER_TRANS_2);
		
	}

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp", "username", "passWeb" })
	@Test
	public void TC_03_DatPhongKhachSan_HanMuc_CaoHon_HanMuc_ToiDa_MotLanGiaoDich_NhomDichVu(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt, String username, String passWeb) throws MalformedURLException {
		log.info("TC_03_01_Click Dat phong khach san");
		hotelBooking.clickToDynamicTextOrButtonLink(HotelBooking_Data.BOOKING_HOTEL);

		log.info("TC_03_02_Click nut Dong y");
		hotelBooking.clickToDynamicButton(HotelBooking_Data.ACCEPT);

		log.info("TC_03_03_Tim kiem dia diem");
		hotelBooking.clickToDynamicTextViewByID("com.VCB:id/tvPlaceName");
		hotelBooking.inputToDynamicInputBox(HotelBooking_Data.HOTEL_NAME_BOOKING_INPUT, HotelBooking_Data.NAME_HOTEL_OR_PLACE);

		log.info("TC_03_04_Chon dia diem");
		hotelBooking.clickToDynamicTextView(HotelBooking_Data.HOTEL_NAME_BOOKING);

		log.info("TC_03_05_Click dat phong");
		hotelBooking.waitForTextViewDisplay(HotelBooking_Data.HOTEL_NAME_BOOKING);
		hotelBooking.scrollIDownToText(HotelBooking_Data.BOOKING);
		hotelBooking.clickToDynamicTextView(HotelBooking_Data.BOOKING);

		money = convertAvailableBalanceCurrentcyOrFeeToLong(hotelBooking.getTextViewByID("com.VCB:id/tvTotalPrice"));
		
		log.info("TC_03_05_Dong App");
		driver.quit();
		
		log.info("TC_03_05_Mo browser va setup Han muc");
		driverWeb = openMultiBrowser(Constants.BE_BROWSER_CHROME, Constants.BE_BROWSER_VERSION, Constants.BE_URL);
		
		setupBE = WebPageFactoryManager.getWebBackendSetupPageObject(driverWeb);
		setupBE.Login_Web_Backend(driverWeb, username, passWeb);
		
		log.info("Setup assign services limit");
		setupBE.Setup_Assign_Services_Type_Limit(driverWeb, Constants.BE_CODE_PACKAGE, "Thanh toán hóa đơn", money - 1 + "");

		setupBE.clearCacheBE(driverWeb);
		
		log.info("TC_03_00_Mo lai App");
		
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		
		login = PageFactoryManager.getLoginPageObject(driver);

		login.Global_login(phone, pass, opt);

		hotelBooking = PageFactoryManager.getHotelBookingPageObject(driver);
		
		log.info("TC_03_01_Click Dat phong khach san");
		hotelBooking.clickToDynamicTextOrButtonLink(HotelBooking_Data.BOOKING_HOTEL);

		log.info("TC_03_02_Click nut Dong y");
		hotelBooking.clickToDynamicButton(HotelBooking_Data.ACCEPT);

		log.info("TC_03_03_Tim kiem dia diem");
		hotelBooking.clickToDynamicTextViewByID("com.VCB:id/tvPlaceName");
		hotelBooking.inputToDynamicInputBox(HotelBooking_Data.HOTEL_NAME_BOOKING_INPUT, HotelBooking_Data.NAME_HOTEL_OR_PLACE);

		log.info("TC_03_04_Chon dia diem");
		hotelBooking.clickToDynamicTextView(HotelBooking_Data.HOTEL_NAME_BOOKING);

		log.info("TC_03_05_Click dat phong");
		hotelBooking.waitForTextViewDisplay(HotelBooking_Data.HOTEL_NAME_BOOKING);
		hotelBooking.scrollIDownToText(HotelBooking_Data.BOOKING);
		hotelBooking.clickToDynamicTextView(HotelBooking_Data.BOOKING);

		log.info("TC_03_06_Nhap ten khach hang");
		hotelBooking.inputToDynamicInputBoxByID(customer_name, "com.VCB:id/etCustomerName");

		log.info("TC_03_07_Nhap so dien thoai khach hang");
		hotelBooking.inputToDynamicInputBoxByID(customer_phone, "com.VCB:id/etCustomerPhone");

		log.info("TC_03_08_Nhap email khach hang");
		hotelBooking.inputToDynamicInputBoxByID(HotelBooking_Data.EMAIL_BOOKING, "com.VCB:id/etCustomerEmail");

		log.info("TC_03_09_Click Thanh toan");
		hotelBooking.clickToDynamicTextView(HotelBooking_Data.PAYMENT);

		log.info("TC_03_10_Chon tai khoan nguon");
		hotelBooking.scrollUpToText(driver, HotelBooking_Data.SOURCE_ACCOUNT);
		hotelBooking.clickToDynamicDropDown(HotelBooking_Data.SOURCE_ACCOUNT);

		sourceAccount = hotelBooking.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		surplus = convertAvailableBalanceCurrentcyOrFeeToLong(hotelBooking.getDynamicTextInTransactionDetail(driver, HotelBooking_Data.AVAILIBLE_BALANCES));

		log.info("TC_03_11_Kiem tra thong tin hoa don");
		hotelBooking.scrollDownToButton(driver, HotelBooking_Data.CONTINUE_TEXT);

		log.info("TC_03_12_Click Tiep tuc");
		hotelBooking.clickToDynamicButton(HotelBooking_Data.CONTINUE_TEXT);
		
		log.info("TC_03_13_Kiem tra thong bao");
		verifyEquals(hotelBooking.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), HotelBooking_Data.MESSAGE_MAX_LIMIT_PER_GROUP_TRANS_1 + addCommasToLong((money - 1) + "") + " VND" + HotelBooking_Data.MESSAGE_MAX_LIMIT_PER_GROUP_TRANS_2);
		
	}
	
	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp", "username", "passWeb" })
	@Test
	public void TC_04_DatPhongKhachSan_HanMuc_CaoHon_HanMuc_ToiDa_MotLanGiaoDich_GoiDichVu(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt, String username, String passWeb) throws MalformedURLException {
		log.info("TC_04_01_Click Dat phong khach san");
		hotelBooking.clickToDynamicTextOrButtonLink(HotelBooking_Data.BOOKING_HOTEL);

		log.info("TC_04_02_Click nut Dong y");
		hotelBooking.clickToDynamicButton(HotelBooking_Data.ACCEPT);

		log.info("TC_04_03_Tim kiem dia diem");
		hotelBooking.clickToDynamicTextViewByID("com.VCB:id/tvPlaceName");
		hotelBooking.inputToDynamicInputBox(HotelBooking_Data.HOTEL_NAME_BOOKING_INPUT, HotelBooking_Data.NAME_HOTEL_OR_PLACE);

		log.info("TC_04_04_Chon dia diem");
		hotelBooking.clickToDynamicTextView(HotelBooking_Data.HOTEL_NAME_BOOKING);

		log.info("TC_04_05_Click dat phong");
		hotelBooking.waitForTextViewDisplay(HotelBooking_Data.HOTEL_NAME_BOOKING);
		hotelBooking.scrollIDownToText(HotelBooking_Data.BOOKING);
		hotelBooking.clickToDynamicTextView(HotelBooking_Data.BOOKING);

		money = convertAvailableBalanceCurrentcyOrFeeToLong(hotelBooking.getTextViewByID("com.VCB:id/tvTotalPrice"));
		
		log.info("TC_04_05_Dong App");
		driver.quit();
		
		log.info("TC_04_05_Mo browser va setup Han muc");
		driverWeb = openMultiBrowser(Constants.BE_BROWSER_CHROME, Constants.BE_BROWSER_VERSION, Constants.BE_URL);
		
		setupBE = WebPageFactoryManager.getWebBackendSetupPageObject(driverWeb);
		setupBE.Login_Web_Backend(driverWeb, username, passWeb);
		
		log.info("Setup assign services limit");
		setupBE.Setup_Assign_Services_Type_Limit(driverWeb, Constants.BE_CODE_PACKAGE, "Thanh toán hóa đơn", money - 1 + "");

		setupBE.clearCacheBE(driverWeb);
		
		log.info("TC_04_00_Mo lai App");
		
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		
		login = PageFactoryManager.getLoginPageObject(driver);

		login.Global_login(phone, pass, opt);

		hotelBooking = PageFactoryManager.getHotelBookingPageObject(driver);
		
		log.info("TC_04_01_Click Dat phong khach san");
		hotelBooking.clickToDynamicTextOrButtonLink(HotelBooking_Data.BOOKING_HOTEL);

		log.info("TC_04_02_Click nut Dong y");
		hotelBooking.clickToDynamicButton(HotelBooking_Data.ACCEPT);

		log.info("TC_04_03_Tim kiem dia diem");
		hotelBooking.clickToDynamicTextViewByID("com.VCB:id/tvPlaceName");
		hotelBooking.inputToDynamicInputBox(HotelBooking_Data.HOTEL_NAME_BOOKING_INPUT, HotelBooking_Data.NAME_HOTEL_OR_PLACE);

		log.info("TC_04_04_Chon dia diem");
		hotelBooking.clickToDynamicTextView(HotelBooking_Data.HOTEL_NAME_BOOKING);

		log.info("TC_04_05_Click dat phong");
		hotelBooking.waitForTextViewDisplay(HotelBooking_Data.HOTEL_NAME_BOOKING);
		hotelBooking.scrollIDownToText(HotelBooking_Data.BOOKING);
		hotelBooking.clickToDynamicTextView(HotelBooking_Data.BOOKING);

		log.info("TC_04_06_Nhap ten khach hang");
		hotelBooking.inputToDynamicInputBoxByID(customer_name, "com.VCB:id/etCustomerName");

		log.info("TC_04_07_Nhap so dien thoai khach hang");
		hotelBooking.inputToDynamicInputBoxByID(customer_phone, "com.VCB:id/etCustomerPhone");

		log.info("TC_04_08_Nhap email khach hang");
		hotelBooking.inputToDynamicInputBoxByID(HotelBooking_Data.EMAIL_BOOKING, "com.VCB:id/etCustomerEmail");

		log.info("TC_04_09_Click Thanh toan");
		hotelBooking.clickToDynamicTextView(HotelBooking_Data.PAYMENT);

		log.info("TC_04_10_Chon tai khoan nguon");
		hotelBooking.scrollUpToText(driver, HotelBooking_Data.SOURCE_ACCOUNT);
		hotelBooking.clickToDynamicDropDown(HotelBooking_Data.SOURCE_ACCOUNT);

		sourceAccount = hotelBooking.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		surplus = convertAvailableBalanceCurrentcyOrFeeToLong(hotelBooking.getDynamicTextInTransactionDetail(driver, HotelBooking_Data.AVAILIBLE_BALANCES));

		log.info("TC_04_11_Kiem tra thong tin hoa don");
		hotelBooking.scrollDownToButton(driver, HotelBooking_Data.CONTINUE_TEXT);

		log.info("TC_04_12_Click Tiep tuc");
		hotelBooking.clickToDynamicButton(HotelBooking_Data.CONTINUE_TEXT);
		
		log.info("TC_04_13_Kiem tra thong bao");
		verifyEquals(hotelBooking.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), HotelBooking_Data.MESSAGE_MAX_LIMIT_PER_PACKAGE_TRANS_1 + addCommasToLong((money - 1) + "") + " VND" + HotelBooking_Data.MESSAGE_MAX_LIMIT_PER_PACKAGE_TRANS_2);
		
	}
	
	@AfterMethod(alwaysRun = true)
	public void afterClass() {
		closeApp();
		setupBE.Reset_Package_Total_Limit(driverWeb, Constants.BE_CODE_PACKAGE, "Method Otp");
		setupBE.Reset_Setup_Assign_Services_Type_Limit(driverWeb, Constants.BE_CODE_PACKAGE, "Thanh toán hóa đơn");
		setupBE.Reset_Setup_Assign_Services_Limit(driverWeb, Constants.BE_CODE_PACKAGE, "Thanh toán phòng khách sạn");
		driverWeb.quit();
		service.stop();
	}

}
