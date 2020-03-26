package vnpay.vietcombank.shopping_online;

import java.io.IOException;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.TransactionReportPageObject;
import pageObjects.shopping_online.ShoppingOnlinePageObject;


public class Shopping_Online_Flow extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private ShoppingOnlinePageObject shopping;
	private TransactionReportPageObject transReport;
	private HomePageObject homePage;
	String passLogin = "";
	String transferTime;
	String transactionNumber;
	List<String> listActual;
	
	long amount, fee, amountStart, feeView, amountView, amountAfter = 0;

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
		shopping = PageFactoryManager.getShoppingOnlinePageObject(driver);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
		passLogin = pass;
		
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.scrollDownToText(driver, "Mua sắm trực tuyến");
		homePage.scrollIDownOneTime(driver);
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Mua sắm trực tuyến");
		homePage.waitForElementInvisible(driver, "android.widget.Image[@text='VNShop']");
	}

	@Test
	public void TC_01_ChonMuaMotSanPhamThanhToanOTP() {
		log.info("TC_07_Lay danh sach gia tri loai chuyen tien");
		shopping.clickChooseOrrder(1,"Bán chạy trong tháng");
	}

}
