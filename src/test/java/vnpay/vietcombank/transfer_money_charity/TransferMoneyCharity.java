package vnpay.vietcombank.transfer_money_charity;

import java.io.IOException;
import java.math.BigDecimal;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import pageObjects.LogInPageObject;
import pageObjects.TransferMoneyCharityPageObject;
import vietcombankUI.DynamicPageUIs;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransferMoney_Data;
import model.TransferCharity;

public class TransferMoneyCharity extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private TransferMoneyCharityPageObject transferMoneyCharity;
	
	TransferCharity info = new TransferCharity("0010000000322", "Test order", "1000000", "Do Minh Duc", "So 18 ngo 3 Thai Ha", "Ho ngheo", "Mật khẩu đăng nhập");
	TransferCharity info1 = new TransferCharity("0011140000647", "Test order", "10", "Do Minh Duc", "So 18 ngo 3 Thai Ha", "Ho ngheo", "Mật khẩu đăng nhập");
	TransferCharity info2 = new TransferCharity("0010000000322", "Test order", "1000000", "Do Minh Duc", "So 18 ngo 3 Thai Ha", "Ho ngheo", "SMS OTP");
	TransferCharity info3 = new TransferCharity("0011140000647", "Test order", "10", "Do Minh Duc", "So 18 ngo 3 Thai Ha", "Ho ngheo", "SMS OTP");
	
	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName" })
	
	@BeforeMethod  
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities,
			String appPackage, String appName) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver= openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);

		login = PageFactoryManager.getLoginPageObject(driver);
		transferMoneyCharity = PageFactoryManager.getTransferMoneyCharityPageObject(driver);
		
		login.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");
		
		login.inputToDynamicLogInTextBox(driver, LogIn_Data.Login_Account.PHONE, "Tiếp tục");
		
		login.clickToDynamicButton(driver, "Tiếp tục");
		
		login.inputToDynamicInputBox(driver, LogIn_Data.Login_Account.NEW_PASSWORD, LogIn_Data.UI.PASSWORD_LABEL);
		
		login.clickToDynamicButton(driver, "Tiếp tục");
		
		login.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");
		
		login.clickToDynamicButton(driver, "Tiếp tục");
		
//		login.clickToDynamicButton(driver, "Hủy");
//		
//		login.clickToDynamicCloseIcon(driver, "Kích hoạt tính năng mới");
	}

	private long surplus, availableBalance, actualAvailableBalance;
	@Test
	public void TC_01_ChuyenTienTuThienBangVNDThanhToanMatKhau(){
		log.info("TC_01_1_Click Chuyen tien tu thien");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền từ thiện");
		
		log.info("TC_01_2_Chon tai khoan nguon");

		transferMoneyCharity.clickToDynamicDropDown(driver,"Tài khoản nguồn");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);

		surplus = Long.parseLong(transferMoneyCharity.getDynamicTextInTextView(driver, "Số dư khả dụng").replaceAll("\\D+",""));
		
		log.info("TC_01_3_Chon Quy/ To chuc tu thien");
		transferMoneyCharity.clickToDynamicInput(driver, "Quỹ/ Tổ chức từ thiện");
//		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, "Đóng");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info.organization);
		
		log.info("TC_01_4_Nhap so tien ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.money, "Số tiền ủng hộ");
		
		log.info("TC_01_5_Nhap ten nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.name, "Tên người ủng hộ");
		
		log.info("TC_01_6_Nhap dia chi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.address, "Địa chỉ người ủng hộ");
		
		log.info("TC_01_7_Hoan canh nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.status, "Hoàn cảnh người ủng hộ");
		
		log.info("TC_01_8_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_01_9_Verify Confirm info screen");
		verifyEquals(transferMoneyCharity.getDynamicTextInTextView(driver, "Tài khoản nguồn"), info.sourceAccount);
		verifyTrue(transferMoneyCharity.getDynamicTextInTextView(driver, "Tài khoản đích").contains(info.organization));
		String destinationAccount = transferMoneyCharity.getTextElement(driver, "Tài khoản đích").split("/")[0].trim();
		verifyEquals(transferMoneyCharity.getDynamicTextInTextView(driver, "Số tiền ủng hộ").replace(",", ""), info.money + " VND");
		verifyEquals(transferMoneyCharity.getDynamicTextInTextView(driver, "Tên người ủng hộ"), info.name);
		verifyEquals(transferMoneyCharity.getDynamicTextInTextView(driver, "Địa chỉ người ủng hộ"), info.address);
		verifyEquals(transferMoneyCharity.getDynamicTextInTextView(driver, "Hoàn cảnh người ủng hộ"), info.status);
		
		log.info("TC_01_10_Chon phuong thuc xac thuc");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		
		long fee = Long.parseLong(transferMoneyCharity.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Mật khẩu đăng nhập").replaceAll("\\D+",""));
		
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info.authenticationMethod);
		
		log.info("TC_01_11_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");
		
		transferMoneyCharity.inputToPasswordConfirm(driver, LogIn_Data.Login_Account.NEW_PASSWORD);
		
		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_01_12_Kiem tra man hinh Chuyen khoan thanh cong");
		verifyTrue(transferMoneyCharity.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoney_Data.TransferQuick.SUCCESS_TRANSFER_MONEY));
		verifyEquals(transferMoneyCharity.getDynamicTextInTextView(driver, "Tên người thụ hưởng"), info.organization);
		verifyEquals(transferMoneyCharity.getDynamicTextInTextView(driver, "Tài khoản đích"), destinationAccount);
		verifyEquals(transferMoneyCharity.getDynamicTextInTextView(driver, "Nội dung"), info.status);
		verifyTrue(transferMoneyCharity.isDynamicButtonDisplayed(driver, "Thực hiện giao dịch mới"));
		
		log.info("TC_01_13_Click Thuc hien giao dich moi");
		transferMoneyCharity.clickToDynamicButton(driver, "Thực hiện giao dịch mới");
		

		log.info("TC_01_12_Kiem tra so du kha dung luc sau");
		transferMoneyCharity.clickToDynamicDropDown(driver,"Tài khoản nguồn");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);

		actualAvailableBalance = Long.parseLong(transferMoneyCharity.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Số dư khả dụng").replaceAll("\\D+",""));

		availableBalance = surplus - Long.parseLong(info.money) - fee;
//		verifyEquals(actualAvailableBalance, availableBalance);
	}
	
	@Test
	public void TC_02_ChuyenTienTuThienBangNgoaiTeThanhToanMatKhau(){
		log.info("TC_02_1_Click Chuyen tien tu thien");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền từ thiện");
		
		log.info("TC_02_2_Chon tai khoan nguon");
		transferMoneyCharity.clickToDynamicDropDown(driver,"Tài khoản nguồn");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info1.sourceAccount);
		surplus = Long.parseLong(transferMoneyCharity.getDynamicTextInTextView(driver, "Số dư khả dụng").replaceAll("\\D+",""));
		
		log.info("TC_02_3_Chon Quy/ To chuc tu thien");
		transferMoneyCharity.clickToDynamicInput(driver, "Quỹ/ Tổ chức từ thiện");
//		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, "Đóng");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info1.organization);
		
		log.info("TC_02_4_Nhap so tien ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info1.money, "Số tiền ủng hộ");
		
		log.info("TC_02_5_Nhap ten nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info1.name, "Tên người ủng hộ");
		
		log.info("TC_02_6_Nhap dia chi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info1.address, "Địa chỉ người ủng hộ");
		
		log.info("TC_02_7_Hoan canh nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info1.status, "Hoàn cảnh người ủng hộ");
		
		log.info("TC_02_8_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_02_9_Verify Confirm info screen");
		verifyEquals(transferMoneyCharity.getDynamicTextInTextView(driver, "Tài khoản nguồn"), info1.sourceAccount);
		verifyTrue(transferMoneyCharity.getDynamicTextInTextView(driver, "Tài khoản đích").contains(info1.organization));
		
		String destinationAccount = transferMoneyCharity.getDynamicTextInTextView(driver, "Tài khoản đích").split("/")[0].trim();
		String actualMoney = transferMoneyCharity.getDynamicTextInTextView(driver, "Số tiền ủng hộ");
		String expectedMoney = String.format("%.2f", new BigDecimal(Double.parseDouble(info1.money))) + " EUR";
		
		verifyEquals(actualMoney, expectedMoney);
		verifyEquals(transferMoneyCharity.getDynamicTextInTextView(driver, "Tên người ủng hộ"), info1.name);
		verifyEquals(transferMoneyCharity.getDynamicTextInTextView(driver, "Địa chỉ người ủng hộ"), info1.address);
		verifyEquals(transferMoneyCharity.getDynamicTextInTextView(driver, "Hoàn cảnh người ủng hộ"), info1.status);
		
		log.info("TC_02_10_Chon phuong thuc xac thuc");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		
		long fee = Long.parseLong(transferMoneyCharity.getDynamicTextInTextView(driver, "Mật khẩu đăng nhập").replaceAll("\\D+",""))/100;
		
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info1.authenticationMethod);
		
		log.info("TC_02_11_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");
		
		transferMoneyCharity.inputToPasswordConfirm(driver, LogIn_Data.Login_Account.NEW_PASSWORD);
		
		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_02_12_Kiem tra man hinh Chuyen khoan thanh cong");
		verifyTrue(transferMoneyCharity.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoney_Data.TransferQuick.SUCCESS_TRANSFER_MONEY));
		verifyEquals(transferMoneyCharity.getDynamicTextInTextView(driver, "Tên người thụ hưởng"), info1.organization);
		verifyEquals(transferMoneyCharity.getDynamicTextInTextView(driver, "Tài khoản đích"), destinationAccount);
		verifyEquals(transferMoneyCharity.getDynamicTextInTextView(driver, "Nội dung"), info1.status);
		verifyTrue(transferMoneyCharity.isDynamicButtonDisplayed(driver, "Thực hiện giao dịch mới"));
		
		log.info("TC_02_13_Click Thuc hien giao dich moi");
		transferMoneyCharity.clickToDynamicButton(driver, "Thực hiện giao dịch mới");
		
		log.info("TC_02_14_Kiem tra so du kha dung luc sau");
		transferMoneyCharity.clickToDynamicDropDown(driver,"Tài khoản nguồn");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info1.sourceAccount);
		actualAvailableBalance = Long.parseLong(transferMoneyCharity.getDynamicTextInTextView(driver, "Số dư khả dụng").replaceAll("\\D+",""));

		availableBalance = surplus - Long.parseLong(info1.money) - fee;
//		verifyEquals(actualAvailableBalance, availableBalance);
	}
	
	@Test
	public void TC_03_ChuyenTienTuThienBangVNDThanhToanSMSOTP(){
		log.info("TC_03_1_Click Chuyen tien tu thien");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền từ thiện");
		
		log.info("TC_03_2_Chon tai khoan nguon");
		transferMoneyCharity.clickToDynamicDropDown(driver,"Tài khoản nguồn");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info2.sourceAccount);
		surplus = Long.parseLong(transferMoneyCharity.getDynamicTextInTextView(driver, "Số dư khả dụng").replaceAll("\\D+",""));
		
		log.info("TC_03_3_Chon Quy/ To chuc tu thien");
		transferMoneyCharity.clickToDynamicInput(driver, "Quỹ/ Tổ chức từ thiện");
//		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, "Đóng");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info2.organization);
		
		log.info("TC_03_4_Nhap so tien ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info2.money, "Số tiền ủng hộ");
		
		log.info("TC_03_5_Nhap ten nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info2.name, "Tên người ủng hộ");
		
		log.info("TC_03_6_Nhap dia chi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info2.address, "Địa chỉ người ủng hộ");
		
		log.info("TC_03_7_Hoan canh nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info2.status, "Hoàn cảnh người ủng hộ");
		
		log.info("TC_03_8_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_03_9_Verify Confirm info screen");
		verifyEquals(transferMoneyCharity.getDynamicTextInTextView(driver, "Tài khoản nguồn"), info2.sourceAccount);
		verifyTrue(transferMoneyCharity.getDynamicTextInTextView(driver, "Tài khoản đích").contains(info2.organization));
		String destinationAccount = transferMoneyCharity.getDynamicTextInTextView(driver, "Tài khoản đích").split("/")[0].trim();
		verifyEquals(transferMoneyCharity.getDynamicTextInTextView(driver, "Số tiền ủng hộ").replace(",", ""), info2.money + " VND");
		verifyEquals(transferMoneyCharity.getDynamicTextInTextView(driver, "Tên người ủng hộ"), info2.name);
		verifyEquals(transferMoneyCharity.getDynamicTextInTextView(driver, "Địa chỉ người ủng hộ"), info2.address);
		verifyEquals(transferMoneyCharity.getDynamicTextInTextView(driver, "Hoàn cảnh người ủng hộ"), info2.status);
		
		log.info("TC_03_10_Chon phuong thuc xac thuc");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		
		long fee = Long.parseLong(transferMoneyCharity.getDynamicTextInTextView(driver, "Mật khẩu đăng nhập").replaceAll("\\D+",""));
		
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info2.authenticationMethod);
		
		log.info("TC_03_11_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");
		
		transferMoneyCharity.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");
		
		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_03_12_Kiem tra man hinh Chuyen khoan thanh cong");
		verifyTrue(transferMoneyCharity.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoney_Data.TransferQuick.SUCCESS_TRANSFER_MONEY));
		verifyEquals(transferMoneyCharity.getDynamicTextInTextView(driver, "Tài khoản đích"), destinationAccount);
		verifyEquals(transferMoneyCharity.getDynamicTextInTextView(driver, "Nội dung"), info2.status);
		verifyTrue(transferMoneyCharity.isDynamicButtonDisplayed(driver, "Thực hiện giao dịch mới"));
		
		log.info("TC_03_13_Click Thuc hien giao dich moi");
		transferMoneyCharity.clickToDynamicButton(driver, "Thực hiện giao dịch mới");
		
		log.info("TC_03_14_Kiem tra so du kha dung luc sau");
		transferMoneyCharity.clickToDynamicDropDown(driver,"Tài khoản nguồn");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info2.sourceAccount);
		actualAvailableBalance = Long.parseLong(transferMoneyCharity.getDynamicTextInTextView(driver, "Số dư khả dụng").replaceAll("\\D+",""));

		availableBalance = surplus - Long.parseLong(info2.money) - fee;
//		verifyEquals(actualAvailableBalance, availableBalance);
	}
	
	@Test
	public void TC_04_ChuyenTienTuThienBangNgoaiTeThanhToanOTP(){
		log.info("TC_04_1_Click Chuyen tien tu thien");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền từ thiện");
		
		log.info("TC_04_2_Chon tai khoan nguon");
		transferMoneyCharity.clickToDynamicDropDown(driver,"Tài khoản nguồn");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info3.sourceAccount);
		surplus = Long.parseLong(transferMoneyCharity.getDynamicTextInTextView(driver, "Số dư khả dụng").replaceAll("\\D+",""));
		
		log.info("TC_04_3_Chon Quy/ To chuc tu thien");
		transferMoneyCharity.clickToDynamicInput(driver, "Quỹ/ Tổ chức từ thiện");
//		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, "Đóng");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info3.organization);
		
		log.info("TC_04_4_Nhap so tien ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info3.money, "Số tiền ủng hộ");
		
		log.info("TC_04_5_Nhap ten nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info3.name, "Tên người ủng hộ");
		
		log.info("TC_04_6_Nhap dia chi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info3.address, "Địa chỉ người ủng hộ");
		
		log.info("TC_04_7_Hoan canh nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info3.status, "Hoàn cảnh người ủng hộ");
		
		log.info("TC_04_8_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_04_9_Verify Confirm info screen");
		verifyEquals(transferMoneyCharity.getDynamicTextInTextView(driver, "Tài khoản nguồn"), info3.sourceAccount);
		verifyTrue(transferMoneyCharity.getDynamicTextInTextView(driver, "Tài khoản đích").contains(info3.organization));
		
		String destinationAccount = transferMoneyCharity.getDynamicTextInTextView(driver, "Tài khoản đích").split("/")[0].trim();
		String actualMoney = transferMoneyCharity.getDynamicTextInTextView(driver, "Số tiền ủng hộ");
		String expectedMoney = String.format("%.2f", new BigDecimal(Double.parseDouble(info1.money))) + " EUR";
		
		verifyEquals(actualMoney, expectedMoney);
		verifyEquals(transferMoneyCharity.getDynamicTextInTextView(driver, "Tên người ủng hộ"), info3.name);
		verifyEquals(transferMoneyCharity.getDynamicTextInTextView(driver, "Địa chỉ người ủng hộ"), info3.address);
		verifyEquals(transferMoneyCharity.getDynamicTextInTextView(driver, "Hoàn cảnh người ủng hộ"), info3.status);
		
		log.info("TC_04_10_Chon phuong thuc xac thuc");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		
		long fee = Long.parseLong(transferMoneyCharity.getDynamicTextInTextView(driver, "Mật khẩu đăng nhập").replaceAll("\\D+",""))/100;
		
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info3.authenticationMethod);
		
		log.info("TC_04_11_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");
		
		transferMoneyCharity.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");
		
		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_04_12_Kiem tra man hinh Chuyen khoan thanh cong");
		verifyTrue(transferMoneyCharity.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoney_Data.TransferQuick.SUCCESS_TRANSFER_MONEY));
		verifyEquals(transferMoneyCharity.getDynamicTextInTextView(driver, "Tên người thụ hưởng"), info3.organization);
		verifyEquals(transferMoneyCharity.getDynamicTextInTextView(driver, "Tài khoản đích"), destinationAccount);
		verifyEquals(transferMoneyCharity.getDynamicTextInTextView(driver, "Nội dung"), info3.status);
		verifyTrue(transferMoneyCharity.isDynamicButtonDisplayed(driver, "Thực hiện giao dịch mới"));
		
		log.info("TC_04_13_Click Thuc hien giao dich moi");
		transferMoneyCharity.clickToDynamicButton(driver, "Thực hiện giao dịch mới");
		
		log.info("TC_04_14_Kiem tra so du kha dung luc sau");
		transferMoneyCharity.clickToDynamicDropDown(driver,"Tài khoản nguồn");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info3.sourceAccount);
		actualAvailableBalance = Long.parseLong(transferMoneyCharity.getDynamicTextInTextView(driver, "Số dư khả dụng").replaceAll("\\D+",""));

		availableBalance = surplus - Long.parseLong(info3.money) - fee;
//		verifyEquals(actualAvailableBalance, availableBalance);
	}
	
	@AfterMethod(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
