package vnpay.vietcombank.sdk.sortUtility;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.annotations.Parameters;

import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import vietcombankUI.DynamicPageUIs;
import vietcombankUI.HomePageUIs;
import vietcombank_test_data.HomePage_Data;

import java.io.IOException;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

import commons.Base;
import commons.PageFactoryManager;

@Test
public class FlowSortUtility extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	List<String> listIconNoiBatHome = new ArrayList<>();
	List<String> listIconNoiBatDaChon = new ArrayList<>();
	LocalDate now = LocalDate.now();
	String today = convertDayOfWeekVietNamese2(getCurrentDayOfWeek(now)) + " " + getCurrentDay() + "/" + getCurrenMonth() + "/" + getCurrentYear();
	String tomorow = convertDayOfWeekVietNamese2(getCurrentDayOfWeek(now)) + " " + Integer.valueOf(getCurrentDay()) + 1 + "/" + getCurrenMonth() + "/" + getCurrentYear();

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
		homePage = PageFactoryManager.getHomePageObject(driver);
		login.Global_login(phone, pass, opt);

	}

	@Test
	public void TC_01_SuaMucNoiBat() {

		log.info("TC_01_Step_1: Scroll tu trai sang phai");
		homePage.scrollLeftToText(HomePageUIs.HomePageTexts.TEXT_BTN_SETTING);

		log.info("TC_01_Step_2: Click chon button tinh nang cai dat");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, HomePageUIs.HomePageTexts.TEXT_BTN_SETTING);

		log.info("TC_01_Step_3: Click chon vao phan huong dan cai dat noi bat");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, HomePageUIs.HomePageMessage.GUID_MESSAGE);

		log.info("TC_01_Step_4: Kiem tra hien thi man hinh cai dat noi bat ");
		verifyEquals(HomePageUIs.HomePageTexts.TITTLE_SETTING, homePage.getTextDynamicInSelectBox(driver, HomePageUIs.HomePageTexts.TITTLE_SETTING));

		log.info("TC_01_Step_5: Click chon bo noi bat");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, HomePageUIs.HomePageTexts.SELECTED);

		homePage.scrollIDownOneTime(driver);
		log.info("TC_01_Step_6: Click them tinh nang noi bat");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, HomePageUIs.HomePageTexts.NO_SELECT);

		homePage.scrollUpToText(driver, HomePageUIs.HomePageMessage.TITILE);

		List<String> listIconNoiBat = homePage.getTextInListElements(driver, HomePageUIs.HomePageElements.DYNAMIC_TEXT_SELECTED, HomePageUIs.HomePageTexts.SELECTED);

		for (int i = 0; i < listIconNoiBat.size(); i++) {
			listIconNoiBatDaChon.add(listIconNoiBat.get(i));
		}

		log.info("TC_01_Step_4: Click chon cap nhat ");
		homePage.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/btnAddContact");

		log.info("TC_01_Step_5: Click chon dong y");
		homePage.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

		log.info("TC_01_Step_9: Click them noi bat");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

		homePage.scrollLeftToRight(DynamicPageUIs.DYNAMIC_QUICK_MENU);

		log.info("TC_01_Step_11: Get toan bo danh sang cac icon noi bat o man hinh home ");
		listIconNoiBatHome = homePage.getTextInListElements(driver, DynamicPageUIs.DYNAMIC_TEXT_BY_ID, "com.VCB:id/title");
        listIconNoiBatHome.remove(listIconNoiBatHome.size()-1);
        
		log.info("TC_01_Step_12: Xac minh cac tinh nang noi bat");
		verifyEquals(listIconNoiBatDaChon, listIconNoiBatHome);

	}

	@Test
	public void TC_02_XoaMucNoiBat() {
		log.info("TC_02_Step_1: Scroll to icon setting");
		homePage.scrollLeftToText(HomePageUIs.HomePageTexts.TEXT_BTN_SETTING);

		log.info("TC_02_Step_2:  Click chon icon cai dat");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, HomePageUIs.HomePageTexts.TEXT_BTN_SETTING);

		listIconNoiBatDaChon = homePage.getTextInListElements(driver, HomePageUIs.HomePageElements.DYNAMIC_TEXT_SELECTED, HomePageUIs.HomePageTexts.SELECTED);

		log.info("TC_02_Step_4: Click bo chon noi bat ");
		for (int i = 0; i < listIconNoiBatDaChon.size(); i++) {
			homePage.clickToDynamicButtonLinkOrLinkText(driver, HomePageUIs.HomePageTexts.SELECTED);
		}

		log.info("TC_02_Step_5: Verify messege thong bao chon it hon 1 icon");
		verifyEquals(HomePageUIs.HomePageMessage.AT_LEAST_1_MESSAGE, homePage.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvContent"));

		log.info("TC_02_Step_6: Click button Close ");
		homePage.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

		log.info("TC_02_Step_6: Lay ra toan bo danh sach icon da chon ");
		listIconNoiBatDaChon = homePage.getTextInListElements(driver, HomePageUIs.HomePageElements.DYNAMIC_TEXT_SELECTED, HomePageUIs.HomePageTexts.SELECTED);

		log.info("TC_02_Step_7: Click button cap nhat ");
		homePage.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/btnAddContact");

		log.info("TC_02_Step_8: Click button dong y ");
		homePage.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

		log.info("TC_02_Step_9: Click them noi bat");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_02_Step_10: Lay ra danh sach icon noi bat o man hinh home ");
		listIconNoiBatHome = homePage.getTextInListElements(driver, DynamicPageUIs.DYNAMIC_TEXT_BY_ID, "com.VCB:id/title");
		listIconNoiBatHome.remove(listIconNoiBatHome.size() - 1);
		listIconNoiBatHome.remove(listIconNoiBatHome.size() - 1);

		log.info("TC_02_Step_11: Verify list cac icon noi bat da duoc cap nhat");
		verifyEquals(listIconNoiBatDaChon, listIconNoiBatHome);

	}

	@Test
	public void TC_03_ThemMucNoiBat() {
		log.info("TC_03_Step_1: Scroll toi icon cai dat tinh nang noi bat");
		homePage.scrollLeftToText(HomePageUIs.HomePageTexts.TEXT_BTN_SETTING);

		log.info("TC_03_Step_1: Click icon cai dat noi ba");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, HomePageUIs.HomePageTexts.TEXT_BTN_SETTING);

		log.info("TC_03_Step_3: Them tin nang noi bat");
		for (int i = 0; i <= 3; i++) {
			homePage.clickToDynamicButtonLinkOrLinkText(driver, HomePageUIs.HomePageTexts.NO_SELECT);
			if (i == 1)
				homePage.scrollIDownOneTime(driver);
		}

		homePage.scrollUpToText(driver, HomePageUIs.HomePageMessage.TITILE);

		log.info("TC_03_Step_4: Click btn cap nhat");
		homePage.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/btnAddContact");

		log.info("TC_03_Step_5: Click btn Dong y ");
		homePage.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

		log.info("TC_03_Step_6: Lay ra danh sach noi bat da chon ");
		List<String> listIconNoiBatDaChon = homePage.getTextInListElements(driver, HomePageUIs.HomePageElements.DYNAMIC_TEXT_SELECTED, HomePageUIs.HomePageTexts.SELECTED);

		log.info("TC_03_Step_7: Click them noi bat");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_03_Step_8: Lay ra danh sach icon noi bat o man hinh home");
		listIconNoiBatHome = homePage.getTextInListElements(driver, DynamicPageUIs.DYNAMIC_TEXT_BY_ID, "com.VCB:id/title");

		log.info("TC_03_Step_09: Verify icon da chon lam noi bat");
		verifyEquals(listIconNoiBatDaChon, listIconNoiBatHome);

	}

	@Test
	public void TC_04_XacMinhMucNoiBatVuotQuaChoPhep() {
		log.info("TC_04_Step_1: scroll den button icon setting");
		homePage.scrollLeftToText(HomePageUIs.HomePageTexts.TEXT_BTN_SETTING);

		log.info("TC_04_Step_2: Click button setting");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, HomePageUIs.HomePageTexts.TEXT_BTN_SETTING);

		log.info("TC_04_Step_3: Chon them tinh nang noi bat");
		homePage.scrollIDownOneTime(driver);
		homePage.scrollIDownOneTime(driver);
		homePage.clickToDynamicButtonLinkOrLinkText(driver, HomePageUIs.HomePageTexts.NO_SELECT);

		log.info("TC_04_Step_4: Verify hien thi thong bao chon qua 5 icon");
		verifyEquals(HomePageUIs.HomePageMessage.MAX_5_ICON_MESSAGE, homePage.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvContent"));

		log.info("TC_04_Step_5: Click button Dong popup");
		homePage.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

		log.info("TC_04_Step_6: Lay ra danh sach icon da chon noi bat ");
		homePage.scrollUpToText(driver, HomePageUIs.HomePageMessage.TITILE);
		List<String> listIconNoiBatDaChon = homePage.getTextInListElements(driver, HomePageUIs.HomePageElements.DYNAMIC_TEXT_SELECTED, HomePageUIs.HomePageTexts.SELECTED);

		log.info("TC_04_Step_7: Click button back");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_04_Step_8: Lay ra danh sach icon noi bat o man hinh home ");
		homePage.scrollLeftToRight(DynamicPageUIs.DYNAMIC_QUICK_MENU);
		listIconNoiBatHome = homePage.getTextInListElements(driver, DynamicPageUIs.DYNAMIC_TEXT_BY_ID, "com.VCB:id/title");

		log.info("TC_04_Step_9: Verify icon noi bat da chon ");
		verifyEquals(listIconNoiBatDaChon, listIconNoiBatHome);

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();

	}

}
