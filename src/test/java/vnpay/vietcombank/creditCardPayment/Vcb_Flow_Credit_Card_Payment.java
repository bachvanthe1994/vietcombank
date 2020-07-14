
package vnpay.vietcombank.creditCardPayment;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.Constants;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import model.SourceAccountModel;
import pageObjects.LogInPageObject;
import pageObjects.SettingVCBSmartOTPPageObject;
import pageObjects.TransactionReportPageObject;
import pageObjects.VCBCreditCardPaymentObject;
import vietcombank_test_data.Creadit_Card_Payment_Data;
import vietcombank_test_data.HomePage_Data;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransactionReport_Data;

public class Vcb_Flow_Credit_Card_Payment extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private VCBCreditCardPaymentObject vcbACreditCardPayment;
	private TransactionReportPageObject transReport;
	private SettingVCBSmartOTPPageObject setupSmartOTP;
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
	public String transactionCode, smartOTP = "";
	SourceAccountModel sourceAccount = new SourceAccountModel();
	String password, phoneNumber = "";

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException, GeneralSecurityException {
		startServer();
		smartOTP = getDataInCell(6);
		phoneNumber = getDataInCell(8);
		password = getDataInCell(27);

		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phoneNumber, password, opt);

		login.scrollDownToText(driver, HomePage_Data.Home_Text_Elements.CREDIT_CARD_PAYMENT);
		login.clickToDynamicButtonLinkOrLinkText(driver, HomePage_Data.Home_Text_Elements.CREDIT_CARD_PAYMENT);
		vcbACreditCardPayment = PageFactoryManager.getVCBCreditCardPaymentPageObject(driver);
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		setupSmartOTP = PageFactoryManager.getSettingVCBSmartOTPPageObject(driver);

		setupSmartOTP.setupSmartOTP(LogIn_Data.Login_Account.Smart_OTP, smartOTP);

	}

	@Test
	public void TC_01_ThanhToanTheTinDung_VND_SMS_OTP() {
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

		log.info("TC_01_Step_17: Click btn tiep tuc");
		vcbACreditCardPayment.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_18: Verify hien thi man hinh Xac nhan thong tin ");
		verifyTrue(vcbACreditCardPayment.isDynamicMessageAndLabelTextDisplayed(driver, Creadit_Card_Payment_Data.Tittle.XAC_NHAN_TT));

		log.info("TC_01_Step_19: Xac minh tai khoan nguon: ");
		verifyEquals(account, vcbACreditCardPayment.getDynamicTextByLabel(driver, Creadit_Card_Payment_Data.Tittle.TK_NGUON));

		log.info("TC_01_Step_21: Verify so tien thanh toan ");
		verifyTrue(vcbACreditCardPayment.getDynamicTextByLabel(driver, Creadit_Card_Payment_Data.Tittle.SO_TIEN_TT).contains(loansAmountRemain));

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

		log.info("TC_01_Step_26: Xac minh man hinh thong bao giao dich thanh cong");
		verifyTrue(vcbACreditCardPayment.isDynamicMessageAndLabelTextDisplayed(driver, Creadit_Card_Payment_Data.Tittle.GD_THANH_CONG));

		log.info("TC_01_Step_27: Xac minh thong tin so the ");
		verifyEquals(vcbACreditCardPayment.getDynamicTextByLabel(driver, Creadit_Card_Payment_Data.Tittle.SO_THE), cardNumber);

		log.info("TC_01_Step_05: Lay thong tin ma giao dich ");
		transactionCode = vcbACreditCardPayment.getDynamicTextByLabel(driver, Creadit_Card_Payment_Data.Tittle.MA_GIAO_DICH);

		log.info("TC_01_13_Click Thuc hien giao dich moi");
		vcbACreditCardPayment.clickToDynamicButton(driver, Creadit_Card_Payment_Data.Tittle.NEW_TRANSACTION_PERFORM);

		log.info("TC_01_14_Kiem tra so du kha dung luc sau");
		vcbACreditCardPayment.clickToDynamicDropDown(driver, Creadit_Card_Payment_Data.Tittle.SOURCE_ACCOUNT);
		vcbACreditCardPayment.clickToDynamicButtonLinkOrLinkText(driver, account);

		actualAvailableBalance = convertAvailableBalanceCurrentcyOrFeeToLong(vcbACreditCardPayment.getDynamicTextInTransactionDetail(driver, Creadit_Card_Payment_Data.Tittle.TEXT_SDKD));
		availableBalance = canculateAvailableBalances(surplus, convertAvailableBalanceCurrentcyOrFeeToLong(amountPaid), 0);
		verifyEquals(actualAvailableBalance, availableBalance);

	}

	@Test
	public void TC_02_BaoCaoGiaoDich_VND_SMS_OTP() {
		log.info("TC_02_Step_01: Click back");
		transReport.clickToDynamicBackIcon(driver, Creadit_Card_Payment_Data.Tittle.CREDIT_CARD_PAYMENT);

		log.info("TC_02_Step_02_Click btn menu");
		transReport.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_02_Step_03_Click Chon muc bao cao giao dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransactionReport_Data.ReportTitle.TRANSACTION_REPORT);

		log.info("TC_02_Step_04_Click Chon cac loai giao dich");
		transReport.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/llSelectTransType");

		log.info("TC_02_Step_05_Scroll to thanh toan the tin dung");
		transReport.scrollDownToText(driver, Creadit_Card_Payment_Data.Tittle.TT_THE_TD);

		log.info("TC_02_Step_06_Scroll to thanh toan the tin dung");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, Creadit_Card_Payment_Data.Tittle.TT_THE_TD);

		log.info("TC_02_6: Click Chon Tai Khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_02_7: Chon tai Khoan chuyen");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, account);

		log.info("TC_02_Step_08: Click tim kiem ");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_02_Step_09: Xac minh so tien thanh toan ");
		verifyEquals(vcbACreditCardPayment.getDynamicTextView("com.VCB:id/tvMoney").replace("- ", ""), amountPaid);

		log.info("TC_02_Step_10: Verify ban ghi Thanh toan ");
		transReport.clickToDynamicLinerLayoutID(driver, "com.VCB:id/llRoot");

		log.info("TC_02_Step_11: Verofy hien thi man hinh chi tiet bao cao giao dich ");
		verifyTrue(transReport.isDynamicMessageAndLabelTextDisplayed(driver, TransactionReport_Data.ReportTitle.TRANSACTION_DETAIL));

		log.info("TC_02_Step_12: Verify ma thanh toan ");
		verifyEquals(transReport.getDynamicTextByLabel(driver, TransactionReport_Data.ReportTitle.TRANSACTION_NUMBER), transactionCode);

		log.info("TC_02_Step_13: Verify tai khoan trich no");
		verifyEquals(transReport.getDynamicTextByLabel(driver, TransactionReport_Data.ReportTitle.ACCOUNT_CARD), account);

		log.info("TC_02_Step_14: Verify ma thanh toan ");
		verifyEquals(transReport.getDynamicTextByLabel(driver, TransactionReport_Data.ReportTitle.CARD_NUMBER), cardNumber);

		log.info("TC_02_Step_15: Verify ma thanh toan ");
		verifyEquals(transReport.getDynamicTextByLabel(driver, Creadit_Card_Payment_Data.Tittle.SO_TK_THE), accountCardNumber);

		log.info("TC_02_16: Click  nut Back");
		transReport.clickToDynamicBackIcon(driver, TransactionReport_Data.ReportTitle.TRANSACTION_DETAIL);

		log.info("TC_02_17: Click  nut Back");
		transReport.clickToDynamicBackIcon(driver, TransactionReport_Data.ReportTitle.TRANSACTION_REPORT);

		log.info("TC_02_18: Click  nut Home");
		transReport.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");

	}

	@Test
	public void TC_03_ThanhToanTheTinDung_USD_SMS_OTP() {
		log.info("TC_03_Step_01: Lay thong tin tai so tai khoan");
		vcbACreditCardPayment.clickToDynamicDropDown(driver, Creadit_Card_Payment_Data.Tittle.SOURCE_ACCOUNT);
		sourceAccount = vcbACreditCardPayment.chooseSourceAccount(driver, Constants.MONEY_CHECK_USD, Constants.USD_CURRENCY);
		account = sourceAccount.account;

		log.info("TC_03_Step_02: Lay thong tin so du tai khoan");
		surplusCurrentcy = convertAvailableBalanceCurrentcyToDouble(vcbACreditCardPayment.getDynamicTextByLabel(driver, Creadit_Card_Payment_Data.Tittle.TEXT_SDKD));

		log.info("TC_03_Step_03: Chon so the");
		vcbACreditCardPayment.clickToDynamicLinerLayoutID(driver, "com.VCB:id/llContent1");

		log.info("TC_03_Step_04: verify hien thi man hinh chon so the");
		vcbACreditCardPayment.isDynamicMessageAndLabelTextDisplayed(driver, Creadit_Card_Payment_Data.Tittle.SELECT_CARD);

		log.info("TC_03_Step_05: get list tai khoan tin dung");
		List<String> listAccount = vcbACreditCardPayment.getListAccount();

		vcbACreditCardPayment.scrollUpToText(driver, listAccount.get(0));

		for (String account : listAccount) {
			vcbACreditCardPayment.scrollDownToText(driver, account);
			log.info("TC_03_Step_06: Chon tai khoan the tin dung:");
			vcbACreditCardPayment.clickToDynamicButtonLinkOrLinkText(driver, account);
			if (vcbACreditCardPayment.isTextDisplayed(Creadit_Card_Payment_Data.Tittle.NO_HAVE_LOANS)) {
				log.info("TC_03_Step_07: Click btn Dong y:");
				vcbACreditCardPayment.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

				log.info("TC_03_Step_08: Click chon so tai khoan the :");
				vcbACreditCardPayment.clickToDynamicLinerLayoutID(driver, "com.VCB:id/llContent1");

				continue;

			} else {
				vcbACreditCardPayment.scrollDownToText(driver, Creadit_Card_Payment_Data.Tittle.TEXT_STK);
				log.info("TC_03_Step_08: Lay thong tin so The");
				cardNumber = vcbACreditCardPayment.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent1");

				log.info("TC_03_Step_09: Lay thong tin so tai khoan the");
				accountCardNumber = vcbACreditCardPayment.getDynamicTextByLabel(driver, Creadit_Card_Payment_Data.Tittle.SO_TK_THE);

				log.info("TC_03_Step_10: Lay thong tin tinh trang the");
				cardStatus = vcbACreditCardPayment.getDynamicTextByLabel(driver, Creadit_Card_Payment_Data.Tittle.TINH_TRANG_THE);

				log.info("TC_03_Step_11: Lay thong tin so tien thanh toan trong ky sao ke: ");
				payedAmountInPeriod = vcbACreditCardPayment.getDynamicTextByLabel(driver, Creadit_Card_Payment_Data.Tittle.TONG_SO_TT_SAO_KE);

				log.info("TC_03_Step_12: So tien toi thieu phai thanh toan ");
				minimumAmount = vcbACreditCardPayment.getDynamicTextByLabel(driver, Creadit_Card_Payment_Data.Tittle.SO_TIEN_TOI_THIEU_TT);

				log.info("TC_03_Step_13: So tien thanh toan ");
				vcbACreditCardPayment.scrollDownToText(driver, "Số tiền thanh toán");
				amountOutStandingStatement = vcbACreditCardPayment.getDynamicTextByLabel(driver, Creadit_Card_Payment_Data.Tittle.SO_TIEN_SK_TT);

				log.info("TC_03_Step_14: So tien du no phai thanh toan ");
				loansAmountRemain = vcbACreditCardPayment.getDynamicTextByLabel(driver, Creadit_Card_Payment_Data.Tittle.SO_TIEN_DU_NO_TT);

				log.info("TC_03_Step_15: So tien thanh toan ");
				amountPaid = vcbACreditCardPayment.getDynamicTextByLabel(driver, Creadit_Card_Payment_Data.Tittle.SO_TIEN_TT);

				if (amountPaid.equals("0 VND")) {
					vcbACreditCardPayment.scrollUpToText(driver, Creadit_Card_Payment_Data.Tittle.TT_GIAO_DICH);

					log.info("TC_03_Step_16: Chon so the ");
					vcbACreditCardPayment.clickToDynamicLinerLayoutID(driver, "com.VCB:id/llContent1");
					continue;

				} else {
					break;

				}
			}

		}

		log.info("TC_03_Step_17: Click btn tiep tuc");
		vcbACreditCardPayment.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_03_Step_18: Verify hien thi man hinh Xac nhan thong tin ");
		verifyTrue(vcbACreditCardPayment.isDynamicMessageAndLabelTextDisplayed(driver, Creadit_Card_Payment_Data.Tittle.XAC_NHAN_TT));

		log.info("TC_03_Step_19: Xac minh tai khoan nguon: ");
		verifyEquals(account, vcbACreditCardPayment.getDynamicTextByLabel(driver, Creadit_Card_Payment_Data.Tittle.TK_NGUON));

		log.info("TC_03_Step_21: Verify so tien thanh toan ");
		verifyTrue(vcbACreditCardPayment.getDynamicTextByLabel(driver, Creadit_Card_Payment_Data.Tittle.SO_TIEN_TT).contains(loansAmountRemain));

		log.info("TC_03_10_Chon phuong thuc xac thuc");
		vcbACreditCardPayment.scrollDownToText(driver, Creadit_Card_Payment_Data.Tittle.ACCURACY_METHOD);
		vcbACreditCardPayment.clickToDynamicDropDown(driver, Creadit_Card_Payment_Data.Tittle.ACCURACY_METHOD);
		vcbACreditCardPayment.clickToDynamicButtonLinkOrLinkText(driver, Creadit_Card_Payment_Data.Tittle.SMS_OTP);

		log.info("TC_03_Step_22: Click btn Tiep tuc ");
		vcbACreditCardPayment.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_03_Step_23: Xac minh man hinh  Xac thuc giao dich ");
		verifyTrue(vcbACreditCardPayment.isDynamicMessageAndLabelTextDisplayed(driver, Creadit_Card_Payment_Data.Tittle.XAC_THUC_GD));

		log.info("TC_03_Step_24: Nhap OTP ");
		vcbACreditCardPayment.inputToDynamicOtp(driver, Creadit_Card_Payment_Data.Tittle.OTP_NUMBER, "Tiếp tục");

		log.info("----------TC_03_Step_25 Click tiep tuc");
		vcbACreditCardPayment.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_03_Step_26: Xac minh man hinh thong bao giao dich thanh cong");
		verifyTrue(vcbACreditCardPayment.isDynamicMessageAndLabelTextDisplayed(driver, Creadit_Card_Payment_Data.Tittle.GD_THANH_CONG));

		log.info("TC_03_Step_27: Xac minh thong tin so the ");
		verifyEquals(vcbACreditCardPayment.getDynamicTextByLabel(driver, Creadit_Card_Payment_Data.Tittle.SO_THE), cardNumber);

		log.info("TC_03_Step_05: Lay thong tin ma giao dich ");
		transactionCode = vcbACreditCardPayment.getDynamicTextByLabel(driver, Creadit_Card_Payment_Data.Tittle.MA_GIAO_DICH);

		log.info("TC_03_13_Click Thuc hien giao dich moi");
		vcbACreditCardPayment.clickToDynamicButton(driver, Creadit_Card_Payment_Data.Tittle.NEW_TRANSACTION_PERFORM);

		log.info("TC_03_14_Kiem tra so du kha dung luc sau");
		vcbACreditCardPayment.clickToDynamicDropDown(driver, Creadit_Card_Payment_Data.Tittle.SOURCE_ACCOUNT);
		vcbACreditCardPayment.clickToDynamicButtonLinkOrLinkText(driver, account);
		actualAvailableBalanceCurrentcy = convertAvailableBalanceCurrentcyToDouble(vcbACreditCardPayment.getDynamicTextInTransactionDetail(driver, Creadit_Card_Payment_Data.Tittle.TEXT_SDKD));
		availableBalanceCurrentcy = canculateAvailableBalancesCurrentcy(surplusCurrentcy, convertAvailableBalanceCurrentcyToDouble(amountPaid.split("~")[0]), 0);
		verifyEquals(actualAvailableBalanceCurrentcy, availableBalanceCurrentcy);

	}

	@Test
	public void TC_04_BaoCaoGiaoDich_USD_SMS_OTP() {
		log.info("TC_04_Step_01: Click back");
		transReport.clickToDynamicBackIcon(driver, Creadit_Card_Payment_Data.Tittle.CREDIT_CARD_PAYMENT);

		log.info("TC_04_Step_02_Click btn menu");
		transReport.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_04_Step_03_Click Chon muc bao cao giao dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransactionReport_Data.ReportTitle.TRANSACTION_REPORT);

		log.info("TC_04_Step_04_Click Chon cac loai giao dich");
		transReport.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/llSelectTransType");

		log.info("TC_04_Step_05_Scroll to thanh toan the tin dung");
		transReport.scrollDownToText(driver, Creadit_Card_Payment_Data.Tittle.TT_THE_TD);

		log.info("TC_04_Step_06_Scroll to thanh toan the tin dung");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, Creadit_Card_Payment_Data.Tittle.TT_THE_TD);

		log.info("TC_04_6: Click Chon Tai Khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_04_7: Chon tai Khoan chuyen");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, account);

		log.info("TC_04_Step_08: Click tim kiem ");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_04_Step_09: Xac minh so tien thanh toan ");
		verifyEquals(vcbACreditCardPayment.getDynamicTextView("com.VCB:id/tvMoney").replace("- ", ""), amountPaid.split(" ~ ")[0]);

		log.info("TC_04_Step_10: Verify ban ghi Thanh toan ");
		transReport.clickToDynamicLinerLayoutID(driver, "com.VCB:id/llRoot");

		log.info("TC_04_Step_11: Verofy hien thi man hinh chi tiet bao cao giao dich ");
		verifyTrue(transReport.isDynamicMessageAndLabelTextDisplayed(driver, TransactionReport_Data.ReportTitle.TRANSACTION_DETAIL));

		log.info("TC_04_Step_12: Verify ma thanh toan ");
		verifyEquals(transReport.getDynamicTextByLabel(driver, TransactionReport_Data.ReportTitle.TRANSACTION_NUMBER), transactionCode);

		log.info("TC_04_Step_13: Verify tai khoan trich no");
		verifyEquals(transReport.getDynamicTextByLabel(driver, TransactionReport_Data.ReportTitle.ACCOUNT_CARD), account);

		log.info("TC_04_Step_14: Verify ma thanh toan ");
		verifyEquals(transReport.getDynamicTextByLabel(driver, TransactionReport_Data.ReportTitle.CARD_NUMBER), cardNumber);

		log.info("TC_04_Step_15: Verify ma thanh toan ");
		verifyEquals(transReport.getDynamicTextByLabel(driver, Creadit_Card_Payment_Data.Tittle.SO_TK_THE), accountCardNumber);

		log.info("TC_04_16: Click  nut Back");
		transReport.clickToDynamicBackIcon(driver, TransactionReport_Data.ReportTitle.TRANSACTION_DETAIL);

		log.info("TC_04_17: Click  nut Back");
		transReport.clickToDynamicBackIcon(driver, TransactionReport_Data.ReportTitle.TRANSACTION_REPORT);

		log.info("TC_04_18: Click  nut Home");
		transReport.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");

	}

	@Test
	public void TC_05_ThanhToanTheTinDung_VND_Smart_OTP() {
		log.info("TC_05_Step_01: Lay thong tin tai so tai khoan");
		vcbACreditCardPayment.clickToDynamicDropDown(driver, Creadit_Card_Payment_Data.Tittle.SOURCE_ACCOUNT);
		sourceAccount = vcbACreditCardPayment.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);
		account = sourceAccount.account;

		log.info("TC_05_Step_02: Lay thong tin so du tai khoan");
		surplus = convertAvailableBalanceCurrentcyOrFeeToLong(vcbACreditCardPayment.getDynamicTextByLabel(driver, Creadit_Card_Payment_Data.Tittle.TEXT_SDKD));

		log.info("TC_05_Step_03: Chon so the");
		vcbACreditCardPayment.clickToDynamicLinerLayoutID(driver, "com.VCB:id/llContent1");

		log.info("TC_05_Step_04: verify hien thi man hinh chon so the");
		vcbACreditCardPayment.isDynamicMessageAndLabelTextDisplayed(driver, Creadit_Card_Payment_Data.Tittle.SELECT_CARD);

		log.info("TC_05_Step_05: get list tai khoan tin dung");
		List<String> listAccount = vcbACreditCardPayment.getListAccount();

		vcbACreditCardPayment.scrollUpToText(driver, listAccount.get(0));

		for (String account : listAccount) {
			vcbACreditCardPayment.scrollDownToText(driver, account);
			log.info("TC_05_Step_06: Chon tai khoan the tin dung:");
			vcbACreditCardPayment.clickToDynamicButtonLinkOrLinkText(driver, account);
			if (vcbACreditCardPayment.isTextDisplayed(Creadit_Card_Payment_Data.Tittle.NO_HAVE_LOANS)) {
				log.info("TC_05_Step_07: Click btn Dong y:");
				vcbACreditCardPayment.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

				log.info("TC_05_Step_08: Click chon so tai khoan the :");
				vcbACreditCardPayment.clickToDynamicLinerLayoutID(driver, "com.VCB:id/llContent1");

				continue;

			} else {
				vcbACreditCardPayment.scrollDownToText(driver, Creadit_Card_Payment_Data.Tittle.TEXT_STK);
				log.info("TC_05_Step_08: Lay thong tin so The");
				cardNumber = vcbACreditCardPayment.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent1");

				log.info("TC_05_Step_09: Lay thong tin so tai khoan the");
				accountCardNumber = vcbACreditCardPayment.getDynamicTextByLabel(driver, Creadit_Card_Payment_Data.Tittle.SO_TK_THE);

				log.info("TC_05_Step_10: Lay thong tin tinh trang the");
				cardStatus = vcbACreditCardPayment.getDynamicTextByLabel(driver, Creadit_Card_Payment_Data.Tittle.TINH_TRANG_THE);

				log.info("TC_05_Step_11: Lay thong tin so tien thanh toan trong ky sao ke: ");
				payedAmountInPeriod = vcbACreditCardPayment.getDynamicTextByLabel(driver, Creadit_Card_Payment_Data.Tittle.TONG_SO_TT_SAO_KE);

				log.info("TC_05_Step_12: So tien toi thieu phai thanh toan ");
				minimumAmount = vcbACreditCardPayment.getDynamicTextByLabel(driver, Creadit_Card_Payment_Data.Tittle.SO_TIEN_TOI_THIEU_TT);

				log.info("TC_05_Step_13: So tien thanh toan ");
				vcbACreditCardPayment.scrollDownToText(driver, "Số tiền thanh toán");
				amountOutStandingStatement = vcbACreditCardPayment.getDynamicTextByLabel(driver, Creadit_Card_Payment_Data.Tittle.SO_TIEN_SK_TT);

				log.info("TC_05_Step_14: So tien du no phai thanh toan ");
				loansAmountRemain = vcbACreditCardPayment.getDynamicTextByLabel(driver, Creadit_Card_Payment_Data.Tittle.SO_TIEN_DU_NO_TT);

				log.info("TC_05_Step_15: So tien thanh toan ");
				amountPaid = vcbACreditCardPayment.getDynamicTextByLabel(driver, Creadit_Card_Payment_Data.Tittle.SO_TIEN_TT);

				if (amountPaid.equals("0 VND")) {
					vcbACreditCardPayment.scrollUpToText(driver, Creadit_Card_Payment_Data.Tittle.TT_GIAO_DICH);

					log.info("TC_05_Step_16: Chon so the ");
					vcbACreditCardPayment.clickToDynamicLinerLayoutID(driver, "com.VCB:id/llContent1");
					continue;

				} else {
					break;

				}
			}

		}

		log.info("TC_05_Step_17: Click btn tiep tuc");
		vcbACreditCardPayment.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_05_Step_18: Verify hien thi man hinh Xac nhan thong tin ");
		verifyTrue(vcbACreditCardPayment.isDynamicMessageAndLabelTextDisplayed(driver, Creadit_Card_Payment_Data.Tittle.XAC_NHAN_TT));

		log.info("TC_05_Step_19: Xac minh tai khoan nguon: ");
		verifyEquals(account, vcbACreditCardPayment.getDynamicTextByLabel(driver, Creadit_Card_Payment_Data.Tittle.TK_NGUON));

		log.info("TC_05_Step_21: Verify so tien thanh toan ");
		verifyTrue(vcbACreditCardPayment.getDynamicTextByLabel(driver, Creadit_Card_Payment_Data.Tittle.SO_TIEN_TT).contains(loansAmountRemain));

		log.info("TC_05_10_Chon phuong thuc xac thuc");
		vcbACreditCardPayment.scrollDownToText(driver, Creadit_Card_Payment_Data.Tittle.ACCURACY_METHOD);
		vcbACreditCardPayment.clickToDynamicDropDown(driver, Creadit_Card_Payment_Data.Tittle.ACCURACY_METHOD);
		vcbACreditCardPayment.clickToDynamicButtonLinkOrLinkText(driver, Creadit_Card_Payment_Data.Tittle.SMART_OTP);

		log.info("TC_05_Step_22: Click btn Tiep tuc ");
		vcbACreditCardPayment.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_05_Step_23: Xac minh man hinh  Xac thuc giao dich ");
		verifyTrue(vcbACreditCardPayment.isDynamicMessageAndLabelTextDisplayed(driver, Creadit_Card_Payment_Data.Tittle.XAC_THUC_GD));

		log.info("TC_05_Step_24: Nhap smart otp ");
		vcbACreditCardPayment.inputToDynamicSmartOTP(driver, LogIn_Data.Login_Account.Smart_OTP, "com.VCB:id/otp");

		vcbACreditCardPayment.clickToDynamicButton(driver, "com.VCB:id/btContinue");

		log.info("----------TC_05_Step_25 Click tiep tuc");
		vcbACreditCardPayment.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_05_Step_26: Xac minh man hinh thong bao giao dich thanh cong");
		verifyTrue(vcbACreditCardPayment.isDynamicMessageAndLabelTextDisplayed(driver, Creadit_Card_Payment_Data.Tittle.GD_THANH_CONG));

		log.info("TC_05_Step_27: Xac minh thong tin so the ");
		verifyEquals(vcbACreditCardPayment.getDynamicTextByLabel(driver, Creadit_Card_Payment_Data.Tittle.SO_THE), cardNumber);

		log.info("TC_05_Step_05: Lay thong tin ma giao dich ");
		transactionCode = vcbACreditCardPayment.getDynamicTextByLabel(driver, Creadit_Card_Payment_Data.Tittle.MA_GIAO_DICH);

		log.info("TC_05_13_Click Thuc hien giao dich moi");
		vcbACreditCardPayment.clickToDynamicButton(driver, Creadit_Card_Payment_Data.Tittle.NEW_TRANSACTION_PERFORM);

		log.info("TC_05_14_Kiem tra so du kha dung luc sau");
		vcbACreditCardPayment.clickToDynamicDropDown(driver, Creadit_Card_Payment_Data.Tittle.SOURCE_ACCOUNT);
		vcbACreditCardPayment.clickToDynamicButtonLinkOrLinkText(driver, account);

		actualAvailableBalance = convertAvailableBalanceCurrentcyOrFeeToLong(vcbACreditCardPayment.getDynamicTextInTransactionDetail(driver, Creadit_Card_Payment_Data.Tittle.TEXT_SDKD));
		availableBalance = canculateAvailableBalances(surplus, convertAvailableBalanceCurrentcyOrFeeToLong(amountPaid), 0);
		verifyEquals(actualAvailableBalance, availableBalance);

	}

	@Test
	public void TC_06_BaoCaoGiaoDich_VND_Smart_OTP() {
		log.info("TC_06_Step_01: Click back");
		transReport.clickToDynamicBackIcon(driver, Creadit_Card_Payment_Data.Tittle.CREDIT_CARD_PAYMENT);

		log.info("TC_06_Step_02_Click btn menu");
		transReport.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_06_Step_03_Click Chon muc bao cao giao dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransactionReport_Data.ReportTitle.TRANSACTION_REPORT);

		log.info("TC_06_Step_04_Click Chon cac loai giao dich");
		transReport.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/llSelectTransType");

		log.info("TC_06_Step_05_Scroll to thanh toan the tin dung");
		transReport.scrollDownToText(driver, Creadit_Card_Payment_Data.Tittle.TT_THE_TD);

		log.info("TC_06_Step_06_Scroll to thanh toan the tin dung");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, Creadit_Card_Payment_Data.Tittle.TT_THE_TD);

		log.info("TC_06_6: Click Chon Tai Khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_06_7: Chon tai Khoan chuyen");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, account);

		log.info("TC_06_Step_08: Click tim kiem ");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_06_Step_09: Xac minh so tien thanh toan ");
		verifyEquals(vcbACreditCardPayment.getDynamicTextView("com.VCB:id/tvMoney").replace("- ", ""), amountPaid);

		log.info("TC_06_Step_10: Verify ban ghi Thanh toan ");
		transReport.clickToDynamicLinerLayoutID(driver, "com.VCB:id/llRoot");

		log.info("TC_06_Step_11: Verofy hien thi man hinh chi tiet bao cao giao dich ");
		verifyTrue(transReport.isDynamicMessageAndLabelTextDisplayed(driver, TransactionReport_Data.ReportTitle.TRANSACTION_DETAIL));

		log.info("TC_06_Step_12: Verify ma thanh toan ");
		verifyEquals(transReport.getDynamicTextByLabel(driver, TransactionReport_Data.ReportTitle.TRANSACTION_NUMBER), transactionCode);

		log.info("TC_06_Step_13: Verify tai khoan trich no");
		verifyEquals(transReport.getDynamicTextByLabel(driver, TransactionReport_Data.ReportTitle.ACCOUNT_CARD), account);

		log.info("TC_06_Step_14: Verify ma thanh toan ");
		verifyEquals(transReport.getDynamicTextByLabel(driver, TransactionReport_Data.ReportTitle.CARD_NUMBER), cardNumber);

		log.info("TC_06_Step_15: Verify ma thanh toan ");
		verifyEquals(transReport.getDynamicTextByLabel(driver, Creadit_Card_Payment_Data.Tittle.SO_TK_THE), accountCardNumber);

		log.info("TC_06_16: Click  nut Back");
		transReport.clickToDynamicBackIcon(driver, TransactionReport_Data.ReportTitle.TRANSACTION_DETAIL);

		log.info("TC_06_17: Click  nut Back");
		transReport.clickToDynamicBackIcon(driver, TransactionReport_Data.ReportTitle.TRANSACTION_REPORT);

		log.info("TC_06_18: Click  nut Home");
		transReport.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");

	}

	@Test
	public void TC_07_ThanhToanTheTinDung_USD_Smart_OTP() {
		log.info("TC_07_Step_01: Lay thong tin tai so tai khoan");
		vcbACreditCardPayment.clickToDynamicDropDown(driver, Creadit_Card_Payment_Data.Tittle.SOURCE_ACCOUNT);
		sourceAccount = vcbACreditCardPayment.chooseSourceAccount(driver, Constants.MONEY_CHECK_USD, Constants.USD_CURRENCY);
		account = sourceAccount.account;

		log.info("TC_07_Step_02: Lay thong tin so du tai khoan");
		surplusCurrentcy = convertAvailableBalanceCurrentcyToDouble(vcbACreditCardPayment.getDynamicTextByLabel(driver, Creadit_Card_Payment_Data.Tittle.TEXT_SDKD));

		log.info("TC_07_Step_03: Chon so the");
		vcbACreditCardPayment.clickToDynamicLinerLayoutID(driver, "com.VCB:id/llContent1");

		log.info("TC_07_Step_04: verify hien thi man hinh chon so the");
		vcbACreditCardPayment.isDynamicMessageAndLabelTextDisplayed(driver, Creadit_Card_Payment_Data.Tittle.SELECT_CARD);

		log.info("TC_07_Step_05: get list tai khoan tin dung");
		List<String> listAccount = vcbACreditCardPayment.getListAccount();

		vcbACreditCardPayment.scrollUpToText(driver, listAccount.get(0));

		for (String account : listAccount) {
			vcbACreditCardPayment.scrollDownToText(driver, account);
			log.info("TC_07_Step_06: Chon tai khoan the tin dung:");
			vcbACreditCardPayment.clickToDynamicButtonLinkOrLinkText(driver, account);
			if (vcbACreditCardPayment.isTextDisplayed(Creadit_Card_Payment_Data.Tittle.NO_HAVE_LOANS)) {
				log.info("TC_07_Step_07: Click btn Dong y:");
				vcbACreditCardPayment.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

				log.info("TC_07_Step_08: Click chon so tai khoan the :");
				vcbACreditCardPayment.clickToDynamicLinerLayoutID(driver, "com.VCB:id/llContent1");

				continue;

			} else {
				vcbACreditCardPayment.scrollDownToText(driver, Creadit_Card_Payment_Data.Tittle.TEXT_STK);
				log.info("TC_07_Step_08: Lay thong tin so The");
				cardNumber = vcbACreditCardPayment.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent1");

				log.info("TC_07_Step_09: Lay thong tin so tai khoan the");
				accountCardNumber = vcbACreditCardPayment.getDynamicTextByLabel(driver, Creadit_Card_Payment_Data.Tittle.SO_TK_THE);

				log.info("TC_07_Step_10: Lay thong tin tinh trang the");
				cardStatus = vcbACreditCardPayment.getDynamicTextByLabel(driver, Creadit_Card_Payment_Data.Tittle.TINH_TRANG_THE);

				log.info("TC_07_Step_11: Lay thong tin so tien thanh toan trong ky sao ke: ");
				payedAmountInPeriod = vcbACreditCardPayment.getDynamicTextByLabel(driver, Creadit_Card_Payment_Data.Tittle.TONG_SO_TT_SAO_KE);

				log.info("TC_07_Step_12: So tien toi thieu phai thanh toan ");
				minimumAmount = vcbACreditCardPayment.getDynamicTextByLabel(driver, Creadit_Card_Payment_Data.Tittle.SO_TIEN_TOI_THIEU_TT);

				log.info("TC_07_Step_13: So tien thanh toan ");
				vcbACreditCardPayment.scrollDownToText(driver, "Số tiền thanh toán");
				amountOutStandingStatement = vcbACreditCardPayment.getDynamicTextByLabel(driver, Creadit_Card_Payment_Data.Tittle.SO_TIEN_SK_TT);

				log.info("TC_07_Step_14: So tien du no phai thanh toan ");
				loansAmountRemain = vcbACreditCardPayment.getDynamicTextByLabel(driver, Creadit_Card_Payment_Data.Tittle.SO_TIEN_DU_NO_TT);

				log.info("TC_07_Step_15: So tien thanh toan ");
				amountPaid = vcbACreditCardPayment.getDynamicTextByLabel(driver, Creadit_Card_Payment_Data.Tittle.SO_TIEN_TT);

				if (amountPaid.equals("0 VND")) {
					vcbACreditCardPayment.scrollUpToText(driver, Creadit_Card_Payment_Data.Tittle.TT_GIAO_DICH);

					log.info("TC_07_Step_16: Chon so the ");
					vcbACreditCardPayment.clickToDynamicLinerLayoutID(driver, "com.VCB:id/llContent1");
					continue;

				} else {
					break;

				}
			}

		}

		log.info("TC_07_Step_17: Click btn tiep tuc");
		vcbACreditCardPayment.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_07_Step_18: Verify hien thi man hinh Xac nhan thong tin ");
		verifyTrue(vcbACreditCardPayment.isDynamicMessageAndLabelTextDisplayed(driver, Creadit_Card_Payment_Data.Tittle.XAC_NHAN_TT));

		log.info("TC_07_Step_19: Xac minh tai khoan nguon: ");
		verifyEquals(account, vcbACreditCardPayment.getDynamicTextByLabel(driver, Creadit_Card_Payment_Data.Tittle.TK_NGUON));

		log.info("TC_07_Step_21: Verify so tien thanh toan ");
		verifyTrue(vcbACreditCardPayment.getDynamicTextByLabel(driver, Creadit_Card_Payment_Data.Tittle.SO_TIEN_TT).contains(loansAmountRemain));

		log.info("TC_07_10_Chon phuong thuc xac thuc");
		vcbACreditCardPayment.scrollDownToText(driver, Creadit_Card_Payment_Data.Tittle.ACCURACY_METHOD);
		vcbACreditCardPayment.clickToDynamicDropDown(driver, Creadit_Card_Payment_Data.Tittle.ACCURACY_METHOD);
		vcbACreditCardPayment.clickToDynamicButtonLinkOrLinkText(driver, Creadit_Card_Payment_Data.Tittle.SMART_OTP);

		log.info("TC_07_Step_22: Click btn Tiep tuc ");
		vcbACreditCardPayment.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_07_Step_23: Xac minh man hinh  Xac thuc giao dich ");
		verifyTrue(vcbACreditCardPayment.isDynamicMessageAndLabelTextDisplayed(driver, Creadit_Card_Payment_Data.Tittle.XAC_THUC_GD));

		vcbACreditCardPayment.inputToDynamicSmartOTP(driver, LogIn_Data.Login_Account.Smart_OTP, "com.VCB:id/otp");

		vcbACreditCardPayment.clickToDynamicButton(driver, "com.VCB:id/btContinue");

		log.info("----------TC_07_Step_25 Click tiep tuc");
		vcbACreditCardPayment.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_07_Step_26: Xac minh man hinh thong bao giao dich thanh cong");
		verifyTrue(vcbACreditCardPayment.isDynamicMessageAndLabelTextDisplayed(driver, Creadit_Card_Payment_Data.Tittle.GD_THANH_CONG));

		log.info("TC_07_Step_27: Xac minh thong tin so the ");
		verifyEquals(vcbACreditCardPayment.getDynamicTextByLabel(driver, Creadit_Card_Payment_Data.Tittle.SO_THE), cardNumber);

		log.info("TC_07_Step_05: Lay thong tin ma giao dich ");
		transactionCode = vcbACreditCardPayment.getDynamicTextByLabel(driver, Creadit_Card_Payment_Data.Tittle.MA_GIAO_DICH);

		log.info("TC_07_13_Click Thuc hien giao dich moi");
		vcbACreditCardPayment.clickToDynamicButton(driver, Creadit_Card_Payment_Data.Tittle.NEW_TRANSACTION_PERFORM);

		log.info("TC_07_14_Kiem tra so du kha dung luc sau");
		vcbACreditCardPayment.clickToDynamicDropDown(driver, Creadit_Card_Payment_Data.Tittle.SOURCE_ACCOUNT);
		vcbACreditCardPayment.clickToDynamicButtonLinkOrLinkText(driver, account);
		actualAvailableBalanceCurrentcy = convertAvailableBalanceCurrentcyToDouble(vcbACreditCardPayment.getDynamicTextInTransactionDetail(driver, Creadit_Card_Payment_Data.Tittle.TEXT_SDKD));
		availableBalanceCurrentcy = canculateAvailableBalancesCurrentcy(surplusCurrentcy, convertAvailableBalanceCurrentcyToDouble(amountPaid.split("~")[0]), 0);
		verifyEquals(actualAvailableBalanceCurrentcy, availableBalanceCurrentcy);

	}

	@Test
	public void TC_08_BaoCaoGiaoDich_USD_Smart_OTP() {
		log.info("TC_08_Step_01: Click back");
		transReport.clickToDynamicBackIcon(driver, Creadit_Card_Payment_Data.Tittle.CREDIT_CARD_PAYMENT);

		log.info("TC_08_Step_02_Click btn menu");
		transReport.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_08_Step_03_Click Chon muc bao cao giao dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransactionReport_Data.ReportTitle.TRANSACTION_REPORT);

		log.info("TC_08_Step_04_Click Chon cac loai giao dich");
		transReport.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/llSelectTransType");

		log.info("TC_08_Step_05_Scroll to thanh toan the tin dung");
		transReport.scrollDownToText(driver, Creadit_Card_Payment_Data.Tittle.TT_THE_TD);

		log.info("TC_08_Step_06_Scroll to thanh toan the tin dung");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, Creadit_Card_Payment_Data.Tittle.TT_THE_TD);

		log.info("TC_08_6: Click Chon Tai Khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_08_7: Chon tai Khoan chuyen");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, account);

		log.info("TC_08_Step_08: Click tim kiem ");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_08_Step_09: Xac minh so tien thanh toan ");
		verifyEquals(vcbACreditCardPayment.getDynamicTextView("com.VCB:id/tvMoney").replace("- ", ""), amountPaid.split(" ~ ")[0]);

		log.info("TC_08_Step_10: Verify ban ghi Thanh toan ");
		transReport.clickToDynamicLinerLayoutID(driver, "com.VCB:id/llRoot");

		log.info("TC_08_Step_11: Verofy hien thi man hinh chi tiet bao cao giao dich ");
		verifyTrue(transReport.isDynamicMessageAndLabelTextDisplayed(driver, TransactionReport_Data.ReportTitle.TRANSACTION_DETAIL));

		log.info("TC_08_Step_12: Verify ma thanh toan ");
		verifyEquals(transReport.getDynamicTextByLabel(driver, TransactionReport_Data.ReportTitle.TRANSACTION_NUMBER), transactionCode);

		log.info("TC_08_Step_13: Verify tai khoan trich no");
		verifyEquals(transReport.getDynamicTextByLabel(driver, TransactionReport_Data.ReportTitle.ACCOUNT_CARD), account);

		log.info("TC_08_Step_14: Verify ma thanh toan ");
		verifyEquals(transReport.getDynamicTextByLabel(driver, TransactionReport_Data.ReportTitle.CARD_NUMBER), cardNumber);

		log.info("TC_08_Step_15: Verify ma thanh toan ");
		verifyEquals(transReport.getDynamicTextByLabel(driver, Creadit_Card_Payment_Data.Tittle.SO_TK_THE), accountCardNumber);

		log.info("TC_08_16: Click  nut Back");
		transReport.clickToDynamicBackIcon(driver, TransactionReport_Data.ReportTitle.TRANSACTION_DETAIL);

		log.info("TC_08_17: Click  nut Back");
		transReport.clickToDynamicBackIcon(driver, TransactionReport_Data.ReportTitle.TRANSACTION_REPORT);

		log.info("TC_08_18: Click  nut Home");
		transReport.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		service.stop();
	}

}
