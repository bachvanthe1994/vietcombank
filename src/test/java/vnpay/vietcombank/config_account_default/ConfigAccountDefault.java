package vnpay.vietcombank.config_account_default;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.TransferMoneyInVcbPageObject;

public class ConfigAccountDefault extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferMoneyInVcbPageObject transferInVCB;
	String account;

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
    @BeforeClass
    public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt)
	    throws IOException, InterruptedException {
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

	}
	
	@Test
	public void TC_01_CaiDatTaiKhoanMacDinh() {
		log.info("TC_01_Step_01: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_01_Step_02: Click vao cai dat");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Cài đặt");
		
		log.info("TC_01_Step_02: Click vao cai dat");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Cài đặt tài khoản mặc định");

		log.info("TC_01_Step_02: Click vao dong");
		homePage.clickToDynamicButton(driver, "Đóng");
		
		log.info("TC_01_Step_02: Chon tai khoan");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Tài khoản thanh toán 2");
		
		log.info("TC_01_Step_02: lay so tai khoan");
		account = homePage.getMoneyByAccount(driver, "Tài khoản thanh toán 2");
		
		log.info("TC_01_Step_02: chon tiep tuc");
		homePage.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_01_Step_02: kiem tra thong bao thanh cong");
		String confirm = homePage.getDynamicTextView(driver, "com.VCB:id/tvContent");
		verifyEquals(confirm, "Cài đặt thanh toán mặc định thành công");
		
		log.info("TC_01_Step_02: Click vao dong");
		homePage.clickToDynamicButton(driver, "Đóng");
		
		log.info("TC_01_Step_02: Click chon image back");
		homePage.clickToDynamicImageView(driver, "com.VCB:id/ivTitleLeft");
		
		log.info("TC_01_Step_01: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}
	
	@Test
	public void TC_02_KiemTraTaiKhoanMacDinhOManHinhHome() {
		log.info("TC_02_Step_01: kiem tra tai khoan hien thi o man hinh chinh");
		String account_home = homePage.getAccountNumber(driver, "com.VCB:id/tvDefaultAcc");
		verifyEquals(account, account_home);
		
	}
	
	@Test
	public void TC_03_KiemTraTaiKhoanMacDinhTrongManHinhChuyenTien() {
		log.info("TC_03_Step_01: chon chuyen tien trong vcb");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền trong VCB");
		
		log.info("TC_03_Step_02: lay ra tai khoan nguon mac dinh");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		String account_transfer = transferInVCB.getTextDynamicDefaultSourceAccount(driver, "Tài khoản nguồn");
		verifyEquals(account, account_transfer);
		
		log.info("TC_03_Step_03: Click chon image back");
		transferInVCB.clickToDynamicImageView(driver, "com.VCB:id/ivTitleLeft");
	}
	
	@Test
	public void TC_04_KiemTraTaiKhoanMacDinhTrongManNapTien() {
		log.info("TC_04_Step_01: chon chuyen tien trong vcb");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Nạp tiền điện thoại");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		
		log.info("TC_04_Step_01: chon chuyen tien trong vcb");
		transferInVCB.clickToDynamicButton(driver, "Từ chối");
		
		log.info("TC_04_Step_02: lay ra tai khoan nguon mac dinh");
		String account_transfer = transferInVCB.getTextDynamicDefaultSourceAccount(driver, "Tài khoản nguồn");
		verifyEquals(account, account_transfer);
		
		log.info("TC_04_Step_03: Click chon image back");
		transferInVCB.clickToDynamicImageView(driver, "com.VCB:id/ivTitleLeft");
	}
	
	@Test
	public void TC_05_KiemTraTaiKhoanMacDinhTrongManThanhToanHoaDon() {
		log.info("TC_05_Step_01: chon chuyen tien trong vcb");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Hóa đơn tiền điện");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		
		log.info("TC_05_Step_01: chon chuyen tien trong vcb");
		transferInVCB.clickToDynamicButton(driver, "Từ chối");
		
		log.info("TC_05_Step_02: lay ra tai khoan nguon mac dinh");
		String account_transfer = transferInVCB.getTextDynamicDefaultSourceAccount(driver, "Tài khoản nguồn");
		verifyEquals(account, account_transfer);
		
		log.info("TC_05_Step_03: Click chon image back");
		transferInVCB.clickToDynamicImageView(driver, "com.VCB:id/ivTitleLeft");
	}
	
	@Test
	public void TC_06_KiemTraTaiKhoanMacDinhTrongManTietKiem() {
		log.info("TC_06_Step_01: chon chuyen tien trong vcb");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Mở tài khoản thiết kiệm");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		
		log.info("TC_06_Step_02: lay ra tai khoan nguon mac dinh");
		String account_transfer = transferInVCB.getTextDynamicDefaultSourceAccount(driver, "Tài khoản nguồn");
		verifyEquals(account, account_transfer);
		
		log.info("TC_06_Step_03: Click chon image back");
		transferInVCB.clickToDynamicImageView(driver, "com.VCB:id/ivTitleLeft");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
//		service.stop();
	}

}
