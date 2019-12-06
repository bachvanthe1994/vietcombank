package vnpay.vietcombank.login;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import pageObjects.LogInPageObject;
import vietcombank_test_data.LogIn_Data;

public class LoginPageDesign extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;


	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities,
			String appPackage, String appName) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver= openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);

		login = PageFactoryManager.getLoginPageObject(driver);
		
		login.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");

	}

	@Test
	public void TC_01_KiemTraManHinhDangNhap(){
		System.out.println("Start");
		
		log.info("TC_01_1_Text Chao mung quy khach den voi ung dung VCB-Mobile B@nking cua Vietcombank");
//		login.verifyIconChangeLanguage();
		verifyTrue(login.isDynamicMessageAndLabelTextDisplayed(driver, LogIn_Data.Message.WELCOME_MESSAGE));
		log.info("TC_01_2_Textbox Số điện thoại");
		verifyTrue(login.isDynamicTextInInputBoxDisPlayed(driver, LogIn_Data.UI.PHONE_NUMBER));
		verifyTrue(login.isDynamicButtonDisplayed(driver, LogIn_Data.UI.CONTINUE_BUTTON));
		verifyTrue(login.isDynamicMessageAndLabelTextDisplayed(driver, LogIn_Data.UI.ONLINE_REGISTER));
		verifyTrue(login.isDynamicMessageAndLabelTextDisplayed(driver, LogIn_Data.UI.REGISTER));
		verifyTrue(login.isDynamicMessageAndLabelTextDisplayed(driver, LogIn_Data.UI.FIND_ATM));
		
		
	}
	
	@Test
	public void TC_02_KiemTraTextBoxSoDienThoai(){
		System.out.println("Start");
		
		log.info("TC_02_1_Textbox Kiem tra textbox So dien thoai");
		verifyTrue(login.isDynamicTextInInputBoxDisPlayed(driver, LogIn_Data.UI.PHONE_NUMBER));
		
		log.info("TC_02_2_Text Nhan va nhap vao textbox so dien thoai");
		login.inputPhoneNumber("01663056625");
		
		log.info("TC_02_3_Text Kiem tra ky tu max");
		login.verifyPhoneNumberTextBox("01663056625");
		
		
	}

	@Test
	public void TC_03_KiemTraButtonTiepTuc(){
		System.out.println("Start");
		
		log.info("TC_03_1_1 Textbox Phone number trong va nhan button Tiep tuc");
		login.clearPhoneNumber();
		login.clickToDynamicButton(driver, LogIn_Data.UI.CONTINUE_BUTTON);
		
		log.info("TC_03_1_2 Kiem tra hien thi thong bao so dien thoai khong duoc bo trong");
		verifyTrue(login.isDynamicMessageAndLabelTextDisplayed(driver, LogIn_Data.Message.PHONE_NUMBER_NOT_EMPTY));
		verifyTrue(login.isDynamicButtonDisplayed(driver, LogIn_Data.UI.CLOSE_BUTTON));
		
		log.info("TC_03_1_3 Nhan nut Dong");
		login.clickToDynamicButton(driver, LogIn_Data.UI.CLOSE_BUTTON);
		
		log.info("TC_03_2_1 Nhap so dien thoai it hon 10 so va bam Tiep tuc");
		login.inputPhoneNumber("036305662");
		login.clickToDynamicButton(driver, LogIn_Data.UI.CONTINUE_BUTTON);
		
		log.info("TC_03_2_2 Kiem tra hien thi thong bao so dien thoai khong hop le");
		verifyTrue(login.isDynamicMessageAndLabelTextDisplayed(driver, LogIn_Data.Message.PHONE_NUMBER_INVALID));
		verifyTrue(login.isDynamicButtonDisplayed(driver, LogIn_Data.UI.CLOSE_BUTTON));
		
		log.info("TC_03_2_3 Nhan nut Dong");
		login.clickToDynamicButton(driver, LogIn_Data.UI.CLOSE_BUTTON);
		
		log.info("TC_03_3_1 Nhap so dien thoai bat dau bang so khac 0 va bam Tiep tuc");
		login.inputPhoneNumber("3630566251");
		login.clickToDynamicButton(driver, LogIn_Data.UI.CONTINUE_BUTTON);
		
		log.info("TC_03_3_2 Kiem tra hien thi thong bao so dien thoai khong hop le");
		verifyTrue(login.isDynamicMessageAndLabelTextDisplayed(driver, LogIn_Data.Message.PHONE_NUMBER_INVALID));
		verifyTrue(login.isDynamicButtonDisplayed(driver, LogIn_Data.UI.CLOSE_BUTTON));
		
		log.info("TC_03_3_3 Nhan nut Dong");
		login.clickToDynamicButton(driver, LogIn_Data.UI.CLOSE_BUTTON);
		
		log.info("TC_03_4_1 Nhap so dien thoai hop le va bam Tiep tuc");
		login.inputPhoneNumber(LogIn_Data.Login_Account.PHONE);
		login.clickToDynamicButton(driver, LogIn_Data.UI.CONTINUE_BUTTON);
		
		log.info("TC_03_4_2 Kiem tra hien thi thong bao Ung dung kich hoat theo luong hien tai thanh cong");
		verifyTrue(login.isDynamicMessageAndLabelTextDisplayed(driver, LogIn_Data.Message.PHONE_NUMBER_VALID));
		verifyTrue(login.isDynamicButtonDisplayed(driver, LogIn_Data.UI.CANCEL_BUTTON));
		verifyTrue(login.isDynamicButtonDisplayed(driver, LogIn_Data.UI.AGREE_BUTTON));
		
		log.info("TC_03_4_3 Nhan nut Huy");
		login.clickToDynamicButton(driver, LogIn_Data.UI.CANCEL_BUTTON);
		
		log.info("TC_03_5_1 Nhap so dien thoai hop le, chua dang ky hoac da ngung su dung dich vu va bam Tiep tuc");
		login.inputPhoneNumber("0363056625");
		login.clickToDynamicButton(driver, LogIn_Data.UI.CONTINUE_BUTTON);
		
		log.info("TC_03_5_2 Kiem tra hien thi thong bao Quy khach chua dang ky dich vu");
		verifyTrue(login.isDynamicMessageAndLabelTextDisplayed(driver, LogIn_Data.Message.PHONE_NOT_REGISTER));
		verifyTrue(login.isDynamicButtonDisplayed(driver, LogIn_Data.UI.REGISTER_NOW_BUTTON));
		verifyTrue(login.isDynamicMessageAndLabelTextDisplayed(driver, LogIn_Data.UI.CONTINUE_USE_SERVICE_BUTTON));
		
		log.info("TC_03_5_3 Nhan nut Tiep tuc su dung dich vu");
		login.clickToDynamicButtonLinkOrLinkText(driver, LogIn_Data.UI.CONTINUE_USE_SERVICE_BUTTON);
		
		
	}
	

	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
