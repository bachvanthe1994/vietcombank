package vnpay.vietcombank.telecommunication_fee_VNPT;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
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
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.TelecommunicationFeeVNPTPageObject;
import pageObjects.TransactionReportPageObject;
import vietcombank_test_data.TelecommunicationFee_VNPT_Data;
import vietcombank_test_data.TransactionReport_Data;

public class Telecommunication_Fee_VNPT extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject home;
	private TelecommunicationFeeVNPTPageObject telecomFeeVNPT;
	private TransactionReportPageObject transactionReport;
	SourceAccountModel sourceAccount = new SourceAccountModel();
	private String sourceAccountMoney,vnpt_provider, customerID, moneyBill, transactionDate, transactionID,account,fee;
	private long transferFee;
	List<String> vnpt_code = new ArrayList<String>();
	
	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException, GeneralSecurityException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
		vnpt_code = Arrays.asList(getDataInCell(32).split(";"));
		vnpt_provider = getDataInCell(33);
	}

	@Parameters("otp")
	@Test
	public void TC_01_ThanhToanCuocVienThongVNPT_OTP(String otp) {
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_01_Step_01: Hoa don VNPT ");
		home.clickToDynamicButtonLinkOrLinkText(driver, TelecommunicationFee_VNPT_Data.data.VNPT_PAYMENT);
		telecomFeeVNPT = PageFactoryManager.getTelecommunicationFeeVNPT(driver);

		log.info("TC_01_Step_02: Chon tai khoan nguon");
		telecomFeeVNPT.clickToTextID(driver, "com.VCB:id/number_account");
		sourceAccount = telecomFeeVNPT.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);
		account = sourceAccount.account;
		sourceAccountMoney = telecomFeeVNPT.getDynamicTextByLabel(driver, TelecommunicationFee_VNPT_Data.data.AMOUNT);

		log.info("TC_01_Step_03: Chon nha cung cap");
		telecomFeeVNPT.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/wrap_tv");
		telecomFeeVNPT.clickToDynamicButtonLinkOrLinkText(driver, vnpt_provider);

		log.info("TC_01_Step_04: Nhap ma khach hang va an tiep tuc");
		customerID = telecomFeeVNPT.inputCustomerId(vnpt_code);

		log.info("TC_01_Step_10: Hien thi So tien thanh toan");
		moneyBill = telecomFeeVNPT.getDynamicTextByLabel(driver, TelecommunicationFee_VNPT_Data.data.PAYMENT_AMOUNT);

		log.info("TC_01_Step_11: Chon phuong thuc xac thuc");
		telecomFeeVNPT.scrollDownToText(driver, TelecommunicationFee_VNPT_Data.data.METHOD_VERIFY);
		telecomFeeVNPT.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/llptxt");
		telecomFeeVNPT.clickToDynamicButtonLinkOrLinkText(driver, TelecommunicationFee_VNPT_Data.data.SMS);

		log.info("TC_01_Step_12: Kiem tra so tien phi");
		fee = telecomFeeVNPT.getDynamicTextInTransactionDetail(driver, TelecommunicationFee_VNPT_Data.data.TRANSACTION_FEE);
		transferFee = convertAvailableBalanceCurrentcyOrFeeToLong(fee);

		log.info("TC_01_Step_13: An nut Tiep Tuc");
		verifyEquals(telecomFeeVNPT.getDynamicTextButtonById(driver, "com.VCB:id/btContinue"), TelecommunicationFee_VNPT_Data.data.CONTINUE);
		telecomFeeVNPT.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_14: Nhap du ki tu vao o nhap OTP");
		telecomFeeVNPT.inputToDynamicOtp(driver, otp, TelecommunicationFee_VNPT_Data.data.CONTINUE);

		log.info("TC_01_Step_15: An tiep button 'Tiep tuc'");
		telecomFeeVNPT.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");
		
		log.info("TC_01_Step_23: An tiep button 'Thuc hien giao dich moi'");
		telecomFeeVNPT.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_24: Hien thi man hinh Hoa don tien dien");
		verifyEquals(telecomFeeVNPT.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), TelecommunicationFee_VNPT_Data.data.VNPT_BILL_TEXT);

		log.info("TC_07_Step_25: Chon tai khoan nguon");
		telecomFeeVNPT.clickToTextID(driver, "com.VCB:id/number_account");
		telecomFeeVNPT.clickToDynamicButtonLinkOrLinkText(driver, account);

		log.info("TC_01_Step_26: Xac nhan so du TK nguon da bi tru thanh cong");
		sourceAccountMoney = (convertAvailableBalanceCurrentcyOrFeeToLong(sourceAccountMoney) - convertAvailableBalanceCurrentcyOrFeeToLong(moneyBill) - transferFee) + "";
		verifyEquals(telecomFeeVNPT.getDynamicTextByLabel(driver, TelecommunicationFee_VNPT_Data.data.AMOUNT), addCommasToLong(sourceAccountMoney) + " VND");

		log.info("TC_07_Step_27: Chon nha cung cap");
		telecomFeeVNPT.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/wrap_tv");
		telecomFeeVNPT.clickToDynamicButtonLinkOrLinkText(driver, vnpt_provider);

		log.info("TC_01_Step_28: Nhap ma khach hang");
		telecomFeeVNPT.inputToDynamicEditviewByLinearlayoutId(driver, customerID, "com.VCB:id/llCode");

		log.info("TC_01_Step_29: An nut Tiep Tuc");
		verifyEquals(telecomFeeVNPT.getDynamicTextButtonById(driver, "com.VCB:id/btn_submit"), TelecommunicationFee_VNPT_Data.data.CONTINUE);
		telecomFeeVNPT.clickToDynamicAcceptButton(driver, "com.VCB:id/btn_submit");

		log.info("TC_01_Step_30: Hien thi thong bao Ma khach hang khong con no truoc");
		verifyEquals(telecomFeeVNPT.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), TelecommunicationFee_VNPT_Data.data.BILL_MESSAGE);

		log.info("TC_01_Step_31: Click nut Dong tat pop-up");
		telecomFeeVNPT.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

		log.info("TC_01_Step_32: Click nut Back ve man hinh chinh");
		telecomFeeVNPT.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_01_Step_33: Click nut Back ve man hinh chinh");
		telecomFeeVNPT.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		service.stop();
	}

}
