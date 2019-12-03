package vnpay.vietcombank.transfer_money_out_side_vbc;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import model.TransferOutSideVCB_Info;
import pageObjects.LogInPageObject;
import vietcombank_test_data.LogIn_Data;

public class TransferMoneyOutSideVCB extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	TransferOutSideVCB_Info info = new TransferOutSideVCB_Info("0011000000779", "01825909301", "Do Minh Duc", "NHTMCP Tien Phong", "500000", "Phí giao dịch người chuyển trả", "test");


	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName" })
	@BeforeClass
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
		System.out.println("Start");
			
		log.info("TC_01_1_Click Chuyen tien toi ngan hang khac");
		login.ScrollToText(driver, "Chuyển tiền tới ngân hàng khác");
		login.clickToDynamicButionLinkOrLinkText(driver, "Chuyển tiền tới ngân hàng khác");
		
//		log.info("TC_01_2_Chon tai khoan nguon");
//		login.inputToDynamicTextView(driver, info.destinationAccount, "Nhập/chọn tài khoản nhận VND");
//		
		log.info("TC_01_2_Nhap tai khoan thu huong");
		login.inputToDynamicInputBox(driver, info.destinationAccount, "Nhập/chọn tài khoản nhận VND");
		
		log.info("TC_01_3_Nhap ten nguoi huong");
		login.inputToDynamicInputBox(driver, info.name, "Tên người hưởng");
		
		log.info("TC_01_4_Chon ngan hang huong");
		login.clickToDynamicButionLinkOrLinkText(driver, "Ngân hàng hưởng");
		login.inputToDynamicInputBox(driver, info.destinationBank, "Tìm kiếm");
		login.clickToDynamicButionLinkOrLinkText(driver, info.destinationBank);
		
		log.info("TC_01_5_Nhap so tien");
		login.inputToDynamicInputBox(driver, info.money, "Số tiền");
		
		log.info("TC_01_6_Nhap noi dung chuyen tien");
		login.inputToDynamicInputBox(driver, info.note, "Nội dung");
		
		log.info("TC_01_7_Click Tiep tuc");
		login.clickToDynamicButton(driver, "Tiếp tục");
	}
	
	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
