package vnpay.vietcombank.internet_ADSL;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebDriver;
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
import pageObjects.InternetADSLPageObject;
import pageObjects.LogInPageObject;
import pageObjects.WebBackendSetupPageObject;
import vietcombank_test_data.Internet_ADSL_Data;

public class Limit_Internet_ADSL extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	WebDriver driverWeb;
	private WebBackendSetupPageObject webBackend;

	private InternetADSLPageObject adsl;
	String transferTime, password;
	String transactionNumber;
	long amount, fee, amountStart, feeView, amountView, amountAfter = 0;
	SourceAccountModel sourceAccount = new SourceAccountModel();
	String account = "";
	List<String> codeViettel = new ArrayList<String>();
	ServiceLimitInfo inputInfo = new ServiceLimitInfo("1000", "10000", "5000000", "5500000");
	String inputAmountMin = inputInfo.minTran;
	String inputAmountMax = inputInfo.maxTran;
	String inputTotalLimit = inputInfo.totalLimit;
	String amountType = "900000";

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp", "username", "passWeb" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt, String username, String passWeb) throws IOException, InterruptedException, GeneralSecurityException {
		webBackend = WebPageFactoryManager.getWebBackendSetupPageObject(driverWeb);
		log.info("Before class: Mo backend ");
		driverWeb = openMultiBrowser(Constants.BE_BROWSER_CHROME, Constants.BE_BROWSER_VERSION, Constants.BE_URL);
		webBackend.Login_Web_Backend(driverWeb, username, passWeb);
		//webBackend.addMethod(driverWeb, "Thanh toán hóa đơn trả sau", inputInfo, "TESTBUG");
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
		password = pass;
		adsl = PageFactoryManager.getInternetADSLPageObject(driver);
		codeViettel = Arrays.asList(getDataInCell(17).split(";"));
	}

	//Lỗi app vẫn cho phép chuyển tiềnthành công
	@Test
	public void TC_01_ThanhToanCuocViettelNhoHonHanMucToiThieu() throws InterruptedException {
		log.info("TC_01_Step_Click cuoc ADSL");
		adsl.scrollDownToText(driver, Internet_ADSL_Data.Valid_Account.SAVE);
		adsl.clickToDynamicButtonLinkOrLinkText(driver, Internet_ADSL_Data.Valid_Account.FEE_ADSL_INTERNET);

		log.info("TC_01_Step_Select tai khoan nguon");
		adsl.clickToDynamicDropDown(driver, Internet_ADSL_Data.Valid_Account.SOURCE_ACCOUNT);
		sourceAccount = adsl.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);
		account = sourceAccount.account;

		log.info("TC_01_Step_Get so du kha dung");
		amountStart = convertAvailableBalanceCurrentcyOrFeeToLong(adsl.getDynamicTextByLabel(driver, Internet_ADSL_Data.Valid_Account.AMOUNT));

		log.info("TC_01_Step_Thong tin giao dich chon Viettel");

		adsl.clickToDynamicImageViewByID(driver, "com.VCB:id/icon");
		adsl.clickToDynamicButtonLinkOrLinkText(driver, Internet_ADSL_Data.Valid_Account.VIETTEL);

		log.info("TC_01_Input ma khach hang");
		adsl.inputCustomerCode(codeViettel);

		log.info("TC_01_Kiem tra man hinh xac nhan thong tin");
		log.info("TC_01_Kiem tra tai khoan nguon");
		verifyEquals(adsl.getDynamicTextInTransactionDetail(driver, Internet_ADSL_Data.Valid_Account.SOURCE_ACCOUNT), account);

		log.info("TC_01_Kiem tra dich vu");
		verifyEquals(adsl.getDynamicTextInTransactionDetail(driver, Internet_ADSL_Data.Valid_Account.SERVICE), Internet_ADSL_Data.Valid_Account.FEE_ADSL);

		log.info("TC_01_Kiem tra nha cung cap");
		verifyEquals(adsl.getDynamicTextInTransactionDetail(driver, Internet_ADSL_Data.Valid_Account.PROVICE), Internet_ADSL_Data.Valid_Account.VIETTEL);

		log.info("TC_01_Kiem tra ma khach hang");
		verifyEquals(adsl.getDynamicTextInTransactionDetail(driver, Internet_ADSL_Data.Valid_Account.CUSTOMER_CODE), InternetADSLPageObject.codeADSL.toUpperCase());

		log.info("TC_01_Get so tien thanh toan");
		amount = convertAvailableBalanceCurrentcyOrFeeToLong(adsl.getDynamicTextInTransactionDetail(driver, Internet_ADSL_Data.Valid_Account.PAYMENT_AMOUNT));
		
		log.info("TC_01_Quay ve man hinh khoi tao");
		adsl.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
		
		ServiceLimitInfo inputInfoMin = new ServiceLimitInfo("1000", (amount + 20000) + "", (amount + 1000000) + "", "10000000");
		webBackend.getInfoServiceLimit(driverWeb, "Thanh toán hóa đơn trả sau", inputInfoMin, "TESTBUG");
		webBackend.clearCacheBE(driverWeb);
		
		log.info("TC_01_Click tiep tuc");
		adsl.clickToDynamicContinue(driver, "com.VCB:id/btContinue");
		
		log.info("TC_01_Chon phuong thuc xac thuc");
		adsl.clickToDynamicTextFollowingLinearlayout(driver, Internet_ADSL_Data.Valid_Account.SELECT_OPTION);
		adsl.clickToDynamicButtonLinkOrLinkText(driver, Internet_ADSL_Data.Valid_Account.PASSWORD);

		log.info("TC_01_Click Tiep tuc");
		adsl.clickToDynamicButton(driver, Internet_ADSL_Data.Valid_Account.CONTINUE);

		log.info("TC_01_Step_Nhap ma xac thuc");
		adsl.inputToDynamicPopupPasswordInput(driver, password, Internet_ADSL_Data.Valid_Account.CONTINUE);

		log.info("TC_01_Step_Tiep tuc");
		adsl.clickToDynamicButton(driver, Internet_ADSL_Data.Valid_Account.CONTINUE);
		//Lỗi app vẫn cho phép chuyển tiềnthành công
		log.info("TC_01_Step_verify message khi so tien chuyen nho hon han muc toi thieu ");
		verifyEquals(adsl.getDynamicTextView(driver, "com.VCB:id/tvContent"), "Thanh toán hoá đơn không thành công. Số tiền giao dịch nhỏ hơn hạn mức " + (amount + 20000) + " VND/1 lần, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp");

		adsl.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
		adsl.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
		webBackend.resetAssignServicesLimit_All(driverWeb, "Thanh toán hóa đơn trả sau",  "TESTBUG");
	}

	@Test
	public void TC_02_ThanhToanCuocViettelCaoHonHanMucToiDa() throws InterruptedException {
		log.info("TC_01_Step_Select tai khoan nguon");
		adsl.clickToDynamicDropDown(driver, Internet_ADSL_Data.Valid_Account.SOURCE_ACCOUNT);
		sourceAccount = adsl.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);
		account = sourceAccount.account;

		log.info("TC_01_Step_Get so du kha dung");
		amountStart = convertAvailableBalanceCurrentcyOrFeeToLong(adsl.getDynamicTextByLabel(driver, Internet_ADSL_Data.Valid_Account.AMOUNT));

		log.info("TC_01_Step_Thong tin giao dich chon Viettel");

		adsl.clickToDynamicImageViewByID(driver, "com.VCB:id/icon");
		adsl.clickToDynamicButtonLinkOrLinkText(driver, Internet_ADSL_Data.Valid_Account.VIETTEL);

		log.info("TC_01_Input ma khach hang");
		adsl.inputCustomerCode(codeViettel);

		log.info("TC_01_Kiem tra man hinh xac nhan thong tin");
		log.info("TC_01_Kiem tra tai khoan nguon");
		verifyEquals(adsl.getDynamicTextInTransactionDetail(driver, Internet_ADSL_Data.Valid_Account.SOURCE_ACCOUNT), account);

		log.info("TC_01_Kiem tra dich vu");
		verifyEquals(adsl.getDynamicTextInTransactionDetail(driver, Internet_ADSL_Data.Valid_Account.SERVICE), Internet_ADSL_Data.Valid_Account.FEE_ADSL);

		log.info("TC_01_Kiem tra nha cung cap");
		verifyEquals(adsl.getDynamicTextInTransactionDetail(driver, Internet_ADSL_Data.Valid_Account.PROVICE), Internet_ADSL_Data.Valid_Account.VIETTEL);

		log.info("TC_01_Kiem tra ma khach hang");
		verifyEquals(adsl.getDynamicTextInTransactionDetail(driver, Internet_ADSL_Data.Valid_Account.CUSTOMER_CODE), InternetADSLPageObject.codeADSL.toUpperCase());

		log.info("TC_01_Get so tien thanh toan");
		amount = convertAvailableBalanceCurrentcyOrFeeToLong(adsl.getDynamicTextInTransactionDetail(driver, Internet_ADSL_Data.Valid_Account.PAYMENT_AMOUNT));

		log.info("TC_01_Quay ve man hinh khoi tao");
		adsl.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
		
		ServiceLimitInfo inputInfoMax = new ServiceLimitInfo("1000", (amount - 20000) + "", (amount - 10000) + "", "10000000");
		webBackend.getInfoServiceLimit(driverWeb, "Thanh toán hóa đơn trả sau", inputInfoMax, "TESTBUG");
		webBackend.clearCacheBE(driverWeb);
		
		log.info("TC_01_Click tiep tuc");
		adsl.clickToDynamicContinue(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Chon phuong thuc xac thuc");
		adsl.clickToDynamicTextFollowingLinearlayout(driver, Internet_ADSL_Data.Valid_Account.SELECT_OPTION);
		adsl.clickToDynamicButtonLinkOrLinkText(driver, Internet_ADSL_Data.Valid_Account.PASSWORD);

		log.info("TC_01_Click Tiep tuc");
		adsl.clickToDynamicButton(driver, Internet_ADSL_Data.Valid_Account.CONTINUE);

		log.info("TC_01_Step_Nhap ma xac thuc");
		adsl.inputToDynamicPopupPasswordInput(driver, password, Internet_ADSL_Data.Valid_Account.CONTINUE);

		log.info("TC_01_Step_Tiep tuc");
		adsl.clickToDynamicButton(driver, Internet_ADSL_Data.Valid_Account.CONTINUE);

		log.info("TC_01_Step_verify message khi so tien chuyen nho hon han muc toi da  ");
		verifyEquals(adsl.getDynamicTextView(driver, "com.VCB:id/tvContent"), "Thanh toán hoá đơn không thành công. Số tiền giao dịch lớn hơn hạn mức " + (amount - 10000) + " VND/1 lần, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp");

		adsl.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
		adsl.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

		webBackend.resetAssignServicesLimit_All(driverWeb, "Thanh toán hóa đơn trả sau", "TESTBUG");
	}

	@Test
	public void TC_03_ThanhToanCuocViettelCaoHonHanMucToiDaNhom() throws InterruptedException {
		log.info("TC_01_Step_Select tai khoan nguon");
		adsl.clickToDynamicDropDown(driver, Internet_ADSL_Data.Valid_Account.SOURCE_ACCOUNT);
		sourceAccount = adsl.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);
		account = sourceAccount.account;

		log.info("TC_01_Step_Get so du kha dung");
		amountStart = convertAvailableBalanceCurrentcyOrFeeToLong(adsl.getDynamicTextByLabel(driver, Internet_ADSL_Data.Valid_Account.AMOUNT));

		log.info("TC_01_Step_Thong tin giao dich chon Viettel");

		adsl.clickToDynamicImageViewByID(driver, "com.VCB:id/icon");
		adsl.clickToDynamicButtonLinkOrLinkText(driver, Internet_ADSL_Data.Valid_Account.VIETTEL);

		log.info("TC_01_Input ma khach hang");
		adsl.inputCustomerCode(codeViettel);

		log.info("TC_01_Kiem tra man hinh xac nhan thong tin");
		log.info("TC_01_Kiem tra tai khoan nguon");
		verifyEquals(adsl.getDynamicTextInTransactionDetail(driver, Internet_ADSL_Data.Valid_Account.SOURCE_ACCOUNT), account);

		log.info("TC_01_Kiem tra dich vu");
		verifyEquals(adsl.getDynamicTextInTransactionDetail(driver, Internet_ADSL_Data.Valid_Account.SERVICE), Internet_ADSL_Data.Valid_Account.FEE_ADSL);

		log.info("TC_01_Kiem tra nha cung cap");
		verifyEquals(adsl.getDynamicTextInTransactionDetail(driver, Internet_ADSL_Data.Valid_Account.PROVICE), Internet_ADSL_Data.Valid_Account.VIETTEL);

		log.info("TC_01_Kiem tra ma khach hang");
		verifyEquals(adsl.getDynamicTextInTransactionDetail(driver, Internet_ADSL_Data.Valid_Account.CUSTOMER_CODE), InternetADSLPageObject.codeADSL.toUpperCase());

		log.info("TC_01_Get so tien thanh toan");
		amount = convertAvailableBalanceCurrentcyOrFeeToLong(adsl.getDynamicTextInTransactionDetail(driver, Internet_ADSL_Data.Valid_Account.PAYMENT_AMOUNT));

		log.info("TC_01_Quay ve man hinh khoi tao");
		adsl.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
		
		webBackend.Setup_Assign_Services_Type_Limit(driver, "TESTBUG", "Thanh toán hoá đơn", (amount - 10000) + "");
		webBackend.clearCacheBE(driverWeb);
		
		log.info("TC_01_Click tiep tuc");
		adsl.clickToDynamicContinue(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Chon phuong thuc xac thuc");
		adsl.clickToDynamicTextFollowingLinearlayout(driver, Internet_ADSL_Data.Valid_Account.SELECT_OPTION);
		adsl.clickToDynamicButtonLinkOrLinkText(driver, Internet_ADSL_Data.Valid_Account.PASSWORD);

		log.info("TC_01_Click Tiep tuc");
		adsl.clickToDynamicButton(driver, Internet_ADSL_Data.Valid_Account.CONTINUE);

		log.info("TC_01_Step_Nhap ma xac thuc");
		adsl.inputToDynamicPopupPasswordInput(driver, password, Internet_ADSL_Data.Valid_Account.CONTINUE);

		log.info("TC_01_Step_Tiep tuc");
		adsl.clickToDynamicButton(driver, Internet_ADSL_Data.Valid_Account.CONTINUE);

		log.info("TC_01_Step_verify message khi so tien chuyen nho hon han muc toi da  ");
		verifyEquals(adsl.getDynamicTextView(driver, "com.VCB:id/tvContent"), "Thanh toán hoá đơn không thành công. Số tiền giao dịch lớn hơn hạn mức " + (amount - 10000) + " VND/1 ngày của nhóm dịch vụ, chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp");

		adsl.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
		adsl.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

		webBackend.Reset_Setup_Assign_Services_Type_Limit(driver, "TESTBUG", "Thanh toán hoá đơn");
	}

	@Test
	public void TC_04_ThanhToanCuocViettelCaoHonHanMucToiDaGoi() throws InterruptedException {
		log.info("TC_01_Step_Select tai khoan nguon");
		adsl.clickToDynamicDropDown(driver, Internet_ADSL_Data.Valid_Account.SOURCE_ACCOUNT);
		sourceAccount = adsl.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);
		account = sourceAccount.account;

		log.info("TC_01_Step_Get so du kha dung");
		amountStart = convertAvailableBalanceCurrentcyOrFeeToLong(adsl.getDynamicTextByLabel(driver, Internet_ADSL_Data.Valid_Account.AMOUNT));

		log.info("TC_01_Step_Thong tin giao dich chon Viettel");

		adsl.clickToDynamicImageViewByID(driver, "com.VCB:id/icon");
		adsl.clickToDynamicButtonLinkOrLinkText(driver, Internet_ADSL_Data.Valid_Account.VIETTEL);

		log.info("TC_01_Input ma khach hang");
		adsl.inputCustomerCode(codeViettel);

		log.info("TC_01_Kiem tra man hinh xac nhan thong tin");
		log.info("TC_01_Kiem tra tai khoan nguon");
		verifyEquals(adsl.getDynamicTextInTransactionDetail(driver, Internet_ADSL_Data.Valid_Account.SOURCE_ACCOUNT), account);

		log.info("TC_01_Kiem tra dich vu");
		verifyEquals(adsl.getDynamicTextInTransactionDetail(driver, Internet_ADSL_Data.Valid_Account.SERVICE), Internet_ADSL_Data.Valid_Account.FEE_ADSL);

		log.info("TC_01_Kiem tra nha cung cap");
		verifyEquals(adsl.getDynamicTextInTransactionDetail(driver, Internet_ADSL_Data.Valid_Account.PROVICE), Internet_ADSL_Data.Valid_Account.VIETTEL);

		log.info("TC_01_Kiem tra ma khach hang");
		verifyEquals(adsl.getDynamicTextInTransactionDetail(driver, Internet_ADSL_Data.Valid_Account.CUSTOMER_CODE), InternetADSLPageObject.codeADSL.toUpperCase());

		log.info("TC_01_Get so tien thanh toan");
		amount = convertAvailableBalanceCurrentcyOrFeeToLong(adsl.getDynamicTextInTransactionDetail(driver, Internet_ADSL_Data.Valid_Account.PAYMENT_AMOUNT));

		log.info("TC_01_Quay ve man hinh khoi tao");
		adsl.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
		
		webBackend.Setup_Assign_Services_Type_Limit(driverWeb, "TESTBUG", "Method Otp", (amount - 10000) + "");
		
		log.info("TC_01_Click tiep tuc");
		adsl.clickToDynamicContinue(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Chon phuong thuc xac thuc");
		adsl.clickToDynamicTextFollowingLinearlayout(driver, Internet_ADSL_Data.Valid_Account.SELECT_OPTION);
		adsl.clickToDynamicButtonLinkOrLinkText(driver, Internet_ADSL_Data.Valid_Account.PASSWORD);

		log.info("TC_01_Click Tiep tuc");
		adsl.clickToDynamicButton(driver, Internet_ADSL_Data.Valid_Account.CONTINUE);

		log.info("TC_01_Step_Nhap ma xac thuc");
		adsl.inputToDynamicPopupPasswordInput(driver, password, Internet_ADSL_Data.Valid_Account.CONTINUE);

		log.info("TC_01_Step_Tiep tuc");
		adsl.clickToDynamicButton(driver, Internet_ADSL_Data.Valid_Account.CONTINUE);

		log.info("TC_01_Step_verify message khi so tien chuyen nho hon han muc toi da  ");
		verifyEquals(adsl.getDynamicTextView(driver, "com.VCB:id/tvContent"), "Thanh toán hoá đơn không thành công. Số tiền giao dịch lớn hơn hạn mức " + (amount - 10000) + " VND/1 ngày của gói dịch vụ, chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp");

		adsl.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
		adsl.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

		webBackend.Reset_Package_Total_Limit(driverWeb, "TESTBUG", "Method Otp");
	}

}
