package vnpay.vietcombank.transfer_money_in_vcb;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.TransferMoneyObject;
import vietcombankUI.DynamicPageUIs;
import vietcombank_test_data.HomePage_Data;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransferMoney_Data;

public class QuickMoneyTransferViaAccount extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferMoneyObject transferMoney;
	private String amountOTPString;
	private long amountOTP;
	private String moneyOTPString;
	private int moneyOTP;
	private String costOTPString;
	private int costOTP;

	private String amountStartStringUSD;
	private long amountStartUSD;
	private String moneyStringUSD;
	private int moneyUSD;
	private String costStringUSD;
	private int costUSD;
	
	private String amountPassString;
	private long amountPass;
	private String moneyPassString;
	private int moneyPass;
	private String costPassString;
	private int costPass;
	
	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities,
			String appPackage, String appName) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);

		login = PageFactoryManager.getLoginPageObject(driver);

		log.info("Before class: Click Allow Button");
		login.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");
		log.info("Before class");
		login.inputToDynamicLogInTextBox(driver, LogIn_Data.Login_Account.PHONE, "Tiếp tục");

		log.info("Before class");
		login.clickToDynamicButton(driver, "Tiếp tục");

		log.info("Before class");
		login.inputToDynamicInputBox(driver, LogIn_Data.Login_Account.NEW_PASSWORD, LogIn_Data.UI.PASSWORD_LABEL);

		log.info("Before class");
		login.clickToDynamicButton(driver, "Tiếp tục");

		log.info("Before class");
		login.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		log.info("Before class");
		login.clickToDynamicButton(driver, "Tiếp tục");

		transferMoney = PageFactoryManager.getTransferMoneyObject(driver);
	}

	//@Test
	public void TC_01_ChuyenTienCoPhiGiaoDichChonNguoiChuyen() throws InterruptedException {
		log.info("TC_01_Step_Click Chuyen tien nhanh");

		transferMoney.scrollToText(driver, "Chuyển tiền đến ngân hàng khác");

		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh 24/7");

		log.info("TC_01_Step_Select Chuyen tien nhanh qua tai khoan");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.OPTION_TRANSFER[0]);

		log.info("TC_01_Step_Select tai khoan nguon");

		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.ACCOUNT_FORM);

		transferMoney.clickToSourceAccount(driver);

		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.ACCOUNT_FORM);

		log.info("TC_01_Step_Get so du kha dung");
		amountOTPString = transferMoney.getDynamicAmountLabel(driver, "Số dư khả dụng").replaceAll("\\D+", "");

		amountOTP = Long.parseLong(amountOTPString);

		log.info("TC_01_Step_Nhap so tai khoan chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoney_Data.TransferQuick.ACCOUNT_TO,
				"Nhập/chọn tài khoản nhận VND");

		log.info("TC_01_Step_Select ngan hang");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng hưởng");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.BANK);

		log.info("TC_01_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoney_Data.TransferQuick.MONEY, "Số tiền");

		log.info("TC_01_Step_Chon phi giao dich");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.COST_SUB[0]);

		log.info("TC_01_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBox(driver, TransferMoney_Data.TransferQuick.NOTE, "Nội dung");

		log.info("TC_01_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

	}
	
	//@Test
	public void TC_02_XacNhanThongTin() throws InterruptedException {
		log.info("TC_02_Step_Verify so tien chuyen");
		moneyOTPString = transferMoney.getDynamicAmountLabel(driver, "Số tiền").replaceAll("\\D+", "");
		verifyEquals(moneyOTPString, TransferMoney_Data.TransferQuick.MONEY);
		moneyOTP = Integer.parseInt(moneyOTPString);

		log.info("TC_02_Step_Verify phi chuyen tien");
		costOTPString = transferMoney.getDynamicAmountLabel(driver, "Số tiền phí").replaceAll("\\D+", "");
		verifyEquals(costOTPString, TransferMoney_Data.TransferQuick.COST_AMOUNT);
		costOTP = Integer.parseInt(costOTPString);
		
		log.info("TC_02_Step_Chon phuong thuc xac thuc");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.ACCURACY[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.ACCURACY[1]);

		log.info("TC_02_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_02_Step_Nhap ma xac thuc");
		transferMoney.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");
		
		log.info("TC_02_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_02_Verify message thanh cong");
		verifyEquals(transferMoney.getTextDynamicPopup(driver, TransferMoney_Data.TransferQuick.MESSAGE_SUCCESS),"CHUYỂN KHOẢN THÀNH CÔNG");
	}
	//@Test
	public void TC_03_BaoCaoGiaoDichChuyenTienNhanh() {
		
	}
	
	//@Test
	public void TC_04_ChuyenTienNhanhQuaTaiKhoanChonUSD() {
		log.info("TC_04_Step_Click Chuyen tien nhanh");

		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh 24/7");

		log.info("TC_04_Step_Select Chuyen tien nhanh qua the");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.OPTION_TRANSFER[0]);

		log.info("TC_04_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.ACCOUNT_FORM);

		transferMoney.scrollToText(driver, "Chuyển tiền tới ngân hàng khác");
		transferMoney.clickToDynamicIcon(driver, "Chuyển tiền nhanh 24/7");

		log.info("TC_04_Step_Select Chuyen tien nhanh qua the");
		transferMoney.clickToDynamicIcon(driver, TransferMoney_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicIcon(driver, TransferMoney_Data.TransferQuick.OPTION_TRANSFER[0]);

		log.info("TC_04_Step_Select tai khoan nguon");
		transferMoney.clickToSourceAccount(driver);

		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.ACCOUNT_USD_FORM);

		log.info("TC_04_Step_Get so du kha dung");
		amountStartStringUSD = transferMoney.getDynamicAmountLabel(driver, "Số dư khả dụng").replaceAll("\\D+", "");

		amountStartUSD = Long.parseLong(amountStartStringUSD);

		log.info("TC_04_Step_Nhap so tai khoan chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoney_Data.TransferQuick.ACCOUNT_TO,
				"Nhập/chọn tài khoản nhận VND");

		log.info("TC_04_Step_Select ngan hang");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng hưởng");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.BANK);

		log.info("TC_04_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoney_Data.TransferQuick.MONEY_USD, "Số tiền");

		log.info("TC_04_Step_Chon phi giao dich la nguoi chuyen");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.COST_SUB[0]);

		log.info("TC_04_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBox(driver, TransferMoney_Data.TransferQuick.NOTE, "Nội dung");

		log.info("TC_04_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

	}
	
	//@Test
	public void TC_05_XacNhanThongTin() throws InterruptedException {

		log.info("TC_05_Step_Verify so tien chuyen");
		moneyStringUSD = transferMoney.getDynamicAmountLabel(driver, "Số tiền").replace(".00 USD", "");
		verifyEquals(moneyStringUSD, TransferMoney_Data.TransferQuick.MONEY_USD);
		moneyUSD = Integer.parseInt(moneyStringUSD);
		System.out.println(moneyUSD);

		log.info("TC_05_Step_Verify phi chuyen tien");
		costStringUSD = transferMoney.getDynamicAmountLabel(driver, "Số tiền phí").replace(".00 USD", "");
		verifyEquals(costStringUSD, TransferMoney_Data.TransferQuick.COST_AMOUNT);
		costUSD = Integer.parseInt(costStringUSD);
		
		log.info("TC_05_Step_Chon phuong thuc xac thuc");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.ACCURACY[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.ACCURACY[1]);

		log.info("TC_05_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_05_Step_Nhap ma xac thuc");
		transferMoney.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");
		
		log.info("TC_05_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_01_Verify message thanh cong");
		verifyEquals(transferMoney.getTextDynamicPopup(driver, TransferMoney_Data.TransferQuick.MESSAGE_SUCCESS),"CHUYỂN KHOẢN THÀNH CÔNG");
	}

@Test
	public void TC_06_ChuyenTienNguoiChuyenTraPhiVND() throws InterruptedException {
		log.info("TC_01_Step_Click Chuyen tien nhanh");
		transferMoney.scrollToText(driver, "Chuyển tiền nhận bằng CMT");

		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh 24/7");

		log.info("TC_01_Step_Select Chuyen tien nhanh qua tai khoan");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.OPTION_TRANSFER[0]);

		log.info("TC_01_Step_Select tai khoan nguon");
		transferMoney.clickToSourceAccount(driver);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.ACCOUNT_FORM);

		log.info("TC_01_Step_Get so du kha dung");
		amountPassString = transferMoney.getDynamicAmountLabel(driver, "Số dư khả dụng").replaceAll("\\D+", "");

		amountPass = Long.parseLong(amountOTPString);

		log.info("TC_01_Step_Nhap so tai khoan chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoney_Data.TransferQuick.ACCOUNT_TO,
				"Nhập/chọn tài khoản nhận VND");

		log.info("TC_01_Step_Select ngan hang");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng hưởng");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.BANK);

		log.info("TC_01_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoney_Data.TransferQuick.MONEY, "Số tiền");

		log.info("TC_01_Step_Chon phi giao dich");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.COST_SUB[0]);

		log.info("TC_01_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBox(driver, TransferMoney_Data.TransferQuick.NOTE, "Nội dung");

		log.info("TC_01_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

	}
	
@Test
	public void TC_07_XacNhanThongTin() throws InterruptedException {
		log.info("TC_02_Step_Verify so tien chuyen");
		moneyPassString = transferMoney.getDynamicAmountLabel(driver, "Số tiền").replaceAll("\\D+", "");
		verifyEquals(moneyOTPString, TransferMoney_Data.TransferQuick.MONEY);
		moneyPass = Integer.parseInt(moneyOTPString);

		log.info("TC_02_Step_Verify phi chuyen tien");
		costPassString = transferMoney.getDynamicAmountLabel(driver, "Số tiền phí").replaceAll("\\D+", "");
		verifyEquals(costOTPString, TransferMoney_Data.TransferQuick.COST_AMOUNT);
		costPass = Integer.parseInt(costOTPString);
		
		log.info("TC_02_Step_Chon phuong thuc xac thuc");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.ACCURACY[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoney_Data.TransferQuick.ACCURACY[0]);

		log.info("TC_02_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_02_Step_Nhap ma xac thuc");
		transferMoney.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.NEW_PASSWORD, "Tiếp tục");
		
		log.info("TC_02_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_02_Verify message thanh cong");
		verifyEquals(transferMoney.getTextDynamicPopup(driver, TransferMoney_Data.TransferQuick.MESSAGE_SUCCESS),"CHUYỂN KHOẢN THÀNH CÔNG");
	}
	@AfterClass(alwaysRun = true)
	public void afterClass() {
		//closeApp();
		// service.stop();
	}

}
