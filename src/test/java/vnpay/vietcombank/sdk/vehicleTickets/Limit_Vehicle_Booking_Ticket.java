package vnpay.vietcombank.sdk.vehicleTickets;

import java.io.IOException;
import java.net.MalformedURLException;

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
import vehicalPageObject.VehicalPageObject;
import vietcombank_test_data.TransactionReport_Data.ReportTitle;
import vnpay.vietcombank.sdk.vehicleTicket.data.VehicalData;
import vnpay.vietcombank.sdk.vehicleTicket.data.VehicalData.DATA_ORDER_TICKET;

public class Limit_Vehicle_Booking_Ticket extends Base {
	AppiumDriver<MobileElement> driver;
	private WebDriver driverWeb;
	private WebBackendSetupPageObject setupBE;
	private LogInPageObject login;
	private VehicalPageObject vehicleTicket;
	long money = 0;
	SourceAccountModel sourceAccount = new SourceAccountModel();
	String account = "";
	
	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeMethod
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		if (deviceType.contains("android")) {
			driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		} else if (deviceType.contains("ios")) {
			driver = openIOSApp(deviceName, udid, url);
		}
		login = PageFactoryManager.getLoginPageObject(driver);
		vehicleTicket = PageFactoryManager.getVehicalPageObject(driver);
		login.Global_login(phone, pass, opt);

	}

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp", "username", "passWeb" })
	@Test
	public void TC_01_DatVeXe_NhoHon_HanMucToiThieu_TrenMotGiaoDich(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt, String username, String passWeb) throws MalformedURLException {
		log.info("TC_01_Step_0: Click mo Dat ve xe");
		vehicleTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.ORDER_TICKET);
		vehicleTicket.clickToDynamicButton(DATA_ORDER_TICKET.AGREE);

		log.info("TC_01_Step_01: Chon va nhap diem di");
		vehicleTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.CHOOSE_FROM_PLACE);
		vehicleTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.PLACE_1, VehicalData.DATA_ORDER_TICKET.DESTINATION);
		vehicleTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_1);

		log.info("TC_01_Step_02: Chon va nhap diem den");
		vehicleTicket.clickToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.ARRIVAL);
		vehicleTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.PLACE_5, VehicalData.DATA_ORDER_TICKET.ARRIVAL);
		vehicleTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_5);

		log.info("TC_01_Step_03: Chon ngay muon di");
		vehicleTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.TOMORROW);

		log.info("TC_01_Step_4: Tim kiem chuyen di");
		vehicleTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.BUTTON_FIND_TRIP);

		log.info("TC_01_Step_5: click chọn gh2");
		vehicleTicket.clickToDynamicText("Chọn ghế");
		vehicleTicket.chooseSeats(2, "(255,255,255)");
		
		log.info("TC_01_Step_6: Lay so tien");
		money = convertAvailableBalanceCurrentcyOrFeeToLong(vehicleTicket.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTotalAmount"));
		
		log.info("TC_01_05_Dong App");
		driver.quit();
		
		log.info("TC_01_05_Mo browser va setup Han muc");
		driverWeb = openMultiBrowser(Constants.BE_BROWSER_CHROME, Constants.BE_BROWSER_VERSION, Constants.BE_URL);
		
		setupBE = WebPageFactoryManager.getWebBackendSetupPageObject(driverWeb);
		setupBE.Login_Web_Backend(driverWeb, username, passWeb);
		
		log.info("Setup assign services limit");
		setupBE.Setup_Assign_Services_Type_Limit(driverWeb, Constants.BE_CODE_PACKAGE, VehicalData.DATA_ORDER_TICKET.PAY_ORDER, "1000000000");

		log.info("Setup assign services limit");
		setupBE.Setup_Assign_Services_Limit(driverWeb, Constants.BE_CODE_PACKAGE, VehicalData.DATA_ORDER_TICKET.PAY_VEHICLE_TICKET, money + 1 + "", "100000000", "1000000000");
		
		log.info("TC_01_00_Mo lai App");
		
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		
		login = PageFactoryManager.getLoginPageObject(driver);

		login.Global_login(phone, pass, opt);

		vehicleTicket = PageFactoryManager.getVehicalPageObject(driver);
		
		log.info("TC_01_Step_0: Click mo Dat ve xe");
		vehicleTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.ORDER_TICKET);
		vehicleTicket.clickToDynamicButton(DATA_ORDER_TICKET.AGREE);

		log.info("TC_01_Step_01: Chon va nhap diem di");
		vehicleTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.CHOOSE_FROM_PLACE);
		vehicleTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.PLACE_1, VehicalData.DATA_ORDER_TICKET.DESTINATION);
		vehicleTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_1);

		log.info("TC_01_Step_02: Chon va nhap diem den");
		vehicleTicket.clickToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.ARRIVAL);
		vehicleTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.PLACE_5, VehicalData.DATA_ORDER_TICKET.ARRIVAL);
		vehicleTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_5);

		log.info("TC_01_Step_03: Chon ngay muon di");
		vehicleTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.TOMORROW);

		log.info("TC_01_Step_4: Tim kiem chuyen di");
		vehicleTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.BUTTON_FIND_TRIP);

		log.info("TC_01_Step_5: click chọn gh2");
		vehicleTicket.clickToDynamicText("Chọn ghế");
		vehicleTicket.chooseSeats(2, "(255,255,255)");
		
		log.info("TC_01_Step_10 : Dat chuyen di: ");
		vehicleTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.BOOK_SEAT);

		log.info("TC_01_Step_11 Click chon tiep tuc ");
		vehicleTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.CONTINUE_BUTTON);
		
		log.info("TC_01_Step_12 Click chon tu choi ");
		vehicleTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.BUTTON_CANCER);

		log.info("TC_01_Step_13 Input email");
		vehicleTicket.inputIntoEditTextByID(VehicalData.DATA_ORDER_TICKET.EMAIL_ADDRESS + randomNumber() + "@gmail.com", "com.VCB:id/email");
		
		log.info("TC_01_Step_14 Click btutton Thanh toan");
		vehicleTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.CONTINUE_BUTTON);
		vehicleTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.PAYMENT_BUTTON);

		log.info("TC_01_Step_15 Verify hien thi man hinh thong tin ve xe");
		verifyTrue(vehicleTicket.isDynamicMessageAndLabelTextDisplayed(VehicalData.DATA_ORDER_TICKET.INFO_TICKET));

		log.info("TC_01_Step_16 Lay thong tin tai khoan nguon");
		vehicleTicket.scrollUpToText(ReportTitle.SOURCE_ACCOUNT);
		vehicleTicket.clickToDynamicTextByID("com.VCB:id/number_account");
		sourceAccount = vehicleTicket.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, "VND");
		account = sourceAccount.account;

		log.info("TC_01_Step_17 Click button tiep tuc");
		vehicleTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.CONTINUE_BUTTON);
		
		log.info("TC_01_18_Kiem tra thong bao");
		verifyEquals(vehicleTicket.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), VehicalData.NOTIFICATION.MESSAGE_MIN_LIMIT_PER_TRANS_1 + addCommasToLong((money + 1) + "") + " VND" +  VehicalData.NOTIFICATION.MESSAGE_MIN_LIMIT_PER_TRANS_2);
		
	}
	
	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp", "username", "passWeb" })
	@Test
	public void TC_02_DatVeXe_LonHon_HanMucToiDa_TrenMotGiaoDich(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt, String username, String passWeb) throws MalformedURLException {
		log.info("TC_02_Step_0: Click mo Dat ve xe");
		vehicleTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.ORDER_TICKET);
		vehicleTicket.clickToDynamicButton(DATA_ORDER_TICKET.AGREE);

		log.info("TC_02_Step_01: Chon va nhap diem di");
		vehicleTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.CHOOSE_FROM_PLACE);
		vehicleTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.PLACE_1, VehicalData.DATA_ORDER_TICKET.DESTINATION);
		vehicleTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_1);

		log.info("TC_02_Step_02: Chon va nhap diem den");
		vehicleTicket.clickToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.ARRIVAL);
		vehicleTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.PLACE_5, VehicalData.DATA_ORDER_TICKET.ARRIVAL);
		vehicleTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_5);

		log.info("TC_02_Step_03: Chon ngay muon di");
		vehicleTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.TOMORROW);

		log.info("TC_02_Step_4: Tim kiem chuyen di");
		vehicleTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.BUTTON_FIND_TRIP);

		log.info("TC_02_Step_5: click chọn gh2");
		vehicleTicket.clickToDynamicText("Chọn ghế");
		vehicleTicket.chooseSeats(2, "(255,255,255)");
		
		log.info("TC_02_Step_6: Lay so tien");
		money = convertAvailableBalanceCurrentcyOrFeeToLong(vehicleTicket.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTotalAmount"));
		
		log.info("TC_02_05_Dong App");
		driver.quit();
		
		log.info("TC_02_05_Mo browser va setup Han muc");
		driverWeb = openMultiBrowser(Constants.BE_BROWSER_CHROME, Constants.BE_BROWSER_VERSION, Constants.BE_URL);
		
		setupBE = WebPageFactoryManager.getWebBackendSetupPageObject(driverWeb);
		setupBE.Login_Web_Backend(driverWeb, username, passWeb);
		
		log.info("Setup assign services limit");
		setupBE.Setup_Assign_Services_Type_Limit(driverWeb, Constants.BE_CODE_PACKAGE, VehicalData.DATA_ORDER_TICKET.PAY_ORDER, "1000000000");

		log.info("Setup assign services limit");
		setupBE.Setup_Assign_Services_Limit(driverWeb, Constants.BE_CODE_PACKAGE, VehicalData.DATA_ORDER_TICKET.PAY_VEHICLE_TICKET, "10000",  money - 1 + "", "1000000000");
		
		log.info("TC_02_00_Mo lai App");
		
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		
		login = PageFactoryManager.getLoginPageObject(driver);

		login.Global_login(phone, pass, opt);

		vehicleTicket = PageFactoryManager.getVehicalPageObject(driver);
		
		log.info("TC_02_Step_0: Click mo Dat ve xe");
		vehicleTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.ORDER_TICKET);
		vehicleTicket.clickToDynamicButton(DATA_ORDER_TICKET.AGREE);

		log.info("TC_02_Step_01: Chon va nhap diem di");
		vehicleTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.CHOOSE_FROM_PLACE);
		vehicleTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.PLACE_1, VehicalData.DATA_ORDER_TICKET.DESTINATION);
		vehicleTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_1);

		log.info("TC_02_Step_02: Chon va nhap diem den");
		vehicleTicket.clickToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.ARRIVAL);
		vehicleTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.PLACE_5, VehicalData.DATA_ORDER_TICKET.ARRIVAL);
		vehicleTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_5);

		log.info("TC_02_Step_03: Chon ngay muon di");
		vehicleTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.TOMORROW);

		log.info("TC_02_Step_4: Tim kiem chuyen di");
		vehicleTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.BUTTON_FIND_TRIP);

		log.info("TC_02_Step_5: click chọn gh2");
		vehicleTicket.clickToDynamicText("Chọn ghế");
		vehicleTicket.chooseSeats(2, "(255,255,255)");
		
		log.info("TC_02_Step_10 : Dat chuyen di: ");
		vehicleTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.BOOK_SEAT);

		log.info("TC_02_Step_11 Click chon tiep tuc ");
		vehicleTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.CONTINUE_BUTTON);
		
		log.info("TC_02_Step_12 Click chon tu choi ");
		vehicleTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.BUTTON_CANCER);

		log.info("TC_02_Step_13 Input email");
		vehicleTicket.inputIntoEditTextByID(VehicalData.DATA_ORDER_TICKET.EMAIL_ADDRESS + randomNumber() + "@gmail.com", "com.VCB:id/email");

		log.info("TC_02_Step_14 Click btutton Thanh toan");
		vehicleTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.CONTINUE_BUTTON);
		vehicleTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.PAYMENT_BUTTON);

		log.info("TC_02_Step_15 Verify hien thi man hinh thong tin ve xe");
		verifyTrue(vehicleTicket.isDynamicMessageAndLabelTextDisplayed(VehicalData.DATA_ORDER_TICKET.INFO_TICKET));

		log.info("TC_02_Step_16 Lay thong tin tai khoan nguon");
		vehicleTicket.scrollUpToText(ReportTitle.SOURCE_ACCOUNT);
		vehicleTicket.clickToDynamicTextByID("com.VCB:id/number_account");
		sourceAccount = vehicleTicket.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, "VND");
		account = sourceAccount.account;

		log.info("TC_02_Step_17 Click button tiep tuc");
		vehicleTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.CONTINUE_BUTTON);
		
		log.info("TC_02_18_Kiem tra thong bao");
		verifyEquals(vehicleTicket.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), VehicalData.NOTIFICATION.MESSAGE_MAX_LIMIT_PER_TRANS_1 + addCommasToLong((money - 1) + "") + " VND" +  VehicalData.NOTIFICATION.MESSAGE_MAX_LIMIT_PER_TRANS_2);
		
	}
	
	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp", "username", "passWeb" })
	@Test
	public void TC_03_DatVeXe_LonHon_HanMucToiDa_NhomDichVu(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt, String username, String passWeb) throws MalformedURLException {
		log.info("TC_03_Step_0: Click mo Dat ve xe");
		vehicleTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.ORDER_TICKET);
		vehicleTicket.clickToDynamicButton(DATA_ORDER_TICKET.AGREE);

		log.info("TC_03_Step_01: Chon va nhap diem di");
		vehicleTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.CHOOSE_FROM_PLACE);
		vehicleTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.PLACE_1, VehicalData.DATA_ORDER_TICKET.DESTINATION);
		vehicleTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_1);

		log.info("TC_03_Step_02: Chon va nhap diem den");
		vehicleTicket.clickToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.ARRIVAL);
		vehicleTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.PLACE_5, VehicalData.DATA_ORDER_TICKET.ARRIVAL);
		vehicleTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_5);

		log.info("TC_03_Step_03: Chon ngay muon di");
		vehicleTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.TOMORROW);

		log.info("TC_03_Step_4: Tim kiem chuyen di");
		vehicleTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.BUTTON_FIND_TRIP);

		log.info("TC_03_Step_5: click chọn gh2");
		vehicleTicket.clickToDynamicText("Chọn ghế");
		vehicleTicket.chooseSeats(2, "(255,255,255)");
		
		log.info("TC_03_Step_6: Lay so tien");
		money = convertAvailableBalanceCurrentcyOrFeeToLong(vehicleTicket.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTotalAmount"));
		
		log.info("TC_03_05_Dong App");
		driver.quit();
		
		log.info("TC_03_05_Mo browser va setup Han muc");
		driverWeb = openMultiBrowser(Constants.BE_BROWSER_CHROME, Constants.BE_BROWSER_VERSION, Constants.BE_URL);
		
		setupBE = WebPageFactoryManager.getWebBackendSetupPageObject(driverWeb);
		setupBE.Login_Web_Backend(driverWeb, username, passWeb);
		
		log.info("Setup assign services limit");
		setupBE.Setup_Assign_Services_Type_Limit(driverWeb, Constants.BE_CODE_PACKAGE, VehicalData.DATA_ORDER_TICKET.PAY_ORDER, money - 1 + "");
		
		log.info("TC_03_00_Mo lai App");
		
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		
		login = PageFactoryManager.getLoginPageObject(driver);

		login.Global_login(phone, pass, opt);

		vehicleTicket = PageFactoryManager.getVehicalPageObject(driver);
		
		log.info("TC_03_Step_0: Click mo Dat ve xe");
		vehicleTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.ORDER_TICKET);
		vehicleTicket.clickToDynamicButton(DATA_ORDER_TICKET.AGREE);

		log.info("TC_03_Step_01: Chon va nhap diem di");
		vehicleTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.CHOOSE_FROM_PLACE);
		vehicleTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.PLACE_1, VehicalData.DATA_ORDER_TICKET.DESTINATION);
		vehicleTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_1);

		log.info("TC_03_Step_02: Chon va nhap diem den");
		vehicleTicket.clickToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.ARRIVAL);
		vehicleTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.PLACE_5, VehicalData.DATA_ORDER_TICKET.ARRIVAL);
		vehicleTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_5);

		log.info("TC_03_Step_03: Chon ngay muon di");
		vehicleTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.TOMORROW);

		log.info("TC_03_Step_4: Tim kiem chuyen di");
		vehicleTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.BUTTON_FIND_TRIP);

		log.info("TC_03_Step_5: click chọn gh2");
		vehicleTicket.clickToDynamicText("Chọn ghế");
		vehicleTicket.chooseSeats(2, "(255,255,255)");
		
		log.info("TC_03_Step_10 : Dat chuyen di: ");
		vehicleTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.BOOK_SEAT);

		log.info("TC_03_Step_11 Click chon tiep tuc ");
		vehicleTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.CONTINUE_BUTTON);
		
		log.info("TC_03_Step_12 Click chon tu choi ");
		vehicleTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.BUTTON_CANCER);

		log.info("TC_03_Step_13 Input email");
		vehicleTicket.inputIntoEditTextByID(VehicalData.DATA_ORDER_TICKET.EMAIL_ADDRESS + randomNumber() + "@gmail.com", "com.VCB:id/email");

		log.info("TC_03_Step_14 Click btutton Thanh toan");
		vehicleTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.CONTINUE_BUTTON);
		vehicleTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.PAYMENT_BUTTON);

		log.info("TC_03_Step_15 Verify hien thi man hinh thong tin ve xe");
		verifyTrue(vehicleTicket.isDynamicMessageAndLabelTextDisplayed(VehicalData.DATA_ORDER_TICKET.INFO_TICKET));

		log.info("TC_03_Step_16 Lay thong tin tai khoan nguon");
		vehicleTicket.scrollUpToText(ReportTitle.SOURCE_ACCOUNT);
		vehicleTicket.clickToDynamicTextByID("com.VCB:id/number_account");
		sourceAccount = vehicleTicket.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, "VND");
		account = sourceAccount.account;

		log.info("TC_03_Step_17 Click button tiep tuc");
		vehicleTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.CONTINUE_BUTTON);
		
		log.info("TC_03_18_Kiem tra thong bao");
		verifyEquals(vehicleTicket.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), VehicalData.NOTIFICATION.MESSAGE_MAX_LIMIT_PER_GROUP_TRANS_1 + addCommasToLong((money - 1) + "") + " VND" +  VehicalData.NOTIFICATION.MESSAGE_MAX_LIMIT_PER_GROUP_TRANS_2);
		
	}
	
	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp", "username", "passWeb" })
	@Test
	public void TC_04_DatVeXe_LonHon_HanMucToiDa_GoiDichVu(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt, String username, String passWeb) throws MalformedURLException {
		log.info("TC_04_Step_0: Click mo Dat ve xe");
		vehicleTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.ORDER_TICKET);
		vehicleTicket.clickToDynamicButton(DATA_ORDER_TICKET.AGREE);

		log.info("TC_04_Step_01: Chon va nhap diem di");
		vehicleTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.CHOOSE_FROM_PLACE);
		vehicleTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.PLACE_1, VehicalData.DATA_ORDER_TICKET.DESTINATION);
		vehicleTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_1);

		log.info("TC_04_Step_02: Chon va nhap diem den");
		vehicleTicket.clickToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.ARRIVAL);
		vehicleTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.PLACE_5, VehicalData.DATA_ORDER_TICKET.ARRIVAL);
		vehicleTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_5);

		log.info("TC_04_Step_03: Chon ngay muon di");
		vehicleTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.TOMORROW);

		log.info("TC_04_Step_4: Tim kiem chuyen di");
		vehicleTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.BUTTON_FIND_TRIP);

		log.info("TC_04_Step_5: click chọn gh2");
		vehicleTicket.clickToDynamicText("Chọn ghế");
		vehicleTicket.chooseSeats(2, "(255,255,255)");
		
		log.info("TC_04_Step_6: Lay so tien");
		money = convertAvailableBalanceCurrentcyOrFeeToLong(vehicleTicket.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTotalAmount"));
		
		log.info("TC_04_05_Dong App");
		driver.quit();
		
		log.info("TC_04_05_Mo browser va setup Han muc");
		driverWeb = openMultiBrowser(Constants.BE_BROWSER_CHROME, Constants.BE_BROWSER_VERSION, Constants.BE_URL);
		
		setupBE = WebPageFactoryManager.getWebBackendSetupPageObject(driverWeb);
		setupBE.Login_Web_Backend(driverWeb, username, passWeb);
		
		log.info("Setup assign services limit");
		setupBE.Setup_Add_Method_Package_Total_Limit(driverWeb, Constants.BE_CODE_PACKAGE, "Method Otp", money - 1 + "");
		
		log.info("TC_04_00_Mo lai App");
		
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		
		login = PageFactoryManager.getLoginPageObject(driver);

		login.Global_login(phone, pass, opt);

		vehicleTicket = PageFactoryManager.getVehicalPageObject(driver);
		
		log.info("TC_04_Step_0: Click mo Dat ve xe");
		vehicleTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.ORDER_TICKET);
		vehicleTicket.clickToDynamicButton(DATA_ORDER_TICKET.AGREE);

		log.info("TC_04_Step_01: Chon va nhap diem di");
		vehicleTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.CHOOSE_FROM_PLACE);
		vehicleTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.PLACE_1, VehicalData.DATA_ORDER_TICKET.DESTINATION);
		vehicleTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_1);

		log.info("TC_04_Step_02: Chon va nhap diem den");
		vehicleTicket.clickToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.ARRIVAL);
		vehicleTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.PLACE_5, VehicalData.DATA_ORDER_TICKET.ARRIVAL);
		vehicleTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_5);

		log.info("TC_04_Step_03: Chon ngay muon di");
		vehicleTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.TOMORROW);

		log.info("TC_04_Step_4: Tim kiem chuyen di");
		vehicleTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.BUTTON_FIND_TRIP);

		log.info("TC_04_Step_5: click chọn gh2");
		vehicleTicket.clickToDynamicText("Chọn ghế");
		vehicleTicket.chooseSeats(2, "(255,255,255)");
		
		log.info("TC_04_Step_10 : Dat chuyen di: ");
		vehicleTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.BOOK_SEAT);

		log.info("TC_04_Step_11 Click chon tiep tuc ");
		vehicleTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.CONTINUE_BUTTON);
		
		log.info("TC_04_Step_12 Click chon tu choi ");
		vehicleTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.BUTTON_CANCER);

		log.info("TC_04_Step_13 Input email");
		vehicleTicket.inputIntoEditTextByID(VehicalData.DATA_ORDER_TICKET.EMAIL_ADDRESS + randomNumber() + "@gmail.com", "com.VCB:id/email");

		log.info("TC_04_Step_14 Click btutton Thanh toan");
		vehicleTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.CONTINUE_BUTTON);
		vehicleTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.PAYMENT_BUTTON);

		log.info("TC_04_Step_15 Verify hien thi man hinh thong tin ve xe");
		verifyTrue(vehicleTicket.isDynamicMessageAndLabelTextDisplayed(VehicalData.DATA_ORDER_TICKET.INFO_TICKET));

		log.info("TC_04_Step_16 Lay thong tin tai khoan nguon");
		vehicleTicket.scrollUpToText(ReportTitle.SOURCE_ACCOUNT);
		vehicleTicket.clickToDynamicTextByID("com.VCB:id/number_account");
		sourceAccount = vehicleTicket.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, "VND");
		account = sourceAccount.account;

		log.info("TC_04_Step_17 Click button tiep tuc");
		vehicleTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.CONTINUE_BUTTON);
		
		log.info("TC_04_18_Kiem tra thong bao");
		verifyEquals(vehicleTicket.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), VehicalData.NOTIFICATION.MESSAGE_MAX_LIMIT_PER_PACKAGE_TRANS_1 + addCommasToLong((money - 1) + "") + " VND" +  VehicalData.NOTIFICATION.MESSAGE_MAX_LIMIT_PER_PACKAGE_TRANS_2);
		
	}

	@AfterMethod(alwaysRun = true)
	public void afterClass() {
		closeApp();
		setupBE.Reset_Package_Total_Limit(driverWeb, Constants.BE_CODE_PACKAGE, "Method Otp");
		setupBE.Reset_Setup_Assign_Services_Type_Limit(driverWeb, Constants.BE_CODE_PACKAGE, VehicalData.DATA_ORDER_TICKET.PAY_ORDER);
		setupBE.Reset_Setup_Assign_Services_Limit(driverWeb, Constants.BE_CODE_PACKAGE, VehicalData.DATA_ORDER_TICKET.PAY_VEHICLE_TICKET);
		driverWeb.quit();
		service.stop();

	}

}
