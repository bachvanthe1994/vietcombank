package vnpay.vietcombank.transfer_money_quick_247;

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
import pageObjects.TransferMoneyObject;
import pageObjects.WebBackendSetupPageObject;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.TransferMoneyQuick_Data;
import vietcombank_test_data.TransferMoneyQuick_Data.Tittle_Quick;
import vietcombank_test_data.TransferMoneyQuick_Data.TransferQuick;

public class Transaction_Limit_Account extends Base {
	AppiumDriver<MobileElement> driver;
	WebDriver driverWeb;
	private LogInPageObject login;
	private TransferMoneyObject transferMoney;
	private WebBackendSetupPageObject webBackend;
	SourceAccountModel sourceAccount = new SourceAccountModel();
	private String bankOut, accountRecived;

	ServiceLimitInfo inputInfo =  new ServiceLimitInfo("1000", "10000000", "20000000", "200000000");
	String inputAmountMin  = inputInfo.minTran ;
	String inputAmountMax   = inputInfo.maxTran ;
	String inputTotalLimit   = inputInfo.totalLimit  ;
	String amountType = "9000000";
	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp", "username", "passWeb" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt, String username, String passWeb) throws IOException, InterruptedException, GeneralSecurityException {
		webBackend = WebPageFactoryManager.getWebBackendSetupPageObject(driverWeb);
		log.info("Before class: Mo backend ");
		driverWeb = openMultiBrowser(Constants.BE_BROWSER_CHROME, Constants.BE_BROWSER_VERSION, Constants.BE_URL);
		webBackend.Login_Web_Backend(driverWeb, username, passWeb);

		//webBackend.addMethodOtpLimit(driverWeb, "Chuyển khoản nhanh qua số tài khoản");
		//webBackend.setupAssignServicesLimit(driverWeb, "Chuyển khoản nhanh qua số tài khoản", inputInfo);
		

		//App
		startServer();
		log.info("Before class: Mo app ");
		if (deviceType.contains("android")) {
			driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		} else if (deviceType.contains("ios")) {
			driver = openIOSApp(deviceName, udid, url);
		}
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
		transferMoney = PageFactoryManager.getTransferMoneyObject(driver);
		bankOut = getDataInCell(21);
		accountRecived = getDataInCell(4);
	}

	//@Test
	public void TC_01_SoTienNhoHonHanMucToiThieuTrenMotLanGiaoDich_TaiKhoan() throws InterruptedException {
		log.info("TC_01_Step_Click Chuyen tien nhanh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferQuick.TRANSFER_MONEY_LABEL);

		log.info("TC_01_Step_Select Chuyen tien nhanh qua tai khoan");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);

		log.info("TC_01_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, TransferQuick.ACCOUNT_FROM_LABEL);
		sourceAccount = transferMoney.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		log.info("TC_01_Step_Nhap so tai khoan chuyen");
		transferMoney.inputToDynamicInputBox(driver, accountRecived, Tittle_Quick.TYPE_SELECT_ACCOUNT);

		log.info("TC_01_Step_Select ngan hang");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Tittle_Quick.BANK_RECIVED);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, bankOut);

		log.info("TC_01_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBox(driver,(Integer.parseInt(inputAmountMin) -1) +"", TransferQuick.MOUNT_LABEL);

		log.info("TC_01_Step_Chon phi giao dich la nguoi chuyen tra");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[0]);

		log.info("TC_01_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE, Tittle_Quick.TRANSACTION_INFO, "3");

		log.info("TC_01_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);
		
		log.info("TC_01_Step_verify message khi so tien chuyen nho hon han muc toi thieu ");
		verifyEquals(transferMoney.getDynamicTextView(driver,"com.VCB:id/tvContent"), "Chuyển tiền không thành công. Số tiền giao dịch nhỏ hơn hạn mức "+addCommasToLong(inputInfo.minTran)+" VND/1 lần, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp");
	
		transferMoney.clickToDynamicContinue(driver, "com.VCB:id/btOK");
		transferMoney.clickToDynamicBackIcon(driver, TransferQuick.TRANSFER_MONEY_LABEL);
	}
	
	//@Test
	public void TC_02_SoTienLonHonHanMucToiDaTrenMotLanGiaoDich_TaiKhoan() throws InterruptedException {
		log.info("TC_01_Step_Click Chuyen tien nhanh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferQuick.TRANSFER_MONEY_LABEL);
		
		log.info("TC_01_Step_Select Chuyen tien nhanh qua tai khoan");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);

		log.info("TC_01_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, TransferQuick.ACCOUNT_FROM_LABEL);
		sourceAccount = transferMoney.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		log.info("TC_01_Step_Nhap so tai khoan chuyen");
		transferMoney.inputToDynamicInputBox(driver, accountRecived, Tittle_Quick.TYPE_SELECT_ACCOUNT);

		log.info("TC_01_Step_Select ngan hang");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Tittle_Quick.BANK_RECIVED);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, bankOut);

		log.info("TC_01_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBoxByHeader(driver, (Integer.parseInt(inputAmountMax)+1) +"", Tittle_Quick.TRANSACTION_INFO, "1");

		log.info("TC_01_Step_Chon phi giao dich la nguoi chuyen tra");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[0]);

		log.info("TC_01_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE, Tittle_Quick.TRANSACTION_INFO, "3");

		log.info("TC_01_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);
		
		log.info("TC_01_Step_verify message khi so tien chuyen nho hon han muc toi da  ");
		verifyEquals(transferMoney.getDynamicTextView(driver,"com.VCB:id/tvContent"), "Chuyển tiền không thành công. Số tiền giao dịch lớn hơn hạn mức "+addCommasToLong(inputInfo.maxTran)+" VND/1 lần, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp");
		
		transferMoney.clickToDynamicContinue(driver, "com.VCB:id/btOK");
		transferMoney.clickToDynamicBackIcon(driver, TransferQuick.TRANSFER_MONEY_LABEL);

	}
	
	//@Test
	public void TC_03_SoTienLonHonHanMucToiDaTrenMotNgayGiaoDich_TaiKhoan() {
		//Setup han muc trong 1 ngay
		webBackend.setupAssignServicesLimit_Total_Day(driverWeb, "Chuyển khoản nhanh qua số tài khoản", inputInfo,Constants.BE_CODE_PACKAGE);
		
		log.info("TC_01_Step_Click Chuyen tien nhanh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferQuick.TRANSFER_MONEY_LABEL);

		log.info("TC_01_Step_Select Chuyen tien nhanh qua tai khoan");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);

		log.info("TC_01_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, TransferQuick.ACCOUNT_FROM_LABEL);
		sourceAccount = transferMoney.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		log.info("TC_01_Step_Nhap so tai khoan chuyen");
		transferMoney.inputToDynamicInputBox(driver, accountRecived, Tittle_Quick.TYPE_SELECT_ACCOUNT);

		log.info("TC_01_Step_Select ngan hang");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Tittle_Quick.BANK_RECIVED);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, bankOut);

		log.info("TC_01_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBox(driver,(Integer.parseInt(inputTotalLimit )+1) +"", TransferQuick.MOUNT_LABEL);

		log.info("TC_01_Step_Chon phi giao dich la nguoi chuyen tra");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[0]);

		log.info("TC_01_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE, Tittle_Quick.TRANSACTION_INFO, "3");

		log.info("TC_01_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);
		
		log.info("TC_01_Step_verify message gioi han");
		verifyEquals(transferMoney.getDynamicTextView(driver,"com.VCB:id/tvContent"),"Chuyển tiền không thành công. Số tiền giao dịch lớn hơn hạn mức "+addCommasToLong(inputTotalLimit)+" VND/1 ngày, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp.");
		
		webBackend.resetAssignServicesLimit(driverWeb, "Chuyển khoản nhanh qua số tài khoản",Constants.BE_CODE_PACKAGE);
	}
	
	@Test
	public void TC_04_SoTienLonHonHanMucToiDaTrenMotNgayNhomGiaoDich_TaiKhoan() {
		//webBackend.Setup_Assign_Services_Type_Limit(driver, "TESTBUG","Chuyển khoản", amountType );
		log.info("TC_01_Step_Click Chuyen tien nhanh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferQuick.TRANSFER_MONEY_LABEL);

		log.info("TC_01_Step_Select Chuyen tien nhanh qua tai khoan");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);

		log.info("TC_01_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, TransferQuick.ACCOUNT_FROM_LABEL);
		sourceAccount = transferMoney.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		log.info("TC_01_Step_Nhap so tai khoan chuyen");
		transferMoney.inputToDynamicInputBox(driver, accountRecived, Tittle_Quick.TYPE_SELECT_ACCOUNT);

		log.info("TC_01_Step_Select ngan hang");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Tittle_Quick.BANK_RECIVED);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, bankOut);

		log.info("TC_01_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBox(driver,(Integer.parseInt(amountType )+1) +"", TransferQuick.MOUNT_LABEL);

		log.info("TC_01_Step_Chon phi giao dich la nguoi chuyen tra");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[0]);

		log.info("TC_01_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE, Tittle_Quick.TRANSACTION_INFO, "3");

		log.info("TC_01_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, Tittle_Quick.CONTINUE_BUTTON);
		
		log.info("TC_01_Step_verify message khi so tien chuyen nho hon han muc toi thieu ");
		//verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.MessageTransferMoney.MESSAGE_LESS_MIN_LIMIT_VND));
	}
	
	@Test
	public void TC_05_SoTienLomHonHanMucToiDaTrenMotNgayGoiGiaoDich_TaiKhoan() {
		log.info("TC_05_Step_Click Chuyen tien nhanh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh 24/7");

		log.info("TC_05_Step_Select Chuyen tien nhanh qua tai khoan");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);

		log.info("TC_05_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_05_Step_Nhap so tai khoan chuyen");
		transferMoney.inputToDynamicInputBox(driver, Account_Data.Valid_Account.ACCOUNT_TO, "Nhập/ chọn tài khoản thụ hưởng");

		log.info("TC_05_Step_Select ngan hang");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng thụ hưởng");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.BANK[0]);

		log.info("TC_05_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_EXCEED_MAX_TRANSFER_ONE_DAY_VND, "Số tiền");

		log.info("TC_05_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE, "Thông tin giao dịch", "3");

		log.info("TC_05_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_05_Step_verify message khi so tien chuyen nho hon han muc toi thieu ");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.MessageTransferMoney.MESSAGE_EXCEED_MAX_TRANSFER_PACKAGE));
	}
	
	@Test
	public void TC_06_SoTienNhoHonHanMucToiThieuTrenMotLanGiaoDich_The() {
		log.info("TC_06_Step_Click Chuyen tien nhanh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh 24/7");

		log.info("TC_06_Step_Select Chuyen tien nhanh qua tai khoan");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[1]);

		log.info("TC_06_Step_nhap so the");
		transferMoney.inputToDynamicInputBox(driver, "9704060129837294", "Nhập/chọn số thẻ");
		
		log.info("TC_06_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.USD_PAYMENT_BY_PASSWORD_FEE, "Số tiền");
		
		log.info("TC_06_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_06_Step_verify message khi so tien chuyen nho hon han muc toi thieu ");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.MessageTransferMoney.MESSAGE_LESS_MIN_LIMIT_USD));
	}
	
	@Test
	public void TC_07_SoTienLonHonHanMucToiThieuTrenMotLanGiaoDich_The() {
		log.info("TC_07_Step_Click Chuyen tien nhanh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh 24/7");

		log.info("TC_07_Step_Select Chuyen tien nhanh qua tai khoan");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[1]);

		log.info("TC_07_Step_nhap so the");
		transferMoney.inputToDynamicInputBox(driver, "9704060129837294", "Nhập/chọn số thẻ");
		
		log.info("TC_07_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.USD_MAX_TRANSECTION, "Số tiền");
		
		log.info("TC_07_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_07_Step_verify message khi so tien chuyen nho hon han muc toi thieu ");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.MessageTransferMoney.MESSAGE_LESS_MAX_LIMIT_USD));

	}
	
//	@Test
	public void TC_08_SoTienLonHonHanMucToiThieuTrenNgayGiaoDich_The() {
		log.info("TC_07_Step_Click Chuyen tien nhanh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh 24/7");

		log.info("TC_07_Step_Select Chuyen tien nhanh qua tai khoan");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[1]);

		log.info("TC_07_Step_nhap so the");
		transferMoney.inputToDynamicInputBox(driver, "9704060129837294", "Nhập/chọn số thẻ");
		
		log.info("TC_07_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.USD_MAX_TRANSECTION, "Số tiền");
		
		log.info("TC_07_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_07_Step_verify message khi so tien chuyen nho hon han muc toi thieu ");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.MessageTransferMoney.MESSAGE_LESS_MAX_LIMIT_USD));

	}
	
//	@Test
	public void TC_09_SoTienLonHonHanMucToiThieuTrenNgayGiaoDichCuaNhomDichVu_The() {
		log.info("TC_07_Step_Click Chuyen tien nhanh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh 24/7");

		log.info("TC_07_Step_Select Chuyen tien nhanh qua tai khoan");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[1]);

		log.info("TC_07_Step_nhap so the");
		transferMoney.inputToDynamicInputBox(driver, "9704060129837294", "Nhập/chọn số thẻ");
		
		log.info("TC_07_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.USD_MAX_TRANSECTION_GROUP, "Số tiền");
		
		log.info("TC_07_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_07_Step_verify message khi so tien chuyen nho hon han muc toi thieu ");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.MessageTransferMoney.MESSAGE_MAX_LIMIT_USD_GROUP));

	}
	
	@Test
	public void TC_10_SoTienLonHonHanMucToiThieuTrenNgayGiaoDichCuaGoiDichVu_The() {
		log.info("TC_07_Step_Click Chuyen tien nhanh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh 24/7");

		log.info("TC_07_Step_Select Chuyen tien nhanh qua tai khoan");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[1]);

		log.info("TC_07_Step_nhap so the");
		transferMoney.inputToDynamicInputBox(driver, "9704060129837294", "Nhập/chọn số thẻ");
		
		log.info("TC_07_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.USD_MAX_TRANSECTION_GROUP, "Số tiền");
		
		log.info("TC_07_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_07_Step_verify message khi so tien chuyen nho hon han muc toi thieu ");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.MessageTransferMoney.MESSAGE_MAX_LIMIT_USD_PACKAGE));

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
//		service.stop();
	}

}