package vnpay.vietcombank.internet_ADSL;

import java.io.IOException;
import java.security.GeneralSecurityException;

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
import pageObjects.InternetADSLPageObject;
import pageObjects.LogInPageObject;
import pageObjects.SettingVCBSmartOTPPageObject;
import pageObjects.TransactionReportPageObject;
import vietcombank_test_data.Internet_ADSL_Data;


public class Internet_ADSL_SmartOTP_Flow extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private InternetADSLPageObject ADSL;
	private HomePageObject homePage;
	private TransactionReportPageObject transReport;
	private SettingVCBSmartOTPPageObject smartOTP;
	String transferTime, password;
	String transactionNumber;
	long amount, fee, amountStart, feeView, amountView, amountAfter = 0;
	SourceAccountModel sourceAccount = new SourceAccountModel();
	String account = "";

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException, GeneralSecurityException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		password = pass;
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
		homePage = PageFactoryManager.getHomePageObject(driver);
		ADSL = PageFactoryManager.getInternetADSLPageObject(driver);
		smartOTP = PageFactoryManager.getSettingVCBSmartOTPPageObject(driver);
		smartOTP.setupSmartOTP("111222", getDataInCell(6));
	}

	@Parameters({ "pass" })
	@Test
	public void TC_01_ThanhToanCuocViettelXacThucMatKhau(String pass) throws InterruptedException {	
		log.info("TC_02_Step_Click cuoc ADSL");
		ADSL.clickToDynamicButtonLinkOrLinkText(driver, "Cước Internet ADSL");

		log.info("TC_02_Step_Select tai khoan nguon");
		ADSL.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		sourceAccount = ADSL.chooseSourceAccountADSL(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);
		account = sourceAccount.account;

		log.info("TC_02_Step_Get so du kha dung");
		amountStart = convertAvailableBalanceCurrentcyOrFeeToLong(ADSL.getDynamicTextByLabel(driver, "Số dư khả dụng"));

		log.info("TC_02_Step_Thong tin giao dich chon Viettel");
		ADSL.clickToTextID(driver, "com.VCB:id/content");

		ADSL.clickToDynamicButtonLinkOrLinkText(driver, Internet_ADSL_Data.Valid_Account.VIETTEL_ADSL);


		log.info("TC_02_Input ma khach hang");
		ADSL.inputCustomerCode(Internet_ADSL_Data.Valid_Account.CODEVIETTEL);

		log.info("TC_02_Kiem tra man hinh xac nhan thong tin");
		log.info("TC_02_Kiem tra tai khoan nguon");
		verifyEquals(ADSL.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), account);

		log.info("TC_02_Kiem tra dich vu");
		verifyEquals(ADSL.getDynamicTextInTransactionDetail(driver, "Dịch vụ"), "Cước Internet ADSL");

		log.info("TC_02_Kiem tra nha cung cap");

		verifyEquals(ADSL.getDynamicTextInTransactionDetail(driver, "Nhà cung cấp"), Internet_ADSL_Data.Valid_Account.VIETTEL_ADSL);

		log.info("TC_02_Kiem tra ma khach hang");
		verifyEquals(ADSL.getDynamicTextInTransactionDetail(driver, "Mã khách hàng"), InternetADSLPageObject.codeADSL.toUpperCase());

		log.info("TC_02_Get so tien thanh toan");
		amount = convertAvailableBalanceCurrentcyOrFeeToLong(ADSL.getDynamicTextInTransactionDetail(driver, "Số tiền thanh toán"));

		log.info("TC_02_Chon phuong thuc xac thuc");
		ADSL.clickToDynamicTextFollowingLinearlayout(driver, Internet_ADSL_Data.Valid_Account.SELECT_OPTION);
		ADSL.clickToDynamicButtonLinkOrLinkText(driver, "VCB - Smart OTP");

		log.info("TC_02_verify so tien phi");
		fee = convertAvailableBalanceCurrentcyOrFeeToLong(ADSL.getDynamicTextInTransactionDetail(driver, "Số tiền phí"));

		log.info("TC_02_Click Tiep tuc");
		ADSL.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_02_Step_Nhap ma xac thuc");
		ADSL.inputToDynamicSmartOTP(driver, "111222", "com.VCB:id/otp");

		log.info("TC_02_Step_Tiep tuc");
		ADSL.clickToDynamicContinue(driver, "com.VCB:id/submit");
		ADSL.clickToDynamicContinue(driver, "com.VCB:id/btContinue");

		log.info("TC_02_Verify message thanh cong");
		verifyEquals(ADSL.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), "GIAO DỊCH THÀNH CÔNG");

		log.info("TC_02_Verify so tien thanh toan");
		amountView = convertAvailableBalanceCurrentcyOrFeeToLong(ADSL.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount"));
		verifyEquals(amountView, amount);

		log.info("TC_02_get thoi gian giao dich thanh cong");
		transferTime = ADSL.getDynamicTransferTimeAndMoney(driver, "GIAO DỊCH THÀNH CÔNG", "4").split(" ")[3];

		log.info("TC_02_Kiem tra dich vu");
		verifyEquals(ADSL.getDynamicTextInTransactionDetail(driver, "Dịch vụ"), "Cước Internet ADSL");

		log.info("TC_02_Kiem tra nha cung cap");
		verifyEquals(ADSL.getDynamicTextInTransactionDetail(driver, "Nhà cung cấp"), Internet_ADSL_Data.Valid_Account.VIETTEL_ADSL);

		log.info("TC_02_Kiem tra ma khach hang");
		verifyEquals(ADSL.getDynamicTextInTransactionDetail(driver, "Mã khách hàng"), InternetADSLPageObject.codeADSL.toUpperCase().toUpperCase());

		log.info("TC_02_Step_:Lay ma giao dich");
		transactionNumber = ADSL.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC_02_Step_: Chon thuc hien giao dich");
		ADSL.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_02_Step_: Chon tai khoan chuyen");
		ADSL.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_02_Step_: Chon tai khoan chuyen");
		ADSL.clickToDynamicButtonLinkOrLinkText(driver, account);

		log.info("TC_02_Step_:Check so du kha dung sau khi chuyen tien");
		amountAfter = convertAvailableBalanceCurrentcyOrFeeToLong(ADSL.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));

		log.info("TC_02_Step_:Check so du kha dung sau khi chuyen tien");
		verifyEquals(amountStart - amount - fee, amountAfter);
	}
}
