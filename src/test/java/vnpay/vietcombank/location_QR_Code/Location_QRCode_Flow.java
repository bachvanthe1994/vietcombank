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
import vietcombank_test_data.Shopping_Online;

public class Location_QRCode_Flow extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private LocationQRCodePageObject QRCode;
	String passLogin = "";
	String transferTime;
	String transactionNumber;
	long amount, fee, amountStart, feeView, amountView, amountAfter = 0;
	String likeNumber,likeNumberActual, locatorActual,locatorExpect;
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
	public void TC_01_CheckTabXemGanDay() {
		log.info("TC_01_Step: Click menu header");
		QRCode.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_01_Step: Click diem chap nhan thanh toan QRCode");
		QRCode.clickToDynamicButtonLinkOrLinkText(driver, "Điểm chấp nhận thanh toán QRCode");

		log.info("TC_01_Step: get dia chi cua hang");
		String locatorExpect = QRCode.getDynamicTextGroupView(driver, "Gợi ý dành cho Quý khách", "1", "2");
		
		log.info("TC_01_Step: Click chon 1 diem thanh toan");
		QRCode.clickToTextGroupView(driver, "Gợi ý dành cho Quý khách", "1", "2");

		log.info("TC_01_Step: get so luong nguoi like");
		 likeNumber = QRCode.getDynamicTextLike(driver, "Đang mở cửa", "4","1");
		 likeNumberExpect = Integer.parseInt(likeNumber) + 1;

		log.info("TC_01_Step: Click like cua hang");
		QRCode.clickToTextGroupLike(driver, "Đang mở cửa", "4","1");

		log.info("TC_01_Step: get so luong nguoi like luc sau");
		verifyEquals(QRCode.getDynamicTextLike(driver, "Đang mở cửa", "4","1"), likeNumberExpect+"");

		log.info("TC_01_Step: Click quay lai man hinh trang chu");
		QRCode.navigateBack(driver);

		log.info("TC_01_Step: Click text tim kiem de den tab xem gan day");
		QRCode.scrollUpToText(driver, "Nổi bật");
		QRCode.clickToDynamicButtonLinkOrLinkText(driver, "Tìm kiếm địa điểm...");

		log.info("TC_01_Step: Click tab xem gan day");
		QRCode.clickToDynamicButtonLinkOrLinkText(driver, "Xem gần đây");

		log.info("TC_01_Step: get dia chi cua hang");
		 locatorActual = QRCode.getDynamicTextScrollText(driver, "Yêu thích", "0", "3");
		
		log.info("TC_01_Step: get so luong nguoi like");
		 likeNumberActual = QRCode.getDynamicTextScrollText(driver, "Yêu thích", "10", "1");
		
		log.info("TC_01_Step: verify diem xem gan nhat la diem vua click");
		verifyEquals(locatorActual, locatorExpect);
		
		log.info("TC_01_Step: verify so luong luot like");
		
		System.out.print(likeNumberActual +"----------------");
		System.out.print(likeNumberExpect+1 +"----------------");
		verifyEquals(likeNumberActual, likeNumberExpect +"");
	}
	
	@Test
	public void TC_02_CheckTabDaTimKiem() {
		log.info("TC_02_Step:Nhap dia chi tim kiem");
		QRCode.inputToDynamicInputBox(driver, Shopping_Online.Valid_Account.LOCATOR_SEARCH, "Tìm kiếm địa điểm...");
		
		log.info("TC_02_Step:nhan tim kiem");
		QRCode.clickToDynamicImageEdit(driver, Shopping_Online.Valid_Account.LOCATOR_SEARCH);
		
		log.info("TC_02_Step: Click quay lai man hinh trang chu");
		QRCode.navigateBack(driver);
		QRCode.navigateBack(driver);
		
		log.info("TC_02_Step: Click text tim kiem de den tab xem gan day");
		QRCode.scrollUpToText(driver, "Nổi bật");
		QRCode.clickToDynamicButtonLinkOrLinkText(driver, "Tìm kiếm địa điểm...");

		log.info("TC_02_Step: Click tab da tim");
		QRCode.clickToDynamicButtonLinkOrLinkText(driver, "Đã tìm");
		
		log.info("TC_02_Step: verify text da tim kiem");
		verifyEquals(QRCode.getDynamicTextScrollText(driver, "Yêu thích","0","0"), Shopping_Online.Valid_Account.LOCATOR_SEARCH);
	}
	
	@Test
	public void TC_03_CheckTabYeuThich() {
		log.info("TC_02_Step: Click quay lai man hinh trang chu");
		QRCode.navigateBack(driver);
		QRCode.navigateBack(driver);
		
		log.info("TC_01_Step: Click diem chap nhan thanh toan QRCode");
		QRCode.clickToDynamicButtonLinkOrLinkText(driver, "Điểm chấp nhận thanh toán QRCode");
		
		log.info("TC_02_Step: Click text tim kiem de den tab xem gan day");
		QRCode.scrollUpToText(driver, "Nổi bật");
		QRCode.clickToDynamicButtonLinkOrLinkText(driver, "Tìm kiếm địa điểm...");
		
		log.info("TC_03_Step: Click tab yeu thich");
		QRCode.clickToDynamicButtonLinkOrLinkText(driver, "Yêu thích");
		
		log.info("TC_03_Step: get dia chi cua hang");
		 locatorActual = QRCode.getDynamicTextScrollText(driver, "Yêu thích", "0", "3");
		
		log.info("TC_03_Step: get so luong nguoi like");
		 likeNumberActual = QRCode.getDynamicTextScrollText(driver, "Yêu thích", "10", "1");
		
		log.info("TC_03_Step: verify diem xem gan nhat la diem vua click");
		verifyEquals(locatorActual, locatorExpect);
		
		log.info("TC_03_Step: verify so luong luot like");
		verifyEquals(likeNumberActual, likeNumberExpect +"");
	}
}
