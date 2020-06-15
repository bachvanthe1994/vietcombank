package vnpay.vietcombank.lookup;

import java.io.IOException;
import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import pageObjects.LogInPageObject;
import pageObjects.SearchPageObject;
import vietcombank_test_data.HomePage_Data.Home_Text_Elements;
import vietcombank_test_data.Search_Data.TITTLE;

public class Search_Interest_Rate_Flow extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private SearchPageObject search;
	List<String> listActualVND;
	List<String> listActualUSD;
	List<String> listActualEUR;

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		if (deviceType.contains("android")) {
			driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		} else if (deviceType.contains("ios")) {
			driver = openIOSApp(deviceName, udid, url);
		}
		login = PageFactoryManager.getLoginPageObject(driver);
		search = PageFactoryManager.getSearchPageObject(driver);
		login.Global_login(phone, pass, opt);

	}

	@Test
	public void TC_01_LaiSuatTietKiem() {
		log.info("TC_01_Step: Click menu header");
		search.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_01_Step: Click tra cuu");
		search.clickToDynamicButtonLinkOrLinkText(driver, Home_Text_Elements.LOOK_UP);

		log.info("TC_01_Step: Scroll xuong phan doi mat khau");
		search.scrollIDownOneTime(driver);

		log.info("TC_01_Step: Click tra cuu lai suat");
		search.clickToDynamicButtonLinkOrLinkText(driver, TITTLE.INTEREST_RATE);

		log.info("TC_01_Step: Click button refresh de update time moi nhat");
		search.clickToDynamicImageViewByID(driver, "com.VCB:id/ivRefresh");

		log.info("TC_01_Step: get time moi refresh la thoi gian hien tai");
		String timeUpdate = search.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvUpdateTime").split(" ")[5];

		log.info("TC_01_Step: verify ngay cap nhat");
		verifyEquals(timeUpdate, getBackwardDate(0));

		log.info("TC_01_Step: Click tab VND");
		search.clickToDynamicLinerLayoutID(driver, "com.VCB:id/llLeft");

		log.info("TC_01_Step: Lay danh sach ky han tien gui");
		listActualVND = search.getListOfSuggestedMoneyOrListText(driver, "com.VCB:id/tvKyhan");

		log.info("TC_01_Step: Click tab USD");
		search.clickToDynamicLinerLayoutID(driver, "com.VCB:id/llCenter");

		log.info("TC_01_Step: Lay danh sach ky han tien gui");
		listActualUSD = search.getListOfSuggestedMoneyOrListText(driver, "com.VCB:id/tvKyhan");

		log.info("TC_01_Step: Verify ky han tien gui VND va USD");
		verifyEquals(listActualVND, listActualUSD);

		log.info("TC_01_Step: Click tab EUR");
		search.clickToDynamicLinerLayoutID(driver, "com.VCB:id/llRight");

		log.info("TC_01_Step: Lay danh sach ky han tien gui");
		listActualEUR = search.getListOfSuggestedMoneyOrListText(driver, "com.VCB:id/tvKyhan");

		log.info("TC_01_Step: Verify ky han tien gui VND va EUR");
		verifyEquals(listActualVND, listActualEUR);
	}

	@Test
	public void TC_02_LaiSuatTienGuiCoKyHan() {
		log.info("TC_02_Step: Click tra cuu lai suat");
		search.clickToDynamicButtonLinkOrLinkText(driver, TITTLE.TERM_DEPOSITS);

		log.info("TC_02_Step: Click button refresh de update time moi nhat");
		search.clickToDynamicImageViewByID(driver, "com.VCB:id/ivRefresh");

		log.info("TC_02_Step: get time moi refresh la thoi gian hien tai");
		String timeUpdate = search.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvUpdateTime").split(" ")[5];

		log.info("TC_02_Step: verify ngay cap nhat");
		verifyEquals(timeUpdate, getBackwardDate(0));

		log.info("TC_02_Step: Click tab VND");
		search.clickToDynamicLinerLayoutID(driver, "com.VCB:id/llLeft");

		log.info("TC_02_Step: Lay danh sach ky han tien gui");
		listActualVND = search.getListOfSuggestedMoneyOrListText(driver, "com.VCB:id/tvKyhan");

		log.info("TC_02_Step: Click tab USD");
		search.clickToDynamicLinerLayoutID(driver, "com.VCB:id/llCenter");

		log.info("TC_02_Step: Lay danh sach ky han tien gui");
		listActualUSD = search.getListOfSuggestedMoneyOrListText(driver, "com.VCB:id/tvKyhan");

		log.info("TC_02_Step: Verify ky han tien gui VND va USD");
		verifyEquals(listActualVND, listActualUSD);

		log.info("TC_02_Step: Click tab EUR");
		search.clickToDynamicLinerLayoutID(driver, "com.VCB:id/llRight");

		log.info("TC_02_Step: Lay danh sach ky han tien gui");
		listActualEUR = search.getListOfSuggestedMoneyOrListText(driver, "com.VCB:id/tvKyhan");

		log.info("TC_02_Step: Verify ky han tien gui VND va EUR");
		verifyEquals(listActualVND, listActualEUR);
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		service.stop();
	}

}
