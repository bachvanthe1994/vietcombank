package vnpay.vietcombank.transfer_money_out_side_vcb;

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
import model.TransferOutSideVCB_Info;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.TransferMoneyOutSideVCBPageObject;
import pageObjects.WebBackendSetupPageObject;
import vietcombank_test_data.TransferMoneyQuick_Data;
import vietcombank_test_data.TransactionReport_Data.ReportTitle;
import vietcombank_test_data.TransferMoneyOutVCB_Data.MESSEGE_ERROR;
import vietcombank_test_data.TransferMoneyOutVCB_Data.TitleOutVCB;

public class TransferOutSideVCB_Limit_02 extends Base {
	AppiumDriver<MobileElement> driver;
	private WebDriver driverWeb;
	private WebBackendSetupPageObject setupBE;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferMoneyOutSideVCBPageObject transferMoneyOutSide;

	long transferFee = 0;
	double transferFeeCurrentcy = 0;
	String lowerMin,higherMax,higherTotal,destinationAccount,password,otpNo;
	ServiceLimitInfo inputInfo = new ServiceLimitInfo("1000", "10000", "1000000", "1500000");
	ServiceLimitInfo infoBE = new ServiceLimitInfo("100000", "10000", "1000000000", "1500000000");

	SourceAccountModel sourceAccount = new SourceAccountModel();
	TransferOutSideVCB_Info info = new TransferOutSideVCB_Info("", "", TitleOutVCB.NAME_RECIEVED, TitleOutVCB.BANK_RECIEVED, "", TitleOutVCB.TRANSACTION_FEE_SENT, TitleOutVCB.TRANSACTION_CONTENT, TitleOutVCB.PASSWORD_TITLE);
	
	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp","username","passWeb" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt,String username,String passWeb) throws IOException, InterruptedException, GeneralSecurityException {
		startServer();
		
		log.info("Before class: Mo backend ");
		driverWeb = openMultiBrowser(Constants.BE_BROWSER_CHROME, Constants.BE_BROWSER_VERSION, Constants.BE_URL);
		
		setupBE = WebPageFactoryManager.getWebBackendSetupPageObject(driverWeb);
		setupBE.Login_Web_Backend(driverWeb,username, passWeb);
		
		setupBE.addMethodOtpLimit(driverWeb, TitleOutVCB.BE_TRANSFER_OUTSIDE_TEXT);
		setupBE.addMethodServicesLimit(driverWeb, TitleOutVCB.BE_TRANSFER_OUTSIDE_TEXT, infoBE, Constants.BE_CODE_PACKAGE);
		setupBE.setupAssignServicesLimit_All(driverWeb,TitleOutVCB.BE_TRANSFER_OUTSIDE_TEXT,inputInfo,Constants.BE_CODE_PACKAGE);
		setupBE.clearCacheBE(driverWeb);
		
		destinationAccount = getDataInCell(34);
		log.info("Before class: Mo app ");
		if (deviceType.contains("android")) {
			driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		} else if (deviceType.contains("ios")) {
			driver = openIOSApp(deviceName, udid, url);
		}
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
		homePage = PageFactoryManager.getHomePageObject(driver);
		transferMoneyOutSide = PageFactoryManager.getTransferMoneyOutSideVCBPageObject(driver);
		password = pass;
		otpNo = opt;

		lowerMin = (Integer.parseInt(inputInfo.minTran)-1)+"";
		higherMax = (Integer.parseInt(inputInfo.maxTran)+1)+"";
		higherTotal = (Integer.parseInt(inputInfo.totalLimit)+1)+"";
		
		transferMoneyOutSide.scrollDownToText(driver, TitleOutVCB.TRANSFER_MONEY_STATUS_TEXT);
	}

	@Test
	public void TC_01_ChuyenTienToiTaiKhoanKhacNhoHonDinhMucToiThieu() {

		log.info("TC_01_1_Click Chuyen tien toi ngan hang khac");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.TITLE_TRANSFER_OUTSIDE);
		clickPopupAfter15h30();

		log.info("TC_01_2_Chon tai khoan nguon");
		transferMoneyOutSide.clickToDynamicDropDown(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		sourceAccount = transferMoneyOutSide.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);
		
		transferMoneyOutSide.scrollUpToText(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);

		log.info("TC_01_3_Nhap tai khoan thu huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, destinationAccount, TitleOutVCB.ACCOUT_TO);

		log.info("TC_01_4_Nhap ten nguoi huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info.name, TitleOutVCB.BENEFICIARY_NAME);

		log.info("TC_01_5_Chon ngan hang huong");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.DESTINATION_BANK);
		transferMoneyOutSide.inputToDynamicInputBox(driver, info.destinationBank, ReportTitle.SEARCH_BUTTON);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info.destinationBank);

		log.info("TC_01_6_Nhap so tien");
		transferMoneyOutSide.inputToDynamicInputBox(driver, lowerMin, TitleOutVCB.MONEY);

		log.info("TC_01_7_Chọn phí giao dịch");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.TRANSACTION_FEE_SENT);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.TRANSFER_PERSON);

		log.info("TC_01_7_Nhap noi dung chuyen tien");
		transferMoneyOutSide.inputToDynamicInputBoxByHeader(driver, info.note, TitleOutVCB.TRANSACTION_INFOMATION, "3");

		log.info("TC_01_8_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEXT);

		log.info("TC_01_Step_11: Verify hien thi man hinh thong bao loi");
		verifyEquals(transferMoneyOutSide.getTextDynamicFollowImage(driver, "com.VCB:id/ivTitle"), MESSEGE_ERROR.LOWER_MIN_A_TRANSACTION+addCommasToLong(inputInfo.minTran)+MESSEGE_ERROR.DETAIL_A_TRANSACTION_MESSAGE);
		
		log.info("TC_01_8_Click Dong");
		transferMoneyOutSide.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

	}

	@Test
	public void TC_02_ChuyenTienToiTaiKhoanKhacCaoHonMucToiDa() {

		log.info("TC_02_1_Nhap so tien");
		transferMoneyOutSide.inputToDynamicInputBoxByHeader(driver, higherMax, TitleOutVCB.TRANSACTION_INFOMATION, "1");

		log.info("TC_02_2_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_02_Step_3: Verify hien thi man hinh thong bao loi");
		verifyEquals(transferMoneyOutSide.getTextDynamicFollowImage(driver, "com.VCB:id/ivTitle"), MESSEGE_ERROR.HIGHER_MAX_A_TRANSACTION+addCommasToLong(inputInfo.maxTran)+MESSEGE_ERROR.DETAIL_A_TRANSACTION_MESSAGE);

		log.info("TC_02_8_Click Dong");
		transferMoneyOutSide.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
		homePage.clickToDynamicBackIcon(driver, TitleOutVCB.TITLE_TRANSFER_OUTSIDE);
	}

	@Test
	public void TC_03_ChuyenTienToiTaiKhoanKhacCaoHonMucToiDaTrongNgay() {

		log.info("TC_03_01_Click Chuyen tien toi ngan hang khac");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.TITLE_TRANSFER_OUTSIDE);
		clickPopupAfter15h30();

		log.info("TC_03_02_Chon tai khoan nguon");
		transferMoneyOutSide.clickToDynamicDropDown(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		sourceAccount = transferMoneyOutSide.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, "VND");
		transferMoneyOutSide.scrollUpToText(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		log.info("TC_03_03_Nhap tai khoan thu huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, destinationAccount, TitleOutVCB.ACCOUT_TO);

		log.info("TC_03_04_Nhap ten nguoi huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info.name, TitleOutVCB.BENEFICIARY_NAME);

		log.info("TC_03_05_Chon ngan hang huong");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.DESTINATION_BANK);
		transferMoneyOutSide.inputToDynamicInputBox(driver, info.destinationBank, ReportTitle.SEARCH_BUTTON);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info.destinationBank);

		log.info("TC_03_06_Nhap so tien");
		transferMoneyOutSide.inputToDynamicInputBox(driver, inputInfo.maxTran, TitleOutVCB.MONEY);

		log.info("TC_03_07_Nhap noi dung chuyen tien");
		transferMoneyOutSide.inputToDynamicInputBoxByHeader(driver, info.note, TitleOutVCB.TRANSACTION_INFOMATION, "3");

		log.info("TC_03_08_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEXT);

		log.info("TC_03_09_Chon phuong thuc xac thuc");
		transferMoneyOutSide.scrollDownToText(driver, TitleOutVCB.CHOOSE_METHOD);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.PASSWORD_TITLE);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.TITLE_OTP);

		log.info("TC_03_10_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEXT);
		transferMoneyOutSide.inputToDynamicOtp(driver, otpNo, TitleOutVCB.NEXT);
		transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEXT);

		log.info("TC_03_11_Kiem tra Chuyen khoan thanh cong");
		verifyEquals(transferMoneyOutSide.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY);

		log.info("TC_03_12_Click Thuc hien giao dich moi");
		transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEW_TRANSFER);
		clickPopupAfter15h30();
		
		log.info("TC_03_13_Chon tai khoan nguon");
		transferMoneyOutSide.clickToDynamicDropDown(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		sourceAccount = transferMoneyOutSide.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, "VND");
		transferMoneyOutSide.scrollUpToText(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		
		log.info("TC_03_14_Nhap tai khoan thu huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, destinationAccount, TitleOutVCB.ACCOUT_TO);

		log.info("TC_03_15_Nhap ten nguoi huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info.name, TitleOutVCB.BENEFICIARY_NAME);

		log.info("TC_03_16_Chon ngan hang huong");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.DESTINATION_BANK);
		transferMoneyOutSide.inputToDynamicInputBox(driver, info.destinationBank, ReportTitle.SEARCH_BUTTON);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info.destinationBank);

		log.info("TC_03_17_Nhap so tien");
		transferMoneyOutSide.inputToDynamicInputBox(driver, inputInfo.maxTran, TitleOutVCB.MONEY);

		log.info("TC_03_18_Nhap noi dung chuyen tien");
		transferMoneyOutSide.inputToDynamicInputBoxByHeader(driver, info.note, TitleOutVCB.TRANSACTION_INFOMATION, "3");

		log.info("TC_03_19_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEXT);

		log.info("TC_03_20_Chon phuong thuc xac thuc");
		transferMoneyOutSide.scrollDownToText(driver, TitleOutVCB.CHOOSE_METHOD);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.PASSWORD_TITLE);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info.authenticationMethod);

		log.info("TC_03_21_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEXT);
		transferMoneyOutSide.inputToDynamicPopupPasswordInput(driver, password, TitleOutVCB.NEXT);
		transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEXT);

		log.info("TC_03_22_Kiem tra Chuyen khoan thanh cong");
		verifyEquals(transferMoneyOutSide.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY);

		log.info("TC_03_23_Click Thuc hien giao dich moi");
		transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEW_TRANSFER);
		clickPopupAfter15h30();
		
		log.info("TC_03_24_Chon tai khoan nguon");
		transferMoneyOutSide.clickToDynamicDropDown(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);
		transferMoneyOutSide.scrollUpToText(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		
		log.info("TC_03_25_Nhap tai khoan thu huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, destinationAccount, TitleOutVCB.ACCOUT_TO);

		log.info("TC_03_26_Nhap ten nguoi huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info.name, TitleOutVCB.BENEFICIARY_NAME);

		log.info("TC_03_27_Chon ngan hang huong");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.DESTINATION_BANK);
		transferMoneyOutSide.inputToDynamicInputBox(driver, info.destinationBank, ReportTitle.SEARCH_BUTTON);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info.destinationBank);

		log.info("TC_03_28_Nhap so tien");
		transferMoneyOutSide.inputToDynamicInputBox(driver, inputInfo.maxTran, TitleOutVCB.MONEY);

		log.info("TC_03_29_Nhap noi dung chuyen tien");
		transferMoneyOutSide.inputToDynamicInputBoxByHeader(driver, info.note, TitleOutVCB.TRANSACTION_INFOMATION, "3");

		log.info("TC_03_30_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEXT);

		log.info("TC_03_31_Verify hien thi man hinh thong bao loi");
		verifyEquals(transferMoneyOutSide.getTextDynamicFollowImage(driver, "com.VCB:id/ivTitle"), MESSEGE_ERROR.HIGHER_MAX_A_TRANSACTION+addCommasToLong(inputInfo.maxTran)+MESSEGE_ERROR.DETAIL_A_DAY_MESSAGE);

		log.info("TC_03_32_Click Dong");
		transferMoneyOutSide.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
		homePage.clickToDynamicBackIcon(driver, TitleOutVCB.TITLE_TRANSFER_OUTSIDE);
	}
	
	@AfterClass(alwaysRun = true)
	public void afterClass() {
		setupBE.resetAssignServicesLimit_All(driverWeb,TitleOutVCB.BE_TRANSFER_OUTSIDE_TEXT,Constants.BE_CODE_PACKAGE);
		driverWeb.quit();
		service.stop();
	}

	public void clickPopupAfter15h30() {
		if (transferMoneyOutSide.isDynamicButtonDisplayed(driver, "Hủy")) {
			transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEXT);
		}

	}
}
