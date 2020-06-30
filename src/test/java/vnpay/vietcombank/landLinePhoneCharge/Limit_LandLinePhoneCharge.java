package vnpay.vietcombank.landLinePhoneCharge;

import java.io.IOException;

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
import pageObjects.HomePageObject;
import pageObjects.LandLinePhoneChargePageObject;
import pageObjects.LogInPageObject;
import pageObjects.TransactionReportPageObject;
import pageObjects.WebBackendSetupPageObject;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.LandLinePhoneCharge_Data;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransferMoneyInVCB_Data.TittleData;

public class Limit_LandLinePhoneCharge extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private WebBackendSetupPageObject loginWeb;
	private LandLinePhoneChargePageObject landLinePhoneCharge;
	WebDriver driverWeb;

	String password = "";
	ServiceLimitInfo inputInfo = new ServiceLimitInfo("1000", "10000", "1000000", "1000000");

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt, String username, String passWeb) throws IOException, InterruptedException {
		log.info("Before class: Mo backend ");
		startServer();
		driverWeb = openMultiBrowser(Constants.BE_BROWSER_CHROME, Constants.BE_BROWSER_VERSION, Constants.BE_URL);
		loginWeb = WebPageFactoryManager.getWebBackendSetupPageObject(driverWeb);
		loginWeb.Login_Web_Backend(driverWeb, username, passWeb);

		loginWeb.setupAssignServicesLimit(driverWeb, "Thanh toán hóa đơn trả sau", inputInfo, TittleData.PACKAGE_NAME);

		
		startServer();
		log.info("Before class: Mo app ");
		if (deviceType.contains("android")) {
			driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		} else if (deviceType.contains("ios")) {
			driver = openIOSApp(deviceName, udid, url);
		}
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);

		password = pass;
		homePage = PageFactoryManager.getHomePageObject(driver);
		landLinePhoneCharge = PageFactoryManager.getLandLinePhoneChargePageObject(driver);
	}


	@Test
	//Set han muc giao dich toi tieu là 25k=) Thanh toan hoa don tra sau
	public void TC_01_ThanhToanCuocDienThoaiCoDinhDuoiHanMucThapNhat() {
	

		log.info("------------------------------TC_01_01_Click Cuoc dien thoai co dinh---------------------------");
		landLinePhoneCharge.scrollDownToText(driver, "Thanh toán tiền nước");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Cước điện thoại cố định");

		log.info("------------------------------TC_01_02_Chon tai khoan nguon------------------------------");
		landLinePhoneCharge.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		landLinePhoneCharge.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("------------------------------TC_01_03_Chon loai cuoc thanh toan------------------------------");
		landLinePhoneCharge.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/wrap_tv");
		landLinePhoneCharge.clickToDynamicButtonLinkOrLinkText(driver, "Cố định có dây Viettel");

		log.info("------------------------------TC_01_04_Nhap so dien thoai tra cuoc va bam Tiep tuc------------------------------");
		landLinePhoneCharge.inputIntoEditTextByID(driver,LandLinePhoneCharge_Data.LIST_LANDLINE_PHONE_LINE_NEW[2],"com.VCB:id/code");

		log.info("------------------------------TC_01_08_Click Tiep tuc------------------------------");
		landLinePhoneCharge.clickToDynamicButton(driver, "Tiếp tục");

		log.info("------------------------------TC_01_08_Hien thi man hinh thong bao lo han muc------------------------------");
		landLinePhoneCharge.isDynamicMessageAndLabelTextDisplayed(driver, LandLinePhoneCharge_Data.MESSEGER_ERROR_LOWER_LIMIT_MIN_A_TRAN);
		
		log.info("------------------------------TC_01_08_ Click btn dong------------------------------");
		landLinePhoneCharge.clickToDynamicAcceptButton(driver,"com.VCB:id/btOK");
	}

	//SEt hạn mức của giao dịch là 77k
	public void TC_02_ThanhToanCuocDienThoaiCoDinhCaoHonHanMucToiDa() {
		
		log.info("------------------------------TC_02_01_Nhap so dien thoai tra cuoc va bam Tiep tuc------------------------------");
		landLinePhoneCharge.inputIntoEditTextByID(driver,LandLinePhoneCharge_Data.LIST_LANDLINE_PHONE_LINE_NEW[7],"com.VCB:id/code");

		log.info("------------------------------TC_02_02_Click Tiep tuc------------------------------");
		landLinePhoneCharge.clickToDynamicButton(driver, "Tiếp tục");

		log.info("------------------------------TC_02_03_Hien thi man hinh thong bao lo han muc------------------------------");
		landLinePhoneCharge.isDynamicMessageAndLabelTextDisplayed(driver, LandLinePhoneCharge_Data.MESSEGER_ERROR_HIHGER_LIMIT_MAX_A_TRAN);
		
		log.info("------------------------------TC_02_04_ Click btn dong------------------------------");
		landLinePhoneCharge.clickToDynamicAcceptButton(driver,"com.VCB:id/btOK");
		
	}
	
	
	
	// Trc khi chạy case set hạn mức giới hạn tối ta hạn mức của 1 giao dịch là 100k và tối đa của 1 ngày là 100 001 VND
	public void TC_03_ThanhToanCuocDienThoaiCoDinhCaoHonHanMucToiDaTrongNgay() {
		
		log.info("------------------------------TC_03_01_Click Cuoc dien thoai co dinh---------------------------");
		landLinePhoneCharge.scrollDownToText(driver, "Thanh toán tiền nước");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Cước điện thoại cố định");

		log.info("------------------------------TC_03_02_Chon tai khoan nguon------------------------------");
		landLinePhoneCharge.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		landLinePhoneCharge.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("------------------------------TC_03_03_Chon loai cuoc thanh toan------------------------------");
		landLinePhoneCharge.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/wrap_tv");
		landLinePhoneCharge.clickToDynamicButtonLinkOrLinkText(driver, "Cố định có dây Viettel");
		
		log.info("------------------------------TC_03_04_Nhap so dien thoai tra cuoc va bam Tiep tuc------------------------------");
		landLinePhoneCharge.inputIntoEditTextByID(driver,LandLinePhoneCharge_Data.LIST_LANDLINE_PHONE_NOLINE_NEW[15],"com.VCB:id/code");

		log.info("------------------------------TC_03_05_Click Tiep tuc------------------------------");
		landLinePhoneCharge.clickToDynamicButton(driver, "Tiếp tục");

		log.info("------------------------------TC_03_06_Hien thi man hinh thong bao lo han muc------------------------------");
		landLinePhoneCharge.isDynamicMessageAndLabelTextDisplayed(driver, "Xác nhận thông tin");
		
		log.info("------------------------------TC_03_07_Click Tiep tuc------------------------------");
		landLinePhoneCharge.clickToDynamicButton(driver, "Tiếp tục");

		log.info("------------------------------TC_03_08_Nhap mat khau");
		landLinePhoneCharge.inputToDynamicPopupPasswordInput(driver, password, "Tiếp tục");
		landLinePhoneCharge.clickToDynamicButton(driver, "Tiếp tục------------------------------");

		verifyTrue(landLinePhoneCharge.isDynamicMessageAndLabelTextDisplayed(driver, LandLinePhoneCharge_Data.SUCCESS_TRANSFER_MONEY));

		log.info("------------------------------TC_03_9_Click Thuc hien giao dich moi------------------------------");
		landLinePhoneCharge.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("------------------------------TC_03_10_Kiem tra so du kha dung luc sau------------------------------");
		landLinePhoneCharge.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		landLinePhoneCharge.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);
		
		log.info("------------------------------TC_03_11_Chon loai cuoc thanh toan------------------------------");
		landLinePhoneCharge.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/wrap_tv");
		landLinePhoneCharge.clickToDynamicButtonLinkOrLinkText(driver, "Cố định có dây Viettel");

		log.info("------------------------------TC_03_12_Nhap so dien thoai tra cuoc va bam Tiep tuc------------------------------");
		landLinePhoneCharge.inputIntoEditTextByID(driver,LandLinePhoneCharge_Data.LIST_LANDLINE_PHONE_LINE_NEW[2],"com.VCB:id/code");

		log.info("------------------------------TC_03_13_Click Tiep tuc------------------------------");
		landLinePhoneCharge.clickToDynamicButton(driver, "Tiếp tục");

		log.info("------------------------------TC_03_14_Hien thi man hinh thong bao lo han muc------------------------------");
		landLinePhoneCharge.isDynamicMessageAndLabelTextDisplayed(driver, LandLinePhoneCharge_Data.MESSEGER_ERROR_HIHGER_LIMIT_MAX_A_DAY);
		
		
		log.info("------------------------------TC_03_15_ Click btn dong------------------------------");
		landLinePhoneCharge.clickToDynamicAcceptButton(driver,"com.VCB:id/btOK");
		
	}
	
	
	
	
	//Trc khi chạy set goi dich vu có hạn mức là 50k
	public void TC_04_ThanhToanCuocDienThoaiCoDinhCaoHonHanMucNhom() {
		log.info("------------------------------TC_04_01_Click Cuoc dien thoai co dinh---------------------------");
		landLinePhoneCharge.scrollDownToText(driver, "Thanh toán tiền nước");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Cước điện thoại cố định");

		log.info("------------------------------TC_04_02_Chon tai khoan nguon------------------------------");
		landLinePhoneCharge.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		landLinePhoneCharge.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("------------------------------TC_04_03_Chon loai cuoc thanh toan------------------------------");
		landLinePhoneCharge.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/wrap_tv");
		landLinePhoneCharge.clickToDynamicButtonLinkOrLinkText(driver, "Cố định có dây Viettel");

		log.info("------------------------------TC_04_04_Nhap so dien thoai tra cuoc va bam Tiep tuc------------------------------");
		landLinePhoneCharge.inputIntoEditTextByID(driver,LandLinePhoneCharge_Data.LIST_LANDLINE_PHONE_LINE_NEW[7],"com.VCB:id/code");

		log.info("------------------------------TC_04_05_Click Tiep tuc------------------------------");
		landLinePhoneCharge.clickToDynamicButton(driver, "Tiếp tục");

		log.info("------------------------------TC_04_06_Hien thi man hinh thong bao lo han muc------------------------------");
		landLinePhoneCharge.isDynamicMessageAndLabelTextDisplayed(driver, LandLinePhoneCharge_Data.MESSEGER_ERROR_HIHGER_LIMIT_MAX_GROUP);
		
		log.info("------------------------------TC_04_07_ Click btn dong------------------------------");
		landLinePhoneCharge.clickToDynamicAcceptButton(driver,"com.VCB:id/btOK");
	}
	
	//Trc khi chạy set goi dich vu có hạn mức là 50k
	public void TC_05_ThanhToanCuocDienThoaiCoDinhCaoHonHanMucGoi() {
		log.info("------------------------------TC_05_01_Click Cuoc dien thoai co dinh---------------------------");
		landLinePhoneCharge.scrollDownToText(driver, "Thanh toán tiền nước");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Cước điện thoại cố định");

		log.info("------------------------------TC_05_02_Chon tai khoan nguon------------------------------");
		landLinePhoneCharge.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		landLinePhoneCharge.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("------------------------------TC_05_03_Chon loai cuoc thanh toan------------------------------");
		landLinePhoneCharge.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/wrap_tv");
		landLinePhoneCharge.clickToDynamicButtonLinkOrLinkText(driver, "Cố định có dây Viettel");

		log.info("------------------------------TC_05_04_Nhap so dien thoai tra cuoc va bam Tiep tuc------------------------------");
		landLinePhoneCharge.inputIntoEditTextByID(driver,LandLinePhoneCharge_Data.LIST_LANDLINE_PHONE_LINE_NEW[7],"com.VCB:id/code");

		log.info("------------------------------TC_05_05_Click Tiep tuc------------------------------");
		landLinePhoneCharge.clickToDynamicButton(driver, "Tiếp tục");

		log.info("------------------------------TC_05_06_Hien thi man hinh thong bao lo han muc------------------------------");
		landLinePhoneCharge.isDynamicMessageAndLabelTextDisplayed(driver, LandLinePhoneCharge_Data.MESSEGER_ERROR_HIHGER_LIMIT_MAX_PACKAGE);
		
		log.info("------------------------------TC_05_07_ Click btn dong------------------------------");
		landLinePhoneCharge.clickToDynamicAcceptButton(driver,"com.VCB:id/btOK");
	}
	
	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
