package vnpay.vietcombank.location_QR_Code;

import java.io.IOException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import pageObjects.LocationQRCodePageObject;
import pageObjects.LogInPageObject;
import vietcombank_test_data.Location_QRCode_Data;

public class Location_QRCode_Flow extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private LocationQRCodePageObject QRCode;
	String passLogin = "";
	String transferTime;
	String transactionNumber;
	long amount, fee, amountStart, feeView, amountView, amountAfter = 0;
	String likeNumber, likeNumberActual, locatorActual, locatorExpect, shopName, typeShop, location, phone;
	int likeNumberExpect;

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
		login.Global_login(phone, pass, opt);
		passLogin = pass;

		QRCode = PageFactoryManager.getLocationQRCodePageObject(driver);

	}

	@Test
	public void TC_01_CheckTabXemGanDay() throws InterruptedException {
		log.info("TC_01: Click menu header");
		QRCode.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_01_Step: Click diem chap nhan thanh toan QRCode");
		QRCode.clickToDynamicButtonLinkOrLinkText(driver, Location_QRCode_Data.LOCATOR_PAYMENT_QR);

		log.info("TC_01_Step: Click cua hang");
		QRCode.clickToDynamicButtonLinkOrLinkText(driver, Location_QRCode_Data.RESTAURANT);
		QRCode.clickToDynamicBack(driver, "0", "2");

		log.info("TC_01_Step: get thong tin cua hang");
		shopName = QRCode.getDynamicTextPrecedingText(driver, Location_QRCode_Data.UNIT_ACCEPT_PAYMENT, "0");
		typeShop = QRCode.getDynamicTextPrecedingText(driver, Location_QRCode_Data.UNIT_ACCEPT_PAYMENT, "1");
		location = QRCode.getDynamicTextFollowingText(driver, Location_QRCode_Data.UNIT_ACCEPT_PAYMENT, "10");
		int likeNumberExpect = QRCode.getNumberLike();

		log.info("TC_01_Step: Click quay lai man hinh trang chu");
		QRCode.clickToTextImageView(driver, Location_QRCode_Data.DETAIL);
		QRCode.clickToTextImageView(driver, Location_QRCode_Data.RESTAURANT);

		log.info("TC_01_Step: Click tim kiem thong tin");
		QRCode.scrollUpToText(driver, Location_QRCode_Data.HIGH_LIGHT);
		QRCode.clickToDynamicTextContains(driver, Location_QRCode_Data.SEACHING_LOCATION);

		log.info("TC_01_Step: Click tab xem gan day");
		QRCode.clickToDynamicTextContains(driver, Location_QRCode_Data.VIEW_TEXT);

		log.info("TC_01_Step: verify loai cua hang");
		verifyEquals(QRCode.getDynamicTextBack(driver, "0", "2"), typeShop);

		log.info("TC_01_Step: verify dia chi");
		verifyEquals(QRCode.getDynamicTextBack(driver, "0", "3"), location);

		log.info("TC_01_Step: verify so luong like/dislike");
		verifyEquals(QRCode.getDynamicTextViewBack(driver, "0", "10"), likeNumberExpect + "");

	}

	@Test
	public void TC_02_CheckTabDaTimKiem() {
		log.info("TC_02_Step:Nhap dia chi tim kiem");
		QRCode.inputToDynamicInputBox(driver, Location_QRCode_Data.LOCATOR_SEARCH, Location_QRCode_Data.SEARCH_ADDRESS);

		log.info("TC_02_Step:nhan tim kiem");
		QRCode.clickToDynamicImageEdit(driver, Location_QRCode_Data.LOCATOR_SEARCH);

		log.info("TC_02_Step: Click chon mot cua hang");
		QRCode.clickToDynamicBack(driver, "0", "2");

		log.info("TC_01_Step: Click quay lai man hinh trang chu");
		QRCode.clickToTextImageView(driver, Location_QRCode_Data.DETAIL);
		QRCode.clickToTextEditText(driver, Location_QRCode_Data.LOCATOR_SEARCH);

		log.info("TC_02_Step: Click text tim kiem de den tab xem gan day");
		QRCode.scrollUpToText(driver, Location_QRCode_Data.HIGH_LIGHT);
		QRCode.clickToDynamicButtonLinkOrLinkText(driver, Location_QRCode_Data.SEARCH_ADDRESS);

		log.info("TC_02_Step: Click tab da tim");
		QRCode.clickToDynamicButtonLinkOrLinkText(driver, Location_QRCode_Data.SEACHED);

		log.info("TC_02_Step: verify text da tim kiem");
		verifyEquals(QRCode.getDynamicTextScrollText(driver, Location_QRCode_Data.LOVE, "0", "0"), Location_QRCode_Data.LOCATOR_SEARCH);
	}

	public void TC_03_HuyYeuThich() {
		QRCode.clickToDynamicButtonLinkOrLinkText(driver, Location_QRCode_Data.LOVE);
		QRCode.clickToDynamicImageNon(driver, "");
		
		shopName = QRCode.getDynamicTextPrecedingText(driver, Location_QRCode_Data.UNIT_ACCEPT_PAYMENT, "0");
		typeShop = QRCode.getDynamicTextPrecedingText(driver, Location_QRCode_Data.UNIT_ACCEPT_PAYMENT, "1");
		location = QRCode.getDynamicTextFollowingText(driver, Location_QRCode_Data.UNIT_ACCEPT_PAYMENT, "10");
		int likeNumberExpectNow = QRCode.getNumberLike();
		QRCode.clickToDynamicButtonLinkOrLinkText(driver, String.valueOf(likeNumberExpectNow));

		int likeNumberExpectDislike = QRCode.getNumberLike();

		verifyEquals(likeNumberExpectDislike, likeNumberExpectNow - 1);

	}
}
