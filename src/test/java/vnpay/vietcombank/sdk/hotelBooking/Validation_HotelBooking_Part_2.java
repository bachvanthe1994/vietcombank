package vnpay.vietcombank.sdk.hotelBooking;

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
import pageObjects.sdk.hotelBooking.HotelBookingPageObject;

public class Validation_HotelBooking_Part_2 extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private HotelBookingPageObject hotelBooking;

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);

	}

	@Test
	public void TC_01_KiemTraBamVaoLocTheoGiaVaHangSao() {
		homePage = PageFactoryManager.getHomePageObject(driver);
		hotelBooking = PageFactoryManager.getHotelBookingPageObject(driver);

		log.info("TC_01_01_Click Dat phong khach san");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Đặt phòng khách sạn");

		log.info("TC_01_02_Click nut Dong y");
		hotelBooking.clickToDynamicButton(driver, "Đồng ý");

		log.info("TC_01_03_Click vao Loc theo gia va hang sao");
		hotelBooking.clickToDynamicButtonLinkOrLinkText(driver, "Lọc theo giá & hạng sao");

		log.info("TC_01_04_Kiem tra man hinh Loc theo gia va hang sao");
		verifyTrue(hotelBooking.isDynamicMessageAndLabelTextDisplayed(driver, "Xếp hạng sao"));
		verifyTrue(hotelBooking.checkStarRate());
		verifyTrue(hotelBooking.isDynamicMessageAndLabelTextDisplayed(driver, "Giá phòng"));
		verifyTrue(hotelBooking.isDynamicMessageAndLabelTextDisplayed(driver, "Xác nhận"));

	}

	@Test
	public void TC_02_KiemTraXepHangSao() {
		log.info("TC_02_01_Click chon hang sao");
		hotelBooking.chooseStarRateHotel(3);

		log.info("TC_02_02_Kiem tra hang sao duoc chon");
		verifyTrue(hotelBooking.checkSelectedStarRate(3));

		log.info("TC_02_03_Click chon lai hang sao");
		hotelBooking.chooseStarRateHotel(3);

		log.info("TC_02_04_Kiem tra bo chon hang sao");
		verifyFailure(hotelBooking.checkSelectedStarRate(3));

	}

	String actualMinPrice;
	String actualMaxPrice;

	@Test
	public void TC_03_KiemTraKeoChonMucGia() {
		log.info("TC_03_01_Click chon hang sao");
		hotelBooking.handleSeekBarPrice(6000000, 9999000, 12000000);

		log.info("TC_03_02_Kiem tra muc gia min duoc chon");
		actualMinPrice = hotelBooking.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFilterPriceMin");
		verifyTrue(Integer.parseInt(actualMinPrice.replaceAll("\\D+", "")) > 0);

		log.info("TC_03_03_Kiem tra muc gia max duoc chon");
		actualMaxPrice = hotelBooking.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFilterPriceMax");
		verifyTrue(Integer.parseInt(actualMaxPrice.replaceAll("\\D+", "")) > 9999000);

	}

	@Test
	public void TC_04_KiemTraNhanXacNhan() {
		log.info("TC_04_01_Click chon hang sao");
		hotelBooking.chooseStarRateHotel(3);

		log.info("TC_04_02_Click Xac nhan");
		hotelBooking.clickToDynamicButtonLinkOrLinkText(driver, "Xác nhận");

		log.info("TC_04_03_Kiem tra thong tin da chon");
		String actualStarAndPriceInfomation = hotelBooking.getTextRateAndPriceFilter();

		log.info("TC_04_03_01_Kiem tra gia Min da chon");
		verifyTrue(actualStarAndPriceInfomation.contains(actualMinPrice.split(" ")[0]));

		log.info("TC_04_03_02_Kiem tra gia Max da chon");
		verifyTrue(actualStarAndPriceInfomation.contains(actualMaxPrice.split(" ")[0]));

		log.info("TC_04_03_03_Kiem tra so Sao da chon");
		verifyTrue(actualStarAndPriceInfomation.contains("3"));

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
