package vnpay.vietcombank.landLinePhoneCharge;

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
import pageObjects.LandLinePhoneChargePageObject;
import pageObjects.LogInPageObject;
import pageObjects.WebBackendSetupPageObject;
import vietcombank_test_data.HomePage_Data.Home_Text_Elements;
import vietcombank_test_data.Internet_ADSL_Data;
import vietcombank_test_data.LandLinePhoneCharge_Data.Text_Data;

public class Limit_LandLinePhoneCharge_2 extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private WebBackendSetupPageObject webBackend;
	private LandLinePhoneChargePageObject landLinePhoneCharge;
	WebDriver driverWeb;
	List<String> notLine = new ArrayList<String>();
	SourceAccountModel sourceAccount = new SourceAccountModel();
	ServiceLimitInfo inputInfo = new ServiceLimitInfo("1000", "10000", "5000000", "5500000");
	long amount = 0;
	String phonenumber;

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp", "username", "passWeb" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt, String username, String passWeb) throws IOException, InterruptedException, GeneralSecurityException {
		log.info("Before class: Mo backend ");
		startServer();
		driverWeb = openMultiBrowser(Constants.BE_BROWSER_CHROME, Constants.BE_BROWSER_VERSION, Constants.BE_URL);
		webBackend = WebPageFactoryManager.getWebBackendSetupPageObject(driverWeb);
		webBackend.Login_Web_Backend(driverWeb, username, passWeb);

		webBackend.addMethod(driverWeb, "Thanh toán hóa đơn trả sau", inputInfo, Constants.BE_CODE_PACKAGE);

		startServer();
		log.info("Before class: Mo app ");
		if (deviceType.contains("android")) {
			driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		} else if (deviceType.contains("ios")) {
			driver = openIOSApp(deviceName, udid, url);
		}
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);

		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.scrollDownToText(driver, Home_Text_Elements.PRICE_CABLE);
		homePage.clickToDynamicButtonLinkOrLinkText(driver, Home_Text_Elements.LANDLINE_TELEPHONE);

		landLinePhoneCharge = PageFactoryManager.getLandLinePhoneChargePageObject(driver);
		notLine = Arrays.asList(getDataInCell(11).split(";"));
	}

	@Parameters({ "pass" })
	@Test
	public void TC_01_ThanhToanCuocDienThoaiCoDinhDuoiHanMucThapNhat(String pass) throws InterruptedException {
		log.info("TC_01_02_Chon tai khoan nguon");
		landLinePhoneCharge.clickToDynamicDropDown(driver, Text_Data.SOURCE_ACCOUNT);
		sourceAccount = landLinePhoneCharge.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		log.info("TC_01_03_Chon loai cuoc thanh toan");
		landLinePhoneCharge.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/wrap_tv");
		landLinePhoneCharge.clickToDynamicTextContains(driver, Text_Data.NOT_LINE);

		log.info("TC_01_04_Nhap so dien thoai tra cuoc va bam Tiep tuc");
		phonenumber = landLinePhoneCharge.inputPhoneNumberLandLinePhoneCharge(notLine);

		amount = convertAvailableBalanceCurrentcyOrFeeToLong(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, Text_Data.AMOUNT_PAY));
		log.info("TC_01_05_Kiem tra man hinh xac nhan thong tin");

		log.info("TC_01_Quay ve man hinh khoi tao");
		landLinePhoneCharge.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

		ServiceLimitInfo inputInfoMin = new ServiceLimitInfo("1000", (amount + 20) + "", (amount + 100) + "", (amount + 200) + "");

		webBackend.getInfoServiceLimit(driverWeb, "Thanh toán hóa đơn trả sau", inputInfoMin, Constants.BE_CODE_PACKAGE);

		log.info("TC_01_Click tiep tuc");
		landLinePhoneCharge.clickToDynamicContinue(driver, "com.VCB:id/btn_submit");

		log.info("TC_01_Step_verify message khi so tien chuyen nho hon han muc toi thieu ");
		verifyEquals(landLinePhoneCharge.getDynamicTextView(driver, "com.VCB:id/tvContent"), "Thanh toán hóa đơn không thành công. Số tiền giao dịch nhỏ hơn hạn mức " + addCommasToLong((amount + 20)+"") + " VND/1 lần, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp.");

		landLinePhoneCharge.clickToDynamicContinue(driver, "com.VCB:id/btOK");
		landLinePhoneCharge.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
		

	}

	@Parameters({ "pass" })
	@Test
	public void TC_02_ThanhToanCuocDienThoaiCoDinhCaoHonHanMucToiDa(String pass) throws InterruptedException {
		webBackend.resetAssignServicesLimit_All(driverWeb, "Thanh toán hóa đơn trả sau", Constants.BE_CODE_PACKAGE);
		homePage.clickToDynamicButtonLinkOrLinkText(driver, Home_Text_Elements.LANDLINE_TELEPHONE);

		log.info("TC_01_02_Chon tai khoan nguon");
		landLinePhoneCharge.clickToDynamicDropDown(driver, Text_Data.SOURCE_ACCOUNT);
		sourceAccount = landLinePhoneCharge.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		log.info("TC_01_03_Chon loai cuoc thanh toan");
		landLinePhoneCharge.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/wrap_tv");
		landLinePhoneCharge.clickToDynamicTextContains(driver, Text_Data.NOT_LINE);

		log.info("TC_01_04_Nhap so dien thoai tra cuoc va bam Tiep tuc");
		landLinePhoneCharge.inputIntoEditTextByID(driver, phonenumber, "com.VCB:id/code");

		ServiceLimitInfo inputInfoMax = new ServiceLimitInfo("1000", (amount - 20) + "", (amount - 10) + "", (amount + 200) + "");

		webBackend.getInfoServiceLimit(driverWeb, "Thanh toán hóa đơn trả sau", inputInfoMax, Constants.BE_CODE_PACKAGE);
		log.info("TC_01_Click tiep tuc");
		landLinePhoneCharge.clickToDynamicContinue(driver, "com.VCB:id/btn_submit");

		log.info("TC_01_Step_verify message khi so tien chuyen nho hon han muc toi thieu ");
		verifyEquals(landLinePhoneCharge.getDynamicTextView(driver, "com.VCB:id/tvContent"), "Thanh toán hóa đơn không thành công. Số tiền giao dịch lớn hơn hạn mức " + addCommasToLong((amount - 10)+"") + " VND/1 lần, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp.");

		landLinePhoneCharge.clickToDynamicContinue(driver, "com.VCB:id/btOK");
		landLinePhoneCharge.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
	

	}

	@Parameters({ "pass" })
	@Test
	public void TC_03_ThanhToanCuocDienThoaiCoDinhCaoHonHanMucNhom(String pass) throws InterruptedException {
		webBackend.resetAssignServicesLimit_All(driverWeb, "Thanh toán hóa đơn trả sau", Constants.BE_CODE_PACKAGE);
		homePage.clickToDynamicButtonLinkOrLinkText(driver, Home_Text_Elements.LANDLINE_TELEPHONE);

		log.info("TC_01_02_Chon tai khoan nguon");
		landLinePhoneCharge.clickToDynamicDropDown(driver, Text_Data.SOURCE_ACCOUNT);
		sourceAccount = landLinePhoneCharge.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		log.info("TC_01_03_Chon loai cuoc thanh toan");
		landLinePhoneCharge.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/wrap_tv");
		landLinePhoneCharge.clickToDynamicTextContains(driver, Text_Data.NOT_LINE);

		log.info("TC_01_04_Nhap so dien thoai tra cuoc va bam Tiep tuc");
		landLinePhoneCharge.inputIntoEditTextByID(driver, phonenumber, "com.VCB:id/code");

		log.info("TC_01_Quay ve man hinh khoi tao");
		webBackend.Setup_Assign_Services_Type_Limit(driverWeb, Constants.BE_CODE_PACKAGE, "Thanh toán hóa đơn" , (amount - 1000) + "");

		log.info("TC_01_Click tiep tuc");
		landLinePhoneCharge.clickToDynamicContinue(driver, "com.VCB:id/btn_submit");

		log.info("TC_01_Step_verify message khi so tien chuyen nho hon han muc toi thieu ");
		verifyEquals(landLinePhoneCharge.getDynamicTextView(driver, "com.VCB:id/tvContent"), "Thanh toán hóa đơn không thành công. Số tiền giao dịch lớn hơn hạn mức " + addCommasToLong((amount - 1000)+"") + " VND/1 ngày của nhóm dịch vụ, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp.");

		landLinePhoneCharge.clickToDynamicContinue(driver, "com.VCB:id/btOK");
		landLinePhoneCharge.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
	}

	@Parameters({ "pass" })
	@Test
	public void TC_04_ThanhToanCuocDienThoaiCoDinhCaoHonHanMucGoi(String pass) throws InterruptedException {
		webBackend.Reset_Setup_Assign_Services_Type_Limit(driverWeb, Constants.BE_CODE_PACKAGE, "Thanh toán hóa đơn");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, Home_Text_Elements.LANDLINE_TELEPHONE);

		log.info("TC_01_02_Chon tai khoan nguon");
		landLinePhoneCharge.clickToDynamicDropDown(driver, Text_Data.SOURCE_ACCOUNT);
		sourceAccount = landLinePhoneCharge.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		log.info("TC_01_03_Chon loai cuoc thanh toan");
		landLinePhoneCharge.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/wrap_tv");
		landLinePhoneCharge.clickToDynamicTextContains(driver, Text_Data.NOT_LINE);

		log.info("TC_01_04_Nhap so dien thoai tra cuoc va bam Tiep tuc");
		landLinePhoneCharge.inputIntoEditTextByID(driver, phonenumber, "com.VCB:id/code");

		log.info("TC_01_Quay ve man hinh khoi tao");
		webBackend.Setup_Add_Method_Package_Total_Limit(driverWeb, Constants.BE_CODE_PACKAGE, "Method Otp", (amount - 1000) + "");
		
		log.info("TC_01_Click tiep tuc");
		landLinePhoneCharge.clickToDynamicContinue(driver, "com.VCB:id/btn_submit");

		log.info("TC_01_Step_verify message khi so tien chuyen nho hon han muc toi thieu ");
		verifyEquals(landLinePhoneCharge.getDynamicTextView(driver, "com.VCB:id/tvContent"), "Thanh toán hóa đơn không thành công. Số tiền giao dịch lớn hơn hạn mức " + addCommasToLong((amount - 1000)+"") + " VND/1 ngày của gói dịch vụ, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp.");

		landLinePhoneCharge.clickToDynamicContinue(driver, "com.VCB:id/btOK");
		landLinePhoneCharge.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
		webBackend.Reset_Package_Total_Limit(driverWeb, Constants.BE_CODE_PACKAGE, "Method Otp");

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
//		service.stop();
	}

}
