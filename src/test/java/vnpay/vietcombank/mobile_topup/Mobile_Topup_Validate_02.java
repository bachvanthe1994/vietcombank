package vnpay.vietcombank.mobile_topup;

import java.io.IOException;

import org.testng.SkipException;
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

public class Mobile_Topup_Validate_02 extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject home;
	private MobileTopupPageObject mobileTopup;

	private String dynamicValue = "";

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
	}

	@Parameters({ "phone" })
	@Test
	public void TC_01_KiemTraMacDinhHienThiSoDienThoaiDuocNap(String phone) {

		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_01_Step_01: Keo xuong va click vao phan 'Nap tien dien thoai'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Nạp tiền điện thoại");
		mobileTopup = PageFactoryManager.getMobileTopupPageObject(driver);

		log.info("TC_01_Step_02: Xac nhan hien thi so dien thoai duoc nap");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");
		verifyEquals(mobileTopup.getTextInEditTextFieldByID(driver, "com.VCB:id/mobile"), phone);

	}

	@Parameters({ "phone" })
	@Test
	public void TC_02_KiemTraBoTrongSoDienThoaiMacDinh(String phone) {

		log.info("TC_02_Step_01: Click vào DrodownList 'Tai khoan nguon' ");
		mobileTopup.clickToTextViewCombobox(driver, "com.VCB:id/number_account");

		log.info("TC_02_Step_02: Chon tai khoan nguon");
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_02_Step_03: Xoa So dien thoai khoi o nhap so dien thoai");
		mobileTopup.inputIntoEditTextByID(driver, "", "com.VCB:id/mobile");

		log.info("TC_02_Step_04: An nut 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btn_submit");

		log.info("TC_02_Step_05: Hien thi man hinh xac nhan thong tin voi so dien thoai mac dinh hien thi");
		verifyEquals(mobileTopup.getDynamicTextByLabel(driver, "Số điện thoại được nạp"), phone);

	}

	@Test
	void TC_03_KiemTraKiTuNhap() {

		log.info("TC_03_Step_01: An nut 'Back' de quay ve man hinh Nap tien dien thoai");
		mobileTopup.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_03_Step_02: Nhap ki tu vao o nhap so dien thoai");
		mobileTopup.inputIntoEditTextByID(driver, "4nh5jh6nb7", "com.VCB:id/mobile");

		log.info("TC_03_Step_02: Xac nhan chi hien thi chu so o o nhap so dien thoai");
		verifyEquals(mobileTopup.getTextInEditTextFieldByID(driver, "com.VCB:id/mobile"), "4567");
	}

	@Test
	public void TC_04_KiemTraGioiHanKiTu() {

		log.info("TC_04_Step_02: Nhap hon 10 chu so vào o So dien thoai mac dinh");
		mobileTopup.inputIntoEditTextByID(driver, "123456789012345", "com.VCB:id/mobile");

		log.info("TC_04_Step_03: Xac nhan chi hien thi duy nhat 10 chu so dau tien o trong o + hien thi dau 'x'");
		verifyEquals(mobileTopup.getTextInEditTextFieldByID(driver, "com.VCB:id/mobile"), "1234567890");
		verifyTrue(mobileTopup.isDynamicImageButtonDisplayed(driver, "com.VCB:id/ivDelete"));

	}

	@Test
	public void TC_05_NhanIconX() {

		log.info("TC_05_Step_01: Nhap Icon X");
		mobileTopup.clickToDynamicImageButtonByID(driver, "com.VCB:id/ivDelete");

		log.info("TC_05_Step_02: Xac nhan o textbox rong");
		verifyEquals(mobileTopup.getTextInEditTextFieldByID(driver, "com.VCB:id/mobile"), "VCB");
	}

	@Test
	public void TC_06_NhapSoDienThoaiNhoHon10KiTu() {

		log.info("TC_06_Step_01: Nhap nho hon 10 chu so vào o So dien thoai mac dinh");
		mobileTopup.inputIntoEditTextByID(driver, "012345678", "com.VCB:id/mobile");

		log.info("TC_06_Step_02: An nut 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btn_submit");

		log.info("TC_06_Step_03: Xac nhan hien thi thong bao so dien thoai khong hop le");
		verifyEquals(mobileTopup.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), UIs.PHONE_NUMBER_INVALID_MESSAGE);
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
	}

	@Test
	public void TC_07_NhapSoDienThoaiBatDauBangKiTuKhac0() {

		log.info("TC_07_Step_01: Nhap nho hon 10 chu so vào o So dien thoai mac dinh");
		mobileTopup.inputIntoEditTextByID(driver, "1234567890", "com.VCB:id/mobile");

		log.info("TC_07_Step_02: An nut 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btn_submit");

		log.info("TC_07_Step_03: Xac nhan hien thi thong bao so dien thoai khong hop le");
		verifyEquals(mobileTopup.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), UIs.PHONE_NUMBER_INVALID_MESSAGE);
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
	}

	@Test
	public void TC_08_NhapSoDienThoaiKhongDungQuyDinhCuaMangVienThong() {

		log.info("TC_08_Step_01: Nhap nho hon 10 chu so vào o So dien thoai mac dinh");
		mobileTopup.inputIntoEditTextByID(driver, "0213456789", "com.VCB:id/mobile");

		log.info("TC_08_Step_02: An nut 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btn_submit");

		log.info("TC_08_Step_03: Xac nhan hien thi thong bao so dien thoai khong hop le");
		verifyEquals(mobileTopup.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), UIs.PHONE_NUMBER_INVALID_MESSAGE);
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

	}

	@Parameters({ "phone" })
	@Test
	public void TC_09_SoTienGiaoDichLonHonSoDuTKNguon(String phone) {

		log.info("TC_09_Step_01: Click vào DrodownList 'Tai khoan nguon' ");
		mobileTopup.clickToTextViewCombobox(driver, "com.VCB:id/number_account");
		dynamicValue = mobileTopup.getStringNumber(500000, "com.VCB:id/descript");
		if (dynamicValue != "0") {

			log.info("TC_09_Step_02: Chon tai khoan nguon nho hon 500,000");
			mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, dynamicValue + " VND");

			log.info("TC_09_Step 03: Chon menh gia the 500,000");
			mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, UIs.LIST_UNIT_VALUE[5]);

			log.info("TC_09_Step_04: Nhap so dien thoai hop le vao So dien thoai mac dinh");
			mobileTopup.inputIntoEditTextByID(driver, phone, "com.VCB:id/mobile");

			log.info("TC_09_Step_05: An but 'Tiep tuc'");
			mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btn_submit");

			log.info("TC_09_Step_06: Xac nhan hien thi thong bao tai khoan nguon khong du so du");
			verifyEquals(mobileTopup.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), UIs.ACCOUNT_MONEY_NOT_ENOUGH);
			mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

		} else {
			log.info("TC_09_Step_02: Tat Dropdownlist");
			mobileTopup.clickToTextViewCombobox(driver, "com.VCB:id/cancel_button");
			throw new SkipException("These Tests shouldn't be run in Production");
		}

	}

	@Parameters({ "phone" })
	@Test
	public void TC_10_KhoiTaoGiaoDichHopLe(String phone) {

		log.info("TC_10_Step_01: Click vào DrodownList 'Tai khoan nguon' ");
		mobileTopup.clickToTextViewCombobox(driver, "com.VCB:id/number_account");

		log.info("TC_10_Step_02: Chon tai khoan nguon");
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_10_Step_03: Nhap so dien thoai hop le vao So dien thoai mac dinh");
		mobileTopup.inputIntoEditTextByID(driver, phone, "com.VCB:id/mobile");

		log.info("TC_10_Step_04: Click vao menh gia 30,000");
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, UIs.LIST_UNIT_VALUE[0]);

		log.info("TC_10_Step_05: An nut 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btn_submit");

		log.info("TC_10_Step_06: Hien thi man hinh xac nhan thong tin");
		verifyEquals(mobileTopup.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleHead"), UIs.MOBILE_TOPUP_CONFIRM_TITLE);
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		service.stop();
	}

}
