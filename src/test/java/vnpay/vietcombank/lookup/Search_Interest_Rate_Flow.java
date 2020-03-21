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
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.SearchPageObject;

public class Search_Interest_Rate_Flow extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject home;
	private SearchPageObject search;
	List<String> listActual;
	


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
		search =  PageFactoryManager.getSearchPageObject(driver);
		login.Global_login(phone, pass, opt);

	}
	@Test
	public void TC_01_LaiSuatTietKiemVND () {
		log.info("TC_01_Step: Click menu header");
		search.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");
		
		log.info("TC_01_Step: Click tra cuu");
		search.clickToDynamicButtonLinkOrLinkText(driver, "Tra cứu");
		
		log.info("TC_01_Step: Scroll xuong phan doi mat khau");
		search.scrollDownToText(driver, "Lãi suất");
		
		log.info("TC_01_Step: Click tra cuu lai suat");
		search.clickToDynamicButtonLinkOrLinkText(driver, "Lãi suất");
	
		log.info("TC_01_Step: Click button refresh de update time moi nhat");
		search.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivRefresh");
		
		log.info("TC_01_Step: get time moi refresh la thoi gian hien tai");
	//	System.out.print("Cập nhật lúc "+getCurrentDay());
	
		//verifyEquals(search.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvUpdateTime"), "Cập nhật lúc "+getCurrentDay());
		
		log.info("TC_01_Step: Click tab VND");
		search.clickToDynamicLinerLayoutID(driver, "com.VCB:id/llLeft");
		
		log.info("TC_01_Step: Lay danh sach ky han tien gui");
		listActual = search.getListOfSuggestedMoneyOrListText(driver, "com.VCB:id/tvKyhan");
	}
	
	@AfterClass(alwaysRun = true)
	public void afterClass() {
		service.stop();
	}

}
