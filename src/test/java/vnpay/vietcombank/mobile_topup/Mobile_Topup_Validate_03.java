package vnpay.vietcombank.mobile_topup;

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
import pageObjects.MobileTopupPageObject;
import vietcombank_test_data.MobileTopupPage_Data.UIs;

public class Mobile_Topup_Validate_03 extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject home;
	private MobileTopupPageObject mobileTopup;
	
	private String originAccountNumber = "";
	private String originPhoneNumber = "";

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
	}

	@Test
	public void TC_01_ManHinhXacNhanThongTin_NhanIconBack() {

		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_01_Step_01: Keo xuong va click vao phan 'Nap tien dien thoai'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Nạp tiền điện thoại");
		mobileTopup = PageFactoryManager.getMobileTopupPageObject(driver);
	
		log.info("TC_01_Step_02: Click vao menh gia 30,000");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, UIs.LIST_UNIT_VALUE[0]);
		
		log.info("TC_01_Step_03: An nut 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btn_submit");
		
		log.info("TC_01_Step_04: An nut 'Back' de quay ve man hinh 'Nap tien dien thoai");
		mobileTopup.clickToDynamicBottomMenu(driver, "com.VCB:id/ivTitleLeft");
		
		log.info("TC_01_Step_05: Xac nhan quay ve man hinh 'Nap tien dien thoai");
		verifyEquals(mobileTopup.getDynamicTextDetailByID(driver, "com.VCB:id/tvTitleBar"), UIs.MOBILE_TOPUP_TITLE);
		
	}

	@Test
	public void TC_02_ManHinhXacNhanThongTin_NhanIconHome() {
		
		log.info("TC_02_Step_01: An nut 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btn_submit");
		
		log.info("TC_02_Step_02: An nut 'Home' de quay ve man hinh chinh");
		mobileTopup.clickToDynamicBottomMenu(driver, "com.VCB:id/ivTitleRight");
		
		log.info("TC_02_Step_03: Xac nhan quay ve man hinh chinh");
		verifyTrue(home.isDynamicImageHomeDisplay(driver, "com.VCB:id/menu_1"));
	}
	
	@Test
	public void TC_03_ManHinhXacNhanThongTin_KiemTraThongTinHienThi() {
		
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_03_Step_01: Keo xuong va click vao phan 'Nap tien dien thoai'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Nạp tiền điện thoại");
		mobileTopup = PageFactoryManager.getMobileTopupPageObject(driver);
	
		log.info("TC_03_Step_02: Click vao menh gia 30,000");
		originAccountNumber = mobileTopup.getTextInDynamicPopup(driver, "com.VCB:id/number_account");
		originPhoneNumber = mobileTopup.getTextInEditTextFieldByID(driver, "com.VCB:id/mobile");
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, UIs.LIST_UNIT_VALUE[0]);
		
		log.info("TC_03_Step_03: An nut 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btn_submit");
		
		log.info("TC_03_Step_04: Xac nhan 'Tai khoan nguon' hien thi dung voi input");
		verifyEquals(mobileTopup.getDynamicAmountLabel(driver, "Tài khoản nguồn"), originAccountNumber);
		
		log.info("TC_03_Step_04: Xac nhan 'So dien thoai duoc nap' hien thi dung voi input");
		verifyEquals(mobileTopup.getDynamicAmountLabel(driver, "Số điện thoại được nạp"), originPhoneNumber);
		
		log.info("TC_03_Step_04: Xac nhan 'Menh gia the' hien thi dung voi input");
		verifyEquals(mobileTopup.getDynamicAmountLabel(driver, "Mệnh giá thẻ"), UIs.LIST_UNIT_VALUE[0]+" VND");
	}
	
	@Test
	public void TC_04_ManHinhXacNhanThongTin_KiemTraTrangThaiCuaNutTiepTuc() {
		
		log.info("TC_04_Step_01: Xac nhan Nut 'Tiep tuc' duoc enable");
		verifyTrue(mobileTopup.isDynamicButtonByIdEnable(driver,"com.VCB:id/btContinue"));
	}
	
	@Test
	public void TC_05_ManHinhXacNhanThongTin_NhanNutTiepTuc() {
		
		log.info("TC_05_Step_01: An nut 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");
		
		log.info("TC_05_Step_02: Xac nhan hien thi pop-up 'Xac thuc giao dich' ");
		verifyEquals(mobileTopup.getTextInDynamicPopup(driver, "com.VCB:id/lblTitle"), UIs.MOBILE_TOPUP_CONFIRM_TRANSACTION_TITLE);
	}
	
	@Test
	public void TC_06_XacThucGiaoDichBangMatKhau_KiemTraManHinhHienThi() {
		
		log.info("TC_06_Step_01: Xac nhan hien thi Title 'Xac thuc giao dich'");
		verifyEquals(mobileTopup.getTextInDynamicPopup(driver, "com.VCB:id/lblTitle"), UIs.MOBILE_TOPUP_CONFIRM_TRANSACTION_TITLE);
		
		log.info("TC_06_Step_01: Xac nhan hien thi Icon 'Quay lai'");
		verifyTrue(mobileTopup.isDynamicLinearlayoutByIdDisplayed(driver, "com.VCB:id/layout_back"));
		verifyEquals(mobileTopup.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/layout_back"), "Quay lại");
		
		log.info("TC_06_Step_01: Xac nhan hien thi dung message thong bao");
		verifyEquals(mobileTopup.getTextInDynamicPopup(driver, "com.VCB:id/lblMessage"), UIs.MOBILE_TOPUP_CONFIRM_TRANSACTION_MESSAGE);
		
		log.info("TC_06_Step_01: Xac nhan hien thi textbox 'Nhap mat khau'");
		verifyEquals(mobileTopup.getTextInEditTextFieldByID(driver, "com.VCB:id/pin"), "Nhập mật khẩu");
		
		log.info("TC_06_Step_01: Xac nhan hien thi button 'Tiep tuc'");
		verifyTrue(mobileTopup.isDynamicButtonByIdDisplayed(driver, "com.VCB:id/btContinue"));
	}
	
	@Test
	public void TC_07_NhapSoDienThoaiKhongDungQuyDinhCuaMangVienThong() {
		
		
	
	}
	
	@Test
	public void TC_08_SoTienGiaoDichLonHonSoDuTKNguon() {
		
		
		
	}
	
	@Test
	public void TC_09_KhoiTaoGiaoDichHopLe() {
		
		
	}
	
	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
