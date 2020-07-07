package vnpay.vietcombank.postpaid_mobile_bills;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.Constants;
import commons.PageFactoryManager;
import commons.WebPageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import model.ServiceLimitInfo;
import model.SourceAccountModel;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.PostpaidMobileBillPageObject;
import pageObjects.WebBackendSetupPageObject;
import vietcombank_test_data.Postpaid_Mobile_Bill_Data;
import vietcombank_test_data.Postpaid_Mobile_Bill_Data.Title;
import vietcombank_test_data.Electric_Bills_Data.Data_Limit;
import vietcombank_test_data.Electric_Bills_Data.Electric_Text;
import vietcombank_test_data.LandLinePhoneCharge_Data.Text_Data;
import vietcombank_test_data.LuckyGift_Data.backendTitle;

public class Limit_PostPaid_Mobile_Bill extends Base {
	AppiumDriver<MobileElement> driver;
	WebDriver driverWeb;
	private LogInPageObject login;
	private HomePageObject home;
	private WebBackendSetupPageObject webBackend;
	private PostpaidMobileBillPageObject postpaidMobile;
	List<String> listViettel = new ArrayList<String>();
	SourceAccountModel sourceAccount = new SourceAccountModel();
	long amount = 0;
	ServiceLimitInfo inputInfo = new ServiceLimitInfo("1000", "100", "1500000000", "1600000000");
	private String mobilePhone;

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp", "username", "passWeb" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt, String username, String passWeb) throws IOException, InterruptedException, GeneralSecurityException {
		startServer();
		log.info("Before class: Mo backend ");
		driverWeb = openMultiBrowser(Constants.BE_BROWSER_CHROME, Constants.BE_BROWSER_VERSION, Constants.BE_URL);
		webBackend = WebPageFactoryManager.getWebBackendSetupPageObject(driverWeb);
		webBackend.Login_Web_Backend(driverWeb, username, passWeb);
		
		log.info("Before class: setup limit ngay");
		webBackend.setupAssignServicesLimit(driverWeb, Constants.POSTPAID_MOBILE, inputInfo, backendTitle.PACKAGE_CODE);
		webBackend.Setup_Assign_Services_Type_Limit(driverWeb, backendTitle.PACKAGE_CODE, Constants.PAY_BILL, Data_Limit.TOTAL_SERVICE);
		webBackend.Setup_Add_Method_Package_Total_Limit(driverWeb, backendTitle.PACKAGE_CODE, Constants.METHOD_OTP, Data_Limit.PACKAGE_LIMIT_TRANSFER);

		log.info("Before class: Mo app ");
		if (deviceType.contains("android")) {
			driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		} else if (deviceType.contains("ios")) {
			driver = openIOSApp(deviceName, udid, url);
		}
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
		
		home = PageFactoryManager.getHomePageObject(driver);
		login = PageFactoryManager.getLoginPageObject(driver);
		postpaidMobile = PageFactoryManager.getPostpaidMobileBillPageObject(driver);
		
		listViettel = Arrays.asList(getDataInCell(14).split(";"));

	}

	@Test
	public void TC_01_ChuyenTienNhoHonHanMucGiaoDich() throws InterruptedException {
		log.info("TC_01_Step_01: Click Cuoc di dong tra sau");
		home.clickToDynamicButtonLinkOrLinkText(driver, Title.POSTPAID_MOBILE_TITLE);

		log.info("TC_01_Step_03: Chon tai khoan nguon");
		postpaidMobile.clickToDynamicDropdownByHeader(driver, Electric_Text.SOURCE_ACCOUNT_TEXT, "1");
		sourceAccount = postpaidMobile.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, "VND");

		log.info("TC_01_Step_04: Chon nha cung cao Viettel");
		postpaidMobile.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/wrap_tv");
		postpaidMobile.clickToDynamicButtonLinkOrLinkText(driver, Postpaid_Mobile_Bill_Data.DATA.VIETTEL_SUPPLIER);

		log.info("TC_01_Step_05: Nhap so dien thoai va an tiep tuc");
		mobilePhone = postpaidMobile.inputPhoneNumberPostPaidMobile(listViettel);
		amount = convertAvailableBalanceCurrentcyOrFeeToLong(postpaidMobile.getDynamicTextInTransactionDetail(driver, Text_Data.AMOUNT_PAY));
		
		log.info("TC_01_Quay ve man hinh khoi tao");
		postpaidMobile.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
		
		ServiceLimitInfo inputInfoMin = new ServiceLimitInfo("1000", (amount + 20) + "", (amount + 100) + "", "10000000");

		webBackend.getInfoServiceLimit(driverWeb, Constants.POSTPAID_MOBILE, inputInfoMin, backendTitle.PACKAGE_CODE);

		log.info("TC_01_Click tiep tuc");
		postpaidMobile.clickToDynamicContinue(driver, "com.VCB:id/btn_submit");

		log.info("TC_01_Step_verify message khi so tien chuyen nho hon han muc toi thieu ");
		verifyEquals(postpaidMobile.getDynamicTextView(driver, "com.VCB:id/tvContent"), "Thanh toán hóa đơn không thành công. Số tiền giao dịch nhỏ hơn hạn mức " + addCommasToLong((amount + 20)+"") + " VND/1 lần, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp.");

		postpaidMobile.clickToDynamicContinue(driver, "com.VCB:id/btOK");
		postpaidMobile.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
		webBackend.resetAssignServicesLimit_All(driverWeb, Constants.POSTPAID_MOBILE, backendTitle.PACKAGE_CODE);
	}
	
	@Test
	public void TC_02_ChuyenTienLonHonHanMucGiaoDich() throws InterruptedException {
		log.info("TC_01_Step_01: Click Cuoc di dong tra sau");
		home.clickToDynamicButtonLinkOrLinkText(driver, Title.POSTPAID_MOBILE_TITLE);

		log.info("TC_01_Step_03: Chon tai khoan nguon");
		postpaidMobile.clickToDynamicDropdownByHeader(driver, Electric_Text.SOURCE_ACCOUNT_TEXT, "1");
		sourceAccount = postpaidMobile.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, "VND");

		log.info("TC_01_Step_04: Chon nha cung cao Viettel");
		postpaidMobile.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/wrap_tv");
		postpaidMobile.clickToDynamicButtonLinkOrLinkText(driver, Postpaid_Mobile_Bill_Data.DATA.VIETTEL_SUPPLIER);

		log.info("TC_01_Step_05: Nhap so dien thoai va an tiep tuc");
		postpaidMobile.inputIntoEditTextByID(driver, mobilePhone, "com.VCB:id/code");
		
		ServiceLimitInfo inputInfoMax = new ServiceLimitInfo("1000", (amount - 20) + "", (amount - 10) + "", "10000000");
		webBackend.getInfoServiceLimit(driverWeb, Constants.POSTPAID_MOBILE, inputInfoMax, backendTitle.PACKAGE_CODE);

		log.info("TC_02_Click tiep tuc");
		postpaidMobile.clickToDynamicContinue(driver, "com.VCB:id/btn_submit");

		log.info("TC_02_Step_verify message khi so tien chuyen nho hon han muc toi thieu ");
		verifyEquals(postpaidMobile.getDynamicTextView(driver, "com.VCB:id/tvContent"), "Thanh toán hóa đơn không thành công. Số tiền giao dịch lớn hơn hạn mức " + addCommasToLong((amount - 10)+"") + " VND/1 lần, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp.");

		postpaidMobile.clickToDynamicContinue(driver, "com.VCB:id/btOK");
		postpaidMobile.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
		webBackend.setupAssignServicesLimit(driverWeb, Constants.POSTPAID_MOBILE, inputInfo, backendTitle.PACKAGE_CODE);
	}
	
	@Test
	public void TC_03_ChuyenTienLonHonHanMucGiaoDichCuaNhom() throws InterruptedException {
		log.info("TC_03_Step_01: Click Cuoc di dong tra sau");
		home.clickToDynamicButtonLinkOrLinkText(driver, Title.POSTPAID_MOBILE_TITLE);

		log.info("TC_03_Step_03: Chon tai khoan nguon");
		postpaidMobile.clickToDynamicDropdownByHeader(driver, Electric_Text.SOURCE_ACCOUNT_TEXT, "1");
		sourceAccount = postpaidMobile.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, "VND");

		log.info("TC_03_Step_04: Chon nha cung cao Viettel");
		postpaidMobile.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/wrap_tv");
		postpaidMobile.clickToDynamicButtonLinkOrLinkText(driver, Postpaid_Mobile_Bill_Data.DATA.VIETTEL_SUPPLIER);

		log.info("TC_03_Step_05: Nhap so dien thoai va an tiep tuc");
		postpaidMobile.inputIntoEditTextByID(driver, mobilePhone, "com.VCB:id/code");
		
		webBackend.Setup_Assign_Services_Type_Limit(driverWeb, backendTitle.PACKAGE_CODE, Constants.PAY_BILL , (amount - 1000) + "");

		log.info("TC_03_Click tiep tuc");
		postpaidMobile.clickToDynamicContinue(driver, "com.VCB:id/btn_submit");

		log.info("TC_03_Step_verify message khi so tien chuyen nho hon han muc toi thieu ");
		verifyEquals(postpaidMobile.getDynamicTextView(driver, "com.VCB:id/tvContent"), "Thanh toán hóa đơn không thành công. Số tiền giao dịch lớn hơn hạn mức " + addCommasToLong((amount - 1000)+"") + " VND/1 ngày của nhóm dịch vụ, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp.");

		postpaidMobile.clickToDynamicContinue(driver, "com.VCB:id/btOK");
		postpaidMobile.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
		webBackend.resetAssignServicesLimit_All(driverWeb, Constants.POSTPAID_MOBILE, backendTitle.PACKAGE_CODE);
	}
	
	@Test
	public void TC_04_ChuyenTienLonHonHanMucGiaoDichCuaGoi() throws InterruptedException {
		log.info("TC_04_Step_01: Click Cuoc di dong tra sau");
		home.clickToDynamicButtonLinkOrLinkText(driver, Title.POSTPAID_MOBILE_TITLE);

		log.info("TC_04_Step_03: Chon tai khoan nguon");
		postpaidMobile.clickToDynamicDropdownByHeader(driver, Electric_Text.SOURCE_ACCOUNT_TEXT, "1");
		sourceAccount = postpaidMobile.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, "VND");

		log.info("TC_04_Step_04: Chon nha cung cao Viettel");
		postpaidMobile.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/wrap_tv");
		postpaidMobile.clickToDynamicButtonLinkOrLinkText(driver, Postpaid_Mobile_Bill_Data.DATA.VIETTEL_SUPPLIER);

		log.info("TC_04_Step_05: Nhap so dien thoai va an tiep tuc");
		postpaidMobile.inputIntoEditTextByID(driver, mobilePhone, "com.VCB:id/code");
		
		webBackend.Setup_Add_Method_Package_Total_Limit(driverWeb, backendTitle.PACKAGE_CODE, Constants.METHOD_OTP, (amount - 1000) + "");

		log.info("TC_04_Click tiep tuc");
		postpaidMobile.clickToDynamicContinue(driver, "com.VCB:id/btn_submit");

		log.info("TC_04_Step_verify message khi so tien chuyen nho hon han muc toi thieu ");
		verifyEquals(postpaidMobile.getDynamicTextView(driver, "com.VCB:id/tvContent"), "Thanh toán hóa đơn không thành công. Số tiền giao dịch lớn hơn hạn mức " + addCommasToLong((amount - 1000)+"") + " VND/1 ngày của gói dịch vụ, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp.");

		postpaidMobile.clickToDynamicContinue(driver, "com.VCB:id/btOK");
		postpaidMobile.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
		webBackend.resetAssignServicesLimit_All(driverWeb, Constants.POSTPAID_MOBILE, backendTitle.PACKAGE_CODE);
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();
	}

}
