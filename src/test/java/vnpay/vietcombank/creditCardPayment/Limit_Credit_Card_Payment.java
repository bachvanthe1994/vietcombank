
package vnpay.vietcombank.creditCardPayment;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.Constants;
import commons.PageFactoryManager;
import commons.WebPageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import model.ServiceLimitInfo;
import model.SourceAccountModel;
import pageObjects.LogInPageObject;
import pageObjects.VCBCreditCardPaymentObject;
import pageObjects.WebBackendSetupPageObject;
import vietcombank_test_data.Creadit_Card_Payment_Data;
import vietcombank_test_data.HomePage_Data;

public class Limit_Credit_Card_Payment extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private VCBCreditCardPaymentObject vcbACreditCardPayment;
	public String account = ""; 
	long surplus, availableBalance, actualAvailableBalance = 0;
	double surplusCurrentcy, availableBalanceCurrentcy, actualAvailableBalanceCurrentcy = 0;
	public String cardNumber = "";
	public String accountCardNumber = "";
	public String cardStatus = "";
	public String payedAmountInPeriod = "";
	public String minimumAmount = "";
	public String amountOutStandingStatement = "";
	public String loansAmountRemain = "";
	public String amountPaid = "";
	WebDriver driverWeb;
	private WebBackendSetupPageObject webBackend;
	SourceAccountModel sourceAccount = new SourceAccountModel();
	String password, phoneNumber = "";
	ServiceLimitInfo inputInfo = new ServiceLimitInfo("1000", "10000", "5000000", "5500000");

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp", "username", "passWeb" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt, String username, String passWeb) throws IOException, InterruptedException, GeneralSecurityException {

		log.info("Before class: Mo backend ");
		startServer();
		driverWeb = openMultiBrowser(Constants.BE_BROWSER_CHROME, Constants.BE_BROWSER_VERSION, Constants.BE_URL);
		webBackend = WebPageFactoryManager.getWebBackendSetupPageObject(driverWeb);
		webBackend.Login_Web_Backend(driverWeb, username, passWeb);

//		webBackend.addMethod(driverWeb, "Thanh toán thẻ tín dụng", inputInfo, Constants.BE_CODE_PACKAGE);
		phoneNumber = getDataInCell(8);
		password = getDataInCell(27);
		
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phoneNumber, password, opt);

		login.scrollDownToText(driver, HomePage_Data.Home_Text_Elements.CREDIT_CARD_PAYMENT);
		login.clickToDynamicButtonLinkOrLinkText(driver, HomePage_Data.Home_Text_Elements.CREDIT_CARD_PAYMENT);
		vcbACreditCardPayment = PageFactoryManager.getVCBCreditCardPaymentPageObject(driver);
		

	}

	@Test
	public void TC_01_Auto_Saving_Nho_Hon_Han_Muc_Toi_THieu_Min_Tran() {
		log.info("TC_01_Step_01: Lay thong tin tai so tai khoan");
		vcbACreditCardPayment.clickToDynamicDropDown(driver, Creadit_Card_Payment_Data.Tittle.SOURCE_ACCOUNT);
		sourceAccount = vcbACreditCardPayment.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);
		account = sourceAccount.account;

		log.info("TC_01_Step_02: Lay thong tin so du tai khoan");
		surplus = convertAvailableBalanceCurrentcyOrFeeToLong(vcbACreditCardPayment.getDynamicTextByLabel(driver, Creadit_Card_Payment_Data.Tittle.TEXT_SDKD));

		log.info("TC_01_Step_03: Chon so the");
		vcbACreditCardPayment.clickToDynamicLinerLayoutID(driver, "com.VCB:id/llContent1");

		log.info("TC_01_Step_04: verify hien thi man hinh chon so the");
		vcbACreditCardPayment.isDynamicMessageAndLabelTextDisplayed(driver, Creadit_Card_Payment_Data.Tittle.SELECT_CARD);

		log.info("TC_01_Step_05: get list tai khoan tin dung");
		List<String> listAccount = vcbACreditCardPayment.getListAccount();

		vcbACreditCardPayment.scrollUpToText(driver, listAccount.get(0));

		for (String account : listAccount) {
			vcbACreditCardPayment.scrollDownToText(driver, account);
			log.info("TC_01_Step_06: Chon tai khoan the tin dung:");
			vcbACreditCardPayment.clickToDynamicButtonLinkOrLinkText(driver, account);
			if (vcbACreditCardPayment.isTextDisplayed(Creadit_Card_Payment_Data.Tittle.NO_HAVE_LOANS)) {
				log.info("TC_01_Step_07: Click btn Dong y:");
				vcbACreditCardPayment.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

				log.info("TC_01_Step_08: Click chon so tai khoan the :");
				vcbACreditCardPayment.clickToDynamicLinerLayoutID(driver, "com.VCB:id/llContent1");

				continue;
				
			} else {
				vcbACreditCardPayment.scrollDownToText(driver, Creadit_Card_Payment_Data.Tittle.TEXT_STK);
				log.info("TC_01_Step_08: Lay thong tin so The");
				cardNumber = vcbACreditCardPayment.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent1");

				log.info("TC_01_Step_09: Lay thong tin so tai khoan the");
				accountCardNumber = vcbACreditCardPayment.getDynamicTextByLabel(driver, Creadit_Card_Payment_Data.Tittle.SO_TK_THE);

				log.info("TC_01_Step_10: Lay thong tin tinh trang the");
				cardStatus = vcbACreditCardPayment.getDynamicTextByLabel(driver, Creadit_Card_Payment_Data.Tittle.TINH_TRANG_THE);

				log.info("TC_01_Step_11: Lay thong tin so tien thanh toan trong ky sao ke: ");
				payedAmountInPeriod = vcbACreditCardPayment.getDynamicTextByLabel(driver, Creadit_Card_Payment_Data.Tittle.TONG_SO_TT_SAO_KE);

				log.info("TC_01_Step_12: So tien toi thieu phai thanh toan ");
				minimumAmount = vcbACreditCardPayment.getDynamicTextByLabel(driver, Creadit_Card_Payment_Data.Tittle.SO_TIEN_TOI_THIEU_TT);

				log.info("TC_01_Step_13: So tien thanh toan ");
				vcbACreditCardPayment.scrollDownToText(driver, "Số tiền thanh toán");
				amountOutStandingStatement = vcbACreditCardPayment.getDynamicTextByLabel(driver, Creadit_Card_Payment_Data.Tittle.SO_TIEN_SK_TT);

				log.info("TC_01_Step_14: So tien du no phai thanh toan ");
				loansAmountRemain = vcbACreditCardPayment.getDynamicTextByLabel(driver, Creadit_Card_Payment_Data.Tittle.SO_TIEN_DU_NO_TT);

				log.info("TC_01_Step_15: So tien thanh toan ");
				amountPaid = vcbACreditCardPayment.getDynamicTextByLabel(driver, Creadit_Card_Payment_Data.Tittle.SO_TIEN_TT);

				if (amountPaid.equals("0 VND")) {
					vcbACreditCardPayment.scrollUpToText(driver, Creadit_Card_Payment_Data.Tittle.TT_GIAO_DICH);

					log.info("TC_01_Step_16: Chon so the ");
					vcbACreditCardPayment.clickToDynamicLinerLayoutID(driver, "com.VCB:id/llContent1");
					continue;
					
				} else {
					break;
					
				}
			}

		}


		ServiceLimitInfo inputInfoMin = new ServiceLimitInfo("1000", (amountOutStandingStatement + 20) + "", (amountOutStandingStatement + 100) + "", "10000000");

		webBackend.getInfoServiceLimit(driverWeb, "	Thanh toán thẻ tín dụng", inputInfoMin, Constants.BE_CODE_PACKAGE);

		log.info("TC_01_Step_17: Click btn tiep tuc");
		vcbACreditCardPayment.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		
		log.info("TC_01_10_Chon phuong thuc xac thuc");
		vcbACreditCardPayment.scrollDownToText(driver, Creadit_Card_Payment_Data.Tittle.ACCURACY_METHOD);
		vcbACreditCardPayment.clickToDynamicDropDown(driver, Creadit_Card_Payment_Data.Tittle.ACCURACY_METHOD);
		vcbACreditCardPayment.clickToDynamicButtonLinkOrLinkText(driver, Creadit_Card_Payment_Data.Tittle.SMS_OTP);

		log.info("TC_01_Step_22: Click btn Tiep tuc ");
		vcbACreditCardPayment.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_23: Xac minh man hinh  Xac thuc giao dich ");
		verifyTrue(vcbACreditCardPayment.isDynamicMessageAndLabelTextDisplayed(driver, Creadit_Card_Payment_Data.Tittle.XAC_THUC_GD));

		log.info("TC_01_Step_24: Nhap OTP ");
		vcbACreditCardPayment.inputToDynamicOtp(driver, Creadit_Card_Payment_Data.Tittle.OTP_NUMBER, "Tiếp tục");

		log.info("----------TC_01_Step_25 Click tiep tuc");
		vcbACreditCardPayment.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

	
		
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		service.stop();
	}

}
