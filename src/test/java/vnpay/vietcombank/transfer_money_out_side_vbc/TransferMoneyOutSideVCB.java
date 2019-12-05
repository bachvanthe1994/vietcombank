package vnpay.vietcombank.transfer_money_out_side_vbc;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import model.TransferOutSideVCB_Info;
import pageObjects.LogInPageObject;
import pageObjects.TransferMoneyOutSideVCBPageObject;
import vietcombankUI.DynamicPageUIs;
import vietcombank_test_data.LogIn_Data;

public class TransferMoneyOutSideVCB extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private TransferMoneyOutSideVCBPageObject transferMoneyOutSide;
	
	TransferOutSideVCB_Info info = new TransferOutSideVCB_Info("0011000000779", "01825909301", "Do Minh Duc", "NHTMCP Tien Phong", "500000", "Phí giao dịch người chuyển trả", "test", "SMS OTP");
	TransferOutSideVCB_Info info1 = new TransferOutSideVCB_Info("0011000000779", "01825909301", "Do Minh Duc", "NHTMCP Tien Phong", "500000", "Phí giao dịch người nhận trả", "test", "SMS OTP");
	TransferOutSideVCB_Info info2 = new TransferOutSideVCB_Info("0011370000646", "01825909301", "Do Minh Duc", "NHTMCP Tien Phong", "50", "Phí giao dịch người chuyển trả", "test", "SMS OTP");
	TransferOutSideVCB_Info info3 = new TransferOutSideVCB_Info("0011370000646", "01825909301", "Do Minh Duc", "NHTMCP Tien Phong", "50", "Phí giao dịch người nhận trả", "test", "SMS OTP");
	TransferOutSideVCB_Info info4 = new TransferOutSideVCB_Info("0011000000779", "01825909301", "Do Minh Duc", "NHTMCP Tien Phong", "500000", "Phí giao dịch người chuyển trả", "test", "Mật khẩu đăng nhập");
	TransferOutSideVCB_Info info5 = new TransferOutSideVCB_Info("0011000000779", "01825909301", "Do Minh Duc", "NHTMCP Tien Phong", "500000", "Phí giao dịch người nhận trả", "test", "Mật khẩu đăng nhập");
	
	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName" })
	
	@BeforeMethod  
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities,
			String appPackage, String appName) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver= openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);

		login = PageFactoryManager.getLoginPageObject(driver);
		
		login.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");
		
		login.inputToDynamicLogInTextBox(driver, LogIn_Data.Login_Account.PHONE, "Tiếp tục");
		
		login.clickToDynamicButton(driver, "Tiếp tục");
		
		login.inputToDynamicInputBox(driver, LogIn_Data.Login_Account.NEW_PASSWORD, LogIn_Data.UI.PASSWORD_LABEL);
		
		login.clickToDynamicButton(driver, "Tiếp tục");
		
		login.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");
		
		login.clickToDynamicButton(driver, "Tiếp tục");
		
		login.clickToDynamicButton(driver, "Hủy");
		
		login.clickToDynamicCloseIcon(driver, "Kích hoạt tính năng mới");
	}

	private long surplus, availableBalance;
	@Test
	public void TC_01_ChuyenTienLienNganHangCoPhiGiaoDichNguoiChuyenTraXacThucBangOTP(){
		transferMoneyOutSide = PageFactoryManager.getTransferMoneyOutSideVCBPageObject(driver);
		
		System.out.println("Start");
			
		log.info("TC_01_1_Click Chuyen tien toi ngan hang khac");
		transferMoneyOutSide.scrollToText(driver, "Chuyển tiền tới ngân hàng khác");
		transferMoneyOutSide.clickToDynamicButionLinkOrLinkText(driver, "Chuyển tiền tới ngân hàng khác");
		
		log.info("TC_01_2_Chon tai khoan nguon");
		transferMoneyOutSide.clickToSourceAccount(driver);
		transferMoneyOutSide.scrollToText(driver, info.sourceAccount);
		transferMoneyOutSide.clickToDynamicButionLinkOrLinkText(driver, info.sourceAccount);
		surplus = Long.parseLong(transferMoneyOutSide.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Số dư khả dụng").replaceAll("\\D+",""));
		
		log.info("TC_01_3_Nhap tai khoan thu huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info.destinationAccount, "Nhập/chọn tài khoản nhận VND");
		
		log.info("TC_01_4_Nhap ten nguoi huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info.name, "Tên người hưởng");
		
		log.info("TC_01_5_Chon ngan hang huong");
		transferMoneyOutSide.clickToDynamicButionLinkOrLinkText(driver, "Ngân hàng hưởng");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info.destinationBank, "Tìm kiếm");
		transferMoneyOutSide.clickToDynamicButionLinkOrLinkText(driver, info.destinationBank);
		
		log.info("TC_01_6_Nhap so tien");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info.money, "Số tiền");
		
		log.info("TC_01_7_Chọn phí giao dịch");
		transferMoneyOutSide.clickToDynamicButionLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
		transferMoneyOutSide.clickToDynamicButionLinkOrLinkText(driver, "Người chuyển trả");
		
		log.info("TC_01_7_Nhap noi dung chuyen tien");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info.note, "Nội dung");
		
		log.info("TC_01_8_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_01_9_Verify Confirm info screen");
		verifyEquals(transferMoneyOutSide.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Tài khoản nguồn"), info.sourceAccount);
		verifyEquals(transferMoneyOutSide.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Tài khoản đích / VND"), info.destinationAccount);
		verifyEquals(transferMoneyOutSide.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Tên người hưởng"), info.name);
		verifyEquals(transferMoneyOutSide.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Ngân hàng hưởng"), info.destinationBank);
		verifyEquals(transferMoneyOutSide.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Số tiền (VND)").replace(",", ""), info.money + " VND");
		verifyEquals(transferMoneyOutSide.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Số tiền phí"), "1,100 VND");
		verifyEquals(transferMoneyOutSide.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Nội dung"), info.note);
		
		long transactionFree = Long.parseLong(transferMoneyOutSide.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Số tiền phí").replaceAll("\\D+",""));
		availableBalance = transferMoneyOutSide.canculateAvailableBalances(surplus, Long.parseLong(info.money), transactionFree);
		
		log.info("TC_01_10_Chon phuong thuc xac thuc");
		transferMoneyOutSide.scrollToText(driver, "Chọn phương thức xác thực");
		transferMoneyOutSide.clickToDynamicButionLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		transferMoneyOutSide.clickToDynamicButionLinkOrLinkText(driver, info.authenticationMethod);
		
		log.info("TC_01_11_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");
		
		transferMoneyOutSide.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");
		
		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");
		
//		verifyTrue(transferMoneyOutSide.isDynamicMessageAndLabelTextDisplayed(driver, "Chuyển khoản thành công"));
	}
	
	@Test
	public void TC_02_ChuyenTienLienNganHangCoPhiGiaoDichNguoiNhanTraXacThucBangOTP(){
		transferMoneyOutSide = PageFactoryManager.getTransferMoneyOutSideVCBPageObject(driver);
		
		System.out.println("Start");
			
		log.info("TC_02_1_Click Chuyen tien toi ngan hang khac");
		transferMoneyOutSide.scrollToText(driver, "Chuyển tiền tới ngân hàng khác");
		transferMoneyOutSide.clickToDynamicButionLinkOrLinkText(driver, "Chuyển tiền tới ngân hàng khác");
		
		log.info("TC_02_2_Chon tai khoan nguon");
		transferMoneyOutSide.clickToSourceAccount(driver);
		transferMoneyOutSide.scrollToText(driver, info1.sourceAccount);
		transferMoneyOutSide.clickToDynamicButionLinkOrLinkText(driver, info1.sourceAccount);
		surplus = Long.parseLong(transferMoneyOutSide.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Số dư khả dụng").replaceAll("\\D+",""));
		
		log.info("TC_02_3_Nhap tai khoan thu huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info1.destinationAccount, "Nhập/chọn tài khoản nhận VND");
		
		log.info("TC_02_4_Nhap ten nguoi huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info1.name, "Tên người hưởng");
		
		log.info("TC_02_5_Chon ngan hang huong");
		transferMoneyOutSide.clickToDynamicButionLinkOrLinkText(driver, "Ngân hàng hưởng");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info1.destinationBank, "Tìm kiếm");
		transferMoneyOutSide.clickToDynamicButionLinkOrLinkText(driver, info.destinationBank);
		
		log.info("TC_02_6_Nhap so tien");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info1.money, "Số tiền");
		
		log.info("TC_02_7_Chọn phí giao dịch");
		transferMoneyOutSide.clickToDynamicButionLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
		transferMoneyOutSide.clickToDynamicButionLinkOrLinkText(driver, "Người nhận trả");
		
		log.info("TC_02_8_Nhap noi dung chuyen tien");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info1.note, "Nội dung");
		
		log.info("TC_02_9_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_02_10_Verify Confirm info screen");
		verifyEquals(transferMoneyOutSide.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Tài khoản nguồn"), info1.sourceAccount);
		verifyEquals(transferMoneyOutSide.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Tài khoản đích / VND"), info1.destinationAccount);
		verifyEquals(transferMoneyOutSide.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Tên người hưởng"), info1.name);
		verifyEquals(transferMoneyOutSide.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Ngân hàng hưởng"), info1.destinationBank);
		verifyEquals(transferMoneyOutSide.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Số tiền (VND)").replace(",", ""), info.money + " VND");
		verifyEquals(transferMoneyOutSide.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Số tiền phí"), "1,100 VND");
		verifyEquals(transferMoneyOutSide.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Nội dung"), info1.note);
		
		long transactionFree = 0;
		availableBalance = transferMoneyOutSide.canculateAvailableBalances(surplus, Long.parseLong(info.money), transactionFree);
		
		log.info("TC_02_11_Chon phuong thuc xac thuc");
		transferMoneyOutSide.scrollToText(driver, "Chọn phương thức xác thực");
		transferMoneyOutSide.clickToDynamicButionLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		transferMoneyOutSide.clickToDynamicButionLinkOrLinkText(driver, info1.authenticationMethod);
		
		log.info("TC_02_12_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");
		
		transferMoneyOutSide.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");
		
		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");
		
//		verifyTrue(transferMoneyOutSide.isDynamicMessageAndLabelTextDisplayed(driver, "Chuyển khoản thành công"));
	}
	
	@Test
	public void TC_03_ChuyenTienLienNganHangNgoaiteCoPhiGiaoDichNguoiChuyenTraXacThucBangOTP(){
		transferMoneyOutSide = PageFactoryManager.getTransferMoneyOutSideVCBPageObject(driver);
		
		System.out.println("Start");

		log.info("TC_03_1_Click Chuyen tien toi ngan hang khac");
		transferMoneyOutSide.scrollToText(driver, "Chuyển tiền tới ngân hàng khác");
		transferMoneyOutSide.clickToDynamicButionLinkOrLinkText(driver, "Chuyển tiền tới ngân hàng khác");
		
		log.info("TC_03_2_Chon tai khoan nguon");
		transferMoneyOutSide.clickToSourceAccount(driver);
		transferMoneyOutSide.scrollToText(driver, info2.sourceAccount);
		transferMoneyOutSide.clickToDynamicButionLinkOrLinkText(driver, info2.sourceAccount);
		surplus = Long.parseLong(transferMoneyOutSide.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Số dư khả dụng").replaceAll("\\D+",""));
		
		log.info("TC_03_3_Nhap tai khoan thu huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info2.destinationAccount, "Nhập/chọn tài khoản nhận VND");
		
		log.info("TC_03_4_Nhap ten nguoi huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info2.name, "Tên người hưởng");
		
		log.info("TC_03_5_Chon ngan hang huong");
		transferMoneyOutSide.clickToDynamicButionLinkOrLinkText(driver, "Ngân hàng hưởng");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info2.destinationBank, "Tìm kiếm");
		transferMoneyOutSide.clickToDynamicButionLinkOrLinkText(driver, info.destinationBank);
		
		log.info("TC_03_6_Nhap so tien");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info2.money, "Số tiền");
		
		log.info("TC_03_7_Chọn phí giao dịch");
		transferMoneyOutSide.clickToDynamicButionLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
		transferMoneyOutSide.clickToDynamicButionLinkOrLinkText(driver, "Người nhận trả");
		
		log.info("TC_03_8_Nhap noi dung chuyen tien");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info2.note, "Nội dung");
		
		log.info("TC_03_9_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_03_10_Verify Confirm info screen");
		verifyEquals(transferMoneyOutSide.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Tài khoản nguồn"), info2.sourceAccount);
		verifyEquals(transferMoneyOutSide.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Tài khoản đích / VND"), info2.destinationAccount);
		verifyEquals(transferMoneyOutSide.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Tên người hưởng"), info2.name);
		verifyEquals(transferMoneyOutSide.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Ngân hàng hưởng"), info2.destinationBank);

		String actualMoney = transferMoneyOutSide.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_SECOND_LINE_INFO, "Số tiền (USD)").replace(",", "");
		
		verifyEquals(actualMoney, (Integer.valueOf(info.money) * 30000) + " VND");
//		verifyEquals(transferMoneyOutSide.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Số tiền (USD)").replace(",", ""), info2.money + " USD");
		verifyEquals(transferMoneyOutSide.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Số tiền phí"), "0.04 USD");
		verifyEquals(transferMoneyOutSide.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Nội dung"), info2.note);
		
		long transactionFree = Long.parseLong(transferMoneyOutSide.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Số tiền phí").replaceAll("\\D+",""));
		availableBalance = transferMoneyOutSide.canculateAvailableBalances(surplus, Long.parseLong(info2.money), transactionFree);
		
		log.info("TC_03_11_Chon phuong thuc xac thuc");
		transferMoneyOutSide.scrollToText(driver, "Chọn phương thức xác thực");
		transferMoneyOutSide.clickToDynamicButionLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		transferMoneyOutSide.clickToDynamicButionLinkOrLinkText(driver, info2.authenticationMethod);
		
		log.info("TC_03_12_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");
		
		transferMoneyOutSide.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");
		
		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");
		
//		verifyTrue(transferMoneyOutSide.isDynamicMessageAndLabelTextDisplayed(driver, "Chuyển khoản thành công"));
	}
	
	@Test
	public void TC_04_ChuyenTienLienNganHangNgoaiteCoPhiGiaoDichNguoiNhanTraXacThucBangOTP(){
		transferMoneyOutSide = PageFactoryManager.getTransferMoneyOutSideVCBPageObject(driver);
		
		System.out.println("Start");
			
		log.info("TC_04_1_Click Chuyen tien toi ngan hang khac");
		transferMoneyOutSide.clickToDynamicButionLinkOrLinkText(driver, "Chuyển tiền tới ngân hàng khác");
		
		log.info("TC_04_2_Chon tai khoan nguon");
		transferMoneyOutSide.clickToSourceAccount(driver);
		transferMoneyOutSide.clickToDynamicButionLinkOrLinkText(driver, info3.sourceAccount);
		surplus = Long.parseLong(transferMoneyOutSide.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Số dư khả dụng").replaceAll("\\D+",""));
		
		log.info("TC_04_3_Nhap tai khoan thu huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info3.destinationAccount, "Nhập/chọn tài khoản nhận VND");
		
		log.info("TC_04_4_Nhap ten nguoi huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info3.name, "Tên người hưởng");
		
		log.info("TC_04_5_Chon ngan hang huong");
		transferMoneyOutSide.clickToDynamicButionLinkOrLinkText(driver, "Ngân hàng hưởng");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info3.destinationBank, "Tìm kiếm");
		transferMoneyOutSide.clickToDynamicButionLinkOrLinkText(driver, info.destinationBank);
		
		log.info("TC_04_6_Nhap so tien");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info3.money, "Số tiền");
		
		log.info("TC_04_7_Chọn phí giao dịch");
		transferMoneyOutSide.clickToDynamicButionLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
		transferMoneyOutSide.clickToDynamicButionLinkOrLinkText(driver, "Người nhận trả");
		
		log.info("TC_04_8_Nhap noi dung chuyen tien");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info3.note, "Nội dung");
		
		log.info("TC_04_9_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_04_10_Verify Confirm info screen");
		verifyEquals(transferMoneyOutSide.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Tài khoản nguồn"), info3.sourceAccount);
		verifyEquals(transferMoneyOutSide.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Tài khoản đích / VND"), info3.destinationAccount);
		verifyEquals(transferMoneyOutSide.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Tên người hưởng"), info3.name);
		verifyEquals(transferMoneyOutSide.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Ngân hàng hưởng"), info3.destinationBank);
		
		String actualMoney = transferMoneyOutSide.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_SECOND_LINE_INFO, "Số tiền (USD)").replace(",", "");
		
		verifyEquals(actualMoney, (Integer.valueOf(info.money) * 30000) + " VND");
//		verifyEquals(transferMoneyOutSide.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Số tiền (USD)").replace(",", ""), info2.money + " USD");
		
		verifyEquals(transferMoneyOutSide.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Số tiền phí"), "0.04 USD");
		verifyEquals(transferMoneyOutSide.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Nội dung"), info3.note);
		
		long transactionFree = 0;
		availableBalance = transferMoneyOutSide.canculateAvailableBalances(surplus, Long.parseLong(info3.money), transactionFree);
		
		log.info("TC_04_11_Chon phuong thuc xac thuc");
		transferMoneyOutSide.clickToDynamicButionLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		transferMoneyOutSide.clickToDynamicButionLinkOrLinkText(driver, info3.authenticationMethod);
		
		log.info("TC_04_12_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");
		
		transferMoneyOutSide.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");
		
		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");
		
//		verifyTrue(transferMoneyOutSide.isDynamicMessageAndLabelTextDisplayed(driver, "Chuyển khoản thành công"));
	}
	
	@Test
	public void TC_05_ChuyenTienLienNganHangCoPhiGiaoDichNguoiChuyenTraXacThucBangMatKhau(){
		transferMoneyOutSide = PageFactoryManager.getTransferMoneyOutSideVCBPageObject(driver);
		
		System.out.println("Start");
			
		log.info("TC_05_1_Click Chuyen tien toi ngan hang khac");
		transferMoneyOutSide.clickToDynamicButionLinkOrLinkText(driver, "Chuyển tiền tới ngân hàng khác");
		
		log.info("TC_05_2_Chon tai khoan nguon");
		transferMoneyOutSide.clickToSourceAccount(driver);
		transferMoneyOutSide.clickToDynamicButionLinkOrLinkText(driver, info4.sourceAccount);
		surplus = Long.parseLong(transferMoneyOutSide.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Số dư khả dụng").replaceAll("\\D+",""));
		
		log.info("TC_05_3_Nhap tai khoan thu huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info4.destinationAccount, "Nhập/chọn tài khoản nhận VND");
		
		log.info("TC_05_4_Nhap ten nguoi huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info4.name, "Tên người hưởng");
		
		log.info("TC_05_5_Chon ngan hang huong");
		transferMoneyOutSide.clickToDynamicButionLinkOrLinkText(driver, "Ngân hàng hưởng");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info4.destinationBank, "Tìm kiếm");
		transferMoneyOutSide.clickToDynamicButionLinkOrLinkText(driver, info.destinationBank);
		
		log.info("TC_05_6_Nhap so tien");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info4.money, "Số tiền");
		
		log.info("TC_05_7_Chọn phí giao dịch");
		transferMoneyOutSide.clickToDynamicButionLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
		transferMoneyOutSide.clickToDynamicButionLinkOrLinkText(driver, "Người nhận trả");
		
		log.info("TC_05_8_Nhap noi dung chuyen tien");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info4.note, "Nội dung");
		
		log.info("TC_05_9_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_05_10_Verify Confirm info screen");
		verifyEquals(transferMoneyOutSide.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Tài khoản nguồn"), info4.sourceAccount);
		verifyEquals(transferMoneyOutSide.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Tài khoản đích / VND"), info4.destinationAccount);
		verifyEquals(transferMoneyOutSide.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Tên người hưởng"), info4.name);
		verifyEquals(transferMoneyOutSide.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Ngân hàng hưởng"), info4.destinationBank);
		verifyEquals(transferMoneyOutSide.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Số tiền (VND)").replace(",", ""), info4.money + " VND");
		verifyEquals(transferMoneyOutSide.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Số tiền phí"), "1,100 VND");
		verifyEquals(transferMoneyOutSide.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Nội dung"), info4.note);
		
		long transactionFree = Long.parseLong(transferMoneyOutSide.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Số tiền phí").replaceAll("\\D+",""));
		availableBalance = transferMoneyOutSide.canculateAvailableBalances(surplus, Long.parseLong(info4.money), transactionFree);
		
		log.info("TC_05_11_Chon phuong thuc xac thuc");
		transferMoneyOutSide.clickToDynamicButionLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		transferMoneyOutSide.clickToDynamicButionLinkOrLinkText(driver, info4.authenticationMethod);
		
		log.info("TC_05_12_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");
		
		transferMoneyOutSide.inputToPasswordConfirm(driver, LogIn_Data.Login_Account.NEW_PASSWORD);
		
		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");
		
//		verifyTrue(transferMoneyOutSide.isDynamicMessageAndLabelTextDisplayed(driver, "Chuyển khoản thành công"));
	}
	
	@Test
	public void TC_06_ChuyenTienLienNganHangCoPhiGiaoDichNguoiNhanTraXacThucBangMatKhau(){
		transferMoneyOutSide = PageFactoryManager.getTransferMoneyOutSideVCBPageObject(driver);
		
		System.out.println("Start");
			
		log.info("TC_06_1_Click Chuyen tien toi ngan hang khac");
		transferMoneyOutSide.clickToDynamicButionLinkOrLinkText(driver, "Chuyển tiền tới ngân hàng khác");
		
		log.info("TC_06_2_Chon tai khoan nguon");
		transferMoneyOutSide.clickToSourceAccount(driver);
		transferMoneyOutSide.clickToDynamicButionLinkOrLinkText(driver, info5.sourceAccount);
		surplus = Long.parseLong(transferMoneyOutSide.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Số dư khả dụng").replaceAll("\\D+",""));
		
		log.info("TC_06_3_Nhap tai khoan thu huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info5.destinationAccount, "Nhập/chọn tài khoản nhận VND");
		
		log.info("TC_06_4_Nhap ten nguoi huong");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info5.name, "Tên người hưởng");
		
		log.info("TC_06_5_Chon ngan hang huong");
		transferMoneyOutSide.clickToDynamicButionLinkOrLinkText(driver, "Ngân hàng hưởng");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info5.destinationBank, "Tìm kiếm");
		transferMoneyOutSide.clickToDynamicButionLinkOrLinkText(driver, info.destinationBank);
		
		log.info("TC_06_6_Nhap so tien");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info5.money, "Số tiền");
		
		log.info("TC_06_7_Chọn phí giao dịch");
		transferMoneyOutSide.clickToDynamicButionLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
		transferMoneyOutSide.clickToDynamicButionLinkOrLinkText(driver, "Người nhận trả");
		
		log.info("TC_06_8_Nhap noi dung chuyen tien");
		transferMoneyOutSide.inputToDynamicInputBox(driver, info5.note, "Nội dung");
		
		log.info("TC_06_9_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_06_10_Verify Confirm info screen");
		verifyEquals(transferMoneyOutSide.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Tài khoản nguồn"), info5.sourceAccount);
		verifyEquals(transferMoneyOutSide.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Tài khoản đích / VND"), info5.destinationAccount);
		verifyEquals(transferMoneyOutSide.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Tên người hưởng"), info5.name);
		verifyEquals(transferMoneyOutSide.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Ngân hàng hưởng"), info5.destinationBank);
		verifyEquals(transferMoneyOutSide.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Số tiền (VND)").replace(",", ""), info5.money + " VND");
		verifyEquals(transferMoneyOutSide.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Số tiền phí"), "1,100 VND");
		verifyEquals(transferMoneyOutSide.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Nội dung"), info5.note);
		
		long transactionFree = Long.parseLong(transferMoneyOutSide.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Số tiền phí").replaceAll("\\D+",""));
		availableBalance = transferMoneyOutSide.canculateAvailableBalances(surplus, Long.parseLong(info5.money), transactionFree);
		
		log.info("TC_06_11_Chon phuong thuc xac thuc");
		transferMoneyOutSide.clickToDynamicButionLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		transferMoneyOutSide.clickToDynamicButionLinkOrLinkText(driver, info5.authenticationMethod);
		
		log.info("TC_06_12_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");
		
		transferMoneyOutSide.inputToPasswordConfirm(driver, LogIn_Data.Login_Account.NEW_PASSWORD);
		
		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");
		
//		verifyTrue(transferMoneyOutSide.isDynamicMessageAndLabelTextDisplayed(driver, "Chuyển khoản thành công"));
	}
	
	
	
	@AfterMethod(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();
	}

}
