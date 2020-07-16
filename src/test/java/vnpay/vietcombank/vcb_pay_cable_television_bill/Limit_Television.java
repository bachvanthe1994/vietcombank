package vnpay.vietcombank.vcb_pay_cable_television_bill;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
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
import pageObjects.LogInPageObject;
import pageObjects.PayBillTelevisionPageObject;
import pageObjects.TransactionReportPageObject;
import pageObjects.WebBackendSetupPageObject;
import vietcombank_test_data.Electric_Bills_Data.Data_Limit;
import vietcombank_test_data.LandLinePhoneCharge_Data.Text_Data;
import vietcombank_test_data.LuckyGift_Data.backendTitle;
import vietcombank_test_data.PayBillTelevison_Data.TitleData;

public class Limit_Television extends Base {
	AppiumDriver<MobileElement> driver;
	WebDriver driverWeb;
	private LogInPageObject login;
	private PayBillTelevisionPageObject billTelevision;
	private WebBackendSetupPageObject webBackend;
	private List<String> listViettel = new ArrayList<String>();
	private String customerID;

	SourceAccountModel sourceAccount = new SourceAccountModel();
	long amount = 0;
	ServiceLimitInfo inputInfo = new ServiceLimitInfo("1000", "100", "1500000000", "1600000000");

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp", "username", "passWeb"})
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt, String username, String passWeb) throws IOException, InterruptedException, GeneralSecurityException {
		startServer();
		log.info("Before class: Mo backend ");
		driverWeb = openMultiBrowser(Constants.BE_BROWSER_CHROME, Constants.BE_BROWSER_VERSION, Constants.BE_URL);
		webBackend = WebPageFactoryManager.getWebBackendSetupPageObject(driverWeb);
		webBackend.Login_Web_Backend(driverWeb, username, passWeb);
		
		log.info("Before class: setup limit ngay");
		webBackend.setupAssignServicesLimit(driverWeb, Constants.POSTPAID_MOBILE, inputInfo, backendTitle.PACKAGE_CODE);
		webBackend.Setup_Assign_Services_Type_Limit(driverWeb, backendTitle.PACKAGE_CODE, "Thanh toán hóa đơn", Data_Limit.TOTAL_SERVICE);
		webBackend.Setup_Add_Method_Package_Total_Limit(driverWeb, backendTitle.PACKAGE_CODE, Constants.METHOD_OTP, Data_Limit.PACKAGE_LIMIT_TRANSFER);
		
		log.info("Before class: Mo app ");
		if (deviceType.contains("android")) {
			driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		} else if (deviceType.contains("ios")) {
			driver = openIOSApp(deviceName, udid, url);
		}
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);

		billTelevision = PageFactoryManager.getPayBillTelevisionPageObject(driver);
		listViettel = Arrays.asList(getDataInCell(29).split(";"));
	}

	@Test
	public void TC_01_ThanhToanHoaDonTruyenHinhCapNhoHonHanMucGiaoDichNgay() throws InterruptedException {
		log.info("TC_01_STEP_0: chọn cước truyền hình cap");
		billTelevision.clickToDynamicButtonLinkOrLinkText(driver, TitleData.TITLE_TELEVISION);

		log.info("TC_01_STEP_1: chon tai khoan nguon");
		billTelevision.clickToTextID(driver, "com.VCB:id/number_account");
		sourceAccount = billTelevision.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, "VND");
		
		log.info("TC_01_STEP_2: nhap ma khach hang va click tiep tuc");
		customerID = billTelevision.inputCustomerId(listViettel);
		amount = convertAvailableBalanceCurrentcyOrFeeToLong(billTelevision.getDynamicTextInTransactionDetail(driver, Text_Data.AMOUNT_PAY));
		
		log.info("TC_01_Quay ve man hinh khoi tao");
		billTelevision.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
		
		ServiceLimitInfo inputInfoMin = new ServiceLimitInfo("1000", (amount + 20) + "", (amount + 100) + "", "10000000");

		webBackend.getInfoServiceLimit(driverWeb, Constants.POSTPAID_MOBILE, inputInfoMin, backendTitle.PACKAGE_CODE);

		log.info("TC_01_Click tiep tuc");
		billTelevision.clickToDynamicContinue(driver, "com.VCB:id/btn_submit");

		log.info("TC_01_Step_verify message khi so tien chuyen nho hon han muc toi thieu ");
		verifyEquals(billTelevision.getDynamicTextView(driver, "com.VCB:id/tvContent"), "Thanh toán hóa đơn không thành công. Số tiền giao dịch nhỏ hơn hạn mức " + addCommasToLong((amount + 20)+"") + " VND/1 lần, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp.");

		billTelevision.clickToDynamicContinue(driver, "com.VCB:id/btOK");
		billTelevision.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
		webBackend.resetAssignServicesLimit_All(driverWeb, Constants.POSTPAID_MOBILE, backendTitle.PACKAGE_CODE);
	}
	
	@Test
	public void TC_02_ThanhToanHoaDonTruyenHinhCapLonHonHanMucGiaoDichNgay() throws InterruptedException {
		log.info("TC_02_STEP_0: chọn cước truyền hình cap");
		billTelevision.scrollUpToText(driver, TitleData.TITLE_TELEVISION);
		billTelevision.clickToDynamicButtonLinkOrLinkText(driver, TitleData.TITLE_TELEVISION);

		log.info("TC_02_STEP_1: chon tai khoan nguon");
		billTelevision.clickToTextID(driver, "com.VCB:id/number_account");
		sourceAccount = billTelevision.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, "VND");
		
		log.info("TC_02_STEP_2: nhap ma khach hang va click tiep tuc");
		billTelevision.inputIntoEditTextByID(driver, customerID, "com.VCB:id/code");
		
		ServiceLimitInfo inputInfoMax = new ServiceLimitInfo("1000", (amount - 20) + "", (amount - 10) + "", "10000000");
		webBackend.getInfoServiceLimit(driverWeb, Constants.POSTPAID_MOBILE, inputInfoMax, backendTitle.PACKAGE_CODE);

		log.info("TC_02_Click tiep tuc");
		billTelevision.clickToDynamicContinue(driver, "com.VCB:id/btn_submit");

		log.info("TC_02_Step_verify message khi so tien chuyen nho hon han muc toi thieu ");
		verifyEquals(billTelevision.getDynamicTextView(driver, "com.VCB:id/tvContent"), "Thanh toán hóa đơn không thành công. Số tiền giao dịch lớn hơn hạn mức " + addCommasToLong((amount - 10)+"") + " VND/1 lần, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp.");

		billTelevision.clickToDynamicContinue(driver, "com.VCB:id/btOK");
		billTelevision.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
		webBackend.setupAssignServicesLimit(driverWeb, Constants.POSTPAID_MOBILE, inputInfo, backendTitle.PACKAGE_CODE);
	}
	
	@Test
	public void TC_03_ThanhToanHoaDonTruyenHinhCapLonHonNhomMucGiaoDichNgay() throws InterruptedException {
		log.info("TC_03_STEP_0: chọn cước truyền hình cap");
		billTelevision.clickToDynamicButtonLinkOrLinkText(driver, TitleData.TITLE_TELEVISION);

		log.info("TC_03_STEP_1: chon tai khoan nguon");
		billTelevision.clickToTextID(driver, "com.VCB:id/number_account");
		sourceAccount = billTelevision.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, "VND");
		
		log.info("TC_03_STEP_2: nhap ma khach hang va click tiep tuc");
		billTelevision.inputIntoEditTextByID(driver, customerID, "com.VCB:id/code");
		
		webBackend.Setup_Assign_Services_Type_Limit(driverWeb, backendTitle.PACKAGE_CODE, "Thanh toán hóa đơn" , (amount - 1000) + "");

		log.info("TC_03_Click tiep tuc");
		billTelevision.clickToDynamicContinue(driver, "com.VCB:id/btn_submit");

		log.info("TC_03_Step_verify message khi so tien chuyen nho hon han muc toi thieu ");
		verifyEquals(billTelevision.getDynamicTextView(driver, "com.VCB:id/tvContent"), "Thanh toán hóa đơn không thành công. Số tiền giao dịch lớn hơn hạn mức " + addCommasToLong((amount - 1000)+"") + " VND/1 ngày của nhóm dịch vụ, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp.");

		billTelevision.clickToDynamicContinue(driver, "com.VCB:id/btOK");
		billTelevision.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
		webBackend.resetAssignServicesLimit_All(driverWeb, Constants.POSTPAID_MOBILE, backendTitle.PACKAGE_CODE);
	}
	
	@Test
	public void TC_04_ThanhToanHoaDonTruyenHinhCapLonHonGoiMucGiaoDichNgay() throws InterruptedException {
		log.info("TC_04_STEP_0: chọn cước truyền hình cap");
		billTelevision.clickToDynamicButtonLinkOrLinkText(driver, TitleData.TITLE_TELEVISION);

		log.info("TC_04_STEP_1: chon tai khoan nguon");
		billTelevision.clickToTextID(driver, "com.VCB:id/number_account");
		sourceAccount = billTelevision.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, "VND");
		
		log.info("TC_04_STEP_2: nhap ma khach hang va click tiep tuc");
		billTelevision.inputIntoEditTextByID(driver, customerID, "com.VCB:id/code");
		
		webBackend.Setup_Add_Method_Package_Total_Limit(driverWeb, backendTitle.PACKAGE_CODE, Constants.METHOD_OTP, (amount - 1000) + "");

		log.info("TC_04_Click tiep tuc");
		billTelevision.clickToDynamicContinue(driver, "com.VCB:id/btn_submit");

		log.info("TC_04_Step_verify message khi so tien chuyen nho hon han muc toi thieu ");
		verifyEquals(billTelevision.getDynamicTextView(driver, "com.VCB:id/tvContent"), "Thanh toán hóa đơn không thành công. Số tiền giao dịch lớn hơn hạn mức " + addCommasToLong((amount - 1000)+"") + " VND/1 ngày của gói dịch vụ, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp.");

		billTelevision.clickToDynamicContinue(driver, "com.VCB:id/btOK");
		billTelevision.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
		webBackend.resetAssignServicesLimit_All(driverWeb, Constants.POSTPAID_MOBILE, backendTitle.PACKAGE_CODE);
	}

	@AfterMethod(alwaysRun = true)
	public void afterClass() throws IOException {
//		closeApp();
//		service.stop();
	}

}
