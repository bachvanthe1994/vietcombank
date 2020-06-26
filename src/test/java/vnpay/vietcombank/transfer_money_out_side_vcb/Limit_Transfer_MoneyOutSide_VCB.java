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
import vietcombank_test_data.TransferMoneyOutVCB_Data;
import vietcombank_test_data.TransferMoneyQuick_Data;
import vietcombank_test_data.TransactionReport_Data.ReportTitle;
import vietcombank_test_data.TransferMoneyOutVCB_Data.MESSEGE_ERROR;
import vietcombank_test_data.TransferMoneyOutVCB_Data.TitleOutVCB;

public class Limit_Transfer_MoneyOutSide_VCB extends Base {
	AppiumDriver<MobileElement> driver;
	private WebDriver driverWeb;
	private WebBackendSetupPageObject setupBE;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferMoneyOutSideVCBPageObject transferMoneyOutSide;

	long transferFee = 0;
	double transferFeeCurrentcy = 0;
	String lowerMin,higherMax,destinationAccount,password,otpNo, currentcy = "";
	ServiceLimitInfo inputInfo = new ServiceLimitInfo("1000", "10000", "1000000", "1500000");

	SourceAccountModel sourceAccount = new SourceAccountModel();
	TransferOutSideVCB_Info info = new TransferOutSideVCB_Info("", "", TitleOutVCB.NAME_RECIEVED, TitleOutVCB.BANK_RECIEVED, "", TitleOutVCB.TRANSACTION_FEE_SENT, TitleOutVCB.TRANSACTION_CONTENT, TitleOutVCB.PASSWORD_TITLE);
	
	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp","username","passWeb" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt,String username,String passWeb) throws IOException, InterruptedException, GeneralSecurityException {
		startServer();
		
		log.info("Before class: Mo backend ");
		driverWeb = openMultiBrowser(Constants.BE_BROWSER_CHROME, Constants.BE_BROWSER_VERSION, Constants.BE_URL);
		
		setupBE = WebPageFactoryManager.getWebBackendSetupPageObject(driver);

		setupBE.Login_Web_Backend(driverWeb,username, passWeb);
		setupBE.addMethodOtpLimit(driverWeb, TitleOutVCB.BE_TRANSFER_OUTSIDE_TEXT);
//		setupBE.setupAssignServicesLimit(driverWeb,TitleOutVCB.BE_TRANSFER_OUTSIDE_TEXT,inputInfo);
		
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
		
		transferMoneyOutSide.scrollDownToText(driver, TitleOutVCB.TRANSFER_MONEY_STATUS_TEXT);
	}

	@Test
	public void TC_01_ChuyenTienToiTaiKhoanKhacNhoHonDinhMucToiThieu() {

		log.info("TC_01_1_Click Chuyen tien toi ngan hang khac");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.TITLE_TRANSFER_OUTSIDE);
		clickPopupAfter15h30();

		log.info("TC_01_2_Chon tai khoan nguon");
		transferMoneyOutSide.clickToTextID(driver, "com.VCB:id/tvContent");
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

		log.info("TC_01_8_Click Dong");
		transferMoneyOutSide.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
		homePage.clickToDynamicBackIcon(driver, TitleOutVCB.TITLE_TRANSFER_OUTSIDE);
	}

	@Test
	public void TC_03_ChuyenTienToiTaiKhoanKhacCaoHonMucToiDaTrongNgay() {

		log.info("TC_03_1_Click Chuyen tien toi ngan hang khac");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.TITLE_TRANSFER_OUTSIDE);
		clickPopupAfter15h30();

		log.info("TC_03_2_Chon tai khoan nguon");
		transferMoneyOutSide.clickToTextID(driver, "com.VCB:id/tvContent");
		sourceAccount = transferMoneyOutSide.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, "VND");
		transferMoneyOutSide.scrollUpToText(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		log.info("TC_03_3_Nhap tai khoan thu huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, destinationAccount, TitleOutVCB.ACCOUT_TO);

		log.info("TC_03_4_Nhap ten nguoi huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info.name, TitleOutVCB.BENEFICIARY_NAME);

		log.info("TC_03_5_Chon ngan hang huong");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.DESTINATION_BANK);
		transferMoneyOutSide.inputToDynamicInputBox(driver, info.destinationBank, ReportTitle.SEARCH_BUTTON);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info.destinationBank);

		log.info("TC_03_6_Nhap so tien");
		transferMoneyOutSide.inputToDynamicInputBox(driver, inputInfo.maxTran, TitleOutVCB.MONEY);

		log.info("TC_03_7_Nhap noi dung chuyen tien");
		transferMoneyOutSide.inputToDynamicInputBoxByHeader(driver, info.note, TitleOutVCB.TRANSACTION_INFOMATION, "3");

		log.info("TC_03_8_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEXT);

		log.info("TC_03_10_Chon phuong thuc xac thuc");
		transferMoneyOutSide.scrollDownToText(driver, TitleOutVCB.CHOOSE_METHOD);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.PASSWORD_TITLE);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info.authenticationMethod);

		log.info("TC_03_11_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEXT);

		transferMoneyOutSide.inputToDynamicPopupPasswordInput(driver, password, TitleOutVCB.NEXT);

		transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEXT);

		log.info("TC_03_12_Kiem tra man hinh Chuyen khoan thanh cong");
		log.info("TC_03_12_1_Kiem tra Chuyen khoan thanh cong");
		verifyEquals(transferMoneyOutSide.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY);

		log.info("TC_03_13_Click Thuc hien giao dich moi");
		transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEW_TRANSFER);
		clickPopupAfter15h30();
		
		log.info("TC_03_14_Chon tai khoan nguon");
		transferMoneyOutSide.clickToTextID(driver, "com.VCB:id/tvContent");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);
		transferMoneyOutSide.scrollUpToText(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		
		log.info("TC_03_15_Nhap tai khoan thu huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, destinationAccount, TitleOutVCB.ACCOUT_TO);

		log.info("TC_03_16_Nhap ten nguoi huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info.name, TitleOutVCB.BENEFICIARY_NAME);

		log.info("TC_03_17_Chon ngan hang huong");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.DESTINATION_BANK);
		transferMoneyOutSide.inputToDynamicInputBox(driver, info.destinationBank, ReportTitle.SEARCH_BUTTON);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info.destinationBank);

		log.info("TC_03_18_Nhap so tien");
		transferMoneyOutSide.inputToDynamicInputBox(driver, inputInfo.maxTran, TitleOutVCB.MONEY);

		log.info("TC_03_19_Nhap noi dung chuyen tien");
		transferMoneyOutSide.inputToDynamicInputBoxByHeader(driver, info.note, TitleOutVCB.TRANSACTION_INFOMATION, "3");

		log.info("TC_03_20_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEXT);

		log.info("TC_03_21_Verify hien thi man hinh thong bao loi");
		verifyEquals(transferMoneyOutSide.getTextDynamicFollowImage(driver, "com.VCB:id/ivTitle"), MESSEGE_ERROR.HIGHER_MAX_A_TRANSACTION+addCommasToLong(inputInfo.maxTran)+MESSEGE_ERROR.DETAIL_A_DAY_MESSAGE);

		log.info("TC_03_22_Click Dong");
		transferMoneyOutSide.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
		homePage.clickToDynamicBackIcon(driver, TitleOutVCB.TITLE_TRANSFER_OUTSIDE);
	}

	public void TC_04_ChuyenTienToiTaiKhoanKhacNhom() {

		log.info("TC_04_1_Click Chuyen tien toi ngan hang khac");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.TITLE_TRANSFER_OUTSIDE);

		log.info("TC_04_2_Chon tai khoan nguon");
		transferMoneyOutSide.scrollUpToText(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		transferMoneyOutSide.clickToDynamicDropDown(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);

		log.info("TC_04_3_Nhap tai khoan thu huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info.destinationAccount, TitleOutVCB.ACCOUT_TO);

		log.info("TC_04_4_Nhap ten nguoi huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info.name, TitleOutVCB.BENEFICIARY_NAME);

		log.info("TC_04_5_Chon ngan hang huong");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.DESTINATION_BANK);
		transferMoneyOutSide.inputToDynamicInputBox(driver, info.destinationBank, TitleOutVCB.SEARCH_BUTTON);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info.destinationBank);

		log.info("TC_04_6_Nhap so tien");
		transferMoneyOutSide.inputToDynamicInputBox(driver, Constants.HIGHER_MAX_TRANSFER, TitleOutVCB.MONEY);

		log.info("TC_04_7_Chọn phí giao dịch");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.TRANSACTION_FEE_SENT);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.TRANSFER_PERSON);

		log.info("TC_04_7_Nhap noi dung chuyen tien");
		transferMoneyOutSide.inputToDynamicInputBoxByHeader(driver, info.note, TitleOutVCB.TRANSACTION_INFOMATION, "3");

		log.info("TC_04_8_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_04_Step_9: Verify hien thi man hinh thong bao loi");
		transferMoneyOutSide.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyOutVCB_Data.MESSEGE_ERROR.HIGHER_MAX_AMOUNT_A_TRAN_GROUP);

		log.info("TC_04_10_Click Dong");
		transferMoneyOutSide.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
	}

	// Han muc goi 100 000 000
	public void TC_05_ChuyenTienToiTaiKhoanKhacGoi() {

		log.info("TC_05_1_Click Chuyen tien toi ngan hang khac");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.TITLE_TRANSFER_OUTSIDE);

		log.info("TC_05_2_Chon tai khoan nguon");
		transferMoneyOutSide.scrollUpToText(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		transferMoneyOutSide.clickToDynamicDropDown(driver, TitleOutVCB.ACCOUNT_FROM_LABEL);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);

		log.info("TC_05_3_Nhap tai khoan thu huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info.destinationAccount, TitleOutVCB.ACCOUT_TO);

		log.info("TC_05_4_Nhap ten nguoi huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info.name, TitleOutVCB.BENEFICIARY_NAME);

		log.info("TC_05_5_Chon ngan hang huong");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.DESTINATION_BANK);
		transferMoneyOutSide.inputToDynamicInputBox(driver, info.destinationBank, TitleOutVCB.SEARCH_BUTTON);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, info.destinationBank);

		log.info("TC_05_6_Nhap so tien");
		transferMoneyOutSide.inputToDynamicInputBox(driver, Constants.HIGHER_MAX_TRANSFER, TitleOutVCB.MONEY);

		log.info("TC_05_7_Chọn phí giao dịch");
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.TRANSACTION_FEE_SENT);
		transferMoneyOutSide.clickToDynamicButtonLinkOrLinkText(driver, TitleOutVCB.TRANSFER_PERSON);

		log.info("TC_05_8_Nhap noi dung chuyen tien");
		transferMoneyOutSide.inputToDynamicInputBoxByHeader(driver, info.note, TitleOutVCB.TRANSACTION_INFOMATION, "3");

		log.info("TC_05_9_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_05_Step_10: Verify hien thi man hinh thong bao loi");
		transferMoneyOutSide.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyOutVCB_Data.MESSEGE_ERROR.HIGHER_MAX_AMOUNT_A_TRAN_PACKAGE);

		log.info("TC_05_11: Click Dong y");
		transferMoneyOutSide.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		setupBE.resetAssignServicesLimit(driverWeb,TitleOutVCB.BE_TRANSFER_OUTSIDE_TEXT);
		service.stop();
	}

	public void clickPopupAfter15h30() {
		if (transferMoneyOutSide.isDynamicButtonDisplayed(driver, "Hủy")) {
			transferMoneyOutSide.clickToDynamicButton(driver, TitleOutVCB.NEXT);
		}

	}
}
