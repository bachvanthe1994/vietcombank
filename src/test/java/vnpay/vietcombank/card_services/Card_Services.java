package vnpay.vietcombank.card_services;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import pageObjects.HomePageObject;
import pageObjects.LockCardPageObject;
import pageObjects.LogInPageObject;
import vietcombank_test_data.Card_Services_Data.Card_Data;
import vietcombank_test_data.Card_Services_Data.Card_Services_Text;

public class Card_Services extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject home;
	private LockCardPageObject lockCard;
	private String otpNumber,numberCard = "";
	

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException, GeneralSecurityException {
		startServer();
		log.info("Before class: Mo app ");
		if (deviceType.contains("android")) {
			driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		} else if (deviceType.contains("ios")) {
			driver = openIOSApp(deviceName, udid, url);
		}
		login = PageFactoryManager.getLoginPageObject(driver);
		phone = getDataInCell(8).trim();
		pass = getDataInCell(27).trim();
		login.Global_login1(phone, pass, opt);
		otpNumber = opt;
		
		lockCard = PageFactoryManager.LockCardPageObject(driver);
		home = PageFactoryManager.getHomePageObject(driver);

	}

	@Test
	public void TC_01_KhoaTheGhiNoVCB() {

		log.info("TC_01_Step_01: Keo xuong va click vao phan 'Dich vu the'");
		home.scrollDownToText(driver, Card_Services_Text.SHOPPING_TEXT);
		
		log.info("TC_01_Step_02: An vao tab 'Khoa the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, Card_Services_Text.LOCK_CARD_TEXT);
		
		log.info("TC_01_Step_03: Mo DropdownList 'Loai The'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, Card_Services_Text.DEBIT_CARD_TEXT);

		log.info("TC_01_Step_04: Chon 'The ghi no'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, Card_Services_Text.DEBIT_CARD_TEXT);

		log.info("TC_01_Step_05: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, Card_Services_Text.CARD_INFO_TEXT, "2");

		log.info("TC_01_Step_06: An chon the bat ki trong Dropdown");
		lockCard.clickToDynamicTextContains(driver, Card_Data.VCB_DEBIT_CARD_DATA);
		numberCard = lockCard.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/layout1");

		log.info("TC_01_Step_07: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, Card_Services_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_01_Step_08: Hien thi phan 'Xac nhan thong tin'");
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, Card_Services_Text.CARD_TYPE_TEXT), Card_Services_Text.DEBIT_CARD_TEXT);
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, Card_Services_Text.CARD_NUMBER_TEXT), numberCard);
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, Card_Services_Text.REQUEST_TEXT), Card_Services_Text.LOCK_CARD_TEXT);

		log.info("TC_01_Step_09: An button 'Tiep tuc'");
		lockCard.clickToDynamicButton(driver, Card_Services_Text.BUTTON_CONTINUE_TEXT);
		verifyTrue(lockCard.isDynamicMessageAndLabelTextDisplayed(driver, Card_Services_Text.LOCK_CARD_SUCCESS_MESSAGE));
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, Card_Services_Text.CARD_NUMBER_TEXT), numberCard);

		log.info("TC_01_Step_10: An button 'Qua ve man hinh dich vu the'");
		lockCard.clickToDynamicButton(driver, Card_Services_Text.BUTTON_BACK_TO_HOME_TEXT);
		
		log.info("TC_01_Step_12: An vao tab 'Khoa the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, Card_Services_Text.LOCK_CARD_TEXT);
		
		log.info("TC_01_Step_13: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, Card_Services_Text.CARD_INFO_TEXT, "2");

		log.info("TC_01_Step_14: Xac nhan the vua bi khoa bi xoa khoi danh sach");
		verifyFailure(lockCard.isTextDisplayedInListTextElements(driver, numberCard, "com.VCB:id/tvContent"));

		log.info("TC_01_Step_15: Click vào nút 'Dong'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, Card_Services_Text.BUTTON_CLOSE_TEXT);

		log.info("TC_01_Step_16: Click ve 'Trang chu'");
		lockCard.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		
	}

	@Test
	public void TC_02_MoKhoaTheGhiNoQuaOTP() {
		
		log.info("TC_02_Step_01: Click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, Card_Services_Text.CARD_SERVICES_TEXT);

		log.info("TC_02_Step_02: An vao tab 'Mo khoa the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, Card_Services_Text.UNLOCK_CARD_TEXT);
		
		log.info("TC_02_Step_03: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, Card_Services_Text.CARD_INFO_TEXT, "2");

		log.info("TC_02_Step_04: An chon the bat ki trong Dropdown");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, numberCard);

		log.info("TC_02_Step_05: An chon checkbox Xac Nhan Dieu Khoan");
		lockCard.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");

		log.info("TC_02_Step_06: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, Card_Services_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_02_Step_07: Hien thi phan 'Xac nhan thong tin'");
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, Card_Services_Text.CARD_TYPE_TEXT), Card_Services_Text.DEBIT_CARD_TEXT);
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, Card_Services_Text.CARD_NUMBER_TEXT), numberCard);
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, Card_Services_Text.REQUEST_TEXT), Card_Services_Text.UNLOCK_CARD_TEXT);

		log.info("TC_02_Step_08: Mo DropdownList 'Phuong Thuc Xac Thuc'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, Card_Services_Text.SMS_OTP_AUTHEN_TEXT);

		log.info("TC_02_Step_09: Chọn Phuong Thuc 'SMS OTP'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, Card_Services_Text.SMS_OTP_AUTHEN_TEXT);

		log.info("TC_02_Step_10: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, Card_Services_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_02_Step_11: Nhap Ma OTP");
		lockCard.inputToDynamicOtp(driver, otpNumber, Card_Services_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_02_Step_12: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, Card_Services_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_02_Step_13: Hien thi phan 'Xac nhan thong tin'");
		verifyTrue(lockCard.isDynamicMessageAndLabelTextDisplayed(driver, Card_Services_Text.UNLOCK_CARD_SUCCESS_MESSAGE));
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, Card_Services_Text.CARD_TYPE_TEXT), Card_Services_Text.DEBIT_CARD_TEXT);
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, Card_Services_Text.CARD_NUMBER_TEXT), numberCard);

		log.info("TC_02_Step_14: An button 'Quay ve man hinh dich vu the'");
		lockCard.clickToDynamicButton(driver, Card_Services_Text.BUTTON_BACK_TO_HOME_TEXT);
		
		log.info("TC_02_Step_16: An vao tab 'Khoa the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, Card_Services_Text.LOCK_CARD_TEXT);
	
		log.info("TC_02_Step_17: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, Card_Services_Text.CARD_INFO_TEXT, "2");

		log.info("TC_02_Step_18: Xac nhan the vua mo khoa hien thị trong danh sach");
		verifyTrue(lockCard.isDynamicMessageAndLabelTextDisplayed(driver, numberCard));

		log.info("TC_02_Step_19: Click vào nút 'Dong'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, Card_Services_Text.BUTTON_CLOSE_TEXT);

		log.info("TC_02_Step_20: Click ve 'Trang chu'");
		lockCard.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		
		log.info("TC_02_Step_21: Click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, Card_Services_Text.CARD_SERVICES_TEXT);

		log.info("TC_02_Step_22: An vao tab 'Mo khoa the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, Card_Services_Text.UNLOCK_CARD_TEXT);
		
		log.info("TC_02_Step_23: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, Card_Services_Text.CARD_INFO_TEXT, "2");

		log.info("TC_02_Step_24: Xac nhan the vua mo khoa bi xoa khoi danh sach");
		verifyFailure(lockCard.isTextDisplayedInListTextElements(driver, numberCard, "com.VCB:id/tvContent"));

		log.info("TC_02_Step_25: Click vào nút 'Dong'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, Card_Services_Text.BUTTON_CLOSE_TEXT);

		log.info("TC_02_Step_26: Click ve 'Trang chu'");
		lockCard.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		
	}

	@Test
	public void TC_03_KhoaTheGhiNoVisa() {
		
		log.info("TC_03_Step_01: Keo xuong va click vao phan 'Dich vu the'");
		home.scrollDownToText(driver, Card_Services_Text.SHOPPING_TEXT);
		
		log.info("TC_03_Step_02: An vao tab 'Khoa the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, Card_Services_Text.LOCK_CARD_TEXT);
		
		log.info("TC_03_Step_03: Mo DropdownList 'Loai The'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, Card_Services_Text.DEBIT_CARD_TEXT);

		log.info("TC_03_Step_04: Chon 'The ghi no'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, Card_Services_Text.DEBIT_CARD_TEXT);

		log.info("TC_03_Step_05: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, Card_Services_Text.CARD_INFO_TEXT, "2");

		log.info("TC_03_Step_06: An chon the bat ki trong Dropdown");
		lockCard.clickToDynamicTextContains(driver, Card_Data.VISA_DEBIT_CARD_DATA);
		numberCard = lockCard.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/layout1");

		log.info("TC_03_Step_07: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, Card_Services_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_03_Step_08: Hien thi phan 'Xac nhan thong tin'");
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, Card_Services_Text.CARD_TYPE_TEXT), Card_Services_Text.DEBIT_CARD_TEXT);
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, Card_Services_Text.CARD_NUMBER_TEXT), numberCard);
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, Card_Services_Text.REQUEST_TEXT), Card_Services_Text.LOCK_CARD_TEXT);

		log.info("TC_03_Step_09: An button 'Tiep tuc'");
		lockCard.clickToDynamicButton(driver, Card_Services_Text.BUTTON_CONTINUE_TEXT);
		verifyTrue(lockCard.isDynamicMessageAndLabelTextDisplayed(driver, Card_Services_Text.LOCK_CARD_SUCCESS_MESSAGE));
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, Card_Services_Text.CARD_NUMBER_TEXT), numberCard);

		log.info("TC_03_Step_10: An button 'Qua ve man hinh dich vu the'");
		lockCard.clickToDynamicButton(driver, Card_Services_Text.BUTTON_BACK_TO_HOME_TEXT);
		
		log.info("TC_03_Step_12: An vao tab 'Khoa the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, Card_Services_Text.LOCK_CARD_TEXT);
		
		log.info("TC_03_Step_13: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, Card_Services_Text.CARD_INFO_TEXT, "2");

		log.info("TC_03_Step_14: Xac nhan the vua bi khoa bi xoa khoi danh sach");
		verifyFailure(lockCard.isTextDisplayedInListTextElements(driver, numberCard, "com.VCB:id/tvContent"));

		log.info("TC_03_Step_15: Click vào nút 'Dong'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, Card_Services_Text.BUTTON_CLOSE_TEXT);

		log.info("TC_03_Step_16: Click ve 'Trang chu'");
		lockCard.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		
	}

	@Test
	public void TC_04_MoKhoaTheGhiNoQuaOTPTheGiNoVisa() {
		
		log.info("TC_04_Step_01: Click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, Card_Services_Text.CARD_SERVICES_TEXT);

		log.info("TC_04_Step_02: An vao tab 'Mo khoa the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, Card_Services_Text.UNLOCK_CARD_TEXT);
	
		log.info("TC_04_Step_03: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, Card_Services_Text.CARD_INFO_TEXT, "2");

		log.info("TC_04_Step_04: An chon the bat ki trong Dropdown");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, numberCard);

		log.info("TC_04_Step_05: An chon checkbox Xac Nhan Dieu Khoan");
		lockCard.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");

		log.info("TC_04_Step_06: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, Card_Services_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_04_Step_07: Hien thi phan 'Xac nhan thong tin'");
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, Card_Services_Text.CARD_TYPE_TEXT), Card_Services_Text.DEBIT_CARD_TEXT);
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, Card_Services_Text.CARD_NUMBER_TEXT), numberCard);
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, Card_Services_Text.REQUEST_TEXT), Card_Services_Text.UNLOCK_CARD_TEXT);

		log.info("TC_04_Step_08: Mo DropdownList 'Phuong Thuc Xac Thuc'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, Card_Services_Text.SMS_OTP_AUTHEN_TEXT);

		log.info("TC_04_Step_09: Chọn Phuong Thuc 'SMS OTP'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, Card_Services_Text.SMS_OTP_AUTHEN_TEXT);

		log.info("TC_04_Step_10: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, Card_Services_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_04_Step_11: Nhap Ma OTP");
		lockCard.inputToDynamicOtp(driver, otpNumber, Card_Services_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_04_Step_12: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, Card_Services_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_04_Step_13: Hien thi phan 'Xac nhan thong tin'");
		verifyTrue(lockCard.isDynamicMessageAndLabelTextDisplayed(driver, Card_Services_Text.UNLOCK_CARD_SUCCESS_MESSAGE));
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, Card_Services_Text.CARD_TYPE_TEXT), Card_Services_Text.DEBIT_CARD_TEXT);
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, Card_Services_Text.CARD_NUMBER_TEXT), numberCard);

		log.info("TC_04_Step_14: An button 'Qua ve man hinh dich vu the'");
		lockCard.clickToDynamicButton(driver, Card_Services_Text.BUTTON_BACK_TO_HOME_TEXT);
		
		log.info("TC_04_Step_16: An vao tab 'Khoa the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, Card_Services_Text.LOCK_CARD_TEXT);
	
		log.info("TC_04_Step_17: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, Card_Services_Text.CARD_INFO_TEXT, "2");

		log.info("TC_04_Step_18: Xac nhan the vua mo khoa hien thị trong danh sach");
		verifyTrue(lockCard.isDynamicMessageAndLabelTextDisplayed(driver, numberCard));

		log.info("TC_04_Step_19: Click vào nút 'Dong'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, Card_Services_Text.BUTTON_CLOSE_TEXT);

		log.info("TC_04_Step_20: Click ve 'Trang chu'");
		lockCard.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		
		log.info("TC_04_Step_21: Click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, Card_Services_Text.CARD_SERVICES_TEXT);

		log.info("TC_04_Step_22: An vao tab 'Mo khoa the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, Card_Services_Text.UNLOCK_CARD_TEXT);
	
		log.info("TC_04_Step_23: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, Card_Services_Text.CARD_INFO_TEXT, "2");

		log.info("TC_04_Step_24: Xac nhan the vua mo khoa bi xoa khoi danh sach");
		verifyFailure(lockCard.isTextDisplayedInListTextElements(driver, numberCard, "com.VCB:id/tvContent"));

		log.info("TC_04_Step_25: Click vào nút 'Dong'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, Card_Services_Text.BUTTON_CLOSE_TEXT);

		log.info("TC_04_Step_26: Click ve 'Trang chu'");
		lockCard.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		
	}

	@Test
	public void TC_05_KhoaTheUnionPay() {
		
		log.info("TC_05_Step_01: Keo xuong va click vao phan 'Dich vu the'");
		home.scrollDownToText(driver, Card_Services_Text.SHOPPING_TEXT);
		
		log.info("TC_05_Step_02: An vao tab 'Khoa the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, Card_Services_Text.LOCK_CARD_TEXT);
	
		log.info("TC_05_Step_03: Mo DropdownList 'Loai The'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, Card_Services_Text.DEBIT_CARD_TEXT);

		log.info("TC_05_Step_04: Chon 'The ghi no'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, Card_Services_Text.DEBIT_CARD_TEXT);

		log.info("TC_05_Step_05: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, Card_Services_Text.CARD_INFO_TEXT, "2");

		log.info("TC_05_Step_06: An chon the bat ki trong Dropdown");
		lockCard.clickToDynamicTextContains(driver, Card_Data.UNION_PAY_DATA);
		numberCard = lockCard.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/layout1");

		log.info("TC_05_Step_07: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, Card_Services_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_05_Step_08: Hien thi phan 'Xac nhan thong tin'");
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, Card_Services_Text.CARD_TYPE_TEXT), Card_Services_Text.DEBIT_CARD_TEXT);
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, Card_Services_Text.CARD_NUMBER_TEXT), numberCard);
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, Card_Services_Text.REQUEST_TEXT), Card_Services_Text.LOCK_CARD_TEXT);

		log.info("TC_05_Step_09: An button 'Tiep tuc'");
		lockCard.clickToDynamicButton(driver, Card_Services_Text.BUTTON_CONTINUE_TEXT);
		verifyTrue(lockCard.isDynamicMessageAndLabelTextDisplayed(driver, Card_Services_Text.LOCK_CARD_SUCCESS_MESSAGE));
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, Card_Services_Text.CARD_NUMBER_TEXT), numberCard);

		log.info("TC_05_Step_10: An button 'Quan ve man hinh dich vu the'");
		lockCard.clickToDynamicButton(driver, Card_Services_Text.BUTTON_BACK_TO_HOME_TEXT);
		
		log.info("TC_05_Step_12: An vao tab 'Khoa the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, Card_Services_Text.LOCK_CARD_TEXT);
	
		log.info("TC_05_Step_13: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, Card_Services_Text.CARD_INFO_TEXT, "2");

		log.info("TC_05_Step_14: Xac nhan the vua bi khoa bi xoa khoi danh sach");
		verifyFailure(lockCard.isTextDisplayedInListTextElements(driver, numberCard, "com.VCB:id/tvContent"));

		log.info("TC_05_Step_15: Click vào nút 'Dong'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, Card_Services_Text.BUTTON_CLOSE_TEXT);

		log.info("TC_05_Step_16: Click ve 'Trang chu'");
		lockCard.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		
	}

	@Test
	public void TC_06_MoKhoaTheGhiNoQuaOTPTheUnionPay() {

		log.info("TC_06_Step_01: Click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, Card_Services_Text.CARD_SERVICES_TEXT);
		
		log.info("TC_06_Step_02: An vao tab 'Mo khoa the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, Card_Services_Text.UNLOCK_CARD_TEXT);
	
		log.info("TC_06_Step_03: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, Card_Services_Text.CARD_INFO_TEXT, "2");
		
		log.info("TC_06_Step_04: An chon the bat ki trong Dropdown");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, numberCard);

		log.info("TC_06_Step_05: An chon checkbox Xac Nhan Dieu Khoan");
		lockCard.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");

		log.info("TC_06_Step_06: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, Card_Services_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_06_Step_07: Hien thi phan 'Xac nhan thong tin'");
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, Card_Services_Text.CARD_TYPE_TEXT), Card_Services_Text.DEBIT_CARD_TEXT);
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, Card_Services_Text.CARD_NUMBER_TEXT), numberCard);
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, Card_Services_Text.REQUEST_TEXT), Card_Services_Text.UNLOCK_CARD_TEXT);

		log.info("TC_06_Step_08: Mo DropdownList 'Phuong Thuc Xac Thuc'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, Card_Services_Text.SMS_OTP_AUTHEN_TEXT);

		log.info("TC_06_Step_09: Chọn Phuong Thuc 'SMS OTP'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, Card_Services_Text.SMS_OTP_AUTHEN_TEXT);

		log.info("TC_06_Step_10: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, Card_Services_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_06_Step_11: Nhap Ma OTP");
		lockCard.inputToDynamicOtp(driver, otpNumber, Card_Services_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_06_Step_12: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, Card_Services_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_06_Step_13: Hien thi phan 'Xac nhan thong tin'");
		verifyTrue(lockCard.isDynamicMessageAndLabelTextDisplayed(driver, Card_Services_Text.UNLOCK_CARD_SUCCESS_MESSAGE));
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, Card_Services_Text.CARD_TYPE_TEXT), Card_Services_Text.DEBIT_CARD_TEXT);
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, Card_Services_Text.CARD_NUMBER_TEXT), numberCard);

		log.info("TC_06_Step_14: An button 'Quay ve man hinh dich vu the'");
		lockCard.clickToDynamicButton(driver, Card_Services_Text.BUTTON_BACK_TO_HOME_TEXT);
		
		log.info("TC_06_Step_16: An vao tab 'Khoa the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, Card_Services_Text.LOCK_CARD_TEXT);

		log.info("TC_06_Step_17: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, Card_Services_Text.CARD_INFO_TEXT, "2");

		log.info("TC_06_Step_18: Xac nhan the vua mo khoa hien thị trong danh sach");
		verifyTrue(lockCard.isDynamicMessageAndLabelTextDisplayed(driver, numberCard));

		log.info("TC_06_Step_19: Click vào nút 'Dong'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, Card_Services_Text.BUTTON_CLOSE_TEXT);

		log.info("TC_06_Step_20: Click ve 'Trang chu'");
		lockCard.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		
		log.info("TC_06_Step_21: Click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, Card_Services_Text.CARD_SERVICES_TEXT);

		log.info("TC_06_Step_22: An vao tab 'Mo khoa the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, Card_Services_Text.UNLOCK_CARD_TEXT);
	
		log.info("TC_06_Step_23: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, Card_Services_Text.CARD_INFO_TEXT, "2");

		log.info("TC_06_Step_24: Xac nhan the vua mo khoa bi xoa khoi danh sach");
		verifyFailure(lockCard.isTextDisplayedInListTextElements(driver, numberCard, "com.VCB:id/tvContent"));

		log.info("TC_06_Step_25: Click vào nút 'Dong'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, Card_Services_Text.BUTTON_CLOSE_TEXT);

		log.info("TC_06_Step_26: Click ve 'Trang chu'");
		lockCard.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		
	}
//	Hien tai khong co the dau so 9734
//	@Test
	public void TC_07_KhoaTheAmex() {
		
		log.info("TC_07_Step_01: Keo xuong va click vao phan 'Dich vu the'");
		home.scrollDownToText(driver, Card_Services_Text.SHOPPING_TEXT);
	
		log.info("TC_07_Step_02: An vao tab 'Khoa the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, Card_Services_Text.LOCK_CARD_TEXT);
	
		log.info("TC_07_Step_03: Mo DropdownList 'Loai The'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, Card_Services_Text.DEBIT_CARD_TEXT);

		log.info("TC_07_Step_04: Chon 'The ghi no'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, Card_Services_Text.DEBIT_CARD_TEXT);

		log.info("TC_07_Step_05: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, Card_Services_Text.CARD_INFO_TEXT, "2");

		log.info("TC_07_Step_06: An chon the bat ki trong Dropdown");
		lockCard.clickToDynamicTextContains(driver, Card_Data.AMEX_DEBIT_CARD_DATA);
		numberCard = lockCard.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/layout1");

		log.info("TC_07_Step_07: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, Card_Services_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_07_Step_08: Hien thi phan 'Xac nhan thong tin'");
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, Card_Services_Text.CARD_TYPE_TEXT), Card_Services_Text.DEBIT_CARD_TEXT);
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, Card_Services_Text.CARD_NUMBER_TEXT), numberCard);
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, Card_Services_Text.REQUEST_TEXT), Card_Services_Text.LOCK_CARD_TEXT);

		log.info("TC_07_Step_09: An button 'Tiep tuc'");
		lockCard.clickToDynamicButton(driver, Card_Services_Text.BUTTON_CONTINUE_TEXT);
		verifyTrue(lockCard.isDynamicMessageAndLabelTextDisplayed(driver, Card_Services_Text.LOCK_CARD_SUCCESS_MESSAGE));
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, Card_Services_Text.CARD_NUMBER_TEXT), numberCard);

		log.info("TC_07_Step_10: An button 'Qua ve man hinh dich vu the'");
		lockCard.clickToDynamicButton(driver, Card_Services_Text.BUTTON_BACK_TO_HOME_TEXT);
		
		log.info("TC_07_Step_12: An vao tab 'Khoa the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, Card_Services_Text.LOCK_CARD_TEXT);
	
		log.info("TC_07_Step_13: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, Card_Services_Text.CARD_INFO_TEXT, "2");

		log.info("TC_07_Step_14: Xac nhan the vua bi khoa bi xoa khoi danh sach");
		verifyFailure(lockCard.isTextDisplayedInListTextElements(driver, numberCard, "com.VCB:id/tvContent"));

		log.info("TC_07_Step_15: Click vào nút 'Dong'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, Card_Services_Text.BUTTON_CLOSE_TEXT);

		log.info("TC_07_Step_16: Click ve 'Trang chu'");
		lockCard.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

	}

//	@Test
	public void TC_08_MoKhoaTheGhiNoQuaOTPTheAmax() {
		
		
		log.info("TC_08_Step_01: Click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, Card_Services_Text.CARD_SERVICES_TEXT);

		log.info("TC_08_Step_02: An vao tab 'Mo khoa the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, Card_Services_Text.UNLOCK_CARD_TEXT);
	
		log.info("TC_08_Step_03: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, Card_Services_Text.CARD_INFO_TEXT, "2");

		log.info("TC_08_Step_04: Xac nhan the vua bi khoa hien thi trong danh sach");
		verifyTrue(lockCard.isTextDisplayedInListTextElements(driver, numberCard, "com.VCB:id/tvContent"));

		log.info("TC_08_Step_05: An chon the bat ki trong Dropdown");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, numberCard);

		log.info("TC_08_Step_06: An chon checkbox Xac Nhan Dieu Khoan");
		lockCard.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");

		log.info("TC_08_Step_07: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, Card_Services_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_08_Step_08: Hien thi phan 'Xac nhan thong tin'");
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, Card_Services_Text.CARD_TYPE_TEXT), Card_Services_Text.DEBIT_CARD_TEXT);
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, Card_Services_Text.CARD_NUMBER_TEXT), numberCard);
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, Card_Services_Text.REQUEST_TEXT), Card_Services_Text.UNLOCK_CARD_TEXT);

		log.info("TC_08_Step_09: Mo DropdownList 'Phuong Thuc Xac Thuc'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, Card_Services_Text.SMS_OTP_AUTHEN_TEXT);

		log.info("TC_08_Step_10: Chọn Phuong Thuc 'SMS OTP'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, Card_Services_Text.SMS_OTP_AUTHEN_TEXT);

		log.info("TC_08_Step_11: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, Card_Services_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_08_Step_12: Nhap Ma OTP");
		lockCard.inputToDynamicOtp(driver, otpNumber, Card_Services_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_08_Step_13: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, Card_Services_Text.BUTTON_CONTINUE_TEXT);

		log.info("TC_08_Step_14: Hien thi phan 'Xac nhan thong tin'");
		verifyTrue(lockCard.isDynamicMessageAndLabelTextDisplayed(driver, Card_Services_Text.UNLOCK_CARD_SUCCESS_MESSAGE));
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, Card_Services_Text.CARD_TYPE_TEXT), Card_Services_Text.DEBIT_CARD_TEXT);
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, Card_Services_Text.CARD_NUMBER_TEXT), numberCard);

		log.info("TC_08_Step_15: An button 'Qua ve man hinh dich vu the'");
		lockCard.clickToDynamicButton(driver, Card_Services_Text.BUTTON_BACK_TO_HOME_TEXT);
		
		log.info("TC_08_Step_17: An vao tab 'Khoa the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, Card_Services_Text.LOCK_CARD_TEXT);
	
		log.info("TC_08_Step_18: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, Card_Services_Text.CARD_INFO_TEXT, "2");

		log.info("TC_08_Step_19: Xac nhan the vua mo khoa hien thị trong danh sach");
		verifyTrue(lockCard.isTextDisplayedInListTextElements(driver, numberCard, "com.VCB:id/tvContent"));

		log.info("TC_08_Step_20: Click vào nút 'Dong'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, Card_Services_Text.BUTTON_CLOSE_TEXT);

		log.info("TC_08_Step_21: Click ve 'Trang chu'");
		lockCard.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		
		log.info("TC_08_Step_22: Click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, Card_Services_Text.CARD_SERVICES_TEXT);

		log.info("TC_08_Step_23: An vao tab 'Mo khoa the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, Card_Services_Text.UNLOCK_CARD_TEXT);
	
		log.info("TC_08_Step_24: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, Card_Services_Text.CARD_INFO_TEXT, "2");

		log.info("TC_08_Step_25: Xac nhan the vua mo khoa bi xoa khoi danh sach");
		verifyFailure(lockCard.isTextDisplayedInListTextElements(driver, numberCard, "com.VCB:id/tvContent"));

		log.info("TC_08_Step_26: Click vào nút 'Dong'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, Card_Services_Text.BUTTON_CLOSE_TEXT);

		log.info("TC_08_Step_27: Click ve 'Trang chu'");
		lockCard.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();
	}

}
