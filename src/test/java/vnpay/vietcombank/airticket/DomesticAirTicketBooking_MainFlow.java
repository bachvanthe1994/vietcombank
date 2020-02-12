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
import pageObjects.sdk.airTicketBooking.DynamicAirTicketBookingObjects;

public class DomesticAirTicketBooking_MainFlow extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private DynamicAirTicketBookingObjects airTicket;
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

	}

	@Test
	public void TC_01_DatVeMayBayNoiDiaThanhCongVoiSoluongKhachToiThieuBang1() {
		airTicket.clickToDynamicTextOrButtonLink("Đặt vé máy bay Nội địa");
		airTicket.clickToDynamicButton("Đồng ý");
		airTicket.clickToDynamicTextOrButtonLink("Một chiều");
		airTicket.clickToDynamicTextOrButtonLink("Khởi hành");
		airTicket.clickToDynamicTextOrButtonLink("Hà Nội");
		airTicket.clickToDynamicTextOrButtonLink("Điểm đến");
		airTicket.clickToDynamicTextOrButtonLink("TP Hồ Chí Minh");
		airTicket.clickToDynamicTextOrButtonLink("Ngày đi");
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYear(), tomorrowDay);
		airTicket.clickToDynamicButton("Xác nhận");
		airTicket.clickToDynamicButton("Tìm chuyến bay");
		airTicket.clickToDynamicFlight(0);
		airTicket.clickToDynamicButton("Đặt vé");

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
