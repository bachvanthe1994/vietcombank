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

		log.info("TC_01_Step_1: kiểm tra hiển thị màn hình home");
		homePage.scrollLeftToText(HomePage_Data.TEXT_BTN_SETTING);

		log.info("TC_01_Step_2: kiểm tra hiển thị màn hình home");
		homePage.clickToDynamicTextViewByText(HomePage_Data.TEXT_BTN_SETTING);

		log.info("TC_01_Step_3: Click vào màn hình ");
		homePage.clickToDynamicByText(HomePage_Data.GUID_MESSAGE);

		log.info("TC_01_Step_4: Kiểm tra hiển thị màn hình chức năng nổi bật ");
		verifyEquals("Chức năng nổi bật", homePage.getDynamicTextView(HomePage_Data.TITTLE_SETTING));

		log.info("TC_01_Step_5: Click bỏ nổi bật");
		homePage.clickToDynamicByText(HomePage_Data.SELECTED);

		homePage.scrollIDownOneTime(driver);
		log.info("TC_01_Step_6: Click thêm  nổi bật");
		homePage.clickToDynamicByText(HomePage_Data.NO_SELECT);

		homePage.scrollUpToText(driver, HomePage_Data.TITILE);

		List<String> listIconNoiBat = homePage.getListElements(HomePageUIs.DYNAMIC_TEXT_SELECTED, HomePage_Data.SELECTED);

		for (int i = 0; i <= listIconNoiBat.size() - 1; i++) {
			listIconNoiBatDaChon.add(listIconNoiBat.get(i));
			System.out.println("----------------" + listIconNoiBatDaChon.get(i));
		}

		log.info("TC_01_Step_4: Click btn cập nhật ");
		homePage.clickToDynamicButtonById("com.VCB:id/btnAddContact");

		log.info("TC_01_Step_5: Click btn đồng ý ");
		homePage.clickToDynamicButtonOK("com.VCB:id/btOK");

		log.info("TC_01_Step_9: Click thêm  nổi bật");
		homePage.clickToDynamicButtonImageBack("com.VCB:id/ivTitleLeft");

		homePage.scrollLeftToRight(DynamicPageUIs.DYNAMIC_QUICK_MENU);

		log.info("TC_01_Step_11: Lấy danh sách nổi bật ở màn hình home ");
		listIconNoiBatHome = homePage.getListElements(HomePageUIs.DYNAMIC_LIST_ICON, "com.VCB:id/title");

		log.info("TC_01_Step_12: Xác minh các mục đã nổi bật ");
		for (int i = 0; i < listIconNoiBat.size(); i++) {
			verifyTrue(listIconNoiBatDaChon.get(i).equals(listIconNoiBatHome.get(i)));
		}

	}

	@Test
	public void TC_02_XoaMucNoiBat() {
		log.info("TC_02_Step_1: kiểm tra hiển thị màn hình home");
		homePage.scrollLeftToText(HomePage_Data.TEXT_BTN_SETTING);

		log.info("TC_02_Step_2: kiểm tra hiển thị màn hình home");
		homePage.clickToDynamicTextViewByText(HomePage_Data.TEXT_BTN_SETTING);

		listIconNoiBatDaChon = homePage.getListElements(HomePageUIs.DYNAMIC_TEXT_SELECTED, HomePage_Data.SELECTED);

		log.info("TC_02_Step_4: Click bỏ nổi bật ");
		for (int i = 0; i < listIconNoiBatDaChon.size(); i++) {
			homePage.clickToDynamicByText(HomePage_Data.SELECTED);
		}

		log.info("TC_02_Step_5: Verify thông báo ");
		verifyEquals("Quý khách vui lòng chọn ít nhất 1 chức năng hiển thị.", homePage.getTextDynamicByID("com.VCB:id/tvContent"));

		log.info("TC_02_Step_6: Click button Close ");
		homePage.clickToDynamicButtonOK("com.VCB:id/btOK");

		log.info("TC_02_Step_6: Lấy danh sách đã chọn ");
		listIconNoiBatDaChon = homePage.getListElements(HomePageUIs.DYNAMIC_TEXT_SELECTED, HomePage_Data.SELECTED);
		System.out.println("=============" + listIconNoiBatDaChon.toString());

		log.info("TC_02_Step_7: Click btn cập nhật ");
		homePage.clickToDynamicButtonById("com.VCB:id/btnAddContact");

		log.info("TC_02_Step_8: Click btn đồng ý ");
		homePage.clickToDynamicButtonOK("com.VCB:id/btOK");

		log.info("TC_02_Step_9: Click thêm  nổi bật");
		homePage.clickToDynamicButtonImageBack("com.VCB:id/ivTitleLeft");

		log.info("TC_02_Step_10: Lấy danh sách nổi bật ở màn hình home ");
		listIconNoiBatHome = homePage.getListElements(HomePageUIs.DYNAMIC_LIST_ICON, "com.VCB:id/title");


		log.info("TC_02_Step_11: Verify chức năng nổi bật đã cập nhật");
		for (int i = 0; i < listIconNoiBatDaChon.size(); i++) {
			verifyTrue(listIconNoiBatDaChon.get(i).equals(listIconNoiBatHome.get(i)));
		}

	}

	@Test
	public void TC_03_ThemMucNoiBat() {
		log.info("TC_03_Step_1: kiểm tra hiển thị màn hình home");
		homePage.scrollLeftToText(HomePage_Data.TEXT_BTN_SETTING);

		log.info("TC_03_Step_2: kiểm tra hiển thị màn hình home");
		homePage.clickToDynamicTextViewByText(HomePage_Data.TEXT_BTN_SETTING);

		log.info("TC_03_Step_3: Thêm chức năng nổi bật");
		for (int i = 0; i <= 3; i++) {
			homePage.clickToDynamicByText(HomePage_Data.NO_SELECT);
			if (i == 1)
				homePage.scrollIDownOneTime(driver);
		}

		homePage.scrollUpToText(driver, HomePage_Data.TITILE);

		log.info("TC_03_Step_4: Click btn cập nhật");
		homePage.clickToDynamicButtonById("com.VCB:id/btnAddContact");

		log.info("TC_03_Step_5: Click btn đồng ý ");
		homePage.clickToDynamicButtonOK("com.VCB:id/btOK");

		log.info("TC_03_Step_6: Verify cập nhật tính năng nổi bật thành công ");
		verifyEquals(HomePage_Data.SUCCESS_MESSAGE, homePage.getTextDynamicByID("com.VCB:id/tvSuccess"));

		log.info("TC_03_Step_7: Lấy danh sách đã chọn nổi bật ");
		List<String> listIconNoiBatDaChon = homePage.getListElements(HomePageUIs.DYNAMIC_TEXT_SELECTED, HomePage_Data.SELECTED);

		log.info("TC_03_Step_8: Click thêm  nổi bật");
		homePage.clickToDynamicButtonImageBack("com.VCB:id/ivTitleLeft");

		log.info("TC_03_Step_9: Lấy danh sách đã chọn nổi bật hiển thị ở màn hình home ");
		listIconNoiBatHome = homePage.getListElements(HomePageUIs.DYNAMIC_LIST_ICON, "com.VCB:id/title");
		System.out.println("================" + listIconNoiBatHome);

		log.info("TC_03_Step_10: Verify chức năng đã chọn ");
		for (int i = 0; i < listIconNoiBatDaChon.size(); i++) {
			verifyTrue(listIconNoiBatDaChon.get(i).equals(listIconNoiBatHome.get(i)));
		}

	}

	@Test
	public void TC_04_XacMinhMucNoiBatVuotQuaChoPhep() {
		log.info("TC_04_Step_1: kiểm tra hiển thị màn hình home");
		homePage.scrollLeftToText(HomePage_Data.TEXT_BTN_SETTING);

		log.info("TC_04_Step_2: Click btn setting");
		homePage.clickToDynamicTextViewByText(HomePage_Data.TEXT_BTN_SETTING);

		log.info("TC_04_Step_3: Chọn thêm một chức năng muốn nổi bật");
		homePage.scrollIDownOneTime(driver);
		homePage.scrollIDownOneTime(driver);
		homePage.clickToDynamicByText(HomePage_Data.NO_SELECT);

		log.info("TC_04_Step_4: Xác minh hiển thị thông báo vượt quá");
		verifyEquals("Chức năng nổi bật chỉ lưu được tối đa 05 chức năng. Vui lòng kiểm tra lại.", homePage.getDynamicTextView(HomePage_Data.FAIL_MESSAGE));

		log.info("TC_04_Step_5: Click btn đóng");
		homePage.clickToDynamicButtonOK("com.VCB:id/btOK");
		
		log.info("TC_04_Step_6: Lấy danh sách đã chọn nổi bật ");
		homePage.scrollUpToText(driver, HomePage_Data.TITILE);
		List<String> listIconNoiBatDaChon = homePage.getListElements(HomePageUIs.DYNAMIC_TEXT_SELECTED, HomePage_Data.SELECTED);

		log.info("TC_04_Step_7: Click thêm  nổi bật");
		homePage.clickToDynamicButtonImageBack("com.VCB:id/ivTitleLeft");
		
		log.info("TC_04_Step_8: Lấy danh sách đã chọn nổi bật hiển thị ở màn hình home ");
		homePage.scrollLeftToRight(DynamicPageUIs.DYNAMIC_QUICK_MENU);
		listIconNoiBatHome = homePage.getListElements(HomePageUIs.DYNAMIC_LIST_ICON, "com.VCB:id/title");

		log.info("TC_04_Step_9: Verify chức năng đã chọn ");
		for (int i = 0; i < listIconNoiBatDaChon.size(); i++) {
			verifyTrue(listIconNoiBatDaChon.get(i).equals(listIconNoiBatHome.get(i)));
		}

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();

	}

}
