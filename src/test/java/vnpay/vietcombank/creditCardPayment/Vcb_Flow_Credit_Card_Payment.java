package vnpay.vietcombank.creditCardPayment;

import java.io.IOException;
import java.util.List;

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
import pageObjects.VCBCreditCardPayment;
import vietcombankUI.DynamicPageUIs;
import vietcombank_test_data.Creadit_Card_Payment_Data;
import vietcombank_test_data.HomePage_Data;

public class Vcb_Flow_Credit_Card_Payment extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private VCBCreditCardPayment vcbACreditCardPayment;
	public String soTaiKhoan = "";
	public String soDuKhaDung = "";

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);

		login.scrollDownToText(driver, HomePage_Data.Home_Text_Elements.CREDIT_CARD_PAYMENT);
		login.clickToDynamicButtonLinkOrLinkText(driver, HomePage_Data.Home_Text_Elements.CREDIT_CARD_PAYMENT);
		login.clickToDynamicAcceptButton(driver, "com.VCB:id/btCancel");
		vcbACreditCardPayment = PageFactoryManager.getVCBCreditCardPaymentPageObject(driver);
	}

	@Test
	public void TC_01_ThanhToanTheTinDung() {

		log.info("TC_01_Step_01: Lay thong tin tai so tai khoan");
		soTaiKhoan = vcbACreditCardPayment.getDynamicTextInDropDownByLable(driver, Creadit_Card_Payment_Data.Tittle.TEXT_STK);

		log.info("TC_01_Step_02: Lay thong tin so du tai khoan");
		soDuKhaDung = vcbACreditCardPayment.getDynamicTextByLabel(driver, Creadit_Card_Payment_Data.Tittle.TEXT_SDKD);

		log.info("TC_01_Step_03: Chon so the");
		vcbACreditCardPayment.clickToDynamicLinerLayoutID(driver, "com.VCB:id/llContent1");

		log.info("TC_01_Step_04: verify hien thi man hinh chon so the");
		vcbACreditCardPayment.isDynamicMessageAndLabelTextDisplayed(driver, Creadit_Card_Payment_Data.Tittle.SELECT_CARD);

		log.info("TC_01_Step_05: get list tai khoan tin dung");
		List<String> listAccount = vcbACreditCardPayment.getTextInListElements(driver, DynamicPageUIs.DYNAMIC_TEXTVIEW_BY_LINEARLAYOUT_ID,"com.VCB:id/LinContent");


	}

	@Test
	public void TC_02_ThayDoiThongTin_HoaDonTienDien_XacThucOTP() {
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		service.stop();
	}

}
