
package vnpay.vietcombank.creditCardPayment;

import java.io.IOException;
import java.security.GeneralSecurityException;

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
	public long amountPaid;
	WebDriver driverWeb;
	private WebBackendSetupPageObject webBackend;
	SourceAccountModel sourceAccount = new SourceAccountModel();
	String password, phoneNumber = "";
	ServiceLimitInfo inputInfo = new ServiceLimitInfo("1000", "10000", "50000000", "5500000000");

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
	public void TC_01_Credit_Card_Payment_Nho_Hon_Han_Muc_Toi_THieu_Min_Tran() throws InterruptedException {
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
//		List<String> listAccount = vcbACreditCardPayment.getListAccount();
//		vcbACreditCardPayment.scrollUpToText(driver, listAccount.get(0));
//		for (String account : listAccount) {
//		vcbACreditCardPayment.scrollDownToText(driver, account);
		vcbACreditCardPayment.scrollDownToText(driver, "546285xxxxxxx040");
		log.info("TC_01_Step_06: Chon tai khoan the tin dung:");
//			vcbACreditCardPayment.clickToDynamicButtonLinkOrLinkText(driver, account);
		vcbACreditCardPayment.clickToDynamicButtonLinkOrLinkText(driver, "546285xxxxxxx040");
//			if (vcbACreditCardPayment.isTextDisplayed(Creadit_Card_Payment_Data.Tittle.NO_HAVE_LOANS)) {
//				log.info("TC_01_Step_07: Click btn Dong y:");
//				vcbACreditCardPayment.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
//
//				log.info("TC_01_Step_08: Click chon so tai khoan the :");
//				vcbACreditCardPayment.clickToDynamicLinerLayoutID(driver, "com.VCB:id/llContent1");
//
//				continue;
//				
//			} else {
//				vcbACreditCardPayment.scrollDownToText(driver, Creadit_Card_Payment_Data.Tittle.TEXT_STK);
//				log.info("TC_01_Step_08: Lay thong tin so The");
//				cardNumber = vcbACreditCardPayment.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent1");
//
//				log.info("TC_01_Step_09: Lay thong tin so tai khoan the");
//				accountCardNumber = vcbACreditCardPayment.getDynamicTextByLabel(driver, Creadit_Card_Payment_Data.Tittle.SO_TK_THE);

		log.info("TC_01_Step_15: So tien thanh toan ");
		amountPaid = convertAvailableBalanceCurrentcyOrFeeToLong(vcbACreditCardPayment.getAccountNumber(driver, "com.VCB:id/valueTienGetThanhToan"));

//				if (amountPaid.equals("0 VND")) {
//					vcbACreditCardPayment.scrollUpToText(driver, Creadit_Card_Payment_Data.Tittle.TT_GIAO_DICH);
//
//					log.info("TC_01_Step_16: Chon so the ");
//					vcbACreditCardPayment.clickToDynamicLinerLayoutID(driver, "com.VCB:id/llContent1");
//					continue;
//					
//				} else {
//					break;
//					
//				}
//			}

//		}

		ServiceLimitInfo inputInfoMin = new ServiceLimitInfo("1000", addCommasToLong((amountPaid + 20) + ""), addCommasToLong((amountPaid + 100) + ""), "10000000000");

		webBackend.getInfoServiceLimit(driverWeb, "Thanh toán thẻ tín dụng", inputInfoMin, Constants.BE_CODE_PACKAGE);

		log.info("TC_01_Step_17: Click btn tiep tuc");
		vcbACreditCardPayment.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		verifyEquals(vcbACreditCardPayment.getDynamicTextView(driver, "com.VCB:id/tvContent"), "Chuyển tiền không thành công. Số tiền giao dịch nhỏ hơn hạn mức " + addCommasToLong((amountPaid) + 20 + "") + " VND/1 lần, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp.");

		vcbACreditCardPayment.clickToDynamicContinue(driver, "com.VCB:id/btOK");

	}

	@Test
	public void TC_02_Credit_Card_Payment_Lon_Hon_Han_Muc_Toi_Da_Max_Tran() throws InterruptedException {

		ServiceLimitInfo inputInfoMax = new ServiceLimitInfo("1000", addCommasToLong((amountPaid - 200) + ""), addCommasToLong((amountPaid - 100) + "") + "", "10000000000");

		webBackend.getInfoServiceLimit(driverWeb, "Thanh toán thẻ tín dụng", inputInfoMax, Constants.BE_CODE_PACKAGE);

		log.info("TC_01_Step_17: Click btn tiep tuc");
		vcbACreditCardPayment.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		verifyEquals(vcbACreditCardPayment.getDynamicTextView(driver, "com.VCB:id/tvContent"), "Chuyển tiền không thành công. Số tiền giao dịch lớn hơn hạn mức " + addCommasToLong((amountPaid) - 100 + "") + " VND/1 lần, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline của Vietcombank để được trợ giúp.");

		webBackend.getInfoServiceLimit(driverWeb, "Thanh toán thẻ tín dụng", inputInfo, Constants.BE_CODE_PACKAGE);
		vcbACreditCardPayment.clickToDynamicContinue(driver, "com.VCB:id/btOK");


	}

	@Test
	public void TC_03_Credit_Card_Payment_Lon_Hon_Han_Muc_Toi_Da_Max_Nhom_Giao_Dich() throws InterruptedException {

		webBackend.Setup_Assign_Services_Type_Limit(driverWeb, Constants.BE_CODE_PACKAGE, "Thanh toán thẻ tín dụng", addCommasToLong((inputInfo.minTran) + ""));

		log.info("TC_01_Step_17: Click btn tiep tuc");
		vcbACreditCardPayment.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		verifyEquals(vcbACreditCardPayment.getDynamicTextView(driver, "com.VCB:id/tvContent"), "Chuyển tiền không thành công. Số tiền giao dịch lớn hơn hạn mức " + addCommasToLong((inputInfo.minTran) + "") + " VND/1 ngày của nhóm dịch vụ, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline của Vietcombank để được trợ giúp.");

		vcbACreditCardPayment.clickToDynamicContinue(driver, "com.VCB:id/btOK");

		webBackend.Reset_Setup_Assign_Services_Type_Limit(driverWeb, Constants.BE_CODE_PACKAGE, "Thanh toán thẻ tín dụng");

	}

	@Test
	public void TC_04_Credit_Card_Payment_Lon_Hon_Han_Muc_Toi_Da_Max_Goi_Giao_Dich() throws InterruptedException {

		webBackend.Setup_Add_Method_Package_Total_Limit(driverWeb, Constants.BE_CODE_PACKAGE, Constants.METHOD_OTP, inputInfo.minTran);

		log.info("TC_01_Step_17: Click btn tiep tuc");
		vcbACreditCardPayment.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		verifyEquals(vcbACreditCardPayment.getDynamicTextView(driver, "com.VCB:id/tvContent"), "Chuyển tiền không thành công. Số tiền giao dịch lớn hơn hạn mức " + addCommasToLong((inputInfo.minTran) + "") + " VND/1 ngày của gói dịch vụ, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline của Vietcombank để được trợ giúp.");

		vcbACreditCardPayment.clickToDynamicContinue(driver, "com.VCB:id/btOK");

		webBackend.Reset_Package_Total_Limit(driverWeb, Constants.BE_CODE_PACKAGE, Constants.METHOD_OTP);

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		service.stop();
	}

}
