package vnpay.vietcombank.transfer_money_out_side_vbc;

import java.io.IOException;

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
	TransferOutSideVCB_Info info2 = new TransferOutSideVCB_Info("0011370000646", "01825909301", "Do Minh Duc", "NHTMCP Tien Phong", "500000", "Phí giao dịch người chuyển trả", "test", "SMS OTP");

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
		
		login.clickToClosePopup(driver);
	}

	@Test
	public void TC_01_ChuyenTienLienNganHangCoPhiGiaoDichNguoiChuyenTra(){
		transferMoneyOutSide = PageFactoryManager.getTransferMoneyOutSideVCBPageObject(driver);
		
		System.out.println("Start");
			
		log.info("TC_01_1_Click Chuyen tien toi ngan hang khac");
		transferMoneyOutSide.ScrollToText(driver, "Chuyển tiền tới ngân hàng khác");
		transferMoneyOutSide.clickToDynamicButionLinkOrLinkText(driver, "Chuyển tiền tới ngân hàng khác");
		
		log.info("TC_01_2_Chon tai khoan nguon");
		transferMoneyOutSide.clickToSourceAccount();
		transferMoneyOutSide.clickToDynamicButionLinkOrLinkText(driver, info.sourceAccount);
		
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
		
		log.info("TC_01_10_Chon phuong thuc xac thuc");
		transferMoneyOutSide.ScrollToText(driver, "Chọn phương thức xác thực");
		transferMoneyOutSide.clickToDynamicButionLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		transferMoneyOutSide.clickToDynamicButionLinkOrLinkText(driver, info.authenticationMethod);
		
		log.info("TC_01_11_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");
		
		transferMoneyOutSide.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");
		
		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");
	}
	
	@Test
	public void TC_02_ChuyenTienLienNganHangCoPhiGiaoDichNguoiNhanTra(){
		transferMoneyOutSide = PageFactoryManager.getTransferMoneyOutSideVCBPageObject(driver);
		
		System.out.println("Start");
			
		log.info("TC_02_1_Click Chuyen tien toi ngan hang khac");
		transferMoneyOutSide.ScrollToText(driver, "Chuyển tiền tới ngân hàng khác");
		transferMoneyOutSide.clickToDynamicButionLinkOrLinkText(driver, "Chuyển tiền tới ngân hàng khác");
		
		log.info("TC_02_2_Chon tai khoan nguon");
		transferMoneyOutSide.clickToSourceAccount();
		transferMoneyOutSide.clickToDynamicButionLinkOrLinkText(driver, info1.sourceAccount);
		
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
		verifyEquals(transferMoneyOutSide.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Số tiền (VND)").replace(",", ""), info1.money + " VND");
		verifyEquals(transferMoneyOutSide.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Số tiền phí"), "1,100 VND");
		verifyEquals(transferMoneyOutSide.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Nội dung"), info1.note);
		
		log.info("TC_02_11_Chon phuong thuc xac thuc");
		transferMoneyOutSide.ScrollToText(driver, "Chọn phương thức xác thực");
		transferMoneyOutSide.clickToDynamicButionLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		transferMoneyOutSide.clickToDynamicButionLinkOrLinkText(driver, info1.authenticationMethod);
		
		log.info("TC_02_12_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");
		
		transferMoneyOutSide.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");
		
		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");
	}
	
	@Test
	public void TC_03_ChuyenTienLienNganHangNgoaiteCoPhiGiaoDichNguoiChuyenTra(){
		transferMoneyOutSide = PageFactoryManager.getTransferMoneyOutSideVCBPageObject(driver);
		
		System.out.println("Start");
			
		log.info("TC_03_1_Click Chuyen tien toi ngan hang khac");
		transferMoneyOutSide.ScrollToText(driver, "Chuyển tiền tới ngân hàng khác");
		transferMoneyOutSide.clickToDynamicButionLinkOrLinkText(driver, "Chuyển tiền tới ngân hàng khác");
		
		log.info("TC_03_2_Chon tai khoan nguon");
		transferMoneyOutSide.clickToSourceAccount();
		transferMoneyOutSide.clickToDynamicButionLinkOrLinkText(driver, info2.sourceAccount);
		
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
		verifyEquals(transferMoneyOutSide.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Số tiền (VND)").replace(",", ""), info2.money + " VND");
		verifyEquals(transferMoneyOutSide.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Số tiền phí"), "1,100 VND");
		verifyEquals(transferMoneyOutSide.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Nội dung"), info2.note);
		
		log.info("TC_03_11_Chon phuong thuc xac thuc");
		transferMoneyOutSide.ScrollToText(driver, "Chọn phương thức xác thực");
		transferMoneyOutSide.clickToDynamicButionLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		transferMoneyOutSide.clickToDynamicButionLinkOrLinkText(driver, info2.authenticationMethod);
		
		log.info("TC_03_12_Click Tiep tuc");
		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");
		
		transferMoneyOutSide.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");
		
		transferMoneyOutSide.clickToDynamicButton(driver, "Tiếp tục");
	}
	
	@AfterMethod(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();
	}

}
