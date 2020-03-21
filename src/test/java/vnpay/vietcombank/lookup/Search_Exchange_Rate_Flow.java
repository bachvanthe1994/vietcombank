package vnpay.vietcombank.lookup;

import java.io.IOException;

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
import vietcombank_test_data.Search_Data;

public class Search_Exchange_Rate_Flow extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject home;
	private SearchPageObject search;

	private String buyExchange, sellExchange, convertUnit, exchangedMoney, resultMoney;

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone",
			"pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities,
			String appPackage, String appName, String phone, String pass, String opt)
			throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		if (deviceType.contains("android")) {
			driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		} else if (deviceType.contains("ios")) {
			driver = openIOSApp(deviceName, udid, url);
		}
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);

	}

	@Test
	public void TC_01_TraCuuTyGia() {

		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_01_Step_01: Chon tab Menu");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_5");

		log.info("TC_01_Step_02: Mo sub-menu Tra cuu");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Tra cứu");

		log.info("TC_01_Step_03: An vao phan Ty gia");
		home.scrollDownToText(driver, "Tỷ giá");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Tỷ giá");

		log.info("TC_01_Step_04: Hien thi man hinh Tra Cuu ty gia ngoai te");
		search = PageFactoryManager.getSearchPageObject(driver);
		verifyEquals(search.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"),
				Search_Data.VALIDATE.EXCHANGE_RATE_TITLE);

		log.info("TC_01_Step_05: Xac nhan tab Ty gia duoc highlight");
		verifyEquals(search.isDynamicValuesFocus(driver, "Tỷ giá"), true);

		log.info("TC_01_Step_06: Click button Refresh");
		search.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/icRefresh");

		log.info("TC_01_Step_07: Chon Ty gia USD");
		search.scrollDownToText(driver, "USD");

		log.info("TC_01_Step_08: Lay Ty gia Mua");
		buyExchange = search.getTextDynamicFollowTextId(driver, "USD", "com.VCB:id/buy_text");

		log.info("TC_01_Step_08: Lay Ty gia Ban");
		sellExchange = search.getTextDynamicFollowTextId(driver, "USD", "com.VCB:id/sell_text");
		sellExchange = sellExchange.replace(",", "");
		sellExchange = sellExchange.replace(".00", "");

		log.info("TC_01_Step_09: Chon tap Quy doi");
		search.clickToTextID(driver, "com.VCB:id/tv_transaction_details_tvTabLogs");

		log.info("TC_01_Step_11: Xac nhan tab Quy doi duoc highlight");
		verifyEquals(search.isDynamicValuesFocus(driver, "Quy đổi"), true);

		log.info("TC_01_Step_11: Chon Ngoai te quy doi la USD");
		search.clickToTextID(driver, "com.VCB:id/fromTV");
		search.clickToDynamicButtonLinkOrLinkText(driver, "USD");

		log.info("TC_01_Step_12: Chon Ngoai te muon quy doi la VND");
		search.clickToTextID(driver, "com.VCB:id/toTV");
		search.clickToDynamicButtonLinkOrLinkText(driver, "VND");

		log.info("TC_01_Step_13: Xac nhan hien thi dung ty gia mua quy doi");
		convertUnit = search.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/convert_unit_text");
		convertUnit = getSplitStringIndex(convertUnit, "=", 1);
		System.out.println(convertUnit);
		buyExchange = buyExchange.replace(",", "");
		buyExchange = buyExchange.replace(".00", "");
		System.out.println(buyExchange);
		verifyEquals(convertUnit, buyExchange);

		log.info("TC_01_Step_13: Nhap so tien muon quy doi");
		search.inputIntoEditTextByID(driver, Search_Data.DATA.USD_MONEY, "com.VCB:id/from_text");

		log.info("TC_01_Step_14: Xac nhan hien thi dung so tien duoc quy doi");
		exchangedMoney = search.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/to_text");
		resultMoney = convertEURO_USDToVNDMoney(Search_Data.DATA.USD_MONEY, buyExchange);
		verifyEquals(exchangedMoney, resultMoney);

		log.info("TC_01_Step_15: An nut chuyen doi ngoai te");
		search.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/converter_button");

		log.info("TC_01_Step_16: Nhap so tien muon quy doi");
		search.inputIntoEditTextByID(driver, Search_Data.DATA.VND_MONEY, "com.VCB:id/from_text");

		log.info("TC_01_Step_17: Xac nhan hien thi dung so tien duoc quy doi");
		exchangedMoney = search.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/to_text");
		resultMoney = convertVNDToEUROOrUSD(Search_Data.DATA.VND_MONEY, sellExchange);
		verifyEquals(exchangedMoney, resultMoney);
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		service.stop();
	}

}
