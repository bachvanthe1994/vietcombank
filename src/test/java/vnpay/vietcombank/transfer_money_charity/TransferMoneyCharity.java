package vnpay.vietcombank.transfer_money_charity;

import java.io.IOException;

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
import model.TransferCharity;

public class TransferMoneyCharity extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private TransferMoneyCharityPageObject transferMoneyCharity;
	
	TransferCharity info = new TransferCharity("0011000000779", "Tấm lòng nhân ái", "500000", "Do Minh Duc", "So 18 ngo 3 Thai Ha", "Ho ngheo");
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
	public void TC_01_ChuyenTienTuThienBangVNDThanhToanMatKhau(){
		transferMoneyCharity = PageFactoryManager.getTransferMoneyCharityPageObject(driver);
		
		System.out.println("Start");
			
		log.info("TC_01_1_Click Chuyen tien tu thien");
		transferMoneyCharity.ScrollToText(driver, "Chuyển tiền từ thiện");
		transferMoneyCharity.clickToDynamicButionLinkOrLinkText(driver, "Chuyển tiền từ thiện");
		
		log.info("TC_01_2_Chon tai khoan nguon");
		transferMoneyCharity.clickToSourceAccount(driver);
		transferMoneyCharity.ScrollToText(driver, info.sourceAccount);
		transferMoneyCharity.clickToDynamicButionLinkOrLinkText(driver, info.sourceAccount);
		surplus = Long.parseLong(transferMoneyCharity.getTextElement(driver, DynamicPageUIs.DYNAMIC_CONFIRM_INFO, "Số dư khả dụng").replaceAll("\\D+",""));
		
		log.info("TC_01_3_Chon Quy/ To chuc tu thien");
		transferMoneyCharity.clickToDynamicButionLinkOrLinkText(driver, "Quỹ/ Tổ chức từ thiện");
		transferMoneyCharity.clickToDynamicButionLinkOrLinkText(driver, info.organization);
		
		log.info("TC_01_4_Nhap so tien ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.money, "Số tiền ủng hộ");
		
		log.info("TC_01_5_Nhap ten nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.name, "Tên người ủng hộ");
		
		log.info("TC_01_6_Nhap dia chi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.money, "Địa chỉ ủng hộ");
		
		log.info("TC_01_7_Hoan canh nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.money, "Hoàn cảnh người ủng hộ");
		
		log.info("TC_01_8_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");

	}
	
	@AfterMethod(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
