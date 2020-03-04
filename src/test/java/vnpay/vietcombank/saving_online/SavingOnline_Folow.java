package vnpay.vietcombank.saving_online;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.saving_online.SavingOnlinePageObject;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransferMoneyQuick_Data;

public class SavingOnline_Folow extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private SavingOnlinePageObject savingOnline;
	
	long transferFee = 0;
	double transferFeeCurrentcy = 0;
	private String transferTime;
	private String savingAccount;
	private String transactionNumber;
	
	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
	}

	private long surplus, availableBalance, actualAvailableBalance;
	private double surplusCurrentcy, availableBalanceCurrentcy, actualAvailableBalanceCurrentcy;

	@Test
	public void TC_01_MoTaiKhoanTietKiem_VND_1Thang_LaiNhapGoc() {
		homePage = PageFactoryManager.getHomePageObject(driver);
		savingOnline = PageFactoryManager.getSavingOnlinePageObject(driver);
		
		log.info("TC_01_1_Click Mo tai khoan tiet kiem");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Mở tài khoản tiết kiệm");

		log.info("TC_01_2_Chon so tai khoan");
		savingOnline.clickToDynamicDropDown(driver, "Số tài khoản");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);
		
		log.info("TC_01_3_Chon ky han gui");
		savingOnline.clickToDynamicDropDownListTextViewByHeader(driver, "Thông tin giao dịch", "1");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, "1 tháng");
		
		log.info("TC_01_4_Nhap so tien gui");
		savingOnline.inputToDynamicInputBoxByHeader(driver, "2000000", "Thông tin giao dịch", "2");
		
		log.info("TC_01_5_Chon hinh thuc chuyen tien");
		savingOnline.clickToDynamicDropDownListTextViewByHeader(driver, "Thông tin giao dịch", "3");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, "Lãi nhập gốc");
		
		log.info("TC_01_6_Chon dong y tuan thu cam ket");
		savingOnline.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");
		
		log.info("TC_01_7_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_01_8_Kiem tra man hinh xac nhan thong tin");

		log.info("TC_01_8_1_Kiem tra tai khoan nguon");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_01_8_2_Kiem tra ky han gui");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Kỳ hạn gửi"), "1 tháng");

		log.info("TC_01_8_3_Kiem tra lai suat");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Lãi suất"), "6.6%/Năm");

		log.info("TC_01_8_4_Kiem tra so tien gui");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Số tiền gửi"), addCommasToLong("2000000") + " VND");
		
		log.info("TC_01_8_5_Kiem tra hinh thuc tra lai");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Hình thức trả lãi"), "Lãi nhập gốc");
		
		log.info("TC_01_09_Chon phuong thuc xac thuc");
		savingOnline.scrollDownToText(driver, "Chọn phương thức xác thực");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		transferFee = savingOnline.convertAvailableBalanceCurrentcyToLong(savingOnline.getDynamicTextInTransactionDetail(driver, "Mật khẩu đăng nhập"));
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		
		log.info("TC_01_10_Kiem tra so tien phi");
		verifyEquals(savingOnline.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), addCommasToLong(transferFee + "") + " VND");
		
		log.info("TC_01_11_Click nut Tiep tuc");
		savingOnline.clickToDynamicButton(driver, "Tiếp tục");
		
		savingOnline.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		savingOnline.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_01_12_Kiem tra man hinh Giao dich thanh cong");
		log.info("TC_01_12_1_Kiem tra Giao dich thanh cong");
		verifyTrue(savingOnline.isDynamicMessageAndLabelTextDisplayed(driver, "Giao dịch thành công"));

		log.info("TC_01_12_6_Lay ma giao dich");
		transferTime = savingOnline.getDynamicTransferTimeAndMoney(driver, "Giao dịch thành công", "4");
		savingAccount = savingOnline.getDynamicTextInTransactionDetail(driver, "Số tài khoản tiết kiệm");
		transactionNumber = savingOnline.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC_01_13_Click Thuc hien giao dich moi");
		savingOnline.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_01_14_Kiem tra so du kha dung luc sau");
		savingOnline.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		savingOnline.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);
		actualAvailableBalance = savingOnline.convertAvailableBalanceCurrentcyToLong(savingOnline.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));
		availableBalance = savingOnline.canculateAvailableBalances(surplus, Long.parseLong("2000000"), transferFee);
		verifyEquals(actualAvailableBalance, availableBalance);
		
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
