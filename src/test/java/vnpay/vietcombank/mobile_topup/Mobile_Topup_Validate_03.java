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
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.MobileTopupPage_Data.UIs;

public class Mobile_Topup_Validate_03 extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject home;
	private MobileTopupPageObject mobileTopup;

	private String originAccountNumber = "";

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
		mobileTopup = PageFactoryManager.getMobileTopupPageObject(driver);
		home = PageFactoryManager.getHomePageObject(driver);
	}

	@Test
	public void TC_01_ManHinhXacNhanThongTin_NhanIconBack() {

		log.info("TC_01_Step_01: Keo xuong va click vao phan 'Nap tien dien thoai'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Nạp tiền điện thoại");
		mobileTopup = PageFactoryManager.getMobileTopupPageObject(driver);
	
		log.info("TC_01_Step_02: Click vào DrodownList 'Tai khoan nguon' ");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");
		mobileTopup.clickToTextViewCombobox(driver, "com.VCB:id/number_account");
		
		log.info("TC_01_Step_03: Chon tai khoan nguon");
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);
		
		log.info("TC_01_Step_04: Click vao menh gia 30,000");
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, UIs.LIST_UNIT_VALUE[0]);
		
		log.info("TC_01_Step_05: An nut 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btn_submit");
		
		log.info("TC_01_Step_06: An nut 'Back' de quay ve man hinh 'Nap tien dien thoai");
		mobileTopup.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		
		log.info("TC_01_Step_07: Xac nhan quay ve man hinh 'Nap tien dien thoai");
		verifyEquals(mobileTopup.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), UIs.MOBILE_TOPUP_TITLE);

	}

	@Test
	public void TC_02_ManHinhXacNhanThongTin_NhanIconHome() {

		log.info("TC_02_Step_01: An nut 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btn_submit");

		log.info("TC_02_Step_02: An nut 'Home' de quay ve man hinh chinh");
		mobileTopup.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleRight");

		log.info("TC_02_Step_03: Xac nhan quay ve man hinh chinh");
		verifyTrue(home.isDynamicImageHomeDisplay(driver, "com.VCB:id/menu_1"));
	}

	@Parameters({ "phone" })
	@Test
	public void TC_03_ManHinhXacNhanThongTin_KiemTraThongTinHienThi(String phone) {

		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_03_Step_01: Keo xuong va click vao phan 'Nap tien dien thoai'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Nạp tiền điện thoại");
		mobileTopup = PageFactoryManager.getMobileTopupPageObject(driver);

		log.info("TC_03_Step_02:Chon tai khoan nguon");
		mobileTopup.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_03_Step_03: Chon tai khoan nap tien");
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_03_Step_04: Click vao menh gia 30,000");
		originAccountNumber = mobileTopup.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/number_account");
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, UIs.LIST_UNIT_VALUE[0]);

		log.info("TC_03_Step_05: An nut 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btn_submit");

		log.info("TC_03_Step_06: Xac nhan 'Tai khoan nguon' hien thi dung voi input");
		verifyEquals(mobileTopup.getDynamicTextByLabel(driver, "Tài khoản nguồn"), originAccountNumber);

		log.info("TC_03_Step_07: Xac nhan 'So dien thoai duoc nap' hien thi dung voi input");
		verifyEquals(mobileTopup.getDynamicTextByLabel(driver, "Số điện thoại được nạp"), phone);

		log.info("TC_03_Step_08: Xac nhan 'Menh gia the' hien thi dung voi input");
		verifyEquals(mobileTopup.getDynamicTextByLabel(driver, "Mệnh giá thẻ"), UIs.LIST_UNIT_VALUE[0] + " VND");

	}

	@Test
	public void TC_04_ManHinhXacNhanThongTin_KiemTraTrangThaiCuaNutTiepTuc() {

		log.info("TC_04_Step_01: Xac nhan Nut 'Tiep tuc' duoc enable");
		verifyTrue(mobileTopup.isDynamicButtonByIdEnable(driver, "com.VCB:id/btContinue"));
	}

	@Test
	public void TC_05_ManHinhXacNhanThongTin_NhanNutTiepTuc() {

		log.info("TC_05_Step_01: An nut 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_05_Step_02: Xac nhan hien thi pop-up 'Xac thuc giao dich' ");
		verifyEquals(mobileTopup.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/lblTitle"), UIs.MOBILE_TOPUP_CONFIRM_TRANSACTION_TITLE);
	}

	@Test
	public void TC_06_XacThucGiaoDichBangMatKhau_KiemTraManHinhHienThi() {

		log.info("TC_06_Step_01: Xac nhan hien thi Title 'Xac thuc giao dich'");
		verifyEquals(mobileTopup.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/lblTitle"), UIs.MOBILE_TOPUP_CONFIRM_TRANSACTION_TITLE);

		log.info("TC_06_Step_02: Xac nhan hien thi Icon 'Quay lai'");
		verifyTrue(mobileTopup.isDynamicLinearlayoutByIdDisplayed(driver, "com.VCB:id/layout_back"));
		verifyEquals(mobileTopup.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/layout_back"), "Quay lại");

		log.info("TC_06_Step_03: Xac nhan hien thi dung message thong bao");
		verifyEquals(mobileTopup.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/lblMessage"), UIs.MOBILE_TOPUP_CONFIRM_PASSWORD_TRANSACTION_MESSAGE);

		log.info("TC_06_Step_04: Xac nhan hien thi textbox 'Nhap mat khau'");
		verifyEquals(mobileTopup.getTextInEditTextFieldByID(driver, "com.VCB:id/pin"), "Nhập mật khẩu");

		log.info("TC_06_Step_05: Xac nhan hien thi button 'Tiep tuc'");
		verifyTrue(mobileTopup.isDynamicButtonByIdDisplayed(driver, "com.VCB:id/btContinue"));
	}

	@Test
	public void TC_07_XacThucGiaoDichBangMatKhau_KiemTraTrangThaiMacDinhNutTiepTuc_AnIconBack() {

		log.info("TC_07_Step_01: Kiem tra trang thai mac dich cua Button 'Tiep tuc'");
		verifyTrue(mobileTopup.isDynamicButtonByIdEnable(driver, "com.VCB:id/btContinue"));

		log.info("TC_07_Step_02: An nut 'Back' de quay ve man hinh xac nhan");
		mobileTopup.clickToDynamicLinerLayoutID(driver, "com.VCB:id/layout_back");

	}

	@Test
	public void TC_08_XacThucGiaoDichBangMatKhau_KiemTraHienThiPhuongThucMacDinh() {

		log.info("TC_08_Step_01: Kiem tra phuong thuc mac dinh hien thi");
		verifyEquals(mobileTopup.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvptxt"), UIs.PASSWORD_METHOD);

	}

	@Test
	public void TC_09_XacThucGiaoDichBangMatKhau_KiemTraBoTrongMK() {

		log.info("TC_09_Step_01: An button 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_09_Step_02: An tiep button 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_09_Step_03: Xac nhan hien thi thong bao nhap MK");
		verifyEquals(mobileTopup.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), UIs.PASSWORD_METHOD_EMPTY_MESSAGE);
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
	}

	@Test
	public void TC_10_XacThucGiaoDichBangMatKhau_KiemTraKiTuNhapMK() {

		log.info("TC_10_Step_01: Nhap ki tu vao o nhap mat khau");
		mobileTopup.inputIntoEditTextByID(driver, "ASbc!@#$%^&*hdj364726kd", "com.VCB:id/pin");

		log.info("TC_10_Step_02: Xac nhan nhap duoc toi da 20 Ki tu");
		verifyEquals(mobileTopup.getTextInDynamicPasswordInput(driver, "com.VCB:id/pin").length(), 20);
	}

	@Test
	public void TC_11_XacThucGiaoDichBangMatKhau_KiemTraNhapMatKhauNhoHon8KiTu() {

		log.info("TC_11_Step_01: Nhap ki tu vao o nhap mat khau");
		mobileTopup.inputIntoEditTextByID(driver, "1234567", "com.VCB:id/pin");

		log.info("TC_11_Step_02: An tiep button 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_11_Step_03: Xac nhan thong bao hien thi");
		verifyEquals(mobileTopup.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), UIs.PASSWORD_METHOD_ERROR_MESSAGE);
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
	}

	@Test
	public void TC_12_XacThucGiaoDichBangMatKhau_NhapSaiMK() {

		log.info("TC_12_Step_01: Nhap ki tu vao o nhap mat khau");
		mobileTopup.inputIntoEditTextByID(driver, "12345678", "com.VCB:id/pin");

		log.info("TC_12_Step_02: An tiep button 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_12_Step_03: Xac nhan thong bao hien thi");
		verifyEquals(mobileTopup.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), UIs.PASSWORD_METHOD_ERROR_MESSAGE);
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
	}

	@Parameters({ "pass", "phone" })
	@Test
	public void TC_13_XacThucGiaoDichBangMatKhau_NhapMatKhauHopLe(String pass, String phone) {

		log.info("TC_13_Step_01: Nhap ki tu vao o nhap mat khau");
		mobileTopup.inputIntoEditTextByID(driver, pass, "com.VCB:id/pin");

		log.info("TC_13_Step_02: An tiep button 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_13_Step_03: Xac nhan hien thi man hinh ket qua giao dich");
		verifyEquals(mobileTopup.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), UIs.MOBILE_TOPUP_TRANSACTION_SUCCEED_TITLE);

		log.info("TC_13_Step_04: Xac nhan hien thi dung Menh gia Nap");
		verifyEquals(mobileTopup.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount"), UIs.LIST_UNIT_VALUE[0] + " VND");

		log.info("TC_13_Step_05: Xac nhan hien thi dung So dien thoai duoc nap");
		verifyEquals(mobileTopup.getDynamicTextByLabel(driver, "Số điện thoại được nạp"), phone);
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");
	}

	@Parameters ({"phone"})
	@Test
	public void TC_14_ManHinhXacNhanThongTin_KiemTraManHinhHienTi(String phone) {
		
		log.info("TC_14_Step_01: Click vào DrodownList 'Tai khoan nguon' ");
		mobileTopup.clickToTextViewCombobox(driver, "com.VCB:id/number_account");
		
		log.info("TC_14_Step_02: Chon tai khoan nguon");
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);
		
		log.info("TC_14_Step_03: Click vao menh gia 30,000");
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, UIs.LIST_UNIT_VALUE[0]);
		
		log.info("TC_14_Step_04: An nut 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btn_submit");
		
		log.info("TC_14_Step_05: Xac nhan hien thi Title 'Xac nhan thong tin'");
		verifyEquals(mobileTopup.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), UIs.MOBILE_TOPUP_CONFIRM_HEADER_TITLE);
		
		log.info("TC_14_Step_06: Xac nhan hien thi Icon 'Back'");
		verifyTrue(mobileTopup.isDynamicImageHomeDisplay(driver, "com.VCB:id/ivTitleLeft"));
		
		log.info("TC_14_Step_07: Xac nhan hien thi Icon 'Home' ");
		verifyTrue(mobileTopup.isDynamicImageHomeDisplay(driver, "com.VCB:id/ivTitleRight"));
		
		log.info("TC_14_Step_08: Xac nhan hien thi label 'Kiem tra thong tin giao dich'");
		verifyEquals(mobileTopup.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleHead"), UIs.MOBILE_TOPUP_CONFIRM_TITLE);
		
		log.info("TC_14_Step_09: Xac nhan hien thi label 'Tai khoan nguon' ");
		verifyEquals(mobileTopup.getDynamicTextByLabel(driver, "Tài khoản nguồn"), Account_Data.Valid_Account.ACCOUNT2);
		
		log.info("TC_14_Step_10: Xac nhan hien thi so dien thoai duoc nap");
		verifyEquals(mobileTopup.getDynamicTextByLabel(driver, "Số điện thoại được nạp"), phone);
		
		log.info("TC_14_Step_11: Xac nhan hien thi nha mang");
		verifyTrue(mobileTopup.isDynamicMessageAndLabelTextDisplayed(driver, "Nhà mạng"));
		
		log.info("TC_14_Step_12: Xac nhan hien thi menh gia nap ");
		verifyEquals(mobileTopup.getDynamicTextByLabel(driver, "Mệnh giá thẻ"), UIs.LIST_UNIT_VALUE[0]+" VND");
		
		log.info("TC_14_Step_13: Xac nhan hien thi phi giao dich");
		verifyEquals(mobileTopup.getDynamicTextByLabel(driver, "Số tiền phí"),"0 VND");
		
		log.info("TC_14_Step_14: Xac nhan hien thi nut 'Tiep Tuc'");
		verifyTrue(mobileTopup.isDynamicButtonByIdDisplayed(driver, "com.VCB:id/btContinue"));
		
	}
	
	
	@AfterClass(alwaysRun = true)
	public void afterClass() {
		service.stop();
	}

}
