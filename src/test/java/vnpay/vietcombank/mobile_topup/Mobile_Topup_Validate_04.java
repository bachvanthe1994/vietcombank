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

public class Mobile_Topup_Validate_04 extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject home;
	private MobileTopupPageObject mobileTopup;

	private String originAccount = "";

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
		mobileTopup = PageFactoryManager.getMobileTopupPageObject(driver);
	}

	@Parameters({ "phone" })
	@Test
	public void TC_01_XacThucGiaoDichBangSMSOTP(String phone) {

		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_01_Step_01: Keo xuong va click vao phan 'Nap tien dien thoai'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Nạp tiền điện thoại");

		log.info("TC_01_Step_02: Click vào DrodownList 'Tai khoan nguon' ");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");
		mobileTopup.clickToTextViewCombobox(driver, "com.VCB:id/number_account");

		log.info("TC_01_Step_03: Chon tai khoan nguon");
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_01_Step_04: Click vao menh gia 30,000");
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, UIs.LIST_UNIT_VALUE[0]);

		log.info("TC_01_Step_05: An nut 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btn_submit");

		log.info("TC_01_Step_06: Chon phuong thuc xac thuc SMS OTP");
		mobileTopup.clickToTextViewCombobox(driver, "com.VCB:id/tvptxt");
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_01_Step_07: An nut 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_08: Xac nhan hien thi Title 'Xac thuc giao dich'");
		verifyEquals(mobileTopup.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/lblTitle"), UIs.MOBILE_TOPUP_CONFIRM_TRANSACTION_TITLE);

		log.info("TC_01_Step_09: Xac nhan hien thi Icon 'Quay lai'");
		verifyTrue(mobileTopup.isDynamicLinearlayoutByIdDisplayed(driver, "com.VCB:id/layout_back"));
		verifyEquals(mobileTopup.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/layout_back"), "Quay lại");

		log.info("TC_01_Step_10: Xac nhan hien thi dung message thong bao");
		verifyEquals(mobileTopup.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/lblMessage"), UIs.MOBILE_TOPUP_CONFIRM_SMS_OTP_TRANSACTION_MESSAGE + " " + phone.substring(0, 3) + "*****" + phone.substring(8, 10));

		log.info("TC_01_Step_11: Xac nhan hien thi textbox 'Nhap OTP'");
		verifyTrue(mobileTopup.isDynamicTextDetailByID(driver, "com.VCB:id/otp"));

		log.info("TC_01_Step_12: Xac nhan hien thi button 'Tiep tuc'");
		verifyTrue(mobileTopup.isDynamicButtonByIdDisplayed(driver, "com.VCB:id/btContinue"));

	}

	@Test
	public void TC_02_XacThucGiaoDichBangSMSOTP_KiemTraTrangThaiMacDinhNutTiepTuc_AnIconBack() {

		log.info("TC_01_Step_01: Kiem tra trang thai mac dich cua Button 'Tiep tuc'");
		verifyTrue(mobileTopup.isDynamicButtonByIdEnable(driver, "com.VCB:id/btContinue"));

		log.info("TC_02_Step_02: An nut 'Back' de quay ve man hinh xac nhan");
		mobileTopup.clickToDynamicLinerLayoutID(driver, "com.VCB:id/layout_back");
	}

	@Parameters({ "phone" })
	@Test
	public void TC_03_XacThucGiaoDichBangSMSOTP_KiemTraBoTrongOTP(String phone) {

		log.info("TC_03_Step_01: Chon phuong thuc xac thuc SMS OTP");
		mobileTopup.clickToTextViewCombobox(driver, "com.VCB:id/tvptxt");
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_03_Step_02: An nut 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_03_Step_03: An tiep nut 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_03_Step_04: Xac nhan thong bao hien thi");
		verifyEquals(mobileTopup.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), UIs.SMS_OTP_METHOD_EMPTY_MESSAGE);
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
	}

	@Test
	public void TC_04_XacThucGiaoDichBangSMSOTP_KiemTraKiTuNhapMaOTP() {

		log.info("TC_04_Step_01: Nhap lon hon 6 ki tu o o nhap OTP");
		mobileTopup.inputToDynamicOtp(driver, "2345678", "Tiếp tục");

		log.info("TC_04_Step_02: Xac nhan chi co the nhap toi da 6 ki tu");
		verifyEquals(mobileTopup.getTextInDynamicOtp(driver, "Tiếp tục"), "234567");
	}

	@Test
	public void TC_05_XacThucGiaoDichBangSMSOTP_NhapOTPNhoHon6KiTu() {

		log.info("TC_05_Step_01: Nhap it hon 6 ki tu o o nhap OTP");
		mobileTopup.inputToDynamicOtp(driver, "23456", "Tiếp tục");

		log.info("TC_05_Step_02: An tiep nut 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_05_Step_03: Xac nhan thong bao loi hien thi");
		verifyEquals(mobileTopup.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), UIs.SMS_OTP_METHOD_LESS_THAN_SIX_CHARACTER_MESSAGE);
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
	}

	@Test
	public void TC_06_XacThucGiaoDichBangSMSOTP_NhapSaiOTP() {

		log.info("TC_06_Step_01: Nhap du 6 ki tu o o nhap OTP");
		mobileTopup.inputToDynamicOtp(driver, "234567", "Tiếp tục");

		log.info("TC_06_Step_02: An tiep nut 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_07_Step_03: Xac nhan thong bao loi hien thi");
		verifyEquals(mobileTopup.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), UIs.SMS_OTP_METHOD_ERROR_MESSAGE);
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
	}

	@Parameters({ "otp", "phone" })
	@Test
	public void TC_07_XacThucGiaoDichBangSMSOTP_NhapMaOtpHopLe(String otp, String phone) {

		log.info("TC_06_Step_01: Nhap du 6 ki tu o o nhap OTP");
		mobileTopup.inputToDynamicOtp(driver, otp, "Tiếp tục");

		log.info("TC_04_Step_02: An tiep nut 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_13_Step_03: Xac nhan hien thi man hinh ket qua giao dich");
		verifyEquals(mobileTopup.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), UIs.MOBILE_TOPUP_TRANSACTION_SUCCEED_TITLE);

		log.info("TC_13_Step_04: Xac nhan hien thi dung Menh gia Nap");
		verifyEquals(mobileTopup.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount"), UIs.LIST_UNIT_VALUE[0] + " VND");

		log.info("TC_13_Step_05: Xac nhan hien thi dung So dien thoai duoc nap");
		verifyEquals(mobileTopup.getDynamicTextByLabel(driver, "Số điện thoại được nạp"), phone);
	}

	@Parameters({ "phone" })
	@Test
	public void TC_08_ManHinhKetQuaGiaoDich_KiemTraManHinhHienThi(String phone) {

		log.info("TC_08_Step_01: Xac nhan hien thi Icon 'Home'");
		verifyTrue(mobileTopup.isDynamicImageHomeDisplay(driver, "com.VCB:id/ivHome"));

		log.info("TC_08_Step_02: Xac nhan hien thi Icon 'Bieu tuong thanh cong'");
		verifyTrue(mobileTopup.isDynamicImageByFollowingImageIdDisplayed(driver, "com.VCB:id/ivHome"));

		log.info("TC_08_Step_03: Xac nhan hien thi Label 'Giao Dich Thanh Cong'");
		verifyEquals(mobileTopup.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), UIs.MOBILE_TOPUP_TRANSACTION_SUCCEED_TITLE);

		log.info("TC_08_Step_04: Xac nhan hien thi Label 'So tien giao dich'");
		verifyEquals(mobileTopup.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount"), UIs.LIST_UNIT_VALUE[0] + " VND");

		log.info("TC_08_Step_05: Xac nhan hien thi Label 'So dien thoai duoc nap'");
		verifyEquals(mobileTopup.getDynamicTextByLabel(driver, "Số điện thoại được nạp"), phone);

		log.info("TC_08_Step_06: Xac nhan hien thi Label 'Ma giao dich'");
		verifyTrue(mobileTopup.isDynamicTextNumberCustomerDisplayed(driver, "Mã giao dịch"));

		log.info("TC_08_Step_07: Xac nhan hien thi Icon 'Chia se'");
		verifyTrue(mobileTopup.isDynamicTextByIdDisplayed(driver, "com.VCB:id/tvShare"));

		log.info("TC_08_Step_08: Xac nhan hien thi Icon 'Luu anh'");
		verifyTrue(mobileTopup.isDynamicTextByIdDisplayed(driver, "com.VCB:id/tvSavePhoto"));

		log.info("TC_08_Step_09: Xac nhan hien thi Button 'Thuc hien giao dich'");
		verifyTrue(mobileTopup.isDynamicButtonByIdDisplayed(driver, "com.VCB:id/btContinue"));
	}

	@Test
	public void TC_09_ManHinhKetQuaGiaoDich_AnNutHome() {

		log.info("TC_09_Step_01: An nut 'Home' de quay ve man hinh chinh");
		mobileTopup.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivHome");

		log.info("TC_09_Step_02: Xac nhan quay ve man hinh chinh");
		verifyTrue(home.isDynamicImageHomeDisplay(driver, "com.VCB:id/menu_1"));
	}

	@Parameters({ "otp", "phone", "pass" })
	@Test
	public void TC_10_ManHinhKetQuaGiaoDich_KiemTraAnNutThucHienGiaoDichMoi(String otp, String phone, String pass) {

		log.info("TC_10_Step_01: Keo xuong va click vao phan 'Nap tien dien thoai'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Nạp tiền điện thoại");
		mobileTopup = PageFactoryManager.getMobileTopupPageObject(driver);

		log.info("TC_10_Step_02: Click vào DrodownList 'Tai khoan nguon' ");
		originAccount = mobileTopup.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/number_account");
		mobileTopup.clickToTextViewCombobox(driver, "com.VCB:id/number_account");

		log.info("TC_10_Step_03: Chon tai khoan nguon");
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_10_Step_04: Click vao menh gia 30,000");
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, UIs.LIST_UNIT_VALUE[0]);

		log.info("TC_10_Step_05: An nut 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btn_submit");

		log.info("TC_10_Step_06: An nut 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_10_Step_07: Nhap ki tu vao o nhap mat khau");
		mobileTopup.inputIntoEditTextByID(driver, pass, "com.VCB:id/pin");

		log.info("TC_10_Step_08: An tiep nut 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_10_Step_09: Xac nhan hien thi man hinh ket qua giao dich");
		verifyEquals(mobileTopup.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), UIs.MOBILE_TOPUP_TRANSACTION_SUCCEED_TITLE);

		log.info("TC_10_Step_10: An tiep nut 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_10_Step_11: Xac nhan hien thi title 'Nap tien dien thoai'");
		verifyEquals(mobileTopup.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), UIs.MOBILE_TOPUP_TITLE);

		log.info("TC_10_Step_12: Xac nhan hien thi Icon 'Back'");
		verifyTrue(mobileTopup.isDynamicImageHomeDisplay(driver, "com.VCB:id/ivTitleLeft"));

		log.info("TC_10_Step_13: Xac nhan hien thi Label huong dan");
		verifyEquals(mobileTopup.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvCarrier"), UIs.MOBILE_TOPUP_GUIDE);

		log.info("TC_10_Step_14: Xac nhan hien thi Label 'Tai khoan nguon'");
		verifyEquals(mobileTopup.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/descript_account"), UIs.MOBILE_TOPUP_DES_ACCOUNT);

		log.info("TC_10_Step_15: Xac nhan hien thi Combobox 'So tai khoan' ");
		verifyEquals(mobileTopup.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/number_account"), originAccount);
		verifyTrue(mobileTopup.isDynamicTextDetailByID(driver, "com.VCB:id/number_account"));

		log.info("TC_10_Step_16: Xac nhan hien thi Label 'So du kha dung'");
		verifyTrue(mobileTopup.isDynamicMessageAndLabelTextDisplayed(driver, UIs.MOBILE_TOPUP_BALANCE_LABEL));

		log.info("TC_10_Step_17: Xac nhan hien thi Label 'Thong tin giao dich'");
		verifyEquals(mobileTopup.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/title_phone_topup"), UIs.MOBILE_TOPUP_TRANSACTION_INFO);

		log.info("TC_10_Step_18: Xac nhan hien thi Label huong dan nhap SDT");
		verifyTrue(mobileTopup.isDynamicMessageAndLabelTextDisplayed(driver, UIs.MOBILE_TOPUP_TRANSACTION_GUIDE));

		log.info("TC_10_Step_19: Xac nhan hien thi Textbox 'So dien thoai duoc nap");
		verifyTrue(mobileTopup.isDynamicEditTexByIdDisplayed(driver, "com.VCB:id/mobile"));
		verifyEquals(mobileTopup.getTextInEditTextFieldByID(driver, "com.VCB:id/mobile"), phone);
		verifyTrue(mobileTopup.isDynamicImageButtonDisplayed(driver, "com.VCB:id/ic_payee"));

		log.info("TC_10_Step_20: Xac nhan hien thi du cac button 'Menh gia the'");
		verifyTrue(mobileTopup.isDynamicMessageAndLabelTextDisplayed(driver, UIs.LIST_UNIT_VALUE[0]));
		verifyTrue(mobileTopup.isDynamicMessageAndLabelTextDisplayed(driver, UIs.LIST_UNIT_VALUE[1]));
		verifyTrue(mobileTopup.isDynamicMessageAndLabelTextDisplayed(driver, UIs.LIST_UNIT_VALUE[2]));
		verifyTrue(mobileTopup.isDynamicMessageAndLabelTextDisplayed(driver, UIs.LIST_UNIT_VALUE[3]));
		verifyTrue(mobileTopup.isDynamicMessageAndLabelTextDisplayed(driver, UIs.LIST_UNIT_VALUE[4]));
		verifyTrue(mobileTopup.isDynamicMessageAndLabelTextDisplayed(driver, UIs.LIST_UNIT_VALUE[5]));

		log.info("TC_10_Step_21: Kiem tra Mac Dinh Focus vao menh gia 100,000");
		verifyTrue(mobileTopup.isDynamicValuesFocus(driver, UIs.LIST_UNIT_VALUE[2]));

		log.info("TC_10_Step_22: Xac nhan hien thi Button 'Tiep tuc");
		verifyTrue(mobileTopup.isDynamicButtonByIdDisplayed(driver, "com.VCB:id/btn_submit"));

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		service.stop();
	}

}
