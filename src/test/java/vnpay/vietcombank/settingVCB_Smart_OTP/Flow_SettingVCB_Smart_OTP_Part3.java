package vnpay.vietcombank.settingVCB_Smart_OTP;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import pageObjects.ConfirmMethodObject;
import pageObjects.ElectricBillPageObject;
import pageObjects.InternetADSLPageObject;
import pageObjects.LandLinePhoneChargePageObject;
import pageObjects.LogInPageObject;
import pageObjects.MobileTopupPageObject;
import pageObjects.PayBillTelevisionPageObject;
import pageObjects.PostpaidMobileBillPageObject;
import pageObjects.VCBCreditCardPaymentObject;
import pageObjects.WaterBillPageObject;
import vietcombankUI.DynamicPageUIs;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.Creadit_Card_Payment_Data;
import vietcombank_test_data.Electric_Bills_Data;
import vietcombank_test_data.Internet_ADSL_Data;
import vietcombank_test_data.LandLinePhoneCharge_Data;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.MobileTopupPage_Data.UIs;
import vietcombank_test_data.PayBillTelevison_Data;
import vietcombank_test_data.Postpaid_Mobile_Bill_Data;
import vietcombank_test_data.SettingVCBSmartOTP_Data;
import vietcombank_test_data.Water_Bills_Data;


public class Flow_SettingVCB_Smart_OTP_Part3 extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private ConfirmMethodObject smartOTP;
	
	

	
	

	private long amountTranfer, costTranfer;

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
		smartOTP = PageFactoryManager.getConfirmMethodObject(driver);
		
	}
	
	@Test
	public void TC_01_CaiDatPhuongThucXacThucOTP() {
		log.info("TC_02_Step: Click menu header");
		smartOTP.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_02_Step: Click cai dat");
		smartOTP.clickToDynamicButtonLinkOrLinkText(driver, "Cài đặt");

		log.info("TC_02_Step: Click cai dat Smart OTP");
		smartOTP.clickToDynamicButtonLinkOrLinkText(driver, "Cài đặt VCB-Smart OTP");

		log.info("TC_02_Step: Click cai dat cho tai khoan");
		smartOTP.clickToDynamicTextFollowText(driver, "Chưa kích hoạt");

		log.info("TC_02_Step: Click toi dong y");
		smartOTP.clickToTextID(driver, "com.VCB:id/rule");

		log.info("TC_02_Step_click button dong y");
		smartOTP.clickToDynamicButton(driver, "Đồng ý");

		log.info("TC_02_Step_Nhap mat khau");
		smartOTP.inputToDynamicInputBox(driver, LogIn_Data.Login_Account.Smart_OTP, "Nhập mật khẩu");

		log.info("TC_02_Step_Nhap lai mat khau");
		smartOTP.inputToDynamicInputBox(driver, LogIn_Data.Login_Account.Smart_OTP, "Nhập lại mật khẩu");

		log.info("TC_02_Step_click button tiep tuc");
		smartOTP.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_02_Step_Nhap ma xac thuc");
		smartOTP.inputToDynamicSmartOtp(driver, "666888", "com.VCB:id/otp");

		log.info("TC_02_Step_click button tiep tuc");
		smartOTP.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_02_Step_Verify cai dat thanh cong");
		smartOTP.waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, "Cài đặt VCB-Smart OTP");
		verifyEquals(smartOTP.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/state_vnpay"), "Đã kích hoạt");

		log.info("TC_02_Step_click button quay lai cai dat");
		smartOTP.clickToDynamicImageViewID(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_02_Click button home");
		smartOTP.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	// Thanh Toan ADSL
	@Test


	public void TC_111_HuyKichHoatVCBSmartOPT() {

		log.info("------------------------TC_01_Step_01: Click menu header-------------------------------");
		smartOTP.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("-----------------------TC_01_Step_02: Click thanh Cai dat VCB-Smart OTP------------------------");
		smartOTP.clickToDynamicButtonLinkOrLinkText(driver, "Cài đặt");

		log.info("---------------------------TC_01_Step_03: Click Cai dat VCB Smart OTP---------------------");
		smartOTP.waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, "Cài đặt VCB-Smart OTP");
		smartOTP.clickToDynamicButtonLinkOrLinkText(driver, "Cài đặt VCB-Smart OTP");

		log.info("-------------------------------TC_01_Step_04: Verify man hinh cai dat VCB Smart OTP------------------------");
		smartOTP.isDynamicMessageAndLabelTextDisplayed(driver, "Cài đặt VCB-Smart OTP");

		log.info("---------------------------------TC_02_Step_01: Click btn Huy Cai dat-------------------------");
		smartOTP.clickToDynamicButtonLinkOrLinkText(driver, "Hủy");

		log.info("----------------------------TC_02_Step_02: Verify hien thi popup xac nhan huy cai dat OTP----------------");
		smartOTP.isDynamicMessageAndLabelTextDisplayed(driver, SettingVCBSmartOTP_Data.MESSEGE_CONFIRM_CANCEL);

		log.info("---------------------------TC_02_Step_03: Verify hien thi popup xac nhan huy cai dat OTP----------------");
		smartOTP.clickToDynamicButtonLinkOrLinkText(driver, "Có");

//		log.info("-------------------------------TC_02_Step_04: Verify xac nhan huy Smart OTP thanh cong----------------");
//		smartOTP.isDynamicMessageAndLabelTextDisplayed(driver, SettingVCBSmartOTP_Data.MESSEGE_CANCEL_SMART_OTP);

		log.info("--------------------------TC_02_Step_05: verify Trang thai dã kich hoat Smart OTP--------------------");
		smartOTP.waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, "Cài đặt VCB-Smart OTP");
		smartOTP.isDynamicMessageAndLabelTextDisplayed(driver, "Chưa kích hoạt");
	}

}
