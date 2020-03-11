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

public class Mobile_Topup_Validate_01 extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject home;
	private MobileTopupPageObject mobileTopup;

	private String originAcc = "";
	private String originAccMoney = "";

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
	public void TC_01_KiemTraManHinhHienThi() {

		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_01_Step_01: Keo xuong va click vao phan 'Nap tien dien thoai'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Nạp tiền điện thoại");
		mobileTopup = PageFactoryManager.getMobileTopupPageObject(driver);

		log.info("TC_01_Step_02: Xac nhan hien thi title 'Nap tien dien thoai'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");
		verifyEquals(mobileTopup.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), UIs.MOBILE_TOPUP_TITLE);
		
		log.info("TC_01_Step_03: Xac nhan hien thi Icon 'Back'");
		verifyTrue(mobileTopup.isDynamicImageHomeDisplay(driver, "com.VCB:id/ivTitleLeft"));
		
		log.info("TC_01_Step_04: Xac nhan hien thi Label huong dan");
		verifyEquals(mobileTopup.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvCarrier"), UIs.MOBILE_TOPUP_GUIDE);
		
		log.info("TC_01_Step_05: Xac nhan hien thi Label 'Tai khoan nguon'");
		verifyEquals(mobileTopup.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/descript_account"), UIs.MOBILE_TOPUP_DES_ACCOUNT);
		
		log.info("TC_01_Step_06: Xac nhan hien thi Combobox 'So tai khoan' ");
		verifyTrue(mobileTopup.isDynamicTextDetailByID(driver, "com.VCB:id/number_account"));
		
		log.info("TC_01_Step_07: Xac nhan hien thi Label 'So du kha dung'");
		verifyTrue(mobileTopup.isDynamicMessageAndLabelTextDisplayed(driver, UIs.MOBILE_TOPUP_BALANCE_LABEL));
		
		log.info("TC_01_Step_08: Xac nhan hien thi Label 'Thong tin giao dich'");
		verifyEquals(mobileTopup.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/title_phone_topup"), UIs.MOBILE_TOPUP_TRANSACTION_INFO);
		
		log.info("TC_01_Step_09: Xac nhan hien thi Label huong dan nhap SDT");
		verifyTrue(mobileTopup.isDynamicMessageAndLabelTextDisplayed(driver, UIs.MOBILE_TOPUP_TRANSACTION_GUIDE));
		
		log.info("TC_01_Step_10: Xac nhan hien thi Textbox 'So dien thoai duoc nap");
		verifyTrue(mobileTopup.isDynamicEditTexByIdDisplayed(driver, "com.VCB:id/mobile"));
		verifyTrue(mobileTopup.isDynamicImageButtonDisplayed(driver, "com.VCB:id/ic_payee"));
		
		log.info("TC_01_Step_11: Xac nhan hien thi du cac button 'Menh gia the'");
		verifyTrue(mobileTopup.isDynamicMessageAndLabelTextDisplayed(driver, UIs.LIST_UNIT_VALUE[0]));
		verifyTrue(mobileTopup.isDynamicMessageAndLabelTextDisplayed(driver, UIs.LIST_UNIT_VALUE[1]));
		verifyTrue(mobileTopup.isDynamicMessageAndLabelTextDisplayed(driver, UIs.LIST_UNIT_VALUE[2]));
		verifyTrue(mobileTopup.isDynamicMessageAndLabelTextDisplayed(driver, UIs.LIST_UNIT_VALUE[3]));
		verifyTrue(mobileTopup.isDynamicMessageAndLabelTextDisplayed(driver, UIs.LIST_UNIT_VALUE[4]));
		verifyTrue(mobileTopup.isDynamicMessageAndLabelTextDisplayed(driver, UIs.LIST_UNIT_VALUE[5]));
		
		log.info("TC_01_Step_12: Xac nhan hien thi Button 'Tiep tuc");
		verifyTrue(mobileTopup.isDynamicButtonByIdDisplayed(driver, "com.VCB:id/btn_submit"));
	}

	@Test
	public void TC_02_KiemTraTrangThaiMacDinhNutTiepTuc_AnIconBack() {

		log.info("TC_02_Step_01: Kiem tra trang thai mac dich cua Button 'Tiep tuc'");
		verifyTrue(mobileTopup.isDynamicButtonByIdEnable(driver,"com.VCB:id/btn_submit"));
		
		log.info("TC_02_Step_02: An nut 'Back' de quay ve man hinh chinh");
		mobileTopup.clickToDynamicBottomMenu(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_02_Step_03: Xac nhan da quay ve man hinh chinh");
		verifyTrue(home.isDynamicImageHomeDisplay(driver, "com.VCB:id/menu_1"));
	}

	@Test
	public void TC_03_KiemTraMacDinhHienThiTaiKhoanNguon_UserCoLonHon1TK() {

		log.info("TC_03_Step_01: Keo xuong va click vao phan 'Nap tien dien thoai'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Nạp tiền điện thoại");
		mobileTopup = PageFactoryManager.getMobileTopupPageObject(driver);

		log.info("TC_03_Step_02: Xac dinh 'Tai khoan nguon' dang hien thi");
		originAcc = mobileTopup.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/number_account");

		log.info("TC_03_Step_03: Click vào DrodownList 'Tai khoan nguon' ");
		mobileTopup.clickToTextViewCombobox(driver, "com.VCB:id/number_account");

		log.info("TC_03_Step_04: Xac nhan tai khoan mac dinh hien thi la tai khoan dau tien trong danh sach ");
		verifyEquals(mobileTopup.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/title"), originAcc);
	}

	@Test
	public void TC_04_KiemTraSoDuKhaDung() {

		log.info("TC_04_Step_01: Xac dinh so tien o 'Tai khoan nguon' mac dinh dang hien thi");
		originAccMoney = mobileTopup.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/descript");

		log.info("TC_04_Step_02: Tat Dropdowlist di");
		mobileTopup.clickToTextViewCombobox(driver, "com.VCB:id/cancel_button");

		log.info("TC_04_Step_03: Xac nhan so du kha dung hien thi dung voi so du mac dinh");
		verifyTrue(mobileTopup.isDynamicMessageAndLabelTextDisplayed(driver, originAccMoney));
	}

	@Test
	public void TC_05_KiemTraNhanComboBoxTaiKhoanNguon_CoTruyVanTaiKhoanNguon() {

		log.info("TC_05_Step_01: Click vào DrodownList 'Tai khoan nguon' ");
		mobileTopup.clickToTextViewCombobox(driver, "com.VCB:id/number_account");

		log.info("TC_05_Step_02: Xac nhan hien thi Tai khoan");
		verifyTrue(mobileTopup.isDynamicTextByIdDisplayed(driver, "com.VCB:id/title"));
		
		log.info("TC_05_Step_03: Xac nhan hien thi So du + Don vi tien VND");
		verifyTrue(mobileTopup.isDynamicTextByIdDisplayed(driver, "com.VCB:id/descript"));
		verifyTrue(mobileTopup.isTextDisplayedInListTextElements(driver, "VND", "com.VCB:id/descript"));

		log.info("TC_05_Step_04: Tat Dropdownlist");
		mobileTopup.clickToTextViewCombobox(driver, "com.VCB:id/cancel_button");
	}

	@Test
	public void TC_06_KiemTraChon1TKTuDanhSach() {

		log.info("TC_06_Step_01: Click vào DrodownList 'Tai khoan nguon' ");
		mobileTopup.clickToTextViewCombobox(driver, "com.VCB:id/number_account");

		log.info("TC_06_Step_02: Click vao tai khoan nguon bat ki trong Droddownlist");
		originAccMoney = mobileTopup.getMoneyByAccount(driver, Account_Data.Valid_Account.ACCOUNT2);
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_06_Step_03: Xac nhan tai khoan nguon vua chon da hien thi");
		verifyEquals(mobileTopup.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/number_account"), Account_Data.Valid_Account.ACCOUNT2);
		verifyTrue(mobileTopup.isDynamicMessageAndLabelTextDisplayed(driver, originAccMoney));
	}

	@Test
	public void TC_07_KiemTraDanhSachMenhGiaHienThi() {

		log.info("TC_07_Step_01: Kiem tra danh sach menh gia hien thi");
		verifyTrue(mobileTopup.getListOfSuggestedMoneyOrListText(driver, "com.VCB:id/denom").equals(mobileTopup.arrayToArrayList(UIs.LIST_UNIT_VALUE)));

	}

	@Test
	public void TC_08_KiemTraMacDinhFocus() {

		log.info("TC_08_Step_01: Kiem tra Mac Dinh Focus vao menh gia 100,000");
		verifyTrue(mobileTopup.isDynamicValuesFocus(driver, UIs.LIST_UNIT_VALUE[2]));
	}

	@Test
	public void TC_09_ChonMenhGiaBatKy() {

		log.info("TC_09_Step_01: Click vao menh gia bat ki");
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, UIs.LIST_UNIT_VALUE[4]);

		log.info("TC_09_Step_02: Kiem tra menh gia vua chon duoc focus");
		verifyTrue(mobileTopup.isDynamicValuesFocus(driver, UIs.LIST_UNIT_VALUE[4]));
	}

	@Test
	public void TC_10_KiemTraChonNhieuMenhGia() {

		log.info("TC_10_Step_01: Click vao menh gia bat ki");
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, UIs.LIST_UNIT_VALUE[5]);

		log.info("TC_09_Step_02: Kiem tra chi duy nhat menh gia vua chon duoc focus ");
		verifyEquals(mobileTopup.getNumberOfDynamicElementsFocus(driver, "com.VCB:id/denom"), 1);
		verifyTrue(mobileTopup.isDynamicValuesFocus(driver, UIs.LIST_UNIT_VALUE[5]));

		log.info("TC_10_Step_03: Click vao menh gia bat ki");
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, UIs.LIST_UNIT_VALUE[3]);

		log.info("TC_09_Step_04: Kiem tra chi duy nhat menh gia vua chon duoc focus ");
		verifyEquals(mobileTopup.getNumberOfDynamicElementsFocus(driver, "com.VCB:id/denom"), 1);
		verifyTrue(mobileTopup.isDynamicValuesFocus(driver, UIs.LIST_UNIT_VALUE[3]));

		log.info("TC_10_Step_05: Click vao menh gia bat ki");
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, UIs.LIST_UNIT_VALUE[0]);

		log.info("TC_09_Step_06: Kiem tra chi duy nhat menh gia vua chon duoc focus ");
		verifyEquals(mobileTopup.getNumberOfDynamicElementsFocus(driver, "com.VCB:id/denom"), 1);
		verifyTrue(mobileTopup.isDynamicValuesFocus(driver, UIs.LIST_UNIT_VALUE[0]));
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		service.stop();
	}

}
