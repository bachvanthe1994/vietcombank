package vnpay.vietcombank.airticket;

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
import pageObjects.sdk.airTicketBooking.DynamicAirTicketBooking;
import vnpay.vietcombank.airticket.data.DomesticAirTicketBooking_Data;

public class DomesticAirTicketBooking_Validation_Part_1 extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private DynamicAirTicketBooking airTicket;
	private String tomorrowDay = getForWardDay(1);

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicIcon(driver, "Đặt vé máy bay");
		airTicket = PageFactoryManager.getDynamicAirTicketBooking(driver);
		airTicket.clickToDynamicTextOrButtonLink("Đặt vé máy bay Nội địa");
		airTicket.clickToDynamicButton("Đồng ý");
		airTicket.clickToDynamicTextOrButtonLink("Một chiều");

	}

	@Test
	public void TC_01_KiemTraDanhSachDiemKhoiHanh() {
		airTicket.clickToDynamicTextOrButtonLink("Khởi hành");
		verifyEquals(airTicket.getTextInDynamicTextBox("com.VCB:id/edtSearch"), "Chọn điểm khởi hành");
		verifyTrue(airTicket.isDynamicTextDisplayed("Điểm khởi hành gần đây"));
		verifyTrue(airTicket.isDynamicPlaceDisplayed("HAN", "Hà Nội"));

	}

	@Test
	public void TC_02_KiemTraChonDiemKhoiHanh() {
		airTicket.clickToDynamicTextOrButtonLink("Hà Nội");
		verifyEquals(airTicket.getTextInDynamicDropDownByLabel("Khởi hành", "com.VCB:id/tvValue"), "Hà Nội");
		airTicket.clickToDynamicTextOrButtonLink("Khởi hành");
		airTicket.clickToDynamicTextOrButtonLink("TP Hồ Chí Minh");
		verifyEquals(airTicket.getTextInDynamicDropDownByLabel("Khởi hành", "com.VCB:id/tvValue"), "TP Hồ Chí Minh");
		airTicket.clickToDynamicIcon("com.VCB:id/ivTitleLeft");
	}

	@Test
	public void TC_03_KiemTraKhongChonDiemKhoiHanh() {
		airTicket.clickToDynamicTextOrButtonLink("Đặt vé máy bay Nội địa");
		airTicket.clickToDynamicButton("Đồng ý");
		airTicket.clickToDynamicButton("Tìm chuyến bay");
		verifyEquals(airTicket.getTextInDynamicPopUp("com.VCB:id/tvTitle"), DomesticAirTicketBooking_Data.Message.EMPTY_DEPARTURE_POINT);
		airTicket.clickToDynamicButton("Đồng ý");
		airTicket.clickToDynamicIcon("com.VCB:id/ivTitleLeft");

	}

	@Test
	public void TC_04_KiemTrChonDiemDenKhiKhongChonDiemKhoiHanh() {
		airTicket.clickToDynamicTextOrButtonLink("Đặt vé máy bay Nội địa");
		airTicket.clickToDynamicButton("Đồng ý");
		airTicket.clickToDynamicTextOrButtonLink("Điểm đến");
		verifyEquals(airTicket.getTextInDynamicPopUp("com.VCB:id/tvTitle"), DomesticAirTicketBooking_Data.Message.EMPTY_DEPARTURE_POINT);
		airTicket.clickToDynamicButton("Đồng ý");

	}

	@Test
	public void TC_05_KiemTraDanhSachDiemDen() {
		airTicket.clickToDynamicTextOrButtonLink("Khởi hành");
		airTicket.clickToDynamicTextOrButtonLink("Hà Nội");
		airTicket.clickToDynamicTextOrButtonLink("Điểm đến");
		verifyEquals(airTicket.getTextInDynamicTextBox("com.VCB:id/edtSearch"), "Chọn điểm đến");
		verifyTrue(airTicket.isDynamicTextDisplayed("Điểm đến gần đây"));
		verifyTrue(airTicket.isDynamicPlaceDisplayed("SGN", "TP Hồ Chí Minh"));

	}

	@Test
	public void TC_06_KiemTraChonDiemDen() {
		airTicket.clickToDynamicTextOrButtonLink("TP Hồ Chí Minh");
		verifyEquals(airTicket.getTextInDynamicDropDownByLabel("Điểm đến", "com.VCB:id/tvValue"), "TP Hồ Chí Minh");
		airTicket.clickToDynamicTextOrButtonLink("Điểm đến");
		airTicket.clickToDynamicTextOrButtonLink("Đà Nẵng");
		verifyEquals(airTicket.getTextInDynamicDropDownByLabel("Điểm đến", "com.VCB:id/tvValue"), "Đà Nẵng");
		airTicket.clickToDynamicIcon("com.VCB:id/ivTitleLeft");
	}

	@Test
	public void TC_07_KiemTraKhongChonDiemDen() {
		airTicket.clickToDynamicTextOrButtonLink("Đặt vé máy bay Nội địa");
		airTicket.clickToDynamicButton("Đồng ý");
		airTicket.clickToDynamicTextOrButtonLink("Khởi hành");
		airTicket.clickToDynamicTextOrButtonLink("Hà Nội");
		airTicket.clickToDynamicButton("Tìm chuyến bay");
		verifyEquals(airTicket.getTextInDynamicPopUp("com.VCB:id/tvTitle"), DomesticAirTicketBooking_Data.Message.EMPTY_ARRIVE_POINT);
		airTicket.clickToDynamicButton("Đồng ý");
		airTicket.clickToDynamicIcon("com.VCB:id/ivTitleLeft");

	}

	@Test
	public void TC_08_KiemTraKhongChonNgayDi() {
		airTicket.clickToDynamicTextOrButtonLink("Đặt vé máy bay Nội địa");
		airTicket.clickToDynamicButton("Đồng ý");
		airTicket.clickToDynamicTextOrButtonLink("Khởi hành");
		airTicket.clickToDynamicTextOrButtonLink("Hà Nội");
		airTicket.clickToDynamicTextOrButtonLink("Điểm đến");
		airTicket.clickToDynamicTextOrButtonLink("TP Hồ Chí Minh");
		airTicket.clickToDynamicButton("Tìm chuyến bay");
		verifyEquals(airTicket.getTextInDynamicPopUp("com.VCB:id/tvTitle"), DomesticAirTicketBooking_Data.Message.EMPTY_TIME_FLIGHT);
		airTicket.clickToDynamicButton("Đồng ý");
		airTicket.clickToDynamicIcon("com.VCB:id/ivTitleLeft");

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
