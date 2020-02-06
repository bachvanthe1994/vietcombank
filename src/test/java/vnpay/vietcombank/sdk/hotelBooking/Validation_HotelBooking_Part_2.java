package vnpay.vietcombank.sdk.hotelBooking;

import java.io.IOException;
import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import model.HotelBookingInfo;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.sdk.hotelBooking.HotelBookingPageObject;
import vietcombank_test_data.LogIn_Data;

public class Validation_HotelBooking_Part_2 extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private HotelBookingPageObject hotelBooking;
	

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName" })

	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);

		login = PageFactoryManager.getLoginPageObject(driver);

		login.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");

		login.inputToDynamicLogInTextBox(driver, LogIn_Data.Login_Account.PHONE, "Tiếp tục");

		login.clickToDynamicButton(driver, "Tiếp tục");

		login.inputToDynamicInputBox(driver, LogIn_Data.Login_Account.NEW_PASSWORD, LogIn_Data.UI.PASSWORD_LABEL);

		login.clickToDynamicButton(driver, "Tiếp tục");

		login.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		login.clickToDynamicButton(driver, "Tiếp tục");

		login.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");
		
	}

	@Test
	public void TC_01_DatPhongKhachSan_KiemTraBamVaoLocTheoGiaVaHangSao() {
		homePage = PageFactoryManager.getHomePageObject(driver);
		hotelBooking = PageFactoryManager.getHotelBookingPageObject(driver);

		log.info("TC_01_01_Click Dat phong khach san");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Đặt phòng khách sạn");
		
		log.info("TC_01_02_Click nut Dong y");
		hotelBooking.clickToDynamicButton(driver, "Đồng ý");
		
//		log.info("TC_01_03_Click vao Loc theo gia va hang sao");
//		hotelBooking.clickToDynamicButtonLinkOrLinkText(driver, "Lọc theo giá & hạng sao");
//		
//		log.info("TC_01_04_Kiem tra man hinh Loc theo gia va hang sao");
//		verifyTrue(hotelBooking.isDynamicMessageAndLabelTextDisplayed(driver, "Xếp hạng sao"));
//		verifyTrue(hotelBooking.checkStarRate());
//		verifyTrue(hotelBooking.isDynamicMessageAndLabelTextDisplayed(driver, "Giá phòng"));
//		verifyTrue(hotelBooking.isDynamicMessageAndLabelTextDisplayed(driver, "Xác nhận"));
		
		hotelBooking.clickToDynamicButtonLinkOrLinkText(driver, "Xem gần đây");
		List<HotelBookingInfo> list = hotelBooking.getListHotelRecentView();

	}
	
//	@Test
	public void TC_02_DatPhongKhachSan_KiemTraXepHangSao() {
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
//	@Test
	public void TC_03_DatPhongKhachSan_KiemTraKeoChonMucGia() {
		log.info("TC_03_01_Click chon hang sao");
		hotelBooking.handleSeekBarPrice(6000000, 9999000, 12000000);
		
		log.info("TC_03_02_Kiem tra muc gia min duoc chon");
		actualMinPrice = hotelBooking.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFilterPriceMin");
		verifyTrue(Integer.parseInt(actualMinPrice.replaceAll("\\D+","")) > 0);
		
		log.info("TC_03_03_Kiem tra muc gia max duoc chon");
		actualMaxPrice = hotelBooking.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFilterPriceMax");
		verifyTrue(Integer.parseInt(actualMaxPrice.replaceAll("\\D+","")) > 9999000);
		
	}
	
//	@Test
	public void TC_04_DatPhongKhachSan_KiemTraNhanXacNhan() {
		log.info("TC_04_01_Click chon hang sao");
		hotelBooking.chooseStarRateHotel(3);
		
		log.info("TC_04_02_Click Xac nhan");
		hotelBooking.clickToDynamicButtonLinkOrLinkText(driver, "Xác nhận");
		
		log.info("TC_04_03_Kiem tra thong tin da chon");
		String actualStarAndPriceInfomation =  hotelBooking.getTextRateAndPriceFilter();
		
		log.info("TC_04_03_01_Kiem tra gia Min da chon");
		verifyTrue(actualStarAndPriceInfomation.contains(actualMinPrice.split(" ")[0]));
		
		log.info("TC_04_03_02_Kiem tra gia Max da chon");
		verifyTrue(actualStarAndPriceInfomation.contains(actualMaxPrice.split(" ")[0]));
		
		log.info("TC_04_03_03_Kiem tra so Sao da chon");
		verifyTrue(actualStarAndPriceInfomation.contains("3"));
		
	}
	
//	@Test
	public void TC_05_DatPhongKhachSan_KiemTraNutXemGanDay() {
		log.info("TC_04_01_Click chon hang sao");
		hotelBooking.chooseStarRateHotel(3);
		
		log.info("TC_04_02_Click Xac nhan");
		hotelBooking.clickToDynamicButtonLinkOrLinkText(driver, "Xác nhận");
		
		log.info("TC_04_03_Kiem tra thong tin da chon");
		String actualStarAndPriceInfomation =  hotelBooking.getTextRateAndPriceFilter();
		
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
