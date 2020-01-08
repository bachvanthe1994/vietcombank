package vnpay.vietcombank.transfer_money_charity;

import java.io.IOException;
import java.util.regex.Pattern;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import model.TransferCharity;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.TransferMoneyCharityPageObject;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransferMoneyCharity_Data;

public class Validation_Transfer_Money_Charity_Part_1 extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferMoneyCharityPageObject transferMoneyCharity;

	TransferCharity info = new TransferCharity(Account_Data.Valid_Account.ACCOUNT3, "Test order", "1000000", "Do Minh Duc", "So 18 ngo 3 Thai Ha", "Ho ngheo", "Mật khẩu đăng nhập");
	TransferCharity info1 = new TransferCharity(Account_Data.Valid_Account.EUR_ACCOUNT, "Test order", "10", "Do Minh Duc", "So 18 ngo 3 Thai Ha", "Ho ngheo", "Mật khẩu đăng nhập");
	
	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName" })

	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);

		login = PageFactoryManager.getLoginPageObject(driver);

		login.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");

		login.inputToDynamicLogInTextBox(driver, LogIn_Data.Login_Account.PHONE, "Tiếp tục");

		login.clickToDynamicButton(driver, "Tiếp tục");

		login.inputToDynamicInputBox(driver, LogIn_Data.Login_Account.NEW_PASSWORD, LogIn_Data.UI.PASSWORD_LABEL);

		login.clickToDynamicButton(driver, "Tiếp tục");

		login.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		login.clickToDynamicButton(driver, "Tiếp tục");

		login.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");
	}

	@Test
	public void TC_01_TenNguoiUngHo_KiemTraHienThiMacDinh() {
		homePage = PageFactoryManager.getHomePageObject(driver);
		transferMoneyCharity = PageFactoryManager.getTransferMoneyCharityPageObject(driver);

		log.info("TC_01_1_Click Chuyen tien tu thien");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền từ thiện");

		String actualName = transferMoneyCharity.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "2");
		log.info("TC_01_2_Kiem tra gia tri trong o Ten nguoi ung ho");
		verifyEquals(actualName, "Tên người ủng hộ");
	}
	
	@Test
	public void TC_02_NhapTenNguoiUngHo() {
		log.info("TC_02_1_Nhap ten nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBoxByHeader(driver, info.name, "Thông tin giao dịch", "2");
		
		
		String actualName = transferMoneyCharity.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "2");
		log.info("TC_02_2_Kiem tra gia tri trong o Ten nguoi ung ho");
		verifyEquals(actualName, info.name);
	}
	
	@Test
	public void TC_03_TenNguoiUngHo_KiemTraLoaiKyTuNhap() {
		log.info("TC_03_1_Nhap ten nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBoxByHeader(driver, TransferMoneyCharity_Data.STRING_HAS_SPECIAL_CHARACTERS, "Thông tin giao dịch", "2");
		
		String actualName = transferMoneyCharity.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "2");
		Pattern regex = Pattern.compile("[$&+,:;=\\\\?@#|/'<>.^*()%!-]");
		boolean condition = regex.matcher(actualName).find();
		log.info("TC_03_2_Kiem tra gia tri trong o Ten nguoi ung ho");
		verifyTrue(condition);
	}
	
	@Test
	public void TC_04_TenNguoiUngHo_KiemTraGioiHanKyTuNhap() {
		log.info("TC_04_1_Nhap ten nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBoxByHeader(driver, TransferMoneyCharity_Data.STRING_OVER_50_CHARACTERS, "Thông tin giao dịch", "2");
		
		String actualName = transferMoneyCharity.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "2");
		int actualNameLength = actualName.length();
		log.info("TC_04_2_Kiem tra gioi han ky tu nhap");
		verifyEquals(actualNameLength, 45);
	}
	
	@Test
	public void TC_05_DiaChiNguoiUngHo_KiemTraHienThiMacDinh() {
		homePage = PageFactoryManager.getHomePageObject(driver);
		transferMoneyCharity = PageFactoryManager.getTransferMoneyCharityPageObject(driver);

		log.info("TC_05_1_Click Chuyen tien tu thien");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền từ thiện");

		String actualName = transferMoneyCharity.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "3");
		log.info("TC_05_2_Kiem tra gia tri trong o Dia chi nguoi ung ho");
		verifyEquals(actualName, "Địa chỉ người ủng hộ");
	}
	
	@Test
	public void TC_06_NhapDiaChiNguoiUngHo() {
		log.info("TC_06_1_Nhap ten nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBoxByHeader(driver, info.address, "Thông tin giao dịch", "3");
		
		
		String actualName = transferMoneyCharity.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "3");
		log.info("TC_06_2_Kiem tra gia tri trong o Dia chi nguoi ung ho");
		verifyEquals(actualName, info.address);
	}
	
	@Test
	public void TC_07_DiaChiNguoiUngHo_KiemTraLoaiKyTuNhap() {
		log.info("TC_07_1_Nhap ten nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBoxByHeader(driver, TransferMoneyCharity_Data.STRING_HAS_SPECIAL_CHARACTERS, "Thông tin giao dịch", "3");
		
		String actualName = transferMoneyCharity.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "3");
		Pattern regex = Pattern.compile("[$&+,:;=\\\\?@#|/'<>.^*()%!-]");
		boolean condition = regex.matcher(actualName).find();
		log.info("TC_07_2_Kiem tra gia tri trong o Dia chi nguoi ung ho");
		verifyTrue(condition);
		
	}
	
	@Test
	public void TC_08_DiaChiNguoiUngHo_KiemTraGioiHanKyTuNhap() {
		log.info("TC_08_1_Nhap ten nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBoxByHeader(driver, TransferMoneyCharity_Data.STRING_OVER_50_CHARACTERS, "Thông tin giao dịch", "3");
		
		String actualName = transferMoneyCharity.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "3");
		int actualNameLength = actualName.length();
		log.info("TC_08_2_Kiem tra gioi han ky tu nhap");
		verifyEquals(actualNameLength, 45);
		
	}
	
	@Test
	public void TC_09_HoanCanhNguoiUngHo_KiemTraHienThiMacDinh() {
		homePage = PageFactoryManager.getHomePageObject(driver);
		transferMoneyCharity = PageFactoryManager.getTransferMoneyCharityPageObject(driver);

		log.info("TC_09_1_Click Chuyen tien tu thien");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền từ thiện");
		
		transferMoneyCharity.scrollToText(driver, "Hoàn cảnh người ủng hộ");

		String actualName = transferMoneyCharity.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "4");
		log.info("TC_09_2_Kiem tra gia tri trong o Hoan canh nguoi ung ho");
		verifyEquals(actualName, "Hoàn cảnh người ủng hộ");
	}
	
	@Test
	public void TC_10_NhapHoanCanhNguoiUngHo() {
		log.info("TC_10_1_Nhap ten nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBoxByHeader(driver, info.status, "Thông tin giao dịch", "4");
		
		String actualName = transferMoneyCharity.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "4");
		log.info("TC_10_2_Kiem tra gia tri trong o Hoan canh nguoi ung ho");
		verifyEquals(actualName, info.status);
	}
	
	@Test
	public void TC_11_HoanCanhNguoiUngHo_KiemTraLoaiKyTuNhap() {
		log.info("TC_11_1_Nhap ten nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBoxByHeader(driver, TransferMoneyCharity_Data.STRING_HAS_SPECIAL_CHARACTERS, "Thông tin giao dịch", "4");
		
		String actualName = transferMoneyCharity.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "4");
		Pattern regex = Pattern.compile("[$&+,:;=\\\\?@#|/'<>.^*()%!-]");
		boolean condition = regex.matcher(actualName).find();
		log.info("TC_11_2_Kiem tra gia tri trong o Hoan canh nguoi ung ho");
		verifyTrue(condition);
	}
	
	@Test
	public void TC_12_HoanCanhNguoiUngHo_KiemTraGioiHanKyTuNhap() {
		log.info("TC_12_1_Nhap ten nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBoxByHeader(driver, TransferMoneyCharity_Data.STRING_OVER_50_CHARACTERS, "Thông tin giao dịch", "4");
		
		String actualName = transferMoneyCharity.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "4");
		int actualNameLength = actualName.length();
		log.info("TC_12_2_Kiem tra gioi han ky tu nhap");
		verifyEquals(actualNameLength, 50);
	}
	
	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
