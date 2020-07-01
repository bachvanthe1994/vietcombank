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
import vietcombank_test_data.LandLinePhoneCharge_Data;
import vietcombank_test_data.LandLinePhoneCharge_Data.Text_Data;
import vietcombank_test_data.TransferMoneyInVCB_Data.TittleData;

public class Limit_LandLinePhoneCharge extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private WebBackendSetupPageObject loginWeb;
	private LandLinePhoneChargePageObject landLinePhoneCharge;
	WebDriver driverWeb;
	List<String> haveLine = new ArrayList<String>();
	SourceAccountModel sourceAccount = new SourceAccountModel();
	ServiceLimitInfo inputInfo = new ServiceLimitInfo("1000", "10000", "1000000", "1000000");

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp", "username", "passWeb" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt, String username, String passWeb) throws IOException, InterruptedException, GeneralSecurityException {
		log.info("Before class: Mo backend ");
		startServer();
		driverWeb = openMultiBrowser(Constants.BE_BROWSER_CHROME, Constants.BE_BROWSER_VERSION, Constants.BE_URL);
		loginWeb = WebPageFactoryManager.getWebBackendSetupPageObject(driverWeb);
		loginWeb.Login_Web_Backend(driverWeb, username, passWeb);


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
		landLinePhoneCharge = PageFactoryManager.getLandLinePhoneChargePageObject(driver);
		
		landLinePhoneCharge = PageFactoryManager.getLandLinePhoneChargePageObject(driver);
		haveLine = Arrays.asList(getDataInCell(12).split(";"));
	}

	@Parameters({ "pass" })
	@Test
	// Set han muc giao dich toi tieu là 25k=) Thanh toan hoa don tra sau
	public void TC_01_ThanhToanCuocDienThoaiCoDinhDuoiHanMucThapNhat(String pass) {

		log.info("TC_01_01_Click Cuoc dien thoai co dinh");
		landLinePhoneCharge.scrollDownToText(driver, Text_Data.PRICE_CABLE);
		landLinePhoneCharge.clickToDynamicButtonLinkOrLinkText(driver, Text_Data.LANDLINE_TELEPHONE);

		log.info("TC_01_02_Chon tai khoan nguon");
		landLinePhoneCharge.clickToDynamicDropDown(driver, Text_Data.SOURCE_ACCOUNT);
		sourceAccount = landLinePhoneCharge.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		log.info("TC_01_03_Chon loai cuoc thanh toan");
		landLinePhoneCharge.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/wrap_tv");
		landLinePhoneCharge.clickToDynamicTextContains(driver, Text_Data.HAVE_LINE);

		log.info("TC_01_04_Nhap so dien thoai tra cuoc va bam Tiep tuc");
		landLinePhoneCharge.inputPhoneNumberLandLinePhoneCharge(haveLine);

		log.info("TC_01_05_Kiem tra man hinh xac nhan thong tin");


		log.info("TC_01_06_Chon phuong thuc xac thuc");
		landLinePhoneCharge.clickToDynamicButtonLinkOrLinkText(driver, Text_Data.PASSWORD_LOGIN);

		log.info("TC_01_08_Click Tiep tuc");
		landLinePhoneCharge.clickToDynamicButton(driver, Text_Data.CONTINUE);

		log.info("TC_01_09_Nhap mat khau");
		landLinePhoneCharge.inputToDynamicPopupPasswordInput(driver, pass, Text_Data.CONTINUE);
		landLinePhoneCharge.clickToDynamicButton(driver, Text_Data.CONTINUE);
		
		
		
		
		
		
		loginWeb.setupAssignServicesLimit(driverWeb, "Thanh toán hóa đơn trả sau", inputInfo, TittleData.PACKAGE_NAME);


	}

	// SEt hạn mức của giao dịch là 77k
	public void TC_02_ThanhToanCuocDienThoaiCoDinhCaoHonHanMucToiDa() {

		log.info("------------------------------TC_02_01_Nhap so dien thoai tra cuoc va bam Tiep tuc------------------------------");
		landLinePhoneCharge.inputIntoEditTextByID(driver, LandLinePhoneCharge_Data.LIST_LANDLINE_PHONE_LINE_NEW[7], "com.VCB:id/code");

		log.info("------------------------------TC_02_02_Click Tiep tuc------------------------------");
		landLinePhoneCharge.clickToDynamicButton(driver, "Tiếp tục");

		log.info("------------------------------TC_02_03_Hien thi man hinh thong bao lo han muc------------------------------");
		landLinePhoneCharge.isDynamicMessageAndLabelTextDisplayed(driver, LandLinePhoneCharge_Data.MESSEGER_ERROR_HIHGER_LIMIT_MAX_A_TRAN);

		log.info("------------------------------TC_02_04_ Click btn dong------------------------------");
		landLinePhoneCharge.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

	}

	// Trc khi chạy case set hạn mức giới hạn tối ta hạn mức của 1 giao dịch là 100k
	// và tối đa của 1 ngày là 100 001 VND
	public void TC_03_ThanhToanCuocDienThoaiCoDinhCaoHonHanMucToiDaTrongNgay() {

	}

	// Trc khi chạy set goi dich vu có hạn mức là 50k
	public void TC_04_ThanhToanCuocDienThoaiCoDinhCaoHonHanMucNhom() {

	}

	// Trc khi chạy set goi dich vu có hạn mức là 50k
	public void TC_05_ThanhToanCuocDienThoaiCoDinhCaoHonHanMucGoi() {
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
