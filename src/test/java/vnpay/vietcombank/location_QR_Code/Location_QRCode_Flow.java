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
	String likeNumber, likeNumberActual, locatorActual, locatorExpect, shopName,typeShop, location , phone;
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
	QRCode.clickToDynamicButtonLinkOrLinkText(driver, "Điểm chấp nhận thanh toán QRCode");

	log.info("TC_01_Step: Click cua hang");
	QRCode.clickToDynamicButtonLinkOrLinkText(driver, "Nhà hàng");
	QRCode.clickToDynamicBack(driver, "0","2");

	log.info("TC_01_Step: get thong tin cua hang"); 
	shopName = QRCode.getDynamicTextPrecedingText(driver, "Đơn vị chấp nhận thanh toán", "0");
	typeShop = QRCode.getDynamicTextPrecedingText(driver, "Đơn vị chấp nhận thanh toán", "1");
	location = QRCode.getDynamicTextFollowingText(driver, "Đơn vị chấp nhận thanh toán", "10");
	int likeNumberExpect = QRCode.getNumberLike();
	
	log.info("TC_01_Step: Click quay lai man hinh trang chu");
	QRCode.clickToTextImageView(driver, "Chi tiết");
	QRCode.clickToTextImageView(driver, "Nhà hàng");
	
	log.info("TC_01_Step: Click tim kiem thong tin");
	QRCode.scrollUpToText(driver, "Nổi bật");
	QRCode.clickToDynamicTextContains(driver, "Tìm kiếm địa điểm");
	
	log.info("TC_01_Step: Click tab xem gan day");
	QRCode.clickToDynamicTextContains(driver, "Xem gần");
	
	log.info("TC_01_Step: verify loai cua hang");
	verifyEquals(QRCode.getDynamicTextBack(driver, "0","2"), typeShop);
	
	log.info("TC_01_Step: verify dia chi");
	verifyEquals(QRCode.getDynamicTextBack(driver, "0","3"), location);
	
	log.info("TC_01_Step: verify so luong like/dislike");
	verifyEquals(QRCode.getDynamicTextViewBack(driver, "0","10"), likeNumberExpect +"");
	
}


@Test
public void TC_02_CheckTabDaTimKiem() {
	log.info("TC_02_Step:Nhap dia chi tim kiem");
	QRCode.inputToDynamicInputBox(driver, Shopping_Online.Valid_Account.LOCATOR_SEARCH, "Tìm kiếm địa điểm...");

	log.info("TC_02_Step:nhan tim kiem");
	QRCode.clickToDynamicImageEdit(driver, Shopping_Online.Valid_Account.LOCATOR_SEARCH);

	log.info("TC_02_Step: Click chon mot cua hang");
	QRCode.clickToDynamicBack(driver, "0","2");
	
	log.info("TC_01_Step: Click quay lai man hinh trang chu");
	QRCode.clickToTextImageView(driver, "Chi tiết");
	QRCode.clickToTextEditText(driver, Shopping_Online.Valid_Account.LOCATOR_SEARCH);
	
	log.info("TC_02_Step: Click text tim kiem de den tab xem gan day");
	QRCode.scrollUpToText(driver, "Nổi bật");
	QRCode.clickToDynamicButtonLinkOrLinkText(driver, "Tìm kiếm địa điểm...");

	log.info("TC_02_Step: Click tab da tim");
	QRCode.clickToDynamicButtonLinkOrLinkText(driver, "Đã tìm");

	log.info("TC_02_Step: verify text da tim kiem");
	verifyEquals(QRCode.getDynamicTextScrollText(driver, "Yêu thích", "0", "0"), Shopping_Online.Valid_Account.LOCATOR_SEARCH);

}

}