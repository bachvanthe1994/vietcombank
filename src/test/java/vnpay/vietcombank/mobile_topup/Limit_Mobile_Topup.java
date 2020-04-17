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
import pageObjects.LogInPageObject;
import pageObjects.MobileTopupPageObject;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.MobileTopupPage_Data;
import vietcombank_test_data.MobileTopupPage_Data.UIs;

public class Limit_Mobile_Topup extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private MobileTopupPageObject mobileTopup;


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
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, "Nạp tiền điện thoại");
	}

	@Test
	
	//Set up tren BE hạn mức tối thiểu là 50 000
	public void TC_01_NapTienNhoHonHanMucToiThieuGiaoDich(String pass, String phone) {
		
		log.info("TC_01_Step_02: Click vào DrodownList 'Tai khoan nguon' ");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");
//		mobileTopup.clickToTextViewCombobox(driver, "com.VCB:id/number_account");

		log.info("TC_01_Step_03: Chon tai khoan nguon");
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_01_Step_04: Click vao menh gia 30,000");
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, UIs.LIST_UNIT_VALUE[0]);

		log.info("TC_01_Step_05: An nut 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btn_submit");

		log.info("TC_02_Step_15: Verify hien thi man hinh thong bao loi");
		mobileTopup.isDynamicMessageAndLabelTextDisplayed(driver,MobileTopupPage_Data.MESSEGE_ERROR_Limit.MESSEGE_LOWER_LIMIT_A_TRAN );

		log.info("TC_02_Step_16: Click btn Dong");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
	}
	
	@Test
	public void TC_02_NapTienCaoHonHanMucToiDaGiaoDich(String pass, String phone) {
		
		log.info("TC_01_Step_02: Click vào DrodownList 'Tai khoan nguon' ");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");
//		mobileTopup.clickToTextViewCombobox(driver, "com.VCB:id/number_account");
		
		log.info("TC_01_Step_03: Chon tai khoan nguon");
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);
		
		log.info("TC_01_Step_04: Click vao menh gia 30,000");
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, UIs.LIST_UNIT_VALUE[3]);
		
		log.info("TC_01_Step_05: An nut 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btn_submit");
		
		log.info("TC_02_Step_15: Verify hien thi man hinh thong bao loi");
		mobileTopup.isDynamicMessageAndLabelTextDisplayed(driver,MobileTopupPage_Data.MESSEGE_ERROR_Limit.MESSEGE_HIGHER_LIMIT_A_TRAN );
		
		log.info("TC_02_Step_16: Click btn Dong");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
	}
	
	@Test
	//Han muc nap tien dien thoai toi da tronf 1 ngay laf 400k
public void TC_03_NapTienCaoHonHanMucToiDaGiaoDichTrongNgay() {
		
		log.info("TC_01_Step_02: Click vào DrodownList 'Tai khoan nguon' ");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");
//		mobileTopup.clickToTextViewCombobox(driver, "com.VCB:id/number_account");
		
		log.info("TC_01_Step_03: Chon tai khoan nguon");
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);
		
		log.info("TC_01_Step_04: Click vao menh gia 30,000");
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, UIs.LIST_UNIT_VALUE[5]);
		
		log.info("TC_01_Step_05: An nut 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btn_submit");
		
		log.info("TC_02_Step_15: Verify hien thi man hinh thong bao loi");
		mobileTopup.isDynamicMessageAndLabelTextDisplayed(driver,MobileTopupPage_Data.MESSEGE_ERROR_Limit.MESSEGE_HIGHER_LIMIT_A_DAY );
		
		log.info("TC_02_Step_16: Click btn Dong");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
	}
	
	//Truoc khi chay can setup BE
	//Han muc nap tien dien thoai toi da Nhom Giao Dich 200k
	public void TC_04_NapTienVuotQuaHanMucToiDaNhomGiaoDich() {
		
		log.info("TC_01_Step_02: Click vào DrodownList 'Tai khoan nguon' ");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");
//		mobileTopup.clickToTextViewCombobox(driver, "com.VCB:id/number_account");
		
		log.info("TC_01_Step_03: Chon tai khoan nguon");
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);
		
		log.info("TC_01_Step_04: Click vao menh gia 30,000");
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, UIs.LIST_UNIT_VALUE[5]);
		
		log.info("TC_01_Step_05: An nut 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btn_submit");
		
		log.info("TC_02_Step_15: Verify hien thi man hinh thong bao loi");
		mobileTopup.isDynamicMessageAndLabelTextDisplayed(driver,MobileTopupPage_Data.MESSEGE_ERROR_Limit.MESSEGE_HIGHER_LIMIT_GROUP );
		
		log.info("TC_02_Step_16: Click btn Dong");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
	}
	
	
	//Truoc khi chay can setup BE
	//Han muc nap tien dien thoai toi da Goi Giao Dich 200k
	public void TC_05_NapTienVuotQuaHanMucToiDaGoiGiaoDich() {
		
		log.info("TC_01_Step_02: Click vào DrodownList 'Tai khoan nguon' ");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");
//		mobileTopup.clickToTextViewCombobox(driver, "com.VCB:id/number_account");
		
		log.info("TC_01_Step_03: Chon tai khoan nguon");
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);
		
		log.info("TC_01_Step_04: Click vao menh gia 30,000");
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, UIs.LIST_UNIT_VALUE[5]);
		
		log.info("TC_01_Step_05: An nut 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btn_submit");
		
		log.info("TC_02_Step_15: Verify hien thi man hinh thong bao loi");
		mobileTopup.isDynamicMessageAndLabelTextDisplayed(driver,MobileTopupPage_Data.MESSEGE_ERROR_Limit.MESSEGE_HIGHER_LIMIT_PACKAGE );
		
		log.info("TC_02_Step_16: Click btn Dong");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
	}

	
	@AfterClass(alwaysRun = true)
	public void afterClass() {
		service.stop();
	}

}
